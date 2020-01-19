package com.wei.orderpay.service.order.impl;

import com.jiutong.common.BusinessException;
import com.jiutong.common.Result;
import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.bean.common.OrderPayResultCode;
import com.wei.orderpay.bean.log.LogDataBean;
import com.jiutong.orderpay.bean.order.*;
import com.wei.orderpay.bean.user.RecepientBean;
import com.wei.orderpay.bean.user.UserAccountBean;
import com.wei.orderpay.bean.user.UserAccountTransactionLogBean;
import com.wei.orderpay.contacts.common.APP;
import com.wei.orderpay.contacts.common.OrderReasonType;
import com.wei.orderpay.contacts.log.LogType;
import com.wei.orderpay.contacts.order.OpType;
import com.wei.orderpay.contacts.order.OrderStatus;
import com.wei.orderpay.contacts.order.OrderType;
import com.wei.orderpay.contacts.order.PayType;
import com.wei.orderpay.contacts.user.UserAccountTransaction;
import com.wei.orderpay.dao.log.LogDataCommonDao;
import com.jiutong.orderpay.dao.order.*;
import com.wei.orderpay.dao.user.RecepientCommonDao;
import com.wei.orderpay.dao.user.UserAccountCommonDao;
import com.wei.orderpay.dao.user.UserAccountTransactionLogCommonDao;
import com.wei.orderpay.pay.chinapay.ChinaPaySimpleDFUtils;
import com.wei.orderpay.pay.unionpay.UnionRefundUtils;
import com.wei.orderpay.pay.wx.WxRefundUtils;
import com.wei.orderpay.pay.wx.WxRefundUtilsH5;
import com.wei.orderpay.service.order.OrderCommonService;
import com.wei.orderpay.service.pay.PayService;
import com.wei.orderpay.service.pay.RefundService;
import com.wei.orderpay.bean.order.*;
import com.wei.orderpay.dao.order.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderCommonService")
public class OrderCommonServiceImpl implements OrderCommonService {

	@Resource
    OrderCommonDao orderCommonDao;

	@Resource
    OrderDetailCommonDao orderDetailCommonDao;

	@Resource
    OrderOperationLogCommonDao orderOperationLogCommonDao;

	@Resource
    OrderTransactionLogCommonDao orderTransactionLogCommonDao;

	@Resource
    OrderLogisticsCommonDao orderLogisticsCommonDao;

	@Resource
	RefundCommonDao refundCommonDao;
	
	@Resource
    PayService payService;
	
	@Resource
    RecepientCommonDao recepientCommonDao;
	
	@Resource
    LogDataCommonDao logDataCommonDao;
	
	@Resource
	OrdersReasonCommonDao ordersReasonCommonDao;
	
	@Resource
	OrderPayLogCommonDao orderPayLogCommonDao;

	@Resource
    UserAccountCommonDao userAccountCommonDao;
	
	@Resource
    UserAccountTransactionLogCommonDao userAccountTransactionLogCommonDao;
	
	@Resource
    RefundService refundService;
	
	@Resource
	ServiceRemitCommonDao serviceRemitCommonDao;
	
	@Resource
	OrderCommonService orderCommonService;
	
	@Override
	public Result saveOrders(List<OrdersBean> orders, String productName, String ip, APP app) {
		return saveOrdersCommon(orders,productName,ip,app, OrderType.APP,"",0);
	}

	@Override
	public Result saveOrdersH5(List<OrdersBean> orders,String productName,String ip,APP app,int shareId) {
		return saveOrdersCommon(orders,productName,ip,app,OrderType.H5,"",shareId);
	}

	@Override
	public Result saveOrdersH5WX(List<OrdersBean> orders,String productName,String ip,APP app,String openID,int shareId) {
		return saveOrdersCommon(orders,productName,ip,app,OrderType.H5,openID,shareId);
	}

