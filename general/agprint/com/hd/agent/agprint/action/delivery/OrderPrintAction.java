/**
 * @(#)OrderPrintAction.java
 * @author wanghongteng
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月2日 wanghongteng 创建版本
 */
package com.hd.agent.agprint.action.delivery;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.delivery.model.DeliveryOrder;
import com.hd.agent.delivery.model.DeliveryOrderDetail;
import com.hd.agent.delivery.service.IDistributeService;
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
 *
 * @author wanghongteng
 */

/**
 * @author Administrator
 *
 */
public class OrderPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(OrderPrintAction.class);
    private IDistributeService distributeService;

    public IDistributeService getDistributeService() {
        return distributeService;
    }

    public void setDistributeService(IDistributeService distributeService) {
        this.distributeService = distributeService;
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
     * 客户订单打印预览
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2015-9-2
     */
    public String orderPrintView() throws Exception {
        return orderPrintHandle(true);
    }

    /**
     * 客户订单打印
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2015-9-2
     */
    public String orderPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            orderPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            if (!isview) {
                logStr = "代配送客户订单打印";
            }
            return orderPrintHandle(isview);
        }
        return null;
    }

    /**
     * 客户订单打印及预览处理
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2015-9-2
     */
    private String orderPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "客户订单打印-" + CommonUtils.getDataNumberSeconds());
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
                printJob.setJobname("客户订单打印");
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
                ajaxResultMap.put("printname", "客户订单打印任务-" + printJob.getId());
            }

            String printlimit = getPrintLimitInfo();
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            map.put("showdetail", "1");
            List<DeliveryOrder> list = distributeService.printOrder(map);
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
            JasperReport jreport = null;

            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();
            SysUser addPersonnel = null;
            for (DeliveryOrder item : list) {
                if (null == item.getPrinttimes()) {
                    item.setPrinttimes(0);
                }
                if (!isview) {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                } else {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                }
                List<DeliveryOrderDetail> detailList = item.getDeliveryOrderDetail();
                if (detailList == null || detailList.size() == 0) {
                    continue;
                }
                //获取打印模板
                Map templetQueryMap = new HashMap();
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "delivery_order");
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

                //String reportModelFile = servletContext.getRealPath("/ireport/purchase_buyorder/purchase_buyorder.jasper");
                jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
                if (jreport == null) {
                    continue;
                }
                parameters = new HashMap();
                //parameters.put("P_BuyOrder", orderInfo);
                parameters.put("P_Totalvolume", item.getTotalvolume());
                parameters.put("P_Totalweight", item.getTotalweight());
                parameters.put("P_Customerid", item.getCustomerid());
                parameters.put("P_Customername", item.getCustomername());
                parameters.put("P_Storageid", item.getStorageid());
                parameters.put("P_Storagename", item.getStoragename());
                parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(item.getBusinessdate()));
                parameters.put("P_PRINTUSER", sysUser.getName());
                parameters.put("P_PrintDate", new Date());
                parameters.put("P_OrderId", item.getId());
                parameters.put("P_BillRemark", item.getRemark());


                //添加用户名称
                parameters.put("P_Adduser", item.getAddusername());

                if (null == addPersonnel || (StringUtils.isNotEmpty(addPersonnel.getUserid()) && !addPersonnel.getUserid().equals(item.getAdduserid()))) {
                    addPersonnel = getSysUserById(item.getAdduserid());
                }
                //添加用户联系电话
                parameters.put("P_BillAdduserTel", addPersonnel.getTelphone());

                Customer customerInfo = item.getCustomerInfo();

                parameters.put("P_CustomerInfo", customerInfo);
                //客户编号
                parameters.put("P_Customerno", customerInfo.getId());
                //客户名称
                parameters.put("P_CustomerName", customerInfo.getName());


                String pCustomerName = "";
                if (StringUtils.isNotEmpty(customerInfo.getPid())) {
                    pCustomerName = customerInfo.getPid();
                    //上级客户编号
                    parameters.put("P_PCustomerId", customerInfo.getPid());
                } else {
                    //上级客户编号
                    parameters.put("P_PCustomerId", "");
                }
                if (StringUtils.isNotEmpty(customerInfo.getPname())) {
                    pCustomerName = pCustomerName + " " + customerInfo.getPname();
                }
                //上级客户编号+名称
                parameters.put("P_PCustomerName", pCustomerName);

                //是否现款
                parameters.put("P_Customer_SFXJ", "1".equals(customerInfo.getIscash()) ? "是" : "否");    //是否现款
                //是否账期
                parameters.put("P_Customer_SFZQ", "1".equals(customerInfo.getIslongterm()) ? "是" : "否");    //是否账期
                //客户地址
                parameters.put("P_CustomerAddr", customerInfo.getAddress());
                //联系人
                parameters.put("P_Contact", customerInfo.getContact());    //通用版，使用contact
                //联系电话
                parameters.put("P_ContactTel", customerInfo.getMobile());
                //销售区域
                parameters.put("P_SaleArea", customerInfo.getSalesareaname());    //销售订单号

                /**打印模板参数用的单据信息**/
                DeliveryOrder orderInfo = (DeliveryOrder) CommonUtils.deepCopy(item);
                orderInfo.setDeliveryOrderDetail(null);
                parameters.put("P_OrderInfo", orderInfo);
                /*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));
                if (null != jrprint) {
                    jrprint.setName("客户订单-" + item.getId());
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
                            printJobCallHandle.setClassname("orderPrintAction");
                            printJobCallHandle.setMethodname("orderPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("客户订单打印");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("客户订单打印");
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
                //printLogsb.append("申请打印的采购订单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append(" 客户订单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称： 客户订单打印。");
                addPrintLogInfo("PrintHandle-order", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-客户订单打印异常：");
                //printLogsb.append("申请打印的采购订单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("客户订单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-客户订单打印预览异常：");
            }
            printLogsb.append("Exception ：orderPrint()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-order", printLogsb.toString(), null);
            logger.error("客户订单打印及预览处理异常", ex);


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
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2015-9-2
     */
    public void orderPrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-order", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-order", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {

            printLogsb.append("客户订单编号： " + id);
            try {
                Map deliveryOrderMap = distributeService.getDeliveryOrderInfo(id);
                DeliveryOrder deliveryOrder = (DeliveryOrder) deliveryOrderMap.get("deliveryOrder");
                if (null != deliveryOrder) {

                    deliveryOrder.setPrinttimes(deliveryOrder.getPrinttimes() + 1);
                    distributeService.updateOrderPrinttimes(deliveryOrder);
                    printLogsb.append(" 更新打印次数成功");
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("客户订单更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无采购订单编号信息");
        }
        printLogsb.append(" 。");

        printLogsb.append(" 打印数据来源(客户订单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-order", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 打印反射回调
     * @param printcallback
     * @return void
     * @throws
     * @author zhanghonghui
     * @date Nov 10, 2016
     */
    public boolean orderPrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-order", printLogsb.toString(), null);
            return false;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-order", printLogsb.toString(), null);
            return false;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {

            printLogsb.append("客户订单编号： " + id);
            try {
                Map deliveryOrderMap = distributeService.getDeliveryOrderInfo(id);
                DeliveryOrder deliveryOrder = (DeliveryOrder) deliveryOrderMap.get("deliveryOrder");
                if (null != deliveryOrder) {

                    deliveryOrder.setPrinttimes(deliveryOrder.getPrinttimes() + 1);
                    distributeService.updateOrderPrinttimes(deliveryOrder);
                    printLogsb.append(" 更新打印次数成功");
                    isok = true;
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("客户订单更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无采购订单编号信息");
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-order", printLogsb.toString(), null);
        return isok;
    }
}

