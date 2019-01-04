/**
 * @(#)IClientOffPriceLogService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月7日 huangzhiqian 创建版本
 */
package com.hd.agent.client.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface IOffPriceLogService {
	
	/**
	 * 日志查询
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	PageData getOffPriceLogList(PageMap pageMap)throws Exception;

}

