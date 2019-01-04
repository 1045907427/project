package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class Delegate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 状态
     */
    private String status;

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
     * 备注
     */
    private String remark;

    /**
     * 流程标识
     */
    private String definitionkey;

    /**
     * 委托人员编号
     */
    private String userid;

    /**
     * 被委托人员编号
     */
    private String delegateuserid;

    /**
     * 开始时间
     */
    private String begindate;

    /**
     * 结束时间
     */
    private String enddate;

    /**
     * 1一直有效
     */
    private String remain;

    /**
     * OA编号
     */
    private String oaid;

    /**
     * 委托人
     */
    private String username;

    /**
     * 被委托人
     */
    private String delegateusername;

    /**
     * 流程名称
     */
    private String definitionname;
    /**
     * @return 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            编号
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return 流程标识
     */
    public String getDefinitionkey() {
        return definitionkey;
    }

    /**
     * @param definitionkey
     *            流程标识
     */
    public void setDefinitionkey(String definitionkey) {
        this.definitionkey = definitionkey == null ? null : definitionkey.trim();
    }

    /**
     * @return 委托人员编号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     *            委托人员编号
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 被委托人员编号
     */
    public String getDelegateuserid() {
        return delegateuserid;
    }

    /**
     * @param delegateuserid
     *            被委托人员编号
     */
    public void setDelegateuserid(String delegateuserid) {
        this.delegateuserid = delegateuserid == null ? null : delegateuserid.trim();
    }

    /**
     * @return 开始时间
     */
    public String getBegindate() {
        return begindate;
    }

    /**
     * @param begindate
     *            开始时间
     */
    public void setBegindate(String begindate) {
        this.begindate = begindate == null ? null : begindate.trim();
    }

    /**
     * @return 结束时间
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate
     *            结束时间
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    /**
     * @return 1一直有效
     */
    public String getRemain() {
        return remain;
    }

    /**
     * @param remain
     *            1一直有效
     */
    public void setRemain(String remain) {
        this.remain = remain == null ? null : remain.trim();
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDelegateusername() {
        return delegateusername;
    }

    public void setDelegateusername(String delegateusername) {
        this.delegateusername = delegateusername;
    }

    public String getDefinitionname() {
        return definitionname;
    }

    public void setDefinitionname(String definitionname) {
        this.definitionname = definitionname;
    }
}