package com.hd.agent.message.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class MsgContent implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3355559016143180316L;

	/**
     * 编号
     */
    private int id;

    /**
     * 标题
     */
    private String title;

    /**
     * 添加人编号
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 定时发送时间（到该时段发送 2013-01-20 12:15）
     */
    private String clocktime;

    /**
     * 定时发送是否执行1即时发2定时发0定时已发送
     */
    private String clocktype;

    /**
     * 消息类型1个人短信2公告通知3电子邮件4工作流5业务预警
     */
    private String msgtype;

    /**
     * 详细地址
     */
    private String url;

    /**
     * 删除标志1未删除0已删除
     */
    private String delflag;
    
    /**
     * 删除时间
     */
    private Date deltime;
    /**
     * 内容
     */
    private String content;
    /**
     * 接收人列表
     */
    private String receivers;
    /**
     * 接收人名称
     */
    private String receiveusername;
    
    /**
     * 消息接收人
     */
    private List<MsgReceive> msgReceive;
    /**
     * 发送人
     */
    private String addusername;
    /**
     * tab标签
     */
    private String tabtitle;
    
    private String ismsgphoneurlshow;
    /**
     * 接收人
     */
    private Integer receivernum;

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
     * @return 添加人编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人编号
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
     * @return 定时发送时间（到该时段发送 2013-01-20 12:15）
     */
    public String getClocktime() {
        return clocktime;
    }

    /**
     * @param clocktime 
	 *            定时发送时间（到该时段发送 2013-01-20 12:15）
     */
    public void setClocktime(String clocktime) {
        this.clocktime = clocktime == null ? null : clocktime.trim();
    }

    /**
     * @return 定时发送是否执行1即时发2定时发0定时已发送
     */
    public String getClocktype() {
        return clocktype;
    }

    /**
     * @param clocktype 
	 *            定时发送是否执行1即时发2定时发0定时已发送
     */
    public void setClocktype(String clocktype) {
        this.clocktype = clocktype == null ? null : clocktype.trim();
    }

    /**
     * @return 消息类型1个人短信2公告通知3电子邮件4工作流5业务预警
     */
    public String getMsgtype() {
        return msgtype;
    }

    /**
     * @param msgtype 
	 *            消息类型1个人短信2公告通知3电子邮件4工作流5业务预警
     */
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype == null ? null : msgtype.trim();
    }

    /**
     * @return 详细地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 
	 *            详细地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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
     * 内容
     * @return
     * @author zhanghonghui 
     * @date 2013-1-19
     */
	public String getContent() {
		return content;
	}
	/**
	 * 内容
	 * @param content
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 接收人
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public String getReceivers() {
		return receivers;
	}

	/**
	 * 接收人
	 * @param receivers
	 * @author zhanghonghui 
	 * @date 2013-1-19
	 */
	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

    @JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getDeltime() {
		return deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public List<MsgReceive> getMsgReceive() {
		return msgReceive;
	}

	public void setMsgReceive(List<MsgReceive> msgReceive) {
		this.msgReceive = msgReceive;
	}

	public String getTabtitle() {
		return tabtitle;
	}

	public void setTabtitle(String tabtitle) {
		this.tabtitle = tabtitle;
	}

	public String getReceiveusername() {
		return receiveusername;
	}

	public void setReceiveusername(String receiveusername) {
        this.receiveusername = receiveusername == null ? null : receiveusername.trim();
	}

	public String getIsmsgphoneurlshow() {
		return ismsgphoneurlshow;
	}

	public void setIsmsgphoneurlshow(String ismsgphoneurlshow) {
		this.ismsgphoneurlshow = ismsgphoneurlshow;
	}

    public Integer getReceivernum() {
        return receivernum;
    }

    public void setReceivernum(Integer receivernum) {
        this.receivernum = receivernum;
    }
}