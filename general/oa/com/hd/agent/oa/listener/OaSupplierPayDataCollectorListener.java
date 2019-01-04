package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaSupplierPay;
import com.hd.agent.oa.service.IOaSupplierPayService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaSupplierPayDataCollectorListener extends TaskListenerImpl {

    private IOaSupplierPayService oaSupplierPayService;

    public IOaSupplierPayService getOaSupplierPayService() {
        return oaSupplierPayService;
    }

    public void setOaSupplierPayService(IOaSupplierPayService oaSupplierPayService) {
        this.oaSupplierPayService = oaSupplierPayService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaSupplierPay pay = oaSupplierPayService.selectSupplierPay(businessid);

        Map data = new HashMap();
        data.put("bill", pay);

        super.addDataTrace(process, data);
    }
}
