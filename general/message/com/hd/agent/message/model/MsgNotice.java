/**
 * @(#)MsgNotice.java
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
public class MsgNotice implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8689514744840064917L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题颜色
     */
    private String tcolor;

    /**
     * 公告类型
     */
    private String type;
    /**
     * 公告类型名称
     */
    private String typename;

    /**
     * 格式:1普通格式，2MHT格式,3超级链接
     */
    private String form;

    /**
     * 是否置顶1是0否
     */
    private String istop;

    /**
     * 置顶天数
     */
    private Integer topday;

    /**
     * 是否内部短信提醒1是0否
     */
    private String ismsg;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 内容简介
     */
    private String intro;

    /**
     * 有效开始日期（yyyy-MM-dd）
     */
    private String startdate;

    /**
     * 有效结束日期
     */
    private String enddate;

    /**
     * 4新增/3暂存/2保存/1启用/0禁用
     */
    private String state;

    /**
     * 是否审核1是0否
     */
    private String isaud;

    /**
     * 审核状态0未审核1审核通过2审核不通过
     */
    private String audstate;

    /**
     * url地址
     */
    private String url;

    /**
     * 附件
     */
    private String attach;

    /**
     * 添加人编号
     */
    private String adduserid;
    
    /**
     * 添加人姓名
     */
    private String addusername;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人所属部门编号
     */
    private String adddeptid;
    
    /**
     * 添加人所属部门名称
     */
    private String adddeptname;

    /**
     * 修改人编号
     */
    private String modifyuserid;
    /**
     * 修改人姓名
     */
    private String modifyusername;

    /**
     * 修改人时间
     */
    private Date modifytime;

    /**
     * 删除标志:1未删除0已删除
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
     * 接收部门列表
     */
    private String receivedept;

    /**
     * 接收角色列表
     */
    private String receiverole;

    /**
     * 接收用户列表
     */
    private String receiveuser;
    /**
     * 接收部门列表
     */
    private String receivedeptname;
    /**
     * 接收角色列表
     */
    private String receiverolename;
    /**
     * 接收用户列表
     */
    private String receiveusername;
    /**
     * 阅读数
     */
    private int readcount;

    /**
     * 发布时间
     */
    private Date publishtime;

    /**
     * 发布人编号
     */
    private String publisherid;
    /**
     * 发布人姓名
     */
    private String publishername;
    /**
     * 发布人部门编号
     */
    private String publishdeptid;
    /**
     * 发布人部门名称
     */
    private String publishdeptname;

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
     * @return 标题颜色
     */
    public String getTcolor() {
        return tcolor;
    }

    /**
     * @param tcolor 
	 *            标题颜色
     */
    public void setTcolor(String tcolor) {
        this.tcolor = tcolor == null ? null : tcolor.trim();
    }

    /**
     * @return 公告类型
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            公告类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	/**
     * @return 格式:1链接形式，2内容,3内容简介
     */
    public String getForm() {
        return form;
    }

    /**
     * @param form 
	 *            格式:1链接形式，2内容,3内容简介
     */
    public void setForm(String form) {
        this.form = form == null ? null : form.trim();
    }

    /**
     * @return 是否置顶1是0否
     */
    public String getIstop() {
        return istop;
    }

    /**
     * @param istop 
	 *            是否置顶1是0否
     */
    public void setIstop(String istop) {
        this.istop = istop == null ? null : istop.trim();
    }

    /**
     * @return 置顶天数
     */
    public Integer getTopday() {
        return topday;
    }

    /**
     * @param topday 
	 *            置顶天数
     */
    public void setTopday(Integer topday) {
        this.topday = topday;
    }

    /**
     * @return 是否内部短信提醒1是0否
     */
    public String getIsmsg() {
        return ismsg;
    }

    /**
     * @param ismsg 
	 *            是否内部短信提醒1是0否
     */
    public void setIsmsg(String ismsg) {
        this.ismsg = ismsg == null ? null : ismsg.trim();
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
     * @return 内容简介
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro 
	 *            内容简介
     */
    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    /**
     * @return 有效开始日期（yyyy-MM-dd）
     */
    public String getStartdate() {
        return startdate;
    }

    /**
     * @param startdate 
	 *            有效开始日期（yyyy-MM-dd）
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    /**
     * @return 有效结束日期
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate 
	 *            有效结束日期
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    /**
     * @return 4新增/3暂存/2保存/1启用/0禁用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            4新增/3暂存/2保存/1启用/0禁用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 是否审核1是0否
     */
    public String getIsaud() {
        return isaud;
    }

    /**
     * @param isaud 
	 *            是否审核1是0否
     */
    public void setIsaud(String isaud) {
        this.isaud = isaud == null ? null : isaud.trim();
    }

    /**
     * @return 审核状态0未审核1审核通过2审核不通过
     */
    public String getAudstate() {
        return audstate;
    }

    /**
     * @param audstate 
	 *            审核状态0未审核1审核通过2审核不通过
     */
    public void setAudstate(String audstate) {
        this.audstate = audstate == null ? null : audstate.trim();
    }

    /**
     * @return url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 
	 *            url地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return 附件
     */
    public String getAttach() {
        return attach;
    }

    /**
     * @param attach 
	 *            有附件
     */
    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
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
     * @return 添加人所属部门编号
     */
    public String getAdddeptid() {
        return adddeptid;
    }

    /**
     * @param adddeptid 
	 *            添加人所属部门编号
     */
    public void setAdddeptid(String adddeptid) {
        this.adddeptid = adddeptid == null ? null : adddeptid.trim();
    }

    public String getAdddeptname() {
		return adddeptname;
	}

	public void setAdddeptname(String adddeptname) {
		this.adddeptname = adddeptname;
	}

	/**
     * @return 修改人编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

    public String getModifyusername() {
		return modifyusername;
	}

	public void setModifyusername(String modifyusername) {
		this.modifyusername = modifyusername;
	}

	/**
     * @return 修改人时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            修改人时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 删除标志:1未删除0已删除
     */
    public String getDelflag() {
        return delflag;
    }

    /**
     * @param delflag 
	 *            删除标志:1未删除0已删除
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
     * @return 接收部门列表
     */
    public String getReceivedept() {
        return receivedept;
    }

    /**
     * @param receivedept 
	 *            接收部门列表
     */
    public void setReceivedept(String receivedept) {
        this.receivedept = receivedept == null ? null : receivedept.trim();
    }

    /**
     * @return 接收角色列表
     */
    public String getReceiverole() {
        return receiverole;
    }

    /**
     * @param receiverole 
	 *            接收角色列表
     */
    public void setReceiverole(String receiverole) {
        this.receiverole = receiverole == null ? null : receiverole.trim();
    }

    /**
     * @return 接收用户列表
     */
    public String getReceiveuser() {
        return receiveuser;
    }

    /**
     * @param receiveuser 
	 *            接收用户列表
     */
    public void setReceiveuser(String receiveuser) {
        this.receiveuser = receiveuser == null ? null : receiveuser.trim();
    }

    /**
     * 接收部门名称列表
     * @return
     */
	public String getReceivedeptname() {
		return receivedeptname;
	}

	/**
	 * 接收部门名称列表
	 * @param receivedeptname
	 */
	public void setReceivedeptname(String receivedeptname) {
		this.receivedeptname = receivedeptname;
	}

	/**
	 * 接收角色名称列表
	 * @return
	 */
	public String getReceiverolename() {
		return receiverolename;
	}

	/**
	 * 接收角色名称列表
	 * @param receiverolename
	 */
	public void setReceiverolename(String receiverolename) {
		this.receiverolename = receiverolename;
	}

	/**
	 * 接收用户名称列表
	 * @return
	 */
	public String getReceiveusername() {
		return receiveusername;
	}

	/**
	 * 接收用户名称列表
	 * @param receiveusername
	 */
	public void setReceiveusername(String receiveusername) {
		this.receiveusername = receiveusername;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(String publisherid) {
        this.publisherid = publisherid == null ? null : publisherid.trim();
    }

    public String getPublishername() {
        return publishername;
    }

    public void setPublishername(String publishername) {
        this.publishername = publishername == null ? null : publishername.trim();
    }

    public String getPublishdeptid() {
        return publishdeptid;
    }

    public void setPublishdeptid(String publishdeptid) {
        this.publishdeptid = publishdeptid == null ? null : publishdeptid.trim();
    }

    public String getPublishdeptname() {
        return publishdeptname;
    }

    public void setPublishdeptname(String publishdeptname) {
        this.publishdeptname = publishdeptname == null ? null : publishdeptname.trim();
    }
}

