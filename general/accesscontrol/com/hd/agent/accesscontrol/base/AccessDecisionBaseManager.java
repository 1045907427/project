/**
 * @(#)AccessDecisionBaseManager.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.base;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


/**
 * 
 * 验证用户是否有访问该资源的权限
 * @author chenwei
 */
public class AccessDecisionBaseManager implements AccessDecisionManager {

	public void decide(Authentication arg0, Object arg1,
			Collection<ConfigAttribute> arg2) throws AccessDeniedException,
			InsufficientAuthenticationException {
		
		if(null == arg2){
			return;
		}
		//用户拥有的权限与系统访问的权限比较
		//判断用户是否有权限访问该资源（URL）
		for(ConfigAttribute configAttribute : arg2){
			for(GrantedAuthority gAuthority : arg0.getAuthorities()){
				if(configAttribute.getAttribute().trim().equals(gAuthority.getAuthority().trim())){
					return;
				}
			}
		}
		//无权限抛出拒绝异常
		throw new AccessDeniedException("无权限");
	}

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean supports(ConfigAttribute arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}

