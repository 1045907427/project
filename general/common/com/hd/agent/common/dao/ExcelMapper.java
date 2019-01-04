/**
 * @(#)ExcelMapper.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.common.dao;

import java.util.List;

import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface ExcelMapper {
	
	public List getList(PageMap pageMap);
	
	public List getAnotherList(PageMap pageMap);

    /**
     * 销售订单明细导出
     * @param pageMap
     * @return
     */
	public List getSaleOrderList(PageMap pageMap);
	
	public List getGoodsList(PageMap pageMap);
	
	public List getSupplierList(PageMap pageMap);
	
	public List getCustomerList(PageMap pageMap);

    /**
     * 其它出入库单明细导出
     * @author lin_xx
     * @date Feb 17,2016
     * @param pageMap
     * @return
     */
    public List getStorageOtherEnterAndOutList(PageMap pageMap);

	/**
	 * 获取借货还货单列表明细
	 * @author lin_xx
	 * @date Feb 17,2016
	 * @param pageMap
	 * @return
	 */
	public List getLendList(PageMap pageMap);


}

