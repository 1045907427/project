/**
 * @(#)FinanceFundsReturnAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 19, 2013 chenwei 创建版本
 */
package com.hd.agent.report.action;

import com.hd.agent.account.service.ISalesBillCheckService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.*;
import com.hd.agent.report.service.IFinanceFundsReturnService;
import com.hd.agent.report.service.IPaymentdaysSetService;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.system.model.SysParam;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 资金回笼情况表action
 * @author chenwei
 */
public class FinanceFundsReturnAction extends BaseFilesAction {
	/**
	 * 资金回笼service
	 */
	private IFinanceFundsReturnService financeFundsReturnService;
	/**
	 * 超账期区间设置
	 */
	private IPaymentdaysSetService paymentdaysSetService;
	private ISalesBillCheckService salesBillCheckService;
	
	public ISalesBillCheckService getSalesBillCheckService() {
		return salesBillCheckService;
	}

	public void setSalesBillCheckService(
			ISalesBillCheckService salesBillCheckService) {
		this.salesBillCheckService = salesBillCheckService;
	}
	private BranduserAssess branduserAssess;
	
	private BranduserAssessExtend branduserAssessExtend;

	public BranduserAssessExtend getBranduserAssessExtend() {
		return branduserAssessExtend;
	}

	public void setBranduserAssessExtend(BranduserAssessExtend branduserAssessExtend) {
		this.branduserAssessExtend = branduserAssessExtend;
	}

	public BranduserAssess getBranduserAssess() {
		return branduserAssess;
	}

	public void setBranduserAssess(BranduserAssess branduserAssess) {
		this.branduserAssess = branduserAssess;
	}

	public IFinanceFundsReturnService getFinanceFundsReturnService() {
		return financeFundsReturnService;
	}

	public void setFinanceFundsReturnService(
			IFinanceFundsReturnService financeFundsReturnService) {
		this.financeFundsReturnService = financeFundsReturnService;
	}
	
	public IPaymentdaysSetService getPaymentdaysSetService() {
		return paymentdaysSetService;
	}

	public void setPaymentdaysSetService(
			IPaymentdaysSetService paymentdaysSetService) {
		this.paymentdaysSetService = paymentdaysSetService;
	}

