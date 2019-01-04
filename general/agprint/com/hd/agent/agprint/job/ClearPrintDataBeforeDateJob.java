package com.hd.agent.agprint.job;

import com.hd.agent.account.service.IReceiptHandService;
import com.hd.agent.agprint.service.IPrintJobService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 清除打印任务相关的打印数据
 * Created by master on 2016/7/4.
 */
public class ClearPrintDataBeforeDateJob extends BaseJob {
    protected static final Logger logger = Logger.getLogger(ClearPrintDataBeforeDateJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        try {
            IPrintJobService printJobService = (IPrintJobService) SpringContextUtils.getBean("printJobService");
            printJobService.clearPrintDataBeforeDateJob();
            flag =  true;
        } catch (Exception e) {
            logger.error("定时器执行异常", e);
        }
        super.executeInternal(jobExecutionContext);
    }
}

