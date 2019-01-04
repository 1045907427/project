/**
 * @(#)SaleOutPrintAction.java
 * @author zhanghonghui
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-22 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action.storage;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.PrintTemplet;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IStorageSaleOutPrintService;
import com.hd.agent.storage.service.IStorageSaleOutService;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

/**
 * 发货单打印Action
 *
 * @author zhanghonghui
 */
public class SaleOutPrintAction extends BaseFilesAction {
    private static final Logger logger = Logger.getLogger(SaleOutPrintAction.class);
    //发货单
    private IStorageSaleOutService storageSaleOutService;


    public IStorageSaleOutService getStorageSaleOutService() {
        return storageSaleOutService;
    }

    public void setStorageSaleOutService(
            IStorageSaleOutService storageSaleOutService) {
        this.storageSaleOutService = storageSaleOutService;
    }

    /**
     * 发货单打印业务层
     */
    private IStorageSaleOutPrintService storageSaleOutPrintService;

    public IStorageSaleOutPrintService getStorageSaleOutPrintService() {
        return storageSaleOutPrintService;
    }

    public void setStorageSaleOutPrintService(
            IStorageSaleOutPrintService storageSaleOutPrintService) {
        this.storageSaleOutPrintService = storageSaleOutPrintService;
    }


    private AgprintServiceImpl agprintServiceImpl;

    public AgprintServiceImpl getAgprintServiceImpl() {
        return agprintServiceImpl;
    }

    public void setAgprintServiceImpl(AgprintServiceImpl agprintServiceImpl) {
        this.agprintServiceImpl = agprintServiceImpl;
    }

    /**
     * 销售订单服务层
     */
    protected IOrderService salesOrderService;

    public IOrderService getSalesOrderService() {
        return salesOrderService;
    }

    public void setSalesOrderService(IOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }
    //endregion 参数初始化

    /**
     * 输出报表
     * @param jrlist
     * @param isview
     * @param viewtype
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-12-30
     */
    private void renderJasperReport(List<JasperPrint> jrlist, Boolean isview, String viewtype) throws Exception {
        Map<String, Object> reportMap = new HashMap<String, Object>();
        reportMap.put("response", response);
        reportMap.put("jreportList", jrlist);
        reportMap.put("isview", isview);
        reportMap.put("viewtype", viewtype);
        reportMap.put("PDFOWNERPASSWORD", getSysParamValue("PDFOWNERPASSWORD"));
        JasperReportUtils.renderJasperReport(reportMap);
    }

    /**
     * 配货单打印预览
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    public String storageOrderblankPrintView() throws Exception {
        return storageOrderblankPrintHandle(true);
    }

    /**
     * 配货单打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    public String storageOrderblankPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            storageOrderblankCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return storageOrderblankPrintHandle(isview);
        }
        return null;
    }

    /**
     * 配货单打印及预览处理
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    private String storageOrderblankPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "发货单-配货单打印-" + CommonUtils.getDataNumberSeconds());
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
        String templetid = request.getParameter("templetid");
        Map printTempletSettingFistMap = null;
        SysUser sysUser = getSysUser();
        Date printDate = new Date();
        String saleoutCanprintIds = "";
        String orderCanprintIds = "";
        try {
            Map map = new HashMap();
            if (!isview) {
                //map.put("status", "2");

                //String printlimit=getPrintLimitInfo();
                //map.put("notphprint", printlimit);
            }
            map.put("idarrs", idarrs);
            map.put("showdetail", "1");
            //map.put("showPCustomerName", "true");
            map.put("noDataSql", "true");//去掉权限判断

            List<Saleout> list = storageSaleOutService.showSaleOutListBy(map);
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
            templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
            templetQueryMap.put("code", "storage_orderblank");

            if (null != templetid && !"".equals(templetid)) {
                templetQueryMap.put("templetid", templetid);
            }
            Map templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);
            //打印模板文件
            String printTempletFile = (String) templetResultMap.get("printTempletFile");
            if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到打印模板");
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
            //第一个可用的打印模板配置信息
            if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                printTempletSettingFistMap = new HashMap();
                printTempletSettingFistMap.putAll(templetResultMap);
            }
            //打印内容排序策略
            String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");
            //String reportModelFile = servletContext.getRealPath("/ireport/storage_orderblank/storage_orderblank.jasper");
            //打印参数
            Map parameters = null;
            //加载打印模板信息
            JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
            if (null == jreport) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到打印模板");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                } else {
                    return null;
                }
            }
            //生成的模板列表
            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            //实际打印单据编号
            Map<String, String> orderPrintIdMap = new HashMap<String, String>();

            //打印条数
            int orderPrintCount = 0;

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("发货单-配货单据打印");
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
                ajaxResultMap.put("printname", "发货单按库位打印任务-" + printJob.getId());
            }

            //查询是否需要客户回款信息,1需要，0不需要，默认为0
            String isPrintShowCustomerYSKInfo = getSysParamValue("isPrintShowCustomerYSKInfo");
            if (null == isPrintShowCustomerYSKInfo || "".equals(isPrintShowCustomerYSKInfo.trim())) {
                isPrintShowCustomerYSKInfo = "0";
            } else {
                isPrintShowCustomerYSKInfo = isPrintShowCustomerYSKInfo.trim();
            }


            for (Saleout item : list) {
                //第一次打印时，配货单据次数为空
                if (null == item.getPhprinttimes()) {
                    item.setPhprinttimes(0);
                }
                //设置当前打印次数
                item.setPhprinttimes(item.getPhprinttimes() + 1);
                item.setPrinttimes(item.getPhprinttimes());
                //打印单据明细
                List<SaleoutDetail> detailList = item.getSaleoutDetailList();

                if (null == detailList || detailList.size() == 0) {
                    continue;
                }
                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }
                Customer customerInfo = item.getCustomerInfo();
                if (null == customerInfo) {
                    customerInfo = new Customer();
                }

                SysUser addPersonnel = null;
                Personnel salePersonnel = null;

                if (StringUtils.isNotEmpty(item.getSalesuser())) {
                    salePersonnel = getPersonnelInfoById(item.getSalesuser());
                }
                if (null == salePersonnel && StringUtils.isNotEmpty(customerInfo.getSalesuserid())) {
                    salePersonnel = getPersonnelInfoById(customerInfo.getSalesuserid());
                }
                if (null == salePersonnel) {
                    salePersonnel = new Personnel();
                }
                if (null == addPersonnel || (StringUtils.isNotEmpty(addPersonnel.getUserid()) && !addPersonnel.getUserid().equals(item.getAdduserid()))) {
                    addPersonnel = getSysUserById(item.getAdduserid());
                }
                if (null == addPersonnel) {
                    addPersonnel = new SysUser();
                }

                parameters = new HashMap();
                //是否显示
                parameters.put("P_IsShow_CustomerAmountYSKE", "1".equals(isPrintShowCustomerYSKInfo) ? "1" : "0");
                //客户应收款
                parameters.put("P_Customer_Amount_YSK", 0);
                //客户余额
                parameters.put("P_Customer_Amount_YE", 0);
                if ("1".equals(isPrintShowCustomerYSKInfo) && null != customerInfo) {
                    Map cusMap = salesOrderService.showCustomerReceivableInfoData(customerInfo.getId());
                    if (null != cusMap && cusMap.size() > 0) {

                        parameters.put("P_Customer_Amount_YSK", cusMap.get("receivableAmount"));
                        parameters.put("P_Customer_Amount_YE", cusMap.get("leftAmount"));
                    }
                }
                //销售员
                parameters.put("P_SaleUser", salePersonnel.getName());
                //销售员电话
                parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                //打印人
                parameters.put("P_PRINTUSER", sysUser.getName());
                //打印人
                parameters.put("P_PrintUser", sysUser.getName());
                //打印时间
                parameters.put("P_PrintDate", printDate);
                //销售订单号
                parameters.put("P_SaleId", item.getSaleorderid());
                //业务日期
                parameters.put("P_Businessdate", item.getBusinessdate());
                //业务日期：字符串格式
                parameters.put("P_Businessdate_str", item.getBusinessdate());
                ////业务日期
                parameters.put("P_BillDate", item.getBusinessdate());
                //订单备注
                parameters.put("P_BillRemark", item.getRemark());
                //订单备注
                parameters.put("P_Remark", item.getRemark());
                //打印次数
                parameters.put("P_PrintTimes", item.getPrinttimes());
                //仓库名称
                parameters.put("P_Storage", item.getStoragename());
                //添加用户名称
                parameters.put("P_Adduser", item.getAddusername());
                //添加用户联系电话
                parameters.put("P_BillAdduserTel", addPersonnel.getTelphone());

				/*客户相关*/
                String customer = "";

