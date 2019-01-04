package com.hd.agent.activiti.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 流程实例信息
 * @author zhengziyong
 *
 */
public class Process implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 流程实例编号
     */
    private String instanceid;

    /**
     * 启动流程用户编号
     */
    private String applyuserid;

    /**
     * 用户姓名
     */
    private String applyusername;

    /**
     * 当前任务编号
     */
    private String taskid;

    /**
     * 当前任务标识
     */
    private String taskkey;

    /**
     * 当前任务名称
     */
    private String taskname;

    /**
     * 任务当前处理人
     */
    private String assignee;

    /**
     * 候选人
     */
    private String condidate;

    /**
     * 会签人员
     */
    private String signuser;
    
    /**
     * 流程描述名称
     */
    private String definitionname;

    /**
     * 流程描述标识
     */
    private String definitionkey;

    /**
     * 业务编号（URL表单）
     */
    private String businessid;

    /**
     * 添加时间
     */
    private Date adddate;

    /**
     * 最新处理时间
     */
    private Date updatedate;

    /**
     * 提交时间
     */
    private Date applydate;

    /**
     * 是否结束（0未1是）
     */
    private String isend;
    
    /**
     * 整个表单Html代码
     */
    private byte[] html;

    /**
     * 表单值json
     */
    private byte[] json;
    
    /**
     * 是否可收回</br>
     * 	0:不可收回
     *  1:可收回
     */
    private String cantakeback;
    
    /**
     * 状态： 1 执行中； 2挂起； 3删除； 9 结束； 0 作废；
     */
    private String status;
    
    /**
     * 
     */
    private String operation;
    
    /**
     * 转交时间
     */
    private String handtime;
    
    /**
     * 接收时间
     */
    private String receivetime;

    /**
     * 能否“彻底删除” 0：否；1：能
     */
    private String candelete;

    private String keyword1;

    private String keyword2;

    private String keyword3;

    private String keyword4;

    private String keyword5;

    /**
     * 手机表单html代码
     */
    private byte[] phonehtml;

    /**
     * 流程ID(版本号)
     */
    private String definitionid;

    /**
     * 上一步是否同意
     */
    private String preagree;

    /**
     * 是否会签
     */
    private boolean sign = false;

    /**
     * 当前处理人
     */
    private String assigneename;

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
     * @return 启动流程用户编号
     */
    public String getApplyuserid() {
        return applyuserid;
    }

    /**
     * @param applyuserid 
	 *            启动流程用户编号
     */
    public void setApplyuserid(String applyuserid) {
        this.applyuserid = applyuserid;
    }

    /**
     * @return 用户姓名
     */
    public String getApplyusername() {
        return applyusername;
    }

    /**
     * @param applyusername 
	 *            用户姓名
     */
    public void setApplyusername(String applyusername) {
        this.applyusername = applyusername;
    }

    /**
     * @return 当前任务编号
     */
    public String getTaskid() {
        return taskid;
    }

    /**
     * @param taskid 
	 *            当前任务编号
     */
    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    /**
     * @return 当前任务标识
     */
    public String getTaskkey() {
        return taskkey;
    }

    /**
     * @param taskkey 
	 *            当前任务标识
     */
    public void setTaskkey(String taskkey) {
        this.taskkey = taskkey;
    }

    /**
     * @return 当前任务名称
     */
    public String getTaskname() {
        return taskname;
    }

    /**
     * @param taskname 
	 *            当前任务名称
     */
    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    /**
     * @return 任务当前处理人
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * @param assignee 
	 *            任务当前处理人
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * @return 候选人
     */
    public String getCondidate() {
        return condidate;
    }

    /**
     * @param condidate 
	 *            候选人
     */
    public void setCondidate(String condidate) {
        this.condidate = condidate;
    }

    /**
     * 会签人员
     * @return
     * @author zhengziyong 
     * @date Oct 7, 2013
     */
    public String getSignuser() {
		return signuser;
	}

    /**
     * 会签人员
     * @param signuser
     * @author zhengziyong 
     * @date Oct 7, 2013
     */
	public void setSignuser(String signuser) {
		this.signuser = signuser;
	}

	/**
     * @return 流程描述名称
     */
    public String getDefinitionname() {
        return definitionname;
    }

    /**
     * @param definitionname 
	 *            流程描述名称
     */
    public void setDefinitionname(String definitionname) {
        this.definitionname = definitionname;
    }

    /**
     * @return 流程描述标识
     */
    public String getDefinitionkey() {
        return definitionkey;
    }

    /**
     * @param definitionkey 
	 *            流程描述标识
     */
    public void setDefinitionkey(String definitionkey) {
        this.definitionkey = definitionkey;
    }

    /**
     * @return 业务编号（URL表单）
     */
    public String getBusinessid() {
        return businessid;
    }

    /**
     * @param businessid 
	 *            业务编号（URL表单）
     */
    public void setBusinessid(String businessid) {
        this.businessid = businessid;
    }

    /**
     * @return 添加时间
     */
    @JSON(format="yyyy-MM-dd HH:mm")
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
     * @return 最新处理时间
     */
    @JSON(format="yyyy-MM-dd HH:mm")
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * @param updatedate 
	 *            最新处理时间
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * @return 提交时间
     */
    @JSON(format="yyyy-MM-dd HH:mm")
    public Date getApplydate() {
        return applydate;
    }

    /**
     * @param applydate 
	 *            提交时间
     */
    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    /**
     * @return 是否结束（0未1是）
     */
    public String getIsend() {
        return isend;
    }

    /**
     * @param isend 
	 *            是否结束（0未1是）
     */
    public void setIsend(String isend) {
        this.isend = isend;
    }
    
    /**
     * @return 整个表单Html代码
     */
    public byte[] getHtml() {
        return html;
    }

    /**
     * @param html 
	 *            整个表单Html代码
     */
    public void setHtml(byte[] html) {
        this.html = html;
    }

    /**
     * @return 表单值json
     */
    public byte[] getJson() {
        return json;
    }

    /**
     * @param json 
	 *            表单值json
     */
    public void setJson(byte[] json) {
        this.json = json;
    }

	public String getCantakeback() {
		return cantakeback;
	}

	public void setCantakeback(String cantakeback) {
		this.cantakeback = cantakeback;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getHandtime() {
		return handtime;
	}

	public void setHandtime(String handtime) {
		this.handtime = handtime;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getCandelete() {
		return candelete;
	}

	public void setCandelete(String candelete) {
		this.candelete = candelete;
	}

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(String keyword4) {
        this.keyword4 = keyword4;
    }

    public String getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(String keyword5) {
        this.keyword5 = keyword5;
    }

    public String getDefinitionid() {
        return definitionid;
    }

    public void setDefinitionid(String definitionid) {
        this.definitionid = definitionid;
    }

    public byte[] getPhonehtml() {
        return phonehtml;
    }

    public void setPhonehtml(byte[] phonehtml) {
        this.phonehtml = phonehtml;
    }

    public String getPreagree() {
        return preagree;
    }

    public void setPreagree(String preagree) {
        this.preagree = preagree;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public String getAssigneename() {
        return assigneename;
    }

    public void setAssigneename(String assigneename) {
        this.assigneename = assigneename;
    }
}