package com.hd.agent.oa.listener;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.service.IOaBusinessService;
import com.hd.agent.sales.model.Offprice;

/**
 * Created by limin on 2015/3/20.
 */
public class OaOffPriceExecutionEndListener extends BusinessEndListenerImpl {

    private IOaBusinessService businessService;

    public IOaBusinessService getBusinessService() {
        return businessService;
    }

    public void setBusinessService(IOaBusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public boolean check(com.hd.agent.activiti.model.Process process) throws Exception {

        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return true;
        }

        Offprice price = businessService.oaOffPriceSelectOffPrice(process.getId());
        if(price == null) {

            return true;
        }

        return false;
    }

}
