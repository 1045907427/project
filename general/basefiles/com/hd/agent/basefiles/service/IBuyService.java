/**
 * @(#)IBuyAreaService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-17 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;


/**
 * 
 * 
 * @author zhanghonghui
 */
public interface IBuyService {
	/**
	 * 添加采购区域
	 * @param area
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public boolean addBuyArea(BuyArea area) throws Exception;
	
	/**
	 * 修改采购区域
	 * @param area
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public Map updateBuyArea(BuyArea area) throws Exception;
	
	/**
	 * 获取采购区域列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public List getBuyAreaList() throws Exception;
	
	/**
	 * 通过采购区域编号获取采购区域信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public BuyArea getBuyAreaDetail(String id) throws Exception;
	/**
	 * 根据map参数，获取采购区域信息<br/>
	 *  map中参数<br/>
	 *  parentAllChildren：获取节点及其所有子结点 <br/>	  
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-22
	 */
	public List getBuyAreaListByMap(Map map) throws Exception;
	
	/**
	 * 删除采购区域信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public boolean deleteBuyArea(String id) throws Exception;
	
	
	/**
	 * 更新采购区域<br/>
	 * map中参数:<br/>
	 * 可更新参数<br/>
	 * newid : 新编号<br/>
	 * state : 状态4新增3暂存2保存1启用0禁用<br/>
	 * modifyuserid : 最后修改人编号<br/>
	 * modifyusername : 最后修改人<br/>
	 * modifytime : 最后修改时间<br/>
	 * openuserid : 启用人编号<br/>
	 * openusername : 启用人<br/>
	 * opentime : 启用时间<br/>
	 * closeuserid : 禁用人 编号<br/>
	 * closeusername : 禁用人<br/>
	 * closetime : 禁用时间<br/>
	 * 条件参数<br/>
	 * id : 编号<br/>
	 * authDataSql : 权限控制SQL<br/>
	 * wadduserid : 添加用户编号<br/>
	 * isdataauth : 是否需要权限检查,默认需要<br/> 
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public boolean updateBuyAreaBy(Map map) throws Exception;
	
	/**
	 * 采购区域统计<br/>
	 * Map中参数: <br/>
	 * thisid : 本级编码 <br/>
	 * adduserid : 建档人部门编号 <br/>
	 * isdataauth : 是否需要权限检查，默认不需要<br/> 
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-19
	 */
	public int getBuyAreaCountBy(Map map) throws Exception;
	/**
	 * 根据采购区域编号禁用采购区域
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-2
	 */
	public Map closeBuyArea(String id) throws Exception;
	
	/**
	 * 判断采购区域本级名称是否重复
	 * @param thisname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public boolean isRepeatBuyAreaThisname(String thisname)throws Exception;
	
	//--------------------------------------------------------------//
	//	供应商分类	//
	//--------------------------------------------------------------//
	
	/**
	 * 添加供应商分类
	 * @param sort
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public boolean addBuySupplierSort(BuySupplierSort sort) throws Exception;
	
	/**
	 * 修改供应商分类
	 * @param sort
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public Map updateBuySupplierSort(BuySupplierSort sort) throws Exception;
	
	/**
	 * 获取供应商分类列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public List getBuySupplierSortList() throws Exception;
	/**
	 * 根据pageMap参数，获取供应商分类信息<br/>
	 *  pageMap中参数<br/>
	 *  parentAllChildren：获取节点及其所有子结点 <br/>	  
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-22
	 */
	public List getBuySupplierSortListByMap(Map map) throws Exception;
	
	/**
	 * 通过供应商分类编号获取供应商分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public BuySupplierSort getBuySupplierSortDetail(String id) throws Exception;
	
	/**
	 * 删除供应商分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date Apr 11, 2013
	 */
	public boolean deleteBuySupplierSort(String id) throws Exception;
	
	
	/**
	 * 更新供应商分类<br/>
	 * map中参数:<br/>
	 * 可更新参数<br/>
	 * newid : 新编号<br/>
	 * state : 状态4新增3暂存2保存1启用0禁用<br/>
	 * modifyuserid : 最后修改人编号<br/>
	 * modifyusername : 最后修改人<br/>
	 * modifytime : 最后修改时间<br/>
	 * openuserid : 启用人编号<br/>
	 * openusername : 启用人<br/>
	 * opentime : 启用时间<br/>
	 * closeuserid : 禁用人 编号<br/>
	 * closeusername : 禁用人<br/>
	 * closetime : 禁用时间<br/>
	 * 条件参数<br/>
	 * id : 编号<br/>
	 * authDataSql : 权限控制SQL<br/>
	 * wadduserid : 添加用户编号<br/>
	 * isdataauth : 是否需要权限检查<br/> 
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public boolean updateBuySupplierSortBy(Map map) throws Exception;
	/**
	 * 供应商分类统计<br/>
	 * id: 编号<br/>
	 * thisid : 本级编码 <br/>
	 * adduserid : 建档人部门编号 <br/>
	 * authDataSql : 数据权限sql <br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-20
	 */
	public int getBuySupplierSortCountBy(Map map) throws Exception;
	/**
	 * 根据采购分类编号禁用采购分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-3
	 */
	public Map closeBuySuppplierSort(String id) throws Exception;
	
