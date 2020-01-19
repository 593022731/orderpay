package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.bean.order.OrderDetailBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.OrderDetailCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Repository("orderDetailCommonDao")
public class OrderDetailCommonDaoImpl extends BaseDao implements OrderDetailCommonDao {

	@Override
	public int saveOrderDetail(OrderDetailBean item) {
		return getTemplate().insert("OrderDetailCommon.saveOrderDetail",item);
	}

	@Override
	public List<OrderDetailBean> getOrderDetails(int orderID,int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		return (List<OrderDetailBean>)getTemplate().selectList("OrderDetailCommon.getOrderDetails",params);
	}

	@Override
	public List<Integer> getSellerOrderList(int guid, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("guid", guid);
		params.put("source", source);
		return (List<Integer>)getTemplate().selectList("OrderDetailCommon.getSellerOrderIDs",params);
	}

	@Override
	public int getSellerUid(int orderID, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		Object selectOne = getTemplate().selectOne("OrderDetailCommon.getSellerUid",params);
		return selectOne == null ? 0 : (int)selectOne;
	}

	@Override
	public OrderDetailBean getOrderDetailByOrderID(int orderID, int source) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		
		return (OrderDetailBean)getTemplate().selectOne("OrderDetailCommon.getOrderDetailByOrderID",params);
	}

}
