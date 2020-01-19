package com.wei.orderpay.manage.bean.order;

import com.wei.orderpay.manage.bean.common.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author : wang.tao
 * @createTime : 2016年4月13日 上午11:07:04
 * @version : 1.0
 * @description :申请退款实体类
 *
 */
public class RefundBean extends BaseBean implements Serializable{

    private static final long serialVersionUID = 8385718282466932360L;
    
    /**主键ID*/
    private int id ;
    
    /**订单ID*/
    private int orderID;

    /**申请者UID(买家UID)*/
    private int guid;
    
    /** 1:退款成功(通过查询第三方退款接口更新) */
    private int flag;

    /**退款金额*/
    private double amount;
    
    /**退货类型(1:退款不退货,2:退款又退货)*/
    private int refundType;
    
    /**收货状态(1:未收到货,2:已收到货)*/
    private int goodStatus;
    
    /**申请原因*/
    private String reason;
    
    /**凭证图片json url地址,最多4个图片*/
    private String voucherPics;
    
    /**凭证图片集合 前台使用*/
    private List<String> voucherPicsList;
    
	/**1:卖家同意退款;2:客服驳回退款申请*/
    private int agree;
    
    /**同意时间*/
    private String agreeDT;
    
    /**处理者(客服后台登录uid，客服可以驳回退款)*/
    private int customerID;
    
    /**处理时间*/
    private String customUpdateDT;
    
    /**客服驳回退款原因*/
    private String rejectRefundReason;
    
    /**创建时间*/
    private String createDT;
    
    /**修改时间*/
    private String updateDT;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public String getAgreeDT() {
        return agreeDT;
    }

    public void setAgreeDT(String agreeDT) {
        this.agreeDT = agreeDT;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomUpdateDT() {
        return customUpdateDT;
    }

    public void setCustomUpdateDT(String customUpdateDT) {
        this.customUpdateDT = customUpdateDT;
    }

    public String getCreateDT() {
        return createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }

    public String getUpdateDT() {
        return updateDT;
    }

    public void setUpdateDT(String updateDT) {
        this.updateDT = updateDT;
    }

	public int getRefundType() {
		return refundType;
	}

	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}

	public int getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(int goodStatus) {
		this.goodStatus = goodStatus;
	}

	public String getVoucherPics() {
		return voucherPics;
	}

	public void setVoucherPics(String voucherPics) {
		this.voucherPics = voucherPics;
	}
	
	public List<String> getVoucherPicsList() {
		return voucherPicsList;
	}

	public void setVoucherPicsList(List<String> voucherPicsList) {
		this.voucherPicsList = voucherPicsList;
	}
	
	public String getRejectRefundReason() {
		return rejectRefundReason;
	}

	public void setRejectRefundReason(String rejectRefundReason) {
		this.rejectRefundReason = rejectRefundReason;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
