package com.hd.agent.storage.dao;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageDeliveryOut;
import com.hd.agent.storage.model.StorageDeliveryOutDetail;
import com.hd.agent.storage.model.StorageDeliveryOutForJob;

public interface StorageDeliveryOutMapper {
	/**
	 * 代配送出库表插入数据
	 * @param storageDeliveryOut
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月13日
	 */
	int insertStorageDeliveryOut(StorageDeliveryOut storageDeliveryOut);
	/**
	 * 代配送出库详情表插入数据
	 * @param obj
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月13日
	 */
	int insertStorageDeliveryOutDetail(StorageDeliveryOutDetail obj);
	/**
	 * 根据条件查询数据条数
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */	
	int getStorageDeliveryOutCount(PageMap pageMap);
	/**
	 * 根据条件查询datagird数据
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	List<StorageDeliveryOut> getStorageDeliveryOutList(PageMap pageMap);
	/**
	 * 根据bill Id删除详情表
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	int deleteDetailsByBillId(String billid);
	
	
	/**
	 * 根据id查询主表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	StorageDeliveryOut getStorageDeliveryOutById(String id);
	
	/**
	 * 根据id删除主表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	int deleteStorageDeliveryOutById(String id);
	
	/**
	 * 根据billId查询详情表信息
	 * @param billId
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	List<StorageDeliveryOutDetail> getDetailsByBillId(String billId);
	/**
	 * 更新主表信息
	 * @param storageDeliveryOut
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月14日
	 */
	int updateDeliveryOut(StorageDeliveryOut storageDeliveryOut);
	
	/**
	 * 根据来源单据编号 获取 StorageDeliveryOut
	 */
	StorageDeliveryOut getStorageDeliveryOutBySourceId(String sourceid);
	/**
	 * 根据主键id查询
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月31日
	 */
	StorageDeliveryOutDetail getDetailById(String id);
	/**
	 * 查询出当天的客户出库单信息(定时任务)
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月30日
	 */
	List<StorageDeliveryOutForJob> getOutsForJob();
	/**
	 * 根据供应商,仓库 查询客户出库单定时任务 商品详情
	 * @param supplierid
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月30日
	 */
	List<StorageDeliveryOutForJob> getOutDetailForJob( @Param("supplierid")String supplierid,@Param("storageid")String storageid);
	/**
	 * 根据id 如 1,2,3,4 查询 StorageDeliveryOut List 
	 * @param map
	 * @return
	 * @author huangzhiqian
	 * @date 2015年10月12日
	 */
	List<StorageDeliveryOut> showDeliveryOutByIds(Map map);
	/**
	 * 更新打印次数
	 * @param id
	 * @author huangzhiqian
	 * @date 2015年10月12日
	 */
	void updateOrderPrinttimes(String id);
	/**
	 * 更新定时任务 的执行次数
	 * @param successIds
	 * @author huangzhiqian
	 * @date 2015年11月5日
	 */
	void updateJobExeCuteTimes(@Param("id")String successId);
	
	/**
	 * 根据主表id查询字表信息(定时任务)
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月10日
	 */
	List<StorageDeliveryOutForJob> getOutDetailForJobWithOutGroup(String id);

    /**
     * 更新明细的入库时成本价
     * @param id
     * @param goodsid
     * @param addcostprice
     * @return
     */
    public int updateDeliverOutDetailAddcostprice(@Param("id")String id,@Param("goodsid")String goodsid,@Param("addcostprice")BigDecimal addcostprice);
}