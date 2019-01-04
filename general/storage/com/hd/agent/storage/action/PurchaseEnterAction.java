/**
 * @(#)PurchaseEnterAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 10, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.model.PurchaseEnterDetail;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.service.IPurchaseEnterService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 采购入库单action
 * @author chenwei
 */
public class PurchaseEnterAction extends BaseFilesAction {
	
	private PurchaseEnter purchaseEnter;
	
	private IPurchaseEnterService purchaseEnterService;

	public IPurchaseEnterService getPurchaseEnterService() {
		return purchaseEnterService;
	}

	public void setPurchaseEnterService(IPurchaseEnterService purchaseEnterService) {
		this.purchaseEnterService = purchaseEnterService;
	}
	
	public PurchaseEnter getPurchaseEnter() {
		return purchaseEnter;
	}

	public void setPurchaseEnter(PurchaseEnter purchaseEnter) {
		this.purchaseEnter = purchaseEnter;
	}

	/**
	 * 显示采购入库单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public String showPurchaseEnterAddPage() throws Exception{
		request.setAttribute("type", "add");
		Map map = getEditAccessColumn("t_storage_purchase_enter_detail");
		request.setAttribute("map", map);
		return "success";
	}
	/**
	 * 显示采购入库单新增详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public String purchaseEnterAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_purchase_enter"));
		request.setAttribute("userName", getSysUser().getName());
		
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		//采购部门
		List deptList = getBaseDepartMentService().getDeptListByOperType("3");
		request.setAttribute("deptList", deptList);
		String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
		if("1".equals(OpenDeptStorage)){
			SysUser sysUser = getSysUser();
			DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
            if (null != departMent  && !departMent.getStorageid().equals("")) {
            	request.setAttribute("defaultStorageid", departMent.getStorageid());
                request.setAttribute("flag",1);
            }else{
            	request.setAttribute("defaultStorageid", "");
                request.setAttribute("flag",0);
            }
		}else{
			request.setAttribute("defaultStorageid", "");
		}
		return "success";
	}
	/**
	 * 现存采购入库单明细新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public String showPurchaseEnterDetailAddPage() throws Exception{
		String type = request.getParameter("type");
		String supplierid = request.getParameter("supplierid");
		request.setAttribute("supplierid", supplierid);
		String sysparm = getSysParamValue("IsPurchaseGoodsRepeat");

		if(null==sysparm || "".equals(sysparm)){
			sysparm="0";
		}
		request.setAttribute("isrepeat", sysparm);
		Map map = getEditAccessColumn("t_storage_purchase_enter_detail");
		request.setAttribute("map", map);

		//采购价取最高采购价还是最新采购价做系统参数
		String purchasePriceType= getSysParamValue("PurchasePriceType");
		if(null==purchasePriceType || "".equals(purchasePriceType.trim())){
			purchasePriceType="1";
		}
		purchasePriceType=purchasePriceType.trim();
		request.setAttribute("purchasePriceType", purchasePriceType);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		if("0".equals(type)){
			return "success";
		}else{
			return "giftsuccess";
		}
	}

	/**
	 * 获取商品详细信息，包括价格套信息、税种信息、计量单位信息
	 *
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 13, 2013
	 */
	public String getGoodsDetail() throws Exception {
		String goodsId = request.getParameter("id"); // 商品编码
		Map map = new HashMap();
		//采购价取最高采购价还是最新采购价做系统参数
		String purchasePriceType= getSysParamValue("PurchasePriceType");
		if(null==purchasePriceType || "".equals(purchasePriceType.trim())){
			purchasePriceType="1";
		}
		purchasePriceType=purchasePriceType.trim();
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		if("2".equals(purchasePriceType)){
			map.put("taxprice",goodsInfo.getNewbuyprice());
		}else{
			map.put("taxprice",goodsInfo.getHighestbuyprice());
		}
		addJSONObject(map);
		return SUCCESS;
	}


