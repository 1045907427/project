/**
 * @(#)AccountPrintAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 17, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.agprint.action.account;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.ReceiptHand;
import com.hd.agent.account.model.ReceiptHandBill;
import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.service.IReceiptHandService;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * 回单交接单打印模块
 * 
 * @author panxiaoxiao
 */
public class ReceiptHandPrintAction extends BaseFilesAction{
	//region 参数初始化
	private static final Logger logger = Logger.getLogger(ReceiptHandPrintAction.class);
	private IReceiptHandService receiptHandService;
	
	public IReceiptHandService getReceiptHandService() {
		return receiptHandService;
	}

	public void setReceiptHandService(IReceiptHandService receiptHandService) {
		this.receiptHandService = receiptHandService;
	}

	private AgprintServiceImpl agprintServiceImpl;

	public AgprintServiceImpl getAgprintServiceImpl() {
		return agprintServiceImpl;
	}

	public void setAgprintServiceImpl(AgprintServiceImpl agprintServiceImpl) {
		this.agprintServiceImpl = agprintServiceImpl;
	}
	//endregion 参数初始化
	
	/*------------------------回单交接单打印-------------------------------*/
	/**
	 * 回单交接单打印预览
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 17, 2014
	 */
	@UserOperateLog(key = "PrintView", type = 0)
	public String receiptHandPrintView()throws Exception{
		return receiptHandPrintHandle(true);
	}
	
	/**
	 * 回单交接单打印
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 18, 2014
	 */
	@UserOperateLog(key = "Print", type = 0)
	public String receiptHandPrint()throws Exception{
		String justprint = request.getParameter("justprint");
		String printcallback = request.getParameter("agprint_callback_params");
		if (null != printcallback && !"".equals(printcallback)) {
			receiptHandCallBackHandle(printcallback);
		} else {
			boolean isview = false;
			if ("1".equals(justprint) || "true".equals(justprint)) {
				isview = false;
			} else {
				isview = true;
			}
			return receiptHandPrintHandle(isview);
		}
		return null;
	}
	
