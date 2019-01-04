package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class BeginAmount implements Serializable {
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
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 总店编号
     */
    private String pcustomerid;

    /**
     * 客户分类
     */
    private String customersort;

    /**
     * 销售区域
     */
    private String salesarea;

    /**
     * 销售部门
     */
    private String salesdept;

    /**
     * 客户业务员
     */
    private String salesuser;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 应收日期
     */
    private String duefromdate;

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

    /**
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return 制单人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 制单人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            制单人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 制单人部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            制单人部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 制单时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最后修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最后修改人名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 最后修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            最后修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 审核人编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核人编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核人名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核人名称
     */
    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername == null ? null : auditusername.trim();
    }

    /**
     * @return 审核时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAudittime() {
        return audittime;
    }

    /**
     * @param audittime 
	 *            审核时间
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    /**
     * @return 中止人编号
     */
    public String getStopuserid() {
        return stopuserid;
    }

    /**
     * @param stopuserid 
	 *            中止人编号
     */
    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid == null ? null : stopuserid.trim();
    }

    /**
     * @return 中止人名称
     */
    public String getStopusername() {
        return stopusername;
    }

    /**
     * @param stopusername 
	 *            中止人名称
     */
    public void setStopusername(String stopusername) {
        this.stopusername = stopusername == null ? null : stopusername.trim();
    }

    /**
     * @return 中止时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getStoptime() {
        return stoptime;
    }

    /**
     * @param stoptime 
	 *            中止时间
     */
    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    /**
     * @return 关闭时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            关闭时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 打印次数
     */
    public Integer getPrinttimes() {
        return printtimes;
    }

    /**
     * @param printtimes 
	 *            打印次数
     */
    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
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
     * @return 总店编号
     */
    public String getPcustomerid() {
        return pcustomerid;
    }

    /**
     * @param pcustomerid 
	 *            总店编号
     */
    public void setPcustomerid(String pcustomerid) {
        this.pcustomerid = pcustomerid == null ? null : pcustomerid.trim();
    }

    /**
     * @return 客户分类
     */
    public String getCustomersort() {
        return customersort;
    }

    /**
     * @param customersort 
	 *            客户分类
     */
    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
    }

    /**
     * @return 销售区域
     */
    public String getSalesarea() {
        return salesarea;
    }

    /**
     * @param salesarea 
	 *            销售区域
     */
    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea == null ? null : salesarea.trim();
    }

    /**
     * @return 销售部门
     */
    public String getSalesdept() {
        return salesdept;
    }

    /**
     * @param salesdept 
	 *            销售部门
     */
    public void setSalesdept(String salesdept) {
        this.salesdept = salesdept == null ? null : salesdept.trim();
    }

    /**
     * @return 客户业务员
     */
    public String getSalesuser() {
        return salesuser;
    }

    /**
     * @param salesuser 
	 *            客户业务员
     */
    public void setSalesuser(String salesuser) {
        this.salesuser = salesuser == null ? null : salesuser.trim();
    }

    /**
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return 应收日期
     */
    public String getDuefromdate() {
        return duefromdate;
    }

    /**
     * @param duefromdate 
	 *            应收日期
     */
    public void setDuefromdate(String duefromdate) {
        this.duefromdate = duefromdate == null ? null : duefromdate.trim();
    }

    /**
     * @return 是否开票1是0否
     */
    public String getIsinvoice() {
        return isinvoice;
    }

    /**
     * @param isinvoice 
	 *            是否开票1是0否
     */
    public void setIsinvoice(String isinvoice) {
        this.isinvoice = isinvoice == null ? null : isinvoice.trim();
    }

    /**
     * @return 是否核销完成1核销完0未核销2核销中
     */
    public String getIswriteoff() {
        return iswriteoff;
    }

    /**
     * @param iswriteoff 
	 *            是否核销完成1核销完0未核销2核销中
     */
    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff == null ? null : iswriteoff.trim();
    }

    /**
     * @return 开票日期
     */
    public String getInvoicedate() {
        return invoicedate;
    }

    /**
     * @param invoicedate 
	 *            开票日期
     */
    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate == null ? null : invoicedate.trim();
    }

    /**
     * @return 核销日期
     */
    public String getWriteoffdate() {
        return writeoffdate;
    }

    /**
     * @param writeoffdate 
	 *            核销日期
     */
    public void setWriteoffdate(String writeoffdate) {
        this.writeoffdate = writeoffdate == null ? null : writeoffdate.trim();
    }

    /**
     * @return 核销人编号
     */
    public String getWriteoffuserid() {
        return writeoffuserid;
    }

    /**
     * @param writeoffuserid 
	 *            核销人编号
     */
    public void setWriteoffuserid(String writeoffuserid) {
        this.writeoffuserid = writeoffuserid == null ? null : writeoffuserid.trim();
    }

    /**
     * @return 核销人姓名
     */
    public String getWriteoffusername() {
        return writeoffusername;
    }

    /**
     * @param writeoffusername 
	 *            核销人姓名
     */
    public void setWriteoffusername(String writeoffusername) {
        this.writeoffusername = writeoffusername == null ? null : writeoffusername.trim();
    }

    /**
     * @return 开票金额
     */
    public BigDecimal getInvoiceamount() {
        return invoiceamount;
    }

    /**
     * @param invoiceamount 
	 *            开票金额
     */
    public void setInvoiceamount(BigDecimal invoiceamount) {
        this.invoiceamount = invoiceamount;
    }

    /**
     * @return 开票未税金额
     */
    public BigDecimal getInvoicenotaxamount() {
        return invoicenotaxamount;
    }

    /**
     * @param invoicenotaxamount 
	 *            开票未税金额
     */
    public void setInvoicenotaxamount(BigDecimal invoicenotaxamount) {
        this.invoicenotaxamount = invoicenotaxamount;
    }

    /**
     * @return 核销金额
     */
    public BigDecimal getWriteoffamount() {
        return writeoffamount;
    }

    /**
     * @param writeoffamount 
	 *            核销金额
     */
    public void setWriteoffamount(BigDecimal writeoffamount) {
        this.writeoffamount = writeoffamount;
    }

    /**
     * @return 核销未税金额
     */
    public BigDecimal getWriteoffnotaxamount() {
        return writeoffnotaxamount;
    }

    /**
     * @param writeoffnotaxamount 
	 *            核销未税金额
     */
    public void setWriteoffnotaxamount(BigDecimal writeoffnotaxamount) {
        this.writeoffnotaxamount = writeoffnotaxamount;
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

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
    
}