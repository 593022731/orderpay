package com.wei.orderpay.service.pay.impl;

import java.util.List;

import javax.annotation.Resource;

import com.wei.orderpay.bean.common.OrderPayResultCode;
import com.wei.orderpay.bean.log.LogDataBean;
import com.wei.orderpay.bean.order.OrdersBean;
import com.wei.orderpay.bean.order.RefundBean;
import com.wei.orderpay.bean.user.UserAccountBean;
import com.wei.orderpay.bean.user.UserAccountTransactionLogBean;
import com.wei.orderpay.contacts.common.APP;
import com.wei.orderpay.contacts.log.LogType;
import com.wei.orderpay.contacts.order.MoneyIOType;
import com.wei.orderpay.contacts.user.UserAccountTransaction;
import com.wei.orderpay.dao.log.LogDataCommonDao;
import com.wei.orderpay.dao.order.MoneyIOLogDao;
import com.wei.orderpay.dao.order.OrderCommonDao;
import com.wei.orderpay.dao.order.OrderDetailCommonDao;
import com.wei.orderpay.dao.order.RefundCommonDao;
import com.wei.orderpay.dao.user.UserAccountCommonDao;
import com.wei.orderpay.dao.user.UserAccountTransactionLogCommonDao;
import com.wei.orderpay.service.pay.RefundService;
import org.springframework.stereotype.Service;

import com.jiutong.common.BusinessException;
import com.jiutong.common.tools.CommonUtils;

@Service("refundService")
public class RefundServiceImpl implements RefundService {

	@Resource
    RefundCommonDao refundCommonDao;
	
	@Resource
    OrderCommonDao orderCommonDao;
	
	@Resource
    LogDataCommonDao logDataCommonDao;
	
	@Resource
    OrderDetailCommonDao orderDetailCommonDao;
	
	@Resource
    UserAccountCommonDao userAccountCommonDao;
	
	@Resource
    MoneyIOLogDao moneyIOLogDao;
	
	@Resource
    UserAccountTransactionLogCommonDao userAccountTransactionLogCommonDao;
	
	/*@Override
	public void doRefundQuery(APP app) {
		try {
			int source = app.ordinal();
			List<RefundBean> refundOrders = refundCommonDao.getRefundOrders(source);
			for(RefundBean item : refundOrders){
				int orderID = item.getOrderID();
				OrdersBean orders = orderCommonDao.getOrders(orderID, source);
				int payType = orders.getPayType();
				String out_trade_no = orders.getOrderPayCode();
				boolean isTrue = false;
				if(payType == PayType.ALIPAY.ordinal()){//支付宝退款通过退款异步通知回调来更新状态
					//isTrue = AliRefundUtils.doRefundQuery(out_trade_no);
				}else if(payType == PayType.WEIXINPAY.ordinal()){
					isTrue = WxRefundUtils.doRefundQuery(out_trade_no);
				}else if(payType == PayType.UNIONPAY.ordinal()){//银联退款通过退款异步通知回调来更新状态
					//isTrue = UnionRefundUtils.doRefundQuery(out_trade_no);
				}
				if(isTrue){//更新退款标示为已退款成功
					refundCommonDao.refundSuccess(item.getId(), source);
					
					LogDataBean logdata = new LogDataBean();
					logdata.setSource(source);
					logdata.setLogType(LogType.REFUND_SUCCESS.ordinal());
					logdata.setResourceID(orderID);
					
					logDataCommonDao.saveLogData(logdata);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}*/

	@Override
	public boolean doRefund(int orderID, APP app){
		try {
			int source = app.ordinal();
			OrdersBean orders = orderCommonDao.getOrders(orderID, source);
			if(orders != null){
				RefundBean item = refundCommonDao.queryRefundReason(orderID, source);
				if(item.getFlag() == 1){//防止重复调用
					return true;
				}
				
				refundCommonDao.refundSuccess(item.getId(), source);
				
				double refundFee = item.getAmount();
				if(!CommonUtils.formatMoney(orders.getActualPayment()).equals(CommonUtils.formatMoney(refundFee))){//不是全额退款，需要把剩余金额打到卖家账户
					double balance = Double.parseDouble(CommonUtils.formatMoney(orders.getActualPayment() - refundFee));//剩余金额
					//查询订单卖家
					int sellerUid = this.orderDetailCommonDao.getSellerUid(orderID, source);
					UserAccountBean userAccount = userAccountCommonDao.getUserAccountForUpdate(sellerUid, source);
					double oldbalance = 0;//原来的账号余额
					if(userAccount == null){//没有账户，先插入
						userAccount = new UserAccountBean();
						userAccount.setGuid(sellerUid);
						userAccount.setSource(source);
						userAccount.setBalance(balance);;
						userAccountCommonDao.saveUserAccount(userAccount);
					}else{//更新账户数据
						oldbalance = userAccount.getBalance();
						
						balance = Double.valueOf(CommonUtils.formatMoney(balance+oldbalance));
						
						int resCode = userAccountCommonDao.updateBalance(balance, sellerUid, source);
						if(resCode == 0){
							System.out.println("余额更新失败：orderID="+orderID+",oldbalance="+oldbalance+",newbalance="+balance);
							throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
						}
					}
					UserAccountTransactionLogBean accountLog = new UserAccountTransactionLogBean();
					accountLog.setSource(source);
					accountLog.setUserAccountID(userAccount.getUserAccountID());
					accountLog.setBalance(balance);
					accountLog.setCharge(+(Double.parseDouble(CommonUtils.formatMoney(orders.getActualPayment() - refundFee))));
					accountLog.setUserAccountTransactionType(UserAccountTransaction.PAY.ordinal());
					accountLog.setResourceID(orderID);
					accountLog.setRecordCode(orders.getOrderCode());
					userAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountLog);
				}
				
				LogDataBean logdata = new LogDataBean();
				logdata.setSource(source);
				logdata.setLogType(LogType.REFUND_SUCCESS.ordinal());
				logdata.setResourceID(orderID);
				
				logDataCommonDao.saveLogData(logdata);
			
				//保存退款资金流水记录
				moneyIOLogDao.saveMoneyIOLog(orders.getOrderPayCode(), refundFee, 0, orders.getGuid(), MoneyIOType.refund.ordinal(), orders.getPayType(), source);
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<RefundBean> getAliRefundOrders(APP app) {
		int source = app.ordinal();
		return refundCommonDao.getAliRefundOrders(source);
	}
}
