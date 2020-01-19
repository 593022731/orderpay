//package com.jiutong.orderpay.pay.chinapay;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.zip.Deflater;
//
//import chinapay.PrivateKey;
//import chinapay.SecureLink;
//import chinapay.util.SecureUtil;
//
//import com.jiutong.common.tools.CommonUtils;
//
//import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.SimpleHttpConnectionManager;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
//import org.apache.commons.httpclient.params.HttpMethodParams;
//
///**
// * 代付
// * @author : weih
// * @createTime : 2016年5月26日 下午5:26:01
// */
//public class ChinaPayDFUtils {
//	
//	public static final String merId = "808080211303513";
//	public static final String MerKeyPath = "D:/certs/MerPrK_808080211303513_20160506164745.key";
//	private static String filepath = "D:/dftempfile/";
//	private static final String PubKeyPath = "D:/certs/PgPubk.key";
//	private static final String url = "http://sfj.chinapay.com/dac/BatchPayGBK";
//	public static int tmpResultLength = -1;
//	
//	public static void doDF(){
//		String today = CommonUtils.getDateString("yyyyMMdd", System.currentTimeMillis());
//		String pici = "000001";
//		
//		//文件名: MERID_YYYYMMDD_XXXXXX.txt，         MERID:15位的商户号；         YYYYMMDD:商户生成文件的日期，共8位；     XXXXXX:6位的批次号
//		String fileName = merId +"_"+today + "_" + pici + ".txt";
//		int amount = 1000;
//		/**
//		 * 文件内容格式规范：文件内容包括文件头和文件体。文件头与文件体由回车换行符分割
//		 * 文件头:  文件的第一行，包含：商户号，批次号，总笔数，总金额，各项用“|”分割。MERID_YYYYMMDD_XXXXXX.txt
//		 * 文件体: 商户日期|流水号|收款账号|收款人姓名|开户银行|省份|城市|开户支行名|金额|用途|对公对私标志(选填项)	
//		 * 
//		 * 事例值：
//		 *  606060061499197|112901|3|1500
//			20111129|11|622202123123123123|测试|工商银行|上海|上海|工商银行|500|付款1|
//			20111129|12|622202123123123123|测试|工商银行|上海|上海|工商银行|500|付款2|
//		 */
//		String fileContent = merId+pici+1+amount;
//		
//		//流水号
//		String recordNO = System.currentTimeMillis() + "" + CommonUtils.getRandomNumber(1, 999) ;
//		String cardNO = "622909211421292711";
//		String name = "魏慧";
//		String cardType = "兴业银行";
//		fileContent += "\n" + today+"|"+recordNO+"|"+cardNO+"|"+name+"|"+cardType+"|上海|上海|"+cardType+"|"+amount+"|提现|";
//		
//		// 对存款批量交易信息进行签名
//		String chkValue1 = SecureUtil.digitalSign(merId, fileContent, MerKeyPath);
//		
//		fileContent += "\n" + chkValue1;
//	
//		String plain = fileContent + chkValue1;
//
//
//		// 文件上传准备
//		HttpClient httpClient = null;
//		PostMethod postMethod = null;
//		BufferedReader reader = null;
//		InputStream resInputStream = null;
//		try {
//			filepath += fileName;
//			File file = new File(filepath);
//			
//			// 将ORA批量信息写入临时文件
//			FileOutputStream fos = new FileOutputStream(filepath);
//			fos.write(plain.getBytes("GBK"));
//			fos.flush();
//			fos.close();
//			
//			httpClient = new HttpClient();
//			httpClient.getParams().setParameter(
//					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
//			postMethod = new PostMethod(url);
//
//			byte[] temSen = null;
//
//			try {
//				temSen = getBytes(file);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			int temSenLength = temSen.length;
//			System.out.println("temSen=[" + temSenLength + "]");
//			String tian = new String(temSen, "GBK");
//			System.out.println("tian=[" + tian + "]");
//
//			// 对需要上传的字段签名
//			String chkValue = null;
//			chkValue = DigestMD5.MD5Sign(merId, fileName, plain.getBytes("GBK"), MerKeyPath);
//			System.out.println("文件上传签名内容:" + chkValue);
//
//			httpClient.getParams().setParameter(
//					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
//			// 获得管理参数
//			HttpConnectionManagerParams managerParams = httpClient
//					.getHttpConnectionManager().getParams();
//			// 设置连接超时时间(单位毫秒)
//			managerParams.setConnectionTimeout(40000);
//			// 设置读数据超时时间(单位毫秒)
//			managerParams.setSoTimeout(120000);
//			postMethod.setRequestHeader("Connection", "close");
//			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
//					new DefaultHttpMethodRetryHandler(1, false));
//			NameValuePair[] data = { new NameValuePair("merId", merId), new NameValuePair("fileName", fileName), new NameValuePair("fileContent", tian), new NameValuePair("chkValue", chkValue) };
//
//			postMethod.setRequestBody(data);
//
//			try {
//				resInputStream = postMethod.getResponseBodyAsStream();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			// 
//			reader = new BufferedReader(new InputStreamReader(resInputStream));
//			String tempBf = null;
//			StringBuffer html = new StringBuffer();
//			while ((tempBf = reader.readLine()) != null) {
//
//				html.append(tempBf);
//			}
//			String result = html.toString();
//			System.out.println("返回数据" + "[" + result + "]");
//			int dex = result.lastIndexOf("=");
//			String tiakong = result.substring(0, dex + 1);
//			System.out.println("验签明文：" + "[" + tiakong + "]");
//
//			// 拆分页面应答数据
//			String str[] = result.split("&");
//			System.out.println(str.length);
//			int Res_Code = str[0].indexOf("=");
//			int Res_message = str[1].indexOf("=");
//			int Res_chkValue = str[2].indexOf("=");
//
//			String responseCode = str[0].substring(Res_Code + 1);
//			String message = str[1].substring(Res_message + 1);
//			String ChkValue = str[2].substring(Res_chkValue + 1);
//			System.out.println("responseCode=" + responseCode);
//			System.out.println("message=" + message);
//			System.out.println("chkValue=" + ChkValue);
//
//			// 对收到的ChinaPay应答传回的域段进行验签
//			boolean res = DigestMD5.MD5Verify(tiakong, ChkValue,PubKeyPath); 
//			System.out.println(res);
//
//
//			if (responseCode.equals("20FM")) {
//				System.out.println("批量文件接口上传成功！");
//			}
//			if (res) {
//				System.out.println("验签数据正确!");
//			} else {
//				System.out.println("签名数据不匹配！");
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//				// 释放httpclient
//				if (postMethod != null) {
//					postMethod.releaseConnection();
//				}
//				if (reader != null) {
//					reader.close();
//				}
//				if (resInputStream != null) {
//					resInputStream.close();
//				}
//			} catch (Exception e) {
//			}
//		}
//
//	}
//	
//	/**
//	 * @author note by Shine
//	 * @20131127
//	 * The method of compress
//	 * */
//	public static byte[] getBytes(File f) throws Exception {
//		FileInputStream in = new FileInputStream(f);
//		byte[] b = new byte[4 * 1024];
//		int n;
//		byte[] tmpResult = null;
//		byte[] base64Result = null;
//		while ((n = in.read(b)) != -1) {
//			if (tmpResult == null) {
//				tmpResult = getSumByte(null, 0, b, n);
//			} else {
//				tmpResult = getSumByte(tmpResult, tmpResult.length, b, n);
//			}
//		}
//		tmpResultLength = tmpResult.length;
//		MsgUtil msgUtil = new MsgUtil();
//		base64Result = msgUtil.deflateEncode(tmpResult);
//		System.out.println("参数:==[" + base64Result + "]");
//		in.close();
//		return base64Result;
//	}
//
//	public static byte[] getSumByte(byte[] baseValue, int orLength, byte[] streamByte,
//			int length) {
//		byte[] result = new byte[orLength + length];
//		for (int i = 0; i < orLength; i++) {
//			result[i] = baseValue[i];
//		}
//		for (int i = 0; i < length; i++) {
//			result[orLength + i] = streamByte[i];
//		}
//		return result;
//	}
//	
//	public static void main(String[] args) {
//		doDF();
//	}
//}
