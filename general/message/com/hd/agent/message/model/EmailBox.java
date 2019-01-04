/**
 * @(#)EmailBox.java
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

import org.apache.struts2.json.annotations.JSON;

/**
 * 邮箱
 * 
 * @author zhanghonghui
 */
public class EmailBox implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1884513512443698373L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 序号
     */
    private Integer boxorderno;

    /**
     * 邮箱名称
     */
    private String title;

    /**
     * 添加人
     */
    private String adduserid;

    /**
     * 添加时间
     */
    private Date addtime;

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
     * @return 序号
     */
    public Integer getBoxorderno() {
        return boxorderno;
    }

    /**
     * @param boxno 
	 *            序号
     */
    public void setBoxorderno(Integer boxorderno) {
        this.boxorderno = boxorderno;
    }

    /**
     * @return 邮箱名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            邮箱名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @return 添加人
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
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
}

