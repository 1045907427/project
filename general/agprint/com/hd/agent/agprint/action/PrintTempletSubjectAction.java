/**
 * @(#)PrintTempletSubjectAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action;

import com.hd.agent.agprint.model.PrintTempletSubject;
import com.hd.agent.agprint.service.IPrintTempletSubjectService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.EhcacheUtils;
import com.hd.agent.common.util.FileUtils;
import com.hd.agent.common.util.PageData;

import java.util.Map;

/**
 * 打印模板代码
 * @author zhanghonghui
 */
public class PrintTempletSubjectAction extends BaseAction{
	private PrintTempletSubject printTempletSubject;
	private IPrintTempletSubjectService printTempletSubjectService;
	public PrintTempletSubject getPrintTempletSubject() {
		return printTempletSubject;
	}
	public void setPrintTempletSubject(PrintTempletSubject printTempletSubject) {
		this.printTempletSubject = printTempletSubject;
	}
	public IPrintTempletSubjectService getPrintTempletSubjectService() {
		return printTempletSubjectService;
	}
	public void setPrintTempletSubjectService(IPrintTempletSubjectService printTempletSubjectService) {
		this.printTempletSubjectService = printTempletSubjectService;
	}
	
	
	public String showPrintTempletSubjectListPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板代码列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletSubjectPageListData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=printTempletSubjectService.showPrintTempletSubjectPageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板代码详情
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletSubjectInfo() throws Exception{
		String code=request.getParameter("code");
		PrintTempletSubject printTempletSubject=printTempletSubjectService.showPrintTempletSubjectInfo(code);
		request.setAttribute("printTempletSubject", printTempletSubject);
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板代码添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletSubjectAddPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 打印模板代码添加方法
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PrintTempletSubject",type=2)
	public String addPrintTempletSubject() throws Exception{
		Map resultMap=printTempletSubjectService.addPrintTempletSubject(printTempletSubject);
		Boolean flag=null;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", flag);
			}
		}else{
			flag=false;
			resultMap.put("flag", false);
		}
		addJSONObject("flag", flag);
		
		addLog("添加打印模板代码 代码分类:"+printTempletSubject.getCode(), flag);
		//移除缓存的打印模板代码
		EhcacheUtils.removeCache("PrintTempletListJsonCache");
		updatePrintTempletSubjectCacheJs();
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板代码修改页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletSubjectEditPage() throws Exception{
		String code=request.getParameter("code");
		PrintTempletSubject printTempletSubject=printTempletSubjectService.showPrintTempletSubjectInfo(code);
		if(null==printTempletSubject){
			return "viewSuccess";
		}
		request.setAttribute("printTempletSubject", printTempletSubject);
		return SUCCESS;
	}
	
	/**
	 * 修改打印模板代码方法
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PrintTempletSubject",type=3)
	public String editPrintTempletSubject() throws Exception{
		Map resultMap=printTempletSubjectService.editPrintTempletSubject(printTempletSubject);
		Boolean flag=null;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", flag);
			}
		}else{
			flag=false;
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		
		addLog("修改打印模板代码 代码分类:"+printTempletSubject.getCode(), flag);
		//移除缓存的打印模板代码
		EhcacheUtils.removeCache("PrintTempletListJsonCache");
		updatePrintTempletSubjectCacheJs();
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板代码查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletSubjectViewPage() throws Exception{
		String code=request.getParameter("code");
		PrintTempletSubject printTempletSubject=printTempletSubjectService.showPrintTempletSubjectInfo(code);
		request.setAttribute("printTempletSubject", printTempletSubject);
		return SUCCESS;
	}
	/**
	 * 禁用打印模板代码
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PrintTempletSubject",type=3)
	public String disablePrintTempletSubject() throws Exception{
		String code=request.getParameter("code");
		Map resultMap=printTempletSubjectService.disablePrintTempletSubject(code);
		Boolean flag=null;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", flag);
			}
		}else{
			flag=false;
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		
		addLog("禁用打印模板代码代码分类:"+code, flag);
		//移除缓存的打印模板代码
		EhcacheUtils.removeCache("PrintTempletListJsonCache");
		updatePrintTempletSubjectCacheJs();
		return SUCCESS;
	}
	
	/**
	 * 启用打印模板代码
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="PrintTempletSubject",type=3)
	public String enablePrintTempletSubject() throws Exception{
		String code=request.getParameter("code");
		Map resultMap=printTempletSubjectService.enablePrintTempletSubject(code);
		Boolean flag=null;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", flag);
			}
		}else{
			flag=false;
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		
		addLog("启用打印模板代码 代码:"+code, flag);
		//移除缓存的打印模板代码
		EhcacheUtils.removeCache("PrintTempletListJsonCache");
		updatePrintTempletSubjectCacheJs();
		return SUCCESS;
	}
	
	/**
	 * 删除打印模板代码
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	@UserOperateLog(key="PrintTempletSubject",type=4,value="")
	public String deletePrintTempletSubject()throws Exception{
		String code=request.getParameter("code");
		Map resultMap= printTempletSubjectService.deletePrintTempletSubject(code);
		Boolean flag=null;
		if(null!=resultMap){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
				resultMap.put("flag", flag);
			}
		}else{
			flag=false;
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		addLog("删除打印模板代码 代码:"+code, flag);
		//移除缓存的打印模板代码
		EhcacheUtils.removeCache("PrintTempletListJsonCache");
		updatePrintTempletSubjectCacheJs();
		return SUCCESS;
	}

	/**
	 * 批量删除打印模板代码
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="PrintTempletSubject",type=4,value="")
	public String deletePrintTempletSubjectMore()throws Exception{
		String codearrs = request.getParameter("codearrs");
		Map map= printTempletSubjectService.deletePrintTempletSubjectMore(codearrs);
		Boolean flag=false;
		if(null!=map){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除打印模板代码 编号:"+codearrs,flag);
		}else{
			addLog("批量删除打印模板代码 编号失败:"+codearrs);
		}
		addJSONObject(map);
		//移除缓存的打印模板代码
		EhcacheUtils.removeCache("PrintTempletListJsonCache");
		updatePrintTempletSubjectCacheJs();
		return SUCCESS;
	}
	/**
	 * 打印模板代码代码是否被使用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月3日
	 */
	public String isUsedPrintTempletSubjectCode() throws Exception{
		String code=request.getParameter("code");		
		boolean flag=printTempletSubjectService.isUsedPrintTempletSubjectCode(code);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	/**
	 * 打印模板代码列表点击打开模板配置列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月31日
	 */
	public String showPrintTempletSubjectFoldListPage() throws Exception{
		return SUCCESS;
	}


	/**
	 * 生成printtempletsubject js
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年8月20日
	 */
	public void updatePrintTempletSubjectCacheJs() throws Exception{
		String codestr = printTempletSubjectService.getPrintTempletSubjectListJsonCache();
		String filepath = request.getSession().getServletContext().getRealPath("/");
		filepath +="js/printTempletSubjectCache.js";
		filepath=CommonUtils.filterFilePathSaparator(filepath);
		if(null!=codestr&&!"".equals(codestr.trim())){
			codestr="var printTempletSubjectCache="+codestr+";";
		}else{
			codestr="var printTempletSubjectCache={};";
		}
		FileUtils.writeTxtFile(codestr, filepath);
	}
}

