/**
 * @(#)DeliveryMapper.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 29, 2014 yezhenyu 创建版本
 */
package com.hd.agent.storage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.LogisticsReport;
import com.hd.agent.storage.model.Delivery;
import com.hd.agent.storage.model.DeliveryCustomer;
import com.hd.agent.storage.model.DeliverySaleOut;
import com.hd.agent.storage.model.Saleout;

/**
 * 
 * 
 * @author yezhenyu
 */
public interface DeliveryMapper {

	/**
	 * 获取需添加发货单列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 10, 2014
	 */
	public List getSaleOutListForAddDelivery(PageMap pageMap);
	
	/**
	 * 获取需添加发货单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 10, 2014
	 */
	public int getSaleOutListForAddDeliveryCount(PageMap pageMap);

	/**
	 * 根据saleoutid获取Saleout
	 * 
	 * @param saleoutid
	 * @return
	 * @author yezhenyu
	 * @date Jun 3, 2014
	 */
	Saleout getSaleout(String saleoutid);

	/**
	 * 根据saleout的ids获取DeliverySaleOut列表
	 *
	 * @param map
	 * @return
	 * @author yezhenyu
	 * @date Jun 3, 2014
	 */
	public List getDeliverySaleOutListByIds(Map map);

	/**
	 * 根据saleout的ids获取DeliveryCustomer列表
	 * 
	 * @param ids
	 * @return
	 * @author yezhenyu
	 * @date Jun 7, 2014
	 */
	List getDeliveryCustomerByIds(@Param("ids")String ids,@Param("printnum")int printnum,@Param("status")String status,@Param("lineid")String lineid);
	
	/**
	 * 根据saleout的ids,配送单编号获取DeliveryCustomer列表(排除虚拟删除的交接单)
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 29, 2014
	 */
	public List getDeliveryCustomerListByMap(Map map);
	/**
	 * 根据配送单编号获取手动添加的交接单列表
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Sep 29, 2014
	 */
	public List getDeliveryCustomerNoRelateSaleout(Map map);
	
	/**
	 * 添加配送单
	 * 
	 * @param delivery
	 * @return
	 * @author yezhenyu
	 * @date Jun 4, 2014
	 */
	int addDelivery(Delivery delivery);

	/**
	 * 添加配送单关联的送货单
	 * 
	 * @param deliverySaleOut
	 * @return
	 * @author yezhenyu
	 * @date Jun 4, 2014
	 */
	int addDeliverySaleOut(DeliverySaleOut deliverySaleOut);

	/**
	 * 获取配送单列表数量
	 * 
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date Jun 5, 2014
	 */
	int getDeliveryListCount(PageMap pageMap);

	/**
	 * 获取配送单列表
	 * 
	 * @param pageMap
	 * @return
	 * @author yezhenyu
	 * @date Jun 5, 2014
	 */
	List getDeliveryList(PageMap pageMap);

	/**
	 * 修改saleout的isdelivery标志
	 * 
	 * @param flag
	 * @return
	 * @author yezhenyu
	 * @date Jun 5, 2014
	 */
	int changeSaleOutDeliveryStatus(@Param("id")String id, @Param("flag")int flag);

	/**
	 * 修改代配送出库单的isdelivery标志
	 * @param id
	 * @param flag
	 * @return
	 */
	int changeDeliveryOutDeliveryStatus(@Param("id")String id, @Param("flag")int flag);

	/**
	 * 根据配送单编号修改是否生成配送单标记
	 * @param flag
	 * @param flag
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 10, 2014
	 */
	public int updateSaleOutIsdelivery(@Param("deliveryid")String deliveryid, @Param("flag")int flag);

	/**
	 * 供应商代配送客户订单配送状态修改
	 * @param deliveryid
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月14日
	 */
	public int updateDeliveryOutIsdelivery(@Param("deliveryid")String deliveryid, @Param("flag")int flag);

	/**
	 * 供应商代配送客户订单配送状态修改
	 * @param deliveryid
	 * @return
	 * @author limin
	 * @date Nov 22, 2017
	 */
	public int updateAllocateOutIsdelivery(@Param("deliveryid")String deliveryid, @Param("flag")int flag);

	/**
	 * 显示配送单_交接单列表
	 * 
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date Jun 7, 2014
	 */
	List<DeliveryCustomer> getDeliveryCustomerList(String id);

	/**
	 * 添加配送单_交接单
	 * 
	 * @param deliveryCustomer
	 * @return
	 * @author yezhenyu
	 * @date Jun 9, 2014
	 */
	int addDeliveryCustomerList(DeliveryCustomer deliveryCustomer);

	/**
	 * 获取配送单
	 * 
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date Jun 9, 2014
	 */
	Delivery getDelivery(String id);

	/**
	 * 显示配送单_发货单列表
	 * 
	 * @param id
	 * @return
	 * @author yezhenyu
	 * @date Jun 10, 2014
	 */
	List<DeliverySaleOut> getDeliverySaleoutList(String id);

	/**
	 * 获取装车次数
	 * 
	 * @param map
	 * @return
	 * @author yezhenyu
	 * @date Jun 12, 2014
	 */
	int getTruckCount(Map map);
	
	/**
	 * 判断是否存在最有一次装车
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 10, 2014
	 */
	public int checkIsLastTruck(Map map);

	/**
	 * 删除DeliverSaleOut
	 * 
	 * @param id
	 * @author yezhenyu
	 * @date Jun 14, 2014
	 */
	void deleteDeliverySaleOut(String id);

