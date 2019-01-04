/**
 * @(#)OaCustomerFeeListener1.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-3-30 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.oa.model.OaCustomerFee;
import com.hd.agent.oa.service.IOaCustomerFeeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.oa.service.IOaPayService;

/**
 * 客户费用申请单（账扣）Listener
 * 生成收款单
 * 
 * @author limin
 */
public class OaCustomerFeeListener1 extends TaskListenerImpl {

	private IOaCustomerFeeService oaCustomerFeeService;

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaCustomerFeeService getOaCustomerFeeService() {
		return oaCustomerFeeService;
	}

	public void setOaCustomerFeeService(IOaCustomerFeeService oaCustomerFeeService) {
		this.oaCustomerFeeService = oaCustomerFeeService;
	}

	public IWorkService getWorkService() {
		return workService;
	}

	public void setWorkService(IWorkService workService) {
		this.workService = workService;
	}

	@Override
	public void notify(DelegateTask task) throws Exception {

		String instanceid = task.getExecution().getProcessInstanceId();
		
		Process process = workService.getProcess(instanceid, "2");
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(process.getBusinessid());
		if(fee == null) {
			
			return ;
		}
		
		int ret = payService.addCollectionOrderByOaCustomerFee(fee);
		addHandlerLog(task, this.getClass(), ret, fee);
	}

}

