/**
 * @(#)OaPrepayAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-04-08 limin 创建版本
 */
package com.hd.agent.oa.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.oa.model.OaPersonalLoan;
import com.hd.agent.oa.service.IOaPersonalLoanService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预付款Action
 * 
 * @author limin
 */
public class OaPrepayAction extends BaseOaAction {

	/**  */
	private static final long serialVersionUID = -5454270360114194355L;

	private OaPersonalLoan prepay;

	private IOaPersonalLoanService oaPersonalLoanService;

	public OaPersonalLoan getPrepay() {
		return prepay;
	}

	public void setPrepay(OaPersonalLoan prepay) {
		this.prepay = prepay;
	}

	public IOaPersonalLoanService getOaPersonalLoanService() {
		return oaPersonalLoanService;
	}

	public void setOaPersonalLoanService(
			IOaPersonalLoanService oaPersonalLoanService) {
		this.oaPersonalLoanService = oaPersonalLoanService;
	}

	/**
	 * 个人借款/预付款页面
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-11-22
	 */
	public String oaPrepayPage() throws Exception {

		return SUCCESS;
	}

	/**
	 * 个人借款/预付款审批页面
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-11-22
	 */
	public String oaPrepayHandlePage() throws Exception {

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		List<SysCode> loantype = getBaseSysCodeService().showSysCodeListByType("loantype");
		OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(id);
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("prepay", loan);
		request.setAttribute("loantype", loantype);
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 个人借款/预付款审批页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-22
	 */
	public String oaPrepayHandlePage2() throws Exception {

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		List<SysCode> loantype = getBaseSysCodeService().showSysCodeListByType("loantype");
		OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(id);
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("prepay", loan);
		request.setAttribute("loantype", loantype);
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 个人借款/预付款查看页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-22
	 */
	public String oaPrepayViewPage() throws Exception {

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		List<SysCode> loantype = getBaseSysCodeService().showSysCodeListByType("loantype");
		OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(id);
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("prepay", loan);
		request.setAttribute("loantype", loantype);
        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 个人借款/预付款新增
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-22
	 */
	@UserOperateLog(key = "OaPrepay", type = 2)
	public String addOaPrepay() throws Exception {

		if (isAutoCreate("t_oa_personal_loan")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(prepay, "t_oa_personal_loan");
			prepay.setId(id);
		} else {
			
			prepay.setId("YF-" + CommonUtils.getDataNumber());
		}

		SysUser user = getSysUser();
		prepay.setAdduserid(user.getUserid());
		prepay.setAddusername(user.getName());
		prepay.setAdddeptid(user.getDepartmentid());
		prepay.setAdddeptname(user.getDepartmentname());
		
		int ret = oaPersonalLoanService.insertOaPersonalLoan(prepay);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", prepay.getId());
		map.put("type", "add");
		addJSONObject(map);
        addLog("预付款申请单新增 编号：" + prepay.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 个人借款/预付款修改
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-22
	 */
	@UserOperateLog(key = "OaPrepay", type = 3)
	public String editOaPrepay() throws Exception {
		
		SysUser user = getSysUser();
		prepay.setModifyuserid(user.getUserid());
		prepay.setModifyusername(user.getName());
		
		int ret = oaPersonalLoanService.editOaPersonalLoan(prepay);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", prepay.getId());
		map.put("type", "add");
		addJSONObject(map);
        addLog("预付款申请单编辑 编号：" + prepay.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-22
	 */
	public String selectOaPersonalLoan() throws Exception {
		
		String id = request.getParameter("id");
		
		OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(id);
		
		request.setAttribute("prepay", loan);
		return SUCCESS;
	}

    /**
     *
     * @return
     * @throws Exception
     */
    public String oaPrepayPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        DepartMent payDept = null;
        Personnel payPersonnel = null;
        Bank bank = null;
        Personnel collectPersonnel = null;

        List<SysCode> loantype = getBaseSysCodeService().showSysCodeListByType("loantype");
        OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(id);

        if(loan != null && StringUtils.isNotEmpty(loan.getPaydeptid())) {

            payDept = getDepartMentService().showDepartMentInfo(loan.getPaydeptid());
        }

        if(loan != null && StringUtils.isNotEmpty(loan.getPayuserid())) {

            payPersonnel = getBasePersonnelService().showPersonnelInfo(loan.getPayuserid());
        }

        if(loan != null && StringUtils.isNotEmpty(loan.getPaybank())) {

            bank = getBaseFinanceService().getBankDetail(loan.getPaybank());
        }

        if(loan != null && StringUtils.isNotEmpty(loan.getCollectuserid())) {

            collectPersonnel = getBasePersonnelService().showPersonnelInfo(loan.getCollectuserid());
        }

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("prepay", loan);
        request.setAttribute("loantype", loantype);
        request.setAttribute("payDept", payDept);
        request.setAttribute("payPersonnel", payPersonnel);
        request.setAttribute("bank", bank);
        request.setAttribute("collectPersonnel", collectPersonnel);
        request.setAttribute("process", process);
        return SUCCESS;
    }
}

