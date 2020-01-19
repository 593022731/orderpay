package com.wei.orderpay.bean.order;

import com.wei.orderpay.bean.common.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author : wang.tao
 * @createTime : 2016年6月11日 下午6:07:26
 * @description : 业务汇款表
 *
 */
public class ServiceRemit extends BaseBean implements Serializable{

    private static final long serialVersionUID = -6282265728260998506L;

    /**主键ID*/
    private int id ;
    
    /**业务uid*/
    private int guid ;
    
    /** guid 集合__用于后台查询 */
    private List<Integer> guids;

	/**订单ID*/
    private int orderID;
    
    /**订单号*/
    private String orderCode;
    
    /**供货商姓名*/
    private String supplierName;
    
    /**储蓄卡号*/
    private String cardNo;
    
    /**银行名称*/
    private String bankName;
    
    /**开户行所在省份*/
    private String provinceName;
    
    /**开户行所在城市*/
    private String cityName;
    
    /**图片json url地址,最多4个图片*/
    private String voucherPics;
    
    /**图片集合 前台使用*/
    private List<String> voucherPicsList;
    
    /**产品ID集合*/
    private String productIds;
    
    /**转账金额*/
    private double totalAmount;
    
    /**提现状态_(默认0:提现中,1:提现成功,2:提现失败)*/
    private int status;
    
    /**流水号*/
    private String tradeNo;
    
    /**创建时间*/
    private String createDT;
    
    /**修改时间*/
    private String updateDT;
    
    //其它属性
    /**业务姓名*/
    private String serviceName;

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

	public List<Integer> getGuids() {
		return guids;
	}

	public void setGuids(List<Integer> guids) {
		this.guids = guids;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getVoucherPics() {
		return voucherPics;
	}

	public void setVoucherPics(String voucherPics) {
		this.voucherPics = voucherPics;
	}

	public List<String> getVoucherPicsList() {
		return voucherPicsList;
	}

	public void setVoucherPicsList(List<String> voucherPicsList) {
		this.voucherPicsList = voucherPicsList;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
    
}
