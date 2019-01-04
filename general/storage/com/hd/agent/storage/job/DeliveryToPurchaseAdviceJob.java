/**
 * @(#)DeliveryToPurchaseAdviceJob.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月30日 huangzhiqian 创建版本
 */
package com.hd.agent.storage.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.storage.service.DeliveryOutService;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 生成采购退货通知单,采购退货出库单
 * @author huangzhiqian
 */
public class DeliveryToPurchaseAdviceJob extends BaseJob{
	
	

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		flag=false;
		DeliveryOutService deliveryOutService = (DeliveryOutService)SpringContextUtils.getBean("deliveryOutService");
		try {
			flag= deliveryOutService.doEntersForJob();
		} catch (Exception e) {
			logger.error("定时器执行异常 代配送出库单生成采购退货通知单失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
	
}

