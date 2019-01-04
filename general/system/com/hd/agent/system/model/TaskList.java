package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class TaskList implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 任务名
     */
    private String name;

    /**
     * 业务实现类
     */
    private String classpath;

    /**
     * 任务类型1业务2系统
     */
    private String type;

    /**
     * 状态0禁用1启用2保存3暂存4新增
     */
    private String state;

    /**
     * 添加时间
     */
    private Date addtime;

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
     * @return 任务名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            任务名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 业务实现类
     */
    public String getClasspath() {
        return classpath;
    }

    /**
     * @param classpath 
	 *            业务实现类
     */
    public void setClasspath(String classpath) {
        this.classpath = classpath == null ? null : classpath.trim();
    }

    /**
     * @return 任务类型1业务2系统
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            任务类型1业务2系统
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 状态0禁用1启用2保存3暂存4新增
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态0禁用1启用2保存3暂存4新增
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
}