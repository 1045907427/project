/**
 * @(#)GoodsChangeJob.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 4, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.job;

import com.hd.agent.basefiles.service.IBaseFilesService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class GoodsChangeJob extends BaseJob{
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
	throws JobExecutionException {
        IBaseFilesService baseFilesService = (IBaseFilesService)SpringContextUtils.getBean("baseFilesService");
		try {
            baseFilesService.editGoodsChangeJob();

			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 品牌/商品档案发生变更时更新相关单据数据失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

