package com.wei.orderpay.manage.dao.order;

/**
 * 订单支付记录日志
 * 
 * @author : weihui
 * @createTime : 2016年4月25日 上午10:57:04
 * @version : 1.0
 * @description :
 *
 */
public interface ManageOrderPayLogCommonDao {
	
	
	/**
	 * 查询第三方平台交易号
	 * @param orderPayCode
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年5月9日 下午12:00:21
	 */
	String getTradeNo(String orderPayCode,int source);
	
}
