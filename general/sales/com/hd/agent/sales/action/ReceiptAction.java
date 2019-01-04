/**
 * @(#)ReceiptAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.service.ISalesInvoiceService;
import com.hd.agent.account.service.ISalesStatementService;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.service.ISalesOutService;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IStorageSaleOutService;
import com.hd.agent.system.model.SysParam;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 
 * @author zhengziyong
 */
public class ReceiptAction extends BaseSalesAction {

	private Receipt receipt;
	private IStorageSaleOutService storageSaleOutService;
	private ISalesOutService salesOutService;
    private ISalesStatementService salesStatementService;

    private ISalesInvoiceService salesInvoiceService;

    public ISalesInvoiceService getSalesInvoiceService() {
        return salesInvoiceService;
    }

    public void setSalesInvoiceService(ISalesInvoiceService salesInvoiceService) {
        this.salesInvoiceService = salesInvoiceService;
    }

    public ISalesStatementService getSalesStatementService() {
        return salesStatementService;
    }

    public void setSalesStatementService(ISalesStatementService salesStatementService) {
        this.salesStatementService = salesStatementService;
    }
	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public IStorageSaleOutService getStorageSaleOutService() {
		return storageSaleOutService;
	}

	public void setStorageSaleOutService(
			IStorageSaleOutService storageSaleOutService) {
		this.storageSaleOutService = storageSaleOutService;
	}
	
	public ISalesOutService getSalesOutService() {
		return salesOutService;
	}

	public void setSalesOutService(ISalesOutService salesOutService) {
		this.salesOutService = salesOutService;
	}

