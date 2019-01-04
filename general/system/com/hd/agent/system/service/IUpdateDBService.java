/**
 * @(#)IUpdateDBService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月20日 chenwei 创建版本
 */
package com.hd.agent.system.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 数据库更新service
 * @author chenwei
 */
public interface IUpdateDBService {

	/**
	 * 更新数据库
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年8月20日
	 */
	public boolean updateDB() throws Exception;

	/**
	 * SQL 补丁更新列表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 20, 2017
	 */
	PageData getUpdateDBPageListData(PageMap pageMap) throws Exception;
}

