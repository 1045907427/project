package com.hd.agent.storage.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class StorageDeliveryOut implements Serializable {
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
     * 客户单号
     */
    private String customerbill;

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
     * 单据类型1供应商配送退货出库单2客户配送出库单
     */
    private String billtype;

    /**
     * 来源类型0无来源1供应商代配送客户订单2供应商代配送退货通知单
     */
    private String sourcetype;

    /**
     * 来源单据编号
     */
    private String sourceid;

    /**
     * 供应商编号
     */
    private String supplierid;

    /**
     * 客户编号
     */
    private String customerid;

    /**
     * 总店客户
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
     * 所属部门
     */
    private String deptid;

    /**
     * 仓库编号
     */
    private String storageid;

    /**
     * 总体积(M*3)
     */
    private BigDecimal totalvolume;

    /**
     * 总重量（KG）
     */
    private BigDecimal totalweight;

    /**
     * 总箱数
     */
    private BigDecimal totalbox;

    /**
     * 总金额
     */
    private BigDecimal totalamount;

    /**
     * 是否验收1是0否
     */
    private String ischeck;

    /**
     * 验收人编号
     */
    private String checkuserid;

    /**
     * 验收人名称
     */
    private String checkname;

    /**
     * 验收时间
     */
    private Date checktime;

    /**
     * '配货状态0未配货1配货中2已配货3配货完毕'
     */
    private String isdelivery;

    private String customername;
    private String suppliername;
    private String storagename;
    private List<StorageDeliveryOutDetail> detailList;
    
    
    public List<StorageDeliveryOutDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<StorageDeliveryOutDetail> detailList) {
		this.detailList = detailList;
	}

	public String getPcustomername() {
		return pcustomername;
	}

	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}

	public String getStoragename() {
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
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
     * @return 单据类型1供应商配送退货出库单2客户配送出库单
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            单据类型1供应商配送退货出库单2客户配送出库单
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * @return 来源类型0无来源1供应商代配送客户订单2供应商代配送退货通知单
     */
    public String getSourcetype() {
        return sourcetype;
    }

    /**
     * @param sourcetype 
	 *            来源类型0无来源1供应商代配送客户订单2供应商代配送退货通知单
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
     * @return 供应商编号
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编号
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
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
     * @return 所属部门
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            所属部门
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 仓库编号
     */
    public String getStorageid() {
        return storageid;
    }

    /**
     * @param storageid 
	 *            仓库编号
     */
    public void setStorageid(String storageid) {
        this.storageid = storageid == null ? null : storageid.trim();
    }

    /**
     * @return 总体积(M*3)
     */
    public BigDecimal getTotalvolume() {
        return totalvolume;
    }

    /**
     * @param totalvolume 
	 *            总体积(M*3)
     */
    public void setTotalvolume(BigDecimal totalvolume) {
        this.totalvolume = totalvolume;
    }

    /**
     * @return 总重量（KG）
     */
    public BigDecimal getTotalweight() {
        return totalweight;
    }

    /**
     * @param totalweight 
	 *            总重量（KG）
     */
    public void setTotalweight(BigDecimal totalweight) {
        this.totalweight = totalweight;
    }

    /**
     * @return 总箱数
     */
    public BigDecimal getTotalbox() {
        return totalbox;
    }

    /**
     * @param totalbox 
	 *            总箱数
     */
    public void setTotalbox(BigDecimal totalbox) {
        this.totalbox = totalbox;
    }

    /**
     * @return 总金额
     */
    public BigDecimal getTotalamount() {
        return totalamount;
    }

    /**
     * @param totalamount 
	 *            总金额
     */
    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    /**
     * @return 是否验收1是0否
     */
    public String getIscheck() {
        return ischeck;
    }

    /**
     * @param ischeck 
	 *            是否验收1是0否
     */
    public void setIscheck(String ischeck) {
        this.ischeck = ischeck == null ? null : ischeck.trim();
    }

    /**
     * @return 验收人编号
     */
    public String getCheckuserid() {
        return checkuserid;
    }

    /**
     * @param checkuserid 
	 *            验收人编号
     */
    public void setCheckuserid(String checkuserid) {
        this.checkuserid = checkuserid == null ? null : checkuserid.trim();
    }

    /**
     * @return 验收人名称
     */
    public String getCheckname() {
        return checkname;
    }

    /**
     * @param checkname 
	 *            验收人名称
     */
    public void setCheckname(String checkname) {
        this.checkname = checkname == null ? null : checkname.trim();
    }

    /**
     * @return 验收时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getChecktime() {
        return checktime;
    }

    /**
     * @param checktime 
	 *            验收时间
     */
    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

	@Override
	public String toString() {
		return "StorageDeliveryOut [id=" + id + ", businessdate=" + businessdate + ", status=" + status + ", remark=" + remark + ", adduserid=" + adduserid + ", addusername=" + addusername
				+ ", adddeptid=" + adddeptid + ", adddeptname=" + adddeptname + ", addtime=" + addtime + ", modifyuserid=" + modifyuserid + ", modifyusername=" + modifyusername + ", modifytime="
				+ modifytime + ", audituserid=" + audituserid + ", auditusername=" + auditusername + ", audittime=" + audittime + ", stopuserid=" + stopuserid + ", stopusername=" + stopusername
				+ ", stoptime=" + stoptime + ", closetime=" + closetime + ", printtimes=" + printtimes + ", billtype=" + billtype + ", sourcetype=" + sourcetype + ", sourceid=" + sourceid
				+ ", supplierid=" + supplierid + ", customerid=" + customerid + ", pcustomerid=" + pcustomerid + ", pcustomername=" + pcustomername + ", customersort=" + customersort + ", deptid="
				+ deptid + ", storageid=" + storageid + ", totalvolume=" + totalvolume + ", totalweight=" + totalweight + ", totalbox=" + totalbox + ", totalamount=" + totalamount + ", ischeck="
				+ ischeck + ", checkuserid=" + checkuserid + ", checkname=" + checkname + ", checktime=" + checktime + ", customername=" + customername + ", suppliername=" + suppliername
				+ ", storagename=" + storagename + "]";
	}

    public String getIsdelivery() {
        return isdelivery;
    }

    public void setIsdelivery(String isdelivery) {
        this.isdelivery = isdelivery;
    }

    public String getCustomerbill() {
        return customerbill;
    }

    public void setCustomerbill(String customerbill) {
        this.customerbill = customerbill;
    }
}