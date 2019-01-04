package com.hd.agent.crm.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.crm.service.ICrmTerminalSalesService;
import com.hd.agent.crm.service.ICustomerStorageOrderService;
import com.hd.agent.crm.service.ICustomerSummaryService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lin_xx on 2016/10/18.
 */
public class CrmDataPortAction extends BaseFilesAction {

    public ICustomerSummaryService customerSummaryService;

    private ICrmTerminalSalesService crmTerminalSalesService;

    public ICustomerSummaryService getCustomerSummaryService() {
        return customerSummaryService;
    }

    public void setCustomerSummaryService(ICustomerSummaryService customerSummaryService) {
        this.customerSummaryService = customerSummaryService;
    }

    public ICrmTerminalSalesService getCrmTerminalSalesService() {
        return crmTerminalSalesService;
    }

    public void setCrmTerminalSalesService(ICrmTerminalSalesService crmTerminalSalesService) {
        this.crmTerminalSalesService = crmTerminalSalesService;
    }

     /**
      *客户库存销量报表页面
      * @author lin_xx
      * @date 2018/02/06
      */
    public String showCustStorageReportPage() throws Exception {
        return SUCCESS;
    }
    /**
     *客户库存销量报表数据
     * @author lin_xx
     * @date 2018/02/06
     */
    public String getCustomerSummaryReportGroupData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = customerSummaryService.getCustomerSummaryReportGroupData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 导出客户库存报表数据
     * @author lin_xx
     * @date 2018/02/06
     */
    public void exportCustomerSummaryReportGroupData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = customerSummaryService.getCustomerSummaryReportGroupData(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map, list);

    }

    /**
     *客户库存明细报表页面
     * @author lin_xx
     * @date 2016/10/18
     */
    public String showCustStorageDetailReportPage() throws Exception {
        return SUCCESS;
    }
     /**
      *客户库存明细报表数据
      * @author lin_xx
      * @date 2016/10/18
      */
    public String getCustomerSummaryData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = customerSummaryService.getCustomerSummaryReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }
     /**
      * 客户库存清除选择客户界面
      * @author lin_xx
      * @date 2016/10/28
      */
    public String getCustomerSummaryClearPage() throws Exception {
        return SUCCESS ;
    }
    /**
     * 清除客户库存
     * @author lin_xx
     * @date 2016/11/1
     */
    public String clearCustomerSummary() throws Exception {
        String customerid = request.getParameter("customerid");
        Map map = customerSummaryService.clearCustomerSummary(customerid);
        Integer count = (Integer) map.get("count");
        String auditOrderid = (String) map.get("auditOrderid");
        String msg = "";
        if(count == 0){
            msg = "客户:" + customerid + "暂无库存商品";
        }else if(count > 0){
            if(StringUtils.isNotEmpty(auditOrderid)){
                msg = "客户:" + customerid + "库存清除成功，商品清除成功"+count+"条,反审客户库存单据:"+auditOrderid;
            }else{
                msg = "客户:" + customerid + "库存清除成功，商品清除成功"+count+"条";
            }
        }else{
            msg = "客户:" + customerid + "库存清除失败，失败"+count+"条";
        }
        map.put("msg",msg);
        addJSONObject(map);
        addLog(msg);
        return SUCCESS;
    }
     /**
      * 跳转到客户库存同步界面
      * @author lin_xx
      * @date 2016/10/26
      */
    public String getCustomerSummaryPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today",today);
        return SUCCESS;
    }
     /**
      * 获取客户最新库存时间
      * @author lin_xx
      * @date 2016/10/26
      */
    public String getLastSummaryDayForCustomer() throws Exception {
        String customerid = request.getParameter("customerid");
        String date = customerSummaryService.getLastSummaryDay(customerid);
        Map map = new HashMap();
        map.put("date",date);
        addJSONObject(map);
        return SUCCESS;
    }

     /**
      * 客户更新库存页面
      * @author lin_xx
      * @date 2016/10/25
      */
    public String updateCustomerSummary() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        String customerid = (String) map.get("customerid");
        pageMap.setCondition(map);
        Map m = customerSummaryService.updateSummaryByCustomer(pageMap);
        Integer count = (Integer) m.get("count");
        String msg = "";
        if(count == 0){
            msg = "客户:" + customerid + "暂无更新";
        }else if(m.containsKey("flag")){
            String successid = m.get("successid").toString();
            successid = successid.replace("[","");
            successid = successid.replace("]","");
            msg = "客户:" + customerid + "库存更新成功，成功"+count+"条，成功商品编号："+successid;
        }else{
            String failureid = m.get("failureid").toString();
            failureid = failureid.replace("[","");
            failureid = failureid.replace("]","");
            msg = "客户:" + customerid + "库存更新失败，失败"+count+"条,失败商品编号："+failureid;
        }
        m.put("msg",msg);
        addJSONObject(m);
        addLog(msg);
        return SUCCESS;
    }
     /**
      * 跳转到客户销量同步界面
      * @author lin_xx
      * @date 2016/10/27
      */
    public String getCrmSalesSyncPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today",today);
        return SUCCESS;
    }

     /**
      * 客户销量同步更新
      * @author lin_xx
      * @date 2016/10/27
      */
    public String crmSalesSync() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        String customerid = (String) map.get("customerid");
        pageMap.setCondition(map);
        Map m = customerSummaryService.crmSalesSync(pageMap);
        boolean flag = (Boolean) m.get("flag");
        Integer count = (Integer) m.get("count");
        String msg = "";

        if(count == 0){
            msg = "客户:" + customerid + "销量暂无更新";
        }else if(flag){
            msg = "客户:" + customerid + "销量更新成功，成功"+count+"条，成功客户销量编号："+(String)m.get("orderids");
        }else{
            msg = "客户:" + customerid + "销量更新失败，失败"+count+"条";
        }
        m.put("msg",msg);
        addJSONObject(m);
        addLog(msg);

        return SUCCESS;
    }


     /**
      * 导出 客户库存明细报表
      * @author lin_xx
      * @date 2016/10/20
      */
    public void exportCustomerStorageData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = customerSummaryService.getCustomerSummaryReportData(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map, list);

    }
     /**
      * 客户库存周转天数报表
      * @author lin_xx
      * @date 2016/11/1
      */
    public String getCustomerSummaryRevolutionReportPage() throws Exception{
        String preMonthLastDay = CommonUtils.getPreMonthLastDay();
        request.setAttribute("preMonthLastDay",preMonthLastDay);
        return SUCCESS;
    }
     /**
      * 客户库存周转天数数据
      * @author lin_xx
      * @date 2016/11/2
      */
    public String getCustomerSummaryRevolutionReportData() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = customerSummaryService.getCustomerSummaryRevolutionReportData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }
     /**
      * 导出 客户库存周转天数数据
      * @author lin_xx
      * @date 2016/11/2
      */
    public void exportCustomerSummaryRevolution() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData =  customerSummaryService.getCustomerSummaryRevolutionReportData(pageMap);
        List list = pageData.getList();
        ExcelUtils.exportAnalysExcel(map, list);
    }
    /**
     * 客户销量报表页面
     * @author lin_xx
     * @date 2016/10/19
     */
    public String showTerminalSalesOrderReportPage() throws Exception {
        return SUCCESS;
    }
     /**
      * 客户销量报表数据
      * @author lin_xx
      * @date 2016/10/19
      */
    public String getTerminalSalesOrderReportData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = crmTerminalSalesService.getTerminalSalesOrderReportData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }
     /**
      *导出 客户销量报表
      * @author lin_xx
      * @date 2016/10/20
      */
    public void  exportCrmSalesOrderData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = crmTerminalSalesService.getTerminalSalesOrderReportData(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map, list);
    }
     /**
      * 查看客户商品的历史库存
      * @author lin_xx
      * @date 2016/11/17
      */
    public String showCustomerSummaryHistory() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        List<Map> list = customerSummaryService.getCustomerSummaryForGoodsHistoryList(map);
        String listjson = JSONUtils.listToJsonStr(list);
        request.setAttribute("listjson",listjson);
        return SUCCESS;
    }

}
