package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BugetAnalyzerGroup implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 预算类型：销售目标、存货金额、应收金额、代垫金额、应付金额、客费金额
     */
    private Integer bugettype;
    
    
    
    private String brandname;
    private String deptname;
    private String suppliername;
    private BigDecimal month01;
    private BigDecimal month02;
    private BigDecimal month03;
    private BigDecimal month04;
    private BigDecimal month05;
    private BigDecimal month06;
    private BigDecimal month07;
    private BigDecimal month08;
    private BigDecimal month09;
    private BigDecimal month10;
    private BigDecimal month11;
    private BigDecimal month12;
    public BugetAnalyzerGroup(){
    	this.month01 = new BigDecimal(0);
    	this.month02 = new BigDecimal(0);
    	this.month03 = new BigDecimal(0);
    	this.month04 = new BigDecimal(0);
    	this.month05 = new BigDecimal(0);
    	this.month06 = new BigDecimal(0);
    	this.month07 = new BigDecimal(0);
    	this.month08 = new BigDecimal(0);
    	this.month09 = new BigDecimal(0);
    	this.month10 = new BigDecimal(0);
    	this.month11 = new BigDecimal(0);
    	this.month12 = new BigDecimal(0);
    }
    
    
	public BigDecimal getMonth01() {
		return month01;
	}


	public void setMonth01(BigDecimal month01) {
		this.month01 = month01;
	}


	public BigDecimal getMonth02() {
		return month02;
	}


	public void setMonth02(BigDecimal month02) {
		this.month02 = month02;
	}


	public BigDecimal getMonth03() {
		return month03;
	}


	public void setMonth03(BigDecimal month03) {
		this.month03 = month03;
	}


	public BigDecimal getMonth04() {
		return month04;
	}


	public void setMonth04(BigDecimal month04) {
		this.month04 = month04;
	}


	public BigDecimal getMonth05() {
		return month05;
	}


	public void setMonth05(BigDecimal month05) {
		this.month05 = month05;
	}


	public BigDecimal getMonth06() {
		return month06;
	}


	public void setMonth06(BigDecimal month06) {
		this.month06 = month06;
	}


	public BigDecimal getMonth07() {
		return month07;
	}


	public void setMonth07(BigDecimal month07) {
		this.month07 = month07;
	}


	public BigDecimal getMonth08() {
		return month08;
	}


	public void setMonth08(BigDecimal month08) {
		this.month08 = month08;
	}


	public BigDecimal getMonth09() {
		return month09;
	}


	public void setMonth09(BigDecimal month09) {
		this.month09 = month09;
	}


	public BigDecimal getMonth10() {
		return month10;
	}


	public void setMonth10(BigDecimal month10) {
		this.month10 = month10;
	}


	public BigDecimal getMonth11() {
		return month11;
	}


	public void setMonth11(BigDecimal month11) {
		this.month11 = month11;
	}


	public BigDecimal getMonth12() {
		return month12;
	}


	public void setMonth12(BigDecimal month12) {
		this.month12 = month12;
	}


	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public Integer getBugettype() {
		return bugettype;
	}
	public void setBugettype(Integer bugettype) {
		this.bugettype = bugettype;
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


	@Override
	public String toString() {
		return "BugetAnalyzerGroup [brand=" + brand + ", deptid=" + deptid + ", supplierid=" + supplierid + ", bugettype=" + bugettype + ", brandname=" + brandname + ", deptname=" + deptname
				+ ", suppliername=" + suppliername + ", month01=" + month01 + ", month02=" + month02 + ", month03=" + month03 + ", month04=" + month04 + ", month05=" + month05 + ", month06="
				+ month06 + ", month07=" + month07 + ", month08=" + month08 + ", month09=" + month09 + ", month10=" + month10 + ", month11=" + month11 + ", month12=" + month12 + "]";
	}
    
	
}