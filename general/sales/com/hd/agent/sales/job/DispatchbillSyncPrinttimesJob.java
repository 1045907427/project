/**
 * @(#)DispatchbillSyncPrinttimesJob.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-1-14 zhanghonghui 创建版本
 */
package com.hd.agent.sales.job;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.service.IDispatchBillService;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时器 调用 根据仓库发货单发货打印次数同步发货打印次数
 * 
 * @author zhanghonghui
 */
public class DispatchbillSyncPrinttimesJob extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		try {
			IDispatchBillService dispatchBillService=(IDispatchBillService) SpringContextUtils.getBean("salesDispatchBillService");
			dispatchBillService.updateBillSyncPrinttimesProc();
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("定时器执行异常", e);	
		}
		super.executeInternal(jobExecutionContext);
	}

}

