package com.hd.agent.storage.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.storage.model.StorageSendUserDetail;
import com.hd.agent.storage.model.StorageSendUserOrder;
import com.hd.agent.storage.service.IStorageSendUserService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wanghongteng on 2016/10/26.
 */
public class StorageSendUserAction extends BaseFilesAction {
    private IStorageSendUserService storageSendUserService;

    public IStorageSendUserService getStorageSendUserService() {
        return storageSendUserService;
    }

    public void setStorageSendUserService(IStorageSendUserService storageSendUserService) {
        this.storageSendUserService = storageSendUserService;
    }


    /*** 发货单据分配    **/

    /**
     * 显示发货人订单列表页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showSendUserOrderListPage() throws Exception {
        request.setAttribute("tomonth",CommonUtils.getMonthDateStr());
        request.setAttribute("today",CommonUtils.getTodayDataStr());
        return "success";
    }

    /**
     * 获取发货人订单列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showSendUserOrderList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageSendUserService.showSendUserOrderList(pageMap);
        addJSONObject(pageData);
        return "success";
    }

    /**
     * 显示核对发货页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showSendUserOrderCheckPage() throws Exception {
        request.setAttribute("tomonth",CommonUtils.getMonthDateStr());
        request.setAttribute("today",CommonUtils.getTodayDataStr());
        return "success";
    }

    /**
     * 显示直接上车页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showSendUserOrderLoadedPage() throws Exception {
        request.setAttribute("tomonth",CommonUtils.getMonthDateStr());
        request.setAttribute("today",CommonUtils.getTodayDataStr());
        return "success";
    }

    /**
     * 显示卸货统计页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showSendUserOrderUnloadedPage() throws Exception {
        request.setAttribute("tomonth",CommonUtils.getMonthDateStr());
        request.setAttribute("today",CommonUtils.getTodayDataStr());
        return "success";
    }

    /**
     * 获取待分配核对发货单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showOrderList() throws Exception{
        String billtype=request.getParameter("billtype");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageSendUserService.showOrderList(pageMap,billtype);
        addJSONObject(pageData);
        return "success";
    }

    /**
     * 核对发货人分配
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public String SendUserOrderAssigned() throws Exception{
        String orders=request.getParameter("orders");
        String storager=request.getParameter("storager");
        String billtype=request.getParameter("type");
        List<StorageSendUserOrder> orderList = JSONUtils.jsonStrToList(orders, new StorageSendUserOrder());
        int snum=0,fnum=0;
        String fmsg="";
        for(StorageSendUserOrder storageSendUserOrder : orderList){
            Map resultMap=storageSendUserService.SendUserOrderAssigned(storageSendUserOrder,storager,billtype);
            if((Boolean)resultMap.get("flag")){
                snum++;
            }else{
                fnum++;
                if(StringUtils.isNotEmpty((String)resultMap.get("msg"))){
                    fmsg=fmsg+"<br>"+(String)resultMap.get("msg");
                }
            }
        }
        String msg="";
        if(StringUtils.isNotEmpty(fmsg)){
            msg ="成功了"+snum+"条，失败了"+fnum+"条"+fmsg;
        }else{
            msg ="成功了"+snum+"条，失败了"+fnum+"条";
        }
        Map resultMap=new HashMap();
        resultMap.put("msg",msg);
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /*** 发货人报表    **/


