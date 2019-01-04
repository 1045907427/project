/**
 * @(#)OaPurchasePayAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-9-14 limin 创建版本
 */
package com.hd.agent.oa.action;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.oa.model.OaPurchasePay;
import com.hd.agent.oa.service.IOaPurchasePayService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 行政采购付款申请单Action
 *
 * Created by limin on 2016/9/14.
 */
public class OaPurchasePayAction extends BaseOaAction {

    private IOaPurchasePayService oaPurchasePayService;

    private OaPurchasePay pay;

    public IOaPurchasePayService getOaPurchasePayService() {
        return oaPurchasePayService;
    }

    public void setOaPurchasePayService(IOaPurchasePayService oaPurchasePayService) {
        this.oaPurchasePayService = oaPurchasePayService;
    }

    public OaPurchasePay getPay() {
        return pay;
    }

    public void setPay(OaPurchasePay pay) {
        this.pay = pay;
    }

    /**
     * 行政采购付款申请单页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public String oaPurchasePayPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Process process = workService.getProcess(processid, "1");
            request.setAttribute("process", process);
            request.setAttribute("pay", pay);

            String type = request.getParameter("type");
            String step = request.getParameter("step");

            String url = "";
            Object[] params = {CommonUtils.nullToEmpty(request.getParameter("type")),
                    CommonUtils.nullToEmpty(request.getParameter("processid")),
                    CommonUtils.nullToEmpty(request.getParameter("taskid")),
                    process != null ? CommonUtils.nullToEmpty(process.getBusinessid()) : "",
                    CommonUtils.nullToEmpty(request.getParameter("sign"))};
            if("view".equals(type) || "99".equals(step)) {

                url = "oa/purchasepay/oaPurchasePayViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/purchasepay/oaPurchasePayHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else if("handle".equals(type) && "02".equals(step)){

                url = "oa/purchasepay/oaPurchasePayHandlePage2.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else {

                url = "oa/purchasepay/oaPurchasePayHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";
            }


            response.sendRedirect(String.format(url, params));
            return null;
        }

        return SUCCESS;
    }

    /**
     * 行政采购付款申请单处理页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public String oaPurchasePayHandlePage() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        Bank bank = null;

        OaPurchasePay pay = oaPurchasePayService.selectPurchasePay(id);
        if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

            bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
        }

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            request.setAttribute("pay", pay);
            request.setAttribute("bank", bank);
            request.setAttribute("process", process);
            return sign ? TO_SIGN : TO_PHONE;
        }

        request.setAttribute("pay", pay);
        request.setAttribute("bank", bank);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 行政采购付款申请单处理页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public String oaPurchasePayHandlePage2() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        Bank bank = null;

        OaPurchasePay pay = oaPurchasePayService.selectPurchasePay(id);
        if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

            bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
        }

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            request.setAttribute("pay", pay);
            request.setAttribute("bank", bank);
            request.setAttribute("process", process);
            return sign ? TO_SIGN : TO_PHONE;
        }

        request.setAttribute("pay", pay);
        request.setAttribute("bank", bank);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 行政采购付款申请单查看页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public String oaPurchasePayViewPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        Bank bank = null;

        OaPurchasePay pay = oaPurchasePayService.selectPurchasePay(id);
        if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

            bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
        }

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            request.setAttribute("pay", pay);
            request.setAttribute("bank", bank);
            request.setAttribute("process", process);
            return TO_PHONE;
        }

        request.setAttribute("pay", pay);
        request.setAttribute("bank", bank);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 行政采购付款申请单新增
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public String addOaPurchasePay() throws Exception {

        int ret = oaPurchasePayService.addOaPurchasePay(pay);

        Map result = new HashedMap();
        result.put("flag", ret > 0);
        result.put("backid", pay.getId());
        addJSONObject(result);
        return SUCCESS;
    }

    /**
     * 行政采购付款申请单修改
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 18, 2016
     */
    public String editOaPurchasePay() throws Exception {

        int ret = oaPurchasePayService.editOaPurchasePay(pay);

        Map result = new HashedMap();
        result.put("flag", ret > 0);
        result.put("backid", pay.getId());
        addJSONObject(result);
        return SUCCESS;
    }

    /**
     * 行政采购付款申请单打印页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-06
     */
    public String oaPurchasePayPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        Bank bank = null;

        OaPurchasePay pay = oaPurchasePayService.selectPurchasePay(id);
        if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

            bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
        }

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("pay", pay);
        request.setAttribute("bank", bank);
        request.setAttribute("process", process);
        return SUCCESS;
    }
}
