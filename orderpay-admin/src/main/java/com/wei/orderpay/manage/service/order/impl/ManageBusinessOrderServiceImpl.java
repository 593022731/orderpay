package com.wei.orderpay.manage.service.order.impl;

import com.jiutong.common.BusinessException;
import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.manage.bean.common.OrderPayResultCode;
import com.wei.orderpay.manage.bean.log.LogDataBean;
import com.wei.orderpay.manage.bean.order.OrderDetailBean;
import com.wei.orderpay.manage.bean.order.OrdersBean;
import com.wei.orderpay.manage.bean.order.ServiceOrderBean;
import com.wei.orderpay.manage.bean.order.ServiceRemit;
import com.wei.orderpay.manage.bean.user.RecepientBean;
import com.wei.orderpay.manage.bean.user.UserAccountBean;
import com.wei.orderpay.manage.bean.user.UserAccountTransactionLogBean;
import com.wei.orderpay.manage.contacts.common.APP;
import com.wei.orderpay.manage.contacts.log.LogType;
import com.wei.orderpay.manage.contacts.order.OrderStatus;
import com.wei.orderpay.manage.contacts.user.UserAccountTransaction;
import com.wei.orderpay.manage.dao.log.ManageLogDataCommonDao;
import com.wei.orderpay.manage.dao.order.*;
import com.wei.orderpay.manage.dao.user.ManageRecepientCommonDao;
import com.wei.orderpay.manage.dao.user.ManageUserAccountCommonDao;
import com.wei.orderpay.manage.dao.user.ManageUserAccountTransactionLogCommonDao;
import com.wei.orderpay.manage.pay.chainpay.ChinaPaySimpleDFUtils;
import com.wei.orderpay.manage.service.order.ManageBusinessOrderService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author : lizhi
 * @creatTime : 2016年6月22日上午10:34:43
 * @version : 1.0
 * @description : 
 *
 */
public class ManageBusinessOrderServiceImpl implements ManageBusinessOrderService{
	
	@Resource
	ManageOrderCommonDao manageOrderCommonDao;
	
	@Resource
	ManageOrderDetailCommonDao manageOrderDetailCommonDao;
	
	@Resource
	ManageOrderTransactionLogCommonDao manageOrderTransactionLogCommonDao;
	
	@Resource
	ManageRecepientCommonDao manageRecepientCommonDao;
	
	@Resource
	ManageOrderLogisticsCommonDao manageOrderLogisticsCommonDao;
	
	@Resource
	ManageOrderOperationLogCommonDao manageOrderOperationLogCommonDao;
	
	@Resource
	RefundCommonDao refundCommonDao;
	
	@Resource
	ManageUserAccountCommonDao manageUserAccountCommonDao;
	
	@Resource
	ManageUserAccountTransactionLogCommonDao manageUserAccountTransactionLogCommonDao;
	
	@Resource
	ManageServiceRemitCommonDao manageServiceRemitCommonDao;
	
	@Resource
	ManageLogDataCommonDao manageLogDataCommonDao;
	
	
	//=================================================================================业务订单相关处理  begin  别动 端午后 此部分要提出===================================================//
	
		@Override
		public ServiceOrderBean getServiceOrderByOrderCode(String keyword,int accountStatus, String createStartDT, String createEndDT, APP app) {
			OrdersBean ordersBean = manageOrderCommonDao.getOrdersByOrderCode(keyword, app.ordinal());
			
			if (ordersBean == null) {
				return null;
			}
			
			Map<String , Object> params = new HashMap<String, Object>();
			params.put("orderID", ordersBean.getOrderID());
			params.put("accountStatus", accountStatus);
			params.put("source", app.ordinal());
			params.put("createStartDT", createStartDT);
			params.put("createEndDT", createEndDT);
			
			ServiceOrderBean serviceOrderBean = manageOrderCommonDao.getServiceOrderByOrderCode(params);   
			
			if (serviceOrderBean != null) {
				serviceOrderBean.setCreateDT(serviceOrderBean.getCreateDT().substring(0, serviceOrderBean.getCreateDT().length()-2));  //时间处理
				RecepientBean recepient = manageRecepientCommonDao.getRecepientByID(serviceOrderBean.getRecepientID(), app.ordinal());  
				serviceOrderBean.setRecepient(recepient); // 收货地址装箱
				
				List<OrderDetailBean> orderDetails = manageOrderDetailCommonDao.getOrderDetails(serviceOrderBean.getOrderID(), app.ordinal());
				serviceOrderBean.setOrderDetails(orderDetails);
			}
			
			return serviceOrderBean;
		}

