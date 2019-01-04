/**
 * @(#)ApprovalPrice.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 15, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * 
 * @author panxiaoxiao
 */

public class ApprovalPrice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String id;

    /**
     * 供应商编码
     */
    private String supplierid;
    
    /**
     * 供应商名称
     */
    private String supplierName;
    
    /**
     * 供应商所属部门编码
     */
    private String supplierdeptid;
    
    /**
     * 供应商所属部门名称
     */
    private String supplierdeptName;

    /**
     * 金额
     */
    private BigDecimal price;
    
    /**
     * 实际占用金额
     */
    private BigDecimal atamount;

    /**
     * @return 编码
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 供应商编码
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编码
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    /**
     * @return 金额
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price 
	 *            金额
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    /**
     * @return 实际占用金额
     */
	public BigDecimal getAtamount() {
		return atamount;
	}

	/**
	 * @param atamount
	 * 实际占用金额
	 */
	public void setAtamount(BigDecimal atamount) {
		this.atamount = atamount;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierdeptid() {
		return supplierdeptid;
	}

	public void setSupplierdeptid(String supplierdeptid) {
		this.supplierdeptid = supplierdeptid;
	}

	public String getSupplierdeptName() {
		return supplierdeptName;
	}

	public void setSupplierdeptName(String supplierdeptName) {
		this.supplierdeptName = supplierdeptName;
	}
    
}
