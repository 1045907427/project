/**
 * @(#)OaCustomerPayAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-17 limin 创建版本
 */
package com.hd.agent.oa.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.*;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.oa.model.OaCustomerPay;
import com.hd.agent.oa.model.OaCustomerPayDetail;
import com.hd.agent.oa.service.IOaCustomerPayService;
import com.hd.agent.system.model.SysCode;

/**
 * 客户费用支付申请单Action
 * 
 * @author limin
 */
public class OaCustomerPayAction extends BaseOaAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8958770235452462391L;

	private IOaCustomerPayService oaCustomerPayService;

	private OaCustomerPay customerpay;
	
	public IOaCustomerPayService getOaCustomerPayService() {
		return oaCustomerPayService;
	}

	public void setOaCustomerPayService(IOaCustomerPayService oaCustomerPayService) {
		this.oaCustomerPayService = oaCustomerPayService;
	}

	public OaCustomerPay getCustomerpay() {
		return customerpay;
	}

	public void setCustomerpay(OaCustomerPay customerpay) {
		this.customerpay = customerpay;
	}

	/**
	 * 客户费用支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-18
	 */
	public String oaCustomerPayPage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaCustomerPay pay = oaCustomerPayService.selectCustomerPayInfo(id);
		
		if(pay != null) {
			
			String supplierid = pay.getSupplierid();
			if(StringUtils.isNotEmpty(supplierid)) {
				
				BuySupplier supplier = getBaseBuySupplierById(supplierid);
				request.setAttribute("supplier", supplier);
			}

            String customerid2 = pay.getCustomerid2();
            if(StringUtils.isNotEmpty(customerid2)) {

                Customer customer2 = getCustomerById(customerid2);
                request.setAttribute("customer2", customer2);
            }
		}

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

                url = "oa/oaCustomerPayViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/oaCustomerPayHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else if("handle".equals(type) && "02".equals(step)){

                url = "oa/oaCustomerPayHandlePage2.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else {

                url = "oa/oaCustomerPayHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

        request.setAttribute("pay", pay);
		return SUCCESS;
	}

	/**
	 * 客户费用支付申请单（申请、审批）
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-18
	 */
	public String oaCustomerPayHandlePage() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaCustomerPay customerpay = oaCustomerPayService.selectCustomerPayInfo(id);
		List<SysCode> invoicetype = (List<SysCode>) getBaseSysCodeService().showSysCodeListByType("invoicetype");
		List<SysCode> paytype = (List<SysCode>) getBaseSysCodeService().showSysCodeListByType("paytype");
		SysUser user = getSysUser();
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("customerpay", customerpay);
		request.setAttribute("user", user);
		request.setAttribute("invoicetype", invoicetype);
		request.setAttribute("paytype", paytype);
        request.setAttribute("process", process);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            OaCustomerPay pay = oaCustomerPayService.selectCustomerPayInfo(id);

            if(pay != null && StringUtils.isNotEmpty(pay.getSupplierid())) {

                BuySupplier supplier = getBaseBuySupplierById(pay.getSupplierid());
                request.setAttribute("supplier", supplier);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getDeptid())) {

                DepartMent dept = getDepartMentService().showDepartMentInfo(pay.getDeptid());
                request.setAttribute("dept", dept);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getCustomerid())) {

                Customer customer = getCustomerById(pay.getCustomerid());
                request.setAttribute("customer", customer);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getExpensesort())) {

                ExpensesSort expensesort = getBaseFinanceService().getExpensesSortDetail(pay.getExpensesort());
                request.setAttribute("expensesort", expensesort);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaCustomerPayService.selectOaCustomerPayDetailList(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            request.setAttribute("pay", pay);
            return sign ? TO_SIGN : TO_PHONE;
        }

        return SUCCESS;
	}

	/**
	 * 客户费用支付申请单（申请、审批）
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-18
	 */
	public String oaCustomerPayHandlePage2() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");
		
		OaCustomerPay customerpay = oaCustomerPayService.selectCustomerPayInfo(id);
		List<SysCode> invoicetype = (List<SysCode>) getBaseSysCodeService().showSysCodeListByType("invoicetype");
		List<SysCode> paytype = (List<SysCode>) getBaseSysCodeService().showSysCodeListByType("paytype");
		SysUser user = getSysUser();
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("customerpay", customerpay);
		request.setAttribute("user", user);
		request.setAttribute("invoicetype", invoicetype);
		request.setAttribute("paytype", paytype);
        request.setAttribute("process", process);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            OaCustomerPay pay = oaCustomerPayService.selectCustomerPayInfo(id);

            if(pay != null && StringUtils.isNotEmpty(pay.getSupplierid())) {

                BuySupplier supplier = getBaseBuySupplierById(pay.getSupplierid());
                request.setAttribute("supplier", supplier);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getDeptid())) {

                DepartMent dept = getDepartMentService().showDepartMentInfo(pay.getDeptid());
                request.setAttribute("dept", dept);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getCustomerid())) {

                Customer customer = getCustomerById(pay.getCustomerid());
                request.setAttribute("customer", customer);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getExpensesort())) {

                ExpensesSort expensesort = getBaseFinanceService().getExpensesSortDetail(pay.getExpensesort());
                request.setAttribute("expensesort", expensesort);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaCustomerPayService.selectOaCustomerPayDetailList(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            request.setAttribute("pay", pay);
            return sign ? TO_SIGN : TO_PHONE;
        }

        return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-18
	 */
	public String selectOaCustomerPayDetailList() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setRows(9999);
		PageData pageData = oaCustomerPayService.selectOaCustomerPayDetailList(pageMap);
		
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 添加客户费用支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-18
	 */
	@UserOperateLog(key="OaCustomerPay", type=2)
	public String addOaCustomerPay() throws Exception {
		
		String customerpaydetail = request.getParameter("customerpaydetail");

		if (isAutoCreate("t_oa_customerpay")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(customerpay, "t_oa_customerpay");
			customerpay.setId(id);
		} else {
			
			customerpay.setId("KF-" + CommonUtils.getDataNumber());
		}

		if(StringUtils.isNotEmpty(customerpay.getSupplierid()) && StringUtils.isEmpty(customerpay.getDeptid())) {

			BuySupplier supplier = getBaseBuySupplierById(customerpay.getSupplierid());
			if(supplier != null) {

				customerpay.setDeptid(supplier.getBuydeptid());
			}
		}

		SysUser user = getSysUser();
		customerpay.setAdduserid(user.getUserid());
		customerpay.setAddusername(user.getName());
		customerpay.setAdddeptid(user.getDepartmentid());
		customerpay.setAdddeptname(user.getDepartmentname());
		
		List detailList = JSONUtils.jsonStrToList(customerpaydetail, new OaCustomerPayDetail());

		int ret = oaCustomerPayService.addOaCustomerPay(customerpay, detailList);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", customerpay.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("客户费用支付申请单新增 编号：" + customerpay.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 编辑客户费用支付申请单
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-18
	 */
	@UserOperateLog(key="OaCustomerPay", type=3)
	public String editOaCustomerPay() throws Exception {
		
		String customerpaydetail = request.getParameter("customerpaydetail");
		
		if(StringUtils.isEmpty(customerpay.getId())) {
			
			addOaCustomerPay();
			return SUCCESS;
		}

		if(StringUtils.isNotEmpty(customerpay.getSupplierid()) && StringUtils.isEmpty(customerpay.getDeptid())) {

			BuySupplier supplier = getBaseBuySupplierById(customerpay.getSupplierid());
			if(supplier != null) {

				customerpay.setDeptid(supplier.getBuydeptid());
			}
		}

		SysUser user = getSysUser();
		customerpay.setModifyuserid(user.getUserid());
		customerpay.setModifyusername(user.getName());

		List detailList = JSONUtils.jsonStrToList(customerpaydetail, new OaCustomerPayDetail());

		int ret = oaCustomerPayService.editOaCustomerPay(customerpay, detailList);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", customerpay.getId());
		map.put("type", "edit");
		addJSONObject(map);
		addLog("客户费用支付申请单编辑 编号：" + customerpay.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 查询品牌销售金额
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-11-20
	 */
	public String getCustomerBrandSalesAmount() throws Exception {
		
		String customerid = request.getParameter("customerid");
		String brandid = request.getParameter("brandid");
		String begindate = CommonUtils.emptyToNull(request.getParameter("begindate"));
		String enddate = CommonUtils.emptyToNull(request.getParameter("enddate"));
		String index = request.getParameter("index");
		
		if(StringUtils.isEmpty(index)) {
			
			index = "-1";
		}
		
		Customer customer = getBaseFilesService().getCustomerByID(customerid);
		
		if(customer == null) {
			
			Map map = new HashMap();
			map.put("salesamount", "0.00");
			map.put("index", Integer.parseInt(index));
			addJSONObject(map);
			return SUCCESS;
		}
		
		Map param = new HashMap();
		if("1".equals(customer.getIslast())) {
			
			param.put("customerid", customer.getId());
		} else {
			
			param.put("pcustomerid", customer.getId());
		}
		param.put("brandid", brandid);
		param.put("begindate", begindate);
		param.put("enddate", enddate);
		
		String salesamount = oaCustomerPayService.getCustomerBrandSalesAmount(param);
		
		Map map = new HashMap();
		map.put("salesamount", salesamount);
		map.put("index", Integer.parseInt(index));
		addJSONObject(map);
		return SUCCESS;
	}

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-22
     */
    public String oaCustomerPayViewPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaCustomerPay customerpay = oaCustomerPayService.selectCustomerPayInfo(id);
        List<SysCode> invoicetype = (List<SysCode>) getBaseSysCodeService().showSysCodeListByType("invoicetype");
        List<SysCode> paytype = (List<SysCode>) getBaseSysCodeService().showSysCodeListByType("paytype");
        SysUser user = getSysUser();
        Process process = workService.getProcess(processid, "1");

        request.setAttribute("customerpay", customerpay);
        request.setAttribute("user", user);
        request.setAttribute("invoicetype", invoicetype);
        request.setAttribute("paytype", paytype);
        request.setAttribute("process", process);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            OaCustomerPay pay = oaCustomerPayService.selectCustomerPayInfo(id);

            if(pay != null && StringUtils.isNotEmpty(pay.getSupplierid())) {

                BuySupplier supplier = getBaseBuySupplierById(pay.getSupplierid());
                request.setAttribute("supplier", supplier);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getDeptid())) {

                DepartMent dept = getDepartMentService().showDepartMentInfo(pay.getDeptid());
                request.setAttribute("dept", dept);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getCustomerid())) {

                Customer customer = getCustomerById(pay.getCustomerid());
                request.setAttribute("customer", customer);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getExpensesort())) {

                ExpensesSort expensesort = getBaseFinanceService().getExpensesSortDetail(pay.getExpensesort());
                request.setAttribute("expensesort", expensesort);
            }

            if(pay != null && StringUtils.isNotEmpty(pay.getPaybank())) {

                Bank bank = getBaseFinanceService().getBankDetail(pay.getPaybank());
                request.setAttribute("bank", bank);
            }

            List list = oaCustomerPayService.selectOaCustomerPayDetailList(id);
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
            request.setAttribute("pay", pay);
            return TO_PHONE;
        }

        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-11-22
     */
    public String oaCustomerPayPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        BuySupplier supplier = null;
        DepartMent dept = null;
        Customer customer = null;
        Bank bank = null;
        ExpensesSort expensesSort = null;

        OaCustomerPay customerpay = oaCustomerPayService.selectCustomerPayInfo(id);
        List<SysCode> invoicetype = (List<SysCode>) getBaseSysCodeService().showSysCodeListByType("invoicetype");
        List<SysCode> paytype = (List<SysCode>) getBaseSysCodeService().showSysCodeListByType("paytype");
        SysUser user = getSysUser();

        if(customerpay != null && StringUtils.isNotEmpty(customerpay.getSupplierid())) {
            supplier = getBaseBuySupplierById(customerpay.getSupplierid());
        }

        if(customerpay != null && StringUtils.isNotEmpty(customerpay.getDeptid())) {
            dept = getDepartMentService().showDepartMentInfo(customerpay.getDeptid());
        }

        if(customerpay != null && StringUtils.isNotEmpty(customerpay.getCustomerid())) {
            customer = getCustomerById(customerpay.getCustomerid());
        }

        if(customerpay != null && StringUtils.isNotEmpty(customerpay.getPaybank())) {
            bank = getBaseFinanceService().getBankDetail(customerpay.getPaybank());
        }

        if(customerpay != null && StringUtils.isNotEmpty(customerpay.getExpensesort())) {
            expensesSort = getBaseFinanceService().getExpensesSortDetail(customerpay.getExpensesort());
        }

        List<OaCustomerPayDetail> list = oaCustomerPayService.selectOaCustomerPayDetailList(id);
        for(OaCustomerPayDetail detail : list) {

            if(StringUtils.isNotEmpty(detail.getBrandid())) {
                detail.setBrandid(getBaseGoodsService().getBrandInfo(detail.getBrandid()).getName());
            }

            if(StringUtils.isNotEmpty(detail.getExpensesort())) {
                detail.setExpensesort( getBaseFinanceService().getExpensesSortDetail(customerpay.getExpensesort()).getName());
            }

            if(StringUtils.isNotEmpty(detail.getDeptid())) {
               detail.setDeptid(getDepartMentService().showDepartMentInfo(customerpay.getDeptid()).getName());
            }
        }

        Process process = workService.getProcess(processid, "1");

        request.setAttribute("customerpay", customerpay);
        request.setAttribute("user", user);
        request.setAttribute("invoicetype", invoicetype);
        request.setAttribute("paytype", paytype);
        request.setAttribute("dept", dept);
        request.setAttribute("supplier", supplier);
        request.setAttribute("customer", customer);
        request.setAttribute("bank", bank);
        request.setAttribute("expensesSort", expensesSort);
        request.setAttribute("list", list);
        request.setAttribute("process", process);
        return SUCCESS;
    }
}

