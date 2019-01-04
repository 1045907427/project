/**
 * @(#)OaInnerShareListener2.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-17 limin 创建版本
 */
package com.hd.agent.oa.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import com.hd.agent.oa.service.IOaInnerShareService;

/**
 * 内部分摊/内部调账申请单
 * 
 * @author limin
 */
public class OaInnerShareListener2 extends TaskListenerImpl {

	private IOaInnerShareService oaInnerShareService;

	public IOaInnerShareService getOaInnerShareService() {
		return oaInnerShareService;
	}

	public void setOaInnerShareService(IOaInnerShareService oaInnerShareService) {
		this.oaInnerShareService = oaInnerShareService;
	}

	@Override
	public void notify(DelegateTask task) throws Exception {

		/*
		String instanceid = task.getExecution().getProcessInstanceId();
		Process process = workService.getProcess(instanceid, "2");
		
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}

		OaInnerShare share = oaInnerShareService.selectOaInnerShare(process.getBusinessid());
		
		if(share == null) {
			
			return ;
		}
		
		payService.addDeptDailyCostByOaInnerShare(share);
		*/

	}

}

