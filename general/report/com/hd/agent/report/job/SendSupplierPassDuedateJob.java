package com.hd.agent.report.job;

import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.report.service.IFinanceFundsReturnService;
import com.hd.agent.system.job.BaseJob;
import com.hd.agent.system.model.TaskSchedule;
import com.hd.agent.system.service.ITaskScheduleService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by luoq on 2017/10/30.
 */
public class SendSupplierPassDuedateJob extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        ITaskScheduleService taskScheduleService = (ITaskScheduleService) SpringContextUtils.getBean("taskScheduleService");
        IFinanceFundsReturnService financeFundsReturnService = (IFinanceFundsReturnService) SpringContextUtils.getBean("financeFundsReturnService");
//        taskScheduleService.showTaskSchedule()
        try {
//            List<PaymentVoucher> paymentVouchers = paymentVoucherService.getPaymentVoucherListByAudit();
            String taskid=jobExecutionContext.getJobDetail().getName();
            String group=jobExecutionContext.getJobDetail().getGroup();
            TaskSchedule taskSchedule=taskScheduleService.showTaskSchedule(taskid, group);
            financeFundsReturnService.sendSupplierPassDudData(taskSchedule);
        } catch (Exception e) {
            logger.error("定时器执行异常 向指定用户发送代垫应收超账期情况", e);
        }
        super.executeInternal(jobExecutionContext);
    }

}
