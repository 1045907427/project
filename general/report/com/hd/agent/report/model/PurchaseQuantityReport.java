/**
 * @(#)PurchaseQuantityReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-2-20 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.math.BigDecimal;

/**
 * 采购进货数量
 * 
 * @author zhanghonghui
 */
public class PurchaseQuantityReport {
	/**
     * 供应商编号
     */
    private String supplierid;    
    /**
     * 供应商名称
     */
    private String suppliername;
    /**
     * 商品编码
     */
    private String goodsid;    
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 品牌编号
     */
    private String brandid;    
    /**
     * 商品品牌
     */
    private String brandname;
    /**
     * 进货辅单位整数数量
     */
    private BigDecimal enternum;
    /**
     * 进货辅单位余数数量
     */
    private BigDecimal enterremainder;
    /**
     * 辅单位数量
     */
    private String enternumdetail;
    /**
     * 进货辅单位
     */
    private String enterunitname;
    /**
     * 箱重
     */
    private BigDecimal totalweight;
    
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
	public BigDecimal getEnternum() {
		return enternum;
	}
	public void setEnternum(BigDecimal enternum) {
		this.enternum = enternum;
	}
	public BigDecimal getEnterremainder() {
		return enterremainder;
	}
	public void setEnterremainder(BigDecimal enterremainder) {
		this.enterremainder = enterremainder;
	}
	public String getEnternumdetail() {
		return enternumdetail;
	}
	public void setEnternumdetail(String enternumdetail) {
		this.enternumdetail = enternumdetail;
	}
	public String getEnterunitname() {
		return enterunitname;
	}
	public void setEnterunitname(String enterunitname) {
		this.enterunitname = enterunitname;
	}
	public BigDecimal getTotalweight() {
		return totalweight;
	}
	public void setTotalweight(BigDecimal totalweight) {
		this.totalweight = totalweight;
	}
}

