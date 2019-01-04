/**
 * @(#)PurchaseForStorageServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-6-14 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.impl.ext;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.purchase.model.*;
import com.hd.agent.purchase.service.ext.IPurchaseForStorageService;
import com.hd.agent.purchase.service.impl.BasePurchaseServiceImpl;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.model.PurchaseEnterDetail;
import com.hd.agent.storage.model.PurchaseRejectOutDetail;
import com.hd.agent.storage.service.IStorageForPurchaseService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 采购与仓库 接口 实现 
 * 
 * @author zhanghonghui
 */
public class PurchaseForStorageServiceImpl extends BasePurchaseServiceImpl implements IPurchaseForStorageService {
	/**
	 * 回写采购订单
	 * @param buyOrderid
	 * @param list 
	 * @param isdelete 是否删除
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-29
	 */
	private boolean updateBuyOrderDetailWriteBack(String buyOrderid,List<PurchaseEnterDetail> list,boolean isdelete) throws Exception {
		boolean flag=false;
		List<BuyOrderDetail> buyDetailList=showBasePureBuyOrderDetailListByOrderId(buyOrderid);
		PurchaseEnterDetail purchaseEnterDetail=null;
		BuyOrderDetail upboDetail=null;
		boolean isupall=true;
		for(BuyOrderDetail item : buyDetailList){
			purchaseEnterDetail=getPurchaseEnterDetailByBuyDetailId(item.getId().toString(), list);
			upboDetail=new BuyOrderDetail();
			if(isdelete){	//删除时
				if(null!=purchaseEnterDetail){
					upboDetail.setId(item.getId());
					upboDetail.setUnstockunitnum(item.getUnstockunitnum().add(purchaseEnterDetail.getUnitnum()));
					upboDetail.setUnstockauxnum(item.getAuxnum().add(purchaseEnterDetail.getAuxnum()));
					upboDetail.setUnstocktaxamount(item.getUnstocktaxamount().add(purchaseEnterDetail.getTaxamount()));
					upboDetail.setUnstocknotaxamount(item.getNotaxamount().add(purchaseEnterDetail.getNotaxamount()));
					upboDetail.setStockunitnum(item.getStockunitnum().subtract(purchaseEnterDetail.getUnitnum()));
					if(BigDecimal.ZERO.compareTo(upboDetail.getStockunitnum())>0){
						upboDetail.setStockunitnum(BigDecimal.ZERO);
					}
					upboDetail.setStockauxnum(item.getStockauxnum().subtract(purchaseEnterDetail.getAuxnum()));
					if(BigDecimal.ZERO.compareTo(upboDetail.getStockauxnum())>0){
						upboDetail.setStockauxnum(BigDecimal.ZERO);
					}
					upboDetail.setStocktaxamount(item.getStocktaxamount().subtract(purchaseEnterDetail.getTaxamount()));
					if(BigDecimal.ZERO.compareTo(upboDetail.getStocktaxamount())>0){
						upboDetail.setStocktaxamount(BigDecimal.ZERO);
					}
					upboDetail.setStocknotaxamount(item.getStocknotaxamount().subtract(purchaseEnterDetail.getNotaxamount()));
					if(BigDecimal.ZERO.compareTo(upboDetail.getStocknotaxamount())>0){
						upboDetail.setStocknotaxamount(BigDecimal.ZERO);
					}
					flag = ( buyOrderMapper.updateBuyOrderDetailWriteBack(upboDetail)>0) || flag;
				}
			}
			else if(null==purchaseEnterDetail){
				upboDetail.setId(item.getId());
				upboDetail.setStockunitnum(BigDecimal.ZERO);
				upboDetail.setStockauxnum(BigDecimal.ZERO);
				upboDetail.setStocktaxamount(BigDecimal.ZERO);
				upboDetail.setStocknotaxamount(BigDecimal.ZERO);
				upboDetail.setUnstockunitnum(item.getUnitnum());
				upboDetail.setUnstockauxnum(item.getAuxnum());
				upboDetail.setUnstocktaxamount(item.getTaxamount());
				upboDetail.setUnstocknotaxamount(item.getNotaxamount());
				upboDetail.setRealstocknum(BigDecimal.ZERO);
				upboDetail.setRealunstocknum(BigDecimal.ZERO);
				isupall=false;
				flag = ( buyOrderMapper.updateBuyOrderDetailWriteBack(upboDetail)>0) || flag;
			}
			else if(null!=purchaseEnterDetail){
				//有的话，更新
				upboDetail=new BuyOrderDetail();
				upboDetail.setId(item.getId());
				upboDetail.setStockunitnum(purchaseEnterDetail.getUnitnum());
				upboDetail.setStockauxnum(purchaseEnterDetail.getAuxnum());
				upboDetail.setStocktaxamount(purchaseEnterDetail.getTaxamount());
				upboDetail.setStocknotaxamount(purchaseEnterDetail.getNotaxamount());
				upboDetail.setUnstockunitnum(item.getUnitnum().subtract(purchaseEnterDetail.getUnitnum()));
				if(BigDecimal.ZERO.compareTo(upboDetail.getUnstockunitnum())>0){
					upboDetail.setUnstockunitnum(BigDecimal.ZERO);
				}
				if(BigDecimal.ZERO.compareTo(upboDetail.getUnstockunitnum())!=0){
					isupall=false;
				}
				upboDetail.setUnstockauxnum(item.getAuxnum().subtract(purchaseEnterDetail.getAuxnum()));
				if(BigDecimal.ZERO.compareTo(upboDetail.getUnstockauxnum())>0){
					upboDetail.setUnstockauxnum(BigDecimal.ZERO);
				}
				upboDetail.setUnstocktaxamount(item.getTaxamount().subtract(purchaseEnterDetail.getTaxamount()));
				if(BigDecimal.ZERO.compareTo(upboDetail.getUnstocktaxamount())>0){
					upboDetail.setUnstocktaxamount(BigDecimal.ZERO);
				}
				upboDetail.setUnstocknotaxamount(item.getNotaxamount().subtract(purchaseEnterDetail.getNotaxamount()));
				if(BigDecimal.ZERO.compareTo(upboDetail.getUnstocknotaxamount())>0){
					upboDetail.setUnstocknotaxamount(BigDecimal.ZERO);
				}
				flag = ( buyOrderMapper.updateBuyOrderDetailWriteBack(upboDetail)>0) || flag;
			}
		}

		BuyOrder buyOrder=buyOrderMapper.getBuyOrder(buyOrderid);
		if(null==list || list.size()==0){	//数据为空情况下
			if("4".equals(buyOrder.getStatus())){	//把关闭的打开
				updateBuyOrderAuditStatus(buyOrderid);
			}
			if(!"0".equals(buyOrder.getIsrefer())){
				updateBuyOrderUnReferFlag(buyOrderid);
			}
		}else{
			if("1".equals(buyOrder.getOrderappend())){	//追加订单情况下
				if(isdelete){	//删除时
					if("4".equals(buyOrder.getStatus())){	//把关闭的打开
						updateBuyOrderAuditStatus(buyOrderid);
					}
					if(!"0".equals(buyOrder.getIsrefer())){
						updateBuyOrderUnReferFlag(buyOrderid);
					}
				}else{
					if(isupall){
						if(!"1".equals(buyOrder.getIsrefer())){
							updateBuyOrderReferFlag(buyOrderid);
						}
					}else{
						if("4".equals(buyOrder.getStatus())){	//把关闭的打开
							updateBuyOrderAuditStatus(buyOrderid);
						}
						if(!"0".equals(buyOrder.getIsrefer())){
							updateBuyOrderUnReferFlag(buyOrderid);
						}
					}
				}
			}else{	//不追加情况下
				if(isdelete){	//删除时
					if("4".equals(buyOrder.getStatus())){	//把关闭的打开
						updateBuyOrderAuditStatus(buyOrderid);
					}
					if(!"0".equals(buyOrder.getIsrefer())){
						updateBuyOrderUnReferFlag(buyOrderid);
					}
				}else{	//不删除时
					if(!"1".equals(buyOrder.getIsrefer())){
						updateBuyOrderReferFlag(buyOrderid);
					}					
				}
			}
		}
		
		
		
		return flag;
	}
	@Override
	public boolean updateBuyOrderDetailWriteBack(String buyOrderid,List<PurchaseEnterDetail> list) throws Exception{
		return updateBuyOrderDetailWriteBack(buyOrderid, list, false);
	}
	@Override
	public boolean updateBuyOrderDetailDeleteWriteBack(String buyOrderid,List<PurchaseEnterDetail> list) throws Exception{
		return updateBuyOrderDetailWriteBack(buyOrderid, list, true);
	}
	@Override
	public boolean updateBuyOrderDetailAuditWriteBack(String buyOrderid,List<PurchaseEnterDetail> list) throws Exception{
		boolean flag=false;
		BuyOrder buyOrder=buyOrderMapper.getBuyOrder(buyOrderid);
		List<BuyOrderDetail> buyDetailList=showBasePureBuyOrderDetailListByOrderId(buyOrderid);
		PurchaseEnterDetail purchaseEnterDetail=null;
		BuyOrderDetail upboDetail=null;
		boolean isclose=false;
		boolean isopen=false;
		for(BuyOrderDetail item : buyDetailList){
			purchaseEnterDetail=getPurchaseEnterDetailByBuyDetailId(item.getId().toString(), list);
			if(null!=purchaseEnterDetail){
				upboDetail=new BuyOrderDetail();
				upboDetail.setId(item.getId());
				upboDetail.setRealstocknum(purchaseEnterDetail.getUnitnum());
				upboDetail.setRealunstocknum(item.getUnitnum().subtract(purchaseEnterDetail.getUnitnum()));
				if(BigDecimal.ZERO.compareTo(upboDetail.getRealunstocknum())>0){
					upboDetail.setRealunstocknum(BigDecimal.ZERO);
				}
				
			}else{
				upboDetail=new BuyOrderDetail();
				upboDetail.setId(item.getId());
				upboDetail.setRealstocknum(BigDecimal.ZERO);
				upboDetail.setRealunstocknum(item.getUnitnum());
			}
			flag = (buyOrderMapper.updateBuyOrderDetailWriteBack(upboDetail)>0) || flag;
			
			if("1".equals(buyOrder.getOrderappend())){
				if(BigDecimal.ZERO.compareTo(upboDetail.getRealunstocknum())==0){
					isclose=true;
				}else{
					isopen=true;
				}				
			}
		}
		if(flag){
			//判断关闭订单状态
			if((!"1".equals(buyOrder.getOrderappend()) || (isclose && !isopen )) ){
				updateBuyOrderCloseStatus(buyOrderid);
			}else{
				updateBuyOrderAuditStatus(buyOrderid);
			}
		}
		return flag;		
	}
	
