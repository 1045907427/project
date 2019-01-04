/**
 * @(#)CustomerReceivableDynamic.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 20, 2013 chenwei 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 客户应收款动态表
 * @author chenwei
 */
public class CustomerReceivableDynamic implements Serializable{
	/**
	 * 单据类型 1发货单 2退货入库单 3冲差单
	 */
	private String billtype;
	
	/**
	 * 单据类型名称
	 */
	private String billtypename;
	/**
     * 编号
     */
    private String id;
    /**
     * 订单日期
     */
    private String orderdate;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 上级客户（总店）
     */
    private String pcustomerid;
    /**
     * 总店名称
     */
    private String pcustomername;
    /**
     * 销售区域
     */
    private String salesarea;
    /**
     * 销售区域名称
     */
    private String salesareaname;
    /**
     * 销售部门
     */
    private String salesdept;
    /**
     * 销售部门名称
     */
    private String salesdeptname;
    /**
     * 客户业务员
     */
    private String salesuser;
    /**
     * 客户业务员姓名
     */
    private String salesusername;
    /**
     * 销售订单编号
     */
    private String saleorderid;

    /**
     * 回单验收日期
     */
    private String checkdate;
    /**
     * 抽单日期
     */
    private String invoicedate;
    /**
     * 核销日期
     */
    private String writeoffdate;

	/**
	 * 应收日期
	 */
	private String duefromdate;

	/**
	 * 开票日期
	 */
	private String invoicebilldate;
    /**
     * 回单编号
     */
    private String receiptid;

	/**
	 * 回单备注
	 */
	private String receiptremark;
    /**
     * 发票号
     */
    private String invoiceid;
    /**
     * 发货金额
     */
    private BigDecimal initsendamount; 
    /**
     * 发货成本金额
     */
    private BigDecimal initsendcostamount;
    /**
     * 发货出库金额
     */
    private BigDecimal sendamount;
    /**
     * 发货出库未税金额
     */
    private BigDecimal sendnotaxamount;
    /**
     * 发货出库成本金额
     */
    private BigDecimal sendcostamount;
    /**
     * 验收金额
     */
    private BigDecimal checkamount;
    /**
     * 验收未税金额
     */
    private BigDecimal checknotaxamount;
    /**
     * 申请金额
     */
    private BigDecimal invoiceamount;

    /**
     * 申请未税金额
     */
    private BigDecimal invoicenotaxamount;
    /**
     * 未申请金额
     */
    private BigDecimal uninvoiceamount;
    /**
     * 未申请未税金额
     */
    private BigDecimal uninvoicenotaxamount;

	/**
	 * 开票金额
	 */
	private BigDecimal invoicebillamount;

	/**
	 * 开票未税金额
	 */
	private BigDecimal invoicebillnotaxamount;

	/**
	 * 未开票金额
	 */
	private BigDecimal uninvoicebillamount;
	/**
	 * 未开票未税金额
	 */
	private BigDecimal uninvoicebillnotaxamount;
    /**
     * 核销金额
     */
    private BigDecimal writeoffamount;
    /**
     * 核销未税金额
     */
    private BigDecimal writeoffnotaxamount;
    /**
     * 未核销金额
     */
    private BigDecimal unwriteoffamount;
    /**
     * 未核销未税金额
     */
    private BigDecimal unwriteoffnotaxamount;

