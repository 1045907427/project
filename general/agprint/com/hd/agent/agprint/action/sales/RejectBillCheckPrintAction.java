/**
 * @(#)RejectBillPrintAction.java
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
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.service.IRejectBillService;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.model.SaleRejectEnterDetail;
import com.hd.agent.storage.service.ISaleRejectEnterService;
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
 * 销售退货验收打印
 * @author zhanghonghui
 */
public class RejectBillCheckPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(RejectBillCheckPrintAction.class);
    protected IRejectBillService salesRejectBillService;

    public IRejectBillService getSalesRejectBillService() {
        return salesRejectBillService;
    }

    public void setSalesRejectBillService(IRejectBillService salesRejectBillService) {
        this.salesRejectBillService = salesRejectBillService;
    }

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
     * 销售退货验收打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-12
     */
    public String rejectBillCheckPrintView() throws Exception {
        return rejectBillCheckPrintHandle(true);
    }

    /**
     * 销售退货验收打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-9-30
     */
    @UserOperateLog(key = "Reportprint", type = 0)
    public String rejectBillCheckPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            rejectBillCheckPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return rejectBillCheckPrintHandle(isview);
        }
        return null;
    }

    /**
     * 销售退货验收打印及预览处理
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-12
     */
    private String rejectBillCheckPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "销售退货验收打印-" + CommonUtils.getDataNumberSeconds());
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
        String orderCanprintIds = "";
        String rejectCanprintIds = "";
        try {

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ( "ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("销售退货验收打印");
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
                ajaxResultMap.put("printname", "销售退货验收打印任务-" + printJob.getId());
            }
            //获取打印模板
            Map templetQueryMap = new HashMap();
            templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
            templetQueryMap.put("code", "sales_rejectbillcheck");
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

            Map paramMap = new HashMap();
            paramMap.put("idarrs", idarrs);
            if (!isview) {
                String printlimit = getPrintLimitInfo();
                if ("1".equals(printlimit)) {
                    //map.put("notprint", "1");通用不用限制
                }
            }
            paramMap.put("showdetail", "0"); //不获取打印明细
            List<RejectBill> list = salesRejectBillService.getRejectBillListBy(paramMap);

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
            Date printDate = new Date();
            Map<String, String> rejectPrintIdMap = new HashMap<String, String>();
            Map<String, String> orderPrintIdMap = new HashMap<String, String>();
            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            SysUser addPersonnel = null;
            Personnel salePersonnel = null;
            int orderPrintCount = 0;
            for (RejectBill rejectBill : list) {
                if (null == rejectBill.getPrinttimes()) {
                    rejectBill.setPrinttimes(0);
                }
                orderPrintCount = 0;
                if (null != rejectBill.getPrinttimes()) {
                    orderPrintCount = rejectBill.getPrinttimes();
                }


                paramMap = new HashMap();
                paramMap.put("sourceid", rejectBill.getId());
                //paramMap.put("sourcetype", "1");
                paramMap.put("status", "4");
                //paramMap.put("ischeck", "1");
                paramMap.put("showdetail", "1");
                paramMap.put("showPCustomerName", "true");
                List<SaleRejectEnter> saleRejectEnterList = saleRejectEnterService.getSaleRejectEnterListBy(paramMap);
                if (saleRejectEnterList == null || saleRejectEnterList.size() == 0) {
                    continue;
                }

                for (SaleRejectEnter saleRejectEnter : saleRejectEnterList) {
                    if (null == saleRejectEnter.getPrinttimes()) {
                        saleRejectEnter.setPrinttimes(0);
                    }
                    List<SaleRejectEnterDetail> detailList = saleRejectEnter.getBillDetailList();
                    if (null == detailList || detailList.size() == 0) {
                        continue;
                    }

                    //进行打印内容明细排序
                    if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                        Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                    }
                    Customer customerInfo = saleRejectEnter.getCustomerInfo();
                    if (null == customerInfo) {
                        customerInfo = new Customer();
                    }

                    if (null == addPersonnel || (StringUtils.isNotEmpty(addPersonnel.getUserid()) && !addPersonnel.getUserid().equals(saleRejectEnter.getAdduserid()))) {
                        addPersonnel = getSysUserById(saleRejectEnter.getAdduserid());
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
                    parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(saleRejectEnter.getBusinessdate()));
                    parameters.put("P_Customerno", customerInfo.getId());
                    parameters.put("P_Remark", saleRejectEnter.getRemark());
                    parameters.put("P_Adduser", saleRejectEnter.getAddusername());

                    ////其他单据默认参数 开始
                    parameters.put("P_BillAdduserTel", addPersonnel.getTelphone());

                    parameters.put("P_PRINTUSER", sysUser.getUsername());
                    parameters.put("P_PrintDate", new Date());
                    parameters.put("P_OrderId", saleRejectEnter.getId());

                    parameters.put("P_Customerid", customerInfo.getId());

                    parameters.put("P_CustomerAddr", customerInfo.getAddress());
                    parameters.put("P_Contact", customerInfo.getContact());    //通用版，使用contact
                    parameters.put("P_ContactTel", customerInfo.getMobile());

                    parameters.put("P_BillDate", saleRejectEnter.getBusinessdate());
                    parameters.put("P_BillRemark", saleRejectEnter.getRemark());
                    parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                    parameters.put("P_Storage", saleRejectEnter.getStoragename());

                    parameters.put("P_RejectBillType", saleRejectEnter.getSourcetype());
                    if ("2".equals(saleRejectEnter.getSourcetype())) {
                        parameters.put("P_RejectBillTypeName", "直退退货");
                    } else if ("1".equals(saleRejectEnter.getSourcetype())) {
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
                    SaleRejectEnter orderInfo = (SaleRejectEnter) CommonUtils.deepCopy(saleRejectEnter);
                    orderInfo.setBillDetailList(null);
                    parameters.put("P_OrderInfo", orderInfo);
                    /*共用参数*/
                    parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                    agprintServiceImpl.setTempletCommonParameter(parameters);
                    JasperPrint jrprint =
                            JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));
                    if (null != jrprint) {
                        jrprint.setName("销售退货验收-" + saleRejectEnter.getId());
                        jrlist.add(jrprint);

                        if (!isview) {
                            Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                            callbackparamMap.put("id", saleRejectEnter.getId());
                            callbackparamMap.put("rejectBillId", rejectBill.getId());
                            callbackparamMap.put("rejectBillPrintCount", rejectBill.getPrinttimes());    //退货通知单打印次数
                            callbackparamMap.put("callback", "updateprinttimes");
                            callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                            String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                            jrprint.setProperty("agprint_callback_params", callbackparams);


                            orderPrintIdMap.put(saleRejectEnter.getId(), saleRejectEnter.getId());
                            rejectPrintIdMap.put(rejectBill.getId(), rejectBill.getId());


                            if (StringUtils.isNotEmpty(printJob.getId())) {
                                //打印次数更新回调方法
                                Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                                //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                                //实际打印的单据号
                                datahtmlparamMap.put("printOrderId", rejectBill.getId());
                                //打印任务编号
                                datahtmlparamMap.put("printJobId", printJob.getId());
                                //页面点击时的单据号
                                datahtmlparamMap.put("printSourceOrderId", "");
                                String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                                jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
                                //打印回调处理
                                PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                                printJobCallHandle.setJobid(printJob.getId());
                                printJobCallHandle.setClassname("rejectBillCheckPrintAction");
                                printJobCallHandle.setMethodname("rejectBillCheckReflectPrintCallBackHandle");
                                printJobCallHandle.setMethodparam(callbackparams);
                                printJobCallHandle.setPages(jrprint.getPages().size());
                                printJobCallHandle.setPrintorderid(rejectBill.getId());
                                printJobCallHandle.setPrintordername("退货通知单");
                                printJobCallHandle.setSourceorderid(saleRejectEnter.getId());
                                printJobCallHandle.setSourceordername("退货进货单");
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
                if (rejectPrintIdMap.size() > 0) {
                    List<String> rejectPrintIdList = new ArrayList(rejectPrintIdMap.values());
                    String[] printarr = (String[]) rejectPrintIdList.toArray(new String[rejectPrintIdList.size()]);
                    rejectCanprintIds = StringUtils.join(printarr, ",");
                }
                if (orderPrintIdMap.size() > 0) {
                    List<String> orderPrintIdList = new ArrayList(orderPrintIdMap.values());
                    String[] printarr = (String[]) orderPrintIdList.toArray(new String[orderPrintIdList.size()]);
                    orderCanprintIds = StringUtils.join(printarr, ",");
                }
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("销售退货验收打印申请：");
                //printLogsb.append("申请打印的销售退货验收编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售退货验收编号： " + rejectCanprintIds);
                printLogsb.append("打印的数据退货入库单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称： 销售退货验收打印。");
                addPrintLogInfo("PrintHandle-rejectBillCheck", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-销售退货验收打印异常：");
                //printLogsb.append("申请打印的销售退货验收编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售退货验收编号： " + rejectCanprintIds);
                printLogsb.append("打印的数据退货入库单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-销售退货验收打印预览异常：");
            }
            printLogsb.append("Exception ：rejectBillCheckPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-rejectBillCheck", printLogsb.toString(), null);
            logger.error("销售退货验收打印及预览处理异常", ex);


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
    public void rejectBillCheckPrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新销售验收打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-rejectBillCheck", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新销售验收打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-rejectBillCheck", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        //实际打印的单据编号
        String id = (String) map.get("id");
        if (null == id) {
            id = "";
        }
        String rejectBillId = (String) map.get("rejectBillId");
        String rejectBillPrintCount = (String) map.get("rejectBillPrintCount");
        if (rejectBillPrintCount == null) {
            rejectBillPrintCount = "";
        }
        if (!"".equals(id.trim())) {
            //printLogsb.append("申请打印的销售退货入库单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售退货入库单编号： " + id);
            try {
                //更新验收单打印次数
                if (rejectBillId != null && !"".equals(rejectBillId.trim())) {
                    RejectBill rejectBill = salesRejectBillService.getPureRejectBill(rejectBillId);
                    if (rejectBill != null) {
                        if (rejectBill.getYsprinttimes() == null) {
                            rejectBill.setYsprinttimes(0);
                        }
                        if (rejectBill.getYsprinttimes().toString().equals(rejectBillPrintCount.trim())) {
                            rejectBill.setYsprinttimes(1);
                            salesRejectBillService.updateOrderPrinttimes(rejectBill);
                        }
                    } else {
                        printLogsb.append("未找到销售退货通知单。");
                    }
                } else {
                    printLogsb.append("未找到销售退货通知单。");
                }
            } catch (Exception ex) {
                printLogsb.append("更新销售退货通知单次数出错，编号： " + rejectBillId);
                logger.error("销售退货验收更新打印次数异常", ex);
            }
            try {
                //更新销售退货入库单打印次数
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
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无销售退货入库单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：销售退货验收单打印。");
        printLogsb.append(" 销售退货验收单编号：" + rejectBillId);
        printLogsb.append(" 销售退货入库单编号：" + id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-rejectBillCheck", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 反射打印回调
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public boolean rejectBillCheckReflectPrintCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新销售验收打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-rejectBillCheck", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新销售验收打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-rejectBillCheck", printLogsb.toString(), null);
            return isok;
        }
        //实际打印的单据编号
        String id = (String) map.get("id");
        if (null == id) {
            id = "";
        }
        String rejectBillId = (String) map.get("rejectBillId");
        Integer rejectBillPrintCount = null;
        if(map.containsKey("rejectBillPrintCount")){
            rejectBillPrintCount=(Integer) map.get("rejectBillPrintCount");
        }
        if (rejectBillPrintCount == null) {
            rejectBillPrintCount = 0;
        }
        if (!"".equals(id.trim())) {
            //printLogsb.append("申请打印的销售退货入库单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售退货入库单编号： " + id);
            try {
                //更新验收单打印次数
                if (rejectBillId != null && !"".equals(rejectBillId.trim())) {
                    RejectBill rejectBill = salesRejectBillService.getPureRejectBill(rejectBillId);
                    if (rejectBill != null) {
                        if (rejectBill.getYsprinttimes() == null) {
                            rejectBill.setYsprinttimes(0);
                        }
                        if (rejectBillPrintCount==rejectBill.getYsprinttimes()) {
                            rejectBill.setYsprinttimes(1);
                            salesRejectBillService.updateOrderPrinttimes(rejectBill);
                        }
                    } else {
                        printLogsb.append("未找到销售退货通知单。");
                    }
                } else {
                    printLogsb.append("未找到销售退货通知单。");
                }
            } catch (Exception ex) {
                printLogsb.append("更新销售退货通知单次数出错，编号： " + rejectBillId);
                logger.error("销售退货通知单更新打印次数异常", ex);
            }
            try {
                //更新销售退货入库单打印次数
                SaleRejectEnter saleRejectEnter = saleRejectEnterService.getSaleRejectEnterPureInfo(id);
                if (null != saleRejectEnter) {
                    saleRejectEnter.setPrinttimes(1);
                    saleRejectEnterService.updateOrderPrinttimes(saleRejectEnter);
                    printLogsb.append(" 退货验收打印更新销售退货入库单打印次数成功");
                    isok = true;
                } else {
                    printLogsb.append(" 退货验收打印更新销售退货入库单打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("退货验收打印更新销售退货入库单打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无销售退货入库单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：销售退货验收单打印。");
        printLogsb.append(" 销售退货验收单编号：" + rejectBillId);
        printLogsb.append(" 销售退货入库单编号：" + id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-rejectBillCheck", printLogsb.toString(), null);
        return isok;
    }
}

