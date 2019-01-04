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
import com.hd.agent.purchase.model.LimitPriceOrder;
import com.hd.agent.purchase.model.LimitPriceOrderDetail;

/**
 * 采购调价单 dao
 * 
 * @author zhanghonghui
 */
public interface LimitPirceOrderMapper {
	/**
	 * 添加采购调价单
	 * @param limitPriceOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public int insertLimitPriceOrder(LimitPriceOrder limitPriceOrder);
	/**
	 * 采购调价单分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public List getLimitPriceOrderPageList(PageMap pageMap);
	/**
	 * 采购调价单分页计算
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public int getLimitPriceOrderPageCount(PageMap pageMap);
	/**
	 * 获取采购调价单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public LimitPriceOrder getLimitPriceOrder(@Param("id")String id);
	/**
	 * 获取采购调价单<br/>
	 * map中的参数：<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * cols: 字段权限<br/>
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public LimitPriceOrder getLimitPriceOrderBy(Map map);
	/**
	 * 获取采购调价单<br/>
	 * map中的参数：<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * cols: 字段权限<br/>
	 * notEffectData : 是否查询过期未关闭数据<br/>
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public List getLimitPriceOrderListBy(Map map);
	/**
	 * 更新采购调价单
	 * @param limitPriceOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateLimitPriceOrder(LimitPriceOrder limitPriceOrder);
	/**
	 * 根据参数更新采购调价单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * limitPriceOrder :采购调价单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * @param limitPriceOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateLimitPriceOrderBy(Map map);
	/**
	 * 删除采购调价单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deleteLimitPriceOrder(@Param("id")String id);
	
	/**
	 * 更新采购调价单状态信息（审核、关闭、中止）<br/>
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
	public int updateLimitPriceOrderStatus(LimitPriceOrder limitPriceOrder);
	
	
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
	public int insertLimitPriceOrderDetail(LimitPriceOrderDetail limitPriceOrderDetail);
	/**
	 * 根据进货单编号删除采购调价单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deleteLimitPriceOrderDetailByOrderid(@Param("orderid") String orderid);
	/**
	 * 更新采购明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateLimitPriceOrderDetail(LimitPriceOrderDetail limitPriceOrderDetail);
	/**
	 * 采购调价单明细分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List<LimitPriceOrderDetail> getLimitPriceOrderDetailListBy(Map<String, Object> map); 
	/**
	 * 要把编号获取采购进货明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public LimitPriceOrderDetail getLimitPriceOrderDetail(@Param("id")String id);
	
	/**
	 * 根据查询的业务日期 和限价单明细商品编号获取最早有效的限价单信息
	 * @param goodsid
	 * @param businessdate
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-13
	 */
	public LimitPriceOrderDetail getLimitPriceOrderDetailValid(@Param("goodsid")String goodsid,@Param("businessdate")String businessdate);
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
	public LimitPriceOrderDetail getLimitPriceOrderDetailValidBy(Map map);
}

