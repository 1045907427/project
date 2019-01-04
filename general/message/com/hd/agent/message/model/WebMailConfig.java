/**
 * @(#)WebEmail.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-5 zhanghonghui 创建版本
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
public class WebMailConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1641471405064990313L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 邮件名称
     */
    private String email;

    /**
     * 接收服务器(POP3)
     */
    private String popserver;
    
    /**
     * 接收服务器端口
     */
    private int popport;

    /**
     * 接收服务器安全连接(SSL):1是，0否
     */
    private String ispopssl;

    /**
     * 发送服务器(SMTP)
     */
    private String smtpserver;
    /**
     * 发送服务器端口
     */
    private int smtpport;

    /**
     * 发送服务器安全连接(SSL):1是，0否
     */
    private String issmtpssl;

    /**
     * 登录账户
     */
    private String emailuser;

    /**
     * 登录密码
     */
    private String emailpwd;

    /**
     * SMTP身份验证:1是，0否
     */
    private String issmtppass;

    /**
     * 自动收取外部邮件:1是，0否
     */
    private String autorecvflag;

    /**
     * 邮箱容量(MB)，为0表示不限制
     */
    private Integer quotalimit;

    /**
     * 默认内部邮件外发邮箱，1默认，0不默认
     */
    private String isdefault;

    /**
     * 收取后删除，1删除，0不删除
     */
    private String isrecvdel;

    /**
     * 收取新邮件短信提醒，1提醒，0不提醒
     */
    private String isrecvmsg;

    /**
     * 添加用户编号
     */
    private String adduserid;

    /**
     * 添加日期
     */
    private Date addtime;

    /**
     * 邮件的uid
     */
    private String emailuid;

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
     * @return 邮件名称
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 
	 *            邮件名称
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return 接收服务器(POP3)
     */
    public String getPopserver() {
        return popserver;
    }

    /**
     * @param popserver 
	 *            接收服务器(POP3)
     */
    public void setPopserver(String popserver) {
        this.popserver = popserver == null ? null : popserver.trim();
    }

    /**
     * 接收服务器端口
     * @return
     * @author zhanghonghui 
     * @date 2013-2-5
     */
    public int getPopport() {
		return popport;
	}

    /**
     * 
     * @param popport
     * 			接收服务器端口
     * @author zhanghonghui 
     * @date 2013-2-5
     */
	public void setPopport(int popport) {
		this.popport = popport;
	}

	/**
     * @return 接收服务器安全连接(SSL):1是，0否
     */
    public String getIspopssl() {
        return ispopssl;
    }

    /**
     * @param ispopssl 
	 *            接收服务器安全连接(SSL):1是，0否
     */
    public void setIspopssl(String ispopssl) {
        this.ispopssl = ispopssl == null ? null : ispopssl.trim();
    }

    /**
     * @return 发送服务器(SMTP)
     */
    public String getSmtpserver() {
        return smtpserver;
    }

    /**
     * @param smtpserver 
	 *            发送服务器(SMTP)
     */
    public void setSmtpserver(String smtpserver) {
        this.smtpserver = smtpserver == null ? null : smtpserver.trim();
    }

    /**
     * 发送服务端口
     * @return
     * @author zhanghonghui 
     * @date 2013-2-5
     */
    public int getSmtpport() {
		return smtpport;
	}

    /**
     * 
     * @param smtpport
     * 			发送服务端口
     * @author zhanghonghui 
     * @date 2013-2-5
     */
	public void setSmtpport(int smtpport) {
		this.smtpport = smtpport;
	}

	/**
     * @return 发送服务器安全连接(SSL):1是，0否
     */
    public String getIssmtpssl() {
        return issmtpssl;
    }

    /**
     * @param issmtpssl 
	 *            发送服务器安全连接(SSL):1是，0否
     */
    public void setIssmtpssl(String issmtpssl) {
        this.issmtpssl = issmtpssl == null ? null : issmtpssl.trim();
    }

    /**
     * @return 登录账户
     */
    public String getEmailuser() {
        return emailuser;
    }

    /**
     * @param emailuser 
	 *            登录账户
     */
    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser == null ? null : emailuser.trim();
    }

    /**
     * @return 登录密码
     */
    public String getEmailpwd() {
        return emailpwd;
    }

    /**
     * @param emailpwd 
	 *            登录密码
     */
    public void setEmailpwd(String emailpwd) {
        this.emailpwd = emailpwd == null ? null : emailpwd.trim();
    }

    /**
     * @return SMTP身份验证:1是，0否
     */
    public String getIssmtppass() {
        return issmtppass;
    }

    /**
     * @param issmtppass 
	 *            SMTP身份验证:1是，0否
     */
    public void setIssmtppass(String issmtppass) {
        this.issmtppass = issmtppass == null ? null : issmtppass.trim();
    }

    /**
     * @return 自动收取外部邮件:1是，0否
     */
    public String getAutorecvflag() {
        return autorecvflag;
    }

    /**
     * @param checkflag 
	 *            自动收取外部邮件:1是，0否
     */
    public void setAutorecvflag(String autorecvflag) {
        this.autorecvflag = autorecvflag == null ? null : autorecvflag.trim();
    }

    /**
     * @return 邮箱容量(MB)，为0表示不限制
     */
    public Integer getQuotalimit() {
        return quotalimit;
    }

    /**
     * @param quotalimit 
	 *            邮箱容量(MB)，为0表示不限制
     */
    public void setQuotalimit(Integer quotalimit) {
        this.quotalimit = quotalimit;
    }

    /**
     * @return 默认内部邮件外发邮箱，1默认，0不默认
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * @param isdefault 
	 *            默认内部邮件外发邮箱，1默认，0不默认
     */
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault == null ? null : isdefault.trim();
    }

    /**
     * @return 收取后删除，1删除，0不删除
     */
    public String getIsrecvdel() {
        return isrecvdel;
    }

    /**
     * @param isrecvdel 
	 *            收取后删除，1删除，0不删除
     */
    public void setIsrecvdel(String isrecvdel) {
        this.isrecvdel = isrecvdel == null ? null : isrecvdel.trim();
    }

    /**
     * @return 收取新邮件短信提醒，1提醒，0不提醒
     */
    public String getIsrecvmsg() {
        return isrecvmsg;
    }

    /**
     * @param isrecvmsg 
	 *            收取新邮件短信提醒，1提醒，0不提醒
     */
    public void setIsrecvmsg(String isrecvmsg) {
        this.isrecvmsg = isrecvmsg == null ? null : isrecvmsg.trim();
    }

    /**
     * @return 添加用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 添加日期
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            添加日期
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 邮件的uid
     */
    public String getEmailuid() {
        return emailuid;
    }

    /**
     * @param emailuid 
	 *            邮件的uid
     */
    public void setEmailuid(String emailuid) {
        this.emailuid = emailuid == null ? null : emailuid.trim();
    }
}

