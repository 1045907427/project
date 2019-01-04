/**
 * @(#)OaCustomerFeeListener3.java
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
import com.hd.agent.oa.model.OaCustomerFeeDetail;
import com.hd.agent.oa.service.IOaCustomerFeeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;

import java.util.List;

/**
 * 客户费用申请单（账扣）Listener
 * 生成代垫
 * 
 * @author limin
 */
public class OaCustomerFeeListener3 extends TaskListenerImpl {

	private IOaCustomerFeeService oaCustomerFeeService;

	public IOaCustomerFeeService getOaCustomerFeeService() {
		return oaCustomerFeeService;
	}

	public void setOaCustomerFeeService(IOaCustomerFeeService oaCustomerFeeService) {
		this.oaCustomerFeeService = oaCustomerFeeService;
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

		List<OaCustomerFeeDetail> list = oaCustomerFeeService.getCustomerFeeDetailListByBillid(fee.getId());
		
		int ret = payService.addMatcostsInputByOaCustomerFee(fee, list);
		addHandlerLog(task, this.getClass(), ret, fee, list);
	}

}

