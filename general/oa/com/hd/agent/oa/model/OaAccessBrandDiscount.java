package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaAccessBrandDiscount implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * OA编号
     */
    private String oaid;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 折让金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联类型1客户应收款冲差单2代垫
     */
    private String relatetype;

    /**
     * 相关单据编号（关联生成的单据）
     */
    private String relateid;
    
    /**
     * 品牌名称
     */
    private String brandname;
    
    /**
     * 
     */
    private String brandname2;

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
     * @return 单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return OA编号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * @param oaid 
	 *            OA编号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
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
     * @return 折让金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            折让金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return 关联类型1客户应收款冲差单2代垫
     */
    public String getRelatetype() {
        return relatetype;
    }

    /**
     * @param relatetype 
	 *            关联类型1客户应收款冲差单2代垫
     */
    public void setRelatetype(String relatetype) {
        this.relatetype = relatetype == null ? null : relatetype.trim();
    }

    /**
     * @return 相关单据编号（关联生成的单据）
     */
    public String getRelateid() {
        return relateid;
    }

    /**
     * @param relateid 
	 *            相关单据编号（关联生成的单据）
     */
    public void setRelateid(String relateid) {
        this.relateid = relateid == null ? null : relateid.trim();
    }

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getBrandname2() {
		return brandname2;
	}

	public void setBrandname2(String brandname2) {
		this.brandname2 = brandname2;
	}
}