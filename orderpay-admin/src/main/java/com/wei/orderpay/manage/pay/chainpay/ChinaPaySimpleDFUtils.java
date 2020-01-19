package com.wei.orderpay.manage.pay.chainpay;


import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.manage.pay.Payment.CHINAPAY;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 单笔代付
 * 注意：此类必须GBK编译
 * @author : weih
 * @createTime : 2016年5月26日 下午5:26:01
 */
public class ChinaPaySimpleDFUtils {

	/**
	 * 正式
	 */
//	public static final String merId = "808080211303513";
//	public static final String MerKeyPath = "D:/certs/MerPrK_808080211303513_20160506164745.key";
//	private static final String PubKeyPath = "D:/certs/PgPubk.key";
//	private static final String url = "http://sfj.chinapay.com/dac/SinPayServletGBK";

	public static final String merId = CHINAPAY.MER_ID;
	public static final String MerKeyPath = CHINAPAY.MERKEYPATH;
	private static final String PubKeyPath = CHINAPAY.PUBKEYPATH;
	private static final String url = CHINAPAY.URL;;

	/**
	 * 代付操作
	 * @param merSeqId 流水号，唯一标示
	 * @param price
	 * @param cardNo 收款方银行卡号
	 * @param usrName 姓名
	 * @param openBank 银行名称
	 * @param prov 开户行省
	 * @param city 开户行市
	 * @return
	 *
	 * @author : weih
	 * @createTime : 2016年5月31日 下午2:36:39
	 */
	public static boolean doDF(String merSeqId,double price,String cardNo,String usrName,String openBank,String prov,String city){
		try {
			//商户日期
			String merDate = CommonUtils.getDateString("yyyyMMdd", System.currentTimeMillis());

			//	String merSeqId = "00"+ CommonUtils.getDateString("yyyyMMddHHmmss", System.currentTimeMillis());

			//	String cardNo = "622909211421292711";
			//
			//String usrName = "魏慧";
			//String openBank = "兴业银行";

			//	String prov = "南京";
			//	String city = "南京";

			String transAmt = CommonUtils.formatMoney("0",price * 100);//分为单位
			String purpose = "提现";  //用途

			String subBank = openBank;  //支行，默认可以写总行

			String flag = "00";
			String version = "20151207";
			String termType = "07";

			String signstr = new StringBuffer(merId).append(merDate)
					.append(merSeqId).append(cardNo)
					.append(usrName).append(openBank)
					.append(prov).append(city)
					.append(transAmt).append(purpose)
					.append(subBank)
					.append(flag)
					.append(version).append(termType).toString();
			System.out.println("字符串数据拼装结果：" + signstr);

//			String plainData = new String(Base64.encode(signstr.getBytes())); windows 环境
			String plainData = new String(Base64.encode(signstr.getBytes("GBK")));//linux 环境

			System.out.println("转换成Base64后数据：" + plainData);

			//签名
			String chkValue = null;
			int KeyUsage = 0;
			PrivateKey key = new PrivateKey();
			boolean flage = key.buildKey(merId, KeyUsage, MerKeyPath);
			if(flage == false){
				System.out.println("buildkey error!");
				return false;
			}else{
				System.out.println("============flage "+flage );
				SecureLink sl = new SecureLink(key);
				System.out.println("====date "+ plainData);
				chkValue = sl.Sign(plainData);
				System.out.println("签名内容:"+ chkValue);
			};

			//连接Chinapay控台
			HttpClient httpClient = new HttpClient();
			System.out.println("HttpClient方法创建！");
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
			PostMethod postMethod = new PostMethod(url);
			System.out.println("Post方法创建！");

			String signFlag = "1";

			//填入各个表单域的值
			NameValuePair[] data = {
					new NameValuePair("merId", merId),
					new NameValuePair("merDate", merDate),
					new NameValuePair("merSeqId", merSeqId),
					new NameValuePair("cardNo", cardNo),
					new NameValuePair("usrName", usrName),
					new NameValuePair("openBank", openBank),
					new NameValuePair("prov", prov),
					new NameValuePair("city", city),
					new NameValuePair("transAmt", transAmt),
					new NameValuePair("purpose", purpose),
					new NameValuePair("subBank",subBank),
					new NameValuePair("flag", flag),
					new NameValuePair("version", version),
					new NameValuePair("chkValue",chkValue),
					new NameValuePair("termType",termType),
					new NameValuePair("signFlag", signFlag)
			};

			System.out.println(data);
			System.out.println("Ora交易提交报文：merId=" + merId + "&merDate=" + merDate + "&merSeqId=" + merSeqId + "&cardNo=" + cardNo + "&usrName=" + usrName + "&openBank=" + openBank + "&prov=" + prov + "&city=" + city + "&transAmt=" + transAmt + "&purpose=" + purpose + "&subBank=" + subBank + "&flag=" + flag + "&version=" + version + "&chkValue=" + chkValue + "&signFlag=" + signFlag + "&termType=" + termType);

			// 将表单的值放入postMethod中
			postMethod.setRequestBody(data);
			// 执行postMethod
			try {
				httpClient.executeMethod(postMethod);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 读取内容
			InputStream resInputStream = null;
			try {
				resInputStream = postMethod.getResponseBodyAsStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 处理内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
			String tempBf = null;
			StringBuffer html=new StringBuffer();
			while((tempBf = reader.readLine()) != null){

				html.append(tempBf);
			}

			/**
			 * 关于单笔代付响应码的说明如下：
			 1).当responseCode=0000&stat为空时，表示由于贵司的请求报文存在问题，造成单笔代付请求未成功提交到我司。
			 当responseCode=0000&stat有值时，表示我司成功接收到贵代付请求，stat=s表示交易成功；stat=6、9表示交易失败(几乎不出现）；stat=2、3、4、5、7、8表示交易处理中。
			 2).当responseCode=0100、0101、0102时，stat均无值，均表示由于各种原因，贵司的请求未成功提交到我司。
			 3).当responseCode=0103&stat=6时，表示贵司请求成功提交到我司，但由于备付金余额不足，故造成交易失败。
			 4).若出现responseCode=0104而stat为空，则是请求信息未提交成功。
			 若出现responseCode=0104而stat不为空，一般对应的是stat=6(银行已退单）。
			 5).当responseCode=0105时，stat无值，此笔交易未成功提交，待查询的交易是先前提交成功的同商户号同流水号同交易日期的代付交易。
			 */
			String resMes = html.toString();
			System.out.println("交易返回报文：" + resMes);
			int dex = resMes.lastIndexOf("&");

			//拆分页面应答数据
			String str[] = resMes.split("&");
			System.out.println(str.length);

			//提取返回数据
			if(str.length == 10){
				int Res_Code = str[0].indexOf("=");
				int Res_merId = str[1].indexOf("=");
				int Res_merDate = str[2].indexOf("=");
				int Res_merSeqId = str[3].indexOf("=");
				int Res_cpDate = str[4].indexOf("=");
				int Res_cpSeqId = str[5].indexOf("=");
				int Res_transAmt = str[6].indexOf("=");
				int Res_stat = str[7].indexOf("=");
				int Res_cardNo = str[8].indexOf("=");
				int Res_chkValue = str[9].indexOf("=");

				String responseCode = str[0].substring(Res_Code+1);
				String MerId = str[1].substring(Res_merId+1);
				String MerDate = str[2].substring(Res_merDate+1);
				String MerSeqId = str[3].substring(Res_merSeqId+1);
				String CpDate = str[4].substring(Res_cpDate+1);
				String CpSeqId = str[5].substring(Res_cpSeqId+1);
				String TransAmt = str[6].substring(Res_transAmt+1);
				String Stat = str[7].substring(Res_stat+1);
				String CardNo = str[8].substring(Res_cardNo+1);
				String ChkValue = str[9].substring(Res_chkValue+1);


				System.out.println("responseCode=" + responseCode);
				System.out.println("merId=" + MerId);
				System.out.println("merDate=" + MerDate);
				System.out.println("merSeqId=" + MerSeqId);
				System.out.println("transAmt=" + TransAmt);
				System.out.println("cpDate=" + CpDate);
				System.out.println("cpSeqId=" + CpSeqId);
				System.out.println("Stat=" + Stat);
				System.out.println("cardNo=" + CardNo);
				System.out.println("chkValue=" + ChkValue);


				String msg = resMes.substring(0,dex);
				plainData = new String(Base64.encode(msg.getBytes()));
				System.out.println("需要验签的字段：" + msg);

				//对收到的ChinaPay应答传回的域段进行验签
				boolean buildOK = false;
				boolean res = false;
				key = new PrivateKey();
				try {
					buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!buildOK) {
					System.out.println("build error!");
					return false;
				}

				SecureLink sl = new SecureLink(key);
				res=sl.verifyAuthToken(plainData,ChkValue);
				System.out.println(res);
				if (res && "0000".equals(responseCode)){//0000	接收成功	提交成功
					System.out.println("验签数据正确!");
					return true;
				}
				else {
					System.out.println("签名数据不匹配！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
