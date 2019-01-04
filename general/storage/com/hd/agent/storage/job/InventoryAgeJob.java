package com.hd.agent.storage.job;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.storage.service.IStorageSummaryService;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 库龄报表生成
 * Created by chenwei on 2017-03-20.
 */
public class InventoryAgeJob extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        IStorageSummaryService storageSummaryService = (IStorageSummaryService) SpringContextUtils.getBean("storageSummaryService");
        try {
            //清理之前的库龄报表数据 防止数据量过多 造成报表查询过慢
            storageSummaryService.deleteInventoryAgeIndays();
            flag=storageSummaryService.addInventoryAge(null);
        } catch (Exception e) {
            logger.error("定时器执行异常 库龄报表生成失败", e);
        }
        flag = true;
        super.executeInternal(jobExecutionContext);
    }
}
