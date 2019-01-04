/**
 * @(#)AccountBeginAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月31日 chenwei 创建版本
 */
package com.hd.agent.account.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.account.model.BeginAmount;
import com.hd.agent.account.service.IBeginAmountService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 客户应收款期初action
 * @author chenwei
 */
public class BeginAmountAction extends BaseFilesAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8514876939054115762L;
	
	private BeginAmount beginAmount;	
	/**
	 * 客户应收款期初service
	 */
	private IBeginAmountService beginAmountService;
	
	public BeginAmount getBeginAmount() {
		return beginAmount;
	}

	public void setBeginAmount(BeginAmount beginAmount) {
		this.beginAmount = beginAmount;
	}

	public IBeginAmountService getBeginAmountService() {
		return beginAmountService;
	}

	public void setBeginAmountService(IBeginAmountService beginAmountService) {
		this.beginAmountService = beginAmountService;
	}
	/**
	 * 显示客户应收款期初列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public String showCustomerBeginAmountListPage() throws Exception{
		return "success";
	}
	/**
	 * 显示客户应收款期初数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public String showBeignAmountList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = beginAmountService.showBeignAmountList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示客户应收款期初新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public String beignAmountAddPage() throws Exception{
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		request.setAttribute("nextday", CommonUtils.getNextMonthDay(new Date()));
		return "success";
	}
	/**
	 * 添加新增客户应收款期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	@UserOperateLog(key="CollectionOrder",type=2)
	public String addBeginAmountSave() throws Exception{
		beginAmount.setStatus("2");
		boolean flag = beginAmountService.addBeignAmount(beginAmount);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", beginAmount.getId());
		addJSONObject(map);
		addLog("客户应收款期初新增  编号："+beginAmount.getId(), flag);
		return "success";
	}
	/**
	 * 显示客户应收款期初修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public String beignAmountEditPage() throws Exception{
		String id = request.getParameter("id");
		BeginAmount beginAmount = beginAmountService.getBeginAmountByID(id);
		request.setAttribute("beginAmount", beginAmount);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		return "success";
	}
	/**
	 * 客户应收款期初修改
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String editBeginAmountSave() throws Exception{
		beginAmount.setStatus("2");
		boolean flag = beginAmountService.editBeginAmount(beginAmount);
		Map map = new HashMap();
		map.put("flag", flag);
		addJSONObject(map);
		addLog("客户应收款期初修改  编号："+beginAmount.getId(), flag);
		return "success";
	}
	
	/**
	 * 显示客户应收款期初查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public String beignAmountViewPage() throws Exception{
		String id = request.getParameter("id");
		BeginAmount beginAmount = beginAmountService.getBeginAmountByID(id);
		request.setAttribute("beginAmount", beginAmount);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		return "success";
	}
	/**
	 * 删除客户应收款期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	@UserOperateLog(key="CollectionOrder",type=4)
	public String deleteMutbeignAmount() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = beginAmountService.deleteBeginAmount(id);
				if(flag){
					succssids += id+",";
				}else{
					errorids += id+",";
				}
				
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "删除成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "删除失败编号:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("客户应收款期初批量删除 成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 审核客户应收款期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String auditMutbeignAmount() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				Map map = beginAmountService.auditBeignAmount(id);
				boolean flag = (Boolean) map.get("flag");
				String msg = (String) map.get("msg");
				if(flag){
					succssids += id+",";
				}else{
					errorids += msg+".";
				}
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "审核成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "审核失败:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("客户应收款期初批量审核 成功编号："+succssids+";审核失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 返回客户应收款期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	@UserOperateLog(key="CollectionOrder",type=3)
	public String oppauditMutbeignAmount() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				Map map = beginAmountService.oppauditBeignAmount(id);
				boolean flag = (Boolean) map.get("flag");
				String msg = (String) map.get("msg");
				if(flag){
					succssids += id+",";
				}else{
					errorids += msg+".";
				}
			}
			Map map = new HashMap();
			map.put("flag", true);
			if(!"".equals(succssids)){
				map.put("succssids", "反审成功编号:"+succssids);
			}else{
				map.put("succssids", "");
			}
			if(!"".equals(errorids)){
				map.put("errorids", "反审失败:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("客户应收款期初批量反审 成功编号："+succssids+";审核失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 导出客户应收款期初
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月3日
	 */
	public void exportBeignAmount() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
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
		PageData pageData = beginAmountService.showBeignAmountList(pageMap);
		ExcelUtils.exportExcel(exportBeginAmountListFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 导出excel格式定义
	 * @param list
	 * @param footerList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 28, 2014
	 */
	private List<Map<String, Object>> exportBeginAmountListFilter(List<BeginAmount> list,List<BeginAmount> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "单据号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("amount", "金额");
		firstMap.put("duefromdate", "应收日期");
		firstMap.put("statusname", "状态");
		result.add(firstMap);
		
		if(null!=list && list.size() != 0){
			for(BeginAmount beginAmount : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(beginAmount);
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
		if(null!=footerList && footerList.size() != 0){
			for(BeginAmount beginAmount : footerList){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(beginAmount);
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
	 * 客户应收款期初导入
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月3日
	 */
	@UserOperateLog(key="CollectionOrder",type=2)
	public String importBeignAmount() throws Exception{
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("单据号".equals(str)){
				paramList2.add("id");
			}else if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("客户编码".equals(str)){
				paramList2.add("customerid");
			}else if("客户名称".equals(str)){
				paramList2.add("customername");
			}else if("金额".equals(str)){
				paramList2.add("amount");
			}else if("应收日期".equals(str)){
				paramList2.add("duefromdate");
			}else{
				paramList2.add("null");
			}
		}
		Map map = new HashMap();
		String ids = "";
		List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
		if(list.size() != 0){
			map = beginAmountService.importBeignAmount(list);
			if(map.containsKey("ids")){
				ids = (String) map.get("ids");
			}
		}else{
			map.put("excelempty", true);
		}
		addJSONObject(map);
		addLog("客户应收款期初导入  编号:"+ids, map);
		return "success";
	}
}

