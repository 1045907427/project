package com.hd.agent.basefiles.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 计量单位
 * @author chenwei
 */
public class MeteringUnit implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 原来的编码 
     */
    private String oldId;
    
    /**
     * 编码
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 4新增/3暂存/2保存/1启用/0禁用
     */
    private String state;
    
    /**
     * 状态名称
     */
    private String stateName;
   
	/**
     * 备注
     */
    private String remark;

    /**
     * 建档人编号
     */
    private String adduserid;

    /**
     * 建档部门编号
     */
    private String adddeptid;

    /**
     * 建档时间
     */
    private Date addtime;

    /**
     * 最后修改人编号
     */
    private String modifyuserid;

    /**
     * 最后修改时间
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

    /**
     * @return 4新增/3暂存/2保存/1启用/0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            4新增/3暂存/2保存/1启用/0禁用
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
     * @return 建档人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            建档人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 建档部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            建档部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
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

    /**
     * @return 原来的编码
     * @author panxiaoxiao 
     * @date 2013-4-11
     */
	public String getOldId() {
		return oldId;
	}

	/**
	 * @param oldId 原来的编码
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	 /**
     * @return 状态名称
     * @author panxiaoxiao 
     * @date 2013-4-11
     */
    public String getStateName() {
		return stateName;
	}

    /**
     * @param stateName 状态名称
     * @author panxiaoxiao 
     * @date 2013-4-11
     */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}