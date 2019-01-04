/**
 * @(#)SupplierPayments.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 供应商应付款
 * @author chenwei
 */
public class SupplierPayments implements Serializable {

	private static final long serialVersionUID = -8251707629646628066L;

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
	 * 供应商编号
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
	 * 期初未核销金额
	 */
	private BigDecimal initunpayamount;
	/**
	 * 进货金额
	 */
	private BigDecimal buyamount;
	/**
	 * 退货金额
	 */
	private BigDecimal returnamount;
	/**
	 * 实际应付金额
	 */
	private BigDecimal realpayamount;
	/**
	 * 开票金额
	 */
	private BigDecimal invoiceamount;
	/**
	 * 预付金额
	 */
	private BigDecimal prepayamount;
	/**
	 * 已付金额
	 */
	private BigDecimal payamount;
	/**
	 * 未付金额
	 */
	private BigDecimal unpayamount;
	/**
	 * 预付余额
	 */
	private	BigDecimal prepaybalance;
	/**
	 * 应付金额
	 */
	private BigDecimal payableamount;

    /**
     * 冲差金额
     */
    private BigDecimal pushbalanceamount;

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
	public BigDecimal getBuyamount() {
		return buyamount;
	}
	public void setBuyamount(BigDecimal buyamount) {
		this.buyamount = buyamount;
	}
	public BigDecimal getReturnamount() {
		return returnamount;
	}
	public void setReturnamount(BigDecimal returnamount) {
		this.returnamount = returnamount;
	}
	public BigDecimal getRealpayamount() {
		return realpayamount;
	}
	public void setRealpayamount(BigDecimal realpayamount) {
		this.realpayamount = realpayamount;
	}
	public BigDecimal getPayamount() {
		return payamount;
	}
	public void setPayamount(BigDecimal payamount) {
		this.payamount = payamount;
	}
	public BigDecimal getUnpayamount() {
		return unpayamount;
	}
	public void setUnpayamount(BigDecimal unpayamount) {
		this.unpayamount = unpayamount;
	}
	public BigDecimal getInvoiceamount() {
		return invoiceamount;
	}
	public void setInvoiceamount(BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}
	public BigDecimal getPrepayamount() {
		return prepayamount;
	}
	public void setPrepayamount(BigDecimal prepayamount) {
		this.prepayamount = prepayamount;
	}
	public BigDecimal getPrepaybalance() {
		return prepaybalance;
	}
	public void setPrepaybalance(BigDecimal prepaybalance) {
		this.prepaybalance = prepaybalance;
	}
	public BigDecimal getPayableamount() {
		return payableamount;
	}
	public void setPayableamount(BigDecimal payableamount) {
		this.payableamount = payableamount;
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

    public BigDecimal getPushbalanceamount() {
        return pushbalanceamount;
    }

    public void setPushbalanceamount(BigDecimal pushbalanceamount) {
        this.pushbalanceamount = pushbalanceamount;
    }

	public BigDecimal getInitunpayamount() {
		return initunpayamount;
	}

	public void setInitunpayamount(BigDecimal initunpayamount) {
		this.initunpayamount = initunpayamount;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}

