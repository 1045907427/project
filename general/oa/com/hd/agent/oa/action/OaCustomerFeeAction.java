package com.hd.agent.oa.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.Comment;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.oa.model.OaCustomerFee;
import com.hd.agent.oa.model.OaCustomerFeeDetail;
import com.hd.agent.oa.service.IOaCustomerFeeService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户费用申请单（账扣）Action
 * @author limin
 * @date Mar 23, 2016
 */
public class OaCustomerFeeAction extends BaseOaAction {

    /**
     *
     */
    private IOaCustomerFeeService oaCustomerFeeService;

    public IOaCustomerFeeService getOaCustomerFeeService() {
        return oaCustomerFeeService;
    }

    public void setOaCustomerFeeService(IOaCustomerFeeService oaCustomerFeeService) {
        this.oaCustomerFeeService = oaCustomerFeeService;
    }

    private OaCustomerFee customerfee;

    public OaCustomerFee getCustomerfee() {
        return customerfee;
    }

    public void setCustomerfee(OaCustomerFee customerfee) {
        this.customerfee = customerfee;
    }

    /**
     * 客户费用申请单（账扣）页面
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 23, 2016
     */
    public String oaCustomerFeePage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(id);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Process process = workService.getProcess(processid, "1");
            request.setAttribute("process", process);
            request.setAttribute("fee", fee);

            String type = request.getParameter("type");
            String step = request.getParameter("step");

            String url = "";
            Object[] params = {CommonUtils.nullToEmpty(request.getParameter("type")),
                    CommonUtils.nullToEmpty(request.getParameter("processid")),
                    CommonUtils.nullToEmpty(request.getParameter("taskid")),
                    process != null ? CommonUtils.nullToEmpty(process.getBusinessid()) : "",
                    CommonUtils.nullToEmpty(request.getParameter("step")),
                    CommonUtils.nullToEmpty(request.getParameter("sign"))};
            if("view".equals(type) || "99".equals(step)) {

                url = "oa/customerfee/oaCustomerFeeViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/customerfee/oaCustomerFeeHandlePage1.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else if("handle".equals(type) && "02".equals(step)){

                url = "oa/customerfee/oaCustomerFeeHandlePage2.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else {

                url = "oa/customerfee/oaCustomerFeeHandlePage1.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

        return SUCCESS;
    }

    /**
     * 客户费用申请单（账扣）处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 23, 2016
     */
    public String oaCustomerFeeHandlePage1() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(id);

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        request.setAttribute("customerfee", fee);
        request.setAttribute("customer", fee == null ? null : getCustomerById(fee.getCustomerid()));

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(fee != null && StringUtils.isNotEmpty(fee.getExpensesort())) {

                ExpensesSort expensesort = getBaseFinanceService().getExpensesSortDetail(fee.getExpensesort());
                request.setAttribute("expensesort", expensesort);
            }

