/**
 * @(#)SysCodeAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 panxiaoxiao 创建版本
 */
package com.hd.agent.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.EhcacheUtils;
import com.hd.agent.common.util.FileUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.service.ISysCodeService;

/**
 * 代码
 * 对映struts-system配置
 * @author panxiaoxiao
 */
public class SysCodeAction extends BaseAction{
	private Map map;
	private SysCode sysCode;
	private ISysCodeService sysCodeService;
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public SysCode getSysCode() {
		return sysCode;
	}
	public void setSysCode(SysCode sysCode) {
		this.sysCode = sysCode;
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
	 * @date 2012-12-15
	 */
	public String index() throws Exception{
		return "success";
	}
	
	public String showSysCodeListPage() throws Exception{
		return "success";
	}
	
	/**
	 * 显示公共代码列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public String showSysCodeList() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=sysCodeService.showSysCodePageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 显示代码类型数据，返回json格式数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-18
	 */
	public String showSysCodeTypes() throws Exception{
		List typeList=sysCodeService.showSysCodeTypes();
		addJSONArray(typeList);
		return "success";
	}
	
	/**
	 * 显示公共代码详情
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public String showSysCodeInfo() throws Exception{
		String code=request.getParameter("code");
		String type=request.getParameter("type");
		SysCode sysCode=sysCodeService.showSysCodeInfo(code, type);
		request.setAttribute("sysCode", sysCode);
		return "success";
	}
	
	/**
	 * 显示公共代码添加页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public String showSysCodeAddPage() throws Exception{
		return "success";
	}
	/**
	 * 公共代码添加方法
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="SysCode",type=2)
	public String addSysCode() throws Exception{
		boolean flag=sysCodeService.addSysCode(sysCode);
		addJSONObject("flag", flag);
		//移除缓存的系统编码数据
		EhcacheUtils.removeCache("SysCodeCache");
		updateSyscodeJs();
		addLog("添加系统编码 类型:"+sysCode.getType()+"-"+sysCode.getCode(), flag);
		return "success";
	}
	
	/**
	 * 显示公共代码修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	public String showSysCodeEditPage() throws Exception{
		String code=request.getParameter("code");
		String type=request.getParameter("type");
		SysCode sysCode=sysCodeService.showSysCodeInfo(code,type);
		request.setAttribute("sysCode", sysCode);
		return "success";
	}
	
	/**
	 * 修改公共代码方法
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="SysCode",type=3)
	public String editSysCode() throws Exception{
		boolean flag=sysCodeService.editSysCode(sysCode);
		addJSONObject("flag", flag);
		//移除缓存的系统编码数据
		EhcacheUtils.removeCache("SysCodeCache");
		updateSyscodeJs();
		addLog("修改系统编码 类型:"+sysCode.getType()+"-"+sysCode.getCode(), flag);
		return "success";
	}
	
	/**
	 * 禁用代码
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="SysCode",type=3)
	public String disableSysCode() throws Exception{
		String code=request.getParameter("code");
		String type=request.getParameter("type");
		boolean flag=sysCodeService.disableSysCode(code,type);
		addJSONObject("flag", flag);
		//移除缓存的系统编码数据
		EhcacheUtils.removeCache("SysCodeCache");
		updateSyscodeJs();
		addLog("禁用系统编码 类型:"+type+"-"+code, flag);
		return "success";
	}
	
	/**
	 * 启用代码
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="SysCode",type=3)
	public String enableSysCode() throws Exception{
		String code=request.getParameter("code");
		String type=request.getParameter("type");
		boolean flag=sysCodeService.enableSysCode(code,type);
		addJSONObject("flag", flag);
		//移除缓存的系统编码数据
		EhcacheUtils.removeCache("SysCodeCache");
		updateSyscodeJs();
		addLog("启用系统编码 类型:"+type+"-"+code, flag);
		return "success";
	}
	
	/**
	 * 删除代码
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="SysCode",type=4,value="")
	public String deleteSysCode()throws Exception{
		Map map = new HashMap();
		map.put("code", request.getParameter("code"));
		map.put("type", request.getParameter("type"));
		map.put("state", request.getParameter("state"));
		boolean flag = sysCodeService.deleteSysCode(map);
		addJSONObject("flag", flag);
		//移除缓存的系统编码数据
		EhcacheUtils.removeCache("SysCodeCache");
		updateSyscodeJs();
		addLog("启用系统编码 类型:"+map.get("type")+"-"+map.get("code"), flag);
		return "success";
	}
	/**
	 * 根据编码表 重新生成syscode.js
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年8月20日
	 */
	public void updateSyscodeJs() throws Exception{
		String codestr = sysCodeService.getAllSysCodeList();
		String filepath = request.getSession().getServletContext().getRealPath("/");  
		filepath +="js/syscode.js";
		if(null!=codestr&&!"".equals(codestr.trim())){
			codestr="var codeJsonCache="+codestr+";";
		}else{
			codestr="var codeJsonCache={};";
		}
		FileUtils.writeTxtFile(codestr, filepath);
	}
}

