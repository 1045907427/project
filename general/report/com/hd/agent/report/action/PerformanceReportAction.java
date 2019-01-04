/**
 * @(#)PerformanceReportAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-8 zhanghonghui 创建版本
 */
package com.hd.agent.report.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.PerformanceKPIScore;
import com.hd.agent.report.model.PerformanceKPISubject;
import com.hd.agent.report.model.PerformanceKPISummary;
import com.hd.agent.report.service.IPerformanceReportService;

/**
 * 
 * 考核报表
 * @author zhanghonghui
 */
public class PerformanceReportAction extends BaseFilesAction {
	private PerformanceKPISummary performanceKPISummary;
	public PerformanceKPISummary getPerformanceKPISummary() {
		return performanceKPISummary;
	}
	public void setPerformanceKPISummary(PerformanceKPISummary performanceKPISummary) {
		this.performanceKPISummary = performanceKPISummary;
	}
	
	private PerformanceKPIScore performanceKPIScore;
	
	
	public PerformanceKPIScore getPerformanceKPIScore() {
		return performanceKPIScore;
	}
	public void setPerformanceKPIScore(PerformanceKPIScore performanceKPIScore) {
		this.performanceKPIScore = performanceKPIScore;
	}

	private IPerformanceReportService performanceReportService;

	public IPerformanceReportService getPerformanceReportService() {
		return performanceReportService;
	}
	public void setPerformanceReportService(
			IPerformanceReportService performanceReportService) {
		this.performanceReportService = performanceReportService;
	}
	
	private PerformanceKPISubject performanceKPISubject;
	
