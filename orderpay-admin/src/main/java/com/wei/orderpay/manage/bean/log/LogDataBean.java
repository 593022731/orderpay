package com.wei.orderpay.manage.bean.log;

import com.wei.orderpay.manage.bean.common.BaseBean;

/** 
 * @description : commonserver库logdata表  
 * @Function: 日志记录表(申请退款，退款查询)  
 * @author lizhi  
 * @createTime : 2016年4月13日 下午3:01:32
 */

public class LogDataBean extends BaseBean {

    private static final long serialVersionUID = -2611980688186633530L;
    
    private int id ;
    
    //日志类型(参考枚举LogType)
    private int logType;
    
    //日志类型对应的id
    private int resourceID;
    
    //可备注,可扩展字段,如resourceID不够用的情况下，存储json，订单举例({orderID:1,guid:100000,storeID:1})
    private String remark;
    
    //创建时间
    private String createDT;
    
    //修改时间
    private String updateDT;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "LogDataBean [id=" + id + ", logType=" + logType + ", resourceID=" + resourceID + ", remark="
                + remark + ", createDT=" + createDT + ", updateDT=" + updateDT + "]";
    }
    
}
