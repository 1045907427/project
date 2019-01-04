/**
 * @(#)SalesGoodsCurrentDetail.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 19, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.math.BigDecimal;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class SalesGoodsFlowDetail {

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
	 * 单据类型1销售发货单2销售退货通知单3应收款冲差
	 */
	private String billtype;
	
	/**
	 * 单据类型名称
	 */
	private String billtypename;
	/**
	 * 客户编号
	 */
	private String customerid;
	/**
	 * 客户名称
	 */
	private String customername;
	/**
	 * 总店客户编号
	 */
	private String pcustomerid;
	/**
	 * 总店名称
	 */
	private String pcustomername;
	/**
	 * 商品编号
	 */
	private String goodsid;
	/**
	 * 商品名称
	 */
	private String goodsname;
	
	/**
	 * 商品品牌
	 */
	private String brandid;
	
	/**
	 * 商品品牌名称
	 */
	private String brandname;
	
	/**
	 * 品牌部门
	 */
	private String branddept;
	
	/**
	 * 品牌部门名称
	 */
	private String branddeptname;
	
	/**
	 * 品牌业务员
	 */
	private String branduser;
	
	/**
	 * 品牌业务员名称
	 */
	private String brandusername;
	
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
	 * 是否开票 0未开票 1已开票
	 */
	private String isinvoice;
	/**
	 * 是否核销 1是0否
	 */
	private String iswriteoff;
	/**
	 * 开票状态名称
	 */
	private String isinvoicename;
	
	/**
	 * 核销状态
	 */
	private String writeoffname;
	
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
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

}

