/**
 * @(#)DispatchUserReport.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年4月8日 wanghongteng 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class DispatchUserReport implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 发货人编号
     */
    private String storagerid;
    
    /**
     * 发货人名称
     */
    private String storagername;
    
    /**
     * 仓库编号
     */
    private String storageid;
    
    /**
     * 仓库名称
     */
    private String storagename;
    
    /**
     * 纸张数
     */
    private BigDecimal papernum;
    
    /**
     * 条数
     */
    private BigDecimal printnum;

    /**
     * 箱数
     */
    private BigDecimal totalbox;
    
    /**
     * 数量
     */
    private BigDecimal unitnum;
    /**
     * 重量
     */
    private BigDecimal totalweight;
    /**
     * 体积
     */
    private BigDecimal totalvolume;
    /**
     * 金额
     */
    private BigDecimal totalamount;
    
    
    
	public String getStoragerid() {
		return storagerid;
	}
	public void setStoragerid(String storagerid) {
		this.storagerid = storagerid;
	}
	public String getStoragername() {
		return storagername;
	}
	public void setStoragername(String storagername) {
		this.storagername = storagername;
	}
	public String getStorageid() {
		return storageid;
	}
	public void setStorageid(String storageid) {
		this.storageid = storageid;
	}
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	public BigDecimal getPapernum() {
		return papernum;
	}
	public void setPapernum(BigDecimal papernum) {
		this.papernum = papernum;
	}
	public BigDecimal getPrintnum() {
		return printnum;
	}
	public void setPrintnum(BigDecimal printnum) {
		this.printnum = printnum;
	}
	public BigDecimal getTotalbox() {
		return totalbox;
	}
	public void setTotalbox(BigDecimal totalbox) {
		this.totalbox = totalbox;
	}
	public BigDecimal getUnitnum() {
		return unitnum;
	}
	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
	}
	public BigDecimal getTotalweight() {
		return totalweight;
	}
	public void setTotalweight(BigDecimal totalweight) {
		this.totalweight = totalweight;
	}
	public BigDecimal getTotalvolume() {
		return totalvolume;
	}
	public void setTotalvolume(BigDecimal totalvolume) {
		this.totalvolume = totalvolume;
	}
	public BigDecimal getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}
    
    
    
    
}

