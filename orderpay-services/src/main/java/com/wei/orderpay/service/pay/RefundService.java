package com.wei.orderpay.service.pay;

import com.wei.orderpay.bean.order.RefundBean;
import com.wei.orderpay.contacts.common.APP;

import java.util.List;

/**
 * 退款service
 * 
 * @author : weihui
 * @createTime : 2016年5月9日 上午16:36:25
 * @version : 1.0
 * @description :
 *
 */
public interface RefundService {
	
/*	*//**
	 * 退款查询(定时任务跑数据，更新未退款成功标示为已退款成)
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年5月9日 下午4:05:45
	 *//*
	void doRefundQuery(APP app);*/
	
	/**
	 * 退款操作
	 * @param orderID
	 * @param app
	 */
	boolean doRefund(int orderID, APP app);
	
    /**
     * TODO sql需要改
     * 获取未退款成功的支付宝订单(支付宝需要手动退款)
     * @param app
     * @return
     *
     * @author : weihui
     * @createTime : 2016年5月9日 下午6:22:18
     */
    List<RefundBean> getAliRefundOrders(APP app);
}
