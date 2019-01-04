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
import com.hd.agent.purchase.model.LimitPriceOrder;
import com.hd.agent.purchase.model.LimitPriceOrderDetail;

/**
 * 采购调价单 Service 接口
 * 
 * @author zhanghonghui
 */
public interface ILimitPriceOrderService {
	/**
	 * 向数据库添加一条采购调价单
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	//public boolean insertLimitPriceOrder(LimitPriceOrder limitPriceOrder) throws Exception;
	/**
	 * 添加采购调价单包括采购计划明细
	 * @param limitPriceOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-10
	 */
	public boolean addLimitPriceOrderAddDetail(LimitPriceOrder limitPriceOrder) throws Exception;
	/**
	 * 更新采购调价单及采购明细
	 * @param limitPriceOrder
	 * @return
	 * @throws Exception
	 */
	public boolean updateLimitPriceOrderAddDetail(LimitPriceOrder limitPriceOrder) throws Exception;
	/**
	 * 获取采购调价单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public LimitPriceOrder showLimitPriceOrderAndDetail(String id) throws Exception;
	/**
	 * 删除采购调价单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deleteLimitPriceOrderAndDetail(String id) throws Exception;
	/**
	 * 获取采购调价单明细，未经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public LimitPriceOrder showPureLimitPriceOrder(String id) throws Exception;
	/**
	 * 获取采购调价单,经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public LimitPriceOrder showLimitPriceOrder(String id) throws Exception;
	/**
	 * 获取采购调价单,经过数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public LimitPriceOrder showLimitPriceOrderByDataAuth(String id) throws Exception;
	/**
	 * 显示分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public PageData showLimitPriceOrderPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据参数更新采购调价单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * limitPriceOrder :采购调价单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * @param limitPriceOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean updateLimitPriceOrderBy(Map map) throws Exception;
	
	/**
	 * 获取与今天相比，失效的采购调价单<br/>
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public List getLimitPriceOrderUnEffectList() throws Exception;
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map auditLimitPriceOrder(String id) throws Exception;
	/**
	 * 反审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-3
	 */
	public Map oppauditLimitPriceOrder(String id) throws Exception;
	/**
	 * 更新采购调价单状态相关（审核、关闭、中止）<br/>
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
	 * @param limitPriceOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-13
	 */
	public boolean updateLimitPriceOrderStatus(LimitPriceOrder limitPriceOrder) throws Exception;
	
	//------------------------------------------------------------------------//
	//采购调价单详细
	//-----------------------------------------------------------------------//
	
	
	/**
	 * 添加采购调价单详细
	 * @param limitPriceOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public boolean insertLimitPriceOrderDetail(LimitPriceOrderDetail limitPriceOrderDetail) throws Exception;
	/**
	 * 采购调价单明细分页列表
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showLimitPriceOrderDetailListByOrderId(String Orderid) throws Exception;
	/**
	 * 采购调价单明细分页列表，不判断权限
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showPureLimitPriceOrderDetailListByOrderId(String orderid) throws Exception;
	
	/**
	 * 根据到货单编号删除采购调价单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deleteLimitPriceOrderDetailByOrderid(String orderid) throws Exception;
	/**
	 * 更新采购明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateLimitPriceOrderDetail(LimitPriceOrderDetail limitPriceOrderDetail) throws Exception;
	
	/**
	 * 根据查询的业务日期 和限价单明细商品编号获取最早有效的限价单信息
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-13
	 */
	public LimitPriceOrderDetail getLimitPriceOrderDetailValid(String goodsid,String businessdate) throws Exception;
	/**
	 * 查询最早有效的限价单信息<br/>
	 * map中参数 ： <br/>
	 * effectstartdate : 生效日期，字符串格式：yyyy-MM-dd<br/>
	 * effectenddate ： 有效截止日期，字符串格式：yyyy-MM-dd <br/>
	 * goodsid : 商品编号<br/>
	 * notcurid : 排除的编号<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-14
	 */
	public LimitPriceOrderDetail getLimitPriceOrderDetailValidBy(Map map) throws Exception;
}

