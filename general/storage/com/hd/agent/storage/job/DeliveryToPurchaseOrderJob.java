/**
 * @(#)JobTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月21日 huangzhiqian 创建版本
 */
package com.hd.agent.storage.job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.storage.service.IDistributeRejectService;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 定时任务  生成采购入库单,采购进货单
 * @author huangzhiqian
 */
public class DeliveryToPurchaseOrderJob extends BaseJob{

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		flag=false;
		IDistributeRejectService distributeRejectService = (IDistributeRejectService)SpringContextUtils.getBean("distributeRejectService");

		try {
			flag=distributeRejectService.doEntersForJob();
		} catch (Exception e) {
			logger.error("定时器执行异常 代配送入库单生成采购入库单失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
	
	
	
	
	
	
}

