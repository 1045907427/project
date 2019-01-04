/**
 * @(#)StorageRevolutionDaysJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 27, 2013 chenwei 创建版本
 */
package com.hd.agent.report.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.report.service.IStorageReportService;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 库存周转天数报表
 * @author chenwei
 */
public class StorageRevolutionDaysJob extends BaseJob{
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
		try {
			String date = CommonUtils.getYestodayDateStr();
			flag = storageReportService.addStorageNumEveryday(date);
		} catch (Exception e) {
			logger.error("定时器执行异常 库存周转天数报表生成失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

