/**
 * @(#)StorageRevolutionDaysByMonthJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 28, 2013 chenwei 创建版本
 */
package com.hd.agent.report.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.report.service.IStorageReportService;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 库存周转天数月报表统计
 * @author chenwei
 */
public class StorageRevolutionDaysByMonthJob extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
		try {
			flag = storageReportService.addStorageRevolutionDaysReport();
		} catch (Exception e) {
			logger.error("定时器执行异常 库存周转天数月报表统计生成失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

