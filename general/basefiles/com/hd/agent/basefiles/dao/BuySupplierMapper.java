/**
 * @(#)BuySupplierMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-23 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.BuySupplierBrandSettletype;
import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface BuySupplierMapper {
	/**
	 * 获取供应商档案
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-25
	 */
	public BuySupplier getBuySupplier(@Param("id")String id);
	/**
	 * 添加供应商档案
	 * @param buySupplier
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int insertBuySupplier(BuySupplier buySupplier);
	/**
	 * 更新供应商档案
	 * @param buySupplier
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int updateBuySupplier(BuySupplier buySupplier);
	/**
	 * 删除供应商档案
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int deleteBuySupplier(@Param("id")String id);
	/**
	 * 根据Map参数删除供应商档案<br/>
	 * map中参数：<br/>
	 * id : 条件编号<br/>
	 * authDataSql : 数据权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int deleteBuySupplierBy(Map map);
	/**
	 * 分页数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public List getBuySupplierPageList(PageMap pageMap);
	/**
	 * 分页数据量
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int getBuySupplierPageListCount(PageMap pageMap);
	
	/**
	 * 供应商统计<br/>
	 * Map中参数: <br/>
	 * id : 本级编码 <br/>
	 * adduserid : 编号 <br/>
	 * authDataSql : 数据权限sql <br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-19
	 */
	public int getBuySupplierCountBy(Map map);
	/**
	 * 获取供应商列表<br/>
	 * map中参数：<br/>
	 * authCols:字段权限控制<br/>
	 * id:编号<br/>
	 * authDataSql:数据权限控制<br/>
	 * idarrs : 编号组字符串，格式：1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-27
	 */
	public List getBuySupplierListBy(Map map);
	
	/**
	 * 获取供应商<br/>
	 * map中参数：<br/>
	 * authCols:字段权限控制<br/>
	 * id:编号<br/>
	 * authDataSql:数据权限控制<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-27
	 */
	public BuySupplier getBuySupplierBy(Map map);
	
	/**
	 * 更新供应商档案<br/>
	 * map中参数<br/>
	 * 更新参数：<br>
	 * newid : 新编号<br/>
	 * state : 状态4新增3暂存2保存1启用0禁用<br/>
	 * disabledate : 禁用日期<br/>
	 * modifyuserid : 修改人用户编号 <br/>
	 * modifyusername : 修改人姓名 <br/>
	 * modifytime  : 修改时间 <br/>
	 * openuserid : 启用人用户编号 <br/>
	 * openusername : 启用人姓名<br/>
	 * opentime : 启用时间 <br/>
	 * closeuserid : 禁用人用户编号 <br/>
	 * closeusername : 禁用人姓名 <br/>
	 * closetime : 禁用时间 <br/>
	 * 条件参数：<br/>
	 * oldid : 原始编号<br/>
	 * authDataSql : 数据权限<br/>
	 * wstate :状态4新增3暂存2保存1启用0禁用<br/>
	 * statearrs : 状态组字符串<br/>
	 * @param buySupplier
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public int updateBuySupplierBy(Map map);
	
	/**
	 * 获取供应商编号和状态列表，有数据权限判断<br/>
	 * map中参数：<br/>
	 * authDataSql:数据权限控制<br/>
	 * idarrs : 编号组字符串，格式：1,2,3<br/>
	 * state :状态4新增3暂存2保存1启用0禁用<br/>
	 * statearrs : 状态组字符串<br/>
	 * authDataSql:数据权限控制<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-27
	 */
	public List getBuySupplierStateListBy(Map map);
	/**
	 * 根据id值获取供应商列表
	 * id查询供应商编码 供应商助记码
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public List getSupplierSelectListData(PageMap pageMap);
	/**
	 *  根据id值获取供应商列表数量
	 * id查询供应商编码 供应商助记码
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public int getSupplierSelectListDataCount(PageMap pageMap);
	
	public List returnSupplierIdByName(String name);
	
	/*------------------供应商档案修改后更新相关单据 -------------------------*/
	public int editPurchasePlannedOrder(BuySupplier buySupplier);
	public int editPurchaseBuyOrder(BuySupplier buySupplier);
	public int editPurchaseArrivalOrder(BuySupplier buySupplier);
	public int editPurchaseReturnOrder(BuySupplier buySupplier);
	public int editPurchaseInvoice(BuySupplier buySupplier);
	public int editStoragePurchaseEnter(BuySupplier buySupplier);
	public int editStoragePurchaserejectOut(BuySupplier buySupplier);

	public int editJsFundInput(BuySupplier buysupplier);

    /**
     * 分页数据
     * @return
     * @author zhanghonghui
     * @date 2013-4-23
     */
    public List getAllBuySupplierList();

	/**
	 * 获取更新时间在time时间之后的供应商
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 30, 2016
	 */
	public List<Map> getSupplierListForMecshop();

    /**
     * 获取启用的供应商list
     * @return
     * @author limin
     * @date Nov 2, 2016
     */
    public List<BuySupplier> getOpenedSupplierList();

	/**
	 * 调整代垫收回所属部门
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-15
	 */
	public int editMatcostsInputSupplierDeptCaseBuydeptid();

    public int editPayorderBuydeptCaseBuydeptid();

    public int editCustomerPayableBuydeptCaseBuydeptid();

	/**
	 * 获取供应商列表
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public List getSupplierListForPactList(PageMap pageMap);

	/**
	 * 获取供应商列表
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public int getSupplierListForPactCount(PageMap pageMap);

	/**
	 * 根据供应商和品牌删除供应商品牌结算方式
	 * @param supplierid
	 * @param brandids
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public int deleteSupplierBrandSettletypeBySupplieridAndBrandid(@Param("supplierid")String supplierid,@Param("brandids")String brandids);

	/**
	 * 添加供应商品牌结算方式
	 * @param buySupplierBrandSettletype
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public int addSupplierBrandSettletype(BuySupplierBrandSettletype buySupplierBrandSettletype);

	/**
	 * 获取供应商的品牌结算方式
	 * @param supplierid
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public List getSupplierBrandSettletypeList(@Param("supplierid")String supplierid);

	/**
	 * 根据id获取供应商品牌结算方式
	 * @param id
	 * @return com.hd.agent.basefiles.model.BuySupplierBrandSettletype
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public BuySupplierBrandSettletype getSupplierBrandSettletypeInfo(@Param("id")String id);

	/**
	 * 根据id删除供应商品牌结算方式
	 * @param id
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public int deleteSupplierBrandSettletype(@Param("id")String id);

	/**
	 * 修改供应商品牌结算方式
	 * @param buySupplierBrandSettletype
	 * @return java.lang.Boolean
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public int editSupplierBrandSettletype(BuySupplierBrandSettletype buySupplierBrandSettletype);

	/**
	 * 根据供应商和品牌获取供应商品牌结算方式
	 * @param supplierid
	 * @param brandid
	 * @return com.hd.agent.basefiles.model.BuySupplierBrandSettletype
	 * @throws
	 * @author luoqiang
	 * @date Oct 26, 2017
	 */
	public BuySupplierBrandSettletype getSupplierBrandSettletypeBySupplieridAndBrandid(@Param("supplierid")String supplierid,@Param("brandid")String brandid);
}

