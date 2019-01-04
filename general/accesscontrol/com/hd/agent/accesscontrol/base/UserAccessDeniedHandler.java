/**
 * @(#)UserAccessDeniedHandler.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-13 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 无权限时页面跳转
 * @author chenwei
 */
public class UserAccessDeniedHandler implements AccessDeniedHandler {
	//无权限统一跳转页面
	private String accessDeniedUrl;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException arg2) throws IOException, ServletException {
		//判断是否为ajax请求
        String requestType = request.getHeader("X-Requested-With");
        if(requestType !=null && "XMLHttpRequest".equals(requestType)){
        	//设置返回状态代码 901表示未登录 902无权限 903session并发
        	response.setStatus(902);
        }else{
        	response.setStatus(902);
            response.sendRedirect(accessDeniedUrl);
        }
	}

	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

}

