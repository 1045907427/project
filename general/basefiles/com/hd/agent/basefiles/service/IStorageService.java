/**
 * @(#)IStorageService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 16, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.StorageInout;
import com.hd.agent.basefiles.model.StorageLocation;
import com.hd.agent.basefiles.model.StorageType;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 库存基础档案service
 * @author chenwei
 */
public interface IStorageService {
	/**
	 * 导入出入库类型档案
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 7, 2013
	 */
	public Map addDRStorageInout(List<StorageInout> list)throws Exception;
	/**
	 * 出入库类型档案添加
	 * @param storageInout
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 16, 2013
	 */
	public boolean addStorageInout(StorageInout storageInout) throws Exception;
	/**
	 * 验证出入库类型编码是否重复
	 * true未重复 false重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean checkStorageInoutID(String id) throws Exception;
	/**
	 * 验证出入库类型名称是否重复
	 * true未重复 false重复
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean checkStorageInoutName(String name) throws Exception;
	/**
	 * 获取出入库类型详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public StorageInout showStorageInoutInfo(String id) throws Exception;
	/**
	 * 获取出入库类型列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public PageData showStorageInoutList(PageMap pageMap) throws Exception;
	/**
	 * 修改出入库类型
	 * @param storageInout
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean editStorageInout(StorageInout storageInout) throws Exception;
	/**
	 * 删除出入库类型档案
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean deleteStorageInout(String id) throws Exception;
	/**
	 * 启用出入库类型档案
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean openStorageInout(String id) throws Exception;
	/**
	 * 禁用出入库类型档案
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean closeStorageInout(String id) throws Exception;
	
	/*----------------------仓库类型---------------------------------*/
	
	public Map addDRStorageType(List<StorageType> list)throws Exception;
	
	/**
	 * 仓库类型添加
	 * @param storageType
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean addStorageType(StorageType storageType) throws Exception;
	/**
	 * 验证仓库类型编码是否重复
	 * true未重复 false重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean checkStorageTypeID(String id) throws Exception;
	/**
	 * 验证仓库类型名称是否重复
	 * true未重复 false重复
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean checkStorageTypeName(String name) throws Exception;
	/**
	 * 获取仓库类型详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public StorageType showStorageTypeInfo(String id) throws Exception;
	/**
	 * 获取仓库类型列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public PageData showStorageTypeList(PageMap pageMap) throws Exception;
	/**
	 * 仓库类型修改
	 * @param storageType
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2013
	 */
	public boolean editStorageType(StorageType storageType) throws Exception;
	/**
	 * 删除仓库类型
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public boolean deleteStorageType(String id) throws Exception;
	/**
	 * 启用仓库类型
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public boolean openStorageType(String id) throws Exception;
	/**
	 * 禁用仓库类型
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public boolean closeStorageType(String id) throws Exception;
	
	/*-------------------库位档案--------------------*/
	/**
	 * 获取库位全部数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public List getStorageLocationList() throws Exception;
	/**
	 * 库位档案新增
	 * @param storageLocation
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public boolean addStorageLocation(StorageLocation storageLocation) throws Exception;
	/**
	 * 获取库位档案详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public StorageLocation showStorageLocationInfo(String id) throws Exception;
	/**
	 * 验证库位档案ID是否重复
	 * true未重复 false重复
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public boolean checkStorageLocationID(String id) throws Exception;
	/**
	 * 验证库位档案名称是否重复
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 19, 2013
	 */
	public boolean checkStorageLocationName(String name) throws Exception;
	/**
	 * 库位档案修改
	 * @param storageLocation
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public Map editStorageLocation(StorageLocation storageLocation) throws Exception;
	/**
	 * 库位档案启用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public boolean openStorageLocation(String id) throws Exception;
	/**
	 * 库位档案禁用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public boolean closeStorageLocation(String id) throws Exception;
	/**
	 * 库位档案删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 20, 2013
	 */
	public boolean deleteStorageLocation(String id) throws Exception;
	
	/**
	 * 判断库位档案本级名称是否重复
	 * @param thisname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public boolean isRepeatStorageLocationThisname(String thisname)throws Exception;
	/*-------------------------------仓库档案-------------------------*/
	/**
	 * 验证仓库档案编码是否重复
	 * true 未重复 false重复
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public boolean checkStorageInfoID(String id) throws Exception;
	/**
	 * 验证仓库档案名称是否重复
	 * true 未重复 false重复
	 * @param name
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public boolean checkStorageInfoName(String name) throws Exception;
	/**
	 *
	 */
	public StorageInfo getStorageByName(String name) throws Exception;
	/**
	 * 仓库档案添加
	 * @param storageInfo
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public boolean addStorageInfo(StorageInfo storageInfo) throws Exception;
	
	public Map addDRStorageInfo(List<StorageInfo> list) throws Exception;
	/**
	 * 获取仓库档案详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public StorageInfo showStorageInfo(String id) throws Exception;
	/**
	 * 获取仓库档案列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public PageData showStorageInfoList(PageMap pageMap) throws Exception;
	/**
	 * 仓库档案修改
	 * @param storageInfo
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 22, 2013
	 */
	public boolean editStorageInfo(StorageInfo storageInfo) throws Exception;
	/**
	 * 删除仓库档案
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	public boolean deleteStorageInfo(String id) throws Exception;
	/**
	 * 仓库档案启用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	public boolean openStorageInfo(String id) throws Exception;
	/**
	 * 仓库档案禁用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 23, 2013
	 */
	public boolean closeStorageInfo(String id) throws Exception;

    /**
     * Excel库位导入列表数据
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-18
     */
    public Map addDRStorageLocationExcel(List<StorageLocation> list)throws Exception;
}

