package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 部门日常费用分摊到供应商
 * @author hdit001
 */
public class DeptDailyCostSupplier implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 业务日期-年
     */
    private Integer year;

    /**
     * 业务日期-月
     */
    private Integer month;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;
    /**
     * 部门编号
     */
    private String deptid;
    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 费用科目
     */
    private String costsort;
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
    public Integer getYear() {
        return year;
    }

    /**
     * @param year 
	 *            业务日期-年
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return 业务日期-月
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month 
	 *            业务日期-月
     */
    public void setMonth(Integer month) {
        this.month = month;
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
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getCostsort() {
		return costsort;
	}

	public void setCostsort(String costsort) {
		this.costsort = costsort;
	}
    
}