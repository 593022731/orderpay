package com.wei.orderpay.manage.dao.order.impl;

import com.wei.orderpay.manage.bean.order.OrderDetailBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.order.ManageOrderDetailCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("all")
@Repository("orderDetailCommonDao")
public class ManageOrderDetailCommonDaoImpl extends BaseDao implements ManageOrderDetailCommonDao {

	@Override
	public List<OrderDetailBean> getOrderDetails(int orderID, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		return (List<OrderDetailBean>)getTemplate().selectList("OrderDetailCommon.getOrderDetails",params);
	}
	
	@Override
	public int getSellerUid(int orderID, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		Object selectOne = getTemplate().selectOne("OrderDetailCommon.getSellerUid",params);
		return selectOne == null ? 0 : (int)selectOne;
	}

}
