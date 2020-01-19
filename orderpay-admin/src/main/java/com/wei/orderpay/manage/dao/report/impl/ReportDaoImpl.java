package com.wei.orderpay.manage.dao.report.impl;

import com.wei.orderpay.manage.contacts.common.APP;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.report.ReportDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportDaoImpl extends BaseDao implements ReportDao{

	@Override
	public Map<Integer, String> getTotalOrderAmount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalOrderAmount",map));
	}

	private Map<Integer, String> listToMap(List<Map<String, String>> listMap) {
		Map<Integer, String> map = new HashMap<Integer,String>();
		for(Map<String, String> m:listMap){
			map.put(Integer.parseInt(m.get("day")), String.valueOf(m.get("total")));
		}
		return map;
	}

	@Override
	public Map<Integer, String> getTotalPayOrderAmount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalPayOrderAmount",map));
	}

	@Override
	public Map<Integer, String> getTotalOrderCount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalPayOrderCount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalPayOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalProductOrderCount(
			String createDTString, String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalProductOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalBidOrderCount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalBidOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalCancelOrderCount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalCancelOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalAliPayOrderCount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalAliPayOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalWXPayOrderCount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalWXPayOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalChinaPayOrderCount(
			String createDTString, String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalChinaPayOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalRefundOrderCount(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalRefundOrderCount",map));
	}

	@Override
	public Map<Integer, String> getTotalRefundOrderAmount(
			String createDTString, String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalRefundOrderAmount",map));
	}

	@Override
	public Map<Integer, String> getTotalCustomerPrice(String createDTString,
			String endDTString,APP app,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getTotalCustomerPrice",map));
	}

	@Override
	public Map<Integer, String> getPayedProductCategoryReports(
			String createDTString, String endDTString, APP app,int categoryId,int type) {
		Map<String,Object> map = new HashMap<>();
		map.put("source", app.ordinal());
		map.put("startDT", createDTString);
		map.put("endDT", endDTString);
		map.put("categoryId", categoryId);
		map.put("type", type);
		return listToMap((List<Map<String, String>>)getTemplate().selectList("Report.getPayedProductCategoryReports",map));
	}
	

}
