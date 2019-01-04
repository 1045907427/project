/**
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-05-7 chenwei 创建版本
 */
package com.hd.agent.storage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StockInit;

/**
 * 库存初始化dao
 * @author chenwei
 */
public interface StockInitMapper {
	/**
	 * 添加库存初始化数量
	 * @param stockInit
	 * @return
	 * @author chenwei 
	 * @date May 9, 2013
	 */
	public int addStockInit(StockInit stockInit);
	/**
	 * 获取库存初始化列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 10, 2013
	 */
	public List showStockInitList(PageMap pageMap);
	/**
	 * 获取库存初始化数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 10, 2013
	 */
	public int showStockInitCount(PageMap pageMap);
	/**
	 * 获取库存初始化合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2015年1月7日
	 */
	public StockInit showStockInitSum(PageMap pageMap);
	/**
	 * 根据仓库编号获取该仓库下的库存初始化列表
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date May 13, 2013
	 */
	public List showStockInitListByStorageid(PageMap pageMap);
	/**
	 * 获取库存初始化数据
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 14, 2013
	 */
	public StockInit getStockInitInfo(@Param("id")String id);
	/**
	 * 删除库存初始化数据
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 14, 2013
	 */
	public int deleteStockInit(@Param("id")String id);
	/**
	 * 修改库存初始化数据
	 * @param stockInit
	 * @return
	 * @author chenwei 
	 * @date May 14, 2013
	 */
	public int editStockInit(StockInit stockInit);
	/**
	 * 获取批次号数量
	 * @param batchno
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public int getStockInitBatchnoCount(@Param("batchno")String batchno);
	/**
	 * 库存初始化审核
	 * @param id
	 * @param audituserid
	 * @param auditusername
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public int auditStockInit(@Param("id")String id,@Param("audituserid")String audituserid,@Param("auditusername")String auditusername,@Param("summarybatchid")String summarybatchid);
	/**
	 * 库存初始化反审
	 * @param id
	 * @param audituserid
	 * @param auditusername
	 * @return
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public int oppauditStockInit(@Param("id")String id,@Param("audituserid")String audituserid,@Param("auditusername")String auditusername);
	/**
	 * 根据商品编码获取库存初始化合计数据（正式数据，审核通过）
	 * @param goodsid		商品编码
	 * @param storageid		仓库编码
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public StockInit getStockInitSumByGoodsidAndStorageid(@Param("goodsid")String goodsid,@Param("storageid")String storageid);
	/**
	 * 根据商品编码获取库存初始化合计数据
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Aug 27, 2013
	 */
	public StockInit getStockInitSumByGoodsid(@Param("goodsid")String goodsid);
}