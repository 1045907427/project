/**
 * @(#)IStorageSaleOutPrintService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-8-11 zhanghonghui 创建版本
 */
package com.hd.agent.storage.service;

import com.hd.agent.storage.model.SaleoutDetail;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface IStorageSaleOutPrintService {

	/**
	 * 获取销售出库单打印明细列表<br/>
	 * map中参数：<br/>
	 * status : 状态<br/>
	 * statusarr : 状态字符串数组<br/>
	 * dispatchbillid : 销售发货通知单<br/>
	 * saleorderid : 销售订单<br/>
	 * notphprint : 配货是否未打印，1未打印<br/>
	 * notprint : 发货是否未打印，1未打印<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date Jun 5, 2013
	 */
	public List getSaleOutDetailPrintListBy(Map map) throws Exception;
	
	/**
	 * 在发货单处打印,更新打印次数并更新上游单据打印次数(发货及配货),参数为更新单据编号字符串组，并根据下游是否打印完成，同步上游单据
	 * @param id
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-11
	 */
	public void updatePrinttimesSyncUpOrder(String id) throws Exception;
	/**
	 * 在发货单处打印,更新打印次数并更新上游单据打印次数(发货及配货),参数为更新单据编号字符串组，并根据下游是否打印完成，同步上游单据
	 * @param ids
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-11
	 */
	public void updatePrinttimesSyncUpOrderIds(String ids) throws Exception;
	/**
	 * 在发货单处打印， 更新配货单打印次数并更新上游单据配货打印次数，并根据下游是否打印完成，同步上游单据
	 * @param saleoutId
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-6
	 */
	public void updatePhPrinttimesSyncUpOrder(String saleoutId) throws Exception;
	
	/**
	 * 更新大单发货打印次数，并同步更新发货单发货打印次数
	 * @param bigsaleoutid
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 25, 2014
	 */
	public void updatePrinttimesSyncUpBigSalout(String bigsaleoutid)throws Exception;
	
	/**
	 * 在发货单处打印，更新发货单打印次数并更新上游单据发货打印次数，并根据下游是否打印完成，同步上游单据
	 * @param saleoutId
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-6
	 */
	public void updateFhPrinttimesSyncUpOrder(String saleoutId) throws Exception;
	
	/**
	 * 按销售订单顺序打印更新打印次数
	 * @param dispatchid
	 * @param idarrs
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-8
	 */
	public void updateSalesDispatchBillPrinttimes(String dispatchid,String idarrs) throws Exception;

	/**
	 * 按销售订单顺序打印更新打印次数
	 * @param salesid
	 * @param idarrs
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-8
	 */
	public void updateSalesDispatchBillPrinttimesForXSD(String salesid,String idarrs) throws Exception;
	/**
	 * 在发货通知单处打印，更新打印次数并更新上游单据打印次数(发货及配货),判断打印传入的打印次数是否加1
	 * @param id
	 * @param dphprintimes 原配货打印次数
	 * @param dfprintimes 原发货打印次数
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-17
	 */
	public void updatePrinttimesSyncOrder(String id,String dphprintimes,String dfprintimes) throws Exception;
	/**
	 * 在销售订单处打印，更新打印次数并更新销售单打印次数(发货及配货),判断打印传入的打印次数是否加1
	 * @param id
	 * @param sphprintimes 原配货打印次数
	 * @param sfprintimes 原发货打印次数
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-17
	 */
	public void updatePrinttimesSyncSalesOrder(String id,String sphprintimes,String sfprintimes) throws Exception;

	/**
	 * 在发货通知单处打印时，更新配货单打印次数并更新发货通知单据配货打印次数,判断打印传入的打印次数是否加1
	 * @param saleoutId
	 * @param dispatchPrintCount 打印时，配货打印次数
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-17
	 */
	public void updatePhPrinttimesSyncOrder(String saleoutId,String dispatchPrintCount) throws Exception;
	/**
	 * 在销售单处时，更新配货单打印次数并更新销售订单据配货打印次数,判断打印传入的打印次数是否加1
	 * @param saleoutId
	 * @param salesPrintCount 打印时，配货打印次数
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-17
	 */
	public void updatePhPrinttimesSyncSalesOrder(String saleoutId,String salesPrintCount) throws Exception;
	
	/**
	 * 获取打印大单发货单商品明细列表数据<br/>
	 * map中参数：<br/>
	 * id:编号
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 24, 2014
	 */
	public List<Map> getBigSaleOutGoodsListForPrint(Map map)throws Exception;

	/**
	 * 获取打印大单发货单按商品分客户明细列表数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 10, 2014
	 */
	public List<Map> getBigSaleOutGoodsCustomerListForPrint(Map map)throws Exception;
	
	/**
	 * 获取打印大单发货单按商品分客户分区块明细列表数据
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 10, 2014
	 */
	public Map getBigOutGoodsCustomerDivMapForPrint(Map map)throws Exception;
	

	/**
	 * 获取打印大单发货单按品牌商品分区块明细列表数据
	 * @param map
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-09-26
	 */
	public List<Map> getBigOutBrandGoodsDivMapForPrint(Map map)throws Exception;

	/**
	 * 更新发货通知单打印次数<br/>
	 * map 中参数：<br/>
	 * id 条件，发货单编号<br/>
	 * idarrs 条件，以,分隔的发货单编号字符串<br/>
	 * saleorderid 条件，销售订单编号<br/>
	 * sourceid 条件，来源单据号<br/>
	 * phprinttimes 原配货打印次数,如果为true直接更新，false不更新 <br/>
	 * printtimes 原发货打印次数,如果为true直接更新，false不更新 <br/>
	 * statusarr :发货时 发货单打印时状态<br/>
	 * @param map
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2014-5-17
	 */
	public void updateSaleoutPrinttimesBy(Map map) throws Exception;
	
	/**
	 * 在发货通知单处打印，只更新发货通知单打印次数，判断打印传入的打印次数是否加1<br/>
	 * map 中参数：<br/>
	 * id 发货通知单编号<br/>
	 * phprinttimes 如果为true直接更新，false不更新 <br/>
	 * fhprinttimes 如果为true直接更新，false不更新 <br/>
	 * phprinttimes 和 fhprinttimes 都为false时，都要更新
	 * @param map 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-17
	 */
	public void updateDispatchBillPrinttimesBy(Map map) throws Exception;
	
	
	/**
	 * 在销售单处打印，只更新销售订单打印次数，判断打印传入的打印次数是否加1<br/>
	 * map 中参数：<br/>
	 * id 发货通知单编号<br/>
	 * phprinttimes 原配货打印次数,如果为true直接更新，false不更新 <br/>
	 * fhprinttimes 原发货打印次数,如果为true直接更新，false不更新 <br/>
	 * phprinttimes 和 fhprinttimes 都没有设置时，默认都要更新<br/>
	 * upSaleoutPrinttimes : 1 更新发货单打印次数 <br/>
	 * upDispatchPrinttimes : 1 更新发货通知单打印次数 <br/>
	 * saleoutidarr: 要打印的发货单编号数组,upSaleoutPrinttimes=1时有效<br/>
	 * saleout_statusarrs : 发货时，发货单打印时状态,upSaleoutPrinttimes=1时有效<br/>
	 * ph_saleout_statusarrs : 配货时，发货单打印时状态,upSaleoutPrinttimes=1时有效<br/>
	 * dispatch_statusarrs: 发货时，发货通知单打印时状态,upDispatchPrinttimes=1时有效<br/>
	 * ph_dispatch_statusarrs: 配货时，发货通知单打印时状态,upDispatchPrinttimes=1时有效<br/>
	 * @param map 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-17
	 */
	public void updateSaleOrderPrinttimesBy(Map map) throws Exception;

}

