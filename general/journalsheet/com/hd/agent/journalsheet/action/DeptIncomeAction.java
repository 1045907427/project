/**
 * @(#)DeptIncomeAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月21日 chenwei 创建版本
 */
package com.hd.agent.journalsheet.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.journalsheet.model.DeptIncome;
import com.hd.agent.journalsheet.service.IDeptIncomeService;

/**
 * 
 * 部门收入action
 * @author chenwei
 */
public class DeptIncomeAction extends BaseFilesAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9114650638019113373L;

	private DeptIncome deptIncome;
	
	public DeptIncome getDeptIncome() {
		return deptIncome;
	}

	public void setDeptIncome(DeptIncome deptIncome) {
		this.deptIncome = deptIncome;
	}
	
	
	private IDeptIncomeService deptIncomeService;

	public IDeptIncomeService getDeptIncomeService() {
		return deptIncomeService;
	}

	public void setDeptIncomeService(IDeptIncomeService deptIncomeService) {
		this.deptIncomeService = deptIncomeService;
	}
	
	/**
	 * 显示部门收入明细页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String showDeptIncomeDetailListPage() throws Exception{
		request.setAttribute("today1", CommonUtils.getNowMonthDay());
		request.setAttribute("today2", CommonUtils.getTodayDataStr());
		return "success";
	}
	/**
	 * 显示部门收入添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String showDeptIncomeAddPage() throws Exception{
		request.setAttribute("autoCreate", isAutoCreate("t_js_dept_dailycost"));
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 添加部门收入数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	@UserOperateLog(key="DeptIncome",type=2)
	public String addDeptIncome() throws Exception{
		boolean flag = deptIncomeService.addDeptIncome(deptIncome);
		addLog("添加部门收入  编号:"+deptIncome.getId(), flag);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 获取部门收入数据列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String showDeptIncomeList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = deptIncomeService.showDeptIncomeList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示部门收入详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String showDeptIncomeInfoPage() throws Exception{
		String id =  request.getParameter("id");
		DeptIncome deptIncome = deptIncomeService.getDeptIncomeInfo(id);
		request.setAttribute("deptIncome", deptIncome);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		if(null!=deptIncome){
			if("2".equals(deptIncome.getStatus())){
				return "editSuccess";
			}else if("3".equals(deptIncome.getStatus()) || "4".equals(deptIncome.getStatus())){
				return "viewSuccess";
			}else{
				return "viewSuccess";
			}
		}else{
			return "addSuccess";
		}
	}
	/**
	 * 显示部门收入查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String showDeptIncomeViewPage() throws Exception{
		String id =  request.getParameter("id");
		DeptIncome deptIncome = deptIncomeService.getDeptIncomeInfo(id);
		request.setAttribute("deptIncome", deptIncome);
		return "success";
	}
	/**
	 * 修改部门收入
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	@UserOperateLog(key="DeptIncome",type=3)
	public String editDeptIncome() throws Exception{
		boolean flag = deptIncomeService.editDeptIncome(deptIncome);
		addJSONObject("flag", flag);
		addLog("修改部门收入  编号:"+deptIncome.getId(), flag);
		return "success";
	}
	/**
	 * 删除部门收入
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	@UserOperateLog(key="DeptIncome",type=4)
	public String deleteDeptIncome() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = deptIncomeService.deleteDeptIncome(id);
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
			addLog("删除部门收入 成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 审核部门收入
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	@UserOperateLog(key="DeptIncome",type=3)
	public String auditDeptIncome() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = deptIncomeService.auditDeptIncome(id);
				if(flag){
					succssids += id+",";
				}else{
					errorids += id+",";
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
				map.put("errorids", "审核失败编号:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("审核部门收入 成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	
	/**
	 * 反审部门收入
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	@UserOperateLog(key="DeptIncome",type=3)
	public String oppauditDeptIncome() throws Exception{
		String ids = request.getParameter("ids");
		if(null!=ids){
			String[] idArr = ids.split(",");
			String succssids = "";
			String errorids = "";
			for(String id : idArr){
				boolean flag = deptIncomeService.oppauditDeptIncome(id);
				if(flag){
					succssids += id+",";
				}else{
					errorids += id+",";
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
				map.put("errorids", "反审失败编号:"+errorids);
			}else{
				map.put("errorids", "");
			}
			addJSONObject(map);
			addLog("反审部门收入 成功编号："+succssids+";失败编号："+errorids, true);
		}else{
			addJSONObject("flag", false);
		}
		return "success";
	}
	/**
	 * 显示部门收入结算页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public String showDeptIncomeSettlePage() throws Exception{
		request.setAttribute("year", CommonUtils.getPreMonthYear());
		request.setAttribute("month", CommonUtils.getPreMonth());
		return "success";
	}
	/**
	 * 按年度月份计算该月份的收入，分摊到个供应商中去
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	@UserOperateLog(key="DeptIncome",type=3)
	public String updateDeptIncomeSettle() throws Exception{
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		boolean flag = deptIncomeService.updateDeptIncomeSettle(year, month);
		addJSONObject("flag", flag);
		addLog("部门收入结算到供应商中去  时间："+year+"年"+month+"月",flag);
		return "success";
	}
	/**
	 * 显示部门收入统计报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String deptIncomeReportPage() throws Exception{
		request.setAttribute("firstday", CommonUtils.getMonthDateStr());
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return SUCCESS;
	}
	/**
	 * 部门收入统计报表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String showDeptIncomeReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = deptIncomeService.getDeptIncomeReportPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 导出-部门收入明细报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportDeptIncomeReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
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
		List list = deptIncomeService.getDeptIncomeReportExportData(map);
		List footerList  = deptIncomeService.getDeptIncomeReportExportSumData(map);
		ExcelUtils.exportExcel(exportDeptIncomeReportDataFilter(list,footerList), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(费用明细报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportDeptIncomeReportDataFilter(List<Map> list,List<Map> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		
		firstMap.put("name", "部门名称");
		firstMap.put("sid", "科目编号");	
		firstMap.put("subjectname", "科目名称");	
		firstMap.put("amount", "科目金额");
		firstMap.put("totalamount", "金额");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Map<String,Object> map : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
				if(map.containsKey("children")){
					List<Map> childList = (List) map.get("children");
					for(Map<String,Object> cmap : childList){
						Map<String, Object> retCMap = new LinkedHashMap<String, Object>();
						for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
							if(cmap.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
								for(Map.Entry<String, Object> entry : cmap.entrySet()){
									if(fentry.getKey().equals(entry.getKey())){
										objectCastToRetMap(retCMap,entry);
									}
								}
							}
							else{
								retCMap.put(fentry.getKey(), "");
							}
						}
						result.add(retCMap);
					}
				}
				
			}
		}
		if(footerList.size() != 0){
			for(Map<String,Object> map : footerList){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
	 * 显示部门收入统计报表详细列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年10月8日
	 */
	public String deptIncomeReportDetailListPage() throws Exception{
		return SUCCESS;
	}
	
	
	/**
	 * 显示部门收入按年统计报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String deptIncomeYearReportPage() throws Exception{
		request.setAttribute("firstday", CommonUtils.getMonthDateStr());
		request.setAttribute("today", CommonUtils.getTodayDataStr());
		return SUCCESS;
	}
	/**
	 * 部门收入按年统计报表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月21日
	 */
	public String showDeptIncomeYearReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=deptIncomeService.getDeptIncomeYearReportPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出-部门收入按年明细报表
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年10月9日
	 */
	public void exportDeptIncomeYearReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isPageflag", "true");
		map.put("isExportData", "true");	//是否导出数据
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
		PageData pageData=deptIncomeService.getDeptIncomeYearReportPageData(pageMap);
		ExcelUtils.exportExcel(exportDeptIncomeYearReportDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(费用明细报表)
	 * @param list
	 * @param footerList
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年10月9日
	 */
	private List<Map<String, Object>> exportDeptIncomeYearReportDataFilter(List<Map> list,List<Map> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		String groupcols=request.getParameter("groupcols");
		if(null==groupcols || "".equals(groupcols)){
			groupcols="";
		}
		groupcols=groupcols.toLowerCase();
		boolean isNotGroup=true;
		
		if(groupcols.indexOf("deptid")>=0){
			firstMap.put("deptname", "部门名称");
			isNotGroup=false;
		}
		if(groupcols.indexOf("year")>=0){
			firstMap.put("year", "年份");
			isNotGroup=false;
		}
		if(groupcols.indexOf("subjectid")>=0){
			firstMap.put("subjectname", "科目名称");
			isNotGroup=false;
		}
		if(isNotGroup){
			firstMap.put("deptname", "部门名称");
			firstMap.put("year", "年份");	
			firstMap.put("subjectname", "科目名称");		
		}

		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+month;
			}
			firstMap.put("month_"+month, i+"月");
		}
		firstMap.put("totalamount", "合计");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Map<String,Object> map : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
		if(footerList.size() != 0){
			for(Map<String,Object> map : footerList){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
	 * 显示分供应商收入分摊报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public String deptSupplierIncomeReportPage() throws Exception{
		String year=CommonUtils.getCurrentYearStr();
		request.setAttribute("currentyear", year);	
		return SUCCESS;
	}
	/**
	 * 分供应商收入分摊报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public String showDeptSupplierIncomeReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=deptIncomeService.getDeptSupplierIncomeReportPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 分供应商收入分摊按年报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public String showDeptSupplierIncomeYearReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=deptIncomeService.getDeptSupplierIncomeReportPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 获取指定供应商下的收入科目金额报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月14日
	 */
	public String showSupplierSubjectReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List list = deptIncomeService.showSupplierSubjectReportData(pageMap);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 导出-分供应商收入分摊细报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportDeptSupplierIncomeReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isPageflag", "true");
		map.put("isExportData", "true");	//是否导出数据
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
		PageData pageData=deptIncomeService.getDeptSupplierIncomeReportPageData(pageMap);
		ExcelUtils.exportExcel(exportDeptSupplierIncomeReportDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	/**
	 * 导出供应商收入科目金额数据
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月14日
	 */
	public void exportSupplierSubjectDetailData() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isPageflag", "true");
		map.put("isExportData", "true");	//是否导出数据
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
		PageData pageData=deptIncomeService.getSupplierSubjectDetailData(pageMap);
		ExcelUtils.exportExcel(exportSupplierSubjectReportDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	/**
	 * 数据转换，list专程符合excel导出的数据格式(费用分供应商收入分摊报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportDeptSupplierIncomeReportDataFilter(List<Map> list,List<Map> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		String groupcols=request.getParameter("groupcols");
		if(null==groupcols || "".equals(groupcols)){
			groupcols="";
		}
		groupcols=groupcols.toLowerCase();
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("year", "年份");	
//		firstMap.put("subjectname", "科目名称");		

		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+month;
			}
			firstMap.put("month_"+month, i+"月");
		}
		firstMap.put("totalamount", "合计");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Map<String,Object> map : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
		if(footerList.size() != 0){
			for(Map<String,Object> map : footerList){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
	 * 数据转换，list专程符合excel导出的数据格式(费用分供应商收入分摊报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSupplierSubjectReportDataFilter(List<Map> list,List<Map> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		String groupcols=request.getParameter("groupcols");
		if(null==groupcols || "".equals(groupcols)){
			groupcols="";
		}
		groupcols=groupcols.toLowerCase();
		firstMap.put("year", "年份");	
		firstMap.put("id", "科目编号");
		firstMap.put("subjectname", "科目名称");
		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+month;
			}
			firstMap.put("month_"+month, i+"月");
		}
		firstMap.put("totalamount", "合计");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Map<String,Object> map : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
		if(footerList.size() != 0){
			for(Map<String,Object> map : footerList){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
	 * 导出-分供应商收入分摊细报表
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年10月9日
	 */
	public void exportDeptSupplierIncomeYearReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isPageflag", "true");
		map.put("isExportData", "true");	//是否导出数据
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
		PageData pageData=deptIncomeService.getDeptSupplierIncomeYearReportPageData(pageMap);
		ExcelUtils.exportExcel(exportDeptSupplierIncomeYearReportDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 *  数据转换，list专程符合excel导出的数据格式(费用分供应商收入分摊报表)
	 * @param list
	 * @param footerList
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年10月9日
	 */
	private List<Map<String, Object>> exportDeptSupplierIncomeYearReportDataFilter(List<Map> list,List<Map> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		String groupcols=request.getParameter("groupcols");
		if(null==groupcols || "".equals(groupcols)){
			groupcols="";
		}
		groupcols=groupcols.toLowerCase();
		boolean isNotGroup=true;
		/*
		if(groupcols.indexOf("deptid")>=0){
			firstMap.put("deptname", "部门名称");
			isNotGroup=false;
		}
		*/
		if(groupcols.indexOf("supplierid")>=0){
			firstMap.put("suppliername", "供应商名称");
			isNotGroup=false;
		}
		if(groupcols.indexOf("year")>=0){
			firstMap.put("year", "年份");
			isNotGroup=false;
		}
		if(groupcols.indexOf("subjectid")>=0){
			firstMap.put("subjectname", "科目名称");
			isNotGroup=false;
		}
		if(isNotGroup){
			firstMap.put("deptname", "部门名称");
			firstMap.put("suppliername", "供应商名称");
			firstMap.put("year", "年份");	
			firstMap.put("subjectname", "科目名称");		
		}

		for(int i=1;i<13;i++){
			String month=i+"";
			if(i<10){
				month="0"+month;
			}
			firstMap.put("month_"+month, i+"月");
		}
		firstMap.put("totalamount", "合计");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Map<String,Object> map : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
		if(footerList.size() != 0){
			for(Map<String,Object> map : footerList){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
	 * 金额、单价、数量变动后，相互计算
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年1月21日
	 */
	public String deptIncomeAmountNumChange() throws Exception{
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		String type=request.getParameter("type");
		String taxpriceStr = request.getParameter("taxprice");
		String unitnumStr = request.getParameter("unitnum");
		String amountStr = request.getParameter("amount");
		
		if(null==taxpriceStr || "".equals(taxpriceStr.trim())){
			taxpriceStr="0";
		}
		if(null==unitnumStr || "".equals(unitnumStr.trim())){
			unitnumStr="0";
		}
		if(null==amountStr || "".equals(amountStr.trim())){
			amountStr="0";
		}
		
		BigDecimal taxprice=new BigDecimal(taxpriceStr.trim());
		BigDecimal unitnum=new BigDecimal(unitnumStr.trim());
		BigDecimal amount=new BigDecimal(amountStr.trim());
		//金额变动
		if("1".equals(type)){
			if(amount.compareTo(BigDecimal.ZERO)==0){
				taxprice=BigDecimal.ZERO;
			}else{
				amount=amount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				//如果数量为零
				if(unitnum.compareTo(BigDecimal.ZERO)==0){
					//单价也为零
					taxprice=BigDecimal.ZERO;					
				}else{
					unitnum=unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
					
					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}
				
			}
			
		}else if("2".equals(type)){
			//数量变动
			if(unitnum.compareTo(BigDecimal.ZERO)==0){
				taxprice=BigDecimal.ZERO;
			}else{
				unitnum=unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
				
				if(amount.compareTo(BigDecimal.ZERO)==0){
					if(taxprice.compareTo(BigDecimal.ZERO)>0){
						amount=unitnum.multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
					}
				}else{
					amount=amount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
					
					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}
				
			}
		}else if("3".equals(type)){
			//单价变动 
			if(taxprice.compareTo(BigDecimal.ZERO)==0){				
				if(amount.compareTo(BigDecimal.ZERO)>0 && unitnum.compareTo(BigDecimal.ZERO)>0){
					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}else{
					taxprice=BigDecimal.ZERO;
				}
			}else{
				taxprice=taxprice.setScale(6,BigDecimal.ROUND_HALF_UP);
				
				if(unitnum.compareTo(BigDecimal.ZERO)==0){
					amount=BigDecimal.ONE;
				}else{
					unitnum=unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
					
					amount=unitnum.multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				}
				
			}
		}
		Map resultMap=new HashMap();
		resultMap.put("taxprice", taxprice);
		resultMap.put("unitnum", unitnum);
		resultMap.put("amount", amount);
		addJSONObject(resultMap);
		return SUCCESS;
	}
}

