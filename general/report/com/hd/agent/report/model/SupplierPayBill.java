package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierPayBill implements Serializable {
    private static final long serialVersionUID = -244860337760326720L;

    /**
     * 单据编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 单据类型
     */
    private String billtype;

    /**
     * 单据类型名称
     */
    private String billtypename;

    /**
     * 供应商编码
     */
    private String supplierid;

    /**
     * 供应商名称
     */
    private String suppliername;

    /**
     * 采购部门
     */
    private String buydeptid;

    /**
     * 采购部门名称
     */
    private String buydeptname;

    /**
     * 采购员
     */
    private String buyuserid;

    /**
     * 采购员名称
     */
    private String buyusername;

    /**
     * 采购区域
     */
    private String buyarea;

    /**
     * 采购区域名称
     */
    private String buyareaname;

    /**
     * 期初金额
     */
    private BigDecimal initbuyamount;

    /**
     * 采购金额
     */
    private BigDecimal payableamount;

    /**
     * 期初采购金额
     */
    private BigDecimal initpayableamount;

    /**
     * 付款金额
     */
    private BigDecimal payamount;

    /**
     * 期初付款金额
     */
    private BigDecimal initpayamount;

    /**
     * 冲差金额
     */
    private BigDecimal pushbalanceamount;

    /**
     * 期初冲茶金额
     */
    private BigDecimal initpushbalanceamount;

    /**
     * 余额
     */
    private BigDecimal balanceamount;

    /**
     * 备注
     */
    private String remark;

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getBuydeptid() {
        return buydeptid;
    }

    public void setBuydeptid(String buydeptid) {
        this.buydeptid = buydeptid;
    }

    public String getBuydeptname() {
        return buydeptname;
    }

    public void setBuydeptname(String buydeptname) {
        this.buydeptname = buydeptname;
    }

    public BigDecimal getPayableamount() {
        return payableamount;
    }

    public void setPayableamount(BigDecimal payableamount) {
        this.payableamount = payableamount;
    }

    public BigDecimal getPayamount() {
        return payamount;
    }

    public void setPayamount(BigDecimal payamount) {
        this.payamount = payamount;
    }

    public BigDecimal getPushbalanceamount() {
        return pushbalanceamount;
    }

    public void setPushbalanceamount(BigDecimal pushbalanceamount) {
        this.pushbalanceamount = pushbalanceamount;
    }

    public BigDecimal getBalanceamount() {
        return balanceamount;
    }

    public void setBalanceamount(BigDecimal balanceamount) {
        this.balanceamount = balanceamount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate;
    }

    public String getBuyuserid() {
        return buyuserid;
    }

    public void setBuyuserid(String buyuserid) {
        this.buyuserid = buyuserid;
    }

    public String getBuyusername() {
        return buyusername;
    }

    public void setBuyusername(String buyusername) {
        this.buyusername = buyusername;
    }

    public String getBuyarea() {
        return buyarea;
    }

    public void setBuyarea(String buyarea) {
        this.buyarea = buyarea;
    }

    public String getBuyareaname() {
        return buyareaname;
    }

    public void setBuyareaname(String buyareaname) {
        this.buyareaname = buyareaname;
    }

    public String getBilltype() {
        return billtype;
    }

    public void setBilltype(String billtype) {
        this.billtype = billtype;
    }

    public String getBilltypename() {
        return billtypename;
    }

    public void setBilltypename(String billtypename) {
        this.billtypename = billtypename;
    }

    public BigDecimal getInitbuyamount() {
        return initbuyamount;
    }

    public void setInitbuyamount(BigDecimal initbuyamount) {
        this.initbuyamount = initbuyamount;
    }

    public BigDecimal getInitpayableamount() {
        return initpayableamount;
    }

    public void setInitpayableamount(BigDecimal initpayableamount) {
        this.initpayableamount = initpayableamount;
    }

    public BigDecimal getInitpayamount() {
        return initpayamount;
    }

    public void setInitpayamount(BigDecimal initpayamount) {
        this.initpayamount = initpayamount;
    }

    public BigDecimal getInitpushbalanceamount() {
        return initpushbalanceamount;
    }

    public void setInitpushbalanceamount(BigDecimal initpushbalanceamount) {
        this.initpushbalanceamount = initpushbalanceamount;
    }
}
