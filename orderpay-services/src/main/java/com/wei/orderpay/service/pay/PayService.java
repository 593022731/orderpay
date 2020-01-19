package com.wei.orderpay.service.pay;

import com.wei.orderpay.contacts.common.APP;

import java.util.Map;

/**
 * 支付service
 * 
 * @author : weihui
 * @createTime : 2016年4月25日 上午10:36:25
 * @version : 1.0
 * @description :
 *
 */
public interface PayService {
	
	/**
	 * 构建微信支付参数，for APP端调起支付
	 * @param orderDesc
	 * @param price
	 * @param orderId
	 * @param ip
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月28日 下午3:38:40
	 */
	String buildWXPayParams(String orderDesc,double price,String orderId,String ip);

	/**
	 * H5 微信支付
	 * song.ty 2016.9.20
	 * @param orderDesc
	 * @param price
	 * @param orderId
	 * @param ip
	 * @return
	 */
	String buildWXPayParamsH5(String orderDesc,double price,String orderId,String ip,String openID);
	
	/**
	 * 构建支付宝支付参数，for APP端调起支付
	 * @param orderDesc
	 * @param price
	 * @param orderId
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月28日 下午3:51:47
	 */
	String buildAliPayParams(String orderDesc,double price,String orderId);

	/**
	 * 不加密
	 * @param orderDesc
	 * @param price
	 * @param orderId
	 * @return
	 */
	String buildAliPayParamsNoBase64(String orderDesc,double price,String orderId);
	
	/**
	 * 构建银联支付参数，for APP端调起支付
	 * @param orderDesc
	 * @param price
	 * @param orderId
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年5月6日 下午5:12:12
	 */
	String buildUnionPayParams(String orderDesc,double price,String orderId);
	
	/**
	 * 微信支付回调
	 * @param xml 微信返回的参数
	 * @param app
	 * @return 返回支付订单号
	 *
	 * @author : weihui
	 * @createTime : 2016年4月28日 下午4:39:22
	 */
	String doWxPayCall(String xml, APP app);
	
	/**
	 * 阿里支付异步回调
	 * @param parameterMap
	 * @param trade_status
	 * @param out_trade_no
	 * @param total_fee
	 * @param subject
	 * @param trade_no
	 * @param gmt_payment
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年4月28日 下午5:01:09
	 */
	boolean doAliPayCall(Map<?, ?> parameterMap, String trade_status,
			String out_trade_no, String total_fee, String subject,String trade_no,
			String gmt_payment,APP app); 
	
	/**
	 * 银联支付异步回调(交易和退款用的是一样的回调代码)
	 * @param reqParam
	 * @param type 1:交易，2:退款
	 * @param app
	 * @return
	 *
	 * @author : weihui
	 * @createTime : 2016年5月6日 下午6:11:19
	 */
	boolean doUnionPayCall(Map<String, String> reqParam,int type, APP app);
	
	/**
	 * 支付宝退款回调
	 * @param parameterMap
	 * @param app
	 * @return
	 */
	boolean doAliRefundCall(Map<?, ?> parameterMap,APP app); 
	
	/**
	 * 确认提现
	 * @param id 提现记录主键ID
	 * @param app
	 * @return
	 *
	 * @author : weih
	 * @createTime : 2016年6月1日 下午3:19:06
	 */
	void doConfirmExtractAmount(int id,APP app);
	
}