	/**
	 * 显示分客户资金回笼情况表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public String showFundsCustomerReturnListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 显示客户销售情况流水表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 22, 2013
	 */
	public String showCustomerSalesFlowListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取客户销售情况流水数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 22, 2013
	 */
	public String showCustomerSalesFlowList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showCustomerSalesFlowList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示分品牌资金回笼表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 23, 2013
	 */
	public String showFundsBrandReturnListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 显示分品牌部门资金回笼表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 14, 2013
	 */
	public String showFundsBrandDeptReturnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 显示客户应收款未回笼超账期页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 30, 2013
	 */
	public String showCustomerReceivablePastDueListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	/**
	 * 获取应收款未回笼超账期统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 15, 2013
	 */
	public String showCustomerReceivablePastDueListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBaseReceivablePassDueListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 显示客户应收款已回笼超账期页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 16, 2013
	 */
	public String showCustomerWithdrawalPastDueListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	/**
	 * 显示分部门资金回笼统计报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public String showFundsDepartmentReturnListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 显示分客户业务员资金回笼统计报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 24, 2013
	 */
	public String showFundsSalesuerReturnListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 显示分品牌业务员资金回笼统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 25, 2013
	 */
	public String showFundsBranduserReturnListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 显示分品牌业务员明细统计页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2013
	 */
	public String showFundsBranduserReturnDetailPage()throws Exception{
		String branduser= request.getParameter("branduser");
		String brandusername = request.getParameter("brandusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		//表示为指定客户业务员 客户业务员为其他。。
		if("".equals(branduser)){
			branduser = "0";
		}
		request.setAttribute("branduser", branduser);
		request.setAttribute("brandusername", brandusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 显示银行收支情况表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 23, 2013
	 */
	public String showBankReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List bankList = getBaseFinanceService().getBankList();
		request.setAttribute("bankList", bankList);
		return "success";
	}
	/**
	 * 获取银行收支情况统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 23, 2013
	 */
	public String showBankReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBankReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示客户资金分布统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public String showCustomerReceiptBankReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List bankList = getBaseFinanceService().getBankList();
		request.setAttribute("bankList", bankList);
		return "success";
	}
	/**
	 * 获取客户资金分布统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 30, 2013
	 */
	public String showCustomerReceiptBankListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showCustomerReceiptBankListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示客户超账期明细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public String showCustomerPastDueListPage() throws Exception{
		String customerid = request.getParameter("customerid");
		String seq = request.getParameter("seq");
		String iswrite = request.getParameter("iswrite");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		
		
		request.setAttribute("type", "customer");
		request.setAttribute("customerid", customerid);
		request.setAttribute("seq", seq);
		request.setAttribute("iswrite", iswrite);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 获取客户超账期明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public String showCustomerPastDueDataList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showCustomerPastDueListPage(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示客户业务员应收款超账期统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public String showSalesUserReceivablePastDueListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	/**
	 * 获取客户业务员应收款超账期统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public String showSalesUserReceivablePastDueListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBaseReceivablePassDueListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示客户业务员超账期明细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 12, 2013
	 */
	public String showSalesUserPastDueListPage() throws Exception{
		request.setAttribute("type", "salesuser");
		String salesuserid = request.getParameter("salesuserid");
		String seq = request.getParameter("seq");
		String iswrite = request.getParameter("iswrite");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		request.setAttribute("salesuserid", salesuserid);
		request.setAttribute("seq", seq);
		request.setAttribute("iswrite", iswrite);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	/**
	 * 获取客户业务员超账期明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public String showSalesUserPastDueDataList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showSalesUserPastDueListPage(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 客户业务员回笼资金超账期统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 18, 2013
	 */
	public String showSalesUserWithdrawalPastDueListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	
	/**
	 * 导出银行收支情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportBankReportData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showBankReportData(pageMap);
		ExcelUtils.exportExcel(exportBankReportDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(银行收支情况表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportBankReportDataFilter(List<BankReport> list,List<BankReport> footerlist) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("bankname", "银行名称");
		firstMap.put("receiptamount", "收款金额");
		firstMap.put("receiptwriteamount", "收款核销金额");
		firstMap.put("unreceiptwriteamount", "收款未核销金额");
		firstMap.put("payamount", "付款金额");
		firstMap.put("paywriteamount", "付款核销金额");
		firstMap.put("unpaywriteamount", "付款未核销金额");
		firstMap.put("endamount", "余额");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(BankReport bankReport : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(bankReport);
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
			for(BankReport bankReport : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(bankReport);
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
	 * 导出客户收款分布统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportCustomerReceiptBankData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showCustomerReceiptBankListData(pageMap);
		ExcelUtils.exportExcel(exportCustomerReceiptBankDataFilter(pageData), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(客户收款分布统计报表)
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportCustomerReceiptBankDataFilter(PageData pageData) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesdeptname", "销售部门");
		firstMap.put("amount", "收款总额");
		List<Bank> bankList = getBaseFinanceService().getBankList();
		if(bankList.size() != 0){
			for(Bank bank : bankList){
				firstMap.put("bank"+bank.getId(), bank.getName());
			}
		}
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		return result;
	}
	
	/**
	 * 导出客户销售情况流水表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportCustomerSalesFlowData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showCustomerSalesFlowList(pageMap);
		ExcelUtils.exportExcel(exportCustomerSalesFlowDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(客户销售情况流水表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportCustomerSalesFlowDataFilter(List<CustomerSalesFlow> list,List<CustomerSalesFlow> footerlist) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("customersortname", "客户分类");
		firstMap.put("salesareaname", "销售区域");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("billtypename", "单据类型");
        firstMap.put("deliverystoragename", "发货仓库");
		firstMap.put("id", "单据编号");
		firstMap.put("orderid", "订单编号");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("spell", "助记符");
		firstMap.put("barcode", "条形码");
		firstMap.put("brandname", "品牌");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "单位");
		firstMap.put("price", "单价");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxnumdetail", "箱数");
        firstMap.put("boxprice", "箱价");
		firstMap.put("taxamount", "商品金额");

		if(isSysUserHaveUrl("/report/finance/costandmarginratebtn.do")){
			firstMap.put("costprice", "成本价");
			firstMap.put("costamount", "成本金额");
			firstMap.put("marginamount", "毛利额");
			firstMap.put("marginamountrate", "毛利率%");
		}

		firstMap.put("isinvoicebillname", "开票状态");
		firstMap.put("writeoffname", "核销状态");
		firstMap.put("invoicebilldate", "开票日期");
		firstMap.put("writeoffdate", "核销日期");
		firstMap.put("duefromdate", "应收日期");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(CustomerSalesFlow customerSalesFlow : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(customerSalesFlow);
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
			for(CustomerSalesFlow customerSalesFlow : footerlist){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(customerSalesFlow);
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
	 * 销售清单导出
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 17, 2014
	 */
	public void exportCustomerSalesFlowDetailData()throws Exception{
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
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("model", "规格型号");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("noprice", "未税单价");
		firstMap.put("notaxamount", "未税金额");
		firstMap.put("taxtypename", "税率");
		firstMap.put("tax", "税额");
		result.add(firstMap);
		
		List<CustomerSalesFlow> list = financeFundsReturnService.showCustomerSalesFlowDetailList(pageMap);
		if(list.size() != 0){
			for(CustomerSalesFlow customerSalesFlow : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(customerSalesFlow);
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
	 * 生成客户销售流水文件
	 *
	 * @throws Exception
	 * @author limin
	 * @date Oct 12, 2017
	 */
	public String generateCustomerFlowListFile() throws Exception {

		Map result = new HashMap();
		result.put("flag", false);

		Map condition = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(condition);
		String flowDataExportStep = getSysParamValue("FlowDataExportStep");
		int rows = 99999999;
		if(StringUtils.isNotEmpty(flowDataExportStep)) {
			rows = Integer.parseInt(flowDataExportStep);
		}
		pageMap.setRows(rows);

		String title = "客户销售情况流水表";

		try {
			File file = financeFundsReturnService.exportCustomerFlowListData(title, pageMap);
			result.put("flag", true);
			result.put("path", file.getPath());
			request.getSession().setAttribute("CustomerSalesFlowFilePath", file.getPath());
			addJSONObject(result);
		} catch (Exception e){
			e.printStackTrace();
			result.put("flag", true);
			result.put("msg", "导出出错！");
		} finally {
//            request.getSession().setAttribute("exportExclusiveCount", "0");
		}

		return SUCCESS;
	}

	/**
	 * 下载客户销售流水文件
	 *
	 * @throws Exception
	 * @author limin
	 * @date Sep 10, 2017
	 */
	public void downloadCustomerFlowListFile() throws Exception {

		String path = (String) request.getSession().getAttribute("CustomerSalesFlowFilePath");
		File file = new File(path);
		ExcelUtils.downloadExcel(file.getParent(), file.getName(), "客户销售情况流水表.xlsx");
		ExcelUtils.deleteExcelFile(file.getParent(), file.getName());
	}
	
	/**
	 * 导出客户应收款超账期统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportCustomerReceivablePastDueData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showCustomerReceivablePastDueListData(pageMap);
		ExcelUtils.exportExcel(exportCustomerReceivablePastDueDataFilter(pageData), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(客户应收款超账期统计报表)
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportCustomerReceivablePastDueDataFilter(PageData pageData) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("amount", "应收款");
		firstMap.put("unpassdayamount", "未超账期金额");
		firstMap.put("passdayamount", "超账期金额");
		List<PaymentdaysSet> list2 = paymentdaysSetService.getPaymentdaysSetInfo();
		if(list2.size() != 0){
			for(PaymentdaysSet paymentdaysSet : list2){
				firstMap.put("passamount"+paymentdaysSet.getSeq(), paymentdaysSet.getDetail());
			}
		}
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		return result;
	}
	
	/**
	 * 导出客户回笼资金超账期统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportWithdrawalPastDueData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showCustomerWithdrawalPastDueListData(pageMap);
		ExcelUtils.exportExcel(exportCustomerWithdrawalPastDueDataFilter(pageData), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(客户回笼资金超账期统计报表)
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportCustomerWithdrawalPastDueDataFilter(PageData pageData) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("amount", "已回笼金额");
		firstMap.put("unpassdayamount", "未超账期金额");
		firstMap.put("passdayamount", "超账期金额");
		List<PaymentdaysSet> list2 = paymentdaysSetService.getPaymentdaysSetInfo();
		if(list2.size() != 0){
			for(PaymentdaysSet paymentdaysSet : list2){
				firstMap.put("passamount"+paymentdaysSet.getSeq(), paymentdaysSet.getDetail());
			}
		}
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		return result;
	}
	
	/**
	 * 导出客户业务员应收款超账期统计报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportSalesUserReceivablePastDueData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showSalesUserReceivablePastDueListData(pageMap);
		ExcelUtils.exportExcel(exportSalesUserReceivablePastDueDataFilter(pageData), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(客户业务员应收款超账期统计报表)
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSalesUserReceivablePastDueDataFilter(PageData pageData) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("amount", "应收款");
		firstMap.put("unpassdayamount", "未超账期金额");
		firstMap.put("passdayamount", "超账期金额");
		List<PaymentdaysSet> list2 = paymentdaysSetService.getPaymentdaysSetInfo();
		if(list2.size() != 0){
			for(PaymentdaysSet paymentdaysSet : list2){
				firstMap.put("passamount"+paymentdaysSet.getSeq(), paymentdaysSet.getDetail());
			}
		}
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		return result;
	}
	
	/**
	 * 显示分客户资金回笼销售发货回单明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 17, 2013
	 */
	public String showFundsCustomerListPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String type = request.getParameter("type");
		String deptid = request.getParameter("deptid");
		String salesuserid = request.getParameter("salesuserid");
		String brand = request.getParameter("brand");
		String businessdate1 = request.getParameter("businessdate1");
		//String businessdate2 = CommonUtils.getTodayDataStr();
		String businessdate2 = request.getParameter("businessdate2");

		if(StringUtils.isNotEmpty(customerid)){
			request.setAttribute("customerid", customerid);
		}
		if(StringUtils.isNotEmpty(deptid)){
			request.setAttribute("deptid", deptid);
		}
		if(StringUtils.isNotEmpty(salesuserid)){
			request.setAttribute("salesuserid", salesuserid);
		}
		if(StringUtils.isNotEmpty(brand)){
			request.setAttribute("brand", brand);
		}
		if(StringUtils.isNotEmpty(businessdate1) && !"null".equals(businessdate1)){
			request.setAttribute("businessdate1", businessdate1);
		}
		if(StringUtils.isNotEmpty(businessdate2) && !"null".equals(businessdate2)){
			request.setAttribute("businessdate2", businessdate2);
		}
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 分客户资金回笼销售发货回单明细数据(历史在途应收款)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 17, 2013
	 */
	public String showUnauditamountDataList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showUnauditamountDataList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

    /**
     * 分客户资金回笼销售发货回单明细数据(历史验收应收款)
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Oct 18, 2013
     */
	public String showAuditamountDataList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showAuditamountDataList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}


    /**
     * 分客户资金应收情况表 数据导出
     * @return
     * @throws Exception
     * @author lin_xx
     * @date sept 14, 2015
     */
    public String exportFundsCustomerList() throws  Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        pageMap.setRows(100);

        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }

        Map<String,List<Map<String, Object>>> retMap = new HashMap<String, List<Map<String,Object>>>();
        String tnjson = "{",str = "";
        for(int i = 0 ;i<4;i++){

            List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
            Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
            List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

            if(i == 0){//未验收应收款表格
                firstMap.put("id","编号");
                firstMap.put("businessdate","业务日期");
                firstMap.put("customerid","客户编码");
                firstMap.put("customername","客户名称");
                firstMap.put("salesdept","销售部门");
                firstMap.put("salesuser","客户业务员");
                firstMap.put("field01","含税金额");
                firstMap.put("duefromdate","应收日期");
                firstMap.put("status","状态");
                firstMap.put("isinvoice","抽单状态");
                firstMap.put("addusername","制单人");
                result.add(firstMap);

                //未验收应收款
                PageData pageData = financeFundsReturnService.showUnauditamountDataList(pageMap);
                List<Receipt> list2 = pageData.getList() ;
                for(Receipt receipt : list2){
                    if("2".equals(receipt.getStatus())){
                        receipt.setStatus("保存");
                    }else if("3".equals(receipt.getStatus())){
                        receipt.setStatus("审核通过");
                    }else if("4".equals(receipt.getStatus())){
                        receipt.setStatus("关闭");
                    }else{
                        receipt.setStatus("");
                    }
                    if("0".equals(receipt.getIsinvoice()) || "3".equals(receipt.getIsinvoice())){
                        receipt.setIsinvoice("未申请");
                    }else if("2".equals(receipt.getIsinvoice())){
                        receipt.setIsinvoice("已核销");
                    }else if("4".equals(receipt.getIsinvoice())){
                        receipt.setIsinvoice("申请中");
                    }else if("5".equals(receipt.getIsinvoice())){
                        receipt.setIsinvoice("部分核销");
                    }else{
                        receipt.setIsinvoice("已申请");
                    }
                }
                list2.addAll(pageData.getFooter());
                list = receiptToMapList(list2,firstMap);
                result.addAll(list);

                retMap.put("salesone_list",result);

            }else if(i == 1){//已验收应收款报表
                firstMap.put("id","编号");
                firstMap.put("businessdate","业务日期");
                firstMap.put("customername","客户名称");
                firstMap.put("salesdept","销售部门");
                firstMap.put("salesuser","客户业务员");
                firstMap.put("field01","客户验收金额");
//                firstMap.put("field02","客户验收未税金额");
//                firstMap.put("field03","客户验收税额");
                firstMap.put("field04","回单金额");
                firstMap.put("duefromdate","应收日期");
                firstMap.put("status","状态");
                firstMap.put("isinvoice","抽单状态");
                firstMap.put("addusername","制单人");
                result.add(firstMap);

                //已验收应收款数据
                PageData pageData = financeFundsReturnService.showAuditamountDataList(pageMap);
                List<Receipt> list2 = pageData.getList() ;
                for(Receipt receipt : list2){
                    if("2".equals(receipt.getStatus())){
                        receipt.setStatus("保存");
                    }else if("3".equals(receipt.getStatus())){
                        receipt.setStatus("审核通过");
                    }else if("4".equals(receipt.getStatus())){
                        receipt.setStatus("关闭");
                    }else{
                        receipt.setStatus("");
                    }
                    if("0".equals(receipt.getIsinvoice()) || "3".equals(receipt.getIsinvoice())){
                        receipt.setIsinvoice("未申请");
                    }else if("2".equals(receipt.getIsinvoice())){
                        receipt.setIsinvoice("已核销");
                    }else if("4".equals(receipt.getIsinvoice())){
                        receipt.setIsinvoice("申请中");
                    }else if("5".equals(receipt.getIsinvoice())){
                        receipt.setIsinvoice("部分核销");
                    }else{
                        receipt.setIsinvoice("已申请");
                    }
                }

                list2.addAll(pageData.getFooter());
                list = receiptToMapList(list2,firstMap);
                result.addAll(list);
                retMap.put("salestwo_list",result);
            }else if(i==3){//退货应收款表格
                firstMap.put("id","编号");
                firstMap.put("businessdate","业务日期");
                firstMap.put("sourceid","来源单据编号");
                firstMap.put("customername","客户名称");
                firstMap.put("salesdept","销售部门");
                firstMap.put("salesuser","客户业务员");
                firstMap.put("field01","含税金额");
               // firstMap.put("field03","客户验收税额");
                firstMap.put("status","状态");
                firstMap.put("iswrite","是否核销");
                firstMap.put("addusername","制单人");
                result.add(firstMap);

                //退货应收款数据
                PageData pageData = financeFundsReturnService.showRejectamountDataList(pageMap);
                List<SaleRejectEnter> list2 = pageData.getList();
                for(SaleRejectEnter enter : list2){
                    if("0".equals(enter.getIswrite())){
                        enter.setIswrite("否");
                    }else{
                        enter.setIswrite("是");
                    }

                    if("2".equals(enter.getStatus())){
                        enter.setStatus("保存");
                    }else if("3".equals(enter.getStatus())){
                        enter.setStatus("审核通过");
                    }else if("4".equals(enter.getStatus())){
                        enter.setStatus("关闭");
                    }else{
                        enter.setStatus("");
                    }
                }
                list2.addAll(pageData.getFooter());
                list = saleRejectEnterToMapList(list2,firstMap);
                result.addAll(list);
                retMap.put("salesthree_list",result);

            }else{ //商品流水表
                firstMap.put("businessdate","业务日期");
                firstMap.put("customername","客户名称");
                firstMap.put("billtype","单据类型");
                firstMap.put("id","单据编号");
                firstMap.put("goodsid","商品编码");
                firstMap.put("goodsname","商品名称");
                firstMap.put("unitname","单位");
                firstMap.put("price","单价");
                firstMap.put("unitnum","数量");
                firstMap.put("taxamount","商品金额");
                firstMap.put("isinvoice","抽单状态");
                firstMap.put("iswriteoff","核销状态");
                firstMap.put("remarks","备注");
                result.add(firstMap);

                //商品流水明细
                PageData pageData = financeFundsReturnService.showSalesGoodsFlowDetailDataList(pageMap);
                List<SalesGoodsFlowDetail> list2 = pageData.getList();
                for(SalesGoodsFlowDetail detail : list2){
                    if("0".equals(detail.getIswriteoff())){
                        detail.setIswriteoff("未核销");
                    }else{
                        detail.setIswriteoff("已核销");
                    }

                    if("1".equals(detail.getBilltype())){
                        detail.setBilltype("发货单");
                    }else if("2".equals(detail.getBilltype())){
                        detail.setBilltype("直退退货单");
                    }else if("3".equals(detail.getBilltype())){
                        detail.setBilltype("售后退货单");
                    }else{
                        detail.setBilltype("冲差单");
                    }

                    if("0".equals(detail.getIsinvoice()) || "3".equals(detail.getIsinvoice())){
                        detail.setIsinvoice("未申请");
                    }else if("2".equals(detail.getIsinvoice())){
                        detail.setIsinvoice("已核销");
                    }else if("4".equals(detail.getIsinvoice())){
                        detail.setIsinvoice("申请中");
                    }else if("5".equals(detail.getIsinvoice())){
                        detail.setIsinvoice("部分核销");
                    }else{
                        detail.setIsinvoice("已申请");
                    }

                }
                list2.addAll(pageData.getFooter());
                list = goodsToMapList(list2,firstMap);
                result.addAll(list);
                retMap.put("salesfour_list",result);
            }
        }
        tnjson += "'salesone':'未验收应收款','salestwo':'已验收应收款','salesthree':'退货应收款','salesfour':'商品流水明细'}";


        ExcelUtils.exportMoreExcel(retMap, title,tnjson);
        return SUCCESS;
    }

    private List<Map<String, Object>> goodsToMapList(List<SalesGoodsFlowDetail> list,Map<String, Object> firstMap)throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for(SalesGoodsFlowDetail rejectEnter : list){
            if(null != rejectEnter){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(rejectEnter);
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

    private List<Map<String, Object>> saleRejectEnterToMapList(List<SaleRejectEnter> list,Map<String, Object> firstMap)throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for(SaleRejectEnter rejectEnter : list){
            if(null != rejectEnter){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(rejectEnter);
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


    private List<Map<String, Object>> receiptToMapList(List<Receipt> list,Map<String, Object> firstMap)throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for(Receipt receipt : list){
            if(null != receipt){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(receipt);
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
	 * 分客户资金回笼销售发货回单明细数据(历史退货应收款)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 18, 2013
	 */
	public String showRejectamountDataList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showRejectamountDataList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据客户商品流水明细
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 21, 2013
	 */
	public String showSalesGoodsFlowDetailDataList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showSalesGoodsFlowDetailDataList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示分品牌资金回笼销售发货回单明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 21, 2013
	 */
	public String showFundsBrandListPage()throws Exception{
		String type = request.getParameter("type");
		String brand = request.getParameter("brand");
		String deptid = request.getParameter("deptid");
		String branduser = request.getParameter("branduser");
		String supplierid = request.getParameter("supplierid");
		String businessdate1 = request.getParameter("businessdate1");
		//String businessdate2 = CommonUtils.getTodayDataStr();
		String businessdate2 = request.getParameter("businessdate2");
		
		request.setAttribute("brand", brand);
		if(StringUtils.isNotEmpty(deptid)){
			request.setAttribute("deptid", deptid);
		}
		if(StringUtils.isNotEmpty(branduser)){
			request.setAttribute("branduserid", branduser);
		}
		if(StringUtils.isNotEmpty(supplierid)){
			request.setAttribute("supplierid", supplierid);
		}
		if(StringUtils.isNotEmpty(businessdate1) && !"null".equals(businessdate1)){
			request.setAttribute("businessdate1", businessdate1);
		}
		if(StringUtils.isNotEmpty(businessdate2) && !"null".equals(businessdate2)){
			request.setAttribute("businessdate2", businessdate2);
		}
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 分品牌资金回笼销售发货回单明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public String showSalesGoodsFlowDetailDataListByBrand()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showSalesGoodsFlowDetailDataListByBrand(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 分品牌部门资金回笼销售发货回单明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public String showSalesGoodsFlowDetailDataListByBrandDept()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showSalesGoodsFlowDetailDataListByBrandDept(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 分品牌业务员资金回笼销售发货回单明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 22, 2013
	 */
	public String showSalesGoodsFlowDetailDataListByBrandUser()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showSalesGoodsFlowDetailDataListByBrandUser(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 显示基础资金回笼情况表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 18, 2013
	 */
	public String showBaseSalesWithdrawnPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取资金回笼情况数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 18, 2013
	 */
	public String showBaseSalesWithdrawnData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBaseSalesWithdrawnData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/*----------------------------单资金回笼开始------------------------------------------------*/
	
	/**
	 * 显示基础资金回笼情况表页面(单资金回笼)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 15, 2014
	 */
	public String showBaseFinanceDrawnPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}

	/**
	 * 显示人员部门资金回笼基础情况表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-17
	 */
	public String showBasePersonDeptFinanceDrawnPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_branduser_dept");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}

	/**
	 * 获取单资金回笼情况统计数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 15, 2014
	 */
	public String showBaseFinanceDrawnData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBaseFinanceDrawnData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 分客户单资金回来情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showCustomerWithdrawnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 分客户单资金回来情况统计数据详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showCustomerWithdrawnDetailListPage()throws Exception{
		String customerid= request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("customerid", customerid);
		request.setAttribute("customername", customername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}
	
	/**
	 * 分品牌单资金回来情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showBrandWithdrawnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 分品牌单资金回来情况统计数据详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showBrandWithdrawnDetailListPage()throws Exception{
		String brandid= request.getParameter("brandid");
		if(StringUtils.isEmpty(brandid)){
			brandid = "0000";
		}
		String brandname = request.getParameter("brandname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("brandid", brandid);
		request.setAttribute("brandname", brandname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 显示分供应商单资金回笼情况页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 3, 2014
	 */
	public String showSupplierWithdrawnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 分供应商单资金回笼情况详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 3, 2014
	 */
	public String showSupplierWithdrawnDetailListPage()throws Exception{
		String supplierid= request.getParameter("supplierid");
		if(StringUtils.isEmpty(supplierid)){
			supplierid = "0000";
		}
		String suppliername = request.getParameter("suppliername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("supplierid", supplierid);
		request.setAttribute("suppliername", suppliername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}
	
	/**
	 * 分品牌部门单资金回笼情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showBranddeptWithdrawnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 分品牌部门单资金回笼情况统计详情数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showBranddeptWithdrawnDetailListPage()throws Exception{
		String branddept= request.getParameter("branddept");
		if(StringUtils.isEmpty(branddept)){
			branddept = "0000";
		}
		String branddeptname = request.getParameter("branddeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("branddept", branddept);
		request.setAttribute("branddeptname", branddeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 分销售部门单资金回笼情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showSalesdeptWithdrawnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 分销售部门单资金回笼情况统计详情数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showSalesdeptWithdrawnDetailListPage()throws Exception{
		String salesdept= request.getParameter("salesdept");
		if(StringUtils.isEmpty(salesdept)){
			salesdept = "0000";
		}
		String salesdeptname = request.getParameter("salesdeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("salesdept", salesdept);
		request.setAttribute("salesdeptname", salesdeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 分销售业务员单资金回笼情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showSalesuserWithdrawnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 分销售业务员单资金回笼情况统计详情数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showSalesuserWithdrawnDetailListPage()throws Exception{
		String salesuser= request.getParameter("salesuser");
		if(StringUtils.isEmpty(salesuser)){
			salesuser = "0000";
		}
		String salesusername = request.getParameter("salesusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("salesuser", salesuser);
		request.setAttribute("salesusername", salesusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}

    /**
     * 分厂家业务员单资金回笼情况统计详情数据页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-06
     */
    public String showSupplieruserWithdrawnListPage()throws Exception{
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);
        return SUCCESS;
    }

    /**
     * 分厂家业务员单资金回笼明细情况统计详情数据页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-06
     */
    public String showSupplieruserWithdrawnDetailListPage()throws Exception{
        String supplieruser= request.getParameter("supplieruser");
        if(StringUtils.isEmpty(supplieruser)){
            supplieruser = "0000";
        }
        String supplierusername = request.getParameter("supplierusername");
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");
        if(null==businessdate1){
            businessdate1 = "";
        }
        if(null==businessdate2){
            businessdate2 = "";
        }
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("supplieruser", supplieruser);
        request.setAttribute("supplierusername", supplierusername);
        request.setAttribute("businessdate1", businessdate1);
        request.setAttribute("businessdate2", businessdate2);
        return SUCCESS;
    }

	/**
	 * 分品牌业务员单资金回笼情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showBranduserWithdrawnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 分品牌业务员单资金回笼情况统计详情数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showBranduserWithdrawnDetailListPage()throws Exception{
		String branduser= request.getParameter("branduser");
		if(StringUtils.isEmpty(branduser)){
			branduser = "0000";
		}
		String brandusername = request.getParameter("brandusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("branduser", branduser);
		request.setAttribute("brandusername", brandusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}

	/**
	 * 导出单资金回笼情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 23, 2013
	 */
	public void exportBaseFinanceDrawnData()throws Exception{
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
        PageData pageData = financeFundsReturnService.showBaseFinanceDrawnData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }
	
	/**
	 * 导出分供应商资金回笼情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 5, 2014
	 */
	public void exportSupplierDrawnData()throws Exception{
		String groupcols = "supplierid";
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
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
		List<BaseSalesWithdrawnReport> list = financeFundsReturnService.getExportSupplierDrawnData(pageMap);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		Map filedMap = new HashMap();
		filedMap.put("withdrawn", true);
		firstMap = getFirstMap(groupcols, filedMap);
		result.add(firstMap);
		
		List<Map<String, Object>> list2 = objectToMapList(list,firstMap);
		result.addAll(list2);
		ExcelUtils.exportExcel(result, title);
	}
	
	
	/**
	 * 导出单资金回笼明细列表数据
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 17, 2014
	 */
	public void exportFinanceWithdrawnDetailReportData()throws Exception{
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
		Map<String,List<Map<String, Object>>> retMap = new HashMap<String, List<Map<String,Object>>>();
		String tnjson = "{",str = "";
		if(map.containsKey("groupcols")){
			String groupcols = (String)map.get("groupcols");
			if(groupcols.indexOf(";") != -1){
				String[] groupcolArr = groupcols.split(";");
				for(String group : groupcolArr){
					String str1 = "sales",str2 = "";
					List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
					pageMap.getCondition().put("groupcols", group);
					Map flagMap = new HashMap();
					if(pageMap.getCondition().containsKey("branduser")){
						flagMap.put("buflag", true);
					}
					if(pageMap.getCondition().containsKey("branddept")){
						flagMap.put("bdflag", true);
					}
					if(pageMap.getCondition().containsKey("customerid")){
						flagMap.put("cflag", true);
					}
					if(pageMap.getCondition().containsKey("brandid")){
						flagMap.put("bflag", true);
					}
					if(pageMap.getCondition().containsKey("salesdept")){
						flagMap.put("sdflag", true);
					}
					if(pageMap.getCondition().containsKey("supplierid")){
						flagMap.put("spflag", true);
					}
					if(pageMap.getCondition().containsKey("supplieruser")){
						flagMap.put("spuflag", true);
					}
					flagMap.put("withdrawn", true);
					Map<String, Object> firstMap = getFirstMapForDetail(group,flagMap);
					result.add(firstMap);
                    PageData pageData = null;
                    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                    if(!"company".equals(group)){
                        pageData = financeFundsReturnService.showBaseFinanceDrawnData(pageMap);
                        List<BaseSalesWithdrawnReport> list2 =  pageData.getList();
                        list2.addAll(pageData.getFooter());
                        list = objectToMapList(list2,firstMap);
                    }else{
                        Map map2 = financeFundsReturnService.getCompanyWithdrawnList(pageMap);
                        List<BaseSalesWithdrawnReport> list2 =  (List<BaseSalesWithdrawnReport>)map2.get("list");
                        list2.addAll((List<BaseSalesWithdrawnReport>)map2.get("footer"));
                        list = objectToMapList(list2,firstMap);
                    }
					result.addAll(list);
					if(group.indexOf(",") == -1){
						if("brandid".equals(group)){
							str1 += "brand";
							str2 += "分品牌统计";
						}else if("goodsid".equals(group)){
							str1 += "goods";
							str2 += "分商品统计";
						}else if("customerid".equals(group)){
							str1 += "customer";
							str2 += "分客户统计";
						}else if("company".equals(group)){
                            str1 += "company";
                            str2 += "分公司统计";
                        }
						str += "'"+str1+"':'"+str2+"',";
					}else{
						for(String group2 : group.split(",")){
							if("brandid".equals(group2)){
								str1 += "brand";
								str2 += "分品牌";
							}else if("goodsid".equals(group2)){
								str1 += "goods";
								str2 += "分商品";
							}else if("customerid".equals(group2)){
								str1 += "customer";
								str2 += "分客户";
							}
						}
						str += "'"+str1+"':'"+str2+"统计',";
					}
					retMap.put(""+str1+"_list", result);
				}
			}
			else{
				if("brandid".equals(groupcols)){
					str += "'salesbrand':'分品牌统计',";
				}else if("goodsid".equals(groupcols)){
					str += "'salesgoods':'分商品统计',";
				}else if("customerid".equals(groupcols)){
					str += "'salescustomer':'分客户统计',";
				}else if("company".equals(groupcols)){
                    str += "'salescompany':'分公司统计',";
                }
			}
			if(str.lastIndexOf(",") != -1){
				tnjson += str.substring(0, str.lastIndexOf(",")) + "}";
			}
			else{
				tnjson += str + "}";
			}
		}
		//String tnjson = "{'salesBrand':'分品牌统计','salesGoods':'分商品统计'}";
		ExcelUtils.exportMoreExcel(retMap, title,tnjson);
	}
	
	
	/**
	 * 显示分公司资金回笼情况报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2015
	 */
	public String showCompanyWithdrawnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取分公司资金回笼报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2015
	 */
	public String getCompanyWithdrawnList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		Map map2 = financeFundsReturnService.getCompanyWithdrawnList(pageMap);
		jsonResult = new HashMap();
		jsonResult.put("rows", map2.get("list"));
		jsonResult.put("footer", map2.get("footer"));
		return SUCCESS;
	}
	
	/**
	 * 导出分公司资金回笼情况表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2015
	 */
	public void exportCompanyWithdrawnData()throws Exception{
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
        pageMap.setCondition(queryMap);
		Map map2 = financeFundsReturnService.getCompanyWithdrawnList(pageMap);
        List row = (List) map2.get("list");
        if(null != map2.get("footer")){
            List footer = (List) map2.get("footer");
            row.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,row);

	}
	
	/**
	 * 显示分公司分品牌部门明细列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2015
	 */
	public String showCompanyWithdrawnDetailListPage()throws Exception{
		String branddept= request.getParameter("branddept");
		String branddeptname = request.getParameter("branddeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("branddept", branddept);
		request.setAttribute("branddeptname", branddeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}
	
	/**
	 * 显示按分公司分品牌部门分品牌资金回笼情况页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 19, 2015
	 */
	public String showCompanyWithdrawnBrandDetailPage()throws Exception{
		String branddept= request.getParameter("branddept");
		String branddeptname = request.getParameter("branddeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		String company = request.getParameter("company");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		if(StringUtils.isEmpty(branddept)){
			branddept = "null";
		}
		request.setAttribute("branddept", branddept);
		request.setAttribute("branddeptname", branddeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		request.setAttribute("company", company);
		return SUCCESS;
	}
	/*------------------------------------单资金回笼情况结束-----------------------------------------*/
	
	/*------------------------------------单资金应收款情况开始-----------------------------------------*/
	
	/**
	 * 显示基础单资金应收情况表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 15, 2014
	 */
	public String showBaseFinanceReceiptPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}

	/**
	 * 显示人员部门资金应收情况表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-17
	 */
	public String showBasePersonDeptFinanceReceiptPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}

	/**
	 * 获取单资金应收情况统计数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String showBaseFinanceReceiptData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBaseFinanceReceiptData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示分客户单资金应收款情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 17, 2014
	 */
	public String showCustomerReceiptListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示分品牌单资金应收款情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 17, 2014
	 */
	public String showBrandReceiptListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示分品牌部门单资金应收款情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 17, 2014
	 */
	public String showBranddeptReceiptListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示分品牌业务员单资金应收款情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 17, 2014
	 */
	public String showBranduserReceiptListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 显示分客户业务员单资金应收款情况统计数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 15, 2014
	 */
	public String showSalesuserReceiptListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 导出单资金应收款情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 23, 2013
	 */
	public void exportBaseFinanceReceiptData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showBaseFinanceReceiptData(pageMap);
		List list = pageData.getList();
		if(null != pageData.getFooter()){
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map,list);
	}
	
	/*--------------------------单资金应收款情况结束--------------------------------*/
	
	/**
	 * 显示客户应收款动态表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 20, 2013
	 */
	public String showCustomerReceivableDynamicPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取客户应收款动态表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 20, 2013
	 */
	public String showCustomerReceivableDynamicData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showCustomerReceivableDynamicData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

	/**
	 * 客户发票对账明细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 21, 2013
	 */
	public String showCustomerInvoiceAccountBillPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 客户发票对账明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 21, 2013
	 */
	public String showCustomerInvoiceAccountBillData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showCustomerInvoiceAccountBillData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出资金回笼情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 23, 2013
	 */
	public void exportBaseSalesWithdrawnData()throws Exception{
		String groupcols = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
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
		PageData pageData = financeFundsReturnService.showBaseSalesWithdrawnData(pageMap);
		
		//数据转换，list转化符合excel导出的数据格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		Map filedMap = new HashMap();
		filedMap.put("sales", true);
		filedMap.put("receipt", true);
		filedMap.put("withdrawn", true);
		firstMap = getFirstMap(groupcols,filedMap);
		result.add(firstMap);
		
		List<Map<String, Object>> list = objectToMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 根据定义类型获取显示的字段(资金回笼情况表)
	 * @param groupcols:customerid,pcustomerid,salesuserid,salesarea,deptid,goodsid,branddept,branduser,brand
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 18, 2013
	 */
	private Map<String, Object> getFirstMap(String groupcols,Map map)throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("pcustomername", "总店名称");
					map2.put("salesusername", "客户业务员");
				}
				else if("pcustomerid".equals(groupcols)){
					map2.put("pcustomername", "总店名称");
				}
				else if("supplierid".equals(groupcols)){
					map2.put("supplierid", "供应商编码");
					map2.put("suppliername", "供应商名称");
					map2.put("branddeptname", "品牌部门");
				}
				else if("salesuser".equals(groupcols)){
					map2.put("salesusername", "客户业务员");
				}
				else if("salesarea".equals(groupcols)){
					map2.put("salesareaname", "销售区域");
				}
				else if("salesdept".equals(groupcols)){
					map2.put("salesdeptname", "销售部门");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("barcode", "条形码");
					map2.put("brandname", "商品品牌");
                    map2.put("unitnum", "数量");
                    map2.put("auxnumdetail", "辅数量");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "商品品牌");
					if(map.containsKey("branddeptcol")){
						map2.put("branddeptname", "品牌部门");
					}
				}
				else if("branduser".equals(groupcols)){
					map2.put("brandusername", "品牌业务员");
					map2.put("branddeptname", "品牌部门");
				}
				else if("branddept".equals(groupcols)){
					map2.put("branddeptname", "品牌部门");
				}else if("supplieruser".equals(groupcols)){
                    map2.put("supplierusername", "厂家业务员");
                }else if("supplierid".equals(groupcols)){
                    map2.put("suppliername", "供应商");
                }
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("pcustomername", "总店名称");
						map2.put("salesusername", "客户业务员");
					}
					else if("pcustomerid".equals(group)){
						map2.put("pcustomername", "总店名称");
					}
					else if("supplierid".equals(groupcols)){
						map2.put("supplierid", "供应商编码");
						map2.put("suppliername", "供应商名称");
						map2.put("branddeptname", "品牌部门");
					}
					else if("salesuser".equals(group)){
						map2.put("salesusername", "客户业务员");
					}
					else if("salesarea".equals(group)){
						map2.put("salesareaname", "销售区域");
					}
					else if("salesdept".equals(group)){
						map2.put("salesdeptname", "销售部门");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("barcode", "条形码");
						map2.put("brandname", "商品品牌");
                        map2.put("unitnum", "数量");
                        map2.put("auxnumdetail", "辅数量");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "商品品牌");
					}
					else if("branduser".equals(group)){
						map2.put("brandusername", "品牌业务员");
						map2.put("branddeptname", "品牌部门");
					}
					else if("branddept".equals(group)){
						map2.put("branddeptname", "品牌部门");
					}else if("supplieruser".equals(groupcols)){
                        map2.put("supplierusername", "厂家业务员");
                    }else if("supplierid".equals(groupcols)){
                        map2.put("suppliername", "供应商");
                    }
				}
			}
			if(map.containsKey("sales")){
				map2.put("sendamount", "发货金额");
				map2.put("directreturnamount", "直退金额");
				map2.put("checkreturnamount", "售后退货金额");
				map2.put("returntaxamount", "退货金额");
				map2.put("pushbalanceamount", "冲差金额");
				map2.put("saleamount", "销售总额");
				map2.put("salecostamount", "销售成本");
				map2.put("salemarginamount", "销售毛利额");
				map2.put("salerate", "销售毛利率");
			}
			if(map.containsKey("withdrawn")){
				map2.put("withdrawnamount", "回笼金额");
				if(map3.containsKey("costwriteoffamount")){
					map2.put("costwriteoffamount", "回笼成本");
				}
				if(map3.containsKey("writeoffmarginamount")){
					map2.put("writeoffmarginamount", "回笼毛利额");
				}
				if(map3.containsKey("writeoffrate")){
					map2.put("writeoffrate", "回笼毛利率%");
				}
			}
			if(map.containsKey("receipt")){
				map2.put("allunwithdrawnamount", "应收款总额");
				map2.put("unauditamount", "未验收应收款");
				map2.put("auditamount", "已验收应收款");
				map2.put("rejectamount", "退货应收款");
				map2.put("allpushbalanceamount", "冲差应收款");
			}
            if(groupcols.indexOf("customerid") != -1){
                map2.put("customeramount", "客户余额");
            }
		}
		else{
			map2.put("customerid", "客户编码");
			map2.put("customername", "客户名称");
			map2.put("pcustomername", "总店名称");
			map2.put("salesusername", "客户业务员");
			map2.put("branddeptname", "品牌部门");
			map2.put("brandusername", "品牌业务员");
            
			map2.put("goodsid", "商品编码");
			map2.put("goodsname", "商品名称");
			map2.put("barcode", "条形码");
			map2.put("brandname", "商品品牌");
			map2.put("suppliername", "供应商");
            map2.put("unitnum", "数量");
            map2.put("auxnumdetail", "辅数量");
			if(map.containsKey("sales")){
				map2.put("sendamount", "发货金额");
				map2.put("directreturnamount", "直退金额");
				map2.put("checkreturnamount", "售后退货金额");
				map2.put("returntaxamount", "退货金额");
				map2.put("pushbalanceamount", "冲差金额");
				map2.put("saleamount", "销售总额");
				map2.put("salecostamount", "销售成本");
				map2.put("salemarginamount", "销售毛利额");
				map2.put("salerate", "销售毛利率");
			}
			if(map.containsKey("withdrawn")){
				map2.put("withdrawnamount", "回笼金额");
				if(map3.containsKey("costwriteoffamount")){
					map2.put("costwriteoffamount", "回笼成本");
				}
				if(map3.containsKey("writeoffmarginamount")){
					map2.put("writeoffmarginamount", "回笼毛利额");
				}
				if(map3.containsKey("writeoffrate")){
					map2.put("writeoffrate", "回笼毛利率%");
				}
			}
			if(map.containsKey("receipt")){
				map2.put("allunwithdrawnamount", "应收款总额");
				map2.put("unauditamount", "未验收应收款");
				map2.put("auditamount", "已验收应收款");
				map2.put("rejectamount", "退货应收款");
				map2.put("allpushbalanceamount", "冲差应收款");
			}
//            if(groupcols.indexOf("customerid") != -1){
//                map2.put("customeramount", "客户余额");
//            }
		}
		return map2;
	}
	
	/**
	 * 将获取的list转化为List<Map<String, Object>>
	 * @param list
	 * @param firstMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 15, 2014
	 */
	private List<Map<String, Object>> objectToMapList(List<BaseSalesWithdrawnReport> list,Map<String, Object> firstMap)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : list){
			if(null != baseSalesWithdrawnReport){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(baseSalesWithdrawnReport);
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
	 * 将类对象转化为List<Map<String, Object>>
	 * @param pageData
	 * @param firstMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2013
	 */
	private List<Map<String, Object>> objectToMapList(PageData pageData,Map<String, Object> firstMap)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(pageData.getList().size() != 0){
			for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : new ArrayList<BaseSalesWithdrawnReport>(pageData.getList())){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(baseSalesWithdrawnReport);
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
		if(pageData.getFooter().size() != 0){
			for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : new ArrayList<BaseSalesWithdrawnReport>(pageData.getFooter())){
				if(null != baseSalesWithdrawnReport){
					Map<String, Object> retMap = new LinkedHashMap<String, Object>();
					Map<String, Object> map = new HashMap<String, Object>();
					map = PropertyUtils.describe(baseSalesWithdrawnReport);
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
		}
		return result;
	}
	
	/**
	 * 显示核销对账明细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 22, 2013
	 */
	public String showWriteAccountDetailPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取销售对账明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 22, 2013
	 */
	public String showWriteAccountDetailData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showWriteAccountDetailData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示应收款超账期基础情况统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public String showBaseReceivablePassDueListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	/**
	 * 获取应收款超账期基础情况统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public String showBaseReceivablePassDueListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBaseReceivablePassDueListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示回笼超账期基础情况统计页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public String showBaseWithdrawnPastdueListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	/**
	 * 获取回笼超账期基础情况统计数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 25, 2013
	 */
	public String showBaseWithdrawnPastdueListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBaseWithdrawnPastdueListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 显示分品牌业务员分品牌资金回笼情况统计表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2013
	 */
	public String showFundsBranduserBrandReturnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 显示分品牌业务员分客户资金回笼情况统计表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2013
	 */
	public String showFundsBranduserCustomerReturnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 显示分品牌业务员分品牌分客户资金回笼情况统计表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2013
	 */
	public String showFundsBranduserBrandCustomerReturnListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	
	/**
	 * 导出客户应收款动态表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2013
	 */
	public void exportReceivableDynamicData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		//不导出回单冲差单
		//map.put("nobilltype", "6");
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
		PageData pageData = financeFundsReturnService.showCustomerReceivableDynamicData(pageMap);
		ExcelUtils.exportExcel(exportReceivableDynamicDataFilter(pageData), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(客户应收款动态表)
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportReceivableDynamicDataFilter(PageData pageData) throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("billtypename", "单据类型");
		firstMap.put("id", "单据编号");
		firstMap.put("duefromdate", "应收日期");
		firstMap.put("orderdate", "订单日期");
		firstMap.put("saleorderid", "订单编号");
		firstMap.put("sourceid", "客户单号");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("pcustomername", "总店名称");
		firstMap.put("salesareaname", "销售区域");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("initsendamount", "发货金额");
		if(map3.containsKey("initsendcostamount")){
			firstMap.put("initsendcostamount", "成本金额");
		}
		firstMap.put("businessdate", "发货出库日期");
		firstMap.put("sendamount", "发货出库金额");
		firstMap.put("receiptid", "回单编号");
		firstMap.put("receiptremark", "回单备注");
		firstMap.put("checkamount", "验收金额");
		firstMap.put("checkdate", "验收日期");
		firstMap.put("invoicebillamount", "开票金额");
		firstMap.put("uninvoicebillamount", "未开票金额");
		firstMap.put("invoiceid", "发票号");
		firstMap.put("invoicebilldate", "开票日期");
		firstMap.put("writeoffamount", "核销金额");
		firstMap.put("unwriteoffamount", "未核销金额");
		firstMap.put("writeoffdate", "最近核销日期");
		firstMap.put("audittime", "单据审核时间");
		result.add(firstMap);
		
		if(pageData.getList().size() != 0){
			for(CustomerReceivableDynamic customerReceivableDynamic : new ArrayList<CustomerReceivableDynamic>(pageData.getList())){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(customerReceivableDynamic);
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
		if(null!=pageData.getFooter() && pageData.getFooter().size() != 0){
			for(CustomerReceivableDynamic customerReceivableDynamic : new ArrayList<CustomerReceivableDynamic>(pageData.getFooter())){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(customerReceivableDynamic);
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
	 * 导出客户核销对账单
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2013
	 */
	public void exportInvoiceAccountBillData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showCustomerInvoiceAccountBillData(pageMap);
		
		//数据转换，list专程符合excel导出的数据格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("billtypename", "单据类型");
		firstMap.put("id", "单据编号");
		firstMap.put("invoiceno", "发票号");
		firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
//		firstMap.put("pcustomername", "总店名称");
		firstMap.put("taxamount", "应收总金额");
		firstMap.put("allwriteoffamount", "核销金额");
        firstMap.put("writeoffamount", "实际核销金额");
		firstMap.put("tailamount", "尾差金额");
		firstMap.put("writeoffdate", "核销日期");
        firstMap.put("writeoffusername", "核销人");

		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 导出核销对账明细
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2013
	 */
	public void exportWrieteAccountDetailData()throws Exception{
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
		PageData pageData = financeFundsReturnService.showWriteAccountDetailData(pageMap);
		
		//数据转换，list专程符合excel导出的数据格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("billtypename", "单据类型");
		firstMap.put("billid", "单据编号");
		firstMap.put("id", "核销编号");
		firstMap.put("invoiceno", "发票号");
		firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户简称");
		firstMap.put("pcustomername", "总店名称");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
        firstMap.put("taxamount", "应收金额");
		firstMap.put("writeoffamount", "核销金额");
		firstMap.put("writeoffdate", "核销日期");
		firstMap.put("writeoffusername", "核销人");
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 导出应收款超账期情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2013
	 */
	public void exportBaseReceivablePastDueData()throws Exception{
		String groupcols = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
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
		PageData pageData = financeFundsReturnService.showBaseReceivablePassDueListData(pageMap);
		
		//数据转换，list转化符合excel导出的数据格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		firstMap = getBaseReceivablePassDueFirstMap(groupcols);
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 根据定义类型获取显示的字段（应收款超账期情况表）
	 * @param groupcols:customerid,pcustomerid,salesuser,salesarea,salesdept,goodsid,branddept,branduser,brandid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 18, 2013
	 */
	private Map<String, Object> getBaseReceivablePassDueFirstMap(String groupcols)throws Exception{
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("pcustomername", "总店名称");
					map2.put("salesusername", "客户业务员");
					map2.put("salesareaname", "销售区域");
					map2.put("salesdeptname", "销售部门");
				}
				else if("pcustomerid".equals(groupcols)){
					map2.put("pcustomername", "总店名称");
				}
				else if("salesuser".equals(groupcols)){
					map2.put("salesusername", "客户业务员");
					map2.put("salesareaname", "销售区域");
					map2.put("salesdeptname", "销售部门");
				}
				else if("salesarea".equals(groupcols)){
					map2.put("salesareaname", "销售区域");
				}
				else if("salesdept".equals(groupcols)){
					map2.put("salesdeptname", "销售部门");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("barcode", "条形码");
					map2.put("brandname", "品牌名称");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "品牌名称");
				}
				else if("branduser".equals(groupcols)){
					map2.put("brandusername", "品牌业务员");
					map2.put("branddeptname", "品牌部门");
				}
				else if("branddept".equals(groupcols)){
					map2.put("branddeptname", "品牌部门");
				}else if("supplierid".equals(groupcols)){
                    map2.put("suppliername", "供应商");
                }
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("pcustomername", "总店名称");
						map2.put("salesusername", "客户业务员");
						map2.put("salesareaname", "销售区域");
						map2.put("salesdeptname", "销售部门");
					}
					else if("pcustomerid".equals(group)){
						map2.put("pcustomername", "总店名称");
					}
					else if("salesuserid".equals(group)){
						map2.put("salesusername", "客户业务员");
						map2.put("salesareaname", "销售区域");
						map2.put("salesdeptname", "销售部门");
					}
					else if("salesarea".equals(group)){
						map2.put("salesareaname", "销售区域");
					}
					else if("salesdept".equals(group)){
						map2.put("salesdeptname", "销售部门");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("barcode", "条形码");
						map2.put("brandname", "品牌名称");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "品牌名称");
					}
					else if("branduser".equals(group)){
						map2.put("brandusername", "品牌业务员");
						map2.put("branddeptname", "品牌部门");
					}
					else if("branddept".equals(group)){
						map2.put("branddeptname", "品牌部门");
					}else if("supplierid".equals(group)){
                        map2.put("suppliername", "供应商");
                    }
				}
			}
			map2.put("saleamount", "应收款");
			map2.put("unpassamount", "正常期金额");
			map2.put("totalpassamount", "超账期金额");
			List<PaymentdaysSet> list = paymentdaysSetService.getPaymentdaysSetInfo();
			if(list.size() != 0){
				for(PaymentdaysSet paymentdaysSet : list){
					map2.put("passamount"+paymentdaysSet.getSeq(),paymentdaysSet.getDetail());
				}
			}
			map2.put("returnamount", "退货金额");
			map2.put("returnrate", "退货率");
			map2.put("pushamount", "冲差金额");
		}
		else{
			map2.put("customerid", "客户编码");
			map2.put("customername", "客户名称");
			map2.put("pcustomername", "总店名称");
			map2.put("salesusername", "客户业务员");
			map2.put("salesareaname", "销售区域");
			map2.put("salesdeptname", "销售部门");
			map2.put("branddeptname", "品牌部门");
			map2.put("brandusername", "品牌业务员");
            
			map2.put("goodsid", "商品编码");
			map2.put("goodsname", "商品名称");
			map2.put("barcode", "条形码");
			map2.put("brandname", "品牌名称");
			map2.put("suppliername", "供应商");
			map2.put("saleamount", "应收款");
			map2.put("unpassamount", "正常期金额");
			map2.put("totalpassamount", "超账期金额");
			List<PaymentdaysSet> list = paymentdaysSetService.getPaymentdaysSetInfo();
			if(list.size() != 0){
				for(PaymentdaysSet paymentdaysSet : list){
					map2.put("passamount"+paymentdaysSet.getSeq(),paymentdaysSet.getDetail());
				}
			}
			map2.put("returnamount", "退货金额");
			map2.put("returnrate", "退货率");
			map2.put("pushamount", "冲差金额");
		}
		return map2;
	}
	
	/**
	 * 导出回笼超账期情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 28, 2013
	 */
	public void exportBaseWithdrawnPastDueData()throws Exception{
		String groupcols = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
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
		PageData pageData = financeFundsReturnService.showBaseWithdrawnPastdueListData(pageMap);
		
		//数据转换，list转化符合excel导出的数据格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		firstMap = getBaseWithdrawnPastdueFirstMap(groupcols);
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 根据定义类型获取显示的字段(回笼超账期情况表)
	 * @param groupcols:customerid,pcustomerid,salesuser,salesarea,deptid,goodsid,branddept,branduser,brandid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 18, 2013
	 */
	private Map<String, Object> getBaseWithdrawnPastdueFirstMap(String groupcols)throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("pcustomername", "总店名称");
				}
				else if("pcustomerid".equals(groupcols)){
					map2.put("pcustomername", "总店名称");
				}
				else if("salesuser".equals(groupcols)){
					map2.put("salesusername", "客户业务员");
					map2.put("salesareaname", "销售区域");
					map2.put("salesdeptname", "销售部门");
				}
				else if("salesarea".equals(groupcols)){
					map2.put("salesareaname", "销售区域");
				}
				else if("salesdept".equals(groupcols)){
					map2.put("salesdeptname", "销售部门");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("barcode", "条形码");
					map2.put("brandname", "品牌名称");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "品牌名称");
				}
				else if("branduser".equals(groupcols)){
					map2.put("brandusername", "品牌业务员");
					map2.put("branddeptname", "品牌部门");
				}
				else if("branddept".equals(groupcols)){
					map2.put("branddeptname", "品牌部门");
				}
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("pcustomername", "总店名称");
					}
					else if("pcustomerid".equals(group)){
						map2.put("pcustomername", "总店名称");
					}
					else if("salesuser".equals(group)){
						map2.put("salesusername", "客户业务员");
						map2.put("salesareaname", "销售区域");
						map2.put("salesdeptname", "销售部门");
					}
					else if("salesarea".equals(group)){
						map2.put("salesareaname", "销售区域");
					}
					else if("salesdept".equals(group)){
						map2.put("salesdeptname", "销售部门");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("barcode", "条形码");
						map2.put("brandname", "品牌名称");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "品牌名称");
					}
					else if("branduser".equals(group)){
						map2.put("brandusername", "品牌业务员");
						map2.put("branddeptname", "品牌部门");
					}
					else if("branddept".equals(group)){
						map2.put("branddeptname", "品牌部门");
					}
				}
			}
			map2.put("taxamount", "回笼金额");
			if(map3.containsKey("costamount")){
				map2.put("costamount", "回笼成本");
			}
			if(map3.containsKey("marginamount")){
				map2.put("marginamount", "回笼毛利额");
			}
			if(map3.containsKey("marginrate")){
				map2.put("marginrate", "回笼毛利率");
			}
			map2.put("unpassamount", "正常期金额");
			map2.put("totalpassamount", "超账期金额");
			List<PaymentdaysSet> list = paymentdaysSetService.getPaymentdaysSetInfo();
			if(list.size() != 0){
				for(PaymentdaysSet paymentdaysSet : list){
					map2.put("passamount"+paymentdaysSet.getSeq(),paymentdaysSet.getDetail() );
				}
			}
			map2.put("returnamount", "退货金额");
			map2.put("returnrate", "退货率");
			map2.put("pushamount", "冲差金额");
		}
		else{
			map2.put("customerid", "客户编码");
			map2.put("customername", "客户名称");
			map2.put("pcustomername", "总店名称");
			map2.put("salesusername", "客户业务员");
			map2.put("salesareaname", "销售区域");
			map2.put("salesdeptname", "销售部门");
			map2.put("branddeptname", "品牌部门");
			map2.put("brandusername", "品牌业务员");
			map2.put("goodsid", "商品编码");
			map2.put("goodsname", "商品名称");
			map2.put("barcode", "条形码");
			map2.put("brandname", "品牌名称");
			map2.put("taxamount", "回笼金额");
			if(map3.containsKey("costamount")){
				map2.put("costamount", "回笼成本");
			}
			if(map3.containsKey("marginamount")){
				map2.put("marginamount", "回笼毛利额");
			}
			if(map3.containsKey("marginrate")){
				map2.put("marginrate", "回笼毛利率");
			}
			map2.put("unpassamount", "正常期金额");
			map2.put("totalpassamount", "超账期金额");
			List<PaymentdaysSet> list = paymentdaysSetService.getPaymentdaysSetInfo();
			if(list.size() != 0){
				for(PaymentdaysSet paymentdaysSet : list){
					map2.put("passamount"+paymentdaysSet.getSeq(), paymentdaysSet.getDetail());
				}
			}
			map2.put("returnamount", "退货金额");
			map2.put("returnrate", "退货率");
			map2.put("pushamount", "冲差金额");
		}
		return map2;
	}
	
	/**
	 * 显示客户收款单对账单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 4, 2013
	 */
	public String showCustomerCollectionStatementListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List bankList = getBaseFinanceService().getAllBankList();
		request.setAttribute("bankList", bankList);
		return "success";
	}
	/**
	 * 显示品牌业务员资金回笼超账期报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 9, 2013
	 */
	public String showBranduserWithdrawalPassDueListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	/**
	 * 显示品牌业务员应收款超账期报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 9, 2013
	 */
	public String showBranduserReceivablePassDueListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	
	/**
	 * 显示分客户显示数据明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 10, 2013
	 */
	public String showFundsCustomerReturnDetailPage()throws Exception{
		String customerid= request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("customerid", customerid);
		request.setAttribute("customername", customername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 导出资金回笼明细表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 12, 2013
	 */
	public void exportFundsReturnDetailData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		if(map.containsKey("branduser")){
			if("0".equals(map.get("branduser"))){
				map.put("branduser", "");
			}
		}
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
		Map<String,List<Map<String, Object>>> retMap = new HashMap<String, List<Map<String,Object>>>();
		String tnjson = "{",str = "";
		if(map.containsKey("groupcols")){
			String groupcols = (String)map.get("groupcols");
			if(groupcols.indexOf(";") != -1){
				String[] groupcolArr = groupcols.split(";");
				for(String group : groupcolArr){
					String str1 = "sales",str2 = "";
					List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
					pageMap.getCondition().put("groupcols", group);
					Map flagMap = new HashMap();
					if(pageMap.getCondition().containsKey("branduser")){
						flagMap.put("buflag", true);
					}
					if(pageMap.getCondition().containsKey("branddept")){
						flagMap.put("bdflag", true);
					}
					if(pageMap.getCondition().containsKey("customerid")){
						flagMap.put("cflag", true);
					}
					if(pageMap.getCondition().containsKey("brandid")){
						flagMap.put("bflag", true);
					}
					if(pageMap.getCondition().containsKey("salesdept")){
						flagMap.put("sdflag", true);
					}
					flagMap.put("sales", true);
					flagMap.put("withdrawn", true);
					flagMap.put("receipt", true);
					Map<String, Object> firstMap = getFirstMapForDetail(group,flagMap);
					result.add(firstMap);
					PageData pageData = financeFundsReturnService.showBaseSalesWithdrawnData(pageMap);
					List<Map<String, Object>> list = objectToMapList(pageData,firstMap);
					result.addAll(list);
					if(group.indexOf(",") == -1){
						if("brandid".equals(group)){
							str1 += "brandid";
							str2 += "分品牌统计";
						}else if("goodsid".equals(group)){
							str1 += "goods";
							str2 += "分商品统计";
						}else if("customerid".equals(group)){
							str1 += "customer";
							str2 += "分客户统计";
						}
						str += "'"+str1+"':'"+str2+"',";
					}else{
						for(String group2 : group.split(",")){
							if("brandid".equals(group2)){
								str1 += "brandid";
								str2 += "分品牌";
							}else if("goodsid".equals(group2)){
								str1 += "goods";
								str2 += "分商品";
							}else if("customerid".equals(group2)){
								str1 += "customer";
								str2 += "分客户";
							}
						}
						str += "'"+str1+"':'"+str2+"统计',";
					}
					retMap.put(""+str1+"_list", result);
				}
			}
			else{
				if("brandid".equals(groupcols)){
					str += "'salesbrand':'分品牌统计',";
				}else if("goodsid".equals(groupcols)){
					str += "'salesgoods':'分商品统计',";
				}else if("customerid".equals(groupcols)){
					str += "'salescustomer':'分客户统计',";
				}
			}
			if(str.lastIndexOf(",") != -1){
				tnjson += str.substring(0, str.lastIndexOf(",")) + "}";
			}
			else{
				tnjson += str + "}";
			}
		}
		//String tnjson = "{'salesBrand':'分品牌统计','salesGoods':'分商品统计'}";
		ExcelUtils.exportMoreExcel(retMap, title,tnjson);
	}
	
	/**
	 * 根据定义类型获取显示的字段(资金回笼明细表)
	 * @param groupcols:customerid,brandid,goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 12, 2013
	 */
	private Map<String, Object> getFirstMapForDetail(String groupcols,Map map)throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("pcustomername", "总店名称");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("barcode", "条形码");
					map2.put("brandname", "商品品牌");
                    map2.put("unitnum", "数量");
                    map2.put("auxnumdetail", "辅数量");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "商品品牌");
					if(!map.containsKey("bdflag") && !map.containsKey("spflag")
							&& !map.containsKey("spuflag")){
						map2.put("branddeptname", "品牌部门");
					}
					if(!map.containsKey("buflag") && !map.containsKey("bdflag")
							&& !map.containsKey("cflag") && !map.containsKey("sdflag")
							&& !map.containsKey("spflag") && !map.containsKey("spuflag")){
						map2.put("brandusername", "品牌业务员");
					}
				}else if("company".equals(groupcols)){
                    map2.put("branddeptname", "公司");
                }
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("pcustomername", "总店名称");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("barcode", "条形码");
						map2.put("brandname", "商品品牌");
                        map2.put("unitnum", "数量");
                        map2.put("auxnumdetail", "辅数量");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "商品品牌");
						if(!map.containsKey("bdflag") && !map.containsKey("spflag")
								&& !map.containsKey("spuflag")){
							map2.put("branddeptname", "品牌部门");
						}
						if(!map.containsKey("buflag")&& !map.containsKey("bdflag")
								&& !map.containsKey("cflag")&& !map.containsKey("sdflag")
								&& !map.containsKey("spflag") && !map.containsKey("spuflag")){
							map2.put("brandusername", "品牌业务员");
						}
					}
				}
			}
			if(map.containsKey("sales")){
				map2.put("sendamount", "发货金额");
				map2.put("directreturnamount", "直退金额");
				map2.put("checkreturnamount", "售后退货金额");
				map2.put("returntaxamount", "退货金额");
				map2.put("pushbalanceamount", "冲差金额");
				map2.put("saleamount", "销售总额");
				map2.put("salecostamount", "销售成本");
				map2.put("salemarginamount", "销售毛利额");
				map2.put("salerate", "销售毛利率%");
			}
			if(map.containsKey("withdrawn")){
				map2.put("withdrawnamount", "回笼金额");
				if(map3.containsKey("costwriteoffamount")){
					map2.put("costwriteoffamount", "回笼成本");
				}
				if(map3.containsKey("writeoffmarginamount")){
					map2.put("writeoffmarginamount", "回笼毛利额");
				}
				if(map3.containsKey("writeoffrate")){
					map2.put("writeoffrate", "回笼毛利率%");
				}
			}
			if(map.containsKey("receipt")){
				map2.put("allunwithdrawnamount", "应收款总额");
				map2.put("unauditamount", "未验收应收款");
				map2.put("auditamount", "已验收应收款");
				map2.put("rejectamount", "退货应收款");
				map2.put("allpushbalanceamount", "冲差应收款");
			}
		}
		return map2;
	}
	
	/**
	 * 导出应收款超账期明细表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 25, 2013
	 */
	public void exportReceivablePastDueDetailData()throws Exception{
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
		Map<String,List<Map<String, Object>>> retMap = new HashMap<String, List<Map<String,Object>>>();
		String tnjson = "{",str = "";
		if(map.containsKey("groupcols")){
			String groupcols = (String)map.get("groupcols");
			if(groupcols.indexOf(";") != -1){
				String[] groupcolArr = groupcols.split(";");
				for(String group : groupcolArr){
					String str1 = "receivable",str2 = "";
					List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
					pageMap.getCondition().put("groupcols", group);
					Map flagMap = new HashMap();
					if(pageMap.getCondition().containsKey("branduser")){
						flagMap.put("buflag", true);
					}
					if(pageMap.getCondition().containsKey("branddept")){
						flagMap.put("bdflag", true);
					}
					if(pageMap.getCondition().containsKey("customerid")){
						flagMap.put("cflag", true);
					}
					Map<String, Object> firstMap = getReceivableFirstMapForDetail(group,flagMap);
					result.add(firstMap);
					PageData pageData = financeFundsReturnService.showBaseReceivablePassDueListData(pageMap);
					List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
					result.addAll(list);
					if(group.indexOf(",") == -1){
						if("brandid".equals(group)){
							str1 += "brand";
							str2 += "分品牌统计";
						}else if("goodsid".equals(group)){
							str1 += "goods";
							str2 += "分商品统计";
						}else if("customerid".equals(group)){
							str1 += "customer";
							str2 += "分客户统计";
						}
						str += "'"+str1+"':'"+str2+"',";
					}else{
						for(String group2 : group.split(",")){
							if("brandid".equals(group2)){
								str1 += "brand";
								str2 += "分品牌";
							}else if("goodsid".equals(group2)){
								str1 += "goods";
								str2 += "分商品";
							}else if("customerid".equals(group2)){
								str1 += "customer";
								str2 += "分客户";
							}
						}
						str += "'"+str1+"':'"+str2+"统计',";
					}
					retMap.put(""+str1+"_list", result);
				}
			}
			else{
				if("brandid".equals(groupcols)){
					str += "'receivablebrand':'分品牌统计',";
				}else if("goodsid".equals(groupcols)){
					str += "'receivablegoods':'分商品统计',";
				}else if("customerid".equals(groupcols)){
					str += "'receivablecustomer':'分客户统计',";
				}
			}
			if(str.lastIndexOf(",") != -1){
				tnjson += str.substring(0, str.lastIndexOf(",")) + "}";
			}
			else{
				tnjson += str + "}";
			}
		}
		//String tnjson = "{'salesBrand':'分品牌统计','salesGoods':'分商品统计'}";
		ExcelUtils.exportMoreExcel(retMap, title,tnjson);
	}
	
	/**
	 * 根据定义类型获取显示的字段
	 * @param groupcols:customerid,brandid,goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 12, 2013
	 */
	private Map<String, Object> getReceivableFirstMapForDetail(String groupcols,Map map)throws Exception{
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("settletypename", "结算方式");
					map2.put("payeename", "收款人");
					//map2.put("deptname", "销售部门");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("brandname", "品牌名称");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "品牌名称");
					if(!map.containsKey("bdflag")){
						map2.put("branddeptname", "品牌部门");
					}
					if(!map.containsKey("buflag") && !map.containsKey("cflag")){
						map2.put("brandusername", "品牌业务员");
					}
				}
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("settletypename", "结算方式");
						map2.put("payeename", "收款人");
						//map2.put("deptname", "销售部门");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("brandname", "品牌名称");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "品牌名称");
						if(!map.containsKey("bdflag")){
							map2.put("branddeptname", "品牌部门");
						}
						if(!map.containsKey("buflag") && !map.containsKey("cflag")){
							map2.put("brandusername", "品牌业务员");
						}
					}
				}
			}
			map2.put("saleamount", "应收款");
			map2.put("unpassamount", "正常期金额");
			map2.put("totalpassamount", "超账期金额");
			List<PaymentdaysSet> list = paymentdaysSetService.getPaymentdaysSetInfo();
			if(list.size() != 0){
				for(PaymentdaysSet paymentdaysSet : list){
					map2.put("passamount"+paymentdaysSet.getSeq(),paymentdaysSet.getDetail());
				}
			}
			map2.put("returnamount", "退货金额");
			map2.put("returnrate", "退货率%");
			map2.put("pushamount", "冲差金额");
		}
		return map2;
	}
	
	/**
	 * 显示品牌部门分品牌分商品
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 14, 2013
	 */
	public String showFundsBrandDeptReturnDetailPage()throws Exception{
		String branddept= request.getParameter("branddept");
		String branddeptname = request.getParameter("branddeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("branddept", branddept);
		request.setAttribute("branddeptname", branddeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 显示按品牌分客户分商品明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 14, 2013
	 */
	public String showFundsBrandReturnDetailPage()throws Exception{
		String brandid= request.getParameter("brandid");
		String brandname = request.getParameter("brandname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("brandid", brandid);
		request.setAttribute("brandname", brandname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 显示按部门分客户分品牌明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 16, 2013
	 */
	public String showFundsSalesDeptReturnDetailPage()throws Exception{
		String salesdept= request.getParameter("salesdept");
		String salesdeptname = request.getParameter("salesdeptname");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("salesdept", salesdept);
		request.setAttribute("salesdeptname", salesdeptname);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return "success";
	}
	
	/**
	 * 显示按客户业务员分客户、分商品、分客户分商品明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 16, 2013
	 */
	public String showFundsSalesuserReturnDetailPage()throws Exception{
		String salesuser= request.getParameter("salesuser");
		String salesusername = request.getParameter("salesusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("salesuser", salesuser);
		request.setAttribute("salesusername", salesusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}
	/**
	 * 显示银行回笼金额页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 19, 2013
	 */
	public String showBankWriteReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List list = baseFinanceService.getAllBankList();
		request.setAttribute("bankList", list);
		return "success";
	}
	/**
	 * 获取银行回笼金额数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 19, 2013
	 */
	public String showBankWriteReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showBankWriteReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 客户银行回笼金额数据导出
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 20, 2013
	 */
	public void exportBankWriteReportData() throws Exception{
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
		PageData pageData = financeFundsReturnService.showBankWriteReportData(pageMap);
		
		//客户银行回笼金额数据导出格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("pid", "总店编号");
		firstMap.put("pname", "总店名称");
		firstMap.put("totalamount", "总金额");
		//firstMap.put("cashamount", "现金");
		List<Bank> bankList = baseFinanceService.getAllBankList();
		for(Bank bank : bankList){
			firstMap.put("amount"+bank.getId(), bank.getName());
		}
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 显示分客户分银行回笼报表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月2日
	 */
	public String showCustomerBankWriteReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		List list = baseFinanceService.getAllBankList();
		request.setAttribute("bankList", list);
		return "success";
	}
	/**
	 * 获取分客户分银行回笼报表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月2日
	 */
	public String showCustomerBankWriteReportData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.showCustomerBankWriteReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出分客户分银行回笼报表数据
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月2日
	 */
	public void exportCustomerBankWriteReportData() throws Exception{
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
		PageData pageData = financeFundsReturnService.showCustomerBankWriteReportData(pageMap);
		
		//客户银行回笼金额数据导出格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("pid", "总店编号");
		firstMap.put("pname", "总店名称");
		firstMap.put("bankname", "银行名称");
		firstMap.put("amount", "金额");
		result.add(firstMap);
		
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	
	
	/**
	 * 显示客户应收款超账期明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 24, 2013
	 */
	public String showCustomerReceivablePastDueDetailPage()throws Exception{
		String customerid= request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String ispastdue = request.getParameter("ispastdue");
		request.setAttribute("ispastdue", ispastdue);
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("list", list);
		request.setAttribute("customerid", customerid);
		request.setAttribute("customername", customername);
		return SUCCESS;
	}
	
	/**
	 * 显示客户业务员收款超账期明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 25, 2013
	 */
	public String showSalesuserReceivablePastDueDetailPage()throws Exception{
		String salesuser= request.getParameter("salesuser");
		String salesusername = request.getParameter("salesusername");
		String ispastdue = request.getParameter("ispastdue");
		request.setAttribute("ispastdue", ispastdue);
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("list", list);
		request.setAttribute("salesuser", salesuser);
		request.setAttribute("salesusername", salesusername);
		return SUCCESS;
	}
	
	/**
	 * 显示客户业务员回笼资金超账期明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 6, 2014
	 */
	public String showSalesUserWithdrawalPastDueDetailPage()throws Exception{
		String salesuser= request.getParameter("salesuser");
		String salesusername = request.getParameter("salesusername");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		Map map = getAccessColumn("t_report_sales_base");
		if(null==businessdate1){
			businessdate1 = "";
		}
		if(null==businessdate2){
			businessdate2 = "";
		}
		request.setAttribute("map", map);
		request.setAttribute("list", list);
		request.setAttribute("salesuser", salesuser);
		request.setAttribute("salesusername", salesusername);
		request.setAttribute("businessdate1", businessdate1);
		request.setAttribute("businessdate2", businessdate2);
		return SUCCESS;
	}
	
	/**
	 * 显示品牌业务员回笼资金超账期明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 7, 2014
	 */
	public String showBranduserReceivablePastDueDetailPage()throws Exception{
		String branduser = request.getParameter("branduser");
		String brandusername = request.getParameter("brandusername");
		String ispastdue = request.getParameter("ispastdue");
		request.setAttribute("ispastdue", ispastdue);
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("list", list);
		request.setAttribute("branduser", branduser);
		request.setAttribute("brandusername", brandusername);
		return SUCCESS;
	}
	
	/**
	 * 导出应收款超账期明细表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 25, 2013
	 */
	public void exportWithdrawalPastDueDetailData()throws Exception{
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
		Map<String,List<Map<String, Object>>> retMap = new HashMap<String, List<Map<String,Object>>>();
		String tnjson = "{",str = "";
		if(map.containsKey("groupcols")){
			String groupcols = (String)map.get("groupcols");
			if(groupcols.indexOf(";") != -1){
				String[] groupcolArr = groupcols.split(";");
				for(String group : groupcolArr){
					String str1 = "receivable",str2 = "";
					List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
					pageMap.getCondition().put("groupcols", group);
					Map flagMap = new HashMap();
					if(pageMap.getCondition().containsKey("branduser")){
						flagMap.put("buflag", true);
					}
					if(pageMap.getCondition().containsKey("branddept")){
						flagMap.put("bdflag", true);
					}
					if(pageMap.getCondition().containsKey("customerid")){
						flagMap.put("cflag", true);
					}
					Map<String, Object> firstMap = getWithdrawnFirstMapForDetail(group,flagMap);
					result.add(firstMap);
					PageData pageData = financeFundsReturnService.showBaseWithdrawnPastdueListData(pageMap);
					List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
					result.addAll(list);
					if(group.indexOf(",") == -1){
						if("brandid".equals(group)){
							str1 += "brandid";
							str2 += "分品牌统计";
						}else if("goodsid".equals(group)){
							str1 += "goods";
							str2 += "分商品统计";
						}else if("customerid".equals(group)){
							str1 += "customer";
							str2 += "分客户统计";
						}
						str += "'"+str1+"':'"+str2+"',";
					}else{
						for(String group2 : group.split(",")){
							if("brandid".equals(group2)){
								str1 += "brandid";
								str2 += "分品牌";
							}else if("goodsid".equals(group2)){
								str1 += "goods";
								str2 += "分商品";
							}else if("customerid".equals(group2)){
								str1 += "customer";
								str2 += "分客户";
							}
						}
						str += "'"+str1+"':'"+str2+"统计',";
					}
					retMap.put(""+str1+"_list", result);
				}
			}
			else{
				if("brandid".equals(groupcols)){
					str += "'receivablebrand':'分品牌统计',";
				}else if("goodsid".equals(groupcols)){
					str += "'receivablegoods':'分商品统计',";
				}else if("customerid".equals(groupcols)){
					str += "'receivablecustomer':'分客户统计',";
				}
			}
			if(str.lastIndexOf(",") != -1){
				tnjson += str.substring(0, str.lastIndexOf(",")) + "}";
			}
			else{
				tnjson += str + "}";
			}
		}
		//String tnjson = "{'salesBrand':'分品牌统计','salesGoods':'分商品统计'}";
		ExcelUtils.exportMoreExcel(retMap, title,tnjson);
	}
	
	/**
	 * 根据定义类型获取显示的字段
	 * @param groupcols:customerid,brandid,goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 12, 2013
	 */
	private Map<String, Object> getWithdrawnFirstMapForDetail(String groupcols,Map map)throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("settletypename", "结算方式");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("brandname", "品牌名称");
                    map2.put("unitnum", "数量");
                    map2.put("auxnumdetail", "辅数量");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "品牌名称");
					if(!map.containsKey("bdflag")){
						map2.put("branddeptname", "品牌部门");
					}
					if(!map.containsKey("buflag") && !map.containsKey("cflag")){
						map2.put("brandusername", "品牌业务员");
					}
				}
			}
			else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("settletypename", "结算方式");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("brandname", "品牌名称");
                        map2.put("unitnum", "数量");
                        map2.put("auxnumdetail", "辅数量");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "品牌名称");
						if(!map.containsKey("bdflag")){
							map2.put("branddeptname", "品牌部门");
						}
						if(!map.containsKey("buflag") && !map.containsKey("cflag")){
							map2.put("brandusername", "品牌业务员");
						}
					}
				}
			}
			map2.put("taxamount", "回笼金额");
			if(map3.containsKey("costamount")){
				map2.put("costamount", "回笼成本");
			}
			if(map3.containsKey("marginamount")){
				map2.put("marginamount", "回笼毛利额");
			}
			if(map3.containsKey("marginrate")){
				map2.put("marginrate", "回笼毛利率%");
			}
			map2.put("unpassamount", "正常期金额");
			map2.put("totalpassamount", "超账期金额");
			List<PaymentdaysSet> list = paymentdaysSetService.getPaymentdaysSetInfo();
			if(list.size() != 0){
				for(PaymentdaysSet paymentdaysSet : list){
					map2.put("passamount"+paymentdaysSet.getSeq(),paymentdaysSet.getDetail());
				}
			}
			map2.put("returnamount", "退货金额");
			map2.put("returnrate", "退货率%");
			map2.put("pushamount", "冲差金额");
		}
		return map2;
	}
	
	/*-----------------------品牌业务员考核表-----------------------------------*/
	
	/**
	 * 显示品牌业务员考核列表页面
	 */
	public String showBranduserAssessListPage()throws Exception{
//		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
//		Calendar c = Calendar.getInstance();
//		Date now = c.getTime();
//		String today = smf.format(now);
//		
//		c.add(Calendar.MONTH, -1);
//		Date benow = c.getTime();
//		String firstDay = smf.format(benow);
//		request.setAttribute("firstDay", firstDay);
//		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取品牌业务员考核列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public String getBranduserAssessListPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.getBranduserAssessData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示新增品牌业务员考核页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public String showBranduserAssessAddPage()throws Exception{
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String date = smf.format(now);
		//回笼奖金基数
		List wdbonusbaseList = super.getBaseSysCodeService().showSysCodeListByType("wdbonusbase");
		request.setAttribute("wdbonusbaseList", wdbonusbaseList);
		request.setAttribute("date", date);
		return SUCCESS;
	}
	
	/**
	 * 新增品牌业务员考核
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	@UserOperateLog(key="BranduserAssess",type=2,value="新增品牌业务员考核")
	public String addBranduserAssess()throws Exception{
		String check = request.getParameter("check");
		boolean flag = false;
		if("1".equals(check)){
			Map map = new HashMap();
			map.put("businessdate", branduserAssess.getBusinessdate());
			map.put("branduser", branduserAssess.getBranduser());
			BranduserAssess bAssess = financeFundsReturnService.getBranduserAssessByParam(map);
			if(null != bAssess){
				branduserAssess.setId(bAssess.getId());
				flag = financeFundsReturnService.editBranduserAssess(branduserAssess);
			}
		}else if("0".equals(check)){
			flag = financeFundsReturnService.addBranduserAssess(branduserAssess);
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 判断是否存在品牌业务员
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public String checkBranduserAssess()throws Exception{
		Map map = new HashMap();
		map.put("businessdate", branduserAssess.getBusinessdate());
		map.put("branduser", branduserAssess.getBranduser());
		boolean flag = financeFundsReturnService.checkBranduserAssess(map);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 显示品牌业务员考核详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public String showBranduserAssessViewPage()throws Exception{
		String id = request.getParameter("id");
		BranduserAssess branduserAssess = financeFundsReturnService.getBranduserAssessInfo(id);
		//回笼奖金基数
		List wdbonusbaseList = super.getBaseSysCodeService().showSysCodeListByType("wdbonusbase");
		request.setAttribute("wdbonusbaseList", wdbonusbaseList);
		request.setAttribute("branduserAssess", branduserAssess);
		return SUCCESS;
	}
	
	/**
	 * 显示品牌业务员考核修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	public String showBranduserAssessEditPage()throws Exception{
		String id = request.getParameter("id");
		BranduserAssess branduserAssess = financeFundsReturnService.getBranduserAssessInfo(id);
		//回笼奖金基数
		List wdbonusbaseList = super.getBaseSysCodeService().showSysCodeListByType("wdbonusbase");
		request.setAttribute("wdbonusbaseList", wdbonusbaseList);
		request.setAttribute("branduserAssess", branduserAssess);
		return SUCCESS;
	}
	
	/**
	 * 修改品牌业务员考核
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	@UserOperateLog(key="BranduserAssess",type=3,value="修改品牌业务员考核")
	public String editBranduserAssess()throws Exception{
		boolean flag = financeFundsReturnService.editBranduserAssess(branduserAssess);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 删除品牌业务员考核(批量)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	@UserOperateLog(key="BranduserAssess",type=4,value="删除品牌业务员考核")
	public String deleteBranduserAssess()throws Exception{
		String ids = request.getParameter("ids");
		int sucnum = 0,unsucnum = 0;
		String[] idArr = ids.split(",");
		for(String id : idArr){
			boolean flag = financeFundsReturnService.deleteBranduserAssess(id);
			if(flag){
				sucnum++;
			}else{
				unsucnum++;
			}
		}
		Map map = new HashMap();
		map.put("sucnum", sucnum);
		map.put("unsucnum", unsucnum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示品牌业务员考核报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public String showBranduserAssessReportPage()throws Exception{
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String today = smf.format(now);
		
		c.add(Calendar.MONTH, -1);
		Date benow = c.getTime();
		String firstDay = smf.format(benow);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取品牌业务员考核报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public String getBranduserAssessReportData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.getBranduserAssessReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出品牌业务员考核报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 12, 2014
	 */
	public void exportBranduserAssessReportData()throws Exception {
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
		firstMap.put("brandusername", "人员名称");
		firstMap.put("wdtargetamount", "回笼目标金额");
		firstMap.put("wdaccomplishamount", "回笼实绩金额");
		firstMap.put("retaccomplishamount", "实绩退货");
		firstMap.put("retstandardamount", "退货标准");
		firstMap.put("superretamount", "超退货部分");
		firstMap.put("checkbonusbase", "核算奖金基数");
		firstMap.put("totalscore", "合计得分");
		firstMap.put("wdbonusamount", "回笼奖金金额");
		firstMap.put("kpitargetamount", "kpi目标");
		firstMap.put("realaccomplish", "实绩完成");
		firstMap.put("kpibonusamount", "kpi奖金金额");
		firstMap.put("monthtotalamount", "本月合计奖金");
		result.add(firstMap);
		
		PageData pageData = financeFundsReturnService.getBranduserAssessReportData(pageMap);
		List<BranduserAssessReport> list = new ArrayList<BranduserAssessReport>();
		list.addAll(pageData.getList());
		list.addAll(pageData.getFooter());
		for(BranduserAssessReport branduserAssessReport : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(branduserAssessReport);
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
		ExcelUtils.exportExcel(result, title);
	}
	
	/*-------------------------------------品牌业务员考核扩展---------------------------------------------*/
	
	/**
	 * 显示品牌业务员考核列表页面
	 */
	public String showBranduserAssessExtendListPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 获取品牌业务员考核明细数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public String getBranduserAssessExtendList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.getBranduserAssessExtendData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示品牌业务员分月考核明细新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public String showBranduserAssessExtendAddPage()throws Exception{
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String today = smf.format(now);
		
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 编辑品牌业务员分月考核明细
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	@UserOperateLog(key="BranduserAssessExtend",type=0,value="")
	public String addBranduserAssessInfoExtend()throws Exception{
		String check = request.getParameter("check");
		boolean flag = false;
		if("1".equals(check)){
			Map map = new HashMap();
			map.put("businessdate", branduserAssessExtend.getBusinessdate());
			map.put("branduser", branduserAssessExtend.getBranduser());
			map.put("salesarea", branduserAssessExtend.getSalesarea());
			BranduserAssessExtend bAssessExtend = financeFundsReturnService.getBranduserAssessExtendByParam(map);
			if(null != bAssessExtend){
				branduserAssessExtend.setId(bAssessExtend.getId());
				flag = financeFundsReturnService.editBranduserAssessInfoExtend(branduserAssessExtend);
				addLog("修改"+ branduserAssessExtend.getBusinessdate()+"考核 业务员编号:"+branduserAssessExtend.getBranduser(),flag);
			}
		}else if("0".equals(check)){
			flag = financeFundsReturnService.addBranduserAssessInfoExtend(branduserAssessExtend);
			addLog("新增"+ branduserAssessExtend.getBusinessdate()+"考核 业务员编号:"+branduserAssessExtend.getBranduser(),flag);
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 显示品牌业务员分月考核明细修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public String showBranduserAssessExtendEditPage()throws Exception{
		String id = request.getParameter("id");
		Map map = new HashMap();
		map.put("id", id);
		BranduserAssessExtend branduserAssessExtend = financeFundsReturnService.getBranduserAssessExtendByParam(map);
		request.setAttribute("branduserAssessExtend", branduserAssessExtend);
		return SUCCESS;
	}
	
	/**
	 * 判断是否存在品牌业务员考核
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public String checkBranduserAssessExtend()throws Exception{
		Map map = new HashMap();
		map.put("businessdate", branduserAssessExtend.getBusinessdate());
		map.put("branduser", branduserAssessExtend.getBranduser());
		map.put("salesarea", branduserAssessExtend.getSalesarea());
		boolean flag = financeFundsReturnService.checkBranduserAssessExtend(map);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 删除品牌业务员考核扩展
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2014
	 */
	public String deleteBranduserAssessExtend()throws Exception{
		String ids = request.getParameter("ids");
		int sucnum = 0,unsucnum = 0;
		String[] idArr = ids.split(",");
		for(String id : idArr){
			boolean flag = financeFundsReturnService.deleteBranduserAssessExtend(id);
			if(flag){
				sucnum++;
			}else{
				unsucnum++;
			}
		}
		Map map = new HashMap();
		map.put("sucnum", sucnum);
		map.put("unsucnum", unsucnum);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示品牌业务员考核报表扩展页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 1, 2014
	 */
	public String showBranduserAssessReportExtendPage()throws Exception{
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		String today = smf.format(now);
		
		c.add(Calendar.MONTH, -1);
		Date benow = c.getTime();
		String firstDay = smf.format(benow);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		SysParam bonusParam = getBaseSysParamService().getSysParam("bonusnum");
		BigDecimal bonusnum = BigDecimal.ZERO;
		if(null != bonusParam){
			bonusnum = new BigDecimal(bonusParam.getPvalue());
		}
		request.setAttribute("bonusnum", bonusnum);
		return SUCCESS;
	}
	
	/**
	 * 获取品牌业务员考核报表扩展数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 1, 2014
	 */
	public String getBranduserAssessReportExtendData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.getBranduserAssessReportExtendData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示品牌业务员分月考核信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 1, 2014
	 */
	public String showBranduserAssessExtendPage()throws Exception{
		String branduser = request.getParameter("branduser");
		String rowindex = request.getParameter("rowindex");
		String businessdate = request.getParameter("businessdate");
		String salesarea = request.getParameter("salesarea");
		request.setAttribute("today", businessdate);
		request.setAttribute("branduser", branduser);
		request.setAttribute("rowindex", rowindex);
		request.setAttribute("salesarea", salesarea);
		Map paramMap = new HashMap();
		paramMap.put("businessdate", businessdate);
		paramMap.put("branduser", branduser);
		paramMap.put("salesarea", salesarea);
		BranduserAssessExtend branduserAssessExtend = financeFundsReturnService.getBranduserAssessExtendByParam(paramMap);
		if(null != branduserAssessExtend){
			request.setAttribute("branduserAssessExtend", branduserAssessExtend);
			return "editsuccess";
		}else{
			return "addsuccess";
		}
	}
	
	/**
	 * 新增品牌业务员考核扩展
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 11, 2014
	 */
	@UserOperateLog(key="BranduserAssess",type=2,value="新增品牌业务员考核")
	public String addBranduserAssessExtend()throws Exception{
		String check = request.getParameter("check");
		String businessdate = request.getParameter("businessdate");
		String branduser = request.getParameter("branduser");
		if(StringUtils.isNotEmpty(businessdate)){
			pageMap.getCondition().put("businessdate", businessdate);
		}
		if(StringUtils.isNotEmpty(branduser)){
			pageMap.getCondition().put("branduser", branduser);
		}
		branduserAssessExtend.setPageMap(pageMap);
		Map retmap = new HashMap();
		if("1".equals(check)){
			Map map = new HashMap();
			map.put("businessdate", branduserAssessExtend.getBusinessdate());
			map.put("branduser", branduserAssessExtend.getBranduser());
			map.put("salesarea", branduserAssessExtend.getSalesarea());
			BranduserAssessExtend bAssessExtend = financeFundsReturnService.getBranduserAssessExtendByParam(map);
			if(null != bAssessExtend){
				branduserAssessExtend.setId(bAssessExtend.getId());
				retmap = financeFundsReturnService.editBranduserAssessExtend(branduserAssessExtend);
			}
		}else if("0".equals(check)){
			retmap = financeFundsReturnService.addBranduserAssessExtend(branduserAssessExtend);
		}
		addJSONObject(retmap);
		return SUCCESS;
	}
	
	/**
	 * 获取超账金额
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	public String getTotalPassAmount()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String today = CommonUtils.getTodayDataStr();
		map.put("businessdate2", today);
		boolean flag = financeFundsReturnService.getTotalPassAmount(map);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 导出品牌业务员考核扩展报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 3, 2014
	 */
	public void exportBranduserAssessExtendReportData()throws Exception{
		Map<String, String> map2 = CommonUtils.changeMap(request.getParameterMap());
		map2.put("isflag", "true");
		String title = "";
		if(map2.containsKey("excelTitle")){
			title = map2.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		pageMap.setCondition(map2);
		PageData pageData = financeFundsReturnService.getBranduserAssessReportExtendData(pageMap);
		pageData.getList().addAll(pageData.getFooter());
		
		List<Map> list = pageData.getList();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("salesareaname", "地区");
		firstMap.put("brandusername", "销售主管");
		firstMap.put("wdtargetamount", "回笼指标");
		firstMap.put("otherwdamount", "其他回笼");
		firstMap.put("erpwdamount", "ERP回笼");
		firstMap.put("realwdamount", "实际回笼");
		firstMap.put("totalscore", "得分");
		firstMap.put("returnamount", "退货金额");
		firstMap.put("returnsubamount", "退货扣减");
		firstMap.put("totalpassamount1", "超账金额1");
		firstMap.put("totalpasssubamount1", "超账扣减1");
		firstMap.put("totalpassamount2", "超账金额2");
		firstMap.put("totalpasssubamount2", "超账扣减2");
		firstMap.put("totalpassamount3", "超账金额3");
		firstMap.put("totalpasssubamount3", "超账扣减3");
		firstMap.put("totalpassamount4", "超账金额4");
		firstMap.put("totalpasssubamount4", "超账扣减4");
		firstMap.put("bonus", "奖金");
		firstMap.put("kpitargetamount", "kpi目标");
		firstMap.put("kpibonusamount", "kpi奖金");
		firstMap.put("bonusamount", "奖金总额");
		firstMap.put("wardenamount", "区长");
		firstMap.put("amountsum", "合计");
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
		ExcelUtils.exportExcel(result,title);
	}
	
	/*----------------------------品牌业务员考核扩展结束-------------------------------------*/
	
	/**
	 * 根据客户编码获取其销售流水明细页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 24, 2014
	 */
	public String showSalesuserRPDSalesflowPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("customerid", customerid);
		request.setAttribute("customername", customername);
		return SUCCESS;
	}
	
	/**
	 * 分供应商单资金应收情况统计页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 1, 2014
	 */
	public String showSupplierReceiptListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 分供应商单资金应收情况统计
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 1, 2014
	 */
	public String showSupplierFinanceReceiptData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List list = financeFundsReturnService.showSupplierFinanceReceiptData(pageMap);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 导出分供应商单资金应收情况统计
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 1, 2014
	 */
	public void exportSupplierFinanceReceiptData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
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

		//数据转换，list转化符合excel导出的数据格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("branddeptname", "品牌部门");
		firstMap.put("allunwithdrawnamount", "应收款总额");
		firstMap.put("unauditamount", "未验收应收款");
		firstMap.put("auditamount", "已验收应收款");
		firstMap.put("rejectamount", "退货应收款");
		firstMap.put("allpushbalanceamount", "冲差应收款");
		result.add(firstMap);
		
		List<BaseSalesWithdrawnReport> list = financeFundsReturnService.showSupplierFinanceReceiptData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(baseSalesWithdrawnReport);
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
		
		ExcelUtils.exportExcel(result, title);
	}

	/**
	 * 显示销售单据核销报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 15, 2014
	 */
	public String showSalesBillCheckListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 导出销售单据核对报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 16, 2014
	 */
	public void exportSalesBillCheckReportList()throws Exception{

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
        PageData pageData = salesBillCheckService.showSalesBillCheckData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

	}
	
	/*--------------------------------预期应收款-----------------------------------*/
	
	/**
	 * 显示客户预期应收款页面
	 */
	public String showCustomerExpectReceiptListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取客户预期应收款报表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 8, 2014
	 */
	public String showCustomerExpectReceiptListData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.getCustomerExpectReceiptListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出客户预期应收款报表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 9, 2014
	 */
	public void exportCustomerExpectReceiptData()throws Exception{
		String groupcols = null;
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        if(map.containsKey("groupcols") && null != map.get("groupcols")){
            groupcols = (String)map.get("groupcols");
            queryMap.put("groupcols",groupcols);
        }
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

       	PageData pageData = financeFundsReturnService.getCustomerExpectReceiptListData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            list.addAll(pageData.getFooter());
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}


    /**
     * 显示客户预期应收款明细页面
     * @return
     * @throws Exception
     */
    public String showCustomerExpectReceiptDetailPage()throws Exception{
        return SUCCESS;
    }
    
    /**
     * 资金回笼按月统计
     * @return
     * @throws Exception
     * @author huangzhiqian
     * @date 2016年1月14日
     */
    public String showmonthFinanceDrawnPage()throws Exception{
    	request.setAttribute("current", new SimpleDateFormat("yyyy").format(new Date()));
        return SUCCESS;
    }
    
    /**
     * 资金回笼按月统计数据
     * @return
     * @throws Exception
     * @author huangzhiqian
     * @date 2016年1月14日
     */
    public String showMonthsFinanceDrawnData()throws Exception{
    	Map map = CommonUtils.changeMap(request.getParameterMap());
    	pageMap.setCondition(map);
    	PageData pageData = financeFundsReturnService.showMonthfinanceFundsReturnData(pageMap);
    	addJSONObjectWithFooter(pageData);
//    	Map map = CommonUtils.changeMap(request.getParameterMap());
//		if(map.containsKey("branddept") && "0000".equals(map.get("branddept"))){
//			map.put("branddept", "null");
//		}
//      if(map.containsKey("supplieruser") && "0000".equals(map.get("supplieruser"))){
//          map.put("supplieruser", "null");
//      }
    	return SUCCESS;
    }

	/**
	 * 打印资金回笼按月统计数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 11, 2018
	 */
    public String printMonthsFinanceDrawnData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = financeFundsReturnService.showMonthfinanceFundsReturnData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "资金回笼按月统计报表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}
    
	/**
	 * 导出单资金回笼按月统计情况表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 23, 2013
	 */
	public void exportMonthFinanceDrawnData()throws Exception{
		String groupcols = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("limit", "false");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		PageData pageData = financeFundsReturnService.showMonthfinanceFundsReturnData(pageMap);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		Map filedMap = new HashMap();
		firstMap = getFirstMapForMonth(groupcols, filedMap);
		result.add(firstMap);
		
		List<MonthSaleWithdrawnReport> list2 =  pageData.getList();
		list2.addAll(pageData.getFooter());
		List<Map<String, Object>> list = objectToMapListForMonth(list2,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	
	/**
	 * 根据定义类型获取显示的字段(资金回笼情况表)
	 * @param groupcols:customerid,pcustomerid,salesuserid,salesarea,deptid,goodsid,branddept,branduser,brand
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 18, 2013
	 */
	private Map<String, Object> getFirstMapForMonth(String groupcols,Map map)throws Exception{
		Map map3 = getAccessColumn("t_report_sales_base");
		Map<String,Object> map2 = new LinkedHashMap<String, Object>();
		if(StringUtils.isNotEmpty(groupcols)){
			if(groupcols.indexOf(",") == -1){
				if("customerid".equals(groupcols)){
					map2.put("customerid", "客户编码");
					map2.put("customername", "客户名称");
					map2.put("pcustomername", "总店名称");
					map2.put("salesusername", "客户业务员");
				}
				else if("pcustomerid".equals(groupcols)){
					map2.put("pcustomername", "总店名称");
				}
				else if("supplierid".equals(groupcols)){
					map2.put("supplierid", "供应商编码");
					map2.put("suppliername", "供应商名称");
					map2.put("branddeptname", "品牌部门");
				}
				else if("salesuser".equals(groupcols)){
					map2.put("salesusername", "客户业务员");
				}
				else if("salesarea".equals(groupcols)){
					map2.put("salesareaname", "销售区域");
				}
				else if("salesdept".equals(groupcols)){
					map2.put("salesdeptname", "销售部门");
				}
				else if("goodsid".equals(groupcols)){
					map2.put("goodsid", "商品编码");
					map2.put("goodsname", "商品名称");
					map2.put("barcode", "条形码");
					map2.put("brandname", "商品品牌");
				}
				else if("brandid".equals(groupcols)){
					map2.put("brandname", "商品品牌");
					if(map.containsKey("branddeptcol")){
						map2.put("branddeptname", "品牌部门");
					}
				}
				else if("branduser".equals(groupcols)){
					map2.put("brandusername", "品牌业务员");
					map2.put("branddeptname", "品牌部门");
				}
				else if("branddept".equals(groupcols)){
					map2.put("branddeptname", "品牌部门");
				}else if("supplieruser".equals(groupcols)){
                    map2.put("supplierusername", "厂家业务员");
                }else if("supplierid".equals(groupcols)){
                    map2.put("suppliername", "供应商");
                }
			}else{
				String[] groupcolsArr = groupcols.split(",");
				for(String  group: groupcolsArr){
					if("customerid".equals(group)){
						map2.put("customerid", "客户编码");
						map2.put("customername", "客户名称");
						map2.put("pcustomername", "总店名称");
						map2.put("salesusername", "客户业务员");
					}
					else if("pcustomerid".equals(group)){
						map2.put("pcustomername", "总店名称");
					}
					else if("supplierid".equals(groupcols)){
						map2.put("supplierid", "供应商编码");
						map2.put("suppliername", "供应商名称");
						map2.put("branddeptname", "品牌部门");
					}
					else if("salesuser".equals(group)){
						map2.put("salesusername", "客户业务员");
					}
					else if("salesarea".equals(group)){
						map2.put("salesareaname", "销售区域");
					}
					else if("salesdept".equals(group)){
						map2.put("salesdeptname", "销售部门");
					}
					else if("goodsid".equals(group)){
						map2.put("goodsid", "商品编码");
						map2.put("goodsname", "商品名称");
						map2.put("barcode", "条形码");
						map2.put("brandname", "商品品牌");
                        map2.put("unitnum", "数量");
                        map2.put("auxnumdetail", "辅数量");
					}
					else if("brandid".equals(group)){
						map2.put("brandname", "商品品牌");
					}
					else if("branduser".equals(group)){
						map2.put("brandusername", "品牌业务员");
						map2.put("branddeptname", "品牌部门");
					}
					else if("branddept".equals(group)){
						map2.put("branddeptname", "品牌部门");
					}else if("supplieruser".equals(groupcols)){
                        map2.put("supplierusername", "厂家业务员");
                    }else if("supplierid".equals(groupcols)){
                        map2.put("suppliername", "供应商");
                    }
				}
			}
		}else{
			map2.put("customerid", "客户编码");
			map2.put("customername", "客户名称");
			map2.put("pcustomername", "总店名称");
			map2.put("salesusername", "客户业务员");
			map2.put("branddeptname", "品牌部门");
			map2.put("brandusername", "品牌业务员");
            
			map2.put("goodsid", "商品编码");
			map2.put("goodsname", "商品名称");
			map2.put("barcode", "条形码");
			map2.put("brandname", "商品品牌");
			map2.put("suppliername", "供应商");
		}
		
		map2.put("withdrawnamount01", "回笼金额1月");
		map2.put("writeoffrate01", "毛利率1月");
		map2.put("withdrawnamount02", "回笼金额2月");
		map2.put("writeoffrate02", "毛利率2月");
		map2.put("withdrawnamount03", "回笼金额3月");
		map2.put("writeoffrate03", "毛利率3月");
		map2.put("withdrawnamount04", "回笼金额4月");
		map2.put("writeoffrate04", "毛利率4月");
		map2.put("withdrawnamount05", "回笼金额5月");
		map2.put("writeoffrate05", "毛利率5月");
		map2.put("withdrawnamount06", "回笼金额6月");
		map2.put("writeoffrate06", "毛利率6月");
		map2.put("withdrawnamount07", "回笼金额7月");
		map2.put("writeoffrate07", "毛利率7月");
		map2.put("withdrawnamount08", "回笼金额8月");
		map2.put("writeoffrate08", "毛利率8月");
		map2.put("withdrawnamount09", "回笼金额9月");
		map2.put("writeoffrate09", "毛利率9月");
		map2.put("withdrawnamount10", "回笼金额10月");
		map2.put("writeoffrate10", "毛利率10月");
		map2.put("withdrawnamount11", "回笼金额11月");
		map2.put("writeoffrate11", "毛利率11月");
		map2.put("withdrawnamount12", "回笼金额12月");
		map2.put("writeoffrate12", "毛利率12月");
		return map2;
	}

	
	/**
	 * 将获取的list转化为List<Map<String, Object>>
	 * @param list
	 * @param firstMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 15, 2014
	 */
	private List<Map<String, Object>> objectToMapListForMonth(List<MonthSaleWithdrawnReport> list,Map<String, Object> firstMap)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(MonthSaleWithdrawnReport baseSalesWithdrawnReport : list){
			if(null != baseSalesWithdrawnReport){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(baseSalesWithdrawnReport);
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
	 * 显示客户总应收款报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-24
	 */
	public String showCustomerTotalReceiptListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}

	/**
	 * 获取客户总应收款报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-24
	 */
	public String getCustomerTotalReceiptReportList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.getCustomerTotalReceiptReportList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 显示对应客户总应收明细表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-25
	 */
	public String showCustomerTotalReceiptDetailReportPage()throws Exception{
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
	 * 获取客户总应收款报表明细数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-25
	 */
	public String getCustomerTotalReceiptReportDetailList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = financeFundsReturnService.getCustomerTotalReceiptReportDetailList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 导出客户总应收款报表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-24
	 */
	public void exportCustomerTotalReceiptReportList()throws Exception{
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
		PageData pageData = financeFundsReturnService.getCustomerTotalReceiptReportList(pageMap);

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("pcustomername", "总店名称");
		firstMap.put("salesdeptname", "销售部门");
		firstMap.put("inittotalreceiptamount", "期初总应收额");
		firstMap.put("saleamount", "销售金额");
		firstMap.put("tailamount", "尾差金额");
		firstMap.put("receiptamount", "收款金额");
		firstMap.put("totalreceiptamount", "期末总应收额");
		result.add(firstMap);

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
	 * 导出客户总应收款明细
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-28
	 */
	public void exportCustomerTotalReceiptDetailData()throws Exception{
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
		PageData pageData = financeFundsReturnService.getCustomerTotalReceiptReportDetailList(pageMap);

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("billtypename", "单据类型");
		firstMap.put("id", "单据编号");
		firstMap.put("orderid", "订单编号");
		firstMap.put("inittotalreceiptamount", "期初总应收额");
		firstMap.put("saleamount", "销售金额");
		firstMap.put("tailamount", "尾差金额");
		firstMap.put("receiptamount", "收款金额");
		firstMap.put("totalreceiptamount", "期末总应收额");
		firstMap.put("remark", "备注");
		result.add(firstMap);

		List<Map<String,Object>> list = pageData.getList();

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
	 * 分客户业务员回笼报表打印
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Feb 02, 2018
	 */
	public String printBaseFinanceDrawnData() throws Exception {
		String strPageName=request.getParameter("strPageName");
		String intOrient=request.getParameter("intOrient");
		request.setAttribute("strPageName",strPageName);
		request.setAttribute("intOrient",intOrient);
		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}
		if(map.containsKey("groupcols")){
			queryMap.put("groupcols",(String)map.get("groupcols"));
		}
		pageMap.setCondition(queryMap);
		PageData pageData = financeFundsReturnService.showBaseFinanceDrawnData(pageMap);
		List list = pageData.getList();
		if (null != pageData.getFooter()) {
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		if (StringUtils.isEmpty(title))
			title = "资金回笼情况表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}

	/**
	 * 按公司打印回笼报表
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Feb 08, 2018
	 */
	public String printCompanyWithdrawnList() throws Exception{

		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());

		Integer rownum=Integer.parseInt(request.getParameter("rownum"));
		String title=request.getParameter("title");
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if (StringUtils.isNotEmpty((String) v)) {
				queryMap.put(k.toString(), v);
			}
		}

		pageMap.setCondition(queryMap);
		Map map2 = financeFundsReturnService.getCompanyWithdrawnList(pageMap);
		List list = (List) map2.get("list");
		list.addAll((List)map2.get("footer"));
		if (StringUtils.isEmpty(title))
			title = "分客户业务员资金回笼表";
		request.setAttribute("title", title);
		request.setAttribute("body", ExcelUtils.dataToHTML(map, list, rownum));
		return SUCCESS;
	}
}

