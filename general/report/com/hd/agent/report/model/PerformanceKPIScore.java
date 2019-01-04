package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PerformanceKPIScore implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 来源：0手动添加，1导入
     */
    private String sourcefrome;

    /**
     * 销售
     */
    private BigDecimal salesamountindex;

    /**
     * 销售额
     */
    private BigDecimal salesamount;

    /**
     * 销售金额得分
     */
    private BigDecimal salesamountscore;

    /**
     * 销售额指标总分
     */
    private BigDecimal salesamountindexscore;

    /**
     * 销售指标分值
     */
    private BigDecimal salesamountindexvalue;

    /**
     * 毛利额指标
     */
    private BigDecimal mlamountindex;

    /**
     * 毛利额
     */
    private BigDecimal mlamount;

    /**
     * 毛利额得分
     */
    private BigDecimal mlamountscore;

    /**
     * 毛利率指标
     */
    private BigDecimal mlrateindex;

    /**
     * 毛利率
     */
    private BigDecimal mlrate;

    /**
     * 毛利率得分
     */
    private BigDecimal mlratescore;

    /**
     * 毛利得分
     */
    private BigDecimal mlscore;

    /**
     * 毛利指标分
     */
    private BigDecimal mlindexscore;

    /**
     * 毛利指标分值
     */
    private BigDecimal mlindexvalue;

    /**
     * 库存周转日数指标
     */
    private BigDecimal kczlrsindex;

    /**
     * 库存周转日数
     */
    private BigDecimal kczlrs;

    /**
     * 库存周转得分
     */
    private BigDecimal kczlrsscore;

    /**
     * 库存周转指标分
     */
    private BigDecimal kczlindexscore;

    /**
     * 库存周转分值
     */
    private BigDecimal kczlindexvalue;

    /**
     * 费用率指标
     */
    private BigDecimal fyrateindex;

    /**
     * 费用率
     */
    private BigDecimal fyrate;

    /**
     * 费用率得分
     */
    private BigDecimal fyratescore;

    /**
     * 费用率指标分
     */
    private BigDecimal fyrateindexscore;

    /**
     * 费用率指标分值
     */
    private BigDecimal fyrateindexvalue;

    /**
     * 总得分
     */
    private BigDecimal totalscore;

    /**
     * 应得奖金
     */
    private BigDecimal bonus;

    public Integer getId() {
        return id;
    }

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
     * @return 销售额指标
     */
    public BigDecimal getSalesamountindex() {
        return salesamountindex;
    }

    /**
     * @param salesindex 
	 *            销售额指标
     */
    public void setSalesamountindex(BigDecimal salesamountindex) {
        this.salesamountindex = salesamountindex;
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
     * @return 销售金额得分
     */
    public BigDecimal getSalesamountscore() {
        return salesamountscore;
    }

    /**
     * @param salesamountscore 
	 *            销售金额得分
     */
    public void setSalesamountscore(BigDecimal salesamountscore) {
        this.salesamountscore = salesamountscore;
    }

    /**
     * @return 销售额指标总分
     */
    public BigDecimal getSalesamountindexscore() {
        return salesamountindexscore;
    }

    /**
     * @param salesamountindexscore 
	 *            销售额指标总分
     */
    public void setSalesamountindexscore(BigDecimal salesamountindexscore) {
        this.salesamountindexscore = salesamountindexscore;
    }

    /**
     * @return 销售指标分值
     */
    public BigDecimal getSalesamountindexvalue() {
        return salesamountindexvalue;
    }

    /**
     * @param salesamountindexvalue 
	 *            销售指标分值
     */
    public void setSalesamountindexvalue(BigDecimal salesamountindexvalue) {
        this.salesamountindexvalue = salesamountindexvalue;
    }

    /**
     * @return 毛利额指标
     */
    public BigDecimal getMlamountindex() {
        return mlamountindex;
    }

    /**
     * @param mlamountindex 
	 *            毛利额指标
     */
    public void setMlamountindex(BigDecimal mlamountindex) {
        this.mlamountindex = mlamountindex;
    }

    /**
     * @return 毛利额
     */
    public BigDecimal getMlamount() {
        return mlamount;
    }

    /**
     * @param mlamount 
	 *            毛利额
     */
    public void setMlamount(BigDecimal mlamount) {
        this.mlamount = mlamount;
    }

    /**
     * @return 毛利额得分
     */
    public BigDecimal getMlamountscore() {
        return mlamountscore;
    }

    /**
     * @param mlamountscore 
	 *            毛利额得分
     */
    public void setMlamountscore(BigDecimal mlamountscore) {
        this.mlamountscore = mlamountscore;
    }

    /**
     * @return 毛利率指标
     */
    public BigDecimal getMlrateindex() {
        return mlrateindex;
    }

    /**
     * @param mlrateindex 
	 *            毛利率指标
     */
    public void setMlrateindex(BigDecimal mlrateindex) {
        this.mlrateindex = mlrateindex;
    }

    /**
     * @return 毛利率
     */
    public BigDecimal getMlrate() {
        return mlrate;
    }

    /**
     * @param mlrate 
	 *            毛利率
     */
    public void setMlrate(BigDecimal mlrate) {
        this.mlrate = mlrate;
    }

    /**
     * @return 毛利率得分
     */
    public BigDecimal getMlratescore() {
        return mlratescore;
    }

    /**
     * @param mlratescore 
	 *            毛利率得分
     */
    public void setMlratescore(BigDecimal mlratescore) {
        this.mlratescore = mlratescore;
    }

    /**
     * @return 毛利得分
     */
    public BigDecimal getMlscore() {
        return mlscore;
    }

    /**
     * @param mlscore 
	 *            毛利得分
     */
    public void setMlscore(BigDecimal mlscore) {
        this.mlscore = mlscore;
    }

    /**
     * @return 毛利指标分
     */
    public BigDecimal getMlindexscore() {
        return mlindexscore;
    }

    /**
     * @param mlindexscore 
	 *            毛利指标分
     */
    public void setMlindexscore(BigDecimal mlindexscore) {
        this.mlindexscore = mlindexscore;
    }

    /**
     * @return 毛利指标分值
     */
    public BigDecimal getMlindexvalue() {
        return mlindexvalue;
    }

    /**
     * @param mlindexvalue 
	 *            毛利指标分值
     */
    public void setMlindexvalue(BigDecimal mlindexvalue) {
        this.mlindexvalue = mlindexvalue;
    }

    /**
     * @return 库存周转日数指标
     */
    public BigDecimal getKczlrsindex() {
        return kczlrsindex;
    }

    /**
     * @param kczlrsindex 
	 *            库存周转日数指标
     */
    public void setKczlrsindex(BigDecimal kczlrsindex) {
        this.kczlrsindex = kczlrsindex;
    }

    /**
     * @return 库存周转日数
     */
    public BigDecimal getKczlrs() {
        return kczlrs;
    }

    /**
     * @param kczlrs 
	 *            库存周转日数
     */
    public void setKczlrs(BigDecimal kczlrs) {
        this.kczlrs = kczlrs;
    }

    /**
     * @return 库存周转得分
     */
    public BigDecimal getKczlrsscore() {
        return kczlrsscore;
    }

    /**
     * @param kczlrsscore 
	 *            库存周转得分
     */
    public void setKczlrsscore(BigDecimal kczlrsscore) {
        this.kczlrsscore = kczlrsscore;
    }

    /**
     * @return 库存周转指标分
     */
    public BigDecimal getKczlindexscore() {
        return kczlindexscore;
    }

    /**
     * @param kczlindexscore 
	 *            库存周转指标分
     */
    public void setKczlindexscore(BigDecimal kczlindexscore) {
        this.kczlindexscore = kczlindexscore;
    }

    /**
     * @return 库存周转分值
     */
    public BigDecimal getKczlindexvalue() {
        return kczlindexvalue;
    }

    /**
     * @param kczlindexvalue 
	 *            库存周转分值
     */
    public void setKczlindexvalue(BigDecimal kczlindexvalue) {
        this.kczlindexvalue = kczlindexvalue;
    }

    /**
     * @return 费用率指标
     */
    public BigDecimal getFyrateindex() {
        return fyrateindex;
    }

    /**
     * @param fyrateindex 
	 *            费用率指标
     */
    public void setFyrateindex(BigDecimal fyrateindex) {
        this.fyrateindex = fyrateindex;
    }

    /**
     * @return 费用率
     */
    public BigDecimal getFyrate() {
        return fyrate;
    }

    /**
     * @param fyrate 
	 *            费用率
     */
    public void setFyrate(BigDecimal fyrate) {
        this.fyrate = fyrate;
    }

    /**
     * @return 费用率得分
     */
    public BigDecimal getFyratescore() {
        return fyratescore;
    }

    /**
     * @param fyratescore 
	 *            费用率得分
     */
    public void setFyratescore(BigDecimal fyratescore) {
        this.fyratescore = fyratescore;
    }

    /**
     * @return 费用率指标分
     */
    public BigDecimal getFyrateindexscore() {
        return fyrateindexscore;
    }

    /**
     * @param fyrateindexscore 
	 *            费用率指标分
     */
    public void setFyrateindexscore(BigDecimal fyrateindexscore) {
        this.fyrateindexscore = fyrateindexscore;
    }

    /**
     * @return 费用率指标分值
     */
    public BigDecimal getFyrateindexvalue() {
        return fyrateindexvalue;
    }

    /**
     * @param fyrateindexvalue 
	 *            费用率指标分值
     */
    public void setFyrateindexvalue(BigDecimal fyrateindexvalue) {
        this.fyrateindexvalue = fyrateindexvalue;
    }

    /**
     * @return 总得分
     */
    public BigDecimal getTotalscore() {
        return totalscore;
    }

    /**
     * @param totalscore 
	 *            总得分
     */
    public void setTotalscore(BigDecimal totalscore) {
        this.totalscore = totalscore;
    }

    /**
     * @return 应得奖金
     */
    public BigDecimal getBonus() {
        return bonus;
    }

    /**
     * @param bonus 
	 *            应得奖金
     */
    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
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
}