package com.wei.orderpay.dao.user;

import com.wei.orderpay.bean.user.UserAccountTransactionLogBean;

import java.util.List;

/**
 * 用户账户表操作相关日志
 * @author : wang.tao
 * @createTime : 2016年4月13日 下午6:45:30
 * @version : 1.0
 * @description :
 *
 */
public interface UserAccountTransactionLogCommonDao {

    /**
     * 保存
     * @param item
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年4月13日 下午6:46:39
     */
    int saveUserAccountTransactionLog(UserAccountTransactionLogBean accountLog);
    
    /**
     * 查询交易记录
     * @param userAccountTransactionType
     * @param source
     * @param page
     * @param pageSize
     * @return
     *
     * @author : liuquan
     * @createTime : 2016年5月3日 下午4:33:41
     */
    List<UserAccountTransactionLogBean> getUserAccountTransactionLog(int guid,int source,int page,int pageSize);
    
    
}
