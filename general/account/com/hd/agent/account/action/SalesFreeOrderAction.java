/**
 * @(#)SalesFreeOrderAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年5月29日 chenwei 创建版本
 */
package com.hd.agent.account.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.account.model.ReceivablePastDueReason;
import com.hd.agent.account.service.ISalesFreeOrderService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.PaymentdaysSet;
import com.hd.agent.report.service.IPaymentdaysSetService;

/**
 * 
 * 销售放单管理
 * @author chenwei
 */
public class SalesFreeOrderAction extends BaseFilesAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9212376617314235765L;
	/**
	 * 销售放单管理service
	 */
	private ISalesFreeOrderService salesFreeOrderService;
	private IPaymentdaysSetService paymentdaysSetService;
	private ReceivablePastDueReason receivablePastDueReason;
	
	public ReceivablePastDueReason getReceivablePastDueReason() {
		return receivablePastDueReason;
	}

	public void setReceivablePastDueReason(
			ReceivablePastDueReason receivablePastDueReason) {
		this.receivablePastDueReason = receivablePastDueReason;
	}

	public ISalesFreeOrderService getSalesFreeOrderService() {
		return salesFreeOrderService;
	}
	public void setSalesFreeOrderService(
			ISalesFreeOrderService salesFreeOrderService) {
		this.salesFreeOrderService = salesFreeOrderService;
	}
	
	public IPaymentdaysSetService getPaymentdaysSetService() {
		return paymentdaysSetService;
	}

	public void setPaymentdaysSetService(
			IPaymentdaysSetService paymentdaysSetService) {
		this.paymentdaysSetService = paymentdaysSetService;
	}

	/**
	 * 显示放单列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public String showSalesFreeOrderListPage()throws Exception{
		String today = CommonUtils.getTodayDataStr();
		request.setAttribute("today", today);
		return SUCCESS;
	}
	
	/**
	 * 获取放单列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public String getSalesFreeOrderList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesFreeOrderService.getSalesFreeOrderData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	 /**
	  * 全局导出放单管理数据
	  * @author lin_xx
	  * @date 2016/12/19
	  */
	 public String overalExportSalesFreeOrderList() throws Exception{

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
		 PageData pageData = salesFreeOrderService.getSalesFreeOrderData(pageMap);
		 List list = pageData.getList();
		 if(null != pageData.getFooter()){
			 List footer = pageData.getFooter();
			 list.addAll(footer);
		 }
		 ExcelUtils.exportAnalysExcel(map,list);

		 return SUCCESS;
	 }
	
	/**
	 * 批量审核放单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	@UserOperateLog(key="SalesFreeOrder",type=3)
	public String auditSalesFreeOrder()throws Exception{
		String ids = request.getParameter("ids");
		Map map = salesFreeOrderService.auditSalesFreeOrder(ids);
		addLog("放单审核,编号:"+ids, map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 批量反审放单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	@UserOperateLog(key="SalesFreeOrder",type=3)
	public String oppauditSalesFreeOrder()throws Exception{
		String ids = request.getParameter("ids");
		Map map = salesFreeOrderService.oppauditSalesFreeOrder(ids);
		addLog("放单反审,编号:"+ids, map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 删除反审单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	@UserOperateLog(key="SalesFreeOrder",type=4)
	public String deleteSalesFreeOrder()throws Exception{
		String ids = request.getParameter("ids");
		boolean flag = salesFreeOrderService.deleteSalesFreeOrder(ids);
		addLog("放单删除,编号:"+ids, flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/*--------------------------------应收款---------------------------------------*/
	
	/**
	 * 显示客户应收款超账原因页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 30, 2013
	 */
	public String showCustomerReceivablePastDueReasonListPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}
	/**
	 * 获取应收款超账期原因数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 15, 2013
	 */
	public String showCustomerReceivablePastDueReasonListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesFreeOrderService.showBaseReceivablePassDueReasonListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 显示分客户品牌业务员应收款超账期原因页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 24, 2014
	 */
	public String showCustomerBranduserReceivablePastDueReasonListPage()throws Exception{
		List list = paymentdaysSetService.getPaymentdaysSetInfo();
		request.setAttribute("list", list);
		return SUCCESS;
	}

    /**
     * 显示分客户业务员应收款超账期原因页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-08-27
     */
    public String showSalesuserReceivablePastDueReasonListPage()throws Exception{
        List list = paymentdaysSetService.getPaymentdaysSetInfo();
        request.setAttribute("list", list);
        return SUCCESS;
    }

    /**
     * 显示分客户业务员明细应收款超账期原因页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-08-31
     */
    public String showSalesuserDetailReceivablePastDueReasonListPage()throws Exception{
        String salesuser = request.getParameter("salesuser");
        if(StringUtils.isEmpty(salesuser)){
            salesuser = "null";
        }
        request.setAttribute("salesuser",salesuser);
        String salesusername = request.getParameter("salesusername");
        request.setAttribute("salesusername",salesusername);
        List list = paymentdaysSetService.getPaymentdaysSetInfo();
        request.setAttribute("list", list);
        String ispastdue = request.getParameter("ispastdue");
        request.setAttribute("ispastdue",ispastdue);
        return SUCCESS;
    }

	/**
	 * 编辑客户应收款超账期原因页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public String showEditCustomerReceivablePastDueReasonPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String rowindex = request.getParameter("rowindex");
		String saleamount = request.getParameter("saleamount");
		String unpassamount = request.getParameter("unpassamount");
		String totalpassamount = request.getParameter("totalpassamount");
		ReceivablePastDueReason receivablePastDueReason = salesFreeOrderService.getCustomerReceivablePastDueReason(customerid);
		request.setAttribute("receivablePastDueReason", receivablePastDueReason);
		request.setAttribute("customerid", customerid);
		request.setAttribute("rowindex", rowindex);
		request.setAttribute("saleamount", saleamount);
		request.setAttribute("unpassamount", unpassamount);
		request.setAttribute("totalpassamount", totalpassamount);
		return SUCCESS;
	}
	
	/**
	 * 导出客户应收款超账期原因
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 22, 2014
	 */
	public void exportBasereceivablePastDueResonData()throws Exception{
		String groupcols = null,type = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", "true");
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
		}
		if(map.containsKey("type") && null != map.get("type")){
			type = (String)map.get("type");
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

		//数据转换，list转化符合excel导出的数据格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		if(groupcols.indexOf("branduser") != -1){
			firstMap.put("brandusername", "品牌业务员");
			firstMap.put("branddeptname", "品牌部门");
		}
        if(groupcols.indexOf("customerid") != -1){
            firstMap.put("customerid", "客户编号");
            firstMap.put("customername", "客户名称");
        }
		if(groupcols.indexOf("salesuser") != -1){
            firstMap.put("salesusername", "客户业务员");
        }
		if(type.equals("customerid")){
			firstMap.put("beginamount", "应收款期初");
		}
		firstMap.put("saleamount", "应收款");
		firstMap.put("unpassamount", "正常期金额");
		firstMap.put("totalpassamount", "超账期金额");
		List<PaymentdaysSet> list2 = paymentdaysSetService.getPaymentdaysSetInfo();
		if(list2.size() != 0){
			for(PaymentdaysSet paymentdaysSet : list2){
				firstMap.put("passamount"+paymentdaysSet.getSeq(),paymentdaysSet.getDetail());
			}
		}
        if(groupcols.indexOf("customerid") != -1){
            firstMap.put("overreason", "超账期原因");
            firstMap.put("commitmentamount", "承诺到款金额");
            firstMap.put("commitmentdate", "承诺到款日期");
            firstMap.put("actualamount", "实际到款金额");
            firstMap.put("cstmerbalance", "客户余额");
			if(type.equals("customerid")){
				firstMap.put("realsaleamount", "实际应收款");
				firstMap.put("salesusername", "客户业务员");
			}
            firstMap.put("payeename", "收款人");
			if(type.equals("customerid")){
				firstMap.put("settletypename", "结算方式");
				firstMap.put("settleday", "结算日");
			}
        }
		result.add(firstMap);
		
		PageData pageData = salesFreeOrderService.showBaseReceivablePassDueReasonListData(pageMap);
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);
		
		ExcelUtils.exportExcel(result, title);
	}
	
	/**
	 * 编辑客户应收款超账期原因
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 22, 2014
	 */
	@UserOperateLog(key="ReceivablePastDueReason",type=2,value="")
	public String addCustomerReceivablePastDueReason()throws Exception{
		Map map = new HashMap();
		String rowindex = request.getParameter("rowindex");
		Customer isexistCustomer = getBaseSalesService().getCustomerInfo(receivablePastDueReason.getCustomerid());
		if(null != isexistCustomer){
			map = salesFreeOrderService.addCustomerReceivablePastDueReason(receivablePastDueReason);
			addLog("编辑客户应收款超账期原因 客户编号:"+receivablePastDueReason.getCustomerid(),map.get("flag").equals(true));
			map.put("rowindex", rowindex);
		}else{
			map.put("msg", "不存在该客户,请完善数据!");
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 一键清除客户应收款超账期原因
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 22, 2014
	 */
	@UserOperateLog(key="ReceivablePastDueReason",type=4)
	public String oneClearReceivablePastDueReason()throws Exception{
		boolean flag = salesFreeOrderService.oneClearReceivablePastDueReason();
		addLog("一键清除客户应收款超账期原因",flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 显示历史超账期原因页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 23, 2014
	 */
	public String showHistoryCustomerReceivablePastDueReasonPage()throws Exception{
		String customerid = request.getParameter("customerid");
		request.setAttribute("customerid", customerid);
		return SUCCESS;
	}
	
	/**
	 * 获取历史超账期原因列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 23, 2014
	 */
	public String getHistoryCustomerReceivablePastDueReasonList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesFreeOrderService.getHistoryCustomerReceivablePastDueReasonList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据客户编码获取该客户销售流水明细数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 23, 2014
	 */
	public String showCustomerSalesFlowDetailListPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String branduser = request.getParameter("branduser");
		String brandusername = request.getParameter("brandusername");
		Map map = getAccessColumn("t_report_sales_base");
		request.setAttribute("map", map);
		request.setAttribute("customerid", customerid);
		request.setAttribute("customername", customername);
		request.setAttribute("branduser", branduser);
		request.setAttribute("brandusername", brandusername);
		return SUCCESS;
	}

	/**
	 * 代垫应收分析报表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 27, 2017
	 */
	public String showSupplierReceivablePastDueReasonListPage() throws Exception {
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		List list = paymentdaysSetService.getPaymentdaysSetInfo("3");
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("list", list);
		return "success";
	}

	/**
	 * 获取代垫应收分析报表数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 27, 2017
	 */
	public String showSupplierReceivablePastDueReasonListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesFreeOrderService.showSupplierReceivablePastDueReasonListData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

	/**
	 * 代垫应收分析报表导出
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Oct 30, 2017
	 */
	public void exportSupplierPastDueResonData()throws Exception {
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

		//数据转换，list转化符合excel导出的数据格式
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();

		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("beginamount", "应收款期初");
		firstMap.put("saleamount", "应收款");
		firstMap.put("unpassamount", "正常期金额");
		firstMap.put("totalpassamount", "超账期金额");
		List<PaymentdaysSet> list2 = paymentdaysSetService.getPaymentdaysSetInfo("3");
		if(list2.size() != 0){
			for(PaymentdaysSet paymentdaysSet : list2){
				firstMap.put("passamount"+paymentdaysSet.getSeq(),paymentdaysSet.getDetail());
			}
		}
		firstMap.put("settletypename", "结算方式");
		firstMap.put("settleday", "结算日");

		result.add(firstMap);

		PageData pageData = salesFreeOrderService.showSupplierReceivablePastDueReasonListData(pageMap);
		List<Map<String, Object>> list = mapToMapList(pageData,firstMap);
		result.addAll(list);

		ExcelUtils.exportExcel(result, title);
	}

	/**
	 * 根据供应商获取代垫应收明细数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 30, 2017
	 */
	public String showSupplierMatcostsListPage()throws Exception {
		String supplierid=request.getParameter("supplierid");
		String suppliername=request.getParameter("suppliername");
		request.setAttribute("supplierid",supplierid);
		request.setAttribute("suppliername",suppliername);
		request.setAttribute("today",CommonUtils.getTodayDateStr());
		return SUCCESS;
	}

	/**
	 * 显示供应商代垫超账期数据页面
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Oct 31, 2017
	 */
	public String showSendSupplierPastDueListPage(){
		return SUCCESS;
	}
}

