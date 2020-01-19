package com.wei.orderpay.contacts.order;

/**
 * 订单操作类型
 * 
 * @author : weihui
 * @createTime : 2016年4月13日 下午12:01:26
 * @version : 1.0
 * @description :
 *
 */
public enum OpType {
	
	/**提交订单*/
	buy(UserRole.buyer,1,""),
	
	/**订单支付*/
	pay(UserRole.buyer,2,""),
	
	/**卖家发货*/
	deliverGoods(UserRole.seller,3,"订单已发货"),
	
	/**买家收货(交易成功)*/
	getGoods(UserRole.buyer,4,""),
	
	/**取消订单*/
	cancel(UserRole.buyer,5,""),
	
	/**修改价格*/
	modifyPrice(UserRole.seller,6,"卖家已修改价格"),
	
	/**申请退款*/
	applicationRefund(UserRole.buyer,7,""),
	
	/**同意退款*/
	refund(UserRole.seller,8,"卖家已退款"),

	/**驳回退款*/
	rejectRefund(UserRole.cus,9,""),
	
	/**买家删除订单*/
	deleteOrderByBuyer(UserRole.buyer,10,""),
	
	/**卖家删除订单*/
	deleteOrderBySeller(UserRole.buyer,11,"");
	
	public UserRole userRole;
	public int opType;
	public String opDesc;
	
	private OpType(UserRole userRole,int opType,String opDesc){
		this.userRole = userRole;
		this.opType = opType;
		this.opDesc = opDesc;
	}
	
	public enum UserRole {
		/**
		 * 买家
		 */
		buyer,
		/**
		 * 卖家
		 */
		seller,
		/**
		 * 客服
		 */
		cus;
	}
}
