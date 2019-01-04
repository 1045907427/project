/**
 * @(#)IGoodsService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface IGoodsService {

	/*---------------------------------计量单位----------------------------------------------*/
	
	/**
	 * 获取计量单位列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public PageData getMeteringUnitList(PageMap pageMap)throws Exception;
	
	/**
	 * 新增计量单位
	 * @param meteringUnit
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public boolean addMeteringUnit(MeteringUnit meteringUnit)throws Exception;
	
	/**
	 * 判断是否id重复，true 重复 false 不重复 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public boolean isRepeatMUID(String id)throws Exception;
	
	/**
	 * 判断是否name重复，true 重复 false 不重复 
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public boolean isRepeatMUName(String name)throws Exception;
	
	/**
	 * 显示计量单位信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public MeteringUnit showMeteringUnitInfo(String id)throws Exception;
	
	/**
	 * 修改计量单位 ，修改前判断是否被引用
	 * @param meteringUnit
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public boolean editMeteringUnit(MeteringUnit meteringUnit)throws Exception;
	
	/**
	 * 删除计量单位,判断是否可以删除 
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public boolean deleteMeteringUnit(String id)throws Exception;
	
	/**
	 * 启用计量单位
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public boolean enableMeteringUnit(String id,String openuserid)throws Exception;
	
	/**
	 * 禁用计量单位
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-11
	 */
	public boolean disableMeteringUnit(String id,String closeuserid)throws Exception;
	
	/**
	 * Excel导入添加记录单位信息，
	 * 判断导入的记录单位是否重复，导入成功后的状态为暂存3
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-20
	 */
	public Map addDRMeteringUnitInfo(List<MeteringUnit> list)throws Exception;
	
	public List getMUListForCombobox()throws Exception;
	
	/*---------------------------------商品品牌----------------------------------------------*/
	
	public PageData getBrandListForCombobox(PageMap pageMap)throws Exception;
	
	/**
	 * 获取商品品牌列表，设置数据权限
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public PageData getBrandListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 根据编号判断该商品品牌是否重复,true 重复，false 不重复
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public boolean isRepeatBrandById(String id)throws Exception;
	
	/**
	 * 判断名称是否重复,true 重复，false 不重复
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public boolean isRepeatBrandName(String name)throws Exception;
	
	/**
	 * 新增商品品牌
	 * @param brand
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public boolean addBrand(Brand brand)throws Exception;
	
	/**
	 * 新增导入商品品牌
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 27, 2013
	 */
	public Map addDRBrand(List<Brand> list)throws  Exception;
	
	/**
	 * 修改商品品牌
	 * @param brand
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public boolean editBrand(Brand brand)throws Exception;
	
	/**
	 * 删除商品品牌，判断是否被引用
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public boolean deleteBrand(String id)throws Exception;
	
	/**
	 * 获取商品品牌详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-16
	 */
	public Brand getBrandInfo(String id)throws Exception;
	
	/**
	 * 启用商品品牌
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public boolean enableBrand(Map paramMap)throws Exception;
	
	/**
	 * 禁用商品品牌
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public boolean disableBrand(Map paramMap)throws Exception;
	
	/**
	 * 获取所属指定部门的所有商品品牌数据
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public List getBrandListByDeptid(String deptid)throws Exception;
	
	/**
	 * 根据所属部门编码获取品牌数据列表(包含父级部门)
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 4, 2014
	 */
	public List getBrandListWithParentByDeptid(String deptid)throws Exception;
	/**
	 * 获取所属指定供应商的所有商品品牌数据
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public List getBrandListBySupplierid(String supplierid)throws Exception;
	
	/**
	 * 
	 * @param supplierids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年5月29日
	 */
	public List getBrandListBySupplierids(String supplierids)throws Exception;
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
	public List getBrandListByMap(Map map) throws Exception;

	/**
	 * 修改金税相关信息
	 * @param brandInfo
	 * @return java.util.Map
	 * @throws
	 * @author zhanghonghui
	 * @date Aug 08, 2017
	 */
	public Map updateBrandInfoForJS(Brand brandInfo) throws Exception;
	
	/*---------------------------------商品分类----------------------------------------------*/
	
	/**
	 * 获取商品分类列表 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public PageData getWaresClassListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 获取树状商品分类列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-12
	 */
	public List<WaresClass> getWaresClassTreeList()throws Exception;
	/**
	 * 获取树状商品分类启用列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年8月28日
	 */
	public List<WaresClass> getWaresClassTreeOpenList()throws Exception;
	/**
	 * 编码是否重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public boolean isRepeatWCID(String id)throws Exception;
	
	/**
	 * 本级名称是否重复
	 * @param thisname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public boolean isRepeatThisName(String thisname)throws Exception;
	
	/**
	 * 新增商品分类
	 * @param waresClass
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public boolean addWaresClass(WaresClass waresClass)throws Exception;
	
	/**
	 * 获取商品分类详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public WaresClass getWaresClassInfo(String id)throws Exception;
	
	/**
	 * 修改商品分类，且更新级联关系
	 * @param waresClass
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-13
	 */
	public Map editWaresClass(WaresClass waresClass)throws Exception;
	
	/**
	 * 删除商品分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-15
	 */
	public boolean deleteWaresClass(String id)throws Exception;
	
	/**
	 * 启用商品分类
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-15
	 */
	public boolean enableWaresClass(Map paramMap)throws Exception;
	
	/**
	 * 禁用商品分类
	 * @param waresClass
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-15
	 */
	public boolean disableWaresClass(WaresClass waresClass)throws Exception;
	
	/**
	 * 判断子节点是否存在商品分类列表,true 存在，false 不存在
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-15
	 */
	public boolean isExistChildWCList(String id)throws Exception;
	
	/**
	 * Excel导入添加商品分类信息
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-20
	 */
	public Map addDRWaresClassInfo(List<WaresClass> list)throws Exception;
	
	/**
	 * Excel导入商品分类列表数据
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 12, 2014
	 */
	public Map addDRGoodsSortExcel(List<WaresClass> list)throws Exception;
	
	/**
	 * 获取所有子节点
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 24, 2013
	 */
	public List getWaresClassListByPid(String id);
	
	/*---------------------------------商品档案----------------------------------------------*/
	
	public List getGoodsListForCombobox(String brandid)throws Exception;
	/**
	 * 获取商品档案列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public PageData goodsInfoListPage(PageMap pageMap)throws Exception;
	
	/**
	 * 商品档案详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public GoodsInfo showGoodsInfo(String id)throws Exception;


    /**
     * 商品档案详情
     * @param barcode
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2013-4-18
     */
    public GoodsInfo getGoodsInfoByBarcode(String barcode)throws Exception;
	/**
	 * 批量启用商品档案
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public boolean enableGoodsInfos(Map map)throws Exception;
	
	/**
	 * 批量禁用商品档案
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-18
	 */
	public boolean disableGoodsInfos(Map map)throws Exception;
	
	/**
	 * 批量删除商品档案，同时删除包含该商品编号的主计量单位、价格套、对饮仓库、对应分类
	 * @param idsArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public boolean deleteGoodsInfos(String[] idsArr)throws Exception;
	
	/**
	 * 新增商品档案
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public boolean addGoodsInfo(GoodsInfo goodsInfo,List<GoodsInfo_PriceInfo> priceInfoList,
			List<GoodsInfo_MteringUnitInfo> meteringUnitInfoList,List<GoodsInfo_StorageInfo> storageInfoList,
			List<GoodsInfo_WaresClassInfo> waresClassList,List<GoodsStorageLocation> SLList)throws Exception;
	
	/**
	 * 导入新增商品档案
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public Map addDRGoodsInfo(GoodsInfo goodsInfo)throws Exception;
	
	/**
	 * 快捷导入商品档案
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 27, 2013
	 */
	public Map addShortcutGoodsExcel(List<GoodsInfo> list)throws Exception;
	
	/**
	 * 导入商品档案对应辅计量
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 19, 2013
	 */
	public Map addDRGoodsInfoMU(List<GoodsInfo_MteringUnitInfo> list)throws Exception;
	
	/**
	 * 导入商品档案对应价格套
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 19, 2013
	 */
	public Map addDRGoodsInfoPrice(List<GoodsInfo_PriceInfo> list)throws Exception;
	
	/**
	 * 导入商品档案对应仓库
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 19, 2013
	 */
	public Map addDRGoodsInfoStorage(List<GoodsInfo_StorageInfo> list)throws Exception;
	
	/**
	 * 导入商品档案对应库位
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 19, 2013
	 */
	public Map addDRGoodsInfoSL(List<GoodsStorageLocation> list)throws Exception;
	
	/**
	 * 导入商品档案对应商品分类
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 19, 2013
	 */
	public Map addDRGoodsInfoWC(List<GoodsInfo_WaresClassInfo> list)throws Exception;
	
	/**
	 * 判断商品ID是否重复或商品是否存在， true 重复，false 不重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public boolean isRepeatGoodsInfoID(String id)throws Exception;
	
	/**
	 * 判断商品名称是否重复,true 重复，false 不重复
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-4-19
	 */
	public boolean isRepeatGoodsInfoName(String name)throws Exception;
	
	/**
	 * 判断条形码是否重复 true 重复，false 不重复
	 * @param barcode
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public boolean isRepeatGoodsInfoBarcode(String barcode)throws Exception;
	
	/**
	 * 判断箱装条形码是否重复 true 重复，false 不重复
	 * @param boxbarcode
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public boolean isRepeatGoodsInfoBoxbarcode(String boxbarcode)throws Exception;
	
	/**
	 * 判断货号是否重复 true 重复，false 不重复
	 * @param itemno
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 22, 2013
	 */
	public boolean isRepeatGoodsInfoItemno(String itemno)throws Exception;
	
	/**
	 * 显示主计量单位列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public List showMeteringUnitInfoList(PageMap pageMap)throws Exception;
	
	/**
	 * 显示价格套列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public List showPriceInfoList(PageMap pageMap)throws Exception;
	
	/**
	 * 新增价格套
	 * @param priceInfoMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 4, 2013
	 */
	public boolean addPriceInfos(Map priceInfoMap)throws Exception;
	
	/**
	 * 显示对应仓库列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public List showStorageInfoList(PageMap pageMap)throws Exception;
	
	/**
	 * 显示对应库位列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 28, 2013
	 */
	public List showGoodsStorageLocationList(PageMap pageMap)throws Exception;
	
	/**
	 * 显示对应分类列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public List showWaresClassInfoList(PageMap pageMap)throws Exception;
	
	/**
	 * 修改商品
	 * @param goodsInfo
	 * @param priceInfoList
	 * @param meteringUnitInfoList
	 * @param storageInfoList
	 * @param waresClassList
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 23, 2013
	 */
	public Map editGoodsInfo(GoodsInfo goodsInfo,List<GoodsInfo_PriceInfo> priceInfoList,
			List<GoodsInfo_MteringUnitInfo> meteringUnitInfoList,List<GoodsInfo_StorageInfo> storageInfoList,
			List<GoodsInfo_WaresClassInfo> waresClassList,List<GoodsStorageLocation> SLList)throws Exception;
	
	/**
	 *根据价格套编号获取商品信息价格信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 6, 2013
	 */
	public PageData getGoodsInfoPriceByCode(PageMap pageMap)throws Exception;
	
	/**
	 * 修改价格套
	 * @param goodsInfo_PriceInfo
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 6, 2013
	 */
	public boolean editPriceInfo(GoodsInfo_PriceInfo goodsInfo_PriceInfo)throws Exception;
	
	/**
	 * 根据商品档案编号获取辅助计量为列表
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date May 10, 2013
	 */
	public List<GoodsInfo_MteringUnitInfo> getMUListByGoodsId(String goodsid)throws Exception;
	
	/**
	 * 根据条件获取已启用商品信息列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 20, 2013
	 */
	public List<GoodsInfo> getGoodsInfoByCondition(PageMap pageMap)throws Exception;
	
	/**
	 * 回写商品档案数据
	 * @param goodsInfo
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-15
	 */
	public boolean updateGoodsInfoWriteBack(GoodsInfo goodsInfo) throws Exception;
	
	/**
	 * 获取商品档案列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 2, 2013
	 */
	public PageData getGoodsSelectListData(PageMap pageMap) throws Exception;
	/**
	 * 获取商品档案列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 2, 2013
	 */
	public List<GoodsInfo> getGoodsSelectListDataSimple(PageMap pageMap) throws Exception;
	/**
	 * 根据商品编码获取价格套管理列表数据
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public List getPriceListByGoodsid(String goodsid)throws Exception;
	
	/**
	 * 快捷新增商品
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 3, 2013
	 */
	public boolean addGoodsInfoShortcut(GoodsInfo goodsInfo, 
			List<GoodsInfo_PriceInfo> priceInfoList,
			GoodsInfo_MteringUnitInfo goodsMUInfo)throws Exception;
	
	/**
	 * 快捷修改商品
	 * @param goodsInfo
	 * @param priceInfoList
	 * @param goodsMUInfo
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 5, 2013
	 */
	public Map editGoodsInfoShortcut(GoodsInfo goodsInfo,
			List<GoodsInfo_PriceInfo> priceInfoList,
			GoodsInfo_MteringUnitInfo goodsMUInfo)throws Exception;
			
	/**
	 * 修改商品档案中的图片信息
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 9, 2015
	 */
	public boolean editGoodsImageInfo(GoodsInfo goodsInfo)throws Exception;
	
	/**
	 * 根据商品编码、价格套编码判断是否存在该价格套数据
	 * @param goodsid
	 * @param code
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 9, 2013
	 */
	public GoodsInfo_PriceInfo getPriceDataByGoodsidAndCode(String goodsid,String code)throws Exception;

	public Map getTaxPriceByGoodsidAndPriceCode(String goodsid,String pricesort)throws Exception;

    /**
     *获取导出模板中的字段值
     * @param idlist
     * @return
     * @throws Exception
     * @author lin_xx
     * @date July 6, 2015
     */
    public List<GoodsInfo> getUpLoadMod(String[] idlist)throws Exception;

	/**
	 * 获取在更新日期之后的商品数据
	 *
	 * @param time
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jun 1, 2016
	 */
	public List<GoodsInfo> getGoodsListForMecshop(Date time) throws Exception;

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
	public List<GoodsInfo_PriceInfo> getGoodsPriceListForMecshop(String offset, String rows);

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
     * 获取商品的成本变更记录
     * @param id
     * @return
     * @throws Exception
     */
	public List getGoodesCostpriceChangeList(String id) throws Exception;
	/**
	 * 按金税导出格式获取商品列表
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author zhanghonghui
	 * @date Apr 21, 2017
	 */
	List<Map> getGoodsListForHTJS(PageMap pageMap) throws Exception;
	/**
	 * 更新商品档案里对应的航天金税系统商品字段
	 * @param dataList
	 * @return map
	 * @throws
	 * @author zhanghonghui
	 * @date Apr 21, 2017
	 */
	Map importAndUpdateJsgoodsForHTKP(List<Map<String,Object>> dataList) throws Exception;

	/**
	 * 修改商品货位
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-6-15
	 */
	public Map editGoodsItemno(String goodsid,String itemno) throws Exception;
    /*---------------------------------以下方法用于门店客户端----------------------------------------------*/

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
	 * 更新高品档案金税相关信息
	 * @param goodsInfo
	 * @return map
	 * @throws
	 * @author zhanghonghui
	 * @date Jul 14, 2017
	 */
	public Map updateGoodsInfoForJS(GoodsInfo goodsInfo) throws Exception;

	/**
	 * 获取商品成本变更记录数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Nov 29, 2017
	 */
	public PageData getGoodesSimplifyViewCostpriceChageData(PageMap pageMap);
}
