package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OaGoods implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 业务日期
     */
    private String businessdate;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 制单人编号
     */
    private String adduserid;

    /**
     * 制单人姓名
     */
    private String addusername;

    /**
     * 制单人部门编号
     */
    private String adddeptid;

    /**
     * 制单人部门名称
     */
    private String adddeptname;

    /**
     * 制单时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改人名称
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 审核人编号
     */
    private String audituserid;

    /**
     * 审核人名称
     */
    private String auditusername;

    /**
     * 审核时间
     */
    private Date audittime;

    /**
     * 中止人编号
     */
    private String stopuserid;

    /**
     * 中止人名称
     */
    private String stopusername;

    /**
     * 中止时间
     */
    private Date stoptime;

    /**
     * 关闭时间
     */
    private Date closetime;

    /**
     * 打印次数
     */
    private Integer printtimes;

    /**
     * 供应商编号
     */
    private String supplierid;

    /**
     * 品牌编号
     */
    private String brandid;

    /**
     * 进场商家家数
     */
    private Integer customernum;

    /**
     * 进场费用
     */
    private BigDecimal costamount;

    /**
     * 第一步骤审核人名称
     */
    private String auditname1;

    /**
     * 第一步骤审核时间
     */
    private String auditdate1;

    /**
     * 第2步骤审核人名称
     */
    private String auditname2;

    /**
     * 第2步骤审核时间
     */
    private String auditdate2;

    /**
     * 第3步骤审核人名称
     */
    private String auditname3;

    /**
     * 第3步骤审核时间
     */
    private String auditdate3;

    /**
     * 第4步骤审核人名称
     */
    private String auditname4;

    /**
     * 第4步骤审核时间
     */
    private String auditdate4;

    /**
     * 第5步骤审核人名称
     */
    private String auditname5;

    /**
     * 第5步骤审核时间
     */
    private String auditdate5;

    /**
     * 第6步骤审核人名称
     */
    private String auditname6;

    /**
     * 第6步骤审核时间
     */
    private String auditdate6;

    /**
     * 第7步骤审核人名称
     */
    private String auditname7;

    /**
     * 第7步骤审核时间
     */
    private String auditdate7;

    /**
     * 第8步骤审核人名称
     */
    private String auditname8;

    /**
     * 第8步骤审核时间
     */
    private String auditdate8;

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
     * @return 业务日期
     */
    public String getBusinessdate() {
        return businessdate;
    }

    /**
     * @param businessdate 
	 *            业务日期
     */
    public void setBusinessdate(String businessdate) {
        this.businessdate = businessdate == null ? null : businessdate.trim();
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
     * @return 制单人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            制单人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 制单人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            制单人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 制单人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            制单人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 制单人部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            制单人部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 制单时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            制单时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最后修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最后修改人名称
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人名称
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 最后修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            最后修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 审核人编号
     */
    public String getAudituserid() {
        return audituserid;
    }

    /**
     * @param audituserid 
	 *            审核人编号
     */
    public void setAudituserid(String audituserid) {
        this.audituserid = audituserid == null ? null : audituserid.trim();
    }

    /**
     * @return 审核人名称
     */
    public String getAuditusername() {
        return auditusername;
    }

    /**
     * @param auditusername 
	 *            审核人名称
     */
    public void setAuditusername(String auditusername) {
        this.auditusername = auditusername == null ? null : auditusername.trim();
    }

    /**
     * @return 审核时间
     */
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

    /**
     * @return 中止人编号
     */
    public String getStopuserid() {
        return stopuserid;
    }

    /**
     * @param stopuserid 
	 *            中止人编号
     */
    public void setStopuserid(String stopuserid) {
        this.stopuserid = stopuserid == null ? null : stopuserid.trim();
    }

    /**
     * @return 中止人名称
     */
    public String getStopusername() {
        return stopusername;
    }

    /**
     * @param stopusername 
	 *            中止人名称
     */
    public void setStopusername(String stopusername) {
        this.stopusername = stopusername == null ? null : stopusername.trim();
    }

    /**
     * @return 中止时间
     */
    public Date getStoptime() {
        return stoptime;
    }

    /**
     * @param stoptime 
	 *            中止时间
     */
    public void setStoptime(Date stoptime) {
        this.stoptime = stoptime;
    }

    /**
     * @return 关闭时间
     */
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            关闭时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 打印次数
     */
    public Integer getPrinttimes() {
        return printtimes;
    }

    /**
     * @param printtimes 
	 *            打印次数
     */
    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    /**
     * @return 供应商编号
     */
    public String getSupplierid() {
        return supplierid;
    }

    /**
     * @param supplierid 
	 *            供应商编号
     */
    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
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
     * @return 进场商家家数
     */
    public Integer getCustomernum() {
        return customernum;
    }

    /**
     * @param customernum 
	 *            进场商家家数
     */
    public void setCustomernum(Integer customernum) {
        this.customernum = customernum;
    }

    /**
     * @return 进场费用
     */
    public BigDecimal getCostamount() {
        return costamount;
    }

    /**
     * @param costamount 
	 *            进场费用
     */
    public void setCostamount(BigDecimal costamount) {
        this.costamount = costamount;
    }

    /**
     * @return 第一步骤审核人名称
     */
    public String getAuditname1() {
        return auditname1;
    }

    /**
     * @param auditname1 
	 *            第一步骤审核人名称
     */
    public void setAuditname1(String auditname1) {
        this.auditname1 = auditname1 == null ? null : auditname1.trim();
    }

    /**
     * @return 第一步骤审核时间
     */
    public String getAuditdate1() {
        return auditdate1;
    }

    /**
     * @param auditdate1 
	 *            第一步骤审核时间
     */
    public void setAuditdate1(String auditdate1) {
        this.auditdate1 = auditdate1 == null ? null : auditdate1.trim();
    }

    /**
     * @return 第2步骤审核人名称
     */
    public String getAuditname2() {
        return auditname2;
    }

    /**
     * @param auditname2 
	 *            第2步骤审核人名称
     */
    public void setAuditname2(String auditname2) {
        this.auditname2 = auditname2 == null ? null : auditname2.trim();
    }

    /**
     * @return 第2步骤审核时间
     */
    public String getAuditdate2() {
        return auditdate2;
    }

    /**
     * @param auditdate2 
	 *            第2步骤审核时间
     */
    public void setAuditdate2(String auditdate2) {
        this.auditdate2 = auditdate2 == null ? null : auditdate2.trim();
    }

    /**
     * @return 第3步骤审核人名称
     */
    public String getAuditname3() {
        return auditname3;
    }

    /**
     * @param auditname3 
	 *            第3步骤审核人名称
     */
    public void setAuditname3(String auditname3) {
        this.auditname3 = auditname3 == null ? null : auditname3.trim();
    }

    /**
     * @return 第3步骤审核时间
     */
    public String getAuditdate3() {
        return auditdate3;
    }

    /**
     * @param auditdate3 
	 *            第3步骤审核时间
     */
    public void setAuditdate3(String auditdate3) {
        this.auditdate3 = auditdate3 == null ? null : auditdate3.trim();
    }

    /**
     * @return 第4步骤审核人名称
     */
    public String getAuditname4() {
        return auditname4;
    }

    /**
     * @param auditname4 
	 *            第4步骤审核人名称
     */
    public void setAuditname4(String auditname4) {
        this.auditname4 = auditname4 == null ? null : auditname4.trim();
    }

    /**
     * @return 第4步骤审核时间
     */
    public String getAuditdate4() {
        return auditdate4;
    }

    /**
     * @param auditdate4 
	 *            第4步骤审核时间
     */
    public void setAuditdate4(String auditdate4) {
        this.auditdate4 = auditdate4 == null ? null : auditdate4.trim();
    }

    /**
     * @return 第5步骤审核人名称
     */
    public String getAuditname5() {
        return auditname5;
    }

    /**
     * @param auditname5 
	 *            第5步骤审核人名称
     */
    public void setAuditname5(String auditname5) {
        this.auditname5 = auditname5 == null ? null : auditname5.trim();
    }

    /**
     * @return 第5步骤审核时间
     */
    public String getAuditdate5() {
        return auditdate5;
    }

    /**
     * @param auditdate5 
	 *            第5步骤审核时间
     */
    public void setAuditdate5(String auditdate5) {
        this.auditdate5 = auditdate5 == null ? null : auditdate5.trim();
    }

    /**
     * @return 第6步骤审核人名称
     */
    public String getAuditname6() {
        return auditname6;
    }

    /**
     * @param auditname6 
	 *            第6步骤审核人名称
     */
    public void setAuditname6(String auditname6) {
        this.auditname6 = auditname6 == null ? null : auditname6.trim();
    }

    /**
     * @return 第6步骤审核时间
     */
    public String getAuditdate6() {
        return auditdate6;
    }

    /**
     * @param auditdate6 
	 *            第6步骤审核时间
     */
    public void setAuditdate6(String auditdate6) {
        this.auditdate6 = auditdate6 == null ? null : auditdate6.trim();
    }

    /**
     * @return 第7步骤审核人名称
     */
    public String getAuditname7() {
        return auditname7;
    }

    /**
     * @param auditname7 
	 *            第7步骤审核人名称
     */
    public void setAuditname7(String auditname7) {
        this.auditname7 = auditname7 == null ? null : auditname7.trim();
    }

    /**
     * @return 第7步骤审核时间
     */
    public String getAuditdate7() {
        return auditdate7;
    }

    /**
     * @param auditdate7 
	 *            第7步骤审核时间
     */
    public void setAuditdate7(String auditdate7) {
        this.auditdate7 = auditdate7 == null ? null : auditdate7.trim();
    }

    /**
     * @return 第8步骤审核人名称
     */
    public String getAuditname8() {
        return auditname8;
    }

    /**
     * @param auditname8 
	 *            第8步骤审核人名称
     */
    public void setAuditname8(String auditname8) {
        this.auditname8 = auditname8 == null ? null : auditname8.trim();
    }

    /**
     * @return 第8步骤审核时间
     */
    public String getAuditdate8() {
        return auditdate8;
    }

    /**
     * @param auditdate8 
	 *            第8步骤审核时间
     */
    public void setAuditdate8(String auditdate8) {
        this.auditdate8 = auditdate8 == null ? null : auditdate8.trim();
    }
}