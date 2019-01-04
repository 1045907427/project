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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.*;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.purchase.model.PlannedOrder;
import com.hd.agent.purchase.model.PlannedOrderDetail;
import com.hd.agent.purchase.service.IPlannedOrderService;
import com.hd.agent.report.model.StorageBuySaleReport;

/**
 * 采购计划单 Action
 * 
 * @author zhanghonghui
 */
public class PlannedOrderAction extends BaseFilesAction {
	private PlannedOrder plannedOrder;
	private PlannedOrderDetail plannedOrderDetail;
	private List<PlannedOrderDetail> plannedOrderDetailList;
	private IPlannedOrderService plannedOrderService;
	
	public IPlannedOrderService getPlannedOrderService() {
		return plannedOrderService;
	}

	public void setPlannedOrderService(IPlannedOrderService plannedOrderService) {
		this.plannedOrderService = plannedOrderService;
	}

	public PlannedOrder getPlannedOrder() {
		return plannedOrder;
	}

	public void setPlannedOrder(PlannedOrder plannedOrder) {
		this.plannedOrder = plannedOrder;
	}

	public PlannedOrderDetail getPlannedOrderDetail() {
		return plannedOrderDetail;
	}

	public void setPlannedOrderDetail(PlannedOrderDetail plannedOrderDetail) {
		this.plannedOrderDetail = plannedOrderDetail;
	}

	public List<PlannedOrderDetail> getPlannedOrderDetailList() {
		return plannedOrderDetailList;
	}

