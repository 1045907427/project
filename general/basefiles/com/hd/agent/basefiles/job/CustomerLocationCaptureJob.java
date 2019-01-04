/**
 * @(#)CustomerLocationCaptureJob.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 8, 2016 limin 创建版本
 */
package com.hd.agent.basefiles.job;

import com.hd.agent.basefiles.model.CustomerLocation;
import com.hd.agent.basefiles.service.ISalesService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author limin
 */
public class CustomerLocationCaptureJob extends BaseJob{

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		JobDetail jobDetail = jobExecutionContext.getJobDetail();
//		Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
		ISalesService salesService = (ISalesService)SpringContextUtils.getBean("salesService");

		try {

			List<Map> customers = salesService.selectUnlocatedCustomerList();

			for(Map customer : customers) {

				CustomerLocation newLocation = new CustomerLocation();
				newLocation.setCustomerid((String) customer.get("id"));
				newLocation.setAddress(null);
				newLocation.setAccuracy("1");
				newLocation.setLocation((String) customer.get("location"));
				newLocation.setRemark("客户拜访坐标");
				salesService.editCustomerLocation(newLocation);
			}

			flag = true;
		} catch (Exception e) {
			logger.error("定时器执行异常 客户档案修改后更新相关单据失败", e);
		}
		super.executeInternal(jobExecutionContext);
	}
}

