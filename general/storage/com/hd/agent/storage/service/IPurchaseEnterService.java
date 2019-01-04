/**
 * @(#)IPurchaseEnterService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 8, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.model.PurchaseEnterDetail;
import com.hd.agent.storage.model.StorageDeliveryEnterForJob;

import java.util.List;
import java.util.Map;

/**
 * 
 * 采购入库单service
 * @author chenwei
 */
public interface IPurchaseEnterService {
	/**
	 * 根据采购订单生成采购入库单
	 * @param buyOrder		采购订单基本信息
	 * @param detailList	采购订单明细
	 * @return	返回采购入库单编号或者null(表示生成失败)
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public String addPurchaseEnterByBuyOrder(BuyOrder buyOrder,List<BuyOrderDetail> detailList) throws Exception;
	/**
	 * 获取商品档案详细信息
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public GoodsInfo getGoodsInfo(String goodsid) throws Exception;
	/**
	 * 添加采购入库单
	 * @param purchaseEnter
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public boolean addPurchaseEnter(PurchaseEnter purchaseEnter,List<PurchaseEnterDetail> detailList) throws Exception;
	/**
	 * 获取采购入库单详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public Map getPurchaseEnterInfo(String id) throws Exception;
	/**
	 * 获取采购入库单列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 13, 2013
	 */
	public PageData showPurchaseEnterList(PageMap pageMap) throws Exception;
	/**
	 * 采购入库单修改
	 * @param purchaseEnter
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public boolean editPurchaseEnter(PurchaseEnter purchaseEnter,List<PurchaseEnterDetail> detailList) throws Exception;
	/**
	 * 删除采购入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public boolean deletePurchaseEnter(String id) throws Exception;
	/**
	 * 审核采购入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public Map auditPurchaseEnter(String id) throws Exception;
	/**
	 * 反审采购入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public Map oppauditPurchaseEnter(String id) throws Exception;
	/**
	 * 根据采购订单编号生成采购入库单
	 * @param dispatchbillid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 22, 2013
	 */
	public Map addPurchaseEnterByRefer(String dispatchbillid) throws Exception;
	/**
	 * 提交采购入库单到工作流中
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 24, 2013
	 */
	public Map submitPurchaseEnterProcess(String id) throws Exception;

	/**
	 * 根据上游单据编号，获取采购入库编号列表
	 * @param sourceid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月13日
	 */
	public List showPurchaseEnterIdListBySourceid(String sourceid) throws Exception;
	
	/**
	 * 获取采购入库单列表<br/>
	 * map中参数:<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015-05-22
	 */
	public List showPurchaseEnterListBy(Map map) throws Exception;
	/**
	 * 更新打印次数
	 * @param purchaseEnter
	 * @author zhanghonghui 
	 * @date 2015-05-22
	 */
	public boolean updateOrderPrinttimes(PurchaseEnter purchaseEnter) throws Exception;
	/**
	 * 更新打印次数
	 * @param list
	 * @author zhanghonghui 
	 * @date 2015-05-22
	 */
	public void updateOrderPrinttimes(List<PurchaseEnter> list) throws Exception;
	/**
	 * 获取采购入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年5月22日
	 */
	public PurchaseEnter showPurchaseEnter(String id) throws Exception;
	

	/**
	 * 根据代配送(客户退货入库单)生产采购入库单
	 * @return
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2015年9月28日
	 */
	public	boolean addPurchaseEnterByDeliveryOrder (StorageDeliveryEnterForJob enterJob,List<StorageDeliveryEnterForJob> enterJobDetail) throws Exception;
	/**
	 * 获取批次号等相关信息
	 * @param goodsid
	 * @param produceddate
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年11月4日
	 */
	public Map getBatchno(String goodsid,String produceddate) throws Exception;

    /**
     * 根据截止日期获取批次号与生产日期
     * @param goodsid       商品编码
     * @param deadline      截止日期
     * @return
     * @throws Exception
     */
    public Map getBatchnoByDeadline(String goodsid,String deadline) throws Exception;

    /**
     * 为扫描枪提供采购入库单列表
     * @param map               查询条件
     * @return
     * @ths Exception
     */
    public List getPurchaseEnterForScanList(Map map) throws Exception;

    /**
     * 获取采购入库单明细
     * @param id
     * @return
     * @throws Exception
     */
    public List getPurchaseEnterDetail(String id) throws Exception;

    /**
     * 扫描枪采购入库单上传并且审核
     * @param json
     * @return
     * @throws Exception
     */
    public Map uploadPurchaseEnterAndAudit(String json) throws Exception;

    public Map auditMultiPurchaseEnter(String ids) throws Exception ;

	public List getPurchaseEnterDetailSumGoodsidList(String buyorderid) throws Exception;

}

