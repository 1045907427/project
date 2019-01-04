/**
 * @(#)FundsBrandDeptReturnReport.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 14, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.math.BigDecimal;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class FundsBrandDeptReturnReport {

	private static final long serialVersionUID = 999720234039840694L;
	
	/**
	 * 品牌部门编号
	 */
	private String deptid;
	/**
	 * 品牌部门名称
	 */
	private String deptname;
	/**
	 * 品牌编号
	 */
	private String brand;
	/**
	 * 品牌名称
	 */
	private String brandname;
	/**
	 * 发货金额
	 */
	private BigDecimal sendamount;
	/**
	 * 发货无税金额
	 */
	private BigDecimal sendnotaxamount;
	/**
     * 直退金额
     */
    private BigDecimal directreturnamount;

    /**
     * 无税直退金额
     */
    private BigDecimal directreturnnotaxamount;

    /**
     * 验收退货金额
     */
    private BigDecimal checkreturnamount;

    /**
     * 无税售后退货金额
     */
    private BigDecimal checkreturnnotaxamount;
	/**
	 * 退货金额
	 */
	private BigDecimal returntaxamount;
	/**
	 * 退货无税金额
	 */
	private BigDecimal returnnotaxamount;
	/**
	 * 冲差金额（时候折扣）
	 */
	private BigDecimal pushbalanceamount;
	/**
	 * 实际应收金额
	 */
	private BigDecimal receivableamount;
	/**
	 * 成本金额
	 */
	private BigDecimal costamount;
	/**
	 * 实际毛利率
	 */
	private BigDecimal realrate;
	/**
	 * 计划毛利率
	 */
	private BigDecimal planrate;
	/**
	 * 已回笼金额
	 */
	private BigDecimal withdrawnamount;
	/**
	 * 未回笼金额
	 */
	private BigDecimal unwithdrawnamount;
	/**
	 * 资金回笼率
	 */
	private BigDecimal withdrawrate;
	/**
	 * 未核销总发货金额
	 */
	private BigDecimal allsendamount;
	/**
	 * 未核销总退货金额
	 */
	private BigDecimal allreturnamount;
	/**
	 * 未核销冲差金额
	 */
	private BigDecimal allpushbalanceamount;
	/**
	 * 总应收款
	 */
	private BigDecimal allunwithdrawnamount;
	/**
	 * 回单未审核应收款（历史在途应收款）
	 */
	private BigDecimal unauditamount;
	/**
	 * 回单已审核应收款（历史已验收应收款）
	 */
	private BigDecimal auditamount;
	/**
	 * 售后退货未核销应收款
	 */
	private BigDecimal rejectamount;
	/**
	 * 回笼成本金额
	 */
	private BigDecimal costwriteoffamount;
	/**
	 * 回笼毛利率
	 */
	private BigDecimal writeoffrate;
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBrandname() {
		return brandname;
	}
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	public BigDecimal getSendamount() {
		return sendamount;
	}
	public void setSendamount(BigDecimal sendamount) {
		this.sendamount = sendamount;
	}
	public BigDecimal getSendnotaxamount() {
		return sendnotaxamount;
	}
	public void setSendnotaxamount(BigDecimal sendnotaxamount) {
		this.sendnotaxamount = sendnotaxamount;
	}
	public BigDecimal getDirectreturnamount() {
		return directreturnamount;
	}
	public void setDirectreturnamount(BigDecimal directreturnamount) {
		this.directreturnamount = directreturnamount;
	}
	public BigDecimal getDirectreturnnotaxamount() {
		return directreturnnotaxamount;
	}
	public void setDirectreturnnotaxamount(BigDecimal directreturnnotaxamount) {
		this.directreturnnotaxamount = directreturnnotaxamount;
	}
	public BigDecimal getCheckreturnamount() {
		return checkreturnamount;
	}
	public void setCheckreturnamount(BigDecimal checkreturnamount) {
		this.checkreturnamount = checkreturnamount;
	}
	public BigDecimal getCheckreturnnotaxamount() {
		return checkreturnnotaxamount;
	}
	public void setCheckreturnnotaxamount(BigDecimal checkreturnnotaxamount) {
		this.checkreturnnotaxamount = checkreturnnotaxamount;
	}
	public BigDecimal getReturntaxamount() {
		return returntaxamount;
	}
	public void setReturntaxamount(BigDecimal returntaxamount) {
		this.returntaxamount = returntaxamount;
	}
	public BigDecimal getReturnnotaxamount() {
		return returnnotaxamount;
	}
	public void setReturnnotaxamount(BigDecimal returnnotaxamount) {
		this.returnnotaxamount = returnnotaxamount;
	}
	public BigDecimal getPushbalanceamount() {
		return pushbalanceamount;
	}
	public void setPushbalanceamount(BigDecimal pushbalanceamount) {
		this.pushbalanceamount = pushbalanceamount;
	}
	public BigDecimal getReceivableamount() {
		return receivableamount;
	}
	public void setReceivableamount(BigDecimal receivableamount) {
		this.receivableamount = receivableamount;
	}
	public BigDecimal getCostamount() {
		return costamount;
	}
	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}
	public BigDecimal getRealrate() {
		return realrate;
	}
	public void setRealrate(BigDecimal realrate) {
		this.realrate = realrate;
	}
	public BigDecimal getPlanrate() {
		return planrate;
	}
	public void setPlanrate(BigDecimal planrate) {
		this.planrate = planrate;
	}
	public BigDecimal getWithdrawnamount() {
		return withdrawnamount;
	}
	public void setWithdrawnamount(BigDecimal withdrawnamount) {
		this.withdrawnamount = withdrawnamount;
	}
	public BigDecimal getUnwithdrawnamount() {
		return unwithdrawnamount;
	}
	public void setUnwithdrawnamount(BigDecimal unwithdrawnamount) {
		this.unwithdrawnamount = unwithdrawnamount;
	}
	public BigDecimal getWithdrawrate() {
		return withdrawrate;
	}
	public void setWithdrawrate(BigDecimal withdrawrate) {
		this.withdrawrate = withdrawrate;
	}
	public BigDecimal getAllsendamount() {
		return allsendamount;
	}
	public void setAllsendamount(BigDecimal allsendamount) {
		this.allsendamount = allsendamount;
	}
	public BigDecimal getAllreturnamount() {
		return allreturnamount;
	}
	public void setAllreturnamount(BigDecimal allreturnamount) {
		this.allreturnamount = allreturnamount;
	}
	public BigDecimal getAllpushbalanceamount() {
		return allpushbalanceamount;
	}
	public void setAllpushbalanceamount(BigDecimal allpushbalanceamount) {
		this.allpushbalanceamount = allpushbalanceamount;
	}
	public BigDecimal getAllunwithdrawnamount() {
		return allunwithdrawnamount;
	}
	public void setAllunwithdrawnamount(BigDecimal allunwithdrawnamount) {
		this.allunwithdrawnamount = allunwithdrawnamount;
	}
	public BigDecimal getUnauditamount() {
		return unauditamount;
	}
	public void setUnauditamount(BigDecimal unauditamount) {
		this.unauditamount = unauditamount;
	}
	public BigDecimal getAuditamount() {
		return auditamount;
	}
	public void setAuditamount(BigDecimal auditamount) {
		this.auditamount = auditamount;
	}
	public BigDecimal getRejectamount() {
		return rejectamount;
	}
	public void setRejectamount(BigDecimal rejectamount) {
		this.rejectamount = rejectamount;
	}
	public BigDecimal getCostwriteoffamount() {
		return costwriteoffamount;
	}
	public void setCostwriteoffamount(BigDecimal costwriteoffamount) {
		this.costwriteoffamount = costwriteoffamount;
	}
	public BigDecimal getWriteoffrate() {
		return writeoffrate;
	}
	public void setWriteoffrate(BigDecimal writeoffrate) {
		this.writeoffrate = writeoffrate;
	}
}

