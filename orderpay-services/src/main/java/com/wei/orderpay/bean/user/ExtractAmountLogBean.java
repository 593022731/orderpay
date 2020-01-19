package com.wei.orderpay.bean.user;

import com.wei.orderpay.bean.common.BaseBean;

/** 
 * @description : commonserver库extractamountlog表  
 * @Function: 提现记录表 
 * @author lizhi  
 * @createTime : 2016年4月13日 上午11:46:43
 */

public class ExtractAmountLogBean extends BaseBean {
    
    private static final long serialVersionUID = -2419858742803817227L;

    //主键ID
    private int id;
      
    //UID
    private int guid;
    
    //银行卡ID
    private int bankCardID;
    
    //提款金额
    private double extractAmount;
    
    //提款成功时间
    private String extractDT;
    
    //(0:提现中,1:提现成功,2:提现失败)
    private int status;
    
    //操作人(客服后台登录uid)
    private int customerID;
    
    //操作时间
    private String customUpdateDT;
    
    //创建时间
    private String createDT;
    
    //修改时间
    private String updateDT;
    
    //银联交易流水号queryId
    private String tradeNo;
    
    
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public int getBankCardID() {
		return bankCardID;
	}

	public void setBankCardID(int bankCardID) {
		this.bankCardID = bankCardID;
	}

	public double getExtractAmount() {
		return extractAmount;
	}

	public void setExtractAmount(double extractAmount) {
		this.extractAmount = extractAmount;
	}

	public String getExtractDT() {
		return extractDT;
	}

	public void setExtractDT(String extractDT) {
		this.extractDT = extractDT;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomUpdateDT() {
		return customUpdateDT;
	}

	public void setCustomUpdateDT(String customUpdateDT) {
		this.customUpdateDT = customUpdateDT;
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

	@Override
	public String toString() {
		return "ExtractAmountLogBean [id=" + id + ", guid=" + guid
				+ ", bankCardID=" + bankCardID + ", extractAmount="
				+ extractAmount + ", extractDT=" + extractDT + ", status="
				+ status + ", customerID=" + customerID + ", customUpdateDT="
				+ customUpdateDT + ", createDT=" + createDT + ", updateDT="
				+ updateDT + ", tradeNo=" + tradeNo + "]";
	}

}
