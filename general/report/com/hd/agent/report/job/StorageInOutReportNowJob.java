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
 * 生成当天的进销存汇总数据
 * @author chenwei
 */
public class StorageInOutReportNowJob extends BaseJob {
	
	
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
		try {
			String date = CommonUtils.getTodayDataStr();
			storageReportService.addStorageInoutReportByDay(date);
			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 库存出入库基础数据(当天数据)失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

