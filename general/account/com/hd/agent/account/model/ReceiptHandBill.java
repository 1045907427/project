/**
 * @(#)ReceiptBill.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 26, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * 
 * @author panxiaoxiao
 */
public class ReceiptHandBill implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 编码
	 */
	private Integer id;
	
	/**
	 * 交接单编号
	 */
	private String billid;
	
	/**
	 * 关联单据编号（回单或者退货通知单）
	 */
	private String relatebillid;

	/**
	 * 单据类型
	 */
	private String billtype;

	/**
	 * 单据类型名称
	 */
	private String billtypename;
	
	/**
	 * 销售订单编号
	 */
	private String saleorderid;
	
	/**
	 * 回单日期
	 */
	private String businessdate;
	
	/**
	 * 领单日期
	 */
	private String handdate;
	
	/**
	 * 客户编码
	 */
	private String customerid;
	
	/**
	 * 客户名称
	 */
	private String customername;
	
	/**
	 * 总店编码
	 */
	private String pcustomerid;
	
	/**
	 * 总店名称
	 */
	private String pcustomername;
	
	/**
	 * 客户分类
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
	 * 销售员
	 */
	private String salesuser;
	
	/**
	 * 销售员名称
	 */
	private String salesusername;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 是否回收
	 */
	private String isrecycle;

	/**
	 * 是否收回名称
	 */
	private String isrecyclename;
	
	/**
	 * 回收日期
	 */
	private String recycledate;
	
	/**
	 * 领单人
	 */
	private String handuserid;
	
	/**
	 * 领单人名称
	 */
	private String handusername;
	
	/**
	 * 回单状态
	 */
	private String status;

	/**
	 * 回单状态
	 */
	private String statusname;

	/**
	 * 备注
	 */
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getRelatebillid() {
		return relatebillid;
	}

	public void setRelatebillid(String relatebillid) {
		this.relatebillid = relatebillid;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getIsrecycle() {
		return isrecycle;
	}

	public void setIsrecycle(String isrecycle) {
		this.isrecycle = isrecycle;
	}

	public String getRecycledate() {
		return recycledate;
	}

	public void setRecycledate(String recycledate) {
		this.recycledate = recycledate;
	}

	public String getHanduserid() {
		return handuserid;
	}

	public void setHanduserid(String handuserid) {
		this.handuserid = handuserid;
	}

	public String getHandusername() {
		return handusername;
	}

	public void setHandusername(String handusername) {
		this.handusername = handusername;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSaleorderid() {
		return saleorderid;
	}

	public void setSaleorderid(String saleorderid) {
		this.saleorderid = saleorderid;
	}

	public String getHanddate() {
		return handdate;
	}

	public void setHanddate(String handdate) {
		this.handdate = handdate;
	}

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getBilltypename() {
		return billtypename;
	}

	public void setBilltypename(String billtypename) {
		this.billtypename = billtypename;
	}

	public String getIsrecyclename() {
		return isrecyclename;
	}

	public void setIsrecyclename(String isrecyclename) {
		this.isrecyclename = isrecyclename;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String sourceid;

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
}

