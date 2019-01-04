package com.hd.agent.sales.model;

import com.hd.agent.basefiles.model.Customer;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售发货回单实体
 * @author zhengziyong
 *
 */
public class Receipt implements Serializable {
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
     * 客户名称
     */
    private String customername;
    /**
     * 上级客户（总店）
     */
    private String pcustomerid;
    
    /**
     * 总店名称名称
     */
    private String pcustomername;
    /**
     * 客户分类
     */
    private String customersort;
    /**
     * 总店客户编号
     */
    private String headcustomerid;
    /**
     * 总店客户名称
     */
    private String headcustomername;
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
     * 客户业务员名称
     */
    private String salesusername;
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
     * 来源类型
     */
    private String source;

    /**
     * 出库仓库
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 总发货金额
     */
    private BigDecimal totaltaxamount;
    /**
     * 总未税金额
     */
    private BigDecimal totalnotaxamount;
    /**
     * 总税额
     */
    private BigDecimal totaltax;
    /**
     * 总应收金额
     */
    private BigDecimal totalreceipttaxamount;
    
    /**
     * 总未税应收金额
     */
    private BigDecimal totalreceiptnotaxamount;
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
     * 是否被参照0未参照1已参照
     */
    private String isrefer;
    
    /**
     * 1生成发票2核销3可以生成发票4开票中5部分核销
     */
    private String isinvoice;
    
    private String canceldate;
    
    private String duefromdate;
    /**
     * 销售订单编号
     */
    private String saleorderid;
    /**
     * 参照上游单据编号
     */
    private String billno;
    /**
     * 物流配送单号
     */
    private String deliveryid;
    /**
     * 销售内勤人员编号
     */
    private String indooruserid;
    /**
     * 销售内勤名称
     */
    private String indoorusername;
	
	/**
     * 账期
     */
    private String accounttype;
    
    /**
     * 账期名称1账期，2现款
     */
    private String accounttypename;
    
    /**
     * 收款人
     */
    private String payeeid;
    
    /**
     * 收款人名称
     */
    private String payeename;
    
    /**
     * 年月（用于分组月份）
     */
    private String yearmonth;
    
    /**
     * 单据类型
     */
    private String billtype;
    
    /**
     * 单据类型名称
     */
    private String billtypename;
    
    /**
     * 1已开票3可以生成发票4开票中
     */
    private String isinvoicebill;
    
    private List<ReceiptDetail> receiptDetailList;

    /**
     * 否生成交接单
     */
    private String ishand;

    /**
     * 配送状态
     */
    private String isdelivery;

    /**
     * 客户单号
     */
    private String sourceid;

