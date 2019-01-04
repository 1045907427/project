/**
 * @(#)BuyApplyAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-6 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.action;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.*;
import com.hd.agent.purchase.service.ext.IPurchaseForStorageService;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.service.IPurchaseEnterService;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.service.IBuyService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.purchase.service.IBuyOrderService;
import com.hd.agent.report.model.StorageBuySaleReport;
import com.hd.agent.storage.model.PurchaseEnterDetail;

/**
 * 采购订单 Action
 * 
 * @author zhanghonghui
 */
public class BuyOrderAction extends BaseFilesAction {
	private BuyOrder buyOrder;
	private BuyOrderDetail buyOrderDetail;
	private IBuyOrderService buyOrderService;
	private IBuyService buyService;
	private IPurchaseEnterService purchaseEnterService;
	private IPurchaseForStorageService purchaseForStorageService;

	public IPurchaseForStorageService getPurchaseForStorageService() {
		return purchaseForStorageService;
	}

	public void setPurchaseForStorageService(IPurchaseForStorageService purchaseForStorageService) {
		this.purchaseForStorageService = purchaseForStorageService;
	}

	public IPurchaseEnterService getPurchaseEnterService() {
		return purchaseEnterService;
	}

	public void setPurchaseEnterService(IPurchaseEnterService purchaseEnterService) {
		this.purchaseEnterService = purchaseEnterService;
	}

	public IBuyOrderService getBuyOrderService() {
		return buyOrderService;
	}

	public void setBuyOrderService(IBuyOrderService buyOrderService) {
		this.buyOrderService = buyOrderService;
	}

	public IBuyService getBuyService() {
		return buyService;
	}

	public void setBuyService(IBuyService buyService) {
		this.buyService = buyService;
	}

	public BuyOrder getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(BuyOrder buyOrder) {
		this.buyOrder = buyOrder;
	}

	public BuyOrderDetail getBuyOrderDetail() {
		return buyOrderDetail;
	}

