/**
 * @(#)StorageNumSaveMonthJob.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 16, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.report.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.report.service.IStorageReportService;
import com.hd.agent.system.job.BaseJob;

/**
 * 每天记录当月库存总量
 * 该任务需要在凌晨24：00后执行
 * 记录上一个月的库存 日期为昨天
 * @author panxiaoxiao
 */
public class StorageNumSaveMonthJob extends BaseJob{

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
		try {
			String date = CommonUtils.getYestodayDateStr();
			flag = storageReportService.addStorageNumEveryMonth(date);
			
			//统计上月库存数据
//			String lastmonthday = CommonUtils.getPreMonthLastDay();
//			flag = storageReportService.addStorageNumEveryMonth(lastmonthday);
			super.executeInternal(jobExecutionContext);
		} catch (Exception e) {
			logger.error("定时器执行异常 每月进销存数据生成失败", e);
		}
	}
}

