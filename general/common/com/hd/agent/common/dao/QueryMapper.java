/**
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-01-28 chenwei 创建版本
 */
package com.hd.agent.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.model.Query;

/**
 * 通用查询dao
 * @author chenwei
 */
public interface QueryMapper {
	/**
	 * 添加通用查询
	 * @param query
	 * @return
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	public int addQuery(Query query);
	/**
	 * 获取用户的通用查询
	 * @param userid
	 * @return
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	public List showUserQuery(@Param("userid")String userid,@Param("divid")String divid);
	/**
	 * 删除通用查询
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2013-1-12
	 */
	public int deleteQuery(@Param("id")String id);
}