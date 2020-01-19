package com.wei.orderpay.manage.service.order.impl;

import com.jiutong.common.BusinessException;
import com.jiutong.common.Result;
import com.jiutong.common.tools.CommonUtils;
import com.jiutong.common.tools.Pagination;
import com.jiutong.orderpay.manage.bean.order.*;
import com.jiutong.orderpay.manage.dao.order.*;
import com.wei.orderpay.manage.bean.common.OrderPayResultCode;
import com.wei.orderpay.manage.bean.order.*;
import com.wei.orderpay.manage.bean.user.RecepientBean;
import com.wei.orderpay.manage.bean.user.UserAccountBean;
import com.wei.orderpay.manage.bean.user.UserAccountTransactionLogBean;
import com.wei.orderpay.manage.contacts.common.APP;
import com.wei.orderpay.manage.contacts.order.OpType;
import com.wei.orderpay.manage.contacts.order.OrderStatus;
import com.wei.orderpay.manage.contacts.user.UserAccountTransaction;
import com.wei.orderpay.manage.dao.order.*;
import com.wei.orderpay.manage.dao.user.ManageRecepientCommonDao;
import com.wei.orderpay.manage.dao.user.ManageUserAccountCommonDao;
import com.wei.orderpay.manage.dao.user.ManageUserAccountTransactionLogCommonDao;
import com.wei.orderpay.manage.service.order.ManageOrderService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("manageOrderService")
public class ManageOrderServiceImpl implements ManageOrderService {
	
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
	
//	@Resource
//	@Resource
	
	
	
	
	
	@Override
	public Result getOrderDetail(int orderID, APP app) {
		int source = app.ordinal();
		OrdersBean orders = manageOrderCommonDao.getOrders(orderID, source);
		if (orders == null) {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_DELETE);
		}

		List<OrderDetailBean> orderDetails = manageOrderDetailCommonDao.getOrderDetails(orderID, source);
		orders.setOrderDetails(orderDetails);

		List<OrderTransactionLogBean> orderTransactionLogs = manageOrderTransactionLogCommonDao.getOrderTransactionLogs(orderID, source);
		for (OrderTransactionLogBean orderTransactionLogBean : orderTransactionLogs) {
			orderTransactionLogBean.setCreateDT(orderTransactionLogBean.getCreateDT().substring(0, orderTransactionLogBean.getCreateDT().length()-2)); 
		}
		orders.setOrderTransactionLogs(orderTransactionLogs);

		RecepientBean recepient = manageRecepientCommonDao.getRecepientByID(orders.getRecepientID(),app.ordinal());
		orders.setRecepient(recepient);
		
