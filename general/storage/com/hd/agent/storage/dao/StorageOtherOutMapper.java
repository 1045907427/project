package com.hd.agent.storage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageOtherOut;
import com.hd.agent.storage.model.StorageOtherOutDetail;

/**
 * 其他出库单dao
 * @author chenwei
 */
public interface StorageOtherOutMapper {
	/**
	 * 其他出库单明细添加
	 * @param storageOtherEnterDetail
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int addStorageOtherOutDetail(StorageOtherOutDetail storageOtherEnterDetail);
	/**
	 * 其他出库单添加
	 * @param storageOtherEnter
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int addStorageOtherOut(StorageOtherOut storageOtherEnter);
	/**
	 * 获取其他出库单详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public StorageOtherOut getStorageOtherOutInfo(@Param("id")String id);
	/**
	 * 根据其他出库单编号获取明细列表
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public List getStorageOtherOutDetailListByID(@Param("billid")String billid);
	/**
	 * 获取其他出库单列表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public List showStorageOtherOutList(PageMap pageMap);
	/**
	 * 获取其他出库单列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int showStorageOtherOutListCount(PageMap pageMap);
	/**
	 * 根据出库单编码查询明细金额总计
	 * @throws
	 * @author lin_xx
	 * @date 2017/11/17
	 */
	public Map getStorageOtherOutAmountByID(@Param("billid")String billid);
	/**
	 * 删除其他出库单明细
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int deleteStorageOtherOutDetailListByBillid(@Param("billid")String billid);
	/**
	 * 其他出库单修改
	 * @param storageOtherEnter
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int editStorageOtherOut(StorageOtherOut storageOtherEnter);
	/**
	 * 其他出库单删除
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int deleteStorageOtherOut(@Param("id")String id);
	/**
	 * 其他出库单审核
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public int auditStorageOtherOut(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);
	/**
	 * 其他出库单反审
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Feb 14, 2014
	 */
	public int oppauditStorageOtherOut(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 根据编号获取其他出库单明细详细信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 7, 2013
	 */
	public StorageOtherOutDetail getStorageOtherOutDetail(@Param("id")String id);
	
	/**
	 * 获取其他出库单列表<br/>
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
	public List showStorageOtherOutListBy(Map map);
	/**
	 * 更新打印次数
	 * @param storageOtherOut
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public int updateOrderPrinttimes(StorageOtherOut storageOtherOut);

	/**
	 * 获取ID
	 * @param sourceid
	 * @return
	 */
	public String getStorageOtherOutIdBySourceId(@Param("sourceid")String sourceid);

	/**
	 * 获取其它出库单生成凭证的数据
	 * @param list
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List getStorageOtherOutSumData(List list);

	/**
	 * 获取其它出库单金额
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Dec 26, 2017
	 */
	public Map getStorageOtherSumById(@Param("id")String id);
}
