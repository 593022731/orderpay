package com.wei.orderpay.manage.dao.common;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;

/**
 * 解决多数据源不支持，故封装一个数据源，所有dao继承此类之间调用getTemplate方法
 * 
 * @author : weihui
 * @createTime : 2016年4月12日 下午6:23:58
 * @version : 1.0
 * @description :
 *
 */
public class BaseDao {

	@Resource
	SqlSessionFactory sqlSessionFactoryOrder;
	
	protected SqlSessionTemplate getTemplate(){
		return new SqlSessionTemplate(sqlSessionFactoryOrder);
	}
}
