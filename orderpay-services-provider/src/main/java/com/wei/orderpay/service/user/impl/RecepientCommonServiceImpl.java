package com.wei.orderpay.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import com.wei.orderpay.bean.common.OrderPayResultCode;
import com.wei.orderpay.bean.user.RecepientBean;
import com.wei.orderpay.contacts.common.APP;
import com.wei.orderpay.dao.user.RecepientCommonDao;
import com.wei.orderpay.service.user.RecepientCommonService;
import org.springframework.stereotype.Service;

import com.jiutong.common.Result;

@Service("recepientCommonService")
public class RecepientCommonServiceImpl implements RecepientCommonService {

	@Resource
    RecepientCommonDao recepientCommonDao;
	
	@Override
	public int saveRecepient(RecepientBean recepient, APP app) {
		recepient.setSource(app.ordinal());
		return recepientCommonDao.saveRecepient(recepient);
	}

	@Override
	public List<RecepientBean> getUserRecepientsByUID(int buyerUID, APP app) {
		return recepientCommonDao.getUserRecepientsByUID(buyerUID,app.ordinal());
	}
	
	@Override
	public RecepientBean getUserRecepientsByID(int id, APP app) {
		return recepientCommonDao.getRecepientByID(id,app.ordinal());
	}

	@Override
	public Result updateRecepient(RecepientBean item, APP app) {
		Result result = new Result();
		RecepientBean recepientBean = recepientCommonDao.getRecepientByID(item.getRecepientID(), app.ordinal()); 
		if(recepientBean==null||item.getGuid()!=recepientBean.getGuid()){
			result.setResultCode(OrderPayResultCode.NO_AUTHORITY);
			return result;
		}
		item.setSource(app.ordinal());
		recepientCommonDao.saveRecepient(item);//保存修改后的新地址
		
		recepientBean = new RecepientBean();//旧地址归档
		recepientBean.setSource(app.ordinal());
		recepientBean.setRecepientID(item.getRecepientID());
		recepientBean.setIsArchived(1);
		recepientCommonDao.changeRecepient(recepientBean);
		
		return result;
	}

	@Override
	public Result deleteRecepient(int guid,int recepientID, APP app) {
		Result result = new Result();
		RecepientBean recepientBean = recepientCommonDao.getRecepientByID(recepientID, app.ordinal());
		if(recepientBean==null||guid!=recepientBean.getGuid()){
			result.setResultCode(OrderPayResultCode.NO_AUTHORITY);
			return result;
		}
		
		recepientBean = new RecepientBean();
		recepientBean.setSource(app.ordinal());
		recepientBean.setRecepientID(recepientID);
		recepientBean.setIsDeleted(1);
		recepientCommonDao.changeRecepient(recepientBean);
		return result;
	}
	
}
