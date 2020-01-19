package com.wei.orderpay.service.order;

import com.jiutong.common.BusinessException;
import com.jiutong.common.Result;
import com.jiutong.orderpay.bean.order.*;
import com.wei.orderpay.bean.order.*;
import com.wei.orderpay.contacts.common.APP;
import com.wei.orderpay.contacts.common.OrderReasonType;
import com.wei.orderpay.contacts.order.OpType;
import com.wei.orderpay.contacts.order.OrderStatus;

import java.util.List;
import java.util.Map;

/**
 * 订单相关service
 * 
 * @author : weihui
 * @createTime : 2016年4月12日 下午2:53:33
 * @version : 1.0
 * @description :
 *
 */
public interface OrderCommonService {
	
	/**
	 * 订单详情
	 * @param orderID
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午3:00:20
	 */
	Result getOrderDetail(int orderID, APP app);

	/**
	 * 获取订单详情 通过订单号
	 * @param orderCode
	 * @param app
	 * @return
	 * @throws BusinessException
	 */
	public Result getOrderDetailByOrderCode(String orderCode, APP app) throws BusinessException;
	
	/**
	 * 买家的订单列表
	 * @param guid
	 * @param status 状态从头1开始，查询全部传入0即可
	 * @param app 买家uid
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 上午11:49:48
	 */
	Result getBuyerOrderList(int guid,int status,  int page, int pageSize ,APP app);
	
	/**
	 * 卖家的订单列表
	 * @param guid 卖家uid
	 * @param status 状态从头1开始，查询全部传入0即可
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午12:05:31
	 */
	Result getSellerOrderList(int guid,int status, int page, int pageSize ,APP app);

	/**
	 * 提交订单
	 * 
	 * @param orders
	 * @param productName
	 * @param ip 微信支付需要的参数，其他支付方式可传入null
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午2:35:04
	 */
	Result saveOrders(List<OrdersBean> orders, String productName, String ip, APP app);

	/**
	 * 提交订单 H5用
	 * @param orders
	 * @param productName
	 * @param ip
	 * @param app
	 * @return
	 */
	Result saveOrdersH5(List<OrdersBean> orders, String productName,String ip,APP app,int shareId);

	/**
	 * 提交订单 微信支付 H5用
	 * @param orders
	 * @param productName
	 * @param ip
	 * @param app
	 * @return
	 */
	Result saveOrdersH5WX(List<OrdersBean> orders, String productName,String ip,APP app,String openID,int shareId);
	
	/**
	 * 待支付订单提交
	 * @param guid 买家uid
	 * @param orderID
	 * @param productName
	 * @param payType
	 * @param ip
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月29日 上午11:16:39
	 */
	Result doNoPayOrder(int guid,int orderID,String productName,int payType,String ip,APP app);

	/**
	 * 待支付订单提交 H5
	 * @param guid 买家uid
	 * @param orderID
	 * @param productName
	 * @param payType
	 * @param ip
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月29日 上午11:16:39
	 */
	Result doNoPayOrderH5(int guid,int orderID,String productName,int payType,String ip,APP app);
	/**
	 * 待支付订单提交 微信支付 H5
	 * @param guid 买家uid
	 * @param orderID
	 * @param productName
	 * @param payType
	 * @param ip
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月29日 上午11:16:39
	 */
	Result doNoPayOrderH5WX(int guid,int orderID,String productName,int payType,String ip,APP app,String openID);
	
	/**
	 * 取消订单
	 * @param orderID
	 * @param guid 买家uid
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午5:23:43
	 */
	int doCancelOrders(int orderID,int guid,APP app);    
	
	/**
	 * 反馈订单取消原因
	 * @param orderID 订单ID
	 * @param guid 
	 * @param cause 取消原因
	 * @param app
	 * @param type 
	 *
	 * @author : lizhi 
	 * @createTime : 2016年5月9日 上午10:31:09
	 */
	public void doFeedBackCause(int orderID, int guid, String cause, APP app , OrderReasonType type);
	
	/**
	 * 买家删除订单
	 * @param orderID
	 * @param guid 买家uid
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午2:34:13
	 */
	int deleteOrdersByBuyer(int orderID,int guid,APP app);
	
	/**
	 * 卖家删除订单
	 * @param orderID
	 * @param guid 卖家uid
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午2:34:13
	 */
	int deleteOrdersBySeller(int orderID,int guid,APP app);
	
	/**
	 * 修改订单价格
	 * @param orderID
	 * @param guid 卖家uid
	 * @param app
	 * @param actualPayment
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午6:30:37
	 */
	int updateOrderPrice(int orderID,int guid,double actualPayment,APP app);
	
