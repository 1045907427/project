/**
 * @(#)LogisticsMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 6, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.LogisticsCar;
import com.hd.agent.basefiles.model.LogisticsLine;
import com.hd.agent.basefiles.model.LogisticsLineCar;
import com.hd.agent.basefiles.model.LogisticsLineCustomer;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 物流档案
 * @author panxiaoxiao
 */
public interface LogisticsMapper {

	/*---------------------线路档案----------------------------*/
	
	/**
	 * 线路档案列表分页
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public List getLineInfoListPage(PageMap pageMap);

	/**
	 * 线路档案数量
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int getLineInfoListCount(PageMap pageMap);

	/**
	 * 线路档案详情
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public LogisticsLine getLineInfo(String id);
	
	/**
	 * 根据客户编码获取存在其客户线路的线路档案列表
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 7, 2014
	 */
	public List getLogisticsLineCustomerList(@Param("customerid")String customerid);

	/**
	 * 根据线路ID获取车辆信息
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public List getCarInfoList(PageMap pageMap);

	/**
	 * 根据线路ID获取车辆信息数量
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int getCarInfoListCount(PageMap pageMap);

	/**
	 * 根据线路ID获取客户信息列表
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public List getCustomerInfoList(PageMap pageMap);

	/**
	 * 根据线路ID获取客户信息数量
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int getCustomerInfoListCount(PageMap pageMap);
	
	/**
	 * 根据线路ID获取客户信息列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 29, 2014
	 */
	public List getLineCustomerList(PageMap pageMap);
	
	/**
	 * 根据线路ID获取客户信息数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 29, 2014
	 */
	public int getLineCustomerCount(PageMap pageMap);

	/**
	 * 判断线路ID是否重复或线路是否存在
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int isRepeatLineInfoID(String id);

	/**
	 * 新增线路档案
	 * @param lineInfo
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int addLineInfo(LogisticsLine lineInfo);

	/**
	 * 添加线路档案，添加相应的线路车辆
	 * @param lineCar
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public void addLineCar(LogisticsLineCar lineCar);
	
	/**
	 * 是否已存在该车辆
	 * @param lineid
	 * @param carid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 30, 2014
	 */
	public int isExistLineCar(@Param("lineid")String lineid,@Param("carid")String carid);
	
	/**
	 * 根据线路编码和车辆编码获取所属车辆明细
	 * @param lineid
	 * @param carid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 10, 2014
	 */
	public LogisticsLineCar getLogisticsLineCar(@Param("lineid")String lineid,@Param("carid")String carid);
	
	/**
	 * 判断是否已存在该客户
	 * @param lineid
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 30, 2014
	 */
	public int isExistLineCustomer(@Param("lineid")String lineid,@Param("customerid")String customerid);

	/**
	 * 添加线路档案，添加相应的线路客户
	 * @param lineCustomer
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int addLineCustomer(LogisticsLineCustomer lineCustomer);
	
	/**
	 * 修改相应的线路客户
	 * @param lineCustomer
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 10, 2014
	 */
	public int updateDRLineCustomer(LogisticsLineCustomer lineCustomer);
	
	/**
	 * 删除线路档案，删除相应的线路车辆
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int deleteLineCar(String id);

	/**
	 * 删除线路档案，删除相应的线路客户
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int deleteLineCustomer(String id);
	
	/**
	 * 根据客户编码删除线路客户
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 23, 2014
	 */
	public int deleteLineCustomerByCustomerid(@Param("customerid")String customerid);

