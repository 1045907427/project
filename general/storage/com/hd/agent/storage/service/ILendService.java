/**
 * @(#)ILendService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 3, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * 借货还货单service
 * @author chenwei
 */
public interface ILendService {
	/**
	 * 借货还货单添加
	 * @param lend
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map addLend(Lend lend, List<LendDetail> list) throws Exception;
    /**
     * 导入的借货还货单添加
     * @param lend
     * @param list
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Apr 7, 201
     */
    public Map addImportLend(Lend lend, List<LendDetail> list) throws Exception;
	/**
	 * 根据编号获取借货还货单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map getLendInfo(String id) throws Exception;
	/**
	 * 获取借货还货单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public PageData showLendList(PageMap pageMap) throws Exception;
	/**
	 * 借货还货单修改
	 * @param lend
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map editLend(Lend lend, List<LendDetail> list) throws Exception;
	/**
	 * 借货还货单删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public boolean deleteLend(String id) throws Exception;

	/**
	 * 借货还货单审核
	 * @param id
	 * @param storageOtherEnter
	 * @param storageEnterList
	 * @return
	 * @throws Exception
	 */
	public Map auditLend(String id, StorageOtherEnter storageOtherEnter,List storageEnterList) throws Exception;

	/**
	 * 借货还货单审核
	 * @param id
	 * @param storageOtherOut
	 * @param storageOutList
	 * @return
	 * @throws Exception
	 */
	public Map auditLend(String id, StorageOtherOut storageOtherOut, List storageOutList) throws Exception;

	/**
	 * 借货还货单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 14, 2014
	 */
	public Map oppauditLend(String id) throws Exception;
	/**
	 * 借货还货单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map submitLendProcess(String id) throws Exception;
	/**
	 * 获取借货还货单明细详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 7, 2013
	 */
	public LendDetail getLendDetailInfo(String id) throws Exception;
	
	/**
	 * 借货还货单列表<br/>
	 * map中参数:<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showLendListBy(Map map) throws Exception;
	/**
	 * 更新打印次数
	 * @param lend
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public boolean updateOrderPrinttimes(Lend lend) throws Exception;
	/**
	 * 获取借货还货单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年1月14日
	 */
	public Lend showPureLend(String id) throws Exception;

	public StorageSummaryBatch addOrGetSummaryBatchByStorageidAndProduceddate(String storageid, String goodsid, String produceddate) throws Exception;

	public StorageSummaryBatch getSummaryBatchByStorageidAndBatchno(String storageid, String batchno, String goodsid) throws Exception;

	public StorageSummaryBatch getSummaryBatchByStorageidAndGoodsid(String storageid, String goodsid) throws Exception;

	/**
	 * 单据导入
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> lendImport(List<Map<String, Object>> list,String type) throws Exception;
}

