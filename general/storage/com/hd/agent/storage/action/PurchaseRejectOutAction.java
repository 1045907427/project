/**
 * @(#)PurchaseRejectOutAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 19, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.storage.model.PurchaseRejectOut;
import com.hd.agent.storage.model.PurchaseRejectOutDetail;
import com.hd.agent.storage.service.IPurchaseRejectOutService;
import com.hd.agent.storage.service.IStorageForPurchaseService;

/**
 * 
 * 采购退货出库单action
 * @author chenwei
 */
public class PurchaseRejectOutAction extends BaseFilesAction {
	
	private PurchaseRejectOut purchaseRejectOut;
	
	public IPurchaseRejectOutService purchaseRejectOutService;

	public IStorageForPurchaseService storageForPurchaseService;
	
	public IPurchaseRejectOutService getPurchaseRejectOutService() {
		return purchaseRejectOutService;
	}

	public void setPurchaseRejectOutService(
			IPurchaseRejectOutService purchaseRejectOutService) {
		this.purchaseRejectOutService = purchaseRejectOutService;
	}
	
	public PurchaseRejectOut getPurchaseRejectOut() {
		return purchaseRejectOut;
	}

	public void setPurchaseRejectOut(PurchaseRejectOut purchaseRejectOut) {
		this.purchaseRejectOut = purchaseRejectOut;
	}
	
	public IStorageForPurchaseService getStorageForPurchaseService() {
		return storageForPurchaseService;
	}

	public void setStorageForPurchaseService(
			IStorageForPurchaseService storageForPurchaseService) {
		this.storageForPurchaseService = storageForPurchaseService;
	}

