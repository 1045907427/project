/**
 * @(#)SysLogJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 27, 2013 chenwei 创建版本
 */
package com.hd.agent.common.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.service.ISysLogService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 系统日志清理任务
 * @author chenwei
 */
public class SysLogJob extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		ISysLogService sysLogService = (ISysLogService) SpringContextUtils.getBean("sysLogService");
		try {
			flag = sysLogService.clearSysLog();
		} catch (Exception e) {
			logger.error("定时器执行异常 系统日志清理任务", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

