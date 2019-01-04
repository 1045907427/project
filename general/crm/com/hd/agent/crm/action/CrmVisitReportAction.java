/**
 * @(#)CrmVisitReportAction.java
 * @author limin
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-8-25 limin 创建版本
 */
package com.hd.agent.crm.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.crm.service.ICrmVisitReportService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户拜访报表Action
 *
 * Created by limin on 2016/8/25.
 */
public class CrmVisitReportAction extends BaseFilesAction {

    private ICrmVisitReportService crmVisitReportService;

    public ICrmVisitReportService getCrmVisitReportService() {
        return crmVisitReportService;
    }

    public void setCrmVisitReportService(ICrmVisitReportService crmVisitReportService) {
        this.crmVisitReportService = crmVisitReportService;
    }

    /**
     * 业务员巡店汇总报表
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    public String visitReportPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 查询业务员巡店汇总报表数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    public String getVisitReportData() throws Exception {

        Map condition = CommonUtils.changeMap(request.getParameterMap());
        String month = (String) condition.get("month");
        if (StringUtils.isNotEmpty(month)) {
            String startdate = month + "-01";
            String enddate = month + "-99";
            condition.put("startdate", startdate);
            condition.put("enddate", enddate);
        }

        pageMap.setCondition(condition);
        PageData data = crmVisitReportService.getVisitReportData(pageMap);

        addJSONObjectWithFooter(data);
        return SUCCESS;
    }

    /**
     * 导出业务员巡店汇总报表数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    public void exportVisitReportData() throws Exception {

        Map condition = CommonUtils.changeMap(request.getParameterMap());
        String month = (String) condition.get("month");
        if (StringUtils.isNotEmpty(month)) {
            String startdate = month + "-01";
            String enddate = month + "-99";
            condition.put("startdate", startdate);
            condition.put("enddate", enddate);
        }

        String groupcols = (String) condition.get("groupcols");
        if (StringUtils.isEmpty(groupcols)) {
            groupcols = "personid";
        }

        String sort = (String) condition.get("sort");
        String order = (String) condition.get("order");
        if (StringUtils.isEmpty(sort) || StringUtils.isEmpty(order)) {
            if (condition.containsKey("sort")) {
                condition.remove("sort");
            }
            if (condition.containsKey("order")) {
                condition.remove("order");
            }
            pageMap.setOrderSql(groupcols.split(",")[0] + " asc ");
        }
        pageMap.setCondition(condition);
        String title = "";
        if (condition.containsKey("excelTitle")) {
            title = condition.get("excelTitle").toString() + "-" + month;
        } else {
            title = "业务员巡店汇总报表-" + month;
        }

        //数据转换，list转化符合excel导出的数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();

        String category = (String) condition.get("category");

        if (groupcols.indexOf("personid") >= 0) {
            firstMap.put("personname", "业务员");
            firstMap.put("deptname", "所属部门");
        }

        if (groupcols.indexOf("customersort") >= 0) {
            firstMap.put("customersortname", "客户分类");
        }

        if (groupcols.indexOf("salesarea") >= 0) {
            firstMap.put("salesareaname", "销售区域");
        }

        int days = getMontyDays(month);

        if ("1".equals(category)) {

            title = title + "-线路内";

            for (int i = 1; i <= days; i++) {
                firstMap.put("isplan" + (i < 10 ? "0" + i : i), i + "号" + (getWeekday(month + "-" + (i < 10 ? "0" + i : i))) + "线路内数量");
                firstMap.put("plan" + (i < 10 ? "0" + i : i), i + "号" + (getWeekday(month + "-" + (i < 10 ? "0" + i : i))) + "计划数量");
                firstMap.put("rate" + (i < 10 ? "0" + i : i), i + "号" + (getWeekday(month + "-" + (i < 10 ? "0" + i : i))) + "完成率");
            }

            firstMap.put("isplan", "线路内数量（合计）");
            firstMap.put("plan", "计划数量（合计）");
            firstMap.put("rate", "完成率（合计）");

        } else if ("2".equals(category)) {

            title = title + "-线路外";

            for (int i = 1; i <= days; i++) {
                firstMap.put("noplan" + (i < 10 ? "0" + i : i), i + "号" + (getWeekday(month + "-" + (i < 10 ? "0" + i : i))) + "线路外数量");
            }
            firstMap.put("total_noplan", "合计");

        } else if ("3".equals(category)) {

            title = title + "-全部";

            for (int i = 1; i <= days; i++) {
                firstMap.put("day" + (i < 10 ? "0" + i : i) + "_sum", i + "号" + (getWeekday(month + "-" + (i < 10 ? "0" + i : i))) + "全部数量");
            }
            firstMap.put("total_sum", "合计");
        }
        result.add(firstMap);

        pageMap.setRows(99999999);
        PageData pageData = crmVisitReportService.getVisitReportData(pageMap);
        List<Map> list = pageData.getList();
        List<Map> footer = pageData.getFooter();
        list.addAll(footer);
        for (Map record : list) {
            if (null != record) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
//                map2 = PropertyUtils.describe(record);
                map2 = record;
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map2.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map2.entrySet()) {
                            if (fentry.getKey().equals(entry.getKey())) {
                                objectCastToRetMap(retMap, entry);
                            }
                        }
                    } else {
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 获取月份天数
     *
     * @param monthStr
     * @return
     * @author limin
     * @date Aug 26, 2016
     */
    private int getMontyDays(String monthStr) {

        int year = Integer.parseInt(monthStr.substring(0, 4));
        int month = Integer.parseInt(monthStr.substring(5, 7));

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 判断当前日期是几
     *
     * @param day 修要判断的时间<br>
     * @Exception 发生异常<br>
     */
    private String getWeekday(String day) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String dayNames[] = {"(日)", "(一)", "(二)", "(三)", "(四)", "(五)", "(六)"};
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(day));
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        return dayNames[weekday - 1];
    }

    /**
     * 业务员巡店汇总报表
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    public String personReportPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 查询业务员巡店汇总报表数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    public String getPersonReportData() throws Exception {
        Map condition = CommonUtils.changeMap(request.getParameterMap());
        String month = (String) condition.get("month");
        if (StringUtils.isNotEmpty(month)) {
            String startdate = month + "-01";
            String enddate = month + "-99";
            condition.put("startdate", startdate);
            condition.put("enddate", enddate);
        }

        pageMap.setCondition(condition);
        PageData data = crmVisitReportService.getPersonReportData(pageMap);
        addJSONObject(data);
        return SUCCESS;
    }

    /**
     * 导出业务员巡店汇总报表数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 25, 2016
     */
    public void exportPersonReportData() throws Exception {

        Map condition = CommonUtils.changeMap(request.getParameterMap());
        String month = (String) condition.get("month");
        if (StringUtils.isNotEmpty(month)) {
            String startdate = month + "-01";
            String enddate = month + "-99";
            condition.put("startdate", startdate);
            condition.put("enddate", enddate);
        }

        String groupcols = (String) condition.get("groupcols");
        if (StringUtils.isEmpty(groupcols)) {
            groupcols = "personid";
        }

        String sort = (String) condition.get("sort");
        String order = (String) condition.get("order");
        if (StringUtils.isEmpty(sort) || StringUtils.isEmpty(order)) {
            if (condition.containsKey("sort")) {
                condition.remove("sort");
            }
            if (condition.containsKey("order")) {
                condition.remove("order");
            }
            pageMap.setOrderSql(groupcols.split(",")[0] + " asc ");
        }
        pageMap.setCondition(condition);
        String title = "";
        if (condition.containsKey("excelTitle")) {
            title = condition.get("excelTitle").toString() + "-" + month;
        } else {
            title = "业务员拜访客户汇总报表-" + month;
        }

        //数据转换，list转化符合excel导出的数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();

        String category = (String) condition.get("category");

        firstMap.put("personname", "业务员");
        firstMap.put("deptname", "所属部门");
//        if(groupcols.contains("personid")) {
//            firstMap.put("personname", "业务员");
//            firstMap.put("deptname", "所属部门");
//        }
//
//        if(groupcols.contains("customersort")) {
//            firstMap.put("customersortname", "客户分类");
//        }
//
//        if(groupcols.contains("salesarea")) {
//            firstMap.put("salesareaname", "销售区域");
//        }

        int days = getMontyDays(month);
        for (int i = 1; i <= days; i++) {
            firstMap.put("day" + i, i + "号" + (getWeekday(month + "-" + (i < 10 ? "0" + i : i))));
        }
        firstMap.put("total", "合计");
        result.add(firstMap);

        pageMap.setRows(99999999);
        PageData pageData = crmVisitReportService.getPersonReportData(pageMap);
        List<Map> list = pageData.getList();
        //List<Map> footer = pageData.getFooter();
        //list.addAll(footer);
        BigDecimal sixty = new BigDecimal(60);
        for (Map record : list) {
            if (null != record) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
//                map2 = PropertyUtils.describe(record);
                map2 = record;
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map2.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        if (fentry.getKey().contains("day") || fentry.getKey().contains("total")) {
                            retMap.put(fentry.getKey(), ((BigDecimal) map2.get(fentry.getKey())).divide(sixty, 2, BigDecimal.ROUND_HALF_UP));
                        } else {
                            for (Map.Entry<String, Object> entry : map2.entrySet()) {
                                if (fentry.getKey().equals(entry.getKey())) {
                                    objectCastToRetMap(retMap, entry);
                                }
                            }
                        }
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }
}
