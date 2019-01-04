package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class AuthMapping implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 流程定义key
     */
    private String definitionkey;

    /**
     * 节点taskkey
     */
    private String taskkey;

    /**
     * 映射类型 1：按发起人； 2：按当前节点审核人；……
     */
    private String mappingtype;

    /**
     * 人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    private String fromrule;

    /**
     * 用户编号
     */
    private String fromuserid;

    /**
     * 角色编号
     */
    private String fromroleid;

    /**
     * 部门编号
     */
    private String fromdeptid;

    /**
     * 岗位编号
     */
    private String frompostid;

    /**
     * 人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    private String torule;

    /**
     * 用户编号
     */
    private String touserid;

    /**
     * 角色编号
     */
    private String toroleid;

    /**
     * 部门编号
     */
    private String todeptid;

    /**
     * 岗位编号
     */
    private String topostid;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     *
     */
    private Integer category;

    /**
     * 流程定义ID(版本号)
     */
    private String definitionid;

    /**
     * 角色名称
     */
    private String fromrolename;

    /**
     * 角色名称
     */
    private String torolename;

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
     * @return 映射类型 1：按发起人； 2：按当前节点审核人；……
     */
    public String getMappingtype() {
        return mappingtype;
    }

    /**
     * @param mappingtype 
	 *            映射类型 1：按发起人； 2：按当前节点审核人；……
     */
    public void setMappingtype(String mappingtype) {
        this.mappingtype = mappingtype == null ? null : mappingtype.trim();
    }

    /**
     * @return 人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    public String getFromrule() {
        return fromrule;
    }

    /**
     * @param fromrule 
	 *            人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    public void setFromrule(String fromrule) {
        this.fromrule = fromrule == null ? null : fromrule.trim();
    }

    /**
     * @return 用户编号
     */
    public String getFromuserid() {
        return fromuserid;
    }

    /**
     * @param fromuserid 
	 *            用户编号
     */
    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid == null ? null : fromuserid.trim();
    }

    /**
     * @return 角色编号
     */
    public String getFromroleid() {
        return fromroleid;
    }

    /**
     * @param fromroleid 
	 *            角色编号
     */
    public void setFromroleid(String fromroleid) {
        this.fromroleid = fromroleid == null ? null : fromroleid.trim();
    }

    /**
     * @return 部门编号
     */
    public String getFromdeptid() {
        return fromdeptid;
    }

    /**
     * @param fromdeptid 
	 *            部门编号
     */
    public void setFromdeptid(String fromdeptid) {
        this.fromdeptid = fromdeptid == null ? null : fromdeptid.trim();
    }

    /**
     * @return 岗位编号
     */
    public String getFrompostid() {
        return frompostid;
    }

    /**
     * @param frompostid 
	 *            岗位编号
     */
    public void setFrompostid(String frompostid) {
        this.frompostid = frompostid == null ? null : frompostid.trim();
    }

    /**
     * @return 人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    public String getTorule() {
        return torule;
    }

    /**
     * @param torule 
	 *            人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    public void setTorule(String torule) {
        this.torule = torule == null ? null : torule.trim();
    }

    /**
     * @return 用户编号
     */
    public String getTouserid() {
        return touserid;
    }

    /**
     * @param touserid 
	 *            用户编号
     */
    public void setTouserid(String touserid) {
        this.touserid = touserid == null ? null : touserid.trim();
    }

    /**
     * @return 角色编号
     */
    public String getToroleid() {
        return toroleid;
    }

    /**
     * @param toroleid 
	 *            角色编号
     */
    public void setToroleid(String toroleid) {
        this.toroleid = toroleid == null ? null : toroleid.trim();
    }

    /**
     * @return 部门编号
     */
    public String getTodeptid() {
        return todeptid;
    }

    /**
     * @param todeptid 
	 *            部门编号
     */
    public void setTodeptid(String todeptid) {
        this.todeptid = todeptid == null ? null : todeptid.trim();
    }

    /**
     * @return 岗位编号
     */
    public String getTopostid() {
        return topostid;
    }

    /**
     * @param topostid 
	 *            岗位编号
     */
    public void setTopostid(String topostid) {
        this.topostid = topostid == null ? null : topostid.trim();
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDefinitionid() {
        return definitionid;
    }

    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid;
    }

    public String getFromrolename() {
        return fromrolename;
    }

    public void setFromrolename(String fromrolename) {
        this.fromrolename = fromrolename;
    }

    public String getTorolename() {
        return torolename;
    }

    public void setTorolename(String torolename) {
        this.torolename = torolename;
    }
}