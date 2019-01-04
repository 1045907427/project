/**
 * @(#)SalesCarReport.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 21, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class SalesCarReport implements Serializable{

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
	 * 客户助记符
	 */
	private String shortcode;
	 /**
     * 总店客户
     */
    private String pcustomerid;
    /**
     * 总店客户名称
     */
    private String pcustomername;
	/**
	 * 客户分类编码
	 */
	private String customersort;
	/**
	 * 客户分类名称
	 */
	private String customersortname;
	
	/**
	 * 销售区域
	 */
	private String salesarea;
	
	/**
	 * 销售区域名称
	 */
	private String salesareaname;
	/**
	 * 销售部门
	 */
	private String salesdept;
	/**
	 * 销售部门名称
	 */
	private String salesdeptname;
	
	/**
	 * 销售业务员
	 */
	private String salesuser;
	
	/**
	 * 销售业务员名称
	 */
	private String salesusername;

	/**
	 * 车销业务员
	 */
	private String caruser;

	/**
	 * 车销业务员名称
	 */
	private String carusername;
	/**
	 * 商品编码
	 */
	private String goodsid;
	
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
	 * 品牌编码
	 */
	private String brandid;
	
	/**
	 * 品牌名称
	 */
	private String brandname;
	
	/**
	 * 品牌业务员
	 */
	private String branduser;
	
	/**
	 * 品牌业务员名称
	 */
	private String brandusername;
	
	/**
	 * 品牌部门
	 */
	private String branddept;
	
	/**
	 * 品牌部门名称
	 */
	private String branddeptname;
	
	/**
	 * 主单位编码
	 */
	private String unitid;
	/**
	 * 主单位名称
	 */
	private String unitname;
	
	/**
	 * 辅单位编码
	 */
	private String auxunitid;
	
	/**
	 * 辅单位名称
	 */
	private String auxunitname;
	
	/**
	 * 含税单价
	 */
	private BigDecimal taxprice;
	/**
	 * 未税单价
	 */
	private BigDecimal notaxprice;
	/**
	 * 成本单价
	 */
	private BigDecimal costprice;
	
	/**
	 * 初始单价
	 */
	private BigDecimal initprice;
	
	/**
	 * 数量
	 */
	private BigDecimal unitnum;
	
	private Integer unitnumInteger;
	
	/**
	 * 数量(辅计量)
	 */
	private BigDecimal auxnum;
	
	private Integer auxInteger;
	
	/**
	 * 主单位余数
	 */
	private BigDecimal overnum;
	
	private Integer overnumInteger;
	
	/**
	 * 含税金额
	 */
	private BigDecimal taxamount;
	
	/**
	 * 未税金额
	 */
	private BigDecimal notaxamount;
	
	/**
	 * 税额
	 */
	private BigDecimal tax;

	/**
	 * 成本金额
	 */
	private BigDecimal costamount;

	/**
	 * 毛利金额
	 */
	private BigDecimal marginamount;

	/**
	 * 实际毛利率
	 */
	private BigDecimal realrate;

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

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getCustomersort() {
		return customersort;
	}

	public void setCustomersort(String customersort) {
		this.customersort = customersort;
	}

	public String getCustomersortname() {
		return customersortname;
	}

	public void setCustomersortname(String customersortname) {
		this.customersortname = customersortname;
	}

	public String getSalesarea() {
		return salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getSalesareaname() {
		return salesareaname;
	}

	public void setSalesareaname(String salesareaname) {
		this.salesareaname = salesareaname;
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

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getBranduser() {
		return branduser;
	}

	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}

	public String getBrandusername() {
		return brandusername;
	}

	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}

	public String getBranddept() {
		return branddept;
	}

	public void setBranddept(String branddept) {
		this.branddept = branddept;
	}

	public String getBranddeptname() {
		return branddeptname;
	}

	public void setBranddeptname(String branddeptname) {
		this.branddeptname = branddeptname;
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

	public String getAuxunitid() {
		return auxunitid;
	}

	public void setAuxunitid(String auxunitid) {
		this.auxunitid = auxunitid;
	}

	public String getAuxunitname() {
		return auxunitname;
	}

	public void setAuxunitname(String auxunitname) {
		this.auxunitname = auxunitname;
	}

	public BigDecimal getTaxprice() {
		return taxprice;
	}

	public void setTaxprice(BigDecimal taxprice) {
		this.taxprice = taxprice;
	}

	public BigDecimal getNotaxprice() {
		return notaxprice;
	}

	public void setNotaxprice(BigDecimal notaxprice) {
		this.notaxprice = notaxprice;
	}

	public BigDecimal getCostprice() {
		return costprice;
	}

	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}

	public BigDecimal getInitprice() {
		return initprice;
	}

	public void setInitprice(BigDecimal initprice) {
		this.initprice = initprice;
	}

	public BigDecimal getUnitnum() {
		return unitnum;
	}

	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}

	public Integer getUnitnumInteger() {
		return unitnumInteger;
	}

	public void setUnitnumInteger(Integer unitnumInteger) {
		this.unitnumInteger = unitnumInteger;
	}

	public BigDecimal getAuxnum() {
		return auxnum;
	}

	public void setAuxnum(BigDecimal auxnum) {
		this.auxnum = auxnum;
	}

	public Integer getAuxInteger() {
		return auxInteger;
	}

	public void setAuxInteger(Integer auxInteger) {
		this.auxInteger = auxInteger;
	}

	public BigDecimal getOvernum() {
		return overnum;
	}

	public void setOvernum(BigDecimal overnum) {
		this.overnum = overnum;
	}

	public Integer getOvernumInteger() {
		return overnumInteger;
	}

	public void setOvernumInteger(Integer overnumInteger) {
		this.overnumInteger = overnumInteger;
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

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public String getPcustomerid() {
		return pcustomerid;
	}

	public void setPcustomerid(String pcustomerid) {
		this.pcustomerid = pcustomerid;
	}

	public String getPcustomername() {
		return pcustomername;
	}

	public void setPcustomername(String pcustomername) {
		this.pcustomername = pcustomername;
	}

	public BigDecimal getCostamount() {
		return costamount;
	}

	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}

	public BigDecimal getMarginamount() {
		return marginamount;
	}

	public void setMarginamount(BigDecimal marginamount) {
		this.marginamount = marginamount;
	}

	public String getCaruser() {
		return caruser;
	}

	public void setCaruser(String caruser) {
		this.caruser = caruser;
	}

	public String getCarusername() {
		return carusername;
	}

	public void setCarusername(String carusername) {
		this.carusername = carusername;
	}

	public BigDecimal getRealrate() {
		return realrate;
	}

	public void setRealrate(BigDecimal realrate) {
		this.realrate = realrate;
	}
}

