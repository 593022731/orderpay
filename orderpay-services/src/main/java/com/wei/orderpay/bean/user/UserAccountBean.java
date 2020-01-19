package com.wei.orderpay.bean.user;

import com.wei.orderpay.bean.common.BaseBean;

import java.io.Serializable;

/**
 * 账户对象
 * 
 * @author : weihui
 * @createTime : 2016年4月12日 下午6:27:03
 * @version : 1.0
 * @description :
 *
 */
public class UserAccountBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**用户账号ID*/
    private int userAccountID;
    
    /**UID*/
    private int guid;
    
    /**余额*/
    private double balance;

    /**创建时间*/
    private String createDT ;
    
    /**修改时间*/
    private String updateDT ;

    public int getUserAccountID() {
        return userAccountID;
    }

    public void setUserAccountID(int userAccountID) {
        this.userAccountID = userAccountID;
    }

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCreateDT() {
        return createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }

    public String getUpdateDT() {
        return updateDT;
    }

    public void setUpdateDT(String updateDT) {
        this.updateDT = updateDT;
    }
    
}
