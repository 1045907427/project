/**
 * @(#)CloseReceiptHandJob.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 8, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.common.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.account.service.IReceiptHandService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;

/**
 * 每天关闭单据明细中所有回单已关闭的审核通过的交接单
 * 
 * @author panxiaoxiao
 */
public class CloseReceiptHandJob extends BaseJob{

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
	throws JobExecutionException {
		try {
			IReceiptHandService receiptHandService = (IReceiptHandService)SpringContextUtils.getBean("receiptHandService");
			receiptHandService.closeAllReceiptClosedOfReceiptHandList();
			flag =  true;
		} catch (Exception e) {
			logger.error("定时器执行异常", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

