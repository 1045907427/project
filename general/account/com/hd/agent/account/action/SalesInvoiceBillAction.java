/**
 * @(#)SalesInvoiceBillAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Feb 14, 2015 panxiaoxiao 创建版本
 */
package com.hd.agent.account.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.account.model.SalesInvoiceBillDetail;
import com.hd.agent.account.model.SalesInvoiceBillExport;
import com.hd.agent.account.service.ISalesInvoiceBillService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.*;
import com.hd.agent.common.util.ftl.CutStringMethodModel;
import com.hd.agent.common.util.ftl.PaddingStringMethodModel;
import com.hd.agent.sales.service.ISalesOutService;
import com.hd.agent.storage.service.IStorageSaleOutService;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * 申请开票
 * 
 * @author panxiaoxiao
 */
public class SalesInvoiceBillAction extends BaseFilesAction{

	/**
	 * 申请开票
	 */
	private SalesInvoiceBill salesInvoiceBill;
	
	private CustomerPushBalance customerPushBalance;
	
	private ISalesInvoiceBillService salesInvoiceBillService;
	
	private ISalesOutService salesOutService;

    private IStorageSaleOutService storageSaleOutService;

	public ISalesOutService getSalesOutService() {
		return salesOutService;
	}

	public void setSalesOutService(ISalesOutService salesOutService) {
		this.salesOutService = salesOutService;
	}

	public SalesInvoiceBill getSalesInvoiceBill() {
		return salesInvoiceBill;
	}

	public void setSalesInvoiceBill(SalesInvoiceBill salesInvoiceBill) {
		this.salesInvoiceBill = salesInvoiceBill;
	}

	public CustomerPushBalance getCustomerPushBalance() {
		return customerPushBalance;
	}

    public IStorageSaleOutService getStorageSaleOutService() {
        return storageSaleOutService;
    }

    public void setStorageSaleOutService(IStorageSaleOutService storageSaleOutService) {
        this.storageSaleOutService = storageSaleOutService;
    }

    public void setCustomerPushBalance(CustomerPushBalance customerPushBalance) {
		this.customerPushBalance = customerPushBalance;
	}

	public ISalesInvoiceBillService getSalesInvoiceBillService() {
		return salesInvoiceBillService;
	}

	public void setSalesInvoiceBillService(
			ISalesInvoiceBillService salesInvoiceBillService) {
		this.salesInvoiceBillService = salesInvoiceBillService;
	}
	
	/**
	 * 显示申请开票列表页面
	 */
	public String showSalesInvoiceBillListPage()throws Exception{
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return SUCCESS;
	}
	
	/**
	 * 获取申请开票列表（申请状态为开票）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 13, 2015
	 */
	public String getSalesInvoiceBillData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesInvoiceBillService.getSalesInvoiceBillData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示申请开票新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public String showSalesInvoiceBillAddPage()throws Exception{
		String addSalesInvoiceBillSourceBillUnWriteoff = "0";
		//申请开票时来源单据是否取未核销状态的1已核销可取0只取未核销
		SysParam sysParam = getBaseSysParamService().getSysParam("addSalesInvoiceBillSourceBillUnWriteoff");
		if(null != sysParam){
			addSalesInvoiceBillSourceBillUnWriteoff = sysParam.getPvalue();
		}
		request.setAttribute("addSalesInvoiceBillSourceBillUnWriteoff",addSalesInvoiceBillSourceBillUnWriteoff);
		return SUCCESS;
	}
	
	/**
	 * 根据来源单据编号和来源类型生成销售发票(申请开票)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=2)
	public String addSalesInvoiceBillByRefer()throws Exception {
		String ids = request.getParameter("ids");
		String customerid = request.getParameter("customerid");
		String iswriteoff = request.getParameter("iswriteoff");
		Map map = salesInvoiceBillService.addSalesInvoiceBillByReceiptAndRejectbill(ids,customerid,iswriteoff);
		addJSONObject(map);
		String id = "";
		if(null!=map){
			id = (String) map.get("id");
		}
		addLog("销售开票新增 编号："+id, map);
		return SUCCESS;
	}
	
	/**
	 * 显示销售发票开票修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 3, 2013
	 */
	public String showSalesInvoiceBillEditPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");

		String jsGoodsVersion=getSysParamValue("HTKPJSGOODSVERSION");
		if(null==jsGoodsVersion || "".equals(jsGoodsVersion.trim())){
			jsGoodsVersion="12.0";
		}
		request.setAttribute("jsGoodsVersion",jsGoodsVersion);

