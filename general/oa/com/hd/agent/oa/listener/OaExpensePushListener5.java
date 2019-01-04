/**
 * @(#)OaExpensePushListener5.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2017-4-24 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.oa.model.OaExpensePush;
import com.hd.agent.oa.model.OaExpensePushDetail;
import com.hd.agent.oa.service.IOaExpensePushService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by limin on 2017/4/24.
 */
public class OaExpensePushListener5 extends TaskListenerImpl {

    private IOaExpensePushService oaExpensePushService;

    public void setWorkService(IWorkService workService) {
        this.workService = workService;
    }

    public IOaExpensePushService getOaExpensePushService() {
        return oaExpensePushService;
    }

    public void setOaExpensePushService(IOaExpensePushService oaExpensePushService) {
        this.oaExpensePushService = oaExpensePushService;
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

        OaExpensePush push = oaExpensePushService.selectOaExpensePush(businessid);
        List<OaExpensePushDetail> list = oaExpensePushService.selectOaExpensePushDetailList(businessid);

        if(push == null || list == null || list.size() == 0) {

            return ;
        }

        int ret = businessService.addCustomerPushBanlanceByOaExpensePush(push, list, "3");
        addHandlerLog(task, this.getClass(), ret, push, list);
    }
}
