package com.wei.orderpay.manage.bean.order;

/**
 * 业务订单Bean
 * 
 * @author : lizhi
 * @creatTime : 2016年6月7日下午4:07:14
 * @version : 1.0
 * @description : 
 *
 */
public class ServiceOrderBean extends OrdersBean{

	private static final long serialVersionUID = 5486365084318077386L;
	
	/** 业务订单状态 */
	private int serviceOrderStatus;

	public int getServiceOrderStatus() {
		return serviceOrderStatus;
	}

	public void setServiceOrderStatus(int serviceOrderStatus) {
		this.serviceOrderStatus = serviceOrderStatus;
	}

	
	
	
}
