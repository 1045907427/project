package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaGoodsPrice;
import com.hd.agent.oa.service.IOaGoodsPriceService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaGoodsPriceDataCollectorListener extends TaskListenerImpl {

    private IOaGoodsPriceService oaGoodsPriceService;

    public IOaGoodsPriceService getOaGoodsPriceService() {
        return oaGoodsPriceService;
    }

    public void setOaGoodsPriceService(IOaGoodsPriceService oaGoodsPriceService) {
        this.oaGoodsPriceService = oaGoodsPriceService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaGoodsPrice oaGoodsPrice = oaGoodsPriceService.selectOaGoodsPrice(businessid);
        List list = oaGoodsPriceService.selectOaGoodsPriceDetailListByBillid(businessid);

        Map data = new HashMap();
        data.put("bill", oaGoodsPrice);
        data.put("detail", list);

        super.addDataTrace(process, data);
    }
}
