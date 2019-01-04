package com.hd.agent.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OaAccess implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 相关确认单号
     */
    private String confirmid;

    /**
     * OA编号
     */
    private String oaid;

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
     * 客户编号
     */
    private String customerid;

    /**
     * 活动主题名称
     */
    private String theme;

    /**
     * 计划开始日期
     */
    private String planbegindate;

    /**
     * 计划结束日期
     */
    private String planenddate;

    /**
     * 通路单费用类别
     */
    private String expensesort;

    /**
     * 申请特价类别：1补差特价2降价特价
     */
    private String pricetype;

    /**
     * 是否补库存1是0否
     */
    private String isaddstorage;

    /**
     * 销售区域
     */
    private String salesarea;

    /**
     * 区域名称
     */
    private String salesareaname;

    /**
     * 执行地点
     */
    private String executeaddr;

    /**
     * 第一部分说明备注
     */
    private String remark1;

    /**
     * 收回方式
     */
    private String reimbursetype;

    /**
     * 收回日期
     */
    private String reimbursedate;

    /**
     * 支付方式
     */
    private String paytype;

    /**
     * 支付日期
     */
    private String paydate;

    /**
     * 总数量
     */
    private String totalnum;

    /**
     * 费用总金额
     */
    private BigDecimal totalamount;

    /**
     * 确认开始日期
     */
    private String conbegindate;

    /**
     * 确认结束日期
     */
    private String conenddate;

    /**
     * 电脑开始日期
     */
    private String combegindate;

    /**
     * 电脑结束日期
     */
    private String comenddate;

    /**
     * 第二部分说明备注
     */
    private String remark2;

    /**
     * 自理金额
     */
    private BigDecimal myamount;

    /**
     * 工厂金额
     */
    private BigDecimal factoryamount;

    /**
     * 电脑折让金额
     */
    private BigDecimal compdiscount;

    /**
     * 电脑降价金额
     */
    private BigDecimal comdownamount;

    /**
     * 支票金额
     */
    private BigDecimal payamount;

    /**
     * 结算金额（转入分公司）
     */
    private BigDecimal branchaccount;

    /**
     * 是否发票支付1是0否
     */
    private String isbillpay;

    /**
     * 是否包含附件1是0否
     */
    private String isfile;
    
    /**
     * 是否自动分配 1：自动分配；0：不自动分配
     */
    private String autoassign;

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
     * @return 相关确认单号
     */
    public String getConfirmid() {
        return confirmid;
    }

    /**
     * @param confirmid 
	 *            相关确认单号
     */
    public void setConfirmid(String confirmid) {
        this.confirmid = confirmid == null ? null : confirmid.trim();
    }

    /**
     * @return OA编号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * @param oaid 
	 *            OA编号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
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
     * @return 客户编号
     */
    public String getCustomerid() {
        return customerid;
    }

    /**
     * @param customerid 
	 *            客户编号
     */
    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
    }

    /**
     * @return 活动主题名称
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme 
	 *            活动主题名称
     */
    public void setTheme(String theme) {
        this.theme = theme == null ? null : theme.trim();
    }

    /**
     * @return 计划开始日期
     */
    public String getPlanbegindate() {
        return planbegindate;
    }

    /**
     * @param planbegindate 
	 *            计划开始日期
     */
    public void setPlanbegindate(String planbegindate) {
        this.planbegindate = planbegindate == null ? null : planbegindate.trim();
    }

    /**
     * @return 计划结束日期
     */
    public String getPlanenddate() {
        return planenddate;
    }

    /**
     * @param planenddate 
	 *            计划结束日期
     */
    public void setPlanenddate(String planenddate) {
        this.planenddate = planenddate == null ? null : planenddate.trim();
    }

    /**
     * @return 通路单费用类别
     */
    public String getExpensesort() {
        return expensesort;
    }

    /**
     * @param expensesort 
	 *            通路单费用类别
     */
    public void setExpensesort(String expensesort) {
        this.expensesort = expensesort == null ? null : expensesort.trim();
    }

    /**
     * @return 申请特价类别：1补差特价2降价特价
     */
    public String getPricetype() {
        return pricetype;
    }

    /**
     * @param pricetype 
	 *            申请特价类别：1补差特价2降价特价
     */
    public void setPricetype(String pricetype) {
        this.pricetype = pricetype == null ? null : pricetype.trim();
    }

    /**
     * @return 是否补库存1是0否
     */
    public String getIsaddstorage() {
        return isaddstorage;
    }

    /**
     * @param isaddstorage 
	 *            是否补库存1是0否
     */
    public void setIsaddstorage(String isaddstorage) {
        this.isaddstorage = isaddstorage == null ? null : isaddstorage.trim();
    }

    /**
     * @return 销售区域
     */
    public String getSalesarea() {
        return salesarea;
    }

    /**
     * @param salesarea 
	 *            销售区域
     */
    public void setSalesarea(String salesarea) {
        this.salesarea = salesarea == null ? null : salesarea.trim();
    }

    /**
     * @return 区域名称
     */
    public String getSalesareaname() {
        return salesareaname;
    }

    /**
     * @param salesareaname 
	 *            区域名称
     */
    public void setSalesareaname(String salesareaname) {
        this.salesareaname = salesareaname == null ? null : salesareaname.trim();
    }

    /**
     * @return 执行地点
     */
    public String getExecuteaddr() {
        return executeaddr;
    }

    /**
     * @param executeaddr 
	 *            执行地点
     */
    public void setExecuteaddr(String executeaddr) {
        this.executeaddr = executeaddr == null ? null : executeaddr.trim();
    }

    /**
     * @return 第一部分说明备注
     */
    public String getRemark1() {
        return remark1;
    }

    /**
     * @param remark1 
	 *            第一部分说明备注
     */
    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    /**
     * @return 收回方式
     */
    public String getReimbursetype() {
        return reimbursetype;
    }

    /**
     * @param reimbursetype 
	 *            收回方式
     */
    public void setReimbursetype(String reimbursetype) {
        this.reimbursetype = reimbursetype == null ? null : reimbursetype.trim();
    }

    /**
     * @return 收回日期
     */
    public String getReimbursedate() {
        return reimbursedate;
    }

    /**
     * @param reimbursedate 
	 *            收回日期
     */
    public void setReimbursedate(String reimbursedate) {
        this.reimbursedate = reimbursedate == null ? null : reimbursedate.trim();
    }

    /**
     * @return 支付方式
     */
    public String getPaytype() {
        return paytype;
    }

    /**
     * @param paytype 
	 *            支付方式
     */
    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    /**
     * @return 支付日期
     */
    public String getPaydate() {
        return paydate;
    }

    /**
     * @param paydate 
	 *            支付日期
     */
    public void setPaydate(String paydate) {
        this.paydate = paydate == null ? null : paydate.trim();
    }

    /**
     * @return 总数量
     */
    public String getTotalnum() {
        return totalnum;
    }

    /**
     * @param totalnum 
	 *            总数量
     */
    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum == null ? null : totalnum.trim();
    }

    /**
     * @return 费用总金额
     */
    public BigDecimal getTotalamount() {
        return totalamount;
    }

    /**
     * @param totalamount 
	 *            费用总金额
     */
    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    /**
     * @return 确认开始日期
     */
    public String getConbegindate() {
        return conbegindate;
    }

    /**
     * @param conbegindate 
	 *            确认开始日期
     */
    public void setConbegindate(String conbegindate) {
        this.conbegindate = conbegindate == null ? null : conbegindate.trim();
    }

    /**
     * @return 确认结束日期
     */
    public String getConenddate() {
        return conenddate;
    }

    /**
     * @param conenddate 
	 *            确认结束日期
     */
    public void setConenddate(String conenddate) {
        this.conenddate = conenddate == null ? null : conenddate.trim();
    }

    /**
     * @return 电脑开始日期
     */
    public String getCombegindate() {
        return combegindate;
    }

    /**
     * @param combegindate 
	 *            电脑开始日期
     */
    public void setCombegindate(String combegindate) {
        this.combegindate = combegindate == null ? null : combegindate.trim();
    }

    /**
     * @return 电脑结束日期
     */
    public String getComenddate() {
        return comenddate;
    }

    /**
     * @param comenddate 
	 *            电脑结束日期
     */
    public void setComenddate(String comenddate) {
        this.comenddate = comenddate == null ? null : comenddate.trim();
    }

    /**
     * @return 第二部分说明备注
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * @param remark2 
	 *            第二部分说明备注
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    /**
     * @return 自理金额
     */
    public BigDecimal getMyamount() {
        return myamount;
    }

    /**
     * @param myamount 
	 *            自理金额
     */
    public void setMyamount(BigDecimal myamount) {
        this.myamount = myamount;
    }

    /**
     * @return 工厂金额
     */
    public BigDecimal getFactoryamount() {
        return factoryamount;
    }

    /**
     * @param factoryamount 
	 *            工厂金额
     */
    public void setFactoryamount(BigDecimal factoryamount) {
        this.factoryamount = factoryamount;
    }

    /**
     * @return 电脑折让金额
     */
    public BigDecimal getCompdiscount() {
        return compdiscount;
    }

    /**
     * @param compdiscount 
	 *            电脑折让金额
     */
    public void setCompdiscount(BigDecimal compdiscount) {
        this.compdiscount = compdiscount;
    }

    /**
     * @return 电脑降价金额
     */
    public BigDecimal getComdownamount() {
        return comdownamount;
    }

    /**
     * @param comdownamount 
	 *            电脑降价金额
     */
    public void setComdownamount(BigDecimal comdownamount) {
        this.comdownamount = comdownamount;
    }

    /**
     * @return 支票金额
     */
    public BigDecimal getPayamount() {
        return payamount;
    }

    /**
     * @param payamount 
	 *            支票金额
     */
    public void setPayamount(BigDecimal payamount) {
        this.payamount = payamount;
    }

    /**
     * @return 结算金额（转入分公司）
     */
    public BigDecimal getBranchaccount() {
        return branchaccount;
    }

    /**
     * @param branchaccount 
	 *            结算金额（转入分公司）
     */
    public void setBranchaccount(BigDecimal branchaccount) {
        this.branchaccount = branchaccount;
    }

    /**
     * @return 是否发票支付1是0否
     */
    public String getIsbillpay() {
        return isbillpay;
    }

    /**
     * @param isbillpay 
	 *            是否发票支付1是0否
     */
    public void setIsbillpay(String isbillpay) {
        this.isbillpay = isbillpay == null ? null : isbillpay.trim();
    }

    /**
     * @return 是否包含附件1是0否
     */
    public String getIsfile() {
        return isfile;
    }

    /**
     * @param isfile 
	 *            是否包含附件1是0否
     */
    public void setIsfile(String isfile) {
        this.isfile = isfile == null ? null : isfile.trim();
    }

	public String getAutoassign() {
		return autoassign;
	}

	public void setAutoassign(String autoassign) {
		this.autoassign = autoassign;
	}
}