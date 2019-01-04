package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaMatcost;
import com.hd.agent.oa.service.IOaMatcostService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OaMatcostDataCollectorListener extends TaskListenerImpl {

    private IOaMatcostService oaMatcostService;

    public IOaMatcostService getOaMatcostService() {
        return oaMatcostService;
    }

    public void setOaMatcostService(IOaMatcostService oaMatcostService) {
        this.oaMatcostService = oaMatcostService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaMatcost matcost = oaMatcostService.getOaMatcost(businessid);
        List list = oaMatcostService.getOaMatcostDetailListByBillid(businessid);

        Map data = new HashMap();
        data.put("bill", matcost);
        data.put("detail", list);

        super.addDataTrace(process, data);
    }
}
