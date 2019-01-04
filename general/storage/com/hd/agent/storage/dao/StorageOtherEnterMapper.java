package com.hd.agent.storage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageOtherEnter;
import com.hd.agent.storage.model.StorageOtherEnterDetail;

/**
 * 其他入库单dao
 * @author chenwei
 */
public interface StorageOtherEnterMapper {
	/**
	 * 其他入库单明细添加
	 * @param storageOtherEnterDetail
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int addStorageOtherEnterDetail(StorageOtherEnterDetail storageOtherEnterDetail);
	/**
	 * 其他入库单添加
	 * @param storageOtherEnter
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int addStorageOtherEnter(StorageOtherEnter storageOtherEnter);
	/**
	 * 获取其他入库单详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public StorageOtherEnter getStorageOtherEnterInfo(@Param("id")String id);
	/**
	 * 根据其他入库单编号获取明细列表
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public List getStorageOtherEnterDetailListByID(@Param("billid")String billid);
	/**
	 * 获取其他入库单列表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public List showStorageOtherEnterList(PageMap pageMap);
	/**
	 * 获取其他入库单列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int showStorageOtherEnterListCount(PageMap pageMap);
	/**
	 * 根据入库单编码查询明细金额总计
	 * @throws
	 * @author lin_xx
	 * @date 2017/11/17
	 */
	public Map getStorageOtherEnterAmountByID(@Param("billid")String billid);
	/**
	 * 删除其他入库单明细
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int deleteStorageOtherEnterDetailListByBillid(@Param("billid")String billid);
	/**
	 * 其他入库单修改
	 * @param storageOtherEnter
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int editStorageOtherEnter(StorageOtherEnter storageOtherEnter);
	/**
	 * 其他入库单删除
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int deleteStorageOtherEnter(@Param("id")String id);
	/**
	 * 其他入库单审核
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int auditStorageOtherEnter(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);
	/**
	 * 更新其他入库单 相关批次号
	 * @param id
	 * @param summarybatchid
	 * @return
	 * @author chenwei 
	 * @date 2015年10月27日
	 */
	public int updateStorageOtherEnterDetailSummarybatchid(@Param("id")String id,@Param("summarybatchid")String summarybatchid);
	/**
	 * 其他入库单反审
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Feb 14, 2014
	 */
	public int oppauditStorageOtherEnter(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	
	/**
	 * 获取其他入库单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showStorageOtherEnterListBy(Map map);
	/**
	 * 更新打印次数
	 * @param storageOtherEnter
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public int updateOrderPrinttimes(StorageOtherEnter storageOtherEnter);

	/**
	 * 获取ID
	 * @param sourceid
	 * @return
	 */
	public String getStorageOtherEnterIdBySourceId(@Param("sourceid")String sourceid);

	/**
	 * 获取其它入库单生成凭证数据
	 * @param list
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List getStorageOtherEnterSumData(List list);

	/**
	 * 获取其它入库单金额
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Dec 26, 2017
	 */
	public Map getStorageOtherEnterSumById(@Param("id") String id);
}
