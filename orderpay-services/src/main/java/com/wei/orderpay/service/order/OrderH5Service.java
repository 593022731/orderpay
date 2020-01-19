/************************************************************************************
 * Create at 2016/9/8
 *
 * Author:song.ty
 *
 *************************************************************************************/

package com.wei.orderpay.service.order;

import com.wei.orderpay.bean.order.OrdersBean;

/**
 * ${DESCRIPTION}
 *
 * @author song.ty
 * @create 2016-09-08-14:41
 **/
public interface OrderH5Service {
    String saveOrder();
    OrdersBean findByOrderCode(String orderCode);
    void payOrder(String orderCode);
}
