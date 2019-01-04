/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-28 chenwei 创建版本
 */
package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 工作日历实体类
 * @author chenwei
 */
public class WorkCanlendar implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键(编码)
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态:4新增/3暂存/2保存/1启用/0禁用
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人部门编号
     */
    private String adddeptid;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 启用人编号
     */
    private String openuserid;

    /**
     * 启用时间
     */
    private Date opentime;

    /**
     * 禁用人编号
     */
    private String closeuserid;

    /**
     * 禁用时间
     */
    private Date closetime;

    /**
     * @return 主键(编码)
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键(编码)
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
     * @return 添加人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 添加人部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            添加人部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
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
     * @return 启用人编号
     */
    public String getOpenuserid() {
        return openuserid;
    }

    /**
     * @param openuserid 
	 *            启用人编号
     */
    public void setOpenuserid(String openuserid) {
        this.openuserid = openuserid == null ? null : openuserid.trim();
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
     * @return 禁用人编号
     */
    public String getCloseuserid() {
        return closeuserid;
    }

    /**
     * @param closeuserid 
	 *            禁用人编号
     */
    public void setCloseuserid(String closeuserid) {
        this.closeuserid = closeuserid == null ? null : closeuserid.trim();
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
}