/**
 * @(#)BusinessListener.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 10, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.service;

import com.hd.agent.activiti.model.Process;

/**
 * 
 * 启动流程时的其他操作
 * @author zhengziyong
 */
public interface BusinessListener {

	public void before(Process process);
	
}

