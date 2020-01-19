package com.wei.orderpay.manage.service.order;

import com.wei.orderpay.manage.bean.order.ServiceOrderBean;
import com.wei.orderpay.manage.bean.order.ServiceRemit;
import com.wei.orderpay.manage.contacts.common.APP;

import java.util.List;

/**
 * 业务订单service
 * 
 * @author : lizhi
 * @creatTime : 2016年6月22日上午10:32:59
 * @version : 1.0
 * @description : 
 *
 */
public interface ManageBusinessOrderService {
	
	//=================================================================================业务订单相关处理  begin=====================================================//
	
		/**
		 * 根据订单号查询 业务订单相关
		 * @param keyword
		 * @param accountStatus
		 * @param createStartDT
		 * @param createEndDT
		 * @param page
		 * @param pageSize
		 * @return
		 * @author lizhi
		 * @creatTime 2016年6月7日下午2:41:45
		 */
		public ServiceOrderBean getServiceOrderByOrderCode(String keyword, int accountStatus, String createStartDT, String createEndDT, APP app);
		
		/**
		 * 业务人员订单查询
		 * @param buyerUserIDs
		 * @param servicerUserIDs
		 * @param accountStatus
		 * @param createStartDT
		 * @param createEndDT
		 * @param ordinal
		 * @param pageNo
		 * @param pageSize
		 * @return
		 *
		 * @author : wang.tao
		 * @createTime : 2016年6月11日 下午4:42:47
		 * @version : web1.4.3
		 */
		List<ServiceOrderBean> getServiceOrders(List<Integer> buyerUserIDs,List<Integer> servicerUserIDs, int accountStatus,String createStartDT, String createEndDT, APP app, int pageNo,int pageSize) ;
		
		/**
		 * 根据orderID查询业务订单详情
		 * @param orderID
		 * @return
		 *
		 * @author : wang.tao
		 * @createTime : 2016年6月12日 下午12:09:26
		 * @version : web1.4.3
		 */
		List<ServiceRemit> getServiceRemitListByOrderID(int orderID , APP app) ;
		
		/**
		 * 保存业务订单
		 * @param item
		 * @return
		 *
		 * @author : wang.tao
		 * @createTime : 2016年6月12日 下午12:10:06
		 * @version : web1.4.3
		 */
		int saveServiceRemit(ServiceRemit item ,int guid, APP app);
		
		/**
		 * 根据条件查询业务汇款表
		 * @param item
		 * @param startDT
		 * @param endDT
		 * @param app
		 * @param pageNo
		 * @param pageSize
		 * @return
		 *
		 * @author : wang.tao
		 * @createTime : 2016年6月14日 下午9:31:33
		 * @version : web1.4.3
		 */
		List<ServiceRemit> getServiceRemitListByParas(ServiceRemit item ,String startDT ,String endDT,APP app ,int pageNo,int pageSize) ;
		
		/**
		 * 业务汇款
		 * @param id
		 * @param app
		 * @author lizhi
		 * @creatTime 2016年6月16日下午4:16:00
		 */
		public void doServiceRemit(int id ,APP app);
		
		//=================================================================================业务订单相关处理 end=====================================================//
		
			
}
