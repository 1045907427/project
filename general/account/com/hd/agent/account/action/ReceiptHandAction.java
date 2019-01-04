/**
 * @(#)ReceiptAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 24, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.action;

import java.math.BigDecimal;
import java.util.*;

import com.hd.agent.common.util.ExcelUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.account.model.ReceiptHand;
import com.hd.agent.account.model.ReceiptHandBill;
import com.hd.agent.account.model.ReceiptHandCustomer;
import com.hd.agent.account.service.IReceiptHandService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;

/**
 * 应收款交接单action
 * 
 * @author panxiaoxiao
 */
public class ReceiptHandAction extends BaseFilesAction{
	
	private IReceiptHandService receiptHandService;
	
	private ReceiptHand receiptHand;
	
	public IReceiptHandService getReceiptHandService() {
		return receiptHandService;
	}

	public void setReceiptHandService(IReceiptHandService receiptHandService) {
		this.receiptHandService = receiptHandService;
	}

	public ReceiptHand getReceiptHand() {
		return receiptHand;
	}

	public void setReceiptHand(ReceiptHand receiptHand) {
		this.receiptHand = receiptHand;
	}

	/**
	 * 显示收款交接单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public String showReceiptHandPage()throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 显示收款交接单查看页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public String showReceiptHandViewPage()throws Exception{
		String id = request.getParameter("id");
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		ReceiptHand receiptHand = receiptHandService.getReceiptHandInfo(id);
		request.setAttribute("receiptHand", receiptHand);
		return SUCCESS;
	}
	
	/**
	 * 显示收款交接单修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public String showReceiptHandEditPage()throws Exception{
		String id = request.getParameter("id");
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		ReceiptHand receiptHand = receiptHandService.getReceiptHandBaseInfo(id);
		request.setAttribute("receiptHand", receiptHand);
		Map colMap = getEditAccessColumn("t_account_receipt");
		request.setAttribute("showMap", colMap);
		return SUCCESS;
	}
	
	/**
	 * 显示回单交接新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public String showReceiptHandAddPage()throws Exception{
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return SUCCESS;
	}
	
	/**
	 * 生成应收交接单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	@UserOperateLog(key = "ReceiptHand", type = 2)
	public String addReceiptHand()throws Exception{
		String ids = request.getParameter("ids");
		Map map = receiptHandService.addReceiptHand(ids,false);
		String receipthandid = (null != map.get("id")) ? (String)map.get("id") : "";
		if(StringUtils.isNotEmpty(receipthandid)){
			addLog("交接单新增 编号：" + receipthandid, true);
		}else{
			addLog("交接单新增失败");
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示应收交接单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public String showReceiptHandListPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 获取应收交接单列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public String getReceiptHandList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = receiptHandService.getReceiptHandList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 根据交接单编号获取交接单单据明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public String getReceiptHandBillListByBillid()throws Exception{
		String id = request.getParameter("id");
		Map map = receiptHandService.getReceiptHandBillListByBillid(id);
		jsonResult = new HashMap();
		jsonResult.put("rows", map.get("list"));
		jsonResult.put("footer", map.get("footer"));
		return SUCCESS;
	}

	/**
	 * 根据回单交接编号导出单据明细列表
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-21
	 */
	public void exportReceiptHandBillDetailList()throws Exception{
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
		Map map1 = receiptHandService.getReceiptHandBillListByBillid(id);
		List<ReceiptHandBill> list = (List<ReceiptHandBill>)map1.get("list");
		List<ReceiptHandBill> footer = (List<ReceiptHandBill>)map1.get("footer");
		list.addAll(footer);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("billid", "交接单编号");
		firstMap.put("relatebillid", "单据编号");
		firstMap.put("saleorderid", "订单编号");
		firstMap.put("billtypename", "单据类型");
		firstMap.put("businessdate","业务日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("pcustomername", "总店");
		firstMap.put("customersortname", "客户分类");
		firstMap.put("salesareaname", "销售区域");
		firstMap.put("salesdeptname", "销售部门");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("amount", "应收金额");
		firstMap.put("isrecyclename","是否回收");
		firstMap.put("recycledate","回收日期");
		firstMap.put("remark", "备注");
		result.add(firstMap);

		if(list.size() != 0){
			for (ReceiptHandBill receiptHandBill : list) {
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(receiptHandBill);
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
	 * 根据交接单编号获取交接单客户明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public String getReceiptHandCustomerListByBillid()throws Exception{
		String id = request.getParameter("id");
		Map map = receiptHandService.getReceiptHandCustomerListByBillid(id);
		jsonResult = new HashMap();
		jsonResult.put("rows", map.get("list"));
		jsonResult.put("footer", map.get("footer"));
		return SUCCESS;
	}
	
	/**
	 * 删除应收款交接单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	@UserOperateLog(key = "ReceiptHand", type = 4)
	public String deleteReceiptHands()throws Exception{
		String ids = request.getParameter("ids");
		String msg = "",newids = "";
		int userNum = 0,lockNum = 0;
		if(StringUtils.isNotEmpty(ids)){
			String[] idsArr = ids.split(",");
			for(String id : idsArr){
				//判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
				if(!isLock("t_account_receipt", id)){
					//判断是否被引用
					if(!canTableDataDelete("t_account_receipt",id)){//true可以操作，false不可以操作
						userNum++;
					}
					else{
						if(StringUtils.isEmpty(newids)){
							newids = id;
						}else{
							newids += "," + id;
						}
					}
				}
				else{
					lockNum++;
				}
			}
		}
		if(userNum != 0){
			msg = userNum+"条记录被引用,不允许删除;";
		}
		if(lockNum != 0){
			if(StringUtils.isEmpty(msg)){
				msg = lockNum+"条记录网络互斥,不允许删除;";
			}else{
				msg += "<br>"+lockNum+"条记录网络互斥,不允许删除;";
			}
		}
		Map remap = new HashMap();
		if(StringUtils.isNotEmpty(newids)){
			Map map = receiptHandService.deleteReceiptHands(newids);
			String sucids = (String)map.get("sucids");
			if(StringUtils.isNotEmpty(sucids)){
				addLog("删除交接单 编号:" + sucids, true);
			}else{
				addLog("删除交接单 编号:" + sucids, false);
			}
			remap.putAll(map);
		}else{
			addLog("删除交接单 编号:" + ids, false);
			remap.put("sucnum", 0);
			remap.put("invalidNum", 0);
		}
		String remsg = (String)remap.get("msg");
		if(StringUtils.isEmpty(remsg)){
			remsg = msg;
		}else{
			remsg += "<br>" + msg;
		}
		remap.put("msg", remsg);
		addJSONObject(remap);
		return SUCCESS;
	}
	
	/**
	 * 修改交接单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	@UserOperateLog(key = "ReceiptHand", type = 3)
	public String editReceiptHand()throws Exception{
		String customer = request.getParameter("customerList");
		String bill = request.getParameter("billList");
		List<ReceiptHandCustomer> customerList = null;
		List<ReceiptHandBill> billList = null;
		if(null != customer){
			customerList = JSONUtils.jsonStrToList(customer, new ReceiptHandCustomer());
		}
		if(null != bill){
			billList = JSONUtils.jsonStrToList(bill, new ReceiptHandBill());
		}
		Map map = receiptHandService.editReceiptHand(receiptHand,billList,customerList);
		addLog("修改交接单 编号:" + receiptHand.getId(), map);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 审核应收款交接单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	@UserOperateLog(key = "ReceiptHand", type = 0)
	public String auditReceiptHands()throws Exception{
		String ids = request.getParameter("ids");
		Map map = receiptHandService.auditReceiptHands(ids);
		addLog("审核交接单 编号:" + map.get("id"), map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 保存并审核交接单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	@UserOperateLog(key = "ReceiptHand", type = 3)
	public String saveAuditReceiptHand()throws Exception{
		String customer = request.getParameter("customerList");
		String bill = request.getParameter("billList");
		List<ReceiptHandCustomer> customerList = null;
		List<ReceiptHandBill> billList = null;
		if(null != customer){
			customerList = JSONUtils.jsonStrToList(customer, new ReceiptHandCustomer());
		}
		if(null != bill){
			billList = JSONUtils.jsonStrToList(bill, new ReceiptHandBill());
		}
		Map map = receiptHandService.saveAuditReceiptHand(receiptHand,billList,customerList);
		addLog("保存并审核交接单 编号:" + receiptHand.getId(), map);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 反审交接单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	@UserOperateLog(key = "ReceiptHand", type = 3)
	public String oppauditReceiptHands()throws Exception{
		String ids = request.getParameter("ids");
		Map map = receiptHandService.oppauditReceiptHands(ids);
		addLog("反审交接单 编号:" + map.get("id"), map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示回单列表（回收）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
	public String showReceiptListByHandPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 获取已生成交接单的回单列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
	public String getReceiptListByHand()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = receiptHandService.getReceiptListByHand(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}

	/**
	 * 回单交接单据列表导出
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-09
	 */
	public void exportReceiptbyhandData()throws Exception{
		String groupcols = null;
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		Map queryMap = new HashMap();
		if(map.containsKey("groupcols") && null != map.get("groupcols")){
			groupcols = (String)map.get("groupcols");
			queryMap.put("groupcols",groupcols);
		}
		String query = (String) map.get("param");
		JSONObject object = JSONObject.fromObject(query);
		for (Object k : object.keySet()) {
			Object v = object.get(k);
			if(StringUtils.isNotEmpty((String) v)){
				queryMap.put(k.toString(), (String) v);
			}
		}
		queryMap.put("isflag", "true");
		pageMap.setCondition(queryMap);
		PageData pageData = receiptHandService.getReceiptListByHand(pageMap);
		List<ReceiptHandBill> list = pageData.getList();
		if(null != pageData.getFooter()){
			List<ReceiptHandBill> footer = pageData.getFooter();
			list.addAll(footer);
		}
		ExcelUtils.exportAnalysExcel(map, list);
	}
	
	/**
	 * 回收回单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
    @UserOperateLog(key = "ReceiptHand", type = 0)
	public String doBackReceiptHands()throws Exception{
		String billrows = request.getParameter("billrows");
		List<ReceiptHandBill> list = new ArrayList<ReceiptHandBill>();
		JSONArray json = JSONArray.fromObject(billrows);
		list = JSONArray.toList(json, ReceiptHandBill.class);
		Map map = receiptHandService.doBackReceiptHands(list);
        String sucreceiptids = (String)map.get("sucreceiptids");
        if(StringUtils.isEmpty(sucreceiptids)){
            addLog("回收单据", false);
        }else{
            addLog("回收单据 编号:" + sucreceiptids, true);
        }
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 根据回单编号显示交接单_客户明细列表(删除)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 3, 2014
	 */
	public String getReceiptHandCustomerListByBills()throws Exception{
		String relatebillid = request.getParameter("relatebillid");
		String billid = request.getParameter("billid");
		Map map = receiptHandService.getReceiptHandCustomerListByBills(billid,relatebillid);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示交接单添加发货回单页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 3, 2014
	 */
	public String showReceiptHandAddReceiptPage()throws Exception{
		String receipthandid = request.getParameter("receipthandid");
		String receiptids = request.getParameter("receiptids");
		request.setAttribute("receipthandid", receipthandid);
		request.setAttribute("receiptids", receiptids);
		return SUCCESS;
	}
	
	/**
	 * 根据选中的回单编码集转换生成客户明细列表，单据明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 3, 2014
	 */
	public String getReceiptHandDetailListMap()throws Exception{
		String receipthandid = request.getParameter("receipthandid");
		String sourceids = request.getParameter("sourceids");
		Map map = receiptHandService.getReceiptHandDetailListMap(receipthandid,sourceids);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/*-------------------新增回单交接单另一种方式-------------------------*/
	/**
	 * 显示回单交接单新增页面（另一种方式）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public String showReceiptHandAddAnotherPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 分客户获取回单列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public String getReceiptListGroupByCustomerForReceiptHand()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = receiptHandService.getReceiptListGroupByCustomerForReceiptHand(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示指定客户下的回单单据列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 12, 2014
	 */
	public String showReceiptListOfReceiptDetailPage()throws Exception{
		Map map = new HashMap();
		String queryJSON = request.getParameter("queryJSON");
		JSONObject jsonObject = JSONObject.fromObject(queryJSON);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = (String)it.next();
			String val = jsonObject.getString(key);
			if(StringUtils.isNotEmpty(val)){
				map.put(key, val);
			}
		}
		map.put("isflag", true);
		pageMap.setCondition(map);
		Map map2 = receiptHandService.getReceiptListOfReceiptDetail(pageMap);
		List list = (List)map2.get("list");
		BigDecimal totalreceipttaxamount = (BigDecimal)map2.get("totalreceipttaxamount");
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("totalreceipttaxamount", totalreceipttaxamount);
		request.setAttribute("customerid", jsonObject.getString("customerid"));
		return SUCCESS;
	}
	
	/**
	 * 为回单交接单获取已审核的回单列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public String getReceiptListForReceiptHand()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String receiptids = (null != map.get("receiptids")) ? (String)map.get("receiptids") : "";
		if(StringUtils.isNotEmpty(receiptids)){
			String[] idsArr = receiptids.split(",");
			map.put("idsArr", idsArr);
		}
		pageMap.setCondition(map);
		PageData pageData = receiptHandService.getReceiptListForReceiptHand(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示已选单据明细列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 13, 2014
	 */
	public String showReceiptHandAddDetailPage()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		String receiptids = (null != map.get("receiptids")) ? (String)map.get("receiptids") : "";
		if(StringUtils.isNotEmpty(receiptids)){
			String[] idsArr = receiptids.substring(0, receiptids.length()-1).split(",");
			map.put("idsArr", idsArr);
		}
		map.put("isflag", true);
		map.put("ordersql", "customerid,businessdate,id,saleorderid");
		pageMap.setCondition(map);
		Map map2 = receiptHandService.getReceiptListOfReceiptDetail(pageMap);
		List list = (List)map2.get("list");
		BigDecimal totalreceipttaxamount = (BigDecimal)map2.get("totalreceipttaxamount");
		request.setAttribute("list", list);
		String customerids = null != map.get("customerid") ? (String)map.get("customerid") : "";
		//List list2 = new ArrayList();
		if(StringUtils.isNotEmpty(customerids)){
			//list2 = getBaseSalesService().getCustomerListByidArr(customerids);
			request.setAttribute("customernum", customerids.split(",").length);
		}else{
			request.setAttribute("customernum", 0);
		}
		request.setAttribute("totalreceipttaxamount", totalreceipttaxamount);
		return SUCCESS;
	}
}

