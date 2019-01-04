/**
 * @(#)IStorageForSalesService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 8, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service;

import com.hd.agent.sales.model.*;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;

import java.util.List;
import java.util.Map;

/**
 * 
 * 库存管理接口 提供销售模块使用
 * @author chenwei
 */
public interface IStorageForSalesService {
	/**
	 * 通过销售发货通知单与明细，生成销售出库单
	 * Map:flag:true成功 false失败（可用量不足，不能自动生成）
	 *     msg:返回信息（失败提醒信息）
	 *     saleoutid :销售出库单编号
	 * @param dispatchBill
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 31, 2013
	 */
	public Map addSaleOutByDispatchbill(DispatchBill dispatchBill,List<DispatchBillDetail> detailList) throws Exception;
	/**
	 * 通过销售发货通知单与明细，生成销售出库单
	 * 销售发货通知单来源直营销售单（车销）
	 * map flag:true发货单生成并审核通过 false失败
	 * receiptid：发货回单编号
	 * @param dispatchBill
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 3, 2013
	 */
	public Map addSaleOutByOrdercar(DispatchBill dispatchBill,List<DispatchBillDetail> detailList) throws Exception;
	/**
	 * 通过来源单据编号 删除销售出库单信息
	 * @param sourceid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public boolean deleteSaleOutBySourceid(String sourceid) throws Exception;
	/**
	 * 更新销售出库单参照状态
	 * @param isrefer 参照状态1为已参照0为未参照
	 * @param id 销售出库单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public boolean updateSaleoutRefer(String isrefer, String id) throws Exception;
	/**
	 * 回单审核时，关闭销售出库单
	 * @param id			发货单号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public boolean updateSaleOutClose(String id) throws Exception;
	/**
	 * 回单反审时，打开销售出库单
	 * @param id			发货单号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public boolean updateSaleOutOpen(String id) throws Exception;
	/**
	 * 回单审核时，回写销售出库单明细
	 * @param detailList
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public void updateSaleOutBack(List<ReceiptDetail> detailList) throws Exception;
	/**
	 * 根据销售订单编号 判断回单是否可以审核
	 * @param saleorderid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 9, 2013
	 */
	public boolean receiptCanAuditBySaleorderid(String saleorderid) throws Exception;
	/**
	 * 回单反审时清除销售出库单明细回写
	 * @param detailList
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 8, 2013
	 */
	public void updateClearSaleOutBack(List<ReceiptDetail> detailList) throws Exception;
	/**
	 * 根据销售单明细列表 判断商品在仓库中 数量是否充足
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月23日
	 */
	public Map isGoodsEnoughByOrderDetailLit(List<OrderDetail> list) throws Exception;
	/**
	 * 根据发货通知单明细列表 判断商品在指定仓库中 数量是否充足
	 * @param storageid
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 3, 2014
	 */
	public Map isGoodsEnoughByOrderDetailInStorage(String storageid,List<OrderDetail> detailList) throws Exception;
	/**
	 * 根据发货通知单明细列表 判断商品在仓库中 数量是否充足
	 * Map flag:true是false否 
	 * 		msg：返回的提醒信息
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 26, 2013
	 */
	public Map isGoodsEnoughByDispatchBillDetail(List<DispatchBillDetail> detailList) throws Exception;
	/**
	 * 根据发货通知单明细列表 判断商品在指定仓库中 数量是否充足
	 * @param storageid
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 3, 2014
	 */
	public Map isGoodsEnoughByDispatchBillDetailInStorage(String storageid,List<DispatchBillDetail> detailList) throws Exception;
	
	/**
	 * 判断直营销售单审核，相关仓库商品数量是否足够
	 * map falg:true数量充足 false 不足
	 * msg 提醒信息
	 * @param orderCar
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 3, 2013
	 */
	public Map isGoodsEnoughByOrdercar(OrderCar orderCar,List<OrderCarDetail> detailList) throws Exception;
	/**
	 * 根据销售订单明细判断商品在仓库中数量是否充足
	 * @param orderDetail
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月23日
	 */
	public boolean isGoodsEnoughByOrderDetail(OrderDetail orderDetail) throws Exception;
	/**
	 * 根据发货通知单明细判断商品在仓库中 数量是否充足
	 * (发货仓库和参与总量控制的仓库)
	 * @param dispatchBillDetail
	 * @return
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	public boolean isGoodsEnoughByDispatchBillDetail(DispatchBillDetail dispatchBillDetail) throws Exception;
	
	/**
	 * 根据商品编号 获取商品在库存中的现存量信息
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 10, 2013
	 */
	public StorageSummary getStorageSummarySumByGoodsid(String goodsid) throws Exception;
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
	 * 根据库存批次编号 获取库存批次信息
	 * @param summarybatchid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月21日
	 */
	public StorageSummaryBatch getStorageSummaryBatchByID(String summarybatchid) throws Exception;
	/**
	 * 根据条形码和商品编码 获取相同条形码不同商品编码的 商品仓库库存信息
	 * @param barcode
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 5, 2013
	 */
	public List getStorageSummarySumByBarcode(String barcode,String goodsid) throws Exception;
	/**
	 * 根据条形码和商品编码 在指定仓库中 获取相同条形码不同商品编码的 商品仓库库存信息
	 * @param barcode
	 * @param goodsid
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月29日
	 */
	public List getStorageSummarySumByBarcodeInStorageid(String barcode,String goodsid,String storageid) throws Exception;
	
