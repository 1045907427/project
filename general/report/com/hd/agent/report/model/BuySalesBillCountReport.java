/**
 * @(#)BuySalesBillCountReport.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-11-28 zhanghonghui 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 采购销售单据数汇总表
 * 
 * @author zhanghonghui
 */
public class BuySalesBillCountReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -682602823103555303L;

	private String treeid;
    
    private String state;
    
    private String parentid;
    /**
     * 编号
     */
    private String id;
    /**
     * 商品编码
     */
    private String goodsid;
    /**
     * 商品名称
     */
    private String goodsname;
    
    /**
     * 条形码
     */
    private String barcode;
    /**
     * 品牌编号
     */
    private String brand;
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
     * 采购数量
     */
    private BigDecimal buynum;
    /**
     * 采购金额
     */
    private BigDecimal buyamount;
    /**
     * 采购无税金额
     */
    private BigDecimal buynotaxamount;
    /**
     * 采购单据数
     */
    private Integer buybillcount;
    /**
     * 销售数量
     */
    private BigDecimal salenum;
    /**
     * 销售金额
     */
    private BigDecimal saleamount;
    /**
     * 销售无税金额
     */
    private BigDecimal salenotaxamount;
    /**
     * 销售单据数
     */
    private Integer salebillcount;
    /**
     * 销售毛利金额
     */
    private BigDecimal salegrossamount;
    /**
     * 销售毛利率
     */
    private BigDecimal salegrossrate;
    /**
     * 销售成本
     */
    private BigDecimal costamount;
    /**
     * 回笼金额
     */
    private BigDecimal writeoffamount;
    /**
     * 回笼金额成本
     */
    private BigDecimal costwriteoffamount;
    /**
     * 回笼毛利额
     */
    private BigDecimal writeoffgrossamount;
    /**
     * 回笼毛利率
     */
    private BigDecimal writeoffgrate;
    /**
     * 总单据数
     */
    private Integer billcount;
    /**
     * 供应商编号
     */
    private String supplierid;
    /**
     * 供应商名称
     */
    private String suppliername;
	public String getTreeid() {
		return treeid;
	}
	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
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
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
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
	public BigDecimal getBuynum() {
		return buynum;
	}
	public void setBuynum(BigDecimal buynum) {
		this.buynum = buynum;
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
	public Integer getBuybillcount() {
		return buybillcount;
	}
	public void setBuybillcount(Integer buybillcount) {
		this.buybillcount = buybillcount;
	}
	public BigDecimal getSalenum() {
		return salenum;
	}
	public void setSalenum(BigDecimal salenum) {
		this.salenum = salenum;
	}
	public BigDecimal getSaleamount() {
		return saleamount;
	}
	public void setSaleamount(BigDecimal saleamount) {
		this.saleamount = saleamount;
	}
	public BigDecimal getSalenotaxamount() {
		return salenotaxamount;
	}
	public void setSalenotaxamount(BigDecimal salenotaxamount) {
		this.salenotaxamount = salenotaxamount;
	}
	public Integer getSalebillcount() {
		return salebillcount;
	}
	public void setSalebillcount(Integer salebillcount) {
		this.salebillcount = salebillcount;
	}
	public BigDecimal getSalegrossamount() {
		return salegrossamount;
	}
	public void setSalegrossamount(BigDecimal salegrossamount) {
		this.salegrossamount = salegrossamount;
	}
	public BigDecimal getSalegrossrate() {
		return salegrossrate;
	}
	public void setSalegrossrate(BigDecimal salegrossrate) {
		this.salegrossrate = salegrossrate;
	}
	public BigDecimal getCostamount() {
		return costamount;
	}
	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}
	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}
	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}
	public BigDecimal getCostwriteoffamount() {
		return costwriteoffamount;
	}
	public void setCostwriteoffamount(BigDecimal costwriteoffamount) {
		this.costwriteoffamount = costwriteoffamount;
	}
	public BigDecimal getWriteoffgrossamount() {
		return writeoffgrossamount;
	}
	public void setWriteoffgrossamount(BigDecimal writeoffgrossamount) {
		this.writeoffgrossamount = writeoffgrossamount;
	}
	public BigDecimal getWriteoffgrate() {
		return writeoffgrate;
	}
	public void setWriteoffgrate(BigDecimal writeoffgrate) {
		this.writeoffgrate = writeoffgrate;
	}
	public Integer getBillcount() {
		return billcount;
	}
	public void setBillcount(Integer billcount) {
		this.billcount = billcount;
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

