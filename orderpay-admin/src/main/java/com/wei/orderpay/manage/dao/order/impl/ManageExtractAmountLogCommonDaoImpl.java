package com.wei.orderpay.manage.dao.order.impl;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.manage.bean.extraction.ExtractAmountLogBean;
import com.wei.orderpay.manage.contacts.user.UserAccountTransaction;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.order.ManageExtractAmountLogCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Repository("manageExtractAmountLogCommonDao")
public class ManageExtractAmountLogCommonDaoImpl extends BaseDao implements ManageExtractAmountLogCommonDao{

	@Override
	public List<Map<String,Object>> getExtractAmountList(List<Integer> guidList,String tradeNo,String recordCode,String status,String startDT,String endDT,String bankcardAccountName,int bankcardNo,int source,int pageNo,int pageSize){
		Map<String,Object> map = new HashMap<>();
		map.put("source", source);
		map.put("page", (pageNo-1) *  pageSize);
		map.put("pageSize", pageSize);
		
		if(guidList != null && !guidList.isEmpty()){
			map.put("guidList", guidList);
		}
		if(!CommonUtils.isEmptyString(recordCode)){
			map.put("recordCode", recordCode);
		}
		if(!CommonUtils.isEmptyString(tradeNo)){
			map.put("tradeNo", tradeNo);
		}
		if(!CommonUtils.isEmptyString(status)){
			map.put("status", status);
		}
		if(!CommonUtils.isEmptyString(startDT)){
			map.put("startDT", startDT);
		}
		if(!CommonUtils.isEmptyString(endDT)){
			map.put("endDT", endDT);
		}
		if(!CommonUtils.isEmptyString(bankcardAccountName)){
			map.put("bankcardAccountName", bankcardAccountName);
		}
		if(bankcardNo > 0){
			map.put("bankcardNo", bankcardNo);
		}
		map.put("userAccountTransactionType", UserAccountTransaction.WITHDRAW.ordinal());
		
		return (List<Map<String,Object>>)getTemplate().selectList("ManageExtractAmountLogCommon.getExtractAmountList",map);
	}

	@Override
	public ExtractAmountLogBean getExtractAmountLogDetail(
			ExtractAmountLogBean exBean) {
		  return (ExtractAmountLogBean) getTemplate().selectOne("ManageExtractAmountLogCommon.getExtractAmountLogDetail", exBean);
	}
	
	@Override
	public int extractamountSuccess(int id, int source) {
    	Map<String,Object> params = new HashMap<>();
    	params.put("id", id);
    	params.put("source", source);
		return getTemplate().update("ManageExtractAmountLogCommon.extractamountSuccess",params);
	}
	
	@Override
    public int extractamountFail(int id, int source) {
    	Map<String,Object> params = new HashMap<>();
    	params.put("id", id);
    	params.put("source", source);
    	return getTemplate().update("ManageExtractAmountLogCommon.extractamountFail",params);
    }
}
