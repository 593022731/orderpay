package com.wei.orderpay.dao.order.impl;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.bean.user.ExtractAmountLogBean;
import com.wei.orderpay.contacts.user.UserAccountTransaction;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.ExtractAmountLogCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Repository("extractAmountLogCommonDao")
public class ExtractAmountLogCommonDaoImpl extends BaseDao implements ExtractAmountLogCommonDao{

    @Override
    public int saveExtractAmountLog(ExtractAmountLogBean extractamountlog) {
        return getTemplate().insert("ExtractAmountLogCommon.saveExtractAmountLog", extractamountlog);
    }

    @Override
	public int extractamountSuccess(int id, int source) {
    	Map<String,Object> params = new HashMap<>();
    	params.put("id", id);
    	params.put("source", source);
		return getTemplate().update("ExtractAmountLogCommon.extractamountSuccess",params);
	}
    
    @Override
    public int extractamountFail(int id, int source) {
    	Map<String,Object> params = new HashMap<>();
    	params.put("id", id);
    	params.put("source", source);
    	return getTemplate().update("ExtractAmountLogCommon.extractamountFail",params);
    }
    
	@Override
	public ExtractAmountLogBean getExtractAmountLogDetail(
			ExtractAmountLogBean exBean) {
		  return (ExtractAmountLogBean) getTemplate().selectOne("ExtractAmountLogCommon.getExtractAmountLogDetail", exBean);
	}

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
		
		return (List<Map<String,Object>>)getTemplate().selectList("ExtractAmountLogCommon.getExtractAmountList",map);
	}

}