	public void setPlannedOrderDetailList(
			List<PlannedOrderDetail> plannedOrderDetailList) {
		this.plannedOrderDetailList = plannedOrderDetailList;
	}
	/**
	 * 采购订单列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String plannedOrderListPage() throws Exception{
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		return SUCCESS;
	}
	/**
	 * 采购订单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showPlannedOrderPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=plannedOrderService.showPlannedOrderPageList(pageMap);
		
		addJSONObjectWithFooter(pageData);

		return SUCCESS;
	}

	/**
	 * 用于下游单据参照引用的采购订单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showPlannedOrderForReferPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isrefer")){
			map.remove("isrefer");
		}
		if(map.containsKey("status")){
			map.remove("status");
		}
		map.put("choiseforrefer", "1");
		pageMap.setCondition(map);
		
		PageData pageData=plannedOrderService.showPlannedOrderPageList(pageMap);
		
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
	public String plannedOrderPage() throws Exception{
		String tmp=request.getParameter("id");
		request.setAttribute("id", tmp);
		tmp=request.getParameter("type");
		request.setAttribute("type", tmp);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	/**
	 * 采购申请添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String plannedOrderAddPage() throws Exception{
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
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("user", sysUser);
		request.setAttribute("gfieldMap", fieldMap);
		
		String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
		if("1".equals(OpenDeptStorage)){
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
		return SUCCESS;
	}
	/**
	 * 添加采购计划单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="PlannedOrder-Purchase",type=2)
	public String addPlannedOrder() throws Exception{
		
		String addType = request.getParameter("addType");
		String plannedOrderDetails=request.getParameter("plannedOrderDetails");
		List<PlannedOrderDetail> podList=null;
		Map map=new HashMap();
		
		if(null!=plannedOrderDetails && !"".equals(plannedOrderDetails.trim())){
			podList=JSONUtils.jsonStrToList(plannedOrderDetails.trim(),new PlannedOrderDetail());
			plannedOrder.setPlannedOrderDetailList(podList);
		}
		if("temp".equals(addType)){
			plannedOrder.setStatus("1");	//暂存
		}else if("real".equals(addType)){
			plannedOrder.setStatus("2");
		}else{
			plannedOrder.setStatus("1");//暂存
		}

		if("2".equals(plannedOrder.getStatus())){
			if(null==podList || podList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要采购的商品");
				addJSONObject(map);
				return SUCCESS;
			}
		}

		SysUser sysUser = getSysUser();
		plannedOrder.setAdduserid(sysUser.getUserid());
		plannedOrder.setAddusername(sysUser.getName());
		plannedOrder.setAdddeptid(sysUser.getDepartmentid());
		plannedOrder.setAdddeptname(sysUser.getDepartmentname());
		plannedOrder.setAddtime(new Date());
		boolean flag=plannedOrderService.addPlannedOrderAddDetail(plannedOrder);

		map.put("flag", flag);
		map.put("backid", plannedOrder.getId());
		map.put("opertype", "add");
		addJSONObject(map);
		if(flag){
			logStr="添加采购计划单成功，单据编号："+plannedOrder.getId();
		}else{
			logStr="添加采购计划单失败，单据编号："+plannedOrder.getId();
		}
		return "success";
	}
	
	/**
	 * 采购编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String plannedOrderEditPage() throws Exception{
		String id=request.getParameter("id");
		PlannedOrder pOrder=plannedOrderService.showPlannedOrderAndDetail(id);
		if(null==pOrder){
			pOrder=new PlannedOrder();
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = "[]";
		if(null!=pOrder.getPlannedOrderDetailList()){
			jsonStr = JSONUtils.listToJsonStr(pOrder.getPlannedOrderDetailList());
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		request.setAttribute("user", sysUser);
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("plannedOrder", pOrder);
		request.setAttribute("goodsDataList", jsonStr);
		if(!"1".equals(pOrder.getStatus()) && !"2".equals(pOrder.getStatus())){
			return "viewSuccess";
		}
		return SUCCESS;
	}
	/**
	 * 编辑采购计划单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="PlannedOrder-Purchase",type=3)
	public String editPlannedOrder() throws Exception{
//		boolean lock = isLockEdit("t_purchase_plannedorder", order.getId()); // 判断锁定并解锁
//		if (!lock) { // 被锁定不能进行修改
//			addJSONObject("lock", true);
//			return SUCCESS;
//		}
		
		String addType = request.getParameter("addType");
		String plannedOrderDetails=request.getParameter("plannedOrderDetails");
		List<PlannedOrderDetail> podList=null;
		Map map=new HashMap();
		
		if(null!=plannedOrderDetails && !"".equals(plannedOrderDetails.trim())){
			podList=JSONUtils.jsonStrToList(plannedOrderDetails.trim(),new PlannedOrderDetail());
			plannedOrder.setPlannedOrderDetailList(podList);
		}
		if("1".equals(plannedOrder.getStatus())){
			if("real".equals(addType)){
				plannedOrder.setStatus("2");				
			}
		}
		if("2".equals(plannedOrder.getStatus())){
			if(null==podList || podList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要采购的商品");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		SysUser sysUser = getSysUser();
		plannedOrder.setModifyuserid(sysUser.getUserid());
		plannedOrder.setModifyusername(sysUser.getName());
		plannedOrder.setModifytime(new Date());
		plannedOrder.setPlannedOrderDetailList(podList);
		boolean flag = plannedOrderService.updatePlannedOrderAddDetail(plannedOrder);
		map.put("flag", flag);
		map.put("backid", plannedOrder.getId());
		map.put("opertype", "edit");
		addJSONObject(map);
		if(flag){
			logStr="修改采购计划单成功，单据编号："+plannedOrder.getId();
		}else{
			logStr="修改采购计划单失败，单据编号："+plannedOrder.getId();
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
	public String plannedOrderViewPage() throws Exception{
		String id=request.getParameter("id");
		PlannedOrder pOrder=plannedOrderService.showPlannedOrderAndDetail(id);
		String jsonStr = "[]";
		if(null!=pOrder){
			if(null!=pOrder.getPlannedOrderDetailList()){
				jsonStr=JSONUtils.listToJsonStr(pOrder.getPlannedOrderDetailList());
			}

			if(StringUtils.isNotEmpty(pOrder.getSupplierid())){
				BuySupplier buySupplier=getBaseBuySupplierById(pOrder.getSupplierid());
				if(null!=buySupplier && StringUtils.isNotEmpty(buySupplier.getName())){
					pOrder.setSuppliername(buySupplier.getName());
				}
			}
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("plannedOrder", pOrder);
		request.setAttribute("goodsDataList", jsonStr);
		return SUCCESS;
	}
	
	/**
	 * 删除采购计划单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="PlannedOrder-Purchase",type=4)
	public String deletePlannedOrder() throws Exception{
		String id=request.getParameter("id");

		boolean delFlag = canTableDataDelete("t_purchase_plannedorder", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag=plannedOrderService.deletePlannedOrderAndDetail(id);
		addJSONObject("flag", flag);
		if(flag){
			logStr="删除采购计划单成功，单据编号："+id;
		}else{
			logStr="删除采购计划单失败，单据编号："+id;
		}
		return SUCCESS;
	}
	/**
	 * 审核采购计划单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="PlannedOrder-Purchase",type=0)
	public String auditPlannedOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=plannedOrderService.auditPlannedOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		String billid=(String) map.get("billid");
		if(flag){
			logStr="审核采购计划单成功，单据编号："+id+"，并生成采购订单，单据编号："+billid;
		}else{
			logStr="审核采购计划单失败，单据编号："+id;
		}
		return SUCCESS;
	}
	/**
	 * 反审采购计划单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="PlannedOrder-Purchase",type=0)
	public String oppauditPlannedOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=plannedOrderService.oppauditPlannedOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="反审采购计划单成功，单据编号："+id;
		}else{
			logStr="反审采购计划单失败，单据编号："+id;
		}
		return SUCCESS;		
	}
	

	//------------------------------------------------------------------------//
	//采购计划单详细
	//-----------------------------------------------------------------------//

	/**
	 * 采购计划单明细计算
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-13
	 */
	public String showPlannedOrderDetailList() throws Exception{
		String orderid=request.getParameter("orderid");
		List<PlannedOrderDetail> list=plannedOrderService.showPlannedOrderDetailListByOrderId(orderid);
		addJSONArray(list);
		return SUCCESS;
	}
	/**
	 * 根据采购计划单编号获取采购计划明细，并组装成采购单明细
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-25
	 */
	public String showPlannedOrderDetailReferList() throws Exception{
		String orderid=request.getParameter("orderid");
		List<PlannedOrderDetail> podlist=plannedOrderService.showPlannedOrderDetailListByOrderId(orderid);
		List<BuyOrderDetail> bodlist=new ArrayList<BuyOrderDetail>();
		if(podlist!=null && podlist.size()>0){
			BuyOrderDetail buyOrderDetail=null;
			for(PlannedOrderDetail item :podlist){
				buyOrderDetail=new BuyOrderDetail();
				buyOrderDetail.setArrivedate(item.getArrivedate());
				buyOrderDetail.setAuxnum(item.getAuxnum());
				buyOrderDetail.setAuxnumdetail(item.getAuxnumdetail());
				buyOrderDetail.setAuxunitid(item.getAuxunitid());
				buyOrderDetail.setAuxunitname(item.getAuxunitname());
				buyOrderDetail.setBilldetailno(item.getId().toString());
				buyOrderDetail.setBillno(item.getOrderid());
				buyOrderDetail.setField01(item.getField01());
				buyOrderDetail.setField02(item.getField02());
				buyOrderDetail.setField03(item.getField03());
				buyOrderDetail.setField04(item.getField04());
				buyOrderDetail.setField05(item.getField05());
				buyOrderDetail.setField06(item.getField06());
				buyOrderDetail.setField07(item.getField07());
				buyOrderDetail.setField08(item.getField08());
				buyOrderDetail.setGoodsid(item.getGoodsid());
				buyOrderDetail.setGoodsInfo(item.getGoodsInfo());
				buyOrderDetail.setRemark(item.getRemark());
				buyOrderDetail.setTax(item.getTax());
				buyOrderDetail.setTaxamount(item.getTaxamount());
				buyOrderDetail.setTaxprice(item.getTaxprice());
				buyOrderDetail.setNotaxamount(item.getNotaxamount());
				buyOrderDetail.setNotaxprice(item.getNotaxprice());
				buyOrderDetail.setTaxtype(item.getTaxtype());
				buyOrderDetail.setTaxtypename(item.getTaxtypename());
				buyOrderDetail.setUnitid(item.getUnitid());
				buyOrderDetail.setUnitname(item.getUnitname());
				buyOrderDetail.setUnitnum(item.getUnitnum());
				buyOrderDetail.setRemark(item.getRemark());
				bodlist.add(buyOrderDetail);
			}
		}
		addJSONArray(bodlist);
		return SUCCESS;
		
	}
	/**
	 * 采购计划单明细列表添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String plannedOrderDetailAddPage() throws Exception{
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
		//取系统参数中的要求到货日期
		String arrivedateDays = getSysParamValue("ARRIVEDAYS");
		int days = 2;
		if(null!=arrivedateDays && !"".equals(arrivedateDays)){
			days = new Integer(arrivedateDays);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, days);
		request.setAttribute("arrivedate", dateFormat.format(calendar.getTime()));
		Map colMap = getEditAccessColumn("t_purchase_plannedorder_detail");
		request.setAttribute("colMap", colMap);
		
		//采购价取最高采购价还是最新采购价做系统参数
		String purchasePriceType= getSysParamValue("PurchasePriceType");
		if(null==purchasePriceType || "".equals(purchasePriceType.trim())){
			purchasePriceType="1";
		}
		purchasePriceType=purchasePriceType.trim();
		request.setAttribute("purchasePriceType", purchasePriceType);
		

		//判断采购管理中商品是否允许重复1允许0不允许
		String isrepeat = getSysParamValue("IsPurchaseGoodsRepeat");
		if(null==isrepeat || "".equals(isrepeat)){
			isrepeat="0";
		}
		request.setAttribute("isrepeat", isrepeat);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	/**
	 * 采购计划单明细列表编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String plannedOrderDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_purchase_plannedorder_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	
	/**
	 * 提交采购计划单进流程
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public String submitPlannedOrderProcess() throws Exception{
		String id = request.getParameter("id");
		PlannedOrder bill = plannedOrderService.showPurePlannedOrder(id);
		if(!bill.getStatus().equals("2")){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "采购计划单 "+bill.getId()+" (" + bill.getBusinessdate() + ")";
		boolean flag= plannedOrderService.submitPlannedOrderProcess(title, user.getUserid(), "purchasePlannedOrder", id, variables);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 通过进销存报表 生成采购计划单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	@UserOperateLog(key="PlannedOrder-Purchase",type=2)
	public String addPlannedOrderByReport() throws Exception{
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

		Map map = plannedOrderService.addPlannedOrderByReport(list,remark,queryForm);
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
			addLog("采购计划分析表生成采购计划单成功，生成采购计划单号："+orderid);
		}else{
			addLog("采购计划分析表生成采购计划单失败");
		}
		return "success";
	}
	
	/**
	 * 导入采购计划单
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 1, 2014
	 */
	public String importPlannedOrder()throws Exception{
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		if(null != paramList){
			List<String> paramList2 = new ArrayList<String>();
			for(String str : paramList){
				if("业务日期".equals(str)){
					paramList2.add("businessdate");
				}else if("供应商编码".equals(str)){
					paramList2.add("supplierid");
				}else if("商品编码".equals(str)){
					paramList2.add("goodsid");
				}else if("助记符".equals(str)){
					paramList2.add("spell");
				}else if("条形码".equals(str)){
					paramList2.add("barcode");
				}else if("箱数".equals(str)){
					paramList2.add("auxnum");
				}else if("个数".equals(str)){
					paramList2.add("auxremainder");
				}else if("单价".equals(str)){
					paramList2.add("taxprice");
				}else if("金额".equals(str)){
					paramList2.add("taxamount");
				}else if("备注".equals(str)){
					paramList2.add("remark");
				}else{
					paramList2.add("null");
				}
			}

			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			List result = new ArrayList();
			if(null != list && list.size() != 0){
				Map map2 =  plannedOrderService.importPlannedOrder(list);
				retMap.putAll(map2);
			}else{
				retMap.put("excelempty", true);
			}
		}else{
			retMap.put("msg", "文件损坏!");
		}

	 	addJSONObject(retMap);
		return SUCCESS;
	}
}

