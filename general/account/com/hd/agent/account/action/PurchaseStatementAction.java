/**
 * @(#)PurchaseStatementAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.PurchaseInvoice;
import com.hd.agent.account.model.PurchaseStatement;
import com.hd.agent.account.model.SupplierCapital;
import com.hd.agent.account.service.IPayorderService;
import com.hd.agent.account.service.IPurchaseInvoiceService;
import com.hd.agent.account.service.IPurchaseStatementService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.service.IBuyService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 采购发票核销 对账单action
 * @author panxiaoxiao
 */
public class PurchaseStatementAction extends BaseFilesAction{

	private List<PurchaseStatement> purchaseStatementList;
	
	/**
	 * 付款单对账单service
	 */
	private IPurchaseStatementService purchaseStatementService;
	
	/**
	 * 付款单service
	 */
	private IPayorderService payorderService;
	
	/**
	 * 采购模块service
	 */
	private IBuyService buyService;
	
	/**
	 * 采购发票service
	 */
	private IPurchaseInvoiceService purchaseInvoiceService;

	public List<PurchaseStatement> getPurchaseStatementList() {
		return purchaseStatementList;
	}

	public void setPurchaseStatementList(
			List<PurchaseStatement> purchaseStatementList) {
		this.purchaseStatementList = purchaseStatementList;
	}

	public IPurchaseStatementService getPurchaseStatementService() {
		return purchaseStatementService;
	}

	public void setPurchaseStatementService(
			IPurchaseStatementService purchaseStatementService) {
		this.purchaseStatementService = purchaseStatementService;
	}

	public IPayorderService getPayorderService() {
		return payorderService;
	}

	public void setPayorderService(IPayorderService payorderService) {
		this.payorderService = payorderService;
	}

	public IPurchaseInvoiceService getPurchaseInvoiceService() {
		return purchaseInvoiceService;
	}

	public void setPurchaseInvoiceService(
			IPurchaseInvoiceService purchaseInvoiceService) {
		this.purchaseInvoiceService = purchaseInvoiceService;
	}
	
	public IBuyService getBuyService() {
		return buyService;
	}

	public void setBuyService(IBuyService buyService) {
		this.buyService = buyService;
	}

	/**
	 * 显示付款单核销页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public String showWriteoffPayorderPage() throws Exception{
		String id = request.getParameter("id");
		String invoiceid = request.getParameter("invoiceid");
		SupplierCapital supplierCapital = payorderService.getSupplierCapitalBySupplierid(id);
		request.setAttribute("supplierCapital", supplierCapital);
		request.setAttribute("invoiceid", invoiceid);
		return "success";
	}
	
	/**
	 * 根据供应商编码获取该供应商下的采购发票
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public String showPurchaseInvoiceListBySupplier()throws Exception{
		String id = request.getParameter("id");
		List list = purchaseInvoiceService.showPurchaseInvoiceListBySupplier(id);
		addJSONArray(list);
		return "success";
	}
	
	/**
	 * 显示核销确认页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public String showWriteoffPage() throws Exception{
		String supplierid = request.getParameter("supplierid");
		String invoiceid = request.getParameter("invoiceid");//采购发票id
		SupplierCapital supplierCapital = payorderService.getSupplierCapitalBySupplierid(supplierid);
		if(null!=supplierCapital){
			List<PurchaseInvoice> purchaseInvoiceList = purchaseInvoiceService.getPurchaseInvoiceListByInvoiceids(invoiceid);
			BigDecimal invoiceAmount = new BigDecimal(0);
			if(purchaseInvoiceList.size() != 0){
				for(PurchaseInvoice purchaseInvoice : purchaseInvoiceList){
					invoiceAmount = invoiceAmount.add(purchaseInvoice.getInvoiceamount());
				}
			}
			request.setAttribute("supplierCapital", supplierCapital);
			request.setAttribute("purchaseInvoiceList", purchaseInvoiceList);
			request.setAttribute("invoiceAmount", invoiceAmount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			request.setAttribute("invoiceid", invoiceid);
			return "success";
		}else{
			request.setAttribute("writeoffPage", true);
			return "error";
		}
	}
	
	/**
	 * 付款单核销审核
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	@UserOperateLog(key="PurchaseInvoice",type=4)
	public String auditWriteoffPayorder()throws Exception{
		String supplierid = request.getParameter("supplierid");
		Map map = purchaseStatementService.auditWriteoffPayorder(supplierid, purchaseStatementList);
		addJSONObject(map);
		String billids = "";
		for(PurchaseStatement purchaseStatement : purchaseStatementList){
			billids += purchaseStatement.getBillid()+",";
		}
		addLog("采购发票核销,供应商编号:"+supplierid+",采购发票单据号："+billids,map);
		return "success";
	}
	
	/**
	 * 显示对账单列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public String showPurchaseStatementListPage()throws Exception{
		String supplierid = request.getParameter("supplierid");
		request.setAttribute("supplierid", supplierid);
		return SUCCESS;
	}
	
	/**
	 * 根据付款单编码获取对账单明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public String showPurchaseStatementList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = purchaseStatementService.showPurchaseStatementData(pageMap);
		addJSONObject(pageData);
		return "success";
	}

    /**
     * 采购发票反核销
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    @UserOperateLog(key="PurchaseInvoice",type=0)
    public String uncancelPurchaseInvoice()throws Exception{
        String invoiceid = request.getParameter("invoiceid");
        boolean flag = purchaseStatementService.uncancelPurchaseInvoice(invoiceid);
        addLog("采购发票:"+invoiceid+"反核销", flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }
}

