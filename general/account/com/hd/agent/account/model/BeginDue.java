package com.hd.agent.account.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BeginDue implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 状态名称
     */
    private String statusname;
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
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改人名称
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 审核人编号
     */
    private String audituserid;

    /**
     * 审核人名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 中止人编号
     */
    private String stopuserid;

    /**
     * 中止人名称
     */
    private String stopusername;

    /**
     * 中止时间
     */
    private Date stoptime;

    /**
     * 关闭时间
     */
    private Date closetime;

    /**
     * 打印次数
     */
    private Integer printtimes;

    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;

    /**
     * 供应商分类
     */
    private String suppliersort;

    /**
     * 采购区域
     */
    private String buyarea;

    /**
     * 采购部门
     */
    private String buydeptid;

    /**
     * 采购员
     */
    private String buyuserid;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 开票引用，1引用，0未引用,新增发票时使用
     */
    private String invoicerefer;
    /**
     * 是否开票1是0否
     */
    private String isinvoice;

    /**
     * 是否核销完成1核销完0未核销2核销中
     */
    private String iswriteoff;

    /**
     * 开票日期
     */
    private String invoicedate;

    /**
     * 核销日期
     */
    private String writeoffdate;

    /**
     * 核销人编号
     */
    private String writeoffuserid;

    /**
     * 核销人姓名
     */
    private String writeoffusername;

    /**
     * 开票金额
     */
    private BigDecimal invoiceamount;

    /**
     * 开票未税金额
     */
    private BigDecimal invoicenotaxamount;

    /**
     * 核销金额
     */
    private BigDecimal writeoffamount;

    /**
     * 核销未税金额
     */
    private BigDecimal writeoffnotaxamount;

    /**
     * 备注
     */
    private String remark;

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

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
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

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getModifyuserid() {
        return modifyuserid;
    }

    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid;
    }

    public String getModifyusername() {
        return modifyusername;
    }

    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getAudituserid() {
        return audituserid;
    }

    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid;
    }

    public String getAuditusername() {
        return auditusername;
    }

    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAudittime() {
        return audittime;
    }

    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    public String getStopuserid() {
        return stopuserid;
    }

    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid;
    }

    public String getStopusername() {
        return stopusername;
    }

    public void setStopusername(String stopusername) {
        this.stopusername = stopusername;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getStoptime() {
        return stoptime;
    }

    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getClosetime() {
        return closetime;
    }

    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    public Integer getPrinttimes() {
        return printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
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

    public String getSuppliersort() {
        return suppliersort;
    }

    public void setSuppliersort(String suppliersort) {
        this.suppliersort = suppliersort;
    }

    public String getBuyarea() {
        return buyarea;
    }

    public void setBuyarea(String buyarea) {
        this.buyarea = buyarea;
    }

    public String getBuydeptid() {
        return buydeptid;
    }

    public void setBuydeptid(String buydeptid) {
        this.buydeptid = buydeptid;
    }

    public String getBuyuserid() {
        return buyuserid;
    }

    public void setBuyuserid(String buyuserid) {
        this.buyuserid = buyuserid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIsinvoice() {
        return isinvoice;
    }

    public void setIsinvoice(String isinvoice) {
        this.isinvoice = isinvoice;
    }

    public String getIswriteoff() {
        return iswriteoff;
    }

    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff;
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

    public String getWriteoffuserid() {
        return writeoffuserid;
    }

    public void setWriteoffuserid(String writeoffuserid) {
        this.writeoffuserid = writeoffuserid;
    }

    public String getWriteoffusername() {
        return writeoffusername;
    }

    public void setWriteoffusername(String writeoffusername) {
        this.writeoffusername = writeoffusername;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInvoicerefer() {
        return invoicerefer;
    }

    public void setInvoicerefer(String invoicerefer) {
        this.invoicerefer = invoicerefer;
    }
}
