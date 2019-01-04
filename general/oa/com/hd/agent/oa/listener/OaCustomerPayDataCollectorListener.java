package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaCustomerPay;
import com.hd.agent.oa.service.IOaCustomerPayService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaCustomerPayDataCollectorListener extends TaskListenerImpl {

    private IOaCustomerPayService oaCustomerPayService;

    public IOaCustomerPayService getOaCustomerPayService() {
        return oaCustomerPayService;
    }

    public void setOaCustomerPayService(IOaCustomerPayService oaCustomerPayService) {
        this.oaCustomerPayService = oaCustomerPayService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaCustomerPay pay = oaCustomerPayService.selectCustomerPayInfo(businessid);
        List list = oaCustomerPayService.selectOaCustomerPayDetailList(pay.getId());

        Map data = new HashMap();
        data.put("bill", pay);
        data.put("detail", list);

        super.addDataTrace(process, data);
    }
}
