package com.hd.agent.phone.dao;

import java.util.List;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.phone.model.RouteDistance;

public interface RouteDistanceMapper {
	
	public int getDistanceDetailCount(String userid, String adddate);
	
	public int addDistance(RouteDistance distance);
	
	public List getDistanceList(PageMap pageMap);
	
	public int getDistanceCount(PageMap pageMap);
	
	/**
	 * 获取每日行程报表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 10, 2014
	 */
	public List getRouteReportList(PageMap pageMap);
	
	/**
	 * 获取每日行程报表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 10, 2014
	 */
	public int getRouteReportCount(PageMap pageMap);
}