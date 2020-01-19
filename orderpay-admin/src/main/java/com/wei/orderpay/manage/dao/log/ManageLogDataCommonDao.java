package com.wei.orderpay.manage.dao.log;

import com.wei.orderpay.manage.bean.log.LogDataBean;

/** 
 * 日志记录表
 * @description :  
 * @author lizhi  
 * @createTime : 2016年4月13日 下午3:01:32
 */
public interface ManageLogDataCommonDao {
    
    /**
     * 保存日志数据
     * @description : 
     * @author lizhi  
     * @createTime : 2016年4月13日 下午3:06:37
     */
    public int saveLogData(LogDataBean logdata);
}
