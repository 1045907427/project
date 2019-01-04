/**
 * @(#)CustomerPushBanlancePrintAction.java
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
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.PaymentVoucher;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.sales.service.IReceiptService;
import com.hd.agent.system.model.SysCode;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * 客户应收冲差单 
 * @author zhanghonghui
 */
public class CustomerPushBanlancePrintAction extends BaseFilesAction {

    //region 参数初始化
    private static final Logger logger = Logger.getLogger(CustomerPushBanlancePrintAction.class);

    private CustomerPushBalance customerPushBalance;

    private ICustomerPushBanlanceService customerPushBanlanceService;

    public ICustomerPushBanlanceService getCustomerPushBanlanceService() {
        return customerPushBanlanceService;
    }

    public void setCustomerPushBanlanceService(
            ICustomerPushBanlanceService customerPushBanlanceService) {
        this.customerPushBanlanceService = customerPushBanlanceService;
    }

    public CustomerPushBalance getCustomerPushBalance() {
        return customerPushBalance;
    }

    public void setCustomerPushBalance(CustomerPushBalance customerPushBalance) {
        this.customerPushBalance = customerPushBalance;
    }

    private IReceiptService receiptService;

    public IReceiptService getReceiptService() {
        return receiptService;
    }

    public void setReceiptService(IReceiptService receiptService) {
        this.receiptService = receiptService;
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
     * 客户应收冲差单打印预览
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    public String customerPushBanlancePrintView() throws Exception {
        return customerPushBanlancePrintHandle(true);
    }

    /**
     * 客户应收冲差单打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    public String customerPushBanlancePrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            customerPushBanlancePrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return customerPushBanlancePrintHandle(isview);
        }
        return null;
    }


    /**
     * 客户应收冲差单打印及预览处理
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    private String customerPushBanlancePrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "客户应收冲差单打印-" + CommonUtils.getDataNumberSeconds());
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
        SysUser sysUser = getSysUser();
        String canprintIds = "";
        Map printTempletSettingFistMap = new HashMap();
        try {
            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("客户应收冲差单打印");
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
                ajaxResultMap.put("printname", "客户应收冲差单打印任务-" + printJob.getId());
            }

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            Map queryMap = new HashMap();
            queryMap.put("idarrs", idarrs.trim());
            queryMap.put("isview", isview);
            queryMap.put("jrList", jrlist);
            queryMap.put("printTempletSettingFistMap", printTempletSettingFistMap);
            //打印任务编号
            queryMap.put("backHandleJobId", printJob.getId());

            String querypushtype = request.getParameter("querypushtype");
            if ("2".equals(querypushtype)) {
                Map queryCountMap = new HashMap();
                queryCountMap.put("idarrs", idarrs);
                queryCountMap.put("notisinvoicearr", "2");//是否有有普通冲差
                int icount = customerPushBanlanceService.getCustomerPushBanlanceCountBy(queryCountMap);
                if (icount > 0) {
                    //普通冲差单
                    getCustomerPushBanlancePrintData(queryMap);
                }
                queryCountMap.clear();
                queryCountMap.put("idarrs", idarrs);
                queryCountMap.put("isinvoice", "2");//是否有回单冲差
                icount = customerPushBanlanceService.getCustomerPushBanlanceCountBy(queryCountMap);
                if (icount > 0) {
                    //回单冲差单
                    getCustomerReceiptPushBanlancePrintData(queryMap);
                }
            } else {
                //普通冲差单
                getCustomerPushBanlancePrintData(queryMap);
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
            printTempletSettingFistMap = (Map) queryMap.get("printTempletSettingFistMap");
            if (null == printTempletSettingFistMap || printTempletSettingFistMap.size() == 0) {
                printTempletSettingFistMap = new HashMap();
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
                //输出打印报表
                Map<String, Object> reportMap = new HashMap<String, Object>();
                reportMap.put("response", response);
                reportMap.put("jreportList", jrlist);
                reportMap.put("isview", isview);
                reportMap.put("viewtype", viewtype);
                reportMap.put("PDFOWNERPASSWORD", getSysParamValue("PDFOWNERPASSWORD"));
                JasperReportUtils.renderJasperReport(reportMap);
                return null;
            }

        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-客户应收冲差单打印：");
                //printLogsb.append("申请打印的客户应收冲差单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("客户应收冲差单编号： " + idarrs);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-客户应收冲差单预览：");
            }
            printLogsb.append("Exception ：customerPushBanlancePrintHandle()>>>>>>>>>>>>>>>>异常信息 " + ex.getMessage());
            addPrintLogInfo("PrintHandle-customerPushBanlance", printLogsb.toString(), null);

            logger.error("打印申请-客户应收冲差单打印及预览处理异常", ex);

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
     * 客户冲差单打印
     * @return
     * @author zhanghonghui
     * @date 2015年11月4日
     */
    public void getCustomerPushBanlancePrintData(Map queryMap) throws Exception {
        //需要打印的编号
        String idarrs = (String) queryMap.get("idarrs");
        //是否预览
        Boolean isview = (Boolean) queryMap.get("isview");
        if (null == isview) {
            isview = false;
        }

        String notisinvoicearr = (String) queryMap.get("notisinvoicearr");
        String canprintIds = "";

        List<JasperPrint> jrList = null;
        if (queryMap.containsKey("jrList")) {
            jrList = (List<JasperPrint>) queryMap.get("jrList");
        }
        if (null == jrList) {
            jrList = new ArrayList<JasperPrint>();
        }

        Map printTempletSettingFistMap = (Map) queryMap.get("printTempletSettingFistMap");
        if (null == printTempletSettingFistMap || printTempletSettingFistMap.size() == 0) {
            printTempletSettingFistMap = new HashMap();
        }
        //打印模板
        String templetid = request.getParameter("templetid");

        //打印任务编号
        String backHandleJobId = (String) queryMap.get("backHandleJobId");


        Map map = new HashMap();
        SysUser sysUser = getSysUser();
        map.put("idarrs", idarrs);
        map.put("statusarr", "3,4");
        map.put("showPrintIdDesc", "1");    //使用id编号从大到小
        if (null != notisinvoicearr && !"".equals(notisinvoicearr.trim())) {
            map.put("notisinvoicearr", notisinvoicearr.trim()); //排除之外
        }
        Map<String, List<CustomerPushBalance>> printData = customerPushBanlanceService.showCustomerPushBanlancePrintListBy(map);
        if (null == printData || printData.size() == 0) {
            return;
        }

        //获取打印模板
        Map templetQueryMap = new HashMap();
        templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
        templetQueryMap.put("code", "account_customerpushbanlance");

        if (null != templetid && !"".equals(templetid)) {
            templetQueryMap.put("templetid", templetid);
        }
        Map templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);
        if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
            printTempletSettingFistMap = new HashMap();
            printTempletSettingFistMap.putAll(templetResultMap);
            queryMap.put("printTempletSettingFistMap", printTempletSettingFistMap);

        }
        //打印模板文件
        String printTempletFile = (String) templetResultMap.get("printTempletFile");
        if (null == printTempletFile || "".equals(printTempletFile.trim())) {
            return;
        }
        //打印内容排序策略
        String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

