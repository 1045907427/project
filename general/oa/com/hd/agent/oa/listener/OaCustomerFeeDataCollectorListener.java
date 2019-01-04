package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaCustomerFee;
import com.hd.agent.oa.service.IOaCustomerFeeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaCustomerFeeDataCollectorListener extends TaskListenerImpl {

    private IOaCustomerFeeService oaCustomerFeeService;

    public IOaCustomerFeeService getOaCustomerFeeService() {
        return oaCustomerFeeService;
    }

    public void setOaCustomerFeeService(IOaCustomerFeeService oaCustomerFeeService) {
        this.oaCustomerFeeService = oaCustomerFeeService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(businessid);
        List list = oaCustomerFeeService.getCustomerFeeDetailListByBillid(businessid);

        Map data = new HashMap();
        data.put("bill", fee);
        data.put("detail", list);
        super.addDataTrace(process, data);
    }
}
