package com.wei.orderpay.manage.bean.order;

import com.wei.orderpay.manage.bean.common.BaseBean;

/**
 * 订单变更事物日志记录表
 *
 * @author : liuquan
 * @createTime : 2016年4月13日	上午11:40:06
 * @version : 1.0 
 * @description : 
 *
 */
public class OrderTransactionLogBean extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	
	/**主键id*/
	private int id;
	
	/**订单ID*/
	private int orderID;
	
	/**订单变更状态(参考枚举OrderStatus)*/
	private int status;
	
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

	public String getCreateDT() {
		return createDT;
	}

	public void setCreateDT(String createDT) {
		this.createDT = createDT;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUpdateDT() {
		return updateDT;
	}

	public void setUpdateDT(String updateDT) {
		this.updateDT = updateDT;
	}
}
