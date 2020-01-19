package com.wei.orderpay.dao.user;

import com.wei.orderpay.bean.user.BankcardBean;

import java.util.List;

/** 
 * @description :银行卡单表操作  
 * @Function:  
 * @author lizhi  
 * @createTime : 2016年4月13日 下午3:12:29
 */
public interface BankcardCommonDao {
    
    /**
     * 银行卡信息保存
     * @description :
     * @author lizhi  
     * @createTime : 2016年4月13日 下午3:13:02
     */
    public int saveBankcard(BankcardBean bankCard);

    /**
     * 获取用户银行卡列表
     * @param guid
     * @param ordinal
     * @return
     *
     * @author : liuquan
     * @createTime : 2016年4月28日 下午12:13:51
     */
	public List<BankcardBean> getBankcardList(int guid, int source);

	/**
	 * 逻辑删除银行卡
	 * @param bankCardID
	 * @param source
	 * @return
	 * @author tankai
	 * @createTime : 2016年5月16日 下午6:33:58
	 * @version : 1.4
	 */
	public int deleteBandcard(int guid,int bankCardID, int source);

	/**
	 * 详情
	 * @param guid
	 * @param bankCardID
	 * @param source
	 * @return
	 */
	BankcardBean getBankcardDetail(int guid,int bankCardID, int source);

	/**
	 * 根据开户名或银行卡号查uid列表
	 * @param name
	 * @param bankcardNo
	 * @param source
	 * @return
	 *
	 * @author : liuquan
	 * @createTime : 2016年6月1日 上午11:03:21
	 */
	public List<Integer> getGuidListByBankcardNoOrName(String name,int bankcardNo, int source);
}
