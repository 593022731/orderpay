package com.wei.orderpay.manage.dao.order.impl;

import com.wei.orderpay.manage.bean.order.OrderOperationLogBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.order.ManageOrderOperationLogCommonDao;
import org.springframework.stereotype.Repository;



@Repository("orderOperationLogCommonDao")
public class ManageOrderOperationLogCommonDaoImpl extends BaseDao implements
		ManageOrderOperationLogCommonDao {

	@Override
	public int saveOrderOperationLog(OrderOperationLogBean item) {
		return getTemplate().insert("OrderOperationLogCommon.saveOrderOperationLog",item);
	}

}
