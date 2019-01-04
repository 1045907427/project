package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeliveryCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 序号
     */
    private int index;

    /**
     * 配送单编号
     */
    private String deliveryid;

    /**
     * 客户编号
     */
    private String customerid;
    
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 单据数
     */
    private Integer billnums;

    /**
     * 销售额
     */
    private BigDecimal salesamount;

    /**
     * 商品箱数
     */
    private BigDecimal auxnum;

    /**
     * 商品件数
     */
    private BigDecimal auxremainder;
    
    /**
     * 箱数
     */
    private BigDecimal boxnum;
    
    /**
     * 体积
     */
    private BigDecimal volume;
    
    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 收款金额
     */
    private BigDecimal collectionamount;

    /**
     * 是否带回回单1是0否
     */
    private String isreceipt;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 是否关联发货1是0否
     */
    private String issaleout;
    
    /**
     * 排序
     */
    private Integer seq;
    
    /**
     * 是否修改
     */
    private String isedit;

    /**
     * 单据类型1发货单2代配送3调拨单
     */
    private String billtype;
    
    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 配送单编号
     */
    public String getDeliveryid() {
        return deliveryid;
    }

    /**
     * @param deliveryid 
	 *            配送单编号
     */
    public void setDeliveryid(String deliveryid) {
        this.deliveryid = deliveryid;
    }

    public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	/**
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    /**
     * @return 单据数
     */
    public Integer getBillnums() {
        return billnums;
    }

    /**
     * @param billnums 
	 *            单据数
     */
    public void setBillnums(Integer billnums) {
        this.billnums = billnums;
    }

    /**
     * @return 销售额
     */
    public BigDecimal getSalesamount() {
        return salesamount;
    }

    /**
     * @param salesamount 
	 *            销售额
     */
    public void setSalesamount(BigDecimal salesamount) {
        this.salesamount = salesamount;
    }

    /**
     * @return 商品箱数
     */
    public BigDecimal getAuxnum() {
        return auxnum;
    }

    /**
     * @param auxnum 
	 *            商品箱数
     */
    public void setAuxnum(BigDecimal auxnum) {
        this.auxnum = auxnum;
    }

    /**
     * @return 商品件数
     */
    public BigDecimal getAuxremainder() {
        return auxremainder;
    }

    /**
     * @param auxremainder 
	 *            商品件数
     */
    public void setAuxremainder(BigDecimal auxremainder) {
        this.auxremainder = auxremainder;
    }

    /**
     * @return 收款金额
     */
    public BigDecimal getCollectionamount() {
        return collectionamount;
    }

    /**
     * @param collectionamount 
	 *            收款金额
     */
    public void setCollectionamount(BigDecimal collectionamount) {
        this.collectionamount = collectionamount;
    }

    /**
     * @return 是否带回回单1是0否
     */
    public String getIsreceipt() {
        return isreceipt;
    }

    /**
     * @param isreceipt 
	 *            是否带回回单1是0否
     */
    public void setIsreceipt(String isreceipt) {
        this.isreceipt = isreceipt;
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

	public BigDecimal getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}

	public String getIssaleout() {
		return issaleout;
	}

	public void setIssaleout(String issaleout) {
		this.issaleout = issaleout;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getIsedit() {
		return isedit;
	}

	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }
}