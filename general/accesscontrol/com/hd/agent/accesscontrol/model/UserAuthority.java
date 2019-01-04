package com.hd.agent.accesscontrol.model;

import java.io.Serializable;
import java.util.Date;

public class UserAuthority implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户权限关联编号
     */
    private String userauthid;

    /**
     * 用户编号
     */
    private String userid;

    /**
     * 权限编号
     */
    private String authorityid;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人编号
     */
    private String adduserid;

    /**
     * @return 用户权限关联编号
     */
    public String getUserauthid() {
        return userauthid;
    }

    /**
     * @param userauthid 
	 *            用户权限关联编号
     */
    public void setUserauthid(String userauthid) {
        this.userauthid = userauthid == null ? null : userauthid.trim();
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
     * @return 权限编号
     */
    public String getAuthorityid() {
        return authorityid;
    }

    /**
     * @param authorityid 
	 *            权限编号
     */
    public void setAuthorityid(String authorityid) {
        this.authorityid = authorityid == null ? null : authorityid.trim();
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
}