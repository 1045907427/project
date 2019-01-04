/**
 * @(#)CollectionOrderRelate.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 2, 2013 chenwei 创建版本
 */
package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 关联收款单
 * @author chenwei
 */
public class CollectionOrderRelate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 收款单编号
	 */
	private String id;
	/**
	 * 收款单日期
	 */
	private String businessdate;
	/**
	 * 收款单金额
	 */
	private BigDecimal amount;
	/**
	 * 已核销金额
	 */
	private BigDecimal writeoffamount;
	/**
	 * 剩余金额
	 */
	private BigDecimal remainderamount; 
	/**
	 * 关联金额
	 */
	private BigDecimal relateamount;
	private int version;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getWriteoffamount() {
		return writeoffamount;
	}
	public void setWriteoffamount(BigDecimal writeoffamount) {
		this.writeoffamount = writeoffamount;
	}
	public BigDecimal getRemainderamount() {
		return remainderamount;
	}
	public void setRemainderamount(BigDecimal remainderamount) {
		this.remainderamount = remainderamount;
	}
	public BigDecimal getRelateamount() {
		return relateamount;
	}
	public void setRelateamount(BigDecimal relateamount) {
		this.relateamount = relateamount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}

