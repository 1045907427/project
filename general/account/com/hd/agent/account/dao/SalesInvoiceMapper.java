package com.hd.agent.account.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.account.model.SalesInvoiceDetail;
import com.hd.agent.common.util.PageMap;

/**
 * 销售发票dao
 * @author chenwei
 */
public interface SalesInvoiceMapper {

    /**
     * 添加销售发票信息
     * @param salesInvoice
     * @return
     * @author chenwei
     * @date Jul 2, 2013
     */
    public int addSalesInvoice(SalesInvoice salesInvoice);

    /**
     * 添加销售发票明细信息
     * @param salesInvoiceDetail
     * @return
     * @author chenwei
     * @date Jul 2, 2013
     */
    public int addSalesInvoiceDetail(SalesInvoiceDetail salesInvoiceDetail);

    /**
     * 批量插入销售发票明细列表
     * @param list
     * @return
     * @author chenwei
     * @date Nov 4, 2013
     */
    public int addSalesInvoiceDetailList(List<SalesInvoiceDetail> list);

    /**
     * 获取销售发票基本信息
     * @param id
     * @return
     * @author chenwei
     * @date Jul 2, 2013
     */
    public SalesInvoice getSalesInvoiceInfo(@Param("id") String id);

    /**
     * 获取销售发票来源单据编号列表
     * @param id
     * @return
     * @author chenwei
     * @date Jan 27, 2014
     */
    public List getSalesInvoiceSouceidList(@Param("id") String id);

    /**
     * 获取销售发票明细列表
     * @param billid
     *
     * @return
     * @author chenwei
     * @date Jul 2, 2013
     */
    public List getSalesInvoiceDetailList(@Param("billid") String billid);

    /**
     * 获取销售发票明细列表（合计品牌折扣）
     * @param billid
     * @return
     * @author chenwei
     * @date Dec 18, 2013
     */
    public List getSalesInvoiceDetailListSumBranddiscount(@Param("billid") String billid);

    /**
     * 获取销售核销明细列表（当前页）
     * @param pageMap
     * @return
     */
    public List getSalesInvoiceDetailListSumBranddiscountByPageMap(PageMap pageMap);

    /**
     * 获取销售核销明细列表总数（分页）
     * @param pageMap
     * @return
     */
    public int getSalesInvoiceDetailCountSumBranddiscountByPageMap(PageMap pageMap);

    /**
     * 获取销售开票明细列表合计（分页）
     * @param pageMap
     * @return
     */
    public List getSalesInvoiceDetailTotalSumBranddiscountByPageMap(PageMap pageMap);
    /**
     * 获取销售发票明细列表（合计品牌折扣），打印时使用，如果修改getSalesInvoiceDetailListSumBranddiscount，请查看本方法是否需要修改<br/>
     * map中参数：<br/>
     * billid:发票单据编号<br/>
     * orderby:排序,值为print时，打印时修改，null时默认排序
     * @param map
     * @return
     * @author chenwei
     * @date Dec 18, 2013
     */
    public List getSalesInvoiceDetailListSumBranddiscountForPrint(Map map);

    /**
     * 获取销售发票的客户列表
     * @param billid
     * @return
     * @author chenwei
     * @date Aug 12, 2013
     */
    public List getSalesInvoiceCustomerList(@Param("billid") String billid);

    /**
     * 获取销售发票列表
     * @param pageMap
     * @return
     * @author chenwei
     * @date Jul 2, 2013
     */
    public List showSalesInvoiceList(PageMap pageMap);

    /**
     * 获取销售发票数量
     * @param pageMap
     * @return
     * @author chenwei
     * @date Jul 2, 2013
     */
    public int showSalesInvoiceCount(PageMap pageMap);

    /**
     * 获取销售发票合计
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Jan 23, 2014
     */
    public SalesInvoice getSalesInvoiceDataSum(PageMap pageMap);

    /**
     * 删除销售发票
     * @param id
     * @return
     * @author chenwei
     * @date Jul 5, 2013
     */
    public int deleteSalesInvoice(@Param("id") String id);

