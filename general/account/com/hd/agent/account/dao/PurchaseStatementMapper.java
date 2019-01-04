/**
 * @(#)PurchaseStatementMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.PurchaseStatement;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 付款对账单dao（核销dao）
 * @author panxiaoxiao
 */
public interface PurchaseStatementMapper {

	/**
	 * 新增采购发票付款单对账单
	 * @param purchaseStatement
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public int addPurchaseStatement(PurchaseStatement purchaseStatement);
	
	/**
	 * 根据付款单编码获取对账单明细列表
	 * @param supplierid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public List getPurchaseStatementList(@Param("supplierid")String supplierid);
	
	/**
	 * 获取对账单明细列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public List getPurchaseStatementListPage(PageMap pageMap);
	
	/**
	 * 获取对账单明细数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 13, 2013
	 */
	public int getPurchaseStatementCount(PageMap pageMap);

    /**
     * 删除核销生成的对账单
     * @param supplierid
     * @param billid
     * @param billtype
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public int deletePurchaseStatement(@Param("supplierid")String supplierid, @Param("billid")String billid, @Param("billtype")String billtype);
}

