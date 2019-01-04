package com.hd.agent.agprint.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.agprint.model.PrintOrderSeq;
import com.hd.agent.agprint.service.IPrintOrderSeqService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
/**
 * 打印内容顺序策略Action
 * @author master
 *
 */
public class PrintOrderSeqAction extends BaseAction {
	/**
	 * 打印顺序策略
	 */
	private PrintOrderSeq printOrderSeq;

	public PrintOrderSeq getPrintOrderSeq() {
		return printOrderSeq;
	}

	public void setPrintOrderSeq(PrintOrderSeq printOrderSeq) {
		this.printOrderSeq = printOrderSeq;
	}
	/**
	 * 打印顺序策略服务层
	 */
	private IPrintOrderSeqService printOrderSeqService;

	public IPrintOrderSeqService getPrintOrderSeqService() {
		return printOrderSeqService;
	}

	public void setPrintOrderSeqService(IPrintOrderSeqService printOrderSeqService) {
		this.printOrderSeqService = printOrderSeqService;
	}
	/**
	 * 显示打印内容顺序策略列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public String showPrintOrderSeqListPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 打印内容顺序策略
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public String showPrintOrderSeqPageListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());

		pageMap.setCondition(map);
		
		PageData pageData=printOrderSeqService.showPrintOrderSeqPageListData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 显示打印内容顺序策略页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public String showPrintOrderSeqPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 显示打印内容顺序策略添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public String showPrintOrderSeqAddPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 添加打印内容顺序策略
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	@UserOperateLog(key="PrintOrderSeq",type=2)
	public String addPrintOrderSeq() throws Exception{
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(printOrderSeq.getName())){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写排序策略名称");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(printOrderSeq.getOrderseq())){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写排序策略中的排序规则");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		resultMap=printOrderSeqService.addPrintOrderSeq(printOrderSeq);
		if(null!=resultMap){
			Boolean flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			resultMap.put("flag", flag);
			addLog("添加打印内容排序策略", flag);
		}else{
			addLog("添加打印内容排序策略失败");
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 显示打印内容顺序策略编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public String showPrintOrderSeqEditPage() throws Exception{
		String id=request.getParameter("id");
		PrintOrderSeq oldPrintOrderSeq=printOrderSeqService.showPrintOrderSeqInfo(id);
		request.setAttribute("printOrderSeq", oldPrintOrderSeq);
		return SUCCESS;
	}
	/**
	 * 编辑打印内容顺序策略
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	@UserOperateLog(key="PrintOrderSeq",type=3)
	public String editPrintOrderSeq() throws Exception{
		Map resultMap=new HashMap();
		if(StringUtils.isEmpty(printOrderSeq.getName())){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写排序策略名称");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		if(StringUtils.isEmpty(printOrderSeq.getOrderseq())){
			resultMap.put("flag", false);
			resultMap.put("msg","请填写排序策略中的排序规则");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		resultMap=printOrderSeqService.updatePrintOrderSeq(printOrderSeq);
		if(null!=resultMap){
			Boolean flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			resultMap.put("flag", flag);
			addLog("修改打印内容排序策略", flag);
		}else{
			addLog("修改打印内容排序策略失败");
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/**
	 * 显示打印内容顺序策略查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public String showPrintOrderSeqViewPage() throws Exception{
		String id=request.getParameter("id");
		PrintOrderSeq oldPrintOrderSeq=printOrderSeqService.showPrintOrderSeqInfo(id);
		request.setAttribute("printOrderSeq", oldPrintOrderSeq);
		return SUCCESS;
	}
	/**
	 * 显示打印内容顺序策略生成页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	public String showPrintOrderSeqOrderCreatePage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 删除打印内容顺序策略
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	@UserOperateLog(key="PrintOrderSeq",type=4)
	public String deletePrintOrderSeq() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=printOrderSeqService.deletePrintOrderSeq(id);
		if(null!=resultMap){
			Boolean flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			resultMap.put("flag", flag);
			addLog("删除打印内容排序策略", flag);
		}else{
			addLog("删除打印内容排序策略失败");
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}

	/**
	 * 批量删除打印内容顺序策略
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年11月5日
	 */
	@UserOperateLog(key="PrintOrderSeq",type=4)
	public String deletePrintOrderSeqMore() throws Exception{
		String idarrs=request.getParameter("idarrs");
		Map resultMap=printOrderSeqService.deletePrintOrderSeq(idarrs);
		if(null!=resultMap){
			Boolean flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
			resultMap.put("flag", flag);
			addLog("删除打印内容排序策略 编号："+idarrs, flag);
		}else{
			addLog("删除打印内容排序策略失败，编号："+idarrs);
			resultMap.put("flag", false);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}
	
	/**
	 * 禁用打印内容顺序策略
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-15
	 */
	@UserOperateLog(key="PrintOrderSeq",type=3)
	public String disablePrintOrderSeq() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=printOrderSeqService.disablePrintOrderSeq(id);
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
		
		addLog("禁用打印内容顺序策略 编号:"+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 启用打印内容顺序策略
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2012-12-19
	 */
	@UserOperateLog(key="PrintOrderSeq",type=3)
	public String enablePrintOrderSeq() throws Exception{
		String id=request.getParameter("id");
		Map resultMap=printOrderSeqService.enablePrintOrderSeq(id);
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
		
		addLog("启用打印内容顺序策略 编号:"+id, flag);
		return SUCCESS;
	}
}
