package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.CheckList;
import com.hd.agent.storage.model.CheckListDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 盘点单dao
 * @author chenwei
 */
public interface CheckListMapper {
	/**
	 * 添加盘点单信息
	 * @param checkList
	 * @return
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public int addCheckList(CheckList checkList);
	/**
	 * 添加盘点单明细信息
	 * @param checkListDetail
	 * @return
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public int addCheckListDetail(CheckListDetail checkListDetail);
	/**
	 * 盘点单明细批量插入
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Aug 8, 2013
	 */
	public int addCheckListDetailBatch(@Param("list")List list);
	/**
	 * 获取盘点单信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public CheckList getCheckListInfo(@Param("id")String id);
	/**
	 * 根据盘点单号和盘点次数 获取盘点单信息
	 * @param id
	 * @param checkno
	 * @return
	 * @author chenwei 
	 * @date Jan 21, 2014
	 */
	public CheckList getCheckListInfoByChecklistidAndNo(@Param("id")String id,@Param("checkno")int checkno);

	/**
	 * 根据盘点单编号获取盘点单明细列表（分页）
	 * @param pageMap
	 * @return
	 */
	public List getCheckListDetailListByPageMap(PageMap pageMap);

	/**
	 * 根据盘点单编号获取盘点单明细总数量（分页）
	 * @param pageMap
	 * @return
	 */
	public int getCheckListDetailCountByPageMap(PageMap pageMap);

