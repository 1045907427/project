package com.hd.agent.hr.action;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.hr.service.IStatisticService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class StatisticAction extends BaseAction {

    private IStatisticService statisticService;

    public IStatisticService getStatisticService() {
        return statisticService;
    }

    public void setStatisticService(IStatisticService statisticService) {
        this.statisticService = statisticService;
    }

    /**
     * 请假单列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 29, 2015
     */
    public String restListPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 查询请假单List
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 29, 2015
     */
    public String selectRestList() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("businessdate", CommonUtils.getTodayDataStr());

        String val07 = (String) map.get("val07");
        // map.put("val07", new ArrayList<String>());
        if(StringUtils.isNotEmpty(val07)) {

            List<String> temp = new ArrayList<String>();
            for(String str : val07.split(",")) {

                temp.add(str);
            }
            map.put("val07", temp);
        }

        String status = (String) map.get("status");
        // map.put("val07", new ArrayList<String>());
        if(StringUtils.isNotEmpty(status)) {

            List<String> temp = new ArrayList<String>();
            for(String str : status.split(",")) {

                temp.add(str);
            }
            map.put("status", temp);
        }

        pageMap.setCondition(map);

        PageData data = statisticService.selectRestList(pageMap);

        addJSONObject(data);
        return SUCCESS;
    }

    /**
     * 导出请假统计报表
     * @throws Exception
     * @author limin
     * @date Jun 30, 2015
     */
    public void exportRestList() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("businessdate", CommonUtils.getTodayDataStr());

        String val07 = (String) map.get("val07");
        // map.put("val07", new ArrayList<String>());
        if(StringUtils.isNotEmpty(val07)) {

            List<String> temp = new ArrayList<String>();
            for(String str : val07.split(",")) {

                temp.add(str);
            }
            map.put("val07", temp);
        }

        String status = (String) map.get("status");
        if(StringUtils.isNotEmpty(status)) {

            List<String> temp = new ArrayList<String>();
            for(String str : status.split(",")) {

                temp.add(str);
            }
            map.put("status", temp);
        }

        pageMap.setCondition(map);
        pageMap.setRows(99999999);

        PageData pageData = statisticService.selectRestList(pageMap);

        String title = null;
        if(map.containsKey("excelTitle")){

            title = (String) map.get("excelTitle");
        } else {

            title = "请假统计报表-" + CommonUtils.getDataNumberSeconds();
        }

        ExcelUtils.exportExcel(exportRestListDataFilter(pageData), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(请假报表)
     * @param data
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 1, 2015
     */
    private List<Map<String, Object>> exportRestListDataFilter(PageData data) throws Exception{

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();

        Map<String, Object> header = new LinkedHashMap<String, Object>();
        header.put("applyuser", "申请人");
        header.put("applydate", "申请时间");
        header.put("dept", "所在部门");
        header.put("workjob", "岗位");
        header.put("delegateuser", "职务代理人");
        header.put("delegatephone", "代理人电话");
        header.put("resttype", "请假类型");
        header.put("begindate", "请假时间（开始）");
        header.put("enddate", "请假时间（结束）");
        header.put("day", "请假天数");
        header.put("reason", "请假理由");
        header.put("processid", "OA号");
        header.put("status", "OA状态");

        result.add(header);

        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) data.getList();

        for(HashMap<String, Object> item2 : list){

            Map<String, Object> content = new LinkedHashMap<String, Object>();

            content.put("applyuser", item2.get("applyuser"));
            content.put("applydate", item2.get("applydate"));
            content.put("dept", item2.get("dept"));
            content.put("workjob", item2.get("workjob"));
            content.put("delegateuser", item2.get("delegateuser"));
            content.put("delegatephone", item2.get("delegatephone"));
            content.put("resttype", item2.get("resttype"));
            content.put("begindate", item2.get("begindate"));
            content.put("enddate", item2.get("enddate"));
            content.put("day", item2.get("day"));
            content.put("reason", item2.get("reason"));
            content.put("processid", item2.get("processid"));
            content.put("status", "1".equals(item2.get("status")) ? "未完结" : "已完结");

            result.add(content);
        }

        return result;
    }

    /**
     * 加班申请单列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 29, 2015
     */
    public String overtimeListPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 查询请假单List
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 29, 2015
     */
    public String selectOvertimeList() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("businessdate", CommonUtils.getTodayDataStr());

        String status = (String) map.get("status");
        if(StringUtils.isNotEmpty(status)) {

            List<String> temp = new ArrayList<String>();
            for(String str : status.split(",")) {

                temp.add(str);
            }
            map.put("status", temp);
        }

        pageMap.setCondition(map);

        PageData data = statisticService.selectOvertimeList(pageMap);

        addJSONObject(data);
        return SUCCESS;
    }

    /**
     * 导出请假统计报表
     * @throws Exception
     * @author limin
     * @date Jun 30, 2015
     */
    public void exportOvertimeList() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("businessdate", CommonUtils.getTodayDataStr());

        String status = (String) map.get("status");
        if(StringUtils.isNotEmpty(status)) {

            List<String> temp = new ArrayList<String>();
            for(String str : status.split(",")) {

                temp.add(str);
            }
            map.put("status", temp);
        }

        pageMap.setCondition(map);
        pageMap.setRows(99999999);

        PageData pageData = statisticService.selectOvertimeList(pageMap);

        String title = null;
        if(map.containsKey("excelTitle")){

            title = (String) map.get("excelTitle");

        } else {

            title = "加班统计报表-" + CommonUtils.getDataNumberSeconds();
        }

        ExcelUtils.exportExcel(exportOvertimeListDataFilter(pageData), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(请假报表)
     * @param data
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 1, 2015
     */
    private List<Map<String, Object>> exportOvertimeListDataFilter(PageData data) throws Exception{

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();

        Map<String, Object> header = new LinkedHashMap<String, Object>();
        header.put("val03", "申请人");
        header.put("val01", "所在部门");
        header.put("val02", "申请时间");
        header.put("val04", "加班时间(开始)");
        header.put("val05", "加班时间(结束)");
        header.put("val06", "加班天数");
        header.put("val07", "加班地点");
        header.put("val08", "工作内容");
        header.put("val18", "OA号");
        header.put("val19", "OA状态");

        result.add(header);

        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) data.getList();

        for(HashMap<String, Object> item2 : list){

            Map<String, Object> content = new LinkedHashMap<String, Object>();

//            content.put("val01", item2.get("val03"));
//            content.put("val02", item2.get("val01"));
//            content.put("val03", item2.get("val02"));
//            content.put("val04", item2.get("val04"));
//            content.put("val05", item2.get("val05"));
//            content.put("val06", item2.get("val06"));
//            content.put("val16", item2.get("val16"));
//            content.put("val17", "1".equals(item2.get("val17")) ? "未完结" : "已完结");


            content.put("val01", item2.get("val03"));
            content.put("val02", item2.get("val01"));
            content.put("val03", item2.get("val02"));
            content.put("val04", item2.get("val04"));
            content.put("val05", item2.get("val05"));
            content.put("val06", item2.get("val06"));
            content.put("val07", item2.get("val07"));
            content.put("val08", item2.get("val08"));
            content.put("val18", item2.get("val18"));
            content.put("val19", "1".equals(item2.get("val19")) ? "未完结" : "已完结");


            result.add(content);
        }

        return result;
    }

}
