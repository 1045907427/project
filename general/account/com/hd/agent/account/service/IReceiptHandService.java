/**
 * @(#)IReceiptHandService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 24, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.service;

import com.hd.agent.account.model.ReceiptHand;
import com.hd.agent.account.model.ReceiptHandBill;
import com.hd.agent.account.model.ReceiptHandCustomer;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Receipt;

import java.util.List;
import java.util.Map;

/**
 * 回单交接单Service
 * 
 * @author panxiaoxiao
 */
public interface IReceiptHandService {

	/**
	 * 新增交接单
     * @param ids      单据编号
     * @param isphone 是否手机用户申请
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public Map addReceiptHand(String ids,boolean isphone)throws Exception;
	
	/**
	 * 获取应收交接单信息详情（包含单据明细、客户明细）
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public ReceiptHand getReceiptHandInfo(String id)throws Exception;
	
	/**
	 * 获取应收交接单基础信息详情（不包含单据明细、客户明细）
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public ReceiptHand getReceiptHandBaseInfo(String id)throws Exception;
	
	/**
	 * 根据交接单编号获取交接单单据明细列表
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public Map getReceiptHandBillListByBillid(String billid)throws Exception;
	
	/**
	 * 根据交接单编号获取交接单客户明细列表
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public Map getReceiptHandCustomerListByBillid(String billid)throws Exception;

    /**
     * 根据回单交接单单据编号和客户编号 获取客户的单据明细列表
     * @param billid
     * @param customerid
     * @return
     * @throws Exception
     */
    public List<ReceiptHandBill> getReceiptHandBillListByBillidAndCustomerid(String billid,String customerid) throws Exception;
	/**
	 * 获取回单交接列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public PageData getReceiptHandList(PageMap pageMap)throws Exception;

    /**
     * 获取回单交接列表（提供手机试用）
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015-06-03
     */
    public List<ReceiptHand> getReceiptHandListForPhone(PageMap pageMap) throws Exception;
	/**
	 * 根据交接单编号字符串集合删除应收款交接单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public Map deleteReceiptHands(String ids)throws Exception;
	
	/**
	 * 审核交接单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public Map auditReceiptHands(String ids)throws Exception;
	
	/**
	 * 修改交接单
	 * @param receiptHand
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public Map editReceiptHand(ReceiptHand receiptHand,List<ReceiptHandBill> billList,List<ReceiptHandCustomer> customerList)throws Exception;
	
	/**
	 * 保存并审核交接单
	 * @param receiptHand
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public Map saveAuditReceiptHand(ReceiptHand receiptHand,List<ReceiptHandBill> billList,List<ReceiptHandCustomer> customerList)throws Exception;
	
	/**
	 * 反审交接单
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public Map oppauditReceiptHands(String ids)throws Exception;
	
	/**
	 * 获取已生成交接单的回单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
	public PageData getReceiptListByHand(PageMap pageMap)throws Exception;
	
	/**
	 * 回收回单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
	public Map doBackReceiptHands(List<ReceiptHandBill> list)throws Exception;
	
	/**
	 * 根据回单编号显示交接单_客户明细列表(删除)
	 * @param billid
     * @param relatebillid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 3, 2014
	 */
	public Map getReceiptHandCustomerListByBills(String billid,String relatebillid)throws Exception;
	
	/**
	 * 根据选中的回单编码集转换生成客户明细列表，单据明细列表
	 * @param receipthandid
	 * @param sourceids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 3, 2014
	 */
	public Map getReceiptHandDetailListMap(String receipthandid,String sourceids)throws Exception;
	
	/**
	 * 获取所有已关闭回单的交接单编码列表集
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 8, 2014
	 */
	public boolean closeAllReceiptClosedOfReceiptHandList()throws Exception;
	
	/**
	 * 分客户获取回单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public PageData getReceiptListGroupByCustomerForReceiptHand(PageMap pageMap)throws Exception;

    /**
     * 分客户获取回单列表数据(提供手机客户端接口)
     * @param con
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Dec 11, 2014
     */
    public List<Receipt> getReceiptHandGroupCustomerForPhone(String con)throws Exception;
	/**
	 * 获取指定客户的回单单据列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 12, 2014
	 */
	public Map getReceiptListOfReceiptDetail(PageMap pageMap)throws Exception;
	
	/**
	 * 根据交接单编号获取应收款账单交接明细（打印）
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 17, 2014
	 */
	public Map getReceiptHandCustomerDetailList(String id)throws Exception;
	
	/**
	 * 根据交接单编号获取回单交接单单据明细（打印）
	 * @param billid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2015年6月1日
	 */
	public List getReceiptHandBillListForPrint(String billid)throws Exception;
	
	/**
	 * 根据交接单编号更新打印次数
	 * @param id
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 18, 2014
	 */
	public boolean updatePrintTimes(String id)throws Exception;
	
	/**
	 * 为回单交接单获取已审核的回单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 24, 2014
	 */
	public PageData getReceiptListForReceiptHand(PageMap pageMap)throws Exception;
	
}

