/**
 * @(#)IAllocateService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 24, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.storage.model.AllocateEnter;
import com.hd.agent.storage.model.AllocateEnterDetail;
import com.hd.agent.storage.model.AllocateNotice;
import com.hd.agent.storage.model.AllocateNoticeDetail;
import com.hd.agent.storage.model.AllocateOut;
import com.hd.agent.storage.model.AllocateOutDetail;

/**
 * 
 * 调拨单相关service
 * @author chenwei
 */
public interface IAllocateService {
	/**
	 * 调拨通知单添加
	 * @param allocateNotice
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public Map addallocateNotice(AllocateNotice allocateNotice,List<AllocateNoticeDetail> detailList) throws Exception;
	/**
	 * 获取调拨通知单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public PageData showAllocateNoticeList(PageMap pageMap) throws Exception;

	/**
	 *  获取批量添加的商品
	 * @param pageMap
	 * @return
	 * @author wanghongteng
	 * @date 2017-12-28
	 */
	public PageData getGoodsByBrandAndSort(PageMap pageMap,String storageid) throws Exception;

	/**
	 * 添加批量商品明细
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2015-12-2
	 */
	public List AddOrderDetailByBrandAndSort(List<AllocateNoticeDetail>  allocateNoticeDetailList)throws Exception ;

    /**
     * 为手机提供调拨通知单列表
     * @param pageMap
     * @return
     * @throws Exception
     */
    public List showAllocateNoticeListForPhone(PageMap pageMap) throws Exception;
	/**
	 * 获取调拨通知单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public Map getAllocateNoticeInfo(String id) throws Exception;
	/**
	 * 根据单据编号和明细编号获取调拨通知单明细详情
	 * @param billno
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 29, 2013
	 */
	public AllocateNoticeDetail getAllocateNoticeDetailInfo(String billno,String id) throws Exception;
	/**
	 * 调拨通知单修改
	 * @param allocateNotice
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public boolean editAllocateNotice(AllocateNotice allocateNotice,List<AllocateNoticeDetail> detailList) throws Exception;
	/**
	 * 调拨通知单删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public boolean deleteAllocateNotice(String id) throws Exception;
	/**
	 * 调拨通知单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public Map auditAllocateNotice(String id) throws Exception;
	/**
	 * 调拨通知单反审
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public Map oppauditAllocateNotice(String id) throws Exception;
	/**
	 * 通过导入excel添加调拨通知单
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年7月21日
	 */
	public Map addAllocateNoticeByImport(List<Map> list) throws Exception;
	/**
	 * 获取调拨通知单明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月28日
	 */
	public List getAllocateNoticeByExport(PageMap pageMap) throws Exception;
	
	/*-------------调拨出库单------------------ */
	/**
	 * 调拨出库单添加
	 * @param allocateOut
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public Map addAllocateOut(AllocateOut allocateOut,List<AllocateOutDetail> detailList) throws Exception;
	/**
	 * 删除调拨出库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public boolean deleteAllocateOut(String id) throws Exception;
	/**
	 * 获取调拨出库单数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public PageData showAllocateOutList(PageMap pageMap) throws Exception;
	/**
	 * 获取调拨出库单详情信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public Map getAllocateOutInfo(String id) throws Exception;
	
	/**
	 * 调拨出库单修改
	 * @param allocateOut
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public Map editAllocateOut(AllocateOut allocateOut,List<AllocateOutDetail> list) throws Exception;
	/**
	 * 根据来源单据编号生成调拨出库单
	 * @param sourceid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public Map addAllocateOutByRefer(String sourceid) throws Exception;
	/**
	 * 调拨出库单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public Map auditAllocateOut(String id) throws Exception;
	/**
	 * 调拨出库单反审
	 * 已废弃
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public boolean oppauditAllocateOut(String id) throws Exception;
	/**
	 * 根据调拨出库单编号和明细编号 获取调拨出库单明细详情
	 * @param id
	 * @param detailid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public AllocateOutDetail getAllocateOutDetailInfo(String id,String detailid) throws Exception;
	/**
	 * 获取调拨单明细查询数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 29, 2013
	 */
	public PageData showAllocateOutDetailListQuery(PageMap pageMap) throws Exception;
    /**
     * 导入调拨（出库）单
     * @param list
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 11, 2015
     */
    public Map addAllocateOutByImport(List<Map> list) throws Exception;
    /**
     *  导出调拨（出库）单
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 11, 2015
     */
    public List<Map<String, Object>> getAllocateOutByExport(PageMap pageMap) throws Exception;


