/**
 * @(#)ReceivablePastDueReason.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 22, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class ReceivablePastDueReason implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编码
	 */
	private String id;
	
	/**
	 * 客户编码
	 */
	private String customerid;
	
	/**
	 * 客户名称
	 */
	private String customername;
	
	/**
	 * 应收款
	 */
	private BigDecimal saleamount;
	
	/**
	 * 正常期金额
	 */
	private BigDecimal unpassamount;
	
	/**
	 * 超账期金额
	 */
	private BigDecimal totalpassamount;
	
	/**
	 * 超账期原因
	 */
	private String overreason;
	
	/**
	 * 承诺到款金额
	 */
	private BigDecimal commitmentamount;
	
	/**
	 * 承诺到款时间
	 */
	private String commitmentdate; 
	
	/**
	 * 添加人
	 */
	private String adduserid;
	
	/**
	 * 添加人名称
	 */
	private String addusername;
	
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

	public String getOverreason() {
		return overreason;
	}

	public void setOverreason(String overreason) {
		this.overreason = overreason;
	}

	public BigDecimal getCommitmentamount() {
		return commitmentamount.setScale(2,BigDecimal.ROUND_HALF_UP);
	}

	public void setCommitmentamount(BigDecimal commitmentamount) {
		this.commitmentamount = commitmentamount;
	}

	public String getCommitmentdate() {
		return commitmentdate;
	}

	public void setCommitmentdate(String commitmentdate) {
		this.commitmentdate = commitmentdate;
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

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public BigDecimal getSaleamount() {
		return saleamount;
	}

	public void setSaleamount(BigDecimal saleamount) {
		this.saleamount = saleamount;
	}

	public BigDecimal getUnpassamount() {
		return unpassamount;
	}

	public void setUnpassamount(BigDecimal unpassamount) {
		this.unpassamount = unpassamount;
	}

	public BigDecimal getTotalpassamount() {
		return totalpassamount;
	}

	public void setTotalpassamount(BigDecimal totalpassamount) {
		this.totalpassamount = totalpassamount;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}
}

