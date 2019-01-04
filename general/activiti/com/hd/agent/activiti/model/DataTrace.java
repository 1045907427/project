package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class DataTrace implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    private Integer id;

    /**
     * 工作编号(OA编号)
     */
    private Integer processid;

    /**
     * 流程版本
     */
    private String definitionid;

    /**
     * 任务编号
     */
    private String taskid;

    /**
     * 节点标识
     */
    private String taskkey;

    /**
     * 节点名称
     */
    private String taskname;

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
     * 数据(JSON格式)
     */
    private byte[] trace;

    /**
     * @return 主键编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 工作编号(OA编号)
     */
    public Integer getProcessid() {
        return processid;
    }

    /**
     * @param processid 
	 *            工作编号(OA编号)
     */
    public void setProcessid(Integer processid) {
        this.processid = processid;
    }

    /**
     * @return 流程版本
     */
    public String getDefinitionid() {
        return definitionid;
    }

    /**
     * @param definitionid 
	 *            流程版本
     */
    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid == null ? null : definitionid.trim();
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
        this.taskid = taskid == null ? null : taskid.trim();
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
        this.taskkey = taskkey == null ? null : taskkey.trim();
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
        this.taskname = taskname == null ? null : taskname.trim();
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
        this.adduserid = adduserid == null ? null : adduserid.trim();
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
        this.addusername = addusername == null ? null : addusername.trim();
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
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
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
        this.adddeptname = adddeptname == null ? null : adddeptname.trim();
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
     * @return 数据(JSON格式)
     */
    public byte[] getTrace() {
        return trace;
    }

    /**
     * @param trace 
	 *            数据(JSON格式)
     */
    public void setTrace(byte[] trace) {
        this.trace = trace;
    }
}