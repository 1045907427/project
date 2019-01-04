/**
 * @(#)IPurchaseForAccountService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-7-11 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.ext;

import java.util.List;

import com.hd.agent.account.model.PurchaseInvoiceDetail;
import com.hd.agent.purchase.model.ArrivalOrder;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface IPurchaseForAccountService {
	/**
	 * 下游单据引用 后，设置采购进货单引用 标志
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateArrivalOrderReferFlag(String id) throws Exception;
	/**
	 * 游单据未引用 后，设置采购进货单未引用 标志
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateArrivalOrderUnReferFlag(String id) throws Exception;
	/**
	 * 下游单据核销后，关闭进货单，并回写商品档案中的最新采购总数量，采购总金额，库存平均价
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-12
	 */
	public boolean updateArrivalOrderClose(String id) throws Exception;
	
	/**
	 * 获取采购进货单，没有经过字段权限和数据权限处理
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public ArrivalOrder showArrivalOrder(String id) throws Exception;
	/**
	 * 显示采购进货单明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-3-6
	 */
	public ArrivalOrderDetail showArrivalOrderDetail(String id) throws Exception;
	/**
	 * 获取采购进货单及其明细，没有经过字段权限和数据权限处理
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-18
	 */
	public ArrivalOrder showArrivalOrderAndDetail(String id) throws Exception;
	/**
	 * 下游单据核销时，回写进货单
	 * @param billno
	 * @param list
	 * @param canceldate 核销时间
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-12
	 */
	public boolean updateArrivalOrderWriteBack(String billno,List<PurchaseInvoiceDetail> list, String canceldate) throws Exception;
	
	/**
	 * 设置采购进货单开票引用，更新数量，不更新金额，并回写采购入库单
	 * @param orderid 进货单
	 * @param invoiceDetailList 与进货单相关所有，开票明细合计
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-27
	 */
	public void updateArrivalOrderInvoiceReferWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception;

	/**
	 * 审核发票时，设置采购进货单，回写开票金额
	 * @param idlist
	 * @param invoiceDetailList 与进货单相关所有，开票明细合计
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-27
	 */
	public void updateArrivalOrderInvoiceAuditWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception;
	/**
	 * 根据采购退货通知单编号，显示采购退货通知单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-24
	 */
	public ReturnOrder showReturnOrder(String id) throws Exception;
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
	 * 根据采购退货通知单明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-3-6
	 */
	public ReturnOrderDetail showReturnOrderDetail(String id) throws Exception;
	/**
	 * 添加发票时，回写来源单据明细是否开票引用,回写未开票，已开票数量,发票状态
	 * @param idlist
	 * @param invoiceDetailList 与采购退货通知单相关所有，开票明细合计
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-27
	 */
	public void updateReturnOrderInvoiceReferWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception;

	/**
	 * 添加发票时，回写来源单据（应付款期初）是否开票引用
	 * @param sourceid
	 * @param invoiceDetailList
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-10-08
	 */
	public void updateBeginDueInvoiceReferWriteBack(String sourceid,List<PurchaseInvoiceDetail> invoiceDetailList)throws Exception;

	/**
	 * 审核发票时，设置采购退货通知单，回写开票金额
	 * @param idlist
	 * @param invoiceDetailList 与采购退货通知单相关所有，开票明细合计
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-27
	 */
	public void updateReturnOrderInvoiceAuditWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception;
	/**
	 * 采购发票核销时，回写采购退货通知单
	 * @param billno
	 * @param list 采购发票
	 * @param canceldate 核销时间
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-29
	 */
	public boolean updateReturnOrderWriteBack(String billno,List<PurchaseInvoiceDetail> list, String canceldate) throws Exception;

	/**
	 * 进货单开票后 更新采购入库单明细开票状态
	 * @param billid		采购入库单编号
	 * @param detailid		采购入库单明细编号
	 * @param isinvoice		1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseEnterDetailIsinvoice(String billid,String detailid,String isinvoice) throws Exception;
	/**
	 *  进货单核销后 更新采购入库单明细核销状态
	 * @param billid			采购入库单编号
	 * @param detailid			采购入库单明细编号
	 * @param iswriteoff		1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseEnterDetailIswriteoff(String billid,String detailid,String iswriteoff) throws Exception;	

	
	/**
	 * 退货通知单开票后 更新采购退货入库单明细开票状态
	 * @param billid		退货通知单编号
	 * @param detailid		退货通知单明细编号
	 * @param isinvoice		1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseRejectDetailIsinvoice(String billid,String detailid,String isinvoice) throws Exception;
	/**
	 *  退货通知单核销后 更新采购退货入库单明细核销状态
	 * @param billid			退货通知单编号
	 * @param detailid			退货通知单明细编号
	 * @param iswriteoff		1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseRejectDetailIswriteoff(String billid,String detailid,String iswriteoff) throws Exception;

	/**
	 * 审核采购发票时，回写应付款期初开票状态、开票金额、开票时间
	 * @param sourceid
	 * @param invoiceDetailList
	 * @param isinvoice
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-10-11
	 */
	public void updateBeginDueInvoiceAuditWriteBack(String sourceid,List<PurchaseInvoiceDetail> invoiceDetailList, String isinvoice)throws Exception;

	/**
	 * 采购发票核销时，回写应付款期初是否核销状态、核销日前、核销金额、核销人员
	 * @param id
	 * @param list
	 * @param canceldate
	 * @param iswriteoff
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-10-11
	 */
	public void updateBeginDueWriteBack(String id, List<PurchaseInvoiceDetail> list, String canceldate,String iswriteoff)throws Exception;
}