	/**
	 * 修改线路档案
	 * @param lineInfo
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int updateLineInfo(LogisticsLine lineInfo);

	/**
	 * 修改线路ID后，修改线路车辆的线路ID
	 * @param lineInfo
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public void updateLineCar(LogisticsLine lineInfo);

	/**
	 * 修改线路ID后，修改线路客户的线路ID
	 * @param lineInfo
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public void updateLineCustomer(LogisticsLine lineInfo);

	/**
	 * 批量删除线路档案
	 * @param idsArr
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int deleteLineInfos(@Param("idsArr") String[] idsArr);

	/**
	 * 批量启用线路档案
	 * @param map
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int enableLineInfos(Map map);

	/**
	 * 批量禁用线路档案
	 * @param map
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	public int disableLineInfos(Map map);

	/**
	 * 获取默认车辆
	 * @param id
	 * @return
	 * @author yezhenyu 
	 * @date May 23, 2014
	 */
	public LogisticsCar getDefaultCarByLineId(String id);

	/**
	 * 根据线路ID获取车辆列表
	 * @param id
	 * @return
	 * @author yezhenyu 
	 * @date May 28, 2014
	 */
	public List<LogisticsCar> getCarInfoListByLineid(String id);
	
	/**
	 * 根据线路档案编码获取客户线路列表数据
	 * @param lineid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 16, 2014
	 */
	public List<LogisticsLineCustomer> getCustomerListByLineid(@Param("lineid")String lineid);
	
	/*-----------------------车辆档案-----------------------------*/
	
	/**
	 * 车辆档案列表分页
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	List<LogisticsCar> getCarListPage(PageMap pageMap);
	
	/**
	 * 车辆档案列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public int getCarListCount(PageMap pageMap);

	/**
	 * 车辆单条档案
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	LogisticsCar getCar(String id);

	/**
	 * 新增车辆
	 * @param carInfo
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	int addCar(LogisticsCar carInfo);

	/**
	 * 判断车辆ID是否重复或车辆是否存在
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	int isRepeatCarID(String id);

	/**
	 * 判断车辆名字是否重复或车辆是否存在
	 * @param name
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	int isRepeatCarName(String name);

	/**
	 * 修改车辆档案
	 * @param carInfo
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	int updateCar(LogisticsCar carInfo);

	/**
	 * 批量删除车辆档案
	 * @param idsArr
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	int deleteCar(@Param("idsArr") String[] idsArr);

	/**
	 * 批量启用车辆档案
	 * @param map
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	int enableCar(Map map);

	/**
	 * 批量禁用车辆档案
	 * @param map
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	int disableCar(Map map);

	/**
	 * 获取ID不在condition.list中的车辆档案列表
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	List getCarListForCombobox(PageMap pageMap);

	/**
	 * 获取ID不在condition.list中的车辆档案列表的数量
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	int getCarListForComboboxCount(PageMap pageMap);

	/**
	 * 根据车辆ID获取车辆档案列表
	 * @param carids
	 * @return
	 * @author yezhenyu
	 * @date May 20, 2014
	 */
	List<LogisticsCar> getCarListForLine(String carids);
	
	/**
	 * 根据线路编码和客户编码获取线路客户明细
	 * @param lineid
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 7, 2014
	 */
	public LogisticsLineCustomer getLogisticsLineCustomer(@Param("lineid")String lineid,@Param("customerid")String customerid);

    /**
     * 获取线路-客户对应关系
	 *
	 * @param customerids
	 * @param linelocation
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 26, 2016
     */
    public List<LogisticsLineCustomer> getLineCustomerMapList(@Param("customerids") List<String> customerids, @Param("linelocation") boolean linelocation);

    /**
     * 获取所有线路
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 26, 2016
     */
    public List<LogisticsLine> getLogisticsLines();

	/**
	 * 删除线路客户档案
	 * @param lineid
	 * @param customerids
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Mar 14, 2018
	 */
    public int deleteCustomerForLine(@Param("lineid") String lineid,@Param("customerids") String customerids);

	/**
	 * 更新线路客户数量
	 * @param lineid
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Mar 14, 2018
	 */
    public int updatLineCustomerNum(@Param("lineid") String lineid);

	/**
	 * 删除线路车辆信息
	 * @param lineid
	 * @param carids
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Mar 16, 2018
	 */
    public int deleteCarForLine(@Param("lineid") String lineid,@Param("carids") String carids);
}

