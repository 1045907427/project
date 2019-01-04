/**
 * @(#)CollectionOrderAssign.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jan 4, 2014 chenwei 创建版本
 */
package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 未指定客户收款单分配金额
 * @author chenwei
 */
public class CollectionOrderAssign implements Serializable{
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 未指定客户收款单号
	 */
	private String sourceid;
	/**
	 * 指定客户的收款单号
	 */
	private String assignid;
	/**
	 * 客户编号
	 */
	private String customerid;
	/**
	 * 银行编号
	 */
	private String bankid;
	/**
	 * 未指定客户的收款单初始总金额
	 */
	private BigDecimal totalamount;
	/**
	 * 分配金额
	 */
	private BigDecimal amount;
	/**
	 * 添加时间
	 */
	private Date addtime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceid() {
		return sourceid;
	}
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
	public String getAssignid() {
		return assignid;
	}
	public void setAssignid(String assignid) {
		this.assignid = assignid;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public BigDecimal getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	} 
	
}

