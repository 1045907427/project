/**
 * @(#)OaMatcostListener2.java
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
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 代垫费用申请单回滚代垫费用（取工厂投入的合计金额）
 */
public class OaMatcostListener2 extends TaskListenerImpl {

    private IOaMatcostService oaMatcostService;

    public IOaMatcostService getOaMatcostService() {
        return oaMatcostService;
    }

    public void setOaMatcostService(IOaMatcostService oaMatcostService) {
        this.oaMatcostService = oaMatcostService;
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

        int ret = payService.rollbackMatcostByOaMatcost(matcost, list);
        addHandlerLog(task, this.getClass(), ret, matcost, list);
    }

}
