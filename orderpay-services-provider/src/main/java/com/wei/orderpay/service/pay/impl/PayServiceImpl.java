package com.wei.orderpay.service.pay.impl;

import com.jiutong.common.BusinessException;
import com.jiutong.common.tools.CommonUtils;
import com.jiutong.common.tools.StringUtils;
import com.wei.orderpay.bean.common.OrderPayResultCode;
import com.wei.orderpay.bean.log.LogDataBean;
import com.wei.orderpay.bean.order.OrdersBean;
import com.wei.orderpay.bean.user.BankcardBean;
import com.wei.orderpay.bean.user.ExtractAmountLogBean;
import com.wei.orderpay.bean.user.UserAccountBean;
import com.wei.orderpay.contacts.common.APP;
import com.wei.orderpay.contacts.log.LogType;
import com.wei.orderpay.contacts.order.MoneyIOType;
import com.wei.orderpay.contacts.order.OpType;
import com.wei.orderpay.contacts.order.OrderStatus;
import com.wei.orderpay.contacts.order.PayType;
import com.wei.orderpay.contacts.user.BankType;
import com.wei.orderpay.dao.log.LogDataCommonDao;
import com.jiutong.orderpay.dao.order.*;
import com.wei.orderpay.dao.user.BankcardCommonDao;
import com.wei.orderpay.dao.user.UserAccountCommonDao;
import com.wei.orderpay.dao.user.UserAccountTransactionLogCommonDao;
import com.wei.orderpay.pay.Payment;
import com.wei.orderpay.pay.alipay.AlipayCore;
import com.wei.orderpay.pay.alipay.AlipayNotify;
import com.wei.orderpay.pay.alipay.RSA;
import com.wei.orderpay.pay.chinapay.ChinaPaySimpleDFUtils;
import com.wei.orderpay.pay.unionpay.AcpService;
import com.wei.orderpay.pay.unionpay.SDKConfig;
import com.wei.orderpay.pay.wx.RandomStringGenerator;
import com.wei.orderpay.pay.wx.WeixinUtils;
import com.wei.orderpay.pay.wx.XMLUtil;
import com.wei.orderpay.service.order.OrderCommonService;
import com.wei.orderpay.service.pay.PayService;
import com.wei.orderpay.service.pay.RefundService;
import com.wei.orderpay.dao.order.*;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("all")
@Service("payService")
public class PayServiceImpl implements PayService {

	@Resource
    OrderPayLogCommonDao orderPayLogCommonDao;

	@Resource
    OrderCommonDao orderCommonDao;
	
	@Resource
    OrderCommonService orderCommonService;
	
	@Resource
    OrderDetailCommonDao orderDetailCommonDao;
	
	@Resource
    UserAccountCommonDao userAccountCommonDao;
	
	@Resource
    UserAccountTransactionLogCommonDao userAccountTransactionLogCommonDao;
	
	@Resource
    RefundService refundService;
	
	@Resource
    ExtractAmountLogCommonDao extractAmountLogCommonDao;

	@Resource
    BankcardCommonDao bankcardCommonDao;
	
	@Resource
    LogDataCommonDao logDataCommonDao;
	
	@Resource
    MoneyIOLogDao moneyIOLogDao;
	
	@Resource
	ServiceRemitCommonDao serviceRemitCommonDao;
	
