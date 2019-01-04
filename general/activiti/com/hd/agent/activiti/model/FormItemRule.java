package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class FormItemRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private Integer id;

    /**
     * form标识
     */
    private String unkey;

    /**
     * 项目编号
     */
    private String itemid;

    /**
     * 项目名称
     */
    private String itemname;

    /**
     * 流程定义key
     */
    private String definitionkey;

    /**
     * 节点taskkey
     */
    private String taskkey;

    /**
     * 是否可编辑(0： 不可编辑；1：可编辑)
     */
    private Integer writable;

    /**
     * 是否可见(0：不可见；1：可见)
     */
    private Integer visible;

    /**
     * 备注
     */
    private String remark;

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
     * 修改人编号
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
     * 是否必填(0：不必填；1：必填)
     */
    private String required;

    /**
     * 流程定义ID(版本号)
     */
    private String definitionid;

    /**
     * @return 主键编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return form标识
     */
    public String getUnkey() {
        return unkey;
    }

    /**
     * @param unkey 
	 *            form标识
     */
    public void setUnkey(String unkey) {
        this.unkey = unkey == null ? null : unkey.trim();
    }

    /**
     * @return 项目编号
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * @param itemid 
	 *            项目编号
     */
    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    /**
     * @return 项目名称
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname 
	 *            项目名称
     */
    public void setItemname(String itemname) {
        this.itemname = itemname == null ? null : itemname.trim();
    }

    /**
     * @return 流程定义key
     */
    public String getDefinitionkey() {
        return definitionkey;
    }

    /**
     * @param definitionkey 
	 *            流程定义key
     */
    public void setDefinitionkey(String definitionkey) {
        this.definitionkey = definitionkey == null ? null : definitionkey.trim();
    }

    /**
     * @return 节点taskkey
     */
    public String getTaskkey() {
        return taskkey;
    }

    /**
     * @param taskkey 
	 *            节点taskkey
     */
    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey == null ? null : taskkey.trim();
    }

    /**
     * @return 是否可编辑(0： 不可编辑；1：可编辑)
     */
    public Integer getWritable() {
        return writable;
    }

    /**
     * @param writable 
	 *            是否可编辑(0： 不可编辑；1：可编辑)
     */
    public void setWritable(Integer writable) {
        this.writable = writable;
    }

    /**
     * @return 是否可见(0：不可见；1：可见)
     */
    public Integer getVisible() {
        return visible;
    }

    /**
     * @param visible 
	 *            是否可见(0：不可见；1：可见)
     */
    public void setVisible(Integer visible) {
        this.visible = visible;
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
        this.adduserid = adduserid == null ? null : adduserid.trim();
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
        this.addusername = addusername == null ? null : addusername.trim();
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
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
    }

    /**
     * @return 添加时间
     */
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
     * @return 修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人编号
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

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getDefinitionid() {
        return definitionid;
    }

    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid;
    }
}