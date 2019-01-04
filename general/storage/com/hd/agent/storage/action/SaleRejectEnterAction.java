/**
 * @(#)SaleRejectEnterAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 15, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.model.SaleRejectEnterDetail;
import com.hd.agent.storage.service.ISaleRejectEnterService;
import com.hd.agent.storage.service.IStorageForSalesService;

/**
 * 
 * 销售退货入库单action
 * @author chenwei
 */
public class SaleRejectEnterAction extends BaseFilesAction {
	
	private SaleRejectEnter saleRejectEnter;
	private ISaleRejectEnterService saleRejectEnterService;
	
	private IOrderService orderService;
	
	private IStorageForSalesService storageForSalesService;
	
	public ISaleRejectEnterService getSaleRejectEnterService() {
		return saleRejectEnterService;
	}

	public void setSaleRejectEnterService(
			ISaleRejectEnterService saleRejectEnterService) {
		this.saleRejectEnterService = saleRejectEnterService;
	}
	
	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}
	
	public SaleRejectEnter getSaleRejectEnter() {
		return saleRejectEnter;
	}

	public void setSaleRejectEnter(SaleRejectEnter saleRejectEnter) {
		this.saleRejectEnter = saleRejectEnter;
	}
	
	public IStorageForSalesService getStorageForSalesService() {
		return storageForSalesService;
	}

	public void setStorageForSalesService(
			IStorageForSalesService storageForSalesService) {
		this.storageForSalesService = storageForSalesService;
	}

	/**
	 * 显示销售退货入库单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 15, 2013
	 */
	public String showSaleRejectEnterAddPage() throws Exception{
		request.setAttribute("type", "add");
		

		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHRKDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		
		return "success";
	}
	/**
	 * 显示销售退货入库单新增详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 15, 2013
	 */
	public String saleRejectEnterAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_salereject_enter"));
		request.setAttribute("userName", getSysUser().getName());
		
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return "success";
	}
	/**
	 * 显示销售退货入库单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public String showSaleRejectEnterDetailAddPage() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		request.setAttribute("rejectCategory", getBaseSysCodeService().showSysCodeListByType("rejectCategory"));
		return "success";
	}
	/**
	 * 销售退货入库单数量金额计算
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public String computeSaleRejectEnterDetailNum() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
		String unitnumStr = request.getParameter("unitnum");
		String taxpriceStr = request.getParameter("taxprice");
		String taxtypeid = request.getParameter("taxtype");
		String customerid = request.getParameter("customerid");
		
		BigDecimal unitnum = null;
		if(null==unitnumStr || "".equals(unitnumStr)){
			unitnum = new BigDecimal(0);
		}else{
			unitnum = new BigDecimal(unitnumStr);
		}
		BigDecimal taxprice = null;
		if(null==taxpriceStr || "".equals(taxpriceStr)){
			OrderDetail orderDetail = orderService.getFixGoodsDetail(goodsid, customerid);
			taxprice = orderDetail.getTaxprice();
		}else{
			taxprice = new BigDecimal(taxpriceStr);
		}
		
		Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(taxprice, taxtypeid,unitnum);
		//无税单价
		BigDecimal notaxprice = (BigDecimal) taxinfoMap.get("notaxprice");
		//税种名称
		String taxtypename = (String) taxinfoMap.get("taxtypename");
		//含税金额
		BigDecimal taxamount = (BigDecimal) taxinfoMap.get("taxamount");
		//无税金额
		BigDecimal notaxamount = (BigDecimal) taxinfoMap.get("notaxamount");
		//税额
		BigDecimal tax = taxamount.subtract(notaxamount);
		
		Map auxnumMap = countGoodsInfoNumber(goodsid, auxunitid, unitnum);
		BigDecimal auxnum = (BigDecimal) auxnumMap.get("auxnum");
		String auxnumdetail = (String) auxnumMap.get("auxnumdetail");
		String auxunitname = (String) auxnumMap.get("auxunitname");
		String unitname = (String) auxnumMap.get("unitname");
		Map returnMap = new HashMap();
		returnMap.put("taxprice", taxprice);
		returnMap.put("notaxprice", notaxprice);
		returnMap.put("taxtypename", taxtypename);
		returnMap.put("taxamount", taxamount);
		returnMap.put("notaxamount", notaxamount);
		returnMap.put("tax", tax);
		returnMap.put("auxnum", auxnum);
		returnMap.put("auxnumdetail", auxnumdetail);
		returnMap.put("auxunitname", auxunitname);
		returnMap.put("unitname", unitname);
		addJSONObject(returnMap);
		return "success";
	}
	/**
	 * 显示销售退货入库单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public String showSaleRejectEnterDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_storage_salereject_enter_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		request.setAttribute("rejectCategory", getBaseSysCodeService().showSysCodeListByType("rejectCategory"));
		return "success";
	}
	/**
	 * 销售退货入库单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public String addSaleRejectEnterSave() throws Exception{
		saleRejectEnter.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new SaleRejectEnterDetail());
		boolean flag = saleRejectEnterService.addSaleRejectEnter(saleRejectEnter, detailList);
		Map map = new HashMap();
		map.put("id", saleRejectEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 销售退货入库单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public String addSaleRejectEnterHold() throws Exception{
		saleRejectEnter.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new SaleRejectEnterDetail());
		boolean flag = saleRejectEnterService.addSaleRejectEnter(saleRejectEnter, detailList);
		Map map = new HashMap();
		map.put("id", saleRejectEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示销售退货入库单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public String showSaleRejectEnterViewPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);		

		String printlimit=getPrintLimitInfo();
		printlimit=(!"1".equals(printlimit)?"0":printlimit);		
		request.setAttribute("printlimit", printlimit);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		

		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHRKDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		
		return "success";
	}
	/**
	 * 显示销售退货入库单详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public String saleRejectEnterViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = saleRejectEnterService.getSaleRejectEnter(id);
		SaleRejectEnter saleRejectEnter = (SaleRejectEnter) map.get("saleRejectEnter");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("saleRejectEnter", saleRejectEnter);
		request.setAttribute("detailList", jsonStr);
		
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		List userList = getPersListByOperationTypeAndDeptid("1", saleRejectEnter.getSalesdept());
		request.setAttribute("userList", userList);
		if("1".equals(saleRejectEnter.getStatus()) || "2".equals(saleRejectEnter.getStatus()) || "3".equals(saleRejectEnter.getStatus()) || "6".equals(saleRejectEnter.getStatus())){
			return "success";
		}else{
			return "viewSuccess";
		}
	}
	/**
	 * 显示销售退货入库单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public String showSaleRejectEnterListPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		
		String printlimit=getPrintLimitInfo();
		printlimit=(!"1".equals(printlimit)?"0":printlimit);		
		request.setAttribute("printlimit", printlimit);		
		request.setAttribute("today", CommonUtils.getMonthDateStr());
		

		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHRKDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		
		return "success";
	}
	/**
	 * 获取销售退货入库单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public String showSaleRejectEnterList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String printsign=(String)map.get("printsign");
		if(null!=printsign && !"".equals(printsign.trim())){
			String printtimes=(String)map.get("queryprinttimes");
			if(!(StringUtils.isNotEmpty(printtimes) && StringUtils.isNumeric(printtimes))){
				map.remove("printsign");
				map.remove("queryprinttimes");
			}
		}
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = saleRejectEnterService.showSaleRejectEnterList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 销售退货入库单删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	@UserOperateLog(key="SaleReject",type=4)
	public String deleteSaleRejectEnter() throws Exception{
		String id = request.getParameter("id");
		boolean flag = saleRejectEnterService.deleteSaleRejectEnter(id);
		addJSONObject("flag", flag);
		addLog("销售退货入库单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 显示销售退货入库单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public String showSaleRejectEnterEditPage() throws Exception{
		boolean flag=true;
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		String printlimit=getPrintLimitInfo();
		printlimit=(!"1".equals(printlimit)?"0":printlimit);		
		request.setAttribute("printlimit", printlimit);
		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHRKDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		SaleRejectEnter saleRejectEnter=saleRejectEnterService.getSaleRejectEnterPureInfo(id);
		if(null==saleRejectEnter){
			flag=false;
		}
		request.setAttribute("flag", flag);
		return "success";
	}
	/**
	 * 销售退货入库单修改详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public String saleRejectEnterEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = saleRejectEnterService.getSaleRejectEnter(id);
		SaleRejectEnter saleRejectEnter = (SaleRejectEnter) map.get("saleRejectEnter");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("saleRejectEnter", saleRejectEnter);
		request.setAttribute("detailList", jsonStr);
		
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		List userList = getPersListByOperationTypeAndDeptid("1", saleRejectEnter.getSalesdept());
		request.setAttribute("userList", userList);
		if("1".equals(saleRejectEnter.getStatus()) || "2".equals(saleRejectEnter.getStatus()) || "6".equals(saleRejectEnter.getStatus())){
			return "success";
		}else{
			return "viewSuccess";
		}
	}
	/**
	 * 审核销售退货入库单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	@UserOperateLog(key="SaleReject",type=3)
	public String auditSaleRejectEnter() throws Exception{
		String id = request.getParameter("id");
		Map map = saleRejectEnterService.auditSaleRejectEnter(id);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		addLog("销售退货入库审核 编号："+id, flag);
		return "success";
	}
	/**
	 * 反审销售退货入库单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 27, 2013
	 */
	@UserOperateLog(key="SaleReject",type=3)
	public String oppauditSaleRejectEnter() throws Exception{
		String id = request.getParameter("id");
		Map map = saleRejectEnterService.oppauditSaleRejectEnter(id);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		addLog("销售退货入库反审 编号："+id, flag);
		return "success";
	}
	/**
	 * 销售退货入库单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	@UserOperateLog(key="SaleReject",type=3)
	public String editSaleRejectEnterHold() throws Exception{
		saleRejectEnter.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new SaleRejectEnterDetail());
		boolean flag = saleRejectEnterService.editSaleRejectEnter(saleRejectEnter, detailList);
		Map map = new HashMap();
		map.put("id", saleRejectEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		addLog("销售退货入库修改暂存 编号："+saleRejectEnter.getId(), flag);
		return "success";
	}
	/**
	 * 销售退货入库单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	@UserOperateLog(key="SaleReject",type=3)
	public String editSaleRejectEnterSave() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new SaleRejectEnterDetail());
		boolean flag = saleRejectEnterService.editSaleRejectEnter(saleRejectEnter, detailList);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		//判断是否保存并审核
		if(flag && "saveaudit".equals(saveaudit)){
			Map returnmap = saleRejectEnterService.auditSaleRejectEnter(saleRejectEnter.getId());
			map.put("auditflag", returnmap.get("flag"));
			map.put("msg", returnmap.get("msg"));
			addLog("销售退货入库保存并且审核 编号："+saleRejectEnter.getId(), flag);
		}else{
			addLog("销售退货入库修改 编号："+saleRejectEnter.getId(), flag);
		}
		map.put("id", saleRejectEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示销售退货入库单参照上游单据页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public String showSaleRejectOrderRelationUpperPage() throws Exception{
		
		return "success";
	}
	/**
	 * 显示销售退货入库单上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public String showSaleRejectEnterSourceListPage() throws Exception{
		String sourcetype = request.getParameter("sourcetype");
		request.setAttribute("sourcetype", sourcetype);
		return "success";
	}
	/**
	 * 获取销售退货通知单明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public String showRejectBillDetailList() throws Exception{
		String ids = request.getParameter("id");
		if(null!=ids){
			String[] idArr =ids.split(",");
			List list = new ArrayList();
			for(String id : idArr){
				RejectBill rejectBill = saleRejectEnterService.showRejectBillDetailList(id);
				if(null!=rejectBill){
					list.addAll(rejectBill.getBillDetailList());
				}
			}
			addJSONArray(list);
		}
		return "success";
	}
	/**
	 * 获取销售发货回单明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public String showReceiptDetailList() throws Exception{
		String ids = request.getParameter("id");
		if(null!=ids){
			String[] idArr =ids.split(",");
			List list = new ArrayList();
			for(String id : idArr){
				Receipt receipt = saleRejectEnterService.showReceiptDetailList(id);
				if(null!=receipt){
					list.addAll(receipt.getReceiptDetailList());
				}
			}
			addJSONArray(list);
		}
		return "success";
	}
	/**
	 * 根据上游单据生成销售退货入库单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	@UserOperateLog(key="SaleReject",type=2)
	public String addSaleRejectEnterByRefer() throws Exception{
		String sourcetype = request.getParameter("sourcetype");
		String id = request.getParameter("id");
		boolean flag = false;
		String salerejectid = "";
		//上游单据为销售退货通知单
		RejectBill rejectBill = saleRejectEnterService.showRejectBillDetailList(id);
		if(null!=rejectBill){
			salerejectid = storageForSalesService.addSaleRejectEnterByRejectBill(rejectBill, rejectBill.getBillDetailList());
			if(null!=salerejectid && !"".equals(salerejectid)){
				flag = true;
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", salerejectid);
		addJSONObject(map);
		addLog("销售退货入库参照上游单据生成 编号："+salerejectid, flag);
		return "success";
	}
	/**
	 * 销售退货入库单工作流提交
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitSaleRejectEnterProcess() throws Exception{
		String id = request.getParameter("id");
		Map map = saleRejectEnterService.submitSaleRejectEnterProcess(id);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 销售退货入库单保存验收
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 15, 2013
	 */
	public String saleRejectEnterSaveCheck() throws Exception{
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = false;
		if("savecheck".equals(saveaudit)){
			String detailJson = request.getParameter("detailJson");
			List detailList = JSONUtils.jsonStrToList(detailJson, new SaleRejectEnterDetail());
			flag = saleRejectEnterService.saveCheckSaleRejectEnter(saleRejectEnter, detailList);
		}
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 批量修改收货人
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2016-12-21
	 */
	@UserOperateLog(key="SaleReject",type=3)
	public String editSalesRejectEnterStorager() throws Exception{
		String ids = request.getParameter("ids");
		String storager = request.getParameter("storager");
		int failure = 0;
		int success = 0;
		boolean sFlag = true;
		String msg = "";
		String failstr = "";
		String successstr = "";
		if(StringUtils.isNotEmpty(ids)){
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			String[] idArr = ids.split(",");
			for(String id : idArr){
				Map map = saleRejectEnterService.editSalesRejectEnterStorager( id,storager);
				boolean flag = false;
				if(map.containsKey("flag")){
					flag = (Boolean)map.get("flag");
				}
				if(flag){
					success++;
					successstr += id+",";
				}
				else{
					failure++;
					failstr += id+",";
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", sFlag);
		map.put("failure", failure);
		map.put("success", success);
		map.put("msg", msg);
		addJSONObject(map);
		addLog("销售退货入库单批量修改发货人 编号"+ids+"，成功条数:"+success+",成功编号:"+successstr+",失败条数"+failure+",失败编号:"+failstr, sFlag);

		return "success";
	}
}