	public void setBuyOrderDetail(BuyOrderDetail buyOrderDetail) {
		this.buyOrderDetail = buyOrderDetail;
	}
	/**
	 * 采购订单列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String buyOrderListPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	/**
	 * 采购订单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showBuyOrderPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		map.put("showdetailcount", "1");
		pageMap.setCondition(map);
		PageData pageData=buyOrderService.showBuyOrderPageList(pageMap);
		
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 采购订单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showBuyOrderForReferPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isrefer")){
			map.remove("isrefer");
		}
		if(map.containsKey("orderappend")){
			map.remove("orderappend");
		}
		if(map.containsKey("status")){
			map.remove("status");
		}
		map.put("choiceforrefer", "1");
		map.put("showdetailcount", "1");
		pageMap.setCondition(map);
		PageData pageData=buyOrderService.showBuyOrderPageList(pageMap);
		
		addJSONObject(pageData);
		return SUCCESS;
	}


	/**
	 * 采购申请页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String buyOrderPage() throws Exception{
		String tmp=request.getParameter("id");
		request.setAttribute("id", tmp);
		tmp=request.getParameter("type");
		request.setAttribute("type", tmp);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	/**
	 * 采购申请添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String buyOrderAddPage() throws Exception{
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("today", dateFormat.format(calendar.getTime()));
        //取系统参数中的要求到货日期
        String arrivedateDays = getSysParamValue("ARRIVEDAYS");
        int days = 2;
        if(null!=arrivedateDays && !"".equals(arrivedateDays)){
            days = new Integer(arrivedateDays);
        }
        calendar.add(calendar.DATE, days);
        request.setAttribute("arrivedate", CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd"));
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("user", sysUser);
		request.setAttribute("gfieldMap", fieldMap);		
		String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
		if("1".equals(OpenDeptStorage)){
			DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
            if (null != departMent && !departMent.getStorageid().equals("")) {
            	request.setAttribute("defaultStorageid", departMent.getStorageid());
                request.setAttribute("flag",1);
            }else{
            	request.setAttribute("defaultStorageid", "");
                request.setAttribute("flag",0);
            }
		}else{
			request.setAttribute("defaultStorageid", "");
		}
		return SUCCESS;
	}
	/**
	 * 添加申请采购申请
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="BuyOrder-Purchase",type=2)
	public String addBuyOrder() throws Exception{
		Map map=new HashMap();
		boolean flag=false;
		String addType = request.getParameter("addType");
		String buyOrderDetails=request.getParameter("buyOrderDetails");
		List<BuyOrderDetail> bodList=null;

		if(null!=buyOrderDetails && !"".equals(buyOrderDetails.trim())){
			bodList=JSONUtils.jsonStrToList(buyOrderDetails.trim(),new BuyOrderDetail());
			buyOrder.setBuyOrderDetailList(bodList);
		}
		if("temp".equals(addType)){
			buyOrder.setStatus("1");	//暂存
		}else if("real".equals(addType)){
			buyOrder.setStatus("2");
		}else{
			buyOrder.setStatus("1");//暂存
		}
		if("2".equals(buyOrder.getStatus())){
			if(null==bodList || bodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要采购的商品");
				addJSONObject(map);
				return SUCCESS;
			}
		}

		SysUser sysUser = getSysUser();
		buyOrder.setAdduserid(sysUser.getUserid());
		buyOrder.setAddusername(sysUser.getName());
		buyOrder.setAdddeptid(sysUser.getDepartmentid());
		buyOrder.setAdddeptname(sysUser.getDepartmentname());
		buyOrder.setAddtime(new Date());
		flag=buyOrderService.addBuyOrderAddDetail(buyOrder);
		map.put("flag", flag);
		map.put("backid", buyOrder.getId());
		map.put("opertype", "add");
		addJSONObject(map);
		if(flag){
			logStr="添加采购订单成功，单据编号："+buyOrder.getId();
		}else{
			logStr="添加采购订单失败，单据编号："+buyOrder.getId();
		}
		return SUCCESS;
	}

	/**
	 * 采购订单参照添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-28
	 */
	@UserOperateLog(key="BuyOrder-Purchase",type=2)
	public String buyOrderReferAddPage() throws Exception{
		SysUser sysUser=getSysUser();
		String referid=request.getParameter("referid");
		BuyOrder bOrder=buyOrderService.showBuyOrderAndDetailByBillno(referid);
		bOrder.setStatus("2");
		bOrder.setAdduserid(sysUser.getUserid());
		bOrder.setAddusername(sysUser.getName());
		bOrder.setAdddeptid(sysUser.getDepartmentid());
		bOrder.setAdddeptname(sysUser.getDepartmentname());
		bOrder.setAddtime(new Date());
		boolean flag=buyOrderService.addBuyOrderAddDetail(bOrder);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(bOrder.getBuyOrderDetailList());
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("user", sysUser);
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("buyOrder", bOrder);
		request.setAttribute("goodsDataList", jsonStr);
		if(flag){
			logStr="引用采购上游计划单生成采购订单成功，单据编号："+bOrder.getId();
		}else{
			logStr="采购上游计划单生成采购订单失败，单据编号："+bOrder.getId();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 采购编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String buyOrderEditPage() throws Exception{
		String id=request.getParameter("id");
		BuyOrder bOrder=buyOrderService.showBuyOrderAndDetail(id);
		if(null==bOrder){
			bOrder=new BuyOrder();
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=bOrder.getBuyOrderDetailList()){
			jsonStr = JSONUtils.listToJsonStr(bOrder.getBuyOrderDetailList());
		}
		BuySupplier buySupplier=buyService.showBuySupplier(bOrder.getSupplierid());
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		request.setAttribute("user", sysUser);
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("buyOrder", bOrder);
		request.setAttribute("goodsDataList", jsonStr);	
		request.setAttribute("buySupplier", buySupplier);
		if(!"1".equals(bOrder.getStatus()) && !"2".equals(bOrder.getStatus())){
			return "viewSuccess";
		}
		return SUCCESS;
	}
	/**
	 * 编辑申请采购申请
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="BuyOrder-Purchase",type=3)
	public String editBuyOrder() throws Exception{
//		boolean lock = isLockEdit("t_purchase_buyorder", order.getId()); // 判断锁定并解锁
//		if (!lock) { // 被锁定不能进行修改
//			addJSONObject("lock", true);
//			return SUCCESS;
//		}
		Map map = new HashMap();
		if(StringUtils.isEmpty(buyOrder.getId())){
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}

		String buyOrderDetails=request.getParameter("buyOrderDetails");
		List<BuyOrderDetail> bodList=null;

		if(null!=buyOrderDetails && !"".equals(buyOrderDetails.trim())){
			bodList=JSONUtils.jsonStrToList(buyOrderDetails.trim(),new BuyOrderDetail());
			buyOrder.setBuyOrderDetailList(bodList);
		}
		
		BuyOrder oldbOrder=buyOrderService.showBuyOrderByDataAuth(buyOrder.getId());
		if(null==oldbOrder){
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}

		String addType = request.getParameter("addType");
		if(!"1".equals(oldbOrder.getStatus()) && !"2".equals(oldbOrder.getStatus())){
			map.put("flag", false);
			map.put("msg", "抱歉，当前单据不可修改");
			addJSONObject(map);
			return SUCCESS;			
		}
		if("1".equals(oldbOrder.getStatus())){
			if("real".equals(addType)){
				buyOrder.setStatus("2");
			}
		}else{
			buyOrder.setStatus(oldbOrder.getStatus());
		}

		if("2".equals(buyOrder.getStatus())){
			if(null==bodList || bodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要采购的商品");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		boolean flag=false;
		SysUser sysUser = getSysUser();
		buyOrder.setModifyuserid(sysUser.getUserid());
		buyOrder.setModifyusername(sysUser.getName());
		buyOrder.setModifytime(new Date());

		if("1".equals(oldbOrder.getSource())){	//有来源时处理
			buyOrder.setSupplierid(oldbOrder.getSupplierid());
			if(StringUtils.isNotEmpty(oldbOrder.getHandlerid())){
				buyOrder.setHandlerid(oldbOrder.getHandlerid());
			}
			
			if(StringUtils.isNotEmpty(oldbOrder.getBuydeptid())){
				buyOrder.setBuydeptid(oldbOrder.getBuydeptid());
			}
			if(StringUtils.isNotEmpty(oldbOrder.getBuyuserid())){
				buyOrder.setBuyuserid(oldbOrder.getBuyuserid());
			}
			buyOrder.setSource(oldbOrder.getSource());
			
		}
		
		flag = buyOrderService.updateBuyOrderAddDetail(buyOrder);
		map.put("flag", flag);
		map.put("backid", buyOrder.getId());
		map.put("opertype", "edit");
		addJSONObject(map);
		if(flag){
			logStr="修改采购订单成功，单据编号："+buyOrder.getId();
		}else{
			logStr="修改采购订单失败，单据编号："+buyOrder.getId();
		}
		return SUCCESS;
	}
	/**
	 * 采购申请查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String buyOrderViewPage() throws Exception{
		String id=request.getParameter("id");
		BuyOrder bOrder=buyOrderService.showBuyOrderAndDetail(id);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=bOrder ){
			if(null!= bOrder.getBuyOrderDetailList()){
				jsonStr=JSONUtils.listToJsonStr(bOrder.getBuyOrderDetailList());
			}
			
			if(StringUtils.isNotEmpty(bOrder.getSupplierid())){
				BuySupplier buySupplier=getBaseBuySupplierById(bOrder.getSupplierid());
				if(null!=buySupplier && StringUtils.isNotEmpty(buySupplier.getName())){
					bOrder.setSuppliername(buySupplier.getName());
				}
			}
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("buyOrder", bOrder);
		request.setAttribute("goodsDataList", jsonStr);
		return SUCCESS;
	}
	
	/**
	 * 删除申请采购申请
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="BuyOrder-Purchase",type=4)
	public String deleteBuyOrder() throws Exception{
		String id=request.getParameter("id");

		boolean delFlag = canTableDataDelete("t_purchase_buyorder", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag=buyOrderService.deleteBuyOrderAndDetail(id);
		addJSONObject("flag", flag);
		if(flag){
			logStr="删除采购订单成功，单据编号："+id;
		}else{
			logStr="删除采购订单失败，单据编号："+id;
		}
		return SUCCESS;
	}
	/**
	 * 审核申请采购申请
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="BuyOrder-Purchase",type=0)
	public String auditBuyOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}

		Map map=buyOrderService.auditBuyOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		String billid=(String) map.get("billid");

		//审核通过就入库 jqq修改的流程
		if (flag) {
			Map purchaseEnterMap = purchaseEnterService.getPurchaseEnterInfo(billid);
			PurchaseEnter purchaseEnter = (PurchaseEnter)purchaseEnterMap.get("purchaseEnter");
			if("1".equals(purchaseEnter.getSourcetype())){
				//调用采购订单回写接口
				List purchaseEnterSumList = purchaseEnterService.getPurchaseEnterDetailSumGoodsidList(purchaseEnter.getSourceid());
				purchaseForStorageService.updateBuyOrderDetailWriteBack(purchaseEnter.getSourceid(), purchaseEnterSumList);
			}
			Map returnmap = purchaseEnterService.auditPurchaseEnter(purchaseEnter.getId());
			boolean auditflag = (Boolean) returnmap.get("flag");
			addLog("采购入库单保存审核 编号："+purchaseEnter.getId(), auditflag);
		}
		if(flag){
			logStr="审核采购订单成功，单据编号："+id+"，并生成入库单，单据编号："+billid;
		}else{
			logStr="审核采购订单失败，单据编号："+id;
		}
		return SUCCESS;
	}

    /**
     * 批量审核采购订单
     * @authro lin_xx
     * @data 2016-5-24
     */
    @UserOperateLog(key = "BuyOrder-Purchase", type = 3)
    public String auditMultiBuyOrder() throws  Exception {
        String ids = request.getParameter("ids");
        Map map = buyOrderService.auditMultiBuyOrder(ids);
        logStr = (String) map.get("msg");
        addJSONObject(map);
        return SUCCESS ;
    }

	/**
	 * 反审申请采购申请
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="BuyOrder-Purchase",type=0)
	public String oppauditBuyOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=buyOrderService.oppauditBuyOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="反审采购订单成功，单据编号："+id;
		}else{
			logStr="反审采购订单失败，单据编号："+id;
		}
		return SUCCESS;		
	}
	

	//------------------------------------------------------------------------//
	//采购订单详细
	//-----------------------------------------------------------------------//
	
	/**
	 * 根据采购订单编号，查询采购订单明细列表信息，并组装成入库单明细
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-25
	 */
	public String showBuyOrderDetailReferList() throws Exception{
		String orderid=request.getParameter("orderid");
		List<PurchaseEnterDetail> pedList=new ArrayList<PurchaseEnterDetail>();
		BuyOrder buyOrder = buyOrderService.showPureBuyOrder(orderid);
		if(null!=buyOrder){
			Map map=new HashMap();
			map.put("orderid", orderid);
			map.put("ischeckauth", "0");
			if("1".equals(buyOrder.getOrderappend())){
				map.put("showcanbuygoods", "1");
			}
			List<BuyOrderDetail> bodList=buyOrderService.showBuyOrderDetailListBy(map);
			if(null!=bodList && bodList.size()>0){
				PurchaseEnterDetail purchaseEnterDetail=null;
				for(BuyOrderDetail item :bodList){
					purchaseEnterDetail=new PurchaseEnterDetail();
					purchaseEnterDetail.setBuyorderdetailid(item.getId()+"");
					purchaseEnterDetail.setBuyorderid(item.getOrderid());
					purchaseEnterDetail.setGoodsid(item.getGoodsid());
					purchaseEnterDetail.setGoodsInfo(item.getGoodsInfo());
					purchaseEnterDetail.setUnitid(item.getUnitid());
					purchaseEnterDetail.setUnitname(item.getUnitname());
					purchaseEnterDetail.setAuxunitid(item.getAuxunitid());
					
					if("1".equals(buyOrder.getOrderappend())){
						//未入库数量
						purchaseEnterDetail.setUnitnum(item.getUnstockunitnum());
						purchaseEnterDetail.setAuxunitname(item.getAuxunitname());
						Map auxMap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnstockunitnum());
						String auxnumdetail = "";
						if(auxMap.containsKey("auxnumdetail")){
							auxnumdetail=(String) auxMap.get("auxnumdetail");
						}
						purchaseEnterDetail.setAuxnumdetail(auxnumdetail);
						purchaseEnterDetail.setAuxnum(item.getUnstockauxnum());
						
						purchaseEnterDetail.setTaxamount(item.getUnstocktaxamount());
						purchaseEnterDetail.setNotaxamount(item.getUnstocknotaxamount());
						purchaseEnterDetail.setTax(item.getUnstocktaxamount().subtract(item.getUnstocknotaxamount()));
					}else{

						purchaseEnterDetail.setUnitnum(item.getUnitnum());
						purchaseEnterDetail.setAuxunitname(item.getAuxunitname());
						purchaseEnterDetail.setAuxnumdetail(item.getAuxnumdetail());
						purchaseEnterDetail.setAuxnum(item.getAuxnum());
						
						purchaseEnterDetail.setTaxamount(item.getTaxamount());
						purchaseEnterDetail.setNotaxamount(item.getNotaxamount());
						purchaseEnterDetail.setTax(item.getTax());
					}
					purchaseEnterDetail.setTaxprice(item.getTaxprice());
					purchaseEnterDetail.setNotaxprice(item.getNotaxprice());
					purchaseEnterDetail.setTaxtype(item.getTaxtype());
					
					purchaseEnterDetail.setRemark(item.getRemark());
					purchaseEnterDetail.setField01(item.getField01());
					purchaseEnterDetail.setField02(item.getField02());
					purchaseEnterDetail.setField03(item.getField03());
					purchaseEnterDetail.setField04(item.getField04());
					purchaseEnterDetail.setField05(item.getField05());
					purchaseEnterDetail.setField06(item.getField06());
					purchaseEnterDetail.setField07(item.getField07());
					purchaseEnterDetail.setField08(item.getField08());
					
					pedList.add(purchaseEnterDetail);
				}
			}
		}
		addJSONArray(pedList);
		return SUCCESS;
	}

	/**
	 * 采购订单明细列表添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String buyOrderDetailAddPage() throws Exception{
		String supplierid=request.getParameter("supplierid");
		/*if(null!=supplier && !"".equals(supplier.trim())){
			List goodsList=getGoodsInfoBySupplier(supplier);
			if(null==goodsList || goodsList.size()==0){
				supplier=null;
			}
		}else{
			supplier=null;
		}*/
		request.setAttribute("supplierid", supplierid);		
		String businessdate=request.getParameter("businessdate");
		request.setAttribute("businessdate", businessdate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		//取系统参数中的要求到货日期
        String arrivedateDays = getSysParamValue("ARRIVEDAYS");
        int days = 2;
        if(null!=arrivedateDays && !"".equals(arrivedateDays)){
            days = new Integer(arrivedateDays);
        }
        calendar.add(calendar.DATE, days);
        request.setAttribute("arrivedate", CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd"));
		Map colMap = getEditAccessColumn("t_purchase_buyorder_detail");
		request.setAttribute("colMap", colMap);
		//判断采购管理中商品是否允许重复1允许0不允许
		String isrepeat = getSysParamValue("IsPurchaseGoodsRepeat");
		if(null==isrepeat || "".equals(isrepeat)){
			isrepeat="0";
		}
		request.setAttribute("isrepeat", isrepeat);
		
		//采购价取最高采购价还是最新采购价做系统参数
		String purchasePriceType= getSysParamValue("PurchasePriceType");
		if(null==purchasePriceType || "".equals(purchasePriceType.trim())){
			purchasePriceType="1";
		}
		purchasePriceType=purchasePriceType.trim();
		request.setAttribute("purchasePriceType", purchasePriceType);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	/**
	 * 采购订单明细列表编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String buyOrderDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_purchase_buyorder_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	/**
	 * 采购订单明细列表查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String buyOrderDetailViewPage() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	/**
	 * 采购订单来源查询
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-23
	 */
	public String buyOrderSourceQueryPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 采购订单来源结果页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-23
	 */
	public String buyOrderSourcePage() throws Exception{		
		String choicerefer=request.getParameter("choicerefer");
		request.setAttribute("choicerefer", choicerefer);
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("gfieldMap", fieldMap);
		return SUCCESS;
	}

	/**
	 * 采购订单来源查询页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-23
	 */
	public String buyOrderSourceViewPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 提交采购订单进流程
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public String submitBuyOrderProcess() throws Exception{
		String id = request.getParameter("id");
		BuyOrder bill = buyOrderService.showBuyOrder(id);
		if(!bill.getStatus().equals("2")){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "采购订单 "+bill.getId()+" (" + bill.getBusinessdate() + ")";
		boolean flag= buyOrderService.submitBuyOrderProcess(title, user.getUserid(), "purchaseBuyOrder", id, variables);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 导出-采购订单明细列表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportBuyOrderDetailData()throws Exception{
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
		List list=buyOrderService.getBuyOrderDetailExport(pageMap);
		ExcelUtils.exportExcel(exportBuyOrderDetailDataFilter(list), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(采购订单明细列表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportBuyOrderDetailDataFilter(List<Map<String,Object>> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("orderid", "编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("suppliername", "供应商");
		firstMap.put("buyusername", "采购员");
		firstMap.put("buydeptname", "采购部门");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("brandname", "品牌名称");
		firstMap.put("model", "规格参数");
		firstMap.put("barcode", "条形码");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("taxprice", "含税单价");
		firstMap.put("taxamount", "含税金额");
		firstMap.put("notaxamount", "未税金额");
		firstMap.put("taxtypename", "税种");
		firstMap.put("tax", "税额");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		if(list.size() != 0){
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
		return result;
	}
	/**
	 * 显示采购计划分析报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-12
	 */
	public String showPlanOrderAnalysisViewReportPage() throws Exception{
		String businessdate=request.getParameter("businessdate");
		String firstDay = "";
		String prevyearfirstday = "";
		String prevyearcurday = "";
		String yesbusinessdate=businessdate;
		Date yestoday=CommonUtils.getYestodayDate();
		Date toDate=new Date();
		String today = CommonUtils.dataToStr(yestoday, "yyyy-MM-dd");

		String flag=getSysParamValue("PurchaseCGJHFXBPageDateSet");
		//1同期时间与前期间隔一年
		//2同期时间与前期间隔一个月
		if("1".equals(flag)){
			flag="1";	
		}else{
			flag="0";
		}
		Date firstDayDate = null;
		if(null!=businessdate && !"".equals(businessdate.trim())){
			Date Datebusiness=CommonUtils.stringToDate(businessdate.trim());
			Date yesDatebusiness=CommonUtils.getYestodayDate(Datebusiness);
			yesbusinessdate=CommonUtils.dataToStr(yesDatebusiness, "yyyy-MM-dd");
			firstDay=CommonUtils.getPrevMonthDay(Datebusiness);
			prevyearfirstday=CommonUtils.getPrevMonthDay(Datebusiness,-2);
			prevyearcurday = CommonUtils.getPrevMonthDay(yesDatebusiness);
			
			firstDayDate =CommonUtils.getBeforeTheDateInDays(Datebusiness,30);
			
			if("1".equals(flag)){
				prevyearfirstday=CommonUtils.getPrevMonthDay(firstDayDate,-12);
				prevyearcurday=CommonUtils.getPrevMonthDay(yesDatebusiness,-12);
			}else{
				firstDayDate = CommonUtils.getBeforeTheDateInDays(Datebusiness,61);
				prevyearfirstday = CommonUtils.dataToStr(firstDayDate, "yyyy-MM-dd");

				firstDayDate = CommonUtils.getBeforeTheDateInDays(Datebusiness,31);
				prevyearcurday = CommonUtils.dataToStr(firstDayDate, "yyyy-MM-dd");
			}
		}else{			
			if("1".equals(flag)){
				prevyearfirstday=CommonUtils.getPrevMonthDay(firstDayDate,-12);
				prevyearcurday=CommonUtils.getPrevMonthDay(yestoday,-12);
			}else{
				firstDayDate = CommonUtils.getBeforeTheDateInDays(toDate,61);
				prevyearfirstday = CommonUtils.dataToStr(firstDayDate, "yyyy-MM-dd");

				firstDayDate = CommonUtils.getBeforeTheDateInDays(toDate,31);
				prevyearcurday = CommonUtils.dataToStr(firstDayDate, "yyyy-MM-dd");
			}
		}
		request.setAttribute("businessdate", yesbusinessdate);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		request.setAttribute("prevyearfirstday",prevyearfirstday );
		request.setAttribute("prevyearcurday",prevyearcurday );
		
		return SUCCESS;
	}
	/**
	 * 通过进销存报表 生成采购订单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	@UserOperateLog(key="BuyOrder-Purchase",type=2)
	public String addBuyOrderByReport() throws Exception{
		String detailList = request.getParameter("detailList");
		String businessdate1 = request.getParameter("businessdate1");
		String businessdate2 = request.getParameter("businessdate2");
		String remark= request.getParameter("remark");
		String queryForm=request.getParameter("queryForm");
		if(null==remark || "".equals(remark.trim())){
			remark= "通过进销存报表生成!日期参照进销存报表:";
		}
		if(null!=businessdate1){
			remark += businessdate1;
		}else{
			remark += "从历史";
		}
		if(null!=businessdate2){
			remark += "到"+businessdate2;
		}else{
			remark += "至今";
		}
		List<StorageBuySaleReport> list = JSONUtils.jsonStrToList(detailList, new StorageBuySaleReport());
        //判断当前用户是否有关联仓库
        String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
        if("1".equals(OpenDeptStorage)){
            SysUser sysUser = getSysUser();
            DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
            if(null != departMent && !departMent.getStorageid().equals("")) {
                for(StorageBuySaleReport report : list){
                    report.setStorageid(departMent.getStorageid());
                }
            }
        }

		Map map = buyOrderService.addBuyOrderByReport(list,remark,queryForm);
		addJSONObject(map);
		Boolean flag=null;
		if(null!=map){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
				map.put("flag", flag);
			}
		}else{
			flag=false;
			map.put("flag", false);
		}
		if(flag){
			String orderid=(String)map.get("orderid");
			if(orderid==null){
				orderid="";
			}
			addLog("采购计划分析表生成采购订单成功，生成采购订单号："+orderid);
		}else{
			addLog("采购计划分析表生成采购订单失败");
		}
		return "success";
	}
	/**
	 * 关闭申请采购订单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="BuyOrder-Purchase",type=4)
	public String closeBuyOrder() throws Exception{
		String id=request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			addJSONObject("flag",false);
			return SUCCESS;
		}
		Map resultMap=buyOrderService.closeBuyOrder(id);
		Boolean flag=false;
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
		}
		addJSONObject(resultMap);
		if(flag){
			logStr="关闭采购订单成功，单据编号："+id;
		}else{
			logStr="关闭采购订单失败，单据编号："+id;
		}
		return SUCCESS;
	}
	
	
	/**
     * 根据商品编码显示历史价格表数据页面
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016年3月16日
     */
    public String showPurchaseHistoryGoodsPricePage()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        List<Map> list = buyOrderService.getPurchaseHistoryGoodsPriceList(map);
        String listjson = JSONUtils.listToJsonStr(list);
        request.setAttribute("listjson",listjson);
        return SUCCESS;
    }

}

