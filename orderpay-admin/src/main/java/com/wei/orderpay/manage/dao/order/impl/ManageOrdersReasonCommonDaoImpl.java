package com.wei.orderpay.manage.dao.order.impl;

import com.wei.orderpay.manage.bean.order.OrdersReason;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.order.ManageOrdersReasonCommonDao;
import org.springframework.stereotype.Repository;


@Repository("ordersReasonCommonDao")
public class ManageOrdersReasonCommonDaoImpl extends BaseDao implements ManageOrdersReasonCommonDao{

	@Override
	public void saveFeedBackCause(OrdersReason OrdersReason) {
		
		getTemplate().insert("ordersReason.saveFeedBackCause", OrdersReason);
	}

}
