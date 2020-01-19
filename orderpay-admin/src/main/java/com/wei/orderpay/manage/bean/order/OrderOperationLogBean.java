package com.wei.orderpay.manage.bean.order;

import com.wei.orderpay.manage.bean.common.BaseBean;

import java.io.Serializable;

/**
 * 订单操作日志对象
 * 
 * @author : weihui
 * @createTime : 2016年4月13日 上午11:23:54
 * @version : 1.0
 * @description :
 *
 */
public class OrderOperationLogBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**主键ID*/
	private int id;

	/**订单ID*/
	private int orderID;
	
	/**用户角色(参考UserRole枚举0:买家,1:卖家,2:客服)*/
	private int userRole;

	/**对应用户角色的uid(买家uid,卖家uid,客服后台登录uid)*/
	private int userID;

	/**操作类型(参考OpType枚举)*/
	private int opTypeID;

	/**创建时间*/
	private String createDT;
	
	/**创建时间*/
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

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getOpTypeID() {
		return opTypeID;
	}

	public void setOpTypeID(int opTypeID) {
		this.opTypeID = opTypeID;
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
