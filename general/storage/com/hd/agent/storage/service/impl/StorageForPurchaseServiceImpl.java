/**
 * @(#)StorageForPurchaseServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 10, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.purchase.model.*;
import com.hd.agent.purchase.service.ext.IPurchaseForStorageService;
import com.hd.agent.storage.dao.PurchaseEnterMapper;
import com.hd.agent.storage.dao.PurchaseRejectOutMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IPurchaseEnterService;
import com.hd.agent.storage.service.IStorageForPurchaseService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 提供给采购模块的service实现类
 * @author chenwei
 */
public class StorageForPurchaseServiceImpl extends BaseStorageServiceImpl
		implements IStorageForPurchaseService {
	/**
	 * 采购入库单dao
	 */
	private PurchaseEnterMapper purchaseEnterMapper;
	/**
	 * 采购退货出库单dao
	 */
	private PurchaseRejectOutMapper purchaseRejectOutMapper;
	
	private IPurchaseEnterService purchaseEnterService;
	
	private IPurchaseForStorageService purchaseForStorageService;
	/**
	 * 采购入库单service
	 * @return
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public PurchaseEnterMapper getPurchaseEnterMapper() {
		return purchaseEnterMapper;
	}

	public void setPurchaseEnterMapper(PurchaseEnterMapper purchaseEnterMapper) {
		this.purchaseEnterMapper = purchaseEnterMapper;
	}

	public IPurchaseEnterService getPurchaseEnterService() {
		return purchaseEnterService;
	}

	public void setPurchaseEnterService(IPurchaseEnterService purchaseEnterService) {
		this.purchaseEnterService = purchaseEnterService;
	}
	
	public PurchaseRejectOutMapper getPurchaseRejectOutMapper() {
		return purchaseRejectOutMapper;
	}

	public void setPurchaseRejectOutMapper(
			PurchaseRejectOutMapper purchaseRejectOutMapper) {
		this.purchaseRejectOutMapper = purchaseRejectOutMapper;
	}
	
	public IPurchaseForStorageService getPurchaseForStorageService() {
		return purchaseForStorageService;
	}

	public void setPurchaseForStorageService(
			IPurchaseForStorageService purchaseForStorageService) {
		this.purchaseForStorageService = purchaseForStorageService;
	}

	@Override
	public String addPurchaseEnterByBuyOrder(BuyOrder buyOrder,
			List<BuyOrderDetail> detailList) throws Exception {
		String purchaseEnterId = purchaseEnterService.addPurchaseEnterByBuyOrder(buyOrder, detailList);
		return purchaseEnterId;
	}

	@Override
	public boolean deletePurchaseEnterBySourceid(String sourceid)
			throws Exception {
		boolean flag = true;
		List<PurchaseEnter> list = purchaseEnterMapper.getPurchaseEnterListBySourceid(sourceid);
		if(list.size()==0){
			return flag;
		}
		//判断采购入库单是否未审核
		for(PurchaseEnter purchaseEnter : list){
			if(!"1".equals(purchaseEnter.getStatus()) && !"2".equals(purchaseEnter.getStatus())){
				flag = false;
				break;
			}
		}
		if(flag){
			for(PurchaseEnter purchaseEnter : list){
				//只有暂存与保存状态才能删除
				if("1".equals(purchaseEnter.getStatus())){
					purchaseEnterMapper.deletePurchaseEnterDetail(purchaseEnter.getId());
					int i = purchaseEnterMapper.deletePurchaseEnter(purchaseEnter.getId());
				}else if("2".equals(purchaseEnter.getStatus())){
					//保存状态的采购入库单 删除前需要回滚在途量
					//修改前 采购入库单为保存状态时 回滚在途量
					List<PurchaseEnterDetail> oldList = purchaseEnterMapper.getPurchaseEnterDetailList(purchaseEnter.getId());
					for(PurchaseEnterDetail purchaseEnterDetail : oldList){
						//回滚在途量
						rollBackStorageSummaryTransitnum(purchaseEnterDetail.getStorageid(), purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getUnitnum());
					}
					purchaseEnterMapper.deletePurchaseEnterDetail(purchaseEnter.getId());
					int i = purchaseEnterMapper.deletePurchaseEnter(purchaseEnter.getId());
				}
			}
		}
		return flag;
	}

	@Override
	public boolean updatePurchaseEnterRefer(String isrefer, String id)
			throws Exception {
		int i = purchaseEnterMapper.updatePurchaseEnterRefer(isrefer, id);
		return i>0;
	}

	@Override
	public boolean updatePurchaseEnterClose(String id) throws Exception {
		int i = purchaseEnterMapper.closePurchaseEnter(id);
		return i>0;
	}

	@Override
	public boolean updatePurchaseEnterOpen(String id) throws Exception {
		int i = purchaseEnterMapper.openPurchaseEnter(id);
		return i>0;
	}
	
	@Override
	public Map getPurchaseEnterByID(String id) throws Exception {
		PurchaseEnter purchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(id);
		List list = purchaseEnterMapper.getPurchaseEnterDetailList(id);
		Map map = new HashMap();
		map.put("purchaseEnter", purchaseEnter);
		map.put("detailList", list);
		return map;
	}

	@Override
	public boolean updatePurchaseEnterDetailPrice(ArrivalOrder arrivalOrder,List<ArrivalOrderDetail> list)
			throws Exception {
        PurchaseEnter purchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(arrivalOrder.getBillno());
		for(ArrivalOrderDetail arrivalOrderDetail : list){
			PurchaseEnterDetail purchaseEnterDetail = purchaseEnterMapper.getPurchaseEnterDetail(arrivalOrderDetail.getBilldetailno());
			if(null!=purchaseEnterDetail){
				boolean updateGoodsFlag = false;
                BigDecimal addcostprice = arrivalOrderDetail.getAddcostprice();
				purchaseEnterDetail.setTaxprice(arrivalOrderDetail.getTaxprice());
				purchaseEnterDetail.setNotaxprice(arrivalOrderDetail.getNotaxprice());
				purchaseEnterDetail.setTaxamount(arrivalOrderDetail.getTaxamount());
				purchaseEnterDetail.setNotaxamount(arrivalOrderDetail.getNotaxamount());
				purchaseEnterDetail.setTaxtype(arrivalOrderDetail.getTaxtype());
				purchaseEnterDetail.setTax(arrivalOrderDetail.getTax());
				purchaseEnterDetail.setRemark(arrivalOrderDetail.getRemark());
                //代配送不重新计算成本价
                if(!"2".equals(purchaseEnter.getSourcetype())){
                    //计算成本价 判断计算后成本价是否符合上下限条件
                    //符合条件则直接计算 不符合条件则把该部分成本金额差异摊分到下次入库计算
                    boolean isUpdateCostprice = false;
                    //获取计算后的成本价
                    BigDecimal countCostprice = getGoodsPirceByDiffPriceAdd(purchaseEnter.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),arrivalOrderDetail.getTaxprice(),addcostprice);
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseEnterDetail.getGoodsid());
                    if (null != goodsInfo) {
                        isUpdateCostprice = isPurcahseUpdateCostpriceInLimit(purchaseEnter.getStorageid(),goodsInfo,countCostprice,arrivalOrderDetail.getTaxprice());
                    }
                    if(isUpdateCostprice){
                        //更新最新档案库存价
                        updateGoodsPirceByDiffPriceAdd(arrivalOrder.getId(),arrivalOrderDetail.getId()+"",purchaseEnter.getStorageid(),
                                purchaseEnterDetail.getGoodsid(),
                                purchaseEnterDetail.getUnitnum(),
                                arrivalOrderDetail.getTaxprice(),
                                addcostprice,true,true);
                    }else{
                        updateStorageGoodsPriceByDiffPriceAdd(purchaseEnter.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),arrivalOrderDetail.getTaxprice(),addcostprice);
                        //未计算到成本中的采购入库差额
                        BigDecimal costDiffAmount = arrivalOrderDetail.getUnitnum().multiply(arrivalOrderDetail.getTaxprice().subtract(addcostprice));
                        addCostDiffAmountShare("1",arrivalOrder.getId(),arrivalOrderDetail.getId().toString(),arrivalOrder.getStorageid(),arrivalOrderDetail.getGoodsid(),costDiffAmount,"审核进货单，产生差额");
                    }
                }
				purchaseEnterMapper.updatePurchaseEnterDetail(purchaseEnterDetail);
			}
		}
		return true;
	}

    /**
     * 进货单反审 价格金额修改后 回写采购入库单
     *
     * @param list
     * @return
     * @throws Exception
     */
    @Override
    public boolean updatePurchaseEnterDetailPriceByOppaudit(ArrivalOrder arrivalOrder,List<ArrivalOrderDetail> list) throws Exception {
        PurchaseEnter purchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(arrivalOrder.getBillno());
        for(ArrivalOrderDetail arrivalOrderDetail : list){
            PurchaseEnterDetail purchaseEnterDetail = purchaseEnterMapper.getPurchaseEnterDetail(arrivalOrderDetail.getBilldetailno());
            if(null!=purchaseEnterDetail){
                boolean updateGoodsFlag = false;
                BigDecimal addcostprice = arrivalOrderDetail.getAddcostprice();
                //回滚更新档案库存价
                if(arrivalOrderDetail.getTaxprice().compareTo(arrivalOrderDetail.getAddcostprice())!=0){
                    updateGoodsFlag = true;
                }
                purchaseEnterDetail.setTaxprice(arrivalOrderDetail.getTaxprice());
                purchaseEnterDetail.setNotaxprice(arrivalOrderDetail.getNotaxprice());
                purchaseEnterDetail.setTaxamount(arrivalOrderDetail.getTaxamount());
                purchaseEnterDetail.setNotaxamount(arrivalOrderDetail.getNotaxamount());
                purchaseEnterDetail.setTaxtype(arrivalOrderDetail.getTaxtype());
                purchaseEnterDetail.setTax(arrivalOrderDetail.getTax());
                purchaseEnterDetail.setRemark(arrivalOrderDetail.getRemark());
                //更新最新档案库存价
                if(updateGoodsFlag){
                    //代配送不重新计算成本价
                    if(!"2".equals(purchaseEnter.getSourcetype())) {
                        //进货单有产生成本差额的 且未分摊 则删除
                        //已分摊的 则重新计算成本
                        boolean hasCostDiff = hasCostDiffAmountNoShare("1",arrivalOrder.getId(),arrivalOrderDetail.getId().toString(),arrivalOrderDetail.getGoodsid());
                        if(hasCostDiff){
                            delteCostDiffAmountShare("1",arrivalOrder.getId(),arrivalOrderDetail.getId().toString(),arrivalOrderDetail.getGoodsid());
                            //更新仓库商品成本价
                            updateStoragePriceByDiffPriceSubtract(purchaseEnter.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),arrivalOrderDetail.getTaxprice(),addcostprice);
                        }else{
                            //计算成本价 判断计算后成本价是否符合上下限条件
                            //符合条件则直接计算 不符合条件则把该部分成本金额差异摊分到下次入库计算
                            boolean isUpdateCostprice = false;
                            //获取计算后的成本价
                            BigDecimal countCostprice = getGoodsPirceByDiffPriceSubtract(purchaseEnter.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),arrivalOrderDetail.getTaxprice(),addcostprice);
                            GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseEnterDetail.getGoodsid());
                            if (null != goodsInfo) {
                                //上下限比较价格 取本次采购的价格 如果本次价格为0取最新采购价
                                BigDecimal newBuyprice = arrivalOrderDetail.getTaxprice();
                                if(newBuyprice.compareTo(BigDecimal.ZERO)!=1){
                                    newBuyprice = goodsInfo.getNewbuyprice();
                                }
                                isUpdateCostprice = isPurcahseUpdateCostpriceInLimit(purchaseEnter.getStorageid(),goodsInfo,countCostprice,newBuyprice);
                            }
                            if(isUpdateCostprice){
                                updateGoodsPirceByDiffPriceSubtract(purchaseEnter.getId(),purchaseEnterDetail.getId()+"",purchaseEnter.getStorageid(),
                                        purchaseEnterDetail.getGoodsid(),
                                        purchaseEnterDetail.getUnitnum(),
                                        arrivalOrderDetail.getTaxprice(),
                                        addcostprice, true, true);
                            }else{
                                //更新仓库商品成本价
                                updateStoragePriceByDiffPriceSubtract(purchaseEnter.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),arrivalOrderDetail.getTaxprice(),addcostprice);
                                //未计算到成本中的采购入库差额
                                BigDecimal costDiffAmount = arrivalOrderDetail.getUnitnum().multiply(arrivalOrderDetail.getTaxprice().subtract(addcostprice)).negate();
                                addCostDiffAmountShare("1",arrivalOrder.getId(),arrivalOrderDetail.getId().toString(),arrivalOrder.getStorageid(),arrivalOrderDetail.getGoodsid(),costDiffAmount,"反审进货单造成差额");
                            }
                        }

                    }
                }
                purchaseEnterMapper.updatePurchaseEnterDetail(purchaseEnterDetail);
            }
        }
        return true;
    }

    @Override
	public boolean updatePurchaseEnterDetailIsinvoice(String billid, String detailid,
			String isinvoice) throws Exception {
		int i = purchaseEnterMapper.updatePurchaseEnterDetailIsinvoice(billid, detailid, isinvoice);
		return i>0;
	}

	@Override
	public boolean updatePurchaseEnterDetailIswriteoff(String billid,
			String detailid, String iswriteoff) throws Exception {
		int i = purchaseEnterMapper.updatePurchaseEnterDetailIswriteoff(billid, detailid, iswriteoff);
		return i>0;
	}
	@Override
	public Map addPurchaseRejectOut(ReturnOrder returnOrder,List<ReturnOrderDetail> list) throws Exception {
		PurchaseRejectOut purchaseRejectOut = new PurchaseRejectOut();
        Map map = new HashMap();
		if (isAutoCreate("t_storage_purchasereject_out")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(purchaseRejectOut, "t_storage_purchasereject_out");
			purchaseRejectOut.setId(id);
		}else{
			purchaseRejectOut.setId("CGCK"+CommonUtils.getDataNumberSendsWithRand());
		} 
		boolean flag = true;
        String idarr = "";
		//验证库存量是否足够
        List<ReturnOrderDetail> newList = new ArrayList<ReturnOrderDetail>();
		for(ReturnOrderDetail returnOrderDetail : list) {
			StorageSummaryBatch storageSummaryBatch = null;
			GoodsInfo goodsInfo = getGoodsInfoByID(returnOrderDetail.getGoodsid());
			if((null!=goodsInfo && "1".equals(goodsInfo.getIsbatch()))
					||StringUtils.isNotEmpty(returnOrderDetail.getSummarybatchid())){
				storageSummaryBatch = getStorageSummaryBatchById(returnOrderDetail.getSummarybatchid());
			}
			if(null==storageSummaryBatch){
				storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(returnOrder.getStorageid(), returnOrderDetail.getGoodsid());
			}
            if (null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(returnOrderDetail.getUnitnum()) != -1) {
            	returnOrderDetail.setSummarybatchid(storageSummaryBatch.getId());
            	newList.add(returnOrderDetail);
            }else{
                flag = false;
                idarr = returnOrderDetail.getGoodsid() + "";
            }
        }
        if(flag){
            for(ReturnOrderDetail returnOrderDetail : newList){
                PurchaseRejectOutDetail purchaseRejectOutDetail = new PurchaseRejectOutDetail();
                purchaseRejectOutDetail.setOrderid(purchaseRejectOut.getId());
                purchaseRejectOutDetail.setBilldetailno(returnOrderDetail.getId()+"");
                purchaseRejectOutDetail.setBillno(returnOrder.getId());
                purchaseRejectOutDetail.setGoodsid(returnOrderDetail.getGoodsid());
                GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
                if(null!=goodsInfo){
                    purchaseRejectOutDetail.setBrandid(goodsInfo.getBrand());
                }
                purchaseRejectOutDetail.setSummarybatchid(returnOrderDetail.getSummarybatchid());
                purchaseRejectOutDetail.setStorageid(returnOrder.getStorageid());
                purchaseRejectOutDetail.setStoragelocationid(returnOrderDetail.getStoragelocationid());
                purchaseRejectOutDetail.setBatchno(returnOrderDetail.getBatchno());
                purchaseRejectOutDetail.setProduceddate(returnOrderDetail.getProduceddate());
                purchaseRejectOutDetail.setDeadline(returnOrderDetail.getDeadline());

                purchaseRejectOutDetail.setUnitid(returnOrderDetail.getUnitid());
                purchaseRejectOutDetail.setUnitname(returnOrderDetail.getUnitname());
                purchaseRejectOutDetail.setUnitnum(returnOrderDetail.getUnitnum());

                purchaseRejectOutDetail.setAuxnum(returnOrderDetail.getAuxnum());
                purchaseRejectOutDetail.setAuxremainder(returnOrderDetail.getAuxremainder());
                purchaseRejectOutDetail.setAuxunitid(returnOrderDetail.getAuxunitid());
                purchaseRejectOutDetail.setAuxunitname(returnOrderDetail.getAuxunitname());
                purchaseRejectOutDetail.setAuxnumdetail(returnOrderDetail.getAuxnumdetail());
                purchaseRejectOutDetail.setTotalbox(returnOrderDetail.getTotalbox());

                purchaseRejectOutDetail.setTaxprice(returnOrderDetail.getTaxprice());
                purchaseRejectOutDetail.setTaxamount(returnOrderDetail.getTaxamount());
                purchaseRejectOutDetail.setNotaxprice(returnOrderDetail.getNotaxprice());
                purchaseRejectOutDetail.setNotaxamount(returnOrderDetail.getNotaxamount());
                purchaseRejectOutDetail.setTaxtype(returnOrderDetail.getTaxtype());
                purchaseRejectOutDetail.setTax(returnOrderDetail.getTax());

                StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
                if(null!=storageSummaryBatch){
                    //更新待发量
                    updateStorageSummaryWaitnum(storageSummaryBatch.getId(), purchaseRejectOutDetail.getUnitnum());
                    purchaseRejectOutDetail.setOrderid(purchaseRejectOut.getId());
                    purchaseRejectOutMapper.addPurchaseRejectOutDetail(purchaseRejectOutDetail);
                }
            }
            purchaseRejectOut.setAdddeptid(returnOrder.getAdddeptid());
            purchaseRejectOut.setAdddeptname(returnOrder.getAdddeptname());
            purchaseRejectOut.setAdduserid(returnOrder.getAdduserid());
            purchaseRejectOut.setAddusername(returnOrder.getAddusername());
            purchaseRejectOut.setStatus("2");

            purchaseRejectOut.setBusinessdate(returnOrder.getBusinessdate());
            purchaseRejectOut.setSupplierid(returnOrder.getSupplierid());
            purchaseRejectOut.setHandlerid(returnOrder.getHandlerid());
            purchaseRejectOut.setBuydeptid(returnOrder.getBuydeptid());
            purchaseRejectOut.setBuyuserid(returnOrder.getBuyuserid());
            purchaseRejectOut.setSettletype(returnOrder.getSettletype());
            purchaseRejectOut.setPaytype(returnOrder.getPaytype());
            purchaseRejectOut.setStorageid(returnOrder.getStorageid());
            purchaseRejectOut.setSourceid(returnOrder.getId());
            purchaseRejectOut.setSourcetype("1");
            int j = purchaseRejectOutMapper.addPurchaseRejectOut(purchaseRejectOut);
            if(j>0){
                purchaseForStorageService.updateReturnOrderReferFlag(returnOrder.getId());
                map.put("id", purchaseRejectOut.getId());
            }else{
                flag = false;
            }
        }
        if(idarr != ""){
            map.put("idarr",idarr);
        }
        map.put("flag",flag);
        return map ;
	}

	@Override
	public boolean deletePurchaseRejectOutBySourceid(String sourceid)
			throws Exception {
		PurchaseRejectOut purchaseRejectOut = purchaseRejectOutMapper.getPurchaseRejectOutBySourceid(sourceid);
		boolean flag = false;
		if(null==purchaseRejectOut){
			return true;
		}else{
			//只有暂存和保存状态才能删除
			if("1".equals(purchaseRejectOut.getStatus()) || "2".equals(purchaseRejectOut.getStatus())){
				//当状态为保存时
				if("2".equals(purchaseRejectOut.getStatus())){
					List<PurchaseRejectOutDetail> detailList = purchaseRejectOutMapper.getPurchaseRejectOutByOrderid(purchaseRejectOut.getId());
					//删除明细 回滚待发量
					for(PurchaseRejectOutDetail purchaseRejectOutDetail : detailList){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
						if(null!=storageSummaryBatch){
							//回滚待发量
							rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), purchaseRejectOutDetail.getUnitnum());
						}
					}
				}
				purchaseRejectOutMapper.deletePurchaseRejectOutDetailByOrderid(purchaseRejectOut.getId());
				int i = purchaseRejectOutMapper.deletePurchaseRejectOut(purchaseRejectOut.getId());
				flag = i>0;
				if(flag && "1".equals(purchaseRejectOut.getSourcetype())){
					purchaseForStorageService.updateReturnOrderUnReferFlag(sourceid);
				}
			}
		}
		return flag;
	}

	@Override
	public Map addPurchaseRejectOutByRefer(String id) throws Exception {
		ReturnOrder returnOrder = purchaseForStorageService.showReturnOrderAndDetail(id);
		if(null!=returnOrder){
			Map map  = addPurchaseRejectOut(returnOrder, returnOrder.getReturnOrderDetailList());
			return map;
		}else{
			return null;
		}
	}

	@Override
	public boolean updatePurchaseRejectDetailIsinvoice(String billid,
			String detailid, String isinvoice) throws Exception {
		int i = purchaseRejectOutMapper.updatePurchaseRejectDetailIsinvoice(billid, detailid, isinvoice);
		return i>0;
	}

	@Override
	public boolean updatePurchaseRejectDetailIswriteoff(String billid,
			String detailid, String iswriteoff) throws Exception {
		int i = purchaseRejectOutMapper.updatePurchaseRejectDetailIswriteoff(billid, detailid, iswriteoff);
		return i>0;
	}

	@Override
	public StorageSummary getStorageSummarySumByGoodsidAndStorageid(
			String goodsid, String storageid) throws Exception {
		StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummaryInfoByGoodsidAndStorageid(goodsid, storageid);
		return storageSummary;
	}

	@Override
	public StorageSummaryBatch getStorageSummaryBatchBySummarybatchid(
			String summarybatchid) throws Exception {
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(summarybatchid);
		return storageSummaryBatch;
	}
	@Override
	public StorageSummaryBatch getStorageSummaryBatchNoBatch(String storageid,
			String goodsid) throws Exception {
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageid, goodsid);
		return storageSummaryBatch;
	}
    @Override
    public boolean updatePurchaseEnterDetailUncancel(String invoiceid) throws Exception {
        boolean flag = purchaseEnterMapper.updatePurchaseEnterDetailUncancel(invoiceid) > 0;
        return flag;
    }

    @Override
    public boolean updatePurchaseRejectDetailUncancel(String invoiceid) throws Exception {
        boolean flag = purchaseRejectOutMapper.updatePurchaseRejectDetailUncancel(invoiceid) > 0;
        return flag;
    }
    @Override
    public List getPuchaseEnterUnAuditIDList(String buyorderid) throws Exception{
    	List list =purchaseEnterMapper.getPuchaseEnterUnAuditIDList(buyorderid);
    	return list;
    }
    @Override
    public boolean updatePurchaseRejectOutDetailByReturnCheck(ReturnOrderDetail returnOrderDetail,boolean isUpdateCostprice) throws Exception{
    	PurchaseRejectOutDetail oldDetail=purchaseRejectOutMapper.getPurchaseRejectOutDetailBySource(returnOrderDetail.getOrderid(),returnOrderDetail.getId().toString());
    	if(null==oldDetail || null == oldDetail.getId()){
    		return false;
    	}
    	PurchaseRejectOutDetail purchaseRejectOutDetail=new PurchaseRejectOutDetail();
        purchaseRejectOutDetail.setStorageid(oldDetail.getStorageid());
    	purchaseRejectOutDetail.setGoodsid(oldDetail.getGoodsid());
		purchaseRejectOutDetail.setBilldetailno(returnOrderDetail.getId()+"");
		purchaseRejectOutDetail.setBillno(returnOrderDetail.getOrderid());

		purchaseRejectOutDetail.setUnitid(returnOrderDetail.getUnitid());
		purchaseRejectOutDetail.setUnitname(returnOrderDetail.getUnitname());
		purchaseRejectOutDetail.setUnitnum(returnOrderDetail.getUnitnum());
		
		purchaseRejectOutDetail.setAuxnum(returnOrderDetail.getAuxnum());
		purchaseRejectOutDetail.setAuxremainder(returnOrderDetail.getAuxremainder());
		purchaseRejectOutDetail.setAuxunitid(returnOrderDetail.getAuxunitid());
		purchaseRejectOutDetail.setAuxunitname(returnOrderDetail.getAuxunitname());
		purchaseRejectOutDetail.setAuxnumdetail(returnOrderDetail.getAuxnumdetail());
		purchaseRejectOutDetail.setTotalbox(returnOrderDetail.getTotalbox());
		
		purchaseRejectOutDetail.setTaxprice(returnOrderDetail.getTaxprice());
		purchaseRejectOutDetail.setTaxamount(returnOrderDetail.getTaxamount());
		purchaseRejectOutDetail.setNotaxprice(returnOrderDetail.getNotaxprice());
		purchaseRejectOutDetail.setNotaxamount(returnOrderDetail.getNotaxamount());
		purchaseRejectOutDetail.setTaxtype(returnOrderDetail.getTaxtype());
		purchaseRejectOutDetail.setTax(returnOrderDetail.getTax());

        purchaseRejectOutDetail.setAddcostprice(oldDetail.getAddcostprice());
		Map paramMap=new HashMap();
		paramMap.put("orderDetail", purchaseRejectOutDetail);
		paramMap.put("id", oldDetail.getId()+"");
		boolean flag= purchaseRejectOutMapper.updatePurchaseRejectOutDetailBy(paramMap)>0;
		if(flag && isUpdateCostprice){
            GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
            //计算成本价 判断计算后成本价是否符合上下限条件
            //符合条件则直接计算 不符合条件则把该部分成本金额差异摊分到下次入库计算
            boolean isChangeUpdateCostprice = false;
            //获取计算后的成本价
            BigDecimal countCostprice = getGoodsPirceByDiffPriceSubtract(purchaseRejectOutDetail.getStorageid(),purchaseRejectOutDetail.getGoodsid(),purchaseRejectOutDetail.getUnitnum(),purchaseRejectOutDetail.getTaxprice(),purchaseRejectOutDetail.getAddcostprice());
            if (null != goodsInfo && countCostprice.compareTo(BigDecimal.ZERO)!=0) {
                //判断计算后成本价和当前成本价是否在范围内
                isChangeUpdateCostprice = isPurcahseUpdateCostpriceInLimit(purchaseRejectOutDetail.getStorageid(),goodsInfo,countCostprice,purchaseRejectOutDetail.getTaxprice());
            }
            if(isChangeUpdateCostprice){
                updateGoodsPirceByDiffPriceSubtract(oldDetail.getOrderid(),oldDetail.getId().toString(),purchaseRejectOutDetail.getStorageid(), purchaseRejectOutDetail.getGoodsid(), purchaseRejectOutDetail.getUnitnum(), purchaseRejectOutDetail.getTaxprice(), purchaseRejectOutDetail.getAddcostprice(),false,true);
            }else{
                //调整仓库商品成本价
                updateStoragePriceByDiffPriceSubtract(purchaseRejectOutDetail.getStorageid(), purchaseRejectOutDetail.getGoodsid(), purchaseRejectOutDetail.getUnitnum(), purchaseRejectOutDetail.getTaxprice(), purchaseRejectOutDetail.getAddcostprice());
                //未计算到成本中的采购入库差额
                BigDecimal costDiffAmount = purchaseRejectOutDetail.getUnitnum().multiply(purchaseRejectOutDetail.getTaxprice().subtract(purchaseRejectOutDetail.getAddcostprice())).negate();
                addCostDiffAmountShare("2",oldDetail.getOrderid(),oldDetail.getId().toString(),purchaseRejectOutDetail.getStorageid(),purchaseRejectOutDetail.getGoodsid(),costDiffAmount,"审核退货单，产生差额");
            }
		}
		return flag;
    }
    @Override
    public boolean updatePurchaseRejectOutBy(Map map) throws Exception{
    	return purchaseRejectOutMapper.updatePurchaseRejectOutBy(map)>0;
    }
    @Override
    public void updatePurchaseRejectOutDetailByReturnCheckCancel(String sourceid) throws Exception{
    	Map paramMap=new HashMap();
    	paramMap.put("billno", sourceid);
    	List<PurchaseRejectOutDetail> list=purchaseRejectOutMapper.getPurchaseRejectOutDetailByMap(paramMap);
        PurchaseRejectOut purchaseRejectOut = purchaseRejectOutMapper.getPurchaseRejectOutBySourceid(sourceid);
    	for(PurchaseRejectOutDetail item : list){
            //代配送不计算成本价
    		if(null!=item && !"2".equals(purchaseRejectOut.getSourcetype())){
                //判断该单据有没有产生差额 且未分摊
                //未分摊 直接删除
                //已分摊 则重新计算成本价
                boolean hasCostDiff = hasCostDiffAmountNoShare("2",purchaseRejectOut.getId(),item.getId().toString(),item.getGoodsid());
                if(hasCostDiff){
                    delteCostDiffAmountShare("2",purchaseRejectOut.getId(),item.getId().toString(),item.getGoodsid());
                    //更新仓库商品成本价
                    updateStorageGoodsPriceByDiffPriceAdd(item.getStorageid(), item.getGoodsid(), item.getUnitnum(), item.getTaxprice(), item.getAddcostprice());
                }else{
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(item.getGoodsid());
                    //计算成本价 判断计算后成本价是否符合上下限条件
                    //符合条件则直接计算 不符合条件则把该部分成本金额差异摊分到下次入库计算
                    boolean isChangeUpdateCostprice = false;
                    //获取计算后的成本价
                    BigDecimal countCostprice = getGoodsPirceByDiffPriceAdd(item.getStorageid(),item.getGoodsid(),item.getUnitnum(),item.getTaxprice(),item.getAddcostprice());
                    isChangeUpdateCostprice = isPurcahseUpdateCostpriceInLimit(item.getStorageid(),goodsInfo,countCostprice,item.getTaxprice());
                    if(isChangeUpdateCostprice) {
                        updateGoodsPirceByDiffPriceAdd(purchaseRejectOut.getId(),item.getId()+"",item.getStorageid(), item.getGoodsid(), item.getUnitnum(), item.getTaxprice(), item.getAddcostprice(),false,true);
                    }else{
                        //更新仓库商品成本价
                        updateStorageGoodsPriceByDiffPriceAdd(item.getStorageid(), item.getGoodsid(), item.getUnitnum(), item.getTaxprice(), item.getAddcostprice());
                        //未计算到成本中的采购入库差额
                        BigDecimal costDiffAmount = item.getUnitnum().multiply(item.getTaxprice().subtract(item.getAddcostprice()));
                        addCostDiffAmountShare("2",purchaseRejectOut.getId(),item.getId().toString(),purchaseRejectOut.getStorageid(),item.getGoodsid(),costDiffAmount,"退货单取消验收，产生差额");
                    }
                }
    		}
    	}
    }
    
    
    
    
    @Override
	public boolean addPurchaseRejectOutForJob(ReturnOrder returnOrder,List<ReturnOrderDetail> list) throws Exception {
    	
    	boolean flag=true;
		PurchaseRejectOut purchaseRejectOut = new PurchaseRejectOut();
		if (isAutoCreate("t_storage_purchasereject_out")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(purchaseRejectOut, "t_storage_purchasereject_out");
			purchaseRejectOut.setId(id);
		}else{
			purchaseRejectOut.setId("CGCK"+CommonUtils.getDataNumberSendsWithRand());
		} 
        for(ReturnOrderDetail returnOrderDetail : list){
                PurchaseRejectOutDetail purchaseRejectOutDetail = new PurchaseRejectOutDetail();
                purchaseRejectOutDetail.setOrderid(purchaseRejectOut.getId());
                purchaseRejectOutDetail.setGoodsid(returnOrderDetail.getGoodsid());
                purchaseRejectOutDetail.setStorageid(returnOrder.getStorageid());
                GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
                if(null!=goodsInfo){
                	purchaseRejectOutDetail.setBrandid(goodsInfo.getBrand());
                }
                purchaseRejectOutDetail.setUnitid(returnOrderDetail.getUnitid());
                purchaseRejectOutDetail.setUnitname(returnOrderDetail.getUnitname());
                purchaseRejectOutDetail.setUnitnum(returnOrderDetail.getUnitnum());
                purchaseRejectOutDetail.setAuxunitid(returnOrderDetail.getAuxunitid());
                purchaseRejectOutDetail.setAuxunitname(returnOrderDetail.getAuxunitname());
                purchaseRejectOutDetail.setAuxnum(returnOrderDetail.getAuxnum());
                purchaseRejectOutDetail.setAuxremainder(returnOrderDetail.getAuxremainder());
                purchaseRejectOutDetail.setTotalbox(returnOrderDetail.getTotalbox());
                purchaseRejectOutDetail.setTaxprice(returnOrderDetail.getTaxprice());
                purchaseRejectOutDetail.setTaxamount(returnOrderDetail.getTaxamount());
                purchaseRejectOutDetail.setTaxtype(returnOrderDetail.getTaxtype());
                purchaseRejectOutDetail.setNotaxprice(returnOrderDetail.getNotaxprice());
                purchaseRejectOutDetail.setNotaxamount(returnOrderDetail.getNotaxamount());
                purchaseRejectOutDetail.setTax(returnOrderDetail.getTax());
                purchaseRejectOutDetail.setRemark(returnOrderDetail.getRemark());
                purchaseRejectOutDetail.setBillno(returnOrder.getId());
                purchaseRejectOutDetail.setSeq(returnOrderDetail.getSeq());
                
                purchaseRejectOutDetail.setSummarybatchid(returnOrderDetail.getSummarybatchid());
                purchaseRejectOutDetail.setStoragelocationid(returnOrderDetail.getStoragelocationid());
                purchaseRejectOutDetail.setBatchno(returnOrderDetail.getBatchno());
                purchaseRejectOutDetail.setProduceddate(returnOrderDetail.getProduceddate());
                purchaseRejectOutDetail.setDeadline(returnOrderDetail.getDeadline());
                //来源单据明细编号,验收时会改变成本价 会用到
                purchaseRejectOutDetail.setBilldetailno(returnOrderDetail.getId()+"");
                
                purchaseRejectOutDetail.setAuxnumdetail(returnOrderDetail.getAuxnumdetail());
                if(null!=returnOrderDetail.getAddcostprice() && returnOrderDetail.getAddcostprice().compareTo(BigDecimal.ZERO)==1){
                    purchaseRejectOutDetail.setAddcostprice(returnOrderDetail.getAddcostprice());
                }else{
                    purchaseRejectOutDetail.setAddcostprice(returnOrderDetail.getTaxprice());
                }
                purchaseRejectOutMapper.addPurchaseRejectOutDetail(purchaseRejectOutDetail);
         }
        	purchaseRejectOut.setBusinessdate(returnOrder.getBusinessdate());
        	
        	purchaseRejectOut.setRemark(returnOrder.getRemark());
        	
            purchaseRejectOut.setAdddeptid(returnOrder.getAdddeptid());
            purchaseRejectOut.setAdddeptname(returnOrder.getAdddeptname());
            purchaseRejectOut.setAdduserid(returnOrder.getAdduserid());
            purchaseRejectOut.setAddusername(returnOrder.getAddusername());
            purchaseRejectOut.setAddtime(new Date());
            purchaseRejectOut.setStorageid(returnOrder.getStorageid());
            
            //审核人信息
            purchaseRejectOut.setAudittime(new Date());
            purchaseRejectOut.setAudituserid(returnOrder.getAudituserid());
            purchaseRejectOut.setAuditusername(returnOrder.getAuditusername());
			//修改人信息
            purchaseRejectOut.setModifytime(new Date());
            purchaseRejectOut.setModifyuserid(returnOrder.getModifyuserid());
            purchaseRejectOut.setModifyusername(returnOrder.getModifyusername());
			//终止人
            purchaseRejectOut.setStoptime(new Date());
            purchaseRejectOut.setStopuserid(returnOrder.getStopuserid());
            purchaseRejectOut.setStopusername(returnOrder.getStopusername());
            
            purchaseRejectOut.setSupplierid(returnOrder.getSupplierid());
            purchaseRejectOut.setBuydeptid(returnOrder.getBuydeptid());
            purchaseRejectOut.setBuyuserid(returnOrder.getBuyuserid());
            purchaseRejectOut.setStorageid(returnOrder.getStorageid());
            purchaseRejectOut.setSourcetype("2");
            purchaseRejectOut.setSourceid(returnOrder.getId());
            //状态为审核
        	purchaseRejectOut.setStatus("3");
        	//改为未验收
            purchaseRejectOut.setIscheck("0");
            purchaseRejectOut.setIsrefer("0");
            flag = purchaseRejectOutMapper.addPurchaseRejectOutForDeliveryJob(purchaseRejectOut)>0;
            
        return flag ;
	}

}

