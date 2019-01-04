package com.hd.agent.sales.job;

import com.hd.agent.basefiles.service.IBaseFilesService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.sales.dao.OffpriceMapper;
import com.hd.agent.sales.dao.PromotionPackageMapper;
import com.hd.agent.sales.model.Offprice;
import com.hd.agent.sales.model.PromotionPackage;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.List;

/**
 * Created by wanghongteng on 2017/6/6.
 */
public class updateOrderstatusJob extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        IOrderService salesOrderService = (IOrderService)SpringContextUtils.getBean("salesOrderService");
        try {
            salesOrderService.doUpdateOrderStatus();
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("定时器执行异常 自动修改订单的打印及配送状态", e);
        }
        super.executeInternal(jobExecutionContext);
    }


}
