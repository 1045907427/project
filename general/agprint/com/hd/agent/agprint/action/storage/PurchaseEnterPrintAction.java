package com.hd.agent.agprint.action.storage;

import java.io.File;
import java.util.*;

import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.service.IPrintTempletService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.model.PurchaseEnterDetail;
import com.hd.agent.storage.service.IPurchaseEnterService;
import org.apache.log4j.Logger;

public class PurchaseEnterPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(PurchaseEnterPrintAction.class);
    private IPurchaseEnterService purchaseEnterService;

    public IPurchaseEnterService getPurchaseEnterService() {
        return purchaseEnterService;
    }

    public void setPurchaseEnterService(IPurchaseEnterService purchaseEnterService) {
        this.purchaseEnterService = purchaseEnterService;
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
     * 采购入库单打印预览
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015-05-22
     */
    public String purchaseEnterPrintView() throws Exception {
        return purchaseEnterPrintHandle(true);
    }

    /**
     * 采购入库单打印
     *
     * @throws Exception
     * @author zhanghonghui
     * @date 2015-05-22
     */
    @UserOperateLog(key = "PurchaseEnter-Purchase", type = 0)
    public String purchaseEnterPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            purchaseEnterPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            if (!isview) {
                logStr = "采购入库单打印";
            }
            return purchaseEnterPrintHandle(isview);
        }
        return null;
    }

    /**
     * 打印及预览处理
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2015-05-22
     */
    private String purchaseEnterPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "采购入库单打印-" + CommonUtils.getDataNumberSeconds());
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
                printJob.setJobname("采购入库单打印");
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
                ajaxResultMap.put("printname", "采购入库单打印任务-" + printJob.getId());
            }
            String printlimit = getPrintLimitInfo();
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            map.put("showdetail", "1");
            if (!isview) {
                if ("1".equals(printlimit)) {
                    map.put("notprint", printlimit);    //未打印
                }
            }
            List<PurchaseEnter> list = purchaseEnterService.showPurchaseEnterListBy(map);
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
            JasperReport jreport = null;

            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();

            for (PurchaseEnter item : list) {
                if (null == item.getPrinttimes()) {
                    item.setPrinttimes(0);
                }
                BuySupplier buySupplierInfo = item.getBuySupplierInfo();
                if (null == buySupplierInfo) {
                    buySupplierInfo = new BuySupplier();
                }
                Personnel buyPersonnel = null;
                if (StringUtils.isNotEmpty(item.getBuyuserid())) {
                    buyPersonnel = getPersonnelInfoById(item.getBuyuserid());
                }
                if (null == buyPersonnel && StringUtils.isNotEmpty(buySupplierInfo.getBuyuserid())) {
                    buyPersonnel = getPersonnelInfoById(buySupplierInfo.getBuyuserid());
                }
                if (null == buyPersonnel) {
                    buyPersonnel = new Personnel();
                }
                if (!isview) {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                } else {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                }

                List<PurchaseEnterDetail> detailList = item.getPurchaseEnterDetailList();
                if (null == detailList || detailList.size() == 0) {
                }


                templetQueryMap.clear();
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "storage_purchaseenter");
                if (null != templetid && !"".equals(templetid)) {
                    templetQueryMap.put("templetid", templetid);
                }
                //关联客户的参数值
                //templetQueryMap.put("linkCustomerid", item.getCustomerid());
                //关联上级客户的参数值
                //templetQueryMap.put("linkPCustomerid", item.getPcustomerid());
                //关联部门的参数值
                templetQueryMap.put("linkDeptid", item.getBuydeptid());
                //关联销售区域的参数值
                //templetQueryMap.put("linkSalesarea", item.getSalesarea());
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
                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");
                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }
                if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                    continue;
                }

                //String reportModelFile = servletContext.getRealPath("/ireport/storage_purchaseenter/storage_purchaseenter.jasper");
                jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
                if (jreport == null) {
                    continue;
                }

                parameters = new HashMap();
                parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(item.getBusinessdate()));
                parameters.put("P_PRINTUSER", sysUser.getName());
                parameters.put("P_Supplierid", item.getSupplierid());
                parameters.put("P_Suppliername", item.getSuppliername());
                parameters.put("P_Businessdate", item.getBusinessdate());
                parameters.put("P_Contact", buySupplierInfo.getContactname());    //通用版，使用contact
                parameters.put("P_ContactTel", buySupplierInfo.getContactmobile());
                parameters.put("P_BuyUser", buyPersonnel.getName());
                parameters.put("P_BuyUserTel", buyPersonnel.getTelphone());
                parameters.put("P_Adduser", item.getAddusername());

                /**主项目不需要，但为了统一**/
                parameters.put("P_PrintDate", new Date());
                parameters.put("P_OrderId", item.getId());
                parameters.put("P_Storage", item.getStoragename());
                parameters.put("P_PrintUser", sysUser.getUsername());
                parameters.put("P_BillRemark", item.getRemark());
                /**打印模板参数用的单据信息**/
                PurchaseEnter orderInfo = (PurchaseEnter) CommonUtils.deepCopy(item);
                orderInfo.setPurchaseEnterDetailList(null);
                parameters.put("P_OrderInfo", orderInfo);
                /*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));
                if (null != jrprint) {
                    jrprint.setName("采购入库单-" + item.getId());
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
                            printJobCallHandle.setClassname("storagePurchaseEnterPrintAction");
                            printJobCallHandle.setMethodname("purchaseEnterPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("采购入库单打印");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("采购入库单打印");
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
                //printLogsb.append("申请打印的采购入库单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append(" 采购入库单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称： 采购入库单打印。");
                addPrintLogInfo("PrintHandle-purchaseEnter", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-采购入库单打印异常：");
                //printLogsb.append("申请打印的采购入库单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("采购入库单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-采购入库单打印预览异常：");
            }
            printLogsb.append("Exception ：purchaseEnterPrint()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-purchaseEnter", printLogsb.toString(), null);
            logger.error("采购入库单打印及预览处理异常", ex);


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
     * @author zhanghonghui
     * @date 2015-05-22
     */
    public void purchaseEnterPrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-purchaseEnter", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-purchaseEnter", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        boolean isok = false;
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的采购入库单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("采购入库单编号： " + id);
            try {
                PurchaseEnter purchaseEnter = purchaseEnterService.showPurchaseEnter(id);
                if (null != purchaseEnter) {
                    purchaseEnter.setPrinttimes(1);
                    isok = purchaseEnterService.updateOrderPrinttimes(purchaseEnter);
                    printLogsb.append(" 更新打印次数成功");
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("采购入库单更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无采购入库单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 打印数据来源(采购入库单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-purchaseEnter", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        if (isok) {
            response.getWriter().write("ok");
        } else {
            response.getWriter().write("fail");
        }
    }


    /**
     * 反射调用打印回调
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2015-05-22
     */
    public boolean purchaseEnterPrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-purchaseEnter", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-purchaseEnter", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的采购入库单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("采购入库单编号： " + id);
            try {
                PurchaseEnter purchaseEnter = purchaseEnterService.showPurchaseEnter(id);
                if (null != purchaseEnter) {
                    purchaseEnter.setPrinttimes(1);
                    isok = purchaseEnterService.updateOrderPrinttimes(purchaseEnter);
                    printLogsb.append(" 更新打印次数成功");
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("采购入库单更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无采购入库单编号信息");
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-purchaseEnter", printLogsb.toString(), null);
        return isok;
    }
}

