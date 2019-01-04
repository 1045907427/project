/**
 * @(#)OaSupplierPayListener2.java
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
import com.hd.agent.oa.model.OaSupplierPay;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaSupplierPayService;

/**
 * 货款支付申请单Listener
 * 
 * @author limin
 */
public class OaSupplierPayListener2 extends TaskListenerImpl {

	private IOaSupplierPayService oaSupplierPayService;
	
	public IOaPayService getPayService() {
		return payService;
	}

	public void setPayService(IOaPayService payService) {
		this.payService = payService;
	}

	public IOaSupplierPayService getOaSupplierPayService() {
		return oaSupplierPayService;
	}

	public void setOaSupplierPayService(IOaSupplierPayService oaSupplierPayService) {
		this.oaSupplierPayService = oaSupplierPayService;
	}

	@Override
	public void notify(DelegateTask task) throws Exception {

		String instanceid = task.getExecution().getProcessInstanceId();
		
		Process process = workService.getProcess(instanceid, "2");
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaSupplierPay pay = oaSupplierPayService.selectSupplierPay(process.getBusinessid());
		if(pay == null) {
			
			return ;
		}
		
		int ret = payService.rollbackPayOrderBySupplierPay(pay);
		addHandlerLog(task, this.getClass(), ret, pay);
	}

}

