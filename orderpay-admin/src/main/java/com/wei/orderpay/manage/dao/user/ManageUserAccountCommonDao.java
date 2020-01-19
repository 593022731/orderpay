package com.wei.orderpay.manage.dao.user;

import com.wei.orderpay.manage.bean.user.UserAccountBean;


/**
 * 账户相关dao
 * 
 * @author : weihui
 * @createTime : 2016年4月12日 下午6:33:40
 * @version : 1.0
 * @description :
 *
 */
public interface ManageUserAccountCommonDao {
    
    /**
     * 保存用户账号
     * @param item
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年4月13日 下午6:32:02
     */
    int saveUserAccount(UserAccountBean userAccount) ;
    
    /**
     * 查询用户账户信息(根据guid)-- for Update 加锁
     * @description :
     * @param user
     * @param source
     * @return 
     * @author lizhi  
     * @createTime : 2016年4月13日 下午3:06:37
     */
    UserAccountBean getUserAccountForUpdate(int guid,int source);
    
    /**
     * 更新账户余额
     * @param balance
     * @param guid
     * @param source
     * @return
     *
     * @author : weihui
     * @createTime : 2016年4月25日 下午3:57:39
     */
    int updateBalance(double balance,int guid,int source);

}
