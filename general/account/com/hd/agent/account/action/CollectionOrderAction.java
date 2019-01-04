/**
 * @(#)CollectionOrderAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 6, 2013 chenwei 创建版本
 */
package com.hd.agent.account.action;

import com.hd.agent.account.model.CollectionOrder;
import com.hd.agent.account.model.CollectionOrderSatement;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.account.model.TransferOrder;
import com.hd.agent.account.service.ICollectionOrderService;
import com.hd.agent.account.service.ISalesInvoiceService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.service.IReceiptService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 收款单action
 * @author chenwei
 */
public class CollectionOrderAction extends BaseFilesAction {

	private CollectionOrder collectionOrder;

    private IReceiptService salesReceiptService;

    private ISalesInvoiceService salesInvoiceService;
	/**
	 * 收款单转账
	 */
	private TransferOrder transferOrder;
	
	private ICollectionOrderService collectionOrderService;

    public IReceiptService getSalesReceiptService() {
        return salesReceiptService;
    }

    public void setSalesReceiptService(IReceiptService salesReceiptService) {
        this.salesReceiptService = salesReceiptService;
    }

    public ICollectionOrderService getCollectionOrderService() {
		return collectionOrderService;
	}

	public void setCollectionOrderService(
			ICollectionOrderService collectionOrderService) {
		this.collectionOrderService = collectionOrderService;
	}
	
	public CollectionOrder getCollectionOrder() {
		return collectionOrder;
	}

	public void setCollectionOrder(CollectionOrder collectionOrder) {
		this.collectionOrder = collectionOrder;
	}
	
	public TransferOrder getTransferOrder() {
		return transferOrder;
	}

	public void setTransferOrder(TransferOrder transferOrder) {
		this.transferOrder = transferOrder;
	}

	public ISalesInvoiceService getSalesInvoiceService() {
		return salesInvoiceService;
	}

	public void setSalesInvoiceService(ISalesInvoiceService salesInvoiceService) {
		this.salesInvoiceService = salesInvoiceService;
	}

