/**
 * @(#)SaleOutAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 31, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.model.SaleoutExport;
import com.hd.agent.storage.service.IBigSaleOutService;
import com.hd.agent.storage.service.IStorageSaleOutService;
import com.hd.agent.system.model.SysCode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 销售出库单action
 * @author chenwei
 */
public class SaleOutAction extends BaseFilesAction {
	/**
	 * 销售出库单
	 */
	private Saleout saleOut;
	
	private IStorageSaleOutService storageSaleOutService;
	
	private IBigSaleOutService bigSaleOutService;
	
	public IBigSaleOutService getBigSaleOutService() {
		return bigSaleOutService;
	}

	public void setBigSaleOutService(IBigSaleOutService bigSaleOutService) {
		this.bigSaleOutService = bigSaleOutService;
	}
	/**
	 * 销售订单service 用户获取客户的商品价格信息
	 */
	private IOrderService  orderService;
	
	public IStorageSaleOutService getStorageSaleOutService() {
		return storageSaleOutService;
	}

	public void setStorageSaleOutService(
			IStorageSaleOutService storageSaleOutService) {
		this.storageSaleOutService = storageSaleOutService;
	}
	
	public IOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}

	public Saleout getSaleOut() {
		return saleOut;
	}

	public void setSaleOut(Saleout saleOut) {
		this.saleOut = saleOut;
	}

	/**
	 * 显示销售出库单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public String showSaleOutListPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoOpenList();
		request.setAttribute("storageList", storageList);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		
		//发货打印是否需要发货单审核通过才能打印
		String fHPrintAfterSaleOutAudit=getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");		
		if(null==fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())){
			fHPrintAfterSaleOutAudit="0";
		}
		request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());
		
		//发货单打印是否显示打印选项
		String showSaleoutPrintOptions=getSysParamValue("showSaleoutPrintOptions");		
		if(null==showSaleoutPrintOptions || "".equals(showSaleoutPrintOptions.trim())){
			showSaleoutPrintOptions="0";
		}
		request.setAttribute("showSaleoutPrintOptions", showSaleoutPrintOptions.trim());
		SysUser user=getSysUser();
		Personnel personnel=getPersonnelInfoById(user.getPersonnelid());
		if(null!=personnel){
			if("2".equals(personnel.getEmployetype())){
				request.setAttribute("storager", user.getPersonnelid());
			}
		}
		return "success";
	}
	/**
	 * 显示销售出库单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public String showSaleOutList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		
		String phprintsign=(String)map.get("phprintsign");
		if(null!=phprintsign && !"".equals(phprintsign.trim())){
			String phprinttimes=(String)map.get("queryphprinttimes");
			if(!(StringUtils.isNotEmpty(phprinttimes) && StringUtils.isNumeric(phprinttimes))){
				map.remove("phprintsign");
				map.remove("queryphprinttimes");
			}
		}
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
		PageData pageData = storageSaleOutService.showSaleOutList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示销售出库单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public String showSaleOutAddPage() throws Exception{
		request.setAttribute("type", "add");
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		
		//发货打印是否需要发货单审核通过才能打印
		String fHPrintAfterSaleOutAudit=getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");		
		if(null==fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())){
			fHPrintAfterSaleOutAudit="0";
		}
		request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());
		
		//发货单打印是否显示打印选项
		String showSaleoutPrintOptions=getSysParamValue("showSaleoutPrintOptions");		
		if(null==showSaleoutPrintOptions || "".equals(showSaleoutPrintOptions.trim())){
			showSaleoutPrintOptions="0";
		}
		request.setAttribute("showSaleoutPrintOptions", showSaleoutPrintOptions.trim());
		return "success";
	}
	/**
	 * 显示销售出库单新增详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public String saleOutAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_saleout"));
		request.setAttribute("userName", getSysUser().getName());
		
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		List deptList = getBaseDepartMentService().getDeptListByOperType("4");
		request.setAttribute("deptList", deptList);
		//获取客户业务员列表
		List userList = getPersListByOperationType("1");
		request.setAttribute("userList", userList);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return "success";
	}
	/**
	 * 显示销售出库单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public String showSaleOutDetailAddPage() throws Exception{
		String storageid = request.getParameter("storageid");
		String customerid = request.getParameter("customerid");
		String sourceid = request.getParameter("sourceid");
		String id = request.getParameter("id");
		request.setAttribute("storageid", storageid);
		request.setAttribute("customerid", customerid);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		if(null!=sourceid&&!"".equals(sourceid) && null!=id && !"".equals(id)){
			List list = storageSaleOutService.showDispatchBillDetailList(id, sourceid);
			String jsonStr = JSONUtils.listToJsonStr(list);
			request.setAttribute("jsonData", jsonStr);
			request.setAttribute("sourceid", sourceid);
			return "sourceSuccess";
		}else{
			return "success";
		}
	}
	/**
	 * 显示销售出库单折扣添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public String showSaleOutDetailDiscountAddPage() throws Exception{
		return "success";
	}
	/**
	 * 显示销售出库单折扣修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public String showSaleOutDetailDiscountEditPage() throws Exception{
		return "success";
	}
	/**
	 * 显示销售出库查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public String showSaleOutViewPage() throws Exception{
		Boolean flag=true;
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "view");
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
        //根据是否启用销售发货通知单流程 判断发货上游单据打开哪个页面
        String IsDispatchProcessUse = getSysParamValue("IsDispatchProcessUse");
        if(StringUtils.isNotEmpty(IsDispatchProcessUse)){
            request.setAttribute("IsDispatchProcessUse",IsDispatchProcessUse);
        }else{
            request.setAttribute("IsDispatchProcessUse","1");
        }
		//发货打印是否需要发货单审核通过才能打印
		String fHPrintAfterSaleOutAudit=getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");		
		if(null==fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())){
			fHPrintAfterSaleOutAudit="0";
		}
		request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());
		
		//发货单打印是否显示打印选项
		String showSaleoutPrintOptions=getSysParamValue("showSaleoutPrintOptions");		
		if(null==showSaleoutPrintOptions || "".equals(showSaleoutPrintOptions.trim())){
			showSaleoutPrintOptions="0";
		}
		request.setAttribute("showSaleoutPrintOptions", showSaleoutPrintOptions.trim());
		
		Saleout saleout=storageSaleOutService.getBaseSaleOutInfo(id);
		if(null==saleout){
			flag=false;
		}
		request.setAttribute("flag", flag);
		return "success";
	}
	/**
	 * 显示销售发货单工作流处理页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 24, 2013
	 */
	public String showSaleOutWorkFlowPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "handler");
		return "success";
	}
	/**
	 * 显示销售出库单详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public String saleOutViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = storageSaleOutService.getSaleOutInfo(id);
		Saleout saleout = (Saleout) map.get("saleOut");
		List list =  (List) map.get("saleOutDetail");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("saleOut", saleout);
		request.setAttribute("saleOutDetailList", jsonStr);
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 显示销售出库单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public String saleOutEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = storageSaleOutService.getSaleOutInfo(id);
		Saleout saleout = (Saleout) map.get("saleOut");
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		if(null!=saleout){
			List list =  (List) map.get("saleOutDetail");
			String jsonStr = JSONUtils.listToJsonStr(list);
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			request.setAttribute("statusList", statusList);
			request.setAttribute("saleOut", saleout);
			request.setAttribute("saleOutDetailList", jsonStr);
			
			//仓库列表
			List storageList = getStorageInfoAllList();
			request.setAttribute("storageList", storageList);
			
			if("1".equals(saleout.getStatus()) || "2".equals(saleout.getStatus()) || "6".equals(saleout.getStatus())){
				return "success";
			}else{
				return "viewSuccess";
			}
		}else{
			return "viewSuccess";
		}
	}
	/**
	 * 获取客户对应商品的价格
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public String getCustomerGoodsPrice() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String customerid = request.getParameter("customerid");
		OrderDetail orderDetail = orderService.getFixGoodsDetail(goodsid, customerid);
		addJSONObject("goodsprice", orderDetail);
		return "success";
	}
	/**
	 * 计算销售出库单明显中的金额与辅单位数量等信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public String computeSaleOutDetailNum() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String customerid = request.getParameter("customerid");
		String unitnumStr = request.getParameter("unitnum");
		String auxunitid = request.getParameter("auxunitid");
		String taxpriceStr = request.getParameter("taxprice");
		String notaxpriceStr = request.getParameter("notaxprice");
		BigDecimal taxprice = new BigDecimal(0);
		BigDecimal notaxprice = new BigDecimal(0);
		Map returnMap = new HashMap();
		if(null!=taxpriceStr){
			taxprice = new BigDecimal(taxpriceStr);
			notaxprice = new BigDecimal(notaxpriceStr);
		}else{
			OrderDetail orderDetail = orderService.getFixGoodsDetail(goodsid, customerid);
			taxprice = orderDetail.getTaxprice();
			notaxprice = orderDetail.getNotaxprice();
			returnMap.put("goodsprice", orderDetail);
		}
		//调账数量
		BigDecimal unitnum = new BigDecimal(unitnumStr);
		//含税金额
		BigDecimal taxamount = taxprice.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
		//无税金额
		BigDecimal notaxamount = notaxprice.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
		//税额
		BigDecimal tax = taxamount.subtract(notaxamount);
		//获取金额 辅单位数量 辅单位数量描述
		Map countmap = countGoodsInfoNumber(goodsid, auxunitid,taxprice, unitnum);
		
		returnMap.put("auxunitnum", countmap.get("auxnum"));
		returnMap.put("auxunitnumdetail", countmap.get("auxnumdetail"));
		returnMap.put("taxamount", taxamount);
		returnMap.put("notaxamount", notaxamount);
		returnMap.put("tax", tax);
		addJSONObject(returnMap);
		return "success";
	}
	/**
	 * 计算折扣的无税金额
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public String computeSaleOutDiscountTax() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String taxamountStr = request.getParameter("taxamount");
		BigDecimal taxamount = new BigDecimal(0);
		if(null!=taxamountStr){
			taxamount = new BigDecimal(taxamountStr);
		}
		GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(goodsid);
		if(null!=goodsInfo){
			BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, goodsInfo.getDefaulttaxtype());
			BigDecimal tax = taxamount.subtract(notaxamount);
			Map map = new HashMap();
			map.put("notaxamount", notaxamount);
			map.put("tax", tax);
			addJSONObject(map);
		}
		return "success";
	}
	/**
	 * 显示销售出库单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 5, 2013
	 */
	public String showSaleOutDetailEditPage() throws Exception{
		String storageid = request.getParameter("storageid");
		String goodsid = request.getParameter("goodsid");
		String summarybatchid = request.getParameter("summarybatchid");
        String initnum = request.getParameter("initnum");
		request.setAttribute("storageid", storageid);
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("summarybatchid", summarybatchid);
        request.setAttribute("initnum",initnum);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 销售出库单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	@UserOperateLog(key="saleOut",type=2)
	public String addSaleOutHold() throws Exception{
		if (isAutoCreate("t_storage_saleout")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(saleOut, "t_storage_saleout");
			saleOut.setId(id);
		}
		saleOut.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new SaleoutDetail());
		Map map = storageSaleOutService.addSaleOut(saleOut, detailList);
		map.put("id", saleOut.getId());
		addJSONObject(map);
		addLog("销售发货单添加暂存 编号："+saleOut.getId(), true);
		return "success";
	}
	/**
	 * 销售发货单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	@UserOperateLog(key="saleOut",type=2)
	public String addSaleOutSave() throws Exception{
		if (isAutoCreate("t_storage_saleout")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(saleOut, "t_storage_saleout");
			saleOut.setId(id);
		}
		saleOut.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new SaleoutDetail());
		Map map = storageSaleOutService.addSaleOut(saleOut, detailList);
		map.put("id", saleOut.getId());
		addJSONObject(map);
		addLog("销售发货单添加保存 编号："+saleOut.getId(), true);
		return "success";
	}
	/**
	 * 删除销售发货单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	@UserOperateLog(key="saleOut",type=4)
	public String deleteSaleOut() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageSaleOutService.deleteSaleOut(id);
		addJSONObject("flag", flag);
		addLog("销售发货单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 显示销售发货单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public String showSaleOutEditPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
        //根据是否启用销售发货通知单流程 判断发货上游单据打开哪个页面
        String IsDispatchProcessUse = getSysParamValue("IsDispatchProcessUse");
        if(StringUtils.isNotEmpty(IsDispatchProcessUse)){
            request.setAttribute("IsDispatchProcessUse",IsDispatchProcessUse);
        }else{
            request.setAttribute("IsDispatchProcessUse","1");
        }
		//发货打印是否需要发货单审核通过才能打印
		String fHPrintAfterSaleOutAudit=getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");		
		if(null==fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())){
			fHPrintAfterSaleOutAudit="0";
		}
		request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());
		
		//发货单打印是否显示打印选项
		String showSaleoutPrintOptions=getSysParamValue("showSaleoutPrintOptions");		
		if(null==showSaleoutPrintOptions || "".equals(showSaleoutPrintOptions.trim())){
			showSaleoutPrintOptions="0";
		}
		request.setAttribute("showSaleoutPrintOptions", showSaleoutPrintOptions.trim());
		SysUser user=getSysUser();
		Personnel personnel=getPersonnelInfoById(user.getPersonnelid());
		if(null!=personnel){
			if("2".equals(personnel.getEmployetype())){
				request.setAttribute("storager", user.getPersonnelid());
			}
		}
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 销售发货单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	@UserOperateLog(key="saleOut",type=3)
	public String editSaleOutHold() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new SaleoutDetail());
		Map map = storageSaleOutService.editSaleOut(saleOut, detailList);
		map.put("id", saleOut.getId());
		addJSONObject(map);
		addLog("销售发货单修改暂存 编号："+saleOut.getId(), true);
		return "success";
	}
	/**
	 * 销售发货单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	@UserOperateLog(key="saleOut",type=3)
	public String editSaleOutSave() throws Exception{
		String detailJson = request.getParameter("detailJson");
		String storager=request.getParameter("storager");
		List detailList = JSONUtils.jsonStrToList(detailJson, new SaleoutDetail());
		Map map = storageSaleOutService.editSaleOut(saleOut, detailList);
		boolean flag = (Boolean) map.get("flag");
		String saveaudit = request.getParameter("saveaudit");
		if(flag && "saveaudit".equals(saveaudit)){
			Map returnmap = storageSaleOutService.auditSaleOut(saleOut.getId());
			map.put("auditflag", returnmap.get("flag"));
			map.put("receiptid", returnmap.get("receiptid"));
			map.put("msg", returnmap.get("msg"));
			if((Boolean)returnmap.get("flag")){
				storageSaleOutService.editDispatchUser(saleOut.getId(),storager);
			}
			addLog("销售发货单保存并审核 编号："+saleOut.getId(), flag);
		}else{
			addLog("销售发货单修改 编号："+saleOut.getId(), flag);
		}
		map.put("id", saleOut.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 审核销售发货单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	@UserOperateLog(key="saleOut",type=3)
	public String auditSaleOut() throws Exception{
		String id = request.getParameter("id");
		String storager=request.getParameter("storager");
		Map map = storageSaleOutService.auditSaleOut(id);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
			if(flag){
				storageSaleOutService.editDispatchUser(id,storager);
			}
		}
		addLog("销售发货单审核 编号："+id, flag);
		return "success";
	}
	

	/**
	 * 反审销售发货单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	@UserOperateLog(key="saleOut",type=3)
	public String oppauditSaleOut() throws Exception{
		String id = request.getParameter("id");
		Map map = storageSaleOutService.oppauditSaleOut(id);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		addLog("销售发货单反审 编号："+id, flag);
		return "success";
	}

    /**
     * 清除发货单核对人信息
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="saleOut",type=3)
    public String clearSaleoutCheckuser() throws Exception{
        String ids = request.getParameter("ids");
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
                Map map = storageSaleOutService.deleteSaleoutCheckuser(id);
                boolean flag = (Boolean)map.get("flag");
                if(flag){
                    success++;
                    successstr += id+",";
                }
                else{
                    failure++;
                    if(map.containsKey("msg")){
                        msg += "<br/>"+map.get("msg");
                    }
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
        addLog("销售发货单清除核对 编号"+ids+"，成功条数:"+success+",成功编号:"+successstr+",失败条数"+failure+",失败编号:"+failstr, sFlag);
        return "success";
    }
	/**
	 * 显示参照上游单据页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	public String showRelationUpperPage() throws Exception{
		return "success";
	}
	/**
	 * 显示参照上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	public String showSaleOutSourceListPage() throws Exception{
		
		return "success";
	}
	/**
	 * 根据出库单编号和销售发货通知单编号获取发货通知单明细
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	public String showDispatchBillDetailList() throws Exception{
		String saleoutid = request.getParameter("saleoutid");
		String dispatchBillid = request.getParameter("dispatchBillid");
		List list = storageSaleOutService.showDispatchBillDetailListBySourceid(dispatchBillid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 参照销售发货通知单 生成销售发货单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 7, 2013
	 */
	@UserOperateLog(key="saleOut",type=2)
	public String addSaleOutByRefer() throws Exception{
		String dispatchbillid = request.getParameter("dispatchbillid");
		Map map = storageSaleOutService.addSaleOutByRefer(dispatchbillid);
		addJSONObject(map);
		boolean flag = false;
		if(null!=map && map.containsKey("flag")){
			flag = (Boolean) map.get("flag");
		}
		if(null!=map){
			String saleoutid = (String) map.get("saleoutid");
			addLog("销售发货单生成 编号："+saleoutid, flag);
		}
		return "success"; 
	}
	/**
	 * 根据销售发货单编号，获取销售出库明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public String getSaleOutDetailListByID() throws Exception{
		String id = request.getParameter("id");
		List list = storageSaleOutService.getSaleOutDetailListByID(id);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 发货单工作流提交
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 24, 2013
	 */
	public String submitSaleOutProcess() throws Exception{
		String id = request.getParameter("id");
		Map map = storageSaleOutService.submitSaleOutProcess(id);
		addJSONObject(map);
		return "success";
	}
	

	
	
	/**
	 * 根据销售订单获取列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public String showSaleOutDetailListBySaleorder() throws Exception{
		String saleorder=request.getParameter("id");
		List list=storageSaleOutService.getSaleOutDetailListBySaleorder(saleorder);
		addJSONArray(list);
		return SUCCESS;
	}
	/**
	 * 显示发货单待发单据商品列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	public String showGoodsWaitSaleListPage() throws Exception{
		String goodsid = request.getParameter("goodsid");
        String storageid = request.getParameter("storageid");
        if(StringUtils.isEmpty(storageid)){
            storageid = "";
        }
        String summarybatchid = request.getParameter("summarybatchid");
        if(StringUtils.isEmpty(summarybatchid)){
        	summarybatchid = "";
        }
        request.setAttribute("storageid",storageid);
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("summarybatchid", summarybatchid);
		return "success";
	}
	/**
	 * 获取发货单待发商品列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	public String showGoodsWaitSaleListData() throws Exception{
		String goodsid = request.getParameter("goodsid");
        String storageid = request.getParameter("storageid");
        if(StringUtils.isEmpty(storageid) || "null".equals(storageid)){
            storageid = null;
        }
        String summarybatchid = request.getParameter("summarybatchid");
        if(StringUtils.isEmpty(summarybatchid)){
        	summarybatchid = null;
        }
		List list = storageSaleOutService.showGoodsWaitSaleListData(goodsid,storageid,summarybatchid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 导出发货单
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 7, 2014
	 */
	public void exportSaleOutList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		
		String phprintsign=(String)map.get("phprintsign");
		if(null!=phprintsign && !"".equals(phprintsign.trim())){
			String phprinttimes=(String)map.get("queryphprinttimes");
			if(!(StringUtils.isNotEmpty(phprinttimes) && StringUtils.isNumeric(phprinttimes))){
				map.remove("phprintsign");
				map.remove("queryphprinttimes");
			}
		}
		String printsign=(String)map.get("printsign");
		if(null!=printsign && !"".equals(printsign.trim())){
			String printtimes=(String)map.get("queryprinttimes");
			if(!(StringUtils.isNotEmpty(printtimes) && StringUtils.isNumeric(printtimes))){
				map.remove("printsign");
				map.remove("queryprinttimes");
			}
		}
		map.put("isflag", "true");
		//map赋值到pageMap中作为查询条件
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
		PageData pageData = storageSaleOutService.showSaleOutList(pageMap);
		ExcelUtils.exportExcel(exportSaleOutListFielter(pageData.getList()), title);
	}
	/**
	 * 导出销售发货出库差额统计报表数据 excel格式
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 3, 2014
	 */
	private List<Map<String, Object>> exportSaleOutListFielter(List<Saleout> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编号");
		firstMap.put("saleorderid", "销售订单编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("storagename", "出库仓库");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesdeptname", "销售部门");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("sendamount", "发货出库金额");
		firstMap.put("sendnotaxamount", "发货出库未税金额");
		firstMap.put("indoorusername", "销售内勤");
		firstMap.put("status", "状态");
		firstMap.put("addusername", "制单人");
		firstMap.put("addtime", "制单时间");
		firstMap.put("auditusername", "审核人");
		firstMap.put("audittime", "审核时间");
		firstMap.put("storagername", "发货人");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		if(null!=list && list.size() != 0){
			for(Saleout saleout : list){
				if("2".equals(saleout.getStatus())){
					saleout.setStatus("保存");
				}else if("3".equals(saleout.getStatus())){
					saleout.setStatus("审核通过");
				}else if("4".equals(saleout.getStatus())){
					saleout.setStatus("关闭");
				}else if("1".equals(saleout.getStatus())){
					saleout.setStatus("暂存");
				}
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(saleout);
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
     * 获取预开票所需的审核通过的发货单数据列表
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public String getSaleoutListForAdvanceBill()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = storageSaleOutService.getSaleoutListForAdvanceBill(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 根据单据编号 获取明细列表(预开票)
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public String showSaleoutDetailListForAdvanceBillPage()throws Exception{
        String id = request.getParameter("id");
        String brandid = request.getParameter("brandid");
        List list = new ArrayList();
        BigDecimal totalamount = BigDecimal.ZERO;
        Map map = storageSaleOutService.getSaleOutInfo(id);
        if(null != map && !map.isEmpty()){
            List<SaleoutDetail> detailList = (List<SaleoutDetail>)map.get("saleOutDetail");
            for(SaleoutDetail saleoutDetail : detailList){
                Map map2 = BeanUtils.describe(saleoutDetail);
                map2.put("billid",saleoutDetail.getSaleoutid());
                if(null!=saleoutDetail.getGoodsInfo()){
                    map2.put("goodsname",saleoutDetail.getGoodsInfo().getName() );
                    map2.put("boxnum",saleoutDetail.getGoodsInfo().getBoxnum() );
                }
                map2.put("taxamount", saleoutDetail.getTaxamount());
                map2.put("notaxamount", saleoutDetail.getNotaxamount());
                map2.put("unitnum", saleoutDetail.getUnitnum());
                String isinvoicebill = (String) map2.get("isinvoicebill");
                if("0".equals(isinvoicebill)){
                    if(null==brandid || "".equals(brandid)){
                        list.add(map2);
                    }else if(brandid.equals(saleoutDetail.getBrandid())){
                        list.add(map2);
                    }
                }
                totalamount = totalamount.add(saleoutDetail.getTaxamount());
            }
        }
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("detailList", jsonStr);
        request.setAttribute("totalamount", totalamount);
        request.setAttribute("id", id);
        return SUCCESS;
    }

    /**
     * 获取发货单明细合计列表(预开票)
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public String showAdvanceBillDetailBySaleout()throws Exception{
        String ids = request.getParameter("id");
        String customerid = request.getParameter("customerid");
        String customername = request.getParameter("customername");
        String brandid = request.getParameter("brandid");
        //未选中的单据与明细
        String uncheckedjson = request.getParameter("uncheckedjson");

		String taxtype = request.getParameter("taxtype");
		if(null == taxtype || "".equals(taxtype.trim())){
			taxtype=null;
		}
        Map unCheckMap = new HashMap();
        if(null!=uncheckedjson && !"".equals(uncheckedjson)){
            JSONArray array = JSONArray.fromObject(uncheckedjson);
            for(int i=0; i<array.size(); i++){
                JSONObject jsonObject = (JSONObject)array.get(i);
                if(!jsonObject.isEmpty()){
                    String detailid = jsonObject.getString("id");
                    String billid = jsonObject.getString("billid");
                    if(!unCheckMap.containsKey(detailid)){
                        Map billMap = new HashMap();
                        billMap.put(billid, billid);
                        unCheckMap.put(detailid, billMap);
                    }else{
                        Map billMap = (Map) unCheckMap.get(detailid);
                        billMap.put(billid, billid);
                        unCheckMap.put(detailid, billMap);
                    }
                }
            }
        }
        Map customerMap = new HashMap();
        Customer customer = getCustomerById(customerid);
        if(null!=customer){
            customerMap.put(customerid, customer.getName());
        }
        if(null != ids){
            String[] idArr = ids.split(",");
            List list = new ArrayList();
            BigDecimal totalamount = BigDecimal.ZERO;
            for(String id : idArr){
                Saleout saleout = storageSaleOutService.getSaleOutInfoById(id);
                if(null != saleout){
                    Customer rcustomer = getCustomerById(saleout.getCustomerid());
                    if(null!=rcustomer){
                        customerMap.put(saleout.getCustomerid(), rcustomer.getName());
                    }
                    Map detailMap=new HashMap();
                    detailMap.put("saleoutid",saleout.getId());
                    detailMap.put("taxtype",taxtype);
                    List<SaleoutDetail> detailList = storageSaleOutService.getSaleOutDetailListSumDiscountByMap(detailMap);
                    for(SaleoutDetail saleoutDetail : detailList){
                        Map map2 = BeanUtils.describe(saleoutDetail);
                        map2.put("billid",saleoutDetail.getSaleoutid());
                        map2.put("goodsInfo", saleoutDetail.getGoodsInfo());
                        map2.put("taxamount", saleoutDetail.getTaxamount());
                        map2.put("notaxamount", saleoutDetail.getNotaxamount());
                        map2.put("unitnum", saleoutDetail.getUnitnum());
                        map2.put("isdiscount", saleoutDetail.getIsdiscount());
                        Map auxMap = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getUnitnum());
                        String auxnumdetail = (String) auxMap.get("auxnumdetail");
                        map2.put("auxnumdetail", auxnumdetail);
                        String isinvoicebill = (String) map2.get("isinvoicebill");
                        if("0".equals(isinvoicebill)){
                            boolean addflag = true;
                            if(unCheckMap.containsKey(saleoutDetail.getId().toString())){
                                Map billMap = (Map) unCheckMap.get(saleoutDetail.getId().toString());
                                if(billMap.containsKey(saleoutDetail.getSaleoutid())){
                                    addflag = false;
                                }
                            }
                            if(addflag){
                                if(null==brandid || "".equals(brandid)){
                                    list.add(map2);
                                    totalamount = totalamount.add(saleoutDetail.getTaxamount());
                                }else if(brandid.equals(saleoutDetail.getBrandid())){
                                    list.add(map2);
                                    totalamount = totalamount.add(saleoutDetail.getTaxamount());
                                }
                            }
                        }
                    }
                }
            }
            request.setAttribute("list", list);
            request.setAttribute("totalamount", totalamount);
            request.setAttribute("customername", customername);
            request.setAttribute("customerid", customerid);
            request.setAttribute("customerMap", customerMap);

			request.setAttribute("pattern", CommonUtils.getFormatNumberType());
        }
        return SUCCESS;
    }
    
    
	
	/**
	 * 修改发货单的发货人
	 * @return
	 * @throws Exception
	 * @author cwanghongteng  
	 * @date 2016-4-5
	 */
	public String editDispatchUser() throws Exception{
		String msg = "",fmsg="";
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		String storager = request.getParameter("storager");
		boolean flag=true;
		for(String id : idArr){
			flag = storageSaleOutService.editDispatchUser(id,storager);
			if(!flag){
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
			}
		}
		if (!StringUtils.isEmpty(fmsg)) {
			msg = "编号：" + fmsg + "修改失败";

		}else{
			msg="修改成功";
		}
		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		addJSONObject(returnMap);
		return "success";
	}
	
	/**
	 * 批量审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年8月12日
	 */
	@UserOperateLog(key="saleOut",type=3)
	public String auditMultiSaleout() throws Exception{
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
				Map map = storageSaleOutService.auditSaleOut(id);
				boolean flag = (Boolean)map.get("flag");
				if(flag){
					storageSaleOutService.editDispatchUser(id,storager);
					success++;
					successstr += id+",";
				}
				else{
					failure++;
					if(map.containsKey("msg")){
						msg += "</br>"+map.get("msg");
					}
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
		addLog("销售发货单批量审核 编号"+ids+"，成功条数:"+success+",成功编号:"+successstr+",失败条数"+failure+",失败编号:"+failstr, sFlag);
		return "success";
	}

	/**
	 * 发货单明细导出
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Mar 02, 2018
	 */
	public void exportSaleOutDetailList() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
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
		//根据定义类型获取显示的字段
		firstMap.put("id","编号");
		firstMap.put("saleorderid","销售订单编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("storagename", "出库仓库");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("sourceid", "来源编号");
		firstMap.put("isinvoicebill", "开票状态");
		firstMap.put("statusname", "状态");
		firstMap.put("indoorusername", "销售内勤");
		firstMap.put("storagername", "发货人");

		firstMap.put("goodsid","商品编码");
		firstMap.put("goodsname","商品名称");
		firstMap.put("isdiscount","是否折扣");
		firstMap.put("barcode","条形码");
		firstMap.put("unitnum","数量");
		firstMap.put("taxprice","单价");
		firstMap.put("costprice","成本单价");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("taxamount","金额");
		firstMap.put("dremark", "备注");
		result.add(firstMap);

		List<SaleoutExport> list = storageSaleOutService.getSaleoutDetailExportData(pageMap);

		if(list.size()!=0){
			for(SaleoutExport saleoutExport :list){
				if("0".equals(saleoutExport.getIsinvoicebill())){
					saleoutExport.setIsinvoicebill("未开票");
				}else if("1".equals(saleoutExport.getIsinvoicebill())){
					saleoutExport.setIsinvoicebill("已开票");
				}else if("4".equals(saleoutExport.getIsinvoicebill())){
					saleoutExport.setIsinvoicebill("开票中");
				}

				if("0".equals(saleoutExport.getIsdiscount())){
					saleoutExport.setIsdiscount("否");
				}else if("1".equals(saleoutExport.getIsdiscount())){
					saleoutExport.setIsdiscount("是");
				}else if("2".equals(saleoutExport.getIsdiscount())){
					saleoutExport.setIsdiscount("返利折扣");
				}

				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(saleoutExport);
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

