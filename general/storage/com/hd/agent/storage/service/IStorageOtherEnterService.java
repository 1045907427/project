/**
 * @(#)IStorageOtherEnterService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 3, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.ExportStorageOtherEnterAndOut;
import com.hd.agent.storage.model.StorageOtherEnter;
import com.hd.agent.storage.model.StorageOtherEnterDetail;

/**
 * 
 * 其他入库单service
 * @author chenwei
 */
public interface IStorageOtherEnterService {
	/**
	 * 其他入库单添加
	 * @param storageOtherEnter
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public boolean addStorageOtherEnter(StorageOtherEnter storageOtherEnter,List<StorageOtherEnterDetail> list) throws Exception;
    /**
     * 其他入库单导入数据添加
     * @param storageOtherEnter
     * @param list
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Apr 6, 2016
     */
    public Map addImportStorageOtherEnter(StorageOtherEnter storageOtherEnter,List<StorageOtherEnterDetail> list) throws Exception;
	/**
	 * 根据编号获取其他入库单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map getStorageOtherEnterInfo(String id) throws Exception;
	/**
	 * 获取其他入库单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public PageData showStorageOtherEnterList(PageMap pageMap) throws Exception;
	/**
	 * 其他入库单修改
	 * @param storageOtherEnter
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public boolean editStorageOtherEnter(StorageOtherEnter storageOtherEnter,List<StorageOtherEnterDetail> list) throws Exception; 
	/**
	 * 其他入库单删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public boolean deleteStorageOtherEnter(String id) throws Exception;
	/**
	 * 其他入库单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public boolean auditStorageOtherEnter(String id) throws Exception;
	/**
	 * 其他入库单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 14, 2014
	 */
	public Map oppauditStorageOtherEnter(String id) throws Exception;
	/**
	 * 其他入库单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map submitStorageOtherEnterProcess(String id) throws Exception;
	
	/**
	 * 其他入库单列表<br/>
	 * map中参数:<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showStorageOtherEnterListBy(Map map) throws Exception;
	/**
	 * 更新打印次数
	 * @param storageOtherEnter
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public boolean updateOrderPrinttimes(StorageOtherEnter storageOtherEnter) throws Exception;
	/**
	 * 获取其他入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年1月14日
	 */
	public StorageOtherEnter showPureStorageOtherEnter(String id) throws Exception;

	/**
	 * 获取ID
	 * @param sourceid
	 * @return
	 * @throws Exception
	 */
	public String getStorageOtherEnterIdBySourceId(String sourceid) throws Exception;



	/**
	 * 获取其它入库单生成凭证数据
	 * @param list
	 * @return java.util.List
	 * @throw
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List getStorageOtherEnterSumData(List list);

	/**
	 * 其它入库单据金额获取
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Dec 26, 2017
	 */
	public Map getStorageOtherEnterSumById(String id);
}
