package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaOffPrice;
import com.hd.agent.oa.service.IOaOffPriceService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaOffPriceDataCollectorListener extends TaskListenerImpl {

    private IOaOffPriceService oaOffPriceService;

    public IOaOffPriceService getOaOffPriceService() {
        return oaOffPriceService;
    }

    public void setOaOffPriceService(IOaOffPriceService oaOffPriceService) {
        this.oaOffPriceService = oaOffPriceService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaOffPrice offPrice = oaOffPriceService.selectOaOffPrice(businessid);
        List list = oaOffPriceService.selectOaOffPriceDetailListByBillid(businessid);

        Map data = new HashMap();
        data.put("bill", offPrice);
        data.put("detail", list);

        super.addDataTrace(process, data);
    }
}