	/**
	 * 判断供应商分类本级名称是否重复
	 * @param thisname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public boolean isRepeatSuppplierSortThisname(String thisname)throws Exception;
	//--------------------------------------------------------------//
	//	供应商对应分类	//
	//--------------------------------------------------------------//
	
	/**
	 * 添加供应商对应分类
	 * @param detailsort
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean addBuySupplierDetailSort(BuySupplierDetailsort detailsort) throws Exception;
	/**
	 * 修改供应商对应分类
	 * @param detailsort
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean updateBuySupplierDetailSort(BuySupplierDetailsort detailsort) throws Exception;
	/**
	 * 删除供应商对应分类
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean deleteBuySupplierDetailSort(String id) throws Exception;
	/**
	 * 根据供应商编号删除供应商对应分类
	 * @param supplierid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean deleteBuySupplierDetailSortBySupplier(String supplierid)throws Exception;

	/**
	 * 根据供应商编号显示供应商对应分类列表<br/>
	 * map中参数：<br/>
	 * supplierid : 供应商档案编号<br/>
	 * state : 供应商分类中的state,状态4新增3暂存2保存1启用0禁用<br/>
	 * statearrs : 供应商分类中的state数组列表字符串，格式：1,2,3<br/>
	 * orderby : 查询排序<br/>
	 * ordersort : 排序方式：desc降序，asc升序<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public List showBuySupplierDetailSortListBy(Map map) throws Exception;
	

	//--------------------------------------------------------------//
	//	供应商档案	//
	//--------------------------------------------------------------//
	/**
	 * 显示供应商档案分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public PageData showBuySupplierPageList(PageMap pageMap) throws Exception;
	/**
	 * 导入供应商档案
	 * @param buySupplier
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map addImportBuySupplier(BuySupplier buySupplier) throws Exception;
	
	/**
	 * 导入供应商所属分类
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 4, 2013
	 */
	public Map addDRSupplierDS(List<BuySupplierDetailsort> list)throws Exception;

	/**
	 * 添加供应商档案
	 * @param buySupplier
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean addBuySupplier(BuySupplier buySupplier) throws Exception;
	/**
	 * 更新供应商档案
	 * @param buySupplier
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean updateBuySupplier(BuySupplier buySupplier) throws Exception;
	/**
	 * 删除供应商档案
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean deleteBuySupplier(String id) throws Exception;
	
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
	public boolean deleteBuySupplierBy(Map map)throws Exception;
	
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
	public int getBuySupplierCountBy(Map map) throws Exception;
	
	/**
	 * 获取供应商档案
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-25
	 */
	public BuySupplier showBuySupplier(String id) throws Exception;
	/**
	 * 获取供应商列表，有字段、数据权限判断<br/>
	 * map中参数：<br/>
	 * id:编号<br/>
	 * idarrs : 编号组字符串，格式：1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-27
	 */
	public List getBuySupplierListBy(Map map) throws Exception;
	
	/**
	 * 获取供应商，有字段、数据权限判断<br/>
	 * map中参数：<br/>
	 * id:编号<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-27
	 */
	public BuySupplier getBuySupplierBy(Map map) throws Exception;
	/**
	 * 获取供应商，有字段、数据权限判断<br/>
	 * map中参数：<br/>
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-27
	 */
	public BuySupplier getBuySupplierByAuth(String id) throws Exception;
	
	/**
	 * 更新供应商档案,有数据权限判断<br/>
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
	 * wstate :状态4新增3暂存2保存1启用0禁用<br/>
	 * statearrs : 状态组字符串<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-23
	 */
	public boolean updateBuySupplierBy(Map map) throws Exception;
	/**
	 * 获取供应商编号和状态列表，有数据权限判断<br/>
	 * map中参数：<br/>
	 * authDataSql:数据权限控制<br/>
	 * idarrs : 编号组字符串，格式：1,2,3<br/>
	 * state :状态4新增3暂存2保存1启用0禁用<br/>
	 * statearrs : 状态组字符串<br/>
	 * isdataauth : 是否需要数据权限，1需要，0不需要<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-27
	 */
	public List getBuySupplierStateListBy(Map map) throws Exception;
	/**
	 * 根据id值获取供应商列表
	 * id查询供应商编码 供应商助记码
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 3, 2013
	 */
	public PageData getSupplierSelectListData(PageMap pageMap) throws Exception;
	
	/**
	 * 快捷导入供应商档案
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 28, 2013
	 */
	public Map addShortcutSupplierExcel(List<BuySupplier> list)throws Exception;
    /**
     * Excel导入采购区域
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-18
     */
    public Map addDRBuyAreaExcel(List<BuyArea> list)throws Exception;
    /**
     * Excel导入供应商分类
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-18
     */
    public Map addDRSupplierSortExcel(List<BuySupplierSort> list)throws Exception;

	/**
	 * 获取在更新日期之后的供应商数据
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 31, 2016
	 */
	public List<Map> getSupplierListForMecshop() throws Exception;

	/**
	 * 获取供应商列表
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public PageData getSupplierListForPact(PageMap pageMap) throws Exception;

	/**
	 * 添加供应商品牌结算方式
	 * @param buySupplierBrandSettletype
	 * @return java.lang.Boolean
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public Boolean addSupplierBrandSettletype(BuySupplierBrandSettletype buySupplierBrandSettletype) throws Exception;

	/**
	 * 获取供应商关联的品牌结算方式
	 * @param supplierid
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public List getSupplierBrandSettletypeList(String supplierid);

	/**
	 * 删除供应商品牌结算方式
	 * @param idstr
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Oct 25, 2017
	 */
	public Map deleteSupplierBrandSettletypes(String idstr) throws Exception;

	/**
	 * 修改供应商品牌结算方式
	 * @param buySupplierBrandSettletype
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-03-30
	 */
	public boolean editSupplierBrandSettletype(BuySupplierBrandSettletype buySupplierBrandSettletype)throws Exception;

	/**
	 * 导入供应商品牌结算方式
	 * @param list
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Nov 06, 2017
	 */
	public List<Map<String, Object>> importSupplierBrandSettletype(List<Map<String, Object>> list) throws Exception;

	/**
	 * 获取全部供应商
	 * @param
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 22, 2017
	 */
	public List getAllBuySupplierList();
}

