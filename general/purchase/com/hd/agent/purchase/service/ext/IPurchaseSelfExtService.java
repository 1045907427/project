/**
 * @(#)IPurchaseExtService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-6-17 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.ext;

import java.util.List;

import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.purchase.model.PlannedOrder;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface IPurchaseSelfExtService {
	/**
	 * 设置采购计划单关闭状态
	 * @param id 订单编号
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-15
	 */
	public boolean updatePlannedOrderCloseStatus(String id) throws Exception;
	
	/**
	 * 设置采购计划审核通过状态
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-15
	 */
	public boolean updatePlannedOrderAuditStatus(String id) throws Exception;
	/**
	 * 设置采购计划单引用状态
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-27
	 */
	public boolean updatePlannedOrderReferFlag(String id) throws Exception;
	/**
	 * 设置采购计划单未引用状态
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-27
	 */
	public boolean updatePlannedOrderUnReferFlag(String id) throws Exception;
	/**
	 * 采购计划单审核通过后，回写采购计划单
	 * @param billno
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-5
	 */
	public boolean updatePlannedOrderDetailWriteBack(String billno,List<BuyOrderDetail> list) throws Exception;
	/**
	 * 采购计划单反审通过后，反回写采购计划单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-8
	 */
	public boolean updatePlannedOrderDetailReWriteBack(String billno,List<BuyOrderDetail> list) throws Exception;
	/**
	 * 根据采购计划单编号，组装成采购订单信息
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-17
	 */
	public BuyOrder showBuyOrderAndDetailReferByBillno(String billno) throws Exception;
	
	
	

	/**
	 * 审核后自动生成采购订单
	 * @param plannedOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public String addCreateBuyOrderByPlanAudit(PlannedOrder plannedOrder) throws Exception;

	/**
	 * 根据采购计划单编号，删除采购订单
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-17
	 */
	public boolean deleteBuyOrderAndDetailByBillno(String billno) throws Exception;
	
}

