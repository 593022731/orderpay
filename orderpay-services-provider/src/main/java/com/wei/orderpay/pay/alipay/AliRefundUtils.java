package com.wei.orderpay.pay.alipay;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.pay.Payment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付宝退款
 * 
 * @author : weihui
 * @createTime : 2016年5月3日 下午4:51:04
 * @version : 1.0
 * @description :
 *
 */
public class AliRefundUtils {

	/**
	 * 请求退款
	 * 参考文档：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.mpLows&treeId=66&articleId=103600&docType=1
	 * @param tradeNo 支付宝交易号
	 * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
	 * @return html代码form表单
	 * @throws Exception
	 *
	 * @author : weihui
	 * @createTime : 2016年5月3日 下午6:45:55
	 */
	public static String getRequestForm(String tradeNo,double refund_amount) throws Exception{
		Map<String,String> order = new LinkedHashMap<String,String>();
		order.put("service", "refund_fastpay_by_platform_pwd");
		order.put("partner", Payment.ALIPAY.PARTNER);
		order.put("_input_charset", Payment.ALIPAY.INPUT_CHARSET);
		order.put("notify_url", Payment.ALIPAY.REFUND_NOTIFY_URL);
		order.put("seller_user_id", Payment.ALIPAY.PARTNER);	
		order.put("refund_date", CommonUtils.getDateString("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()));//退款日期 时间格式 yyyy-MM-dd HH:mm:ss
		order.put("batch_no", CommonUtils.getDateString("yyyyMMdd", System.currentTimeMillis()) + CommonUtils.getDateString("MMddHHmmss", System.currentTimeMillis()) + CommonUtils.getRandomNumber(10, 999));//格式为：退款日期（8位）+流水号（3～24位）。不可重复，且退款日期必须是当天日期 ，例如：201101120001
		order.put("batch_num", "1");//总笔数
		
		order.put("detail_data", tradeNo+"^"+refund_amount+"^退款");//退款详细数据(支付宝交易号^退款金额^备注)多笔请用#隔开，例如：2011011201037066^5.00^协商退款
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(order,"get","确认");
		return sHtmlText;
	}
	
	public static String getRequestForm(List<Map<String, String>> data) throws Exception{
		Map<String,String> order = new LinkedHashMap<String,String>();
		order.put("service", "refund_fastpay_by_platform_pwd");
		order.put("partner", Payment.ALIPAY.PARTNER);
		order.put("_input_charset", Payment.ALIPAY.INPUT_CHARSET);
		order.put("notify_url", Payment.ALIPAY.REFUND_NOTIFY_URL);
		order.put("seller_user_id", Payment.ALIPAY.PARTNER);	
		order.put("refund_date", CommonUtils.getDateString("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis()));//退款日期 时间格式 yyyy-MM-dd HH:mm:ss
		order.put("batch_no", CommonUtils.getDateString("yyyyMMdd", System.currentTimeMillis()) + CommonUtils.getDateString("MMddHHmmss", System.currentTimeMillis()) + CommonUtils.getRandomNumber(10, 999));//格式为：退款日期（8位）+流水号（3～24位）。不可重复，且退款日期必须是当天日期 ，例如：201101120001
		order.put("batch_num", "" + data.size());//总笔数
		StringBuffer sb = new StringBuffer(); 
		for(int i = 0;i<data.size();i++){
			Map<String, String> map = data.get(i);
			String tradeNo = map.get("tradeNo");
			String refund_amount = map.get("refund_amount");
			
			if(i > 0){
				sb.append("#");
			}
			sb.append(tradeNo).append("^").append(refund_amount).append("^退款");
		}
		
		order.put("detail_data", sb.toString());//退款详细数据(支付宝交易号^退款金额^备注)多笔请用#隔开，例如：2011011201037066^5.00^协商退款
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(order,"get","确认");
		return sHtmlText;
	}
	
}