	    @Override
	    public List<ServiceOrderBean> getServiceOrders(List<Integer> buyerUserIDs, List<Integer> servicerUserIDs,int accountStatus, String createStartDT, String createEndDT, APP app, int page, int pageSize){
	    	int source = app.ordinal() ;
	        List<ServiceOrderBean> serviceOrderList = new ArrayList<ServiceOrderBean>();
	        
	        List<Integer> statuses = new ArrayList<Integer>();  
	        statuses.add(OrderStatus.receipted.ordinal());
	        statuses.add(OrderStatus.refunded.ordinal());   
			statuses.add(OrderStatus.arbitrationed.ordinal()); // 已完成,退款已驳回,同意退款
			
	        if (!servicerUserIDs.isEmpty()) {
	            Map<String , Object> params = new HashMap<String, Object>();
	            params.put("page", (page - 1) * pageSize);
	            params.put("pageSize", pageSize);
	            params.put("source",source);
	            params.put("statuses",statuses);
	            if( buyerUserIDs != null && buyerUserIDs.size() > 0){
	                params.put("buyerGuids", buyerUserIDs);
	            }
	            
	            params.put("servicerGuids", servicerUserIDs);
	            params.put("accountStatus", accountStatus);
	            params.put("createStartDT", createStartDT);
	            if (CommonUtils.isNotEmpty(createEndDT)) {  //结束时间
	                params.put("createEndDT", createEndDT);
	            }
	            serviceOrderList = manageOrderCommonDao.getServiceOrders(params) ;
	        }
	        if (serviceOrderList != null) {
	            for (ServiceOrderBean serviceOrderBean : serviceOrderList) {
	                serviceOrderBean.setCreateDT(serviceOrderBean.getCreateDT().substring(0, serviceOrderBean.getCreateDT().length()-2)); 
	                List<OrderDetailBean> orderDetails = manageOrderDetailCommonDao.getOrderDetails(serviceOrderBean.getOrderID(), source);
	                serviceOrderBean.setOrderDetails(orderDetails);
	            }
	        }
	        return serviceOrderList;
	    }

	    @Override
	    public List<ServiceRemit> getServiceRemitListByOrderID(int orderID ,APP app) {
	        return manageServiceRemitCommonDao.getServiceRemitListByOrderID(orderID,app.ordinal()) ;
	    }

	    @Override
	    public int saveServiceRemit(ServiceRemit item ,int guid, APP app) {
	        // 保存业务订单 添加一条提现记录
	        int source = app.ordinal();
	        UserAccountBean userAccount = manageUserAccountCommonDao.getUserAccountForUpdate(guid, app.ordinal());
	        double balance = userAccount == null ? 0:Double.parseDouble(CommonUtils.formatMoney(userAccount.getBalance()));
	        
	        if (item.getTotalAmount() - balance > 0) {
	            return 0;
	        }
	        String tradeNo = CommonUtils.getRandomNumber(10, 99) + CommonUtils.getDateString("yyyyMMddHHmmss", System.currentTimeMillis());
	        
	        item.setTradeNo(tradeNo); 
	        item.setSource(source);
	        
	        int userAccountID = userAccount.getUserAccountID();
	        
			balance = Double.valueOf(CommonUtils.formatMoney(balance-item.getTotalAmount()));
			
	        //账户表更新（如果提现失败需要把金额恢复）
	        int resCode = manageUserAccountCommonDao.updateBalance(balance, guid, source);
	        if(resCode == 0){
				System.out.println("余额更新失败：guid="+guid+",balance="+balance);
				throw new BusinessException(OrderPayResultCode.WITHDRAWCASH_ERROR);
			}
	        
	        int id = manageServiceRemitCommonDao.saveServiceRemit(item) ;
	        Map<String, Object> paras = new HashMap<String, Object>() ;
	        paras.put("orderID", item.getOrderID()) ;
	        paras.put("source", source) ;
	        // 每次提交 都校验一下状态
	        int count = manageServiceRemitCommonDao.getNotAddCount(paras) ;
	        if(count == 0){
	            paras.put("status", 1) ;
	            manageServiceRemitCommonDao.updateAccountStatus(paras) ;
	        }
	        
	        //账户操作日志记录
	        UserAccountTransactionLogBean accountTransactionLog = new UserAccountTransactionLogBean();
	        accountTransactionLog.setUserAccountID(userAccountID);
	        accountTransactionLog.setSource(source);
	        accountTransactionLog.setUserAccountTransactionType(UserAccountTransaction.REMITTANCE.ordinal());
	        accountTransactionLog.setResourceID(id);
	        accountTransactionLog.setBalance(balance);
	        accountTransactionLog.setCharge(-item.getTotalAmount());
	        
	        manageUserAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountTransactionLog);
	        
