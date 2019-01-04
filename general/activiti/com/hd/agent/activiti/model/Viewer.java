package com.hd.agent.activiti.model;

import java.io.Serializable;

public class Viewer implements Serializable {
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
     * 查看人编号集合
     */
    private String viewerid;

    /**
     * 查看人姓名集合
     */
    private String viewername;

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
     * @return 查看人编号集合
     */
    public String getViewerid() {
        return viewerid;
    }

    /**
     * @param viewerid 
	 *            查看人编号集合
     */
    public void setViewerid(String viewerid) {
        this.viewerid = viewerid;
    }

    /**
     * @return 查看人姓名集合
     */
    public String getViewername() {
        return viewername;
    }

    /**
     * @param viewername 
	 *            查看人姓名集合
     */
    public void setViewername(String viewername) {
        this.viewername = viewername;
    }
}