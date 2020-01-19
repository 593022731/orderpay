package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.bean.order.OrderLogisticsBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.OrderLogisticsCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("orderLogisticsCommonDao")
public class OrderLogisticsCommonDaoImpl extends BaseDao implements OrderLogisticsCommonDao{
	
	@Override
	public int saveOrderLogistics(OrderLogisticsBean item) {
		return getTemplate().insert("OrderLogisticsCommon.saveOrderLogistics",item);
	}

	@Override
	public OrderLogisticsBean getOrderLogisticsByOrderID(int orderID, int source) {
		
		Map<String , Object>  params = new HashMap<String,Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		Object selectOne = getTemplate().selectOne("OrderLogisticsCommon.getOrderLogisticsByOrderID",params);
		return (OrderLogisticsBean) selectOne;
	}
	
	@Override
	public int updateOrderLogistics(OrderLogisticsBean item) {
		return getTemplate().update("OrderLogisticsCommon.updateOrderLogistics",item);
	}
	
	@Override
	public List<OrderLogisticsBean> getOrderLogisticList(int orderID) {
		
		Map<String , Object>  params = new HashMap<String,Object>();
		params.put("orderID", orderID);
		List<OrderLogisticsBean> selectOne = (List<OrderLogisticsBean>) getTemplate().selectList("OrderLogisticsCommon.getOrderLogisticList",params);
		return selectOne;
	}
	
	
}
