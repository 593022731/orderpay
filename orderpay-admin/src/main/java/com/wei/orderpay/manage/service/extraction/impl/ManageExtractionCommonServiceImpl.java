package com.wei.orderpay.manage.service.extraction.impl;

import com.jiutong.common.APP;
import com.jiutong.common.BusinessException;
import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.manage.bean.common.OrderPayResultCode;
import com.wei.orderpay.manage.bean.extraction.ExtractAmountLogBean;
import com.wei.orderpay.manage.bean.log.LogDataBean;
import com.wei.orderpay.manage.bean.user.BankcardBean;
import com.wei.orderpay.manage.bean.user.UserAccountBean;
import com.wei.orderpay.manage.contacts.log.LogType;
import com.wei.orderpay.manage.contacts.order.MoneyIOType;
import com.wei.orderpay.manage.contacts.order.PayType;
import com.wei.orderpay.manage.contacts.user.BankType;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.log.ManageLogDataCommonDao;
import com.wei.orderpay.manage.dao.moneyIO.impl.ManageMoneyIOLogDaoImpl;
import com.wei.orderpay.manage.dao.order.ManageExtractAmountLogCommonDao;
import com.wei.orderpay.manage.dao.user.ManageBankcardCommonDao;
import com.wei.orderpay.manage.dao.user.ManageUserAccountCommonDao;
import com.wei.orderpay.manage.pay.chainpay.ChinaPaySimpleDFUtils;
import com.wei.orderpay.manage.service.extraction.ManageExtractionCommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("manageExtractionCommonService")
public class ManageExtractionCommonServiceImpl extends BaseDao implements ManageExtractionCommonService{

	@Resource
	ManageLogDataCommonDao manageLogDataCommonDao;
	
	@Resource
	ManageExtractAmountLogCommonDao manageExtractAmountLogCommonDao;
	
	@Resource
	ManageBankcardCommonDao manageBankcardCommonDao;
	
	@Resource
	ManageUserAccountCommonDao manageUserAccountCommonDao;
	
	@Resource
	ManageMoneyIOLogDaoImpl manageMoneyIOLogDao;
	
	@Override
	public void doConfirmExtractAmount(int id, APP app) {
		int source = app.ordinal();
		
		LogDataBean logdata = new LogDataBean();
		logdata.setSource(source);
		logdata.setLogType(LogType.EXTRACTION_REQ.ordinal());
		logdata.setResourceID(id);
		manageLogDataCommonDao.saveLogData(logdata);//日志记录
		
		ExtractAmountLogBean exBean= new ExtractAmountLogBean();
		exBean.setId(id);
		exBean.setSource(source);
		exBean = manageExtractAmountLogCommonDao.getExtractAmountLogDetail(exBean);
		
		int resCode = manageExtractAmountLogCommonDao.extractamountSuccess(id,  source);
		if(resCode == 0){//重复提现
			throw new BusinessException(OrderPayResultCode.WITHDRAWCASH_ERROR);
		}
		String tradeNo = exBean.getTradeNo();
		int guid = exBean.getGuid();
		int bankCardID = exBean.getBankCardID();
		double amount = exBean.getExtractAmount();
		
		BankcardBean bankcardDetail = manageBankcardCommonDao.getBankcardDetail(guid, bankCardID, source);
			//银行名称
		String openBank = BankType.getBankType(bankcardDetail.getBankType()).title;
			
		boolean isSuccess = ChinaPaySimpleDFUtils.doDF(tradeNo, amount, bankcardDetail.getCardNo(), bankcardDetail.getName(), openBank, bankcardDetail.getProvinceName(), bankcardDetail.getCityName());
		if(!isSuccess){//提现失败
			manageExtractAmountLogCommonDao.extractamountFail(id, source);

			UserAccountBean userAccount = manageUserAccountCommonDao.getUserAccountForUpdate(guid, app.ordinal());
			double balance = userAccount == null ? 0:Double.parseDouble(CommonUtils.formatMoney(userAccount.getBalance()));// 获取账户余额
			balance = Double.valueOf(CommonUtils.formatMoney(balance+amount));
			//提现失败需要把金额恢复
			resCode = manageUserAccountCommonDao.updateBalance(balance, guid, source);
			if(resCode == 0){
				System.out.println("余额更新失败：guid="+guid+"balance="+balance);
				throw new BusinessException(OrderPayResultCode.WITHDRAWCASH_ERROR);
			}
			
			logdata.setLogType(LogType.EXTRACTION_FAILED.ordinal());
			manageLogDataCommonDao.saveLogData(logdata);//日志记录
		}else{
			logdata.setLogType(LogType.EXTRACTION_SUCCESS.ordinal());
			manageLogDataCommonDao.saveLogData(logdata);//日志记录
			
			//保存提现资金流水记录
			manageMoneyIOLogDao.saveMoneyIOLog(tradeNo, amount, 0, guid, MoneyIOType.extractMoney.ordinal(), PayType.UNIONPAY.ordinal(), source);
		}
	}
	
}
