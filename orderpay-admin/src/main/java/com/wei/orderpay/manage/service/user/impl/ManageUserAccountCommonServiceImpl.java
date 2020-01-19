package com.wei.orderpay.manage.service.user.impl;

import com.wei.orderpay.manage.contacts.common.APP;
import com.wei.orderpay.manage.dao.order.ManageExtractAmountLogCommonDao;
import com.wei.orderpay.manage.dao.order.ManageOrderCommonDao;
import com.wei.orderpay.manage.service.user.ManageUserAccountCommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("manageUserAccountCommonService")
public class ManageUserAccountCommonServiceImpl implements ManageUserAccountCommonService{

	@Resource
	ManageOrderCommonDao manageOrderCommonDao;
	
	@Resource
	ManageExtractAmountLogCommonDao manageExtractAmountLogCommonDao;
	
	@Override
	public List<Map<String,Object>> getExtractList(List<Integer> guidList,String tradeNo,String recordCode,String status,String startDT,String endDT,String bankcardAccountName,int bankcardNo,APP app,int pageNo, int pageSize) {
		return manageExtractAmountLogCommonDao.getExtractAmountList(guidList,tradeNo,recordCode,status,startDT,endDT,bankcardAccountName,bankcardNo,app.ordinal(),pageNo, pageSize);
	}

	@Override
	public List<Map<String, Object>> getDealList(List<Integer> listBuyer,List<Integer> listSeller, String recordCode, String startDT, String endDT, APP app,int pageNo,int pageSize) {
		return manageOrderCommonDao.getDealList(listBuyer,listSeller,recordCode,startDT,endDT,app.ordinal(),pageNo,pageSize);
	}
	

}
