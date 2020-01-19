package com.wei.orderpay.manage.bean.user;

import com.jiutong.orderpay.bean.common.BaseBean;

import java.io.Serializable;

/**
 * 
 * @author : wang.tao
 * @createTime : 2016年4月13日 上午11:12:25
 * @version : 1.0
 * @description :账户事物操作日志记录表
 *
 */
public class UserAccountTransactionLogBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 8985789381440952737L;
    
    /**主键ID*/
    private int id ;
    
    /**用户账号ID*/
    private int userAccountID;
    
    /**来源类型(参考枚举UserAccountTransaction)*/
    private int userAccountTransactionType;
    
    /**来源类型对应的ID*/
    private int resourceID;
    
    /**当前余额*/
    private double balance;
    
    /**变更金额(可用+-代表收入支出)*/
    private double charge;
    
    /**创建时间*/
    private String createDT ;
    
    /**修改时间*/
    private String updateDT ;
    
    /**交易记录号(仅页面展示使用)*/
    private String recordCode;

    /**交易记录状态（1：收款成功，2：提现成功 3：提现失败，4：提现中）*/
    private int processStatus;
    
    public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserAccountID() {
        return userAccountID;
    }

    public void setUserAccountID(int userAccountID) {
        this.userAccountID = userAccountID;
    }

    public int getUserAccountTransactionType() {
        return userAccountTransactionType;
    }

    public void setUserAccountTransactionType(int userAccountTransactionType) {
        this.userAccountTransactionType = userAccountTransactionType;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
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

	public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}

	
    
}
