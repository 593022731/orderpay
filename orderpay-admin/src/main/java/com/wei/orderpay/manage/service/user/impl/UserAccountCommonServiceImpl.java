//package com.jiutong.orderpay.manage.service.user.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.jiutong.orderpay.contacts.user.BankType;
//import com.jiutong.orderpay.manage.contacts.common.APP;
//import com.jiutong.orderpay.manage.dao.order.ManageExtractAmountLogCommonDao;
//import com.jiutong.orderpay.manage.dao.order.ManageOrderCommonDao;
//import com.jiutong.orderpay.manage.dao.user.ManageBankcardCommonDao;
//import com.jiutong.orderpay.manage.service.user.ManageUserAccountCommonService;
//
//
//@Service("userAccountCommonService")
//public class UserAccountCommonServiceImpl implements ManageUserAccountCommonService{
//	
//	@Resource
//	ManageBankcardCommonDao manageBankcardCommonDao;
//
//	@Resource
//	ManageOrderCommonDao manageOrderCommonDao;
//	
//	@Resource
//	ManageExtractAmountLogCommonDao manageExtractAmountLogCommonDao;
//	
//	@Override
//	public List<Map<String,Object>> getBankcardTypeList() {
//		BankType[] bt = BankType.values();
//		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//		for(int i=1;i<bt.length;i++){
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("bankType",bt[i].bankType);
//			map.put("logo",bt[i].logo);
//			map.put("title",bt[i].title);
//			list.add(map);
//		}
//		return list;
//	}
//	
//	@Override
//	public List<Map<String,Object>> getExtractList(List<Integer> guidList,String tradeNo,String recordCode,String status,String startDT,String endDT,String bankcardAccountName,int bankcardNo,APP app,int pageNo, int pageSize) {
//		return manageExtractAmountLogCommonDao.getExtractAmountList(guidList,tradeNo,recordCode,status,startDT,endDT,bankcardAccountName,bankcardNo,app.ordinal(),pageNo, pageSize);
//	}
//
//	@Override
//	public List<Integer> getGuidListByBankcardNoOrName(String name,int bankcardNo, APP app) {
//		return manageBankcardCommonDao.getGuidListByBankcardNoOrName(name,bankcardNo,app.ordinal());
//	}
//
//	@Override
//	public List<Map<String, Object>> getDealList(List<Integer> listBuyer,List<Integer> listSeller, String recordCode, String startDT, String endDT, APP app,int pageNo,int pageSize) {
//		return manageOrderCommonDao.getDealList(listBuyer,listSeller,recordCode,startDT,endDT,app.ordinal(),pageNo,pageSize);
//	}
//	
//
//}
