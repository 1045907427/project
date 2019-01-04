/**
 * @(#)PurchaseReportAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 16, 2013 chenwei 创建版本
 */
package com.hd.agent.report.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.ArrivalOrderCostAccountReport;
import com.hd.agent.report.model.BuyPaymentBalanceReport;
import com.hd.agent.report.model.PlannedOrderAnalysis;
import com.hd.agent.report.model.PurchaseDetailReport;
import com.hd.agent.report.model.PurchaseQuantityReport;
import com.hd.agent.report.service.IPurchaseReportService;

import net.sf.json.JSONObject;

/**
 * 
 * 采购报表action
 * @author chenwei
 */
public class PurchaseReportAction extends BaseFilesAction {
	
	private IPurchaseReportService purchaseReportService;

	public IPurchaseReportService getPurchaseReportService() {
		return purchaseReportService;
	}

	public void setPurchaseReportService(
			IPurchaseReportService purchaseReportService) {
		this.purchaseReportService = purchaseReportService;
	}
	
	/**
	 * 显示按商品采购情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 27, 2013
	 */
	public String showBuyGoodsReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取按商品采购情况报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 27, 2013
	 */
	public String showBuyGoodsReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("auto")){
            String query = (String) map.get("auto");
            JSONObject object = JSONObject.fromObject(query);
            for (Object k : object.keySet()) {
                Object v = object.get(k);
                if(StringUtils.isNotEmpty((String) v)){
                    map.put(k.toString(), v);
                }
            }
            pageMap.setCondition(map);
            pageMap.setRows(10000);
            PageData pageData =  purchaseReportService.showBuyGoodsReportData(pageMap);
            jsonResult = new HashMap();
            List row = pageData.getList();
            List footer = pageData.getFooter();
            row.addAll(footer);
            jsonResult.put("rows", row);

        }else{
            pageMap.setCondition(map);
            PageData pageData =  purchaseReportService.showBuyGoodsReportData(pageMap);
            addJSONObjectWithFooter(pageData);
        }
