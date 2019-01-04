package com.hd.agent.message.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class MsgReceive implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2904808182279414056L;

	/**
     * 编号
     */
    private int id;

    /**
     * 消息编号
     */
    private int msgid;

    /**
     * 发送人编号
     */
    private String senduserid;
    /**
     * 发送人姓名
     */
    private String sendusername;

    /**
     * 发送时间
     */
    private Date sendtime;

    /**
     * 接收人用户编号
     */
    private String recvuserid;
    
    /**
     * 接收人姓名
     */
    private String recvusername;

    /**
     * 接收时间
     */
    private Date recvtime;

    /**
     * 查看状态1未读0已读
     */
    private String viewflag;

    /**
     * 查看时间
     */
    private Date viewtime;

    /**
     * 删除标志1未删除0已删除
     */
    private String delflag;
    
    /**
     * 删除时间
     */
    private Date deltime;
    /**
     * 接收状态1未接收0已接收
     */
    private String recvflag;
    
    /**
     * 消息内容
     */
    private MsgContent msgContent;
    
    /**
     * @return 编号
     */
    public int getId() {
        return id;
    }

    /**
     * @param id 
	 *            编号
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return 消息编号
     */
    public int getMsgid() {
        return msgid;
    }

    /**
     * @param msgid 
	 *            消息编号
     */
    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    /**
     * @return 发送人编号
     */
    public String getSenduserid() {
        return senduserid;
    }

    /**
     * @param senduserid 
	 *            发送人编号
     */
    public void setSenduserid(String senduserid) {
        this.senduserid = senduserid == null ? null : senduserid.trim();
    }

	public String getSendusername() {
		return sendusername;
	}

	public void setSendusername(String sendusername) {
		this.sendusername = sendusername;
	}

	public String getRecvusername() {
		return recvusername;
	}

	public void setRecvusername(String recvusername) {
		this.recvusername = recvusername;
	}

	/**
     * @return 发送时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * @param sendtime 
	 *            发送时间
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    /**
     * @return 接收人用户编号
     */
    public String getRecvuserid() {
        return recvuserid;
    }

    /**
     * @param recvuserid 
	 *            接收人用户编号
     */
    public void setRecvuserid(String recvuserid) {
        this.recvuserid = recvuserid == null ? null : recvuserid.trim();
    }

    /**
     * @return 接收时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getRecvtime() {
        return recvtime;
    }

    /**
     * @param recvtime 
	 *            接收时间
     */
    public void setRecvtime(Date recvtime) {
        this.recvtime = recvtime;
    }

    /**
     * @return 查看状态1未读0已读
     */
    public String getViewflag() {
        return viewflag;
    }

    /**
     * @param viewflag 
	 *            查看状态1未读0已读
     */
    public void setViewflag(String viewflag) {
        this.viewflag = viewflag == null ? null : viewflag.trim();
    }

    /**
     * @return 查看时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getViewtime() {
        return viewtime;
    }

    /**
     * @param viewtime 
	 *            查看时间
     */
    public void setViewtime(Date viewtime) {
        this.viewtime = viewtime;
    }

    /**
     * @return 删除标志1未删除0已删除
     */
    public String getDelflag() {
        return delflag;
    }

    /**
     * @param delflag 
	 *            删除标志1未删除0已删除
     */
    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    /**
     * @return 删除时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getDeltime() {
        return deltime;
    }

    /**
     * @param deltime 
	 *            删除时间
     */
    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }

    /**
     * @return 接收状态1未接收0已接收
     */
    public String getRecvflag() {
        return recvflag;
    }

    /**
     * @param remindflag 
	 *            接收状态1未接收0已接收
     */
    public void setRecvflag(String recvflag) {
        this.recvflag = recvflag == null ? null : recvflag.trim();
    }

    /**
     * 消息内容
     * @return
     * @author zhanghonghui 
     * @date 2013-1-19
     */
	public MsgContent getMsgContent() {
		return msgContent;
	}

	/**
	 * 消息内容
	 * @param msgContent
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}
}