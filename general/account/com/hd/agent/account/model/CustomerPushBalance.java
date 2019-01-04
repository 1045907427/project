package com.hd.agent.account.model;

import com.hd.agent.basefiles.model.Customer;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 客户应收款冲差
 * @author chenwei
 */
public class CustomerPushBalance implements Serializable {
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
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 客户信息
     */
    private Customer customerInfo;
    /**
     * 总店客户编号
     */
    private String pcustomerid;
    /**
     * 总店客户名称
     */
    private String pcustomername;
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
     * 冲差类型1品牌冲差0其他冲差
     */
    private String pushtype;   
    /**
     * 冲差类型1品牌冲差0其他冲差
     */
    private String pushtypename;
    /**
     * 费用科目
     */
    private String subject;
    /**
     * 费用科目名称
     */
    private String subjectname;
    /**
     * 商品品牌
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 品牌业务员
     */
    private String branduser;
    /**
     * 品牌业务员名称
     */
    private String brandusername;
    /**
     * 品牌部门
     */
    private String branddept;
    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 厂家业务员
     */
    private String supplieruser;
    /**
     * 订单编号
     */
    private String orderid;
    /**
     * 冲差单类型0正常冲差单1关联发票2关联回单
     */
    private String isinvoice;

    /**
     * 冲差单类型名称
     */
    private String isinvoicename;
    /**
     * 是否返利1是0否
     */
    private String isrebate;
    /**
     * 销售发票编号(回单编号)
     */
    private String invoiceid;

    /**
     * 默认税种
     */
    private String defaulttaxtype;

    /**
     * 默认税种名称
     */
    private String defaulttaxtypename;

    /**
     * 冲差金额
     */
    private BigDecimal amount;

    /**
     * 冲差未税金额
     */
    private BigDecimal notaxamount;

    /**
     * 冲差税额
     */
    private BigDecimal tax;

    /**
     * 是否核销1是0否
     */
    private String iswriteoff;

    /**
     * 是否核销名称
     */
    private String iswriteoffname;
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
     * 状态
     */
    private String status;

    /**
     * 状态名称
     */
    private String statusname;

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
     * 回单验收日期
     */
    private String checkdate;
    /**
     * 抽单日期
     */
    private String invoicedate;

    /**
     * 开票日期
     */
    private String invoicebilldate;
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
     * 尾差金额
     */
    private BigDecimal tailamount;
    /**
     * 核销未税金额
     */
    private BigDecimal writeoffnotaxamount;
    /**
     * 是否关联收款单1是0否
     */
    private String isrelate;
    /**
     * 销售内勤
     */
    private String indooruserid;
    /**
     * 正常的冲差是否关联发票1是0否
     */
    private String isrefer;

    /**
     * 发票状态
     */
    private String isrefername;
    /**
     * OA编号
     */
    private String oaid;
    
    /**
     * 是否实际开票1是0否
     */
    private String isinvoicebill;
    /**
     * 是否生成回单j交接单1是0否
     */
    private String ishand;
    /**
     * 开票状态名称
     */
    private String isinvoicebillname;

    /**
     * 1: 正常流转；2：驳回
     */
    private String oaback;

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
     * @return 销售发票编号
     */
    public String getInvoiceid() {
        return invoiceid;
    }

    /**
     * @param invoiceid 
	 *            销售发票编号
     */
    public void setInvoiceid(String invoiceid) {
        this.invoiceid = invoiceid == null ? null : invoiceid.trim();
    }

    /**
     * @return 冲差金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            冲差金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return 是否核销1是0否
     */
    public String getIswriteoff() {
        return iswriteoff;
    }

    /**
     * @param iswriteoff 
	 *            是否核销1是0否
     */
    public void setIswriteoff(String iswriteoff) {
        this.iswriteoff = iswriteoff == null ? null : iswriteoff.trim();
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

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public Customer getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(Customer customerInfo) {
		this.customerInfo = customerInfo;
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

	public String getPushtype() {
		return pushtype;
	}

	public void setPushtype(String pushtype) {
		this.pushtype = pushtype;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getPushtypename() {
		return pushtypename;
	}

	public void setPushtypename(String pushtypename) {
		this.pushtypename = pushtypename;
	}
	/**
	 * 冲差单类型0正常冲差单1关联发票2关联回单
	 * @return
	 * @author chenwei 
	 * @date Nov 15, 2013
	 */
	public String getIsinvoice() {
		return isinvoice;
	}
	/**
	 * 冲差单类型0正常冲差单1关联发票2关联回单
	 * @param isinvoice
	 * @author chenwei 
	 * @date Nov 15, 2013
	 */
	public void setIsinvoice(String isinvoice) {
		this.isinvoice = isinvoice;
	}

	public String getBranduser() {
		return branduser;
	}

	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}

	public String getSalesarea() {
		return salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getSalesdept() {
		return salesdept;
	}

	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}

	public String getSalesuser() {
		return salesuser;
	}

	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}

	public String getBranddept() {
		return branddept;
	}

	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}

