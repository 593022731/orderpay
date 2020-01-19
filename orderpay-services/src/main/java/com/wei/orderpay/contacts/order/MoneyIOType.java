package com.wei.orderpay.contacts.order;

public enum MoneyIOType {
	/**
	 * 占位符，没有意义，因为业务值从1开始
	 */
	empty,
	
	/**
	 * 提现
	 */
	extractMoney,
	
	/**
	 * 交易
	 */
	deal,
	
	/**
	 * 退款
	 */
	refund;
}
