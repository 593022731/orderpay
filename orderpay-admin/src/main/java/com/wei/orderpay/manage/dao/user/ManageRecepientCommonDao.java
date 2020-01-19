package com.wei.orderpay.manage.dao.user;

import com.wei.orderpay.manage.bean.user.RecepientBean;

/**
 * 收货地址
 *
 * @author : liuquan
 * @createTime : 2016年4月13日	下午4:38:25
 * @version : 1.0 ss
 * @description : 
 *
 */
public interface ManageRecepientCommonDao {
	
	/**
	 * 根据收货地址id获取收货地址详情
	 * @param recepientID
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月26日 下午5:39:20
	 */
	RecepientBean getRecepientByID(int recepientID, int source);

}
