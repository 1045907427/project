package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户品牌结算方式
 * @author chenwei
 */
public class CustomerBrandSettletype implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 结算方式
     */
    private String settletype;

    /**
     * 结算方式名称
     */
    private String settletypename;
    
    /**
     * 初始结算方式
     */
    private String initsettletype;

    /**
     * 每月结算日
     */
    private String settleday;

    /**
     * 初始结算方式
     */
    private String initsettleday;

    /**
     * 备注
     */
    private String remark;
    /**
     * 添加时间
     */
    private Date addtime;
    
    /**
     * 是否修改
     */
    private String isedit;

    /**
     * 品牌字符串集合
     */
    private String brandids;
    
    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(Integer id) {
        this.id = id;
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
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 品牌编号
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌编号
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsedit() {
		return null != isedit ? isedit : "0";
	}

	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}

    public String getSettletype() {
        return settletype;
    }

    public void setSettletype(String settletype) {
        this.settletype = settletype;
    }

    public String getSettletypename() {
        return settletypename;
    }

    public void setSettletypename(String settletypename) {
        this.settletypename = settletypename;
    }

    public String getInitsettletype() {
        return initsettletype;
    }

    public void setInitsettletype(String initsettletype) {
        this.initsettletype = initsettletype;
    }

    public String getSettleday() {
        return settleday;
    }

    public void setSettleday(String settleday) {
        this.settleday = settleday;
    }

    public String getInitsettleday() {
        return initsettleday;
    }

    public void setInitsettleday(String initsettleday) {
        this.initsettleday = initsettleday;
    }

    public String getBrandids() {
        return brandids;
    }

    public void setBrandids(String brandids) {
        this.brandids = brandids;
    }
}