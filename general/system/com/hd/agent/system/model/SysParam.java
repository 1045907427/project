package com.hd.agent.system.model;


import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 系统参数实体类
 * @author pan_xx
 *
 */
public class SysParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 参数编号
     */
    private String paramid;

    /**
     * 参数名称(唯一)
     */
    private String pname;

    /**
     * 参数描述
     */
    private String description;

    /**
     * 参数值
     */
    private String pvalue;

    /**
     * 参数值描述
     */
    private String pvdescription;

    /**
     * 参数用途
     */
    private String puser;

    /**
     * 状态：1启用0停用
     */
    private String state;

    /**
     * 所属模块id
     */
    private String moduleid;
    
    /**
     * 所属模块名称
     */
    private String modulename;

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
        this.modulename = modulename == null ? null : modulename.trim();
	}

	/**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * @return 参数编号
     */
    public String getParamid() {
        return paramid;
    }

    /**
     * @param paramid 
	 *            参数编号
     */
    public void setParamid(String paramid) {
        this.paramid = paramid == null ? null : paramid.trim();
    }

    /**
     * @return 参数名称(唯一)
     */
    public String getPname() {
        return pname;
    }

    /**
     * @param pname 
	 *            参数名称(唯一)
     */
    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    /**
     * @return 参数描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            参数描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return 参数值
     */
    public String getPvalue() {
        return pvalue;
    }

    /**
     * @param pvalue 
	 *            参数值
     */
    public void setPvalue(String pvalue) {
        this.pvalue = pvalue == null ? null : pvalue.trim();
    }

    /**
     * @return 参数值描述
     */
    public String getPvdescription() {
        return pvdescription;
    }

    /**
     * @param pvdescription 
	 *            参数值描述
     */
    public void setPvdescription(String pvdescription) {
        this.pvdescription = pvdescription == null ? null : pvdescription.trim();
    }

    /**
     * @return 参数用途
     */
    public String getPuser() {
        return puser;
    }

    /**
     * @param puser 
	 *            参数用途
     */
    public void setPuser(String puser) {
        this.puser = puser == null ? null : puser.trim();
    }

    /**
     * @return 状态：1启用0停用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态：1启用0停用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 所属模块id
     */
    public String getModuleid() {
        return moduleid;
    }

    /**
     * @param moduleid 
	 *            所属模块id
     */
    public void setModuleid(String moduleid) {
        this.moduleid = moduleid == null ? null : moduleid.trim();
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
}