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
public class FileDistrib implements Serializable {
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
     * 分类，0内容显示、1文档显示、2表单显示
     */
    private String type;

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
     * 主题词
     */
    private String titleword;
    /**
     * 字号
     */
    private String wordsize;

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
     * 原内容，存放原内容、文档、表单
     */
    private String orgcont;
    /**
     * 内容编号
     */
    private String contid;
    /**
     * 文件转换类型，程序指定
     */
    private String cftype;

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
     * 接收用户内容修改版本
     */
    private int cversion;
    /**
     * 模板表单编号
     */
    private String formid;
    /**
     * 模板表单名称
     */
    private String formname;

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
     * @return 分类，0内容显示、1文档显示、2表单显示
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            分类，0内容显示、1文档显示、2表单显示
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
    public String getTitleword() {
        return titleword;
    }

    /**
     * @param keyword 
	 *            关键字
     */
    public void setTitleword(String titleword) {
        this.titleword = titleword == null ? null : titleword.trim();
    }


    public String getWordsize() {
		return wordsize;
	}

	public void setWordsize(String wordsize) {
		this.wordsize = wordsize;
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

    public String getOrgcont() {
		return orgcont;
	}

	public void setOrgcont(String orgcont) {
		this.orgcont = orgcont;
	}

	public String getContid() {
		return contid;
	}

	public void setContid(String contid) {
		this.contid = contid;
	}

	/**
	 * 文件转换类型，程序指定，0原文件，1flash文件，2html,3pdf
	 * @param cftype
	 * @author zhanghonghui 
	 * @date 2013-9-25
	 */
	public String getCftype() {
		return cftype;
	}

	/**
	 * 文件转换类型，程序指定，0原文件，1flash文件，2html,3pdf
	 * @param cftype
	 * @author zhanghonghui 
	 * @date 2013-9-25
	 */
	public void setCftype(String cftype) {
		this.cftype = cftype;
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

	public int getCversion() {
		return cversion;
	}

	public void setCversion(int cversion) {
		this.cversion = cversion;
	}

	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}
}

