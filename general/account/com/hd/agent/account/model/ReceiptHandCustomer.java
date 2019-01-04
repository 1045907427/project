/**
 * @(#)ReceiptCustomer.java
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
 * 应收款交接单客户明细
 * 
 * @author panxiaoxiao
 */
public class ReceiptHandCustomer implements Serializable{
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
	 * 客户编码
	 */
	private String customerid;
	
	/**
	 * 客户名称
	 */
	private String customername;
	
	/**
	 * 单据数
	 */
	private Integer billnums;
	
	/**
	 * 应收金额
	 */
	private BigDecimal amount;
	
	/**
	 * 收款金额
	 */
	private BigDecimal collectionamount;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 是否关联单据
	 */
	private String isreceipt;
	
	/**
	 * 是否修改
	 */
	private String isedit;

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

	public Integer getBillnums() {
		return billnums;
	}

	public void setBillnums(Integer billnums) {
		this.billnums = billnums;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCollectionamount() {
		return collectionamount;
	}

	public void setCollectionamount(BigDecimal collectionamount) {
		this.collectionamount = collectionamount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsreceipt() {
		return isreceipt;
	}

	public void setIsreceipt(String isreceipt) {
		this.isreceipt = isreceipt;
	}

	public String getIsedit() {
		return isedit;
	}

	public void setIsedit(String isedit) {
		this.isedit = isedit;
	}

	private String sourceids;

	public String getSourceids() {
		return sourceids;
	}

	public void setSourceids(String sourceids) {
		this.sourceids = sourceids;
	}
}

