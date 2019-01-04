package com.hd.agent.storage.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.model.RejectBillDetail;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.model.SaleRejectEnterDetail;

/**
 * 销售退货入库单dao
 * @author chenwei
 */
public interface SaleRejectEnterMapper {
	/**
	 * 添加销售退货入库单信息
	 * @param saleRejectEnter
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public int addSaleRejectEnter(SaleRejectEnter saleRejectEnter);
	/**
	 * 添加销售退货入库单明细
	 * @param saleRejectEnterDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public int addSaleRejectEnterDetail(SaleRejectEnterDetail saleRejectEnterDetail);
	/**
	 * 通过来源单据编号获取销售退货入库单信息
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public List getSaleRejectEnterBySourceid(@Param("sourceid")String sourceid);

	/**
	 * 获取类型为直退的销售退货入库单信息（直退）
	 * @param receiptid
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public List getErectSaleRejectEnterByReceiptid(@Param("receiptid")String receiptid);
	/**
	 * 根据单据编号获取销售退货入库单信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public SaleRejectEnter getSaleRejectEnterByID(@Param("id")String id);
	/**
	 * 删除销售退货入库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public int deleteSaleRejectEnterByID(@Param("id")String id);
	/**
	 * 根据销售退货入库单编号 删除明细
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public int deleteSaleRejectEnterDetailByBillid(@Param("billid")String billid);
	/**
	 * 根据销售退货入库单编号 获取明细列表
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public List getSaleRejectEnterDetailList(@Param("billid")String billid);
	/**
	 * 获取销售退货入库单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public List showSaleRejectEnterList(PageMap pageMap);
	/**
	 * 获取销售退货入库单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public int showSaleRejectEnterCount(PageMap pageMap);
	/**
	 * 根据销售退货入库单获取退货金额
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public BigDecimal showSaleRejectEnterSum(String id);
	/**
	 * 根据查询条件获取查询合计退货金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public BigDecimal showSaleRejectEnterSumByQuery(PageMap pageMap);
	/**
	 * 审核销售退货入库单 审核通过后关闭
	 * @param id			
	 * @param status		状态
	 * @param ischeck		1验收0未验收
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public int auditSaleRejectEnter(@Param("id")String id,@Param("status")String status,@Param("ischeck")String ischeck,@Param("userid")String userid,@Param("username")String username,@Param("auditBusinessdate")String auditBusinessdate);

    /**
     * 更新发货单的成本价
     * @param id
     * @param isStorageAccount
     * @return
     */
    public int updateSaleRejectEnterDetailCostprice(@Param("id")String id,@Param("isStorageAccount")String isStorageAccount);
	/**
	 * 修改销售退货入库单
	 * @param saleRejectEnter
	 * @return
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public int editSaleRejectEnter(SaleRejectEnter saleRejectEnter);
	/**
	 * 根据销售退货通知单编号 修改销售退货入库单核销状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Sep 10, 2013
	 */
	public int updateSaleRejectEnterByRejectbill(@Param("id")String id);
	/**
	 * 根据销售发票编号 更新销售退货入库单明细 是否核销状态 
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public int updateSaleRejectEnterDetailIsWriteoffByReceipt(@Param("id")String billid,@Param("writeoffdate") String writeoffdate);
	
	/**
	 * 根据销售开票编号 更新销售退货入库单明细 是否核销状态 （申请开票）
	 * @param billid
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public int updateSaleRejectEnterDetailIsWriteoffByReceiptInvoiceBill(@Param("id")String billid);
	
	/**
	 * 根据销售发票编号 更新销售退货入库单明细 不为核销状态 
	 * @param billid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int updateSaleRejectEnterDetailBackWriteoffByReceipt(@Param("id")String billid);
	
	/**
	 * 根据销售开票编号 更新销售退货入库单明细 不为核销状态 （申请开票）
	 * @param billid
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public int updateSaleRejectEnterDetailBackWriteoffByReceiptInvoiceBill(@Param("id")String billid);
	
	/**
	 * 根据销售发票编号 更新销售退货入库单明细 是否核销状态 
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public int updateSaleRejectEnterDetailIsWriteoffBySalesInvoiceid(@Param("id")String id,@Param("writeoffdate") String writeoffdate);
	
	/**
	 * 根据销售开票编号 更新销售退货入库单明细 是否核销状态 （申请开票）
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public int updateSaleRejectEnterDetailIsWriteoffBySalesInvoicebillid(@Param("id")String id);
	
	/**
	 * 反核销，根据销售发票编号 更新销售退货入库单明细 不为核销状态
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int updateSaleRejectEnterDetailBackWriteoffBySalesInvoiceid(@Param("id")String id);
	/**
	 * 根据来源明细编号 更新销售退货入库单明细 是否核销状态 
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public int updateSaleRejectEnterDetailIsinvoiceByReject(@Param("billid")String invoiceid,@Param("isinvoice")String isinvoice,@Param("invoicedate") String invoicedate);
	
	/**
	 * 根据来源明细编号 更新销售退货入库单明细 是否实际开票 （申请开票）
	 * @param invoicebillid
	 * @param isinvoicebill
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public int updateSaleRejectEnterDetailIsinvoicebillByReject(@Param("billid")String invoicebillid,@Param("isinvoicebill")String isinvoicebill,@Param("invoicebilldate") String invoicebilldate);

	/**
	 * 根据来源明细编号 更新销售退货入库单明细 是否开票状态 
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 11, 2013
	 */
	public int updateSaleRejectEnterDetailIsinvoiceByInvoiceid(@Param("id")String id,@Param("isinvoice")String isinvoice,@Param("invoicedate") String invoicedate);
	
