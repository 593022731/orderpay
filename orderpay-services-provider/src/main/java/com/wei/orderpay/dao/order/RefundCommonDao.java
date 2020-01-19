package com.wei.orderpay.dao.order;

import com.wei.orderpay.bean.order.RefundBean;

import java.util.List;

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
     * 保存申请
     * @param item
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年4月13日 下午5:35:24
     */
    int saveRefund(RefundBean item);
    
    /**
     * 卖家同意退款
     * @param item
     * @return
     *
     * @author : weihui
     * @createTime : 2016年4月15日 下午6:39:59
     */
    int agreeRefund(RefundBean item);
    
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
     * 第三方查询接口退款成功更新字段
     * @param id
     * @param source
     * @return
     *
     * @author : weihui
     * @createTime : 2016年5月9日 下午6:08:44
     */
    int refundSuccess(int id,int source);
    
    /**
     * 获取未退款成功的支付宝订单(支付宝需要手动退款)
     * @param source
     * @return
     *
     * @author : weihui
     * @createTime : 2016年5月9日 下午6:22:18
     */
    List<RefundBean> getAliRefundOrders(int source);
    
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
