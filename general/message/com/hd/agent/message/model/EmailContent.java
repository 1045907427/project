/**
 * @(#)EmailContent.java
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
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

/**
 * 邮件内容
 * 
 * @author zhanghonghui
 */
public class EmailContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2215230500095422258L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 发送者
     */
    private String adduserid;
    
    /**
     * 发送者姓名
     */
    private String addusername;

    /**
     * 发送时间
     */
    private Date addtime;

    /**
     * 标题
     */
    private String title;

    /**
     * 附件
     */
    private String attach;

    /**
     * 内部短信提醒,1提醒，0不提醒
     */
    private String ismsg;

    /**
     * 收条，1回收条，0不回收条
     */
    private String isreceipt;

    /**
     * 重要标志:0一般邮件，1重要邮件，2非常重要
     */
    private String importantflag;

    /**
     * 发送标志:1发送成功，0未发送
     */
    private String sendflag;

    /**
     * 发送的外部邮件编号
     */
    private Integer fromwebmailid;

    /**
     * 发送的外部邮件地址
     */
    private String fromwebmail;

    /**
     * 外部邮件发送标志：1等待发送，2发送成功，3发送失败，0默认状态
     */
    private String webmailflag;

    /**
     * 邮件类型：0内部邮件，1内部外部邮件，2外部邮件
     */
    private String mailtype;

    /**
     * 关键字
     */
    private String keyword;
    
    /**
     * 接收人
     */
    private String receiveuser;
    /**
     * 接收人姓名
     */
    private String recvusername;

    /**
     * 抄送列表
     */
    private String copytouser;
    /**
     * 抄送列表
     */
    private String copytousername;

    /**
     * 密送列表
     */
    private String secrettouser;
    /**
     * 密送列表
     */
    private String secrettousername;

    /**
     * 内容
     */
    private String content;

    /**
     * 接收的外部邮件地址列表
     */
    private String towebmail;

    /**
     * 删除标志：1未删除，2进入废纸喽，0删除
     */
    private String delflag;

    /**
     * 删除时间
     */
    private Date deltime;
    /**
     * 接收人列表
     */
    private List<EmailReceive> emailReceiveList;
    /**
     * 接收的人数
     */
    private Integer receivernum;

    /**
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 发送者
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            发送者
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	/**
     * @return 发送时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            发送时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
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
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return 附件
     */
    public String getAttach() {
        return attach;
    }

    /**
     * @param attach 
	 *            附件
     */
    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
    }

    /**
     * @return 内部短信提醒,1提醒，0不提醒
     */
    public String getIsmsg() {
        return ismsg;
    }

    /**
     * @param ismsg 
	 *            内部短信提醒,1提醒，0不提醒
     */
    public void setIsmsg(String ismsg) {
        this.ismsg = ismsg == null ? null : ismsg.trim();
    }

    /**
     * @return 收条，1回收条，0不回收条
     */
    public String getIsreceipt() {
        return isreceipt;
    }

    /**
     * @param isreceipt 
	 *            收条，1回收条，0不回收条
     */
    public void setIsreceipt(String isreceipt) {
        this.isreceipt = isreceipt == null ? null : isreceipt.trim();
    }

    /**
     * @return 重要标志:0一般邮件，1重要邮件，2非常重要
     */
    public String getImportantflag() {
        return importantflag;
    }

    /**
     * @param importantflag 
	 *            重要标志:0一般邮件，1重要邮件，2非常重要
     */
    public void setImportantflag(String importantflag) {
        this.importantflag = importantflag == null ? null : importantflag.trim();
    }

    /**
     * @return 发送标志:1发送成功，0未发送
     */
    public String getSendflag() {
        return sendflag;
    }

    /**
     * @param sendflag 
	 *            发送标志:1发送成功，0未发送
     */
    public void setSendflag(String sendflag) {
        this.sendflag = sendflag == null ? null : sendflag.trim();
    }

    /**
     * @return 发送的外部邮件编号
     */
    public Integer getFromwebmailid() {
        return fromwebmailid;
    }

    /**
     * @param fromwebmailid 
	 *            发送的外部邮件编号
     */
    public void setFromwebmailid(Integer fromwebmailid) {
        this.fromwebmailid = fromwebmailid;
    }

    /**
     * @return 发送的外部邮件地址
     */
    public String getFromwebmail() {
        return fromwebmail;
    }

    /**
     * @param fromwebmail 
	 *            发送的外部邮件地址
     */
    public void setFromwebmail(String fromwebmail) {
        this.fromwebmail = fromwebmail == null ? null : fromwebmail.trim();
    }

    /**
     * @return 外部邮件发送标志：1等待发送，2发送成功，3发送失败，0默认状态
     */
    public String getWebmailflag() {
        return webmailflag;
    }

    /**
     * @param webmailflag 
	 *            外部邮件发送标志：1等待发送，2发送成功，3发送失败，0默认状态
     */
    public void setWebmailflag(String webmailflag) {
        this.webmailflag = webmailflag == null ? null : webmailflag.trim();
    }

    /**
     * @return 邮件类型：0内部邮件，1内部外部邮件，2外部邮件
     */
    public String getMailtype() {
        return mailtype;
    }

    /**
     * @param mailtype 
	 *            邮件类型：0内部邮件，1内部外部邮件，2外部邮件
     */
    public void setMailtype(String mailtype) {
        this.mailtype = mailtype == null ? null : mailtype.trim();
    }

    /**
     * @return 关键字
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword 
	 *            关键字
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }
    /**
     * @return 接收人
     */
    public String getReceiveuser() {
        return receiveuser;
    }

    /**
     * @param receiveuser 
	 *            接收人
     */
    public void setReceiveuser(String receiveuser) {
        this.receiveuser = receiveuser == null ? null : receiveuser.trim();
    }

    public String getRecvusername() {
		return recvusername;
	}

	public void setRecvusername(String recvusername) {
		this.recvusername = recvusername;
	}

	/**
     * @return 抄送列表
     */
    public String getCopytouser() {
        return copytouser;
    }

    /**
     * @param copytouser 
	 *            抄送列表
     */
    public void setCopytouser(String copytouser) {
        this.copytouser = copytouser == null ? null : copytouser.trim();
    }

    public String getCopytousername() {
		return copytousername;
	}

	public void setCopytousername(String copytousername) {
		this.copytousername = copytousername;
	}

	/**
     * @return 密送列表
     */
    public String getSecrettouser() {
        return secrettouser;
    }

    /**
     * @param secrettouser 
	 *            密送列表
     */
    public void setSecrettouser(String secrettouser) {
        this.secrettouser = secrettouser == null ? null : secrettouser.trim();
    }

    public String getSecrettousername() {
		return secrettousername;
	}

	public void setSecrettousername(String secrettousername) {
		this.secrettousername = secrettousername;
	}

	/**
     * @return 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * @return 接收的外部邮件地址列表
     */
    public String getTowebmail() {
        return towebmail;
    }

    /**
     * @param towebmail 
	 *            接收的外部邮件地址列表
     */
    public void setTowebmail(String towebmail) {
        this.towebmail = towebmail == null ? null : towebmail.trim();
    }
    /**
     * @return 删除标志：1未删除，2进入废纸喽，0删除
     */
    public String getDelflag() {
        return delflag;
    }

    /**
     * @param delfalg 
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

	public List<EmailReceive> getEmailReceiveList() {
		return emailReceiveList;
	}

	public void setEmailReceiveList(List<EmailReceive> emailReceiveList) {
		this.emailReceiveList = emailReceiveList;
	}

    public Integer getReceivernum() {
        return receivernum;
    }

    public void setReceivernum(Integer receivernum) {
        this.receivernum = receivernum;
    }
}

