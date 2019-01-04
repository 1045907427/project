package com.hd.agent.oa.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.oa.model.OaAccess;

public interface OaAccessMapper {
	
	/**
	 * 通路费申请单登录
	 * @param access
	 * @return
	 * @author limin 
	 * @date 2014-9-29
	 */
	public int insertOaAccess(OaAccess access);
	
	/**
	 * 费用通路单申请单查询
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public OaAccess selectOaAccessInfo(@Param("id") String id);
	
	/**
	 * 费用通路单申请单更新
	 * @param access
	 * @return
	 * @author limin 
	 * @date 2014-9-30
	 */
	public int updateOaAccess(OaAccess access);
	
	/**
	 * 获取商品出数量
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-11-4
	 */
	public int selectGoodsSalesOut(Map map);
	
	/**
	 * 获取商品退货数量
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-11-28
	 */
	public int selectGoodsRejectEnter(Map map);
}