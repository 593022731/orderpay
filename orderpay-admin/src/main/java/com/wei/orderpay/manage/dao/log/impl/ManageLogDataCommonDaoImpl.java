package com.wei.orderpay.manage.dao.log.impl;

import com.wei.orderpay.manage.bean.log.LogDataBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.log.ManageLogDataCommonDao;
import org.springframework.stereotype.Repository;


@Repository("logDataCommon")
public class ManageLogDataCommonDaoImpl extends BaseDao implements ManageLogDataCommonDao {

    @Override
    public int saveLogData(LogDataBean logdata) {
        return getTemplate().insert("LogDataCommon.saveLogData", logdata);
    }

}
