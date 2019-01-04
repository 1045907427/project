/**
 * @(#)SalesInvoiceAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 1, 2013 chenwei 创建版本
 */
package com.hd.agent.account.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.model.SalesInvoiceDetail;
import com.hd.agent.account.service.ISalesInvoiceService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;



/**
 * 
 * 销售发票action
 * @author chenwei
 */
public class SalesInvoiceAction extends BaseFilesAction{
	/**
	 * 销售发票
	 */
	private SalesInvoice salesInvoice;
	
	private CustomerPushBalance customerPushBalance;
	
	private ISalesInvoiceService salesInvoiceService;

	public ISalesInvoiceService getSalesInvoiceService() {
		return salesInvoiceService;
	}

	public void setSalesInvoiceService(ISalesInvoiceService salesInvoiceService) {
		this.salesInvoiceService = salesInvoiceService;
	}
	
	public SalesInvoice getSalesInvoice() {
		return salesInvoice;
	}

	public void setSalesInvoice(SalesInvoice salesInvoice) {
		this.salesInvoice = salesInvoice;
	}
	
	public CustomerPushBalance getCustomerPushBalance() {
		return customerPushBalance;
	}

	public void setCustomerPushBalance(CustomerPushBalance customerPushBalance) {
		this.customerPushBalance = customerPushBalance;
	}

