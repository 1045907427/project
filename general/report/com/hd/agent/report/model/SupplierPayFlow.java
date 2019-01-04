/**
 * @(#)CustomerSalesFlow.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 22, 2013 chenwei 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 供应商采购情况流水
 * @author chenwei
 */
public class SupplierPayFlow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -244860337760326720L;
	/**
	 * 单据编号
	 */
	private String id;
	/**
	 * 业务日期
	 */
	private String businessdate;
	/**
	 * 单据类型1采购入库单2采购退货通知单
	 */
	private String billtype;
	
	/**
	 * 单据类型名称
	 */
	private String billtypename;
	/**
	 * 客户编号
	 */
	private String supplierid;
	/**
	 * 客户名称
	 */
	private String suppliername;
	/**
	 * 商品编号
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
	private String brandid;
	/**
	 * 品牌名称
	 */
	private String brandname;
	/**
	 * 商品单位
	 */
	private String unitid;
	/**
	 * 商品单位名称
	 */
	private String unitname;
	/**
	 * 商品单价
	 */
	private BigDecimal price;
	/**
	 * 商品数量
	 */
	private BigDecimal unitnum;
	/**
	 * 商品辅单位描述
	 */
	private String auxunitnumdetail;
	/**
	 * 辅单位数量
	 */
	private BigDecimal auxnum;
	/**
	 * 辅单位余数
	 */
	private BigDecimal auxremainder;
	/**
	 * 辅单位名称
	 */
	private String auxunitname;
	/**
	 * 商品金额
	 */
	private BigDecimal taxamount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否折扣
	 */
	private String isdiscount;
	/**
	 * 是否开票 是否核销 0未开票 1已开票 2 已核销
	 */
	private String isinvoice;
	/**
	 * 是否核销 1核销 0未核销
	 */
	private String iswriteoff;
	/**
	 * 开票状态
	 */
	private String isinvoicename;
	/**
	 * 核销状态名称
	 */
	private String iswriteoffname;
	/**
	 * 核销状态
	 */
	private String writeoffname;
	/**
	 * 退货验收
	 */
	private String ischeck;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessdate() {
		return businessdate;
	}
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
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
	public BigDecimal getTaxamount() {
		return taxamount;
	}
	public void setTaxamount(BigDecimal taxamount) {
		this.taxamount = taxamount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsdiscount() {
		return isdiscount;
	}
	public void setIsdiscount(String isdiscount) {
		this.isdiscount = isdiscount;
	}
	public String getIsinvoice() {
		return isinvoice;
	}
	public void setIsinvoice(String isinvoice) {
		this.isinvoice = isinvoice;
	}
	public String getBilltype() {
		return billtype;
	}
	public void setBilltype(String billtype) {
		this.billtype = billtype;
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
	public String getAuxunitnumdetail() {
		return auxunitnumdetail;
	}
	public void setAuxunitnumdetail(String auxunitnumdetail) {
		this.auxunitnumdetail = auxunitnumdetail;
	}
	public BigDecimal getAuxnum() {
		return auxnum;
	}
	public void setAuxnum(BigDecimal auxnum) {
		this.auxnum = auxnum;
	}
	public BigDecimal getAuxremainder() {
		return auxremainder;
	}
	public void setAuxremainder(BigDecimal auxremainder) {
		this.auxremainder = auxremainder;
	}
	public String getAuxunitname() {
		return auxunitname;
	}
	public void setAuxunitname(String auxunitname) {
		this.auxunitname = auxunitname;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getBilltypename() {
		return billtypename;
	}
	public void setBilltypename(String billtypename) {
		this.billtypename = billtypename;
	}
	public String getIsinvoicename() {
		return isinvoicename;
	}
	public void setIsinvoicename(String isinvoicename) {
		this.isinvoicename = isinvoicename;
	}
	public String getWriteoffname() {
		return writeoffname;
	}
	public void setWriteoffname(String writeoffname) {
		this.writeoffname = writeoffname;
	}
	public String getIswriteoff() {
		return iswriteoff;
	}
	public void setIswriteoff(String iswriteoff) {
		this.iswriteoff = iswriteoff;
	}
	public String getIswriteoffname() {
		return iswriteoffname;
	}
	public void setIswriteoffname(String iswriteoffname) {
		this.iswriteoffname = iswriteoffname;
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
        this.ischeck = ischeck == null ? null : ischeck.trim();
	}
	
}

