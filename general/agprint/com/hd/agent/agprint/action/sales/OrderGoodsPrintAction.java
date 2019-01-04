package com.hd.agent.agprint.action.sales;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.sales.model.OrderGoods;
import com.hd.agent.sales.model.OrderGoodsDetail;
import com.hd.agent.sales.service.IOrderGoodsService;
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
 * Created by luoq on 2017/10/19.
 */
public class OrderGoodsPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(SaleOrderPrintAction.class);
    private AgprintServiceImpl agprintServiceImpl;

    private IOrderGoodsService orderGoodsService;

    public IOrderGoodsService getOrderGoodsService() {
        return orderGoodsService;
    }

    public void setOrderGoodsService(IOrderGoodsService orderGoodsService) {
        this.orderGoodsService = orderGoodsService;
    }

    public AgprintServiceImpl getAgprintServiceImpl() {
        return agprintServiceImpl;
    }

    public void setAgprintServiceImpl(AgprintServiceImpl agprintServiceImpl) {
        this.agprintServiceImpl = agprintServiceImpl;
    }

    /**
     * 在销售订货单处打印预览-销售订货单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "PrintView", type = 0)
    public String salesOrderGoodsPrintView() throws Exception {
        return salesOrderGoodsPrintHandle(true);
    }
    /**
     * 在销售订货单打印-销售订货单打印
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    @UserOperateLog(key = "Print", type = 0)
    private String salesOrderGoodsPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "在销售订货单中-按销售订货单数据打印-" + CommonUtils.getDataNumberSeconds());
        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs || "".equals(saleidarrs.trim())) {
            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "未能找到相关单据");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            } else {
                return null;
            }
        }
        viewtype = viewtype.trim();
        String templetid = request.getParameter("templetid");
        SysUser sysUser = getSysUser();
        String orderCanprintIds = "";
        try {
            String printlimit = "";
            Map paramMap = new HashMap();
            paramMap.put("idarrs", saleidarrs);
            if (!isview) {
                printlimit = getPrintLimitInfo();
				 /*
				 if("0".equals(printlimit)){
					 paramMap.put("statusarr", "2,3,4");
				 }else{
					 paramMap.put("statusarr", "2,3,4");
				 }
				 */
                //paramMap.put("notphprint", printlimit);
            }
            //显示总店名称
            paramMap.put("showPCustomerName", "true");
            //显示明细
            paramMap.put("showDetailListType", "print");
            List<OrderGoods> list = orderGoodsService.getSalesOrderGoodsListBy(paramMap);
            if (list.size() == 0) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到相关销售订货单打印");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                } else {
                    return null;
                }
            }

            Map parameters = null;

            Map<String, String> orderIdMap = new HashMap<String, String>();

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            Map<String, String> saleoutPrintIdMap = new HashMap<String, String>();
            Map<String, String> orderPrintIdMap = new HashMap<String, String>();

            Date printDate = new Date();

            int orderPrintCount = 0;
            //获取打印模板
            Map templetQueryMap = new HashMap();

            Map printTempletSettingFistMap = null;


            //查询是否需要客户回款信息,1需要，0不需要，默认为0
            String isPrintShowCustomerYSKInfo = getSysParamValue("isPrintShowCustomerYSKInfo");
            if (null == isPrintShowCustomerYSKInfo || "".equals(isPrintShowCustomerYSKInfo.trim())) {
                isPrintShowCustomerYSKInfo = "0";
            } else {
                isPrintShowCustomerYSKInfo = isPrintShowCustomerYSKInfo.trim();
            }
            //打印任务
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("在销售订货单中-按销售订货单数据打印");
                printJob.setOrderidarr(saleidarrs);
                if (request.getQueryString() != null) {
                    printJob.setRequestparam(request.getQueryString());
                }

                if (isview) {
                    //3打印预览
                    printJob.setStatus("3");
                } else {
                    //0申请
                    printJob.setStatus("0");
                }

                boolean isjobflag = agprintServiceImpl.addPrintJob(printJob);
                if (!isjobflag || StringUtils.isEmpty(printJob.getId())) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "申请销售订货单打印时失败");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                }
                ajaxResultMap.put("printJobId", printJob.getId());
                ajaxResultMap.put("printname", "销售订货单打印任务-" + printJob.getId());
            }
            for (OrderGoods item : list) {
                if (null == item.getId() || "".equals(item.getId())) {
                    continue;
                }

                List<OrderGoodsDetail> detailList = item.getOrderDetailList();

                if (null == detailList || detailList.size() == 0) {
                    continue;
                }

                BigDecimal totalamount = BigDecimal.ZERO;

                for (OrderGoodsDetail orderDetail : detailList) {
                    if (null != orderDetail.getTaxamount()) {
                        totalamount = totalamount.add(orderDetail.getTaxamount());
                    }
                }

                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "sales_ordergoods");
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
                //打印模板
                String printTempletFile = (String) templetResultMap.get("printTempletFile");
                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                    if ("ajaxhtml".equals(viewtype)) {
                        ajaxResultMap.put("flag", false);
                        ajaxResultMap.put("msg", "未找到相关打印模板");
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
                if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                    printTempletSettingFistMap = new HashMap();
                    printTempletSettingFistMap.putAll(templetResultMap);
                }

                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }

                //String reportModelFile = servletContext.getRealPath("/ireport/storage_deliveryorder/storage_deliveryorder.jasper");
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));

                Customer customerInfo = getCustomerById(item.getCustomerid());
                orderPrintCount = 0;
                if (null != item.getPhprinttimes()) {
                    orderPrintCount = item.getPhprinttimes();
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

                paramMap = new HashMap();
                if (!isview) {
                    if ("0".equals(printlimit)) {
                        paramMap.put("statusarr ", "2,3,4");
                    } else {
                        paramMap.put("statusarr", "2,3,4");
                    }
                    //map.put("notphprint", printlimit);
                }
                paramMap.put("orderid", item.getId());
                paramMap.put("noDataSql", "true");//去掉权限判断

                if (item.getPhprinttimes() == null) {
                    item.setPhprinttimes(0);
                }

                if (!isview) {
                    item.setPrinttimes(item.getPhprinttimes());
                } else {
                    item.setPrinttimes(item.getPhprinttimes() + 1);
                }
                parameters = new HashMap();
                if (null == addPersonnel || (StringUtils.isNotEmpty(addPersonnel.getUserid()) && !addPersonnel.getUserid().equals(item.getAdduserid()))) {
                    addPersonnel = getSysUserById(item.getAdduserid());
                }
                if (null == addPersonnel) {
                    addPersonnel = new SysUser();
                }
                parameters = new HashMap();

                //是否显示
                parameters.put("P_IsShow_CustomerAmountYSKE", "1".equals(isPrintShowCustomerYSKInfo) ? "1" : "0");

                //销售员
                parameters.put("P_SaleUser", salePersonnel.getName());
                //销售员电话
                parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                //打印人
                parameters.put("P_PRINTUSER", sysUser.getName());    //打印人
                //打印人
                parameters.put("P_PrintUser", sysUser.getName());    //打印人
                //打印时间
                parameters.put("P_PrintDate", new Date());
                //销售订货单号
                parameters.put("P_SaleId", item.getId());
                //仓库名称
                StorageInfo storageInfo = getStorageInfo(item.getStorageid());
                if (null != storageInfo) {
                    parameters.put("P_Storage", storageInfo.getName());
                }

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
                parameters.put("P_SaleArea", customerInfo.getSalesareaname());    //销售订货单号
				/*客户相关*/


                //客户单据信息号

                parameters.put("P_TotalAmount", totalamount);

                /**打印模板参数用的单据信息**/
                OrderGoods orderInfo = (OrderGoods) CommonUtils.deepCopy(item);
                orderInfo.setOrderDetailList(null);
                parameters.put("P_OrderInfo", orderInfo);
				/*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);
                /**打印参数组装结束**/
                JasperPrint jrprint =
                        JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));


                if (null != jrprint) {
                    if (!isview) {
                        if (StringUtils.isNotEmpty(item.getId())) {
                            orderPrintIdMap.put(item.getId(), item.getId());
                        }


                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", item.getId());
                        callbackparamMap.put("saleorderid", item.getId());
                        callbackparamMap.put("callback", "updatephprinttimes");
                        callbackparamMap.put("updateSaleorderPrintCount", "true");
                        callbackparamMap.put("orderPrintCount", orderPrintCount);    //当前销售单打印次数
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
                            datahtmlparamMap.put("printSourceOrderId", item.getId());
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(printJob.getId());
                            printJobCallHandle.setClassname("saleOrderPrintAction");
                            printJobCallHandle.setMethodname("salesOrderGoodsReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("销售订货单");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("销售订货单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrprint.setName(item.getId() + "-销售订货单打印-" + item.getId());
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
                //printLogsb.append("申请打印销售单编号："+saleidarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：在销售单中-按销售订货单数据打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-salesOrder", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-在销售单中-按销售订货单数据打印异常：");
                //printLogsb.append("申请打印销售单编号："+saleidarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售订货单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在销售单中-按销售订货单数据打印预览异常：");
                printLogsb.append("预览的销售订货单编号：" + saleidarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：salesOrderPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-salesOrder", printLogsb.toString(), null);

            logger.error("在销售单中-按销售订货单数据打印及预览处理异常", ex);


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
     * 输出报表
     *
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
     * 在销售订货单打印-销售订货单打印
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    @UserOperateLog(key = "Print", type = 0)
    private String salesOrderPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "在销售订货单中-按销售订货单数据打印-" + CommonUtils.getDataNumberSeconds());
        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs || "".equals(saleidarrs.trim())) {
            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "未能找到相关单据");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            } else {
                return null;
            }
        }
        viewtype = viewtype.trim();
        String templetid = request.getParameter("templetid");
        SysUser sysUser = getSysUser();
        String orderCanprintIds = "";
        try {
            String printlimit = "";
            Map paramMap = new HashMap();
            paramMap.put("idarrs", saleidarrs);
            if (!isview) {
                printlimit = getPrintLimitInfo();
				 /*
				 if("0".equals(printlimit)){
					 paramMap.put("statusarr", "2,3,4");
				 }else{
					 paramMap.put("statusarr", "2,3,4");
				 }
				 */
                //paramMap.put("notphprint", printlimit);
            }
            //显示总店名称
            paramMap.put("showPCustomerName", "true");
            //显示明细
            paramMap.put("showDetailListType", "print");
            List<OrderGoods> list = orderGoodsService.getSalesOrderGoodsListBy(paramMap);
            if (list.size() == 0) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到相关销售订货单打印");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                } else {
                    return null;
                }
            }

            Map parameters = null;

            Map<String, String> orderIdMap = new HashMap<String, String>();

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            Map<String, String> saleoutPrintIdMap = new HashMap<String, String>();
            Map<String, String> orderPrintIdMap = new HashMap<String, String>();

            Date printDate = new Date();

            int orderPrintCount = 0;
            //获取打印模板
            Map templetQueryMap = new HashMap();

            Map printTempletSettingFistMap = null;


            //查询是否需要客户回款信息,1需要，0不需要，默认为0
            String isPrintShowCustomerYSKInfo = getSysParamValue("isPrintShowCustomerYSKInfo");
            if (null == isPrintShowCustomerYSKInfo || "".equals(isPrintShowCustomerYSKInfo.trim())) {
                isPrintShowCustomerYSKInfo = "0";
            } else {
                isPrintShowCustomerYSKInfo = isPrintShowCustomerYSKInfo.trim();
            }
            //打印任务
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("在销售订货单中-按销售订货单数据打印");
                printJob.setOrderidarr(saleidarrs);
                if (request.getQueryString() != null) {
                    printJob.setRequestparam(request.getQueryString());
                }

                if (isview) {
                    //3打印预览
                    printJob.setStatus("3");
                } else {
                    //0申请
                    printJob.setStatus("0");
                }

                boolean isjobflag = agprintServiceImpl.addPrintJob(printJob);
                if (!isjobflag || StringUtils.isEmpty(printJob.getId())) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "申请销售订货单打印时失败");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                }
                ajaxResultMap.put("printJobId", printJob.getId());
                ajaxResultMap.put("printname", "销售订货单打印任务-" + printJob.getId());
            }
            for (OrderGoods item : list) {
                if (null == item.getId() || "".equals(item.getId())) {
                    continue;
                }

                List<OrderGoodsDetail> detailList = item.getOrderDetailList();

                if (null == detailList || detailList.size() == 0) {
                    continue;
                }

                BigDecimal totalamount = BigDecimal.ZERO;

                for (OrderGoodsDetail orderDetail : detailList) {
                    if (null != orderDetail.getTaxamount()) {
                        totalamount = totalamount.add(orderDetail.getTaxamount());
                    }
                }

                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "sales_ordergoods");
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
                //打印模板
                String printTempletFile = (String) templetResultMap.get("printTempletFile");
                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                    if ("ajaxhtml".equals(viewtype)) {
                        ajaxResultMap.put("flag", false);
                        ajaxResultMap.put("msg", "未找到相关打印模板");
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
                if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                    printTempletSettingFistMap = new HashMap();
                    printTempletSettingFistMap.putAll(templetResultMap);
                }

                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }

                //String reportModelFile = servletContext.getRealPath("/ireport/storage_deliveryorder/storage_deliveryorder.jasper");
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));

                Customer customerInfo = item.getCustomerInfo();
                orderPrintCount = 0;
                if (null != item.getPhprinttimes()) {
                    orderPrintCount = item.getPhprinttimes();
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

                paramMap = new HashMap();
                if (!isview) {
                    if ("0".equals(printlimit)) {
                        paramMap.put("statusarr ", "2,3,4");
                    } else {
                        paramMap.put("statusarr", "2,3,4");
                    }
                    //map.put("notphprint", printlimit);
                }
                paramMap.put("orderid", item.getId());
                paramMap.put("noDataSql", "true");//去掉权限判断

                if (item.getPhprinttimes() == null) {
                    item.setPhprinttimes(0);
                }

                if (!isview) {
                    item.setPrinttimes(item.getPhprinttimes());
                } else {
                    item.setPrinttimes(item.getPhprinttimes() + 1);
                }
                parameters = new HashMap();
                if (null == addPersonnel || (StringUtils.isNotEmpty(addPersonnel.getUserid()) && !addPersonnel.getUserid().equals(item.getAdduserid()))) {
                    addPersonnel = getSysUserById(item.getAdduserid());
                }
                if (null == addPersonnel) {
                    addPersonnel = new SysUser();
                }
                parameters = new HashMap();

                //是否显示
                parameters.put("P_IsShow_CustomerAmountYSKE", "1".equals(isPrintShowCustomerYSKInfo) ? "1" : "0");


                //销售员
                parameters.put("P_SaleUser", salePersonnel.getName());
                //销售员电话
                parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                //打印人
                parameters.put("P_PRINTUSER", sysUser.getName());    //打印人
                //打印人
                parameters.put("P_PrintUser", sysUser.getName());    //打印人
                //打印时间
                parameters.put("P_PrintDate", new Date());
                //销售订货单号
                parameters.put("P_SaleId", item.getId());
                //仓库名称
                StorageInfo storageInfo = getStorageInfo(item.getStorageid());
                if (null != storageInfo) {
                    parameters.put("P_Storage", storageInfo.getName());
                }

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
                parameters.put("P_SaleArea", customerInfo.getSalesareaname());    //销售订货单号
				/*客户相关*/


                //客户单据信息号

                parameters.put("P_TotalAmount", totalamount);

                /**打印模板参数用的单据信息**/
                OrderGoods orderInfo = (OrderGoods) CommonUtils.deepCopy(item);
                orderInfo.setOrderDetailList(null);
                parameters.put("P_OrderInfo", orderInfo);
				/*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);
                /**打印参数组装结束**/
                JasperPrint jrprint =
                        JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));


                if (null != jrprint) {
                    if (!isview) {
                        if (StringUtils.isNotEmpty(item.getId())) {
                            orderPrintIdMap.put(item.getId(), item.getId());
                        }


                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", item.getId());
                        callbackparamMap.put("saleorderid", item.getId());
                        callbackparamMap.put("callback", "updatephprinttimes");
                        callbackparamMap.put("updateSaleorderPrintCount", "true");
                        callbackparamMap.put("orderPrintCount", orderPrintCount);    //当前销售单打印次数
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
                            datahtmlparamMap.put("printSourceOrderId", item.getId());
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(printJob.getId());
                            printJobCallHandle.setClassname("orderGoodsPrintAction");
                            printJobCallHandle.setMethodname("salesOrderGoodsCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("销售订货单");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("销售订货单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrprint.setName(item.getId() + "-销售订货单打印-" + item.getId());
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
                //printLogsb.append("申请打印销售单编号："+saleidarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：在销售单中-按销售订货单数据打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-salesOrder", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-在销售单中-按销售订货单数据打印异常：");
                //printLogsb.append("申请打印销售单编号："+saleidarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在销售单中-按销售订货单数据打印预览异常：");
                printLogsb.append("预览的销售单编号：" + saleidarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：salesOrderPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-salesOrder", printLogsb.toString(), null);

            logger.error("在销售单中-按销售订货单数据打印及预览处理异常", ex);


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
    public boolean salesOrderGoodsCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesOrder", printLogsb.toString(), null);
            return isok;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesOrder", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印销售单编号："+saleidarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售订货单编号： " + id);
            try {
                OrderGoods order = orderGoodsService.getOrderGoods(id);
                if (null != order) {
                    order.setPrinttimes(1);
                    order.setPhprinttimes(null);
                    orderGoodsService.updateOrderGoodsPrinttimes(order);
                    printLogsb.append(" 更新打印次数成功");
                    isok = true;
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-按销售订货单数据更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无销售订货单编号信息");
        }

        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesOrder", printLogsb.toString(), null);
        return isok;
    }
    /**
     * 在销售订货单打印-销售订货单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "Print", type = 0)
    public String salesOrderGoodsPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            salesOrderGoodsCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return salesOrderPrintHandle(isview);
        }
        return null;
    }
}