	/**
	 * 根据盘点单编号获取盘点单明细总合计（分页）
	 * @param pageMap
	 * @return
	 */
	public CheckListDetail getCheckListDetailSumByPageMap(PageMap pageMap);
	/**
	 * 根据盘点单编号获取盘点单明细列表
	 * @param checklistid
	 * @return
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public List getCheckListDetailListByCheckListid(@Param("checklistid")String checklistid);
	/**
	 * 获取盘点单列表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public List showCheckListData(PageMap pageMap);
	/**
	 * 获取盘点单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 22, 2013
	 */
	public int showCheckListCount(PageMap pageMap);
	/**
	 * 根据盘点单编号，获取盘点单品牌名称列表
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Feb 18, 2014
	 */
	public String getCheckListInfoBrandList(@Param("id")String id);
	/**
	 * 修改盘点单基本信息
	 * @param checkList
	 * @return
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	public int editCheckList(CheckList checkList);
	/**
	 * 根据盘点单编号删除盘点单明细
	 * @param checklistid
	 * @return
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	public int deleteCheckListDetailByCheckListid(@Param("checklistid")String checklistid);
	/**
	 * 删除盘点单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 23, 2013
	 */
	public int deleteCheckList(@Param("id")String id);
	/**
	 * 根据仓库编码获取未审核的盘点单数量
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public int getCheckListCountByStorageid(@Param("storageid")String storageid);
	/**
	 * 根据仓库编码获取未审核的盘点单编号
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public String getCheckListIdByStorageid(@Param("storageid")String storageid);
	/**
	 * 审核盘点单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public int auditCheckList(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 审核并且关闭盘点单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public int auditAndCloseCheckList(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 反审盘点单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date May 28, 2013
	 */
	public int oppauditCheckList(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 中止盘点单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public int stopCheckList(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 关闭盘点单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 28, 2013
	 */
	public int closeCheckList(@Param("id")String id);
	/**
	 * 通过来源盘点单号关闭盘点单
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public int closeCheckListBySourceid(@Param("sourceid")String sourceid);
	/**
	 * 更新盘点单参照状态
	 * @param isrefer
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public int updateCheckListRefer(@Param("isrefer")String isrefer,@Param("id")String id);
	/**
	 * 获取盘点单数量不正确的条数
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public int getCheckListNumNotTureCount(@Param("id")String id);
	/**
	 * 获取盘点单中明细未指定盘点人列表
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public List getCheckListNoUserList(@Param("id")String id);
	/**
	 * 盘点单盘点完成
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public int updateCheckListFinish(@Param("id")String id);
    /**
     * 盘点单盘点完成 明细状态标记
     * @param id
     * @return
     * @author chenwei
     * @date Aug 9, 2013
     */
    public int updateCheckListDetailTrue(@Param("id")String id);
    /**
     * 盘点单盘点完成 明细状态标记
     * @param id
     * @return
     * @author chenwei
     * @date Aug 9, 2013
     */
    public int updateCheckListDetailFalse(@Param("id")String id);
	/**
	 * 获取盘点单数量不正确的明细列表
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public List getCheckListNumNotTureList(@Param("id")String id);
	/**
	 * 获取盘点单 盘点人员列表
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public List getUseridByCheckList(@Param("id")String id);
	/**
	 * 根据盘点单 获取盘点人员盘点条数信息 包括正确不正确的
	 * @param checklistid
	 * @param checkuserid
	 * @param istrue			是否盘点正确
	 * @return
	 * @author chenwei 
	 * @date Aug 9, 2013
	 */
	public int getCheckListNumByUserid(@Param("checklistid")String checklistid,@Param("checkuserid")String checkuserid,@Param("istrue")String istrue);
	/**
	 * 获取盘点单明细详情
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Sep 29, 2013
	 */
	public CheckListDetail getCheckListDetailInfo(@Param("id")String id);
	/**
	 * 根据盘点单编号和商品编码获取盘点单明细
	 * @param checklistid
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Jan 18, 2014
	 */
	public CheckListDetail getCheckListDetailInfoByGoodsid(@Param("checklistid")String checklistid,@Param("goodsid")String goodsid);
	/**
	 * 导出盘点单
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public List exportCheckListData(PageMap pageMap);
	/**
	 * 根据导入的盘点单数据 进行盘点
	 * @param checkListDetail
	 * @return
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public int updateCheckListDetail(CheckListDetail checkListDetail);
	/**
	 * 根据订单号和商品编号修改盘点单明细
	 * @param checkListDetail
	 * @return
	 * @author wanghongteng
	 * @date 2016-8-30
	 */
	public int updateCheckListDetailByGoodsid(CheckListDetail checkListDetail);
	/**
	 * 根据订单号和商品编号删除盘点单明细
	 * @param checkListDetail
	 * @return
	 * @author wanghongteng
	 * @date 2016-8-30
	 */
	public int deleteCheckListDetailByGoodsid(CheckListDetail checkListDetail);
	/**
	 * 根据仓库获取未完成盘点的盘点单列表
     * @param map
	 * @return
	 * @author chenwei 
	 * @date Apr 24, 2014
	 */
	public List getCheckListUnfinishForPhone(Map map);
	/**
	 * 根据盘点单来源编号获取盘点单列表
	 * @param sourceid
	 * @return
	 * @author chenwei 
	 * @date Apr 24, 2014
	 */
	public List getCheckListBySourceid(@Param("sourceid")String sourceid);

	/**
	 * 检查该商品是否存在该单据明细中
	 * @param goodsid
	 * @param checklistid
	 * @return
	 */
	public int doCheckListDetailIsHadGoods(@Param("goodsid")String goodsid, @Param("checklistid")String checklistid);

	/**
	 * 去除要删除明细的盘点单明细数据
	 * @param map2
	 * @return
	 */
	public List<CheckListDetail> getCheckListDetailListRemoveDelDetailList(Map map2);

    /**
     * 获取仓库多少天内 库存变动过的记录
     * @param storageid                     仓库编号
     * @param days                            多少天内变动过库存记录
     * @param isCheckListUseBatch           是否按批次盘点1是0否
     * @return
     */
    public List<CheckListDetail> getCheckListDetailChangeListInDays(@Param("storageid") String storageid,@Param("days") String days,@Param("isCheckListUseBatch") String isCheckListUseBatch);

	/**
	 * 获取盘点单明细数量
	 * @return
	 * @author chenwei
	 * @date Aug 8, 2013
	 */
	public int getCheckListDetailCountByChecklistid(@Param("checklistid")String checklistid);

	/**
	 * 检查该商品是否存在该单据明细中
	 * @param id
	 * @return
	 */
	public CheckListDetail getCheckListDetailByGoodsidAndChecklistid(@Param("id")String id);

	/**
	 * 根据盘点单编号更新打印次数
	 * @param id
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-03
	 */
	public int updatePrintTimes(@Param("id")String id);

    /**
     * 获取仓库库存盘点记录数据列表
     * @param pageMap
     * @return
     */
    public List getStorageGoodsCheckLogData(PageMap pageMap);

    /**
     * 获取仓库库存盘点记录数量
     * @param pageMap
     * @return
     */
    public int getStorageGoodsCheckLogCount(PageMap pageMap);

    /**
     * 获取仓库 商品 最近几次盘点记录
     * @param storageid
     * @param goodsid
     * @param num
     * @return
     */
    public List<CheckListDetail> getStroageGoodsCheckList(@Param("storageid") String storageid,@Param("goodsid") String goodsid,@Param("num") String num);
}