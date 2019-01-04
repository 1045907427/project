/**
 * @(#)IPurchaseForStorageService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-6-14 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.ext;

import java.util.List;

import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.model.PurchaseEnterDetail;
import com.hd.agent.storage.model.PurchaseRejectOutDetail;

/**
 * 采购与仓库接口
 * 
 * @author zhanghonghui
 */
public interface IPurchaseForStorageService  {
	/**
	 * 回写采购订单
	 * @param buyOrderid 采购订单编号
	 * @param list	明细列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-5
	 */
	public boolean updateBuyOrderDetailWriteBack(String buyOrderid,List<PurchaseEnterDetail> list) throws Exception;
	/**
	 * 删除时回写采购订单
	 * @param buyOrderid 采购订单编号
	 * @param list	明细列表
	 * @param isdelete 
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-5
	 */
	public boolean updateBuyOrderDetailDeleteWriteBack(String buyOrderid,List<PurchaseEnterDetail> list) throws Exception;
	/**
	 * 入库单审核通过后，回写采购订单
	 * @param buyOrderid
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-5
	 */
	public boolean updateBuyOrderDetailAuditWriteBack(String buyOrderid,List<PurchaseEnterDetail> list) throws Exception;
	/**
	 * 采购入库单反审，打开采购订单状态
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public boolean updateBuyOrderOpen(String id) throws Exception;
	/**
	 * 根据采购订单编码，查询出采购订单及采购订单明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-22
	 */
	public BuyOrder showBuyOrderAndDetail(String id) throws Exception;
	/**
	 * 采购入库单审核通过后，采购订单是否可以继续生产下游单据
	 * @param buyOrderid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-26
	 */
	public boolean getCanBuyOrderCreateNextAfterEnterAudit(String buyOrderid) throws Exception;
	/**
	 * 设置采购进货单引用 标志
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateArrivalOrderReferFlag(String id) throws Exception;
	/**
	 * 设置采购进货单未引用 标志
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateArrivalOrderUnReferFlag(String id) throws Exception;
	/**
	 * 入库单审核通过后，生成采购进货单,如果生成成功则返回编号，失败则返回空
	 * @param purchaseEnter
	 * @param purchaseEnterDetails
	 * @return 如果生成成功则返回编号，失败则返回空
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-10
	 */
	public String addArrivalOrder(PurchaseEnter purchaseEnter,List<PurchaseEnterDetail> purchaseEnterDetails) throws Exception;
    /**
     * 审核进货单
     * @param id
     * @return
     * @throws Exception
     */
    public boolean auditArrivalOrder(String id) throws Exception;
	/**
	 * 根据入库编号删除生成的进货单信息
	 * @param billno
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-15
	 */
	public boolean deleteArrivalOrderAndDetailByBillno(String billno) throws Exception;
	/**
	 * 回写退货通知单,
	 * @param returnOrderid
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-21
	 */
	public boolean updateReturnOrderWriteBack(String returnOrderid,List<PurchaseRejectOutDetail> list) throws Exception;
	/**
	 * 采购退货出库单反审，回写退货通知单
	 * @param returnOrderid
	 * @param list
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月24日
	 */
	public boolean updateReturnOrderOppauditWriteBack(String returnOrderid,List<PurchaseRejectOutDetail> list) throws Exception;
	/**
	 * 设置采购退货通知单引用 标志
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateReturnOrderReferFlag(String id) throws Exception;
	/**
	 * 设置采购退货未单引用 标志
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateReturnOrderUnReferFlag(String id) throws Exception;
	/**
	 * 将退货通知单打开处于审核状态，并将引用状态设置为不引用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-21
	 */
	public boolean updateReturnOrderOpen(String id) throws Exception;
	/**
	 * 根据采购退货通知单编号，显示采购退货通知单及其明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-24
	 */
	public ReturnOrder showReturnOrderAndDetail(String id) throws Exception;

	/**
	 * 更新退货通知单出库状态<br/>
	 * 出库状态：1出库，0未出库
	 * @param id 编号
	 * @param ckstatus 出库状态 
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-3-5
	 */
	public boolean updateReturnOrderCkstatusBy(String id,String ckstatus) throws Exception;
	/**
	 * 更新退货通知单 验收状态
	 * @param returnOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public boolean updateReturnOrderCheck(ReturnOrder returnOrder) throws Exception;
	/**
	 * 代配送定时任务 生成采购进货单
	 * @param purchaseEnter
	 * @param purchaseEnterDetailList
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月28日
	 */
	public String addCreateArrivalOrderByDeliveryEnter(PurchaseEnter purchaseEnter, List<PurchaseEnterDetail> purchaseEnterDetailList) throws Exception;
}

