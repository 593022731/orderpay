package com.wei.orderpay.dao.order;

import com.wei.orderpay.bean.user.ExtractAmountLogBean;

import java.util.List;
import java.util.Map;

/** 
 * @description : 提现记录单表操作 
 * @Function:  
 * @author lizhi  
 * @createTime : 2016年4月13日 下午3:18:49
 */
public interface ExtractAmountLogCommonDao {
    
    /**
     * 提现记录的保存
     * @description :
     * @author lizhi  
     * @createTime : 2016年4月13日 下午3:19:37
     */
    public int saveExtractAmountLog(ExtractAmountLogBean extractamountlog);
    
    /**
     * 提现成功
     * @param id
     * @param status
     * @param source
     * @return
     *
     * @author : weih
     * @createTime : 2016年6月1日 下午12:30:48
     */
    int extractamountSuccess(int id,int source);
    
    /**
     * 提现失败
     * @param id
     * @param source
     * @return
     *
     * @author : weih
     * @createTime : 2016年6月8日 下午3:43:33
     */
    int extractamountFail(int id,int source);

	/**
	 * 获取提现记录
	 * @param exBean
	 * @return
	 * @author tankai
	 * @createTime : 2016年5月19日 上午11:28:20
	 * @version : 1.4
	 */
	public ExtractAmountLogBean getExtractAmountLogDetail(
			ExtractAmountLogBean exBean);

	/**
	 * 后台提现管理查询
	 * @param guidList
	 * @param tradeNo
	 * @param recordCode
	 * @param status
	 * @param startDT
	 * @param endDT
	 * @param bankcardAccountName
	 * @param bankcardNo
	 * @param source
	 * @param pageNo
	 * @param pageSize
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月8日 下午2:52:31
	 */
	public List<Map<String,Object>> getExtractAmountList(List<Integer> guidList,String tradeNo,String recordCode,String status,String startDT,String endDT,String bankcardAccountName,int bankcardNo,int source,int pageNo,int pageSize);
    
}
