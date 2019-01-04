/**
 * @(#)OweOrderSendMessageToIndooruser.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月24日 wanghongteng 创建版本
 */
package com.hd.agent.sales.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.service.IDispatchBillService;
import com.hd.agent.sales.service.IOweOrderService;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class OweOrderSendMessageToIndooruser extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		try {
			IOweOrderService oweOrderService=(IOweOrderService) SpringContextUtils.getBean("salesOweOrderService");
			oweOrderService.sendMessage();
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("定时器执行异常", e);	
		}
		super.executeInternal(jobExecutionContext);
	}

}

