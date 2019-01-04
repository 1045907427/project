/**
 * @(#)FundsDeptReturnReport.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 16, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 按部门资金回笼统计报表
 * @author panxiaoxiao
 */
public class FundsDeptReturnReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 999720234039840694L;
	/**
	 * 部门编号
	 */
	private String salesdept;
	/**
	 * 部门名称
	 */
	private String salesdeptname;
	/**
	 * 发货金额
	 */
	private BigDecimal sendamount;
	/**
	 * 发货无税金额
	 */
	private BigDecimal sendnotaxamount;
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
	 * 部门编码
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public String getSalesdept() {
		return salesdept;
	}
	
	/**
	 * 部门编码
	 * @param salesdept
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setSalesdept(String salesdept) {
		this.salesdept = salesdept;
	}
	
	/**
	 * 部门名称
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public String getSalesdeptname() {
		return salesdeptname;
	}
	
	/**
	 * 部门名称
	 * @param salesdeptname
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setSalesdeptname(String salesdeptname) {
		this.salesdeptname = salesdeptname;
	}
	
	/**
	 * 发货金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getSendamount() {
		return sendamount;
	}
	
	/**
	 * 发货金额
	 * @param sendamount
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setSendamount(BigDecimal sendamount) {
		this.sendamount = sendamount;
	}
	
	/**
	 * 发货无税金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getSendnotaxamount() {
		return sendnotaxamount;
	}
	
	/**
	 * 发货无税金额
	 * @param sendnotaxamount
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setSendnotaxamount(BigDecimal sendnotaxamount) {
		this.sendnotaxamount = sendnotaxamount;
	}
	
	/**
	 * 退货金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getReturntaxamount() {
		return returntaxamount;
	}
	
	/**
	 * 退货金额
	 * @param returntaxamount
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setReturntaxamount(BigDecimal returntaxamount) {
		this.returntaxamount = returntaxamount;
	}
	
	/**
	 * 退货无税金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getReturnnotaxamount() {
		return returnnotaxamount;
	}
	
	/**
	 * 退货无税金额
	 * @param returnnotaxamount
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setReturnnotaxamount(BigDecimal returnnotaxamount) {
		this.returnnotaxamount = returnnotaxamount;
	}
	
	/**
	 * 实际应收金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getReceivableamount() {
		return receivableamount;
	}
	
	/**
	 * 实际应收金额
	 * @param receivableamount
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setReceivableamount(BigDecimal receivableamount) {
		this.receivableamount = receivableamount;
	}
	
	/**
	 * 成本金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getCostamount() {
		return costamount;
	}
	
	/**
	 * 成本金额
	 * @param costamount
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setCostamount(BigDecimal costamount) {
		this.costamount = costamount;
	}
	
	/**
	 * 实际毛利率
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getRealrate() {
		return realrate;
	}
	
	/**
	 * 实际毛利率
	 * @param realrate
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setRealrate(BigDecimal realrate) {
		this.realrate = realrate;
	}
	
	/**
	 * 计划毛利率
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getPlanrate() {
		return planrate;
	}
	
	/**
	 * 计划毛利率
	 * @param planrate
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setPlanrate(BigDecimal planrate) {
		this.planrate = planrate;
	}
	
	/**
	 * 已回笼金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getWithdrawnamount() {
		return withdrawnamount;
	}
	
	/**
	 * 已回笼金额
	 * @param withdrawnamount
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setWithdrawnamount(BigDecimal withdrawnamount) {
		this.withdrawnamount = withdrawnamount;
	}
	
	/**
	 * 未回笼金额
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getUnwithdrawnamount() {
		return unwithdrawnamount;
	}
	
	/**
	 * 未回笼金额
	 * @param unwithdrawnamount
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setUnwithdrawnamount(BigDecimal unwithdrawnamount) {
		this.unwithdrawnamount = unwithdrawnamount;
	}
	
	/**
	 * 资金回笼率
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public BigDecimal getWithdrawrate() {
		return withdrawrate;
	}
	
	/**
	 * 资金回笼率
	 * @param withdrawrate
	 * @author panxiaoxiao 
	 * @date Aug 16, 2013
	 */
	public void setWithdrawrate(BigDecimal withdrawrate) {
		this.withdrawrate = withdrawrate;
	}

	public BigDecimal getPushbalanceamount() {
		return pushbalanceamount;
	}

	public void setPushbalanceamount(BigDecimal pushbalanceamount) {
		this.pushbalanceamount = pushbalanceamount;
	}
	
}

