package com.hd.agent.salestarget.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.CustomerSort;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.salestarget.service.ISalesTargetReportService;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by master on 2016/8/6.
 */
public class SalesTargetReportAction extends BaseFilesAction {
    private ISalesTargetReportService salesTargetReportService;

    public ISalesTargetReportService getSalesTargetReportService() {
        return salesTargetReportService;
    }

    public void setSalesTargetReportService(ISalesTargetReportService salesTargetReportService) {
        this.salesTargetReportService = salesTargetReportService;
    }


    /**
     * 销售目标跟踪报表页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-05
     */
    public String showSalesTargetTraceReportPage() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int iYear=calendar.get(Calendar.YEAR);
        int iMonth=calendar.get(Calendar.MONTH)+1;
        String businessdate=dateFormat.format(calendar.getTime());
        Map timeScheduleMap = salesTargetReportService.getCalcYearAndMonthTimeSchedule(businessdate);
        BigDecimal monthTimeSchedule=null;
        BigDecimal yearTimeSchedule=null;
        if(timeScheduleMap!=null){
            monthTimeSchedule=(BigDecimal)timeScheduleMap.get("monthTimeSchedule");
            if(monthTimeSchedule!=null){
                monthTimeSchedule=monthTimeSchedule.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else{
                monthTimeSchedule=BigDecimal.ZERO;
            }
            yearTimeSchedule=(BigDecimal)timeScheduleMap.get("yearTimeSchedule");
            if(yearTimeSchedule!=null){
                yearTimeSchedule=yearTimeSchedule.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else{
                yearTimeSchedule=BigDecimal.ZERO;
            }
        }
        request.setAttribute("today", businessdate);
        request.setAttribute("lastyear", iYear-1);
        request.setAttribute("iMonth", iMonth);
        request.setAttribute("monthTimeSchedule", monthTimeSchedule + "%");
        request.setAttribute("yearTimeSchedule", yearTimeSchedule + "%");
        return SUCCESS;
    }

    /**
     * 销售目标跟踪报表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-05
     */
    public String showSalesTargetTraceReportData() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("isExportData")){
            map.remove("isExportData");
        }
        pageMap.setCondition(map);
        PageData pageData=salesTargetReportService.showSalesTargetTraceReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 导出销售目标跟踪报表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-05
     */
    public void exportSalesTargetTraceReportData() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isExportData", "true");
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
        PageData pageData =salesTargetReportService.showSalesTargetTraceReportData(pageMap);
        ExcelUtils.exportExcel(exportSalesTargetTraceReportDataFilter(pageData.getList()), title);
    }
    /**
     * 数据转换，list专程符合excel导出的数据格式(销售目标跟踪报表数据)
     * @param list
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-05
     */
    private List<Map<String, Object>> exportSalesTargetTraceReportDataFilter(List<Map> list) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "更新日期");
        firstMap.put("month", "查询月份");
        firstMap.put("year", "对比年份");
        firstMap.put("monthtimeschedule", "月进度");
        firstMap.put("yeartimeschedule", "年进度");
        firstMap.put("subjectname", "科目");
        for(int i=1;i<13;i++){
            firstMap.put("month_" + i, i + "月");
        }
        firstMap.put("summarycolumn", "合计");
        result.add(firstMap);

        if(list.size() != 0){
            for(Map<String,Object> map : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map.entrySet()){
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
     * 销售毛利跟踪报表页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-05
     */
    public String showSalesTargetGrossTraceReportPage() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int iYear=calendar.get(Calendar.YEAR);
        int iMonth=calendar.get(Calendar.MONTH)+1;
        String businessdate=dateFormat.format(calendar.getTime());
        Map timeScheduleMap = salesTargetReportService.getCalcYearAndMonthTimeSchedule(businessdate);
        BigDecimal monthTimeSchedule=null;
        BigDecimal yearTimeSchedule=null;
        if(timeScheduleMap!=null){
            monthTimeSchedule=(BigDecimal)timeScheduleMap.get("monthTimeSchedule");
            if(monthTimeSchedule!=null){
                monthTimeSchedule=monthTimeSchedule.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else{
                monthTimeSchedule=BigDecimal.ZERO;
            }
            yearTimeSchedule=(BigDecimal)timeScheduleMap.get("yearTimeSchedule");
            if(yearTimeSchedule!=null){
                yearTimeSchedule=yearTimeSchedule.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else{
                yearTimeSchedule=BigDecimal.ZERO;
            }
        }
        request.setAttribute("today", businessdate);
        request.setAttribute("lastyear", iYear-1);
        request.setAttribute("iMonth", iMonth);
        request.setAttribute("monthTimeSchedule",monthTimeSchedule+"%");
        request.setAttribute("yearTimeSchedule",yearTimeSchedule+"%");
        return SUCCESS;
    }

    /**
     * 销售毛利跟踪报表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-05
     */
    public String showSalesTargetGrossTraceReportData() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("isExportData")){
            map.remove("isExportData");
        }
        pageMap.setCondition(map);
        PageData pageData=salesTargetReportService.showSalesTargetGrossTraceReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }
    /**
     * 导出销售毛利跟踪报表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-05
     */
    public void exportSalesTargetGrossTraceReportData() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isExportData", "true");
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

        PageData pageData =salesTargetReportService.showSalesTargetGrossTraceReportData(pageMap);
        ExcelUtils.exportExcel(exportSalesTargetGrossTraceReportDataFilter(pageData.getList()), title);
    }


    /**
     * 数据转换，list专程符合excel导出的数据格式(销售毛利跟踪报表数据)
     * @param list
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-05
     */
    private List<Map<String, Object>> exportSalesTargetGrossTraceReportDataFilter(List<Map> list) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "更新日期");
        firstMap.put("month", "查询月份");
        firstMap.put("year", "对比年份");
        firstMap.put("monthtimeschedule", "月进度");
        firstMap.put("yeartimeschedule", "年进度");
        firstMap.put("subjectname", "科目");
        for(int i=1;i<13;i++){
            firstMap.put("month_" + i, i + "月");
        }
        firstMap.put("summarycolumn", "合计");
        result.add(firstMap);

        if(list.size() != 0){
            for(Map<String,Object> map : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map.entrySet()){
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
     * 按品牌月度目标分析报表页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-4-7
     */
    public String showSalesTargetBrandMonthAnalyzeReportPage() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int iYear=calendar.get(Calendar.YEAR);
        int iMonth=calendar.get(Calendar.MONTH)+1;
        String businessdate=dateFormat.format(calendar.getTime());
        BigDecimal timeSchedule = salesTargetReportService.getCalcMonthAnalyzeTimeSchedule(iMonth, businessdate);
        timeSchedule=timeSchedule.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        request.setAttribute("today", businessdate);
        request.setAttribute("lastyear", iYear-1);
        request.setAttribute("iMonth", iMonth);
        request.setAttribute("timeSchedule",timeSchedule+"%");
        return SUCCESS;
    }
    /**
     * 按品牌月度目标报表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-4-7
     */
    public String showSalesTargetBrandMonthAnalyzeReportData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        if(null!=map ){
            if(map.containsKey("isPageflag")){
                map.remove("isPageflag");
            }
        }

        pageMap.setCondition(map);

        PageData pageData = salesTargetReportService.showSalesTargetBrandMonthAnalyzeReportData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 导出按品牌月度目标报表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-4-8
     */
    public String exportSalesTargetBrandMonthAnalyzeReportData() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }else{
            title = "list";
        }
        if(org.apache.commons.lang3.StringUtils.isEmpty(title)){
            title = "list";
        }
        String brandid=(String)map.get("brandid");

        pageMap.setCondition(map);

        PageData pageData = salesTargetReportService.showSalesTargetBrandMonthAnalyzeReportData(pageMap);
        ExcelUtils.exportExcel(exportSalesTargetBrandMonthAnalyzeReportDataFilter(pageData.getList(), brandid), title);
        return null;
    }
    /**
     * 按品牌月度目标报表数据 导出
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportSalesTargetBrandMonthAnalyzeReportDataFilter(List<Map> list,String brandid) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "更新日期");
        firstMap.put("month", "查询月份");
        firstMap.put("year", "对比年份");
        firstMap.put("timeschedule", "时间进度");
        firstMap.put("subjectsort", "分类");
        firstMap.put("subjectname", "科目");
        if(null!=brandid && !"".equals(brandid)){
            Map paramMap=new HashMap();
            paramMap.put("idarrs",brandid);
            List<Brand> brandList=getBaseGoodsService().getBrandListByMap(paramMap);
            if(null!=brandList && brandList.size()>0){
                for(Brand itemBrand: brandList){
                    firstMap.put("target_"+itemBrand.getId(), itemBrand.getName());
                }
            }
        }
        firstMap.put("summarycolumn", "合计");
        firstMap.put("realdiffcolumn", "真实差异");

        result.add(firstMap);

        if(null!=list && list.size() > 0){
            for(Map<String,Object> map : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map.entrySet()){
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
     * 按渠道月度目标分析报表页面
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-4-7
     */
    public String showSalesTargetCustomerSortMonthAnalyzeReportPage() throws Exception{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int iYear=calendar.get(Calendar.YEAR);
        int iMonth=calendar.get(Calendar.MONTH)+1;
        String businessdate=dateFormat.format(calendar.getTime());
        BigDecimal timeSchedule = salesTargetReportService.getCalcMonthAnalyzeTimeSchedule(iMonth, businessdate);
        timeSchedule=timeSchedule.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        request.setAttribute("today", businessdate);
        request.setAttribute("lastyear", iYear-1);
        request.setAttribute("iMonth", iMonth);
        request.setAttribute("timeSchedule",timeSchedule+"%");
        return SUCCESS;
    }
    /**
     * 按渠道月度目标报表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-4-7
     */
    public String showSalesTargetCustomerSortMonthAnalyzeReportData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        if(null!=map ){
            if(map.containsKey("isPageflag")){
                map.remove("isPageflag");
            }
        }

        pageMap.setCondition(map);

        PageData pageData = salesTargetReportService.showSalesTargetCustomerSortMonthAnalyzeReportData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 导出按渠道月度目标报表数据
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-4-8
     */
    public String exportSalesTargetCustomerSortMonthAnalyzeReportData() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }else{
            title = "list";
        }
        if(org.apache.commons.lang3.StringUtils.isEmpty(title)){
            title = "list";
        }
        String customersort=(String)map.get("customersort");

        pageMap.setCondition(map);

        PageData pageData = salesTargetReportService.showSalesTargetCustomerSortMonthAnalyzeReportData(pageMap);
        ExcelUtils.exportExcel(exportSalesTargetCustomerSortMonthAnalyzeReportDataFilter(pageData.getList(), customersort), title);
        return null;
    }
    /**
     * 按渠道月度目标报表数据 导出
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportSalesTargetCustomerSortMonthAnalyzeReportDataFilter(List<Map> list,String customersort) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "更新日期");
        firstMap.put("month", "查询月份");
        firstMap.put("year", "对比年份");
        firstMap.put("timeschedule", "时间进度");
        firstMap.put("subjectsort", "分类");
        firstMap.put("subjectname", "科目");
        if(null!=customersort && !"".equals(customersort)){
            Map paramMap=new HashMap();
            paramMap.put("idarrs",customersort);
            List<CustomerSort> customerSortList=getBaseSalesService().getCustomerSortListByMap(paramMap);
            if(null!=customerSortList && customerSortList.size()>0){
                for(CustomerSort item: customerSortList){
                    firstMap.put("target_"+item.getId(), item.getName());
                }
            }
        }
        firstMap.put("summarycolumn", "合计");
        firstMap.put("realdiffcolumn", "真实差异");


        result.add(firstMap);

        if(null!=list && list.size() > 0){
            for(Map<String,Object> map : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map.entrySet()){
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
     * 获取时间进度
     * @return
     * @throws Exception
     */
    public String getCalcMonthAnalyzeTimeSchedule() throws Exception{
        String month=request.getParameter("month");
        String businessdate=request.getParameter("businessdate");
        Map resultMap=new HashMap();
        if(businessdate==null || "".equals(businessdate.trim())){
            resultMap.put("flag",false);
            resultMap.put("msg","查询的更新日期不能为空");
            addJSONObject(resultMap);
        }
        Integer iMonth=null;
        if(month!=null &&!"".equals(month.trim())){
            iMonth=Integer.parseInt(month.trim());
        }
        BigDecimal resultDec=salesTargetReportService.getCalcMonthAnalyzeTimeSchedule(iMonth,businessdate);
        if(resultDec==null){
            resultDec=BigDecimal.ZERO;
        }
        resultDec=resultDec.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        resultMap.put("flag",true);
        resultMap.put("result",resultDec);
        addJSONObject(resultMap);
        return SUCCESS;
    }
    /**
     * 获取年或月时间进度
     * @return
     * @throws Exception
     */
    public String getCalcYearAndMonthTimeSchedule() throws Exception{
        String businessdate=request.getParameter("businessdate");
        Map resultMap=new HashMap();
        if(businessdate==null || "".equals(businessdate.trim())){
            resultMap.put("flag",false);
            resultMap.put("msg","查询的更新日期不能为空");
            addJSONObject(resultMap);
        }

        resultMap=salesTargetReportService.getCalcYearAndMonthTimeSchedule(businessdate.trim());
        if(resultMap!=null){
            BigDecimal monthTimeSchedule=(BigDecimal)resultMap.get("monthTimeSchedule");
            if(monthTimeSchedule!=null){
                monthTimeSchedule=monthTimeSchedule.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else{
                monthTimeSchedule=BigDecimal.ZERO;
            }
            BigDecimal yearTimeSchedule=(BigDecimal)resultMap.get("yearTimeSchedule");
            if(yearTimeSchedule!=null){
                yearTimeSchedule=yearTimeSchedule.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
            }else{
                yearTimeSchedule=BigDecimal.ZERO;
            }


            resultMap.put("monthTimeSchedule",monthTimeSchedule);
            resultMap.put("yearTimeSchedule",yearTimeSchedule);

            resultMap.put("flag",true);
        }else{
            resultMap.put("flag", false);
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }
    /**
     * 按品牌月度目标分解报表品牌列取值说明
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Oct 15, 2016
     */
    public String showBrandMonthAnalyzeReportColSelectHelp() throws Exception{
        return SUCCESS;
    }
    /**
     * 按渠道月度目标分解报表渠道列取值说明
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Oct 15, 2016
     */
    public String showCustomerSortMonthAnalyzeReportColSelectHelp() throws Exception{
        return SUCCESS;

    }
}
