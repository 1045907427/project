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

import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.ModelOrder;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.purchase.service.IReturnOrderService;

/**
 * 采购退货通知单 Action
 * 
 * @author zhanghonghui
 */
public class ReturnOrderAction extends BaseFilesAction {
	private ReturnOrder returnOrder;
	private ReturnOrderDetail returnOrderDetail;
	private IReturnOrderService returnOrderService;
	
	public IReturnOrderService getReturnOrderService() {
		return returnOrderService;
	}

	public void setReturnOrderService(IReturnOrderService returnOrderService) {
		this.returnOrderService = returnOrderService;
	}

	public ReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}

	public ReturnOrderDetail getReturnOrderDetail() {
		return returnOrderDetail;
	}

	public void setReturnOrderDetail(ReturnOrderDetail returnOrderDetail) {
		this.returnOrderDetail = returnOrderDetail;
	}
	/**
	 * 采购退货通知单列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String returnOrderListPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	/**
	 * 采购退货通知单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showReturnOrderPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=returnOrderService.showReturnOrderPageList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 导出采购退货通知单列表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 30, 2014
	 */
	public void exportReturnOrderListData()throws Exception{
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
		firstMap.put("supplierid", "供应商编码");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("handlername", "对方联系人");
		firstMap.put("buydeptname", "采购部门");
		firstMap.put("field01", "金额");
		firstMap.put("addusername", "制单人");
		firstMap.put("auditusername", "审核人");
		firstMap.put("statusname", "状态");
		firstMap.put("isinvoicename", "发票状态");
		firstMap.put("ckstatusname", "出库状态");
		firstMap.put("remark", "备注");
		firstMap.put("addtime", "制单时间");
		firstMap.put("printtimes", "打印次数");
		result.add(firstMap);
		
		PageData pageData=returnOrderService.showReturnOrderPageList(pageMap);
		List<ReturnOrder> list = pageData.getList();
        //计算导出记录的金额
        BigDecimal money = BigDecimal.ZERO ;
		for(ReturnOrder returnOrder : list){
            money = money.add(new BigDecimal(returnOrder.getField01()));
        }
        ReturnOrder returnOrder = new ReturnOrder();
        returnOrder.setId("选中金额");
        returnOrder.setField01(money+"");
        list.add(returnOrder);
		if(list.size() != 0){
			for(ReturnOrder item : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(item);
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
		ExcelUtils.exportExcel(result, title);
	}



    /**
     * 明细导出
     * @throws Exception
     */
    public void exportReturnOrderListDetail() throws Exception {
        Map map2=CommonUtils.changeMap(request.getParameterMap());
        map2.put("isflag", "true");
        pageMap.setCondition(map2);
        String title = "";
        if(map2.containsKey("excelTitle")){
            title = map2.get("excelTitle").toString();
        }else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("buydeptname", "采购部门");
		firstMap.put("storagename", "退货仓库");
        firstMap.put("statusname", "状态");
        firstMap.put("isinvoicename", "发票状态");
        firstMap.put("ckstatusname", "出库状态");
        firstMap.put("goodsid","商品编码");
        firstMap.put("goodsname","商品名称");
        firstMap.put("barcode","条形码");
        firstMap.put("boxnum","箱装量");
        firstMap.put("unitname","单位");
        firstMap.put("unitnum","数量");
        firstMap.put("taxprice","单价");
        firstMap.put("boxprice","箱价");
        firstMap.put("goodstaxamount","金额");
        firstMap.put("notaxprice","未税单价");
        firstMap.put("notaxboxprice","未税箱价");
        firstMap.put("goodsnotaxamount","未税金额");
        firstMap.put("tax","税额");
        firstMap.put("auxnumdetail","辅数量");
        firstMap.put("storagelocationname","所属库位");
        firstMap.put("batchno","批次号");
        firstMap.put("produceddate","生产日期");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        PageData pageData=returnOrderService.showReturnOrderPageList(pageMap);
        List<ReturnOrder> list = pageData.getList();

        List<ReturnOrder> addList = new ArrayList<ReturnOrder>();
        for(ReturnOrder item : list){
            ReturnOrder returnOrder = returnOrderService.getReturnOrder(item.getId());
            List<ReturnOrderDetail> detailList = returnOrder.getReturnOrderDetailList();
            for(ReturnOrderDetail detail : detailList){
                ReturnOrder reOrder = getReturn(returnOrder);
                reOrder.setGoodsid(detail.getGoodsid());
                GoodsInfo goodsInfo = detail.getGoodsInfo();
                reOrder.setGoodsname(goodsInfo.getName());
                reOrder.setBarcode(goodsInfo.getBarcode());
                reOrder.setBoxnum(goodsInfo.getBoxnum());
                reOrder.setUnitname(goodsInfo.getMainunitName());
                reOrder.setUnitnum(detail.getUnitnum());
                reOrder.setTaxprice(detail.getTaxprice());
                reOrder.setBoxprice(detail.getTaxprice().multiply(goodsInfo.getBoxnum()));
                reOrder.setGoodstaxamount(detail.getTaxamount());
                reOrder.setNotaxprice(detail.getNotaxprice());
                reOrder.setNotaxboxprice(detail.getNotaxprice().multiply(goodsInfo.getBoxnum()));
                reOrder.setGoodsnotaxamount(detail.getNotaxamount());
                reOrder.setTax(detail.getTax());
                reOrder.setAuxnumdetail(detail.getAuxnumdetail());
                reOrder.setStoragelocationname(detail.getStoragelocationname());
                reOrder.setBatchno(detail.getBatchno());
                reOrder.setProduceddate(detail.getProduceddate());
                reOrder.setRemark(detail.getRemark());
                addList.add(reOrder);
            }
        }

        if(addList.size() != 0){
            for(ReturnOrder item : addList){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(item);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 明细导出时获取记录的单据信息
     * @param returnOrder
     * @return
     * @throws Exception
     */
    public ReturnOrder getReturn(ReturnOrder returnOrder) throws Exception {

        ReturnOrder order = new ReturnOrder();
        order.setId(returnOrder.getId());
        order.setStoragename(returnOrder.getStoragename());
        order.setBusinessdate(returnOrder.getBusinessdate());
        order.setSupplierid(returnOrder.getSupplierid());
        order.setSuppliername(returnOrder.getSuppliername());
        order.setBuydeptname(returnOrder.getBuydeptname());
        order.setField01(returnOrder.getField01());
        SysCode code = getBaseSysCodeService().showSysCodeInfo(returnOrder.getStatus(),"status");
        order.setStatusname(code.getCodename());
        if("1".equals(returnOrder.getIsinvoice())){
            order.setIsinvoicename("已开票");
        }else  if("2".equals(returnOrder.getIsinvoice())){
            order.setIsinvoicename("已核销");
        }else if("3".equals(returnOrder.getIsinvoice())){
            order.setIsinvoicename("开票中");
        }else if("-999".equals(returnOrder.getIsinvoice())){
            order.setIsinvoicename("");
        }else{
            order.setIsinvoicename("未开票");
        }
        if("0".equals(returnOrder.getCkstatus())){
            order.setCkstatusname("未出库");
        }else if("1".equals(returnOrder.getCkstatus())){
            order.setCkstatusname("出库");
        }

        return order ;
    }

	/**
	 * 采购申请页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String returnOrderPage() throws Exception{
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
	public String returnOrderAddPage() throws Exception{
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("today", dateFormat.format(calendar.getTime()));
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
	@UserOperateLog(key="ReturnOrder-Purchase",type=2)
	public String addReturnOrder() throws Exception{
		Map map=new HashMap();
		boolean flag=false;
		String addType = request.getParameter("addType");
		String returnOrderDetails=request.getParameter("returnOrderDetails");
		List<ReturnOrderDetail> rodList=null;
		if(null!=returnOrderDetails && !"".equals(returnOrderDetails.trim())){
			rodList=JSONUtils.jsonStrToList(returnOrderDetails.trim(),new ReturnOrderDetail());
			returnOrder.setReturnOrderDetailList(rodList);
		}
		
		if("temp".equals(addType)){
			returnOrder.setStatus("1");	//暂存
		}else if("real".equals(addType)){
			returnOrder.setStatus("2");
		}else{
			returnOrder.setStatus("1");//暂存
		}
		if("2".equals(returnOrder.getStatus())){
			if(null==rodList || rodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要退货的商品信息");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		SysUser sysUser = getSysUser();
		returnOrder.setAdduserid(sysUser.getUserid());
		returnOrder.setAddusername(sysUser.getName());
		returnOrder.setAdddeptid(sysUser.getDepartmentid());
		returnOrder.setAdddeptname(sysUser.getDepartmentname());
		returnOrder.setAddtime(new Date());
		Map map1 = returnOrderService.addReturnOrderAddDetail(returnOrder);
		flag = map1.get("flag").equals(true);

		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit) && flag){
			Map result = returnOrderService.auditReturnOrder(returnOrder.getId());
			//addJSONObject(result);
			boolean auditflag = (Boolean) result.get("flag");
			String msg = (String) result.get("msg");
			String billid = (String) result.get("billid");
			map.put("auditflag", auditflag);
			map.put("billid", billid);
			map.put("msg", msg);
			logStr="保存并审核采购采购退货通知单成功，单据编号："+returnOrder.getId()+",生成采购退货出库单，单据编号："+billid;
		}else if(flag){
			logStr="添加采购退货通知单成功，单据编号："+returnOrder.getId();
		}else{
			logStr="添加采购退货通知单失败，单据编号："+returnOrder.getId();
		}
		map.put("flag", flag);
		map.put("backid", returnOrder.getId());
		map.put("opertype", "add");
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 采购编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String returnOrderEditPage() throws Exception{
		String id=request.getParameter("id");
		ReturnOrder rOrder=returnOrderService.showReturnOrderAndDetail(id);
		if(null==rOrder){
			rOrder=new ReturnOrder();
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=rOrder.getReturnOrderDetailList()){
			jsonStr = JSONUtils.listToJsonStr(rOrder.getReturnOrderDetailList());
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		request.setAttribute("user", sysUser);
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("returnOrder", rOrder);
		request.setAttribute("goodsDataList", jsonStr);
		if(!"1".equals(rOrder.getStatus()) && !"2".equals(rOrder.getStatus())){
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
	@UserOperateLog(key="ReturnOrder-Purchase",type=3)
	public String editReturnOrder() throws Exception{
//		boolean lock = isLockEdit("t_purchase_returnorder", order.getId()); // 判断锁定并解锁
//		if (!lock) { // 被锁定不能进行修改
//			addJSONObject("lock", true);
//			return SUCCESS;
//		}
		Map map = new HashMap();
		if(StringUtils.isEmpty(returnOrder.getId())){
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}
		
		ReturnOrder oldaOrder=returnOrderService.showReturnOrderByDataAuth(returnOrder.getId());
		if(null==oldaOrder){
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}

		String addType = request.getParameter("addType");
		if(!"1".equals(oldaOrder.getStatus()) && !"2".equals(oldaOrder.getStatus())){
			map.put("flag", false);
			map.put("msg", "抱歉，当前单据不可修改");
			addJSONObject(map);
			return SUCCESS;			
		}
		if("1".equals(oldaOrder.getStatus())){
			if("real".equals(addType)){
				returnOrder.setStatus("2");
			}
		}else{
			returnOrder.setStatus(oldaOrder.getStatus());
		}

		String returnOrderDetails=request.getParameter("returnOrderDetails");
		List<ReturnOrderDetail> rodList=null;
		if(null!=returnOrderDetails && !"".equals(returnOrderDetails.trim())){
			rodList=JSONUtils.jsonStrToList(returnOrderDetails.trim(),new ReturnOrderDetail());
			returnOrder.setReturnOrderDetailList(rodList);
		}
		
		if("2".equals(returnOrder.getStatus())){
			if(null==rodList || rodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要退货的商品信息");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		
		boolean flag=false;
		SysUser sysUser = getSysUser();
		returnOrder.setModifyuserid(sysUser.getUserid());
		returnOrder.setModifyusername(sysUser.getName());
		returnOrder.setModifytime(new Date());
		
		flag= returnOrderService.updateReturnOrderAddDetail(returnOrder);			

		map.clear();
		//判断是否审核
		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit) && flag){
			Map result = returnOrderService.auditReturnOrder(returnOrder.getId());
			//addJSONObject(result);
			boolean auditflag = (Boolean) result.get("flag");
			String msg = (String) result.get("msg");
			String billid = (String) result.get("billid");
			map.put("auditflag", auditflag);
			map.put("billid", billid);
			map.put("msg", msg);
			logStr="保存并审核采购采购退货通知单成功，单据编号："+returnOrder.getId()+",生成采购退货出库单，单据编号："+billid;
		}else if(flag){
			logStr="修改采购退货通知单成功，单据编号："+returnOrder.getId();
		}else{
			logStr="修改采购退货通知单失败，单据编号："+returnOrder.getId();
		}
		map.put("flag", flag);
		map.put("backid", returnOrder.getId());
		map.put("opertype", "edit");
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 采购申请查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String returnOrderViewPage() throws Exception{
		String id=request.getParameter("id");
		ReturnOrder rOrder=returnOrderService.showReturnOrderAndDetail(id);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=rOrder){
			jsonStr = JSONUtils.listToJsonStr(rOrder.getReturnOrderDetailList());
			
			if(StringUtils.isNotEmpty(rOrder.getSupplierid())){
				BuySupplier buySupplier=getBaseBuySupplierById(rOrder.getSupplierid());
				if(null!=buySupplier && StringUtils.isNotEmpty(buySupplier.getName())){
					rOrder.setSuppliername(buySupplier.getName());
				}
			}
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("returnOrder", rOrder);
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
	@UserOperateLog(key="ReturnOrder-Purchase",type=4)
	public String deleteReturnOrder() throws Exception{
		String id=request.getParameter("id");

		boolean delFlag = canTableDataDelete("t_purchase_returnorder", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag=returnOrderService.deleteReturnOrderAndDetail(id);
		addJSONObject("flag", flag);if(flag){
			logStr="删除采购退货通知单成功，单据编号："+id;
		}else{
			logStr="删除采购退货通知单失败，单据编号："+id;
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
	@UserOperateLog(key="ReturnOrder-Purchase",type=0)
	public String auditReturnOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=returnOrderService.auditReturnOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		String billid=(String) map.get("billid");
		if(flag){
			logStr="审核采购退货通知单成功，单据编号："+id+",生成采购退货出库单，单据编号："+billid;
		}else{
			logStr="审核采购退货通知单失败，单据编号："+id;
		}
		return SUCCESS;
	}
	/**
	 * 反审申请采购申请
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="ReturnOrder-Purchase",type=0)
	public String oppauditReturnOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=returnOrderService.oppauditReturnOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="反审采购退货通知单成功，单据编号："+id;
		}else{
			logStr="反审采购退货通知单失败，单据编号："+id;
		}
		return SUCCESS;		
	}
	

	//------------------------------------------------------------------------//
	//采购退货通知单详细
	//-----------------------------------------------------------------------//
	
	/**
	 * 根据采购退货通知单编号数组字符串，查询采购退货通知单明细列表信息，并组装成采购退货出库单明细
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-25
	 */
	public String showReturnOrderDetailReferList() throws Exception{
		String orderidarrs=request.getParameter("orderidarrs");
		Map map=new HashMap();
		map.put("orderidarrs", orderidarrs);
		map.put("ischeckauth", "0");
		map.put("notinvoicerefer", "1");
		List<ReturnOrderDetail> list=returnOrderService.showReturnOrderDetailListBy(map);		
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 采购退货通知单明细列表添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String returnOrderDetailAddPage() throws Exception{
		String supplierid=request.getParameter("supplierid");
		String storageid=request.getParameter("storageid");
		/*if(null!=supplier && !"".equals(supplier.trim())){
			List goodsList=getGoodsInfoBySupplier(supplier);
			if(null==goodsList || goodsList.size()==0){
				supplier=null;
			}
		}else{
			supplier=null;
		}*/
		request.setAttribute("supplierid", supplierid);
		request.setAttribute("storageid", storageid);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, 2);
		request.setAttribute("arrivedate", dateFormat.format(calendar.getTime()));
		Map colMap = getEditAccessColumn("t_purchase_returnorder_detail");
		request.setAttribute("colMap", colMap);

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
	 * 采购退货通知单明细列表编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String returnOrderDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_purchase_returnorder_detail");
		request.setAttribute("colMap", colMap);
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null!=goodsInfo && "1".equals(goodsInfo.getIsbatch())){
			request.setAttribute("isbatch", "1");
		}else{
			request.setAttribute("isbatch", "0");
		}
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	/**
	 * 提交采购退货单进流程
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public String submitReturnOrderProcess() throws Exception{
		String id = request.getParameter("id");
		ReturnOrder bill = returnOrderService.showReturnOrder(id);
		if(!bill.getStatus().equals("2")){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "采购计划单 "+bill.getId()+" (" + bill.getBusinessdate() + ")";
		boolean flag= returnOrderService.submitReturnOrderProcess(title, user.getUserid(), "purchaseReturnOrder", id, variables);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	 /**
	  * 显示采购退货通知单模板导入页面
	  * @author lin_xx
	  * @date 2016/12/13
	  */
	 public String showReturnModelPage() throws Exception {
		 return SUCCESS;
	 }
	  /**
	   * 模板导入
	   * @author lin_xx
	   * @date 2016/12/13
	   */
	  public String importReturnModel() throws Exception {
		  String gtype = request.getParameter("gtype");
		  String pricetype = request.getParameter("pricetype");
		  String fileparam = request.getParameter("fileparam");
		  String[] info = fileparam.split(";");
		  int suppliercol = -1;
		  int storagecol = -1 ;
		  int goodsIdetifycol = -1;//商品标识所在的列
		  int goodsboxnumcol = -1;//商品箱数所在列
		  int goodsNumcol = -1;
		  int pricecol = -1;
		  int beginRow = -1;//开始读取的行
		  for (int i = 0; i < info.length; i++) {
			  String value = info[i].split("=")[1];
			  if (info[i].contains("开始单元格")) {
				  String beginRowStr = value.replaceAll("[a-zA-Z]+","");
				  beginRow = Integer.parseInt(beginRowStr) - 1;
			  }
		  }
		  List<String> paramList =  ExcelUtils.importFirstRowByIndex(importFile,beginRow-1,0);//商品信息行
		  if(paramList.size() > 0){
			  for(int i = 0 ; i< info.length ; ++ i){
				  String value = info[i].split("=")[1];
				  String param = info[i].split("=")[0];
				  if("商品条形码".equals(param) || "商品编码".equals(param)){
					  goodsIdetifycol = paramList.indexOf(value);;
				  }else if("商品数量".equals(param) ){
					  goodsNumcol = paramList.indexOf(value);
				  }else if("商品箱数".equals(param) ){
					  goodsboxnumcol =paramList.indexOf(value);
				  }else if("商品单价".equals(param) ){
					  pricecol = paramList.indexOf(value);
				  }else if("供应商编码".equals(param)){
					  suppliercol = paramList.indexOf(value);
				  }else if("仓库名称".equals(param)){
					  storagecol = paramList.indexOf(value);
				  }
			  }
		  }
		  Map goodsMap = ExcelUtils.importExcelByGoodsGolumn(importFile,0,beginRow,0,goodsIdetifycol);
		  Map numMap = new HashMap();
		  Map priceMap = new HashMap();
		  Map supplierMap = new HashMap();
		  String msg = "";
		  if(goodsNumcol > -1){
			  numMap = ExcelUtils.importExcelByGoodsGolumn(importFile,0,beginRow,0,goodsNumcol);
		  }else if(goodsboxnumcol > -1){
			  numMap = ExcelUtils.importExcelByGoodsGolumn(importFile,0,beginRow,0,goodsboxnumcol);
		  }else{
			  msg = "无法读取商品数量，导入失败！";
		  }
		  if(pricecol > -1 && "1".equals(pricetype)){
			  priceMap = ExcelUtils.importExcelByGoodsGolumn(importFile,0,beginRow,0,pricecol);
		  }
		  if(suppliercol > -1){
			  supplierMap = ExcelUtils.importExcelByGoodsGolumn(importFile,0,beginRow,0,suppliercol);
		  }else{
			  msg += "无法读取供应商，导入失败！";
		  }
		  Map storageMap = ExcelUtils.importExcelByGoodsGolumn(importFile,0,beginRow,0,storagecol);
		  if(StringUtils.isEmpty(msg)){
			  Map<String,List> orderSupplierMap = new HashMap<String, List>();
			  for (int i = beginRow; i <= supplierMap.size() && i<= goodsMap.size(); i++) {//有效记录必须有供应商和商品标识
				  String supplierid = (String) supplierMap.get(i);
				  String storage = (String) storageMap.get(i);
				  StorageInfo storageInfo = getBaseStorageService().getStorageByName(storage);
				  String storageid = storageInfo.getId();
				  String key = supplierid+","+storageid ;
				  ModelOrder modelOrder = new ModelOrder();
				  modelOrder.setUnitnum((String) numMap.get(i));
				  if("1".equals(gtype)){
					  modelOrder.setBarcode((String)goodsMap.get(i));
				  }else if("4".equals(gtype)){
					  modelOrder.setGoodsid((String) goodsMap.get(i));
				  }
				  if(priceMap.size() > 0 && "1".equals(pricetype)){
					  modelOrder.setTaxprice((String)priceMap.get(i));
				  }
				  if(null == orderSupplierMap || !orderSupplierMap.containsKey(key)){
					  List list = new ArrayList();
					  list.add(modelOrder);
					  orderSupplierMap.put(supplierid+","+storageid,list);
				  }else if(orderSupplierMap.containsKey(key)){//同供应商同仓库商品
					  List list = orderSupplierMap.get(supplierid+","+storageid);
					  list.add(modelOrder);
					  orderSupplierMap.put(supplierid+","+storageid,list);
				  }
			  }
			  if(null != orderSupplierMap){
                  //导入采购退货通知单
                  Map map = returnOrderService.importReturnOrderList(orderSupplierMap);
                  boolean flag = (Boolean) map.get("flag");
                  if(flag){
                      msg = "导入成功！"+map.get("msg");
                  }else{
                      msg = "导入失败！"+map.get("msg");
                  }
              }else{
                  msg = "无法读取文件信息，导入失败！";
              }
		  }
          request.setAttribute("msg",msg);

		  return SUCCESS;
	  }

	//------------------------------------------------------------------------//
	//采购退货验收
	//-----------------------------------------------------------------------//
	/**
	 * 显示采购退货验收列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public String returnCheckOrderListPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	/**
	 * 显示采购退货验收分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public String showReturnCheckOrderPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		map.put("showcheckorder", "true");
		PageData pageData=returnOrderService.showReturnOrderPageList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 显示采购退货验收页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public String returnCheckOrderPage() throws Exception{
		String tmp=request.getParameter("id");
		request.setAttribute("id", tmp);
		tmp=request.getParameter("type");
		request.setAttribute("type", tmp);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	/**
	 * 显示采购退货验收编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public String returnCheckOrderEditPage() throws Exception{
		String id=request.getParameter("id");
		ReturnOrder rOrder=returnOrderService.showReturnOrderAndDetail(id);
		if(null==rOrder){
			return "viewSuccess";
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=rOrder.getReturnOrderDetailList()){
			jsonStr = JSONUtils.listToJsonStr(rOrder.getReturnOrderDetailList());
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		request.setAttribute("user", sysUser);
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("returnOrder", rOrder);
		request.setAttribute("goodsDataList", jsonStr);
		if(!"1".equals(rOrder.getCkstatus()) 
				|| "1".equals(rOrder.getIscheck())
				|| !("0".equals(rOrder.getIsinvoice()) || "4".equals(rOrder.getIsinvoice()))){
			return "viewSuccess";
		}
		return SUCCESS;
	}
	/**
	 * 显示采购退货验收查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public String returnCheckOrderViewPage() throws Exception{
		String id=request.getParameter("id");
		ReturnOrder rOrder=returnOrderService.showReturnOrderAndDetail(id);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=rOrder){
			jsonStr = JSONUtils.listToJsonStr(rOrder.getReturnOrderDetailList());
			
			if(StringUtils.isNotEmpty(rOrder.getSupplierid())){
				BuySupplier buySupplier=getBaseBuySupplierById(rOrder.getSupplierid());
				if(null!=buySupplier && StringUtils.isNotEmpty(buySupplier.getName())){
					rOrder.setSuppliername(buySupplier.getName());
				}
			}
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("returnOrder", rOrder);
		request.setAttribute("goodsDataList", jsonStr);
		return SUCCESS;
	}

	/**
	 * 采购退货验收明细列表编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public String returnCheckOrderDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_purchase_returncheckorder_detail");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}

	/**
	 * 采购退货验收明细列表编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public String returnCheckOrderDetailViewPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 采购退货进行验收
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	@UserOperateLog(key="ReturnCheckOrder",type=3)
	public String updateReturnOrderCheck() throws Exception{
		Map map = new HashMap();
		if(StringUtils.isEmpty(returnOrder.getId())){
			map.put("flag", false);
			map.put("msg", "未能找到要验收的信息");
			addJSONObject(map);
			return SUCCESS;
		}
		
		

		String returnOrderDetails=request.getParameter("returnCheckOrderDetails");
		List<ReturnOrderDetail> rodList=null;
		if(null!=returnOrderDetails && !"".equals(returnOrderDetails.trim())){
			rodList=JSONUtils.jsonStrToList(returnOrderDetails.trim(),new ReturnOrderDetail());
			returnOrder.setReturnOrderDetailList(rodList);
		}
		
		
		SysUser sysUser = getSysUser();
		returnOrder.setModifyuserid(sysUser.getUserid());
		returnOrder.setModifyusername(sysUser.getName());
		returnOrder.setModifytime(new Date());
		
		Map resultMap= returnOrderService.updateReturnOrderCheck(returnOrder);			
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
		map.clear();
		map.put("flag", flag);
		map.put("backid", returnOrder.getId());
		map.put("opertype", "view");
		addJSONObject(map);
		addLog("采购退货通知单保存并验收 编号："+returnOrder.getId(), flag);
		return SUCCESS;
	}
	/**
	 * 采购退进行取消验收
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	@UserOperateLog(key="ReturnCheckOrder",type=0)
	public String updateReturnOrderCheckCancel() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=new HashMap();
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要取消验收的编号");
			addJSONObject(resultMap);
			return SUCCESS;			
		}
		resultMap= returnOrderService.updateReturnOrderCheckCancel(id);		
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
		addLog("采购退货通知单取消验收 编号："+id, flag);
		return SUCCESS;
	}

	@UserOperateLog(key="ReturnCheckOrder",type=0)
	public String updateReturnOrderMutiCheck() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map resultMap=new HashMap();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要取消验收的编号");
			addJSONObject(resultMap);
			return SUCCESS;			
		}
		resultMap= returnOrderService.updateReturnOrderMutiCheck(idarrs);		
		Boolean flag=false;
		String logMsg="";
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
			Integer isucess=(Integer)resultMap.get("isucess");
			Integer ifail=(Integer)resultMap.get("ifail");
			String tmpStr=(String)resultMap.get("successid");
			if(null!=isucess && isucess>0){
				logMsg="成功："+tmpStr;
			}

			tmpStr=(String)resultMap.get("failid");
			if(null!=ifail && ifail>0){
				logMsg=(logMsg.length()>0?logMsg+",":"")+"失败："+tmpStr;
			}
		}
		resultMap.put("msg", logMsg);
		addJSONObject(resultMap);
		addLog("批量验收采购退货通知单："+logMsg, flag);
		return SUCCESS;
	}

	/**
	 * 导入采购退货通知单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-21
	 */
	@UserOperateLog(key = "ReturnOrder", type = 2)
	public String importReturnOrder()throws Exception{
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("供应商编码".equals(str)){
				paramList2.add("supplierid");
			}else if("供应商名称".equals(str)){
				paramList2.add("suppliername");
			}else if("采购部门".equals(str)){
				paramList2.add("buydeptname");
			}else if("退货仓库".equals(str)){
				paramList2.add("storagename");
			}else if("商品编码".equals(str)){
				paramList2.add("goodsid");
			}else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}else if("条形码".equals(str)){
				paramList2.add("barcode");
			}else if("数量".equals(str)){
				paramList2.add("unitnum");
			}else if("单价".equals(str)){
				paramList2.add("taxprice");
			}else if("备注".equals(str)){
				paramList2.add("remark");
			}else{
				paramList2.add("null");
			}
		}
		List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
		if(null != list && list.size() != 0){
			Map map2 =  returnOrderService.importReturnOrder(list);
			String sucbillid = (String)map2.get("sucbillid");
			if(StringUtils.isNotEmpty(sucbillid)){
				addLog("采购退货通知单导入 编号："+sucbillid , true);
			}
			retMap.putAll(map2);
		} else{
			retMap.put("excelempty", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}
}

