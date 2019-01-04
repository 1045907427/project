/**
 * @(#)OaDelegateDataCollectorListener.java
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

import java.util.HashMap;
import java.util.Map;

/**
 * 工作委托规则Data Collector
 *
 * @author limin
 */
public class OaDelegateDataCollectorListener extends TaskListenerImpl {

    private IOaDelegateService oaDelegateService;

    public IOaDelegateService getOaDelegateService() {
        return oaDelegateService;
    }

    public void setOaDelegateService(IOaDelegateService oaDelegateService) {
        this.oaDelegateService = oaDelegateService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaDelegate delegate = oaDelegateService.selectOaDelegate(businessid);

        Map data = new HashMap();
        data.put("bill", delegate);
        super.addDataTrace(process, data);
    }
}
