package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaExpensePush;
import com.hd.agent.oa.service.IOaExpensePushService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaExpensePushDataCollectorListener extends TaskListenerImpl {

    private IOaExpensePushService oaExpensePushService;

    public IOaExpensePushService getOaExpensePushService() {
        return oaExpensePushService;
    }

    public void setOaExpensePushService(IOaExpensePushService oaExpensePushService) {
        this.oaExpensePushService = oaExpensePushService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaExpensePush push = oaExpensePushService.selectOaExpensePush(businessid);
        List list = oaExpensePushService.selectOaExpensePushDetailList(businessid);

        Map data = new HashMap();
        data.put("bill", push);
        data.put("detail", list);

        super.addDataTrace(process, data);
    }
}
