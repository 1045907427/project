package com.hd.agent.agprint.action.storage;

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
import com.hd.agent.storage.model.BigSaleOut;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IBigSaleOutService;
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
 * 大单发货打印
 *
 * @author master
 */
public class BigSaleOutPrintAction extends BaseFilesAction {

    private static final Logger logger = Logger.getLogger(BigSaleOutPrintAction.class);

    /**
     * 打印发货打印业务层
     */
    private IBigSaleOutService bigSaleOutService;

    public IBigSaleOutService getBigSaleOutService() {
        return bigSaleOutService;
    }

    public void setBigSaleOutService(IBigSaleOutService bigSaleOutService) {
        this.bigSaleOutService = bigSaleOutService;
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
    //endregion 参数初始化

    /**
     * 大单发货单打印预览
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Oct 15, 2017
     */
    @UserOperateLog(key = "PrintView", type = 0)
    public String storageBigSaleOutPrintView() throws Exception {
        return bigSaleOutPrintHandle(true);
    }

    /**
     * 大单发货单打印
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 25, 2014
     */
    @UserOperateLog(key = "Print", type = 0)
    public String storageBigSaleOutPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        String printcallback = request.getParameter("agprint_callback_params");
        if (null != printcallback && !"".equals(printcallback)) {
            storageBigSaleoutCallBackHandle(printcallback);
        } else {
            boolean isview = false;
            if ("1".equals(justprint) || "true".equals(justprint)) {
                isview = false;
            } else {
                isview = true;
            }
            return bigSaleOutPrintHandle(isview);
        }
        return null;
    }
    //region 大单发货总处理
    /**
     * 大单发货单打印及预览处理
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date Oct 15, 2017
     */
    @UserOperateLog(key = "print", type = 5)
    private String bigSaleOutPrintHandle(boolean isview) throws Exception {
        String printbilltype=request.getParameter("printbilltype");
        String jobname="整件分拣&分拣单";
        if("zjfj".equals(printbilltype) || "zj".equals(printbilltype) || "fj".equals(printbilltype)){
            return bigSaleOutForZJFJPrintHandle(isview);
        }else if("customer".equals(printbilltype)){
            return bigSaleOutForCustomerPrintHandle(isview);
        }else if("brand".equals(printbilltype)){
            return bigSaleOutForBrandPrintHandle(isview);
        }else{
            printbilltype="zjfj";
            return bigSaleOutForZJFJPrintHandle(isview);
        }
    }
    //endregion