    /**
     * 删除销售发票明细
     * @param billid
     * @return
     * @author chenwei
     * @date Jul 8, 2013
     */
    public int deleteSalesInvoiceDetail(@Param("billid") String billid);

    /**
     * 根据单据编号和商品编码 删除销售发票明细
     * @param billid
     * @param goodsid
     * @return
     * @author chenwei
     * @date Nov 5, 2013
     */
    public int deleteSalesInvoiceDetailByGoodsid(@Param("billid") String billid, @Param("goodsid") String goodsid);

    /**
     * 根据单据编码、来源单据、商品编码  删除销售发票明细
     * @param billid
     * @param goodsid
     * @param sourceid
     * @return
     * @author panxiaoxiao
     * @date Aug 22, 2014
     */
    public int deleteSalesInvoiceDetailByGoodsidBoth(@Param("billid") String billid, @Param("goodsid") String goodsid, @Param("sourceid") String sourceid);

    /**
     * 销售发票修改
     * @param salesInvoice
     * @return
     * @author chenwei
     * @date Jul 8, 2013
     */
    public int editSalesInvoice(SalesInvoice salesInvoice);

    /**
     * 销售发票审核
     * @param id
     * @param userid
     * @param username
     * @return
     * @author chenwei
     * @date Jul 8, 2013
     */
    public int auditSalesInvoice(@Param("id") String id, @Param("userid") String userid, @Param("username") String username,@Param("businessdate") String businessdate);

    /**
     * 销售发票反审
     * @param id
     * @param userid
     * @param username
     * @return
     * @author chenwei
     * @date Jul 8, 2013
     */
    public int oppauditSalesInvoice(@Param("id") String id, @Param("userid") String userid, @Param("username") String username);

    /**
     * 销售发票核销
     * @param id
     * @param writeoffamount
     * @param tailamount
     * @param writeoffdate
     * @param userid
     * @param username
     * @return
     * @author chenwei
     * @date Jul 11, 2013
     */
    public int writeOffSalesInvoice(@Param("id") String id, @Param("writeoffamount") BigDecimal writeoffamount, @Param("tailamount") BigDecimal tailamount,@Param("oldWriteoffamount") BigDecimal oldWriteoffamount, @Param("oldTailamount") BigDecimal oldTailamount, @Param("writeoffdate") String writeoffdate, @Param("userid") String userid, @Param("username") String username);

    /**
     * 反核销销售发票回写
     * @param id
     * @return
     * @author panxiaoxiao
     * @date Aug 4, 2014
     */
    public int unWriteOffSalesInvoice(@Param("id") String id);

    /**
     * 根据客户编号获取未核销的销售发票列表
     * @param customerid
     * @return
     * @author chenwei
     * @date Jul 11, 2013
     */
    public List showSalesInvoiceListByCustomerid(String customerid);

    /**
     * 根据销售发票 获取该发票下客户折扣合计列表
     * @param id
     * @return
     * @author chenwei
     * @date Aug 25, 2013
     */
    public List getSalesInvocieDiscountGroupByCustoemrid(String id);

    /**
     * 更新打印次数
     *
     * @param salesInvoice
     * @return
     * @author zhanghonghui
     * @date 2013-9-30
     */
    public int updateOrderPrinttimes(SalesInvoice salesInvoice);

    /**
     * 根据来源单据编号 获取销售发票明细列表
     * @param sourceid
     * @return
     * @author chenwei
     * @date Oct 10, 2013
     */
    public List getSalesInvoiceDetailListBySourceid(@Param("sourceid") String sourceid);

    /**
     * 根据销售发票编号和来源单据编号 获取明细列表
     * @param billid
     * @param sourceid
     * @return
     * @author chenwei
     * @date Oct 10, 2013
     */
    public List getSalesInvoiceDetailListByBillidAndSourceid(@Param("billid") String billid, @Param("sourceid") String sourceid);

    /**
     * 获取来源单据开票金额
     * @param billid
     * @param sourceid
     * @return
     * @author panxiaoxiao
     * @date Jul 24, 2014
     */
    public BigDecimal getSalesInvoiceDetailAmount(@Param("billid") String billid, @Param("sourceid") String sourceid);

