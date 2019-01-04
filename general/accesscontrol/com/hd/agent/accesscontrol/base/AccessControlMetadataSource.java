/**
 * @(#)SecurityMetadataSource.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.hd.agent.accesscontrol.service.ISecurityService;


/**
 * 初始化权限与资源的对应关系
 * @author chenwei
 */
public class AccessControlMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	
	private ISecurityService securityService;
	
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	/**
	 * 权限与资源的对应关系
	 */
	public static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	public AccessControlMetadataSource(ISecurityService securityService) {
		// TODO Auto-generated constructor stub
		this.securityService = securityService;
		loadResourceDefine();
	}
	/**
	 * 建立请求url(key)与权限(value)的关系集合
	 */
	public void loadResourceDefine(){
		try {
			resourceMap  = securityService.getResourceMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
	/**
	 * 根据url获取需要访问该url需要的权限
	 */
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		// object 是一个URL，被用户请求的url。
		String url = ((FilterInvocation) object).getRequestUrl();
		//刷新资源权限对应关系数据
		if(null == resourceMap){
			loadResourceDefine();
		}
		int firstQuestionMarkIndex = url.indexOf("?");
		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if(null!=resURL){
				if (urlMatcher.pathMatchesUrl(url, resURL)) {
					return resourceMap.get(resURL);
				}
			}
		}
		return null;
	}

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}
	public ISecurityService getSecurityService() {
		return securityService;
	}
	public void setSecurityService(ISecurityService securityService) {
		this.securityService = securityService;
	}
	/**
	 * 刷新权限与资源的对应关系
	 * @author chenwei 
	 * @date 2012-12-12
	 */
	public static void refresh(){
		resourceMap = null;
	}
}

