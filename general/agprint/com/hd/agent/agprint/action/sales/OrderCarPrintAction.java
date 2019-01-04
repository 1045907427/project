/**
 * @(#)OrderCarPrintAction.java
 * @author zhanghonghui
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-30 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action.sales;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.sales.service.IOrderCarService;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IStorageSaleOutPrintService;
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
 *
 * 车销打印
 * @author zhanghonghui
 */
public class OrderCarPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(OrderCarPrintAction.class);

    private IStorageSaleOutPrintService storageSaleOutPrintService;

    public IStorageSaleOutPrintService getStorageSaleOutPrintService() {
        return storageSaleOutPrintService;
    }

    public void setStorageSaleOutPrintService(
            IStorageSaleOutPrintService storageSaleOutPrintService) {
        this.storageSaleOutPrintService = storageSaleOutPrintService;
    }

    protected IOrderCarService salesOrderCarService;

    public IOrderCarService getSalesOrderCarService() {
        return salesOrderCarService;
    }

    public void setSalesOrderCarService(IOrderCarService salesOrderCarService) {
        this.salesOrderCarService = salesOrderCarService;
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
     * 车销打印预览
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "PrintView", type = 0)
    public String orderCarPrintView() throws Exception {
        return orderCarPrintHandle(true);
    }

    /**
     * 车销打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "Print", type = 0)
    public String orderCarPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            orderCarPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return orderCarPrintHandle(isview);
        }
        return null;
    }

    /**
     * 车销打印
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    @UserOperateLog(key = "Print", type = 0)
    private String orderCarPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "车销单打印-" + CommonUtils.getDataNumberSeconds());
        Map printTempletSettingFistMap = null;
        String templetid = request.getParameter("templetid");

        Map requestMap = CommonUtils.changeMap(request.getParameterMap());

        SysUser sysUser = getSysUser();
        String saleoutCanprintIds = "";
        String dispatchCanprintIds = "";
        if (requestMap.containsKey("status")) {
            requestMap.remove("status");
        }
        if (requestMap.containsKey("statusarr")) {
            requestMap.remove("statusarr");
        }
        if (requestMap.containsKey("notprint")) {
            requestMap.remove("notprint");
        }
        String groupby = (String) requestMap.get("groupby");
        if (!"customer".equals(groupby) && !"storage".equals(groupby)) {
            groupby = "customer";
        }
        requestMap.put("showOrderDetail", "true");
        String canprintIds = "";
        try {
            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("车销单打印");
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
                ajaxResultMap.put("printname", "车销单打印任务-" + printJob.getId());
            }
            String printlimit = "";
            if (!isview) {
                printlimit = getPrintLimitInfo();

                requestMap.put("statusarr", "3,4");

                requestMap.put("notprint", printlimit);
            }
            requestMap.put("showOrderDetail", "true");
            pageMap.setCondition(requestMap);
            List<Saleout> list = salesOrderCarService.getOrderCarPrintList(pageMap);
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
            //获取打印模板
            Map templetQueryMap = new HashMap();

            templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
            templetQueryMap.put("code", "sales_ordercar");
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
            //String reportModelFile = servletContext.getRealPath("/ireport/sales_ordercar/sales_ordercar.jasper");
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
            Map parameters = null;
            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();
            for (Saleout item : list) {
                List<SaleoutDetail> detailList = item.getSaleoutDetailList();

                if (null == detailList || detailList.size() == 0) {
                    continue;
                }
                List<String> saleoutPrintIdList = new ArrayList<String>();

                Customer customerInfo = item.getCustomerInfo();
                SysUser addPersonnel = null;
                Personnel salePersonnel = null;
                if (null == customerInfo) {
                    customerInfo = new Customer();
                }
                if (null == detailList || detailList.size() == 0) {
                    continue;
                }


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
                if (StringUtils.isNotEmpty(item.getSalesusername())) {
                    parameters.put("P_SaleUser", item.getSalesusername());
                } else {
                    parameters.put("P_SaleUser", salePersonnel.getName());
                }
                parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                parameters.put("P_Customer", customerInfo.getName());
                parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(item.getBusinessdate()));
                parameters.put("P_Customerno", customerInfo.getId());
                parameters.put("P_Remark", item.getRemark());
                parameters.put("P_Adduser", item.getAddusername());

                ////其他单据默认参数 开始
                parameters.put("P_BillAdduserTel", addPersonnel.getTelphone());

                parameters.put("P_PRINTUSER", sysUser.getUsername());
                parameters.put("P_PrintDate", new Date());
                parameters.put("P_SaleId", item.getSaleorderid());

                parameters.put("P_Customerid", customerInfo.getId());

                parameters.put("P_CustomerAddr", customerInfo.getAddress());
                parameters.put("P_Contact", customerInfo.getContact());    //通用版，使用contact
                parameters.put("P_ContactTel", customerInfo.getMobile());

                parameters.put("P_BillDate", item.getBusinessdate());
                parameters.put("P_BillRemark", item.getRemark());
                parameters.put("P_Storage", item.getStoragename());

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
                Saleout orderInfo = (Saleout) CommonUtils.deepCopy(item);
                orderInfo.setSaleoutDetailList(null);
                parameters.put("P_OrderInfo", orderInfo);
                    /*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                String printid = "";
                if ("customer".equals(groupby)) {
                } else {
                    String storageid = item.getStorageid();
                    String storagename = item.getStoragename();
                    String customer = "";
                    if (StringUtils.isNotEmpty(storageid)) {
                        customer = storageid + " ";
                        printid = "仓库编号: " + storageid;
                    }
                    if (StringUtils.isNotEmpty(storagename)) {
                        customer += storagename;
                    }
                    //parameters.put("P_Customer", customer);
                }
                JasperPrint jrprint =
                        JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));

                if (null != jrprint) {

                    if (!isview) {
                        for (SaleoutDetail itemDetail : detailList) {
                            if (!saleoutPrintIdList.contains(itemDetail.getSaleoutid())) {
                                saleoutPrintIdList.add(itemDetail.getSaleoutid());
                            }
                            if (!printIdList.contains(itemDetail.getSaleoutid())) {
                                printIdList.add(itemDetail.getSaleoutid());
                            }
                        }
                        String printidarr = "";
                        if (printIdList.size() > 0) {
                            String[] printarr = (String[]) saleoutPrintIdList.toArray(new String[saleoutPrintIdList.size()]);
                            printidarr = StringUtils.join(printarr, ",");
                        }
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("idarr", printidarr);
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);


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
                            printJobCallHandle.setClassname("orderCarPrintAction");
                            printJobCallHandle.setMethodname("orderCarPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("车销单打印");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("车销单打印");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");//次数更新
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrprint.setName("车销打印-" + printid);
                    jrlist.add(jrprint);

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
                    canprintIds = StringUtils.join(printarr, ",");
                }
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                //printLogsb.append("申请打印发货单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("发货单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：车销打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-orderCarPrint", printLogsb.toString(), null);
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
            jrlist = null;
            list = null;

        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-车销打印异常：");
                //printLogsb.append("申请打印发货单编号："+idarrs);
                printLogsb.append("发货单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-车销打印预览异常：");
                //printLogsb.append("申请打印预览发货单编号："+idarrs);
                //printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：orderCarPrintHandle()>>>>>>>>>>>>>>>>异常信息：" + ex.getMessage());
            addPrintLogInfo("PrintHandle-orderCarPrint", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("车销单打印及预览处理异常", ex);


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
     * 车销打印回调
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public void orderCarPrintCallBackHandle(String printcallback) throws Exception {
        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs) {
            saleidarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        printLogsb.append("更新车销打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-orderCarPrint", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-orderCarPrint", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String idarr = (String) map.get("idarr");
        if (null != idarr && !"".equals(idarr.trim())) {
            //printLogsb.append("申请打印销售单编号："+saleidarrs);
            //printLogsb.append(" 。");
            printLogsb.append("仓库发货单编号： " + idarr);
            try {
                storageSaleOutPrintService.updatePrinttimesSyncUpOrderIds(idarr);
                printLogsb.append(" 更新打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败,发货单编号:" + idarr);
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("车销单更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }

        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：车销打印。");
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-orderCarPrint", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 车销反射打印回调
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public boolean orderCarPrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        printLogsb.append("更新车销打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-orderCarPrint", printLogsb.toString(), null);
            return isok;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-orderCarPrint", printLogsb.toString(), null);
            return isok;
        }
        String idarr = (String) map.get("idarr");
        if (null != idarr && !"".equals(idarr.trim())) {
            //printLogsb.append("申请打印销售单编号："+saleidarrs);
            //printLogsb.append(" 。");
            printLogsb.append("仓库发货单编号： " + idarr);
            try {
                storageSaleOutPrintService.updatePrinttimesSyncUpOrderIds(idarr);
                printLogsb.append(" 更新打印次数成功");
                isok = true;
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败,发货单编号:" + idarr);
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("车销单更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }

        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：车销打印。");
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-orderCarPrint", printLogsb.toString(), null);
        return isok;
    }
}

