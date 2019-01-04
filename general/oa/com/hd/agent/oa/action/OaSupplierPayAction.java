/**
 * @(#)OaSupplierpayAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-17 zhanghonghui 创建版本
 */
package com.hd.agent.oa.action;

import java.util.HashMap;
import java.util.Map;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.Bank;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.oa.model.OaSupplierPay;
import com.hd.agent.oa.service.IOaSupplierPayService;

/**
 * 货款支付申请单Action
 * 
 * @author zhanghonghui
 */
public class OaSupplierPayAction extends BaseOaAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7376035076704226051L;
	
	private OaSupplierPay pay;

	private IOaSupplierPayService oaSupplierPayService;

	public OaSupplierPay getPay() {
		return pay;
	}

	public void setPay(OaSupplierPay pay) {
		this.pay = pay;
	}

	public IOaSupplierPayService getOaSupplierPayService() {
		return oaSupplierPayService;
	}

	public void setOaSupplierPayService(IOaSupplierPayService oaSupplierPayService) {
		this.oaSupplierPayService = oaSupplierPayService;
	}

	/**
	 * 货款支付申请单页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-29
	 */
	public String oaSupplierPayPage() throws Exception {

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
					CommonUtils.nullToEmpty(request.getParameter("sign"))
            		};
            if("view".equals(type) || "99".equals(step)) {

                url = "oa/oaSupplierPayViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/oaSupplierPayHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else if("handle".equals(type) && "02".equals(step)){

                url = "oa/oaSupplierPayHandlePage2.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else {

                url = "oa/oaSupplierPayHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

        return SUCCESS;
	}

	/**
	 * 货款支付申请单审批页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-29
	 */
	public String oaSupplierPayHandlePage() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		BuySupplier supplier = null;
		
		OaSupplierPay pay = oaSupplierPayService.selectSupplierPay(id);
		if(pay != null && StringUtils.isNotEmpty(pay.getSupplierid())) {

			supplier = getBaseBuySupplierById(pay.getSupplierid());
		}

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
                request.setAttribute("bank", bank);
            }

            request.setAttribute("pay", pay);
            request.setAttribute("supplier", supplier);
            request.setAttribute("process", process);
            return sign ? TO_SIGN : TO_PHONE;
        }

		request.setAttribute("pay", pay);
		request.setAttribute("supplier", supplier);
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 货款支付申请单审批页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-29
	 */
	public String oaSupplierPayHandlePage2() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		BuySupplier supplier = null;
		
		OaSupplierPay pay = oaSupplierPayService.selectSupplierPay(id);
		if(pay != null && StringUtils.isNotEmpty(pay.getSupplierid())) {

			supplier = getBaseBuySupplierById(pay.getSupplierid());
		}

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
                request.setAttribute("bank", bank);
            }

            request.setAttribute("pay", pay);
            request.setAttribute("supplier", supplier);
            request.setAttribute("process", process);
            return sign ? TO_SIGN : TO_PHONE;
        }

		request.setAttribute("pay", pay);
		request.setAttribute("supplier", supplier);
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 货款支付申请单查看页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-29
	 */
	public String oaSupplierPayViewPage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		BuySupplier supplier = null;
		
		OaSupplierPay pay = oaSupplierPayService.selectSupplierPay(id);
		if(pay != null && StringUtils.isNotEmpty(pay.getSupplierid())) {

			supplier = getBaseBuySupplierById(pay.getSupplierid());
		}

        Process process = workService.getProcess(processid, "1");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
                request.setAttribute("bank", bank);
            }

            request.setAttribute("pay", pay);
            request.setAttribute("supplier", supplier);
            request.setAttribute("process", process);
            return TO_PHONE;
        }
		
		request.setAttribute("pay", pay);
		request.setAttribute("supplier", supplier);
        request.setAttribute("process", process);
		return SUCCESS;
	}
	
	/**
	 * 添加货款支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-29
	 */
	public String addOaSupplierPay() throws Exception {
		
		if (isAutoCreate("t_oa_supplierpay")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(pay, "t_oa_supplierpay");
			pay.setId(id);
		} else {
			
			pay.setId("HK-" + CommonUtils.getDataNumber());
		}

		SysUser user = getSysUser();
		pay.setAdduserid(user.getUserid());
		pay.setAddusername(user.getName());
		pay.setAdddeptid(user.getDepartmentid());
		pay.setAdddeptname(user.getDepartmentname());

		int ret = oaSupplierPayService.insertOaSupplierPay(pay);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", pay.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("货款支付申请单新增 编号：" + pay.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 编辑货款支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-29
	 */
	public String editOaSupplierPay() throws Exception {

		SysUser user = getSysUser();
		pay.setModifyuserid(user.getUserid());
		pay.setModifyusername(user.getName());

		int ret = oaSupplierPayService.updateOaSupplierPay(pay);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", pay.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("货款支付申请单编辑 编号：" + pay.getId(), ret > 0);

		return SUCCESS;
	}

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-04-06
     */
    public String oaSupplierPayPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        BuySupplier supplier = null;
        Bank bank = null;

        OaSupplierPay pay = oaSupplierPayService.selectSupplierPay(id);
        if(pay != null && StringUtils.isNotEmpty(pay.getSupplierid())) {

            supplier = getBaseBuySupplierById(pay.getSupplierid());
        }
        if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

            bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
        }

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("pay", pay);
        request.setAttribute("supplier", supplier);
        request.setAttribute("bank", bank);
        request.setAttribute("process", process);
        return SUCCESS;
    }
}

