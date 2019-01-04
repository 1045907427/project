/**
 * @(#)MsgNoticeread.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-25 zhanghonghui 创建版本
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
public class MsgNoticeread  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 543499274884437009L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 通告编号
     */
    private Integer noticeid;

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
     * 接收人部门
     */
    private String recvuserdeptname;

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
     * @return 通告编号
     */
    public Integer getNoticeid() {
        return noticeid;
    }

    /**
     * @param noticeid 
	 *            通告编号
     */
    public void setNoticeid(Integer noticeid) {
        this.noticeid = noticeid;
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

	public String getRecvusername() {
		return recvusername;
	}

	public void setRecvusername(String recvusername) {
        this.recvusername = recvusername == null ? null : recvusername.trim();
	}

    public String getRecvuserdeptname() {
        return recvuserdeptname;
    }

    public void setRecvuserdeptname(String recvuserdeptname) {
        this.recvuserdeptname = recvuserdeptname == null ? null : recvuserdeptname.trim();
    }
}

