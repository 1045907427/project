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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.storage.model.StorageDeliveryOutForJob;

/**
 * 采购退货通知单 dao
 * 
 * @author zhanghonghui
 */
public interface ReturnOrderMapper {
	/**
	 * 添加采购退货通知单
	 * @param returnOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public int insertReturnOrder(ReturnOrder returnOrder);
	/**
	 * 采购退货通知单分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public List getReturnOrderPageList(PageMap pageMap);
	/**
	 * 采购退货通知单分页计算
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public int getReturnOrderPageCount(PageMap pageMap);
	/**
	 * 采购退货通知单明细关联采购退货单通知单，统计含税金额、未税金额、税额
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-7
	 */
	public Map getReturnOrderDetailTotalSum(PageMap pageMap);
	/**
	 * 获取采购退货通知单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ReturnOrder getReturnOrder(@Param("id")String id);
	/**
	 * 获取采购退货通知单<br/>
	 * map中的参数：<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * cols: 字段权限<br/>
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ReturnOrder getReturnOrderBy(Map map);
	/**
	 * 更新采购退货通知单
	 * @param returnOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateReturnOrder(ReturnOrder returnOrder);
	/**
	 * 根据参数更新采购退货通知单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * returnOrder :采购退货通知单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * ischeck: 是否验收
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateReturnOrderBy(Map map);
	/**
	 * 删除采购退货通知单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deleteReturnOrder(@Param("id")String id);
	
	/**
	 * 更新采购退货通知单状态信息（审核、关闭、中止）<br/>
	 * 开票状态：<br/>
	 * isinvoice : 1开票，0未票,2核销，3开票中,4可以生成发票<br/>
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
	 * @param returnOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-10
	 */
	public int updateReturnOrderStatus(ReturnOrder returnOrder);
	/**
	 * 更新采购退货通知单发票状态
	 * @param isinvoice
	 * @param canceldate
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public int updateReturnOrderInvoice(@Param("isinvoice")String isinvoice, @Param("canceldate")String canceldate, @Param("id")String id) throws Exception;

	
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
	public int insertReturnOrderDetail(ReturnOrderDetail returnOrderDetail);
	/**
	 * 根据进货单编号删除采购退货通知单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deleteReturnOrderDetailByOrderid(@Param("orderid") String orderid);
	/**
	 * 更新采购明细
	 * @param returnOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateReturnOrderDetail(ReturnOrderDetail returnOrderDetail);
	/**
	 * 采购退货通知单明细分页列表
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List<ReturnOrderDetail> getReturnOrderDetailListBy(Map<String, Object> map); 
	/**
	 * 要把编号获取采购进货明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public ReturnOrderDetail getReturnOrderDetail(@Param("id")String id);
	/**
	 * 采购退货通知单回写
	 * @param returnOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-21
	 */
	public int updateReturnOrderDetailWriteBack(ReturnOrderDetail returnOrderDetail);
	/**
	 * 采购发票核销时，采购退货通知单回写
	 * @param returnOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-21
	 */
	public int updateReturnOrderDetailInvoiceWriteBack(ReturnOrderDetail returnOrderDetail);
	/**
	 * 要把采购退货通知单编号获取采购进货明细列表
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public List getReturnOrderDetailListByOrderid(@Param("orderid")String orderid);
	/**
	 * 采购退货通知单明细查询统计<br/>
	 * map中参数<br/>
	 * orderid :　采购进货单编号<br/>
	 * invoicerefer： 开票状态<br/>
	 * notinvoicerefer: 开票状态<br/>
	 * orderidarrs : 订单状态<br/>
	 * authDataSql  ：数据权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-20
	 */
	public int getReturnOrderDetailCountBy(Map map);
	/**
	 * 更新退货通知单明细中的开票引用状态<br/>
	 * @param id
	 * @param invoicerefer
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-27
	 */
	public int updateReturnOrderDetailInvoiceRefer(@Param("id")String id,@Param("invoicerefer") String invoicerefer);
	
	public Map getReturnOrderDetailTotal(String id);
	
	/**
	 * 根据采购退货通知单编码和商品编码获取采购退货通知单详情信息
	 * @param orderid
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 12, 2013
	 */
	public ReturnOrderDetail getReturnOrderDetailByOrderidAndGoodsId(@Param("orderid")String orderid,@Param("goodsid") String goodsid);

	public int updateReturnOrderIsrefer(@Param("id")String id,@Param("isrefer") String isrefer);
	
	public int updateReturnOrderDetailAuditnum(ReturnOrderDetail returnOrderDetail);
	
	public int updateReturnOrderStatusAndisinvoice(@Param("id")String id,@Param("isinvoice") String isinvoice,@Param("status") String status);
	
	/**
	 * 根据采购退货通知单编码获取总含税金额
	 * @param orderid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	public BigDecimal getReturnOrderTotalTaxAmountByOderid(@Param("orderid")String orderid);
	

	/**
	 * 根据编号查询退货通知单条数
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-3-10
	 */
	public int getReturnOrderCountById(String id);
	

	/**
	 * 获取退货通知单列表<br/>
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
	public List showReturnOrderListBy(Map map);
	/**
	 * 更新打印次数
	 * @param returnOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public int updateOrderPrinttimes(ReturnOrder returnOrder);

    /**
     * 更新采购退货通知单明细的核销状态
     * @param invoiceid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public int updateReturnOrderDetailUncancel(@Param("invoiceid")String invoiceid);
    /**
     * 更新采购退货通知单（可以更新所有字段）
     * @param returnOrder
     * @return
     * @author zhanghonghui 
     * @date 2015年6月18日
     */
    public int updateReturnOrderALL(ReturnOrder returnOrder);
    /**
     * 采购退货验收更新
     * @param returnOrderDetail
     * @return
     * @author zhanghonghui 
     * @date 2015年6月19日
     */
    public int updateReturnOrderDetailByCheck(ReturnOrderDetail returnOrderDetail);
    /**
     * 代配送定时任务 生成采购退货通知单
     * @param returnOrder
     * @return
     * @author huangzhiqian
     * @date 2015年9月30日
     */
	public int addreturnOrderForDeliveryJob(ReturnOrder returnOrder);
	/**
	 * 
	 * @param detail
	 * @author huangzhiqian
	 * @date 2015年9月30日
	 */
	public int addReturnOrderDetailByDeliveryOrderJob(ReturnOrderDetail detail);
}

