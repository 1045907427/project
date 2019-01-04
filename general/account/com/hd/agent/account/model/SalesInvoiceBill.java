package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.PageData;
import org.apache.struts2.json.annotations.JSON;

public class SalesInvoiceBill implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;
    
    /**
     * 单据类型 1销售开票，2冲差单
     */
    private String ordertype;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 单据类型1正常开票2预开票
     */
    private String billtype;

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
     * 客户(总店客户)
     */
    private String customerid;
    
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 门店客户编码
     */
    private String chlidcustomerid;
    
    /**
     * 门店客户名称
     */
    private String chlidcustomername;

    /**
     * 发票客户
     */
    private String invoicecustomerid;

    /**
     * 发票客户名称
     */
    private String invoicecustomername;

    /**
     * 总店客户
     */
    private String pcustomerid;
    
    /**
     * 总店客户名称
     */
    private String pcustomername;

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
     * 销售员
     */
    private String salesuser;
    
    /**
     * 销售员名称
     */
    private String salesusername;

    /**
     * 应收日期
     */
    private String receivedate;

    /**
     * 含税总金额
     */
    private BigDecimal taxamount;

    /**
     * 无税总金额
     */
    private BigDecimal notaxamount;

    /**
     * 是否关联收款单1是0否
     */
    private String isrelate;

    /**
     * 默认内勤
     */
    private String indooruserid;

    /**
     * 来源编号
     */
    private String sourceid;
    
    /**
     * 客户余额
     */
    private BigDecimal customeramount;

    /**
     * 关联的核销单据编号
     */
    private String salesinvoiceid;

    /**
     * 是否核销1是0否
     */
    private String iswriteoff;

    /**
     * 明细列表
     */
    private List<SalesInvoiceBillDetail> detaiList;
    /**
     * 税种
     */
    private String taxtype;
    /**
     * 税种名称
     */
    private String taxtypename;

    private Integer jxexporttimes;

    private String jxexportuserid;

    private String jxexportusername;

    private Date jxexportdatetime;
    /**
     * 客户信息
     */
    private Customer customerInfo;
    /**
     * 生成凭证次数
     */
    private String vouchertimes;

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
     * @return 单据类型1正常开票2预开票
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            单据类型1正常开票2预开票
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
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
     * @return 客户(总店客户)
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户(总店客户)
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 门店客户列表
     */
    public String getChlidcustomerid() {
        return chlidcustomerid;
    }

    /**
     * @param chlidcustomerid 
	 *            门店客户列表
     */
    public void setChlidcustomerid(String chlidcustomerid) {
        this.chlidcustomerid = chlidcustomerid == null ? null : chlidcustomerid.trim();
    }

    /**
     * @return 发票客户
     */
    public String getInvoicecustomerid() {
        return invoicecustomerid;
    }

    /**
     * @param invoicecustomerid 
	 *            发票客户
     */
    public void setInvoicecustomerid(String invoicecustomerid) {
        this.invoicecustomerid = invoicecustomerid == null ? null : invoicecustomerid.trim();
    }

    /**
     * @return 发票客户名称
     */
    public String getInvoicecustomername() {
        return invoicecustomername;
    }

    /**
     * @param invoicecustomername 
	 *            发票客户名称
     */
    public void setInvoicecustomername(String invoicecustomername) {
        this.invoicecustomername = invoicecustomername == null ? null : invoicecustomername.trim();
    }

    /**
     * @return 总店客户
     */
    public String getPcustomerid() {
        return pcustomerid;
    }

    /**
     * @param pcustomerid 
	 *            总店客户
     */
    public void setPcustomerid(String pcustomerid) {
        this.pcustomerid = pcustomerid == null ? null : pcustomerid.trim();
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
     * @return 发票号
     */
    public String getInvoiceno() {
        return invoiceno;
    }

    /**
     * @param invoiceno 
	 *            发票号
     */
    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    /**
     * @return 发票代码
     */
    public String getInvoicecode() {
        return invoicecode;
    }

    /**
     * @param invoicecode 
	 *            发票代码
     */
    public void setInvoicecode(String invoicecode) {
        this.invoicecode = invoicecode == null ? null : invoicecode.trim();
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
     * @return 销售员
     */
    public String getSalesuser() {
        return salesuser;
    }

    /**
     * @param salesuser 
	 *            销售员
     */
    public void setSalesuser(String salesuser) {
        this.salesuser = salesuser == null ? null : salesuser.trim();
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
     * @return 是否关联收款单1是0否
     */
    public String getIsrelate() {
        return isrelate;
    }

    /**
     * @param isrelate 
	 *            是否关联收款单1是0否
     */
    public void setIsrelate(String isrelate) {
        this.isrelate = isrelate == null ? null : isrelate.trim();
    }

    /**
     * @return 默认内勤
     */
    public String getIndooruserid() {
        return indooruserid;
    }

    /**
     * @param indooruserid 
	 *            默认内勤
     */
    public void setIndooruserid(String indooruserid) {
        this.indooruserid = indooruserid == null ? null : indooruserid.trim();
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

	public BigDecimal getCustomeramount() {
		return customeramount;
	}

	public void setCustomeramount(BigDecimal customeramount) {
		this.customeramount = customeramount;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getChlidcustomername() {
		return chlidcustomername;
	}

	public void setChlidcustomername(String chlidcustomername) {
		this.chlidcustomername = chlidcustomername;
	}

	public String getPcustomername() {
		return pcustomername;
	}

	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
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

	public List<SalesInvoiceBillDetail> getDetaiList() {
		return detaiList;
	}

	public void setDetaiList(List<SalesInvoiceBillDetail> detaiList) {
		this.detaiList = detaiList;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

    public String getSalesinvoiceid() {
        return salesinvoiceid;
    }

    public void setSalesinvoiceid(String salesinvoiceid) {
        this.salesinvoiceid = salesinvoiceid;
    }

    public String getIswriteoff() {
        return iswriteoff;
    }

    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff;
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

    public Integer getJxexporttimes() {
        return jxexporttimes;
    }

    public void setJxexporttimes(Integer jxexporttimes) {
        this.jxexporttimes = jxexporttimes;
    }

    public String getJxexportuserid() {
        return jxexportuserid;
    }

    public void setJxexportuserid(String jxexportuserid) {
        this.jxexportuserid = jxexportuserid == null ? null : jxexportuserid.trim();
    }

    public String getJxexportusername() {
        return jxexportusername;
    }

    public void setJxexportusername(String jxexportusername) {
        this.jxexportusername = jxexportusername == null ? null : jxexportusername.trim();
    }

    public Date getJxexportdatetime() {
        return jxexportdatetime;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public void setJxexportdatetime(Date jxexportdatetime) {
        this.jxexportdatetime = jxexportdatetime;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getVouchertimes() {
        return vouchertimes;
    }

    public void setVouchertimes(String vouchertimes) {
        this.vouchertimes = vouchertimes;
    }
}