		Result result = new Result();
		result.setData(orders);
		return result;
	}
	
	@Override
	public int doCancelOrders(int orderID, int buyerUID, APP app,int operatorId) {
		int source = app.ordinal();
		OrdersBean orders = manageOrderCommonDao.getOrders(orderID, source);

		if (orders.getGuid() != buyerUID) {
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}

		int status = orders.getStatus();
		// 待付款才可以取消订单
		if (status == OrderStatus.noPay.ordinal()) {
			int resCode = manageOrderCommonDao.updateOrderStatus(orderID, source,
					buyerUID, OrderStatus.cancel.ordinal(), status);
			if (resCode > 0) {// 更新成功,记录日志
				saveOrderLog(orderID, operatorId, OpType.cancelByKefu,
						OrderStatus.cancel, source);
			} else {
				throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
			}
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_SEND);
		}
		return 1;
	}
	
	@Override
	public void saveOrderLog(int orderID, int userID, OpType opType,
			OrderStatus orderStatus, int source) {
		OrderTransactionLogBean orderTransactionLog = new OrderTransactionLogBean();
		orderTransactionLog.setOrderID(orderID);
		orderTransactionLog.setStatus(orderStatus.ordinal());
		orderTransactionLog.setSource(source);
		this.manageOrderTransactionLogCommonDao
				.saveOrderTransactionLog(orderTransactionLog);

		saveOrderOpLog(orderID, userID, opType, source);
	}
	
	/**
	 * 只保存订单操作日志，因为有些订单操作不需要变更订单状态，比如卖家修改价格操作、删掉订单、待付款订单支付操作
	 * 
	 * @param orderID
	 * @param userID
	 * @param opType
	 * @param source
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午6:27:00
	 */
	void saveOrderOpLog(int orderID, int userID, OpType opType, int source) {
		OrderOperationLogBean orderOperationLog = new OrderOperationLogBean();
		orderOperationLog.setOrderID(orderID);
		orderOperationLog.setUserID(userID);
		orderOperationLog.setSource(source);
		orderOperationLog.setOpTypeID(opType.opType);
		orderOperationLog.setUserRole(opType.userRole.ordinal());
		manageOrderOperationLogCommonDao.saveOrderOperationLog(orderOperationLog);
	}
	
	@Override
	public RefundBean doQueryRefundReason(int orderID, APP app) {
		int source = app.ordinal();
		return refundCommonDao.queryRefundReason(orderID, source);
	}
	
	@Override
	public Result doRejectRefund(int orderID, int customerID,
			String rejectRefundReason, APP app) {
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();
		int source = app.ordinal();
		
		OrdersBean orders = manageOrderCommonDao.getOrders(orderID, source);
		int status = orders.getStatus();
		if (status != OrderStatus.applicationRefund.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
		}
		RefundBean refund = new RefundBean();
		refund.setOrderID(orderID);
		refund.setGuid(orders.getGuid());
		refund.setSource(source);
		refund.setCustomerID(customerID);
		refund.setCustomUpdateDT(CommonUtils.getDateString(
				"yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()));
		if (CommonUtils.isNotEmpty(rejectRefundReason)) {
			refund.setRejectRefundReason(rejectRefundReason);
		}
		refundCommonDao.rejectRefund(refund);

		int resCode = manageOrderCommonDao.updateOrderStatus(orderID, source,
				orders.getGuid(), OrderStatus.arbitrationed.ordinal(), status);
		if (resCode > 0) {// 更新成功,记录日志
			saveOrderLog(orderID, customerID, OpType.rejectRefund,
					OrderStatus.arbitrationed, source);
			
			//查询订单卖家
			int sellerUid = this.manageOrderDetailCommonDao.getSellerUid(orderID, source);
			UserAccountBean userAccount = manageUserAccountCommonDao.getUserAccountForUpdate(sellerUid, source);
			double balance = orders.getActualPayment();
			double oldbalance = 0;//原来的账号余额
			if(userAccount == null){//没有账户，先插入
				userAccount = new UserAccountBean();
				userAccount.setGuid(sellerUid);
				userAccount.setSource(source);
				userAccount.setBalance(balance);;
				manageUserAccountCommonDao.saveUserAccount(userAccount);
			}else{//更新账户数据
				oldbalance = userAccount.getBalance();
				
				balance = Double.valueOf(CommonUtils.formatMoney(balance+oldbalance));
				
				resCode = manageUserAccountCommonDao.updateBalance(balance, sellerUid, source);
				if(resCode == 0){
					System.out.println("余额更新失败：orderID="+orderID+",oldbalance="+oldbalance+",newbalance="+balance);
					throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
				}
			}
			UserAccountTransactionLogBean accountLog = new UserAccountTransactionLogBean();
			accountLog.setSource(source);
			accountLog.setUserAccountID(userAccount.getUserAccountID());
			accountLog.setBalance(balance);
			accountLog.setCharge(+orders.getActualPayment());
			accountLog.setUserAccountTransactionType(UserAccountTransaction.PAY.ordinal());
			accountLog.setResourceID(orderID);
			manageUserAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountLog);
			
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		
		map.put("buyerUID", String.valueOf(orders.getGuid()));
		map.put("orderCode", orders.getOrderCode());
		result.setData(map);
		return result;
	}
	
	@Override
	public Result doGetGoods(int orderID, int buyerUID, APP app,int operatorId) {
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();
	
		int source = app.ordinal();
		OrdersBean orders = manageOrderCommonDao.getOrders(orderID, source); // 获取订单详情
		int status = orders.getStatus();				//订单状态
		if (orders.getGuid() != buyerUID) {
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}
		if (status != OrderStatus.waitGoodsReceipt.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
		}
		int resCode = manageOrderCommonDao.updateOrderStatus(orderID, source,
				buyerUID, OrderStatus.receipted.ordinal(), status); //修改订单状态
		if (resCode > 0) {// 更新成功,记录日志
			
			//查询订单卖家
			int sellerUid = this.manageOrderDetailCommonDao.getSellerUid(orderID, source);
			UserAccountBean userAccount = manageUserAccountCommonDao.getUserAccountForUpdate(sellerUid, source);
			double balance = orders.getActualPayment();
			double oldbalance = 0;//原来的账号余额
			if(userAccount == null){//没有账户，先插入
				userAccount = new UserAccountBean();
				userAccount.setGuid(sellerUid);
				userAccount.setSource(source);
				userAccount.setBalance(balance);;
				manageUserAccountCommonDao.saveUserAccount(userAccount);
			}else{//更新账户数据
				oldbalance = userAccount.getBalance();
				
				balance = Double.valueOf(CommonUtils.formatMoney(balance+oldbalance));
				
				resCode = manageUserAccountCommonDao.updateBalance(balance, sellerUid, source);
				if(resCode == 0){
					System.out.println("余额更新失败：orderID="+orderID+",oldbalance="+oldbalance+",newbalance="+balance);
					throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
				}
			}
			UserAccountTransactionLogBean accountLog = new UserAccountTransactionLogBean();
			accountLog.setSource(source);
			accountLog.setUserAccountID(userAccount.getUserAccountID());
			accountLog.setBalance(balance);
			accountLog.setCharge(+orders.getActualPayment());
			accountLog.setUserAccountTransactionType(UserAccountTransaction.PAY.ordinal());
			accountLog.setResourceID(orderID);
			manageUserAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountLog);

			
			saveOrderLog(orderID, operatorId, OpType.getGoodsByKefu,
					OrderStatus.receipted, source);
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		
		int sellerUid = manageOrderDetailCommonDao.getSellerUid(orderID, source); //查询卖家ID
		
		map.put("sellerUID", String.valueOf(sellerUid));
		map.put("orderCode", orders.getOrderCode());
		result.setData(map);
		return result;
	}
	
	
	
	
	
	
	
	@Override
	public OrdersBean getOrderByOrderCode(String orderCode, APP app) {
		
		OrdersBean ordersBean = manageOrderCommonDao.getOrdersByOrderCode(orderCode, app.ordinal());
		if (ordersBean != null) {
			ordersBean.setCreateDT(ordersBean.getCreateDT().substring(0, ordersBean.getCreateDT().length()-2));
			RecepientBean recepient = manageRecepientCommonDao.getRecepientByID(ordersBean.getRecepientID(), app.ordinal());  
			ordersBean.setRecepient(recepient); // 收货地址装箱
			
			List<OrderDetailBean> orderDetails = manageOrderDetailCommonDao.getOrderDetails(ordersBean.getOrderID(), app.ordinal());
			ordersBean.setOrderDetails(orderDetails);
		}
		
		return ordersBean;
	}
	
	@Override
	public List<OrdersBean> getOrderByBuyerGuid4Manage(List<Integer> guids ,String status ,String createStartDT ,String createEndDT ,int source ,int page ,int pageSize){
		List<OrdersBean> ordersBeans = new ArrayList<OrdersBean>();
		if (!guids.isEmpty()) {
			ordersBeans = manageOrderCommonDao.getBuyerOrderList4Buyer(guids ,status ,createStartDT ,createEndDT , source ,page ,pageSize);
		}
		if (ordersBeans != null) {
			for (OrdersBean ordersBean : ordersBeans) {
				ordersBean.setCreateDT(ordersBean.getCreateDT().substring(0, ordersBean.getCreateDT().length()-2)); 
				List<OrderDetailBean> orderDetails = manageOrderDetailCommonDao.getOrderDetails(ordersBean.getOrderID(), source);
				ordersBean.setOrderDetails(orderDetails);
			}
		}
		
		return ordersBeans;
	}
	
	@Override
	public List<OrdersBean> getOrderBysellererGuid4Manage(List<Integer> guids, String status,String createStartDT, String createEndDT, int source, int page,int pageSize) {
		List<OrdersBean> ordersBeans = new ArrayList<OrdersBean>();
		if (!guids.isEmpty()) {
			ordersBeans = manageOrderCommonDao.getBuyerOrderList4Seller(guids ,status ,createStartDT ,createEndDT , source ,page ,pageSize);
		}
		if (ordersBeans != null) {
			for (OrdersBean ordersBean : ordersBeans) {
				ordersBean.setCreateDT(ordersBean.getCreateDT().substring(0, ordersBean.getCreateDT().length()-2)); 
				List<OrderDetailBean> orderDetails = manageOrderDetailCommonDao.getOrderDetails(ordersBean.getOrderID(), source);
				ordersBean.setOrderDetails(orderDetails);
			}
		}
		return ordersBeans;
	}
	
	@Override
	public List<OrdersBean> getOrderByNull4Manage(String status,String createStartDT, String createEndDT, int source, int page,int pageSize) {
		
		List<OrdersBean> ordersBeans = manageOrderCommonDao.getOrderByNull4Manage(status ,createStartDT ,createEndDT , source ,page ,pageSize);
		
		if (ordersBeans != null) {
			for (OrdersBean ordersBean : ordersBeans) {
				ordersBean.setCreateDT(ordersBean.getCreateDT().substring(0, ordersBean.getCreateDT().length()-2)); 
				List<OrderDetailBean> orderDetails = manageOrderDetailCommonDao.getOrderDetails(ordersBean.getOrderID(), source);
				ordersBean.setOrderDetails(orderDetails);
			}
		}
		return ordersBeans;
	}
	
	@Override
	public String getOrderCodesByStatusAndFollowMark(int followMark ,int source) {
		List<Integer> statuses = new ArrayList<Integer>();  
		statuses.add(OrderStatus.payWaitDelivery.ordinal());
		statuses.add(OrderStatus.waitGoodsReceipt.ordinal());
		statuses.add(OrderStatus.applicationRefund.ordinal());  //待收货，代发货 ，退款
		
		String orderCode = manageOrderCommonDao.getOrderCodesByStatusAndFollowMark(statuses, followMark, source, 1, 1);
		Integer update = manageOrderCommonDao.updateFollowMarkByOrderCode(orderCode);
		if (update == 0) {
			orderCode = null;	
		}
		return orderCode;
	}
	
	@Override
	public void updateFollowMark4Quartz() {
		List<Integer> statuses = new ArrayList<Integer>();  
		statuses.add(OrderStatus.payWaitDelivery.ordinal());
		statuses.add(OrderStatus.waitGoodsReceipt.ordinal());
		statuses.add(OrderStatus.applicationRefund.ordinal());  //待收货，代发货 ，退款
		
		manageOrderCommonDao.updateFollowMark4Quartz(statuses);
		
	}
	
	@Override
	public OrderLogisticsBean getOrderLogisticsByOrderID(int orderID, APP app) {
		
		return manageOrderLogisticsCommonDao.getOrderLogisticsByOrderID(orderID, app.ordinal());
	}

	@Override
	public List<OrdersBean> getOrdersUnpaidOver24h(APP app) {
		return manageOrderCommonDao.getOrdersUnpaidOver24h(OrderStatus.noPay.ordinal(),app.ordinal());
	}
	
	@Override
	public int getSellerUid(int orderID, APP app) {
		return manageOrderDetailCommonDao.getSellerUid(orderID,app.ordinal());
	}
	
	@Override
	public Result doWarnDelivery(int orderID,int buyerUID, APP app) {
		int source = app.ordinal();
		manageOrderCommonDao.updateOrderStatusDT(orderID, buyerUID,OrderStatus.payWaitDelivery.ordinal(), source);
		
		OrdersBean orders = manageOrderCommonDao.getOrders(orderID, source);
		String orderCode = orders.getOrderCode();
		
		int sellerUid = manageOrderDetailCommonDao.getSellerUid(orderID,source);
		
		Result result = new Result();
		JSONObject data = new JSONObject();
		data.put("orderCode", orderCode);
		data.put("sellerUid", sellerUid);
		result.setData(data);
		return result;
	}

	public static void main(String[] args) {
		System.out.println(1.8f<1.8d);
	}

	@Override
	public List<OrdersBean> getOrdersList(List<Integer> productID, List<Integer> userBidID,
			Integer status, Integer productOrBid, String orderCode,
			Integer buyerOrSellerUid, String createStartDT, String createEndDT,
			Integer pageNo, Integer pageSize, APP app,Integer orderType) {
		return this.manageOrderCommonDao.getOrdersList(productID, userBidID, status, productOrBid, orderCode, buyerOrSellerUid, createStartDT, createEndDT, app.ordinal(), pageNo, pageSize,orderType);
	}
	
	@Override
	public int getOrdersListCount(List<Integer> productID, List<Integer> userBidID,
			Integer status, Integer productOrBid, String orderCode,
			Integer buyerOrSellerUid, String createStartDT, String createEndDT,
			Integer pageNo, Integer pageSize, APP app,Integer orderType) {
		return this.manageOrderCommonDao.getOrdersListCount(productID, userBidID, status, productOrBid, orderCode, buyerOrSellerUid, createStartDT, createEndDT, app.ordinal(), pageNo, pageSize,orderType);
	}
	
	@Override
	public Pagination<OrdersBean> getOrdersListPagination(List<Integer> productID, List<Integer> userBidID,
			Integer status, Integer productOrBid, String orderCode,
			Integer buyerOrSellerUid, String createStartDT, String createEndDT,
			Integer pageNo, Integer pageSize, APP app,Integer orderType) {
		List<OrdersBean> ordersList = this.manageOrderCommonDao.getOrdersList(productID, userBidID, status, productOrBid, orderCode, buyerOrSellerUid, createStartDT, createEndDT, app.ordinal(), pageNo, pageSize,orderType);
		int resultCount = this.manageOrderCommonDao.getOrdersListCount(productID, userBidID, status, productOrBid, orderCode, buyerOrSellerUid, createStartDT, createEndDT, app.ordinal(), pageNo, pageSize,orderType);
		return new Pagination<OrdersBean>(pageNo,pageSize,resultCount,ordersList); 
	}
	
}
