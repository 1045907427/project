package com.hd.agent.salestarget.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by master on 2016/8/8.
 */
public class SalesTargetMonthAnalyzeData implements Serializable {

    private static final long serialVersionUID = 3415813360824209815L;
    /**
     * 更新日期
     */
    private String businessdate;
    private String businessyear;
    private String businessmonth;
    private String businessday;
    /**
     * 年月
     */
    private String yearmonth;
    /**
     * 年
     */
    private String year;
    /**
     * 月
     */
    private String month;
    /**
     * 去年年月
     */
    private String lastyearmonth;

    /**
     * 去年
     */
    private String lastyear;
    /**
     * 销售业务员
     */
    private String salesuerid;
    /**
     * 销售业务员名称
     */
    private String salesuername;
    /**
     * 品牌
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;
    /**
     * 客户分类
     */
    private String customersort;
    /**
     * 时间进度
     */
    private BigDecimal timeschedule;
    /**
     * 去年销售
     */
    private BigDecimal qnxs;
    /**
     * 今年第一销售目标
     */
    private BigDecimal jndyxsmb;
    /**
     * 今年第二目标
     */
    private BigDecimal jndexsmb;
    /**
     * 今年实际完成
     */
    private BigDecimal jnxs;
    /**
     * 去年同期对比
     */
    private BigDecimal jqnxstqb;
    /**
     * 第一目标 达成率
     */
    private BigDecimal dcl_dyxsmb;
    /**
     * 第二目标 达成率
     */
    private BigDecimal dcl_dexsmb;
    /**
     * 第一目标对应进度差额
     */
    private BigDecimal jdce_dyxsmb;
    /**
     * 差异额-第一目标
     */
    private BigDecimal cye_dyxsmb;
    /**
     * 差异额-第二目标
     */
    private BigDecimal cye_dexsmb;

    /**
     * 去年销售毛利
     */
    private BigDecimal qnml;
    /**
     * 今年第一销售毛利目标
     */
    private BigDecimal jndymlmb;
    /**
     * 今年第二目标
     */
    private BigDecimal jndemlmb;
    /**
     * 今年实际完成
     */
    private BigDecimal jnml;
    /**
     * 毛利率
     */
    private BigDecimal jnmll;
    /**
     * 去年同期对比
     */
    private BigDecimal jqnmltqb;
    /**
     * 第一目标 达成率
     */
    private BigDecimal dcl_dymlmb;
    /**
     * 第二目标 达成率
     */
    private BigDecimal dcl_demlmb;
    /**
     * 第一目标对应进度差额
     */
    private BigDecimal jdce_dymlmb;
    /**
     * 差异额-第一目标
     */
    private BigDecimal cye_dymlmb;
    /**
     * 差异额-第二目标
     */
    private BigDecimal cye_demlmb;

