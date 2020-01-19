package com.wei.orderpay.service.user;

import com.jiutong.common.Result;
import com.wei.orderpay.bean.user.BankcardBean;
import com.wei.orderpay.bean.user.UserAccountTransactionLogBean;
import com.wei.orderpay.contacts.common.APP;
import com.wei.orderpay.contacts.user.UserAccountTransaction;

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
public interface UserAccountCommonService {
	
	/**
	 * 保存银行卡信息
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月28日 上午11:16:14
	 */
	Result saveBankCard(BankcardBean bandCard, APP app);

	/**
	 * 根据guid获取用户的银行卡列表
	 * @param guid
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月28日 下午12:06:22
	 */
	List<BankcardBean> getBankCardList(int guid,APP app);

	/**
	 * 获取账户余额
	 * @param userID
	 * @param app
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月28日 下午5:52:56
	 */
	double getBalance(int guid, APP app);

	/**
	 * 获取交易记录
	 * @param userID
	 * @param zml
	 * @param page
	 * @param pageSize
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年5月3日 下午4:51:20
	 */
	List<UserAccountTransactionLogBean> getTradingRecord(int guid, APP app,int page, int pageSize);
	
	/**
	 * 提现 -- 提现具体逻辑的实现,不包含密码验证
	 * @param amount 提现金额
	 * @param bankCardID 银行卡ID
	 * @param guid 
	 * @param app
	 *
	 * @author : lizhi 
	 * @createTime : 2016年5月6日 上午10:20:11
	 */
	public Result doWithdrawCash(double amount,int bankCardID,int guid, APP app);

	/**
	 * 获取银行卡类型列表
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年5月11日 下午5:49:56
	 */
	List<Map<String,Object>> getBankcardTypeList(); 

	/**
	 * 逻辑删除银行卡
	 * @param bankCardID
	 * @param APP
	 * @return
	 * @author tankai
	 * @createTime : 2016年5月16日 下午6:31:03
	 * @version : 1.4
	 */
	Result deleteBandcard(int guid,int bankCardID, APP app);

/////////////////////////////////////后台//////////////////////////////////////////////	
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
	 * 根据开户姓名或者银行卡号查询uid列表
	 * @param name
	 * @param bankcardNo
	 * @param app
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月1日 上午11:01:17
	 */
	List<Integer> getGuidListByBankcardNoOrName(String name,int bankcardNo,APP app);
	
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

	public Result doCharge(double amount, UserAccountTransaction type, int guid, APP app, String productName);

}
