package com.wei.orderpay.manage.bean.order;

import com.wei.orderpay.manage.bean.common.BaseBean;

/**
 * 物流表
 *
 * @author : liuquan
 * @createTime : 2016年4月13日	上午11:39:59
 * @version : 1.0 
 * @description : 
 *
 */
public class OrderLogisticsBean extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	/**主键id*/
	private int id;
	
	/**订单ID*/
	private int orderID;
	
	/**卖家UID*/
	private int guid;

	/**物流单号*/
	private String deliveryNo;
	
	/**凭证图片json url地址,最多4个图片*/
	private String voucherPics;
	
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

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getVoucherPics() {
		return voucherPics;
	}

	public void setVoucherPics(String voucherPics) {
		this.voucherPics = voucherPics;
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
