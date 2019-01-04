/**
 * @(#)UserDetailBaseService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.base;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hd.agent.accesscontrol.hasp.AgentHasp;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.service.ISecurityService;
import com.hd.agent.accesscontrol.service.ISysUserService;


/**
 * 
 * 从数据库中读取用户的角色、权限等信息并授予该用户
 * @author chenwei
 */
public class UserDetailBaseService implements UserDetailsService {
	private static final Logger logger = Logger.getLogger(Logger.class);
	private ISecurityService securityService;
	private ISysUserService sysUserService;
	public ISecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}
	
	public ISysUserService getSysUserService() {
		return sysUserService;
	}
	public void setSysUserService(ISysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		SysUser sysuser = null;
		String password = "";
		String username1 = username;
		boolean isPhoneLogin = false;
		boolean isClientLogin = false;
		boolean isWechatLogin = false;
		if(username.startsWith("P_")){
			String[] strArr = username.split("P_");
			username1 = strArr[1];
			isPhoneLogin = true;
		} else if(username.startsWith("CW_")){
			String[] strArr = username.split("CW_");
			username1 = strArr[1];
			isWechatLogin = true;
		} else if(username.startsWith("C_")){
			String[] strArr = username.split("C_");
			username1 = strArr[1].split("@@")[0];
			isClientLogin = true;
		}
		try {
			auths = securityService.getUserAuthority(username1);
			sysuser = sysUserService.getUser(username1);
			
		} catch (Exception e) {
			logger.error(e,e);
		}
		password = sysuser.getPassword();
		User user = new User(username,password,true,true,true,true,auths);
		return user;
	}

}