                if (StringUtils.isNotEmpty(customerInfo.getName())) {
                    customer += customerInfo.getName();
                }

                parameters.put("P_CustomerInfo", customerInfo);
                //客户编号+名称
                parameters.put("P_Customer", customer);
                //客户编号
                parameters.put("P_Customerno", customerInfo.getId());
                //客户编号
                parameters.put("P_Customerid", customerInfo.getId());
                //客户名称
                parameters.put("P_CustomerName", customerInfo.getName());
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

                String pCustomerName = "";
                if (StringUtils.isNotEmpty(customerInfo.getPid())) {
                    pCustomerName = customerInfo.getPid();
                    parameters.put("P_PCustomerId", customerInfo.getPid());
                } else {
                    parameters.put("P_PCustomerId", "");
                }
                if (StringUtils.isNotEmpty(customerInfo.getPname())) {
                    pCustomerName = pCustomerName + " " + customerInfo.getPname();
                }
                parameters.put("P_PCustomerName", pCustomerName);
                /*客户相关*/

                /**打印模板参数用的单据信息**/
                Saleout orderInfo = (Saleout) CommonUtils.deepCopy(item);
                orderInfo.setSaleoutDetailList(null);

                parameters.put("P_OrderInfo", orderInfo);

				/*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                //每页条数
                Integer countperpage = 0;
                //是否填充空行
                String isfillblank = "0";
                countperpage = (Integer) printTempletSettingFistMap.get("countperpage");
                if (null == countperpage || countperpage < 0) {
                    countperpage = 0;
                }
                isfillblank = (String) printTempletSettingFistMap.get("isfillblank");
                if (isfillblank == null || !"1".equals(isfillblank)) {
                    isfillblank = "0";
                }

                List<SaleoutDetail> tmpdList = detailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(detailList, countperpage);
                }

                JasperPrint jrprint =
                        JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));
                if (null != jrprint) {

                    if (!isview) {
                        //打印次数更新回调参数
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", item.getId());
                        callbackparamMap.put("callback", "updatephprinttimes");
                        if (item.getPhprinttimes() > 0) {
                            //数据库里的打印次数
                            callbackparamMap.put("orderPrintCount", item.getPhprinttimes() - 1);
                        } else {
                            callbackparamMap.put("orderPrintCount", item.getPhprinttimes());    //当前发货通知单打印次数
                        }
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        item.setPrinttimes(null);
                        orderPrintIdMap.put(item.getId(), item.getId());

                        if (StringUtils.isNotEmpty(printJob.getId())) {
                            //打印次数更新回调方法
                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", item.getId());
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);
                            //打印回调处理
                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(printJob.getId());
                            printJobCallHandle.setClassname("saleOutPrintAction");
                            printJobCallHandle.setMethodname("storageOrderblankPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("发货单");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("发货单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");//次数更新
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }

                    }
                    jrprint.setName("配货单-" + item.getId());
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

                if (orderPrintIdMap.size() > 0) {
                    List<String> orderPrintIdList = new ArrayList(orderPrintIdMap.values());
                    String[] printarr = (String[]) orderPrintIdList.toArray(new String[orderPrintIdList.size()]);
                    orderCanprintIds = StringUtils.join(printarr, ",");
                }
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                //printLogsb.append("申请打印仓库发货单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("仓库发货单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：在仓库发货单中打印-配货打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-storageOrderblank", printLogsb.toString(), null);

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
                //输出打印报表
                renderJasperReport(jrlist, isview, viewtype);
            }
            jrlist = null;
            list = null;

        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-在仓库发货单中-配货打印异常：");
                //printLogsb.append("申请打印仓库发货单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("仓库发货单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在仓库发货单中-配货预览异常：");
                //printLogsb.append("申请预览仓库发货单编号："+idarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：storageOrderblankPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-storageOrderblank", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("在仓库发货单中-配货打印及预览处理异常", ex);


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
     * 配货单打印回调处理
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public void storageOrderblankCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageOrderblank", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageOrderblank", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        boolean uplogFlag = true;
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            //printLogsb.append("申请打印仓库发货单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("仓库发货单编号： " + id);

            try {
                Integer orderPrintCount = null;
                if(map.containsKey("orderPrintCount")){
                    orderPrintCount = new Integer(map.get("orderPrintCount").toString());
                }
                if (null != orderPrintCount) {
                    int iPrintCount = orderPrintCount;
                    Saleout saleout = storageSaleOutService.getSaloutPrinttimesById(id);
                    if (null != saleout && null != saleout.getPhprinttimes()
                            && iPrintCount < saleout.getPhprinttimes()) {
                        printLogsb.append(" 更新打印次数已经更新");
                        uplogFlag = false;
                    } else {
                        Map paramMap = new HashMap();
                        paramMap.put("phprinttimes", "true");
                        paramMap.put("id", id);
                        storageSaleOutPrintService.updateSaleoutPrinttimesBy(paramMap);
                        printLogsb.append(" 更新打印次数成功");
                    }
                } else {
                    Saleout saleout = storageSaleOutService.getSaloutPrinttimesById(id);
                    if (null != saleout) {
                        if (null == saleout.getPhprinttimes()) {
                            Map paramMap = new HashMap();
                            paramMap.put("phprinttimes", "true");
                            paramMap.put("id", id);
                            storageSaleOutPrintService.updateSaleoutPrinttimesBy(paramMap);
                        }
                    }
                    printLogsb.append(" 更新打印次数成功");
                }
                printLogsb.append(" 更新配货打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新配货打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在仓库发货单中-配货更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：在仓库发货单中打印-配货单。");
        printLogsb.append(" 打印数据来源(仓库发货单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        if (uplogFlag) {
            addPrintLogInfo("PrintCallHandle-storageOrderblank", printLogsb.toString(), null);
        }
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 通过后台反射方式打印回调处理
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-7-8
     */
    public boolean storageOrderblankPrintReflectCallBackHandle(String printcallback) throws Exception {
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageOrderblank", printLogsb.toString(), null);
            return false;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageOrderblank", printLogsb.toString(), null);
            return false;
        }
        String id = (String) map.get("id");
        boolean isok = false;
        boolean uplogFlag = true;
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印发货单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("发货单编号： " + id);
            try {
                String orderPrintCount = (String) map.get("orderPrintCount");
                if (null != orderPrintCount && !"".equals(orderPrintCount.trim())) {
                    int iPrintCount = -1;
                    if (null != orderPrintCount && !"".equals(orderPrintCount.trim()) && StringUtils.isNumeric(orderPrintCount.trim())) {
                        iPrintCount = Integer.parseInt(orderPrintCount.trim());
                    }
                    Saleout saleout = storageSaleOutService.getSaloutPrinttimesById(id);
                    if (null != saleout && null != saleout.getPhprinttimes()
                            && iPrintCount != -1
                            && iPrintCount < saleout.getPhprinttimes()) {
                        printLogsb.append(" 更新打印次数已经更新");
                        uplogFlag = false;
                    } else {
                        Map paramMap = new HashMap();
                        paramMap.put("phprinttimes", "true");
                        paramMap.put("id", id);
                        storageSaleOutPrintService.updateSaleoutPrinttimesBy(paramMap);
                        printLogsb.append(" 更新打印次数成功");
                    }
                    isok = true;
                } else {
                    Saleout saleout = storageSaleOutService.getSaloutPrinttimesById(id);
                    if (null != saleout) {
                        if (null == saleout.getPhprinttimes()) {
                            Map paramMap = new HashMap();
                            paramMap.put("phprinttimes", "true");
                            paramMap.put("id", id);
                            storageSaleOutPrintService.updateSaleoutPrinttimesBy(paramMap);
                        }
                    }
                    printLogsb.append(" 更新打印次数成功");
                    isok = true;
                }

            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在仓库发货单中-配货更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于： 在发货单中打印-按仓库打印 。");
        printLogsb.append(" 。");
        if (uplogFlag) {
            addPrintLogInfo("PrintCallHandle-storageOrderblank", printLogsb.toString(), null);
        }
        return isok;
    }

