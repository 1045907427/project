/**
 * @(#)StorageInOutReportJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 14, 2013 chenwei 创建版本
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
 * 记录每天库存总量.
 * 该任务需要在凌晨24：00后执行
 * 记录当前的库存 日期为昨天
 * @author chenwei
 */
public class StorageNumSaveJob extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
		try {
            String yesdate = CommonUtils.getYestodayDateStr();
            flag = storageReportService.addStorageNumEveryday(yesdate);
		} catch (Exception e) {
			logger.error("定时器执行异常 每日进销存数据生成失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

