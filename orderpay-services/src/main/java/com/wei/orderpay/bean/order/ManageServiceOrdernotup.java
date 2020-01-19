package com.wei.orderpay.bean.order;

import java.io.Serializable;

/**
 * 后台订单对象
 * 
 * @author : lizhi
 * @creatTime : 2016年5月16日下午2:18:44
 * @version : 1.0
 * @description : 
 *
 */
public class ManageServiceOrdernotup implements Serializable {

	private static final long serialVersionUID = -307772672436351442L;

	/** 用户ID**/
	private int userID;
	
	/**名称**/
	private String name;
	
	/**账号**/
	private String userAccount;
	
	/**店铺名称**/
	private String storeTitle;
	
	/**订单号*/
	private String orderCode;
	
	/**买家**/
	private String buyerName;
	
	/**买家账号**/
	private String buyerUserAccount;
	
	/**业务**/
	private String service;
	
	/**业务账号**/
	private String servicerUserAccount;
	
	/**汇款账户 (默认0 未完善， 1为已完成)*/
	private int accountStatus;
	
	/**下单时间*/
	private String createDT;
	
	/**订单金额*/
	private double amount;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
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

	public String getStoreTitle() {
		return storeTitle;
	}

	public void setStoreTitle(String storeTitle) {
		this.storeTitle = storeTitle;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerUserAccount() {
		return buyerUserAccount;
	}

	public void setBuyerUserAccount(String buyerUserAccount) {
		this.buyerUserAccount = buyerUserAccount;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getServicerUserAccount() {
		return servicerUserAccount;
	}

	public void setServicerUserAccount(String servicerUserAccount) {
		this.servicerUserAccount = servicerUserAccount;
	}

	public int getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getCreateDT() {
		return createDT;
	}

	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
