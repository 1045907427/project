package com.hd.agent.phone.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.crm.service.ICrmTerminalSalesService;
import com.hd.agent.crm.service.ICustomerStorageOrderService;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 客户数据上报 手机端action
 * Created by chenwei on 2016-11-09.
 */
public class CrmCustomerDataUploadAction extends BaseAction {
    /**
     * 客户库存上报service
     */
    private ICustomerStorageOrderService customerStorageOrderService;
    /**
     * 客户销售上报service
     */
    private ICrmTerminalSalesService crmTerminalSalesService;

    public ICrmTerminalSalesService getCrmTerminalSalesService() {
        return crmTerminalSalesService;
    }

    public void setCrmTerminalSalesService(ICrmTerminalSalesService crmTerminalSalesService) {
        this.crmTerminalSalesService = crmTerminalSalesService;
    }

    public ICustomerStorageOrderService getCustomerStorageOrderService() {
        return customerStorageOrderService;
    }

    public void setCustomerStorageOrderService(ICustomerStorageOrderService customerStorageOrderService) {
        this.customerStorageOrderService = customerStorageOrderService;
    }

    /**
     * 上传客户库存数据
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String uploadCrmStorageOrder() throws Exception{
        String json = request.getParameter("json");
        JSONObject jsonObject = JSONObject.fromObject(json);
        Map map = customerStorageOrderService.addPhoneStorageOrder(jsonObject);
        String customerid = jsonObject.getString("customerid");
        String billid = null;
        if(null!=map && map.containsKey("id")){
            billid = (String) map.get("id");
        }
        String msg = (String) map.get("msg");
        map.put("msg",msg);
        addJSONObject(map);
        addLog("手机上传客户库存 编号："+billid+";客户："+customerid, map);
        return SUCCESS;
    }

    /**
     * 上传客户销售数据
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String uploadCrmSalesOrder() throws Exception{
        String json = request.getParameter("json");
        JSONObject jsonObject = JSONObject.fromObject(json);
        Map map = crmTerminalSalesService.addPhoneTerminalOrder(jsonObject);
        String customerid = jsonObject.getString("customerid");
        String billid = null;
        if(null!=map && map.containsKey("id")){
            billid = (String) map.get("id");
        }
        String msg = (String) map.get("msg");
        map.put("msg",msg);
        addJSONObject(map);
        addLog("手机上传客户库存 编号："+billid+";客户："+customerid, map);
        return SUCCESS;
    }
}
