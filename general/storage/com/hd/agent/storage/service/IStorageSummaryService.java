/**
 * @(#)IStorageSummaryService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 15, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import java.util.List;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;

/**
 * 
 * 库存现存量service
 * @author chenwei
 */
public interface IStorageSummaryService {
	/**
	 * 商品库存现存量总量查询
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 16, 2013
	 */
	public PageData showStorageSummarySumList(PageMap pageMap) throws Exception;
	/**
	 * 商品库存分仓库现存量总量查询
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 28, 2013
	 */
	public PageData showStorageSummaryByStorageList(PageMap pageMap) throws Exception;
	/**
	 * 商品仓库现存量查询
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 29, 2013
	 */
	public List showStorageSummaryList(String goodsid,String istotalcontrol) throws Exception;
	/**
	 * 通过仓库编号获取批次现存量列表数据
	 * @param storageid
	 * @param goodsid
	 * @param showZero 是否显示0库存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 16, 2013
	 */
	public List showStorageSummaryBatchList(String storageid,String goodsid,String showZero) throws Exception;
	/**
	 * 获取库存追踪日志表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public PageData showStorageSummaryLogList(PageMap pageMap) throws Exception;
	/**
	 * 通过仓库获取库存追踪日志表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 1 8,2016
	 */
	public PageData showStorageSummaryLogListByStorage(PageMap pageMap) throws Exception;
	/**
	 * 根据仓库编码和商品编码 获取该仓库下的商品批次列表
	 * @param storageid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public List getStorageBatchListByStorageidAndGoodsid(String storageid,String goodsid) throws Exception;
	/**
	 * 根据批次现存量编号 获取批次现存量详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public StorageSummaryBatch getStorageSummaryBatchInfo(String id) throws Exception;
	/**
	 * 根据商品编号获取库存中商品的总量
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public StorageSummary getStorageSummarySumByGoodsid(String goodsid) throws Exception;

    /**
     * 根据商品编号获取库存中商品的总量
     * @param goodsid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 24, 2013
     */
    public StorageSummary getStorageSummarySumByGoodsidWithDatarule(String goodsid) throws Exception;

	/**
	 * 根据仓库编号和商品编号获取该仓库下的商品数量
	 * @param storageid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public StorageSummary getStorageSummaryByStorageAndGoods(String storageid,String goodsid) throws Exception;

	/**
	 * 根据id值获取仓库下的商品列表
	 * id查询商品编码 条形码 商品助记码
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 7, 2013
	 */
	public PageData getStorageGoodsSelectListData(PageMap pageMap) throws Exception;
	/**
	 * 获取仓库下的商品品牌列表
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 8, 2013
	 */
	public List  getStorageBrandList(String storageid) throws Exception;

    /**
     * 根据仓库编码 获取该仓库下的有可用量的商品批次列表
     * @param storageid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 20, 2013
     */
    public List getStorageBatchListHasUsenumByStorageid(String storageid) throws Exception;

    /**
     * 清理库龄报表(超过60天的库龄报表)
     * @return
     * @throws Exception
     */
    public boolean deleteInventoryAgeIndays() throws Exception;
    /**
     * 生成库龄报表
     * @return
     * @throws Exception
     */
    public boolean addInventoryAge(String date) throws Exception;

    /**
     * 获取库龄报表数据
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData showInventoryAgeDataList(PageMap pageMap) throws Exception;

    /**
     * 获取库龄入库明细记录
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData showInventoryAgeDetailLogData(PageMap pageMap) throws Exception;
}

