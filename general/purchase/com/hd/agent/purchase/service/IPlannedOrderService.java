/**
 * @(#)IBuyApply.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-6 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.PlannedOrder;
import com.hd.agent.purchase.model.PlannedOrderDetail;
import com.hd.agent.report.model.StorageBuySaleReport;

/**
 * 采购计划单 Service 接口
 * 
 * @author zhanghonghui
 */
public interface IPlannedOrderService {
	/**
	 * 向数据库添加一条采购计划单
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	//public boolean insertPlannedOrder(PlannedOrder plannedOrder) throws Exception;
	/**
	 * 添加采购计划单包括采购计划明细
	 * @param plannedOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-10
	 */
	public boolean addPlannedOrderAddDetail(PlannedOrder plannedOrder) throws Exception;
	/**
	 * 更新采购计划及采购计划单明细
	 * @param plannedOrder
	 * @return
	 * @throws Exception
	 */
	public boolean updatePlannedOrderAddDetail(PlannedOrder plannedOrder) throws Exception;
	/**
	 * 获取采购计划及明细信息,未做字段权限或数据权限检查
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public PlannedOrder showPurePlannedOrderAndDetail(String id) throws Exception;
	/**
	 * 获取采购计划单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public PlannedOrder showPlannedOrderAndDetail(String id) throws Exception;
	/**
	 * 删除采购计划单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deletePlannedOrderAndDetail(String id) throws Exception;
	/**
	 * 获取采购计划单，未经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-30
	 */
	public PlannedOrder showPurePlannedOrder(String id) throws Exception;
	/**
	 * 获取采购计划单，经过字段和数据权限检查
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public PlannedOrder showPlannedOrder(String id) throws Exception;
	/**
	 * 获取采购计划单,经过数据权限检查
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-30
	 */
	public PlannedOrder showPlannedOrderByDataAuth(String id) throws Exception;
	/**
	 * 显示分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public PageData showPlannedOrderPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据参数更新采购计划单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * plannedOrder :采购计划单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean updatePlannedOrderBy(Map map) throws Exception;
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map auditPlannedOrder(String id) throws Exception;
	/**
	 * 反审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map oppauditPlannedOrder(String id) throws Exception;
	/**
	 * 工作流审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean auditWorkflowPlannedOrder(String id) throws Exception;
	
	/**
	 * 更新采购计划单状态信息（审核、关闭、中止）<br/>
	 * 审核时更新字段<br/>
	 * status : 3审核、2反审为保存<br/>
	 * audituserid : 审核用户编号<br/>
	 * auditusername ： 审核用户姓名<br/>
	 * audittime : 审核时间<br/>
	 * 中止时更新字段<br/>
	 * status : 5 <br/>
	 * stopuserid : 中止用户编号<br/>
	 * stopusername : 中止用户姓名<br/>
	 * stoptime : 中止时间<br/>
	 * 关闭时更新字段<br/>
	 * status : 4 <br/>
	 * closetime : 关闭时间<br/>
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-10
	 */
	public boolean updatePlannedOrderStatus(PlannedOrder plannedOrder) throws Exception;
	
	
	//------------------------------------------------------------------------//
	//采购计划单详细
	//-----------------------------------------------------------------------//
	
	
	/**
	 * 添加采购计划单详细
	 * @param plannedOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public boolean insertPlannedOrderDetail(PlannedOrderDetail plannedOrderDetail) throws Exception;
	/**
	 * 采购计划单明细分页列表
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showPlannedOrderDetailListByOrderId(String orderid) throws Exception;
	/**
	 * 只获取采购计划单明细信息，不判断权限
	 * @param orderid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-30
	 */
	public List showPurePlannedOrderDetailListByOrderId(String orderid) throws Exception;
	/**
	 * 根据订单编号删除采购计划单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deletePlannedOrderDetailByOrderid(String orderid) throws Exception;
	/**
	 * 更新采购计划单明细
	 * @param plannedOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean updatePlannedOrderDetail(PlannedOrderDetail plannedOrderDetail) throws Exception;
	
	/**
	 * 采购计划单明细回写<br/>
	 * 更新参数：orderunitnum, orderauxnum,ordertaxamount, unorderunitnum, unorderauxnum, unordertaxamount<br/>
	 * 条件参数 ：id 编号
	 * @param plannedOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-30
	 */
	public boolean updatePlannedOrderDetailWriteBack(PlannedOrderDetail plannedOrderDetail) throws Exception;
	/**
	 * 获取采购计划明细信息,未做字段权限或数据权限检查
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public PlannedOrderDetail showPurePlannedOrderDetail(String id) throws Exception;
	
	/**
	 * 提交采购计划单到工作流
	 * @param title
	 * @param userId
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public boolean submitPlannedOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;
	/**
	 * 通过进销存报表 生成采购计划单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public Map addPlannedOrderByReport(List<StorageBuySaleReport> list,String remark,String queryForm) throws Exception;
	
	/**
	 * 导入采购计划单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 1, 2014
	 */
	public Map importPlannedOrder(List<Map<String, Object>> list)throws Exception;

	/**
	 * 获取采购计划单列表<br/>
	 * map中参数:<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date Mar 29, 2017
	 */
	public List showPlannedOrderListBy(Map map) throws Exception;
	/**
	 * 更新打印次数
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui
	 * @date Mar 29, 2017
	 */
	public boolean updateOrderPrinttimes(PlannedOrder plannedOrder) throws Exception;

	/**
	 * 导出采购计划单明细
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 29, 2017
	 */
	List getPlannedOrderDetailExport(PageMap pageMap) throws Exception;
}

