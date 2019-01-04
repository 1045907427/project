/**
 * @(#)PurchaseExtService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-6-17 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.impl.ext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.purchase.model.PlannedOrder;
import com.hd.agent.purchase.model.PlannedOrderDetail;
import com.hd.agent.purchase.service.ext.IPurchaseSelfExtService;
import com.hd.agent.purchase.service.impl.BasePurchaseServiceImpl;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class PurchaseSelfExtServiceImpl extends BasePurchaseServiceImpl implements IPurchaseSelfExtService {

	@Override
	public boolean updatePlannedOrderCloseStatus(String id) throws Exception{
		PlannedOrder plannedOrder=new PlannedOrder();
		plannedOrder.setId(id);
		plannedOrder.setStatus("4");
		return plannedOrderMapper.updatePlannedOrderStatus(plannedOrder)>0;
	}

	@Override
	public boolean updatePlannedOrderAuditStatus(String id) throws Exception{
		PlannedOrder plannedOrder=new PlannedOrder();
		plannedOrder.setId(id);
		plannedOrder.setStatus("3");
		return plannedOrderMapper.updatePlannedOrderStatus(plannedOrder)>0;	
	}
	@Override
	public boolean updatePlannedOrderDetailWriteBack(String billno,List<BuyOrderDetail> list) throws Exception {
		boolean flag=false;
		if(list==null || list.size()==0){
			return flag;
		}
		PlannedOrderDetail uppoDetail=null;
		List<PlannedOrderDetail> poDetailList=plannedOrderMapper.getPlannedOrderDetailListByOrderid(billno);
		
		for(PlannedOrderDetail item : poDetailList){
			BuyOrderDetail buyOrderDetail=getBuyOrderDetailByBuyDetailId(item.getId().toString(),list);
			if(null!=buyOrderDetail){
				uppoDetail=new PlannedOrderDetail();
				uppoDetail.setId(item.getId());
				uppoDetail.setOrderunitnum(buyOrderDetail.getUnitnum());
				uppoDetail.setOrderauxnum(buyOrderDetail.getAuxnum());
				uppoDetail.setOrdertaxamount(buyOrderDetail.getTaxamount());
				uppoDetail.setUnorderunitnum(item.getUnitnum().subtract(buyOrderDetail.getUnitnum()));
				uppoDetail.setUnorderauxnum(item.getAuxnum().subtract(buyOrderDetail.getAuxnum()));
				uppoDetail.setUnordertaxamount(item.getTaxamount().subtract(buyOrderDetail.getTaxamount()));
				flag=plannedOrderMapper.updatePlannedOrderDetailWriteBack(uppoDetail)>0;
			}
		}
		if(flag){
			updatePlannedOrderCloseStatus(billno);
		}
		return flag;
	}
	private BuyOrderDetail getBuyOrderDetailByBuyDetailId(String detailid,List<BuyOrderDetail> list){
		if(null==list || list.size()==0){
			return null;
		}
		BuyOrderDetail buyOrderDetail=null;
		for(BuyOrderDetail item: list){
			if(StringUtils.isNotEmpty(detailid) && detailid.equals(item.getBilldetailno())){
				buyOrderDetail=item;
				break;
			}
		}
		return buyOrderDetail;
	}
	@Override
	public boolean updatePlannedOrderDetailReWriteBack(String billno,List<BuyOrderDetail> list) throws Exception {
		boolean flag=false;
		if(list==null || list.size()==0){
			return flag;
		}
		PlannedOrderDetail uppoDetail=null;
		for(BuyOrderDetail item : list){
			if(StringUtils.isNumeric(item.getBilldetailno())){
				uppoDetail=new PlannedOrderDetail();
				uppoDetail.setId(Integer.parseInt(item.getBilldetailno()));
				uppoDetail.setOrderunitnum(BigDecimal.ZERO);
				uppoDetail.setOrderauxnum(BigDecimal.ZERO);
				uppoDetail.setOrdertaxamount(BigDecimal.ZERO);
				uppoDetail.setUnorderunitnum(BigDecimal.ZERO);
				uppoDetail.setUnorderauxnum(BigDecimal.ZERO);
				uppoDetail.setUnorderunitnum(BigDecimal.ZERO);
				flag=plannedOrderMapper.updatePlannedOrderDetailWriteBack(uppoDetail)>0;
			}
		}
		updatePlannedOrderAuditStatus(billno);
		return flag;
	}
	@Override
	public boolean updatePlannedOrderReferFlag(String id) throws Exception{
		return updateBasePlannedOrderRefer(id, "1");
	}
	@Override
	public boolean updatePlannedOrderUnReferFlag(String id) throws Exception{
		return updateBasePlannedOrderRefer(id, "0");
	}
	@Override
	public BuyOrder showBuyOrderAndDetailReferByBillno(String billno) throws Exception{
		BuyOrder buyOrder=new BuyOrder();
		List<BuyOrderDetail> bodlist=new ArrayList<BuyOrderDetail>();
		BuyOrderDetail buyOrderDetail=null;
		
		SysUser sysUser=getSysUser();
		buyOrder.setAddtime(new Date());
		buyOrder.setAdduserid(sysUser.getUserid());
		buyOrder.setAddusername(sysUser.getName());
		if(null!=billno && !"".equals(billno.trim())){
			PlannedOrder plannedOrder=showBasePurePlannedOrderAndDetail(billno.trim());
			if(plannedOrder!=null){
				buyOrder.setBusinessdate(plannedOrder.getBusinessdate());
				buyOrder.setBuydeptid(plannedOrder.getBuydeptid());
				buyOrder.setBuyuserid(plannedOrder.getBuyuserid());
				buyOrder.setField01(plannedOrder.getField01());
				buyOrder.setField02(plannedOrder.getField02());
				buyOrder.setField03(plannedOrder.getField03());
				buyOrder.setField04(plannedOrder.getField04());
				buyOrder.setField05(plannedOrder.getField05());
				buyOrder.setField06(plannedOrder.getField06());
				buyOrder.setField07(plannedOrder.getField07());
				buyOrder.setField08(plannedOrder.getField08());
				buyOrder.setHandlerid(plannedOrder.getHandlerid());
				buyOrder.setPrinttimes(0);
				buyOrder.setRemark(plannedOrder.getRemark());
				buyOrder.setSource("1");
				buyOrder.setSupplierid(plannedOrder.getSupplierid());
				buyOrder.setStorageid(plannedOrder.getStorageid());
				buyOrder.setAdddeptid(plannedOrder.getAdddeptid());
				buyOrder.setAdddeptname(plannedOrder.getAdddeptname());
				buyOrder.setAdduserid(plannedOrder.getAdduserid());
				buyOrder.setAddusername(plannedOrder.getAddusername());
				BuySupplier buySupplier=getSupplierInfoById(plannedOrder.getSupplierid());
				if(null!=buySupplier){
					buyOrder.setPaytype(buySupplier.getPaytype());
					buyOrder.setSettletype(buySupplier.getSettletype());
					buyOrder.setHandlerid(buySupplier.getContact());
					buyOrder.setOrderappend(buySupplier.getOrderappend());
				}
				buyOrder.setBillno(plannedOrder.getId());
				List<PlannedOrderDetail> podlist=plannedOrder.getPlannedOrderDetailList();
				if(podlist!=null){
					TaxType taxType=null;
					MeteringUnit meteringUnit=null;
					for(PlannedOrderDetail item :podlist){
						buyOrderDetail=new BuyOrderDetail();
						//商品信息
						if(StringUtils.isNotEmpty(item.getGoodsid())){
							buyOrderDetail.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
						}
						//税种信息
						if(StringUtils.isNotEmpty(item.getTaxtype())){
							taxType=getTaxType(item.getTaxtype());
							if(null!=taxType){
								buyOrderDetail.setTaxtypename(taxType.getName());
							}
						}
						//辅助计量单位信息
						if(StringUtils.isNotEmpty(item.getAuxunitid())){
							meteringUnit=getMeteringUnitById(item.getAuxunitid());
							if(null!=meteringUnit){
								buyOrderDetail.setAuxunitname(meteringUnit.getName());
							}
						}
						buyOrderDetail.setOrderid(buyOrder.getId());
						buyOrderDetail.setArrivedate(item.getArrivedate());
						buyOrderDetail.setAuxnum(item.getAuxnum());
						buyOrderDetail.setAuxnumdetail(item.getAuxnumdetail());
						buyOrderDetail.setAuxunitid(item.getAuxunitid());
						buyOrderDetail.setAuxunitname(item.getAuxunitname());
						buyOrderDetail.setAuxremainder(item.getAuxremainder());
						buyOrderDetail.setBilldetailno(item.getId().toString());
						buyOrderDetail.setBillno(item.getOrderid());
						buyOrderDetail.setField01(item.getField01());
						buyOrderDetail.setField02(item.getField02());
						buyOrderDetail.setField03(item.getField03());
						buyOrderDetail.setField04(item.getField04());
						buyOrderDetail.setField05(item.getField05());
						buyOrderDetail.setField06(item.getField06());
						buyOrderDetail.setField07(item.getField07());
						buyOrderDetail.setField08(item.getField08());
						buyOrderDetail.setGoodsid(item.getGoodsid());
						//buyOrderDetail.setGoodsInfo(item.getGoodsInfo());
						buyOrderDetail.setRemark(item.getRemark());
						buyOrderDetail.setTax(item.getTax());
						buyOrderDetail.setTaxamount(item.getTaxamount());
						buyOrderDetail.setTaxprice(item.getTaxprice());
						buyOrderDetail.setNotaxamount(item.getNotaxamount());
						buyOrderDetail.setNotaxprice(item.getNotaxprice());
						buyOrderDetail.setTaxtype(item.getTaxtype());
						buyOrderDetail.setTaxtypename(item.getTaxtypename());
						buyOrderDetail.setUnitid(item.getUnitid());
						buyOrderDetail.setUnitname(item.getUnitname());
						buyOrderDetail.setUnitnum(item.getUnitnum());
						buyOrderDetail.setRemark(item.getRemark());
						bodlist.add(buyOrderDetail);
					}
				}
			}
		}
		buyOrder.setBuyOrderDetailList(bodlist);
		return buyOrder;
	}
	
	
	/**
	 * 审核后自动生成采购订单
	 * @param plannedOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	@Override
	public String addCreateBuyOrderByPlanAudit(PlannedOrder plannedOrder) throws Exception{
		boolean flag=false;
		BuyOrder buyOrder=new BuyOrder();
		SysUser sysUser=getSysUser();
		buyOrder.setId(getBillNumber(buyOrder, "t_purchase_buyorder"));
		buyOrder.setAdddeptid(sysUser.getDepartmentid());
		buyOrder.setAdddeptname(sysUser.getDepartmentname());
		buyOrder.setAddtime(new Date());
		buyOrder.setAdduserid(plannedOrder.getAdduserid());
		buyOrder.setAddusername(plannedOrder.getAddusername());
		buyOrder.setBusinessdate(plannedOrder.getBusinessdate());
		buyOrder.setBuydeptid(plannedOrder.getBuydeptid());
		buyOrder.setBuyuserid(plannedOrder.getBuyuserid());
		buyOrder.setField01(plannedOrder.getField01());
		buyOrder.setField02(plannedOrder.getField02());
		buyOrder.setField03(plannedOrder.getField03());
		buyOrder.setField04(plannedOrder.getField04());
		buyOrder.setField05(plannedOrder.getField05());
		buyOrder.setField06(plannedOrder.getField06());
		buyOrder.setField07(plannedOrder.getField07());
		buyOrder.setField08(plannedOrder.getField08());
		buyOrder.setHandlerid(plannedOrder.getHandlerid());
		buyOrder.setStorageid(plannedOrder.getStorageid());
		buyOrder.setPrinttimes(0);
		buyOrder.setRemark(plannedOrder.getRemark());
		buyOrder.setSource("1");
		buyOrder.setStatus("2");
		buyOrder.setSupplierid(plannedOrder.getSupplierid());
		BuySupplier buySupplier=getSupplierInfoById(plannedOrder.getSupplierid());
		if(null!=buySupplier){
			buyOrder.setPaytype(buySupplier.getPaytype());
			buyOrder.setSettletype(buySupplier.getSettletype());
			buyOrder.setHandlerid(buySupplier.getContact());
			if(StringUtils.isEmpty(plannedOrder.getOrderappend())){
				buyOrder.setOrderappend(buySupplier.getOrderappend());
			}else{
				buyOrder.setOrderappend(plannedOrder.getOrderappend());
			}
		}
		buyOrder.setBillno(plannedOrder.getId());	
		buyOrder.setArrivedate(plannedOrder.getArrivedate());	
		flag=buyOrderMapper.insertBuyOrder(buyOrder)>0;
		if(flag){
			List<PlannedOrderDetail> podlist=plannedOrder.getPlannedOrderDetailList();
			BuyOrderDetail buyOrderDetail=null;
			for(PlannedOrderDetail item :podlist){
				buyOrderDetail=new BuyOrderDetail();
				buyOrderDetail.setOrderid(buyOrder.getId());
				buyOrderDetail.setArrivedate(item.getArrivedate());
				buyOrderDetail.setAuxnum(item.getAuxnum());
				buyOrderDetail.setAuxnumdetail(item.getAuxnumdetail());
				buyOrderDetail.setAuxunitid(item.getAuxunitid());
				buyOrderDetail.setAuxunitname(item.getAuxunitname());
				buyOrderDetail.setAuxremainder(item.getAuxremainder());
				buyOrderDetail.setTotalbox(item.getTotalbox());
				buyOrderDetail.setBilldetailno(item.getId().toString());
				buyOrderDetail.setBillno(item.getOrderid());
				buyOrderDetail.setField01(item.getField01());
				buyOrderDetail.setField02(item.getField02());
				buyOrderDetail.setField03(item.getField03());
				buyOrderDetail.setField04(item.getField04());
				buyOrderDetail.setField05(item.getField05());
				buyOrderDetail.setField06(item.getField06());
				buyOrderDetail.setField07(item.getField07());
				buyOrderDetail.setField08(item.getField08());
				buyOrderDetail.setGoodsid(item.getGoodsid());
				buyOrderDetail.setNotaxamount(item.getNotaxamount());
				buyOrderDetail.setNotaxprice(item.getNotaxprice());
				buyOrderDetail.setRemark(item.getRemark());
				buyOrderDetail.setTax(item.getTax());
				buyOrderDetail.setTaxamount(item.getTaxamount());
				buyOrderDetail.setTaxprice(item.getTaxprice());
				buyOrderDetail.setTaxtype(item.getTaxtype());
				buyOrderDetail.setUnitid(item.getUnitid());
				buyOrderDetail.setUnitname(item.getUnitname());
				buyOrderDetail.setUnitnum(item.getUnitnum());
				buyOrderMapper.insertBuyOrderDetail(buyOrderDetail);
			}
		}
		if(flag){
			return buyOrder.getId();
		}
		return "";		
	}
	@Override
	public boolean deleteBuyOrderAndDetailByBillno(String billno) throws Exception{
		boolean flag = true;
		BuyOrder buyOrder=buyOrderMapper.getBuyOrderByBillno(billno);
		if(null==buyOrder){
			return flag;
		}
		if(!"1".equals(buyOrder.getStatus()) && !"2".equals(buyOrder.getStatus())){
			flag=false;
		}
		if(flag){
			buyOrderMapper.deleteBuyOrder(buyOrder.getId());
			buyOrderMapper.deleteBuyOrderDetailByOrderid(buyOrder.getId());
		}
		return flag;
	}
}