	/**
	 * 显示销售发票新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 2, 2013
	 */
	public String showSalesInvoiceAddPage() throws Exception{
		request.setAttribute("type", "add");
		return "success";
	}
	/**
	 * 显示新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 5, 2013
	 */
	public String salesInvoiceAddPage() throws Exception{
		return "success";
	}
	/**
	 * 显示参照上游单据查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 2, 2013
	 */
	public String showSalesInvoiceRelationUpperPage() throws Exception{
		return "success";
	}
	/**
	 * 显示上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 2, 2013
	 */
	public String showSalesInvoiceSourceListPage() throws Exception{
		//1销售发货回单2销售退货通知单
		String sourcetype = request.getParameter("sourcetype");
		request.setAttribute("sourcetype", sourcetype);
		return "success";
	}
	/**
	 * 根据来源单据编号和来源类型生成销售发票
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 2, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=2)
	public String addSalesInvoiceByRefer() throws Exception{
		String ids = request.getParameter("ids");
		String customerid = request.getParameter("customerid");
		String iswriteoff = request.getParameter("iswriteoff");
		Map map = salesInvoiceService.addSalesInvoiceByReceiptAndRejectbill(ids,customerid,iswriteoff);
		addJSONObject(map);
		String id = "";
		if(null!=map){
			id = (String) map.get("id");
		}
		addLog("销售发票新增 编号："+id, map);
		return "success";
	}
	/**
	 * 显示销售发票查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 2, 2013
	 */
	public String showSalesInvoiceViewPage() throws Exception{
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
	 * 显示销售发票查看详细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 2, 2013
	 */
	public String salesInvoiceViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = salesInvoiceService.getSalesInvoiceInfo(id);
		SalesInvoice salesInvoice = (SalesInvoice) map.get("salesInvoice");
		
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		
		if(null!=salesInvoice){
			List list =  (List) map.get("detailList");
			String jsonStr = JSONUtils.listToJsonStrFilter(list);
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			request.setAttribute("statusList", statusList);
			request.setAttribute("salesInvoice", salesInvoice);
			request.setAttribute("detailList", jsonStr);
			request.setAttribute("listSize", list.size());
			//获取销售部门
			List deptList = getBaseDepartMentService().getDeptListByOperType("4");
			request.setAttribute("deptList", deptList);
			request.setAttribute("settletype", getSettlementListData());
			request.setAttribute("paytype", getPaymentListData());
			request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
			String hasblance="0";
			if("3".equals(salesInvoice.getStatus()) || "4".equals(salesInvoice.getStatus())){
				if(salesInvoiceService.getSalesInvoiceHasBlance(salesInvoice.getId())>0){
					hasblance="1";
				}				
			}
			request.setAttribute("hasblance", hasblance);

			
			return "success";
		}else{
			return "addSuccess";
		}
		
	}
	/**
	 * 显示销售发票列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 2, 2013
	 */
	public String showSalesInvoiceListPage() throws Exception{
		return "success";
	}
	/**
	 * 获取销售发票列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 2, 2013
	 */
	public String showSalesInvoiceList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String salesarea=(String)map.get("salesarea");
		String[] salesareaArr  =null; 
		if(null!=salesarea){
			salesareaArr = salesarea.split(",");
	    }
		map.put("salesareaArr", salesareaArr);
		pageMap.setCondition(map);
		PageData pageData = salesInvoiceService.showSalesInvoiceList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	
	/**
	 * 显示尾差报表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 13, 2014
	 */
	public String showTailamountReportPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取尾差报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 13, 2014
	 */
	public String getTailamountReportPageData()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesInvoiceService.getTailamountReportPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出尾差报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 13, 2014
	 */
	public void exportTailamountReportData()throws Exception{
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
		PageData pageData = salesInvoiceService.getTailamountReportPageData(pageMap);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("salesareaname", "销售区域");
		firstMap.put("salesdeptname", "销售部门");
		firstMap.put("tailamount", "尾差金额");
		result.add(firstMap);
		
		if(pageData.getList().size() != 0){
			for(SalesInvoice salesInvoice : new ArrayList<SalesInvoice>(pageData.getList())){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(salesInvoice);
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
		if(pageData.getFooter().size() != 0){
			for(SalesInvoice salesInvoice : new ArrayList<SalesInvoice>(pageData.getFooter())){
				if(null != salesInvoice){
					Map<String, Object> retMap = new LinkedHashMap<String, Object>();
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2 = PropertyUtils.describe(salesInvoice);
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
		}
		ExcelUtils.exportExcel(result, title);
	}
	/**
	 * 显示销售发票修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 3, 2013
	 */
	public String showSalesInvoiceEditPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		Map map = salesInvoiceService.getSalesInvoiceInfo(id);
		SalesInvoice salesInvoice = (SalesInvoice) map.get("salesInvoice");
        request.setAttribute("writeoffdate",salesInvoice.getWriteoffdate());
		request.setAttribute("iswriteoff", salesInvoice.getIswriteoff());
		return "success";
	}
	/**
	 * 显示销售发票修改详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 3, 2013
	 */
	public String salesInvoiceEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = salesInvoiceService.getSalesInvoiceInfo(id);
		SalesInvoice salesInvoice = (SalesInvoice) map.get("salesInvoice");
//		List list =  (List) map.get("detailList");
//		String jsonStr = JSONUtils.listToJsonStrFilter(list);
		List customerArr = (List) map.get("customerArr");
		request.setAttribute("customerArr", customerArr);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("salesInvoice", salesInvoice);
//		request.setAttribute("detailList", jsonStr);
//		if(null!=list){
//			request.setAttribute("listSize", list.size());
//		}
		//获取销售部门
		List deptList = getBaseDepartMentService().getDeptListByOperType("4");
		request.setAttribute("deptList", deptList);
		request.setAttribute("settletype", getSettlementListData());
		request.setAttribute("paytype", getPaymentListData());
		request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
		
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);		

		String hasblance="0";
		if("3".equals(salesInvoice.getStatus()) || "4".equals(salesInvoice.getStatus())){
			if(salesInvoiceService.getSalesInvoiceHasBlance(salesInvoice.getId())>0){
				hasblance="1";
			}				
		}
		request.setAttribute("hasblance", hasblance);
		
		if(null!=salesInvoice){
			if("1".equals(salesInvoice.getStatus()) || "2".equals(salesInvoice.getStatus()) || "6".equals(salesInvoice.getStatus()) || "5".equals(salesInvoice.getStatus())){
				return "success";
			}else{
				return "viewSuccess";
			}
		}else{
			return "addSuccess";
		}
	}
	/**
	 * 显示销售发票明细修改
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 3, 2013
	 */
	public String showSalesInvoiceDetailEditPage() throws Exception{
		return "success";
	}
	/**
	 * 销售发票删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 5, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=4)
	public String deleteSalesInvoice() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesInvoiceService.deletesalesInvoice(id);
		addJSONObject("flag", flag);
		addLog("销售发票删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 销售发票修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=3)
	public String editSalesInvoiceSave() throws Exception{
		String delgoodsids = request.getParameter("delgoodsids");
		Map map = salesInvoiceService.editSalesInvoice(salesInvoice, delgoodsids);
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean)map.get("flag");
		if(flag && "saveaudit".equals(saveaudit)){
			boolean auditflag = salesInvoiceService.auditSalesInvoice(salesInvoice.getId());
			map.put("auditflag", auditflag);
			addLog("销售发票保存审核 编号："+salesInvoice.getId(), auditflag);
			

			String hasblance="0";

			if(salesInvoiceService.getSalesInvoiceHasBlance(salesInvoice.getId())>0){
				hasblance="1";
			}
			request.setAttribute("hasblance", hasblance);
			map.put("hasblance", hasblance);
		}else{
			addLog("销售发票修改 编号："+salesInvoice.getId(), flag);
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 销售发票审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=3)
	public String auditSalesInvoice() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesInvoiceService.auditSalesInvoice(id);
		addJSONObject("flag", flag);
		addLog("销售发票审核 编号："+id, flag);
		return "success";
	}
	/**
	 * 销售发票反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=3)
	public String oppauditSalesInvoice() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesInvoiceService.oppauditSalesInvoice(id);
		addJSONObject("flag", flag);
		addLog("销售发票反审 编号："+id, flag);
		return "success";
	}
	/**
	 * 显示折扣调整页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 19, 2013
	 */
	public String showSalesInvoiceDiscountPage() throws Exception{
		
		return "success";
	}
	/**
	 * 销售发票提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitSalesInvoicePageProcess() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesInvoiceService.submitSalesInvoicePageProcess(id);
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 显示上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 29, 2013
	 */
	public String showSalesInvoiceSourceListReferPage() throws Exception{
//		String sourcetype = request.getParameter("sourcetype");
		String id = request.getParameter("id");
		List list = salesInvoiceService.showSalesInvoiceSourceListReferData(id, "");
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("list", jsonStr);
		Map map = salesInvoiceService.getSalesInvoiceInfo(id);
		SalesInvoice salesInvoice = (SalesInvoice)map.get("salesInvoice");
		if(null != salesInvoice){
			request.setAttribute("invoiceStatus", salesInvoice.getStatus());
		}
//		request.setAttribute("sourcetype", sourcetype);
		return "success";
	}
	
	/**
	 * 显示发票来源明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 28, 2014
	 */
	public String showSalesInvoiceSourceDetailPage()throws Exception{
		String id = request.getParameter("id");
		List list = salesInvoiceService.getSalesInvoiceSourceDetailList(id);
		String jsonStr = JSONUtils.listToJsonStrFilter(list);
		request.setAttribute("detailList", jsonStr);
		return SUCCESS;
	}
	
	
	/**
	 * 对未已开票 未核销的销售发票 申请核销
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 10, 2013
	 */
	public String salesInvoiceApplyWriteOff() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesInvoiceService.updateSalesInvoiceApplyWriteOff(id);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 对未已开票 未核销的销售发票 批量申请核销
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 23, 2014
	 */
	public String salesInvoiceMuApplyWriteOff()throws Exception{
		String ids = request.getParameter("ids");
		Map map = new HashMap();
		String failids = "",applyids = "";
		int sucNum = 0;
		if(StringUtils.isNotEmpty(ids)){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				SalesInvoice salesInvoice = salesInvoiceService.getPureSalesInvoicePureInfo(id);
				if(null != salesInvoice && !"3".equals(salesInvoice.getApplytype())){
					boolean flag = salesInvoiceService.updateSalesInvoiceApplyWriteOff(id);
					if(!flag){
						if(StringUtils.isEmpty(failids)){
							failids = id;
						}else{
							failids += "," + id;
						}
					}else{
						sucNum++;
					}
				}else{
					if(StringUtils.isEmpty(applyids)){
						applyids = id;
					}else{
						applyids += "," + id;
					}
				}
			}
		}
		map.put("sucNum", sucNum);
		map.put("failids", failids);
		map.put("applyids", applyids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 销售发票冲差
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 5, 2013
	 */
	public String showSalesInvoicePushAddPage() throws Exception{
		String id = request.getParameter("id");
		Map map = salesInvoiceService.getSalesInvoiceInfo(id);
		SalesInvoice salesInvoice = (SalesInvoice) map.get("salesInvoice");
		if(null!=salesInvoice){
			request.setAttribute("salesInvoice", salesInvoice);
			List customerArr = (List) map.get("customerArr");
			request.setAttribute("customerArr", customerArr);
		}
		List pushList = getBaseSysCodeService().showSysCodeListByType("pushtypeprint");
		request.setAttribute("pushList",pushList);
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 销售发票冲差
	 * 冲差类型为总店冲差时， 按成本平摊到门店
	 * 冲差类型为门店时， 冲差到该门店中
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 5, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=3)
	public String addCustomerPushBanlanceBySalesInvoice() throws Exception{
		String pushtype = request.getParameter("pushtype");
		String invoiceid = request.getParameter("invoiceid");
		Map map = salesInvoiceService.addCustomerPushBanlanceBySalesInvoice(invoiceid, pushtype, customerPushBalance);
		addJSONObject(map);
		addLog("销售发票冲差 编号："+invoiceid, map);
		return "success";
	}
	/**
	 * 根据发票号或者发票单据编号 获取销售发票列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 20, 2013
	 */
	public String showSalesInvoiceListPageByIds() throws Exception{
		String id = request.getParameter("id");
		List list = salesInvoiceService.showSalesInvoiceListPageByIds(id);
		if(null!=list){
			String jsonStr = JSONUtils.listToJsonStrFilter(list);
			request.setAttribute("dataList", jsonStr);
		}
		return "success";
	}
	/**
	 * 显示销售发票返利页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年8月18日
	 */
	public String showSalesInvoiceRebatePage() throws Exception{
		String id = request.getParameter("id");
		SalesInvoice salesInvoice = salesInvoiceService.getPureSalesInvoicePureInfo(id);
		request.setAttribute("salesInvoice", salesInvoice);
		return "success";
	}
	/**
	 * 对销售发票进行返利冲差
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 23, 2013
	 */
	@UserOperateLog(key="SalesInvoice",type=3)
	public String addSalesInvoiceRebate() throws Exception{
		String id = request.getParameter("id");
		String rebateamountstr = request.getParameter("rebateamount");
		String remark = request.getParameter("remark");
		String subject = request.getParameter("subject");
		BigDecimal rebateamount = new BigDecimal(rebateamountstr);
		boolean flag = salesInvoiceService.addSalesInvoiceRebate(id, rebateamount,remark,subject);
		addJSONObject("flag", flag);
		addLog("销售发票返利 编号："+id+",返利："+rebateamountstr, flag);
		return "success";
	}
	/**
	 * 显示客户回单未核销页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 7, 2014
	 */
	public String showReceiptUnWriteOffPage() throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		request.setAttribute("invoiceid", invoiceid);
		Map map = salesInvoiceService.getSalesInvoiceInfo(invoiceid);
		SalesInvoice salesInvoice = (SalesInvoice) map.get("salesInvoice");
		request.setAttribute("receiptids", salesInvoice.getSourceid());
		return "success";
	}
	/**
	 * 获取客户回单未核销数据列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 7, 2014
	 */
	public String showReceiptUnWriteOffData() throws Exception{
		String invoiceid = request.getParameter("invoiceid");
		Map map = salesInvoiceService.getSalesInvoiceInfo(invoiceid);
		SalesInvoice salesInvoice = (SalesInvoice) map.get("salesInvoice");
		if(null!=salesInvoice){
			List list = salesInvoiceService.getReceiptUnWriteOffData(salesInvoice.getCustomerid());
			addJSONArray(list);
		}
		return "success";
	}
	/**
	 * 导出销售发票清单
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 8, 2014
	 */
	public void exportSalesInvoiceList() throws Exception{
		String exportid = request.getParameter("exportid");
		Map map = salesInvoiceService.getSalesInvoiceInfo(exportid);
		SalesInvoice salesInvoice = (SalesInvoice) map.get("salesInvoice");
		List list =  (List) map.get("detailList");
		ExcelUtils.exportExcel(exportSalesInvoiceListFilter(list), "销售发票清单");
	}
	/**
	 * 数据转换，list专程符合excel导出的数据格式(分商品)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSalesInvoiceListFilter(List list) throws Exception{
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
		for(SalesInvoiceDetail salesInvoiceDetail : new ArrayList<SalesInvoiceDetail>(list)){
			Map<String,Object> map = new LinkedHashMap<String,Object>();
			map.put("goodsid", salesInvoiceDetail.getGoodsid());
			GoodsInfo goodsInfo = salesInvoiceDetail.getGoodsInfo();
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				map.put("model",goodsInfo.getModel());
			}
			map.put("unitname", (salesInvoiceDetail.getUnitname() != null ? salesInvoiceDetail.getUnitname() : ""));
			BigDecimal unitnum = BigDecimal.ZERO ;
			if(null!=salesInvoiceDetail.getUnitnum()){
				unitnum = salesInvoiceDetail.getUnitnum();
			}
			
			map.put("unitnum",unitnum);
			
			//map.put("price", (salesInvoiceDetail.getTaxprice() != null ?salesInvoiceDetail.getTaxprice() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			map.put("noprice", (salesInvoiceDetail.getNotaxprice() != null ?salesInvoiceDetail.getNotaxprice() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			//map.put("taxamount", (salesInvoiceDetail.getTaxamount() != null ? salesInvoiceDetail.getTaxamount() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			map.put("notaxamount", (salesInvoiceDetail.getNotaxamount() != null ? salesInvoiceDetail.getNotaxamount() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			map.put("taxname", (salesInvoiceDetail.getTaxtypename() != null ? salesInvoiceDetail.getTaxtypename() : ""));
			map.put("tax", (salesInvoiceDetail.getTax() != null ? salesInvoiceDetail.getTax() : BigDecimal.ZERO).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			result.add(map);
		}
		return result;
	}
	/**
	 * 回退销售发票（作废，中止）
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 13, 2014
	 */
	@UserOperateLog(key="SalesInvoice",type=3)
	public String salesInvoiceCancel() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = salesInvoiceService.updateSalesInvoiceCancel(id);
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
			addLog("销售发票回退 成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 显示开票抽单回退列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 13, 2014
	 */
	public String showSalesInvoiceCancelListPage() throws Exception{
		return "success";
	}
	/**
	 * 显示客户销售发票列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 20, 2014
	 */
	public String showSalesInvoiceListPageByCustomer() throws Exception{
		String customerid = request.getParameter("customerid");
		List list = salesInvoiceService.showSalesInvoiceListPageByCustomer(customerid);
		String jsonStr = JSONUtils.listToJsonStrFilter(list);
		request.setAttribute("dataList", jsonStr);
		return "success";
	}
	/**
	 * 追加销售发票
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 20, 2014
	 */
	@UserOperateLog(key="SalesInvoice",type=2)
	public String addToSalesInvoiceByCustomer() throws Exception{
		String customerid = request.getParameter("customerid");
		String billid = request.getParameter("billid");
		String ids = request.getParameter("ids");
		Map map = salesInvoiceService.addToSalesInvoiceByReceiptAndRejectbill(ids,customerid,billid);
		addJSONObject(map);
		String id = "";
		if(null!=map){
			id = (String) map.get("id");
		}
		addLog("销售发票追加 编号："+billid, map);
		return "success";
	}
	
	/**
	 * 删除开票抽单回退
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 5, 2014
	 */
	@UserOperateLog(key="SalesInvoice",type=4)
	public String salesInvoiceMutiDelete()throws Exception{
		String ids = request.getParameter("ids");
		Map map = salesInvoiceService.salesInvoiceMutiDelete(ids);
		addJSONObject(map);
		if(StringUtils.isNotEmpty((String)map.get("sucids"))){
			addLog("开票抽单回退删除 编号："+(String)map.get("sucids"), true);
		}else{
			addLog("开票抽单回退删除 编号："+ids, false);
		}
		return SUCCESS;
	}
	/**
	 * 显示销售发票明细列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月2日
	 */
	public String showSalesInvoiceDetailPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 获取销售发票明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月3日
	 */
	public String showSalesInvoiceDetailData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesInvoiceService.showSalesInvoiceDetailData(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 导出销售发票明细数据
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月3日
	 */
	public void exportSalesInvoiceDetailData() throws Exception{
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
		firstMap.put("billid", "销售发票编号");
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
		PageData pageData = salesInvoiceService.showSalesInvoiceDetailData(pageMap);
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
	 * 销售发票中删除明细，回写来源单据开票状态，开票金额、未开票金额等
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 24, 2014
	 */
	@UserOperateLog(key="SalesInvoice",type=0)
	public String deleteSalesInvoiceSource()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		Map retmap = salesInvoiceService.deleteSalesInvoiceSource(map);
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
     * 根据销售核销编码判断是否允许核销
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-18
     */
    public String getCancelSalesInvoiceFlag()throws Exception{
        String invoiceid = request.getParameter("invoiceid");
        Map map = salesInvoiceService.getCancelSalesInvoiceFlag(invoiceid);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 根据销售核销编码判断是否允许核销
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-18
     */
    public String getUnrelateSalesInvoiceFlag()throws Exception{
        String invoiceid = request.getParameter("invoiceid");
        Map map = salesInvoiceService.getUnrelateSalesInvoiceFlag(invoiceid);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 根据销售核销编码判断是否允许关联收款单
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-18
     */
    public String getRelateSalesInvoiceFlag()throws Exception{
        String invoiceid = request.getParameter("invoiceid");
        Map map = salesInvoiceService.getRelateSalesInvoiceFlag(invoiceid);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 根据销售核销编码判断是否允许反审
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-18
     */
    public String getOppauditFlag()throws Exception{
        String invoiceid = request.getParameter("invoiceid");
        Map map = salesInvoiceService.getOppauditFlag(invoiceid);
        addJSONObject(map);
        return SUCCESS;
    }

	/**
	 * 根据核销编号获取核销明细分页列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-02
	 */
	public String getSalesInvoiceDetailList()throws Exception{
		String id = request.getParameter("id");
		Map map = new HashMap();
		map.put("billid",id);
		pageMap.setCondition(map);
		PageData pageData = salesInvoiceService.getSalesInvoiceDetailList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
/*------------------------普通版特有-----------------------*/
    /**
     * 检查选中的销售发票是否允许申请开票
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/02
     */
    public String checkSalesInvoiceCanMuApplyInvoice()throws Exception{
        String ids = request.getParameter("ids");
		Map map = salesInvoiceService.checkSalesInvoiceCanMuApplyInvoice(ids);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 申请开票
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015/03/02
     */
	@UserOperateLog(key="SalesInvoice",type=2)
    public String doSalesInvoiceMuApplyInvoice()throws Exception{
        String ids = request.getParameter("ids");
        Map map = new HashMap();
        String failids = "",salesinvoicebillids="",msglog = "";
        int sucNum = 0;
        if(StringUtils.isNotEmpty(ids)){
            String[] idArr = ids.split(",");
            for(String id : idArr){
                Map map1 = salesInvoiceService.doSalesInvoiceMuApplyInvoice(id);
                if(!map1.get("flag").equals(true)){
                    if(StringUtils.isEmpty(failids)){
                        failids = id;
                    }else{
                        failids += "," + id;
                    }
					if(StringUtils.isEmpty(msglog)){
						msglog = "销售核销 编号："+id+"申请开票 失败";
					}else{
						msglog += "；" + "销售核销 编号："+id+"申请开票 失败";
					}
                }else{
                    String salesinvoicebillid = map1.get("id").toString();
                    if(StringUtils.isEmpty(salesinvoicebillids)){
                        salesinvoicebillids = salesinvoicebillid;
                    }else{
                        salesinvoicebillids += "," + salesinvoicebillid;
                    }
                    sucNum++;
					if(StringUtils.isEmpty(msglog)){
						msglog = "销售核销 编号："+id+"申请开票 编号："+salesinvoicebillid;
					}else{
						msglog += "；" + "销售核销 编号："+id+"申请开票 编号："+salesinvoicebillid;
					}
                }
            }
        }
        map.put("salesinvoicebillids", salesinvoicebillids);
        map.put("sucNum", sucNum);
        map.put("failids", failids);
        addJSONObject(map);
		addLog(msglog, true);
        return SUCCESS;
    }
/*------------------------普通版特有-----------------------*/
  /**
   * 销售核销单据明细税种统计
   * @return
   * @throws Exception
   * @author zhanghonghui 
   * @date 2016年3月9日
   */
  public String salesInvoiceDetailTaxtypeCount() throws Exception{
	  String invoiceidarrs=request.getParameter("invoiceidarrs");
	  List list=salesInvoiceService.getSalesInvoiceDetailTaxtypeCountList(invoiceidarrs);
	  addJSONArray(list);
	  return SUCCESS;
  }
}

