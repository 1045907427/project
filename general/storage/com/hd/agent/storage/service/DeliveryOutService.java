/**
 * @(#)DeliveryOutService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月13日 huangzhiqian 创建版本
 */
package com.hd.agent.storage.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageDeliveryOut;
import com.hd.agent.storage.model.StorageDeliveryOutDetail;
import com.hd.agent.storage.model.StorageSummaryBatch;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface DeliveryOutService {
	/**
	 * 保存 添加数据
	 * @param storageDeliveryOut
	 * @param list
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月13日
	 */
	Map<String,Object> insertDatas(StorageDeliveryOut storageDeliveryOut, List list) throws  Exception;
	/**
	 * 查询datagrid列表
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	PageData getStorageDeliveryOutList (PageMap pageMap) throws Exception;
	/**
	 * 根据id删除 主表,详情表信息
	 * @param string
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	boolean deleteOutAndDetailById(String string)throws Exception;
	
	
	/**
	 * 根据billIdid删除详情表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	int deleteDetailsByBillId(String id);
	
	/**
	 * 根据id查询主表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	StorageDeliveryOut getStorageDeliveryOutById(String id);
	/**
	 * 查询主表和详情表信息
	 * @param id
	 * @return list(0) 主表    list(1) 详情表beanList
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	List<Object> getEnterAndDetailById(String id) throws Exception;
	/**
	 * 更新主表,详情表
	 * @param storageDeliveryOut
	 * @param bodList
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	Map updataEnterAndDetail(StorageDeliveryOut storageDeliveryOut,
			List<StorageDeliveryOutDetail> bodList)throws Exception;
	/**
	 * 保存主表
	 * @param saveout
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月17日
	 */
	boolean updateDeliveryOut(StorageDeliveryOut saveout);
	/**
	 * 审核
	 */
	boolean audit(String id,StorageDeliveryOut updateOut) throws Exception;
	/**
	 * 根据主键id查询Detail
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月1日
	 */
	StorageDeliveryOutDetail getDeliveryOutDetailById(String id);
	/**
	 * 根据客户id获取上游单据客户名称
	 * @param customerid
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月11日
	 */
	String getUpCustomerName(String sourceid);
	/**
	 * 反审
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月15日
	 */
	Map oppauditDeliveryOut(String id)throws Exception;
	/**
	 * 批量验收
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月15日
	 */
	Map batchCheck(String idarrs)throws Exception;
	/**
	 * 代配送定时任务,处理所有逻辑
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月30日
	 */
	boolean doEntersForJob()throws Exception;
	/**
	 * 打印
	 * @param map
	 * @return
	 * @author huangzhiqian
	 * @date 2015年10月12日
	 */
	List<StorageDeliveryOut> printDeliveryOut(Map map)throws Exception;
	/**
	 * 更新打印次数
	 * @param id
	 * @author huangzhiqian
	 * @date 2015年10月12日
	 */
	void updateOrderPrinttimes(String id)throws Exception;
	/**
	 * 获取批次库存信息
	 * @param goodsId
	 * @param storageid
	 * @param batchno
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月17日
	 */
	StorageSummaryBatch getStorageSummaryBatchByStorageidAndGoodsidAndBatchNo(String goodsId, String storageid, String batchno)throws Exception ;
}

