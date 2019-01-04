/**
 * @(#)dispatchBillAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.DispatchBill;
import com.hd.agent.sales.model.DispatchBillDetail;
import com.hd.agent.sales.model.Order;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.system.model.SysParam;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 销售发货通知单action
 * @author zhengziyong
 */
public class DispatchBillAction extends BaseSalesAction {

	private DispatchBill dispatchBill;

	public DispatchBill getDispatchBill() {
		return dispatchBill;
	}

	public void setDispatchBill(DispatchBill dispatchBill) {
		this.dispatchBill = dispatchBill;
	}
	/**
	 * 显示销售发货通知单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 17, 2013
	 */
	public String showDispatchBillAddPage() throws Exception{		

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
	 * 销售发货通知单页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 17, 2013
	 */
	public String dispatchBill() throws Exception{
		String  id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		

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
		return SUCCESS;
	}
	
	/**
	 * 销售发货通知单参照上游单据查询页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 20, 2013
	 */
	public String dispatchBillSourceQueryPage() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 销售发货通知单参照上游单据页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 20, 2013
	 */
	public String dispatchBillSourcePage() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 销售发货通知单商品详细信息添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 27, 2013
	 */
	public String billDetailAddPage() throws Exception{
		String customerId = request.getParameter("cid");
		List list = getBaseSysCodeService().showSysCodeListByType("deliverytype");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, 2);
		Map colMap = getEditAccessColumn("t_sales_dispatchbill_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("customerId", customerId);
		request.setAttribute("list", list);
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		return SUCCESS;
	}
	/**
	 * 销售发货通知单折扣添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public String billDetailDiscountAddPage() throws Exception{
		return "success";
	}
	/**
	 * 销售发货通知单品牌折扣添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 17, 2013
	 */
	public String billDetailBrandDiscountAddPage() throws Exception{
		SysParam discountTypeSysParam = getBaseSysParamService().getSysParam("repartitionType");
		if(null != discountTypeSysParam){
			request.setAttribute("repartitionType",discountTypeSysParam.getPvalue());
		}else{
			request.setAttribute("repartitionType","0");
		}
		return "success";
	}
	/**
	 * 显示销售发货通知单品牌折扣修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 17, 2013
	 */
	public String billDetailBrandDiscountEditPage() throws Exception{
		return "success";
	}
	/**
	 * 折扣修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public String billDetailDiscountEditPage() throws Exception{
		return "success";
	}
	/**
	 * 通过含税金额计算无税金额
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public String computeDispatchBillDiscountTax() throws Exception{
		String goodsid = request.getParameter("goodsid");
        String brandid = request.getParameter("brandid");
		String taxamountStr = request.getParameter("taxamount");
		BigDecimal taxamount = new BigDecimal(0);
		if(null!=taxamountStr){
			taxamount = new BigDecimal(taxamountStr);
		}
		Map map = new HashMap();
        BigDecimal notaxamount = BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(goodsid)){
            GoodsInfo goodsInfo = getBaseGoodsService().showGoodsInfo(goodsid);
            if(null!=goodsInfo){
                notaxamount = getNotaxAmountByTaxAmount(taxamount, goodsInfo.getDefaulttaxtype());
                TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype());
                if(null!=taxType){
                    map.put("taxtype", goodsInfo.getDefaulttaxtype());
                    map.put("taxtypename", taxType.getName());
                }
            }else{
                notaxamount = getNotaxAmountByTaxAmount(taxamount, null);
            }
        }else if(StringUtils.isNotEmpty(brandid)){
            Brand brand = getBaseGoodsService().getBrandInfo(brandid);
            if(null != brand){
                notaxamount = getNotaxAmountByTaxAmount(taxamount, brand.getDefaulttaxtype());
                TaxType taxType = getTaxType(brand.getDefaulttaxtype());
                if(null!=taxType){
                    map.put("taxtype", brand.getDefaulttaxtype());
                    map.put("taxtypename", taxType.getName());
                }
            }else{
                notaxamount = getNotaxAmountByTaxAmount(taxamount, null);
            }
        }

		BigDecimal tax = taxamount.subtract(notaxamount);
		map.put("notaxamount", notaxamount);
		map.put("tax", tax);
		
		addJSONObject(map);
		return "success";
	}
	/**
	 * 销售发货通知单商品详细信息修改页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 27, 2013
	 */
	public String billDetailEditPage() throws Exception{
		String customerId = request.getParameter("cid");
		List list = getBaseSysCodeService().showSysCodeListByType("deliverytype");
		request.setAttribute("list", list);
		Map colMap = getEditAccessColumn("t_sales_dispatchbill_detail");
		request.setAttribute("colMap", colMap);
		request.setAttribute("customerId", customerId);
		
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null!=goodsInfo){
			request.setAttribute("isbatch", goodsInfo.getIsbatch());
		}else{
			request.setAttribute("isbatch", "0");
		}
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	
	/**
	 * 销售发货通知单添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 17, 2013
	 */
	public String dispatchBillAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_sales_dispatchbill"));
		request.setAttribute("userName", getSysUser().getName());
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		return SUCCESS;
	}
	
