/**
 * @(#)PerformanceCheck.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-8 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 业绩考核明
 * @author zhanghonghui
 */
public class PerformanceKPISummary implements Serializable {

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
     * 销售部门编号
     */
    private String salesdeptid;
    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 状态
     */
    private String status;
    /**
     * 部门名称
     */
    private String deptname;

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
     * 修改时间
     */
    private Date modifytime;

    /**
     * 来源：0手动添加，1导入
     */
    private String sourcefrome;

    /**
     * 销售额
     */
    private BigDecimal salesamount;

    /**
     * 签呈返还
     */
    private BigDecimal jcfhamount;

    /**
     * 含税毛利
     */
    private BigDecimal hsmlamount;

    /**
     * 小计
     */
    private BigDecimal xjamount;

    /**
     * 小计率，小计/销售额
     */
    private BigDecimal xjrate;

    /**
     * 费用额
     */
    private BigDecimal fyamount;

    /**
     * 费用额率,费用额/销售额
     */
    private BigDecimal fyrate;

    /**
     * 净利
     */
    private BigDecimal jlamount;

    /**
     * 净利率：净利/销售额
     */
    private BigDecimal jlrate;

    /**
     * 平均期末库存额
     */
    private BigDecimal pjqmkcamount;

    /**
     * 平均库存周转天数
     */
    private BigDecimal pjkczzday;

    /**
     * 平均资金占用额
     */
    private BigDecimal pjzjzyamount;

    /**
     * 资金利润率
     */
    private BigDecimal zjlrrate;

    /**
     * 期末代垫费用余额	
     */
    private BigDecimal qmddfyyeamount;

    /**
     * 代垫费占用率％	
     */
    private BigDecimal ddfyzyrate;

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

    /**
     * @return 销售额
     */
    public BigDecimal getSalesamount() {
        return salesamount;
    }

    /**
     * @param salesamount 
	 *            销售额
     */
    public void setSalesamount(BigDecimal salesamount) {
        this.salesamount = salesamount;
    }

    /**
     * @return 签呈返还
     */
    public BigDecimal getJcfhamount() {
        return jcfhamount;
    }

    /**
     * @param jcfhamount 
	 *            签呈返还
     */
    public void setJcfhamount(BigDecimal jcfhamount) {
        this.jcfhamount = jcfhamount;
    }

    /**
     * @return 含税毛利
     */
    public BigDecimal getHsmlamount() {
        return hsmlamount;
    }

    /**
     * @param hsmlamount 
	 *            含税毛利
     */
    public void setHsmlamount(BigDecimal hsmlamount) {
        this.hsmlamount = hsmlamount;
    }

    /**
     * @return 小计
     */
    public BigDecimal getXjamount() {
        return xjamount;
    }

    /**
     * @param xjamount 
	 *            小计
     */
    public void setXjamount(BigDecimal xjamount) {
        this.xjamount = xjamount;
    }

    /**
     * @return 小计率，小计/销售额
     */
    public BigDecimal getXjrate() {
        return xjrate;
    }

    /**
     * @param xjrate 
	 *            小计率，小计/销售额
     */
    public void setXjrate(BigDecimal xjrate) {
        this.xjrate = xjrate;
    }

    /**
     * @return 费用额
     */
    public BigDecimal getFyamount() {
        return fyamount;
    }

    /**
     * @param fyamount 
	 *            费用额
     */
    public void setFyamount(BigDecimal fyamount) {
        this.fyamount = fyamount;
    }

    /**
     * @return 费用额率,费用额/销售额
     */
    public BigDecimal getFyrate() {
        return fyrate;
    }

    /**
     * @param fyrate 
	 *            费用额率,费用额/销售额
     */
    public void setFyrate(BigDecimal fyrate) {
        this.fyrate = fyrate;
    }

    /**
     * @return 净利
     */
    public BigDecimal getJlamount() {
        return jlamount;
    }

    /**
     * @param jlamount 
	 *            净利
     */
    public void setJlamount(BigDecimal jlamount) {
        this.jlamount = jlamount;
    }

    /**
     * @return 净利率：净利/销售额
     */
    public BigDecimal getJlrate() {
        return jlrate;
    }

    /**
     * @param jlrate 
	 *            净利率：净利/销售额
     */
    public void setJlrate(BigDecimal jlrate) {
        this.jlrate = jlrate;
    }

    /**
     * @return 平均期末库存额
     */
    public BigDecimal getPjqmkcamount() {
        return pjqmkcamount;
    }

    /**
     * @param pjqmkcamount 
	 *            平均期末库存额
     */
    public void setPjqmkcamount(BigDecimal pjqmkcamount) {
        this.pjqmkcamount = pjqmkcamount;
    }

    /**
     * @return 平均库存周转天数
     */
    public BigDecimal getPjkczzday() {
        return pjkczzday;
    }

    /**
     * @param pjkczzday 
	 *            平均库存周转天数
     */
    public void setPjkczzday(BigDecimal pjkczzday) {
        this.pjkczzday = pjkczzday;
    }

    /**
     * @return 平均资金占用额
     */
    public BigDecimal getPjzjzyamount() {
        return pjzjzyamount;
    }

    /**
     * @param pjzjzyamount 
	 *            平均资金占用额
     */
    public void setPjzjzyamount(BigDecimal pjzjzyamount) {
        this.pjzjzyamount = pjzjzyamount;
    }

    /**
     * @return 资金利润率
     */
    public BigDecimal getZjlrrate() {
        return zjlrrate;
    }

    /**
     * @param zjlrrate 
	 *            资金利润率
     */
    public void setZjlrrate(BigDecimal zjlrrate) {
        this.zjlrrate = zjlrrate;
    }

    /**
     * @return 期末代垫费用余额	
     */
    public BigDecimal getQmddfyyeamount() {
        return qmddfyyeamount;
    }

    /**
     * @param qmddfyyeamount 
	 *            期末代垫费用余额	
     */
    public void setQmddfyyeamount(BigDecimal qmddfyyeamount) {
        this.qmddfyyeamount = qmddfyyeamount;
    }

    /**
     * @return 代垫费占用率％	
     */
    public BigDecimal getDdfyzyrate() {
        return ddfyzyrate;
    }

    /**
     * @param ddfyzyrate 
	 *            代垫费占用率％	
     */
    public void setDdfyzyrate(BigDecimal ddfyzyrate) {
        this.ddfyzyrate = ddfyzyrate;
    }

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getAudittime() {
		return audittime;
	}

	public void setAudittime(Date audittime) {
		this.audittime = audittime;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
	}

    public String getSalesdeptid() {
        return salesdeptid;
    }

    public void setSalesdeptid(String salesdeptid) {
        this.salesdeptid = salesdeptid == null ? null : salesdeptid.trim();
    }
}

