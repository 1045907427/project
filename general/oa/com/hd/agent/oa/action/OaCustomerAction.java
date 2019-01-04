/**
 * @(#)OaCustomerAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-14 limin 创建版本
 */
package com.hd.agent.oa.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.hd.agent.oa.model.OaCustomer;
import com.hd.agent.oa.model.OaCustomerBrand;
import com.hd.agent.oa.service.IOaCustomerService;

/**
 * 新客户登录单Action
 * 
 * @author limin
 */
public class OaCustomerAction extends BaseOaAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1566252653997525211L;

	private OaCustomer customer;
	
//	private String type;

	private IOaCustomerService oaCustomerService;

	public OaCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(OaCustomer customer) {
		this.customer = customer;
	}

	public IOaCustomerService getOaCustomerService() {
		return oaCustomerService;
	}

	public void setOaCustomerService(IOaCustomerService oaCustomerService) {
		this.oaCustomerService = oaCustomerService;
	}

	/**
	 * 新客户添加页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String oacustomerAddPage() throws Exception {
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");

		// 获取新客户登录单信息
		OaCustomer customer = oaCustomerService.selectOaCustomer(id);
		if(customer == null) {
			
			type = "edit";
		}

		List dayList = new ArrayList();
		for(int i = 1; i < 32; i++) {
			dayList.add(i);
		}
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");

		request.setAttribute("creditratingList", creditratingList);
		request.setAttribute("canceltypeList", canceltypeList);
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("dayList", dayList);
		request.setAttribute("customer", customer);
		request.setAttribute("type", type);
		request.setAttribute("promotionsortList", promotionsortList);

		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String oacustomerPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

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
					CommonUtils.nullToEmpty(request.getParameter("sign"))};
            if("view".equals(type) || "99".equals(step)) {

                url = "oa/oacustomerViewPage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else if("handle".equals(type) && "01".equals(step)){

                url = "oa/oacustomerHandlePage1.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";

            } else {

                url = "oa/oacustomerHandlePage.do?type=%s&to=phone&processid=%s&taskid=%s&id=%s&sign=%s";
            }

            response.sendRedirect(String.format(url, params));
            return null;
        }

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String oacustomerBrandAddPage() {
		
		return SUCCESS;
	}
	
	/**
	 * 品牌进场明细编辑页面
	 * @return
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String oacustomerBrandEditPage(){
		
		return SUCCESS;
	}
	
	/**
	 * 获取条码总数
	 * @return
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String getBarcodenum() {

		String brand = request.getParameter("brand");
		
		Map map = new HashMap();
		map.put("barcodenum", oaCustomerService.selectBarcodenum(brand));
		addJSONObject(map);
		
		return SUCCESS;
	}
	
	/**
	 * 保存新客户
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-6-24
	 */
	@UserOperateLog(key="OaCustomer",type=2)
	public String addOaCustomer() throws Exception {

		if (isAutoCreate("t_oa_customer")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(customer, "t_oa_customer");
			customer.setId(id);
		} else {
			customer.setId("KH-" + CommonUtils.getDataNumber());
		}

		customer.setAdduserid(getSysUser().getUserid());
		customer.setAddusername(getSysUser().getName());
		customer.setAdddeptid(getSysUser().getDepartmentid());
		customer.setAdddeptname(getSysUser().getDepartmentname());
		
		String customerBrandJSON = request.getParameter("customerBrandJSON");
		List<OaCustomerBrand> brandList = JSONUtils.jsonStrToList(customerBrandJSON, new OaCustomerBrand());
		
		int ret = oaCustomerService.insertOaCustomer(customer, brandList);
		
		String personids = request.getParameter("allotpersonids");
		Map allotMap = new HashMap();
		allotMap.put("billid", customer.getId());
		allotMap.put("personids", personids);
		oaCustomerService.addPsnBrandCustomer(allotMap);
		
		Map map = new HashMap();
		map.put("flag", ret);
		map.put("backid", customer.getId());
		map.put("type", "add");
		addJSONObject(map);
		addLog("新客户登录单新增 编号：" + customer.getId(), ret > 0);
		
		return SUCCESS;
	}
	
	/**
	 * 修改新客户登录票
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-6-28
	 */
	@UserOperateLog(key="OaCustomer", type=3)
	public String editOaCustomer() throws Exception {
		
		if(StringUtils.isEmpty(customer.getId())) {
			
			addOaCustomer();
			return SUCCESS;
		}
		
		SysUser user = getSysUser();
		customer.setModifyuserid(user.getUserid());
		customer.setModifyusername(user.getName());
		
		String customerBrandJSON = request.getParameter("customerBrandJSON");
		List<OaCustomerBrand> brandList = JSONUtils.jsonStrToList(customerBrandJSON, new OaCustomerBrand());
		
		int ret = oaCustomerService.updateOaCustomer(customer, brandList);
		
		Map map = new HashMap();
		map.put("flag", true);
		map.put("backid", customer.getId());
		map.put("type", "edit");
		addJSONObject(map);
		addLog("新客户登录单编辑 编号：" + customer.getId(), ret > 0);

		return SUCCESS;
	}
	
	/**
	 * 删除新客户登录票
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-7-1
	 */
	@UserOperateLog(key = "OaCustomer", type =4)
	public String deleteOaCustomer() throws Exception {

		String id = request.getParameter("id");

		boolean lock = isLockEdit("t_oa_customer", id);

		// 该客户登录票被锁定
		if(!lock) {

			Map map = new HashMap();
			map.put("flag", false);
			map.put("lock", false);
			addJSONObject(map);
			
			return SUCCESS;
		}
		
		int ret = oaCustomerService.deleteOaCustomer(id);
		
		Map map = new HashMap();
		map.put("flag", true);
		map.put("backid", id);
		map.put("type", "delete");
		addJSONObject(map);
		addLog("新客户登录单删除 编号：" + id, ret > 0);
		return SUCCESS;
	}
	
	/**
	 * 新客户列表查询
	 * @return
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String selectOacustomerList() {
		
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		
		PageData pageData = oaCustomerService.selectOaCustomerList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 新客户登录票列表页面
	 * @return
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String oacustomerListPage() {
		
		return SUCCESS;
	}
	
	/**
	 * 查询新客户登录票状态
	 * @return
	 * @author limin 
	 * @date 2014-6-30
	 */
	public String selectOacustomerStatus() {
		
		String id = request.getParameter("id");
		
		OaCustomer customer = oaCustomerService.selectOaCustomer(id);
		
		Map map = new HashMap ();
		map.put("status", customer != null ? customer.getStatus() : "");
		map.put("exist", customer == null ? false : true);
		
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 新客户信息查看页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String oacustomerViewPage() throws Exception {
		
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		// 获取新客户登录单信息
		OaCustomer customer = oaCustomerService.selectOaCustomer(id);
		
		List dayList = new ArrayList();
		for(int i = 1; i < 32; i++) {
			dayList.add(i);
		}
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
        Process process = workService.getProcess(processid, "1");

		request.setAttribute("creditratingList", creditratingList);
		request.setAttribute("canceltypeList", canceltypeList);
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("dayList", dayList);
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("customer", customer);
        request.setAttribute("process", process);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Map param = new HashMap();
            param.put("billid", id);
            pageMap.setCondition(param);
            pageMap.setRows(9999);

            List brands = oaCustomerService.selectCustomerBrandList(pageMap).getList();

            // 销售区域
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesarea())) {

                SalesArea salesarea = getBaseSalesService().getSalesAreaDetail(customer.getSalesarea());
                request.setAttribute("salesarea", salesarea);
            }

            // 销售区域
            if(customer != null && StringUtils.isNotEmpty(customer.getCustomersort())) {

                CustomerSort customersort = getBaseSalesService().getCustomerSortDetail(customer.getCustomersort());
                request.setAttribute("customersort", customersort);
            }

            // 客户业务员
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesuserid())) {

                Personnel salesuser = getPersonnelInfoById(customer.getSalesuserid());
                request.setAttribute("salesuser", salesuser);
            }

            // 默认内勤
            if(customer != null && StringUtils.isNotEmpty(customer.getIndoorstaff())) {

                Personnel indoorstaff = getPersonnelInfoById(customer.getIndoorstaff());
                request.setAttribute("indoorstaff", indoorstaff);
            }

            // 结算方式
            if(customer != null && StringUtils.isNotEmpty(customer.getSettletype())) {

                Settlement settletype = oaCustomerService.getSettlement(customer.getSettletype());
                request.setAttribute("settletype", settletype);
            }

            // 上级客户
            if(customer != null && StringUtils.isNotEmpty(customer.getPcustomerid())) {

                Customer pcustomer = getCustomerById(customer.getPcustomerid());
                request.setAttribute("pcustomer", pcustomer);
            }

            // 销售部门
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesdeptid())) {

                DepartMent salesdept = getDepartMentService().showDepartMentInfo(customer.getSalesdeptid());
                request.setAttribute("salesdept", salesdept);
            }

            request.setAttribute("brands", JSONUtils.listToJsonStr(brands));
            return TO_PHONE;
        }

		return SUCCESS;
	}
	
	/**
	 * 新客户信息编辑
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-6-27
	 */
	public String oacustomerEditPage() throws Exception {
		
		String id = request.getParameter("id");

		// 获取新客户登录单信息
		OaCustomer customer = oaCustomerService.selectOaCustomer(id);
		
		List dayList = new ArrayList();
		for(int i = 1; i < 32; i++) {
			dayList.add(i);
		}
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
		
		request.setAttribute("creditratingList", creditratingList);
		request.setAttribute("canceltypeList", canceltypeList);
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("dayList", dayList);
		request.setAttribute("customer", customer);
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("type", request.getParameter("type"));
		
		return SUCCESS;
	}
	
	/**
	 * 新客户品牌进场明细列表
	 * @return
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String selectCustomerBrandList() {
		
		String billid = request.getParameter("billid");
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setRows(9999);
		PageData pageData = oaCustomerService.selectCustomerBrandList(pageMap);
		
		addJSONObject(pageData);
		
		return SUCCESS;
	}
	
	/**
	 * Check客户编号是否被占用
	 * @return
	 * @author limin 
	 * @date 2014-6-24
	 */
	public String checkCustomerUsed() {

		String customerid = request.getParameter("customerid");
		String id = request.getParameter("id");

		int ret = oaCustomerService.selectExistedCustomerCount(customerid, id);
		
		addJSONObject("flag", false);
		if(ret > 0) {
			addJSONObject("flag", true);
		}
		
		return SUCCESS;
	}

	/**
	 * Check客户名称是否被占用
	 * @return
	 * @author limin
	 * @date Feb 6, 2018
	 */
	public String checkCustomerNameUsed() {

		String customername = request.getParameter("customername");
		String id = request.getParameter("id");

		int ret = oaCustomerService.selectExistedCustomerNameCount(customername, id);

		addJSONObject("flag", false);
		if(ret > 0) {
			addJSONObject("flag", true);
		}

		return SUCCESS;
	}
	
	/**
	 * 新客户登录审批页面
	 * @return
	 * @author limin 
	 * @throws Exception 
	 * @date 2014-6-30
	 */
	public String oacustomerHandlePage() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		// 获取新客户登录单信息
		OaCustomer customer = oaCustomerService.selectOaCustomer(id);
		
		List dayList = new ArrayList();
		for(int i = 1; i < 32; i++) {
			dayList.add(i);
		}
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("creditratingList", creditratingList);
		request.setAttribute("canceltypeList", canceltypeList);
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("dayList", dayList);
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("customer", customer);
		request.setAttribute("type", request.getParameter("type"));
        request.setAttribute("process", process);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Map param = new HashMap();
            param.put("billid", id);
            pageMap.setCondition(param);
            pageMap.setRows(9999);

            List brands = oaCustomerService.selectCustomerBrandList(pageMap).getList();

            // 销售区域
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesarea())) {

                SalesArea salesarea = getBaseSalesService().getSalesAreaDetail(customer.getSalesarea());
                request.setAttribute("salesarea", salesarea);
            }

            // 销售区域
            if(customer != null && StringUtils.isNotEmpty(customer.getCustomersort())) {

                CustomerSort customersort = getBaseSalesService().getCustomerSortDetail(customer.getCustomersort());
                request.setAttribute("customersort", customersort);
            }

            // 客户业务员
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesuserid())) {

                Personnel salesuser = getPersonnelInfoById(customer.getSalesuserid());
                request.setAttribute("salesuser", salesuser);
            }

            // 默认内勤
            if(customer != null && StringUtils.isNotEmpty(customer.getIndoorstaff())) {

                Personnel indoorstaff = getPersonnelInfoById(customer.getIndoorstaff());
                request.setAttribute("indoorstaff", indoorstaff);
            }

            // 结算方式
            if(customer != null && StringUtils.isNotEmpty(customer.getSettletype())) {

                Settlement settletype = oaCustomerService.getSettlement(customer.getSettletype());
                request.setAttribute("settletype", settletype);
            }

            // 上级客户
            if(customer != null && StringUtils.isNotEmpty(customer.getPcustomerid())) {

                Customer pcustomer = getCustomerById(customer.getPcustomerid());
                request.setAttribute("pcustomer", pcustomer);
            }

            // 销售部门
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesdeptid())) {

                DepartMent salesdept = getDepartMentService().showDepartMentInfo(customer.getSalesdeptid());
                request.setAttribute("salesdept", salesdept);
            }

            request.setAttribute("brands", JSONUtils.listToJsonStr(brands));
            return sign ? TO_SIGN : TO_PHONE;
        }

		return SUCCESS;
	}
	
	/**
	 * 新客户登录添加页面
	 * @return
	 * @author limin 
	 * @throws Exception 
	 * @date 2014-6-30
	 */
	public String oacustomerHandlePage1() throws Exception {

		boolean sign = "1".equals(request.getParameter("sign"));
		String id = request.getParameter("id");
        String processid = request.getParameter("processid");

		// 获取新客户登录单信息
		OaCustomer customer = oaCustomerService.selectOaCustomer(id);
		
		List dayList = new ArrayList();
		for(int i = 1; i < 32; i++) {
			dayList.add(i);
		}
		List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
		List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
		List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
        Process process = workService.getProcess(processid, "1");
		
		request.setAttribute("creditratingList", creditratingList);
		request.setAttribute("canceltypeList", canceltypeList);
		request.setAttribute("priceList", priceList); //价格套
		request.setAttribute("dayList", dayList);
		request.setAttribute("promotionsortList", promotionsortList);
		request.setAttribute("customer", customer);
		request.setAttribute("type", request.getParameter("type"));
        request.setAttribute("process", process);

        if(TO_PHONE.equals(request.getParameter("to"))) {

            Map param = new HashMap();
            param.put("billid", id);
            pageMap.setCondition(param);
            pageMap.setRows(9999);

            List brands = oaCustomerService.selectCustomerBrandList(pageMap).getList();

            // 销售区域
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesarea())) {

                SalesArea salesarea = getBaseSalesService().getSalesAreaDetail(customer.getSalesarea());
                request.setAttribute("salesarea", salesarea);
            }

            // 销售区域
            if(customer != null && StringUtils.isNotEmpty(customer.getCustomersort())) {

                CustomerSort customersort = getBaseSalesService().getCustomerSortDetail(customer.getCustomersort());
                request.setAttribute("customersort", customersort);
            }

            // 客户业务员
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesuserid())) {

                Personnel salesuser = getPersonnelInfoById(customer.getSalesuserid());
                request.setAttribute("salesuser", salesuser);
            }

            // 默认内勤
            if(customer != null && StringUtils.isNotEmpty(customer.getIndoorstaff())) {

                Personnel indoorstaff = getPersonnelInfoById(customer.getIndoorstaff());
                request.setAttribute("indoorstaff", indoorstaff);
            }

            // 结算方式
            if(customer != null && StringUtils.isNotEmpty(customer.getSettletype())) {

                Settlement settletype = oaCustomerService.getSettlement(customer.getSettletype());
                request.setAttribute("settletype", settletype);
            }

            // 上级客户
            if(customer != null && StringUtils.isNotEmpty(customer.getPcustomerid())) {

                Customer pcustomer = getCustomerById(customer.getPcustomerid());
                request.setAttribute("pcustomer", pcustomer);
            }

            // 销售部门
            if(customer != null && StringUtils.isNotEmpty(customer.getSalesdeptid())) {

                DepartMent salesdept = getDepartMentService().showDepartMentInfo(customer.getSalesdeptid());
                request.setAttribute("salesdept", salesdept);
            }

            request.setAttribute("brands", JSONUtils.listToJsonStr(brands));
            return sign ? TO_SIGN : TO_PHONE;
        }

		return SUCCESS;
	}
	
	public String oaAllotPSNCustomerPage() throws Exception {
		
		String id = request.getParameter("id");
		String personids = CommonUtils.nullToEmpty(request.getParameter("p"));
		String companies = CommonUtils.nullToEmpty(request.getParameter("d"));
		Map map4 = new HashMap();
		List<Map> paramlist = new ArrayList<Map>();
		
		List<Personnel> personnelList = getBasePersonnelService().getPersListByOperType("3");
		
		for(Personnel personnel : personnelList) {
			
			if(StringUtils.isNotEmpty(personnel.getBelongdeptid())){
				
				if(map4.containsKey(personnel.getBelongdeptid())) {
					
					continue;
				}
				
				map4.put(personnel.getBelongdeptid(), personnel.getBelongdeptid());
				Map map2 = new LinkedHashMap();
				if(StringUtils.isNotEmpty(id)) {
					
					List<Map<String, String>> bclist = oaCustomerService.selectPersonListByBillid(id);
					for(Map map : bclist) {
						
						if(null != map.get("belongdeptid") && map.get("belongdeptid").equals(personnel.getBelongdeptid())){
							if(null != map.get("personid")){
								map2.put("personid", map.get("personid"));
							}
						}
					}
					request.setAttribute("type", "1");
				} else {
					
					request.setAttribute("type", "0");
				}
				
				DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(personnel.getBelongdeptid());
				if(departMent != null) {
					
            		map2.put("deptid", personnel.getBelongdeptid());
            		map2.put("deptname", departMent.getName());
            		paramlist.add(map2);
				}
			}
		}
		
		if(StringUtils.isNotEmpty(personids)) {
			
			String[] personid = personids.split(",");
			String[] company = companies.split(",");
			
			Map temp = new HashMap();
			for(int i= 0; i < personid.length; i++) {
				
				String pid = personid[i];
				String cmp = company[i];
				temp.put(cmp, pid);
			}
			
			for(Map m : paramlist) {
				
				if(temp.get(m.get("deptid")) != null) {
					m.put("personid", temp.get(m.get("deptid")));
				}
			}
		}
		
		request.setAttribute("paramlist", paramlist);
		request.setAttribute("id", id);
		return SUCCESS;
	}
	
	/**
	 * 分配品牌业务员
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-9-19
	 */
	@UserOperateLog(key="OaCustomer", type=3)
	public String allotCustomerToPsn() throws Exception {

		String billid = request.getParameter("billid");
		String personids = request.getParameter("personids");
		String delpersonids = request.getParameter("delpersonids");
		String companys = request.getParameter("company");
		String msg = null;
		Map paramMap = new HashMap();
		paramMap.put("personids", personids);
		paramMap.put("delpersonids", delpersonids);
		paramMap.put("companys", companys);
		paramMap.put("billid", billid);

		boolean flag = oaCustomerService.addPsnBrandCustomer(paramMap);
		
		if(flag) {

			Map map = new HashMap();
			map.put("flag", flag);
			map.put("backid", billid);
			map.put("type", "edit");
			addJSONObject(map);
			addLog("新客户登录单分配品牌业务员 单据编号：" + billid, flag);
		}

		return SUCCESS;
	}

    /**
     * 新客户信息查看页面
     * @return
     * @throws Exception
     * @author limin
     * @date 2014-6-24
     */
    public String oacustomerPrintPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");
        SalesArea salesArea = null;
        CustomerSort customerSort = null;
        Personnel salesUser = null;
        Personnel payUser = null;
        Personnel indoorUser = null;
        Customer pcustomer = null;      // 4040 通用版&鸿都：增加上级客户，手机上也要改，把助记符去掉
        Settlement settlement = null;
        DepartMent salesdept = null;    // 4083 新客户登陆增加销售部门

        // 获取新客户登录单信息
        OaCustomer customer = oaCustomerService.selectOaCustomer(id);

        List dayList = new ArrayList();
        for(int i = 1; i < 32; i++) {
            dayList.add(i);
        }
        List creditratingList = getBaseSysCodeService().showSysCodeListByType("creditrating");
        List canceltypeList = getBaseSysCodeService().showSysCodeListByType("canceltype");
        List priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
        List promotionsortList = getBaseSysCodeService().showSysCodeListByType("promotionsort");
        Process process = workService.getProcess(processid, "1");

        if(process == null || !process.getBusinessid().equals(id)) {
            request.setAttribute("process", process);
            return SUCCESS;
        }

        if(customer != null && StringUtils.isNotEmpty(customer.getSalesarea())) {
            salesArea = getBaseSalesService().getSalesAreaDetail(customer.getSalesarea());
        }

        if(customer != null && StringUtils.isNotEmpty(customer.getCustomersort())) {
            customerSort = getBaseSalesService().getCustomerSortDetail(customer.getCustomersort());
        }

        if(customer != null && StringUtils.isNotEmpty(customer.getSalesuserid())) {
            salesUser = getPersonnelInfoById(customer.getSalesuserid());
        }

        if(customer != null && StringUtils.isNotEmpty(customer.getPayeeid())) {
            payUser = getPersonnelInfoById(customer.getPayeeid());
        }

        if(customer != null && StringUtils.isNotEmpty(customer.getIndoorstaff())) {
            indoorUser = getPersonnelInfoById(customer.getIndoorstaff());
        }

        if(customer != null && StringUtils.isNotEmpty(customer.getSettletype())) {
            settlement = getBaseFinanceService().getSettlemetDetail(customer.getSettletype());
        }

        // 4040 通用版&鸿都：增加上级客户，手机上也要改，把助记符去掉
        if(customer != null && StringUtils.isNotEmpty(customer.getPcustomerid())) {

            pcustomer = getCustomerById(customer.getPcustomerid());
        }

        // 4083 新客户登陆增加销售部门
        if(customer != null && StringUtils.isNotEmpty(customer.getSalesdeptid())) {

            salesdept = getDepartMentService().showDepartMentInfo(customer.getSalesdeptid());
        }

        Map map = new HashMap();
        map.put("billid", id);
        pageMap.setCondition(map);
        pageMap.setRows(9999);
        PageData data = oaCustomerService.selectCustomerBrandList(pageMap);
        List<OaCustomerBrand> list = data.getList();
        for(OaCustomerBrand brand : list) {

            if(StringUtils.isNotEmpty(brand.getBrandid())) {

                brand.setBrandname(getBaseGoodsService().getBrandInfo(brand.getBrandid()).getName());
            }
        }

        request.setAttribute("creditratingList", creditratingList);
        request.setAttribute("canceltypeList", canceltypeList);
        request.setAttribute("priceList", priceList); //价格套
        request.setAttribute("dayList", dayList);
        request.setAttribute("promotionsortList", promotionsortList);
        request.setAttribute("customer", customer);
        request.setAttribute("process", process);
        request.setAttribute("salesArea", salesArea);
        request.setAttribute("customerSort", customerSort);
        request.setAttribute("salesUser", salesUser);
        request.setAttribute("payUser", payUser);
        request.setAttribute("indoorUser", indoorUser);
        request.setAttribute("settlement", settlement);
        request.setAttribute("list", list);
        // 4040 通用版&鸿都：增加上级客户，手机上也要改，把助记符去掉
        request.setAttribute("pcustomer", pcustomer);
        // 4083 新客户登陆增加销售部门
        request.setAttribute("salesdept", salesdept);
        return SUCCESS;
    }

    /**
     * 手机分配品牌业务员
     * @return
     * @throws Exception
     * @author limin
     * @date Jul 29, 2015
     */
    public String oacustomerAllotPSNPage() throws Exception {

        String id = request.getParameter("id");
        String personids = CommonUtils.nullToEmpty(request.getParameter("p"));
        String companies = CommonUtils.nullToEmpty(request.getParameter("d"));
        Map map4 = new HashMap();
        List<Map> paramlist = new ArrayList<Map>();

        List<Personnel> personnelList = getBasePersonnelService().getPersListByOperType("3");

        for(Personnel personnel : personnelList) {

            if(StringUtils.isNotEmpty(personnel.getBelongdeptid())){

                if(map4.containsKey(personnel.getBelongdeptid())) {

                    continue;
                }

                map4.put(personnel.getBelongdeptid(), personnel.getBelongdeptid());
                Map map2 = new LinkedHashMap();
                if(StringUtils.isNotEmpty(id)) {

                    List<Map<String, String>> bclist = oaCustomerService.selectPersonListByBillid(id);
                    for(Map map : bclist) {

                        if(null != map.get("belongdeptid") && map.get("belongdeptid").equals(personnel.getBelongdeptid())){
                            if(null != map.get("personid")){
                                map2.put("personid", map.get("personid"));
                            }
                        }
                    }
                    request.setAttribute("type", "1");
                } else {

                    request.setAttribute("type", "0");
                }

                DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(personnel.getBelongdeptid());
                if(departMent != null) {

                    map2.put("deptid", personnel.getBelongdeptid());
                    map2.put("deptname", departMent.getName());
                    paramlist.add(map2);
                }
            }
        }

        if(StringUtils.isNotEmpty(personids)) {

            String[] personid = personids.split(",");
            String[] company = companies.split(",");

            Map temp = new HashMap();
            for(int i= 0; i < personid.length; i++) {

                String pid = personid[i];
                String cmp = company[i];
                temp.put(cmp, pid);
            }

            for(Map m : paramlist) {

                if(temp.get(m.get("deptid")) != null) {
                    m.put("personid", temp.get(m.get("deptid")));
                }
            }
        }

        for(Map temp : paramlist) {

            String personid = (String) temp.get("personid");
            if(StringUtils.isEmpty(personid)) {

                continue;
            }

            Personnel personnel = getPersonnelInfoById(personid);
            temp.put("personname", personnel.getName());
        }

        request.setAttribute("paramlist", paramlist);
        request.setAttribute("id", id);
        return SUCCESS;
    }
}

