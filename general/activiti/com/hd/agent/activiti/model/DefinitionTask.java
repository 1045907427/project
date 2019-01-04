package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程节点人员分配及表单信息表
 * @author zhengziyong
 *
 */
public class DefinitionTask implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private String id;

    /**
     * 流程定义标识
     */
    private String definitionkey;

    /**
     * 节点标识
     */
    private String taskkey;

    /**
     * 节点名称
     */
    private String taskname;

    /**
     * 规则
     */
    private String rule;

    /**
     * 规则名称
     */
    private String rulename;

    /**
     * 人员
     */
    private String user;

    /**
     * 部门
     */
    private String dept;

    /**
     * 角色
     */
    private String role;

    /**
     * 岗位
     */
    private String post;

    /**
     * 规则说明
     */
    private String ruledetail;

    /**
     * 在线表单
     */
    private String formkey;

    /**
     * URL表单地址
     */
    private String businessurl;

    /**
     * 提醒方式
     */
    private String remindtype;

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
     * 能否设定人员
     */
    private String canassign;

    /**
     * 流程定义ID(版本号)
     */
    private String definitionid;

    /**
     * 该节点结束通知发起人
     */
    private String endremindapplier;

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
     * @return 流程定义标识
     */
    public String getDefinitionkey() {
        return definitionkey;
    }

    /**
     * @param definitionkey 
	 *            流程定义标识
     */
    public void setDefinitionkey(String definitionkey) {
        this.definitionkey = definitionkey;
    }

    /**
     * @return 节点标识
     */
    public String getTaskkey() {
        return taskkey;
    }

    /**
     * @param taskkey 
	 *            节点标识
     */
    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey;
    }

    /**
     * @return 节点名称
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * @param taskname 
	 *            节点名称
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    /**
     * @return 规则
     */
    public String getRule() {
        return rule;
    }

    /**
     * @param rule 
	 *            规则
     */
    public void setRule(String rule) {
        this.rule = rule;
    }

    /**
     * @return 规则名称
     */
    public String getRulename() {
        return rulename;
    }

    /**
     * @param rulename 
	 *            规则名称
     */
    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    /**
     * @return 人员
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user 
	 *            人员
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return 部门
     */
    public String getDept() {
        return dept;
    }

    /**
     * @param dept 
	 *            部门
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    /**
     * @return 角色
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role 
	 *            角色
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return 岗位
     */
    public String getPost() {
        return post;
    }

    /**
     * @param post 
	 *            岗位
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * @return 规则说明
     */
    public String getRuledetail() {
        return ruledetail;
    }

    /**
     * @param ruledetail 
	 *            规则说明
     */
    public void setRuledetail(String ruledetail) {
        this.ruledetail = ruledetail;
    }

    /**
     * @return 在线表单
     */
    public String getFormkey() {
        return formkey;
    }

    /**
     * @param formkey 
	 *            在线表单
     */
    public void setFormkey(String formkey) {
        this.formkey = formkey;
    }

    /**
     * @return URL表单地址
     */
    public String getBusinessurl() {
        return businessurl;
    }

    /**
     * @param businessurl 
	 *            URL表单地址
     */
    public void setBusinessurl(String businessurl) {
        this.businessurl = businessurl;
    }

    /**
     * @return 提醒方式
     */
    public String getRemindtype() {
        return remindtype;
    }

    /**
     * @param remindtype 
	 *            提醒方式
     */
    public void setRemindtype(String remindtype) {
        this.remindtype = remindtype;
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
        this.modifyuserid = modifyuserid;
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
        this.modifyusername = modifyusername;
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

    public String getEndremindapplier() {
        return endremindapplier;
    }

    public void setEndremindapplier(String endremindapplier) {
        this.endremindapplier = endremindapplier;
    }
}