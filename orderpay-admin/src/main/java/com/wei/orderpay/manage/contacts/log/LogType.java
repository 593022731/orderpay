package com.wei.orderpay.manage.contacts.log;

/** 
 * ClassName: LogType  
 * Function: 支付日志类型  
 * @author lizhi  date: 2016年4月13日 下午12:31:00
 */
public enum LogType {

	/**
	 * 占位符
	 */
	empty,
    /**
     * 1:订单退款请求
     */
	REFUND_REQ,
    
    /**
     * 2:订单退款请求成功
     */
	REFUND_REQ_SUCCESS,
	   
    /**
     * 3:订单退款已成功
     */
	REFUND_SUCCESS,
	  
    /**
     * 4:提现请求
     */
	EXTRACTION_REQ,
	
	/**
	 * 5:提现成功
	 */
	EXTRACTION_SUCCESS,
	   
    /**
     * 6:提现失败
     */
	EXTRACTION_FAILED,
	
	/**
	 * 7:业务汇款请求
	 */
	SERVICE_REMIT_REQ,
	
	/**
	 * 8:业务汇款成功
	 */
	SERVICE_REMIT_SUCCESS,
	
	/**
	 * 9:业务汇款失败
	 */
	SERVICE_REMIT_FAILED;
	
	
    
}
