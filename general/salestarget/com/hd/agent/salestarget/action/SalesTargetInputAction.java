package com.hd.agent.salestarget.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.*;
import com.hd.agent.salestarget.model.SalesTargetInput;
import com.hd.agent.salestarget.service.ISalesTargetInputService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 销售目标Action层
 * Created by master on 2016/7/15.
 */
public class SalesTargetInputAction extends BaseFilesAction {
    private ISalesTargetInputService salesTargetInputService;

    public ISalesTargetInputService getSalesTargetInputService() {
        return salesTargetInputService;
    }

    public void setSalesTargetInputService(ISalesTargetInputService salesTargetInputService) {
        this.salesTargetInputService = salesTargetInputService;
    }

    private SalesTargetInput salesTargetInput;

    public SalesTargetInput getSalesTargetInput() {
        return salesTargetInput;
    }

    public void setSalesTargetInput(SalesTargetInput salesTargetInput) {
        this.salesTargetInput = salesTargetInput;
    }

    /**
     * 销售目标报表
     */
    private static String SALESTARGETREPORT_DATA_CACHE_KEY="SALESTARGETREPORT_DATA_CACHE";
    /**
     * 销售目标分页页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public String showSalesTargetInputListPage() throws Exception{
        String yearmonth=CommonUtils.getTodayMonStr();
        /*
        String yearfirstmonth="";
        if(yearmonth.length()>=4){
            yearfirstmonth=yearmonth.substring(0,4)+"-01";
        }else{
            yearfirstmonth=yearmonth;
        }
        */
        request.setAttribute("yearfirstmonth",yearmonth);
        request.setAttribute("yearmonth",yearmonth);
        return SUCCESS;
    }

    /**
     * 销售目标分页数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public String getSalesTargetInputListPageData() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());

        String sort = (String)map.get("sort");
        String order = (String) map.get("order");
        if(StringUtils.isEmpty(sort) || StringUtils.isEmpty(order)){
            if(map.containsKey("sort")){
                map.remove("sort");
            }
            if(map.containsKey("order")){
                map.remove("order");
            }
            pageMap.setOrderSql(" addtime desc , id desc");
        }
        pageMap.setCondition(map);
        PageData pageData=salesTargetInputService.showSalesTargetInputListPageData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }
    /**
     * 销售目标添加页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public String showSalesTargetInputAddPage() throws Exception{
        request.setAttribute("autoCreate", isAutoCreate("t_salestarget_input"));
        request.setAttribute("today", CommonUtils.getTodayMonStr());
        return SUCCESS;
    }

    /**
     * 销售目标添加操作
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @UserOperateLog(key="SalesTargetInput",type=2)
    public String addSalesTargetInput() throws Exception{
        Map resultMap = new HashMap();
        if(StringUtils.isEmpty(salesTargetInput.getYearmonth())){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，请输入年月");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(StringUtils.isEmpty(salesTargetInput.getCustomerid())){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，未能找到门店信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(StringUtils.isEmpty(salesTargetInput.getBrandid())){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，未能找到品牌信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(null==salesTargetInput.getFirstsalestarget() ){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，第一销售目标不能为空");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(salesTargetInput.getFirstsalestarget().compareTo(BigDecimal.ZERO)<0){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，第一销售目标不能为负数");
            addJSONObject(resultMap);
            return SUCCESS;
        }

        if(null==salesTargetInput.getSecondsalestarget()){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，第二销售目标不能为空");
            addJSONObject(resultMap);
            return SUCCESS;
        }else if(salesTargetInput.getSecondsalestarget().compareTo(BigDecimal.ZERO)<0){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，第二销售目标不能为负数");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(salesTargetInput.getSecondgrossprofit()==null) {
            salesTargetInput.setSecondgrossprofit(BigDecimal.ZERO);
        }

        if(StringUtils.isEmpty(salesTargetInput.getStatus())){
            salesTargetInput.setStatus("2");
        }

        resultMap=salesTargetInputService.addSalesTargetInput(salesTargetInput);
        Boolean flag = false;
        if (null == resultMap) {
            resultMap = new HashMap();
            resultMap.put("flag", false);
            flag = false;
        } else {
            flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
                resultMap.put("flag", false);
            }
        }
        StringBuffer logSb=new StringBuffer();
        logSb.append("添加销售目标。");
        logSb.append(" 门店编码：");
        logSb.append(salesTargetInput.getCustomerid());
        logSb.append(" 品牌编码：");
        logSb.append(salesTargetInput.getBrandid());
        if(!flag){
            String msg=(String)resultMap.get("msg");
            if(null!=msg && !"".equals(msg.trim())){
                logSb.append(msg);
            }
        }
        addLog(logSb.toString(), flag);
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 销售目标修改页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public String showSalesTargetInputEditPage() throws Exception{
        String id=request.getParameter("id");
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        SalesTargetInput oldSalesTargetInput = salesTargetInputService.showSalesTargetInput(id);
        request.setAttribute("salesTargetInput", oldSalesTargetInput);
        if(null!=oldSalesTargetInput){
            if("2".equals(oldSalesTargetInput.getStatus())){
                return "editSuccess";
            }else if("3".equals(oldSalesTargetInput.getStatus()) || "4".equals(oldSalesTargetInput.getStatus())){
                return "viewSuccess";
            }else{
                return "viewSuccess";
            }
        }else{
            return "addSuccess";
        }
    }

    /**
     * 销售目标修改操作
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @UserOperateLog(key="SalesTargetInput",type=3)
    public String editSalesTargetInput() throws Exception{
        Map resultMap = new HashMap();
        if(StringUtils.isEmpty(salesTargetInput.getYearmonth())){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，请输入年月");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(StringUtils.isEmpty(salesTargetInput.getCustomerid())){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，未能打到门店信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(StringUtils.isEmpty(salesTargetInput.getBrandid())){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，未能找到品牌信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(null==salesTargetInput.getFirstsalestarget() ){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，第一销售目标不能为空");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if(salesTargetInput.getFirstsalestarget().compareTo(BigDecimal.ZERO)<0){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，第一销售目标不能为负数");
            addJSONObject(resultMap);
            return SUCCESS;
        }

        if(null==salesTargetInput.getSecondsalestarget()){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，第二销售目标不能为空");
            addJSONObject(resultMap);
            return SUCCESS;
        }else if(salesTargetInput.getSecondsalestarget().compareTo(BigDecimal.ZERO)<0){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，第二销售目标不能为负数");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        resultMap=salesTargetInputService.editSalesTargetInput(salesTargetInput);
        Boolean flag = false;
        if (null == resultMap) {
            resultMap = new HashMap();
            resultMap.put("flag", false);
            flag = false;
        } else {
            flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
                resultMap.put("flag", false);
            }
        }
        StringBuffer logSb=new StringBuffer();
        logSb.append("添加销售目标。");
        logSb.append(" 门店编码：");
        logSb.append(salesTargetInput.getCustomerid());
        logSb.append(" 品牌编码：");
        logSb.append(salesTargetInput.getBrandid());
        if(!flag){
            String msg=(String)resultMap.get("msg");
            if(null!=msg && !"".equals(msg.trim())){
                logSb.append(msg);
            }
        }
        addLog(logSb.toString(), flag);
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 销售目标查询页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    public String showSalesTargetInputViewPage() throws Exception{
        String id=request.getParameter("id");
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        SalesTargetInput oldSalesTargetInput = salesTargetInputService.showSalesTargetInput(id);
        request.setAttribute("salesTargetInput", oldSalesTargetInput);
        if(null!=oldSalesTargetInput){
            if("2".equals(oldSalesTargetInput.getStatus())){
                return "editSuccess";
            }else if("3".equals(oldSalesTargetInput.getStatus()) || "4".equals(oldSalesTargetInput.getStatus())){
                return "viewSuccess";
            }else{
                return "viewSuccess";
            }
        }else{
            return "addSuccess";
        }
    }

    /**
     * 销售目标删除操作
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年7月15日
     */
    @UserOperateLog(key="SalesTargetInput",type=4)
    public String deleteSalesTargetInputMore() throws Exception{
        String idarrs = request.getParameter("idarrs");
        Map resultMap= salesTargetInputService.deleteSalesTargetInputMore(idarrs);
        Boolean flag=false;
        StringBuffer logBuffer=new StringBuffer();
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
            }
            logBuffer.append("删除销售目标 编号:");
            logBuffer.append(idarrs);
            String tmpd=null;
            Integer itmpd=null;
            if(resultMap.containsKey("isuccess")) {
                itmpd=(Integer)resultMap.get("isuccess");
                if (null!=itmpd && itmpd>0 && resultMap.containsKey("succssids")) {
                    tmpd = (String) resultMap.get("succssids");
                    if (null != tmpd && !"".equals(tmpd.trim())) {
                        logBuffer.append(" 成功编号:");
                        logBuffer.append(tmpd);
                    }
                }
            }
            if(resultMap.containsKey("ifailure")) {
                itmpd=(Integer)resultMap.get("ifailure");
                if (null!=itmpd && itmpd>0 && resultMap.containsKey("failids")) {
                    tmpd = (String) resultMap.get("failids");
                    if (null != tmpd && !"".equals(tmpd.trim())) {
                        logBuffer.append(" 失败编号:");
                        logBuffer.append(tmpd);
                    }
                }
            }
            if(resultMap.containsKey("msg")){
                tmpd=(String)resultMap.get("msg");
                if(null!=tmpd && !"".equals(tmpd.trim())){
                    logBuffer.append(" 处理提示消息:");
                    logBuffer.append(tmpd);
                }
            }
            addLog(logBuffer.toString(),flag);
        } else {
            addLog("删除销售目标失败 编号失败:" + idarrs);
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 销售目标启用操作
     * @return
     * @throws Exception
     * @date 2016年07月15日
     */
    @UserOperateLog(key="SalesTargetInput",type=0)
    public String auditSalesTargetInput() throws Exception{
        String idarrs = request.getParameter("idarrs");
        Map resultMap= salesTargetInputService.auditSalesTargetInputMore(idarrs);
        Boolean flag=false;
        StringBuffer logBuffer=new StringBuffer();
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
            }

            logBuffer.append("审核销售目标 编号:");
            logBuffer.append(idarrs);
            String tmpd=null;
            Integer itmpd=null;
            if(resultMap.containsKey("isuccess")) {
                itmpd=(Integer)resultMap.get("isuccess");
                if (null!=itmpd && itmpd>0 && resultMap.containsKey("succssids")) {
                    tmpd = (String) resultMap.get("succssids");
                    if (null != tmpd && !"".equals(tmpd.trim())) {
                        logBuffer.append(" 成功编号:");
                        logBuffer.append(tmpd);
                    }
                }
            }
            if(resultMap.containsKey("ifailure")) {
                itmpd=(Integer)resultMap.get("ifailure");
                if (null!=itmpd && itmpd>0 && resultMap.containsKey("failids")) {
                    tmpd = (String) resultMap.get("failids");
                    if (null != tmpd && !"".equals(tmpd.trim())) {
                        logBuffer.append(" 失败编号:");
                        logBuffer.append(tmpd);
                    }
                }
            }
            if(resultMap.containsKey("msg")){
                tmpd=(String)resultMap.get("msg");
                if(null!=tmpd && !"".equals(tmpd.trim())){
                    logBuffer.append(" 处理提示消息:");
                    logBuffer.append(tmpd);
                }
            }
            addLog(logBuffer.toString(),flag);
        }else {
            addLog("审核销售目标失败 编号失败:"+idarrs);
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }
    /**
     * 销售目标反审操作
     * @return
     * @throws Exception
     * @date 2016年07月15日
     */
    @UserOperateLog(key="SalesTargetInput",type=0)
    public String oppauditSalesTargetInput() throws Exception{
        String idarrs = request.getParameter("idarrs");
        Map resultMap= salesTargetInputService.oppauditSalesTargetInputMore(idarrs);
        Boolean flag=false;
        StringBuffer logBuffer=new StringBuffer();
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
            }
            logBuffer.append("反审销售目标 编号:");
            logBuffer.append(idarrs);
            String tmpd=null;
            Integer itmpd=null;
            if(resultMap.containsKey("isuccess")) {
                itmpd=(Integer)resultMap.get("isuccess");
                if (null!=itmpd && itmpd>0 && resultMap.containsKey("succssids")) {
                    tmpd = (String) resultMap.get("succssids");
                    if (null != tmpd && !"".equals(tmpd.trim())) {
                        logBuffer.append(" 成功编号:");
                        logBuffer.append(tmpd);
                    }
                }
            }
            if(resultMap.containsKey("ifailure")) {
                itmpd=(Integer)resultMap.get("ifailure");
                if (null!=itmpd && itmpd>0 && resultMap.containsKey("failids")) {
                    tmpd = (String) resultMap.get("failids");
                    if (null != tmpd && !"".equals(tmpd.trim())) {
                        logBuffer.append(" 失败编号:");
                        logBuffer.append(tmpd);
                    }
                }
            }
            if(resultMap.containsKey("msg")){
                tmpd=(String)resultMap.get("msg");
                if(null!=tmpd && !"".equals(tmpd.trim())){
                    logBuffer.append(" 处理提示消息:");
                    logBuffer.append(tmpd);
                }
            }
            addLog(logBuffer.toString(),flag);
        }else {
            addLog("反审销售目标失败 编号失败:"+idarrs);
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }
    /**
     * 导出-销售目标报表
     * @param
     * @return void
     * @throws
     * @author zhang_honghui
     * @date Sep 20, 2016
     */
    public void exportSalesTargetInputData()throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag", "true");
        map.put("isExportData", "true");	//是否导出数据
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
        PageData pageData = salesTargetInputService.showSalesTargetInputListPageData(pageMap);
        ExcelUtils.exportExcel(exportSalesTargetInputDataFilter(pageData.getList()), title);
    }

    /**
     * 导出的数据转换，list专程符合excel导出的数据格式
     * @param list
     * @return
     * @throws
     * @author zhang_honghui
     * @date Sep 20, 2016
     */
    private List<Map<String, Object>> exportSalesTargetInputDataFilter(List<SalesTargetInput> list) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编码");
        firstMap.put("yearmonth", "录入年月");
        firstMap.put("customerid","客户编码");
        firstMap.put("customername","门店名称");
        firstMap.put("customersort", "渠道编码");
        firstMap.put("customersortname", "渠道");
        firstMap.put("brandid", "品牌编码");
        firstMap.put("brandname", "品牌");
        firstMap.put("salesusername", "客户业务员");
        firstMap.put("firstsalestarget", "第一销售目标");
        firstMap.put("firstgrossprofit", "第一目标毛利");
        firstMap.put("firstgrossprofitrate", "第一目标毛利率(%)");
        firstMap.put("secondsalestarget", "第二销售目标");
        firstMap.put("secondgrossprofit", "第二目标毛利");
        firstMap.put("secondgrossprofitrate", "第二目标毛利率(%)");
        firstMap.put("statusname", "状态");
        firstMap.put("remark", "备注");
        firstMap.put("addusername", "制单人");
        firstMap.put("addtime", "制单时间");
        firstMap.put("auditusername", "审核人");
        firstMap.put("audittime", "审核时间");

        result.add(firstMap);

        if(list.size() != 0){
            for(SalesTargetInput salesTargetInput : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(salesTargetInput);
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
        return result;
    }

    /**
     * 批量导入销售目标
     * @param
     * @return
     * @throws
     * @author zhang_honghui
     * @date Sep 20, 2016
     */
    @UserOperateLog(key = "SalesTargetInput", type = 2)
    public String importSalesTargetInputData() throws Exception {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            String errorTempletFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/SalesTargetInputTemplet.xls");
            List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
            List<String> paramList2 = new ArrayList<String>();
            List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
            for (String str : paramList) {
                if (null == str || "".equals(str.trim())) {
                    continue;
                }
                if ("年份".equals(str.trim())) {
                    paramList2.add("year");
                }else if ("月份".equals(str.trim())) {
                    paramList2.add("month");
                }   else if ("门店名称".equals(str.trim())) {
                    paramList2.add("customername");
                } else if ("品牌名称".equals(str.trim())) {
                    paramList2.add("brandname");
                } else if ("第一销售目标".equals(str.trim())) {
                    paramList2.add("firstsalestarget");
                } else if ("第一目标毛利".equals(str.trim())) {
                    paramList2.add("firstgrossprofit");
                } else if ("第二销售目标".equals(str.trim())) {
                    paramList2.add("secondsalestarget");
                }  else if ("第二目标毛利".equals(str.trim())) {
                    paramList2.add("secondgrossprofit");
                } else if ("备注".equals(str.trim())) {
                    paramList2.add("remark");
                } else {
                    paramList2.add("null");
                }
            }
            List<String> dataCellList = new ArrayList<String>();
            dataCellList.add("year");
            dataCellList.add("month");
            dataCellList.add("customername");
            dataCellList.add("brandname");
            dataCellList.add("firstsalestarget");
            dataCellList.add("firstgrossprofit");
            dataCellList.add("secondsalestarget");
            dataCellList.add("secondgrossprofit");
            dataCellList.add("remark");
            dataCellList.add("errormessage");


            List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
            if (list!=null && list.size() != 0) {
                resultMap = salesTargetInputService.addDRSalesTargetInput(list);
                if(resultMap.containsKey("errorDataList") && null!=resultMap.get("errorDataList")){
                    List<SalesTargetInput> errorDataList=(List<SalesTargetInput>)resultMap.get("errorDataList");
                    for(SalesTargetInput item:errorDataList){
                        Map itemMap = PropertyUtils.describe(item);
                        if(null!=itemMap){
                            errorList.add(itemMap);
                        }
                    }
                }

                Boolean flag = false;
                if (null != resultMap) {
                    flag = (Boolean) resultMap.get("flag");
                    if (null == flag) {
                        flag = false;
                    }
                }
                StringBuffer logBuffer=new StringBuffer();
                logBuffer.append("批量导入销售目标");
                if(errorList.size() > 0){
                    try {
                        IAttachFileService attachFileService = (IAttachFileService) SpringContextUtils.getBean("attachFileService");
                        String fileid = attachFileService.createExcelAndAttachFile(errorList, dataCellList, errorTempletFilePath,"销售目标导入失败");
                        resultMap.put("msg", "导入失败" + errorList.size() + "条");
                        resultMap.put("errorid", fileid);
                        if (errorList.size() > 0) {
                            logBuffer.append("导入失败" + errorList.size() + "条");
                        }
                    }catch (Exception ex){
                        resultMap.put("msg", "生成导入出错的excel文件失败");
                    }
                }
                addLog(logBuffer.toString(), flag);
            } else {
                resultMap.put("excelempty", true);
            }
        }catch (Exception ex){
            resultMap.put("msg","导入时系统异常");
        }
        addJSONObject(resultMap);

        return SUCCESS;
    }

    /**
     * 获取销售目标获取客户分类信息列表
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Sep 22, 2016
     */
    public String getSalesTargetInputCustomerSortGroupList() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());
        map.put("grouptype","customersort");
        List<Map> list=salesTargetInputService.getSalesTargetInputGroupListBy(map);
        addJSONArray(list);
        return SUCCESS;
    }
    /**
     * 获取销售目标中品牌信息列表
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Sep 22, 2016
     */
    public String getSalesTargetInputBrandGroupList() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());
        map.put("grouptype","brandid");
        List<Map> list=salesTargetInputService.getSalesTargetInputGroupListBy(map);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 获取销售报表中品牌列表
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Oct 13, 2016
     */
    public String getBrandListInSalesReportCache() throws Exception{
        String customersort=request.getParameter("customersort");
        String brandidarrs=request.getParameter("brandidarrs");
        String businessdatestart=request.getParameter("businessdatestart");
        String businessdateend=request.getParameter("businessdateend");
        String businessdatestart2=request.getParameter("businessdatestart2");
        String businessdateend2=request.getParameter("businessdateend2");
        if(customersort==null){
            customersort="";
        }
        customersort=customersort.trim();

        if(brandidarrs==null||"".equals(brandidarrs.trim())){
            brandidarrs=",";
        }
        brandidarrs=brandidarrs.trim();
        String[] brandidArr=brandidarrs.split(",");

        if(businessdatestart==null){
            businessdatestart="";
        }
        businessdatestart=businessdatestart.trim();

        if(businessdateend==null){
            businessdateend="";
        }
        businessdateend=businessdateend.trim();

        if(businessdatestart2==null){
            businessdatestart2="";
        }
        businessdatestart2=businessdatestart2.trim();

        if(businessdateend2==null){
            businessdateend2="";
        }
        businessdateend2=businessdateend2.trim();

        String key="brandid_"+CommonUtils.MD5(customersort+"_"+businessdatestart+"_"+businessdateend+"_"+businessdatestart2+"_"+businessdateend2);
        List<Map> list =null;
        Map cacheMap=(Map)EhcacheUtils.getCacheData(SALESTARGETREPORT_DATA_CACHE_KEY,"brandListInSalesReport");
        if(null!=cacheMap && cacheMap.size()>0){
            if(cacheMap.containsKey(key)) {
                list=(List<Map>) cacheMap.get(key);
            }
        }else{
            cacheMap=new HashMap();
        }
        List<Map> resultList=new ArrayList<Map>();
        if(list==null || list.size()==0){
            Map paramMap=new HashMap();
            if(!"".equals(customersort)) {
                paramMap.put("customersort", customersort);
            }
            if(!"".equals(businessdatestart)) {
                paramMap.put("businessdatestart", businessdatestart);
            }
            if(!"".equals(businessdateend)) {
                paramMap.put("businessdateend", businessdateend);
            }
            if(!"".equals(businessdatestart2)) {
                paramMap.put("businessdatestart2", businessdatestart2);
            }
            if(!"".equals(businessdateend2)) {
                paramMap.put("businessdateend2", businessdateend2);
            }
            paramMap.put("grouptype","brandid");
            list=salesTargetInputService.getBrandCustomerSortListInSalesReport(paramMap);
            cacheMap.put(key,list);
            EhcacheUtils.addCache(SALESTARGETREPORT_DATA_CACHE_KEY, "brandListInSalesReport", cacheMap);
        }
        resultList=list;
        if(list!=null && list.size()>0) {
            if(brandidArr.length>0) {
                List<Map> tmpList = new ArrayList<Map>();
                boolean hasQueryv=false;
                for (Map itemMap : list) {
                    String brandid = (String) itemMap.get("id");
                    if(brandid!=null && !"".equals(brandid.trim())){
                        //只要brandid不为空就表明查询
                        hasQueryv=true;
                        if(Arrays.binarySearch(brandidArr,brandid.trim())>=0){
                            tmpList.add(itemMap);
                        }
                    }
                }
                if(hasQueryv){
                    resultList=tmpList;
                }
            }
        }
        addJSONArray(resultList);
        return SUCCESS;
    }


    /**
     * 获取销售报表中渠道列表
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Oct 13, 2016
     */
    public String getCustomerSortListInSalesReportCache() throws Exception{
        String brandid=request.getParameter("brandid");
        String customersortarrs=request.getParameter("customersortarrs");
        String businessdatestart=request.getParameter("businessdatestart");
        String businessdateend=request.getParameter("businessdateend");
        String businessdatestart2=request.getParameter("businessdatestart2");
        String businessdateend2=request.getParameter("businessdateend2");
        if(brandid==null){
            brandid="";
        }
        brandid=brandid.trim();

        if(customersortarrs==null || "".equals(customersortarrs.trim())){
            customersortarrs=",";
        }
        customersortarrs=customersortarrs.trim();
        String[] customersortArr=customersortarrs.split(",");

        if(businessdatestart==null){
            businessdatestart="";
        }
        businessdatestart=businessdatestart.trim();

        if(businessdateend==null){
            businessdateend="";
        }
        businessdateend=businessdateend.trim();

        if(businessdatestart2==null){
            businessdatestart2="";
        }
        businessdatestart2=businessdatestart2.trim();

        if(businessdateend2==null){
            businessdateend2="";
        }
        businessdateend2=businessdateend2.trim();

        String key="customersort_"+CommonUtils.MD5(brandid+"_"+businessdatestart+"_"+businessdateend+"_"+businessdatestart2+"_"+businessdateend2);
        List<Map> list =null;
        Map cacheMap=(Map)EhcacheUtils.getCacheData(SALESTARGETREPORT_DATA_CACHE_KEY,"customersortListInSalesReport");
        if(null!=cacheMap && cacheMap.size()>0){
            if(cacheMap.containsKey(key)) {
                list=(List<Map>) cacheMap.get(key);
            }
        }else{
            cacheMap=new HashMap();
        }
        List<Map> resultList=new ArrayList<Map>();
        if(list==null || list.size()==0){
            Map paramMap=new HashMap();
            if(!"".equals(brandid)) {
                paramMap.put("brandid", brandid);
            }
            if(!"".equals(businessdatestart)) {
                paramMap.put("businessdatestart", businessdatestart);
            }
            if(!"".equals(businessdateend)) {
                paramMap.put("businessdateend", businessdateend);
            }
            if(!"".equals(businessdatestart2)) {
                paramMap.put("businessdatestart2", businessdatestart2);
            }
            if(!"".equals(businessdateend2)) {
                paramMap.put("businessdateend2", businessdateend2);
            }
            paramMap.put("grouptype","customersort");
            list=salesTargetInputService.getBrandCustomerSortListInSalesReport(paramMap);
            cacheMap.put(key,list);
            EhcacheUtils.addCache(SALESTARGETREPORT_DATA_CACHE_KEY,"customersortListInSalesReport", cacheMap);
        }
        resultList=list;
        if(list!=null && list.size()>0) {
            if(customersortArr.length>0) {
                List<Map> tmpList = new ArrayList<Map>();
                boolean hasQueryv=false;
                for (Map itemMap : list) {
                    String customersort = (String) itemMap.get("id");
                    if(brandid!=null && !"".equals(brandid.trim())){
                        hasQueryv=true;
                        if(Arrays.binarySearch(customersortArr,customersort.trim())>=0){
                            tmpList.add(itemMap);
                        }
                    }
                }
                if(hasQueryv){
                    resultList=tmpList;
                }
            }
        }
        addJSONArray(resultList);
        return SUCCESS;
    }

    /**
     * 清除销售报表中品牌和渠道列表缓存
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Oct 13, 2016
     */
    public String clearBrandCustomerSortListInSalesReportCache() throws Exception{
        String opertype=request.getParameter("opertype");
        if(opertype==null){
            opertype="";
        }
        opertype=opertype.toLowerCase().trim();
        if(!"1".equals(opertype)&&!"2".equals(opertype)){
            opertype="all";
        }
        Map cacheMap=null;
        if("1".equals(opertype) || "all".equals(opertype)) {
            cacheMap = (Map) EhcacheUtils.getCacheData(SALESTARGETREPORT_DATA_CACHE_KEY,"brandListInSalesReport");
            if (cacheMap != null) {
                EhcacheUtils.removeCache(SALESTARGETREPORT_DATA_CACHE_KEY,"brandListInSalesReport");
            }
        }
        if("2".equals(opertype) || "all".equals(opertype)) {
            cacheMap = (Map) EhcacheUtils.getCacheData(SALESTARGETREPORT_DATA_CACHE_KEY,"customerSortListInSalesReport");
            if (cacheMap != null) {
                EhcacheUtils.removeCache(SALESTARGETREPORT_DATA_CACHE_KEY,"customerSortListInSalesReport");
            }
        }
        addJSONObject("flag",true);
        return  SUCCESS;
    }
}
