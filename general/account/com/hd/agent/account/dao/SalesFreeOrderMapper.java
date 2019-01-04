package com.hd.agent.account.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.ReceivablePastDueReason;
import com.hd.agent.account.model.SalesFreeOrder;
import com.hd.agent.common.util.PageMap;

/**
 * 销售放单管理dao
 * @author chenwei
 */
public interface SalesFreeOrderMapper {
	/**
	 * 添加销售放单申请
	 * @param salesFreeOrder
	 * @return
	 * @author chenwei 
	 * @date 2014年5月29日
	 */
	public int addSalesFreeOrder(SalesFreeOrder salesFreeOrder);
	
	/**
	 * 修改销售放单
	 * @param salesFreeOrder
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public int editSalesFreeOrder(SalesFreeOrder salesFreeOrder);
	
	/**
	 * 根据放单编码获取放单信息
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public SalesFreeOrder getSalesFreeOrderByID(String id);
	
	/**
	 * 审核
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public int auditSalesFreeOrder(SalesFreeOrder salesFreeOrder);
	
	/**
	 * 批量审核
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public int auditSalesFreeOrderBatch(List<SalesFreeOrder> list);
	
	/**
	 * 反审
	 * @param salesFreeOrder
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public int oppauditSalesFreeOrder(SalesFreeOrder salesFreeOrder);
	
	/**
	 * 批量反审
	 * @param idsArr
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public int oppauditSalesFreeOrderBatch(String[] idsArr);
	
	/**
	 * 批量删除放单
	 * @param idsArr
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public int deleteSalesFreeOrder(String[] idsArr);
	
	/**
	 * 判断是否存在保存状态的放单
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public int getSalesFreeOrderCount(Map map);
	
	/**
	 * 获取保存状态的放单
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public SalesFreeOrder getSalesFreeOrder(Map map);
	
	/**
	 * 获取放单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public List getSalesFreeOrderList(PageMap pageMap);
	
	/**
	 * 获取放单列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 30, 2014
	 */
	public int getSalesFreeOrderListCount(PageMap pageMap);
	
	/*-------------------超账期原因-----------------------------------------*/
	/**
	 * 添加客户应收款超账期原因
	 * @param reaReceivablePastDueReason
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 22, 2014
	 */
	public int addCustomerReceivablePastDueReason(ReceivablePastDueReason reaReceivablePastDueReason);
	
	/**
	 * 获取启用状态客户应收款超账期原因
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 22, 2014
	 */
	public ReceivablePastDueReason getCustomerReceivablePastDueReason(@Param("customerid")String customerid);
	
	/**
	 * 获取客户应收款超账期原因变更次数
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 22, 2014
	 */
	public int getCustomerReceivablePastDueReasonChangenum(@Param("customerid")String customerid);
	
	/**
	 * 一键清除客户应收款超账期原因
	 * @return
	 * @author panxiaoxiao 
	 * @date May 22, 2014
	 */
	public int oneClearReceivablePastDueReason();
	
	/**
	 * 禁用客户应收款超账期原因
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 22, 2014
	 */
	public int editCustomerReceivablePastDueReason(@Param("customerid")String customerid);
	
	/**
	 * 获取客户应收款超账期原因情况统计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public List showBaseReceivablePassDueReasonListData(PageMap pageMap);
	/**
	 * 获取应收款超账期原因情况统计数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public int showBaseReceivablePassDueReasonListCount(PageMap pageMap);

	
	/**
	 * 根据客户编码获取历史应收款超账期原因列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 23, 2014
	 */
	public List getHistoryCustomerReceivablePastDueReasonList(PageMap pageMap);
	
	/**
	 * 根据客户编码获取历史应收款超账期原因数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date May 23, 2014
	 */
	public int getHistoryCustomerReceivablePastDueReasonCount(PageMap pageMap);
	
	/**
	 * 根据承诺到款日期时间段等获取不重复的客户编码集
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date May 27, 2014
	 */
	public List getReceivablePastDueReasonByMap(Map map);
	/**
	 * 根据客户编号和业务日期判断 该客户是否允许放单
	 * @param customerid
	 * @param businessdate
	 * @return
	 * @author chenwei 
	 * @date 2014年5月29日
	 */
	public int getSalesFreeOrderCountByCustomeridAndDate(@Param("customerid")String customerid,@Param("businessdate")String businessdate);

	/**
	 * 代垫应收分析报表
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Oct 27, 2017
	 */
	public List showSupplierReceivablePastDueReasonList(PageMap pageMap);


	/**
	 * 代垫应收分析报表
	 * @param pageMap
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Oct 27, 2017
	 */
	public int showSupplierReceivablePastDueReasonCount(PageMap pageMap);

	/**
	 * 获取代垫应收款超账期情况统计数据中的游标
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Oct 30, 2017
	 */
	public List getSupplierPassDueListDataRS(PageMap pageMap);

}