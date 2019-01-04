package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 费用主表
 * @author zhanghonghui
 * 时间 2014-6-26
 */
public class DepartmentCosts implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8382292640275619754L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 业务日期-年
     */
    private String year;

    /**
     * 业务日期-月
     */
    private String month;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 部门编号
     */
    private String deptid;
    /**
     * 部门名称
     */
    private String deptname;

    /**
     * 状态
     */
    private String status;
    /**
     * 状态名
     */
    private String statusname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加者编号
     */
    private String adduserid;

    /**
     * 添加者名称
     */
    private String addusername;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 最新修改者编号
     */
    private String modifyuserid;

    /**
     * 最新修改者名称
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 审核者编号
     */
    private String audituserid;

    /**
     * 审核者名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 来源：0手动添加，1导入
     */
    private String sourcefrome;
    /**
     * 各科目费用
     */
    List<DepartmentCostsDetail> deptCostsDetailList;   

    /**
     * 销售金额
     */
    private BigDecimal salesamount;

    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 业务日期-年
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year 
	 *            业务日期-年
     */
    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    /**
     * @return 业务日期-月
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month 
	 *            业务日期-月
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
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
     * @return 部门编号
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            部门编号
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
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
     * @return 添加者编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加者编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加者名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加者名称
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最新修改者编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最新修改者编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最新修改者名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最新修改者名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 审核者编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核者编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核者名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核者名称
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
     * @return 来源：0手动添加，1导入
     */
    public String getSourcefrome() {
        return sourcefrome;
    }

    /**
     * @param sourcefrome 
	 *            来源：0手动添加，1导入
     */
    public void setSourcefrome(String sourcefrome) {
        this.sourcefrome = sourcefrome == null ? null : sourcefrome.trim();
    }

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname == null ? null : deptname.trim();
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname == null ? null : statusname.trim();
	}

	public List<DepartmentCostsDetail> getDeptCostsDetailList() {
		return deptCostsDetailList;
	}

	public void setDeptCostsDetailList(
			List<DepartmentCostsDetail> deptCostsDetailList) {
		this.deptCostsDetailList = deptCostsDetailList;
	}

	public BigDecimal getSalesamount() {
		return salesamount;
	}

	public void setSalesamount(BigDecimal salesamount) {
		this.salesamount = salesamount;
	}
}