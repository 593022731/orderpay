package com.wei.orderpay.manage.service.report;

import com.wei.orderpay.manage.contacts.common.APP;

import java.util.List;
import java.util.Map;

/**
 * 订单报表
*
* @author : song.ty
* @createTime : 2016年8月2日 下午4:34:39
* @version : 1.0 
* @description : 
*
*
 */
public interface ReportService {


	/**
	 * 订单总表统计
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月2日 下午4:38:28
	 */
	List<Map<String, String>> getAllReports(String createDTString,String endDTString,APP app,int type);
	
	/**
	 * 商品付款订单分类统计
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月2日 下午4:39:01
	 */
	List<Map<String, String>> getPayedProductCategoryReports(String createDTString,String endDTString,APP app,int type);
	
	/**
	 * 付款方式订单统计
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月2日 下午4:40:01
	 */
	List<Map<String, String>> getPayTypeReports(String createDTString,String endDTString,APP app,int type);
	
	/**
	 * 退款完成订单统计
	 * @param createDTString
	 * @param endDTString
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年8月2日 下午4:40:08
	 */
	List<Map<String, String>> getRefundedReports(String createDTString,String endDTString,APP app,int type);
}
