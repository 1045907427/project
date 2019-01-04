/**
 * @(#)DeptIncomeAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月21日 chenwei 创建版本
 */
package com.hd.agent.basefiles.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.FilesLevel;
import com.hd.agent.basefiles.model.Subject;
import com.hd.agent.basefiles.service.ISubjectService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.SpringContextUtils;

/**
 * 
 * action
 * @author chenwei
 */
public class SubjectAction extends BaseFilesAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9114650638019113373L;

	private Subject subject;
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}	
	private ISubjectService subjectService;

	public ISubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}
	/**
	 * 科目分类
	 */
	/**===================================================================================*/
	public String showSubjectTypeListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 显示科目分类列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showSubjectTypePageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		map.put("istypehead", "1");
		pageMap.setCondition(map);
		PageData pageData=subjectService.showSubjectPageList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	/**
	 * 科目分类添加页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showSubjectTypeAddPage() throws Exception{
		int len=0;
		int nextLen = getBaseTreeFilesNext("t_base_subject", len);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	/**
	 * 科目分类添加方法
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Subject",type=2)
	public String addSubjectType() throws Exception{
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(subject.getId())){
			resultMap.put("flag", false);
			resultMap.put("msg", "编码不能为空");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(subject.getName())){
			resultMap.put("flag", false);
			resultMap.put("msg", "名称不能为空");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(subject.getTypecode())){
			resultMap.put("flag", false);
			resultMap.put("msg", "分类代码不能为空");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		SysUser sysUser=getSysUser();
		subject.setAdduserid(sysUser.getUserid());
		subject.setAddusername(sysUser.getName());
		resultMap=subjectService.addSubjectType(subject);
		addJSONObject(resultMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		//移除缓存的科目数据
		//EhcacheUtils.removeCache("SubjectCache");
		addLog("添加科目分类   编号"+subject.getId()+"-代码:"+subject.getId(), flag);
		return SUCCESS;
	}
	/**
	 * 科目分类修改页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showSubjectTypeEditPage() throws Exception{
		String id=request.getParameter("id");
		Subject subject=subjectService.getSubjectTypeById(id);
		int len=0;
		int nextLen = getBaseTreeFilesNext("t_base_subject", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("subject", subject);
		return SUCCESS;
	}
	/**
	 * 科目分类修改方法
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Subject",type=2)
	public String editSubjectType() throws Exception{
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(subject.getId())){
			resultMap.put("flag", false);
			resultMap.put("msg", "编码不能为空");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(subject.getOldid())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		resultMap=subjectService.editSubjectType(subject);
		addJSONObject(resultMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		//移除缓存的科目数据
		//EhcacheUtils.removeCache("SubjectCache");
		addLog("修改科目分类   编号"+subject.getId()+"-代码:"+subject.getId(), flag);
		return SUCCESS;
	}
	/**
	 * 科目分类查看页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showSubjectTypeViewPage() throws Exception{
		String id=request.getParameter("id");
		Subject subject=subjectService.getSubjectTypeById(id);
		request.setAttribute("subject", subject);
		return SUCCESS;
	}
	/**
	 * 禁用科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Subject",type=0)
	public String disableSubjectType() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=subjectService.disableSubjectType(id);
		
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
		addLog("禁用科目分类 编号:"+id, flag);
		return SUCCESS;
	}
	/**
	 * 启用科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Subject",type=0)
	public String enableSubjectType() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=subjectService.enableSubjectType(id);
		
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
		addLog("启用科目分类 编号:"+id, flag);
		return SUCCESS;
	}
	/**
	 * 删除科目分类
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="Subject",type=4,value="")
	public String deleteSubjectType()throws Exception{
		String id=request.getParameter("id");
		Map resultMap = subjectService.deleteSubjectType(id);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", flag);
			}
		}
		addJSONObject(resultMap);
		addLog("删除科目分类 编号:"+id, flag);
		return SUCCESS;
	}
	/**
	 * 科目
	 */
	/**===================================================================================*/
	
	/**
	 * 科目总页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月23日
	 */
	public String showSubjectPage() throws Exception{
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_subject");
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
	 * 科目页面子页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月23日
	 */
	public String showSubjectSubPage() throws Exception{
		String typeid=request.getParameter("typeid");
		if(null==typeid || "".equals(typeid.trim())){
			request.setAttribute("MSG", "未能找到相关科目分类信息");
			return ERROR;
		}
		Subject aSubjectType=subjectService.getSubjectTypeById(typeid.trim());
		if(null==aSubjectType){
			request.setAttribute("MSG", "未能找到相关科目分类信息");
			return ERROR;
		}
		if(StringUtils.isEmpty(aSubjectType.getTypecode())){
			request.setAttribute("MSG", "该科目分类代码有误");
			return ERROR;
		}
		List list = getBaseFilesLevelService().showFilesLevelList("t_base_subject");
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
		request.setAttribute("subjectType", aSubjectType);
		return SUCCESS;
	}
	
	
	public String showSubjectListPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示科目列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showSubjectPageList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		map.put("istypehead", "0");
		pageMap.setCondition(map);
		PageData pageData=subjectService.showSubjectPageList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
	
	
	
	/**
	 * 科目添加页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showSubjectAddPage() throws Exception{
		String typeid=request.getParameter("typeid");
		if(null==typeid || "".equals(typeid.trim())){
			request.setAttribute("MSG", "未能找到相关科目分类信息");
			return ERROR;
		}
		Subject aSubjectType=subjectService.getSubjectTypeById(typeid.trim());
		if(null==aSubjectType){
			request.setAttribute("MSG", "未能找到相关科目分类信息");
			return ERROR;
		}
		if(StringUtils.isEmpty(aSubjectType.getTypecode())){
			request.setAttribute("MSG", "该科目分类代码有误");
			return ERROR;
		}
		String id = request.getParameter("id");//当为空时添加的科目档案为一级目录，否则该编号为添加科目档案的父级
		Subject subject=subjectService.showSubjectById(id);
		if(null==subject){
			request.setAttribute("MSG", "未能找到相关科目信息");
			return ERROR;			
		}
		int len=0;
		if(null!=subject && StringUtils.isNotEmpty(subject.getId())){
			len=subject.getId().length();
		}

		int nextLen = getBaseTreeFilesNext("t_base_subject", len);
		request.setAttribute("subject", subject);
		request.setAttribute("subjectType", aSubjectType);
		request.setAttribute("len", nextLen);
		return SUCCESS;
	}
	/**
	 * 科目添加方法
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Subject",type=2)
	public String addSubject() throws Exception{
		String type = request.getParameter("type");
		if("hold".equals(type)){
			subject.setState("3");
		}
		else{
			subject.setState("2");
		}
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(subject.getTypeid())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关科目分类信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		SysUser sysUser=getSysUser();
		subject.setAdduserid(sysUser.getUserid());
		subject.setAddusername(sysUser.getName());
		resultMap=subjectService.addSubject(subject);
		addJSONObject(resultMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		//移除缓存的科目数据
		//EhcacheUtils.removeCache("SubjectCache");
		addLog("添加科目档案  编号"+subject.getId()+"-代码:"+subject.getId(), flag);
		
		Tree node = new Tree();
		node.setId(subject.getId());
		node.setParentid(subject.getPid());
		node.setState(subject.getState());
		node.setText(subject.getThisname());
		resultMap.put("node", node);
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 显示科目修改页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showSubjectEditPage() throws Exception{
		String id=request.getParameter("id");
		int len = 0;
		Subject subject=subjectService.showSubjectById(id);
		if(null==subject ){
			request.setAttribute("MSG", "未能找到相关科目信息");
			return ERROR;			
		}
		if(StringUtils.isNotEmpty(subject.getPid())){	
			len=subject.getPid().length();
			Subject pSubject=subjectService.showSubjectById(subject.getPid());
			if(null!=pSubject){
				request.setAttribute("parentName", pSubject.getName());
			}
		}

		if(StringUtils.isEmpty(subject.getTypecode())){
			request.setAttribute("MSG", "未能找到相关科目分类信息");
			return ERROR;
		}
		Subject aSubjectType=subjectService.getSubjectTypeByCode(subject.getTypecode());
		if(null==aSubjectType){
			request.setAttribute("MSG", "未能找到相关科目分类信息");
			return ERROR;
		}
		if(StringUtils.isEmpty(aSubjectType.getTypecode())){
			request.setAttribute("MSG", "该科目分类代码有误");
			return ERROR;
		}
		int nextLen = getBaseTreeFilesNext("t_base_subject", len);
		boolean editFlag=canTableDataDelete("t_base_subject", subject.getId());
		request.setAttribute("editFlag", editFlag);
		request.setAttribute("len", nextLen);
		request.setAttribute("subject", subject);
		request.setAttribute("subjectType", aSubjectType);
		return SUCCESS;
	}
	
	/**
	 * 修改科目方法
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Subject",type=3)
	public String editSubject() throws Exception{
		String type = request.getParameter("type");
		if(!"1".equals(subject.getState()) && !"0".equals(subject.getState())){
			if("save".equals(type)){
				subject.setState("2");
			}
		}
		Map resultMap=subjectService.editSubject(subject);
		addJSONObject(resultMap);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean) resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		//移除缓存的科目数据
		//EhcacheUtils.removeCache("SubjectCache");
		addLog("修改科目档案  编号"+subject.getId()+"-代码:"+subject.getId(), flag);
		return SUCCESS;
	}
	/**
	 * 显示科目复制页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月24日
	 */
	public String showSubjectCopyPage() throws Exception{
		String id = request.getParameter("id");
		Subject subject = subjectService.showSubjectById(id);
		if(subject != null && StringUtils.isNotEmpty(subject.getPid())){
			Subject pSubject = subjectService.showSubjectById(subject.getPid());
			request.setAttribute("parentName", pSubject.getName());
		}
		int len = 0;
		String name="",pname="";
		if(subject != null && StringUtils.isNotEmpty(subject.getPid())){
			len = subject.getPid().length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_finance_expenses_sort", len);
		request.setAttribute("len", nextLen);
		request.setAttribute("subject", subject);
		return SUCCESS;
	}

	/**
	 * 显示科目查看页面
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	public String showSubjectViewPage() throws Exception{
		String code=request.getParameter("id");
		Subject subject=subjectService.showSubjectById(code);
		request.setAttribute("subject", subject);
		return SUCCESS;
	}
	/**
	 * 获取科目树形数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年9月24日
	 */
	public String getSubjectTree()throws Exception{
		List result = new ArrayList();
		String typeid=request.getParameter("typeid");
		if(null==typeid ||"".equals(typeid.trim())){
			addJSONArray(result);
			return SUCCESS;
		}
		Subject aSubjectType=subjectService.getSubjectTypeById(typeid.trim());
		if(null==aSubjectType){
			addJSONArray(result);
			return SUCCESS;
		}
		Map<String,String> first = new HashMap<String,String>();
		Tree pTree = new Tree();
		pTree.setId(aSubjectType.getId());
		pTree.setState(aSubjectType.getState());
		pTree.setText(aSubjectType.getName());
		pTree.setOpen("true");
		result.add(pTree);
		Map paramMap=new HashMap();
		paramMap.put("typecode", aSubjectType.getTypecode());
		paramMap.put("istypehead", "0");
		List<Subject> list =new ArrayList<Subject>();
		
		list= subjectService.showSubjectListByMap(paramMap);
		if(list.size() > 0){
			for(Subject subject:list){
				Tree cTree = new Tree();
				cTree.setId(subject.getId());
				cTree.setParentid(subject.getPid());
				cTree.setText(subject.getThisname());
				cTree.setState(subject.getState());
				result.add(cTree);
			}
		}
		addJSONArray(result);
		return SUCCESS;
	}
	/**
	 * 禁用科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="Subject",type=3)
	public String disableSubject() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=subjectService.disableSubject(id);
		
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
		//移除缓存的科目数据
		//EhcacheUtils.removeCache("SubjectCache");
		addLog("禁用科目档案 编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 启用科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="Subject",type=3)
	public String enableSubject() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=subjectService.enableSubject(id);
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
		//移除缓存的科目数据
		//EhcacheUtils.removeCache("SubjectCache");
		addLog("启用科目档案  编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 删除科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="Subject",type=4,value="")
	public String deleteSubject()throws Exception{
		String id=request.getParameter("id");
		Map resultMap = subjectService.deleteSubjectById(id);
		Boolean flag=false;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", flag);
			}
		}
		addJSONObject(resultMap);
		addLog("删除科目档案 编号:"+id, flag);
		return SUCCESS;
	}
	/**
	 * 批量删除科目
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="Subject",type=4,value="")
	public String deleteSubjectMore()throws Exception{
		String idarrs = request.getParameter("idarrs");
		Map map= subjectService.deleteSubjectMore(idarrs);
		Boolean flag=false;
		if(null!=map){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除科目档案 编号:"+idarrs,flag);
		}else{
			addLog("批量删除科目档案 编号失败:"+idarrs);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * ==============================================
	 * 共用方法
	 * ==============================================
	 */
	/**
	 * 验证科目档案编码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String isUsedSubjectID()throws Exception{
		String id = request.getParameter("id");
		Subject subject = subjectService.getSubjectDetail(id);
		boolean flag = false;
		if(null!=subject){
			flag = true;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 验证科目档案名称是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String isUsedSubjectName()throws Exception{
		String name = request.getParameter("name");
		String typeid = request.getParameter("typeid");
		String istypehead=request.getParameter("istypehead");
		boolean flag=false;
		if(null==name || "".equals(name.trim())){
			flag=false;
		}else{
			Map queryMap=new HashMap();
			queryMap.put("name", name);
			if(null!=typeid && !"".equals(typeid.trim())){
				queryMap.put("typeid", typeid.trim());
			}
			if(null!=istypehead && !"".equals(istypehead.trim())){
				queryMap.put("istypehead", istypehead.trim());
			}
			flag= subjectService.getSubjectCountByMap(queryMap)>0;
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 验证科目档案分类代码是否被使用，true 被使用，false 未被使用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 21, 2013
	 */
	public String isUsedSubjectType()throws Exception{
		String typecode = request.getParameter("typecode");
		boolean flag=false;
		if(null==typecode || "".equals(typecode.trim())){
			flag=false;
		}else{
			flag= subjectService.isUsedSubjectType(typecode);
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 科目档案导入
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2016年2月19日
	 */
	@UserOperateLog(key="suject",type=2,value="科目档案导入")
	public String importSubjectListData() throws Exception{
		String typeid=request.getParameter("typeid");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null==typeid ||"".equals(typeid.trim())){
			resultMap.put("error", true);
			resultMap.put("msg", "未能找到相关科目分类");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		Subject aSubjectType=subjectService.getSubjectTypeById(typeid.trim());
		if(null==aSubjectType){
			resultMap.put("error", true);
			resultMap.put("msg", "未能找到相关科目分类");
			addJSONObject(resultMap);
			return SUCCESS;			
		}
		try {
			//模板文件路径
	        String subjectTempletFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/SubjectTemplet.xls");
			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
			List<String> paramList2 = new ArrayList<String>();
			for(String str : paramList){
				if("编号".equals(str)){
					paramList2.add("id");
				}
				else if("名称".equals(str)){
					paramList2.add("name");
				}
				else if("本级编号".equals(str)){
					paramList2.add("thisid");
				}
				else if("本级名称".equals(str)){
					paramList2.add("thisname");
				}
				else if("上级编号".equals(str)){
					paramList2.add("pid");
				}
				else if("分类代码".equals(str)){
					paramList2.add("typecode");
				}
				else if("状态".equals(str)){
					paramList2.add("state");
				}
				else if("备注".equals(str)){
					paramList2.add("remark");
				}else{
					paramList2.add("null");
				}
			}
			List<String> dataCellList = new ArrayList<String>();
			dataCellList.add("id");
			dataCellList.add("thisname");
			dataCellList.add("state");
			dataCellList.add("remark");
			dataCellList.add("errormsg");
			
			if(paramList.size() == paramList2.size()){
				List result = new ArrayList();
				List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
                List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
                if(list.size() != 0){
					for(Map<String, Object> dataMap : list){
						Subject subject=new Subject();						
						Field[] fields = Subject.class.getDeclaredFields();
                        try{
                            //获取的导入数据格式转换
                            DRCastTo(dataMap,fields);
                            //BeanUtils.populate(object, map4);
                            PropertyUtils.copyProperties(subject, dataMap);
                            subject.setTypeid(aSubjectType.getId());
                            subject.setTypecode(aSubjectType.getTypecode());
                            result.add(subject);
                        }catch (Exception ex){
                        	dataMap.put("errormsg", "导入时，数据格式转换出错，异常："+ex.getMessage());
                            errorList.add(dataMap);
                        }
					}
					if(result.size() != 0){
						resultMap = subjectService.addDRSubjectExcel(result,aSubjectType.getId());
					}
					if(resultMap.containsKey("errorDataList") && null!=resultMap.get("errorDataList")){
						List<Subject> errorDataList=(List<Subject>)resultMap.get("errorDataList");
						for(Subject item:errorDataList){
							Map itemMap = PropertyUtils.describe(item);
							if(null!=itemMap){
								errorList.add(itemMap);
							}
						}
					}
                    if(errorList.size() > 0){
                    	IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
                        String fileid=attachFileService.createExcelAndAttachFile(errorList, dataCellList, subjectTempletFilePath,"科目档案导入失败");
                        resultMap.put("msg","导入失败"+errorList.size()+"条");
                        resultMap.put("errorid",fileid);
                   }
				}else{
					resultMap.put("excelempty", true);
				}
			}
			else{
				resultMap.put("versionerror", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", true);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 科目档案导出列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	@UserOperateLog(key="suject",type=0,value="科目档案导出")
	public String exportSubjectListData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		map.put("isNoPageflag", "true");
		pageMap.setCondition(map);
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "科目档案列表";
		}
		if(StringUtils.isEmpty(title)){
			title = "科目档案列表";
		}
		PageData pageData=subjectService.showSubjectPageList(pageMap);
		ExcelUtils.exportExcel(exportSubjectDataFillter(pageData.getList()), title);
		return SUCCESS;
	}
	private List<Map<String, Object>> exportSubjectDataFillter(List<Subject> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编号");
		firstMap.put("name", "名称");
		firstMap.put("thisid", "本级编号");
		firstMap.put("thisname", "本级名称");
		firstMap.put("pid", "上级编号");
		firstMap.put("typecode", "分类代码");
		firstMap.put("typeid", "分类编号");
		firstMap.put("state", "状态");
		firstMap.put("statename", "状态名称");
		firstMap.put("remark", "备注");
		firstMap.put("leaf", "末级标志");
		firstMap.put("leafname", "末级标志（名称）");
		firstMap.put("addtime", "添加时间");
		firstMap.put("adduserid", "添加用户编号");
		firstMap.put("addusername", "添加用户名称");
		firstMap.put("adddeptid", "建档人部门编号");
		firstMap.put("adddeptname", "建档部门名称");
		firstMap.put("modifyuserid", "修改人用户编号");
		firstMap.put("modifyusername", "修改人姓名");
		firstMap.put("modifytime", "修改时间");
		firstMap.put("openuserid", "启用人用户编号");
		firstMap.put("openusername", "启用人姓名");
		firstMap.put("opentime", "启用时间");
		firstMap.put("closeuserid", "禁用人用户编号");
		firstMap.put("closeusername", "禁用人姓名");
		firstMap.put("closetime", "禁用时间");
		firstMap.put("istypehead", "是否所属分类");
		firstMap.put("istypeheadname", "是否所属分类（名称）");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Subject item : list){
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

