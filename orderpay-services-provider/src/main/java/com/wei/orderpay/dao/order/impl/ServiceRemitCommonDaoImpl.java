package com.wei.orderpay.dao.order.impl;

import com.wei.orderpay.bean.order.ServiceRemit;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.order.ServiceRemitCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author : wang.tao
 * @createTime : 2016年6月12日 上午10:21:52
 * @version : web1.4.3
 * @description :
 *
 */
@SuppressWarnings("all")
@Repository("serviceRemitCommonDao")
public class ServiceRemitCommonDaoImpl extends BaseDao implements ServiceRemitCommonDao{
	
	@Override
	public ServiceRemit getServiceRemitByID(int id, int source) {
		Map<String, Object> params = new HashMap<String, Object>() ;
        params.put("id", id) ;
        params.put("source", source); 
		return (ServiceRemit) getTemplate().selectOne("ServiceRemit.getServiceRemitByID", params);
	}
	
    @Override
    public List<ServiceRemit> getServiceRemitListByOrderID(int orderID ,int source) {
        Map<String, Object> paras = new HashMap<String, Object>() ;
        paras.put("orderID", orderID) ;
        paras.put("source", source) ;
        return (List<ServiceRemit>) getTemplate().selectList("ServiceRemit.getServiceRemitListByOrderID", paras);
    }

    @Override
    public int saveServiceRemit(ServiceRemit item) {
        return getTemplate().insert("ServiceRemit.saveServiceRemit", item);		
    }

    @Override
    public int getNotAddCount(Map<String, Object> params) {
        return (int)getTemplate().selectOne("ServiceRemit.getNotAddCount", params);
    }

    @Override
    public int updateAccountStatus(Map<String, Object> params) {
        return getTemplate().insert("ServiceRemit.updateAccountStatus", params);
    }

    @Override
    public List<ServiceRemit> getServiceRemitListByParas(Map<String, Object> params) {
        return (List<ServiceRemit>) getTemplate().selectList("ServiceRemit.getServiceRemitListByParas", params);
    }

	@Override
	public int updateServiceRemit4Success(int id, int source) {

		Map<String, Object> params = new HashMap<String, Object>() ;
        params.put("id", id) ;
        params.put("source", source); 
		return getTemplate().update("ServiceRemit.updateServiceRemit4Success", params);
	}

	@Override
	public int updateServiceRemit4Fail(int id, int source) {
		Map<String, Object> params = new HashMap<String, Object>() ;
        params.put("id", id) ;
        params.put("source", source); 
		return getTemplate().update("ServiceRemit.updateServiceRemit4Fail", params);
	}

	
    
}
