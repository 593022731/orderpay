package com.wei.orderpay.dao.user;

import com.wei.orderpay.bean.user.RecepientBean;

import java.util.List;

/**
 * 收货地址
 *
 * @author : liuquan
 * @createTime : 2016年4月13日	下午4:38:25
 * @version : 1.0 
 * @description : 
 *
 */
public interface RecepientCommonDao {
	
	/**
	 * 保存收货地址
	 * @param recepient
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月26日 下午4:17:16
	 */
	int saveRecepient(RecepientBean recepient);
	
	/**
	 * 根据收货地址id获取收货地址详情
	 * @param recepientID
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月26日 下午5:39:20
	 */
	RecepientBean getRecepientByID(int recepientID,int source);

	/**
	 * 根据app和uid获取用户的收货地址列表
	 * @param uid
	 * @param source
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月26日 下午6:02:10
	 */
	List<RecepientBean> getUserRecepientsByUID(int guid, int source);

	/**
	 * 修改收货地址
	 * @param item
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年5月11日 下午4:25:37
	 */
	int changeRecepient(RecepientBean item);
	
}
