/**
 * @(#)OaAccessAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-18 limin 创建版本
 */
package com.hd.agent.oa.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.ExpensesSort;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.oa.model.*;
import com.hd.agent.oa.service.IOaAccessService;
import com.hd.agent.sales.model.OrderDetail;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 费用特价通路单Action
 * 
 * @author limin
 */
public class OaAccess2Action extends BaseOaAction {

	/**  */
	private static final long serialVersionUID = 4037821346622735119L;

	private OaAccess access;

	private OaAccessInvoice invoice;

	private IOaAccessService oaAccessService;

	public OaAccess getAccess() {
		return access;
	}

	public void setAccess(OaAccess access) {
		this.access = access;
	}

	public OaAccessInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(OaAccessInvoice invoice) {
		this.invoice = invoice;
	}

	public IOaAccessService getOaAccessService() {
		return oaAccessService;
	}

	public void setOaAccessService(IOaAccessService oaAccessService) {
		this.oaAccessService = oaAccessService;
	}

	/**
	 *
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-9-23
	 */
	public String oaAccessPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        String businessid = request.getParameter("businessid");

		OaAccess access = oaAccessService.selectOaAccessInfo(id);
		Customer customer = new Customer();
		BuySupplier supplier = new BuySupplier();

		if(access != null && StringUtils.isNotEmpty(access.getCustomerid())) {

			customer = getBaseFilesService().getCustomerByID(access.getCustomerid());
		}

		if(access != null && StringUtils.isNotEmpty(access.getSupplierid())) {

			supplier = getBaseFilesService().getSupplierInfoById(access.getSupplierid());
		}

		request.setAttribute("access", access);
		request.setAttribute("customer", customer);
		request.setAttribute("supplier", supplier);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Process process = workService.getProcess(processid, "1");
            request.setAttribute("process", process);

            String type = request.getParameter("type");
            String step = request.getParameter("step");

            String url = "";
			Object[] params = {CommonUtils.nullToEmpty(request.getParameter("type")),
					CommonUtils.nullToEmpty(request.getParameter("processid")),
					CommonUtils.nullToEmpty(request.getParameter("taskid")),
					process != null ? CommonUtils.nullToEmpty(process.getBusinessid()) : "",
					CommonUtils.nullToEmpty(request.getParameter("sign")),
					CommonUtils.nullToEmpty(request.getParameter("buyprice"))};
			if("view".equals(type) || "99".equals(step)) {

				url = "oa/oaAccessViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s&buyprice=%s";

			} else if("handle".equals(type) && "01".equals(step)){

				url = "oa/oaAccessHandlePage1.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s&buyprice=%s";

			} else if("handle".equals(type) && "02".equals(step)){

				url = "oa/oaAccessHandlePage2.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s&buyprice=%s";

			} else if("handle".equals(type) && "00".equals(step)){

				url = "oa/oaAccessHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s&buyprice=%s";

			} else {

				url = "oa/oaAccessHandlePage3.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s&buyprice=%s";
			}