    /**
     * 对未已开票 未核销的销售发票 申请核销
     * @param id
     * @return
     * @author chenwei
     * @date Oct 10, 2013
     */
    public int updateSalesInvoiceApplyWriteOff(@Param("id") String id);

    /**
     * 根据客户编号和销售发票编号 获取该发票下 该客户的成本金额
     *
     * @param billid
     * @param customerid
     * @param brandid
     * @return
     * @author chenwei
     * @date Nov 5, 2013
     */
    public BigDecimal getSalesInvoiceCostByCustomerid(@Param("billid") String billid, @Param("customerid") String customerid, @Param("brandid") String brandid);

    /**
     * 根据客户编号和销售发票编号 获取该发票下 该客户的发货金额
     * @param billid
     * @param customerid
     * @return
     * @author chenwei
     * @date Nov 23, 2013
     */
    public BigDecimal getSalesInvoiceAmountByCustomerid(@Param("billid") String billid, @Param("customerid") String customerid);

    /**
     * 获取销售发票的 总发货金额
     * @param billid
     * @return
     * @author chenwei
     * @date Nov 23, 2013
     */
    public BigDecimal getSalesInvoiceSendAmount(@Param("billid") String billid);

    /**
     * 获取销售发票下有发货金额的 客户列表
     * @param billid
     * @return
     * @author chenwei
     * @date Nov 23, 2013
     */
    public List getSalesInvoiceSendCustomerList(@Param("billid") String billid);

    /**
     * 获取销售发票下客户按品牌合计发货金额
     * @param billid
     * @param customerid
     * @return
     * @author chenwei
     * @date Nov 23, 2013
     */
    public List getSalesInvoiceBrandAmountByCustomerid(@Param("billid") String billid, @Param("customerid") String customerid);

    /**
     * 根据发票号或者销售发票单据号 或者销售发票列表
     * @param ids
     * @return
     * @author chenwei
     * @date Nov 20, 2013
     */
    public List showSalesInvoiceListPageByIds(@Param("ids") String ids);

    /**
     * 更新销售发票是否关联收款单状态
     *
     * @param id
     * @param isrelate
     * @return
     * @author chenwei
     * @date Dec 2, 2013
     */
    public int updateSalesInvoiceIsrelate(@Param("id") String id, @Param("isrelate") String isrelate);

    /**
     * 分客户尾差报表数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Jan 13, 2014
     */
    public List getTailamountReportData(PageMap pageMap);

    /**
     * 分客户尾差报表数量
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Jan 13, 2014
     */
    public int getTailamountReportCount(PageMap pageMap);

    /**
     * 分客户尾差合计
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Jan 13, 2014
     */
    public List getTailamountReportSumData(PageMap pageMap);

    /**
     * 更新销售发票回退状态（中止）
     * @param id
     * @param userid
     * @param username
     * @return
     * @author chenwei
     * @date Jan 13, 2014
     */
    public int updateSalesInvoiceCancel(@Param("id") String id, @Param("userid") String userid, @Param("username") String username);

    /**
     * 根据客户编号 获取该客户上次开票客户名称
     * @param customerid
     * @return
     * @author chenwei
     * @date Jan 15, 2014
     */
    public String getSalesInvoiceNameByCustomerid(@Param("customerid") String customerid);

    /**
     * 根据客户编号获取该客户的保存状态的销售发票列表
     * @param customerid
     * @return
     * @author chenwei
     * @date Jan 20, 2014
     */
    public List showSalesInvoiceListPageByCustomer(@Param("customerid") String customerid);

    /**
     * 删除
     *
     * @param ids
     * @return
     * @author panxiaoxiao
     * @date May 5, 2014
     */
    public int salesInvoiceMutiDelete(@Param("ids") String ids);

    /**
     * 获取客户应收款超账期数据列表
     * @param pageMap
     * @return
     * @author chenwei
     * @date 2014年5月28日
     */
    public List getCustomerReceivablePastdueList(PageMap pageMap);

    /**
     * 获取销售发票明细数据
     * @param pageMap
     * @return
     * @author chenwei
     * @date 2014年7月3日
     */
    public List showSalesInvoiceDetailData(PageMap pageMap);