	@Override
	public String buildWXPayParams(String productDesc,double price,String orderId,String ip){

		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String time_start = f.format(new Date(System.currentTimeMillis()));
		String time_expire = f.format(new Date(System.currentTimeMillis()+ Payment.TIMEOUT_MILLISECOND));
		/**
		 * 统一下单接口
		 */
		Map<String, String> map = WeixinUtils.payUnifiedorder(productDesc,
				Integer.valueOf(CommonUtils.formatMoney("0",price * 100)), ip, orderId,time_start,time_expire);
		
		if (map == null || CommonUtils.isEmptyString(map.get("prepay_id"))) {
			throw new BusinessException(OrderPayResultCode.WX_GETPREPAYID_ERROR);
		}
		
		
		String app_id = map.get("appid");
		/**
		 * 获取预支付id
		 */
		String prepayid = map.get("prepay_id");

		/**
		 * 创建预支付sign签名
		 */
		String noceStr = RandomStringGenerator
				.getRandomStringByLength(32);
		String timeStamp = String
				.valueOf((System.currentTimeMillis() / 1000));
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", app_id));
		signParams.add(new BasicNameValuePair("noncestr", noceStr));
		signParams.add(new BasicNameValuePair("package", "Sign=WXPay"));
		signParams.add(new BasicNameValuePair("partnerid",
				 Payment.WX.PARTNER_ID));
		signParams.add(new BasicNameValuePair("prepayid", prepayid));
		signParams.add(new BasicNameValuePair("timestamp", timeStamp));
		String sign = WeixinUtils.genAppSign(signParams);
		
		/**
		 * 返回手机端支付需要的全部参数
		 */
		JSONObject json  = new JSONObject();
		json.put("appid", Payment.WX.APP_ID);
		json.put("partnerid", Payment.WX.PARTNER_ID);
		json.put("prepayid", prepayid);
		json.put("package", "Sign=WXPay");
		json.put("noncestr", noceStr);
		json.put("timestamp", timeStamp);
		json.put("sign", sign);
		String result = null;
		try {
			result = StringUtils.base64Encode(json.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}



	@Override
	public String buildWXPayParamsH5(String productDesc,double price,String orderId,String ip,String openID){
		/**
		 * 统一下单接口
		 */
		Map<String, String> map = WeixinUtils.payUnifiedorderH5( productDesc,
				Integer.valueOf(CommonUtils.formatMoney("0",price * 100)), ip, orderId,"","",openID);

		if (map == null || CommonUtils.isEmptyString(map.get("prepay_id"))) {
			throw new BusinessException(OrderPayResultCode.WX_GETPREPAYID_ERROR);
		}


		String app_id = map.get("appid");
		/**
		 * 获取预支付id
		 */
		String prepayid = map.get("prepay_id");

		/**
		 * 创建预支付sign签名
		 */
		String noceStr = RandomStringGenerator
				.getRandomStringByLength(32);
		String timeStamp = String
				.valueOf((System.currentTimeMillis() / 1000));
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appId", app_id));
		signParams.add(new BasicNameValuePair("nonceStr", noceStr));
		signParams.add(new BasicNameValuePair("package", "prepay_id="+prepayid));
		signParams.add(new BasicNameValuePair("signType", "MD5"));
		signParams.add(new BasicNameValuePair("timeStamp", timeStamp));
		String sign = WeixinUtils.genAppSignH5(signParams);

		/**
		 * 返回手机端支付需要的全部参数
		 */
		JSONObject json  = new JSONObject();
		json.put("appId", Payment.WX.H5_APP_ID);
		json.put("package", "prepay_id="+prepayid);
		json.put("nonceStr", noceStr);
		json.put("timeStamp", timeStamp);
        json.put("signType", "MD5");
		json.put("paySign", sign);
		return json.toString();
	}

	
	@Override
	public String buildAliPayParams(String orderDesc,double price,String orderId){
		return buildAliPayParamsCommon(orderDesc,price,orderId,true);
	}

	@Override
	public String buildAliPayParamsNoBase64(String orderDesc,double price,String orderId){
		return buildAliPayParamsCommon(orderDesc,price,orderId,false);
	}

	/**
	 * 支付宝 app 网页通用支付
	 * @param orderDesc
	 * @param price
	 * @param orderId
	 * @param base64 true app  false 网页
	 * @return
	 */
	private String buildAliPayParamsCommon(String orderDesc,double price,String orderId,boolean base64){

		String result = null;
		try {
			Map<String,String> order = new LinkedHashMap<String,String>();
			if(base64){
				order.put("service", Payment.ALIPAY.SERVICE);
				order.put("partner", Payment.ALIPAY.PARTNER);
				order.put("_input_charset", Payment.ALIPAY.INPUT_CHARSET);
				order.put("sign_type", Payment.ALIPAY.SIGN_TYPE);
				order.put("notify_url", Payment.ALIPAY.NOTIFY_URL);
				order.put("payment_type", Payment.ALIPAY.PAYMENT_TYPE);
				order.put("seller_id", Payment.ALIPAY.SELLER_EMAIL);
				order.put("out_trade_no", orderId);
				order.put("subject", orderDesc);
				order.put("total_fee", CommonUtils.formatMoney("0.00",price));
				order.put("body", orderDesc);
				order.put("it_b_pay", Payment.TIMEOUT_MINUTE+"m");

			}
			else {
				order.put("service", Payment.ALIPAY.SERVICE_H5);
				order.put("partner", Payment.ALIPAY.PARTNER);
				order.put("_input_charset", Payment.ALIPAY.INPUT_CHARSET);
				order.put("sign_type", Payment.ALIPAY.SIGN_TYPE);
				order.put("notify_url", Payment.ALIPAY.NOTIFY_URL);
				order.put("payment_type", Payment.ALIPAY.PAYMENT_TYPE);
				order.put("seller_id", Payment.ALIPAY.PARTNER);
				order.put("out_trade_no", orderId);
				order.put("subject", orderDesc);
				order.put("total_fee", CommonUtils.formatMoney("0.00",price));
				order.put("body", orderDesc);
				order.put("show_url", "http://www.renmaitong.com/");
				order.put("app_pay","Y");
				order.put("return_url",Payment.ALIPAY.RETURN_URL);

			}


			Map<String,String> temp = AlipayCore.paraFilter(order);

			result = AlipayCore.createLinkString(temp);
			String sign = RSA.sign(result, Payment.ALIPAY.PRIVATE_KEY, Payment.ALIPAY.INPUT_CHARSET);
			order.put("sign", java.net.URLEncoder.encode(sign,Payment.ALIPAY.INPUT_CHARSET));
//			order.put("sign", sign);

			result = AlipayCore.createLinkString(order);
			if(base64) result = StringUtils.base64Encode(result.getBytes("UTF-8"));

		} catch (Exception e) {
			throw new BusinessException(OrderPayResultCode.ALI_BUILD_PARAM_ERROR);
		}
		return result;
	}
	
	@Override
	public String buildUnionPayParams(String orderDesc,double price,String orderId){
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		String time_start = f.format(new Date(System.currentTimeMillis()));
		String time_expire = f.format(new Date(System.currentTimeMillis()+ Payment.TIMEOUT_MILLISECOND));
		String result = null;
		try {
			Map<String, String> contentData = new HashMap<String, String>();
			
			/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
			contentData.put("version", Payment.UNIONPAY.VERSION);            //版本号 全渠道默认值
			contentData.put("encoding", Payment.UNIONPAY.INPUT_CHARSET);     //字符集编码 可以使用UTF-8,GBK两种方式
			contentData.put("signMethod", "01");           		 	//签名方法 目前只支持01：RSA方式证书加密
			contentData.put("txnType", "01");              		 	//交易类型 01:消费
			contentData.put("txnSubType", "01");           		 	//交易子类 01：消费
			contentData.put("bizType", "000201");          		 	//填写000201
			contentData.put("channelType", "08");          		 	//渠道类型 08手机

			/***商户接入参数***/
			contentData.put("merId", Payment.UNIONPAY.MER_ID);   		 				//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
			contentData.put("accessType", "0");            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
			contentData.put("orderId", orderId);        	 	    //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
			contentData.put("txnTime", CommonUtils.getDateString("yyyyMMddHHmmss", System.currentTimeMillis()));		 		    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
			contentData.put("accType", "01");					 	//账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
			contentData.put("txnAmt", CommonUtils.formatMoney("0",price * 100));						//交易金额 单位为分，不能带小数点
			contentData.put("currencyCode", "156");                 //境内商户固定 156 人民币
			contentData.put("payTimeout", time_expire);//支付超时时间

			//contentData.put("reqReserved", "透传字段");              //商户自定义保留域，交易应答时会原样返回
			
			//后台通知地址（需设置为外网能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，【支付失败的交易银联不会发送后台通知】
			//后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
			//注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
			//    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200或302，那么银联会间隔一段时间再次发送。总共发送5次，银联后续间隔1、2、4、5 分钟后会再次通知。
			//    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
			contentData.put("backUrl", Payment.UNIONPAY.NOTIFY_URL);
			
			String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
			/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
			Map<String, String> reqData = AcpService.sign(contentData,Payment.UNIONPAY.INPUT_CHARSET);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
			Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,Payment.UNIONPAY.INPUT_CHARSET);  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
			
			/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
			//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》https://open.unionpay.com/upload/download/平台接入接口规范-第5部分-附录V1.8.pdf
			if(!rspData.isEmpty()){
				if(AcpService.validate(rspData, Payment.UNIONPAY.INPUT_CHARSET)){
					System.out.println("验证签名成功");
					String respCode = rspData.get("respCode") ;//一直返回10
					if(("00").equals(respCode)){
						//成功,获取tn号
						String tn = rspData.get("tn");
						System.out.println(tn);
						result = StringUtils.base64Encode(tn.getBytes("UTF-8")); 
					}else{
						//其他应答码为失败请排查原因或做失败处理
						throw new BusinessException(OrderPayResultCode.UNION_BUILD_PARAM_ERROR);
					}
				}else{
					System.out.println("验证签名失败");
					throw new BusinessException(OrderPayResultCode.UNION_BUILD_PARAM_ERROR);
				}
			}else{
				//未返回正确的http状态
				System.out.println("未获取到返回报文或返回http状态码非200");
			}
			
		} catch (Exception e) {
			throw new BusinessException(OrderPayResultCode.UNION_BUILD_PARAM_ERROR);
		}
		return result;
	}
	
	@Override
	public String doWxPayCall(String xml, APP app) {
		try {
			Map<String, String> map = XMLUtil.doXMLParse(xml);
			if(!WeixinUtils.isTenpaySign(map) && !WeixinUtils.isTenpaySignH5(map)){
				//如果签名不正确 
				return null;
			}
			if ("SUCCESS".equals(map.get("return_code"))) {
				
				String out_trade_no = map.get("out_trade_no");
				String tradeNo = map.get("transaction_id");
				String totalFee = map.get("total_fee");
				String attach = map.get("attach");
				String payDT = map.get("time_end");

				/**
				 * 看是否处理过订单
				 * song.ty 2016.9.23 fix
				 */
				List<OrdersBean> orderByOrderPayCode = orderCommonDao.getOrderByOrderPayCode(out_trade_no, app.ordinal());
				for(OrdersBean order:orderByOrderPayCode){
					if(order.getStatus() > OrderStatus.noPay.ordinal()){
						return out_trade_no;
					}
				}


				System.out.println("doWxPayCall out_trade_no:"+ out_trade_no + " transaction_id:"+ tradeNo+" total_fee:"+ totalFee+ " attach:"+ attach);

				/**
				 *  处理数据库逻辑
				 *  注意交易单不要重复处理
				 *  注意判断返回金额
				 */
				int source = app.ordinal();
				payDT = CommonUtils.getDateString("yyyy-MM-dd HH:mm:ss", CommonUtils.parseDateString("yyyyMMddHHmmss", payDT));//支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
				boolean result = saveDB(tradeNo, Double.valueOf(totalFee) / 100, payDT, source, out_trade_no, PayType.WEIXINPAY.ordinal());
				if(result){
					return out_trade_no;
				}
			} else {
				System.out.println("doWxPayCall 通知失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean doAliPayCall(Map<?, ?> parameterMap, String trade_status,
			String out_trade_no, String total_fee, String subject,String trade_no,
			String gmt_payment,APP app) {
		try {
			Map<String, String> params = new HashMap<String, String>();
	
			// 获取支付宝POST过来反馈信息
			for (Iterator iter = parameterMap.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) parameterMap.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			
			System.out.println("doAliPayCall：" + params);
			
	//		Map<String,String> temp = AlipayCore.paraFilter(params);
	//		
	//		String s = AlipayCore.createLinkString(temp);
	//		
	//		String sign = java.net.URLEncoder.encode(RSA.sign(s, Payment.ALIPAY.PRIVATE_KEY, Payment.ALIPAY.INPUT_CHARSET),"UTF-8");
	//		params.put("sign", sign);

		
			
			if (AlipayNotify.verify(params)) {// 验证成功
				System.out.println("AlipayNotifyAction verify true...");
				/**
				 * 交易状态 ：trade_status
				 * 
				 *  判断该笔订单是否在商户网站中已经做过处理
				 *	如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				 *	如果有做过处理，不执行商户的业务程序
				 * 
				 * 
				 * 注意：TRADE_FINISHED
				 *	该种交易状态只在两种情况下出现
				 *	1、开通了普通即时到账，买家付款成功后。
				 *	2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				 *
				 * 注意：TRADE_SUCCESS
				 *	该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				 */
				String tradeStatus = new String(trade_status.getBytes("ISO-8859-1"), "UTF-8");
				
				// 自己订单号
				out_trade_no = new String(out_trade_no
						.getBytes("ISO-8859-1"), "UTF-8");
				/**
				 * 看是否处理过订单
				 * song.ty 2016.9.23 fix
				 */
				List<OrdersBean> orderByOrderPayCode = orderCommonDao.getOrderByOrderPayCode(out_trade_no, app.ordinal());
				for(OrdersBean order:orderByOrderPayCode){
					if(order.getStatus() > OrderStatus.noPay.ordinal()){
						return true;
					}
				}

				if(tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")){
					int source = app.ordinal();
					trade_no = new String(trade_no.getBytes("ISO-8859-1"),"UTF-8");
				//		tradeNo = PatternUtils.parseStr(trade_no, "(\\d+)_\\d+", 1);
					return saveDB(trade_no, Double.parseDouble(total_fee), gmt_payment, source, out_trade_no, PayType.ALIPAY.ordinal());
				}
			} else {//验证失败
				System.out.println("AlipayNotifyAction verify error.");
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("AlipayNotifyAction error."+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean doUnionPayCall(Map<String, String> reqParam,int type, APP app) {
		try {
			Map<String, String> valideData = null;
			if (null != reqParam && !reqParam.isEmpty()) {
				Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
				valideData = new HashMap<String, String>(reqParam.size());
				while (it.hasNext()) {
					Entry<String, String> e = it.next();
					String key = (String) e.getKey();
					String value = (String) e.getValue();
					value = new String(value.getBytes("ISO-8859-1"), Payment.UNIONPAY.INPUT_CHARSET);
					valideData.put(key, value);
				}
			}

			//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
			if (!AcpService.validate(valideData, Payment.UNIONPAY.INPUT_CHARSET)) {
				System.out.println("doUnionPayCall验证签名结果[失败].");
				//验签失败，需解决验签问题
				return false;
			} else {
				System.out.println("doUnionPayCall验证签名结果[成功].");
				//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
				
				String respCode = valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
				if("00".equals(respCode)){
					String orderId = valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
					int source = app.ordinal();
					String queryId = valideData.get("queryId"); //交易的流水号，供后续查
					if(type == 1){ //交易(消费)
						String txnAmt = valideData.get("txnAmt"); //返回的金额
						String respTime = valideData.get("txnTime"); //YYYYMMDDhhmmss
						respTime = CommonUtils.getDateString("yyyy-MM-dd HH:mm:ss", CommonUtils.parseDateString("yyyyMMddHHmmss", respTime));
						return saveDB(queryId, Double.parseDouble(txnAmt) / 100, respTime, source, orderId, PayType.UNIONPAY.ordinal());
					}else if(type == 2){//退款(退货)
						orderId = orderId.substring(8);// 订单规则为 torefund+订单id
						return refundService.doRefund(Integer.valueOf(orderId), app);
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("doUnionPayCall error."+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	private boolean saveDB(String tradeNo, double totalFee, String payDT,int source, String orderPayCode,int payType) {
		if(orderPayLogCommonDao.getTradeNo(orderPayCode, source) != null){//已保存
			return true;
		}
		
		orderPayLogCommonDao.save(orderPayCode,payType, totalFee, tradeNo, payDT, source);

		List<OrdersBean> orders = orderCommonDao.getOrderByOrderPayCode(orderPayCode, source);
		if(orders.isEmpty()){
			System.out.println(payType+"支付回调接口，找不到对应的支付订单号："+orderPayCode);
			return false;
		}
		for(OrdersBean order : orders){
			int orderID = order.getOrderID();

		 // 修改价格功能
		  if(order.getActualPayment() != totalFee){
				orderCommonDao.updateOrderPrice(orderID, source, OrderStatus.noPay.ordinal(), totalFee);//金额不一致，已第三方回调值为准
				System.out.println(payType+",orderID="+orderID+"支付回调接口，金额不正确：totalFee="+totalFee+",actualPayment="+order.getActualPayment());
			}

			//更新订单状态
			int resCode = orderCommonDao.updateOrderStatus(orderID, source,
					order.getGuid(), OrderStatus.payWaitDelivery.ordinal(), OrderStatus.noPay.ordinal());
			if(resCode == 0){
				System.out.println(payType+"支付回调接口，订单更新失败：orderID="+orderID+",status="+order.getStatus());
			}else{
				//记录日志
				orderCommonService.saveOrderLog(orderID, order.getGuid(), OpType.pay,
						OrderStatus.payWaitDelivery, source);
			}
			
		}
		
		//保存交易的资金流水记录
		moneyIOLogDao.saveMoneyIOLog(orderPayCode, totalFee, orders.get(0).getGuid(), 0, MoneyIOType.deal.ordinal(), payType, source);
		return true;
	}

	@Override
	public boolean doAliRefundCall(Map<?, ?> parameterMap, APP app) {
		try {
			Map<String, String> params = new HashMap<String, String>();
	
			// 获取支付宝POST过来反馈信息
			for (Iterator<?> iter = parameterMap.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) parameterMap.get(name);
				
				String valueStr = "";
				
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "GBK");
				params.put(name, valueStr);
			}
			
			
			System.out.println("doAliRefundCall：" + params);
			
	//		Map<String,String> temp = AlipayCore.paraFilter(params);
	//		
	//		String s = AlipayCore.createLinkString(temp);
	//		
	//		String sign = java.net.URLEncoder.encode(RSA.sign(s, Payment.ALIPAY.PRIVATE_KEY, Payment.ALIPAY.INPUT_CHARSET),"UTF-8");
	//		params.put("sign", sign);

		
			
			if (AlipayNotify.verify(params)) {// 验证成功
				int source = app.ordinal();
				System.out.println("AlipayNotifyAction verify true...");
				
				/**
				 * 退款结果明细：
					退手续费结果返回格式：交易号^退款金额^处理结果$退费账号^退费账户ID^退费金额^处理结果；
					2016060721001004160212361592^0.01^SUCCESS#2016060721001004160215684597^0.01^SUCCESS
					不退手续费结果返回格式：交易号^退款金额^处理结果。
					若退款申请提交成功，处理结果会返回“SUCCESS”。若提交失败，退款的处理结果中会有报错码
				 */
				String result_details = params.get("result_details");
				System.out.println("result_detailsstart="+result_details);
				String[] split = result_details.split("#");
				for(String arr : split){
					String[] arrs = arr.split("\\^");
					if("SUCCESS".equals(arrs[2])){
						String tradeNO = arrs[0];
						int orderID = orderPayLogCommonDao.getOrderIDByTradeNo(tradeNO, source);
						this.refundService.doRefund(orderID, app);
					}
				}
				System.out.println("result_detailsend="+result_details);
				
				return true;
			} else {//验证失败
				System.out.println("doAliRefundCall verify error.");
				return false;
			}
			
		} catch (Exception e) {
			System.out.println("doAliRefundCall error."+e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void doConfirmExtractAmount(int id, APP app) {
		int source = app.ordinal();
		
		LogDataBean logdata = new LogDataBean();
		logdata.setSource(source);
		logdata.setLogType(LogType.EXTRACTION_REQ.ordinal());
		logdata.setResourceID(id);
		logDataCommonDao.saveLogData(logdata);//日志记录
		
		ExtractAmountLogBean exBean= new ExtractAmountLogBean();
		exBean.setId(id);
		exBean.setSource(source);
		exBean = extractAmountLogCommonDao.getExtractAmountLogDetail(exBean);
		
		int resCode = extractAmountLogCommonDao.extractamountSuccess(id,  source);
		if(resCode == 0){//重复提现
			throw new BusinessException(OrderPayResultCode.WITHDRAWCASH_ERROR);
		}
		String tradeNo = exBean.getTradeNo();
		int guid = exBean.getGuid();
		int bankCardID = exBean.getBankCardID();
		double amount = exBean.getExtractAmount();
		
		BankcardBean bankcardDetail = bankcardCommonDao.getBankcardDetail(guid, bankCardID, source);
			//银行名称
		String openBank = BankType.getBankType(bankcardDetail.getBankType()).title;
			
		boolean isSuccess = ChinaPaySimpleDFUtils.doDF(tradeNo, amount, bankcardDetail.getCardNo(), bankcardDetail.getName(), openBank, bankcardDetail.getProvinceName(), bankcardDetail.getCityName());
		if(!isSuccess){//提现失败
			extractAmountLogCommonDao.extractamountFail(id, source);

			UserAccountBean userAccount = userAccountCommonDao.getUserAccountForUpdate(guid, app.ordinal());
			double balance = userAccount == null ? 0:Double.parseDouble(CommonUtils.formatMoney(userAccount.getBalance()));// 获取账户余额
			balance = Double.valueOf(CommonUtils.formatMoney(balance+amount));
			//提现失败需要把金额恢复
			resCode = userAccountCommonDao.updateBalance(balance, guid, source);
			if(resCode == 0){
				System.out.println("余额更新失败：guid="+guid+"balance="+balance);
				throw new BusinessException(OrderPayResultCode.WITHDRAWCASH_ERROR);
			}
			
			logdata.setLogType(LogType.EXTRACTION_FAILED.ordinal());
			logDataCommonDao.saveLogData(logdata);//日志记录
		}else{
			logdata.setLogType(LogType.EXTRACTION_SUCCESS.ordinal());
			logDataCommonDao.saveLogData(logdata);//日志记录
			
			//保存提现资金流水记录
			moneyIOLogDao.saveMoneyIOLog(tradeNo, amount, 0, guid, MoneyIOType.extractMoney.ordinal(), PayType.UNIONPAY.ordinal(), source);
		}
	}
	
		
	
	
}
