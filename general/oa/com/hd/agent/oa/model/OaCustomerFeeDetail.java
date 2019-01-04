package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaCustomerFeeDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 品牌编号
     */
    private String supplierid;

    /**
     * 品牌编号
     */
    private String deptid;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 申请事由
     */
    private String reason;

    /**
     * 付款金额
     */
    private BigDecimal factoryamount;

    /**
     * 付款金额
     */
    private BigDecimal selfamount;

    /**
     * 品牌责任人
     */
    private String branduser;

    /**
     * 备注
     */
    private String remark;

    /**
     * 供应商名称
     */
    private String suppliername;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 部门名称
     */
    private String deptname;

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
     * @return 品牌编号
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            品牌编号
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    /**
     * @return 品牌编号
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            品牌编号
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
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
     * @return 申请事由
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason 
	 *            申请事由
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * @return 付款金额
     */
    public BigDecimal getFactoryamount() {
        return factoryamount;
    }

    /**
     * @param factoryamount 
	 *            付款金额
     */
    public void setFactoryamount(BigDecimal factoryamount) {
        this.factoryamount = factoryamount;
    }

    /**
     * @return 付款金额
     */
    public BigDecimal getSelfamount() {
        return selfamount;
    }

    /**
     * @param selfamount 
	 *            付款金额
     */
    public void setSelfamount(BigDecimal selfamount) {
        this.selfamount = selfamount;
    }

    /**
     * @return 品牌责任人
     */
    public String getBranduser() {
        return branduser;
    }

    /**
     * @param branduser 
	 *            品牌责任人
     */
    public void setBranduser(String branduser) {
        this.branduser = branduser == null ? null : branduser.trim();
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

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}