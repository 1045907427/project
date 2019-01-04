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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.purchase.model.LimitPriceOrder;
import com.hd.agent.purchase.model.LimitPriceOrderDetail;
import com.hd.agent.purchase.service.ILimitPriceOrderService;

/**
 * 采购调价单 Action
 * 
 * @author zhanghonghui
 */
public class LimitPriceOrderAction extends BaseFilesAction {
	private LimitPriceOrder limitPriceOrder;
	private LimitPriceOrderDetail limitPriceOrderDetail;
	private ILimitPriceOrderService limitPriceOrderService;
	
	public ILimitPriceOrderService getLimitPriceOrderService() {
		return limitPriceOrderService;
	}

	public void setLimitPriceOrderService(ILimitPriceOrderService limitPriceOrderService) {
		this.limitPriceOrderService = limitPriceOrderService;
	}

	public LimitPriceOrder getLimitPriceOrder() {
		return limitPriceOrder;
	}

	public void setLimitPriceOrder(LimitPriceOrder limitPriceOrder) {
		this.limitPriceOrder = limitPriceOrder;
	}

	public LimitPriceOrderDetail getLimitPriceOrderDetail() {
		return limitPriceOrderDetail;
	}

	public void setLimitPriceOrderDetail(LimitPriceOrderDetail limitPriceOrderDetail) {
		this.limitPriceOrderDetail = limitPriceOrderDetail;
	}
	/**
	 * 采购进货单列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String limitPriceOrderListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 采购进货单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showLimitPriceOrderPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=limitPriceOrderService.showLimitPriceOrderPageList(pageMap);
		
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
	public String limitPriceOrderPage() throws Exception{
		String tmp=request.getParameter("id");
		request.setAttribute("id", tmp);
		tmp=request.getParameter("type");
		request.setAttribute("type", tmp);
		return SUCCESS;
	}
	/**
	 * 采购申请添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String limitPriceOrderAddPage() throws Exception{
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		request.setAttribute("today", new Date());
		request.setAttribute("user", sysUser);
		request.setAttribute("gfieldMap", fieldMap);
		return SUCCESS;
	}
	/**
	 * 添加采购限价单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="LimitPriceOrder-Purchase",type=2)
	public String addLimitPriceOrder() throws Exception{
		Map map=new HashMap();
		boolean flag=false;
		String addType = request.getParameter("addType");
		String limitPriceOrderDetails=request.getParameter("limitPriceOrderDetails");
		List<LimitPriceOrderDetail> lodList=null;
		if(null!=limitPriceOrderDetails && !"".equals(limitPriceOrderDetails.trim())){
			lodList=JSONUtils.jsonStrToList(limitPriceOrderDetails.trim(),new LimitPriceOrderDetail());
			limitPriceOrder.setLimitPriceOrderDetailList(lodList);
		}
		
		if("temp".equals(addType)){
			limitPriceOrder.setStatus("1");	//暂存
		}else if("real".equals(addType)){
			limitPriceOrder.setStatus("2");
		}else{
			limitPriceOrder.setStatus("1");//暂存
		}
		if("2".equals(limitPriceOrder.getStatus())){
			if(null==lodList || lodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要采购的商品");
				addJSONObject(map);
				return SUCCESS;
			}
		}

		SysUser sysUser = getSysUser();
		limitPriceOrder.setAdduserid(sysUser.getUserid());
		limitPriceOrder.setAddusername(sysUser.getName());
		limitPriceOrder.setAdddeptid(sysUser.getDepartmentid());
		limitPriceOrder.setAdddeptname(sysUser.getDepartmentname());
		limitPriceOrder.setAddtime(new Date());
		flag=limitPriceOrderService.addLimitPriceOrderAddDetail(limitPriceOrder);
		map.put("flag", flag);
		map.put("backid", limitPriceOrder.getId());
		map.put("opertype", "add");
		addJSONObject(map);
		if(flag){
			logStr="添加采购限价单成功，单据编号："+limitPriceOrder.getId();
		}else{
			logStr="添加采购限价单失败，单据编号："+limitPriceOrder.getId();
		}
		return SUCCESS;
	}

	/**
	 * 采购进货单参照添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-28
	 */
	public String limitPriceOrderReferAddPage() throws Exception{
		return SUCCESS;
	}
	
	
	/**
	 * 采购编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String limitPriceOrderEditPage() throws Exception{
		String id=request.getParameter("id");
		LimitPriceOrder pOrder=limitPriceOrderService.showLimitPriceOrderAndDetail(id);
		if(null==pOrder){
			pOrder=new LimitPriceOrder();
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = "[]";
		if(null!=pOrder.getLimitPriceOrderDetailList()){
			jsonStr=JSONUtils.listToJsonStr(pOrder.getLimitPriceOrderDetailList());
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		request.setAttribute("user", sysUser);
		request.setAttribute("today", new Date());
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("limitPriceOrder", pOrder);
		request.setAttribute("goodsDataList", jsonStr);
		if(!"1".equals(pOrder.getStatus()) && !"2".equals(pOrder.getStatus())){
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
	@UserOperateLog(key="LimitPriceOrder-Purchase",type=3)
	public String editLimitPriceOrder() throws Exception{
//		boolean lock = isLockEdit("t_purchase_limitPriceorder", order.getId()); // 判断锁定并解锁
//		if (!lock) { // 被锁定不能进行修改
//			addJSONObject("lock", true);
//			return SUCCESS;
//		}
		Map map = new HashMap();
		if(StringUtils.isEmpty(limitPriceOrder.getId())){
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}
		
		LimitPriceOrder oldaOrder=limitPriceOrderService.showLimitPriceOrderByDataAuth(limitPriceOrder.getId());
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
				limitPriceOrder.setStatus("2");
			}
		}else{
			limitPriceOrder.setStatus(oldaOrder.getStatus());
		}

		String limitPriceOrderDetails=request.getParameter("limitPriceOrderDetails");
		List<LimitPriceOrderDetail> lodList=null;
		if(null!=limitPriceOrderDetails && !"".equals(limitPriceOrderDetails.trim())){
			lodList=JSONUtils.jsonStrToList(limitPriceOrderDetails.trim(),new LimitPriceOrderDetail());
			limitPriceOrder.setLimitPriceOrderDetailList(lodList);
		}
		if("2".equals(limitPriceOrder.getStatus())){
			if(null==lodList || lodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写需要调价的商品");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		boolean flag=false;
		SysUser sysUser = getSysUser();
		limitPriceOrder.setModifyuserid(sysUser.getUserid());
		limitPriceOrder.setModifyusername(sysUser.getName());
		limitPriceOrder.setModifytime(new Date());
		flag=limitPriceOrderService.updateLimitPriceOrderAddDetail(limitPriceOrder);
		map.clear();
		map.put("flag", flag);
		map.put("backid", limitPriceOrder.getId());
		map.put("opertype", "edit");
		addJSONObject(map);
		if(flag){
			logStr="修改采购限价单成功，单据编号："+limitPriceOrder.getId();
		}else{
			logStr="修改采购限价单失败，单据编号："+limitPriceOrder.getId();
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
	public String limitPriceOrderViewPage() throws Exception{
		String id=request.getParameter("id");
		LimitPriceOrder pOrder=limitPriceOrderService.showLimitPriceOrderAndDetail(id);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(pOrder.getLimitPriceOrderDetailList());
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("today", new Date());
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("limitPriceOrder", pOrder);
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
	@UserOperateLog(key="LimitPriceOrder-Purchase",type=3)
	public String deleteLimitPriceOrder() throws Exception{
		String id=request.getParameter("id");

		boolean delFlag = canTableDataDelete("t_purchase_limitPriceorder", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag=limitPriceOrderService.deleteLimitPriceOrderAndDetail(id);
		addJSONObject("flag", flag);
		if(flag){
			logStr="删除采购限价单成功，单据编号："+id;
		}else{
			logStr="删除采购限价单失败，单据编号："+id;
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
	@UserOperateLog(key="LimitPriceOrder-Purchase",type=0)
	public String auditLimitPriceOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=limitPriceOrderService.auditLimitPriceOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="审核采购限价单成功，单据编号："+id;
		}else{
			logStr="审核采购限价单失败，单据编号："+id;
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
	@UserOperateLog(key="LimitPriceOrder-Purchase",type=0)
	public String oppauditLimitPriceOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=limitPriceOrderService.oppauditLimitPriceOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="反审采购限价单成功，单据编号："+id;
		}else{
			logStr="反审采购限价单失败，单据编号："+id;
		}
		return SUCCESS;		
	}
	

	//------------------------------------------------------------------------//
	//采购进货单详细
	//-----------------------------------------------------------------------//

	/**
	 * 采购进货单明细列表添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String limitPriceOrderDetailAddPage() throws Exception{		
		//String supplierid=request.getParameter("supplierid");
		/*if(null!=supplier && !"".equals(supplier.trim())){
			List goodsList=getGoodsInfoBySupplier(supplier);
			if(null==goodsList || goodsList.size()==0){
				supplier=null;
			}
		}else{
			supplier=null;
		}*/
		//request.setAttribute("supplierid", supplierid);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, 2);
		request.setAttribute("arrivedate", dateFormat.format(calendar.getTime()));
		Map colMap = getEditAccessColumn("t_purchase_limitpriceorder_detail");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	/**
	 * 采购进货单明细列表编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String limitPriceOrderDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_purchase_limitpriceorder_detail");
		request.setAttribute("colMap", colMap);
		return SUCCESS;
	}
	/**
	 * 采购进货单明细列表查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String limitPriceOrderDetailViewPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 采购进货单来源查询
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-23
	 */
	public String limitPriceOrderSourceQueryPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 采购进货单来源结果页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-23
	 */
	public String limitPriceOrderSourcePage() throws Exception{		
		String choicerefer=request.getParameter("choicerefer");
		request.setAttribute("choicerefer", choicerefer);
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("gfieldMap", fieldMap);
		return SUCCESS;
	}

