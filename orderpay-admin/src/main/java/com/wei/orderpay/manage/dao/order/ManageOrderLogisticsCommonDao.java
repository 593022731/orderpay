package com.wei.orderpay.manage.dao.order;

import com.wei.orderpay.manage.bean.order.OrderLogisticsBean;


/**
 * 物流相关
 *
 * @author : liuquan
 * @createTime : 2016年4月13日	下午4:43:34
 * @version : 1.0 
 * @description : 
 *
 */
public interface ManageOrderLogisticsCommonDao {
	
	/**
	 * 根据 订单号 查询物流信息, 订单号和物流信息属于一一对应
	 * @param orderID
	 * @param source
	 * @return
	 * @author lizhi
	 * @creatTime 2016年5月20日下午4:37:27
	 */
	public OrderLogisticsBean getOrderLogisticsByOrderID(int orderID, int source);
	
	
}
