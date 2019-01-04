/**
 * @(#)PurchaseQueryPrintAction.java
 * @author zhanghonghui
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-30 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.action.purchase;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.agprint.service.impl.AgprintServiceImpl;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.JasperReportUtils;
import com.hd.agent.common.util.ListSortLikeSQLComparator;
import com.hd.agent.purchase.model.PurchaseJournalAccount;
import com.hd.agent.purchase.service.IPurchaseQueryService;
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
 * 采购进货流水打印
 * @author zhanghonghui
 */
public class PurchaseQueryPrintAction extends BaseFilesAction {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(PurchaseQueryPrintAction.class);
    /**
     * 采购业务查询service
     */
    private IPurchaseQueryService purchaseQueryService;

    public IPurchaseQueryService getPurchaseQueryService() {
        return purchaseQueryService;
    }

    public void setPurchaseQueryService(IPurchaseQueryService purchaseQueryService) {
        this.purchaseQueryService = purchaseQueryService;
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
     * 流水账打印预览
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-12
     */
    public String purchaseJournalAccountPrintView() throws Exception {
        return purchaseJournalAccountPrintHandle(true);
    }

    /**
     * 流水账打印
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-10-9
     */
    public String purchaseJournalAccountPrint() throws Exception {
        String justprint = request.getParameter("justprint");
        boolean isview = false;
        if ("1".equals(justprint) || "true".equals(justprint)) {
            isview = false;
        } else {
            isview = true;
        }
        return purchaseJournalAccountPrintHandle(isview);
    }

    /**
     * 流水账打印及预览处理
     * @param isview
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-11-12
     */
    private String purchaseJournalAccountPrintHandle(boolean isview) throws Exception {
        String viewtype = request.getParameter("viewtype");
        if (null == viewtype) {
            viewtype = "pdf";
        }
        viewtype = viewtype.trim();
        //返回的格式：
        //{flag:true,printname:'',printdata:[{}],msg:'',totalpages:100}
        //ajax数据
        Map ajaxResultMap = new HashMap();
        ajaxResultMap.put("printname", "采购进货流水打印-" + CommonUtils.getDataNumberSeconds());
        Map printTempletSettingFistMap = null;
        String templetid = request.getParameter("templetid");
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());

        String uninvoice = request.getParameter("invoice0");
        if (null != uninvoice && !"".equals(uninvoice.trim())) {
            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "已核销的才能打印或预览");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            } else {
                return null;
            }
        }
        String businessdatestart = request.getParameter("businessdatestart");
        String businessdateend = request.getParameter("businessdateend");

        if ("".equals(businessdatestart) || "".equals(businessdateend)) {
            if ("ajaxhtml".equals(viewtype)) {
                ajaxResultMap.put("flag", false);
                ajaxResultMap.put("msg", "请输入业务日期时间段以便打印或预览");
                addJSONObject(ajaxResultMap);
                return SUCCESS;
            } else {
                return null;
            }
        }
        String countdate = businessdatestart + "至" + businessdateend;

        String invoice = request.getParameter("invoice1");
        if (null == invoice || "".equals(invoice)) {
            map.put("invoice1", "1");
        }

        String abilltype = request.getParameter("billtype0"); //进货
        String rbilltype = request.getParameter("billtype1"); //退货

        String accountType = "采购";
        if (StringUtils.isNotEmpty(abilltype) && StringUtils.isNotEmpty(rbilltype)) {
            accountType = "进货退货";
        } else if (StringUtils.isNotEmpty(abilltype)) {
            accountType = "进货";
        } else if (StringUtils.isNotEmpty(rbilltype)) {
            accountType = "退货";
        }

        String ispreview = request.getParameter("ispreview");
        if ("1".equals(ispreview)) {
            ispreview = "1";
        } else {
            ispreview = "0";
        }
        if (map.containsKey("justprint")) {
            map.remove("justprint");
        }
        if (map.containsKey("viewtype")) {
            map.remove("viewtype");
        }
        if (map.containsKey("ispreview")) {
            map.remove("ispreview");
        }
        SysUser sysUser = getSysUser();
        try {

            //打印任务信息
            PrintJob printJob = new PrintJob();
            if ("ajaxhtml".equals(viewtype)) {
                printJob.setAddtime(new Date());
                printJob.setAdduserid(sysUser.getUserid());
                printJob.setAddusername(sysUser.getName());
                printJob.setIp(CommonUtils.getIP(request));
                printJob.setJobname("采购进货流水打印");
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
                ajaxResultMap.put("printname", "采购进货流水打印任务-" + printJob.getId());
            }
            pageMap.setCondition(map);
            List<PurchaseJournalAccount> list = purchaseQueryService.showPurchaseJournalAccountList(pageMap);

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
            templetQueryMap.put("code", "purchase_journalaccout");
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
            //String reportModelFile = servletContext.getRealPath("/ireport/purchase_journalaccout/purchase_journalaccout.jasper");
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
            //打印内容排序策略
            String printDataOrderSeq = (String) templetResultMap.get("printDataOrderSeq");

            //进行打印内容明细排序
            if (null != printDataOrderSeq && !"".equals(printDataOrderSeq.trim())) {
                Collections.sort(list, ListSortLikeSQLComparator.createComparator(printDataOrderSeq.trim()));
            }
            Map parameters = null;

            List<JasperPrint> jrlist = new ArrayList<JasperPrint>();
            parameters = new HashMap();
            parameters.put("P_countdate", countdate);
            parameters.put("P_accountTypeName", accountType);
                /*共用参数*/
            parameters.put("P_TPL_COMPANYNAME", printTemplet.getCompanytitle());
            agprintServiceImpl.setTempletCommonParameter(parameters);

            JasperPrint jrprint =
                    JasperFillManager.fillReport(jreport, parameters, new JRBeanCollectionDataSource(list));
            if (null != jrprint) {
                jrprint.setName("采购进货流水账-" + countdate);
                jrlist.add(jrprint);


                Map<String, Object> callbackparamMap = new HashMap<String, Object>();
                callbackparamMap.put("rand", CommonUtils.getRandomWithTime());    //随机数
                String callbackparams = JSONUtils.mapToJsonStr(callbackparamMap);
                jrprint.setProperty("agprint_callback_params", callbackparams);


                if (StringUtils.isNotEmpty(printJob.getId())) {
                    //打印次数更新回调方法
                    Map<String, Object> datahtmlparamMap = new HashMap<String, Object>();
                    //ajaxhtml 打印时，前台加入到打印时，出错提示用。
                    //实际打印的单据号
                    datahtmlparamMap.put("printOrderId", "");
                    //打印任务编号
                    datahtmlparamMap.put("printJobId", printJob.getId());
                    //页面点击时的单据号
                    datahtmlparamMap.put("printSourceOrderId", "");
                    String datahtmlparams = JSONUtils.mapToJsonStr(datahtmlparamMap);
                    jrprint.setProperty("agprint_params_datahtml", datahtmlparams);

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
            logStr = "Exception ：purchaseJournalAccountPrintHandle()>>>>>>>>>>>>>>>>异常信息： " + ex.getMessage();
            logger.error("采购流水账打印及预览处理异常", ex);


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
}

