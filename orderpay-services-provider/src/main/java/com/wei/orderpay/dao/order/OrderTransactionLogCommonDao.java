package com.wei.orderpay.dao.order;

import com.wei.orderpay.bean.order.OrderTransactionLogBean;

import java.util.List;

/**
 * 订单事务记录
 *
 * @author : liuquan
 * @createTime : 2016年4月13日	下午4:40:48
 * @version : 1.0 
 * @description : 
 *
 */
public interface OrderTransactionLogCommonDao {
	
	/**
	 * 保存订单变更事物日志记录
	 * @param item
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月13日 下午2:44:38
	 */
	int saveOrderTransactionLog(OrderTransactionLogBean item);
	
	/**
	 * 订单状态变更记录
	 * @param orderID
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月14日 下午4:41:59
	 */
	List<OrderTransactionLogBean> getOrderTransactionLogs(int orderID,int source);
}
