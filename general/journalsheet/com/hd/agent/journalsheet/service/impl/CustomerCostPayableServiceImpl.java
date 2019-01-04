/**
 * @(#)CustomerCostPayableServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月12日 chenwei 创建版本
 */
package com.hd.agent.journalsheet.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.dao.BankAmountMapper;
import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.account.service.IBankAmountService;
import com.hd.agent.basefiles.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.journalsheet.dao.CustomerCostPayableMapper;
import com.hd.agent.journalsheet.model.CustomerCostPayable;
import com.hd.agent.journalsheet.service.ICustomerCostPayableService;

/**
 * 
 * 客户应付费用service实现方法
 * 
 * @author chenwei
 */
public class CustomerCostPayableServiceImpl extends BaseFilesServiceImpl
		implements ICustomerCostPayableService {

	private CustomerCostPayableMapper customerCostPayableMapper;

	private IBankAmountService bankAmountService;

	public CustomerCostPayableMapper getCustomerCostPayableMapper() {
		return customerCostPayableMapper;
	}

	public void setCustomerCostPayableMapper(
			CustomerCostPayableMapper customerCostPayableMapper) {
		this.customerCostPayableMapper = customerCostPayableMapper;
	}

	public IBankAmountService getBankAmountService() {
		return bankAmountService;
	}

	public void setBankAmountService(IBankAmountService bankAmountService) {
		this.bankAmountService = bankAmountService;
	}

	@Override
	public PageData showCustomerCostPayableListData(PageMap pageMap)
			throws Exception {

		String begindate = (String) pageMap.getCondition().get("begindate");
		if(StringUtils.isEmpty(begindate)) {
			pageMap.getCondition().put("begindate", "1900-00-00");
		}

		String dataSql = getDataAccessRule("t_base_sales_customer", "t1");
		pageMap.setDataSql(dataSql);
		List<Map> list = customerCostPayableMapper.showCustomerCostPayableListData(pageMap);
		for (Map map : list) {
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
			}
			if(map.containsKey("salesarea")){
				String salesareaid = (String) map.get("salesarea");
				SalesArea salesArea = getSalesareaByID(salesareaid);
				if(null!=salesArea){
					map.put("salesareaname", salesArea.getName());
				}
			}
			if(map.containsKey("salesuserid")){
				String salesuserid = (String) map.get("salesuserid");
				Personnel personnel = getPersonnelById(salesuserid);
				if(null!=personnel){
					map.put("salesusername", personnel.getName());
				}
			}
			if(map.containsKey("salesdeptid")){
				String salesdeptid = (String) map.get("salesdeptid");
				DepartMent departMent = getDepartmentByDeptid(salesdeptid);
				if(null!=departMent){
					map.put("salesdeptname", departMent.getName());
				}
			}
		}
		PageData pageData = new PageData(customerCostPayableMapper.showCustomerCostPayableListCount(pageMap), list, pageMap);
		List footerList = new ArrayList();
		Map map = customerCostPayableMapper.showCustomerCostPayableSumData(pageMap);
		if(null!=map){
			map.put("customerid", "合计");
			footerList.add(map);
		}
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showCustomerCostPayableDetailList(PageMap pageMap)
			throws Exception {
		Map condition=pageMap.getCondition();
        String isExportData=(String)condition.get("isExportData");
        if("true".equals(isExportData)){
            condition.put("isPageflag", "true");
        }
        String isPcustomer = (String)condition.get("isPcustomer");
        if(StringUtils.isEmpty(isPcustomer)){
        	isPcustomer = "1";
		}
		String customerid = (String)condition.get("customerid");
		BigDecimal beginAmount = BigDecimal.ZERO;
        int i =0 ;
		List<CustomerCostPayable> list = customerCostPayableMapper.showCustomerCostPayableDetailList(pageMap);
		for(CustomerCostPayable customerCostPayable: list){
			DepartMent departMent = null;
			if(StringUtils.isNotEmpty(customerCostPayable.getExpensesort())){
				ExpensesSort expensesSort = getExpensesSortByID(customerCostPayable.getExpensesort());
				if(null!=expensesSort){
					customerCostPayable.setExpensesortname(expensesSort.getName());
				}
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getApplyuserid())){
				SysUser sysUser = getSysUserById(customerCostPayable.getApplyuserid());
				if(null!=sysUser){
					customerCostPayable.setApplyusername(sysUser.getName());
				}
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getApplydeptid())){
				departMent = getDepartmentByDeptid(customerCostPayable.getApplydeptid());
				if(null!=departMent){
					customerCostPayable.setApplydeptname(departMent.getName());
				}
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
				BuySupplier buySupplier = getSupplierInfoById(customerCostPayable.getSupplierid());
				if(null!=buySupplier){
					customerCostPayable.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(customerCostPayable.getCustomerid())){
				Customer customer = getCustomerByID(customerCostPayable.getCustomerid());
				if(null!=customer){
					customerCostPayable.setCustomername(customer.getName());
				}
			}

			if(StringUtils.isNotEmpty(customerCostPayable.getDeptid())){
				departMent = getDepartmentByDeptid(customerCostPayable.getDeptid());
				if(null!=departMent){
					customerCostPayable.setDeptname(departMent.getName());
				}
			}
			
			if("1".equals(customerCostPayable.getHcflag()) || "2".equals(customerCostPayable.getHcflag())){
				customerCostPayable.setHcflagname("是");
			}else{
				customerCostPayable.setHcflagname("否");
			}
			if("1".equals(customerCostPayable.getIsbegin())){
				customerCostPayable.setIsbeginname("是");
			}else{
				customerCostPayable.setIsbeginname("否");
			}
			
			if("1".equals(customerCostPayable.getBilltype())){
				customerCostPayable.setBilltypename("借");
			}else if("2".equals(customerCostPayable.getBilltype())){
				customerCostPayable.setBilltypename("贷");
			}
			if("1".equals(customerCostPayable.getPaytype())){
				customerCostPayable.setPaytypename("支付");
			}else if("2".equals(customerCostPayable.getPaytype())){
				customerCostPayable.setPaytypename("冲差");
			}
			if("0".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("手工录入");
			}else if("1".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("代垫");
			}else if("11".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("费用冲差支付单");
			}else if("12".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("通路单");
			}else if("13".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("客户费用申请单");
			}else if("14".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("客户费用批量支付申请单");
			}else if("15".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("客户费用申请单（账扣）");
			}else if("17".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("品牌费用申请单（支付）");
			}else if("18".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("品牌费用申请单（支付）");
			}else if("19".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("费用冲差支付单");
			}else if("20".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("代垫费用申请单");
			}else if("21".equals(customerCostPayable.getSourcefrom())){
				customerCostPayable.setSourcefromname("代垫费用申请单");
			}

			if(i==0){
				String businessdate = customerCostPayable.getBusinessdate();
				String id = customerCostPayable.getId();
				Map ret = customerCostPayableMapper.showCustomerCostPayableBeginEnd(id, customerid, businessdate,isPcustomer);
				customerCostPayable.setBeginamount(BigDecimal.ZERO);
				if(ret.containsKey("beginamount")) {
					beginAmount = (BigDecimal)ret.get("beginamount");
				}
			}
			i ++ ;
			customerCostPayable.setBeginamount(beginAmount);
			customerCostPayable.setEndamount(customerCostPayable.getBeginamount().add(customerCostPayable.getLendamount()).subtract(customerCostPayable.getPayamount()));
			beginAmount = customerCostPayable.getEndamount();
			//			if("2".equals(customerCostPayable.getBilltype())) {
//				customerCostPayable.setEndamount(customerCostPayable.getBeginamount().setScale(6).subtract(customerCostPayable.getAmount()));
//			} else {
//				customerCostPayable.setEndamount(customerCostPayable.getBeginamount().setScale(6).add(customerCostPayable.getAmount()));
//			}

			if(StringUtils.isNotEmpty(customerCostPayable.getBankid())) {
				Bank bank = getBankInfoByID(customerCostPayable.getBankid());
				if (bank != null) {
					customerCostPayable.setBankname(bank.getName());
				}
			}
		}
		int icount=0;
		if(!"true".equals(isExportData)){
			icount=customerCostPayableMapper.showCustomerCostPayableDetailCount(pageMap);
		}
		PageData pageData = new PageData(icount, list, pageMap);
		List footerList = new ArrayList();
		CustomerCostPayable customerCostPayable = customerCostPayableMapper.showCustomerCostPayableDetailSum(pageMap);
		if(null!=customerCostPayable){
			customerCostPayable.setOaid("合计");
			// customerCostPayable.setApplyusername("余额");
            customerCostPayable.setRelateoaid("余额");
			customerCostPayable.setApplyusername(customerCostPayable.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            footerList.add(customerCostPayable);
		}
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public boolean addCustomerCostPayableInit(
			CustomerCostPayable customerCostPayable) throws Exception {
		if(null==customerCostPayable){
			return false;
		}
		if (isAutoCreate("t_js_customercost_payable")) {
			// 获取自动编号
			customerCostPayable.setId(getAutoCreateSysNumbderForeign(customerCostPayable, "t_js_customercost_payable"));
		}else{
			customerCostPayable.setId("FY-"+CommonUtils.getDataNumber());
		}
		SysUser sysUser = getSysUser();
		customerCostPayable.setApplyuserid(sysUser.getUserid());
		customerCostPayable.setApplydeptid(sysUser.getDepartmentid());
		Customer customer = getCustomerByID(customerCostPayable.getCustomerid());
		if(null!=customer){
			customerCostPayable.setPcustomerid(customer.getPid());
			if("0".equals(customer.getIslast())){
				customerCostPayable.setPcustomerid(customerCostPayable.getCustomerid());
			}
		}
		customerCostPayable.setIspay("0");
		customerCostPayable.setBilltype("1");
		customerCostPayable.setIsbegin("1");
		customerCostPayable.setSourcefrom("0"); //手工录入
		int i = customerCostPayableMapper.addCustomerCostPayable(customerCostPayable);
		if(i>0){
			return true;
		}
		return false;
	}

	@Override
	public PageData showCustomerCostPayableInitList(PageMap pageMap)
			throws Exception {
		List<CustomerCostPayable> list = customerCostPayableMapper.showCustomerCostPayableInitList(pageMap);
		for(CustomerCostPayable customerCostPayable: list){
			Customer customer = getCustomerByID(customerCostPayable.getCustomerid());
			if(null!=customer){
				customerCostPayable.setCustomername(customer.getName());
				SalesArea salesArea = getSalesareaByID(customer.getSalesarea());
				if(null!=salesArea){
					customerCostPayable.setSalesareaname(salesArea.getName());
				}
			}
			ExpensesSort expensesSort = getExpensesSortByID(customerCostPayable.getExpensesort());
			if(null!=expensesSort){
				customerCostPayable.setExpensesortname(expensesSort.getName());
			}
			SysUser sysUser = getSysUserById(customerCostPayable.getApplyuserid());
			if(null!=sysUser){
				customerCostPayable.setApplyusername(sysUser.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(customerCostPayable.getApplydeptid());
			if(null!=departMent){
				customerCostPayable.setApplydeptname(departMent.getName());
			}
			BuySupplier buySupplier = getSupplierInfoById(customerCostPayable.getSupplierid());
			if(null!=buySupplier){
				customerCostPayable.setSuppliername(buySupplier.getName());
			}
		}
		PageData pageData = new PageData(customerCostPayableMapper.showCustomerCostPayableInitCount(pageMap), list, pageMap);
		Map map = customerCostPayableMapper.showCustomerCostPayableInitSum(pageMap);
		if(null!=map){
			map.put("businessdate", "合计");
			List footerList = new ArrayList();
			footerList.add(map);
			pageData.setFooter(footerList);
		}
		return pageData;
	}

	@Override
	public CustomerCostPayable getCustomerCostPayableByID(String id)
			throws Exception {
		CustomerCostPayable customerCostPayable = customerCostPayableMapper.getCustomerCostPayableByID(id);
		if(null!=customerCostPayable){
			if(StringUtils.isNotEmpty(customerCostPayable.getCustomerid())){
				Customer customer = getCustomerByID(customerCostPayable.getCustomerid());
				if(null!=customer){
					customerCostPayable.setCustomername(customer.getName());
				}
			}

			if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
				BuySupplier buySupplier = getSupplierInfoById(customerCostPayable.getSupplierid());
				if(null!=buySupplier){
					customerCostPayable.setSuppliername(buySupplier.getName());
				}
			}
		}
		return customerCostPayable;
	}

	@Override
	public boolean editCustomerCostPayableInit(
			CustomerCostPayable customerCostPayable) throws Exception {
		int i = customerCostPayableMapper.editCustomerCostPayableInit(customerCostPayable);
		return i>0;
	}

	@Override
	public boolean deleteCustomerCostPayableInit(String id) throws Exception {
		CustomerCostPayable customerCostPayable = customerCostPayableMapper.getCustomerCostPayableByID(id);
		if(null!=customerCostPayable && "1".equals(customerCostPayable.getIsbegin())){
			int i = customerCostPayableMapper.deleteCustomerCostPayableByID(id);
			return i>0;
		}
		return false;
	}

	@Override
	public Map addCustomerCostPayableInitList(List<Map> list) throws Exception {
		Map msgmap = new HashMap();
		boolean flag = false;
		int successNum = 0,failureNum = 0,errorNum = 0;
		StringBuilder successSb = new StringBuilder();
		StringBuilder failSb = new StringBuilder();
		StringBuilder errorSb  = new StringBuilder();
		SysUser sysUser = getSysUser();
		boolean isId=isAutoCreate("t_js_customercost_payable");
		if(null != list && list.size() != 0){
			List<ExpensesSort> subjectCodelist = getBaseFinanceMapper().getExpensesSortByStateList();
			CustomerCostPayable customerCostPayable=null;
			for(Map map : list){
				try{
					if(null==map){
						continue;
					}
					customerCostPayable=new CustomerCostPayable();
					customerCostPayable.setBusinessdate((String) map.get("businessdate"));
					if(StringUtils.isEmpty(customerCostPayable.getBusinessdate())){
						customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
					}
					customerCostPayable.setOaid((String)map.get("oaid"));
					
					customerCostPayable.setRemark((String)map.get("remark"));
					String tmpd=(String)map.get("amount");
					if(StringUtils.isNotEmpty(tmpd) && CommonUtils.isNumStr(tmpd)){
						customerCostPayable.setAmount(new BigDecimal(tmpd));
					}else{
						customerCostPayable.setAmount(BigDecimal.ZERO);
					}
					customerCostPayable.setCustomerid((String)map.get("customerid"));
					customerCostPayable.setCustomername((String)map.get("customername"));
					customerCostPayable.setExpensesortname((String)map.get("expensesortname"));
						
					if(StringUtils.isNotEmpty(customerCostPayable.getExpensesortname())){
						if(subjectCodelist.size() != 0){
							for(ExpensesSort expensesSort:subjectCodelist){
								if(StringUtils.isNotEmpty(expensesSort.getThisname()) && expensesSort.getThisname().trim().equals(customerCostPayable.getExpensesortname())){
									customerCostPayable.setExpensesort(expensesSort.getId());
									break;
								}
							}
						}
					}
					
					customerCostPayable.setBilltype("1");
					customerCostPayable.setIsbegin("1");
					
					customerCostPayable.setApplyuserid(sysUser.getUserid());
					customerCostPayable.setApplyusername(sysUser.getName());
					customerCostPayable.setApplydeptid(sysUser.getDepartmentid());
					customerCostPayable.setApplydeptname(sysUser.getDepartmentname());
					if (isId) {
						// 获取自动编号
						customerCostPayable.setId(getAutoCreateSysNumbderForeign(customerCostPayable, "t_js_customercost_payable"));
					}else{
						customerCostPayable.setId("FY-"+CommonUtils.getDataNumber());
					}
					//添加记录
					if(customerCostPayableMapper.addCustomerCostPayable(customerCostPayable)> 0){
						successNum=successNum+1;
						if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
							successSb.append(" 业务日期："+customerCostPayable.getBusinessdate());
						}
						if(StringUtils.isNotEmpty(customerCostPayable.getCustomerid())){
							successSb.append(" 客户编号："+customerCostPayable.getCustomerid());
						}	
						if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
							successSb.append(" 费用分类："+customerCostPayable.getExpensesortname());
						}
						successSb.append(" 金额："+customerCostPayable.getAmount().toString());
					}else{
						failureNum=failureNum+1;
						if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
							failSb.append(" 业务日期："+customerCostPayable.getBusinessdate());
						}
						if(StringUtils.isNotEmpty(customerCostPayable.getCustomerid())){
							failSb.append(" 客户编号："+customerCostPayable.getCustomerid());
						}	
						if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
							failSb.append(" 费用分类："+customerCostPayable.getExpensesortname());
						}
					}
				}catch (Exception e) {
					errorNum=errorNum+1;
					if(null==customerCostPayable){
						continue;
					}
					if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
						errorSb.append(" 业务日期："+customerCostPayable.getBusinessdate());
					}
					if(StringUtils.isNotEmpty(customerCostPayable.getCustomerid())){
						errorSb.append(" 客户编号："+customerCostPayable.getCustomerid());
					}	
					if(StringUtils.isNotEmpty(customerCostPayable.getBusinessdate())){
						errorSb.append(" 费用分类："+customerCostPayable.getExpensesortname());
					}
				}
			}
		}
		else{
			msgmap.put("msg", "导入的数据为空!");
		}
		if(successNum>0){
			msgmap.put("flag", true);
		}else{
			msgmap.put("flag", false);			
		}
		if(successNum==0 && failureNum==0 && errorNum==0 && list.size()>0){
			msgmap.put("msg", "导入的数据操失败!");
		}
		msgmap.put("success", successNum);
		msgmap.put("successMsg", successSb.toString());
		msgmap.put("failure", failureNum);
		msgmap.put("failStr", failSb.toString());
		msgmap.put("errorNum", errorNum);
		msgmap.put("errorVal", errorSb.toString());
		return msgmap;
	}
	
	/**
	 * 添加客户应付费用
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月4日
	 */
	public boolean addCustomerCostPayable(CustomerCostPayable customerCostPayable) throws Exception{
		if (isAutoCreate("t_js_customercost_payable")) {
            // 获取自动编号
            customerCostPayable.setId(getAutoCreateSysNumbderForeign(customerCostPayable, "t_js_customercost_payable"));
        }else{
            customerCostPayable.setId("FY-"+CommonUtils.getDataNumber());
        }
		boolean flag=false;
		customerCostPayable.setSourcefrom("0");//手工添加
		customerCostPayable.setIspay("0");//是否付款1是0否
    	customerCostPayable.setHcflag("0"); //非红冲
    	customerCostPayable.setIsbegin("0"); //期初数据
    	Customer customer = getCustomerByID(customerCostPayable.getCustomerid());
		if(null!=customer){
			customerCostPayable.setPcustomerid(customer.getPid());
			if("0".equals(customer.getIslast())){
				customerCostPayable.setPcustomerid(customerCostPayable.getCustomerid());
			}
		}
		SysUser sysUser=getSysUser();
		customerCostPayable.setApplyuserid(sysUser.getUserid());
        customerCostPayable.setApplydeptid(sysUser.getDepartmentid());
        
        if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
        	if(StringUtils.isEmpty(customerCostPayable.getDeptid())){
        		BuySupplier buySupplier=getSupplierInfoById(customerCostPayable.getSupplierid());
        		if(null!=buySupplier){
                	customerCostPayable.setDeptid(buySupplier.getBuydeptid());
        		}else{
                	customerCostPayable.setDeptid("");        			
        		}
        	}
        }else{
        	customerCostPayable.setSupplierid("");
        	customerCostPayable.setDeptid("");
        }
        
		flag = customerCostPayableMapper.addCustomerCostPayable(customerCostPayable)>0;
        if(flag) {
			addBankAmountByCustomerCostPayable(customerCostPayable, true);
        }
		return flag;
	}

	/**
	 * 添加客户应付费用
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月4日
	 */
	public Map editCustomerCostPayable(CustomerCostPayable customerCostPayable) throws Exception{
		Map resultMap=new HashMap();
		boolean flag=false;
		
		CustomerCostPayable oldData=customerCostPayableMapper.getCustomerCostPayableByID(customerCostPayable.getId());
		if(null==oldData){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关客户应付费用");
			return resultMap;
		}
		if(!"0".equals(oldData.getSourcefrom())){
			resultMap.put("flag", false);
			resultMap.put("msg", "手工添加的数据才能修改");
			return resultMap;
		}
		if(!"0".equals(oldData.getIsbegin())){
			resultMap.put("flag", false);
			resultMap.put("msg", "期初数据不能修改");
			return resultMap;
		}
		if("1".equals(oldData.getHcflag()) || "2".equals(oldData.getHcflag())){
			resultMap.put("flag", false);
			resultMap.put("msg", "红冲的客户应付费用不能修改");
			return resultMap;
		}

		CustomerCostPayable oldCustomerCostPayable = customerCostPayableMapper.getCustomerCostPayableByID(customerCostPayable.getId());

		customerCostPayable.setSourcefrom("0");//手工添加
		customerCostPayable.setIspay("0");//是否付款1是0否
    	Customer customer = getCustomerByID(customerCostPayable.getCustomerid());
		if(null!=customer){
			customerCostPayable.setPcustomerid(customer.getPid());
			if("0".equals(customer.getIslast())){
				customerCostPayable.setPcustomerid(customerCostPayable.getCustomerid());
			}
		}
        
        if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
        	if(StringUtils.isEmpty(customerCostPayable.getDeptid())){
        		BuySupplier buySupplier=getSupplierInfoById(customerCostPayable.getSupplierid());
        		if(null!=buySupplier){
                	customerCostPayable.setDeptid(buySupplier.getBuydeptid());
        		}else{
                	customerCostPayable.setDeptid("");        			
        		}
        	}
        }else{
        	customerCostPayable.setSupplierid("");
        	customerCostPayable.setDeptid("");
        }
        
		flag=customerCostPayableMapper.updateCustomerCostPayable(customerCostPayable)>0;
        if(flag) {
			addBankAmountByCustomerCostPayable(oldCustomerCostPayable, false);
			addBankAmountByCustomerCostPayable(customerCostPayable, true);
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	/**
	 * 新增代垫红冲
	 * @param customerCostPayable
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map addCustomerCostPayableHC(CustomerCostPayable customerCostPayable)throws Exception{
		 Map resultMap=new HashMap();
    	 
    	 if(StringUtils.isEmpty(customerCostPayable.getHcreferid())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "未能找到关联的费用");
 			 return resultMap;
    	 }
    	 
    	 CustomerCostPayable refCustomerCostPayable=customerCostPayableMapper.getCustomerCostPayableByID(customerCostPayable.getHcreferid());
    	 
    	 if(null==refCustomerCostPayable){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "未能找到关联的费用");
 			 return resultMap;    		 
    	 }
    	 if(!"2".equals(refCustomerCostPayable.getBilltype())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "单据类型为贷的客户应付费用才能被红冲");
 			 return resultMap;    		 
    	 }
    	 if("1".equals(refCustomerCostPayable.getIsbegin())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "期初数据不能进行红冲");
 			 return resultMap;    		 
    	 }
    	 if("11".equals(refCustomerCostPayable.getSourcefrom())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "来源单据类型为费用冲差支付单不能进行红冲");
 			 return resultMap;    		 
    	 }
    	 
    	 if("1".equals(refCustomerCostPayable.getHcflag()) || "2".equals(refCustomerCostPayable.getHcflag())){
    		 resultMap.put("flag","false");
    		 resultMap.put("msg", "此单据已经被红冲");
 			 return resultMap;    		 
    	 }

    	 if (isAutoCreate("t_js_customercost_payable")) {
 			// 获取自动编号
 			customerCostPayable.setId(getAutoCreateSysNumbderForeign(customerCostPayable, "t_js_customercost_payable"));
 		 }else{
 			customerCostPayable.setId("HC-"+CommonUtils.getDataNumber());
 		 }

    	 customerCostPayable.setSourcefrom("0");//手工添加
    	 customerCostPayable.setIspay(refCustomerCostPayable.getIspay());//是否付款1是0否
 		
    	 customerCostPayable.setOaid(refCustomerCostPayable.getOaid());
    	 customerCostPayable.setRelateoaid(refCustomerCostPayable.getRelateoaid());
    	 customerCostPayable.setCustomerid(refCustomerCostPayable.getCustomerid());
    	 customerCostPayable.setPcustomerid(refCustomerCostPayable.getPcustomerid());
    	 customerCostPayable.setSupplierid(refCustomerCostPayable.getSupplierid());
    	 customerCostPayable.setDeptid(refCustomerCostPayable.getDeptid());
    	 customerCostPayable.setExpensesort(refCustomerCostPayable.getExpensesort());
    	 customerCostPayable.setAmount(refCustomerCostPayable.getAmount().negate());

    	 SysUser sysUser = getSysUser();
    	 customerCostPayable.setApplyuserid(sysUser.getUserid());
    	 customerCostPayable.setApplydeptid(sysUser.getDepartmentid());
		
    	 customerCostPayable.setBilltype(refCustomerCostPayable.getBilltype());
    	 customerCostPayable.setBillno(null);//这个是来源单据号

    	 customerCostPayable.setAddtime(new Date());
    	 customerCostPayable.setHcflag("1"); //红冲
    	 customerCostPayable.setHcreferid(refCustomerCostPayable.getId());
    	 customerCostPayable.setBankid(refCustomerCostPayable.getBankid());
		
    	 boolean flag= customerCostPayableMapper.addCustomerCostPayable(customerCostPayable) > 0;
		
    	 if(flag){
			 // 生成相反的借贷单
			 CustomerCostPayable oldCustomerCostPayable = (CustomerCostPayable) CommonUtils.deepCopy(refCustomerCostPayable);
			 oldCustomerCostPayable.setId(customerCostPayable.getId());
			 oldCustomerCostPayable.setPaytype("1");
			 addBankAmountByCustomerCostPayable(oldCustomerCostPayable, false);
			 //红冲添加成功,原单据更改红冲状态
			 CustomerCostPayable upCustomerCostPayable=new CustomerCostPayable();
			 upCustomerCostPayable.setId(refCustomerCostPayable.getId());
			 upCustomerCostPayable.setHcflag("2");	//原单据红冲状态，为红冲
			 //原单据核销
			 flag=customerCostPayableMapper.updateCustomerCostPayable(upCustomerCostPayable)>0;
			 if(!flag){
				 //原单据未能更新成功，回滚~~
				 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				 
				 resultMap.put("flag",false);
				 return resultMap; 
			 }
    	 }
    	 resultMap.put("flag",flag);
    	 return resultMap; 
	}

	/**
	 * 撤销代垫红冲
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-26
	 */
	public Map removeCustomerCostPayableHC(String id)throws Exception{
		Map resultMap=new HashMap();
		Map queryMap=new HashMap();
    	if(null==id || "".equals(id.trim())){
      		 resultMap.put("flag","false");
      		 resultMap.put("msg", "未能找到关联的客户应付费用");
   			 return resultMap;    
    	}
    	CustomerCostPayable oldData=customerCostPayableMapper.getCustomerCostPayableByID(id.trim());
    	if(null==oldData){
     		 resultMap.put("flag","false");
     		 resultMap.put("msg", "未能找到关联的客户应付费用");
  			 return resultMap;        		
    	}
    	if(!"1".equals(oldData.getHcflag()) && !"2".equals(oldData.getHcflag())){
   		 	resultMap.put("flag","false");
   		 	resultMap.put("msg", "请选择相应的红冲单据");
			 return resultMap;     
    	}
    	
	   	if(!"2".equals(oldData.getBilltype())){
	   		 resultMap.put("flag","false");
	   		 resultMap.put("msg", "单据类型为贷的客户应付费用才能撤销红冲");
			 return resultMap;    		 
	   	}
    	CustomerCostPayable refMatData=null;
    	CustomerCostPayable hcMatData=null;
    	if("1".equals(oldData.getHcflag())){
    		hcMatData=oldData;
    		refMatData=customerCostPayableMapper.getCustomerCostPayableByID(oldData.getHcreferid());
    		if (null==refMatData) {
    			 resultMap.put("flag","false");
        		 resultMap.put("msg", "未能找到红冲对应的客户付费用");
     			 return resultMap;   
			}
    	}else{
    		refMatData=oldData;
    		queryMap.put("hcreferid", oldData.getId());
    		queryMap.put("hcflag", "1");//红冲
    		hcMatData=customerCostPayableMapper.getCustomerCostPayableByMap(queryMap);
    		if(null==hcMatData){
	   			 resultMap.put("flag","false");
	    		 resultMap.put("msg", "未能找到对应的红冲");
	 			 return resultMap;       			
    		}
    	}
    	
    	CustomerCostPayable upCustomerCostPayable=new CustomerCostPayable();
    	upCustomerCostPayable.setId(refMatData.getId());
		upCustomerCostPayable.setHcflag("0");	//原单据红冲状态，非红冲
		 //原单据核销
		boolean flag=customerCostPayableMapper.updateCustomerCostPayable(upCustomerCostPayable)>0;
		if(flag){
			CustomerCostPayable oldCustomerCostPayable = (CustomerCostPayable) CommonUtils.deepCopy(hcMatData);
			flag=customerCostPayableMapper.deleteCustomerCostPayableByID(hcMatData.getId())>0;			
			if(!flag){
				 //原单据未能删除成功，回滚~~
				 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				 
	 			 resultMap.put("flag",false);
	 			 return resultMap; 
			}
			oldCustomerCostPayable.setAmount(oldCustomerCostPayable.getAmount().negate());
			addBankAmountByCustomerCostPayable(oldCustomerCostPayable, true);
		}

		resultMap.put("flag",flag);
		return resultMap; 
	}
	/**
	 * 删除客户应付费用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月4日
	 */
	public boolean deleteCustomerCostPayable(String id) throws Exception{
		Map delMap=new HashMap();
        String dataSql = getDataAccessRule("t_js_customercost_payable",null); //数据权限
        if(null!=dataSql && !"".equals(dataSql.trim())){
        	delMap.put("dataAuthSql", dataSql);
        }
        if(null==id || "".equals(id.trim())){
        	return false;
        }

		CustomerCostPayable oldCustomerCostPayable = customerCostPayableMapper.getCustomerCostPayableByID(id);

		delMap.put("id", id);
        delMap.put("candelete", "1");	// 可删除来源
        boolean flag=customerCostPayableMapper.deleteCustomerCostPayableByMap(delMap) > 0;
        if(flag) {
        	addBankAmountByCustomerCostPayable(oldCustomerCostPayable, false);
		}
        
        return flag;
	}
	/**
	 * 批量删除客户应付费用
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月4日
	 */
	public Map deleteCustomerCostPayableMore(String idarrs) throws Exception{
		Map map=new HashMap();
        int iSuccess=0;
        int iFailure=0;
        int iNohandle=0;

        if(null==idarrs || "".equals(idarrs.trim())){

            map.put("flag", false);
            map.put("isuccess", iSuccess);
            map.put("ifailure", iFailure);
            return map;
        }
        String[] idarr=idarrs.trim().split(",");
        String dataSql = getDataAccessRule("t_js_customercost_payable",null); //数据权限
        Map delMap=new HashMap();
        if(null!=dataSql && !"".equals(dataSql.trim())){
            delMap.put("dataAuthSql", dataSql);
        }
        delMap.put("candelete", "1");	// 可删除来源
        for(String id : idarr){
            if(null==id || "".equals(id.trim())){
                iFailure=iFailure+1;
                continue;
            }
            delMap.put("id", id);

			CustomerCostPayable oldCustomerCostPayable = customerCostPayableMapper.getCustomerCostPayableByID(id);
            if(customerCostPayableMapper.deleteCustomerCostPayableByMap(delMap)>0){
				addBankAmountByCustomerCostPayable(oldCustomerCostPayable, false);
                iSuccess=iSuccess+1;
            }else{
                iFailure=iFailure+1;
            }
        }
        map.clear();
        if(iSuccess>0){
            map.put("flag", true);
        }else{
            map.put("flag", false);
        }
        map.put("isuccess", iSuccess);
        map.put("ifailure", iFailure);
        return map;
	}

	@Override
	public Map addDRCustomerCostPayable(List<Map> list) throws Exception {
		Map resultMap = new HashMap();
		boolean flag = false;
		int successNum = 0;
		int failureNum = 0;
		int repeatNum = 0;
		int closeNum = 0;
		int errorNum = 0;
		int levelNum=0;
		List<CustomerCostPayable> errorDataList=new ArrayList<CustomerCostPayable>();
		SysUser sysUser = getSysUser();
		String tmpStr="";
		boolean isId=isAutoCreate("t_js_customercost_payable");
		if(null != list && list.size() != 0){
			CustomerCostPayable customerCostPayable=null;
			List<ExpensesSort> subjectCodelist = getBaseFinanceMapper().getExpensesSortByStateList();
			for(Map dataMap : list){
				try{
					flag=false;
					customerCostPayable=new CustomerCostPayable();
					customerCostPayable.setSourcefrom("0");//手工添加
					customerCostPayable.setIspay("0");//是否付款1是0否
					customerCostPayable.setHcflag("0"); //非红冲
					customerCostPayable.setIsbegin("0"); //期初数据
					customerCostPayable.setBilltype("2"); //单据类型为贷
					if(dataMap.containsKey("oaid")) {
						tmpStr = (String) dataMap.get("oaid");
						customerCostPayable.setOaid(tmpStr);
					}
					if(dataMap.containsKey("businessdate")) {
						tmpStr = (String) dataMap.get("businessdate");
						customerCostPayable.setBusinessdate(tmpStr);
					}
					if(StringUtils.isEmpty(customerCostPayable.getBusinessdate())){
						customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
					}
					if(dataMap.containsKey("customerid")) {
						tmpStr = (String) dataMap.get("customerid");
						customerCostPayable.setCustomerid(tmpStr);
					}
					if(dataMap.containsKey("customername")) {
						tmpStr = (String) dataMap.get("customername");
						customerCostPayable.setCustomername(tmpStr);
					}
					if(dataMap.containsKey("expensesortname")) {
						tmpStr = (String) dataMap.get("expensesortname");
						customerCostPayable.setExpensesortname(tmpStr);
					}
					if(dataMap.containsKey("supplierid")) {
						tmpStr = (String) dataMap.get("supplierid");
						customerCostPayable.setSupplierid(tmpStr);
					}
					if(dataMap.containsKey("suppliername")) {
						tmpStr = (String) dataMap.get("suppliername");
						customerCostPayable.setSuppliername(tmpStr);
					}
					if(dataMap.containsKey("billtypename")) {
						tmpStr = (String) dataMap.get("billtypename");
						customerCostPayable.setBilltypename(tmpStr);
					}
					if(dataMap.containsKey("paytypename")) {
						tmpStr = (String) dataMap.get("paytypename");
						customerCostPayable.setPaytypename(tmpStr);
					}
					// 银行
					if(dataMap.containsKey("bankid")) {
						tmpStr = (String) dataMap.get("bankid");
						customerCostPayable.setBankid(tmpStr);
					}

					BigDecimal lendAmount=BigDecimal.ZERO;
					BigDecimal payAmount=BigDecimal.ZERO;
					BigDecimal amount=BigDecimal.ZERO;

					if(dataMap.containsKey("lendamount")) {
						tmpStr = (String) dataMap.get("lendamount");
						if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
							lendAmount=new BigDecimal(tmpStr);
						}
					}
					if(dataMap.containsKey("payamount")) {
						tmpStr = (String) dataMap.get("payamount");
						if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
							payAmount=new BigDecimal(tmpStr);
						}
					}
					if(dataMap.containsKey("amount")) {
						tmpStr = (String) dataMap.get("amount");
						if (StringUtils.isNotEmpty(tmpStr) && CommonUtils.isNumStr(tmpStr)) {
							amount=new BigDecimal(tmpStr);
						}
					}
					if (dataMap.containsKey("remark")) {
						tmpStr = (String) dataMap.get("remark");
						customerCostPayable.setRemark(tmpStr);
					}

					if(StringUtils.isEmpty(customerCostPayable.getCustomerid())){
						failureNum=failureNum+1;

						customerCostPayable.setErrormessage("客户编号不能为空");
						errorDataList.add(customerCostPayable);
						continue;
					}

					if(StringUtils.isEmpty(customerCostPayable.getBilltypename())){
						failureNum=failureNum+1;
						customerCostPayable.setErrormessage("单据类型值为空");
						errorDataList.add(customerCostPayable);
						continue;
					}
					if(BigDecimal.ZERO.compareTo(payAmount)==0){
						if(!"贷".equals(customerCostPayable.getBilltypename())){
							failureNum=failureNum+1;
							customerCostPayable.setErrormessage("单据类型必须为贷");
							errorDataList.add(customerCostPayable);
							continue;
						}
						if(BigDecimal.ZERO.compareTo(amount)==0){
							failureNum=failureNum+1;
							customerCostPayable.setErrormessage("费用金额不能为空");
							errorDataList.add(customerCostPayable);
							continue;
						}
					}else{
						amount=payAmount;
					}
					customerCostPayable.setAmount(amount);

					if(StringUtils.isNotEmpty(customerCostPayable.getExpensesortname())){
						if(subjectCodelist.size() != 0){
							for(ExpensesSort expensesSort:subjectCodelist){
								if(StringUtils.isNotEmpty(expensesSort.getThisname())
										&& expensesSort.getThisname().toUpperCase().trim().equals(customerCostPayable.getExpensesortname().toUpperCase())){
									customerCostPayable.setExpensesort(expensesSort.getId());
									break;
								}
							}
						}
					}

					if(StringUtils.isNotEmpty(customerCostPayable.getExpensesortname())
							&& StringUtils.isEmpty(customerCostPayable.getExpensesort())){
						failureNum=failureNum+1;
						customerCostPayable.setErrormessage("系统未能找到对应费用类别");
						errorDataList.add(customerCostPayable);
						continue;
					}
					Customer customer = getCustomerByID(customerCostPayable.getCustomerid());
					if(null!=customer){
						customerCostPayable.setPcustomerid(customer.getPid());
						if("0".equals(customer.getIslast())){
							customerCostPayable.setPcustomerid(customerCostPayable.getCustomerid());
						}
					}else{
						failureNum=failureNum+1;
						customerCostPayable.setErrormessage("系统未能找到对应客户信息");
						errorDataList.add(customerCostPayable);
						continue;

					}

					customerCostPayable.setApplyuserid(sysUser.getUserid());
					customerCostPayable.setApplydeptid(sysUser.getDepartmentid());

					if(StringUtils.isNotEmpty(customerCostPayable.getSupplierid())){
						if(StringUtils.isEmpty(customerCostPayable.getDeptid())){
							BuySupplier buySupplier=getSupplierInfoById(customerCostPayable.getSupplierid());
							if(null!=buySupplier){
								customerCostPayable.setDeptid(buySupplier.getBuydeptid());
							}else{
								failureNum=failureNum+1;
								customerCostPayable.setErrormessage("系统未能找到对应供应商信息");
								errorDataList.add(customerCostPayable);
								continue;
							}
						}
					}else{
						customerCostPayable.setSupplierid("");
						customerCostPayable.setDeptid("");
					}

					if(StringUtils.isEmpty(customerCostPayable.getPaytypename())){
						failureNum=failureNum+1;
						customerCostPayable.setErrormessage("请填写支付类型（支付 或 冲差）");
						errorDataList.add(customerCostPayable);
						continue;
					}

					if(!"支付".equals(customerCostPayable.getPaytypename()) && !"冲差".equals(customerCostPayable.getPaytypename())){
						failureNum=failureNum+1;
						customerCostPayable.setErrormessage("请认真填写支付类型（支付 或 冲差）");
						errorDataList.add(customerCostPayable);
						continue;
					}
					if("支付".equals(customerCostPayable.getPaytypename())){
						customerCostPayable.setPaytype("1");
					}else if("冲差".equals(customerCostPayable.getPaytypename())){
						customerCostPayable.setPaytype("2");
						customerCostPayable.setBankid("");
					}

					if(StringUtils.isNotEmpty(customerCostPayable.getBankid())) {
						Bank bank = getBankInfoByID(customerCostPayable.getBankid());
						if (bank != null) {
							customerCostPayable.setBankname(bank.getName());
						}
					}

					if (isId) {
						// 获取自动编号
						customerCostPayable.setId(getAutoCreateSysNumbderForeign(customerCostPayable, "t_js_customercost_payable"));
					}else{
						customerCostPayable.setId("FY-"+CommonUtils.getDataNumber());
					}
					flag=customerCostPayableMapper.addCustomerCostPayable(customerCostPayable)>0;
					if(flag){
						successNum=successNum+1;
						addBankAmountByCustomerCostPayable(customerCostPayable, true);
					}else{
						failureNum=failureNum+1;
					}
				}catch (Exception ex){

					errorNum=errorNum+1;
				}
			}
		}else{
			resultMap.put("nolevel", true);
		}
		if(successNum>0){
			resultMap.put("flag", true);
		}else{
			resultMap.put("flag", false);
		}
		if(successNum==0 && failureNum==0 && errorNum==0 && list.size()>0){
			resultMap.put("msg", "导入的数据操失败!");
		}
		resultMap.put("flag", flag);
		resultMap.put("success", successNum);
		resultMap.put("failure", failureNum);
		resultMap.put("repeatNum", repeatNum);
		resultMap.put("closeNum", closeNum);
		resultMap.put("errorNum", errorNum);
		resultMap.put("levelNum", levelNum);
		resultMap.put("errorDataList", errorDataList);
		return resultMap;
	}

	/**
	 * 通过客户费用新增银行借贷单
	 *
	 * @param customerCostPayable
	 * @param flag		true：贷；false：借；
	 * @throws Exception
	 */
	private void addBankAmountByCustomerCostPayable(CustomerCostPayable customerCostPayable, boolean flag) throws Exception {

		if("1".equals(customerCostPayable.getPaytype()) && StringUtils.isNotEmpty(customerCostPayable.getBankid())) {
			BankAmountOthers bankAmountOthers = new BankAmountOthers();
			bankAmountOthers.setBusinessdate(CommonUtils.getTodayDataStr());
			bankAmountOthers.setRemark("客户应付费用：" + customerCostPayable.getId());
			bankAmountOthers.setBankid(customerCostPayable.getBankid());
			Bank bank = getBankInfoByID(customerCostPayable.getBankid());
			if (bank != null) {
				bankAmountOthers.setDeptid(bank.getBankdeptid());
			}
			bankAmountOthers.setLendtype(flag ? "2" : "1");
			bankAmountOthers.setBillid(customerCostPayable.getId());
			bankAmountOthers.setBilltype("41");	// 客户应付费用
			bankAmountOthers.setAmount(customerCostPayable.getAmount());
			bankAmountOthers.setUpamount(CommonUtils.AmountUnitCnChange(Double.parseDouble(customerCostPayable.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString())));
			bankAmountOthers.setOppid(customerCostPayable.getCustomerid());
			Customer customer = getCustomerByID(customerCostPayable.getCustomerid());
			bankAmountOthers.setOppname(customer.getName());
			bankAmountOthers.setOaid(customerCostPayable.getOaid());
			bankAmountService.addBankAmountOthers(bankAmountOthers);
			bankAmountService.auditBankAmountOthers(bankAmountOthers.getId());
			bankAmountService.closeBankAmountOthers(bankAmountOthers.getId());
		}
	}
	/**
	 * 获取客户费用单据生成凭证的数据
	 * @param idList
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List<Map> getCustomerCostPayableSumData(List idList){
		List list=customerCostPayableMapper.getCustomerCostPayableSumData(idList);
		return list;
	}
}
