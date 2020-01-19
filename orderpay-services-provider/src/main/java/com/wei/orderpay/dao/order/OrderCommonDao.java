package com.wei.orderpay.dao.order;

import com.wei.orderpay.bean.order.OrdersBean;
import com.wei.orderpay.bean.order.ServiceOrderBean;

import java.util.List;
import java.util.Map;

/**
 * 订单相关dao
 * 
 * @author : weihui
 * @createTime : 2016年4月12日 下午6:33:31
 * @version : 1.0
 * @description :
 *
 */
public interface OrderCommonDao {
	/**
	 * 保存订单
	 * 
	 * @param item
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月12日 下午2:55:41
	 */
	int saveOrders(OrdersBean item);

	/**
	 * 买家的订单列表
	 * 
	 * @param guid
	 * @param source
	 * @param status
	 *            状态从头1开始，查询全部传入0即可
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 上午11:49:48
	 */
	List<OrdersBean> getBuyerOrderList(int guid, int source, int status ,int page,int pageSize);

	/**
	 * 卖家的订单列表
	 * 
	 * @param orderIDs
	 * @param source
	 * @param status
	 *            状态从头1开始，查询全部传入0即可
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午12:13:00
	 */
	List<OrdersBean> getSellerOrderList(List<Integer> orderIDs, int source,
			int status ,int page,int pageSize);

	/**
	 * 查询订单对象
	 * 
	 * @param orderID
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午3:07:43
	 */
	OrdersBean getOrders(int orderID, int source);

	/**
	 * 修改订单状态
	 * 
	 * @param orderID
	 * @param source
	 * @param guid
	 * @param newStatus
	 * @param oldStatus
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午6:00:22
	 */
	int updateOrderStatus(int orderID, int source, int guid, int newStatus,
			int oldStatus);

	/**
	 * 修改订单价格
	 * 
	 * @param orderID
	 * @param source
	 * @param status
	 * @param actualPayment
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午6:29:03
	 */
	int updateOrderPrice(int orderID, int source, int status,
			double actualPayment);

	/**
	 * 买家删除订单
	 * 
	 * @param orderID
	 * @param guid
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午2:36:16
	 */
	int deleteOrdersByBuyer(int orderID, int guid, int source);
	
	/**
	 * 卖家删除订单
	 * 
	 * @param orderID
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午2:36:16
	 */
	int deleteOrdersBySeller(int orderID,int source);

	/**
	 * 根据支付订单号获取订单信息
	 * @param orderPayCode
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月25日 上午11:47:53
	 */
	List<OrdersBean> getOrderByOrderPayCode(String orderPayCode,int source);
	
	/**
	 * 获取订单总数
	 * @param guid
	 * @param app
	 * @return
	 *
	 * @author : lizhi
	 * @createTime : 2016年4月27日 下午4:35:46
	 */
	public int getMyOrderCount(int guid,int source);
	
	/**
	 * 更新支付订单号
	 * @param orderID
	 * @param source
	 * @param status
	 * @param orderPayCode
	 * @param payType
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月29日 上午10:50:08
	 */
	int updateOrderPayCode(int orderID, int source, int status,String orderPayCode,int payType);
	
	//=============================================后台查询=============================================//
	
	/**
	 * 根据买家相关信息查询 _买家逻辑
	 * @param guids
	 * @param status
	 * @param createStartDT
	 * @param createEndDT
	 * @param source
	 * @param page
	 * @param pageSize
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月19日下午4:48:28
	 */
	public List<OrdersBean> getBuyerOrderList4Buyer(List<Integer> guids,String status,String createStartDT,String createEndDT,int source,int page,int pageSize );
	
	/**
	 * 根据卖家相关信息查询 _ 卖家逻辑
	 * @param guids
	 * @param status
	 * @param createStartDT
	 * @param createEndDT
	 * @param source
	 * @param page
	 * @param pageSize
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月19日下午4:48:18
	 */
	public List<OrdersBean> getBuyerOrderList4Seller(List<Integer> guids,String status,String createStartDT,String createEndDT,int source,int page,int pageSize );
	
