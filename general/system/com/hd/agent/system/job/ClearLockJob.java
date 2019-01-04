/**
 * @(#)ClearLockJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 19, 2013 chenwei 创建版本
 */
package com.hd.agent.system.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.service.INetLockService;

/**
 * 
 * 网络互斥自动解锁
 * @author chenwei
 */
public class ClearLockJob extends BaseJob {
	
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		INetLockService netLockService = (INetLockService) SpringContextUtils.getBean("netLockService");
		try {
			netLockService.clearLock();
			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

