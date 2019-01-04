/**
 * @(#)HrReportAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-9-1 limin 创建版本
 */
package com.hd.agent.hr.action;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.hr.service.IHrReportService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by limin on 2016/9/1.
 */
public class HrReportAction extends BaseAction {

    private IHrReportService hrReportService;

    public IHrReportService getHrReportService() {
        return hrReportService;
    }

    public void setHrReportService(IHrReportService hrReportService) {
        this.hrReportService = hrReportService;
    }

    /**
     * 签到报表页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 1, 2016
     */
    public String signinReportPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 查询签到报表数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 1, 2016
     */
    public String getSignReportData() throws Exception {

        Map condition = CommonUtils.changeMap(request.getParameterMap());

        String startdate = (String) condition.get("startdate");
        String enddate = (String) condition.get("enddate");
        if(StringUtils.isEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
            startdate = enddate;
            condition.put("startdate", startdate);
        }
        if(StringUtils.isNotEmpty(startdate) && StringUtils.isEmpty(enddate)) {
            enddate = startdate;
            condition.put("enddate", enddate);
        }
        if(StringUtils.isEmpty(startdate) && StringUtils.isEmpty(enddate)) {
            startdate = CommonUtils.getTodayDataStr();
            enddate = CommonUtils.getTodayDataStr();
            condition.put("startdate", startdate);
            condition.put("enddate", enddate);
        }

        int interval = CommonUtils.daysBetween(startdate, enddate);
        List intervalList = new ArrayList();
        for(int i = 0; i <= interval; i++) {
            intervalList.add(i);
        }
        condition.put("intervalList", intervalList);

        pageMap.setCondition(condition);
        PageData data = hrReportService.getSignReportData(pageMap);

        addJSONObject(data);
        return SUCCESS;
    }

    /**
     * 导出签到数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 1, 2016
     */
    public void exportSignReportData() throws Exception {


        Map condition = CommonUtils.changeMap(request.getParameterMap());

        String startdate = (String) condition.get("startdate");
        String enddate = (String) condition.get("enddate");
        if(StringUtils.isEmpty(startdate) && StringUtils.isNotEmpty(enddate)) {
            startdate = enddate;
            condition.put("startdate", startdate);
        }
        if(StringUtils.isNotEmpty(startdate) && StringUtils.isEmpty(enddate)) {
            enddate = startdate;
            condition.put("enddate", enddate);
        }
        if(StringUtils.isEmpty(startdate) && StringUtils.isEmpty(enddate)) {
            startdate = CommonUtils.getTodayDataStr();
            enddate = CommonUtils.getTodayDataStr();
            condition.put("startdate", startdate);
            condition.put("enddate", enddate);
        }

        int interval = CommonUtils.daysBetween(startdate, enddate);
        List intervalList = new ArrayList();
        for(int i = 0; i <= interval; i++) {
            intervalList.add(i);
        }
        condition.put("intervalList", intervalList);

        condition.put("sort", "businessdate,deptid,userid ");
        condition.put("order", "asc");
        pageMap.setCondition(condition);
        pageMap.setRows(99999999);

        PageData data = hrReportService.getSignReportData(pageMap);

        String title = "";
        if(condition.containsKey("excelTitle")){
            title = condition.get("excelTitle").toString();
        } else {
            title = "签到报表";
        }

        // 数据转换，list转化符合excel导出的数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();

        firstMap.put("businessdate", "日期");
        firstMap.put("username", "用户");
        firstMap.put("deptname", "部门");
        firstMap.put("ambegin", "上午上班");
        firstMap.put("amend", "上午下班");
        firstMap.put("pmbegin", "下午上班");
        firstMap.put("pmend", "下午下班");
        firstMap.put("outtime", "外出");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        List<Map> list = data.getList();

        for(Map record : list){
            if(null != record){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
//                map2 = PropertyUtils.describe(record);
                map2 = record;
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }
}