	/**
	 * 无信息查询 _ 无买卖家信息逻辑
	 * @param status
	 * @param createStartDT
	 * @param createEndDT
	 * @param source
	 * @param page
	 * @param pageSize
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月19日下午5:33:45
	 */
	List<OrdersBean> getOrderByNull4Manage(String status, String createStartDT,String createEndDT, int source, int page, int pageSize);
	
	/**
	 *  根据状态和 followMark   For__Update
	 * @param status 要查询的状态集
	 * @param followMark 后台客服跟进标识
	 * @param source
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月30日下午3:33:23
	 */
	public String getOrderCodesByStatusAndFollowMark(List<Integer> status,int followMark ,int source, int pageNo , int pageSize);
	
	/**
	 * 根据订单Code 更新后台客服跟进标识
	 * @param orderCode
	 * @author lizhi
	 * @creatTime 2016年5月30日下午4:29:41
	 */
	public Integer updateFollowMarkByOrderCode(String orderCode);
	
	/**
	 * 定时更新后台客服跟进标识
	 * @param status 要查询的状态集
	 * 
	 * @author lizhi
	 * @creatTime 2016年5月31日上午10:45:09
	 */
	public void updateFollowMark4Quartz(List<Integer> statuses);
	
	/**
	 * 根据订单号查询订单详情
	 * @param orderCode
	 * @param source
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月17日上午11:51:32
	 */
	public OrdersBean getOrdersByOrderCode(String orderCode,int source);
	
	/**
	 * 只修改订单状态时间 (提现发货逻辑)-
	 * @param orderID
	 * @param guid
	 * @param orderStatus
	 * @param source
	 * @return
	 *
	 * @author : weih
	 * @createTime : 2016年6月6日 下午2:41:39
	 */
	int updateOrderStatusDT(int orderID,int guid,int orderStatus,int source);

	
	//=============================================后台查询=============================================//
	
	/**
	 * 获取超过24小时未支付的订单
	 * @param orderStatus
	 * @param source
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年5月24日 上午9:52:37
	 */
	List<OrdersBean> getOrdersUnpaidOver24h(int orderStatus,int source);

	/**
	 * 交易记录列表
	 * @param guidList
	 * @param orderNo
	 * @param startDT
	 * @param endDT
	 * @param receiveName
	 * @param receiveUserAccount
	 * @param source
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月1日 下午4:28:05
	 */
	List<Map<String, Object>> getDealList(List<Integer> listBuyer,List<Integer> listSeller,String recordCode, String startDT, String endDT,int source,int pageNo,int pageSize);



	
	//=================================================================================业务订单相关处理  begin=====================================================//
	
	
	
	/**
	 * 通过	业务orderCode 查询订单信息 
	 * @param params 相关参数的封装
	 * @return
	 * @author lizhi
	 * @creatTime 2016年6月7日下午3:13:36
	 */
	public ServiceOrderBean getServiceOrderByOrderCode(Map<String, Object> params);
	
	/**
	 * 获取该支付订单号全部全部支付金额
	 * @param orderPayCode
	 * @param ordinal
	 * @return
	 * @author tankai
	 * @createTime : 2016年6月12日 上午10:56:04
	 * @version : 1.4
	 */
	double getTotalAmountByOrderPayCode(Map<String, Object> params);
	
	/**
	 * 查询业务人员订单
	 * @param params
	 * @return
	 *
	 * @author : wang.tao
	 * @createTime : 2016年6月11日 下午5:30:50
	 * @version : web1.4.3
	 */
	List<ServiceOrderBean> getServiceOrders(Map<String, Object> params);

	int getSellerFinishedOrderCount(Map<String, Object> paras);

	Double getSellerFinishedOrderTotalPrice(Map<String, Object> paras);

	/**
	 * 更新最后发起支付时间
	 * @param orderID
	 * @return
	 */
    int updateOrderLastPayDT(int orderID);

    int getOrderCountByShareId(int shareId,int status);


    //=================================================================================业务订单相关处理  end=====================================================//
	
	
	
	
	
	
}










