/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-03-20 chenwei 创建版本
 */
package com.hd.agent.common.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 快捷操作
 * @author chenwei
 */
public class Shortcut implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 菜单操作编号
     */
    private String operateid;
    /**
     * 菜单描述名
     */
    private String description;
    /**
     * 别名
     */
    private String aliasname;

    /**
     * URL地址
     */
    private String url;
    /**
     * 菜单图片地址
     */
    private String image;
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
     * @return 用户编号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            用户编号
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 菜单操作编号
     */
    public String getOperateid() {
        return operateid;
    }

    /**
     * @param operateid 
	 *            菜单操作编号
     */
    public void setOperateid(String operateid) {
        this.operateid = operateid == null ? null : operateid.trim();
    }

    /**
     * @return 别名
     */
    public String getAliasname() {
        return aliasname;
    }

    /**
     * @param aliasname 
	 *            别名
     */
    public void setAliasname(String aliasname) {
        this.aliasname = aliasname == null ? null : aliasname.trim();
    }

    /**
     * @return URL地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url 
	 *            URL地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return 添加时间
     */
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
    
}