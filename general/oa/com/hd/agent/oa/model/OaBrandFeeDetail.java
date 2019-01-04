package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaBrandFeeDetail implements Serializable {
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
     * 申请事由
     */
    private String reason;

    /**
     * 付款金额
     */
    private BigDecimal factoryamount;

    /**
     * 客户编码
     */
    private String customerid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 客户名称
     */
    private String customername;

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
}