    public String getBusinessdate() {
        return businessdate;
    }

    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
    }

    public String getBusinessyear() {
        return businessyear;
    }

    public void setBusinessyear(String businessyear) {
        this.businessyear = businessyear == null ? null : businessyear.trim();
    }

    public String getBusinessmonth() {
        return businessmonth;
    }

    public void setBusinessmonth(String businessmonth) {
        this.businessmonth = businessmonth == null ? null : businessmonth.trim();
    }

    public String getBusinessday() {
        return businessday;
    }

    public void setBusinessday(String businessday) {
        this.businessday = businessday == null ? null : businessday.trim();
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth == null ? null : yearmonth.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getLastyearmonth() {
        return lastyearmonth;
    }

    public void setLastyearmonth(String lastyearmonth) {
        this.lastyearmonth = lastyearmonth == null ? null : lastyearmonth.trim();
    }

    public String getLastyear() {
        return lastyear;
    }

    public void setLastyear(String lastyear) {
        this.lastyear = lastyear == null ? null : lastyear.trim();
    }

    public String getSalesuerid() {
        return salesuerid;
    }

    public void setSalesuerid(String salesuerid) {
        this.salesuerid = salesuerid == null ? null : salesuerid.trim();
    }

    public String getSalesuername() {
        return salesuername;
    }

    public void setSalesuername(String salesuername) {
        this.salesuername = salesuername == null ? null : salesuername.trim();
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
    }

    public String getCustomersort() {
        return customersort;
    }

    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
    }

    public BigDecimal getTimeschedule() {
        return timeschedule;
    }

    public void setTimeschedule(BigDecimal timeschedule) {
        this.timeschedule = timeschedule;
    }

    public BigDecimal getQnxs() {
        return qnxs;
    }

    public void setQnxs(BigDecimal qnxs) {
        this.qnxs = qnxs;
    }

    public BigDecimal getJndyxsmb() {
        return jndyxsmb;
    }

    public void setJndyxsmb(BigDecimal jndyxsmb) {
        this.jndyxsmb = jndyxsmb;
    }

    public BigDecimal getJndexsmb() {
        return jndexsmb;
    }

    public void setJndexsmb(BigDecimal jndexsmb) {
        this.jndexsmb = jndexsmb;
    }

    public BigDecimal getJnxs() {
        return jnxs;
    }

    public void setJnxs(BigDecimal jnxs) {
        this.jnxs = jnxs;
    }

    public BigDecimal getJqnxstqb() {
        return jqnxstqb;
    }

    public void setJqnxstqb(BigDecimal jqnxstqb) {
        this.jqnxstqb = jqnxstqb;
    }

    public BigDecimal getDcl_dyxsmb() {
        return dcl_dyxsmb;
    }

    public void setDcl_dyxsmb(BigDecimal dcl_dyxsmb) {
        this.dcl_dyxsmb = dcl_dyxsmb;
    }

    public BigDecimal getDcl_dexsmb() {
        return dcl_dexsmb;
    }

    public void setDcl_dexsmb(BigDecimal dcl_dexsmb) {
        this.dcl_dexsmb = dcl_dexsmb;
    }

    public BigDecimal getJdce_dyxsmb() {
        return jdce_dyxsmb;
    }

    public void setJdce_dyxsmb(BigDecimal jdce_dyxsmb) {
        this.jdce_dyxsmb = jdce_dyxsmb;
    }

    public BigDecimal getCye_dyxsmb() {
        return cye_dyxsmb;
    }

    public void setCye_dyxsmb(BigDecimal cye_dyxsmb) {
        this.cye_dyxsmb = cye_dyxsmb;
    }

    public BigDecimal getCye_dexsmb() {
        return cye_dexsmb;
    }

    public void setCye_dexsmb(BigDecimal cye_dexsmb) {
        this.cye_dexsmb = cye_dexsmb;
    }

    public BigDecimal getQnml() {
        return qnml;
    }

    public void setQnml(BigDecimal qnml) {
        this.qnml = qnml;
    }

    public BigDecimal getJndymlmb() {
        return jndymlmb;
    }

    public void setJndymlmb(BigDecimal jndymlmb) {
        this.jndymlmb = jndymlmb;
    }

    public BigDecimal getJndemlmb() {
        return jndemlmb;
    }

    public void setJndemlmb(BigDecimal jndemlmb) {
        this.jndemlmb = jndemlmb;
    }

    public BigDecimal getJnml() {
        return jnml;
    }

    public void setJnml(BigDecimal jnml) {
        this.jnml = jnml;
    }

    public BigDecimal getJnmll() {
        return jnmll;
    }

    public void setJnmll(BigDecimal jnmll) {
        this.jnmll = jnmll;
    }

    public BigDecimal getJqnmltqb() {
        return jqnmltqb;
    }

    public void setJqnmltqb(BigDecimal jqnmltqb) {
        this.jqnmltqb = jqnmltqb;
    }

    public BigDecimal getDcl_dymlmb() {
        return dcl_dymlmb;
    }

    public void setDcl_dymlmb(BigDecimal dcl_dymlmb) {
        this.dcl_dymlmb = dcl_dymlmb;
    }

    public BigDecimal getDcl_demlmb() {
        return dcl_demlmb;
    }

    public void setDcl_demlmb(BigDecimal dcl_demlmb) {
        this.dcl_demlmb = dcl_demlmb;
    }

    public BigDecimal getJdce_dymlmb() {
        return jdce_dymlmb;
    }

    public void setJdce_dymlmb(BigDecimal jdce_dymlmb) {
        this.jdce_dymlmb = jdce_dymlmb;
    }

    public BigDecimal getCye_dymlmb() {
        return cye_dymlmb;
    }

    public void setCye_dymlmb(BigDecimal cye_dymlmb) {
        this.cye_dymlmb = cye_dymlmb;
    }

    public BigDecimal getCye_demlmb() {
        return cye_demlmb;
    }

    public void setCye_demlmb(BigDecimal cye_demlmb) {
        this.cye_demlmb = cye_demlmb;
    }
}
