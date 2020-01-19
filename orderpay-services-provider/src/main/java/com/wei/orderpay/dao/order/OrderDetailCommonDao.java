package com.wei.orderpay.dao.order;

import com.wei.orderpay.bean.order.OrderDetailBean;

import java.util.List;

/**
 * 订单详情dao
 * 
 * @author : weihui
 * @createTime : 2016年4月13日 上午11:15:11
 * @version : 1.0
 * @description :
 *
 */
public interface OrderDetailCommonDao {

	/**
	 * 保存订单详情
	 * @param item
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月13日 上午11:15:38
	 */
	int saveOrderDetail(OrderDetailBean item);
	
	/**
	 * 查询订单详情
	 * @param orderID
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午3:16:14
	 */
	List<OrderDetailBean> getOrderDetails(int orderID,int source);

	/**
	 * 查询卖家所有订单id**orderID
	 * @param guid
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月15日 下午12:02:22
	 */
	List<Integer> getSellerOrderList(int guid,int source);
	
	/**
	 * 查询卖家uid
	 * @param orderID
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月25日 下午3:18:41
	 */
	int getSellerUid(int orderID,int source);
	
	/**
	 * 根据订单ID 查询订单详情 信息  // 删除!!!!
	 * @param orderID
	 * @param source
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月18日下午4:41:39
	 */
	public OrderDetailBean getOrderDetailByOrderID(int orderID,int source);   
	
}
