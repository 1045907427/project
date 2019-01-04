/**
 * @(#)IBuyOrderMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-21 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.report.model.StorageBuySaleReport;

/**
 * 采购订单 Service 接口
 * 
 * @author zhanghonghui
 */
public interface IBuyOrderService {
	/**
	 * 向数据库添加一条采购订单
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	//public boolean insertBuyOrder(BuyOrder buyOrder) throws Exception;
	/**
	 * 添加采购订单包括采购计划明细
	 * @param buyOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-10
	 */
	public boolean addBuyOrderAddDetail(BuyOrder buyOrder) throws Exception;
	/**
	 * 更新采购订单及采购明细
	 * @param buyOrder
	 * @return
	 * @throws Exception
	 */
	public boolean updateBuyOrderAddDetail(BuyOrder buyOrder) throws Exception;
	/**
	 * 获取采购订单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public BuyOrder showBuyOrderAndDetail(String id) throws Exception;
	/**
	 * 删除采购订单及明细,对采购计划单明细回写进行清零并对采购计划单设置为不引用
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deleteBuyOrderAndDetail(String id) throws Exception;
	/**
	 * 获取采购订单明细，未经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public BuyOrder showPureBuyOrder(String id) throws Exception;
	/**
	 * 获取采购订单,经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public BuyOrder showBuyOrder(String id) throws Exception;
	/**
	 * 获取采购订单,经过数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public BuyOrder showBuyOrderByDataAuth(String id) throws Exception;
	/**
	 * 显示分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public PageData showBuyOrderPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据参数更新采购订单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * buyOrder :采购订单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * @param buyOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean updateBuyOrderBy(Map map) throws Exception;
	/**
	 * 根据上游单据编号显示采购订单及其明细
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-31
	 */
	public BuyOrder showBuyOrderAndDetailByBillno(String billno) throws Exception;
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map auditBuyOrder(String id) throws Exception;
	/**
	 * 反审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-3
	 */
	public Map oppauditBuyOrder(String id) throws Exception;
	/**
	 * 工作流审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-31
	 */
	public boolean auditWorkflowBuyOrder(String id) throws Exception;
	
	/**
	 * 设置采购订单引用 标志
	 * @param id
	 * @param isrefer 1表示引用，0表示未引用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateBuyOrderRefer(String id,String isrefer) throws Exception;
	/**
	 * 审核时更新字段<br/>
	 * isrefer : 引用状态：1引用，0未引用<br/>
	 * status : 3审核、2反审为保存<br/>
	 * audituserid : 审核用户编号<br/>
	 * auditusername ： 审核用户姓名<br/>
	 * audittime : 审核时间<br/>
	 * 中止时更新字段<br/>
	 * status : 5 <br/>
	 * stopuserid : 中止用户编号<br/>
	 * stopusername : 中止用户姓名<br/>
	 * stoptime : 中止时间<br/>
	 * 关闭时更新字段<br/>
	 * status : 4 <br/>
	 * closetime : 关闭时间<br/>
	 * @param id
	 * @param isrefer
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-17
	 */
	public boolean updateBuyOrderStatus(BuyOrder buyOrder) throws Exception;
	
	/**
	 * 根据上游采购计划单编号，查找采购订单信息，未做字段或数据权限检查。
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-30
	 */
	public BuyOrder showBuyOrderByBillno(String billno) throws Exception;
	
	/**
	 * 删除订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-17
	 */
	public boolean deleteBuyOrder(String id) throws Exception;
	
	//------------------------------------------------------------------------//
	//采购订单详细
	//-----------------------------------------------------------------------//
	
	
	/**
	 * 添加采购订单详细
	 * @param buyOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public boolean insertBuyOrderDetail(BuyOrderDetail buyOrderDetail) throws Exception;
	/**
	 * 采购订单明细分页列表
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showBuyOrderDetailListByOrderId(String orderid) throws Exception;
	/**
	 * 采购订单明细分页列表，不判断权限
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showPureBuyOrderDetailListByOrderId(String orderid) throws Exception;
	/**
	 * 采购订单明细分页列表<br/>
	 * map中参数：<br/>
	 * orderid : 采购订单编号<br/>
	 * ischeckauth : 是否检查数据权限,默认检查，0时不检查<br/>
	 * showcanbuygoods : 有值时，查询可购买商品,判断未入库数量是否大于0<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List<BuyOrderDetail> showBuyOrderDetailListBy(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据订单编号删除采购订单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deleteBuyOrderDetailByOrderid(String orderid) throws Exception;
	/**
	 * 更新采购明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateBuyOrderDetail(BuyOrderDetail buyOrderDetail) throws Exception;

	/**
	 * 检查如果有上游采购计划单据编号，获取最大采购订单数量
	 * @param unitnum
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public BigDecimal getMaxBuyUnitnum(String billdetailno) throws Exception;
	/**
	 * 获取采购订单明细信息,未做字段权限或数据权限检查
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public BuyOrderDetail showPureBuyOrderDetail(String id) throws Exception;
	/**
	 * 采购订单回写
	 * @param buyOrderDetail
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-17
	 */
	public boolean updateBuyOrderDetailWriteBack(BuyOrderDetail buyOrderDetail) throws Exception;
	/**
	 * 提交采购单到工作流
	 * @param title
	 * @param userId
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jul 2, 2013
	 */
	public boolean submitBuyOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;
	/**
	 * 导出采购订单明细
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-14
	 */
	public List getBuyOrderDetailExport(PageMap pageMap) throws Exception;
	
	/**
	 * 获取采购订单列表<br/>
	 * map中参数:<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showBuyOrderListBy(Map map) throws Exception;
	/**
	 * 更新打印次数
	 * @param buyOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public boolean updateOrderPrinttimes(BuyOrder buyOrder) throws Exception;
	/**
	 * 根据报表生成采购订单
	 * @param list
	 * @param remark
	 * @param queryForm
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年1月23日
	 */
	public Map addBuyOrderByReport(List<StorageBuySaleReport> list,String remark,String queryForm) throws Exception;

	/**
	 * 关闭采购订单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public Map closeBuyOrder(String id) throws Exception;
	

    /**
     * 根据商品编码显示历史价格表数据页面
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016年3月16日
     */
    public List<Map> getPurchaseHistoryGoodsPriceList(Map map)throws  Exception;

    /**
     * 批量审核采购订单
     * @authro lin_xx
     * @data 2016-5-24
     */
    public Map auditMultiBuyOrder(String ids) throws Exception ;
	
}

