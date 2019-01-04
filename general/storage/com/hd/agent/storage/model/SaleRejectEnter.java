package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hd.agent.basefiles.model.Customer;
/**
 * 销售退货入库单
 * @author chenwei
 */
public class SaleRejectEnter implements Serializable {
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
     * 客户
     */
    private String customerid;


    /**
     * 客户
     */
    private Customer customerInfo;
    /**
     * 客户名称
     */
    private String customername;
    /**
     * 上级客户（总店）
     */
    private String pcustomerid;
    /**
     * 客户分类
     */
    private String customersort;
    /**
     * 对方经手人
     */
    private String handlerid;
    /**
     * 对方经手人名称
     */
    private String handlername;
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
     * 客户业务员名字
     */
    private String saleusername;
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
     * 来源类型1销售退货通知单0无2销售发货回单
     */
    private String sourcetype;

    /**
     * 来源单据编号
     */
    private String sourceid;

    /**
     * 入货仓库
     */
    private String storageid;
    /**
     * 入库仓库名称
     */
    private String storagename;
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
     * 参照上游单据的编号
     */
    private String billno;

    /**
     * 是否被参照0未参照1已参照
     */
    private String isrefer;
    /**
     * 是否验收1是0否
     */
    private String ischeck;
    /**
     * 是否核销1是0否
     */
    private String iswrite;
    /**
     * 验收人编号
     */
    private String checkuserid;
    /**
     * 验收人姓名
     */
    private String checkusername;
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
     * 开票日期
     */
    private String invoicebilldate;
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
     * 抽单金额
     */
    private BigDecimal invoiceamount;
    /**
     * 抽单未税金额
     */
    private BigDecimal invoicenotaxamount;

    /**
     * 开票金额
     */
    private BigDecimal invoicebillamount;

    /**
     * 开票未税金额
     */
    private BigDecimal invoicebillnotaxamount;
    /**
     * 核销金额
     */
    private BigDecimal writeoffamount;
    /**
     * 核销未税金额
     */
    private BigDecimal writeoffnotaxamount;
    /**
     * 销售内勤人员编号
     */
    private String indooruserid;
    /**
     * 销售内勤人员名称
     */
    private String indoorusername;
    /**
     * 订单编号
     */
    private String orderid;
    /**
     * 相关回单编号
     */
    private String receiptid;
    /**
     * 单据金额
     */
    private BigDecimal totalamount;
    /**
     * 应收日期
     */
    private String duefromdate;
    /**
     * 司机编号
     */
    private String driverid;
    /**
     * 司机名称
     */
    private String drivername;
    /**
     * 明细列表
     */
    List<SaleRejectEnterDetail> billDetailList ;
    /**
     * 收货人
     */
    private String storager;
    /**
     * 收货人名称
     */
    private String storagername;

    /**
     * 销售类型0正常销售1大宗销售。取系统编码表
     */
    private String salestype;
    /**
     * 生成凭证次数
     * @return
     */
    private int vouchertimes;



    public String getIswrite() {
		return iswrite;
	}

	public void setIswrite(String iswrite) {
		this.iswrite = iswrite;
	}

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
     * @return 来源类型1销售退货通知单2销售回单0无
     */
    public String getSourcetype() {
        return sourcetype;
    }

    /**
     * @param sourcetype 
	 *            来源类型1销售退货通知单2销售回单0无
     */
    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
    }

    /**
     * @return 来源单据编号
     */
    public String getSourceid() {
        return sourceid;
    }

    /**
     * @param sourceid 
	 *            来源单据编号
     */
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid == null ? null : sourceid.trim();
    }

    /**
     * @return 入货仓库
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            入货仓库
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
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
     * @return 参照上游单据的编号
     */
    public String getBillno() {
        return billno;
    }

    /**
     * @param billno 
	 *            参照上游单据的编号
     */
    public void setBillno(String billno) {
        this.billno = billno == null ? null : billno.trim();
    }

    /**
     * @return 是否被参照0未参照1已参照
     */
    public String getIsrefer() {
        return isrefer;
    }

    /**
     * @param isrefer 
	 *            是否被参照0未参照1已参照
     */
    public void setIsrefer(String isrefer) {
        this.isrefer = isrefer == null ? null : isrefer.trim();
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

	public String getSaleusername() {
		return saleusername;
	}

	public void setSaleusername(String saleusername) {
		this.saleusername = saleusername;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public String getSalesarea() {
		return salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getPcustomerid() {
		return pcustomerid;
	}

	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getCheckuserid() {
		return checkuserid;
	}

	public void setCheckuserid(String checkuserid) {
		this.checkuserid = checkuserid;
	}

	public String getCheckusername() {
		return checkusername;
	}

	public void setCheckusername(String checkusername) {
		this.checkusername = checkusername;
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

	public String getIndooruserid() {
		return indooruserid;
	}

	public void setIndooruserid(String indooruserid) {
		this.indooruserid = indooruserid;
	}

	public String getIndoorusername() {
		return indoorusername;
	}

	public void setIndoorusername(String indoorusername) {
		this.indoorusername = indoorusername;
	}

	public Customer getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(Customer customerInfo) {
		this.customerInfo = customerInfo;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<SaleRejectEnterDetail> getBillDetailList() {
		return billDetailList;
	}

	public void setBillDetailList(List<SaleRejectEnterDetail> billDetailList) {
		this.billDetailList = billDetailList;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public String getReceiptid() {
		return receiptid;
	}

	public void setReceiptid(String receiptid) {
		this.receiptid = receiptid;
	}

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}

	public String getDuefromdate() {
		return duefromdate;
	}

	public void setDuefromdate(String duefromdate) {
		this.duefromdate = duefromdate;
	}

	public String getDriverid() {
		return driverid;
	}

	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}

    public String getInvoicebilldate() {
        return invoicebilldate;
    }

    public void setInvoicebilldate(String invoicebilldate) {
        this.invoicebilldate = invoicebilldate;
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

    public String getStorager() {
        return storager;
    }

    public void setStorager(String storager) {
        this.storager = storager;
    }

    public String getStoragername() {
        return storagername;
    }

    public void setStoragername(String storagername) {
        this.storagername = storagername;
    }

    public String getSalestype() {
        return salestype;
    }

    public void setSalestype(String salestype) {
        this.salestype = salestype;
    }

    public int getVouchertimes() {
        return vouchertimes;
    }

    public void setVouchertimes(int vouchertimes) {
        this.vouchertimes = vouchertimes;
    }
}
