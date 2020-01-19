package com.wei.orderpay.service.user.impl;

import com.jiutong.common.BusinessException;
import com.jiutong.common.Result;
import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.bean.common.OrderPayResultCode;
import com.wei.orderpay.bean.user.BankcardBean;
import com.wei.orderpay.bean.user.ExtractAmountLogBean;
import com.wei.orderpay.bean.user.UserAccountBean;
import com.wei.orderpay.bean.user.UserAccountTransactionLogBean;
import com.wei.orderpay.contacts.common.APP;
import com.wei.orderpay.contacts.user.BankType;
import com.wei.orderpay.contacts.user.UserAccountTransaction;
import com.wei.orderpay.dao.order.ExtractAmountLogCommonDao;
import com.wei.orderpay.dao.order.OrderCommonDao;
import com.wei.orderpay.dao.user.BankcardCommonDao;
import com.wei.orderpay.dao.user.UserAccountCommonDao;
import com.wei.orderpay.dao.user.UserAccountTransactionLogCommonDao;
import com.wei.orderpay.service.user.UserAccountCommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userAccountCommonService")
public class UserAccountCommonServiceImpl implements UserAccountCommonService {

	@Resource
    BankcardCommonDao bankcardCommonDao;
	
	@Resource
    UserAccountCommonDao userAccountCommonDao;
	
	@Resource
    UserAccountTransactionLogCommonDao userAccountTransactionLogCommonDao;
	
	@Resource
    ExtractAmountLogCommonDao extractAmountLogCommonDao;
	
	@Resource
    OrderCommonDao orderCommonDao;
	
	@Override
	public Result saveBankCard(BankcardBean bankCard, APP app) {
		Result result = new Result();
		
		if(getBankCardList(bankCard.getGuid(),app).size()>0){//假如以前曾经添加过银行卡
			BankcardBean firstBankcard = getBankCardList(bankCard.getGuid(),app).get(0);
			if(!firstBankcard.getMobile().equals(bankCard.getMobile())){//预留手机号不同于第一张银行卡
				result.setResultCode(OrderPayResultCode.MOBILE_DIFFERENT);
				return result;
			}if (!firstBankcard.getName().equals(bankCard.getName())) {//开户姓名不同于第一张银行卡
				result.setResultCode(OrderPayResultCode.NAME_DIFFERENT);
				return result;
			}
			if (!firstBankcard.getIdentityNumber().equals(bankCard.getIdentityNumber())) {//开户身份证不同于第一张银行卡
				result.setResultCode(OrderPayResultCode.IDENTITYNUMBER_DIFFERENT);
				return result;
			}
		}
		
		bankCard.setSource(app.ordinal());
		bankcardCommonDao.saveBankcard(bankCard);
		return result;
	}

	@Override
	public List<BankcardBean> getBankCardList(int guid,APP app) {
		List<BankcardBean> list =  bankcardCommonDao.getBankcardList(guid,app.ordinal());
		for(BankcardBean bcb:list){
			bcb.setBankTitle(BankType.getBankType(bcb.getBankType()).title);  //银行名称
			bcb.setLogo(BankType.getBankType(bcb.getBankType()).logo);			//银行logo
		}
		return list;
	}

	@Override
	public double getBalance(int guid, APP app) {
		UserAccountBean userAccount = userAccountCommonDao.getUserAccountForUpdate(guid, app.ordinal());
		return userAccount == null ? 0:Double.parseDouble(CommonUtils.formatMoney(userAccount.getBalance()));
	}

	@Override
	public List<UserAccountTransactionLogBean> getTradingRecord(int guid, APP app, int page, int pageSize) {
		List<UserAccountTransactionLogBean> list = userAccountTransactionLogCommonDao.getUserAccountTransactionLog(guid,app.ordinal(),page,pageSize);
		
		for (UserAccountTransactionLogBean bean : list) {
			if (bean.getUserAccountTransactionType() == UserAccountTransaction.WITHDRAW.ordinal()) {
				ExtractAmountLogBean exBean= new ExtractAmountLogBean();
				exBean.setId(bean.getResourceID());
				exBean.setSource(app.ordinal());
				exBean = extractAmountLogCommonDao.getExtractAmountLogDetail(exBean);
				if (exBean.getStatus() == 0) {//提现中
					bean.setProcessStatus(4);
				}else if (exBean.getStatus() == 1) {//提现成功
					bean.setProcessStatus(2);
				}else if(exBean.getStatus() == 2){//提现失败
					bean.setProcessStatus(3);
				}
			}else if(bean.getUserAccountTransactionType() == UserAccountTransaction.PAY.ordinal()){
				bean.setProcessStatus(1);//收款成功
			}else if(bean.getUserAccountTransactionType() == UserAccountTransaction.PROMOTE_IN.ordinal()){
				bean.setProcessStatus(6);//推广红包收入
			}else if(bean.getUserAccountTransactionType() == UserAccountTransaction.SELLMONEY_IN.ordinal()){
				bean.setProcessStatus(7);//销售奖金收入
			}else if(bean.getUserAccountTransactionType() == UserAccountTransaction.PROMOTE_OUT.ordinal()){
				bean.setProcessStatus(8);//推广红包支出
			}else if(bean.getUserAccountTransactionType() == UserAccountTransaction.SELLMONEY_OUT.ordinal()){
				bean.setProcessStatus(9);//销售奖金支出
			}
			
			bean.setCreateDT(bean.getCreateDT().substring(0, 19));
			
		}
		return list;
	}