	private Result saveOrdersCommon(List<OrdersBean> orders,String productName,String ip,APP app,int type,String openID,int shareId) {
		if (orders == null || orders.isEmpty()) {
			throw new BusinessException(OrderPayResultCode.ORDER_ISNOT_EMPTY);
		}

		int firstOrderID=0;

		double amount = 0;
		int source = app.ordinal();
		// 保存订单，
		String orderPayCode = createOrderCode();

		for (OrdersBean order : orders) {
			if(type ==0) order.setType(OrderType.APP);
			if(type ==1) order.setType(OrderType.H5);
			order.setSource(source);
		 	order.setStatus(OrderStatus.noPay.ordinal());
			order.setOrderPayCode(orderPayCode); // 支付订单号
			orderCommonDao.saveOrders(order);

			//保存子订单 并计算邮费
			List<OrderDetailBean> orderDetails = order.getOrderDetails();
			double postage = 0d;
			for (OrderDetailBean orderDetail : orderDetails) {
				orderDetail.setSource(source);
				orderDetail.setPromoteId(shareId);
				orderDetail.setOrderID(order.getOrderID());
				orderDetailCommonDao.saveOrderDetail(orderDetail);
				postage += orderDetail.getPostage();
			}

			if (firstOrderID == 0) {
				firstOrderID = order.getOrderID();
			}
			amount += order.getAmount(); // 计算订单总金额;
//			amount += postage; //计算邮费

			/**
			 * 更新最后发起支付时间
			 */
			if(orderCommonDao.updateOrderLastPayDT(order.getOrderID()) == 0){
				throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
			}
			saveOrderLog(order.getOrderID(), order.getGuid(), OpType.buy,
					OrderStatus.noPay, order.getSource());
		}
		amount = Double.parseDouble(CommonUtils.formatMoney(amount));
		Result result = new Result();
		String payParams = null;
		// 获得订单支付方式
		int payType = orders.get(0).getPayType();
		if(PayType.ALIPAY.ordinal() == payType){
            if(type ==0)  payParams = payService.buildAliPayParams(productName, amount, orderPayCode);
            if(type ==1)  payParams = payService.buildAliPayParamsNoBase64(productName, amount, orderPayCode);//H5
		} else if (PayType.WEIXINPAY.ordinal() == payType) {
			if(type ==0) payParams = payService.buildWXPayParams(productName, amount, orderPayCode, ip);
			if(type ==1) payParams = payService.buildWXPayParamsH5(productName, amount, orderPayCode, ip,openID);//H5
		} else if (PayType.UNIONPAY.ordinal() == payType) {
			payParams = payService.buildUnionPayParams(productName, amount, orderPayCode);
		}

		JSONObject json = new JSONObject();

		json.put("orderPayCode", orderPayCode);
		json.put("orderID", firstOrderID);
		json.put("payType", payType);
		json.put("orderCode", orders.get(0).getOrderCode());
		json.put("orderDT", CommonUtils.getDateString("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()));
		json.put("payParams", payParams);
		result.setData(json);
		return result;
	}

	@Override
	public Result doNoPayOrder(int guid,int orderID,String productName,int payType,String ip,APP app) {
		return doNoPayOrderCommon(guid,orderID,productName,payType,ip,app,OrderType.APP,"");
	}

	@Override
	public Result doNoPayOrderH5(int guid,int orderID,String productName,int payType,String ip,APP app) {
		return doNoPayOrderCommon(guid,orderID,productName,payType,ip,app,OrderType.H5,"");
	}

	@Override
	public Result doNoPayOrderH5WX(int guid,int orderID,String productName,int payType,String ip,APP app,String openID) {
		return doNoPayOrderCommon(guid,orderID,productName,payType,ip,app,OrderType.H5,openID);
	}

	private Result doNoPayOrderCommon(int guid,int orderID,String productName,int payType,String ip,APP app,int type,String openID) {
		int source = app.ordinal();
		OrdersBean order = orderCommonDao.getOrders(orderID, source);
		if (order == null) {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_EMPTY);
		}
		if(guid != order.getGuid()){
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}
		double amount = order.getActualPayment();//实际支付金额
		amount = Double.parseDouble(CommonUtils.formatMoney(amount));
		String orderPayCode = createOrderCode();
		
		/**
		 * 更新支付订单号(可能会出现提交的一批订单未支付的情况，需要重新生成支付订单号)
		 */
		int resCode = orderCommonDao.updateOrderPayCode(orderID, source, OrderStatus.noPay.ordinal(), orderPayCode,payType);
		if(resCode == 0){
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
		}

		/**
		 * 更新最后发起支付时间
		 */
		if(orderCommonDao.updateOrderLastPayDT(orderID) == 0){
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
		}
		saveOrderOpLog(orderID, order.getGuid(), OpType.buy, source);
		
		Result result = new Result();
		String payParams = null;
		if(PayType.ALIPAY.ordinal() == payType){
			if(type ==0)  payParams = payService.buildAliPayParams(productName, amount, orderPayCode);
			if(type ==1)  payParams = payService.buildAliPayParamsNoBase64(productName, amount, orderPayCode);//H5
		} else if (PayType.WEIXINPAY.ordinal() == payType) {
			if(type ==0) payParams = payService.buildWXPayParams(productName, amount, orderPayCode, ip);
			if(type ==1) payParams = payService.buildWXPayParamsH5(productName, amount, orderPayCode, ip,openID);//H5
		} else if (PayType.UNIONPAY.ordinal() == payType) {
			payParams = payService.buildUnionPayParams(productName, amount, orderPayCode);
		}

		JSONObject json = new JSONObject();
		json.put("payType", payType);
		json.put("orderCode", order.getOrderCode());
		json.put("orderDT", CommonUtils.getDateString("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()));
		json.put("payParams", payParams);
		result.setData(json);
		return result;
	}
	
	/**
	 * 创建支付订单号
	 * 
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月18日 上午10:50:50
	 */
	private String createOrderCode() {
		String code = CommonUtils.getDateString("MMddHHmmss",
				System.currentTimeMillis())
				+ "" + CommonUtils.getRandomNumber(10000, 99999);
		return code;
	}

	/**
	 * 订单一切操作，都要有日志记录
	 * @param orderID
	 * @param userID
	 * @param opType
	 * @param orderStatus
	 * @param source
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 上午11:27:07
	 */
	@Override
	public void saveOrderLog(int orderID, int userID, OpType opType,
			OrderStatus orderStatus, int source) {
		OrderTransactionLogBean orderTransactionLog = new OrderTransactionLogBean();
		orderTransactionLog.setOrderID(orderID);
		orderTransactionLog.setStatus(orderStatus.ordinal());
		orderTransactionLog.setSource(source);
		this.orderTransactionLogCommonDao
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
		this.orderOperationLogCommonDao
				.saveOrderOperationLog(orderOperationLog);
	}

	@Override
	public Result getBuyerOrderList(int buyerUID, int status,int page,int pageSize ,APP app) {
		int source = app.ordinal();
		List<OrdersBean> resList = orderCommonDao.getBuyerOrderList(buyerUID,source, status, page , pageSize);
		for (OrdersBean item : resList) {
			int orderID = item.getOrderID();
			List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(orderID, source);
			item.setOrderDetails(orderDetails);
		}
		Result result = new Result();
		result.setData(resList);
		return result;
	}

	@Override
	public Result getSellerOrderList(int sellerUID, int status,int page,int pageSize , APP app) {
		int source = app.ordinal();
		Result result = new Result();
		List<Integer> orderIDs = orderDetailCommonDao.getSellerOrderList(sellerUID, source);
		if (!orderIDs.isEmpty()) {
			List<OrdersBean> resList = orderCommonDao.getSellerOrderList(
					orderIDs, source, status , page , pageSize);
			for (OrdersBean item : resList) {
				int orderID = item.getOrderID();
				List<OrderDetailBean> orderDetails = orderDetailCommonDao
						.getOrderDetails(orderID, source);
				item.setOrderDetails(orderDetails);
			}
			result.setData(resList);
		}
		return result;
	}

	@Override
	public Result getOrderDetail(int orderID, APP app) throws BusinessException {
		int source = app.ordinal();
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
		if (orders == null) {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_DELETE);
		}

		List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(orderID, source);
		orders.setOrderDetails(orderDetails);

		List<OrderTransactionLogBean> orderTransactionLogs = orderTransactionLogCommonDao.getOrderTransactionLogs(orderID, source);
		for (OrderTransactionLogBean orderTransactionLogBean : orderTransactionLogs) {
			orderTransactionLogBean.setCreateDT(orderTransactionLogBean.getCreateDT().substring(0, orderTransactionLogBean.getCreateDT().length()-2)); 
		}
		orders.setOrderTransactionLogs(orderTransactionLogs);

		RecepientBean recepient = recepientCommonDao.getRecepientByID(orders.getRecepientID(),app.ordinal());
		orders.setRecepient(recepient);
		
		Result result = new Result();
		result.setData(orders);
		return result;
	}

	@Override
	public Result getOrderDetailByOrderCode(String orderCode, APP app) throws BusinessException {
		int source = app.ordinal();
		OrdersBean orders = orderCommonDao.getOrdersByOrderCode(orderCode, source);
		if (orders == null) {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_DELETE);
		}

		List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(orders.getOrderID(), source);
		orders.setOrderDetails(orderDetails);

		List<OrderTransactionLogBean> orderTransactionLogs = orderTransactionLogCommonDao.getOrderTransactionLogs(orders.getOrderID(), source);
		for (OrderTransactionLogBean orderTransactionLogBean : orderTransactionLogs) {
			orderTransactionLogBean.setCreateDT(orderTransactionLogBean.getCreateDT().substring(0, orderTransactionLogBean.getCreateDT().length()-2));
		}
		orders.setOrderTransactionLogs(orderTransactionLogs);

		RecepientBean recepient = recepientCommonDao.getRecepientByID(orders.getRecepientID(),app.ordinal());
		orders.setRecepient(recepient);

		Result result = new Result();
		result.setData(orders);
		return result;
	}

	@Override
	public int doCancelOrders(int orderID, int buyerUID, APP app) {
		int source = app.ordinal();
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);

		if (orders.getGuid() != buyerUID) {
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}

		int status = orders.getStatus();
		// 待付款才可以取消订单
		if (status == OrderStatus.noPay.ordinal()) {
			int resCode = orderCommonDao.updateOrderStatus(orderID, source,
					buyerUID, OrderStatus.cancel.ordinal(), status);
			if (resCode > 0) {// 更新成功,记录日志
				saveOrderLog(orderID, buyerUID, OpType.cancel,
						OrderStatus.cancel, source);
			} else {
				throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
			}
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_SEND);
		}
		return 1;
	}

	/**
	 * 退款
	 * @param orders
	 *
	 * @author : weihui
	 * @createTime : 2016年5月3日 下午6:50:55
	 */
	private void refund(OrdersBean orders,APP app) {
		int payType = orders.getPayType();
		int source = orders.getSource();
		LogDataBean logdata = new LogDataBean();
		logdata.setSource(source);
		logdata.setLogType(LogType.REFUND_REQ.ordinal());
		logdata.setResourceID(orders.getOrderID());
		
		logDataCommonDao.saveLogData(logdata);
		try {
			int orderID = orders.getOrderID();
			RefundBean queryRefundReason = this.refundCommonDao.queryRefundReason(orderID, source);
			
			double refundFee = queryRefundReason.getAmount(); //退款金额
			String orderPayCode = orders.getOrderPayCode();//支付订单号(传给支付平台的订单号)
			boolean isSuccess = false;
			if(payType == PayType.WEIXINPAY.ordinal()){//微信退款
				double totalAmount = getTotalAmountByOrderPayCode(orders.getOrderPayCode(),app);
				if(orders.getType() == OrderType.H5){
					isSuccess = WxRefundUtilsH5.doRefund(orderPayCode, orders.getOrderID() + "", totalAmount, refundFee);
				}else{
					isSuccess = WxRefundUtils.doRefund(orderPayCode, orders.getOrderID() + "", totalAmount, refundFee);
				}
			}else if(payType == PayType.ALIPAY.ordinal()){//支付宝退款
				isSuccess = true;//支付宝需要手动页面操作，才能退款
			}else if(payType == PayType.UNIONPAY.ordinal()){//银联退款
				String tradeNo = orderPayLogCommonDao.getTradeNo(orderPayCode, source);
				isSuccess = UnionRefundUtils.doRefund(orderID + "",tradeNo, refundFee);
			}
			if(isSuccess){
				logdata.setLogType(LogType.REFUND_REQ_SUCCESS.ordinal());
				logDataCommonDao.saveLogData(logdata);
				
				if(payType == PayType.WEIXINPAY.ordinal()){//微信退款,没有异步回调通知，直接默认为退款成功，应该写退款查询来确认退款申请成功
					refundService.doRefund(orders.getOrderID(), app);
				}
			}else{
				throw new BusinessException(OrderPayResultCode.REFUND_FAILED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(OrderPayResultCode.REFUND_FAILED);
		}
	}

	/**
	 * 获取该支付订单号全部全部支付金额
	 * @param orderPayCode
	 * @param app
	 * @return
	 * @author tankai
	 * @createTime : 2016年6月12日 上午10:52:11
	 * @version : 1.4
	 */
	private double getTotalAmountByOrderPayCode(String orderPayCode, APP app) {
		Map<String, Object> map =new  HashMap<String, Object>();
		map.put("orderPayCode", orderPayCode);
		map.put("source",app.ordinal());
		return 	orderCommonDao.getTotalAmountByOrderPayCode(map);
	}

	@Override
	public int updateOrderPrice(int orderID, int sellerUID,
			double actualPayment, APP app) {
		int source = app.ordinal();
		List<OrderDetailBean> orderDetails = orderDetailCommonDao
				.getOrderDetails(orderID, source);
		if (orderDetails.isEmpty()) {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_EMPTY);
		}
		if (orderDetails.get(0).getGuid() != sellerUID) {
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
		if (orders.getStatus() != OrderStatus.noPay.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_UPDATE_ERROR);
		}
		int resCode = orderCommonDao.updateOrderPrice(orderID, source,
				OrderStatus.noPay.ordinal(), actualPayment);
		if (resCode > 0) {// 更新成功,记录日志
			saveOrderOpLog(orderID, sellerUID, OpType.modifyPrice, source);
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		return 1;
	}

	@Override
	public Result doGetGoods(int orderID, int buyerUID, APP app) {
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();
	
		int source = app.ordinal();
		OrdersBean orders = orderCommonDao.getOrders(orderID, source); // 获取订单详情
		int status = orders.getStatus();				//订单状态
		if (orders.getGuid() != buyerUID) {
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}
		if (status != OrderStatus.waitGoodsReceipt.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
		}
		int resCode = orderCommonDao.updateOrderStatus(orderID, source,
				buyerUID, OrderStatus.receipted.ordinal(), status); //修改订单状态
		if (resCode > 0) {// 更新成功,记录日志
			
			//查询订单卖家
			int sellerUid = this.orderDetailCommonDao.getSellerUid(orderID, source);
			UserAccountBean userAccount = userAccountCommonDao.getUserAccountForUpdate(sellerUid, source);
			double balance = orders.getActualPayment();
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
				
				resCode = userAccountCommonDao.updateBalance(balance, sellerUid, source);
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
			accountLog.setRecordCode(orders.getOrderCode());
			userAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountLog);

			
			saveOrderLog(orderID, buyerUID, OpType.getGoods,
					OrderStatus.receipted, source);
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		
		int sellerUid = orderDetailCommonDao.getSellerUid(orderID, source); //查询卖家ID
		
		map.put("sellerUID", String.valueOf(sellerUid));
		map.put("orderCode", orders.getOrderCode());
		result.setData(map);
		return result;
	}

	@Override
	public int deleteOrdersByBuyer(int orderID, int buyerUID, APP app) {
		int source = app.ordinal();
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
		int status = orders.getStatus();
		// 已取消和已完成的订单才可以删除
		if (status != OrderStatus.cancel.ordinal()
				&& status != OrderStatus.receipted.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_CANNOT_DELETE);
		}

		int resCode = orderCommonDao.deleteOrdersByBuyer(orderID, buyerUID, source);
		if (resCode > 0) {// 更新成功,记录日志
			saveOrderOpLog(orderID, buyerUID, OpType.deleteOrderByBuyer, source);
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		return 1;
	}
	
	@Override
	public int deleteOrdersBySeller(int orderID, int sellerUID, APP app) {
		int source = app.ordinal();
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
		int status = orders.getStatus();
		// 已取消和已完成的订单才可以删除
		if (status != OrderStatus.cancel.ordinal()
				&& status != OrderStatus.receipted.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_CANNOT_DELETE);
		}

		int sellerUid = orderDetailCommonDao.getSellerUid(orderID, source);
		if(sellerUID != sellerUid){
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}
		
		int resCode = orderCommonDao.deleteOrdersBySeller(orderID, source);
		if (resCode > 0) {// 更新成功,记录日志
			saveOrderOpLog(orderID, sellerUID, OpType.deleteOrderBySeller, source);
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		return 1;
	}

	@Override
	public Result doDeliverGoods(int orderID, int sellerUID, String deliveryNo,
			String voucherPics, APP app) {
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();
		
		int source = app.ordinal();
		List<OrderDetailBean> orderDetails = orderDetailCommonDao
				.getOrderDetails(orderID, source);
		if (orderDetails.isEmpty()) {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_EMPTY);
		}
		if (orderDetails.get(0).getGuid() != sellerUID) {
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
		int status = orders.getStatus();
		if (status != OrderStatus.payWaitDelivery.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
		}
		int buyerUID = orders.getGuid();

		OrderLogisticsBean logistics = new OrderLogisticsBean();
		logistics.setOrderID(orderID);
		logistics.setSource(source);
		logistics.setGuid(sellerUID);
		if (CommonUtils.isNotEmpty(deliveryNo)) {
			logistics.setDeliveryNo(deliveryNo);
		}
		if (CommonUtils.isNotEmpty(voucherPics)) {
			logistics.setVoucherPics(voucherPics);
		}
		orderLogisticsCommonDao.saveOrderLogistics(logistics); 	// 保存物流
		
		int resCode = orderCommonDao.updateOrderStatus(orderID, source,
				buyerUID, OrderStatus.waitGoodsReceipt.ordinal(), status);
		if (resCode > 0) {// 更新成功,记录日志
			saveOrderLog(orderID, sellerUID, OpType.deliverGoods,
					OrderStatus.waitGoodsReceipt, source);
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		map.put("buyerUID", String.valueOf(buyerUID));
		map.put("orderCode", orders.getOrderCode());
		result.setData(map);
		return result;
	}

	@Override
	public Result doApplicationRefund(int orderID, int buyerUID, double amount,
			int refundType, int goodStatus, String reason, String voucherPics,
			APP app) {
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();
		
		int source = app.ordinal();
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
		if (amount - orders.getActualPayment() > 0.00000001) {
			throw new BusinessException(OrderPayResultCode.REFUND_AMOUNT_ERROR);
		}
		int status = orders.getStatus();
		//待发货和待收货都可以申请退款
		if (status != OrderStatus.payWaitDelivery.ordinal() && status != OrderStatus.waitGoodsReceipt.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
		}
		RefundBean refund = new RefundBean();
		refund.setOrderID(orderID);
		refund.setGuid(buyerUID);
		refund.setSource(source);
		refund.setAmount(amount);
		refund.setRefundType(refundType);
		refund.setGoodStatus(goodStatus);
		if (CommonUtils.isNotEmpty(reason)) {
			refund.setReason(reason);
		}
		if (CommonUtils.isNotEmpty(voucherPics)) {
			refund.setVoucherPics(voucherPics);
		}
		refundCommonDao.saveRefund(refund);// 申请退款

		int resCode = orderCommonDao.updateOrderStatus(orderID, source,
				buyerUID, OrderStatus.applicationRefund.ordinal(), status);
		if (resCode > 0) {// 更新成功,记录日志
			saveOrderLog(orderID, buyerUID, OpType.applicationRefund,
					OrderStatus.applicationRefund, source);
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		
		int sellerUid = orderDetailCommonDao.getSellerUid(orderID, source);
		map.put("sellerUID", String.valueOf(sellerUid));
		map.put("orderCode", orders.getOrderCode());
		result.setData(map);
		return result;
	}

	@Override
	public Result doAgreeRefund(int orderID, int sellerUID, APP app) {
		Result result = new Result();
		Map<String,String> map = new HashMap<String,String>();
		
		int source = app.ordinal();
		List<OrderDetailBean> orderDetails = orderDetailCommonDao
				.getOrderDetails(orderID, source);
		if (orderDetails.isEmpty()) {
			throw new BusinessException(OrderPayResultCode.ORDER_IS_EMPTY);
		}
		if (orderDetails.get(0).getGuid() != sellerUID) {
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
		int status = orders.getStatus();
		if (status != OrderStatus.applicationRefund.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR);
		}
		RefundBean refund = new RefundBean();
		refund.setOrderID(orderID);
		refund.setGuid(orders.getGuid());
		refund.setSource(source);
		refund.setAgreeDT(CommonUtils.getDateString("yyyy-MM-dd HH:mm:ss",
				System.currentTimeMillis()));
		refundCommonDao.agreeRefund(refund);// 同意退款

		// 退款
		refund(orders,app);

		int resCode = orderCommonDao.updateOrderStatus(orderID, source,
				orders.getGuid(), OrderStatus.refunded.ordinal(), status);
		if (resCode > 0) {// 更新成功,记录日志
			saveOrderLog(orderID, sellerUID, OpType.refund,
					OrderStatus.refunded, source);
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		
		map.put("buyerUID", String.valueOf(orders.getGuid()));
		map.put("orderCode", orders.getOrderCode());
		result.setData(map);
		
		return result;
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
		
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
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

		int resCode = orderCommonDao.updateOrderStatus(orderID, source,
				orders.getGuid(), OrderStatus.arbitrationed.ordinal(), status);
		if (resCode > 0) {// 更新成功,记录日志
			saveOrderLog(orderID, customerID, OpType.rejectRefund,
					OrderStatus.arbitrationed, source);
			
			//查询订单卖家
			int sellerUid = this.orderDetailCommonDao.getSellerUid(orderID, source);
			UserAccountBean userAccount = userAccountCommonDao.getUserAccountForUpdate(sellerUid, source);
			double balance = orders.getActualPayment();
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
				
				resCode = userAccountCommonDao.updateBalance(balance, sellerUid, source);
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
			accountLog.setRecordCode(orders.getOrderCode());
			userAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountLog);
			
		} else {
			throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
		}
		
		map.put("buyerUID", String.valueOf(orders.getGuid()));
		map.put("orderCode", orders.getOrderCode());
		result.setData(map);
		return result;
	}

	@Override
	public int getMyOrderCount(int guid, APP app) {
		return orderCommonDao.getMyOrderCount(guid, app.ordinal());
	}

	@Override
	public void doFeedBackCause(int orderID, int guid, String cause, APP app,
			OrderReasonType type) {
		
		int source = app.ordinal();
		
		OrdersBean order = orderCommonDao.getOrders(orderID, source);
		
		// 订单校验
		if (order.getStatus() != OrderStatus.cancel.ordinal()) {
			throw new BusinessException(OrderPayResultCode.ORDER_STATUS_ERROR); 
		} else if(order.getGuid() != guid){
			throw new BusinessException(OrderPayResultCode.IS_NOT_MY_ORDER);
		}else{
			//校验通过
			OrdersReason ordersReason = new OrdersReason();
			ordersReason.setOrderID(orderID);
			ordersReason.setSource(source);
			ordersReason.setReasonType(type.ordinal());
			ordersReason.setReason(cause);
			
			ordersReasonCommonDao.saveFeedBackCause(ordersReason);
		}
		
	}

	@Override
	public List<Map<String,String>> getOrderByOrderPayCode(String orderPayCode, APP app) {
		
		int source = app.ordinal();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		List<OrdersBean> orderByOrderPayCode = orderCommonDao.getOrderByOrderPayCode(orderPayCode, source);
		
		for (OrdersBean ordersBean : orderByOrderPayCode) {
			
			Map<String,String> map = new HashMap<String, String>();
			int sellerUid = orderDetailCommonDao.getSellerUid(ordersBean.getOrderID(), source); //查询卖家ID
			map.put("sellerUid", String.valueOf(sellerUid));
			map.put("orderCode", ordersBean.getOrderCode());
			list.add(map);
		}
		return list;
	}

	
	
	
	@Override
	public OrdersBean getOrderByOrderCode(String orderCode, APP app) {
		
		OrdersBean ordersBean = orderCommonDao.getOrdersByOrderCode(orderCode, app.ordinal());
		if (ordersBean != null) {
			ordersBean.setCreateDT(ordersBean.getCreateDT().substring(0, ordersBean.getCreateDT().length()-2));
			RecepientBean recepient = recepientCommonDao.getRecepientByID(ordersBean.getRecepientID(), app.ordinal());  
			ordersBean.setRecepient(recepient); // 收货地址装箱
			
			List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(ordersBean.getOrderID(), app.ordinal());
			ordersBean.setOrderDetails(orderDetails);
		}
		
		return ordersBean;
	}

	@Override
	public List<OrdersBean> getOrderByBuyerGuid4Manage(List<Integer> guids ,String status ,String createStartDT ,String createEndDT ,int source ,int page ,int pageSize){
		List<OrdersBean> ordersBeans = new ArrayList<OrdersBean>();
		if (!guids.isEmpty()) {
			ordersBeans = orderCommonDao.getBuyerOrderList4Buyer(guids ,status ,createStartDT ,createEndDT , source ,page ,pageSize);
		}
		if (ordersBeans != null) {
			for (OrdersBean ordersBean : ordersBeans) {
				ordersBean.setCreateDT(ordersBean.getCreateDT().substring(0, ordersBean.getCreateDT().length()-2)); 
				List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(ordersBean.getOrderID(), source);
				ordersBean.setOrderDetails(orderDetails);
			}
		}
		
		return ordersBeans;
	}
	
	@Override
	public List<OrdersBean> getOrderBysellererGuid4Manage(List<Integer> guids, String status,String createStartDT, String createEndDT, int source, int page,int pageSize) {
		List<OrdersBean> ordersBeans = new ArrayList<OrdersBean>();
		if (!guids.isEmpty()) {
			ordersBeans = orderCommonDao.getBuyerOrderList4Seller(guids ,status ,createStartDT ,createEndDT , source ,page ,pageSize);
		}
		if (ordersBeans != null) {
			for (OrdersBean ordersBean : ordersBeans) {
				ordersBean.setCreateDT(ordersBean.getCreateDT().substring(0, ordersBean.getCreateDT().length()-2)); 
				List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(ordersBean.getOrderID(), source);
				ordersBean.setOrderDetails(orderDetails);
			}
		}
		return ordersBeans;
	}
	
	@Override
	public List<OrdersBean> getOrderByNull4Manage(String status,String createStartDT, String createEndDT, int source, int page,int pageSize) {
		
		List<OrdersBean> ordersBeans = orderCommonDao.getOrderByNull4Manage(status ,createStartDT ,createEndDT , source ,page ,pageSize);
		
		if (ordersBeans != null) {
			for (OrdersBean ordersBean : ordersBeans) {
				ordersBean.setCreateDT(ordersBean.getCreateDT().substring(0, ordersBean.getCreateDT().length()-2)); 
				List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(ordersBean.getOrderID(), source);
				ordersBean.setOrderDetails(orderDetails);
			}
		}
		return ordersBeans;
	}

	@Override
	public OrderLogisticsBean getOrderLogisticsByOrderID(int orderID, APP app) {
		
		return orderLogisticsCommonDao.getOrderLogisticsByOrderID(orderID, app.ordinal());
	}

	@Override
	public List<OrdersBean> getOrdersUnpaidOver24h(APP app) {
		return orderCommonDao.getOrdersUnpaidOver24h(OrderStatus.noPay.ordinal(),app.ordinal());
	}

	@Override
	public int getSellerUid(int orderID, APP app) {
		return orderDetailCommonDao.getSellerUid(orderID,app.ordinal());
	}

	@Override
	public String getOrderCodesByStatusAndFollowMark(int followMark ,int source) {
		List<Integer> statuses = new ArrayList<Integer>();  
		statuses.add(OrderStatus.payWaitDelivery.ordinal());
		statuses.add(OrderStatus.waitGoodsReceipt.ordinal());
		statuses.add(OrderStatus.applicationRefund.ordinal());  //待收货，代发货 ，退款
		
		String orderCode = orderCommonDao.getOrderCodesByStatusAndFollowMark(statuses, followMark, source, 1, 1);
		Integer update = orderCommonDao.updateFollowMarkByOrderCode(orderCode);
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
		
		orderCommonDao.updateFollowMark4Quartz(statuses);
		
	}

	@Override
	public Result doWarnDelivery(int orderID,int buyerUID, APP app) {
		int source = app.ordinal();
		orderCommonDao.updateOrderStatusDT(orderID, buyerUID,OrderStatus.payWaitDelivery.ordinal(), source);
		
		OrdersBean orders = orderCommonDao.getOrders(orderID, source);
		String orderCode = orders.getOrderCode();
		
		int sellerUid = orderDetailCommonDao.getSellerUid(orderID,source);
		
		Result result = new Result();
		JSONObject data = new JSONObject();
		data.put("orderCode", orderCode);
		data.put("sellerUid", sellerUid);
		result.setData(data);
		return result;
	}
	
	
	
	//=================================================================================业务订单相关处理  begin  别动 端午后 此部分要提出===================================================//
	
	@Override
	public ServiceOrderBean getServiceOrderByOrderCode(String keyword,int accountStatus, String createStartDT, String createEndDT, APP app) {
		OrdersBean ordersBean = orderCommonDao.getOrdersByOrderCode(keyword, app.ordinal());
		
		if (ordersBean == null) {
			return null;
		}
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orderID", ordersBean.getOrderID());
		params.put("accountStatus", accountStatus);
		params.put("source", app.ordinal());
		params.put("createStartDT", createStartDT);
		params.put("createEndDT", createEndDT);
		
		ServiceOrderBean serviceOrderBean = orderCommonDao.getServiceOrderByOrderCode(params);   
		
		if (serviceOrderBean != null) {
			serviceOrderBean.setCreateDT(serviceOrderBean.getCreateDT().substring(0, serviceOrderBean.getCreateDT().length()-2));  //时间处理
			RecepientBean recepient = recepientCommonDao.getRecepientByID(serviceOrderBean.getRecepientID(), app.ordinal());  
			serviceOrderBean.setRecepient(recepient); // 收货地址装箱
			
			List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(serviceOrderBean.getOrderID(), app.ordinal());
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
            serviceOrderList = orderCommonDao.getServiceOrders(params) ;
        }
        if (serviceOrderList != null) {
            for (ServiceOrderBean serviceOrderBean : serviceOrderList) {
                serviceOrderBean.setCreateDT(serviceOrderBean.getCreateDT().substring(0, serviceOrderBean.getCreateDT().length()-2)); 
                List<OrderDetailBean> orderDetails = orderDetailCommonDao.getOrderDetails(serviceOrderBean.getOrderID(), source);
                serviceOrderBean.setOrderDetails(orderDetails);
            }
        }
        return serviceOrderList;
    }

    @Override
    public List<ServiceRemit> getServiceRemitListByOrderID(int orderID ,APP app) {
        return serviceRemitCommonDao.getServiceRemitListByOrderID(orderID,app.ordinal()) ;
    }

    @Override
    public int saveServiceRemit(ServiceRemit item ,int guid, APP app) {
        // 保存业务订单 添加一条提现记录
        int source = app.ordinal();
        UserAccountBean userAccount = userAccountCommonDao.getUserAccountForUpdate(guid, app.ordinal());
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
        int resCode = userAccountCommonDao.updateBalance(balance, guid, source);
        if(resCode == 0){
			System.out.println("余额更新失败：guid="+guid+",balance="+balance);
			throw new BusinessException(OrderPayResultCode.WITHDRAWCASH_ERROR);
		}
        
        int id = serviceRemitCommonDao.saveServiceRemit(item) ;
        Map<String, Object> paras = new HashMap<String, Object>() ;
        paras.put("orderID", item.getOrderID()) ;
        paras.put("source", source) ;
        // 每次提交 都校验一下状态
        int count = serviceRemitCommonDao.getNotAddCount(paras) ;
        if(count == 0){
            paras.put("status", 1) ;
            serviceRemitCommonDao.updateAccountStatus(paras) ;
        }
        
        //账户操作日志记录
        UserAccountTransactionLogBean accountTransactionLog = new UserAccountTransactionLogBean();
        accountTransactionLog.setUserAccountID(userAccountID);
        accountTransactionLog.setSource(source);
        accountTransactionLog.setUserAccountTransactionType(UserAccountTransaction.REMITTANCE.ordinal());
        accountTransactionLog.setResourceID(id);
        accountTransactionLog.setBalance(balance);
        accountTransactionLog.setCharge(-item.getTotalAmount());
        accountTransactionLog.setRecordCode(tradeNo);
        
        userAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountTransactionLog);
        
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
        
        return serviceRemitCommonDao.getServiceRemitListByParas(paras) ;
    }
    
    @Override
	public void doServiceRemit(int id, APP app) {
    	int source = app.ordinal();
		//日志记录
		LogDataBean logdata = new LogDataBean();
		logdata.setSource(source);
		logdata.setLogType(LogType.SERVICE_REMIT_REQ.ordinal());
		logdata.setResourceID(id);
		logDataCommonDao.saveLogData(logdata);
		
		//业务汇款表数据读取
		ServiceRemit serviceRemit = serviceRemitCommonDao.getServiceRemitByID(id, source);
		if (serviceRemit != null) {
			
			// 修改业务汇款表状态
			int updateCode= serviceRemitCommonDao.updateServiceRemit4Success(id, source);
			if (updateCode==0) { // 重复汇款了
				throw new BusinessException(OrderPayResultCode.SERVICE_REMIT_ERROR);
			}
			boolean flag = ChinaPaySimpleDFUtils.doDF(serviceRemit.getTradeNo(), serviceRemit.getTotalAmount(), serviceRemit.getCardNo(), serviceRemit.getSupplierName(), serviceRemit.getBankName(), serviceRemit.getProvinceName(), serviceRemit.getCityName());
			if (!flag) { //汇款失败
				serviceRemitCommonDao.updateServiceRemit4Fail(id, source);
				logdata.setLogType(LogType.SERVICE_REMIT_FAILED.ordinal());
				logDataCommonDao.saveLogData(logdata);//日志记录
			} else { //汇款成功
				logdata.setLogType(LogType.SERVICE_REMIT_SUCCESS.ordinal());
				logDataCommonDao.saveLogData(logdata);//日志记录
			}
		}
	}

	public int getSellerFinishedOrderCount(int uid, APP app) {
		 Map<String, Object> paras = new HashMap<String, Object>() ;
		 paras.put("uid", uid);
		 paras.put("source", app.ordinal());
		return orderCommonDao.getSellerFinishedOrderCount(paras);
	}

	public Double getSellerFinishedOrderTotalPrice(int uid, APP app) {
		Map<String, Object> paras = new HashMap<String, Object>() ;
		 paras.put("uid", uid);
		 paras.put("source", app.ordinal());
		return orderCommonDao.getSellerFinishedOrderTotalPrice(paras);
	}

	@Override
	public List<OrderLogisticsBean> getOrderLogisticList(int orderID) {
		return orderLogisticsCommonDao.getOrderLogisticList(orderID);
	}

	@Override
	public int updateOrderLogistic(OrderLogisticsBean logisticsBean) {
		 return orderLogisticsCommonDao.updateOrderLogistics(logisticsBean);
	}

	/**
	 * 通过promoteId shareId 查询订单数
	 *
	 * @param shareId
	 * @return
	 */
	@Override
	public int getOrderCountByShareId(int shareId,int status) {
		return orderCommonDao.getOrderCountByShareId(shareId,status);
	}

	//=================================================================================业务订单相关处理  begin=====================================================//
	
	
	
}

















