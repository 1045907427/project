/**
 * @(#)EmailReceive.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-2 zhanghonghui 创建版本
 */
package com.hd.agent.message.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * 邮件接收人
 * 
 * @author zhanghonghui
 */
public class EmailReceive implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2198463458250044682L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 邮件编号
     */
    private Integer emailid;

    /**
     * 接收人编号
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
     * 部门名称
     */
    private String deptname;
    /**
     * 角色名称
     */
    private String rolename;

    /**
     * 阅读标志，1未阅读，0已阅读
     */
    private String viewflag;

    /**
     * 阅读时间
     */
    private Date viewtime;

    /**
     * 删除标志：1未删除，2进入废纸喽，0删除
     */
    private String delflag;

    /**
     * 删除时间
     */
    private Date deltime;

    /**
     * 收条标志：1未回收条，2已回收条，0默认
     */
    private String receiptflag;

    /**
     * 星号标志
     */
    private String signflag;

    /**
     * 邮箱编号：0为收件箱
     */
    private Integer boxid;
    /**
     * 接收者类型：0接收者，1抄送者，3密送者
     */
    private String recvtype;
    /**
     * 邮件内容
     */
    private EmailContent emailContent;
    /**
     * 发送者编号
     */
    private String senduserid;

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
     * @return 邮件编号
     */
    public Integer getEmailid() {
        return emailid;
    }

    /**
     * @param emailid 
	 *            邮件编号
     */
    public void setEmailid(Integer emailid) {
        this.emailid = emailid;
    }

    /**
     * @return 接收人编号
     */
    public String getRecvuserid() {
        return recvuserid;
    }

    /**
     * @param recvuserid 
	 *            接收人编号
     */
    public void setRecvuserid(String recvuserid) {
        this.recvuserid = recvuserid == null ? null : recvuserid.trim();
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

    public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
     * @return 阅读标志，1未阅读，0已阅读
     */
    public String getViewflag() {
        return viewflag;
    }

    /**
     * @param viewflag 
	 *            阅读标志，1未阅读，0已阅读
     */
    public void setViewflag(String viewflag) {
        this.viewflag = viewflag == null ? null : viewflag.trim();
    }

    /**
     * @return 阅读时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getViewtime() {
        return viewtime;
    }

    /**
     * @param viewtime 
	 *            阅读时间
     */
    public void setViewtime(Date viewtime) {
        this.viewtime = viewtime;
    }

    /**
     * @return 删除标志：1未删除，2进入废纸喽，0删除
     */
    public String getDelflag() {
        return delflag;
    }

    /**
     * @param delflag 
	 *            删除标志：1未删除，2进入废纸喽，0删除
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
     * @return 收条标志：1未回收条，2已回收条，0默认
     */
    public String getReceiptflag() {
        return receiptflag;
    }

    /**
     * @param receiptflag 
	 *            收条标志：1未回收条，2已回收条，0默认
     */
    public void setReceiptflag(String receiptflag) {
        this.receiptflag = receiptflag == null ? null : receiptflag.trim();
    }

    /**
     * @return 星号标志
     */
    public String getSignflag() {
        return signflag;
    }

    /**
     * @param signflag 
	 *            星号标志
     */
    public void setSignflag(String signflag) {
        this.signflag = signflag == null ? null : signflag.trim();
    }

    /**
     * @return 邮箱编号：0为收件箱
     */
    public Integer getBoxid() {
        return boxid;
    }

    /**
     * @param boxid 
	 *            邮箱编号：0为收件箱
     */
    public void setBoxid(Integer boxid) {
        this.boxid = boxid;
    }

    /**
     * @return 接收者类型：0接收者，1抄送者，3密送者
     */
    public String getRecvtype() {
        return recvtype;
    }

    /**
     * @param recvtype 
	 *            接收者类型：0接收者，1抄送者，3密送者
     */
    public void setRecvtype(String recvtype) {
        this.recvtype = recvtype == null ? null : recvtype.trim();
    }

	public EmailContent getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(EmailContent emailContent) {
		this.emailContent = emailContent;
	}

	public String getSenduserid() {
		return senduserid;
	}

	public void setSenduserid(String senduserid) {
		this.senduserid = senduserid;
	}
}

