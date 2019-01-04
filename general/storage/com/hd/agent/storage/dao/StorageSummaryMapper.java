/**
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-05-7 chenwei 创建版本
 */
package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.InventoryEnterDetail;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.model.StorageSummaryLog;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 库存现存量汇总dao
 * @author chenwei
 */
public interface StorageSummaryMapper {
	/**
	 * 判断批次现存量表中是否存在该批次信息
	 * @param batchno		批次号
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public int getStorageSummaryBatchCountByBatchno(@Param("storageid")String storageid,@Param("batchno")String batchno);
	/**
	 * 根据商品编码获取仓库现存量总数据列表
	 * @param goodsid		商品编号
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public List getStorageSummaryInfoByGoodsid(@Param("goodsid")String goodsid);
	/**
	 * 根据库存编号 获取库存信息
	 * @param id		商品批次编号
	 * @return
	 * @author chenwei 
	 * @date May 29, 2013
	 */
	public StorageSummary getStorageSummaryInfoByID(@Param("id")String id);
	/**
	 * 根据商品编码和仓库编码获取仓库现存量总量信息
	 * @param goodsid
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date May 29, 2013
	 */
	public StorageSummary getStorageSummaryInfoByGoodsidAndStorageid(@Param("goodsid")String goodsid,@Param("storageid")String storageid);

	/**
	 * 添加批次库存现存量信息
	 * @param storageSummaryBatch
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public int addStorageSummaryBatch(StorageSummaryBatch storageSummaryBatch);
	/**
	 * 更新库存现存量总量信息
	 * @param storageSummary
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public int updateStorageSummary(StorageSummary storageSummary);

    /**
     * 调拨出库，根据仓库编码和商品编码 更新库存
     * @param storageid
     * @param goodsid
     * @param allotnum
     * @param allotnum
     * @return
     */
    public int updateStorageSummaryAlloteSubtract(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("allotwaitnum")BigDecimal allotwaitnum,@Param("allotnum")BigDecimal allotnum,@Param("version") int version);

    /**
     * 调拨出库，根据仓库编码和商品编码 更新批次库存
     * @param id
     * @param oldWaitnum
     * @param allotnum
     * @return
     */
    public int updateStorageSummaryBacthAlloteSubtract(@Param("id")String id,@Param("allotwaitnum")BigDecimal oldWaitnum,@Param("allotnum")BigDecimal allotnum,@Param("batchstate")String batchstate,@Param("version") int version);
    /**
     * 调拨入库，根据仓库编码和商品编码 更新库存
     * @param storageid
     * @param goodsid
     * @param allotnum
     * @param allotnum
     * @return
     */
    public int updateStorageSummaryAlloteAdd(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("existingnum")BigDecimal existingnum,@Param("allotnum")BigDecimal allotnum,@Param("version") int version);
    /**
     * 调拨入库，根据仓库编码和商品编码 更新库存
     * @param storageid
     * @param goodsid
     * @param allotnum
     * @param allotnum
     * @return
     */
    public int updateStorageSummaryAlloteAddEnter(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("existingnum")BigDecimal existingnum,@Param("allotnum")BigDecimal allotnum,@Param("version") int version);

