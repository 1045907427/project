/**
 * @(#)OaDailPayListener2.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-10 limin 创建版本
 */
package com.hd.agent.oa.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaDailyPay;
import com.hd.agent.oa.model.OaDailyPayDetail;
import com.hd.agent.oa.service.IOaDailPayService;

/**
 * 日常费用支付申请单Listener
 * 
 * @author limin
 */
public class OaDailPayListener2 extends TaskListenerImpl {

	private IOaDailPayService oaDailPayService;

	public IOaDailPayService getOaDailPayService() {
		return oaDailPayService;
	}

	public void setOaDailPayService(IOaDailPayService oaDailPayService) {
		this.oaDailPayService = oaDailPayService;
	}

	@Override
	public void notify(DelegateTask task) throws Exception {

		String instanceid = task.getExecution().getProcessInstanceId();
		
		Process process = workService.getProcess(instanceid, "2");
		if(process == null || StringUtils.isEmpty(process.getBusinessid())) {
			
			return ;
		}
		
		OaDailyPay pay = oaDailPayService.selectOaDailPay(process.getBusinessid());		
		if(pay == null) {
			
			return ;
		}
		
		List<OaDailyPayDetail> list = oaDailPayService.selectOaDailPayDetailListByBillid(pay.getId());
		
		payService.rollbackDeptDailCostByDailyPay(pay, list);
	}
}