        PrintTemplet printTemplet = new PrintTemplet();
        if (templetResultMap.containsKey("printTempletInfo")) {
            printTemplet = (PrintTemplet) templetResultMap.get("printTempletInfo");
            if (null == printTemplet) {
                printTemplet = new PrintTemplet();
                templetResultMap.put("printTempletInfo", printTemplet);
            }
        }
        //String reportModelFile = servletContext.getRealPath("/ireport/account_customerpushbanlance/account_customerpushbanlance.jasper");
        JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
        Map parameters = null;

        List<String> printIdList = new ArrayList<String>();

        Iterator<Entry<String, List<CustomerPushBalance>>> iter = printData.entrySet().iterator();

        Entry<String, List<CustomerPushBalance>> entry = null;

        while (iter.hasNext()) {
            printIdList = new ArrayList<String>();
            entry = iter.next();
            if (null == entry) {
                continue;
            }
            List<CustomerPushBalance> printList = entry.getValue();
            if (null == printList || printList.size() == 0) {
                continue;
            }
            //进行打印内容明细排序
            if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                Collections.sort(printList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
            } else {
                Collections.sort(printList, ListSortLikeSQLComparator.createComparator("id desc"));
            }
            String businessdate = "";
            String cusomterid = "";
            String printid = "";
            for (CustomerPushBalance item : printList) {
                printid = "";
                if (StringUtils.isNotEmpty(item.getPushtype())) {
                    SysCode sysCode = getBaseSysCodeService().showSysCodeInfo(item.getPushtype(), "pushtypeprint");
                    if (null != sysCode) {
                        item.setPushtypename(sysCode.getCodename());
                    } else {
                        item.setPushtypename("其他冲差");
                    }
                } else {
                    item.setPushtypename("其他冲差");
                }
                if (!isview) {
                    printIdList.add(item.getId());
                }
                if (StringUtils.isEmpty(businessdate) || StringUtils.isEmpty(cusomterid)) {
                    businessdate = item.getBusinessdate();
                    cusomterid = item.getCustomerid();
                }
                if (printList.size() == 1) {
                    printid = item.getId();
                }
            }
            parameters = new HashMap();

            parameters.put("P_PrintUser", sysUser.getUsername());
            parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(businessdate));
            parameters.put("P_PrintDate", new Date()); //打印时间
            if (null != sysUser) {
                Personnel curPersonnel = sysUserConnectePersonnelInfo(sysUser.getUserid());
                if (null != curPersonnel) {
                    parameters.put("P_PrintUserTel", curPersonnel.getTelphone());
                }
            }
            //公共参数
            parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
            agprintServiceImpl.setTempletCommonParameter(parameters);
            JasperPrint jrprint =
                    JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(printList));
            if (!isview) {
                if (printIdList.size() > 0) {
                    String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
                    canprintIds = StringUtils.join(printarr, ",");
                }
            }
            if (null != jrprint) {
                if (printList.size() == 1) {
                    jrprint.setName("客户应收冲差单-" + printid);
                } else {
                    jrprint.setName("客户应收冲差单-" + cusomterid + "-" + businessdate);
                }

                if (!isview) {
                    Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                    callbackparamMap.put("id", canprintIds);
                    callbackparamMap.put("callback", "updateprinttimes");
                    callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                    String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                    jrprint.setProperty("agprint_callback_params", callbackparams);


                    if (backHandleJobId != null) {

                        Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                        //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                        //实际打印的单据号
                        datahtmlparamMap.put("printOrderId", canprintIds);
                        //打印任务编号
                        datahtmlparamMap.put("printJobId", backHandleJobId);
                        //页面点击时的单据号
                        datahtmlparamMap.put("printSourceOrderId", "");
                        String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                        jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                        PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                        printJobCallHandle.setJobid(backHandleJobId);
                        printJobCallHandle.setClassname("customerPushBanlancePrintAction");
                        printJobCallHandle.setMethodname("customerPushBanlancePrintReflectCallBackHandle");
                        printJobCallHandle.setMethodparam(callbackparams);
                        printJobCallHandle.setPages(jrprint.getPages().size());
                        printJobCallHandle.setPrintorderid(canprintIds);
                        printJobCallHandle.setPrintordername("发货单");
                        printJobCallHandle.setSourceorderid(canprintIds);
                        printJobCallHandle.setSourceordername("发货单");
                        printJobCallHandle.setStatus("0");
                        printJobCallHandle.setType("1");
                        agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                    }
                }
                jrList.add(jrprint);
            }


            if (!isview) {
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                //printLogsb.append("申请打印的客户应收冲差单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("客户应收冲差单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：客户应收冲差单打印。");
                addPrintLogInfo("PrintHandle-customerPushBanlance", printLogsb.toString(), null);
            }
        }
    }

    /**
     * 客户冲差单，回单冲差打印
     * @return
     * @author zhanghonghui
     * @date 2015年11月4日
     */
    public void getCustomerReceiptPushBanlancePrintData(Map queryMap) throws Exception {
        //需要打印的编号
        String idarrs = (String) queryMap.get("idarrs");
        //是否预览
        Boolean isview = (Boolean) queryMap.get("isview");
        if (null == isview) {
            isview = false;
        }
        //传入打印模板List
        List<JasperPrint> jrList = null;
        if (queryMap.containsKey("jrList")) {
            jrList = (List<JasperPrint>) queryMap.get("jrList");
        }
        if (null == jrList) {
            jrList = new ArrayList<JasperPrint>();
        }

        Map printTempletSettingFistMap = (Map) queryMap.get("printTempletSettingFistMap");
        if (null == printTempletSettingFistMap || printTempletSettingFistMap.size() == 0) {
            printTempletSettingFistMap = new HashMap();
        }
        //打印模板
        String templetid = request.getParameter("hdtempletid");
        String canprintIds = "";
        //打印任务编号
        String backHandleJobId = (String) queryMap.get("backHandleJobId");

        Map map = new HashMap();
        SysUser sysUser = getSysUser();
        map.put("cxidarr", idarrs);
        map.put("cxstatusarr", "3,4");
        Map<String, List<Map>> printData = receiptService.getReceiptListForHDCXByMap(map);
        if (null == printData || printData.size() == 0) {
            return;
        }
        //获取打印模板
        Map templetQueryMap = new HashMap();
        templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
        templetQueryMap.put("code", "account_hdcustomerpushbanlance");

        if (null != templetid && !"".equals(templetid)) {
            templetQueryMap.put("templetid", templetid);
        }
        Map templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);

        if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
            printTempletSettingFistMap = new HashMap();
            printTempletSettingFistMap.putAll(templetResultMap);
            queryMap.put("printTempletSettingFistMap", printTempletSettingFistMap);
        }
        //打印模板文件
        String printTempletFile = (String) templetResultMap.get("printTempletFile");
        if (null == printTempletFile || "".equals(printTempletFile.trim())) {
            return;
        }
        //打印内容排序策略
        String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

        PrintTemplet printTemplet = new PrintTemplet();
        if (templetResultMap.containsKey("printTempletInfo")) {
            printTemplet = (PrintTemplet) templetResultMap.get("printTempletInfo");
            if (null == printTemplet) {
                printTemplet = new PrintTemplet();
                templetResultMap.put("printTempletInfo", printTemplet);
            }
        }
        //String reportModelFile = servletContext.getRealPath("/ireport/account_customerpushbanlance/account_customerpushbanlance.jasper");
        JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
        Map parameters = null;

        Iterator<Entry<String, List<Map>>> iter = printData.entrySet().iterator();

        Entry<String, List<Map>> entry = null;

        while (iter.hasNext()) {
            Map<String, String> canPrintIdMap = new HashMap<String, String>();
            List<String> printIdList = new ArrayList<String>();
            entry = iter.next();
            if (null == entry) {
                continue;
            }
            List<Map> printList = entry.getValue();
            if (null == printList || printList.size() == 0) {
                continue;
            }

            //进行打印内容明细排序
            if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                Collections.sort(printList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
            } else {
                //Collections.sort(printList, ListSortLikeSQLComparator.createComparator("cxid desc"));
            }
            String businessdate = "";
            String cusomterid = "";
            String printid = "";
            for (Map item : printList) {
                printid = "";

                if (!isview) {
                    //冲差编号
                    String tmpStr = (String) item.get("cxid");
                    if (null != tmpStr && !"".equals(tmpStr.trim()) && !canPrintIdMap.containsKey(tmpStr.trim())) {
                        printIdList.add(tmpStr.trim());
                        canPrintIdMap.put(tmpStr.trim(), tmpStr.trim());
                    }
                    //
                    printid = (String) item.get("billid");
                    if (null == printid) {
                        printid = "";
                    }
                }
            }
            parameters = new HashMap();

            //公共参数
            parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
            agprintServiceImpl.setTempletCommonParameter(parameters);

            parameters.put("P_PrintUser", sysUser.getUsername());
            parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(businessdate));
            parameters.put("P_PrintDate", new Date()); //打印时间
            if (null != sysUser) {
                Personnel curPersonnel = sysUserConnectePersonnelInfo(sysUser.getUserid());
                if (null != curPersonnel) {
                    parameters.put("P_PrintUserTel", curPersonnel.getTelphone());
                }
            }
            JasperPrint jrprint =
                    JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(printList));
            if (!isview) {

                if (printIdList.size() > 0) {
                    String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
                    canprintIds = StringUtils.join(printarr, ",");
                }
            }
            if (null != jrprint) {
                jrprint.setName("客户应收冲差单-回单冲差-" + printid);

                if (!isview) {
                    Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                    callbackparamMap.put("id", canprintIds);
                    callbackparamMap.put("callback", "updateprinttimes");
                    callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                    String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                    jrprint.setProperty("agprint_callback_params", callbackparams);


                    if (StringUtils.isNotEmpty(backHandleJobId)) {
                        //打印次数更新回调方法
                        Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                        //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                        //实际打印的单据号
                        datahtmlparamMap.put("printOrderId", canprintIds);
                        //打印任务编号
                        datahtmlparamMap.put("printJobId", backHandleJobId);
                        //页面点击时的单据号
                        datahtmlparamMap.put("printSourceOrderId", "");
                        String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                        jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
                        //打印回调处理
                        PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                        printJobCallHandle.setJobid(backHandleJobId);
                        printJobCallHandle.setClassname("customerPushBanlancePrintAction");
                        printJobCallHandle.setMethodname("storageOrderblankPrintReflectCallBackHandle");
                        printJobCallHandle.setMethodparam(callbackparams);
                        printJobCallHandle.setPages(jrprint.getPages().size());
                        printJobCallHandle.setPrintorderid(canprintIds);
                        printJobCallHandle.setPrintordername("客户冲差单");
                        printJobCallHandle.setSourceorderid(canprintIds);
                        printJobCallHandle.setSourceordername("客户冲差单");
                        printJobCallHandle.setStatus("0");
                        printJobCallHandle.setType("1");//次数更新
                        agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                    }
                }
                jrList.add(jrprint);
            }


            if (!isview) {
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                //printLogsb.append("申请打印的客户应收冲差单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("回单冲差的客户应收冲差单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：客户应收冲差单打印。");
                addPrintLogInfo("PrintHandle-customerPushBanlance", printLogsb.toString(), null);
            }
        }
    }


    /**
     * 客户应收冲差单打印预览
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    public String customerPushBanlanceByInvoicePrintView() throws Exception {
        return customerPushBanlancePrintByInvoiceHandle(true);
    }

    /**
     * 客户应收冲差单打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    public String customerPushBanlanceByInvoicePrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            customerPushBanlancePrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return customerPushBanlancePrintByInvoiceHandle(isview);
        }
        return null;
    }


    /**
     * 销售发票打印-客户应收冲差单打印及预览处理
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    private String customerPushBanlancePrintByInvoiceHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "发货单-配货单打印-" + CommonUtils.getDataNumberSeconds());
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
        SysUser sysUser = getSysUser();
        String canprintIds = "";
        Map printTempletSettingFistMap = null;
        try {

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("销售发票打印-客户应收冲差单打印");
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
                ajaxResultMap.put("printname", "销售发票打印-客户应收冲差单打印-" + printJob.getId());
            }
            Map map = new HashMap();
            map.put("invoiceid", idarrs);
            map.put("statusarr", "3,4");
            List<CustomerPushBalance> list = customerPushBanlanceService.showCustomerPushBanlanceListBy(map);

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
            templetQueryMap.put("code", "account_customerpushbanlance");

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
            //第一个可用的打印模板配置信息
            if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                printTempletSettingFistMap = new HashMap();
                printTempletSettingFistMap.putAll(templetResultMap);
            }
            //打印内容排序策略
            String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

            PrintTemplet printTemplet = new PrintTemplet();
            if (templetResultMap.containsKey("printTempletInfo")) {
                printTemplet = (PrintTemplet) templetResultMap.get("printTempletInfo");
                if (null == printTemplet) {
                    printTemplet = new PrintTemplet();
                    templetResultMap.put("printTempletInfo", printTemplet);
                }
            }
            //String reportModelFile = servletContext.getRealPath("/ireport/account_customerpushbanlance/account_customerpushbanlance.jasper");
            JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();
            CustomerPushBalance printData = new CustomerPushBalance();

            for (CustomerPushBalance item : list) {
                if (StringUtils.isEmpty(printData.getId())) {
                    printData.setId(item.getInvoiceid());
                }
                if (StringUtils.isEmpty(printData.getBusinessdate())) {
                    printData.setBusinessdate(item.getBusinessdate());
                }
                if (StringUtils.isEmpty(printData.getPushtypename())) {
                    printData.setPushtypename("折扣");
                }
                if (StringUtils.isEmpty(printData.getCustomerid())) {
                    printData.setCustomerid(item.getCustomerid());
                }
                if (StringUtils.isEmpty(printData.getCustomername())) {
                    printData.setCustomername(item.getCustomername());
                }
                if (StringUtils.isEmpty(printData.getInvoiceid())) {
                    printData.setInvoiceid(item.getInvoiceid());
                }
                if (null == printData.getCustomerInfo() && null != item.getCustomerInfo()) {
                    printData.setCustomerInfo(item.getCustomerInfo());
                }
                if (null == printData.getAmount()) {
                    printData.setAmount(item.getAmount());
                } else if (null != item.getAmount()) {
                    printData.setAmount(printData.getAmount().add(item.getAmount()));
                }
                if (StringUtils.isEmpty(printData.getAddusername())) {
                    printData.setAddusername(item.getAddusername());
                }
                if (!isview) {
                    printIdList.add(item.getId());
                }
            }
            if (!isview) {
                if (printIdList.size() > 0) {
                    String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
                    canprintIds = StringUtils.join(printarr, ",");
                }
            }
            List<CustomerPushBalance> tmplist = new ArrayList<CustomerPushBalance>();
            tmplist.add(printData);
            parameters = new HashMap();
            //公共参数
            parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
            agprintServiceImpl.setTempletCommonParameter(parameters);

            parameters.put("P_PrintUser", sysUser.getUsername());
            parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(printData.getBusinessdate()));
            parameters.put("P_PrintDate", new Date()); //主项目，套打，打印日期就是业务日期
            if (null != sysUser) {
                Personnel curPersonnel = sysUserConnectePersonnelInfo(sysUser.getUserid());
                if (null != curPersonnel) {
                    parameters.put("P_PrintUserTel", curPersonnel.getTelphone());
                }
            }

            //进行打印内容明细排序
            if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                Collections.sort(tmplist, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
            } else {
                Collections.sort(tmplist, ListSortLikeSQLComparator.createComparator("id desc"));
            }
            JasperPrint jrprint =
                    JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmplist));
            if (null != jrprint) {
                jrprint.setName("客户应收冲差单-发票编号-" + idarrs);

                if (!isview) {

                    Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                    callbackparamMap.put("id", canprintIds);
                    callbackparamMap.put("callback", "updateprinttimesbyinvoice");
                    callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                    String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                    jrprint.setProperty("agprint_callback_params", callbackparams);


                    if (StringUtils.isNotEmpty(printJob.getId())) {
                        //打印次数更新回调方法
                        Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                        //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                        //实际打印的单据号
                        datahtmlparamMap.put("printOrderId", canprintIds);
                        //页面点击时的单据号
                        datahtmlparamMap.put("printSourceOrderId", "");
                        String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                        jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
                        //打印回调处理
                        PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                        printJobCallHandle.setJobid(printJob.getId());
                        printJobCallHandle.setClassname("customerPushBanlancePrintAction");
                        printJobCallHandle.setMethodname("customerPushBanlancePrintReflectCallBackHandle");
                        printJobCallHandle.setMethodparam(callbackparams);
                        printJobCallHandle.setPages(jrprint.getPages().size());
                        printJobCallHandle.setPrintorderid(canprintIds);
                        printJobCallHandle.setPrintordername("客户应收冲差单");
                        printJobCallHandle.setSourceorderid(canprintIds);
                        printJobCallHandle.setSourceordername("客户应收冲差单");
                        printJobCallHandle.setStatus("0");
                        printJobCallHandle.setType("1");//次数更新
                        agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                    }
                }
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

            if (!isview) {
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请-客户应收冲差单打印：");
                //printLogsb.append("申请打印的客户应收冲差单发票编号："+invoiceid);
                //printLogsb.append(" 。");
                printLogsb.append("客户应收冲差单编号： " + canprintIds);
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-customerPushBanlance", printLogsb.toString(), null);
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
                //输出打印报表
                Map<String, Object> reportMap = new HashMap<String, Object>();
                reportMap.put("response", response);
                reportMap.put("jreportList", jrlist);
                reportMap.put("isview", isview);
                reportMap.put("viewtype", viewtype);
                reportMap.put("PDFOWNERPASSWORD", getSysParamValue("PDFOWNERPASSWORD"));
                JasperReportUtils.renderJasperReport(reportMap);
                return null;
            }

        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-客户应收冲差单打印：");
                //printLogsb.append("申请打印的客户应收冲差单发票编号："+invoiceid);
                //printLogsb.append(" 。");
                printLogsb.append("客户应收冲差单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-客户应收冲差单打印预览：");
            }
            printLogsb.append("Exception ：customerPushBanlancePrintByInvoiceHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-customerPushBanlance", printLogsb.toString(), null);

            logger.error("客户应收冲差单打印及预览处理异常", ex);

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
     * 打印回调
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public void customerPushBanlancePrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-customerPushBanlance", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-customerPushBanlance", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        String callback = (String) map.get("callback");
        if (null != id && !"".equals(id.trim())) {
            //if("updateprinttimesbyinvoice".equals(callback)){
            //	printLogsb.append("申请打印的按发票打印客户应收冲差单的发票编号："+idarrs);
            //}else{
            //	printLogsb.append("申请打印的客户应收冲差单编号："+idarrs);
            //}
            //printLogsb.append(" 。");
            printLogsb.append("客户应收冲差单编号： " + id);
            try {
                Map<String, String> queryMap = new HashMap<String, String>();
                queryMap.put("idarrs", id);
                queryMap.put("statusarr", "3,4");
                List<CustomerPushBalance> updateList = customerPushBanlanceService.showCustomerPushBanlanceListBy(queryMap);
                if (null != updateList) {
                    customerPushBanlanceService.updateOrderPrinttimes(updateList);
                    printLogsb.append(" 更新打印次数成功");
                } else {
                    printLogsb.append(" 更新打印次数失败。");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("客户应收冲差单打印更新打印次数失败", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append(" 回调参数中无客户应收冲差单编号信息。");
        }
        printLogsb.append(" 。");
        //printLogsb.append(" 更新响应来源于：客户应收冲差单打印。");
        printLogsb.append(" 打印数据来源");
        if ("updateprinttimesbyinvoice".equals(callback)) {
            printLogsb.append("(按发票打印客户应收冲差单的发票编号)：" + idarrs);
        } else {
            printLogsb.append("(客户应收冲差单编号)：" + idarrs);
        }
        printLogsb.append(" 。");
        /*
		printLogsb.append("回调参数："+printcallback);
		printLogsb.append(" 。");
		String urlparam=request.getQueryString();
		String callbackurl=request.getRequestURI();
		if(StringUtils.isNotEmpty(callbackurl)){
			printLogsb.append("回调地址："+callbackurl);
			printLogsb.append(" 。");
		}
		if(StringUtils.isNotEmpty(urlparam)){
			printLogsb.append("回调地址参数："+urlparam);
			printLogsb.append(" 。");
		}
		*/
        addPrintLogInfo("PrintCallHandle-customerPushBanlance", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 打印回调-反射调用
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public boolean customerPushBanlancePrintReflectCallBackHandle(String printcallback) throws Exception {
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageDeliveryOrder", printLogsb.toString(), null);
            return false;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageDeliveryOrder", printLogsb.toString(), null);
            return false;
        }
        String id = (String) map.get("id");
        String callback = (String) map.get("callback");
        boolean isok = false;
        if (null != id && !"".equals(id.trim())) {
            //if("updateprinttimesbyinvoice".equals(callback)){
            //	printLogsb.append("申请打印的按发票打印客户应收冲差单的发票编号："+idarrs);
            //}else{
            //	printLogsb.append("申请打印的客户应收冲差单编号："+idarrs);
            //}
            //printLogsb.append(" 。");
            printLogsb.append("客户应收冲差单编号： " + id);
            try {
                Map<String, String> queryMap = new HashMap<String, String>();
                queryMap.put("idarrs", id);
                queryMap.put("statusarr", "3,4");
                List<CustomerPushBalance> updateList = customerPushBanlanceService.showCustomerPushBanlanceListBy(queryMap);
                if (null != updateList) {
                    customerPushBanlanceService.updateOrderPrinttimes(updateList);
                    printLogsb.append(" 更新打印次数成功");
                    isok = true;
                } else {
                    printLogsb.append(" 更新打印次数失败。");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("客户应收冲差单打印更新打印次数失败", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append(" 回调参数中无客户应收冲差单编号信息。");
        }
        printLogsb.append(" 。");
        //printLogsb.append(" 更新响应来源于：客户应收冲差单打印。");
        printLogsb.append(" 打印数据来源");
        if ("updateprinttimesbyinvoice".equals(callback)) {
            printLogsb.append("(按发票打印客户应收冲差单的发票编号)：" + id);
        } else {
            printLogsb.append("(客户应收冲差单编号)：" + id);
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-customerPushBanlance", printLogsb.toString(), null);
        return isok;
    }

}

