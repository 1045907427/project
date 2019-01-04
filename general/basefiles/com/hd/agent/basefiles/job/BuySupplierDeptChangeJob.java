package com.hd.agent.basefiles.job;

import com.hd.agent.basefiles.service.IBaseFilesService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: PXX
 * Date: 2016/11/15
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class BuySupplierDeptChangeJob extends BaseJob {
    @Override
    protected synchronized void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        IBaseFilesService baseFilesService = (IBaseFilesService)SpringContextUtils.getBean("baseFilesService");
        try {
            baseFilesService.doBuySupplierDeptChangeJob();
            flag = true;
        } catch (Exception e) {
            logger.error("定时器执行异常 根据供应商档案的采购部门调整代垫收回和代垫录入、付款单中的所属部门失败", e);
        }
        super.executeInternal(jobExecutionContext);
    }
}
