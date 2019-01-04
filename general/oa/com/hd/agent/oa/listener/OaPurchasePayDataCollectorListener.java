/**
 * @(#)OaPurchasePayDataCollectorListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-9-14 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.oa.model.OaPurchasePay;
import com.hd.agent.oa.service.IOaPurchasePayService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.impl.TaskListenerImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 行政采购付款申请单Data Collector
 *
 * @author limin
 */
public class OaPurchasePayDataCollectorListener extends TaskListenerImpl {

    private IOaPurchasePayService oaPurchasePayService;

    public IOaPurchasePayService getOaPurchasePayService() {
        return oaPurchasePayService;
    }

    public void setOaPurchasePayService(IOaPurchasePayService oaPurchasePayService) {
        this.oaPurchasePayService = oaPurchasePayService;
    }

    @Override
    public void notify(DelegateTask task) throws Exception {

        Process process = getProcess(task);

        if(process == null) {
            return ;
        }

        String businessid = process.getBusinessid();

        OaPurchasePay pay = oaPurchasePayService.selectPurchasePay(businessid);

        Map data = new HashMap();
        data.put("bill", pay);

        super.addDataTrace(process, data);
    }
}
