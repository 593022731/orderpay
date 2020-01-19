package com.wei.orderpay.dao.order;

import com.wei.orderpay.bean.order.ManageRefundBean;

import java.util.List;
import java.util.Map;

/**
 * 后台退款管理
 * @author : weih
 * @createTime : 2016年6月7日 下午5:25:10
 */
public interface ManageRefundCommonDao {

	/**
	 * 退款管理查询
	 * @param params
	 * @return
	 *
	 * @author : weih
	 * @createTime : 2016年6月7日 下午5:26:08
	 */
    List<ManageRefundBean> getRefundInfo(Map<String, Object> params);
    
    /**
     * 所有支付宝未退款成功记录
     * @param payType
     * @param source
     * @return
     *
     * @author : weih
     * @createTime : 2016年6月7日 下午6:33:07
     */
    List<ManageRefundBean> getAliRefundInfo(int source);
}
