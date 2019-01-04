/**
 * @(#)IPhoneOrderService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jan 17, 2014 zhengziyong 创建版本
 */
package com.hd.agent.phone.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IPhoneOrderService {
	/**
	 * 获取客户应收款账户余额等信息
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 28, 2014
	 */
	public List getCustomerInvoiceList(PageMap pageMap) throws Exception;
	/**
	 * 根据客户编号 获取客户应收款等信息
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 29, 2014
	 */
	public Map getCustomerInvoiceInfo(String customerid) throws Exception;
	/**
	 * 获取客户可以申请抽单的单据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 29, 2014
	 */
	public List getCustomerInvoiceBillList(PageMap pageMap) throws Exception;
	/**
	 * 回单及退货通知单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public PageData getReceiptAndRejectBillList(PageMap pageMap) throws Exception;
	
	/**
	 * 回单及退货通知单明细列表
	 * @param id
	 * @param cid
	 * @param type
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public Map getReceiptAndRejectBillDetailList(String id, String cid, String type) throws Exception;
	
	/**
	 * 申请抽单
	 * @param uid
	 * @param id
	 * @param cid
	 * @param type
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public Map makeInvoice(String uid, String id, String cid, String type) throws Exception;

    /**
     * 获取客户开票数据列表
     * @param pageMap
     * @return
     * @throws Exception
     */
    public List getCustomerInvoiceBillInfoList(PageMap pageMap) throws Exception;

    /**
     * 获取客户可以申请开票的单据列表
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 29, 2014
     */
    public List getCustomerInvoiceBillApplayList(PageMap pageMap) throws Exception;

    /**
     * 上传申请开票
     * @param map
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 30, 2014
     */
    public Map addCustomerInvoiceBillApplayList(Map map) throws Exception;
	/**
	 * 要货申请单数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public PageData getDemandData(PageMap pageMap) throws Exception;
	
	/**
	 * 订单追踪数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public Map getOrderTrack(String id) throws Exception;
	/**
	 * 上传申请抽单
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 30, 2014
	 */
	public Map addCustomerInvoiceBillList(Map map) throws Exception;
	/**
	 * 获取客户超账期数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月28日
	 */
	public List getCustomerReceivablePastdueList(PageMap pageMap) throws Exception;
	/**
	 * 获取销售核销列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月16日
	 */
	public List getSalesInvoiceListByPhone(Map map) throws Exception;

    /**
     * 获取销售发票列表
     * @param map
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月16日
     */
    public List getSalesInvoiceBillListByPhone(Map map) throws Exception;

	/**
	 * 销售发票申请抽单
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月17日
	 */
	public boolean updateSalesInvoiceWrite(String billid) throws Exception;
	/**
	 * 销售发票回退删除
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月17日
	 */
	public boolean deleteSalesInvoiceBack(String billid) throws Exception;

    /**
     * 销售发票回退删除
     * @param billid
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月17日
     */
    public boolean deleteSalesInvoiceBillBack(String billid) throws Exception;
}

