/**
 * @(#)SalesGoods.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 15, 2013 chenwei 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 销售商品信息（报表使用）
 * @author chenwei
 */
public class SalesGoods implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6317178402750858944L;
	/**
	 * 发货单编号
	 */
	private String id;
	/**
	 * 客户编号
	 */
	private String customerid;
	/**
	 * 销售部门
	 */
	private String salesdept;
	/**
	 * 客户业务员
	 */
	private String salesuser;
	/**
	 * 商品编号
	 */
	private String goodsid;
	
	/**
	 * 主单位编号
	 */
	private String unitid;
	/**
	 * 主单位名称
	 */
	private String unitname;
	/**
	 * 发货数量
	 */
	private BigDecimal unitnum;
	/**
	 * 发货金额
	 */
	private BigDecimal taxamount;
	/**
	 * 发货无税金额
	 */
	private BigDecimal notaxamount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getSalesdept() {
		return salesdept;
	}
	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}
	public String getSalesuser() {
		return salesuser;
	}
	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public BigDecimal getUnitnum() {
		return unitnum;
	}
	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}
	public BigDecimal getTaxamount() {
		return taxamount;
	}
	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}
	public BigDecimal getNotaxamount() {
		return notaxamount;
	}
	public void setNotaxamount(BigDecimal notaxamount) {
		this.notaxamount = notaxamount;
	} 
	
	
}

