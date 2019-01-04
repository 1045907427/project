package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class DelegateLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 工作编号
     */
    private String processid;
    
    /**
     * 流程标识
     */
    private String definitionkey;
    
    /**
     * 流程名称
     */
    private String definitionname;

    /**
     * 标题
     */
    private String title;

    /**
     * 任务编号
     */
    private String taskid;

    /**
     * 任务名称
     */
    private String taskname;

    /**
     * 委托人
     */
    private String userid;
    
    private String username;

    /**
     * 被委托人
     */
    private String delegateuserid;
    
    private String delegateusername;

    /**
     * 委托时间
     */
    private Date adddate;

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
     * @return 工作编号
     */
    public String getProcessid() {
        return processid;
    }

    /**
     * @param 工作编号
	 *            流程实例编号
     */
    public void setProcessid(String processid) {
        this.processid = processid;
    }

    /**
     * 
     * @return 流程标识
     * @author zhengziyong 
     * @date Oct 9, 2013
     */
    public String getDefinitionkey() {
		return definitionkey;
	}

    /**
     * 
     * @param definitionkey 流程标识
     * @author zhengziyong 
     * @date Oct 9, 2013
     */
	public void setDefinitionkey(String definitionkey) {
		this.definitionkey = definitionkey;
	}

	/**
	 * 
	 * @return 流程名称
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public String getDefinitionname() {
		return definitionname;
	}

	/**
	 * 
	 * @param definitionname 流程名称
	 * @author zhengziyong 
	 * @date Oct 9, 2013
	 */
	public void setDefinitionname(String definitionname) {
		this.definitionname = definitionname;
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
     * @return 委托人
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            委托人
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
     * @return 被委托人
     */
    public String getDelegateuserid() {
        return delegateuserid;
    }

    /**
     * @param delegateuserid 
	 *            被委托人
     */
    public void setDelegateuserid(String delegateuserid) {
        this.delegateuserid = delegateuserid;
    }

    public String getDelegateusername() {
		return delegateusername;
	}

	public void setDelegateusername(String delegateusername) {
		this.delegateusername = delegateusername;
	}

	/**
     * @return 委托时间
     */
	@JSON(format="yyyy-MM-dd HH:mm")
    public Date getAdddate() {
        return adddate;
    }

    /**
     * @param adddate 
	 *            委托时间
     */
    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }
}