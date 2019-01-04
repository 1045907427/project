package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.model.RejectBillDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RejectBillMapper {
    
	public int addRejectBill(RejectBill bill);
	
	public List getRejectBillList(PageMap pageMap);
	
	public int getRejectBillCount(PageMap pageMap);
	
	public RejectBill getRejectBill(Map map);
	
	public RejectBill getRejectBillById(String id);
	
	public int getRejectBillCountById(String id);
	
	public int updateRejectBill(RejectBill bill);

	public int updateRejectBillVersion(RejectBill bill);

	public int deleteRejectBill(String id);
	
	public int updateRejectBillStatus(RejectBill bill);

	public int updateRejectBillStatusWithNoVersion(RejectBill bill);

	public int updateRejectBillRefer(String isrefer, String id);
	
	public int updateRejectBillInvoice(String isinvoice, String canceldate, String id);
	
	/**
	 * 更新退货通知单发票状态(申请开票)
	 * @param isinvoicebill
	 * @param canceldate
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public int updateRejectBillInvoicebill(String isinvoicebill, String canceldate, String id);
	
	public RejectBill getRejectBillByReceipt(Map map);
	
	//detail
	
	public int addRejectBillDetail(RejectBillDetail detail);
	
	public int deleteRejectBillDetailByBill(String id);
	
	public List getRejectBillDetailListByBill(String id);

    /**
     * 根据单据编号 获取回单管理退货通知单的商品明细
     * @param billArr
     * @param customerid
     * @return
     */
    public List getRejectBillDetailListByBillidsForRel(@Param("billArr")String[] billArr,@Param("customerid")String customerid);
	/**
	 * 获取退货通知单未抽单明细列表
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 2, 2014
	 */
	public List getRejectBillDetailListNoInvoiceByBill(String id);

    /**
     * 获取退货通知单未开票明细列表
     * @param id
     * @return
     * @author chenwei
     * @date May 2, 2014
     */
    public List getRejectBillDetailListNoInvoiceBillByBill(String id);
	/**
	 * 根据退货通知单编码数组字符串获取退货通知单明细列表（group by goodsid）
	 * @param ids
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 8, 2014
	 */
	public List getRejectBillDetailListByBills(String ids);
	
	public Map getRejectBillDetailTotal(String id);
	/**
	 * 获取退货通知单未开票金额
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Dec 16, 2013
	 */
	public BigDecimal getRejectBillUninvoiceAmount(@Param("id")String id);
	/**
	 * 获取退货通知单明细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Feb 20, 2014
	 */
	public RejectBillDetail getRejectBillDetail(String id);
	/**
	 * 获取退货通知单明细信息
	 * @param id
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Feb 20, 2014
	 */
	public RejectBillDetail getRejectBillDetailByBillidAndId(@Param("id")String id,@Param("billid")String billid);
	
	public int updateRejectBillDetailBack(RejectBillDetail detail);
	
	/**
	 * 更新打印次数
	 * @param rejectBill
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public int updateOrderPrinttimes(RejectBill rejectBill);
	/**
	 * 根据退货通知单明细编号 修改是否开票状态
	 * @param id
	 * @param isinvoice		1开票 0未开票
	 * @return
	 * @author chenwei 
	 * @date Oct 10, 2013
	 */
	public int updateRejectDetailIsinvoice(@Param("id")String id,@Param("isinvoice")String isinvoice);
	
	/**
	 * 根据退货通知单明细编号 修改是否实际开票 （申请开票）
	 * @param id
	 * @param isinvoicebill
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public int updateRejectDetailIsinvoicebill(@Param("id")String id,@Param("isinvoicebill")String isinvoicebill);
	/**
	 * 根据退货通知单明细编号 修改是否核销状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 10, 2013
	 */
	public int updateRejectDetailWriteoff(@Param("id")String id,@Param("writeoffdate") String writeoffdate);
	
	/**
	 * 根据退货通知单明细编号 修改是否核销状态(申请开票)
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public int updateRejectDetailWriteoffInvoiceBill(@Param("id")String id);
	
	/**
	 * 反核销 根据退货通知单明细编号 修改核销状态为未核销
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int updateRejectDetailBackWriteoff(@Param("id")String id);
	/**
	 * 获取退货通知单列表
	 * map中参数：<br/>
	 * idarrs：编号字符串组，类似1,2,3<br/>
	 * status: 状态<br/>
	 * @param map
	 * @return
	 */
	public List getRejectBillListBy(Map map);
	/**
	 * 根据客户编号 获取直退的销售退货通知单列表
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Oct 26, 2013
	 */
	public List getDirectRejectBillListByCustomerid(@Param("customerid")String customerid);
	/**
	 * 更新直退销售退货通知单明细是否被关联状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 29, 2013
	 */
	public int updateDirectRejectBillDetailIsrefer(@Param("id")String id,@Param("isrefer")String isrefer);
	/**
	 * 获取直退销售退货通知单 未关联的明细数量
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Oct 29, 2013
	 */
	public int getDirectRejectBillListUnreferCount(@Param("billid")String billid);
	/**
	 * 获取直退销售退货通知单 已关联的明细数量
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Oct 29, 2013
	 */
	public int getDirectRejectBillListReferCount(@Param("billid")String billid);
	/**
	 * 根据明细编号获取退货通知单详细信息
	 * @param detailid
	 * @return
	 * @author chenwei 
	 * @date Nov 16, 2013
	 */
	public RejectBill getRejectBillByDetailid(@Param("detailid")String detailid);
	/**
	 * 根据单据编号 获取未关联完的明细数量
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public int isRejectBillDetailRelate(@Param("billid")String billid);
	/**
	 * 根据回单编号获取退货通知单信息
	 * @param receiptid
	 * @return
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public List getRejectBillByReceiptid(@Param("receiptid")String receiptid);
	/**
	 * 清空退货通知单关联回单编号
	 * @param receiptid
	 * @return
	 * @author chenwei 
	 * @date Jan 13, 2014
	 */
	public int updateRejectBillClearReceipt(@Param("receiptid")String receiptid);
	/**
	 * 清空退货入库单关联回单编号
	 * @param receiptid
	 * @return
	 * @author chenwei 
	 * @date Jan 15, 2014
	 */
	public int updateSaleRejectEnterClearReceipt(@Param("receiptid")String receiptid);
	/**
	 * 更新退货通知单明细 关联信息
	 * @param receiptid
	 * @return
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public int updateRejectBillDetailIsrefer(@Param("receiptid")String receiptid);
	/**
	 * 删除退货通知单明细
	 * @param detailid
	 * @return
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public int deleteRejectBillDetailByDetailid(@Param("id")String detailid);
	/**
	 * 审核手机上传的销售退货通知单（暂存变为保存）
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	public int auditRejectBillPhone(Map map);
	/**
	 * 判断退货是否重复 
	 * @param id
	 * @return	Null 不重复 不为Null 重复
	 * @author chenwei 
	 * @date Mar 29, 2014
	 */
	public Map checkRejectRepeat(@Param("id")String id,@Param("customerid")String customerid);
	/**
	 * 根据手机上传校验码 获取退货通知单
	 * @param keyid
	 * @return
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public int getRejectBillByKeyid(String keyid);
	
	/**
	 * 回写销售退货通知单是否生成交接单
	 * @param id
	 * @param ishand
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 31, 2015
	 */
	public int updateRejectBillIsHand(@Param("id")String id,@Param("ishand")String ishand);
    /**
     * 导出销售退货通知单
     * @return
     * @author lin_xx
     * @date June 12, 2015
     */
    public List<Map<String, Object>> getRejectBillListExport(PageMap pageMap);

    /**
     * 多少天之内或多少月之内的最低销售价
     * @param customerid
     * @param goodsid
     * @param lowestdate
     * @return
     */
    public BigDecimal getRejectBillLowestPrice(@Param("customerid")String customerid, @Param("goodsid")String goodsid, @Param("lowestdate")String lowestdate);

    /**
     * 最近一次销售价（交易价）
     * @param customerid
     * @param goodsid
     * @return
     */
    public BigDecimal getRejectBillLastPrice(@Param("customerid")String customerid, @Param("goodsid")String goodsid);

    /**
     * 根据退货通知单明细id修改退货价格
     * 1、取默认销售价（当时）（价格套价格或合同价）；
     * 2、多少天之内或多少月之内的最低销售价；
     * 3、最近一次销售价（交易价）
     * @param rejectBillDetail
     * @return
     */
    public int updateSaleRejectBillDetailPrice(RejectBillDetail rejectBillDetail);

	/**
	 * 更新开票时间（申请开票）
	 * @param map
	 * @return
	 */
	public int updateSaleRejectBillInvoicedate(Map map);

	/**
	 * 更新抽单日期（申请核销）
	 * @param id
	 * @param isinvoice
	 * @return
	 */
	public int upageRejectBillInvoicedate(@Param("id")String id,@Param("isinvoice")String isinvoice,@Param("invoicedate") String invoicedate);

	/**
	 * 根据退货通知单编号修改申请状态
	 * @param billid
	 * @return
	 * @author pan_xx
	 * @date 2016-05-25
	 */
	public int updateRejectBillIsvoiceByBillid(@Param("billid")String billid);

	/**
	 * 根据退货通知单编号修改开票状态
	 * @param billid
	 * @return
	 * @author pan_xx
	 * @date 2016-05-25
	 */
	public int updateRejectBillIsvoicebillByBillid(@Param("billid")String billid);

	/**
	 * 更新退货通知单（直退）明细的开票状态
	 * @param invoiceid
	 * @param isinvoice
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public int updateRejectBillDetailIsinvoiceByInvoiceid(@Param("invoiceid")String invoiceid, @Param("isinvoice")String isinvoice);

	/**
	 * 更新退货通知单（直退）明细是否实际开票
	 * @param invoicebillid
	 * @param isinvoicebill
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public int updateRejectBillDetailIsinvoicebillByInvoicebillid(@Param("invoicebillid")String invoicebillid, @Param("isinvoicebill")String isinvoicebill);

	/**
	 * 更新退货通知单（直退）是否开票标记
	 * @param billid
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public int updateErectRejectBillIsvoiceByBillid(@Param("billid")String billid);

	/**
	 * 更新退货通知单（直退）是否实际开票
	 * @param billid
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public int updateErectRejectBillIsvoicebillByBillid(@Param("billid")String billid);

	/**
	 * 更新退货通知单（直退）明细的核销状态
	 * @param invoiceid
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public int updateRejectBillDetailIsWriteoffByReceipt(@Param("invoiceid")String invoiceid,@Param("writeoffdate") String writeoffdate);

	/**
	 * 根据发货回单编号更新发货通知单核销状态（直退）
	 * @param receiptid
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public int updateRejectBillWriteoffByReceipt(@Param("receiptid")String receiptid);

	/**
	 * 更新退货通知单（直退）明细的核销状态(开票)
	 * @param invoicebillid
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public int updateRejectBillDetailIsWriteoffByReceiptInvoiceBill(@Param("invoicebillid")String invoicebillid);

	/**
	 * 更新退货通知单（直退）明细的核销状态（反核销）
	 * @param invoiceid
	 * @return
	 * @author pan_xx
	 * @date 2016-12-11
	 */
	public int updateRejectBillDetailBackWriteoffByReceipt(@Param("invoiceid")String invoiceid);

	/**
	 * 批量修改收货人
	 * @param id
	 * @param storager
	 * @return
	 * @author wanghongteng
	 * @date 2016-12-21
	 */
	public int editSalesRejectBillStorager(@Param("id")String id,@Param("storager")String storager);

	/**
	 * 根据Map中参数获取销售退货明细列表<br/>
	 * billid:单据编号<br/>
	 * billidarrs:单据编号组<br/>
	 * goodsid:商品编号<br/>
	 * taxtype:税种<br/>
	 * @param map
	 * @return java.util.List<com.hd.agent.sales.model.RejectBillDetail>
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public List<RejectBillDetail> getRejectBillDetailListByMap(Map map);
}
