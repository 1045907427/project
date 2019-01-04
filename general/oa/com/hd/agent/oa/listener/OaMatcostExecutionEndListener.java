package com.hd.agent.oa.listener;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.oa.model.OaMatcost;
import com.hd.agent.oa.model.OaMatcostDetail;
import com.hd.agent.oa.service.IOaMatcostService;
import com.hd.agent.oa.service.IOaPayService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class OaMatcostExecutionEndListener extends BusinessEndListenerImpl {

    private IOaMatcostService oaMatcostService;

    private IOaPayService payService;

    public IOaMatcostService getOaMatcostService() {
        return oaMatcostService;
    }

    public void setOaMatcostService(IOaMatcostService oaMatcostService) {
        this.oaMatcostService = oaMatcostService;
    }

    public IOaPayService getPayService() {
        return payService;
    }

    public void setPayService(IOaPayService payService) {
        this.payService = payService;
    }

    @Override
    public void delete(Process process) throws Exception {

    }

    @Override
    public void end(Process process) throws Exception {

    }

    @Override
    public boolean check(Process process) throws Exception {

        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return true;
        }


        OaMatcost matcost = oaMatcostService.getOaMatcost(process.getBusinessid());
        if (matcost == null) {
            return true;
        }

        List<OaMatcostDetail> detailList = oaMatcostService.getOaMatcostDetailListByBillid(matcost.getId());

        return payService.checkMatcostByOaMatcost(matcost, detailList)
                && payService.checkCustomerFeeByOaMatcost(matcost, detailList, "1")
                && payService.checkCustomerFeeByOaMatcost(matcost, detailList, "2")
                && payService.checkCustomerPushBalanceByOaMatcost(matcost, detailList)
                && payService.checkBankBillByOaMatcost(matcost, detailList)
                && payService.checkCollectionOrderByOaMatcost(matcost, detailList);
    }
}
