package com.hd.agent.report.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.BaseSalesReport;
import com.hd.agent.report.service.IPaymentdaysSetService;
import com.hd.agent.storage.service.IStorageSummaryService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库龄报表action
 * Created by chenwei on 2017-03-21.
 */
public class InventoryAgeAction extends BaseFilesAction {

    private IStorageSummaryService storageSummaryService;
    /**
     * 超账期区间设置
     */
    private IPaymentdaysSetService paymentdaysSetService;

    public IStorageSummaryService getStorageSummaryService() {
        return storageSummaryService;
    }

    public void setStorageSummaryService(IStorageSummaryService storageSummaryService) {
        this.storageSummaryService = storageSummaryService;
    }

    public IPaymentdaysSetService getPaymentdaysSetService() {
        return paymentdaysSetService;
    }

    public void setPaymentdaysSetService(IPaymentdaysSetService paymentdaysSetService) {
        this.paymentdaysSetService = paymentdaysSetService;
    }

    /**
     * 显示库龄报表页面
     * @return
     * @throws Exception
     */
    public String showInventoryAgePage() throws Exception{
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        List list = paymentdaysSetService.getPaymentdaysSetInfo("3");
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);
        request.setAttribute("list", list);
        request.setAttribute("listStr", JSONUtils.listToJsonStr(list));
        return "success";
    }

    /**
     * 获取库龄数据
     * @return
     * @throws Exception
     */
    public String showInventoryAgeDataList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageSummaryService.showInventoryAgeDataList(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

    /**
     * 生成库龄报表数据
     * @return
     * @throws Exception
     */
    public String addInventoryAgeData() throws Exception{
        String businessdate = request.getParameter("businessdate");
        boolean flag = storageSummaryService.addInventoryAge(businessdate);
        addJSONObject("flag",flag);
        return "success";
    }

    /**
     * 导出销售情况统计报表
     * @throws Exception
     * @author panxiaoxiao
     * @date Nov 18, 2013
     */
    public void exportInventoryAageData()throws Exception{
        String groupcols = null,exporttype = null;
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        queryMap.put("isflag", "true");
        pageMap.setCondition(queryMap);
        PageData pageData = storageSummaryService.showInventoryAgeDataList(pageMap);
        List<BaseSalesReport> list = pageData.getList();
        if(null != pageData.getFooter()){
            List<BaseSalesReport> footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

    }

    /**
     * 显示库龄入库明细页面
     * @return
     * @throws Exception
     */
    public String showInventoryAgeDetailLogPage() throws Exception{
        String businessdate = request.getParameter("businessdate");
        String storageid = request.getParameter("storageid");
        String goodsid = request.getParameter("goodsid");
        request.setAttribute("businessdate",businessdate);
        request.setAttribute("storageid",storageid);
        request.setAttribute("goodsid",goodsid);
        return "success";
    }

    /**
     * 获取库龄入库明细数据
     * @return
     * @throws Exception
     */
    public String showInventoryAgeDetailLogData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageSummaryService.showInventoryAgeDetailLogData(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }
}