	/**
	 * 回单交接单打印，打印预览处理
	 * @param isview
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 17, 2014
	 */
	private String receiptHandPrintHandle(boolean isview)throws Exception{
		String viewtype=request.getParameter("viewtype");
		if(null==viewtype){
			viewtype="pdf";
		}
		viewtype=viewtype.trim();
		//返回的格式：
		//{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
		//ajax数据
		Map ajaxResultMap=new HashMap();
		ajaxResultMap.put("printname", "回单交接单打印-"+CommonUtils.getDataNumberSeconds());
		String id=request.getParameter("id");
		if(null ==id || "".equals(id.trim())){
			if("ajaxhtml".equals(viewtype)){
				ajaxResultMap.put("flag", false);
				ajaxResultMap.put("msg", "未能找到相关打印单据");
				addJSONObject(ajaxResultMap);
				return SUCCESS;
			}else{
				return null;
			}
		}
		String templetid=request.getParameter("templetid");
		SysUser sysUser = getSysUser();
		Date printDate = new Date();
		String canprintIds = "";
		Map printTempletSettingFistMap=null;
		try {
			//打印任务信息
			PrintJob printJob=new PrintJob();
			if("ajaxhtml".equals(viewtype)){
				printJob.setAddtime(new Date());
				printJob.setAdduserid(sysUser.getUserid());
				printJob.setAddusername(sysUser.getName());
				printJob.setIp( CommonUtils.getIP(request));
				printJob.setJobname("采购发票打印");
				printJob.setOrderidarr(id);
				if(request.getQueryString()!=null){
					printJob.setRequestparam(request.getQueryString());
				}

            if(isview){
                //3打印预览
                printJob.setStatus("3");
             }else{
                //0申请
                 printJob.setStatus("0");
             }
				//打印对象添加到数据库
				boolean isjobflag=agprintServiceImpl.addPrintJob(printJob);
				if(!isjobflag || StringUtils.isEmpty(printJob.getId())){
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "申请单据打印时失败");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				}
				ajaxResultMap.put("printJobId", printJob.getId());
				ajaxResultMap.put("printname", "采购发票打印任务-"+printJob.getId());
			}
			Map map = new HashMap();
			if(!isview){
				String printlimit = getPrintLimitInfo();
				map.put("notphprint", printlimit);
			}
			map.put("idarrs", id);
			map.put("showdetail", "1");
			Map parameters = null;
			ReceiptHand receiptHand = receiptHandService.getReceiptHandInfo(id);
			if(null == receiptHand){
				if("ajaxhtml".equals(viewtype)){
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "未能找到相关打印单据");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				}else{
					return null;
				}
			}
			if(!"3".equals(receiptHand.getStatus())){
				if("ajaxhtml".equals(viewtype)){
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "非审核通过状态单据不能打印");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				}else{
					return null;
				}
			}
			
			Map receiptHandMap = receiptHandService.getReceiptHandCustomerDetailList(id);
			if(!receiptHandMap.isEmpty()){
				BigDecimal totalamount = BigDecimal.ZERO;
				List<Map<String,Object>> list = (List<Map<String,Object>>)receiptHandMap.get("list");
				if(null != list && list.size() != 0){
					for(Map<String,Object> map2 : list){
						BigDecimal collectionamount = null != map2.get("collectionamount") ? (BigDecimal)map2.get("collectionamount") : BigDecimal.ZERO;
						totalamount = totalamount.add(collectionamount);
					}
				}else{
					if("ajaxhtml".equals(viewtype)){
						ajaxResultMap.put("flag", false);
						ajaxResultMap.put("msg", "未能找到相关打印单据数据");
						addJSONObject(ajaxResultMap);
						return SUCCESS;
					}else{
						return null;
					}
				}
				//插入空白行
//				list.add(new HashMap());
//				list.add(new HashMap());
//				list.add(new HashMap());
				//获取打印模板
				Map templetQueryMap=new HashMap();
				templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
				templetQueryMap.put("code", "account_receipthand");

				if(null!=templetid && !"".equals(templetid)){
					templetQueryMap.put("templetid", templetid);
				}
				Map templetResultMap=agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);

				//打印模板文件
				String printTempletFile=(String)templetResultMap.get("printTempletFile");
				if(null==printTempletFile || "".equals(printTempletFile.trim())){
					if("ajaxhtml".equals(viewtype)){
						ajaxResultMap.put("flag", false);
						ajaxResultMap.put("msg", "未能找到打印模板");
						addJSONObject(ajaxResultMap);
						return SUCCESS;
					}else{
						return null;
					}
				}
				//第一个可用的打印模板配置信息
				if(printTempletSettingFistMap==null || printTempletSettingFistMap.size()==0){
					printTempletSettingFistMap=new HashMap();
					printTempletSettingFistMap.putAll(templetResultMap);
				}
				JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
				if(null==jreport){
					if("ajaxhtml".equals(viewtype)){
						ajaxResultMap.put("flag", false);
						ajaxResultMap.put("msg", "未能找到打印模板");
						addJSONObject(ajaxResultMap);
						return SUCCESS;
					}else{
						return null;
					}
				}
				PrintTemplet printTemplet = new PrintTemplet();
				if (templetResultMap.containsKey("printTempletInfo")) {
					printTemplet = (PrintTemplet) templetResultMap.get("printTempletInfo");
					if (null == printTemplet) {
						printTemplet = new PrintTemplet();
						templetResultMap.put("printTempletInfo", printTemplet);
					}
				}
				//打印内容排序策略
				String printDataOrderSeq =(String)templetResultMap.get("printDataOrderSeq");
				//进行打印内容明细排序
				if(null!=printDataOrderSeq && !"".equals(printDataOrderSeq.trim())){
					Collections.sort(list, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
				}
				List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
				List<String> printIdList = new ArrayList<String>();
				if (!isview) {
					receiptHand.setPrinttimes(receiptHand.getPrinttimes() + 1);
				}
				
				parameters = new HashMap();
				
				parameters.put("P_BillRemark", receiptHand.getRemark());
				
				parameters.put("P_Title", "应收款账单交接凭证");
				parameters.put("P_ReceipthandID", id);
				parameters.put("P_Addusername", receiptHand.getAddusername());
				Personnel personnel = getPersonnelInfoById(receiptHand.getHanduserid());
				if(null != personnel){
					parameters.put("P_Handusername", personnel.getName());
				}
				parameters.put("P_TotalCnum", "共：" + receiptHand.getCnums() + "  家");
				parameters.put("P_TotalAmount", totalamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				//公共参数
				parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
				agprintServiceImpl.setTempletCommonParameter(parameters);
				JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(list));
				if (null != jrprint) {
					if (!isview) {
						Map<String, Object> callbackparamMap = new HashMap<String, Object>();
						callbackparamMap.put("id", receiptHand.getId());
						callbackparamMap.put("callback", "updateprinttimes");
						callbackparamMap.put("rand", CommonUtils.getRandomWithTime()); // 随机数
						String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
						jrprint.setProperty("agprint_callback_params", callbackparams);

						receiptHand.setPrinttimes(null);
						printIdList.add(receiptHand.getId());


						if(StringUtils.isNotEmpty(printJob.getId())){
							//打印次数更新回调方法
							Map<String,Object> datahtmlparamMap=new HashMap<String, Object>();
							//ajaxhtml 打印时，前台加入到打印时，出错提示用。
							//实际打印的单据号
							datahtmlparamMap.put("printOrderId", receiptHand.getId());
							//打印任务编号
							datahtmlparamMap.put("printJobId", printJob.getId());
							//页面点击时的单据号
							datahtmlparamMap.put("printSourceOrderId", "");
							String datahtmlparams= JSONUtils.mapToJsonStr(datahtmlparamMap);
							jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
							//打印回调处理
							PrintJobCallHandle printJobCallHandle =new PrintJobCallHandle();
							printJobCallHandle.setJobid(printJob.getId());
							printJobCallHandle.setClassname("receiptHandPrintAction");
							printJobCallHandle.setMethodname("receiptHandReflectCallBackHandle");
							printJobCallHandle.setMethodparam(callbackparams);
							printJobCallHandle.setPages(jrprint.getPages().size());
							printJobCallHandle.setPrintorderid(receiptHand.getId());
							printJobCallHandle.setPrintordername("回单交接单");
							printJobCallHandle.setSourceorderid(receiptHand.getId());
							printJobCallHandle.setSourceordername("回单交接单");
							printJobCallHandle.setStatus("0");
							printJobCallHandle.setType("1");//次数更新
							agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
						}
					}
					jrprint.setName("回单交接单-" + receiptHand.getId());
					jrlist.add(jrprint);

				}
				if(null ==jrlist || jrlist.size()==0){
					if("ajaxhtml".equals(viewtype)){
						ajaxResultMap.put("flag", false);
						ajaxResultMap.put("msg", "抱歉，未能找到相关打印单据");
						addJSONObject(ajaxResultMap);
						return SUCCESS;
					}else{
						return null;
					}
				}

				if (!isview) {

					if (printIdList.size() > 0) {
						String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
						canprintIds = StringUtils.join(printarr, ",");
					}
					StringBuilder printLogsb = new StringBuilder();
					printLogsb.append("打印申请：");
					// printLogsb.append("申请打印仓库发货单编号："+idarrs);
					// printLogsb.append(" 。");
					printLogsb.append("交接单编号： " + canprintIds);
					printLogsb.append(" 。");
					printLogsb.append(" 操作名称：在回单交接单打印");
					printLogsb.append(" 。");
					addPrintLogInfo("PrintHandle-receiptHand", printLogsb.toString(), null);
				}
				//输出打印报表
				if("ajaxhtml".equals(viewtype)) {
					List<Map<String, Object>> renderResult=JasperReportUtils.renderJasperReportForAjax(jrlist, isview, viewtype, printJob.getId());
					boolean resultFlag=false;
					if(null!=renderResult && renderResult.size()>0){
						resultFlag=true;
					}
					ajaxResultMap.put("flag", resultFlag);
					ajaxResultMap.put("printdata", renderResult);
					ajaxResultMap.put("msg", "获取数据成功");
					if(printTempletSettingFistMap!=null) {
						ajaxResultMap.put("showpagesize", true);
						ajaxResultMap.put("paperwidth", printTempletSettingFistMap.get("paperwidth"));
						ajaxResultMap.put("paperheight", printTempletSettingFistMap.get("paperheight"));
						ajaxResultMap.put("paperintwidth", printTempletSettingFistMap.get("paperintwidth"));
						ajaxResultMap.put("paperintheight", printTempletSettingFistMap.get("paperintheight"));
						ajaxResultMap.put("papersizename", printTempletSettingFistMap.get("papersizename"));
						ajaxResultMap.put("lodophtmlmodel", printTempletSettingFistMap.get("lodophtmlmodel"));
					}
					addJSONObject(ajaxResultMap);

					jrlist=null;

					return SUCCESS;
				}else {
					Map<String, Object> reportMap = new HashMap<String, Object>();
					reportMap.put("response", response);
					reportMap.put("jreportList", jrlist);
					reportMap.put("isview", isview);
					reportMap.put("viewtype", viewtype);
					reportMap.put("PDFOWNERPASSWORD", getSysParamValue("PDFOWNERPASSWORD"));
					JasperReportUtils.renderJasperReport(reportMap);
				}
				jrlist = null;
			}
		} catch (Exception ex) {
			StringBuilder printLogsb = new StringBuilder();
			if (!isview) {
				printLogsb.append("打印申请-在回单交接单打印及预览异常：");
				// printLogsb.append("申请打印仓库发货单编号："+idarrs);
				// printLogsb.append(" 。");
				printLogsb.append("回单交接单编号： " + canprintIds);
				printLogsb.append(" 。");
			} else {
				printLogsb.append("打印预览申请-在回单交接单打印预览异常：");
				// printLogsb.append("申请预览仓库发货单编号："+idarrs);
				printLogsb.append(" 。");
			}
			printLogsb.append("Exception ：receiptHandPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
			addPrintLogInfo("PrintHandle-receiptHand", printLogsb.toString(), null);
			logStr = printLogsb.toString();

			logger.error("打印申请-在回单交接单打印及预览异常", ex);
		}
		if("ajaxhtml".equals(viewtype)){
			return SUCCESS;
		}else{
			return null;
		}
	}
	
	/**
	 * 交接单打印回调处理
	 * @param printcallback
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 18, 2014
	 */
	private void receiptHandCallBackHandle(String printcallback)throws Exception{
		String idarrs = request.getParameter("idarrs");
		if (null == idarrs) {
			idarrs = "";
		}
		StringBuilder printLogsb = new StringBuilder();
		// printLogsb.append("更新打印次数：");
		if (null == printcallback || "".equals(printcallback)) {
			printLogsb.append("更新打印次数失败：回调参数为空");
			printLogsb.append(" 。");
			addPrintLogInfo("PrintCallHandle-receiptHand", printLogsb.toString(), null);
			response.reset();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("failure");
			return;
		}

		Map map = JSONUtils.jsonStrToMap(printcallback);
		if (null == map) {
			printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
			printLogsb.append(" 。");
			addPrintLogInfo("PrintCallHandle-receiptHand", printLogsb.toString(), null);
			response.reset();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("failure");
			return;
		}
		String id = (String) map.get("id");
		if (null != id && !"".equals(id.trim())) {
			printLogsb.append(" 。");
			printLogsb.append("回单交接单编号： " + id);

			try {
				boolean flag = receiptHandService.updatePrintTimes(id);
				if(flag){
					printLogsb.append(" 更新回单交接单打印次数成功");
				}else{
					printLogsb.append(" 更新回单交接单打印次数失败");
				}
			} catch (Exception ex) {
				printLogsb.append(" 更新回单交接单打印次数失败");
				printLogsb.append(" 。");
				printLogsb.append(" 异常信息：" + ex.getMessage());
				logger.error("回单交接单更新打印次数异常",ex);
			}
		} else {
			printLogsb.append(" 。");
			printLogsb.append("更新打印次数失败：回调参数中无回单交接单编号信息");
		}
		printLogsb.append(" 。");

		printLogsb.append(" 更新响应来源于：在回单交接单-交接单。");
		printLogsb.append(" 打印数据来源(回单交接单编号)：");
		printLogsb.append(id);
		printLogsb.append(" 。");
		addPrintLogInfo("PrintCallHandle-receiptHand", printLogsb.toString(), null);
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("ok");
	}


	/**
	 * 交接单打印回调处理
	 * @param printcallback
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Dec 18, 2014
	 */
	public boolean receiptHandReflectCallBackHandle(String printcallback)throws Exception{
		boolean isok=false;
		StringBuilder printLogsb = new StringBuilder();
		// printLogsb.append("更新打印次数：");
		if (null == printcallback || "".equals(printcallback)) {
			printLogsb.append("更新打印次数失败：回调参数为空");
			printLogsb.append(" 。");
			addPrintLogInfo("PrintCallHandle-receiptHand", printLogsb.toString(), null);
			return isok;
		}

		Map map = JSONUtils.jsonStrToMap(printcallback);
		if (null == map) {
			printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
			printLogsb.append(" 。");
			addPrintLogInfo("PrintCallHandle-receiptHand", printLogsb.toString(), null);
			return isok;
		}
		String id = (String) map.get("id");
		if (null != id && !"".equals(id.trim())) {
			printLogsb.append(" 。");
			printLogsb.append("回单交接单编号： " + id);

			try {
				boolean flag = receiptHandService.updatePrintTimes(id);
				if(flag){
					printLogsb.append(" 更新回单交接单打印次数成功");
					isok=true;
				}else{
					printLogsb.append(" 更新回单交接单打印次数失败");
				}
			} catch (Exception ex) {
				printLogsb.append(" 更新回单交接单打印次数失败");
				printLogsb.append(" 。");
				printLogsb.append(" 异常信息：" + ex.getMessage());
				logger.error("回单交接单更新打印次数异常",ex);
				isok=false;
			}
		} else {
			printLogsb.append(" 。");
			printLogsb.append("更新打印次数失败：回调参数中无回单交接单编号信息");
		}
		printLogsb.append(" 。");

		printLogsb.append(" 更新响应来源于：在回单交接单-交接单。");
		printLogsb.append(" 打印数据来源(回单交接单编号)：");
		printLogsb.append(id);
		printLogsb.append(" 。");
		addPrintLogInfo("PrintCallHandle-receiptHand", printLogsb.toString(), null);
		return isok;
	}


	/**
	 * 回单交接单明细打印预览
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年6月1日
	 */
	@UserOperateLog(key = "PrintView", type = 0)
	public String receiptHandDetailPrintView()throws Exception{
		return receiptHandDetailPrintHandle(true);
	}
	
	/**
	 * 回单交接单明细打印
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 18, 2014
	 */
	@UserOperateLog(key = "Print", type = 0)
	public String receiptHandDetailPrint()throws Exception{
		String justprint = request.getParameter("justprint");
		String printcallback=request.getParameter("agprint_callback_params");
		if(null!=printcallback && !"".equals(printcallback)) {

		}else{
			boolean isview = false;
			if ("1".equals(justprint) || "true".equals(justprint)) {
				isview = false;
			} else {
				isview = true;
			}
			return receiptHandDetailPrintHandle(isview);
		}
		return null;
	}
	
	/**
	 * 回单交接单明细打印，打印预览处理
	 * @param isview
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年6月1日
	 */
	private String receiptHandDetailPrintHandle(boolean isview)throws Exception{
		String viewtype=request.getParameter("viewtype");
		if(null==viewtype){
			viewtype="pdf";
		}
		viewtype=viewtype.trim();
		//返回的格式：
		//{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
		//ajax数据
		Map ajaxResultMap=new HashMap();
		ajaxResultMap.put("printname", "回单交接单明细印-"+CommonUtils.getDataNumberSeconds());
		String id=request.getParameter("id");
		if(null ==id || "".equals(id.trim())){
			if("ajaxhtml".equals(viewtype)){
				ajaxResultMap.put("flag", false);
				ajaxResultMap.put("msg", "未能找到相关打印单据");
				addJSONObject(ajaxResultMap);
				return SUCCESS;
			}else{
				return null;
			}
		}
		String templetid=request.getParameter("templetid");
		Map printTempletSettingFistMap=null;

		SysUser sysUser = getSysUser();
		Date printDate = new Date();
		String canprintIds = "";
		try {
			//打印任务信息
			PrintJob printJob=new PrintJob();
			if("ajaxhtml".equals(viewtype)){
				printJob.setAddtime(new Date());
				printJob.setAdduserid(sysUser.getUserid());
				printJob.setAddusername(sysUser.getName());
				printJob.setIp( CommonUtils.getIP(request));
				printJob.setJobname("回单交接单明细打印");
				printJob.setOrderidarr(id);
				if(request.getQueryString()!=null){
					printJob.setRequestparam(request.getQueryString());
				}

            if(isview){
                //3打印预览
                printJob.setStatus("3");
             }else{
                //0申请
                 printJob.setStatus("0");
             }
				//打印对象添加到数据库
				boolean isjobflag=agprintServiceImpl.addPrintJob(printJob);
				if(!isjobflag || StringUtils.isEmpty(printJob.getId())){
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "申请单据打印时失败");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				}
				ajaxResultMap.put("printJobId", printJob.getId());
				ajaxResultMap.put("printname", "回单交接单明细打印任务-"+printJob.getId());
			}
			Map map = new HashMap();
			if(!isview){
				String printlimit = getPrintLimitInfo();
				map.put("notphprint", printlimit);
			}
			map.put("idarrs", id);
			map.put("showdetail", "1");
			Map parameters = null;
			ReceiptHand receiptHand = receiptHandService.getReceiptHandInfo(id);
			if(null == receiptHand){
				if("ajaxhtml".equals(viewtype)){
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "未能找到相关打印单据");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				}else{
					return null;
				}
			}
			if(!"3".equals(receiptHand.getStatus())){
				if("ajaxhtml".equals(viewtype)){
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "非审核通过状态单据不能打印");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				}else{
					return null;
				}
			}
			ReceiptHand orderInfo=(ReceiptHand)CommonUtils.deepCopy(receiptHand);
			orderInfo.setBilllist(null);
			
			List<ReceiptHandBill> list = receiptHandService.getReceiptHandBillListForPrint(id);
			if(null != list && list.size() != 0){
				BigDecimal totalamount = BigDecimal.ZERO;
				for(ReceiptHandBill receiptHandBill : list){
					BigDecimal amount = null != receiptHandBill.getAmount() ? receiptHandBill.getAmount() : BigDecimal.ZERO;
					totalamount = totalamount.add(amount);
				}
				//插入空白行
//				list.add(new HashMap());
//				list.add(new HashMap());
//				list.add(new HashMap());
				//获取打印模板
				Map templetQueryMap=new HashMap();
				templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
				templetQueryMap.put("code", "account_receipthanddetail");

				if(null!=templetid && !"".equals(templetid)){
					templetQueryMap.put("templetid", templetid);
				}
				Map templetResultMap=agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);

				//打印模板文件
				String printTempletFile=(String)templetResultMap.get("printTempletFile");
				if(null==printTempletFile || "".equals(printTempletFile.trim())){
					if("ajaxhtml".equals(viewtype)){
						ajaxResultMap.put("flag", false);
						ajaxResultMap.put("msg", "未能找到打印模板");
						addJSONObject(ajaxResultMap);
						return SUCCESS;
					}else{
						return null;
					}
				}
				//第一个可用的打印模板配置信息
				if(printTempletSettingFistMap==null || printTempletSettingFistMap.size()==0){
					printTempletSettingFistMap=new HashMap();
					printTempletSettingFistMap.putAll(templetResultMap);
				}
				JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
				//打印内容排序策略
				String printDataOrderSeq =(String)templetResultMap.get("printDataOrderSeq");
				if(null==jreport){
					if("ajaxhtml".equals(viewtype)){
						ajaxResultMap.put("flag", false);
						ajaxResultMap.put("msg", "未能找到打印模板");
						addJSONObject(ajaxResultMap);
						return SUCCESS;
					}else{
						return null;
					}
				}
				//进行打印内容明细排序
				if(null!=printDataOrderSeq && !"".equals(printDataOrderSeq.trim())){
					Collections.sort(list, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
				}
				List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
				List<String> printIdList = new ArrayList<String>();
				
				parameters = new HashMap();
				parameters.put("P_Title", "应收款账单明细交接凭证");
				parameters.put("P_ReceipthandID", id);
				parameters.put("P_Addusername", receiptHand.getAddusername());
				Personnel personnel = getPersonnelInfoById(receiptHand.getHanduserid());
				if(null != personnel){
					parameters.put("P_Handusername", personnel.getName());
				}
				parameters.put("P_TotalCnum", "共：" + receiptHand.getCnums() + "  家");
				parameters.put("P_TotalAmount", totalamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				parameters.put("P_OrderInfo", orderInfo);

				JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(list));
				if (null != jrprint) {
					if (!isview) {
						Map<String, Object> callbackparamMap = new HashMap<String, Object>();
						callbackparamMap.put("id", receiptHand.getId());
						//callbackparamMap.put("callback", "updateprinttimes");
						callbackparamMap.put("rand", CommonUtils.getRandomWithTime()); // 随机数
						String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
						jrprint.setProperty("agprint_callback_params", callbackparams);

						receiptHand.setPrinttimes(null);
						printIdList.add(receiptHand.getId());

						if(StringUtils.isNotEmpty(printJob.getId())){
							//打印次数更新回调方法
							Map<String,Object> datahtmlparamMap=new HashMap<String, Object>();
							//ajaxhtml 打印时，前台加入到打印时，出错提示用。
							//实际打印的单据号
							datahtmlparamMap.put("printOrderId", receiptHand.getId());
							//打印任务编号
							datahtmlparamMap.put("printJobId", printJob.getId());
							//页面点击时的单据号
							datahtmlparamMap.put("printSourceOrderId", "");
							String datahtmlparams= JSONUtils.mapToJsonStr(datahtmlparamMap);
							jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
							//打印回调处理
							PrintJobCallHandle printJobCallHandle =new PrintJobCallHandle();
							printJobCallHandle.setJobid(printJob.getId());
							printJobCallHandle.setClassname("receiptHandPrintAction");
							printJobCallHandle.setMethodname("purchaseInvoicePrintReflectCallBackHandle");
							printJobCallHandle.setMethodparam(callbackparams);
							printJobCallHandle.setPages(jrprint.getPages().size());
							printJobCallHandle.setPrintorderid(receiptHand.getId());
							printJobCallHandle.setPrintordername("回单交接单");
							printJobCallHandle.setSourceorderid(receiptHand.getId());
							printJobCallHandle.setSourceordername("回单交接单");
							printJobCallHandle.setStatus("0");
							printJobCallHandle.setType("1");//次数更新
							//agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
						}
					}
					jrprint.setName("回单交接单-" + receiptHand.getId());
					jrlist.add(jrprint);
				}
				if(null ==jrlist || jrlist.size()==0){
					if("ajaxhtml".equals(viewtype)){
						ajaxResultMap.put("flag", false);
						ajaxResultMap.put("msg", "抱歉，未能找到相关打印单据");
						addJSONObject(ajaxResultMap);
						return SUCCESS;
					}else{
						return null;
					}
				}


				if (!isview) {

					if (printIdList.size() > 0) {
						String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
						canprintIds = StringUtils.join(printarr, ",");
					}
					StringBuilder printLogsb = new StringBuilder();
					printLogsb.append("打印申请：");
					printLogsb.append("交接单编号： " + canprintIds);
					printLogsb.append(" 。");
					printLogsb.append(" 操作名称：在回单交接单明细打印");
					printLogsb.append(" 。");
					addPrintLogInfo("PrintHandle-receiptHandDetail", printLogsb.toString(), null);
				}			 //输出打印报表
				if("ajaxhtml".equals(viewtype)) {
					List<Map<String, Object>> renderResult=JasperReportUtils.renderJasperReportForAjax(jrlist, isview, viewtype, printJob.getId());
					boolean resultFlag=false;
					if(null!=renderResult && renderResult.size()>0){
						resultFlag=true;
					}
					ajaxResultMap.put("flag", resultFlag);
					ajaxResultMap.put("printdata", renderResult);
					ajaxResultMap.put("msg", "获取数据成功");
					if(printTempletSettingFistMap!=null) {
						ajaxResultMap.put("showpagesize", true);
						ajaxResultMap.put("paperwidth", printTempletSettingFistMap.get("paperwidth"));
						ajaxResultMap.put("paperheight", printTempletSettingFistMap.get("paperheight"));
						ajaxResultMap.put("paperintwidth", printTempletSettingFistMap.get("paperintwidth"));
						ajaxResultMap.put("paperintheight", printTempletSettingFistMap.get("paperintheight"));
						ajaxResultMap.put("papersizename", printTempletSettingFistMap.get("papersizename"));
						ajaxResultMap.put("lodophtmlmodel", printTempletSettingFistMap.get("lodophtmlmodel"));
					}
					addJSONObject(ajaxResultMap);

					jrlist=null;

					return SUCCESS;
				}else {
					// 输出打印报表
					Map<String, Object> reportMap = new HashMap<String, Object>();
					reportMap.put("response", response);
					reportMap.put("jreportList", jrlist);
					reportMap.put("isview", isview);
					reportMap.put("viewtype", viewtype);
					reportMap.put("PDFOWNERPASSWORD", getSysParamValue("PDFOWNERPASSWORD"));
					JasperReportUtils.renderJasperReport(reportMap);
				}
				jrlist = null;
			}
		} catch (Exception ex) {
			StringBuilder printLogsb = new StringBuilder();
			if (!isview) {
				printLogsb.append("打印申请-在回单交接单明细打印异常：");
				printLogsb.append("回单交接单编号： " + canprintIds);
				printLogsb.append(" 。");
			} else {
				printLogsb.append("打印预览申请-在回单交接单明细打印预览异常：");
				printLogsb.append(" 。");
			}
			printLogsb.append("Exception ：receiptHandDetailPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
			addPrintLogInfo("PrintHandle-receiptHandDetail", printLogsb.toString(), null);
			logStr = printLogsb.toString();
			logger.error("在回单交接单明细打印及预览处理异常",ex);


			if("ajaxhtml".equals(viewtype)){
				ajaxResultMap.put("flag", false);
				ajaxResultMap.put("msg", "抱歉，获取打印数据异常");
				addJSONObject(ajaxResultMap);
			}
		}
		if("ajaxhtml".equals(viewtype)){
			return SUCCESS;
		}else{
			return null;
		}
	}
}