	/**
	 * 显示收款单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public String showCollectionOrderAddPage() throws Exception{
		return "success";
	}
	/**
	 * 显示收款单新增详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public String collectionOrderAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_account_collection_order"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}
	
	/**
	 * 显示收款单新增保存并审核详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 13, 2015
	 */
	public String showCollectionOrderAddAuditPage()throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_account_collection_order"));
		request.setAttribute("userName", getSysUser().getName());
		request.setAttribute("customerid", customerid);
		request.setAttribute("customername", customername);
		return SUCCESS;
	}
	
	/**
	 * 收款单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=2)
	public String addCollectionOrderHold() throws Exception{
		collectionOrder.setStatus("1");
		boolean flag = collectionOrderService.addCollectionOrder(collectionOrder);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", collectionOrder.getId());
		addJSONObject(map);
		addLog("收款单新增暂存 编号："+collectionOrder.getId(), flag);
		return "success";
	}
	/**
	 * 收款单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=2)
	public String addCollectionOrderSave() throws Exception{
		collectionOrder.setStatus("2");
		boolean flag = collectionOrderService.addCollectionOrder(collectionOrder);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", collectionOrder.getId());
		addJSONObject(map);
		addLog("收款单新增 编号："+collectionOrder.getId(), flag);
		return "success";
	}
	
	/**
	 * 收款单新增保存并审核
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 13, 2015
	 */
	public String addCollectionOrderSaveAudit()throws Exception{
		collectionOrder.setStatus("2");
		Map map = collectionOrderService.addCollectionOrderSaveAudit(collectionOrder);
		map.put("id", collectionOrder.getId());
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示收款单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public String showCollectionOrderViewPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 显示收款单查看详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public String collectionOrderViewPage() throws Exception{
		String id = request.getParameter("id");
		CollectionOrder collectionOrder = collectionOrderService.getCollectionOrderInfo(id);
		request.setAttribute("collectionOrder", collectionOrder);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		return "success";
	}
	/**
	 * 显示收款单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public String showCollectionOrderListPage() throws Exception{
		String custoemrid = getSysParamValue("collectionOrderOtherCustomer");
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		request.setAttribute("othercustomer", custoemrid);
		return "success";
	}
	/**
	 * 获取收款单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public String showCollectionOrderList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String bank = (String) map.get("bank");
		if("cash".equals(bank)){
			map.put("bank", "");
		}
		pageMap.setCondition(map);
		PageData pageData = collectionOrderService.showCollectionOrderList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

	/**
	 * 删除收款单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=4)
	public String deleteCollectionOrder() throws Exception{
		String id = request.getParameter("id");
		boolean flag = collectionOrderService.deleteCollectionOrder(id);
		addJSONObject("flag", flag);
		addLog("收款单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 批量删除收款单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 4, 2014
	 */
	@UserOperateLog(key="CollectionOrder",type=4)
	public String deleteMutCollectionOrder() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = collectionOrderService.deleteCollectionOrder(id);
				if(flag){
					succssids += id+",";
				}else{
					errorids += id+",";
				}
				
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "删除成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "删除失败编号:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("收款单批量删除 成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 显示收款单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public String showCollectionOrderEditPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		return "success";
	}
	/**
	 * 显示收款单修改详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public String collectionOrderEditPage() throws Exception{
		String id = request.getParameter("id");
		CollectionOrder collectionOrder = collectionOrderService.getCollectionOrderInfo(id);
		request.setAttribute("collectionOrder", collectionOrder);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		if(null!=collectionOrder){
			if("".equals(collectionOrder.getCustomerid())){
				return "success";
			}else if(!"1".equals(collectionOrder.getStatus()) && !"2".equals(collectionOrder.getStatus()) && !"6".equals(collectionOrder.getStatus())){
				return "viewSuccess";
			}else{
				return "success";
			}
		}else{
			return "addSuccess";
		}
		
	}
	/**
	 * 收款单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String editCollectionOrderHold() throws Exception{
		boolean flag = collectionOrderService.editCollectionOrder(collectionOrder);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", collectionOrder.getId());
		addJSONObject(map);
		addLog("收款单修改暂存 编号："+collectionOrder.getId(), flag);
		return "success";
	}
	/**
	 * 收款单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String editCollectionOrderSave() throws Exception{
		boolean flag = collectionOrderService.editCollectionOrder(collectionOrder);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", collectionOrder.getId());
		addJSONObject(map);
		addLog("收款单修改 编号："+collectionOrder.getId(), flag);
		return "success";
	}
	/**
	 * 收款单审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String auditCollectionOrder() throws Exception{
		String id = request.getParameter("id");
		Map map = collectionOrderService.auditCollectionOrder(id,false);
		addJSONObject(map);
		addLog("收款单审核 编号："+id, map);
		return "success";
	}
	/**
	 * 收款单批量审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 4, 2014
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String auditMutCollectionOrder() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				Map map = collectionOrderService.auditCollectionOrder(id,false);
				boolean flag = (Boolean) map.get("flag");
				String msg = (String) map.get("msg");
				if(flag){
					succssids += id+",";
				}else{
					errorids += msg+".";
				}
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "审核成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "审核失败:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("收款单批量审核 成功编号："+succssids+";审核失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 超级审核收款单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 2, 2014
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String auditSuperMutCollectionOrder() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				Map map = collectionOrderService.auditCollectionOrder(id,true);
				boolean flag = (Boolean) map.get("flag");
				String msg = (String) map.get("msg");
				if(flag){
					succssids += id+",";
				}else{
					errorids += msg+".";
				}
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "审核成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "审核失败:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("收款单批量超级审核 成功编号："+succssids+";审核失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 收款单反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String oppauditCollectionOrder() throws Exception{
		String id = request.getParameter("id");
		Map map = collectionOrderService.oppauditCollectionOrder(id);
		addJSONObject(map);
		addLog("收款单反审 编号："+id, map);
		return "success";
	}
	/**
	 * 收款单批量反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 4, 2014
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String oppauditMutCollectionOrder() throws Exception{
		String ids = request.getParameter("ids");
		boolean retflag = true;
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				Map map = collectionOrderService.oppauditCollectionOrder(id);
				boolean flag = (Boolean) map.get("flag");
				retflag = retflag && flag;
				String msg = (String) map.get("msg");
				if(flag){
					succssids += id+",";
				}else{
					errorids += msg+",";
				}
			}
			Map map = new HashMap();
			map.put("flag", retflag);
			if(!"".equals(succssids)){
				map.put("succssids", "反审成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "反审失败:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("收款单批量反审 成功编号："+succssids+";反审失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 显示客户资金情况列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public String showCustomerAccountListPage() throws Exception{
		return "success";
	}
	/**
	 * 获取客户资金情况列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public String showCustomerAccountList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = collectionOrderService.showCustomerAccountList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出客户资金余额表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 30, 2014
	 */
	public void exportCustomerAccountData()throws Exception{
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
		firstMap.put("id", "编号");
		firstMap.put("name", "客户名称");
		firstMap.put("salesareaname", "所属区域");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("amount", "余额");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		PageData pageData = collectionOrderService.showCustomerAccountList(pageMap);
		List<Customer> list = new ArrayList<Customer>();
		
		list.addAll(pageData.getList());
		list.addAll(pageData.getFooter());
		
		for(Customer customer : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(customer);
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
	 * 显示客户资金流水页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public String showCustomerCapitalLogListPage() throws Exception{
		String id = request.getParameter("id");
		if("".equals(id)){
			id = "000000";
		}
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 获取客户资金流水列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public String showCustomerCapitalLogList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String customerid = (String) map.get("customerid");
		//000000表示未指定客户的数据 
		if("000000".equals(customerid)){
			map.put("customerid", "");
		}
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = collectionOrderService.showCustomerCapitalLogList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示收款单合并页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	public String showCollectionOrderMergePage() throws Exception{
		return "success";
	}
	/**
	 * 显示收款单合并确认页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	public String showCollectionOrderMergeSubmitPage() throws Exception{
		String ids = request.getParameter("ids");
		List<CollectionOrder> list = collectionOrderService.getCollectionOrderListByIds(ids);
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal totalWriteoffamount = new BigDecimal(0);
		BigDecimal totalRemainderamount = new BigDecimal(0);
		for(CollectionOrder collectionOrder : list){
			collectionOrder.setAmount(collectionOrder.getAmount().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			collectionOrder.setWriteoffamount(collectionOrder.getWriteoffamount().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			collectionOrder.setRemainderamount(collectionOrder.getRemainderamount().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			totalAmount = totalAmount.add(collectionOrder.getAmount());
			totalWriteoffamount = totalWriteoffamount.add(collectionOrder.getWriteoffamount());
			totalRemainderamount = totalRemainderamount.add(collectionOrder.getRemainderamount());
		}
		request.setAttribute("list", list);
		request.setAttribute("totalAmount", totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
		request.setAttribute("totalWriteoffamount", totalWriteoffamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		request.setAttribute("totalRemainderamount", totalRemainderamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		request.setAttribute("ids", ids);
		return "success";
	}
	/**
	 * 收款单合并
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String setCollectionOrderMerge() throws Exception{
		String orderid = request.getParameter("orderid");
		String ids = request.getParameter("ids");
		String remark = request.getParameter("remark");
		boolean flag = collectionOrderService.setCollectionOrderMerge(ids, orderid, remark);
		addJSONObject("flag", flag);
		addLog("收款单:"+ids+",合并到"+orderid,flag);
		return "success";
	}
	/**
	 * 显示收款单转账页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	public String showCollectionOrderTransferPage() throws Exception{
		return "success";
	}
	/**
	 * 显示转账确认页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	public String showCollectionOrderTransferSubmitPage() throws Exception{
		String customerid = request.getParameter("customerid");
		CustomerCapital customerCapital = collectionOrderService.getCustomerCapitalByCustomerid(customerid);
		request.setAttribute("customerCapital", customerCapital);
		return "success";
	}
	/**
	 * 收款单转账确认
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String setCollectionOrderTransfer() throws Exception{
		boolean flag = collectionOrderService.setCollectionOrderTransfer(transferOrder);
		addJSONObject("flag", flag);
		addLog("客户余额转账 转出客户:"+transferOrder.getOutcustomerid()+",转出收款单:"+transferOrder.getOutorderid()+",转入客户:"+transferOrder.getIncustomerid()+",转账金额:"+transferOrder.getTransferamount().toString(),flag);
		return "success";
	}
	/**
	 * 收款单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitCollectionOrderPageProcess() throws Exception{
		String id = request.getParameter("id");
		boolean flag  = collectionOrderService.submitCollectionOrderPageProcess(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 未指定客户的收款单 指定客户页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 7, 2013
	 */
	public String collectionOrderAssignCustomerPage() throws Exception{
		String id = request.getParameter("id");
		CollectionOrder collectionOrder = collectionOrderService.getCollectionOrderInfo(id);
		request.setAttribute("collectionOrder", collectionOrder);
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 给收款单指定客户
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 7, 2013
	 */
	@UserOperateLog(key="CollectionOrder",type=0)
	public String assignCustomer() throws Exception{
		String id = request.getParameter("id");
		String detailList = request.getParameter("detailList");
		String customerid = request.getParameter("customerid");
		String amountStr = request.getParameter("amount");
		BigDecimal amount = BigDecimal.ZERO;
		if(null!=amountStr ){
			amount = new BigDecimal(amountStr);
		}
		Map map = collectionOrderService.updateCollectionOrderAssignCustomer(id, detailList);
		addJSONObject(map);
		String customerids = "";
		if(null!=map && map.containsKey("customerids")){
			customerids = (String) map.get("customerids");
		}
		addLog("未指定客户的收款单指定客户 编号："+id+",客户编号:"+customerids, map);
		return "success";
	}
	/**
	 * 获取客户资金情况
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 9, 2013
	 */
	public String getCustomerCapital() throws Exception{
		String customerid = request.getParameter("id");
		String pcustomerid = request.getParameter("pcustomerid");
		CustomerCapital customerCapital = collectionOrderService.getCustomerCapitalByCustomerid(customerid);
		CustomerCapital pcustomerCapital = collectionOrderService.getCustomerCapitalByCustomerid(pcustomerid);
		Map map = new HashMap();
		if(null!=pcustomerCapital){
			map.put("amount", pcustomerCapital.getAmount());
		}else{
			map.put("amount", BigDecimal.ZERO);
		}
		if(null!=customerCapital){
			map.put("camount", customerCapital.getAmount());
		}else{
			map.put("camount", BigDecimal.ZERO);
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示收款单关联单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public String showCollectionStatementDetailListPage() throws Exception{
		String id = request.getParameter("id");
		List list = collectionOrderService.showCollectionStatementDetailList(id);
		List sourceList = new ArrayList();
		for(Object obj : list){
			CollectionOrderSatement collectionOrderSatement = (CollectionOrderSatement) obj;
			if("1".equals(collectionOrderSatement.getBilltype())){
				List indexlist = salesInvoiceService.showSalesInvoiceSourceListReferData(collectionOrderSatement.getBillid(), "");
				sourceList.addAll(indexlist);
			}
		}
		String jsonStr = JSONUtils.listToJsonStr(list);
		String sourceListStr = JSONUtils.listToJsonStr(sourceList);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("sourceListStr", sourceListStr);
		return "success";
	}
	
	/**
	 * 显示关联的收款单信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 11, 2014
	 */
	public String showBillRelateCollectionListPage()throws Exception{
		String billid = request.getParameter("billid");
		String billtype = request.getParameter("billtype");
		List list = collectionOrderService.showBillRelateCollectionList(billid,billtype);
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("detailList", jsonStr);
		return SUCCESS;
	}
	
	/**
	 * 显示客户银行对应收款单列表数据页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 29, 2014
	 */
	public String showBankWriteReportCollectionListPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		List list = collectionOrderService.getBankWriteReportCollectionList(map);
		String jsonList = JSONUtils.listToJsonStr(list);
		request.setAttribute("detailList", jsonList);
		return SUCCESS;
	}
	
	/**
	 * 显示其他收款单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 26, 2014
	 */
	public String collectionOrderOtherListPage() throws Exception{
		String custoemrid = getSysParamValue("collectionOrderOtherCustomer");
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		request.setAttribute("othercustomer", custoemrid);
		return "success";
	}
	/**
	 * 显示其他收款单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 26, 2014
	 */
	public String collectionOrderOtherAddPage() throws Exception{
		String custoemrid = getSysParamValue("collectionOrderOtherCustomer");
		Customer customer = getCustomerById(custoemrid);
		if(null!=customer){
			request.setAttribute("customer", customer);
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_account_collection_order"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}

    /**
     * 收款单导出
     * @throws Exception
     * @author lin_xx
     * @date JAN 11, 2016
     */
    @UserOperateLog(key="CollectionOrder",type=2)
    public String importCollectionOrder() throws Exception{

        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
        List<String> paramList2 = new ArrayList<String>();
        for(String str : paramList){
            if("银行名称".equals(str)){
                paramList2.add("bankname");
            }else if("收款金额".equals(str)){
                paramList2.add("amount");
            }else if("客户编号".equals(str)){
                paramList2.add("customerid");
            }else if("编号".equals(str)){
                paramList2.add("id");
            }else if("业务日期".equals(str)){
                paramList2.add("businessdate");
            }else if("客户名称".equals(str)){
                paramList2.add("customername");
            }else if("收款人".equals(str)){
                paramList2.add("collectionuser");
            }else if("已核销金额".equals(str)){
                paramList2.add("writeoffamount");
            }else if("未核销金额".equals(str)){
                paramList2.add("remainderamount");
            }else if("银行部门".equals(str)){
                paramList2.add("bankdeptname");
            }else if("制单人".equals(str)){
                paramList2.add("addusername");
            }else if("制单时间".equals(str)){
                paramList2.add("addtime");
            }else if("审核人".equals(str)){
                paramList2.add("auditusername");
            }else if("审核时间".equals(str)){
                paramList2.add("audittime");
            }else if("状态".equals(str)){
                paramList2.add("status");
            }else if("备注".equals(str)){
                paramList2.add("remark");
            }else{
				paramList2.add(null);
			}

        }

        Map map = new HashMap();
        int failur = 0 ;
        int success = 0;
        String msg = "";
        List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
        if(list.size() != 0){
            for(int i = 0 ; i < list.size() ; i++){
                Map m = (Map) list.get(i);
                String businessdate = (String) m.get("businessdate");
                if("合计".equals(businessdate)){
                    ++success ;
                    continue;
                }else{
                    CollectionOrder collectionOrder = new CollectionOrder();
                    String customerid = (String) m.get("customerid") ;
                    Customer customer = getCustomerById(customerid);
                    Personnel personnel = getPersonnelInfoById(customer.getPayeeid());
                    if(null != personnel){
						collectionOrder.setCollectionusername(personnel.getName());
					}
                    collectionOrder.setCollectionuser(customer.getPayeeid());
                    collectionOrder.setCustomerid(customerid);

                    String bankname = (String)m.get("bankname");
                    collectionOrder.setBankname(bankname);
                    Bank bank = baseFinanceService.getBankInfoByName(bankname);
                    if(null != bank){
						collectionOrder.setBank(bank.getId());
						collectionOrder.setBankdeptid(bank.getBankdeptid());
					}
					String bankdeptname = (String)m.get("bankdeptname");
					if(StringUtils.isNotEmpty(bankdeptname)){
						DepartMent departMent = getBaseDepartMentService().getDepartMentInfoByNameLimitOne(bankdeptname);
						if(null != departMent){
							collectionOrder.setBankdeptid(departMent.getId());
						}
					}

                    String amount = (String) m.get("amount");
                    collectionOrder.setAmount( new BigDecimal(amount));
                    collectionOrder.setStatus("2");
                    collectionOrder.setBusinessdate(CommonUtils.dataToStr(new Date(),""));
                    boolean flag = collectionOrderService.addCollectionOrder(collectionOrder);
                    if(flag){
                        ++ success;
                    }else{
                        ++ failur;
                    }
                }
            }
            if(failur == list.size()){
                map.put("excelempty", true);
            }else if(success != list.size()){
                msg = "收款单 导入成功"+success+"条，导入失败"+failur+"条";
                map.put("msg", msg);
            }else if(success == list.size()){
                map.put("msg", "导入成功!");
            }
        } else {
            map.put("excelempty", true);
        }
        addJSONObject(map);
        addLog(msg);

        return SUCCESS;
    }
	/**
	 * 收款单导出
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public void exportCollectionOrderList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
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
		PageData pageData = collectionOrderService.showCollectionOrderList(pageMap);
		ExcelUtils.exportExcel(exportCollectionOrderListFilter(pageData.getList(),pageData.getFooter()), title);
	}
	/**
	 * 导出excel格式定义
	 * @param list
	 * @param footerList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 28, 2014
	 */
	private List<Map<String, Object>> exportCollectionOrderListFilter(List<CollectionOrder> list,List<CollectionOrder> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("id", "编号");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
        firstMap.put("salesdeptname", "销售部门");
		firstMap.put("collectionusername", "收款人");
		firstMap.put("amount", "收款金额");
		firstMap.put("writeoffamount", "已核销金额");
		firstMap.put("remainderamount", "未核销金额");
		firstMap.put("bankname", "银行名称");
		firstMap.put("bankdeptname", "银行部门");
		firstMap.put("addusername", "制单人");
		firstMap.put("addtime", "制单时间");
		firstMap.put("auditusername", "审核人");
		firstMap.put("audittime", "审核时间");
		firstMap.put("statusname", "状态");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		if(null!=list && list.size() != 0){
			for(CollectionOrder collectionOrder : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(collectionOrder);
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
		if(null!=footerList && footerList.size() != 0){
			for(CollectionOrder collectionOrder : footerList){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(collectionOrder);
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
     * 根据回单核销的客户显示对应收款单页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-25
     */
    public String showCollectionOrderListCaseReceiptPage()throws Exception{
        String customerid = request.getParameter("customerid");
		request.setAttribute("customerid",customerid);
        String ids = request.getParameter("ids");
        BigDecimal receiptamount = BigDecimal.ZERO;
        Receipt receiptSum = salesReceiptService.getTotalReceiptAmount(ids);
        if(null != receiptSum){
            receiptamount = receiptSum.getTotaltaxamount();
        }
        request.setAttribute("receiptamount",receiptamount);
        List list = collectionOrderService.getCollectionOrderListByCustomerid(customerid);
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("list", jsonStr);
        request.setAttribute("ids",ids);
        CustomerCapital customerCapital = collectionOrderService.getCustomerCapitalByCustomerid(customerid);
        if(null != customerCapital){
            request.setAttribute("customerCapital",customerCapital);
        }

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
		request.setAttribute("positeTailAmount", afterTailContr);
		request.setAttribute("negateTailAmount", beforeTailContr);

		//核销金额（即回单金额）在尾差范围内直接允许调尾差
		if(receiptamount.negate().compareTo(beforeTailContr) >= 0 && receiptamount.negate().compareTo(afterTailContr) <= 0){
			request.setAttribute("isrelate", "2");
		}
        return SUCCESS;
    }
}

