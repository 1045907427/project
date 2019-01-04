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

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.purchase.model.ArrivalOrder;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import com.hd.agent.purchase.service.IArrivalOrderService;
import com.hd.agent.system.model.SysParam;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 采购到货单 Action
 * 
 * @author zhanghonghui
 */
public class ArrivalOrderAction extends BaseFilesAction {
	private ArrivalOrder arrivalOrder;
	private ArrivalOrderDetail arrivalOrderDetail;
	private IArrivalOrderService arrivalOrderService;
	
	public IArrivalOrderService getArrivalOrderService() {
		return arrivalOrderService;
	}

	public void setArrivalOrderService(IArrivalOrderService arrivalOrderService) {
		this.arrivalOrderService = arrivalOrderService;
	}

	public ArrivalOrder getArrivalOrder() {
		return arrivalOrder;
	}

	public void setArrivalOrder(ArrivalOrder arrivalOrder) {
		this.arrivalOrder = arrivalOrder;
	}

	public ArrivalOrderDetail getArrivalOrderDetail() {
		return arrivalOrderDetail;
	}

	public void setArrivalOrderDetail(ArrivalOrderDetail arrivalOrderDetail) {
		this.arrivalOrderDetail = arrivalOrderDetail;
	}
	/**
	 * 采购进货单列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String arrivalOrderListPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		return SUCCESS;
	}
	/**
	 * 采购进货单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showArrivalOrderPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(null!=map){
			if(map.containsKey("isNoPageflag")){
				map.remove("isNoPageflag");
			}
		}
//		String sort = (String)map.get("sort");
//		String order = (String) map.get("order");
//		if(StringUtils.isEmpty(sort) || StringUtils.isEmpty(order)){
//			if(map.containsKey("sort")){
//				map.remove("sort");
//			}
//			if(map.containsKey("order")){
//				map.remove("order");
//			}
//			pageMap.setOrderSql(" businessdate desc , id desc");
//		}
		pageMap.setCondition(map);
		PageData pageData=arrivalOrderService.showArrivalOrderPageList(pageMap);
		
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 用于下游单据引用时，采购进货单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String showArrivalOrderForReferPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=arrivalOrderService.showArrivalOrderPageList(pageMap);
		
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
	public String arrivalOrderPage() throws Exception{
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
	public String arrivalOrderAddPage() throws Exception{
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
		return SUCCESS;
	}
	/**
	 * 添加采购进货单
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="ArrivalOrder-Purchase",type=2)
	public String addArrivalOrder() throws Exception{
		Map map=new HashMap();
		boolean flag=false;
		String addType = request.getParameter("addType");
		String arrivalOrderDetails=request.getParameter("arrivalOrderDetails");
		List<ArrivalOrderDetail> aodList=null;
		if(null!=arrivalOrderDetails && !"".equals(arrivalOrderDetails.trim())){
			aodList=JSONUtils.jsonStrToList(arrivalOrderDetails.trim(),new ArrivalOrderDetail());
			arrivalOrder.setArrivalOrderDetailList(aodList);
		}
		
		if("temp".equals(addType)){
			arrivalOrder.setStatus("1");	//暂存
		}else if("real".equals(addType)){
			arrivalOrder.setStatus("2");
		}else{
			arrivalOrder.setStatus("1");//暂存
		}		
		if("2".equals(arrivalOrder.getStatus())){
			if(null==aodList || aodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写进货的商品信息");
				addJSONObject(map);
				return SUCCESS;
			}
		}

		SysUser sysUser = getSysUser();
		arrivalOrder.setAdduserid(sysUser.getUserid());
		arrivalOrder.setAddusername(sysUser.getName());
		arrivalOrder.setAdddeptid(sysUser.getDepartmentid());
		arrivalOrder.setAdddeptname(sysUser.getDepartmentname());
		arrivalOrder.setAddtime(new Date());
		flag=arrivalOrderService.addArrivalOrderAddDetail(arrivalOrder);
		//判断是否审核
		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit) && flag){
			Map result = arrivalOrderService.auditArrivalOrder(arrivalOrder.getId());
			//addJSONObject(result);
			boolean auditflag = (Boolean) result.get("flag");
			String msg = (String) result.get("msg");
			map.put("auditflag", auditflag);
			map.put("msg", msg);
			logStr="保存并审核采购进货单成功，单据编号："+arrivalOrder.getId();
		}else if(flag){
			logStr="添加采购进货单成功，单据编号："+arrivalOrder.getId();
		}else{
			logStr="添加采购进货单失败，单据编号："+arrivalOrder.getId();
		}
		map.put("flag", flag);
		map.put("backid", arrivalOrder.getId());
		map.put("opertype", "add");
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 采购进货单参照添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-28
	 */
	public String arrivalOrderReferAddPage() throws Exception{
		String referid=request.getParameter("referid");
		SysUser sysUser=getSysUser();
		ArrivalOrder order=arrivalOrderService.showArrivalOrderAndDetailByBillno(referid);
		order.setStatus("2");
		order.setAdduserid(sysUser.getUserid());
		order.setAddusername(sysUser.getName());
		order.setAdddeptid(sysUser.getDepartmentid());
		order.setAdddeptname(sysUser.getDepartmentid());
		order.setAddtime(new Date());
		boolean flag=arrivalOrderService.addArrivalOrderAddDetail(order);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(order.getArrivalOrderDetailList());
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("user", sysUser);
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("arrivalOrder", order);
		request.setAttribute("goodsDataList", jsonStr);
		return SUCCESS;
	}
	
	
	/**
	 * 采购编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String arrivalOrderEditPage() throws Exception{
		String id=request.getParameter("id");
		//true表示分摊过，分摊过的单据不能修改金额
		Boolean updateflag=arrivalOrderService.isArrivalOrderShare(id);
		request.setAttribute("updateflag",updateflag);

		ArrivalOrder aOrder=arrivalOrderService.showArrivalOrderAndDetail(id);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr ="[]";
		if(null==aOrder){
			aOrder=new ArrivalOrder();
		}
		if(null!=aOrder.getArrivalOrderDetailList()){
			jsonStr = JSONUtils.listToJsonStr(aOrder.getArrivalOrderDetailList());
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		SysUser sysUser=getSysUser();
		request.setAttribute("user", sysUser);
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("arrivalOrder", aOrder);
		request.setAttribute("goodsDataList", jsonStr);

		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		
		if(!"1".equals(aOrder.getStatus()) && !"2".equals(aOrder.getStatus())){
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
	@UserOperateLog(key="ArrivalOrder-Purchase",type=3)
	public String editArrivalOrder() throws Exception{
//		boolean lock = isLockEdit("t_purchase_arrivalorder", order.getId()); // 判断锁定并解锁
//		if (!lock) { // 被锁定不能进行修改
//			addJSONObject("lock", true);
//			return SUCCESS;
//		}
		Map map = new HashMap();
		if(StringUtils.isEmpty(arrivalOrder.getId())){
			map.put("flag", false);
			map.put("msg", "未能找到要修改的信息");
			addJSONObject(map);
			return SUCCESS;
		}

		String arrivalOrderDetails=request.getParameter("arrivalOrderDetails");
		List<ArrivalOrderDetail> aodList=null;
		if(null!=arrivalOrderDetails && !"".equals(arrivalOrderDetails.trim())){
			aodList=JSONUtils.jsonStrToList(arrivalOrderDetails.trim(),new ArrivalOrderDetail());
			arrivalOrder.setArrivalOrderDetailList(aodList);
		}
		
		if("2".equals(arrivalOrder.getStatus())){
			if(null==aodList || aodList.size()==0){
				map.put("flag", false);
				map.put("backid", "");
				map.put("msg", "保存状态下，请填写进货的商品信息");
				addJSONObject(map);
				return SUCCESS;
			}
		}
		ArrivalOrder oldaOrder=arrivalOrderService.showArrivalOrderByDataAuth(arrivalOrder.getId());
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
				arrivalOrder.setStatus("2");
			}
		}else{
			arrivalOrder.setStatus(oldaOrder.getStatus());
		}
		boolean flag=false;
		SysUser sysUser = getSysUser();
		arrivalOrder.setModifyuserid(sysUser.getUserid());
		arrivalOrder.setModifyusername(sysUser.getName());
		arrivalOrder.setModifytime(new Date());
		if("1".equals(oldaOrder.getSource())){	
			arrivalOrder.setSupplierid(oldaOrder.getSupplierid());
		}
		//来源类型不作修改
		arrivalOrder.setSource(oldaOrder.getSource());
		
		flag= arrivalOrderService.updateArrivalOrderAddDetail(arrivalOrder);	
		map.clear();

		//判断是否审核
		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit) && flag){
			Map result = arrivalOrderService.auditArrivalOrder(arrivalOrder.getId());
			//addJSONObject(result);
			boolean auditflag = (Boolean) result.get("flag");
			String msg = (String) result.get("msg");
			map.put("auditflag", auditflag);
			map.put("msg", msg);
			logStr="保存并审核采购进货单成功，单据编号："+arrivalOrder.getId();
		}else if(flag){
			logStr="修改采购进货单成功，单据编号："+arrivalOrder.getId();
		}else{
			logStr="修改采购进货单失败，单据编号："+arrivalOrder.getId();
		}
		map.put("backid", arrivalOrder.getId());
		map.put("opertype", "edit");
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}

    /**
     * 批量审核
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="ArrivalOrder-Purchase",type=3)
    public String batchAuditArrivalOrder() throws Exception {
        String ids=request.getParameter("ids");
        String[] idArray = ids.split(",");
        int failNum = 0,successNum = 0;
        Map map = new HashMap();
        for(int i = 0 ; i< idArray.length ; ++ i){
            Map result = arrivalOrderService.auditArrivalOrder(idArray[i]);
            boolean flag = (Boolean) result.get("flag");
            if(flag){
                ++ successNum;
            }else{
                ++ failNum;
            }
        }
        if(successNum == 0){
            map.put("flag",false);
            map.put("failure",failNum);
        }else{
            map.put("flag",true);
            map.put("failure",failNum);
            map.put("success",successNum);
        }
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
	public String arrivalOrderViewPage() throws Exception{
		String id=request.getParameter("id");
		//true表示分摊过，分摊过的单据不能修改金额
		Boolean updateflag=arrivalOrderService.isArrivalOrderShare(id);
		request.setAttribute("updateflag",updateflag);
		ArrivalOrder aOrder=arrivalOrderService.showArrivalOrderAndDetail(id);
		if(null==aOrder){
			aOrder=new ArrivalOrder();
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr="[]";
		if(null!=aOrder.getArrivalOrderDetailList()){
			jsonStr = JSONUtils.listToJsonStr(aOrder.getArrivalOrderDetailList());
		}
		Map<String,Object> fieldMap=getColumnDIYFieldName("t_base_goods_info");
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("buyDept", getBaseDepartMentService().getDeptListByOperType("3"));
		request.setAttribute("gfieldMap", fieldMap);
		request.setAttribute("statusList", statusList);
		request.setAttribute("arrivalOrder", aOrder);
		request.setAttribute("goodsDataList", jsonStr);

		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	
	/**
	 * 删除申请采购申请
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	@UserOperateLog(key="ArrivalOrder-Purchase",type=4)
	public String deleteArrivalOrder() throws Exception{
		String id=request.getParameter("id");

		boolean delFlag = canTableDataDelete("t_purchase_arrivalorder", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag=arrivalOrderService.deleteArrivalOrderAndDetail(id);
		addJSONObject("flag", flag);
		if(flag){
			logStr="删除采购进货单成功，单据编号："+id;
		}else{
			logStr="删除采购进货单失败，单据编号："+id;
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
	@UserOperateLog(key="ArrivalOrder-Purchase",type=0)
	public String auditArrivalOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=arrivalOrderService.auditArrivalOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="审核采购进货单成功，单据编号："+id;
		}else{
			logStr="审核采购进货单失败，单据编号："+id;
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
	@UserOperateLog(key="ArrivalOrder-Purchase",type=0)
	public String oppauditArrivalOrder() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id)){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		Map map=arrivalOrderService.oppauditArrivalOrder(id);
		addJSONObject(map);
		boolean flag = (Boolean) map.get("flag");
		if(flag){
			logStr="反审采购进货单成功，单据编号："+id;
		}else{
			logStr="反审采购进货单失败，单据编号："+id;
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
	public String arrivalOrderDetailAddPage() throws Exception{
		String supplier=request.getParameter("supplierid");
		/*if(null!=supplier && !"".equals(supplier.trim())){
			List goodsList=getGoodsInfoBySupplier(supplier);
			if(null==goodsList || goodsList.size()==0){
				supplier=null;
			}
		}else{
			supplier=null;
		}*/
		request.setAttribute("supplierid", supplier);
		String businessdate=request.getParameter("businessdate");
		request.setAttribute("businessdate", businessdate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, 2);
		request.setAttribute("arrivedate", dateFormat.format(calendar.getTime()));

