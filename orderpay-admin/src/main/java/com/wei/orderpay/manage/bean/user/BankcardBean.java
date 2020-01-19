package com.wei.orderpay.manage.bean.user;

import com.jiutong.orderpay.bean.common.BaseBean;

/** 
 * @description : commonserver库bankcard表  
 * @Function: 银行卡信息  
 * @author lizhi  
 * @createTime : 2016年4月13日 下午3:01:32
 */
public class BankcardBean extends BaseBean {

	private static final long serialVersionUID = 381167120868558120L;
	
	//主键id
	private int bankCardID;
	
	//UID
	private int guid; 
	
	//储蓄卡类型(参考枚举BankType)
	private int bankType;
	
	//开户姓名
	private String name;
	
	//银行名
	private String bankTitle;
	
	//银行logo
	private String logo;
	
	//储蓄卡号
	private String cardNo;
	
	//创建时间
	private String createDT;
	
	//修改时间
	private String updateDT;
	
	//预留手机号
	private String mobile;
	//身份证
	private String identityNumber;
	//开户行省份
	private String provinceName;
	//开户行城市
	private String cityName;

	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankTitle() {
		return bankTitle;
	}

	public void setBankTitle(String bankTitle) {
		this.bankTitle = bankTitle;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getBankCardID() {
		return bankCardID;
	}

	public void setBankCardID(int bankCardID) {
		this.bankCardID = bankCardID;
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public int getBankType() {
		return bankType;
	}

	public void setBankType(int bankType) {
		this.bankType = bankType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	
	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return "BankcardBean [bankCardID=" + bankCardID + ", guid=" + guid + ", bankType="
				+ bankType + ", name=" + name + ", cardNo=" + cardNo
				+ ", createDT=" + createDT + ", updateDT=" + updateDT + "]";
	}
	
	
}
