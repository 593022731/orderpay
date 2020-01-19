package com.wei.orderpay.contacts.user;

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
	REMITTANCE,


	/** 4:推广红包收入 */
	PROMOTE_IN,

	/** 5:销售奖金收入 */
	SELLMONEY_IN,

	/** 6:推广红包支出 */
	PROMOTE_OUT,

	/** 7:销售奖金支出 */
	SELLMONEY_OUT;
	
}
