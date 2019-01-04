package com.hd.agent.salestarget.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by master on 2016/8/8.
 */
public class SalesTargetTraceData implements Serializable {

    private static final long serialVersionUID = 6595205667200558528L;
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
     * 年-整数
     */
    private Integer iyear;
    /**
     * 月-整数
     */
    private Integer imonth;
    /**
     * 去年年月
     */
    private String lastyearmonth;
    /**
     * 去年
     */
    private String lastyear;
    /**
     * 去年年月
     */
    private String querymonth;
    /**
     * 去年年月
     */
    private Integer iquerymonth;

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
     * 月进度
     */
    private BigDecimal monthtimeschedule;
    /**
     * 年进度
     */
    private BigDecimal yeartimeschedule;
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
     * 去年销售累计
     */
    private BigDecimal qnxslj;
    /**
     * 今年第一目标累计
     */
    private BigDecimal jndyxsmblj;
    /**
     * 今年第二目标累计
     */
    private BigDecimal jndexsmblj;
    /**
     * 今年实际完成累计
     */
    private BigDecimal jnxslj;
    /**
     * 月达成率-第一目标
     */
    private BigDecimal ydcl_dyxsmb;
    /**
     * 月达成率-第二目标
     */
    private BigDecimal ydcl_dexsmb;
    /**
     * 累计达成率-第一目标
     */
    private BigDecimal ljdcl_dyxsmb;
    /**
     * 累计达成率-第二目标
     */
    private BigDecimal ljdcl_dexsmb;
    /**
     * 月差异额-第一目标
     */
    private BigDecimal ycye_dyxsmb;
    /**
     * 月差异额-第二目标
     */
    private BigDecimal ycye_dexsmb;
    /**
     * 累计差异额-第一目标
     */
    private BigDecimal ljcye_dyxsmb;
    /**
     * 累计差异额-第二目标
     */
    private BigDecimal ljcye_dexsmb;
    /**
     * 较去年同期-月
     */
    private BigDecimal jqntq_yxsb;
    /**
     * 较去年同期-累计
     */
    private BigDecimal jqntq_ljxsb;

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

    public Integer getIyear() {
        return iyear;
    }

    public void setIyear(Integer iyear) {
        this.iyear = iyear;
    }

    public Integer getImonth() {
        return imonth;
    }

    public void setImonth(Integer imonth) {
        this.imonth = imonth;
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

    public BigDecimal getQnxslj() {
        return qnxslj;
    }

    public void setQnxslj(BigDecimal qnxslj) {
        this.qnxslj = qnxslj;
    }

    public BigDecimal getJndyxsmblj() {
        return jndyxsmblj;
    }

    public void setJndyxsmblj(BigDecimal jndyxsmblj) {
        this.jndyxsmblj = jndyxsmblj;
    }

    public BigDecimal getJndexsmblj() {
        return jndexsmblj;
    }

    public void setJndexsmblj(BigDecimal jndexsmblj) {
        this.jndexsmblj = jndexsmblj;
    }

    public BigDecimal getJnxslj() {
        return jnxslj;
    }

    public void setJnxslj(BigDecimal jnxslj) {
        this.jnxslj = jnxslj;
    }

    public BigDecimal getYdcl_dyxsmb() {
        return ydcl_dyxsmb;
    }

    public void setYdcl_dyxsmb(BigDecimal ydcl_dyxsmb) {
        this.ydcl_dyxsmb = ydcl_dyxsmb;
    }

    public BigDecimal getYdcl_dexsmb() {
        return ydcl_dexsmb;
    }

    public void setYdcl_dexsmb(BigDecimal ydcl_dexsmb) {
        this.ydcl_dexsmb = ydcl_dexsmb;
    }

    public BigDecimal getLjdcl_dyxsmb() {
        return ljdcl_dyxsmb;
    }

    public void setLjdcl_dyxsmb(BigDecimal ljdcl_dyxsmb) {
        this.ljdcl_dyxsmb = ljdcl_dyxsmb;
    }

    public BigDecimal getLjdcl_dexsmb() {
        return ljdcl_dexsmb;
    }

    public void setLjdcl_dexsmb(BigDecimal ljdcl_dexsmb) {
        this.ljdcl_dexsmb = ljdcl_dexsmb;
    }

    public BigDecimal getYcye_dyxsmb() {
        return ycye_dyxsmb;
    }

    public void setYcye_dyxsmb(BigDecimal ycye_dyxsmb) {
        this.ycye_dyxsmb = ycye_dyxsmb;
    }

    public BigDecimal getYcye_dexsmb() {
        return ycye_dexsmb;
    }

    public void setYcye_dexsmb(BigDecimal ycye_dexsmb) {
        this.ycye_dexsmb = ycye_dexsmb;
    }

    public BigDecimal getLjcye_dyxsmb() {
        return ljcye_dyxsmb;
    }

    public void setLjcye_dyxsmb(BigDecimal ljcye_dyxsmb) {
        this.ljcye_dyxsmb = ljcye_dyxsmb;
    }

    public BigDecimal getLjcye_dexsmb() {
        return ljcye_dexsmb;
    }

    public void setLjcye_dexsmb(BigDecimal ljcye_dexsmb) {
        this.ljcye_dexsmb = ljcye_dexsmb;
    }

    public BigDecimal getJqntq_yxsb() {
        return jqntq_yxsb;
    }

    public void setJqntq_yxsb(BigDecimal jqntq_yxsb) {
        this.jqntq_yxsb = jqntq_yxsb;
    }

    public BigDecimal getJqntq_ljxsb() {
        return jqntq_ljxsb;
    }

    public void setJqntq_ljxsb(BigDecimal jqntq_ljxsb) {
        this.jqntq_ljxsb = jqntq_ljxsb;
    }

    public BigDecimal getYeartimeschedule() {
        return yeartimeschedule;
    }

    public void setYeartimeschedule(BigDecimal yeartimeschedule) {
        this.yeartimeschedule = yeartimeschedule;
    }

    public BigDecimal getMonthtimeschedule() {
        return monthtimeschedule;
    }

    public void setMonthtimeschedule(BigDecimal monthtimeschedule) {
        this.monthtimeschedule = monthtimeschedule;
    }

    public String getQuerymonth() {
        return querymonth;
    }

    public void setQuerymonth(String querymonth) {
        this.querymonth = querymonth == null ? null : querymonth.trim();
    }

    public Integer getIquerymonth() {
        return iquerymonth;
    }

    public void setIquerymonth(Integer iquerymonth) {
        this.iquerymonth = iquerymonth;
    }
}
