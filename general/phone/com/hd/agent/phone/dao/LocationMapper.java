package com.hd.agent.phone.dao;

import com.hd.agent.phone.model.Location;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LocationMapper {
    
	public Location getLocation(String username);
	
	public Location getLocationByUserId(String userid);
	
	public int addLocation(Location location);
	
	public int updateLocation(Location location);
	/**
	 * 根据日期或者用户定位信息
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date 2014年7月4日
	 */
	public List getLocationList(@Param("date")String date,@Param("sql")String sql);
	
	public int addLocationHistory(Location location);
	
	public List getLocationHistoryList(Map map);

    /**
     *
     * @param map
     * @author lin_xx
     * @date June 13, 2016
     */
    public List getLocationHistoryListByInfo(Map map);
	/**
	 * 根据用户编号 和时间 判断定位信息
	 * @param userid
	 * @param time
	 * @return
	 * @author chenwei 
	 * @date 2015年8月26日
	 */
	public int checkLocationByUseridAndTime(@Param("userid")String userid,@Param("time")String time);
	/**
	 * 根据条件查询行程 
	 * @param map   begindate,enddate,userid
	 * @return
	 * @author huangzhiqian
	 * @date 2016年1月20日
	 */
	public List<Location> getLocationListByDateAndUserId(Map map);

	/**
	 * 根据确定的日期,用户Id获取当天行程
	 * @param dateStr
	 * @param userId
	 * @return
	 * @author huangzhiqian
	 * @date 2016年1月20日
	 */
	public List<Location> getLocationsByexactDateAndUserId(@Param("date")String dateStr, @Param("userid")String userId);
	/**
	 * 查看这个业务员这天是否有行程数据
	 * @param dateStr
	 * @param userId
	 * @return
	 * @author huangzhiqian
	 * @date 2016年1月20日
	 */
	public int getRouteDistanceCount(@Param("date")String dateStr, @Param("userid")String userId);

    /**
     * 删除行程
     * @param dateStr 必填  日期
     * @param userId 可选  用户编号
     * @return
     */
    public int deleteRouteDistance(@Param("date")String dateStr, @Param("userid")String userId);
}