    //region 整件分拣
    /**
     * 整件分拣
     * @param isview
     * @return java.util.Map
     * @throws
     * @author zhanghonghui
     * @date Oct 05, 2017
     */
    private String bigSaleOutForZJFJPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        String jobname="大单发货整件分拣&分拣单";
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "大单发货单打印-" + CommonUtils.getDataNumberSeconds());
        String idarrs = request.getParameter("idarrs"); //这里的参数是id
        if (null == idarrs || "".equals(idarrs.trim())) {
            if ("ajaxhtml".equals(viewtype)){
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
        //分拣单模板编号
        String fjtempletid = request.getParameter("fjtempletid");
        String printbilltype=request.getParameter("printbilltype");
        if("zjfj".equals(printbilltype)){
            jobname="大单发货整件分拣&分拣单";
        }else if("zj".equals(printbilltype)){
            jobname="大单发货整件分拣";
        }else if("fj".equals(printbilltype)){
            jobname="大单发货分拣单";
            fjtempletid=templetid;
        }
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
                printJob.setJobname(jobname+"打印");
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
                ajaxResultMap.put("printname", jobname+"打印任务-" + printJob.getId());
            }
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            //获取审核通过的大单发货商品明细列表
            List<Map> dataList = storageSaleOutPrintService.getBigSaleOutGoodsListForPrint(map);
            if (null==dataList || dataList.size() == 0) {
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
            Map templetResultMap = new HashMap();
            String printTempletFile="";
            PrintTemplet printTemplet = new PrintTemplet();
            String printDataOrderSeq = "";
            JasperReport jreport = null;

            if(!"fj".equals(printbilltype)) {
                templetQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                templetQueryMap.put("code", "storage_bigsaleout");
                if (null != templetid && !"".equals(templetid)) {
                    templetQueryMap.put("templetid", templetid);
                } else {
                    templetQueryMap.put("templetid", "notnull");
                }
                templetResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(templetQueryMap);
                if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                    printTempletSettingFistMap = new HashMap();
                    printTempletSettingFistMap.putAll(templetResultMap);
                }
                //打印模板文件
                printTempletFile = (String) templetResultMap.get("printTempletFile");
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
                if (templetResultMap.containsKey("printTempletInfo")) {
                    printTemplet = (PrintTemplet) templetResultMap.get("printTempletInfo");
                    if (null == printTemplet) {
                        printTemplet = new PrintTemplet();
                        templetResultMap.put("printTempletInfo", printTemplet);
                    }
                }
                //打印内容排序策略
                printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

                //String reportModelFile = servletContext.getRealPath("/ireport/storage_bigsaleout/storage_bigsaleout.jasper");

                jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));
            }

            //获取分拣打印模板
            Map fjTempletQueryMap = new HashMap();
            //分拣模板
            Map fjTempletResultMap = new HashMap();
            String fjPrintTempletFile = "";
            //分拣模板信息
            PrintTemplet fjPrintTemplet = new PrintTemplet();
            //排序
            String fjPrintDataOrderSeq ="";
            //打印模板
            JasperReport fjReport = null;

            if(!"zj".equals(printbilltype)) {
                fjTempletQueryMap.put("realServerPath", servletContext.getRealPath("/"));
                fjTempletQueryMap.put("code", "storage_bigsaleout");
                if (null != fjtempletid && !"".equals(fjtempletid)) {
                    fjTempletQueryMap.put("templetid", fjtempletid);
                } else {
                    fjTempletQueryMap.put("templetid", "notnull");
                }
                fjTempletResultMap = agprintServiceImpl.showPrintTempletByPrintQuery(fjTempletQueryMap);
                //打印模板文件
                fjPrintTempletFile = (String) fjTempletResultMap.get("printTempletFile");
                if (null == fjPrintTempletFile || "".equals(fjPrintTempletFile.trim())) {
                    if ("ajaxhtml".equals(viewtype)) {
                        ajaxResultMap.put("flag", false);
                        ajaxResultMap.put("msg", "未找到相关打印模板");
                        addJSONObject(ajaxResultMap);
                        return SUCCESS;
                    } else {
                        return null;
                    }
                }
                if("fj".equals(printbilltype)){
                    if (printTempletSettingFistMap == null || printTempletSettingFistMap.size() == 0) {
                        printTempletSettingFistMap = new HashMap();
                        printTempletSettingFistMap.putAll(fjTempletResultMap);
                    }
                }
                if (fjTempletResultMap.containsKey("printTempletInfo")) {
                    fjPrintTemplet = (PrintTemplet) fjTempletResultMap.get("printTempletInfo");
                    if (null == fjPrintTemplet) {
                        fjPrintTemplet = new PrintTemplet();
                        fjTempletResultMap.put("printTempletInfo", fjPrintTemplet);
                    }
                }
                //打印内容排序策略
                fjPrintDataOrderSeq = (String) fjTempletResultMap.get("printDataOrderSeq");

                fjReport = (JasperReport) JRLoader.loadObject(new File(fjPrintTempletFile));
            }

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList=new ArrayList<String>();

            for(Map itemMap : dataList){
                if(null==itemMap || itemMap.size()==0){
                    continue;
                }
                String id=(String)itemMap.get("id");

                List<SaleoutDetail> zjdatalist = (List<SaleoutDetail>) itemMap.get("goodsList");
                if(null==zjdatalist){
                    continue;
                }
                //整件数据排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(zjdatalist, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }
                //分拣打印数据
                List<JasperPrint> fjJRList = new ArrayList<JasperPrint>();

                //大单发货数据信息
                BigSaleOut bigSaleOut = (BigSaleOut)itemMap.get("bigSaleOut");
                Integer billnum=(Integer) itemMap.get("billnum");
                if (null != bigSaleOut) {
                    if (null == bigSaleOut.getPrinttimes()) {
                        bigSaleOut.setPrinttimes(0);
                    }
                    if (!isview) {
                        bigSaleOut.setPrinttimes(bigSaleOut.getPrinttimes());
                    } else {
                        bigSaleOut.setPrinttimes(bigSaleOut.getPrinttimes() + 1);
                    }
                    //整件打印模板参数
                    Map zjparameters = new HashMap();

                    //分拣数据列表
                    Map<String, List<SaleoutDetail>> fjdataListMap=(Map<String, List<SaleoutDetail>>) itemMap.get("billdetailmap");
                    //分拣发货单map
                    Map<String,Saleout> fjdataOrderMap=(Map<String,Saleout>)itemMap.get("billordermap");

                    if(!"zj".equals(printbilltype)) {
                        if (null != fjdataListMap && fjdataListMap.size() > 0) {
                            if (null == billnum) {
                                billnum = fjdataListMap.size();
                            }
                            //遍历分拣单
                            Iterator iter = fjdataListMap.entrySet().iterator();
                            while (iter.hasNext()) {
                                Map.Entry entry = (Map.Entry) iter.next();
                                //key值，发货单编号
                                String saleoutid = (String) entry.getKey();
                                //发货单信息
                                Saleout saleout = (Saleout) fjdataOrderMap.get(saleoutid);
                                //发货单明细信息
                                List<SaleoutDetail> fjdataList = (List<SaleoutDetail>) entry.getValue();

                                //分拣明细排序
                                if (null != fjPrintDataOrderSeq && !"".equals(fjPrintDataOrderSeq.trim())) {
                                    Collections.sort(fjdataList, ListSortLikeSQLComparator.createComparator(fjPrintDataOrderSeq.trim()));
                                }

                                Map fjparameters = new HashMap();

                            /*共用参数*/
                                fjparameters.put("P_TPL_COMPANYNAME", fjPrintTemplet.getCompanytitle());
                                //整件编号
                                fjparameters.put("P_BigSaleOut_ID", bigSaleOut.getId());

                                if (null != saleout) {
                                    /**打印模板参数用的单据信息**/
                                    Saleout orderInfo = (Saleout) CommonUtils.deepCopy(saleout);
                                    fjparameters.put("P_OrderInfo", orderInfo);

                                    Customer customerInfo = saleout.getCustomerInfo();
                                    if (null == customerInfo) {
                                        customerInfo = new Customer();
                                    }

                                    SysUser addPersonnel = null;
                                    Personnel salePersonnel = null;

                                    if (StringUtils.isNotEmpty(saleout.getSalesuser())) {
                                        salePersonnel = getPersonnelInfoById(saleout.getSalesuser());
                                    }
                                    if (null == salePersonnel && StringUtils.isNotEmpty(customerInfo.getSalesuserid())) {
                                        salePersonnel = getPersonnelInfoById(customerInfo.getSalesuserid());
                                    }
                                    if (null == salePersonnel) {
                                        salePersonnel = new Personnel();
                                    }
                                    if (null == addPersonnel || (StringUtils.isNotEmpty(addPersonnel.getUserid()) && !addPersonnel.getUserid().equals(saleout.getAdduserid()))) {
                                        addPersonnel = getSysUserById(saleout.getAdduserid());
                                    }
                                    if (null == addPersonnel) {
                                        addPersonnel = new SysUser();
                                    }

                                    //销售员
                                    fjparameters.put("P_SaleUser", salePersonnel.getName());
                                    //销售员电话
                                    fjparameters.put("P_SaleUserTel", salePersonnel.getTelphone());
                                    //打印人
                                    fjparameters.put("P_PRINTUSER", sysUser.getName());
                                    //打印人
                                    fjparameters.put("P_PrintUser", sysUser.getName());
                                    //打印时间
                                    fjparameters.put("P_PrintDate", printDate);
                                    //销售订单号
                                    fjparameters.put("P_SaleId", saleout.getSaleorderid());
                                    //业务日期
                                    fjparameters.put("P_Businessdate", CommonUtils.stringToDate(saleout.getBusinessdate()));
                                    //业务日期：字符串格式
                                    fjparameters.put("P_Businessdate_str", saleout.getBusinessdate());
                                    ////业务日期
                                    fjparameters.put("P_BillDate", saleout.getBusinessdate());
                                    //订单备注
                                    fjparameters.put("P_BillRemark", saleout.getRemark());
                                    //订单备注
                                    fjparameters.put("P_Remark", saleout.getRemark());
                                    //打印次数
                                    fjparameters.put("P_PrintTimes", saleout.getPrinttimes());
                                    //仓库名称
                                    fjparameters.put("P_Storage", saleout.getStoragename());
                                    //添加用户名称
                                    fjparameters.put("P_Adduser", saleout.getAddusername());
                                    //添加用户联系电话
                                    fjparameters.put("P_BillAdduserTel", addPersonnel.getTelphone());

				                /*客户相关*/
                                    String customer = "";

                                    if (StringUtils.isNotEmpty(customerInfo.getName())) {
                                        customer += customerInfo.getName();
                                    }

                                    fjparameters.put("P_CustomerInfo", customerInfo);
                                    //客户编号+名称
                                    fjparameters.put("P_Customer", customer);
                                    //客户编号
                                    fjparameters.put("P_Customerno", customerInfo.getId());
                                    //客户编号
                                    fjparameters.put("P_Customerid", customerInfo.getId());
                                    //客户名称
                                    fjparameters.put("P_CustomerName", customerInfo.getName());
                                    //客户地址
                                    fjparameters.put("P_CustomerAddr", customerInfo.getAddress());
                                    //联系人
                                    fjparameters.put("P_Contact", customerInfo.getContact());    //通用版，使用contact
                                    //联系电话
                                    fjparameters.put("P_ContactTel", customerInfo.getMobile());
                                    //销售区域
                                    fjparameters.put("P_SaleArea", customerInfo.getSalesareaname());    //销售订单号
                                }
                                agprintServiceImpl.setTempletCommonParameter(fjparameters);

                                JasperPrint fjjrprint =
                                        JasperFillManager.fillReport(fjReport, fjparameters, new JRBeanCollectionDataSource(fjdataList));

                                if (null != fjjrprint) {

                                    if("fj".equals(printbilltype)){
                                        if (!isview) {
                                            Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                                            callbackparamMap.put("id", id);
                                            callbackparamMap.put("callback", "updateprinttimes");
                                            //数据库里的打印次数
                                            callbackparamMap.put("orderPrintCount", bigSaleOut.getPrinttimes());
                                            callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                                            String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                                            fjjrprint.setProperty("agprint_callback_params", callbackparams);

                                            bigSaleOut.setPrinttimes(null);
                                            //多单据循环才用到这个
                                            printIdList.add(id);


                                            if (StringUtils.isNotEmpty(printJob.getId())) {
                                                //打印次数更新回调方法
                                                Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                                                //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                                                //实际打印的单据号
                                                datahtmlparamMap.put("printOrderId", id);
                                                //打印任务编号
                                                datahtmlparamMap.put("printJobId", printJob.getId());
                                                //页面点击时的单据号
                                                datahtmlparamMap.put("printSourceOrderId", id);
                                                String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                                                fjjrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                                                PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                                                printJobCallHandle.setJobid(printJob.getId());
                                                printJobCallHandle.setClassname("bigSaleOutPrintAction");
                                                printJobCallHandle.setMethodname("storageBigSaleoutReflectCallBackHandle");
                                                printJobCallHandle.setMethodparam(callbackparams);
                                                printJobCallHandle.setPages(fjjrprint.getPages().size());
                                                printJobCallHandle.setPrintorderid(id);
                                                printJobCallHandle.setPrintordername(jobname);
                                                printJobCallHandle.setSourceorderid(id);
                                                printJobCallHandle.setSourceordername(jobname);
                                                printJobCallHandle.setStatus("0");
                                                printJobCallHandle.setType("1");
                                                agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                                            }
                                        }
                                    }

                                    fjJRList.add(fjjrprint);
                                }
                            }
                        }
                    }
                    zjparameters.put("P_Billnum",billnum);


                    if(StringUtils.isNotEmpty(bigSaleOut.getStorageid())){
                        String storageInfoKey="storageInfo_"+bigSaleOut.getStorageid();
                        if(itemMap.containsKey(storageInfoKey)) {
                            StorageInfo storageInfo = (StorageInfo) itemMap.get(storageInfoKey);
                            if (null != storageInfo) {
                                String storageManagerInfoKey="storage_managerInfo_" + storageInfo.getManageruserid();
                                if(itemMap.containsKey(storageManagerInfoKey)) {
                                    Personnel personnel = (Personnel) itemMap.get(storageManagerInfoKey);
                                    if (null != personnel) {
                                        zjparameters.put("P_StorageManager", personnel.getName());
                                    }
                                }
                            }
                        }
                    }
                    //仓库名称
                    zjparameters.put("P_Storage", bigSaleOut.getStoragename());

                    /**打印模板参数用的单据信息**/
                    BigSaleOut orderInfo = (BigSaleOut) CommonUtils.deepCopy(bigSaleOut);
                    zjparameters.put("P_OrderInfo", orderInfo);
                    /*共用参数*/
                    zjparameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                    agprintServiceImpl.setTempletCommonParameter(zjparameters);

                    if(!"fj".equals(printbilltype)) {
                        JasperPrint jrprint =
                                JasperFillManager.fillReport(jreport, zjparameters, new JRBeanCollectionDataSource(zjdatalist));
                        if (null != jrprint) {
                            if (!isview) {
                                Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                                callbackparamMap.put("id", id);
                                callbackparamMap.put("callback", "updateprinttimes");
                                //数据库里的打印次数
                                callbackparamMap.put("orderPrintCount", bigSaleOut.getPrinttimes());
                                callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                                String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                                jrprint.setProperty("agprint_callback_params", callbackparams);

                                bigSaleOut.setPrinttimes(null);
                                //多单据循环才用到这个
                                printIdList.add(id);


                                if (StringUtils.isNotEmpty(printJob.getId())) {
                                    //打印次数更新回调方法
                                    Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                                    //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                                    //实际打印的单据号
                                    datahtmlparamMap.put("printOrderId", id);
                                    //打印任务编号
                                    datahtmlparamMap.put("printJobId", printJob.getId());
                                    //页面点击时的单据号
                                    datahtmlparamMap.put("printSourceOrderId", id);
                                    String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                                    jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                                    PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                                    printJobCallHandle.setJobid(printJob.getId());
                                    printJobCallHandle.setClassname("bigSaleOutPrintAction");
                                    printJobCallHandle.setMethodname("storageBigSaleoutReflectCallBackHandle");
                                    printJobCallHandle.setMethodparam(callbackparams);
                                    printJobCallHandle.setPages(jrprint.getPages().size());
                                    printJobCallHandle.setPrintorderid(id);
                                    printJobCallHandle.setPrintordername(jobname);
                                    printJobCallHandle.setSourceorderid(id);
                                    printJobCallHandle.setSourceordername(jobname);
                                    printJobCallHandle.setStatus("0");
                                    printJobCallHandle.setType("1");
                                    agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                                }
                            }
                            jrprint.setName("大单发货单-" + id);
                            jrlist.add(jrprint);
                            jrlist.addAll(fjJRList);
                        }
                    }else{
                        jrlist.addAll(fjJRList);
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
                if(printIdList.size()>0){
                    String[] printarr= (String[])printIdList.toArray(new String[printIdList.size()]);
                    canprintIds=StringUtils.join(printarr, ",");
                }
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                printLogsb.append("大单发货单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：大单发货单打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-storageBigSaleout", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-大单发货单打印异常：");
                printLogsb.append("大单发货单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-大单发货单预览异常：");
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：storageBigSaleoutPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-storageBigSaleout", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("大单发货单打印及预览处理异常", ex);


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
    //endregion

    //region 按客户
    /**
     * 大单发货单按客户打印及预览处理
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date Oct 15, 2017
     */
    @UserOperateLog(key = "print", type = 5)
    private String bigSaleOutForCustomerPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        String jobname="按商品分客户";
        String jobtype=request.getParameter("jobtype");
        if("2".equals(jobtype)){
            jobname="按商品分客户区块";
        }
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "大单发货单打印-" + CommonUtils.getDataNumberSeconds());
        String idarrs = request.getParameter("idarrs"); //这里的参数是id
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
                printJob.setJobname(jobname+"打印");
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
                ajaxResultMap.put("printname", jobname+"打印任务-" + printJob.getId());
            }
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            //获取审核通过的大单发货商品明细列表
            List<Map> dataList = storageSaleOutPrintService.getBigSaleOutGoodsCustomerListForPrint(map);
            if(dataList==null || dataList.size()==0){
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
            templetQueryMap.put("code", "storage_bigsaleout");
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
                    ajaxResultMap.put("msg", "未找到相关大单发货打印模板");
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


            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList=new ArrayList<String>();

            for(Map itemMap : dataList) {
                if (null == itemMap || itemMap.size() == 0) {
                    continue;
                }
                String id=(String)itemMap.get("id");
                List<SaleoutDetail> detailList = (List<SaleoutDetail>)itemMap.get("orderDetailList");
                if (null==detailList || detailList.size() == 0) {
                    continue;
                }

                //进行打印内容明细排序
                if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                    Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                }
                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));


                BigSaleOut bigSaleOut =(BigSaleOut) itemMap.get("bigSaleOut");
                if (null != bigSaleOut) {
                    if (null == bigSaleOut.getPrinttimes()) {
                        bigSaleOut.setPrinttimes(0);
                    }
                    if (!isview) {
                        bigSaleOut.setPrinttimes(bigSaleOut.getPrinttimes());
                    } else {
                        bigSaleOut.setPrinttimes(bigSaleOut.getPrinttimes() + 1);
                    }
                    Map parameters = new HashMap();
                    if(StringUtils.isNotEmpty(bigSaleOut.getStorageid())){
                        String storageInfoKey="storageInfo_"+bigSaleOut.getStorageid();
                        if(itemMap.containsKey(storageInfoKey)) {
                            StorageInfo storageInfo = (StorageInfo) itemMap.get(storageInfoKey);
                            if (null != storageInfo) {
                                String storageManagerInfoKey="storage_managerInfo_" + storageInfo.getManageruserid();
                                if(itemMap.containsKey(storageManagerInfoKey)) {
                                    Personnel personnel = (Personnel) itemMap.get(storageManagerInfoKey);
                                    if (null != personnel) {
                                        parameters.put("P_StorageManager", personnel.getName());
                                    }
                                }
                            }
                        }
                    }
                    Integer customernums=(Integer)itemMap.get("customernums");
                    parameters.put("P_Billnum", customernums);

                    //仓库名称
                    parameters.put("P_Storage", bigSaleOut.getStoragename());

                    /**打印模板参数用的单据信息**/
                    BigSaleOut orderInfo = (BigSaleOut) CommonUtils.deepCopy(bigSaleOut);
                    parameters.put("P_OrderInfo", orderInfo);
                    /*共用参数*/
                    parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                    agprintServiceImpl.setTempletCommonParameter(parameters);
                    JasperPrint jrprint =
                            JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));
                    if (null != jrprint) {
                        if (!isview) {
                            Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                            callbackparamMap.put("id", id);
                            callbackparamMap.put("callback", "updateprinttimes");
                            //数据库里的打印次数
                            callbackparamMap.put("orderPrintCount", bigSaleOut.getPrinttimes());
                            callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                            String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                            jrprint.setProperty("agprint_callback_params", callbackparams);

                            bigSaleOut.setPrinttimes(null);
                            printIdList.add(id);//多单据循环才用到这个


                            if (StringUtils.isNotEmpty(printJob.getId())) {
                                //打印次数更新回调方法
                                Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                                //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                                //实际打印的单据号
                                datahtmlparamMap.put("printOrderId", id);
                                //打印任务编号
                                datahtmlparamMap.put("printJobId", printJob.getId());
                                //页面点击时的单据号
                                datahtmlparamMap.put("printSourceOrderId", id);
                                String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                                jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                                PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                                printJobCallHandle.setJobid(printJob.getId());
                                printJobCallHandle.setClassname("bigSaleOutPrintAction");
                                printJobCallHandle.setMethodname("storageBigSaleoutReflectCallBackHandle");
                                printJobCallHandle.setMethodparam(callbackparams);
                                printJobCallHandle.setPages(jrprint.getPages().size());
                                printJobCallHandle.setPrintorderid(id);
                                printJobCallHandle.setPrintordername(jobname);
                                printJobCallHandle.setSourceorderid(id);
                                printJobCallHandle.setSourceordername(jobname);
                                printJobCallHandle.setStatus("0");
                                printJobCallHandle.setType("1");
                                agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                            }
                        }
                        jrprint.setName("大单发货单-" + id);
                        jrlist.add(jrprint);
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
					if(printIdList.size()>0){
						String[] printarr= (String[])printIdList.toArray(new String[printIdList.size()]);
						canprintIds=StringUtils.join(printarr, ",");
					}
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                printLogsb.append("大单发货单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：大单发货单打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-storageBigSaleout", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-大单发货单打印异常：");
                printLogsb.append("大单发货单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-大单发货单预览异常：");
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：storageBigSaleoutPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-storageBigSaleout", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("大单发货单打印及预览处理异常", ex);


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
    //endregion

    //region 按品牌分商品
    /**
     * 大单发货单按品牌分商品打印及预览处理
     *
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date Oct 15, 2017
     */
    @UserOperateLog(key = "print", type = 5)
    private String bigSaleOutForBrandPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        String jobname="按品牌分商品";
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "大单发货单打印-" + CommonUtils.getDataNumberSeconds());
        String idarrs = request.getParameter("idarrs"); //这里的参数是id
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
                printJob.setJobname(jobname+"打印");
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
                ajaxResultMap.put("printname", jobname+"打印任务-" + printJob.getId());
            }
            Map map = new HashMap();
            map.put("idarrs", idarrs);
            //获取审核通过的大单发货商品明细列表
            List<Map> dataList = storageSaleOutPrintService.getBigOutBrandGoodsDivMapForPrint(map);
            if(dataList==null || dataList.size()==0){
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
            templetQueryMap.put("code", "storage_bigsaleout");
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
                    ajaxResultMap.put("msg", "未找到相关大单发货打印模板");
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


            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            List<String> printIdList=new ArrayList<String>();

            for(Map itemMap : dataList) {
                if (null == itemMap || itemMap.size() == 0) {
                    continue;
                }
                String id=(String)itemMap.get("id");

                JasperReport jreport = (JasperReport) JRLoader.loadObject(new File(printTempletFile));


                BigSaleOut bigSaleOut =(BigSaleOut) itemMap.get("bigSaleOut");
                if (null != bigSaleOut) {
                    if (null == bigSaleOut.getPrinttimes()) {
                        bigSaleOut.setPrinttimes(0);
                    }
                    if (!isview) {
                        bigSaleOut.setPrinttimes(bigSaleOut.getPrinttimes());
                    } else {
                        bigSaleOut.setPrinttimes(bigSaleOut.getPrinttimes() + 1);
                    }
                    Map parameters = new HashMap();
                    if(StringUtils.isNotEmpty(bigSaleOut.getStorageid())){
                        String storageInfoKey="storageInfo_"+bigSaleOut.getStorageid();
                        if(itemMap.containsKey(storageInfoKey)) {
                            StorageInfo storageInfo = (StorageInfo) itemMap.get(storageInfoKey);
                            if (null != storageInfo) {
                                String storageManagerInfoKey="storage_managerInfo_" + storageInfo.getManageruserid();
                                if(itemMap.containsKey(storageManagerInfoKey)) {
                                    Personnel personnel = (Personnel) itemMap.get(storageManagerInfoKey);
                                    if (null != personnel) {
                                        parameters.put("P_StorageManager", personnel.getName());
                                    }
                                }
                            }
                        }
                    }
                    Integer billnum=(Integer)itemMap.get("billnum");
                    parameters.put("P_Billnum", billnum);
                    //仓库名称
                    parameters.put("P_Storage", bigSaleOut.getStoragename());

                    /**打印模板参数用的单据信息**/
                    BigSaleOut orderInfo = (BigSaleOut) CommonUtils.deepCopy(bigSaleOut);
                    parameters.put("P_OrderInfo", orderInfo);
                    /*共用参数*/
                    parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
                    agprintServiceImpl.setTempletCommonParameter(parameters);


                    Map<String,SaleoutDetail> billsummap =new HashMap<String, SaleoutDetail>();
                    if(itemMap.containsKey("billsummap")){
                        billsummap = (HashMap) itemMap.get("billsummap");
                    }
                    Map<String, List<SaleoutDetail>> billdetailmap = new LinkedHashMap<String, List<SaleoutDetail>>();
                    if(itemMap.containsKey("billdetailmap")){
                        billdetailmap = (LinkedHashMap)itemMap.get("billdetailmap");
                    }
                    if(null==billdetailmap || billdetailmap.size()==0){
                        continue;
                    }
                    for (Map.Entry<String, List<SaleoutDetail>> entry : billdetailmap.entrySet()) {
                        //主键
                        String key = entry.getKey();

                        SaleoutDetail saleoutSumDetail = billsummap.get(key);

                        parameters.put("P_SaleoutDetailSumInfo", saleoutSumDetail);

                        List<SaleoutDetail> detailList =entry.getValue();
                        if (null==detailList || detailList.size() == 0) {
                            continue;
                        }


                        //进行打印内容明细排序
                        if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                            Collections.sort(detailList, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
                        }
                        JasperPrint jrprint =
                                JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(detailList));
                        if (null != jrprint) {
                            if (!isview) {
                                Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                                callbackparamMap.put("id", id);
                                //数据库里的打印次数
                                callbackparamMap.put("orderPrintCount", bigSaleOut.getPrinttimes());
                                callbackparamMap.put("callback", "updateprinttimes");
                                callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                                String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                                jrprint.setProperty("agprint_callback_params", callbackparams);

                                bigSaleOut.setPrinttimes(null);
                                printIdList.add(id);//多单据循环才用到这个


                                if (StringUtils.isNotEmpty(printJob.getId())) {
                                    //打印次数更新回调方法
                                    Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                                    //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                                    //实际打印的单据号
                                    datahtmlparamMap.put("printOrderId", id);
                                    //打印任务编号
                                    datahtmlparamMap.put("printJobId", printJob.getId());
                                    //页面点击时的单据号
                                    datahtmlparamMap.put("printSourceOrderId", id);
                                    String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                                    jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

                                    PrintJobCallHandle printJobCallHandle = new PrintJobCallHandle();
                                    printJobCallHandle.setJobid(printJob.getId());
                                    printJobCallHandle.setClassname("bigSaleOutPrintAction");
                                    printJobCallHandle.setMethodname("storageBigSaleoutReflectCallBackHandle");
                                    printJobCallHandle.setMethodparam(callbackparams);
                                    printJobCallHandle.setPages(jrprint.getPages().size());
                                    printJobCallHandle.setPrintorderid(id);
                                    printJobCallHandle.setPrintordername(jobname);
                                    printJobCallHandle.setSourceorderid(id);
                                    printJobCallHandle.setSourceordername(jobname);
                                    printJobCallHandle.setStatus("0");
                                    printJobCallHandle.setType("1");
                                    agprintServiceImpl.getPrintJobService().addPrintJobCallHandle(printJobCallHandle);
                                }
                            }
                            jrprint.setName("大单发货单-" + id);
                            jrlist.add(jrprint);
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
                if(printIdList.size()>0){
                    String[] printarr= (String[])printIdList.toArray(new String[printIdList.size()]);
                    canprintIds=StringUtils.join(printarr, ",");
                }
                StringBuilder printLogsb = new StringBuilder();
                printLogsb.append("打印申请：");
                printLogsb.append("大单发货单编号： " + canprintIds);
                printLogsb.append(" 。");
                printLogsb.append(" 操作名称：大单发货单打印");
                printLogsb.append(" 。");
                addPrintLogInfo("PrintHandle-storageBigSaleout", printLogsb.toString(), null);
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
                printLogsb.append("打印申请-大单发货单打印异常：");
                printLogsb.append("大单发货单编号： " + canprintIds);
                printLogsb.append(" 。");
            } else {
                printLogsb.append("打印预览申请-大单发货单预览异常：");
                printLogsb.append(" 。");
            }
            printLogsb.append("Exception ：storageBigSaleoutPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage());
            addPrintLogInfo("PrintHandle-storageBigSaleout", printLogsb.toString(), null);
            logStr = printLogsb.toString();
            logger.error("大单发货单打印及预览处理异常", ex);


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
    //endregion
    
    /**
     * 大单发货单打印回调处理
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 25, 2014
     */
    private void storageBigSaleoutCallBackHandle(String printcallback) throws Exception {
        String idarrs = request.getParameter("idarrs");
        if (null == idarrs) {
            idarrs = "";
        }
        StringBuilder printLogsb = new StringBuilder();
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBigSaleout-storageBigSaleout", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBigSaleout-storageBigSaleout", printLogsb.toString(), null);
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("failure");
            return;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            printLogsb.append("大单发货单编号： " + id);

            try {
                Integer orderPrintCount = null;
                if(map.containsKey("orderPrintCount")){
                    orderPrintCount = new Integer(map.get("orderPrintCount").toString());
                }
                if (null == orderPrintCount ) {
                    orderPrintCount = 0;
                }
                BigSaleOut bigSaleOut=bigSaleOutService.getBigSaleOutInfo(id);
                if(null==bigSaleOut){
                    printLogsb.append(" 更新大单发货单打印次数失败，未找到相关单据");
                    addPrintLogInfo("PrintCallBigSaleout-storageBigSaleout", printLogsb.toString(), null);
                    response.reset();
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write("ok");
                }
                if(null==bigSaleOut.getPrinttimes() ){
                    bigSaleOut.setPrinttimes(0);
                }
                if(orderPrintCount.equals(bigSaleOut.getPrinttimes())  && orderPrintCount>=0) {
                    storageSaleOutPrintService.updatePrinttimesSyncUpBigSalout(id);
                }else{
                    response.reset();
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write("ok");
                    return;
                }
                printLogsb.append(" 更新大单发货单打印次数成功");
            } catch (Exception ex) {
                printLogsb.append(" 更新大单发货单打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("大单发货单更新打印次数异常", ex);
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无大单发货单编号信息");
        }
        printLogsb.append(" 。");

        printLogsb.append(" 更新响应来源于：大单发货单。");
        printLogsb.append(" 打印数据来源(大单发货单编号)：");
        printLogsb.append(idarrs);
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBigSaleout-storageBigSaleout", printLogsb.toString(), null);
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("ok");
    }

    /**
     * 反射调用大单发货单打印回调处理
     *
     * @param printcallback
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 25, 2014
     */
    public boolean storageBigSaleoutReflectCallBackHandle(String printcallback) throws Exception {
        boolean isok = false;
        StringBuilder printLogsb = new StringBuilder();
        if (null == printcallback || "".equals(printcallback)) {
            printLogsb.append("更新打印次数失败：回调参数为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBigSaleout-storageBigSaleout", printLogsb.toString(), null);
            return isok;
        }

        Map map = JSONUtils.jsonStrToMap(printcallback);
        if (null == map) {
            printLogsb.append("更新打印次数失败：回调参数转成Map时为空");
            printLogsb.append(" 。");
            addPrintLogInfo("PrintCallBigSaleout-storageBigSaleout", printLogsb.toString(), null);
            return isok;
        }
        String id = (String) map.get("id");
        if (null != id && !"".equals(id.trim())) {
            printLogsb.append(" 。");
            printLogsb.append("大单发货单编号： " + id);

            try {
                Integer icount=0;
                if(map.containsKey("orderPrintCount")){
                    icount=new Integer(map.get("orderPrintCount").toString());
                }
                BigSaleOut bigSaleOut=bigSaleOutService.getBigSaleOutInfo(id);
                if(null==bigSaleOut){
                    printLogsb.append(" 更新大单发货单打印次数失败，未找到相关单据");
                    addPrintLogInfo("PrintCallBigSaleout-storageBigSaleout", printLogsb.toString(), null);
                    return false;
                }
                if(null==bigSaleOut.getPrinttimes() ){
                    bigSaleOut.setPrinttimes(0);
                }
                if(icount.equals(bigSaleOut.getPrinttimes()) && icount>=0) {
                    storageSaleOutPrintService.updatePrinttimesSyncUpBigSalout(id);
                }else{
                    return isok;
                }
                printLogsb.append(" 更新大单发货单打印次数成功");
                isok = true;
            } catch (Exception ex) {
                printLogsb.append(" 更新大单发货单打印次数失败");
                printLogsb.append(" 。");
                printLogsb.append(" 异常信息：" + ex.getMessage());
                logger.error("大单发货单更新打印次数异常", ex);
                isok = false;
            }
        } else {
            printLogsb.append(" 。");
            printLogsb.append("更新打印次数失败：回调参数中无大单发货单编号信息");
        }
        printLogsb.append(" 。");
        addPrintLogInfo("PrintCallBigSaleout-storageBigSaleout", printLogsb.toString(), null);
        return isok;
    }
}