		String jskpDefaultReceipter=getSysParamValue("JSKPDefaultReceipter");
		request.setAttribute("jskpDefaultReceipter",jskpDefaultReceipter);
		String jskpDefaultChecker=getSysParamValue("JSKPDefaultChecker");
		request.setAttribute("jskpDefaultChecker",jskpDefaultChecker);

		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return "success";
	}

	/**
	 * 显示销售发票开票修改详情页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 15, 2015
	 */
	public String salesInvoiceBillEditPage()throws Exception{
		String id = request.getParameter("id");
		Map map = salesInvoiceBillService.getSalesInvoiceBillInfo(id);
		SalesInvoiceBill salesInvoiceBill = (SalesInvoiceBill) map.get("salesInvoiceBill");
//		List list =  (List) map.get("detailList");
//		String jsonStr = JSONUtils.listToJsonStrFilter(list);
		List customerArr = (List) map.get("customerArr");
		request.setAttribute("customerArr", customerArr);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("salesInvoiceBill", salesInvoiceBill);
//		request.setAttribute("detailList", jsonStr);
//		if(null!=list){
//			request.setAttribute("listSize", list.size());
//		}
		//获取销售部门
		List deptList = getBaseDepartMentService().getDeptListByOperType("4");
		request.setAttribute("deptList", deptList);
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
		
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);		

		String hasblance="0";
		if("3".equals(salesInvoiceBill.getStatus()) || "4".equals(salesInvoiceBill.getStatus())){
			if(salesInvoiceBillService.getSalesInvoiceBillHasBlance(salesInvoiceBill.getId())>0){
				hasblance="1";
			}				
		}
		request.setAttribute("hasblance", hasblance);
		
		if(null!=salesInvoiceBill){
			if("1".equals(salesInvoiceBill.getStatus()) || "2".equals(salesInvoiceBill.getStatus()) || "6".equals(salesInvoiceBill.getStatus()) || "5".equals(salesInvoiceBill.getStatus())){
				return "success";
			}else{
				return "viewSuccess";
			}
		}else{
			return "addSuccess";
		}
	}
	
	/**
	 * 销售开票修改保存
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 15, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=3)
	public String editSalesInvoiceBillSave()throws Exception{
		String delgoodsids = request.getParameter("delgoodsids");
		Map map = salesInvoiceBillService.editSalesInvoiceBill(salesInvoiceBill, delgoodsids);
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean)map.get("flag");
		if(flag && "saveaudit".equals(saveaudit)){
			boolean auditflag = salesInvoiceBillService.auditSalesInvoiceBill(salesInvoiceBill.getId());
			map.put("auditflag", auditflag);
			addLog("销售开票保存审核 编号："+salesInvoiceBill.getId(), auditflag);
			
			String hasblance="0";
			if(salesInvoiceBillService.getSalesInvoiceBillHasBlance(salesInvoiceBill.getId())>0){
				hasblance="1";
			}
			request.setAttribute("hasblance", hasblance);
			map.put("hasblance", hasblance);
		}else{
			addLog("销售开票修改 编号："+salesInvoiceBill.getId(), flag);
		}
		addJSONObject(map);
		return "success";
	}
	
	/**
	 * 销售开票审核
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=3)
	public String auditSalesInvoiceBill() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesInvoiceBillService.auditSalesInvoiceBill(id);
		addJSONObject("flag", flag);
		addLog("销售开票审核 编号："+id, flag);
		return "success";
	}
	
	/**
	 * 销售开票反审
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=3)
	public String oppauditSalesInvoiceBill() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesInvoiceBillService.oppauditSalesInvoiceBill(id);
		addJSONObject("flag", flag);
		addLog("销售开票反审 编号："+id, flag);
		return "success";
	}
	
	/**
	 * 销售开票删除
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=4)
	public String deleteSalesInvoiceBill()throws Exception{
		String id = request.getParameter("id");
        //单据类型1正常开票2预开票
        String billtype = request.getParameter("billtype");
        boolean flag = false;
        if("1".equals(billtype)){
            flag = salesInvoiceBillService.deleteSalesInvoiceBill(id);
        }else if("2".equals(billtype)){
            flag = salesInvoiceBillService.deleteSalesAdvanceBill(id);
        }
		addJSONObject("flag", flag);
		addLog("销售开票删除 编号："+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 显示上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public String showSalesInvoiceBillSourceListReferPage()throws Exception{
		String id = request.getParameter("id");
		List list = salesInvoiceBillService.showSalesInvoiceBillSourceListReferPage(id, "");
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("list", jsonStr);
		Map map = salesInvoiceBillService.getSalesInvoiceBillInfo(id);
		SalesInvoiceBill salesInvoiceBill = (SalesInvoiceBill)map.get("salesInvoiceBill");
		if(null != salesInvoiceBill){
			request.setAttribute("invoiceStatus", salesInvoiceBill.getStatus());
		}
		return SUCCESS;
	}
	
	/**
	 * 销售开票中删除明细，回写来源单据开票状态，开票金额、未开票金额等
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=0)
	public String deleteSalesInvoiceBillSource()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		Map retmap = salesInvoiceBillService.deleteSalesInvoiceBillSource(map);
		String invoiceid = request.getParameter("invoiceid");
		String sourceid = request.getParameter("sourceid");
		String delgoodsids = (String)retmap.get("delgoodsids");
		if(StringUtils.isNotEmpty(delgoodsids)){
			addLog(invoiceid+"销售发票中删除 来源单据编号："+sourceid+"明细 商品编号："+delgoodsids, retmap.get("flag").equals(true));
		}else{
			addLog(invoiceid+"销售发票中删除 来源单据编号："+sourceid+"中的明细为空!", false);
		}
		addJSONObject(retmap);
		return SUCCESS;
	}
	
	/**
	 * 显示客户回单未核销页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public String showReceiptUnWriteOffForInvoicebillPage() throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		request.setAttribute("invoiceid", invoiceid);
		Map map = salesInvoiceBillService.getSalesInvoiceBillInfo(invoiceid);
		SalesInvoiceBill salesInvoiceBill = (SalesInvoiceBill) map.get("salesInvoiceBill");
		request.setAttribute("receiptids", salesInvoiceBill.getSourceid());
		return "success";
	}
	/**
	 * 获取客户回单未核销数据列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public String showReceiptUnWriteOffForInvoicebillData() throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		Map map = salesInvoiceBillService.getSalesInvoiceBillInfo(invoiceid);
		SalesInvoiceBill salesInvoiceBill = (SalesInvoiceBill) map.get("salesInvoiceBill");
		if(null!=salesInvoiceBill){
			List list = salesOutService.getReceiptUnWriteoffListByCustomerid(salesInvoiceBill.getCustomerid());
			addJSONArray(list);
		}
		return "success";
	}
	
	/**
	 * 显示销售开票明细列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public String showSalesInvoiceBillDetailPage()throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 获取销售开票明细数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public String showSalesInvoiceBillDetailData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesInvoiceBillService.showSalesInvoiceBillDetailData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出销售开票明细数据
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public void exportSalesInvoiceBillDetailData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag", true);
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
		firstMap.put("billid", "销售开票编号");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("sourcetypename", "单据类型");
		firstMap.put("sourceid", "单据编号");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("taxprice", "单价");
		firstMap.put("taxamount", "金额");
		firstMap.put("notaxprice", "未税单价");
		firstMap.put("notaxamount", "未税金额");
		firstMap.put("tax", "税额");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		PageData pageData = salesInvoiceBillService.showSalesInvoiceBillDetailData(pageMap);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.addAll(pageData.getList());
		list.addAll(pageData.getFooter());
		for(Map<String, Object> map2 : list){
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
	}
	
	/**
	 * 回退销售开票（作废，中止）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=3)
	public String salesInvoiceBillCancel()throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = salesInvoiceBillService.updateSalesInvoiceBillCancel(id);
				if(flag){
					succssids += id+",";
				}else{
					errorids += id+",";
				}
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "失败编号:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("销售开票回退 成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	
	/**
	 * 导出销售开票清单
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 26, 2015
	 */
	public void exportSalesInvoiceBillList()throws Exception{
		String exportid = request.getParameter("exportid");
		Map map = salesInvoiceBillService.getSalesInvoiceBillInfo(exportid);
		List list =  (List) map.get("detailList");

		String exceltitle=request.getParameter("excelTitle");
		if (null==exceltitle || "".equals(exceltitle.trim())) {
			exceltitle = "销售开票清单";
		}
		ExcelUtils.exportExcel(exportSalesInvoiceBillListFilter(list), exceltitle);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(分商品)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSalesInvoiceBillListFilter(List list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("model", "规格型号");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("noprice", "未税单价");
		firstMap.put("notaxamount", "未税金额");
		firstMap.put("taxname", "税率");
		firstMap.put("tax", "税额");
		result.add(firstMap);
		for(SalesInvoiceBillDetail salesInvoiceBillDetail : new ArrayList<SalesInvoiceBillDetail>(list)){
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			map.put("goodsid", salesInvoiceBillDetail.getGoodsid());
			GoodsInfo goodsInfo = salesInvoiceBillDetail.getGoodsInfo();
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				map.put("model",goodsInfo.getModel());
			}
			map.put("unitname", (salesInvoiceBillDetail.getUnitname() != null ? salesInvoiceBillDetail.getUnitname() : ""));
			BigDecimal unitnum = BigDecimal.ZERO;
			if(null!=salesInvoiceBillDetail.getUnitnum()){
				unitnum = salesInvoiceBillDetail.getUnitnum();
			}
			
			map.put("unitnum", unitnum);
			
			//map.put("price", (salesInvoiceDetail.getTaxprice() != null ?salesInvoiceDetail.getTaxprice() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			map.put("noprice", (salesInvoiceBillDetail.getNotaxprice() != null ?salesInvoiceBillDetail.getNotaxprice() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			//map.put("taxamount", (salesInvoiceDetail.getTaxamount() != null ? salesInvoiceDetail.getTaxamount() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			map.put("notaxamount", (salesInvoiceBillDetail.getNotaxamount() != null ? salesInvoiceBillDetail.getNotaxamount() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			map.put("taxname", (salesInvoiceBillDetail.getTaxtypename() != null ? salesInvoiceBillDetail.getTaxtypename() : ""));
			map.put("tax", (salesInvoiceBillDetail.getTax() != null ? salesInvoiceBillDetail.getTax() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			result.add(map);
		}
		return result;
	}
	/**
	 * 销售开票明细导出
	 * @throws
	 * @author lin_xx
	 * @date 2017/11/10
	 */
	public void exportSalesInvoiceBillDetailList() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String id = request.getParameter("id");
		String title = "";
		if (map.containsKey("excelTitle")) {
			title = map.get("excelTitle").toString();
		} else {
			title = "list";
		}
		if (StringUtils.isEmpty(title)) {
			title = "list";
		}
		Map resMap = salesInvoiceBillService.getSalesInvoiceBillInfo(id);
		List<SalesInvoiceBillDetail> list =  (List) resMap.get("detailList");

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("brandname", "商品品牌");
		firstMap.put("model", "规格型号");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("taxprice", "单价");
		firstMap.put("taxamount", "金额");
		firstMap.put("notaxprice", "未税单价");
		firstMap.put("notaxamount", "未税金额");
		firstMap.put("taxtypename", "税种");
		firstMap.put("tax", "税额");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("remark", "备注");
		result.add(firstMap);

		if(list.size() != 0){
			for (SalesInvoiceBillDetail detail : list) {
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(detail);
				GoodsInfo goodsInfo = detail.getGoodsInfo();
				if(null!=goodsInfo){
					map2.put("goodsname", goodsInfo.getName());
					map2.put("barcode",goodsInfo.getBarcode());
					map2.put("brandname",goodsInfo.getBrandName());
					map2.put("model",goodsInfo.getModel());
					map2.put("boxnum",goodsInfo.getBoxnum());
				}
				for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
					if (map2.containsKey(fentry.getKey())) { // 如果记录中包含该Key，则取该Key的Value
						for (Map.Entry<String, Object> entry : map2.entrySet()) {
							if (fentry.getKey().equals(entry.getKey())) {
								objectCastToRetMap(retMap, entry);
							}
						}
					} else {
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		ExcelUtils.exportExcel(result, title);
	}

	/**
	 * 显示客户销售开票列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 26, 2015
	 */
	public String showSalesInvoiceBillListPageByCustomer()throws Exception{
		String customerid = request.getParameter("customerid");
        String billtype = request.getParameter("billtype");
		List list = salesInvoiceBillService.showSalesInvoiceBillListPageByCustomer(customerid,billtype);
		String jsonStr = JSONUtils.listToJsonStrFilter(list);
		request.setAttribute("dataList", jsonStr);
		return SUCCESS;
	}
	
	/**
	 * 追加销售开票
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 26, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=2)
	public String addToSalesInvoiceBillByCustomer()throws Exception{
		String customerid = request.getParameter("customerid");
		String billid = request.getParameter("billid");
		String ids = request.getParameter("ids");
        String billtype = request.getParameter("billtype");
        Map map = new HashMap();
        if("1".equals(billtype)){//正常开票
            map = salesInvoiceBillService.addToSalesInvoiceBillByReceiptAndRejectbill(ids,customerid,billid);
        }else if("2".equals(billtype)){//预开票
            map = salesInvoiceBillService.addToSalesInvoiceBillBySaleout(ids,customerid,billid);
        }
        addJSONObject(map);
		String id = "";
		if(null!=map){
			id = (String) map.get("id");
		}
		addLog("销售开票追加 编号："+billid, map);
		return SUCCESS;
	}
	
	/**
	 * 显示修改发票号、发票代码信息页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 26, 2015
	 */
	public String showSalesInvoiceBillInvoicenoInfoPage()throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		String customerid = request.getParameter("customerid");
		request.setAttribute("invoiceid", invoiceid);
		request.setAttribute("customerid", customerid);
		return SUCCESS;
	}
	
	/**
	 * 修改销售开票发票号、发票代码信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Feb 26, 2015
	 */
	@UserOperateLog(key="SalesInvoiceBill",type=3)
	public String editSalesInvoiceBillInvoiceno()throws Exception{
		boolean flag = salesInvoiceBillService.editSalesInvoiceBillInvoiceno(salesInvoiceBill);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

    /**
     * 显示申请开票回退页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/01
     */
    public String showSalesInvoiceBillCancelListPage()throws Exception{
        return  SUCCESS;
    }

    /**
     * 删除申请开票回退
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/01
     */
    @UserOperateLog(key="SalesInvoiceBill",type=4)
    public String salesInvoiceBillMutiDelete()throws  Exception{
        String ids = request.getParameter("ids");
        Map map = salesInvoiceBillService.salesInvoiceBillMutiDelete(ids);
        addJSONObject(map);
        if(StringUtils.isNotEmpty((String)map.get("sucids"))){
            addLog("销售开票回退删除 编号："+(String)map.get("sucids"), true);
        }else{
            addLog("销售开票回退删除 编号："+ids, false);
        }
        return SUCCESS;
    }

    /**
     * 检查选中的销售开票是否允许申请核销
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/01
     */
    public String checkSalesInvoiceBillCanMuApplyWriteOff()throws Exception{
        String ids = request.getParameter("ids");
		Map map = salesInvoiceBillService.doCheckSalesInvoiceBillCanMuApplyWriteOff(ids);
		addJSONObject(map);
        return SUCCESS;
    }
    /**
     * 申请核销
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/01
     */
	@UserOperateLog(key="SalesInvoiceBill",type=2)
    public String doSalesInvoiceBillMuApplyWriteOff()throws Exception{
        String ids = request.getParameter("ids");
        Map map = new HashMap();
        String failids = "",salesinvoiceids="",msg = "",msglog = "";
        int sucNum = 0;
        if(StringUtils.isNotEmpty(ids)){
            String[] idArr = ids.split(",");
            for(String id : idArr){
                Map map1 = salesInvoiceBillService.doSalesInvoiceBillMuApplyWriteOff(id);
                if(!map1.get("flag").equals(true)){
                    if(StringUtils.isEmpty(failids)){
                        failids = id;
                    }else{
                        failids += "," + id;
                    }
					if(StringUtils.isEmpty(msglog)){
						msglog = "销售开票 编号："+id+"申请核销 失败";
					}else{
						msglog += "；" + "销售开票 编号："+id+"申请核销 失败";
					}
                }else{
                    String salesinvoiceid = map1.get("id").toString();
                    if(StringUtils.isEmpty(failids)){
                        salesinvoiceids = salesinvoiceid;
                    }else{
                        salesinvoiceids += "," + salesinvoiceid;
                    }
                    sucNum++;
					if(StringUtils.isEmpty(msglog)){
						msglog = "销售开票 编号："+id+"申请核销 编号："+salesinvoiceid;
					}else{
						msglog += "；" + "销售开票 编号："+id+"申请核销 编号："+salesinvoiceid;
					}
                }
				String retmsg = (String)map1.get("msg");
				if(StringUtils.isNotEmpty(msg)){
					msg = retmsg;
				}else{
					msg += "；<br>" + retmsg;
				}
            }
        }
        map.put("salesinvoiceids", salesinvoiceids);
        map.put("sucNum", sucNum);
        map.put("failids", failids);
		map.put("msg",msg);
        addJSONObject(map);
		addLog(msglog, true);
        return SUCCESS;
    }

    /*--------------------------------------预开票------------------------------------------*/

    /**
     * 预开票新增页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/11
     */
    public String showSalesAdvanceBillAddPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 根据来源单据编号和来源类型生成开票(预开票)
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 14, 2015
     */
    @UserOperateLog(key="SalesInvoiceBill",type=2)
    public String addSalesAdvanceBillByRefer()throws Exception{
        String ids = request.getParameter("ids");
        String customerid = request.getParameter("customerid");
        Map map = salesInvoiceBillService.addSalesAdvanceBillByRefer(ids,customerid);
        addJSONObject(map);
        String id = "";
        if(null!=map){
            id = (String) map.get("id");
        }
        addLog("销售预开票新增 编号："+id, map);
        return SUCCESS;
    }

	/**
	 * 根据开票编号获取开票明细分页列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-02
	 */
	public String getSalesInvoiceBillDetailList()throws Exception{
		String id = request.getParameter("id");
		Map map = new HashMap();
		map.put("billid",id);
		pageMap.setCondition(map);
		PageData pageData = salesInvoiceBillService.getSalesInvoiceBillDetailList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 根据发票号或者发票单据编号 获取销售开票列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-17
	 */
	public String showSalesInvoiceBillListPageByIds()throws Exception{
		String id = request.getParameter("id");
		List list = salesInvoiceBillService.getSalesInvoiceBillListPageByIds(id);
		if(null!=list){
			String jsonStr = JSONUtils.listToJsonStrFilter(list);
			request.setAttribute("dataList", jsonStr);
		}
		return SUCCESS;
	}

	/**
	 * 导出销售开票清单 航天开票系统
	 * @param
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 30, 2016
	 */
	public void exportSalesInvoiceBillForHTKP()throws Exception {
		String type = request.getParameter("type");
		if ("txt".equals(type)) {
			exportSalesInvoiceBillTXTForHTKP();
		}else if("xml".equals(type)) {
			exportSalesInvoiceBillXMLForHTKP();
		}else{
			exportSalesInvoiceBillTXTForHTKP();
		}
	}

	/**
	 * 导出销售开票清单 航天开票系统 txt格式
	 * @param
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 30, 2016
	 */
	public void exportSalesInvoiceBillTXTForHTKP()throws Exception{
		String title = request.getParameter("title");
		if(null==title || "".equals(title.trim())){
			title="航天正数开票格式";
		}
		String exportid = request.getParameter("exportid");
		String msg="";
		if(null==exportid || "".equals(exportid.trim())){
			title="航天开票格式导出出错";
			msg="未能找到相关单据信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		String checker=request.getParameter("checkerid");
		if(null==checker || "".equals(checker.trim())){
			title="航天开票格式导出出错";
			msg="未能找到复核人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Personnel checkerPersonnel=getPersonnelInfoById(checker);
		if(null==checkerPersonnel || StringUtils.isEmpty(checkerPersonnel.getName())){
			title="航天开票格式导出出错";
			msg="未能找到复核人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		String receipter=request.getParameter("receipterid");
		if(null==receipter || "".equals(receipter.trim())){
			title="航天开票格式导出出错";
			msg="未能找到收款人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Personnel receipterPersonnel=getPersonnelInfoById(receipter);
		if(null==receipterPersonnel || StringUtils.isEmpty(receipterPersonnel.getName())){
			title="航天开票格式导出出错";
			msg="未能找到收款人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Map map = salesInvoiceBillService.getSalesInvoiceBillForHTKP(exportid.trim());
		Boolean flag=false;
		if(map.containsKey("flag")){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		if(false==flag){
			title="航天开票格式导出出错";
			if(map.containsKey("msg")){
				msg=(String)map.get("msg");
			}
			if(null==msg || "".equals(msg.trim())){
				msg="未能找到相关导出数据";
			}
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Map billMap = null;
		//开票单据是否存在
		if(map.containsKey("salesInvoiceBill")){
			billMap=(Map) map.get("salesInvoiceBill");
		}
		if(null==billMap || billMap.size()==0){
			msg="未能找到相关订单数据";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		List<Map> detailMapList = null;
		//开票明细是否存在
		if(map.containsKey("detailList")){
			detailMapList=(List<Map>) map.get("detailList");
		}
		if(null==detailMapList || detailMapList.size()==0){
			msg="未能找到相关订单明细数据";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Customer customerInfo=null;
		if(map.containsKey("customerInfo")){
			customerInfo=(Customer)map.get("customerInfo");
		}
		if(null==customerInfo){
			customerInfo=new Customer();
		}
		String tmpstr=null;
		billMap.put("detailcount",detailMapList.size());
		tmpstr= MessageFormat.format("{0} {1}",
				ValueUtils.getValueOrEmpty(customerInfo.getAddress()),
				ValueUtils.getValueOrEmpty(customerInfo.getMobile()));
		billMap.put("customeradrrphone", tmpstr);

		String tplFileDir = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/ftl/");
		Map tplDataMap=new HashMap();
		tplDataMap.put("companyName",getSysParamValue("COMPANYNAME"));
		tplDataMap.put("billOrderData",billMap);
		tplDataMap.put("detailList",detailMapList);
		tplDataMap.put("billchecker",checkerPersonnel.getName());
		tplDataMap.put("billreceipter",receipterPersonnel.getName());
		tplDataMap.put("billgoodsname","");
		tplDataMap.put("customerInfo",customerInfo);
		tplDataMap.put("cutStringFunc",new CutStringMethodModel());
		//创建freemarker配置实例
		Configuration config=new Configuration();
		config.setNumberFormat("#.######");
		config.setDirectoryForTemplateLoading(new File(tplFileDir));
		config.setDefaultEncoding("UTF-8");
		config.setTemplateUpdateDelay(0);
		Template template=config.getTemplate("salesinvoice_htkp.ftl");
		template.setEncoding("UTF-8");
		StringWriter stringWriter = new StringWriter();
		template.process(tplDataMap, stringWriter);

		//byte[] result =new String(stringWriter.getBuffer().toString().getBytes("UTF-8"),"gb2312").getBytes("gb2312");
		byte[] result =stringWriter.getBuffer().toString().getBytes("GBK");
		CommonUtils.responseExportTxtFile(response, result, title, ".txt");
		SysUser sysUser=getSysUser();
		SalesInvoiceBill upBillExportTimes=new SalesInvoiceBill();
		upBillExportTimes.setId(exportid);
		upBillExportTimes.setJxexporttimes(1);
		upBillExportTimes.setJxexportuserid(sysUser.getUserid());
		upBillExportTimes.setJxexportusername(sysUser.getName());
		upBillExportTimes.setJxexportdatetime(new Date());
		salesInvoiceBillService.updateOrderJSExportTimes(upBillExportTimes);
	}
	/**
	 * 导出销售开票清单 航天开票系统 xml格式
	 * @param
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 30, 2016
	 */
	public String exportSalesInvoiceBillXMLForHTKP()throws Exception{
		String title = request.getParameter("title");
		if(null==title || "".equals(title.trim())){
			title="航天正数开票XML格式";
		}
		Map resultMap=new HashMap();
		String exportid = request.getParameter("exportid");
		String msg="";
		if(null==exportid || "".equals(exportid.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关单据信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		String checker=request.getParameter("checkerid");
		if(null==checker || "".equals(checker.trim())){
				resultMap.put("flag", false);
				resultMap.put("msg", "未能找到复核人信息");
				addJSONObject(resultMap);
				return SUCCESS;
		}
		Personnel checkerPersonnel=getPersonnelInfoById(checker);
		if(null==checkerPersonnel || StringUtils.isEmpty(checkerPersonnel.getName())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到复核人信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		String receipter=request.getParameter("receipterid");
		if(null==receipter || "".equals(receipter.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到收款人信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		Personnel receipterPersonnel=getPersonnelInfoById(receipter);
		if(null==receipterPersonnel || StringUtils.isEmpty(receipterPersonnel.getName())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到收款人信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		String jsgoodsversion=request.getParameter("jsgoodsversion");
		if(null==jsgoodsversion || "".equals(jsgoodsversion.trim())){
			jsgoodsversion="12.0";
		}
		jsgoodsversion=jsgoodsversion.trim();
		Map handleResultMap = salesInvoiceBillService.getSalesInvoiceBillXMLForHTKP(exportid.trim());
		Boolean flag=false;
		if(handleResultMap.containsKey("flag")){
			flag=(Boolean)handleResultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		if(false==flag){
			title="航天开票XML格式导出出错";
			if(handleResultMap.containsKey("msg")){
				msg=(String)handleResultMap.get("msg");
			}
			if(null==msg || "".equals(msg.trim())){
				msg="未能找到相关导出数据";
			}

			List<String> dataCellList = new ArrayList<String>();
			dataCellList.add("billid");
			dataCellList.add("jstaxsortid");
			dataCellList.add("goodsid");
			dataCellList.add("goodsname");
			dataCellList.add("unitname");
			dataCellList.add("unitnum");
			dataCellList.add("notaxprice");
			dataCellList.add("notaxamount");
			dataCellList.add("tax");
			dataCellList.add("taxrate");
			dataCellList.add("taxtypename");
			dataCellList.add("errormessage");

			List<Map<String, Object>> errorDataList=new ArrayList<Map<String, Object>>();
			if(handleResultMap.containsKey("errorDataList")) {
				errorDataList = (List<Map<String, Object>>) handleResultMap.get("errorDataList");
			}
			if(errorDataList.size()>0){
				msg=msg+"，单据检查不通过。";
				if(errorDataList.size()>0){
					String outTplFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/salesinvoicebillToHTKPXmlTpl.xls");
					IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
					String fileid=attachFileService.createExcelAndAttachFile(errorDataList, dataCellList, outTplFilePath,"销售开票金税XML生成失败");
					resultMap.put("datafileid",fileid);
				}
				addJSONObject(resultMap);
				return SUCCESS;
			}
			String amountlog="销售开票“"+exportid+"”金税XML文件生成成功";
			String amountmsg="";
			if(handleResultMap.containsKey("amountmsg")){
				amountmsg=(String)handleResultMap.get("amountmsg");
				amountlog=amountmsg;
			}

			addLog(amountlog.trim());

			resultMap.put("flag", false);
			resultMap.put("msg", msg);
			addJSONObject(resultMap);
			return SUCCESS;
		}
		Map billMap = null;
		//开票单据是否存在
		if(handleResultMap.containsKey("salesInvoiceBill")){
			billMap=(Map) handleResultMap.get("salesInvoiceBill");
		}
		if(null==billMap || billMap.size()==0){
			msg="未能找到相关订单数据";
			resultMap.put("flag", false);
			resultMap.put("msg", msg);
			addJSONObject(resultMap);
			return SUCCESS;
		}
		List<Map> detailMapList = null;
		//开票明细是否存在
		if(handleResultMap.containsKey("detailList")){
			detailMapList=(List<Map>) handleResultMap.get("detailList");
		}
		if(null==detailMapList || detailMapList.size()==0){
			msg="未能找到相关订单明细数据";
			resultMap.put("flag", false);
			resultMap.put("msg", msg);
			addJSONObject(resultMap);
			return SUCCESS;
		}
		Customer customerInfo=null;
		if(handleResultMap.containsKey("customerInfo")){
			customerInfo=(Customer)handleResultMap.get("customerInfo");
		}
		if(null==customerInfo){
			customerInfo=new Customer();
		}
		String tmpstr=null;
		billMap.put("detailcount",detailMapList.size());
		tmpstr= MessageFormat.format("{0} {1}",
				ValueUtils.getValueOrEmpty(customerInfo.getAddress()),
				ValueUtils.getValueOrEmpty(customerInfo.getMobile()));
		billMap.put("customeradrrphone", tmpstr);

		String tplFileDir = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/ftl/");
		Map tplDataMap=new HashMap();
		tplDataMap.put("companyName",getSysParamValue("COMPANYNAME"));
		tplDataMap.put("billOrderData",billMap);
		tplDataMap.put("detailList",detailMapList);
		tplDataMap.put("billchecker",checkerPersonnel.getName());
		tplDataMap.put("billreceipter",receipterPersonnel.getName());
		tplDataMap.put("billgoodsname","");
		tplDataMap.put("jsgoodsversion",jsgoodsversion);
		tplDataMap.put("customerInfo",customerInfo);
		tplDataMap.put("cutStringFunc",new CutStringMethodModel());
		tplDataMap.put("paddingStringFunc",new PaddingStringMethodModel());
		//创建freemarker配置实例
		Configuration config=new Configuration();
		config.setNumberFormat("#.######");
		config.setDirectoryForTemplateLoading(new File(tplFileDir));
		config.setDefaultEncoding("UTF-8");
		config.setTemplateUpdateDelay(0);
		Template template=config.getTemplate("salesinvoice_htkp_xml.ftl");
		template.setEncoding("UTF-8");
		StringWriter stringWriter = new StringWriter();
		template.process(tplDataMap, stringWriter);

		//byte[] result =new String(stringWriter.getBuffer().toString().getBytes("UTF-8"),"gb2312").getBytes("gb2312");
		byte[] result =stringWriter.getBuffer().toString().getBytes("GBK");
		IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
		String fileid=attachFileService.createFileAndAttachFile(result,"xml","htkp",title);

		String amountlog="销售开票“"+exportid+"”金税XML文件生成成功";
		String amountmsg="";
		if(handleResultMap.containsKey("amountmsg")){
			amountmsg=(String)handleResultMap.get("amountmsg");
			amountlog=amountmsg;
		}
		addLog(amountlog.trim());

		resultMap.put("flag",true);
		resultMap.put("amountmsg",amountmsg);
		resultMap.put("datafileid",fileid);

		SysUser sysUser=getSysUser();
		SalesInvoiceBill upBillExportTimes=new SalesInvoiceBill();
		upBillExportTimes.setId(exportid);
		upBillExportTimes.setJxexporttimes(1);
		upBillExportTimes.setJxexportuserid(sysUser.getUserid());
		upBillExportTimes.setJxexportusername(sysUser.getName());
		upBillExportTimes.setJxexportdatetime(new Date());
		salesInvoiceBillService.updateOrderJSExportTimes(upBillExportTimes);

		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 导出销售开票清单 航天开票系统 负数发票
	 * @param
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 30, 2016
	 */
	public void exportSalesInvoiceBillForHTKPHZ()throws Exception{
		String type = request.getParameter("type");
		if ("txt".equals(type)) {
			exportSalesInvoiceBillTXTForHTKPHZ();
		}else if("xml".equals(type)) {
			exportSalesInvoiceBillXMLForHTKPHZ();
		}else{
			exportSalesInvoiceBillTXTForHTKPHZ();
		}
	}
	/**
	 * 导出txt销售开票清单 航天开票系统 负数发票
	 * @param
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 30, 2016
	 */
	public void exportSalesInvoiceBillTXTForHTKPHZ()throws Exception{
		String title = request.getParameter("title");
		if(null==title || "".equals(title.trim())){
			title="航天红字开票格式";
		}
		String exportid = request.getParameter("exportid");
		String msg="";
		if(null==exportid || "".equals(exportid.trim())){
			title="航天开票格式导出出错";
			msg="未能找到相关单据信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		String checker=request.getParameter("checkerid");
		if(null==checker || "".equals(checker.trim())){
			title="航天开票格式导出出错";
			msg="未能找到复核人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Personnel checkerPersonnel=getPersonnelInfoById(checker);
		if(null==checkerPersonnel || StringUtils.isEmpty(checkerPersonnel.getName())){
			title="航天开票格式导出出错";
			msg="未能找到复核人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		String receipter=request.getParameter("receipterid");
		if(null==receipter || "".equals(receipter.trim())){
			title="航天开票格式导出出错";
			msg="未能找到收款人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Personnel receipterPersonnel=getPersonnelInfoById(receipter);
		if(null==receipterPersonnel || StringUtils.isEmpty(receipterPersonnel.getName())){
			title="航天开票格式导出出错";
			msg="未能找到收款人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Map map = salesInvoiceBillService.getSalesInvoiceBillForHTKPHZ(exportid.trim());
		Boolean flag=false;
		if(map.containsKey("flag")){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		if(false==flag){
			title="航天开票格式导出出错";
			if(map.containsKey("msg")){
				msg=(String)map.get("msg");
			}
			if(null==msg || "".equals(msg.trim())){
				msg="未能找到相关导出数据";
			}
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Map billMap = null;
		//开票单据是否存在
		if(map.containsKey("salesInvoiceBill")){
			billMap=(Map) map.get("salesInvoiceBill");
		}
		if(null==billMap || billMap.size()==0){
			msg="未能找到相关订单数据";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		List<Map> detailMapList = null;
		//开票明细是否存在
		if(map.containsKey("detailList")){
			detailMapList=(List<Map>) map.get("detailList");
		}
		if(null==detailMapList || detailMapList.size()==0){
			msg="未能找到相关订单明细数据";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Customer customerInfo=null;
		if(map.containsKey("customerInfo")){
			customerInfo=(Customer)map.get("customerInfo");
		}
		if(null==customerInfo){
			customerInfo=new Customer();
		}
		String tmpstr=null;
		billMap.put("detailcount",detailMapList.size());
		tmpstr=MessageFormat.format("{0} {1}",
				ValueUtils.getValueOrEmpty(customerInfo.getAddress()),
				ValueUtils.getValueOrEmpty(customerInfo.getMobile()));
		billMap.put("customeradrrphone", tmpstr);

		String tplFileDir = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/ftl/");
		Map tplDataMap=new HashMap();
		tplDataMap.put("companyName",getSysParamValue("COMPANYNAME"));
		tplDataMap.put("billOrderData",billMap);
		tplDataMap.put("detailList",detailMapList);
		tplDataMap.put("billchecker",checkerPersonnel.getName());
		tplDataMap.put("billreceipter",receipterPersonnel.getName());
		tplDataMap.put("billgoodsname","");
		tplDataMap.put("customerInfo",customerInfo);
		tplDataMap.put("cutStringFunc",new CutStringMethodModel());
		//创建freemarker配置实例
		Configuration config=new Configuration();
		config.setNumberFormat("#.######");
		config.setDirectoryForTemplateLoading(new File(tplFileDir));
		config.setDefaultEncoding("UTF-8");
		config.setTemplateUpdateDelay(0);
		Template template=config.getTemplate("salesinvoice_htkp.ftl");
		template.setEncoding("UTF-8");
		StringWriter stringWriter = new StringWriter();
		template.process(tplDataMap, stringWriter);

		byte[] result =stringWriter.getBuffer().toString().getBytes("GB18030");
		CommonUtils.responseExportTxtFile(response, result, title, ".txt");
		SysUser sysUser=getSysUser();
		SalesInvoiceBill upBillExportTimes=new SalesInvoiceBill();
		upBillExportTimes.setId(exportid);
		upBillExportTimes.setJxexporttimes(1);
		upBillExportTimes.setJxexportuserid(sysUser.getUserid());
		upBillExportTimes.setJxexportusername(sysUser.getName());
		upBillExportTimes.setJxexportdatetime(new Date());
		salesInvoiceBillService.updateOrderJSExportTimes(upBillExportTimes);
	}

	/**
	 * 导出xml销售开票清单 航天开票系统 负数发票
	 * @param
	 * @return void
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 30, 2016
	 */
	public void exportSalesInvoiceBillXMLForHTKPHZ()throws Exception{
		String title = request.getParameter("title");
		if(null==title || "".equals(title.trim())){
			title="航天红字开票格式";
		}
		String exportid = request.getParameter("exportid");
		String msg="";
		if(null==exportid || "".equals(exportid.trim())){
			title="航天开票格式导出出错";
			msg="未能找到相关单据信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		String checker=request.getParameter("checkerid");
		if(null==checker || "".equals(checker.trim())){
			title="航天开票格式导出出错";
			msg="未能找到复核人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Personnel checkerPersonnel=getPersonnelInfoById(checker);
		if(null==checkerPersonnel || StringUtils.isEmpty(checkerPersonnel.getName())){
			title="航天开票格式导出出错";
			msg="未能找到复核人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		String receipter=request.getParameter("receipterid");
		if(null==receipter || "".equals(receipter.trim())){
			title="航天开票格式导出出错";
			msg="未能找到收款人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Personnel receipterPersonnel=getPersonnelInfoById(receipter);
		if(null==receipterPersonnel || StringUtils.isEmpty(receipterPersonnel.getName())){
			title="航天开票格式导出出错";
			msg="未能找到收款人信息";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Map map = salesInvoiceBillService.getSalesInvoiceBillForHTKPHZ(exportid.trim());
		Boolean flag=false;
		if(map.containsKey("flag")){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		if(false==flag){
			title="航天开票格式导出出错";
			if(map.containsKey("msg")){
				msg=(String)map.get("msg");
			}
			if(null==msg || "".equals(msg.trim())){
				msg="未能找到相关导出数据";
			}
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Map billMap = null;
		//开票单据是否存在
		if(map.containsKey("salesInvoiceBill")){
			billMap=(Map) map.get("salesInvoiceBill");
		}
		if(null==billMap || billMap.size()==0){
			msg="未能找到相关订单数据";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		List<Map> detailMapList = null;
		//开票明细是否存在
		if(map.containsKey("detailList")){
			detailMapList=(List<Map>) map.get("detailList");
		}
		if(null==detailMapList || detailMapList.size()==0){
			msg="未能找到相关订单明细数据";
			byte[] errData=msg.getBytes();
			CommonUtils.responseExportTxtFile(response, errData, title, ".txt");
			return;
		}
		Customer customerInfo=null;
		if(map.containsKey("customerInfo")){
			customerInfo=(Customer)map.get("customerInfo");
		}
		if(null==customerInfo){
			customerInfo=new Customer();
		}
		String tmpstr=null;
		billMap.put("detailcount",detailMapList.size());
		tmpstr=MessageFormat.format("{0} {1}",
				ValueUtils.getValueOrEmpty(customerInfo.getAddress()),
				ValueUtils.getValueOrEmpty(customerInfo.getMobile()));
		billMap.put("customeradrrphone", tmpstr);

		String tplFileDir = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/ftl/");
		Map tplDataMap=new HashMap();
		tplDataMap.put("companyName",getSysParamValue("COMPANYNAME"));
		tplDataMap.put("billOrderData",billMap);
		tplDataMap.put("detailList",detailMapList);
		tplDataMap.put("billchecker",checkerPersonnel.getName());
		tplDataMap.put("billreceipter",receipterPersonnel.getName());
		tplDataMap.put("billgoodsname","");
		tplDataMap.put("customerInfo",customerInfo);
		tplDataMap.put("cutStringFunc",new CutStringMethodModel());
		tplDataMap.put("paddingStringFunc",new PaddingStringMethodModel());
		//创建freemarker配置实例
		Configuration config=new Configuration();
		config.setNumberFormat("#.######");
		config.setDirectoryForTemplateLoading(new File(tplFileDir));
		config.setDefaultEncoding("UTF-8");
		config.setTemplateUpdateDelay(0);
		Template template=config.getTemplate("salesinvoice_htkp_xml.ftl");
		template.setEncoding("UTF-8");
		StringWriter stringWriter = new StringWriter();
		template.process(tplDataMap, stringWriter);

		byte[] result =stringWriter.getBuffer().toString().getBytes("GB18030");
		CommonUtils.responseExportTxtFile(response, result, title, ".txt");
		SysUser sysUser=getSysUser();
		SalesInvoiceBill upBillExportTimes=new SalesInvoiceBill();
		upBillExportTimes.setId(exportid);
		upBillExportTimes.setJxexporttimes(1);
		upBillExportTimes.setJxexportuserid(sysUser.getUserid());
		upBillExportTimes.setJxexportusername(sysUser.getName());
		upBillExportTimes.setJxexportdatetime(new Date());
		salesInvoiceBillService.updateOrderJSExportTimes(upBillExportTimes);
	}

	/**
	 * 导出销售开票清单 航天开票
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Feb 26, 2015
	 */
	public void exportSalesInvoiceBillExcelForHTKP()throws Exception{
		String exportid = request.getParameter("exportid");
		Map map = salesInvoiceBillService.getSalesInvoiceBillInfo(exportid);
		SalesInvoiceBill salesInvoiceBill = (SalesInvoiceBill) map.get("salesInvoiceBill");
		List list =  (List) map.get("detailList");

		String exceltitle=request.getParameter("excelTitle");
		if (null==exceltitle || "".equals(exceltitle.trim())) {
			exceltitle = "航天EXCEL格式导出";
		}
		ExcelUtils.exportExcel(exportSalesInvoiceBillExcelFilterForHTKP(list), exceltitle);
	}

	/**
	 * 数据转换，list专程符合excel导出的数据格式 航天开票
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSalesInvoiceBillExcelFilterForHTKP(List list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("billid","单据号");
		firstMap.put("jstaxsortid","金税税收分类编码");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("jsnotaxprice", "未税单价");
		firstMap.put("notaxamount", "未税金额");
		firstMap.put("tax", "税额");
		firstMap.put("taxrate","税率");
		firstMap.put("taxname", "税率名称");
		firstMap.put("jstaxsortname","金税税收分类名称");
		firstMap.put("remark","导出备注");
		result.add(firstMap);

		List<SalesInvoiceBillDetail> oldDetailList=new ArrayList<SalesInvoiceBillDetail>();
		if(null!=list){
			oldDetailList=new ArrayList<SalesInvoiceBillDetail>(list);
		}

		List<SalesInvoiceBillDetail> detailList=new ArrayList<SalesInvoiceBillDetail>();

		for(SalesInvoiceBillDetail salesInvoiceBillDetail:oldDetailList){
			if(BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getNotaxamount())==0
					&& BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getUnitnum())==0){
				continue;
			}
			if(BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getNotaxamount())>0
					&& BigDecimal.ZERO.compareTo(salesInvoiceBillDetail.getUnitnum())>0){
				salesInvoiceBillDetail.setIsdataneg(1);
			}else{
				salesInvoiceBillDetail.setIsdataneg(0);
			}
			detailList.add(salesInvoiceBillDetail);
		}
		Collections.sort(detailList,ListSortLikeSQLComparator.createComparator("isdataneg,seq,brandid"));

		for(SalesInvoiceBillDetail salesInvoiceBillDetail : detailList){
			Map<String,Object> map = new LinkedHashMap<String,Object>();

			map.put("billid","");
			map.put("jstaxsortid","");
			map.put("goodsid", "");
			map.put("goodsname", "");
			map.put("unitname", "");
			map.put("unitnum", "");
			map.put("jsnotaxprice", "");
			map.put("notaxamount", "");
			map.put("tax", "");
			map.put("taxrate","");
			map.put("taxname", "");
			map.put("jstaxsortname", "");
			map.put("remark","");

			map.put("billid",salesInvoiceBillDetail.getBillid());
			map.put("goodsid", salesInvoiceBillDetail.getGoodsid());
			GoodsInfo goodsInfo = salesInvoiceBillDetail.getGoodsInfo();
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				String sourcetype=salesInvoiceBillDetail.getSourcetype();
				String isdiscount=salesInvoiceBillDetail.getIsdiscount();
				if(!"3".equals(sourcetype)&& !"1".equals(isdiscount) && !"2".equals(isdiscount) ) {
					if (StringUtils.isEmpty(goodsInfo.getJstaxsortid())) {
						map.put("remark", "金税税收分类编码为空，请维护商品信息");
					}else {
						if (StringUtils.isEmpty(goodsInfo.getJstaxsortname())) {
							map.put("remark", "金税税收分类名称为空，请维护商品信息");
						}
					}
				}
				map.put("jstaxsortid", goodsInfo.getJstaxsortid());
				map.put("jstaxsortname", goodsInfo.getJstaxsortname());
			}
			map.put("unitname", (salesInvoiceBillDetail.getUnitname() != null ? salesInvoiceBillDetail.getUnitname() : ""));
			BigDecimal unitnum = BigDecimal.ZERO;
			if(null!=salesInvoiceBillDetail.getUnitnum()){
				unitnum = salesInvoiceBillDetail.getUnitnum();
			}

			map.put("unitnum", unitnum);

			BigDecimal notaxamount=(salesInvoiceBillDetail.getNotaxamount() != null ? salesInvoiceBillDetail.getNotaxamount() : BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP);
			//默认状态下未税单价为零
			BigDecimal notaxprice=BigDecimal.ZERO;
			if(BigDecimal.ZERO.compareTo(unitnum)!=0){
				notaxprice= notaxamount.divide(unitnum,9,BigDecimal.ROUND_HALF_UP);;
			}
			map.put("jsnotaxprice", notaxprice);
			//未税金额
			map.put("notaxamount", notaxamount);

			BigDecimal taxrate=BigDecimal.ZERO;
			if(StringUtils.isNotEmpty(salesInvoiceBillDetail.getTaxtype())){
				TaxType taxType = getTaxType(salesInvoiceBillDetail.getTaxtype());
				if(null!=taxType){
					salesInvoiceBillDetail.setTaxtypename(taxType.getName());

					if(null!=taxType.getRate()){
						taxrate=taxType.getRate().divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
					}
				}
			}
			map.put("taxrate", taxrate);
			map.put("taxname", (salesInvoiceBillDetail.getTaxtypename() != null ? salesInvoiceBillDetail.getTaxtypename() : ""));
			map.put("tax", (salesInvoiceBillDetail.getTax() != null ? salesInvoiceBillDetail.getTax() : BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));
			result.add(map);
		}
		return result;
	}

	/**
	 * 导入并生成航天开票对接文件
	 * @param
	 * @return
	 * @throws
	 * @author
	 * @date
	 */
	public String importAndCreateSalesInvoiceBillExcelForHTKP()throws Exception{
		Map resultMap=new HashMap();
		String exportid = request.getParameter("exportid");
		if(null==exportid || "".equals(exportid.trim())){
			resultMap.put("msg","未能找到相关单据信息");
			addJSONObject( resultMap);
			return SUCCESS;
		}
		String checker=request.getParameter("checkerid");
		if(null==checker || "".equals(checker.trim())){
			resultMap.put("msg","未能找到复核人信息");
			addJSONObject( resultMap);
			return SUCCESS;
		}
		Personnel checkerPersonnel=getPersonnelInfoById(checker);
		if(null==checkerPersonnel || StringUtils.isEmpty(checkerPersonnel.getName())){
			resultMap.put("msg","未能找到复核人信息");
			addJSONObject( resultMap);
			return SUCCESS;
		}
		String receipter=request.getParameter("receipterid");
		if(null==receipter || "".equals(receipter.trim())){
			resultMap.put("msg","未能找到收款人信息");
			addJSONObject( resultMap);
			return SUCCESS;
		}
		String jsgoodsversion=request.getParameter("jsgoodsversion");
		if(null==jsgoodsversion || "".equals(jsgoodsversion.trim())){
			jsgoodsversion="12.0";
		}
		jsgoodsversion=jsgoodsversion.trim();
		Personnel receipterPersonnel=getPersonnelInfoById(receipter);
		if(null==receipterPersonnel || StringUtils.isEmpty(receipterPersonnel.getName())){
			resultMap.put("msg","未能找到收款人信息");
			addJSONObject( resultMap);
			return SUCCESS;
		}
		String exportTitle=request.getParameter("title");
		if(null==exportTitle || "".equals(exportTitle.trim())){
			exportTitle="销售开票EXCEL转金税XML导入格式";
		}
		//默认消息
		resultMap.put("msg","生成航天开票导入文件失败");

		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		if(null==paramList){
			resultMap.put("msg","无法读取上传文件内容");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("单据号".equals(str)){
				paramList2.add("billid");
			}
			else if("金税税收分类编码".equals(str)){
				paramList2.add("jstaxsortid");
			}
			else if("商品编码".equals(str)){
				paramList2.add("goodsid");
			}
			else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}
			else if("单位".equals(str)){
				paramList2.add("unitname");
			}
			else if("数量".equals(str)){
				paramList2.add("unitnum");
			}
			else if("未税单价".equals(str)){
				paramList2.add("notaxprice");
			}
			else if("未税金额".equals(str)){
				paramList2.add("notaxamount");
			}
			else if("税额".equals(str)){
				paramList2.add("tax");
			}
			else if("税率".equals(str)){
				paramList2.add("taxrate");
			}
			else if("税率名称".equals(str)){
				paramList2.add("taxtypename");
			}
			else{
				paramList2.add("null");
			}
		}
		List<String> dataCellList = new ArrayList<String>();
		dataCellList.add("billid");
		dataCellList.add("jstaxsortid");
		dataCellList.add("goodsid");
		dataCellList.add("goodsname");
		dataCellList.add("unitname");
		dataCellList.add("unitnum");
		dataCellList.add("notaxprice");
		dataCellList.add("notaxamount");
		dataCellList.add("tax");
		dataCellList.add("taxrate");
		dataCellList.add("taxtypename");
		dataCellList.add("errormessage");


		String diffamountlog="";
		if(paramList.size() == paramList2.size()){
			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据

			if(null==list || list.size()==0){
				resultMap.put("msg","无法读取上传文件内容");
				addJSONObject(resultMap);
				return SUCCESS;
			}
			Map checkResultMap=salesInvoiceBillService.andCheckSalesInvoiceBillListByImportForHTKP(exportid,list);
			if(null==checkResultMap){
				resultMap.put("msg","校验上传文件内容数据失败");
				addJSONObject(resultMap);
				return SUCCESS;
			}
			Boolean checkFlag=(Boolean)checkResultMap.get("flag");
			List<Map<String,Object>> errorDataList=new ArrayList<Map<String, Object>>();
			if(!checkFlag){
				if(checkResultMap.containsKey("errorDataList")) {
					errorDataList = (List<Map<String, Object>>) checkResultMap.get("errorDataList");
				}
				if(errorDataList.size()>0){
					String msg="单据检查不通过";
					Boolean isbillidcheck=(Boolean)checkResultMap.get("isbillidcheck");
					if(!isbillidcheck){
						msg=msg+"，excel里单据号不一致";
						resultMap.put("msg",msg);
					}
					if(errorDataList.size()>0){
						String outTplFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/salesinvoicebillToHTKPXmlTpl.xls");
						IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
						String fileid=attachFileService.createExcelAndAttachFile(errorDataList, dataCellList, outTplFilePath,"销售开票金税XML格式转换失败");
						resultMap.put("datafileid",fileid);
					}
					addJSONObject(resultMap);
					return SUCCESS;
				}
				String msg=(String)checkResultMap.get("msg");
				if(null==msg){
					msg="";
				}
				resultMap.put("msg","单据检查未通过"+msg);
				addJSONObject(resultMap);
				return SUCCESS;
			}
			SalesInvoiceBill billData=(SalesInvoiceBill)checkResultMap.get("salesInvoiceBill");

			Map billDataMap= PropertyUtils.describe(billData);

			List<Map> detailMapList = null;
			//开票明细是否存在
			if(checkResultMap.containsKey("billDetailList")){
				detailMapList=(List<Map>) checkResultMap.get("billDetailList");
			}
			if(null==detailMapList || detailMapList.size()==0){
				resultMap.put("msg","未能找到相关订单明细数据");
				addJSONObject(resultMap);
				return SUCCESS;
			}
			Customer customerInfo=billData.getCustomerInfo();
			if(null==customerInfo){
				customerInfo=new Customer();
			}
			String tmpstr=null;
			tmpstr=MessageFormat.format("{0} {1}",
					ValueUtils.getValueOrEmpty(customerInfo.getAddress()),
					ValueUtils.getValueOrEmpty(customerInfo.getMobile()));
			billDataMap.put("customeradrrphone", tmpstr);

			String tplFileDir = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/ftl/");
			Map tplDataMap=new HashMap();
			tplDataMap.put("companyName",getSysParamValue("COMPANYNAME"));
			tplDataMap.put("billOrderData",billData);
			tplDataMap.put("detailList",detailMapList);
			tplDataMap.put("billchecker",checkerPersonnel.getName());
			tplDataMap.put("billreceipter",receipterPersonnel.getName());
			tplDataMap.put("billgoodsname","");
			tplDataMap.put("customerInfo",customerInfo);
			tplDataMap.put("jsgoodsversion",jsgoodsversion);
			tplDataMap.put("cutStringFunc",new CutStringMethodModel());
			tplDataMap.put("paddingStringFunc",new PaddingStringMethodModel());
			//创建freemarker配置实例
			Configuration config=new Configuration();
			config.setNumberFormat("#.######");
			config.setDirectoryForTemplateLoading(new File(tplFileDir));
			config.setDefaultEncoding("UTF-8");
			config.setTemplateUpdateDelay(0);
			Template template=config.getTemplate("salesinvoice_htkp_xml.ftl");
			template.setEncoding("UTF-8");
			StringWriter stringWriter = new StringWriter();
			template.process(tplDataMap, stringWriter);

			byte[] result =stringWriter.getBuffer().toString().getBytes("GB18030");

			IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
			String fileid=attachFileService.createFileAndAttachFile(result,"xml","htkp",exportTitle);

			String resultmsg="》》销售开票EXCEL数据转换金税XML格式成功《《";
			if(checkResultMap.containsKey("diffamountmsg")){
				diffamountlog=(String)checkResultMap.get("diffamountmsg");
				if(!"".equals(diffamountlog.trim())) {
					resultmsg = resultmsg + "<br><br>注意：" + diffamountlog;

					addLog(diffamountlog.trim());
				}
			}

			resultMap.put("msg",resultmsg);
			resultMap.put("datafileid",fileid);

			SysUser sysUser=getSysUser();
			SalesInvoiceBill upBillExportTimes=new SalesInvoiceBill();
			upBillExportTimes.setId(billData.getId());
			upBillExportTimes.setJxexporttimes(1);
			upBillExportTimes.setJxexportuserid(sysUser.getUserid());
			upBillExportTimes.setJxexportusername(sysUser.getName());
			upBillExportTimes.setJxexportdatetime(new Date());
			salesInvoiceBillService.updateOrderJSExportTimes(upBillExportTimes);
		}
		addJSONObject(resultMap);

		return SUCCESS;
	}


	/**
	 * 查看金税开票参数配置页面
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 20, 2017
	 */
	public String showJSKPSysParamConfigPage() throws Exception{
		String jskpDefaultReceipter=getSysParamValue("JSKPDefaultReceipter");
		request.setAttribute("jskpDefaultReceipter",jskpDefaultReceipter);
		String jskpDefaultChecker=getSysParamValue("JSKPDefaultChecker");
		request.setAttribute("jskpDefaultChecker",jskpDefaultChecker);
		return SUCCESS;
	}

	/**
	 * 更新金税开票人员相关系统参数配置
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 20, 2017
	 */
	public String updateJSKPSysParamConfig() throws Exception{
		String receipterid=request.getParameter("receipterid");
		String receiptername=request.getParameter("receiptername");
		String checkerid=request.getParameter("checkerid");
		String checkername=request.getParameter("checkername");
		Map resultMap=new HashMap();
		Map paramMap=new HashMap();
		paramMap.put("receipterid",receipterid);
		paramMap.put("receiptername",receiptername);
		paramMap.put("checkerid",checkerid);
		paramMap.put("checkername",checkername);

		resultMap=salesInvoiceBillService.updateJSKPSysParamConfig(paramMap);
		Boolean flag = false;
		if(resultMap.containsKey("flag")){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag",flag);
			}
		}
		addLog("更新系统参数“JSKPDefaultReceipter”和“JSKPDefaultChecker”值" , flag);
		addJSONObject(resultMap);
		return SUCCESS;
	}


	/**
	 * 销售开票明细导出
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Mar 02, 2018
	 */
	public void exportSalesInvoiceBillData() throws Exception {

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
		firstMap.put("invoiceno","发票号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("billtype", "开票类型");
		firstMap.put("iswriteoff", "核销状态");
		firstMap.put("sourceid", "来源单据编号");
		firstMap.put("taxamount", "含税总金额");
		firstMap.put("customeramount", "客户余额");
		firstMap.put("statusname", "状态");
		firstMap.put("goodsid","商品编码");
		firstMap.put("goodsname","商品名称");
		firstMap.put("isdiscount","是否折扣");
		firstMap.put("barcode","条形码");
		firstMap.put("brandname", "商品品牌");
		firstMap.put("model", "规格型号");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "单位");
		firstMap.put("unitnum","数量");
		firstMap.put("dtaxprice","单价");
		firstMap.put("costprice","成本单价");
		firstMap.put("auxnumdetail", "辅数量");
		firstMap.put("dtaxamount", "金额");
		firstMap.put("dnotaxprice", "未税单价");
		firstMap.put("dnotaxamount", "未税金额");
		firstMap.put("taxtypename", "税种");
		firstMap.put("dtax", "税额");
		firstMap.put("dremark", "备注");
		result.add(firstMap);

		List<SalesInvoiceBillExport> list = salesInvoiceBillService.getSalesInvoiceBillExportData(pageMap);

		if(list.size()!=0){
			for(SalesInvoiceBillExport salesInvoiceBillExport :list){
			    if("1".equals(salesInvoiceBillExport.getIswriteoff())){
			    	salesInvoiceBillExport.setIswriteoff("已核销");
				}else if("0".equals(salesInvoiceBillExport.getIswriteoff())){
					salesInvoiceBillExport.setIswriteoff("未核销");
				}

				if("1".equals(salesInvoiceBillExport.getBilltype())){
			    	salesInvoiceBillExport.setBilltype("正常开票");
				}else if("2".equals(salesInvoiceBillExport.getBilltype())){
			    	salesInvoiceBillExport.setBilltype("预开票");
				}

				if("0".equals(salesInvoiceBillExport.getIsdiscount())){
					salesInvoiceBillExport.setIsdiscount("否");
				}else if("1".equals(salesInvoiceBillExport.getIsdiscount())){
					salesInvoiceBillExport.setIsdiscount("是");
				}else if("2".equals(salesInvoiceBillExport.getIsdiscount())){
					salesInvoiceBillExport.setIsdiscount("返利折扣");
				}

				SysCode sysCode=getBaseSysCodeService().showSysCodeInfo(salesInvoiceBillExport.getStatus(),"status");
			    if(sysCode!=null){
			    	salesInvoiceBillExport.setStatusname(sysCode.getCodename());
				}

				GoodsInfo goodsInfo = salesInvoiceBillExport.getGoodsInfo();
				if(null!=goodsInfo){
					salesInvoiceBillExport.setGoodsname(goodsInfo.getName());
					salesInvoiceBillExport.setBarcode(goodsInfo.getBarcode());
					salesInvoiceBillExport.setModel(goodsInfo.getModel());
					salesInvoiceBillExport.setBoxnum(goodsInfo.getBoxnum());
					salesInvoiceBillExport.setBrandname(goodsInfo.getBrandName());
				}

				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(salesInvoiceBillExport);
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