	/**
	 * 根据来源明细编号 更新销售退货入库单明细 是否实际开票 （申请开票）
	 * @param id
	 * @param isinvoicebill
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public int updateSaleRejectEnterDetailIsinvoicebillByInvoicebillid(@Param("id")String id,@Param("isinvoicebill")String isinvoicebill,@Param("invoicebilldate") String invoicebilldate);
	/**
	 * 获取相同来源单据的销售入库单未审核数量
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Nov 16, 2013
	 */
	public int getSaleRejectEnterNoAuditCount(@Param("sourceid")String sourceid);
	/**
	 * 根据上游单据编号 获取销售退货入库单列表
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public List getSaleRejectEnterListBySalerejectid(@Param("sourceid")String sourceid);
	/**
	 * 根据销售退货入库单编号 获取入库单开票金额
	 * @param salerejectid
	 * @return
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public SaleRejectEnterDetail getSaleRejectEnterSumInvoiceAmount(String salerejectid);
	
	/**
	 * 根据销售退货入库单编号 获取入库单开票金额 （申请开票）
	 * @param salerejectid
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public SaleRejectEnterDetail getSaleRejectEnterSumInvoicebillAmount(String salerejectid);
	/**
	 * 根据销售退货入库单编号 获取入库单核销金额
	 * @param salerejectid
	 * @return
	 * @author chenwei 
	 * @date Nov 19, 2013
	 */
	public SaleRejectEnterDetail getSaleRejectEnterSumWriteoffAmount(String salerejectid);
	
	/**
	 * 根据销售退货入库单编号 获取入库单未核销金额
	 * @param salerejectid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 6, 2014
	 */
	public SaleRejectEnterDetail getSaleRejectEnterSumUNWriteoffAmount(String salerejectid);
	
	/**
	 * 获取销售退货入库单列表
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-27
	 */
	public List getSaleRejectEnterListBy(Map map);
	
	/**
	 * 更新打印次数
	 * @param id
	 * @param printtimes
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public int updateOrderPrinttimes(SaleRejectEnter saleRejectEnter);
	/**
	 * 根据销售退货通知单编号 验收销售退货入库单
	 * @param rejectbillid
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public int updateSaleRejectEnterCheckByRejectbillid(@Param("rejectbillid")String rejectbillid,@Param("duefromdate")String duefromdate);
	/**
	 * 根据销售退货通知单编号 取消销售退货入库单
	 * @param rejectbillid
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public int updateSaleRejectEnterCheckCancelByRejectbillid(@Param("rejectbillid")String rejectbillid);
	/**
	 * 根据销售退货通知单明细编号 获取销售退货入库单明细信息
	 * @param rejectdetialid
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public SaleRejectEnterDetail getSaleRejectEnterDetailByRejectDetailid(@Param("rejectdetialid")String rejectdetialid);
	/**
	 * 更新销售退货入库单明细
	 * @param saleRejectEnterDetail
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public int updateSaleRejectEnterDetail(SaleRejectEnterDetail saleRejectEnterDetail);
	
	/**
	 * 更新明细中的来源单据编号来源单据明细编号
	 * @param oldbillid
	 * @param oldbilldetailid
	 * @param newbillid
	 * @param newbilldetailid
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public int updateSaleRejectEnterDetailIDs(@Param("oldbillid")String oldbillid,@Param("oldbilldetailid")String oldbilldetailid,@Param("newbillid")String newbillid,@Param("newbilldetailid")String newbilldetailid);
	/**
	 * 根据上游单据（退货通知单编号） 更新相关退货入库单客户
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Jan 10, 2014
	 */
	public int updateSaleRejectEnterCustomeridBySourceid(SaleRejectEnter saleRejectEnter);
	/**
	 * 根据上游单据（退货通知单编号） 更新相关退货通知单客户
	 * @param saleRejectEnter
	 * @return
	 * @author chenwei 
	 * @date Jan 10, 2014
	 */
	public int updateRejectBillCustomeridBySourceid(SaleRejectEnter saleRejectEnter);
	/**
	 * 根据销售退货通知单明细编号 更新销售退货入库单明细信息
	 * @param rejectBillDetail
	 * @return
	 * @author chenwei 
	 * @date Jan 10, 2014
	 */
	public int updateSaleRejectEnterDetailByRejectDetail(RejectBillDetail rejectBillDetail);
	/**
	 * 根据回单编号 更新相关销售退货入库单的关联回单编号
	 * @param receiptid
	 * @return
	 * @author chenwei 
	 * @date Jan 10, 2014
	 */
	public int updateSaleRejectEnterReceiptid(@Param("receiptid")String receiptid);
	/**
	 * 根据销售退货通知单明细编号 删除销售退货入库单明细信息
	 * @param rejectDetailid
	 * @return
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public int deleteSaleRejectEnterDetailByRejectDetail(@Param("rejectid")String rejectid,@Param("rejectdetailid")String rejectDetailid);
	/**
	 * 更新销售退货入库单客户和退货类型 验收状态
	 * @param rejectBill
	 * @return
	 * @author chenwei 
	 * @date Jan 15, 2014
	 */
	public int updateSaleRejectEnterCheckByReject(RejectBill rejectBill);
	/**
	 * 更新销售退货入库单客户和退货类型 取消验收状态
	 * @param rejectid
	 * @return
	 * @author chenwei 
	 * @date Apr 18, 2014
	 */
	public int updateSaleRejectEnterChecCancelkByRejectid(@Param("rejectid")String rejectid);
	/**
	 * 根据回单编号 更新销售退货入库单核销日期
	 * @param receiptid
	 * @return
	 * @author chenwei 
	 * @date Jan 20, 2014
	 */
	public int updateRejectEnterWriteoffByReceipt(@Param("receiptid")String receiptid);
	
