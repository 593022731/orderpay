package com.wei.orderpay.bean.order;

import com.wei.orderpay.bean.common.BaseBean;

public class OrdersReason extends BaseBean {

	/**
	 * @description :
	 */
	private static final long serialVersionUID = 7716064029754903875L;

	/** 主键id */
	private int id;
	
	/** 订单Id */
	private int orderID;
	
	/** 理由类型 参考枚举OrderReasonType */
	private int reasonType;
	
	/** 理由 */
	private String reason;
	
	/** 创建时间 */
	private String createDT;
	
	/** 修改时间 */
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

	public int getReasonType() {
		return reasonType;
	}

	public void setReasonType(int reasonType) {
		this.reasonType = reasonType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
