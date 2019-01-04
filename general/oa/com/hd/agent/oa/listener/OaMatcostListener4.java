/**
 * @(#)OaMatcostListener4.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2018-2-13 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaMatcost;
import com.hd.agent.oa.service.IOaMatcostService;
import com.hd.agent.oa.service.IOaPayService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 代垫费用申请单回滚客户费用借方
 */
public class OaMatcostListener4 extends TaskListenerImpl {

    private IOaMatcostService oaMatcostService;

    private IOaPayService payService;

    public IOaMatcostService getOaMatcostService() {
        return oaMatcostService;
    }

    public void setOaMatcostService(IOaMatcostService oaMatcostService) {
        this.oaMatcostService = oaMatcostService;
    }

    @Override
    public IOaPayService getPayService() {
        return payService;
    }

    @Override
    public void setPayService(IOaPayService payService) {
        this.payService = payService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        String instanceid = task.getExecution().getProcessInstanceId();

        if(StringUtils.isEmpty(instanceid)) {

            return ;
        }

        Process process = workService.getProcess(instanceid, "2");
        if(process == null) {

            return ;
        }

        String businessid = process.getBusinessid();
        if(StringUtils.isEmpty(businessid)) {

            return ;
        }

        OaMatcost matcost = oaMatcostService.getOaMatcost(businessid);
        List list = oaMatcostService.getOaMatcostDetailListByBillid(matcost.getId());

        int ret = payService.rollbackCustomerFeeByOaMatcost(matcost, list, "1");
        addHandlerLog(task, this.getClass(), ret, matcost, list, "1");
    }

}
