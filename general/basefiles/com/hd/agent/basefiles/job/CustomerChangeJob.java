/**
 * @(#)CustomerChangeJob.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 3, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.job;

import java.util.Map;

import com.hd.agent.basefiles.service.IBaseFilesService;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class CustomerChangeJob extends BaseJob{
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
	throws JobExecutionException {
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
        IBaseFilesService baseFilesService = (IBaseFilesService)SpringContextUtils.getBean("baseFilesService");
		try {
            baseFilesService.editCustomerChangeJob(map);
			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 客户档案修改后更新相关单据失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

