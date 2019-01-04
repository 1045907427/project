/**
 * @(#)ReportConfigFile.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-7-18 zhanghonghui 创建版本
 */
package com.hd.agent.system.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ireport 报表配置文件
 * @author zhanghonghui
 */
public class ReportConfigFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4145612566815523728L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 名称，唯一
     */
    private String name;

    /**
     * 描述名称
     */
    private String descname;

    /**
     * 创建方式，系统预置1，自建2
     */
    private String createmethod;

    /**
     * 状态1启用0停用
     */
    private String state;

    /**
     * 存储的文件名称
     */
    private String filename;

    /**
     * 存储的文件路径
     */
    private String filepath;

    /**
     * 原文件名称
     */
    private String oldfilename;

    /**
     * 添加日期
     */
    private Date addtime;

    /**
     * 历史数
     */
    private Integer historycount;

    /**
     * 添加者编号
     */
    private String adduserid;
    /**
     * 文件上传编号
     */
    private String attachid;

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
     * @return 名称，唯一
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            名称，唯一
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return 描述名称
     */
    public String getDescname() {
        return descname;
    }

    /**
     * @param descname 
	 *            描述名称
     */
    public void setDescname(String descname) {
        this.descname = descname == null ? null : descname.trim();
    }

    /**
     * @return 创建方式，系统预置1，自建2
     */
    public String getCreatemethod() {
        return createmethod;
    }

    /**
     * @param createmethod 
	 *            创建方式，系统预置1，自建2
     */
    public void setCreatemethod(String createmethod) {
        this.createmethod = createmethod == null ? null : createmethod.trim();
    }

    /**
     * @return 状态1启用0停用
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1启用0停用
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 存储的文件名称
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename 
	 *            存储的文件名称
     */
    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    /**
     * @return 存储的文件路径
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * @param filepath 
	 *            存储的文件路径
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    /**
     * @return 原文件名称
     */
    public String getOldfilename() {
        return oldfilename;
    }

    /**
     * @param oldfilename 
	 *            原文件名称
     */
    public void setOldfilename(String oldfilename) {
        this.oldfilename = oldfilename == null ? null : oldfilename.trim();
    }

    /**
     * @return 添加日期
     */
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
     * @return 历史数
     */
    public Integer getHistorycount() {
        return historycount;
    }

    /**
     * @param historycount 
	 *            历史数
     */
    public void setHistorycount(Integer historycount) {
        this.historycount = historycount;
    }

    /**
     * @return 添加者编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加者编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

	public String getAttachid() {
		return attachid;
	}

	public void setAttachid(String attachid) {
		this.attachid = attachid;
	}
}

