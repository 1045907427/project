/**
 * @(#)OaCustomerExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-6-28 limin 创建版本
 */
package com.hd.agent.oa.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.activiti.dao.ProcessMapper;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.oa.dao.OaCustomerBrandMapper;
import com.hd.agent.oa.dao.OaCustomerMapper;
import com.hd.agent.oa.model.OaCustomer;
import org.apache.commons.lang3.StringUtils;

/**
 * 新客户登录单End Listener
 * 
 * @author limin
 */
public class OaCustomerExecutionEndListener extends BusinessEndListenerImpl {

	private OaCustomerMapper oaCustomerMapper;
	
	private BaseServiceImpl baseService;
	
	private ProcessMapper processMapper;
	
	private OaCustomerBrandMapper oaCustomerBrandMapper;
	
	private IPersonnelService personnelService;

	public OaCustomerMapper getOaCustomerMapper() {
		return oaCustomerMapper;
	}

	public void setOaCustomerMapper(OaCustomerMapper oaCustomerMapper) {
		this.oaCustomerMapper = oaCustomerMapper;
	}

	public BaseServiceImpl getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseServiceImpl baseService) {
		this.baseService = baseService;
	}

	public ProcessMapper getProcessMapper() {
		return processMapper;
	}

	public void setProcessMapper(ProcessMapper processMapper) {
		this.processMapper = processMapper;
	}

	public OaCustomerBrandMapper getOaCustomerBrandMapper() {
		return oaCustomerBrandMapper;
	}

	public void setOaCustomerBrandMapper(OaCustomerBrandMapper oaCustomerBrandMapper) {
		this.oaCustomerBrandMapper = oaCustomerBrandMapper;
	}

	public IPersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(IPersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	@Override
	public void end(Process process) throws Exception {
		
		OaCustomer oac = oaCustomerMapper.selectOaCustomer(process.getBusinessid());

		if(StringUtils.isEmpty(oac.getCustomerid())) {
			throw new Exception("客户编码为空，无法转交。");
		}

		Customer customer = new Customer();
		
		customer.setId(oac.getCustomerid());
		customer.setThisid(oac.getCustomerid());
		customer.setState("1");
		customer.setRemark(oac.getRemark());
		customer.setName(oac.getCustomername());
        // 4083 新客户登陆增加销售部门
        customer.setShortname(oac.getCustomername());
		customer.setShortcode(oac.getShortcode());
		customer.setAbclevel(oac.getAbclevel());
		customer.setTelphone(oac.getTelphone());
		customer.setFaxno(oac.getFaxno());
		customer.setEmail(oac.getEmail());
		customer.setMobile(oac.getMobile());
		customer.setWebsite(oac.getWebsite());
		customer.setContactname(oac.getContact());
		customer.setMobile(oac.getMobile());
		customer.setPersoncard(oac.getPersoncard());
		customer.setNature(oac.getNature());
		customer.setFund(oac.getFund());
		customer.setSetupdate(oac.getSetupdate());
		customer.setStaffnum(oac.getStaffnum());
		customer.setTurnoveryear(oac.getTurnoveryear());
		customer.setFirstbusinessdate(oac.getFirstbusinessdate());
		customer.setSalesarea(oac.getSalesarea());
		customer.setCustomersort(oac.getCustomersort());
		customer.setContact(oac.getContact());
		customer.setAddress(oac.getAddress());
		customer.setPromotionsort(oac.getPromotionsort());
		customer.setZip(oac.getZip());
		customer.setTaxno(oac.getTaxno());
		customer.setBank(oac.getBank());
		customer.setCardno(oac.getCardno());
		customer.setCaraccount(oac.getCaraccount());
		customer.setStorearea(oac.getStorearea());
		customer.setIschain(oac.getIschain());
		customer.setCountylevel(oac.getCountylevel());
		customer.setVillagetown(oac.getVillagetown());
		customer.setCredit(oac.getCredit());
		customer.setCreditrating(oac.getCreditrating());
		customer.setReconciliationdate(oac.getReconciliationdate());
		customer.setBillingdate(oac.getBillingdate());
		customer.setArrivalamountdate(oac.getArrivalamountdate());
		customer.setTickettype(oac.getTickettype());
		customer.setCreditdate(oac.getCreditdate());
		customer.setSalesmonth(oac.getSalesmonth());
		customer.setTargetsales(oac.getTargetsales());
		customer.setYearback(oac.getYearback());
		customer.setMonthback(oac.getMonthback());
		customer.setDispatchingamount(oac.getDispatchingamount());
		customer.setSixone(oac.getSixone());
		customer.setSettlement(oac.getSettlement());
		customer.setSettletype(oac.getSettletype());
		customer.setSettleday(oac.getSettleday());
		customer.setPaytype(oac.getPaytype());
		customer.setIscash(oac.getIscash());
		customer.setIslongterm(oac.getIslongterm());
		customer.setBilling(oac.getBilling());
		customer.setBilltype(oac.getBilltype());
		customer.setOvercontrol(oac.getOvercontrol());
		customer.setOvergracedate(oac.getOvergracedate());
		customer.setCanceltype(oac.getCanceltype());
        // 4083 新客户登陆增加销售部门
		customer.setSalesdeptid(oac.getSalesdeptid());
        if(StringUtils.isNotEmpty(oac.getSalesdeptid())) {

            DepartMent dept = getBaseService().getDepartmentByDeptid(oac.getSalesdeptid());
            customer.setSalesdeptname(dept == null ? null : dept.getName());
        }
		customer.setSalesuserid(oac.getSalesuserid());
		customer.setSalesusername(oac.getSalesusername());
		customer.setPricesort(oac.getPricesort());
		customer.setTallyuserid(oac.getTallyuserid());
		customer.setTallyusername(oac.getTallyusername());
		customer.setIndoorstaff(oac.getIndoorstaff());
		customer.setPayeeid(oac.getPayeeid());
		customer.setChecker(oac.getChecker());
		customer.setCheckmobile(oac.getCheckmobile());
		customer.setCheckemail(oac.getCheckemail());
		customer.setPayer(oac.getPayer());
		customer.setPayermobile(oac.getPayermobile());
		customer.setPayeremail(oac.getPayeremail());
		customer.setShopmanager(oac.getShopmanager());
		customer.setShopmanagermobile(oac.getShopmanagermobile());
		customer.setGsreceipter(oac.getGsreceipter());
		customer.setGsreceiptermobile(oac.getGsreceiptermobile());
		customer.setMargin(oac.getMargin());
		customer.setField01(oac.getField01());
		customer.setField02(oac.getField02());
		customer.setField03(oac.getField03());
		customer.setField04(oac.getField04());
		customer.setField05(oac.getField05());
		customer.setField06(oac.getField06());
		customer.setField07(oac.getField07());
		customer.setField08(oac.getField08());
		customer.setField09(oac.getField09());
		customer.setField10(oac.getField10());
		customer.setField11(oac.getField11());
		customer.setField12(oac.getField12());
		customer.setIslast("1");
        // 4040 通用版&鸿都：增加上级客户，手机上也要改，把助记符去掉
        customer.setPid(oac.getPcustomerid());
        // 7309 （通用版和1114.）客户档案，OA新客户登陆
		customer.setShopno(oac.getLicenseno());

		// 登录到客户档案表
		baseService.addBaseCustomerInfo(customer);

		String customerid = oac.getCustomerid();
		List<Map<String, String>> list = oaCustomerMapper.selectPersonListByBillid(process.getId());
		String companies = "";
		String personids = "";
		String delpersonids = "";
		for(Map<String, String> map : list) {
			
			companies = companies + "," +  map.get("belongdeptid");
			personids = personids + "," +  map.get("personid");
		}
		if(companies.startsWith(",")) {
			
			companies = companies.substring(1);
			personids = personids.substring(1);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("customerid", customerid);
		map.put("companys", companies);
		map.put("personids", personids);
		map.put("delpersonids", delpersonids);
		personnelService.addPsnBrandCustomer(map);
	}

	@Override
	public void delete(Process process) throws Exception {
		
		// 当该Process已经被删除时，删除相关的业务数据
		if(processMapper.getProcess(process.getId()) != null) {
			
			String businessId = process.getBusinessid();
			
//			OaCustomer customer = oaCustomerMapper.selectOaCustomer(businessId);
			oaCustomerMapper.deleteOaCustomer(businessId);
			oaCustomerBrandMapper.deleteBrandByBillid(businessId);
//			baseService.deleteAllotCustomerToPsn(customer.getCustomerid());
			oaCustomerMapper.deletePersonListByBillid(process.getId());
		}
	}

}