    /**
     * 获取销售发票明细数据数量
     * @param pageMap
     * @return
     * @author chenwei
     * @date 2014年7月3日
     */
    public int showSalesInvoiceDetailDataCount(PageMap pageMap);

    /**
     * 根据销售发票编号获取销售发票明细合计数据
     * @param id
     * @return
     * @author chenwei
     * @date 2014年7月3日
     */
    public Map getSalesInvoiceDetailSumData(String id);

    /**
     * 获取销售发票列表（手机）
     * @param map
     * @return
     * @author chenwei
     * @date 2014年7月16日
     */
    public List getSalesInvoiceListByPhone(Map map);

    /**
     * 检查选中的销售发票是否已完全生成申请开票
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-02
     */
    public int checkSalesInvoiceBillCanMuApplyInvoiceAll(String id);

    /**
     * 检查选中的销售发票是否允许申请开票
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-02
     */
    public int checkSalesInvoiceBillCanMuApplyInvoice(String id);

    /**
     * 获取为申请开票过的单据明细列表
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-02
    */
    public List getUnInvoiceSalesInvoiceList(String id);

    /**
     * 回写销售核销数据
     * @param map
     * @return
     */
    public int updateSalesInvoiceBack(Map map);

    /**
     * 根据关联的开票单据编号获取销售核销信息
     * @param salesinvoicebillid
     * @return
     */
    public SalesInvoice getSalesInvoiceInfoBySalesInvoiceBillid(@Param("salesinvoicebillid")String salesinvoicebillid);

    /**
     * 根据销售开票编号和销售核销编码集合判断销售开票明细是否与销售核销明细相同，0为相同，大于0为不相同
     * 销售核销明细数量大于等于销售开票明细数量使用
     * @param map
     * @return
     */
    public int checkInvoiceDetailSameAsInvoiceBillDetailByMap(Map map);

    /**
     * 检查核销明细中是否存在未开票的来源明细 true存在 false不存在
     * @param billid
     * @return
     */
//    public int checkSalesInvoiceDetailHasNoInvoicebill(@Param("billid")String billid);

    /**
     * 根据是否开票标记获取销售核销数据列表
     * @param isinvoicebill
     * @return
     */
    public List getSalesInvoiceListByIsinvoicebill(@Param("isinvoicebill")String isinvoicebill);

    /**
     * 获取来源单据全部开票了的销售核销单据列表数据
     * @return
     */
    public List getTotalInvoicebillSalesInvoiceList();

    /**
     * 销售核销与销售开票明细相同的前提下根据销售核销编号获取销售开票编号集
     * @param salesinvoiceid
     * @return
     */
    public int getInvoiceBillidBySameDetailsByInvoiceid(@Param("salesinvoiceid")String salesinvoiceid);
    /**
     * 获取销售开票明细中,一个不能为空的税种
     * @param salesinvoiceid
     * @return
     * @author zhanghonghui 
     * @date 2016年3月2日
     */
    public String getSalesInvoiceDetailOneTaxtype(@Param("salesinvoiceid")String salesinvoiceid);
    /**
     * 获取销售开票明细中，合计税种个数
     * @param salesinvoiceid
     * @return
     * @author zhanghonghui 
     * @date 2016年3月2日
     */
    public int getSalesInvoiceDetailTaxtypeCount(@Param("salesinvoiceid")String salesinvoiceid);

    /**
     * 根据销售核销编号查询明细中，税种个数，返回列表，billid,taxtypecount
     * @param invoiceids
     * @return
     * @author zhanghonghui 
     * @date 2016年3月9日
     */
    public List getSalesInvoiceDetailTaxtypeCountList(@Param("invoiceids")String invoiceids);

    /**
     * 根据销售核销编号判断该销售核销来源单据中是否存在客户应收款期初单据
     * @param salesinvoiceid
     * @return
     * @author panxiaoxiao
     * @date 2016-11-18
     */
    public int getSalesInvoiceDetailListHasBeginAmount(@Param("salesinvoiceid")String salesinvoiceid);
}
