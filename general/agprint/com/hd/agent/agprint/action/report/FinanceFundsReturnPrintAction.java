/**
 * @(#)FinanceFundsReturnPrintAction.java
 * @author zhanghonghui
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-30 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action.report;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.common.util.PageData;
import com.hd.agent.report.service.IFinanceFundsReturnService;
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
 *
 * 资金回笼相关打印
 * @author zhanghonghui
 */
public class FinanceFundsReturnPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(FinanceFundsReturnPrintAction.class);
    /**
     * 资金回笼service
     */
    private IFinanceFundsReturnService financeFundsReturnService;

    public IFinanceFundsReturnService getFinanceFundsReturnService() {
        return financeFundsReturnService;
    }

    public void setFinanceFundsReturnService(
            IFinanceFundsReturnService financeFundsReturnService) {
        this.financeFundsReturnService = financeFundsReturnService;
    }

    private AgprintServiceImpl agprintServiceImpl;

    public AgprintServiceImpl getAgprintServiceImpl() {
        return agprintServiceImpl;
    }

    public void setAgprintServiceImpl(AgprintServiceImpl agprintServiceImpl) {
        this.agprintServiceImpl = agprintServiceImpl;
    }
    //endregion 参数初始化

    /**
     * 客户银行回笼金额数据打印预览
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-10-9
     */
    public String financeBankWriteReportPrintView() throws Exception {
        return financeBankWriteReportPrintHandle(true);
    }

    /**
     * 客户银行回笼金额数据打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-10-9
     */
    public String financeBankWriteReportPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        boolean isview = false;
        if ("1".equals(justprint) || "true".equals(justprint)) {
            isview = false;
        } else {
            isview = true;
        }
        return financeBankWriteReportPrintHandle(isview);
    }

    /**
     * 客户银行回笼金额数据打印及预览处理
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-12
     */
    private String financeBankWriteReportPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "客户银行回笼金额数据打印-" + CommonUtils.getDataNumberSeconds());
        Map printTempletSettingFistMap = null;
        String templetid = request.getParameter("templetid");
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        String businessdatestart = request.getParameter("businessdate1");
        String businessdateend = request.getParameter("businessdate2");

        if ("".equals(businessdatestart) || "".equals(businessdateend)) {
            ajaxResultMap.put("msg", "请输入业务日期时间段以便打印或预览");
        }
        String countdate = businessdatestart + "至" + businessdateend;

        SysUser sysUser = getSysUser();
        pageMap.setCondition(map);
        map.put("isflag", "true");
        try {
            PageData pageData = financeFundsReturnService.showBankWriteReportData(pageMap);
            List<Map<String, Object>> list = pageData.getList();
            if (null == list || list.size() == 0) {
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
                printJob.setJobname("客户银行回笼金额数据打印");
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
                ajaxResultMap.put("printname", "客户银行回笼金额数据打印任务-" + printJob.getId());
            }

            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            for (Map item : list) {
                BigDecimal totalamount = (BigDecimal) item.get("totalamount");
                if (null == totalamount || BigDecimal.ZERO.compareTo(totalamount) == 0) {
                    continue;
                }
                resultList.add(item);
            }


            //获取打印模板
            Map templetQueryMap = new HashMap();

            templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
            templetQueryMap.put("code", "finance_bankwrite");
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
            //String reportModelFile = servletContext.getRealPath("/ireport/finance_bankwrite/finance_bankwrite.jasper");
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
            //打印内容排序策略
            String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

            //进行打印内容明细排序
            if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                Collections.sort(list, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
            }

            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            parameters = new HashMap();
            parameters.put("P_countdate", countdate);
            parameters.put("P_modeltype", "1");
                /*共用参数*/
            parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
            agprintServiceImpl.setTempletCommonParameter(parameters);

            JasperPrint jrprint =
                    JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(resultList));
            if (null != jrprint) {
                jrprint.setName("客户银行回笼情况表-" + countdate);
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
            logStr = "Excepti	on ：financeBankWriteReportPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage();
            logger.error("客户银行回笼金额数据打印及预览处理异常", ex);


            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "抱歉，获取打印数据异常");
                addJSONObject(ajaxResultMap);
            }
        }
        if ("ajaxhtml".equals(viewtype)) {
            return SUCCESS;
        } else {
            return null;
        }
    }

    /**
     * 分客户银行回笼打印预览
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-10-9
     */
    public String financeCustomerBankWriteReportPrintView() throws Exception {
        return financeCustomerBankWriteReportPrintHandle(true);
    }

    /**
     * 分客户银行回笼打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-10-9
     */
    public String financeCustomerBankWriteReportPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        boolean isview = false;
        if ("1".equals(justprint) || "true".equals(justprint)) {
            isview = false;
        } else {
            isview = true;
        }
        return financeCustomerBankWriteReportPrintHandle(isview);
    }

    /**
     * 分客户银行回笼打印及预览处理
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-12
     */
    private String financeCustomerBankWriteReportPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "分客户银行回笼打印-" + CommonUtils.getDataNumberSeconds());
        Map printTempletSettingFistMap = null;
        String templetid = request.getParameter("templetid");
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        String businessdatestart = request.getParameter("businessdate1");
        String businessdateend = request.getParameter("businessdate2");

        if ("".equals(businessdatestart) || "".equals(businessdateend)) {
            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "请输入业务日期时间段以便打印预览");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            } else {
                return null;
            }
        }
        String countdate = businessdatestart + "至" + businessdateend;

        pageMap.setCondition(map);
        map.put("isflag", "true");
        SysUser sysUser = getSysUser();
        try {
            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("分客户银行回笼打印");
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
                ajaxResultMap.put("printname", "分客户银行回笼打印任务-" + printJob.getId());
            }
            PageData pageData = financeFundsReturnService.showCustomerBankWriteReportData(pageMap);
            List<Map<String, Object>> list = pageData.getList();
            if (null == list || list.size() == 0) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到相关打印单据");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                } else {
                    return null;
                }
            }

            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            for (Map item : list) {
                BigDecimal totalamount = (BigDecimal) item.get("amount");
                if (null == totalamount || BigDecimal.ZERO.compareTo(totalamount) == 0) {
                    continue;
                }
                resultList.add(item);
            }


            //获取打印模板
            Map templetQueryMap = new HashMap();

            templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
            templetQueryMap.put("code", "finance_customerbankwrite");
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
            //String reportModelFile = servletContext.getRealPath("/ireport/finance_customerbankwrite/finance_customerbankwrite.jasper");
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
            //打印内容排序策略
            String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

            //进行打印内容明细排序
            if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                Collections.sort(list, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
            }

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            Map parameters = new HashMap();
            parameters.put("P_countdate", countdate);
				/*共用参数*/
            parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
            agprintServiceImpl.setTempletCommonParameter(parameters);

            JasperPrint jrprint =
                    JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(resultList));
            if (null != jrprint) {
                jrprint.setName("分客户银行回笼情况表-" + countdate);
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
            logStr = "Excepti	on ：financeCustomerBankWriteReportPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage();

            logger.error("分客户银行回笼打印及预览处理异常", ex);


            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "抱歉，获取打印数据异常");
                addJSONObject(ajaxResultMap);
            }
        }
        if ("ajaxhtml".equals(viewtype)) {
            return SUCCESS;
        } else {
            return null;
        }
    }
}

