package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hd.agent.basefiles.model.Customer;
import org.apache.struts2.json.annotations.JSON;
/**
 * 销售发票
 * @author chenwei
 */
public class SalesInvoice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    /**
     * 类型 1销售发票 2应收款冲差（只用于收款单核销时）
     */
    private String billtype;
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
     * 表头自定义项1
     */
    private String field01;

    /**
     * 表头自定义项2
     */
    private String field02;

    /**
     * 表头自定义项3
     */
    private String field03;

    /**
     * 表头自定义项4
     */
    private String field04;

    /**
     * 表头自定义项5
     */
    private String field05;

    /**
     * 表头自定义项6
     */
    private String field06;

    /**
     * 表头自定义项7
     */
    private String field07;

    /**
     * 表头自定义项8
     */
    private String field08;

    /**
     * 客户
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 门店客户
     */
    private String chlidcustomerid;
    /**
     * 门店名称
     */
    private String chlidcustomername;
    /**
     * 开票客户
     */
    private String invoicecustomerid;
    
    /**
     * 开票客户名称
     */
    private String invoicecustomername;
    /**
     * 总店客户
     */
    private String pcustomerid;
    /**
     * 总店名称
     */
    private String pcutomername;
    /**
     * 对方经手人
     */
    private String handlerid;
    /**
     * 对方经手人名称
     */
    private String handlername;
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
     * 客户业务员名称
     */
    private String salesusername;
    
    /**
     * 销售区域
     */
    private String salesarea;
    
    /**
     * 销售区域名称
     */
    private String salesareaname;
    
    /**
     * 来源类型0无1销售发货回单2销售退货通知单
     */
    private String sourcetype;

    /**
     * 结算方式
     */
    private String settletype;
    /**
     * 结算方式名称
     */
    private String settletypename;
    /**
     * 支付方式
     */
    private String paytype;
    /**
     * 支付方式名称
     */
    private String paytypename;
    /**
     * 是否折扣0否1是
     */
    private String isdiscount;
    /**
     * 申请状态
     */
    private String applytype;
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
     * 应收日期
     */
    private String receivedate;

    /**
     * 是否已核销完成0否1是
     */
    private String iswriteoff;

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
     * 含税总金额
     */
    private BigDecimal taxamount;

    /**
     * 无税总金额
     */
    private BigDecimal notaxamount;

    /**
     * 折扣金额
     */
    private BigDecimal discountamount;

    /**
     * 应收总金额
     */
    private BigDecimal invoiceamount;

    /**
     * 核销总金额
     */
    private BigDecimal writeoffamount;

    /**
     * 尾差
     */
    private BigDecimal tailamount;
    /**
     * 是否关联收款单1是0否
     */
    private String isrelate;
    /**
     * 来源编号
     */
    private String sourceid;
    /**
     * 客户余额
     */
    private BigDecimal customeramount;
    /**
     * 销售内勤
     */
    private String indooruserid;

    /**
     * 关联的开票单据编号
     */
    private String salesinvoicebillid;

    /**
     * 是否开票0未开票1已开票
     */
    private String isinvoicebill;
    /**
     * 明细列表
     */
    private List<SalesInvoiceDetail> salesInvoiceDetailList;
    /**
     * 税种
     */
    private String taxtype;
    /**
     * 税种名称
     */
    private String taxtypename;
    /**
     * 客户信息
     */
    private Customer customerInfo;
    /**
     * 生成凭证次数
     */
    private int vouchertimes;

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
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 表头自定义项1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01 
	 *            表头自定义项1
     */
    public void setField01(String field01) {
        this.field01 = field01 == null ? null : field01.trim();
    }

    /**
     * @return 表头自定义项2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02 
	 *            表头自定义项2
     */
    public void setField02(String field02) {
        this.field02 = field02 == null ? null : field02.trim();
    }

    /**
     * @return 表头自定义项3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03 
	 *            表头自定义项3
     */
    public void setField03(String field03) {
        this.field03 = field03 == null ? null : field03.trim();
    }

    /**
     * @return 表头自定义项4
     */
    public String getField04() {
        return field04;
    }

    /**
     * @param field04 
	 *            表头自定义项4
     */
    public void setField04(String field04) {
        this.field04 = field04 == null ? null : field04.trim();
    }

    /**
     * @return 表头自定义项5
     */
    public String getField05() {
        return field05;
    }

    /**
     * @param field05 
	 *            表头自定义项5
     */
    public void setField05(String field05) {
        this.field05 = field05 == null ? null : field05.trim();
    }

    /**
     * @return 表头自定义项6
     */
    public String getField06() {
        return field06;
    }

    /**
     * @param field06 
	 *            表头自定义项6
     */
    public void setField06(String field06) {
        this.field06 = field06 == null ? null : field06.trim();
    }

    /**
     * @return 表头自定义项7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07 
	 *            表头自定义项7
     */
    public void setField07(String field07) {
        this.field07 = field07 == null ? null : field07.trim();
    }

    /**
     * @return 表头自定义项8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08 
	 *            表头自定义项8
     */
    public void setField08(String field08) {
        this.field08 = field08 == null ? null : field08.trim();
    }

    /**
     * @return 客户
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 对方经手人
     */
    public String getHandlerid() {
        return handlerid;
    }

    /**
     * @param handlerid 
	 *            对方经手人
     */
    public void setHandlerid(String handlerid) {
        this.handlerid = handlerid == null ? null : handlerid.trim();
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
     * @return 来源类型0无1销售发货回单2销售退货通知单
     */
    public String getSourcetype() {
        return sourcetype;
    }

    /**
     * @param sourcetype 
	 *            来源类型0无1销售发货回单2销售退货通知单
     */
    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
    }

    /**
     * @return 结算方式
     */
    public String getSettletype() {
        return settletype;
    }

    /**
     * @param settletype 
	 *            结算方式
     */
    public void setSettletype(String settletype) {
        this.settletype = settletype == null ? null : settletype.trim();
    }

    /**
     * @return 支付方式
     */
    public String getPaytype() {
        return paytype;
    }

    /**
     * @param paytype 
	 *            支付方式
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    /**
     * @return 是否折扣0否1是
     */
    public String getIsdiscount() {
        return isdiscount;
    }

    /**
     * @param isdiscount 
	 *            是否折扣0否1是
     */
    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount == null ? null : isdiscount.trim();
    }

    /**
     * @return 发票类型1增值税2普通3其他
     */
    public String getInvoicetype() {
        return invoicetype;
    }

    /**
     * @param invoicetype 
	 *            发票类型1增值税2普通3其他
     */
    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype == null ? null : invoicetype.trim();
    }

    /**
     * @return 应收日期
     */
    public String getReceivedate() {
        return receivedate;
    }

    /**
     * @param receivedate 
	 *            应收日期
     */
    public void setReceivedate(String receivedate) {
        this.receivedate = receivedate == null ? null : receivedate.trim();
    }

    /**
     * @return 是否已核销完成0否1是
     */
    public String getIswriteoff() {
        return iswriteoff;
    }

    /**
     * @param iswriteoff 
	 *            是否已核销完成0否1是
     */
    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff == null ? null : iswriteoff.trim();
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
     * @return 含税总金额
     */
    public BigDecimal getTaxamount() {
        return taxamount;
    }

    /**
     * @param taxamount 
	 *            含税总金额
     */
    public void setTaxamount(BigDecimal taxamount) {
        this.taxamount = taxamount;
    }

    /**
     * @return 无税总金额
     */
    public BigDecimal getNotaxamount() {
        return notaxamount;
    }

    /**
     * @param notaxamount 
	 *            无税总金额
     */
    public void setNotaxamount(BigDecimal notaxamount) {
        this.notaxamount = notaxamount;
    }

    /**
     * @return 折扣金额
     */
    public BigDecimal getDiscountamount() {
        return discountamount;
    }

    /**
     * @param discountamount 
	 *            折扣金额
     */
    public void setDiscountamount(BigDecimal discountamount) {
        this.discountamount = discountamount;
    }

    /**
     * @return 应收总金额
     */
    public BigDecimal getInvoiceamount() {
        return invoiceamount;
    }

    /**
     * @param invoiceamount 
	 *            应收总金额
     */
    public void setInvoiceamount(BigDecimal invoiceamount) {
        this.invoiceamount = invoiceamount;
    }

    /**
     * @return 核销总金额
     */
    public BigDecimal getWriteoffamount() {
        return writeoffamount;
    }

    /**
     * @param writeoffamount 
	 *            核销总金额
     */
    public void setWriteoffamount(BigDecimal writeoffamount) {
        this.writeoffamount = writeoffamount;
    }

    /**
     * @return 尾差
     */
    public BigDecimal getTailamount() {
        return tailamount;
    }

    /**
     * @param tailamount 
	 *            尾差
     */
    public void setTailamount(BigDecimal tailamount) {
        this.tailamount = tailamount;
    }

    /**
     * @return 来源编号
     */
    public String getSourceid() {
        return sourceid;
    }

    /**
     * @param sourceid 
	 *            来源编号
     */
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid == null ? null : sourceid.trim();
    }

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getHandlername() {
		return handlername;
	}

	public void setHandlername(String handlername) {
		this.handlername = handlername;
	}

	public String getSalesdeptname() {
		return salesdeptname;
	}

	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
	}

	public String getSalesusername() {
		return salesusername;
	}

	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
	}

	public String getSettletypename() {
		return settletypename;
	}

	public void setSettletypename(String settletypename) {
		this.settletypename = settletypename;
	}

	public String getPaytypename() {
		return paytypename;
	}

	public void setPaytypename(String paytypename) {
		this.paytypename = paytypename;
	}

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getInvoicecustomerid() {
		return invoicecustomerid;
	}

	public void setInvoicecustomerid(String invoicecustomerid) {
		this.invoicecustomerid = invoicecustomerid;
	}

	public String getInvoicecustomername() {
		return invoicecustomername;
	}

	public void setInvoicecustomername(String invoicecustomername) {
		this.invoicecustomername = invoicecustomername;
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

	public List<SalesInvoiceDetail> getSalesInvoiceDetailList() {
		return salesInvoiceDetailList;
	}

	public void setSalesInvoiceDetailList(
			List<SalesInvoiceDetail> salesInvoiceDetailList) {
		this.salesInvoiceDetailList = salesInvoiceDetailList;
	}

	public String getApplytype() {
		return applytype;
	}

	public void setApplytype(String applytype) {
		this.applytype = applytype;
	}

	public String getChlidcustomerid() {
		return chlidcustomerid;
	}

	public void setChlidcustomerid(String chlidcustomerid) {
		this.chlidcustomerid = chlidcustomerid;
	}

	public String getChlidcustomername() {
		return chlidcustomername;
	}

	public void setChlidcustomername(String chlidcustomername) {
		this.chlidcustomername = chlidcustomername;
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

	public String getIsrelate() {
		return isrelate;
	}

	public void setIsrelate(String isrelate) {
		this.isrelate = isrelate;
	}

	public BigDecimal getCustomeramount() {
		return customeramount;
	}

	public void setCustomeramount(BigDecimal customeramount) {
		this.customeramount = customeramount;
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

	public String getIndooruserid() {
		return indooruserid;
	}

	public void setIndooruserid(String indooruserid) {
		this.indooruserid = indooruserid;
	}

	public String getPcustomerid() {
		return pcustomerid;
	}

	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}

	public String getPcutomername() {
		return pcutomername;
	}

	public void setPcutomername(String pcutomername) {
		this.pcutomername = pcutomername;
	}

    public String getSalesinvoicebillid() {
        return salesinvoicebillid;
    }

    public void setSalesinvoicebillid(String salesinvoicebillid) {
        this.salesinvoicebillid = salesinvoicebillid;
    }

    public String getIsinvoicebill() {
        return isinvoicebill;
    }

    public void setIsinvoicebill(String isinvoicebill) {
        this.isinvoicebill = isinvoicebill;
    }

	public String getTaxtype() {
		return taxtype;
	}

	public void setTaxtype(String taxtype) {
        this.taxtype = taxtype == null ? null : taxtype.trim();
	}

	public String getTaxtypename() {
		return taxtypename;
	}

	public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename == null ? null : taxtypename.trim();
	}

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public int getVouchertimes() {
        return vouchertimes;
    }

    public void setVouchertimes(int vouchertimes) {
        this.vouchertimes = vouchertimes;
    }
}