	/*----------------销售退货入库单接口-------------------- */
	/**
	 * 通过销售退货通知单生成销售退货入库单
	 * @param rejectBill		销售退货通知单基本信息
	 * @param detailList		销售退货通知单明细
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 17, 2013
	 */
	public String addSaleRejectEnterByRejectBill(RejectBill rejectBill,List<RejectBillDetail> detailList) throws Exception;
	/**
	 * 通过拆分的销售退货通知单 生成销售退货入库单。。
	 * @param rejectBill
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public String addSaleRejectEnterByRejectSplit(RejectBill rejectBill,List<RejectBillDetail> detailList) throws Exception;
	/**
	 * 通过来源单据编号 删除销售退货入库单信息
	 * @param sourceid			销售退货通知单编号
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 6, 2013
	 */
	public boolean deleteSaleRejectEnterBySourceid(String sourceid) throws Exception;
	/**
	 * 根据销售退货通知单编号和回单编号 反审删除销售退货入库单
	 * @param rejectid
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年2月6日
	 */
	public boolean deleteSaleRejectEnterByRejectidAndReceiptid(String rejectid,String receiptid) throws Exception;
	/**
	 * 通过销售回单生成销售退货入库单
	 * @param receipt
	 * @param list
	 * @return				生成成功返回销售退货入库单编号
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 18, 2013
	 */
	public String addSaleRejectEnterByReceipt(Receipt receipt,List<ReceiptDetail> list) throws Exception;
	/**
	 * 通过销售回单 验收销售退货入库单
	 * @param receipt
	 * @param receiptDetailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public boolean updateSaleRejectEnterCheckByReceipt(Receipt receipt,List<ReceiptDetail> receiptDetailList) throws Exception;
	 /**
	  *更新销售退货入库单 备注
	  * @author lin_xx
	  * @date 2016/9/28
	  */
	public boolean updateSaleRejectEnter(SaleRejectEnter saleRejectEnter) throws Exception;
	/**
	 * 通过销售回单 取消验收销售退货入库单
	 * @param receipt
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public boolean updateSaleRejectEnterCheckByReceiptOpen(Receipt receipt) throws Exception;
	/**
	 * 更新销售退货入库单明细中相关的来源单据编号 来源单据明细编号（用来核销明细使用）
	 * @param oldbillid
	 * @param oldbilldetailid
	 * @param newbillid
	 * @param newbilldetailid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 9, 2014
	 */
	public boolean updateSaleRejectEnterDetailIDs(String oldbillid,String oldbilldetailid,String newbillid,String newbilldetailid) throws Exception;
	/**
	 * 根据销售退货通知单编号 更新销售退货入库单明细信息
	 * @param rejectBillDetail
	 * @return
	 * @author chenwei 
	 * @date Jan 10, 2014
	 */
	public boolean updateSaleRejectEnterDetailByRejectDetail(RejectBillDetail rejectBillDetail) throws Exception;
	/**
	 * 根据销售退货通知单明细编号 删除销售退货入库单明细
	 * @param rejectBillDetail
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public boolean deleteSaleRejectEnterDetailByRejectDetail(RejectBillDetail rejectBillDetail) throws Exception;
	/**
	 * 更新销售退货入库单 单据金额
	 * @param rejectid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public boolean updateSaleRejectEnterAmount(String rejectid) throws Exception;
	/**
	 * 根据销售退货通知单编号 获取销售退货入库单列表
	 * @param rejectid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 15, 2014
	 */
	public List getSaleRejectEnterListByRejectid(String rejectid) throws Exception;
	/**
	 * 根据销售发货通知单 验收销售退货入库单
	 * @param rejectBill
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 15, 2014
	 */
	public boolean updateSaleRejectEnterCheckByReject(RejectBill rejectBill) throws Exception;
	/**
	 * 根据销售退货通知单编号 取消退货入库单验收状态
	 * @param rejectid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 18, 2014
	 */
	public boolean updateSaleRejectEnterCheckCancelByReject(String rejectid) throws Exception;
	/**
	 * 根据回单更新发货单应收日期
	 * @param receipt
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 22, 2014
	 */
	public boolean updateSaleOutDueformdateByReceipt(Receipt receipt) throws Exception;
	/**
	 * 根据订单编号 删除销售发货单
	 * @param orderid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public boolean deleteSaleOutByOrderid(String orderid) throws Exception;

    /**
     * 根据商品编码和生产日期获取批次号 截止日期等相关数据
     * @param goodsid
     * @param produceddate
     * @return
     * @throws Exception
     */
    public Map getBatchno(String goodsid,String produceddate) throws Exception;

    /**
     * 根据截止日期获取批次号与生产日期
     * @param goodsid           商品编码
     * @param deadline          截止日期
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年11月4日
     */
    public Map getBatchnoByDeadline(String goodsid,String deadline) throws Exception;

	/**
	 * 根据发货通知单编号判断发货单是否已生成大单发货，true已生成，false未生成
	 * @param dispatchbillid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-18
	 */
	public boolean doCheckSaleoutIsbigsaleout(String dispatchbillid)throws Exception;

	/**
	 * 根据销售订单编号判断发货单是否已生成大单发货，true已生成，false未生成
	 * @param orderid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-19
	 */
	public boolean doCheckSaleoutIsbigsaleoutByOrderid(String orderid)throws Exception;
}

