package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 流程定义
 * @author zhengziyong
 *
 */
public class Definition implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 唯一标识
     */
    private String unkey;

    /**
     * 模板编号
     */
    private String modelid;

    /**
     * 是否布署(0否1是)
     */
    private String isdeploy;

    /**
     * 是否有修改未布署（0否1是）
     */
    private String ismodify;

    /**
     * 最新布署编号
     */
    private String deploymentid;

    /**
     * 当前使用定义编号
     */
    private String definitionid;

    /**
     * 当前使用定义版本
     */
    private String version;

    /**
     * 表单类型（formkey在线表单,business URL表单）
     */
    private String formtype;

    /**
     * 外置表单
     */
    private String formkey;

    /**
     * 业务表单地址
     */
    private String businessurl;
    
    /**
     * 流程为业务表单时流程结束时执行的监听
     */
    private String endlistener;
    
    /**
     * 通知方式
     */
    private String remindtype;

    /**
     * 状态(0禁用1启用)
     */
    private String state;

    /**
     * 流程分类
     */
    private String type;

    /**
     * 添加人编号
     */
    private String adduserid;

    /**
     * 添加人姓名
     */
    private String addusername;

    /**
     * 添加人部门编号
     */
    private String adddeptid;

    /**
     * 添加人部门名称
     */
    private String adddeptname;

    /**
     * 添加时间
     */
    private Date adddate;

    /**
     * 最后修改人
     */
    private String modifyuserid;

    /**
     * 最后修改人姓名
     */
    private String modifyusername;

    /**
     * 最后修改时间
     */
    private Date modifytime;

    /**
     * 定义内容
     */
    private byte[] bytes;
    
    private DefinitionType definitionType;
    
    /**
     * 结束通知
     */
    private String endremindtype;

    /**
     * @return 主键编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键编号
     */
    public void setId(String id) {
        this.id = id;
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
        this.name = name;
    }

    /**
     * @return 唯一标识
     */
    public String getUnkey() {
        return unkey;
    }

    /**
     * @param key 
	 *            唯一标识
     */
    public void setUnkey(String unkey) {
        this.unkey = unkey;
    }

    /**
     * @return 模板编号
     */
    public String getModelid() {
        return modelid;
    }

    /**
     * @param modelid 
	 *            模板编号
     */
    public void setModelid(String modelid) {
        this.modelid = modelid;
    }

    /**
     * @return 是否布署(0否1是)
     */
    public String getIsdeploy() {
        return isdeploy;
    }

    /**
     * @param isdeploy 
	 *            是否布署(0否1是)
     */
    public void setIsdeploy(String isdeploy) {
        this.isdeploy = isdeploy;
    }

    /**
     * @return 是否有修改未布署（0否1是）
     */
    public String getIsmodify() {
        return ismodify;
    }

    /**
     * @param ismodify 
	 *            是否有修改未布署（0否1是）
     */
    public void setIsmodify(String ismodify) {
        this.ismodify = ismodify;
    }

    /**
     * @return 最新布署编号
     */
    public String getDeploymentid() {
        return deploymentid;
    }

    /**
     * @param deploymentid 
	 *            最新布署编号
     */
    public void setDeploymentid(String deploymentid) {
        this.deploymentid = deploymentid;
    }

    /**
     * @return 当前使用定义编号
     */
    public String getDefinitionid() {
        return definitionid;
    }

    /**
     * @param definitionid 
	 *            当前使用定义编号
     */
    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid;
    }

    /**
     * @return 当前使用定义版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version 
	 *            当前使用定义版本
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return 表单类型（formkey在线表单,business URL表单）
     */
    public String getFormtype() {
        return formtype;
    }

    /**
     * @param formtype 
	 *            表单类型（formkey在线表单,business URL表单）
     */
    public void setFormtype(String formtype) {
        this.formtype = formtype;
    }

    /**
     * @return 外置表单
     */
    public String getFormkey() {
        return formkey;
    }

    /**
     * @param formkey 
	 *            外置表单
     */
    public void setFormkey(String formkey) {
        this.formkey = formkey;
    }

    /**
     * @return 业务表单地址
     */
    public String getBusinessurl() {
        return businessurl;
    }

    /**
     * @param businessurl 
	 *            业务表单地址
     */
    public void setBusinessurl(String businessurl) {
        this.businessurl = businessurl;
    }

    /**
     * 流程为业务表单时流程结束时执行的监听
     * @return
     * @author zhengziyong 
     * @date Oct 10, 2013
     */
    public String getEndlistener() {
		return endlistener;
	}

    /**
     * 流程为业务表单时流程结束时执行的监听
     * @param endlistener
     * @author zhengziyong 
     * @date Oct 10, 2013
     */
	public void setEndlistener(String endlistener) {
		this.endlistener = endlistener;
	}

	/**
     * 
     * @return 通知方式
     * @author zhengziyong 
     * @date Sep 27, 2013
     */
    public String getRemindtype() {
		return remindtype;
	}

    /**
     * 通知方式
     * @param remindtype 通知方式
     * @author zhengziyong 
     * @date Sep 27, 2013
     */
	public void setRemindtype(String remindtype) {
		this.remindtype = remindtype;
	}

	/**
     * @return 状态(0禁用1启用)
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态(0禁用1启用)
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return 流程分类
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            流程分类
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 添加人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid;
    }

    /**
     * @return 添加人姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            添加人姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername;
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
        this.adddeptid = adddeptid;
    }

    /**
     * @return 添加人部门名称
     */
    public String getAdddeptname() {
        return adddeptname;
    }

    /**
     * @param adddeptname 
	 *            添加人部门名称
     */
    public void setAdddeptname(String adddeptname) {
        this.adddeptname = adddeptname;
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm")
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            添加时间
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
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
        this.modifyuserid = modifyuserid;
    }

    /**
     * @return 最后修改人姓名
     */
    public String getModifyusername() {
        return modifyusername;
    }

    /**
     * @param modifyusername 
	 *            最后修改人姓名
     */
    public void setModifyusername(String modifyusername) {
        this.modifyusername = modifyusername;
    }

    /**
     * @return 最后修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
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
     * @return 定义内容
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param bytes 
	 *            定义内容
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

	public DefinitionType getDefinitionType() {
		return definitionType;
	}

	public void setDefinitionType(DefinitionType definitionType) {
		this.definitionType = definitionType;
	}

	public String getEndremindtype() {
		return endremindtype;
	}

	public void setEndremindtype(String endremindtype) {
		this.endremindtype = endremindtype;
	}
}