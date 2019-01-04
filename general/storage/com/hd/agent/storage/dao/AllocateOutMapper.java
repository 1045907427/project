package com.hd.agent.storage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.AllocateOut;
import com.hd.agent.storage.model.AllocateOutDetail;

/**
 * 调拨出库单dao
 * @author chenwei
 */
public interface AllocateOutMapper {
	/**
	 * 添加调拨出库单
	 * @param allocateOut
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int addAllocateOut(AllocateOut allocateOut);
	/**
	 * 添加调拨出库单明细
	 * @param allocateOutDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int addAllocateOutDetail(AllocateOutDetail allocateOutDetail);
	/**
	 * 根据单据编号获取调拨出库单信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public AllocateOut getAllocateOutByID(@Param("id")String id);
	/**
	 * 根据来源单据编号获取调拨出库单信息
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public AllocateOut getAllocateOutBySourceid(@Param("sourceid")String sourceid);
	/**
	 * 删除调拨出库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int deleteAllocateOut(@Param("id")String id);
	/**
	 * 删除调拨出库单明细
	 * @param billno
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int deleteAllocateOutDetail(@Param("billno")String billno);
	/**
	 * 获取调拨出库单列表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public List showAllocateOutList(PageMap pageMap);
	/**
	 * 获取调拨出库单列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int showAllocateOutCount(PageMap pageMap);
	/**
	 * 根据单据编号获取调拨出库单明细列表
	 * @param billno
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public List getAllocateOutDetailList(@Param("billno")String billno);
	/**
	 * 调拨出库单修改
	 * @param allocateOut
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int editAllocateOut(AllocateOut allocateOut);
	/**
	 * 调拨出库单审核
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public int auditAllocateOut(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);
	/**
	 * 调拨出库单反审
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public int oppauditAllocateOut(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 调拨出库单明细详情
	 * @param billno
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public AllocateOutDetail getAllocateOutDetailInfo(@Param("billno")String billno,@Param("id")String id);
	/**
	 * 更新调拨出库单参照状态
	 * @param isrefer
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public int updateAllocateOutRefer(@Param("isrefer")String isrefer,@Param("id")String id);
	/**
	 * 关闭调拨出库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public int closeAllocateOut(@Param("id")String id);
	/**
	 * 获取调拨单明细数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 29, 2013
	 */
	public List showAllocateOutDetailListQuery(PageMap pageMap);
	/**
	 * 获取调拨单明细数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 29, 2013
	 */
	public int showAllocateOutDetailCountQuery(PageMap pageMap);
	/**
	 * 显示调拨单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * printstatus:1，表示打印时的，状态3，4<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-9
	 */
	public List showAllocateOutListBy(Map map);

	/**
	 * 更新打印次数
	 * @param id
	 * @param printtimes
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public int updateAllocateOutPrinttimes(AllocateOut allocateOut);
	/**
	 * 流水账
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-10
	 */
	public List showAllocateOutJournalList(PageMap pageMap);
    /**
     * 调拨单数据导出
     * @param pageMap
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2013-10-10
     */
    public List<Map<String, Object>> getAllocateOutListExport(PageMap pageMap);

	/**
	 * 调拨单出库审核
	 * @param allocateOut
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	public int auditAllocateStorageOut(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);

	/**
	 * 根据商品 仓库 单据编码获取调拨单明细
	 * @param goodsid
	 * @param storageid
	 * @param billno
	 * @return com.hd.agent.storage.model.AllocateOutDetail
	 * @throws
	 * @author luoqiang
	 * @date Nov 13, 2017
	 */
	public AllocateOutDetail getAllocateOutDetailByGoodsAndBill(@Param("goodsid") String goodsid,@Param("billno") String billno);

	/**
	 * 调拨入库审核
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Nov 20, 2017
	 */
	public int auditAllocateStorageEnter(@Param("id")String id);

	/**
	 * 调拨反审出库
	 * @param id
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Nov 20, 2017
	 */
	public int oppauditAllocateStorageOut(@Param("id")String id);

	/**
	 * 获取调拨差额报表数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 01, 2017
	 */
	public List getAllocateDiffAmountList(PageMap pageMap);

	/**
	 * 获取调拨差额报表数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Dec 01, 2017
	 */
	public int getAllocateDiffAmountCount(PageMap pageMap);

	/**
	 * 调拨差额报表合计
	 * @param pageMap
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Dec 05, 2017
	 */
	public Map getSumAllocateDiffAmount(PageMap pageMap);

	/**
	 * 获取调拨单商品待发数据
	 * @param goodsid
	 * @param storageid
	 * @param summarybatchid
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Jan 26, 2018
	 */
	public List showGoodsAllocateOutWaitListData(@Param("goodsid") String goodsid,@Param("storageid") String storageid,@Param("summarybatchid") String summarybatchid );

	/**
	 * 判断手机调拨出库是否上传过
	 * @param billid
	 * @return
	 */
	public int hasPhoneBillByAllocateOut(@Param("billid")String billid);

	/**
	 * 根据手机调拨出库单 获取调拨出库单编号
	 * @param billid
	 * @return
	 */
	public String getAllocateOutBillIDByPhoneBillid(@Param("billid")String billid);
	/**
	 * 获取调拨出库单列表 (提供手机端查询)
	 * @param pageMap
	 * @return
	 * @author wanghongteng
	 * @date 2018-3-7
	 */
	public List showAllocateOutListForPhone(PageMap pageMap);
}
