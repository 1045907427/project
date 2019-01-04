/**
 * @(#)FileDistribReceive.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-9-14 zhanghonghui 创建版本
 */
package com.hd.agent.message.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class FileDistribReceive implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8364837090473616748L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 传阅编号
     */
    private Integer fid;

    /**
     * 接收人编号
     */
    private String receiveuserid;
    /**
     * 接收人姓名
     */
    private String recvusername;

    /**
     * 接收时间
     */
    private Date receivetime;
    
    /**
     * 阅读时间
     */
    private Date readtime;
    /**
     * 阅读次数
     */
    private Integer readcount;

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
     * @return 传阅编号
     */
    public Integer getFid() {
        return fid;
    }

    /**
     * @param fid 
	 *            传阅编号
     */
    public void setFid(Integer fid) {
        this.fid = fid;
    }

    /**
     * @return 接收人编号
     */
    public String getReceiveuserid() {
        return receiveuserid;
    }

    /**
     * @param receiveuserid 
	 *            接收人编号
     */
    public void setReceiveuserid(String receiveuserid) {
        this.receiveuserid = receiveuserid == null ? null : receiveuserid.trim();
    }

	public String getRecvusername() {
		return recvusername;
	}

	public void setRecvusername(String recvusername) {
		this.recvusername = recvusername;
	}

    /**
     * @return 接收时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getReceivetime() {
        return receivetime;
    }

    /**
     * @param receivetime 
	 *            接收时间
     */
    public void setReceivetime(Date receivetime) {
        this.receivetime = receivetime;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getReadtime() {
		return readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	public Integer getReadcount() {
		return readcount;
	}

	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}
}
