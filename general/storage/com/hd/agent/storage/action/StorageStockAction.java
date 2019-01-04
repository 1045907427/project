package com.hd.agent.storage.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.storage.service.IStorageStockService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2018/1/26.
 */
public class StorageStockAction extends BaseFilesAction {
    private IStorageStockService storageStockService;

    public IStorageStockService getStorageStockService() {
        return storageStockService;
    }

    public void setStorageStockService(IStorageStockService storageStockService) {
        this.storageStockService = storageStockService;
    }

    /**
     * 异常成本报表
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Jan 26, 2018
     */
    public String showAbnormalBillReportPage() {
        request.setAttribute("beginAccountDate",CommonUtils.getMonthDateStr());
        request.setAttribute("endAccountDate",CommonUtils.getTodayDateStr());
        request.setAttribute("today",CommonUtils.getTodayDateStr());
        return SUCCESS;
    }

    /**
     * 成本异常报表查询数据
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Jan 26, 2018
     */
    public String getAbnormalBillReportData() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData=storageStockService.getAbnormalBillReportData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 结算商品成本价
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    @UserOperateLog(key = "StorageStockSum", type = 2)
    public String accountCostReportData() throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());

        String businessdate=(String)map.get("businessdate");
        String businessdate1=(String)map.get("businessdate1");
        String accounttype=(String)map.get("accounttype");
        String datalist=(String)map.get("datalist");
        List<Map> dataList= JSONUtils.jsonStrToList(datalist,new HashMap());
        StringBuilder stringBuilder=new StringBuilder();
        for(Map dataMap:dataList){
            String goodsid=(String)dataMap.get("goodsid");
            String storageid=(String)dataMap.get("storageid");
            dataMap=(Map)CommonUtils.deepCopy(map);
            dataMap.put("goodsid",goodsid);
            dataMap.put("storageid",storageid);
            if(!dataMap.containsKey("storageid")||dataMap.get("storageid")==null){
                dataMap.put("storageid","");
            }
            Map resMap=storageStockService.insertCostReportData(dataMap);
            Boolean flag=(Boolean)resMap.get("flag");
            if(!flag){
                addJSONObject(resMap);
                return SUCCESS;
            }
            stringBuilder.append("仓库"+storageid+"商品"+goodsid);
        }
        stringBuilder.append("成本计算:业务日期"+businessdate+"到"+businessdate1+" 结算方式"+accounttype);
        addLog(stringBuilder.toString(),true);
        addJSONObject("flag",true);
        return SUCCESS;
    }

    /**
     * 显示存货明细页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    public String showCostAccountDetailPage() {
        return SUCCESS;
    }

    /**
     * 导出成本异常报表数据
     * @param
     * @return void
     * @throws
     * @author luoqiang
     * @date Jan 30, 2018
     */
    public void exportAccountCostReportData() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("ispageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(org.apache.commons.lang3.StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData=storageStockService.getAbnormalBillReportData(pageMap);

        List list = pageData.getList();
        ExcelUtils.exportAnalysExcel(map,list);
    }

    /**
     * 获取存货明细数据
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Jan 25, 2018
     */
    public String showCostAccountDetailData() throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        if(!map.containsKey("storageid")){
            map.put("storageid","");
        }
        pageMap.setCondition(map);
        PageData pageData=storageStockService.showCostAccountDetailData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 显示结算方式页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Jan 30, 2018
     */
    public String showAccountTypePage() {
        return SUCCESS;
    }

    /**
     * 显示库存成本调整页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 22, 2018
     */
    public String showStorageCostChangePage() throws Exception{
        String goodsid=request.getParameter("goodsid");
        String storageid=request.getParameter("storageid");
        String businessdate=request.getParameter("businessdate");
        if(StringUtils.isEmpty(storageid)){
            GoodsInfo goodsInfo=getGoodsInfoByID(goodsid);
            if(goodsInfo!=null){
                request.setAttribute("storageid",goodsInfo.getStorageid());
            }
        }else{
            request.setAttribute("storageid",storageid);
        }
        request.setAttribute("goodsid",goodsid);

        return SUCCESS;
    }

    /**
     * 更新库存成本
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 22, 2018
     */
    @UserOperateLog(key = "StorageStockSum", type = 2)
    public String addChangeBill() throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        if(!map.containsKey("storageid")){
            map.put("storageid","");
        }
        String realstorageid=(String)map.get("realstorageid");
        String goodsid=(String)map.get("goodsid");
        String changedate=(String)map.get("changedate");
        String isAcountCostPrice=(String)map.get("isAcountCostPrice");
        BigDecimal changeamount=new BigDecimal(map.get("changeamount").toString());
        Map resMap=storageStockService.addChangeBill(realstorageid,goodsid,changedate,changeamount,isAcountCostPrice,map);
        String id=resMap.containsKey("id")?(String)resMap.get("id"):"";
        Boolean flag=(Boolean)resMap.get("flag");
        String msg="库存成本调整金额"+changeamount+"生成单据"+id;
        if("1".equals(isAcountCostPrice)){
            msg+="成本重新结算";
        }
        addLog(msg,flag);
        addJSONObject(resMap);

        return SUCCESS;
    }

    /**
     * 获取库存成本调整调整后的单价和期末单价
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 22, 2018
     */
    public String getStorageAfterChangePrice() throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        if(!map.containsKey("storageid")){
            map.put("storageid","");
        }
        pageMap.setCondition(map);
//        PageData pageData=storageStockService.getAbnormalBillReportData(pageMap);
        Map resMap=storageStockService.getStorageAfterChangePrice(pageMap);
        addJSONObject(resMap);
        return SUCCESS;
    }
}
