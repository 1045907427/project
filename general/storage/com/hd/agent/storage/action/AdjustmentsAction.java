/**
 * @(#)AdjustmentsAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 24, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.model.Adjustments;
import com.hd.agent.storage.model.AdjustmentsDetail;
import com.hd.agent.storage.service.IAdjustmentsService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 库存调账单action
 * @author chenwei
 */
public class AdjustmentsAction extends BaseFilesAction {
	
	private Adjustments adjustments;
	
	/**
	 * 库存调账单service
	 */
	private IAdjustmentsService adjustmentsService;

	public IAdjustmentsService getAdjustmentsService() {
		return adjustmentsService;
	}

	public void setAdjustmentsService(IAdjustmentsService adjustmentsService) {
		this.adjustmentsService = adjustmentsService;
	}
	
	public Adjustments getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(Adjustments adjustments) {
		this.adjustments = adjustments;
	}

	/**
	 * 显示调账单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public String showAdjustmentsListPage() throws Exception{
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 获取调账单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String showAdjustmentsList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = adjustmentsService.showAdjustmentsList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示调账单页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String showAdjustmentsPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 调账单详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String adjustmentsViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = adjustmentsService.getAdjustmentsInfo(id);
		Adjustments adjustments = (Adjustments) map.get("adjustments");
		List list =  (List) map.get("adjustmentsDetail");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("adjustments", adjustments);
		request.setAttribute("adjustmentsDetailList", jsonStr);
		return "success";
	}
	/**
	 * 显示调账单添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String showAdjustmentsAddPage() throws Exception{
		request.setAttribute("type", "add");
		return "success";
	}
	/**
	 * 调账单添加详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String adjustmentsAddPage() throws Exception{
		//单据类型1报损单2报溢单
		String billtype = request.getParameter("billtype");
		if(StringUtils.isEmpty(billtype)){
			billtype = "1";
		}
		request.setAttribute("billtype", billtype);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_adjustments"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}
	/**
	 * 显示调账单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String showAdjustmentsDetailAddPage() throws Exception{
		String storageid = request.getParameter("storageid");
		request.setAttribute("storageid", storageid);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 计算调账数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String computeAdjustmentsDetailNum() throws Exception{
		String adjustnumStr = request.getParameter("adjustnum");
		String priceStr = request.getParameter("price");
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
		
		//调账数量
		BigDecimal adjustnum = new BigDecimal(adjustnumStr);
		BigDecimal price = new BigDecimal(priceStr);
		//获取金额 辅单位数量 辅单位数量描述
		Map adjustmap = countGoodsInfoNumber(goodsid, auxunitid,price, adjustnum);
		Map map = new HashMap();
		map.put("auxadjustnum", adjustmap.get("auxnum"));
		map.put("auxadjustnumdetail", adjustmap.get("auxnumdetail"));
		map.put("amount", adjustmap.get("unitamount"));
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示调账单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String showAdjustmentsDetailEditPage() throws Exception{
		String storageid = request.getParameter("storageid");
		request.setAttribute("storageid", storageid);
		
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        StorageInfo storageInfo = getStorageInfo(storageid);
		if(null!=goodsInfo && null!=storageInfo){
            String isbatch = "0";
            if("1".equals(goodsInfo.getIsbatch()) && "1".equals(storageInfo.getIsbatch())){
                isbatch = "1";
            }
			request.setAttribute("isbatch", isbatch);
		}else{
			request.setAttribute("isbatch", "0");
		}
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 显示调账单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String showAdjustmentsDetailOtherAddPage() throws Exception{
		String storageid = request.getParameter("storageid");
		request.setAttribute("storageid", storageid);
		return "success";
	}
	/**
	 * 调账单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	@UserOperateLog(key="Adjustments",type=2)
	public String addAdjustmentsHold() throws Exception{
		if (isAutoCreate("t_storage_adjustments")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(adjustments, "t_storage_adjustments");
			adjustments.setId(id);
		}
		adjustments.setStatus("1");
		String adjustmentsDetailJson = request.getParameter("adjustmentsDetailJson");
		List detailList = JSONUtils.jsonStrToList(adjustmentsDetailJson, new AdjustmentsDetail());
		boolean flag = adjustmentsService.addAdjustments(adjustments, detailList);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", adjustments.getId());
		addJSONObject(map);
		addLog("调账单新增暂存 编号："+adjustments.getId(), flag);
		return "success";
	}
	/**
	 * 调账单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	@UserOperateLog(key="Adjustments",type=2)
	public String addAdjustmentsSave() throws Exception{
		if (isAutoCreate("t_storage_adjustments")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(adjustments, "t_storage_adjustments");
			adjustments.setId(id);
		}
		adjustments.setStatus("2");
		String adjustmentsDetailJson = request.getParameter("adjustmentsDetailJson");
		List detailList = JSONUtils.jsonStrToList(adjustmentsDetailJson, new AdjustmentsDetail());
		boolean flag = adjustmentsService.addAdjustments(adjustments, detailList);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		if(flag && "saveaudit".equals(saveaudit)){
			map.put("saveaudit", "saveaudit");
			Map auditMap = adjustmentsService.auditAdjustments(adjustments.getId());
			boolean auditflag = false;
			String msg = "";
			if(auditMap.containsKey("flag")){
				auditflag = (Boolean) auditMap.get("flag");
			}
			if(auditMap.containsKey("msg")){
				msg = (String) auditMap.get("msg");
			}
			map.put("msg", msg);
			map.put("auditflag", auditflag);
			addLog("调账单保存审核 编号："+adjustments.getId(), auditflag);
		}else{
			addLog("调账单新增保存 编号："+adjustments.getId(), flag);
		}
		map.put("flag", flag);
		map.put("id", adjustments.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 删除调账单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	@UserOperateLog(key="Adjustments",type=4)
	public String deleteAdjustments() throws Exception{
		String id = request.getParameter("id");
		boolean flag = adjustmentsService.deleteAdjustments(id);
		addJSONObject("flag", flag);
		addLog("调账单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 显示调账单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public String showAdjustmentsEditPage() throws Exception{
		Boolean flag=true;
		String id = request.getParameter("id");
		String billypte = request.getParameter("billypte");
		if(StringUtils.isEmpty(billypte)){
			billypte = "1";
		}
		request.setAttribute("type", "edit");
		request.setAttribute("id", id);
		Adjustments adjustments=adjustmentsService.showPureAdjustments(id);
		if(null==adjustments){
			flag=false;
		}
		request.setAttribute("flag", flag);
		if("1".equals(billypte)){
			return "success";
		}else if("2".equals(billypte)){
			return "lossSuccess";
		}
		return "success";
	}
	/**
	 * 显示调账单修改详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public String adjustmentsEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = adjustmentsService.getAdjustmentsInfo(id);
		Adjustments adjustments = (Adjustments) map.get("adjustments");
		List list =  (List) map.get("adjustmentsDetail");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("adjustments", adjustments);
		request.setAttribute("adjustmentsDetailList", jsonStr);
		if("1".equals(adjustments.getStatus()) || "2".equals(adjustments.getStatus()) || "6".equals(adjustments.getStatus())){
			return "success";
		}else{
			return "viewSuccess";
		}
		
	}
	/**
	 * 调账单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	@UserOperateLog(key="Adjustments",type=3)
	public String editAdjustmentsHold() throws Exception{
		adjustments.setStatus("1");
		String adjustmentsDetailJson = request.getParameter("adjustmentsDetailJson");
		List detailList = JSONUtils.jsonStrToList(adjustmentsDetailJson, new AdjustmentsDetail());
		boolean flag = adjustmentsService.editAdjustments(adjustments, detailList);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", adjustments.getId());
		addJSONObject(map);
		addLog("调账单修改 编号："+adjustments.getId(), flag);
		return "success";
	}
	/**
	 * 调账单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	@UserOperateLog(key="Adjustments",type=3)
	public String editAdjustmentsSave() throws Exception{
		String adjustmentsDetailJson = request.getParameter("adjustmentsDetailJson");
		List detailList = JSONUtils.jsonStrToList(adjustmentsDetailJson, new AdjustmentsDetail());
		boolean flag = adjustmentsService.editAdjustments(adjustments, detailList);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		String msg = "";
		if(flag && "saveaudit".equals(saveaudit)){
			map.put("saveaudit", "saveaudit");
			Map auditMap = adjustmentsService.auditAdjustments(adjustments.getId());
			boolean auditflag = false;
			if(auditMap.containsKey("flag")){
				auditflag = (Boolean) auditMap.get("flag");
			}
			if(auditMap.containsKey("msg")){
				msg = (String) auditMap.get("msg");
			}
			map.put("auditflag", auditflag);
			
			addLog("调账单保存审核 编号："+adjustments.getId(), auditflag);
		}else{
			addLog("调账单修改 编号："+adjustments.getId(), flag);
		}
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("id", adjustments.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 审核调账单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	@UserOperateLog(key="Adjustments",type=3)
	public String auditAdjustments() throws Exception{
		String id = request.getParameter("id");
		Map auditMap = adjustmentsService.auditAdjustments(id);
		addJSONObject(auditMap);
		addLog("调账单审核 编号："+id, auditMap);
		return "success";
	}

    /**
     * 反审调账单
     * @return
     * @throws Exception
     * @author chenwei
     * @date May 27, 2013
     */
    @UserOperateLog(key="Adjustments",type=3)
    public String oppauditAdjustments() throws Exception{
        String id = request.getParameter("id");
        Map auditMap = adjustmentsService.oppauditAdjustments(id);
        addJSONObject(auditMap);
        addLog("调账单反审 编号："+id, auditMap);
        return "success";
    }
	/**
	 * 显示上游单据查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public String showAdjustmentsRelationUpperPage() throws Exception{
		
		return "success";
	}
	/**
	 * 显示上游单据列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public String showAdjustmentsSourceListPage() throws Exception{
		
		return "success";
	}
	/**
	 * 根据盘点单编号生成调账单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public String addAdjustmentsByRefer() throws Exception{
		String checklistid = request.getParameter("id");
		String billid = adjustmentsService.addAdjustmentsByRefer(checklistid);
		Map map = new HashMap();
		map.put("billid", billid);
		if(null!=billid){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		addJSONObject(map);
		return "success";
	}
	/**
	 * 调账单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public String submitAdjustmentsPageProcess() throws Exception{
		String id = request.getParameter("id");
		boolean flag = adjustmentsService.submitAdjustmentsPageProcess(id);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 导出调账单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 29, 2014
	 */
	public void getAdjustmentListExport()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
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
		firstMap.put("id", "调账单编码");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("adjuststoragename", "调账仓库");
		firstMap.put("printtimes", "打印次数");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("barcode", "条形码");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "单位");
		firstMap.put("adjustnum", "数量");
		firstMap.put("price", "单价");
		firstMap.put("amount", "金额");
//		firstMap.put("notaxamount", "未税金额");
//		firstMap.put("tax", "税额");
		firstMap.put("auxadjustnumdetail", "辅数量");
		firstMap.put("storagelocationname", "所属库位");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		
		List<Map<String, Object>> adjustList = adjustmentsService.getAdjustmentListExport(pageMap);
		for(Map<String, Object> map2 : adjustList){
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
	 * 显示报损调账单页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月8日
	 */
	public String showLossAdjustmentsListPage() throws Exception{
		return "success";
	}
	/**
	 * 获取调账单列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public String showLossAdjustmentsList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = adjustmentsService.showAdjustmentsList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示报损调账单添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月8日
	 */
	public String showLossAdjustmentsAddPage() throws Exception{
		request.setAttribute("type", "add");
		return "success";
	}
	/**
	 * 显示报损单添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月8日
	 */
	public String lossAdjustmentsAddPage() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_storage_adjustments"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}
	/**
	 * 显示报损单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月8日
	 */
	public String showLossAdjustmentsEditPage() throws Exception{
		String id = request.getParameter("id");
		request.setAttribute("type", "edit");
		request.setAttribute("id", id);
		return "success";
	}
}

