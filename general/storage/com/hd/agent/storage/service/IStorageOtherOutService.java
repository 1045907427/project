/**
 * @(#)IStorageOtherOutService.java
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
import com.hd.agent.storage.model.StorageOtherOut;
import com.hd.agent.storage.model.StorageOtherOutDetail;

/**
 * 
 * 其他出库单service
 * @author chenwei
 */
public interface IStorageOtherOutService {
	/**
	 * 其他出库单添加
	 * @param storageOtherOut
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map addStorageOtherOut(StorageOtherOut storageOtherOut,List<StorageOtherOutDetail> list) throws Exception;
    /**
     * 导入的其他出库单添加
     * @param storageOtherOut
     * @param list
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Apr 7, 201
     */
    public Map addImportStorageOtherOut(StorageOtherOut storageOtherOut,List<StorageOtherOutDetail> list) throws Exception;
	/**
	 * 根据编号获取其他出库单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map getStorageOtherOutInfo(String id) throws Exception;
	/**
	 * 获取其他出库单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public PageData showStorageOtherOutList(PageMap pageMap) throws Exception;
	/**
	 * 其他出库单修改
	 * @param storageOtherOut
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map editStorageOtherOut(StorageOtherOut storageOtherOut,List<StorageOtherOutDetail> list) throws Exception; 
	/**
	 * 其他出库单删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public boolean deleteStorageOtherOut(String id) throws Exception;
	/**
	 * 其他出库单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public boolean auditStorageOtherOut(String id) throws Exception;
	/**
	 * 其他出库单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 14, 2014
	 */
	public boolean oppauditStorageOtherOut(String id) throws Exception;
	/**
	 * 其他出库单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public Map submitStorageOtherOutProcess(String id) throws Exception;
	/**
	 * 获取其他出库单明细详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 7, 2013
	 */
	public StorageOtherOutDetail getStorageOtherOutDetailInfo(String id) throws Exception;
	
	/**
	 * 其他出库单列表<br/>
	 * map中参数:<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showStorageOtherOutListBy(Map map) throws Exception;
	/**
	 * 更新打印次数
	 * @param storageOtherOut
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public boolean updateOrderPrinttimes(StorageOtherOut storageOtherOut) throws Exception;
	/**
	 * 获取其他出库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年1月14日
	 */
	public StorageOtherOut showPureStorageOtherOut(String id) throws Exception;

	/**
	 * 获取ID
	 * @param sourceid
	 * @return
	 * @throws Exception
	 */
	public String getStorageOtherOutIdBySourceId(String sourceid) throws Exception;

	/**
	 * 获取其它出库单要生成凭证的数据
	 * @param list
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List getStorageOtherOutSumData(List list);

	/**
	 * 获取其它出库单的金额
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Dec 26, 2017
	 */
	public Map getStorageOtherSumById(String id);

}

