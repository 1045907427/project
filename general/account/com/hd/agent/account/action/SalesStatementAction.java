/**
 * @(#)SalesStatementAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 11, 2013 chenwei 创建版本
 */
package com.hd.agent.account.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.account.model.CollectionOrderRelate;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.account.model.SalesStatement;
import com.hd.agent.account.service.ICollectionOrderService;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.account.service.ISalesInvoiceBillService;
import com.hd.agent.account.service.ISalesInvoiceService;
import com.hd.agent.account.service.ISalesStatementService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 销售发票核销 对账单等action
 * @author chenwei
 */
public class SalesStatementAction extends BaseFilesAction {
	
	private List<SalesStatement> salesStatementList;
	/**
	 * 收款单对账单service
	 */
	private ISalesStatementService salesStatementService;
	/**
	 * 收款单service
	 */
	private ICollectionOrderService collectionOrderService;
	/**
	 * 销售发票service
	 */
	private ISalesInvoiceService salesInvoiceService;
	
	/**
	 * 销售开票service
	 */
	private ISalesInvoiceBillService salesInvoiceBillService;
	/**
	 * 客户冲差service
	 */
	private ICustomerPushBanlanceService customerPushBanlanceService;
	
	//普通版特有开始
	public ISalesInvoiceBillService getSalesInvoiceBillService() {
		return salesInvoiceBillService;
	}
	public void setSalesInvoiceBillService(
			ISalesInvoiceBillService salesInvoiceBillService) {
		this.salesInvoiceBillService = salesInvoiceBillService;
	}
	//普通版特有结束
	public ISalesStatementService getSalesStatementService() {
		return salesStatementService;
	}
	public void setSalesStatementService(
			ISalesStatementService salesStatementService) {
		this.salesStatementService = salesStatementService;
	}
	
	public ICollectionOrderService getCollectionOrderService() {
		return collectionOrderService;
	}
	public void setCollectionOrderService(
			ICollectionOrderService collectionOrderService) {
		this.collectionOrderService = collectionOrderService;
	}
	public ISalesInvoiceService getSalesInvoiceService() {
		return salesInvoiceService;
	}
	public void setSalesInvoiceService(ISalesInvoiceService salesInvoiceService) {
		this.salesInvoiceService = salesInvoiceService;
	}
	
	public List<SalesStatement> getSalesStatementList() {
		return salesStatementList;
	}
	public void setSalesStatementList(List<SalesStatement> salesStatementList) {
		this.salesStatementList = salesStatementList;
	}
	
