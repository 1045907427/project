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

import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ArrivalOrder;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 采购进货 dao
 * 
 * @author zhanghonghui
 */
public interface ArrivalOrderMapper {
	/**
	 * 添加采购进货单
	 * @param arrivalOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public int insertArrivalOrder(ArrivalOrder arrivalOrder);
	/**
	 * 采购进货单分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public List getArrivalOrderPageList(PageMap pageMap);
	/**
	 * 采购进货单分页计算
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public int getArrivalOrderPageCount(PageMap pageMap);

	/**
	 * 采购进货单与采购进货单明细联合查询，统计 含税金额，无税金额，税额
	 * 
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public Map getArrivalOrderDetailTotalSum(PageMap pageMap);
	/**
	 * 获取采购进货单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ArrivalOrder getArrivalOrder(@Param("id")String id);
	
	public Map getArrivalOrderDetailTotal(String id);
	/**
	 * 获取采购进货单<br/>
	 * map中的参数：<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * billno : 上游单据编号
	 * authDataSql ： 数据权限参数<br/>
	 * cols: 字段权限<br/>
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ArrivalOrder getArrivalOrderBy(Map map);
	/**
	 * 更新采购进货单
	 * @param arrivalOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateArrivalOrder(ArrivalOrder arrivalOrder);
	/**
	 * 根据参数更新采购进货单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * arrivalOrder :采购进货单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateArrivalOrderBy(Map map);
	/**
	 * 删除采购进货单
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deleteArrivalOrder(@Param("id")String id);
	/**
	 * 更新采购进货单状态信息（审核、关闭、中止）<br/>
	 * 审核时更新字段<br/>
	 * isrefer : 是否被引用，1引用，0未引用<br/>
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
	 * @param arrivalOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-10
	 */
	public int updateArrivalOrderStatus(ArrivalOrder arrivalOrder);
	
	/**
	 * 根据入库单编号获取进货信息列表
	 * @param billno
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-15
	 */
	public List getArrivalOrderListByBillno(@Param("billno")String billno);
	/**
	 * 根据入库单编号获取进货信息
	 * @param billno
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-15
	 */
	public ArrivalOrder getArrivalOrderByBillno(@Param("billno")String billno);
	
	/**
	 * 更新采购进货单发票状态
	 * @param isinvoice
	 * @param canceldate
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public int updateArrivalOrderInvoice(@Param("isinvoice")String isinvoice, @Param("canceldate")String canceldate, @Param("id")String id);
	
	//------------------------------------------------------------------------//
	//采购进货单详细
	//-----------------------------------------------------------------------//
	/**
	 * 添加采购进货单详细
	 * @param arrivalOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public int insertArrivalOrderDetail(ArrivalOrderDetail arrivalOrderDetail);
	/**
	 * 根据进货单编号删除采购进货单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int deleteArrivalOrderDetailByOrderid(@Param("orderid") String orderid);
	/**
	 * 更新采购明细
	 * @param arrivalOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateArrivalOrderDetail(ArrivalOrderDetail arrivalOrderDetail);
	/**
	 * 采购进货单明细分页列表
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List<ArrivalOrderDetail> getArrivalOrderDetailListBy(Map<String, Object> map); 
	/**
	 * 根据进货单编号查询采购进货明细列表
	 * @param orderid 采购进货订单编号
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-15
	 */
	public List<ArrivalOrderDetail> getArrivalOrderDetailListByOrderid(@Param("orderid")String orderid);
	/**
	 * 要把编号获取采购进货明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public ArrivalOrderDetail getArrivalOrderDetail(@Param("id")String id);
	/**
	 * 采购退货通知单审核通过后，回写采购进货单	
	 * @param arrivalOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-10
	 */
	public int updateArrivalOrderDetailWriteBack(ArrivalOrderDetail arrivalOrderDetail);
	/**
	 * 采购进货单明细查询统计<br/>
	 * map中参数<br/>
	 * orderid :　采购进货单编号<br/>
	 * authDataSql  ：数据权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-20
	 */
	public int getArrivalOrderDetailCountBy(Map map);
	/**
	 * 更新进货单明细中的开票引用状态<br/>
	 * @param id
	 * @param invoicerefer
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-7-27
	 */
	public int updateArrivalOrderDetailInvoiceRefer(@Param("id")String id,@Param("invoicerefer") String invoicerefer);

	public ArrivalOrderDetail getArrivalOrderDetailByOrderidAndGoodsId(@Param("orderid")String orderid,@Param("goodsid") String goodsid);

	public int updateArrivalOrderIsrefer(@Param("id")String id,@Param("isrefer") String isrefer);
	
	public int updateArrivalOrderDetailAuditnum(ArrivalOrderDetail arrivalOrderDetail);
	
	public int updateArrivalOrderStatusAndisinvoice(@Param("id")String id,@Param("isinvoice") String isinvoice,@Param("status") String status);
	
	/**
	 * 根据采购到货单编码获取总含税金额
	 * @param orderid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	public BigDecimal getArrivalOrderTotalTaxAmountByOderid(@Param("orderid")String orderid);
	/**
	 * 更新打印次数
	 * @param arrivalOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public int updateOrderPrinttimes(ArrivalOrder arrivalOrder);
	/**
	 * 获取采购进货单列表<br/>
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
	public List showArrivalOrderListBy(Map map);
	/**
	 * 根据编号查询进货单条数
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-3-10
	 */
	public int getArrivalOrderCountById(String id);
	/**
	 * 更新采购进货单明细
	 * @param arrivalOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-26
	 */
	public int updateArrivalOrderDetailRemark(ArrivalOrderDetail arrivalOrderDetail);
    /**
     * 更新采购进货单明细的核销状态
     * @param invoiceid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public int updateArrivalOrderDetailUncancel(String invoiceid);
    

    /**
     * 根据商品编码显示历史价格表数据页面
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016年3月16日
     */
    public List<Map> getPurchaseHistoryGoodsPriceList(Map map);

    /**
     * 根据进货单编号数组 获取供应商的合计信息
     * @param ids
     * @return
     */
    public List<Map> getSupplierArrivalSumData(@Param("list")List ids);

	/**
	 * 根据进货单编号数组 获取供应商的合计信息,生成第三方账套凭证的时候要按单据和商品汇总
	 * @param ids
	 * @return
	 */
	public List<Map> getSupplierArrivalSumDataForThird(@Param("list")List ids);


    /**
     * 获取最新批次价
     *
     * @param goodsid
     * @param batchno
     * @return
     * @author limin
     * @date Oct 28, 2016
     */
    public Map selectLastestBuyPriceByGoodsAndBatchno(@Param("goodsid") String goodsid, @Param("batchno") String batchno);

	/**
	 * 更新单据金额为分摊后的金额
	 * @param arrivalOrderDetail
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public int updateArrivalOrderChange(ArrivalOrderDetail arrivalOrderDetail);

	/**
	 * 修改进货单的金额为分摊前的金额
	 * @param id
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public int updateArrivalOrderChangeById(@Param("id")String id);
}

