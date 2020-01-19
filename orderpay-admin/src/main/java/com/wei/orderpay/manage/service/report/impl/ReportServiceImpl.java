package com.wei.orderpay.manage.service.report.impl;

import com.wei.orderpay.manage.contacts.common.APP;
import com.wei.orderpay.manage.dao.report.ReportDao;
import com.wei.orderpay.manage.service.report.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Map.Entry;

@Service
public class ReportServiceImpl implements ReportService {

	public static int SUMKEY = Integer.MAX_VALUE;
	private static DecimalFormat  decimalFormat= new DecimalFormat("0.00");
	@Resource
	private ReportDao reportDao;
	@Override
	public List<Map<String,String>> getAllReports(
			String createDTString, String endDTString,APP app,int type) {
		Map<Integer, Map<String, String>>  resMap = new HashMap<Integer,Map<String,String>>();
		//下单总额
		Map<Integer,String> totalOrderAmount = reportDao.getTotalOrderAmount(createDTString,endDTString,app,type);
		//计算合计
		double sumDouble = 0l;
		for(Entry e:totalOrderAmount.entrySet()){
			sumDouble += Double.parseDouble((String) e.getValue());
		}
		totalOrderAmount.put(SUMKEY, decimalFormat.format(sumDouble));
		
		//销售额
		Map<Integer,String> totalPayOrderAmount = reportDao.getTotalPayOrderAmount(createDTString,endDTString,app,type);
		//计算合计
		sumDouble = 0l;
		for(Entry e:totalPayOrderAmount.entrySet()){
			sumDouble += Double.parseDouble((String) e.getValue());
		}
		totalPayOrderAmount.put(SUMKEY, decimalFormat.format(sumDouble));
		
		//下单数
		Map<Integer,String> totalOrderCount = reportDao.getTotalOrderCount(createDTString,endDTString,app,type);
		//计算合计
		int sumInt = 0;
		for(Entry e:totalOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalOrderCount.put(SUMKEY, sumInt+"");
		
		//付款订单数
		Map<Integer,String> totalPayOrderCount = reportDao.getTotalPayOrderCount(createDTString,endDTString,app,type);
		//计算合计
		sumInt = 0;
		for(Entry e:totalPayOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalPayOrderCount.put(SUMKEY, sumInt+"");
		
		//客单价
		Map<Integer,String> totalCustomerPrice = reportDao.getTotalCustomerPrice(createDTString,endDTString,app,type);
		//计算合计
		sumDouble = 0l;
		for(Entry e:totalCustomerPrice.entrySet()){
			sumDouble += Double.parseDouble((String) e.getValue());
		}
		totalCustomerPrice.put(SUMKEY, decimalFormat.format(sumDouble));
		
		//商品订单
		Map<Integer,String> totalProductOrderCount = reportDao.getTotalProductOrderCount(createDTString,endDTString,app,type);
		//计算合计
		sumInt = 0;
		for(Entry e:totalProductOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalProductOrderCount.put(SUMKEY, sumInt+"");
		
		//报价订单
		Map<Integer,String> totalBidOrderCount = reportDao.getTotalBidOrderCount(createDTString,endDTString,app,type);
		//计算合计
		sumInt = 0;
		for(Entry e:totalBidOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalBidOrderCount.put(SUMKEY, sumInt+"");
		
		//已取消订单
		Map<Integer,String> totalCancelOrderCount = reportDao.getTotalCancelOrderCount(createDTString,endDTString,app,type);
		//计算合计
		sumInt = 0;
		for(Entry e:totalCancelOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalCancelOrderCount.put(SUMKEY, sumInt+"");
		
		//已退款订单总额
		Map<Integer,String> totalRefundOrderAmount = reportDao.getTotalRefundOrderAmount(createDTString,endDTString,app,type);
		//计算合计
		sumDouble = 0l;
		for(Entry e:totalRefundOrderAmount.entrySet()){
			sumDouble += Double.parseDouble((String) e.getValue());
		}
		totalRefundOrderAmount.put(SUMKEY, decimalFormat.format(sumDouble));
		
		//已退款订单数
		Map<Integer,String> totalRefundOrderCount = reportDao.getTotalRefundOrderCount(createDTString,endDTString,app,type);
		//计算合计
		sumInt = 0;
		for(Entry e:totalRefundOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalRefundOrderCount.put(SUMKEY, sumInt+"");
		
		
		
		for(Entry<Integer, String> e :totalOrderAmount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalOrderAmount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalPayOrderAmount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalPayOrderAmount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalOrderCount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalPayOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalPayOrderCount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalProductOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalProductOrderCount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalBidOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalBidOrderCount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalCancelOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalCancelOrderCount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalCustomerPrice.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalCustomerPrice", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalRefundOrderAmount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalRefundOrderAmount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalRefundOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalRefundOrderCount", e.getValue());
		}
		
		return MapToList(resMap);
	}

	@Override
	public  List<Map<String,String>> getPayedProductCategoryReports(
			String createDTString, String endDTString,APP app,int type) {
		/**
		 *  面料	10
			汽车配件	11
			美妆日化	12
			服装/鞋靴/配件	13
			日用百货	14
			数码家电	15
			母婴玩具	16
			箱包/珠宝配饰	17
			运动户外	19
			美食茶酒	20
			农产品	29
			家纺/家饰/家装建材	21
			花鸟文娱	22
			IT通讯/互联网服务	30
			服务信息	18
			房地产	31
			物流运输	32
			医药卫生	33
			安全防护/电工电气	23
			仪器仪表/五金工具	24
			化工原料/精细化学	25
			橡塑/冶金矿产	26
			包装/钢材	27
			微商/直销	28

		 */
		Map<Integer, Map<String, String>>  resMap = new HashMap<Integer,Map<String,String>>();
		
		for(int i = 10;i<=33;i++){
			Map<Integer, String> payedProductCategoryReports = reportDao.getPayedProductCategoryReports(createDTString,endDTString ,app,i,type);
			//计算合计
			int sumInt = 0;
			for(Entry e:payedProductCategoryReports.entrySet()){
				sumInt += Integer.parseInt((String) e.getValue());
			}
			payedProductCategoryReports.put(SUMKEY, sumInt+"");
			
			for(Entry<Integer, String> e :payedProductCategoryReports.entrySet()){
				Map<String, String> map = resMap.get(e.getKey());
				if(map == null) {
					resMap.put(e.getKey(), new HashMap<String,String>());
					map = resMap.get(e.getKey());
				}
				map.put("total"+i, e.getValue());
			}
		}
		return MapToList(resMap);
	}

	@Override
	public  List<Map<String,String>> getPayTypeReports(
			String createDTString, String endDTString,APP app,int type) {
		
		Map<Integer, Map<String, String>>  resMap = new HashMap<Integer,Map<String,String>>();
		
		//支付宝
		Map<Integer,String> totalAliPayOrderCount = reportDao.getTotalAliPayOrderCount(createDTString,endDTString,app, type);
		//计算合计
		int sumInt = 0;
		for(Entry e:totalAliPayOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalAliPayOrderCount.put(SUMKEY, sumInt+"");
		//微信
		Map<Integer,String> totalWXPayOrderCount = reportDao.getTotalWXPayOrderCount(createDTString,endDTString,app, type);
		//计算合计
		sumInt = 0;
		for(Entry e:totalWXPayOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalWXPayOrderCount.put(SUMKEY, sumInt+"");
		//银联
		Map<Integer,String> totalChinaPayOrderCount = reportDao.getTotalChinaPayOrderCount(createDTString,endDTString,app, type);
		//计算合计
		sumInt = 0;
		for(Entry e:totalWXPayOrderCount.entrySet()){
			sumInt += Integer.parseInt((String) e.getValue());
		}
		totalWXPayOrderCount.put(SUMKEY, sumInt+"");
		 
		
		for(Entry<Integer, String> e :totalAliPayOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalAliPayOrderCount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalWXPayOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalWXPayOrderCount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalChinaPayOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalChinaPayOrderCount", e.getValue());
		}
		
		return MapToList(resMap);
		
	}

	

	@Override
	public  List<Map<String,String>> getRefundedReports(
			String createDTString, String endDTString,APP app,int type) {
		Map<Integer, Map<String, String>>  resMap = new HashMap<Integer,Map<String,String>>();
		//已退款订单总额
		Map<Integer,String> totalRefundOrderAmount = reportDao.getTotalRefundOrderAmount(createDTString,endDTString,app, type);
		
		//已退款订单数
		Map<Integer,String> totalRefundOrderCount = reportDao.getTotalRefundOrderCount(createDTString,endDTString,app, type);
		
		for(Entry<Integer, String> e :totalRefundOrderAmount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalRefundOrderAmount", e.getValue());
		}
		
		for(Entry<Integer, String> e :totalRefundOrderCount.entrySet()){
			Map<String, String> map = resMap.get(e.getKey());
			if(map == null) {
				resMap.put(e.getKey(), new HashMap<String,String>());
				map = resMap.get(e.getKey());
			}
			map.put("totalRefundOrderCount", e.getValue());
		}
		
		return MapToList(resMap);
	}
	
	private List<Map<String, String>> MapToList(
			Map<Integer, Map<String, String>> resMap) {
		List<Integer> datetimeList = new ArrayList<Integer>();
		
		for(Integer key :resMap.keySet()){
			datetimeList.add(key);
		}
		Collections.sort(datetimeList,new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1<SUMKEY && o2<SUMKEY) return o2-o1;
				else return o1-o2;
			}
			
		});
		List<Map<String,String>> resList = new ArrayList<Map<String,String>>();
		
		for(Integer dt:datetimeList){
			Map<String, String> map = resMap.get(dt);
			map.put("day", dt+"");
			resList.add(map);
		}
		return resList;
	}

}
