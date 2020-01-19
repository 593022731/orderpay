package com.wei.orderpay.pay.wx;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.pay.Payment;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.SignatureException;
import java.util.*;

/**
 * 微信退款
 * @author : weihui
 * @createTime : 2016年5月3日 上午11:02:37
 * @version : 1.0
 * @description :
 *
 */
@SuppressWarnings("all")
public class WxRefundUtils {
	
	final static String KEYSTORE_FILE = Payment.WX.KEYSTORE_FILE;//证书路径
	final static String KEYSTORE_PASSWORD = Payment.WX.PARTNER_ID;//证书密码默认为商户ID
	final static String URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	private static String partnerkey = Payment.WX.PAY_SIGN_KEY;//微信支付的API密钥
	private static String charset = "UTF-8";
	
	 /**
     * 请求退款
     * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_4&index=6
     * @param out_trade_no 商户侧传给微信的订单号
     * @param out_refund_no 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔(目前使用orderID)
     * @param total_fee 订单总金额
     * @param refund_fee  退款总金额
     * @return
     *
     * @author : weihui
     * @createTime : 2016年5月3日 下午12:13:51
     */
	public static boolean doRefund(String out_trade_no,String out_refund_no,double total_fee,double refund_fee) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid",  Payment.WX.APP_ID);
		map.put("mch_id",  Payment.WX.PARTNER_ID);
		String nonceStr = RandomStringGenerator
				.getRandomStringByLength(32);
		map.put("nonce_str",  nonceStr);
		map.put("out_trade_no",  out_trade_no);
		map.put("out_refund_no",  out_refund_no);
		map.put("total_fee",  CommonUtils.formatMoney("0",total_fee * 100));//单位为分，只能为整数
		map.put("refund_fee", CommonUtils.formatMoney("0",refund_fee * 100));//单位为分，只能为整数
		map.put("op_user_id",  Payment.WX.PARTNER_ID);
		map.put("sign", createSign(map));//签名
		
		String data = createXML(map);
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(KEYSTORE_FILE));//P12文件目录
        try {
            keyStore.load(instream, KEYSTORE_PASSWORD.toCharArray());//这里写密码..默认是你的MCHID
        } finally {
            instream.close();
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, KEYSTORE_PASSWORD.toCharArray())//这里也是写密码的
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
        		SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
        	HttpPost httpost = new HttpPost(URL); // 设置响应头信息
        	httpost.addHeader("Connection", "keep-alive");
        	httpost.addHeader("Accept", "*/*");
        	httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        	httpost.addHeader("Host", "api.mch.weixin.qq.com");
        	httpost.addHeader("X-Requested-With", "XMLHttpRequest");
        	httpost.addHeader("Cache-Control", "max-age=0");
        	httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
    		httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                String xml = toStringInfo(response.getEntity(),charset);
            	Map<String, String> result = XMLUtil.doXMLParse(xml);
               return "SUCCESS".equals(result.get("return_code"));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
	}
	

	 /**
    * 退款查询
    * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_5&index=7
    * @param out_trade_no 商户侧传给微信的订单号
    * @return
    *
    * @author : weihui
    * @createTime : 2016年5月9日 下午15:50:51
    */
	public static boolean doRefundQuery(String out_trade_no) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid",  Payment.WX.APP_ID);
		map.put("mch_id",  Payment.WX.PARTNER_ID);
		String nonceStr = RandomStringGenerator
				.getRandomStringByLength(32);
		map.put("nonce_str",  nonceStr);
		map.put("out_trade_no",  out_trade_no);
		map.put("sign", createSign(map));//签名
		
		String data = createXML(map);
		DefaultHttpClient httpclient = new DefaultHttpClient(); 
        try {
        	HttpPost httpost = new HttpPost(URL); // 设置响应头信息
        	httpost.addHeader("Connection", "keep-alive");
        	httpost.addHeader("Accept", "*/*");
        	httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        	httpost.addHeader("Host", "api.mch.weixin.qq.com");
        	httpost.addHeader("X-Requested-With", "XMLHttpRequest");
        	httpost.addHeader("Cache-Control", "max-age=0");
        	httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
    		httpost.setEntity(new StringEntity(data, "UTF-8"));

            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                String jsonStr = toStringInfo(response.getEntity(),charset);
                
                //微信返回的报文时GBK，直接使用httpcore解析乱码
              //  String jsonStr = EntityUtils.toString(response.getEntity(),"UTF-8");
               EntityUtils.consume(entity);
               JSONObject json = JSONObject.fromObject(jsonStr);
               return "SUCCESS".equals(json.getString("return_code"));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
	}
	
	private static String toStringInfo(HttpEntity entity, String defaultCharset) throws Exception, IOException{
		final InputStream instream = entity.getContent();
	    if (instream == null) {
	        return null;
	    }
	    try {
	        Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
	                "HTTP entity too large to be buffered in memory");
	        int i = (int)entity.getContentLength();
	        if (i < 0) {
	            i = 4096;
	        }
	        Charset charset = null;
	        
	        if (charset == null) {
	            charset = Charset.forName(defaultCharset);
	        }
	        if (charset == null) {
	            charset = HTTP.DEF_CONTENT_CHARSET;
	        }
	        final Reader reader = new InputStreamReader(instream, charset);
	        final CharArrayBuffer buffer = new CharArrayBuffer(i);
	        final char[] tmp = new char[1024];
	        int l;
	        while((l = reader.read(tmp)) != -1) {
	            buffer.append(tmp, 0, l);
	        }
	        return buffer.toString();
	    } finally {
	        instream.close();
	    }
	}
	
	private static String createXML(Map<String, Object> map){
		String xml = "<xml>";
		Set<String> set = map.keySet();
		Iterator<String> i = set.iterator();
		while(i.hasNext()){
			String str = i.next();
			xml+="<"+str+">"+"<![CDATA["+map.get(str)+"]]>"+"</"+str+">";
		}
		xml+="</xml>";
		return xml;
	}
	
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。 sign
	 */
	@SuppressWarnings("all")
	private static String createSign(Map<String, Object> map) {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		for (Map.Entry<String, Object> m : map.entrySet()) {
			packageParams.put(m.getKey(), m.getValue().toString());
		}

		StringBuffer sb = new StringBuffer();
		Set<?> es = packageParams.entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (CommonUtils.isNotEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + partnerkey);
		 
		
		String sign = sign(sb.toString(), "", charset).toUpperCase();
		
		return sign;
	}
	
	  /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
	private static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}
