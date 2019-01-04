/**
 * @(#)TaskDetail.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 16, 2013 chenwei 创建版本
 */
package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * 任务描述
 * @author chenwei
 */
public class TaskDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 任务编号
	 */
	private String taskid;
	/**
	 * 任务名称
	 */
	private String taskname;
	/**
	 * 任务组
	 */
	private String group;
	/**
	 * 任务要传递的数据集合
	 */
	private Map dataMap;
	/**
	 * 触发时间 采用quartz表达式
	 */
	private String cronTrigger;
	/**
	 * 任务接收执行类路径(com.agent.system.job.Myjob)
	 */
	private String classpath;
	/**
	 * 任务类型1单次执行2循环执行
	 */
	private String type;
	
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Map getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}
	public String getCronTrigger() {
		return cronTrigger;
	}
	public void setCronTrigger(String cronTrigger) {
		this.cronTrigger = cronTrigger;
	}
	public String getClasspath() {
		return classpath;
	}
	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}