    /**
     * 出库单打印-按库位打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    public String storageDeliveryOrderPrintView() throws Exception {
        return storageDeliveryOrderPrintHandle(true);
    }

    /**
     * 出库单打印-按库位打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "Print", type = 0)
    public String storageDeliveryOrderPrint() throws Exception {

        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            storageDeliveryOrderPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return storageDeliveryOrderPrintHandle(isview);
        }
        return null;
    }

    private String storageDeliveryOrderPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "发货单按库位打印-" + CommonUtils.getDataNumberSeconds());
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

        String printOrder = request.getParameter("printOrder");
        if (null == printOrder || "".equals(printOrder.trim())) {
            printOrder = "1";
        }

        String templetid = request.getParameter("templetid");
        viewtype = viewtype.trim();
        SysUser sysUser = getSysUser();

        String canprintIds = "";
        String printlimit = "0";//1表示限制
        Map printTempletSettingFistMap = null;
        try {
            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();

            Map paramMap = new HashMap();
            paramMap.put("idarrs", idarrs);
            paramMap.put("showdetail", "0"); //不需要在查询单据时，查询出明细信息
            if (!isview) {
                printlimit = getPrintLimitInfo();
				 /*
        		 if("0".equals(printlimit)){
            		 paramMap.put("statusarr", "2,3,4");
        		 }else{
            		 paramMap.put("statusarr", "2,3,4");
        		 }
        		 */
                //paramMap.put("notprint", printlimit);
            }
            paramMap.put("showPCustomerName", "true"); //显示上级客户名称
            paramMap.put("noDataSql", "true");//去掉权限判断
            List<Saleout> list = storageSaleOutService.showSaleOutListBy(paramMap);
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

            //查询是否需要客户回款信息,1需要，0不需要，默认为0
            String isPrintShowCustomerYSKInfo = getSysParamValue("isPrintShowCustomerYSKInfo");
            if (null == isPrintShowCustomerYSKInfo || "".equals(isPrintShowCustomerYSKInfo.trim())) {
                isPrintShowCustomerYSKInfo = "0";
            } else {
                isPrintShowCustomerYSKInfo = isPrintShowCustomerYSKInfo.trim();
            }

            //查询是否要进行拆单
            String saleOrderPrintSplitType = getSysParamValue("saleOrderPrintSplitType"); //销售打印拆单方式，0普通，1整散，2按客户进分按品牌拆单
            if (null == saleOrderPrintSplitType || "".equals(saleOrderPrintSplitType.trim())) {
                saleOrderPrintSplitType = "0";
            } else {
                saleOrderPrintSplitType = saleOrderPrintSplitType.trim();
            }

            Map parameters = null;

            Map<String, String> saleoutPrintIdMap = new HashMap<String, String>();

            BigDecimal saleTaxamount = null;
            BigDecimal saleNoTaxamount = null;
            BigDecimal saleTax = null;
            //打印任务
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("发货单-按库位套打");
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

                boolean isjobflag = agprintServiceImpl.addPrintJob(printJob);
                if (!isjobflag || StringUtils.isEmpty(printJob.getId())) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "申请单据打印时失败");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                }
                ajaxResultMap.put("printJobId", printJob.getId());
                ajaxResultMap.put("printname", "发货单按库位打印任务-" + printJob.getId());
            }

            //获取打印模板
            Map templetQueryMap = new HashMap();
            for (Saleout item : list) {

                paramMap.clear();
                paramMap.put("saleoutid", item.getId());
                //传入客户编号，查询店内码
                paramMap.put("customerid", item.getCustomerid());
                List<SaleoutDetail> detailList = storageSaleOutService.getSaleOutDetailListBy(paramMap);
                if (null == detailList || detailList.size() == 0) {
                    continue;
                }

                templetQueryMap.clear();
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "storage_deliveryorder");
                if (null != templetid && !"".equals(templetid)) {
                    templetQueryMap.put("templetid", templetid);
                }
                //关联客户的参数值
                templetQueryMap.put("linkCustomerid", item.getCustomerid());
                //关联上级客户的参数值
                templetQueryMap.put("linkPCustomerid", item.getPcustomerid());
                //关联部门的参数值
                templetQueryMap.put("linkDeptid", item.getSalesdept());
                //关联销售区域的参数值
                templetQueryMap.put("linkSalesarea", item.getSalesarea());
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
                //String reportModelFile = servletContext.getRealPath("/ireport/storage_deliveryorder/storage_deliveryorder.jasper");
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
                if (jreport == null) {
                    continue;
                }

                if (StringUtils.isNotEmpty(item.getSaleorderid())) {
                    Map saleTotal = salesOrderService.getOrderDetailTotal(item.getSaleorderid());
                    if (null != saleTotal) {
                        if (saleTotal.containsKey("taxamount")) {
                            saleTaxamount = new BigDecimal(saleTotal.get("taxamount").toString());
                        }
                        if (saleTotal.containsKey("notaxamount")) {
                            saleNoTaxamount = new BigDecimal(saleTotal.get("notaxamount").toString());
                        }
                        if (saleTotal.containsKey("tax")) {
                            saleTax = new BigDecimal(saleTotal.get("tax").toString());
                        }
                    }
                }


                Customer customerInfo = item.getCustomerInfo();
                if (null == customerInfo) {
                    customerInfo = new Customer();
                }

                if (null == item.getPhprinttimes()) {
                    item.setPrinttimes(0);
                }

                if (null == item.getPrinttimes()) {
                    item.setPrinttimes(0);
                }
                Saleout orderInfo = (Saleout) CommonUtils.deepCopy(item);
                orderInfo.setSaleoutDetailList(null);

                SysUser addPersonnel = null;
                Personnel salePersonnel = null;

                if (null == addPersonnel || (StringUtils.isNotEmpty(addPersonnel.getUserid()) && !addPersonnel.getUserid().equals(item.getAdduserid()))) {
                    addPersonnel = getSysUserById(item.getAdduserid());
                }
                if (null == addPersonnel) {
                    addPersonnel = new SysUser();
                }
                if (StringUtils.isNotEmpty(item.getSalesuser())) {
                    salePersonnel = getPersonnelInfoById(item.getSalesuser());
                }
                if (null == salePersonnel && StringUtils.isNotEmpty(customerInfo.getSalesuserid())) {
                    salePersonnel = getPersonnelInfoById(customerInfo.getSalesuserid());
                }
                if (null == salePersonnel) {
                    salePersonnel = new Personnel();
                }
                parameters = new HashMap();
                //是否显示
                parameters.put("P_IsShow_CustomerAmountYSKE", "1".equals(isPrintShowCustomerYSKInfo) ? "1" : "0");
                //客户应收款
                parameters.put("P_Customer_Amount_YSK", 0);
                //客户余额
                parameters.put("P_Customer_Amount_YE", 0);
                if ("1".equals(isPrintShowCustomerYSKInfo) && null != customerInfo) {
                    Map cusMap = salesOrderService.showCustomerReceivableInfoData(customerInfo.getId());
                    if (null != cusMap && cusMap.size() > 0) {

                        parameters.put("P_Customer_Amount_YSK", cusMap.get("receivableAmount"));
                        parameters.put("P_Customer_Amount_YE", cusMap.get("leftAmount"));
                    }
                }

                //销售员
                parameters.put("P_SaleUser", salePersonnel.getName());
                //销售员电话
                parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                //打印人
                parameters.put("P_PRINTUSER", sysUser.getName());
                //打印人
                parameters.put("P_PrintUser", sysUser.getName());
                //打印时间
                parameters.put("P_PrintDate", new Date());
                //销售订单号
                parameters.put("P_SaleId", item.getSaleorderid());
                //业务日期
                parameters.put("P_Businessdate", CommonUtils.getBusinessdateByString(item.getBusinessdate()));
                //业务日期：字符串格式
                parameters.put("P_Businessdate_str", item.getBusinessdate());
                ////业务日期
                parameters.put("P_BillDate", item.getBusinessdate());
                //订单备注
                parameters.put("P_BillRemark", item.getRemark());
                //订单备注
                parameters.put("P_Remark", item.getRemark());
                //打印次数
                parameters.put("P_PrintTimes", item.getPrinttimes());
                //仓库名称
                parameters.put("P_Storage", item.getStoragename());
                //添加用户名称
                parameters.put("P_Adduser", item.getAddusername());
                //添加用户联系电话
                parameters.put("P_BillAdduserTel", addPersonnel.getTelphone());
                //销售金额
                parameters.put("P_SaleTaxamount", saleTaxamount);
                //销售无税金额
                parameters.put("P_SaleNoTaxamount", saleNoTaxamount);
                //销售税额
                parameters.put("P_SaleTax", saleTax);

				/*客户相关*/
                String customer = "";

                if (StringUtils.isNotEmpty(customerInfo.getName())) {
                    customer += customerInfo.getName();
                }
                parameters.put("P_CustomerInfo", customerInfo);
                //客户编号+名称
                parameters.put("P_Customer", customer);
                //客户编号
                parameters.put("P_Customerno", customerInfo.getId());
                //客户编号
                parameters.put("P_Customerid", customerInfo.getId());
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
				/*客户相关*/

				/*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);

                String customerBillid = "";
                if (StringUtils.isNotEmpty(item.getSaleorderid())) {
                    customerBillid = salesOrderService.getCustomerBillId(item.getSaleorderid());
                    if (null != customerBillid && !"".equals(customerBillid.trim())) {
                        item.setCustomerbillid(customerBillid);
                    }
                }
                //客户单据信息号
                parameters.put("P_CustomerBillId", customerBillid);
                //单据
                parameters.put("P_OrderInfo", orderInfo);


                Map queryMap = new HashMap();
                //是否预览
                queryMap.put("isview", isview);
                //打印任务编号
                queryMap.put("backHandleJobId", printJob.getId());
                //打印的组装结果列表
                queryMap.put("jrList", jrlist);
                //打印是否排序
                queryMap.put("printOrder", printOrder);
                //打印的参数
                queryMap.put("parametersMap", parameters);
                //发货单
                queryMap.put("saleoutOrder", item);
                //打印报表
                queryMap.put("JasperReport", jreport);
                //已经组装的打印编号
                queryMap.put("saleoutPrintIdMap", saleoutPrintIdMap);
                //发货单明细列表
                queryMap.put("saleoutDetailList", detailList);
                //打印配置项
                queryMap.put("printTempletSettingMap",printTempletSettingFistMap);

                if ("0".equals(saleOrderPrintSplitType)) {
                    getDelieryOrderPrintData(queryMap);
                } else if ("1".equals(saleOrderPrintSplitType)) {
                    getDelieryOrderSplitByStorageZSPrintData(queryMap);
                } else if ("2".equals(saleOrderPrintSplitType)) {
                    getDelieryOrderSplitByBrandPrintData(queryMap);
                } else {
                    getDelieryOrderPrintData(queryMap);
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

                if (saleoutPrintIdMap.size() > 0) {
                    List<String> printIdList = new ArrayList(saleoutPrintIdMap.values());
                    String[] printarr = (String[]) printIdList.toArray(new String[printIdList.size()]);
                    canprintIds = StringUtils.join(printarr, ",");
                }
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                //printLogsb.append("申请打印发货单编号："+idarrs);
                //printLogsb.append(" 。");
                printLogsb.append("发货单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：在发货单中打印-按仓库打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-storageDeliveryOrder", printLogsb.toString(), null);
            }

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
                //输出打印报表
                renderJasperReport(jrlist, isview, viewtype);
            }
            jrlist = null;
            list = null;

        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-在发货单中-按仓库打印异常：");
                //printLogsb.append("申请打印发货单编号："+idarrs);
                printLogsb.append("发货单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在发货单中-按仓库预览异常：");
                //printLogsb.append("申请打印预览发货单编号："+idarrs);
                //printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：storageDeliveryOrderPrintHandle()>>>>>>>>>>>>>>>>异常信息：" + ex.getMessage());
            addPrintLogInfo("PrintHandle-storageDeliveryOrder", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("在发货单中-按仓库打印及预览处理异常", ex);


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
     * 普通明细列表生成打印数据
     * @param queryMap
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年11月20日
     */
    private void getDelieryOrderPrintData(Map queryMap) throws Exception {
        //是否预览
        Boolean isview = (Boolean) queryMap.get("isview");

        String backHandleJobId = (String) queryMap.get("backHandleJobId");
        if (backHandleJobId == null || "".equals(backHandleJobId.trim())) {
            backHandleJobId = null;
        } else {
            backHandleJobId = backHandleJobId.trim();
        }

        String printOrder = (String) queryMap.get("printOrder");

        String canprintIds = "";

        List<JasperPrint> jrList = null;
        if (queryMap.containsKey("jrList")) {
            jrList = (List<JasperPrint>) queryMap.get("jrList");
        }
        if (null == jrList) {
            jrList = new ArrayList<JasperPrint>();
        }


        if (null == isview) {
            isview = false;
        }
        //发货单
        Saleout saleout = (Saleout) queryMap.get("saleoutOrder");
        //打印的参数
        Map parameters = (Map) queryMap.get("parametersMap");
        //报表文件
        JasperReport jreport = (JasperReport) queryMap.get("JasperReport");

        //已经组装的打印编号
        Map<String, String> saleoutPrintIdMap = (Map<String, String>) queryMap.get("saleoutPrintIdMap");
        //发货单明细
        List<SaleoutDetail> detailList = (List<SaleoutDetail>) queryMap.get("saleoutDetailList");
        if (null == detailList || detailList.size() == 0) {
            return;
        }

        Map printTempletSettingMap=(Map) queryMap.get("printTempletSettingMap");
        if(null==printTempletSettingMap){
            printTempletSettingMap=new HashMap();
        }
        //每页条数
        Integer countperpage = 0;
        //是否填充空行
        String isfillblank = "0";
        countperpage = (Integer) printTempletSettingMap.get("countperpage");
        if (null == countperpage || countperpage < 0) {
            countperpage = 0;
        }
        isfillblank = (String) printTempletSettingMap.get("isfillblank");
        if (isfillblank == null || !"1".equals(isfillblank)) {
            isfillblank = "0";
        }

        List<SaleoutDetail> tmpdList = detailList;
        if (countperpage > 0 && "1".equals(isfillblank)) {
            tmpdList = fillReportWithBlankForOrderDetail(detailList, countperpage);
        }


        //打印
        JasperPrint jrprint = null;

        List<String> detailGroupByList = null;
        jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

        if (null != jrprint) {
            jrprint.setName("发货单(按仓库)-" + saleout.getId());

            if (!isview) {
                //打印回调的参数
                Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                callbackparamMap.put("id", saleout.getId());
                callbackparamMap.put("callback", "updateprinttimes");
                callbackparamMap.put("orderPrintCount", saleout.getPrinttimes());    //当前发货通知单打印次数
                String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                jrprint.setProperty("agprint_callback_params", callbackparams);


                //记录打印次数使用
                saleoutPrintIdMap.put(saleout.getId(), saleout.getId());

                if (backHandleJobId != null) {

                    Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                    //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                    //实际打印的单据号
                    datahtmlparamMap.put("printOrderId", saleout.getId());
                    //打印任务编号
                    datahtmlparamMap.put("printJobId", backHandleJobId);
                    //打印任务编号
                    datahtmlparamMap.put("printJobId", backHandleJobId);
                    //页面点击时的单据号
                    datahtmlparamMap.put("printSourceOrderId", "");
                    String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                    jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                    PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                    printJobCallHandle.setJobid(backHandleJobId);
                    printJobCallHandle.setClassname("saleOutPrintAction");
                    printJobCallHandle.setMethodname("storageDeliveryOrderPrintReflectBackHandle");
                    printJobCallHandle.setMethodparam(callbackparams);
                    printJobCallHandle.setPages(jrprint.getPages().size());
                    printJobCallHandle.setPrintorderid(saleout.getId());
                    printJobCallHandle.setPrintordername("发货单");
                    printJobCallHandle.setSourceorderid(saleout.getId());
                    printJobCallHandle.setSourceordername("发货单");
                    printJobCallHandle.setStatus("0");
                    printJobCallHandle.setType("1");
                    agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                }
            }
            jrList.add(jrprint);

        }
    }

    /**
     * 按品牌进行拆单打印数据
     * @param queryMap
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年11月20日
     */
    private void getDelieryOrderSplitByBrandPrintData(Map queryMap) throws Exception {
        //按品牌拆单
        String saleOrderPrintSplitType = "2";
        //是否预览
        Boolean isview = (Boolean) queryMap.get("isview");
        String backHandleJobId = (String) queryMap.get("backHandleJobId");
        if (backHandleJobId == null || "".equals(backHandleJobId.trim())) {
            backHandleJobId = null;
        } else {
            backHandleJobId = backHandleJobId.trim();
        }


        String printOrder = (String) queryMap.get("printOrder");

        String canprintIds = "";

        List<JasperPrint> jrList = null;
        if (queryMap.containsKey("jrList")) {
            jrList = (List<JasperPrint>) queryMap.get("jrList");
        }
        if (null == jrList) {
            jrList = new ArrayList<JasperPrint>();
        }


        if (null == isview) {
            isview = false;
        }
        //发货单
        Saleout saleout = (Saleout) queryMap.get("saleoutOrder");
        //打印的参数
        Map parameters = (Map) queryMap.get("parametersMap");
        //报表文件
        JasperReport jreport = (JasperReport) queryMap.get("JasperReport");

        //已经组装的打印编号
        Map<String, String> saleoutPrintIdMap = (Map<String, String>) queryMap.get("saleoutPrintIdMap");
        //发货单明细
        List<SaleoutDetail> detailList = (List<SaleoutDetail>) queryMap.get("saleoutDetailList");
        if (null == detailList || detailList.size() == 0) {
            return;
        }

        //客户
        Customer customerInfo = saleout.getCustomerInfo();


        //按客户进分按品牌拆单

        String brandSplitByCustomer = getSysParamValue("saleOrderPrintBrandSplitByCustomer");
        if (null == brandSplitByCustomer || "".equals(brandSplitByCustomer.trim())) {
            saleOrderPrintSplitType = "0";
        } else {
            brandSplitByCustomer = brandSplitByCustomer.trim();
        }
        String[] brandSplitByCustomerArr = brandSplitByCustomer.split(",");
        if (ArrayUtils.contains(brandSplitByCustomerArr, customerInfo.getId())) {
            saleOrderPrintSplitType = "2";
        } else if (StringUtils.isNotEmpty(customerInfo.getPid()) && ArrayUtils.contains(brandSplitByCustomerArr, customerInfo.getPid())) {
            saleOrderPrintSplitType = "2";
        } else {
            saleOrderPrintSplitType = "0";
        }

        if ("0".equals(saleOrderPrintSplitType)) {
            getDelieryOrderPrintData(queryMap);
            return;
        }
        //打印模板配置
        Map printTempletSettingMap=(Map) queryMap.get("printTempletSettingMap");
        if(null==printTempletSettingMap){
            printTempletSettingMap=new HashMap();
        }
        //每页条数
        Integer countperpage = 0;
        //是否填充空行
        String isfillblank = "0";
        countperpage = (Integer) printTempletSettingMap.get("countperpage");
        if (null == countperpage || countperpage < 0) {
            countperpage = 0;
        }
        isfillblank = (String) printTempletSettingMap.get("isfillblank");
        if (isfillblank == null || !"1".equals(isfillblank)) {
            isfillblank = "0";
        }




        //打印
        JasperPrint jrprint = null;

        List<String> detailGroupByList = null;

        Map<String, List<SaleoutDetail>> brandDataMap = storageSaleOutService.getSaleOutDetailListDataSplitByBrand(detailList);

        if (null == brandDataMap) {
            return;
        }

        int detailTheBrandCount = brandDataMap.size();
        int iBrandCount = 0;

        for (Map.Entry<String, List<SaleoutDetail>> entry : brandDataMap.entrySet()) {
            iBrandCount = iBrandCount + 1;
            //打印备注
            parameters.put("P_PrintRemark", "按品牌：" + iBrandCount + " / " + detailTheBrandCount);


            List<SaleoutDetail> tmpdList = entry.getValue();
            if (countperpage > 0 && "1".equals(isfillblank)) {
                tmpdList = fillReportWithBlankForOrderDetail(entry.getValue(), countperpage);
            }
            jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

            if (null != jrprint) {
                jrprint.setName("发货单(按仓库)-" + saleout.getId());

                if (!isview) {
                    //打印回调的参数
                    Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                    callbackparamMap.put("id", saleout.getId());
                    callbackparamMap.put("callback", "updateprinttimes");
                    callbackparamMap.put("splitbill", "true"); //拆分单
                    callbackparamMap.put("orderPrintCount", saleout.getPrinttimes());    //当前发货通知单打印次数
                    String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                    jrprint.setProperty("agprint_callback_params", callbackparams);

                    //记录打印次数使用
                    saleoutPrintIdMap.put(saleout.getId(), saleout.getId());

                    if (backHandleJobId != null) {
                        //打印次数反射方法
                        Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                        //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                        //实际打印的单据号
                        datahtmlparamMap.put("printOrderId", saleout.getId());
                        //打印任务编号
                        datahtmlparamMap.put("printJobId", backHandleJobId);
                        //页面点击时的单据号
                        datahtmlparamMap.put("printSourceOrderId", "");
                        String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                        jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                        PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                        printJobCallHandle.setJobid(backHandleJobId);
                        printJobCallHandle.setClassname("saleOutPrintAction");
                        printJobCallHandle.setMethodname("storageDeliveryOrderPrintReflectBackHandle");
                        printJobCallHandle.setMethodparam(callbackparams);
                        printJobCallHandle.setPages(jrprint.getPages().size());
                        printJobCallHandle.setPrintorderid(saleout.getId());
                        printJobCallHandle.setPrintordername("发货单");
                        printJobCallHandle.setSourceorderid(saleout.getId());
                        printJobCallHandle.setSourceordername("发货单");
                        printJobCallHandle.setStatus("0");
                        printJobCallHandle.setType("1");
                        agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                    }
                }
                jrList.add(jrprint);
            }
        }

    }

    /**
     * 根据仓库编号，判断是否是整形单
     * @param queryMap
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年11月18日
     */
    private void getDelieryOrderSplitByStorageZSPrintData(Map queryMap) throws Exception {
        //拆单类型
        String saleOrderPrintSplitType = "0";
        //是否预览
        Boolean isview = (Boolean) queryMap.get("isview");

        String backHandleJobId = (String) queryMap.get("backHandleJobId");
        if (backHandleJobId == null || "".equals(backHandleJobId.trim())) {
            backHandleJobId = null;
        } else {
            backHandleJobId = backHandleJobId.trim();
        }

        String printOrder = (String) queryMap.get("printOrder");

        String canprintIds = "";

        List<JasperPrint> jrList = null;
        if (queryMap.containsKey("jrList")) {
            jrList = (List<JasperPrint>) queryMap.get("jrList");
        }
        if (null == jrList) {
            jrList = new ArrayList<JasperPrint>();
        }


        if (null == isview) {
            isview = false;
        }
        //发货单
        Saleout saleout = (Saleout) queryMap.get("saleoutOrder");
        //打印的参数
        Map parameters = (Map) queryMap.get("parametersMap");
        //报表文件
        JasperReport jreport = (JasperReport) queryMap.get("JasperReport");
        //已经组装的打印编号
        Map<String, String> saleoutPrintIdMap = (Map<String, String>) queryMap.get("saleoutPrintIdMap");
        //发货单明细
        List<SaleoutDetail> detailList = (List<SaleoutDetail>) queryMap.get("saleoutDetailList");
        if (null == detailList || detailList.size() == 0) {
            return;
        }

        Map printTempletSettingMap=(Map) queryMap.get("printTempletSettingMap");
        if(null==printTempletSettingMap){
            printTempletSettingMap=new HashMap();
        }
        //每页条数
        Integer countperpage = 0;
        //是否填充空行
        String isfillblank = "0";
        countperpage = (Integer) printTempletSettingMap.get("countperpage");
        if (null == countperpage || countperpage < 0) {
            countperpage = 0;
        }
        isfillblank = (String) printTempletSettingMap.get("isfillblank");
        if (isfillblank == null || !"1".equals(isfillblank)) {
            isfillblank = "0";
        }


        //是否显示整散分离不分单
        String showWholeByStorageStr = getSysParamValue("orderIsShowWholeByStorage");
        //是否显示整散分离分单
        String showWholeInMutiByStorageStr = getSysParamValue("orderIsShowWholeInMutiByStorage");

        String[] storageArr = null;
        //按客户进分按品牌拆单

        if (StringUtils.isNotEmpty(saleout.getStorageid())) {


            if (null == showWholeByStorageStr || "".equals(showWholeByStorageStr.trim())) {
                showWholeByStorageStr = "";
            } else {
                showWholeByStorageStr = showWholeByStorageStr.trim();
            }
            storageArr = showWholeByStorageStr.split(",");
            if (ArrayUtils.contains(storageArr, saleout.getStorageid())) {
                saleOrderPrintSplitType = "1"; //整散分离不分单
            } else {
                if (null == showWholeInMutiByStorageStr || "".equals(showWholeInMutiByStorageStr.trim())) {
                    showWholeInMutiByStorageStr = "";
                } else {
                    showWholeInMutiByStorageStr = showWholeInMutiByStorageStr.trim();
                }
                storageArr = showWholeInMutiByStorageStr.split(",");
                if (ArrayUtils.contains(storageArr, saleout.getStorageid())) {
                    saleOrderPrintSplitType = "2"; //整散分离分单
                }
            }


        } else {
            saleOrderPrintSplitType = "0";
        }

        if ("0".equals(saleOrderPrintSplitType)) {
            getDelieryOrderPrintData(queryMap);
            return;
        }

        //打印
        JasperPrint jrprint = null;

        List<String> detailGroupByList = null;

        Map detalListMap = storageSaleOutService.getSaleOutDetailListDataSplitByZSD(detailList);
        if (null == detalListMap) {
            return;
        }
        //整箱明细
        List<SaleoutDetail> zxDetailList = null;

        if (detalListMap.containsKey("zxDetailList")) {
            zxDetailList = (List<SaleoutDetail>) detalListMap.get("zxDetailList");
        }
        if (null == zxDetailList) {
            zxDetailList = new ArrayList<SaleoutDetail>();
        }

        //散箱明细
        List<SaleoutDetail> sxDetailList = null;
        if (detalListMap.containsKey("sxDetailList")) {
            sxDetailList = (List<SaleoutDetail>) detalListMap.get("sxDetailList");
        }
        if (null == sxDetailList) {
            sxDetailList = new ArrayList<SaleoutDetail>();
        }
        //折扣明细
        List<SaleoutDetail> zkouDetailList = null;
        if (detalListMap.containsKey("zkouDetailList")) {
            zkouDetailList = (List<SaleoutDetail>) detalListMap.get("zkouDetailList");
        }
        if (null == zkouDetailList) {
            zkouDetailList = new ArrayList<SaleoutDetail>();
        }


        //整散分离不分单
        if ("1".equals(saleOrderPrintSplitType)) {
            zxDetailList.addAll(sxDetailList);
            zxDetailList.addAll(zkouDetailList);
            if (zxDetailList.size() > 0) {

                List<SaleoutDetail> tmpdList = zxDetailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(zxDetailList, countperpage);
                }

                jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

                if (null != jrprint) {
                    jrprint.setName("发货单(按仓库)-整散单-" + saleout.getId());

                    if (!isview) {
                        //打印回调参数
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", saleout.getId());
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("splitbill", "true"); //拆分单
                        callbackparamMap.put("orderPrintCount", saleout.getPrinttimes());    //当前发货通知单打印次数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        //记录打印次数使用
                        saleoutPrintIdMap.put(saleout.getId(), saleout.getId());

                        if (backHandleJobId != null) {
                            //打印反射方法
                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号 固定格式
                            datahtmlparamMap.put("printOrderId", saleout.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", backHandleJobId);
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(backHandleJobId);
                            printJobCallHandle.setClassname("saleOutPrintAction");
                            printJobCallHandle.setMethodname("storageDeliveryOrderPrintReflectBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(saleout.getId());
                            printJobCallHandle.setPrintordername("发货单");
                            printJobCallHandle.setSourceorderid(saleout.getId());
                            printJobCallHandle.setSourceordername("发货单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrList.add(jrprint);
                }
            }
        } else if ("2".equals(saleOrderPrintSplitType)) {

            zxDetailList.addAll(zkouDetailList);
            if (zxDetailList.size() > 0) {

                List<SaleoutDetail> tmpdList = zxDetailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(zxDetailList, countperpage);
                }

                jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

                if (null != jrprint) {
                    jrprint.setName("发货单(按仓库)-整单-" + saleout.getId());

                    if (!isview) {
                        //打印次数更新参数
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", saleout.getId());
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("splitbill", "true"); //拆分单
                        callbackparamMap.put("orderPrintCount", saleout.getPrinttimes());    //当前发货通知单打印次数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        //记录打印次数使用
                        saleoutPrintIdMap.put(saleout.getId(), saleout.getId());

                        if (backHandleJobId != null) {
                            //打印次数更新回调方法
                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", saleout.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", backHandleJobId);
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(backHandleJobId);
                            printJobCallHandle.setClassname("saleOutPrintAction");
                            printJobCallHandle.setMethodname("storageDeliveryOrderPrintReflectBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(saleout.getId());
                            printJobCallHandle.setPrintordername("发货单");
                            printJobCallHandle.setSourceorderid(saleout.getId());
                            printJobCallHandle.setSourceordername("发货单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }

                    jrList.add(jrprint);
                }
            }

            if (sxDetailList.size() > 0) {
                List<SaleoutDetail> tmpdList = sxDetailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(sxDetailList, countperpage);
                }
                jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

                if (null != jrprint) {
                    jrprint.setName("发货单(按仓库)-散单-" + saleout.getId());

                    if (!isview) {
                        //打印次数更新参数
                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", saleout.getId());
                        callbackparamMap.put("callback", "updateprinttimes");
                        callbackparamMap.put("splitbill", "true"); //拆分单
                        callbackparamMap.put("orderPrintCount", saleout.getPrinttimes());    //当前发货通知单打印次数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);
                        //记录打印次数使用
                        saleoutPrintIdMap.put(saleout.getId(), saleout.getId());

                        if (backHandleJobId != null) {
                            //打印次数更新方法
                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", saleout.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", backHandleJobId);
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(backHandleJobId);
                            printJobCallHandle.setClassname("saleOutPrintAction");
                            printJobCallHandle.setMethodname("storageDeliveryOrderPrintReflectBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(saleout.getId());
                            printJobCallHandle.setPrintordername("发货单");
                            printJobCallHandle.setSourceorderid(saleout.getId());
                            printJobCallHandle.setSourceordername("发货单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }

                    jrList.add(jrprint);
                }
            }

        }

    }

    /**
     * 打印回调处理
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    private void storageDeliveryOrderPrintCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageDeliveryOrder", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageDeliveryOrder", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印发货单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("发货单编号： " + id);
            try {
                Integer orderPrintCount = null;
                if(map.containsKey("orderPrintCount")){
                    orderPrintCount = new Integer(map.get("orderPrintCount").toString());
                }
                if (null != orderPrintCount ) {
                    int iPrintCount = orderPrintCount;
                    Saleout saleout = storageSaleOutService.getSaloutPrinttimesById(id);
                    if (null != saleout && null != saleout.getPrinttimes()
                            && iPrintCount < saleout.getPrinttimes()) {
                        printLogsb.append(" 更新打印次数已经更新");
                    } else {
                        storageSaleOutPrintService.updateFhPrinttimesSyncUpOrder(id);
                        printLogsb.append(" 更新打印次数成功");
                    }
                } else {
                    storageSaleOutPrintService.updateFhPrinttimesSyncUpOrder(id);
                    printLogsb.append(" 更新打印次数成功");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在发货单中-按仓库更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于： 在发货单中打印-按仓库打印 。");
        printLogsb.append(" 打印数据来源(发货单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-storageDeliveryOrder", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 通过后台反射方式打印回调处理
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public boolean storageDeliveryOrderPrintReflectBackHandle(String printcallback) throws Exception {
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageDeliveryOrder", printLogsb.toString(), null);
            return false;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-storageDeliveryOrder", printLogsb.toString(), null);
            return false;
        }
        String id = (String) map.get("id");
        boolean isok = false;
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印发货单编号："+idarrs);
            //printLogsb.append(" 。");
            printLogsb.append("发货单编号： " + id);
            try {

                Integer orderPrintCount = new Integer(map.get("orderPrintCount").toString());
                if (null != orderPrintCount) {
                    int iPrintCount = orderPrintCount;
                    Saleout saleout = storageSaleOutService.getSaloutPrinttimesById(id);
                    if (null != saleout && null != saleout.getPrinttimes() && iPrintCount > saleout.getPrinttimes()) {
                        printLogsb.append(" 更新打印次数已经更新");
                    } else {
                        storageSaleOutPrintService.updateFhPrinttimesSyncUpOrder(id);
                        printLogsb.append(" 更新打印次数成功");
                    }
                } else {
                    storageSaleOutPrintService.updateFhPrinttimesSyncUpOrder(id);
                    printLogsb.append(" 更新打印次数成功");
                }
                isok = true;
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在发货单中-按仓库更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于： 在发货单中打印-按仓库打印 。");
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-storageDeliveryOrder", printLogsb.toString(), null);
        return isok;
    }


    /**
     * 循环加入空白行
     * @param list
     * @param countperpage
     * @return java.util.List
     * @throws
     * @author zhanghonghui
     * @date Aug 14, 2017
     */
    private List fillReportWithBlankForOrderDetail(List<SaleoutDetail> list, Integer countperpage) {
        if (countperpage == null || countperpage <= 0) {
            return list;
        }
        int isize = 0;
        if (null == list) {
            list = new ArrayList<SaleoutDetail>();
        } else {
            isize = list.size();
        }
        int iLeft = isize % countperpage;
        if (isize == 0 || (isize != 0 && iLeft != 0)) {
            iLeft = countperpage - iLeft;
        }
        for (int i = 0; i < iLeft; i++) {
            SaleoutDetail detailItem = new SaleoutDetail();
            detailItem.setGoodsid(null);
            detailItem.setGoodsInfo(null);
            detailItem.setUnitnum(null);
            detailItem.setAuxnum(null);
            detailItem.setAuxremainder(null);
            detailItem.setTax(null);
            detailItem.setNotaxamount(null);
            detailItem.setTaxamount(null);
            detailItem.setId(-999);
            list.add(detailItem);
        }
        return list;
    }

}

