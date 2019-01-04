package com.hd.agent.activiti.model;

import java.io.Serializable;

/**
 * 审批意见
 * @author zhengziyong
 *
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 流程实例编号
     */
    private String instanceid;

    /**
     * 任务编号
     */
    private String taskid;

    /**
     * 任务标识
     */
    private String taskkey;

    /**
     * 任务名称
     */
    private String taskname;

    /**
     * 处理人
     */
    private String assignee;
    
    /**
     * 处理人姓名
     */
    private String assigneename;

    /**
     * 1同意0不同意
     */
    private String agree;

    /**
     * 意见
     */
    private String comment;

    /**
     * 签名
     */
    private String signature;

    /**
     * 开始时间
     */
    private String begintime;

    /**
     * 结束时间
     */
    private String endtime;
    
    /**
     * 工作编号
     */
    private String processid;

    /**
     * 设备。1：网页；2：手机；
     */
    private String device;
    
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
        this.taskid = taskid;
    }

    /**
     * @return 任务标识
     */
    public String getTaskkey() {
        return taskkey;
    }

    /**
     * @param taskkey 
	 *            任务标识
     */
    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey;
    }

    /**
     * @return 任务名称
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * @param taskname 
	 *            任务名称
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    /**
     * @return 处理人
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * @param assignee 
	 *            处理人
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * 处理人姓名
     * @return
     * @author zhengziyong 
     * @date Oct 7, 2013
     */
    public String getAssigneename() {
		return assigneename;
	}

    /**
     * 处理人姓名
     * @param assigneename
     * @author zhengziyong 
     * @date Oct 7, 2013
     */
	public void setAssigneename(String assigneename) {
		this.assigneename = assigneename;
	}

	/**
     * @return 1同意0不同意
     */
    public String getAgree() {
        return agree;
    }

    /**
     * @param agree 
	 *            1同意0不同意
     */
    public void setAgree(String agree) {
        this.agree = agree;
    }

    /**
     * @return 意见
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment 
	 *            意见
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return 签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature 
	 *            签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return 开始时间
     */
    public String getBegintime() {
        return begintime;
    }

    /**
     * @param begintime 
	 *            开始时间
     */
    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    /**
     * @return 结束时间
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * @param endtime 
	 *            结束时间
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}