package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class TaskLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 任务编号
     */
    private String taskid;

    /**
     * 任务名
     */
    private String taskname;

    /**
     * 任务组
     */
    private String team;

    /**
     * 执行时间
     */
    private Date executetime;

    /**
     * 执行状态1成功0失败
     */
    private String state;

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

    /**
     * @return 任务组
     */
    public String getTeam() {
        return team;
    }

    /**
     * @param team 
	 *            任务组
     */
    public void setTeam(String team) {
        this.team = team == null ? null : team.trim();
    }

    /**
     * @return 执行时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getExecutetime() {
        return executetime;
    }

    /**
     * @param executetime 
	 *            执行时间
     */
    public void setExecutetime(Date executetime) {
        this.executetime = executetime;
    }

    /**
     * @return 执行状态1成功0失败
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            执行状态1成功0失败
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}