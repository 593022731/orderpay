package com.wei.orderpay.manage.dao.order.impl;

import com.wei.orderpay.manage.bean.order.OrderLogisticsBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.order.ManageOrderLogisticsCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;



@Repository("orderLogisticsCommonDao")
public class ManageOrderLogisticsCommonDaoImpl extends BaseDao implements ManageOrderLogisticsCommonDao{
	
	@Override
	public OrderLogisticsBean getOrderLogisticsByOrderID(int orderID, int source) {
		
		Map<String , Object>  params = new HashMap<String,Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		Object selectOne = getTemplate().selectOne("OrderLogisticsCommon.getOrderLogisticsByOrderID",params);
		return (OrderLogisticsBean) selectOne;
	}
	
}
