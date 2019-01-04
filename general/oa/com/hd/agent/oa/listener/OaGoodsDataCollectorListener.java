package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaGoods;
import com.hd.agent.oa.service.IOaGoodsService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limin on 2016/4/20.
 */
public class OaGoodsDataCollectorListener extends TaskListenerImpl {

    private IOaGoodsService oaGoodsService;

    public IOaGoodsService getOaGoodsService() {
        return oaGoodsService;
    }

    public void setOaGoodsService(IOaGoodsService oaGoodsService) {
        this.oaGoodsService = oaGoodsService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaGoods goods = oaGoodsService.selectOaGoods(businessid);

        PageMap pageMap = new PageMap(1, 9999);
        Map condition = new HashMap();
        condition.put("billid", goods.getId());
        pageMap.setCondition(condition);

        List list = oaGoodsService.selectGoodsDetailList(pageMap).getList();

        Map data = new HashMap();
        data.put("bill", goods);
        data.put("detail", list);
        super.addDataTrace(process, data);
    }
}
