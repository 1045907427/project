package com.hd.agent.phone.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.phone.service.IPhoneService;

import java.util.List;
import java.util.Map;

/**
 * 手机采购相关业务
 * Created by chenwei on 2015-12-11.
 */
public class PurchaseAction extends BaseFilesAction {
    private IPhoneService phoneService;

    public IPhoneService getPhoneService() {
        return phoneService;
    }

    public void setPhoneService(IPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    /**
     * 未扫描枪提供采购入库单未审核列表
     * @return
     * @throws Exception
     */
    public String getPurchaseEnterForScanList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        List list = phoneService.getPurchaseEnterForScanList(map);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 采购入库单明细数据
     * @return
     * @throws Exception
     */
    public String getPurchaseEnterDetail() throws Exception{
        String id = request.getParameter("id");
        List list = phoneService.getPurchaseEnterDetail(id);
        addJSONArray(list);
        return  SUCCESS;
    }

    /**
     * 上传采购入库单数据并且审核
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String uploadPurchaseEnterAndAudit() throws Exception{
        String id = request.getParameter("id");
        String json = request.getParameter("json");
        Map map = phoneService.uploadPurchaseEnterAndAudit(json);
        addJSONObject(map);
        addLog("扫描枪采购入库单上传："+id,map);
        return SUCCESS;
    }
}
