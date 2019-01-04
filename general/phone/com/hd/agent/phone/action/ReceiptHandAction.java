package com.hd.agent.phone.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.phone.service.IPhoneReceiptHandService;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回单交接单手机客户端相关操作
 * Created by chenwei on 2015-05-27.
 */
public class ReceiptHandAction extends BaseAction {
    /**
     * 回单交接单service
     */
    private IPhoneReceiptHandService phoneReceiptHandService;

    public IPhoneReceiptHandService getPhoneReceiptHandService() {
        return phoneReceiptHandService;
    }

    public void setPhoneReceiptHandService(IPhoneReceiptHandService phoneReceiptHandService) {
        this.phoneReceiptHandService = phoneReceiptHandService;
    }

    /**
     * 获取客户回单交接相关数据
     * @return
     * @throws Exception
     */
    public String getCustomerReceiptList() throws Exception{
        String con = request.getParameter("con");
        List list =  phoneReceiptHandService.getReceiptListGroupByCustomerForReceiptHand(con);
        addJSONArray(list);
        return "success";
    }

    /**
     * 根据客户编号获取客户未申请的回单交接单单据明细
     * @return
     * @throws Exception
     */
    public String getCustomerReceiptHandDetailList() throws Exception{
        String customerid = request.getParameter("customerid");
        Map map = new HashMap();
        map.put("customerid",customerid);
        map.put("isflag", true);
        pageMap.setCondition(map);
        Map dataMap = phoneReceiptHandService.getCustomerReceiptHandDetailList(pageMap);
        addJSONObject(dataMap);
        return "success";
    }

    /**
     * 手机申请回单交接单
     * @return
     * @throws Exception
     */
    @UserOperateLog(key = "ReceiptHand", type = 2)
    public String addReceiptHand() throws Exception{
        String ids = request.getParameter("ids");
        Map map = phoneReceiptHandService.addReceiptHand(ids);
        String receipthandid = (null != map.get("id")) ? (String)map.get("id") : "";
        if(StringUtils.isNotEmpty(receipthandid)){
            addLog("手机申请交接单新增 编号：" + receipthandid, true);
        }else{
            addLog("手机申请交接单新增失败");
        }
        addJSONObject(map);
        return "success";
    }

    /**
     * 根据查询条件获取回单交接单列表
     * @return
     * @throws Exception
     */
    public String getReceiptHandList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list = phoneReceiptHandService.getReceiptHandList(pageMap);
        addJSONArray(list);
        return "success";
    }

    /**
     * 根据单据编号获取单据客户列表
     * @return
     * @throws Exception
     */
    public String getReceiptHandCustomerList() throws Exception{
        String id = request.getParameter("id");
        List list = phoneReceiptHandService.getReceiptHandCustomerList(id);
        addJSONArray(list);
        return "success";
    }

    /**
     * 根据单据编号和客户编号 获取客户的单据列表
     * @return
     * @throws Exception
     */
    public String getReceiptHandBillList() throws Exception{
        String id = request.getParameter("id");
        String customerid = request.getParameter("customerid");
        List list = phoneReceiptHandService.getReceiptHandBillList(id,customerid);
        addJSONArray(list);
        return "success";
    }
}
