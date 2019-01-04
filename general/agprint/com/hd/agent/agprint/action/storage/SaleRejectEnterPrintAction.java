/**
 * @(#)SaleRejectEnterPrintAction.java
 * @author zhanghonghui
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-30 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action.storage;

import java.io.File;
import java.util.*;

import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.service.IPrintTempletService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.model.SaleRejectEnterDetail;
import com.hd.agent.storage.service.ISaleRejectEnterService;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

/**
 *
 * 销售退货单
 * @author zhanghonghui
 */
public class SaleRejectEnterPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(SaleRejectEnterPrintAction.class);

    private ISaleRejectEnterService saleRejectEnterService;

    public ISaleRejectEnterService getSaleRejectEnterService() {
        return saleRejectEnterService;
    }

    public void setSaleRejectEnterService(
            ISaleRejectEnterService saleRejectEnterService) {
        this.saleRejectEnterService = saleRejectEnterService;
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
     * 销售退货单打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-12
     */
    public String saleRejectEnterPrintView() throws Exception {
        return saleRejectEnterPrintHandle(true);
    }

    /**
     * 销售退货单打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    @UserOperateLog(key = "Reportprint", type = 0)
    public String saleRejectEnterPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            saleRejectEnterPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return saleRejectEnterPrintHandle(isview);
        }
        return null;
    }

    /**
     * 销售退货打印及预览处理
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-12
     */
    private String saleRejectEnterPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "销售退货入库单打印-" + CommonUtils.getDataNumberSeconds());
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
                printJob.setJobname("销售退货入库单打印");
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
                ajaxResultMap.put("printname", "销售退货入库单打印任务-" + printJob.getId());
            }

            Map map = new HashMap();
            map.put("idarrs", idarrs);
            map.put("status", "4");
            map.put("ischeck", "1");
            map.put("showdetail", "1");
            map.put("showPCustomerName", "true");
            List<SaleRejectEnter> list = saleRejectEnterService.getSaleRejectEnterListBy(map);

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
            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();
            for (SaleRejectEnter item : list) {
                List<SaleRejectEnterDetail> detailList = item.getBillDetailList();
                if (detailList == null || detailList.size() == 0) {
                    continue;
                }
                //获取打印模板
                Map templetQueryMap = new HashMap();
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "storage_salerejectenter");
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
                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }
                //String reportModelFile = servletContext.getRealPath("/ireport/storage_salerejectenter/storage_salerejectenter.jasper");
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));

                if (jreport == null) {
                    continue;
                }
                if (null == item.getPrinttimes()) {
                    item.setPrinttimes(0);
                }
                if (!isview) {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                } else {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                }

                Customer customerInfo = item.getCustomerInfo();
                if (null == customerInfo) {
                    customerInfo = new Customer();
                }
                SysUser addPersonnel = null;
                Personnel salePersonnel = null;


                if (null == addPersonnel || (StringUtils.isNotEmpty(addPersonnel.getUserid()) && !addPersonnel.getUserid().equals(item.getAdduserid()))) {
                    addPersonnel = getSysUserById(item.getAdduserid());
                }
                if (null == addPersonnel) {
                    addPersonnel = new SysUser();
                }
                if (null == salePersonnel || (StringUtils.isNotEmpty(salePersonnel.getId()) && !salePersonnel.getId().equals(customerInfo.getSalesuserid()))) {
                    salePersonnel = getPersonnelInfoById(customerInfo.getSalesuserid());
                }
                if (null == salePersonnel) {
                    salePersonnel = new Personnel();
                }

                parameters = new HashMap();
                parameters.put("P_SaleUser", salePersonnel.getName());
                parameters.put("P_Customer", customerInfo.getName());
                parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(item.getBusinessdate()));
                parameters.put("P_Customerno", customerInfo.getId());
                parameters.put("P_Remark", item.getRemark());
                parameters.put("P_Adduser", item.getAddusername());

                ////其他单据默认参数 开始
                parameters.put("P_BillAdduserTel", addPersonnel.getTelphone());


                parameters.put("P_PRINTUSER", sysUser.getUsername());
                parameters.put("P_PrintDate", new Date());
                parameters.put("P_OrderId", item.getId());

                parameters.put("P_Customerid", customerInfo.getId());

                parameters.put("P_CustomerAddr", customerInfo.getAddress());
                parameters.put("P_Contact", customerInfo.getContact());    //通用版，使用contact
                parameters.put("P_ContactTel", customerInfo.getMobile());

                parameters.put("P_BillDate", item.getBusinessdate());
                parameters.put("P_BillRemark", item.getRemark());
                parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                parameters.put("P_Storage", item.getStoragename());

                parameters.put("P_RejectBillType", item.getSourcetype());
                if ("2".equals(item.getSourcetype())) {
                    parameters.put("P_RejectBillTypeName", "直退退货");
                } else if ("1".equals(item.getSourcetype())) {
                    parameters.put("P_RejectBillTypeName", "售后退货");
                }
                String pCustomerName = "";
                if (StringUtils.isNotEmpty(customerInfo.getPid())) {
                    pCustomerName = customerInfo.getPid();
                }
                if (StringUtils.isNotEmpty(customerInfo.getPname())) {
                    pCustomerName = pCustomerName + " " + customerInfo.getPname();
                }
                parameters.put("P_PCustomerName", pCustomerName);
                ////其他单据默认参数 结束

                /**打印模板参数用的单据信息**/
                SaleRejectEnter orderInfo = (SaleRejectEnter) CommonUtils.deepCopy(item);
                orderInfo.setBillDetailList(null);
                parameters.put("P_OrderInfo", orderInfo);
                /*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                JasperPrint jrprint =
                        JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));
                if (null != jrprint) {
                    jrprint.setName("销售退货入库-" + item.getId());
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
                            printJobCallHandle.setClassname("saleRejectEnterPrintAction");
                            printJobCallHandle.setMethodname("saleRejectEnterPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("销售退货入库单打印");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("销售退货入库单打印");
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
                //printLogsb.append("申请打印的销售退货入库单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售退货入库单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：销售退货入库单打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-saleRejectEnter", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-销售退货入库单打印异常：");
                //printLogsb.append("申请打印的销售退货入库单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售退货入库单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-销售退货入库单打印预览异常：");
            }
            printLogsb.append("Exception ：saleRejectEnterPrintHandle()>>>>>>>>>>>>>>>>异常信息: " + ex.getMessage());
            addPrintLogInfo("PrintHandle-saleRejectEnter", printLogsb.toString(), null);
            logger.error("销售退货入库单打印及预览处理异常", ex);


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
    public void saleRejectEnterPrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-saleRejectEnter", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-saleRejectEnter", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的销售退货入库单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售退货入库单编号： " + id);
            try {
                SaleRejectEnter saleRejectEnter = saleRejectEnterService.getSaleRejectEnterPureInfo(id);
                if (null != saleRejectEnter) {
                    saleRejectEnter.setPrinttimes(1);
                    saleRejectEnterService.updateOrderPrinttimes(saleRejectEnter);
                    printLogsb.append(" 更新打印次数成功");
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("销售退货入库单更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无销售退货入库单编号信息");
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
        printLogsb.append(" 更新响应来源于：销售退货入库单打印。");
        printLogsb.append(" 打印数据来源(销售退货入库单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-saleRejectEnter", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 反射调用 打印回调
     * @param printcallback
     * @return boolean
     * @throws
     * @author zhanghonghui
     * @date Nov 14, 2016
     */

    public boolean saleRejectEnterPrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-saleRejectEnter", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-saleRejectEnter", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的销售退货入库单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售退货入库单编号： " + id);
            try {
                SaleRejectEnter saleRejectEnter = saleRejectEnterService.getSaleRejectEnterPureInfo(id);
                if (null != saleRejectEnter) {
                    saleRejectEnter.setPrinttimes(1);
                    isok = saleRejectEnterService.updateOrderPrinttimes(saleRejectEnter);
                    printLogsb.append(" 更新打印次数成功");
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("销售退货入库单更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无销售退货入库单编号信息");
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-saleRejectEnter", printLogsb.toString(), null);
        return isok;
    }
}

