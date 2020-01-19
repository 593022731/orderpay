package com.wei.orderpay.manage.dao.order;

import com.wei.orderpay.manage.bean.order.ServiceRemit;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author : wang.tao
 * @createTime : 2016年6月12日 上午10:20:56
 * @description :业务订单
 *
 */
public interface ManageServiceRemitCommonDao {
    
	/**
	 * 根据id查询业务汇款表对象
	 * @param id
	 * @param source
	 * @return
	 * @author lizhi
	 * @creatTime 2016年6月16日下午4:51:23
	 */
	public ServiceRemit getServiceRemitByID(int id , int source);
	
    /**
     * 通过订单ID查询出业务订单账号添加情况
     * @param orderID
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年6月12日 上午10:23:23
     * @version : web1.4.3
     */
    List<ServiceRemit> getServiceRemitListByOrderID(int orderID ,int source) ;
    
    /**
     * 业务订单
     * @param item
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年6月12日 上午10:38:59
     * @version : web1.4.3
     */
    int saveServiceRemit(ServiceRemit item);

    /**
     * 通过条件查询是否有商品没有添加配件商账户
     * @param params
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年6月13日 下午4:12:59
     * @version : web1.4.3
     */
    int getNotAddCount(Map<String, Object> params);
    
    /**
     * 更新账户状态
     * @param params
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年6月13日 下午4:12:44
     * @version : web1.4.3
     */
    int updateAccountStatus(Map<String, Object> params) ;
    
    /**
     * 根据条件查询业务汇款表
     * @param params
     * @return
     *
     * @author : wang.tao
     * @createTime : 2016年6月14日 下午8:43:52
     * @version : web1.4.3
     */
    List<ServiceRemit> getServiceRemitListByParas(Map<String, Object> params) ;
    
    /**
     * 更新业务汇款表状态 为汇款成功
     * @param id
     * @param source
     * @return
     * @author lizhi
     * @creatTime 2016年6月16日下午5:24:27
     */
    public int updateServiceRemit4Success(int id , int source);
    
    /**
     * 更新业务汇款表状态 为汇款失败
     * @param id
     * @param source
     * @author lizhi
     * @creatTime 2016年6月16日下午5:45:54
     */
	public int  updateServiceRemit4Fail(int id, int source);
    
    
    
    
}
