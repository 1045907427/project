/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-28 chenwei 创建版本
 */
package com.hd.agent.common.model;

import net.sf.json.JSONObject;
import org.apache.struts2.json.annotations.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作日志
 * @author chenwei
 */
public class SysLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 日志编号
     */
    private Long id;

    /**
     * 日志类型0其他操作1查询2新增3修改4删除
     */
    private String type;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 关键字
     */
    private String keyname;

    /**
     * 添加时间
     */
    private Date addtime;
    
    /**
     * 搜索的开始时间
     */
    private Date begintime;
    /**
     * 修改记录编号
     */
    private String dataid;
    /**
     * 搜索的结束时间
     */
    private Date endtime;
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
     * 添加人编号
     */
    private String userid;

    /**
     * 添加人姓名
     */
    private String name;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 修改前数据
     */
    public String olddata;
    /**
     * 修改后数据
     */
    public String newdata;
    /**
     * 被修改的数据
     */
    public String changedata;

    /**
     * @return 日志编号
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 
	 *            日志编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 日志类型0其他操作1查询2新增3修改4删除
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            日志类型0其他操作1查询2新增3修改4删除
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return 日志内容
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content 
	 *            日志内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * @return 关键字
     */
    public String getKeyname() {
        return keyname;
    }

    /**
     * @param keyname 
	 *            关键字
     */
    public void setKeyname(String keyname) {
        this.keyname = keyname == null ? null : keyname.trim();
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
     * @return 添加人编号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            添加人编号
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 添加人姓名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            添加人姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return ip地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip 
	 *            ip地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getOlddata() {
        return olddata;
    }

    public void setOlddata(String olddata) {
        this.olddata = olddata;
    }

    public String getChangedata() {
        return changedata;
    }

    public void setChangedata(String changedata) {
        this.changedata = changedata;
    }

    public String getNewdata() {
        return newdata;
    }

    public void setNewdata(String newdata) {
        this.newdata = newdata;
    }

}