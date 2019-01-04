/**
 * @(#)ImportSalesOrder.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 25, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.sales.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class ImportSalesOrder implements Serializable{

private static final long serialVersionUID = 1L;
	
	/**
	 * 客户编码
	 */
	private String customerid;
	/**
	 * 客户名称
	 */
	private String customername;
	
	/**
	 * 销售部门
	 */
	private String salesdept;
	
	/**
	 * 销售部门名称
	 */
	private String salesdeptname;
	
	/**
	 * 客户业务员
	 */
	private String salesuser;
	
	/**
	 * 客户业务员名称
	 */
	private String salesusername;
	
	/**
	 * 业务日期
	 */
	private String businessdate;
	/**
	 * 商品编码
	 */
	private String goodsid;
	/**
	 * 商品店内码
	 */
	private String customergoodsid;
	/**
	 * 商品名称
	 */
	private String goodsname;
	
	/**
	 * 商品助记符
	 */
	private String spell;
	
	/**
	 * 条形码
	 */
	private String barcode;
	
	/**
	 * 主数量
	 */
	private BigDecimal unitnum;
	
	/**
	 * 辅数量描述
	 */
	private String auxnumdetail;
	
	/**
	 * 含税单价
	 */
	private BigDecimal taxprice;
	
	/**
	 * 含税金额
	 */
	private BigDecimal taxamount;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 来源单号/客户单号
	 * @return
	 */
	private String sourceid;

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public BigDecimal getUnitnum() {
		return unitnum;
	}

	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}

	public String getAuxnumdetail() {
		return auxnumdetail;
	}

	public void setAuxnumdetail(String auxnumdetail) {
		this.auxnumdetail = auxnumdetail;
	}

	public BigDecimal getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
	}

	public BigDecimal getTaxamount() {
		return taxamount;
	}

	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}

	public String getBusinessdate() {
		return businessdate;
	}

	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}

	public String getCustomergoodsid() {
		return customergoodsid;
	}

	public void setCustomergoodsid(String customergoodsid) {
		this.customergoodsid = customergoodsid;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSalesuser() {
		return salesuser;
	}

	public void setSalesuser(String salesuser) {
		this.salesuser = salesuser;
	}

	public String getSalesusername() {
		return salesusername;
	}

	public void setSalesusername(String salesusername) {
		this.salesusername = salesusername;
	}

	public String getSalesdept() {
		return salesdept;
	}

	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}

	public String getSalesdeptname() {
		return salesdeptname;
	}

	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
}

