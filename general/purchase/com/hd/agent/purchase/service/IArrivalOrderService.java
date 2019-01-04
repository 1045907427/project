/**
 * @(#)IArrivalOrderMapper.java
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

import com.hd.agent.account.model.PurchaseInvoice;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ArrivalOrder;
import com.hd.agent.purchase.model.ArrivalOrderDetail;

import java.util.List;
import java.util.Map;

/**
 * 采购进货单 Service 接口
 * 
 * @author zhanghonghui
 */
public interface IArrivalOrderService {
	/**
	 * 向数据库添加一条采购到货单
	 * @param plannedOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	//public boolean insertArrivalOrder(ArrivalOrder arrivalOrder) throws Exception;
	/**
	 * 添加采购进货单包括采购计划明细
	 * @param arrivalOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-10
	 */
	public boolean addArrivalOrderAddDetail(ArrivalOrder arrivalOrder) throws Exception;
	/**
	 * 更新采购进货单及采购明细
	 * @param arrivalOrder
	 * @return
	 * @throws Exception
	 */
	public boolean updateArrivalOrderAddDetail(ArrivalOrder arrivalOrder) throws Exception;
	/**
	 * 获取采购进货单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ArrivalOrder showArrivalOrderAndDetail(String id) throws Exception;
	
	/**
	 * 获取采购进货单详细信息和明细列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 7, 2013
	 */
	public ArrivalOrder getArrivalOrder(String id)throws Exception;
	/**
	 * 删除采购进货单及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deleteArrivalOrderAndDetail(String id) throws Exception;
	/**
	 * 获取采购进货单明细，未经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ArrivalOrder showPureArrivalOrder(String id) throws Exception;
	/**
	 * 获取采购进货单,经过字段和数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ArrivalOrder showArrivalOrder(String id) throws Exception;
	/**
	 * 获取采购进货单,经过数据权限检查的数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public ArrivalOrder showArrivalOrderByDataAuth(String id) throws Exception;
	/**
	 * 显示分页数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-17
	 */
	public PageData showArrivalOrderPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据参数更新采购进货单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * arrivalOrder :采购进货单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean updateArrivalOrderBy(Map map) throws Exception;
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map auditArrivalOrder(String id) throws Exception;
	/**
	 * 反审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-3
	 */
	public Map oppauditArrivalOrder(String id) throws Exception;
	/**
	 * 工作流审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-31
	 */
	public boolean auditWorkflowArrivalOrder(String id) throws Exception;
	
	/**
	 * 根据上游采购订单编号，查找采购到货单信息
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-30
	 */
	public ArrivalOrder showArrivalOrderAndDetailByBillno(String billno) throws Exception;
	
	/**
	 * 设置采购进货单引用 标志
	 * @param id
	 * @param isrefer 1表示引用，0表示未引用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public boolean updateArrivalOrderRefer(String id,String isrefer) throws Exception;
	
	/**
	 * 更新采购进货单状态信息（审核、关闭、中止）<br/>
	 * 审核时更新字段<br/>
	 * isrefer : 是否被引用，1引用，0未引用<br/>
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
	 * @param arrivalOrder
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-10
	 */
	public boolean updateArrivalOrderStatus(ArrivalOrder arrivalOrder) throws Exception;
	
	/**
	 * 删除采购进货单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-17
	 */
	public boolean deleteArrivalOrder(String id) throws Exception;
	
	//------------------------------------------------------------------------//
	//采购进货单详细
	//-----------------------------------------------------------------------//
	
	
	/**
	 * 添加采购进货单详细
	 * @param arrivalOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-9
	 */
	public boolean insertArrivalOrderDetail(ArrivalOrderDetail arrivalOrderDetail) throws Exception;
	/**
	 * 采购进货单明细分页列表
	 * @param Orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showArrivalOrderDetailListByOrderId(String Orderid) throws Exception;

	/**
	 * 根据采购进货单编号，获取采购进货单明细分页列表，不判断权限
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showPureArrivalOrderDetailListByOrderId(String orderid) throws Exception;
	
	/**
	 * 根据到货单编号删除采购进货单明细
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean deleteArrivalOrderDetailByOrderid(String orderid) throws Exception;
	/**
	 * 更新采购明细
	 * @param arrivalOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateArrivalOrderDetail(ArrivalOrderDetail arrivalOrderDetail) throws Exception;
	/**
	 * 获取采购进货明细信息,未做字段权限或数据权限检查
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	public ArrivalOrderDetail showPureArrivalOrderDetail(String id) throws Exception;
	/**
	 * 根据采购入库单编号，获取入库单明细
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-1
	 */
	public List showArrivalOrderDetailUpReferList(String billno) throws Exception;

	public List addArrivalOrderDiscount(Map map) throws Exception ;
	/**
	 * 用于下游参照采购进货单，显示商品明细列表
	 * @param orderidarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-7-1
	 */
	public List showArrivalOrderDetailDownReferList(String orderidarrs) throws Exception;
	/**
	 * 提交采购进货通知单到工作流
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
	public boolean submitArrivalOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception;

	/**
	 * 根据采购进货单编码和商品编码获取采购进货单详情信息
	 * @param orderid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 11, 2013
	 */
	public ArrivalOrderDetail getArrivalOrderDetailByOrderidAndGoodsId(String orderid,String goodsid)throws Exception;
	/**
	 * 根据采购进货单编号获取采购进进货单及其明细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public ArrivalOrder getArrivalOrderByPrint(String id) throws Exception;
	
	/**
	 * 更新打印次数
	 * @param arrivalOrder
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateOrderPrinttimes(ArrivalOrder arrivalOrder) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public void updateOrderPrinttimes(List<ArrivalOrder> list) throws Exception;
	
	/**
	 * 获取采购进货单列表<br/>
	 * map中参数:<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showArrivalOrderListBy(Map map) throws Exception;
	/**
	 * 更新采购进货单明细备注
	 * @param arrivalOrderDetail
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-26
	 */
	public boolean updateArrivalOrderDetailRemark(ArrivalOrderDetail arrivalOrderDetail) throws Exception;

    /**
     * 反核销 根据单据编号和单据类型,以及发票明细数据 更新单据的核销状态
     * @param sourceidList
     * @param purchaseInvoice
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public boolean updateArrivalAndPurchaseEnterUncancel(List<String> sourceidList, PurchaseInvoice purchaseInvoice)throws Exception;

    /**
     * 根据采购进货单编号 获取金额
     * @param idarr
     * @return
     */
    public List<Map> getSupplierArrivalSumData(List idarr);

	/**
	 * 根据采购进货单编号 获取金额
	 * @param idarr
	 * @return
	 */
	public List<Map> getSupplierArrivalSumDataForThird(List idarr);

	/**
	 * 采购进货单添加分摊记录
	 * @param map
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Jan 05, 2018
	 */
	public Map addPurchaseShareAmount(Map map)throws Exception;

	/**
	 * 获取采购费用分摊记录
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public PageData getPurchaseShareLogDate(PageMap pageMap) throws Exception;

	/**
	 * 采购运费取消分摊
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public Map cancelArrivalPurchaseShare(String id);

	/**
	 * 判断采购进货单是否分摊过
	 * @param id
	 * @return java.lang.Boolean
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public Boolean isArrivalOrderShare(String id);
}

