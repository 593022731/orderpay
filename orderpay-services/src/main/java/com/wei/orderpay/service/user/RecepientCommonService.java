package com.wei.orderpay.service.user;

import com.jiutong.common.Result;
import com.wei.orderpay.bean.user.RecepientBean;
import com.wei.orderpay.contacts.common.APP;

import java.util.List;


/**
 * 收货地址相关service
 *
 * @author : liuquan
 * @createTime : 2016年4月26日 下午5:28:05
 * @version : 1.0 
 * @description : 
 *
 */
public interface RecepientCommonService {
	
	/**
	 * 保存收获地址
	 * @param recepient
	 * @param app
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月26日 下午5:31:33
	 */
	int saveRecepient(RecepientBean recepient, APP app);
	
	/**
	 * 获取用户的收货地址列表(没有删除也没有归档)
	 * 根据app和uid
	 * @param guid 买家uid
	 * @param app
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年4月26日 下午5:59:15
	 */
	List<RecepientBean> getUserRecepientsByUID(int guid,APP app);

	/**
	 * 修改收获地址
	 * @param item
	 * @param app
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年5月11日 下午4:20:31
	 */
	Result updateRecepient(RecepientBean item, APP app);

	/**
	 * 删除收货地址
	 * @param guid
	 * @param recepientID
	 * @param app
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年5月25日 上午10:50:29
	 */
	Result deleteRecepient(int guid,int recepientID,APP app);

	/**
	 * 查询收货地址按ID
	 * @param id
	 * @param app
	 * @return
	 *
	 * @author : song.ty
	 * @createTime : 2016年7月25日 下午4:31:23
	 */
	RecepientBean getUserRecepientsByID(int id, APP app);
}
