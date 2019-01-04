/**
 * @(#)BusinessUndoListener.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 28, 2013 zhengziyong 创建版本
 */
package com.hd.agent.activiti.service;

import com.hd.agent.activiti.model.Process;

/**
 * 
 * 撤销流程时执行的业务相关方法，比如改变业务单据的状态
 * @author zhengziyong
 */
public interface BusinessUndoListener {

	public void undo(Process process) throws Exception;
	
}

