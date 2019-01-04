
/**
 * @(#)ISalesInvoiceService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 1, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.SalesInvoice;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 *
 * 销售发票业务接口
 * @author chenwei
 */
public interface ISalesInvoiceService {

    /**
     * 根据销售发货回单生成销售发票
     * @param ids
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 2, 2013
     */
    public Map addSalesInvoiceByReceipt(String ids) throws Exception;

    /**
     * 根据销售退货通知单生成销售发票
     * @param ids
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 2, 2013
     */
    public Map addSalesInvoiceByRejectbill(String ids) throws Exception;

    /**
     * 根据销售发货回单和销售退货通知单明细 生成销售发票
     * @param ids
     * @param customerid
     * @param iswriteoff
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 9, 2013
     */
    public Map addSalesInvoiceByReceiptAndRejectbill(String ids, String customerid, String iswriteoff) throws Exception;

    /**
     * 根据销售发货回单和销售退货通知单明细 生成销售发票(手机端使用)
     *
     * @param uid
     * @param ids
     * @param customerid
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jan 19, 2014
     */
    public Map addSalesInvoiceByReceiptAndRejectbillForPhone(String uid, String ids, String customerid) throws Exception;

    /**
     * 根据销售发货回单和销售退货通知单明细 追加到销售发票中
     * @param ids
     * @param customerid
     * @param billid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jan 20, 2014
     */
    public Map addToSalesInvoiceByReceiptAndRejectbill(String ids, String customerid, String billid) throws Exception;

    /**
     * 获取销售发票信息
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 2, 2013
     */
    public Map getSalesInvoiceInfo(String id) throws Exception;

    /**
     * 获取销售发票列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 2, 2013
     */
    public PageData showSalesInvoiceList(PageMap pageMap) throws Exception;

    /**
     * 销售发票删除
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 5, 2013
     */
    public boolean deletesalesInvoice(String id) throws Exception;

    /**
     * 销售发票修改
     * @param salesInvoice
     * @param delgoodsids
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 8, 2013
     */
    public Map editSalesInvoice(SalesInvoice salesInvoice, String delgoodsids) throws Exception;

    /**
     * 销售发票审核
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 8, 2013
     */
    public boolean auditSalesInvoice(String id) throws Exception;

    /**
     * 销售发票反审
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 8, 2013
     */
    public boolean oppauditSalesInvoice(String id) throws Exception;

    /**
     * 根据客户编号获取未核销的销售发票列表
     * @param customerid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 11, 2013
     */
    public List showSalesInvoiceListByCustomerid(String customerid) throws Exception;

    /**
     * 根据发票编号获取销售发票列表
     * @param invoiceid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 12, 2013
     */
    public List getSalesInvoiceListByInvoiceids(String invoiceid) throws Exception;

    /**
     * 销售发票提交工作流
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 25, 2013
     */
    public boolean submitSalesInvoicePageProcess(String id) throws Exception;

    /**
     * 获取销售发票上游单据列表
     * @param salesinvoiceid
     * @param sourcetype
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 29, 2013
     */
    public List showSalesInvoiceSourceListReferData(String salesinvoiceid, String sourcetype) throws Exception;

    /**
     * 获取销售发票打印数据列表<br/>
     * map中参数：<br/>
     * idarrs: id字符串组，格式1,2,3<br/>
     * showdetail : 1表示查询明细列表<br/>
     * @param map
     * @return
     * @author chenwei
     * @date Jul 2, 2013
     *
     * @throws Exception
     */
    public List showSalesInvoicePrintListBy(Map map) throws Exception;

    /**
     * 根据发票编码获取该发票来源明细
     * @param id
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 28, 2014
     */
    public List getSalesInvoiceSourceDetailList(String id) throws Exception;

    /**
     * 更新打印次数
     * @author zhanghonghui
     * @date 2013-9-10
     *
     * @param salesInvoice
     * @return
     *
     * @throws Exception
     */
    public boolean updateOrderPrinttimes(SalesInvoice salesInvoice) throws Exception;

    /**
     * 更新打印次数
     * @param list
     * @author zhanghonghui
     * @date 2013-9-10
     *
     * @throws Exception
     */
    public void updateOrderPrinttimes(List<SalesInvoice> list) throws Exception;

    /**
     * 对未开票 未核销的销售发票 申请核销
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Oct 10, 2013
     */
    public boolean updateSalesInvoiceApplyWriteOff(String id) throws Exception;

    /**
     * 销售发票冲差
     * 冲差类型为总店冲差时， 按成本平摊到门店
     * 冲差类型为门店时， 冲差到该门店中
     * @param invoiceid
     * @param pushtype
     * @param customerPushBalance
     * @return
     * @throws Exception
     * @author chenwei
     * @date Nov 5, 2013
     */
    public Map addCustomerPushBanlanceBySalesInvoice(String invoiceid, String pushtype, CustomerPushBalance customerPushBalance) throws Exception;

