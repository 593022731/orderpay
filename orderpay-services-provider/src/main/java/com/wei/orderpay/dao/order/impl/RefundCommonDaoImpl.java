package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.bean.order.RefundBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.RefundCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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
public class RefundCommonDaoImpl extends BaseDao implements RefundCommonDao {

    @Override
    public int saveRefund(RefundBean item) {
        return getTemplate().insert("RefundCommon.saveRefund", item);
    }

	@Override
	public int agreeRefund(RefundBean item) {
		return getTemplate().update("RefundCommon.agreeRefund",item);
	}

	@Override
	public int rejectRefund(RefundBean item) {
		return getTemplate().update("RefundCommon.rejectRefund",item);
	}

	@Override
	public int refundSuccess(int id, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("source", source);
		return getTemplate().update("RefundCommon.refundSuccess",params);
	}

	@Override
	public List<RefundBean> getAliRefundOrders(int source) {
		return (List<RefundBean>)getTemplate().selectList("RefundCommon.getAliRefundOrders",source);
	}

	@Override
	public RefundBean queryRefundReason(int orderID, int source) {
		Map<String,Object> parameter = new HashMap<String, Object>();
		parameter.put("orderID", orderID);
		parameter.put("source", source);
		
		return (RefundBean)getTemplate().selectOne("RefundCommon.queryRefundReason", parameter);
	}

}
