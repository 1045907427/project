package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程会签节点规则设置
 * @author zhengziyong
 *
 */
public class DefinitionSign implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 流程标识
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
     * 统计方式1达条件统计0全签收统计
     */
    private String counttype;

    /**
     * 决策方式1通过0拒绝
     */
    private String decisiontype;

    /**
     * 投票方式1绝对票数0百分比
     */
    private String votetype;

    /**
     * 票数/百分数
     */
    private Integer votenum;

    /**
     * 会签人编号集合,隔开
     */
    private String user;

    /**
     * 会签人姓名集合,隔开
     */
    private String name;

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
     * 流程定义ID(版本号)
     */
    private String definitionid;

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
        this.id = id;
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
     * @return 统计方式1达条件统计0全签收统计
     */
    public String getCounttype() {
        return counttype;
    }

    /**
     * @param counttype 
	 *            统计方式1达条件统计0全签收统计
     */
    public void setCounttype(String counttype) {
        this.counttype = counttype;
    }

    /**
     * @return 决策方式1通过0拒绝
     */
    public String getDecisiontype() {
        return decisiontype;
    }

    /**
     * @param decisiontype 
	 *            决策方式1通过0拒绝
     */
    public void setDecisiontype(String decisiontype) {
        this.decisiontype = decisiontype;
    }

    /**
     * @return 投票方式1绝对票数0百分比
     */
    public String getVotetype() {
        return votetype;
    }

    /**
     * @param votetype 
	 *            投票方式1绝对票数0百分比
     */
    public void setVotetype(String votetype) {
        this.votetype = votetype;
    }

    /**
     * @return 票数/百分数
     */
    public Integer getVotenum() {
        return votenum;
    }

    /**
     * @param votenum 
	 *            票数/百分数
     */
    public void setVotenum(Integer votenum) {
        this.votenum = votenum;
    }

    /**
     * @return 会签人编号集合,隔开
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user 
	 *            会签人编号集合,隔开
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return 会签人姓名集合,隔开
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            会签人姓名集合,隔开
     */
    public void setName(String name) {
        this.name = name;
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

    public String getDefinitionid() {
        return definitionid;
    }

    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid;
    }
}