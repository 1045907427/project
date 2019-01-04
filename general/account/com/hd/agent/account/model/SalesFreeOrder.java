package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 销售放单管理
 * @author 陈伟
 */
public class SalesFreeOrder implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6218313922889567352L;

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
     * 客户编号
     */
    private String customerid;
    
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 申请人编号
     */
    private String applyid;
    
    /**
     * 申请人名称
     */
    private String applyname;

    /**
     * 申请人部门编号
     */
    private String applydeptid;

    /**
     * 放单类型1按审核当天放2按指定单据号放
     */
    private String freetype;
    
    /**
     * 放单类型名称
     */
    private String freetypename;

    /**
     * 相关订单编号
     */
    private String orderid;

    /**
     * 超账期原因编号
     */
    private String pastduereasonid;

    /**
     * 超账期原因
     */
    private String overreason;

    /**
     * 承诺到款金额
     */
    private BigDecimal commitmentamount;

    /**
     * 承诺到款时间
     */
    private String commitmentdate;
    
    /**
     * 应收款
     */
    private BigDecimal saleamount;
    
    /**
     * 正常期金额
     */
    private BigDecimal unpassamount;
    
    /**
     * 超账期金额
     */
    private BigDecimal totalpassamount;

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
     * @return 申请人编号
     */
    public String getApplyid() {
        return applyid;
    }

    /**
     * @param applyid 
	 *            申请人编号
     */
    public void setApplyid(String applyid) {
        this.applyid = applyid == null ? null : applyid.trim();
    }

    /**
     * @return 申请人部门编号
     */
    public String getApplydeptid() {
        return applydeptid;
    }

    /**
     * @param applydeptid 
	 *            申请人部门编号
     */
    public void setApplydeptid(String applydeptid) {
        this.applydeptid = applydeptid == null ? null : applydeptid.trim();
    }

    /**
     * @return 放单类型1按审核当天放2按指定单据号放
     */
    public String getFreetype() {
        return freetype;
    }

    /**
     * @param freetype 
	 *            放单类型1按审核当天放2按指定单据号放
     */
    public void setFreetype(String freetype) {
        this.freetype = freetype == null ? null : freetype.trim();
    }

    /**
     * @return 相关订单编号
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * @param orderid 
	 *            相关订单编号
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    /**
     * @return 超账期原因编号
     */
    public String getPastduereasonid() {
        return pastduereasonid;
    }

    /**
     * @param pastduereasonid 
	 *            超账期原因编号
     */
    public void setPastduereasonid(String pastduereasonid) {
        this.pastduereasonid = pastduereasonid == null ? null : pastduereasonid.trim();
    }

    /**
     * @return 超账期原因
     */
    public String getOverreason() {
        return overreason;
    }

    /**
     * @param overreason 
	 *            超账期原因
     */
    public void setOverreason(String overreason) {
        this.overreason = overreason == null ? null : overreason.trim();
    }

    /**
     * @return 承诺到款金额
     */
    public BigDecimal getCommitmentamount() {
        return commitmentamount;
    }

    /**
     * @param commitmentamount 
	 *            承诺到款金额
     */
    public void setCommitmentamount(BigDecimal commitmentamount) {
        this.commitmentamount = commitmentamount;
    }

    /**
     * @return 承诺到款时间
     */
    public String getCommitmentdate() {
        return commitmentdate;
    }

    /**
     * @param commitmentdate 
	 *            承诺到款时间
     */
    public void setCommitmentdate(String commitmentdate) {
        this.commitmentdate = commitmentdate == null ? null : commitmentdate.trim();
    }

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getFreetypename() {
		return freetypename;
	}

	public void setFreetypename(String freetypename) {
		this.freetypename = freetypename;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public BigDecimal getSaleamount() {
		return saleamount;
	}

	public void setSaleamount(BigDecimal saleamount) {
		this.saleamount = saleamount;
	}

	public BigDecimal getUnpassamount() {
		return unpassamount;
	}

	public void setUnpassamount(BigDecimal unpassamount) {
		this.unpassamount = unpassamount;
	}

	public BigDecimal getTotalpassamount() {
		return totalpassamount;
	}

	public void setTotalpassamount(BigDecimal totalpassamount) {
		this.totalpassamount = totalpassamount;
	}

	public String getApplyname() {
		return applyname;
	}

	public void setApplyname(String applyname) {
		this.applyname = applyname;
	}

}