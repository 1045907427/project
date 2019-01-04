package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaBrandFee;
import com.hd.agent.oa.service.IOaBrandFeeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaBrandFeeDataCollectorListener extends TaskListenerImpl {

    private IOaBrandFeeService oaBrandFeeService;

    public IOaBrandFeeService getOaBrandFeeService() {
        return oaBrandFeeService;
    }

    public void setOaBrandFeeService(IOaBrandFeeService oaBrandFeeService) {
        this.oaBrandFeeService = oaBrandFeeService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaBrandFee fee = oaBrandFeeService.selectOaBrandFee(businessid);
        List list = oaBrandFeeService.selectBrandFeeDetailByBillid(businessid);

        Map data = new HashMap();
        data.put("bill", fee);
        data.put("detail", list);
        super.addDataTrace(process, data);
    }
}
