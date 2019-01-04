/**
 * @(#)OaCustomerPayListener2.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-6 limin 创建版本
 */
package com.hd.agent.oa.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaCustomerPay;
import com.hd.agent.oa.service.IOaCustomerPayService;

/**
 * 客户费用支付申请单Listener
 * 删除支付单
 * 
 * @author limin
 */
public class OaCustomerPayListener2 extends TaskListenerImpl {

	private IOaCustomerPayService oaCustomerPayService;

	public IOaCustomerPayService getOaCustomerPayService() {
		return oaCustomerPayService;
	}

	public void setOaCustomerPayService(IOaCustomerPayService oaCustomerPayService) {
		this.oaCustomerPayService = oaCustomerPayService;
	}

	@Override
	public void notify(DelegateTask task) throws Exception {

		String instanceid = task.getExecution().getProcessInstanceId();
		
		Process process = workService.getProcess(instanceid, "2");
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaCustomerPay pay = oaCustomerPayService.selectCustomerPayInfo(process.getBusinessid());
		if(pay == null) {
			
			return ;
		}
		
		int ret = payService.rollbackBankBillByOaCustomerPay(pay);
		addHandlerLog(task, this.getClass(), ret, pay);
	}

}