	@Override
	public boolean updateBuyOrderOpen(String id) throws Exception {
		return updateBuyOrderAuditStatus(id);
	}
	@Override
	public boolean getCanBuyOrderCreateNextAfterEnterAudit(String buyOrderid) throws Exception{
		boolean isok=false;
		BuyOrder buyOrder=buyOrderMapper.getBuyOrder(buyOrderid);
		if(null!=buyOrder && "1".equals(buyOrder.getOrderappend())){
			List<BuyOrderDetail> buyDetailList=showBasePureBuyOrderDetailListByOrderId(buyOrderid);
			for(BuyOrderDetail item : buyDetailList){
				if(item.getUnstockunitnum().compareTo(BigDecimal.ZERO)!=0){
					isok=true;
				}
			}
		}
		return isok;
	}

	private PurchaseEnterDetail getPurchaseEnterDetailByBuyDetailId(String detailid,List<PurchaseEnterDetail> list){
		if(null==list || list.size()==0){
			return null;
		}
		PurchaseEnterDetail purchaseEnterDetail=null;
		for(PurchaseEnterDetail item: list){
			if(StringUtils.isNotEmpty(detailid) && detailid.equals(item.getBuyorderdetailid())){
				purchaseEnterDetail=item;
				break;
			}
		}
		return purchaseEnterDetail;
	}

