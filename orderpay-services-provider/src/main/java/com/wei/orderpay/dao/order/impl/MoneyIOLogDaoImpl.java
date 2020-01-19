package com.wei.orderpay.dao.order.impl;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.MoneyIOLogDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Repository("moneyIOLogDao")
public class MoneyIOLogDaoImpl extends BaseDao implements MoneyIOLogDao {

	@Override
	public List<Map<String, Object>> getMoneyIOLogs(String tradeNo,List<Integer> payerList, List<Integer> receiverList, String type,String payType, String startDT, String endDT, int source,int pageNo,int pageSize) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", source);
		map.put("page", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		if(CommonUtils.isNotEmpty(tradeNo)){
			map.put("tradeNo", tradeNo);
		}
		if(!payerList.isEmpty()){
			map.put("payerList", payerList);
		}
		if(!receiverList.isEmpty()){
			map.put("receiverList", receiverList);
		}
		if(CommonUtils.isNotEmpty(type)){
			map.put("type", type);
		}
		if(CommonUtils.isNotEmpty(payType)){
			map.put("payType", payType);
		}
		if(CommonUtils.isNotEmpty(startDT)){
			map.put("startDT", startDT);
		}
		if(CommonUtils.isNotEmpty(endDT)){
			map.put("endDT", endDT);
		}
		
		return (List<Map<String,Object>>)getTemplate().selectList("MoneyIOLog.selectMoneyIOLogs",map);
	}

	@Override
	public int saveMoneyIOLog(String tradeNo,double amount, int payerUid, int receiverUid,int type, int payType, int source) {
		Map<String,Object> map = new HashMap<>();
		map.put("tradeNo", tradeNo);
		map.put("amount", amount);
		map.put("payerGuid", payerUid);
		map.put("receiverGuid", receiverUid);
		map.put("type", type);
		map.put("payType", payType);
		map.put("source", source);
		return getTemplate().insert("MoneyIOLog.saveMoneyIOLog",map);
	}

}
