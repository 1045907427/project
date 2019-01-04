/**
 * @(#)PurchaseOrder.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 9, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;

import java.math.BigDecimal;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class PurchaseOrder {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号
	 */
	private String id;
	
	/**
	 * 业务日期
	 */
	private String businessdate;
	
	/**
	 * 订单类型1采购进货单,2采购退货通知单
	 */
	private String ordertype;
	
	/**
	 * 订单类型名称
	 */
	private String ordertypename;
	
	/**
	 * 供应商编码
	 */
	private String supplierid;
	
	/**
	 * 供应商名称
	 */
	private String suppliername;
	
	/**
	 * 对方经手人
	 */
	private String handlerid;
	
	/**
	 * 经手人名称
	 */
	private String handlername;
	
	/**
	 * 采购部门编号
	 */
	private String buydeptid;
	
	/**
	 * 采购部门名称
	 */
	private String buydeptname;
	
	/**
	 * 采购人员编号
	 */
	private String buyuserid;
	
	/**
	 * 采购人员名称
	 */
	private String buyusername;

	/**
	 * 金额
	 */
	private BigDecimal totalamount;
	
	/**
	 * 制单人用户编码
	 */
	private String adduserid;
	
	/**
	 * 制单人用户名称
	 */
	private String addusername;
	
	/**
	 * 备注
	 */
	private String remark;

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

	public String getHandlerid() {
		return handlerid;
	}

	public void setHandlerid(String handlerid) {
		this.handlerid = handlerid;
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

	public String getBuyuserid() {
		return buyuserid;
	}

	public void setBuyuserid(String buyuserid) {
		this.buyuserid = buyuserid;
	}

	public String getBuyusername() {
		return buyusername;
	}

	public void setBuyusername(String buyusername) {
		this.buyusername = buyusername;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public String getAdduserid() {
		return adduserid;
	}

	public void setAdduserid(String adduserid) {
		this.adduserid = adduserid;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHandlername() {
		return handlername;
	}

	public void setHandlername(String handlername) {
		this.handlername = handlername;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getOrdertypename() {
		return ordertypename;
	}

	public void setOrdertypename(String ordertypename) {
		this.ordertypename = ordertypename;
	}
}

