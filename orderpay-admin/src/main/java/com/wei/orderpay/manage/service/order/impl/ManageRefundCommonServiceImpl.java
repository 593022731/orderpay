package com.wei.orderpay.manage.service.order.impl;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.manage.bean.order.ManageRefundBean;
import com.wei.orderpay.manage.contacts.common.APP;
import com.wei.orderpay.manage.dao.order.ManageOrderPayLogCommonDao;
import com.wei.orderpay.manage.dao.order.ManageRefundCommonDao;
import com.wei.orderpay.manage.pay.alipay.AliRefundUtils;
import com.wei.orderpay.manage.service.order.ManageRefundCommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("manageRefundCommonService")
public class ManageRefundCommonServiceImpl implements ManageRefundCommonService {

	@Resource
	ManageRefundCommonDao manageRefundCommonDao;
	
	@Resource
	ManageOrderPayLogCommonDao manageOrderPayLogCommonDaoOrderPayLogCommonDao;
	
	@Override
	public List<ManageRefundBean> getRefundInfo(String orderCode,
			String orderPayCode, String flag, String startDT, String endDT,
			List<Integer> buyerUids,int pageNo ,int pageSize, APP app) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("source", app.ordinal());
		if(CommonUtils.isNotEmpty(orderCode)){
			params.put("orderCode",orderCode);
		}
		if(CommonUtils.isNotEmpty(orderPayCode)){
			params.put("orderPayCode",orderPayCode);
		}
		if(CommonUtils.isNotEmpty(flag)){
			params.put("flag",flag);
		}
		if(CommonUtils.isNotEmpty(startDT)){
			params.put("startDT",startDT);
		}
		if(CommonUtils.isNotEmpty(endDT)){
			params.put("endDT",endDT);
		}
		if(buyerUids != null && !buyerUids.isEmpty()){
			params.put("buyerUids",buyerUids);
		}
		params.put("page", (pageNo-1) *  pageSize);
		params.put("pageSize", pageSize);
		return manageRefundCommonDao.getRefundInfo(params);
	}

	@Override
	public String getRequestFormForAli(APP app,List<Integer> orderIDList)
			throws Exception {
		List<ManageRefundBean> resList = manageRefundCommonDao.getAliRefundInfo(app.ordinal(),orderIDList);
		if(!resList.isEmpty()){
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			for(ManageRefundBean item : resList){
				Map<String, String> map = new HashMap<String, String>();
				map.put("refund_amount", item.getRefundFee()+"");
				map.put("tradeNo", item.getTradeNO());
				list.add(map);
			}
			return AliRefundUtils.getRequestForm(list);
		}
		return null;
	}

	@Override
	public String getRequestFormForAli(String orderPayCode,String refundFee, APP app)
			throws Exception {
		String tradeNo = manageOrderPayLogCommonDaoOrderPayLogCommonDao.getTradeNo(orderPayCode, app.ordinal());
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("refund_amount", refundFee);
		map.put("tradeNo", tradeNo);
		list.add(map);
		return AliRefundUtils.getRequestForm(list);
	}
}
