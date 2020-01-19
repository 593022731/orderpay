package com.wei.orderpay.manage.bean.user;

import com.jiutong.orderpay.bean.common.BaseBean;

/**
 * 收货地址
 *
 * @author : liuquan
 * @createTime : 2016年4月13日	上午11:40:12
 * @version : 1.0 
 * @description : 
 *
 */
public class RecepientBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private int recepientID;
	
	/**买家UID*/
	private int guid;
	
	/**区域ID*/
	private int areaID;
	
	/**电话*/
	private String mobile;
	
	/**详细地址*/
	private String detail;
	
	/**收货人姓名*/
	private String name;
	
	/**创建时间*/
	private String createDT;
	
	/**修改时间*/
	private String updateDT;

	/**是否归档(0可以查看 1历史记录)*/
	private int isArchived;
	
	/**是否已删除(0未删除  1已删除)*/
	private int isDeleted;
	
	
	public int getIsArchived() {
		return isArchived;
	}

	public void setIsArchived(int isArchived) {
		this.isArchived = isArchived;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getRecepientID() {
		return recepientID;
	}

	public void setRecepientID(int recepientID) {
		this.recepientID = recepientID;
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
