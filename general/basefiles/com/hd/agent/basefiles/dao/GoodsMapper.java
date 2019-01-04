/**
 * @(#)GoodsMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface GoodsMapper {

	/*----------------------------------计量单位-----------------------------------------------*/
	
	/**
	 * 获取计量单位列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public List getMeteringUnitList(PageMap pageMap);

    /**
     * 根据名称获取信息
     * @param name
     * @return
     */
    public MeteringUnit getMeteringUnitByName(@Param("name")String name);
	
	/**
	 * 获取计量单位数量 
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public int getMeteringUnitCount(PageMap pageMap);

	/**
	 * 新增计量单位
	 * @param meteringUnit
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public int addMeteringUnit(MeteringUnit meteringUnit);
	
	/**
	 * 获取计量单位信息 
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public MeteringUnit showMeteringUnitInfo(@Param("id")String id);

	/**
	 * 判断id是否重复
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public String isRepeatMUID(@Param("id")String id);
	
	/**
	 * 判断name是否重复
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public String isRepeatMUName(@Param("name")String name);
	
	/**
	 * 修改计量单位
	 * @param meteringUnit
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public int editMeteringUnit(MeteringUnit meteringUnit);
	
	/**
	 * 删除计量单位
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public int deleteMeteringUnit(@Param("id")String id);
	
	/**
	 * 启用计量单位
	 * @param paramMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public int enableMeteringUnit(Map paramMap);
	
	/**
	 * 禁用计量单位
	 * @param paramMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public int disableMeteringUnit(Map paramMap);
	
	public List getMUListForCombobox();
	
	/*----------------------------------商品品牌-----------------------------------------------*/
	
	/**
	 * 获取商品牌列表combobox
	 */
	public List getBrandListForCombobox(PageMap pageMap);
	
	/**
	 * 获取商品牌列表combobox数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 24, 2013
	 */
	public int getBrandListForComboboxCount(PageMap pageMap);

	/**
	 * 获取商品品牌列表
	 * @param pageMap
	 * @return
	 * @author zhang_honghui
	 * @date 2016-08-18
	 */
	public List getBrandListPage(PageMap pageMap);

	/**
	 * 列表数量 
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public int getBrandListCount(PageMap pageMap);
	
	/**
	 * 根据编号判断该商品品牌是否重复
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public int isRepeatBrandById(@Param("id")String id);
	
	/**
	 * 判断名称是否重复
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public int isRepeatBrandName(@Param("name")String name);
	
	/**
	 * 新增商品品牌
	 * @param brand
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public int addBrand(Brand brand);
	
	/**
	 * 修改商品品牌
	 * @param brand
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public int editBrand(Brand brand);
	/**
	 * 修改品牌金税相关的信息
	 * @param brand
	 * @return int
	 * @throws
	 * @author zhanghonghui
	 * @date Aug 08, 2017
	 */
	public int updateBrandInfoForJS(Brand brand);
	
	/**
	 * 删除商品品牌，判断是否被引用
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public int deleteBrand(@Param("id")String id);
	
	/**
	 * 获取商品品牌详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public Brand getBrandInfo(@Param("id")String id);

	/**
	 * 根据品牌名称获取品牌信息
	 * @param name
	 * @return
	 */
	public Brand getBrandInfoByName(@Param("name")String name);
	
	/**
	 * 启用商品品牌
	 * @param paramMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public int enableBrand(Map paramMap);
	
	/**
	 * 禁用商品品牌
	 * @param paramMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public int disableBrand(Map paramMap);
	
	/**
	 * 根据所属部门获取商品品牌列表数据
	 * @param deptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public List getBrandListByDeptid(@Param("deptid")String deptid);
	
	/**
	 * 根据品牌编码字符串集合获取品牌数据列表
	 * @param map
	 * @return
	 * @author panxiaoxiao
	 * @date Nov 4, 2014
	 */
	public List getBrandListByBrandids(Map map);
	
	/**
	 * 根据所属供应商获取商品品牌列表数据
	 * @param supplierid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public List getBrandListBySupplierid(@Param("supplierid")String supplierid);
	
	/**
	 * 根据所属供应商编码集获取商品品牌列表数据
	 * @param supplieridArr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2015年5月29日
	 */
	public List getBrandListBySupplierids(@Param("supplieridArr")String[] supplieridArr);
	/**
	 * 获取商品品牌列表
	 * @return
	 * @author wanghongteng
	 * @date 2015年10月5日
	 */
	public List getBrandList();
	/**
	 * 根据map中参数获取商品品牌列表<br/>
	 * map中参数：<br/>
	 * idarrs: id字符串<br/>
	 * state: 1启用0禁用<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui
	 * @date 2016-08-11
	 */
	public List getBrandListByMap(Map map);
	
	/*------------------------开始 -- 品牌部门修改后更新相关单据-------------------*/
	public int editBrandChangePushBalance(Map map);
	
	public int editSalesInvoiceCauseOfSupplier(Map map);

	public int editSalesInvoiceBillCauseOfSupplier(Map map);

	//修改商品档案的对应所属部门
	public int editBrandDeptChangeGoods(Map map);
	/*-----------------结束 -- 品牌部门修改后更新相关单据-------------------*/
	
	/*----------------------------------商品分类-----------------------------------------------*/
	
	/**
	 * 根据pageMap中的条件获取商品分类列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public List getWaresClassListPage(PageMap pageMap);
	
	/**
	 * 根据pageMap中的条件获取商品分类数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public int getWaresClassListCount(PageMap pageMap);
	
	/**
	 * 获取树状商品分类列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public List<WaresClass> getWaresClassTreeList();
	
	/**
	 * 获取树状商品分类启用列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public List<WaresClass> getWaresClassTreeOpenList();
	
	/**
	 * 编码是否重复
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public String isRepeatWCID(@Param("id")String id);
	
	/**
	 * 本级名称是否重复
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public int isRepeatThisName(@Param("thisname")String thisname);
	
	/**
	 * 新增商品分类
	 * @param waresClass
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public int addWaresClass(WaresClass waresClass);
	
	/**
	 * 获取保存、暂存以外的商品分类列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public List getWaresClassByState();
	
	/**
	 * 将商品编号作为上级分类编号查询上级分类编号是否存在，判断该商品分类是否为末及标志
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public String isExistPidJudgeLeaf(@Param("id")String id);
	
	/**
	 * 获取商品分类详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public WaresClass getWaresClassInfo(@Param("id")String id);
	
	/**
	 * 修改商品分类
	 * @param waresClass
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public int editWaresClass(WaresClass waresClass);
	
	/**
	 * 删除商品分类
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-15
	 */
	public int deleteWaresClass(@Param("id")String id);
	
	/**
	 * 启用商品分类
	 * @param paramMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-15
	 */
	public int enableWaresClass(Map paramMap);
	
	/**
	 * 禁用商品分类
	 * @param waresClass
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-15
	 */
	public int disableWaresClass(WaresClass waresClass);
	
	/**
	 * 编码作为上级编码获取下级所有的商品分类列表(包括上级)
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-15
	 */
	public List<WaresClass> getWaresClassListByPid(@Param("id")String id);
	
	/**
	 * 编码作为上级编码获取下级所有的商品分类列表
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 3, 2013
	 */
	public List<WaresClass> getWaresClassChildListByPid(@Param("id")String id);
	
	/**
	 * 批量修改商品分类
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date May 10, 2014
	 */
	public int editWaresClassBatch(List<WaresClass> list);
	
	/**
	 * 获取名称为空，上级编码不为空的商品分类
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 13, 2014
	 */
	public List<WaresClass> getWCWithoutName();
	
	/*----------------------------------商品档案-----------------------------------------------*/
	
	public List getGoodsListForCombobox(@Param("brandid")String brandid);
	/**
	 * 根据pageMap条件获取商品档案列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public List getGoodsInfoListPage(PageMap pageMap);
	
	/**
	 * 获取商品档案数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public int getGoodsInfoListCount(PageMap pageMap);
	
	/**
	 * 获取商品档案详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public GoodsInfo getGoodsInfo(@Param("id")String id);
	/**
	 * 获取商品档案的基础信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 17, 2013
	 */
	public GoodsInfo getBaseGoodsInfo(@Param("id")String id);
	
	/**
	 * 获取商品档案的基础信息（去缓存）
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 5, 2014
	 */
	public GoodsInfo getBaseGoodsInfoNoCache(@Param("id")String id);
	/**
	 * 批量启用商品档案
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public int enableGoodsInfos(Map map);
	
	/**
	 * 批量禁用商品档案
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public int disableGoodsInfos(Map map);
	
	/**
	 * 批量删除商品档案
	 * @param idsArr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public int deleteGoodsInfos(@Param("idsArr")String[] idsArr);
	
	/**
	 * 新增商品档案
	 * @param goodsInfo
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public int addGoodsInfo(GoodsInfo goodsInfo);
	
	/**
	 * 判断商品ID是否重复或商品是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public int isRepeatGoodsInfoID(@Param("id")String id);
	
	/**
	 * 判断商品名称是否重复
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public int isRepeatGoodsInfoName(@Param("name")String name);
	
	/**
	 * 判断条形码是否重复
	 * @param barcode
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int isRepeatGoodsInfoBarcode(@Param("barcode")String barcode);
	
	/**
	 * 判断箱装条形码是否重复
	 * @param boxbarcode
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int isRepeatGoodsInfoBoxbarcode(@Param("boxbarcode")String boxbarcode);
	
	/**
	 * 判断货号是否重复
	 * @param itemno
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int isRepeatGoodsInfoItemno(@Param("itemno")String itemno);
	/**
	 * 商品选择控件 根据id获取商品列表
	 * id 查询商品编码 商品名称 条形码 助记码等
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 2, 2013
	 */
	public List getGoodsSelectListData(PageMap pageMap);
	/**
	 * 商品选择控件 根据id获取商品列表数量
	 * id 查询商品编码 商品名称 条形码 助记码等
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 2, 2013
	 */
	public int getGoodsSelectListDataCount(PageMap pageMap);
	/**
	 * 商品选择控件 根据id获取商品列表
	 * id 查询商品编码 商品名称 条形码 助记码等
	 * @param pageMap
	 * @return
	 * @author chenwei
	 * @date Aug 2, 2013
	 */
	public List getGoodsSelectListDataSimple(PageMap pageMap);
	/**
	 * 根据品牌获取商品列表
	 * @param brand
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 28, 2014
	 */
	public List getGoodsListByBrand(String brand);
	/**
	 * 根据分类获取商品列表
	 * @param defaultsort
	 * @return
	 * @author wanghongteng
	 * @date Jun 28, 2014
	 */
	public List getGoodsListByDefaultSort(String defaultsort);
	/**
	 * 根据品牌和供应商获取商品列表
	 * @param brand
	 * @param supplierid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 28, 2014
	 */
	public List getGoodsListByBrandWithSupplierid(@Param("brand")String brand,@Param("supplierid")String supplierid);
	
	
	
	/**
	 * 根据pageMap，品牌和分类获取商品档案
	 * @param pageMap
	 * @return
	 * @author wanghongteng
	 * @date 2015-12-1
	 */
	public List getGoodsInfoListByBrandAndSort(PageMap pageMap);
	
	/**
	 * 根据pageMap，品牌和分类获取商品档案数量
	 * @param pageMap 
	 * @return
	 * @author wanghongteng
	 * @date 2015-12-1
	 */
	public int getGoodsInfoListByBrandAndSortCount(PageMap pageMap);
	
	/*---------------------开始---商品修改后更新相关单据-------------------------------------*/
	public int editGoodsInfoChangeCustomerPriceCtcboxprice(Map map);

	public int editGoodsInfoChangeDemandDetail(Map map);
	public int editGoodsInfoChangeDispatchbillDetail(Map map);
	public int editGoodsInfoChangeOrderCarDetail(Map map);
	public int editGoodsInfoChangeOrderDetail(Map map);
	public int editGoodsInfoChangeReceiptDetail(Map map);
	public int editGoodsInfoChangeRejectbillDetail(Map map);
	public int editGoodsInfoChangeSaleoutDetail(Map map);
	public int editGoodsInfoChangeSalerejectEnterDetail(Map map);
	public int editGoodsInfoChangeInvoiceDetail(Map map);
	public int editGoodsInfoChangeInvoiceBillDetail(Map map);
	public int editStorageDayCauseOfGoodsChange(Map map);
	public int editStorageMonthCauseOfGoodsChange(Map map);
	public int editStorageRealDayCauseOfGoodsChange(Map map);
	public int editStorageRealMonthCauseOfGoodsChange(Map map);
	
	/**
	 * 商品修改更新采购单据
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 17, 2015
	 */
	public int editGoodsInfoPurchaseArrivalOrder(Map map);
	public int editGoodsInfoPurchaseEnter(Map map);
	public int editGoodsInfoPurchaserejectOut(Map map);

	/**
	 * 获取天数范围内修改过的商品数据列表
	 * @param map
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-06-17
	 */
	public List<GoodsInfo> getChangeDaysUpdateGoodsList(Map map);

	public int editStorageDataCauseOfGoodsChange(GoodsInfo goodsInfo);
	/*---------------------结束---商品修改后更新相关单据-------------------------------------*/
	/*-------------------------商品档案(辅计量单位)--------------------------------------*/
	
	/**
	 * 根据商品编号获取辅计量单位列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public List getMeteringUnitInfoList(PageMap pageMap);
	
	/**
	 * 根据编号删除辅计量单位列表 
	 * @param idsArr
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int deleteMeteringUnitInfos(@Param("idsArr")String[] idsArr);
	
	/**
	 * 批量新增辅计量单位
	 * @param meteringUnitListMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int addMeteringUnitInfos(Map meteringUnitListMap);
	
	/**
	 * 修改辅计量单位
	 * @param goodsInfo_MteringUnitInfo
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int editMeteringUnitInfo(GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo);
	
	/**
	 * 根据商品编号删除辅计量单位
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int deleteMereringUnitInfoByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 判断辅计量单位是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int isExistMeteringUnitInfo(@Param("id")int id);
	
	/**
	 * 根据商品编码辅单位编码判断是否已存在
	 * @param goodsid
	 * @param meteringunitid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 5, 2014
	 */
	public int isExistMUInfo(@Param("goodsid")String goodsid,@Param("meteringunitid")String meteringunitid);
	
	/**
	 * 根据商品编码删除默认计量单位为是的辅计量单位
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public int deleteMUInfoByGoodsidAndIsdefault(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码、默认辅计量单位为是获取辅单位详情
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public GoodsInfo_MteringUnitInfo getMUInfoByGoodsIdAndIsdefault(@Param("goodsid")String goodsid);
	
	public GoodsInfo_MteringUnitInfo getGoodsInfoMU(@Param("goodsid")String goodsid,@Param("meteringunitid")String meteringunitid);
	
	/*-------------------------商品档案(价格套)--------------------------------------*/
	
	
	/**
	 * 根据商品编号获取价格套列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public List getPriceInfoList(PageMap pageMap);
	
	/**
	 * 获取所有价格套信息（不包括taxprice为null和0的数据）
	 * @return
	 * @author zhengziyong 
	 * @date Jul 24, 2013
	 */
	public List getAllPriceInfoList(Map map);

    /**
     * 获取所有价格套信息数量
     * @param map
     * @return
     */
    public int getAllPriceInfoListCount(Map map);
	
	/**
	 * 根据商品编号和价格套编码获取价格套信息
	 * @param goodsid
	 * @param pricecode
	 * @return
	 * @author zhengziyong 
	 * @date May 13, 2013
	 */
	public GoodsInfo_PriceInfo getPriceInfoByGoodsAndCode(@Param("goodsid")String goodsid, @Param("pricecode")String pricecode);
	
	/**
	 * 根据编号批量删除价格套
	 * @param idsArr
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int deletePriceInfos(@Param("idsArr")String[] idsArr);
	
	/**
	 * 批量新增价格套
	 * @param priceInfoMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int addPriceInfos(Map priceInfoMap);
	
	public int addPriceInfo(GoodsInfo_PriceInfo goodsInfo_PriceInfo);
	
	/**
	 * 修改价格套
	 * @param goodsInfo_PriceInfo
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int editPriceInfo(GoodsInfo_PriceInfo goodsInfo_PriceInfo);
	
	/**
	 * 根据商品编号删除价格套
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int deletePriceInfoByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 判断价格套是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int isExistPriceInfo(@Param("id")int id);
	
	public int isExistPriceInfoByGoodsidAndCode(@Param("goodsid")String goodsid,@Param("code")String code);
	
	/**
	 * 根据价格套编码获取对应价格套列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 6, 2013
	 */
	public List getPriceListByCode(PageMap pageMap);
	
	/**
	 * 根据价格套编码获取对应价格套列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date May 6, 2013
	 */
	public int getPriceListByCodeCount(PageMap pageMap);
	
	/**
	 * 根据商品编码获取价格套管理列表数据
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public List getPriceListByGoodsid(String goodsid);
	
	/**
	 * 根据商品编码、价格套编码判断是否存在该价格套数据
	 * @param goodsid
	 * @param code
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public GoodsInfo_PriceInfo getPriceDataByGoodsidAndCode(@Param("goodsid")String goodsid,@Param("code")String code);
	
	/**
	 * 根据商品编码、价格套编码删除价格套管理数据
	 * @param goodsid
	 * @param code
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public int deletePriceDataByGoodsidAndCode(@Param("goodsid")String goodsid,@Param("code")String code);
	/**
	 * 根据价格套编码获取对应价格套列表
	 * @param code
	 * @return
	 * @author wanghongteng 
	 * @date 10 13, 2015
	 */
	public List<GoodsInfo_PriceInfo> getPriceListByTypeCode(String code);
	/*-------------------------商品档案(对应仓库)--------------------------------------*/
	/**
	 * 根据商品编号获取对应仓库列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public List getStorageInfoList(PageMap pageMap);
	
	/**
	 * 根据编号删除对应仓库
	 * @param idsArr
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int deleteStorageInfos(@Param("idsArr")String[] idsArr);
	
	/**
	 * 批量新增对应仓库
	 * @param storageInfoListMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int addStorageInfos(Map storageInfoListMap);
	
	/**
	 * 批量修改对应仓库
	 * @param goodsInfo_StorageInfo
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int editStorageInfo(GoodsInfo_StorageInfo goodsInfo_StorageInfo);
	
	/**
	 * 根据商品编号删除对应仓库
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int deleteStorageInfoByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 判断辅对应仓库是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int isExistStorageInfo(@Param("id")int id);
	
	public int isExistStorageInfoByGoodsAndStorageid(@Param("goodsid")String goodsid,@Param("storageid")String storageid);
	/**
	 * 根据商品编码和仓库编号获取商品对应仓库信息
	 * @param goodsid
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date Jun 19, 2013
	 */
	public GoodsInfo_StorageInfo getGoodsInfoStorageInfo(@Param("goodsid")String goodsid,@Param("storageid")String storageid);
	
	/*-------------------------商品档案(对应库位)--------------------------------------*/
	public List getGoodsStorageLocationList(PageMap pageMap);
	
	public int getStorageLocationCountByGoodsId(String goodsid);
	
	/**
	 * 根据商品编码获取对应库位数据列表
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 14, 2014
	 */
	public List getStorageLocationListByGoodsid(String goodsid);
	
	public int deleteGoodsStorageLocation(@Param("idsArr")String[] idsArr);
	
	public int addGoodsStorageLocation(Map SLListMap);
	
	public int editGoodsStorageLocation(GoodsStorageLocation goodsStorageLocation);
	
	public int deleteGoodsStorageLocationByGoodsId(String goodsid);
	
	public int isExistGoodsStorageLocation(int id);
	
	public int isExistGoodsSL(@Param("goodsid")String goodsid,@Param("storagelocationid")String storagelocationid);
	
	public GoodsStorageLocation getGoodsStorageLocation(@Param("goodsid")String goodsid,@Param("storagelocationid")String storagelocationid);
	
	/*-------------------------商品档案(对应分类)--------------------------------------*/
	
	/**
	 * 根据商品编码获取对应分类列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public List getWaresClassInfoList(PageMap pageMap);
	
	/**
	 * 根据编号批量删除对应分类
	 * @param idsArr
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int deleteWaresClassInfos(@Param("idsArr")String[] idsArr);
	
	/**
	 * 批量新增对应分类
	 * @param waresClassInfoListMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int addWaresClassInfos(Map waresClassInfoListMap);
	
	/**
	 * 修改对应分类
	 * @param goodsInfo_WaresClassInfo
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int editWaresClassInfo(GoodsInfo_WaresClassInfo goodsInfo_WaresClassInfo);
	
	/**
	 * 根据商品编号删除对应分类
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int deleteWaresClassInfoByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 判断对应分类是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public int isExistWaresClassInfo(@Param("id")int id);
	
	public int ixExistWCInfo(@Param("goodsid")String goodsid,@Param("waresclass")String waresclass);
	
	/**
	 * 根据商品编码和商品分类编码获取商品分类
	 * @param goodsid
	 * @param waresclass
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 5, 2014
	 */
	public GoodsInfo_WaresClassInfo getGoodsWCInfo(@Param("goodsid")String goodsid,@Param("waresclass")String waresclass);
	
	/**
	 * 修改商品
	 * @param goodsInfo
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public int editGoodsInfo(GoodsInfo goodsInfo);
	
	/**
	 * 修改商品（批量）
	 * @param goodsInfo
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 3, 2013
	 */
	public int editMoreGoodsInfo(GoodsInfo goodsInfo);
	
	/**
	 * 修改商品档案图片信息
	 * @param goodsInfo
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	public int editGoodsImageInfo(GoodsInfo goodsInfo);
	
	/**
	 * 根据商品编号获取计量单位列表数量
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public int getMUCountByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编号获取价格套列表数量
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public int getPriceCountByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编号获取对应仓库列表数量
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public int getStorageCountByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码获取对应分类列表数据
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 14, 2014
	 */
	public List getWCListByGoodsid(@Param("goodsid")String goodsid);
	/**
	 * 根据商品编号获取对应分类列表数量
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public int getWCCountByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品档案编号获取辅助计量为列表
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 10, 2013
	 */
	public List<GoodsInfo_MteringUnitInfo> getMUListByGoodsId(@Param("goodsid")String goodsid);
	
	/**
	 * 根据条件获取已启用的商品信息列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 20, 2013
	 */
	public List<GoodsInfo> getGoodsInfoByCondition(PageMap pageMap);
	
	/**
	 * 获取所有商品档案列表(提供给手机端) 同步数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Jun 21, 2013
	 */
	public List<PGoodsInfo> getAllGoodsInfoPhone(Map map);

    /**
     * 获取所有商品档案列表(提供给手机端)数量
     * @param map
     * @return
     */
    public int getAllGoodsInfoPhoneCount(Map map);
	/**
	 * 在线获取所有商品档案列表(提供给手机端)
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年9月1日
	 */
	public List<PGoodsInfo> getGoodsInfoListFroPhone(Map map);

    /**
     * 在线获取所有商品档案数量(提供给手机端)
     * @param map
     * @return
     */
    public int getGoodsInfoListFroPhoneCount(Map map);
	/**
	 * 获取客户已交易商品列表(提供给手机端)
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年8月31日
	 */
	public List<PGoodsInfo> getCustomerGoodsInfoPhone(Map map);

    /**
     * 获取客户已交易商品列表数量(提供给手机端)
     * @param map
     * @return
     */
    public int getCustomerGoodsInfoPhoneCount(Map map);

	/**
	 * 获取客户未交易商品列表(提供给手机端)
	 * @param map
	 * @return
	 * @author chenwei 
	 * @date 2015年8月31日
	 */
	public List<PGoodsInfo> getCustomerNoSalesGoodsInfoPhone(Map map);

    /**
     * 获取客户未交易商品列表数量(提供给手机端)
     * @param map
     * @return
     */
    public int getCustomerNoSalesGoodsInfoPhoneCount(Map map);
	/**
	 * 根据品牌编码获取商品列表
	 * @param brands
	 * @return
	 * @author chenwei 
	 * @date Apr 16, 2014
	 */
	public List<PGoodsInfo> getGoodsInfoPhoneByBrands(@Param("brands")String brands);
	/**
	 * 获取品牌业务员商品档案列表（提供给手机端）
	 * @param perid
	 * @return
	 * @author zhengziyong 
	 * @date Nov 30, 2013
	 */
	public List<PGoodsInfo> getBrandSalerGoodsPhone(String perid);
	/**
	 * 获取车销人员商品档案列表（提供给手机端）
	 * @param perid
	 * @return
	 * @author chenwei 
	 * @date Mar 14, 2014
	 */
	public List<PGoodsInfo> getStorageGoodsPhone(String perid);
	/**
	 * 获取所有品牌数据列表(提供给手机端)
	 * @return
	 * @author zhengziyong 
	 * @date Aug 14, 2013
	 */
	public List<Brand> getAllBrandPhone(Map map);
	
	/**
	 * 获取品牌业务员对应品牌信息
	 * @param perid
	 * @return
	 * @author zhengziyong 
	 * @date Nov 30, 2013
	 */
	public List<Brand> getBrandSalerBrandPhone(String perid);
	
	/**
	 * 回写商品档案数据
	 * @param goodsInfo
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-15
	 */
	public int updateGoodsInfoWriteBack(GoodsInfo goodsInfo);
	/**
	 * 根据商品编码、默认辅计量单位为是获取对应仓库详情
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public GoodsInfo_StorageInfo getStorageByGoodsidAndIsdefault(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码获取对应仓库列表数据
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 14, 2014
	 */
	public List getStorageListByGoodsid(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码、默认辅计量单位为是删除对应仓库
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public int deleteStorageByGoodsidAndIsdefault(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码、默认辅计量单位为是获取对应分类详情
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public GoodsInfo_WaresClassInfo getWCByGoodsidAndIsdefault(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码、默认辅计量单位为是删除对应分类
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public int deleteWCByGoodsidAndIsdefault(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码、默认辅计量单位为是获取对应库位详情
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public GoodsStorageLocation getSLByGoodsidAndIsdefault(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码、默认辅计量单位为是删除对应库位
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public int deleteSLByGoodsidAndIsdefault(@Param("goodsid")String goodsid);
	
	/**
	 * 根据商品编码获取商品库存平均单价
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 13, 2013
	 */
	public BigDecimal getGoodsStockavgamountById(@Param("id")String id);
	/**
	 * 添加商品成本价变更记录
	 * @param goodsid
	 * @param costprice
	 * @return
	 * @author chenwei 
	 * @date Sep 16, 2013
	 */
	public int addGoodsCostpriceChangeLog(@Param("goodsid")String goodsid,@Param("costprice")BigDecimal costprice,@Param("remark") String remark,@Param("billid") String billid);
	/**
	 * 获取商品成本价变化记录表
	 * @return
	 * @author chenwei 
	 * @date Sep 16, 2013
	 */
	public List getGoodsCostpriceList();
	/**
	 * 成本价相关数据更新后，修改记录状态
	 * @return
	 * @author chenwei 
	 * @date Sep 16, 2013
	 */
	public int updateGoodsCostpriceState();

	public List retunMUIdByName(String name);
	
	public List retunBrandIdByName(String name);
	
	/**
	 * 根据本级名称获取商品分类列表数据
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 3, 2014
	 */
	public List retunWCIdBythisname(String thisname);
	
	/**
	 * 根据全名获取商品分类列表数据
	 * @param name
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 3, 2014
	 */
	public List returnWCListByName(String name);
	
	public Map addShortcutGoodsExcel(List<GoodsInfo> list) throws Exception;
	
	/**
	 * 根据所属部门编码获取品牌数据列表(包含父级部门)
	 * @param deptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 4, 2014
	 */
	public List getBrandListWithParentByDeptid(@Param("deptid")String deptid);
	
	/**
	 * 根据所属部门编码获取品牌数据列表(包含父级部门)分页
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 4, 2014
	 */
	public List getBrandListWithParentByDeptidPage(PageMap pageMap);
	
	/**
	 * 根据所属部门编码获取品牌数据列表数量(包含父级部门)分页
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 4, 2014
	 */
	public int getBrandListWithParentByDeptidCount(PageMap pageMap);
	
	public Map getTaxPriceByGoodsidAndPriceCode(@Param("goodsid")String goodsid,@Param("pricesort")String pricesort)throws Exception;

	/**
	 * 基础档案更新后 同时更新相关单据的信息(客户档案修改更新单据的销售区域、销售部门、客户业务员、内勤，其中销售核销、销售开票、冲差不需更新内勤)
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-12-28
	 */
	public int updateBillByCustomerChange();

	/**
	 * 根据助记符获取数据列表中第一行商品
	 * @param spell
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 20, 2013
	 */
	public GoodsInfo getGoodsInfoBySpellLimitOne(String spell);
	
	/**
	 * 根据条形码获取数据列表中的第一行商品
	 * @param barcode
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 10, 2014
	 */
	public GoodsInfo getGoodsInfoByBarcodeLimitOne(String barcode);
	
	/**
	 * 修改商品价格套名称
	 * @param sysCode
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 25, 2014
	 */
	public int updateGoodsPriceListByCode(SysCode sysCode);
    /**
     * 添加商品成本差额记录
     * @param billtype      单据类型1进货单2退货单
     * @param billid        单据编号
     * @param detailid      单据明细编号
     * @param storageid     仓库编号
     * @param goodsid       商品编号
     * @param amount        差额
     * @return
     */
    public int addGoodsCostpriceShare(@Param("billtype")String billtype,@Param("billid")String billid,@Param("detailid")String detailid,@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("amount")BigDecimal amount,@Param("remark")String remark);

    /**
     * 删除商品成本差额记录
     * @param billtype      单据类型1进货单2退货单
     * @param billid        单据编号
     * @param detailid      单据明细编号
     * @param goodsid       商品编号
     * @return
     */
    public int delteCostDiffAmountShare(@Param("billtype")String billtype,@Param("billid")String billid,@Param("detailid")String detailid,@Param("goodsid")String goodsid);

    /**
     * 根据商品编号 获取成本差额金额
     * @param storageid
     * @param goodsid
     * @return
     */
    public BigDecimal getCostDiffAmountByGoodsid(@Param("storageid")String storageid,@Param("goodsid")String goodsid);
    /**
     * 根据商品编号和关联单据号 获取成本差额金额
     * @param goodsid
     * @return
     */
    public BigDecimal getCostDiffAmountByGoodsidAndRelate(@Param("goodsid")String goodsid,@Param("relatebillid")String relatebillid,@Param("releatedetailid")String releatedetailid);

    /**
     * 根据商品编号 关闭成本差额
     * @param goodsid
     * @return
     */
    public int closeCostDiffAmountByGoodsid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("relatebillid")String relatebillid,@Param("releatedetailid")String releatedetailid);
    /**
     * 根据商品编号 启用成本差额
     * @param goodsid
     * @return
     */
    public int openCostDiffAmountByGoodsid(@Param("goodsid")String goodsid,@Param("relatebillid")String relatebillid,@Param("releatedetailid")String releatedetailid);
    /**
     * 判断成本差额是否分摊
     * @param billtype
     * @param billid
     * @param detailid
     * @param goodsid
     * @return
     */
    public int hasCostDiffAmountNoShare(@Param("billtype")String billtype,@Param("billid")String billid,@Param("detailid")String detailid,@Param("goodsid")String goodsid);

    /**
     * 获取符合条件的商品信息
     * @param map
     * @return
     * @throws Exception
     */
    public List<GoodsInfo> getGoodsInfoListByMap(Map map) throws Exception ;

    /**
     * 获取所有商品档案编号
     * @return
     * @author panxiaoxiao
     * @date Jun 21, 2013
     */
    public List getAllGoodsidList();

    /**
     * 在线获取仓库下所有商品档案列表(提供给手机端)
     * @param map
     * @return
     * @author chenwei
     * @date 2015年9月1日
     */
    public List<GoodsInfo> getStorageGoodsInfoListFroPhone(Map map);

    /**
     * 在线获取所有仓库下商品档案数量(提供给手机端)
     * @param map
     * @return
     */
    public int gettStorageGoodsInfoListFroPhoneCount(Map map);

	/**
	 * 获取更新时间在time时间之后的客户
	 *
	 * @param time      更新时间，获取更新时间在此之后的所有客户。格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 30, 2016
	 */
	public List<GoodsInfo> getGoodsListForMecshop(@Param("time") Date time);

	/**
	 * 获取所有商品价格套
	 *
	 * @param offset
	 * @param rows
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 17, 2016
	 */
	public List<GoodsInfo_PriceInfo> getGoodsPriceListForMecshop(@Param("offset") int offset, @Param("rows") int rows);

	/**
	 * 获取计量单位
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 29, 2016
	 */
	public List<MeteringUnit> getMeteringUnitListForMecshop();

	/**
	 * 获取品牌
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 29, 2016
	 */
	public List<Brand> getBrandListForMecshop();

	/**
	 * 获取商品分类
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 29, 2016
	 */
	public List<WaresClass> getWaresClassListForMecshop();

    /**
     * 获取全部启用品牌list
     *
     * @return
     * @author limin
     * @date Nov 2, 2016
     */
    public List<Brand> getOpenedBrandList();

	/**
	 * 根据商品编码，业务日期更新商品最新销售日期
	 * @param goodsid
	 * @param businessdate
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-21
	 */
	public int updateGoodsinfoNewsaledate(@Param("goodsid")String goodsid, @Param("businessdate")String businessdate);

	void updateDemandDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateDispatchbillDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateOrderCarDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateOrderDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateReceiptDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateRejectbillDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateSaleoutDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateSalerejectEnterDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateInvoiceDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateInvoiceBillDetailByBrandOrBranduserOrSupplieruserChange(Map map);

	void updateCustomerPushByBrandOrBranduserOrSupplieruserChange();

    void updatePersnBrandOrPersonSupplier();
    /**
     * 获取商品的成本变更记录
     * @param goodsid
     * @return
     */
    List getGoodesCostpriceChangeList(@Param("goodsid") String goodsid);

	/**
	 * 按金税导出格式获取商品列表
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author zhanghonghui
	 * @date Apr 21, 2017
	 */
    List<Map> getGoodsListForHTJS(PageMap pageMap);

	/**
	 * 批量更新商品档案里对应金税系统编码
	 * @param dataList
	 * @return int
	 * @throws
	 * @author zhanghonghui
	 * @date Apr 24, 2017
	 */
    int updateJsgoodsForHTKPBatch(List dataList);

	/**
	 * 获取指定时间内修改过的商品
	 * @param param
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Dec 15, 2015
	 */
	public List<Map> getUpdatedGoodsList(Map param) throws Exception;

	/**
	 * 修改商品货位
	 * @param goodsid
	 * @param itemno
	 * @return
	 * @author wanghongteng
	 * @date 2017-6-15
	 */
	public int editGoodsItemno(@Param("goodsid") String goodsid,@Param("itemno") String itemno);

	/**
	 * 修改商品金税相关信息
	 * @param goodsInfo
	 * @return
	 * @author zhang_honghui
	 * @date 2017-07-14
	 */
	public int updateGoodsInfoForJS(GoodsInfo goodsInfo);

	/**
	 * 获取商品成本变更记录
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Nov 29, 2017
	 */
	public List getGoodesSimplifyViewCostpriceChageList(PageMap pageMap);

	/**
	 * 获取商品成本变更记录
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Nov 29, 2017
	 */
	public int getGoodesSimplifyViewCostpriceChageCount(PageMap pageMap);

	/**
	 * 修改商品最新库存价
	 * @param accountid
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Jan 29, 2018
	 */
	public int updateCostGoodsCostPrice(@Param("accountid") String accountid);


    /**
     * 修改了商品档案的主单位和辅单位后，t_storage_summary,t_storage_summary_batch 表里相关字段也调整回来
     * @param goodsid
     * @param unitid
     * @param auxunitid
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 27, 2018
     */
    public int editStorageSummaryMeteringInfo(@Param("goodsid") String goodsid,@Param("unitid") String unitid,@Param("unitname") String unitname,@Param("auxunitid") String auxunitid,@Param("auxunitname") String auxunitname);
}


