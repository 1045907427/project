/**
 * @(#)FileDistribWrite.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-9-23 zhanghonghui 创建版本
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
public class FileDistribWrite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -395520402926113737L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 传阅编号
     */
    private Integer fid;

    /**
     * 编辑时间
     */
    private Date addtime;

    /**
     * 编辑用户编号
     */
    private String adduserid;

    /**
     * 编辑用户姓名
     */
    private String addusername;

    /**
     * 用户类型：0编辑者，1初始添加人员
     */
    private String addtype;

    /**
     * 文件类型
     */
    private String filetype;

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
     * @return 传阅编号
     */
    public Integer getFid() {
        return fid;
    }

    /**
     * @param fid 
	 *            传阅编号
     */
    public void setFid(Integer fid) {
        this.fid = fid;
    }

    /**
     * @return 编辑时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime 
	 *            编辑时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return 编辑用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            编辑用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 编辑用户姓名
     */
    public String getAddusername() {
        return addusername;
    }

    /**
     * @param addusername 
	 *            编辑用户姓名
     */
    public void setAddusername(String addusername) {
        this.addusername = addusername == null ? null : addusername.trim();
    }

    /**
     * @return 用户类型：0编辑者，1初始添加人员
     */
    public String getAddtype() {
        return addtype;
    }

    /**
     * @param addtype 
	 *            用户类型：0编辑者，1初始添加人员
     */
    public void setAddtype(String addtype) {
        this.addtype = addtype == null ? null : addtype.trim();
    }

    /**
     * @return 文件类型
     */
    public String getFiletype() {
        return filetype;
    }

    /**
     * @param filetype 
	 *            文件类型
     */
    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }
}

