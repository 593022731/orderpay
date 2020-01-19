package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.bean.order.OrderOperationLogBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.OrderOperationLogCommonDao;
import org.springframework.stereotype.Repository;

@Repository("orderOperationLogCommonDao")
public class OrderOperationLogCommonDaoImpl extends BaseDao implements
		OrderOperationLogCommonDao {

	@Override
	public int saveOrderOperationLog(OrderOperationLogBean item) {
		return getTemplate().insert("OrderOperationLogCommon.saveOrderOperationLog",item);
	}

}
