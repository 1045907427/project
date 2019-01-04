/**
 * @(#)ISalesOutReceiptService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 5, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service;

import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.model.SalesInvoiceBill;
import com.hd.agent.sales.model.*;
import com.hd.agent.storage.model.SaleRejectEnterDetail;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;

import java.util.List;
import java.util.Map;

/**
 * 
 * 提供给仓库的接口
 * @author zhengziyong
 */
public interface ISalesOutService {

	/**
	 * 更新销售发货通知单参照状态
	 * @param isrefer 参照状态1为已参照0为未参照
	 * @param id 发货通知单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public boolean updateDispatchBillRefer(String isrefer, String id) throws Exception;
	
	/**
	 * 出库单审核时关闭发货通知单
	 * @param id 发货通知单编号
	 * @param orderid 销售订单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public boolean updateDispatchBillClose(String id,String orderid) throws Exception;
	
	/**
	 * 出库单反审时打开发货通知单
	 * @param id 发货通知单编号
	 * @param orderid 销售订单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public boolean updateDispatchBillOpen(String id,String orderid) throws Exception;
	
	/**
	 * 出库单审核时回写发货通知单明细
	 * @param detailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public void updateDispatchBillDetailBack(List<SaleoutDetail> detailList) throws Exception;
	
	/**
	 * 出库单反审时清除发货通知单明细回写
	 * @param detailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public void updateClearDispatchBillDetailBack(List<SaleoutDetail> detailList) throws Exception;
	
	/**
	 * 获取发货通知单信息及明细信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public DispatchBill getDispatchBill(String id) throws Exception;
	
	/**
	 * 获取发货通知单明细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public DispatchBillDetail getDispatchBillDetail(String id) throws Exception;
	/**
	 * 获取发货通知单中品牌折扣的明细列表
	 * @param id
	 * @param brandid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 19, 2013
	 */
	public List getDispatchBillDetailBrandDiscountList(String id,String brandid) throws Exception;
	/**
	 * 出库单自动生成回单
	 * @param saleout
	 * @param detailList
	 * @return 返回自动生成回单的编号，返回null生成失败
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public String addReceiptAuto(Saleout saleout, List<SaleoutDetail> detailList) throws Exception;

    /**
     * 通过销售发货回单编号 审核销售发货回单
     * @param id
     * @return
     * @throws Exception
     */
    public boolean auditReceipt(String id) throws Exception;
	/**
	 * 出库单反审时删除回单
	 * @param id 出库单编号
	 * @return true删除成功或无下游回单可反审，false下游回单已审核通过无法删除及无法反审
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public boolean deleteReceipt(String id,String orderid) throws Exception;
	
	/**
	 * 更新回单的参照状态
	 * @param isrefer 参照状态1为已参照0为未参照
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public boolean updateReceiptRefer(String isrefer, String id) throws Exception;
	
	/**
	 * 关闭回单
	 * @param id 
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public boolean updateReceiptClose(String id) throws Exception;
	
	/**
	 * 将回单状态改为审核成功
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public boolean updateReceiptOpen(String id) throws Exception;
	
	/**
	 * 退货入库单回写回单
	 * @param detailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public void updateReceiptDetailBack(List<SaleRejectEnterDetail> detailList) throws Exception;
	
	/**
	 * 清除回写的回单信息
	 * @param detailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public void updateClearReceiptDetailBack(List<SaleRejectEnterDetail> detailList) throws Exception;
	
	/**
	 * 获取回单详细信息和明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public Receipt getReceipt(String id) throws Exception;
	/**
	 * 获取回单基本信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 17, 2014
	 */
	public Receipt getReceiptInfo(String id) throws Exception;
	/**
	 * 获取回单明细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public ReceiptDetail getReceiptDetail(String id) throws Exception;
	/**
	 * 根据单据编号和明细编号，获取回单明细详情
	 * @param id
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 20, 2014
	 */
	public ReceiptDetail getReceiptDetailInfo(String id,String billid) throws Exception;
	/**
	 * 根据回单编号和品牌编号 获取回单中品牌折扣的明细列表
	 * @param billid
	 * @param brandid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 18, 2013
	 */
	public List getReceiptDetailBrandDiscountList(String billid,String brandid) throws Exception;
	/**
	 * 更新退货通知单的参照状态
	 * @param isrefer 参照状态1为已参照0为未参照
	 * @param id 退货通知单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 17, 2013
	 */
	public boolean updateRejectBillRefer(String isrefer, String id) throws Exception;
	
