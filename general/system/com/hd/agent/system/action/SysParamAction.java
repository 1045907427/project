/**
 * @(#)SysParamAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-20 panxiaoxiao 创建版本
 */
package com.hd.agent.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.FileUtils;
import com.hd.agent.common.util.SpringContextUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.druid.support.json.JSONUtils;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysCodeService;
import com.hd.agent.system.service.ISysParamService;

/**
 * 系统参数
 * 对映struts-system配置
 * @author panxiaoxiao
 */
public class SysParamAction extends BaseAction {
	private Map map;
	
	private SysParam sysParam;
	
	private ISysParamService sysParamService;
	private ISysCodeService sysCodeService;

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public SysParam getSysParam() {
		return sysParam;
	}

	public void setSysParam(SysParam sysParam) {
		this.sysParam = sysParam;
	}

	public ISysParamService getSysParamService() {
		return sysParamService;
	}

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}
	
	public ISysCodeService getSysCodeService() {
		return sysCodeService;
	}

	public void setSysCodeService(ISysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}

	/**
	 * 显示首页
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String Index() throws Exception{
		return "success";
	}
	
	public String showSysParamListPage() throws Exception{
		return "success";
	}
	
	/**
	 * 系统参数2
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年1月6日
	 */
	public String showSysParamListPage2() throws Exception{
		return "success";
	}
	
	/**
	 * 系统参数模块Panel
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年1月6日
	 */
	public String sysParamModualPage()throws Exception{
		String codeVal = request.getParameter("codeVal");
		return "codemodual"+codeVal;
	}
	
	/**
	 * 显示系统参数列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String showSysParamList() throws Exception{
		Map mapMap=request.getParameterMap();
		Map map=CommonUtils.changeMap(mapMap);
		pageMap.setCondition(map);
		PageData pageData=sysParamService.showSysParamPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 根据模块获取表单定制的数据
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年1月6日
	 */
	public String showSysParamListByModualId() throws Exception{
		String id = request.getParameter("id");
		List<SysParam> params=sysParamService.showSysParamListByModualId(id);
		Map rsmap = new HashMap();
		
		
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		if(params!=null&&params.size()>0){
			for(SysParam sysparam:params){
				map1.put(sysparam.getPname(),sysparam.getPvalue());
			}
			for(SysParam sysparam:params){
				map2.put(sysparam.getPname(),sysparam.getPuser());
			}
		}
		rsmap.put("values", map1);
		rsmap.put("pusers", map2);
		addJSONObject(rsmap);
		return "success";
	}
	

	
	/**
	 * 显示模块ID类型数据，返回json格式数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String showSysCodeModuleid()throws Exception{
		List codeList=sysCodeService.showSysCodeCodes();
		addJSONArray(codeList);
		return "success";
	}
	
	
	/**
	 * 模块更新系统参数
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年1月6日
	 */
	public String updateSysParamByModual()throws Exception{
		String json = request.getParameter("updateInfo");
		ObjectMapper mapper = new ObjectMapper();
		Map map=mapper.readValue(json, java.util.Map.class);
		Map rs=sysParamService.updateSysCodeByModual(map);
		addJSONObject(rs);
		return "success";
	}
	
	/**
	 * 显示系统参数详情信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String showSysParamInfo() throws Exception{
		String paramid=request.getParameter("paramid");
		SysParam sysParam=sysParamService.showSysParamInfo(paramid);
		request.setAttribute("sysParam", sysParam);
		return "success";
	} 
	
	/**
	 * 显示系统参数详情信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String showSysParamInfoByName() throws Exception{
		String name=request.getParameter("name");
		SysParam sysParam=sysParamService.getSysParam(name);
		request.setAttribute("sysParam", sysParam);
		return "success";
	}

	/**
	 * 显示系统参数详情信息JSON格式<br/>
	 * {flag:boolean,data:{}}
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhang_honghui
	 * @date Dec 21, 2016
	 */
	public String getSysParamInfoJsonByName() throws Exception{
		String name=request.getParameter("name");
		SysParam sysParam=sysParamService.getSysParam(name);
		boolean flag=true;
		if(sysParam==null){
			sysParam=new SysParam();
			flag=false;
		}
		Map resultMap=new HashMap();
		resultMap.put("flag",flag);
		resultMap.put("data",sysParam);
		addJSONObject(resultMap);
		return SUCCESS;
	}
	
	/**
	 * 显示添加系统参数页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String showSysParamAddPage()throws Exception{
		String moduleid = request.getParameter("moduleid");
		request.setAttribute("moduleid", moduleid);
		return "success";
	}
	
	/**
	 * 添加系统参数
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	@UserOperateLog(key="SysParam",type=2,value="")
	public String addSysParam()throws Exception{
		SysUser sysUser = getSysUser();
		sysParam.setAdduserid(sysUser.getUserid());
		boolean flag=sysParamService.addSysParam(sysParam);
		//添加日志内容
		addLog("新增系统参数 编号:"+sysParam.getParamid(),flag);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 显示系统参数修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String showSysParamEditPage()throws Exception{
		String paramid=request.getParameter("paramid");
		SysParam sysParam=sysParamService.showSysParamInfo(paramid);
		request.setAttribute("sysParam", sysParam);
		return "success";
	}
	/**
	 * 修改系统参数
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	@UserOperateLog(key="SysParam",type=3,value="")
	public String editSysParam()throws Exception{
		SysUser sysUser = getSysUser();
		sysParam.setAdduserid(sysUser.getUserid());
		boolean flag=sysParamService.editSysParam(sysParam);
		if(flag) {
			if ("AgPrintToolType".equals(sysParam.getPname()) || "AgPrintViewToolType".equals(sysParam.getPname())) {
				createSysPrintToolTypeJs();
			}
		}
		//添加日志内容
		addLog("修改系统参数 编号:"+sysParam.getParamid(),flag);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 停用系统参数
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	@UserOperateLog(key="SysParam",type=0,value="")
	public String disableSysParam()throws Exception{
		String paramid=request.getParameter("paramid");
		boolean flag=sysParamService.disableSysParam(paramid);
		if(flag){
			SysParam sysParam=sysParamService.showSysParamInfo(paramid);
			if(null!=sysParam){
				if ("AgPrintToolType".equals(sysParam.getPname()) || "AgPrintViewToolType".equals(sysParam.getPname())) {
					createSysPrintToolTypeJs();
				}
			}
		}
		//添加日志内容
		addLog("停用系统参数 编号:"+paramid,flag);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 启用系统参数
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	@UserOperateLog(key="SysParam",type=0,value="")
	public String enableSysParam()throws Exception{
		String paramid=request.getParameter("paramid");
		boolean flag=sysParamService.enableSysParam(paramid);
		if(flag){
			SysParam sysParam=sysParamService.showSysParamInfo(paramid);
			if(null!=sysParam){
				if ("AgPrintToolType".equals(sysParam.getPname()) || "AgPrintViewToolType".equals(sysParam.getPname())) {
					createSysPrintToolTypeJs();
				}
			}
		}
		//添加日志内容
		addLog("启用系统参数 编号:"+paramid,flag);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 验证参数名的唯一性
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public String  searchPname() throws Exception{
		String pname=request.getParameter("pname");
		boolean flag=sysParamService.searchPname(pname);
		addJSONObject("flag",flag);
		return "success";
	}
	
	/**
	 * 验证模块ID是否存在
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-22
	 */
	public String verifyCode() throws Exception{
		String codename=request.getParameter("codename");
		String type=request.getParameter("type");
		String code=sysCodeService.codenametocode(codename,type);
		boolean flag=sysCodeService.searchCode(code, type);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 根据模块Id获取模块名称
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-22
	 */
	public String searchCodeName() throws Exception{
		String code=request.getParameter("code");
		String type=request.getParameter("type");
		String codeName=sysCodeService.searchCodeName(code,type);
		addJSONObject("codeName", codeName);
		return "success";
	}
	
	/**
	 * 获取系统参数模块信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 10, 2015
	 */
	public String getSysParamModuleList()throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		map.put("type", "sysParamModule");
		map.put("state", "1");
		map.put("flag", true);
		pageMap.setCondition(map);
		PageData pageData = sysCodeService.showSysCodePageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 生成打印方式类型js
	 * @param
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Sep 07, 2017
	 */
	private void createSysPrintToolTypeJs() throws Exception{
		String filepath = request.getSession().getServletContext().getRealPath("/");
		filepath +="js/sysPrintToolTypeCache.js";
		String printtoolstr=sysParamService.getPrintToolTypeJsonCache();
		if(null!=printtoolstr&&!"".equals(printtoolstr.trim())){
			printtoolstr="var sysPrintToolTypeCache="+printtoolstr+";";
		}else{
			printtoolstr="var sysPrintToolTypeCache={};";
		}
		FileUtils.writeTxtFile(printtoolstr,filepath);
	}
}

