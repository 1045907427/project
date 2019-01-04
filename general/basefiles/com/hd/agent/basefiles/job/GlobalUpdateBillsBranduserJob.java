package com.hd.agent.basefiles.job;

import com.hd.agent.basefiles.service.IBaseFilesService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 *
 * 全局更新单据品牌业务员定时器
 * @author panxiaoxiao
 */
public class GlobalUpdateBillsBranduserJob extends BaseJob{
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        IBaseFilesService baseFilesService = (IBaseFilesService)SpringContextUtils.getBean("baseFilesService");
        try {
            baseFilesService.doGlobalUpdateBillsBranduser();

            flag = true;
        } catch (Exception e) {
            logger.error("定时器执行异常 全局更新单据品牌/厂家业务员失败", e);
        }
        super.executeInternal(jobExecutionContext);
    }
}
