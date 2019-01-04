/**
 * @(#)BuyApplyMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-6 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.PlannedOrder;
import com.hd.agent.purchase.model.PlannedOrderDetail;

/**
 * 采购计划单 dao
 * 
 * @author zhanghonghui
 */
public interface PlannedOrderMapper {
	/**
	 * 添加采购计划单
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public int insertPlannedOrder(PlannedOrder plannedOrder);
	/**
	 * 采购计划单分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public List getPlannedOrderPageList(PageMap pageMap);
	/**
	 * 采购计划单分页计算
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public int getPlannedOrderPageCount(PageMap pageMap);
	/**
	 * 采购计划单合计
	 * @param pageMap
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Mar 16, 2018
	 */
	public Map getPlannedOrderPageSum(PageMap pageMap);
	/**
	 * 获取采购计划单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public PlannedOrder getPlannedOrder(@Param("id")String id);
	/**
	 * 获取采购计划单<br/>
	 * map中的参数：<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * cols: 字段权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-30
	 */
	public PlannedOrder getPlannedOrderBy(Map map);
	/**
	 * 更新采购计划单
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updatePlannedOrder(PlannedOrder plannedOrder);
	/**
	 * 根据参数更新采购计划单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * plannedOrder :采购计划单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updatePlannedOrderBy(Map map);
	/**
	 * 删除采购计划单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deletePlannedOrder(@Param("id")String id);
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
	public int updatePlannedOrderStatus(PlannedOrder plannedOrder);
	
	
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
	public int insertPlannedOrderDetail(PlannedOrderDetail plannedOrderDetail);
	/**
	 * 根据订单编号删除采购计划单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deletePlannedOrderDetailByOrderid(@Param("orderid") String orderid);
	/**
	 * 更新采购计划单明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updatePlannedOrderDetail(PlannedOrderDetail plannedOrderDetail);
	/**
	 * 采购计划单明细回写
	 * @param plannedOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-30
	 */
	public int updatePlannedOrderDetailWriteBack(PlannedOrderDetail plannedOrderDetail);
	/**
	 * 采购计划单明细分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List<PlannedOrderDetail> getPlannedOrderDetailListBy(Map<String, Object> map); 
	/**
	 * 获取采购计划单明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public PlannedOrderDetail getPlannedOrderDetail(@Param("id")String id);
	/**
	 * 根据采购计划编码获取采购计划单明细列表
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public List getPlannedOrderDetailListByOrderid(@Param("orderid")String orderid);

	/**
	 * 根据采购计划单编号，查询采购明细中含税金额、无税金额、税额
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-12
	 */
	public Map getPlannedOrderDetailTotal(@Param("orderid")String orderid);


	/**
	 * 获取采购订单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui
	 * @date Mar 29, 2017
	 */
	public List<PlannedOrder> showPlannedOrderListBy(Map map);
	/**
	 * 更新打印次数
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui
	 * @date Mar 29, 2017
	 */
	public int updateOrderPrinttimes(PlannedOrder plannedOrder);

	/**
	 * 导出采购计划单明细
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author zhanghonghui
	 * @date Mar 29, 2017
	 */
	List<Map> getPlannedOrderDetailExport(PageMap pageMap);
}

