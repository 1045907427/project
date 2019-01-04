package com.hd.agent.agprint.action.purchase;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Personnel;
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
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.purchase.service.IReturnOrderService;
import com.hd.agent.storage.service.IPurchaseRejectOutService;
import org.apache.log4j.Logger;

public class ReturnOrderPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(ReturnOrderPrintAction.class);

    public IReturnOrderService returnOrderService;

    public IReturnOrderService getReturnOrderService() {
        return returnOrderService;
    }

    public void setReturnOrderService(IReturnOrderService returnOrderService) {
        this.returnOrderService = returnOrderService;
    }

    public IPurchaseRejectOutService purchaseRejectOutService;

    public IPurchaseRejectOutService getPurchaseRejectOutService() {
        return purchaseRejectOutService;
    }

    public void setPurchaseRejectOutService(
            IPurchaseRejectOutService purchaseRejectOutService) {
        this.purchaseRejectOutService = purchaseRejectOutService;
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
     * 采购退货单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015-01-14
     */
    public String returnOrderPrintView() throws Exception {
        return returnOrderPrintHandle(true);
    }

    /**
     * 采购退货单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2015-01-14
     */
    @UserOperateLog(key = "Reportprint", type = 0)
    public String returnOrderPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            returnOrderPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return returnOrderPrintHandle(isview);
        }
        return null;
    }

    /**
     * 采购退货单打印及预览处理
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2015-01-14
     */
    private String returnOrderPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "采购退货通知单打印-" + CommonUtils.getDataNumberSeconds());
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
                printJob.setJobname("采购退货通知单打印");
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
                ajaxResultMap.put("printname", "采购退货通知单打印任务-" + printJob.getId());
            }
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            //map.put("statusarr", "2,3,4");
            map.put("showdetail", "1");
            List<ReturnOrder> list = returnOrderService.showReturnOrderListBy(map);
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
            Date todayDate = new Date();

            for (ReturnOrder item : list) {
                /*模板参数用的单据信息*/
                ReturnOrder orderInfo = null;
                if (null == item.getPrinttimes()) {
                    item.setPrinttimes(0);
                }
                item.setPrinttimes(item.getPrinttimes() + 1);
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
                List<ReturnOrderDetail> detailList = item.getReturnOrderDetailList();
                if (null != detailList && detailList.size() > 0) {

                    orderInfo = (ReturnOrder) CommonUtils.deepCopy(item);
                    orderInfo.setReturnOrderDetailList(null);
                    orderInfo.setField08("");  //用来存储出库单号

                    if ("3".equals(orderInfo.getStatus()) || "4".equals(orderInfo.getStatus())) {
                        List<String> enterIdList = purchaseRejectOutService.showPurchaseRejectOutIdListBySourceid(orderInfo.getId());
                        if (null != enterIdList) {
                            if (enterIdList.size() == 1) {
                                orderInfo.setField08(enterIdList.get(0));
                            } else {
                                StringBuffer eiBuffer = new StringBuffer();
                                for (String enterId : enterIdList) {
                                    if (eiBuffer.length() > 0) {
                                        eiBuffer.append("-");
                                    }
                                    if (enterId.length() > 4) {
                                        eiBuffer.append(enterId.substring(enterId.length() - 4, enterId.length()));
                                    } else {
                                        eiBuffer.append(enterId);
                                    }

                                }
                                orderInfo.setField08(eiBuffer.toString());
                            }

                        }
                    }

                    templetQueryMap = new HashMap();
                    templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                    templetQueryMap.put("code", "purchase_returnorder");
                    if (null != templetid && !"".equals(templetid)) {
                        templetQueryMap.put("templetid", templetid);
                    }
                    templetQueryMap.put("linkDeptid", item.getBuydeptid());
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
                    //String reportModelFile = servletContext.getRealPath("/ireport/purchase_returnorder/purchase_returnorder.jasper");
                    jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));

                    parameters = new HashMap();

                    parameters.put("P_CompanyName", getSysParamValue("COMPANYNAME"));    //公司名称
                    parameters.put("P_Supplierid", item.getSupplierid());
                    parameters.put("P_Suppliername", item.getSuppliername());
                    parameters.put("P_Businessdate", item.getBusinessdate());
                    parameters.put("P_Contact", buySupplierInfo.getContactname());    //通用版，使用contact
                    parameters.put("P_ContactTel", buySupplierInfo.getContactmobile());
                    parameters.put("P_BuyUser", buyPersonnel.getName());
                    parameters.put("P_BuyUserTel", buyPersonnel.getTelphone());
                    parameters.put("P_Adduser", item.getAddusername());


                    parameters.put("P_EnterBillId", orderInfo.getField08());

                    /**主项目不需要，但为了统一**/
                    parameters.put("P_PrintDate", new Date());
                    parameters.put("P_OrderId", item.getId());
                    parameters.put("P_Storage", item.getStoragename());
                    parameters.put("P_PrintUser", sysUser.getUsername());
                    parameters.put("P_BillRemark", item.getRemark());
                    /**主项目不需要，但为了统一**/
                    parameters.put("P_OrderInfo", orderInfo);
					/*共用参数*/
					parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                    agprintServiceImpl.setTempletCommonParameter(parameters);

                    JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));

                    if (null != jrprint) {
                        jrprint.setName("采购退货-" + item.getId());
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
                                printJobCallHandle.setClassname("returnOrderPrintAction");
                                printJobCallHandle.setMethodname("returnOrderPrintReflectCallBackHandle");
                                printJobCallHandle.setMethodparam(callbackparams);
                                printJobCallHandle.setPages(jrprint.getPages().size());
                                printJobCallHandle.setPrintorderid(item.getId());
                                printJobCallHandle.setPrintordername("采购退货通知单打印");
                                printJobCallHandle.setSourceorderid(item.getId());
                                printJobCallHandle.setSourceordername("采购退货通知单打印");
                                printJobCallHandle.setStatus("0");
                                printJobCallHandle.setType("1");//次数更新
                                agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                            }
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
                //printLogsb.append("申请打印的采购退货通知单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("采购退货通知单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：采购退货通知单打印 。");
                addPrintLogInfo("PrintHandle-returnOrder", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-采购退货通知单打印异常：");
                //printLogsb.append("申请打印的采购退货通知单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("采购退货通知单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-采购退货通知单打印预览异常：");
            }
            printLogsb.append("Exception ：returnOrderPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-returnOrder", printLogsb.toString(), null);
            logger.error("采购退货通知单打印及预览处理异常", ex);


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
     * @date 2014-1-6
     */
    public void returnOrderPrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-returnOrder", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-returnOrder", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的采购退货通知单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("采购退货通知单编号： " + id);
            try {
                ReturnOrder returnOrder = returnOrderService.showPureReturnOrder(id);
                if (null != returnOrder) {
                    returnOrder.setPrinttimes(1);
                    returnOrderService.updateOrderPrinttimes(returnOrder);
                    printLogsb.append(" 更新打印次数成功");
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 回调更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无采购退货通知单编号信息");
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
        //printLogsb.append(" 更新响应来源于：采购退货通知单打印。");

        printLogsb.append(" 打印数据来源(采购退货通知单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-returnOrder", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 反射打印回调
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public boolean returnOrderPrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-returnOrder", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-returnOrder", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印的采购退货通知单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("采购退货通知单编号： " + id);
            try {
                ReturnOrder returnOrder = returnOrderService.showPureReturnOrder(id);
                if (null != returnOrder) {
                    returnOrder.setPrinttimes(1);
                    returnOrderService.updateOrderPrinttimes(returnOrder);
                    printLogsb.append(" 更新打印次数成功");
                    isok=true;
                    return isok;
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 回调更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无采购退货通知单编号信息");
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-returnOrder", printLogsb.toString(), null);
        return isok;
    }
}


