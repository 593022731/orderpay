package com.wei.orderpay.manage.contacts.order;
/**
 * 支付方式枚举   
 * @description :和调用的jar 中的 PayType Enum顺序保持一致
 * @Function:   
 * @author lizhi  
 * @createTime : 2016年4月20日下午3:03:49
 */
public enum PayType {
	
	/**
	 * 占位空位0
	 */
	EMPTY,
	
	/**
	 * 支付宝
	 */
	ALIPAY,
	
	/**
	 * 微信支付
	 */
	WEIXINPAY,
	
	/**
	 * 银联支付
	 */
	UNIONPAY
}
