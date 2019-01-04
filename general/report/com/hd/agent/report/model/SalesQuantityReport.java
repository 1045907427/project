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
 * 销售出库数量
 * 
 * @author zhanghonghui
 */
public class SalesQuantityReport {
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
     * 辅单位整数数量
     */
    private BigDecimal auxunitnum;
    /**
     * 辅单位余数数量
     */
    private BigDecimal auxremainder;
    /**
     * 辅单位数量
     */
    private String auxnumdetail;
    /**
     * 辅单位
     */
    private String auxunitname;
    /**
     * 箱重
     */
    private BigDecimal totalweight;
    /**
     * 仓库编号
     */
    private String storageid;
    /**
     * 仓库名称
     */
    private String storagename;
    /**
     * 箱体积
     */
    private BigDecimal totalvolume;
  
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
	
	public BigDecimal getTotalweight() {
		return totalweight;
	}
	public void setTotalweight(BigDecimal totalweight) {
		this.totalweight = totalweight;
	}
	public BigDecimal getAuxunitnum() {
		return auxunitnum;
	}
	public void setAuxunitnum(BigDecimal auxunitnum) {
		this.auxunitnum = auxunitnum;
	}
	public BigDecimal getAuxremainder() {
		return auxremainder;
	}
	public void setAuxremainder(BigDecimal auxremainder) {
		this.auxremainder = auxremainder;
	}
	public String getAuxnumdetail() {
		return auxnumdetail;
	}
	public void setAuxnumdetail(String auxnumdetail) {
		this.auxnumdetail = auxnumdetail;
	}
	public String getAuxunitname() {
		return auxunitname;
	}
	public void setAuxunitname(String auxunitname) {
		this.auxunitname = auxunitname;
	}
	public String getStorageid() {
		return storageid;
	}
	public void setStorageid(String storageid) {
		this.storageid = storageid;
	}
	public BigDecimal getTotalvolume() {
		return totalvolume;
	}
	public void setTotalvolume(BigDecimal totalvolume) {
		this.totalvolume = totalvolume;
	}
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
}

