package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 任务调度安排
 * @author chenwei
 */
public class TaskSchedule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务编号
     */
    private String taskid;

    /**
     * 任务清单编号
     */
    private String tasklistid;

    /**
     * 任务名
     */
    private String taskname;

    /**
     * 任务组
     */
    private String team;

    /**
     * 业务执行路径（com.agent.demo.Task）
     */
    private String classpath;

    /**
     * 定时类型1只执行一次2循环执行
     */
    private String type;

    /**
     * 触发时间（使用quartz的cronExpression表达式）
     */
    private String triggertime;

    /**
     * 状态1启动2暂停3停止0执行完毕
     */
    private String state;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 执行次数
     */
    private Integer times;
    /**
     * 根级任务编号
     */
    private String roottaskid;
    /**
     * 是否预警
     */
    private String isalert;

    /**
     * 预警地址
     */
    private String alerturl;

    /**
     * 指定用户
     */
    private String senduserid;

    /**
     *指定角色
     */
    private String sendroleid;

    /**
     * @return 任务编号
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * @param taskid 
	 *            任务编号
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * @return 任务清单编号
     */
    public String getTasklistid() {
        return tasklistid;
    }

    /**
     * @param tasklistid 
	 *            任务清单编号
     */
    public void setTasklistid(String tasklistid) {
        this.tasklistid = tasklistid == null ? null : tasklistid.trim();
    }

    /**
     * @return 任务名
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * @param taskname 
	 *            任务名
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname == null ? null : taskname.trim();
    }

    

    public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	/**
     * @return 业务执行路径（com.agent.demo.Task）
     */
    public String getClasspath() {
        return classpath;
    }

    /**
     * @param classpath 
	 *            业务执行路径（com.agent.demo.Task）
     */
    public void setClasspath(String classpath) {
        this.classpath = classpath == null ? null : classpath.trim();
    }

    /**
     * @return 定时类型1只执行一次2循环执行
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            定时类型1只执行一次2循环执行
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 触发时间（使用quartz的cronExpression表达式）
     */
    public String getTriggertime() {
        return triggertime;
    }

    /**
     * @param triggertime 
	 *            触发时间（使用quartz的cronExpression表达式）
     */
    public void setTriggertime(String triggertime) {
        this.triggertime = triggertime == null ? null : triggertime.trim();
    }

    /**
     * @return 状态1启动2暂停3停止0执行完毕
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1启动2暂停3停止0执行完毕
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
     * @return 执行次数
     */
    public Integer getTimes() {
        return times;
    }

    /**
     * @param times 
	 *            执行次数
     */
    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getRoottaskid() {
        return roottaskid;
    }

    public void setRoottaskid(String roottaskid) {
        this.roottaskid = roottaskid == null ? null : roottaskid.trim();
    }

    public String getIsalert() {
        return isalert;
    }

    public void setIsalert(String isalert) {
        this.isalert = isalert;
    }

    public String getAlerturl() {
        return alerturl;
    }

    public void setAlerturl(String alerturl) {
        this.alerturl = alerturl;
    }

    public String getSenduserid() {
        return senduserid;
    }

    public void setSenduserid(String senduserid) {
        this.senduserid = senduserid;
    }

    public String getSendroleid() {
        return sendroleid;
    }

    public void setSendroleid(String sendroleid) {
        this.sendroleid = sendroleid;
    }
}