package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaCustomerPayDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 单据编号
     */
    private String billid;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 费用科目
     */
    private String expensesort;

    /**
     * 费用执行时间
     */
    private String executedate;

    /**
     * 费用部门
     */
    private String deptid;

    /**
     * 费用金额
     */
    private BigDecimal amount;

    /**
     * 销售金额
     */
    private BigDecimal salesamount;

    /**
     * 费比
     */
    private BigDecimal rate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 费用科目名称
     */
    private String expensesortname;

    /**
     * 部门名
     */
    private String deptname;

    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 单据编号
     */
    public String getBillid() {
        return billid;
    }

    /**
     * @param billid 
	 *            单据编号
     */
    public void setBillid(String billid) {
        this.billid = billid == null ? null : billid.trim();
    }

    /**
     * @return 品牌编号
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌编号
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 费用科目
     */
    public String getExpensesort() {
        return expensesort;
    }

    /**
     * @param expensesort 
	 *            费用科目
     */
    public void setExpensesort(String expensesort) {
        this.expensesort = expensesort == null ? null : expensesort.trim();
    }

    /**
     * @return 费用执行时间
     */
    public String getExecutedate() {
        return executedate;
    }

    /**
     * @param executedate 
	 *            费用执行时间
     */
    public void setExecutedate(String executedate) {
        this.executedate = executedate == null ? null : executedate.trim();
    }

    /**
     * @return 费用部门
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            费用部门
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 费用金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            费用金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return 销售金额
     */
    public BigDecimal getSalesamount() {
        return salesamount;
    }

    /**
     * @param salesamount 
	 *            销售金额
     */
    public void setSalesamount(BigDecimal salesamount) {
        this.salesamount = salesamount;
    }

    /**
     * @return 费比
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate 
	 *            费比
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getExpensesortname() {
		return expensesortname;
	}

	public void setExpensesortname(String expensesortname) {
		this.expensesortname = expensesortname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
}