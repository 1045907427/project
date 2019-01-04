/**
 * @(#)SalesCustomerTargetReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-4-18 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 销售客户目标考核报表
 * @author zhanghonghui
 */
public class SalesCustomerTargetReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4948226528075038413L;
	/**
	 * 品牌业务员
	 */
	private String branduser;
	/**
	 * 客户
	 */
	private String customerid;
	/**
	 * 开始时间
	 */
	private String startdate;
	/**
	 * 结束时间
	 */
	private String enddate;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 品牌部门
	 */
	private String branddept;
	/**
	 * 销售目标
	 */
	private BigDecimal targets;
	/**
	 * 下个月销售目标
	 */
	private BigDecimal nweektargets;
	
	public String getBranduser() {
		return branduser;
	}
	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBranddept() {
		return branddept;
	}
	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}
	public BigDecimal getTargets() {
		return targets;
	}
	public void setTargets(BigDecimal targets) {
		this.targets = targets;
	}
	public BigDecimal getNweektargets() {
		return nweektargets;
	}
	public void setNweektargets(BigDecimal nweektargets) {
		this.nweektargets = nweektargets;
	}
}

