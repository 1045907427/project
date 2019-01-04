/**
 * @(#)LimitPriceOrderStatusCloseJob.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-6-22 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.purchase.model.LimitPriceOrder;
import com.hd.agent.purchase.service.ILimitPriceOrderService;
import com.hd.agent.system.job.BaseJob;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class LimitPriceOrderEndDateCloseJob  extends BaseJob {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext)
			throws JobExecutionException {
		try{
			ILimitPriceOrderService limitPriceOrderService=(ILimitPriceOrderService) SpringContextUtils.getBean("limitPriceOrderService");
			List<LimitPriceOrder> list=limitPriceOrderService.getLimitPriceOrderUnEffectList();

			if(null!=list && list.size()>0){
				LimitPriceOrder upOrder=null;
				for(LimitPriceOrder item : list){
					logger.info(item.getId());
					if("3".equals(item.getStatus())){
						upOrder=new LimitPriceOrder();
						upOrder.setId(item.getId());
						upOrder.setStatus("4");
						limitPriceOrderService.updateLimitPriceOrderStatus(upOrder);
					}
				}
			}
			flag = true;
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("定时器执行异常", e);	
		}
		super.executeInternal(jobExecutionContext);
	}

}

