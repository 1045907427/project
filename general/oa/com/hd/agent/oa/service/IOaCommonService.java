/**
 * @(#)IOaCommonService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-12-12 limin 创建版本
 */
package com.hd.agent.oa.service;

import java.util.Map;

/**
 * OA模块Common Service
 * 
 * @author limin
 */
public interface IOaCommonService {

	/**
	 * 添加数据变更记录
	 * @param trace
	 * @return
	 * @author limin 
	 * @date 2014-12-12
	 */
	public boolean addTrace(Map trace);
}

