/**
 * @(#)BankReport.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 23, 2013 chenwei 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 银行收支情况统计数据
 * @author chenwei
 */
public class BankReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4318627424647465731L;
	/**
	 * 银行编号
	 */
	private String bank;
	/**
	 * 银行名称
	 */
	private String bankname;
	/**
	 * 业务日期
	 */
	private String businessdate;
	/**
	 * 收款金额
	 */
	private BigDecimal receiptamount;
	/**
	 * 付款金额
	 */
	private BigDecimal payamount;
	/**
	 * 余额
	 */
	private BigDecimal endamount;
	/**
	 * 收款核销金额
	 */
	private BigDecimal receiptwriteamount;
	/**
	 * 收款未核销金额
	 */
	private BigDecimal unreceiptwriteamount;
	/**
	 * 付款核销金额
	 */
	private BigDecimal paywriteamount;
	/**
	 * 付款未核销金额
	 */
	private BigDecimal unpaywriteamount;
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBusinessdate() {
		return businessdate;
	}
	public void setBusinessdate(String businessdate) {
		this.businessdate = businessdate;
	}
	public BigDecimal getReceiptamount() {
		return receiptamount;
	}
	public void setReceiptamount(BigDecimal receiptamount) {
		this.receiptamount = receiptamount;
	}
	public BigDecimal getPayamount() {
		return payamount;
	}
	public void setPayamount(BigDecimal payamount) {
		this.payamount = payamount;
	}
	public BigDecimal getEndamount() {
		return endamount;
	}
	public void setEndamount(BigDecimal endamount) {
		this.endamount = endamount;
	}
	public BigDecimal getReceiptwriteamount() {
		return receiptwriteamount;
	}
	public void setReceiptwriteamount(BigDecimal receiptwriteamount) {
		this.receiptwriteamount = receiptwriteamount;
	}
	public BigDecimal getPaywriteamount() {
		return paywriteamount;
	}
	public void setPaywriteamount(BigDecimal paywriteamount) {
		this.paywriteamount = paywriteamount;
	}
	public BigDecimal getUnreceiptwriteamount() {
		return unreceiptwriteamount;
	}
	public void setUnreceiptwriteamount(BigDecimal unreceiptwriteamount) {
		this.unreceiptwriteamount = unreceiptwriteamount;
	}
	public BigDecimal getUnpaywriteamount() {
		return unpaywriteamount;
	}
	public void setUnpaywriteamount(BigDecimal unpaywriteamount) {
		this.unpaywriteamount = unpaywriteamount;
	}
	
	
}

