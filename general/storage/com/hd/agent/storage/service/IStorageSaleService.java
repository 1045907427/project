/**
 * @(#)IStorageSaleService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Oct 11, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 
 * 库存提供销售调用接口
 * @author chenwei
 */
public interface IStorageSaleService {
	/**
	 * 更新发货单明细 是否开票状态
	 * @param receiptDetail
	 * @param invoice
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public boolean updateSaleOutDetailIsinvoiceBySalesInvoiceid(String invoiceid,String invoice,String invoicedate) throws Exception;
	
	/**
	 * 更新发货单明细 是否实际开票1是0否(申请开票)
	 * @param invoicebillid
	 * @param isinvoicebill
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public boolean updateSaleOutDetailIsinvoicebillBySalesInvoicebillid(String invoicebillid,String isinvoicebill,String invoicebilldate) throws Exception;
	/**
	 * 更新发货单开票金额 已经发票号
	 * @param receiptid			回单编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public boolean updateSaleOutInvoiceAmount(String receiptid) throws Exception;
	
	/**
	 * 更新发货单开票金额 已经发票号（申请开票）
	 * @param receiptid    回单编号
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public boolean updateSaleOutInvoicebillAmount(String receiptid)throws Exception;

	/**
	 * 更新发货单开票日期（申请开票）
	 * @param receiptid
	 * @return
	 * @throws Exception
	 */
	public boolean updateSaleOutInvoicebilldate(String receiptid,String hasinvoicedate,String invoicebilldate)throws Exception;
	/**
	 * 更新发货单核销金额
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public boolean updateSaleOutWriteOffAmount(String receiptid) throws Exception;
	
	/**
	 * 发货单销售金额清零
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public boolean clearSaleOutWriteOffAmount(String receiptid)throws Exception;
	/**
	 * 通过销售发票编号更新发货单明细 是否核销状态
	 * @param invoiceid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public boolean updateSaleOutDetailIswriteoffByReceipt(String invoiceid,String writeoffdate) throws Exception;
	/**
	 * 反核销回写 通过销售发票编码更新发货单明细 不为核销状态
	 * @param invoiceid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public boolean updateSaleOutDetailBackWriteoffByReceipt(String invoiceid)throws Exception;
	
	/**
	 * 清除发货单中的发票号
	 * @param saleout
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public boolean clearSaleoutInvoiceidByReceiptid(String receiptid)throws Exception;
	
	/**
	 * 根据发货单编号（来源单据）清除退货入库单中的发票号
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public boolean clearSaleRejectEnterInvoiceidByReceiptid(String receiptid)throws Exception;
	
	/**
	 * 根据退货通知单编号（来源单据）清除退货入库单中的发票号
	 * @param rejectbillid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public boolean clearSaleRejectEnterInvoiceidByRejectbillid(String rejectbillid)throws Exception;
	
	/**
	 * 根据回单编号（来源编号）获取退货入库单列表数据
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public List getSaleRejectEnterListByReceipt(String receiptid)throws Exception;
	
	/**
	 * 根据销售退货通知单明细数据 更新退货入库单明细的核销状态
	 * @param invoiceid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public boolean updateRejectEnterDetailIswriteoffByReject(String invoiceid,String writeoffdate) throws Exception;
	
	/**
	 * 根据销售退货通知单明细数据 更新退货入库单明细的核销状态(申请开票)
	 * @param invoicebillid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public boolean updateRejectEnterDetailIswriteoffByRejectInvoiceBill(String invoicebillid)throws Exception;
	
	/**
	 * 反核销回写 根据销售退货通知单明细数据 更新退货入库单明细未核销状态
	 * @param invoiceid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public boolean updateRejectEnterDetailBackWriteoffByReject(String invoiceid)throws Exception;
	
	/**
	 * 根据销售退货通知单明细数据 更新退货入库单明细的开票状态
	 * @param rejectBillDetail
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public boolean updateRejectEnterDetailIsinvoiceBySalesInvoiceid(String invoiceid,String invoice,String invoicedate) throws Exception;
	
	/**
	 * 根据销售退货通知单明细数据 更新退货入库单明细的是否实际开票 （申请开票）
	 * @param invoicebillid
	 * @param isinvoicebill
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public boolean updateRejectEnterDetailIsinvoicebillBySalesInvoicebillid(String invoicebillid,String isinvoicebill,String invoicebilldate) throws Exception;
	/**
	 * 更新销售退货入库单的开票金额
	 * @param salerejectid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public boolean updateRejectEnterInvoiceAmount(String salerejectid) throws Exception;
	
	/**
	 * 更新销售退货入库单的开票金额(申请开票)
	 * @param salerejectid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public boolean updateRejectEnterInvoicebillAmount(String salerejectid) throws Exception;

	/**
	 * 更新销售退货入库单的开票日期(申请开票)
	 * @param salerejectid
	 * @return
	 * @throws Exception
	 */
	public boolean updateRejectEnterInvoicebilldate(String salerejectid,String hasinvoicedate,String invoicebilldate)throws Exception;
	/**
	 *  更新销售退货入库单的核销金额
	 * @param salerejectid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public boolean updateRejectEnterWriteoffAmount(String salerejectid) throws Exception;
	
	/**
	 * 反核销 清零销售退货入库单的核销金额
	 * @param salerejectid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public boolean clearRejectEnterWriteoffAmount(String salerejectid) throws Exception;
	/**
	 * 根据回单编号更新销售退货入库单（直退）核销日期
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 20, 2014
	 */
	public boolean updateRejectEnterWriteoffByReceipt(String receiptid) throws Exception;
	
	/**
	 * 反核销 根据回单编号清空销售退货入库单（直退）核销日期
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public boolean updateRejectEnterBackWriteoffByReceipt(String receiptid) throws Exception;
}

