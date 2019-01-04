package com.hd.agent.common.job;

import com.hd.agent.basefiles.service.IBaseFilesService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Map;

/**
 * Created by PXX on 2015/7/1.
 */
public class AttachOneConvertHtmlJob extends BaseJob implements StatefulJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        Map map =  (Map) jobDetail.getJobDataMap().get("dataMap");
        IBaseFilesService baseFilesService = (IBaseFilesService) SpringContextUtils.getBean("baseFilesService");
        try {
            baseFilesService.editAttachOneConvertHtmlJob(map);

            flag = true;
        } catch (Exception ex) {
            logger.error("定时器执行异常 文件上传格式转换html(按单次计划)失败", ex);
            flag=false;
        }
        super.executeInternal(jobExecutionContext);
    }
}
