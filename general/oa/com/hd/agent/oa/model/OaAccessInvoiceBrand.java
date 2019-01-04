package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaAccessInvoiceBrand implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 付款金额
     */
    private BigDecimal payamount;

    /**
     * 相关单据编号
     */
    private String relateid;

    public Integer getId() {
        return id;
    }

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
     * @return 付款金额
     */
    public BigDecimal getPayamount() {
        return payamount;
    }

    /**
     * @param payamount 
	 *            付款金额
     */
    public void setPayamount(BigDecimal payamount) {
        this.payamount = payamount;
    }

    /**
     * @return 相关单据编号
     */
    public String getRelateid() {
        return relateid;
    }

    /**
     * @param relateid 
	 *            相关单据编号
     */
    public void setRelateid(String relateid) {
        this.relateid = relateid == null ? null : relateid.trim();
    }
}