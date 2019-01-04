package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class AuthRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 操作类型 view：查看；add：申请；del：删除；aud：审批；……
     */
    private String type;

    /**
     * 流程定义key
     */
    private String definitionkey;

    /**
     * 节点taskkey
     */
    private String taskkey;

    /**
     * 人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    private String rule;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 角色编号
     */
    private String roleid;

    /**
     * 部门编号
     */
    private String deptid;

    /**
     * 岗位编号
     */
    private String postid;

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
     * 能否指定人员 1：能指定 0：不能指定
     */
    private String canassign;

    /**
     * 流程定义ID(版本号)
     */
    private String definitionid;

    /**
     * 角色名称
     */
    private String rolename;

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
     * @return 操作类型 view：查看；add：申请；del：删除；aud：审批；……
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            操作类型 view：查看；add：申请；del：删除；aud：审批；……
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * @return 人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    public String getRule() {
        return rule;
    }

    /**
     * @param rule 
	 *            人员配置规则 01：指定人员； 02：本部门指定角色； 03：本部门； 04：指定角色； 05：指定部门； 06：指定部门与角色； 07：制定岗位；……
     */
    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    /**
     * @return 用户编号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            用户编号
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 角色编号
     */
    public String getRoleid() {
        return roleid;
    }

    /**
     * @param roleid 
	 *            角色编号
     */
    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    /**
     * @return 部门编号
     */
    public String getDeptid() {
        return deptid;
    }

    /**
     * @param deptid 
	 *            部门编号
     */
    public void setDeptid(String deptid) {
        this.deptid = deptid == null ? null : deptid.trim();
    }

    /**
     * @return 岗位编号
     */
    public String getPostid() {
        return postid;
    }

    /**
     * @param postid 
	 *            岗位编号
     */
    public void setPostid(String postid) {
        this.postid = postid == null ? null : postid.trim();
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

	public String getCanassign() {
		return canassign;
	}

	public void setCanassign(String canassign) {
		this.canassign = canassign;
	}

    public String getDefinitionid() {
        return definitionid;
    }

    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}