/**
 * @(#)OaBrandFeeListener4.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-11 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.oa.model.OaBrandFee;
import com.hd.agent.oa.service.IOaBrandFeeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;

/**
 * 品牌费用申请单（支付）Listener
 * 删除客户应付费用
 * 
 * @author limin
 */
public class OaBrandFeeListener4 extends TaskListenerImpl {

	private IOaBrandFeeService oaBrandFeeService;

	public IOaBrandFeeService getOaBrandFeeService() {
		return oaBrandFeeService;
	}

	public void setOaBrandFeeService(IOaBrandFeeService oaBrandFeeService) {
		this.oaBrandFeeService = oaBrandFeeService;
	}

	@Override
	public void notify(DelegateTask task) throws Exception {

		String instanceid = task.getExecution().getProcessInstanceId();
		
		Process process = workService.getProcess(instanceid, "2");
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaBrandFee fee = oaBrandFeeService.selectOaBrandFee(process.getBusinessid());
		if(fee == null) {
			
			return ;
		}
		
		int ret = payService.rollbackCustomerFeeByOaBrandFee(fee);
		addHandlerLog(task, this.getClass(), ret, fee);
	}

}

