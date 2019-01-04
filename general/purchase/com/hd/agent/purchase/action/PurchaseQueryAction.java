/**
 * @(#)PurchaseQueryAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 30, 2013 chenwei 创建版本
 */
package com.hd.agent.purchase.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.purchase.service.IPurchaseQueryService;

/**
 * 
 * 采购业务查询action
 * @author chenwei
 */
public class PurchaseQueryAction extends BaseFilesAction {
	/**
	 * 采购业务查询service
	 */
	private IPurchaseQueryService purchaseQueryService;
	
	public IPurchaseQueryService getPurchaseQueryService() {
		return purchaseQueryService;
	}

	public void setPurchaseQueryService(IPurchaseQueryService purchaseQueryService) {
		this.purchaseQueryService = purchaseQueryService;
	}


	/**
	 * 显示采购进货退货明细查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 30, 2013
	 */
	public String showArrivalReturnDetailListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取采购退货明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 30, 2013
	 */
	public String showArrivalReturnDetailList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = purchaseQueryService.showArrivalReturnDetailList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出-采购进货退货明细列表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportArrivalReturnListData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
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
		PageData pageData = purchaseQueryService.showArrivalReturnDetailList(pageMap);
		ExcelUtils.exportExcel(exportArrivalReturnDetailListDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(采购进货退货明细列表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportArrivalReturnDetailListDataFilter(List<Map<String,Object>> list,List<Map<String,Object>> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编号");
		firstMap.put("billtypename", "类型");//+unitname
		firstMap.put("businessdate", "业务日期");
		firstMap.put("supplierid", "供应商编码");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("model", "规格参数");
		firstMap.put("barcode", "条形码");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("taxprice", "含税单价");
		firstMap.put("taxamount", "含税金额");
		firstMap.put("notaxamount", "未税金额");
		firstMap.put("taxtypename", "税种");
		firstMap.put("tax", "税额");
		firstMap.put("iswriteoffname", "状态");
		firstMap.put("remark", "备注");
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
		if(null!=footerList && footerList.size() > 0){
			for(Map<String,Object> map : footerList){
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
}

