package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

public class EventHandler implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 流程定义key
     */
    private String definitionkey;

    /**
     * 节点taskkey
     */
    private String taskkey;

    /**
     * 事件类型 create：转交至该节点时执行；complete：该节点结束时执行；assignment：保留，暂时没用；
     */
    private String event;

    /**
     * spring bean，需要notify()方法
     */
    private String handler;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * handler描述
     */
    private String description;

    /**
     * 流程定义ID(版本号)
     */
    private String definitionid;

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
     * @return 流程定义key
     */
    public String getDefinitionkey() {
        return definitionkey;
    }

    /**
     * @param definitionkey 
	 *            流程定义key
     */
    public void setDefinitionkey(String definitionkey) {
        this.definitionkey = definitionkey == null ? null : definitionkey.trim();
    }

    /**
     * @return 节点taskkey
     */
    public String getTaskkey() {
        return taskkey;
    }

    /**
     * @param taskkey 
	 *            节点taskkey
     */
    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey == null ? null : taskkey.trim();
    }

    /**
     * @return 事件类型 create：转交至该节点时执行；complete：该节点结束时执行；assignment：保留，暂时没用；
     */
    public String getEvent() {
        return event;
    }

    /**
     * @param event 
	 *            事件类型 create：转交至该节点时执行；complete：该节点结束时执行；assignment：保留，暂时没用；
     */
    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    /**
     * @return spring bean，需要notify()方法
     */
    public String getHandler() {
        return handler;
    }

    /**
     * @param handler 
	 *            spring bean，需要notify()方法
     */
    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }

    /**
     * @return 添加人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加时间
     */
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
     * @return 修改人用户编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人用户编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getDefinitionid() {
        return definitionid;
    }

    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid;
    }
}