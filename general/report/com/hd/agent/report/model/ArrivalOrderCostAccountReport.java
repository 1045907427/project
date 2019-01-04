/**
 * @(#)ArrivalOrderCostAccountReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-12-4 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 采购进货单差额报表
 * @author zhanghonghui
 */
public class ArrivalOrderCostAccountReport implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6555034991184665796L;

	/**
	 * 编号 
	 */
	private String id;

    /**
     * 商品编号
     */
    private String goodsid;
    /**
     * 商品名称
     */
    private String goodsname;
    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;
    /**
     * 条形码
     */
    private String barcode;

    /**
     * 品牌编号
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 采购员
     */
    private String buyuserid;
    /**
     * 采购员名称
     */
    private String buyusername;
    /**
     * 采购部门
     */
    private String buydeptid;
    /**
     * 采购门名称
     */
    private String buydeptname;
    /**
     * 箱装量
     */
    private BigDecimal boxnum;

    /**
     * 采购数量
     */
    private BigDecimal buynum;
    /**
     * 采购主单位数量
     */
    private BigDecimal buyunitnum;
    /**
     * 采购单位
     */
    private String buyunitname;
    /**
     * 采购金额
     */
    private BigDecimal buyamount;
    /**
     * 采购无税金额
     */
    private BigDecimal buynotaxamount;
   
    /**
     * 税额
     */
    private BigDecimal buytax;

    /**
     * 核算成本价
     */
    private BigDecimal costaccountprice;
    /**
     * 核算成本金额
     */
    private BigDecimal costaccountamount;
    /**
     * 差额
     */
    private BigDecimal blanceamount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	public String getBuyusername() {
		return buyusername;
	}
	public void setBuyusername(String buyusername) {
		this.buyusername = buyusername;
	}
	public String getBrandid() {
		return brandid;
	}
	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}
	public String getBuyuserid() {
		return buyuserid;
	}
	public void setBuyuserid(String buyuserid) {
		this.buyuserid = buyuserid;
	}
	public String getBuydeptid() {
		return buydeptid;
	}
	public void setBuydeptid(String buydeptid) {
		this.buydeptid = buydeptid;
	}
	public String getBuydeptname() {
		return buydeptname;
	}
	public void setBuydeptname(String buydeptname) {
		this.buydeptname = buydeptname;
	}
	public BigDecimal getBoxnum() {
		return boxnum;
	}
	public void setBoxnum(BigDecimal boxnum) {
		this.boxnum = boxnum;
	}
	public BigDecimal getBuynum() {
		return buynum;
	}
	public void setBuynum(BigDecimal buynum) {
		this.buynum = buynum;
	}
	public BigDecimal getBuyunitnum() {
		return buyunitnum;
	}
	public void setBuyunitnum(BigDecimal buyunitnum) {
		this.buyunitnum = buyunitnum;
	}
	public String getBuyunitname() {
		return buyunitname;
	}
	public void setBuyunitname(String buyunitname) {
		this.buyunitname = buyunitname;
	}
	public BigDecimal getBuyamount() {
		return buyamount;
	}
	public void setBuyamount(BigDecimal buyamount) {
		this.buyamount = buyamount;
	}
	public BigDecimal getBuynotaxamount() {
		return buynotaxamount;
	}
	public void setBuynotaxamount(BigDecimal buynotaxamount) {
		this.buynotaxamount = buynotaxamount;
	}
	public BigDecimal getBuytax() {
		return buytax;
	}
	public void setBuytax(BigDecimal buytax) {
		this.buytax = buytax;
	}
	public BigDecimal getCostaccountprice() {
		return costaccountprice;
	}
	public void setCostaccountprice(BigDecimal costaccountprice) {
		this.costaccountprice = costaccountprice;
	}
	public BigDecimal getCostaccountamount() {
		return costaccountamount;
	}
	public void setCostaccountamount(BigDecimal costaccountamount) {
		this.costaccountamount = costaccountamount;
	}
	public BigDecimal getBlanceamount() {
		return blanceamount;
	}
	public void setBlanceamount(BigDecimal blanceamount) {
		this.blanceamount = blanceamount;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
}

