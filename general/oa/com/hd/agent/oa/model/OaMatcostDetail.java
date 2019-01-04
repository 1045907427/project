package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaMatcostDetail implements Serializable {
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
     * 客户编码
     */
    private String customerid;

    /**
     * 申请事由
     */
    private String reason;

    /**
     * 品牌编码
     */
    private String brandid;

    /**
     * 费用科目
     */
    private String expensesort;

    /**
     * 工厂投入
     */
    private BigDecimal factoryamount;

    /**
     * 自理金额
     */
    private BigDecimal myamount;

    /**
     * 费用金额
     */
    private BigDecimal feeamount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 客户名称
     */
    private String customername;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 费用科目名称
     */
    private String expensesortname;

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
     * @return 客户编码
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid
     *            客户编码
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
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
     * @return 品牌编码
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid
     *            品牌编码
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 费用科目
     */
    public String getExpensesort() {
        return expensesort;
    }

    /**
     * @param expensesort
     *            费用科目
     */
    public void setExpensesort(String expensesort) {
        this.expensesort = expensesort == null ? null : expensesort.trim();
    }

    /**
     * @return 工厂投入
     */
    public BigDecimal getFactoryamount() {
        return factoryamount;
    }

    /**
     * @param factoryamount
     *            工厂投入
     */
    public void setFactoryamount(BigDecimal factoryamount) {
        this.factoryamount = factoryamount;
    }

    /**
     * @return 自理金额
     */
    public BigDecimal getMyamount() {
        return myamount;
    }

    /**
     * @param myamount
     *            自理金额
     */
    public void setMyamount(BigDecimal myamount) {
        this.myamount = myamount;
    }

    /**
     * @return 费用金额
     */
    public BigDecimal getFeeamount() {
        return feeamount;
    }

    /**
     * @param feeamount
     *            费用金额
     */
    public void setFeeamount(BigDecimal feeamount) {
        this.feeamount = feeamount;
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

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getExpensesortname() {
        return expensesortname;
    }

    public void setExpensesortname(String expensesortname) {
        this.expensesortname = expensesortname;
    }
}