package com.hd.agent.agprint.action;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.PrintJobDetail;
import com.hd.agent.agprint.model.PrintJobDetailImage;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintJob;
import com.hd.agent.agprint.model.PrintJobCallHandle;
import com.hd.agent.agprint.model.query.PrintJobCallHandleQuery;
import com.hd.agent.agprint.service.IPrintJobService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.util.SpringContextUtils;

/**
 * 打印任务Action
 *
 * @author master
 */
public class PrintJobAction extends BaseAction {

    private static final Logger logger = Logger.getLogger(PrintJobAction.class);
    /**
     * 打印任务服务层
     */
    private IPrintJobService printJobService;

    public IPrintJobService getPrintJobService() {
        return printJobService;
    }

    public void setPrintJobService(IPrintJobService printJobService) {
        this.printJobService = printJobService;
    }

    /**
     * 更新打印次数
     *
     * @throws Exception
     * @author zhanghonghui
     * @date 2016年5月31日
     */
    public String updatePrintJobCallback() throws Exception {
        String printJobId = request.getParameter("jobid");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (null == printJobId || "".equals(printJobId.trim())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到打印任务编号");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        SysUser sysUser = getSysUser();
        PrintJob printJob = printJobService.getPrintJobWithUserid(printJobId, sysUser.getUserid());
        if (null == printJob) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到打印任务编号");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if ("1".equals(printJob.getStatus())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "打印已经完成");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        PrintJobCallHandleQuery printJobCallHandleQuery = new PrintJobCallHandleQuery();
        printJobCallHandleQuery.setJobid(printJobId);
        printJobCallHandleQuery.setStatus("0");
        List<PrintJobCallHandle> handleList = printJobService.getPrintJobCallHandleListBy(printJobCallHandleQuery);
        int iSuccess = 0;
        int iFailure = 0;
        StringBuffer messageBuffer = new StringBuffer();
        Map<String, Object> clazzMap = new HashMap<String, Object>();
        Map<String, Object> methodMap = new HashMap<String, Object>();
        for (PrintJobCallHandle printJobCallHandle : handleList) {
            if (StringUtils.isEmpty(printJobCallHandle.getClassname()) ||
                    StringUtils.isEmpty(printJobCallHandle.getMethodname()) ||
                    StringUtils.isEmpty(printJobCallHandle.getMethodparam())) {
                if (StringUtils.isNotEmpty(printJobCallHandle.getPrintorderid())) {
                    messageBuffer.append("打印的单据号：");
                    messageBuffer.append(printJobCallHandle.getPrintorderid());
                    if (StringUtils.isNotEmpty(printJobCallHandle.getSourceorderid())) {
                        messageBuffer.append(" 上游单据号：" + printJobCallHandle.getSourceorderid());
                    }
                    messageBuffer.append(" 未知的处理<br/>");
                }
                iFailure = iFailure + 1;
                continue;
            }
            String objectKeyId = printJobCallHandle.getClassname();
            String methodKeyId = printJobCallHandle.getClassname() + "." + printJobCallHandle.getMethodname();
            Boolean operFlag = false;
            try {

                Object clazzObject = null;
                Method method = null;
                if (clazzMap.containsKey(objectKeyId) && methodMap.containsKey(methodKeyId)) {
                    clazzObject = (Object) clazzMap.get(objectKeyId);
                    method = (Method) methodMap.get(methodKeyId);
                    if (clazzObject == null || method == null) {
                        clazzObject = null;
                        method = null;
                        clazzMap.remove(objectKeyId);
                        methodMap.remove(methodKeyId);
                    }
                }
                if (clazzObject == null || method == null) {
                    clazzObject = SpringContextUtils.getBean(printJobCallHandle.getClassname());
                    //clazzObject=Class.forName(printJobCallHandle.getClassname());
                    Method[] methods = clazzObject.getClass().getMethods();
                    for (Method m : methods) {
                        if (m.getName().equals(printJobCallHandle.getMethodname())) {
                            method = m;
                            break;
                        }
                    }
                }
                if (clazzObject != null && method != null) {
                    operFlag = (Boolean) method.invoke(clazzObject, printJobCallHandle.getMethodparam());
                    if (operFlag == null) {
                        operFlag = false;
                    }
                }
            } catch (Exception ex) {
                logger.error(ex, ex.getCause());
            }
            if (operFlag == false) {
                if (StringUtils.isNotEmpty(printJobCallHandle.getPrintorderid())) {
                    messageBuffer.append("打印的单据号：");
                    messageBuffer.append(printJobCallHandle.getPrintorderid());
                    /*
					if(StringUtils.isNotEmpty(printJobCallHandle.getSourceorderid())){
						messageBuffer.append(" 上游单据号："+ printJobCallHandle.getSourceorderid());
					}
					*/
                    messageBuffer.append(" 更新次数未成功<br/>");
                }
                iFailure = iFailure + 1;
            } else if (operFlag == true) {
                iSuccess = iSuccess + 1;
                PrintJobCallHandle updatePJB = new PrintJobCallHandle();
                updatePJB.setId(printJobCallHandle.getId());
                updatePJB.setStatus("1");
                printJobService.updatePrintJobCallHandleStatus(updatePJB);
            }
        }
        if (iSuccess > 0) {
            resultMap.put("flag", true);
            PrintJob updatePrintJob = new PrintJob();
            updatePrintJob.setId(printJobId);
            updatePrintJob.setStatus("1");
            printJobService.updatePrintJobStatus(updatePrintJob);
        } else {
            resultMap.put("flag", false);
        }
        resultMap.put("iSuccess", iSuccess);
        resultMap.put("iFailure", iFailure);
        resultMap.put("msg", messageBuffer.toString());
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 打印任务内容内容使用
     *
     * @param
     * @return void
     * @throws
     * @author zhang_honghui
     * @date Jan 06, 2017
     */
    public void getPrintJobDetailContentData() throws Exception {
        String jobdetailid = request.getParameter("jobdetailid");
        String viewtype = request.getParameter("viewtype");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        boolean flag = false;

        try {
            writer = response.getWriter();
            PrintJobDetail printJobDetail = printJobService.getPrintJobDetail(jobdetailid);
            if (null == printJobDetail) {
                writer.write("");
                writer.flush();
                return;
            }
            if (null == printJobDetail.getContent()) {
                writer.write("");
                writer.flush();
                return;
            }
            String content = new String(printJobDetail.getContent(), "UTF-8");
            writer.write(content);
            writer.flush();
            flag = true;
        } catch (Exception ex) {
            logger.error("输出打印内容时失败", ex);
            if (null != writer) {
                writer.write("");
                writer.flush();
            }
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
        if (flag && !"justview".equals(viewtype)) {
            printJobService.updatePrintJobDetailReadflag(jobdetailid);
        }
    }

    /**
     * 获取打印模板中图片地址
     *
     * @param
     * @return void
     * @throws
     * @author zhanghonghui
     * @date Nov 07, 2016
     */
    public void getPrintContentImageFile() throws Exception {
        String image = request.getParameter("image");
        OutputStream writer = null;
        response.setContentType("application/octet-stream");
        try {
            writer = response.getOutputStream();
            PrintJobDetailImage printJobDetailImage = printJobService.getPrintJobDetailImage(image);
            if (null == printJobDetailImage) {
                writer.flush();
                return;
            }
            if (null == printJobDetailImage.getContent() || printJobDetailImage.getContent().length == 0) {
                writer.flush();
                return;
            }
            writer.write(printJobDetailImage.getContent());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            logger.error("输出打印内容时失败", ex);
            if (null != writer) {
                writer.flush();
            }
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }


    //===================================================================//
    //打印任务列表
    //===================================================================//

    /**
     * 显示打印任务列表页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public String showPrintJobListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 打印任务列表数据
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public String showPrintJobPageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());

        pageMap.setCondition(map);
        PageData pageData = printJobService.showPrintJobPageListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }


    //===================================================================//
    //打印任务内容列表
    //===================================================================//

    /**
     * 显示打印任务内容页面列表页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public String showPrintJobDetailListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 打印任务内容页面列表数据
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public String showPrintJobDetailPageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());

        pageMap.setCondition(map);
        PageData pageData = printJobService.showPrintJobDetailPageListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }


    //===================================================================//
    //打印任务调用列表
    //===================================================================//


    /**
     * 显示打印任务调用列表页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public String showPrintJobCallHandleListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 打印任务调用列表数据
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Sep 11, 2016
     */
    public String showPrintJobCallHandlePageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());

        pageMap.setCondition(map);
        PageData pageData = printJobService.showPrintJobCallHandlePageListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }
}
