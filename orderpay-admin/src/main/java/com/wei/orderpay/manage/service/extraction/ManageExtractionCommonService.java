package com.wei.orderpay.manage.service.extraction;

import com.jiutong.common.APP;

/**
 * 提现管理
 *
 * @author : liuquan
 * @createTime : 2016年6月24日 上午10:30:00
 * @version : 1.0 
 * @description : 
 *
 */
public interface ManageExtractionCommonService {
	
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
