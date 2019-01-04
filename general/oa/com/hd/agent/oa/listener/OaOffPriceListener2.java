/**
 * @(#)OaOffPriceListener1.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-3-10 limin 创建版本
 */
package com.hd.agent.oa.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaOffPrice;
import com.hd.agent.oa.service.IOaOffPriceService;

import java.util.Map;

/**
 * 批量特价申请单Listener
 * 
 * @author limin
 */
public class OaOffPriceListener2 extends TaskListenerImpl {

    private IOaOffPriceService oaOffPriceService;

    public IOaOffPriceService getOaOffPriceService() {
        return oaOffPriceService;
    }

    public void setOaOffPriceService(IOaOffPriceService oaOffPriceService) {
        this.oaOffPriceService = oaOffPriceService;
    }

    @Override
	public void notify(DelegateTask task) throws Exception {

        String instanceid = task.getExecution().getProcessInstanceId();

        Process process = workService.getProcess(instanceid, "2");
        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return ;
        }

        String businessid = process.getBusinessid();
        if(StringUtils.isEmpty(businessid)) {

            return ;
        }

        OaOffPrice price = oaOffPriceService.selectOaOffPrice(businessid);

        if(price == null || StringUtils.isEmpty(price.getOaid())) {

            return ;
        }

        Map map = businessService.rollbackOffPriceByOffPrice(price);
        addHandlerLog(task, this.getClass(), map, price);
	}
}

