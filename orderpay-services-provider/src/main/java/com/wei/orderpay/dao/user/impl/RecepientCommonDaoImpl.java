package com.wei.orderpay.dao.user.impl;


import com.wei.orderpay.bean.user.RecepientBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.user.RecepientCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("recepientCommonDao")
public class RecepientCommonDaoImpl extends BaseDao implements RecepientCommonDao {

	@Override
	public int saveRecepient(RecepientBean recepient) {
		return getTemplate().insert("RecepientCommon.saveRecepient", recepient);
	}

	@Override
	public RecepientBean getRecepientByID(int recepientID,int source) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("recepientID",recepientID);
		map.put("source",source);
		Object obj = getTemplate().selectOne("RecepientCommon.getRecepientByID", map);
		return obj == null ? null : (RecepientBean)obj;
	} 

	@SuppressWarnings("unchecked")
	@Override
	public List<RecepientBean> getUserRecepientsByUID(int guid, int source) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("guid",guid);
		map.put("source",source);
		return (List<RecepientBean>) getTemplate().selectList("RecepientCommon.getUserRecepientsByUID", map);
	}

	@Override
	public int changeRecepient(RecepientBean item) {
		return getTemplate().update("RecepientCommon.updateRecepient",item);
	}
}
