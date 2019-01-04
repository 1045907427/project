package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

public class Accounting implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    private String id;

    /**
     * 开始日期
     */
    private String begindate;

    /**
     * 结束日期
     */
    private String enddate;

    /**
     * 月度
     */
    private String belongmonth;

    /**
     * 会计年度
     */
    private String year;

    /**
     * 状态4新增3暂存2保存1启用0禁用
     */
    private String state;
    
    private String stateName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 建档人用户编号
     */
    private String adduserid;

    /**
     * 建档人姓名
     */
    private String addusername;

    /**
     * 建档人部门编号
     */
    private String adddeptid;

    /**
     * 建档部门名称
     */
    private String adddeptname;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * 修改人姓名
     */
    private String modifyusername;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 启用人用户编号
     */
    private String openuserid;

    /**
     * 启用人姓名
     */
    private String openusername;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人用户编号
     */
    private String closeuserid;

    /**
     * 禁用人姓名
     */
    private String closeusername;

    /**
     * 禁用时间
     */
    private Date closetime;

    /**
     * @return 编码
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            编码
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 开始日期
     */
    public String getBegindate() {
        return begindate;
    }

    /**
     * @param begindate 
	 *            开始日期
     */
    public void setBegindate(String begindate) {
        this.begindate = begindate == null ? null : begindate.trim();
    }

    /**
     * @return 结束日期
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate 
	 *            结束日期
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    /**
     * @return 会计年度
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year 
	 *            会计年度
     */
    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
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
     * @return 建档人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            建档人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 建档人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    /**
     * @return 建档部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            建档部门名称
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
     * @return 修改人用户编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人用户编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    /**
     * @return 修改人姓名
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            修改人姓名
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername == null ? null : modifyusername.trim();
    }

    /**
     * @return 修改时间
     */
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
     * @return 启用人用户编号
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人用户编号
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
    }

    /**
     * @return 启用人姓名
     */
    public String getOpenusername() {
        return openusername;
    }

    /**
     * @param openusername 
	 *            启用人姓名
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
     * @return 禁用人用户编号
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人用户编号
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
    }

    /**
     * @return 禁用人姓名
     */
    public String getCloseusername() {
        return closeusername;
    }

    /**
     * @param closeusername 
	 *            禁用人姓名
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

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

    public String getBelongmonth() {
        return belongmonth;
    }

    public void setBelongmonth(String belongmonth) {
        this.belongmonth = belongmonth;
    }
}