package com.wei.orderpay.manage.dao.user.impl;

import com.wei.orderpay.manage.bean.user.UserAccountBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.user.ManageUserAccountCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository("userAccountCommonDao")
public class ManageUserAccountCommonDaoImpl extends BaseDao implements ManageUserAccountCommonDao {

    @Override
    public int saveUserAccount(UserAccountBean userAccount) {
        return getTemplate().insert("UserAccountCommon.saveUserAccount", userAccount);
    }
    
    @Override
    public UserAccountBean getUserAccountForUpdate(int guid,int source) {
    	Map<String ,Object> params = new HashMap<String ,Object>();
    	params.put("guid", guid);    
    	params.put("source", source);    
    	Object obj = getTemplate().selectOne("UserAccountCommon.getUserAccountForUpdate", params);
    	return obj == null ? null : (UserAccountBean)obj;
    }

	@Override
	public int updateBalance(double balance,int guid,int source) {
		Map<String ,Object> params = new HashMap<String ,Object>();
		params.put("guid", guid); 
		params.put("balance", balance);
		params.put("source", source);
		return getTemplate().update("UserAccountCommon.updateBalance", params);
	}

}
