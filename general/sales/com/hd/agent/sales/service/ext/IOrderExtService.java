/**
 * @(#)IOrderExtService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 7, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.ext;

import com.hd.agent.sales.model.Demand;
import com.hd.agent.sales.model.DispatchBillDetail;

import java.util.List;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IOrderExtService {

	/**
	 * 要货申请生成订单
	 * @param demand
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Oct 24, 2013
	 */
	public String addOrderAuto(Demand demand) throws Exception;
	/**
	 * 根据部门把要货单拆分成多张订单
	 * @param demand
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月28日
	 */
	public String addOrderSplitByDept(Demand demand) throws Exception;
	
	/**
	 * 审核发货通知单回写订单明细
	 * @param billDetailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public void updateOrderDetailBack(List<DispatchBillDetail> billDetailList,String id) throws Exception;
	
	/**
	 * 发货通知单反审时清除订单商品明细回写的数据
	 * @param billDetailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public void updateClearOrderDetailBack(List<DispatchBillDetail> billDetailList) throws Exception;
	
	/**
	 * 发货通知单审核后关闭销售订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public boolean updateOrderClose(String id) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public boolean updateOrderOpen(String id) throws Exception;
	
}

