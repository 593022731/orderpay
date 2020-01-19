package com.wei.orderpay.manage.dao.user;

import com.wei.orderpay.manage.bean.user.UserAccountTransactionLogBean;

/**
 * 用户账户表操作相关日志
 * @author : wang.tao
 * @createTime : 2016年4月13日 下午6:45:30
 * @version : 1.0
 * @description :
 *
 */
public interface ManageUserAccountTransactionLogCommonDao {

    /**
     * 保存
     * @param item
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年4月13日 下午6:46:39
     */
    int saveUserAccountTransactionLog(UserAccountTransactionLogBean accountLog);
    
}
