package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AdjustMultiPrice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 单据编号
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
     * 调价单名称
     */
    private String name;

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
     * 多价调整单商品明细
     */
    private List<AdjustMultiPriceDetail> adjustMultiPriceDetailList;

    /**
     * @return 单据编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            单据编号
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
     * @return 调价单名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            调价单名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public List<AdjustMultiPriceDetail> getAdjustMultiPriceDetailList() {
        return adjustMultiPriceDetailList;
    }

    public void setAdjustMultiPriceDetailList(List<AdjustMultiPriceDetail> adjustMultiPriceDetailList) {
        this.adjustMultiPriceDetailList = adjustMultiPriceDetailList;
    }
}