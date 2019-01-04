/**
 * @(#)OaExpensePushMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-7 limin 创建版本
 */
package com.hd.agent.oa.dao;

import com.hd.agent.oa.model.OaExpensePush;

/**
 * 费用冲差支付申请单Mapper
 * 
 * @author limin
 */
public interface OaExpensePushMapper {

	/**
	 * 查询费用冲差支付申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2015-1-7
	 */
	public OaExpensePush selectOaExpensePush(String id);
	
	/**
	 * 删除费用冲差支付申请单
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2015-1-7
	 */
	public int deleteOaExpensePush(String id);
	
	/**
	 * 添加费用冲差支付申请单
	 * @param push
	 * @return
	 * @author limin 
	 * @date 2015-1-7
	 */
	public int insertOaExpensePush(OaExpensePush push);
	
	/**
	 * 更新费用冲差支付申请单
	 * @param push
	 * @return
	 * @author limin 
	 * @date 2015-1-7
	 */
	public int updateOaExpensePush(OaExpensePush push);
}

