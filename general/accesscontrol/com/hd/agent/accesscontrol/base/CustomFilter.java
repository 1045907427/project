/**
 * @(#)CustomFilter.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;




/**
 * 该过滤器的主要作用就是通过spring的IoC生成securityMetadataSource
 * 该CustomFilter的作用提从数据库提取权限和资源，装配到HashMap中，供Spring Security使用，用于权限校验。
 * @author 陈伟
 *
 */
public class CustomFilter extends AbstractSecurityInterceptor implements Filter{
	 private FilterInvocationSecurityMetadataSource securityMetadataSource;  
	  
	    @Override  
	    public SecurityMetadataSource obtainSecurityMetadataSource() {  
	        return this.securityMetadataSource;  
	    }  
	  
	    public void doFilter(ServletRequest request, ServletResponse response,  
	            FilterChain chain) throws IOException, ServletException {  
	        FilterInvocation fi = new FilterInvocation(request, response, chain);  
	        invoke(fi);  
	    }  
	      
	    private void invoke(FilterInvocation fi) throws IOException, ServletException {  
	        // object为FilterInvocation对象  
	                  //super.beforeInvocation(fi);源码  
	        //1.获取请求资源的权限  
	        //执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);  
	        //2.是否拥有权限  
	        //this.accessDecisionManager.decide(authenticated, object, attributes);  
	        InterceptorStatusToken token = super.beforeInvocation(fi);  
	        try {  
	            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());  
	        } finally {  
	            super.afterInvocation(token, null);  
	        }  
	    }  
	  
	    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {  
	        return securityMetadataSource;  
	    }  
	  
	    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {  
	        this.securityMetadataSource = securityMetadataSource;  
	    }  
	      
	    public void init(FilterConfig arg0) throws ServletException {  
	        // TODO Auto-generated method stub  
	    }  
	      
	    public void destroy() {  
	        // TODO Auto-generated method stub  
	          
	    }  
	  
	    @Override  
	    public Class<? extends Object> getSecureObjectClass() {  
	        //下面的MyAccessDecisionManager的supports方面必须放回true,否则会提醒类型错误  
	        return FilterInvocation.class;  
	    }  
}
