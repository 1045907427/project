package com.hd.agent.report.job;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.report.service.IStorageReportService;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 添加商品库存每天记录表
 * 该任务需要在凌晨24：00后执行,执行时间需安排在每日进销存定时器后面
 * User: PXX
 * Date: 2016/2/19
 */
public class ReportStorageBakJob extends BaseJob{
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
        try {
            String yesdate = CommonUtils.getYestodayDateStr();
            flag = storageReportService.addReportStorageBakEveryday(yesdate);
        } catch (Exception e) {
            logger.error("定时器执行异常 添加商品库存每天记录表失败", e);
        }
        super.executeInternal(jobExecutionContext);
    }
}
