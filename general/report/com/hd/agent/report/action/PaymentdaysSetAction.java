/**
 * @(#)PaymentdaysSetAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 15, 2013 chenwei 创建版本
 */
package com.hd.agent.report.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.report.model.PaymentdaysSet;
import com.hd.agent.report.service.IPaymentdaysSetService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 *
 * 超账期设置
 * @author chenwei
 */
public class PaymentdaysSetAction extends BaseFilesAction {

    private IPaymentdaysSetService paymentdaysSetService;

    public IPaymentdaysSetService getPaymentdaysSetService() {
        return paymentdaysSetService;
    }

    public void setPaymentdaysSetService(
            IPaymentdaysSetService paymentdaysSetService) {
        this.paymentdaysSetService = paymentdaysSetService;
    }
    /**
     * 显示超账期设置页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 15, 2013
     */
    public String showPaymetdaysSetPage() throws Exception{
        String type = request.getParameter("type");
        if(StringUtils.isEmpty(type)){
            type = "1";
        }
        List list = paymentdaysSetService.getPaymentdaysSetInfo(type);
        String jsonStr = JSONUtils.listToJsonStr(list);

        request.setAttribute("detailList", jsonStr);
        request.setAttribute("type", type);
        return "success";
    }
    /**
     * 超账期区间设置保存
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 15, 2013
     */
    public String addPaymentdays() throws Exception{
        String detailJson = request.getParameter("detail");
        String type = request.getParameter("type");
        if(StringUtils.isEmpty(type)){
            type="1";
        }
        List detailList = JSONUtils.jsonStrToList(detailJson, new PaymentdaysSet());
        boolean flag = paymentdaysSetService.addPaymentdays(detailList,type);
        addJSONObject("flag", flag);
        return "success";
    }
}

