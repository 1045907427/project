/**
 * @(#)DeliveryReportAction.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月16日 huangzhiqian 创建版本
 */
package com.hd.agent.report.action;

import java.util.*;

import com.hd.agent.report.model.DeliveryAllReport;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.DeliveryCustomerOutReport;
import com.hd.agent.report.service.IDeliveryReportService;

/**
 * 代配送报表
 * 
 * @author huangzhiqian
 */
public class DeliveryReportAction extends BaseFilesAction{
	/**报表统计类型(客户退货入库)**/
	public static final int REPORT_CUSTOMER_ENTER=0X00;
	/**报表统计类型(客户配送出库)**/
	public static final int REPORT_CUSTOMER_OUT=0X01;
	
	private IDeliveryReportService deliveryReportService;

	public IDeliveryReportService getDeliveryReportService() {
		return deliveryReportService;
	}

	public void setDeliveryReportService(IDeliveryReportService deliveryReportService) {
		this.deliveryReportService = deliveryReportService;
	}
	
	
	/**
	 * 显示首页 客户出库
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月16日
	 */
	public String firstPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
//		String firstDay = CommonUtils.getMonthDateStr();
//		Map map = getAccessColumn("t_report_sales_base");
//		request.setAttribute("map", map);
//		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	
	/**
	 * 查询 客户出库
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月17日
	 */
	public String showBaseDeliveryCustomerOut()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = deliveryReportService.getDeliveryCustomerData(pageMap,REPORT_CUSTOMER_OUT);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	
	
	
	/**
	 * 显示首页 客户入库
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月16日
	 */
	public String firstPageEnter() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		request.setAttribute("today", today);
		return "success";
	}
	
	
	/**
	 * 查询 客户入库
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月17日
	 */
	public String showBaseDeliveryCustomerEnter()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = deliveryReportService.getDeliveryCustomerData(pageMap,REPORT_CUSTOMER_ENTER);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	
	
	/**
	 * 客户出入库导出
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年3月11日
	 */
	public void ExportDeliveryCustomerReport()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		pageMap.setCondition(map);		
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		
		String groupcols = map.get("groupcols");
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf("customerid")!=-1){
				firstMap.put("customerid", "客户编号");
				firstMap.put("customername", "客户名称");
			}
			if(groupcols.indexOf("goodsid")!=-1){
				firstMap.put("goodsid", "商品编号");
				firstMap.put("goodsname", "商品名称");
			}
			if(groupcols.indexOf("brandid")!=-1){
				firstMap.put("brandname", "品牌名称");
			}
			if(groupcols.indexOf("goodssort")!=-1){
				firstMap.put("goodssortname", "商品分类名称");
			}
			if(groupcols.indexOf("supplierid")!=-1){
				firstMap.put("supplierid", "供应商编号");
				firstMap.put("suppliername", "供应商名称");
			}
		}else{
			firstMap.put("customerid", "客户编号");
			firstMap.put("customername", "客户名称");
		}
		
		firstMap.put("saleprice", "销售金额");
		firstMap.put("cost", "成本金额");
		firstMap.put("totalbox", "箱数");
		firstMap.put("volume", "体积");
		firstMap.put("weight", "重量");
		result.add(firstMap);

		
		int type=Integer.parseInt(request.getParameter("deliveyType"));
		List<DeliveryCustomerOutReport> list = deliveryReportService.getDeliveryCustomerDataExcel(pageMap, type);
		if(list.size() != 0){
			for(DeliveryCustomerOutReport report : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(report);
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
		
		ExcelUtils.exportExcel(result, title);

	}
	/**
	 * 显示代配送进销存报表
	 * @return
	 * @throws Exception
	 * @author luoqiang
	 * @date 2016/9/5
	 */
	public String showAllDeliveryPage()throws Exception{
		String today=CommonUtils.getTodayDataStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}
	/**
	 * 获取待培训进销存汇总统计数据
	 * @return
	 * @throws Exception
	 * @author luoqiang
	 * @date 2016/9/5
	 */
	public String showAllDeliveryData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String businessdate2=(String)map.get("businessdate2");
		String businessdate1=(String)map.get("businessdate1");
		if(StringUtils.isNotEmpty(businessdate2)){//获取昨天的日期查询期初数据
			Date yesterday1=CommonUtils.getYestodayDate(CommonUtils.stringToDate((String)map.get("businessdate1")));
			map.put("yesterday1",CommonUtils.dataToStr(yesterday1,""));
			Date yesterday2=CommonUtils.getYestodayDate(CommonUtils.stringToDate((String)map.get("businessdate2")));
			map.put("yesterday2",CommonUtils.dataToStr(yesterday2,""));
		}
		pageMap.setCondition(map);
		PageData pageData = deliveryReportService.showAllDeliveryData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出代配送进销存汇总统计报表
	 * @throws Exception
	 * @author luoqiang
	 * @date 2016/9/5
	 */
	public void expertAllDeliveryData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		//pageMap.setCondition(map);
		String title = "",groupcols = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		//小计列
		groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!map.containsKey("groupcols")){
			groupcols = "goodsid";
		}
		else {
			groupcols = map.get("groupcols").toString();
		}
		String businessdate2=(String)map.get("businessdate2");
		String businessdate1=(String)map.get("businessdate1");
		if(StringUtils.isNotEmpty(businessdate2)){//获取昨天的日期查询期初数据
			Date yesterday1=CommonUtils.getYestodayDate(CommonUtils.stringToDate((String)map.get("businessdate1")));
			map.put("yesterday1",CommonUtils.dataToStr(yesterday1,""));
			Date yesterday2=CommonUtils.getYestodayDate(CommonUtils.stringToDate((String)map.get("businessdate2")));
			map.put("yesterday2",CommonUtils.dataToStr(yesterday2,""));
		}
		pageMap.setCondition(map);
		PageData pageData = deliveryReportService.showAllDeliveryData(pageMap);
		pageData.getList().addAll(pageData.getFooter());
		ExcelUtils.exportExcel(expertAllDeliveryDataFilter(pageData.getList(), groupcols), title);
	}

	/**
	 * 数据转换，list专程符合excel导出的数据格式(进销存汇总统计报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author luoqiang
	 * @date 2016/9/5
	 */
	private List<Map<String, Object>> expertAllDeliveryDataFilter(List<DeliveryAllReport> list,String groupcols) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		boolean goodsidflag = false;
		if(groupcols.indexOf("goodsid") != -1){
			goodsidflag = true;
		}
		if(StringUtils.isNotEmpty(groupcols)){
			String[] groupArr = groupcols.split(",");
			for(String col : groupArr){
				if(col.equals("goodsid")){
					firstMap.put("goodsid", "商品编号");
					firstMap.put("goodsname", "商品名称");
					firstMap.put("barcode", "条形码");
					firstMap.put("brandname", "品牌名称");
					firstMap.put("unitname", "单位");
				}else if(col.equals("storageid")){
					firstMap.put("storagename", "仓库名称");
				}else if(col.equals("brandid")){
					firstMap.put("brandname", "品牌名称");
				}else if(col.equals("supplierid")){
					firstMap.put("supplierid", "供应商编码");
					firstMap.put("suppliername", "供应商名称");
				}
			}
		}else{
			firstMap.put("goodsid", "商品编号");
			firstMap.put("goodsname", "商品名称");
			firstMap.put("barcode", "条形码");
			firstMap.put("brandname", "品牌名称");
			firstMap.put("unitname", "单位");
		}
		firstMap.put("existingnum", "期初数量");
		if(goodsidflag){
			firstMap.put("auxinitnumdetail", "期初箱数");
		}else{
			firstMap.put("existingbox", "期初箱数");
		}
		firstMap.put("taxamount", "期初含税金额");
		firstMap.put("notaxamount", "期初未税金额");

		firstMap.put("intotalnum", "采购进货数量");
		if(goodsidflag){
			firstMap.put("auxbuyinnumdetail", "采购进货箱数");
		}else{
			firstMap.put("intotalbox", "采购进货箱数");
		}
		firstMap.put("intaxamount", "采购进货含税金额");
		firstMap.put("innotaxamount", "采购进货未税金额");
		firstMap.put("outtotalnum", "采购退货数量");
		if(goodsidflag){
			firstMap.put("auxbuyoutnumdetail", "采购退货箱数");
		}else{
			firstMap.put("outtotalbox", "采购退货箱数");
		}
		firstMap.put("outtaxamount", "采购退货含税金额");
		firstMap.put("outnotaxamount", "采购退货未税金额");

		firstMap.put("saleouttotalnum", "销售出库数量");
		if(goodsidflag){
			firstMap.put("auxsaleoutnumdetail", "销售出库箱数");
		}else{
			firstMap.put("saleouttotalbox", "销售出库箱数");
		}
		firstMap.put("saleouttaxamount", "销售出库含税金额");
		firstMap.put("saleoutnotaxamount", "销售出库未税金额");
		firstMap.put("saleintotalnum", "销售退货数量");
		if(goodsidflag){
			firstMap.put("auxsaleinnumdetail", "销售退货箱数");
		}else{
			firstMap.put("saleintotalbox", "销售退货箱数");
		}
		firstMap.put("saleintaxamount", "销售退货含税金额");
		firstMap.put("saleinnotaxamount", "销售退货未税金额");
		firstMap.put("costoutamount", "合计成本含税金额");
		firstMap.put("costnotaxoutamount", "合计成本未税金额");

		firstMap.put("endnum", "期末数量");
		if(goodsidflag){
			firstMap.put("auxendnumdetail", "期末箱数");
		}else{
			firstMap.put("endtotalbox", "期末箱数");
		}
		firstMap.put("endamount", "期末含税金额");
		firstMap.put("endnotaxamount", "期末未税金额");
		result.add(firstMap);

		if(list.size() != 0){
			for(DeliveryAllReport storageBuySaleReport : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(storageBuySaleReport);
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

