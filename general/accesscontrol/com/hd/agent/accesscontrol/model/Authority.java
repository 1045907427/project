/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
/**
 * 权限实体类
 * @author chenwei
 */
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    private String authorityid;

    /**
     * 权限名称
     */
    private String authorityname;

    /**
     * 权限描述
     */
    private String description;
    /**
     * 权限（角色）别名
     */
    private String alias;
    /**
     * 状态1有效0无效
     */
    private String state;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人用户编号
     */
    private String adduserid;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 修改人用户编号
     */
    private String modifyuserid;

    /**
     * @return 权限id
     */
    public String getAuthorityid() {
        return authorityid;
    }

    /**
     * @param authorityid 
	 *            权限id
     */
    public void setAuthorityid(String authorityid) {
        this.authorityid = authorityid == null ? null : authorityid.trim();
    }

    /**
     * @return 权限名称
     */
    public String getAuthorityname() {
        return authorityname;
    }

    /**
     * @param authorityname 
	 *            权限名称
     */
    public void setAuthorityname(String authorityname) {
        this.authorityname = authorityname == null ? null : authorityname.trim();
    }

    /**
     * @return 权限描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description 
	 *            权限描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return 状态1有效0无效
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态1有效0无效
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
     * @return 添加人用户编号
     */
    public String getAdduserid() {
        return adduserid;
    }

    /**
     * @param adduserid 
	 *            添加人用户编号
     */
    public void setAdduserid(String adduserid) {
        this.adduserid = adduserid == null ? null : adduserid.trim();
    }

    /**
     * @return 修改时间
     */
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime 
	 *            修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return 修改人用户编号
     */
    public String getModifyuserid() {
        return modifyuserid;
    }

    /**
     * @param modifyuserid 
	 *            修改人用户编号
     */
    public void setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid == null ? null : modifyuserid.trim();
    }

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
    
    
}