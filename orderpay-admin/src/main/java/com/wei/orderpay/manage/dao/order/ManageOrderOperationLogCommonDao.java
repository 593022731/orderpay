package com.wei.orderpay.manage.dao.order;

import com.wei.orderpay.manage.bean.order.OrderOperationLogBean;

/**
 * 订单操作日志记录dao
 * 
 * @author : weihui
 * @createTime : 2016年4月13日 上午11:29:20
 * @version : 1.0
 * @description :
 *
 */
public interface ManageOrderOperationLogCommonDao {

	/**
	 * 保存订单操作记录
	 * @param item
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月13日 上午11:30:00
	 */
	int saveOrderOperationLog(OrderOperationLogBean item);
}
