/**
 * @(#)DeliveryPrintAction.java
 * @author zhanghonghui
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-30 zhanghonghui 创建版本
 */
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
import com.hd.agent.storage.model.Delivery;
import com.hd.agent.storage.model.DeliveryCustomer;
import com.hd.agent.storage.model.DeliverySaleOut;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.service.IDeliveryService;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * 交接单相关打印
 * @author zhanghonghui
 */
public class DeliveryPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(DeliveryPrintAction.class);

    private IDeliveryService deliveryService;

    public IDeliveryService getDeliveryService() {
        return deliveryService;
    }

    public void setDeliveryService(IDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
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
     * 交接单打印预览
     *
     * @return
     * @throws Exception
     * @author yezhenyu
     * @date Jun 25, 2014
     */
    @UserOperateLog(key = "PrintView", type = 0)
    public String deliveryCustomerPrintView() throws Exception {
        return deliveryCustomerPrintHandle(true);
    }

    /**
     * 交接单打印
     *
     * @return
     * @throws Exception
     * @author yezhenyu
     * @date Jun 25, 2014
     */
    @UserOperateLog(key = "Print", type = 0)
    public String deliveryCustomerPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            deliveryCustomerCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return deliveryCustomerPrintHandle(isview);
        }
        return null;
    }

    /**
     * 交接单打印,打印预览处理
     *
     * @param isview
     * @throws Exception
     * @author yezhenyu
     * @date Jun 25, 2014
     */
    @UserOperateLog(key = "Print", type = 0)
    private String deliveryCustomerPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "交接单打印-" + CommonUtils.getDataNumberSeconds());
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
        Date printDate = new Date();
        String canprintIds = "";
        try {

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("交接单打印");
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
                ajaxResultMap.put("printname", "交接单打印任务-" + printJob.getId());
            }

            Map map = new HashMap();
            if (!isview) {
                // map.put("status", "2");

                String printlimit = getPrintLimitInfo();
                map.put("notphprint", printlimit);
            }
            map.put("idarrs", idarrs);
            map.put("showdetail", "1");
            map.put("detailorderbygoodsid", "1");

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();
            String[] idArr = idarrs.split(",");
            for (String id : idArr) {
                Map deliveryCustomerMap = deliveryService.getDeliveryCustomerList(id);
                if (deliveryCustomerMap == null || deliveryCustomerMap.size() == 0) {
                    continue;
                }
                List<DeliveryCustomer> detailList = (List<DeliveryCustomer>) deliveryCustomerMap.get("list");
                if (detailList == null || detailList.size() == 0) {
                    continue;
                }
                // 将交接单排序 先按单据数排,再按客户编号排

                // 插入总计行
                BigDecimal auxnum = (BigDecimal) deliveryCustomerMap.get("auxnum");
                BigDecimal auxremainder = (BigDecimal) deliveryCustomerMap.get("auxremainder");
                BigDecimal collectionamount = (BigDecimal) deliveryCustomerMap.get("collectionamount");
                BigDecimal boxnum = (BigDecimal) deliveryCustomerMap.get("boxnum");
                BigDecimal volume = (BigDecimal) deliveryCustomerMap.get("volume");
                BigDecimal weight = (BigDecimal) deliveryCustomerMap.get("weight");
                int billnums = Integer.parseInt(deliveryCustomerMap.get("billnums").toString());
                BigDecimal salesamount = (BigDecimal) deliveryCustomerMap.get("salesamount");
                DeliveryCustomer dctotal = new DeliveryCustomer();
                dctotal.setAuxnum(auxnum);
                dctotal.setAuxremainder(auxremainder);
                dctotal.setCollectionamount(collectionamount);
                dctotal.setBoxnum(boxnum);
                dctotal.setBillnums(billnums);
                dctotal.setSalesamount(salesamount);
                //dctotal.setVolume(volume);
                //dctotal.setWeight(weight);
                //detailList.add(dctotal);

                Delivery item = deliveryService.getDelivery(id);
                if (detailList.size() == 0) {
                    continue;
                }
                int customernums = item.getCustomernums();


                //获取打印模板
                Map templetQueryMap = new HashMap();
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "storage_logistics");
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
                //String reportModelFile = servletContext.getRealPath("/ireport/storage_logistics/storage_logistics.jasper");
                Map parameters = null;
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
                if (jreport == null) {
                    continue;
                }

                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                } else {
                    // 将交接单排序 先按单据数排,再按客户编号排
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator("seq asc"));
                }

                List<Saleout> printList = new ArrayList<Saleout>();
                // for(Saleout item : list){

                if (!isview) {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                }
                // List<SaleoutDetail> detailList=item.getSaleoutDetailList();

                // if(null==detailList || detailList.size()==0){
                // continue;
                // }
                // Customer customerInfo=item.getCustomerInfo();
                // if(null==customerInfo){
                // customerInfo=new Customer();
                // }
                parameters = new HashMap();
                //合计
                parameters.put("P_TBillnums", dctotal.getBillnums());
                parameters.put("P_TSalesamount", dctotal.getSalesamount());
                parameters.put("P_TBoxnum", dctotal.getBoxnum());

                parameters.put("P_Title", "日销售物流汇总交接单 （" + item.getLinename() + "）"); //
                parameters.put("P_ID", id);
                parameters.put("P_Businessdate", item.getBusinessdate());
                parameters.put("P_Carname", item.getCarname()); //
                parameters.put("P_Drivername", item.getDrivername()); //
                parameters.put("P_Followname", item.getFollowname()); //
                parameters.put("P_Boxnum", boxnum.setScale(2, BigDecimal.ROUND_HALF_UP)); //
                parameters.put("P_Volume", volume.setScale(2, BigDecimal.ROUND_HALF_UP)); //
                parameters.put("P_Weight", weight.setScale(2, BigDecimal.ROUND_HALF_UP)); //
                parameters.put("P_TotalCustomernums", "共：" + customernums + "   家"); //
                // String customer="";
                //
                // if(StringUtils.isNotEmpty(customerInfo.getId())){
                // customer=customerInfo.getId()+" ";
                // }
                // if(StringUtils.isNotEmpty(customerInfo.getName())){
                // customer+=customerInfo.getName();
                // }
                // parameters.put("P_Customer", customer);

                // parameters.put("P_PrintTimes", item.getPrinttimes());


                /**打印模板参数用的单据信息**/
                Delivery orderInfo = (Delivery) CommonUtils.deepCopy(item);

                parameters.put("P_OrderInfo", orderInfo);

					/*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(
                        detailList));
                if (null != jrprint) {

                    if (!isview) {
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", item.getId());
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("rand", CommonUtils.getRandomWithTime()); // 随机数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        item.setPrinttimes(null);
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
                            datahtmlparamMap.put("printSourceOrderId", item.getId());
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(printJob.getId());
                            printJobCallHandle.setClassname("deliveryPrintAction");
                            printJobCallHandle.setMethodname("deliveryCustomerReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("交接单");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("交接单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrprint.setName("交接单-" + item.getId());
                    jrlist.add(jrprint);
                }
                // }
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
                printLogsb.append("配送单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：在配送单-交接单打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-deliveryCustomer", printLogsb.toString(), null);
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

        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-在配送单-交接单打印异常：");
                // printLogsb.append("申请打印仓库发货单编号："+idarrs);
                // printLogsb.append(" 。");
                printLogsb.append("配送单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在配送单-交接单打印预览异常：");
                // printLogsb.append("申请预览仓库发货单编号："+idarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：deliveryCustomerPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-deliveryCustomer", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("交接单打印及预览处理异常", ex);


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
     * 交接单打印回调处理
     *
     * @param printcallback
     * @throws Exception
     * @author yezhenyu
     * @date Jun 25, 2014
     */
    private void deliveryCustomerCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        // printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deliveryCustomer", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deliveryCustomer", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            printLogsb.append("配送单编号： " + id);

            try {
                deliveryService.updatePrintTimes(id);
                printLogsb.append(" 更新配送单打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新配送单打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在配送单-交接单更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无配送单编号信息");
        }
        printLogsb.append(" 。");

        printLogsb.append(" 更新响应来源于：在配送单-交接单。");
        printLogsb.append(" 打印数据来源(配送单编号)：");
        printLogsb.append(id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-deliveryCustomer", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 反射调用交接单打印回调处理
     *
     * @param printcallback
     * @throws Exception
     * @author yezhenyu
     * @date Jun 25, 2014
     */
    public boolean deliveryCustomerReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        // printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deliveryCustomer", printLogsb.toString(), null);
            return isok;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deliveryCustomer", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            printLogsb.append("配送单编号： " + id);

            try {
                deliveryService.updatePrintTimes(id);
                isok = true;
                printLogsb.append(" 更新配送单打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新配送单打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在配送单-交接单更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无配送单编号信息");
        }
        printLogsb.append(" 。");

        printLogsb.append(" 更新响应来源于：在配送单-交接单。");
        printLogsb.append(" 打印数据来源(配送单编号)：");
        printLogsb.append(id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-deliveryCustomer", printLogsb.toString(), null);
        return isok;
    }


    /**
     * 配送单明细打印
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-10-14
     */
    @UserOperateLog(key = "PrintView", type = 0)
    public String deliveryBillPrintView() throws Exception {
        return deliveryBillPrintHandle(true);
    }

    /**
     * 交接单打印
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-10-14
     */
    @UserOperateLog(key = "Print", type = 0)
    public String deliveryBillPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            deliveryBillCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return deliveryBillPrintHandle(isview);
        }
        return null;
    }

    /**
     * 配送单明细打印,打印预览处理
     * @param isview
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-10-14
     */
    private String deliveryBillPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "配送单明细打印-" + CommonUtils.getDataNumberSeconds());
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
        Date printDate = new Date();
        String canprintIds = "";
        try {

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("配送单明细");
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
                ajaxResultMap.put("printname", "配送单明细打印任务-" + printJob.getId());
            }

            Map map = new HashMap();
            if (!isview) {
                // map.put("status", "2");

                String printlimit = getPrintLimitInfo();
                map.put("notphprint", printlimit);
            }
            map.put("idarrs", idarrs);
            map.put("showdetail", "1");
            map.put("detailorderbygoodsid", "1");

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList = new ArrayList<String>();
            String[] idArr = idarrs.split(",");
            for (String id : idArr) {
                Map billmap = deliveryService.getDeliverySaleoutList(id);

                List<DeliverySaleOut> saleoutList = (List<DeliverySaleOut>) CommonUtils.deepCopy(billmap.get("list"));
                for(DeliverySaleOut deliverySaleOut : saleoutList) {
                    if ("3".equals(deliverySaleOut.getBilltype())) {
                        deliverySaleOut.setOrderid(deliverySaleOut.getSaleoutid());
                    }
                }

                billmap.put("list", saleoutList);

                if (null == billmap || billmap.size() == 0) {
                    continue;
                }
                List<DeliverySaleOut> detailList = (List<DeliverySaleOut>) billmap.get("list");
                if (null == detailList || detailList.size() == 0) {
                    continue;
                }
                Delivery item = deliveryService.getDelivery(id);


                //获取打印模板
                Map templetQueryMap = new HashMap();
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "storage_logistics_bill");
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
                Map parameters = null;
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
                if (null == jreport) {
                    continue;
                }
                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }
                List<Saleout> printList = new ArrayList<Saleout>();

                if (!isview) {
                    item.setPrinttimes(item.getPrinttimes() + 1);
                }
                parameters = new HashMap();

                if (null != item) {
                    parameters.put("P_OrderInfo", item);
                }
                    /*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                JasperPrint jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(
                        detailList));
                if (null != jrprint) {

                    if (!isview) {
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", item.getId());
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("rand", CommonUtils.getRandomWithTime()); // 随机数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        item.setPrinttimes(null);
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
                            datahtmlparamMap.put("printSourceOrderId", item.getId());
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(printJob.getId());
                            printJobCallHandle.setClassname("deliveryPrintAction");
                            printJobCallHandle.setMethodname("deliveryBillReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("配送单明细");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("配送单明细");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrprint.setName("配送单-" + item.getId());
                    jrlist.add(jrprint);
                }
                // }
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
                printLogsb.append("配送单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：在配送单-发货明细打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-deliveryBill", printLogsb.toString(), null);
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

        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-在配送单-发货明细打印异常：");
                // printLogsb.append("申请打印仓库发货单编号："+idarrs);
                // printLogsb.append(" 。");
                printLogsb.append("配送单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在配送单-发货明细打印预览异常：");
                // printLogsb.append("申请预览仓库发货单编号："+idarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：deliveryBillPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-deliveryBill", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("配送单明细打印及预览处理异常", ex);


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
     * 配送单明细打印回调处理
     * @param printcallback
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-10-14
     */
    private void deliveryBillCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        // printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deliveryBill", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deliveryBill", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            printLogsb.append("配送单编号： " + id);

            try {
                deliveryService.updatePrintTimes(id);
                printLogsb.append(" 更新配送单打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新配送单打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("配送单明细更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无配送单编号信息");
        }
        printLogsb.append(" 。");

        printLogsb.append(" 更新响应来源于：在配送单-发货明细。");
        printLogsb.append(" 打印数据来源(配送单编号)：");
        printLogsb.append(id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-deliveryBill", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 反射调用配送单明细打印回调处理
     * @param printcallback
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-10-14
     */
    public boolean deliveryBillReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        // printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deliveryBill", printLogsb.toString(), null);
            return isok;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBackHandle-deliveryBill", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            printLogsb.append("配送单编号： " + id);

            try {
                deliveryService.updatePrintTimes(id);
                printLogsb.append(" 更新配送单打印次数成功");
                isok = true;
            } catch (Exception ex) {
                printLogsb.append(" 更新配送单打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("配送单明细更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无配送单编号信息");
        }
        printLogsb.append(" 。");

        printLogsb.append(" 更新响应来源于：在配送单-发货明细。");
        printLogsb.append(" 打印数据来源(配送单编号)：");
        printLogsb.append(id);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBackHandle-deliveryBill", printLogsb.toString(), null);
        return isok;
    }
}

