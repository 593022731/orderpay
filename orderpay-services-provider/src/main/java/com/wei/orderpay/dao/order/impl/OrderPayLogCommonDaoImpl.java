package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.OrderPayLogCommonDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("orderPayLogCommonDao")
public class OrderPayLogCommonDaoImpl extends BaseDao implements OrderPayLogCommonDao {

	@Override
	public int save(String orderPayCode, int payType, double totalFee,
			String tradeNo, String payDT, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderPayCode", orderPayCode);
		params.put("payType", payType);
		params.put("totalFee", totalFee);
		params.put("tradeNo", tradeNo);
		params.put("payDT", payDT);
		params.put("source", source);
		return getTemplate().insert("OrderPayLogCommon.save",params);
	}

	@Override
	public String getTradeNo(String orderPayCode, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderPayCode", orderPayCode);
		params.put("source", source);
		Object selectOne = getTemplate().selectOne("OrderPayLogCommon.getTradeNo", params);
		return selectOne == null ? null : (String)selectOne;
	}

	@Override
	public int getOrderIDByTradeNo(String tradeNO, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeNO", tradeNO);
		params.put("source", source);
		Object selectOne = getTemplate().selectOne("OrderPayLogCommon.getOrderIDByTradeNo", params);
		return selectOne == null ? 0 : (int)selectOne;
	}

}
