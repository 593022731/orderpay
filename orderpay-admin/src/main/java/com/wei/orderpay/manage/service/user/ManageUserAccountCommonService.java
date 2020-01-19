package com.wei.orderpay.manage.service.user;

import com.wei.orderpay.manage.contacts.common.APP;

import java.util.List;
import java.util.Map;


/**
 * 账户相关service
 * 
 * @author : weihui
 * @createTime : 2016年4月12日 下午6:28:18
 * @version : 1.0
 * @description :
 *
 */
public interface ManageUserAccountCommonService {
	/**
	 * 后台提现管理查询
	 * @param guidList
	 * @param tradeNo
	 * @param recordCode
	 * @param status
	 * @param startDT
	 * @param endDT
	 * @param bankcardAccountName
	 * @param bankcardNo
	 * @param app
	 * @param pageNo
	 * @param pageSize
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月8日 下午2:52:01
	 */
	List<Map<String,Object>> getExtractList(List<Integer> guidList,String tradeNo,String recordCode,String status,String startDT,String endDT,String bankcardAccountName,int bankcardNo,APP app,int pageNo ,int pageSize);
	
	/**
	 * 交易记录列表
	 * @param guidList
	 * @param orderNo
	 * @param startDT
	 * @param endDT
	 * @param receiveName
	 * @param receiveUserAccount
	 * @param app
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月1日 下午4:20:40
	 */
	List<Map<String,Object>> getDealList(List<Integer> listBuyer,List<Integer> listSeller,String recordCode,String startDT,String endDT,APP app,int pageNo,int pageSize);
}
