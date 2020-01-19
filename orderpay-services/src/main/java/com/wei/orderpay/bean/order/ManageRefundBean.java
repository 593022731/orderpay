package com.wei.orderpay.bean.order;

import java.io.Serializable;

/**
 * 退款管理后台类
 * @author : weih
 * @createTime : 2016年6月7日 下午5:22:29
 */
public class ManageRefundBean extends RefundBean implements Serializable{

    private static final long serialVersionUID = 8385718282466932360L;
    
    /**订单号*/
	private String orderCode;
	
	/**支付订单号(传给支付平台的订单号)*/
	private String orderPayCode;
	
	/**第三方交易号*/
	private String tradeNO;
	
	/**退款人*/
	private String name;
	
	/**退款人账号*/
	private String userAccount;

	/**退款金额*/
	private double refundFee;
	
	/**支付方式(参考PayType枚举)*/
	private int payType;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderPayCode() {
		return orderPayCode;
	}

	public void setOrderPayCode(String orderPayCode) {
		this.orderPayCode = orderPayCode;
	}

	public double getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(double refundFee) {
		this.refundFee = refundFee;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getTradeNO() {
		return tradeNO;
	}

	public void setTradeNO(String tradeNO) {
		this.tradeNO = tradeNO;
	}
	
	
}
