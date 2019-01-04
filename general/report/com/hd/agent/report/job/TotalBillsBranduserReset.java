package com.hd.agent.report.job;

import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: PXX
 * Date: 2016/7/14
 * Time: 11:22
 * 所有单据的品牌业务员重新生成
 */
public class TotalBillsBranduserReset extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        IPersonnelService personnelService = (IPersonnelService) SpringContextUtils.getBean("personnelService");
        try{
            flag = personnelService.doTotalBillsBranduserReset();
        }catch (Exception e) {
            logger.error("定时器执行异常 所有单据的品牌业务员重新生成失败", e);
        }
        super.executeInternal(jobExecutionContext);
    }
}
