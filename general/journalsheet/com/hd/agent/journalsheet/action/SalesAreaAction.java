/**
 * @(#)SalesAreaAction.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年4月13日 wanghongteng 创建版本
 */
package com.hd.agent.journalsheet.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.basefiles.action.FilesLevelAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.journalsheet.service.ISalesAreaReportService;

/**
 * 
 * 
 * @author wanghongteng
 */
/**
 * @author Administrator
 *
 */
public class SalesAreaAction extends FilesLevelAction {
	private ISalesAreaReportService salesAreaReportService;
	
	
	
	public ISalesAreaReportService getSalesAreaReportService() {
		return salesAreaReportService;
	}

	public void setSalesAreaReportService(ISalesAreaReportService salesAreaReportService) {
		this.salesAreaReportService = salesAreaReportService;
	}

	/**
     * 显示销售区域报表
     * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2016年4月15日
     */
    public String salesAreaReportPage() throws Exception {
    	String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 获取销售区域报表数据
     * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2016年4月15日
     */
    public String showDSalesAreaReportData() throws Exception {
    	Map map=CommonUtils.changeMap(request.getParameterMap());
    	String salesareas=(String)map.get("salesarea");
    	String[] salesareaArr  =null; 
    	if(null!=salesareas){
    		salesareaArr = salesareas.split(",");
     	}
    	map.put("salesareaArr", salesareaArr);
		pageMap.setCondition(map);
		PageData pageData = salesAreaReportService.showDSalesAreaReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
    }
    
    /**
	 * 导出-销售区域报表
	  * @throws Exception
	 * @author wanghongteng 
	 * @date 2016年4月15日
	 */
	public void exportSalesAreaReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String ids = request.getParameter("id");
		String[] salesareaArr  =null; 
    	if(null!=ids){
    		salesareaArr = ids.split(",");
     	}
    	map.put("salesareaArr", salesareaArr);
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
		PageData pageData = salesAreaReportService.showDSalesAreaReportData(pageMap);
		ExcelUtils.exportExcel(exportSalesAreaReportDataFilter(pageData.getList()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式
	 * @param list
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2016年4月15日
	 */
	private List<Map<String, Object>> exportSalesAreaReportDataFilter(List<Map> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		
		firstMap.put("salesarea", "销售编号");
		firstMap.put("salesareaname", "销售区域");	
		firstMap.put("orderamount", "订单金额");	
		firstMap.put("initsendamount", "发货单金额");
		firstMap.put("sendamount", "发货出库金额");
		firstMap.put("pushbalanceamount", "冲差金额");
		firstMap.put("directreturnamount", "直退金额");
		firstMap.put("checkreturnamount", "退货金额");
		firstMap.put("returnamount", "退货合计");
		firstMap.put("salenum", "销售数量");
		firstMap.put("saletotalbox", "销售箱数");
		firstMap.put("saleamount", "销售金额");
		firstMap.put("costamount", "成本金额");
		firstMap.put("salemarginamount", "销售毛利额");
		firstMap.put("realrate", "实际毛利率");
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
				if(map.containsKey("children")){
					List<Map> childList = (List) map.get("children");
					for(Map<String,Object> cmap : childList){
						Map<String, Object> retCMap = new LinkedHashMap<String, Object>();
						for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
							if(cmap.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
								for(Map.Entry<String, Object> entry : cmap.entrySet()){
									if(fentry.getKey().equals(entry.getKey())){
										objectCastToRetMap(retCMap,entry);
									}
								}
							}
							else{
								retCMap.put(fentry.getKey(), "");
							}
						}
						result.add(retCMap);
					}
				}
				
			}
		}
		return result;
	}
}

