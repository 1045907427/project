/**
 * @(#)IDistributeRejectService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月4日 huangzhiqian 创建版本
 */
package com.hd.agent.storage.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageDeliveryEnter;
import com.hd.agent.storage.model.StorageDeliveryEnterDetail;

/**
 * 
 * 
 * @author huangzhiqian
 */
public interface IDistributeRejectService {
	/**
	 * 查询所有配送入库单
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年8月4日
	 */
	PageData getStorageDeliveryEnterList(PageMap pageMap) throws Exception;
	/**
	 * 获取指定GOODSID的体积,重量相关信息
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月7日
	 */
	Map getGoodsInfo(String goodsId);
	/**
	 * 添加入库单和明细单
	 * @param storageDeliveryEnter
	 * @param list
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月10日
	 */
	boolean insertDatas(StorageDeliveryEnter storageDeliveryEnter, List list) throws Exception;
	/**
	 * 根据id查询入库单和入库单详细
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月10日
	 */
	List getDistributeRejectInfoByID(String id) throws Exception;
	/**
	 * 根据id查找入库单主表信息
	 * @param id
	 * @author huangzhiqian
	 * @return 
	 * @date 2015年8月11日
	 */
	StorageDeliveryEnter getDistributeRejectEnterById(String id);
	/**
	 * 更新主表和详情表
	 * @param storageDeliveryEnter
	 * @param bodList
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月11日
	 */
	boolean updataEnterAndDetail(StorageDeliveryEnter storageDeliveryEnter,
			List<StorageDeliveryEnterDetail> bodList) throws Exception;
	/**
	 * 根据主表id删除详情表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月11日
	 */
	int deleteDetailsById(String id);
	
	/**
	 * 根据id删除主表信息和详情表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月12日
	 */
	boolean deleteEnterAndDetailById(String id) throws Exception;
	/**
	 * 更新
	 * @param storageDeliveryEnter
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	boolean updateStorageDeliveryEnter(StorageDeliveryEnter storageDeliveryEnter);
	/**
	 * 审核
	 * @param saveEnter	需要修改的Enter部分数据 保存这里
	 * @param enter	数据库里查出来的Enter
	 * @param flag    ture,进行在途量入库   false 直接商品入库(保存并审核时)
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年8月26日
	 */
	boolean audit(StorageDeliveryEnter saveEnter, StorageDeliveryEnter enter,boolean flag)throws Exception;
	/**
	 * 根据来源编号获取客户名称
	 * @param sourceid
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月11日
	 */
	String getUpCustomerName(String sourceid);
	Map oppauditDeliveryEnter(String id)throws Exception;
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
	 * 代配送定时任务方法(处理所有逻辑)
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月28日
	 */
	boolean doEntersForJob() throws Exception;
	/**
	 * 打印
	 * @param map
	 * @return
	 * @author huangzhiqian
	 * @date 2015年10月9日
	 */
	List<StorageDeliveryEnter> printDeliveryEnter(Map map)throws Exception;
	/**
	 * 更新代配送入库单的打印次数
	 * @param id
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年10月12日
	 */
	void updateOrderPrinttimes(String id) throws Exception;
}

