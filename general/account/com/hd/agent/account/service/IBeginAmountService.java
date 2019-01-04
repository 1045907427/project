/**
 * @(#)IBeginAmountService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月31日 chenwei 创建版本
 */
package com.hd.agent.account.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.BeginAmount;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 客户应收款期初接口
 * @author chenwei
 */
public interface IBeginAmountService {
	/**
	 * 添加客户应收款期初
	 * @param beginAmount		客户应收款期初
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public boolean addBeignAmount(BeginAmount beginAmount) throws Exception;
	/**
	 * 获取客户应收款期初数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public PageData showBeignAmountList(PageMap pageMap) throws Exception;
	/**
	 * 根据编号获取客户应收款期初数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public BeginAmount getBeginAmountByID(String id) throws Exception;
	/**
	 * 客户应收款期初修改
	 * @param beginAmount
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月31日
	 */
	public boolean editBeginAmount(BeginAmount beginAmount) throws Exception;
	/**
	 * 删除客户应收款期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	public boolean deleteBeginAmount(String id) throws Exception;
	/**
	 * 审核客户应收款期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	public Map auditBeignAmount(String id) throws Exception;
	/**
	 * 反审客户应收款期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月1日
	 */
	public Map oppauditBeignAmount(String id) throws Exception;
	/**
	 * 导入客户应收款期初
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月3日
	 */
	public Map importBeignAmount(List<Map> list) throws Exception;
}

