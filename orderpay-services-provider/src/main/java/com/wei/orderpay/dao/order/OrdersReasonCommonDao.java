package com.wei.orderpay.dao.order;

import com.wei.orderpay.bean.order.OrdersReason;

public interface OrdersReasonCommonDao {
	
	/**
	 * 保存反馈原因
	 * @param OrdersReason
	 * @return
	 *
	 * @author : lizhi 
	 * @createTime : 2016年5月9日 上午11:15:06
	 */
	public void saveFeedBackCause(OrdersReason OrdersReason);
	
	
	
}
