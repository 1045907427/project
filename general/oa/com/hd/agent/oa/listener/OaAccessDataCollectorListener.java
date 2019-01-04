package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.service.IOaAccessService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/19.
 */
public class OaAccessDataCollectorListener extends TaskListenerImpl {

    private IOaAccessService oaAccessService;

    public IOaAccessService getOaAccessService() {
        return oaAccessService;
    }

    public void setOaAccessService(IOaAccessService oaAccessService) {
        this.oaAccessService = oaAccessService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaAccess access = oaAccessService.selectOaAccessInfo(businessid);

        PageMap pageMap = new PageMap(1, 9999);
        Map condition = new HashMap();
        condition.put("billid", access.getId());
        pageMap.setCondition(condition);

        List prices = oaAccessService.selectOaAccessGoodsPriceList(pageMap).getList();
        List amounts = oaAccessService.selectOaAccessGoodsAmountList(pageMap).getList();

        Map data = new HashMap();
        data.put("bill", access);
        data.put("detail1", prices);
        data.put("detail2", amounts);
        super.addDataTrace(process, data);
    }
}
