package com.hd.agent.agprint.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import com.hd.agent.agprint.model.PrintPaperSize;
import com.hd.agent.agprint.model.PrintTempletSubject;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.agprint.model.PrintTempletResource;
import com.hd.agent.agprint.service.IPrintTempletResourceService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;

public class PrintTempletResourceAction extends BaseAction{
	private PrintTempletResource printTempletResource;
	private IPrintTempletResourceService printTempletResourceService;
	public PrintTempletResource getPrintTempletResource() {
		return printTempletResource;
	}
	public void setPrintTempletResource(PrintTempletResource printTempletResource) {
		this.printTempletResource = printTempletResource;
	}
	public IPrintTempletResourceService getPrintTempletResourceService() {
		return printTempletResourceService;
	}
	public void setPrintTempletResourceService(IPrintTempletResourceService printTempletResourceService) {
		this.printTempletResourceService = printTempletResourceService;
	}
	
	
	public String showPrintTempletResourceListPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板资源列表数据，返回Json格式数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletResourcePageListData() throws Exception{
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=printTempletResourceService.showPrintTempletResourcePageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板资源详情
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletResourceInfo() throws Exception{
		String id=request.getParameter("id");
		PrintTempletResource printTempletResource=printTempletResourceService.showPrintTempletResourceInfo(id);
		request.setAttribute("printTempletResource", printTempletResource);
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板资源添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletResourceAddPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 打印模板资源添加方法
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PrintTempletResource",type=2)
	public String addPrintTempletResource() throws Exception{
		printTempletResource.setIssystem("0"); //手动添加
		Map resultMap=printTempletResourceService.addPrintTempletResource(printTempletResource);
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
		
		addLog("添加打印模板资源 编号分类:"+printTempletResource.getCode(), flag);
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板资源修改页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletResourceEditPage() throws Exception{
		String id=request.getParameter("id");
		
		PrintTempletResource aPrintTempletResource=printTempletResourceService.showPrintTempletResourceInfo(id);
		if(null==aPrintTempletResource){
			return "viewSuccess";
		}
		if(null!=aPrintTempletResource){

			StringBuilder sb=null;

			PrintTempletSubject printTempletSubject=aPrintTempletResource.getPrintTempletSubjectInfo();
			if(null!=printTempletSubject){
				sb=new StringBuilder();
				sb.append(printTempletSubject.getName());
				if(!"1".equals(printTempletSubject.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("codename", sb.toString());
			}
			PrintPaperSize printPaperSize=aPrintTempletResource.getPrintPaperSizeInfo();
			if(null!=printPaperSize ){
				sb=new StringBuilder();
				sb.append(printPaperSize.getName());
				if(!"1".equals(printPaperSize.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("papersizename", sb.toString());
			}
		}
		request.setAttribute("printTempletResource", aPrintTempletResource);
		return SUCCESS;
	}
	
	/**
	 * 修改打印模板资源方法
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PrintTempletResource",type=3)
	public String editPrintTempletResource() throws Exception{
		Map resultMap=printTempletResourceService.editPrintTempletResource(printTempletResource);
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
		
		addLog("修改打印模板资源 编号分类:"+printTempletResource.getCode(), flag);
		return SUCCESS;
	}
	
	/**
	 * 显示打印模板资源查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	public String showPrintTempletResourceViewPage() throws Exception{
		String id=request.getParameter("id");
		
		PrintTempletResource aPrintTempletResource=printTempletResourceService.showPrintTempletResourceInfo(id);

		if(null!=aPrintTempletResource){

			StringBuilder sb=null;

			PrintTempletSubject printTempletSubject=aPrintTempletResource.getPrintTempletSubjectInfo();
			if(null!=printTempletSubject){
				sb=new StringBuilder();
				sb.append(printTempletSubject.getName());
				if(!"1".equals(printTempletSubject.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("codename", sb.toString());
			}
			PrintPaperSize printPaperSize=aPrintTempletResource.getPrintPaperSizeInfo();
			if(null!=printPaperSize ){
				sb=new StringBuilder();
				sb.append(printPaperSize.getName());
				if(!"1".equals(printPaperSize.getState())){
					sb.append("(未启用)");
				}
				request.setAttribute("papersizename", sb.toString());
			}
		}
		request.setAttribute("printTempletResource", aPrintTempletResource);
		return SUCCESS;
	}
	
	/**
	 * 禁用打印模板资源
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PrintTempletResource",type=3)
	public String disablePrintTempletResource() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=printTempletResourceService.disablePrintTempletResource(id);
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
		
		addLog("禁用打印模板资源编号分类:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 启用打印模板资源
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="PrintTempletResource",type=3)
	public String enablePrintTempletResource() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=printTempletResourceService.enablePrintTempletResource(id);
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
		
		addLog("启用打印模板资源 编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 删除打印模板资源
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015-11-30
	 */
	@UserOperateLog(key="PrintTempletResource",type=4,value="")
	public String deletePrintTempletResource()throws Exception{
		String id=request.getParameter("id");
		Map resultMap= printTempletResourceService.deletePrintTempletResource(id);
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
		addLog("删除打印模板资源 编号:"+id, flag);
		return SUCCESS;
	}
	/**
	 * 批量删除打印模板资源
	 * @return
	 * @throws Exception
	 * @author zhang_hh 
	 * @date 2013-3-27
	 */
	@UserOperateLog(key="PrintTempletResource",type=4,value="")
	public String deletePrintTempletResourceMore()throws Exception{
		String idarrs = request.getParameter("idarrs");
		Map map= printTempletResourceService.deletePrintTempletResourceMore(idarrs);
		Boolean flag=false;
		if(null!=map){
			flag=(Boolean)map.get("flag");
			if(null==flag){
				flag=false;
			}
			addLog("批量删除打印模板资源 编号:"+idarrs,flag);
		}else{
			addLog("批量删除打印模板资源 编号失败:"+idarrs);
		}
		addJSONObject(map);
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
		PrintTempletResource aPrintTempletResource=printTempletResourceService.showPurePrintTempletResource(id);
		String realpath= "";
		File theFile=null;
		if(null!=aPrintTempletResource && "1".equals(aPrintTempletResource.getIssystem())){
			if("1".equals(type)){
				if(StringUtils.isNotEmpty(aPrintTempletResource.getSourcepath())){
					realpath=servletContext.getRealPath(aPrintTempletResource.getSourcepath());
					theFile=new File(realpath);
					fileName=aPrintTempletResource.getSourcefile();
				}
			}else if("2".equals(type)){
				if(StringUtils.isNotEmpty(aPrintTempletResource.getTempletpath())){
					realpath=servletContext.getRealPath(aPrintTempletResource.getTempletpath());
					theFile=new File(realpath);
					fileName=aPrintTempletResource.getTempletfile();
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
}
