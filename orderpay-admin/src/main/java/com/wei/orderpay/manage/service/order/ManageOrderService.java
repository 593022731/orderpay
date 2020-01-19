package com.wei.orderpay.manage.service.order;

import com.jiutong.common.Result;
import com.jiutong.common.tools.Pagination;
import com.wei.orderpay.manage.bean.order.OrderLogisticsBean;
import com.wei.orderpay.manage.bean.order.OrdersBean;
import com.wei.orderpay.manage.bean.order.RefundBean;
import com.wei.orderpay.manage.contacts.common.APP;
import com.wei.orderpay.manage.contacts.order.OpType;
import com.wei.orderpay.manage.contacts.order.OrderStatus;

import java.util.List;

/**
 * 后台管理 service
 * 
 * @author : lizhi
 * @creatTime : 2016年6月21日上午10:15:27
 * @version : 1.0
 * @description : 
 *
 */
public interface ManageOrderService {
	
	/**
	 * 订单详情
	 * @param orderID
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午3:00:20
	 */
	public Result getOrderDetail(int orderID,APP app);
	
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
	public int doCancelOrders(int orderID,int guid,APP app,int operatorId);
	
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
	public void saveOrderLog(int orderID, int userID, OpType opType,OrderStatus orderStatus, int source);
	
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
	public RefundBean doQueryRefundReason(int orderID,APP app); //******//
	
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
	public Result doGetGoods(int orderID,int guid,APP app,int operatorId);
	

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
	 * @param orderID
	 * @param app
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月20日下午4:48:14
	 */
	public OrderLogisticsBean getOrderLogisticsByOrderID(int orderID, APP app);
	
	/**
	 * 获取超过24小时未支付的订单
	 * @param app
	 * @return
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
	
	/**
	 * 模糊查询订单列表
	 * @param productID
	 * @param userBidID
	 * @param status
	 * @param productOrBid
	 * @param orderCode
	 * @param buyerOrSellerUid
	 * @param createStartDT
	 * @param createEndDT
	 * @param pageNo
	 * @param pageSize
	 * @param app
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年7月22日 下午2:30:47
	 */
	List<OrdersBean> getOrdersList(List<Integer> productID,List<Integer> userBidID,Integer status,Integer productOrBid,String orderCode,Integer buyerOrSellerUid ,String createStartDT,String createEndDT, Integer pageNo,Integer pageSize,APP app,Integer orderType);
	
	Pagination<OrdersBean> getOrdersListPagination(List<Integer> productID,List<Integer> userBidID,Integer status,Integer productOrBid,String orderCode,Integer buyerOrSellerUid ,String createStartDT,String createEndDT, Integer pageNo,Integer pageSize,APP app,Integer orderType);
	
	int getOrdersListCount(List<Integer> productID,List<Integer> userBidID,Integer status,Integer productOrBid,String orderCode,Integer buyerOrSellerUid ,String createStartDT,String createEndDT, Integer pageNo,Integer pageSize,APP app,Integer orderType);
	
	
}
