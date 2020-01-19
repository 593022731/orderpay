package com.wei.orderpay.dao.user.impl;

import com.jiutong.common.tools.CommonUtils;
import com.wei.orderpay.bean.user.BankcardBean;
import com.wei.orderpay.dao.common.BaseDao;
import com.wei.orderpay.dao.user.BankcardCommonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @description :  
 * @Function:  
 * @author lizhi  
 * @createTime : 2016年4月13日 下午4:40:40
 */
@Repository("bankcardCommonDao")
public class BankcardCommonDaoImpl extends BaseDao implements BankcardCommonDao {

    @Override
    public int saveBankcard(BankcardBean bankCard) {
        
        return getTemplate().insert("BankcardCommon.saveBankcard", bankCard);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<BankcardBean> getBankcardList(int guid, int source) {
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("guid", guid);
		map.put("source", source);
		
		List<BankcardBean> list = (List<BankcardBean>)getTemplate().selectList("BankcardCommon.getBankcardList", map);
		
		return list;
	}

	@Override
	public int deleteBandcard(int guid,int bankCardID, int source) {
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("guid", guid);
		map.put("bankCardID", bankCardID);
		map.put("source", source);
		return getTemplate().update("BankcardCommon.deleteBandcard", map);
	}

	@Override
	public BankcardBean getBankcardDetail(int guid, int bankCardID, int source) {
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("guid", guid);
		params.put("bankCardID", bankCardID);
		params.put("source", source);
		return (BankcardBean)getTemplate().selectOne("BankcardCommon.getBankcardDetail", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getGuidListByBankcardNoOrName(String name,int bankcardNo, int source) {
		Map<String,Object> params = new HashMap<String,Object>(); 
		if(CommonUtils.isNotEmpty(name)){
			params.put("name", name);
		}
		if(bankcardNo > 0){
			params.put("bankcardNo", bankcardNo);
		}
		params.put("source", source);
		return (List<Integer>)getTemplate().selectList("BankcardCommon.getGuidListByBankcardNoOrName", params);
	}

}