		Map colMap = getEditAccessColumn("t_purchase_arrivalorder_detail");
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
	 * 采购进货单明细列表编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String arrivalOrderDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_purchase_arrivalorder_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	/**
	 * 有来源采购进货单明细列表编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-6
	 */
	public String arrivalOrderSourceDetailEditPage() throws Exception{
		Map colMap = getEditAccessColumn("t_purchase_arrivalorder_detail");
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
	public String arrivalOrderDetailViewPage() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	 /**
	  * 跳转至采购折扣页面
	  * @author lin_xx
	  * @date 2016/10/17
	  */
	public String showArrivalOrderDiscountAddPage() throws Exception {
		String id = request.getParameter("arrivalorderid");
		request.setAttribute("arrivalorderid",id);
		SysParam discountTypeSysParam = getBaseSysParamService().getSysParam("repartitionType");
		if(null != discountTypeSysParam){
			request.setAttribute("repartitionType",discountTypeSysParam.getPvalue());
		}else{
			request.setAttribute("repartitionType","0");
		}
		return SUCCESS;
	}
	 /**
	  * 添加采购折扣到单价中
	  * @author lin_xx
	  * @date 2016/10/17
	  */
	public String addArrivalOrderDiscount() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		List list = arrivalOrderService.addArrivalOrderDiscount(map);
		addJSONArray(list);
		return SUCCESS;
	}
	/**
	 * 采购进货单来源查询
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-23
	 */
	public String arrivalOrderSourceQueryPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 采购进货单来源结果页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-23
	 */
	public String arrivalOrderSourcePage() throws Exception{		
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
	public String arrivalOrderSourceViewPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 用于采购进货单参照页面，参照上游采购入库单商品明细列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-1
	 */
	public String showArrivalOrderDetailUpReferList() throws Exception{
		String billno=request.getParameter("billno");	//采购入库单编号
		List list=arrivalOrderService.showArrivalOrderDetailUpReferList(billno);
		addJSONArray(list);
		return SUCCESS;
	}
	/**
	 * 用于下游参照采购进货单，显示商品明细列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-12
	 */
	public String showArrivalOrderDetailDownReferList() throws Exception{
		String orderidarrs=request.getParameter("orderidarrs");
		List list=arrivalOrderService.showArrivalOrderDetailDownReferList(orderidarrs);
		addJSONArray(list);
		return SUCCESS;
	}
	/**
	 * 提交采购进货单进流程
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public String submitArrivalOrderProcess() throws Exception{
		String id = request.getParameter("id");
		ArrivalOrder bill = arrivalOrderService.showArrivalOrder(id);
		if(!bill.getStatus().equals("2")){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "采购进货单 "+bill.getId()+" (" + bill.getBusinessdate() + ")";
		boolean flag= arrivalOrderService.submitArrivalOrderProcess(title, user.getUserid(), "purchaseArrivalOrder", id, variables);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 采购进货单明细列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public String showArrivalOrderDetailListByOrderid() throws Exception{
		String orderid=request.getParameter("id");
		List<ArrivalOrderDetail> list=arrivalOrderService.showArrivalOrderDetailListByOrderId(orderid);
		addJSONArray(list);
		return SUCCESS;
	}
	
	
	/**
	 * 采购进货单分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public String exportArrivalOrderData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		map.put("isNoPageflag", "true");
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
		PageData pageData=arrivalOrderService.showArrivalOrderPageList(pageMap);
		ExcelUtils.exportExcel(exportArrivalOrderDataFillter(pageData.getList()), title);
		return SUCCESS;
	}
	
	private List<Map<String, Object>> exportArrivalOrderDataFillter(List<ArrivalOrder> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("supplierid", "供应商编码");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("billno", "来源编号");
		firstMap.put("field01", "含税金额");
		firstMap.put("field02", "未税金额");
		firstMap.put("field03", "税额");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(ArrivalOrder item : list){
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
		return result;
	}
	/**
	 * 修改采购进货单明细备注
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-26
	 */
	public String updateArrivalOrderDetailRemark() throws Exception{
		String detailId=request.getParameter("detailid");
		String remark=request.getParameter("remark");
		String orderid=request.getParameter("orderid");
		Integer iDetailId=-1000;
		if(null!=detailId && !"".equals(detailId.trim()) && CommonUtils.isNumStr(detailId.trim())){
			iDetailId=Integer.parseInt(detailId.trim());
		}
		Map resultMap=new HashMap();
		if(null==iDetailId || iDetailId == -1000){
			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，未能找到明细编号");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(null==remark){
			remark="";
		}
		if(null==orderid || "".equals(orderid.trim())){
			orderid=null;
		}
		ArrivalOrderDetail upDetail=new ArrivalOrderDetail();
		upDetail.setId(iDetailId);
		upDetail.setRemark(remark);
		upDetail.setOrderid(orderid);
		boolean flag=arrivalOrderService.updateArrivalOrderDetailRemark(upDetail);
		resultMap.put("flag", flag);
		resultMap.put("msg", "更新成功");
		addJSONObject(resultMap);
		return SUCCESS;
	}

	/**
	 * 采购金额分摊页面
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 05, 2018
	 */
	public String showArrivalPurchaseSharePage() {
		return SUCCESS;
	}

	/**
	 * 添加采购进货单分摊金额
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 05, 2018
	 */
	public String addPurchaseShareAmount() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		Map resMap=arrivalOrderService.addPurchaseShareAmount(map);
		addJSONObject(resMap);
		return SUCCESS;
	}

	/**
	 * 查看采购费用分摊记录
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public String showArrivalPurchaseShareLogPage() {
		return SUCCESS;
	}

	/**
	 * 获取采购费用分摊记录数据
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public String getPurchaseShareLogDate() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=arrivalOrderService.getPurchaseShareLogDate(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 取消采购运费分摊
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public String cancelArrivalPurchaseShare() {
		String id=request.getParameter("id");
		Map resMap=arrivalOrderService.cancelArrivalPurchaseShare(id);
		addJSONObject(resMap);
		return SUCCESS;
	}
}

