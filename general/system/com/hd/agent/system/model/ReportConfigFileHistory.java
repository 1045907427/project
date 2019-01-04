/**
 * @(#)ReportConfigFileHistory.java
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
 * ireport 报表配置文件 历史
 * @author zhanghonghui
 */
public class ReportConfigFileHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6277155085043645724L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 配置文件编号
     */
    private Integer confid;

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
     * 当前使用状态，1当前使用，0未用
     */
    private String usestate;

    /**
     * 配置文件中添加时间
     */
    private Date confaddtime;

    /**
     * 配置文件中添加者编号
     */
    private String confadduserid;

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
     * @return 配置文件编号
     */
    public Integer getConfid() {
        return confid;
    }

    /**
     * @param confid 
	 *            配置文件编号
     */
    public void setConfid(Integer confid) {
        this.confid = confid;
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
     * @return 当前使用状态，1当前使用，0未用
     */
    public String getUsestate() {
        return usestate;
    }

    /**
     * @param usestate 
	 *            当前使用状态，1当前使用，0未用
     */
    public void setUsestate(String usestate) {
        this.usestate = usestate == null ? null : usestate.trim();
    }

    /**
     * @return 配置文件中添加时间
     */
    public Date getConfaddtime() {
        return confaddtime;
    }

    /**
     * @param confaddtime 
	 *            配置文件中添加时间
     */
    public void setConfaddtime(Date confaddtime) {
        this.confaddtime = confaddtime;
    }

    /**
     * @return 配置文件中添加者编号
     */
    public String getConfadduserid() {
        return confadduserid;
    }

    /**
     * @param confadduserid 
	 *            配置文件中添加者编号
     */
    public void setConfadduserid(String confadduserid) {
        this.confadduserid = confadduserid == null ? null : confadduserid.trim();
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

