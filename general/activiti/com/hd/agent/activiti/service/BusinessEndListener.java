/**
 * @(#)BusinessEndListener.java
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

import java.util.Map;

/**
 * 
 * 业务流程结束时执行
 * @author zhengziyong
 */
public interface BusinessEndListener {

	/**
	 * 结束流程
	 * @param process
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-14
	 */
	public void end(Process process) throws Exception;

	/**
	 * 删除草稿
	 * @param process
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-14
	 */
	public void delete(Process process) throws Exception;
	
	/**
	 * 判断该流程是否可以删除
	 * @param process
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-16
	 */
	public boolean check(Process process) throws Exception;

    /**
     * 作废
     * @param process
     * @return
     * @throws Exception
     */
    public Map rollback(Process process) throws Exception;
}

