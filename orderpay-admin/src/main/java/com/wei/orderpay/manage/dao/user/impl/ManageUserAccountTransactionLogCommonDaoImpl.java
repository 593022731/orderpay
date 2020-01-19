package com.wei.orderpay.manage.dao.user.impl;

import com.wei.orderpay.manage.bean.user.UserAccountTransactionLogBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.user.ManageUserAccountTransactionLogCommonDao;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author : wang.tao
 * @createTime : 2016年4月13日 下午6:47:53
 * @version : 1.0
 * @description :
 *
 */
@Repository("userAccountTransactionLogCommonDao")
public class ManageUserAccountTransactionLogCommonDaoImpl extends BaseDao implements ManageUserAccountTransactionLogCommonDao {

    @Override
    public int saveUserAccountTransactionLog(UserAccountTransactionLogBean accountLog) {
    	accountLog.setRecordCode(System.currentTimeMillis()+"");
        return getTemplate().insert("UserAccountTransactionLogCommon.saveUserAccountTransactionLog", accountLog);
    }

}
