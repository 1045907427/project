/**
 * @(#)BusinessEndListenerImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-16 limin 创建版本
 */
package com.hd.agent.activiti.service.impl;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.BusinessEndListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author limin
 */
public class BusinessEndListenerImpl implements BusinessEndListener {

	@Override
	public void delete(Process process) throws Exception {
		
		return ;
	}

	@Override
	public void end(Process process) throws Exception {
		
		return ;
	}

	@Override
	public boolean check(Process process) throws Exception {

		// true: 默认可以删除
		return true;
	}

    @Override
    public Map rollback(Process process) throws Exception {

        this.delete(process);

        Map map = new HashMap();
        map.put("flag", true);
        return map;
    }
}

