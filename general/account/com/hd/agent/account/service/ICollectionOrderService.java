/**
 * @(#)ICollectionOrderService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 6, 2013 chenwei 创建版本
 */
package com.hd.agent.account.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.account.model.CollectionOrder;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.account.model.CustomerCapitalLog;
import com.hd.agent.account.model.TransferOrder;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 收款单service
 * @author chenwei
 */
public interface ICollectionOrderService {
	/**
	 * 收款单添加
	 * @param collectionOrder
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public boolean addCollectionOrder(CollectionOrder collectionOrder) throws Exception;
	
	/**
	 * 收款单添加保存并审核
	 * @param collectionOrder
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 13, 2015
	 */
	public Map addCollectionOrderSaveAudit(CollectionOrder collectionOrder)throws Exception;
	/**
	 * 获取收款单详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public CollectionOrder getCollectionOrderInfo(String id) throws Exception;
	/**
	 * 获取收款单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 6, 2013
	 */
	public PageData showCollectionOrderList(PageMap pageMap) throws Exception;
	/**
	 * 收款单删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public boolean deleteCollectionOrder(String id) throws Exception;
	/**
	 * 收款单修改
	 * @param collectionOrder
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public boolean editCollectionOrder(CollectionOrder collectionOrder) throws Exception;
	/**
	 * 收款单审核
	 * @param id			单据编号
	 * @param issuper  		是否超级审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 8, 2013
	 */
	public Map auditCollectionOrder(String id,boolean issuper) throws Exception;
	/**
	 * 收款单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public Map oppauditCollectionOrder(String id) throws Exception;
	/**
	 * 获取客户资金情况列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public PageData showCustomerAccountList(PageMap pageMap) throws Exception;
	/**
	 * 获取客户资金流水表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 12, 2013
	 */
	public PageData showCustomerCapitalLogList(PageMap pageMap) throws Exception;
	/**
	 * 根据收款单编号获取收款单列表
	 * @param ids
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	public List getCollectionOrderListByIds(String ids) throws Exception;
	/**
	 * 根据客户编号获取该客户下未核销完的收款单列表
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 30, 2013
	 */
	public List getCollectionOrderListByCustomerid(String customerid) throws Exception;
	/**
	 * 收款单合并
	 * @param ids		需要合并的收款单编号
	 * @param orderid	合并到那张收款单中
	 * @param remark	备注
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	public boolean setCollectionOrderMerge(String ids,String orderid,String remark) throws Exception;
	/**
	 * 收款单转账
	 * @param transferOrder
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 13, 2013
	 */
	public boolean setCollectionOrderTransfer(TransferOrder transferOrder) throws Exception;
	/**
	 * 收款单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public boolean submitCollectionOrderPageProcess(String id) throws Exception;
	/**
	 * 给收款单 指定客户
	 * @param id
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 7, 2013
	 */
	public Map updateCollectionOrderAssignCustomer(String id,String detailList) throws Exception;
	
	/**
	 * 根据客户编号获取 客户余额
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 8, 2013
	 */
	public CustomerCapital getCustomerCapitalByCustomerid(String customerid) throws Exception;
	/**
	 * 获取收款单对应关联的核销单（发票，冲差单）列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public List showCollectionStatementDetailList(String id) throws Exception;
	
	/**
	 * 根据（发票，冲差单）获取关联的收款单信息
	 * @param billid
	 * @param billtype
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 11, 2014
	 */
	public List showBillRelateCollectionList(String billid,String billtype)throws Exception;
	
	/**
	 * 客户银行对应收款单列表数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 29, 2014
	 */
	public List getBankWriteReportCollectionList(Map map)throws Exception;

	/**
	 * 根据oaid查询收款单
	 * @param oaid
	 * @return
	 * @author limin
	 * @date Mar 29, 2016
	 */
	public List<CollectionOrder>  selectCollectionOrderByOaid(String oaid) throws Exception;

    /**
     * 根据采购进货单编号 获取供应商的汇总金额
     * @param idarr
     * @return
     */
    public List<Map> getCollectionAmountData(List idarr);

	/**
	 * 查询客户资金余额List
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jul 26, 2016
	 */
	public List<CustomerCapital> getCustomerCapitalListForSync() throws Exception;

	/**
	 * 查询客户资金余额List
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jul 26, 2016
	 */
	public List<CustomerCapitalLog> getCustomerCapitalLogListForSync(int startindex, int endindex) throws Exception;

	/**
	 * 查询客户资金余额List
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jul 26, 2016
	 */
	public PageData getCustomerCapitalListForSync(PageMap pageMap) throws Exception;

	/**
	 * 查询客户资金余额List
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Jul 26, 2016
	 */
	public PageData getCustomerCapitalLogListForSync(PageMap pageMap) throws Exception;

	/**
	 * 根据来源取收款单
	 *
	 * @param source
	 * @param sourceno
	 * @return
	 * @author limin
	 * @date Sep 5, 2016
	 */
	public CollectionOrder getCollectionOrderBySource(String source, String sourceno) throws Exception;

	/**
	 * 根据map中参数获取收款单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * notprint ： 1 表示未打印的<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月26日
	 */
	public List showCollectionOrderListBy(Map map) throws Exception;

	/**
	 * 根据收款单更新打印次数
	 * @param collectionOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月26日
	 */
	public boolean updateOrderPrinttimes(CollectionOrder collectionOrder) throws Exception;
	/**
	 * 根据收款单列表更新打印次数
	 * @param list
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2015年3月26日
	 */
	public void updateOrderPrinttimes(List<CollectionOrder> list) throws Exception;
}

