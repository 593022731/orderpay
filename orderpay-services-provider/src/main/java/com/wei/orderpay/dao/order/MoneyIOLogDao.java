package com.wei.orderpay.dao.order;

import java.util.List;
import java.util.Map;

/**
 * 资金流水
 *
 * @author : liuquan
 * @createTime : 2016年6月15日 下午6:29:57
 * @version : 1.0 
 * @description : 
 *
 */
public interface MoneyIOLogDao {
	
	/**
	 * 资金流水列表
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月15日 下午6:30:30
	 */
	List<Map<String,Object>> getMoneyIOLogs(String tradeNo,
			List<Integer> payerList, List<Integer> receiverList,
			String type, String payType, String startDT, String endDT,
			int source,int pageNo,int pageSize);
	
	/**
	 * 保存资金流水
	 * @param moneyIOLog
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月16日 下午2:10:49
	 */
	int saveMoneyIOLog(String tradeNo,double amount,int payerUid,int receiverUid,int type,int payType,int source);
}
