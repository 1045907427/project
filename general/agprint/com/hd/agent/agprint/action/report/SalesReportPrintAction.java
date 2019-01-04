/**
 * @(#)SalesReportPrintAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-30 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action.report;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.hd.agent.agprint.service.IPrintTempletService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.model.SalesCustomerPriceReport;
import com.hd.agent.report.service.ISalesReportService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class SalesReportPrintAction extends BaseFilesAction  {
	//region 参数初始化
	private static final Logger logger = Logger.getLogger(SalesReportPrintAction.class);
	/**
	 * 销售报表service
	 */
	private ISalesReportService salesReportService;

	public ISalesReportService getSalesReportService() {
		return salesReportService;
	}

	public void setSalesReportService(ISalesReportService salesReportService) {
		this.salesReportService = salesReportService;
	}

	private AgprintServiceImpl agprintServiceImpl;

	public AgprintServiceImpl getAgprintServiceImpl() {
		return agprintServiceImpl;
	}

	public void setAgprintServiceImpl(AgprintServiceImpl agprintServiceImpl) {
		this.agprintServiceImpl = agprintServiceImpl;
	}
	/**
	 * 商品报价单打印预览
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	public String salesGoodsQuotationReportPrintView() throws Exception{
		return salesGoodsQuotationReportHandle(true);
	}
	/**
	 * 商品报价单打印处理
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-9
	 */
	public String salesGoodsQuotationReportPrint() throws Exception{
		String justprint=request.getParameter("justprint");
		boolean isview=false;
		if("1".equals(justprint) || "true".equals(justprint) ){
			isview=false;
		}else{
			isview=true;
		}
		return salesGoodsQuotationReportHandle(isview);
	}
	/**
	 * 商品报价单打印
	 * @param isview
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-12
	 */
	private String salesGoodsQuotationReportHandle(boolean isview) throws Exception{
		String viewtype = request.getParameter("viewtype");
		if (null == viewtype) {
			viewtype = "pdf";
		}
		viewtype = viewtype.trim();
		//返回的格式：
		//{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
		//ajax数据
		Map ajaxResultMap = new HashMap();
		ajaxResultMap.put("printname", "商品报价单打印-" + CommonUtils.getDataNumberSeconds());
		Map printTempletSettingFistMap = null;
		String templetid = request.getParameter("templetid");
		SysUser sysUser = getSysUser();
		 try {		

			//获取页面传过来的参数 封装到map里面
			Map map = CommonUtils.changeMap(request.getParameterMap());
			map.put("ispageflag", "true");
			//map赋值到pageMap中作为查询条件
			pageMap.setCondition(map);
			PageData pageData = salesReportService.showSalesGoodsQuotationReportData(pageMap);
        	if(null==pageData || null==pageData.getList() || pageData.getList().size()==0){
				if ("ajaxhtml".equals(viewtype)) {
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "未能找到相关打印单据");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				} else {
					return null;
				}
        	}

			 //打印任务信息
			 PrintJob printJob = new PrintJob();
			 if ( "ajaxhtml".equals(viewtype)) {
				 printJob.setAddtime(new Date());
				 printJob.setAdduserid(sysUser.getUserid());
				 printJob.setAddusername(sysUser.getName());
				 printJob.setIp(CommonUtils.getIP(request));
				 printJob.setJobname("商品报价单打印");
				 printJob.setOrderidarr("表单请求参数");
				 if (request.getQueryString() != null) {
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
				 boolean isjobflag = agprintServiceImpl.addPrintJob(printJob);
				 if (!isjobflag || StringUtils.isEmpty(printJob.getId())) {
					 ajaxResultMap.put("flag", false);
					 ajaxResultMap.put("msg", "申请单据打印时失败");
					 addJSONObject(ajaxResultMap);
					 return SUCCESS;
				 }
				 ajaxResultMap.put("printJobId", printJob.getId());
				 ajaxResultMap.put("printname", "商品报价单打印-" + printJob.getId());
			 }


			 //获取打印模板
			 Map templetQueryMap = new HashMap();

			 templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
			 templetQueryMap.put("code", "report_salesGoodsQuotation");
			 if (null != templetid && !"".equals(templetid)) {
				 templetQueryMap.put("templetid", templetid);
			 }
			 Map templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);
			 if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
				 printTempletSettingFistMap = new HashMap();
				 printTempletSettingFistMap.putAll(templetResultMap);
			 }
			 //打印模板文件
			 String printTempletFile = (String) templetResultMap.get("printTempletFile");
			 if (null == printTempletFile || "".equals(printTempletFile.trim())) {
				 if ("ajaxhtml".equals(viewtype)) {
					 ajaxResultMap.put("flag", false);
					 ajaxResultMap.put("msg", "未能找到相关打印模板");
					 addJSONObject(ajaxResultMap);
					 return SUCCESS;
				 } else {
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
			 //String reportModelFile = servletContext.getRealPath("/ireport/report_salesGoodsQuotation/report_salesGoodsQuotation.jasper");
			 JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
			 if (jreport == null) {
				 if ("ajaxhtml".equals(viewtype)) {
					 ajaxResultMap.put("flag", false);
					 ajaxResultMap.put("msg", "未能找到相关打印模板");
					 addJSONObject(ajaxResultMap);
					 return SUCCESS;
				 } else {
					 return null;
				 }
			 }
        	List<SalesCustomerPriceReport> list=pageData.getList();
			 //打印内容排序策略
			 String printDataOrderSeq =(String)templetResultMap.get("printDataOrderSeq");

			 //进行打印内容明细排序
			 if(null!=printDataOrderSeq && !"".equals(printDataOrderSeq.trim())){
				 Collections.sort(list, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
			 }

			Map parameters =null;
			
			List<JasperPrint> jrlist=new ArrayList<JasperPrint>();
			parameters = new HashMap();
                /*共用参数*/
			 parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
			 agprintServiceImpl.setTempletCommonParameter(parameters);
			
            JasperPrint jrprint=  
                 JasperFillManager.fillReport(jreport,parameters,new JRBeanCollectionDataSource(list));
            if(null!=jrprint){
    			SimpleDateFormat sf1 = new SimpleDateFormat("HHmmss");
    			String timeStr = sf1.format(new Date());
            	jrprint.setName("商品报价单-"+timeStr);
            	jrlist.add(jrprint);
            }

			 if (null == jrlist || jrlist.size() == 0) {
				 if ("ajaxhtml".equals(viewtype)) {
					 ajaxResultMap.put("flag", false);
					 ajaxResultMap.put("msg", "抱歉，未能找到相关打印单据");
					 addJSONObject(ajaxResultMap);
					 return SUCCESS;
				 } else {
					 return null;
				 }
			 }

			 //输出打印报表
			 if ("ajaxhtml".equals(viewtype)) {
				 List<Map<String, Object>> renderResult = JasperReportUtils.renderJasperReportForAjax(jrlist, isview, viewtype, printJob.getId());
				 boolean resultFlag = false;
				 if (null != renderResult && renderResult.size() > 0) {
					 resultFlag = true;
				 }
				 ajaxResultMap.put("flag", resultFlag);
				 ajaxResultMap.put("printdata", renderResult);
				 ajaxResultMap.put("msg", "获取数据成功");
				 if (printTempletSettingFistMap != null) {
					 ajaxResultMap.put("showpagesize", true);
					 ajaxResultMap.put("paperwidth", printTempletSettingFistMap.get("paperwidth"));
					 ajaxResultMap.put("paperheight", printTempletSettingFistMap.get("paperheight"));
					 ajaxResultMap.put("paperintwidth", printTempletSettingFistMap.get("paperintwidth"));
					 ajaxResultMap.put("paperintheight", printTempletSettingFistMap.get("paperintheight"));
					 ajaxResultMap.put("papersizename", printTempletSettingFistMap.get("papersizename"));
					 ajaxResultMap.put("lodophtmlmodel", printTempletSettingFistMap.get("lodophtmlmodel"));
				 }
				 addJSONObject(ajaxResultMap);

				 jrlist = null;
				 list = null;

				 return SUCCESS;
			 } else {
				 Map<String, Object> reportMap = new HashMap<String, Object>();
				 reportMap.put("response", response);
				 reportMap.put("jreportList", jrlist);
				 reportMap.put("isview", isview);
				 reportMap.put("viewtype", viewtype);
				 reportMap.put("PDFOWNERPASSWORD", getSysParamValue("PDFOWNERPASSWORD"));
				 JasperReportUtils.renderJasperReport(reportMap);
			 }
			
        } catch (Exception ex) {  
            logStr="Exception ：salesGoodsQuotationReportHandle()>>>>>>>>>>>>>>>>商品报价单异常 "+ex.getMessage();
			 logger.error("商品报价单打印及预览处理异常", ex);


			 if ("ajaxhtml".equals(viewtype)) {
				 ajaxResultMap.put("flag", false);
				 ajaxResultMap.put("msg", "抱歉，获取打印数据异常");
				 addJSONObject(ajaxResultMap);
			 }
        }finally{
        }
		if ("ajaxhtml".equals(viewtype)) {
			return SUCCESS;
		} else {
			return null;
		}
	}

	public String printSalesBillStatement() throws Exception{
		boolean isview = true;
		String viewtype = request.getParameter("viewtype");
		if (null == viewtype) {
			viewtype = "pdf";
		}
		viewtype = viewtype.trim();
		//返回的格式：
		//{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
		//ajax数据
		Map ajaxResultMap = new HashMap();
		ajaxResultMap.put("printname", "销售对账单打印-" + CommonUtils.getDataNumberSeconds());
		Map printTempletSettingFistMap = null;
		String templetid = request.getParameter("templetid");
		SysUser sysUser = getSysUser();

		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String begindate = CommonUtils.nullToEmpty(request.getParameter("begindate"));
		String enddate = CommonUtils.nullToEmpty(request.getParameter("enddate"));
		String customerid = request.getParameter("customerid");

		String countdate = begindate + "至" + enddate;

		pageMap.setCondition(map);
		map.put("isflag", "true");
		try {
			pageMap.setRows(99999999);
			PageData pageData = salesReportService.getSalesBillStatementDetailPageData(pageMap);
			List<Map<String, Object>> list = pageData.getList();
			if(list.size()==0){
				if ("ajaxhtml".equals(viewtype)) {
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "未能找到相关打印单据");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				} else {
					return null;
				}
			}

			//打印任务信息
			PrintJob printJob = new PrintJob();
			if ("ajaxhtml".equals(viewtype)) {
				printJob.setAddtime(new Date());
				printJob.setAdduserid(sysUser.getUserid());
				printJob.setAddusername(sysUser.getName());
				printJob.setIp(CommonUtils.getIP(request));
				printJob.setJobname("销售对账单打印");
				printJob.setOrderidarr("表单请求参数");
				if (request.getQueryString() != null) {
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
				boolean isjobflag = agprintServiceImpl.addPrintJob(printJob);
				if (!isjobflag || StringUtils.isEmpty(printJob.getId())) {
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "申请单据打印时失败");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				}
				ajaxResultMap.put("printJobId", printJob.getId());
				ajaxResultMap.put("printname", "销售对账单打印-" + printJob.getId());
			}

			//获取打印模板
			Map templetQueryMap = new HashMap();

			templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
			templetQueryMap.put("code", "report_salesbillstatement");
			if (null != templetid && !"".equals(templetid)) {
				templetQueryMap.put("templetid", templetid);
			}
			Map templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);
			if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
				printTempletSettingFistMap = new HashMap();
				printTempletSettingFistMap.putAll(templetResultMap);
			}
			//打印模板文件
			String printTempletFile = (String) templetResultMap.get("printTempletFile");
			if (null == printTempletFile || "".equals(printTempletFile.trim())) {
				if ("ajaxhtml".equals(viewtype)) {
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "未能找到相关打印模板");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				} else {
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
			//String reportModelFile = servletContext.getRealPath("/ireport/report_salesGoodsQuotation/report_salesGoodsQuotation.jasper");
			JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
			if (jreport == null) {
				if ("ajaxhtml".equals(viewtype)) {
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "未能找到相关打印模板");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				} else {
					return null;
				}
			}

			List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
			for(Map item : list){
				resultList.add(item);
			}
			Map parameters =null;

			List<JasperPrint> jrlist=new ArrayList<JasperPrint>();
			parameters = new HashMap();
			parameters.put("P_countdate", countdate);
			parameters.put("P_CustomerInfo", getCustomerById(customerid));
			parameters.put("P_SumDataInfo", pageData.getFooter().get(0));
                /*共用参数*/
			parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
			agprintServiceImpl.setTempletCommonParameter(parameters);

			JasperPrint jrprint= JasperFillManager.fillReport(jreport, parameters,new JRBeanCollectionDataSource(resultList));
			if(jrprint != null){
				jrprint.setName("销售对账单 - " + countdate);
				jrlist.add(jrprint);
			}

			if (null == jrlist || jrlist.size() == 0) {
				if ("ajaxhtml".equals(viewtype)) {
					ajaxResultMap.put("flag", false);
					ajaxResultMap.put("msg", "抱歉，未能找到相关打印单据");
					addJSONObject(ajaxResultMap);
					return SUCCESS;
				} else {
					return null;
				}
			}

			//输出打印报表
			if ("ajaxhtml".equals(viewtype)) {
				List<Map<String, Object>> renderResult = JasperReportUtils.renderJasperReportForAjax(jrlist, isview, viewtype, printJob.getId());
				boolean resultFlag = false;
				if (null != renderResult && renderResult.size() > 0) {
					resultFlag = true;
				}
				ajaxResultMap.put("flag", resultFlag);
				ajaxResultMap.put("printdata", renderResult);
				ajaxResultMap.put("msg", "获取数据成功");
				if (printTempletSettingFistMap != null) {
					ajaxResultMap.put("showpagesize", true);
					ajaxResultMap.put("paperwidth", printTempletSettingFistMap.get("paperwidth"));
					ajaxResultMap.put("paperheight", printTempletSettingFistMap.get("paperheight"));
					ajaxResultMap.put("paperintwidth", printTempletSettingFistMap.get("paperintwidth"));
					ajaxResultMap.put("paperintheight", printTempletSettingFistMap.get("paperintheight"));
					ajaxResultMap.put("papersizename", printTempletSettingFistMap.get("papersizename"));
					ajaxResultMap.put("lodophtmlmodel", printTempletSettingFistMap.get("lodophtmlmodel"));
				}
				addJSONObject(ajaxResultMap);

				return SUCCESS;
			} else {
				Map<String, Object> reportMap = new HashMap<String, Object>();
				reportMap.put("response", response);
				reportMap.put("jreportList", jrlist);
				reportMap.put("isview", isview);
				reportMap.put("viewtype", viewtype);
				reportMap.put("PDFOWNERPASSWORD", getSysParamValue("PDFOWNERPASSWORD"));
				JasperReportUtils.renderJasperReport(reportMap);
			}
		} catch (Exception ex) {
			logStr="Excepti	on ：financeBankWriteReportPrintHandle()>>>>>>>>>>>>>>>>销售对账单打印及预览处理异常 "+ex.getMessage();
			logger.error("销售对账单打印及预览处理异常", ex);


			if ("ajaxhtml".equals(viewtype)) {
				ajaxResultMap.put("flag", false);
				ajaxResultMap.put("msg", "抱歉，获取打印数据异常");
				addJSONObject(ajaxResultMap);
			}
		}finally{
		}
		if ("ajaxhtml".equals(viewtype)) {
			return SUCCESS;
		} else {
			return null;
		}
	}
}