    private Customer customerInfo;

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
        this.id = id;
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
        this.businessdate = businessdate;
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
        this.status = status;
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
        this.remark = remark;
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
        this.adduserid = adduserid;
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
        this.addusername = addusername;
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
        this.adddeptid = adddeptid;
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
        this.adddeptname = adddeptname;
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
        this.modifyuserid = modifyuserid;
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
        this.modifyusername = modifyusername;
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
        this.audituserid = audituserid;
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
        this.auditusername = auditusername;
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
        this.stopuserid = stopuserid;
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
        this.stopusername = stopusername;
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
        this.customerid = customerid;
    }

    /**
     * 对方经手人
     * @return
     * @author zhengziyong 
     * @date Jun 1, 2013
     */
    public String getHandlerid() {
		return handlerid;
	}

    /**
     * 对方经手人
     * @param handlerid
     * @author zhengziyong 
     * @date Jun 1, 2013
     */
	public void setHandlerid(String handlerid) {
		this.handlerid = handlerid;
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
        this.salesdept = salesdept;
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
        this.salesuser = salesuser;
    }

    /**
     * 结算方式
     * @return
     * @author zhengziyong 
     * @date Jun 1, 2013
     */
    public String getSettletype() {
		return settletype;
	}

    /**
     * 结算方式
     * @param settletype
     * @author zhengziyong 
     * @date Jun 1, 2013
     */
	public void setSettletype(String settletype) {
		this.settletype = settletype == null ? null : settletype.trim();
	}

	/**
	 * 支付方式
	 * @return
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public String getPaytype() {
		return paytype;
	}

	/**
	 * 支付方式
	 * @param paytype
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	/**
     * @return 来源类型
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source 
	 *            来源类型
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return 出库仓库
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            出库仓库
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid;
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
        this.field01 = field01;
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
        this.field02 = field02;
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
        this.field03 = field03;
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
        this.field04 = field04;
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
        this.field05 = field05;
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
        this.field06 = field06;
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
        this.field07 = field07;
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
        this.field08 = field08;
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
        this.isrefer = isrefer;
    }

    /**
     * 
     * @return 1生成发票2核销3可以生成发票4开票中5部分核销
     * @author zhengziyong 
     * @date Jul 26, 2013
     */
    public String getIsinvoice() {
		return isinvoice;
	}

    /**
     * 
     * @param isinvoice 1生成发票2核销3可以生成发票4开票中5部分核销
     * @author zhengziyong 
     * @date Jul 26, 2013
     */
	public void setIsinvoice(String isinvoice) {
		this.isinvoice = isinvoice;
	}

	public String getCanceldate() {
		return canceldate;
	}

	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}

	/**
     * 参照上游单据编号
     * @return
     * @author zhengziyong 
     * @date Jun 1, 2013
     */
	public String getBillno() {
		return billno;
	}

	/**
	 * 参照上游单据编号
	 * @param billno
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getDuefromdate() {
		return duefromdate;
	}

	public void setDuefromdate(String duefromdate) {
		this.duefromdate = duefromdate;
	}

	public List<ReceiptDetail> getReceiptDetailList() {
		return receiptDetailList;
	}

	public void setReceiptDetailList(List<ReceiptDetail> receiptDetailList) {
		this.receiptDetailList = receiptDetailList;
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
		this.settletypename = settletypename == null ? null : settletypename.trim();
	}

	public String getPaytypename() {
		return paytypename;
	}

	public void setPaytypename(String paytypename) {
		this.paytypename = paytypename;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public BigDecimal getTotaltaxamount() {
		return totaltaxamount;
	}

	public void setTotaltaxamount(BigDecimal totaltaxamount) {
		this.totaltaxamount = totaltaxamount;
	}

	public BigDecimal getTotalnotaxamount() {
		return totalnotaxamount;
	}

	public void setTotalnotaxamount(BigDecimal totalnotaxamount) {
		this.totalnotaxamount = totalnotaxamount;
	}

	public BigDecimal getTotaltax() {
		return totaltax;
	}

	public void setTotaltax(BigDecimal totaltax) {
		this.totaltax = totaltax;
	}

	public String getHeadcustomerid() {
		return headcustomerid;
	}

	public void setHeadcustomerid(String headcustomerid) {
		this.headcustomerid = headcustomerid;
	}

	public String getHeadcustomername() {
		return headcustomername;
	}

	public void setHeadcustomername(String headcustomername) {
		this.headcustomername = headcustomername;
	}

	public String getSaleorderid() {
		return saleorderid;
	}

	public void setSaleorderid(String saleorderid) {
		this.saleorderid = saleorderid;
	}

	public BigDecimal getTotalreceipttaxamount() {
		return totalreceipttaxamount;
	}

	public void setTotalreceipttaxamount(BigDecimal totalreceipttaxamount) {
		this.totalreceipttaxamount = totalreceipttaxamount;
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

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}
	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public String getPayeeid() {
		return payeeid;
	}

	public void setPayeeid(String payeeid) {
		this.payeeid = payeeid;
	}

	public String getPayeename() {
		return payeename;
	}

	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}

	public String getSalesareaname() {
		return salesareaname;
	}

	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
	}

	public String getPcustomername() {
		return pcustomername;
	}

	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}

	public String getYearmonth() {
		return yearmonth;
	}

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}

	public BigDecimal getTotalreceiptnotaxamount() {
		return totalreceiptnotaxamount;
	}

	public void setTotalreceiptnotaxamount(BigDecimal totalreceiptnotaxamount) {
		this.totalreceiptnotaxamount = totalreceiptnotaxamount;
	}

	public String getAccounttypename() {
		return accounttypename;
	}

	public void setAccounttypename(String accounttypename) {
		this.accounttypename = accounttypename;
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

	public String getIsinvoicebill() {
		return isinvoicebill;
	}

	public void setIsinvoicebill(String isinvoicebill) {
		this.isinvoicebill = isinvoicebill;
	}

    public String getIshand() {
        return ishand;
    }

    public void setIshand(String ishand) {
        this.ishand = ishand;
    }

    public String getIsdelivery() {
        return isdelivery;
    }

    public void setIsdelivery(String isdelivery) {
        this.isdelivery = isdelivery;
    }

    public String getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(String deliveryid) {
        this.deliveryid = deliveryid;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public Customer getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Customer customerInfo) {
        this.customerInfo = customerInfo;
    }
}