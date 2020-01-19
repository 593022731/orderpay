package com.wei.orderpay.manage.contacts.user;

/**
 * 
 * @author : wang.tao
 * @createTime : 2016年4月13日 上午11:52:59
 * @version : 1.0
 * @description :账户事物操作来源类型枚举
 *
 */
public enum UserAccountTransaction {
	/**
	 * 占位符，没有意义，因为业务值从1开始
	 */
	empty,

	/** 1:收款 */
	PAY,
	
	/** 2:提现 */
	WITHDRAW ,
	
	/** 3:业务汇款 */
	REMITTANCE;
	
}
