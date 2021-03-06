package com.wei.orderpay.bean.order;

import com.wei.orderpay.bean.common.BaseBean;
import com.wei.orderpay.bean.user.RecepientBean;

import java.io.Serializable;
import java.util.List;

/**
 * 订单主表
 * 
 * @author : weihui
 * @createTime : 2016年4月12日 下午2:35:58
 * @version : 1.0
 * @description :
 *
 */
public class OrdersBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private int orderID;

	/**买家UID*/
	private int guid;
	
	/**收货地址ID*/
	private int recepientID;
	
	/**收货地址对象*/
	RecepientBean recepient;
	
	/**订单号*/
	private String orderCode;
	
	/**支付订单号(传给支付平台的订单号)*/
	private String orderPayCode;
	
	/**订单金额*/
	private double amount;

	/**订单实际支付金额*/
	private double actualPayment;

	/**订单状态(参考枚举OrderStatus)*/
	private int status;

	/**订单变更当前状态时间*/
	private String statusDT;
	
	/**支付方式(参考PayType枚举)*/
	private int payType;

	/**订单备注*/
	private String memo;
	
	/**创建时间*/
	private String createDT;
	
	/**修改时间*/
	private String updateDT;
	
	/**订单详情*/
	private List<OrderDetailBean> orderDetails;
	
	/**订单状态变更*/
	List<OrderTransactionLogBean> orderTransactionLogs;
	
	/**订单物流信息*/
	List<OrderLogisticsBean> orderLogistics;
	/**
	 * 卖家店铺ID
	 */
	private  int storeID;

	/**
	 * 邮费
	 */
	private double postage;

	/**
	 * 订单类型 0 app  1 H5
	 */
	private int type = 0;

	/**
	 * 最后发起支付时间
	 */
	private long lastPayDT;


	public long getLastPayDT() {
		return lastPayDT;
	}

	public void setLastPayDT(long lastPayDT) {
		this.lastPayDT = lastPayDT;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getPostage() {
		if(postage>0) {
			return postage;
		}
		else{
			double sumPostage = 0d;
			if(orderDetails != null && orderDetails.size()>0 ){
				for(OrderDetailBean d:orderDetails){
					sumPostage += d.getPostage();
				}
			}

			return sumPostage;
		}
	}

	public void setPostage(double postage) {
		this.postage = postage;
	}
	 

	public List<OrderLogisticsBean> getOrderLogistics() {
		return orderLogistics;
	}

	public void setOrderLogistics(List<OrderLogisticsBean> orderLogistics) {
		this.orderLogistics = orderLogistics;
	}

	public int getStoreID() {
		return storeID;
	}

	public void setStoreID(int storeID) {
		this.storeID = storeID;
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

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getActualPayment() {
		return actualPayment;
	}

	public void setActualPayment(double actualPayment) {
		this.actualPayment = actualPayment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDT() {
		return statusDT;
	}

	public void setStatusDT(String statusDT) {
		this.statusDT = statusDT;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public int getRecepientID() {
		return recepientID;
	}

	public void setRecepientID(int recepientID) {
		this.recepientID = recepientID;
	}

	public List<OrderDetailBean> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailBean> orderDetails) {
		this.orderDetails = orderDetails;
		double sumPostage = 0d;
		if(orderDetails != null && orderDetails.size()>0 ){
			for(OrderDetailBean d:orderDetails){
				sumPostage += d.getPostage();
			}
		}

		if(sumPostage>0) this.postage = sumPostage;
	}

	public List<OrderTransactionLogBean> getOrderTransactionLogs() {
		return orderTransactionLogs;
	}

	public void setOrderTransactionLogs(
			List<OrderTransactionLogBean> orderTransactionLogs) {
		this.orderTransactionLogs = orderTransactionLogs;
	}

	public String getOrderPayCode() {
		return orderPayCode;
	}

	public void setOrderPayCode(String orderPayCode) {
		this.orderPayCode = orderPayCode;
	}

	public RecepientBean getRecepient() {
		return recepient;
	}

	public void setRecepient(RecepientBean recepient) {
		this.recepient = recepient;
	}

}
