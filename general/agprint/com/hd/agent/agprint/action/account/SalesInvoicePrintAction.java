/**
 * @(#)SalesInvoicePrintAction.java
 * @author zhanghonghui
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-30 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action.account;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.account.model.SalesInvoiceBillDetail;
import com.hd.agent.account.model.SalesInvoiceDetail;
import com.hd.agent.account.service.ISalesInvoiceBillService;
import com.hd.agent.account.service.ISalesInvoiceService;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
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
 *
 * 销售发票
 * @author zhanghonghui
 */
public class SalesInvoicePrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(SalesInvoicePrintAction.class);
    /**
     * 销售发票
     */
    private SalesInvoice salesInvoice;

    private ISalesInvoiceService salesInvoiceService;

    private ISalesInvoiceBillService salesInvoiceBillService;

    public ISalesInvoiceService getSalesInvoiceService() {
        return salesInvoiceService;
    }

    public void setSalesInvoiceService(ISalesInvoiceService salesInvoiceService) {
        this.salesInvoiceService = salesInvoiceService;
    }

    public SalesInvoice getSalesInvoice() {
        return salesInvoice;
    }

    public void setSalesInvoice(SalesInvoice salesInvoice) {
        this.salesInvoice = salesInvoice;
    }

    public ISalesInvoiceBillService getSalesInvoiceBillService() {
        return salesInvoiceBillService;
    }

    public void setSalesInvoiceBillService(ISalesInvoiceBillService salesInvoiceBillService) {
        this.salesInvoiceBillService = salesInvoiceBillService;
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
     * 销售发票打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    public String salesInvoicePrintView() throws Exception {
        return salesInvoicePrintHandle(true);
    }

    /**
     * 销售发票打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-10-5
     */
    public String salesInvoicePrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            salesInvoicePrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return salesInvoicePrintHandle(isview);
        }
        return null;
    }

    private String salesInvoicePrintHandle(Boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "销售开票抽单打印-" + CommonUtils.getDataNumberSeconds());
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs || "".equals(idarrs.trim())) {
            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "未能找到相关打印单据");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            } else {
                return null;
            }
        }
        String templetid = request.getParameter("templetid");
        Map printTempletSettingFistMap = null;

        String ispreview = request.getParameter("ispreview");
        if ("1".equals(ispreview)) {
            ispreview = "1";
        } else {
            ispreview = "0";
        }
        String neggoodstodiscount = request.getParameter("neggoodstodiscount");
        if ("1".equals(neggoodstodiscount)) {
            neggoodstodiscount = "1";
        } else {
            neggoodstodiscount = "0";
        }
        String zkgoodstodiscount = request.getParameter("zkgoodstodiscount");
        if ("1".equals(zkgoodstodiscount)) {
            zkgoodstodiscount = "1";
        } else {
            zkgoodstodiscount = "0";
        }
        String isShowDiscountSum = request.getParameter("isShowDiscountSum");
        if ("1".equals(isShowDiscountSum)) {
            isShowDiscountSum = "1";
        } else {
            isShowDiscountSum = "0";
        }
        String canprintIds = "";
        SysUser sysUser = getSysUser();
        try {

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("销售开票抽单打印");
                printJob.setOrderidarr(idarrs);
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
                ajaxResultMap.put("printname", "销售开票抽单打印-" + printJob.getId());
            }
            String printlimit = getPrintLimitInfo();
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            map.put("showdetail", "1");
            map.put("neggoodstodiscount", neggoodstodiscount);
            map.put("zkgoodstodiscount", zkgoodstodiscount);
            map.put("isShowDiscountSum", isShowDiscountSum);
            map.put("isShowListDataForPrint", "1");    //
            List<SalesInvoice> list = salesInvoiceService.showSalesInvoicePrintListBy(map);

            if (list.size() == 0) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到打印模板");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                } else {
                    return null;
                }
            }
            //获取打印模板
            Map templetQueryMap = new HashMap();
            templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
            templetQueryMap.put("code", "sales_invoice");

            if (null != templetid && !"".equals(templetid)) {
                templetQueryMap.put("templetid", templetid);
            }
            Map templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);
            //打印模板文件
            String printTempletFile = (String) templetResultMap.get("printTempletFile");
            if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到打印模板");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                } else {
                    return null;
                }
            }
            //每页条数
            Integer countperpage = 0;
            //是否填充空行
            String isfillblank = "0";
            //第一个可用的打印模板配置信息
            if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                printTempletSettingFistMap = new HashMap();
                printTempletSettingFistMap.putAll(templetResultMap);
                countperpage = (Integer) templetResultMap.get("countperpage");
                if (null == countperpage || countperpage < 0) {
                    countperpage = 0;
                }
                isfillblank = (String) templetResultMap.get("isfillblank");
                if (isfillblank == null || !"1".equals(isfillblank)) {
                    isfillblank = "0";
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
            String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");


            //String reportModelFile = servletContext.getRealPath("/ireport/sales_invoice/sales_invoice.jasper");
            JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();

            Personnel salePersonnel = null;
            for (SalesInvoice item : list) {
                Customer customerInfo = item.getCustomerInfo();
                if (null == customerInfo) {
                    customerInfo = new Customer();
                }

                if (StringUtils.isNotEmpty(item.getSalesuser())) {
                    salePersonnel = getPersonnelInfoById(item.getSalesuser());
                }
                if (null == salePersonnel && StringUtils.isNotEmpty(customerInfo.getSalesuserid())) {
                    salePersonnel = getPersonnelInfoById(customerInfo.getSalesuserid());
                }
                if (null == salePersonnel) {
                    salePersonnel = new Personnel();
                }
                List<SalesInvoiceDetail> detaillist = item.getSalesInvoiceDetailList();
                if (null == detaillist || detaillist.size() == 0) {
                    continue;
                }

                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detaillist, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }
                parameters = new HashMap();
                //销售员
                parameters.put("P_SaleUser", salePersonnel.getName());
                //销售员电话
                parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                parameters.put("P_buyername", item.getInvoicecustomername());
                parameters.put("P_Customer", item.getInvoicecustomername());
                parameters.put("P_salername", getSysParamValue("INVOICETITLE"));
                parameters.put("P_invoiceno", item.getInvoiceno());
                parameters.put("P_invoicecode", item.getInvoicecode());
                parameters.put("P_remark", item.getRemark());
                String sdate = item.getBusinessdate();
                if (StringUtils.isNotEmpty(sdate)) {
                    String[] darr = sdate.split("-");
                    if (darr.length >= 3) {
                        sdate = darr[0] + "年" + darr[1] + "月" + darr[2] + "日";
                    }
                }
                parameters.put("P_createdate", sdate);
                /**打印模板参数用的单据信息**/
                SalesInvoice orderInfo = (SalesInvoice) CommonUtils.deepCopy(item);
                orderInfo.setSalesInvoiceDetailList(null);
                parameters.put("P_OrderInfo", orderInfo);
                //公共参数
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                List<SalesInvoiceDetail> tmpdlist = null;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdlist = fillReportWithBlank(detaillist, countperpage);
                } else {
                    tmpdlist = detaillist;
                }
                JasperPrint jrprint =
                        JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdlist));
                if (null != jrprint) {
                    jrprint.setName("销售开票抽单-" + item.getId());
                    jrlist.add(jrprint);

                    if (!isview) {
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", item.getId());
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                        if (null == item.getPrinttimes()) {
                            item.setPrinttimes(0);
                        }
                        callbackparamMap.put("orderprinttimes", item.getPrinttimes());
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        printIdList.add(item.getId());


                        if (StringUtils.isNotEmpty(printJob.getId())) {
                            //打印次数更新回调方法
                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", item.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", printJob.getId());
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
                            //打印回调处理
                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(printJob.getId());
                            printJobCallHandle.setClassname("salesInvoicePrintAction");
                            printJobCallHandle.setMethodname("salesInvoicePrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("销售开票抽单");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("销售开票抽单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");//次数更新
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                }
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

            if (!isview) {
                if (printIdList.size() > 0) {
                    String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
                    if (null != printarr) {
                        canprintIds = StringUtils.join(printarr, ",");
                    }
                }
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                //printLogsb.append("申请打印的销售开票抽单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售开票抽单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：销售开票抽单打印 。");
                addPrintLogInfo("PrintHandle-salesInvoice", printLogsb.toString(), null);
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
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-销售开票抽单打印及预览异常：");
                //printLogsb.append("申请打印的销售开票抽单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售开票抽单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-销售开票抽单打印预览异常：");
            }
            printLogsb.append("Exception ：salesInvoicePrintHandle()>>>>>>>>>>>>>>>>异常信息 " + ex.getMessage());
            addPrintLogInfo("PrintHandle-salesInvoice", printLogsb.toString(), null);
            logger.error("打印申请-销售开票抽单打印及预览异常：", ex);

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
     * 销售发票打印回调
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-10
     */
    public void salesInvoicePrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesInvoice", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesInvoice", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        String orderprinttimes = (String) map.get("orderprinttimes");
        int iprinttimes = 0;
        if (null != orderprinttimes && !"".equals(orderprinttimes.trim()) && StringUtils.isNumeric(orderprinttimes.trim())) {
            iprinttimes = Integer.parseInt(orderprinttimes);
        }
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的销售开票抽单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售开票抽单编号： " + id);
            try {
                SalesInvoice salesInvoice = salesInvoiceService.getPureSalesInvoicePureInfo(id);
                if (null != salesInvoice) {
                    if (null == salesInvoice.getPrinttimes() || iprinttimes >= salesInvoice.getPrinttimes()) {
                        salesInvoice.setPrinttimes(1);
                        salesInvoiceService.updateOrderPrinttimes(salesInvoice);
                    }
                    printLogsb.append(" 更新打印次数成功 ");
                } else {
                    printLogsb.append(" 更新打印次数成功 ");
                }
            } catch (Exception ex) {
                printLogsb.append(" 回调更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("销售开票抽单更新打印次数失败", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无销售开票抽单编号信息");
        }
        printLogsb.append(" 。");
        //printLogsb.append(" 更新响应来源于：销售开票抽单打印 。");
        printLogsb.append(" 打印数据来源");
        printLogsb.append("(销售开票抽单编号)：" + idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesInvoice", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 销售发票反射打印回调
     * @param printcallback
     * @return void
     * @throws
     * @author zhanghonghui
     * @date Nov 02, 2016
     */
    public boolean salesInvoicePrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesInvoice", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesInvoice", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        Integer orderprinttimes = (Integer) map.get("orderprinttimes");
        if (null == orderprinttimes ) {
            orderprinttimes = 0;
        }
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的销售开票抽单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售开票抽单编号： " + id);
            try {
                SalesInvoice salesInvoice = salesInvoiceService.getPureSalesInvoicePureInfo(id);
                if (null != salesInvoice) {
                    if (null == salesInvoice.getPrinttimes() || orderprinttimes >= salesInvoice.getPrinttimes()) {
                        salesInvoice.setPrinttimes(1);
                        salesInvoiceService.updateOrderPrinttimes(salesInvoice);
                    }
                    printLogsb.append(" 更新打印次数成功 ");
                } else {
                    printLogsb.append(" 更新打印次数成功 ");
                }
                isok = true;
            } catch (Exception ex) {
                printLogsb.append(" 回调更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("销售开票抽单更新打印次数失败", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无销售开票抽单编号信息");
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesInvoice", printLogsb.toString(), null);
        return isok;
    }


    private List fillReportWithBlank(List<SalesInvoiceDetail> list, Integer countperpage) {
        if (countperpage == null || countperpage <= 0) {
            return list;
        }
        int isize = 0;
        if (null == list) {
            list = new ArrayList<SalesInvoiceDetail>();
        } else {
            isize = list.size();
        }
        int iLeft = isize % countperpage;
        if (isize == 0 || (isize != 0 && iLeft != 0)) {
            iLeft = countperpage - iLeft;
        }
        for (int i = 0; i < iLeft; i++) {
            SalesInvoiceDetail sid = new SalesInvoiceDetail();
            sid.setUnitnum(BigDecimal.ZERO);
            sid.setAuxnum(BigDecimal.ZERO);
            sid.setTax(BigDecimal.ZERO);
            sid.setIsreportblank(1);    //为空
            list.add(sid);
        }
        return list;
    }

    /**
     * 填充空白行数
     * @param list
     * @return
     */
    private List fillReportWithBlankInvoiceBill(List<SalesInvoiceBillDetail> list, Integer countperpage) {
        if (countperpage == null || countperpage <= 0) {
            return list;
        }
        int isize = 0;
        if (null == list) {
            list = new ArrayList<SalesInvoiceBillDetail>();
        } else {
            isize = list.size();
        }
        int iLeft = isize % countperpage;
        if (isize == 0 || (isize != 0 && iLeft != 0)) {
            iLeft = countperpage - iLeft;
        }
        for (int i = 0; i < iLeft; i++) {
            SalesInvoiceBillDetail sid = new SalesInvoiceBillDetail();
            sid.setUnitnum(BigDecimal.ZERO);
            sid.setAuxnum(BigDecimal.ZERO);
            sid.setTax(BigDecimal.ZERO);
            sid.setIsreportblank(1);    //为空
            list.add(sid);
        }
        return list;
    }

    /**
     * 销售开票打印
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-16
     */
    public String salesInvoiceBillPrintView() throws Exception {
        return salesInvoiceBillPrintHandle(true);
    }

    /**
     * 销售开票打印
     * @return
     * @throws Exception
     * @author 潘笑笑
     * @date 2015-03-16
     */
    public String salesInvoiceBillPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            salesInvoiceBillPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return salesInvoiceBillPrintHandle(isview);
        }
        return null;
    }

    /**
     * 执行销售开票打印
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-16
     */
    private String salesInvoiceBillPrintHandle(Boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "销售开票打印-" + CommonUtils.getDataNumberSeconds());
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs || "".equals(idarrs.trim())) {
            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "未能找到相关打印单据");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            } else {
                return null;
            }
        }
        String templetid = request.getParameter("templetid");
        Map printTempletSettingFistMap = null;
        String ispreview = request.getParameter("ispreview");
        if ("1".equals(ispreview)) {
            ispreview = "1";
        } else {
            ispreview = "0";
        }
        String neggoodstodiscount = request.getParameter("neggoodstodiscount");
        if ("1".equals(neggoodstodiscount)) {
            neggoodstodiscount = "1";
        } else {
            neggoodstodiscount = "0";
        }
        String zkgoodstodiscount = request.getParameter("zkgoodstodiscount");
        if ("1".equals(zkgoodstodiscount)) {
            zkgoodstodiscount = "1";
        } else {
            zkgoodstodiscount = "0";
        }
        String isShowDiscountSum = request.getParameter("isShowDiscountSum");
        if ("1".equals(isShowDiscountSum)) {
            isShowDiscountSum = "1";
        } else {
            isShowDiscountSum = "0";
        }
        String canprintIds = "";
        SysUser sysUser = getSysUser();
        try {
            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("销售开票打印");
                printJob.setOrderidarr(idarrs);
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
                ajaxResultMap.put("printname", "销售开票打印任务-" + printJob.getId());
            }
            String printlimit = getPrintLimitInfo();
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            map.put("showdetail", "1");
            map.put("isflag", true);
            map.put("neggoodstodiscount", neggoodstodiscount);
            map.put("zkgoodstodiscount", zkgoodstodiscount);
            map.put("isShowDiscountSum", isShowDiscountSum);
            map.put("isShowListDataForPrint", "1");
            List<SalesInvoiceBill> list = salesInvoiceBillService.showSalesInvoiceBillPrintListBy(map);

            if (list.size() == 0) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到相关打印单据");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                } else {
                    return null;
                }
            }
            //获取打印模板
            Map templetQueryMap = new HashMap();
            templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
            templetQueryMap.put("code", "sales_invoicebill");

            if (null != templetid && !"".equals(templetid)) {
                templetQueryMap.put("templetid", templetid);
            }
            Map templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);
            //打印模板文件
            String printTempletFile = (String) templetResultMap.get("printTempletFile");
            if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到打印模板");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                } else {
                    return null;
                }
            }
            //每页条数
            Integer countperpage = 0;
            //是否填充空行
            String isfillblank = "0";
            //第一个可用的打印模板配置信息
            if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                printTempletSettingFistMap = new HashMap();
                printTempletSettingFistMap.putAll(templetResultMap);
                countperpage = (Integer) templetResultMap.get("countperpage");
                if (null == countperpage || countperpage < 0) {
                    countperpage = 0;
                }
                isfillblank = (String) templetResultMap.get("isfillblank");
                if (isfillblank == null || !"1".equals(isfillblank)) {
                    isfillblank = "0";
                }
            }
            //String reportModelFile = servletContext.getRealPath("/ireport/sales_invoice/sales_invoice.jasper");
            JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
            if (null == jreport) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到打印模板");
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
            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();
            Personnel salePersonnel = null;
            for (SalesInvoiceBill item : list) {
                Customer customerInfo = item.getCustomerInfo();
                if (null == customerInfo) {
                    customerInfo = new Customer();
                }

                if (StringUtils.isNotEmpty(item.getSalesuser())) {
                    salePersonnel = getPersonnelInfoById(item.getSalesuser());
                }
                if (null == salePersonnel && StringUtils.isNotEmpty(customerInfo.getSalesuserid())) {
                    salePersonnel = getPersonnelInfoById(customerInfo.getSalesuserid());
                }
                if (null == salePersonnel) {
                    salePersonnel = new Personnel();
                }
                if (true == isview) {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                } else {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                }
                List<SalesInvoiceBillDetail> detaillist = item.getDetaiList();
                List<SalesInvoiceBillDetail> tmpdlist = new ArrayList<SalesInvoiceBillDetail>();
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdlist = fillReportWithBlankInvoiceBill(detaillist, countperpage);
                } else {
                    tmpdlist = detaillist;
                }
                parameters = new HashMap();
                //销售员
                parameters.put("P_SaleUser", salePersonnel.getName());
                //销售员电话
                parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                parameters.put("P_buyername", item.getInvoicecustomername());
                parameters.put("P_Customer", item.getInvoicecustomername());
                parameters.put("P_salername", salePersonnel.getName());
                parameters.put("P_invoiceno", item.getInvoiceno());
                parameters.put("P_invoicecode", item.getInvoicecode());
                parameters.put("P_remark", item.getRemark());
                String sdate = item.getBusinessdate();
                if (StringUtils.isNotEmpty(sdate)) {
                    String[] darr = sdate.split("-");
                    if (darr.length >= 3) {
                        sdate = darr[0] + "年" + darr[1] + "月" + darr[2] + "日";
                    }
                }
                parameters.put("P_createdate", sdate);

                /**打印模板参数用的单据信息**/
                SalesInvoiceBill orderInfo = (SalesInvoiceBill) CommonUtils.deepCopy(item);
                orderInfo.setDetaiList(null);
                parameters.put("P_OrderInfo", orderInfo);
                //公共参数
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);
                JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdlist));
                if (null != jrprint) {
                    jrprint.setName("销售开票-" + item.getId());
                    jrlist.add(jrprint);

                    if (!isview) {
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", item.getId());
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        printIdList.add(item.getId());


                        if (StringUtils.isNotEmpty(printJob.getId())) {
                            //打印次数更新回调方法
                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", item.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", printJob.getId());
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
                            //打印回调处理
                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(printJob.getId());
                            printJobCallHandle.setClassname("salesInvoicePrintAction");
                            printJobCallHandle.setMethodname("salesInvoiceBillPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("销售开票");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("销售开票");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");//次数更新
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                }
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

            if (!isview) {
                if (printIdList.size() > 0) {
                    String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
                    if (null != printarr) {
                        canprintIds = StringUtils.join(printarr, ",");
                    }
                }
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                //printLogsb.append("申请打印的销售开票抽单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售开票编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：销售开票单打印 。");
                addPrintLogInfo("PrintHandle-salesInvoiceBill", printLogsb.toString(), null);
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
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-销售开票单打印：");
                //printLogsb.append("申请打印的销售开票抽单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售开票单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-销售开票单打印预览：");
            }
            printLogsb.append("Exception ：salesInvoiceBillPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-salesInvoiceBill", printLogsb.toString(), null);
            logger.error("销售开票单打印及预览处理异常", ex);

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
     * 销售开票打印回调
     * @param printcallback
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-16
     */
    public void salesInvoiceBillPrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesInvoiceBill", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesInvoiceBill", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的销售开票抽单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售开票抽单编号： " + id);
            try {
                SalesInvoiceBill salesInvoiceBill = salesInvoiceBillService.getPureSalesInvoiceBillPureInfo(id);
                if (null != salesInvoiceBill) {
                    salesInvoiceBill.setPrinttimes(1);
                    salesInvoiceBillService.updateOrderPrinttimes(salesInvoiceBill);
                    printLogsb.append(" 更新打印次数成功 ");
                } else {
                    printLogsb.append(" 更新打印次数成功 ");
                }
            } catch (Exception ex) {
                printLogsb.append(" 回调更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("销售开票单更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无销售开票抽单编号信息");
        }
        printLogsb.append(" 。");
        //printLogsb.append(" 更新响应来源于：销售开票抽单打印 。");
        printLogsb.append(" 打印数据来源");
        printLogsb.append("(销售开票抽单编号)：" + idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesInvoiceBill", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 销售开票打印回调
     * @param printcallback
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Nov 03, 2016
     */
    public boolean salesInvoiceBillPrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesInvoiceBill", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesInvoiceBill", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的销售开票抽单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售开票单编号： " + id);
            try {
                SalesInvoiceBill salesInvoiceBill = salesInvoiceBillService.getPureSalesInvoiceBillPureInfo(id);
                if (null != salesInvoiceBill) {
                    salesInvoiceBill.setPrinttimes(1);
                    salesInvoiceBillService.updateOrderPrinttimes(salesInvoiceBill);
                    printLogsb.append(" 更新打印次数成功 ");
                } else {
                    printLogsb.append(" 更新打印次数成功 ");
                }
                isok = true;
            } catch (Exception ex) {
                printLogsb.append(" 回调更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("销售开票单更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无销售开票抽单编号信息");
        }
        printLogsb.append(" 。");
        //printLogsb.append(" 更新响应来源于：销售开票抽单打印 。");
        printLogsb.append(" 打印数据来源");
        printLogsb.append("(销售开票单编号)：" + id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesInvoiceBill", printLogsb.toString(), null);
        return isok;
    }
}

