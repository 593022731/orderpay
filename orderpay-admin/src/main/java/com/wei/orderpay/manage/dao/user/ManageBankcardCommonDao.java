package com.wei.orderpay.manage.dao.user;

import com.wei.orderpay.manage.bean.user.BankcardBean;

/** 
 * @description :银行卡单表操作  
 * @Function:  
 * @author lizhi  
 * @createTime : 2016年4月13日 下午3:12:29
 */
public interface ManageBankcardCommonDao {

    /**
	 * 详情
	 * @param guid
	 * @param bankCardID
	 * @param source
	 * @return
	 */
	BankcardBean getBankcardDetail(int guid, int bankCardID, int source);
}
