/**
 * @(#)BranduserAssessReport.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 11, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class BranduserAssessReport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 品牌业务员
	 */
	private String branduser;
	
	/**
	 * 品牌业务员名称
	 */
	private String brandusername;
	
	/**
	 * 回笼目标金额
	 */
	private BigDecimal wdtargetamount;
	
	/**
	 * 回笼实绩金额
	 */
	private BigDecimal wdaccomplishamount;
	
	/**
	 * 实绩退货
	 */
	private BigDecimal retaccomplishamount;
	
	/**
	 * 退货标准
	 */
	private BigDecimal retstandardamount;
	
	/**
	 * 超退货部分
	 */
	private BigDecimal superretamount;
	
	/**
	 * 核算奖金基数
	 */
	private BigDecimal checkbonusbase;
	
	/**
	 * 合计得分
	 */
	private BigDecimal totalscore;
	
	/**
	 * 回笼奖金金额
	 */
	private BigDecimal wdbonusamount;
	
	/**
	 * 回笼奖金基数
	 */
	private String wdbonusbase;
	
	/**
	 * kpi目标金额
	 */
	private BigDecimal kpitargetamount;
	
	/**
	 * 实绩完成
	 */
	private BigDecimal realaccomplish;
	
	/**
	 * kpi奖金金额
	 */
	private BigDecimal kpibonusamount;
	
	/**
	 * 本月合计奖金
	 */
	private BigDecimal monthtotalamount;

	public String getBranduser() {
		return branduser;
	}

	public void setBranduser(String branduser) {
		this.branduser = branduser;
	}

	public String getBrandusername() {
		return brandusername;
	}

	public void setBrandusername(String brandusername) {
		this.brandusername = brandusername;
	}

	public BigDecimal getWdtargetamount() {
		return wdtargetamount;
	}

	public void setWdtargetamount(BigDecimal wdtargetamount) {
		this.wdtargetamount = wdtargetamount;
	}

	public BigDecimal getWdaccomplishamount() {
		return wdaccomplishamount;
	}

	public void setWdaccomplishamount(BigDecimal wdaccomplishamount) {
		this.wdaccomplishamount = wdaccomplishamount;
	}

	public BigDecimal getRetaccomplishamount() {
		return retaccomplishamount;
	}

	public void setRetaccomplishamount(BigDecimal retaccomplishamount) {
		this.retaccomplishamount = retaccomplishamount;
	}

	public BigDecimal getRetstandardamount() {
		return retstandardamount;
	}

	public void setRetstandardamount(BigDecimal retstandardamount) {
		this.retstandardamount = retstandardamount;
	}

	public BigDecimal getSuperretamount() {
		return superretamount;
	}

	public void setSuperretamount(BigDecimal superretamount) {
		this.superretamount = superretamount;
	}

	public BigDecimal getCheckbonusbase() {
		return checkbonusbase;
	}

	public void setCheckbonusbase(BigDecimal checkbonusbase) {
		this.checkbonusbase = checkbonusbase;
	}

	public BigDecimal getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(BigDecimal totalscore) {
		this.totalscore = totalscore;
	}

	public BigDecimal getWdbonusamount() {
		return wdbonusamount;
	}

	public void setWdbonusamount(BigDecimal wdbonusamount) {
		this.wdbonusamount = wdbonusamount;
	}

	public BigDecimal getKpitargetamount() {
		return kpitargetamount;
	}

	public void setKpitargetamount(BigDecimal kpitargetamount) {
		this.kpitargetamount = kpitargetamount;
	}

	public BigDecimal getRealaccomplish() {
		return realaccomplish;
	}

	public void setRealaccomplish(BigDecimal realaccomplish) {
		this.realaccomplish = realaccomplish;
	}

	public BigDecimal getKpibonusamount() {
		return kpibonusamount;
	}

	public void setKpibonusamount(BigDecimal kpibonusamount) {
		this.kpibonusamount = kpibonusamount;
	}

	public BigDecimal getMonthtotalamount() {
		return monthtotalamount;
	}

	public void setMonthtotalamount(BigDecimal monthtotalamount) {
		this.monthtotalamount = monthtotalamount;
	}

	public String getWdbonusbase() {
		return wdbonusbase;
	}

	public void setWdbonusbase(String wdbonusbase) {
		this.wdbonusbase = wdbonusbase;
	}
}