	/**
	 * 销售发货通知单参照页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	public String dispatchBillReferPage() throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			return INPUT;
		}
		Order order = salesOrderService.getOrder(id);
		String jsonStr = JSONUtils.listToJsonStr(order.getOrderDetailList());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("order", order);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_sales_dispatchbill"));
		request.setAttribute("userName", getSysUser().getName());
		
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		return SUCCESS;
	}
	
	/**
	 * 添加销售发货通知单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 18, 2013
	 */
	@UserOperateLog(key="DispatchBill",type=2)
	public String addDispatchBill() throws Exception{
		if (isAutoCreate("t_sales_dispatchbill")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(dispatchBill, "t_sales_dispatchbill");
			dispatchBill.setId(id);
		}
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){ //暂存
			dispatchBill.setStatus("1");
		}
		else if("real".equals(addType)){ //保存
			dispatchBill.setStatus("2");
		}
		String billDetailJson = request.getParameter("goodsjson");
		List<DispatchBillDetail> billDetailList = JSONUtils.jsonStrToList(billDetailJson, new DispatchBillDetail());
		if("1".equals(dispatchBill.getSource())){ //1为来源于销售订单
			List<OrderDetail> orderDetailList = JSONUtils.jsonStrToList(billDetailJson, new OrderDetail());
			for(DispatchBillDetail billDetail : billDetailList){
				for(OrderDetail orderDetail : orderDetailList){
					if(billDetail.getId().equals(orderDetail.getId())){
						billDetail.setBillno(orderDetail.getOrderid());
						billDetail.setBilldetailno(orderDetail.getId());
						break;
					}
				}
			}
		}
		dispatchBill.setBillDetailList(billDetailList);
		boolean flag = salesDispatchBillService.addDispatchBill(dispatchBill);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit)){
			Map result = salesDispatchBillService.auditDispatchBill("1", dispatchBill.getId());
			if(null!=result && result.containsKey("flag")){
				boolean auditflag = (Boolean) result.get("flag");
				String msg = (String) result.get("msg");
				String billId = (String) result.get("billId");
				map.put("auditflag", auditflag);
				map.put("msg", msg);
				map.put("billId", billId);
			}
		}
		map.put("flag", flag);
		map.put("backid", dispatchBill.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("销售发货通知单新增 编号："+dispatchBill.getId(), flag);
		return SUCCESS;
	}
	
	/**
	 * 参照销售订单生成发货单时，将订单商品明细转为发货单商品明细
	 * @param orderDetailList
	 * @return
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	private List<DispatchBillDetail> orderToBillList(List<OrderDetail> orderDetailList) {
		List<DispatchBillDetail> billDetailList = new ArrayList<DispatchBillDetail>();
		for(OrderDetail orderDetail : orderDetailList){
			DispatchBillDetail detail = new DispatchBillDetail();
			
		}
		return null;
	}

	public String dispatchBillEditPage() throws Exception{
		String id = request.getParameter("id");
		DispatchBill bill = salesDispatchBillService.getDispatchBill(id);
		
		//系统参数 控制销售订单 仓库是否必填
		String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
		if(StringUtils.isEmpty(isOrderStorageSelect)){
			isOrderStorageSelect = "0";
		}
		request.setAttribute("isOrderStorageSelect", isOrderStorageSelect);
		
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);

		//发货打印是否需要发货单审核通过才能打印
		String fHPrintAfterSaleOutAudit=getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");		
		if(null==fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())){
			fHPrintAfterSaleOutAudit="0";
		}
		request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());
		
		if(null!=bill ){	
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			String jsonStr = JSONUtils.listToJsonStr(bill.getBillDetailList());
			request.setAttribute("settletype", getSettlementListData());
			request.setAttribute("paytype", getPaymentListData());
			request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
			request.setAttribute("goodsJson", jsonStr);
			request.setAttribute("statusList", statusList);
			request.setAttribute("bill", bill);
			if("2".equals(bill.getStatus()) || "6".equals(bill.getStatus())){
				//加锁
				lockData("t_sales_order", bill.getId());
				return "editSuccess";
			}else{
				return "viewSuccess";
			}
		}else{
			return "viewSuccess";
		}
		
	}
	@UserOperateLog(key="DispatchBill",type=3)
	public String updateDispatchBill() throws Exception{
		boolean lock = isLockEdit("t_sales_dispatchbill", dispatchBill.getId()); // 判断锁定并解锁
		if (!lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			return SUCCESS;
		}
		String addType = request.getParameter("addType");
		if("1".equals(dispatchBill.getStatus())){
			if("real".equals(addType)){
				dispatchBill.setStatus("2");
			}
		}
		SysUser sysUser = getSysUser();
		dispatchBill.setModifyuserid(sysUser.getUserid());
		dispatchBill.setModifyusername(sysUser.getName());
		String billDetailJson = request.getParameter("goodsjson");
		List<DispatchBillDetail> billDetailList = JSONUtils.jsonStrToList(billDetailJson, new DispatchBillDetail());
		dispatchBill.setBillDetailList(billDetailList);
		boolean flag = salesDispatchBillService.updateDispatchBill(dispatchBill);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit) && flag){
			Map result = salesDispatchBillService.auditDispatchBill("1", dispatchBill.getId());
			if(null!=result && result.containsKey("flag")){
				boolean auditflag = (Boolean) result.get("flag");
				if(result.containsKey("closeFlag")){
					boolean closeFlag = (Boolean) result.get("closeFlag");
					map.put("closeFlag", closeFlag);
				}
				String msg = (String) result.get("msg");
				String billId = (String) result.get("billId");
				map.put("auditflag", auditflag);
				map.put("msg", msg);
				map.put("billId", billId);
				
				map.put("id", dispatchBill.getId());
			}
			addLog("销售发货通知单保存审核 编号："+dispatchBill.getId(), result);
		}else{
			addLog("销售发货通知单修改 编号："+dispatchBill.getId(), flag);
		}
		map.put("flag", flag);
		map.put("backid", dispatchBill.getId());
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 销售发货通知单查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 18, 2013
	 */
	public String dispatchBillViewPage() throws Exception{
		String id = request.getParameter("id");
		DispatchBill bill = salesDispatchBillService.getDispatchBill(id);
		if(bill == null){
			return SUCCESS;
		}

		Map queryMap=new HashMap();
		int canprint=0;
		String printlimit=getPrintLimitInfo();
		
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(bill.getBillDetailList());
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("statusList", statusList);
		request.setAttribute("bill", bill);
		
		request.setAttribute("printlimit", printlimit);
		

		//发货打印是否需要发货单审核通过才能打印
		String fHPrintAfterSaleOutAudit=getSysParamValue("saleOrderFHPrintAfterSaleOutAudit");		
		if(null==fHPrintAfterSaleOutAudit || "".equals(fHPrintAfterSaleOutAudit.trim())){
			fHPrintAfterSaleOutAudit="0";
		}
		request.setAttribute("fHPrintAfterSaleOutAudit", fHPrintAfterSaleOutAudit.trim());
		
		return SUCCESS;
	}
	
	/**
	 * 销售发货通知单列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 18, 2013
	 */
	public String dispatchBillListPage() throws Exception{
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		String printlimit=getPrintLimitInfo();
		printlimit=(!"1".equals(printlimit)?"0":printlimit);
		
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
		return SUCCESS;
	}
	
	/**
	 * 获取销售发货通知单列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 18, 2013
	 */
	public String getDispatchBillList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("showcanprint", "1");

		String printlimit=getPrintLimitInfo();
		///列表中打印提示时使用，页面上用printlimit判断是否可以打印，暂存在列表中
		map.put("printlimit",printlimit);
		///列表查询使用，当状态为100或101
		map.put("ckprintlimit", printlimit);
		
		/*
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
		*/
		pageMap.setCondition(map);
		PageData pageData = salesDispatchBillService.getDispatchBillData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 通过销售发货通知单获取明细列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public String getDetailListByDispatchBill() throws Exception{
		String id = request.getParameter("id");
		List list = salesDispatchBillService.getDetailListByBill(id);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 删除发货通知单信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 17, 2013
	 */
	@UserOperateLog(key="DispatchBill",type=4)
	public String deleteDispatchBill() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_sales_dispatchbill", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = salesDispatchBillService.deleteDispatchBill(id);
		addJSONObject("flag", flag);
		addLog("销售发货通知单删除 编号："+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 审核发货通知单信息，并回写订单信息
	 * 
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	@UserOperateLog(key="DispatchBill",type=3)
	public String auditDispatchBill() throws Exception{
		String type = request.getParameter("type"); //1为审核2为反审
		String id = request.getParameter("id");
		Map result = salesDispatchBillService.auditDispatchBill(type, id);
		addJSONObject(result);
		if("1".equals(type)){
			addLog("销售发货通知单审核 编号："+id, result);
		}else{
			addLog("销售发货通知单反审 编号："+id, result);
		}
		return SUCCESS;
	}
	/**
	 * 发货通知单超级审核 
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月27日
	 */
	@UserOperateLog(key="DispatchBill",type=3)
	public String auditSupperDispatchBill() throws Exception{
		String id = request.getParameter("id");
		Map result = salesDispatchBillService.auditSupperDispatchBill(id);
		addJSONObject(result);
		addLog("销售发货通知单超级审核 编号："+id, result);
		return SUCCESS;
	}
	/**
	 * 提交销售发货通知单进流程
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	@UserOperateLog(key="DispatchBill",type=3)
	public String submitDispatchBillProcess() throws Exception{
		String id = request.getParameter("id");
		DispatchBill bill = salesDispatchBillService.getDispatchBill(id);
		if(!bill.getStatus().equals("2")){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "销售发货通知单 "+bill.getId()+" (" + bill.getBusinessdate() + ")";
		Map map = salesDispatchBillService.submitDispatchBillProcess(title, user.getUserid(), "salesDispatchBill", id, variables);
		addJSONObject(map);
		addLog("销售发货通知单提交工作流 编号："+id, map);
		return SUCCESS;
	}
	/**
	 * 配置库存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	@UserOperateLog(key="DispatchBill",type=3)
	public String dispatchBillDeploy() throws Exception{
		String id = request.getParameter("id");
		Map map = salesDispatchBillService.dispatchBillDeploy(id);
		addJSONObject(map);
		addLog("销售发货通知单配置库存 编号："+id, true);
		return "success";
	}
	/**
	 * 显示销售发货通知单库存配置页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	public String dispatchBillDeployPage() throws Exception{
		String id = request.getParameter("id");
		//1追加 2替换
		String barcodeflag = request.getParameter("barcodeflag");
        String deploy = request.getParameter("deploy");
		DispatchBill bill = salesDispatchBillService.getDispatchBillDeploy(id,barcodeflag,deploy);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(bill.getBillDetailList());
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("statusList", statusList);
		request.setAttribute("bill", bill);
		
		//系统参数 控制销售订单 仓库是否必填
		String isOrderStorageSelect = getSysParamValue("IsOrderStorageSelect");
		if(StringUtils.isEmpty(isOrderStorageSelect)){
			isOrderStorageSelect = "0";
		}
		request.setAttribute("isOrderStorageSelect", isOrderStorageSelect);
		return SUCCESS;
	}
}

