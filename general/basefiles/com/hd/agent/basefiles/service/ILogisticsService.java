/**
 * @(#)ILogisticsService.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 24, 2014 yezhenyu 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.LogisticsCar;
import com.hd.agent.basefiles.model.LogisticsLine;
import com.hd.agent.basefiles.model.LogisticsLineCar;
import com.hd.agent.basefiles.model.LogisticsLineCustomer;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author yezhenyu
 */
public interface ILogisticsService {
	/**
	 * 获取线路档案列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 25, 2014
	 */
	public PageData lineInfoListPage(PageMap pageMap) throws Exception;

	/**
	 * 显示线路档案详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 26, 2014
	 */
	public LogisticsLine showLineInfo(String id) throws Exception;

	/**
	 * 显示线路档案页面中，车辆列表
	 * @param lineid
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 29, 2014
	 */
	public PageData showCarInfoList(PageMap pageMap) throws Exception;

	/**
	 * 显示线路档案页面，客户列表
	 * @param lineid
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 29, 2014
	 */
	public PageData showCustomerInfoList(PageMap pageMap) throws Exception;

	/**
	 * 判断线路ID是否已存在
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 5, 2014
	 */
	public boolean isRepeatLineInfoID(String id) throws Exception;

	/**
	 * 添加线路档案
	 * @param lineInfo
	 * @param lineCarList
	 * @param lineCustomerList
	 * @throws Exception
	 * @return
	 * @author yezhenyu
	 * @date May 5, 2014
	 */
	public boolean addLineInfo(LogisticsLine lineInfo, List<LogisticsLineCar> lineCarList,
			List<LogisticsLineCustomer> lineCustomerList) throws Exception;

	/**
	 * 编辑线路档案
	 * @param lineInfo
	 * @param lineCarList
	 * @param lineCustomerList
	 * @throws Exception
	 * @return
	 * @author yezhenyu
	 * @date May 8, 2014
	 */
	public Map editLineInfo(LogisticsLine lineInfo, List<LogisticsLineCar> lineCarList,
			List<LogisticsLineCustomer> lineCustomerList) throws Exception;

	/**
	 * 删除线路档案
	 * @param newIdArr
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 9, 2014
	 */
	public boolean deleteLineInfos(String[] newIdArr) throws Exception;

	/**
	 * 启用线路档案
	 * @param map
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 12, 2014
	 */
	public boolean enableLineInfos(Map map) throws Exception;

	/**
	 * 禁用线路档案
	 * @param map
	 * @return
	 * @author yezhenyu
	 * @date May 12, 2014
	 */
	public boolean disableLineInfos(Map map) throws Exception;

	/**
	 * 显示车辆档案详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public LogisticsCar showCar(String id) throws Exception;

	/**
	 * 判断车辆ID是否已存在
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public boolean isRepeatCarID(String id) throws Exception;

	/**
	 * 判断车辆名称是否已存在
	 * @param name
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public boolean isRepeatCarName(String name) throws Exception;

	/**
	 * 添加车辆档案
	 * @param carInfo
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public boolean addCar(LogisticsCar carInfo) throws Exception;

	/**
	 * 编辑车辆档案
	 * @param carInfo
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public Map editCar(LogisticsCar carInfo) throws Exception;

	/**
	 * 删除车辆档案
	 * @param newIdArr
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public boolean deleteCar(String[] newIdArr) throws Exception;

	/**
	 * 启用车辆档案
	 * @param map
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public boolean enableCar(Map map) throws Exception;

	/**
	 * 禁用车辆档案
	 * @param map
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public boolean disableCar(Map map) throws Exception;

	/**
	 * 获取车辆档案列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public PageData getCarListPage(PageMap pageMap) throws Exception;

	/**
	 * 在线路档案中，获取ID不在condition.list中的车辆档案列表
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public PageData getCarListForCombobox(PageMap pageMap);

	/**
	 * 在线路档案中，根据ids获取车辆档案列表
	 * @param carids
	 * @return
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public List<LogisticsCar> getCarListForLine(String carids);

	/**
	 * 根据线路ID获取车辆列表
	 * @param id
	 * @return
	 * @author yezhenyu 
	 * @date May 28, 2014
	 */
	public List<LogisticsCar> showCarInfoListByLineid(String id);
	
	/**
	 * 导入线路档案
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 30, 2014
	 */
	public Map addDRLogisticsLine(LogisticsLine logisticsLine)throws Exception;
	
	/**
	 * 导入线路档案所属车辆
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2014
	 */
	public Map addDRLogisticsLineCar(List<LogisticsLineCar> list)throws Exception;
	
	/**
	 * 导入线路档案线路客户
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 30, 2014
	 */
	public Map addDRLogisticsLineCustomer(List<LogisticsLineCustomer> list)throws Exception;
	
	/**
	 * 根据线路编码和客户编码获取线路客户明细
	 * @param lineid
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 7, 2014
	 */
	public LogisticsLineCustomer getLogisticsLineCustomer(String lineid,String customerid)throws Exception;
	
	/**
	 * 根据线路编码获取所属线路客户列表数据
	 * @param lineid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 27, 2014
	 */
	public List getLineCustomerListByLineid(String lineid)throws Exception;
	
	/**
	 * 导入车辆档案
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 5, 2015
	 */
	public Map addDRCar(List<LogisticsCar> list)throws Exception;

    /**
     * 获取默认车辆
     * @param id
     * @return
     * @author yezhenyu
     * @date May 23, 2014
     */
    public LogisticsCar getDefaultCarByLineId(String id);

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
    public List<LogisticsLineCustomer> getLineCustomerMapList(List<String> customerids, boolean linelocation) throws Exception;

    /**
     * 获取所有线路
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 26, 2016
     */
    public List<LogisticsLine> getLogisticsLines() throws Exception;

	/**
	 * 线路档案添加客户信息
	 * @param lineid
	 * @param logisticsLineCustomers
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Mar 14, 2018
	 */
    public Boolean saveCustomerForLine(String lineid,List<LogisticsLineCustomer> logisticsLineCustomers);

    /**
     * 删除线路的客户档案
     * @param lineid
     * @param customerids
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 14, 2018
     */
    public Boolean deleteCustomerForLine(String lineid,String customerids) throws Exception;

    /**
     * 添加线路车辆信息
     * @param lineid
     * @param carids
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 13, 2018
     */
    public Boolean addCarForLine(String lineid,String carids);

	/**
	 * 删除线路的车辆信息
	 * @param lineid
	 * @param carids
	 * @return java.lang.Boolean
	 * @throws
	 * @author luoqiang
	 * @date Mar 16, 2018
	 */
    public Boolean deleteCarForLine(String lineid,String carids);
}
