package com.wei.orderpay.manage.contacts.order;

/**
 * 订单状态
 * 
 * @author : weihui
 * @createTime : 2016年4月12日 下午6:36:06
 * @version : 1.0
 * @description :
 *
 */
public enum OrderStatus {

	/**
	 * 占位符，没有意义，因为业务值从1开始
	 */
	empty,

	/** 1:待付款 */
	noPay,

	/** 2:待发货(买家已付款，等待卖家发货) */
	payWaitDelivery,

	/** 3:待收货(卖家已发货，等待买家收货) */
	waitGoodsReceipt,

	/** 4:已完成(买家已收货,交易成功) */
	receipted,

	/** 5:买家申请退款中 */
	applicationRefund,

	/** 6:同意，已退款 */
	refunded,

	/** 7:已驳回(买家申请退款，卖家不同意退款，联系客服，客服可以驳回退款操作) */
	arbitrationed,

	/** 8:订单已取消  */
	cancel;
}
