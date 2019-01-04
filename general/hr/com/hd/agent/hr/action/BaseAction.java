/**
 * @(#)BaseAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-20 zhengziyong 创建版本
 */
package com.hd.agent.hr.action;


import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.system.service.ISysCodeService;

/**
 * 
 * 
 * @author zhengziyong
 */
public class BaseAction extends com.hd.agent.common.action.BaseAction {

	protected IDepartMentService departMentService;
	protected ISysCodeService sysCodeService;
	

	public IDepartMentService getDepartMentService() {
		return departMentService;
	}

	public void setDepartMentService(IDepartMentService departMentService) {
		this.departMentService = departMentService;
	}

	public ISysCodeService getSysCodeService() {
		return sysCodeService;
	}

	public void setSysCodeService(ISysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}
	
}

