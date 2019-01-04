package com.hd.agent.report.job;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.report.service.IStorageReportService;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by lin_xx on 2016/8/10.
 */
public class SaleOutDetailAndDiscountDealJob extends BaseJob {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
        try {
             flag = storageReportService.addSaleoutDetailWithBrandDiscount();
        } catch (Exception e) {
            logger.error("定时器执行异常 （吉马）业务员发货报表旧数据生成失败", e);
        }

        super.executeInternal(jobExecutionContext);
    }


}
