package com.hd.agent.account.action;

import com.hd.agent.account.model.BeginDue;
import com.hd.agent.account.service.IBeginDueService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 供应商应付款期初action
 * @author panxiaoxiao
 */
public class BeginDueAction extends BaseFilesAction {

    private BeginDue beginDue;
    /**
     * 供应商应付款期初service
     */
    private IBeginDueService beginDueService;

    public IBeginDueService getBeginDueService() {
        return beginDueService;
    }

    public void setBeginDueService(IBeginDueService beginDueService) {
        this.beginDueService = beginDueService;
    }

    public BeginDue getBeginDue() {
        return beginDue;
    }

    public void setBeginDue(BeginDue beginDue) {
        this.beginDue = beginDue;
    }

    /**
     * 显示供应商应付款期初列表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public String showSupplierBeginDueListPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 获取供应商应付款期初数据列表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public String getBeginDueList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = beginDueService.getBeginDueList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 显示供应商应付款期初新增页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public String showBeginDueAddPage()throws Exception{
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        request.setAttribute("nextday", CommonUtils.getNextMonthDay(new Date()));
//        request.setAttribute("autoCreate", isAutoCreate("t_account_begin_due"));
        return SUCCESS;
    }

    /**
     * 添加供应商应付款期初
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    @UserOperateLog(key="BeginDue",type=2)
    public String addBeginDueSave()throws Exception{
        beginDue.setStatus("2");
        boolean flag = beginDueService.addBeginDue(beginDue);
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("id", beginDue.getId());
        addJSONObject(map);
        addLog("供应商应付款期初新增  编号："+beginDue.getId(), flag);
        return SUCCESS;
    }

    /**
     * 显示供应商应付款期初修改页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public String showBeginDueEditPage()throws Exception{
        String id = request.getParameter("id");
        BeginDue beginDue = beginDueService.getBeginDueInfo(id);
        request.setAttribute("beginDue", beginDue);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        return SUCCESS;
    }

    /**
     * 修改应付款期初
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    @UserOperateLog(key="BeginDue",type=3)
    public String editBeginDueSave()throws Exception{
        beginDue.setStatus("2");
        boolean flag = beginDueService.editBeginDue(beginDue);
        Map map = new HashMap();
        map.put("flag", flag);
        addJSONObject(map);
        addLog("供应商应付款期初修改  编号："+beginDue.getId(), flag);
        return SUCCESS;
    }

    /**
     * 显示应付款期初查看页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public String showBeginDueViewPage()throws Exception{
        String id = request.getParameter("id");
        BeginDue beginDue = beginDueService.getBeginDueInfo(id);
        request.setAttribute("beginDue", beginDue);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        return SUCCESS;
    }

    /**
     * 批量删除应付款期初
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    @UserOperateLog(key="BeginDue",type=4)
    public String deleteMutBeginDue()throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = beginDueService.deleteBeginDue(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "删除成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "删除失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("客户应付款期初批量删除 成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return SUCCESS;
    }

    /**
     * 批量审核应付款期初
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    @UserOperateLog(key="BeginDue",type=0)
    public String auditMutBeginDue()throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                Map map = beginDueService.auditBeignDue(id);
                boolean flag = (Boolean) map.get("flag");
                String msg = (String) map.get("msg");
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += msg+".";
                }
            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "审核成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "审核失败:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("供应商应付款期初批量审核 成功编号："+succssids+";审核失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return SUCCESS;
    }

    /**
     * 批量反审应付款期初
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    @UserOperateLog(key="BeginDue",type=0)
    public String oppauditMutBeginDue()throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                Map map = beginDueService.oppauditBeignDue(id);
                boolean flag = (Boolean) map.get("flag");
                String msg = (String) map.get("msg");
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += msg+".";
                }
            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "反审成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "反审失败:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("供应商应付款期初批量反审 成功编号："+succssids+";审核失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return SUCCESS;
    }

    /**
     * 导出应付款期初
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    public void exportBeginDue()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
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
        PageData pageData = beginDueService.getBeginDueList(pageMap);
        List<BeginDue> list = pageData.getList();
        list.addAll(pageData.getFooter());

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();

        firstMap.put("id", "单据号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("supplierid", "供应商编号");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("amount", "金额");
        firstMap.put("statusname", "状态");
        result.add(firstMap);

        if(null!=list && list.size() != 0){
            for(BeginDue beginDue : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(beginDue);
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
     * 应付款期初导入
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-09-27
     */
    @UserOperateLog(key="BeginDue",type=2)
    public String importBeginDue()throws Exception{
        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
        List<String> paramList2 = new ArrayList<String>();
        for(String str : paramList){
            if("单据号".equals(str)){
                paramList2.add("id");
            }else if("业务日期".equals(str)){
                paramList2.add("businessdate");
            }else if("供应商编号".equals(str)){
                paramList2.add("supplierid");
            }else if("供应商名称".equals(str)){
                paramList2.add("suppliername");
            }else if("金额".equals(str)){
                paramList2.add("amount");
            }else if("应付日期".equals(str)){
                paramList2.add("duetodate");
            }else{
                paramList2.add("null");
            }
        }
        Map map = new HashMap();
        String ids = "";
        List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
        if(list.size() != 0){
            map = beginDueService.importBeignDue(list);
            if(map.containsKey("ids")){
                ids = (String) map.get("ids");
            }
        }else{
            map.put("excelempty", true);
        }
        addJSONObject(map);
        addLog("商品应付款期初导入  编号:"+ids, map);
        return SUCCESS;
    }
}
