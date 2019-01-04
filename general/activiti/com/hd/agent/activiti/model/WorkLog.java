package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 工作流程日志
 * @author zhengziyong
 *
 */
public class WorkLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 流程实例编号
     */
    private String instanceid;
    
    /**
     * t_act_process编号
     */
    private String processid;

    /**
     * 流程标识
     */
    private String definitionkey;

    /**
     * 标题
     */
    private String title;

    /**
     * 步骤号
     */
    private String stepnum;

    /**
     * 步骤名称
     */
    private String taskname;

    /**
     * 发起人
     */
    private String applyuserid;

    /**
     * 发起人姓名
     */
    private String applyusername;

    /**
     * 处理人
     */
    private String assigneeid;

    /**
     * 处理人姓名
     */
    private String assigneename;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 日志类型(1转交2代理3修改4结束5作废)
     */
    private String type;

    /**
     * 内容
     */
    private String content;

    /**
     * 日志时间
     */
    private Date adddate;

    /**
     * 节点key
     */
    private String taskkey;

    /**
     * 下一节点key
     */
    private String nexttaskkey;

    /**
     * 设备。1：网页；2：手机；
     */
    private String device;

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
     * @return 流程实例编号
     */
    public String getInstanceid() {
        return instanceid;
    }

    /**
     * @param instanceid 
	 *            流程实例编号
     */
    public void setInstanceid(String instanceid) {
        this.instanceid = instanceid;
    }

    /**
     * 
     * @return t_act_process编号
     * @author zhengziyong 
     * @date Oct 9, 2013
     */
    public String getProcessid() {
		return processid;
	}

    /**
     * 
     * @param processid t_act_process编号
     * @author zhengziyong 
     * @date Oct 9, 2013
     */
	public void setProcessid(String processid) {
		this.processid = processid;
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
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 步骤号
     */
    public String getStepnum() {
        return stepnum;
    }

    /**
     * @param stepnum 
	 *            步骤号
     */
    public void setStepnum(String stepnum) {
        this.stepnum = stepnum;
    }

    /**
     * @return 步骤名称
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * @param taskname 
	 *            步骤名称
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    /**
     * @return 发起人
     */
    public String getApplyuserid() {
        return applyuserid;
    }

    /**
     * @param applyuserid 
	 *            发起人
     */
    public void setApplyuserid(String applyuserid) {
        this.applyuserid = applyuserid;
    }

    /**
     * @return 发起人姓名
     */
    public String getApplyusername() {
        return applyusername;
    }

    /**
     * @param applyusername 
	 *            发起人姓名
     */
    public void setApplyusername(String applyusername) {
        this.applyusername = applyusername;
    }

    /**
     * @return 处理人
     */
    public String getAssigneeid() {
        return assigneeid;
    }

    /**
     * @param assigneeid 
	 *            处理人
     */
    public void setAssigneeid(String assigneeid) {
        this.assigneeid = assigneeid;
    }

    /**
     * @return 处理人姓名
     */
    public String getAssigneename() {
        return assigneename;
    }

    /**
     * @param assigneename 
	 *            处理人姓名
     */
    public void setAssigneename(String assigneename) {
        this.assigneename = assigneename;
    }

    /**
     * @return ip地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip 
	 *            ip地址
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return 日志类型(1转交2代理3修改4结束5作废)
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            日志类型(1转交2代理3修改4结束5作废)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 日志时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            日志时间
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }

    public String getTaskkey() {
        return taskkey;
    }

    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey;
    }

    public String getNexttaskkey() {
        return nexttaskkey;
    }

    public void setNexttaskkey(String nexttaskkey) {
        this.nexttaskkey = nexttaskkey;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}