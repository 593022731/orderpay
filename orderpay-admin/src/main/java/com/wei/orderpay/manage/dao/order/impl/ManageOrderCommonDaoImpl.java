package com.wei.orderpay.manage.dao.order.impl;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.manage.bean.order.OrdersBean;
import com.wei.orderpay.manage.bean.order.ServiceOrderBean;
import com.wei.orderpay.manage.contacts.user.UserAccountTransaction;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.order.ManageOrderCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("all")
@Repository("manageOrderCommonDaoOrderCommonDao")
public class ManageOrderCommonDaoImpl extends BaseDao implements ManageOrderCommonDao {
	
	@Override
	public OrdersBean getOrders(int orderID, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		Object selectOne = getTemplate().selectOne("OrderCommon.getOrdersManage",
				params);
		return selectOne == null ? null : (OrdersBean) selectOne;
	}
	
	@Override
	public int updateOrderStatus(int orderID, int source, int guid,
			int newStatus, int oldStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("source", source);
		params.put("guid", guid);
		params.put(
				"statusDT",
				CommonUtils.getDateString("yyyy-MM-dd HH:mm:ss",
						System.currentTimeMillis()));
		params.put("newStatus", newStatus);
		params.put("oldStatus", oldStatus);
		return getTemplate().update("OrderCommon.updateOrderStatus", params);
	}

	@Override
	public int updateOrderStatusDT(int orderID,int guid,int orderStatus,int source){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderID", orderID);
		params.put("guid", guid);
		params.put("source", source);
		params.put("status", orderStatus);
		return getTemplate().update("OrderCommon.updateOrderStatusDT", params);
	}

