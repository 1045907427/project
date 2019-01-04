/**
 * @(#)JasperReportUtils.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-1-8 zhanghonghui 创建版本
 */
package com.hd.agent.common.util;

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hd.agent.agprint.model.PrintJobDetail;
import com.hd.agent.agprint.service.IPrintJobService;
import com.hd.agent.report.service.IStorageReportService;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import net.sf.jasperreports.engine.export.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.opensymphony.xwork2.ActionContext;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.json.JSONObject;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class JasperReportUtils {
	private static final Logger logger = Logger.getLogger(JasperReportUtils.class);
	
	public JasperReportUtils(){
		
	}
	/**
	 * 输出报表<br/>
	 * map中参数：<br/>
	 * jreportList : 报表列表<br/>
	 * response :   HttpServletResponse <br/>
	 * isview : 只显示<br/>
	 * viewtype : 显示类型<br/>
	 * PDFOWNERPASSWORD ： pdf拥有者密码<br/>
	 * PDFUSERPASSWORD : PDF 用户密码<br/>
	 * @param map
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-30
	 */
	public static void renderJasperReport(Map<String, Object> map) throws Exception{
		
    	ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response=(HttpServletResponse) ac.get(StrutsStatics.HTTP_RESPONSE);
		
		//HttpServletResponse response=(HttpServletResponse) map.get("response");
		List<JasperPrint> jrlist=(List<JasperPrint>)map.get("jreportList");
		boolean isview=(Boolean) map.get("isview");
		String viewtype=(String) map.get("viewtype");
		String pdfOwnerPasswd=(String) map.get("PDFOWNERPASSWORD");
		String pdfUserPasswd=(String) map.get("PDFUSERPASSWORD");
		if(null!=jrlist && jrlist.size()>0){
        	if(!isview){
            	response.reset();
            	response.setContentType("application/octet-stream");
            	ServletOutputStream ouputStream = response.getOutputStream(); 
            	GZIPOutputStream gzip = new GZIPOutputStream(ouputStream);//压缩
	            ObjectOutputStream oos = new ObjectOutputStream(gzip);     
	            oos.writeObject(jrlist);  
	            oos.flush();        
	            oos.close();
	            gzip.flush();
	            gzip.close();
	            ouputStream.flush();   
	            ouputStream.close();
        	}else{
        		if("applet".equals(viewtype)){
	            	response.reset();
	            	response.setContentType("application/octet-stream");
	            	ServletOutputStream ouputStream = response.getOutputStream();  
		            ObjectOutputStream oos = new ObjectOutputStream(ouputStream);     
		            oos.writeObject(jrlist);  
		            oos.flush();        
		            oos.close();
		            ouputStream.flush();   
		            ouputStream.close();            			
        		}else if("html".equals(viewtype)){                    	
    				//ouputStream.flush();
    				//ouputStream.close();
                	response.reset();
                	response.setCharacterEncoding("UTF-8");
					JRXhtmlExporter exporter = new JRXhtmlExporter();
    	            //exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);                	
                	exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT_LIST, jrlist);
                	request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE, jrlist);  
    	            exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,response.getWriter());
    	            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
    	            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "utf-8");
    	            exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES,false); 
    	            exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,"pt");
    	            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath()+"/jreportServlet/image?image=");
    	            //exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
    	            //exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<div style=\"display:none; page-break-after: always;\">&nbsp;</div>");
    	            //exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "<hr/>");
    	            exporter.setParameter(JRHtmlExporterParameter.WRAP_BEFORE_PAGE_HTML, "<div style=\"display:block; page-break-after: always;\" print='lodop'>\n");
    	            exporter.setParameter(JRHtmlExporterParameter.WRAP_AFTER_PAGE_HTML, "</div>\n");
    	            exporter.exportReport();
        		}else if("xls".equals(viewtype)){
                	response.reset();
                	response.setCharacterEncoding("UTF-8"); 
        			response.setContentType("application/vnd.ms-excel");  
                    String reportName=null;  
                    reportName=(String)map.get("reportName");
                    if(reportName!=null && "".equals(reportName)){  
                    	reportName=reportName+".xls";  
                    }else{  
                    	reportName="export.xls";  
                    }  
                    String fileName = new String(reportName.getBytes("gbk"), "utf-8");  
                    response.setHeader("Content-disposition", "attachment; filename="  
                      + fileName);  
                    ServletOutputStream ouputStream = response.getOutputStream();  
                    JRXlsExporter exporter = new JRXlsExporter();  
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jrlist);  
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,ouputStream);  
                    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);  
                    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);  
                    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);  
                    exporter.exportReport();  
                    ouputStream.flush();  
                    ouputStream.close();  
        		}else if("image".equals(viewtype)){

				}
				else{
	            	response.reset();
	            	response.setContentType("application/pdf");
					//byte[] bytes=JasperRunManager.runReportToPdf(jreport,parameters,new JRBeanCollectionDataSource(list));  
					//response.setContentLength(bytes.length);
					//ServletOutputStream ouputStream = response.getOutputStream();
					//ouputStream.write(bytes, 0, bytes.length);
	            	JRPdfExporter exporter = new JRPdfExporter();
		            //exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
	        		exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
	        		exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY, Boolean.TRUE);
	        		//exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, "");
	        		if(null!=pdfUserPasswd && !"".equals(pdfUserPasswd.trim())){
	        			exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, pdfUserPasswd.trim());
	        		}
	        		if(null!=pdfOwnerPasswd && !"".equals(pdfOwnerPasswd.trim())){
	        			exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, pdfOwnerPasswd);
	        		}
		    		exporter.setParameter(
		    				JRPdfExporterParameter.PERMISSIONS, 
		    				new Integer(0)
		    				);
	            	exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jrlist);
	                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,response.getOutputStream());
		            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,"UTF-8");
		            exporter.exportReport();
        		}
        	}
        }
	}
	/**
	 * 给lodop用的输出<br/>
	 * map中参数：<br/>
	 * jreportList : 报表列表<br/>
	 * response :   HttpServletResponse <br/>
	 * isview : 只显示<br/>
	 * viewtype : 显示类型<br/>
	 * PDFOWNERPASSWORD ： pdf拥有者密码<br/>
	 * PDFUSERPASSWORD : PDF 用户密码<br/>
	 * @param map
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-30
	 */
	public static List<Map<String, Object>> renderJasperReportForAjax(Map<String, Object> map) throws Exception{

		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		StringBuffer sBuffer=new StringBuffer();
		
    	ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response=(HttpServletResponse) ac.get(StrutsStatics.HTTP_RESPONSE);
		ISysParamService sysParamService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
		SysParam sysParam = sysParamService.getSysParam("isSetPrintHeight");
		String isSetPrintHeight = "1";
		if(null!=sysParam){
			isSetPrintHeight = sysParam.getPvalue();
		}
		SysParam htmlHeaderSysParam = sysParamService.getSysParam("isPrintUseHtmlHeader");
		String isPrintUseHtmlHeader = "1";
		if(null!=htmlHeaderSysParam){
			isPrintUseHtmlHeader = htmlHeaderSysParam.getPvalue();
		}
		if(null==isPrintUseHtmlHeader || "".equals(isPrintUseHtmlHeader)){
			isPrintUseHtmlHeader="1";
		}
		//HttpServletResponse response=(HttpServletResponse) map.get("response");
		List<JasperPrint> jrlist=(List<JasperPrint>)map.get("jreportList");
		boolean isview=(Boolean) map.get("isview");
		String viewtype=(String) map.get("viewtype");
		String printJobId=(String)map.get("printJobId");
    	
		//ouputStream.flush();
		//ouputStream.close();
		response.reset();
		response.setCharacterEncoding("UTF-8"); 
		JRXhtmlExporter exporter = new JRXhtmlExporter();
	    //exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);                	
		exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT_LIST, jrlist);
		request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE, jrlist);  
	    exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STRING_BUFFER,sBuffer);
	    exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "utf-8");
	    exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES,false);
	    exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,"pt");
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
		//保存文件到数据库
		exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DB, Boolean.TRUE);
		/*。
		String imageDirPath = OfficeUtils.getFilepath() + "/jasperimg/" ;
		String dayDirPath=CommonUtils.getYearMonthDayDirPath();
		imageDirPath=imageDirPath+dayDirPath;
		imageDirPath=CommonUtils.filterFilePathSaparator(imageDirPath);
		File imageDir=new File(imageDirPath);
		if(!imageDir.exists() && !imageDir.isDirectory()){
			imageDir.mkdirs();
		}
		String dayDirUrlParam=dayDirPath.replace('\\','-').replace('/','-');
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, imageDirPath);
		*/
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+(request.getServerPort() == 80 ? "" : ":" + request.getServerPort())+path;
		exporter.setParameter(JRHtmlExporterParameter.WEB_BASE_URL_PATH,basePath);
	    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, basePath);
		//exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath()+"/jreportServlet/image?image=");
	    //exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
	    //exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<div style=\"display:none; page-break-after: always;\">&nbsp;</div>");
	    //exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "<hr/>");
	    exporter.setParameter(JRHtmlExporterParameter.WRAP_BEFORE_PAGE_HTML, "<div style=\"display:block; page-break-after: always;\" printapp='ajaxhtml' >\n");
	    exporter.setParameter(JRHtmlExporterParameter.WRAP_AFTER_PAGE_HTML, "</div>\n");
	    exporter.exportReport();
	    
	    if(sBuffer.length()>0){
	    	String htmlstring=sBuffer.toString().replaceAll("\n", "");
	    	Document doc = Jsoup.parse(htmlstring);
	    	StringBuffer htmlHeadBuffer=new StringBuffer();
			//htmlHeadBuffer.append("<!DOCTYPE html>");
			if("1".equals(isPrintUseHtmlHeader)) {
				htmlHeadBuffer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
				htmlHeadBuffer.append("<html>\n");
				htmlHeadBuffer.append("<head>\n");
				htmlHeadBuffer.append("  <title></title>\n");
				htmlHeadBuffer.append("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n");
				htmlHeadBuffer.append("  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\n");
				htmlHeadBuffer.append("  <style type=\"text/css\">\n");
				htmlHeadBuffer.append("    a {text-decoration: none}\n");
				htmlHeadBuffer.append("    * {padding:0;margin:0;}\n");
				htmlHeadBuffer.append("  </style>\n");
				htmlHeadBuffer.append("</head>\n");
				htmlHeadBuffer.append("<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">\n");
			}else{
				htmlHeadBuffer.append("  <style type=\"text/css\">\n");
				htmlHeadBuffer.append("    a {text-decoration: none}\n");
				htmlHeadBuffer.append("    * {padding:0;margin:0;}\n");
				htmlHeadBuffer.append("  </style>\n");
			}
			htmlHeadBuffer.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n");
			htmlHeadBuffer.append("<tr><td width=\"50%\">&nbsp;</td><td align=\"center\">\n");
			htmlHeadBuffer.append("");
			StringBuffer htmlFooterBuffer=new StringBuffer();
			htmlFooterBuffer.append("</td><td width=\"50%\">&nbsp;</td></tr>\n");
			htmlFooterBuffer.append("</table>\n");
			if("1".equals(isPrintUseHtmlHeader)) {
				htmlFooterBuffer.append("</body>\n");
				htmlFooterBuffer.append("</html>\n");
			}
			IPrintJobService printJobService = (IPrintJobService) SpringContextUtils.getBean("printJobService");
			if (doc!=null){
				//解析打印内容高度 不设置高度
				String jrpageStyle =  null;
				String jrPageRep = "";
				if("0".equals(isSetPrintHeight)){
					Elements e2 = doc.select("div.jrPage");
					if(null!=e2 && e2.size()>0){
						jrpageStyle = e2.get(0).attr("style");
						if (StringUtils.isNotEmpty(jrpageStyle) && jrpageStyle.indexOf("height")>=0) {
							String[] styleArr = jrpageStyle.split(";");
							for(int i=0;i<styleArr.length;i++){
								if(styleArr[i].indexOf("height")<0){
									jrPageRep += styleArr[i]+";";
								}
							}
						}
					}
				}
	    		Elements elements=doc.select("div[printapp='ajaxhtml']");
	    		for (org.jsoup.nodes.Element element : elements) {
			    	Map<String,Object> resultMap=new HashMap<String, Object>();
	    			if(element==null){
	    				continue;
	    			}
					String elemhtml=element.html();

					elemhtml=elemhtml.replaceAll("\n", "");
					elemhtml=elemhtml.replaceAll("font-family: 'DejaVu Sans'","font-family: sans-serif");
					elemhtml=elemhtml.replaceAll("font-family: 'DejaVu Sans Mono'","font-family: sans-serif");
					elemhtml=elemhtml.replaceAll("font-family: 'DejaVu Serif'","font-family: sans-serif");
					if(null!=jrpageStyle){
						elemhtml=elemhtml.replaceAll(jrpageStyle,jrPageRep);
					}
					if(elemhtml==null || "".equals(elemhtml.trim())){
						continue;
					}

					elemhtml=htmlHeadBuffer.toString()+ elemhtml +htmlFooterBuffer.toString();

					//String elembase64=CodeUtils.base64Encode(elemhtml.getBytes());
					//打印的数据页
					PrintJobDetail printJobDetail =new PrintJobDetail();


					if(null==printJobId || "".equals(printJobId.trim())){
						printJobId=CommonUtils.getUUIDString();
					}
					//对应的打印编号
					printJobDetail.setJobid(printJobId);
					String printOrderId="";
					try{
						Elements agpagedataInputs=element.select("input[myattr='agprintparamsdatahtml']");
						if(agpagedataInputs!=null && agpagedataInputs.size()>0){
							String tmp=agpagedataInputs.first().attr("jsondata");
							if(tmp!=null && !"".equals(tmp.trim())) {
								byte[] tmpArr= Base64.decodeBase64(tmp.trim().getBytes("UTF-8"));
								tmp=new String(tmpArr);
								JSONObject agpagedata = JSONObject.fromObject(tmp);
								if (agpagedata != null) {
									resultMap.putAll(agpagedata);
									if (agpagedata.containsKey("printOrderId")) {
										printOrderId = (String) agpagedata.get("printOrderId");
									}
								}
							}
						}
					}catch(Exception ex){
						logger.error("读取打印内容里的打印参数信息时错误",ex);
					}


					printJobDetail.setPrintorderid(printOrderId);
					printJobDetail.setAddtime(new Date());

					try{
						Elements printPageInput=element.select("input[myattr='showprintpage']");
						if(printPageInput!=null && printPageInput.size()>0){
							String tmp=printPageInput.first().attr("current");
							if(tmp!=null && !"".equals(tmp.trim())){
								resultMap.put("orderCurrentPage", tmp.trim());
								//当前打印页数
								printJobDetail.setCurrentpage(Integer.parseInt(tmp.trim()));
							}
							tmp=printPageInput.first().attr("total");
							if(tmp!=null && !"".equals(tmp.trim())){
								resultMap.put("orderTotalPage", tmp.trim());
								//总打印页数
								printJobDetail.setTotalpage(Integer.parseInt(tmp.trim()));
							}
						}
					}catch(Exception ex){
						logger.error("读取打印内容里的页信息错误",ex);
					}

					printJobDetail.setContent(elemhtml.getBytes("UTF-8"));
					printJobDetail.setContenttype("html");
					boolean storageflag=printJobService.addPrintJobDetail(printJobDetail);
					if(storageflag && StringUtils.isEmpty(printJobDetail.getId())){
						storageflag=false;
					}

					if(!storageflag){
						String msg="";
						if(printOrderId!=null && !"".equals(printOrderId.trim())){
							msg="单据："+printOrderId+"生成第"+ printJobDetail.getCurrentpage()+"页打印内容时失败";
						}else{
							msg="生成打印内容时失败";
						}
						logger.error(msg);
						throw new Exception(msg);
					}

					resultMap.put("pagedata", printJobDetail.getId());

					resultList.add(resultMap);
				}
	    	}	    	
	    }
    	return resultList;
	}
	/**
	 * 输出报表
	 * @param jrlist
	 * @param isview
	 * @param viewtype
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-12-30
	 */
	public static List<Map<String, Object>> renderJasperReportForAjax(List<JasperPrint> jrlist,Boolean isview,String viewtype,String printJobId) throws Exception{
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> reportMap = new HashMap<String, Object>();
			reportMap.put("jreportList", jrlist);
			reportMap.put("isview", isview);
			reportMap.put("viewtype", viewtype);
			reportMap.put("printJobId", printJobId);
			list = JasperReportUtils.renderJasperReportForAjax(reportMap);
		}catch (Exception ex){
			logger.error("打印数据转成AJAX数据时失败",ex);
		}
		return list;
	}
}

