package com.hd.agent.salestarget.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by master on 2016/8/8.
 */
public class SalesTargetGrossTraceData implements Serializable {

    private static final long serialVersionUID = 8454686006364800225L;
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
    private BigDecimal qnml;
    /**
     * 今年第一销售目标
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
     * 去年销售累计
     */
    private BigDecimal qnmllj;
    /**
     * 今年第一目标累计
     */
    private BigDecimal jndymlmblj;
    /**
     * 今年第二目标累计
     */
    private BigDecimal jndemlmblj;
    /**
     * 今年实际完成累计
     */
    private BigDecimal jnmllj;
    /**
     * 月达成率-第一目标
     */
    private BigDecimal ydcl_dymlmb;
    /**
     * 月达成率-第二目标
     */
    private BigDecimal ydcl_demlmb;
    /**
     * 累计达成率-第一目标
     */
    private BigDecimal ljdcl_dymlmb;
    /**
     * 累计达成率-第二目标
     */
    private BigDecimal ljdcl_demlmb;
    /**
     * 月差异额-第一目标
     */
    private BigDecimal ycye_dymlmb;
    /**
     * 月差异额-第二目标
     */
    private BigDecimal ycye_demlmb;
    /**
     * 累计差异额-第一目标
     */
    private BigDecimal ljcye_dymlmb;
    /**
     * 累计差异额-第二目标
     */
    private BigDecimal ljcye_demlmb;
    /**
     * 较去年同期-月
     */
    private BigDecimal jqntq_ymlb;
    /**
     * 较去年同期-累计
     */
    private BigDecimal jqntq_ljmlb;
    /**
     * 今年销售
     */
    private BigDecimal jnxs;
    /**
     * 今年销售累计
     */
    private BigDecimal jnxslj;
    /**
     * 今年毛利率
     */
    private BigDecimal jnmll;

    /**
     * 今年累计毛利率
     * @return
     */
    private BigDecimal jnmllljb;

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

    public BigDecimal getQnmllj() {
        return qnmllj;
    }

    public void setQnmllj(BigDecimal qnmllj) {
        this.qnmllj = qnmllj;
    }

    public BigDecimal getJndymlmblj() {
        return jndymlmblj;
    }

    public void setJndymlmblj(BigDecimal jndymlmblj) {
        this.jndymlmblj = jndymlmblj;
    }

    public BigDecimal getJndemlmblj() {
        return jndemlmblj;
    }

    public void setJndemlmblj(BigDecimal jndemlmblj) {
        this.jndemlmblj = jndemlmblj;
    }

    public BigDecimal getJnmllj() {
        return jnmllj;
    }

    public void setJnmllj(BigDecimal jnmllj) {
        this.jnmllj = jnmllj;
    }

    public BigDecimal getYdcl_dymlmb() {
        return ydcl_dymlmb;
    }

    public void setYdcl_dymlmb(BigDecimal ydcl_dymlmb) {
        this.ydcl_dymlmb = ydcl_dymlmb;
    }

    public BigDecimal getYdcl_demlmb() {
        return ydcl_demlmb;
    }

    public void setYdcl_demlmb(BigDecimal ydcl_demlmb) {
        this.ydcl_demlmb = ydcl_demlmb;
    }

    public BigDecimal getLjdcl_dymlmb() {
        return ljdcl_dymlmb;
    }

    public void setLjdcl_dymlmb(BigDecimal ljdcl_dymlmb) {
        this.ljdcl_dymlmb = ljdcl_dymlmb;
    }

    public BigDecimal getLjdcl_demlmb() {
        return ljdcl_demlmb;
    }

    public void setLjdcl_demlmb(BigDecimal ljdcl_demlmb) {
        this.ljdcl_demlmb = ljdcl_demlmb;
    }

    public BigDecimal getYcye_dymlmb() {
        return ycye_dymlmb;
    }

    public void setYcye_dymlmb(BigDecimal ycye_dymlmb) {
        this.ycye_dymlmb = ycye_dymlmb;
    }

    public BigDecimal getYcye_demlmb() {
        return ycye_demlmb;
    }

    public void setYcye_demlmb(BigDecimal ycye_demlmb) {
        this.ycye_demlmb = ycye_demlmb;
    }

    public BigDecimal getLjcye_dymlmb() {
        return ljcye_dymlmb;
    }

    public void setLjcye_dymlmb(BigDecimal ljcye_dymlmb) {
        this.ljcye_dymlmb = ljcye_dymlmb;
    }

    public BigDecimal getLjcye_demlmb() {
        return ljcye_demlmb;
    }

    public void setLjcye_demlmb(BigDecimal ljcye_demlmb) {
        this.ljcye_demlmb = ljcye_demlmb;
    }

    public BigDecimal getJqntq_ymlb() {
        return jqntq_ymlb;
    }

    public void setJqntq_ymlb(BigDecimal jqntq_ymlb) {
        this.jqntq_ymlb = jqntq_ymlb;
    }

    public BigDecimal getJqntq_ljmlb() {
        return jqntq_ljmlb;
    }

    public void setJqntq_ljmlb(BigDecimal jqntq_ljmlb) {
        this.jqntq_ljmlb = jqntq_ljmlb;
    }

    /**
     * 今年毛利率
     */
    public BigDecimal getJnmll() {
        return jnmll;
    }

    /**
     * 今年毛利率
     * @param jnmll
     */
    public void setJnmll(BigDecimal jnmll) {
        this.jnmll = jnmll;
    }

    public BigDecimal getJnxs() {
        return jnxs;
    }

    public void setJnxs(BigDecimal jnxs) {
        this.jnxs = jnxs;
    }

    public BigDecimal getJnmllljb() {
        return jnmllljb;
    }

    public void setJnmllljb(BigDecimal jnmllljb) {
        this.jnmllljb = jnmllljb;
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

    public String getLastyear() {
        return lastyear;
    }

    public void setLastyear(String lastyear) {
        this.lastyear = lastyear == null ? null : lastyear.trim();
    }

    public BigDecimal getJnxslj() {
        return jnxslj;
    }

    public void setJnxslj(BigDecimal jnxslj) {
        this.jnxslj = jnxslj;
    }
}
