/**
 * @(#)OaDelegate.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-21 limin 创建版本
 */
package com.hd.agent.oa.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作委托规则Model
 *
 * @author limin
 */
public class OaDelegate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 状态
     */
    private String status;

    /**
     * 业务日期
     */
    private String businessdate;

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
     * 打印次数
     */
    private Integer printtimes;

    /**
     * oa编号
     */
    private String oaid;

    /**
     * 流程标识
     */
    private String definitionkey;

    /**
     * 全部流程
     */
    private String all;

    /**
     * 委托人
     */
    private String userid;

    /**
     * 被委托人
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
     * 是否一直有效 1:一直有效
     */
    private String remain;

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
     * @return oa编号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * @param oaid
     *            oa编号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid == null ? null : oaid.trim();
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
     * @return 全部流程
     */
    public String getAll() {
        return all;
    }

    /**
     * @param all
     *            全部流程
     */
    public void setAll(String all) {
        this.all = all == null ? null : all.trim();
    }

    /**
     * @return 委托人
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     *            委托人
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 被委托人
     */
    public String getDelegateuserid() {
        return delegateuserid;
    }

    /**
     * @param delegateuserid
     *            被委托人
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
     * @return 是否一直有效 1:一直有效
     */
    public String getRemain() {
        return remain;
    }

    /**
     * @param remain
     *            是否一直有效 1:一直有效
     */
    public void setRemain(String remain) {
        this.remain = remain == null ? null : remain.trim();
    }
}