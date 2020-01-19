package com.wei.orderpay.dao.user.impl;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.bean.user.UserAccountTransactionLogBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.user.UserAccountTransactionLogCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author : wang.tao
 * @createTime : 2016年4月13日 下午6:47:53
 * @version : 1.0
 * @description :
 *
 */
@Repository("userAccountTransactionLogCommonDao")
public class UserAccountTransactionLogCommonDaoImpl extends BaseDao implements UserAccountTransactionLogCommonDao {

    @Override
    public int saveUserAccountTransactionLog(UserAccountTransactionLogBean accountLog) {
    	if(CommonUtils.isEmptyString(accountLog.getRecordCode())){
    		accountLog.setRecordCode(System.currentTimeMillis()+"");
    	}
        return getTemplate().insert("UserAccountTransactionLogCommon.saveUserAccountTransactionLog", accountLog);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAccountTransactionLogBean> getUserAccountTransactionLog(int guid, int source,int page ,int pageSize){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("guid",guid);
		map.put("source",source);
		map.put("page", (page-1) *  pageSize);
		map.put("pageSize", pageSize);
		return (List<UserAccountTransactionLogBean>)getTemplate().selectList("UserAccountTransactionLogCommon.getUserAccountTransactionLog",map);
	}

}
