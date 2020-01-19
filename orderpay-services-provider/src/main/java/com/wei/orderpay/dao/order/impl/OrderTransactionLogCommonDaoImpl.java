package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.bean.order.OrderTransactionLogBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.OrderTransactionLogCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Repository("orderTransactionLogCommonDao")
public class OrderTransactionLogCommonDaoImpl extends BaseDao implements OrderTransactionLogCommonDao{
	
	@Override
	public int saveOrderTransactionLog(OrderTransactionLogBean item) {
		return getTemplate().insert("OrderTransactionLogCommon.saveOrderTransactionLog",item);
	}

	@Override
	public List<OrderTransactionLogBean> getOrderTransactionLogs(int orderID,int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		return (List<OrderTransactionLogBean>)getTemplate().selectList("OrderTransactionLogCommon.getOrderTransactionLogs",params);
	}
}