    private String sourceid ;

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
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAudittime() {
		return audittime;
	}
	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getPcustomerid() {
		return pcustomerid;
	}
	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}
	public String getPcustomername() {
		return pcustomername;
	}
	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}
	public String getSalesarea() {
		return salesarea;
	}
	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}
	public String getSalesareaname() {
		return salesareaname;
	}
	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}
	public String getSalesdept() {
		return salesdept;
	}
	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}
	public String getSalesdeptname() {
		return salesdeptname;
	}
	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
	}
	public String getSalesuser() {
		return salesuser;
	}
	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}
	public String getSalesusername() {
		return salesusername;
	}
	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
	}
	public String getSaleorderid() {
		return saleorderid;
	}
	public void setSaleorderid(String saleorderid) {
		this.saleorderid = saleorderid;
	}
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getInvoicedate() {
		return invoicedate;
	}
	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}
	public String getWriteoffdate() {
		return writeoffdate;
	}
	public void setWriteoffdate(String writeoffdate) {
		this.writeoffdate = writeoffdate;
	}
	public String getReceiptid() {
		return receiptid;
	}
	public void setReceiptid(String receiptid) {
		this.receiptid = receiptid;
	}
	public String getInvoiceid() {
		return invoiceid;
	}
	public void setInvoiceid(String invoiceid) {
		this.invoiceid = invoiceid;
	}
	public BigDecimal getInitsendamount() {
		return initsendamount;
	}
	public void setInitsendamount(BigDecimal initsendamount) {
		this.initsendamount = initsendamount;
	}
	public BigDecimal getInitsendcostamount() {
		return initsendcostamount;
	}
	public void setInitsendcostamount(BigDecimal initsendcostamount) {
		this.initsendcostamount = initsendcostamount;
	}
	public BigDecimal getSendamount() {
		return sendamount;
	}
	public void setSendamount(BigDecimal sendamount) {
		this.sendamount = sendamount;
	}
	public BigDecimal getSendnotaxamount() {
		return sendnotaxamount;
	}
	public void setSendnotaxamount(BigDecimal sendnotaxamount) {
		this.sendnotaxamount = sendnotaxamount;
	}
	public BigDecimal getSendcostamount() {
		return sendcostamount;
	}
	public void setSendcostamount(BigDecimal sendcostamount) {
		this.sendcostamount = sendcostamount;
	}
	public BigDecimal getCheckamount() {
		return checkamount;
	}
	public void setCheckamount(BigDecimal checkamount) {
		this.checkamount = checkamount;
	}
	public BigDecimal getChecknotaxamount() {
		return checknotaxamount;
	}
	public void setChecknotaxamount(BigDecimal checknotaxamount) {
		this.checknotaxamount = checknotaxamount;
	}
	public BigDecimal getInvoiceamount() {
		return invoiceamount;
	}
	public void setInvoiceamount(BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}
	public BigDecimal getInvoicenotaxamount() {
		return invoicenotaxamount;
	}
	public void setInvoicenotaxamount(BigDecimal invoicenotaxamount) {
		this.invoicenotaxamount = invoicenotaxamount;
	}
	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}
	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}
	public BigDecimal getWriteoffnotaxamount() {
		return writeoffnotaxamount;
	}
	public void setWriteoffnotaxamount(BigDecimal writeoffnotaxamount) {
		this.writeoffnotaxamount = writeoffnotaxamount;
	}
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}
	public BigDecimal getUninvoiceamount() {
		return uninvoiceamount;
	}
	public void setUninvoiceamount(BigDecimal uninvoiceamount) {
		this.uninvoiceamount = uninvoiceamount;
	}
	public BigDecimal getUninvoicenotaxamount() {
		return uninvoicenotaxamount;
	}
	public void setUninvoicenotaxamount(BigDecimal uninvoicenotaxamount) {
		this.uninvoicenotaxamount = uninvoicenotaxamount;
	}
	public BigDecimal getUnwriteoffamount() {
		return unwriteoffamount;
	}
	public void setUnwriteoffamount(BigDecimal unwriteoffamount) {
		this.unwriteoffamount = unwriteoffamount;
	}
	public BigDecimal getUnwriteoffnotaxamount() {
		return unwriteoffnotaxamount;
	}
	public void setUnwriteoffnotaxamount(BigDecimal unwriteoffnotaxamount) {
		this.unwriteoffnotaxamount = unwriteoffnotaxamount;
	}
	public String getBilltypename() {
		return billtypename;
	}
	public void setBilltypename(String billtypename) {
		this.billtypename = billtypename;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getDuefromdate() {
		return duefromdate;
	}

	public void setDuefromdate(String duefromdate) {
		this.duefromdate = duefromdate;
	}

	public BigDecimal getInvoicebillamount() {
		return invoicebillamount;
	}

	public void setInvoicebillamount(BigDecimal invoicebillamount) {
		this.invoicebillamount = invoicebillamount;
	}

	public BigDecimal getInvoicebillnotaxamount() {
		return invoicebillnotaxamount;
	}

	public void setInvoicebillnotaxamount(BigDecimal invoicebillnotaxamount) {
		this.invoicebillnotaxamount = invoicebillnotaxamount;
	}

	public BigDecimal getUninvoicebillamount() {
		return uninvoicebillamount;
	}

	public void setUninvoicebillamount(BigDecimal uninvoicebillamount) {
		this.uninvoicebillamount = uninvoicebillamount;
	}

	public BigDecimal getUninvoicebillnotaxamount() {
		return uninvoicebillnotaxamount;
	}

	public void setUninvoicebillnotaxamount(BigDecimal uninvoicebillnotaxamount) {
		this.uninvoicebillnotaxamount = uninvoicebillnotaxamount;
	}

	public String getInvoicebilldate() {
		return invoicebilldate;
	}

	public void setInvoicebilldate(String invoicebilldate) {
		this.invoicebilldate = invoicebilldate;
	}

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

	public String getReceiptremark() {
		return receiptremark;
	}

	public void setReceiptremark(String receiptremark) {
		this.receiptremark = receiptremark;
	}
}

