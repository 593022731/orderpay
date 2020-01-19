package com.wei.orderpay.service.pay.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.wei.orderpay.contacts.common.APP;
import com.wei.orderpay.dao.order.MoneyIOLogDao;
import com.wei.orderpay.service.pay.MoneyIOLogCommonService;
import org.springframework.stereotype.Service;

@Service("moneyIOLogCommonService")
public class MoneyIOLogCommonServiceImpl implements MoneyIOLogCommonService {

	@Resource
    MoneyIOLogDao moneyIOLogDao;
	
	@Override
	public List<Map<String, Object>> getMoneyIOLogs(String tradeNo, List<Integer> payerList, List<Integer> receiverList, String type, String payType, String startDT, String endDT, APP app, int pageNo, int pageSize) {
		return moneyIOLogDao.getMoneyIOLogs(tradeNo, payerList, receiverList, type, payType, startDT, endDT, app.ordinal(),pageNo,pageSize);
	}

}
