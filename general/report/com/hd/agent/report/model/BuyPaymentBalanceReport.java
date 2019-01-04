/**
 * @(#)BuyPaymentBalanceReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-12-6 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购付款差额统计报表
 * 
 * @author zhanghonghui
 */
public class BuyPaymentBalanceReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -840966199675920563L;
	/**
     * 编号
     */
    private String id;
    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 采购员
     */
    private String buyuserid;
    /**
     * 采购员名称
     */
    private String buyusername;
    /**
     * 采购部门
     */
    private String buydeptid;
    /**
     * 采购门名称
     */
    private String buydeptname;

    /**
     * 供应商编号
     */
    private String supplierid;

    /**
     * 供应商名称
     */
    private String suppliername;

    /**
     * 进货金额
     */
    private BigDecimal arrivalamount;
    /**
     * 开票金额
     */
    private BigDecimal invoiceamount;
    /**
     * 付款差额
     */
    private BigDecimal paybalance;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
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
	public BigDecimal getArrivalamount() {
		return arrivalamount;
	}
	public void setArrivalamount(BigDecimal arrivalamount) {
		this.arrivalamount = arrivalamount;
	}
	public BigDecimal getInvoiceamount() {
		return invoiceamount;
	}
	public void setInvoiceamount(BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}
	public BigDecimal getPaybalance() {
		return paybalance;
	}
	public void setPaybalance(BigDecimal paybalance) {
		this.paybalance = paybalance;
	}
	public String getBuyusername() {
		return buyusername;
	}
	public void setBuyusername(String buyusername) {
		this.buyusername = buyusername;
	}
	public String getBuydeptname() {
		return buydeptname;
	}
	public void setBuydeptname(String buydeptname) {
		this.buydeptname = buydeptname;
	}
	public String getBrandid() {
		return brandid;
	}
	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}
	public String getBuyuserid() {
		return buyuserid;
	}
	public void setBuyuserid(String buyuserid) {
		this.buyuserid = buyuserid;
	}
	public String getBuydeptid() {
		return buydeptid;
	}
	public void setBuydeptid(String buydeptid) {
		this.buydeptid = buydeptid;
	}
}