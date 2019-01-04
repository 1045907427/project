/**
 * @(#)BuyOrderMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-21 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;

/**
 * 采购订单 dao
 * 
 * @author zhanghonghui
 */
public interface BuyOrderMapper {
	/**
	 * 添加采购订单
	 * @param buyOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public int insertBuyOrder(BuyOrder buyOrder);
	/**
	 * 采购订单分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public List getBuyOrderPageList(PageMap pageMap);
	/**
	 * 采购订单分页计算
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public int getBuyOrderPageCount(PageMap pageMap);
	/**
	 * 获取采购订单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public BuyOrder getBuyOrder(@Param("id")String id);
	/**
	 * 获取采购订单<br/>
	 * map中的参数：<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * cols: 字段权限<br/>
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public BuyOrder getBuyOrderBy(Map map);
	/**
	 * 更新采购订单
	 * @param buyOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateBuyOrder(BuyOrder buyOrder);
	/**
	 * 根据参数更新采购订单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * buyOrder :采购订单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * @param buyOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateBuyOrderBy(Map map);
	/**
	 * 删除采购订单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deleteBuyOrder(@Param("id")String id);
	/**
	 * 更新采购订单状态信息（引用、审核、关闭、中止）<br/>
	 * 审核时更新字段<br/>
	 * isrefer : 引用状态：1引用，0未引用<br/>
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
	public int updateBuyOrderStatus(BuyOrder buyOrder);
	/**
	 * 根据上游单据编号获取采购订单
	 * @param billno
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-17
	 */
	public BuyOrder getBuyOrderByBillno(@Param("billno")String billno);
	
	
	//------------------------------------------------------------------------//
	//采购订单详细
	//-----------------------------------------------------------------------//
	/**
	 * 添加采购订单详细
	 * @param buyOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public int insertBuyOrderDetail(BuyOrderDetail buyOrderDetail);
	/**
	 * 根据订单编号删除采购订单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deleteBuyOrderDetailByOrderid(@Param("orderid") String orderid);
	/**
	 * 更新采购明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateBuyOrderDetail(BuyOrderDetail buyOrderDetail);
	/**
	 * 采购订单明细分页列表<br/>
	 * map中参数：<br/>
	 * orderid : 采购订单编号<br/>
	 * authDataSql : 数据权限<br/>
	 * showcanbuygoods : 有值时查询可购买商品,判断未入库数量<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List<BuyOrderDetail> getBuyOrderDetailListBy(Map<String, Object> map);
	/**
	 * 根据采购订单编号查询采购明细列表
	 * @param orderid 采购订单编号
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-15
	 */
	public List<BuyOrderDetail> getBuyOrderDetailListByOrderid(@Param("orderid")String orderid);
	/**
	 * 获取采购订单明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public BuyOrderDetail getBuyOrderDetail(@Param("id")String id);
	/**
	 * 采购订单回写
	 * @param buyOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-8
	 */
	public int updateBuyOrderDetailWriteBack(BuyOrderDetail buyOrderDetail);
	/**
	 * 根据采购订单编号，查询采购明细中含税金额、无税金额、税额
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-12
	 */
	public Map getBuyOrderDetailTotal(@Param("orderid")String orderid);
	/**
	 * 导出采购订单明细
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-14
	 */
	public List getBuyOrderDetailExport(PageMap pageMap);
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
	 * @date 2013-10-16
	 */
	public List showBuyOrderListBy(Map map);
	/**
	 * 更新打印次数
	 * @param buyOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public int updateOrderPrinttimes(BuyOrder buyOrder);
}

