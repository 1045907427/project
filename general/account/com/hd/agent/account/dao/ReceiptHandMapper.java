/**
 * @(#)ReceiptHandMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Nov 24, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.account.dao;

import com.hd.agent.account.model.ReceiptHand;
import com.hd.agent.account.model.ReceiptHandBill;
import com.hd.agent.account.model.ReceiptHandCustomer;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Receipt;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 回单交接单dao
 * 
 * @author panxiaoxiao
 */
public interface ReceiptHandMapper {

	/**
	 * 为回单交接单获取已审核的回单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 25, 2014
	 */
	public List getReceiptListForReceiptHand(PageMap pageMap);
	
	/**
	 * 为回单交接单详情页面单据明细添加页面获取已审核的回单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 25, 2014
	 */
	public List getReceiptListForAddReceiptHand(PageMap pageMap);
	/**
	 * 为回单交接单详情页面单据明细添加页面获取已审核的回单列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 25, 2014
	 */
	public int getReceiptCountForAddReceiptHand(PageMap pageMap);
	
	/**
	 * 为回单交接单详情页面单据明细添加页面获取已审核的回单合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 25, 2014
	 */
	public Receipt getReceiptSumForAddReceiptHand(PageMap pageMap);
	
	/**
	 * 根据回单编号集合获取交接单单据明细列表数据
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public List getReceiptHandBillListByIds(Map map);
	
	/**
	 * 根据回单编号集合获取交接单客户明细列表数据
	 * @param map (ids,printnum)
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public List getReceiptHandCustomerListByIds(Map map);
	
	/**
	 * 新增应收交接单
	 * @param receiptHand
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public int addReceiptHand(ReceiptHand receiptHand);
	
	/**
	 * 新增应收交接单单据明细
	 * @param billList
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public int addReceiptHandBillDetail(List<ReceiptHandBill> billList);
	
	/**
	 * 新增应收交接单客户明细
	 * @param customerList
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public int addReceiptHandCustomerDetail(List<ReceiptHandCustomer> customerList);
	
	/**
	 * 获取应收交接单详情明细
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public ReceiptHand getReceiptHandInfo(@Param("id")String id);
	
	/**
	 * 根据交接单编号获取交接单单据明细列表数据
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public List getReceiptHandBillListByBillid(@Param("id")String id);

	/**
	 * 根据交接单编号获取交接单客户明细列表数据
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 26, 2014
	 */
	public List getReceiptHandCustomerListByBillid(@Param("id")String id);

    /**
     * 根据回单交接单单据编号和客户编号 获取客户单据明细列表
     * @param id
     * @param customerid
     * @return
     */
    public List getReceiptHandBillListByBillidAndCustomerid(@Param("id")String id,@Param("customerid")String customerid);
	/**
	 * 获取应收交接单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public List getReceiptHandList(PageMap pageMap);
	
	/**
	 * 获取应收交接单列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public int getReceiptHandListCount(PageMap pageMap);
	
	/**
	 * 获取应收交接单列表合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public ReceiptHand getReceiptHandListSum(PageMap pageMap);
	
	/**
	 * 删除应收交接单
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public int deleteReceiptHand(@Param("id")String id);
	
	/**
	 * 根据参数删除应收交接单单据明细列表数据
	 * @param map（billid：交接单编号，relatebillid：回单编号）
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public int deleteReceiptHandBillListByMap(Map map);
	
	/**
	 * 根据参数删除应收交接单客户明细列表数据
	 * @param map（billid：交接单编号，customerid：客户编号）
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public int deleteReceiptHandCustomerListByMap(Map map);
	
	/**
	 * 审核应收交接单
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public int auditReceiptHands(Map map);
	
	/**
	 * 反审应收交接单
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
	public int oppauditReceiptHands(Map map);
	
	/**
	 * 修改交接单
	 * @param receiptHand
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 27, 2014
	 */
	public int editReceiptHand(ReceiptHand receiptHand);
	
	/**
	 * 获取已生成交接单的回单列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
	public int getReceiptListByHandCount(PageMap pageMap);
	/**
	 * 获取已生成交接单的回单列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
	public List getReceiptListByHand(PageMap pageMap);
	
	/**
	 * 获取已生成交接单的回单列表合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 28, 2014
	 */
	public ReceiptHandBill getReceiptListByHandSum(PageMap pageMap);
	
	/**
	 * 
	 * @param relatebillid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 29, 2014
	 */
	public int updateReceiptHandBillIsrecycle(@Param("billid")String billid,@Param("relatebillid")String relatebillid);
	
	/**
	 * 根据回单编号显示交接单_客户明细列表
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 3, 2014
	 */
	public List getReceiptHandCustomerListByBills(Map map);
	
	/**
	 * 根据回单编号显示交接单_单据明细列表（新增）
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 4, 2014
	 */
	public List getReceiptHandBillListByBills(Map map);
	
	/**
	 * 根据交接单编号删除对应单据明细
	 * @param billid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 4, 2014
	 */
	public int deleteReceiptHandBillByBillid(@Param("billid")String billid);
	
	/**
	 * 根据交接单编号删除对应客户明细
	 * @param billid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 4, 2014
	 */
	public int deleteReceiptHandCustomerByBill(@Param("billid")String billid);
	
	/**
	 * 根据最终单据编码集获取对应客户详情
	 * @param map（relatebills数组,customerid）
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 5, 2014
	 */
	public ReceiptHandCustomer getReceiptHandCustomerInfo(Map map);
	
	/**
	 * 获取所有已关闭回单的交接单编码列表集
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 8, 2014
	 */
	public List getAllReceiptClosedOfReceiptHandList();
	
	/**
	 * 根据编码关闭交接单
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 8, 2014
	 */
	public int closeReceiptHand(@Param("id")String id);
	
	/**
	 * 分客户获取回单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public List getReceiptListGroupByCustomer(PageMap pageMap);
	
	/**
	 * 分客户获取回单列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public int getReceiptCountGroupByCustomer(PageMap pageMap);

    /**
     * 分客户获取回单列表数据(手机客户端)
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date Dec 11, 2014
     */
    public List getReceiptListGroupByCustomerForPhone(PageMap pageMap);
	/**
	 * 分客户获取回单列表数据合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public Receipt getReceiptSumGroupByCustomer(PageMap pageMap);
	
	/**
	 * 根据交接单编号获取应收款账单交接明细（打印）
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 17, 2014
	 */
	public List getReceiptHandCustomerDetailList(@Param("id")String id);
	
	/**
	 * 根据交接单编号获取回单交接单单据明细（打印）
	 * @param billid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2015年6月1日
	 */
	public List getReceiptHandBillListForPrint(@Param("billid")String billid);
	
	/**
	 * 更新打印次数
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 18, 2014
	 */
	public int updatePrintTimes(@Param("id")String id);
	
	/**
	 * 根据回单交接单编码&客户编码数组集合获取回单交接单单据回单单据明细列表数据
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 23, 2014
	 */
	public List getReceiptHandBillListByParam(Map map);
}

