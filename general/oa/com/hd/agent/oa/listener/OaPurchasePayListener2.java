/**
 * @(#)OaPurchasePayListener2.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-6 limin 创建版本
 */
package com.hd.agent.oa.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaPurchasePay;
import com.hd.agent.oa.service.IOaPayService;
import com.hd.agent.oa.service.IOaPurchasePayService;

/**
 * 行政采购付款申请单Listener
 *
 * @author limin
 */
public class OaPurchasePayListener2 extends TaskListenerImpl {

    private IOaPurchasePayService oaPurchasePayService;

    public IOaPayService getPayService() {
        return payService;
    }

    public void setPayService(IOaPayService payService) {
        this.payService = payService;
    }

    public IOaPurchasePayService getOaPurchasePayService() {
        return oaPurchasePayService;
    }

    public void setOaPurchasePayService(IOaPurchasePayService oaPurchasePayService) {
        this.oaPurchasePayService = oaPurchasePayService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        String instanceid = task.getExecution().getProcessInstanceId();

        Process process = workService.getProcess(instanceid, "2");
        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return ;
        }

        OaPurchasePay pay = oaPurchasePayService.selectPurchasePay(process.getBusinessid());
        if(pay == null) {

            return ;
        }

        int ret = payService.rollbackBankBillByOaPurchasePay(pay);
        addHandlerLog(task, this.getClass(), ret, pay);
    }

}

