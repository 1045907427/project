package com.hd.agent.crm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CrmVisitPlan implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 478090325819435332L;
    /**
     * 业务属性
     */
    private String employetype;
    /**
     * 业务员姓名
     */
    private String name ;
    /**
     * 品牌名称
     */
    private String[] brandname;

	/**
     * 编号
     */
    private String id;

    /**
     * 人员编号(人员档案)
     */
    private String personid;

    /**
     * 人员名称
     */
    private String personname;

    /**
     * 主管编号(人员档案)
     */
    private String leadid;

    /**
     * 主管名称
     */
    private String leadname;
    /**
     * 路线名称
     */
    private String wayname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 月份
     */
    private Integer month;

    /**
     * 周
     */
    private Integer week;

    /**
     * 状态4新增3暂存2保存1启用0禁用
     */
    private String state;

    /**
     * 建档人
     */
    private String adduserid;

    /**
     * 建档人
     */
    private String addusername;

    /**
     * 建档部门
     */
    private String adddeptid;

    /**
     * 建档部门
     */
    private String adddeptname;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 最后修改人
     */
    private String modifyuserid;

    /**
     * 最后修改人
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 启用人
     */
    private String openuserid;

    /**
     * 启用人
     */
    private String openusername;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人
     */
    private String closeuserid;

    /**
     * 禁用人
     */
    private String closeusername;

    /**
     * 禁用时间
     */
    private Date closetime;

    /**
     * 品牌列表
     */
    private String brands;

    private List<CrmVisitPlanDetail> detailList;
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
     * @return 人员编号(人员档案)
     */
    public String getPersonid() {
        return personid;
    }

    /**
     * @param personid 
	 *            人员编号(人员档案)
     */
    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }

    /**
     * @return 主管编号(人员档案)
     */
    public String getLeadid() {
        return leadid;
    }

    /**
     * @param leadid 
	 *            主管编号(人员档案)
     */
    public void setLeadid(String leadid) {
        this.leadid = leadid == null ? null : leadid.trim();
    }

    /**
     * @return 路线名称
     */
    public String getWayname() {
        return wayname;
    }

    /**
     * @param wayname 
	 *            路线名称
     */
    public void setWayname(String wayname) {
        this.wayname = wayname == null ? null : wayname.trim();
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
     * @return 月份
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month 
	 *            月份
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return 周
     */
    public Integer getWeek() {
        return week;
    }

    /**
     * @param week 
	 *            周
     */
    public void setWeek(Integer week) {
        this.week = week;
    }

    /**
     * @return 状态4新增3暂存2保存1启用0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态4新增3暂存2保存1启用0禁用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 建档人
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档人
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            建档人
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 建档部门
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档部门
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 建档部门
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            建档部门
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 建档时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            建档时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 最后修改人
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            最后修改人
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 最后修改人
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人
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
     * @return 启用人
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
    }

    /**
     * @return 启用人
     */
    public String getOpenusername() {
        return openusername;
    }

    /**
     * @param openusername 
	 *            启用人
     */
    public void setOpenusername(String openusername) {
        this.openusername = openusername == null ? null : openusername.trim();
    }

    /**
     * @return 启用时间
     */
    public Date getOpentime() {
        return opentime;
    }

    /**
     * @param opentime 
	 *            启用时间
     */
    public void setOpentime(Date opentime) {
        this.opentime = opentime;
    }

    /**
     * @return 禁用人
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
    }

    /**
     * @return 禁用人
     */
    public String getCloseusername() {
        return closeusername;
    }

    /**
     * @param closeusername 
	 *            禁用人
     */
    public void setCloseusername(String closeusername) {
        this.closeusername = closeusername == null ? null : closeusername.trim();
    }

    /**
     * @return 禁用时间
     */
    public Date getClosetime() {
        return closetime;
    }

    /**
     * @param closetime 
	 *            禁用时间
     */
    public void setClosetime(Date closetime) {
        this.closetime = closetime;
    }

    /**
     * @return 品牌列表
     */
    public String getBrands() {
        return brands;
    }

    /**
     * @param brands 
	 *            品牌列表
     */
    public void setBrands(String brands) {
        this.brands = brands == null ? null : brands.trim();
    }

    public String[] getBrandname() {
        return brandname;
    }

    public void setBrandname(String[] brandname) {
        this.brandname = brandname;
    }

    public String getEmployetype() {
        return employetype;
    }

    public void setEmployetype(String employetype) {
        this.employetype = employetype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeadname() {
        return leadname;
    }

    public void setLeadname(String leadname) {
        this.leadname = leadname;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public List<CrmVisitPlanDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<CrmVisitPlanDetail> detailList) {
        this.detailList = detailList;
    }
}