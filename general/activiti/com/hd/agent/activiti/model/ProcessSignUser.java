package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class ProcessSignUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * OA号
     */
    private Integer processid;

    /**
     * 流程实例号
     */
    private String instanceid;

    /**
     * 任务id
     */
    private String taskid;

    /**
     * 任务key
     */
    private String taskkey;

    /**
     * 会签用户编号
     */
    private String userid;

    /**
     * 新增时间
     */
    private Date addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return OA号
     */
    public Integer getProcessid() {
        return processid;
    }

    /**
     * @param processid 
	 *            OA号
     */
    public void setProcessid(Integer processid) {
        this.processid = processid;
    }

    /**
     * @return 流程实例号
     */
    public String getInstanceid() {
        return instanceid;
    }

    /**
     * @param instanceid 
	 *            流程实例号
     */
    public void setInstanceid(String instanceid) {
        this.instanceid = instanceid == null ? null : instanceid.trim();
    }

    /**
     * @return 任务id
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * @param taskid 
	 *            任务id
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid == null ? null : taskid.trim();
    }

    /**
     * @return 任务key
     */
    public String getTaskkey() {
        return taskkey;
    }

    /**
     * @param taskkey 
	 *            任务key
     */
    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey == null ? null : taskkey.trim();
    }

    /**
     * @return 会签用户编号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            会签用户编号
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 新增时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            新增时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}