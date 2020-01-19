package com.wei.orderpay.manage.dao.order.impl;

import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.order.ManageOrderPayLogCommonDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("orderPayLogCommonDao")
public class ManageOrderPayLogCommonDaoImpl extends BaseDao implements ManageOrderPayLogCommonDao {

	@Override
	public String getTradeNo(String orderPayCode, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderPayCode", orderPayCode);
		params.put("source", source);
		Object selectOne = getTemplate().selectOne("OrderPayLogCommon.getTradeNo", params);
		return selectOne == null ? null : (String)selectOne;
	}

}
