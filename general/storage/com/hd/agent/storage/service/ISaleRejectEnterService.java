/**
 * @(#)ISaleRejectEnterService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 15, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.model.SaleRejectEnterDetail;

/**
 * 
 * 销售退货入库单service接口
 * @author chenwei
 */
public interface ISaleRejectEnterService {
	/**
	 * 销售退货入库单添加
	 * @param saleRejectEnter
	 * @param detailList
	 * @return
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public boolean addSaleRejectEnter(SaleRejectEnter saleRejectEnter,List<SaleRejectEnterDetail> detailList) throws Exception;
	/**
	 * 获取销售退货入库单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public Map getSaleRejectEnter(String id) throws Exception;
	/**
	 * 获取销售退货入库单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public PageData showSaleRejectEnterList(PageMap pageMap) throws Exception;
	/**
	 * 销售退货入库单删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public boolean deleteSaleRejectEnter(String id) throws Exception;
	/**
	 * 审核销售退货入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public Map auditSaleRejectEnter(String id) throws Exception;
	/**
	 * 反审销售退货入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 27, 2013
	 */
	public Map oppauditSaleRejectEnter(String id) throws Exception;
	/**
	 * 销售退货入库单修改
	 * @param saleRejectEnter
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public boolean editSaleRejectEnter(SaleRejectEnter saleRejectEnter,List<SaleRejectEnterDetail> detailList) throws Exception;
	/**
	 * 获取销售退货通知单明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public RejectBill showRejectBillDetailList(String id) throws Exception;
	/**
	 * 获取销售发货回单明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public Receipt showReceiptDetailList(String id) throws Exception;
	/**
	 * 销售退货入库单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public Map submitSaleRejectEnterProcess(String id) throws Exception;
	/**
	 * 销售入库单保存并验收
	 * @param saleRejectEnter
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 15, 2013
	 */
	public boolean saveCheckSaleRejectEnter(SaleRejectEnter saleRejectEnter,List<SaleRejectEnterDetail> detailList) throws Exception;
	
	/**
	 * 获取销售退货入库单列表
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-27
	 */
	public List getSaleRejectEnterListBy(Map map) throws Exception;
	
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateOrderPrinttimes(SaleRejectEnter saleRejectEnter) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public void updateOrderPrinttimes(List<SaleRejectEnter> list) throws Exception;
	/**
	 * 获取销售退货入库
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-10
	 */
	public SaleRejectEnter getSaleRejectEnterPureInfo(String id) throws Exception;


	/**
	 * 批量修改收货人
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2016-12-21
	 */
	public Map editSalesRejectEnterStorager(String id,String storager) throws Exception;
}

