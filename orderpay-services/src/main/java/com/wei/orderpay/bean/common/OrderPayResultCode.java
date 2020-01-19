package com.wei.orderpay.bean.common;

import com.jiutong.common.ResultCode;

/**
 * 自己业务的resultCode
 * 
 * @author : weihui
 * @createTime : 2016年4月14日 下午12:22:36
 * @version : 1.0
 * @description :
 *
 */
public class OrderPayResultCode extends ResultCode{
	
	public static final ResultCode WX_GETTOKEN_ERROR = new ResultCode(20001,"微信获取Token失败");
	public static final ResultCode WX_GETPREPAYID_ERROR = new ResultCode(20002,"微信获取prepayId失败");
	public static final ResultCode WX_GETPACKAGE_ERROR = new ResultCode(20003,"微信获取package失败");
	public static final ResultCode ALI_BUILD_PARAM_ERROR = new ResultCode(20004,"支付宝组织支付参数失败");
	public static final ResultCode UNION_BUILD_PARAM_ERROR = new ResultCode(20005,"银联构建支付参数失败");
	public static final ResultCode NO_AUTHORITY = new ResultCode(20006,"无权操作");
	/**
	 * @description :你无权操作此订单
	 */
	public static final ResultCode IS_NOT_MY_ORDER = new ResultCode(10001,"你无权操作此订单");
	/**
	 * @description :订单不存在
	 */
	public static final ResultCode ORDER_IS_EMPTY = new ResultCode(10002,"订单不存在");
	/**
	 * @description :订单状态错误,请刷新
	 */
	public static final ResultCode ORDER_STATUS_ERROR = new ResultCode(10003,"订单状态错误,请刷新");
	/**
	 * @description : 订单操作失败,请刷新
	 */
	public static final ResultCode ORDER_OPT_ERROR = new ResultCode(10003,"订单操作失败,请刷新");
	/**
	 * @description : 订单不能为空
	 */
	public static final ResultCode ORDER_ISNOT_EMPTY = new ResultCode(10004,"订单不能为空");
	/**
	 * @description : 此订单卖家已发货,无法取消,请刷新
	 */
	public static final ResultCode ORDER_IS_SEND = new ResultCode(10005,"此订单卖家已发货,无法取消,请刷新");
	/**
	 * @description : 此订单状态,无法修改价格,请刷新
	 */
	public static final ResultCode ORDER_UPDATE_ERROR = new ResultCode(10006,"此订单状态,无法修改价格,请刷新");
	/**
	 * @description : 此订单已删除,请刷新
	 */
	public static final ResultCode ORDER_IS_DELETE = new ResultCode(10007,"此订单已删除,请刷新");
	/**
	 * @description : 此订单无法删除
	 */
	public static final ResultCode ORDER_CANNOT_DELETE = new ResultCode(10008,"此订单无法删除");
	/**
	 * @description : 退款金额不能超过订单金额
	 */
	public static final ResultCode REFUND_AMOUNT_ERROR = new ResultCode(10009,"退款金额不能超过订单金额");
	
	/**
	 * @description : 退款失败
	 */
	public static final ResultCode REFUND_FAILED = new ResultCode(10010,"退款失败");
	
	/**
	 * @description : 提现金额超出总金额
	 */
	public static final ResultCode ACCOUNT_OVERSTEP = new ResultCode(10011,"提现金额超出总金额");
	
	/**
	 * 手机号码和已添加信息手机号码不一致
	 */
	public static final ResultCode MOBILE_DIFFERENT = new ResultCode(10012,"手机号码和已添加信息手机号码不一致");
	
	/**
	 * 姓名和已添加信息姓名不一致
	 */
	public static final ResultCode NAME_DIFFERENT = new ResultCode(10013,"姓名和已添加信息姓名不一致");
	
	public static final ResultCode WITHDRAWCASH_ERROR = new ResultCode(10014,"提现失败");
	/**
	 * 身份证和已添加信息身份证不一致
	 */
	public static final ResultCode IDENTITYNUMBER_DIFFERENT = new ResultCode(10015,"身份证和已添加信息身份证不一致");
	
	public static final ResultCode SERVICE_REMIT_ERROR = new ResultCode(10016,"汇款失败");
	
}