	        return id ;
	    }

	    @Override
	    public List<ServiceRemit> getServiceRemitListByParas(ServiceRemit item,String startDT ,String endDT, APP app ,int pageNo,int pageSize) {
	        int source = app.ordinal() ;
	        Map<String, Object> paras = new HashMap<String, Object>() ;
	        
	        paras.put("pageNo", (pageNo - 1) * pageSize);
	        paras.put("pageSize", pageSize);
	        paras.put("startDT", startDT) ;
	        paras.put("endDT", endDT) ;
	        paras.put("source", source) ;
	        paras.put("status", item.getStatus()) ;
	        
	        if (item != null) {
	        	if(item.getGuids() != null && !item.getGuids().isEmpty()){
	        		paras.put("guids", item.getGuids()) ;
	        	}
	        	if(CommonUtils.isNotEmpty(item.getOrderCode())){
	        		paras.put("orderCode", item.getOrderCode()) ;
	        	}
	        	if(CommonUtils.isNotEmpty(item.getSupplierName())){
	        		paras.put("supplierName", item.getSupplierName()) ;
	        	}
			}
	        
	        return manageServiceRemitCommonDao.getServiceRemitListByParas(paras) ;
	    }
	    
	    @Override
		public void doServiceRemit(int id, APP app) {
	    	int source = app.ordinal();
			//日志记录
			LogDataBean logdata = new LogDataBean();
			logdata.setSource(source);
			logdata.setLogType(LogType.SERVICE_REMIT_REQ.ordinal());
			logdata.setResourceID(id);
			manageLogDataCommonDao.saveLogData(logdata);
			
			//业务汇款表数据读取
			ServiceRemit serviceRemit = manageServiceRemitCommonDao.getServiceRemitByID(id, source);
			if (serviceRemit != null) {
				
				// 修改业务汇款表状态
				int updateCode= manageServiceRemitCommonDao.updateServiceRemit4Success(id, source);
				if (updateCode==0) { // 重复汇款了
					throw new BusinessException(OrderPayResultCode.SERVICE_REMIT_ERROR);
				}
				boolean flag = ChinaPaySimpleDFUtils.doDF(serviceRemit.getTradeNo(), serviceRemit.getTotalAmount(), serviceRemit.getCardNo(), serviceRemit.getSupplierName(), serviceRemit.getBankName(), serviceRemit.getProvinceName(), serviceRemit.getCityName());
				if (!flag) { //汇款失败
					manageServiceRemitCommonDao.updateServiceRemit4Fail(id, source);
					logdata.setLogType(LogType.SERVICE_REMIT_FAILED.ordinal());
					manageLogDataCommonDao.saveLogData(logdata);//日志记录
				} else { //汇款成功
					logdata.setLogType(LogType.SERVICE_REMIT_SUCCESS.ordinal());
					manageLogDataCommonDao.saveLogData(logdata);//日志记录
				}
			}
		}

		//=================================================================================业务订单相关处理  begin=====================================================//
	
	
	
	
}