	private boolean updateBuyOrderReferFlag(String id) throws Exception{
		BuyOrder buyOrder=new BuyOrder();
		buyOrder.setIsrefer("1");
		buyOrder.setId(id);
		return buyOrderMapper.updateBuyOrderStatus(buyOrder)>0;
	}	
	private boolean updateBuyOrderUnReferFlag(String id) throws Exception{
		BuyOrder buyOrder=new BuyOrder();
		buyOrder.setIsrefer("0");
		buyOrder.setId(id);
		return buyOrderMapper.updateBuyOrderStatus(buyOrder)>0;
	}
	private boolean updateBuyOrderAuditStatus(String id) throws Exception{
		BuyOrder buyOrder=new BuyOrder();
		buyOrder.setStatus("3");
		buyOrder.setId(id);
		return buyOrderMapper.updateBuyOrderStatus(buyOrder)>0;
	}
	private boolean updateBuyOrderCloseStatus(String id) throws Exception{
		BuyOrder buyOrder=new BuyOrder();
		buyOrder.setStatus("4");
		buyOrder.setId(id);
		return buyOrderMapper.updateBuyOrderStatus(buyOrder)>0;
	}
	@Override
	public BuyOrder showBuyOrderAndDetail(String id) throws Exception{
		BuyOrder buyOrder= showBasePureBuyOrderAndDetail(id);
		List<BuyOrderDetail> oldList=buyOrder.getBuyOrderDetailList();
		List<BuyOrderDetail> list=new ArrayList<BuyOrderDetail>();
		if(null!=oldList && oldList.size()>0){
			for(BuyOrderDetail item : oldList){
				if(null!=item.getUnstockunitnum() && item.getUnstockunitnum().compareTo(BigDecimal.ZERO)!=0){
					list.add(item);
				}
			}
		}
		buyOrder.setBuyOrderDetailList(list);
		return buyOrder;
	}
	
	@Override
	public boolean updateArrivalOrderReferFlag(String id) throws Exception {
		if(null==id || "".equals(id.trim())){
			return false;
		}
		return updateBaseArrivalOrderRefer(id, "1");
	}

