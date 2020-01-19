package com.wei.orderpay.dao.order;

/**
 * 订单支付记录日志
 * 
 * @author : weihui
 * @createTime : 2016年4月25日 上午10:57:04
 * @version : 1.0
 * @description :
 *
 */
public interface OrderPayLogCommonDao {
	
	/**
	 * 保存支付日志
	 * @param orderPayCode
	 * @param payType
	 * @param totalFee
	 * @param tradeNo
	 * @param payDT
	 * @param source
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月25日 上午10:56:51
	 */
	int save(String orderPayCode,int payType,double totalFee,String tradeNo,String payDT,int source);
	
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
	
	/**
	 * 根据第三方平台交易号获取orderID
	 * @param tradeNO
	 * @param source
	 * @return
	 *
	 * @author : weih
	 * @createTime : 2016年6月7日 下午7:46:03
	 */
	int getOrderIDByTradeNo(String tradeNO,int source);
}