    /**
     * 调拨入库，根据仓库编码和商品编码 更新批次库存
     * @param id
     * @param existingnum
     * @param allotnum
     * @return
     */
    public int updateStorageSummaryBacthAlloteAdd(@Param("id")String id,@Param("existingnum")BigDecimal existingnum,@Param("allotnum")BigDecimal allotnum,@Param("version") int version);
	/**
	 * 发货待发，根据商品编码和仓库编码更新库存现存量等信息
	 * @param storageid         仓库编号
     * @param goodsid           商品编号
     * @param oldWaitnum        修改前待发量
     * @param waitnum           待发量
	 * @return
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public int updateStorageSummaryWaitAddByGoodsidAndStorageid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("oldWaitnum")BigDecimal oldWaitnum,@Param("waitnum")BigDecimal waitnum,@Param("version") int version);
	/**
	 * 发货待发，根据库存批量编号更新批量库存现存量等信息
	 * @param id
     * @param oldWaitnum
	 * @param waitnum
	 * @return
	 * @author chenwei 
	 * @date Mar 4, 2014
	 */
	public int updateStorageSummaryBacthWaitAddByID(@Param("id")String id,@Param("oldWaitnum")BigDecimal oldWaitnum,@Param("waitnum")BigDecimal waitnum,@Param("version") int version);
	/**
	 * 发货待发回滚 根据商品编码和仓库编码更新库存现存量等信息
	 * @param storageid         仓库编号
     * @param goodsid           商品编号
     * @param oldWaitnum        修改前待发量
     * @param waitnum           待发量
	 * @return
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public int updateStorageSummaryWaitBackByGoodsidAndStorageid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("oldWaitnum")BigDecimal oldWaitnum,@Param("waitnum")BigDecimal waitnum,@Param("version") int version);
	/**
	 * 发货待发回滚，根据库存批量编号更新批量库存现存量等信息
	 * @param id
     * @param oldWaitnum
	 * @param waitnum
	 * @return
	 * @author chenwei 
	 * @date Mar 4, 2014
	 */
	public int updateStorageSummaryBacthWaitBackByID(@Param("id")String id,@Param("oldWaitnum")BigDecimal oldWaitnum,@Param("waitnum")BigDecimal waitnum,@Param("version") int version);
	/**
	 * 发货出库 根据商品编码和仓库编码更新库存现存量等信息
	 * @param storageid
     * @param goodsid
     * @param oldExistingnum
     * @param oldWaitnum
     * @param waitnum
	 * @return
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public int updateStorageSummarySendByGoodsidAndStorageid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("oldWaitnum")BigDecimal oldWaitnum,@Param("waitnum")BigDecimal waitnum,@Param("version") int version);
	/**
	 * 发货出库 根据库存批量编号更新批量库存现存量等信息
	 * @param id
     * @param oldExistingnum
     * @param oldWaitnum
	 * @param waitnum
     * @param batchstate
	 * @return
	 * @author chenwei 
	 * @date Mar 4, 2014
	 */
	public int updateStorageSummaryBacthSendByID(@Param("id")String id,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("oldWaitnum")BigDecimal oldWaitnum,@Param("waitnum")BigDecimal waitnum,@Param("batchstate")String batchstate,@Param("version") int version);
	/**
	 * 发货出库回滚 根据商品编码和仓库编码更新库存现存量等信息
	 * @param storageid
     * @param goodsid
     * @param oldExistingnum
     * @param oldWaitnum
     * @param waitnum
	 * @return
	 * @author chenwei 
	 * @date Dec 5, 2013
	 */
	public int updateStorageSummarySendBackByGoodsidAndStorageid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("oldWaitnum")BigDecimal oldWaitnum,@Param("waitnum")BigDecimal waitnum,@Param("version") int version);
	/**
	 * 发货出库回滚 根据库存批量编号更新批量库存现存量等信息
	 * @param id
     * @param oldExistingnum
     * @param oldWaitnum
     * @param waitnum
	 * @return
	 * @author chenwei 
	 * @date Mar 4, 2014
	 */
	public int updateStorageSummaryBacthBackByID(@Param("id")String id,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("oldWaitnum")BigDecimal oldWaitnum,@Param("waitnum")BigDecimal waitnum,@Param("version") int version);

    /**
     * 商品入库 商品编码和仓库编码更新库存现存量等信息
     * @param storageid
     * @param goodsid
     * @param oldExistingnum
     * @param enternum
     * @return
     */
    public int updateStorageSummaryEnterByGoodsidAndStorageid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("enternum")BigDecimal enternum,@Param("version") int version);

    /**
     * 商品入库 根据库存批量编号更新批量库存现存量等信息
     * @param id
     * @param oldExistingnum
     * @param enternum
     * @return
     * @author chenwei
     * @date Mar 4, 2014
     */
    public int updateStorageSummaryBacthEnterByID(@Param("id")String id,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("enternum")BigDecimal enternum,@Param("enterdate")String enterdate,@Param("version") int version);
    /**
     * 商品入库回滚 商品编码和仓库编码更新库存现存量等信息
     * @param storageid
     * @param goodsid
     * @param oldExistingnum
     * @param enternum
     * @return
     */
    public int updateStorageSummaryEnterRollbackByGoodsidAndStorageid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("enternum")BigDecimal enternum,@Param("version") int version);

    /**
     * 商品入库回滚 根据库存批量编号更新批量库存现存量等信息
     * @param id
     * @param oldExistingnum
     * @param enternum
     * @return
     * @author chenwei
     * @date Mar 4, 2014
     */
    public int updateStorageSummaryBacthEnterRollbackByID(@Param("id")String id,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("enternum")BigDecimal enternum,@Param("batchstate")String batchstate,@Param("version") int version);

    /**
     * 在途量商品入库 商品编码和仓库编码更新库存现存量等信息
     * @param storageid
     * @param goodsid
     * @param oldExistingnum
     * @param enternum
     * @return
     */
    public int updateStorageSummaryEnterTransitnumByGoodsidAndStorageid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("enternum")BigDecimal enternum,@Param("version") int version);

    /**
     * 在途量商品入库 根据库存批量编号更新批量库存现存量等信息
     * @param id
     * @param oldExistingnum
     * @param enternum
     * @return
     * @author chenwei
     * @date Mar 4, 2014
     */
    public int updateStorageSummaryBacthEnterTransitnumByID(@Param("id")String id,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("enternum")BigDecimal enternum,@Param("enterdate")String enterdate,@Param("version") int version);

    /**
     * 在途量商品入库回滚 商品编码和仓库编码更新库存现存量等信息
     * @param storageid
     * @param goodsid
     * @param oldExistingnum
     * @param enternum
     * @return
     */
    public int updateStorageSummaryEnterTransitnumRollbackByGoodsidAndStorageid(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("enternum")BigDecimal enternum,@Param("version") int version);

    /**
     * 在途量商品入库回滚 根据库存批量编号更新批量库存现存量等信息
     * @param id
     * @param oldExistingnum
     * @param enternum
     * @param batchstate
     * @return
     * @author chenwei
     * @date Mar 4, 2014
     */
    public int updateStorageSummaryBacthEnterTransitnumRollbackByID(@Param("id")String id,@Param("oldExistingnum")BigDecimal oldExistingnum,@Param("enternum")BigDecimal enternum,@Param("batchstate")String batchstate,@Param("version") int version);
    /**
	 * 添加库存现存量总量信息
	 * @param storageSummary
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public int addStorageSummary(StorageSummary storageSummary);
	/**
	 * 根据商品编码判断库存现存量总表中是否存在该商品的库存信息
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public int getStorageSummaryCountByGoodsid(@Param("goodsid")String goodsid);
	/**
	 * 根据批次号获取库存批次现存量信息
	 * @param batchno			批次号
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public StorageSummaryBatch getStorageSummaryBatchInfoByBatchno(@Param("storageid")String storageid,@Param("batchno")String batchno,@Param("goodsid")String goodsid);

	/**
	 * 根据生产日期获取库存批次现存量信息
	 * @param storageid		仓库编号
	 * @param produceddate	生产日期
	 * @param goodsid			商品编号
	 * @return					商品库存批次信息
	 */
	public StorageSummaryBatch getStorageSummaryBatchInfoByProduceddate(@Param("storageid")String storageid,@Param("produceddate")String produceddate,@Param("goodsid")String goodsid);
    /**
     * 根据生产日期获取库存批次现存量信息
     * @param storageid		仓库编号
     * @param produceddate	生产日期
     * @param goodsid			商品编号
     * @return					商品库存批次信息
     */
    public StorageSummaryBatch getStorageSummaryBatchInfoAllByProduceddate(@Param("storageid")String storageid,@Param("produceddate")String produceddate,@Param("goodsid")String goodsid);

	/**
	 * 根据仓库编号和商品编号 获取该仓库中最早的批次库存信息
	 * @param storageid		仓库编号
	 * @param goodsid			商品编号
	 * @return
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public StorageSummaryBatch getStorageSummaryBatchByStorageidNew(@Param("storageid")String storageid,@Param("goodsid")String goodsid);

	/**
	 * 根据仓库编号和商品编号 获取该仓库中最近的批次库存信息
	 * @param storageid		仓库编号
	 * @param goodsid			商品编号
	 * @return
	 * @author chenwei
	 * @date 2015年10月21日
	 */
	public StorageSummaryBatch getStorageSummaryBatchByStorageidLast(@Param("storageid")String storageid,@Param("goodsid")String goodsid);
	/**
	 * 根据商品编码获取仓库现存量信息
	 * @param pageMap		查询条件
	 * @return				仓库商品库存信息
	 * @author chenwei 
	 * @date May 29, 2013
	 */
	public List showStorageSummaryList(PageMap pageMap);
	/**
	 * 获取库存分仓库现存量列表
	 * @param pageMap		查询条件
	 * @return
	 * @author chenwei 
	 * @date May 29, 2013
	 */
	public List getStorageSummaryByStorageList(PageMap pageMap);
	/**
	 * 获取库存分仓库现存量总量数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 16, 2013
	 */
	public int getStorageSummaryByStorageCount(PageMap pageMap);
	/**
	 * 获取库存现存量总量列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 16, 2013
	 */
	public List getStorageSummarySumList(PageMap pageMap);
	/**
	 * 获取库存现存量总量数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 16, 2013
	 */
	public int getStorageSummarySumCount(PageMap pageMap);

    /**
     * 获取库存现存量 合计
     * @param pageMap
     * @return
     * @author chenwei
     * @date May 16, 2013
     */
    public List getStorageSummarySum(PageMap pageMap);
	/**
	 * 根据商品编号获取库存中发货仓 商品的总量 
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public StorageSummary getStorageSummarySumByGoodsid(String goodsid);

    /**
     * 根据商品编号获取库存中发货仓 商品的总量
     * @param goodsid
     * @return
     * @author chenwei
     * @date Jun 24, 2013
     */
    public StorageSummary getStorageSummarySumByGoodsidWithDatarule(@Param("goodsid") String goodsid,@Param("datasql")String datasql);

	/**
	 * 根据商品编号 获取库存中 参加总量控制的商品合计数量
	 * @param goodsid		商品编号
	 * @return
	 * @author chenwei 
	 * @date 2015年1月26日
	 */
	public StorageSummary getStorageSummarySumByGoodsidInTotal(String goodsid);
	/**
	 * 获取允许超可用量发货的仓库合计在途量
	 * @param goodsid		商品编号
	 * @return
	 * @author chenwei 
	 * @date 2014年11月11日
	 */
	public StorageSummary getStorageSummaryTransitnumSumByOutusable(String goodsid);

    /**
     * 获取允许超可用量发货的仓库合计在途量(数据权限)
     * @param goodsid		商品编号
     * @return
     * @author chenwei
     * @date 2014年11月11日
     */
    public StorageSummary getStorageSummaryTransitnumSumByOutusableWithDatarule(@Param("goodsid") String goodsid,@Param("datasql") String datasql);

	/**
	 * 获取相同条形码不同商品编码的 商品仓库库存信息列表
	 * @param barcode		条形码
	 * @param goodsid		商品编号
	 * @return
	 * @author chenwei 
	 * @date Sep 5, 2013
	 */
	public List getStorageSummarySumByBarcode(@Param("barcode")String barcode,@Param("goodsid")String goodsid);
	/**
	 * 获取相同条形码不同商品编码的 商品仓库库存信息列表
	 * @param barcode		条形码
	 * @param goodsid		商品编号
	 * @return
	 * @author chenwei 
	 * @date Sep 5, 2013
	 */
	public List getStorageSummarySumByBarcodeInStorageid(@Param("barcode")String barcode,@Param("goodsid")String goodsid,@Param("storageid")String storageid);
	/**
	 * 通过仓库编号和商品编码获取批次现存量列表
	 * @param pageMap		查询条件
	 * @return
	 * @author chenwei 
	 * @date May 16, 2013
	 */
	public List showStorageSummaryBatchListByStorageidAndGoodsid(PageMap pageMap);
	/**
	 * 根据库存现存量总量编码获取批次量信息
	 * @param summaryid		仓库商品编号
	 * @return
	 * @author chenwei 
	 * @date May 21, 2013
	 */
	public List getStorageSummaryBatchBySummaryid(@Param("summaryid")String summaryid);

	/**
	 * 根据仓库编码与库位编码获取 库存批次量（当商品进行库位管理时）
	 * @param goodsid					商品编号
	 * @param storageid				仓库编号
	 * @param storagelocationid		库位编号
	 * @return
	 */
	public StorageSummaryBatch getStorageSummaryBatchByStorageidAndStoragelocationid(@Param("goodsid")String goodsid,@Param("storageid")String storageid,@Param("storagelocationid")String storagelocationid);
	/**
	 * 根据仓库编码与批次编号获取 库存批次信息
	 * @param goodsid					商品编号
	 * @param storageid				仓库编号
	 * @param batchno					批次号
	 * @return
	 * @author chenwei 
	 * @date 2015年10月22日
	 */
	public StorageSummaryBatch getStorageSummaryBatchByStorageidAndBatchno(@Param("goodsid")String goodsid,@Param("storageid")String storageid,@Param("batchno")String batchno);
	/**
	 * 根据编号获取批次现存量详细信息
	 * @param id						商品库存批次编号
	 * @return
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public StorageSummaryBatch getStorageSummaryBatchInfoById(@Param("id")String id);
	/**
	 * 根据仓库编码和商品编码获取批次现存量（商品不进行批次管理 库位管理时 只进行总量管理）
	 * @param storageid				仓库编号
	 * @param goodsid					商品编号
	 * @return
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public StorageSummaryBatch getStorageSummaryBatchByStorageidAndGoodsid(@Param("storageid")String storageid,@Param("goodsid")String goodsid);
	
	/**
	 * 根据仓库编码和商品编码获取没有批次号的库存
	 * @param storageid				仓库编号
	 * @param goodsid					商品编号
	 * @return
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public StorageSummaryBatch getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(@Param("storageid")String storageid,@Param("goodsid")String goodsid);
	
	/**
	 * 更新批次现存量信息
	 * @param storageSummaryBatch	商品库存批次信息
	 * @return
	 * @author chenwei 
	 * @date May 21, 2013
	 */
	public int updateStorageSummaryBacth(StorageSummaryBatch storageSummaryBatch);
	/**
	 * 根据编号与商品编码删除库存现存量信息
	 * @param id						仓库商品编号
	 * @param goodsid					商品编号
	 * @return
	 * @author chenwei 
	 * @date May 21, 2013
	 */
	public int deleteStorageSummary(@Param("id")String id,@Param("goodsid")String goodsid);
	/**
	 * 根据商品编码和仓库编码，还有库位编码（库位编码可以为null）,删除批次现存量
	 * @param id					商品库存批次编号
	 * @return
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public int deleteStorageSummaryBatchByID(@Param(("id"))String id);
    /**
	 * 根据仓库编码获取该仓库下的商品批次数量信息列表
	 * @param storageid				仓库编号
	 * @return
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public List getStorageSummaryBatchListByStorageid(@Param("storageid")String storageid);
	
	/**
	 * 根据仓库编码获取该仓库下的商品合计数量信息列表
	 * @param storageid				仓库编号
	 * @return
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public List getStorageSummaryBatchSumListByStorageid(@Param("storageid")String storageid);

	/**
	 * 根据仓库编码和品牌获取仓库下的商品批次数量信息列表
	 * @param map
	 * @param storageid				仓库编号
	 * @param brands				品牌编号数组
	 * @param goodssorts			商品分类编号数组
	 * @return
	 */
	public List getStorageSummaryBatchListByMap(Map map);

	/**
	 * 根据仓库编码和品牌获取仓库下的商品批次数量合计信息列表
	 * @param map
	 * @param storageid				仓库编号
	 * @param brands				品牌编号数组
	 * @param goodssorts			商品分类编号数组
	 * @return
	 */
	public List getStorageSummaryBatchSumListByMap(Map map);
	/**
	 * 根据仓库编码和品牌获取仓库下的商品批次数量信息列表
	 * @param storageid				仓库编号
	 * @param brands					品牌编号数组
	 * @return
	 * @author chenwei 
	 * @date Aug 8, 2013
	 */
	public List getStorageSummaryBatchListByStorageidAndBrand(@Param("storageid")String storageid,@Param("brands")String[] brands);
	/**
	 * 根据仓库编码和品牌获取仓库下的商品批次数量合计信息列表
	 * @param storageid				仓库编号
	 * @param brands					品牌编号数组
	 * @return
	 * @author chenwei 
	 * @date 2015年10月28日
	 */
	public List getStorageSummaryBatchSumListByStorageidAndBrand(@Param("storageid")String storageid,@Param("brands")String[] brands);
	
	/**
	 * 根据仓库编码和商品类别获取仓库下的商品批次数量信息列表
	 * @param storageid				仓库编号
	 * @param goodssorts				商品分类编号数组
	 * @return
	 * @author chenwei 
	 * @date Aug 8, 2013
	 */
	public List getStorageSummaryBatchListByStorageidAndGoodssorts(@Param("storageid")String storageid,@Param("goodssorts")String[] goodssorts);
	
	/**
	 * 根据仓库编码和商品类别获取仓库下的商品合计数量信息列表
	 * @param storageid				仓库编号
	 * @param goodssorts				商品分类编号数组
	 * @return
	 * @author chenwei 
	 * @date Aug 8, 2013
	 */
	public List getStorageSummaryBatchSumListByStorageidAndGoodssorts(@Param("storageid")String storageid,@Param("goodssorts")String[] goodssorts);
	/**
	 * 根据商品编码获取该编码各批次现存量信息
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date May 28, 2013
	 */
	public List getSotrageSummaryBatchListByGoodsid(@Param("goodsid")String goodsid);
	/**
	 * 添加库存现存量追踪日志
	 * @param storageSummaryLog		库位变更日志
	 * @return
	 * @author chenwei 
	 * @date May 29, 2013
	 */
	public int addStorageSummaryLog(StorageSummaryLog storageSummaryLog);
	
	/**
	 * 根据日期获取当日库存
	 * @param pageMap					查询条件
	 * @return
	 * @author wanghongteng
	 * @date 1 8, 2015
	 */
	public StorageSummaryLog getInitdate(@Param("queryinitdate1")String queryinitdate1,@Param("queryinitdate2")String queryinitdate2,@Param("storageid")String storageid,@Param("goodsid")String goodsid);
	/**
	 * 获取库存现存量汇总数据
	 * @param pageMap					查询条件
	 * @return
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public List showStorageSummaryLogList(PageMap pageMap);
	/**
	 * 获取库存现存量汇总数量
	 * @param pageMap					查询条件
	 * @return
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public int showStorageSummaryLogCount(PageMap pageMap);
	/**
	 * 获取库存现存量合计
	 * @param pageMap					查询条件
	 * @return
	 * @author wanghongteng
	 * @date 12 30, 2015
	 */
	public Map showStorageSummaryLogSum(PageMap pageMap);
	/**
	 * 根据仓库编码和商品编码获取仓库批次量数据列表
	 * @param storageid				仓库编号
	 * @param goodsid					商品编号
	 * @return
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public List getStorageSummaryBatchListByStorageidAndGoodsid(@Param("storageid")String storageid,@Param("goodsid")String goodsid);
	
	/**
	 * 根据仓库编码和商品编码获取仓库批次量数据列表(只包含批次号与生产日期的记录)
	 * @param storageid
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public List getStorageSummaryBatchListWithoutNoBatchByStorageidAndGoodsid(@Param("storageid")String storageid,@Param("goodsid")String goodsid);
	
	/**
	 * 根据仓库编码和商品编码 获取该仓库的各库位现存量信息列表
	 * 根据数量多少排序
	 * @param storageid
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Jun 4, 2013
	 */
	public List getStorageSummaryBatchListOrderNums(@Param("storageid")String storageid,@Param("goodsid")String goodsid);
	/**
	 * 根据仓库编码和商品编码 获取该仓库的各库位现存量信息列表
	 * 根据入库日期 现存量排序
	 * @param storageid
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date 2014年6月17日
	 */
	public List getStorageSummaryBatchListOrderEnterdate(@Param("storageid")String storageid,@Param("goodsid")String goodsid);
	/**
	 * 获取商品各仓库的现存量信息.
	 * 发货仓库的列表
	 * @param goodsid
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	public List getStorageSummaryListByGoodsWithoutStorageid(@Param("goodsid")String goodsid,@Param("storageid")String storageid);
	/**
	 * 根据id值获取仓库下的商品列表
	 * id查询商品编码 条形码 商品助记码
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 7, 2013
	 */
	public List getStorageGoodsSelectListData(PageMap pageMap);
	/**
	 * 根据id值获取仓库下的商品数量
	 * id查询商品编码 条形码 商品助记码
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 7, 2013
	 */
	public int getStorageGoodsSelectListDataCount(PageMap pageMap);
	/**
	 * 获取仓库下的品牌列表
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date Aug 8, 2013
	 */
	public List getStorageBrandList(@Param("storageid")String storageid);
	/**
	 * 根据仓库编码 更新仓库商品库存 是否参与总量控制 是否发货仓的状态
	 * @param storageid				仓库编码
	 * @param istotalcontrol		是否参与总量控制
	 * @param issendstorage			是否发货仓的状态
	 * @return
	 * @author chenwei 
	 * @date Sep 5, 2013
	 */
	public int editStorageSummaryInfoByStorageid(@Param("storageid")String storageid,@Param("istotalcontrol")String istotalcontrol,@Param("issendstorage")String issendstorage);
	/**
	 * 根据商品编码和仓库编码 获取商品在该仓库中存放的库位信息
	 * @param goodsid		商品编码	
	 * @param storageid		仓库编码
	 * @return
	 * @author chenwei 
	 * @date 2014年6月17日
	 */
	public List getGoodsStorageLocationInfoList(@Param("goodsid")String goodsid,@Param("storageid")String storageid);
	/**
	 * 更新仓库商品的成本价
	 * @param storageid		仓库编号
	 * @param goodsid		商品编号
	 * @param costprice		最新成本价
     * @param storageamount     库存金额
	 * @return
	 * @author chenwei 
	 * @date 2015年11月3日
	 */
	public int updateStorageGoodsCostprice(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("existingnum")BigDecimal existingnum,@Param("costprice")BigDecimal costprice,@Param("storageamount")BigDecimal storageamount);

    /**
     * 获取该仓库下 拥有可用量的商品批次列表
     * @param storageid
     * @return
     */
    public List getStorageBatchListHasUsenumByStorageid(@Param("storageid")String storageid);

    /**
     * 获取仓库多少天内 库存变动过的记录
     * @param storageid                     仓库编号
     * @param bdate                            变动过日期
     * @param isCheckListUseBatch           是否按批次盘点1是0否
     * @param split                           拆分类型
     * @param sid                              拆分类型编号
     * @return
     */
    public List<StorageSummaryBatch> getStorageSummaryBatchChangeListInDays(@Param("storageid") String storageid, @Param("bdate") String bdate, @Param("isCheckListUseBatch") String isCheckListUseBatch,@Param("split") String split,@Param("sid") String sid);
    /**
     * 获取仓库多少天内 库存变动过的品牌列表
     * @param storageid
     * @param bdate
     * @return
     */
    public List<String> getStorageSummaryBatchChangeBrandListInDays(@Param("storageid") String storageid, @Param("bdate") String bdate);
    /**
     * 获取仓库多少天内 库存变动过的供应商列表
     * @param storageid
     * @param bdate
     * @return
     */
    public List<String> getStorageSummaryBatchChangeSupplierListInDays(@Param("storageid") String storageid, @Param("bdate") String bdate);
    /**
     * 获取仓库多少天内 库存变动过的客户分类列表
     * @param storageid
     * @param bdate
     * @return
     */
    public List<String> getStorageSummaryBatchChangeGoodsSortListInDays(@Param("storageid") String storageid, @Param("bdate") String bdate);

    /**
     * 获取库存明细
     * @return
     * @author chenwei
     * @date May 29, 2013
     */
    public List<StorageSummary> getAllStorageSummaryList();

    /**
     * 根据日期获取备份的库存记录
     * @param date
     * @return
     */
    public List<StorageSummary> getAllStorageSummaryListByDate(@Param("date") String date);
    /**
     * 根据仓库编号和商品编号 获取入库明细
     * @param storageid
     * @param goodsid
     * @return
     */
    public List getStorageEnterDetailList(@Param("storageid") String storageid,@Param("goodsid") String goodsid,@Param("date") String date);

    /**
     * 添加库龄报表 入库明细
     * @param detail
     * @return
     */
    public int addInventoryAgeDetail(InventoryEnterDetail detail);

    /**
     * 添加库存报表
     * @param businessdate
     * @param storageid
     * @param goodsid
     * @param age
     * @param unitnum
     * @param begindate
     * @return
     */
    public int addInventoryAge(@Param("businessdate") String businessdate,@Param("storageid") String storageid,@Param("goodsid") String goodsid,
                               @Param("age") BigDecimal age,@Param("unitnum") BigDecimal unitnum,@Param("begindate") String begindate);

    /**
     * 删除库龄报表数据
     * @param date
     * @return
     */
    public int deleteInventoryAge(@Param("date") String date);

    /**
     * 获取库龄报表数据
     * @param pageMap
     * @return
     */
    public List getInventoryAgeDataList(PageMap pageMap);

    /**
     * 获取库龄报表数量
     * @param pageMap
     * @return
     */
    public int getInventoryAgeDataListCount(PageMap pageMap);

    /**
     * 获取库龄报表合计数据
     * @param pageMap
     * @return
     */
    public List getInventoryAgeDataSumList(PageMap pageMap);
    /**
     * 获取库龄报表统计数据中的游标
     * @param userid
     * @return
     * @author wanghongteng
     * @date Nov 25, 2016
     */
    public List getInventoryAgeListDataRS(@Param("userid") String userid);

    /**
     * 获取库龄入库明细列表
     * @param pageMap
     * @return
     */
    public List showInventoryAgeDetailLogDataList(PageMap pageMap);

    /**
     * 获取库龄入库明细数量
     * @param pageMap
     * @return
     */
    public int showInventoryAgeDetailLogDataListCount(PageMap pageMap);

    /**
     * 获取库龄入库明细列表
     * @param pageMap
     * @return
     */
    public List showInventoryAgeDetailLogDataSum(PageMap pageMap);

    /**
     * 清理多少天生成的库龄报表
     * @param days
     * @return
     */
    public int deleteInventoryAgeIndays(@Param("days") int days);
	/**
	 * 更新调拨入库仓库待入量
	 * @param storageid
	 * @param goodsid
	 * @param allotnum
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Nov 09, 2017
	 */
	public int updateStorageSummaryAllotEnter(@Param("storageid")String storageid,@Param("goodsid")String goodsid,@Param("allotnum")BigDecimal allotnum,@Param("version") int version);
}
