package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OaAccessResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
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
     * 项目（费用类别）
     */
    private String expensesort;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 销售金额
     */
    private BigDecimal salesamount;

    /**
     * 执行开始日期
     */
    private String begindate;

    /**
     * 执行结束日期
     */
    private String enddate;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
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
     * @return 项目（费用类别）
     */
    public String getExpensesort() {
        return expensesort;
    }

    /**
     * @param expensesort 
	 *            项目（费用类别）
     */
    public void setExpensesort(String expensesort) {
        this.expensesort = expensesort == null ? null : expensesort.trim();
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
     * @return 执行开始日期
     */
    public String getBegindate() {
        return begindate;
    }

    /**
     * @param begindate 
	 *            执行开始日期
     */
    public void setBegindate(String begindate) {
        this.begindate = begindate == null ? null : begindate.trim();
    }

    /**
     * @return 执行结束日期
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate 
	 *            执行结束日期
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }
}