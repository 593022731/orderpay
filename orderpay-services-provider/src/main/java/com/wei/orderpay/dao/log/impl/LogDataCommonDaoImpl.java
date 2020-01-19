package com.wei.orderpay.dao.log.impl;

import com.wei.orderpay.bean.log.LogDataBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.log.LogDataCommonDao;
import org.springframework.stereotype.Repository;

@Repository("logDataCommon")
public class LogDataCommonDaoImpl extends BaseDao implements LogDataCommonDao {

    @Override
    public int saveLogData(LogDataBean logdata) {
        return getTemplate().insert("LogDataCommon.saveLogData", logdata);
    }

}
