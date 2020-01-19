package com.wei.orderpay.service.pay;

import com.wei.orderpay.contacts.common.APP;

import java.util.List;
import java.util.Map;

/**
 * 资金流水记录
 *
 * @author : liuquan
 * @createTime : 2016年6月15日 下午6:21:34
 * @version : 1.0 
 * @description : 
 *
 */
public interface MoneyIOLogCommonService {

	/**
	 * 查询资金流水记录
	 * 
	 * @param tradeNo
	 * @param payerList
	 * @param receiverList
	 * @param type
	 * @param payType
	 * @param source
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月15日 下午6:26:02
	 */
	List<Map<String,Object>> getMoneyIOLogs(String tradeNo, List<Integer> payerList, List<Integer> receiverList, String type, String payType, String startDT, String endDT, APP app, int pageNo, int pageSize);
	
}
