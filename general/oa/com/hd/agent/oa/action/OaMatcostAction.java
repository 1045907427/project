/**
 * @(#)OaMatcostAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2018-2-12 limin 创建版本
 */
package com.hd.agent.oa.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.ExpensesSort;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.oa.model.OaMatcost;
import com.hd.agent.oa.model.OaMatcostDetail;
import com.hd.agent.oa.service.IOaMatcostService;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代垫费用申请单Action
 *
 * @author limin
 * @date Feb 12, 2018
 */
public class OaMatcostAction extends BaseOaAction {

    private IOaMatcostService oaMatcostService;

    public IOaMatcostService getOaMatcostService() {
        return oaMatcostService;
    }

    public void setOaMatcostService(IOaMatcostService oaMatcostService) {
        this.oaMatcostService = oaMatcostService;
    }

    private OaMatcost matcost;

    public OaMatcost getMatcost() {
        return matcost;
    }

    public void setMatcost(OaMatcost matcost) {
        this.matcost = matcost;
    }

    /**
     * 代垫费用申请单页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 12, 2018
     */
    public String oaMatcostPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaMatcost matcost = oaMatcostService.getOaMatcost(id);
        List<OaMatcostDetail> detailList = oaMatcostService.getOaMatcostDetailListByBillid(id);
        request.setAttribute("detailListStr", JSONUtils.listToJsonStr(detailList));

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Process process = workService.getProcess(processid, "1");
            request.setAttribute("process", process);
            request.setAttribute("matcost", matcost);

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

                url = "oa/matcost/oaMatcostViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/matcost/oaMatcostHandlePage1.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else if("handle".equals(type) && "02".equals(step)){

                url = "oa/matcost/oaMatcostHandlePage2.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else {

                url = "oa/matcost/oaMatcostHandlePage1.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

        return SUCCESS;
    }

    /**
     * 代垫费用申请单处理页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 12, 2018
     */
    public String oaMatcostHandlePage1() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaMatcost matcost = oaMatcostService.getOaMatcost(id);

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        request.setAttribute("matcost", matcost);
        request.setAttribute("supplier", matcost == null ? null : getBaseBuySupplierById(matcost.getSupplierid()));

        List reimburseTypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimburseTypeList", reimburseTypeList);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(matcost != null && StringUtils.isNotEmpty(matcost.getDeptid())) {
                DepartMent dept = getDepartMentService().showDepartMentInfo(matcost.getDeptid());
                request.setAttribute("dept", dept);
            }

            if(matcost != null && StringUtils.isNotEmpty(matcost.getPaybank())) {
                Bank bank = getBaseFinanceService().getBankDetail(matcost.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaMatcostService.getOaMatcostDetailListByBillid(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return sign ? TO_SIGN : TO_PHONE;
        }

        return SUCCESS;
    }

    /**
     * 代垫费用申请单处理页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 12, 2018
     */
    public String oaMatcostHandlePage2() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaMatcost matcost = oaMatcostService.getOaMatcost(id);

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        request.setAttribute("matcost", matcost);
        request.setAttribute("supplier", matcost == null ? null : getBaseBuySupplierById(matcost.getSupplierid()));

        List reimburseTypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimburseTypeList", reimburseTypeList);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(matcost != null && StringUtils.isNotEmpty(matcost.getDeptid())) {
                DepartMent dept = getDepartMentService().showDepartMentInfo(matcost.getDeptid());
                request.setAttribute("dept", dept);
            }

            if(matcost != null && StringUtils.isNotEmpty(matcost.getPaybank())) {
                Bank bank = getBaseFinanceService().getBankDetail(matcost.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaMatcostService.getOaMatcostDetailListByBillid(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return sign ? TO_SIGN : TO_PHONE;
        }

        return SUCCESS;
    }

    /**
     * 代垫费用申请单处理页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Feb 12, 2018
     */
    public String oaMatcostViewPage() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaMatcost matcost = oaMatcostService.getOaMatcost(id);

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);

        request.setAttribute("matcost", matcost);
        request.setAttribute("supplier", matcost == null ? null : getBaseBuySupplierById(matcost.getSupplierid()));

        List reimburseTypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimburseTypeList", reimburseTypeList);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(matcost != null && StringUtils.isNotEmpty(matcost.getDeptid())) {
                DepartMent dept = getDepartMentService().showDepartMentInfo(matcost.getDeptid());
                request.setAttribute("dept", dept);
            }

            if(matcost != null && StringUtils.isNotEmpty(matcost.getPaybank())) {
                Bank bank = getBaseFinanceService().getBankDetail(matcost.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaMatcostService.getOaMatcostDetailListByBillid(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return sign ? TO_SIGN : TO_PHONE;
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
    public String addOaMatcost() throws Exception {

        String detaillist = request.getParameter("detaillist");
        List<OaMatcostDetail> list = JSONUtils.jsonStrToList(detaillist, new OaMatcostDetail());

        int ret = oaMatcostService.addOaMatcost(matcost, list);

        Map map = new HashMap();
        map.put("flag", ret);
        map.put("backid", matcost.getId());
        map.put("type", "add");
        addJSONObject(map);

        return SUCCESS;
    }

    /**
     * 编辑客户费用
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    public String editOaMatcost() throws Exception {

        String detaillist = request.getParameter("detaillist");
        List<OaMatcostDetail> list = JSONUtils.jsonStrToList(detaillist, new OaMatcostDetail());

        int ret = oaMatcostService.editOaMatcost(matcost, list);

        Map map = new HashMap();
        map.put("flag", ret);
        map.put("backid", matcost.getId());
        map.put("type", "edit");
        addJSONObject(map);

        return SUCCESS;
    }
}
