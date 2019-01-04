package com.hd.agent.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门金额
 * @author chenwei
 */
public class DeptAmount implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    private String deptid;
    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 收入金额（借）
     */
    private BigDecimal lendamount;
    /**
     * 支出金额（贷）
     */
    private BigDecimal payamount;
    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * @return 部门编号
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            部门编号
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public BigDecimal getLendamount() {
		return lendamount;
	}

	public BigDecimal getPayamount() {
		return payamount;
	}

	public void setLendamount(BigDecimal lendamount) {
		this.lendamount = lendamount;
	}

	public void setPayamount(BigDecimal payamount) {
		this.payamount = payamount;
	}
    
}