	/**
	 * 显示采购退货出库单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 19, 2013
	 */
	public String showPurchaseRejectOutAddPage() throws Exception{
		request.setAttribute("type", "add");
		Map map = getEditAccessColumn("t_storage_purchasereject_out_detail");
		request.setAttribute("map", map);
		return "success";
	}
	/**
	 * 显示采购退货出库单新增详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 19, 2013
	 */
	public String purchaseRejectOutAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_purchasereject_out"));
		request.setAttribute("userName", getSysUser().getName());
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		//采购部门
		List deptList = getBaseDepartMentService().getDeptListByOperType("3");
		request.setAttribute("deptList", deptList);
		return "success";
	}
	/**
	 * 显示采购退货出库单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public String showPurchaseRejectOutDetailAddPage() throws Exception{
		String storageid = request.getParameter("storageid");
		String supplierid = request.getParameter("supplierid");
		request.setAttribute("storageid", storageid);
		request.setAttribute("supplierid", supplierid);
		Map map = getEditAccessColumn("t_storage_purchasereject_out_detail");
		request.setAttribute("map", map);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 采购退货出库单 数量 金额计算
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public String computePurchaseRejectOutDetailNum() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
		String unitnumStr = request.getParameter("unitnum");
		String taxpriceStr = request.getParameter("taxprice");
		String taxtypeid = request.getParameter("taxtype");
		
		
		BigDecimal unitnum = null;
		if(null==unitnumStr || "".equals(unitnumStr)){
			unitnum = new BigDecimal(0);
		}else{
			unitnum = new BigDecimal(unitnumStr);
		}
		BigDecimal taxprice = null;
		if(null==taxpriceStr || "".equals(taxpriceStr)){
			taxprice = new BigDecimal(0);
		}else{
			taxprice = new BigDecimal(taxpriceStr);
		}
		Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(taxprice, taxtypeid,unitnum);
		
		Map returnMap = new HashMap();
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
		BigDecimal auxrate = (BigDecimal) auxnumMap.get("auxrate");
		BigDecimal auxnum = (BigDecimal) auxnumMap.get("auxnum");
		String auxnumdetail = (String) auxnumMap.get("auxnumdetail");
		String auxunitname = (String) auxnumMap.get("auxunitname");
		String unitname = (String) auxnumMap.get("unitname");
		String auxremainder = (String) auxnumMap.get("auxremainder");
		String auxInteger = (String) auxnumMap.get("auxInteger");
		
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
		returnMap.put("auxremainder", auxremainder);
		returnMap.put("auxInteger", auxInteger);
		if(null!=auxrate){
			returnMap.put("auxrate", auxrate);
		}
		addJSONObject(returnMap);
		return "success";
	}
	/**
	 * 显示采购退货出库单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public String showPurchaseRejectOutDetailEditPage() throws Exception{
		Map map = getEditAccessColumn("t_storage_purchasereject_out_detail");
		request.setAttribute("map", map);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 采购退货出库单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	@UserOperateLog(key="PurchaseRejectOut",type=2)
	public String addPurchaseRejectOutHold() throws Exception{
		purchaseRejectOut.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseRejectOutDetail());
		Map map = purchaseRejectOutService.addPurchaseRejectOut(purchaseRejectOut, detailList);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		addLog("采购退货出库单新增暂存 编号："+purchaseRejectOut.getId(), flag);
		return "success";
	}
	/**
	 * 采购退货出库单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	@UserOperateLog(key="PurchaseRejectOut",type=2)
	public String addPurchaseRejectOutSave() throws Exception{
		purchaseRejectOut.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseRejectOutDetail());
		Map map = purchaseRejectOutService.addPurchaseRejectOut(purchaseRejectOut, detailList);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		addLog("采购退货出库单新增保存 编号："+purchaseRejectOut.getId(), flag);
		return "success";
	}
	/**
	 * 显示采购退货出库单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public String showPurchaseRejectOutViewPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);

		

		String printlimit=getPrintLimitInfo();	
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 显示采购退货出库单查看详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public String purchaseRejectOutViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = purchaseRejectOutService.getPurchaseRejectOut(id);
		PurchaseRejectOut purchaseRejectOut = (PurchaseRejectOut) map.get("purchaseRejectOut");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("purchaseRejectOut", purchaseRejectOut);
		request.setAttribute("detailList", jsonStr);
		return "success";
	}
	/**
	 * 显示采购退货出库单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public String showPurchaseRejectOutListPage() throws Exception{
		//采购部门
		List deptList = getBaseDepartMentService().getDeptListByOperType("3");
		request.setAttribute("deptList", deptList);		
		
		String printlimit=getPrintLimitInfo();
		printlimit=(!"1".equals(printlimit)?"0":printlimit);		
		request.setAttribute("printlimit", printlimit);
		Map map = getEditAccessColumn("t_storage_purchasereject_out_detail");
		request.setAttribute("map", map);
		return "success";
	}
	/**
	 * 获取采购退货出库单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public String showPurchaseRejectOutList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = purchaseRejectOutService.showPurchaseRejectOutList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 导出采购退货出库单列表数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 30, 2014
	 */
	public void exportPurchaseRejectOutListData()throws Exception{
		Map map = getEditAccessColumn("t_storage_purchasereject_out_detail");
		Map map2=CommonUtils.changeMap(request.getParameterMap());
		map2.put("isflag", "true");
		pageMap.setCondition(map2);
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
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("storagename", "退货仓库");
		firstMap.put("supplierid", "供应商编码");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("buydeptname", "采购部门");
		firstMap.put("buyusername", "采购员");
		if(map.containsKey("taxamount")){
			firstMap.put("taxamount", "含税金额");
		}
		if(map.containsKey("taxamount")){
			firstMap.put("notaxamount", "未税金额");
		}
		firstMap.put("sourcetypename", "来源类型");
		firstMap.put("sourceid", "来源编号");
		firstMap.put("statusname", "状态");
		firstMap.put("printtip", "打印提示");
		firstMap.put("printtimes", "打印次数");
		firstMap.put("addusername", "制单人");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		PageData pageData = purchaseRejectOutService.showPurchaseRejectOutList(pageMap);
		List<PurchaseRejectOut> list = pageData.getList();
		list.addAll(pageData.getFooter());
		if(list.size() != 0){
			for(PurchaseRejectOut item : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3 = PropertyUtils.describe(item);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map3.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map3.entrySet()){
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
	 * 显示采购退货出库单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public String showPurchaseRejectOutEditPage() throws Exception{
		Boolean flag=true;
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		Map map = getEditAccessColumn("t_storage_purchasereject_out_detail");
		request.setAttribute("map", map);
		PurchaseRejectOut purchaseRejectOut=purchaseRejectOutService.getPurchaseRejectOutPureInfo(id);
		if(null==purchaseRejectOut){
			flag=false;
		}
		request.setAttribute("flag", flag);
		return "success";
	}
	/**
	 * 显示采购退货出库单详细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public String purchaseRejectOutEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = purchaseRejectOutService.getPurchaseRejectOut(id);
		PurchaseRejectOut purchaseRejectOut = (PurchaseRejectOut) map.get("purchaseRejectOut");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("purchaseRejectOut", purchaseRejectOut);
		request.setAttribute("detailList", jsonStr);
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		//采购部门
		List deptList = getBaseDepartMentService().getDeptListByOperType("3");
		request.setAttribute("deptList", deptList);
		List userList = getPersListByOperationTypeAndDeptid("0", purchaseRejectOut.getBuydeptid());
		request.setAttribute("userList", userList);

		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		
		if("1".equals(purchaseRejectOut.getStatus()) || "2".equals(purchaseRejectOut.getStatus()) || "6".equals(purchaseRejectOut.getStatus())){
			return "success";
		}else{
			return "viewSuccess";
		}
	}
	/**
	 * 采购退货出库单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	@UserOperateLog(key="PurchaseRejectOut",type=3)
	public String editPurchaseRejectOutHold() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseRejectOutDetail());
		Map map = purchaseRejectOutService.editPurchaseRejectOut(purchaseRejectOut, detailList);
		addJSONObject(map);
		addLog("采购退货出库单修改暂存 编号："+purchaseRejectOut.getId(), map);
		return "success";
	}
	/**
	 * 采购退货出库修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	@UserOperateLog(key="PurchaseRejectOut",type=3)
	public String editPurchaseRejectOutSave() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseRejectOutDetail());
		Map map = purchaseRejectOutService.editPurchaseRejectOut(purchaseRejectOut, detailList);
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean)map.get("flag");
		if(flag && "saveaudit".equals(saveaudit)){
			Map result = purchaseRejectOutService.auditPurchaseRejectOut(purchaseRejectOut.getId());
			map.put("auditflag", result.get("flag"));
			addLog("采购退货出库单保存审核 编号："+purchaseRejectOut.getId(), (Boolean) result.get("flag"));
		}else{
			addLog("采购退货出库单修改保存 编号："+purchaseRejectOut.getId(), map);
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 获取采购退货出库单明细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public String getPurchaseRejectOutDetailInfo() throws Exception{
		String id = request.getParameter("id");
		PurchaseRejectOutDetail purchaseRejectOutDetail = purchaseRejectOutService.getPurchaseRejectOutDetailInfo(id);
		addJSONObject("detail", purchaseRejectOutDetail);
		return "success";
	}
	/**
	 * 采购退货出库单删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	@UserOperateLog(key="PurchaseRejectOut",type=4)
	public String deletePurchaseRejectOut() throws Exception{
		String id = request.getParameter("id");
		boolean flag = purchaseRejectOutService.deletePurchaseRejectOut(id);
		addJSONObject("flag", flag);
		addLog("采购退货出库单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 采购退货出库单审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	@UserOperateLog(key="PurchaseRejectOut",type=0)
	public String auditPurchaseRejectOut() throws Exception{
		String id = request.getParameter("id");
		Map map = purchaseRejectOutService.auditPurchaseRejectOut(id);
		addJSONObject(map);
		addLog("采购退货出库单审核 编号："+id, (Boolean) map.get("flag"));
		return "success";
	}
	/**
	 * 采购退货出库单反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	@UserOperateLog(key="PurchaseRejectOut",type=0)
	public String oppauditPurchaseRejectOut() throws Exception{
		String id = request.getParameter("id");
		Map resultMap = purchaseRejectOutService.oppauditPurchaseRejectOut(id);
		Boolean flag=false;
		String msg="";
		if(null==resultMap){
			resultMap=new HashMap();
			resultMap.put("flag", false);
			flag=false;
		}else{
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", false);
			}
			if(resultMap.containsKey("msg")){
				msg=(String)resultMap.get("msg");
			}
		}
		addJSONObject(resultMap);
		addLog("采购退货出库单反审 编号："+id+"。 "+msg, flag);
		return "success";
	}
	/**
	 * 显示参照上游单据查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public String showPurchaseRejectOutRelationUpperPage() throws Exception{
		
		return "success";
	}
	/**
	 * 显示上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public String showPurchaseRejectOutSourceListPage() throws Exception{
		
		return "success";
	}
	/**
	 * 根据采购退货单编号生成采购退货出库单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	@UserOperateLog(key="PurchaseRejectOut",type=2)
	public String addPurchaseRejectOutByRefer() throws Exception{
		String id = request.getParameter("id");
		Map map = storageForPurchaseService.addPurchaseRejectOutByRefer(id);
		addJSONObject(map);
        String billid = (String)map.get("id");
        boolean flag = (Boolean) map.get("flag");
		addLog("采购退货出库单参照上游单据生成 编号："+billid, flag);
		return "success";
	}
	/**
	 * 采购退货出库单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitPurchaseRejectOutProcess() throws Exception{
		String id = request.getParameter("id");
		boolean flag = purchaseRejectOutService.submitPurchaseRejectOutProcess(id);
		addJSONObject("flag", flag);
		return "success";
	}
	
	
}