            if(fee != null && StringUtils.isNotEmpty(fee.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(fee.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaCustomerFeeService.getCustomerFeeDetailListByBillid(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return sign ? TO_SIGN : TO_PHONE;
        }

        return SUCCESS;
    }

    /**
     * 客户费用申请单（账扣）处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public String oaCustomerFeeHandlePage2() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(id);

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        request.setAttribute("customerfee", fee);
        request.setAttribute("customer", fee == null ? null : getCustomerById(fee.getCustomerid()));

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(fee != null && StringUtils.isNotEmpty(fee.getExpensesort())) {

                ExpensesSort expensesort = getBaseFinanceService().getExpensesSortDetail(fee.getExpensesort());
                request.setAttribute("expensesort", expensesort);
            }

            if(fee != null && StringUtils.isNotEmpty(fee.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(fee.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaCustomerFeeService.getCustomerFeeDetailListByBillid(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return sign ? TO_SIGN : TO_PHONE;
        }

        return SUCCESS;
    }

    /**
     * 客户费用申请单（账扣）查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 30, 2016
     */
    public String oaCustomerFeeViewPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(id);

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        request.setAttribute("customerfee", fee);
        request.setAttribute("customer", fee == null ? null : getCustomerById(fee.getCustomerid()));

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(fee != null && StringUtils.isNotEmpty(fee.getExpensesort())) {

                ExpensesSort expensesort = getBaseFinanceService().getExpensesSortDetail(fee.getExpensesort());
                request.setAttribute("expensesort", expensesort);
            }

            if(fee != null && StringUtils.isNotEmpty(fee.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(fee.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaCustomerFeeService.getCustomerFeeDetailListByBillid(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return TO_PHONE;
        }

        return SUCCESS;
    }

    /**
     * 新增客户费用
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    @UserOperateLog(type = 2, key = "OaCustomerFee")
    public String addOaCustomerFee() throws Exception {

        SysUser user = getSysUser();
        customerfee.setAdduserid(user.getUserid());
        customerfee.setAddusername(user.getName());
        customerfee.setAdddeptid(user.getDepartmentid());
        customerfee.setAdddeptname(user.getDepartmentname());

        String detaillist = request.getParameter("detaillist");
        List<OaCustomerFeeDetail> list = JSONUtils.jsonStrToList(detaillist, new OaCustomerFeeDetail());

        int ret = oaCustomerFeeService.addOaCustomerFee(customerfee, list);

        Map map = new HashMap();
        map.put("flag", ret);
        map.put("backid", customerfee.getId());
        map.put("type", "add");
        addJSONObject(map);
        addLog("客户费用申请单（账扣）新增 编号：" + customerfee.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 编辑客户费用
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    @UserOperateLog(type = 3, key = "OaCustomerFee")
    public String editOaCustomerFee() throws Exception {

        SysUser user = getSysUser();
        customerfee.setModifyuserid(user.getUserid());
        customerfee.setModifyusername(user.getName());

        String detaillist = request.getParameter("detaillist");
        List<OaCustomerFeeDetail> list = JSONUtils.jsonStrToList(detaillist, new OaCustomerFeeDetail());

        int ret = oaCustomerFeeService.editOaCustomerFee(customerfee, list);

        Map map = new HashMap();
        map.put("flag", ret);
        map.put("backid", customerfee.getId());
        map.put("type", "edit");
        addJSONObject(map);
        addLog("客户费用申请单（账扣）修改 编号：" + customerfee.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 编辑客户费用
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public String getCustomerFeeDetailList() throws Exception {

        String billid = request.getParameter("billid");

        List<OaCustomerFeeDetail> list = oaCustomerFeeService.getCustomerFeeDetailListByBillid(billid);

        Map map = new HashMap();
        map.put("total", list.size());
        map.put("rows", list);

        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 客户费用申请单（账扣）打印页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 7, 2016
     */
    public String oaCustomerFeePrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        Customer customer = null;
        Bank bank = null;
        ExpensesSort expensesSort = null;
        List<OaCustomerFeeDetail> list = new ArrayList<OaCustomerFeeDetail>();

        OaCustomerFee fee = oaCustomerFeeService.selectOaCustomerFee(id);
        SysUser user = getSysUser();

        if(fee != null) {

            if(StringUtils.isNotEmpty(fee.getCustomerid())) {
                customer = getCustomerById(fee.getCustomerid());
            }

            if(StringUtils.isNotEmpty(fee.getPaybank())) {
                bank = getBaseFinanceService().getBankDetail(fee.getPaybank());
            }

            if(StringUtils.isNotEmpty(fee.getExpensesort())) {
                expensesSort = getBaseFinanceService().getExpensesSortDetail(fee.getExpensesort());
            }

            list = oaCustomerFeeService.getCustomerFeeDetailListByBillid(id);
        }

        Process process = workService.getProcess(processid, "1");

        List<Comment> commentList = workService.getRealCommentList(process.getId(), process.getInstanceid(), true, true);
        Map<String, String> comments = new HashMap<String, String>();
        for(Comment comment : commentList) {

            comments.put(comment.getTaskkey(), comment.getAssigneename() + "  " + comment.getEndtime().substring(0, 10));
        }

        request.setAttribute("customerfee", fee);
        request.setAttribute("user", user);
        request.setAttribute("customer", customer);
        request.setAttribute("bank", bank);
        request.setAttribute("expensesSort", expensesSort);
        request.setAttribute("list", list);
        request.setAttribute("process", process);
        request.setAttribute("comments", comments);
        return SUCCESS;
    }
}
