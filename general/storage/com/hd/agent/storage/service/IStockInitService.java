/**
 * @(#)StockInitService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 7, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StockInit;

/**
 * 
 * 库存初始化service
 * @author chenwei
 */
public interface IStockInitService {
	
	/**
	 * 添加库存初始化数据列表
	 * @param list
	 * @param status	状态
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 9, 2013
	 */
	public boolean addStockInitList(List<StockInit> list,String status) throws Exception;
	/**
	 * 添加库存初始化数据
	 * @param stockInit
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 14, 2013
	 */
	public boolean addStockInit(StockInit stockInit) throws Exception;
	/**
	 * 添加库存初始化数据（Excel导入）
	 * @param stockInit
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 9, 2013
	 */
	public Map addStockInitByImport(List<StockInit> list) throws Exception;
	/**
	 * 获取库存初始化数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 10, 2013
	 */
	public PageData showStockInitList(PageMap pageMap) throws Exception;
	/**
	 * 根据仓库编号获取该仓库下的库存初始化列表
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 13, 2013
	 */
	public PageData showStockInitListByStorageid(PageMap pageMap) throws Exception;
	/**
	 * 删除库存初始化数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 14, 2013
	 */
	public boolean deleteStockInit(String id) throws Exception;
	/**
	 * 获取仓库初始化数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 14, 2013
	 */
	public StockInit showStockInitInfo(String id) throws Exception;
	/**
	 * 修改仓库初始化数据
	 * @param stockInit
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 14, 2013
	 */
	public boolean editStockInit(StockInit stockInit) throws Exception;
	/**
	 * 审核库存初始化数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 14, 2013
	 */
	public boolean auditStockInit(String id) throws Exception;
	/**
	 * 反审库存初始化数据
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public boolean oppauditStockInit(String id) throws Exception;
	/**
	 * 验证库存初始化批次号是否重复
	 * true 不重复 false重复
	 * @param batchno	批次号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public boolean checkStockInitBatchno(String batchno) throws Exception;
}

