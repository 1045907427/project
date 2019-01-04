/**
 * @(#)UserLoginNum.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月21日 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.model;

import java.io.Serializable;

/**
 * 
 * 用户登录数量
 * @author chenwei
 */
public class UserLoginNum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1075049714401088672L;
	/**
	 * 手机用户登录数量
	 */
	private int phonenum;
	/**
	 * 系统用户登录数量
	 */
	private int sysnum;
	/**
	 * 总数量
	 */
	private int totalnum;
	public int getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(int phonenum) {
		this.phonenum = phonenum;
	}
	public int getSysnum() {
		return sysnum;
	}
	public void setSysnum(int sysnum) {
		this.sysnum = sysnum;
	}
	public int getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}
	
	
}

