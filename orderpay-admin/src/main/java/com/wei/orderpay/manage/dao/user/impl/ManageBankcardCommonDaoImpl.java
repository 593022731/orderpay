package com.wei.orderpay.manage.dao.user.impl;

import com.wei.orderpay.manage.bean.user.BankcardBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.user.ManageBankcardCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
/** 
 * @description :  
 * @Function:  
 * @author lizhi  
 * @createTime : 2016年4月13日 下午4:40:40
 */
@Repository("manageBankcardCommonDao")
public class ManageBankcardCommonDaoImpl extends BaseDao implements ManageBankcardCommonDao {

	@Override
	public BankcardBean getBankcardDetail(int guid, int bankCardID, int source) {
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("guid", guid);
		params.put("bankCardID", bankCardID);
		params.put("source", source);
		return (BankcardBean)getTemplate().selectOne("ManageBankcardCommon.getBankcardDetail", params);
	}
}
