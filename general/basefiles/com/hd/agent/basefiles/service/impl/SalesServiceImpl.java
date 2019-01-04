/**
 * @(#)SalesServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 10, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.basefiles.dao.*;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.ISalesService;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.service.IStorageSaleOutService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;


/**
 *
 *
 * @author zhengziyong
 */
public class SalesServiceImpl extends FilesLevelServiceImpl implements ISalesService {

	private SalesAreaMapper salesAreaMapper;
	private CustomerSortMapper customerSortMapper;
	private CustomerMapper customerMapper;
	private CustomerAndSortMapper customerAndSortMapper;
	private ContacterMapper contacterMapper;
	private GoodsMapper goodsMapper;
    private CustomerCapitalMapper customerCapitalMapper;

    private DistributionRuleMapper distributionRuleMapper;

    private BuySupplierMapper buySupplierMapper;

    public CustomerCapitalMapper getCustomerCapitalMapper() {
        return customerCapitalMapper;
    }

    public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
        this.customerCapitalMapper = customerCapitalMapper;
    }

    private DepartMentMapper departMentMapper;
	public DepartMentMapper getDepartMentMapper() {
		return departMentMapper;
	}

	public void setDepartMentMapper(DepartMentMapper departMentMapper) {
		this.departMentMapper = departMentMapper;
	}

    public DistributionRuleMapper getDistributionRuleMapper() {
        return distributionRuleMapper;
    }

    public void setDistributionRuleMapper(DistributionRuleMapper distributionRuleMapper) {
        this.distributionRuleMapper = distributionRuleMapper;
    }

    public BuySupplierMapper getBuySupplierMapper() {
        return buySupplierMapper;
    }

    public void setBuySupplierMapper(BuySupplierMapper buySupplierMapper) {
        this.buySupplierMapper = buySupplierMapper;
    }

    /**销售区域方法开始*/
	@Override
	public boolean addSalesArea(SalesArea area) throws Exception {
		return salesAreaMapper.addSalesArea(area)>0;
	}

	@Override
	public Map updateSalesArea(SalesArea area) throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		map3.put("id", area.getOldid());
		SalesArea oldSalesArea = salesAreaMapper.getSalesAreaDetail(map3);
		if(null != oldSalesArea){
			if(!oldSalesArea.getThisid().equals(area.getThisid()) || !oldSalesArea.getThisname().equals(area.getThisname())){ //判断名称是否有修改，有修改则更新所有子节点名称
				//updateTreeName("t_base_sales_area", area.getName(), area.getId());
				List<SalesArea> childList = salesAreaMapper.getSalesAreaChildList(oldSalesArea.getId());
				if(childList.size() != 0){
					for(SalesArea repeatSalesArea : childList){
						repeatSalesArea.setOldid(repeatSalesArea.getId());
						if(!oldSalesArea.getThisid().equals(area.getThisid())){
							String newid = repeatSalesArea.getId().replaceFirst(oldSalesArea.getThisid(), area.getThisid()).trim();
							repeatSalesArea.setId(newid);
							String newpid = repeatSalesArea.getPid().replaceFirst(oldSalesArea.getThisid(), area.getThisid()).trim();
							repeatSalesArea.setPid(newpid);
						}
						if(!oldSalesArea.getThisname().equals(area.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatSalesArea.getName().replaceFirst(oldSalesArea.getThisname(), area.getThisname());
							repeatSalesArea.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatSalesArea.getId());
						node.setParentid(repeatSalesArea.getPid());
						node.setText(repeatSalesArea.getThisname());
						node.setState(repeatSalesArea.getState());
						map2.put(repeatSalesArea.getOldid(), node);
					}
					salesAreaMapper.editSalesAreaBatch(childList);
				}
			}
		}
		boolean flag = salesAreaMapper.updateSalesArea(area)>0;
		if(flag){
			Tree node = new Tree();
			node.setId(area.getId());
			node.setParentid(area.getPid());
			node.setText(area.getThisname());
			node.setState(area.getState());
			map2.put(area.getOldid(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public List getSalesAreaList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_sales_area",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_sales_area",null); //数据权限
		pageMap.setDataSql(sql);
		return salesAreaMapper.getSalesAreaList(pageMap);
	}

	@Override
	public SalesArea getSalesAreaDetail(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_sales_area", null); //加字段查看权限
		map.put("id", id);
		return salesAreaMapper.getSalesAreaDetail(map);
	}

	@Override
	public List getSalesAreaParentAllChildren(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_sales_area",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_sales_area",null);
		pageMap.setDataSql(sql);
		return salesAreaMapper.getSalesAreaParentAllChildren(pageMap);
	}

	@Override
	public boolean deleteSalesArea(String id) throws Exception {
		return salesAreaMapper.deleteSalesArea(id)>0;
	}

	@Override
	public boolean updateSalesAreaOpen(SalesArea area) throws Exception {
		return salesAreaMapper.updateSalesAreaOpen(area)>0;
	}

	@Override
	public boolean updateSalesAreaClose(SalesArea area) throws Exception {
		return salesAreaMapper.updateSalesAreaClose(area)>0;
	}

	@Override
	public boolean isRepeatSalesAreaThisname(String thisname) throws Exception {
		if(salesAreaMapper.isRepeatThisName(thisname) > 0){//不为空，已存在该本级名称
			return false;
		}
		return true;
	}
	/**销售区域方法结束*/

	/**客户分类方法开始*/
	@Override
	public boolean addCustomerSort(CustomerSort customer) throws Exception {
		return customerSortMapper.addCustomerSort(customer)>0;
	}

	@Override
	public Map updateCustomerSort(CustomerSort customerSort) throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		map3.put("id", customerSort.getOldid());
		CustomerSort oldSort = customerSortMapper.getCustomerSortDetail(map3);
		if(null != oldSort){
			if(!oldSort.getThisid().equals(customerSort.getThisid()) || !oldSort.getThisname().equals(customerSort.getThisname())){ //判断名称是否有修改，有修改则更新所有子节点名称
				//updateTreeName("t_base_sales_customersort", customer.getName(), customer.getId());
				List<CustomerSort> childList = customerSortMapper.getCustomerSortChildList(oldSort.getId());
				if(childList.size() != 0){
					for(CustomerSort repeatCstSort : childList){
						repeatCstSort.setOldid(repeatCstSort.getId());
						if(!oldSort.getThisid().equals(customerSort.getThisid())){
							String newid = repeatCstSort.getId().replaceFirst(oldSort.getThisid(), customerSort.getThisid()).trim();
							repeatCstSort.setId(newid);
							String newpid = repeatCstSort.getPid().replaceFirst(oldSort.getThisid(), customerSort.getThisid()).trim();
							repeatCstSort.setPid(newpid);
						}
						if(!oldSort.getThisname().equals(customerSort.getThisname())){
							//若本级名称改变，下属所有的任务分类名称应做对应的替换
							String newname = repeatCstSort.getName().replaceFirst(oldSort.getThisname(), customerSort.getThisname());
							repeatCstSort.setName(newname);
						}
						Tree node = new Tree();
						node.setId(repeatCstSort.getId());
						node.setParentid(repeatCstSort.getPid());
						node.setText(repeatCstSort.getThisname());
						node.setState(repeatCstSort.getState());
						map2.put(repeatCstSort.getOldid(), node);
					}
					customerSortMapper.editCustomerSortBatch(childList);
				}
			}
		}
		boolean flag = customerSortMapper.updateCustomerSort(customerSort)>0;
		if(flag){
			Tree node = new Tree();
			node.setId(customerSort.getId());
			node.setParentid(customerSort.getPid());
			node.setText(customerSort.getThisname());
			node.setState(customerSort.getState());
			map2.put(customerSort.getOldid(), node);
		}
		map.put("flag", flag);
		map.put("nodes", map2);
		return map;
	}

	@Override
	public List getCustomerSortList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_sales_customersort",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_sales_customersort",null); //数据权限
		pageMap.setDataSql(sql);
		return customerSortMapper.getCustomerSortList(pageMap);
	}

	@Override
	public CustomerSort getCustomerSortDetail(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_sales_customersort", null); //加字段查看权限
		map.put("id", id);
		return customerSortMapper.getCustomerSortDetail(map);
	}

	@Override
	public List getCustomerSortParentAllChildren(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_sales_customersort",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_sales_customersort",null);
		pageMap.setDataSql(sql);
		return customerSortMapper.getCustomerSortParentAllChildren(pageMap);
	}

	@Override
	public boolean deleteCustomerSort(String id) throws Exception {
		return customerSortMapper.deleteCustomerSort(id)>0;
	}

	@Override
	public boolean updateCustomerSortOpen(CustomerSort customer) throws Exception {
		return customerSortMapper.updateCustomerSortOpen(customer)>0;
	}

	@Override
	public boolean updateCustomerSortClose(CustomerSort customer) throws Exception {
		return customerSortMapper.updateCustomerSortClose(customer)>0;
	}

	@Override
	public boolean isRepeatCustomerSortThisname(String thisname)
			throws Exception {
		if(customerSortMapper.isRepeatThisName(thisname) > 0){//不为空，已存在该本级名称
			return false;
		}
		return true;
	}
	/**客户分类方法结束*/

	/**客户档案方法开始*/
	@Override
	public boolean addCustomer(Customer customer) throws Exception {
		CustomerAndSort customerAndSort = customer.getCustomerAndSort();
		if(customerAndSort != null){
			String sortIds = customerAndSort.getSortid();
//			String sortNames = customerAndSort.getSortname();
			String isDefaults = customerAndSort.getDefaultsort();
			String remarks = customerAndSort.getRemark();
			String[] sortIdArr = null;
//			String[] sortNameArr = null;
			String[] isDefaultArr = null;
			String[] remarkArr = null;
			if(null != sortIds){
				sortIdArr = sortIds.split(",");
			}
//			if(null != sortNames){
//				sortNameArr = sortNames.split(",");
//			}
			if(null != isDefaults){
				isDefaultArr = isDefaults.split(",");
			}
			if(null != remarks){
				remarkArr = remarks.split(",");
			}
			if(sortIdArr != null){
				for(int i=0; i<sortIdArr.length;i++){
					CustomerAndSort sort = new CustomerAndSort();
					sort.setDefaultsort(isDefaultArr[i].trim());
					sort.setCustomerid(customer.getId().trim());
					sort.setSortid(sortIdArr[i].trim());
//					sort.setSortname(sortNameArr[i].trim());
					sort.setRemark(remarkArr[i].trim());
					customerAndSortMapper.addCustomerAndSort(sort);
				}
			}
		}
		else{
			if(StringUtils.isNotEmpty(customer.getCustomersort())){
				List<CustomerAndSort> list = customerAndSortMapper.getCustomerAndSortListByCustomer(customer.getOldid());
				if(list.size() != 0){
					for(CustomerAndSort sort2 : list){
						sort2.setId(null);
						sort2.setCustomerid(customer.getId().trim());
						customerAndSortMapper.addCustomerAndSort(sort2);
					}
				}
			}
		}
		List<CustomerPrice> priceList = customer.getPriceList();
		if(priceList != null){
			for(CustomerPrice price : priceList){
				if(StringUtils.isNotEmpty(price.getId())){
					price.setId(null);
				}
				price.setCustomerid(customer.getId());
				customerMapper.addCustomerPrice(price);
			}
		}
		else{
			priceList = customerMapper.getCustomerPriceListByCustomer(customer.getOldid());
			if(priceList.size() != 0){
				for(CustomerPrice price : priceList){
					if(StringUtils.isNotEmpty(price.getId())){
						price.setId(null);
					}
					price.setCustomerid(customer.getId());
					customerMapper.addCustomerPrice(price);
				}
			}
		}
		SysUser sysUser = getSysUser();
		customer.setAdddeptid(sysUser.getDepartmentid());
		customer.setAdddeptname(sysUser.getDepartmentname());
		customer.setAdduserid(sysUser.getUserid());
		customer.setAddusername(sysUser.getName());
		if(StringUtils.isEmpty(customer.getPinyin()) && StringUtils.isNotEmpty(customer.getName())){
			customer.setPinyin(CommonUtils.getPinYingJCLen(customer.getName()));
		}
		boolean flag = customerMapper.addCustomer(customer)>0;
		if(flag){
			//联系人档案
			if(StringUtils.isNotEmpty(customer.getContactname())){
				Contacter contacter = new Contacter();
				contacter.setName(customer.getContactname());
				if(StringUtils.isNotEmpty(customer.getMobile())){
					contacter.setMobile(customer.getMobile());
				}
				contacter.setIsdefault("1");
				contacter.setState("1");
				contacter.setCustomer(customer.getId());
				if(contacterMapper.addContacter(contacter) > 0){
					List<Contacter> clist = contacterMapper.getContacterByName(customer.getContactname());
					if(clist.size() != 0){
						customer.setContact(clist.get(0).getId());
						customer.setOldid(customer.getId());
					}
					int i = customerMapper.updateCustomer(customer);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean updateCustomer(Customer customer, String sortEdit) throws Exception {
		CustomerAndSort customerAndSort = customer.getCustomerAndSort();
		if("1".equals(sortEdit)){ //sortEdit为“1”表示对应分类有修改，为“0”表示没有修改，没有修改不做操作
			customerAndSortMapper.deleteCustomerAddSortByCustomer(customer.getId());
			if(customerAndSort != null){
				String sortIds = customerAndSort.getSortid();
				String sortNames = customerAndSort.getSortname();
				String isDefaults = customerAndSort.getDefaultsort();
				String remarks = customerAndSort.getRemark();
				String[] sortIdArr = null;
				String[] sortNameArr = null;
				String[] isDefaultArr = null;
				String[] remarkArr = null;
				if(null != sortIds){
					sortIdArr = sortIds.split(",");
				}
				if(null != sortNames){
					sortNameArr = sortNames.split(",");
				}
				if(null != isDefaults){
					isDefaultArr = isDefaults.split(",");
				}
				if(null != remarks){
					remarkArr = remarks.split(",");
				}
				if(sortIdArr != null){
					for(int i=0; i<sortIdArr.length;i++){
						CustomerAndSort sort = new CustomerAndSort();
						sort.setDefaultsort(isDefaultArr[i].trim());
						sort.setCustomerid(customer.getId().trim());
						sort.setSortid(sortIdArr[i].trim());
						sort.setSortname(sortNameArr[i].trim());
						sort.setRemark(remarkArr[i].trim());
						customerAndSortMapper.addCustomerAndSort(sort);
					}
				}
			}
		}
		String[] contacter = customer.getContacterId();
		String[] isdefault = customer.getContacterDefaultSort();
		if(contacter != null && contacter.length>0){
			for(int i=0;i<contacter.length;i++){
				contacterMapper.updateContacterDetault(isdefault[i], contacter[i]);
			}
		}
		List<CustomerPrice> priceList = customer.getPriceList();
		if(priceList != null){
			for(CustomerPrice price : priceList){
				if(StringUtils.isNotEmpty(price.getId())){
					if(StringUtils.isEmpty(customer.getDelPriceList())){
						customerMapper.updateCustomerPrice(price);
					}
					else{
						if(customer.getDelPriceList().contains(price.getId())){
							customerMapper.deleteCustomerPriceById(price.getId());
						}
					}
				}
				else{
					price.setCustomerid(customer.getId());
					customerMapper.addCustomerPrice(price);
				}
			}
		}
		//对方联系人
		if(StringUtils.isNotEmpty(customer.getContactname())){
			if(StringUtils.isNotEmpty(customer.getContact())){
				Contacter contacter2 = new Contacter();
				contacter2.setOldid(customer.getContact());
				contacter2.setName(customer.getContactname());
				contacter2.setMobile(customer.getMobile());
				contacter2.setIsdefault("1");
				contacterMapper.updateContacter(contacter2);
			}
			else{
				Contacter contacter2 = new Contacter();
				contacter2.setName(customer.getContactname());
				if(StringUtils.isNotEmpty(customer.getMobile())){
					contacter2.setMobile(customer.getMobile());
				}
				contacter2.setState("1");
				contacter2.setCustomer(customer.getId());
				contacter2.setIsdefault("1");
				if(contacterMapper.addContacter(contacter2) > 0){
					List<Contacter> clist = contacterMapper.getContacterByName(customer.getContactname());
					if(clist.size() != 0){
						customer.setContact(clist.get(0).getId());
						customer.setOldid(customer.getId());
					}
					customerMapper.updateCustomer(customer);
				}
			}
		}
		else{
			if(StringUtils.isNotEmpty(customer.getContact())){
				contacterMapper.deleteContacter(customer.getContact());
				customer.setContact("");
				customerMapper.updateCustomer(customer);
			}
		}
		SysUser sysUser = getSysUser();
		customer.setModifyuserid(sysUser.getUserid());
		customer.setModifyusername(sysUser.getName());
		if(StringUtils.isEmpty(customer.getPinyin()) && StringUtils.isNotEmpty(customer.getName())){
			customer.setPinyin(CommonUtils.getPinYingJCLen(customer.getName()));
		}
		//客户编码变动，人员档案对应客户做相应变动
		if(!customer.getOldid().equals(customer.getId())){
			List<PersonnelCustomer> customerList = getBasePersonnelMapper().getCustomerByCustomerid(customer.getOldid());
			if(customerList.size() != 0){
				getBasePersonnelMapper().editPersonCustomer(customer.getOldid(), customer.getId());
			}
			List<PersonnelBrandAndCustomer> brandcustomerList = getBasePersonnelMapper().getBrandCustomerListByCustomerid(customer.getOldid());
			if(brandcustomerList.size() != 0){
				getBasePersonnelMapper().editPersonBrandAndCustomerByCustomerid(customer.getOldid(), customer.getId());
			}
		}
		return customerMapper.updateCustomer(customer)>0;
	}

	@Override
	public Customer getCustomerDetail(String id) throws Exception {
		Map map = getAccessColumnMap("t_base_sales_customer", null); //加字段查看权限
		map.put("id", id);
		Customer customer = customerMapper.getCustomerDetail(map);
		if(customer != null){
			//上级客户
			//Customer pCustomer = getCustomerDetail(customer.getPid());
			Customer pCustomer = customerMapper.getCustomerInfo(customer.getPid());
			if(null != pCustomer){
				customer.setPname(pCustomer.getName());
			}
			List list = getCustomerAndSortListByCustomer(id);
			customer.setSortList(list);
			List<CustomerPrice> priceList = customerMapper.getCustomerPriceListByCustomer(id);
			for(CustomerPrice price : priceList){
				GoodsInfo goodsInfo = goodsMapper.getBaseGoodsInfo(price.getGoodsid());
				price.setGoodsInfo(goodsInfo);
			}
			//PageData pageData = new PageData(priceList.size(),priceList,pageMap);
			customer.setPriceList(priceList);
			if(StringUtils.isNotEmpty(customer.getContact())){
				Map map2 = new HashMap();
				map2.put("id", customer.getContact());
				Contacter contacter = contacterMapper.getContacterDetail(map2);
				if(null != contacter){
					customer.setContactname(contacter.getName());
					customer.setContactmobile(contacter.getMobile());
				}
			}
		}
		return customer;
	}

	@Override
	public Customer getOnlyCustomer(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id);
		Customer customer = customerMapper.getCustomerDetail(map);
		return customer;
	}

	@Override
	public PageData getPriceListPage(PageMap pageMap) throws Exception {
		List<CustomerPrice> list = customerMapper.getPriceListByPageMap(pageMap);
		for(CustomerPrice price : list){
			//获取价格套价格，税率
			GoodsInfo goodsInfo = goodsMapper.getBaseGoodsInfo(price.getGoodsid());
			if(null != goodsInfo){
				price.setGoodsInfo(goodsInfo);
				price.setGoodsname(goodsInfo.getName());
				TaxType taxType = getBaseFinanceMapper().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
				if(null != taxType && null != taxType.getRate()){
					BigDecimal taxrate = taxType.getRate().divide(new BigDecimal("100")).add(new BigDecimal("1"));
					price.setTaxrate(taxrate);
				}
                GoodsInfo_MteringUnitInfo goodsMU = goodsMapper.getMUInfoByGoodsIdAndIsdefault(price.getGoodsid());
                if (null != goodsMU) {
                    goodsInfo.setAuxunitid(goodsMU.getMeteringunitid());
                    MeteringUnit meteringUnit2 = getBaseGoodsMapper().showMeteringUnitInfo(goodsMU.getMeteringunitid());
                    if (null != meteringUnit2) {
                        goodsInfo.setAuxunitname(meteringUnit2.getName());
                    }
                    goodsInfo.setBoxnum(goodsMU.getRate());
                }
			}
			Customer customer = getCustomerInfo(price.getCustomerid());
			if(null != customer){
				price.setCustomername(customer.getName());
				GoodsInfo_PriceInfo gPriceInfo = getBaseGoodsMapper().getPriceDataByGoodsidAndCode(price.getGoodsid(), customer.getPricesort());
				if(null != gPriceInfo){
					price.setTaxprice(gPriceInfo.getTaxprice());
				}
			}
            if(null != goodsInfo && null != customer){
                Map map = goodsMapper.getTaxPriceByGoodsidAndPriceCode(goodsInfo.getId(), customer.getPricesort());
                if(null != map){
                    BigDecimal boxprice = (BigDecimal)map.get("boxprice");
                    price.setBoxprice(boxprice);
                }
            }

		}
		int count = customerMapper.getPriceCountByPageMap(pageMap);
		PageData pageData = new PageData(count,list,pageMap);
		return pageData;
	}

	@Override
	public List getCustomerList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_sales_customer","t"); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_sales_customer","t"); //数据权限
		pageMap.setDataSql(sql);
		return customerMapper.getCustomerList(pageMap);
	}

    @Override
    public List getCustomerByConditon(Map map) throws Exception{
        return customerMapper.getCustomerByConditon(map);
    }

	@Override
	public List getCustomerBySalesman(String username) throws Exception {
		return customerMapper.getCustomerBySalesman(username);
	}

	@Override
	public List getCustomerParentAllChildren(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_base_sales_customer",null); //字段权限
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_base_sales_customer",null); //数据权限
		pageMap.setDataSql(sql);
		return customerMapper.getCustomerParentAllChildren(pageMap);
	}

	@Override
	public boolean deleteCustomer(String id) throws Exception {
		customerAndSortMapper.deleteCustomerAddSortByCustomer(id);
		customerMapper.deleteCustomerPriceByCustomer(id);
		Map map = new HashMap();
		map.put("id", id);
		Customer customer = customerMapper.getCustomerDetail(map);
		if(null != customer){
			if(StringUtils.isNotEmpty(customer.getContact())){
				 contacterMapper.deleteContacter(customer.getContact());
			}
		}
		//删除对应客户
		getBasePersonnelMapper().deleteBrandAndCustomerByCustomer(id);
		getBasePersonnelMapper().deleteCustomerListByCustomerid(id);
		//删除线路档案对应线路客户
		List<LogisticsLineCustomer> lineCustomerList = getBaseLogisticsMapper().getLogisticsLineCustomerList(id);
		if(lineCustomerList.size() != 0){
			boolean lineflag = getBaseLogisticsMapper().deleteLineCustomerByCustomerid(id) > 0;
			if(lineflag){
				for(LogisticsLineCustomer lineCustomer : lineCustomerList){
					LogisticsLine line = getBaseLogisticsMapper().getLineInfo(lineCustomer.getLineid());
					if(null != line){
						line.setTotalcustomers(line.getTotalcustomers()-Integer.parseInt("1"));
						line.setOldid(line.getId());
						getBaseLogisticsMapper().updateLineInfo(line);
					}
				}
			}
		}
		return customerMapper.deleteCustomer(id)>0;
	}

	@Override
	public boolean updateCustomerOpen(Customer customer) throws Exception {
		Customer customer2 = getCustomerDetail(customer.getId());
		if(StringUtils.isNotEmpty(customer2.getPid())){
			customerMapper.updateCustomerLast("0", customer2.getPid());
		}
		return customerMapper.updateCustomerOpen(customer)>0;
	}

	@Override
	public boolean updateCustomerClose(Customer customer) throws Exception {
		Customer customer2 = getCustomerDetail(customer.getId());
		if(StringUtils.isNotEmpty(customer2.getPid())){
			int count = customerMapper.getCustomerOpenCountByPid(customer2.getPid());
			if(count == 1){
				customerMapper.updateCustomerLast("1", customer2.getPid());
			}
		}
		return customerMapper.updateCustomerClose(customer)>0;
	}

	@Override
	public List getCustomerAndSortListByCustomer(String id) throws Exception {
		List<CustomerAndSort> list = customerAndSortMapper.getCustomerAndSortListByCustomer(id);
		for(CustomerAndSort sort: list){
			Map map = new HashMap();
			map.put("id", sort.getSortid());
			CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
			if(customerSort != null){
				sort.setSortname(customerSort.getName());
			}
		}
		return list;
	}

	@Override
	public PageData getCustomerData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_base_sales_customer", "t");
		pageMap.setDataSql(dataSql);
		//单表取字段权限
		String cols = getAccessColumnList("t_base_sales_customer","t");
		pageMap.setCols(cols);
		List<Customer> customerList = customerMapper.getCustomerList(pageMap);
		for(Customer customer: customerList){
			//上级客户
			if(StringUtils.isNotEmpty(customer.getPid())){
				Map map = new HashMap();
				map.put("id", customer.getPid());
				Customer customer2 = customerMapper.getCustomerDetail(map);
				if(null != customer2){
					customer.setPname(customer2.getName());
				}
			}
			//结算方式
			Map map = new HashMap();
			map.put("id", customer.getSettletype());
			Settlement settlement = getBaseFinanceMapper().getSettlemetDetail(map);
			if(null != settlement){
				customer.setSettletypename(settlement.getName());
			}
			//价格套
			SysCode priceSysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getPricesort(), "price_list");
			if(null != priceSysCode){
				customer.setPricesortname(priceSysCode.getCodename());
			}
			//促销分类
			SysCode promotionsortSysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getPromotionsort(), "promotionsort");
			if(null != promotionsortSysCode){
				customer.setPromotionsortname(promotionsortSysCode.getCodename());
			}
			CustomerSort customerSort = getCustomerSortDetail(customer.getCustomersort());
			if(customerSort != null){
				customer.setCustomersortname(customerSort.getThisname());
			}
			//所属区域
			SalesArea salesArea = getSalesAreaDetail(customer.getSalesarea());
			if(salesArea != null){
				customer.setSalesareaname(salesArea.getThisname());
			}
			//客户业务员
			Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getSalesuserid());
			if(null != personnel){
				customer.setSalesusername(personnel.getName());
			}
			//收款人
			Personnel payee = getBasePersonnelMapper().getPersonnelInfo(customer.getPayeeid());
			if(null != payee){
				customer.setPayeename(payee.getName());
			}
			SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getState(), "state");
			if(null != sysCode){
				customer.setStatename(sysCode.getCodename());
			}
		}
		return new PageData(customerMapper.getCustomerCount(pageMap), customerList, pageMap);
	}

	@Override
	public PageData getCustomerListForCustomerprice(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_base_sales_customer", "t");
		pageMap.setDataSql(dataSql);
		//单表取字段权限
		String cols = getAccessColumnList("t_base_sales_customer","t");
		pageMap.setCols(cols);
		//销售区域
		if(null != pageMap.getCondition().get("salesarea")){
			String salesareasql = "";
			String salesarea = (String)pageMap.getCondition().get("salesarea");
			salesarea = StringEscapeUtils.escapeSql(salesarea);
			if(salesarea.indexOf(",") == -1){
				salesareasql = " t.salesarea = '"+salesarea+"' ";
			}else{
				String[] salesareaArr = salesarea.split(",");
				for(String salesarea2 : salesareaArr){
					if(StringUtils.isEmpty(salesareasql)){
						salesareasql = " t.salesarea = '"+salesarea2+"' ";
					}else{
						salesareasql += " or " + " t.salesarea = '"+salesarea2+"' ";
					}
				}
			}
			if(StringUtils.isNotEmpty(salesareasql)){
				pageMap.getCondition().put("salesareasql", salesareasql);
			}
		}
		//客户分类
		if(null != pageMap.getCondition().get("customersort")){
			String customersortsql = "";
			String customersort = (String)pageMap.getCondition().get("customersort");
			customersort = StringEscapeUtils.escapeSql(customersort);
			if(customersort.indexOf(",") == -1){
				customersortsql = " t.customersort like '"+customersort+"%' ";
			}else{
				String[] customersortArr = customersort.split(",");
				for(String customersort2 : customersortArr){
					if(StringUtils.isEmpty(customersortsql)){
						customersortsql = " t.customersort like '"+customersort2+"%' ";
					}else{
						customersortsql += " or " + " t.customersort like '"+customersort2+"%' ";
					}
				}
			}
			if(StringUtils.isNotEmpty(customersortsql)){
				pageMap.getCondition().put("customersortsql", customersortsql);
			}
		}
		List<Customer> customerList = customerMapper.getCustomerListForCustomerprice(pageMap);
		for(Customer customer: customerList){
			//上级客户
			if(StringUtils.isNotEmpty(customer.getPid())){
				Map map = new HashMap();
				map.put("id", customer.getPid());
				Customer customer2 = customerMapper.getCustomerDetail(map);
				if(null != customer2){
					customer.setPname(customer2.getName());
				}
			}
			CustomerSort customerSort = getCustomerSortDetail(customer.getCustomersort());
			if(customerSort != null){
				customer.setCustomersortname(customerSort.getThisname());
			}
			//所属区域
			SalesArea salesArea = getSalesAreaDetail(customer.getSalesarea());
			if(salesArea != null){
				customer.setSalesareaname(salesArea.getThisname());
			}
			//客户业务员
			Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getSalesuserid());
			if(null != personnel){
				customer.setSalesusername(personnel.getName());
			}
			SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getState(), "state");
			if(null != sysCode){
				customer.setStatename(sysCode.getCodename());
			}
		}
		return new PageData(customerMapper.getCustomerCountForCustomerprice(pageMap), customerList, pageMap);
	}

	@Override
	public PageData getCustomerDataForPact(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_base_sales_customer", "a");
		pageMap.setDataSql(dataSql);
		//单表取字段权限
		String cols = getAccessColumnList("t_base_sales_customer","a");
		pageMap.setCols(cols);
		List<Customer> customerList = customerMapper.getCustomerListForPact(pageMap);
		for(Customer customer: customerList){
			//上级客户
			if(StringUtils.isNotEmpty(customer.getPid())){
				Map map = new HashMap();
				map.put("id", customer.getPid());
				Customer customer2 = customerMapper.getCustomerDetail(map);
				if(null != customer2){
					customer.setPname(customer2.getName());
				}
			}
			//联系人
			Map map = new HashMap();
			map.put("id", customer.getContact());
			Contacter contacter = getBaseContacterMapper().getContacterDetail(map);
			if(null != contacter){
				customer.setContactname(contacter.getName());
				customer.setContactmobile(contacter.getMobile());
			}
			//结算方式
			map.put("id", customer.getSettletype());
			Settlement settlement = getBaseFinanceMapper().getSettlemetDetail(map);
			if(null != settlement){
				customer.setSettletypename(settlement.getName());
			}
			//价格套
			SysCode priceSysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getPricesort(), "price_list");
			if(null != priceSysCode){
				customer.setPricesortname(priceSysCode.getCodename());
			}
			//促销分类
			SysCode promotionsortSysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getPromotionsort(), "promotionsort");
			if(null != promotionsortSysCode){
				customer.setPromotionsortname(promotionsortSysCode.getCodename());
			}
			CustomerSort customerSort = getCustomerSortDetail(customer.getCustomersort());
			if(customerSort != null){
				customer.setCustomersortname(customerSort.getThisname());
			}
			SalesArea salesArea = getSalesAreaDetail(customer.getSalesarea());
			if(salesArea != null){
				customer.setSalesareaname(salesArea.getName());
			}
			//客户业务员
			Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getSalesuserid());
			if(null != personnel){
				customer.setSalesusername(personnel.getName());
			}
			SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getState(), "state");
			if(null != sysCode){
				customer.setStatename(sysCode.getCodename());
			}
		}
		return new PageData(customerMapper.getCustomerCountForPact(pageMap), customerList, pageMap);
	}

	@Override
	public PageData getCustomerListForCombobox(PageMap pageMap)
			throws Exception {
		if(null != pageMap.getCondition().get("customerid")){
			String customerid = pageMap.getCondition().get("customerid").toString();
			if(customerid.indexOf(",") != -1){
				String[] idArr = customerid.split(",");
				List<String> list = new ArrayList<String>(Arrays.asList(idArr));
				pageMap.getCondition().put("list", list);
			}
			else{
				String idArr[] = new String[1];
				idArr[0] = customerid;
				List<String> list = new ArrayList<String>(Arrays.asList(idArr));
				pageMap.getCondition().put("list", list);
			}
		}
		else{
			pageMap.getCondition().put("list", null);
		}
		if(null != pageMap.getCondition().get("customersort")){
			String customersorts = pageMap.getCondition().get("customersort").toString();
			String str = "";
			if(customersorts.indexOf(",") != -1){
				String[] customersortArr = customersorts.split(",");
				for(String customersort : customersortArr){
					if(StringUtils.isEmpty(str)){
						str = "a.customersort like '"+customersort+"%'";
					}else{
						str += " or " + "a.customersort like '"+customersort+"%'";
					}
				}
			}else{
				if(StringUtils.isEmpty(str)){
					str = "a.customersort like '"+customersorts+"%'";
				}else{
					str += " or " + "a.customersort like '"+customersorts+"%'";
				}
			}
			pageMap.getCondition().put("customersort", str);
		}
		List<Customer> list = customerMapper.getCustomerListForCombobox(pageMap);
		for(Customer customer : list){
			Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getSalesuserid());
			if(null != personnel){
				customer.setSalesusername(personnel.getName());
			}
			SalesArea salesArea = getSalesAreaDetail(customer.getSalesarea());
			if(null != salesArea){
				customer.setSalesareaname(salesArea.getThisname());
			}
			CustomerSort customerSort = getCustomerSortDetail(customer.getCustomersort());
			if(null != customerSort){
				customer.setCustomersortname(customerSort.getThisname());
			}
		}
		PageData pageData = new PageData(customerMapper.getCustomerListForComboboxCount(pageMap),
				list,pageMap);
		return pageData;
	}

	/**客户档案方法结束*/

	public SalesAreaMapper getSalesAreaMapper() {
		return salesAreaMapper;
	}

	public void setSalesAreaMapper(SalesAreaMapper salesAreaMapper) {
		this.salesAreaMapper = salesAreaMapper;
	}

	public CustomerSortMapper getCustomerSortMapper() {
		return customerSortMapper;
	}

	public void setCustomerSortMapper(CustomerSortMapper customerSortMapper) {
		this.customerSortMapper = customerSortMapper;
	}

	public CustomerMapper getCustomerMapper() {
		return customerMapper;
	}

	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	public CustomerAndSortMapper getCustomerAndSortMapper() {
		return customerAndSortMapper;
	}

	public void setCustomerAndSortMapper(CustomerAndSortMapper customerAndSortMapper) {
		this.customerAndSortMapper = customerAndSortMapper;
	}

	public ContacterMapper getContacterMapper() {
		return contacterMapper;
	}

	public void setContacterMapper(ContacterMapper contacterMapper) {
		this.contacterMapper = contacterMapper;
	}

	public GoodsMapper getGoodsMapper() {
		return goodsMapper;
	}

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	@Override
	public List getCustomerBySalesuserid(String salesuserid) throws Exception {
		List list = customerMapper.getCustomerBySalesuserid(salesuserid);
		return list;
	}

	@Override
	public PageData getCustomerSelectListData(PageMap pageMap) throws Exception {
		String isDataSql = (String) pageMap.getCondition().get("isdatasql");
		if(!"2".equals(isDataSql)){
			String dataSql = getDataAccessRule("t_base_sales_customer", "t");
			pageMap.getCondition().put("datasql", dataSql);

			SysUser sysUser = getSysUser();
			//品牌业务员与厂家业务员不能同时存在
			//判断是否品牌业务员
			String brandUserRoleName = getSysParamValue("BrandUserRoleName");
			boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
			if(isBrandUser){
				pageMap.getCondition().put("isBrandUser", true);
				pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
			}else{
				//判断是否厂家业务员
				String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
				boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
				if(isSupplierUser){
					pageMap.getCondition().put("isSupplierUser", true);
					pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
				}
			}
		}
		//控件传过来的参数 生成sql语句
		String paramRule = (String) pageMap.getCondition().get("paramRule");
		String paramRuleSql = RuleJSONUtils.widgetParamToSql(paramRule,"t");
		pageMap.getCondition().put("paramRuleSql", paramRuleSql);
        //多条件模糊查询 以空格为标识
        if(pageMap.getCondition().containsKey("id")){
            String id = (String) pageMap.getCondition().get("id");
            if(StringUtils.isNotEmpty(id) && id.indexOf(" ")>=0){
                String[] conArr = id.split(" ");
                List<String> conList  = new ArrayList<String>();
                for(String con : conArr){
                    con = con.trim();
                    if(StringUtils.isNotEmpty(con)){
                        conList.add(con);
                    }
                }
                pageMap.getCondition().put("con",conList);
            }
        }
		List<Customer> list = customerMapper.getCustomerSelectListData(pageMap);
		for(Customer customer: list){
			CustomerSort customerSort = getCustomerSortDetail(customer.getCustomersort());
			if(customerSort != null){
				customer.setCustomersortname(customerSort.getName());
			}
			SalesArea salesArea = getSalesAreaDetail(customer.getSalesarea());
			if(salesArea != null){
				customer.setSalesareaname(salesArea.getName());
			}
			Map map = new HashMap();
			map.put("id", customer.getContact());
			Contacter contacter = getContacterMapper().getContacterDetail(map);
			if(null!=contacter){
				customer.setContactname(contacter.getName());
			}
			Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getSalesuserid());
			if(null!=personnel){
				customer.setSalesusername(personnel.getName());
			}
		}
		PageData pageData = new PageData(customerMapper.getCustomerSelectListDataCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public boolean customerNameNoUsed(String name) throws Exception {
		return customerMapper.customerNameNoUsed(name) > 0;
	}

	@Override
	public boolean addCustomerShortcut(Customer customer) throws Exception {
		if(StringUtils.isEmpty(customer.getThisid())){
			customer.setThisid(customer.getId());
		}
		if(StringUtils.isEmpty(customer.getPinyin()) && StringUtils.isNotEmpty(customer.getName())){
			customer.setPinyin(CommonUtils.getPinYingJCLen(customer.getName()));
		}
		int i = customerMapper.addCustomer(customer);
		if(i > 0){
			if(StringUtils.isNotEmpty(customer.getCustomersort())){
				CustomerAndSort customerAndSort = new CustomerAndSort();
				customerAndSort.setCustomerid(customer.getId());
				customerAndSort.setDefaultsort("1");
				customerAndSort.setSortid(customer.getCustomersort());
				Map map = new HashMap();
				map.put("id", customer.getCustomersort());
				CustomerSort sort = customerSortMapper.getCustomerSortDetail(map);
				if(null != sort){
					customerAndSort.setSortname(sort.getName());
				}
				customerAndSortMapper.addCustomerAndSort(customerAndSort);
			}
		}
		return i > 0;
	}

	@Override
	public boolean editCustomerShortcut(Customer customer) throws Exception {
		if(StringUtils.isEmpty(customer.getPinyin()) && StringUtils.isNotEmpty(customer.getName())){
			String pinyin = CommonUtils.getPinYingJCLen(customer.getName());
			if(pinyin.length() > 20){
				pinyin = pinyin.substring(0,20);
			}
			customer.setPinyin(pinyin);
		}
		int i = customerMapper.updateCustomer(customer);
		if(i > 0){
			List<CustomerAndSort> list = customerAndSortMapper.getCustomerAndSortListByCustomer(customer.getId());
			if(list.size() != 0){
				for(CustomerAndSort sort : list){
					if("1".equals(sort.getDefaultsort())){
						if(StringUtils.isNotEmpty(customer.getCustomersort())){
							sort.setSortid(customer.getCustomersort());
							customerAndSortMapper.updateCustomerAndSort(sort);
						}
						else{
							customerAndSortMapper.deleteCustomerAddSort(sort.getId());
						}
					}
				}
			}
			//客户编码变动，人员档案对应客户做相应变动
			if(!customer.getOldid().equals(customer.getId())){
				List<PersonnelCustomer> customerList = getBasePersonnelMapper().getCustomerByCustomerid(customer.getOldid());
				if(customerList.size() != 0){
					getBasePersonnelMapper().editPersonCustomer(customer.getOldid(), customer.getId());
				}
				List<PersonnelBrandAndCustomer> brandcustomerList = getBasePersonnelMapper().getBrandCustomerListByCustomerid(customer.getOldid());
				if(brandcustomerList.size() != 0){
					getBasePersonnelMapper().editPersonBrandAndCustomerByCustomerid(customer.getOldid(), customer.getId());
				}
			}
		}
		return i > 0;
	}

	@Override
	public Map editCustomer(Customer customer, List<CustomerAndSort> csList)
			throws Exception {
		boolean retFlag = false,unEditFlag = true,lockFlag = true;
		if(null != customer){
			//保存修改前判断数据是否已经被加锁 可以修改
			if(isLockEdit("t_base_sales_customer",customer.getOldid())){
				Map map = new HashMap();
				map.put("id", customer.getOldid());
				Customer beforeCustomer = customerMapper.getCustomerDetail(map);
				if(canBasefilesIsEdit("t_base_sales_customer",beforeCustomer,customer)){
					boolean csFlag = true;
					retFlag = customerMapper.editMoreCustomer(customer) > 0;
					if(retFlag){
						//处理默认销售部门名称
						customerMapper.updateSalesdeptname();
						if(null != csList && csList.size() != 0){
							List<CustomerAndSort> addList = new ArrayList<CustomerAndSort>();
							for(CustomerAndSort customerAndSort : csList){
								if("1".equals(customerAndSort.getDefaultsort()) && StringUtils.isNotEmpty(customerAndSort.getSortid())){
									if(StringUtils.isNotEmpty(customerAndSort.getId())){//存在
										csFlag = customerAndSortMapper.updateCustomerAndSort(customerAndSort) > 0;
									}
									else{
										csFlag = customerAndSortMapper.addCustomerAndSort(customerAndSort) > 0;
									}
								}
							}
						}
						//对方联系人
						if(StringUtils.isNotEmpty(customer.getContactname())){
							if(StringUtils.isNotEmpty(customer.getContact())){
								Contacter contacter2 = new Contacter();
								contacter2.setOldid(customer.getContact());
								contacter2.setName(customer.getContactname());
								contacter2.setMobile(customer.getMobile());
								contacter2.setIsdefault("1");
								contacterMapper.updateContacter(contacter2);
							}
							else{
								Contacter contacter2 = new Contacter();
								contacter2.setName(customer.getContactname());
								if(StringUtils.isNotEmpty(customer.getMobile())){
									contacter2.setMobile(customer.getMobile());
								}
								contacter2.setState("1");
								contacter2.setIsdefault("1");
								contacter2.setCustomer(customer.getId());
								if(contacterMapper.addContacter(contacter2) > 0){
									List<Contacter> clist = contacterMapper.getContacterByName(customer.getContactname());
									if(clist.size() != 0){
										customer.setContact(clist.get(0).getId());
										customer.setOldid(customer.getId());
									}
									customerMapper.updateCustomer(customer);
								}
							}
						}
						else{
							if(StringUtils.isNotEmpty(customer.getContact())){
								contacterMapper.deleteContacter(customer.getContact());
								customer.setContact("");
								customerMapper.updateCustomer(customer);
							}
						}
					}
					retFlag = retFlag && csFlag;
				}
				else{
					unEditFlag = false;
				}
			}
			else{
				lockFlag = false;
			}
		}
		Map map2 = new HashMap();
		map2.put("retFlag", retFlag);
		map2.put("unEditFlag", unEditFlag);
		map2.put("lockFlag", lockFlag);
		return map2;
	}

	@Override
	public PageData getGoodsListPage(PageMap pageMap) throws Exception {
		List<CustomerGoods> list = customerMapper.getCustomerGoodsList(pageMap);
		if(list.size() != 0){
			for(CustomerGoods customerGoods : list){
				GoodsInfo goodsInfo = getBaseGoodsMapper().getBaseGoodsInfo(customerGoods.getGoodsid());
				if(null != goodsInfo){
					customerGoods.setGoodsname(goodsInfo.getName());
				}
			}
		}
		PageData pageData = new PageData(customerMapper.getCustomerGoodsCount(pageMap),
				list,pageMap);
		return pageData;
	}

	@Override
	public Map addDRCustomer(Customer customer) throws Exception {
		String failStr = "",closeVal = "",repeatVal = "",errorVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0,errorNum = 0;
		boolean flag = false;
		Map map = new HashMap();
		try {
			//判断人员档案是否重复
			Customer customer2 = customerMapper.getCustomerInfo(customer.getId());
			if(customer2 == null){//不重复
				SysUser sysUser = getSysUser();
				customer.setAdduserid(sysUser.getUserid());
				customer.setAddusername(sysUser.getName());
				customer.setAdddeptid(sysUser.getDepartmentid());
				customer.setAdddeptname(sysUser.getDepartmentname());
				if(StringUtils.isEmpty(customer.getState())){
					customer.setState("2");
				}
				if(StringUtils.isNotEmpty(customer.getPid())){
					customer.setIslast("1");
				}
				else{
					customer.setIslast("0");
				}
				if(StringUtils.isEmpty(customer.getPinyin()) && StringUtils.isNotEmpty(customer.getName())){
					customer.setPinyin(CommonUtils.getPinYingJCLen(customer.getName()));
				}
				if(StringUtils.isNotEmpty(customer.getSalesdeptid())){
					DepartMent departMent = getDepartmentByDeptid(customer.getSalesdeptid());
					if(null != departMent){
						customer.setSalesdeptname(departMent.getName());
					}
				}
				else if(StringUtils.isNotEmpty(customer.getSalesdeptname())){
					List<DepartMent> list = getBaseDepartMentMapper().returnDeptIdByName(customer.getSalesdeptname());
					if(null != list && list.size() != 0){
						customer.setSalesdeptid(list.get(0).getId());
					}
				}
				if(StringUtils.isNotEmpty(customer.getSalesuserid())){
					Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getSalesuserid());
					if(null != personnel){
						customer.setSalesusername(personnel.getName());
					}
				}
				else if(StringUtils.isNotEmpty(customer.getSalesusername())){
					List<Personnel> list = getBasePersonnelMapper().returnPersnnelIdByName(customer.getSalesusername());
					if(null != list && list.size() != 0){
						customer.setSalesuserid(list.get(0).getId());
					}
				}
				if(StringUtils.isNotEmpty(customer.getTallyuserid())){
					Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getTallyuserid());
					if(null != personnel){
						customer.setTallyusername(personnel.getName());
					}
				}
				else if(StringUtils.isNotEmpty(customer.getTallyusername())){
					List<Personnel> list = getBasePersonnelMapper().returnPersnnelIdByName(customer.getTallyusername());
					if(null != list && list.size() != 0){
						customer.setTallyuserid(list.get(0).getId());
					}
				}
				if(StringUtils.isNotEmpty(customer.getContact())){
					customer.setContactname(customer.getContact());
					customer.setContact("");
				}
				flag = customerMapper.addCustomer(customer) > 0;
				if(!flag){
					if(StringUtils.isNotEmpty(failStr)){
						failStr += "," + customer.getId();
					}
					else{
						failStr = customer.getId();
					}
					failureNum++;
				}
				else{
					successNum++;
					//联系人
					if(StringUtils.isNotEmpty(customer.getContactname()) ||
						StringUtils.isNotEmpty(customer.getMobile())){
						Contacter contacter = new Contacter();
						contacter.setName(customer.getContactname());
						if(StringUtils.isNotEmpty(customer.getMobile())){
							contacter.setMobile(customer.getMobile());
						}
						contacter.setIsdefault("1");
						contacter.setState("1");
						contacter.setCustomer(customer.getId());
						if(contacterMapper.addContacter(contacter) > 0){
							List<Contacter> clist = contacterMapper.getContacterByName(customer.getContactname());
							if(clist.size() != 0){
								customer.setContact(clist.get(0).getId());
								customer.setOldid(customer.getId());
							}
							customerMapper.updateCustomer(customer);
						}
					}
				}
			}
			else{
				if("0".equals(customer2.getState())){//禁用状态，不允许导入
					if(StringUtils.isEmpty(closeVal)){
						closeVal = customer2.getId();
					}
					else{
						closeVal += "," + customer2.getId();
					}
					closeNum++;
				}
				else{
					if(StringUtils.isEmpty(repeatVal)){
						repeatVal = customer2.getId();
					}
					else{
						repeatVal += "," + customer2.getId();
					}
					repeatNum++;
				}
			}
		} catch (Exception e) {
			if(StringUtils.isEmpty(repeatVal)){
				errorVal = customer.getId();
			}
			else{
				errorVal += "," + customer.getId();
			}
			errorNum++;
		}
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatNum", repeatNum);
		map.put("repeatVal", repeatVal);
		map.put("errorNum", errorNum);
		map.put("errorVal", errorVal);
		return map;
	}

	@Override
	public Map addDRCustomerAndPrice(List<CustomerPrice> list)
			throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if(list.size() != 0){
			for(CustomerPrice customerPrice : list){
				try {
					if(null != customerPrice.getId()){
						CustomerPrice customerPrice2 = customerMapper.getCustomerPrice(customerPrice.getId());
						if(null != customerPrice2){
							customerPrice.setId(null);
							flag = customerMapper.addCustomerPrice(customerPrice) > 0;
						}
					}
					else{
						flag = customerMapper.addCustomerPrice(customerPrice) > 0;
					}
				} catch (Exception e) {
					flag = false;
					break;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addDRCustomerAndSort(List<CustomerAndSort> list)
			throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if(list.size() != 0){
			for(CustomerAndSort customerAndSort : list){
				try {
					if(null != customerAndSort.getId()){
						CustomerAndSort customerAndSort2 = customerAndSortMapper.getCustomerAndSortDetail(customerAndSort);
						if(null != customerAndSort2){
							customerAndSort.setId(null);
							flag = customerAndSortMapper.addCustomerAndSort(customerAndSort) > 0;
						}
					}
					else{
						flag = customerAndSortMapper.addCustomerAndSort(customerAndSort) > 0;
					}
				} catch (Exception e) {
					flag = false;
					break;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addShortcutCustomerExcel(List<Customer> list)
			throws Exception {
		boolean flag = false,cflag = true,sflag = true;
		String failStr = "",closeVal = "",repeatVal = "",errorVal = "",noidval = "",repeateIndex="",closeIndex="",errorIndex="",noidindex = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0,errorNum = 0,noidnum = 0;
		Map returnMap = new HashMap();
		if(list.size() != 0){
			for(Customer customer: list){
				try {
					Customer customer2 = customerMapper.getCustomerInfo(customer.getId());
					if(null != customer2){//重复
						if("0".equals(customer2.getState())){//禁用状态，不允许导入
							if(StringUtils.isEmpty(closeVal)){
								closeVal = customer.getId();
                                closeIndex = (list.indexOf(customer)+2)+"";
							}
							else{
								closeVal += "," + customer.getId();
                                closeIndex += "," + (list.indexOf(customer)+2)+"";
							}
							closeNum++;
						}
						else{
							if(StringUtils.isEmpty(repeatVal)){
								repeatVal = customer.getId();
                                repeateIndex = (list.indexOf(customer)+2) + "";
							}
							else{
								repeatVal += "," + customer.getId();
                                repeateIndex += "," + (list.indexOf(customer)+2);
							}
							repeatNum++;
						}
					}
					else{
						List<CustomerAndSort> sList = new ArrayList<CustomerAndSort>();
						if(StringUtils.isNotEmpty(customer.getId())){
							customer.setThisid(customer.getId());
							//上级客户
							if(null != customer.getPname()){
								List<Customer> clist = getBaseCustomerMapper().getCustomerByName(customer.getPname());
								if(clist.size() != 0){
									customer.setPid(clist.get(0).getId());
								}
							}
							//简称
							if(StringUtils.isEmpty(customer.getPinyin()) && StringUtils.isNotEmpty(customer.getName())){
                                String word = CommonUtils.getPinYingJCLen(customer.getName());
                                if(word.length()>20){
                                    word = word.substring(0,20);
                                }
								customer.setPinyin(word);
							}
							if(null != customer.getNaturename()){
								String natureSysCode = getBaseSysCodeMapper().codenametocode(customer.getNaturename(), "firm_nature");
								if(StringUtils.isNotEmpty(natureSysCode)){
									customer.setNature(natureSysCode);
								}
							}
							if(null != customer.getIschainname()){
								String yesno = getBaseSysCodeMapper().codenametocode(customer.getIschainname(), "yesorno");
								if(StringUtils.isNotEmpty(yesno)){
									customer.setIschain(yesno);
								}
							}
							//是否总店
							if(StringUtils.isNotEmpty(customer.getIslastname())){
								String islast = getBaseSysCodeMapper().codenametocode(customer.getIslastname(), "islast");
								if(StringUtils.isNotEmpty(islast)){
									customer.setIslast(islast);
								}
							}else{
								customer.setIslast("1");
							}
							//结算方式
							if(null != customer.getSettletypename()){
								Settlement settlement = getBaseFinanceMapper().getSettlementListByName(customer.getSettletypename());
								if(null!=settlement){
									customer.setSettletype(settlement.getId());
								}
							}else{
								//取系统默认结算方式
								String value = getSysParamValue("SETTLETYPE");
								if(null!=value){
									customer.setSettletype(value);
								}
							}
							//支付方式
							List<Payment> payList = getBaseFinanceMapper().returnPaymentIdByName(customer.getPaytypename());
							if(payList.size() != 0){
								customer.setPaytype(payList.get(0).getId());
							}
							if(null != customer.getIscashname()){
								String yesno = getBaseSysCodeMapper().codenametocode(customer.getIscashname(), "yesorno");
								if(StringUtils.isNotEmpty(yesno)){
									customer.setIscash(yesno);
								}
							}
							if(null != customer.getIslongtermname()){
								String yesno = getBaseSysCodeMapper().codenametocode(customer.getIslongtermname(), "yesorno");
								if(StringUtils.isNotEmpty(yesno)){
									customer.setIslongterm(yesno);
								}
							}
							if(null != customer.getTickettypename()){
								if("增值税发票".equals(customer.getTickettypename())){
									customer.setTickettype("1");
								}else if("普通发票".equals(customer.getTickettypename())){
									customer.setTickettype("2");
								}
							}
							//核销方式
							if(null != customer.getCanceltypename()){
								String canceltype = getBaseSysCodeMapper().codenametocode(customer.getCanceltypename(), "canceltype");
								if(StringUtils.isNotEmpty(canceltype)){
									customer.setCanceltype(canceltype);
								}
							}
							//价格套
							if(null != customer.getPricesortname()){
								String pricesort = getBaseSysCodeMapper().codenametocode(customer.getPricesortname(), "price_list");
								if(StringUtils.isNotEmpty(pricesort)){
									customer.setPricesort(pricesort);
								}
							}
							if(null != customer.getPromotionsortname()){
								String promotionsortSysCode = getBaseSysCodeMapper().codenametocode(customer.getPromotionsortname(), "promotionsort");
								if(StringUtils.isNotEmpty(promotionsortSysCode)){
									customer.setPromotionsort(promotionsortSysCode);
								}
							}
							//所属区域
							List<SalesArea> aList = getBaseSalesAreaMapper().returnSalesAreaIdByName(customer.getSalesareaname());
							if(aList.size() != 0){
								customer.setSalesarea(aList.get(0).getId());
							}else{
                                customer.setSalesareaname("");
                            }
							//所属分类
							CustomerSort customerSort = getBaseCustomerSortMapper().returnCustomerSortIdByName(customer.getCustomersortname());
							if(null != customerSort){
								customer.setCustomersort(customerSort.getId());
								CustomerAndSort customerAndSort = new CustomerAndSort();
								customerAndSort.setCustomerid(customer.getId());
								customerAndSort.setDefaultsort("1");
								customerAndSort.setSortid(customerSort.getId());
								customerAndSort.setSortname(customer.getCustomersortname());
								sflag = customerAndSortMapper.addCustomerAndSort(customerAndSort) > 0;
							}else{
                                customer.setCustomersortname("");
                            }
							if(null != customer.getAbclevelname()){
								if("A".equals(customer.getAbclevelname())){
									customer.setAbclevel("A");
								}else if("B".equals(customer.getAbclevelname())){
									customer.setAbclevel("B");
								}else if("C".equals(customer.getAbclevelname())){
									customer.setAbclevel("C");
								}else if("D".equals(customer.getAbclevelname())){
									customer.setAbclevel("D");
								}else{
                                    customer.setAbclevelname("");
                                }
							}
							//默认销售部门
							if(StringUtils.isNotEmpty(customer.getSalesdeptname())){
								List<DepartMent> dList = getBaseDepartMentMapper().returnDeptIdByName(customer.getSalesdeptname());
								if(dList.size() != 0){
									customer.setSalesdeptid(dList.get(0).getId());
									customer.setSalesdeptname(customer.getSalesdeptname());
								}else{
                                    customer.setSalesdeptname("");
                                }
							}
							//默认业务员
							if(StringUtils.isNotEmpty(customer.getSalesusername())){
								List<Personnel> pList = getBasePersonnelMapper().returnPersnnelIdByName(customer.getSalesusername());
								if(pList.size() != 0){
									customer.setSalesuserid(pList.get(0).getId());
									customer.setSalesusername(pList.get(0).getName());
								}else{
                                    customer.setSalesusername("");
                                }
							}
							//默认理货员
							if(StringUtils.isNotEmpty(customer.getTallyusername())){
								List<Personnel> tList = getBasePersonnelMapper().returnPersnnelIdByName(customer.getTallyusername());
								if(tList.size() != 0){
									customer.setTallyuserid(tList.get(0).getId());
									customer.setTallyusername(customer.getTallyusername());
								}else{
                                    customer.setTallyusername("");
                                }
							}
							//默认销售内勤
							if(StringUtils.isNotEmpty(customer.getIndoorstaffname())){
								List<Personnel> indoorList = getBasePersonnelMapper().returnPersnnelIdByName(customer.getIndoorstaffname());
								if(indoorList.size() != 0){
									customer.setIndoorstaff(indoorList.get(0).getId());
								}else{
                                    customer.setIndoorstaffname("");
                                }
							}
							//收款人
							if(StringUtils.isNotEmpty(customer.getPayeename())){
								List<Personnel> payeeList = getBasePersonnelMapper().returnPersnnelIdByName(customer.getPayeename());
								if(payeeList.size() != 0){
									customer.setPayeeid(payeeList.get(0).getId());
								}else{
                                    customer.setPayeename("");
                                }
							}
							//状态
							if(StringUtils.isNotEmpty(customer.getStatename())){
								String state = getBaseSysCodeMapper().codenametocode(customer.getStatename(), "state");
								if(StringUtils.isNotEmpty(state)){
									customer.setState(state);
								}
							}
							else{
								customer.setState("1");
							}
							//超账期控制
							if(null != customer.getOvercontrolname()){
								if("是".equals(customer.getOvercontrolname())){
									customer.setOvercontrol("1");
								}
								else if("否".equals(customer.getOvercontrolname())){
									customer.setOvercontrol("0");
								}
							}
							if(null != customer.getCreditratingname()){
								String creditrating = getBaseSysCodeMapper().codenametocode(customer.getCreditratingname(), "creditrating");
								if(StringUtils.isNotEmpty(creditrating)){
									customer.setCreditrating(creditrating);
								}
							}
                            int a = customerMapper.addCustomer(customer);
							cflag = a > 0;
							if(cflag && sflag){
								flag = true;
								successNum++;
							}
							else{
								failureNum++;
								if(StringUtils.isNotEmpty(failStr)){
									failStr += "," + customer.getId();
								}
								else{
									failStr = customer.getId();
								}
							}
						}else{
							if(StringUtils.isEmpty(noidval)){
								noidval = customer.getName();
								noidindex = (list.indexOf(customer)+2)+"";
							}
							else{
								noidval += "," + customer.getName();
								noidindex +="," + (list.indexOf(customer)+2);
							}
							noidnum++;
						}
					}
				} catch (Exception e) {
                    System.out.println(e);
					//删除对应分类
					customerAndSortMapper.deleteCustomerAddSortByCustomer(customer.getId());
					if(StringUtils.isEmpty(repeatVal) && StringUtils.isEmpty(errorVal)){
						errorVal = customer.getId();
                        errorIndex = (list.indexOf(customer)+2)+"";
					}else{
						errorVal += "," + customer.getId();
                        errorIndex +="," + (list.indexOf(customer)+2);
					}
					errorNum++;
					continue;
				}
			}
		}
        if(repeateIndex != ""){
            repeatVal = repeatVal+"(导入数据中第"+repeateIndex+"行数据) ";
        }
        if(closeIndex != ""){
            closeVal = closeVal +"(导入数据中第"+closeIndex+"行数据) ";
        }
        if(errorIndex != ""){
            errorVal = errorVal +"(导入数据中第"+errorIndex+"行数据) 导入格式";
        }
		if(noidindex != ""){
			noidval = noidval +"(导入数据中第"+noidindex+"行数据) 编码为空";
		}
		returnMap.put("flag", flag);
		returnMap.put("success", successNum);
		returnMap.put("failure", failureNum);
		returnMap.put("failStr", failStr);
		returnMap.put("repeatNum", repeatNum);
		returnMap.put("repeatVal", repeatVal);
		returnMap.put("closeNum", closeNum);
		returnMap.put("closeVal", closeVal);
		returnMap.put("errorNum", errorNum);
		returnMap.put("errorVal", errorVal);
		returnMap.put("noidval", noidval);
		returnMap.put("noidnum", noidnum);
		return returnMap;
	}

	@Override
	public boolean addCustomerPrice(CustomerPrice customerPrice)
			throws Exception {
		return customerMapper.addCustomerPrice(customerPrice) > 0;
	}

	@Override
	public boolean copyCustomerPrice(Map map)throws Exception {
		String goodspriceids = (String)map.get("goodspriceids");
		String customerids = (String)map.get("customerids");
		String goodsids = (String)map.get("goodsids");
		String type = (String)map.get("type");//是否覆盖1是0否
		if(StringUtils.isNotEmpty(goodspriceids)){
			if("1".equals(type)){
				String[] goodspriceidArr = goodspriceids.split(",");
				Map map2 = new HashMap();
				map2.put("idArr", goodspriceidArr);
				List<CustomerPrice> list = customerMapper.getCustomerPriceListByMap(map2);
				if(list.size() != 0){
					boolean retflag = true;
					String[] customerArr = customerids.split(",");
					//删除已存在的客户合同商品
					String[] goodsidArr = goodsids.split(",");
					map2.put("goodsidArr", goodsidArr);
					for(String customerid : customerArr){
						map2.put("customerid", customerid);
						customerMapper.deleteExistCustomerPrice(map2);
						for(CustomerPrice customerPrice : list){
							customerPrice.setCustomerid(customerid);
						}
						boolean flag = customerMapper.addCustomerPriceMoreTotal(list) > 0;
						retflag = retflag && flag;
					}
					return retflag;
				}
			}else{
				String[] goodspriceidArr = goodspriceids.split(",");
				Map map2 = new HashMap();
				map2.put("idArr", goodspriceidArr);
				List<CustomerPrice> list = customerMapper.getCustomerPriceListByMap(map2);
				if(list.size() != 0){
					boolean retflag = true;
					String[] customerArr = customerids.split(",");
					for(String customerid : customerArr){
						List<CustomerPrice> list2 = new ArrayList<CustomerPrice>();
						map2.put("customerid", customerid);
						for(CustomerPrice customerPrice : list){
							map2.put("goodsid", customerPrice.getGoodsid());
							boolean flag = customerMapper.doCheckIsExistCustomerPrice(map2) > 0;
							if(!flag){
								customerPrice.setCustomerid(customerid);
								list2.add(customerPrice);
							}
						}
						if(list2.size() != 0){
							boolean flag = customerMapper.addCustomerPriceMoreTotal(list2) > 0;
							retflag = retflag && flag;
						}
					}
					return retflag;
				}
			}
		}
		return false;
	}

	@Override
	public Map doCheckIsExistCustomerPrice(String goodsids,String customerids) throws Exception {
		String retcustomerids = "";
		String[] goodsidArr = goodsids.split(",");
		Map map = new HashMap();
		map.put("goodsidArr", goodsidArr);
		String[] customerArr = customerids.split(",");
		for(String customerid : customerArr){
			map.put("customerid", customerid);
			boolean flag = customerMapper.doCheckIsExistCustomerPrice(map) > 0;
			if(flag){
				if(StringUtils.isEmpty(retcustomerids)){
					retcustomerids = customerid;
				}else{
					retcustomerids += "," + customerid;
				}
			}
		}
		Map retmap = new HashMap();
		if(StringUtils.isEmpty(retcustomerids)){
			retmap.put("flag", false);
		}else{
			retmap.put("flag", true);
			retmap.put("retcustomerids", retcustomerids);
		}
		return retmap;
	}

	@Override
	public boolean deleteCustomerPriceById(String id) throws Exception {
		return customerMapper.deleteCustomerPriceById(id) > 0;
	}

	@Override
	public boolean updateCustomerPrice(CustomerPrice customerPrice)
			throws Exception {
		return customerMapper.updateCustomerPrice(customerPrice) > 0;
	}

	@Override
	public List getCustoemrPriceListByCustomerid(String customerid)
			throws Exception {
		return customerMapper.getCustoemrPriceListByCustomerid(customerid);
	}

	@Override
	public CustomerPrice getCustomerPriceInfo(String id) throws Exception {
		CustomerPrice customerPrice = customerMapper.getCustomerPrice(id);
		//获取价格套价格，税率
		GoodsInfo goodsInfo = getBaseGoodsMapper().getBaseGoodsInfo(customerPrice.getGoodsid());
		if(null != goodsInfo){
			TaxType taxType = getBaseFinanceMapper().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
			if(null != taxType && null != taxType.getRate()){
				BigDecimal taxrate = taxType.getRate().divide(new BigDecimal("100")).add(new BigDecimal("1"));
				customerPrice.setTaxrate(taxrate);
			}
		}
		Customer customer = getCustomerInfo(customerPrice.getCustomerid());
		if(null != customer){
			GoodsInfo_PriceInfo gPriceInfo = getBaseGoodsMapper().getPriceDataByGoodsidAndCode(customerPrice.getGoodsid(), customer.getPricesort());
			if(null != gPriceInfo){
				customerPrice.setTaxprice(gPriceInfo.getTaxprice());
			}
		}
        if(null != goodsInfo && null != customer){
            Map map = goodsMapper.getTaxPriceByGoodsidAndPriceCode(goodsInfo.getId(), customer.getPricesort());
            if(null != map && !map.isEmpty()){
				BigDecimal boxprice = null != map.get("boxprice") ? (BigDecimal)map.get("boxprice") : BigDecimal.ZERO;
				customerPrice.setBoxprice(boxprice);
			}
        }
		return customerPrice;
	}

	@Override
	public boolean deleteCustomerPrices(String idstr) throws Exception {
		String[] idarr = idstr.split(",");
		int count = 0;
		for(String id : idarr){
			if(customerMapper.deleteCustomerPrices(id)>0){
				count++;
			}
		}
		int idcount = idarr.length;
		boolean flag = idcount == count;
		return flag;
	}


	@Override
	public PageData showPriceCustomerData(PageMap pageMap) throws Exception {
		List<CustomerPrice> pclist = customerMapper.getCustoemrPriceList(pageMap);
		if(pclist.size() != 0){
			for(CustomerPrice customerPrice : pclist){
				Customer customer = customerMapper.getCustomerInfo(customerPrice.getCustomerid());
				if(null != customer){
					customerPrice.setCustomername(customer.getName());
					customerPrice.setShortcode(customer.getShortcode());
					//上级客户
					if(StringUtils.isNotEmpty(customer.getPid())){
						customerPrice.setPcustomerid(customer.getPid());
						Customer customer2 = customerMapper.getCustomerInfo(customer.getPid());
						if(null != customer2){
							customerPrice.setPcustomername(customer2.getName());
						}
					}
					GoodsInfo goodsInfo = getBaseGoodsMapper().getGoodsInfo(customerPrice.getGoodsid());
					if(null != goodsInfo){
						customerPrice.setGoodsname(goodsInfo.getName());
						customerPrice.setGoodsspell(goodsInfo.getSpell());
						customerPrice.setBarcode(goodsInfo.getBarcode());
					}
				}
			}
		}
		PageData pageData = new PageData(customerMapper.getCustoemrPriceCount(pageMap),pclist,pageMap);
		return pageData;
	}

	@Override
	public Map addDRPriceCustomer(List<CustomerPrice> list) throws Exception {
		int successNum = 0,failureNum = 0,coverNum = 0,closeNum = 0,emptPriceNum = 0,goodsidNum=0,spellNum=0,barcodeNum=0;
        //导入的所有客户都不存在
        int failureCustomerNum = -1;
        //系统中不存在的客户编码及其所在行、编码、个数
        String emptCustomerID = "",emptIndex = "",emptVal = "",emptSize="";
        //价格为空的商品编码，所在行
        String emptPrice="",emptPriceIndex="";
        //商品覆盖，商品编码，商品助记码 参数
		String coverVal = "",coverIndex = "",goodsidVal="",goodsidIndex="",goodsspellVal="",spellIndex="",barcodeVal="",barcodeIndex="",closeIndex="",closeval="";
		Map map2 = new HashMap();

        if(list.size() != 0){
            //判断导入的客户编码是否存在
            Map map = checkImportCustomer(list);
            String customerStr = (String) map.get("customerID");
            //字段为空，表示系统中不存在对应的客户编码
            if(StringUtils.isEmpty(customerStr)){
                failureCustomerNum = list.size();

            }else{
                emptCustomerID = (String) map.get("emptCustomerID");
                emptIndex = (String) map.get("emptIndex");
                if(emptIndex != ""){
                    emptSize = (String) map.get("emptSize");
                    if(StringUtils.isNotEmpty(emptVal)){
                        emptVal = emptVal.substring(0,emptVal.length()-1);//去掉最后一个逗号
                    }
                }
                for(CustomerPrice cp : list){
                    Map cpMap = new HashMap();
                    if(StringUtils.isNotEmpty(cp.getCustomerid())){
                        Customer customer = customerMapper.getCustomerInfo(cp.getCustomerid());
                        if(null != customer){
                            cpMap = goodsInfoByCustPrice(cp,customer);
                        }

                    }else if(StringUtils.isNotEmpty(cp.getShortcode())){
                        Customer customer = customerMapper.getCustomerInfoLimitOne(cp.getShortcode());
                        if(null != customer){
                            cpMap = goodsInfoByCustPrice(cp,customer);
                        }
                    }
                    //对一条信息的导入结果进行解析
                    if(cpMap.containsKey("goodsid")){
                       ++ goodsidNum ;
                        if(StringUtils.isEmpty(goodsidVal)){
                            goodsidIndex = (list.indexOf(cp)+2)+"";
                            goodsidVal = (String) cpMap.get("goodsid");
                        }else{
                            goodsidIndex  += "," +(list.indexOf(cp)+2);
                            goodsidVal += "," +(String) cpMap.get("goodsid");
                        }
                    }
                    if(cpMap.containsKey("goodsspell")){
                       ++ spellNum;
                        if(StringUtils.isEmpty(goodsspellVal)){
                            spellIndex = (list.indexOf(cp)+2)+"";
                            goodsspellVal = (String)cpMap.get("goodsspell");
                        }else{
                            spellIndex+= "," +(list.indexOf(cp)+2);
                            goodsspellVal += ","+(String)cpMap.get("goodsspell");
                        }
                    }
					if(cpMap.containsKey("barcode")){
						++ barcodeNum;
						if(StringUtils.isEmpty(barcodeVal)){
							barcodeIndex = (list.indexOf(cp)+2)+"";
							barcodeVal = (String)cpMap.get("barcode");
						}else{
							barcodeIndex+= "," +(list.indexOf(cp)+2);
							barcodeVal += ","+(String)cpMap.get("barcode");
						}
					}
                    //是否被覆盖
                    if(cpMap.containsKey("coverVal")){
                        ++ coverNum;
                        if(StringUtils.isEmpty(coverVal)){
                            coverIndex = (list.indexOf(cp)+2)+"";
                            coverVal = (String) cpMap.get("coverVal");
                        }else{
                            coverIndex += "," +(list.indexOf(cp)+2);
                            coverVal += "," + (String) cpMap.get("coverVal");
                        }
                    }
                    //商品是否禁用
                    if(cpMap.containsKey("closeNum")){
                        ++ closeNum;
						if(StringUtils.isEmpty(closeval)){
							closeIndex = (list.indexOf(cp)+2)+"";
							closeval = (String) cpMap.get("closeval");
						}else{
							closeIndex += "," +(list.indexOf(cp)+2);
							closeval += "," + (String) cpMap.get("closeval");
						}
                    }
                    //客户商品价格是否为空
                    if(cpMap.containsKey("emptPrice")){
                        ++ emptPriceNum;
                        if(StringUtils.isEmpty(emptPrice)){
                            emptPrice = (String)cpMap.get("emptPrice");
                            emptPriceIndex = (list.indexOf(cp)+2)+"";
                        }else{
                            emptPrice += "," + (String) cpMap.get("emptPrice");
                            emptPriceIndex += ","+(list.indexOf(cp)+2);
                        }
                    }
                    //信息是否新增成功
                    if(cpMap.containsKey("result")){
                        String flag = (String) cpMap.get("result");
                        if("true".equals(flag)){
                            ++ successNum ;
                        }else{
                            ++ failureNum ;
                        }
                    }
                }
            }
        }
        if(emptPriceIndex != ""){
            emptPrice = emptPrice+"(导入数据中第"+emptPriceIndex+"行数据) ";
        }
        if(coverIndex != ""){
            coverVal = coverVal +"(导入数据中第"+coverIndex+"行数据) ";
        }
        if(emptIndex != ""){
            emptVal = emptVal +"(导入数据中第"+emptIndex+"行数据) ";
        }
		if(closeIndex != ""){
			closeval = closeval +"(导入数据中第"+closeIndex+"行数据) ";
		}
        if(goodsidIndex != ""){
            goodsidVal = goodsidVal  +"(导入数据中第"+goodsidIndex+"行数据) ";
        }
        if(spellIndex != ""){
            goodsspellVal = goodsspellVal +"(导入数据中第"+spellIndex+"行数据) ";
        }
		if(barcodeIndex != ""){
			barcodeVal = barcodeVal +"(导入数据中第"+barcodeIndex+"行数据) ";
		}

        if(failureCustomerNum != -1){
            map2.put("failureCustomerNum",failureCustomerNum);
        }

        map2.put("emptCustomerID",emptCustomerID);
        map2.put("emptVal",emptVal);
        map2.put("emptSize",emptSize);
        map2.put("emptPrice",emptPrice);
        map2.put("emptPriceNum",emptPriceNum);
        map2.put("goodsidVal",goodsidVal);
        map2.put("goodsidNum",goodsidNum);
        map2.put("goodsspellVal",goodsspellVal);
        map2.put("spellNum",spellNum);
		map2.put("barcodeVal",barcodeVal);
		map2.put("barcodeNum",barcodeNum);
		map2.put("success", successNum);
		map2.put("failure", failureNum);
        map2.put("closeNum", closeNum);
		map2.put("coverNum", coverNum);
		map2.put("coverVal", coverVal);
		map2.put("closeval", closeval);
		return map2;
	}

    public Map goodsInfoByCustPrice(CustomerPrice cp , Customer customer) throws Exception{
        Map m = new HashMap();

        cp.setCustomerid(customer.getId());
        GoodsInfo goodsInfo = null;
        if(StringUtils.isNotEmpty(cp.getGoodsid())){
            goodsInfo = getBaseGoodsMapper().getGoodsInfo(cp.getGoodsid());
            if(null == goodsInfo){
                m.put("goodsid",cp.getGoodsid());
            }else if("0".equals(goodsInfo.getState())){
				m.put("closeNum","0");
				m.put("closeval",goodsInfo.getId());
				return m;
			}
        }else if(StringUtils.isNotEmpty(cp.getGoodsspell())){
			goodsInfo = getBaseGoodsMapper().getGoodsInfoBySpellLimitOne(cp.getGoodsspell());
			if(null!=goodsInfo){
				cp.setGoodsid(goodsInfo.getId());
			}else{
				m.put("goodsspell",cp.getGoodsspell());
			}
        }else if(StringUtils.isNotEmpty(cp.getBarcode())){
			goodsInfo = getBaseGoodsMapper().getGoodsInfoByBarcodeLimitOne(cp.getBarcode());
			if(null!=goodsInfo){
                if("0".equals(goodsInfo.getState())){
                    Map map = new HashMap();
                    map.put("barcode",cp.getBarcode());
                    List<GoodsInfo> list = getBaseGoodsMapper().getGoodsInfoListByMap(map);
                    for(GoodsInfo goods : list){
                        if("1".equals(goods.getState())){
                            goodsInfo = goods;
                            break;
                        }
                    }
                }
				cp.setGoodsid(goodsInfo.getId());
			}else{
				m.put("barcode",cp.getBarcode());
			}
		}

        if(customerMapper.checkCustomerPrice(cp.getCustomerid(), cp.getGoodsid()) > 0){
            GoodsInfo_MteringUnitInfo goodsMU = goodsMapper.getMUInfoByGoodsIdAndIsdefault(cp.getGoodsid());
            BigDecimal boxnum = null != goodsMU.getRate() ? goodsMU.getRate() : BigDecimal.ZERO;
            //条形码
            if(StringUtils.isEmpty(cp.getBarcode())){
                cp.setBarcode(goodsInfo.getBarcode());
            }
            //价格套价格
            Map map = getBaseGoodsMapper().getTaxPriceByGoodsidAndPriceCode(goodsInfo.getId(), customer.getPricesort());
            if(null != map && map.containsKey("taxprice") && null != map.get("taxprice")){
                cp.setTaxprice((BigDecimal)map.get("taxprice"));
            }

            BigDecimal taxrate = new BigDecimal(0);
            TaxType taxType = getBaseFinanceMapper().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
            if(null != taxType){
                BigDecimal rate = (BigDecimal)taxType.getRate();
                taxrate = rate.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
                cp.setTaxrate(taxrate);
            }
            if(null == cp.getCtcboxprice() || cp.getCtcboxprice().compareTo(BigDecimal.ZERO) == 0){
                if(null != cp.getPrice() && cp.getPrice().compareTo(BigDecimal.ZERO) != 0){
                    cp.setCtcboxprice(cp.getPrice().multiply(boxnum));
                    if(taxrate.compareTo(BigDecimal.ZERO) != 0){
                        cp.setNoprice(cp.getPrice().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP));
                    }
                }else if(null != cp.getNoprice() && cp.getNoprice().compareTo(BigDecimal.ZERO) != 0){
                    cp.setPrice(cp.getNoprice().multiply(taxrate));
                    cp.setCtcboxprice(cp.getPrice().multiply(boxnum));
                }else{
                    m.put("emptPrice",cp.getGoodsid());
                    return m;
                }
            }else{
                if(null == cp.getPrice() || cp.getPrice().compareTo(BigDecimal.ZERO) == 0){
                    if(boxnum.compareTo(BigDecimal.ZERO) != 0){
                        cp.setPrice(cp.getCtcboxprice().divide(boxnum,6,BigDecimal.ROUND_HALF_UP));
                    }
                    if((null == cp.getNoprice() || cp.getNoprice().compareTo(BigDecimal.ZERO) == 0) && taxrate.compareTo(BigDecimal.ZERO) != 0){
                        cp.setNoprice(cp.getPrice().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP));
                    }
                }else if(null == cp.getNoprice() || cp.getNoprice().compareTo(BigDecimal.ZERO) == 0){
                    if(taxrate.compareTo(BigDecimal.ZERO) != 0){
                        cp.setNoprice(cp.getPrice().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            boolean fgflag = customerMapper.updateCustomerPriceByParam(cp) > 0;
            if(fgflag && !m.containsKey("emptPrice")){
                m.put("coverVal","客户:"+cp.getCustomerid()+"商品:"+cp.getGoodsid());
                m.put("flag",fgflag);
            }
        }else{
            if(null != goodsInfo){
                GoodsInfo_MteringUnitInfo goodsMU = goodsMapper.getMUInfoByGoodsIdAndIsdefault(cp.getGoodsid());
                BigDecimal boxnum = null != goodsMU.getRate() ? goodsMU.getRate() : BigDecimal.ZERO;
                if("0".equals(goodsInfo.getState())){
                    m.put("closeNum","0");
                } else{
                    //条形码
                    if(StringUtils.isEmpty(cp.getBarcode())){
                        cp.setBarcode(goodsInfo.getBarcode());
                    }
                    //价格套价格
                    Map map = getBaseGoodsMapper().getTaxPriceByGoodsidAndPriceCode(goodsInfo.getId(), customer.getPricesort());
                    if(null != map && map.containsKey("taxprice") && null != map.get("taxprice")){
                        cp.setTaxprice((BigDecimal)map.get("taxprice"));
                    }
                    Map map3 = new HashMap();
                    BigDecimal taxrate = new BigDecimal(0);
                    TaxType taxType = getBaseFinanceMapper().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
                    if(null != taxType){
                        BigDecimal rate = (BigDecimal)taxType.getRate();
                        taxrate = rate.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
                        cp.setTaxrate(taxrate);
                    }
                    if(null == cp.getCtcboxprice() || cp.getCtcboxprice().compareTo(BigDecimal.ZERO) == 0){
                        if(null != cp.getPrice() && cp.getPrice().compareTo(BigDecimal.ZERO) != 0){
                            cp.setCtcboxprice(cp.getPrice().multiply(boxnum));
                            if(taxrate.compareTo(BigDecimal.ZERO) != 0){
                                cp.setNoprice(cp.getPrice().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP));
                            }
                        }else if(null != cp.getNoprice() && cp.getNoprice().compareTo(BigDecimal.ZERO) != 0){
                            cp.setPrice(cp.getNoprice().multiply(taxrate));
                            cp.setCtcboxprice(cp.getPrice().multiply(boxnum));
                        }else{
							m.put("emptPrice",cp.getGoodsid());
							return m;
						}
                    }else{
                        if(null == cp.getPrice() || cp.getPrice().compareTo(BigDecimal.ZERO) == 0){
                            if(boxnum.compareTo(BigDecimal.ZERO) != 0){
                                cp.setPrice(cp.getCtcboxprice().divide(boxnum,6,BigDecimal.ROUND_HALF_UP));
                            }
                            if((null == cp.getNoprice() || cp.getNoprice().compareTo(BigDecimal.ZERO) == 0) && taxrate.compareTo(BigDecimal.ZERO) != 0){
                                cp.setNoprice(cp.getPrice().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP));
                            }
                        }else if(null == cp.getNoprice() || cp.getNoprice().compareTo(BigDecimal.ZERO) == 0){
                            if(taxrate.compareTo(BigDecimal.ZERO) != 0){
                                cp.setNoprice(cp.getPrice().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    boolean flag = customerMapper.addCustomerPrice(cp) > 0;
                    if(flag){
                        m.put("result","true");
                    }else{
                        m.put("result","false");
                    }
                }
            }else{
                m.put("goodsid",cp.getGoodsid());
            }
        }
        return m ;
    }

    /**
     * 验证list中的客户编码在系统中是否存在
     * @param list
     * @return
     * @throws Exception
     */
    public Map checkImportCustomer(List<CustomerPrice> list) throws Exception{
        Map map = new HashMap();
        List<String> emptyCustomerID = new ArrayList();
        List<String> customerList = new ArrayList<String>();
        String index = "";
        int emptSize = 0 ;

        for(CustomerPrice cp : list){
            String customerid = cp.getCustomerid();
            String shortcode = cp.getShortcode();
            boolean flag = false;
            Customer customer = new Customer();

            if(StringUtils.isNotEmpty(customerid)){
                customer = customerMapper.getCustomerInfo(customerid);
                if(null != customer){
                    for(String cid : customerList){
                        if(cid.equals(customerid)){
                            flag = true;
                            break;
                        }
                    }
                }else{
                    flag = true;
                    index += (list.indexOf(cp)+2)+",";
                    ++ emptSize;
                    emptyCustomerID.add(customerid);
                }
            }else if(StringUtils.isNotEmpty(cp.getShortcode())){
                customer = customerMapper.getCustomerInfoLimitOne(shortcode);
                if(null != customer){
                    for(String cid : customerList){
                        if(cid.equals(customer.getId())){
                            flag = true;
                            break;
                        }
                    }
                }else{
                    flag = true;
                    index += (list.indexOf(cp)+2)+",";
                    ++ emptSize;
                    emptyCustomerID.add("");
                }
            }else{
                flag = true;
                index += (list.indexOf(cp)+2)+",";
                ++ emptSize;
                emptyCustomerID.add(customerid);
            }
            //根据条件将对应的 客户编码 或 客户助记码 加入客户参数中
            if(!flag && StringUtils.isNotEmpty(customerid)){
                customerList.add(customer.getId());
            }else if(!flag){
                customerList.add(customer.getShortcode());
            }
        }

        if(list.size() == emptyCustomerID.size()){
            map.put("emptCustomerID",emptyCustomerID.toString());
            map.put("customerID","");
        }else{
            map.put("emptCustomerID",emptyCustomerID.toString());
            map.put("customerID",customerList.toString());
            map.put("emptIndex", index);
            map.put("emptSize",emptSize+"");
        }
        return map;
    }

	@Override
	public PageData getCustomerListData(PageMap pageMap) throws Exception {
		String type = (String)pageMap.getCondition().get("type");
		if("customer".equals(type)){
			//获取客户列表
			PageData pageData = new PageData(customerMapper.getCustomerCountPidNull(pageMap),
					customerMapper.getCustomerListPidNull(pageMap),pageMap);
			return pageData;
		}
		else if("pcustomer".equals(type)){
			//获取总店列表
			PageData pageData = new PageData(customerMapper.getCustomerCountPidNoNull(pageMap),
					customerMapper.getCustomerListPidNoNull(pageMap),pageMap);
			return pageData;
		}
		return null;
	}

	@Override
	public Customer getCustomerInfoByShopno(String pid, String shopno)
			throws Exception {
		return customerMapper.getCustomerInfoByShopno(pid, shopno);
	}

	@Override
	public Customer getCustomerInfo(String id) throws Exception {
		Customer customer = customerMapper.getCustomerInfo(id);
		if(customer != null){
			Customer pCustomer = customerMapper.getCustomerInfo(customer.getPid());
			if(null != pCustomer){
				customer.setPname(pCustomer.getName());
			}
			List list = getCustomerAndSortListByCustomer(id);
			customer.setSortList(list);
		}
		return customer;
	}

	@Override
	public boolean updateMoreCustomerPrice(Map map,CustomerPrice customerPrice) throws Exception {
		if(null != customerPrice.getPrice() && null != customerPrice.getNoprice()
			&& customerPrice.getPrice().compareTo(BigDecimal.ZERO) != 0
			&& customerPrice.getNoprice().compareTo(BigDecimal.ZERO) != 0
		){
			int i = 0;
			List<CustomerPrice> list = customerMapper.getCustomerPriceListByMap(map);
			for(CustomerPrice oldCPrice : list){
				oldCPrice.setPrice(customerPrice.getPrice());
				oldCPrice.setNoprice(customerPrice.getNoprice());
				boolean flag = customerMapper.updateCustomerPrice(oldCPrice) > 0;
				if(flag){
					i++;
				}
			}
			return (list.size() == i);
		}else{
			return true;
		}
	}

	@Override
	public Customer getCustomerInfoLimitOneByShortcode(String shortcode)
			throws Exception {
		return customerMapper.getCustomerInfoLimitOne(shortcode);
	}

	@Override
	public List getCustomerListByidArr(String ids) throws Exception {
		if(StringUtils.isNotEmpty(ids)){
			return customerMapper.getCustomerListByidArr(ids.split(","));
		}
		return null;
	}

	@Override
	public boolean addCustomerBrandPricesort(Map map) throws Exception {
		boolean flag = false;
		if(map.containsKey("brandids")){
			String brandids = (String) map.get("brandids");
			String[] brandidArr = brandids.split(",");
			String pricesort = (String) map.get("pricesort");
			String remark = (String) map.get("remark");
			List<Customer> list = customerMapper.getCustomerListWithBrandPricesort(map);
			for(Customer customer : list){
				//删除客户品牌对应价格套
				customerMapper.detleCustomerBrandPricesortByCustomeridAndBrandid(customer.getId(), brandids);
				for(String brandid : brandidArr){
					customerMapper.addCustomerBrandPricesort(customer.getId(), brandid, pricesort,remark);
				}
			}
			flag = true;
		}
		return flag;
	}

	@Override
	public List showCustomerBrandPricesort(String customerid) throws Exception {
		List<CustomerBrandPricesort> list = customerMapper.showCustomerBrandPricesort(customerid);
		for(CustomerBrandPricesort customerBrandPricesort : list){
			Brand brand = getBaseGoodsMapper().getBrandInfo(customerBrandPricesort.getBrandid());
			if(null!=brand){
				customerBrandPricesort.setBrandname(brand.getName());
				DepartMent departMent = getDepartmentByDeptid(brand.getDeptid());
				if(null!=departMent){
					customerBrandPricesort.setDeptname(departMent.getName());
				}
			}
		}
		return list;
	}

	@Override
	public boolean editCustomerBrandPricesort(
			CustomerBrandPricesort customerBrandPricesort) throws Exception {
		return customerMapper.editCustomerBrandPricesort(customerBrandPricesort) > 0;
	}

	@Override
	public Map deleteCustomerBrandPricesorts(String idstr) throws Exception {
		String[] idarr = idstr.split(",");
		String sucbrands = "",unsucbrands = "";
		int sucnum = 0,unsucnum = 0;
		for(String id : idarr){
			CustomerBrandPricesort brandPricesort = customerMapper.getCustomerBrandPricesortInfo(id);
			if(null != brandPricesort){
				boolean flag = customerMapper.deleteCustomerBrandPricesorts(id) > 0;
				if(flag){
					sucnum++;
					if(sucbrands == ""){
						sucbrands = brandPricesort.getBrandid();
					}else{
						sucbrands += "," + brandPricesort.getBrandid();
					}
				}else{
					unsucnum++;
					if(unsucbrands == ""){
						unsucbrands = brandPricesort.getBrandid();
					}else{
						unsucbrands += "," + brandPricesort.getBrandid();
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("sucnum", sucnum);
		map.put("sucbrands", sucbrands);
		map.put("unsucnum", unsucnum);
		map.put("unsucbrands", unsucbrands);
		return map;
	}

	@Override
	public PageData getSalesDepartmentSelectListData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_base_department", null);
		pageMap.setDataSql(dataSql);
		//控件传过来的参数 生成sql语句
		String name = (String) pageMap.getCondition().get("name");
		String paramRuleSql = RuleJSONUtils.widgetParamToSql(name,null);
		pageMap.getCondition().put("paramRuleSql", paramRuleSql);
		List<DepartMent> list = departMentMapper.getDepartmentList(pageMap);
		PageData pageData = new PageData(departMentMapper.getDepartmentCount(pageMap),list,pageMap);
		return pageData;
	}

    @Override
    public Map addDRSalesAreaExcel(List<SalesArea> list) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0,levelNum=0,reptthisNum = 0;
        String closeVal = "",repeatVal = "",failStr = "",errorVal = "",levelVal = "",reptthisnameVal = "";
        if(list.size() != 0){
            String tn = "t_base_sales_area";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(SalesArea salesArea : list){
                    if(StringUtils.isEmpty(salesArea.getId())){
                        continue;
                    }
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,salesArea.getId());
                    if(null != map && !map.isEmpty()){
                        String id = salesArea.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
                        try {
                            //判断销售区域是否重复
                            Map map1 = new HashMap();
                            map1.put("id",id);
                            SalesArea salesAreaInfo = salesAreaMapper.getSalesAreaDetail(map1);
                            if(salesAreaInfo == null){//不重复
								//判断本机名称是否重复
								if(salesAreaMapper.isRepeatThisName(salesArea.getThisname()) == 0){
									salesArea.setThisid(thisid);
									salesArea.setPid(pid);
									SysUser sysUser = getSysUser();
									salesArea.setAdduserid(sysUser.getUserid());
									salesArea.setAdddeptid(sysUser.getDepartmentid());
									salesArea.setAdddeptname(sysUser.getDepartmentname());
									salesArea.setAddusername(sysUser.getName());
									if(StringUtils.isEmpty(salesArea.getPid())){
										salesArea.setName(salesArea.getThisname());
									}
									salesArea.setState("1");
									flag = salesAreaMapper.addSalesArea(salesArea) > 0;
									if(!flag){
										if(StringUtils.isNotEmpty(failStr)){
											failStr += "," + salesArea.getId();
										}
										else{
											failStr = salesArea.getId();
										}
										failureNum++;
									}else{
										successNum++;
									}
								}else{
									if(StringUtils.isEmpty(reptthisnameVal)){
										reptthisnameVal = salesArea.getId();
									}else{
										reptthisnameVal += "," + salesArea.getId();
									}
									reptthisNum++;
								}
                            }else{
                                if("0".equals(salesAreaInfo.getState())){//禁用状态，不允许导入
                                    if(StringUtils.isEmpty(closeVal)){
                                        closeVal = salesAreaInfo.getId();
                                    }else{
                                        closeVal += "," + salesAreaInfo.getId();
                                    }
                                    closeNum++;
                                }
                                else{
                                    if(StringUtils.isEmpty(repeatVal)){
                                        repeatVal = salesAreaInfo.getId();
                                    }else{
                                        repeatVal += "," + salesAreaInfo.getId();
                                    }
                                    repeatNum++;
                                }
                            }
                        }catch (Exception e) {
                            if(StringUtils.isEmpty(errorVal)){
                                errorVal = id;
                            }
                            else{
                                errorVal += "," + id;
                            }
                            errorNum++;
                            continue;
                        }
                    }else{
                        levelNum++;
                        if(StringUtils.isNotEmpty(levelVal)){
                            levelVal += "," + salesArea.getId();
                        }
                        else{
                            levelVal = salesArea.getId();
                        }
                    }
                }
                //名称
                List<SalesArea> nonameList = salesAreaMapper.getSalesAreaWithoutName();
                if(nonameList.size() != 0){
                    for(SalesArea nonameSA : nonameList){
                        SalesArea pSA = getSalesAreaDetail(nonameSA.getPid());
                        if(null != pSA){
                            nonameSA.setOldid(nonameSA.getId());
                            String name = "";
                            if(StringUtils.isEmpty(pSA.getName())){
                                name = nonameSA.getThisname();
                            }else{
                                name = pSA.getName() + "/" + nonameSA.getThisname();
                            }
                            if(StringUtils.isNotEmpty(pSA.getName())){
                                nonameSA.setName(name);
                            }else{
                                nonameSA.setName(pSA.getThisname());
                            }
                            salesAreaMapper.updateSalesArea(nonameSA);
                        }
                    }
                }
            }else{
                returnMap.put("nolevel", true);
            }
        }
        returnMap.put("flag", flag);
        returnMap.put("success", successNum);
        returnMap.put("failure", failureNum);
        returnMap.put("failStr", failStr);
        returnMap.put("repeatNum", repeatNum);
        returnMap.put("repeatVal", repeatVal);
        returnMap.put("closeNum", closeNum);
        returnMap.put("closeVal", closeVal);
        returnMap.put("errorNum", errorNum);
        returnMap.put("errorVal", errorVal);
        returnMap.put("levelNum", levelNum);
        returnMap.put("levelVal", levelVal);
		returnMap.put("reptthisNum", reptthisNum);
		returnMap.put("reptthisnameVal", reptthisnameVal);
        return returnMap;
    }

    @Override
    public Map addDRCustomerSortExcel(List<CustomerSort> list) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0,levelNum=0,reptthisNum = 0;
        String closeVal = "",repeatVal = "",failStr = "",errorVal = "",levelVal = "",reptthisnameVal = "";
        if(list.size() != 0){
            String tn = "t_base_sales_customersort";
            List levelList = showFilesLevelList(tn);
            if(levelList.size() != 0){
                for(CustomerSort customerSort : list){
                    if(StringUtils.isEmpty(customerSort.getId())){
                        continue;
                    }
                    //根据档案级次信息根据编码获取本级编码
                    Map map = getObjectThisidByidCaseFilesLevel(tn,customerSort.getId());
                    if(null != map && !map.isEmpty()){
                        String id = customerSort.getId();
                        String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
                        String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
                        try {
                            //判断销售区域是否重复
                            Map map1 = new HashMap();
                            map1.put("id",id);
                            CustomerSort customerSortInfo = customerSortMapper.getCustomerSortDetail(map1);
                            if(customerSortInfo == null){//不重复
								//判断本机名称是否重复
								if(customerSortMapper.isRepeatThisName(customerSort.getThisname()) == 0){
									customerSort.setThisid(thisid);
									customerSort.setPid(pid);
									SysUser sysUser = getSysUser();
									customerSort.setAdduserid(sysUser.getUserid());
									customerSort.setAdddeptid(sysUser.getDepartmentid());
									customerSort.setAdddeptname(sysUser.getDepartmentname());
									customerSort.setAddusername(sysUser.getName());
									if(StringUtils.isEmpty(customerSort.getPid())){
										customerSort.setName(customerSort.getThisname());
									}
									customerSort.setState("1");
									flag = customerSortMapper.addCustomerSort(customerSort) > 0;
									if(!flag){
										if(StringUtils.isNotEmpty(failStr)){
											failStr += "," + customerSort.getId();
										}
										else{
											failStr = customerSort.getId();
										}
										failureNum++;
									}else{
										successNum++;
									}
								}else{
									if(StringUtils.isEmpty(reptthisnameVal)){
										reptthisnameVal = customerSort.getId();
									}else{
										reptthisnameVal += "," + customerSort.getId();
									}
									reptthisNum++;
								}
                            }else{
                                if("0".equals(customerSortInfo.getState())){//禁用状态，不允许导入
                                    if(StringUtils.isEmpty(closeVal)){
                                        closeVal = customerSortInfo.getId();
                                    }else{
                                        closeVal += "," + customerSortInfo.getId();
                                    }
                                    closeNum++;
                                }
                                else{
                                    if(StringUtils.isEmpty(repeatVal)){
                                        repeatVal = customerSortInfo.getId();
                                    }else{
                                        repeatVal += "," + customerSortInfo.getId();
                                    }
                                    repeatNum++;
                                }
                            }
                        }catch (Exception e) {
                            if(StringUtils.isEmpty(errorVal)){
                                errorVal = id;
                            }
                            else{
                                errorVal += "," + id;
                            }
                            errorNum++;
                            continue;
                        }
                    }else{
                        levelNum++;
                        if(StringUtils.isNotEmpty(levelVal)){
                            levelVal += "," + customerSort.getId();
                        }
                        else{
                            levelVal = customerSort.getId();
                        }
                    }
                }
                //名称
                List<CustomerSort> nonameList = customerSortMapper.getCustomerSortWithoutName();
                if(nonameList.size() != 0){
                    for(CustomerSort nonameCS : nonameList){
                        CustomerSort pCS = getCustomerSortDetail(nonameCS.getPid());
                        if(null != pCS){
                            nonameCS.setOldid(nonameCS.getId());
                            String name = "";
                            if(StringUtils.isEmpty(pCS.getName())){
                                name = nonameCS.getThisname();
                            }else{
                                name = pCS.getName() + "/" + nonameCS.getThisname();
                            }
                            if(StringUtils.isNotEmpty(pCS.getName())){
                                nonameCS.setName(name);
                            }else{
                                nonameCS.setName(pCS.getThisname());
                            }
                            customerSortMapper.updateCustomerSort(nonameCS);
                        }
                    }
                }
            }else{
                returnMap.put("nolevel", true);
            }
        }
        returnMap.put("flag", flag);
        returnMap.put("success", successNum);
        returnMap.put("failure", failureNum);
        returnMap.put("failStr", failStr);
        returnMap.put("repeatNum", repeatNum);
        returnMap.put("repeatVal", repeatVal);
        returnMap.put("closeNum", closeNum);
        returnMap.put("closeVal", closeVal);
        returnMap.put("errorNum", errorNum);
        returnMap.put("errorVal", errorVal);
        returnMap.put("levelNum", levelNum);
        returnMap.put("levelVal", levelVal);
		returnMap.put("reptthisNum", reptthisNum);
		returnMap.put("reptthisnameVal", reptthisnameVal);
        return returnMap;
    }

    @Override
    public boolean getShopnoIsRepeatFlag(String pcustomerid, String shopno) throws Exception {
        return customerMapper.getShopnoIsRepeatFlag(pcustomerid,shopno) > 0;
    }

    @Override
    public List<Customer> getUpLoadCustomerMod(String[] idlist)throws Exception{
        List<Customer> customerList = new ArrayList<Customer>();
        //对编号进行排序
        Arrays.sort(idlist);

        for(String id : idlist){
            Customer customer = customerMapper.getCustomerById(id);
            //是否总店
            if(customer.getIslast().equals("0")){
                customer.setIslastname("是");
            }else  if(customer.getIslast().equals("1")){
                customer.setIslastname("否");
            }else{
                customer.setIslastname("");
            }

            //是否现款
            if("0".equals(customer.getIscash())){
                customer.setIscashname("否");
            }else if("1".equals(customer.getIscash())){
                customer.setIscashname("是");
            }
            //核销方式
            SysCode canceltypeSysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getCanceltype(), "canceltype");
            if(null != canceltypeSysCode){
                customer.setCanceltypename(canceltypeSysCode.getCodename());
            }
            //价格套
            SysCode pricesortSysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getPricesort(), "price_list");
            if(null != pricesortSysCode){
                customer.setPricesortname(pricesortSysCode.getCodename());
            }
            //促销分类
            SysCode promotionsortSysCode = getBaseSysCodeMapper().getSysCodeInfo(customer.getPromotionsort(), "promotionsort");
            if(null != promotionsortSysCode){
                customer.setPromotionsortname(promotionsortSysCode.getCodename());
            }
            //默认业务员
            if(null != customer.getSalesuserid()){
                Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getSalesuserid());
                if(null!=personnel){
                    customer.setSalesusername(personnel.getName());
                }
            }
            //默认理货员
            if(null != customer.getTallyuserid()){
                Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getTallyuserid());
                if(null!=personnel){
                    customer.setTallyusername(personnel.getName());
                }
            }
            //默认内勤
            if(null != customer.getIndoorstaff()){
                Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getIndoorstaff());
                if(null!=personnel){
                    customer.setIndoorstaffname(personnel.getName());
                }
            }
            //收款人
            if(StringUtils.isNotEmpty(customer.getPayeeid())){
                Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(customer.getPayeeid());
                if(null != personnel){
                    customer.setPayeename(personnel.getName());
                }
            }
            //账期
            if("0".equals(customer.getIslongterm())){
                customer.setIslongtermname("否");
            }else if("1".equals(customer.getIslongterm())){
                customer.setIslongtermname("是");
            }else{
                customer.setIslongtermname("");
            }
            //所属区域
            List<SalesArea> aList = getBaseSalesAreaMapper().returnSalesAreaIdByName(customer.getSalesareaname());
            if(aList.size() != 0){
                customer.setSalesarea(aList.get(0).getId());
            }
            //所属分类
            List<CustomerSort> customerSort = getCustomerAndSortMapper().getCustomerAndSortListByCustomer(customer.getCustomersort());
            if(customerSort.size()>0){
                customer.setCustomersortname(customerSort.get(0).getName());
            }
            //所属区域
            SalesArea salesArea = getSalesAreaDetail(customer.getSalesarea());
            if(salesArea != null){
                customer.setSalesareaname(salesArea.getThisname());
            }
            //结算方式
            if(null != customer.getSettletypename()){
                Settlement settlement = getBaseFinanceMapper().getSettlementListByName(customer.getSettletypename());
                if(null!=settlement){
                    customer.setSettletype(settlement.getId());
                }
            }else{
                //取系统默认结算方式
                String value = getSysParamValue("SETTLETYPE");
                if(null!=value){
                    customer.setSettletype(value);
                }
            }
            //信用等级
            if("1".equals(customer.getCreditrating())){
                customer.setCreditratingname("一星级");
            }else if("2".equals(customer.getCreditrating())){
                customer.setCreditratingname("二星级");
            }else if("3".equals(customer.getCreditrating())){
                customer.setCreditratingname("三星级");
            }else if("4".equals(customer.getCreditrating())){
                customer.setCreditratingname("四星级");
            }else if("5".equals(customer.getCreditrating())){
                customer.setCreditratingname("五星级");
            }else{
                customer.setCreditratingname("");
            }
            //开户银行
            if(StringUtils.isNotEmpty(customer.getPayeeid())){
                Bank bank  = getBaseFinanceMapper().getBankDetail(customer.getBank());
                if(null != bank){
                    customer.setPayeename(bank.getName());
                }
            }
            //状态
            if(StringUtils.isNotEmpty(customer.getStatename()) || StringUtils.isEmpty(customer.getState())){
                String state = getBaseSysCodeMapper().searchCodename(customer.getState(), "state");
                if(StringUtils.isNotEmpty(state)){
                    customer.setStatename(state);
                }else{
                    state = getBaseSysCodeMapper().searchCodename(customer.getStatename(), "state");
                }
            }
//            else{
//                customer.setStatename("启用");
//            }
            //取整
            if(null != customer.getCredit()){
                customer.setCredit(customer.getCredit().setScale(0,BigDecimal.ROUND_HALF_UP));
            }

            if(null != customer.getFund()) {
                customer.setFund(customer.getFund().setScale(0, BigDecimal.ROUND_HALF_UP));
            }
            customerList.add(customer);
        }


        return  customerList;
    }

    @Override
    public CustomerCapital getCustomerCapital(String id) throws Exception{
        return customerCapitalMapper.getCustomerCapital(id);
    }

	@Override
	public List getCustomerBrandSettletypeList(String customerid) throws Exception {
		List<CustomerBrandSettletype> list = customerMapper.getCustomerBrandSettletypeList(customerid);
		for(CustomerBrandSettletype customerBrandSettletype : list){
			Brand brand = getBaseGoodsMapper().getBrandInfo(customerBrandSettletype.getBrandid());
			if(null!=brand){
				customerBrandSettletype.setBrandname(brand.getName());
			}
			//结算方式
			Map map = new HashMap();
			map.put("id",customerBrandSettletype.getSettletype());
			Settlement settlement = getBaseFinanceMapper().getSettlemetDetail(map);
			if(null != settlement){
				customerBrandSettletype.setSettletypename(settlement.getName());
			}
		}
		return list;
	}

	@Override
	public boolean addCustomerBrandSettletype(CustomerBrandSettletype customerBrandSettletype) throws Exception {
		boolean flag = false;
		IStorageSaleOutService storageSaleOutService = (IStorageSaleOutService) SpringContextUtils.getBean("storageSaleOutService");
		if(StringUtils.isNotEmpty(customerBrandSettletype.getBrandids()) && StringUtils.isNotEmpty(customerBrandSettletype.getCustomerid())){
			String[] customerArr = customerBrandSettletype.getCustomerid().split(",");
			for(String customerid : customerArr){
				customerBrandSettletype.setCustomerid(null);
				customerBrandSettletype.setCustomerid(customerid);
				//删除客户品牌结算方式
				customerMapper.detleCustomerBrandSettletypeByCustomeridAndBrandid(customerid, customerBrandSettletype.getBrandids());
				String[] brandidArr = customerBrandSettletype.getBrandids().split(",");
				for(String brandid : brandidArr){
					customerBrandSettletype.setBrandid(brandid);
					boolean flag2 = customerMapper.addCustomerBrandSettletype(customerBrandSettletype) > 0;
					if(flag2){
						storageSaleOutService.updateCustomerSaleOutNoWriteoffDuefromdateByCustomerid(customerBrandSettletype.getCustomerid());
					}
				}
			}

			flag = true;
		}
		return flag;
	}

	@Override
	public boolean editCustomerBrandSettletype(CustomerBrandSettletype customerBrandSettletype) throws Exception {
		IStorageSaleOutService storageSaleOutService = (IStorageSaleOutService) SpringContextUtils.getBean("storageSaleOutService");
		boolean flag2 = customerMapper.editCustomerBrandSettletype(customerBrandSettletype) > 0;
		if(flag2){
			storageSaleOutService.updateCustomerSaleOutNoWriteoffDuefromdateByCustomerid(customerBrandSettletype.getCustomerid());
		}
		return flag2;
	}

	@Override
	public Map deleteCustomerBrandSettletypes(String idstr) throws Exception {
		String[] idarr = idstr.split(",");
		String sucbrands = "",unsucbrands = "";
		int sucnum = 0,unsucnum = 0;
		IStorageSaleOutService storageSaleOutService = (IStorageSaleOutService) SpringContextUtils.getBean("storageSaleOutService");
		for(String id : idarr){
			CustomerBrandSettletype brandSettletype = customerMapper.getCustomerBrandSettletypeInfo(id);
			if(null != brandSettletype){
				boolean flag = customerMapper.deleteCustomerBrandSettletype(id) > 0;
				if(flag){
					storageSaleOutService.updateCustomerSaleOutNoWriteoffDuefromdateByCustomerid(brandSettletype.getCustomerid());
					sucnum++;
					if(sucbrands == ""){
						sucbrands = brandSettletype.getBrandid();
					}else{
						sucbrands += "," + brandSettletype.getBrandid();
					}
				}else{
					unsucnum++;
					if(unsucbrands == ""){
						unsucbrands = brandSettletype.getBrandid();
					}else{
						unsucbrands += "," + brandSettletype.getBrandid();
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("sucnum", sucnum);
		map.put("sucbrands", sucbrands);
		map.put("unsucnum", unsucnum);
		map.put("unsucbrands", unsucbrands);
		return map;
	}

	@Override
	public Map importCustomerBrandSettletype(List<Map> list) throws Exception {
		String sucstr = "",failStr = "",errorVal = "",noidval = "",nobidval = "",errorIndex="",noidindex = "";
		int failureNum = 0,successNum = 0,errorNum = 0,noidnum = 0,nobidnum = 0,nullcstnum = 0,nullbnum = 0;
		Map returnMap = new HashMap();
		Map<String, String> errorMap = new HashMap<String, String>();
		Map<String, String> nocstidMap = new HashMap<String, String>();
		Map<String, String> failMap = new HashMap<String, String>();
		Map<String, String> sucMap = new HashMap<String, String>();
		IStorageSaleOutService storageSaleOutService = (IStorageSaleOutService) SpringContextUtils.getBean("storageSaleOutService");
		if(list.size() != 0){
			for(Map map : list){
				CustomerBrandSettletype brandSettletype = new CustomerBrandSettletype();
				BeanUtils.populate(brandSettletype,map);
				try {
					if(StringUtils.isEmpty(brandSettletype.getCustomerid())){
						nullcstnum++;
					}else if(StringUtils.isEmpty(brandSettletype.getBrandid())) {
						nullbnum++;
					}else{
						Customer customer = customerMapper.getCustomerById(brandSettletype.getCustomerid());
						if(null == customer){
							if(!nocstidMap.containsKey(brandSettletype.getCustomerid())){
								nocstidMap.put(brandSettletype.getCustomerid(),brandSettletype.getCustomerid());
							}
							if(StringUtils.isEmpty(noidindex)){
								noidindex = (list.indexOf(customer)+2)+"";
							} else{
								noidindex +="," + (list.indexOf(customer)+2);
							}
							noidnum++;
						}else{
							Brand brand = getBaseGoodsMapper().getBrandInfo(brandSettletype.getBrandid());
							if(null == brand){
								if(StringUtils.isNotEmpty(nobidval)){
									nobidval += "," + brandSettletype.getBrandid();
								}else{
									nobidval = brandSettletype.getBrandid();
								}
								nobidnum++;
							}else{
								//根据客户、品牌判断是否已存在该结算方式，若存在进行覆盖
								CustomerBrandSettletype customerBrandSettletype1 = customerMapper.getCustomerBrandSettletypeByCustomeridAndBrandid(brandSettletype.getCustomerid(),brandSettletype.getBrandid());
								if(null != customerBrandSettletype1){//重复
									customerMapper.deleteCustomerBrandSettletypeByCustomeridAndBrandid(brandSettletype.getBrandid(), brandSettletype.getCustomerid());
								}
								//结算方式
								Settlement settlement = getBaseFinanceMapper().getSettlementListByName(brandSettletype.getSettletypename());
								if(null != settlement){
									brandSettletype.setSettletype(settlement.getId());
									if("1".equals(settlement.getType())){
										if(StringUtils.isEmpty(brandSettletype.getSettleday())){
											brandSettletype.setSettleday("1");
										}else if(Integer.parseInt(brandSettletype.getSettleday())> 31){
											brandSettletype.setSettleday("1");
										}
									}
								}
								if(StringUtils.isNotEmpty(brandSettletype.getBrandid()) && StringUtils.isNotEmpty(brandSettletype.getCustomerid())){
									boolean flag = customerMapper.addCustomerBrandSettletype(brandSettletype) > 0;
									if(flag){
										storageSaleOutService.updateCustomerSaleOutNoWriteoffDuefromdateByCustomerid(brandSettletype.getCustomerid());
										successNum++;
										if(sucMap.containsKey(brandSettletype.getCustomerid())){
											String brandids = (String)sucMap.get(brandSettletype.getCustomerid());
											if(StringUtils.isEmpty(brandids)){
												brandids = brandSettletype.getBrandid();
											}else{
												brandids += "," + brandSettletype.getBrandid();
											}
											sucMap.put(brandSettletype.getCustomerid(),brandids);
										}else{
											sucMap.put(brandSettletype.getCustomerid(),brandSettletype.getBrandid());
										}
									}else{
										failureNum++;
										if(failMap.containsKey(brandSettletype.getCustomerid())){
											String brandids = (String)failMap.get(brandSettletype.getCustomerid());
											if(StringUtils.isEmpty(brandids)){
												brandids = brandSettletype.getBrandid();
											}else{
												brandids += "," + brandSettletype.getBrandid();
											}
											failMap.put(brandSettletype.getCustomerid(),brandids);
										}else{
											failMap.put(brandSettletype.getCustomerid(),brandSettletype.getBrandid());
										}
									}
								}
							}
						}
					}
				}catch (Exception e) {
					if(errorMap.containsKey(brandSettletype.getCustomerid())){
						String brandids = (String)errorMap.get(brandSettletype.getCustomerid());
						if(StringUtils.isNotEmpty(brandids)){
							brandids += "," + brandSettletype.getBrandid();
						}else{
							brandids = brandSettletype.getBrandid();
						}
						errorMap.put(brandSettletype.getCustomerid(),brandids);
					}else{
						errorMap.put(brandSettletype.getCustomerid(),brandSettletype.getBrandid());
					}
					if(StringUtils.isEmpty(errorIndex)){
						errorIndex = (list.indexOf(brandSettletype)+2)+"";
					}else{
						errorIndex +="," + (list.indexOf(brandSettletype)+2);
					}
					errorNum++;
					continue;
				}
			}
		}

		if(!errorMap.isEmpty()){
			for (Map.Entry<String, String> entry : errorMap.entrySet()) {
				if(StringUtils.isEmpty(errorVal)){
					errorVal = "<客户编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
				}else{
					errorVal += "<br>" + "客户编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
				}
			}
			if(errorIndex != ""){
				errorVal = errorVal +" (导入数据中第"+errorIndex+"行数据) 导入格式";
			}
		}
		if(!nocstidMap.isEmpty()){
			for (Map.Entry<String, String> entry : nocstidMap.entrySet()) {
				if(StringUtils.isEmpty(noidval)){
					noidval = entry.getKey();
				}else{
					noidval += "," + entry.getKey();
				}
			}
			if(noidindex != ""){
				noidval = "客户编码：" +noidval +" (导入数据中第"+noidindex+"行数据) 不存在，"+noidnum+"条不允许导入";
			}
		}
		if(!failMap.isEmpty()){
			for (Map.Entry<String, String> entry : failMap.entrySet()) {
				if(StringUtils.isEmpty(failStr)){
					failStr = "<客户编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
				}else{
					failStr += "<br>" + "<客户编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
				}
			}
		}
		if(!sucMap.isEmpty()){
			for (Map.Entry<String, String> entry : sucMap.entrySet()) {
				if(StringUtils.isEmpty(sucstr)){
					sucstr = "<客户编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
				}else{
					sucstr += "<br>" + "<客户编码："+entry.getKey()+"，品牌编码："+entry.getValue()+">；";
				}
			}
		}
		String msg = "";
		if(StringUtils.isNotEmpty(errorVal)){
			msg = errorVal + " 共" + errorNum +"条数据出错";
		}
		if(nullcstnum > 0){
			if(StringUtils.isEmpty(msg)){
				msg = nullcstnum + "条导入数据客户编码为空，不允许导入";
			}else{
				msg += "<br>" + nullcstnum + "条导入数据客户编码为空，不允许导入";
			}
		}
		if(nullbnum > 0){
			if(StringUtils.isEmpty(msg)){
				msg = nullbnum + "条导入数据品牌编码为空，不允许导入";
			}else{
				msg += "<br>" + nullbnum + "条导入数据品牌编码为空，不允许导入";
			}
		}
		if(StringUtils.isNotEmpty(noidval)){
			if(StringUtils.isEmpty(msg)){
				msg = noidval;
			}else{
				msg += "<br>" + noidval;
			}
		}
		if(StringUtils.isNotEmpty(nobidval)){
			if(StringUtils.isEmpty(msg)){
				msg = "品牌编码："+nobidval+" 不存在，"+nobidnum+"条不允许导入";
			}else{
				msg += "<br>" + "品牌编码："+nobidval+" 不存在，"+nobidnum+"条不允许导入";
			}
		}
		if(StringUtils.isNotEmpty(failStr)){
			if(StringUtils.isEmpty(msg)){
				msg = failStr + " 共" + failureNum + "条数据，导入失败";
			}else{
				msg += "<br>" + failStr + " 共" + failureNum + "条数据，导入失败";
			}
		}
		if(StringUtils.isNotEmpty(sucstr)){
			if(StringUtils.isEmpty(msg)){
				msg = sucstr + " 共" + successNum +"条数据,导入成功";
			}else{
				msg += "<br>" + sucstr + " 共" + successNum +"条数据,导入成功";
			}
		}
		returnMap.put("sucstr", sucstr);
		returnMap.put("msg", msg);
		return returnMap;
	}

	@Override
	public List getCustomerSortListByMap(Map map) throws Exception{
		List<CustomerSort> list=customerSortMapper.getCustomerSortListByMap(map);
		return list;
	}

    @Override
    public List<Map> getCustomerLocationList(List<String> customerids) throws Exception {

        List<Map> locations = customerMapper.getCustomerLocationList(customerids);
        return locations;
    }

    @Override
    public int insertCustomerLocation(CustomerLocation location) {
        return customerMapper.insertCustomerLocation(location);
    }

    @Override
    public CustomerLocation selectCustomerLocation(String customerid) {
        return customerMapper.selectCustomerLocation(customerid);
    }

    @Override
    public int editCustomerLocation(CustomerLocation customerLocation) throws Exception {

        CustomerLocation old = customerMapper.selectCustomerLocation(customerLocation.getCustomerid());
        if(old != null) {
            customerMapper.deleteCustomerLocation(customerLocation.getCustomerid());
        }

        int ret = customerMapper.insertCustomerLocation(customerLocation);
        return ret;
    }

	@Override
	public List<Map> selectUnlocatedCustomerList() {
		return customerMapper.selectUnlocatedCustomerList();
	}

	@Override
	public List<Customer> getCustomerListForMecshop(Date time) throws Exception {

		return customerMapper.getCustomerListForMecshop(time);
	}

	@Override
	public List<Map> getCustomersortListForMecshop(Date time) throws Exception {

		return customerSortMapper.getCustomersortListForMecshop(time);
	}

	@Override
	public List<Map> getCustomerPriceListForMecshop(String offset, String rows) throws Exception {

		return customerMapper.getCustomerPriceListForMecshop(Integer.parseInt(offset), Integer.parseInt(rows));
	}

	@Override
	public List<Map> getSalesareaListForMecshop() throws Exception {

		return salesAreaMapper.getSalesareaListForMecshop();
	}

    @Override
    public PageData getGoodsList(PageMap pageMap) throws Exception {

        String sql = getDataAccessRule("t_base_goods_info", null); //数据权限
        pageMap.setDataSql(sql);

        List<GoodsInfo> goodsInfoList = goodsMapper.getGoodsInfoListByBrandAndSort(pageMap);
        List<Map> list = new ArrayList<Map>();
        for(GoodsInfo goodsInfo : goodsInfoList) {

            Map temp = new HashMap();
            temp.put("goodsid", goodsInfo.getId());
            temp.put("goodsname", goodsInfo.getName());
            temp.put("barcode", goodsInfo.getBarcode());
            String brandid = goodsInfo.getBrand();
            if(StringUtils.isNotEmpty(brandid)) {
                Brand brand = goodsMapper.getBrandInfo(brandid);
                if (brand != null) {
                    goodsInfo.setBrandName(brand.getName());
                    temp.put("brandid", brandid);
                    temp.put("brandname", brand.getName());
                }
            }

            String goodssort = goodsInfo.getDefaultsort();
            if(StringUtils.isNotEmpty(goodssort)) {
                WaresClass waresClass = goodsMapper.getWaresClassInfo(goodssort);
                if (waresClass != null) {
                    goodsInfo.setDefaultsortName(waresClass.getThisname());
                    temp.put("goodssortname", waresClass.getThisname());
                }
            }

            String supplierid = goodsInfo.getDefaultsupplier();
            if(StringUtils.isNotEmpty(supplierid)) {
                BuySupplier supplier = buySupplierMapper.getBuySupplier(supplierid);
                if (supplier != null) {
                    goodsInfo.setDefaultsalerName(supplier.getName());
                    temp.put("supplierid", supplier.getId());
                    temp.put("suppliername", supplier.getName());
                }
            }

            GoodsInfo_MteringUnitInfo gm = goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
            if (gm != null) {
                temp.put("boxnum", gm.getRate());
            }

            String goodstype = goodsInfo.getGoodstype();
            if(StringUtils.isNotEmpty(goodstype)) {


                SysCode sysCode = getBaseSysCodeInfo(goodstype, "goodstype");
                if (sysCode != null) {
                    temp.put("goodstype", goodstype);
                    temp.put("goodstypename", sysCode.getCodename());
                }
            }

            list.add(temp);
        }

        int count = goodsMapper.getGoodsInfoListByBrandAndSortCount(pageMap);
        return new PageData(count, list, pageMap);
    }

    @Override
    public List<Brand> getBrandList() throws Exception {
        return goodsMapper.getOpenedBrandList();
    }

    @Override
    public List<BuySupplier> getSupplierList() throws Exception {
        return buySupplierMapper.getOpenedSupplierList();
    }

    @Override
    public List<WaresClass> getWaresClassList() throws Exception {
        return goodsMapper.getWaresClassTreeList();
    }

    @Override
    public int addDistributionRule(DistributionRule distributionRule, List<DistributionRuleDetail> details) throws Exception {

        if (isAutoCreate("t_base_sales_distribution_rule")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(distributionRule, "t_base_sales_distribution_rule");
            distributionRule.setId(id);
        } else {
            distributionRule.setId("FXGZ-" + CommonUtils.getDataNumberWithRand());
        }

        SysUser user = getSysUser();
        distributionRule.setAdduserid(user.getUserid());
        distributionRule.setAddusername(user.getName());
        distributionRule.setAdddeptid(user.getDepartmentid());
        distributionRule.setAdddeptname(user.getDepartmentname());
        distributionRule.setState("2");

        int ret = distributionRuleMapper.insertDistributionRule(distributionRule);
        if(ret > 0) {
            for(DistributionRuleDetail detail : details) {

                detail.setRuleid(distributionRule.getId());
                if("1".equals(distributionRule.getGoodsruletype())) {
                    detail.setBrandid("");
                    detail.setGoodssort("");
                    detail.setGoodstype("");
                    detail.setSupplierid("");
                }
                distributionRuleMapper.insertDistributionRuleDetail(detail);
            }
        }

        return ret;
    }

    @Override
    public int editDistributionRule(DistributionRule distributionRule, List<DistributionRuleDetail> details) throws Exception {

        SysUser user = getSysUser();
        distributionRule.setModifyuserid(user.getUserid());
        distributionRule.setModifyusername(user.getName());

        int ret = distributionRuleMapper.updateDistributionRule(distributionRule);
        if(ret > 0) {
            distributionRuleMapper.deleteDistributionRuleDetailByRuleid(distributionRule.getId());
            for(DistributionRuleDetail detail : details) {

                detail.setRuleid(distributionRule.getId());
                if("1".equals(distributionRule.getGoodsruletype())) {
                    detail.setBrandid("");
                    detail.setGoodssort("");
                    detail.setGoodstype("");
                    detail.setSupplierid("");
                }
                distributionRuleMapper.insertDistributionRuleDetail(detail);
            }
        }

        return ret;
    }

    @Override
    public Map deleteDistributionRule(String[] idArr) throws Exception {

        int success = 0;
        int fail = 0;
        StringBuilder msgSb = new StringBuilder();
        StringBuilder successSb = new StringBuilder();
        for(String id : idArr) {

            DistributionRule rule = selectDistributionRule(id);
            if(rule == null) {
                fail++;
                msgSb.append("编号：" + id + " 已不存在。<br/>");
                continue;
            }
            if("1".equals(rule.getState())) {
                fail++;
                msgSb.append("编号：" + rule.getId() + " 已启用。<br/>");
                continue;
            }

            int ret = distributionRuleMapper.deleteDistributionRule(id);
            if(ret > 0) {
                distributionRuleMapper.deleteDistributionRuleDetailByRuleid(id);
            } else {
                fail++;
                msgSb.append("编号：" + rule.getId() + " 无法删除。<br/>");
                continue;
            }

            success++;
            successSb.append((success > 0 ? ", " : "") + id);
        }

        Map result = new HashMap();
        result.put("success", success);
        result.put("fail", fail);
        result.put("successIds", success > 0 ? new String(successSb).substring(2) : new String(successSb));
        result.put("msg", new String(msgSb));
        return result;
    }

    @Override
    public DistributionRule selectDistributionRule(String id) throws Exception {
        return distributionRuleMapper.selectDistributionRule(id);
    }

    @Override
    public PageData selectDistributionRulePageData(PageMap pageMap) throws Exception {

        List<DistributionRule> list = distributionRuleMapper.selectDistributionRuleList(pageMap);
        for(DistributionRule rule : list) {

            String customertype = rule.getCustomertype();
            // 单一客户
            if("1".equals(customertype)) {

                String customerids = rule.getCustomerid();
                if(StringUtils.isNotEmpty(customerids)) {

                    String[] customeridArr = customerids.split(",");
                    Arrays.sort(customeridArr);
                    StringBuilder customernameSb = new StringBuilder();
                    for(String customerid : customeridArr) {

                        Customer customer = getCustomerInfo(customerid);
                        if(customer != null) {
                            customernameSb.append(",");
                            customernameSb.append(customer.getName());
                        }
                    }

                    rule.setCustomername(customernameSb.length() > 0 ? new String(customernameSb).substring(1) : new String(customernameSb));
                }

            // 总店
            } else if("2".equals(customertype)) {

                String pcustomerids = rule.getPcustomerid();
                if(StringUtils.isNotEmpty(pcustomerids)) {

                    String[] pcustomeridArr = pcustomerids.split(",");
                    Arrays.sort(pcustomeridArr);
                    StringBuilder pcustomernameSb = new StringBuilder();
                    for(String pcustomerid : pcustomeridArr) {

                        Customer pcustomer = getCustomerInfo(pcustomerid);
                        if(pcustomer != null) {
                            pcustomernameSb.append(",");
                            pcustomernameSb.append(pcustomer.getName());
                        }
                    }

                    rule.setCustomername(pcustomernameSb.length() > 0 ? new String(pcustomernameSb).substring(1) : new String(pcustomernameSb));
                }

            // 客户分类
            } else if("3".equals(customertype)) {

                String customersorts = rule.getCustomersort();
                if(StringUtils.isNotEmpty(customersorts)) {

                    String[] customersortArr = customersorts.split(",");
                    Arrays.sort(customersortArr);
                    StringBuilder customersortnameSb = new StringBuilder();
                    for(String customersort : customersortArr) {

                        CustomerSort customerSort = getCustomerSortDetail(customersort);
                        if(customerSort != null) {
                            customersortnameSb.append(",");
                            customersortnameSb.append(customerSort.getThisname());
                        }
                    }
                    rule.setCustomersortname(customersortnameSb.length() > 0 ? new String(customersortnameSb).substring(1) : new String(customersortnameSb));
                }

            // 促销分类
            } else if("4".equals(customertype)) {

                String promotionsorts = rule.getPromotionsort();
                if(StringUtils.isNotEmpty(promotionsorts)) {

                    String[] promotionsortArr = promotionsorts.split(",");
                    Arrays.sort(promotionsortArr);
                    StringBuilder promotionsortnameSb = new StringBuilder();
                    for(String promotionsort : promotionsortArr) {

                        SysCode sysCode = getBaseSysCodeInfo(promotionsort, "promotionsort");
                        if(sysCode != null) {
                            promotionsortnameSb.append(",");
                            promotionsortnameSb.append(sysCode.getCodename());
                        }
                    }
                    rule.setPromotionsortname(promotionsortnameSb.length() > 0 ? new String(promotionsortnameSb).substring(1) : new String(promotionsortnameSb));
                }

            // 销售区域
            } else if("5".equals(customertype)) {

                String salesareas = rule.getSalesarea();
                if(StringUtils.isNotEmpty(salesareas)) {

                    String[] salesareaArr = salesareas.split(",");
                    Arrays.sort(salesareaArr);
                    StringBuilder salesareanameSb = new StringBuilder();
                    for(String salesarea : salesareaArr) {

                        SalesArea salesArea = getSalesAreaDetail(salesarea);
                        if(salesArea != null) {

                            salesareanameSb.append(",");
                            salesareanameSb.append(salesArea.getThisname());
                        }
                    }
                    rule.setSalesareaname(salesareanameSb.length() > 0 ? new String(salesareanameSb).substring(1) : new String(salesareanameSb));
                }

            // 信用等级
            } else if("6".equals(customertype)) {

                String creditratings = rule.getCreditrating();
                if(StringUtils.isNotEmpty(creditratings)) {

                    String[] creditratingArr = creditratings.split(",");
                    Arrays.sort(creditratingArr);
                    StringBuilder creditratingnameSb = new StringBuilder();
                    for(String creditrating : creditratingArr) {

                        SysCode sysCode = getBaseSysCodeInfo(creditrating, "creditrating");
                        if(sysCode != null) {

                            creditratingnameSb.append(",");
                            creditratingnameSb.append(sysCode.getCodename());
                        }
                    }
                    rule.setCreditratingname(creditratingnameSb.length() > 0 ? new String(creditratingnameSb).substring(1) : new String(creditratingnameSb));
                }

            // 核销方式
            } else if("7".equals(customertype)) {

                String canceltypes = rule.getCanceltype();
                if(StringUtils.isNotEmpty(canceltypes)) {

                    String[] canceltypeArr = canceltypes.split(",");
                    Arrays.sort(canceltypeArr);
                    StringBuilder canceltypenameSb = new StringBuilder();
                    for(String canceltype : canceltypeArr) {

                        SysCode sysCode = getBaseSysCodeInfo(canceltype, "canceltype");
                        if(sysCode != null) {

                            canceltypenameSb.append(",");
                            canceltypenameSb.append(sysCode.getCodename());
                        }
                    }
                    rule.setCanceltypename(canceltypenameSb.length() > 0 ? new String(canceltypenameSb).substring(1) : new String(canceltypenameSb));
                }
            }
        }

        int count = distributionRuleMapper.selectDistributionRuleTotalCount(pageMap);
        return new PageData(count, list, pageMap);
    }

    @Override
    public List<DistributionRuleDetail> selectDistributionRuleDetailListByRuleid(String ruleid) throws Exception {

//        DistributionRule rule = distributionRuleMapper.selectDistributionRule(ruleid);
        List<DistributionRuleDetail> list = distributionRuleMapper.selectDistributionRuleDetailListByRuleid(ruleid);
        for(DistributionRuleDetail detail : list) {

            // 商品
            if(StringUtils.isNotEmpty(detail.getGoodsid())) {
                GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(detail.getGoodsid());
                if(goodsInfo != null) {
                    detail.setGoodsname(goodsInfo.getName());
                    detail.setBarcode(goodsInfo.getBarcode());
                    detail.setBrandid(goodsInfo.getBrand());
                    detail.setGoodstype(goodsInfo.getGoodstype());
                    detail.setGoodssort(goodsInfo.getDefaultsort());
                    detail.setSupplierid(goodsInfo.getDefaultsupplier());
                }
            }

            // 品牌
            if(StringUtils.isNotEmpty(detail.getBrandid())) {

                Brand brand = goodsMapper.getBrandInfo(detail.getBrandid());
                if(brand != null) {
                    detail.setBrandname(brand.getName());
                }
            }

            // 商品类型
            if(StringUtils.isNotEmpty(detail.getGoodstype())) {

                SysCode sysCode = getBaseSysCodeInfo(detail.getGoodstype(), "goodstype");
                if (sysCode != null) {
                    detail.setGoodstypename(sysCode.getCodename());
                }
            }

            // 商品分类
            if(StringUtils.isNotEmpty(detail.getGoodssort())) {

                WaresClass waresClass = goodsMapper.getWaresClassInfo(detail.getGoodssort());
                if (waresClass != null) {
                    detail.setGoodssortname(waresClass.getThisname());
                }
            }

            // 供应商
            if(StringUtils.isNotEmpty(detail.getSupplierid())) {

                BuySupplier supplier = buySupplierMapper.getBuySupplier(detail.getSupplierid());
                if (supplier != null) {
                    detail.setSuppliername(supplier.getName());
                }
            }
        }
        return list;
    }

    @Override
    public Map enableDistributionRule(String[] idArr) throws Exception {

        SysUser user = getSysUser();

        int success = 0;
        int fail = 0;
        StringBuilder msgSb = new StringBuilder();
        StringBuilder successSb = new StringBuilder();
        for(String id : idArr) {

            DistributionRule rule = selectDistributionRule(id);
            if(rule == null) {
                fail++;
                msgSb.append("编号：" + id + " 已不存在。<br/>");
                continue;
            }
            if("1".equals(rule.getState())) {
                fail++;
                msgSb.append("编号：" + rule.getId() + " 已启用。<br/>");
                continue;
            }

            Map param = new HashMap();
            param.put("id", id);
            param.put("openuserid", user.getUserid());
            param.put("openusername", user.getName());

            int ret = distributionRuleMapper.enableDistributionRule(param);
            if(ret > 0) {
                success++;
                successSb.append((success > 0 ? ", " : "") + id);
            } else {
                fail++;
                msgSb.append("编号：" + rule.getId() + " 无法启用。<br/>");
            }
        }

        Map result = new HashMap();
        result.put("success", success);
        result.put("fail", fail);
        result.put("successIds", success > 0 ? new String(successSb).substring(2) : new String(successSb));
        result.put("msg", new String(msgSb));
        return result;
    }

    @Override
    public Map disableDistributionRule(String[] idArr) throws Exception {

        SysUser user = getSysUser();

        int success = 0;
        int fail = 0;
        StringBuilder msgSb = new StringBuilder();
        StringBuilder successSb = new StringBuilder();
        for(String id : idArr) {

            DistributionRule rule = selectDistributionRule(id);
            if(rule == null) {
                fail++;
                msgSb.append("编号：" + id + " 已不存在。<br/>");
                continue;
            }
            if("0".equals(rule.getState())) {
                fail++;
                msgSb.append("编号：" + rule.getId() + " 已禁用。<br/>");
                continue;
            }
            if("2".equals(rule.getState())) {
                fail++;
                msgSb.append("编号：" + rule.getId() + " 未启用。<br/>");
                continue;
            }

            Map param = new HashMap();
            param.put("id", id);
            param.put("closeuserid", user.getUserid());
            param.put("closeusername", user.getName());

            int ret = distributionRuleMapper.disableDistributionRule(param);
            if(ret > 0) {
                success++;
                successSb.append((success > 0 ? ", " : "") + id);
            } else {
                fail++;
                msgSb.append("编号：" + rule.getId() + " 无法禁用。<br/>");
            }
        }

        Map result = new HashMap();
        result.put("success", success);
        result.put("fail", fail);
        result.put("successIds", success > 0 ? new String(successSb).substring(2) : new String(successSb));
        result.put("msg", new String(msgSb));
        return result;
    }

    @Override
    public List<DistributionRule> selectDistributionRuleIdByCustomer(String customerid, String canbuy) throws Exception {

        Customer customer = getCustomerInfo(customerid);
        if(customer != null) {
            Map map = new HashMap();
            map.put("customerid", customer.getId());
            map.put("pcustomerid", CommonUtils.emptyToNull(customer.getPid()));
            map.put("customersort", CommonUtils.emptyToNull(customer.getCustomersort()));
            map.put("salesarea", CommonUtils.emptyToNull(customer.getSalesarea()));
            map.put("creditrating", CommonUtils.emptyToNull(customer.getCreditrating()));
            map.put("canceltype", CommonUtils.emptyToNull(customer.getCanceltype()));
            map.put("promotionsort", CommonUtils.emptyToNull(customer.getPromotionsort()));
            map.put("canbuy", canbuy);

            List<DistributionRule> distributionids = distributionRuleMapper.selectDistributionRuleIdByCustomer(map);
            return distributionids;

        }

        return new ArrayList();
    }

	/**
	 * 获取全部客户档案
	 * @param
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 22, 2017
	 */
	public List getAllCustomer(){
		return customerMapper.getAllCustomer();
	}
}

