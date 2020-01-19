package com.wei.orderpay.manage.dao.order.impl;

import com.wei.orderpay.manage.bean.order.RefundBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.order.RefundCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author : wang.tao
 * @createTime : 2016年4月13日 下午5:49:02
 * @version : 1.0
 * @description :
 *
 */
@SuppressWarnings("all")
@Repository("refundCommonDao")
public class ManageRefundCommonDaoImpl extends BaseDao implements RefundCommonDao {

	@Override
	public int rejectRefund(RefundBean item) {
		return getTemplate().update("RefundCommon.rejectRefund",item);
	}

	@Override
	public RefundBean queryRefundReason(int orderID, int source) {
		Map<String,Object> parameter = new HashMap<String, Object>();
		parameter.put("orderID", orderID);
		parameter.put("source", source);
		
		return (RefundBean)getTemplate().selectOne("RefundCommon.queryRefundReason", parameter);
	}

}
