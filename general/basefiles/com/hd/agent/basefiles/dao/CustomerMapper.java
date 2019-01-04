package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CustomerMapper {

    public List getCustomerByPersonid(String pid);
    /**
     * 获取全部客户列表数据
     * @return
     * @author panxiaoxiao
     * @date Feb 12, 2015
     */
    public List getAllCustomerList();

    /**
     * 获取客户档案信息列表
     * @return
     */
    public List getAllCustomerInfoList();
	/**
	 * 获取客户列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public List getCustomerList(PageMap pageMap);
    /**
     * 根据条件查找对应客户
     * @param map
     * @return
     * @author lin-xx
     * @date oct 20, 2015
     */
    public List getCustomerByConditon(Map map);
	
	/**
	 * 获取客户列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public int getCustomerCount(PageMap pageMap);
	
	/**
	 * 为客户合同商品获取客户列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public List getCustomerListForCustomerprice(PageMap pageMap);
	
	/**
	 * 为客户合同商品获取客户列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public int getCustomerCountForCustomerprice(PageMap pageMap);
	
	/**
	 * 根据默认业务员获取客户列表数量
	 * @param salesuserid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 8, 2014
	 */
	public int getCustomerListCountBySalesuserid(@Param("salesuserid")String salesuserid);
	
	public List getCustomerParentAllChildren(PageMap pageMap);
	
	public List getCustomerBySalesman(String username);
	
	/**
	 * 业务员客户
	 * @param userId
	 * @return
	 * @author zhengziyong 
	 * @date Dec 2, 2013
	 */
	public List getCustomerBySalesmanId(String userId);
	
	/**
	 * 品牌业务员客户
	 * @param userId
	 * @return
	 * @author zhengziyong 
	 * @date Dec 2, 2013
	 */
	public List getBrandSalerCustomer(String userId);
	/**
	 * 客户合同价格列表
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Dec 2, 2013
	 */
	public List getCustomerPriceList(Map map);

    /**
     * 客户合同价格列表数量
     * @param map
     * @return
     */
    public int getCustomerPriceListCount(Map map);
	/**
	 * 根据品牌编号 获取客户合同价格列表
	 * @param brands
	 * @return
	 * @author zhengziyong 
	 * @date Dec 2, 2013
	 */
	public List getCustomerPriceListByBrands(@Param("brands")String brands);
	/**
	 * 业务员客户合同价格数量
	 * @param userId
	 * @return
	 * @author chenwei 
	 * @date Mar 17, 2014
	 */
	public int getCustomerPriceCount(String userId);
	
	/**
	 * 品牌业务员客户合同价格列表
	 * @param userId
	 * @return
	 * @author zhengziyong 
	 * @date Dec 2, 2013
	 */
	public List getBrandSalerCustomerPriceList(String userId);
	/**
	 * 获取品牌业务员客户合同价格数量
	 * @param userId
	 * @return
	 * @author chenwei 
	 * @date Mar 17, 2014
	 */
	public int getBrandSalerCustomerPriceCount(String userId);
	/**
	 * 获取车销人员客户合同价列表
	 * @param userId
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2014
	 */
	public List getStorageCustomerPriceList(String userId);
	
	/**
	 * 所有客户合同价格列表
	 * @param startnum
     * @param rows
	 * @return
	 * @author zhengziyong 
	 * @date Dec 2, 2013
	 */
	public List getAllCustomerPriceList(@Param("startnum")int startnum,@Param("rows")int rows);
	/**
	 * 所有客户合同价格数量
	 * @return
	 * @author chenwei 
	 * @date Mar 17, 2014
	 */
	public int getAllCustomerPriceCount();
	/**
	 * 客户业务员客户交易商品列表
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2014
	 */
	public List getSalerCustomerGoodsList(Map map);

    /**
     * 客户业务员客户交易商品列表数量
     * @param map
     * @return
     */
    public int getSalerCustomerGoodsListCount(Map map);
	/**
	 * 客户品牌价格套列表
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public List getCustomerBrandPricesort(Map map);
	/**
	 * 根据品牌获取 客户交易商品列表
	 * @param brands
	 * @return
	 * @author chenwei 
	 * @date Apr 16, 2014
	 */
	public List getSalerCustomerGoodsListByBrand(@Param("brands")String brands);
	/**
	 * 品牌业务员交易商品列表
	 * @param userId
	 * @return
	 * @author chenwei 
	 * @date Mar 18, 2014
	 */
	public List getBrandSalerCustomerGoodsList(String userId);
	/**
	 * 车销人员交易商品列表
	 * @param userId
	 * @return
	 * @author chenwei 
	 * @date Mar 14, 2014
	 */
	public List getStorageCustomerGoodsList(String userId);
	/**
	 * 所有客户产品
	 * @param userId
	 * @return
	 * @author zhengziyong 
	 * @date Dec 2, 2013
	 */
	public List getAllCustomerGoodsList(String userId);
	
	/**
	 * 查询有共同父节点的所有启用状态节点的个数
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date Jul 5, 2013
	 */
	public int getCustomerOpenCountByPid(String id);
	
	public Customer getCustomerDetail(Map map);

	/**
	 * 获取客户档案信息（无缓存）
	 * @param id
	 * @return
	 */
	public Customer getCustomerDetailNoCache(@Param("id")String id);
	
	public int deleteCustomer(String id);
	
	public int addCustomer(Customer customer);
	
	public int updateCustomer(Customer customer);
	
	public int editMoreCustomer(Customer customer);
	
	public int updateCustomerOpen(Customer customer);
	
	public int updateCustomerClose(Customer customer);
	
	public int updateCustomerLast(String islast, String id);

	/**
	 * 处理默认销售部门名称
	 * @return
	 */
	public int updateSalesdeptname();
	
	/**
	 * 获取客户的价格套 
	 */
	public String getCustomerPriceInfo(String id);
	
	/**
	 * 回写客户档案
	 */
	public int updateCustomerBack(Customer customer);
	
	/**
	 * 获取客户档案回写的数据
	 */
	public Customer getCustomerBack(String id);
	
	/**
	 * 根据默认业务员获取客户列表数据
	 * @param salesuserid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 24, 2013
	 */
	public List getCustomerBySalesuserid(String salesuserid);
	
	/**
	 * 获取客户合同商品详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 31, 2014
	 */
	public CustomerPrice getCustomerPrice(String id);
	
	/**
	 * 根据客户编码获取合同商品列表数据
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 31, 2014
	 */
	public List<CustomerPrice> getCustomerPriceListByCustomer(String id);
	
	public CustomerPrice getCustomerPriceByCustomerAndGoods(@Param("customerid")String customerid, @Param("goodsid")String goodsid);
	/**
	 * 根据客户编号和客户店内码 获取客户合同价
	 * @param customerid
	 * @param shopid
	 * @return
	 * @author chenwei 
	 * @date Dec 26, 2013
	 */
	public CustomerPrice getCustomerPriceByCustomerAndShopid(@Param("customerid")String customerid, @Param("shopid")String shopid);
	
	/**
	 * 删除包含给客户的合同商品
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 7, 2014
	 */
	public int deleteCustomerPriceByCustomer(String id);
	
	/**
	 * 根据商品编码删除客户合同商品
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 31, 2014
	 */
	public int deleteCustomerPriceByGoods(String goodsid);
	
	/**
	 * 根据编码删除客户合同商品
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 31, 2014
	 */
	public int deleteCustomerPriceById(String id);
	
	/**
	 * 新增客户合同商品
	 * @param price
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 31, 2014
	 */
	public int addCustomerPrice(CustomerPrice price);
	
	/**
	 * 批量新增客户合同商品
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 31, 2014
	 */
	public int addCustomerPriceMore(List list);
	
	/**
	 * 批量新增完整客户合同商品
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public int addCustomerPriceMoreTotal(List list);
	
	/**
	 * 判断是否已存在该客户合同商品
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public int doCheckIsExistCustomerPrice(Map map);
	
	/**
	 * 修改合同商品
	 * @param price
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 31, 2014
	 */
	public int updateCustomerPrice(CustomerPrice price);
	
	/**
	 * 根据商品编码获取客户合同商品数据
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 31, 2014
	 */
	public List getCustomerPriceListByGoodsid(String goodsid);
	
	/**
	 * 获取未选择客户列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 25, 2013
	 */
	public List getCustomerListForCombobox(PageMap pageMap);
	
	/**
	 * 获取未选择客户列表数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 25, 2013
	 */
	public int getCustomerListForComboboxCount(PageMap pageMap);
	/**
	 * 根据id值获取客户列表
	 * id查询客户编号 客户助记码
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public List getCustomerSelectListData(PageMap pageMap);
	/**
	 * 根据id值获取客户列表数量
	 * id查询客户编号 客户助记码
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public int getCustomerSelectListDataCount(PageMap pageMap);
	
	/**
	 * 验证名称是否重复
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 7, 2013
	 */
	public int customerNameNoUsed(String name);
	
	/**
	 * 根据pageMap获取合同商品列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public List getPriceListByPageMap(PageMap pageMap)throws Exception;
	
	/**
	 * 根据pageMap获取合同商品列表数量
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 10, 2013
	 */
	public int getPriceCountByPageMap(PageMap pageMap)throws Exception;
	
	/**
	 * 新增已买过客户商品
	 * @param customerGoods
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public int addCustomerGoods(CustomerGoods customerGoods)throws Exception;
	
	/**
	 * 根据客户编码和商品编码获取已买商品-检验是否存在该商品
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public CustomerGoods getCustomerGoodsByCustomerAndGoodsid(@Param("customerid")String customerid,
			@Param("goodsid")String goodsid)throws Exception;
	
	/**
	 * 修改已买客户商品
	 * @param customerGoods
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public int updateCustomerGoods(CustomerGoods customerGoods)throws Exception;
	
	/**
	 * 获取已买客户商品列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public List getCustomerGoodsList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取已买客户商品数量
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 10, 2013
	 */
	public int getCustomerGoodsCount(PageMap pageMap)throws Exception;
	
	public Customer getCustomerInfo(String id)throws Exception;
	
	public List getCustomerByName(String name)throws Exception;
	
	public List getCustoemrPriceListByCustomerid(String customerid)throws Exception;
	
	public int deleteCustomerPrices(String id)throws Exception;
	
	public int checkCustomerPrice(@Param("customerid")String customerid,@Param("goodsid")String goodsid)throws Exception;
	/**
	 * 根据客户编码和商品编码，获取客户商品合同价
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月16日
	 */
	public CustomerPrice getCustomerPriceByCustomerAndGoodsid(@Param("customerid")String customerid,@Param("goodsid")String goodsid)throws Exception;
	
	public List getCustoemrPriceList(PageMap pageMap)throws Exception;
	
	public int getCustoemrPriceCount(PageMap pageMap)throws Exception;
	
	public List getAllCustomer();
	/**
	 * 获取门店客户
	 * @return
	 * @author chenwei 
	 * @date Mar 26, 2014
	 */
	public List getAllCustomerWithoutPcustomer(Map map);
	/**
	 * 提供手机门店客户列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2015年9月10日
	 */
	public List getCustomerListForPhone(PageMap pageMap);

    /**
     *提供手机门店客户列表数量
     * @param pageMap
     * @return
     */
    public int getCustomerListForPhoneCount(PageMap pageMap);

	public List getCustomerListPidNull(PageMap pageMap);
	
	public int getCustomerCountPidNull(PageMap pageMap);
	
	public List getCustomerListPidNoNull(PageMap pageMap); 
	
	public int getCustomerCountPidNoNull(PageMap pageMap);
	
	/**
	 * 客户合同商品根据参数获取客户列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 20, 2014
	 */
	public List getCustomerListForPact(PageMap pageMap);
	
	public int getCustomerCountForPact(PageMap pageMap);
	
	/**
	 * 根据助记符获取客户信息
	 * @param shortcode
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 26, 2013
	 */
	public Customer getCustomerInfoLimitOne(String shortcode);
	
	/**
	 * 根据客户编码集获取客户列表数据
	 * @param idArr
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 16, 2014
	 */
	public List getCustomerListByidArr(@Param("array")String[] idArr);
	
	/**
	 * 根据店号+总店编码获取客户信息
	 * @param shopno
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 26, 2013
	 */
	public Customer getCustomerInfoByShopno(@Param("pid")String pid,@Param("shopno")String shopno);
	
	/**
	 * 根据客户编码集获取客户列表数据
	 * @param customerids
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 9, 2014
	 */
	public List getCustomerListByCustomerids(String customerids);
	/**
	 * 根据总店编号获取门店列表
	 * @param pid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月17日
	 */
	public List getCustomerListByPid(@Param("pid")String pid);
	/**
	 * 覆盖合同商品根据客户和商品
	 * @param cp
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 17, 2014
	 */
	public int updateCustomerPriceByParam(CustomerPrice cp);
	
	/**
	 * 根据客户编码数组字符串，商品编码获取客户合同商品列表
	 * @param map( customerids,goodsid)
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 20, 2014
	 */
	public List getCustomerPriceListByMap(Map map);
	
	/**
	 * 根据客户编码，商品编码集获取客户合同商品
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 13, 2015
	 */
	public List getCustomerPriceListByMapAloneCst(Map map);
	
	/**
	 * 删除已存在的客户合同商品
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 12, 2015
	 */
	public int deleteExistCustomerPrice(Map map);
	
	/**
	 * 品牌业务员修改后更新相关单据
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 28, 2014
	 */
	public int editBranduserAfterDoData(Map map);
	
	/*------------------------开始 -- 客户档案修改后更新相关单据-------------------*/
	public int editCustomerChangeBill(Customer customer);
	public int editCustomerSalesDemand(Customer customer);
	public int editCustomerSalesOrder(Customer customer);
	public int editCustomerSalesOrderCar(Customer customer);
	public int editCustomerSalesDispatchbill(Customer customer);
	public int editCustomerSalesReceipt(Customer customer);
	public int editCustomerSalesRejectbill(Customer customer);
	public int editCustomerSalesSaleout(Customer customer);
	public int editCustomerSalesInvoice(Customer customer);
	public int editCustomerSalesInvoiceDetail(Customer customer);
	public int editCustomerSalesInvoiceBill(Customer customer);
	public int editCustomerSalesInvoiceBillDetail(Customer customer);
	public int editCustomerSalesSalerejectEnter(Customer customer);
	public int editCustomerSalesPushBalance(Customer customer);
    public int editCustomerSalesBeginAmount(Customer customer);
	/*-----------------结束 -- 客户档案修改后更新相关单据-------------------*/
	
	/*------------------------开始 -- 品牌业务员修改后更新相关单据-------------------*/
	public int editBranduserChangeBill(Map map);
	public int editOrderDispatchbill(Map map);
	public int editOrderCar(Map map);
	public int editOrder(Map map);
	public int editOrderReceipt(Map map);
	public int editOrderRejectbill(Map map);
	public int editOrderSaleout(Map map);
	public int editOrderSalerejectEnter(Map map);
	public int editOrderInvoice(Map map);
	public int editOrderInvoiceBill(Map map);
	public int editOrderPushBalance(Map map);

	public int editOrderDemandBC(Map map);
	public int editOrderDispatchbillBC(Map map);
	public int editOrderCarBC(Map map);
	public int editOrderBC(Map map);
	public int editOrderReceiptBC(Map map);
	public int editOrderRejectbillBC(Map map);
	public int editOrderSaleoutBC(Map map);
	public int editOrderSalerejectEnterBC(Map map);
	public int editOrderInvoiceBC(Map map);
	public int editOrderInvoiceBillBC(Map map);
	public int editOrderPushBalanceBC(Map map);

	public int editOrderDemandBCDel(Map map);
	public int editOrderDispatchbillBCDel(Map map);
	public int editOrderCarBCDel(Map map);
	public int editOrderBCDel(Map map);
	public int editOrderReceiptBCDel(Map map);
	public int editOrderRejectbillBCDel(Map map);
	public int editOrderSaleoutBCDel(Map map);
	public int editOrderSalerejectEnterBCDel(Map map);
	public int editOrderInvoiceBCDel(Map map);
	public int editOrderInvoiceBillBCDel(Map map);
	public int editOrderPushBalanceBCDel(Map map);
	/*-----------------结束 -- 品牌业务员修改后更新相关单据-------------------*/
	
	/*------------------开始 -- 厂家业务员修改后更新相关单据--------------------*/
	public int editSupplieruserChangeBill(Map map);
	public int editSupplieruserChangeDispatchbill(Map map);
	public int editSupplieruserChangeOrderCar(Map map);
	public int editSupplieruserChangeOrder(Map map);
	public int editSupplieruserChangeReceipt(Map map);
	public int editSupplieruserChangeRejectbill(Map map);
	public int editSupplieruserChangeSaleout(Map map);
	public int editSupplieruserChangeSaleRejectEnter(Map map);
	public int editSupplieruserChangeInvoice(Map map);
	public int editSupplieruserChangeInvoiceBill(Map map);
	public int editSupplieruserChangePushBalance(Map map);

	public int editSupplieruserChangeDemandBC(Map map);
	public int editSupplieruserChangeDispatchbillBC(Map map);
	public int editSupplieruserChangeOrderCarBC(Map map);
	public int editSupplieruserChangeOrderBC(Map map);
	public int editSupplieruserChangeReceiptBC(Map map);
	public int editSupplieruserChangeRejectbillBC(Map map);
	public int editSupplieruserChangeSaleoutBC(Map map);
	public int editSupplieruserChangeSaleRejectEnterBC(Map map);
	public int editSupplieruserChangeInvoiceBC(Map map);
	public int editSupplieruserChangeInvoiceBillBC(Map map);
	public int editSupplieruserChangePushBalanceBC(Map map);

	public int editSupplieruserChangeDemandDel(Map map);
	public int editSupplieruserChangeDispatchbillDel(Map map);
	public int editSupplieruserChangeOrderCarDel(Map map);
	public int editSupplieruserChangeOrderDel(Map map);
	public int editSupplieruserChangeReceiptDel(Map map);
	public int editSupplieruserChangeRejectbillDel(Map map);
	public int editSupplieruserChangeSaleoutDel(Map map);
	public int editSupplieruserChangeSaleRejectEnterDel(Map map);
	public int editSupplieruserChangeInvoiceDel(Map map);
	public int editSupplieruserChangeInvoiceBillDel(Map map);
	public int editSupplieruserChangePushBalanceDel(Map map);
	/*------------------结束 -- 厂家业务员修改后更新相关单据--------------------*/
	
	/**
	 * 根据联系人编码获取客户数据
	 */
	public List getCustomerListByContact(@Param("contact")String contact);
	
	/**
	 * 根据客户编码和商品编码判断是否买过该商品
	 * @param customerid
     * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 2, 2014
	 */
	public int checkCustomerGoods(@Param("customerid")String customerid,@Param("goodsid")String goodsid);
	
	/*客户品牌对应价格套*/
	/**
	 * 根据条件提供客户品牌对应价格套的 客户列表
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public List getCustomerListWithBrandPricesort(Map map);
	/**
	 * 删除客户品牌对应价格套
	 * @param customerid
	 * @param brandids
	 * @return
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public int detleCustomerBrandPricesortByCustomeridAndBrandid(@Param("customerid")String customerid,@Param("brandids")String brandids);
	/**
	 * 添加客户品牌对应价格套
	 * @param customerid
	 * @param brandid
	 * @param pricesort
	 * @return
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public int addCustomerBrandPricesort(@Param("customerid")String customerid,@Param("brandid")String brandid,@Param("pricesort")String pricesort,@Param("remark")String remark);
	/**
	 * 获取客户品牌价格套列表
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date 2015年1月9日
	 */
	public List showCustomerBrandPricesort(@Param("customerid")String customerid);
	
	/**
	 * 根据客户编号和品牌编号 获取客户的价格套 
	 */
	public String getCustomerPriceInfoByCustomeridAndBrandid(@Param("customerid")String customerid,@Param("brandid")String brandid);

	/**
	 * 根据客户编号和品牌编号 获取客户总店的价格套 
	 */
	public String getCustomerPriceInfoByPCustomeridAndBrandid(@Param("customerid")String customerid,@Param("brandid")String brandid);
	
	/**
	 * 修改品牌价格套
	 * @param customerBrandPricesort
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 12, 2015
	 */
	public int editCustomerBrandPricesort(CustomerBrandPricesort customerBrandPricesort);
	
	/**
	 * 删除品牌价格套
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 12, 2015
	 */
	public int deleteCustomerBrandPricesorts(@Param("id")String id);
	
	/**
	 * 根据品牌价格套编码获取品牌价格套信息
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 12, 2015
	 */
	public CustomerBrandPricesort getCustomerBrandPricesortInfo(@Param("id")String id);

    /**
     * 根据条件获取客户业务员对应客户
     * @param pageMap
     * @return
     */
    public List<Customer> getCustomerListBySalesman(PageMap pageMap);

    public int getCustomerListBySalesmanCount(PageMap pageMap);

    /**
     * 根据条件获取品牌业务员对应客户
     * @param pageMap
     * @return
     */
    public List<Customer> getCustomerByBrandman(PageMap pageMap);

    public int getCustomerByBrandmanCount(PageMap pageMap);

    /**
     * 根据条件获取厂家业务员对应客户
     * @param pageMap
     * @return
     */
    public List<Customer> getCustomerByManufactedMan(PageMap pageMap);

    public int getCustomerByManufactedManCount(PageMap pageMap);

    /**
     * 判断店号是否重复
     * @param pcustomerid
     * @param shopno
     * @return
     */
    public int getShopnoIsRepeatFlag(@Param("pcustomerid")String pcustomerid, @Param("shopno")String shopno);
    /**
     * 根据编号获取客户信息
     * @param id
     * @return
     */
    public Customer getCustomerById (String id);

	/**
	 * 根据客户编码获取对应品牌结算方式
	 * @param customerid
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-03-29
	 */
	public List getCustomerBrandSettletypeList(@Param("customerid")String customerid);

	/**
	 * 删除客户品牌结算方式
	 * @param customerid
	 * @param brandids
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	public int detleCustomerBrandSettletypeByCustomeridAndBrandid(@Param("customerid")String customerid,@Param("brandids")String brandids);

	/**
	 * 新增客户品牌结算方式
	 * @param customerBrandSettletype
	 * @return
	 */
	public int addCustomerBrandSettletype(CustomerBrandSettletype customerBrandSettletype);

	/**
	 * 根据客户编码品牌编码获取客户品牌结算方式数据
	 * @return
	 */
	public CustomerBrandSettletype getCustomerBrandSettletypeByCustomeridAndBrandid(@Param("customerid")String customerid,@Param("brandid")String brandid);

	/**
	 * 修改客户品牌结算方式
	 * @param customerBrandSettletype
	 * @return
	 */
	public int editCustomerBrandSettletype(CustomerBrandSettletype customerBrandSettletype);

	/**
	 * 根据编码获取客户品牌结算方式
	 * @param id
	 * @return
	 */
	public CustomerBrandSettletype getCustomerBrandSettletypeInfo(@Param("id")String id);

	/**
	 * 根据编号删除客户品牌结算方式
	 * @param id
	 * @return
	 */
	public int deleteCustomerBrandSettletype(@Param("id")String id);

	/**
	 * 根据品牌编码、客户编码删除客户品牌结算方式
	 * @param brandid
	 * @param customerid
	 * @return
	 */
	public int deleteCustomerBrandSettletypeByCustomeridAndBrandid(@Param("brandid")String brandid,@Param("customerid")String customerid);

	void editGlobalOrderDemandBC(Map map);

	void editGlobalOrderDispatchbillBC(Map map);

	void editGlobalOrderCarBC(Map map);

	void editGlobalOrderBC(Map map);

	void editGlobalOrderReceiptBC(Map map);

	void editGlobalOrderRejectbillBC(Map map);

	void editGlobalOrderSaleoutBC(Map map);

	void editGlobalOrderSalerejectEnterBC(Map map);

	void editGlobalOrderInvoiceBC(Map map);

	void editGlobalOrderInvoiceBillBC(Map map);

	void editGlobalOrderPushBalanceBC(Map map);


	void editGlobalSupplieruserChangeDemandBC(Map map);

	void editGlobalSupplieruserChangeDispatchbillBC(Map map);

	void editGlobalSupplieruserChangeOrderCarBC(Map map);

	void editGlobalSupplieruserChangeOrderBC(Map map);

	void editGlobalSupplieruserChangeReceiptBC(Map map);

	void editGlobalSupplieruserChangeRejectbillBC(Map map);

	void editGlobalSupplieruserChangeSaleoutBC(Map map);

	void editGlobalSupplieruserChangeSaleRejectEnterBC(Map map);

	void editGlobalSupplieruserChangeInvoiceBC(Map map);

	void editGlobalSupplieruserChangeInvoiceBillBC(Map map);

	void editGlobalSupplieruserChangePushBalanceBC(Map map);

	/**
	 * 获取各单据明细数量
	 * @return
	 */
	public Map getBillDetailsNum();

	/**
	 * 清除客户档案中的默认客户业务员
	 * @param salesuserid
	 * @return
	 */
	public int editClearCustomerSalesuser(@Param("salesuserid")String salesuserid);

    /**
     * 获取客户坐标
     *
     * @return
     * @author limin
     * @date Sep 6, 2016
     */
    public List<Map> getCustomerLocationList(@Param("customerids") List<String> customerids);

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
    public CustomerLocation selectCustomerLocation(@Param("customerid") String customerid);

    /**
     * 新增客户坐标
     *
     * @param customerid
     * @return
     * @date Sep 22, 2016
     */
    public int deleteCustomerLocation(@Param("customerid") String customerid);

	/**
	 * 查询无坐标客户list
	 *
	 * @return
	 * @return
	 * @date Oct 17, 2016
	 */
	public List<Map> selectUnlocatedCustomerList();

	/**
	 * 获取更新时间在time时间之后的客户
	 *
	 * @param time      更新时间，获取更新时间在此之后的所有客户。格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 30, 2016
	 */
	public List<Customer> getCustomerListForMecshop(@Param("time") Date time);

	/**
	 * 获取所有的客户合同价
	 *
	 * @param offset
	 * @param rows
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 17, 2016
	 */
	public List<Map> getCustomerPriceListForMecshop(@Param("offset") int offset, @Param("rows") int rows);

	/**
	 * 根据客户更新客户最新销售日期
	 * @param customerid
	 * @param businessdate
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-21
	 */
	public int updateCustomerNewsaledate(@Param("customerid")String customerid, @Param("businessdate")String businessdate);

}