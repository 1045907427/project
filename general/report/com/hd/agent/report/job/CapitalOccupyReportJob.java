/**
 * @(#)CapitalOccupyReportJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 3, 2013 chenwei 创建版本
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
 * 每日资金占用报表生成
 * @author chenwei
 */
public class CapitalOccupyReportJob extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
		try {
			String date = CommonUtils.getYestodayDateStr();
			flag = storageReportService.addCapitalOccupyReport(date);
		} catch (Exception e) {
			logger.error("定时器执行异常 资金占用报表生成失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

