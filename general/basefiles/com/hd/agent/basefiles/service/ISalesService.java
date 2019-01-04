/**
 * @(#)ISalesService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 10, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface ISalesService {

	/**
	 * 添加销售区域
	 * @param area
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public boolean addSalesArea(SalesArea area) throws Exception;
	
	/**
	 * 修改销售区域
	 * @param area
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public Map updateSalesArea(SalesArea area) throws Exception;
	
	/**
	 * 获取销售区域列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public List getSalesAreaList(PageMap pageMap) throws Exception;
	
	/**
	 * 通过销售区域编号获取销售区域信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public SalesArea getSalesAreaDetail(String id) throws Exception;
	
	/**
	 * 获取节点及节点下所有子节点
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public List getSalesAreaParentAllChildren(PageMap pageMap) throws Exception;
	
	/**
	 * 删除销售区域信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public boolean deleteSalesArea(String id) throws Exception;
	
	/**
	 * 启用销售区域信息
	 * @param area
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public boolean updateSalesAreaOpen(SalesArea area) throws Exception;
	
	/**
	 * 禁用销售区域信息
	 * @param area
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public boolean updateSalesAreaClose(SalesArea area) throws Exception;
	
	/**
	 * 判断销售区域本级名称是否重复
	 * @param thisname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public boolean isRepeatSalesAreaThisname(String thisname)throws Exception;
	
	/**
	 * 添加客户分类
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public boolean addCustomerSort(CustomerSort customer) throws Exception;
	
	/**
	 * 修改客户分类
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public Map updateCustomerSort(CustomerSort customer) throws Exception;
	
	/**
	 * 获取客户分类列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public List getCustomerSortList(PageMap pageMap) throws Exception;
	
	/**
	 * 通过客户分类编号获取客户分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public CustomerSort getCustomerSortDetail(String id) throws Exception;
	
	/**
	 * 获取节点及节点下所有子节点
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public List getCustomerSortParentAllChildren(PageMap pageMap) throws Exception;
	
	/**
	 * 删除客户分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public boolean deleteCustomerSort(String id) throws Exception;
	
	/**
	 * 启用客户分类信息
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public boolean updateCustomerSortOpen(CustomerSort customer) throws Exception;
	
	/**
	 * 禁用客户分类信息
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 11, 2013
	 */
	public boolean updateCustomerSortClose(CustomerSort customer) throws Exception;
	
	/**
	 * 判断客户分类本级名称是否重复
	 * @param thisname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public boolean isRepeatCustomerSortThisname(String thisname)throws Exception;
	/**
	 * 添加客户档案信息
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public boolean addCustomer(Customer customer) throws Exception;
	
	/**
	 * 修改客户档案信息
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public boolean updateCustomer(Customer customer, String sortEdit) throws Exception;
	
	/**
	 * 获取客户档案详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public Customer getCustomerDetail(String id) throws Exception;
	/**
	 * 获取单纯的客户档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-9
	 */
	public Customer getOnlyCustomer(String id) throws Exception;
	
	/**
	 * 根据助记符获取客户信息
	 * @param shortcode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2014
	 */
	public Customer getCustomerInfoLimitOneByShortcode(String shortcode)throws Exception;
	
	/**
	 * 获取客户档案列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public List getCustomerList(PageMap pageMap) throws Exception;

    /**
     * 根据条件获取客户
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Oct 20, 2015
     */
    public List getCustomerByConditon(Map map) throws Exception;
	
	/**
	 * 查询登录用户对应人员档案关联的客户档案
	 * @param username
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 24, 2013
	 */
	public List getCustomerBySalesman(String username) throws Exception;
	
	/**
	 * 获取客户档案节点及节点下所有子节点
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public List getCustomerParentAllChildren(PageMap pageMap) throws Exception;
	
	/**
	 * 根据客户编码集获取客户列表数据
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 16, 2014
	 */
	public List getCustomerListByidArr(String ids)throws Exception;
	
	/**
	 * 删除客户档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public boolean deleteCustomer(String id) throws Exception;
	
	/**
	 * 启用客户档案信息
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public boolean updateCustomerOpen(Customer customer) throws Exception;
	
	/**
	 * 禁用客户档案信息
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 15, 2013
	 */
	public boolean updateCustomerClose(Customer customer) throws Exception;
	
	/**
	 * 获取客户对应分类列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 22, 2013
	 */
	public List getCustomerAndSortListByCustomer(String id) throws Exception;
	
	/**
	 * 获取客户列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public PageData getCustomerData(PageMap pageMap) throws Exception;
	
	/**
	 * 为客户合同商品获取客户列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public PageData getCustomerListForCustomerprice(PageMap pageMap)throws Exception;
	
	/**
	 * 根据默认业务员获取客户列表数据
	 * @param salesuserid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 24, 2013
	 */
	public List getCustomerBySalesuserid(String salesuserid)throws Exception;
	
	/**
	 * 获取未选择客户列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 25, 2013
	 */
	public PageData getCustomerListForCombobox(PageMap pageMap)throws Exception;
	/**
	 * 根据id获取客户列表
	 * id查询客户编号 客户助记码
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public PageData getCustomerSelectListData(PageMap pageMap) throws Exception;
	
	/**
	 * 验证名称是否重复
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public boolean customerNameNoUsed(String name)throws Exception;
	
	/**
	 * 客户快捷新增
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public boolean addCustomerShortcut(Customer customer)throws Exception;
	
	/**
	 * 客户快捷修改
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 8, 2013
	 */
	public boolean editCustomerShortcut(Customer customer)throws Exception;
	
	/**
	 * 根据当前页page，每页显示条数rows获取合同商品列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public PageData getPriceListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 批量修改客户
	 * @param customer
	 * @param csList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 28, 2013
	 */
	public Map editCustomer(Customer customer,List<CustomerAndSort> csList)throws Exception;
	
	/**
	 * 获取已买客户商品
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public PageData getGoodsListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 导入客户
	 * @param customer
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 24, 2013
	 */
	public Map addDRCustomer(Customer customer)throws Exception;
	
	public Map addDRCustomerAndSort(List<CustomerAndSort> list)throws Exception;
	
	public Map addDRCustomerAndPrice(List<CustomerPrice> list)throws Exception;
	
	/**
	 * 快捷导入客户
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 28, 2013
	 */
	public Map addShortcutCustomerExcel(List<Customer> list)throws Exception;
	
	public boolean addCustomerPrice(CustomerPrice customerPrice)throws Exception;
	
	/**
	 * 复制合同商品
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public boolean copyCustomerPrice(Map map)throws Exception;
	
	/**
	 * 判断是否已存在该合同商品
	 * @param goodsids
	 * @param customerids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public Map doCheckIsExistCustomerPrice(String goodsids,String customerids)throws Exception;
	
	public boolean updateCustomerPrice(CustomerPrice customerPrice)throws Exception;
	
	public boolean deleteCustomerPriceById(String id)throws Exception;
	
	public List getCustoemrPriceListByCustomerid(String customerid)throws Exception;
	
	public CustomerPrice getCustomerPriceInfo(String id)throws Exception;
	
	public boolean deleteCustomerPrices(String idstr)throws  Exception;
	
	public Map addDRPriceCustomer(List<CustomerPrice> list)throws Exception;
	
	public PageData showPriceCustomerData(PageMap pageMap)throws Exception;
	
	public PageData getCustomerListData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取合同商品客户列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 13, 2013
	 */
	public PageData getCustomerDataForPact(PageMap pageMap)throws Exception;
	
	/**
	 * 根据店号+总店编码获取客户信息
	 * @param pid
	 * @param shopno
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 26, 2013
	 */
	public Customer getCustomerInfoByShopno(String pid,String shopno)throws Exception;
	
	/**
	 * 获取客户档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 18, 2014
	 */
	public Customer getCustomerInfo(String id)throws Exception;
	
	/**
	 * 批量修改客户合同商品
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 20, 2014
	 */
	public boolean updateMoreCustomerPrice(Map map,CustomerPrice customerPrice)throws Exception;
	/**
	 * 添加客户品牌对应价格套
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public boolean addCustomerBrandPricesort(Map map) throws Exception;
	/**
	 * 获取客户品牌价格套列表
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public List showCustomerBrandPricesort(String customerid) throws Exception;
	
	/**
	 * 修改品牌价格套
	 * @param customerBrandPricesort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 12, 2015
	 */
	public boolean editCustomerBrandPricesort(CustomerBrandPricesort customerBrandPricesort)throws Exception;
	
	/**
	 * 删除品牌价格套
	 * @param idstr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 12, 2015
	 */
	public Map deleteCustomerBrandPricesorts(String idstr)throws Exception;
	
	/**
	 *  根据id值获取销售部门
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月3日
	 */
	public PageData getSalesDepartmentSelectListData(PageMap pageMap) throws Exception ;

    /**
     * Excel导入销售区域列表数据
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-17
     */
    public Map addDRSalesAreaExcel(List<SalesArea> list)throws Exception;

    /**
     * Excel导入客户分类列表数据
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-17
     */
    public Map addDRCustomerSortExcel(List<CustomerSort> list)throws Exception;

    /**
     * 判断店号是否重复
     * @param pcustomerid
     * @param shopno
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-05-20
     */
    public boolean getShopnoIsRepeatFlag(String pcustomerid, String shopno)throws Exception;

    /**
     *
     * @param idlist
     * @return
     * @throws Exception
     * @author lin_xx
     * @date July 6, 2015
     */
    public List<Customer> getUpLoadCustomerMod(String[] idlist) throws Exception;
    /**
     * 获取客户资金情况
     * @param id
     * @return
     * @author chenwei
     * @date Jul 8, 2013
     */
    public CustomerCapital getCustomerCapital(String id) throws Exception;

	/**
	 * 根据客户编码获取对应品牌结算方式
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-29
	 */
	public List getCustomerBrandSettletypeList(String customerid)throws Exception;

	/**
	 * 新增客户品牌结算方式
	 * @param customerBrandSettletype
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	public boolean addCustomerBrandSettletype(CustomerBrandSettletype customerBrandSettletype)throws Exception;

	/**
	 * 修改客户品牌结算方式
	 * @param customerBrandSettletype
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	public boolean editCustomerBrandSettletype(CustomerBrandSettletype customerBrandSettletype)throws Exception;

	/**
	 * 根据编码字符串集合删除客户品牌结算方式
	 * @param idstr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	public Map deleteCustomerBrandSettletypes(String idstr)throws Exception;

	/**
	 * 导入客户品牌结算方式
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	public Map importCustomerBrandSettletype(List<Map> list)throws Exception;
	/**
	 * 根据map中参数获取客户分类列表<br/>
	 * map中参数：<br/>
	 * idarrs: id字符串<br/>
	 * state: 1启用0禁用<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui
	 * @date 2016-08-11
	 */
	public List getCustomerSortListByMap(Map map) throws Exception;

    /**
     * 获取客户坐标
     *
     * @param customerids
     * @return
     * @author limin
     * @date Sep 6, 2016
     */
    public List<Map> getCustomerLocationList(List<String> customerids) throws Exception;

    /**
     * 新增客户坐标
     *
     * @param location
     * @return
     * @date Sep 22, 2016
     */
    public int insertCustomerLocation(CustomerLocation location);

    /**
     * 新增客户坐标
     *
     * @param customerid
     * @return
     * @date Sep 22, 2016
     */
    public CustomerLocation selectCustomerLocation(String customerid);

    /**
     * 修改客户坐标
     *
     * @param customerLocation
     * @return
     * @date Sep 23, 2016
     */
    public int editCustomerLocation(CustomerLocation customerLocation) throws Exception;

	/**
	 * 查询无坐标客户list
	 *
	 * @return
	 * @return
	 * @date Oct 17, 2016
	 */
	public List<Map> selectUnlocatedCustomerList();

	/**
	 * 获取在更新日期之后的客户数据
	 *
	 * @param time
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 31, 2016
     */
	public List<Customer> getCustomerListForMecshop(Date time) throws Exception;

	/**
	 * 获取在更新日期之后的客户分类
	 *
	 * @param time
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 31, 2016
	 */
	public List<Map> getCustomersortListForMecshop(Date time) throws Exception;

	/**
	 * 获取客户合同价
	 *
	 * @param offset
	 * @param rows
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 17, 2016
	 */
	public List<Map> getCustomerPriceListForMecshop(String offset, String rows) throws Exception;

	/**
	 * 获取销售区域
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 29, 2016
	 */
	public List<Map> getSalesareaListForMecshop() throws Exception;

    /**
     * 查询商品
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 30, 2015
     */
    public PageData getGoodsList(PageMap pageMap) throws Exception;

    /**
     * 获取全部已启用品牌List
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 2, 2016
     */
    public List<Brand> getBrandList() throws Exception;

    /**
     * 获取全部已启用供应商List
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 2, 2016
     */
    public List<BuySupplier> getSupplierList() throws Exception;

    /**
     * 获取全部已启用供应商List
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 2, 2016
     */
    public List<WaresClass> getWaresClassList() throws Exception;


    /**
     * 新增分销规则
     *
     * @param distributionRule
     * @param details
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 3, 2016
     */
    public int addDistributionRule(DistributionRule distributionRule, List<DistributionRuleDetail> details) throws Exception;

    /**
     * 修改分销规则
     *
     * @param distributionRule
     * @param details
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 3, 2016
     */
    public int editDistributionRule(DistributionRule distributionRule, List<DistributionRuleDetail> details) throws Exception;

    /**
     * 删除分销规则
     *
     * @param idArr
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 3, 2016
     */
    public Map deleteDistributionRule(String[] idArr) throws Exception;

    /**
     * 查询分销规则
     *
     * @param id
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 3, 2016
     */
    public DistributionRule selectDistributionRule(String id) throws Exception;

    /**
     * 查询分销规则分页数据
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 4, 2016
     */
    public PageData selectDistributionRulePageData(PageMap pageMap) throws Exception;

    /**
     * 根据规则编号查询规则明细
     *
     * @param ruleid
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 4, 2016
     */
    public List<DistributionRuleDetail> selectDistributionRuleDetailListByRuleid(String ruleid) throws Exception;

    /**
     * 启用分销规则
     *
     * @param idArr
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 4, 2016
     */
    public Map enableDistributionRule(String[] idArr) throws Exception;

    /**
     * 禁用分销规则
     *
     * @param idArr
     * @return
     * @throws Exception
     * @author limin
     * @date Nov 4, 2016
     */
    public Map disableDistributionRule(String[] idArr) throws Exception;

    /**
     * 查询与客户相关的分销规则
     *
     * @param customerid
	 * @param canbuy
     * @return
     * @author limin
     * @date Nov 9, 2016
     */
    public List<DistributionRule> selectDistributionRuleIdByCustomer(String customerid, String canbuy) throws Exception;

	/**
	 * 获取全部客户档案
	 * @param
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 22, 2017
	 */
	public List getAllCustomer();

}

