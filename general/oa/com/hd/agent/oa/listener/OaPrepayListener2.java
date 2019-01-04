package com.hd.agent.oa.listener;

import com.hd.agent.oa.model.OaPersonalLoan;
import com.hd.agent.oa.service.IOaPersonalLoanService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by limin on 2015/4/8.
 */
public class OaPrepayListener2 extends TaskListenerImpl {

    private IOaPersonalLoanService oaPersonalLoanService;

    public IOaPersonalLoanService getOaPersonalLoanService() {
        return oaPersonalLoanService;
    }

    public void setOaPersonalLoanService(IOaPersonalLoanService oaPersonalLoanService) {
        this.oaPersonalLoanService = oaPersonalLoanService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        String instanceid = task.getExecution().getProcessInstanceId();

        com.hd.agent.activiti.model.Process process = workService.getProcess(instanceid, "2");
        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return ;
        }

        OaPersonalLoan prepay = oaPersonalLoanService.selectOaPersonalLoanInfo(process.getBusinessid());

        if(prepay == null) {

            return ;
        }

        payService.rollbackBankBillByOaPersonalLoan(prepay);
    }
}
