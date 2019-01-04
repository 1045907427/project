package com.hd.agent.agprint.action.storage;

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
import com.hd.agent.delivery.model.DeliveryOrder;
import com.hd.agent.storage.model.SplitMerge;
import com.hd.agent.storage.model.SplitMergeDetail;
import com.hd.agent.storage.service.ISplitMergeService;
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
 * 拆装单打印Action
 *
 * @author limin
 * @date Jan 11, 2016
 */
public class SplitMergePrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(SplitMergePrintAction.class);

    private ISplitMergeService splitMergeService;

    public ISplitMergeService getSplitMergeService() {
        return splitMergeService;
    }

    public void setSplitMergeService(ISplitMergeService splitMergeService) {
        this.splitMergeService = splitMergeService;
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
     * 拆装单打印预览
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Jan 11, 2016
     */
    public String splitMergePrintView() throws Exception {
        return splitMergePrintHandle(true);
    }

    /**
     * 拆装单打印
     *
     * @throws Exception
     * @author limin
     * @date Jan 11, 2016
     */
    @UserOperateLog(key = "SplitMerge-Storage", type = 0)
    public String splitMergePrint() throws Exception {

        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");

        if (null != printcallback && !"".equals(printcallback)) {

            splitMergePrintCallBackHandle(printcallback);

        } else {

            boolean view = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {

                view = false;
            } else {

                view = true;
            }

            return splitMergePrintHandle(view);
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
    private String splitMergePrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "拆装单打印-" + CommonUtils.getDataNumberSeconds());
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

        String category = "拆分单";

        try {

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("拆装单打印");
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
                ajaxResultMap.put("printname", "拆装单打印任务-" + printJob.getId());
            }

            String printlimit = getPrintLimitInfo();
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            map.put("showdetail", "1");

            pageMap.setCondition(map);
            pageMap.setRows(9999);

            List<SplitMerge> list = splitMergeService.selectSplitMergeList(pageMap).getList();
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
            Map templetQueryMap = null;

            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();
            SplitMerge sm = null;
            for (SplitMerge item : list) {

                category = "1".equals(item.getBilltype()) ? "拆分单" : "组装单";

                if (null == item.getPrinttimes()) {
                    item.setPrinttimes(0);
                }
//                if(!view){
                item.setPrinttimes(item.getPrinttimes() + 1);
//                }else{
//                    item.setPrinttimes(item.getPrinttimes()+1);
//                }

                sm = (SplitMerge) CommonUtils.deepCopy(item);

                List<SplitMergeDetail> detailList = splitMergeService.selectSplitMergeDetailListByBillid(sm.getId());

                if (detailList == null || detailList.size() == 0) {
                    continue;
                }


                JasperPrint jrprint = null;

                templetQueryMap = new HashMap();
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));

                if ("1".equals(item.getBilltype())) {
                    templetQueryMap.put("code", "storage_split");
                } else {
                    templetQueryMap.put("code", "storage_merge");
                }
                if (null != templetid && !"".equals(templetid)) {
                    templetQueryMap.put("templetid", templetid);
                }
                Map templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);
                if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                    printTempletSettingFistMap = new HashMap();
                    printTempletSettingFistMap.putAll(templetResultMap);
                }
                PrintTemplet printTemplet = new PrintTemplet();
                if (templetResultMap.containsKey("printTempletInfo")) {
                    printTemplet = (PrintTemplet) templetResultMap.get("printTempletInfo");
                    if (null == printTemplet) {
                        printTemplet = new PrintTemplet();
                        templetResultMap.put("printTempletInfo", printTemplet);
                    }
                }
                //打印模板文件
                String printTempletFile = (String) templetResultMap.get("printTempletFile");
                if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                    continue;
                }

                parameters = new HashMap();

                parameters.put("P_CompanyName", getSysParamValue("COMPANYNAME"));    //公司名称
                parameters.put("P_OrderInfo", sm);
                /**打印模板参数用的单据信息**/
                SplitMerge orderInfo = (SplitMerge) CommonUtils.deepCopy(item);
                parameters.put("P_OrderInfo", orderInfo);
                /*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }
                if ("1".equals(item.getBilltype())) {
                    //进行打印内容明细排序
                    if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                        Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                    }

                    jrprint = JasperFillManager.fillReport(printTempletFile, parameters, new JRBeanCollectionDataSource(detailList));
                } else {
                    //进行打印内容明细排序
                    if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                        Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                    }

                    jrprint = JasperFillManager.fillReport(printTempletFile, parameters, new JRBeanCollectionDataSource(detailList));
                }

                if (null != jrprint) {

                    jrprint.setName(category + "-" + item.getId());
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
                            printJobCallHandle.setClassname("splitMergePrintAction");
                            printJobCallHandle.setMethodname("splitMergePrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername(category + "打印");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername(category + "打印");
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
                //printLogsb.append("申请打印的库存调账单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append(" " + category + "编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称： " + category + "打印。");
                addPrintLogInfo("PrintHandle-splitmerge", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-" + category + "打印异常：");

                printLogsb.append(category + "编号： " + canprintIds + " 。");
            } else {
                printLogsb.append("打印预览申请-" + category + "打印预览异常：");
            }
            printLogsb.append("Exception ：splitmergePrint()>>>>>>>>>>>>>>>>" + category + "异常信息 " + ex.getMessage());
            addPrintLogInfo("PrintHandle-splitmerge", printLogsb.toString(), null);
            logger.error(category + "打印及预览处理异常", ex);


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
    public void splitMergePrintCallBackHandle(String printcallback) throws Exception {

        String idarrs = CommonUtils.nullToEmpty(request.getParameter("idarrs"));
        SplitMerge splitMerge = null;

        StringBuilder printLogsb = new StringBuilder();

        if (StringUtils.isEmpty(printcallback)) {

            printLogsb.append("更新打印次数失败：回调参数为空。");
            addPrintLogInfo("PrintCallBackHandle-splitmerge", new String(printLogsb), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {

            printLogsb.append("更新打印次数失败：回调参数转成Map时为空。");
            addPrintLogInfo("PrintCallBackHandle-splitmerge", new String(printLogsb), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {

            try {
                splitMerge = splitMergeService.selectSplitMerge(id);
                if (null != splitMerge) {

                    splitMerge.setPrinttimes(1);
                    splitMergeService.updateSplitMergePrinttimes(id);
                    printLogsb.append("1".equals(splitMerge.getBilltype()) ? "拆分单" : "组装单" + "编号" + id + " 更新打印次数成功。");
                } else {
                    printLogsb.append("1".equals(splitMerge.getBilltype()) ? "拆分单" : "组装单" + "编号" + id + " 更新打印次数失败。");
                }

            } catch (Exception ex) {

                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("拆装单更新打印次数异常", ex);
            }
        } else {

            printLogsb.append("。回调参数中无库存调账单编号信息。");
        }

        printLogsb.append(" 打印数据来源(" + (splitMerge != null && "1".equals(splitMerge.getBilltype()) ? "拆分单" : "组装单") + "编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-splitmerge", new String(printLogsb), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 打印回调
     *
     * @param printcallback
     * @return void
     * @throws
     * @author zhanghonghui
     * @date Nov 14, 2016
     */
    public boolean splitMergePrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        SplitMerge splitMerge = null;

        StringBuilder printLogsb = new StringBuilder();

        if (StringUtils.isEmpty(printcallback)) {

            printLogsb.append("更新打印次数失败：回调参数为空。");
            addPrintLogInfo("PrintCallBackHandle-splitmerge", new String(printLogsb), null);
            return isok;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {

            printLogsb.append("更新打印次数失败：回调参数转成Map时为空。");
            addPrintLogInfo("PrintCallBackHandle-splitmerge", new String(printLogsb), null);
            return isok;
        }

        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {

            try {
                splitMerge = splitMergeService.selectSplitMerge(id);
                if (null != splitMerge) {

                    splitMerge.setPrinttimes(1);
                    isok = splitMergeService.updateSplitMergePrinttimes(id) > 0;
                    printLogsb.append("1".equals(splitMerge.getBilltype()) ? "拆分单" : "组装单" + "编号" + id + " 更新打印次数成功。");
                } else {
                    printLogsb.append("1".equals(splitMerge.getBilltype()) ? "拆分单" : "组装单" + "编号" + id + " 更新打印次数失败。");
                }

            } catch (Exception ex) {

                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("拆装单更新打印次数异常", ex);
                isok = false;
            }
        } else {

            printLogsb.append("。回调参数中无库存调账单编号信息。");
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-splitmerge", new String(printLogsb), null);
        return isok;
    }
}
