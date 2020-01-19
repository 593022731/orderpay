package com.wei.orderpay.manage.dao.report;

import com.wei.orderpay.manage.contacts.common.APP;

import java.util.Map;

public interface ReportDao {

	/**
	 * 获取指定日期间每日 订单总额
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:49:18
	 */
	Map<Integer, String> getTotalOrderAmount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 已付款订单总额
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:50:53
	 */
	Map<Integer, String> getTotalPayOrderAmount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:50:56
	 */
	Map<Integer, String> getTotalOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 已付款订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:50:59
	 */
	Map<Integer, String> getTotalPayOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 商品订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:02
	 */
	Map<Integer, String> getTotalProductOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 报价订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:06
	 */
	Map<Integer, String> getTotalBidOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 已取消订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:10
	 */
	Map<Integer, String> getTotalCancelOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 支付宝支付订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:13
	 */
	Map<Integer, String> getTotalAliPayOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 微信支付订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:16
	 */
	Map<Integer, String> getTotalWXPayOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 银联支付订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:20
	 */
	Map<Integer, String> getTotalChinaPayOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 已退款订单总数
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:23
	 */
	Map<Integer, String> getTotalRefundOrderCount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 已退款订单总额
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:25
	 */
	Map<Integer, String> getTotalRefundOrderAmount(String createDTString,
			String endDTString,APP app,int type);

	/**
	 * 获取指定日期间每日 客单价
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月4日 上午10:51:28
	 */
	Map<Integer, String> getTotalCustomerPrice(String createDTString,
			String endDTString,APP app,int type);

	Map<Integer, String> getPayedProductCategoryReports(String createDTString,
			String endDTString, APP app, int categoryId,int type);

}
