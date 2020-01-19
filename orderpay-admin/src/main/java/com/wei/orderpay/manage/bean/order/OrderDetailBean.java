package com.wei.orderpay.manage.bean.order;

import com.wei.orderpay.manage.bean.common.BaseBean;

import java.io.Serializable;

/**
 * 订单详情
 * 
 * @author : weihui
 * @createTime : 2016年4月13日 上午11:08:16
 * @version : 1.0
 * @description :
 *
 */

public class OrderDetailBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**主键ID*/
	private int id;

	/**订单ID*/
	private int orderID;
	
	/**卖家UID*/
	private int guid;
	
	/**店铺ID*/
	private int storeID;
	
	/**产品ID*/
	private int productID;
	
	/**报价ID(可以对报价直接下订单)*/
	private int userBidID;

	/**订单数量*/
	private int quantity;

	/**商品价格*/
	private double qPrice;

	/**创建时间*/
	private String createDT;
	
	/**修改时间*/
	private String updateDT;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getqPrice() {
		return qPrice;
	}

	public void setqPrice(double qPrice) {
		this.qPrice = qPrice;
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

	public int getUserBidID() {
		return userBidID;
	}

	public void setUserBidID(int userBidID) {
		this.userBidID = userBidID;
	}

	public int getStoreID() {
		return storeID;
	}

	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
}
