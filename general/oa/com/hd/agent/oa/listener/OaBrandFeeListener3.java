/**
 * @(#)OaBrandFeeListener3.java
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
import com.hd.agent.oa.model.OaBrandFeeDetail;
import com.hd.agent.oa.service.IOaBrandFeeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;

import java.util.List;

/**
 * 品牌费用申请单（支付）Listener
 * 生成客户应付费用
 * 
 * @author limin
 */
public class OaBrandFeeListener3 extends TaskListenerImpl {

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

		List<OaBrandFeeDetail> list = oaBrandFeeService.selectBrandFeeDetailByBillid(fee.getId());
		
		int ret = payService.addCustomerFeeByOaBrandFee(fee, list);
		addHandlerLog(task, this.getClass(), ret, fee, list);
	}

}

