package com.hd.agent.agprint.action.sales;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.delivery.model.DeliveryOrder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.sales.model.Order;
import com.hd.agent.sales.model.OrderDetail;
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
 * 销售订单打印功能
 *
 * @author master
 */
public class SaleOrderPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(SaleOrderPrintAction.class);
    /**
     * 发货单业务服务层
     */
    private IStorageSaleOutService storageSaleOutService;

    public IStorageSaleOutService getStorageSaleOutService() {
        return storageSaleOutService;
    }

    public void setStorageSaleOutService(
            IStorageSaleOutService storageSaleOutService) {
        this.storageSaleOutService = storageSaleOutService;
    }

    private AgprintServiceImpl agprintServiceImpl;

    public AgprintServiceImpl getAgprintServiceImpl() {
        return agprintServiceImpl;
    }

    public void setAgprintServiceImpl(AgprintServiceImpl agprintServiceImpl) {
        this.agprintServiceImpl = agprintServiceImpl;
    }


    /**
     * 发货单打印业务服务层
     */
    private IStorageSaleOutPrintService storageSaleOutPrintService;

    public IStorageSaleOutPrintService getStorageSaleOutPrintService() {
        return storageSaleOutPrintService;
    }

    public void setStorageSaleOutPrintService(
            IStorageSaleOutPrintService storageSaleOutPrintService) {
        this.storageSaleOutPrintService = storageSaleOutPrintService;
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
     * 把销售订单转为发货单
     *
     * @param orderInfo
     * @return
     * @author zhanghonghui
     * @date 2015年12月29日
     */
    public Saleout convertToSaleoutInfo(Order orderInfo) {
        Saleout saleout = new Saleout();
        //根据销售发货通知单 生成销售出库单
        saleout.setBusinessdate(orderInfo.getBusinessdate());
        saleout.setStatus(orderInfo.getStatus());
        saleout.setSalesarea(orderInfo.getSalesarea());
        saleout.setSalesdept(orderInfo.getSalesdept());
        saleout.setSalesdeptname(orderInfo.getSalesdept());
        saleout.setSalesuser(orderInfo.getSalesuser());
        saleout.setSalesusername(orderInfo.getSalesuser());
        saleout.setStorageid(orderInfo.getStorageid());
        //订单编号
        saleout.setSaleorderid(orderInfo.getId());
        saleout.setSourcetype(orderInfo.getSourcetype());
        saleout.setSourceid(orderInfo.getSourceid());
        if (null != orderInfo.getCustomerInfo()) {
            saleout.setCustomerInfo(orderInfo.getCustomerInfo());
        }
        saleout.setCustomerid(orderInfo.getCustomerid());
        saleout.setCustomername(orderInfo.getCustomername());
        saleout.setPcustomerid(orderInfo.getPcustomerid());
        saleout.setCustomersort(orderInfo.getCustomersort());
        saleout.setHandlerid(orderInfo.getHandlerid());
        saleout.setHandlername(orderInfo.getHandlername());
        saleout.setSettletype(orderInfo.getSettletype());
        saleout.setSettletypename(orderInfo.getSettletypename());
        saleout.setPaytype(orderInfo.getPaytype());
        saleout.setRemark(orderInfo.getRemark());
        saleout.setAdduserid(orderInfo.getAdduserid());
        saleout.setAddusername(orderInfo.getAddusername());
        saleout.setAdddeptid(orderInfo.getAdddeptid());
        saleout.setAdddeptname(orderInfo.getAdddeptname());
		saleout.setAddtime(orderInfo.getAddtime());
        saleout.setIndooruserid(orderInfo.getIndooruserid());
        saleout.setIndoorusername(orderInfo.getIndoorusername());
        saleout.setCustomerbillid(orderInfo.getSourceid());
		saleout.setAuditusername(orderInfo.getAuditusername());
		saleout.setAudittime(orderInfo.getAudittime());
        saleout.setField01(orderInfo.getField01());
        saleout.setField02(orderInfo.getField02());
        saleout.setField03(orderInfo.getField03());
        saleout.setField04(orderInfo.getField04());
        saleout.setField05(orderInfo.getField05());
        saleout.setField06(orderInfo.getField06());
        saleout.setField07(orderInfo.getField07());
        saleout.setField08(orderInfo.getField08());
        return saleout;
    }

    //region 在销售订单处  配货单打印预览-销售发货通知单打印

    /**
     * 在销售订单处  配货单打印预览-销售发货通知单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "PrintView", type = 0)
    public String salesOrderblankForXSDPrintView() throws Exception {
        return salesOrderblankForXSDPrintHandle(true);
    }

    /**
     * 在销售订单处 配货单打印-销售发货通知单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "Print", type = 0)
    public String salesOrderblankForXSDPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            salesOrderblankForXSDPrintCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return salesOrderblankForXSDPrintHandle(isview);
        }
        return null;
    }

    /**
     * 配货单打印及预览处理-销售单中打印
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    private String salesOrderblankForXSDPrintHandle(boolean isview) throws Exception {


            return null;

    }

    /**
     * 打印回调
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public void salesOrderblankForXSDPrintCallBackHandle(String printcallback) throws Exception {
        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs) {
            saleidarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesOrderblank", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesOrderblank", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        boolean uplogFlag = true;
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            //printLogsb.append("申请打印销售单编号："+saleidarrs);
            //printLogsb.append(" 。");
            printLogsb.append("仓库发货单编号： " + id);
            try {

                Integer orderPrintCount = null;
                if(map.containsKey("orderPrintCount")){
                    orderPrintCount = new Integer(map.get("orderPrintCount").toString());
                }
                if (null == orderPrintCount ) {
                    orderPrintCount = 0;
                }
                storageSaleOutPrintService.updatePhPrinttimesSyncSalesOrder(id, orderPrintCount.toString());
                printLogsb.append(" 更新配货打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新配货打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-更新配货打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }

        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：在销售单中-配货打印。");
        printLogsb.append(" 打印数据来源(销售单编号)：");
        printLogsb.append(saleidarrs);
        printLogsb.append(" 。");
        if (uplogFlag) {
            addPrintLogInfo("PrintCallHandle-salesOrderblank", printLogsb.toString(), null);
        }
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 打印回调
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public boolean salesOrderblankPrintForXSDReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesOrderblank", printLogsb.toString(), null);
            return isok;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesOrderblank", printLogsb.toString(), null);
            return isok;
        }
        String dispatchid = (String) map.get("dispatchid");
        boolean uplogFlag = true;
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append("仓库发货单编号： " + id);
            try {
                Integer orderPrintCount = new Integer(map.get("orderPrintCount").toString());
                storageSaleOutPrintService.updatePhPrinttimesSyncSalesOrder(id, orderPrintCount.toString());
                printLogsb.append(" 更新配货打印次数成功");
                isok = true;
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-更新配货打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }

        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：在销售单中-配货打印。");
        printLogsb.append(" 打印数据来源(发货通知单编号)：");
        printLogsb.append(dispatchid);
        printLogsb.append(" 。");
        if (uplogFlag) {
            addPrintLogInfo("PrintCallHandle-salesOrderblank", printLogsb.toString(), null);
        }
        return isok;
    }
    //endregion 在销售订单处  配货单打印预览-销售发货通知单打印

    //region 在销售订单处 随货单打印-按订单顺序-销售发货通知单打印

    /**
     * 在销售订单处 随货单打印-按订单顺序-销售发货通知单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    public String salesDispatchBillForXSDPrintView() throws Exception {
        return salesDispatchBillForXSDPrintHandle(true);
    }

    /**
     * 在销售订单处 随货单打印-按订单顺序-销售发货通知单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    public String salesDispatchBillForXSDPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            salesDispatchBillPrintForXSDCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return salesDispatchBillForXSDPrintHandle(isview);
        }
        return null;
    }

    /**
     * 在销售单中打印
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-8-11
     */
    private String salesDispatchBillForXSDPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();

        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "销售单按库位打印-" + CommonUtils.getDataNumberSeconds());

        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs || "".equals(saleidarrs.trim())) {
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

        //打印任务
        PrintJob printJob = new PrintJob();
        if ("ajaxhtml".equals(viewtype)) {
            printJob.setAddtime(new Date());
            printJob.setAdduserid(sysUser.getUserid());
            printJob.setAddusername(sysUser.getName());
            printJob.setIp(CommonUtils.getIP(request));
            printJob.setJobname("销售单-按订单套打");
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
                ajaxResultMap.put("msg", "申请单据打印时失败");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            }
            ajaxResultMap.put("printJobId", printJob.getId());
            ajaxResultMap.put("printname", "发货通知单按订单套打打印任务-" + printJob.getId());
        }
        String printOrder = null;
        String orderCanprintIds = "";
        try {


            Map paramMap = new HashMap();
            paramMap.put("idarrs", saleidarrs);

            String printlimit = getPrintLimitInfo();
          /*  if (!isview) {
                paramMap.put("statusarr", "3,4");
                //paramMap.put("notprint", printlimit);
            } else {
                paramMap.put("statusarr", "3,4");
            }*/

            paramMap.put("showPCustomerName", "true");
            List<Order> list = salesOrderService.getSalesOrderListBy(paramMap);
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
            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            Map<String, String> orderPrintIdMap = new HashMap<String, String>();


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

            for (Order item : list) {

                paramMap.clear();
                paramMap.put("saleorderid", item.getId());
                //传入客户编号，查询店内码


                paramMap.put("customerid", item.getCustomerid());
              //  List<OrderDetail> detailList = getSalesOrderDetailMapper().getOrderDetailByOrderWithDiscount(id, orderStr);
               List<OrderDetail> detailList = storageSaleOutPrintService.getSaleOutDetailPrintListBy(paramMap);

                if (null == detailList || detailList.size() == 0) {
                    continue;
                }
                templetQueryMap.clear();
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "storage_dispatchbill");
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
                //打印模板文件
                String printTempletFile = (String) templetResultMap.get("printTempletFile");
                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                    continue;
                }
                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }

                BigDecimal totalamount = BigDecimal.ZERO;

                for (OrderDetail saleoutDetail : detailList) {
                    if (null != saleoutDetail.getTaxamount()) {
                        totalamount = totalamount.add(saleoutDetail.getTaxamount());
                    }
                }

                if (null == item.getPrinttimes()) {
                    item.setPrinttimes(0);
                }


                PrintTemplet printTemplet = new PrintTemplet();
                if (templetResultMap.containsKey("printTempletInfo")) {
                    printTemplet = (PrintTemplet) templetResultMap.get("printTempletInfo");
                    if (null == printTemplet) {
                        printTemplet = new PrintTemplet();
                        templetResultMap.put("printTempletInfo", printTemplet);
                    }
                }


                //String reportModelFile = servletContext.getRealPath("/ireport/storage_dispatchbill/storage_dispatchbill.jasper");
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));


                //客户信息
                Customer customerInfo = item.getCustomerInfo();


                if (null == customerInfo) {
                    customerInfo = new Customer();
                }

                /*paramMap = new HashMap();

                paramMap.put("showdetail", "0");
                paramMap.put("saleorderid", item.getId());
                List<Saleout> saleoutList = storageSaleOutService.showSaleOutPureListBy(paramMap);

                if (null == saleoutList || saleoutList.size() == 0) {
                    continue;
                }*/
                SysUser addPersonnel = null;

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
              //  parameters.put("P_SaleUser", salePersonnel.getName());
                //销售员电话
             //   parameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                //打印人
                parameters.put("P_PRINTUSER", sysUser.getName());    //打印人
                //打印人
                parameters.put("P_PrintUser", sysUser.getName());    //打印人
                //打印时间
                parameters.put("P_PrintDate", new Date());
                //销售订单号
                parameters.put("P_SaleId", item.getId());
                //仓库名称
              /* StorageInfo storageInfo = getStorageInfo(item.getStorageid());
                if (null != storageInfo) {
                    parameters.put("P_Storage", storageInfo.getName());
                }*/

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
                parameters.put("P_SaleArea", customerInfo.getSalesareaname());    //销售订单号
				/*客户相关*/


                //客户单据信息号
                parameters.put("P_CustomerBillId", item.getSourceid());

                parameters.put("P_TotalAmount", totalamount);


                //把销售订单存储到发货单类里
                Saleout saleout = convertToSaleoutInfo(item);
                parameters.put("P_OrderInfo", saleout);

                parameters.put("P_CompanyName", "B08");	//公司名称
				/*共用参数*/
                parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                agprintServiceImpl.setTempletCommonParameter(parameters);
                /**打印参数组装结束**/

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
                //销售单
                queryMap.put("saleOrder", item);
                //打印报表
                queryMap.put("JasperReport", jreport);
                //已经组装的打印编号
                queryMap.put("orderPrintIdMap", orderPrintIdMap);
                //在销售订单打印时，销售编号所对应的发货单列表
             //   queryMap.put("saleoutList", saleoutList);

                //发货单明细
                queryMap.put("saleoutDetailList", detailList);

                //打印配置项
                queryMap.put("printTempletSettingMap",printTempletSettingFistMap);


              //  if ("0".equals(saleOrderPrintSplitType)) {
                    getDispatchBillPrintData(queryMap);
              /*  } else if ("1".equals(saleOrderPrintSplitType)) {
                    getDispatchBillSplitByStorageZSPrintData(queryMap);
                } else if ("2".equals(saleOrderPrintSplitType)) {
                    getDispatchBillSplitByBrandPrintData(queryMap);
                } else {
                    getDispatchBillPrintData(queryMap);
                }*/

            }
            if (null != jrlist && jrlist.size() > 0) {
                if (!isview) {
                    if (orderPrintIdMap.size() > 0) {
                        List<String> orderPrintIdList = new ArrayList<String>(orderPrintIdMap.values());
                        String[] printarr = (String[]) orderPrintIdList.toArray(new String[orderPrintIdList.size()]);
                        orderCanprintIds = StringUtils.join(printarr, ",");
                    }
                    StringBuilder printLogsb = new StringBuilder();
                    printLogsb.append("打印申请：");
                    //printLogsb.append("申请打印销售单编号："+saleidarrs);
                    //printLogsb.append(" 。");
                    printLogsb.append("销售单编号： " + orderCanprintIds);
                    printLogsb.append(" 。");
                    printLogsb.append(" 操作名称：在销售单中-按订单顺序打印");
                    printLogsb.append(" 。");
                    logStr = printLogsb.toString();
                    addPrintLogInfo("PrintHandle-salesDispatchBill", printLogsb.toString(), null);
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
            }
        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-在销售单中-按订单顺序打印异常：");
                //printLogsb.append("申请打印销售单编号："+saleidarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在销售单中-按订单顺序打印预览异常：");
                printLogsb.append("预览的销售单编号：" + saleidarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：salesDispatchBillForXSDPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-salesDispatchBill", printLogsb.toString(), null);
            logger.error("在销售单中-按订单顺序打印及预览处理异常", ex);


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
     * 打印回调--销售发货通知单-按订单顺序打印
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public void salesDispatchBillPrintForXSDCallBackHandle(String printcallback) throws Exception {
        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs) {
            saleidarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            //printLogsb.append(" 。");
            printLogsb.append("更新销售单中打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesDispatchBill", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            addPrintLogInfo("PrintCallHandle-salesDispatchBill", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印销售单编号："+saleidarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售单编号： " + id);

            try {

                Integer orderPrintCount = null;
                if(map.containsKey("fhprinttimes")){
                    orderPrintCount = new Integer(map.get("fhprinttimes").toString());
                }
                if (null == orderPrintCount ) {
                    orderPrintCount = 0;
                }
                Map paramMap = new HashMap();
                paramMap.put("id", id);
                paramMap.put("fhprinttimes", orderPrintCount.toString());
                paramMap.put("phprinttimes", "true");
                paramMap.put("upSaleoutPrinttimes", "1"); //所有发货单次数更新1
                //paramMap.put("saleout_statusarr", "2,3,4");//发货单打印时，状态
                storageSaleOutPrintService.updateSaleOrderPrinttimesBy(paramMap);
                printLogsb.append(" 更新打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-按订单顺序更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }

        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：在销售单中打印-按订单顺序打印。");
        printLogsb.append(" 打印数据来源(销售单编号)：");
        printLogsb.append(saleidarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesDispatchBill", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 反射打印回调--销售发货通知单-按订单顺序打印
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public boolean salesDispatchBillPrintForXSDPrintReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            //printLogsb.append(" 。");
            printLogsb.append("更新销售单打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesDispatchBill", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            addPrintLogInfo("PrintCallHandle-salesDispatchBill", printLogsb.toString(), null);
            return isok;
        }
        boolean uplogFlag = true;
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append("发货单编号： " + id);

            try {
                Integer orderPrintCount = new Integer(map.get("fhprinttimes").toString());
                if (null != orderPrintCount) {
                    int iPrintCount = -1;
                    if (null != orderPrintCount) {
                        iPrintCount = orderPrintCount;
                    }
                    Order saleOrder = salesOrderService.getOnlyOrder(id);
                    if (null != saleOrder && null != saleOrder.getPrinttimes()
                            && iPrintCount != -1
                            && iPrintCount < saleOrder.getPrinttimes()) {
                        printLogsb.append(" 更新打印次数已经更新");

                        uplogFlag = false;
                    } else {
                        Map paramMap = new HashMap();
                        paramMap.put("id", id);
                        paramMap.put("fhprinttimes", "true");
                        paramMap.put("phprinttimes", "false");
                        paramMap.put("upSaleoutPrinttimes", "1"); //所有发货单次数更新1
                        paramMap.put("saleout_statusarr", "2,3,4");//发货单打印时，状态
                        storageSaleOutPrintService.updateSaleOrderPrinttimesBy(paramMap);
                        printLogsb.append(" 更新打印次数成功");
                    }
                    isok = true;
                } else {

                    if (null == orderPrintCount) {
                        orderPrintCount = 0;
                    }
                    Map paramMap = new HashMap();
                    paramMap.put("id", id);
                    paramMap.put("fhprinttimes", "true");
                    paramMap.put("phprinttimes", "false");
                    paramMap.put("upSaleoutPrinttimes", "1"); //所有发货单次数更新1
                    paramMap.put("saleout_statusarr", "2,3,4");//发货单打印时，状态
                    storageSaleOutPrintService.updateSaleOrderPrinttimesBy(paramMap);
                    printLogsb.append(" 更新打印次数成功");
                    isok = true;
                }

            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-按订单顺序更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无发货单编号信息");
        }

        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesDispatchBill", printLogsb.toString(), null);
        return isok;
    }
    //endregion 在销售订单处 随货单打印-按订单顺序-销售发货通知单打印

    //region 在销售单处 出库单打印-按仓库打印-销售发货通知单打印

    /**
     * 在销售单处 出库单打印-按仓库打印-销售发货通知单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    @UserOperateLog(key = "PrintView", type = 0)
    public String salesDeliveryOrderForXSDPrintView() throws Exception {
        return salesDeliveryOrderForXSDPrintHandle(true);
    }

    /**
     * 在销售单处 出库单打印-按仓库打印-销售发货通知单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "Print", type = 0)
    public String salesDeliveryOrderForXSDPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            salesDeliveryOrderForXSDCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return salesDeliveryOrderForXSDPrintHandle(isview);
        }
        return null;
    }

    /**
     * 在销售单中打印发货单
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-8-12
     */
    private String salesDeliveryOrderForXSDPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();

        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "销售单按库位打印-" + CommonUtils.getDataNumberSeconds());

        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs || "".equals(saleidarrs.trim())) {
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
        //第一个模板打印配置信息
        Map printTempletSettingFistMap = null;
        //使用前台选择模板的形式
        String templetid = request.getParameter("templetid");
        SysUser sysUser = getSysUser();


        //打印任务
        PrintJob printJob = new PrintJob();
        if ("ajaxhtml".equals(viewtype)) {
            printJob.setAddtime(new Date());
            printJob.setAdduserid(sysUser.getUserid());
            printJob.setAddusername(sysUser.getName());
            printJob.setIp(CommonUtils.getIP(request));
            printJob.setJobname("发货通知单-按库位套打");
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
                ajaxResultMap.put("msg", "申请单据打印时失败");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            }
            ajaxResultMap.put("printJobId", printJob.getId());
            ajaxResultMap.put("printname", "发货通知单按库位套打打印任务-" + printJob.getId());
        }

        String saleoutCanprintIds = "";
        String orderCanprintIds = "";
        try {

            String printlimit = "";
            Map paramMap = new HashMap();
            paramMap.put("idarrs", saleidarrs);
            printlimit = getPrintLimitInfo();
            if (!isview) {
                paramMap.put("statusarr", "3,4");
                //paramMap.put("notprint", printlimit);
            } else {
                paramMap.put("statusarr", "3,4");
            }

            paramMap.put("showPCustomerName", "true");
            List<Order> list = salesOrderService.getSalesOrderListBy(paramMap);
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
            Map parameters = null;

            Date printDate = new Date();

            Map<String, String> orderIdMap = new HashMap<String, String>();
            List<Order> orderPrintList = new ArrayList<Order>();
            List<Saleout> saleoutPrintList = new ArrayList<Saleout>();

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();


            Map<String, String> saleoutPrintIdMap = new HashMap<String, String>();
            Map<String, String> orderPrintIdMap = new HashMap<String, String>();

            int orderPrintCount = 0;
            int orderPhPrintCount = 0;

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


            BigDecimal saleTaxamount = null;
            BigDecimal saleNoTaxamount = null;
            BigDecimal saleTax = null;

            for (Order item : list) {

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
                //打印模板文件
                String printTempletFile = (String) templetResultMap.get("printTempletFile");
                if (null == printTempletFile || "".equals(printTempletFile.trim())) {
                    continue;
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
                //打印内容排序策略
                String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                //String reportModelFile = servletContext.getRealPath("/ireport/storage_deliveryorder/storage_deliveryorder.jasper");
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));

                Map saleTotal = salesOrderService.getOrderDetailTotal(item.getId());
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

                paramMap = new HashMap();
                Customer customerInfo = item.getCustomerInfo();
                if (null == customerInfo) {
                    customerInfo = new Customer();
                }

                orderPrintCount = 0;
                orderPhPrintCount = 0;
                if (null != item.getPrinttimes()) {
                    orderPrintCount = item.getPrinttimes();
                }
                if (null != item.getPhprinttimes()) {
                    orderPhPrintCount = item.getPhprinttimes();
                }
                if (!isview) {

                    if ("0".equals(printlimit)) {
                        paramMap.put("statusarr ", "2,3,4");
                    } else {
                        paramMap.put("statusarr", "2,3,4");
                    }
                    //paramMap.put("notprint", printlimit);
                }
                paramMap.put("showdetail", "0"); //查询单据时，不关联明细

                paramMap.put("saleorderid", item.getId());
                paramMap.put("noDataSql", "true");//去掉权限判断

                List<Saleout> saleOutList = storageSaleOutService.showSaleOutListBy(paramMap);
                SysUser addPersonnel = null;
                int saleOutCurCount = 0;    //当前仓库数
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
                for (Saleout saleout : saleOutList) {
                    paramMap.clear();
                    paramMap.put("saleoutid", saleout.getId());
                    //传入客户编号，查询店内码
                    paramMap.put("customerid", item.getCustomerid());
                    List<SaleoutDetail> detailList = storageSaleOutService.getSaleOutDetailListBy(paramMap);

                    if (null == detailList || detailList.size() == 0) {
                        continue;
                    }

                    //进行打印内容明细排序
                    if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                        Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                    }

                    saleOutCurCount = saleOutCurCount + 1;
                    if (null == saleout.getPrinttimes()) {
                        saleout.setPrinttimes(0);
                    }
                    if (!isview) {
                        saleout.setPrinttimes(saleout.getPrinttimes() + 1);
                    } else {
                        saleout.setPrinttimes(saleout.getPrinttimes() + 1);
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
                    parameters.put("P_SaleId", saleout.getSaleorderid());
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
                    parameters.put("P_Storage", saleout.getStoragename());
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

                    parameters.put("P_ShowStorageCount", "1"); //显示仓库数合计
                    parameters.put("P_StorageCurCount", saleOutCurCount);
                    parameters.put("P_StorageTotalCount", saleOutList.size());



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


                    //客户单据信息号
                    parameters.put("P_CustomerBillId", item.getSourceid());
                    //单据信息号
                    saleout.setCustomerbillid(item.getSourceid());

                    //把销售订单存储到发货单类里
                    Saleout orderInfo = convertToSaleoutInfo(item);
                    parameters.put("P_OrderInfo", orderInfo);
					/*共用参数*/
                    parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                    agprintServiceImpl.setTempletCommonParameter(parameters);

                    ////打印参数组装 结束


                    Map queryMap = new HashMap();
                    //是否预览
                    queryMap.put("isview", isview);
                    //打印的组装结果列表
                    queryMap.put("jrList", jrlist);
                    //打印是否排序
                    queryMap.put("printOrder", printOrder);
                    //打印的参数
                    queryMap.put("parametersMap", parameters);
                    //发货通知单
                    queryMap.put("saleOrder", item);
                    //发货单
                    queryMap.put("saleoutOrder", saleout);
                    //打印报表
                    queryMap.put("JasperReport", jreport);
                    //已经组装的发货通知单打印编号
                    queryMap.put("orderPrintIdMap", orderPrintIdMap);

                    //已经组装的打印编号
                    queryMap.put("saleoutPrintIdMap", saleoutPrintIdMap);
                    //发货单明细
                    queryMap.put("saleoutDetailList", detailList);
                    //打印任务编号
                    queryMap.put("backHandleJobId", printJob.getId());

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
            }
            if (null != jrlist && jrlist.size() > 0) {
                if (!isview) {

                    if (saleoutPrintIdMap.size() > 0) {
                        List<String> saleoutPrintIdList = new ArrayList(saleoutPrintIdMap.values());
                        String[] printarr = (String[]) saleoutPrintIdList.toArray(new String[saleoutPrintIdList.size()]);
                        saleoutCanprintIds = StringUtils.join(printarr, ",");
                    }
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
                    printLogsb.append(" ,");
                    printLogsb.append("关联的发货通知单编号： " + saleoutCanprintIds);
                    printLogsb.append(" 。");
                    printLogsb.append(" 操作名称：在销售单中-按仓库打印：");
                    printLogsb.append(" 。");
                    addPrintLogInfo("PrintHandle-salesDeliveryOrder", printLogsb.toString(), null);
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
            }
            jrlist = null;
            list = null;
        } catch (Exception ex) {
            StringBuilder printLogsb = new StringBuilder();
            if (!isview) {
                printLogsb.append("打印申请-在销售单中-按仓库打印异常：");
                //printLogsb.append("申请打印销售单编号："+saleidarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售单编号： " + orderCanprintIds);
                printLogsb.append(" ，");
                printLogsb.append("关联的发货通知单编号： " + saleoutCanprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在销售单中-按仓库打印预览异常：");
                printLogsb.append("预览的销售单编号：" + saleidarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：salesDeliveryOrderForXSDPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-salesDeliveryOrder", printLogsb.toString(), null);
            logger.error("在销售单中-按仓库打印及预览处理异常", ex);


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
     * 销售单处 打印回调
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public void salesDeliveryOrderForXSDCallBackHandle(String printcallback) throws Exception {
        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs) {
            saleidarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesDeliveryOrder", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesDeliveryOrder", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String saleorderid = (String) map.get("saleorderid");
        String isPfPrintTimes = "0";
        if (null != saleorderid && !"".equals(saleorderid.trim())) {
            printLogsb.append("销售订单编号： " + saleorderid);
            try {
                Integer orderPrintCount = null;
                if(map.containsKey("orderPrintCount")){
                    orderPrintCount = new Integer(map.get("orderPrintCount").toString());
                }
                if (null == orderPrintCount ) {
                    orderPrintCount = 0;
                }
                Map paramMap = new HashMap();
                paramMap.put("id", saleorderid);
                paramMap.put("fhprinttimes", orderPrintCount.toString());
                paramMap.put("phprinttimes", "false");
                paramMap.put("upSaleoutPrinttimes", "1"); //所有发货单次数更新1
                storageSaleOutPrintService.updateSaleOrderPrinttimesBy(paramMap);
                printLogsb.append(" 更新打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-按仓库更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无发货单编号信息");
        }
        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：在销售单中打印发货单-按仓库打印。");
        printLogsb.append(" 打印数据来源(销售单编号)：");
        printLogsb.append(saleidarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesDeliveryOrder", printLogsb.toString(), null);
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
    public boolean salesDeliveryOrderForXSDReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesDeliveryOrder", printLogsb.toString(), null);
            return isok;
        }
        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesDeliveryOrder", printLogsb.toString(), null);
            return isok;
        }
        String saleorderid = (String) map.get("saleorderid");
        if (null != saleorderid && !"".equals(saleorderid.trim())) {
            printLogsb.append("销售订单编号： " + saleorderid);
            try {
                Integer fhprintCount = new Integer(map.get("orderPrintCount").toString());
                if (null == fhprintCount) {
                    fhprintCount = 0;
                }
                Map paramMap = new HashMap();
                paramMap.put("id", saleorderid);
                paramMap.put("fhprinttimes", fhprintCount.toString());
                paramMap.put("phprinttimes", "false");
                paramMap.put("upSaleoutPrinttimes", "1"); //所有发货单次数更新1
                storageSaleOutPrintService.updateSaleOrderPrinttimesBy(paramMap);
                printLogsb.append(" 更新打印次数成功");
                isok = true;
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-按仓库更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("回调参数中无发货单编号信息");
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesDeliveryOrder", printLogsb.toString(), null);
        return isok;
    }
    //endregion 在销售单处 出库单打印-按仓库打印-销售发货通知单打印

    //region 按订单顺序打印处理方法
    /**
     * 普通明细列表生成打印数据
     *
     * @param queryMap
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年11月20日
     */
    private void getDispatchBillPrintData(Map queryMap) throws Exception {
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
        Order saleOrder = (Order) queryMap.get("saleOrder");
        //打印的参数
        Map parameters = (Map) queryMap.get("parametersMap");
        //报表文件
        JasperReport jreport = (JasperReport) queryMap.get("JasperReport");
        //销售订单对应的发货单
      //  List<Saleout> saleoutList = (List<Saleout>) queryMap.get("saleoutList");
        //发货单明细
        List<OrderDetail> detailList = (List<OrderDetail>) queryMap.get("saleoutDetailList");

        if (null == detailList || detailList.size() == 0) {
            return;
        }
        //打印的编号
        Map<String, String> orderPrintIdMap = (Map<String, String>) queryMap.get("orderPrintIdMap");
        if (null == orderPrintIdMap) {
            orderPrintIdMap = new HashMap<String, String>();
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
        List<OrderDetail> tmpdList = detailList;
        if (countperpage > 0 && "1".equals(isfillblank)) {
            tmpdList = fillReportWithBlankForOrderDetail(detailList, countperpage);
        }


        //打印
        JasperPrint jrprint = null;

        jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

        if (null != jrprint) {
            jrprint.setName("发货打印(订单套打)-" + saleOrder.getId());

            if (!isview) {
                //记录打印次数使用
                orderPrintIdMap.put(saleOrder.getId(), saleOrder.getId());

                List<String> saleoutPrintIdList = new ArrayList<String>();
               /* for (Saleout saleout : saleoutList) {
                    if (null != saleout.getId()) {
                        saleoutPrintIdList.add(saleout.getId());
                    }
                }*/

                String saleoutCanprintIds = "";
                if (saleoutPrintIdList.size() > 0) {
                    String[] printarr = (String[]) saleoutPrintIdList.toArray(new String[saleoutPrintIdList.size()]);
                    saleoutCanprintIds = StringUtils.join(printarr, ",");
                }

                /*Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                callbackparamMap.put("id", saleOrder.getId());
                callbackparamMap.put("saleoutids", saleoutCanprintIds);
                callbackparamMap.put("fhprinttimes", saleOrder.getPrinttimes());    //当前发货单打印次数
                callbackparamMap.put("callback", "updateprinttimes");
                String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                jrprint.setProperty("agprint_callback_params", callbackparams);*/

                if (backHandleJobId != null) {

                    Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                    //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                    //实际打印的单据号
                    datahtmlparamMap.put("printOrderId", saleOrder.getId());
                    //打印任务编号
                    datahtmlparamMap.put("printJobId", backHandleJobId);
                    //页面点击时的单据号
                    datahtmlparamMap.put("printSourceOrderId", "");
                    String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                    jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                    PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                    printJobCallHandle.setJobid(backHandleJobId);
                    printJobCallHandle.setClassname("saleOrderPrintAction");
                    printJobCallHandle.setMethodname("salesDispatchBillPrintForXSDPrintReflectCallBackHandle");
                  //  printJobCallHandle.setMethodparam(callbackparams);
                    printJobCallHandle.setPages(jrprint.getPages().size());
                    printJobCallHandle.setPrintorderid(saleOrder.getId());
                    printJobCallHandle.setPrintordername("销售订单");
                    printJobCallHandle.setSourceorderid(saleOrder.getId());
                    printJobCallHandle.setSourceordername("销售订单");
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
     *
     * @param queryMap
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年11月20日
     */
    private void getDispatchBillSplitByBrandPrintData(Map queryMap) throws Exception {

    }

    /**
     * 根据仓库编号，判断是否是整形单
     *
     * @param queryMap
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年11月18日
     */
    private void getDispatchBillSplitByStorageZSPrintData(Map queryMap) throws Exception {
        //拆单类型
        String saleOrderPrintSplitType = "0";
        //是否预览
        Boolean isview = (Boolean) queryMap.get("isview");

        String printOrder = (String) queryMap.get("printOrder");

        String backHandleJobId = (String) queryMap.get("backHandleJobId");
        if (backHandleJobId == null || "".equals(backHandleJobId.trim())) {
            backHandleJobId = null;
        } else {
            backHandleJobId = backHandleJobId.trim();
        }
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
        Order saleOrder = (Order) queryMap.get("saleOrder");
        //打印的参数
        Map parameters = (Map) queryMap.get("parametersMap");
        //报表文件
        JasperReport jreport = (JasperReport) queryMap.get("JasperReport");
        //销售订单对应的发货单
       // List<Saleout> saleoutList = (List<Saleout>) queryMap.get("saleoutList");
        //发货单明细
        List<SaleoutDetail> detailList = (List<SaleoutDetail>) queryMap.get("saleoutDetailList");

        if (null == detailList || detailList.size() == 0) {
            return;
        }

        //打印的编号
        Map<String, String> orderPrintIdMap = (Map<String, String>) queryMap.get("orderPrintIdMap");
        if (null == orderPrintIdMap) {
            orderPrintIdMap = new HashMap<String, String>();
        }


        //是否显示整散分离不分单
        String showWholeByStorageStr = getSysParamValue("orderIsShowWholeByStorage");
        //是否显示整散分离分单
        String showWholeInMutiByStorageStr = getSysParamValue("orderIsShowWholeInMutiByStorage");

        String[] storageArr = null;
        //按客户进分按品牌拆单

        if (StringUtils.isNotEmpty(saleOrder.getStorageid())) {
            if (null == showWholeByStorageStr || "".equals(showWholeByStorageStr.trim())) {
                showWholeByStorageStr = "";
            } else {
                showWholeByStorageStr = showWholeByStorageStr.trim();
            }
            storageArr = showWholeByStorageStr.split(",");
            if (ArrayUtils.contains(storageArr, saleOrder.getStorageid())) {
                saleOrderPrintSplitType = "1"; //整散分离不分单
            } else {
                if (null == showWholeInMutiByStorageStr || "".equals(showWholeInMutiByStorageStr.trim())) {
                    showWholeInMutiByStorageStr = "";
                } else {
                    showWholeInMutiByStorageStr = showWholeInMutiByStorageStr.trim();
                }
                storageArr = showWholeInMutiByStorageStr.split(",");
                if (ArrayUtils.contains(storageArr, saleOrder.getStorageid())) {
                    saleOrderPrintSplitType = "2"; //整散分离分单
                }
            }
        } else {
            saleOrderPrintSplitType = "0";
        }

        if ("0".equals(saleOrderPrintSplitType)) {
            getDispatchBillPrintData(queryMap);
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

        //打印
        JasperPrint jrprint = null;

        List<String> detailGroupByList = null;

        Map detalListMap = storageSaleOutService.getSaleOutDetailListDataSplitByZSD(detailList);
        if (null == detalListMap) {
            return;
        }
        //整箱明细
        List<OrderDetail> zxDetailList = null;

        if (detalListMap.containsKey("zxDetailList")) {
            zxDetailList = (List<OrderDetail>) detalListMap.get("zxDetailList");
        }
        if (null == zxDetailList) {
            zxDetailList = new ArrayList<OrderDetail>();
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
           // zxDetailList.addAll(sxDetailList);
            //zxDetailList.addAll(zkouDetailList);
            if (zxDetailList.size() > 0) {

                List<OrderDetail> tmpdList = zxDetailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(zxDetailList, countperpage);
                }
                jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

                if (null != jrprint) {
                    jrprint.setName("发货单(按仓库)-整散单-" + saleOrder.getId());

                    if (!isview) {
                        //记录打印次数使用
                        orderPrintIdMap.put(saleOrder.getId(), saleOrder.getId());

                        List<String> saleoutPrintIdList = new ArrayList<String>();
                       /* for (Saleout saleout : saleoutList) {
                            if (null != saleout.getId()) {
                                saleoutPrintIdList.add(saleout.getId());
                            }
                        }
*/
                        String saleoutCanprintIds = "";
                        if (saleoutPrintIdList.size() > 0) {
                            String[] printarr = (String[]) saleoutPrintIdList.toArray(new String[saleoutPrintIdList.size()]);
                            saleoutCanprintIds = StringUtils.join(printarr, ",");
                        }

                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", saleOrder.getId());
                        callbackparamMap.put("saleoutids", saleoutCanprintIds);
                        callbackparamMap.put("fhprinttimes", saleOrder.getPrinttimes());    //当前发货单打印次数
                        callbackparamMap.put("splitbill", "true"); //拆分单
                        callbackparamMap.put("callback", "updateprinttimes");
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);


                        if (backHandleJobId != null) {

                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", saleOrder.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", backHandleJobId);
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(backHandleJobId);
                            printJobCallHandle.setClassname("saleOrderPrintAction");
                            printJobCallHandle.setMethodname("salesDispatchBillPrintForXSDPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(saleOrder.getId());
                            printJobCallHandle.setPrintordername("销售订单");
                            printJobCallHandle.setSourceorderid(saleOrder.getId());
                            printJobCallHandle.setSourceordername("销售订单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrList.add(jrprint);
                }
            }
        } else if ("2".equals(saleOrderPrintSplitType)) {


            if (zxDetailList.size() > 0) {


                List<OrderDetail> tmpdList = zxDetailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(zxDetailList, countperpage);
                }
                jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

                if (null != jrprint) {
                    jrprint.setName("发货单(按仓库)-整单-" + saleOrder.getId());

                    if (!isview) {
                        //记录打印次数使用
                        orderPrintIdMap.put(saleOrder.getId(), saleOrder.getId());

                        List<String> saleoutPrintIdList = new ArrayList<String>();
                       /* for (Saleout saleout : saleoutList) {
                            if (null != saleout.getId()) {
                                saleoutPrintIdList.add(saleout.getId());
                            }
                        }*/

                        String saleoutCanprintIds = "";
                        if (saleoutPrintIdList.size() > 0) {
                            String[] printarr = (String[]) saleoutPrintIdList.toArray(new String[saleoutPrintIdList.size()]);
                            saleoutCanprintIds = StringUtils.join(printarr, ",");
                        }

                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", saleOrder.getId());
                        callbackparamMap.put("saleoutids", saleoutCanprintIds);
                        callbackparamMap.put("fhprinttimes", saleOrder.getPrinttimes());    //当前发货单打印次数
                        callbackparamMap.put("splitbill", "true"); //拆分单
                        callbackparamMap.put("callback", "updateprinttimes");
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);


                        if (backHandleJobId != null) {

                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", saleOrder.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", backHandleJobId);
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(backHandleJobId);
                            printJobCallHandle.setClassname("saleOrderPrintAction");
                            printJobCallHandle.setMethodname("salesDispatchBillPrintForXSDPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(saleOrder.getId());
                            printJobCallHandle.setPrintordername("销售订单");
                            printJobCallHandle.setSourceorderid(saleOrder.getId());
                            printJobCallHandle.setSourceordername("销售订单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrList.add(jrprint);
                }
            }

            if (sxDetailList.size() > 0) {
                List<OrderDetail> tmpdList = zxDetailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(zxDetailList, countperpage);
                }
                jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

                if (null != jrprint) {
                    jrprint.setName("发货单(按仓库)-散单-" + saleOrder.getId());

                    if (!isview) {
                        //记录打印次数使用
                        orderPrintIdMap.put(saleOrder.getId(), saleOrder.getId());

                        List<String> saleoutPrintIdList = new ArrayList<String>();
                      /*  for (Saleout saleout : saleoutList) {
                            if (null != saleout.getId()) {
                                saleoutPrintIdList.add(saleout.getId());
                            }
                        }*/

                        String saleoutCanprintIds = "";
                        if (saleoutPrintIdList.size() > 0) {
                            String[] printarr = (String[]) saleoutPrintIdList.toArray(new String[saleoutPrintIdList.size()]);
                            saleoutCanprintIds = StringUtils.join(printarr, ",");
                        }

                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", saleOrder.getId());
                        callbackparamMap.put("saleoutids", saleoutCanprintIds);
                        callbackparamMap.put("fhprinttimes", saleOrder.getPrinttimes());    //当前发货单打印次数
                        callbackparamMap.put("splitbill", "true"); //拆分单
                        callbackparamMap.put("callback", "updateprinttimes");
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);


                        if (backHandleJobId != null) {

                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", saleOrder.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", backHandleJobId);
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(backHandleJobId);
                            printJobCallHandle.setClassname("saleOrderPrintAction");
                            printJobCallHandle.setMethodname("salesDispatchBillPrintForXSDPrintReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(saleOrder.getId());
                            printJobCallHandle.setPrintordername("销售订单");
                            printJobCallHandle.setSourceorderid(saleOrder.getId());
                            printJobCallHandle.setSourceordername("销售订单");
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
    //endregion

    //region 按库位处理打印处理方法
    /**
     * 普通明细列表生成打印数据
     *
     * @param queryMap
     * @throws Exception
     * @author zhanghonghui
     * @date 2015年11月20日
     */
    private void getDelieryOrderPrintData(Map queryMap) throws Exception {
        //是否预览
        Boolean isview = (Boolean) queryMap.get("isview");

        String printOrder = (String) queryMap.get("printOrder");

        String backHandleJobId = (String) queryMap.get("backHandleJobId");
        if (backHandleJobId == null || "".equals(backHandleJobId.trim())) {
            backHandleJobId = null;
        } else {
            backHandleJobId = backHandleJobId.trim();
        }

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
        //销售订单
        Order saleOrder = (Order) queryMap.get("saleOrder");
        //发货单
        Saleout saleout = (Saleout) queryMap.get("saleoutOrder");
        //打印的参数
        Map parameters = (Map) queryMap.get("parametersMap");
        //报表文件
        JasperReport jreport = (JasperReport) queryMap.get("JasperReport");

        //已经组装的发货通知单打印编号
        Map<String, String> orderPrintIdMap = (Map<String, String>) queryMap.get("orderPrintIdMap");
        if (null == orderPrintIdMap) {
            orderPrintIdMap = new HashMap<String, String>();
        }

        //已经组装的打印编号
        Map<String, String> saleoutPrintIdMap = (Map<String, String>) queryMap.get("saleoutPrintIdMap");
        if (null == saleoutPrintIdMap) {
            saleoutPrintIdMap = new HashMap<String, String>();
        }
        //发货单明细
        List<OrderDetail> detailList = (List<OrderDetail>) queryMap.get("saleoutDetailList");

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

        List<OrderDetail> tmpdList = detailList;
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

                Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                callbackparamMap.put("id", saleout.getId());
                callbackparamMap.put("saleorderid", saleOrder.getId());
                callbackparamMap.put("callback", "updatephprinttimes");
                callbackparamMap.put("updateDispatchPrintCount", "true");
                int orderPrintCount = 0;
                if (null != saleOrder.getPrinttimes()) {
                    orderPrintCount = saleOrder.getPrinttimes();
                }
                int orderPhPrintCount = 0;
                if (null != saleOrder.getPhprinttimes()) {
                    orderPhPrintCount = saleOrder.getPhprinttimes();
                }
                callbackparamMap.put("orderPrintCount", orderPrintCount);    //当前销售单打印次数
                callbackparamMap.put("orderPhPrintCount", orderPhPrintCount);    //当前销售单配货打印次数
                callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                jrprint.setProperty("agprint_callback_params", callbackparams);

                if (StringUtils.isNotEmpty(saleOrder.getId())) {
                    orderPrintIdMap.put(saleOrder.getId(), saleOrder.getId());
                }
                saleoutPrintIdMap.put(saleout.getId(), saleout.getId());


                if (backHandleJobId != null) {

                    Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                    //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                    //实际打印的单据号
                    datahtmlparamMap.put("printOrderId", saleOrder.getId());
                    //打印任务编号
                    datahtmlparamMap.put("printJobId", backHandleJobId);
                    //页面点击时的单据号
                    datahtmlparamMap.put("printSourceOrderId", "");
                    String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                    jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                    PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                    printJobCallHandle.setJobid(backHandleJobId);
                    printJobCallHandle.setClassname("saleOrderPrintAction");
                    printJobCallHandle.setMethodname("salesDeliveryOrderForXSDReflectCallBackHandle");
                    printJobCallHandle.setMethodparam(callbackparams);
                    printJobCallHandle.setPages(jrprint.getPages().size());
                    printJobCallHandle.setPrintorderid(saleOrder.getId());
                    printJobCallHandle.setPrintordername("销售订单");
                    printJobCallHandle.setSourceorderid(saleOrder.getId());
                    printJobCallHandle.setSourceordername("销售订单");
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
     *
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

        String printOrder = (String) queryMap.get("printOrder");

        String backHandleJobId = (String) queryMap.get("backHandleJobId");
        if (backHandleJobId == null || "".equals(backHandleJobId.trim())) {
            backHandleJobId = null;
        } else {
            backHandleJobId = backHandleJobId.trim();
        }

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

        //销售订单
        Order saleOrder = (Order) queryMap.get("saleOrder");
        //发货单
        Saleout saleout = (Saleout) queryMap.get("saleoutOrder");
        //打印的参数
        Map parameters = (Map) queryMap.get("parametersMap");
        //报表文件
        JasperReport jreport = (JasperReport) queryMap.get("JasperReport");

        //已经组装的发货通知单打印编号
        Map<String, String> orderPrintIdMap = (Map<String, String>) queryMap.get("orderPrintIdMap");
        if (null == orderPrintIdMap) {
            orderPrintIdMap = new HashMap<String, String>();
        }

        //已经组装的打印编号
        Map<String, String> saleoutPrintIdMap = (Map<String, String>) queryMap.get("saleoutPrintIdMap");
        if (null == saleoutPrintIdMap) {
            saleoutPrintIdMap = new HashMap<String, String>();
        }
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
              //  tmpdList = fillReportWithBlankForOrderDetail(entry.getValue(), countperpage);
            }


            jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

            if (null != jrprint) {
                jrprint.setName("发货打印(仓库)-" + saleout.getId() + "品牌：" + entry.getKey());

                if (!isview) {

                    Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                    callbackparamMap.put("id", saleout.getId());
                    callbackparamMap.put("saleorderid", saleOrder.getId());
                    callbackparamMap.put("callback", "updatephprinttimes");
                    callbackparamMap.put("updateDispatchPrintCount", "true");
                    int orderPrintCount = 0;
                    if (null != saleOrder.getPrinttimes()) {
                        orderPrintCount = saleOrder.getPrinttimes();
                    }
                    int orderPhPrintCount = 0;
                    if (null != saleOrder.getPhprinttimes()) {
                        orderPhPrintCount = saleOrder.getPhprinttimes();
                    }
                    callbackparamMap.put("orderPrintCount", orderPrintCount);    //当前销售单打印次数
                    callbackparamMap.put("orderPhPrintCount", orderPhPrintCount);    //当前销售单配货打印次数
                    callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                    String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                    jrprint.setProperty("agprint_callback_params", callbackparams);

                    if (StringUtils.isNotEmpty(saleOrder.getId())) {
                        orderPrintIdMap.put(saleOrder.getId(), saleOrder.getId());
                    }
                    saleoutPrintIdMap.put(saleout.getId(), saleout.getId());


                    if (backHandleJobId != null) {

                        Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                        //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                        //实际打印的单据号
                        datahtmlparamMap.put("printOrderId", saleOrder.getId());
                        //打印任务编号
                        datahtmlparamMap.put("printJobId", backHandleJobId);
                        //页面点击时的单据号
                        datahtmlparamMap.put("printSourceOrderId", "");
                        String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                        jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                        PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                        printJobCallHandle.setJobid(backHandleJobId);
                        printJobCallHandle.setClassname("saleOrderPrintAction");
                        printJobCallHandle.setMethodname("salesDeliveryOrderForXSDReflectCallBackHandle");
                        printJobCallHandle.setMethodparam(callbackparams);
                        printJobCallHandle.setPages(jrprint.getPages().size());
                        printJobCallHandle.setPrintorderid(saleOrder.getId());
                        printJobCallHandle.setPrintordername("销售订单");
                        printJobCallHandle.setSourceorderid(saleOrder.getId());
                        printJobCallHandle.setSourceordername("销售订单");
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
     *
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

        String printOrder = (String) queryMap.get("printOrder");

        String backHandleJobId = (String) queryMap.get("backHandleJobId");
        if (backHandleJobId == null || "".equals(backHandleJobId.trim())) {
            backHandleJobId = null;
        } else {
            backHandleJobId = backHandleJobId.trim();
        }

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

        //销售订单
        Order saleOrder = (Order) queryMap.get("saleOrder");
        //发货单
        Saleout saleout = (Saleout) queryMap.get("saleoutOrder");
        //打印的参数
        Map parameters = (Map) queryMap.get("parametersMap");
        //报表文件
        JasperReport jreport = (JasperReport) queryMap.get("JasperReport");

        //已经组装的发货通知单打印编号
        Map<String, String> orderPrintIdMap = (Map<String, String>) queryMap.get("orderPrintIdMap");
        if (null == orderPrintIdMap) {
            orderPrintIdMap = new HashMap<String, String>();
        }

        //已经组装的打印编号
        Map<String, String> saleoutPrintIdMap = (Map<String, String>) queryMap.get("saleoutPrintIdMap");
        if (null == saleoutPrintIdMap) {
            saleoutPrintIdMap = new HashMap<String, String>();
        }
        //发货单明细
        List<SaleoutDetail> detailList = (List<SaleoutDetail>) queryMap.get("saleoutDetailList");

        if (null == detailList || detailList.size() == 0) {
            return;
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

        Map detalListMap = storageSaleOutService.getSaleOutDetailListDataSplitByZSD(detailList);
        if (null == detalListMap) {
            return;
        }
        //整箱明细
        List<OrderDetail> zxDetailList = null;

        if (detalListMap.containsKey("zxDetailList")) {
            zxDetailList = (List<OrderDetail>) detalListMap.get("zxDetailList");
        }
        if (null == zxDetailList) {
            zxDetailList = new ArrayList<OrderDetail>();
        }

        //整散分离不分单
        if ("1".equals(saleOrderPrintSplitType)) {
            if (zxDetailList.size() > 0) {

                List<OrderDetail> tmpdList = zxDetailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(zxDetailList, countperpage);
                }

                jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

                if (null != jrprint) {
                    jrprint.setName("发货单(按仓库)-整散单-" + saleout.getId());

                    if (!isview) {

                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", saleout.getId());
                        callbackparamMap.put("saleorderid", saleOrder.getId());
                        callbackparamMap.put("callback", "updatephprinttimes");
                        callbackparamMap.put("updateDispatchPrintCount", "true");
                        int orderPrintCount = 0;
                        if (null != saleOrder.getPrinttimes()) {
                            orderPrintCount = saleOrder.getPrinttimes();
                        }
                        int orderPhPrintCount = 0;
                        if (null != saleOrder.getPhprinttimes()) {
                            orderPhPrintCount = saleOrder.getPhprinttimes();
                        }
                        callbackparamMap.put("orderPrintCount", orderPrintCount);    //当前销售单打印次数
                        callbackparamMap.put("orderPhPrintCount", orderPhPrintCount);    //当前销售单配货打印次数
                        callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        if (StringUtils.isNotEmpty(saleOrder.getId())) {
                            orderPrintIdMap.put(saleOrder.getId(), saleOrder.getId());
                        }
                        saleoutPrintIdMap.put(saleout.getId(), saleout.getId());

                        if (backHandleJobId != null) {

                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", saleOrder.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", backHandleJobId);
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(backHandleJobId);
                            printJobCallHandle.setClassname("saleOrderPrintAction");
                            printJobCallHandle.setMethodname("salesDeliveryOrderForXSDReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(saleOrder.getId());
                            printJobCallHandle.setPrintordername("销售订单");
                            printJobCallHandle.setSourceorderid(saleOrder.getId());
                            printJobCallHandle.setSourceordername("销售订单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrList.add(jrprint);
                }
            }
        } else if ("2".equals(saleOrderPrintSplitType)) {

            if (zxDetailList.size() > 0) {


                List<OrderDetail> tmpdList = zxDetailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForOrderDetail(zxDetailList, countperpage);
                }

                jrprint = JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));

                if (null != jrprint) {
                    jrprint.setName("发货单(按仓库)-整单-" + saleout.getId());

                    if (!isview) {

                        Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                        callbackparamMap.put("id", saleout.getId());
                        callbackparamMap.put("saleorderid", saleOrder.getId());
                        callbackparamMap.put("callback", "updatephprinttimes");
                        callbackparamMap.put("updateDispatchPrintCount", "true");
                        int orderPrintCount = 0;
                        if (null != saleOrder.getPrinttimes()) {
                            orderPrintCount = saleOrder.getPrinttimes();
                        }
                        int orderPhPrintCount = 0;
                        if (null != saleOrder.getPhprinttimes()) {
                            orderPhPrintCount = saleOrder.getPhprinttimes();
                        }
                        callbackparamMap.put("orderPrintCount", orderPrintCount);    //当前销售单打印次数
                        callbackparamMap.put("orderPhPrintCount", orderPhPrintCount);    //当前销售单配货打印次数
                        callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                        String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                        jrprint.setProperty("agprint_callback_params", callbackparams);

                        if (StringUtils.isNotEmpty(saleOrder.getId())) {
                            orderPrintIdMap.put(saleOrder.getId(), saleOrder.getId());
                        }
                        saleoutPrintIdMap.put(saleout.getId(), saleout.getId());

                        if (backHandleJobId != null) {

                            Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                            //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                            //实际打印的单据号
                            datahtmlparamMap.put("printOrderId", saleOrder.getId());
                            //打印任务编号
                            datahtmlparamMap.put("printJobId", backHandleJobId);
                            //页面点击时的单据号
                            datahtmlparamMap.put("printSourceOrderId", "");
                            String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                            jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                            PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                            printJobCallHandle.setJobid(backHandleJobId);
                            printJobCallHandle.setClassname("saleOutPrintAction");
                            printJobCallHandle.setMethodname("salesDeliveryOrderForXSDReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(saleOrder.getId());
                            printJobCallHandle.setPrintordername("销售订单");
                            printJobCallHandle.setSourceorderid(saleOrder.getId());
                            printJobCallHandle.setSourceordername("销售订单");
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

    //endregion

    //region 在销售订单处打印预览-销售订单打印

    /**
     * 在销售订单处打印预览-销售订单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "PrintView", type = 0)
    public String salesOrderPrintView() throws Exception {
        return salesOrderPrintHandle(true);
    }

    /**
     * 在销售订单打印-销售订单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-8-21
     */
    @UserOperateLog(key = "Print", type = 0)
    public String salesOrderPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            salesOrderCallBackHandle(printcallback);
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

    /**
     * 在销售订单打印-销售订单打印
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-11
     */
    private String salesOrderPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "在销售订单中-按销售订单数据打印-" + CommonUtils.getDataNumberSeconds());
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
            List<Order> list = salesOrderService.getSalesOrderListBy(paramMap);
            if (list.size() == 0) {
                if ("ajaxhtml".equals(viewtype)) {
                    ajaxResultMap.put("flag", false);
                    ajaxResultMap.put("msg", "未能找到相关销售订单打印");
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
                printJob.setJobname("在销售订单中-按销售订单数据打印");
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
                    ajaxResultMap.put("msg", "申请销售订单打印时失败");
                    addJSONObject(ajaxResultMap);
                    return SUCCESS;
                }
                ajaxResultMap.put("printJobId", printJob.getId());
                ajaxResultMap.put("printname", "销售订单打印任务-" + printJob.getId());
            }
            for (Order item : list) {
                if (null == item.getId() || "".equals(item.getId())) {
                    continue;
                }

                List<OrderDetail> detailList = item.getOrderDetailList();

                if (null == detailList || detailList.size() == 0) {
                    continue;
                }

                BigDecimal totalamount = BigDecimal.ZERO;

                for (OrderDetail orderDetail : detailList) {
                    if (null != orderDetail.getTaxamount()) {
                        totalamount = totalamount.add(orderDetail.getTaxamount());
                    }
                }

                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "sales_order");
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
                if (null != item.getPrinttimes()) {
                    orderPrintCount = item.getPrinttimes();
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

                if (item.getPrinttimes() == null) {
                    item.setPrinttimes(0);
                }

                if (!isview) {
                    item.setPrinttimes(item.getPrinttimes());
                } else {
                    item.setPrinttimes(item.getPrinttimes() + 1);
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
                parameters.put("P_PRINTUSER", sysUser.getName());    //打印人
                //打印人
                parameters.put("P_PrintUser", sysUser.getName());    //打印人
                //打印时间
                parameters.put("P_PrintDate", new Date());
                //销售订单号
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
                parameters.put("P_SaleArea", customerInfo.getSalesareaname());    //销售订单号
				/*客户相关*/


                //客户单据信息号
                //客户单号
                parameters.put("P_CustomerBillId", item.getSourceid());

                parameters.put("P_TotalAmount", totalamount);

                /**打印模板参数用的单据信息**/
                Order orderInfo = (Order) CommonUtils.deepCopy(item);
                orderInfo.setOrderDetailList(null);
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

                List<OrderDetail> tmpdList = detailList;
                if (countperpage > 0 && "1".equals(isfillblank)) {
                    tmpdList = fillReportWithBlankForSalesOrderDetail(detailList, countperpage);
                }

                /**打印参数组装结束**/
                JasperPrint jrprint =
                        JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(tmpdList));


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
                            printJobCallHandle.setMethodname("salesOrderReflectCallBackHandle");
                            printJobCallHandle.setMethodparam(callbackparams);
                            printJobCallHandle.setPages(jrprint.getPages().size());
                            printJobCallHandle.setPrintorderid(item.getId());
                            printJobCallHandle.setPrintordername("销售订单");
                            printJobCallHandle.setSourceorderid(item.getId());
                            printJobCallHandle.setSourceordername("销售订单");
                            printJobCallHandle.setStatus("0");
                            printJobCallHandle.setType("1");
                            agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                        }
                    }
                    jrprint.setName(item.getId() + "-销售订单打印-" + item.getId());
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
                printLogsb.append(" 操作名称：在销售单中-按销售订单数据打印");
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
                printLogsb.append("打印申请-在销售单中-按销售订单数据打印异常：");
                //printLogsb.append("申请打印销售单编号："+saleidarrs);
                //printLogsb.append(" 。");
                printLogsb.append("销售单编号： " + orderCanprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-在销售单中-按销售订单数据打印预览异常：");
                printLogsb.append("预览的销售单编号：" + saleidarrs);
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：salesOrderPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-salesOrder", printLogsb.toString(), null);

            logger.error("在销售单中-按销售订单数据打印及预览处理异常", ex);


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
    public void salesOrderCallBackHandle(String printcallback) throws Exception {
        String saleidarrs = request.getParameter("saleidarrs");
        if (null == saleidarrs) {
            saleidarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        //printLogsb.append("更新打印次数：");
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesOrder", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallHandle-salesOrder", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            //printLogsb.append("申请打印销售单编号："+saleidarrs);
            //printLogsb.append(" 。");
            printLogsb.append("销售订单编号： " + id);
            try {
                Order order = salesOrderService.getPureOrder(id);
                if (null != order) {
                    order.setPrinttimes(1);
                    salesOrderService.updateOrderPrinttimes(order);
                    printLogsb.append(" 更新打印次数成功");
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-按销售订单数据更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无销售订单编号信息");
        }

        printLogsb.append(" 。");
        printLogsb.append(" 更新响应来源于：销售订单打印。");
        printLogsb.append(" 打印数据来源(销售单编号)：");
        printLogsb.append(saleidarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesOrder", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 打印回调
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-6
     */
    public boolean salesOrderReflectCallBackHandle(String printcallback) throws Exception {
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
            printLogsb.append("销售订单编号： " + id);
            try {
                Order order = salesOrderService.getPureOrder(id);
                if (null != order) {
                    order.setPrinttimes(1);
                    order.setPhprinttimes(null);
                    salesOrderService.updateOrderPrinttimes(order);
                    printLogsb.append(" 更新打印次数成功");
                    isok = true;
                } else {
                    printLogsb.append(" 更新打印次数失败");
                }
            } catch (Exception ex) {
                printLogsb.append(" 更新打印次数失败。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("在销售单中-按销售订单数据更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无销售订单编号信息");
        }

        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallHandle-salesOrder", printLogsb.toString(), null);
        return isok;
    }
    //endregion 在销售订单处打印预览-销售订单打印


    /**
     * 循环加入空白行
     * @param list
     * @param countperpage
     * @return java.util.List
     * @throws
     * @author zhanghonghui
     * @date Aug 14, 2017
     */
    private List fillReportWithBlankForOrderDetail(List<OrderDetail> list, Integer countperpage) {
        if (countperpage == null || countperpage <= 0) {
            return list;
        }
        int isize = 0;
        if (null == list) {
            list = new ArrayList<OrderDetail>();
        } else {
            isize = list.size();
        }
        int iLeft = isize % countperpage;
        if (isize == 0 || (isize != 0 && iLeft != 0)) {
            iLeft = countperpage - iLeft;
        }
        for (int i = 0; i < iLeft; i++) {
            OrderDetail detailItem = new OrderDetail();
            detailItem.setGoodsid(null);
            detailItem.setGoodsInfo(null);
            detailItem.setUnitnum(null);
            detailItem.setAuxnum(null);
          //  detailItem.setAuxremainder(null);
            detailItem.setTax(null);
            detailItem.setNotaxamount(null);
            detailItem.setTaxamount(null);
        //    detailItem.setId(-999);
            list.add(detailItem);
        }
        return list;
    }


    /**
     * 循环加入销售订单空白行
     * @param list
     * @param countperpage
     * @return java.util.List
     * @throws
     * @author zhanghonghui
     * @date Aug 14, 2017
     */
    private List fillReportWithBlankForSalesOrderDetail(List<OrderDetail> list, Integer countperpage) {
        if (countperpage == null || countperpage <= 0) {
            return list;
        }
        int isize = 0;
        if (null == list) {
            list = new ArrayList<OrderDetail>();
        } else {
            isize = list.size();
        }
        int iLeft = isize % countperpage;
        if (isize == 0 || (isize != 0 && iLeft != 0)) {
            iLeft = countperpage - iLeft;
        }
        for (int i = 0; i < iLeft; i++) {
            OrderDetail detailItem = new OrderDetail();
            detailItem.setGoodsid(null);
            detailItem.setGoodsInfo(null);
            detailItem.setUnitnum(null);
            detailItem.setAuxnum(null);
            detailItem.setOvernum(null);
            detailItem.setTax(null);
            detailItem.setNotaxamount(null);
            detailItem.setTaxamount(null);
            detailItem.setId("-999");
            list.add(detailItem);
        }
        return list;
    }
}
