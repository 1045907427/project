/**
 * @(#)CostsFeeAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-19 zhanghonghui 创建版本
 */
package com.hd.agent.journalsheet.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.JSONUtils;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.FilesLevelAction;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.FilesLevel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.journalsheet.model.DepartmentCosts;
import com.hd.agent.journalsheet.model.DeptCostsSubject;
import com.hd.agent.journalsheet.service.ICostsFeeService;

/**
 * 
 * 费用Action
 * @author zhanghonghui
 */
public class CostsFeeAction extends FilesLevelAction {
	ICostsFeeService costsFeeService;

	public ICostsFeeService getCostsFeeService() {
		return costsFeeService;
	}

	public void setCostsFeeService(ICostsFeeService costsFeeService) {
		this.costsFeeService = costsFeeService;
	}	

	private DeptCostsSubject deptCostsSubject;
	public DeptCostsSubject getDeptCostsSubject() {
		return deptCostsSubject;
	}
	public void setDeptCostsSubject(DeptCostsSubject deptCostsSubject) {
		this.deptCostsSubject = deptCostsSubject;
	}
	
	/**
	 * 部门费用科目
	 */
	/**===================================================================================*/
	public String showDeptCostsSubjectListPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示部门费用科目列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showDeptCostsSubjectPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=costsFeeService.showDeptCostsSubjectPageList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 部门费用科目
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月23日
	 */
	public String showDeptCostsSubjectPage() throws Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_js_departmentcosts_subject");
		int len = 0;
		if(null != list && list.size() != 0){
			len = list.size();
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<list.size();i++){
				FilesLevel level = (FilesLevel)list.get(i);
				sb.append(level.getLen() + ",");
			}
			if(sb.length() > 0){
				request.setAttribute("lenStr", sb.deleteCharAt(sb.length()-1).toString());
			}
		}
		request.setAttribute("len", len);
		return SUCCESS;
	}
	
	
	
	/**
	 * 部门费用科目添加页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showDeptCostsSubjectAddPage() throws Exception{
		String id = request.getParameter("id");//当为空时添加的费用分类为一级目录，否则该编号为添加费用分类的父级
		DeptCostsSubject deptCostsSubject=costsFeeService.showDeptCostsSubjectById(id);
		int len=0;
		if(null!=deptCostsSubject && StringUtils.isNotEmpty(deptCostsSubject.getId())){
			len=deptCostsSubject.getId().length();
		}

		int nextLen = getBaseTreeFilesNext("t_js_departmentcosts_subject", len);
		request.setAttribute("deptCostsSubject", deptCostsSubject);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	/**
	 * 部门费用科目添加方法
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="DeptCostsSubject",type=2)
	public String addDeptCostsSubject() throws Exception{
		String type = request.getParameter("type");
		if("hold".equals(type)){
			deptCostsSubject.setState("3");
		}
		else{
			deptCostsSubject.setState("2");
		}
		SysUser sysUser=getSysUser();
		deptCostsSubject.setAdduserid(sysUser.getUserid());
		deptCostsSubject.setAddusername(sysUser.getName());
		Map resultMap=costsFeeService.addDeptCostsSubject(deptCostsSubject);
		addJSONObject(resultMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		//移除缓存的部门费用科目数据
		//EhcacheUtils.removeCache("DeptCostsSubjectCache");
		addLog("添加部门费用科目  编号"+deptCostsSubject.getId()+"-代码:"+deptCostsSubject.getId(), flag);
		
		Tree node = new Tree();
		node.setId(deptCostsSubject.getId());
		node.setParentid(deptCostsSubject.getPid());
		node.setState(deptCostsSubject.getState());
		node.setText(deptCostsSubject.getThisname());
		resultMap.put("node", node);
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 显示部门费用科目修改页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showDeptCostsSubjectEditPage() throws Exception{
		String id=request.getParameter("id");
		int len = 0;
		DeptCostsSubject deptCostsSubject=costsFeeService.showDeptCostsSubjectById(id);
		if(null!=deptCostsSubject && StringUtils.isNotEmpty(deptCostsSubject.getPid())){
			len=deptCostsSubject.getPid().length();
			DeptCostsSubject pDeptCostsSubject=costsFeeService.showDeptCostsSubjectById(deptCostsSubject.getPid());
			if(null!=pDeptCostsSubject){
				request.setAttribute("parentName", pDeptCostsSubject.getName());
			}
		}
		int nextLen = getBaseTreeFilesNext("t_js_departmentcosts_subject", len);
		boolean editFlag=canTableDataDelete("t_js_departmentcosts_subject", deptCostsSubject.getId());
		request.setAttribute("editFlag", editFlag);
		request.setAttribute("len", nextLen);
		request.setAttribute("deptCostsSubject", deptCostsSubject);
		return SUCCESS;
	}
	
	/**
	 * 修改部门费用科目方法
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="DeptCostsSubject",type=3)
	public String editDeptCostsSubject() throws Exception{
		String type = request.getParameter("type");
		if(!"1".equals(deptCostsSubject.getState()) && !"0".equals(deptCostsSubject.getState())){
			if("save".equals(type)){
				deptCostsSubject.setState("2");
			}
		}
		Map resultMap=costsFeeService.editDeptCostsSubject(deptCostsSubject);
		addJSONObject(resultMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		//移除缓存的部门费用科目数据
		//EhcacheUtils.removeCache("DeptCostsSubjectCache");
		addLog("修改部门费用科目  编号"+deptCostsSubject.getId()+"-代码:"+deptCostsSubject.getId(), flag);
		return SUCCESS;
	}
	/**
	 * 显示部门费用科目复制页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月24日
	 */
	public String showDeptCostsSubjectCopyPage() throws Exception{
		String id = request.getParameter("id");
		DeptCostsSubject deptCostsSubject = costsFeeService.showDeptCostsSubjectById(id);
		if(deptCostsSubject != null && StringUtils.isNotEmpty(deptCostsSubject.getPid())){
			DeptCostsSubject pDeptCostsSubject = costsFeeService.showDeptCostsSubjectById(deptCostsSubject.getPid());
			request.setAttribute("parentName", pDeptCostsSubject.getName());
		}
		int len = 0;
		String name="",pname="";
		if(deptCostsSubject != null && StringUtils.isNotEmpty(deptCostsSubject.getPid())){
			len = deptCostsSubject.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_finance_expenses_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("deptCostsSubject", deptCostsSubject);
		return SUCCESS;
	}

	/**
	 * 显示部门费用科目查看页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showDeptCostsSubjectViewPage() throws Exception{
		String code=request.getParameter("id");
		DeptCostsSubject deptCostsSubject=costsFeeService.showDeptCostsSubjectById(code);
		request.setAttribute("deptCostsSubject", deptCostsSubject);
		return SUCCESS;
	}
	/**
	 * 获取部门费用科目树形数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月24日
	 */
	public String getDeptCostsSubjectTree()throws Exception{
		List result = new ArrayList();
		Map paramMap=new HashMap();
		List<DeptCostsSubject> list = costsFeeService.showDeptCostsSubjectListByMap(paramMap);
		Map<String,String> first = new HashMap<String,String>();
		Tree pTree = new Tree();
		pTree.setId("");
		pTree.setText("费用科目");
		pTree.setOpen("true");
		result.add(pTree);
		if(list.size() != 0){
			for(DeptCostsSubject deptCostsSubject:list){
				Tree cTree = new Tree();
				cTree.setId(deptCostsSubject.getId());
				cTree.setParentid(deptCostsSubject.getPid());
				cTree.setText(deptCostsSubject.getThisname());
				cTree.setState(deptCostsSubject.getState());
				result.add(cTree);
			}
		}
		addJSONArray(result);
		return SUCCESS;
	}
	/**
	 * 禁用部门费用科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="DeptCostsSubject",type=3)
	public String disableDeptCostsSubject() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=costsFeeService.disableDeptCostsSubject(id);
		
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", false);
			}
		}else{
			resultMap=new HashMap();
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		//移除缓存的部门费用科目数据
		//EhcacheUtils.removeCache("DeptCostsSubjectCache");
		addLog("禁用部门费用科目 编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 启用部门费用科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="DeptCostsSubject",type=3)
	public String enableDeptCostsSubject() throws Exception{
		String id=request.getParameter("id");
		boolean flag=costsFeeService.enableDeptCostsSubject(id);
		addJSONObject("flag", flag);
		//移除缓存的部门费用科目数据
		//EhcacheUtils.removeCache("DeptCostsSubjectCache");
		addLog("启用部门费用科目  编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 删除部门费用科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="DeptCostsSubject",type=4,value="")
	public String deleteDeptCostsSubject()throws Exception{
		String id=request.getParameter("id");
		Map resultMap = costsFeeService.deleteDeptCostsSubjectById(id);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", flag);
			}
		}
		addJSONObject(resultMap);
		//移除缓存的部门费用科目数据
		//EhcacheUtils.removeCache("DeptCostsSubjectCache");
		addLog("删除部门费用科目 编号:"+id, flag);
		return SUCCESS;
	}
	/**
	 * 批量删除部门费用科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="DeptCostsSubject",type=4,value="")
	public String deleteDeptCostsSubjectMore()throws Exception{
		String idarrs = request.getParameter("idarrs");
		Map map= costsFeeService.deleteDeptCostsSubjectMore(idarrs);
		Boolean flag=false;
		if(null!=map){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除部门费用科目 编号:"+idarrs,flag);
		}else{
			addLog("批量删除部门费用科目 编号失败:"+idarrs);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 验证费用分类编码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String isUsedDeptCostsSubjectID()throws Exception{
		String id = request.getParameter("id");
		DeptCostsSubject deptCostsSubject = costsFeeService.getDeptCostsSubjectDetail(id);
		boolean flag = false;
		if(null!=deptCostsSubject){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 验证费用分类名称是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String isUsedDeptCostsSubjectName()throws Exception{
		String name = request.getParameter("name");
		boolean flag=false;
		if(null==name || "".equals(name.trim())){
			flag=false;
		}else{
			flag= costsFeeService.isUsedDeptCostsSubjectName(name);
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	//================================================================================//
	//部门费用列表
	//================================================================================//
	
	/**
	 * 部门费用列表
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public String showDepartmentCostsListPage() throws Exception{
		
		String yearmonth=CommonUtils.getCurrentYearFirstMonStr();
		request.setAttribute("yearfirstmonth", yearmonth);
		
		yearmonth=CommonUtils.getTodayMonStr();
		request.setAttribute("yearcurmonth", yearmonth);

		
		List list = costsFeeService.showDeptCostsSubjectEnableList();
		request.setAttribute("deptCostsSubjectList", list);
		
		
		return SUCCESS;
	}
	/**
	 * 部门费用列表数据
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public String getDepartmentCostsPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=costsFeeService.getDepartmentCostsPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		
		return SUCCESS;
	}
	/**
	 * 部门费用添加页面
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public String showDepartmentCostsAddPage() throws Exception{
		String yearmonth=CommonUtils.getCurrentYearLastMonStrMonStr();
		request.setAttribute("yearmonth", yearmonth);
		SysUser sysUser=getSysUser();
		DepartMent departMent=getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
		if(null!=departMent){
			request.setAttribute("deptParentId", departMent.getThisid());
		}
		List list = costsFeeService.showDeptCostsSubjectEnableList();
		request.setAttribute("deptCostsSubjectList", list);
		return SUCCESS;
	}
	/**
	 * 部门费用添加
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	@UserOperateLog(key="DepartmentCosts",type=2,value="")
	public String addDepartmentCosts() throws Exception{
		Map requestMap=CommonUtils.changeMap(request.getParameterMap());
		Map resultMap=costsFeeService.addDepartmentCosts(requestMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("新增并初始化部门费用  ",flag);
		}else{
			addLog("新增并初始化部门费用 失败");
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 部门费用编辑
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public String showDepartmentCostsEditPage() throws Exception{
		List subjectList = costsFeeService.showDeptCostsSubjectEnableList();
		String id=request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			request.setAttribute("deparmentCosts", new DepartmentCosts());
			return "viewSuccess";
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		DepartmentCosts departmentCosts=costsFeeService.showDepartmentCostsByMap(map);
		if(null==departmentCosts){
			request.setAttribute("deparmentCosts", new DepartmentCosts());
			return "viewSuccess";			
		}
		request.setAttribute("deparmentCosts", departmentCosts);
		if("3".equals(departmentCosts.getStatus())){
			return "viewSuccess";
		}
		costsFeeService.setDeptCostsSubjectListWithAmount(subjectList, departmentCosts.getDeptCostsDetailList());
		request.setAttribute("deptCostsSubjectList", subjectList);
		return SUCCESS;
	}
	/**
	 * 部门费用编辑
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	@UserOperateLog(key="DepartmentCosts",type=3,value="")
	public String editDepartmentCosts() throws Exception{
		Map requestMap=CommonUtils.changeMap(request.getParameterMap());
		Map resultMap=costsFeeService.editDepartmentCosts(requestMap);
		Boolean flag=false;
		String id=(String)requestMap.get("id");
		if(null==id){
			id="";
		}
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("编辑部门费用  编号:"+id,flag);
		}else{
			addLog("编辑部门费用失败  编号:"+id);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 部门费用查看
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public String showDepartmentCostsViewPage() throws Exception{
		List subjectList = costsFeeService.showDeptCostsSubjectEnableList();
		String id=request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			request.setAttribute("deparmentCosts", new DepartmentCosts());
			return SUCCESS;
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		DepartmentCosts departmentCosts=costsFeeService.showDepartmentCostsByMap(map);
		if(null==departmentCosts){
			request.setAttribute("deparmentCosts", new DepartmentCosts());
			return SUCCESS;			
		}
		request.setAttribute("deparmentCosts", departmentCosts);
		costsFeeService.setDeptCostsSubjectListWithAmount(subjectList, departmentCosts.getDeptCostsDetailList());
		request.setAttribute("deptCostsSubjectList", subjectList);
		return SUCCESS;
	}
	/**
	 * 删除部门费用
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	@UserOperateLog(key="DepartmentCosts",type=4,value="")
	public String deleteDepartmentCosts() throws Exception{
		String id=request.getParameter("id");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要删除的相关信息");
			addJSONObject(resultMap);
		}
		boolean flag=costsFeeService.deleteDepartmentCosts(id.trim());
		addLog("删除部门费用  编号:"+id,flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 批量删除部门费用
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	@UserOperateLog(key="DepartmentCosts",type=4,value="")
	public String deleteDepartmentCostsMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要删除的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=costsFeeService.deleteDepartmentCostsMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除部门费用  编号:"+idarrs,flag);
		}else{
			addLog("批量删除部门费用失败  编号:"+idarrs);
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
	public String auditDepartmentCostsMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要审核的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=costsFeeService.auditDepartmentCostsMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量审核部门费用  编号:"+idarrs,flag);
		}else{
			addLog("批量审核部门费用失败  编号:"+idarrs);
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
	public String oppauditDepartmentCostsMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if(null==idarrs || "".equals(idarrs.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要审核的相关信息");
			addJSONObject(resultMap);
		}
		resultMap=costsFeeService.oppauditDepartmentCostsMore(idarrs.trim());
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量反审核部门费用  编号:"+idarrs,flag);
		}else{
			addLog("批量反审核部门费用失败  编号:"+idarrs);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 导出-部门费用报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportDepartmentCostsData()throws Exception{
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
		PageData pageData =costsFeeService.getDepartmentCostsPageData(pageMap);
		ExcelUtils.exportExcel(exportDepartmentCostsFilter(pageData.getList(),pageData.getFooter()), title);
	}
	private List<Map<String, Object>> exportDepartmentCostsFilter(List<Map> list,List<Map> footerList) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("deptid", "部门编码");
		firstMap.put("deptname", "部门名称");
		firstMap.put("statusname", "状态");
		
		List<DeptCostsSubject> subjectList=costsFeeService.showDeptCostsSubjectEnableList();
		if(null!=subjectList && subjectList.size()>0){
			for(DeptCostsSubject item : subjectList){
				if(null!=item && StringUtils.isNotEmpty(item.getId()) && StringUtils.isNotEmpty(item.getName())){
					firstMap.put("deptCostsSubject_"+item.getId(), item.getName());
				}
			}
		}
		firstMap.put("remark", "备注");
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
	
	//===========================================================================//
	//供应商费用分摊
	//===========================================================================//
	
	/**
	 * 供应商费用分摊列表
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public String showDeptSupplierCostsListPage() throws Exception{
		
		String yearmonth=CommonUtils.getCurrentYearFirstMonStr();
		request.setAttribute("yearfirstmonth", yearmonth);
		
		yearmonth=CommonUtils.getTodayMonStr();
		request.setAttribute("yearcurmonth", yearmonth);

		
		List list = costsFeeService.showDeptCostsSubjectEnableList();
		request.setAttribute("deptCostsSubjectList", list);
		
		
		return SUCCESS;
	}
	/**
	 * 分供应商费用分摊
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-24
	 */
	public String showDeptSupplierCostsPopViewPage() throws Exception{		
		String deptcostsid=request.getParameter("deptcostsid");
		if(null==deptcostsid || "".equals(deptcostsid.trim())){
			deptcostsid="justNoDataSelected";
		}
		request.setAttribute("deptcostsid", deptcostsid);	
		
		List list = costsFeeService.showDeptCostsSubjectEnableList();
		request.setAttribute("deptCostsSubjectList", list);
		
		return SUCCESS;
	}
	
	/**
	 * 供应商费用分摊列表数据
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-6-20
	 */
	public String getDeptSupplierCostsPageData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=costsFeeService.getDeptSupplierCostsPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		
		return SUCCESS;
	}
	
	
	
	
	
	
	//===========================================================================//
	//报表
	//===========================================================================//
	/**
	 * 显示费用明细报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public String departmentCostsMonthReportListPage() throws Exception{
		String year=CommonUtils.getCurrentYearStr();
		request.setAttribute("currentyear", year);	
		return SUCCESS;
	}
	/**
	 * 费用明细报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public String showDepartmentCostsMonthReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=costsFeeService.getDepartmentCostsMonthPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出-费用明细报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportDepartmentCostsMonthReportData()throws Exception{
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
		PageData pageData=costsFeeService.getDepartmentCostsMonthPageData(pageMap);
		ExcelUtils.exportExcel(exportDepartmentCostsMonthReportDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(费用明细报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportDepartmentCostsMonthReportDataFilter(List<Map> list,List<Map> footerList) throws Exception{
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
	 * 显示分供应商费用明细报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public String deptSupplierCostsMonthReportListPage() throws Exception{
		String year=CommonUtils.getCurrentYearStr();
		request.setAttribute("currentyear", year);	
		return SUCCESS;
	}
	/**
	 * 分供应商费用明细报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-4
	 */
	public String showDeptSupplierCostsMonthReportData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("isExportData")){
			map.remove("isExportData");
		}
		pageMap.setCondition(map);
		PageData pageData=costsFeeService.getDeptSupplierCostsMonthPageData(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	/**
	 * 导出-分供应商费用明细报表
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2013
	 */
	public void exportDeptSupplierCostsMonthReportData()throws Exception{
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
		PageData pageData=costsFeeService.getDeptSupplierCostsMonthPageData(pageMap);
		ExcelUtils.exportExcel(exportDeptSupplierCostsMonthReportDataFilter(pageData.getList(),pageData.getFooter()), title);
	}
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(费用分供应商明细报表)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportDeptSupplierCostsMonthReportDataFilter(List<Map> list,List<Map> footerList) throws Exception{
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

    /**===================================费用投入产出比报表=============================================**/

    public String showCustomerInputOutputSheetPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }
    /**
     *获取分客户投入产出比报表 数据
     * @ahthor lin_xx
     * @return
     * @throws Exception
     * @date 2016-9-12
     */
    public String getCustomerInputOutputData() throws Exception {
        Map map=CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData=costsFeeService.getCustomerInputOutputData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     *导出分客户投入产出比报表 数据
     * @ahthor lin_xx
     * @return
     * @throws Exception
     */
    public void exportCustomerInputOutputData() throws Exception {

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isPageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(org.apache.commons.lang3.StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = costsFeeService.getCustomerInputOutputData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);

    }

    /**
     * 跳转到费用明细页面（客户应付费用报表 借方金额）
     * @ahthor lin_xx
     * @return
     * @throws Exception
     */
    public String showCustomerCostPayableDetailPage() throws Exception {

        Map map=CommonUtils.changeMap(request.getParameterMap());
        if(map.containsKey("customerid")){
            request.setAttribute("customerid",map.get("customerid"));
        }
        if(map.containsKey("salesuser")){
            request.setAttribute("salesuser",map.get("salesuser"));
        }
        if(map.containsKey("supplierid")){
            request.setAttribute("supplierid",map.get("supplierid"));
        }
        if(map.containsKey("pid")){
			request.setAttribute("pid",map.get("pid"));
		}
        request.setAttribute("businessdate1",map.get("businessdate1"));
        request.setAttribute("businessdate2",map.get("businessdate2"));
        return SUCCESS;
    }

     /**
      * 获取费用合计 详情
      * @author lin_xx
      * @date 2016/9/22
      */
    public String getCustomerCostPayableDetailData() throws Exception {

        Map map=CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = costsFeeService.getPayableDetailList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }
	 /**
	  *跳转到费用支付明细页面（日常费用录入 金额）
	  * @author lin_xx
	  * @date 2016/10/20
	  */
	public String showDailyCostDetailPage() throws Exception {

		Map map=CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("salesuser")){
			request.setAttribute("salesuser",map.get("salesuser"));
		}
		if(map.containsKey("supplierid")){
			request.setAttribute("supplierid",map.get("supplierid"));
		}
		request.setAttribute("businessdate1",map.get("businessdate1"));
		request.setAttribute("businessdate2",map.get("businessdate2"));
		return SUCCESS;
	}
	/**
	 * 获取费用支出 详情
	 * @author lin_xx
	 * @date 2016/9/22
	 */
	public String getDailyCostDetailPageData() throws Exception {

		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = costsFeeService.getDailyCostDetailList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	 /**
	  *导出费用支付 详情
	  * @author lin_xx
	  * @date 2016/10/20
	  */
	public void exportDailyCostDetailPageData() throws Exception {
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("ispageFlag","true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("businessdate", "业务日期");
		firstMap.put("salesusername","客户业务员");
		firstMap.put("deptname","所属部门");
		firstMap.put("costsortname", "费用科目");
		firstMap.put("amount","金额");
		firstMap.put("remark","备注");
		result.add(firstMap);

		PageData pageData= costsFeeService.getDailyCostDetailList(pageMap);
		List list = pageData.getList();
		for(Object map1 : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(map1);
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
     * 业务员投入产出比报表页面
     * @ahthor lin_xx
     * @return
     * @throws Exception
     */
    public String showSalesuserInputOutputSheetPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }
    /**
     *获取业务员投入产出比报表 数据
     * @ahthor lin_xx
     * @return
     * @throws Exception
     * @date 2016-9-12
     */
    public String getSalesuserInputOutputData() throws Exception {
        Map map=CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData=costsFeeService.getSalesuserInputOutputData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     *导出业务员投入产出比报表 数据
     * @ahthor lin_xx
     * @return
     * @throws Exception
     */
    public void exportSalesuserInputOutputData() throws Exception {

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isPageflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(org.apache.commons.lang3.StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = costsFeeService.getSalesuserInputOutputData(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }

    /**
     *导出 费用明细
     * @ahthor lin_xx
     * @return
     * @throws Exception
     */
    public void exportCustomerCostPayableDetail() throws Exception {

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("ispageFlag","true");
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("oaid", "OA编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("expensesortname", "费用类别");
        firstMap.put("customerid", "客户编码");
        firstMap.put("customername", "客户名称");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername","供应商名称");
        firstMap.put("amount","费用金额");
        result.add(firstMap);

        PageData pageData= costsFeeService.getPayableDetailList(pageMap);
        List list = pageData.getList();
        for(Object map1 : list){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2 = PropertyUtils.describe(map1);
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


}

