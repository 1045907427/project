/**
 * @(#)AgentSessionListener.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月21日 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.hd.agent.accesscontrol.base.AgentSessionContext;

/**
 * 
 * session监听
 * @author chenwei
 */
public class AgentSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		AgentSessionContext.AddSession(arg0.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		AgentSessionContext.DelSession(session);
	}

}

