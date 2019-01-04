/**
 * @(#)AgPrintAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-10-27 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.*;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.agprint.service.IPrintTempletService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 打印管理
 * @author zhanghonghui
 */
public class PrintTempletAction extends BaseAction {
	private IPrintTempletService printTempletService;
	public IPrintTempletService getPrintTempletService() {
		return printTempletService;
	}
	public void setPrintTempletService(
			IPrintTempletService printTempletService) {
		this.printTempletService = printTempletService;
	}
	private PrintTemplet printTemplet;
	public PrintTemplet getPrintTemplet() {
		return printTemplet;
	}
	public void setPrintTemplet(PrintTemplet printTemplet) {
		this.printTemplet = printTemplet;
	}
	/**
	 * 显示打印模板页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	public String showPrintTempletListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 获取打印模板数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	public String showPrintTempetPageListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());

		pageMap.setCondition(map);
		PageData pageData=printTempletService.showPrintTempletPageListData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 打印模板数据添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	public String showPrintTempletAddPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 添加打印模板数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	@UserOperateLog(key="PrintTemplet",type=2)
	public String addPrintTemplet() throws Exception{
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(printTemplet.getCode())){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写清单代码");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(printTemplet.getName())){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写模板名称");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(printTemplet.getTplresourceid())){
			resultMap.put("flag", false);
			resultMap.put("msg","请选择模板资源文件");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(null!=printTemplet.getCountperpage() && printTemplet.getCountperpage()<=0){
			printTemplet.setCountperpage(null);
		}
		if(!"1".equals(printTemplet.getIsfillblank())){
			printTemplet.setIsfillblank("0");
		}
		resultMap=printTempletService.addPrintTemplet(printTemplet);
		if(null!=resultMap){
			boolean flag=(Boolean)resultMap.get("flag");
			addLog("添加打印模板数据", flag);
		}else{
			addLog("添加打印模板数据失败");			
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}

	/**
	 * 打印模板数据添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	public String showPrintTempletEditPage() throws Exception{
		String id=request.getParameter("id");
		PrintTemplet aPrintTemplet=printTempletService.showPrintTemplet(id);
		if(null!=aPrintTemplet){
			if(null!=aPrintTemplet.getCountperpage() && aPrintTemplet.getCountperpage()<=0) {
				aPrintTemplet.setCountperpage(null);
			}

			StringBuilder sb=null;
			
			PrintTempletSubject printTempletSubject=aPrintTemplet.getPrintTempletSubjectInfo();
			if(null!=printTempletSubject && !"1".equals(printTempletSubject.getState())){
				sb=new StringBuilder();
				sb.append("【");
				sb.append(printTempletSubject.getCode());
				sb.append("】");
				sb.append(printTempletSubject.getName());
				sb.append("(未启用)");
				request.setAttribute("codename", sb.toString());
			}
			
			PrintTempletResource printTempletResource=aPrintTemplet.getPrintTempletResourceInfo();
			if(null!=printTempletResource && !"1".equals(printTempletResource.getState())){
				sb=new StringBuilder();
				sb.append("【");
				sb.append(printTempletResource.getViewid());
				sb.append("】");
				sb.append(printTempletResource.getName());
				sb.append("(未启用)");
				request.setAttribute("tplresourcename", sb.toString());
			}
			
			PrintOrderSeq printOrderSeq=aPrintTemplet.getPrintOrderSeqInfo();
			if(null!=printOrderSeq &&!"1".equals(printOrderSeq.getState())){
				sb=new StringBuilder();
				sb.append("【");
				sb.append(printOrderSeq.getViewid());
				sb.append("】");
				sb.append(printOrderSeq.getName());
				sb.append("(未启用)");
				request.setAttribute("tplorderseqname", sb.toString());
			}
			PrintPaperSize printPaperSize=aPrintTemplet.getPrintPaperSizeInfo();
			if(null!=printPaperSize ){
				sb=new StringBuilder();
				sb.append(printPaperSize.getName());
				if(!"1".equals(printPaperSize.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("papersizename", sb.toString());
			}
		}
		request.setAttribute("printTemplet", aPrintTemplet);
		return SUCCESS;
	}
	/**
	 * 编辑打印模板数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	@UserOperateLog(key="PrintTemplet",type=3)
	public String editPrintTemplet() throws Exception{
		Map resultMap=new HashMap();
		if(null==printTemplet.getId()){
			resultMap.put("flag", false);
			resultMap.put("msg","抱歉，未能找到要修改的信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(null!=printTemplet.getCountperpage() && printTemplet.getCountperpage()<=0){
			printTemplet.setCountperpage(null);
		}
		if(!"1".equals(printTemplet.getIsfillblank())){
			printTemplet.setIsfillblank("0");
		}
		resultMap=printTempletService.updatePrintTemplet(printTemplet);
		if(null!=resultMap){
			boolean flag=(Boolean)resultMap.get("flag");
			addLog("添加打印模板数据", flag);
		}else{
			addLog("添加打印模板数据失败");			
		}

		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 显示打印模板查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	public String showPrintTempletViewPage() throws Exception{
		String id=request.getParameter("id");
		PrintTemplet aPrintTemplet=printTempletService.showPrintTemplet(id);
		if(null!=aPrintTemplet){

			StringBuilder sb=null;
			
			PrintTempletSubject printTempletSubject=aPrintTemplet.getPrintTempletSubjectInfo();
			if(null!=printTempletSubject){				
				sb=new StringBuilder();
				sb.append(printTempletSubject.getName());
				if(!"1".equals(printTempletSubject.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("codename", sb.toString());
			}
			
			PrintTempletResource printTempletResource=aPrintTemplet.getPrintTempletResourceInfo();
			if(null!=printTempletResource ){
				sb=new StringBuilder();
				sb.append("【");
				sb.append(printTempletResource.getViewid());
				sb.append("】");
				sb.append(printTempletResource.getName());
				if(!"1".equals(printTempletResource.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("tplresourcename", sb.toString());
			}
			
			PrintOrderSeq printOrderSeq=aPrintTemplet.getPrintOrderSeqInfo();
			if(null!=printOrderSeq ){
				sb=new StringBuilder();
				sb.append("【");
				sb.append(printOrderSeq.getViewid());
				sb.append("】");
				sb.append(printOrderSeq.getName());
				if(!"1".equals(printOrderSeq.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("tplorderseqname", sb.toString());
			}
			PrintPaperSize printPaperSize=aPrintTemplet.getPrintPaperSizeInfo();
			if(null!=printPaperSize ){
				sb=new StringBuilder();
				sb.append(printPaperSize.getName());
				if(!"1".equals(printPaperSize.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("papersizename", sb.toString());
			}
		}
		request.setAttribute("printTemplet", aPrintTemplet);
		return SUCCESS;
	}	
	
	/**
	 * 删除打印模板数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	@UserOperateLog(key="PrintTemplet",type=3)
	public String deletePrintTemplet() throws Exception{
		Map resultMap=new HashMap();
		String id=request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关打印模板信息");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		resultMap=printTempletService.deletePrintTemplet(id);
		if(null!=resultMap){
			boolean flag=(Boolean)resultMap.get("flag");
			addLog("删除编号为"+id+"打印模板数据", flag);
		}else{
			addLog("删除编号为"+id+"打印模板数据");			
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 添加打印模板数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	@UserOperateLog(key="PrintTemplet",type=3)
	public String updatePrintTempletDefault() throws Exception{		
		String id = request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			addJSONObject("flag", false);
			addJSONObject("msg", "未找到要设置为默认的数据");
			return SUCCESS;
		}
		Map resultMap=printTempletService.enablePrintTemplet(id.trim());
		if(null!=resultMap){
			boolean flag=(Boolean)resultMap.get("flag");
			addLog("设置编号为"+id+"打印模板数据为默认模板", flag);
		}else{
			addLog("设置编号为"+id+"打印模板数据为默认模板");			
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 启用打印模板数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	public String enablePrintTemplet() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			addJSONObject("flag", false);
			addJSONObject("msg", "未找到要启用的数据");
			return SUCCESS;
		}
		Map resultMap=printTempletService.enablePrintTemplet(id.trim());
		if(null!=resultMap){
			boolean flag=(Boolean)resultMap.get("flag");
			addLog("启用编号为"+id+"打印模板数据", flag);
		}else{
			addLog("启用编号为"+id+"打印模板数据");			
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 禁用打印模板数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-10-28
	 */
	public String disablePrintTemplet() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			addJSONObject("flag", false);
			addJSONObject("msg", "未找到要禁用的数据");
			return SUCCESS;
		}
		Map resultMap=printTempletService.disablePrintTemplet(id.trim());
		if(null!=resultMap){
			boolean flag=(Boolean)resultMap.get("flag");
			addLog("禁用编号为"+id+"打印模板数据", flag);
		}else{
			addLog("禁用编号为"+id+"打印模板数据");			
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 下载打印模板源文件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-26
	 */
	public void downloadTempletSourceFile() throws Exception{
		String id = request.getParameter("id");
		String type=request.getParameter("type");
		if(null==type || "".equals(type.trim())){
			type="1";
		}else{
			type=type.trim();
		}
		String fileName ="未能找到相关文件"; 
		PrintTemplet aPrintTemplet=printTempletService.showPrintTemplet(id);
		String realpath= "";
		File theFile=null;
		if(null!=aPrintTemplet && "1".equals(aPrintTemplet.getIssystem())){
			if("1".equals(type)){
				if(StringUtils.isNotEmpty(aPrintTemplet.getSourcepath())){
					realpath=servletContext.getRealPath(aPrintTemplet.getSourcepath());
					theFile=new File(realpath);
					fileName=aPrintTemplet.getSourcefile();
				}
			}else if("2".equals(type)){
				if(StringUtils.isNotEmpty(aPrintTemplet.getTempletpath())){
					realpath=servletContext.getRealPath(aPrintTemplet.getTempletpath());
					theFile=new File(realpath);
					fileName=aPrintTemplet.getTempletfile();
				}
			}
		}
		InputStream inputStream=null;
		if(null!=theFile){
			inputStream = new FileInputStream(theFile);
		}else{
			inputStream = new ByteArrayInputStream("未能找到相关文件".getBytes());
		}
		int len = -1;
		byte[] b = new byte[1024];
		response.addHeader("Content-Length", "" + theFile.length());
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		String userAgent = request.getHeader("User-Agent");
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			//非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
		OutputStream outputStream = response.getOutputStream();
		while((len = inputStream.read(b)) != -1){
			outputStream.write(b, 0, len);
		}
		outputStream.flush();
		inputStream.close();
		outputStream.close();
	}
	
	public String showPrintTempletForSelect() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		map.put("state", "1");
		map.put("showDataAuthSql","1");
		List list=printTempletService.getPrintTempletListBy(map);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 复制打印模板数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2014-10-28
	 */
	public String copyPrintTemplet() throws Exception{
		String id = request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			addJSONObject("flag", false);
			addJSONObject("msg", "未找到要复制的数据");
			return SUCCESS;
		}
		Map resultMap=printTempletService.addCopyPrintTemplet(id.trim());
		if(null!=resultMap){
			boolean flag=(Boolean)resultMap.get("flag");
			addLog("复制编号为"+id+"打印模板数据", flag);
		}else{
			addLog("复制编号为"+id+"打印模板数据");
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
}

