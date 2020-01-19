package com.wei.orderpay.manage.service.moneyIO.impl;

import com.wei.orderpay.manage.contacts.common.APP;
import com.wei.orderpay.manage.dao.moneyIO.ManageMoneyIOLogDao;
import com.wei.orderpay.manage.service.moneyIO.MoneyIOLogCommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("moneyIOLogCommonService")
public class MoneyIOLogCommonServiceImpl implements MoneyIOLogCommonService{

	@Resource
	ManageMoneyIOLogDao manageMoneyIOLogDao;
	
	@Override
	public List<Map<String, Object>> getMoneyIOLogs(String tradeNo,List<Integer> payerList, List<Integer> receiverList,String type, String payType, String startDT, String endDT,APP app,int pageNo,int pageSize) {
		return manageMoneyIOLogDao.getMoneyIOLogs(tradeNo, payerList, receiverList, type, payType, startDT, endDT, app.ordinal(),pageNo,pageSize);
	}

}