	@Override
	public OrdersBean getOrdersByOrderCode(String orderCode, int source) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("source", source);
		return (OrdersBean) getTemplate().selectOne("OrderCommon.getOrdersByOrderCode", params);
	}
	
	@Override
	public List<OrdersBean> getBuyerOrderList4Buyer(List<Integer> guids,String status ,String createStartDT ,String createEndDT,int source ,int page ,int pageSize ){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", (page - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("source",source);
		
		params.put("guids", guids);
		params.put("status", Integer.valueOf(status));
		params.put("createStartDT", createStartDT);
		if (CommonUtils.isNotEmpty(createEndDT)) {  //结束时间
			params.put("createEndDT", createEndDT);
		}
		
		return (List<OrdersBean>) getTemplate().selectList("OrderCommon.getBuyerOrderList4Buyer", params);
	}
	
	@Override
	public List<OrdersBean> getBuyerOrderList4Seller(List<Integer> guids,String status ,String createStartDT ,String createEndDT,int source ,int page ,int pageSize ){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", (page - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("source",source);
		
		params.put("guids", guids);
		params.put("status", Integer.valueOf(status));
		params.put("createStartDT", createStartDT);
		if (CommonUtils.isNotEmpty(createEndDT)) {  //结束时间
			params.put("createEndDT", createEndDT);
		}
		
		return (List<OrdersBean>) getTemplate().selectList("OrderCommon.getBuyerOrderList4Seller", params);
	}

	@Override
	public List<OrdersBean> getOrderByNull4Manage(String status,String createStartDT, String createEndDT, int source, int page,int pageSize) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", (page - 1) * pageSize);
		params.put("pageSize", pageSize);
		params.put("source",source);
		params.put("status", Integer.valueOf(status));
		params.put("createStartDT", createStartDT);
		if (CommonUtils.isNotEmpty(createEndDT)) {  //结束时间
			params.put("createEndDT", createEndDT);
		}
		return (List<OrdersBean>) getTemplate().selectList("OrderCommon.getOrderByNull4Manage", params);
	}

	@Override
	public  List<OrdersBean> getOrdersUnpaidOver24h(int orderStatus,int source) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderStatus", orderStatus);
		map.put("source", source);
		return (List<OrdersBean>)getTemplate().selectList("OrderCommon.getOrdersUnpaidOver24h",map);
	}

	@Override
	public String getOrderCodesByStatusAndFollowMark(List<Integer> statuses, int followMark, int source, int pageNo,int pageSize) {
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("statuses", statuses);
		params.put("followMark",followMark);
		params.put("page", pageNo);
		params.put("pageSize", pageSize);
		params.put("source",source);
		
		return (String) getTemplate().selectOne("OrderCommon.getOrderCodesByStatusAndFollowMark", params);
	}

	@Override
	public Integer updateFollowMarkByOrderCode(String orderCode) {
		
		return getTemplate().update("OrderCommon.updateFollowMarkByOrderCode", orderCode);
	}

	@Override
	public void updateFollowMark4Quartz(List<Integer> statuses) {
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("statuses", statuses);
		
		getTemplate().update("OrderCommon.updateFollowMark4Quartz",params);
	}

	@Override
	public List<Map<String, Object>> getDealList(List<Integer> listBuyer,List<Integer> listSeller, String recordCode, String startDT, String endDT, int source,int pageNo,int pageSize) {
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("page", (pageNo-1) *  pageSize);
		params.put("pageSize", pageSize);
		params.put("source", source);
		
		if(listBuyer!=null && !listBuyer.isEmpty()){
			params.put("listBuyer", listBuyer);
		}
		if(listSeller!=null && !listSeller.isEmpty()){
			params.put("listSeller", listSeller);
		}
		if(CommonUtils.isNotEmpty(recordCode)){
			params.put("recordCode", recordCode);
		}
		if(CommonUtils.isNotEmpty(startDT)){
			params.put("startDT", startDT);
		}
		if(CommonUtils.isNotEmpty(endDT)){
			params.put("endDT", endDT);
		}
		/*List<Integer> list = new ArrayList<>();
			if(CommonUtils.isEmptyString(status)){
				list = new ArrayList<>();
				list.add(OrderStatus.payWaitDelivery.ordinal());
				list.add(OrderStatus.waitGoodsReceipt.ordinal());
				list.add(OrderStatus.receipted.ordinal());
				list.add(OrderStatus.arbitrationed.ordinal());
				list.add(OrderStatus.refunded.ordinal());
				params.put("statusList",list);
			}else if(status.equals("0")){//进行中
				list = new ArrayList<>();
				list.add(OrderStatus.payWaitDelivery.ordinal());
				list.add(OrderStatus.waitGoodsReceipt.ordinal());
				params.put("statusList",list);
			}else if(status.equals("1")){//已完成
				list = new ArrayList<>();
				list.add(OrderStatus.receipted.ordinal());
				list.add(OrderStatus.arbitrationed.ordinal());
				list.add(OrderStatus.refunded.ordinal());//补充部分退款完成
				params.put("statusList",list);
			}*/
		params.put("userAccountTransactionType", UserAccountTransaction.PAY.ordinal());
		
		return (List<Map<String, Object>>)getTemplate().selectList("OrderCommon.getDealList",params);
	}
	
	//=================================================================================业务订单相关处理  begin=====================================================//
	
	@Override
	public ServiceOrderBean getServiceOrderByOrderCode(Map<String, Object> params) {
		
		return (ServiceOrderBean) getTemplate().selectOne("OrderCommon.getServiceOrderByOrderCode", params);
	}

    @Override
    public List<ServiceOrderBean> getServiceOrders(Map<String, Object> params) {
        return (List<ServiceOrderBean>) getTemplate().selectList("OrderCommon.getServiceOrders", params);
    }

	
	@Override
	public List<OrdersBean> getOrdersList(List<Integer> productID,List<Integer> userBidID,Integer status,Integer productOrBid,String orderCode,Integer buyerOrSellerUid ,String createStartDT,String createEndDT,Integer source, Integer pageNo,Integer pageSize,Integer orderType) {
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("productIDList", productID);
		params.put("userBidIDList", userBidID);
		params.put("status", status);
		params.put("productOrBid", productOrBid);
		params.put("orderCode", orderCode);
		params.put("guid", buyerOrSellerUid);
		params.put("createStartDT", createStartDT);
		params.put("createEndDT", createEndDT);
		params.put("page", (pageNo-1)*pageSize);
		params.put("pageSize", pageSize);
		params.put("source",source);
		params.put("type", orderType);
		return (List<OrdersBean>) getTemplate().selectList("OrderCommon.getOrderList", params);
	}
	
	@Override
	public int getOrdersListCount(List<Integer> productID,List<Integer> userBidID,Integer status,Integer productOrBid,String orderCode,Integer buyerOrSellerUid ,String createStartDT,String createEndDT,Integer source, Integer pageNo,Integer pageSize,Integer orderType) {
		
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("productIDList", productID);
		params.put("userBidIDList", userBidID);
		params.put("status", status);
		params.put("productOrBid", productOrBid);
		params.put("orderCode", orderCode);
		params.put("guid", buyerOrSellerUid);
		params.put("createStartDT", createStartDT);
		params.put("createEndDT", createEndDT);
		params.put("page", (pageNo-1)*pageSize);
		params.put("pageSize", pageSize);
		params.put("source",source);
		params.put("type", orderType);
		return (int) getTemplate().selectOne("OrderCommon.getOrderListCount", params);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//=================================================================================业务订单相关处理  end=====================================================//
}