	/**
	 * 反核销 根据回单编号 清空销售退货入库单核销日期
	 * @param receiptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int updateRejectEnterBackWriteoffByReceipt(@Param("receiptid")String receiptid);
	
	/**
	 * 根据发货单编号（来源单据）清除退货入库单中的发票号
	 * @param receiptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public int clearSaleRejectEnterInvoiceidByReceiptid(@Param("receiptid")String receiptid);
	
	/**
	 * 根据退货通知单编号（来源单据）清除退货入库单中的发票号
	 * @param rejectbillid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public int clearSaleRejectEnterInvoiceidByRejectbillid(@Param("rejectbillid")String rejectbillid);
	/**
	 * 更新销售退货入库单关联的商品库存批次编号
	 * @param id				销售退货入库单明细编号
	 * @param summarybatchid	商品库存批次编号
     * @param batchno           批次号
     * @param produceddate  生产日期
     * @param deadline          截止日期
     * @param storagelocationid 库位编号
	 * @return
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public int updateSaleRejectEnterDetailSummarybatchid(@Param("id")int id,@Param("summarybatchid")String summarybatchid,@Param("batchno")String batchno,@Param("produceddate")String produceddate,@Param("deadline")String deadline,@Param("storagelocationid")String storagelocationid);

    /**
     * 根据退货入库明细id修改退货价格
     * 1、取默认销售价（当时）（价格套价格或合同价）；
     * 2、多少天之内或多少月之内的最低销售价；
     * 3、最近一次销售价（交易价）
     * @param saleRejectEnterDetail
     * @return
     */
    public int updateSaleRejectEnterDetailPrice(SaleRejectEnterDetail saleRejectEnterDetail);

    /**
     * 根据客户编号获取 未核销的退货入库单列表
     * @param customerid
     * @return
     */
    public List<SaleRejectEnter> getSaleRejectEnterNoWriteoffListByCustomerid(@Param("customerid")String customerid);

    /**
     * 获取退货入库单中的商品列表
     * @param id
     * @return
     */
    public List<String> getSaleRejectEnterBrandListById(@Param("id")String id);

    /**
     * 根据销售退货单编号和品牌编号 更新应收日期
     * @param id
     * @param brandid
     * @param duefromdate
     * @return
     */
    public int updateSaleRejectEnterDetailDuefromdate(@Param("id")String id,@Param("brandid")String brandid,@Param("duefromdate")String duefromdate);

    /**
     * 更新销售退货单应收日期
     * @param id
     * @param duefromdate
     * @return
     * @author chenwei
     * @date Mar 22, 2014
     */
    public int updateSaleRejectEnterDuefromdate(@Param("id")String id,@Param("duefromdate")String duefromdate);

    /**
     * 更新销售退货通知单的应收日期
     * @param billid
     * @param duefromdate
     * @return
     * @author chenwei
     * @date Mar 22, 2014
     */
    public int updateSalesrejectDuefromdateByBillid(@Param("billid")String billid,@Param("duefromdate")String duefromdate);

    /**
     * 更新销售退货通知单明细的应收日期
     * @param billid
     * @param duefromdate
     * @return
     * @author chenwei
     * @date Mar 22, 2014
     */
    public int updateSalesRejectDetailDuefromdateByBillid(@Param("billid")String billid,@Param("brandid")String brandid,@Param("duefromdate")String duefromdate);

	/**
	 * 批量修改收货人
	 * @param id
	 * @param storager
	 * @return
	 * @author wanghongteng
	 * @date 2016-12-21
	 */
	public int editSalesRejectEnterStorager(@Param("id")String id,@Param("storager")String storager);
}