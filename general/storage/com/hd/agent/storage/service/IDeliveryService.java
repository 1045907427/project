/**
 * @(#)IDeliveryService.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 29, 2014 yezhenyu 创建版本
 */
package com.hd.agent.storage.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.Delivery;
import com.hd.agent.storage.model.DeliveryCustomer;
import com.hd.agent.storage.model.DeliverySaleOut;
import com.hd.agent.storage.model.Saleout;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yezhenyu
 */
public interface IDeliveryService {

	/**
	 * 获取发货单列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 30, 2014
	 */
	PageData getSaleOutListForDelivery(PageMap pageMap) throws Exception;
	
	/**
	 * 获取需添加发货单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 10, 2014
	 */
	public PageData getSaleOutListForAddDelivery(PageMap pageMap)throws Exception;

	/**
	 * 根据saleoutid获取Saleout
	 * 
	 * @param saleoutid
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 3, 2014
	 */
	Saleout getSaleout(String saleoutid) throws Exception;

	/**
	 * 根据saleout的ids&deliveryid获取DeliveryCustomer列表
	 * @param ids
	 * @param deliveryid
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 23, 2014
	 */
	public List getDeliveryCustomerByIds(String ids,String deliveryid,String lineid);
	
	/**
	 * 根据配送单编号获取手动添加的交接单列表
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 29, 2014
	 */
	public List getDeliveryCustomerNoRelateSaleout(String id,String delcustomerid);

	/**
	 * 添加配送单
	 * 
	 * @param delivery
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 4, 2014
	 */
	boolean addDelivery(Delivery delivery, String ids, List<DeliverySaleOut> deliverySaleoutList,
			List<DeliveryCustomer> deliveryCustomerList) throws Exception;

	/**
	 * 添加配送单关联的送货单
	 * 
	 * @param deliverySaleoutList
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 4, 2014
	 */
	int addDeliverySaleOutList(List<DeliverySaleOut> deliverySaleoutList) throws Exception;

	/**
	 * 获取配送单列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 5, 2014
	 */
	PageData showDeliveryList(PageMap pageMap) throws Exception;

	/**
	 * 修改saleout的isdelivery标志
	 * 
	 * @param ids
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 5, 2014
	 */
	boolean changeSaleOutDeliveryStatus(String ids, int flag) throws Exception;

	/**
	 * 显示配送单_交接单列表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 7, 2014
	 */
	Map getDeliveryCustomerList(String id) throws Exception;
	
	/**
	 * 显示配送单_交接单列表（去除虚拟删除的交接单）
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2014
	 */
	Map getDeliveryCustomerList(Map map)throws Exception;

	/**
	 * 添加配送单关联的交接单
	 * 
	 * @param deliveryCustomerList
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 9, 2014
	 */
	int addDeliveryCustomerList(List<DeliveryCustomer> deliveryCustomerList) throws Exception;

	/**
	 * 获取配送单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 9, 2014
	 */
	Delivery getDelivery(String id) throws Exception;

	/**
	 * 显示配送单_发货单列表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 10, 2014
	 */
	Map getDeliverySaleoutList(String id) throws Exception;

	/**
	 * 显示配送单_商品汇总
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Nov 21, 2014
	 */
	public List<Map> getDeliveryGoodsSumData(String id) throws Exception;

	/**
	 * 获取装车次数
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 12, 2014
	 */
	int getTruckCount(Map map) throws Exception;
	
	/**
	 * 根据saleoutids显示配送单_交接单列表
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2014
	 */
	public Map getDeliveryCustomerListBySaleoutids(String id,String lineid)throws Exception;

	/**
	 * 编辑配送单
	 * 
	 * @param delivery
	 * @param saleoutList
	 * @param customerList
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Jun 14, 2014
	 */
	Map editDelivery(Delivery delivery, List<DeliverySaleOut> saleoutList, List<DeliveryCustomer> customerList)
			throws Exception;

	/**
	 * 批量审核配送单
	 * 
	 * @param map
	 * @return
	 * @Exception
	 * @author yezhenyu
	 * @date Jun 16, 2014
	 */
	boolean doAuditDeliverys(Map map) throws Exception;

	/**
	 * 修改配送单验收状态
	 * 
	 * @param id
	 * @param flag
	 * @return
	 * @Exception
	 * @author yezhenyu
	 * @date Jun 18, 2014
	 */
	boolean changeCheckDelivery(String id, String flag)throws Exception;

	/**
	 * 添加物流报表信息
	 * @param id
	 * @return
	 * @Exception
	 * @author yezhenyu 
	 * @date Jun 19, 2014
	 */
	int addLogisticsReport(String id,String type)throws Exception;

	/**
	 * 更新配送单
	 * @param delivery
	 * @return
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jun 20, 2014
	 */
	boolean updateDelivery(Delivery delivery)throws Exception;

	/**
	 * 更新交接单
	 * @param deliveryCustomer
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jun 20, 2014
	 */
	void updateDeliveryCustomer(DeliveryCustomer deliveryCustomer)throws Exception;

	/**
	 * 将配送单设置为关闭状态
	 * @param delivery
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jun 23, 2014
	 */
	void closeDelivery(Delivery delivery)throws Exception;

	/**
	 * 批量删除配送单
	 * @param map
	 * @return
	 * @throws Exception
	 * @author yezhenyu 
	 * @param deliveryIds 
	 * @date Jun 23, 2014
	 */
	boolean deleteDeliverys(Map map, String deliveryIds)throws Exception;

	/**
	 * 更新打印次数
	 * @param id
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jun 26, 2014
	 */
	void updatePrintTimes(String id)throws Exception;

	/**
	 * 保存审核配送单
	 * @param saleout
	 * @param customer
	 * @param saleoutid
	 * @param delivery 
	 * @throws Exception
	 * @return
	 * @author yezhenyu 
	 * @date Jul 7, 2014
	 */
	Map saveAuditDelivery(Delivery delivery, String saleout, String customer)throws Exception;

	/**
	 * 审核配送单
	 * @param oldIdStr
	 * @return
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jul 7, 2014
	 */
	Map auditDeliverys(String oldIdStr)throws Exception;

	/**
	 * 反审配送单
	 * @param oldIdStr
	 * @return
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jul 8, 2014
	 */
	Map oppauditDeliverys(String oldIdStr)throws Exception;

	/**
	 * 验收配送单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu 
	 * @param customer 
	 * @date Jul 8, 2014
	 */
	Map doCheckDelivery(String id, String customer)throws Exception;

	/**
	 * 反验收配送单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author yezhenyu 
	 * @date Jul 8, 2014
	 */
	Map doUnCheckDelivery(String id)throws Exception;
	
	/**
	 * 根据装车次数变动获取装车补助
	 * @param truckstr
	 * @param carid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 15, 2014
	 */
	public Map getTruckSubsidyByTruck(String truckstr, String carid)throws Exception;
	
	/**
	 * 根据送货家数变动获取家数补助
	 * @param customernumsStr
	 * @param lineid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 15, 2014
	 */
	public Map getCustomersubsidyByCustomernums(String customernumsStr,String lineid)throws Exception;
	
	/**
	 * 根据装车次数，车辆编号，出车日期判断是否已存在审核通过的配送单true存在，false不存在
	 * @param truckstr
	 * @param carid
	 * @param businessdate
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public boolean isExistSameTruck(String truckstr,String carid,String businessdate)throws Exception;

	/**
	 * 查询出DeliverySaleOut 和代配送客户出库
	 * @param ids
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月9日
	 */
	List<DeliverySaleOut> getDeliverySaleAndDeliveryListByIds(String ids);
	

}
