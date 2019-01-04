package com.hd.agent.accesscontrol.model;

import java.io.Serializable;
/**
 * 用户登录规则控制
 * @author 陈伟
 */
public class SysLoginRule implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userid;

    private String username;
    
    private String name;
    
    private String isphone;
    
    private String departmentid;
    
    private String departmentname;
    /**
     * 登录规则控制1内网登录2外网登录3指定IP地址4指定mac地址
     */
    private String logintype;

    /**
     * ip地址
     */
    private String ip;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 手机登录控制1不限制手机2绑定手机sid
     */
    private String ptype;

    /**
     * 手机登录sid
     */
    private String psid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return 登录规则控制1内网登录2外网登录3指定IP地址4指定mac地址
     */
    public String getLogintype() {
        return logintype;
    }

    /**
     * @param logintype 
	 *            登录规则控制1内网登录2外网登录3指定IP地址4指定mac地址
     */
    public void setLogintype(String logintype) {
        this.logintype = logintype == null ? null : logintype.trim();
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

    /**
     * @return mac地址
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac 
	 *            mac地址
     */
    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    /**
     * @return 手机登录控制1不限制手机2绑定手机sid
     */
    public String getPtype() {
        return ptype;
    }

    /**
     * @param ptype 
	 *            手机登录控制1不限制手机2绑定手机sid
     */
    public void setPtype(String ptype) {
        this.ptype = ptype == null ? null : ptype.trim();
    }

    /**
     * @return 手机登录sid
     */
    public String getPsid() {
        return psid;
    }

    /**
     * @param psid 
	 *            手机登录sid
     */
    public void setPsid(String psid) {
        this.psid = psid == null ? null : psid.trim();
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsphone() {
		return isphone;
	}

	public void setIsphone(String isphone) {
		this.isphone = isphone;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}
    
}