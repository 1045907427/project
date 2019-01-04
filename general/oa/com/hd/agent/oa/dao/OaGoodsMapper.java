/**
 * @(#)OaGoodsMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-7-9 limin 创建版本
 */
package com.hd.agent.oa.dao;

import com.hd.agent.oa.model.OaGoods;

/**
 * 
 * 
 * @author limin
 */
public interface OaGoodsMapper {

	/**
	 * 添加新品登录单
	 * @param goods
	 * @return
	 * @author limin 
	 * @date 2014-7-16
	 */
	public int insertOaGoods(OaGoods goods);
	
	/**
	 * 查询新品登录单
	 * @param id 新品登录单号
	 * @return
	 * @author limin 
	 * @date 2014-7-16
	 */
	public OaGoods selectOaGoods(String id);
	
	/**
	 * 修改新品登录单
	 * @param goods
	 * @return
	 * @author limin 
	 * @date 2014-7-25
	 */
	public int updateOaGoods(OaGoods goods);
	
	/**
	 * 根据id删除新客户登录单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-7-29
	 */
	public int deleteOaGoods(String id);
}

