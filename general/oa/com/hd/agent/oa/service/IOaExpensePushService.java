/**
 * @(#)IOaExpensePushService.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-7 limin 创建版本
 */
package com.hd.agent.oa.service;

import java.util.List;

import com.hd.agent.oa.model.OaExpensePush;
import com.hd.agent.oa.model.OaExpensePushDetail;

/**
 * 费用冲差支付申请单Service
 * 
 * @author limin
 */
public interface IOaExpensePushService {

	/**
	 * 查询费用冲差明细List
	 * 
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-7
	 */
	public List<OaExpensePushDetail> selectOaExpensePushDetailList(String billid) throws Exception;
	
	/**
	 * 查询费用冲差申请单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-8
	 */
	public OaExpensePush selectOaExpensePush(String id) throws Exception;

	/**
	 * 新增费用冲差申请单
	 * @param push
	 * @param list
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-8
	 */
	public int insertOaExpensePush(OaExpensePush push, List<OaExpensePushDetail> list) throws Exception;

	/**
	 * 编辑费用冲差申请单
	 * @param push
	 * @param list
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-8
	 */
	public int updateOaExpensePush(OaExpensePush push, List<OaExpensePushDetail> list) throws Exception;

	/**
	 * 删除费用冲差申请单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2015-1-16
	 */
	public int deleteOaExpensePush(String id) throws Exception;
}

