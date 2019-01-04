/**
 * @(#)SupplierPaymentsInvoiceReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-1-26 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class SupplierPaymentsInvoiceReport implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -6689139691090966704L;

	/**
     * 编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;
    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人姓名
     */
    private String addusername;

    /**
     * 制单人部门编号
     */
    private String adddeptid;

    /**
     * 制单人部门名称
     */
    private String adddeptname;
    

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 审核时间
     */
    private Date audittime; 
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
    private String buyuser;
    
    /**
     * 采购员名称
     */
    private String buyusername;

    /**
     * 供应商编号
     */
    private String supplierid;
    
    /**
     * 供应商名称
     */
    private String suppliername;

    /**
     * 发票类型1增值税2普通3其他
     */
    private String invoicetype;
    /**
     * 发票号
     */
    private String invoiceno;
    /**
     * 发票代码
     */
    private String invoicecode;
    /**
     * 应付日期
     */
    private String paydate;

    /**
     * 是否已核销完成0否1是
     */
    private String iswriteoff;

    /**
     * 核销日期
     */
    private String writeoffdate;

    /**
     * 含税总金额
     */
    private BigDecimal taxamount;

    /**
     * 无税总金额
     */
    private BigDecimal notaxamount;

    /**
     * 应付总金额
     */
    private BigDecimal invoiceamount;

    /**
     * 核销总金额
     */
    private BigDecimal writeoffamount;
    
    /**
     * 尾差金额
     */
    private BigDecimal tailamount;

    /**
     * 来源编号
     */
    private String sourceid;
    /**
     * 小计列
     */
    private String columnflag;
    /**
     * 冲差金额
     */
    private BigDecimal pushamount;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
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

	public String getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getInvoicecode() {
		return invoicecode;
	}

	public void setInvoicecode(String invoicecode) {
		this.invoicecode = invoicecode;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public String getIswriteoff() {
		return iswriteoff;
	}

	public void setIswriteoff(String iswriteoff) {
		this.iswriteoff = iswriteoff;
	}

	public String getWriteoffdate() {
		return writeoffdate;
	}

	public void setWriteoffdate(String writeoffdate) {
		this.writeoffdate = writeoffdate;
	}

	public BigDecimal getTaxamount() {
		return taxamount;
	}

	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}

	public BigDecimal getNotaxamount() {
		return notaxamount;
	}

	public void setNotaxamount(BigDecimal notaxamount) {
		this.notaxamount = notaxamount;
	}

	public BigDecimal getInvoiceamount() {
		return invoiceamount;
	}

	public void setInvoiceamount(BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}

	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}

	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}

	public BigDecimal getTailamount() {
		return tailamount;
	}

	public void setTailamount(BigDecimal tailamount) {
		this.tailamount = tailamount;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
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

	public String getBuyuser() {
		return buyuser;
	}

	public void setBuyuser(String buyuser) {
		this.buyuser = buyuser;
	}

	public String getBuyusername() {
		return buyusername;
	}

	public void setBuyusername(String buyusername) {
		this.buyusername = buyusername;
	}

	public String getColumnflag() {
		return columnflag;
	}

	public void setColumnflag(String columnflag) {
		this.columnflag = columnflag;
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

	public String getAdddeptid() {
		return adddeptid;
	}

	public void setAdddeptid(String adddeptid) {
		this.adddeptid = adddeptid;
	}

	public String getAdddeptname() {
		return adddeptname;
	}

	public void setAdddeptname(String adddeptname) {
		this.adddeptname = adddeptname;
	}

	public BigDecimal getPushamount() {
		return pushamount;
	}

	public void setPushamount(BigDecimal pushamount) {
		this.pushamount = pushamount;
	}
}