	/**
	 * 采购进货单来源查询页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-23
	 */
	public String limitPriceOrderSourceViewPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 是否存在高价单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-19
	 */
	public String existLimitPriceOrder() throws Exception{
		String id=request.getParameter("id");
		String goodsid=request.getParameter("goodsid");
		String efstartdate=request.getParameter("efstartdate");
		String efenddate=request.getParameter("efenddate");
		Map map=new HashMap();
		if(null!=id && !"".equals(id.trim())){
			map.put("notcurid", id.trim());
		}
		map.put("goodsid", goodsid);
		if(null!=efstartdate && !"".equals(efstartdate.trim())){
			map.put("effectstartdate", efstartdate.trim());
		}
		if(null!=efenddate && !"".equals(efenddate.trim())){
			map.put("effectenddate", efenddate.trim());
		}
		LimitPriceOrderDetail lPriceOrderDetail=limitPriceOrderService.getLimitPriceOrderDetailValidBy(map);
		map.clear();
		if(null!=lPriceOrderDetail){
			map.put("flag", true);
			map.put("goodsid", goodsid);
			map.put("orderid", lPriceOrderDetail.getOrderid());
			map.put("efstartdate", lPriceOrderDetail.getEffectstartdate());
			map.put("efenddate", lPriceOrderDetail.getEffectenddate());
		}else{
			map.put("flag", false);
		}
		addJSONObject(map);
		return SUCCESS;
	}
}

