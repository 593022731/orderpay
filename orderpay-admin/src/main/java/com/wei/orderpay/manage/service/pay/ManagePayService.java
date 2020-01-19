package com.wei.orderpay.manage.service.pay;

import com.wei.orderpay.manage.contacts.common.APP;


/**
 * 支付service
 * 
 * @author : weihui
 * @createTime : 2016年4月25日 上午10:36:25
 * @version : 1.0
 * @description :
 *
 */
public interface ManagePayService {
	
	/**
	 * 确认提现
	 * @param id 提现记录主键ID
	 * @param app
	 * @return
	 *
	 * @author : weih
	 * @createTime : 2016年6月1日 下午3:19:06
	 */
	void doConfirmExtractAmount(int id,APP app);
	
}