	/**
	 * 确认发货
	 * @param orderID
	 * @param guid 卖家uid
	 * @param deliveryNo 物流单号/发货单号
	 * @param voucherPics 凭证图片json {'logistics_pic/01/7c69431287690df5.jpg','logistics_pic/55/d1334e908b6d34d0.jpg'}
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午4:49:48
	 */
	public Result doDeliverGoods(int orderID,int guid,String deliveryNo,String voucherPics,APP app);
	
	/**
	 * 买家申请退款
	 * @param orderID
	 * @param guid 买家uid
	 * @param amount 退款金额
	 * @param refundType 退货类型(1:退款不退货,2:退款又退货)
	 * @param goodStatus 收货状态(1:未收到货,2:已收到货)，默认为0
	 * @param reason 退款原因
	 * @param voucherPics 凭证图片json {'refund_pic/01/7c69431287690df5.jpg','refund_pic/55/d1334e908b6d34d0.jpg'}
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午5:52:01
	 */
	public Result doApplicationRefund(int orderID,int guid,double amount,int refundType,int goodStatus,String reason,String voucherPics,APP app);
	
	/**
	 * 卖家同意退款
	 * @param orderID
	 * @param guid 卖家uid
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午6:14:37
	 */
	public Result doAgreeRefund(int orderID,int guid,APP app);
	
	/**
	 * 查询退款原因接口
	 * @param orderID
	 * @param guid
	 * @param app
	 * @return
	 * 
	 * @author lizhi
	 * @creatTime 2016年5月13日上午10:14:49
	 */
	public RefundBean doQueryRefundReason(int orderID, APP app);
	
	/**
	 * 客服驳回退款
	 * @param orderID
	 * @param customerID
	 * @param rejectRefundReason
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午6:47:38
	 */
	public Result doRejectRefund(int orderID,int customerID,String rejectRefundReason,APP app);
	
	/**
	 * 确认收货
	 * @param orderID
	 * @param guid 买家uid
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午12:25:14
	 */
	public Result doGetGoods(int orderID,int guid,APP app); 
	
	/**
	 * 订单操作日志
	 * @param orderID
	 * @param userID
	 * @param opType
	 * @param orderStatus
	 * @param source
	 *
	 * @author : weihui
	 * @createTime : 2016年4月25日 下午12:15:04
	 */
	void saveOrderLog(int orderID, int userID, OpType opType,
                      OrderStatus orderStatus, int source);
	
	/**
	 * 获取订单总数
	 * @param guid
	 * @param app
	 * @return
	 *
	 * @author : lizhi 
	 * @createTime : 2016年4月27日 下午4:33:21
	 */
	public int getMyOrderCount(int guid,APP app);
	
	/**
	 * 根据支付订单号 获取push需要的相关信息
	 * @param orderPayCode
	 * @param app
	 * @return
	 * 
	 * @author lizhi
	 * @creatTime 2016年5月11日下午3:38:18
	 */
	public List<Map<String,String>> getOrderByOrderPayCode(String orderPayCode , APP app);
	
	//====================================后台使用=====================================//
	
	/**
	 * 根据订单号获取order 信息
	 * @param orderCode
	 * @param app
	 * @author lizhi
	 * @creatTime 2016年5月17日上午11:39:59
	 */
	public OrdersBean getOrderByOrderCode(String orderCode ,APP app);
	
	/**
	 * 根据Guid参数 获取订单信息 _ 买家逻辑 
	 * @param guid
	 * @param status
	 * @param createStartDT
	 * @param createEndDT
	 * @param source
	 * @param page
	 * @param pageSize
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月18日下午7:38:51
	 */
	public List<OrdersBean> getOrderByBuyerGuid4Manage(List<Integer> guids ,String status ,String createStartDT ,String createEndDT ,int source ,int page ,int pageSize);
	
	/**
	 * 根据guid参数 获取订单信息 _ 卖家逻辑
	 * @param orderID
	 * @param status
	 * @param createStartDT
	 * @param createEndDT
	 * @param source
	 * @param page
	 * @param pageSize
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月19日上午10:42:15
	 */
	public List<OrdersBean> getOrderBysellererGuid4Manage(List<Integer> guids,String status ,String createStartDT ,String createEndDT ,int source ,int page ,int pageSize);
	
	/**
	 * 无参数获取订单信息 _ 没有买卖家信息逻辑
	 * @param guids
	 * @param status
	 * @param createStartDT
	 * @param createEndDT
	 * @param source
	 * @param page
	 * @param pageSize
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月19日下午5:24:45
	 */
	public List<OrdersBean> getOrderByNull4Manage(String status ,String createStartDT ,String createEndDT ,int source ,int page ,int pageSize);
	
	/**
	 * 根据状态和 followMark拿到OrderCode
	 * @param followMark 后台客服跟进标识	
	 * @param source
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月30日下午4:21:21
	 */
	public String getOrderCodesByStatusAndFollowMark(int followMark ,int source);
	