	@Override
	public Result doWithdrawCash(double amount, int bankCardID,int guid, APP app) {
		Result result = new Result();
		int source = app.ordinal();
		
		UserAccountBean userAccount = userAccountCommonDao.getUserAccountForUpdate(guid, app.ordinal());
		double balance = userAccount == null ? 0:Double.parseDouble(CommonUtils.formatMoney(userAccount.getBalance()));// 获取账户余额
		
		if (amount - balance > 0.00000001) {
			result.setResultCode(OrderPayResultCode.ACCOUNT_OVERSTEP);//抛出金额超出信息
			return result;
		}
		
		//交易流水号
		String tradeNo = CommonUtils.getRandomNumber(10, 99) + CommonUtils.getDateString("yyyyMMddHHmmss", System.currentTimeMillis());
		
		//提现申请记录的保存
		ExtractAmountLogBean extractAmountLog = new ExtractAmountLogBean();
		extractAmountLog.setGuid(guid);
		extractAmountLog.setBankCardID(bankCardID);
		extractAmountLog.setSource(source); 
		extractAmountLog.setExtractAmount(amount); 
		extractAmountLog.setTradeNo(tradeNo);
		extractAmountLogCommonDao.saveExtractAmountLog(extractAmountLog);
		
		int userAccountID = userAccount.getUserAccountID();
		
		balance = Double.valueOf(CommonUtils.formatMoney(balance-amount));
		//账户表更新（如果提现失败需要把金额恢复）
		int resCode = userAccountCommonDao.updateBalance(balance, guid, source);
		if(resCode == 0){
			System.out.println("余额更新失败：guid="+guid+",balance="+balance);
			throw new BusinessException(OrderPayResultCode.WITHDRAWCASH_ERROR);
		}
		
		int extractAmountLogID = extractAmountLog.getId();
		
		//账户操作日志记录
		UserAccountTransactionLogBean accountTransactionLog = new UserAccountTransactionLogBean();
		accountTransactionLog.setUserAccountID(userAccountID);
		accountTransactionLog.setSource(source);
		accountTransactionLog.setUserAccountTransactionType(UserAccountTransaction.WITHDRAW.ordinal());
		accountTransactionLog.setResourceID(extractAmountLogID);
		accountTransactionLog.setBalance(balance);
		accountTransactionLog.setCharge(-amount);
		accountTransactionLog.setRecordCode(tradeNo);
		
		userAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountTransactionLog);

		return result;
	}

	@Override
	public List<Map<String,Object>> getBankcardTypeList() {
		BankType[] bt = BankType.values();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=1;i<bt.length;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("bankType",bt[i].bankType);
			map.put("logo",bt[i].logo);
			map.put("title",bt[i].title);
			list.add(map);
		}
		return list;
	}

	@Override
	public Result deleteBandcard(int guid,int bankCardID, APP app) {
		Result result = new Result();
		int pass = bankcardCommonDao.deleteBandcard(guid,bankCardID,app.ordinal());
		if (pass==0) {
			result.setResultCode(OrderPayResultCode.NO_AUTHORITY);
		}
		return result;
	}
	
	@Override
	public List<Map<String,Object>> getExtractList(List<Integer> guidList,String tradeNo,String recordCode,String status,String startDT,String endDT,String bankcardAccountName,int bankcardNo,APP app,int pageNo, int pageSize) {
		return extractAmountLogCommonDao.getExtractAmountList(guidList,tradeNo,recordCode,status,startDT,endDT,bankcardAccountName,bankcardNo,app.ordinal(),pageNo, pageSize);
	}

	@Override
	public List<Integer> getGuidListByBankcardNoOrName(String name,int bankcardNo, APP app) {
		return bankcardCommonDao.getGuidListByBankcardNoOrName(name,bankcardNo,app.ordinal());
	}

	@Override
	public List<Map<String, Object>> getDealList(List<Integer> listBuyer,List<Integer> listSeller, String recordCode, String startDT, String endDT, APP app,int pageNo,int pageSize) {
		return orderCommonDao.getDealList(listBuyer,listSeller,recordCode,startDT,endDT,app.ordinal(),pageNo,pageSize);
	}

	@Override
	public Result doCharge(double amount, UserAccountTransaction type, int guid, APP app,String productName){
		Result result = new Result();
		UserAccountBean userAccount = userAccountCommonDao.getUserAccountForUpdate(guid, app.ordinal());
		double oldbalance = 0;//原来的账号余额
		double balance = amount;
		if(userAccount == null){//没有账户，先插入
			userAccount = new UserAccountBean();
			userAccount.setGuid(guid);
			userAccount.setSource(app.ordinal());
			userAccount.setBalance(amount);;
			userAccountCommonDao.saveUserAccount(userAccount);
		}else{//更新账户数据
			oldbalance = userAccount.getBalance();

			balance = Double.valueOf(CommonUtils.formatMoney(amount+oldbalance));

			int resCode = userAccountCommonDao.updateBalance(balance, guid, app.ordinal());
			if(resCode == 0){
				throw new BusinessException(OrderPayResultCode.ORDER_OPT_ERROR);
			}
		}
		UserAccountTransactionLogBean accountLog = new UserAccountTransactionLogBean();
		accountLog.setSource(app.ordinal());
		accountLog.setUserAccountID(userAccount.getUserAccountID());
		accountLog.setBalance(balance);
		accountLog.setCharge(amount);
		accountLog.setUserAccountTransactionType(type.ordinal());
		accountLog.setResourceID(0);
		accountLog.setProductName(productName);
		userAccountTransactionLogCommonDao.saveUserAccountTransactionLog(accountLog);
		return result;
	}
}
