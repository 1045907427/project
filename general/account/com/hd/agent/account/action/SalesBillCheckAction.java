/**
 * @(#)SalesBillCheckAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 19, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.account.model.SalesBillCheck;
import com.hd.agent.account.service.ISalesBillCheckService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.model.SysParam;

/**
 *
 * 销售单据核对action
 * @author panxiaoxiao
 */
public class SalesBillCheckAction extends BaseFilesAction {

    private SalesBillCheck salesBillCheck;

    private IExcelService excelService;

    private ISalesBillCheckService salesBillCheckService;

    public ISalesBillCheckService getSalesBillCheckService() {
        return salesBillCheckService;
    }

    public void setSalesBillCheckService(
            ISalesBillCheckService salesBillCheckService) {
        this.salesBillCheckService = salesBillCheckService;
    }

    public SalesBillCheck getSalesBillCheck() {
        return salesBillCheck;
    }

    public void setSalesBillCheck(SalesBillCheck salesBillCheck) {
        this.salesBillCheck = salesBillCheck;
    }

    public IExcelService getExcelService() {
        return excelService;
    }

    public void setExcelService(IExcelService excelService) {
        this.excelService = excelService;
    }

    /**
     * 显示销售单据核对页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 14, 2014
     */
    public String showSalesBillCheckPage()throws Exception{
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 获取销售单据核对列表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 14, 2014
     */
    public String showSalesBillCheckList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        String onedayflag = null != map.get("onedayflag") ? (String)map.get("onedayflag") : "";
        if("1".equals(onedayflag)){
            String businessdate1 = (String)map.get("businessdate1");
            map.put("businessdate2",businessdate1);
        }
        pageMap.setCondition(map);
        PageData pageData = salesBillCheckService.showSalesBillCheckData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 显示销售单据核销信息页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 14, 2014
     */
    public String showSalesBillCheckInfoPage()throws Exception{
        String customerid = request.getParameter("customerid");
        String businessdate = request.getParameter("businessdate");
        SysParam sysParam = getBaseSysParamService().getSysParam("amountDecimalsLength");
        if(null != sysParam){
            request.setAttribute("amounlen", sysParam.getPvalue());
        }else{
            request.setAttribute("amounlen", "2");
        }
        boolean flag = salesBillCheckService.repeatSalesBillCheck(customerid,businessdate);
        if(flag){
            request.setAttribute("type", "1");
            SalesBillCheck salesBillCheck = salesBillCheckService.getSalesBillCheckInfo(customerid, businessdate);
            request.setAttribute("salesBillCheck", salesBillCheck);
            return "editsuccess";
        }else{
            request.setAttribute("customerid", customerid);
            request.setAttribute("businessdate", businessdate);
            request.setAttribute("type", "0");
            return "addsuccess";
        }
    }

    /**
     * 添加销售单据核对信息
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 14, 2014
     */
    @UserOperateLog(key="SalesBillCheck",type=0,value="")
    public String addSalesBillCheck()throws Exception{
        boolean flag = salesBillCheckService.repeatSalesBillCheck(salesBillCheck.getCustomerid(),salesBillCheck.getBusinessdate());
        if(flag){
            SalesBillCheck salesBillCheck2 = salesBillCheckService.getSalesBillCheckInfo(salesBillCheck.getCustomerid(),salesBillCheck.getBusinessdate());
            salesBillCheck.setId(salesBillCheck2.getId());
            Map map = salesBillCheckService.editSalesBillCheck(salesBillCheck);
            addLog("修改销售核对信息 客户编号:"+salesBillCheck.getCustomerid(),map.get("flag").equals(true));
            addJSONObject(map);
        }else{
            Map map = salesBillCheckService.addSalesBillCheck(salesBillCheck);
            addLog("新增销售核对信息 客户编号:"+salesBillCheck.getCustomerid(),map.get("flag").equals(true));
            addJSONObject(map);
        }
        return SUCCESS;
    }

    /**
     * 修改销售单据核对信息
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 14, 2014
     */
    @UserOperateLog(key="SalesBillCheck",type=0,value="")
    public String editSalesBillCheck()throws Exception{
        boolean flag = salesBillCheckService.repeatSalesBillCheck(salesBillCheck.getCustomerid(),salesBillCheck.getBusinessdate());
        if(flag){
            SalesBillCheck salesBillCheck2 = salesBillCheckService.getSalesBillCheckInfo(salesBillCheck.getCustomerid(),salesBillCheck.getBusinessdate());
            salesBillCheck.setId(salesBillCheck2.getId());
            Map map = salesBillCheckService.editSalesBillCheck(salesBillCheck);
            addLog("修改销售核对信息 客户编号:"+salesBillCheck.getCustomerid(),map.get("flag").equals(true));
            addJSONObject(map);
        }else{
            Map map = salesBillCheckService.addSalesBillCheck(salesBillCheck);
            addLog("新增销售核对信息 客户编号:"+salesBillCheck.getCustomerid(),map.get("flag").equals(true));
            addJSONObject(map);
        }
        return SUCCESS;
    }

    /**
     * 导出销售单据核对
     * @throws Exception
     * @author panxiaoxiao
     * @date May 16, 2014
     */
    public void exportSalesBillCheckList()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
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
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编码");
        firstMap.put("customername", "客户名称");
        firstMap.put("salesamount", "销售金额");
        firstMap.put("billnums", "单据数");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        List<SalesBillCheck> list = salesBillCheckService.getExportSalesBillCheckList(pageMap);
        for(SalesBillCheck salesBillCheck : list){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2 = PropertyUtils.describe(salesBillCheck);
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
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 全局导出列表数据
     * @throws Exception
     * @author panxiaoxiao
     * @date May 16, 2014
     */
    public void autoExportSalesBillCheckList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        String onedayflag = null != queryMap.get("onedayflag") ? (String)queryMap.get("onedayflag") : "";
        if("1".equals(onedayflag)){
            String businessdate1 = (String)queryMap.get("businessdate1");
            queryMap.put("businessdate2",businessdate1);
        }
        pageMap.setCondition(queryMap);
        PageData pageData = salesBillCheckService.showSalesBillCheckData(pageMap);
        List list = pageData.getList();
        ExcelUtils.exportAnalysExcel(map,list);

    }

    /**
     * 导入销售单据核对
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 16, 2014
     */
    public String importSalesBillCheckList()throws Exception{
        Map<String,Object> retMap = new HashMap<String,Object>();
        try {
            String clazz = "salesBillCheckService",meth = "addDRSalesBillCheck",module = "account",pojo = "SalesBillCheck";
            Object object2 = SpringContextUtils.getBean(clazz);
            Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
            Method[] methods = object2.getClass().getMethods();
            Method method = null;
            for(Method m : methods){
                if(m.getName().equals(meth)){
                    method = m;
                }
            }

            List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
            List<String> paramList2 = new ArrayList<String>();
            for(String str:paramList){
                if("客户编码".equals(str)){
                    paramList2.add("customerid");
                }
                else if("客户名称".equals(str)){
                    paramList2.add("customername");
                }
                else if("业务日期".equals(str)){
                    paramList2.add("businessdate");
                }
                else if("录入销售金额".equals(str)){
                    paramList2.add("salesamount");
                }
                else if("录入单据数".equals(str)){
                    paramList2.add("billnums");
                }
                else if("备注".equals(str)){
                    paramList2.add("remark");
                }
                else{
                    paramList2.add("null");
                }
            }

            if(paramList.size() == paramList2.size()){
                List result = new ArrayList();
                List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
                if(list.size() != 0){
                    Map detialMap = new HashMap();
                    for(Map<String, Object> map4 : list){
                        Object object = entity.newInstance();
                        Field[] fields = entity.getDeclaredFields();
                        //获取的导入数据格式转换
                        DRCastTo(map4,fields);
                        //BeanUtils.populate(object, map4);
                        PropertyUtils.copyProperties(object, map4);
                        result.add(object);
                    }
                    if(result.size() != 0){
                        retMap = excelService.insertSalesOrder(object2, result, method);
                    }
                }else{
                    retMap.put("excelempty", true);
                }
            }
            else{
                retMap.put("versionerror", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("error", true);
        }
        addJSONObject(retMap);
        return SUCCESS;
    }
}

