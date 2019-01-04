/**
 * @(#)IPurchaseQueryService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 30, 2013 chenwei 创建版本
 */
package com.hd.agent.purchase.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;

/**
 * 
 * 采购业务查询service
 * @author chenwei
 */
public interface IPurchaseQueryService {
	/**
	 * 获取采购进货退货明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 30, 2013
	 */
	public PageData showArrivalReturnDetailList(PageMap pageMap) throws Exception;
	/**
	 * 采购进货退货流水账
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-7
	 */
	public List showPurchaseJournalAccountList(PageMap pageMap) throws Exception;
}

