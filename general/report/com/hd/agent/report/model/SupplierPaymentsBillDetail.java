/**
 * @(#)SupplierPaymentsBillDetail.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-12-14 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class SupplierPaymentsBillDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5718496103446597879L;
	/**
	 * 单据编号
	 */
	private String id;
	/**
	 * 订单号
	 */
	private String buyorderid;
	/**
	 * 单据日期
	 */
	private String businessdate;
	/**
	 * 单据类型
	 */
	private String billtype;
	/**
	 * 类型名称
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
	 * 进货金额
	 */
	private BigDecimal buyamount;
	/**
	 * 开票金额
	 */
	private BigDecimal invoiceamount;
	/**
	 * 未票金额
	 */
	private BigDecimal uninvoiceamount;
	/**
	 * 开票日期
	 */
	private String invoicedate;
	/**
	 * 开票次数
	 */
	private int invoicetimes;
	/**
	 * 已付金额
	 */
	private BigDecimal payamount;
	/**
	 * 未付金额
	 */
	private BigDecimal unpayamount;
	/**
	 * 添加人编号
	 */
	private String adduserid;
	/**
	 * 添加人姓名
	 */
	private String addusername;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBuyorderid() {
		return buyorderid;
	}
	public void setBuyorderid(String buyorderid) {
		this.buyorderid = buyorderid;
	}
	public String getBusinessdate() {
		return businessdate;
	}
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
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
	public BigDecimal getInvoiceamount() {
		return invoiceamount;
	}
	public void setInvoiceamount(BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}
	public BigDecimal getUninvoiceamount() {
		return uninvoiceamount;
	}
	public void setUninvoiceamount(BigDecimal uninvoiceamount) {
		this.uninvoiceamount = uninvoiceamount;
	}
	public String getInvoicedate() {
		return invoicedate;
	}
	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
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
	public String getAdduserid() {
		return adduserid;
	}
	public void setAdduserid(String adduserid) {
		this.adduserid = adduserid;
	}
	public String getAddusername() {
		return addusername;
	}
	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}
	public int getInvoicetimes() {
		return invoicetimes;
	}
	public void setInvoicetimes(int invoicetimes) {
		this.invoicetimes = invoicetimes;
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
}

