package com.wei.orderpay.manage.dao.user.impl;

import com.wei.orderpay.manage.bean.user.RecepientBean;
import com.wei.orderpay.manage.dao.common.BaseDao;
import com.wei.orderpay.manage.dao.user.ManageRecepientCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("recepientCommonDao")
public class ManageRecepientCommonDaoImpl extends BaseDao implements ManageRecepientCommonDao {
	
	@Override
	public RecepientBean getRecepientByID(int recepientID, int source) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("recepientID",recepientID);
		map.put("source",source);
		Object obj = getTemplate().selectOne("RecepientCommon.getRecepientByID", map);
		return obj == null ? null : (RecepientBean)obj;
	}

}
