package com.hd.agent.client.dao;

import java.util.List;

import com.hd.agent.client.model.ClientOffprice;
import com.hd.agent.client.model.ClientOffpriceLog;
import com.hd.agent.common.util.PageMap;


public interface ClientOffpriceLogMapper {
	
	/**
	 * 查询
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	List<ClientOffpriceLog> getOffPriceLogList(PageMap pageMap);
	
	/**
	 * 数量查询
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	int getOffPriceLogCount(PageMap pageMap);
	
	/**
	 * 日志新增
	 * @param log
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	int addOffPriceLog(ClientOffpriceLog log);
}