package com.hd.agent.phone.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.phone.service.IPhoneAllocateService;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 手机调拨申请相关action
 * Created by chenwei on 2016-07-23.
 */
public class AllocateAction extends BaseAction {

    private IPhoneAllocateService phoneAllocateService;

    public IPhoneAllocateService getPhoneAllocateService() {
        return phoneAllocateService;
    }

    public void setPhoneAllocateService(IPhoneAllocateService phoneAllocateService) {
        this.phoneAllocateService = phoneAllocateService;
    }

    /**
     * 获取仓库下的商品列表
     * @return
     * @throws Exception
     */
    public String searchStorageGoodsList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = phoneAllocateService.searchStorageGoodsList(pageMap);
        addJSONObject(pageData);
        return "success";
    }

    /**
     * 获取仓库商品信息
     * @return
     * @throws Exception
     */
    public String getStorageGoodsInfo() throws Exception{
        String goodsid = request.getParameter("goodsid");
        String storageid = request.getParameter("storageid");
        Map map = phoneAllocateService.getStorageGoodsInfo(storageid,goodsid);
        if(null!=map){
            addJSONObject(map);
        }else{
            addJSONObject("flag",false);
        }
        return "success";
    }

    /**
     * 上传调拨申请单
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String addAllocateNotice() throws Exception{
        String json = request.getParameter("json");
        JSONObject jsonObject = JSONObject.fromObject(json);
        Map map = phoneAllocateService.addAllocateNotice(jsonObject);
        String msg = (String) map.get("msg");
        addJSONObject(map);
        addLog("手机上传调拨申请单 ,"+msg, map);
        return "success";
    }

    /**
     * 获取调拨通知单列表
     * @return
     * @throws Exception
     */
    public String getAllocateNoticeList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list = phoneAllocateService.getAllocateNoticeList(pageMap);
        addJSONArray(list);
        return "success";
    }

    /**
     * 获取调拨通知单详细信息
     * @return
     * @throws Exception
     */
    public String getAllocateNoticeInfo() throws Exception{
        String billid = request.getParameter("billid");
        Map map = phoneAllocateService.getAllocateNoticeInfo(billid);
        addJSONObject(map);
        return "success";
    }

    /**
     * 获取仓库下的商品明细列表
     * @return
     * @throws Exception
     */
    public String getStorageGoodsByAllAllocate() throws Exception{
        String storageid = request.getParameter("storageid");
        List list = phoneAllocateService.getStorageGoodsByAllAllocate(storageid);
        addJSONArray(list);
        return "success";
    }


    /**
     * 上传调拨出库单
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String addAllocateOut() throws Exception{
        String json = request.getParameter("json");
        String addtype = request.getParameter("addtype");
        JSONObject jsonObject = JSONObject.fromObject(json);
        Map map = phoneAllocateService.addAllocateOut(jsonObject,addtype);
        String msg = (String) map.get("msg");
        addJSONObject(map);
        addLog("手机上传调拨出库单 ,"+msg, map);
        return "success";
    }
    /**
     * 获取调拨出库单列表
     * @return
     * @throws Exception
     */
    public String getAllocateOutList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list = phoneAllocateService.getAllocateOutList(pageMap);
        addJSONArray(list);
        return "success";
    }


    /**
     * 获取调拨出库单详细信息
     * @return
     * @throws Exception
     */
    public String getAllocateOutInfo() throws Exception{
        String billid = request.getParameter("billid");
        Map map = phoneAllocateService.getAllocateOutInfo(billid);
        addJSONObject(map);
        return "success";
    }

    /**
     * 获取调拨出库单明细数据
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月30日
     */
    @UserOperateLog(key="phone",type=2)
    public String getAllocateOutDetail() throws Exception{
        String id = request.getParameter("id");
        List list = phoneAllocateService.getAllocateOutDetail(id);
        addJSONArray(list);
        if(list.size()>0){
            addLog("扫描枪发货单同步："+id,true);
        }else{
            addLog("扫描枪发货单同步："+id,false);
        }
        return "success";
    }

    /**
     * 审核调拨入库单
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String auditAllocatStorageEnter() throws Exception{
        String billid = request.getParameter("billid");
        Map map = phoneAllocateService.auditAllocatStorageEnter(billid);
        String msg = (String) map.get("msg");
        addJSONObject(map);
        addLog("手机上传调拨出库单 ,"+msg, map);
        return "success";
    }
    /**
     * 调拨单审核出库
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 08, 2017
     */
    @UserOperateLog(key="AllocateOut",type=3)
    public String auditAllocateStorageOut() throws Exception {
        String id = request.getParameter("billid");
        Map map = phoneAllocateService.auditAllocateStorageOut(id);
        addJSONObject(map);
        addLog("调拨单审核出库 编号："+id, map);
        return "success";
    }

}
