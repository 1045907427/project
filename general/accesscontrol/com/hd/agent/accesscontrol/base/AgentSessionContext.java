/**
 * @(#)AgentSessionContext.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月21日 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.base;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * 管理session
 * @author chenwei
 */
public class AgentSessionContext {
	private static HashMap mymap = new HashMap();

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }
    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
        return null;
        return (HttpSession) mymap.get(session_id);
    }
}

