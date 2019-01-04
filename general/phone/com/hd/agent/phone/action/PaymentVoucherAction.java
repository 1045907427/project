package com.hd.agent.phone.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.phone.service.IPhonePaymentVoucherService;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 交款单
 * Created by chenwei on 2016-07-14.
 */
public class PaymentVoucherAction extends BaseAction {

    private IPhonePaymentVoucherService phonePaymentVoucherService;

    public IPhonePaymentVoucherService getPhonePaymentVoucherService() {
        return phonePaymentVoucherService;
    }

    public void setPhonePaymentVoucherService(IPhonePaymentVoucherService phonePaymentVoucherService) {
        this.phonePaymentVoucherService = phonePaymentVoucherService;
    }

    /**
     * 添加交款单
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String addPaymentVoucher() throws Exception{
        String json = request.getParameter("json");
        JSONObject jsonObject = JSONObject.fromObject(json);
        Map map = phonePaymentVoucherService.addPaymentVoucher(jsonObject);
        String msg = (String) map.get("msg");
        addJSONObject(map);
        addLog("手机上传交款单,"+msg, map);
        return "success";
    }

    /**
     * 获取交款单列表
     * @return
     * @throws Exception
     */
    public String getPaymentVoucherList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list = phonePaymentVoucherService.getPaymentVoucherList(pageMap);
        addJSONArray(list);
        return "success";
    }

    /**
     * 获取交款单信息
     * @return
     * @throws Exception
     */
    public String getPaymentVoucherInfo() throws Exception{
        String billid = request.getParameter("billid");
        Map map = phonePaymentVoucherService.getPaymentVoucherInfo(billid);
        addJSONObject(map);
        return "success";
    }
}
