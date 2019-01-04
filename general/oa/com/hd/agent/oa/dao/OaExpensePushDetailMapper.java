/**
 * @(#)OaExpensePushDetailMapper.java
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

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.oa.model.OaExpensePushDetail;

/**
 * 费用冲差支付申请单明细Mapper
 * 
 * @author limin
 */
public interface OaExpensePushDetailMapper {

	/**
	 * 查询费用冲差支付申请单明细List
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2015-1-7
	 */
	public List<OaExpensePushDetail> selectOaExpensePushDetailList(@Param("billid") String billid);
	
	/**
	 * 删除费用冲差支付申请单明细List
	 * @param billid
	 * @return
	 * @author limin 
	 * @date 2015-1-7
	 */
	public int deleteOaExpensePushDetail(@Param("billid") String billid);
	
	/**
	 * 插入费用冲差支付申请单明细
	 * @param detail
	 * @return
	 * @author limin 
	 * @date 2015-1-8
	 */
	public int insertOaExpensePushDetail(OaExpensePushDetail detail);
}

