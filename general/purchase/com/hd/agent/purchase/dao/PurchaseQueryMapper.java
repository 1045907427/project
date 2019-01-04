/**
 * @(#)PurchaseQueryMapper.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 30, 2013 chenwei 创建版本
 */
package com.hd.agent.purchase.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageMap;

/**
 * 
 * 采购业务查询dao
 * @author chenwei
 */
public interface PurchaseQueryMapper {
	/**
	 * 采购进货退货明细列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 30, 2013
	 */
	public List showArrivalReturnDetailList(PageMap pageMap);
	/**
	 * 采购进货退货明细数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 30, 2013
	 */
	public int showArrivalReturnDetailCount(PageMap pageMap);
	/**
	 * 采购进货退货明细数量 合计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-10
	 */
	public Map showArrivalReturnDetailTotalSum(PageMap pageMap);
	/**
	 * 采购进货退货流水账
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-7
	 */
	public List showPurchaseJournalAccountList(PageMap pageMap);
}

