/**
 * @(#)IStorageForPurchaseService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 10, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import com.hd.agent.purchase.model.*;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;

import java.util.List;
import java.util.Map;

/**
 * 
 * 提供给采购模块的service接口
 * @author chenwei
 */
public interface IStorageForPurchaseService {
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
	 * 根据上游单据编号删除采购入库单信息
	 * @param sourceid		采购订单编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 14, 2013
	 */
	public boolean deletePurchaseEnterBySourceid(String sourceid) throws Exception;
	/**
	 * 更新采购入库单参照状态
	 * @param isrefer 参照状态1为已参照0为未参照
	 * @param id 采购入库单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public boolean updatePurchaseEnterRefer(String isrefer, String id) throws Exception;
	/**
	 * 采购进货单审核后，关闭采购入库单
	 * @param id			采购入库单编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public boolean updatePurchaseEnterClose(String id) throws Exception;
	/**
	 * 采购进货单反审后，打开采购入库单
	 * @param id			采购入库单编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public boolean updatePurchaseEnterOpen(String id) throws Exception;
	
	/**
	 * 根据采购入库单编号获取采购入库单明细
	 * 返回Map对象purchaseEnter基本表单信息 detailList明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public Map getPurchaseEnterByID(String id) throws Exception;
	/**
	 * 进货单开票后 更新采购入库单明细开票状态
	 * @param billid		采购入库单编号
	 * @param detailid		采购入库单明细编号
	 * @param isinvoice		1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseEnterDetailIsinvoice(String billid,String detailid,String isinvoice) throws Exception;
	/**
	 *  进货单核销后 更新采购入库单明细核销状态
	 * @param billid			采购入库单编号
	 * @param detailid			采购入库单明细编号
	 * @param iswriteoff		1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseEnterDetailIswriteoff(String billid,String detailid,String iswriteoff) throws Exception;
	/**
	 * 进货单价格 金额修改后 回写采购入库单
     * @param arrivalOrder
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseEnterDetailPrice(ArrivalOrder arrivalOrder,List<ArrivalOrderDetail> list) throws Exception;

    /**
     * 进货单反审 价格金额修改后 回写采购入库单
     * @param arrivalOrder
     * @param list
     * @return
     * @throws Exception
     */
    public boolean updatePurchaseEnterDetailPriceByOppaudit(ArrivalOrder arrivalOrder,List<ArrivalOrderDetail> list) throws Exception;
	/*-----------------采购退货出库单接口--------------------*/
	/**
	 * 根据采购退货单生成采购退货出库单
	 * @param returnOrder
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public Map addPurchaseRejectOut(ReturnOrder returnOrder,List<ReturnOrderDetail> list) throws Exception;
	/**
	 * 根据采购退货单编号删除采购退货出库单
	 * @param sourceid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public boolean deletePurchaseRejectOutBySourceid(String sourceid) throws Exception;
	
	/**
	 * 根据上游单据采购退货单编号生成采购退货出库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 24, 2013
	 */
	public Map addPurchaseRejectOutByRefer(String id) throws Exception;
	
	/**
	 * 退货通知单开票后 更新采购退货入库单明细开票状态
	 * @param billid		退货通知单编号
	 * @param detailid		退货通知单明细编号
	 * @param isinvoice		1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseRejectDetailIsinvoice(String billid,String detailid,String isinvoice) throws Exception;
	/**
	 *  退货通知单核销后 更新采购退货入库单明细核销状态
	 * @param billid			退货通知单编号
	 * @param detailid			退货通知单明细编号
	 * @param iswriteoff		1是0否
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 15, 2013
	 */
	public boolean updatePurchaseRejectDetailIswriteoff(String billid,String detailid,String iswriteoff) throws Exception;
	
	/**
	 * 根据商品编号仓库编号 获取商品在该仓库中的现存量信息
	 * @param goodsid
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 14, 2013
	 */
	public StorageSummary getStorageSummarySumByGoodsidAndStorageid(String goodsid,String storageid) throws Exception;
	/**
	 * 根据库存明细商品编号（批次商品数据编号） 获取库存信息
	 * @param summarybatchid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 1, 2014
	 */
	public StorageSummaryBatch getStorageSummaryBatchBySummarybatchid(String summarybatchid) throws Exception;
	/**
	 * 获取商品不是批次管理 仓库的总的批次量
	 * @param storageid		仓库编号
	 * @param goodsid		商品编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年11月4日
	 */
	public StorageSummaryBatch getStorageSummaryBatchNoBatch(String storageid,String goodsid) throws Exception;
    /**
     * 更新采购入库单明细的核销状态
     * @param invoiceid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public boolean updatePurchaseEnterDetailUncancel(String invoiceid)throws Exception;

    /**
     * 更新采购退货出库单明细的核销状态
     * @param invoiceid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-24
     */
    public boolean updatePurchaseRejectDetailUncancel(String invoiceid)throws Exception;
    /**
     * 获取未审核的采购入库单编号列表
     * @param buyorderid
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年6月10日
     */
    public List getPuchaseEnterUnAuditIDList(String buyorderid) throws Exception;
    /**
     * 验收时根据采购退货通知单明细更新采购退货出库单明细
     * @param returnOrderDetail
     * @param isUpdateCostprice     是否调整成本价
     * @return
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年6月19日
     */
    public boolean updatePurchaseRejectOutDetailByReturnCheck(ReturnOrderDetail returnOrderDetail,boolean isUpdateCostprice) throws Exception;
    /**
     * 根据MAP中参数更新采购退货出库单<br/>
     * rejectOut: 需要更新的出库单信息<br/>
     * id : 出库单编号 <br/>
     * sourceid : 上游单据编号 <br/>
     * sourcetype : 上游单据类型<br/>
     * ischeck : 是否验收,1已验收，0未验收<br/>
     * @return
     * @author zhanghonghui 
     * @date 2015年6月19日
     */
    public boolean updatePurchaseRejectOutBy(Map map) throws Exception;
    /**
     * 取消验收时根据采购退货通知单明细根据来源单据编号更新采购退货出库单明细
     * @param sourceid
     * @throws Exception
     * @author zhanghonghui 
     * @date 2015年6月29日
     */
    public void updatePurchaseRejectOutDetailByReturnCheckCancel(String sourceid) throws Exception;
    
    /**
     * 代配送生成采购退货出库单
     * @param returnOrder
     * @param list
     * @return
     * @throws Exception
     * @author huangzhiqian
     * @date 2015年9月30日
     */
    public boolean addPurchaseRejectOutForJob(ReturnOrder returnOrder,List<ReturnOrderDetail> list) throws Exception ;
 
    
    
}