	/**
	 * 定时更新后台客服跟进标识
	 * 
	 * @author lizhi
	 * @creatTime 2016年5月31日上午10:55:05
	 */
	public void updateFollowMark4Quartz();
	
	/**
	 * 根据orderID 获取物流信息
	 * @param a
	 * @param app
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月20日下午4:48:14
	 */
	public OrderLogisticsBean getOrderLogisticsByOrderID(int orderID, APP app);
	
	/**
	 * 获取超过24小时未支付的订单
	 * @param orderStatus
	 * @param app
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年5月24日 上午9:50:11
	 */
	public List<OrdersBean> getOrdersUnpaidOver24h(APP app);
	
	/**
	 * 查询卖家uid
	 * @param orderID
	 * @param APP
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月25日 下午3:18:41
	 */
	int getSellerUid(int orderID,APP app);
	
	/**
	 * 提醒发货
	 * @param orderID
	 * @param guid 买家uid
	 * @param app
	 * @return {"orderCode":"060315081432647","sellerUid":100160}
	 *
	 * @author : weih
	 * @createTime : 2016年6月6日 下午2:36:04
	 */
	Result doWarnDelivery(int orderID,int guid,APP app);
	
	//=================================================================================业务订单相关处理  begin=====================================================//
	
	/**
	 * 根据订单号查询 业务订单相关
	 * @param keyword
	 * @param accountStatus
	 * @param createStartDT
	 * @param createEndDT
	 * @param page
	 * @param pageSize
	 * @return
	 * @author lizhi
	 * @creatTime 2016年6月7日下午2:41:45
	 */
	public ServiceOrderBean getServiceOrderByOrderCode(String keyword, int accountStatus, String createStartDT, String createEndDT, APP app);
	
	/**
	 * 业务人员订单查询
	 * @param buyerUserIDs
	 * @param servicerUserIDs
	 * @param accountStatus
	 * @param createStartDT
	 * @param createEndDT
	 * @param ordinal
	 * @param pageNo
	 * @param pageSize
	 * @return
	 *
	 * @author : wang.tao
	 * @createTime : 2016年6月11日 下午4:42:47
	 * @version : web1.4.3
	 */
	List<ServiceOrderBean> getServiceOrders(List<Integer> buyerUserIDs,List<Integer> servicerUserIDs, int accountStatus,String createStartDT, String createEndDT, APP app, int pageNo,int pageSize) ;
	
	/**
	 * 根据orderID查询业务订单详情
	 * @param orderID
	 * @return
	 *
	 * @author : wang.tao
	 * @createTime : 2016年6月12日 下午12:09:26
	 * @version : web1.4.3
	 */
	List<ServiceRemit> getServiceRemitListByOrderID(int orderID , APP app) ;
	
	/**
	 * 保存业务订单
	 * @param item
	 * @return
	 *
	 * @author : wang.tao
	 * @createTime : 2016年6月12日 下午12:10:06
	 * @version : web1.4.3
	 */
	int saveServiceRemit(ServiceRemit item ,int guid, APP app);
	
	/**
	 * 根据条件查询业务汇款表
	 * @param item
	 * @param startDT
	 * @param endDT
	 * @param app
	 * @param pageNo
	 * @param pageSize
	 * @return
	 *
	 * @author : wang.tao
	 * @createTime : 2016年6月14日 下午9:31:33
	 * @version : web1.4.3
	 */
	List<ServiceRemit> getServiceRemitListByParas(ServiceRemit item ,String startDT ,String endDT,APP app ,int pageNo,int pageSize) ;
	
	/**
	 * 业务汇款
	 * @param id
	 * @param app
	 * @author lizhi
	 * @creatTime 2016年6月16日下午4:16:00
	 */
	public void doServiceRemit(int id ,APP app);
	
	
	/**
	 * 获取卖家已完成订单数量
	 * @param uid
	 * @param app
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年7月13日 下午12:02:08
	 */
	public int getSellerFinishedOrderCount(int uid,APP app);
	
	/**
	 * 获取卖家累计销售额
	 * @param uid
	 * @param app
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年7月13日 下午12:01:58
	 */
	public Double getSellerFinishedOrderTotalPrice(int uid,APP app);
	
	
	
	
	//=================================================================================业务订单相关处理 end=====================================================//
	
	/**
	 * 查询发货信息 卖家 买家
	 * @param orderID
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月15日 上午11:48:13
	 */
	public List<OrderLogisticsBean> getOrderLogisticList(int orderID);
	
	/**
	 * 更新发货信息 卖家
	 * @param logisticsBean
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月15日 上午11:48:32
	 */
	public int updateOrderLogistic(OrderLogisticsBean logisticsBean);

	/**
	 * 通过promoteId shareId 查询订单数
	 * @return
	 */
	public int getOrderCountByShareId(int shareId,int status);
}
