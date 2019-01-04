package com.hd.agent.agprint.action.storage;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.storage.model.CheckList;
import com.hd.agent.storage.model.CheckListDetail;
import com.hd.agent.storage.service.ICheckListService;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

/**
 * 盘点单
 *
 * @author panxiaoxiao
 */
public class CheckListPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(CheckListPrintAction.class);

    private ICheckListService checkListService;

    public ICheckListService getCheckListService() {
        return checkListService;
    }

    public void setCheckListService(ICheckListService checkListService) {
        this.checkListService = checkListService;
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
     * 盘点单打印预览
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-11-03
     */
    public String checkListPrintView() throws Exception {
        return checkListPrintHandle(true);
    }

    /**
     * 盘点单打印
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-11-03
     */
    @UserOperateLog(key = "Reportprint", type = 0)
    public String checkListPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            checkListPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return checkListPrintHandle(isview);
        }
        return null;
    }

    /**
     * 盘点单打印及预览处理
     *
     * @param isview
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-11-03
     */
    private String checkListPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "盘点单打印-" + CommonUtils.getDataNumberSeconds());
        String id = request.getParameter("id");
        if (null == id || "".equals(id.trim())) {
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
                printJob.setJobname("盘点单打印");
                printJob.setOrderidarr(id);
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
                ajaxResultMap.put("printname", "盘点单打印任务-" + printJob.getId());
            }

            Map map = new HashMap();
            if (!isview) {
                String printlimit = getPrintLimitInfo();
                map.put("notphprint", printlimit);
            }
            map.put("idarrs", id);
            map.put("showdetail", "1");
            Map parameters = null;
            Map infomap = checkListService.getCheckListInfo(id);
            if (!infomap.isEmpty()) {
                CheckList checkList = (CheckList) infomap.get("checkList");
                List<CheckListDetail> detailList = (List<CheckListDetail>) infomap.get("checkListDetail");
                if (detailList == null || detailList.size() == 0) {
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
                templetQueryMap.put("code", "storage_checklist");
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
                        ajaxResultMap.put("msg", "未找到相关配货单打印模板");
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
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                } else {
                    // 将盘点单明细列表排序 先按品牌排序,再按商品编号排序
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator("brandid asc,goodsid asc"));
                }

                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));

                List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
                List<String> printIdList = new ArrayList<String>();
                if (!isview) {
                    checkList.setPrinttimes(checkList.getPrinttimes() + 1);
                }

                parameters = new HashMap();
                if (null != checkList) {
                    parameters.put("P_OrderInfo", checkList);
                }
                    /*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);
                JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));
                if (null != jrprint) {
                    if (!isview) {
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", checkList.getId());
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("rand", CommonUtils.getRandomWithTime()); // 随机数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        checkList.setPrinttimes(null);
                        printIdList.add(checkList.getId());

                        if (StringUtils.isNotEmpty(printJob.getId())) {
                            //打印次数更新回调方法
                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", checkList.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", printJob.getId());
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", checkList.getId());
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(printJob.getId());
                            printJobCallHandle.setClassname("checkListPrintAction");
                            printJobCallHandle.setMethodname("checkListPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(checkList.getId());
                            printJobCallHandle.setPrintordername("盘点单");
                            printJobCallHandle.setSourceorderid(checkList.getId());
                            printJobCallHandle.setSourceordername("盘点单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrprint.setName("盘点单-" + checkList.getId());
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

                    if (printIdList.size() > 0) {
                        String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
                        canprintIds = StringUtils.join(printarr, ",");
                    }
                    StringBuilder printLogsb = new StringBuilder();
                    printLogsb.append("打印申请：");
                    // printLogsb.append("申请打印仓库发货单编号："+idarrs);
                    // printLogsb.append(" 。");
                    printLogsb.append("盘点单编号： " + canprintIds);
                    printLogsb.append(" 。");
                    printLogsb.append(" 操作名称：在盘点单打印");
                    printLogsb.append(" 。");
                    addPrintLogInfo("PrintHandle-checkList", printLogsb.toString(), null);
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
                jrlist = null;
            }
        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-在盘点单打印异常：");
                // printLogsb.append("申请打印仓库发货单编号："+idarrs);
                // printLogsb.append(" 。");
                printLogsb.append("盘点单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在盘点单打印预览异常：");
                // printLogsb.append("申请预览仓库发货单编号："+idarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：checkListPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-checkList", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("在盘点单打印及预览处理异常", ex);


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
     * 盘点单打印回调处理
     *
     * @param printcallback
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-11-03
     */
    private void checkListPrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        // printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-receiptHand", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-checkList", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            printLogsb.append("盘点单编号： " + id);

            try {
                boolean flag = checkListService.updatePrintTimes(id);
                if (flag) {
                    printLogsb.append(" 更新盘点单打印次数成功");
                } else {
                    printLogsb.append(" 更新盘点单打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新盘点单打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在盘点单更新盘点单打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无盘点单编号信息");
        }
        printLogsb.append(" 。");

        printLogsb.append(" 更新响应来源于：在盘点单。");
        printLogsb.append(" 打印数据来源(盘点单编号)：");
        printLogsb.append(id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-checkList", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 反射调用盘点单打印回调处理
     *
     * @param printcallback
     * @return void
     * @throws
     * @author zhanghonghui
     * @date Nov 12, 2016
     */
    public boolean checkListPrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        // printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-checkList", printLogsb.toString(), null);
            return isok;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-checkList", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            printLogsb.append("盘点单编号： " + id);

            try {
                boolean flag = checkListService.updatePrintTimes(id);
                if (flag) {
                    printLogsb.append(" 更新盘点单打印次数成功");
                    isok = true;
                } else {
                    printLogsb.append(" 更新盘点单打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新盘点单打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在盘点单更新盘点单打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无盘点单编号信息");
        }
        printLogsb.append(" 。");

        printLogsb.append(" 更新响应来源于：在盘点单。");
        printLogsb.append(" 打印数据来源(盘点单编号)：");
        printLogsb.append(id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-checkList", printLogsb.toString(), null);
        return isok;
    }
}
