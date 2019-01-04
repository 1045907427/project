package com.hd.agent.account.job;

import com.hd.agent.account.service.ISalesInvoiceBillService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 关闭来源单据全部核销了的申请开票单据，且核销状态为已核销
 * 将销售核销中来源单据全部开票的单据开票状态变为已开票
 * @author panxiaoxiao
 */
public class CloseWriteoffSourcesInvoicebillJob extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        ISalesInvoiceBillService salesInvoiceBillService = (ISalesInvoiceBillService) SpringContextUtils.getBean("salesInvoiceBillService");
        try {
            salesInvoiceBillService.doCloseWriteoffSourcesInvoicebill();

            flag = true;
        } catch (Exception e) {
            logger.error("定时器执行异常 全局关闭申请开票更新销售开票开票状态", e);
        }
        super.executeInternal(jobExecutionContext);
    }
}
