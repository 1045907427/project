/**
 * @(#)OaExpensePushAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-7 limin 创建版本
 */
package com.hd.agent.oa.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.annotation.UserOperateLog;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.oa.model.OaExpensePush;
import com.hd.agent.oa.model.OaExpensePushDetail;
import com.hd.agent.oa.service.IOaExpensePushService;

/**
 * 费用冲差支付申请单Action
 * 
 * @author limin
 */
public class OaExpensePushAction extends BaseOaAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1904399934535016973L;

	private OaExpensePush push;
	
	private IOaExpensePushService oaExpensePushService;

	public IOaExpensePushService getOaExpensePushService() {
		return oaExpensePushService;
	}

	public void setOaExpensePushService(IOaExpensePushService oaExpensePushService) {
		this.oaExpensePushService = oaExpensePushService;
	}
	
	public OaExpensePush getPush() {
		return push;
	}

	public void setPush(OaExpensePush push) {
		this.push = push;
	}

	/**
	 * 费用冲差支付申请单页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-7
	 */
	public String oaExpensePushPage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		if(StringUtils.isNotEmpty(id)) {
			
			OaExpensePush push = oaExpensePushService.selectOaExpensePush(id);

            if(null != push && StringUtils.isNotEmpty(push.getCustomerid())){
                String customerid = push.getCustomerid();
                Customer customer = getCustomerById(customerid);
                request.setAttribute("customer", customer);
            }

			request.setAttribute("push", push);

		}
        if(TO_PHONE.equals(request.getParameter("to"))) {

            List typelist = getBaseSysCodeService().showSysCodeListByType("paypushtype");

            Process process = workService.getProcess(processid, "1");

            request.setAttribute("process",process);
            request.setAttribute("typelist",typelist);

            String type = request.getParameter("type");
            String step = request.getParameter("step");

            String url = "";
            if("view".equals(type) || "99".equals(step)) {

				url = "oa/expensepush/oaExpensePushViewPage.do?";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/expensepush/oaExpensePushHandlePage.do?";

            } else {

                url = "oa/expensepush/oaExpensePushHandlePage.do?";
            }

			response.sendRedirect(url + changeMapToUrl(CommonUtils.changeMap(request.getParameterMap())));
            return null;
        }

		return SUCCESS;
	}
	
	/**
	 * 费用冲差支付申请单页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-7
	 */
	public String oaExpensePushHandlePage() throws Exception {
	
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		if(StringUtils.isNotEmpty(id)) {
			
			OaExpensePush push = oaExpensePushService.selectOaExpensePush(id);
			request.setAttribute("push", push);
		}

        Process process = workService.getProcess(processid, "1");
		boolean sign = "1".equals(request.getParameter("sign"));

        if(TO_PHONE.equals(request.getParameter("to"))) {

            OaExpensePush push = oaExpensePushService.selectOaExpensePush(id);

            Customer customer = null;
            if(null != push && StringUtils.isNotEmpty(push.getCustomerid())){
                customer = getCustomerById(push.getCustomerid());
            }

            List typelist = getBaseSysCodeService().showSysCodeListByType("paypushtype");

            List<OaExpensePushDetail> pushDetail = oaExpensePushService.selectOaExpensePushDetailList(id);

            request.setAttribute("push", push);
            request.setAttribute("customer",customer);
            request.setAttribute("process",process);
            request.setAttribute("typelist",typelist);
            request.setAttribute("pushJSON",JSONUtils.listToJsonStr(pushDetail));

            return sign ? TO_SIGN : TO_PHONE;
        }

        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 费用冲差支付申请单页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-8
	 */
	public String oaExpensePushViewPage() throws Exception {
	
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		if(StringUtils.isNotEmpty(id)) {
			
			OaExpensePush push = oaExpensePushService.selectOaExpensePush(id);
			request.setAttribute("push", push);
		}

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            OaExpensePush push = oaExpensePushService.selectOaExpensePush(id);

            Customer customer = null;
            if(null != push && StringUtils.isNotEmpty(push.getCustomerid())){
                customer = getCustomerById(push.getCustomerid());
            }

            List typelist = getBaseSysCodeService().showSysCodeListByType("paypushtype");


            List<OaExpensePushDetail> pushDetail = oaExpensePushService.selectOaExpensePushDetailList(id);

            request.setAttribute("push", push);
            request.setAttribute("customer",customer);
            request.setAttribute("process",process);
            request.setAttribute("typelist",typelist);
            request.setAttribute("pushJSON",JSONUtils.listToJsonStr(pushDetail));

            return TO_PHONE;
        }

        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 查询费用冲差明细List
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-7
	 */
	public String selectOaExpensePushDetailList() throws Exception {
		
		String billid = request.getParameter("billid");
		
		if(StringUtils.isEmpty(billid)) {
			
			// addJSONObject("list", new ArrayList());
			addJSONArray(new ArrayList());
			return SUCCESS;
		}
		
		List<OaExpensePushDetail> list = oaExpensePushService.selectOaExpensePushDetailList(billid);
		
		// addJSONObject("list", list);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 添加费用冲差支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-8
	 */
	@UserOperateLog(key = "OaExpensePush", type = 2)
	public String addOaExpensePush() throws Exception {
		
		String detaillist = request.getParameter("detaillist");

		if (isAutoCreate("t_oa_expense_push")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(push, "t_oa_expense_push");
			push.setId(id);
		} else {
			
			push.setId("CC-" + CommonUtils.getDataNumberSendsWithRand());
		}

		SysUser user = getSysUser();
		push.setAdduserid(user.getUserid());
		push.setAddusername(user.getName());
		push.setAdddeptid(user.getDepartmentid());
		push.setAdddeptname(user.getDepartmentname());
		
		List list = JSONUtils.jsonStrToList(detaillist, new OaExpensePushDetail());

		int ret = oaExpensePushService.insertOaExpensePush(push, list);
		
		Map map = new HashMap();
		map.put("flag", ret > 0);
		map.put("backid", push.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("费用冲差支付申请单新增 编号：" + push.getId(), ret > 0);

		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 编辑费用冲差支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-8
	 */
	@UserOperateLog(key = "OaExpensePush", type = 3)
	public String editOaExpensePush() throws Exception {
		
		String detaillist = request.getParameter("detaillist");

		SysUser user = getSysUser();
		push.setModifyuserid(user.getUserid());
		push.setModifyusername(user.getName());
		
		List list = JSONUtils.jsonStrToList(detaillist, new OaExpensePushDetail());

		int ret = oaExpensePushService.updateOaExpensePush(push, list);
		
		Map map = new HashMap();
		map.put("flag", ret > 0);
		map.put("backid", push.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("费用冲差支付申请单编辑 编号：" + push.getId(), ret > 0);

		addJSONObject(map);
		return SUCCESS;
	}

    /**
     * 费用冲差支付申请单打印页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-06
     */
    public String oaExpensePushPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        OaExpensePush push = null;
        DepartMent dept = null;
        Customer customer = null;
		String noaccess = "0";

        if(StringUtils.isNotEmpty(id)) {

            push = oaExpensePushService.selectOaExpensePush(id);
            request.setAttribute("push", push);
        }

        if(push != null && StringUtils.isNotEmpty(push.getDeptid())) {
            dept = getDepartMentService().showDepartMentInfo(push.getDeptid());
        }

        if(push != null && StringUtils.isNotEmpty(push.getCustomerid())) {
            customer = getCustomerById(push.getCustomerid());
        }

        List<OaExpensePushDetail> list = oaExpensePushService.selectOaExpensePushDetailList(id);
        for(OaExpensePushDetail detail : list) {
            if(StringUtils.isNotEmpty(detail.getBrandid())) {
                detail.setBrandid(getBaseGoodsService().getBrandInfo(detail.getBrandid()).getName());
            }
            if(StringUtils.isNotEmpty(detail.getDeptid())) {
                detail.setDeptid(getDepartMentService().showDepartMentInfo(detail.getDeptid()).getName());
            }
			if(StringUtils.isNotEmpty(detail.getExpensesort())) {
				detail.setExpensesort(getBaseFinanceService().getExpensesSortDetail(detail.getExpensesort()).getThisname());
			}

			// 费用部门若为空，则表明noaccess=1
			if(StringUtils.isEmpty(detail.getDeptid())) {
				noaccess = "1";
			}
        }

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("dept", dept);
        request.setAttribute("customer", customer);
        request.setAttribute("list", list);
        request.setAttribute("process", process);
		request.setAttribute("noaccess", noaccess);
        return SUCCESS;
    }

}

