package com.hd.agent.salestarget.model;

import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SalesTargetInput implements Serializable {

    private static final long serialVersionUID = -5404075176736994485L;
    /**
     * 编号
     */
    private String id;

    /**
     * 年月
     */
    private String yearmonth;

    /**
     * 年
     */
    private Integer year;

    /**
     * 月
     */
    private Integer month;

    /**
     * 状态
     */
    private String status;
    /**
     * 状态名称
     */
    private String statusname;
    /**
     * 客户编号
     */
    private String customerid;
    /**
     * 客户名称
     */
    private String customername;

    /**
     * 渠道
     */
    private String customersort;
    /**
     * 渠道名称
     */
    private String customersortname;

    /**
     * 品牌
     */
    private String brandid;
    /**
     * 品牌名称
     */
    private String brandname;

    /**
     * 客户业务员
     */
    private String salesuserid;
    /**
     * 客户业务员名称
     */
    private String salesusername;

    /**
     * 第一销售目标
     */
    private BigDecimal firstsalestarget;

    /**
     * 第一目标毛利
     */
    private BigDecimal firstgrossprofit;

    /**
     * 第一目标毛利率
     */
    private BigDecimal firstgrossprofitrate;

    /**
     * 第二销售目标
     */
    private BigDecimal secondsalestarget;

    /**
     * 第二目标毛利
     */
    private BigDecimal secondgrossprofit;

    /**
     * 第二目标毛利率
     */
    private BigDecimal secondgrossprofitrate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 录入人编号
     */
    private String adduserid;

    /**
     * 录入人名称
     */
    private String addusername;

    /**
     * 录入时间
     */
    private Date addtime;

    private String adddeptid;
    private String adddeptname;

    /**
     * 最新修改者编号
     */
    private String modifyuserid;

    /**
     * 最新修改者名称
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 审核者编号
     */
    private String audituserid;

    /**
     * 审核者名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;
    /**
     * 错误信息
     */
    private String errormessage;

    /**
     * @return 编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 年月
     */
    public String getYearmonth() {
        return yearmonth;
    }

    /**
     * @param yearmonth 
	 *            年月
     */
    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth == null ? null : yearmonth.trim();
    }

    /**
     * @return 年
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year 
	 *            年
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return 月
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month 
	 *            月
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }


    /**
     * @return 渠道
     */
    public String getCustomersort() {
        return customersort;
    }

    /**
     * @param customersort
	 *            渠道
     */
    public void setCustomersort(String customersort) {
        this.customersort = customersort == null ? null : customersort.trim();
    }

    /**
     * @return 品牌
     */
    public String getBrandid() {
        return brandid;
    }

    /**
     * @param brandid 
	 *            品牌
     */
    public void setBrandid(String brandid) {
        this.brandid = brandid == null ? null : brandid.trim();
    }

    /**
     * @return 客户业务员
     */
    public String getSalesuserid() {
        return salesuserid;
    }

    /**
     * @param salesuserid 
	 *            客户业务员
     */
    public void setSalesuserid(String salesuserid) {
        this.salesuserid = salesuserid == null ? null : salesuserid.trim();
    }

    /**
     * @return 第一销售目标
     */
    public BigDecimal getFirstsalestarget() {
        return firstsalestarget;
    }

    /**
     * @param firstsalestarget 
	 *            第一销售目标
     */
    public void setFirstsalestarget(BigDecimal firstsalestarget) {
        this.firstsalestarget = firstsalestarget;
    }

    /**
     * @return 第一目标毛利
     */
    public BigDecimal getFirstgrossprofit() {
        return firstgrossprofit;
    }

    /**
     * @param firstgrossprofit 
	 *            第一目标毛利
     */
    public void setFirstgrossprofit(BigDecimal firstgrossprofit) {
        this.firstgrossprofit = firstgrossprofit;
    }

    /**
     * @return 第一目标毛利率
     */
    public BigDecimal getFirstgrossprofitrate() {
        return firstgrossprofitrate;
    }

    /**
     * @param firstgrossprofitrate
	 *            第一目标毛利率
     */
    public void setFirstgrossprofitrate(BigDecimal firstgrossprofitrate) {
        this.firstgrossprofitrate = firstgrossprofitrate;
    }

    /**
     * @return 第二销售目标
     */
    public BigDecimal getSecondsalestarget() {
        return secondsalestarget;
    }

    /**
     * @param secondsalestarget 
	 *            第二销售目标
     */
    public void setSecondsalestarget(BigDecimal secondsalestarget) {
        this.secondsalestarget = secondsalestarget;
    }

    /**
     * @return 第二目标毛利
     */
    public BigDecimal getSecondgrossprofit() {
        return secondgrossprofit;
    }

    /**
     * @param secondgrossprofit 
	 *            第二目标毛利
     */
    public void setSecondgrossprofit(BigDecimal secondgrossprofit) {
        this.secondgrossprofit = secondgrossprofit;
    }

    /**
     * @return 第二目标毛利率
     */
    public BigDecimal getSecondgrossprofitrate() {
        return secondgrossprofitrate;
    }

    /**
     * @param secondgrossprofitrate 
	 *            第二目标毛利率
     */
    public void setSecondgrossprofitrate(BigDecimal secondgrossprofitrate) {
        this.secondgrossprofitrate = secondgrossprofitrate;
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

    /**
     * @return 录入人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            录入人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 录入人名称
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            录入人名称
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 录入时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            录入时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最新修改者编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最新修改者编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最新修改者名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最新修改者名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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

    /**
     * @return 审核者编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核者编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核者名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核者名称
     */
    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername == null ? null : auditusername.trim();
    }

    /**
     * @return 审核时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAudittime() {
        return audittime;
    }

    /**
     * @param audittime 
	 *            审核时间
     */
    public void setAudittime(Date audittime) {
        this.audittime = audittime;
    }

    public String getCustomersortname() {
        return customersortname;
    }

    public void setCustomersortname(String customersortname) {
        this.customersortname = customersortname == null ? null : customersortname.trim();
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname == null ? null : brandname.trim();
    }

    public String getAdddeptname() {
        return adddeptname;
    }

    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    public String getAdddeptid() {
        return adddeptid;
    }

    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    public String getSalesusername() {
        return salesusername;
    }

    public void setSalesusername(String salesusername) {
        this.salesusername = salesusername == null ? null : salesusername.trim();
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname == null ? null : statusname.trim();
    }


    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }
    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage == null ? null : errormessage.trim();
    }
}