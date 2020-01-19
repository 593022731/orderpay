package com.wei.orderpay.manage.dao.order;

import com.wei.orderpay.manage.bean.order.RefundBean;

/**
 * 
 * @author : wang.tao
 * @createTime : 2016年4月13日 下午4:50:56
 * @version : 1.0
 * @description :申请退款
 *
 */
public interface RefundCommonDao {
    
    
    /**
     * 客服驳回退款
     * @param item
     * @return
     *
     * @author : weihui
     * @createTime : 2016年4月15日 下午6:40:07
     */
    int rejectRefund(RefundBean item);
    
    /**
     * 获取退款原因
     * @param orderID -- 订单id
     * @param source
     * @return
     * @author lizhi
     * @creatTime 2016年5月13日上午10:25:51
     */
    public RefundBean queryRefundReason(int orderID,int source);

}
