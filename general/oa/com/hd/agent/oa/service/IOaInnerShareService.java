/**
 * @(#)IOaInnerShareService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-24 limin 创建版本
 */
package com.hd.agent.oa.service;

import com.hd.agent.oa.model.OaInnerShare;

/**
 * 内部分摊申请单Service
 * 
 * @author limin
 */
public interface IOaInnerShareService {

	/**
	 * 添加内部分摊申请单
	 * @param share
	 * @return
	 * @author limin 
	 * @date 2014-11-24
	 */
	public int insertOaInnerShare(OaInnerShare share);

	/**
	 * 删除内部分摊申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-24
	 */
	public int deleteOaInnerShare(String id);

	/**
	 * 查询内部分摊申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-11-24
	 */
	public OaInnerShare selectOaInnerShare(String id);

	/**
	 * 更新内部分摊申请单
	 * @param share
	 * @return
	 * @author limin 
	 * @date 2014-11-24
	 */
	public int updateOaInnerShare(OaInnerShare share);
}