	/**
	 * 采购入库单数量金额计算
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public String computePurchaseEnterDetailNum() throws Exception{
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
		BigDecimal auxnum = (BigDecimal) auxnumMap.get("auxnum");
		String auxnumdetail = (String) auxnumMap.get("auxnumdetail");
		String auxunitname = (String) auxnumMap.get("auxunitname");
		String unitname =  (String) auxnumMap.get("unitname");
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
	 * 现存采购入库单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public String showPurchaseEnterDetailEditPage() throws Exception{
		String type = request.getParameter("type");
        String initnumStr = request.getParameter("initnum");
        if(StringUtils.isNotEmpty(initnumStr)){
            BigDecimal initnum = new BigDecimal(initnumStr);
            if(initnum.compareTo(BigDecimal.ZERO)==1){
                request.setAttribute("initnum",initnumStr);
            }else{
                request.setAttribute("initnum","99999999");
            }
        }else{
            request.setAttribute("initnum","99999999");
        }
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		Map map = getEditAccessColumn("t_storage_purchase_enter_detail");
		request.setAttribute("map", map);
		
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null!=goodsInfo){
			request.setAttribute("isbatch", goodsInfo.getIsbatch());
		}else{
			request.setAttribute("isbatch", "0");
		}
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		if("0".equals(type)){
			return "success";
		}else{
			return "giftsuccess";
		}
	}
	/**
	 * 采购入库单明细多批次修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月23日
	 */
	public String showPurchaseEnterDetailBatchEditPage() throws Exception{
		String goodsid = request.getParameter("goodsid");
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 采购入库单明细指定库位
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月18日
	 */
	public String showPurchaseEnterDetailEditLocationPage() throws Exception{
		return "success";
	}
	/**
	 * 获取商品详细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public String getGoodsInfo() throws Exception{
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = purchaseEnterService.getGoodsInfo(goodsid);
		addJSONObject("goodsInfo", goodsInfo);
		return "success";
	}
	/**
	 * 采购入库单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	@UserOperateLog(key="PurchaseEnter",type=2)
	public String addPurchaseEnterHold() throws Exception{
		purchaseEnter.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseEnterDetail());
		boolean flag = purchaseEnterService.addPurchaseEnter(purchaseEnter, detailList);
		Map map = new HashMap();
		map.put("id", purchaseEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		addLog("采购入库单新增暂存 编号："+purchaseEnter.getId(), flag);
		return "success";
	}
	/**
	 * 采购入库单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	@UserOperateLog(key="PurchaseEnter",type=2)
	public String addPurchaseEnterSave() throws Exception{
		purchaseEnter.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseEnterDetail());
		boolean flag = purchaseEnterService.addPurchaseEnter(purchaseEnter, detailList);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		if(flag && "saveaudit".equals(saveaudit)){
			Map returnmap = purchaseEnterService.auditPurchaseEnter(purchaseEnter.getId());
			map.put("auditflag", returnmap.get("flag"));
			map.put("msg", returnmap.get("msg"));
			map.put("downid", returnmap.get("downid"));
			map.put("purchaseEnterId", returnmap.get("purchaseEnterId"));
		}
		map.put("id", purchaseEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		addLog("采购入库单新增保存 编号："+purchaseEnter.getId(), flag);
		return "success";
	}

    /**
     * 采购入库单批量审核
     * @return
     * @throws Exception
     * @author chenwei
     * @date may 13, 2016
     */
    @UserOperateLog(key="PurchaseEnter",type=3)
    public String auditMultiPurchaseEnter() throws Exception {

        String ids = request.getParameter("ids");
        Map map = purchaseEnterService.auditMultiPurchaseEnter(ids);
        logStr = (String) map.get("msg");
        addJSONObject(map);

        return SUCCESS;
    }

	/**
	 * 显示采购入库单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public String showPurchaseEnterViewPage() throws Exception{
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
	 * 显示采购入库单查看详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public String purchaseEnterViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = purchaseEnterService.getPurchaseEnterInfo(id);
		PurchaseEnter purchaseEnter = (PurchaseEnter) map.get("purchaseEnter");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("purchaseEnter", purchaseEnter);
		request.setAttribute("detailList", jsonStr);
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 显示采购入库单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public String showPurchaseEnterListPage() throws Exception{
		//采购部门
		List deptList = getBaseDepartMentService().getDeptListByOperType("3");
		request.setAttribute("deptList", deptList);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
        Map access = getEditAccessColumn("t_storage_purchase_enter_detail");
        request.setAttribute("access", access);
		return "success";
	}
	/**
	 * 获取采购入库单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public String showPurchaseEnterList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = purchaseEnterService.showPurchaseEnterList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示采购入库单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public String showPurchaseEnterEditPage() throws Exception{
		Boolean flag=true;
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		Map map = getEditAccessColumn("t_storage_purchase_enter_detail");
		request.setAttribute("map", map);
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		PurchaseEnter purchaseEnter=purchaseEnterService.showPurchaseEnter(id);
		if(null==purchaseEnter){
			flag=false;
		}
		request.setAttribute("flag", flag);
		return "success";
	}
	/**
	 * 显示采购入库单修改详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public String purchaseEnterEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = purchaseEnterService.getPurchaseEnterInfo(id);
		PurchaseEnter purchaseEnter = (PurchaseEnter) map.get("purchaseEnter");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("purchaseEnter", purchaseEnter);
		request.setAttribute("detailList", jsonStr);
		
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		//采购部门
		List deptList = getBaseDepartMentService().getDeptListByOperType("3");
		request.setAttribute("deptList", deptList);
		List userList = getPersListByOperationTypeAndDeptid("0", purchaseEnter.getBuydeptid());
		request.setAttribute("userList", userList);
		if("1".equals(purchaseEnter.getStatus()) || "2".equals(purchaseEnter.getStatus()) || "6".equals(purchaseEnter.getStatus())){
			return "success";
		}else{
			return "viewSuccess";
		}
	}
	/**
	 * 采购入库单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	@UserOperateLog(key="PurchaseEnter",type=3)
	public String editPurchaseEnterHold() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseEnterDetail());
		boolean flag = purchaseEnterService.editPurchaseEnter(purchaseEnter, detailList);
		Map map = new HashMap();
		map.put("id", purchaseEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		addLog("采购入库单修改暂存 编号："+purchaseEnter.getId(), flag);
		return "success";
	}
	/**
	 * 采购入库单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	@UserOperateLog(key="PurchaseEnter",type=3)
	public String editPurchaseEnterSave() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new PurchaseEnterDetail());
		boolean flag = purchaseEnterService.editPurchaseEnter(purchaseEnter, detailList);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		if(flag && "saveaudit".equals(saveaudit)){
			Map returnmap = purchaseEnterService.auditPurchaseEnter(purchaseEnter.getId());
			map.put("auditflag", returnmap.get("flag"));
			map.put("msg", returnmap.get("msg"));
			map.put("downid", returnmap.get("downid"));
			map.put("purchaseEnterId", returnmap.get("purchaseEnterId"));
			boolean auditflag = (Boolean) returnmap.get("flag");
			addLog("采购入库单保存审核 编号："+purchaseEnter.getId(), auditflag);
		}else{
			addLog("采购入库单修改保存 编号："+purchaseEnter.getId(), flag);
		}
		map.put("id", purchaseEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 删除采购入库单信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	@UserOperateLog(key="PurchaseEnter",type=4)
	public String deletePurchaseEnter() throws Exception{
		String id = request.getParameter("id");
		boolean flag = purchaseEnterService.deletePurchaseEnter(id);
		addJSONObject("flag", flag);
		addLog("采购入库单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 采购入库单审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	@UserOperateLog(key="PurchaseEnter",type=3)
	public String auditPurchaseEnter() throws Exception{
		String id = request.getParameter("id");
		Map map = purchaseEnterService.auditPurchaseEnter(id);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		addLog("采购入库单审核 编号："+id, flag);
		return "success";
	}
	/**
	 * 采购入库单反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	@UserOperateLog(key="PurchaseEnter",type=3)
	public String oppauditPurchaseEnter() throws Exception{
		String id =request.getParameter("id");
		Map map = purchaseEnterService.oppauditPurchaseEnter(id);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		addLog("采购入库单反审 编号："+id, flag);
		return "success";
	}

	 /**
	  * 全局导出采购入库单
	  * @author lin_xx
	  * @date 2017/2/17
	  */
	public void exportPurchaseEnterList() throws Exception {
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		queryMap.put("isflag", "true");
		String query = (String) map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if(org.apache.commons.lang3.StringUtils.isNotEmpty((String) v)){
				queryMap.put(k.toString(), (String) v);
			}
		}
		pageMap.setCondition(queryMap);
		PageData pageData = purchaseEnterService.showPurchaseEnterList(pageMap);
		List list = pageData.getList();
		if(null != pageData.getFooter()){
			List footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 全局导出采购入库单 明细
	 * @author lin_xx
	 * @date 2017/2/17
	 */
	public void exportPurchaseEnterDetail() throws Exception {
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String id = (String) map.get("param");
		Map map1 = purchaseEnterService.getPurchaseEnterInfo(id);
		List list = (List) map1.get("detailList");
		ExcelUtils.exportAnalysExcel(map,list);
	}

	/**
	 * 参照上游单据查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 15, 2013
	 */
	public String showPurchaseOrderRelationUpperPage() throws Exception{
		
		return "success";
	}
	/**参照上游单据列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 15, 2013
	 */
	public String showPurchaseEnterSourceListPage() throws Exception{
		
		return "success";
	}
	/**
	 * 根据采购订单生成采购入库单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	@UserOperateLog(key="PurchaseEnter",type=3)
	public String addPurchaseEnterByRefer() throws Exception{
		String dispatchbillid = request.getParameter("dispatchbillid");
		Map map = purchaseEnterService.addPurchaseEnterByRefer(dispatchbillid);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		String id = "";
		if(null!=map){
			id = (String) map.get("id");
		}
		addLog("采购入库单参照上游单据生成 编号："+id, flag);
		return "success";
	}
	/**
	 * 提交采购入库单到工作流中
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 24, 2013
	 */
	public String submitPurchaseEnterProcess() throws Exception{
		String id = request.getParameter("id");
		Map map = purchaseEnterService.submitPurchaseEnterProcess(id);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 获取批次号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月22日
	 */
	public String getBatchno() throws Exception{
		String produceddate = request.getParameter("produceddate");
		String goodsid = request.getParameter("goodsid");
		Map map = purchaseEnterService.getBatchno(goodsid, produceddate);
		addJSONObject(map);
		return "success";
	}

    /**
     * 根据截止日期获取批次号与生产日期
     * @return
     * @throws Exception
     */
    public String getBatchnoByDeadline() throws Exception{
        String deadline = request.getParameter("deadline");
        String goodsid = request.getParameter("goodsid");
        Map map = purchaseEnterService.getBatchnoByDeadline(goodsid, deadline);
        addJSONObject(map);
        return "success";
    }
}

