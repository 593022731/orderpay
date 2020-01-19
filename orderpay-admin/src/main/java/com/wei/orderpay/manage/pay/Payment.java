package com.wei.orderpay.manage.pay;

import com.jiutong.common.tools.PropertiesUtils;


/**
 * 
 * @author : Ares.yi
 * @createTime : 2014-5-13 下午03:08:23
 * @version : 1.0
 * @description :
 *
 */
public class Payment {
	
	//58.247.112.82:9991   外网ip对应内网ip192.168.254.55:80
	//140.206.176.238:9081 外网ip对应内网ip192.168.254.10:8081

	/** 支付宝 **/
	public static final class ALIPAY {
		/** 合作伙伴id **/
		public static final String PARTNER = PropertiesUtils.getProperties("ali.partner","2088912672199440"); 
		
		/** 卖家帐户 **/
		public static final String SELLER_EMAIL = PropertiesUtils.getProperties("ali.seller_id","13488858@qq.com");
		
		/** 通知接收URL **/
		public static final String NOTIFY_URL = PropertiesUtils.getProperties("ali.notifyUrl","http://192.168.254.55/9tong-zml-app/paynotify/ali");
		
		/** 商户私钥，自助生成**/
		public static final String PRIVATE_KEY = PropertiesUtils.getProperties("ali.private.key","MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALx3D1MnYsG1g4UmpYgnL5RJl8K6cF5XYJxMnL/GVZipbeRjnlXgZuMQPLZTOeZZ5o03u0Y1XV1BzsL8HctLeI4pj7eHWWgMRgInhKtNow2jWzyvJuu+mn8U+SB8QyN7w8JZYIT0eDBvt0YvsMKzX+YMnpq4KYTcCG40JkdBxefjAgMBAAECgYEAqP2lSy282u80sFc4BL8GZdQbdauRiLaW1W/I+hkQtaPu+uTsnBpsh1wO5Hn6J/giyCvaDBlbz3xrtuE0og7ZWC4WVofLMxj8e1Wb1SWY0/LZ7Du1Y4Mhf8Yv1cJoKFQINdLY4AIZye7pPiliqnC7wnYrZ3cN0FnVlOUVQuf1fZECQQDhXpcJhbtJX/2K5f/Mfz2wEbzU9TBIpNRgsvqj4gxiq7Qp1Ij4OCH6jgzB+DwSrGFF9z1CSHUyHa+lvTogCNvJAkEA1hRu8+/de1SCvGp8iXTMbW46MRiYYrsQ2TnkyWO0hsMo6ECaWe4CVdV4GJlJkU83sgA/vDYA60i4a2/gsjtkSwJAKnZkBstHql+HqfAD7kfbG5178rPv0QAdc8rFJrOkmcK4Q1lkJUCORmMVTEa1Kt+HxGL9C8wTKMonGhc5KGgC4QJBALRyisdxMuZG6N/6fji6y6md1M0XZzMpbN09UFx0f3FsRSnncnNFDJDfG5jYdEtYU6DlVtPFTWcFZ/WfWu3A5BsCQQDCAXXHPilEXu3Nvo8jUm1ZuWbZUEyiwbU62Wxxn+dcivHe6VDCBjLawHYOJltAkVJtuEUkCdUDTj7eNinUer8S");
		
		/** 阿里退款异步通知接收URL **/
		public static final String REFUND_NOTIFY_URL = PropertiesUtils.getProperties("ali.refund.notifyUrl","http://58.247.112.82:9991/9tong-zml-app/paynotify/aliRefund");
		
		/** 快速付款交易服务 **/
		public static final String SERVICE = "mobile.securitypay.pay";

		/** 支付宝的给的公钥，写死**/
		public static final String ALI_PUBLIC_KEY  =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	
		/** 安全校验码 **/
//		public static final String KEY = "rbgzst4uolh3rq9jmljwn8srzzsgaqxo"; 
		
		/** 类型.1代表商品购买 **/
		public static final String PAYMENT_TYPE = "1";

		/** 签名加密方式 （RSA） **/
		public static final String SIGN_TYPE = "RSA";

		/** 传输字符编码 （UTF8） **/
		public static final String INPUT_CHARSET = "utf-8";
	}
	
	
	/** chinapay支付 **/
	public static final class CHINAPAY {
		/** 商户号码 **/
		public static String MER_ID = PropertiesUtils.getProperties("chinapay.merId","808080211880168");
		
		/** 私钥路径 **/
		public static final String MERKEYPATH = PropertiesUtils.getProperties("chinapay.merKeyPath","D:/certstest/MerPrK_808080211880168_20160530145821.key");
		
		/** 公钥路径 **/
		public static final String PUBKEYPATH = PropertiesUtils.getProperties("chinapay.pubKeyPath","D:/certstest/PgPubk.key");
		
		/**单笔代付请求url */
		public static String URL = PropertiesUtils.getProperties("chinapay.url","http://sfj-test.chinapay.com/dac/SinPayServletGBK");
	}
}