    /**
     * 根据发票号或者发票单据编号 获取销售发票列表
     * @param ids
     * @return
     * @throws Exception
     * @author chenwei
     * @date Nov 20, 2013
     */
    public List showSalesInvoiceListPageByIds(String ids) throws Exception;

    /**
     * 销售发票返利冲差
     * @param id
     * @param rebate
     * @param remark
     * @param subject
     * @return
     * @throws Exception
     * @author chenwei
     * @date Nov 23, 2013
     */
    public boolean addSalesInvoiceRebate(String id, BigDecimal rebate, String remark, String subject) throws Exception;

    /**
     * 获取客户回单未核销列表
     * @param customerid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jan 7, 2014
     */
    public List getReceiptUnWriteOffData(String customerid) throws Exception;

    /**
     * 显示销售发票
     * @param id
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-10
     */
    public SalesInvoice getPureSalesInvoicePureInfo(String id) throws Exception;

    /**
     * 获取分客户尾差报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jan 13, 2014
     */
    public PageData getTailamountReportPageData(PageMap pageMap) throws Exception;

    /**
     * 回退销售发票（作废 中止）
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jan 13, 2014
     */
    public boolean updateSalesInvoiceCancel(String id) throws Exception;

    /**
     * 根据客户编号获取销售发票列表
     * @param customerid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jan 20, 2014
     */
    public List showSalesInvoiceListPageByCustomer(String customerid) throws Exception;

    /**
     * 发票是否有冲差
     * @param invoiceid
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-1-25
     */
    public int getSalesInvoiceHasBlance(String invoiceid) throws Exception;

    /**
     * 批量删除开票抽单回退
     * @param ids
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 5, 2014
     */
    public Map salesInvoiceMutiDelete(String ids) throws Exception;

    /**
     * 获取客户应收款超账期数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年5月28日
     */
    public List getCustomerReceivablePastdueList(PageMap pageMap) throws Exception;

    /**
     * 获取销售发票明细数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月3日
     */
    public PageData showSalesInvoiceDetailData(PageMap pageMap) throws Exception;

    /**
     * 获取销售发票列表
     * @param map
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月16日
     */
    public List getSalesInvoiceListByPhone(Map map) throws Exception;

    /**
     * 销售发票中删除明细，回写来源单据开票状态，开票金额、未开票金额等
     * @param map
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 24, 2014
     */
    public Map deleteSalesInvoiceSource(Map map) throws Exception;

    /**
     * 检查选中的销售发票是否允许申请开票
     * @param ids
     * @return
     * @throws Exception
     */
    public Map checkSalesInvoiceCanMuApplyInvoice(String ids)throws Exception;

    /**
     * 检查选中的销售发票是否已完全生成申请开票
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-02
     */
    public boolean checkSalesInvoiceBillCanMuApplyInvoiceAll(String id)throws Exception;

    /**
     * 检查选中的销售发票是否允许申请开票
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-02
     */
    public boolean checkSalesInvoiceBillCanMuApplyInvoice(String id)throws Exception;

    /**
     * 申请开票
     * @param id
     * @return
     * @author panxiaoxiao
     * @date 2015-03-02
    */
    public Map doSalesInvoiceMuApplyInvoice(String id)throws Exception;

    /**
     * 判断是否允许核销
     * @param invoiceid
     * @return
     * @author panxiaoxiao
     * @date 2015-06-18
     */
    public Map getCancelSalesInvoiceFlag(String invoiceid)throws Exception;

    /**
     * 判断是否允许取消关联收款单
     * @param invoiceid
     * @return
     * @author panxiaoxiao
     * @date 2015-06-18
     */
    public Map getUnrelateSalesInvoiceFlag(String invoiceid)throws Exception;

    /**
     * 根据销售核销编码判断是否允许关联收款单
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-18
     */
    public Map getRelateSalesInvoiceFlag(String invoiceid)throws Exception;

    /**
     * 根据销售核销编码判断是否允许反审
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-18
     */
    public Map getOppauditFlag(String invoiceid)throws Exception;

    /**
     * 根据核销编号获取核销明细分页列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-02-02
     */
    public PageData getSalesInvoiceDetailList(PageMap pageMap)throws Exception;
    /**
     * 根据销售核销编号(多个编号以 , 分隔)查询明细中，税种个数税种条数
     * @param invoiceids
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2016年3月9日
     */
    public List getSalesInvoiceDetailTaxtypeCountList(String invoiceids) throws Exception;
}