	public ICustomerPushBanlanceService getCustomerPushBanlanceService() {
		return customerPushBanlanceService;
	}
	public void setCustomerPushBanlanceService(
			ICustomerPushBanlanceService customerPushBanlanceService) {
		this.customerPushBanlanceService = customerPushBanlanceService;
	}
	/**
	 * 显示收款单核销页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public String showWriteoffCollectionOrderPage() throws Exception{
		String id = request.getParameter("id");
		String invoiceid = request.getParameter("invoiceid");
		CustomerCapital customerCapital = collectionOrderService.getCustomerCapitalByCustomerid(id);
		request.setAttribute("customerCapital", customerCapital);
		request.setAttribute("invoiceid", invoiceid);
		return "success";
		
	}
	/**
	 * 根据客户编号获取该客户下的销售发票
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	public String showSalesInvoiceListByCustomerid() throws Exception{
		String id = request.getParameter("id");
		List list = salesInvoiceService.showSalesInvoiceListByCustomerid(id);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示核销确认页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public String showWriteoffPage() throws Exception{
		String customerid = request.getParameter("customerid");
		String invoiceid = request.getParameter("invoiceid");
		CustomerCapital customerCapital = collectionOrderService.getCustomerCapitalByCustomerid(customerid);
		if(null!=customerCapital){
			//获取尾差金额控制
//			String tailAmountLimit = getSysParamValue("tailAmountLimit");
//			if(null==tailAmountLimit || !StringUtils.isNumeric(tailAmountLimit)){
//				tailAmountLimit = "0";
//			}
			BigDecimal afterTailContr = BigDecimal.ZERO;
			String positeTailAmountStr = getSysParamValue("positeTailAmountLimit");
			if(StringUtils.isNotEmpty(positeTailAmountStr)){
				afterTailContr = new BigDecimal(positeTailAmountStr);
			}

			BigDecimal beforeTailContr = BigDecimal.ZERO;
			String negateTailAmountStr = getSysParamValue("negateTailAmountLimit");
			if(StringUtils.isNotEmpty(negateTailAmountStr)){
				beforeTailContr = new BigDecimal(negateTailAmountStr);
			}

			List<SalesInvoice> salesInvoiceList = salesInvoiceService.getSalesInvoiceListByInvoiceids(invoiceid);
			BigDecimal invoiceAmount = new BigDecimal(0);
			for(SalesInvoice salesInvoice : salesInvoiceList){
				BigDecimal salesinvoiceamount = BigDecimal.ZERO;
				if(null!= salesInvoice.getInvoiceamount()){
					salesinvoiceamount= salesInvoice.getInvoiceamount();
				}else{
					salesInvoice.setInvoiceamount(BigDecimal.ZERO);
				}
				invoiceAmount = invoiceAmount.add(salesinvoiceamount);
				BigDecimal taxamount = null != salesInvoice.getTaxamount() ? salesInvoice.getTaxamount() : BigDecimal.ZERO;
				BigDecimal tailamount = BigDecimal.ZERO.subtract(taxamount);
				//若销售发票不关联，判读是否在尾差控制范围内
				if(!"1".equals(salesInvoice.getIsrelate())){
					if(tailamount.compareTo(beforeTailContr) >= 0 && tailamount.compareTo(afterTailContr) <= 0){
						salesInvoice.setIsrelate("2");
					}
				}
			}
			request.setAttribute("customerCapital", customerCapital);
			request.setAttribute("salesInvoiceList", salesInvoiceList);
			request.setAttribute("invoiceAmount", invoiceAmount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			request.setAttribute("invoiceid", invoiceid);
			request.setAttribute("beforeTailContr", beforeTailContr);
			request.setAttribute("afterTailContr", afterTailContr);
			return "success";
		}else{
			request.setAttribute("writeoffPage", true);
			return "error";
		}
	}
	
	/**
	 * 收款单核销审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 11, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=3)
	public String auditWriteoffCollectionOrder() throws Exception{
		String customerid = request.getParameter("customerid");
		Map map = salesStatementService.auditWriteoffCollectionOrder(customerid, salesStatementList);
		addJSONObject(map);
		String invoiceids = "";
		if(null!=salesStatementList){
			for(SalesStatement salesStatement : salesStatementList){
				invoiceids += salesStatement.getBillid()+",";
			}
		}
		addLog("销售发票核销,客户编号:"+customerid+",核销单据编号:"+invoiceids, map);
		return "success";
	}
	
	/**
	 * 销售发票反核销
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	@UserOperateLog(key="SalesInvoice",type=0)
	public String uncancelSalesInvoice()throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		boolean flag = salesStatementService.cancelBackSalesInvoice(invoiceid);
		addLog("销售发票:"+invoiceid+"反核销", flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 显示对账单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public String showSalesStatementListPage() throws Exception{
		String customerid = request.getParameter("customerid");
		request.setAttribute("customerid", customerid);
		return "success";
	}
	/**
	 * 根据收款单编号获取对账单明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public String showSalesStatementList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(!map.containsKey("customerid")){
			map.put("customerid", "");
		}
		pageMap.setCondition(map);
		PageData pageData = salesStatementService.showSalesStatementData(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示发票关联收款单页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 30, 2013
	 */
	public String showInvoiceRelateCollectionPage() throws Exception{
		String customerid = request.getParameter("customerid");
		String billid = request.getParameter("billid");
		String billtype = request.getParameter("billtype");
		String amount = request.getParameter("amount");
		request.setAttribute("amount", amount);
		List collOrderList = collectionOrderService.getCollectionOrderListByCustomerid(customerid);
		String jsonStr = JSONUtils.listToJsonStr(collOrderList);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("billid",billid);
		request.setAttribute("billtype", billtype);
		//获取尾差金额控制
//		String tailAmountLimit = getSysParamValue("tailAmountLimit");
//		if(null!=tailAmountLimit && StringUtils.isNumeric(tailAmountLimit)){
//			request.setAttribute("tailAmountLimit", tailAmountLimit);
//		}else{
//			request.setAttribute("tailAmountLimit", 0);
//		}
		BigDecimal positeTailAmount = BigDecimal.ZERO;
		String positeTailAmountStr = getSysParamValue("positeTailAmountLimit");
		if(StringUtils.isNotEmpty(positeTailAmountStr)){
			positeTailAmount = new BigDecimal(positeTailAmountStr);
		}
		request.setAttribute("positeTailAmount", positeTailAmount);

		String negateTailAmountStr = getSysParamValue("negateTailAmountLimit");
		BigDecimal negateTailAmount = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(negateTailAmountStr)){
			negateTailAmount = new BigDecimal(negateTailAmountStr);
		}
		request.setAttribute("negateTailAmount", negateTailAmount);
		return "success";
	}
	
	/**
	 * 核销关联收款单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=2)
	public String addRelateCollectionOrder() throws Exception{
		String billid = request.getParameter("billid");
		String billtype = request.getParameter("billtype");
		String amountStr = request.getParameter("amount");
		BigDecimal amount = BigDecimal.ZERO;
		if(null!=amountStr && !"".equals(amountStr)){
			amount = new BigDecimal(amountStr);
		}
		String detailListStr = request.getParameter("detailList");
		List detailList = JSONUtils.jsonStrToList(detailListStr, new CollectionOrderRelate());
		
		boolean flag = salesStatementService.addRelateCollectionOrder(billid, billtype, amount, detailList);
		addJSONObject("flag", flag);
		addLog("销售发票关联收款单,单据编号:"+billid, flag);
		return "success";
	}
	
	/**
	 * 取消关联收款单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=4)
	public String deleteRelateCollectionOrder() throws Exception{
		String billid = request.getParameter("billid");
		String billtype = request.getParameter("billtype");
		boolean flag = salesStatementService.deleteRelateCollectionOrder(billid, billtype);
		addJSONObject("flag", flag);
		addLog("销售发票取消关联收款单,单据编号:"+billid, flag);
		return "success";
	}
	
	/**
	 * 查看关联的收款单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 2, 2013
	 */
	public String showRelateCollectionListPage() throws Exception{
		String billid = request.getParameter("billid");
		List list = salesStatementService.showRelateCollectionList(billid);
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("detailList", jsonStr);
		return "success";
	}
	
	/*------------------------普通版特有------------------------------*/
	/**
	 * 显示销售开票核销确认页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public String showWriteoffInvoicebillPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String invoiceid = request.getParameter("invoiceid");
		CustomerCapital customerCapital = collectionOrderService.getCustomerCapitalByCustomerid(customerid);
		if(null!=customerCapital){
			//获取尾差金额控制
//			String tailAmountLimit = getSysParamValue("tailAmountLimit");
//			if(null==tailAmountLimit || !StringUtils.isNumeric(tailAmountLimit)){
//				tailAmountLimit = "0";
//			}
			String positeTailAmount = getSysParamValue("positeTailAmountLimit");
			if(null==positeTailAmount || !StringUtils.isNumeric(positeTailAmount)){
				positeTailAmount = "0";
			}
			String negateTailAmount = getSysParamValue("negateTailAmountLimit");
			if(negateTailAmount.indexOf("-") != -1){
				negateTailAmount = negateTailAmount.substring(negateTailAmount.indexOf("-")+1, negateTailAmount.length());
			}
			if(null==negateTailAmount || !StringUtils.isNumeric(negateTailAmount)){
				negateTailAmount = "0";
			}
			BigDecimal beforeTailContr = new BigDecimal(negateTailAmount);
			if(beforeTailContr.compareTo(BigDecimal.ZERO) > 0){
				beforeTailContr = beforeTailContr.negate();
			}
			BigDecimal afterTailContr = new BigDecimal(positeTailAmount);
			List<SalesInvoiceBill> salesInvoiceBillList = salesInvoiceBillService.getSalesInvoiceBillListByInvoiceids(invoiceid);
			BigDecimal invoiceAmount = new BigDecimal(0);
			for(SalesInvoiceBill salesInvoiceBill : salesInvoiceBillList){
				BigDecimal salesinvoiceamount = BigDecimal.ZERO;
				if(null!= salesInvoiceBill.getTaxamount()){
					salesinvoiceamount= salesInvoiceBill.getTaxamount();
				}else{
					salesInvoiceBill.setTaxamount(BigDecimal.ZERO);
				}
				invoiceAmount = invoiceAmount.add(salesinvoiceamount);
				//若销售发票不关联，判读是否在尾差控制范围内
				if(!"1".equals(salesInvoiceBill.getIsrelate())){
					if(null != salesInvoiceBill.getTaxamount() && salesInvoiceBill.getTaxamount().compareTo(beforeTailContr) >= 0 
							&& salesInvoiceBill.getTaxamount().compareTo(afterTailContr) <= 0){
						salesInvoiceBill.setIsrelate("2");
					}
				}
			}
			request.setAttribute("customerCapital", customerCapital);
			request.setAttribute("salesInvoiceBillList", salesInvoiceBillList);
			request.setAttribute("invoiceAmount", invoiceAmount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			request.setAttribute("invoiceid", invoiceid);
			request.setAttribute("beforeTailContr", beforeTailContr);
			request.setAttribute("afterTailContr", afterTailContr);
			return "success";
		}else{
			request.setAttribute("writeoffPage", true);
			return "error";
		}
	}
	/**
	 * 显示销售开票关联收款单页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public String showInvoicebillRelateCollectionPage()throws Exception{
		String customerid = request.getParameter("customerid");
		String billid = request.getParameter("billid");
		String billtype = request.getParameter("billtype");
		String amount = request.getParameter("amount");
		request.setAttribute("amount", amount);
		List collOrderList = collectionOrderService.getCollectionOrderListByCustomerid(customerid);
		String jsonStr = JSONUtils.listToJsonStr(collOrderList);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("billid",billid);
		request.setAttribute("billtype", billtype);
		//获取尾差金额控制
//		String tailAmountLimit = getSysParamValue("tailAmountLimit");
//		if(null!=tailAmountLimit && StringUtils.isNumeric(tailAmountLimit)){
//			request.setAttribute("tailAmountLimit", tailAmountLimit);
//		}else{
//			request.setAttribute("tailAmountLimit", 0);
//		}
		String positeTailAmount = getSysParamValue("positeTailAmountLimit");
		if(null==positeTailAmount || !StringUtils.isNumeric(positeTailAmount)){
			request.setAttribute("positeTailAmount", 0);
		}else{
			request.setAttribute("positeTailAmount", positeTailAmount);
		}
		String negateTailAmount = getSysParamValue("negateTailAmountLimit");
		if(negateTailAmount.indexOf("-") != -1){
			negateTailAmount = negateTailAmount.substring(negateTailAmount.indexOf("-")+1, negateTailAmount.length());
		}
		if(null==negateTailAmount || !StringUtils.isNumeric(negateTailAmount)){
			request.setAttribute("negateTailAmount", 0);
		}else{
			BigDecimal beforeTailContr = new BigDecimal(negateTailAmount);
			if(beforeTailContr.compareTo(BigDecimal.ZERO) > 0){
				beforeTailContr = beforeTailContr.negate();
			}
			request.setAttribute("negateTailAmount", beforeTailContr);
		}
		return SUCCESS;
	}
	
	/**
	 * 销售开票核销关联收款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 17, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=2)
	public String addInvoiceBillRelateCollectionOrder()throws Exception{
		String billid = request.getParameter("billid");
		String billtype = request.getParameter("billtype");
		String amountStr = request.getParameter("amount");
		BigDecimal amount = BigDecimal.ZERO;
		if(null!=amountStr && !"".equals(amountStr)){
			amount = new BigDecimal(amountStr);
		}
		String detailListStr = request.getParameter("detailList");
		List detailList = JSONUtils.jsonStrToList(detailListStr, new CollectionOrderRelate());
		
		boolean flag = salesStatementService.addInvoiceBillRelateCollectionOrder(billid, billtype, amount, detailList);
		addJSONObject("flag", flag);
		addLog("销售开票关联收款单,单据编号:"+billid, flag);
		return SUCCESS;
	}
	
	/**
	 * 取消销售开票关联收款单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 17, 2015
	 */
	public String deleteInvoiceBillRelateCollectionOrder()throws Exception{
		String billid = request.getParameter("billid");
		String billtype = request.getParameter("billtype");
		boolean flag = salesStatementService.deleteInvoiceBillRelateCollectionOrder(billid, billtype);
		addJSONObject("flag", flag);
		addLog("销售发票取消关联收款单,单据编号:"+billid, flag);
		return SUCCESS;
	}
	
	/*--------------------普通版特有结束------------------*/
}