//		pageMap.setCondition(map);
//		PageData pageData = purchaseReportService.showBuyGoodsReportData(pageMap);
//		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示分部门采购情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 29, 2013
	 */
	public String showBuyDeptReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取分部门采购情况报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 29, 2013
	 */
	public String showBuyDeptReportData()throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			Map map = CommonUtils.changeMap(request.getParameterMap());
			pageMap.setCondition(map);
			PageData pageData = purchaseReportService.showBuyDeptReportData(pageMap);
			if(pageData.getList().size()>0){
				addJSONObjectWithFooter(pageData);
			}else{
				addJSONObject(pageData);
			}
			return SUCCESS;
		}
		else{
			String[] idArr = id.split("_");
			String buydeptid = idArr[0];
			String businessdate1 = idArr[1];
			String businessdate2 = idArr[2];
			Map map = new HashMap();
			map.put("buydeptid", buydeptid);
			if(!"null".equals(businessdate1)){
				map.put("businessdate1", businessdate1);
			}
			if(!"null".equals(businessdate2)){
				map.put("businessdate2", businessdate2);
			}
			List list = purchaseReportService.showBuyDeptReportDetailList(map);
			addJSONArray(list);
			return "treeSuccess";
		}
	}
	
	/**
	 * 获取分部门采购情况报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	public String getBuyDeptReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("auto")){
            String query = (String) map.get("auto");
            JSONObject object = JSONObject.fromObject(query);
            for (Object k : object.keySet()) {
                Object v = object.get(k);
                if(StringUtils.isNotEmpty((String) v)){
                    map.put(k.toString(), v);
                }
            }
            pageMap.setCondition(map);
            pageMap.setRows(10000);
            PageData pageData = purchaseReportService.showBuyDeptReportData(pageMap);
            jsonResult = new HashMap();
            List row = pageData.getList();
            if(row.size() > 0 ){
                List footer = pageData.getFooter();
                row.addAll(footer);
            }
            jsonResult.put("rows", row);

        }else{
            pageMap.setCondition(map);
            PageData pageData = purchaseReportService.showBuyDeptReportData(pageMap);
            if(pageData.getList().size()>0){
                addJSONObjectWithFooter(pageData);
            }else{
                addJSONObject(pageData);
            }
        }
//		pageMap.setCondition(map);
//		PageData pageData = purchaseReportService.showBuyDeptReportData(pageMap);
//		if(pageData.getList().size()>0){
//			addJSONObjectWithFooter(pageData);
//		}else{
//			addJSONObject(pageData);
//		}
		return SUCCESS;
	}
	
	/**
	 * 显示分品牌采购情况统计报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public String showBuyBrandReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取分品牌采购情况统计报表数据 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public String showBuyBrandReportData()throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			Map map = CommonUtils.changeMap(request.getParameterMap());
			pageMap.setCondition(map);
			PageData pageData = purchaseReportService.showBuyBrandReportData(pageMap);
			if(pageData.getList().size()>0){
				addJSONObjectWithFooter(pageData);
			}else{
				addJSONObject(pageData);
			}
			return SUCCESS;
		}
		else{
			String[] idArr = id.split("_");
			String brandid = idArr[0];
			String businessdate1 = idArr[1];
			String businessdate2 = idArr[2];
			Map map = new HashMap();
			map.put("brandid", brandid);
			if(!"null".equals(businessdate1)){
				map.put("businessdate1", businessdate1);
			}
			if(!"null".equals(businessdate2)){
				map.put("businessdate2", businessdate2);
			}
			List list = purchaseReportService.showBuyBrandReportDetailList(map);
			addJSONArray(list);
			return "treeSuccess";
		}
	}
	
	/**
	 * 获取分品牌采购情况统计报表数据 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	public String getBuyBrandReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("auto")){
            String query = (String) map.get("auto");
            JSONObject object = JSONObject.fromObject(query);
            for (Object k : object.keySet()) {
                Object v = object.get(k);
                if(StringUtils.isNotEmpty((String) v)){
                    map.put(k.toString(), v);
                }
            }
            pageMap.setCondition(map);
            pageMap.setRows(10000);
            PageData pageData = purchaseReportService.showBuyBrandReportData(pageMap);
            jsonResult = new HashMap();
            List row = pageData.getList();
            if(row.size() > 0 ){
                List footer = pageData.getFooter();
                row.addAll(footer);
            }
            jsonResult.put("rows", row);

        }else{
            pageMap.setCondition(map);
            PageData pageData = purchaseReportService.showBuyBrandReportData(pageMap);
            if(pageData.getList().size()>0){
                addJSONObjectWithFooter(pageData);
            }else{
                addJSONObject(pageData);
            }
        }
		/*pageMap.setCondition(map);
		PageData pageData = purchaseReportService.showBuyBrandReportData(pageMap);
		if(pageData.getList().size()>0){
			addJSONObjectWithFooter(pageData);
		}else{
			addJSONObject(pageData);
		}*/
		return SUCCESS;
	}
	
	/**
	 * 显示分供应商采购情况统计报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public String showBuySupplierReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取分供应商采购情况统计报表数据 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public String showBuySupplierReportData()throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			Map map = CommonUtils.changeMap(request.getParameterMap());
			pageMap.setCondition(map);
			PageData pageData = purchaseReportService.showBuySupplierReportData(pageMap);
			if(pageData.getList().size()>0){
				addJSONObjectWithFooter(pageData);
			}else{
				addJSONObject(pageData);
			}
			return SUCCESS;
		}
		else{
			String[] idArr = id.split("_");
			String supplierid = idArr[0];
			String businessdate1 = idArr[1];
			String businessdate2 = idArr[2];
			Map map = new HashMap();
			map.put("supplierid", supplierid);
			if(!"null".equals(businessdate1)){
				map.put("businessdate1", businessdate1);
			}
			if(!"null".equals(businessdate2)){
				map.put("businessdate2", businessdate2);
			}
			List list = purchaseReportService.showBuySupplierReportDetailList(map);
			addJSONArray(list);
			return "treeSuccess";
		}
	}
	
	/**
	 * 获取分供应商采购统计情况数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	public String getBuySupplierReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("auto")){
            String query = (String) map.get("auto");
            JSONObject object = JSONObject.fromObject(query);
            for (Object k : object.keySet()) {
                Object v = object.get(k);
                if(StringUtils.isNotEmpty((String) v)){
                    map.put(k.toString(), v);
                }
            }
            pageMap.setCondition(map);
            pageMap.setRows(10000);
            PageData pageData = purchaseReportService.showBuySupplierReportData(pageMap);
            jsonResult = new HashMap();
            List row = pageData.getList();
            if(row.size() > 0 ){
                List footer = pageData.getFooter();
                row.addAll(footer);
            }
            jsonResult.put("rows", row);

        }else{
            pageMap.setCondition(map);
            PageData pageData = purchaseReportService.showBuySupplierReportData(pageMap);
            if(pageData.getList().size()>0){
                addJSONObjectWithFooter(pageData);
            }else{
                addJSONObject(pageData);
            }
        }
//		pageMap.setCondition(map);
//		PageData pageData = purchaseReportService.showBuySupplierReportData(pageMap);
//		if(pageData.getList().size()>0){
//			addJSONObjectWithFooter(pageData);
//		}else{
//			addJSONObject(pageData);
//		}
		return SUCCESS;
	}
	
	/**
	 * 显示分供应商采购情况统计表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	public String showBuySupplierReportDetailDataPage()throws Exception{
		String supplierid = request.getParameter("supplierid");
		String suppliername = request.getParameter("suppliername");
		String buydeptid = request.getParameter("buydeptid");
		String buydeptname = request.getParameter("buydeptname");
		String brandid = request.getParameter("brandid");
		String brandname = request.getParameter("brandname");
		String buyuserid = request.getParameter("buyuserid");
		String buyusername = request.getParameter("buyusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		request.setAttribute("supplierid", supplierid);
		request.setAttribute("suppliername", suppliername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		request.setAttribute("buydeptid", buydeptid);
		request.setAttribute("buydeptname", buydeptname);
		request.setAttribute("brandid", brandid);
		request.setAttribute("brandname", brandname);
		request.setAttribute("buyuserid", buyuserid);
		request.setAttribute("buyusername", buyusername);
		return SUCCESS;
	}
	
	/**
	 * 获取分供应商采购情况统计情况表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	public String showBuySupplierReportDetailData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = purchaseReportService.getBuySupplierReportDetailData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	public String showBuySupplierReportChildrenData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		List list = purchaseReportService.showBuySupplierReportDetailList(map);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 显示分采购员采购情况统计报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public String showBuyUserReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取分采购员采购情况统计报表数据 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public String showBuyUserReportData()throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			Map map = CommonUtils.changeMap(request.getParameterMap());
			pageMap.setCondition(map);
			PageData pageData = purchaseReportService.showBuyUserReportData(pageMap);
			if(pageData.getList().size()>0){
				addJSONObjectWithFooter(pageData);
			}else{
				addJSONObject(pageData);
			}
			return SUCCESS;
		}
		else{
			String[] idArr = id.split("_");
			String buyuserid = idArr[0];
			String businessdate1 = idArr[1];
			String businessdate2 = idArr[2];
			Map map = new HashMap();
			map.put("buyuserid", buyuserid);
			if(!"null".equals(businessdate1)){
				map.put("businessdate1", businessdate1);
			}
			if(!"null".equals(businessdate2)){
				map.put("businessdate2", businessdate2);
			}
			List list = purchaseReportService.showBuyUserReportDetailList(map);
			addJSONArray(list);
			return "treeSuccess";
		}
	}
	
	/**
	 * 获取分采购员采购情况统计报表数据 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 22, 2014
	 */
	public String getBuyUserReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("auto")){
            String query = (String) map.get("auto");
            JSONObject object = JSONObject.fromObject(query);
            for (Object k : object.keySet()) {
                Object v = object.get(k);
                if(StringUtils.isNotEmpty((String) v)){
                    map.put(k.toString(), v);
                }
            }
            pageMap.setCondition(map);
            pageMap.setRows(10000);
            PageData pageData = purchaseReportService.showBuyUserReportData(pageMap);
            jsonResult = new HashMap();
            List row = pageData.getList();
            List footer = pageData.getFooter();
            row.addAll(footer);
            jsonResult.put("rows", row);

        }else{
            pageMap.setCondition(map);
            PageData pageData = purchaseReportService.showBuyUserReportData(pageMap);
            addJSONObjectWithFooter(pageData);
        }

		return SUCCESS;
	}
	
	/**
	 * 显示按某采购员采购情况统计情况报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 22, 2014
	 */
	public String showBuyuserReportDetailPage()throws Exception{
		String buyuserid = request.getParameter("buyuserid");
		String buyusername = request.getParameter("buyusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		request.setAttribute("buyuserid", buyuserid);
		request.setAttribute("buyusername", buyusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}
	
	/**
	 * 导出-分商品采购情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportBuyGoodsReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = purchaseReportService.showBuyGoodsReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}
	

	/**
	 * 导出-分供应商采购情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportBuysupplierReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = purchaseReportService.showBuySupplierReportData(pageMap);

        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}
	

	/**
	 * 导出-分供应商采购明细报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	public void exportBuysupplierDetailReportData()throws Exception{
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
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		//firstMap.put("unitname", "主单位");
		//firstMap.put("enternum", "进货数量");
		firstMap.put("entertotalbox", "进货箱数");
		//firstMap.put("auxunitname", "辅单位");
		firstMap.put("entertaxamount", "进货总金额");
		firstMap.put("entertax", "进货税额");
		firstMap.put("outtotalbox", "退货箱数");
		firstMap.put("outtaxamount", "退货总金额");
		firstMap.put("outtax", "退货税额");
		firstMap.put("totalamount", "合计金额");
		result.add(firstMap);
		
		PageData pageData = purchaseReportService.getBuySupplierReportDetailData(pageMap);
		List<PurchaseDetailReport> list = new ArrayList<PurchaseDetailReport>();
		list.addAll(pageData.getList());
		list.addAll(pageData.getFooter());
		List<Map<String, Object>> backList = retBackChangeData(list,firstMap);
		result.addAll(backList);
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 采购明细导出数据转换
	 * @param list
	 * @param firstMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	private List<Map<String, Object>> retBackChangeData(List<PurchaseDetailReport> list,Map<String, Object> firstMap)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(PurchaseDetailReport purchaseDetailReport : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			map = PropertyUtils.describe(purchaseDetailReport);
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
		return result;
	}
	
	/**
	 * 导出-分部门采购情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportBuyDeptReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = purchaseReportService.showBuyDeptReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}
	

	/**
	 * 导出-分品牌采购情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportBuyBrandReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = purchaseReportService.showBuyBrandReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 导出-分采购员采购情况统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportBuyUserReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
		PageData pageData = purchaseReportService.showBuyUserReportData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 采购订单列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String plannedOrderAnalysisReportPage() throws Exception{
		String flag=getSysParamValue("PurchaseCGJHFXBPageDateSet");
		//1同期时间与前期间隔一年
		//2同期时间与前期间隔一个月
		if("1".equals(flag)){
			flag="1";	
		}else{
			flag="0";
		}
		Date yestoDay=CommonUtils.getYestodayDate();
		Date toDate=new Date();
		String today = CommonUtils.dataToStr(toDate, "yyyy-MM-dd");
		String yestoday = CommonUtils.dataToStr(yestoDay, "yyyy-MM-dd");
		Date firstDayDate=CommonUtils.getBeforeTheDateInDays(toDate,30);
		String firstDay = CommonUtils.dataToStr(firstDayDate, "yyyy-MM-dd");
		String prevyearfirstday = "";
		String prevyearcurday ="";
		if("1".equals(flag)){
			prevyearfirstday=CommonUtils.getPrevMonthDay(firstDayDate,-12);
			prevyearcurday=CommonUtils.getPrevMonthDay(yestoDay,-12);
		}else{
			firstDayDate = CommonUtils.getBeforeTheDateInDays(toDate,61);
			prevyearfirstday = CommonUtils.dataToStr(firstDayDate, "yyyy-MM-dd");

			firstDayDate = CommonUtils.getBeforeTheDateInDays(toDate,31);
			prevyearcurday = CommonUtils.dataToStr(firstDayDate, "yyyy-MM-dd");
		}

		request.setAttribute("firstDay", firstDay);
		request.setAttribute("yestoday", yestoday);
		request.setAttribute("today", today);
		request.setAttribute("prevyearfirstday", prevyearfirstday);
		request.setAttribute("prevyearcurday", prevyearcurday);
		return SUCCESS;
	}
	/**
	 * 采购计划分析分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showPlannedOrderAnalysisPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=purchaseReportService.showPlannedOrderAnalysisPageData(pageMap);		
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 采购计划分析查看，按品牌合计库存
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 13, 2014
	 */
	public String showStorageSummaryByBrand()throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=purchaseReportService.showStorageSummaryByBrand(pageMap);		
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 采购计划分析分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showPlannedOrderAnalysisPageListInBuyOrder() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=purchaseReportService.showPlannedOrderAnalysisPageDataInBuyOrder(pageMap);		
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 采购计划分析查看，按品牌合计库存
	 * @param
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-19
	 */
	public String showStorageSummaryByBrandInBuyOrder() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=purchaseReportService.showStorageSummaryByBrandInBuyOrder(pageMap);		
		addJSONObjectWithFooter(pageData);
		return SUCCESS;		
	}
	
	/**
	 * 导出采购计划分析报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportPlannedOrderAnalysisReportData()throws Exception{
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
		PageData pageData = purchaseReportService.showPlannedOrderAnalysisPageData(pageMap);
		ExcelUtils.exportExcel(exportPlannedOrderAnalysisReportDataFilter(pageData.getList()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(采购计划分析表) 
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportPlannedOrderAnalysisReportDataFilter(List<PlannedOrderAnalysis> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("goodssortname", "商品分类");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "主单位");
		firstMap.put("auxunitname", "单位");
		firstMap.put("boxamount", "箱价");
		firstMap.put("existingunitnum", "实际数量");
		firstMap.put("existingnum", "实际库存");
		firstMap.put("existingamount", "实际库存金额");
		firstMap.put("transitnum", "在途量");
		firstMap.put("transitamount", "在途金额");
		firstMap.put("curstoragenum", "本期存货");
		firstMap.put("curstorageamount", "本期库存金额");
		firstMap.put("tqsaleunitnum", "同期销售主单位数量");
		firstMap.put("tqsalenum", "同期销售数量");
		firstMap.put("tqsaleamount", "同期销售金额");
		firstMap.put("qqsaleunitnum", "前期销售主单位数量");
		firstMap.put("qqsalenum", "前期销售数量");
		firstMap.put("qqsaleamount", "前期销售金额");
		firstMap.put("canordernum", "可订量");
		firstMap.put("orderunitnum", "本次采购主单位数量");
		firstMap.put("ordernum", "本次采购箱数");
		firstMap.put("orderauxremainder", "本次采购个数");
		firstMap.put("orderamount", "本次采购金额");
		firstMap.put("totalstoragenum", "合计库存数");
		firstMap.put("totalstorageamount", "合计库存金额");
		firstMap.put("cansaleday", "可销售时段");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(PlannedOrderAnalysis item : list){
				if(null!=item.getCanordernum() && item.getCanordernum().compareTo(BigDecimal.ZERO)<0){
					item.setCanordernum(BigDecimal.ZERO);
				}
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
		//按品牌合计库存
		Map<String, Object> firstMap2 = new LinkedHashMap<String, Object>();
		firstMap2.put("brandname", "品牌名称");
		firstMap2.put("ordernumdetail", "本次采购数");
		firstMap2.put("orderamount", "本次采购金额");
		firstMap2.put("existingunitnum", "实际数量");
		firstMap2.put("existingnumdetail", "实际库存");
		firstMap2.put("existingamount", "实际库存金额");
		firstMap2.put("transitnumdetail", "在途量");
		firstMap2.put("transitamount", "在途金额");
		firstMap2.put("curstoragenumdetail", "当前库存数");
		firstMap2.put("curstorageamount", "当前库存金额");
		firstMap2.put("totalstoragenumdetail", "合计库存数");
		firstMap2.put("totalstorageamount", "合计库存金额");
		result.add(firstMap2);
		
		PageData pageData=purchaseReportService.showStorageSummaryByBrand(pageMap);	
		List<PlannedOrderAnalysis> list2 = pageData.getList();
		list2.addAll(pageData.getFooter());
		if(list2.size() != 0){
			for(PlannedOrderAnalysis item : list2){
				if(null!=item.getCanordernum() && item.getCanordernum().compareTo(BigDecimal.ZERO)<0){
					item.setCanordernum(BigDecimal.ZERO);
				}
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
				for(Map.Entry<String, Object> fentry : firstMap2.entrySet()){
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
	 * 显示采购计划单分析表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String arrivalOrderCostAccountReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	/**
	 * 获取采购计划单分析表统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String showArrivalOrderCostAccountReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map && map.containsKey("ispageflag")){
			map.remove("ispageflag");
		}
		pageMap.setCondition(map);
		PageData pageData = purchaseReportService.showArrivalOrderCostAccountReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出采购计划单分析表统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String exportArrivalOrderCostAccountReportData() throws Exception{
		String groupcols=null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
		map.put("ispageflag", "true");
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
		PageData pageData = purchaseReportService.showArrivalOrderCostAccountReportData(pageMap);
		ExcelUtils.exportExcel(exportArrivalOrderCostAccountReportDataFilter(pageData.getList(),pageData.getFooter(),groupcols), title);
		return null;
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(采购计划单分析表统计报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportArrivalOrderCostAccountReportDataFilter(List<ArrivalOrderCostAccountReport> list,List<ArrivalOrderCostAccountReport> footerlist,String groupcols) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		boolean isshowgoods=false;
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("goodsid".equals(groupcols)){
					firstMap.put("goodsid", "商品编码");
					firstMap.put("goodsname", "商品名称");
					firstMap.put("barcode", "条形码");
					firstMap.put("brandname", "品牌名称");
					isshowgoods=true;
				}
				else if("brandid".equals(groupcols)){
					firstMap.put("brandname", "品牌名称");
				}
				else if("buyuserid".equals(groupcols)){
					firstMap.put("buyusername", "采购员");
					firstMap.put("buydeptname", "采购部门");
				}
				else if("buydeptid".equals(groupcols)){
					firstMap.put("buydeptname", "采购部门");
				}
				else if("supplierid".equals(groupcols)){
					firstMap.put("supplierid", "供应商编码");
					firstMap.put("suppliername", "供应商名称");
				}
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("goodsid".equals(group)){
						firstMap.put("goodsid", "商品编码");
						firstMap.put("goodsname", "商品名称");
						firstMap.put("barcode", "条形码");
						firstMap.put("brandname", "品牌名称");
						firstMap.put("suppliername", "供应商");
						isshowgoods=true;
					}else if("brandid".equals(group)){
						firstMap.put("brandname", "品牌名称");
					}else if("buyuserid".equals(group)){
						firstMap.put("buyusername", "采购员");
						firstMap.put("buydeptname", "采购部门");
					}else if("buydeptid".equals(group)){
						firstMap.put("buydeptname", "采购部门");
					}else if("supplierid".equals(groupcols)){
						firstMap.put("supplierid", "供应商编码");
						firstMap.put("suppliername", "供应商名称");
					}
				}
			}
			if(isshowgoods){
				firstMap.put("boxnum", "箱装量");
				firstMap.put("buyunitname", "单位");
				firstMap.put("buynum", "采购数量");
			}
			firstMap.put("buyamount", "采购金额");
			if(isshowgoods){
				firstMap.put("costaccountprice", "核算成本价");
			}
			firstMap.put("costaccountamount", "核算金额");
			firstMap.put("blanceamount", "差额");
		}
		else{
			firstMap.put("buydeptname", "采购部门");
			firstMap.put("buyusername", "采购员");
			firstMap.put("goodsid", "商品编号");
			firstMap.put("goodsname", "商品名称");
			firstMap.put("barcode", "条形码");
			firstMap.put("brandname", "品牌名称");
			firstMap.put("boxnum", "箱装量");
			firstMap.put("buyunitname", "单位");
			firstMap.put("buynum", "采购数量");
			firstMap.put("buyamount", "采购金额");
			firstMap.put("costaccountprice", "核算成本价");
			firstMap.put("costaccountamount", "核算金额");
			firstMap.put("blanceamount", "差额");
		}
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(ArrivalOrderCostAccountReport item : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
		if(null!=footerlist && footerlist.size() > 0){
			for(ArrivalOrderCostAccountReport item : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
	 * 显示采购付款差额报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String buyPaymentBalanceReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	/**
	 * 获取采购付款差额报表统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public String showBuyPaymentBalanceReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map && map.containsKey("ispageflag")){
			map.remove("ispageflag");
		}
		pageMap.setCondition(map);
		PageData pageData = purchaseReportService.showBuyPaymentBalanceReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 导出采购付款差额报表统计数据
	 */
	public String exportBuyPaymentBalanceReportData() throws Exception{
		String groupcols=null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
		map.put("ispageflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		PageData pageData = purchaseReportService.showBuyPaymentBalanceReportData(pageMap);
		ExcelUtils.exportExcel(exportBuyPaymentBalanceReportDataFilter(pageData.getList(),pageData.getFooter(),groupcols), title);
		return null;
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(采购计划单分析表统计报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportBuyPaymentBalanceReportDataFilter(List<BuyPaymentBalanceReport> list,List<BuyPaymentBalanceReport> footerlist,String groupcols) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("supplierid".equals(groupcols)){
					firstMap.put("supplierid", "供应商编码");
					firstMap.put("suppliername", "供应商名称");
				}
				else if("brandid".equals(groupcols)){
					firstMap.put("brandname", "品牌名称");
				}
				else if("buyuserid".equals(groupcols)){
					firstMap.put("buyusername", "采购员");
					firstMap.put("buydeptname", "采购部门");
				}
				else if("buydeptid".equals(groupcols)){
					firstMap.put("buydeptname", "采购部门");
				}
			}else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("supplier".equals(groupcols)){
						firstMap.put("supplierid", "供应商编码");
						firstMap.put("suppliername", "供应商");
					}
					else if("brand".equals(group)){
						firstMap.put("brandname", "品牌名称");
					}
					else if("buyuser".equals(group)){
						firstMap.put("buyusername", "采购员");
						firstMap.put("buydeptname", "采购部门");
					}
					else if("buydept".equals(group)){
						firstMap.put("buydeptname", "采购部门");
					}
				}
			}
			firstMap.put("arrivalamount", "进货金额");
			firstMap.put("invoiceamount", "开票金额");
			firstMap.put("paybalance", "差额");
		}else{
			firstMap.put("brandname", "品牌名称");
			firstMap.put("arrivalamount", "进货金额");
			firstMap.put("invoiceamount", "开票金额");
			firstMap.put("paybalance", "差额");
		}
		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(BuyPaymentBalanceReport item : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
		if(null!=footerlist && footerlist.size() > 0){
			for(BuyPaymentBalanceReport item : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
	 * 采购进货数量列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public String showPurchaseQuantityReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	/**
	 * 采购进货数量报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public String showPurchaseQuantityReportData() throws Exception{		
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map && map.containsKey("ispageflag")){
			map.remove("ispageflag");
		}
		pageMap.setCondition(map);
		PageData pageData = purchaseReportService.showPurchaseQuantityReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 导出 采购进货数量报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public String exportPurchaseQuantityReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		PageData pageData = purchaseReportService.showPurchaseQuantityReportData(pageMap);
		ExcelUtils.exportExcel(exportPurchaseQuantityReportDataFilter(pageData.getList(),pageData.getFooter()), title);
		return null;
	}
	/**
	 * 数据转换，采购进货数量报表数据 导出
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportPurchaseQuantityReportDataFilter(List<PurchaseQuantityReport> list,List<PurchaseQuantityReport> footerlist) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		
		firstMap.put("brandid", "品牌编号");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("enternum", "进货总数量");
		firstMap.put("enterunitname", "进货单位");
		firstMap.put("totalweight", "进货总重量");

		result.add(firstMap);
		
		if(null!=list && list.size() > 0){
			for(PurchaseQuantityReport item : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
		if(null!=footerlist && footerlist.size() > 0){
			for(PurchaseQuantityReport item : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
	 * 导出查看相应采购计划分析表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 12, 2014
	 */
	public void exportAnalysisReportView()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
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
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("goodssortname", "商品分类");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "主单位");
		firstMap.put("auxunitname", "单位");
		firstMap.put("boxamount", "箱价");
		firstMap.put("existingnum", "实际库存");
		firstMap.put("existingamount", "实际库存金额");
		firstMap.put("transitnum", "在途量");
		firstMap.put("transitamount", "在途金额");
		firstMap.put("curstoragenum", "本期存货");
		firstMap.put("curstorageamount", "本期库存金额");
		firstMap.put("tqsalenum", "同期销售数量");
		firstMap.put("qqsalenum", "前期销售数量");
		firstMap.put("canordernum", "可订量");
		//firstMap.put("orderunitnum", "本次采购主单位数量");
		firstMap.put("ordernum", "本次采购箱数");
		firstMap.put("orderamount", "本次采购金额");
		firstMap.put("totalstoragenum", "合计库存数");
		firstMap.put("totalstorageamount", "合计库存金额");
		firstMap.put("cansaleday", "可销售时段");
		result.add(firstMap);
		
		List<PlannedOrderAnalysis> list1 = new ArrayList<PlannedOrderAnalysis>();
		map.put("doexportdata", "true");
		PageData pageData=purchaseReportService.showPlannedOrderAnalysisPageDataInBuyOrder(pageMap);
		list1.addAll(pageData.getList());
		list1.addAll(pageData.getFooter());
		if(null!=list1 && list1.size() > 0){
			for(PlannedOrderAnalysis item : list1){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(item);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
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
		
		//按品牌合计库存
		Map<String, Object> firstMap2 = new LinkedHashMap<String, Object>();
		firstMap2.put("brandname", "品牌名称");
		firstMap2.put("ordernum", "本次采购数");
		firstMap2.put("orderamount", "本次采购金额");
		firstMap2.put("curstoragenum", "当前库存数");
		firstMap2.put("curstorageamount", "当前库存金额");
		firstMap2.put("totalstoragenum", "合计库存数");
		firstMap2.put("totalstorageamount", "合计库存金额");
		result.add(firstMap2);
		
		map.put("pOrderAnalysisList", list1);
		List<Map<String,Object>> list2 = purchaseReportService.getExportStorageSummaryByBrandInBuyOrder(pageMap);
		if(null!=list2 && list2.size() > 0){
			for(Map<String,Object> map2 : list2){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				for(Map.Entry<String, Object> fentry : firstMap2.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
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
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 显示采购订单追踪列表
	 * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	public String showBuyOrderTrackReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 获取销售订单追踪明细数据
	 * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	public String showBuyOrderTrackReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = purchaseReportService.showBuyOrderTrackReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出采购订单追踪明细数据
	 * @return
	  * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	public void exportBuyOrderTrackReportData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageflag", "true");
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
		pageMap.setCondition(map);
		PageData pageData = purchaseReportService.showBuyOrderTrackReportData(pageMap);
		pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(exportBuyOrderTrackReportDataFilter(pageData.getList()), title);	
	}
	
	/**
	 * 采购订单追踪明细数据excel数据转换
	 * @param list
	 * @return
	 * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	private List<Map<String, Object>> exportBuyOrderTrackReportDataFilter(List<Map> list) throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("orderid", "订单编号");
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商编号");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
        firstMap.put("brandname", "品牌名称");
		firstMap.put("unitname", "单位");
		firstMap.put("taxprice", "订单单价");
		firstMap.put("ordernum", "订单数量");
		firstMap.put("ordertotalbox", "订单箱数");
		firstMap.put("orderamount", "订单金额");
		firstMap.put("enterprice", "入库单价");
		firstMap.put("enternum", "入库数量");
		firstMap.put("entertotalbox", "入库箱数");
		firstMap.put("enteramount", "入库金额");
		firstMap.put("arrivalorderprice", "进货单价");
		firstMap.put("arrivalordernum", "进货数量");
		firstMap.put("arrivalordertotalbox", "进货箱数");
		firstMap.put("arrivalorderamount", "进货金额");
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
}

