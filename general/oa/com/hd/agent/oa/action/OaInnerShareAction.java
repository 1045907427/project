/**
 * @(#)OaInnerShareAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-24 limin 创建版本
 */
package com.hd.agent.oa.action;

import java.util.HashMap;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.journalsheet.model.DeptCostsSubject;
import com.hd.agent.journalsheet.service.ICostsFeeService;
import com.hd.agent.oa.model.OaInnerShare;
import com.hd.agent.oa.service.IOaInnerShareService;
import org.apache.commons.lang3.StringUtils;

/**
 * 内部分摊/内部调账Action
 * 
 * @author limin
 */
public class OaInnerShareAction extends BaseOaAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 586079767826976055L;

	private OaInnerShare share;

	private IOaInnerShareService oaInnerShareService;

	public OaInnerShare getShare() {
		return share;
	}

	public void setShare(OaInnerShare share) {
		this.share = share;
	}

	public IOaInnerShareService getOaInnerShareService() {
		return oaInnerShareService;
	}

	public void setOaInnerShareService(IOaInnerShareService oaInnerShareService) {
		this.oaInnerShareService = oaInnerShareService;
	}
	
	/**
	 * 内部分摊申请单页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-24
	 */
	public String oaInnerSharePage() throws Exception {
		
		return SUCCESS;
	}
	
	/**
	 * 内部分摊申请单审批页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-24
	 */
	public String oaInnerShareHandlePage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaInnerShare share = oaInnerShareService.selectOaInnerShare(id);

        com.hd.agent.activiti.model.Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("share", share);
        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 内部分摊申请单审批页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-24
	 */
	public String oaInnerShareHandlePage2() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaInnerShare share = oaInnerShareService.selectOaInnerShare(id);

        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("share", share);
        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 内部分摊申请单查看页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-24
	 */
	public String oaInnerShareViewPage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaInnerShare share = oaInnerShareService.selectOaInnerShare(id);

        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("share", share);
        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 添加内部分摊申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-24
	 */
	public String addOaInnerShare() throws Exception {

		if (isAutoCreate("t_oa_inner_share")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(share, "t_oa_inner_share");
			share.setId(id);
		} else {
			
			share.setId("FT-" + CommonUtils.getDataNumber());
		}

		SysUser user = getSysUser();
		share.setAdduserid(user.getUserid());
		share.setAddusername(user.getName());
		share.setAdddeptid(user.getDepartmentid());
		share.setAdddeptname(user.getDepartmentname());
		
		int ret = oaInnerShareService.insertOaInnerShare(share);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", share.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("内部分摊申请单新增 编号：" + share.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 更新内部分摊申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-24
	 */
	public String editOaInnerShare() throws Exception {
		
		SysUser user = getSysUser();
		share.setModifyuserid(user.getUserid());
		share.setModifyusername(user.getName());
		
		int ret = oaInnerShareService.updateOaInnerShare(share);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", share.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("内部分摊申请单编辑 编号：" + share.getId(), ret > 0);

		return SUCCESS;
	}

    /**
     * 内部分摊申请单打印页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-24
     */
    public String oaInnerSharePrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        DepartMent payDept = null;
        DeptCostsSubject costsort = null;
        DepartMent collectDept = null;

        OaInnerShare share = oaInnerShareService.selectOaInnerShare(id);

        if(share != null && StringUtils.isNotEmpty(share.getPaydeptid())) {
            payDept = getDepartMentService().showDepartMentInfo(share.getPaydeptid());
        }

        if(share != null && StringUtils.isNotEmpty(share.getCostsort())) {
            ICostsFeeService costsFeeService = (ICostsFeeService)SpringContextUtils.getBean("costsFeeServiceImpl");
            costsort = costsFeeService.showDeptCostsSubjectById(share.getCostsort());
        }

        if(share != null && StringUtils.isNotEmpty(share.getCollectdeptid())) {
            collectDept = getDepartMentService().showDepartMentInfo(share.getCollectdeptid());
        }

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("share", share);
        request.setAttribute("payDept", payDept);
        request.setAttribute("costsort", costsort);
        request.setAttribute("collectDept", collectDept);
        request.setAttribute("process", process);
        return SUCCESS;
    }
}

