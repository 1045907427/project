package com.hd.agent.agprint.action;

import com.hd.agent.agprint.model.PrintPaperSize;
import com.hd.agent.agprint.service.IPrintPaperSizeService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 打印纸张大小Action
 * @author master
 *
 */
public class PrintPaperSizeAction extends BaseAction {
	/**
	 * 打印纸张大小
	 */
	private PrintPaperSize printPaperSize;

	public PrintPaperSize getPrintPaperSize() {
		return printPaperSize;
	}

	public void setPrintPaperSize(PrintPaperSize printPaperSize) {
		this.printPaperSize = printPaperSize;
	}
	/**
	 * 打印纸张大小服务层
	 */
	private IPrintPaperSizeService printPaperSizeService;

	public IPrintPaperSizeService getPrintPaperSizeService() {
		return printPaperSizeService;
	}

	public void setPrintPaperSizeService(IPrintPaperSizeService printPaperSizeService) {
		this.printPaperSizeService = printPaperSizeService;
	}
	/**
	 * 显示打印纸张大小列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	public String showPrintPaperSizeListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 打印纸张大小
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	public String showPrintPaperSizePageListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());

		pageMap.setCondition(map);
		
		PageData pageData=printPaperSizeService.showPrintPaperSizePageListData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 显示打印纸张大小页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	public String showPrintPaperSizePage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 显示打印纸张大小添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	public String showPrintPaperSizeAddPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 添加打印纸张大小
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	@UserOperateLog(key="PrintPaperSize",type=2)
	public String addPrintPaperSize() throws Exception{
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(printPaperSize.getName())){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写排序策略名称");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(printPaperSize.getWidth()==null || printPaperSize.getWidth()==0){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写打印纸张宽度");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(printPaperSize.getHeight()==null || printPaperSize.getHeight()==0){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写打印纸张高度");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		resultMap=printPaperSizeService.addPrintPaperSize(printPaperSize);
		if(null!=resultMap){
			Boolean flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			resultMap.put("flag", flag);
			addLog("添加打印纸张大小", flag);
		}else{
			addLog("添加打印纸张大小失败");
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 显示打印纸张大小编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	public String showPrintPaperSizeEditPage() throws Exception{
		String id=request.getParameter("id");
		PrintPaperSize oldPrintPaperSize=printPaperSizeService.showPrintPaperSizeInfo(id);
		request.setAttribute("printPaperSize", oldPrintPaperSize);
		return SUCCESS;
	}
	/**
	 * 编辑打印纸张大小
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	@UserOperateLog(key="PrintPaperSize",type=3)
	public String editPrintPaperSize() throws Exception{
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(printPaperSize.getName())){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写排序策略名称");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(printPaperSize.getWidth()==null || printPaperSize.getWidth()==0){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写打印纸张宽度");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(printPaperSize.getHeight()==null || printPaperSize.getHeight()==0){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写打印纸张高度");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		resultMap=printPaperSizeService.updatePrintPaperSize(printPaperSize);
		if(null!=resultMap){
			Boolean flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			resultMap.put("flag", flag);
			addLog("修改打印纸张大小", flag);
		}else{
			addLog("修改打印纸张大小失败");
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 显示打印纸张大小查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	public String showPrintPaperSizeViewPage() throws Exception{
		String id=request.getParameter("id");
		PrintPaperSize oldPrintPaperSize=printPaperSizeService.showPrintPaperSizeInfo(id);
		request.setAttribute("printPaperSize", oldPrintPaperSize);
		return SUCCESS;
	}
	/**
	 * 显示打印纸张大小生成页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	public String showPrintPaperSizeOrderCreatePage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 删除打印纸张大小
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	@UserOperateLog(key="PrintPaperSize",type=4)
	public String deletePrintPaperSize() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=printPaperSizeService.deletePrintPaperSize(id);
		if(null!=resultMap){
			Boolean flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			resultMap.put("flag", flag);
			addLog("删除打印纸张大小", flag);
		}else{
			addLog("删除打印纸张大小失败");
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}

	/**
	 * 批量删除打印纸张大小
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Sep 11, 2016
	 */
	@UserOperateLog(key="PrintPaperSize",type=4)
	public String deletePrintPaperSizeMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map resultMap=printPaperSizeService.deletePrintPaperSize(idarrs);
		if(null!=resultMap){
			Boolean flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			resultMap.put("flag", flag);
			addLog("删除打印纸张大小 编号："+idarrs, flag);
		}else{
			addLog("删除打印纸张大小失败，编号："+idarrs);
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	
	/**
	 * 禁用打印纸张大小
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PrintPaperSize",type=3)
	public String disablePrintPaperSize() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=printPaperSizeService.disablePrintPaperSize(id);
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
		
		addLog("禁用打印纸张大小 编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 启用打印纸张大小
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="PrintPaperSize",type=3)
	public String enablePrintPaperSize() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=printPaperSizeService.enablePrintPaperSize(id);
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
		
		addLog("启用打印纸张大小 编号:"+id, flag);
		return SUCCESS;
	}
}
