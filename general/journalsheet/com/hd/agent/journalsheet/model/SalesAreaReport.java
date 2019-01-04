/**
 * @(#)SalesAreaReport.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年4月15日 wanghongteng 创建版本
 */
package com.hd.agent.journalsheet.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 销售区域报表
 * 
 * @author wanghongteng
 */

public class SalesAreaReport implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 所属区域
     */
    private String salesarea;
    /**
     * 所属区域名称
     */
    private String salesareaname;
    /**
     * 订单金额
     */
    private BigDecimal orderamount;
    /**
     * 发货单金额
     */
    private BigDecimal initsendamount;
    /**
     * 发货出库金额
     */
    private BigDecimal sendamount;
    /**
     * 冲差金额
     */
    private BigDecimal pushbalanceamount;
    /**
     * 直退金额
     */
    private BigDecimal directreturnamount;
    /**
     * 验收退货金额
     */
    private BigDecimal checkreturnamount;
    /**
     * 退货金额
     */
    private BigDecimal returnamount;
    /**
     * 销售数量
     */
    private BigDecimal salenum;
    /**
     * 销售箱数
     */
    private BigDecimal saletotalbox;
    /**
     * 销售金额
     */
    private BigDecimal saleamount;
    /**
     * 成本金额
     */
    private BigDecimal costamount;
    /**
     * 毛利额
     */
    private BigDecimal salemarginamount;
    /**
     * 实际毛利率
     */
    private BigDecimal realrate;
    
    
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
	public BigDecimal getOrderamount() {
		return orderamount;
	}
	public void setOrderamount(BigDecimal orderamount) {
		this.orderamount = orderamount;
	}
	public BigDecimal getInitsendamount() {
		return initsendamount;
	}
	public void setInitsendamount(BigDecimal initsendamount) {
		this.initsendamount = initsendamount;
	}
	public BigDecimal getSendamount() {
		return sendamount;
	}
	public void setSendamount(BigDecimal sendamount) {
		this.sendamount = sendamount;
	}
	public BigDecimal getPushbalanceamount() {
		return pushbalanceamount;
	}
	public void setPushbalanceamount(BigDecimal pushbalanceamount) {
		this.pushbalanceamount = pushbalanceamount;
	}
	public BigDecimal getDirectreturnamount() {
		return directreturnamount;
	}
	public void setDirectreturnamount(BigDecimal directreturnamount) {
		this.directreturnamount = directreturnamount;
	}
	public BigDecimal getCheckreturnamount() {
		return checkreturnamount;
	}
	public void setCheckreturnamount(BigDecimal checkreturnamount) {
		this.checkreturnamount = checkreturnamount;
	}
	public BigDecimal getReturnamount() {
		return returnamount;
	}
	public void setReturnamount(BigDecimal returnamount) {
		this.returnamount = returnamount;
	}
	public BigDecimal getSalenum() {
		return salenum;
	}
	public void setSalenum(BigDecimal salenum) {
		this.salenum = salenum;
	}
	public BigDecimal getSaletotalbox() {
		return saletotalbox;
	}
	public void setSaletotalbox(BigDecimal saletotalbox) {
		this.saletotalbox = saletotalbox;
	}
	public BigDecimal getSaleamount() {
		return saleamount;
	}
	public void setSaleamount(BigDecimal saleamount) {
		this.saleamount = saleamount;
	}
	public BigDecimal getCostamount() {
		return costamount;
	}
	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}
	public BigDecimal getSalemarginamount() {
		return salemarginamount;
	}
	public void setSalemarginamount(BigDecimal salemarginamount) {
		this.salemarginamount = salemarginamount;
	}
	public BigDecimal getRealrate() {
		return realrate;
	}
	public void setRealrate(BigDecimal realrate) {
		this.realrate = realrate;
	}
    
    
    
    
}

