/**
 * @(#)OaDelegateListener1.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-22 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaDelegate;
import com.hd.agent.oa.service.IOaDelegateService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * 工作委托规则Listener
 *   生成工作委托规则
 *
 * @author limin
 */
public class OaDelegateListener1 extends TaskListenerImpl {

    private IOaDelegateService oaDelegateService;

    public IOaDelegateService getOaDelegateService() {
        return oaDelegateService;
    }

    public void setOaDelegateService(IOaDelegateService oaDelegateService) {
        this.oaDelegateService = oaDelegateService;
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

        OaDelegate delegate = oaDelegateService.selectOaDelegate(businessid);
        int ret = businessService.addDelegateByOaDelegate(delegate);
        super.addHandlerLog(task, this.getClass(), ret, delegate);
    }

}
