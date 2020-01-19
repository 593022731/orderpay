package com.wei.orderpay.manage.dao.order;

import com.wei.orderpay.manage.bean.order.OrderDetailBean;

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
public interface ManageOrderDetailCommonDao {

	/**
	 * 查询订单详情
	 * @param orderID
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午3:16:14
	 */
	List<OrderDetailBean> getOrderDetails(int orderID, int source);

	
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
	
	
}
