package com.hd.agent.basefiles.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.StorageInout;
import com.hd.agent.basefiles.model.StorageLocation;
import com.hd.agent.basefiles.model.StorageType;
import com.hd.agent.common.util.PageMap;
/**
 * 各库存档案dao
 * @author chenwei
 */
public interface StorageMapper {
   /**
    * 添加出入库类型档案
    * @param storageInout
    * @return
    * @author chenwei 
    * @date Apr 16, 2013
    */
	public int addStorageInout(StorageInout storageInout);
	/**
	 * 根据id获取出入库类型的数量
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 16, 2013
	 */
	public int getStorageInoutCountByID(@Param("id")String id);
	/**
	 * 根据名称获取出入库类型的数量
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 16, 2013
	 */
	public int getStorageInoutCountByName(@Param("name")String name);
	/**
	 * 获取出入库类型详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public StorageInout showStorageInoutInfo(@Param("id")String id);
	/**
	 * 获取出入库类型列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public List showStorageInoutList(PageMap pageMap);
	/**
	 * 获取出入库类型数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int showStorageInoutCount(PageMap pageMap);
	/**
	 * 获取出入库类型全部列表数据 根据类型获取
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 5, 2013
	 */
	public List getStorageInoutAllList(PageMap pageMap);
	/**
	 * 修改出入库类型
	 * @param storageInout
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int editStorageInout(StorageInout storageInout);
	/**
	 * 删除出入库类型档案
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int deleteStorageInout(@Param("id")String id);
	/**
	 * 启用出入库类型档案
	 * @param id
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int openStorageInout(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	/**
	 * 禁用出入库类型档案
	 * @param id
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int closeStorageInout(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	
	/*--------------------------------------仓库类型档案------------------------------*/
	/**
	 * 添加仓库类型
	 * @param storageType
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int addStorageType(StorageType storageType);
	/**
	 * 根据ID获取仓库类型的数量
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int getStorageTypeCountByID(@Param("id")String id);
	/**
	 * 根据名称获取仓库类型的数量
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int getStorageTypeCountByName(@Param("name")String name);
	/**
	 * 获取仓库类型详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public StorageType showStorageTypeInfo(@Param("id")String id);
	/**
	 * 获取仓库类型列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public List showStorageTypeList(PageMap pageMap);
	/**
	 * 获取仓库类型数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int showStorageTypeCount(PageMap pageMap);
	/**
	 * 仓库类型修改
	 * @param storageType
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public int editStorageType(StorageType storageType);
	/**
	 * 删除仓库类型
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public int deleteStorageType(@Param("id")String id);
	/**
	 * 启用仓库类型
	 * @param id
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public int openStorageType(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	/**
	 * 禁用仓库类型
	 * @param id
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public int closeStorageType(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	
	/*--------------------库位档案-------------------------*/
	/**
	 * 获取库位档案数据
	 * @return
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public List getStorageLocationList();
	/**
	 * 库位档案新增
	 * @param storageLocation
	 * @return
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public int addStorageLocation(StorageLocation storageLocation);
	/**
	 * 根据id获取库位档案数量
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public int getStorageLocationCountByID(@Param("id")String id);
	/**
	 * 根据name获取库位档案数量
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public int getStorageLocationCountByName(@Param("name")String name);
	/**
	 * 获取库位档案详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public StorageLocation showStorageLocationInfo(@Param("id")String id);
	/**
	 * 修改库位档案信息
	 * @param storageLocation
	 * @return
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public int editStorageLocation(StorageLocation storageLocation);
	/**
	 * 启用库位档案
	 * @param id
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public int openStorageLocation(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	/**
	 * 禁用库位档案
	 * @param id
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public int closeStorageLocation(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	/**
	 * 删除库位档案
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public int deleteStorageLocation(@Param("id")String id);
	
	/**
	 * 判断库位档案本级名称是否重复
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public int isRepeatThisName(String thisname);
	/*---------------------仓库档案----------------------*/
	/**
	 * 根据id获取仓库档案数量
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public int getStorageInfoCountByID(@Param("id")String id);
	/**
	 * 根据名称获取仓库档案数量
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public int getStorageInfoCountByName(@Param("name")String name);
	/**
	 * 根据名称获取仓库档案信息
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date 2014年7月21日
	 */
	public StorageInfo getStorageInfoByName(@Param("name")String name);
	/**
	 * 仓库档案添加
	 * @param storageInfo
	 * @return
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public int addStorageInfo(StorageInfo storageInfo);
	/**
	 * 获取仓库档案详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public StorageInfo showStorageInfo(String id);
	/**
	 * 获取仓库档案基础信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 17, 2013
	 */
	public StorageInfo showBaseStorageInfo(@Param("id")String id);
	/**
	 * 获取仓库档案列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public List showStorageInfoList(PageMap pageMap);
	/**
	 * 获取仓库档案全部列表
	 * @return
	 * @author chenwei 
	 * @date Aug 5, 2013
	 */
	public List getStorageInfoAllList(PageMap pageMap);
	/**
	 * 获取启用的仓库档案列表
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Mar 05, 2018
	 */
	public List getStorageInfoOpenList(PageMap pageMap);
	/**
	 * 获取仓库档案数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public int showStorageInfoCount(PageMap pageMap);
	/**
	 * 仓库档案修改
	 * @param storageInfo
	 * @return
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public int editStorageInfo(StorageInfo storageInfo);
	/**
	 * 删除仓库档案
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	public int deleteStorageInfo(@Param("id")String id);
	/**
	 * 启用仓库档案
	 * @param id
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	public int openStorageInfo(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	/**
	 * 禁用仓库档案
	 * @param id
	 * @param userid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	public int closeStorageInfo(@Param("id")String id,@Param("userid")String userid,@Param("name")String name);
	/**
	 * 根据车销人员获取对应车销仓库
	 * @param carsaleuser
	 * @return
	 * @author chenwei 
	 * @date Sep 2, 2013
	 */
	public StorageInfo getStorageInfoByCarsaleuser(@Param("carsaleuser")String carsaleuser);
	
	public List returnStorageIdByName(String name);
	
	public List returnSLIdByName(String name);
	
	/**
	 * 根据父级编码获取所有下属库位档案列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 12, 2014
	 */
	public List getStorageLocationChildList(String pid);
	
	/**
	 * 批量修改库位档案
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date May 12, 2014
	 */
	public int editStorageLocationBatch(List<StorageLocation> list);
    /**
     * 获取名称为空，上级编码不为空的库位
     * @return
     * @author panxiaoxiao
     * @date 2015-03-18
     */
    public List<StorageLocation> getStorageLocationWithoutName();
}