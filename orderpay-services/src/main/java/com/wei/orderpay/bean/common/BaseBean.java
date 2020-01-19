package com.wei.orderpay.bean.common;

import com.wei.orderpay.contacts.common.APP;

import java.io.Serializable;

/**
 * 应用名和来源
 * 
 * @author : weihui
 * @createTime : 2016年4月13日 上午10:28:48
 * @version : 1.0
 * @description :
 *
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private APP app;
	
	private int source;

	public APP getApp() {
		return app;
	}
	
	public void setApp(int app) {
		this.app = APP.values()[app];
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
}