	public PerformanceKPISubject getPerformanceKPISubject() {
		return performanceKPISubject;
	}
	public void setPerformanceKPISubject(PerformanceKPISubject performanceKPISubject) {
		this.performanceKPISubject = performanceKPISubject;
	}
	/**
	 * 业绩考核指标数据汇总分页面
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPISummaryListPage() throws Exception{
		String yearmonth=CommonUtils.getCurrentYearFirstMonStr();
		request.setAttribute("yearfirstmonth", yearmonth);
		
		yearmonth=CommonUtils.getTodayMonStr();
		request.setAttribute("yearcurmonth", yearmonth);
		
		return SUCCESS;
	}
	/**
	 * 业绩考核指标数据汇总分页面数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPISummaryPageData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=performanceReportService.getPerformanceKPISummaryPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 添加业绩考核指标数据汇总
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPISummaryAddPage() throws Exception{
		String yearmonth=CommonUtils.getCurrentYearLastMonStrMonStr();
		request.setAttribute("yearmonth", yearmonth);
		SysUser sysUser=getSysUser();
		DepartMent departMent=getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
		if(null!=departMent){
			request.setAttribute("deptParentId", departMent.getThisid());
		}
		return SUCCESS;
	}
	/**
	 * 添加业绩考核指标数据汇总
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	@UserOperateLog(key="PerformanceKPISummary",type=2,value="")
	public String addPerformanceKPISummary() throws Exception{
		Map requestMap=CommonUtils.changeMap(request.getParameterMap());
		Map resultMap=performanceReportService.addPerformanceKPISummary(requestMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("新增考核指标数据  ",flag);
		}else{
			addLog("新增考核指标数据 失败");
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 添加业绩考核指标数据汇总
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPISummaryEditPage() throws Exception{
		String id=request.getParameter("id");
		PerformanceKPISummary performanceKPISummary=performanceReportService.showPerformanceKPISummary(id);
		request.setAttribute("performanceKPISummary", performanceKPISummary);
		return SUCCESS;
	}
	/**
	 * 更新业绩考核指标数据汇总
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	@UserOperateLog(key="PerformanceKPISummary",type=3,value="")
	public String editPerformanceKPISummary() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=performanceReportService.updatePerformanceKPISummary(id);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("更新考核指标数据 ",flag);
		}else{
			addLog("更新考核指标数据 失败");
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 添加业绩考核指标数据汇总
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPISummaryViewPage() throws Exception{
		String id=request.getParameter("id");
		PerformanceKPISummary performanceKPISummary=performanceReportService.showPerformanceKPISummary(id);
		request.setAttribute("performanceKPISummary", performanceKPISummary);
		return SUCCESS;
	}
	/**
	 * 删除部门考核指标数据汇总
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	@UserOperateLog(key="PerformanceKPISummary",type=4,value="")
	public String deletePerformanceKPISummary() throws Exception{
		String id=request.getParameter("id");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要删除的相关信息");
			addJSONObject(resultMap);
		}
		boolean flag=performanceReportService.deletePerformanceKPISummary(id.trim());
		addLog("删除部门考核指标数据汇总  编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 批量删除部门考核指标数据汇总
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	@UserOperateLog(key="PerformanceKPISummary",type=4,value="")
	public String deletePerformanceKPISummaryMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要删除的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=performanceReportService.deletePerformanceKPISummaryMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除部门考核指标数据汇总  编号:"+idarrs,flag);
		}else{
			addLog("批量删除部门考核指标数据汇总失败  编号:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	
	/**
	 * 批量审核部门费用
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	@UserOperateLog(key="DepartmentCosts",type=0,value="")
	public String auditPerformanceKPISummaryMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要审核的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=performanceReportService.auditPerformanceKPISummaryMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量审核部门考核指标数据汇总成功  编号:"+idarrs,flag);
		}else{
			addLog("批量审核部门考核指标数据汇总失败  编号:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 批量反审核部门费用
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	@UserOperateLog(key="DepartmentCosts",type=0,value="")
	public String oppauditPerformanceKPISummaryMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要审核的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=performanceReportService.oppauditPerformanceKPISummaryMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量反审核部门考核指标数据汇总  编号:"+idarrs,flag);
		}else{
			addLog("批量反审核部门考核指标数据汇总失败  编号:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 导出部门考核指标数据汇总数据
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-4
	 */
	public void exportPerformanceKPISummaryPageData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isExportData", "true");
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
		PageData pageData =performanceReportService.getPerformanceKPISummaryPageData(pageMap);
		ExcelUtils.exportExcel(exportPerformanceKPISummaryPageDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	/**
	 * 数据转换，list专程符合excel导出的数据格式(采购进货退货明细列表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportPerformanceKPISummaryPageDataFilter(List<PerformanceKPISummary> list,List<PerformanceKPISummary> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("deptname", "部门名称");
		firstMap.put("salesamount", "销售额");
		firstMap.put("jcfhamount", "签呈返还");
		firstMap.put("hsmlamount", "含税毛利");
		firstMap.put("xjamount", "小计");
		firstMap.put("xjrate", "小计率%");
		firstMap.put("fyamount", "费用额");
		firstMap.put("fyrate", "费用率%");
		firstMap.put("jlamount", "净利额");
		firstMap.put("jlrate", "净利率%");
		firstMap.put("pjqmkcamount", "平均期末库存额");
		firstMap.put("pjkczzday", "平均库存周转天数");
		firstMap.put("pjzjzyamount", "平均资金占用额");
		firstMap.put("zjlrrate", "资金利润率％");
		firstMap.put("qmddfyyeamount", "期末代垫费用余额");
		firstMap.put("ddfyzyrate", "代垫费占用率％");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(PerformanceKPISummary item : list){
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
		if(null!=footerList && footerList.size() > 0){
			for(PerformanceKPISummary item : footerList){
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
	
	//==============================================================================
	// 报表
	//==============================================================================
	/**
	 * 业绩考核指标数据汇总分页面
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPISummaryReportDataPage() throws Exception{
		String year=CommonUtils.getCurrentYearStr();
		request.setAttribute("currentyear", year);	
		
		return SUCCESS;
	}
	/**
	 * 业绩考核指标数据汇总分页面数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPISummaryReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=performanceReportService.getPerformanceKPISummaryReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 导出部门考核指标数据汇总数据
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-4
	 */
	public void exportPerformanceKPISummaryReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isExportData", "true");
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
		PageData pageData =performanceReportService.getPerformanceKPISummaryReportData(pageMap);
		ExcelUtils.exportExcel(exportPerformanceKPISummaryReportDataFilter(pageData.getList()), title);
	}
	/**
	 * 数据转换，list专程符合excel导出的数据格式(采购进货退货明细列表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportPerformanceKPISummaryReportDataFilter(List<Map> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		
		firstMap.put("subjectname", "月份");
		for(int i=1;i<13;i++){
			firstMap.put("month_"+i, i+"月");
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
		
		return result;
	}
	
	
	
	/**
	 * 考核指标科目
	 */
	/**===================================================================================*/
	public String showPerformanceKPISubjectListPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示部门考核指标科目列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showPerformanceKPISubjectPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=performanceReportService.showPerformanceKPISubjectPageList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 部门考核指标科目添加页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showPerformanceKPISubjectAddPage() throws Exception{

		List kpiScoreIndexSubject = getBaseSysCodeService().showSysCodeListByType("kpiScoreIndexSubject");
		request.setAttribute("kpiScoreIndexSubject", kpiScoreIndexSubject);
		
		return SUCCESS;
	}
	/**
	 * 部门考核指标科目添加方法
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PerformanceKPISubject",type=2)
	public String addPerformanceKPISubject() throws Exception{
		SysUser sysUser=getSysUser();
		performanceKPISubject.setAdduserid(sysUser.getUserid());
		performanceKPISubject.setAddusername(sysUser.getName());
		Map resultMap=performanceReportService.addPerformanceKPISubject(performanceKPISubject);
		addJSONObject(resultMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		//移除缓存的部门考核指标科目数据
		//EhcacheUtils.removeCache("PerformanceKPISubjectCache");
		addLog("添加部门考核指标科目  编号"+performanceKPISubject.getId()+"-代码:"+performanceKPISubject.getCode(), flag);
		return SUCCESS;
	}
	/**
	 * 显示部门考核指标科目修改页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showPerformanceKPISubjectEditPage() throws Exception{

		List kpiScoreIndexSubject = getBaseSysCodeService().showSysCodeListByType("kpiScoreIndexSubject");
		request.setAttribute("kpiScoreIndexSubject", kpiScoreIndexSubject);
		
		String id=request.getParameter("id");
		PerformanceKPISubject performanceKPISubject=performanceReportService.showPerformanceKPISubjectById(id);
		if(null!=performanceKPISubject && StringUtils.isNotEmpty(performanceKPISubject.getCode())){
			boolean canEditCode=canTableDataDelete("t_js_departmentcosts_subject", performanceKPISubject.getCode());
			request.setAttribute("canEditCode", canEditCode);
		}
		request.setAttribute("performanceKPISubject", performanceKPISubject);
		return SUCCESS;
	}
	
	/**
	 * 修改部门考核指标科目方法
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PerformanceKPISubject",type=3)
	public String editPerformanceKPISubject() throws Exception{
		Map resultMap=performanceReportService.editPerformanceKPISubject(performanceKPISubject);
		addJSONObject(resultMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		//移除缓存的部门考核指标科目数据
		//EhcacheUtils.removeCache("PerformanceKPISubjectCache");
		addLog("修改部门考核指标科目  编号"+performanceKPISubject.getId()+"-代码:"+performanceKPISubject.getCode(), flag);
		return SUCCESS;
	}

	/**
	 * 显示部门考核指标科目查看页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showPerformanceKPISubjectViewPage() throws Exception{

		List kpiScoreIndexSubject = getBaseSysCodeService().showSysCodeListByType("kpiScoreIndexSubject");
		request.setAttribute("kpiScoreIndexSubject", kpiScoreIndexSubject);
		
		String code=request.getParameter("id");
		PerformanceKPISubject performanceKPISubject=performanceReportService.showPerformanceKPISubjectById(code);
		request.setAttribute("performanceKPISubject", performanceKPISubject);
		return SUCCESS;
	}
	
	/**
	 * 禁用部门考核指标科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PerformanceKPISubject",type=3)
	public String disablePerformanceKPISubject() throws Exception{
		String id=request.getParameter("id");
		boolean flag=performanceReportService.disablePerformanceKPISubject(id);
		addJSONObject("flag", flag);
		//移除缓存的部门考核指标科目数据
		//EhcacheUtils.removeCache("PerformanceKPISubjectCache");
		addLog("禁用部门考核指标科目 编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 启用部门考核指标科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="PerformanceKPISubject",type=3)
	public String enablePerformanceKPISubject() throws Exception{
		String id=request.getParameter("id");
		boolean flag=performanceReportService.enablePerformanceKPISubject(id);
		addJSONObject("flag", flag);
		//移除缓存的部门考核指标科目数据
		//EhcacheUtils.removeCache("PerformanceKPISubjectCache");
		addLog("启用部门考核指标科目  编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 删除部门考核指标科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="PerformanceKPISubject",type=4,value="")
	public String deletePerformanceKPISubject()throws Exception{
		String code=request.getParameter("id");
		boolean flag = performanceReportService.deletePerformanceKPISubjectById(code);
		addJSONObject("flag", flag);
		//移除缓存的部门考核指标科目数据
		//EhcacheUtils.removeCache("PerformanceKPISubjectCache");
		addLog("删除部门考核指标科目 编号:"+code, flag);
		return SUCCESS;
	}
	/**
	 * 批量删除部门考核指标科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="PerformanceKPISubject",type=4,value="")
	public String deletePerformanceKPISubjectMore()throws Exception{
		String idarrs = request.getParameter("idarrs");
		Map map= performanceReportService.deletePerformanceKPISubjectMore(idarrs);
		Boolean flag=false;
		if(null!=map){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除部门考核指标科目 编号:"+idarrs,flag);
		}else{
			addLog("批量删除部门考核指标科目 编号失败:"+idarrs);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	
	/**===================================================================================*/
	//考核业绩
	/**===================================================================================*/
	/** 
	 * 考核业绩数据录入页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public String showPerformanceKPIScorePage() throws Exception{
		return SUCCESS;
	}
	
	/** 
	 * 考核业绩数据录入新增页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	public String showPerformanceKPIScoreAddPage() throws Exception{
		String yearmonth=CommonUtils.getCurrentYearLastMonStrMonStr();
		request.setAttribute("yearmonth", yearmonth);
		SysUser sysUser=getSysUser();
		DepartMent departMent=getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
		if(null!=departMent){
			request.setAttribute("deptParentId", departMent.getThisid());
		}
		return SUCCESS;
	}
	/**
	 * 添加考核业绩数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	@UserOperateLog(key="PerformanceKPIScore",type=2,value="")
	public String addPerformanceKPIScore() throws Exception{
		Map requestMap=CommonUtils.changeMap(request.getParameterMap());
		Map resultMap=performanceReportService.addPerformanceKPIScore(requestMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("新增考核指标数据  ",flag);
		}else{
			addLog("新增考核指标数据 失败");
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 考核业绩数据编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	public String showPerformanceKPIScoreEditPage() throws Exception{
		String id=request.getParameter("id");
		PerformanceKPIScore performanceKPIScore=performanceReportService.showPerformanceKPIScore(id);
		request.setAttribute("performanceKPIScore", performanceKPIScore);
		return SUCCESS;
	}
	/**
	 * 编辑考核业绩数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	@UserOperateLog(key="PerformanceKPIScore",type=3,value="")
	public String editPerformanceKPIScore() throws Exception{
		Map requestMap=CommonUtils.changeMap(request.getParameterMap());
		Map resultMap=performanceReportService.updatePerformanceKPIScore(performanceKPIScore,requestMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("更新考核指标数据 ",flag);
		}else{
			addLog("更新考核指标数据 失败");
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}

	/**
	 * 考核业绩数据编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	public String showPerformanceKPIScoreViewPage() throws Exception{
		String id=request.getParameter("id");
		PerformanceKPIScore performanceKPIScore=performanceReportService.showPerformanceKPIScore(id);
		request.setAttribute("performanceKPIScore", performanceKPIScore);
		return SUCCESS;
	}
	/**
	 * 审核考核业绩数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	public String auditPerformanceKPIScoreMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要审核的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=performanceReportService.auditPerformanceKPIScoreMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量审核部门业绩考核数据成功  编号:"+idarrs,flag);
		}else{
			addLog("批量审核部门业绩考核数据失败  编号:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 反审核考核业绩数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	public String oppauditPerformanceKPIScoreMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要审核的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=performanceReportService.oppauditPerformanceKPIScoreMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量反审核部门业绩考核数据成功  编号:"+idarrs,flag);
		}else{
			addLog("批量反审核部门业绩考核数据失败  编号:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 删除考核业绩数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	@UserOperateLog(key="PerformanceKPIScore",type=4,value="")
	public String deletePerformanceKPIScoreMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要删除的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=performanceReportService.deletePerformanceKPIScoreMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除部门业绩考核数据  编号:"+idarrs,flag);
		}else{
			addLog("批量删除部门业绩考核数据失败  编号:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 显示部门业绩考核数据列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	public String showPerformanceKPIScoreListPage() throws Exception{
		String yearmonth=CommonUtils.getCurrentYearFirstMonStr();
		request.setAttribute("yearfirstmonth", yearmonth);
		
		yearmonth=CommonUtils.getTodayMonStr();
		request.setAttribute("yearcurmonth", yearmonth);
		
		return SUCCESS;
	}
	/**
	 * 显示部门业绩考核列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-30
	 */
	public String showPerformanceKPIScorePageData() throws Exception{Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=performanceReportService.getPerformanceKPIScorePageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出部门业绩考核指标数据汇总数据
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-4
	 */
	public void exportPerformanceKPIScorePageData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isExportData", "true");
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
		PageData pageData =performanceReportService.getPerformanceKPIScorePageData(pageMap);
		ExcelUtils.exportExcel(exportPerformanceKPIScorePageDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	/**
	 * 数据转换，list专程符合excel导出的数据格式(部门业绩考核列表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportPerformanceKPIScorePageDataFilter(List<PerformanceKPIScore> list,List<PerformanceKPIScore> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("deptname", "所属部门");
		firstMap.put("salesamountindex", "销售额指标");
		firstMap.put("salesamount", "销售额");
		firstMap.put("salesamountindexscore", "销售额总分");
		firstMap.put("salesamountindexvalue", "销售额分值");
		firstMap.put("salesamountscore", "销售额得分");
		firstMap.put("mlamountindex", "毛利额指标");
		firstMap.put("mlamount", "毛利额");
		firstMap.put("mlamountscore", "毛利额得分");
		firstMap.put("mlrateindex", "毛利率指标");
		firstMap.put("mlrate", "毛利率");
		firstMap.put("mlratescore", "毛利率得分");
		firstMap.put("mlindexscore", "毛利指标总分");
		firstMap.put("mlindexvalue", "毛利指标分值");
		firstMap.put("mlscore", "毛利得分");
		firstMap.put("kczlrsindex", "库存周转日数指标");
		firstMap.put("kczlrs", "库存周转指标实绩");
		firstMap.put("kczlindexscore", "库存周转指标总分");
		firstMap.put("kczlindexvalue", "库存周转指标分值");
		firstMap.put("kczlrsscore", "库存周转指标得分");
		firstMap.put("fyrateindex", "费用率指标");
		firstMap.put("fyrate", "费用率指标实绩");
		firstMap.put("fyrateindexscore", "费用率指标总分");
		firstMap.put("fyrateindexvalue", "费用率指标分值");
		firstMap.put("fyratescore", "费用率指标得分");
		firstMap.put("totalscore", "合计得分");
		firstMap.put("bonus", "应得奖金");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(PerformanceKPIScore item : list){
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
		if(null!=footerList && footerList.size() > 0){
			for(PerformanceKPIScore item : footerList){
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
	//================================================================
	//部门业绩考核报表
	//================================================================

	/**
	 * 部门业绩考核数据汇总分页面
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPIScoreReportDataPage() throws Exception{
		String year=CommonUtils.getCurrentYearStr();
		request.setAttribute("currentyear", year);	
		
		return SUCCESS;
	}
	/**
	 * 部门业绩考核数据汇总分页面数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-8
	 */
	public String showPerformanceKPIScoreReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=performanceReportService.getPerformanceKPIScoreReportData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 导出部门业绩考核数据汇总数据
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-8-4
	 */
	public void exportPerformanceKPIScoreReportData()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isExportData", "true");
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
		PageData pageData =performanceReportService.getPerformanceKPIScoreReportData(pageMap);
		ExcelUtils.exportExcel(exportPerformanceKPIScorePageDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	/**
	 * 数据转换，list专程符合excel导出的数据格式(部门业绩考核列表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportPerformanceKPIScoreReportDataFilter(List<PerformanceKPIScore> list,List<PerformanceKPIScore> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("deptname", "所属部门");
		firstMap.put("salesamountindex", "销售额指标");
		firstMap.put("salesamount", "销售额");
		firstMap.put("salesamountindexscore", "销售额总分");
		firstMap.put("salesamountindexvalue", "销售额分值");
		firstMap.put("salesamountscore", "销售额得分");
		firstMap.put("mlamountindex", "毛利额指标");
		firstMap.put("mlamount", "毛利额");
		firstMap.put("mlamountscore", "毛利额得分");
		firstMap.put("mlrateindex", "毛利率指标");
		firstMap.put("mlrate", "毛利率");
		firstMap.put("mlratescore", "毛利率得分");
		firstMap.put("mlindexscore", "毛利指标总分");
		firstMap.put("mlindexvalue", "毛利指标分值");
		firstMap.put("mlscore", "毛利得分");
		firstMap.put("kczlrsindex", "库存周转日数指标");
		firstMap.put("kczlrs", "库存周转指标实绩");
		firstMap.put("kczlindexscore", "库存周转指标总分");
		firstMap.put("kczlindexvalue", "库存周转指标分值");
		firstMap.put("kczlrsscore", "库存周转指标得分");
		firstMap.put("fyrateindex", "费用率指标");
		firstMap.put("fyrate", "费用率指标实绩");
		firstMap.put("fyrateindexscore", "费用率指标总分");
		firstMap.put("fyrateindexvalue", "费用率指标分值");
		firstMap.put("fyratescore", "费用率指标得分");
		firstMap.put("totalscore", "合计得分");
		firstMap.put("bonus", "应得奖金");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(PerformanceKPIScore item : list){
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
		if(null!=footerList && footerList.size() > 0){
			for(PerformanceKPIScore item : footerList){
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
}

