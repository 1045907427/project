/**
 * @(#)OaBrandFeeAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-8 limin 创建版本
 */
package com.hd.agent.oa.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.Comment;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.oa.model.OaBrandFee;
import com.hd.agent.oa.model.OaBrandFeeDetail;
import com.hd.agent.oa.service.IOaBrandFeeService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌费用申请单（支付）Action
 * 
 * @author limin
 */
public class OaBrandFeeAction extends BaseOaAction {

    private IOaBrandFeeService oaBrandFeeService;

    private OaBrandFee brandfee;

    public IOaBrandFeeService getOaBrandFeeService() {
        return oaBrandFeeService;
    }

    public void setOaBrandFeeService(IOaBrandFeeService oaBrandFeeService) {
        this.oaBrandFeeService = oaBrandFeeService;
    }

    public OaBrandFee getBrandfee() {
        return brandfee;
    }

    public void setBrandfee(OaBrandFee brandfee) {
        this.brandfee = brandfee;
    }

    /**
     * 品牌费用申请单（支付）页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public String oaBrandFeePage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaBrandFee fee = oaBrandFeeService.selectOaBrandFee(id);

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

                url = "oa/brandfee/oaBrandFeeViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/brandfee/oaBrandFeeHandlePage1.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else if("handle".equals(type) && "02".equals(step)){

                url = "oa/brandfee/oaBrandFeeHandlePage2.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";

            } else {

                url = "oa/brandfee/oaBrandFeeHandlePage1.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&step=%s&sign=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

        return SUCCESS;
    }

    /**
     * 品牌费用申请单（支付）处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    private String oaBrandFeePageLogic() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaBrandFee fee = oaBrandFeeService.selectOaBrandFee(id);

        Process process = workService.getProcess(processid, "1");
        request.setAttribute("process", process);
        request.setAttribute("brandfee", fee);

        if(fee != null && StringUtils.isNotEmpty(fee.getSupplierid())) {

            BuySupplier supplier = getBaseBuySupplierById(fee.getSupplierid());
            request.setAttribute("supplier", supplier);
        }

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(fee != null && StringUtils.isNotEmpty(fee.getDeptid())) {

                DepartMent dept = getDepartMentService().showDepartMentInfo(fee.getDeptid());
                request.setAttribute("dept", dept);
            }

            if(fee != null && StringUtils.isNotEmpty(fee.getBrandid())) {

                Brand brand = getBaseGoodsService().getBrandInfo(fee.getBrandid());
                request.setAttribute("brand", brand);
            }

            if(fee != null && StringUtils.isNotEmpty(fee.getExpensesort())) {

                ExpensesSort expensesort = getBaseFinanceService().getExpensesSortDetail(fee.getExpensesort());
                request.setAttribute("expensesort", expensesort);
            }

            if(fee != null && StringUtils.isNotEmpty(fee.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(fee.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaBrandFeeService.selectBrandFeeDetailByBillid(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            return sign ? TO_SIGN : TO_PHONE;
        }

        return SUCCESS;

    }

    /**
     * 品牌费用申请单（支付）处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public String oaBrandFeeHandlePage1() throws Exception {
        return oaBrandFeePageLogic();
    }

    /**
     * 品牌费用申请单（支付）处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public String oaBrandFeeHandlePage2() throws Exception {
        return oaBrandFeePageLogic();
    }

    /**
     * 品牌费用申请单（支付）查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public String oaBrandFeeViewPage() throws Exception {
        return oaBrandFeePageLogic();
    }

    /**
     * 品牌费用申请单（支付）明细取得
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 11, 2016
     */
    public String getBrandFeeDetailList() throws Exception {

        String billid = request.getParameter("billid");

        List<OaBrandFeeDetail> list = oaBrandFeeService.selectBrandFeeDetailByBillid(billid);
        Map data = new HashMap();
        data.put("total", list.size());
        data.put("rows", list);
        addJSONObject(data);
        return SUCCESS;
    }

    /**
     * 新增品牌费用申请单
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    @UserOperateLog(type = 2, key = "OaBrandFee")
    public String addBrandFee() throws Exception {

        String detaillist = request.getParameter("detaillist");
        List<OaBrandFeeDetail> list = JSONUtils.jsonStrToList(detaillist, new OaBrandFeeDetail());

        int ret = oaBrandFeeService.addBrandFee(brandfee, list);

        Map map = new HashMap();
        map.put("flag", ret);
        map.put("backid", brandfee.getId());
        map.put("type", "add");
        addJSONObject(map);
        addLog("品牌费用申请单（支付）新增 编号：" + brandfee.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 编辑品牌费用申请单
     * @return
     * @throws Exception
     * @author limin
     * @date Mar 29, 2016
     */
    @UserOperateLog(type = 3, key = "OaBrandFee")
    public String editBrandFee() throws Exception {

        String detaillist = request.getParameter("detaillist");
        List<OaBrandFeeDetail> list = JSONUtils.jsonStrToList(detaillist, new OaBrandFeeDetail());

        int ret = oaBrandFeeService.editBrandFee(brandfee, list);

        Map map = new HashMap();
        map.put("flag", ret);
        map.put("backid", brandfee.getId());
        map.put("type", "edit");
        addJSONObject(map);
        addLog("品牌费用申请单（支付）修改 编号：" + brandfee.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 品牌费用申请单（支付）打印页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 12, 2016
     */
    public String oaBrandFeePrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        BuySupplier supplier = null;
        Bank bank = null;
        ExpensesSort expensesSort = null;
        DepartMent dept = null;
        Brand brand = null;
        List<OaBrandFeeDetail> list = new ArrayList<OaBrandFeeDetail>();

        OaBrandFee fee = oaBrandFeeService.selectOaBrandFee(id);
        SysUser user = getSysUser();

        if(fee != null) {

            if(StringUtils.isNotEmpty(fee.getSupplierid())) {
                supplier = getBaseBuySupplierById(fee.getSupplierid());
            }

            if(StringUtils.isNotEmpty(fee.getDeptid())) {
                dept = getDepartMentService().showDepartMentInfo(fee.getDeptid());
            }

            if(StringUtils.isNotEmpty(fee.getBrandid())) {
                brand = getBaseGoodsService().getBrandInfo(fee.getBrandid());
            }

            if(StringUtils.isNotEmpty(fee.getPaybank())) {
                bank = getBaseFinanceService().getBankDetail(fee.getPaybank());
            }

            if(StringUtils.isNotEmpty(fee.getExpensesort())) {
                expensesSort = getBaseFinanceService().getExpensesSortDetail(fee.getExpensesort());
            }

            list = oaBrandFeeService.selectBrandFeeDetailByBillid(id);
        }

        Process process = workService.getProcess(processid, "1");

        List<Comment> commentList = workService.getRealCommentList(process.getId(), process.getInstanceid(), true, true);
        Map<String, String> comments = new HashMap<String, String>();
        for(Comment comment : commentList) {

            comments.put(comment.getTaskkey(), comment.getAssigneename() + "  " + comment.getEndtime().substring(0, 10));
        }

        request.setAttribute("brandfee", fee);
        request.setAttribute("user", user);
        request.setAttribute("supplier", supplier);
        request.setAttribute("dept", dept);
        request.setAttribute("brand", brand);
        request.setAttribute("bank", bank);
        request.setAttribute("expensesSort", expensesSort);
        request.setAttribute("list", list);
        request.setAttribute("process", process);
        request.setAttribute("comments", comments);
        return SUCCESS;
    }
}

