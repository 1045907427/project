/**
 * @(#)PhoneAction.java
 * @author zhengziyong
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 28, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.hr.service.IMapService;
import com.hd.agent.phone.service.IPhoneService;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhengziyong
 */
public class PhoneAction extends BaseSalesAction {

    private IPhoneService phoneService;

    public IPhoneService getPhoneService() {
        return phoneService;
    }

    public void setPhoneService(IPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    private IMapService mapService;

    public IMapService getMapService() {
        return mapService;
    }

    public void setMapService(IMapService mapService) {
        this.mapService = mapService;
    }

    public String locationPage() throws Exception {
        String locationPoint = getSysParamValue("LocationPoint");
        if (StringUtils.isNotEmpty(locationPoint) && locationPoint.indexOf(",") > 0) {
            request.setAttribute("location1", locationPoint.split(",")[0]);
            request.setAttribute("location2", locationPoint.split(",")[1]);
        } else {
            request.setAttribute("location1", "121.431277");
            request.setAttribute("location2", "28.652722");
        }
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        List codeList = getBaseSysCodeService().showSysCodeListByType("employetype");
        request.setAttribute("codeList", codeList);
        return SUCCESS;
    }

    public String routeListPage() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        request.setAttribute("today", today);
        return SUCCESS;
    }

    public String getRouteList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = (phoneService.getRouteDistanceData(pageMap));
        addJSONObject(pageData);
        return SUCCESS;
    }

    public String routePage() throws Exception {
        String userid = request.getParameter("u");
        String date = request.getParameter("t");
        request.setAttribute("userid", userid);
        request.setAttribute("date", date);
        String locationPoint = getSysParamValue("LocationPoint");
        if (StringUtils.isNotEmpty(locationPoint) && locationPoint.indexOf(",") > 0) {
            request.setAttribute("location1", locationPoint.split(",")[0]);
            request.setAttribute("location2", locationPoint.split(",")[1]);
        } else {
            request.setAttribute("location1", "121.431277");
            request.setAttribute("location2", "28.652722");
        }
        return SUCCESS;
    }

    public String getRoute() throws Exception {
        String userid = request.getParameter("u");
        String date = request.getParameter("t");
        Map map = new HashMap();
        map.put("userid", userid);
        map.put("date", date);
        List list = phoneService.getLocationHistoryList(map);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 显示行程报表页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 9, 2014
     */
    public String showRouteReportPage() throws Exception {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String today = sdf.format(date);
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 显示每日行程报表页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 9, 2014
     */
    public String showRouteDetailReportPage() throws Exception {
        String businessdate = request.getParameter("businessdate");
        List list = getDaysOfTheMonth(businessdate);
        request.setAttribute("daylist", list);
        return SUCCESS;
    }

    /**
     * 获取每日行程报表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 10, 2014
     */
    public String getRouteReportList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list = phoneService.getRouteReportList(pageMap);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 根据年月获取该月份天数
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 9, 2014
     */
    public List getDaysOfTheMonth(String businessdate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        if (StringUtils.isNotEmpty(businessdate)) {
            date = sdf.parse(businessdate);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        List list = new ArrayList();
        for (int i = 0; i < maxDay; i++) {
            String daynum = String.valueOf(i + 1);
            if (daynum.length() == 1) {
                daynum = "0" + daynum;
            }
            String day = businessdate + "-" + daynum;
            list.add(i, day);
        }
        return list;
    }

    /**
     * 导出行程报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 10, 2014
     */
    public void exportRouteReportData() throws Exception {
        Map map2 = CommonUtils.changeMap(request.getParameterMap());
        String businessdate = (String) map2.get("businessdate");
        pageMap.setCondition(map2);
        String title = "";
        if (map2.containsKey("excelTitle")) {
            title = businessdate + map2.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("userid", "编码");
        firstMap.put("username", "业务员");
        List<String> list = getDaysOfTheMonth(businessdate);
        for (int i = 0; i < list.size(); i++) {
            firstMap.put(list.get(i), i + 1 + "号");
        }
        result.add(firstMap);

        List<Map<String, Object>> relist = phoneService.getRouteReportList(pageMap);
        for (Map<String, Object> map : relist) {
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
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

        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 行程新增对话框
     *
     * @return
     * @author huangzhiqian
     * @date 2016年1月20日
     */
    public String showRouteAddPage() {
        return SUCCESS;
    }

    /**
     * 新增行程
     *
     * @return
     * @author huangzhiqian
     * @date 2016年1月20日
     */
    public String addRoute() throws Exception {
        String startDate = request.getParameter("businessdatestart");
        String endDate = request.getParameter("businessdateend");
        String userid = request.getParameter("userid");
        String type = request.getParameter("type");
        Map result;
        if ("1".equals(type)) {
            result = phoneService.addRootByDateAndUserId(startDate, endDate, userid);
        } else {
            result = mapService.addRootByDateAndUserId(startDate, endDate, userid, "3".equals(type));
        }
        addLog(result.get("msg").toString());
        addJSONObject(result);
        return SUCCESS;
    }
}

