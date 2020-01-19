package com.wei.orderpay.manage.bean.moneyio;

import com.wei.orderpay.manage.bean.common.BaseBean;

/**
 * 资金流水表
 *
 * @author : liuquan
 * @createTime : 2016年6月16日 下午12:24:51
 * @version : 1.0 
 * @description : 
 *
 */
public class MoneyIOLogBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	private int id;
	
	/**交易流水/支付订单号*/
	private String tradeNo;
	
	/**变动金额*/
	private double amount;
	
	/**操作类型*/
	private int type;
	
	/**平台*/
	private int payType;
	
	/**支付人uid*/
	private int payerGuid;
	
	/**收款人uid*/
	private int receiverGuid;
	
	private String createDT;
	
	private String updateDT;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getPayerGuid() {
		return payerGuid;
	}

	public void setPayerGuid(int payerGuid) {
		this.payerGuid = payerGuid;
	}

	public int getReceiverGuid() {
		return receiverGuid;
	}

	public void setReceiverGuid(int receiverGuid) {
		this.receiverGuid = receiverGuid;
	}

	public String getCreateDT() {
		return createDT;
	}

	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}

	public String getUpdateDT() {
		return updateDT;
	}

	public void setUpdateDT(String updateDT) {
		this.updateDT = updateDT;
	}
	
}