    /**
     * 显示发货人报表页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showSendUserDetailListPage() throws Exception {
        request.setAttribute("tomonth",CommonUtils.getMonthDateStr());
        request.setAttribute("today",CommonUtils.getTodayDataStr());
        return "success";
    }


    /**
     * 获取发货人报表列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showSendUserDetailList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageSendUserService.showSendUserDetailList(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }


    /**
     * 显示发货人报表日志界面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String showSendUserDetailListLogPage() throws Exception {

        String senduserid=request.getParameter("senduserid");
        request.setAttribute("senduserid",senduserid);
        request.setAttribute("tomonth",CommonUtils.getMonthDateStr());
        request.setAttribute("today",CommonUtils.getTodayDataStr());
        return "success";
    }

    /**
     * 获取发货人报表日志列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    public String getSendUserDetailLogBySendUser() throws Exception {
        String senduserid=request.getParameter("senduserid");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("senduserid",senduserid);
        pageMap.setCondition(map);
        PageData pageData = storageSendUserService.getSendUserDetailLogBySendUser(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

    /**
     * 导出发货人报表
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/31
     */
    public void exportSendUserDetailList()throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        String state = request.getParameter("state");
        if(StringUtils.isNotEmpty(state)){
            map.put("state", state);
        }
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }
        pageMap.setCondition(map);

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("senduserid", "发货人编号");
        firstMap.put("sendusername", "发货人名称");
        firstMap.put("checknum", "核对数量");
        firstMap.put("checkamount", "核对金额");
        firstMap.put("checkvolume", "核对体积");
        firstMap.put("checkweight", "核对重量");
        firstMap.put("checkbox", "核对箱数");

        firstMap.put("loadednum", "发货数量");
        firstMap.put("loadedamount", "发货金额");
        firstMap.put("loadedvolume", "发货体积");
        firstMap.put("loadedweight", "发货重量");
        firstMap.put("loadedbox", "发货箱数");

        firstMap.put("unloadednum", "卸货数量");
        firstMap.put("unloadedamount", "卸货金额");
        firstMap.put("unloadedvolume", "卸货体积");
        firstMap.put("unloadedweight", "卸货重量");
        firstMap.put("unloadedbox", "卸货箱数");

        firstMap.put("remark", "备注");
        result.add(firstMap);

        PageData pageData = storageSendUserService.showSendUserDetailList(pageMap);
        List list = pageData.getList();
        if(list.size() != 0){
            for(Object obj : list){
                StorageSendUserDetail exportStorageSendUserDetail=(StorageSendUserDetail)obj;
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(exportStorageSendUserDetail);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }
                    else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 导出发货人报表明细
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/31
     */
    public void exportSendUserDetailLogList()throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        String state = request.getParameter("state");
        if(StringUtils.isNotEmpty(state)){
            map.put("state", state);
        }
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }
        map.put("isAll","isAll");
        pageMap.setCondition(map);

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("sourceid", "单据编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("checknum", "核对数量");
        firstMap.put("checkamount", "核对金额");
        firstMap.put("checkvolume", "核对体积");
        firstMap.put("checkweight", "核对重量");
        firstMap.put("checkbox", "核对箱数");

        firstMap.put("loadednum", "发货数量");
        firstMap.put("loadedamount", "发货金额");
        firstMap.put("loadedvolume", "发货体积");
        firstMap.put("loadedweight", "发货重量");
        firstMap.put("loadedbox", "发货箱数");

        firstMap.put("unloadednum", "卸货数量");
        firstMap.put("unloadedamount", "卸货金额");
        firstMap.put("unloadedvolume", "卸货体积");
        firstMap.put("unloadedweight", "卸货重量");
        firstMap.put("unloadedbox", "卸货箱数");

        firstMap.put("remark", "备注");
        result.add(firstMap);

        PageData pageData = storageSendUserService.getSendUserDetailLogBySendUser(pageMap);
        List list = pageData.getList();
        if(list.size() != 0){
            for(Object obj : list){
                StorageSendUserDetail exportStorageSendUserDetail=(StorageSendUserDetail)obj;
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(exportStorageSendUserDetail);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }
                    else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 删除发货人报表数据
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public String deleteSendUserOrder() throws Exception{
        String orders=request.getParameter("orders");
        List<Map> orderList = JSONUtils.jsonStrToList(orders, new HashMap());
        int snum=0,fnum=0;
        String fmsg="";
        String msg="";
        for(Map map : orderList){
            Map resultMap=storageSendUserService.deleteSendUserOrder(map);
            if((Boolean)resultMap.get("flag")){
                snum++;
            }else{
                fnum++;
                fmsg="<br>"+(String)resultMap.get("msg");
            }
        }
        if(StringUtils.isNotEmpty(fmsg)){
            msg ="成功了"+snum+"条，失败了"+fnum+"条"+fmsg;
        }else{
            msg ="成功了"+snum+"条，失败了"+fnum+"条";
        }

        Map resultMap=new HashMap();
        resultMap.put("msg",msg);
        addJSONObject(resultMap);
        return SUCCESS;
    }
}
