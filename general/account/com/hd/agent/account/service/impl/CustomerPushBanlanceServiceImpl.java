/**
 * @(#)CustomerPushBanlanceServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 18, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CustomerPushBalanceMapper;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.ISalesExtService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 客户应收款冲差service实现类
 * @author chenwei
 */
public class CustomerPushBanlanceServiceImpl extends BaseAccountServiceImpl
		implements ICustomerPushBanlanceService {
	
	private CustomerPushBalanceMapper customerPushBalanceMapper;
	
	private ISalesExtService salesExtService;
	
	public CustomerPushBalanceMapper getCustomerPushBalanceMapper() {
		return customerPushBalanceMapper;
	}

	public void setCustomerPushBalanceMapper(
			CustomerPushBalanceMapper customerPushBalanceMapper) {
		this.customerPushBalanceMapper = customerPushBalanceMapper;
	}
	
	public ISalesExtService getSalesExtService() {
		return salesExtService;
	}

	public void setSalesExtService(ISalesExtService salesExtService) {
		this.salesExtService = salesExtService;
	}

	@Override
	public PageData showCustomerPushBanlanceList(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_account_customer_push_balance", null);
		pageMap.setDataSql(dataSql);
        if(pageMap.getCondition().containsKey("isflag") && null != pageMap.getCondition().get("ids")){
            String ids = (String)pageMap.getCondition().get("ids");
            if(StringUtils.isNotEmpty(ids)){
                pageMap.getCondition().put("idArr",ids.split(","));
            }
        }
		List<CustomerPushBalance> list = customerPushBalanceMapper.showCustomerPushBanlanceList(pageMap);
		list = setPushBalanceInfo(list);//组装数据信息
		PageData pageData = new PageData(customerPushBalanceMapper.showCustomerPushBanlanceCount(pageMap),list,pageMap);
		CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceSum(pageMap);
		List footer = new ArrayList();
		if(null!=customerPushBalance){
			customerPushBalance.setId("合计");
			footer.add(customerPushBalance);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public boolean addCustomerPushBanlance(CustomerPushBalance customerPushBalance) throws Exception {
		if(null!=customerPushBalance){
			SysUser sysUser = getSysUser();
			if (isAutoCreate("t_account_customer_push_balance")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(customerPushBalance, "t_account_customer_push_balance");
				customerPushBalance.setId(id);
			}else{
				customerPushBalance.setId("CC-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
			if(null!=customer){
				customerPushBalance.setSalesarea(customer.getSalesarea());
				if(StringUtils.isEmpty(customerPushBalance.getSalesdept())){
					customerPushBalance.setSalesdept(customer.getSalesdeptid());
				}
				if(StringUtils.isEmpty(customerPushBalance.getSalesuser())){
					customerPushBalance.setSalesuser(customer.getSalesuserid());
				}
				customerPushBalance.setPcustomerid(customer.getPid());
				customerPushBalance.setCustomersort(customer.getCustomersort());
				customerPushBalance.setIndooruserid(customer.getIndoorstaff());
			}
			customerPushBalance.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
			//厂家业务员
			customerPushBalance.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
			//获取品牌部门
			Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
			if(null!=brand){
				customerPushBalance.setBranddept(brand.getDeptid());
				customerPushBalance.setSupplierid(brand.getSupplierid());
			}
			customerPushBalance.setAdddeptid(sysUser.getDepartmentid());
			customerPushBalance.setAdddeptname(sysUser.getDepartmentname());
			customerPushBalance.setAdduserid(sysUser.getUserid());
			customerPushBalance.setAddusername(sysUser.getName());
			//customerPushBalance.setStatus("2");
//			customerPushBalance.setInitsendamount(customerPushBalance.getAmount());
			customerPushBalance.setSendamount(customerPushBalance.getAmount());
			customerPushBalance.setSendnotaxamount(customerPushBalance.getNotaxamount());
			customerPushBalance.setCheckamount(customerPushBalance.getAmount());
			customerPushBalance.setChecknotaxamount(customerPushBalance.getNotaxamount());
			customerPushBalance.setStatus("2");
//			customerPushBalance.setInvoiceamount(customerPushBalance.getAmount());
			int i = customerPushBalanceMapper.addCustomerPushBanlance(customerPushBalance);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public CustomerPushBalance showCustomerPushBanlanceInfo(String id)
			throws Exception {
		CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
		if(null!=customerPushBalance){
			Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
			if(null!=customer){
				customerPushBalance.setCustomername(customer.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(customerPushBalance.getSalesdept());
			if(null!=departMent){
				customerPushBalance.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(customerPushBalance.getSalesuser());
			if(null!=personnel){
				customerPushBalance.setSalesusername(personnel.getName());
			}
			//默认税种
            TaxType taxType = getTaxType(customerPushBalance.getDefaulttaxtype());
            if(null != taxType){
                customerPushBalance.setDefaulttaxtypename(taxType.getName());
            }
		}
		return customerPushBalance;
	}

	@Override
	public boolean editCustomerPushBanlance(
			CustomerPushBalance customerPushBalance) throws Exception {
		CustomerPushBalance oldCustomerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(customerPushBalance.getId());
		if(null!=oldCustomerPushBalance && ("2".equals(oldCustomerPushBalance.getStatus()) || "1".equals(oldCustomerPushBalance.getStatus()))){
			SysUser sysUser = getSysUser();
			Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
			if(null!=customer){
				customerPushBalance.setSalesarea(customer.getSalesarea());
				if(StringUtils.isEmpty(customerPushBalance.getSalesdept())){
					customerPushBalance.setSalesdept(customer.getSalesdeptid());
				}
				if(StringUtils.isEmpty(customerPushBalance.getSalesuser())){
					customerPushBalance.setSalesuser(customer.getSalesuserid());
				}
				customerPushBalance.setPcustomerid(customer.getPid());
				customerPushBalance.setCustomersort(customer.getCustomersort());
				customerPushBalance.setIndooruserid(customer.getIndoorstaff());
			}
			customerPushBalance.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
			//厂家业务员
			customerPushBalance.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
			//获取品牌部门
			Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
			if(null!=brand){
				customerPushBalance.setBranddept(brand.getDeptid());
				customerPushBalance.setSupplierid(brand.getSupplierid());
			}
			customerPushBalance.setModifyuserid(sysUser.getUserid());
			customerPushBalance.setModifyusername(sysUser.getName());
			//customerPushBalance.setStatus("2");
//			customerPushBalance.setInitsendamount(customerPushBalance.getAmount());
			customerPushBalance.setSendamount(customerPushBalance.getAmount());
			customerPushBalance.setSendnotaxamount(customerPushBalance.getNotaxamount());
			customerPushBalance.setCheckamount(customerPushBalance.getAmount());
			customerPushBalance.setChecknotaxamount(customerPushBalance.getNotaxamount());
//			customerPushBalance.setInvoiceamount(customerPushBalance.getAmount());
			int i = customerPushBalanceMapper.editCustomerPushBanlance(customerPushBalance);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public boolean editCustomerPushBanlanceRemark(
			CustomerPushBalance customerPushBalance) throws Exception {
		SysUser sysUser = getSysUser();
		customerPushBalance.setModifyuserid(sysUser.getUserid());
		customerPushBalance.setModifyusername(sysUser.getName());
		int i = customerPushBalanceMapper.editCustomerPushBanlance(customerPushBalance);
		return i>0;
	}

	@Override
	public boolean deleteCustomerPushBanlance(String id) throws Exception {
		CustomerPushBalance oldCustomerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
		if(null!=oldCustomerPushBalance && ("2".equals(oldCustomerPushBalance.getStatus()) || "1".equals(oldCustomerPushBalance.getStatus()))){
			int i = customerPushBalanceMapper.deleteCustomerPushBanlance(id);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public boolean auditCustomerPushBanlance(String id) throws Exception {
		CustomerPushBalance oldCustomerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
		if(null!=oldCustomerPushBalance && "2".equals(oldCustomerPushBalance.getStatus()) 
				&& (null==oldCustomerPushBalance.getInvoiceid()||"".equals(oldCustomerPushBalance.getInvoiceid()))){
			SysUser sysUser = getSysUser();
			int i = customerPushBalanceMapper.auditCustomerPushBanlance(id, sysUser.getUserid(), sysUser.getName());
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public boolean oppauditCustomerPushBanlance(String id) throws Exception {
		CustomerPushBalance oldCustomerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
		if(null!=oldCustomerPushBalance && "3".equals(oldCustomerPushBalance.getStatus()) &&"0".equals(oldCustomerPushBalance.getIsrefer())
				&& (null==oldCustomerPushBalance.getInvoiceid()||"".equals(oldCustomerPushBalance.getInvoiceid()))){
			SysUser sysUser = getSysUser();
			int i = customerPushBalanceMapper.oppauditCustomerPushBanlance(id, sysUser.getUserid(), sysUser.getName());
			return i>0;
		}else{
			return false;
		}
	}
	@Override
	public List showCustomerPushBanlanceListBy(Map map) throws Exception{
		List<CustomerPushBalance> list = customerPushBalanceMapper.showCustomerPushBanlanceListBy(map);
		String showPCustomerName=(String)map.get("showPCustomerName");
		for(CustomerPushBalance customerPushBalance : list){
			Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
			if(null!=customer){
				customerPushBalance.setCustomername(customer.getName());
				customerPushBalance.setCustomerInfo(customer);
				
				if("true".equals(showPCustomerName)){
					if(StringUtils.isNotEmpty(customer.getPid())){
						Map queryMap=new HashMap();
						queryMap.put("id", customer.getPid());
						Customer pCustomer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
						if(null!=pCustomer){
							customer.setPname(pCustomer.getName());
						}
					}
				}
			}
			Customer customer1= getCustomerByID(customerPushBalance.getPcustomerid());
			if(null!=customer1){
				customerPushBalance.setPcustomername(customer1.getName());
			}
			Brand brand = getBaseFilesGoodsMapper().getBrandInfo(customerPushBalance.getBrandid());
			if(null!=brand){
				customerPushBalance.setBrandname(brand.getName());
			}
			map.clear();
			map.put("id", customerPushBalance.getSubject());
			ExpensesSort expensesSort = getBaseFilesFinanceMapper().getExpensesSortDetail(map);
			if(null!=expensesSort){
				customerPushBalance.setSubjectname(expensesSort.getName());
			}
		}
		return list;
	}
	
	@Override
	public boolean updateOrderPrinttimes(CustomerPushBalance customerPushBalance) throws Exception{
		return customerPushBalanceMapper.updateOrderPrinttimes(customerPushBalance)>0;
	}
	@Override
	public void updateOrderPrinttimes(List<CustomerPushBalance> list) throws Exception{
		if(null!=list){
			for(CustomerPushBalance item : list){
				customerPushBalanceMapper.updateOrderPrinttimes(item);
			}
		}		
	}

	@Override
	public boolean addCustomerPushBanlanceByReceipt(Receipt receipt,
			List<ReceiptDetail> list) throws Exception {
		
		Map brandmap = new HashMap();
		for(ReceiptDetail receiptDetail : list){
			if(receiptDetail.getTaxprice().compareTo(receiptDetail.getInittaxprice())!=0){
				BigDecimal pushamount = receiptDetail.getTaxprice().subtract(receiptDetail.getInittaxprice()).multiply(receiptDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				if(brandmap.containsKey(receiptDetail.getBrandid())){
					BigDecimal brandamount = (BigDecimal) brandmap.get(receiptDetail.getBrandid());
					brandamount = brandamount.add(pushamount).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
					brandmap.put(receiptDetail.getBrandid(), brandamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				}else{
					brandmap.put(receiptDetail.getBrandid(), pushamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		Set set = brandmap.entrySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String brandid = entry.getKey();
			BigDecimal amount = (BigDecimal) brandmap.get(brandid);
			if(amount.compareTo(BigDecimal.ZERO)!=0){
				CustomerPushBalance customerPushBalance = new CustomerPushBalance();
				if (isAutoCreate("t_account_customer_push_balance")) {
					// 获取自动编号
					String id = getAutoCreateSysNumbderForeign(customerPushBalance, "t_account_customer_push_balance");
					customerPushBalance.setId(id);
				}else{
					customerPushBalance.setId("CC-"+CommonUtils.getDataNumberSendsWithRand());
				} 
				//类型为关联回单
				customerPushBalance.setIsinvoice("2");
				customerPushBalance.setCheckdate(CommonUtils.getTodayDataStr());
				customerPushBalance.setBusinessdate(getCurrentDate());
				customerPushBalance.setStatus("3");
				customerPushBalance.setIswriteoff("0");
				customerPushBalance.setPushtype("1");
				customerPushBalance.setIsrefer("1");
//				customerPushBalance.setInvoicedate(CommonUtils.getTodayDataStr());
                customerPushBalance.setOrderid(receipt.getSaleorderid());
				customerPushBalance.setInvoiceid(receipt.getId());
				customerPushBalance.setCustomerid(receipt.getCustomerid());
				customerPushBalance.setPcustomerid(receipt.getPcustomerid());
				customerPushBalance.setCustomersort(receipt.getCustomersort());
				customerPushBalance.setSalesarea(receipt.getSalesarea());
				customerPushBalance.setSalesdept(receipt.getSalesdept());
				customerPushBalance.setSalesuser(receipt.getSalesuser());
                customerPushBalance.setIndooruserid(receipt.getIndooruserid());
				customerPushBalance.setBrandid(brandid);
				customerPushBalance.setAmount(amount);
//				customerPushBalance.setInitsendamount(amount);
				customerPushBalance.setSendamount(amount);
//				customerPushBalance.setCheckamount(amount);
				Brand brand = getGoodsBrandByID(brandid);
				if(null!=brand){
					customerPushBalance.setBranddept(brand.getDeptid());
					customerPushBalance.setSupplierid(brand.getSupplierid());
                    customerPushBalance.setDefaulttaxtype(brand.getDefaulttaxtype());
				}
                BigDecimal notaxamount = BigDecimal.ZERO;
                if(StringUtils.isNotEmpty(customerPushBalance.getDefaulttaxtype())){
                    notaxamount = getNotaxAmountByTaxAmount(customerPushBalance.getAmount(), customerPushBalance.getDefaulttaxtype());
                }else{
                    notaxamount = getNotaxAmountByTaxAmount(customerPushBalance.getAmount(), null);
                }
				customerPushBalance.setAmount(customerPushBalance.getAmount());
				customerPushBalance.setNotaxamount(notaxamount);
                BigDecimal tax = customerPushBalance.getAmount().subtract(customerPushBalance.getNotaxamount());

                customerPushBalance.setTax(tax);
				customerPushBalance.setBranduser(getBrandUseridByCustomeridAndBrand(brandid, customerPushBalance.getCustomerid()));
				//厂家业务员
				customerPushBalance.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
				SysUser sysUser = getSysUser();
				customerPushBalance.setAdduserid(sysUser.getUserid());
				customerPushBalance.setAddusername(sysUser.getName());
				customerPushBalance.setAdddeptid(sysUser.getDepartmentid());
				customerPushBalance.setAdddeptname(sysUser.getDepartmentname());
				customerPushBalance.setAudittime(new Date());
				customerPushBalance.setAudituserid(sysUser.getUserid());
				customerPushBalance.setAuditusername(sysUser.getName());
				customerPushBalanceMapper.addCustomerPushBanlance(customerPushBalance);
			}
		}
		return true;
	}

	@Override
	public boolean deleteCustomerPushBanlanceByReceiptid(String receiptid)
			throws Exception {
		int i = customerPushBalanceMapper.deleteCustomerPushBanlanceByInvoiceid(receiptid);
		return i>0;
	}
	@Override
	public Map<String, List<CustomerPushBalance>> showCustomerPushBanlancePrintListBy(Map map) throws Exception{
		LinkedHashMap<String, List<CustomerPushBalance>> printData=new LinkedHashMap<String, List<CustomerPushBalance>>();
		List<CustomerPushBalance> dataList=showCustomerPushBanlanceListBy(map);
		List<CustomerPushBalance> printList=null;
		Iterator<CustomerPushBalance> it = dataList.iterator();
		String printTaskName="";
		Map queryMap=new HashMap();
        while (it.hasNext())
        {
        	CustomerPushBalance item = it.next();
        	if(null!=item){
            	printTaskName="";
            	if(StringUtils.isNotEmpty(item.getCustomerid())){
					Customer customer = getCustomerByID(item.getCustomerid());
					item.setCustomerInfo(customer);
					
					/**
					 * 通用版本不用，客户联系人电话，直接存储在客户档案
					if(StringUtils.isNotEmpty(customer.getContact())){
						queryMap.clear();
						queryMap.put("id", customer.getContact());
						Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(queryMap);
						if(contacter != null){
							customer.setContactname(contacter.getName());
						}
					}
					*/
            	}
        		if(StringUtils.isNotEmpty(item.getBusinessdate()) && StringUtils.isNotEmpty(item.getCustomerid())){
        			printTaskName=item.getCustomerid()+"_"+item.getBusinessdate();
        			if(printData.containsKey(printTaskName)){
        				printList=(List<CustomerPushBalance>)printData.get(printTaskName);
        				if(null==printList){
        					printList=new ArrayList<CustomerPushBalance>();        					
        				}
        			}else{
    					printList=new ArrayList<CustomerPushBalance>();
        			}
					printList.add(item);
					printData.put(printTaskName, printList);					
        			
        		}else{
        			if(printData.containsKey(item.getId())){
        				printList=(List<CustomerPushBalance>)printData.get(item.getId());
        				if(null==printList){
        					printList=new ArrayList<CustomerPushBalance>();
        				}
        			}else{
    					printList=new ArrayList<CustomerPushBalance>();
        			}
					printList.add(item);
					printData.put(item.getId(), printList);
        		}
        	}
        }
		return printData;
	}

	@Override
	public String addAndAuditCustomerPushBanlanceByOA(CustomerPushBalance customerPushBalance) throws Exception {

		if(null!=customerPushBalance){
			SysUser sysUser = getSysUser();
			if (isAutoCreate("t_account_customer_push_balance")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(customerPushBalance, "t_account_customer_push_balance");
				customerPushBalance.setId(id);
			}else{
				customerPushBalance.setId("CC-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
			if(null!=customer){
				customerPushBalance.setSalesarea(customer.getSalesarea());
				customerPushBalance.setSalesdept(customer.getSalesdeptid());
				customerPushBalance.setSalesuser(customer.getSalesuserid());
				customerPushBalance.setPcustomerid(customer.getPid());
				customerPushBalance.setCustomersort(customer.getCustomersort());
				customerPushBalance.setIndooruserid(customer.getIndoorstaff());
			}
			customerPushBalance.setBranduser(getBrandUseridByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
			//厂家业务员
			customerPushBalance.setSupplieruser(getSupplieruserByCustomeridAndBrand(customerPushBalance.getBrandid(), customerPushBalance.getCustomerid()));
			//获取品牌部门
			Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
			if(null!=brand){
				customerPushBalance.setBranddept(brand.getDeptid());
				customerPushBalance.setSupplierid(brand.getSupplierid());
			}
			customerPushBalance.setAdddeptid(sysUser.getDepartmentid());
			customerPushBalance.setAdddeptname(sysUser.getDepartmentname());
			customerPushBalance.setAdduserid(sysUser.getUserid());
			customerPushBalance.setAddusername(sysUser.getName());
			customerPushBalance.setAudittime(new Date());
			customerPushBalance.setAudituserid(sysUser.getUserid());
			customerPushBalance.setAuditusername(sysUser.getName());
			customerPushBalance.setStatus("3");
			//customerPushBalance.setStatus("2");
//			customerPushBalance.setInitsendamount(customerPushBalance.getAmount());
//			customerPushBalance.setSendamount(customerPushBalance.getAmount());
			customerPushBalance.setCheckamount(customerPushBalance.getAmount());
//			customerPushBalance.setInvoiceamount(customerPushBalance.getAmount());
			int i = customerPushBalanceMapper.addCustomerPushBanlance(customerPushBalance);
			return customerPushBalance.getId();
		}else{
			return null;
		}
	}

	@Override
	public boolean deleteCustomerPushBanlanceByOA(String oaid) throws Exception {
		int i = customerPushBalanceMapper.deleteCustomerPushBanlanceByOA(oaid);
		return i>0;
	}
	@Override
	public int getCustomerPushBanlanceCountBy(Map map) throws Exception{
		return customerPushBalanceMapper.getCustomerPushBanlanceCountBy(map);
	}


    @Override
    public List selectCustomerPushBanlanceByOaid(String oaid) throws Exception {
        return customerPushBalanceMapper.selectCustomerPushBanlanceByOaid(oaid);
    }

    @Override
    public Map getPushBanlanceNoTaxAmount(Map map) throws Exception {
        String amountstr = null != map.get("amount") ? (String)map. get("amount") : "";
        BigDecimal amount = BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(amountstr)){
            amount = new BigDecimal(amountstr);
        }
        String defaulttaxtype = null != map.get("defaulttaxtype") ? (String)map.get("defaulttaxtype") : "";
        BigDecimal notaxamount = getNotaxAmountByTaxAmount(amount,defaulttaxtype);
        BigDecimal tax = amount.subtract(notaxamount);
        Map map1 = new HashMap();
        map1.put("notaxamount",notaxamount);
        map1.put("tax",tax);
        return map1;
    }

    public List<CustomerPushBalance> setPushBalanceInfo(List<CustomerPushBalance> list) throws Exception {
        for(CustomerPushBalance customerPushBalance : list){
            Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
            if(null!=customer){
                customerPushBalance.setCustomername(customer.getName());
            }
            Customer customer1= getCustomerByID(customerPushBalance.getPcustomerid());
            if(null!=customer1){
                customerPushBalance.setPcustomername(customer1.getName());
            }
            Brand brand = getBaseFilesGoodsMapper().getBrandInfo(customerPushBalance.getBrandid());
            if(null!=brand){
                customerPushBalance.setBrandname(brand.getName());
            }
            //默认税种
            TaxType taxType = getTaxType(customerPushBalance.getDefaulttaxtype());
            if(null != taxType){
                customerPushBalance.setDefaulttaxtypename(taxType.getName());
            }
            Personnel personnel = getPersonnelById(customerPushBalance.getSalesuser());
            if(null != personnel){
                customerPushBalance.setSalesusername(personnel.getName());
            }
            DepartMent departMent = getDepartMentById(customerPushBalance.getSalesdept());
            if(null != departMent){
                customerPushBalance.setSalesdeptname(departMent.getName());
            }
            Map map = new HashMap();
            map.put("id", customerPushBalance.getSubject());
            ExpensesSort expensesSort = getBaseFilesFinanceMapper().getExpensesSortDetail(map);
            if(null!=expensesSort){
                customerPushBalance.setSubjectname(expensesSort.getName());
            }
            String statename = getBaseSysCodeMapper().searchCodename(customerPushBalance.getStatus(),"status");
            if(StringUtils.isNotEmpty(statename)){
                customerPushBalance.setStatusname(statename);
            }
            if("1".equals(customerPushBalance.getIsrefer())){
                customerPushBalance.setIsrefername("已申请");
            }else{
                customerPushBalance.setIsrefername("未申请");
            }
            if("1".equals(customerPushBalance.getIsinvoicebill())){
                customerPushBalance.setIsinvoicebillname("已开票");
            }else{
                customerPushBalance.setIsinvoicebillname("未开票");
            }
            if("0".equals(customerPushBalance.getIsinvoice())){
                customerPushBalance.setIsinvoicename("正常冲差");
            }else if("1".equals(customerPushBalance.getIsinvoice())){
                customerPushBalance.setIsinvoicename("发票冲差");
            }else if("2".equals(customerPushBalance.getIsinvoice())){
                customerPushBalance.setIsinvoicename("回单冲差");
            }
            //冲差类型
			List<SysCode> listByType=getBaseSysCodeMapper().getSysCodeListForeign("pushtypeprint");
			for(SysCode sysCode : listByType){
				if(sysCode.getCode().equals(customerPushBalance.getPushtype())){
					customerPushBalance.setPushtypename(sysCode.getCodename());
					break;
				}
			}
            if("0".equals(customerPushBalance.getIswriteoff())){
                customerPushBalance.setIswriteoffname("未核销");
            }else{
                customerPushBalance.setIswriteoffname("已核销");
            }
        }
        return list;
    }

    @Override
    public PageData getPushBalanceReportData(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_account_customer_push_balance", null);
        pageMap.setDataSql(dataSql);
        String groupcols = (String) pageMap.getCondition().get("groupcols");
		String column = "";
        if(!pageMap.getCondition().containsKey("groupcols")){
            groupcols = "id";
            pageMap.getCondition().put("groupcols", groupcols);
			column = "customerid" ;
        }else{
			column = groupcols.split(",")[0];
		}
        List<CustomerPushBalance> list = customerPushBalanceMapper.getPushBalanceReportData(pageMap);
        list = setPushBalanceInfo(list);
        for(CustomerPushBalance customerPushBalance : list){
			if(groupcols.contains("customerid")){
				Customer customer = getCustomerByID(customerPushBalance.getCustomerid());
				if(null!=customer){
					customerPushBalance.setCustomername(customer.getName());
				}
				Customer customer1= getCustomerByID(customerPushBalance.getPcustomerid());
				if(null!=customer1){
					customerPushBalance.setPcustomername(customer1.getName());
				}
			}else if(groupcols.contains("brandid")){
				Brand brand = getBaseFilesGoodsMapper().getBrandInfo(customerPushBalance.getBrandid());
				if(null!=brand){
					customerPushBalance.setBrandname(brand.getName());
				}
			}else  if(groupcols.contains("subject")){
				Map map = new HashMap();
				map.put("id", customerPushBalance.getSubject());
				ExpensesSort expensesSort = getBaseFilesFinanceMapper().getExpensesSortDetail(map);
				if(null!=expensesSort){
					customerPushBalance.setSubjectname(expensesSort.getName());
				}
			}else if(groupcols.contains("pushtype")){
				List<SysCode> listByType=getBaseSysCodeMapper().getSysCodeListForeign("pushtypeprint");
				for(SysCode sysCode : listByType){
					if(sysCode.getCode().equals(customerPushBalance.getPushtype())){
						customerPushBalance.setPushtypename(sysCode.getCodename());
						break;
					}
				}
			}
        }
        PageData pageData= new PageData(customerPushBalanceMapper.getPushBalanceReportDataCount(pageMap),list,pageMap);
        CustomerPushBalance customerPushBalance = customerPushBalanceMapper.getPushBalanceReportSum(pageMap);
        List footer = new ArrayList();
        if(null!=customerPushBalance){
			if("customerid".equals(column) ){
				customerPushBalance.setCustomerid("合计");
			}else if("brandid".equals(column)){
				customerPushBalance.setBrandname("合计");
			}else  if("subject".equals(column)){
				customerPushBalance.setSubjectname("合计");
			}else if("pushtype".equals(column)){
				customerPushBalance.setPushtypename("合计");
			}
            footer.add(customerPushBalance);
        }
        pageData.setFooter(footer);
        return pageData;
    }

	@Override
	public PageData getCustomerAndUserPushData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_account_customer_push_balance", null);
		pageMap.setDataSql(dataSql);
        Map condition = pageMap.getCondition();
        String groupcols = (String) condition.get("groupcols");
		if(!condition.containsKey("groupcols")){
			condition.put("groupcols", "id");
		}
        List<SysCode> pushtypeList = getBaseSysCodeMapper().getSysCodeListForeign("pushtypeprint");
        pageMap.getCondition().put("pushtypes", pushtypeList);

        List<Map> list = customerPushBalanceMapper.getCustomerAndUserPushData(pageMap);
        for(Map map : list){
            String customerid = (String) map.get("customerid");
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
                if(StringUtils.isNotEmpty(customer.getPid())){
                    customer = getCustomerByID(customer.getPid());
                    if(null != customer){
                        map.put("pcustomername",customer.getName());
                    }
                }
            }
            String brandid = (String) map.get("brandid");
            Brand brand = getGoodsBrandByID(brandid);
            if(null != brand){
                map.put("brandname",brand.getName());
            }
            String salesuser = (String) map.get("salesuser");
            Personnel personnel = getPersonnelById(salesuser);
            if(null != personnel){
                map.put("salesusername",personnel.getName());
            }
            String branduser = (String) map.get("branduser");
            personnel = getPersonnelById(branduser);
            if(null != personnel){
                map.put("brandusername",personnel.getName());
            }
            BigDecimal amountSum = new BigDecimal(0);
            for(SysCode sysCode : pushtypeList){
				BigDecimal value = (BigDecimal) map.get("amount"+sysCode.getCode());
				amountSum = amountSum.add(value);
			}
			map.put("amountSum",amountSum);
            if("customerid".equals(groupcols)){
                map.remove("brandname");
                map.remove("salesusername");
                map.remove("brandusername");
            }else if("brandid".equals(groupcols) || "salesuser".equals(groupcols) || "branduser".equals(groupcols)){
                map.remove("customerid");
                map.remove("customername");
                map.remove("pcustomername");
            }else if("brandid".equals(groupcols)){
                map.remove("salesusername");
                map.remove("brandusername");
            }else  if("salesuser".equals(groupcols)){
                map.remove("brandname");
                map.remove("brandusername");
            }else if("branduser".equals(groupcols)){
                map.remove("salesusername");
                map.remove("brandname");
            }
        }
        int count = customerPushBalanceMapper.getCustomerAndUserPushDataCount(pageMap);
        PageData pageData= new PageData(count,list,pageMap);
        Map map = customerPushBalanceMapper.getCustomerAndUserPushDataSum(pageMap);
        List footer = new ArrayList();
        if(map.size() > 0){
            if("customerid".equals(groupcols)){
                map.put("customerid","合计");
            }else if("brandid".equals(groupcols)){
                map.put("brandname","合计") ;
            }else  if("salesuser".equals(groupcols)){
                map.put("salesusername","合计");
            }else if("branduser".equals(groupcols)){
                map.put("brandusername","合计");
            }else{
                map.put("customerid","合计");
            }
        }
        footer.add(map);
        pageData.setFooter(footer);

		return pageData;
	}
	/**
	 * 批量添加客户应收款冲差
	 * @param customerPushBalance
	 * @param list
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date May 19, 2017
	 */
	public Map addMoreCustomerPushBanlace(CustomerPushBalance customerPushBalance,List<CustomerPushBalance> list) throws Exception{
		Map resMap=new HashMap();
		String successid="";
		Boolean flag=false;
		for(CustomerPushBalance detail:list){
			detail.setCustomerid(customerPushBalance.getCustomerid());
			detail.setSalesdept(customerPushBalance.getSalesdept());
			detail.setSalesuser(customerPushBalance.getSalesuser());
			detail.setBusinessdate(customerPushBalance.getBusinessdate());
			flag=addCustomerPushBanlance(detail);
			if(flag){
				if(StringUtils.isEmpty(successid)){
					successid=detail.getId();
				}else{
					successid+=","+detail.getId();
				}
			}

		}
		resMap.put("successid",successid);
		resMap.put("flag",flag);
		return resMap;
	}
	/**
	 * 批量审核客户应收款冲差
	 * @param ids
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date May 19, 2017
	 */
	public Map auditMoreCustomerPushBanlace(String ids) throws Exception{
		Map resMap=new HashMap();
		Boolean auditflag=false;
		String auditsuccessid="";
		String []idlist=ids.split(",");
		for(String id:idlist){
			auditflag=auditCustomerPushBanlance(id);
			if(auditflag){
				if(StringUtils.isEmpty(auditsuccessid)){
					auditsuccessid=id;
				}else{
					auditsuccessid+=","+id;
				}
			}
		}
		resMap.put("auditsuccessid",auditsuccessid);
		resMap.put("auditflag",auditflag);
		return resMap;
	}

}

