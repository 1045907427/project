/**
 * @(#)CustomerChagePricesortJob.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 31, 2014 panxiaoxiao 创建版本
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
public class CustomerChangePricesortJob extends BaseJob{
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
	throws JobExecutionException {
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
        IBaseFilesService baseFilesService = (IBaseFilesService)SpringContextUtils.getBean("baseFilesService");
		try {
            baseFilesService.editCustomerChangePricesortJob(map);

			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 客户档案默认价格套修改后更新合同商品价格套价格失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

