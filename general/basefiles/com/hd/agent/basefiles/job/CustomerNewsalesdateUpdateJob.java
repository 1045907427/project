/**
 * @(#)CustomerNewsalesdateUpdateJob.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 21, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.job;

import com.hd.agent.basefiles.service.IBaseFilesService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.basefiles.dao.CustomerMapper;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 
 * @author zhengziyong
 */
public class CustomerNewsalesdateUpdateJob extends BaseJob {

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		IBaseFilesService baseFilesService = (IBaseFilesService)SpringContextUtils.getBean("baseFilesService");
		try {
			baseFilesService.doAutoUpdateCustomerNewsalesdate();
			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 客户最新销售日期更新失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}

}

