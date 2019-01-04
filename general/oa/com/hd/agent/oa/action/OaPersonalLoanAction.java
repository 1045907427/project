/**
 * @(#)OaPersonelLoanAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-22 limin 创建版本
 */
package com.hd.agent.oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * 个人借款/预付款Action
 * 
 * @author limin
 */
public class OaPersonalLoanAction extends BaseOaAction {

	/**  */
	private static final long serialVersionUID = -5454270209118194355L;

	private OaPersonalLoan loan;

	private IOaPersonalLoanService oaPersonalLoanService;

	public OaPersonalLoan getLoan() {
		return loan;
	}

	public void setLoan(OaPersonalLoan loan) {
		this.loan = loan;
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
	public String oaPersonalLoanPage() throws Exception {
		
		return SUCCESS;
	}

	/**
	 * 个人借款/预付款审批页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-22
	 */
	public String oaPersonalLoanHandlePage() throws Exception {

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		List<SysCode> loantype = getBaseSysCodeService().showSysCodeListByType("loantype");
		OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(id);
        com.hd.agent.activiti.model.Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("loan", loan);		
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
	public String oaPersonalLoanHandlePage2() throws Exception {

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		List<SysCode> loantype = getBaseSysCodeService().showSysCodeListByType("loantype");
		OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(id);
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("loan", loan);		
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
	public String oaPersonalLoanViewPage() throws Exception {

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		List<SysCode> loantype = getBaseSysCodeService().showSysCodeListByType("loantype");
		OaPersonalLoan loan = oaPersonalLoanService.selectOaPersonalLoanInfo(id);
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("loan", loan);		
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
	@UserOperateLog(key = "OaPersonalLoan", type = 2)
	public String addOaPersonalLoan() throws Exception {

		if (isAutoCreate("t_oa_personal_loan")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(loan, "t_oa_personal_loan");
			loan.setId(id);
		} else {
			
			loan.setId("JK-" + CommonUtils.getDataNumber());
		}

		SysUser user = getSysUser();
		loan.setAdduserid(user.getUserid());
		loan.setAddusername(user.getName());
		loan.setAdddeptid(user.getDepartmentid());
		loan.setAdddeptname(user.getDepartmentname());
		
		int ret = oaPersonalLoanService.insertOaPersonalLoan(loan);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", loan.getId());
		map.put("type", "add");
		addJSONObject(map);
        addLog("个人借款申请单新增 编号：" + loan.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 个人借款/预付款修改
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-22
	 */
	@UserOperateLog(key = "OaPersonalLoan", type = 3)
	public String editOaPersonalLoan() throws Exception {
		
		SysUser user = getSysUser();
		loan.setModifyuserid(user.getUserid());
		loan.setModifyusername(user.getName());
		
		int ret = oaPersonalLoanService.editOaPersonalLoan(loan);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", loan.getId());
		map.put("type", "add");
		addJSONObject(map);
        addLog("个人借款申请单编辑 编号：" + loan.getId(), ret > 0);

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
		
		request.setAttribute("loan", loan);
		return SUCCESS;
	}

    /**
     *
     * @return
     * @throws Exception
     */
    public String oaPersonalLoanPrintPage() throws Exception {

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

        request.setAttribute("loan", loan);
        request.setAttribute("loantype", loantype);
        request.setAttribute("payDept", payDept);
        request.setAttribute("payPersonnel", payPersonnel);
        request.setAttribute("bank", bank);
        request.setAttribute("collectPersonnel", collectPersonnel);
        request.setAttribute("process", process);
        return SUCCESS;
    }
}

