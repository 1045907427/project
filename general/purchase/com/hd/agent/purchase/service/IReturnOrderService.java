/**
 * @(#)IArrivalOrderMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-21 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.storage.model.StorageDeliveryOutForJob;

/**
 *	采购退货通知单服务接口 
 * 
 * @author zhanghonghui
 */
public interface IReturnOrderService {
	/**
	 * 向数据库添加一条采购退货通知单
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	//public boolean insertReturnOrder(ReturnOrder returnOrder) throws Exception;
	/**
	 * 添加采购退货通知单包括采购计划明细
	 * @param returnOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-10
	 */
	public Map addReturnOrderAddDetail(ReturnOrder returnOrder) throws Exception;
	/**
	 * 更新采购退货通知单及采购明细
	 * @param returnOrder
	 * @return
	 * @throws Exception
	 */
	public boolean updateReturnOrderAddDetail(ReturnOrder returnOrder) throws Exception;
	/**
	 * 获取采购退货通知单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ReturnOrder showReturnOrderAndDetail(String id) throws Exception;
	/**
	 * 删除采购退货通知单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deleteReturnOrderAndDetail(String id) throws Exception;
	/**
	 * 获取采购退货通知单明细，未经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ReturnOrder showPureReturnOrder(String id) throws Exception;
	/**
	 * 获取采购退货通知单,经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ReturnOrder showReturnOrder(String id) throws Exception;
	
	/**
	 * 获取采购退货通知单详细信息和明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 7, 2013
	 */
	public ReturnOrder getReturnOrder(String id)throws Exception;
	/**
	 * 获取采购退货通知单,经过数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ReturnOrder showReturnOrderByDataAuth(String id) throws Exception;
	/**
	 * 显示分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public PageData showReturnOrderPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据参数更新采购退货通知单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * returnOrder :采购退货通知单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * @param returnOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean updateReturnOrderBy(Map map) throws Exception;
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map auditReturnOrder(String id) throws Exception;
	/**
	 * 反审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-3
	 */
	public Map oppauditReturnOrder(String id) throws Exception;
	/**
	 * 工作流审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-31
	 */
	public boolean auditWorkflowReturnOrder(String id) throws Exception;
	/**
	 * 设置采购退货通知单引用 标志
	 * @param id
	 * @param isrefer 1表示引用，0表示未引用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateReturnOrderRefer(String id,String isrefer) throws Exception;
	/**
	 * 更新采购退货通知单状态信息（审核、关闭、中止）<br/>
	 * 审核时更新字段<br/>
	 * status : 6工作游戏、3审核、2反审为保存<br/>
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
	public boolean updateReturnOrderStatus(ReturnOrder returnOrder) throws Exception;
	//------------------------------------------------------------------------//
	//采购退货通知单详细
	//-----------------------------------------------------------------------//
	
	
	/**
	 * 添加采购退货通知单详细
	 * @param returnOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public boolean insertReturnOrderDetail(ReturnOrderDetail returnOrderDetail) throws Exception;
	/**
	 * 采购退货通知单明细分页列表
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showReturnOrderDetailListByOrderId(String Orderid) throws Exception;
	/**
	 * 采购退货通知单明细分页列表，不判断权限
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showPureReturnOrderDetailListByOrderId(String orderid) throws Exception;
	
	/**
	 * 根据到货单编号删除采购退货通知单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deleteReturnOrderDetailByOrderid(String orderid) throws Exception;
	/**
	 * 更新采购明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateReturnOrderDetail(ReturnOrderDetail returnOrderDetail) throws Exception;
	/**
	 * 采购退货通知单明细分页列表<br/>
	 * map中参数：<br/>
	 * orderid : 采购订单编号<br/>
	 * ischeckauth : 是否检查数据权限,默认检查，0时不检查<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List<ReturnOrderDetail> showReturnOrderDetailListBy(Map<String, Object> map) throws Exception;
	/**
	 * 根据退货通知单编号，获取退货通知单及期明细，数据未做字段权限及数据权限检查
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-24
	 */
	public ReturnOrder showPureReturnOrderAndDetail(String id) throws Exception;
	/**
	 * 提交采购退货单到工作流
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
	public boolean submitReturnOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;

	/**
	 * 根据采购退货通知单编码和商品编码获取采购退货通知单详情信息
	 * @param orderid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2013
	 */
	public ReturnOrderDetail getReturnOrderDetailByOrderidAndGoodsId(String orderid,String goodsid)throws Exception;
	/**
	 * 根据退货通知单明细编码获取明细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-9-22
	 */
	public ReturnOrderDetail showPureReturnOrderDetail(String id) throws Exception;

	/**
	 * 获取退货通知单列表<br/>
	 * map中参数:<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showReturnOrderListBy(Map map) throws Exception;
	/**
	 * 更新打印次数
	 * @param buyOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public boolean updateOrderPrinttimes(ReturnOrder returnOrder) throws Exception;
	/**
	 * 保存验收
	 * @param returnOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public Map updateReturnOrderCheck(ReturnOrder returnOrder) throws Exception;
	/**
	 * 取消验收
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public Map updateReturnOrderCheckCancel(String ids) throws Exception;
	/**
	 * 批量验收
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public Map updateReturnOrderMutiCheck(String idarrs) throws Exception;
	/**
	 * 代配送客户出库生成采购退货通知单,出库单
	 * @param job
	 * @param jobDetail
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月30日
	 */
	public boolean addReturnOrderByDelivery(StorageDeliveryOutForJob job,List<StorageDeliveryOutForJob> jobDetails)throws Exception;
	 /**
	  * 导入采购退货通知单
	  * @author lin_xx
	  * @date 2016/12/13
	  */
	 public Map importReturnOrderList(Map<String,List> supplierMap) throws Exception ;

	/**
	 * 导入采购退货通知单（普通版）
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-21
	 */
	 public Map importReturnOrder(List<Map<String,Object>> list)throws Exception ;
}