	/*--------------------调拨入库单-----------------------*/
	/**
	 * 添加调拨入库单
	 * @param allocateEnter
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public Map addAllocateEnter(AllocateEnter allocateEnter,List<AllocateEnterDetail> list) throws Exception;
	/**
	 * 获取调拨入库单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public PageData showAllocateEnterList(PageMap pageMap) throws Exception;
	/**
	 * 获取调拨入库单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public Map getAllocateEnterInfo(String id) throws Exception;
	/**
	 * 调拨入库单修改
	 * @param allocateEnter
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public boolean editAllocateEnter(AllocateEnter allocateEnter,List<AllocateEnterDetail> detailList) throws Exception;
	/**
	 * 调拨入库单删除
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public boolean deleteAllocateEnter(String id) throws Exception;
	/**
	 * 调拨入库单审核
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public Map auditAllocateEnter(String id) throws Exception;
	/**
	 * 根据来源单据编号生成调拨入库单
	 * @param sourceid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public Map addAllocateEnterByRefer(String sourceid) throws Exception;
	/**
	 * 调拨出库单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public Map submitAllocateOutPageProcess(String id) throws Exception;
	/**
	 * 调拨入库单体提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public Map submitAllocateEnterPageProcess(String id) throws Exception;
	/**
	 * 调拨通知单提交工作流
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 25, 2013
	 */
	public boolean submitAllocateNoticePageProcess(String id) throws Exception;
	/**
	 * 显示调拨单列表
	 * map中参数:<br/>
	 * showdetail:显示明细列表<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * printstatus:1，表示打印时的，状态3，4<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-9
	 */
	public List showAllocateOutListBy(Map map) throws Exception;

	/**
	 * 更新调拨单打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public boolean updateAllocateOutPrinttimes(AllocateOut allocateOut) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2013-9-10
	 */
	public void updateAllocateOutPrinttimes(List<AllocateOut> list) throws Exception;
	/**
	 * 流水账
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-10-10
	 */
	public List showAllocateOutJournalList(PageMap pageMap) throws Exception;
	/**
	 * 显示调拨单信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-10
	 */
	public AllocateOut getAllocateOutPureInfo(String id) throws Exception;

    /**
     * 根据手机调拨申请单编号 获取是否上传
     * @param billid
     * @return
     * @throws Exception
     */
    public String hasPhoneBillByAllocateNotice(String billid) throws Exception;

	/**
	 * 调拨单审核出库
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	public Map auditAllocateStorageOut(String id) throws Exception;

	/**
	 * 调拨单审核入库
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	public Map auditAllocatStorageEnter(String id) throws Exception;

	/**
	 * 调拨单反审出库
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Nov 08, 2017
	 */
	public Map oppauditAllocateStorageOut(String id) throws Exception;

	/**
	 * 获取商品库存成本价
	 * @param goodsid
	 * @param storageid
	 * @return java.math.BigDecimal
	 * @throws
	 * @author luoqiang
	 * @date Nov 14, 2017
	 */
	public BigDecimal getStorageSummaryCostprice(String goodsid,String storageid) throws Exception;

	/**
	 * 获取调拨差额报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Dec 01, 2017
	 */
	public PageData getAllocateDiffAmountData(PageMap pageMap) throws Exception;

	/**
	 * 获取调拨单待发商品数据
	 * @param goodsid
	 * @param storageid
	 * @param summarybatchid
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Jan 26, 2018
	 */
	public List showAllocateOutGoodsWaitListData(String goodsid,String storageid,String summarybatchid) throws Exception;

	/**
	 * 根据手机调拨出库编号 获取是否上传
	 * @param billid
	 * @return
	 * @throws Exception
	 */
	public String hasPhoneBillByAllocateOut(String billid) throws Exception;
	/**
	 * 为手机提供调拨出库单列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 */
	public List showAllocateOutListForPhone(PageMap pageMap) throws Exception;
	/**
	 * 根据billid获取调拨出库明细
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2015-12-2
	 */
	public List<AllocateOutDetail> getAllocateOutDetailList(String billid)throws Exception ;
}

