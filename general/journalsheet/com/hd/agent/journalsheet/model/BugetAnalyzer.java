package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class BugetAnalyzer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 预算编号
     */
    private String budgetid;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 部门
     */
    private String deptid;

    /**
     * 供应商编号
     */
    private String supplierid;

    /**
     * 月份
     */
    private String month;

    /**
     * 年份
     */
    private String year;
    
    private String yearMonth;

	/**
     * 预算金额
     */
    private BigDecimal budgetnum;

    /**
     * 预算类型：销售目标、存货金额、应收金额、代垫金额、应付金额、客费金额
     */
    private Integer bugettype;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 录入时间
     */
    private Date addtime;

    /**
     * 录入人
     */
    private String adduserid;

    
    
    private String brandname;
    private String deptname;
    private String suppliername;
    private String addusername;
    
    
    
    public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

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
     * @return 预算编号
     */
    public String getBudgetid() {
        return budgetid;
    }

    /**
     * @param budgetid 
	 *            预算编号
     */
    public void setBudgetid(String budgetid) {
        this.budgetid = budgetid == null ? null : budgetid.trim();
    }

    /**
     * @return 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand 
	 *            品牌
     */
    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    /**
     * @return 部门
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            部门
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
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
     * @return 月份
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month 
	 *            月份
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    /**
     * @return 预算金额
     */
    public BigDecimal getBudgetnum() {
        return budgetnum;
    }

    /**
     * @param budgetnum 
	 *            预算金额
     */
    public void setBudgetnum(BigDecimal budgetnum) {
        this.budgetnum = budgetnum;
    }

    /**
     * @return 预算类型：销售目标、存货金额、应收金额、代垫金额、应付金额、客费金额
     */
    public Integer getBugettype() {
        return bugettype;
    }

    /**
     * @param bugettype 
	 *            预算类型：销售目标、存货金额、应收金额、代垫金额、应付金额、客费金额
     */
    public void setBugettype(Integer bugettype) {
        this.bugettype = bugettype;
    }

    /**
     * @return 状态
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态
     */
    public void setState(Integer state) {
        this.state = state;
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
     * @return 录入时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            录入时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 录入人
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            录入人
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }
    
    
    public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}