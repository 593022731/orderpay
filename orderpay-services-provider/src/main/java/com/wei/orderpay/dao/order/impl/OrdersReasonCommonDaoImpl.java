package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.bean.order.OrdersReason;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.OrdersReasonCommonDao;
import org.springframework.stereotype.Repository;

@Repository("ordersReasonCommonDao")
public class OrdersReasonCommonDaoImpl extends BaseDao implements OrdersReasonCommonDao{

	@Override
	public void saveFeedBackCause(OrdersReason OrdersReason) {
		
		getTemplate().insert("ordersReason.saveFeedBackCause", OrdersReason);
	}

}