	/**
	 * 删除DeliveryCustome
	 * 
	 * @param id
	 * @author yezhenyu
	 * @date Jun 14, 2014
	 */
	void deleteDeliveryCustomer(String id);

	/**
	 * 更新配送单
	 * 
	 * @param delivery
	 * @return
	 * @author yezhenyu
	 * @date Jun 14, 2014
	 */
	int updateDelivery(Delivery delivery);

	/**
	 * 批量审核配送单
	 * 
	 * @param map
	 * @return
	 * @author yezhenyu
	 * @date Jun 16, 2014
	 */
	int auditDeliverys(Map map);

	/**
	 * 修改配送单验收状态
	 * 
	 * @param id
	 * @param flag
	 * @return
	 * @author yezhenyu
	 * @date Jun 18, 2014
	 */
	int changeCheckDelivery(@Param("id") String id, @Param("flag") String flag);

	/**
	 * 添加物流报表
	 * @param logisticsReport
	 * @return
	 * @author yezhenyu 
	 * @date Jun 19, 2014
	 */
	int addLogisticsReport(LogisticsReport logisticsReport);

	/**
	 * 更新交接单
	 * @param deliveryCustomer
	 * @return
	 * @author yezhenyu 
	 * @date Jun 20, 2014
	 */
	int updateDeliveryCustomer(DeliveryCustomer deliveryCustomer);

	/**
	 * 将配送单设置为关闭状态
	 * @param delivery
	 * @return
	 * @author yezhenyu 
	 * @date Jun 23, 2014
	 */
	int closeDelivery(Delivery delivery);

	/**
	 * 批量删除配送单
	 * @param map
	 * @return
	 * @author yezhenyu 
	 * @date Jun 23, 2014
	 */
	int deleteDeliverys(Map map);

	/**
	 * 更新打印次数
	 * @param id
	 * @return
	 * @author yezhenyu 
	 * @date Jun 26, 2014
	 */
	int updatePrintTimes(String id);

	/**
	 * 反审配送单
	 * @param map
	 * @return
	 * @author yezhenyu 
	 * @date Jul 8, 2014
	 */
	int oppauditDeliverys(Map map);

	/**
	 * 反验收配送单
	 * @param id
	 * @return
	 * @author yezhenyu 
	 * @date Jul 8, 2014
	 */
	int unCheckDelivery(String id);

	/**
	 * 删除配送单对应的物流报表
	 * @param id
	 * @return
	 * @author yezhenyu 
	 * @date Jul 8, 2014
	 */
	int deleteLogisticsReport(String id);
	
	/**
	 * 根据装车次数，车辆编号，出车日期判断是否已存在审核通过的配送单
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 11, 2014
	 */
	public int isExistSameTruck(Map map);
	
	/**
	 * 根据配送单编号，客户编码集获取配送单单据明细列表
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 24, 2014
	 */
	public List getDeliverySaleOutListByParam(Map map);
	
	/**
	 * 根据传递的参数删除配送单发货单据
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 24, 2014
	 */
	public int deleteDeliverySaleOutByMap(Map map);

    /**
     * 验收成功后回写线路对应客户装车次数(增加)
     * @param lineid
     * @param deliveryid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-20
     */
    public int addLineInfoCustomerCarNum(@Param("lineid")String lineid, @Param("deliveryid")String deliveryid);
    /**
     * 验收成功后回写线路对应客户装车次数（减去）
     * @param lineid
     * @param deliveryid
     * @return
     * @author panxiaoxiao
     * @date 2015-03-20
     */
    public int deleteLineInfoCustomerCarNum(@Param("lineid")String lineid, @Param("deliveryid")String deliveryid);
    /**
     * 根据客户编号，按品牌拆分单据所生成的单据数<br/>
     * Map 中参数：<br/>
     * saleoutidarr : 发货单编号字符串，多个编号以,分隔<br/>
     * printnum : 每页面条数<br/>
     * statusarr : 状态字符串，多个状态以,分隔<br/>
     * customerid: 客户编号<br/>
     * @param map
     * @return
     * @author zhanghonghui 
     * @date 2015年8月14日
     */
    public int getDeliveryBrandSplitBillCountByCustomer(Map map);
    

	/**
	 * 根据条件查询 代配送,销售 配送单
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月9日
	 */
	List<DeliverySaleOut> getSaleOutAndDeliveryOutListForDelivery(PageMap pageMap);

	/**
	 * 根据条件查询 代配送,销售 配送单数量
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月9日
	 */
	int getSaleOutAndDeliveryOutCountForDelivery(PageMap pageMap);
	
	/**
	 * 查询出所有配送(包括代配送)
	 * @param ids
	 * @param printnum
	 * @param deliveryid
	 * @param status
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月9日
	 */
	List<DeliveryCustomer> getCustomerListByAllDelevery(@Param("ids")String ids,@Param("printnum")int printnum,@Param("deliveryid")String deliveryid,@Param("status")String status,@Param("lineid")String lineid);

	/**
	 * 根据DeliveryId查询物流配送单对应发货单
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年10月16日
	 */
	List<DeliverySaleOut> getDeliveryOutByDeliveryId(String id);

	/**
	 * 未关联发货单的合计数据
	 * @param deliveryid
	 * @return
	 * @author panxiaoxiao
	 * @date 2017-01-23
	 */
	public DeliveryCustomer getDeliveryCustomerNoRelateSaleoutSum(@Param("deliveryid")String deliveryid);

	/**
	 * 获取配送单商品汇总信息
	 *
	 * @param list
	 * @return
	 * @author limin
	 * @date Nov 21, 2017
	 */
	public List<Map> selectDeliveryGoodsSumData(List<DeliverySaleOut> list);
}
