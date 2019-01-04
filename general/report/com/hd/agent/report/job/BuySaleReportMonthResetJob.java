package com.hd.agent.report.job;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.report.service.IStorageReportService;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * 每月进销存数据截断重新生成
 * @author panxiaoxiao
 */
public class BuySaleReportMonthResetJob extends BaseJob{
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        IStorageReportService storageReportService = (IStorageReportService) SpringContextUtils.getBean("storageReportService");
        try {
            String today = CommonUtils.getTodayDataStr();
            flag = storageReportService.clearResetBuySaleReportMonthData(today);

        } catch (Exception e) {
            logger.error("定时器执行异常 每月进销存数据清空重新生成失败", e);
        }
        super.executeInternal(jobExecutionContext);
    }
}
