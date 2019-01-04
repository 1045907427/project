package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ReceiptMapper {
	
	/**
	 * 添加销售发货回单
	 * @param receipt
	 * @return
	 * @author zhengziyong 
	 * @date May 30, 2013
	 */
	public int addReceipt(Receipt receipt);
	
	/**
	 * 修改销售发货回单
	 * @param receipt
	 * @return
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public int updateReceipt(Receipt receipt);
	
	/**
	 * 更新回单状态
	 * @param receipt
	 * @return
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public int updateReceiptStatus(Receipt receipt);
	
	/**
	 * 更新回单参照状态
	 * @param isrefer
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public int updateReceiptRefer(String isrefer, String id);
	
	public int updateReceiptInvoice(String isinvoice, String canceldate, String id);
	
	/**
	 * 更新回单申请开票发票状态 (申请开票)
	 * @param isinvoicebill
	 * @param canceldate
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public int updateReceiptInvoicebill(String isinvoicebill, String canceldate, String id);
	
	public int updateReceiptDetailBack(ReceiptDetail detail);

    /**
     * 根据回单编号 更新回单的成本价等于发货单成本价
     * @param id
     * @return
     */
    public int updateReceiptDetailCostprice(@Param("id")String id);
	/**
	 * 删除回单信息
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public int deleteReceipt(String id);
	
	/**
	 * 获取销售发货回单
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public Receipt getReceipt(Map map);
	
	/**
	 * 通过出库单获取回单信息
	 * @param map
	 * @return
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public Receipt getReceiptBySaleout(Map map);
	
	/**
	 * 获取销售发货回单列表
	 * @param pageMap
	 * @return
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public List getReceiptList(PageMap pageMap);
	
	/**
	 * 获取销售发货回单记录条数
	 * @param pageMap
	 * @return
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public int getReceiptCount(PageMap pageMap);
	
	/**
	 * 添加销售发货回单明细
	 * @return
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public int addReceiptDetail(ReceiptDetail detail);
	/**
	 * 通过列表添加发货回单明细
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public int addReceiptDetailList(@Param("list")List list);
	/**
	 * 修改销售发货回单明细
	 * @param detail
	 * @return
	 * @author chenwei 
	 * @date 2014年11月13日
	 */
	public int updateReceiptDetail(ReceiptDetail detail);
	/**
	 * 通过回单编号获取明细列表
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public List getReceiptDetailListByReceipt(String id);
    /**
     * 通过回单编号获取明细列表
     * @param id
     * @return
     * @author zhengziyong
     * @date May 31, 2013
     */
    public List getReceiptDetailListByReceiptForRel(String id);
	/**
	 * 通过回单编号获取未开票的明细列表
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 2, 2014
	 */
	public List getReceiptDetailListNoInvoiceByReceipt(String id);
	/**
	 * 通过回单编码获取分商品明细列表
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 9, 2014
	 */
	public List getReceiptDetailListByReceiptGroupGoods(String id);
	/**
	 * 通过回单编号获取明细列表(合计品牌折扣)
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Dec 18, 2013
	 */
	public List getReceiptDetailListSumDiscount(String id);

    /**
     * 通过回单编号获取明细列表(合计品牌折扣)
     * @param id
     * @return
     * @author chenwei
     * @date Dec 18, 2013
     */
    public List getReceiptDetailListSumDiscountNoInvoice(String id);

    /**
     * 通过回单编号获取明细列表(合计品牌折扣)
     * @param id
     * @return
     * @author chenwei
     * @date Dec 18, 2013
     */
    public List getReceiptDetailListSumDiscountNoInvoicebill(String id);
	/**
	 * 通过回单编号删除明细信息
	 * @param id
	 * @return
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public int deleteReceiptDetailByReceiptId(String id);
	/**
	 * 通过回单编号删除明细信息(除折扣数据)
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Dec 18, 2013
	 */
	public int deleteReceiptDetailWithoutDiscountByReceiptId(String id);
	
	public Map getReceiptDetailTotal(String id);
	/**
	 * 获取回单中未开票金额
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Dec 16, 2013
	 */
	public BigDecimal getReceiptUnInvoiceAmount(@Param("id")String id);
	/**
	 * 获取回单明细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Feb 20, 2014
	 */
	public ReceiptDetail getReceiptDetail(String id);
	/**
	 * 获取回单明细信息
	 * @param id
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Feb 20, 2014
	 */
	public ReceiptDetail getReceiptDetailByBillidAndId(@Param("id")String id,@Param("billid")String billid);
	/**
	 * 获取回单与销售退货通知单合计列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Oct 9, 2013
	 */
	public List getReceiptAndRejectBillList(PageMap pageMap);
	
	/**
	 * 获取回单与销售退货通知单合计列表（申请开票）
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public List getReceiptAndRejectBillListForInvoiceBill(PageMap pageMap);
	/**
	 * 获取回单与销售退货通知单合计列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Oct 9, 2013
	 */
	public int getReceiptAndRejectBillCount(PageMap pageMap);
	
	/**
	 * 获取回单与销售退货通知单合计列表数量(申请开票)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public int getReceiptAndRejectBillCountForInvoiceBill(PageMap pageMap);
	/**
	 * 获取回单与销售退货通知单合计金额
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Feb 20, 2014
	 */
	public Map getReceiptAndRejectBillSumData(PageMap pageMap);
	
	/**
	 * 获取回单与销售退货通知单合计金额(申请开票)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public Map getReceiptAndRejectBillSumDataForInvoiceBill(PageMap pageMap);
	/**
	 * 获取客户的应收款，未开票金额，已开票金额等
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 29, 2014
	 */
	public List getCustomerInvoiceSumData(PageMap pageMap);
    /**
     * 获取客户的应收款，未开票金额，已开票金额等
     * @param pageMap
     * @return
     * @author chenwei
     * @date Apr 29, 2014
     */
    public List getCustomerInvoiceBillInfoSumData(PageMap pageMap);
	/**
	 * 根据客户编号 获取该客户的应收款等信息
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Apr 29, 2014
	 */
	public Map getCustomerInvoiceInfoData(@Param("customerid")String customerid);

	/**
	 * 获取回单与销售退货通知单 明细合计列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 23, 2013
	 */
	public List getReceiptAndRejectBillDetailList(PageMap pageMap);
	/**
	 * 根据销售发票编号 更新回单 修改是否开票状态
	 * @param id
	 * @param isinvoice			1开票 0未开票
	 * @return
	 * @author chenwei 
	 * @date Oct 10, 2013
	 */
	public int updateReceiptDetailIsinvoice(@Param("id")String id,@Param("isinvoice")String isinvoice);
	
	/**
	 * 根据销售发票编号 更新回单 修改是否实际开票1是0否
	 * @param id
	 * @param isinvoicebill
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public int updateReceiptDetailIsinvoicebill(@Param("id")String id,@Param("isinvoicebill")String isinvoicebill);
	/**
	 * 根据回单明细编号 修改是否核销
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Oct 10, 2013
	 */
	public int updateReceiptDetailIsinvoiceWriteoff(@Param("id")String id,@Param("writeoffdate") String writeoffdate);
	
	/**
	 * 根据销售开票编号 更新回单 修改是否核销
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 25, 2015
	 */
	public int updateReceiptDetailIsinvoicebillWriteoff(@Param("id")String id);
	
	/**
	 * 反核销 根据回单明细编号 修改核销状态为未核销
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 4, 2014
	 */
	public int updateReceiptDetailBackInvoiceWriteoff(@Param("id")String id);
	/**
	 * 获取客户应收款超账期金额
	 * @param customerid        客户编号
	 * @param date              日期
     * @param brandList         品牌列表 null表示全部
	 * @return
	 * @author chenwei 
	 * @date Oct 25, 2013
	 */
	public BigDecimal getReceivablePassDateAmountByCustomerid(@Param("customerid")String customerid,@Param("date")String date,@Param("brandList") List brandList);

    /**
     * 获取客户应收款超账期金额
     * @param customerid
     * @param date
     * @return
     * @author chenwei
     * @date Oct 25, 2013
     */
    public List<Map> getReceivablePassDateBrandListByCustomerid(@Param("customerid")String customerid,@Param("date")String date,@Param("brandList") List brandList);
	/**
	 * 获取客户应收款总额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Oct 25, 2013
	 */
	public BigDecimal getReceivableAmountByCustomerid(@Param("customerid")String customerid);

    /**
     * 获取总店应收款总额
     * @param pcustomerid
     * @return
     */
    public BigDecimal getReceivableAmountByPCustomerid(@Param("pcustomerid")String pcustomerid);
	/**
	 * 根据销售订单编号 获取回单信息
	 * @param saleorderid
	 * @return
	 * @author chenwei 
	 * @date Nov 9, 2013
	 */
	public Receipt getReceiptBySaleorderid(String saleorderid);
	/**
	 * 根据回单编号和品牌编号 获取回单中品牌折扣的明细列表
	 * @param billid
	 * @param brandid
	 * @return
	 * @author chenwei 
	 * @date Dec 18, 2013
	 */
	public List getReceiptDetailBrandDiscountList(@Param("billid")String billid,@Param("brandid")String brandid);
	/**
	 * 根据回单编号判断回单是否关联了直退通知单
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date 2015年1月17日
	 */
	public int getReceiptDetailRejectCount(@Param("billid")String billid);
	/**
	 * 根据上游发货单编号 删除回单明细
	 * @param billno
	 * @return
	 * @author chenwei 
	 * @date Jan 6, 2014
	 */
	public int deleteReceiptDetailByBillno(@Param("billno")String billno);
	/**
	 * 根据客户编号 获取未核销完的回单列表
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Jan 7, 2014
	 */
	public List getReceiptUnWriteoffListByCustomerid(@Param("customerid")String customerid);
	/**
	 * 根据客户编号 获取未核销完的回单列表（应收日期使用）
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public List getReceiptNoWriteoffListByCustomerid(@Param("customerid")String customerid);
	/**
	 * 根据订单编号删除回单
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public int deleteReceiptByOrderid(@Param("orderid")String orderid);
	/**
	 * 根据订单编号 删除回单明细
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public int deleteReceiptDetailByOrderid(@Param("orderid")String orderid);
	
	/**
	 * 获取对单明细编码根据回单编码和商品编码
	 * @param billid
	 * @param goodsid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 2, 2014
	 */
	public ReceiptDetail getReceiptDetailByBillidAndGoods(@Param("billid")String billid,@Param("goodsid")String goodsid);
	
	/**
	 * 回写销售回单是否生成交接单
	 * @param id
	 * @param ishand
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public int updateReceiptIsHand(@Param("id")String id,@Param("ishand")String ishand);

    /**
     * 根据发货单的发货回单编码更新发货回单明细列表是否实际开票状态
     * @param saleoutid
     * @param isinvoicebill
     * @return
     * @author panxiaoxiao
     * @date 2015-03-12
     */
    public int updateReceiptDetailIsinvoicebillCaseSaleout(@Param("saleoutid")String saleoutid, @Param("isinvoicebill")String isinvoicebill);
    /**
     * 根据发货单的发货回单编码更新发货回单明细列表是否实际开票状态
     * @param receiptid
     * @param isinvoicebill
     * @return
     * @author panxiaoxiao
     * @date 2015-03-13
     */
    public int getReceiptDetailListIsinvoicebillCount(@Param("receiptid")String receiptid, @Param("isinvoicebill")String isinvoicebill);
    /**
     * 根据预开票中的发货单编码获取发货回单编码列表数据
     * @param saleoutid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-13
     */
    public List getReceiptidListFromInvoicebillBySaleoutid(@Param("saleoutid")String saleoutid);

    /**
     * 根据发货单编码获取回单编号列表（用于检测发货回单是否已验收）
     * @param saleoutid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-13
     */
    public List<String> getReceiptidListBySaleoutid(@Param("saleoutid")String saleoutid);

    /**
     * 根据回单编码集计算回单金额合计
     * @param idArr
     * @return
     * @author panxiaoxiao
     * @date 2015-03-26
     */
    public Receipt getTotalReceiptAmount(@Param("idArr")String[] idArr);

    /**
     * 根据回单编码集获取回单明细列表数据
     * @param idArr
     * @return
     * @author panxiaoxiao
     * @date 2015-03-26
     */
    public List<ReceiptDetail> getReceiptDetailListByReceiptids(@Param("idArr")String[] idArr);

    /**
     * 根据配送单编码集获取回单编码列表
     * @param map
     * @return
     * @author panxiaoxiao
     * @date 2015-04-22
     */
    public List<String> getReceiptidsList(Map map);

    /**
     * 根据回单编码获取来源单据编码集
     * @param billid
     * @return
     * @date 2015-04-23
     */
    public List<String> getReceiptBillnosListByReceiptid(@Param("billid")String billid);

    /**
     * 导出销售退货通知单
     * @return
     * @author lin_xx
     * @date June 12, 2015
     */
    public List<Map<String, Object>> getReceiptListExport(PageMap pageMap);
    /**
     * 获取冲差单类型为回单冲差的所有回单及回单明细列表<br/>
     * map中参数：<br/>
     * cxid：冲差编号<br/>
     * cxidarr: 冲差编号字符串，多个编号，以 , 分隔<br/>
     * cxstatusarr: 冲差单状态,多个状态，以, 分隔<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年11月4日
     */
    public List getReceiptListForHDCXByMap(Map map);

	/**
	 * 更新开票时间(申请开票)
	 * @param map
	 * @return
	 */
	public int updateReceiptInvoicedate(Map map);

	/**
	 * 更新抽单日期（申请核销）
	 * @param id
	 * @param isinvoice
	 * @return
	 */
	public int updateSalesReceiptInvoicedate(@Param("id")String id,@Param("isinvoice")String isinvoice,@Param("invoicedate") String invoicedate);

    /**
     * 获取销售发货回单品牌列表
     * @param id
     * @return
     */
    public List getReceiptBrandListById(@Param("id")String id);

    /**
     * 根据销售发货回单编号和品牌编号 更新应收日期
     * @param billid
     * @param brandid
     * @param duefromdate
     * @return
     */
    public int updateReceiptDetailDuefromdateByBrandidAndBillid(@Param("billid")String billid,@Param("brandid")String brandid,@Param("duefromdate")String duefromdate);

	/**
	 * 根据回单编号修改回单申请状态
	 * @param billid
	 * @return
	 * @author pan_xx
	 * @date 2016-05-25
	 */
	public int updateReceiptIsvoiceByBillid(@Param("billid")String billid);

	/**
	 * 根据回单编号修改回单开票状态
	 * @param billid
	 * @return
	 * @author pan_xx
	 * @date 2016-05-25
	 */
	public int updateReceiptIsvoicebillByBillid(@Param("billid")String billid);

	/**
	 * 根据品牌、回单编号获取不为折扣的商品明细
	 * @param brandid
	 * @param receiptid
	 * @return
	 * @author pan_xx
	 * @date 2016-08-11
	 */
	public List<ReceiptDetail> getReceiptDetailNoDiscountList(@Param("brandid")String brandid, @Param("receiptid")String receiptid);

	/**
	 * 根据回单编号获取不同品牌商品合计
	 * @param receiptid
	 * @return
	 * @author pan_xx
	 * @date 2016-08-12
	 */
	public List<ReceiptDetail> getNoDiscountReceiptDetailListGroupBrand(@Param("receiptid")String receiptid);

	/**
	 * 根据Map中参数获取回单明细列表<br/>
	 * billid:单据编号<br/>
	 * billidarrs:单据编号组<br/>
	 * goodsid:商品编号<br/>
	 * taxtype:税种<br/>
	 * @param map
	 * @return java.util.List<com.hd.agent.sales.model.ReceiptDetail>
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public List<ReceiptDetail> getReceiptDetailListByMap(Map map);

	/**
	 * 根据Map中参数获取回单明细按折扣汇总列表<br/>
	 * billid:单据编号<br/>
	 * billidarrs:单据编号组<br/>
	 * taxtype:税种<br/>
	 * @param map
	 * @return java.util.List<com.hd.agent.sales.model.ReceiptDetail>
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public List<ReceiptDetail> getReceiptDetailListSumDiscountByMap(Map map);

	/**
	 * 根据回单中关联退货单编号获取退货单列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-6-8
	 */
	public List<Map> getrejectBillIdListByreject(String id);

	/**
	 * 获取回单列表
	 * @param map
	 * @return
	 * @throws
	 * @author zhanghonghui
	 * @date Nov 06, 2017
	 */
	public List<Receipt> getReceiptListBy(Map map);

	/**
	 * 更新打印次数
	 * @param receipt
	 * @return int
	 * @throws
	 * @author zhanghonghui
	 * @date Nov 06, 2017
	 */
	public int updateOrderPrinttimes(Receipt receipt);

    /**
     * 查看回单历史价格
     *
     * @param param
     * @return
     * @author limin
     * @date Jun 4, 2018
     */
	public List<Map> getReceiptBillHistoryGoodsPriceList(Map param);


	/**
	 * 根据客户编号和商品编码 获取历史交易信息(一年内的数据)
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @author limin
	 * @date Jun 81 2018
	 */
	public List<Map> getCustomerHisGoodsSalesList(@Param("customerid")String customerid,@Param("goodsid")String goodsid);

}