/**
 * @(#)OaDailPayAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-27 limin 创建版本
 */
package com.hd.agent.oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.Bank;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.journalsheet.model.DeptCostsSubject;
import com.hd.agent.journalsheet.service.ICostsFeeService;
import com.hd.agent.oa.model.OaDailyPay;
import com.hd.agent.oa.model.OaDailyPayDetail;
import com.hd.agent.oa.service.IOaDailPayService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 日常费用支付申请单Action
 * 
 * @author limin
 */
public class OaDailPayAction extends BaseOaAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7048835220027322786L;

	private OaDailyPay pay;

	private IOaDailPayService oaDailPayService;

	public OaDailyPay getPay() {
		return pay;
	}

	public void setPay(OaDailyPay pay) {
		this.pay = pay;
	}

	public IOaDailPayService getOaDailPayService() {
		return oaDailPayService;
	}

	public void setOaDailPayService(IOaDailPayService oaDailPayService) {
		this.oaDailPayService = oaDailPayService;
	}

	/**
	 * 日常费用支付申请单页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-27
	 */
	public String oaDailPayPage() throws Exception {
		
		return SUCCESS;
	}

	/**
	 * 日常费用支付申请单审批页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-27
	 */
	public String oaDailPayHandlePage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaDailyPay pay = oaDailPayService.selectOaDailPay(id);
		
		List<SysCode> bxtype = getBaseSysCodeService().showSysCodeListByType("bxtype");

        com.hd.agent.activiti.model.Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("bxtype", bxtype);
		request.setAttribute("pay", pay);
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 日常费用支付申请单审批页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-27
	 */
	public String oaDailPayHandlePage2() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaDailyPay pay = oaDailPayService.selectOaDailPay(id);
		
		List<SysCode> bxtype = getBaseSysCodeService().showSysCodeListByType("bxtype");

        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("bxtype", bxtype);
		request.setAttribute("pay", pay);
        request.setAttribute("process", process);
		return SUCCESS;
	}

    /**
     * 日常费用支付申请单审批页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-27
     */
    public String oaDailPayHandlePage3() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaDailyPay pay = oaDailPayService.selectOaDailPay(id);

        List<SysCode> bxtype = getBaseSysCodeService().showSysCodeListByType("bxtype");

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("bxtype", bxtype);
        request.setAttribute("pay", pay);
        request.setAttribute("process", process);
        return SUCCESS;
    }

    /**
     * 日常费用支付申请单审批页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-27
     */
    public String oaDailPayHandlePage4() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaDailyPay pay = oaDailPayService.selectOaDailPay(id);

        List<SysCode> bxtype = getBaseSysCodeService().showSysCodeListByType("bxtype");

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("bxtype", bxtype);
        request.setAttribute("pay", pay);
        request.setAttribute("process", process);
        return SUCCESS;
    }

	/**
	 * 日常费用支付申请单查看页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-28
	 */
	public String oaDailPayViewPage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaDailyPay pay = oaDailPayService.selectOaDailPay(id);

		List<SysCode> bxtype = getBaseSysCodeService().showSysCodeListByType("bxtype");

        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("bxtype", bxtype);
		request.setAttribute("pay", pay);
        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 添加日常费用支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-28
	 */
	@UserOperateLog(key="OaDailPay", type=2)
	public String addOaDailPay() throws Exception {
		
		String paydetail = request.getParameter("paydetail");

		if (isAutoCreate("t_oa_dailpay")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(pay, "t_oa_dailpay");
			pay.setId(id);
		} else {
			
			pay.setId("RC-" + CommonUtils.getDataNumber());
		}

		SysUser user = getSysUser();
		pay.setAdduserid(user.getUserid());
		pay.setAddusername(user.getName());
		pay.setAdddeptid(user.getDepartmentid());
		pay.setAdddeptname(user.getDepartmentname());
		
		List detailList = JSONUtils.jsonStrToList(paydetail, new OaDailyPayDetail());

		int ret = oaDailPayService.insertOaDailPay(pay, detailList);
		
		Map map = new HashMap();
		map.put("flag", ret > 0);
		map.put("backid", pay.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("日常费用支付申请单新增 编号：" + pay.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 添加日常费用支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-28
	 */
	@UserOperateLog(key="OaDailPay", type=3)
	public String editOaDailPay() throws Exception {
		
		String paydetail = request.getParameter("paydetail");

		SysUser user = getSysUser();
		pay.setModifyuserid(user.getUserid());
		pay.setModifyusername(user.getName());
		
		List detailList = JSONUtils.jsonStrToList(paydetail, new OaDailyPayDetail());

		int ret = oaDailPayService.updateOaDailPay(pay, detailList);
		
		Map map = new HashMap();
		map.put("flag", ret > 0);
		map.put("backid", pay.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("日常费用支付申请单编辑 编号：" + pay.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 查询日常费用支付单明细List
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-28
	 */
	public String selectOaDailPayDetailList() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setRows(9999);
		PageData pageData = oaDailPayService.selectOaDailPayDetailList(pageMap);
		
		addJSONObject(pageData);
		return SUCCESS;
	}

    /**
     * 日常费用支付申请单打印页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-28
     */
    public String oaDailPayPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        Bank bank = null;
        DeptCostsSubject costsort = null;
        DepartMent dept = null;

        OaDailyPay pay = oaDailPayService.selectOaDailPay(id);

        if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {
            bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
        }

        if(pay != null && StringUtils.isNotEmpty(pay.getCostsort())) {
            ICostsFeeService costsFeeService = (ICostsFeeService) SpringContextUtils.getBean("costsFeeServiceImpl");
            costsort = costsFeeService.showDeptCostsSubjectById(pay.getCostsort());
        }

        if(pay != null && StringUtils.isNotEmpty(pay.getApplydeptid())) {
            dept = getBaseDepartMentService().showDepartMentInfo(pay.getApplydeptid());
        }

        List<OaDailyPayDetail> list = oaDailPayService.selectOaDailPayDetailListByBillid(id);
        for(OaDailyPayDetail detail : list) {
            if(StringUtils.isNotEmpty(detail.getApplydetpid())) {
                detail.setApplydetpid(getDepartMentService().showDepartMentInfo(detail.getApplydetpid()).getName());
            }
        }

        List<SysCode> bxtype = getBaseSysCodeService().showSysCodeListByType("bxtype");

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("bxtype", bxtype);
        request.setAttribute("pay", pay);
        request.setAttribute("bank", bank);
        request.setAttribute("costsort", costsort);
        request.setAttribute("dept", dept);
        request.setAttribute("list", list);
        request.setAttribute("process", process);
        return SUCCESS;
    }

}

