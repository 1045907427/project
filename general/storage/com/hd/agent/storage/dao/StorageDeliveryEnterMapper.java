package com.hd.agent.storage.dao;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageDeliveryEnter;
import com.hd.agent.storage.model.StorageDeliveryEnterDetail;
import com.hd.agent.storage.model.StorageDeliveryEnterForJob;
import com.hd.agent.storage.model.StorageDeliveryOut;


public interface StorageDeliveryEnterMapper {
	 /**
     * 根据条件查询记录集
     */
	List<StorageDeliveryEnter> getStorageDeliveryEnterList(PageMap pageMap);

    /**
     * 根据条件查询记录总数
     */
	int getStorageDeliveryEnterCount(PageMap pageMap);
	/**
	 * 根据goodsId查询商品体积,重量相关信息
	 * @param goodsId
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月7日
	 */
	Map getGoodsInfo(String goodsId);
	
	/**
	 * storageDeliveryEnter数据
	 * @param storageDeliveryEnter
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月10日
	 */
	int insertStorageDeliveryEnter(StorageDeliveryEnter storageDeliveryEnter);
	
	
	
	/**
	 * 详情表中插入数据
	 * @param list
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月10日
	 */
	int insertStorageDeliveryEnterDetail(StorageDeliveryEnterDetail storageDeliveryEnterDetail);
	
	
	/**
	 * 根据主键查询记录
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月10日
	 */
	StorageDeliveryEnter getDeliveryEnterByID(String id);
	
	/**
	 * 根据 主表id查询详情表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月10日
	 */
	List<StorageDeliveryEnterDetail> getDeliveryEnterDetailByID(String id);

	/**
     * 根据Model更新主表记录
     */
	int updateDeliveryEnter(StorageDeliveryEnter storageDeliveryEnter);

    /**
     * 根据主表id删除详情表信息
     * @param id
     * @return
     * @author huangzhiqian
     * @date 2015年8月11日
     */
	int deleteDetailsById(String id);
	/**
	 * 根据id删除入库单主表信息
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年8月12日
	 */
	int deleteEnterById(String id);
    
	
    /**
     * 根据来源单据编号获取StorageDeliveryEnter
     */
	StorageDeliveryEnter getStorageDeliveryEnterBySourceId(String sourceid);
	/**
	 * 查询出Enter按照 供应商进行汇总
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月28日
	 */
	List<StorageDeliveryEnterForJob> getEntersForJob();
	
	/**
	 * 对应每个供应商  查询出Detail,按照商品编号进行汇总(指定供应商编号)
	 * @param supplierid
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月29日
	 */
	List<StorageDeliveryEnterForJob> getEntersDetailForJob(@Param("supplierid")String supplierid,@Param("storageid")String storageid);
	/**
	 * 根据id 如 1,2,3,4 查询 StorageDeliveryEnter List 
	 * @param map
	 * @return
	 * @author huangzhiqian
	 * @date 2015年10月9日
	 */
	List<StorageDeliveryEnter> showDeliveryEnterByIds(Map map);
	/**
	 * 根据id 如 1,2,3,4 查询 StorageDeliveryEnter List 
	 * @param map
	 * @return
	 * @author huangzhiqian
	 * @date 2015年10月9日
	 */
	 List<StorageDeliveryOut> showDeliveryOutByIds(Map map);

	 /**
	  * 更新代配送入库单的打印次数
	  * @param id
	  * @author huangzhiqian
	  * @date 2015年10月12日
	  */
	void updateOrderPrinttimes(@Param("id")String id);
	/**
	 * 更新代配送定时任务的执行次数
	 * @param string
	 * @author huangzhiqian
	 * @date 2015年11月5日
	 */
	void updateJobtimes(String string);
	/**
	 * 根据主表id查询字表信息(定时任务)
	 * @param id
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月10日
	 */
	List<StorageDeliveryEnterForJob> getEnterDetailForJobWithOutGroup(String id);

    /**
     * 更新明细的入库时成本价
     * @param id
     * @param goodsid
     * @param addcostprice
     * @return
     */
    public int updateDeliverEnterDetailAddcostprice(@Param("id")String id,@Param("goodsid")String goodsid,@Param("addcostprice")BigDecimal addcostprice);
}