	/**
	 * 关闭退货通知单
	 * @param id 
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public boolean updateRejectBillClose(String id) throws Exception;

    /**
     * 更新退货通知单应收日期
     * @param id
     * @param duefromdate
     * @return
     * @throws Exception
     */
    public boolean updateRejectBillDuefromdate(String id,String duefromdate) throws Exception;
	/**
	 * 将退货通知单状态改为审核成功
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public boolean updateRejectBillOpen(String id) throws Exception;
	
	/**
	 * 退货入库单回写退货通知单
	 * @param detailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public void updateRejectBillDetailBack(List<SaleRejectEnterDetail> detailList) throws Exception;
	
	/**
	 * 清除回写的退货通知单信息
	 * @param detailList
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public void updateClearRejectBillDetailBack(List<SaleRejectEnterDetail> detailList) throws Exception;
	
	/**
	 * 获取退货通知单详细信息和明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public RejectBill getRejectBill(String id) throws Exception;
	/**
	 * 获取退货通知单基本信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 17, 2014
	 */
	public RejectBill getRejectBillInfo(String id) throws Exception;
	/**
	 * 获取退货通知单明细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 18, 2013
	 */
	public RejectBillDetail getRejectBillDetail(String id) throws Exception;
	/**
	 * 获取退货通知单明细信息
	 * @param id
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 20, 2014
	 */
	public RejectBillDetail getRejectBillDetailInfo(String id,String billid) throws Exception;
	/**
	 * 更新退货通知单明细信息
	 * @param rejectBillDetail
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 10, 2014
	 */
	public boolean updateRejectBillDetailBack(RejectBillDetail rejectBillDetail) throws Exception;
	/**
	 * 根据明细编号 获取退货通知单详细信息
	 * @param detailid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 16, 2013
	 */
	public RejectBill getRejectBillByDetailid(String detailid) throws Exception;
	
	/**
	 * 更新回单发票状态
	 * @param isinvoice		1已开票2核销3可以开票4开票中5部分核销
	 * @param canceldate
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 1, 2013
	 */
	public boolean updateReceiptInvoice(String isinvoice, String canceldate, String id) throws Exception;
	
	/**
	 * 更新回单申请开票发票状态 (申请开票)
	 * @param isinvoicebill 1已开票3可以生成发票4开票中
	 * @param canceldate
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public boolean updateReceiptInvoicebill(String isinvoicebill, String canceldate, String id)throws Exception;
	
	/**
	 * 更新退货通知单发票状态
	 * @param isinvoice			1已开票2核销3可以开票4开票中5部分核销
	 * @param canceldate
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public boolean updateRejectBillInvoice(String isinvoice, String canceldate, String id) throws Exception;
	
	/**
	 * 更新退货通知单发票状态(申请开票)
	 * @param isinvoicebill 1已开票3可以生成发票4开票中
	 * @param canceldate
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public boolean updateRejectBillInvoicebill(String isinvoicebill, String canceldate, String id)throws Exception;
	/**
	 * 根据单据编号和单据类型,以及发票明细数据 更新单据的发票生成状态
	 * @param detailList		
	 * @param salesInvoice		销售发票
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 9, 2013
	 */
	public boolean updateReceiptAndRejectBillInvoice(List<String> detailList,SalesInvoice salesInvoice,String isinvoice) throws Exception;
	
	/**
	 * 根据单据编号和单据类型,以及发票明细数据 更新单据的发票生成状态(申请开票)
	 * @param detailList
	 * @param salesInvoiceBill     (申请开票)
	 * @param isinvoicebill
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public boolean updateReceiptAndRejectBillInvoiceBill(List<String> detailList,SalesInvoiceBill salesInvoiceBill,String isinvoicebill)throws Exception;
	/**
	 * 发票删除后 更新相关单据开票状态
	 * @param detailList
	 * @param salesInvoice
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public boolean updateReceiptAndRejectBillInvoiceByDelete(List<String> detailList,SalesInvoice salesInvoice) throws Exception;
	
	/**
	 * 开票删除后 更新相关单据开票状态（申请开票）
	 * @param detailList
	 * @param salesInvoiceBill
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 16, 2015
	 */
	public boolean updateReceiptAndRejectBillInvoicebillByDelete(List<String> detailList,SalesInvoiceBill salesInvoiceBill) throws Exception;
	/**
	 * 根据单据编号和单据类型,以及发票明细数据 更新单据的核销状态
	 * @param billList
	 * @param salesInvoice
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 10, 2013
	 */
	public boolean updateReceiptAndRejectBillInvoiceWriteoff(List<String> billList,SalesInvoice salesInvoice,String writeoffdate) throws Exception;
	


	/**
	 * 反核销 根据单据编号和单据类型,以及发票明细数据 更新单据的核销状态
	 * @param billList
	 * @param salesInvoice
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public boolean updateReceiptAndRejectBillInvoiceBackWriteoff(List<String> billList,SalesInvoice salesInvoice) throws Exception;
	
	/**
	 * 根据客户编号（包括总店） 获取未核销的回单列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 7, 2014
	 */
	public List getReceiptUnWriteoffListByCustomerid(String id) throws Exception;
	/**
	 * 判断退货通知单明细是否都关联回单 验收入库
	 * true:已全部关联 false未全部关联
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public boolean isRejectBillDetailRelate(String id) throws Exception;
	/**
	 * 根据回单编号获取关联的退货通知单信息
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public List getRejectBillByReceiptid(String receiptid) throws Exception;
	/**
	 * 根据客户编号获取该客户未核销的回单列表
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public List getReceiptNoWriteoffListByCustomerid(String customerid) throws Exception;
    /**
     * 根据发货编号判断发货回单是否已验收
     * @param saleoutid
     * @return true 已验收 false 未验收
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-13
     */
    public boolean checkIsDoneApplyWriteOffCaseReceipt(String saleoutid)throws Exception;

	/**
	 * 更新开票时间
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public boolean updateReceiptInvoicedate(Map map)throws Exception;

	/**
	 * 更新开票时间
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public boolean updateRejectBillInvoicedate(Map map)throws Exception;
}

