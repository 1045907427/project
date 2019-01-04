/**
 * @(#)PurchaseForAccountServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-7-11 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.impl.ext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.BeginDue;
import com.hd.agent.common.util.CommonUtils;
import com.thoughtworks.xstream.mapper.Mapper;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.account.model.PurchaseInvoiceDetail;
import com.hd.agent.purchase.model.ArrivalOrder;
import com.hd.agent.purchase.model.ArrivalOrderDetail;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.purchase.service.ext.IPurchaseForAccountService;
import com.hd.agent.purchase.service.impl.BasePurchaseServiceImpl;
import com.hd.agent.storage.service.IStorageForPurchaseService;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class PurchaseForAccountServiceImpl extends BasePurchaseServiceImpl implements IPurchaseForAccountService{
	private IStorageForPurchaseService storageForPurchaseService;


	public IStorageForPurchaseService getStorageForPurchaseService() {
		return storageForPurchaseService;
	}

	public void setStorageForPurchaseService(
			IStorageForPurchaseService storageForPurchaseService) {
		this.storageForPurchaseService = storageForPurchaseService;
	}
	@Override
	public boolean updateArrivalOrderReferFlag(String id) throws Exception {
		// TODO Auto-generated method stub
		if(null==id || "".equals(id.trim())){
			return false;
		}
		return updateBaseArrivalOrderRefer(id, "1");
	}

	@Override
	public boolean updateArrivalOrderUnReferFlag(String id) throws Exception {
		// TODO Auto-generated method stub
		if(null==id || "".equals(id.trim())){
			return false;
		}
		return updateBaseArrivalOrderRefer(id, "0");
	}
	@Override
	public boolean updateArrivalOrderClose(String id) throws Exception{
		boolean isupall=true;
		List<ArrivalOrderDetail> list=arrivalOrderMapper.getArrivalOrderDetailListByOrderid(id);
		if(null!=list && list.size()>0){
			for(ArrivalOrderDetail item : list){
				if(!"1".equals(item.getInvoicerefer())){
					isupall=false;
				}
			}			
		}
		if(!isupall){
			return false;
		}
		boolean flag=false;
		ArrivalOrder order=new ArrivalOrder();
		order.setId(id);
		order.setStatus("4");
		flag=arrivalOrderMapper.updateArrivalOrderStatus(order)>0;
		return flag;
	}
	@Override
	public ArrivalOrder showArrivalOrder(String id) throws Exception{
		ArrivalOrder arrivalOrder= showBasePureArrivalOrderAndDetail(id);
		return arrivalOrder;
	}
	
	@Override
	public ArrivalOrder showArrivalOrderAndDetail(String id) throws Exception{
		ArrivalOrder arrivalOrder= showBasePureArrivalOrderAndDetail(id);
		List<ArrivalOrderDetail> list=arrivalOrder.getArrivalOrderDetailList();
		arrivalOrder.setArrivalOrderDetailList(list);
		return arrivalOrder;
	}
	@Override
	public ArrivalOrderDetail showArrivalOrderDetail(String id) throws Exception{
		ArrivalOrderDetail arrivalOrderDetail= arrivalOrderMapper.getArrivalOrderDetail(id);
		return arrivalOrderDetail;
	}
	
	@Override
	public boolean updateArrivalOrderWriteBack(String billno,List<PurchaseInvoiceDetail> list,String canceldate) throws Exception{
		boolean flag=false;
		if(list==null || list.size()==0){
			return flag;
		}
		List<ArrivalOrderDetail> arrivalOrderDetailList=arrivalOrderMapper.getArrivalOrderDetailListByOrderid(billno);
		PurchaseInvoiceDetail purchaseInvoiceDetail=null;
		boolean isclose=true;
		ArrivalOrderDetail upaodDetail=null;
		boolean iswriteoff=false;

		for(ArrivalOrderDetail item : arrivalOrderDetailList){
			iswriteoff=false;
			purchaseInvoiceDetail=getPurchaseInvoiceDetailById(item.getId().toString(), list);
			if(null !=purchaseInvoiceDetail){
				upaodDetail=new ArrivalOrderDetail();
				upaodDetail.setId(item.getId());
				
				upaodDetail.setWriteoffamount(purchaseInvoiceDetail.getTaxamount());
				upaodDetail.setWriteoffnum(purchaseInvoiceDetail.getUnitnum());
								
				if(!"1".equals(item.getIswriteoff())){
					if(item.getUnitnum().compareTo(purchaseInvoiceDetail.getUnitnum())==0){
						upaodDetail.setIswriteoff("1");	//更新核销状态
						iswriteoff=true;
					}
				}else{
					iswriteoff=true;
					upaodDetail.setIswriteoff("1");
				}
				arrivalOrderMapper.updateArrivalOrderDetailWriteBack(upaodDetail);
				
				if(!"1".equals(upaodDetail.getIswriteoff())){
					isclose=false;
				}
				updatePurchaseEnterDetailIswriteoff(item.getBillno(), item.getBilldetailno(), (iswriteoff?"1":"0"));
			}else {
				if(null==item.getWriteoffamount() || 
						item.getUnitnum().compareTo(item.getWriteoffnum())!=0 || 
						BigDecimal.ZERO.compareTo(item.getWriteoffnum())==0){
					isclose=false;
				}
			}
		}
		if(isclose){
			updateArrivalOrderClose(billno);
			arrivalOrderMapper.updateArrivalOrderInvoice("2", canceldate, billno);
		}
		return flag;
	}
	
	private PurchaseInvoiceDetail getPurchaseInvoiceDetailById(String detailid,List<PurchaseInvoiceDetail> list)  throws Exception{
		if(null==list || list.size()==0){
			return null;
		}
		PurchaseInvoiceDetail purchaseInvoiceDetail=null;
		for(PurchaseInvoiceDetail item : list){
			if(StringUtils.isNotEmpty(detailid) && detailid.equals(item.getSourcedetailid())){
				purchaseInvoiceDetail=item;
				break;
			}
		}
		return purchaseInvoiceDetail;
	}
	
	@Override
	public void updateArrivalOrderInvoiceReferWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception{
		List<ArrivalOrderDetail> detailList = arrivalOrderMapper.getArrivalOrderDetailListByOrderid(orderid);
		boolean isRefer=false;
		if(detailList.size() != 0){
			for(ArrivalOrderDetail arrivalOrderDetail : detailList){
				PurchaseInvoiceDetail purchaseInvoiceDetail=null;
				if(null!=invoiceDetailList && invoiceDetailList.size() != 0){
					for(PurchaseInvoiceDetail pidItem : invoiceDetailList){
						if(null != pidItem && 
								StringUtils.isNotEmpty(pidItem.getSourcedetailid()) &&
								pidItem.getSourcedetailid().equals(arrivalOrderDetail.getId().toString())){
							purchaseInvoiceDetail=pidItem;
							break;
						}
					}
				}

				if(null==purchaseInvoiceDetail){
					purchaseInvoiceDetail=new PurchaseInvoiceDetail();
					purchaseInvoiceDetail.setUnitnum(BigDecimal.ZERO);
					purchaseInvoiceDetail.setTaxamount(BigDecimal.ZERO);
					purchaseInvoiceDetail.setNotaxamount(BigDecimal.ZERO);
				}
				if(BigDecimal.ZERO.compareTo(purchaseInvoiceDetail.getUnitnum())<0){
					isRefer=true;
				}
				//未开票数量，用于发票添加使用				
				if(purchaseInvoiceDetail.getUnitnum().compareTo(arrivalOrderDetail.getUnitnum()) != 0){
					arrivalOrderDetail.setInvoicerefer("0");
					arrivalOrderDetail.setUninvoicenum(arrivalOrderDetail.getUnitnum().subtract(purchaseInvoiceDetail.getUnitnum()));
				}
				else{
					arrivalOrderDetail.setInvoicerefer("1");
					//20150729 旧代码
					//arrivalOrderDetail.setUninvoicenum(BigDecimal.ZERO);
					//20150729 新代码，发票添加完后，未开票数量等于发票的数量
					arrivalOrderDetail.setUninvoicenum(purchaseInvoiceDetail.getUnitnum());
				}
				
				if(arrivalOrderDetail.getUninvoicenum().compareTo(BigDecimal.ZERO) != 0){
					//未开票含税金额 = 未开票数量*含税单价
					arrivalOrderDetail.setUninvoicetaxamount(arrivalOrderDetail.getUninvoicenum().multiply(arrivalOrderDetail.getTaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//未开票无税金额 = 未开票数量*无税单价
					arrivalOrderDetail.setUninvoicenotaxamount(arrivalOrderDetail.getUninvoicenum().multiply(arrivalOrderDetail.getNotaxprice()).setScale(6,BigDecimal.ROUND_HALF_UP));
				}else{
					arrivalOrderDetail.setUninvoicetaxamount(new BigDecimal(0));
					arrivalOrderDetail.setUninvoicenotaxamount(new BigDecimal(0));						
				}
				
				//添加发票时，更新
				arrivalOrderMapper.updateArrivalOrderDetailWriteBack(arrivalOrderDetail);
			}
		}
		if(isRefer){
			arrivalOrderMapper.updateArrivalOrderIsrefer(orderid, "1");
		}else{
			arrivalOrderMapper.updateArrivalOrderIsrefer(orderid, "0");
		}
	}
	@Override
	public void updateArrivalOrderInvoiceAuditWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception{
		List<ArrivalOrderDetail> detailList = arrivalOrderMapper.getArrivalOrderDetailListByOrderid(orderid);
		int invoiceCount=0; //开票
		int mvoiceCount=0; //开票中
		int uninvoiceCount=0; //未开票
		if(detailList.size() != 0){
			for(ArrivalOrderDetail arrivalOrderDetail : detailList){
				PurchaseInvoiceDetail purchaseInvoiceDetail=null;
				if(null!=invoiceDetailList && invoiceDetailList.size() != 0){
					for(PurchaseInvoiceDetail pidItem : invoiceDetailList){
						if(null != pidItem && 
								StringUtils.isNotEmpty(pidItem.getSourcedetailid()) &&
								pidItem.getSourcedetailid().equals(arrivalOrderDetail.getId().toString())){
							purchaseInvoiceDetail=pidItem;
							arrivalOrderDetail.setRemark(pidItem.getRemark());
							arrivalOrderMapper.updateArrivalOrderDetailRemark(arrivalOrderDetail);
							break;
						}
					}
				}
				if(null==purchaseInvoiceDetail){
					purchaseInvoiceDetail=new PurchaseInvoiceDetail();
					purchaseInvoiceDetail.setUnitnum(BigDecimal.ZERO);
					purchaseInvoiceDetail.setTaxamount(BigDecimal.ZERO);
					purchaseInvoiceDetail.setNotaxamount(BigDecimal.ZERO);
				}
				arrivalOrderDetail.setInvoicenum(purchaseInvoiceDetail.getUnitnum());
				arrivalOrderDetail.setUninvoicenum(arrivalOrderDetail.getUnitnum().subtract(purchaseInvoiceDetail.getUnitnum()));
				
				//已开票含税金额 = 已开票金额
				arrivalOrderDetail.setInvoicetaxamount(purchaseInvoiceDetail.getTaxamount());
				//已开票无税金额 = 已开票无税金额
				arrivalOrderDetail.setInvoicenotaxamount(purchaseInvoiceDetail.getNotaxamount());
				
					
				//20150729 审核时，重新计算未开票数量
				if(BigDecimal.ZERO.compareTo(arrivalOrderDetail.getUninvoicenum()) != 0){
					//未开票含税金额 = 未开票数量*含税单价
					arrivalOrderDetail.setUninvoicetaxamount(arrivalOrderDetail.getUninvoicenum().multiply(arrivalOrderDetail.getTaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//未开票无税金额 = 未开票数量*无税单价
					arrivalOrderDetail.setUninvoicenotaxamount(arrivalOrderDetail.getUninvoicenum().multiply(arrivalOrderDetail.getNotaxprice()).setScale(6,BigDecimal.ROUND_HALF_UP));
				}else{
					arrivalOrderDetail.setUninvoicetaxamount(new BigDecimal(0));
					arrivalOrderDetail.setUninvoicenotaxamount(new BigDecimal(0));						
				}
				
				arrivalOrderMapper.updateArrivalOrderDetailWriteBack(arrivalOrderDetail);
				//以供更新开票状态使用
				arrivalOrderDetail.setUninvoicenum(arrivalOrderDetail.getUnitnum().subtract(purchaseInvoiceDetail.getUnitnum()));
			}
		}

		
		if(detailList.size() != 0){
			for(ArrivalOrderDetail arrivalOrderDetail : detailList){				
				if(arrivalOrderDetail.getUnitnum().compareTo(arrivalOrderDetail.getInvoicenum()) == 0){
					invoiceCount=invoiceCount+1;
					updatePurchaseEnterDetailIsinvoice(arrivalOrderDetail.getBillno(), arrivalOrderDetail.getBilldetailno(), "1");
				}else if(arrivalOrderDetail.getUnitnum().compareTo(arrivalOrderDetail.getUninvoicenum()) == 0){
					uninvoiceCount=uninvoiceCount+1;
					updatePurchaseEnterDetailIsinvoice(arrivalOrderDetail.getBillno(), arrivalOrderDetail.getBilldetailno(), "0");
				}else{
					mvoiceCount=mvoiceCount+1;
				}
			}
		}
		//1已开票，0未票,3开票中
		if(invoiceCount>0 && uninvoiceCount==0 && mvoiceCount==0){
			arrivalOrderMapper.updateArrivalOrderInvoice("1", null, orderid);//已开票
		}else if(invoiceCount>0 && uninvoiceCount>0 || mvoiceCount>0){
			arrivalOrderMapper.updateArrivalOrderInvoice("3", null, orderid);//开票中
		}else {
			arrivalOrderMapper.updateArrivalOrderInvoice("0", null, orderid);//未开票		
		}
	}

	@Override
	public ReturnOrder showReturnOrder(String id) throws Exception{
		ReturnOrder returnOrder= showBasePureReturnOrderAndDetail(id);
		return returnOrder;
	}
	@Override
	public ReturnOrder showReturnOrderAndDetail(String id) throws Exception{
		ReturnOrder returnOrder= showBasePureReturnOrderAndDetail(id);
		List<ReturnOrderDetail> oldList=returnOrder.getReturnOrderDetailList();
		List<ReturnOrderDetail> list=new ArrayList<ReturnOrderDetail>();
		if(null!=list && list.size()>0){
			for(ReturnOrderDetail item : oldList){
				if("".equals(item.getInvoicerefer())|| "1".equals(item.getInvoicerefer()) || "3".equals(item.getInvoicerefer())){
					list.add(item);
				}
			}
		}
		returnOrder.setReturnOrderDetailList(list);
		return returnOrder;
	}
	@Override
	public ReturnOrderDetail showReturnOrderDetail(String id) throws Exception{
		ReturnOrderDetail returnOrderDetail= returnOrderMapper.getReturnOrderDetail(id);
		return returnOrderDetail;
	}

	@Override
	public void updateBeginDueInvoiceReferWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception {
		BeginDue beginDue = beginDueMapper.getBeginDueByID(orderid);
		if(null != beginDue){
			PurchaseInvoiceDetail purchaseInvoiceDetail = null;
			if(invoiceDetailList.size() != 0){
				purchaseInvoiceDetail = invoiceDetailList.get(0);
			}
			if(null==purchaseInvoiceDetail){
				purchaseInvoiceDetail=new PurchaseInvoiceDetail();
				purchaseInvoiceDetail.setUnitnum(BigDecimal.ZERO);
				purchaseInvoiceDetail.setTaxamount(BigDecimal.ZERO);
				purchaseInvoiceDetail.setNotaxamount(BigDecimal.ZERO);
			}
			//判断添加的开票总金额与商品金额是否相等
			if(purchaseInvoiceDetail.getTaxamount().compareTo(beginDue.getAmount()) != 0) {
				beginDue.setInvoicerefer("0");
			}
			else{
				beginDue.setInvoicerefer("1");
			}

			beginDueMapper.updateBeginDueInvoicerefer(beginDue);
		}
	}

	@Override
	public void updateReturnOrderInvoiceReferWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception{
		List<ReturnOrderDetail> detailList = returnOrderMapper.getReturnOrderDetailListByOrderid(orderid);		
		if(detailList.size() != 0){
			for(ReturnOrderDetail returnOrderDetail : detailList){
				PurchaseInvoiceDetail purchaseInvoiceDetail=null;
				if(null!=invoiceDetailList && invoiceDetailList.size() != 0){
					for(PurchaseInvoiceDetail pidItem : invoiceDetailList){
						if(null != pidItem && 
								StringUtils.isNotEmpty(pidItem.getSourcedetailid()) &&
								pidItem.getSourcedetailid().equals(returnOrderDetail.getId().toString())){
							purchaseInvoiceDetail=pidItem;
							break;
						}
					}
				}

				if(null==purchaseInvoiceDetail){
					purchaseInvoiceDetail=new PurchaseInvoiceDetail();
					purchaseInvoiceDetail.setUnitnum(BigDecimal.ZERO);
					purchaseInvoiceDetail.setTaxamount(BigDecimal.ZERO);
					purchaseInvoiceDetail.setNotaxamount(BigDecimal.ZERO);
				}
				
				//判断添加的开票总商品数与商品数是否相等
				if(purchaseInvoiceDetail.getUnitnum().abs().compareTo(returnOrderDetail.getUnitnum()) != 0){
					returnOrderDetail.setInvoicerefer("0");
					returnOrderDetail.setUninvoicenum(returnOrderDetail.getUnitnum().subtract(purchaseInvoiceDetail.getUnitnum().abs()));
				}
				else{
					returnOrderDetail.setInvoicerefer("1");
					//20150729 旧代码
					//returnOrderDetail.setUninvoicenum(BigDecimal.ZERO);
					//20150729 新代码 发票保存成功后，未开票数量 等于开票数量
					returnOrderDetail.setUninvoicenum(purchaseInvoiceDetail.getUnitnum().abs());
				}
				
				if(BigDecimal.ZERO.compareTo(returnOrderDetail.getUninvoicenum())!=0){
					//未开票含税金额 = 未开票数量*含税单价
					returnOrderDetail.setUninvoicetaxamount(returnOrderDetail.getUninvoicenum().abs().multiply(returnOrderDetail.getTaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					
					if(StringUtils.isNotEmpty(returnOrderDetail.getTaxtype())){
						BigDecimal notaxamount = getNotaxAmountByTaxAmount(returnOrderDetail.getUninvoicetaxamount(), returnOrderDetail.getTaxtype());
						returnOrderDetail.setUninvoicenotaxamount(notaxamount);
					}else{
						//未开票无税金额 = 未开票数量*无税单价
						returnOrderDetail.setUninvoicenotaxamount(returnOrderDetail.getUninvoicenum().abs().multiply(returnOrderDetail.getNotaxprice()).setScale(6,BigDecimal.ROUND_HALF_UP));
					}
				}else{
					returnOrderDetail.setUninvoicetaxamount(new BigDecimal(0));
					returnOrderDetail.setUninvoicenotaxamount(new BigDecimal(0));
				}
				
				returnOrderMapper.updateReturnOrderDetailWriteBack(returnOrderDetail);
			}
		}		
	}
	@Override
	public void updateReturnOrderInvoiceAuditWriteBack(String orderid,List<PurchaseInvoiceDetail> invoiceDetailList) throws Exception{
		List<ReturnOrderDetail> detailList = returnOrderMapper.getReturnOrderDetailListByOrderid(orderid);
		int invoiceCount=0; //开票
		int mvoiceCount=0; //开票中
		int uninvoiceCount=0; //未开票
		if(detailList.size() != 0){
			for(ReturnOrderDetail returnOrderDetail : detailList){
				PurchaseInvoiceDetail purchaseInvoiceDetail=null;
				if(null!=invoiceDetailList && invoiceDetailList.size() != 0){
					for(PurchaseInvoiceDetail pidItem : invoiceDetailList){
						if(null != pidItem && 
								StringUtils.isNotEmpty(pidItem.getSourcedetailid()) &&
								pidItem.getSourcedetailid().equals(returnOrderDetail.getId().toString())){
							purchaseInvoiceDetail=pidItem;
							break;
						}
					}
				}
				if(null==purchaseInvoiceDetail){
					purchaseInvoiceDetail=new PurchaseInvoiceDetail();
					purchaseInvoiceDetail.setUnitnum(BigDecimal.ZERO);
					purchaseInvoiceDetail.setTaxamount(BigDecimal.ZERO);
					purchaseInvoiceDetail.setNotaxamount(BigDecimal.ZERO);
				}
				
				returnOrderDetail.setInvoicenum(purchaseInvoiceDetail.getUnitnum().abs());
				returnOrderDetail.setUninvoicenum(returnOrderDetail.getUnitnum().subtract(purchaseInvoiceDetail.getUnitnum().abs()));
				
				//已开票含税金额 = 已开票金额
				returnOrderDetail.setInvoicetaxamount(purchaseInvoiceDetail.getTaxamount().abs());
				//已开票无税金额 = 已开票无税金额
				returnOrderDetail.setInvoicenotaxamount(purchaseInvoiceDetail.getNotaxamount().abs());
				
				/*
				//审核时，不更新未开票金额，未开票金额只用于添加发票时
				if(BigDecimal.ZERO.compareTo(returnOrderDetail.getUninvoicenum())==0){
					returnOrderDetail.setUninvoicetaxamount(new BigDecimal(0));
					returnOrderDetail.setUninvoicenotaxamount(new BigDecimal(0));
				}else{
					//更新开票数量，不更未开票数量 
					returnOrderDetail.setUninvoicenum(null);
					returnOrderDetail.setUninvoicetaxamount(null);
					returnOrderDetail.setUninvoicenotaxamount(null);
				}
				*/
				
				if(BigDecimal.ZERO.compareTo(returnOrderDetail.getUninvoicenum())!=0){
					//未开票含税金额 = 未开票数量*含税单价
					returnOrderDetail.setUninvoicetaxamount(returnOrderDetail.getUninvoicenum().abs().multiply(returnOrderDetail.getTaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					
					if(StringUtils.isNotEmpty(returnOrderDetail.getTaxtype())){
						BigDecimal notaxamount = getNotaxAmountByTaxAmount(returnOrderDetail.getUninvoicetaxamount(), returnOrderDetail.getTaxtype());
						returnOrderDetail.setUninvoicenotaxamount(notaxamount);
					}else{
						//未开票无税金额 = 未开票数量*无税单价
						returnOrderDetail.setUninvoicenotaxamount(returnOrderDetail.getUninvoicenum().abs().multiply(returnOrderDetail.getNotaxprice()).setScale(6,BigDecimal.ROUND_HALF_UP));
					}					
				}else{
					returnOrderDetail.setUninvoicetaxamount(new BigDecimal(0));
					returnOrderDetail.setUninvoicenotaxamount(new BigDecimal(0));
				}
				
				returnOrderMapper.updateReturnOrderDetailInvoiceWriteBack(returnOrderDetail);
				//以供更新开票状态使用				
				returnOrderDetail.setUninvoicenum(returnOrderDetail.getUnitnum().subtract(purchaseInvoiceDetail.getUnitnum().abs()));
				
			}
		}
		
		if(detailList.size() != 0){
			for(ReturnOrderDetail returnOrderDetail : detailList){
				if(returnOrderDetail.getUnitnum().compareTo(returnOrderDetail.getInvoicenum()) == 0){
					invoiceCount=invoiceCount+1;
					updatePurchaseRejectDetailIsinvoice(returnOrderDetail.getOrderid(), returnOrderDetail.getId().toString(), "1");
				}else if(returnOrderDetail.getUnitnum().compareTo(returnOrderDetail.getUninvoicenum()) == 0){
					uninvoiceCount=uninvoiceCount+1;
					updatePurchaseRejectDetailIsinvoice(returnOrderDetail.getOrderid(), returnOrderDetail.getId().toString(), "0");
				}else{
					mvoiceCount=mvoiceCount+1;
				}
			}
		}
		if(invoiceCount>0 && uninvoiceCount==0 && mvoiceCount==0){
			returnOrderMapper.updateReturnOrderInvoice("1", null, orderid);//已开票
		}else if(invoiceCount>0 && uninvoiceCount>0 || mvoiceCount>0){
			returnOrderMapper.updateReturnOrderInvoice("3", null, orderid);//开票中
		}else{
			returnOrderMapper.updateReturnOrderInvoice("4", null, orderid);//未开票		
		}
	}
	@Override
	public boolean updateReturnOrderWriteBack(String billno,List<PurchaseInvoiceDetail> list, String canceldate) throws Exception{
		boolean flag=false;
		if(list==null || list.size()==0){
			return flag;
		}
		List<ReturnOrderDetail> returnOrderDetailList=returnOrderMapper.getReturnOrderDetailListByOrderid(billno);
		PurchaseInvoiceDetail purchaseInvoiceDetail=null;
		boolean isclose=true;
		boolean iswriteoff=false;
		ReturnOrderDetail uprodDetail=null;
		
		for(ReturnOrderDetail item : returnOrderDetailList){
			iswriteoff=false;
			purchaseInvoiceDetail=getPurchaseInvoiceDetailById(item.getId().toString(), list);
			if(null !=purchaseInvoiceDetail){
				uprodDetail=new ReturnOrderDetail();
				uprodDetail.setId(item.getId());			
				
				uprodDetail.setWriteoffamount(purchaseInvoiceDetail.getTaxamount().abs());
				uprodDetail.setWriteoffnum(purchaseInvoiceDetail.getUnitnum().abs());
				
				if(!"1".equals(item.getIswriteoff())){
					if(item.getUnitnum().compareTo(purchaseInvoiceDetail.getUnitnum().abs())==0){
						uprodDetail.setIswriteoff("1");	//更新核销状态
						iswriteoff=true;
					}
				}else{
					iswriteoff=true;
					uprodDetail.setIswriteoff("1");
				}
				returnOrderMapper.updateReturnOrderDetailInvoiceWriteBack(uprodDetail);
				if(!"1".equals(uprodDetail.getIswriteoff())){
					isclose=false;
				}
				updatePurchaseRejectDetailIswriteoff(item.getOrderid(), item.getId()+"", (iswriteoff?"1":"0"));
			}else {
				if(null==item.getWriteoffamount() || 
						item.getUnitnum().compareTo(item.getWriteoffnum())!=0 || 
						BigDecimal.ZERO.compareTo(item.getWriteoffnum())==0){
					isclose=false;
				}
			}
		}
		if(isclose){
			updateReturnOrderCloseStatus(billno);
			returnOrderMapper.updateReturnOrderInvoice("2", canceldate, billno);
		}
		return flag;
	}
	/**
	 * 关闭采购退货通知单状态
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-21
	 */
	private boolean updateReturnOrderCloseStatus(String id) throws Exception{
		ReturnOrder order=new ReturnOrder();
		order.setStatus("4");
		order.setId(id);
		return returnOrderMapper.updateReturnOrderStatus(order)>0;
	}
	@Override
	public boolean updatePurchaseEnterDetailIsinvoice(String billid,String detailid,String isinvoice) throws Exception{
		return storageForPurchaseService.updatePurchaseEnterDetailIsinvoice(billid, detailid, isinvoice);
	}
	@Override
	public boolean updatePurchaseEnterDetailIswriteoff(String billid,String detailid,String iswriteoff) throws Exception{
		return storageForPurchaseService.updatePurchaseEnterDetailIswriteoff(billid, detailid, iswriteoff);
	}

	
	@Override
	public boolean updatePurchaseRejectDetailIsinvoice(String billid,String detailid,String isinvoice) throws Exception{
		return storageForPurchaseService.updatePurchaseRejectDetailIsinvoice(billid, detailid, isinvoice);
	}
	@Override
	public boolean updatePurchaseRejectDetailIswriteoff(String billid,String detailid,String iswriteoff) throws Exception{
		return storageForPurchaseService.updatePurchaseRejectDetailIswriteoff(billid, detailid, iswriteoff);
	}

	@Override
	public void updateBeginDueInvoiceAuditWriteBack(String sourceid,List<PurchaseInvoiceDetail> invoiceDetailList, String isinvoice) throws Exception {
		BeginDue beginDue = beginDueMapper.getBeginDueByID(sourceid);
		if(null != beginDue){
			PurchaseInvoiceDetail purchaseInvoiceDetail = null;
			if(invoiceDetailList.size() != 0){
				purchaseInvoiceDetail = invoiceDetailList.get(0);
			}
			if(null==purchaseInvoiceDetail){
				purchaseInvoiceDetail=new PurchaseInvoiceDetail();
				purchaseInvoiceDetail.setUnitnum(BigDecimal.ZERO);
				purchaseInvoiceDetail.setTaxamount(BigDecimal.ZERO);
				purchaseInvoiceDetail.setNotaxamount(BigDecimal.ZERO);
			}
			if("1".equals(isinvoice)){
				beginDue.setIsinvoice(isinvoice);
				beginDue.setInvoiceamount(purchaseInvoiceDetail.getTaxamount());
				beginDue.setInvoicenotaxamount(purchaseInvoiceDetail.getNotaxamount());
				beginDue.setInvoicedate(CommonUtils.getTodayDataStr());
			}else if("0".equals(isinvoice)){
				beginDue.setIsinvoice(isinvoice);
				beginDue.setInvoiceamount(BigDecimal.ZERO);
				beginDue.setInvoicenotaxamount(BigDecimal.ZERO);
				beginDue.setInvoicedate("");
			}
			beginDueMapper.updateBeginDueInvoiceAuditWriteBack(beginDue);
		}
	}

	@Override
	public void updateBeginDueWriteBack(String id, List<PurchaseInvoiceDetail> list, String canceldate,String iswriteoff) throws Exception {
		BeginDue beginDue = beginDueMapper.getBeginDueByID(id);
		if(null != beginDue){
			PurchaseInvoiceDetail purchaseInvoiceDetail = null;
			if(list.size() != 0){
				purchaseInvoiceDetail = list.get(0);
			}
			if(null==purchaseInvoiceDetail){
				purchaseInvoiceDetail=new PurchaseInvoiceDetail();
				purchaseInvoiceDetail.setUnitnum(BigDecimal.ZERO);
				purchaseInvoiceDetail.setTaxamount(BigDecimal.ZERO);
				purchaseInvoiceDetail.setNotaxamount(BigDecimal.ZERO);
			}
			if("1".equals(iswriteoff)){
				beginDue.setStatus("4");
				beginDue.setIswriteoff(iswriteoff);
				beginDue.setWriteoffamount(purchaseInvoiceDetail.getTaxamount());
				beginDue.setWriteoffnotaxamount(purchaseInvoiceDetail.getNotaxamount());
				beginDue.setWriteoffdate(canceldate);

				SysUser sysUser = getSysUser();
				beginDue.setWriteoffuserid(sysUser.getUserid());
				beginDue.setWriteoffusername(sysUser.getName());
			} else if ("0".equals(iswriteoff)){
				beginDue.setStatus("3");
				beginDue.setIswriteoff(iswriteoff);
				beginDue.setWriteoffamount(BigDecimal.ZERO);
				beginDue.setWriteoffnotaxamount(BigDecimal.ZERO);
				beginDue.setWriteoffdate("");

				beginDue.setWriteoffuserid("");
				beginDue.setWriteoffusername("");
			}
			beginDueMapper.updateBeginDueWriteBack(beginDue);
		}
	}
}

