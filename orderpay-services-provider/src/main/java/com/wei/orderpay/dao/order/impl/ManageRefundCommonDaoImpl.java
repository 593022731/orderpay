package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.bean.order.ManageRefundBean;
import com.wei.orderpay.contacts.order.PayType;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.ManageRefundCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Repository("manageRefundCommonDao")
public class ManageRefundCommonDaoImpl extends BaseDao implements ManageRefundCommonDao {

	@Override
	public List<ManageRefundBean> getRefundInfo(Map<String, Object> params) {
		return (List<ManageRefundBean>)getTemplate().selectList("ManageRefundBeanCommon.getRefundInfo",params);
	}

	@Override
	public List<ManageRefundBean> getAliRefundInfo(int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("source", source);
		params.put("payType", PayType.ALIPAY.ordinal());
		return (List<ManageRefundBean>)getTemplate().selectList("ManageRefundBeanCommon.getAliRefundInfo",params);
	}

}
