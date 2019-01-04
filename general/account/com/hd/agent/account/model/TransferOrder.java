/**
 * @(#)TransferOrder.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 13, 2013 chenwei 创建版本
 */
package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 收款单转账
 * @author chenwei
 */
public class TransferOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4489828408548789220L;
	/**
	 * 转出客户编号
	 */
	private String outcustomerid;
	/**
	 * 转入客户编号
	 */
	private String incustomerid;
	/**
	 * 转出收款单编号
	 */
	private String outorderid;
	/**
	 * 转入收款单编号
	 */
	private String inorderid;
	/**
	 * 转出供应商编码
	 */
	private String outsupplierid;
	
	/**
	 * 转入供应商编号
	 */
	private String insupplierid;
	/**
	 * 转成金额
	 */
	private BigDecimal transferamount;
	/**
	 * 备注
	 */
	private String remark;
	public BigDecimal getTransferamount() {
		return transferamount;
	}
	public void setTransferamount(BigDecimal transferamount) {
		this.transferamount = transferamount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOutcustomerid() {
		return outcustomerid;
	}
	public void setOutcustomerid(String outcustomerid) {
		this.outcustomerid = outcustomerid;
	}
	public String getIncustomerid() {
		return incustomerid;
	}
	public void setIncustomerid(String incustomerid) {
		this.incustomerid = incustomerid;
	}
	public String getOutsupplierid() {
		return outsupplierid;
	}
	public void setOutsupplierid(String outsupplierid) {
		this.outsupplierid = outsupplierid;
	}
	public String getInsupplierid() {
		return insupplierid;
	}
	public void setInsupplierid(String insupplierid) {
		this.insupplierid = insupplierid;
	}
	public String getOutorderid() {
		return outorderid;
	}
	public void setOutorderid(String outorderid) {
		this.outorderid = outorderid;
	}
	public String getInorderid() {
		return inorderid;
	}
	public void setInorderid(String inorderid) {
		this.inorderid = inorderid;
	}
	
}

