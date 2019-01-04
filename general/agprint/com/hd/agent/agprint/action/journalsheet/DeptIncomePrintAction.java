package com.hd.agent.agprint.action.journalsheet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.IPrintTempletService;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.journalsheet.model.DeptIncome;
import com.hd.agent.journalsheet.service.IDeptIncomeService;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class DeptIncomePrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(DeptIncomePrintAction.class);
    private IDeptIncomeService deptIncomeService;

    public IDeptIncomeService getDeptIncomeService() {
        return deptIncomeService;
    }

    public void setDeptIncomeService(IDeptIncomeService deptIncomeService) {
        this.deptIncomeService = deptIncomeService;
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
     * 部门收入单打印预览
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Jan 11, 2016
     */
    public String deptIncomePrintView() throws Exception {
        return deptIncomePrintHandle(true);
    }

    /**
     * 部门收入单打印
     *
     * @throws Exception
     * @author limin
     * @date Jan 11, 2016
     */
    @UserOperateLog(key = "deptIncome-Storage", type = 0)
    public String deptIncomePrint() throws Exception {

        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");

        if (null != printcallback && !"".equals(printcallback)) {

            deptIncomePrintCallBackHandle(printcallback);

        } else {

            boolean view = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {

                view = false;
            } else {

                view = true;
            }

            return deptIncomePrintHandle(view);
        }
        return null;
    }

    /**
     * 打印及预览处理
     *
     * @param isview
     * @throws Exception
     * @author limin
     * @date Jan 11, 2016
     */
    private String deptIncomePrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "部门收入用单打印-" + CommonUtils.getDataNumberSeconds());
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
        Map printTempletSettingFistMap = null;
        String templetid = request.getParameter("templetid");
        SysUser sysUser = getSysUser();
        String canprintIds = "";
        try {

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("部门收入用单打印");
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
                ajaxResultMap.put("printname", "部门收入用单打印任务-" + printJob.getId());
            }
            String printlimit = getPrintLimitInfo();
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            map.put("noDataSql", "1");
            List<DeptIncome> list = deptIncomeService.showDeptIncomeListBy(map);
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
            //获取打印模板
            JasperReport jreport = null;

            templetQueryMap.clear();
            templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
            templetQueryMap.put("code", "journalsheet_deptincome");
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
            //打印内容排序策略
            String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

            //进行打印内容明细排序
            if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                Collections.sort(list, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
            }
            //String reportModelFile = servletContext.getRealPath("/ireport/journalsheet_deptincome/journalsheet_deptincome.jasper");
            jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();

            Map parameters = new HashMap();

            parameters.put("P_PrintUser", sysUser.getName());
            parameters.put("P_PrintUserTel", sysUser.getTelphone());
                /*共用参数*/
            parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
            agprintServiceImpl.setTempletCommonParameter(parameters);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");

            JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(list));
            if (null != jrprint) {
                jrprint.setName("部门收入单-" + sdf.format(date));
                jrlist.add(jrprint);

                if (!isview) {
                    Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                    callbackparamMap.put("idarrs", idarrs);
                    callbackparamMap.put("callback", "updateprinttimes");
                    callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                    String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                    jrprint.setProperty("agprint_callback_params", callbackparams);


                    if (StringUtils.isNotEmpty(printJob.getId())) {
                        //打印次数更新回调方法
                        Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                        //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                        //实际打印的单据号
                        datahtmlparamMap.put("printOrderId", idarrs);
                        //打印任务编号
                        datahtmlparamMap.put("printJobId", printJob.getId());
                        //页面点击时的单据号
                        datahtmlparamMap.put("printSourceOrderId", "");
                        String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                        jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
                        //打印回调处理
                        PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                        printJobCallHandle.setJobid(printJob.getId());
                        printJobCallHandle.setClassname("deptIncomePrintAction");
                        printJobCallHandle.setMethodname("deptIncomePrintReflectCallBackHandle");
                        printJobCallHandle.setMethodparam(callbackparams);
                        printJobCallHandle.setPages(jrprint.getPages().size());
                        printJobCallHandle.setPrintorderid(idarrs);
                        printJobCallHandle.setPrintordername("部门收入用单打印");
                        printJobCallHandle.setSourceorderid(idarrs);
                        printJobCallHandle.setSourceordername("部门收入用单打印");
                        printJobCallHandle.setStatus("0");
                        printJobCallHandle.setType("1");//次数更新
                        agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
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
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                //printLogsb.append("申请打印的部门收入单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append(" 部门收入单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称： 部门收入单打印。");
                addPrintLogInfo("PrintHandle-deptdailycost", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-部门收入单打印异常：");
                //printLogsb.append("申请打印的部门收入单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("部门收入单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-部门收入单打印预览异常：");
            }
            printLogsb.append("Exception ：deptIncomePrint()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-deptIncomePrint", printLogsb.toString(), null);
            logger.error("部门收入单打印及预览异常", ex);

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
     *
     * @param printcallback
     * @throws Exception
     * @author limin
     * @date Jan 12, 2016
     */
    public void deptIncomePrintCallBackHandle(String printcallback) throws Exception {
        String orgidarrs = request.getParameter("idarrs");
        if (null == orgidarrs) {
            orgidarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deptincome", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deptincome", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String idarrs = (String) map.get("idarrs");
        if (idarrs == null) {
            idarrs = "";
        }
        String[] idArr = idarrs.trim().split(",");
        if (null != idArr && idArr.length > 0) {
            //printLogsb.append("申请打印的部门收入单编号："+idarrs);
            //printLogsb.append(" 。");

            for (String id : idArr) {
                printLogsb.append("部门收入单编号： " + id);
                try {
                    DeptIncome deptIncome = deptIncomeService.getDeptIncomePureInfo(id);
                    if (null != deptIncome) {
                        deptIncomeService.updateDeptIncomePrinttimes(deptIncome);
                        printLogsb.append(" 更新打印次数成功");
                    } else {
                        printLogsb.append(" 更新打印次数失败");
                    }

                } catch (Exception ex) {
                    printLogsb.append(" 更新打印次数失败。");
                    printLogsb.append(" 异常信息：" + ex.getMessage());
                }
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无部门收入单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 打印数据来源(部门收入单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-deptincome", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 打印反射回调
     *
     * @param printcallback
     * @throws Exception
     * @author limin
     * @date Jan 12, 2016
     */
    public boolean deptIncomePrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deptincome", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deptincome", printLogsb.toString(), null);
            return isok;
        }
        String idarrs = (String) map.get("idarrs");
        if (idarrs == null) {
            idarrs = "";
        }
        String[] idArr = idarrs.trim().split(",");
        if (null != idArr && idArr.length > 0) {
            //printLogsb.append("申请打印的部门收入单编号："+idarrs);
            //printLogsb.append(" 。");

            for (String id : idArr) {
                printLogsb.append("部门收入单编号： " + id);
                try {
                    DeptIncome deptIncome = deptIncomeService.getDeptIncomePureInfo(id);
                    if (null != deptIncome) {
                        deptIncomeService.updateDeptIncomePrinttimes(deptIncome);
                        printLogsb.append(" 更新打印次数成功");
                        isok = true;
                    } else {
                        printLogsb.append(" 更新打印次数失败");
                    }

                } catch (Exception ex) {
                    printLogsb.append(" 更新打印次数失败。");
                    printLogsb.append(" 异常信息：" + ex.getMessage());
                    logger.error("部门收入单更新打印次数异常", ex);
                    isok = false;
                }
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无部门收入单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 打印数据来源(部门收入单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-deptincome", printLogsb.toString(), null);
        return isok;
    }
}