	/**
	 * 显示销售发货回单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 17, 2013
	 */
	public String showReceiptAddPage() throws Exception{
		return "success";
	}
	/**
	 * 销售发货回单页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 30, 2013
	 */
	public String receiptPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		//销售发货回单直退操作模式1回单关联退货通知单2回单直接改数量
		String receiptAndRejectType = getSysParamValue("ReceiptAndRejectType");
		if(StringUtils.isEmpty(receiptAndRejectType)){
			receiptAndRejectType = "1";
		}
		request.setAttribute("receiptAndRejectType", receiptAndRejectType);
        //1直接核销0不直接核销
        String isSalesReceiptDirectWriteoff = getSysParamValue("IsSalesReceiptDirectWriteoff");
        if(StringUtils.isEmpty(isSalesReceiptDirectWriteoff)){
            isSalesReceiptDirectWriteoff = "0";
        }
        request.setAttribute("isSalesReceiptDirectWriteoff",isSalesReceiptDirectWriteoff);
        Receipt receipt1 = salesReceiptService.getReceipt(id);
        if(null != receipt1){
            request.setAttribute("customerid",receipt1.getCustomerid());
        }
		return SUCCESS;
	}
	/**
	 * 销售发货回单页面(没有可操作按钮)
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月20日
	 */
	public String receiptUnCheckPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return "success";
	}
	/**
	 * 销售发货回单明细添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public String receiptDetailAddPage() throws Exception{
		String customerId = request.getParameter("cid");SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, 2);
		request.setAttribute("customerId", customerId);
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	
	/**
	 * 销售发货回单明细修改页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public String receiptDetailEditPage() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
	
	/**
	 * 参照上游单据查询页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public String receiptSourceQueryPage() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 参照上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public String receiptSourcePage() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 销售发货回单添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 30, 2013
	 */
	public String receiptAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_sales_receipt"));
		request.setAttribute("userName", getSysUser().getName());
		
		//销售部门
		List deptList = getDeptListByOperationType("4");
		request.setAttribute("deptList", deptList);
		//客户业务员列表
		List saleuserList = getPersListByOperationType("1");
		request.setAttribute("saleuserList", saleuserList);
		//结算方式
		List settletype = getSettlementListData();
		request.setAttribute("settletype", settletype);
		List paytypeList = getPaymentListData();
		request.setAttribute("paytypeList", paytypeList);
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		return SUCCESS;
	}
	
	/**
	 * 销售回单参照页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	@UserOperateLog(key="Receipt",type=2)
	public String addReceiptRefer() throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			return INPUT;
		}
		Map map = storageSaleOutService.getSaleOutInfoWithoutDiscount(id);
		Saleout saleout = (Saleout)map.get("saleOut");
		List dataList = (List<SaleoutDetail>)map.get("saleOutDetail");
		String receiptid = salesOutService.addReceiptAuto(saleout, dataList);
		if(null!=receiptid){
			storageSaleOutService.updateSaleOutRefer(saleout.getId(), "1");
		}
		addLog("销售发货回单参照上游单据生成 编号："+receiptid, map);
		Receipt receipt = salesReceiptService.getReceipt(receiptid);
		if(receipt == null){
			return "viewSuccess";
		}else{
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			String jsonStr = JSONUtils.listToJsonStr(receipt.getReceiptDetailList());
			request.setAttribute("receipt", receipt);
			request.setAttribute("goodsJson", jsonStr);
			request.setAttribute("status", statusList);
			//客户联系人
			List handerList = getBaseFilesService().getContacterByCustomerid(receipt.getCustomerid());
			request.setAttribute("handerList", handerList);
			//销售部门
			List deptList = getDeptListByOperationType("4");
			request.setAttribute("deptList", deptList);
			//客户业务员列表
			List saleuserList = getPersListByOperationType("1");
			request.setAttribute("saleuserList", saleuserList);
			//结算方式
			List settletype = getSettlementListData();
			request.setAttribute("settletype", settletype);
			List paytypeList = getPaymentListData();
			request.setAttribute("paytypeList", paytypeList);
			List storageList = getStorageInfoAllList();
			request.setAttribute("storageList", storageList);
			//字段权限
			Map colMap = getEditAccessColumn("t_sales_receipt_detail");
			request.setAttribute("colMap", colMap);
			if("2".equals(receipt.getStatus()) || "6".equals(receipt.getStatus())){
				return "editSuccess";
			}else{
				return "viewSuccess";
			}
		}
	}
	
	private List<ReceiptDetail> getReceiptDetailFromSaleoutDetail(List<SaleoutDetail> list) throws Exception {
		List<ReceiptDetail> billDetailList = new ArrayList<ReceiptDetail>();
		for(SaleoutDetail saleoutDetail : list){
			ReceiptDetail receiptDetail = new ReceiptDetail();
			receiptDetail.setGoodsid(saleoutDetail.getGoodsid());
			receiptDetail.setGroupid(saleoutDetail.getGroupid());
			receiptDetail.setDeliverytype(saleoutDetail.getDeliverytype());
			receiptDetail.setGoodsInfo(saleoutDetail.getGoodsInfo());
			receiptDetail.setIsdiscount(saleoutDetail.getIsdiscount());
			receiptDetail.setIsbranddiscount(saleoutDetail.getIsbranddiscount());
			receiptDetail.setUnitid(saleoutDetail.getUnitid());
			receiptDetail.setUnitname(saleoutDetail.getUnitname());
			receiptDetail.setUnitnum(saleoutDetail.getUnitnum());
			receiptDetail.setReceiptnum(saleoutDetail.getUnitnum());
			receiptDetail.setAuxnum(saleoutDetail.getAuxnum());
			receiptDetail.setAuxremainder(saleoutDetail.getAuxremainder());
			receiptDetail.setAuxnumdetail(saleoutDetail.getAuxnumdetail());
			receiptDetail.setAuxunitid(saleoutDetail.getAuxunitid());
			receiptDetail.setAuxunitname(saleoutDetail.getAuxunitname());
			receiptDetail.setBatchno(saleoutDetail.getBatchno());
			receiptDetail.setRemark(saleoutDetail.getRemark());
			receiptDetail.setTax(saleoutDetail.getTax());
			receiptDetail.setTaxamount(saleoutDetail.getTaxamount());
			receiptDetail.setReceipttaxamount(saleoutDetail.getTaxamount());
			receiptDetail.setTaxprice(saleoutDetail.getTaxprice());
			receiptDetail.setInittaxprice(saleoutDetail.getTaxprice());
			receiptDetail.setNotaxamount(saleoutDetail.getNotaxamount());
			receiptDetail.setReceiptnotaxamount(saleoutDetail.getNotaxamount());
			receiptDetail.setNotaxprice(saleoutDetail.getNotaxprice());
			receiptDetail.setTaxtype(saleoutDetail.getTaxtype());
			receiptDetail.setTaxtypename(saleoutDetail.getTaxtypename());
			receiptDetail.setBillno(saleoutDetail.getSaleoutid());
			receiptDetail.setBilldetailno(saleoutDetail.getId().toString());
			receiptDetail.setField01(saleoutDetail.getField01());
			receiptDetail.setField02(saleoutDetail.getField02());
			receiptDetail.setField03(saleoutDetail.getField03());
			receiptDetail.setField04(saleoutDetail.getField04());
			receiptDetail.setField05(saleoutDetail.getField05());
			receiptDetail.setField06(saleoutDetail.getField06());
			receiptDetail.setField07(saleoutDetail.getField07());
			receiptDetail.setField08(saleoutDetail.getField08());
			
			receiptDetail.setDiscountamount(saleoutDetail.getDiscountamount());
			receiptDetail.setCostprice(saleoutDetail.getCostprice());
			receiptDetail.setSeq(saleoutDetail.getSeq());
			
			receiptDetail.setSummarybatchid(saleoutDetail.getSummarybatchid());
            receiptDetail.setStoragelocationid(saleoutDetail.getStoragelocationid());
            receiptDetail.setBatchno(saleoutDetail.getBatchno());
            receiptDetail.setProduceddate(saleoutDetail.getProduceddate());
            receiptDetail.setDeadline(saleoutDetail.getDeadline());
            
			billDetailList.add(receiptDetail);
		}
		return billDetailList;
	}

	/**
	 * 添加销售发货回单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 30, 2013
	 */
	@UserOperateLog(key="Receipt",type=2)
	public String addReceipt() throws Exception{
		if (isAutoCreate("t_sales_receipt")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(receipt, "t_sales_receipt");
			receipt.setId(id);
		}
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){ //暂存
			receipt.setStatus("1");
		}
		else if("real".equals(addType)){ //保存
			receipt.setStatus("2");
		}
		receipt.setSource("0");
		String orderDetailJson = request.getParameter("goodsjson");
		List<ReceiptDetail> receiptDetailList = JSONUtils.jsonStrToList(orderDetailJson, new ReceiptDetail());
		receipt.setReceiptDetailList(receiptDetailList);
		boolean flag = salesReceiptService.addReceipt(receipt);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("backid", receipt.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("销售发货回单新增 编号："+receipt.getId(), map);
		return SUCCESS;
	}
	
	/**
	 * 销售发货回单修改页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public String receiptEditPage() throws Exception{

		String printlimit = getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);

		String id = request.getParameter("id");
		Receipt receipt = salesReceiptService.getReceipt(id);
		if(receipt == null){
			request.setAttribute("goodsJson", "[]");
			return "viewSuccess";
		}else{
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			String jsonStr = JSONUtils.listToJsonStr(receipt.getReceiptDetailList());
			request.setAttribute("receipt", receipt);
			request.setAttribute("goodsJson", jsonStr);
			request.setAttribute("status", statusList);
			//销售发货回单直退操作模式1回单关联退货通知单2回单直接改数量
			String receiptAndRejectType = getSysParamValue("ReceiptAndRejectType");
			if(StringUtils.isEmpty(receiptAndRejectType)){
				receiptAndRejectType = "1";
			}
			request.setAttribute("receiptAndRejectType", receiptAndRejectType);
			if("2".equals(receipt.getStatus()) || "6".equals(receipt.getStatus())){
				return "editSuccess";
			}else{
				return "viewSuccess";
			}
		}
	}
	
	/**
	 * 修改销售发货回单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	@UserOperateLog(key="Receipt",type=3)
	public String updateReceipt() throws Exception{
		String addType = request.getParameter("addType");
		if("1".equals(receipt.getStatus())){
			if("real".equals(addType)){
				receipt.setStatus("2");
			}
		}
		SysUser sysUser = getSysUser();
		receipt.setModifyuserid(sysUser.getUserid());
		receipt.setModifyusername(sysUser.getName());
		String orderDetailJson = request.getParameter("goodsjson");
		List<ReceiptDetail> receiptDetailList = JSONUtils.jsonStrToList(orderDetailJson, new ReceiptDetail());
		receipt.setReceiptDetailList(receiptDetailList);
		boolean flag = salesReceiptService.updateReceipt(receipt);
		Map map = new HashMap();
		//判断是否审核
		String saveaudit = request.getParameter("saveaudit");
		if("saveaudit".equals(saveaudit) && flag){
			Map result = salesReceiptService.auditReceipt("1", receipt.getId());
			if(null!=result && result.containsKey("flag")){
				boolean auditflag = (Boolean) result.get("flag");
				map.put("auditflag", auditflag);
				map.put("msg", result.get("msg"));
				map.put("billId", result.get("billId"));
			}
			addLog("销售发货回单保存审核 编号："+receipt.getId(), result);
		}else{
			addLog("销售发货回单修改 编号："+receipt.getId(), flag);
		}
		map.put("flag", flag);
		map.put("backid", receipt.getId());
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 销售发货回单查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public String receiptViewPage() throws Exception{

		String printlimit = getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);

		String id = request.getParameter("id");
		Receipt receipt = salesReceiptService.getReceipt(id);
		if(receipt == null){
			return SUCCESS;
		}
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(receipt.getReceiptDetailList());
		request.setAttribute("receipt", receipt);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("status", statusList);
		return SUCCESS;
	}
	
	/**
	 * 销售发货回单列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public String receiptListPage() throws Exception{
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		return SUCCESS;
	}
	/**
	 * 销售发货回单未验收列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月20日
	 */
	public String receiptListUnAuditPage() throws Exception{
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		return "success";
	}
	/**
	 * 销售发货回单列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public String getReceiptList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReceiptService.getReceiptData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 通过回单编号获取明细列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public String getDetailListByReceipt() throws Exception{
		String id = request.getParameter("id");
		List list = salesReceiptService.getDetailListByReceipt(id);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 删除销售发货回单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	@UserOperateLog(key="Receipt",type=4)
	public String deleteReceipt() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_sales_receipt", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = salesReceiptService.deleteReceipt(id);
		addJSONObject("flag", flag);
		addLog("销售发货回单保存删除 编号："+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 审核或发审发货回单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	@UserOperateLog(key="Receipt",type=3)
	public String auditReceipt() throws Exception{
		String type = request.getParameter("type"); //1为审核2为反审
		String id = request.getParameter("id");
		Map result = salesReceiptService.auditReceipt(type, id);
		addJSONObject(result);
		if("1".equals(type)){
			addLog("销售发货回单审核 编号："+id, result);
		}else{
			addLog("销售发货回单反审 编号："+id, result);
		}
		return SUCCESS;
	}
	
	/**
	 * 批量审核发货回单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 4, 2014
	 */
	@UserOperateLog(key="Receipt",type=3)
	public String auditMultiReceipt() throws Exception{
		String ids = request.getParameter("ids");
		Map map = salesReceiptService.auditMultiReceipt(ids);
		addJSONObject(map);
		addLog("销售发货回单批量审核 编号："+ids, map);
		return SUCCESS;
	}
	
	/**
	 * 提交工作流
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 29, 2013
	 */
	@UserOperateLog(key="Receipt",type=3)
	public String submitReceiptProcess() throws Exception{
		String id = request.getParameter("id");
		Receipt receipt = salesReceiptService.getReceipt(id);
		if(!receipt.getStatus().equals("2")){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "销售回单 "+receipt.getId()+" (" + receipt.getBusinessdate() + ")";
		boolean flag = salesReceiptService.submitReceiptProcess(title, user.getUserid(), "salesReceipt", id, variables);
		addJSONObject("flag", flag);
		addLog("销售发货回单提交工作流 编号："+id, flag);
		return SUCCESS;
	}
	/**
	 * 修改单价后，重新计算金额
	 * 计算公司 含税金额 = 单价*数量-折扣金额
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 25, 2013
	 */
	public String countReceiptTaxamount() throws Exception{
		String billid = request.getParameter("billid");
		String detailid = request.getParameter("detailid");
		String taxpriceStr = request.getParameter("taxprice");
		
		ReceiptDetail receiptDetail = salesReceiptService.getReceiptDetail(billid, detailid);
		if(null!=receiptDetail){
			//获取修改后单价 单价为null或者为空时，取初始化单价
			BigDecimal taxprice =  null;
			if(null!=taxpriceStr && !"".equals(taxpriceStr)){
				taxprice = new BigDecimal(taxpriceStr);
			}else{
				taxprice = receiptDetail.getInittaxprice();
			}
//			//含税金额 = 单价*数量-折扣金额
//			BigDecimal taxamount = taxprice.multiply(receiptDetail.getUnitnum()).subtract(receiptDetail.getDiscountamount());
			BigDecimal receipttaxamount = taxprice.multiply(receiptDetail.getReceiptnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
//			//无税金额
//			BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, receiptDetail.getTaxtype());
			Map map = new HashMap();
//			map.put("taxamount", taxamount);
//			map.put("notaxamount", notaxamount);
			map.put("receipttaxamount", receipttaxamount);
			
			GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail.getGoodsid());
			if(null!=goodsInfo){
				BigDecimal boxprice = goodsInfo.getBoxnum().multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				map.put("boxprice", boxprice);
			}
			addJSONObject(map);
		}
		return "success";
	}
	/**
	 * 取消回单验收
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 7, 2013
	 */
	@UserOperateLog(key="Receipt",type=3)
	public String receiptCancelCheck() throws Exception{
		String id = request.getParameter("id");
		Receipt receipt = salesReceiptService.getReceipt(id);
		boolean flag = false;
		if(null!=receipt && "2".equals(receipt.getStatus())){
			String billno = receipt.getBillno();
			if(null!=billno){
				String[] sourceids = billno.split(",");
				List<ReceiptDetail> datalist = new ArrayList<ReceiptDetail>();
				for(String sourceid : sourceids){
					Map map = storageSaleOutService.getSaleOutInfoWithoutDiscount(sourceid);
					Saleout saleout = (Saleout)map.get("saleOut");
					List<ReceiptDetail> detailList = getReceiptDetailFromSaleoutDetail((List<SaleoutDetail>)map.get("saleOutDetail"));
					datalist.addAll(detailList);
				}
				flag = salesReceiptService.receiptCancelCheck(id,datalist);
			}
			
		}
		addJSONObject("flag", flag);
		addLog("销售发货回单取消验收 编号："+id, flag);
		return "success";
	}
	/**
	 * 显示回单与销售退货通知单（直退）关联列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 26, 2013
	 */
	public String receiptRelationRejectBillPage() throws Exception{
		String id = request.getParameter("id");
		Map map = salesReceiptService.getRejectListByReceiptid(id);
		if(null!=map){
			List list = (List) map.get("list");
			String jsonStr = JSONUtils.listToJsonStr(list);
			request.setAttribute("jsonStr", jsonStr);
			request.setAttribute("id", id);
			List detailList = (List) map.get("detailList");
			String jsonStr1 = JSONUtils.listToJsonStr(detailList);
			request.setAttribute("detailListStr", jsonStr1);
		}
		return "success";
	}
	/**
	 * 回单关联销售退货通知单 更新回单客户接收数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 26, 2013
	 */
	@UserOperateLog(key="Receipt",type=3)
	public String receiptRelationRejectBill() throws Exception{
		String receiptid = request.getParameter("receiptid");
		String rejectbillid = request.getParameter("rejectbillid");
		Map map = salesReceiptService.updateReceiptRelationRejectBillAgain(receiptid, rejectbillid);
		addJSONObject(map);
		addLog("销售发货回单关联销售退货通知单 回单编号："+receiptid+",退货通知单编号:"+rejectbillid, map);
		return "success";
	}

	/**
	 * 显示回单与销售退货通知单（直退）关联列表
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Oct 26, 2013
	 */
	public String showReceiptRejectBillIdPage() throws Exception{
		String id = request.getParameter("id");
		List<RejectBill> list = salesReceiptService.getReceiptRejectBillList(id);
		request.setAttribute("id", id);
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("jsonStr", jsonStr);
		return "success";
	}


	/**
	 * 退货通知单的数量大于发货回单的数量
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 8, 2014
	 */
	public String rejectNumBigerThanReceiptNum()throws Exception{
		String receiptid = request.getParameter("receiptid");
		String rejectbillid = request.getParameter("rejectbillid");
		Map map = salesReceiptService.rejectNumBigerThanReceiptNum(receiptid, rejectbillid);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 获取上游发货单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 11, 2013
	 */
	public String showUpperSaleoutList() throws Exception{
		String saleorderid = request.getParameter("saleorderid");
		List list = storageSaleOutService.showSaleOutListBySaleorderid(saleorderid);
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("jsonList", jsonStr);
		return "success";
	}

    /**
     * 导出销售发货回单     多日未验收回单导出
     * @throws Exception
     */
    public void exportReceiptList() throws  Exception{
        Map queryMap = CommonUtils.changeMap(request.getParameterMap());
        String title = "";
        if(queryMap.containsKey("excelTitle")){
            title = queryMap.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        queryMap.put("isPageLimit",true);
        pageMap.setCondition(queryMap);
        PageData pageData = salesReceiptService.getReceiptData(pageMap);

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("saleorderid", "销售订单编号");
		firstMap.put("sourceid","客户单号");
		firstMap.put("deliveryid","配送单号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编号");
        firstMap.put("customername", "客户名称");
        firstMap.put("accounttypename", "账期类型");
		firstMap.put("salesareaname", "销售区域");
        firstMap.put("salesdeptname", "销售部门");
        firstMap.put("salesusername", "客户业务员");
        firstMap.put("totaltaxamount", "发货金额");
        firstMap.put("totalreceipttaxamount", "应收金额");
        firstMap.put("duefromdate", "应收日期");
        firstMap.put("canceldate", "核销日期");
        firstMap.put("status", "状态");
        firstMap.put("isinvoice", "抽单状态");
        firstMap.put("isinvoicebill", "开票状态");
		firstMap.put("billno", "来源编号");
        firstMap.put("indoorusername", "销售内勤");
        firstMap.put("addusername", "制单人");
        firstMap.put("addtime", "制单时间");
        firstMap.put("auditusername", "审核人");
        firstMap.put("audittime", "审核时间");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        //数据转换，list专程符合excel导出的数据格式
        List<Receipt> list = pageData.getList();
        if(list.size() != 0){
            for(Receipt receipt : list){
                if("2".equals(receipt.getStatus())){
                    receipt.setStatus("保存");
                }else if("3".equals(receipt.getStatus())){
                    receipt.setStatus("审核通过");
                }else if("4".equals(receipt.getStatus())){
                    receipt.setStatus("关闭");
                }
                if("0".equals(receipt.getIsinvoice())){
                    receipt.setIsinvoice("未申请");
                }else if("1".equals(receipt.getIsinvoice())){
                    receipt.setIsinvoice("已申请");
                }else if("2".equals(receipt.getIsinvoice())){
                    receipt.setIsinvoice("已核销");
                }else if("3".equals(receipt.getIsinvoice())){
                    receipt.setIsinvoice("未申请");
                }else if("4".equals(receipt.getIsinvoice())){
                    receipt.setIsinvoice("申请中");
                }else if("5".equals(receipt.getIsinvoice())){
                    receipt.setIsinvoice("部分核销");
                }
                if("0".equals(receipt.getIsinvoicebill())){
                    receipt.setIsinvoicebill("未开票");
                }else if("1".equals(receipt.getIsinvoicebill())){
                    receipt.setIsinvoicebill("已开票");
                }else if("3".equals(receipt.getIsinvoicebill())){
                    receipt.setIsinvoicebill("未开票");
                }else if("4".equals(receipt.getIsinvoicebill())){
                    receipt.setIsinvoicebill("开票中");
                }
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(receipt);
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
     * 核销回单
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-26
     */
    @UserOperateLog(key="Receipt",type=0)
    public String writeoffSalesReceipt()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Map retmap = new HashMap();
		String ids = "";
        //1直接核销0不直接核销
        String isSalesReceiptDirectWriteoff = getSysParamValue("IsSalesReceiptDirectWriteoff");
        if(StringUtils.isEmpty(isSalesReceiptDirectWriteoff)){
            isSalesReceiptDirectWriteoff = "0";
        }
        if("0".equals(isSalesReceiptDirectWriteoff)){
			ids = (String)map.get("receiptids");
            retmap = salesReceiptService.doWriteoffSalesReceipt(map);
        }else if("1".equals(isSalesReceiptDirectWriteoff)){
			ids = (String)map.get("ids");
            retmap = salesReceiptService.doDirectWriteoffSalesReceipt(map);
        }
        addJSONObject(retmap);
		if(retmap.get("flag").equals(true)){
			addLog("回单编号："+ids+"核销生成销售核销编号："+(String)retmap.get("salesInvoiceid"), true);
		}else{
			addLog("回单编号："+ids+"核销", false);
		}
        return SUCCESS;
    }

    /**
     * 销售发货回单导出
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年6月12日
     */
    public String exportReceipt() throws Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(org.apache.commons.lang3.StringUtils.isEmpty(title)){
            title = "list";
        }

        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编号");
        firstMap.put("customername", "客户");
        firstMap.put("state", "状态");
        firstMap.put("salesdeptname", "销售部门");
        firstMap.put("salesusername", "客户业务员");
        firstMap.put("saleorderid", "销售订单编号");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("barcode", "条形码");
        firstMap.put("boxnum", "箱装量");
        firstMap.put("unitname", "单位");
        firstMap.put("unitnum", "发货数量");
        firstMap.put("inittaxprice", "初始单价");
        firstMap.put("taxprice", "单价");
        firstMap.put("boxprice", "箱价");
        firstMap.put("taxamount", "发货金额");
        firstMap.put("auxnumdetail", "辅数量");
        firstMap.put("receipttaxamount", "应收金额");
        firstMap.put("receiptnum", "客户接收数量");
        firstMap.put("remark", "备注");

        result.add(firstMap);

        List<Map<String, Object>> rejectBillList = salesReceiptService.getReceiptByExport(pageMap);
        for(Map<String, Object> map2 : rejectBillList){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
        ExcelUtils.exportExcel(result, title);



        return SUCCESS;
    }

    /**
     * 根据来源编号获取单据类型1销售订单2退货通知单3发货回单
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-08-18
     */
    public String checkSourceBillType()throws Exception{
        String id = request.getParameter("id");
        String billtype = checkSourceBillType(id);
        addJSONObject("billtype",billtype);
        return SUCCESS;
    }

	/**
	 * 显示品牌折扣添加页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-08-11
	 */
	public String showReceiptBrandDiscountAddPage()throws Exception{
		String receiptid = request.getParameter("receiptid");
		request.setAttribute("receiptid",receiptid);
		SysParam discountTypeSysParam = getBaseSysParamService().getSysParam("repartitionType");
		if(null != discountTypeSysParam){
			request.setAttribute("repartitionType",discountTypeSysParam.getPvalue());
		}else{
			request.setAttribute("repartitionType","0");
		}
		return SUCCESS;
	}

	/**
	 * 添加品牌折扣到单价中
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-08-11
	 */
	public String addSaveReceiptDetailBrandDiscount()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		List list = salesReceiptService.addSaveReceiptDetailBrandDiscount(map);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 显示回单折扣添加页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-08-12
	 */
	public String showReceiptDetailDiscountAddPage()throws Exception{
		String receiptid = request.getParameter("receiptid");
		request.setAttribute("receiptid",receiptid);
		SysParam discountTypeSysParam = getBaseSysParamService().getSysParam("repartitionType");
		if(null != discountTypeSysParam){
			request.setAttribute("repartitionType",discountTypeSysParam.getPvalue());
		}else{
			request.setAttribute("repartitionType","0");
		}
		return SUCCESS;
	}

	/**
	 * 添加回单折扣到单价中
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-08-12
	 */
	public String addSaveBillDetailReceiptDiscount()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		List list = salesReceiptService.addSaveBillDetailReceiptDiscount(map);
		addJSONArray(list);
		return SUCCESS;
	}
}

