package com.wei.orderpay.pay.wx;

import com.wei.orderpay.pay.Payment;
import org.apache.http.NameValuePair;


public class WeixinUtils {

	public static String getRandomNumber(int n) {
		if (n <= 0)
			return null;
		String str = "";
		for (int i = 0; i < n; i++) {
			str += (int) (Math.random() * 10);
		}
		return str;
	}

	/**
	 * 统一下单
	 *
	 * @param name
	 *            商品名称
	 * @param total_fee
	 *            价格（单位：分）
	 * @param ip
	 *            ip
	 */
	public static Map<String, String> payUnifiedorder(String name,
													  int total_fee, String ip, String out_trade_no,String time_start,String time_expire) {
		UnifiedorderPayReqDate unifiedorderPayReqDate = new UnifiedorderPayReqDate(
				Payment.WX.APP_ID, Payment.WX.PARTNER_ID, "", name, "", "",
				out_trade_no, "", total_fee, ip, time_start, time_expire, "",
				Payment.WX.NOTIFY_URL, "APP", "", "");
		String postDataXML = unifiedorderPayReqDate.toXml();
		postDataXML = "\n" + postDataXML;
		System.out.println(postDataXML);

		try {
			String res = HttpUtils.postXml(Payment.WX.PAY_UNFIFIEDORDER_API,
					postDataXML);
			System.out.println(res);
			Map<String, String> map = XMLParser.getMapFromXML(res);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	/**
	 * 统一下单H5
	 *
	 * @param name
	 *            商品名称
	 * @param total_fee
	 *            价格（单位：分）
	 * @param ip
	 *            ip
	 */
	public static Map<String, String> payUnifiedorderH5(String name,
													  int total_fee, String ip, String out_trade_no,String time_start,String time_expire,String openId) {
		UnifiedorderPayReqDateH5 unifiedorderPayReqDateH5 = new UnifiedorderPayReqDateH5(
				Payment.WX.H5_APP_ID, Payment.WX.H5_MCH_ID, "", name, "", "",
				out_trade_no, "", total_fee, ip, time_start, time_expire, "",
				Payment.WX.NOTIFY_URL, "JSAPI", "", openId);
		String postDataXML = unifiedorderPayReqDateH5.toXml();
		postDataXML = "\n" + postDataXML;
		System.out.println(postDataXML);

		try {
			String res = HttpUtils.postXml(Payment.WX.PAY_UNFIFIEDORDER_API,
					postDataXML);
			System.out.println(res);
			Map<String, String> map = XMLParser.getMapFromXML(res);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	public static String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Payment.WX.PAY_SIGN_KEY);
		String result = MD5.MD5Encode(sb.toString()).toUpperCase();
		return result;
	}

	public static String genAppSignH5(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Payment.WX.H5_KEY);
		String result = MD5.MD5Encode(sb.toString()).toUpperCase();
		return result;
	}
	
	/**
	 * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @return boolean
	 */
	public static boolean isTenpaySign(Map<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		
		//通过ArrayList构造函数把map.entrySet()转换成list  
		List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String, String>>(parameters.entrySet ());  
		  
		//通过比较器实现比较排序  
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {  
		    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {  
		        return  o1.getKey().compareTo (o2.getKey()); //升序  
//		        return  o2.getValue().compareTo (o1.getValue()); //倒序  
		    }  
		});  
		
		for(Map.Entry<String,String> entry : list){
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		
		sb.append("key=" + Payment.WX.PAY_SIGN_KEY);
		
		//算出摘要
		String sign = MD5.MD5Encode(sb.toString()).toLowerCase();
		
		String tenpaySign = parameters.get("sign").toLowerCase();
		
		//debug信息
		System.out.println(sb.toString() + " => sign:" + sign +
				" tenpaySign:" + tenpaySign);
		
		return tenpaySign.equals(sign);
	}

    /**
     * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     * 微信公众号对应的商户号 H5支付
     * @return boolean
     */
    public static boolean isTenpaySignH5(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();

        //通过ArrayList构造函数把map.entrySet()转换成list
        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String, String>>(parameters.entrySet ());

        //通过比较器实现比较排序
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return  o1.getKey().compareTo (o2.getKey()); //升序
//		        return  o2.getValue().compareTo (o1.getValue()); //倒序
            }
        });

        for(Map.Entry<String,String> entry : list){
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + Payment.WX.H5_KEY);

        //算出摘要
        String sign = MD5.MD5Encode(sb.toString()).toLowerCase();

        String tenpaySign = parameters.get("sign").toLowerCase();

        //debug信息
        System.out.println(sb.toString() + " => sign:" + sign +
                " tenpaySign:" + tenpaySign);

        return tenpaySign.equals(sign);
    }

}
