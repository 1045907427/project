/**
 * @(#)FinancePaymentsAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.report.model.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.service.IFinancePaymentsService;

/**
 * 
 * 应付款相关action
 * @author chenwei
 */
public class FinancePaymentsAction extends BaseFilesAction {
	
	private IFinancePaymentsService financePaymentsService;

	public IFinancePaymentsService getFinancePaymentsService() {
		return financePaymentsService;
	}

	public void setFinancePaymentsService(
			IFinancePaymentsService financePaymentsService) {
		this.financePaymentsService = financePaymentsService;
	}
	/**
	 * 显示供应商应付款统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public String showSupplierPaymentListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 显示供应商应付款流水详细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public String showSupplierPaymentFlowDetailPage() throws Exception{
		String supplierid= request.getParameter("supplierid");
		String suppliername = request.getParameter("suppliername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("supplierid", supplierid);
		request.setAttribute("suppliername", suppliername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 获取供应商应付款统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public String showSupplierPaymentsData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.showSupplierPaymentsData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

	/**
	 * 获取供应商应付款明细数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-22
	 */
	public String getSupplierPaymentDetailList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.getSupplierPaymentDetailList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 导出供应商应付款明细表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-23
	 */
	public void exportSupplierPaymentDetailReportData()throws Exception{
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
		String tnjson = "{'supplierPaymentFlow':'应付款流水明细','supplierPaymentDetail':'应付款明细'}";
		Map<String,List<Map<String, Object>>> retMap = new HashMap<String, List<Map<String,Object>>>();

		List<Map<String, Object>> resultFlow = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstFlowMap = new LinkedHashMap<String, Object>();
		firstFlowMap.put("businessdate","业务日期");
		firstFlowMap.put("billtypename","单据类型");
		firstFlowMap.put("id","单据编号");
		firstFlowMap.put("goodsid","商品编码");
		firstFlowMap.put("goodsname","商品名称");
		firstFlowMap.put("unitname","单位");
		firstFlowMap.put("auxunitnumdetail","辅数量");
		firstFlowMap.put("unitnum","数量");
		firstFlowMap.put("price","单价");
		firstFlowMap.put("taxamount","金额");
		firstFlowMap.put("isinvoicename","开票状态");
		firstFlowMap.put("iswriteoffname","核销状态");
		firstFlowMap.put("remark","备注");
		resultFlow.add(firstFlowMap);
		PageData pageDataFlow = financePaymentsService.showSupplierPaysFlowListData(pageMap);
		List<SupplierPayFlow> listFlow = pageDataFlow.getList();
		listFlow.addAll(pageDataFlow.getFooter());
		if(null != listFlow && listFlow.size() != 0){
			for(SupplierPayFlow supplierPayFlow : listFlow){
				Map<String, Object> retMap2 = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(supplierPayFlow);
				for(Map.Entry<String, Object> fentry : firstFlowMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap2,entry);
							}
						}
					}
					else{
						retMap2.put(fentry.getKey(), "");
					}
				}
				resultFlow.add(retMap2);
			}

			retMap.put("supplierPaymentFlow_list", resultFlow);
		}

		List<Map<String, Object>> resultDetail = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstDetailMap = new LinkedHashMap<String, Object>();
		firstDetailMap.put("businessdate","业务日期");
		firstDetailMap.put("billtypename","单据类型");
		firstDetailMap.put("id","单据编号");
		firstDetailMap.put("initunpayamount","期初金额");
		firstDetailMap.put("buyamount","进货金额");
		firstDetailMap.put("returnamount","退货金额");
		firstDetailMap.put("realpayamount","采购总额");
		firstDetailMap.put("pushbalanceamount","冲差金额");
		firstDetailMap.put("payamount","已核销金额");
		firstDetailMap.put("unpayamount","未核销金额");
		firstDetailMap.put("remark","备注");
		resultDetail.add(firstDetailMap);
		PageData pageDataDetail = financePaymentsService.getSupplierPaymentDetailList(pageMap);
		List<SupplierPayments> listDetail = pageDataDetail.getList();
		if(null != listDetail && listDetail.size() != 0){
			for(SupplierPayments supplierPayments : listDetail){
				Map<String, Object> retMap2 = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(supplierPayments);
				for(Map.Entry<String, Object> fentry : firstDetailMap.entrySet()){
					if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map2.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap2,entry);
							}
						}
					}
					else{
						retMap2.put(fentry.getKey(), "");
					}
				}
				resultDetail.add(retMap2);
			}

			retMap.put("supplierPaymentDetail_list", resultDetail);
		}
		ExcelUtils.exportMoreExcel(retMap, title,tnjson);
	}

	/**
	 * 显示供应商开票核销情况统计表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2014
	 */
	public String showSupplierInvoiceWriteoffListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 获取供应商开票核销情况统计数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2014
	 */
	public String getSupplierInvoiceWriteoffData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.getSupplierInvoiceWriteoffData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 显示供应商应付款流水明细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 19, 2013
	 */
	public String showSupplierPaysFlowListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取供应商应付款流水数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 19, 2013
	 */
	public String showSupplierPaysFlowListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.showSupplierPaysFlowListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示按供应商付款分布情况
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public String showSupplierPayBankReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List bankList = getBaseFinanceService().getBankList();
		request.setAttribute("bankList", bankList);
		return "success";
	}
	/**
	 * 获取按供应商付款分布数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public String showSupplierPayBankListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.showSupplierPayBankListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出供应商收款分布统计报表
	 * @throws Exception
	 * @author zhanghonghui
	 * @date Jan 18, 2014
	 */
	public void exportSupplierPayBankData()throws Exception{
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
		PageData pageData = financePaymentsService.showSupplierPayBankListData(pageMap);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("amount", "付款总额");
		//firstMap.put("cashamount", "现金");
		List<Bank> bankList = getBaseFinanceService().getBankList();
		if(bankList.size() != 0){
			for(Bank bank : bankList){
				firstMap.put("bank"+bank.getId(), bank.getName());
			}
		}
		result.add(firstMap);
		
		//数据转换，list专程符合excel导出的数据格式
		List<Map<String,Object>> list = pageData.getList();
		list.addAll(pageData.getFooter());
		if(list.size() != 0){
			for(Map<String,Object> map2 : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
	 * 导出供应商应付款流水明细表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 18, 2014
	 */
	public void exportSupplierPayFlowData()throws Exception{
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
		PageData pageData = financePaymentsService.showSupplierPaysFlowListData(pageMap);
		ExcelUtils.exportExcel(exportSupplierPaysFlowDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(供应商应付款流水明细表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSupplierPaysFlowDataFilter(List<SupplierPayFlow> list,List<SupplierPayFlow> footerlist) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("billtypename", "单据类型");
		firstMap.put("id", "单据编号");
		firstMap.put("suppliername", "供应商");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("brandname", "品牌");
		firstMap.put("unitname", "单位");
		firstMap.put("price", "单价");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxunitnumdetail", "辅数量");
		firstMap.put("taxamount", "商品金额");
		firstMap.put("isinvoicename", "开票状态");
		firstMap.put("writeoffname", "核销状态");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(SupplierPayFlow supplierPayFlow : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(supplierPayFlow);
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
		if(footerlist.size() != 0){
			for(SupplierPayFlow supplierPayFlow : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(supplierPayFlow);
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
	 * 导出分供应商应付款统计表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportSupplierPaymentsData()throws Exception{
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
		PageData pageData = financePaymentsService.showSupplierPaymentsData(pageMap);
		ExcelUtils.exportExcel(exportSupplierPaymentsDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(分供应商应付款统计表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSupplierPaymentsDataFilter(List<SupplierPayments> list,List<SupplierPayments> footerlist) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("buydeptname", "采购部门");
		firstMap.put("initunpayamount", "期初金额");
		firstMap.put("buyamount", "进货金额");
		firstMap.put("returnamount", "退货金额");
		firstMap.put("realpayamount", "采购总额");
        firstMap.put("pushbalanceamount", "冲差金额");
		firstMap.put("invoiceamount", "发票总金额");
		firstMap.put("prepayamount", "当前余额");
		firstMap.put("payamount", "已核销金额");
		firstMap.put("unpayamount", "未核销金额金额");
		//firstMap.put("prepaybalance", "预付余额");
		firstMap.put("payableamount", "当前应付金额");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(SupplierPayments supplierPayments : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(supplierPayments);
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
		if(footerlist.size() != 0){
			for(SupplierPayments supplierPayments : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(supplierPayments);
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
	 * 导出分供应商开票核销情况统计表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2014
	 */
	public void exportSupplierInvoiceWriteoffData()throws Exception{
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
		PageData pageData = financePaymentsService.getSupplierInvoiceWriteoffData(pageMap);
		ExcelUtils.exportExcel(exportSupplierPaymentsDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 供应商应付动态-单据对账明细 页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-14
	 */
	public String showSupplierPaymentsBillDetailPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 供应商应付动态-单据对账明细
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-16
	 */
	public String showSupplierPaymentsBillDetailData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map && map.containsKey("ispageflag")){
			map.remove("ispageflag");
		}
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.showSupplierPaymentsBillDetailData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 供应商应付动态-单据对账明细 导出
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-17
	 */
	public String exportSupplierPaymentsBillDetailData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
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
		PageData pageData = financePaymentsService.showSupplierPaymentsBillDetailData(pageMap);
		ExcelUtils.exportExcel(exportSupplierPaymentsBillDetailDataFilter(pageData.getList(),pageData.getFooter()), title);
		return null;
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(分供应商应付款统计表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSupplierPaymentsBillDetailDataFilter(List<SupplierPaymentsBillDetail> list,List<SupplierPaymentsBillDetail> footerlist) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("billtypename", "单据类型");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("id", "单据编号");
		firstMap.put("orderid", "订单编号");
		firstMap.put("supplierid", "供应商");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("buydeptname", "采购部门");
		firstMap.put("buyamount", "采购金额");
		firstMap.put("invoiceamount", "开票金额");
		firstMap.put("uninvoiceamount", "未开票金额");
		firstMap.put("invoicedate", "开票日期");
		firstMap.put("payamount", "已核销金额");
		firstMap.put("unpayamount", "未核销金额");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(SupplierPaymentsBillDetail item : list){
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
		if(footerlist.size() != 0){
			for(SupplierPaymentsBillDetail item : footerlist){
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
	 * 供应商应付动态-采购发票页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-14
	 */
	public String showSupplierPaymentsInvoiceReportPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 供应商应付款动态-采购发票数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-26
	 */
	public String showSupplierPaymentsInvoiceReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(null!=map && map.containsKey("ispageflag")){
			map.remove("ispageflag");
		}
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.showSupplierPaymentsInvoiceReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 供应商应付款动态-采购发票导出数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-26
	 */
	public String exportSupplierPaymentsInvoiceReportData() throws Exception{
		String groupcols=null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
		map.put("ispageflag", "true");
		map.put("exportdataflag", "true");
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
		PageData pageData = financePaymentsService.showSupplierPaymentsInvoiceReportData(pageMap);
		ExcelUtils.exportExcel(exportSupplierPaymentsInvoiceReportDataFilter(pageData.getList(),pageData.getFooter()), title);
		return SUCCESS;
	}
	private List<Map<String, Object>> exportSupplierPaymentsInvoiceReportDataFilter(List<SupplierPaymentsInvoiceReport> list,List<SupplierPaymentsInvoiceReport> footerlist) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "开票日期");
		firstMap.put("id", "单据编号");
		firstMap.put("invoiceno", "发票号");
		firstMap.put("supplierid", "供应商编码");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("buydeptname", "采购部门");
		firstMap.put("taxamount", "到货总金额");
		firstMap.put("invoiceamount", "发票总金额");
		firstMap.put("writeoffamount", "核销总金额");
		firstMap.put("tailamount", "尾差金额");
		firstMap.put("pushamount", "冲差金额");
		firstMap.put("writeoffdate", "核销日期");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(SupplierPaymentsInvoiceReport supplierPaymentsInvoiceReport : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(supplierPaymentsInvoiceReport);
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
		if(footerlist.size() != 0){
			for(SupplierPaymentsInvoiceReport supplierPaymentsInvoiceReport : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(supplierPaymentsInvoiceReport);
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
     * 显示供应商对应付账统计报表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public String showSupplierPayBillReportPage()throws Exception{
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 获取供应商对应付账统计报表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public String getSupplierPayBillData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = financePaymentsService.getSupplierPayBillData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 显示供应商对应付账明细统计报表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public String showSupplierPayBillDetailReportPage()throws Exception{
        String supplierid = request.getParameter("supplierid");
        String suppliername = request.getParameter("suppliername");
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");
        request.setAttribute("supplierid",supplierid);
        request.setAttribute("suppliername",suppliername);
        request.setAttribute("businessdate1",businessdate1);
        request.setAttribute("businessdate2",businessdate2);
        return SUCCESS;
    }

    /**
     * 获取供应商对应付账明细统计报表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public String getSupplierPayBillDetailData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        List list = financePaymentsService.getSupplierPayBillDetailData(pageMap);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 导出供应商对应付账明细统计报表
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-05-04
     */
    public void exportSupplierPayBillData()throws Exception{
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
        PageData pageData = financePaymentsService.getSupplierPayBillData(pageMap);

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("supplierid", "供应商编号");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("buydeptname", "采购部门");
		firstMap.put("initbuyamount", "期初金额");
        firstMap.put("payableamount", "采购金额");
        firstMap.put("payamount", "付款金额");
        firstMap.put("pushbalanceamount", "冲差金额");
        firstMap.put("balanceamount", "余额");
        result.add(firstMap);

        List<SupplierPayBill> list = pageData.getList();
        list.addAll(pageData.getFooter());
        if(list.size() != 0){
            for(SupplierPayBill item : list){
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
        ExcelUtils.exportExcel(result, title);
    }

    public void exportSupplierPayBillDetailData()throws Exception{
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

        List<SupplierPayBill> list = financePaymentsService.getSupplierPayBillDetailData(pageMap);
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "业务日期");
        firstMap.put("billtypename", "单据类型");
        firstMap.put("id", "单据编号");
		firstMap.put("initbuyamount", "期初金额");
        firstMap.put("payableamount", "采购金额");
        firstMap.put("payamount", "付款金额");
        firstMap.put("pushbalanceamount", "冲差金额");
        firstMap.put("balanceamount", "余额");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        if(list.size() != 0){
            for(SupplierPayBill item : list){
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
        ExcelUtils.exportExcel(result, title);
    }

	/**
	 * 显示应收款余额报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-03
	 */
	public String showReceiptAmountReportPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}

	/**
	 * 获取应收款余额报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-03
	 */
	public String getReceiptAmountReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.getReceiptAmountReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出应收款余额报表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-03
	 */
	public void exportReceiptAmountReportData()throws Exception{
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
		PageData pageData = financePaymentsService.getReceiptAmountReportData(pageMap);

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesareaname", "销售区域");
		firstMap.put("initunwithdrawnamount", "期初应收");
		firstMap.put("salesamount", "本期销售");
		firstMap.put("withdrawnamount", "本期回笼");
		firstMap.put("balanceamount", "期末应收余额");
		firstMap.put("customeramount", "客户余额");
		firstMap.put("surplusamount", "结余金额");
		result.add(firstMap);

		List<Map> list = pageData.getList();
		list.addAll(pageData.getFooter());
		if(list.size() != 0){
			for(Map<String,Object> map2 : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
	 * 显示应收款余额明细报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-03
	 */
	public String showReceiptAmountDetailReportPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		request.setAttribute("customerid",customerid);
		request.setAttribute("customername",customername);
		request.setAttribute("businessdate1",businessdate1);
		request.setAttribute("businessdate2",businessdate2);
		return SUCCESS;
	}

	/**
	 * 获取应收款余额明细报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-03
	 */
	public String getReceiptAmountDetailData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financePaymentsService.getReceiptAmountDetailData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 导出应收款余额报表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-03
	 */
	public void exportReceiptAmountDetailData()throws Exception{
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
		PageData pageData = financePaymentsService.getReceiptAmountDetailData(pageMap);

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("billtypename", "单据类型");
		firstMap.put("id", "单据编号");
		firstMap.put("initunwithdrawnamount", "期初应收");
		firstMap.put("salesamount", "本期销售");
		firstMap.put("withdrawnamount", "本期回笼");
		firstMap.put("balanceamount", "期末应收余额");
		firstMap.put("remark", "备注");
		result.add(firstMap);

		List<Map> list = pageData.getList();
		if(list.size() != 0){
			for(Map<String,Object> map2 : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
}

