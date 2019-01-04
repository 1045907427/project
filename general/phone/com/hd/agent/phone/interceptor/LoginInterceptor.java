package com.hd.agent.phone.interceptor;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @(#)LoginInterceptor.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 21, 2013 zhengziyong 创建版本
 */
/**
 * 
 * 
 * @author zhengziyong
 */
public class LoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		ActionContext ac = arg0.getInvocationContext();
		Map session = ac.getSession();
		Object userId = session.get("userid");
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getRequestURI();
		if(userId == null && !url.contains("login")){
			return Action.LOGIN;
		}
		else{
			return arg0.invoke();
		}
	}

}

