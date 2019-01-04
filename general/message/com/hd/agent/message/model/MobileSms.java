/**
 * @(#)SmsContent.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-6 zhanghonghui 创建版本
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
public class MobileSms implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8558895341425802723L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 发送者编号
     */
    private String adduserid;
    /**
     * 发送者姓名
     */
    private String addusername;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 发送时间
     */
    private Date sendtime;

    /**
     * 发送标志，1未发送，2发送失败，0发送成功
     */
    private String sendflag;

    /**
     * 删除标志,1未删除，0已删除
     */
    private String delflag;

    /**
     * 删除时间
     */
    private Date deltime;

    /**
     * 接收人编号
     */
    private String recvuserid;
    /**
     * 接收人姓名
     */
    private String recvusername;

    /**
     * 接收手机号
     */
    private String mobile;

    /**
     * 发送次数
     */
    private Integer sendnum;

    /**
     * 处理时间
     */
    private Date dealtime;

    /**
     * 短信内容
     */
    private String content;
    
    /**
     * 接收人，多个接收人使用“,”分隔 
     */
    private String receivers;
    /**
     * 接收电话，多个接收人使用“,”分隔
     */
    private String recvphones;
    /**
     * 人呗档案中接收人，多个接收人使用“,”分隔 
     */
    private String recvpersons;
    /**
     * 序列，用来判断同一人发送
     */
    private String serialno;

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
     * @return 发送者编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            发送者编号
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
     * @return 发送标志，1未发送，2发送失败，0发送成功
     */
    public String getSendflag() {
        return sendflag;
    }

    /**
     * @param sendflag 
	 *            发送标志，1未发送，2发送失败，0发送成功
     */
    public void setSendflag(String sendflag) {
        this.sendflag = sendflag == null ? null : sendflag.trim();
    }

    /**
     * @return 删除标志,1未删除，0已经删除
     */
    public String getDelflag() {
        return delflag;
    }

    /**
     * @param delflag 
	 *            删除标志,1未删除，0已经删除
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
     * @return 接收手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile 
	 *            接收手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return 发送次数
     */
    public Integer getSendnum() {
        return sendnum;
    }

    /**
     * @param sendnum 
	 *            发送次数
     */
    public void setSendnum(Integer sendnum) {
        this.sendnum = sendnum;
    }

    /**
     * @return 处理时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getDealtime() {
        return dealtime;
    }

    /**
     * @param dealtime 
	 *            处理时间
     */
    public void setDealtime(Date dealtime) {
        this.dealtime = dealtime;
    }

    /**
     * @return 短信内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            短信内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public String getRecvphones() {
		return recvphones;
	}

	public void setRecvphones(String recvphones) {
		this.recvphones = recvphones;
	}

	public String getRecvpersons() {
		return recvpersons;
	}

	public void setRecvpersons(String recvpersons) {
		this.recvpersons = recvpersons;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
}