			response.sendRedirect(String.format(url, params));
            return null;
        }

		return SUCCESS;
	}

	/**
	 * 费用通路单添加页面
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-9-24
	 */
	public String oaAccessAddPage() throws Exception {

		List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

		request.setAttribute("typelist", list);
		return SUCCESS;
	}

	/**
	 * 费用通路单查看页面
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-9-30
	 */
	public String oaAccessViewPage() throws Exception {

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		OaAccess access = oaAccessService.selectOaAccessInfo(id);
		OaAccessInvoice invoice = new OaAccessInvoice();
		Customer customer = new Customer();
		BuySupplier supplier = new BuySupplier();

		if(access != null/* && StringUtils.isNotEmpty(access.getId())*/) {

			invoice = oaAccessService.selectOaAccessInvoice(access.getId());
			customer = getBaseFilesService().getCustomerByID(access.getCustomerid());
			supplier = getBaseFilesService().getSupplierInfoById(access.getSupplierid());
		}

        Process process = workService.getProcess(processid, "1");

		List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            if(access != null && StringUtils.isNotEmpty(access.getCustomerid())) {

                customer = getBaseFilesService().getCustomerByID(access.getCustomerid());
            }

            if(access != null && StringUtils.isNotEmpty(access.getSupplierid())) {

                supplier = getBaseFilesService().getSupplierInfoById(access.getSupplierid());
            }

            request.setAttribute("access", access);
            request.setAttribute("customer", customer);
            request.setAttribute("supplier", supplier);

            List<OaAccessGoodsPrice> prices = oaAccessService.selectOaAccessGoodsPriceList(id);

            Map param = new HashMap();
            param.put("billid", id);
            pageMap.setCondition(param);
            pageMap.setRows(9999);

            List<OaAccessGoodsAmount> amounts = oaAccessService.selectOaAccessGoodsAmountList(pageMap).getList();

            List typelist = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

            ExpensesSort expenseSort = null;
            if(access != null && StringUtils.isNotEmpty(access.getExpensesort())) {

                expenseSort = getBaseFinanceService().getExpensesSortDetail(access.getExpensesort());
            }

            request.setAttribute("typelist", typelist);
            request.setAttribute("prices", prices);
            request.setAttribute("pricesJSON", JSONUtils.listToJsonStr(prices));
            request.setAttribute("amounts", amounts);
            request.setAttribute("amountsJSON", JSONUtils.listToJsonStr(amounts));
            request.setAttribute("expensesort", expenseSort);
            request.setAttribute("process", process);
            return TO_PHONE;
        }

		request.setAttribute("typelist", list);
		request.setAttribute("access", access);
		request.setAttribute("invoice", invoice);
		request.setAttribute("customer", customer);
		request.setAttribute("supplier", supplier);
		request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
        request.setAttribute("process", process);

		return SUCCESS;
	}

	/**
	 * 费用通路单编辑页面
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-9-30
	 */
	public String oaAccessEditPage() throws Exception {

		String id = request.getParameter("id");

		OaAccess access = oaAccessService.selectOaAccessInfo(id);
		OaAccessInvoice invoice = oaAccessService.selectOaAccessInvoice(access.getId());
		List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

		request.setAttribute("typelist", list);
		request.setAttribute("access", access);
		request.setAttribute("invoice", invoice);
		return SUCCESS;
	}

	/**
	 * 费用通路单审批页面
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-10-7
	 */
	public String oaAccessHandlePage() throws Exception {

		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		OaAccess access = new OaAccess();
		OaAccessInvoice invoice = new OaAccessInvoice();

		if(StringUtils.isNotEmpty(id)) {
			access = oaAccessService.selectOaAccessInfo(id);
		}

		if(access != null) {
			invoice = oaAccessService.selectOaAccessInvoice(access.getId());
		}

        Process process = workService.getProcess(processid, "1");

		List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

		request.setAttribute("typelist", list);
		request.setAttribute("access", access);
		request.setAttribute("invoice", invoice);
		request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 费用通路单申请页面
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-10-7
	 */
	public String oaAccessHandlePage1() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		OaAccess access = new OaAccess();
		OaAccessInvoice invoice = new OaAccessInvoice();

		if(StringUtils.isNotEmpty(id)) {
			access = oaAccessService.selectOaAccessInfo(id);
		}

		if(access != null) {
			invoice = oaAccessService.selectOaAccessInvoice(access.getId());
		}

        Process process = workService.getProcess(processid, "1");

		List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Customer customer = null;
            BuySupplier supplier = null;
            if(access != null && StringUtils.isNotEmpty(access.getCustomerid())) {


                customer = getBaseFilesService().getCustomerByID(access.getCustomerid());
            }

            if(access != null && StringUtils.isNotEmpty(access.getSupplierid())) {

                supplier = getBaseFilesService().getSupplierInfoById(access.getSupplierid());
            }

            request.setAttribute("access", access);
            request.setAttribute("customer", customer);
            request.setAttribute("supplier", supplier);

            List<OaAccessGoodsPrice> prices = oaAccessService.selectOaAccessGoodsPriceList(id);
            Map param = new HashMap();
            param.put("billid", id);
            pageMap.setCondition(param);
            pageMap.setRows(9999);

            List<OaAccessGoodsAmount> amounts = oaAccessService.selectOaAccessGoodsAmountList(pageMap).getList();

            List typelist = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

            ExpensesSort expenseSort = null;
            if(access != null && StringUtils.isNotEmpty(access.getExpensesort())) {

                expenseSort = getBaseFinanceService().getExpensesSortDetail(access.getExpensesort());
            }

            request.setAttribute("typelist", typelist);
            request.setAttribute("prices", prices);
            request.setAttribute("pricesJSON", JSONUtils.listToJsonStr(prices));
            request.setAttribute("amounts", amounts);
            request.setAttribute("amountsJSON", JSONUtils.listToJsonStr(amounts));
            request.setAttribute("expensesort", expenseSort);
			return sign ? TO_SIGN : TO_PHONE;
        }

		request.setAttribute("typelist", list);
		request.setAttribute("access", access);
		request.setAttribute("invoice", invoice);
		request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 费用通路单申请页面
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-10-13
	 */
	public String oaAccessHandlePage2() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		OaAccess access = new OaAccess();
		OaAccessInvoice invoice = new OaAccessInvoice();

		if(StringUtils.isNotEmpty(id)) {
			access = oaAccessService.selectOaAccessInfo(id);
		}

		if(access != null) {
			invoice = oaAccessService.selectOaAccessInvoice(access.getId());
		}

        Process process = workService.getProcess(processid, "1");

		List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Customer customer = null;
            BuySupplier supplier = null;
            if(access != null && StringUtils.isNotEmpty(access.getCustomerid())) {

                customer = getBaseFilesService().getCustomerByID(access.getCustomerid());
            }

            if(access != null && StringUtils.isNotEmpty(access.getSupplierid())) {

                supplier = getBaseFilesService().getSupplierInfoById(access.getSupplierid());
            }

            request.setAttribute("access", access);
            request.setAttribute("customer", customer);
            request.setAttribute("supplier", supplier);

            List<OaAccessGoodsPrice> prices = oaAccessService.selectOaAccessGoodsPriceList(id);
            Map param = new HashMap();
            param.put("billid", id);
            pageMap.setCondition(param);
            pageMap.setRows(9999);

            List<OaAccessGoodsAmount> amounts = oaAccessService.selectOaAccessGoodsAmountList(pageMap).getList();

            List typelist = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

            ExpensesSort expenseSort = null;
            if(access != null && StringUtils.isNotEmpty(access.getExpensesort())) {

                expenseSort = getBaseFinanceService().getExpensesSortDetail(access.getExpensesort());
            }

            request.setAttribute("typelist", typelist);
            request.setAttribute("prices", prices);
            request.setAttribute("pricesJSON", JSONUtils.listToJsonStr(prices));
            request.setAttribute("amounts", amounts);
            request.setAttribute("amountsJSON", JSONUtils.listToJsonStr(amounts));
            request.setAttribute("expensesort", expenseSort);
			return sign ? TO_SIGN : TO_PHONE;
        }

		request.setAttribute("typelist", list);
		request.setAttribute("access", access);
		request.setAttribute("invoice", invoice);
		request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 费用通路单申请页面
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-10-13
	 */
	public String oaAccessHandlePage3() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		OaAccess access = new OaAccess();
		OaAccessInvoice invoice = new OaAccessInvoice();

		if(StringUtils.isNotEmpty(id)) {
			access = oaAccessService.selectOaAccessInfo(id);
		}

		if(access != null) {
			invoice = oaAccessService.selectOaAccessInvoice(access.getId());
		}

        Process process = workService.getProcess(processid, "1");

		List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Customer customer = null;
            BuySupplier supplier = null;
            if(access != null && StringUtils.isNotEmpty(access.getCustomerid())) {

                customer = getBaseFilesService().getCustomerByID(access.getCustomerid());
            }

            if(access != null && StringUtils.isNotEmpty(access.getSupplierid())) {

                supplier = getBaseFilesService().getSupplierInfoById(access.getSupplierid());
            }

            request.setAttribute("access", access);
            request.setAttribute("customer", customer);
            request.setAttribute("supplier", supplier);

            List<OaAccessGoodsPrice> prices = oaAccessService.selectOaAccessGoodsPriceList(id);
            Map param = new HashMap();
            param.put("billid", id);
            pageMap.setCondition(param);
            pageMap.setRows(9999);

            List<OaAccessGoodsAmount> amounts = oaAccessService.selectOaAccessGoodsAmountList(pageMap).getList();

            List typelist = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

            ExpensesSort expenseSort = null;
            if(access != null && StringUtils.isNotEmpty(access.getExpensesort())) {

                expenseSort = getBaseFinanceService().getExpensesSortDetail(access.getExpensesort());
            }

            request.setAttribute("typelist", typelist);
            request.setAttribute("prices", prices);
            request.setAttribute("pricesJSON", JSONUtils.listToJsonStr(prices));
            request.setAttribute("amounts", amounts);
            request.setAttribute("amountsJSON", JSONUtils.listToJsonStr(amounts));
            request.setAttribute("expensesort", expenseSort);
            return sign ? TO_SIGN : TO_PHONE;
        }

		request.setAttribute("typelist", list);
		request.setAttribute("access", access);
		request.setAttribute("invoice", invoice);
		request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 费用通路单申请页面
	 * @return
	 * @author limin
	 * @throws Exception
	 * @date 2014-10-13
	 */
	public String oaAccessHandlePage4() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		OaAccess access = new OaAccess();
		OaAccessInvoice invoice = new OaAccessInvoice();

		if(StringUtils.isNotEmpty(id)) {
			access = oaAccessService.selectOaAccessInfo(id);
		}

		if(access != null) {
			invoice = oaAccessService.selectOaAccessInvoice(access.getId());
		}

        Process process = workService.getProcess(processid, "1");

		List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

		request.setAttribute("typelist", list);
		request.setAttribute("access", access);
		request.setAttribute("invoice", invoice);
		request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
        request.setAttribute("process", process);
		return SUCCESS;
	}

	/**
	 * 获取商品价格
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-9-26
	 */
	public String getGoodsPrice() throws Exception {

		String customerid = request.getParameter("customerid");
		String goodsid = request.getParameter("goodsid");
		String index = request.getParameter("index");	// index

		OrderDetail orderDetail = oaAccessService.getGoodsPrice(customerid, goodsid);

		Map map = new HashMap();
		map.put("goods", orderDetail);
		map.put("index", index);
		addJSONObject(map);
		// addJSONObject("goods", orderDetail);
		return SUCCESS;
	}

	/**
	 * 获取商品ERP数量
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-9-27
     * @date 2015-8-11
	 */
	public String getErpNum() throws Exception {

		String goodsid = request.getParameter("goodsid");
        String customerid = request.getParameter("customerid");
        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");
        String index = request.getParameter("index");

        Map result = oaAccessService.getErpNum(goodsid, customerid, startdate, enddate);

        if(result != null) {

            result.put("index", index);
        }

        addJSONObject(result);
        return SUCCESS;
//		StorageSummary storage = getStorageSummarySumByGoodsid(goodsid);
//
//		Map map = new HashMap();
//		if(storage != null) {
//
//			map.put("total", storage.getUsablenum());
//			map.put("unitname", storage.getUnitname());
//		} else {
//
//			map.put("total", "");
//			map.put("unitname", "");
//		}
//
//		addJSONObject(map);
//		return SUCCESS;
	}

	/**
	 * 获取箱装量
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-9-27
	 */
	public String getBoxNum() throws Exception {

		return SUCCESS;
	}

	/**
	 * 根据商品编号获取商品情报
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-9-28
	 */
	public String getGoodsInfo() throws Exception {

		String goodsid = request.getParameter("goodsid");

		GoodsInfo goods = getGoodsInfoByID(goodsid);
		addJSONObject("goods", goods);
		return SUCCESS;
	}

	/**
	 * 添加申请单
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-9-29
	 */
	public String addOaAccess() throws Exception {

		String goodsprice = request.getParameter("goodsprice");
		String goodsamount = request.getParameter("goodsamount");
		String branddiscount = request.getParameter("branddiscount");
//		String accessresult = request.getParameter("goodsprice");

		if (isAutoCreate("t_oa_access")) {

			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(access, "t_oa_access");
			access.setId(id);
		} else {

			access.setId("TLD-" + CommonUtils.getDataNumber());
		}

		SysUser user = getSysUser();
		access.setAdduserid(user.getUserid());
		access.setAddusername(user.getName());
		access.setAdddeptid(user.getDepartmentid());
		access.setAdddeptname(user.getDepartmentname());

		List priceList = JSONUtils.jsonStrToList(goodsprice, new OaAccessGoodsPrice());
		List amountList = JSONUtils.jsonStrToList(goodsamount, new OaAccessGoodsAmount());
		List discountList = JSONUtils.jsonStrToList(branddiscount, new OaAccessBrandDiscount());

		boolean ret = oaAccessService.addOaAccess(access, priceList, amountList, discountList, invoice);

		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", access.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("费用特价通路费申请单新增 编号：" + access.getId(), ret);

		return SUCCESS;
	}

	/**
	 * 编辑通路单申请单
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-10-8
	 */
	public String editOaAccess() throws Exception {

		String goodsprice = request.getParameter("goodsprice");
		String goodsamount = request.getParameter("goodsamount");
		String branddiscount = request.getParameter("branddiscount");

		if(StringUtils.isEmpty(access.getId())) {

			addOaAccess();
			return SUCCESS;
		}

		SysUser user = getSysUser();
		access.setModifyuserid(user.getUserid());
		access.setModifyusername(user.getName());

		List priceList = JSONUtils.jsonStrToList(goodsprice, new OaAccessGoodsPrice());
		List amountList = JSONUtils.jsonStrToList(goodsamount, new OaAccessGoodsAmount());
		List discountList = JSONUtils.jsonStrToList(branddiscount, new OaAccessBrandDiscount());

		boolean ret = oaAccessService.editOaAccess(access, priceList, amountList, discountList, invoice);

		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", access.getId());
		map.put("type", "edit");
		addJSONObject(map);
		addLog("费用特价通路费申请单编辑 编号：" + access.getId(), ret);

		return SUCCESS;
	}

	/**
	 * 根据单号查询申请单价格变更信息List
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-9-30
	 */
	public String selectOaAccessGoodsPriceList() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setRows(9999);
		PageData pageData = oaAccessService.selectOaAccessGoodsPriceList(pageMap);

		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 根据单号查询申请单差价金额信息List
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-9-30
	 */
	public String selectOaAccessGoodsAmountList() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setRows(9999);
		PageData pageData = oaAccessService.selectOaAccessGoodsAmountList(pageMap);

		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 根据单号查询品牌折扣信息List
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-9-30
	 */
	public String selectOaAccessBrandDiscountList() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setRows(9999);
		PageData pageData = oaAccessService.selectOaAccessBrandDiscountList(pageMap);

		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public String getCustomerById() throws Exception {

		String customerid = request.getParameter("customerid");

		Customer customer = getBaseFilesService().getCustomerByID(customerid );

		Map map = new HashMap();
		map.put("customer", customer);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 查询通路单情报
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2014-12-16
	 */
	public String selectAccessInfo() throws Exception {

		String id = request.getParameter("id");
		Map map = new HashMap();

		OaAccess access = oaAccessService.selectOaAccessInfo(id);
		map.put("access", access);

		if(access != null && StringUtils.isNotEmpty(access.getCustomerid())) {

			String customerid = access.getCustomerid();
			Customer customer = getCustomerById(customerid);
			map.put("customer", customer);
		}

		addJSONObject(map);
		return SUCCESS;
	}

    /**
     * 费用通路单打印页面
     * @return
     * @author limin
     * @throws Exception
     * @date 2014-9-30
     */
    public String oaAccessPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        OaAccess access = oaAccessService.selectOaAccessInfo(id);
        OaAccessInvoice invoice = new OaAccessInvoice();
        Customer customer = new Customer();
        BuySupplier supplier = new BuySupplier();
        ExpensesSort expensesSort = null;
        List<OaAccessGoodsPrice> pricelist = null;
        List<OaAccessGoodsAmount> amountlist = null;

        if(access != null/* && StringUtils.isNotEmpty(access.getId())*/) {

            invoice = oaAccessService.selectOaAccessInvoice(access.getId());
            customer = getBaseFilesService().getCustomerByID(access.getCustomerid());
            supplier = getBaseFilesService().getSupplierInfoById(access.getSupplierid());
            expensesSort = getBaseFinanceService().getExpensesSortDetail(access.getExpensesort());

            pricelist = oaAccessService.selectOaAccessGoodsPriceList(id);

            Map map = new HashMap();
            map.put("billid", id);
            pageMap.setCondition(map);
            pageMap.setRows(9999);
            amountlist = oaAccessService.selectOaAccessGoodsAmountList(pageMap).getList();
        }

        Process process = workService.getProcess(processid, "1");

        List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");

        request.setAttribute("typelist", list);
        request.setAttribute("access", access);
        request.setAttribute("invoice", invoice);
        request.setAttribute("customer", customer);
        request.setAttribute("supplier", supplier);
        request.setAttribute("expensesSort", expensesSort);
        request.setAttribute("pricelist", pricelist);
        request.setAttribute("amountlist", amountlist);
        request.setAttribute("invoicetype", getBaseSysCodeService().showSysCodeListByType("invoicetype"));
        request.setAttribute("process", process);

        return SUCCESS;
    }
}

