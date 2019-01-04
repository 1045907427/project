/**
 * @(#)DeliveryCustomerOutReport.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月16日 huangzhiqian 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class DeliveryCustomerOutReport implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 销售金额
	 */
	private BigDecimal saleprice;
	/**
	 * 成本金额
	 */
	private BigDecimal cost;
	/**
	 * 箱数
	 */
	private BigDecimal totalbox;
	/**
	 * 体积
	 */
	private BigDecimal volume;
	/**
	 * 重量
	 */
	private BigDecimal  weight;
	
	private String customerid;
	private String customername;
	
	private String goodsid;
	private String goodsname;
	
	private String brandid;
	private String brandname;
	
	private String goodssort;
	private String goodssortname;
	
	private String supplierid;
	private String suppliername;
	
	
	
	
	
	
	public String getGoodssort() {
		return goodssort;
	}
	public void setGoodssort(String goodssort) {
		this.goodssort = goodssort;
	}
	public String getGoodssortname() {
		return goodssortname;
	}
	public void setGoodssortname(String goodssortname) {
		this.goodssortname = goodssortname;
	}
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
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public BigDecimal getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public BigDecimal getTotalbox() {
		return totalbox;
	}
	public void setTotalbox(BigDecimal totalbox) {
		this.totalbox = totalbox;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "DeliveryCustomerOutReport [saleprice=" + saleprice + ", cost=" + cost + ", totalbox=" + totalbox + ", volume=" + volume + ", weight=" + weight + ", customerid=" + customerid
				+ ", customername=" + customername + ", goodsid=" + goodsid + ", goodsname=" + goodsname + ", brandid=" + brandid + ", brandname=" + brandname + ", goodssort=" + goodssort
				+ ", goodssortname=" + goodssortname + ", supplierid=" + supplierid + ", suppliername=" + suppliername + "]";
	}
	
	
	
}

