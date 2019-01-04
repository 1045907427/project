package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaCustomer;
import com.hd.agent.oa.service.IOaCustomerService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaCustomerDataCollectorListener extends TaskListenerImpl {

    private IOaCustomerService oaCustomerService;

    public IOaCustomerService getOaCustomerService() {
        return oaCustomerService;
    }

    public void setOaCustomerService(IOaCustomerService oaCustomerService) {
        this.oaCustomerService = oaCustomerService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaCustomer customer = oaCustomerService.selectOaCustomer(businessid);


        PageMap pageMap = new PageMap(1, 9999);
        Map condition = new HashMap();
        condition.put("billid", customer.getId());
        pageMap.setCondition(condition);

        List list = oaCustomerService.selectCustomerBrandList(pageMap).getList();

        Map data = new HashMap();
        data.put("bill", customer);
        data.put("detail", list);
        super.addDataTrace(process, data);
    }
}