	@Override
	public boolean updateArrivalOrderUnReferFlag(String id) throws Exception {
		if(null==id || "".equals(id.trim())){
			return false;
		}
		return updateBaseArrivalOrderRefer(id, "0");
	}
	@Override
	public String addArrivalOrder(
			PurchaseEnter purchaseEnter,
			List<PurchaseEnterDetail> purchaseEnterDetails) throws Exception {
		boolean flag=false;

		ArrivalOrder arrivalOrder=new ArrivalOrder();
		SysUser sysUser=getSysUser();
		arrivalOrder.setId(getBillNumber(arrivalOrder, "t_purchase_arrivalorder"));
		arrivalOrder.setAdduserid(purchaseEnter.getAdduserid());
		arrivalOrder.setAddusername(purchaseEnter.getAddusername());
		arrivalOrder.setAddtime(new Date());
		arrivalOrder.setAdddeptid(purchaseEnter.getAdddeptid());
		arrivalOrder.setAdddeptname(purchaseEnter.getAdddeptname());
		arrivalOrder.setBusinessdate(purchaseEnter.getBusinessdate());
		arrivalOrder.setBuydeptid(purchaseEnter.getBuydeptid());
		arrivalOrder.setBuyuserid(purchaseEnter.getBuyuserid());
		arrivalOrder.setField01(purchaseEnter.getField01());
		arrivalOrder.setField02(purchaseEnter.getField02());
		arrivalOrder.setField03(purchaseEnter.getField03());
		arrivalOrder.setField04(purchaseEnter.getField04());
		arrivalOrder.setField05(purchaseEnter.getField05());
		arrivalOrder.setField06(purchaseEnter.getField06());
		arrivalOrder.setField07(purchaseEnter.getField07());
		arrivalOrder.setField08(purchaseEnter.getField08());
		arrivalOrder.setHandlerid(purchaseEnter.getHandlerid());
		arrivalOrder.setPrinttimes(0);
		arrivalOrder.setRemark(purchaseEnter.getRemark());
		arrivalOrder.setSource("1");
		arrivalOrder.setStatus("2");
		arrivalOrder.setSupplierid(purchaseEnter.getSupplierid());
		arrivalOrder.setBillno(purchaseEnter.getId());
		arrivalOrder.setPaytype(purchaseEnter.getPaytype());
		arrivalOrder.setSettletype(purchaseEnter.getSettletype());
		arrivalOrder.setStorageid(purchaseEnter.getStorageid());
		if(!"1".equals(purchaseEnter.getSourcetype())){
			if(StringUtils.isNotEmpty(purchaseEnter.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(purchaseEnter.getSupplierid());
				if(null!=buySupplier){
					arrivalOrder.setPaytype(buySupplier.getPaytype());
					arrivalOrder.setSettletype(buySupplier.getSettletype());
					arrivalOrder.setHandlerid(buySupplier.getContact());
				}
			}
		}
		
		int addnum = 0;
		if(null != purchaseEnterDetails){
			ArrivalOrderDetail arrivalOrderDetail=null;
			for(PurchaseEnterDetail item : purchaseEnterDetails){
				if(null==item || null==item.getUnitnum() || BigDecimal.ZERO.compareTo(item.getUnitnum())==0){
					continue;
				}
				arrivalOrderDetail=new ArrivalOrderDetail();
				arrivalOrderDetail.setOrderid(arrivalOrder.getId());
				arrivalOrderDetail.setArrivedate(item.getArrivedate());
				arrivalOrderDetail.setAuxnum(item.getAuxnum());
				arrivalOrderDetail.setAuxnumdetail(item.getAuxnumdetail());
				arrivalOrderDetail.setAuxunitid(item.getAuxunitid());
				arrivalOrderDetail.setAuxunitname(item.getAuxunitname());
				arrivalOrderDetail.setAuxremainder(item.getAuxremainder());
				arrivalOrderDetail.setTotalbox(item.getTotalbox());
				arrivalOrderDetail.setBilldetailno(item.getId().toString());
				arrivalOrderDetail.setBillno(item.getPurchaseenterid());
				arrivalOrderDetail.setBuyorderid(item.getBuyorderid());
				arrivalOrderDetail.setBrandid(item.getBrandid());
				arrivalOrderDetail.setField01(item.getField01());
				arrivalOrderDetail.setField02(item.getField02());
				arrivalOrderDetail.setField03(item.getField03());
				arrivalOrderDetail.setField04(item.getField04());
				arrivalOrderDetail.setField05(item.getField05());
				arrivalOrderDetail.setField06(item.getField06());
				arrivalOrderDetail.setField07(item.getField07());
				arrivalOrderDetail.setField08(item.getField08());
				arrivalOrderDetail.setGoodsid(item.getGoodsid());
				arrivalOrderDetail.setNotaxamount(item.getNotaxamount());
				arrivalOrderDetail.setNotaxprice(item.getNotaxprice());
				arrivalOrderDetail.setRemark(item.getRemark());
				arrivalOrderDetail.setTax(item.getTax());
				arrivalOrderDetail.setTaxamount(item.getTaxamount());
				arrivalOrderDetail.setTaxprice(item.getTaxprice());
				arrivalOrderDetail.setTaxtype(item.getTaxtype());
				arrivalOrderDetail.setUnitid(item.getUnitid());
				arrivalOrderDetail.setUnitname(item.getUnitname());
				arrivalOrderDetail.setUnitnum(item.getUnitnum());
				arrivalOrderDetail.setBatchno(item.getBatchno());
				arrivalOrderDetail.setProduceddate(item.getProduceddate());
				arrivalOrderDetail.setDeadline(item.getDeadline());

                arrivalOrderDetail.setAddcostprice(item.getAddcostprice());

				int i = arrivalOrderMapper.insertArrivalOrderDetail(arrivalOrderDetail);
				if(i>0){
					addnum++;
				}
			}
		}
		if(addnum>0){
			flag=arrivalOrderMapper.insertArrivalOrder(arrivalOrder)>0;
		}
		if(flag){
			return arrivalOrder.getId();
		}else{
			return "";
		}
	}

    /**
     * 审核进货单
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean auditArrivalOrder(String id) throws Exception {
        Map map=new HashMap();
        boolean flag=false;
        ArrivalOrder arrivalOrder=arrivalOrderMapper.getArrivalOrder(id);
        if("2".equals(arrivalOrder.getStatus())){
            map.put("orderid", id);
            int acount=arrivalOrderMapper.getArrivalOrderDetailCountBy(map);
            map.clear();
            if(acount==0){
                return flag;
            }
            ArrivalOrder upaOrder=new ArrivalOrder();
            SysUser sysUser=getSysUser();
            upaOrder.setId(id);
            upaOrder.setBusinessdate(getAuditBusinessdate(arrivalOrder.getBusinessdate()));
            upaOrder.setStatus("3");
            upaOrder.setAudituserid(sysUser.getUserid());
            upaOrder.setAuditusername(sysUser.getName());
            upaOrder.setAudittime(new Date());
            flag=arrivalOrderMapper.updateArrivalOrderStatus(upaOrder)>0;
            if(flag){
                List<ArrivalOrderDetail> detailList=arrivalOrderMapper.getArrivalOrderDetailListByOrderid(arrivalOrder.getId());
                if("1".equals(arrivalOrder.getSource())||"2".equals(arrivalOrder.getSource()) ){
                    if(StringUtils.isNotEmpty(arrivalOrder.getBillno())){
                        IStorageForPurchaseService storageForPurchaseService = (IStorageForPurchaseService) SpringContextUtils.getBean("storageForPurchaseService");
                        storageForPurchaseService.updatePurchaseEnterClose(arrivalOrder.getBillno());
                        storageForPurchaseService.updatePurchaseEnterDetailPrice(arrivalOrder,detailList);
                    }
                }
                if(null!=detailList && detailList.size()>0){
                    for(ArrivalOrderDetail item : detailList){

                        ArrivalOrderDetail aodupDetail=new ArrivalOrderDetail();
                        aodupDetail.setId(item.getId());
                        aodupDetail.setUninvoicenum(item.getUnitnum());
                        aodupDetail.setInvoicenum(BigDecimal.ZERO);
                        aodupDetail.setUninvoicetaxamount(item.getTaxamount());
                        aodupDetail.setInvoicetaxamount(BigDecimal.ZERO);
                        arrivalOrderMapper.updateArrivalOrderDetailWriteBack(aodupDetail);
                    }
                }
            }
        }
        return flag;
    }

    @Override
	public boolean deleteArrivalOrderAndDetailByBillno(String billno) throws Exception{
		boolean flag = true;
		ArrivalOrder arrivalOrder=arrivalOrderMapper.getArrivalOrderByBillno(billno);
		if(null==arrivalOrder){
			return flag;
		}
		if(!"1".equals(arrivalOrder.getStatus()) && !"2".equals(arrivalOrder.getStatus())){
			flag=false;
		}
		if(flag){
			arrivalOrderMapper.deleteArrivalOrder(arrivalOrder.getId());
			arrivalOrderMapper.deleteArrivalOrderDetailByOrderid(arrivalOrder.getId());
		}
		return flag;
	}
	
	@Override
	public boolean updateReturnOrderWriteBack(String returnOrderid,List<PurchaseRejectOutDetail> list) throws Exception{
		boolean flag=false;
		if(list==null || list.size()==0){
			return flag;
		}
		List<ReturnOrderDetail> orderDetalList=showBasePureReturnOrderDetailListByOrderId(returnOrderid);
		PurchaseRejectOutDetail rejectOutDetail=null;
		ReturnOrderDetail upOrderDetail=null;
		for(ReturnOrderDetail item : orderDetalList){
			rejectOutDetail=getPurchaseRejectOutDetailByReturnDetailId(item.getId().toString(), list);
			upOrderDetail=new ReturnOrderDetail();
			upOrderDetail.setId(item.getId());
			if(null==rejectOutDetail){
				upOrderDetail.setReturnleftunitnum(BigDecimal.ZERO);
				upOrderDetail.setReturnleftauxnum(BigDecimal.ZERO);
				upOrderDetail.setReturnleftaxamount(BigDecimal.ZERO);
				upOrderDetail.setReturnlefnotaxamount(BigDecimal.ZERO);
				upOrderDetail.setUnreturnunitnum(BigDecimal.ZERO);
				upOrderDetail.setUnreturnauxnum(BigDecimal.ZERO);
				upOrderDetail.setUnreturntaxamount(BigDecimal.ZERO);
				upOrderDetail.setUnreturnnotaxamount(BigDecimal.ZERO);
			}else{
                upOrderDetail.setAddcostprice(rejectOutDetail.getAddcostprice());
				upOrderDetail.setUnitnum(rejectOutDetail.getUnitnum());
				upOrderDetail.setAuxnum(rejectOutDetail.getAuxnum());
				upOrderDetail.setTaxamount(rejectOutDetail.getTaxamount());
				upOrderDetail.setNotaxamount(rejectOutDetail.getNotaxamount());
				upOrderDetail.setTax(rejectOutDetail.getTax());
				upOrderDetail.setAuxnumdetail(rejectOutDetail.getAuxnumdetail());
				upOrderDetail.setAuxremainder(rejectOutDetail.getAuxremainder());
				upOrderDetail.setTotalbox(rejectOutDetail.getTotalbox());
				
				upOrderDetail.setReturnleftunitnum(rejectOutDetail.getUnitnum());
				upOrderDetail.setReturnleftauxnum(rejectOutDetail.getAuxnum());
				upOrderDetail.setReturnleftaxamount(rejectOutDetail.getTaxamount());
				upOrderDetail.setReturnlefnotaxamount(rejectOutDetail.getNotaxamount());
				upOrderDetail.setUnreturnunitnum(BigDecimal.ZERO);
				upOrderDetail.setUnreturnauxnum(BigDecimal.ZERO);
				upOrderDetail.setUnreturntaxamount(BigDecimal.ZERO);
				upOrderDetail.setUnreturnnotaxamount(BigDecimal.ZERO);
				
				upOrderDetail.setUninvoicetaxamount(rejectOutDetail.getTaxamount());
				upOrderDetail.setUninvoicenotaxamount(rejectOutDetail.getNotaxamount());
				upOrderDetail.setUninvoicenum(rejectOutDetail.getUnitnum());
			}
			flag=(returnOrderMapper.updateReturnOrderDetailWriteBack(upOrderDetail)>0) || flag;
		}
		if(null != orderDetalList && orderDetalList.size()>0 && flag){
			updateReturnOrderIsinvoiceStatus(returnOrderid);
		}
		return flag;
	}
	
	@Override
	public boolean updateReturnOrderOppauditWriteBack(String returnOrderid,List<PurchaseRejectOutDetail> list) throws Exception{
		boolean flag=false;
		if(list==null || list.size()==0){
			return flag;
		}
		List<ReturnOrderDetail> orderDetalList=showBasePureReturnOrderDetailListByOrderId(returnOrderid);
		PurchaseRejectOutDetail rejectOutDetail=null;
		ReturnOrderDetail upOrderDetail=null;
		for(ReturnOrderDetail item : orderDetalList){
			rejectOutDetail=getPurchaseRejectOutDetailByReturnDetailId(item.getId().toString(), list);
			upOrderDetail=new ReturnOrderDetail();
			upOrderDetail.setId(item.getId());
			if(null==rejectOutDetail){
				upOrderDetail.setReturnleftunitnum(BigDecimal.ZERO);
				upOrderDetail.setReturnleftauxnum(BigDecimal.ZERO);
				upOrderDetail.setReturnleftaxamount(BigDecimal.ZERO);
				upOrderDetail.setReturnlefnotaxamount(BigDecimal.ZERO);
				upOrderDetail.setUnreturnunitnum(BigDecimal.ZERO);
				upOrderDetail.setUnreturnauxnum(BigDecimal.ZERO);
				upOrderDetail.setUnreturntaxamount(BigDecimal.ZERO);
				upOrderDetail.setUnreturnnotaxamount(BigDecimal.ZERO);
			}else{
				upOrderDetail.setUnitnum(rejectOutDetail.getUnitnum());
				upOrderDetail.setAuxnum(rejectOutDetail.getAuxnum());
				upOrderDetail.setTaxamount(rejectOutDetail.getTaxamount());
				upOrderDetail.setNotaxamount(rejectOutDetail.getNotaxamount());
				upOrderDetail.setTax(rejectOutDetail.getTax());
				upOrderDetail.setAuxnumdetail(rejectOutDetail.getAuxnumdetail());
				upOrderDetail.setAuxremainder(rejectOutDetail.getAuxremainder());
				upOrderDetail.setTotalbox(rejectOutDetail.getTotalbox());
				
				upOrderDetail.setReturnleftunitnum(BigDecimal.ZERO);
				upOrderDetail.setReturnleftauxnum(BigDecimal.ZERO);
				upOrderDetail.setReturnleftaxamount(BigDecimal.ZERO);
				upOrderDetail.setReturnlefnotaxamount(BigDecimal.ZERO);
				upOrderDetail.setUnreturnunitnum(BigDecimal.ZERO);
				upOrderDetail.setUnreturnauxnum(BigDecimal.ZERO);
				upOrderDetail.setUnreturntaxamount(BigDecimal.ZERO);
				upOrderDetail.setUnreturnnotaxamount(BigDecimal.ZERO);
				
				upOrderDetail.setUninvoicetaxamount(BigDecimal.ZERO);
				upOrderDetail.setUninvoicenotaxamount(BigDecimal.ZERO);
				upOrderDetail.setUninvoicenum(BigDecimal.ZERO);
			}
			flag=(returnOrderMapper.updateReturnOrderDetailWriteBack(upOrderDetail)>0) || flag;
		}
		if(null != orderDetalList && orderDetalList.size()>0 && flag){
			ReturnOrder order=new ReturnOrder();
			order.setIsinvoice("0");
			order.setId(returnOrderid);
			return returnOrderMapper.updateReturnOrderStatus(order)>0;
		}
		return flag;
	}

	/**
	 * 根据采购退货通知单明细编号，查找出对应的退货出库单
	 * @param detailid
	 * @param list
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-21
	 */
	private PurchaseRejectOutDetail getPurchaseRejectOutDetailByReturnDetailId(String detailid,List<PurchaseRejectOutDetail> list){
		if(null==list || list.size()==0){
			return null;
		}
		PurchaseRejectOutDetail rejectOutDetail=null;
		for(PurchaseRejectOutDetail item: list){
			if(StringUtils.isNotEmpty(detailid) && detailid.equals(item.getBilldetailno())){
				rejectOutDetail=item;
				break;
			}
		}
		return rejectOutDetail;
	}
	
	@Override
	public boolean updateReturnOrderReferFlag(String id) throws Exception {
		if(null==id || "".equals(id.trim())){
			return false;
		}
		return updateBaseReturnOrderRefer(id.trim(), "1");
	}
	@Override
	public boolean updateReturnOrderUnReferFlag(String id) throws Exception {
		if(null==id || "".equals(id.trim())){
			return false;
		}
		return updateBaseReturnOrderRefer(id.trim(), "0");
	}
	@Override
	public boolean updateReturnOrderOpen(String id) throws Exception{
		ReturnOrder returnOrder=returnOrderMapper.getReturnOrder(id);
		if(null!=returnOrder){
			ReturnOrder upOrder=new ReturnOrder();
			if("3".equals(returnOrder.getStatus())){
				upOrder.setStatus("2");
			}
			if("1".equals(returnOrder.getIsrefer())){
				upOrder.setIsrefer("0");
			}else{
				upOrder.setIsrefer("1");
			}
			upOrder.setId(id);
			return returnOrderMapper.updateReturnOrderStatus(upOrder)>0;
		}else{
			return false;
		}
	}
	/**
	 * 更新采购退货通知单开票状态为可开票
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-21
	 */
	private boolean updateReturnOrderIsinvoiceStatus(String id) throws Exception{
		ReturnOrder order=new ReturnOrder();
		order.setIsinvoice("4");
		order.setId(id);
		return returnOrderMapper.updateReturnOrderStatus(order)>0;
	}
	@Override
	public ReturnOrder showReturnOrderAndDetail(String id) throws Exception{
		return showBasePureReturnOrderAndDetail(id);
	}
	@Override
	public boolean updateReturnOrderCkstatusBy(String id,String ckstatus) throws Exception{
		if(null==id || "".equals(id.trim())){
			return false;
		}
		ReturnOrder order =new ReturnOrder();
		order.setCkstatus(ckstatus);
		order.setId(id);
		return returnOrderMapper.updateReturnOrderStatus(order)>0;
	}
	
	@Override
	public boolean updateReturnOrderCheck(ReturnOrder returnOrder) throws Exception{
		if(null==returnOrder || StringUtils.isEmpty(returnOrder.getId())){
			return false;
		}
		ReturnOrder upReturnOrder=new ReturnOrder();
		upReturnOrder.setId(returnOrder.getId());
		upReturnOrder.setIscheck(returnOrder.getIscheck());
		upReturnOrder.setCheckuserid(returnOrder.getCheckuserid());
		upReturnOrder.setCheckusername(returnOrder.getCheckusername());
		upReturnOrder.setCheckdate(returnOrder.getCheckdate());
		upReturnOrder.setIsinvoice(returnOrder.getIsinvoice());
		return returnOrderMapper.updateReturnOrderALL(upReturnOrder)>0;
	}
	
	public String addCreateArrivalOrderByDeliveryEnter(PurchaseEnter purchaseEnter,List<PurchaseEnterDetail> purchaseEnterDetails) throws Exception {
		boolean flag=false;
		ArrivalOrder arrivalOrder=new ArrivalOrder();
		arrivalOrder.setId(getBillNumber(arrivalOrder, "t_purchase_arrivalorder"));
		arrivalOrder.setBusinessdate(purchaseEnter.getBusinessdate());
		//11.4修改状态为保存的
		arrivalOrder.setStatus("2");
		arrivalOrder.setRemark(purchaseEnter.getRemark());
		//添加人相关信息
		arrivalOrder.setAdduserid(purchaseEnter.getAdduserid());
		arrivalOrder.setAddusername(purchaseEnter.getAddusername());
		arrivalOrder.setAdddeptid(purchaseEnter.getAdddeptid());
		arrivalOrder.setAdddeptname(purchaseEnter.getAdddeptname());
		arrivalOrder.setAddtime(new Date());
		//修改人相关信息
		arrivalOrder.setModifyuserid(purchaseEnter.getModifyuserid());
		arrivalOrder.setModifyusername(purchaseEnter.getModifyusername());
		arrivalOrder.setModifytime(new Date());
		//审核人相关信息
//		arrivalOrder.setAudittime(new Date());
//		arrivalOrder.setAudituserid(purchaseEnter.getAudituserid());
//		arrivalOrder.setAuditusername(purchaseEnter.getAuditusername());
		//中之人相关信息
//		arrivalOrder.setStoptime(new Date());
//		arrivalOrder.setStopuserid(purchaseEnter.getStopuserid());
//		arrivalOrder.setStopusername(purchaseEnter.getStopusername());
		//关闭时间
//		arrivalOrder.setClosetime(purchaseEnter.getClosetime());
		
		arrivalOrder.setSupplierid(purchaseEnter.getSupplierid());
		BuySupplier supplier=getSupplierInfoById(purchaseEnter.getSupplierid());
		String supplierName="";
		if(supplier!=null){
			supplierName=supplier.getName();
		}
		arrivalOrder.setSuppliername(supplierName);
		//来源类型 代配送订单
		arrivalOrder.setSource("2");
		arrivalOrder.setBillno(purchaseEnter.getId());
		arrivalOrder.setStorageid(purchaseEnter.getStorageid());
		arrivalOrder.setIsrefer("0");
		//状态,开票中
		arrivalOrder.setIsinvoice("3");
		//采购订单编号(代配送订单编号)
		arrivalOrder.setBuyorderid(purchaseEnter.getSourceid());
		//采购部门,采购人员
		arrivalOrder.setBuyuserid(purchaseEnter.getBuyuserid());
		arrivalOrder.setBuydeptid(purchaseEnter.getBuydeptid());
		//仓库
		arrivalOrder.setStorageid(purchaseEnter.getStorageid());
		
		int addnum = 0;
		if(null != purchaseEnterDetails){
			ArrivalOrderDetail arrivalOrderDetail=null;
			for(PurchaseEnterDetail item : purchaseEnterDetails){
				if(null==item || null==item.getUnitnum() || BigDecimal.ZERO.compareTo(item.getUnitnum())==0){
					continue;
				}
				arrivalOrderDetail=new ArrivalOrderDetail();
				arrivalOrderDetail.setOrderid(arrivalOrder.getId());
				arrivalOrderDetail.setGoodsid(item.getGoodsid());
				arrivalOrderDetail.setBrandid(item.getBrandid());
				
				arrivalOrderDetail.setUnitid(item.getUnitid());
				arrivalOrderDetail.setUnitname(item.getUnitname());
				arrivalOrderDetail.setUnitnum(item.getUnitnum());
				
				arrivalOrderDetail.setAuxunitid(item.getAuxunitid());
				arrivalOrderDetail.setAuxunitname(item.getAuxunitname());
				arrivalOrderDetail.setAuxnum(item.getAuxnum());
				arrivalOrderDetail.setAuxnumdetail(item.getAuxnumdetail());
				arrivalOrderDetail.setAuxremainder(item.getAuxremainder());
				
				arrivalOrderDetail.setTotalbox(item.getTotalbox());
				arrivalOrderDetail.setTaxprice(item.getTaxprice());
				arrivalOrderDetail.setTaxamount(item.getTaxamount());
				arrivalOrderDetail.setTaxtype(item.getTaxtype());
				arrivalOrderDetail.setNotaxprice(item.getNotaxprice());
				arrivalOrderDetail.setNotaxamount(item.getNotaxamount());
				arrivalOrderDetail.setTax(item.getTax());
				arrivalOrderDetail.setRemark(item.getRemark());
                if(null!=item.getAddcostprice() && item.getAddcostprice().compareTo(BigDecimal.ZERO)==1){
                    arrivalOrderDetail.setAddcostprice(item.getAddcostprice());
                }else{
                    arrivalOrderDetail.setAddcostprice(item.getTaxprice());
                }
				arrivalOrderDetail.setSeq(item.getSeq());
				//来源单据编号
				arrivalOrderDetail.setBillno(purchaseEnter.getId());
				//来源单位明细编号
				arrivalOrderDetail.setBilldetailno(item.getId().toString());
				//采购订单编号(代配送订单编号)
				arrivalOrderDetail.setBuyorderid(purchaseEnter.getSourceid());
				
				
				int i = arrivalOrderMapper.insertArrivalOrderDetail(arrivalOrderDetail);
				if(i>0){
					addnum++;
					ArrivalOrderDetail aodupDetail=new ArrivalOrderDetail();
					aodupDetail.setId(item.getId());
					aodupDetail.setUninvoicenum(item.getUnitnum());
					aodupDetail.setInvoicenum(BigDecimal.ZERO);
					aodupDetail.setUninvoicetaxamount(item.getTaxamount());
					aodupDetail.setInvoicetaxamount(BigDecimal.ZERO);
					arrivalOrderMapper.updateArrivalOrderDetailWriteBack(aodupDetail);
				}
			}
			
		}
		if(addnum>0){
			flag=arrivalOrderMapper.insertArrivalOrder(arrivalOrder)>0;
		}
		
		if(flag){
			return arrivalOrder.getId();
		}else{
			return "";
		}
	}
}