	public String getWriteoffdate() {
		return writeoffdate;
	}

	public void setWriteoffdate(String writeoffdate) {
		this.writeoffdate = writeoffdate;
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

	public BigDecimal getChecknotaxamount() {
		return checknotaxamount;
	}

	public void setChecknotaxamount(BigDecimal checknotaxamount) {
		this.checknotaxamount = checknotaxamount;
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

	public String getIsrebate() {
		return isrebate;
	}

	public void setIsrebate(String isrebate) {
		this.isrebate = isrebate;
	}

	public String getIsrelate() {
		return isrelate;
	}

	public void setIsrelate(String isrelate) {
		this.isrelate = isrelate;
	}

	public BigDecimal getTailamount() {
		return tailamount;
	}

	public void setTailamount(BigDecimal tailamount) {
		this.tailamount = tailamount;
	}

	public String getIndooruserid() {
		return indooruserid;
	}

	public void setIndooruserid(String indooruserid) {
		this.indooruserid = indooruserid;
	}

	public String getIsrefer() {
		return isrefer;
	}

	public void setIsrefer(String isrefer) {
		this.isrefer = isrefer;
	}

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getOaid() {
		return oaid;
	}

	public void setOaid(String oaid) {
		this.oaid = oaid;
	}

	public String getSupplieruser() {
		return supplieruser;
	}

	public void setSupplieruser(String supplieruser) {
		this.supplieruser = supplieruser;
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

	public String getIsinvoicebill() {
		return isinvoicebill;
	}

	public void setIsinvoicebill(String isinvoicebill) {
		this.isinvoicebill = isinvoicebill;
	}

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getIsinvoicename() {
        return isinvoicename;
    }

    public void setIsinvoicename(String isinvoicename) {
        this.isinvoicename = isinvoicename;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getIsrefername() {
        return isrefername;
    }

    public void setIsrefername(String isrefername) {
        this.isrefername = isrefername;
    }

    public String getIsinvoicebillname() {
        return isinvoicebillname;
    }

    public void setIsinvoicebillname(String isinvoicebillname) {
        this.isinvoicebillname = isinvoicebillname;
    }
    public String getIswriteoffname() {
        return iswriteoffname;
    }

    public void setIswriteoffname(String iswriteoffname) {
        this.iswriteoffname = iswriteoffname;
    }

    public String getIshand() {
        return ishand;
    }

    public void setIshand(String ishand) {
        this.ishand = ishand;
    }

    public String getDefaulttaxtype() {
        return defaulttaxtype;
    }

    public void setDefaulttaxtype(String defaulttaxtype) {
        this.defaulttaxtype = defaulttaxtype;
    }

    public String getDefaulttaxtypename() {
        return defaulttaxtypename;
    }

    public void setDefaulttaxtypename(String defaulttaxtypename) {
        this.defaulttaxtypename = defaulttaxtypename;
    }

    public BigDecimal getNotaxamount() {
        return notaxamount;
    }

    public void setNotaxamount(BigDecimal notaxamount) {
        this.notaxamount = notaxamount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getInvoicebilldate() {
        return invoicebilldate;
    }

    public void setInvoicebilldate(String invoicebilldate) {
        this.invoicebilldate = invoicebilldate;
    }

    public String getOaback() {
        return oaback;
    }

    public void setOaback(String oaback) {
        this.oaback = oaback;
    }

    public String getBrandusername() {
        return brandusername;
    }

    public void setBrandusername(String brandusername) {
        this.brandusername = brandusername;
    }

    public int getVouchertimes() {
        return vouchertimes;
    }

    public void setVouchertimes(int vouchertimes) {
        this.vouchertimes = vouchertimes;
    }
}