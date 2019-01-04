/**
 * @(#)CustomerChageSettletypeJob.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 22, 2014 chenwei 创建版本
 */
package com.hd.agent.storage.job;

import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.storage.service.IStorageSaleOutService;
import com.hd.agent.system.job.BaseJob;

/**
 * 客户结算方式变更后，同时更新未核销的发货单，回单的应收日期
 * @author chenwei
 */
public class CustomerChageSettletypeJob extends BaseJob{
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
	throws JobExecutionException {
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
		IStorageSaleOutService storageSaleOutService = (IStorageSaleOutService)SpringContextUtils.getBean("storageSaleOutService");
		try {
			String customerid = (String) map.get("customerid");
			if(null!=customerid){
				flag = storageSaleOutService.updateCustomerSaleOutNoWriteoffDuefromdateByCustomerid(customerid);
			}
		} catch (Exception e) {
			logger.error("定时器执行异常 客户档案修改后更新相关单据失败", e);
			throw new JobExecutionException("定时器执行异常");
		}
		super.executeInternal(jobExecutionContext);
	}
}

