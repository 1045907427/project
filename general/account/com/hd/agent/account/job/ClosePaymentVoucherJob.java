package com.hd.agent.account.job;

import com.hd.agent.account.model.PaymentVoucher;
import com.hd.agent.account.service.IPaymentVoucherService;
import com.hd.agent.account.service.ISalesInvoiceBillService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by lin_xx on 2016/8/2.
 */
public class ClosePaymentVoucherJob extends BaseJob {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        IPaymentVoucherService paymentVoucherService = (IPaymentVoucherService) SpringContextUtils.getBean("paymentVoucherService");
        try {
            List<PaymentVoucher> paymentVouchers = paymentVoucherService.getPaymentVoucherListByAudit();
            flag = paymentVoucherService.updateOrderByStatus(paymentVouchers);
        } catch (Exception e) {
            logger.error("定时器执行异常 关闭明细（付款单）已审核的交款单", e);
        }
        super.executeInternal(jobExecutionContext);
    }

}
