package com.wei.orderpay.manage.service.order;

import com.wei.orderpay.manage.bean.order.ManageRefundBean;
import com.wei.orderpay.manage.contacts.common.APP;

import java.util.List;
/**
 * 后台退款
 * @author : weih
 * @createTime : 2016年6月7日 下午5:29:19
 */
public interface ManageRefundCommonService {
	
	/**
	 * 退款管理查询
	 * @param orderCode
	 * @param orderPayCode
	 * @param flag
	 * @param startDT
	 * @param endDT
	 * @param buyerUids
	 * @param pageNo
	 * @param pageSize
	 * @param app
	 * @return
	 *
	 * @author : weih
	 * @createTime : 2016年6月7日 下午5:34:03
	 */
	List<ManageRefundBean> getRefundInfo(String orderCode,String orderPayCode,String flag,String startDT,String endDT,List<Integer> buyerUids,int pageNo ,int pageSize,APP app);
    
    /**
     * 支付宝批量退款
     * @param app
     * @return
     * @throws Exception
     *
     * @author : weih
     * @createTime : 2016年6月7日 下午6:24:28
     */
    String getRequestFormForAli(APP app,List<Integer> orderIDList) throws Exception;
    
    /**
     * 支付宝单个退款
     * @param orderPayCode
     * @param refundFee
     * @param app
     * @return
     * @throws Exception
     *
     * @author : weih
     * @createTime : 2016年6月7日 下午6:24:28
     */
    String getRequestFormForAli(String orderPayCode,String refundFee,APP app) throws Exception;
}
