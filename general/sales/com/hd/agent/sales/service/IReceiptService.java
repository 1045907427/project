/**
 * @(#)IReceiptService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service;

import com.hd.agent.account.model.BeginAmount;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.ReceiptDetail;
import com.hd.agent.sales.model.RejectBill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IReceiptService {

	/**
	 * 添加销售发货回单信息
	 * @param receipt
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 30, 2013
	 */
	public boolean addReceipt(Receipt receipt) throws Exception;
	
	/**
	 * 更新回单信息
	 * @param receipt
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public boolean updateReceipt(Receipt receipt) throws Exception;
	
	/**
	 * 删除回单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public boolean deleteReceipt(String id) throws Exception;
	
	/**
	 * 销售发货回单审核
	 * @param type
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 1, 2013
	 */
	public Map auditReceipt(String type, String id) throws Exception;
	
	/**
	 * 批量销售发货回单审核
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 4, 2014
	 */
	public Map auditMultiReceipt(String ids) throws Exception;
	
	/**
	 * 获取销售发货回单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public Receipt getReceipt(String id) throws Exception;
	
	/**
	 * 获取销售发货回单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 31, 2013
	 */
	public PageData getReceiptData(PageMap pageMap) throws Exception;
	
	/**
	 * 通过回单编号获取明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public List getDetailListByReceipt(String id) throws Exception;
	
	/**
	 * 提交销售回单进流程
	 * @param title
	 * @param userId
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 3, 2013
	 */
	public boolean submitReceiptProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;
	/**
	 * 根据回单编号和明细编号获取明细信息
	 * @param billid
	 * @param detailid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 25, 2013
	 */
	public ReceiptDetail getReceiptDetail(String billid,String detailid) throws Exception;
	/**
	 * 取消回单验收
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 7, 2013
	 */
	public boolean receiptCancelCheck(String id,List<ReceiptDetail> detailList) throws Exception;
	/**
	 * 获取回单和销售退货通知单合计列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 9, 2013
	 */
	public PageData getReceiptAndRejectBillList(PageMap pageMap) throws Exception;
	
	/**
	 * 获取回单和销售退货通知单合计列表数据（申请开票）
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 14, 2015
	 */
	public PageData getReceiptAndRejectBillListForInvoiceBill(PageMap pageMap)throws Exception;
	/**
	 * 获取回单和销售退货通知单 明细合计列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 23, 2013
	 */
	public List getReceiptAndRejectBillDetailList(PageMap pageMap) throws Exception;
	/**
	 * 根据日期获取客户的应收日期
	 * @param date
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 24, 2013
	 */
	public String getReceiptDate(Date date,String customerid) throws Exception;
	/**
	 * 判断客户是否存在超账期应收款
	 * @param customerid
	 * @return				true存在 false不存在
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 25, 2013
	 */
	public boolean isReceivableAmountPassDateByCustomerid(String customerid) throws Exception;
	/**
	 * 根据客户编号 判断客户应收款是否在信用额度 信用期限内
	 * 信用期限为空时 信用额度一直有效
	 * 应收费 大于信用额度时 返回false
	 * 应收款 小于等于信用额度时 返回true
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 25, 2013
	 */
	public boolean isReceivableInCreditByCustomerid(String customerid) throws Exception;
	/**
	 * 根据回单编号 获取相关销售退货通知单（直退）列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 26, 2013
	 */
	public Map getRejectListByReceiptid(String id) throws Exception;
	/**
	 * 销售回单关联销售退货通知单（直退） 更新回单客户接收数量
	 * @param receiptid
	 * @param rejectbilldetailids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 26, 2013
	 */
	public Map receiptRelationRejectBill(String receiptid,String rejectbilldetailids) throws Exception;
	/**
	 * 根据回单中关联退货单编号获取退货单列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-6-8
	 */
	public List<RejectBill> getReceiptRejectBillList(String id) throws Exception;
	/**
	 * 销售回单关联销售退货通知单（直退） 更新回单客户接收数量（多次）
	 * @param receiptid
	 * @param rejectbillids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 4, 2014
	 */
	public Map updateReceiptRelationRejectBillAgain(String receiptid,String rejectbillids) throws Exception;
	
	/**
	 * 退货通知单的数量大于发货回单的数量
	 * @param receiptid
	 * @param rejectbillids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 8, 2014
	 */
	public Map rejectNumBigerThanReceiptNum(String receiptid,String rejectbillids)throws Exception;
	/**
	 * 获取冲差单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 25, 2014
	 */
	public CustomerPushBalance getCustomerPushBalanceByID(String id) throws Exception;
	/**
	 * 获取客户应收款期初
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月4日
	 */
	public BeginAmount getBeginAmountByID(String id) throws Exception;
    /**
     * 根据回单编码集计算回单金额合计
     * @param ids
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-26
     */
    public Receipt getTotalReceiptAmount(String ids)throws Exception;
    /**
     * 核销回单
     * @param map
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-26
     */
    public Map doWriteoffSalesReceipt(Map map)throws Exception;

    /**
     * 直接核销回单
     * @param map
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-26
     */
    public Map doDirectWriteoffSalesReceipt(Map map)throws Exception;

    /**
     * 提供申请核销 获取回单明细列表
     * @param id
     * @return
     * @throws Exception
     */
    public List<ReceiptDetail> getReceiptDetailListByApplyWriteoff(String id) throws Exception;
    /**
     * 销售发货回单导出
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年6月12日
     */
    public List<Map<String, Object>> getReceiptByExport(PageMap pageMap) throws  Exception;

	    /**
     * 获取冲差单类型为回单冲差的所有回单及回单明细列表<br/>
     * map中参数：<br/>
     * cxid：冲差编号<br/>
     * cxidarr: 冲差编号字符串，多个编号，以 , 分隔<br/>
     * cxstatusarr: 冲差单状态,多个状态，以, 分隔<br/>
     * @param map
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年11月4日
     */
    public Map<String, List<Map>> getReceiptListForHDCXByMap(Map map) throws Exception;
    /**
     * 获取客户历史应收款总额
     * @param customerid
     * @return
     * @author chenwei
     * @date Oct 25, 2013
     */
    public BigDecimal getReceivableAmountByCustomerid(String customerid) throws  Exception;

	/**
	 * 添加品牌折扣到商品明细单价
	 * @param map
	 * @return
	 * @throws Exception
	 * @author pan_xx
	 * @date 2016-08-11
	 */
	public List addSaveReceiptDetailBrandDiscount(Map map)throws  Exception;

	/**
	 * 添加回单折扣到商品明细单价
	 * @param map
	 * @return
	 * @throws Exception
	 * @author pan_xx
	 * @date 2016-08-12
	 */
	public List addSaveBillDetailReceiptDiscount(Map map)throws  Exception;

	/**
	 * 根据销售订单编号 获取回单信息
	 * @param saleorderid
	 * @return
	 * @author chenwei
	 * @date Nov 9, 2013
	 */
	public Receipt getReceiptBySaleorderid(String saleorderid);
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
	public List<ReceiptDetail> getReceiptDetailListByMap(Map map) throws Exception;
	/**
	 * 根据编号获取回单<br/>
	 * @param id
	 * @return Receipt
	 * @throws
	 * @author zhanghonghui
	 * @date May 10, 2017
	 */
	public Receipt getReceiptInfoById(String id) throws Exception;

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
	public List<ReceiptDetail> getReceiptDetailListSumDiscountByMap(Map map) throws Exception;

	/**
	 * 获取销售回单明细及单据列表
	 * @param map
	 * @return java.util.List<com.hd.agent.sales.model.Receipt>
	 * @throws
	 * @author zhanghonghui
	 * @date Nov 06, 2017
	 */
	public List<Receipt> getReceiptListBy(Map map) throws Exception;

	/**
	 * 更新打印次数
	 * @param order
	 * @author zhanghonghui
	 * @date 2013-9-10
	 */
	public boolean updateOrderPrinttimes(Receipt order) throws Exception;
}

