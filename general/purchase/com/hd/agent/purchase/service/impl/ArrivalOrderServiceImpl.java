/**
 * @(#)ArrivalOrderServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-5-21 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.BeginDue;
import com.hd.agent.account.model.PurchaseInvoice;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.dao.ArrivalOrderChangeMapper;
import com.hd.agent.purchase.model.*;
import com.hd.agent.purchase.service.IArrivalOrderService;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.model.PurchaseEnterDetail;
import com.hd.agent.storage.service.IStorageForPurchaseService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 采购进货单服务实现
 * 
 * @author zhanghonghui
 */
public class ArrivalOrderServiceImpl extends BasePurchaseServiceImpl implements IArrivalOrderService {

	
	private IStorageForPurchaseService storageForPurchaseService;

	private ArrivalOrderChangeMapper arrivalOrderChangeMapper;

	public IStorageForPurchaseService getStorageForPurchaseService() {
		return storageForPurchaseService;
	}

	public void setStorageForPurchaseService(
			IStorageForPurchaseService storageForPurchaseService) {
		this.storageForPurchaseService = storageForPurchaseService;
	}
	public boolean insertArrivalOrder(ArrivalOrder arrivalOrder) throws Exception{
		return arrivalOrderMapper.insertArrivalOrder(arrivalOrder)>0;
	}

	public ArrivalOrderChangeMapper getArrivalOrderChangeMapper() {
		return arrivalOrderChangeMapper;
	}

	public void setArrivalOrderChangeMapper(ArrivalOrderChangeMapper arrivalOrderChangeMapper) {
		this.arrivalOrderChangeMapper = arrivalOrderChangeMapper;
	}

	@Override
	public boolean addArrivalOrderAddDetail(ArrivalOrder arrivalOrder) throws Exception{
		arrivalOrder.setId(getBillNumber(arrivalOrder, "t_purchase_arrivalorder"));
		if(StringUtils.isEmpty(arrivalOrder.getId())){
			return false;
		}
		if(StringUtils.isNotEmpty(arrivalOrder.getBillno())){
			if(!"1".equals(arrivalOrder.getSource())){
				arrivalOrder.setSource("1");
			}
		}
		boolean flag=insertArrivalOrder(arrivalOrder);
		List<ArrivalOrderDetail> list=arrivalOrder.getArrivalOrderDetailList();
		if(flag && list!=null && list.size()>0){
			int iseq=1;
			for(ArrivalOrderDetail item : list){			
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
				item.setOrderid(arrivalOrder.getId());
				item.setSeq(iseq);
				item.setUninvoicenum(item.getUnitnum());
				item.setInvoicenum(BigDecimal.ZERO);
				item.setUninvoicetaxamount(item.getTaxamount());
				insertArrivalOrderDetail(item);
				iseq=iseq+1;
                BigDecimal boxnum = item.getGoodsInfo().getBoxnum();
                if(null == item.getBoxprice() && null == item.getNoboxprice() && item.getAuxnum() != null){
                    if(null == item.getTaxprice()){
                        item.setBoxprice(new BigDecimal(0));
                    }else{
                        item.setBoxprice(item.getTaxprice().multiply(boxnum));
                    }
                    if(null == item.getNotaxprice()){
                        item.setNoboxprice(new BigDecimal(0));
                    }else{
                        item.setNoboxprice(item.getNotaxprice().multiply(boxnum));
                    }
                }
			}
		}
		if(StringUtils.isNotEmpty(arrivalOrder.getBillno())){
			storageForPurchaseService.updatePurchaseEnterRefer("1", arrivalOrder.getBillno());
		}

		return flag;
		
	}
	@Override
	public ArrivalOrder showArrivalOrderAndDetail(String id) throws Exception{
		ArrivalOrder arrivalOrder= arrivalOrderMapper.getArrivalOrder(id);
		if(null!=arrivalOrder){
			DepartMent departMent =null;
			TaxType taxType=null;
			MeteringUnit meteringUnit=null;
			BuySupplier buySupplier = getSupplierInfoById(arrivalOrder.getSupplierid());
			if(null!=buySupplier){
				arrivalOrder.setSuppliername(buySupplier.getName());
			}
			List<ArrivalOrderDetail> list=showPureArrivalOrderDetailListByOrderId(arrivalOrder.getId());
			if(null!=list && list.size()>0){
				for(ArrivalOrderDetail item :list){
					Map changeMap=arrivalOrderChangeMapper.getArrivalPurchaseChangeData(item);
					if(changeMap!=null){
						item.setChangeamount(changeMap.get("taxamount").toString());
					}
					GoodsInfo goodsInfo = getGoodsInfoByID(item.getGoodsid());
					if(StringUtils.isNotEmpty(item.getGoodsid())){
						item.setGoodsInfo(goodsInfo);
					}
					if(StringUtils.isNotEmpty(item.getTaxtype())){
						taxType=getTaxType(item.getTaxtype());
						if(null!=taxType){
							item.setTaxtypename(taxType.getName());
						}
					}
					if(StringUtils.isNotEmpty(item.getAuxunitid())){
						meteringUnit=getMeteringUnitById(item.getAuxunitid());
						if(null!=meteringUnit){
							item.setAuxunitname(meteringUnit.getName());
						}
					}
					if(null!=goodsInfo){
						item.setBoxprice(goodsInfo.getBoxnum().multiply(item.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
						item.setNoboxprice(goodsInfo.getBoxnum().multiply(item.getNotaxprice()).setScale(6, BigDecimal.ROUND_HALF_UP));
					}
				}
				arrivalOrder.setArrivalOrderDetailList(list);
			}else{
				arrivalOrder.setArrivalOrderDetailList(new ArrayList<ArrivalOrderDetail>());
			}
		}
		return arrivalOrder;
	}

	@Override
	public ArrivalOrder getArrivalOrder(String id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		ArrivalOrder arrivalOrder= arrivalOrderMapper.getArrivalOrderBy(map);
		if(null != arrivalOrder){
			//供应商
			BuySupplier buySupplier = getSupplierInfoById(arrivalOrder.getSupplierid());
			if(null != buySupplier){
				arrivalOrder.setSuppliername(buySupplier.getName());
			}
			//对方经手人
			Map map2 = new HashMap();
			map2.put("id", arrivalOrder.getHandlerid());
			Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(map2);
			if(contacter != null){
				arrivalOrder.setHandlername(contacter.getName());
			}
			//采购部门
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(arrivalOrder.getBuydeptid());
			if(departMent != null){
				arrivalOrder.setBuydeptname(departMent.getName());
			}
			//采购人员
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(arrivalOrder.getBuyuserid());
			if(personnel != null){
				arrivalOrder.setBuyusername(personnel.getName());
			}
			
			Map total = getArrivalOrderMapper().getArrivalOrderDetailTotal(id);
			if(null != total){
				if(total.containsKey("taxamount")){
					arrivalOrder.setField01(total.get("taxamount").toString());
				}
				if(total.containsKey("notaxamount")){
					arrivalOrder.setField02(total.get("notaxamount").toString());
				}
				if(total.containsKey("tax")){
					arrivalOrder.setField03(total.get("tax").toString());
				}

				if(total.containsKey("oldtaxamount")){
					arrivalOrder.setOldtaxamount(total.get("oldtaxamount").toString());
				}
				if(total.containsKey("oldnotaxamount")){
					arrivalOrder.setOldnotaxamount(total.get("oldnotaxamount").toString());
				}
				if(total.containsKey("oldtax")){
					arrivalOrder.setOldtax(total.get("oldtax").toString());
				}
			}
			
			List<ArrivalOrderDetail> list=showPureArrivalOrderDetailListByOrderId(id);
			if(null!=list && list.size()>0){
				for(ArrivalOrderDetail item :list){
					if(null!=item ){
						if(StringUtils.isNotEmpty(item.getGoodsid())){
							item.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
						}
						if(StringUtils.isNotEmpty(item.getTaxtype())){
							TaxType	taxType=getTaxType(item.getTaxtype());
							if(null!=taxType){
								item.setTaxtypename(taxType.getName());
							}
						}
						if(StringUtils.isNotEmpty(item.getAuxunitid())){
							MeteringUnit meteringUnit=getMeteringUnitById(item.getAuxunitid());
							if(null!=meteringUnit){
								item.setAuxunitname(meteringUnit.getName());
							}
						}
					}
				}
				arrivalOrder.setArrivalOrderDetailList(list);
			}else{
				arrivalOrder.setArrivalOrderDetailList(new ArrayList<ArrivalOrderDetail>());
			}
		}
		return arrivalOrder;
	}
	
	@Override
	public boolean updateArrivalOrderAddDetail(ArrivalOrder arrivalOrder) throws Exception{
		ArrivalOrder oldArrivalOrder = arrivalOrderMapper.getArrivalOrder(arrivalOrder.getId());
		if(null==oldArrivalOrder || "3".equals(oldArrivalOrder.getStatus()) || "4".equals(oldArrivalOrder.getStatus())){
			return false;
		}
		boolean flag=arrivalOrderMapper.updateArrivalOrder(arrivalOrder)>0;
		if(flag ){
			if(!"1".equals(arrivalOrder.getSource())){
				deleteArrivalOrderDetailByOrderid(arrivalOrder.getId());
				List<ArrivalOrderDetail> list=arrivalOrder.getArrivalOrderDetailList();
				if(null!=list && list.size()>0){
					int iseq=1;
					for(ArrivalOrderDetail item : list){	
						//计算辅单位数量
						Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
						if(auxmap.containsKey("auxnum")){
							item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
						}
						item.setOrderid(arrivalOrder.getId());
						item.setSeq(iseq);
						item.setUninvoicenum(item.getUnitnum());
						item.setInvoicenum(BigDecimal.ZERO);
						item.setUninvoicetaxamount(item.getTaxamount());
						insertArrivalOrderDetail(item);
						iseq=iseq+1;
					}
				}
			}else{
				List<ArrivalOrderDetail> list=arrivalOrder.getArrivalOrderDetailList();
				if(null!=list && list.size()>0){
					for(ArrivalOrderDetail item : list){
						item.setOrderid(arrivalOrder.getId());
						item.setUninvoicenum(item.getUnitnum());
						item.setInvoicenum(BigDecimal.ZERO);
						item.setUninvoicetaxamount(item.getTaxamount());
						updateArrivalOrderDetail(item);
					}
				}
			}
		}
		return flag;
	}

	@Override
	public boolean deleteArrivalOrderAndDetail(String id) throws Exception{
		ArrivalOrder arrivalOrder=showArrivalOrderByDataAuth(id);
		if(null==arrivalOrder || "3".equals(arrivalOrder.getStatus()) || "4".equals(arrivalOrder.getStatus())){
			return false;
		}
		if("1".equals(arrivalOrder.getIsrefer())){
			return false;
		}
		boolean flag=arrivalOrderMapper.deleteArrivalOrder(id)>0;
		if(flag){
			arrivalOrderMapper.deleteArrivalOrderDetailByOrderid(id);
			if(StringUtils.isNotEmpty(arrivalOrder.getBillno())){
				storageForPurchaseService.updatePurchaseEnterRefer("0", arrivalOrder.getBillno());
			}
		}
		return flag;
	}
	@Override
	public ArrivalOrder showPureArrivalOrder(String id) throws Exception{
		return arrivalOrderMapper.getArrivalOrder(id);
	}
	@Override
	public ArrivalOrder showArrivalOrder(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String cols = getAccessColumnList("t_purchase_arrivalorder",null);
		String datasql = getDataAccessRule("t_purchase_arrivalorder",null);
		map.put("cols", cols);
		map.put("authDataSql", datasql);
		ArrivalOrder arrivalOrder = arrivalOrderMapper.getArrivalOrderBy(map);
		BuySupplier buySupplier = getSupplierInfoById(arrivalOrder.getSupplierid());
		if(null != buySupplier){
			arrivalOrder.setSuppliername(buySupplier.getName());
		}
		Contacter contacter = getContacterById(arrivalOrder.getHandlerid());
		if(null != contacter){
			arrivalOrder.setHandlername(contacter.getName());
		}
		DepartMent departMent = getDepartMentById(arrivalOrder.getBuydeptid());
		if(null != departMent){
			arrivalOrder.setBuydeptname(departMent.getName());
		}
		Personnel personnel = getPersonnelById(arrivalOrder.getBuyuserid());
		if(null != personnel){
			arrivalOrder.setBuyusername(personnel.getName());
		}
		return arrivalOrder;
	}
	@Override
	public ArrivalOrder showArrivalOrderByDataAuth(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String datasql = getDataAccessRule("t_purchase_arrivalorder",null);
		map.put("authDataSql", datasql);		
		return arrivalOrderMapper.getArrivalOrderBy(map);		
	}
	
	@Override
	public PageData showArrivalOrderPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_purchase_arrivalorder",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_purchase_arrivalorder",null);
		pageMap.setDataSql(sql);
		List<ArrivalOrder> porderList=arrivalOrderMapper.getArrivalOrderPageList(pageMap);
		BigDecimal tmp=new BigDecimal(0);
		if(porderList!=null && porderList.size()>0){
			DepartMent departMent =null;
			Personnel personnel = null;
			BuySupplier buySupplier=null;
			Contacter contacter =null;
			Map map=null;
			for(ArrivalOrder item : porderList){
				item.setOrdertypename("采购进货单");
				if(StringUtils.isNotEmpty(item.getHandlerid())){
					personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getHandlerid());
					if(null!=personnel){
						item.setHandlername(personnel.getName());
					}
				}
				if(StringUtils.isNotEmpty(item.getBuydeptid())){
					departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getBuydeptid());
					if(null!=departMent){
						item.setBuydeptname(departMent.getName());
					}					
				}
				if(StringUtils.isNotEmpty(item.getBuyuserid())){
					personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getBuyuserid());
					if(null!=personnel){
						item.setBuyusername(personnel.getName());
					}
				}
				if(StringUtils.isNotEmpty(item.getSupplierid())){
					buySupplier = getSupplierInfoById(item.getSupplierid());
					if(buySupplier != null){
						item.setSuppliername(buySupplier.getName());
					}
				}
//				if(StringUtils.isNotEmpty(item.getSupplierid())){
//					buySupplier=getSupplierInfoById(item.getSupplierid());
//					if(null!=buySupplier){
//						item.setSuppliername(buySupplier.getName());
//					}
//				}
				if(StringUtils.isNotEmpty(item.getHandlerid())){
					map=new HashMap();
					map.put("id", item.getHandlerid());
					contacter = getBaseFilesContacterMapper().getContacterDetail(map);
					if(contacter != null){
						item.setHandlername(contacter.getName());
					}
				}
				Map total = arrivalOrderMapper.getArrivalOrderDetailTotal(item.getId());
				if(null!=total){
					if(total.containsKey("taxamount")){
						tmp=(BigDecimal)total.get("taxamount");
						if(null==tmp){
							tmp=BigDecimal.ZERO;
						}
						item.setField01(tmp.toString());
					}
					if(total.containsKey("notaxamount")){
						tmp=(BigDecimal)total.get("notaxamount");
						if(null==tmp){
							tmp=BigDecimal.ZERO;
						}
						item.setField02(tmp.toString());
					}
					if(total.containsKey("tax")){
						tmp=(BigDecimal)total.get("tax");
						if(null==tmp){
							tmp=BigDecimal.ZERO;
						}
						item.setField03(tmp.toString());
					}
					if(total.containsKey("oldtaxamount")){
						item.setOldtaxamount(total.get("oldtaxamount").toString());
					}
					if(total.containsKey("oldnotaxamount")){
						item.setOldnotaxamount(total.get("oldnotaxamount").toString());
					}
					if(total.containsKey("oldtax")){
						item.setOldtax(total.get("oldtax").toString());
					}
					if(total.containsKey("changeamount")){
						item.setChangeamount(total.get("changeamount").toString());
					}
				}
			}
		}
		PageData pageData=new PageData(arrivalOrderMapper.getArrivalOrderPageCount(pageMap), 
						porderList, pageMap);
		Map footMap =arrivalOrderMapper.getArrivalOrderDetailTotalSum(pageMap);
		if(null!=footMap){
			List<ArrivalOrder> footList=new ArrayList<ArrivalOrder>();
//			ArrivalOrder selectFoot=new ArrivalOrder();
//			selectFoot.setId("选中金额");
//			selectFoot.setSource("-999");
//			selectFoot.setIsinvoice("-999");
//			footList.add(selectFoot);
			ArrivalOrder foot =new ArrivalOrder();
			foot.setId("合计金额");
			foot.setSource("-999");
			foot.setIsinvoice("-999");
			if(footMap.containsKey("taxamount")){
				tmp=(BigDecimal)footMap.get("taxamount");
				if(null==tmp){
					tmp=BigDecimal.ZERO;
				}
				foot.setField01(tmp.toString());
			}
			if(footMap.containsKey("notaxamount")){
				tmp=(BigDecimal)footMap.get("notaxamount");
				if(null==tmp){
					tmp=BigDecimal.ZERO;
				}
				foot.setField02(tmp.toString());
			}
			if(footMap.containsKey("tax")){
				tmp=(BigDecimal)footMap.get("tax");
				if(null==tmp){
					tmp=BigDecimal.ZERO;
				}
				foot.setField03(tmp.toString());
			}
			footList.add(foot);
			pageData.setFooter(footList);
		}
		return pageData;
	}
	@Override
	public boolean updateArrivalOrderBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_purchase_arrivalorder",null);
		map.put("authDataSql", sql);
		return arrivalOrderMapper.updateArrivalOrderBy(map)>0;
	}
	/**
	 * 审核<br/>
	 * 根据系统参数设置，采购入库单审核时，判断采购进货单是否可以自动审核<br/>
	 * 注意：如果修改了审核，请查看对应的PurchaseForStorageServiceImpl.addCreateArrivalOrderByEnterAudit是否需要修改
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	@Override
	public Map auditArrivalOrder(String id) throws Exception{
		Map map=new HashMap();
		Boolean flag=false;
		ArrivalOrder arrivalOrder=showArrivalOrderByDataAuth(id);
		if("2".equals(arrivalOrder.getStatus())){
			map.put("orderid", id);
			int acount=arrivalOrderMapper.getArrivalOrderDetailCountBy(map);
			map.clear();
			if(acount==0){
				map.put("flag", false);
				map.put("msg", "未能找到进货的商品信息!");
				return map;
			}
			ArrivalOrder upaOrder=new ArrivalOrder();
			SysUser sysUser=getSysUser();
			upaOrder.setId(id);
			upaOrder.setBusinessdate(arrivalOrder.getBusinessdate());
			upaOrder.setStatus("3");
			upaOrder.setAudituserid(sysUser.getUserid());
			upaOrder.setAuditusername(sysUser.getName());
			upaOrder.setAudittime(new Date());
			upaOrder.setBusinessdate(getAuditBusinessdate(arrivalOrder.getBusinessdate()));
			flag=arrivalOrderMapper.updateArrivalOrderStatus(upaOrder)>0;
			if(flag){
				List<ArrivalOrderDetail> detailList=arrivalOrderMapper.getArrivalOrderDetailListByOrderid(arrivalOrder.getId());
				
				if("1".equals(arrivalOrder.getSource())||"2".equals(arrivalOrder.getSource()) ){
					
					if(StringUtils.isNotEmpty(arrivalOrder.getBillno())){
						storageForPurchaseService.updatePurchaseEnterClose(arrivalOrder.getBillno());
						storageForPurchaseService.updatePurchaseEnterDetailPrice(arrivalOrder,detailList);
					}
				}

				GoodsInfo goodsInfo=null;
				GoodsInfo upGoodsInfo=null;
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
		map.put("flag", flag);
		map.put("msg", "");
		return map;
	}
	/**
	 * 反审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	@Override
	public Map oppauditArrivalOrder(String id) throws Exception{
		Map map=new HashMap();
		Boolean flag=false;
		ArrivalOrder arrivalOrder=showArrivalOrderByDataAuth(id);
		if("3".equals(arrivalOrder.getStatus()) && !"1".equals(arrivalOrder.getIsinvoice()) && !"1".equals(arrivalOrder.getIsrefer())){
			ArrivalOrder upaOrder=new ArrivalOrder();
			SysUser sysUser=getSysUser();
			upaOrder.setId(id);
			upaOrder.setStatus("2");
			upaOrder.setAudituserid(sysUser.getUserid());
			upaOrder.setAuditusername(sysUser.getName());
			upaOrder.setAudittime(new Date());
			flag=arrivalOrderMapper.updateArrivalOrderStatus(upaOrder)>0;
			if(flag){
                List<ArrivalOrderDetail> detailList=arrivalOrderMapper.getArrivalOrderDetailListByOrderid(arrivalOrder.getId());
				storageForPurchaseService.updatePurchaseEnterOpen(arrivalOrder.getBillno());
                //重新更新成本价
                storageForPurchaseService.updatePurchaseEnterDetailPriceByOppaudit(arrivalOrder,detailList);
			}
			map.put("flag", true);
		}else{
			map.put("flag", flag);
			map.put("msg", "抱歉，反审失败，因为该进货单已经被采购发票引用");
		}
		return map;
	}
	@Override
	public boolean auditWorkflowArrivalOrder(String id) throws Exception{
		Boolean flag=false;
		ArrivalOrder arrivalOrder=showArrivalOrderByDataAuth(id);
		if("2".equals(arrivalOrder.getStatus())){
			ArrivalOrder upaOrder=new ArrivalOrder();
			SysUser sysUser=getSysUser();
			upaOrder.setId(id);
			upaOrder.setStatus("3");
			upaOrder.setAudituserid(sysUser.getUserid());
			upaOrder.setAuditusername(sysUser.getName());
			upaOrder.setAudittime(new Date());
			flag=arrivalOrderMapper.updateArrivalOrderStatus(upaOrder)>0;
		}
		return flag;
	}
	
	@Override
	public ArrivalOrder showArrivalOrderAndDetailByBillno(String billno) throws Exception{
		ArrivalOrder arrivalOrder=new ArrivalOrder();
		Map map=storageForPurchaseService.getPurchaseEnterByID(billno);
		PurchaseEnter purchaseEnter=null;
		List<PurchaseEnterDetail> pedList=null;
		ArrivalOrderDetail arrivalOrderDetail=null;
		List<ArrivalOrderDetail> list=new ArrayList<ArrivalOrderDetail>();
		TaxType taxType=null;
		if(null!=map){
			if(map.containsKey("purchaseEnter")){
				purchaseEnter=(PurchaseEnter) map.get("purchaseEnter");
				if(null!=purchaseEnter){
					arrivalOrder.setBillno(billno);
					arrivalOrder.setBusinessdate(purchaseEnter.getBusinessdate());
					arrivalOrder.setBuydeptid(purchaseEnter.getBuydeptid());
					arrivalOrder.setBuyuserid(purchaseEnter.getBuyuserid());
					arrivalOrder.setBillno(purchaseEnter.getId());
					arrivalOrder.setSource("1");
					arrivalOrder.setHandlerid(purchaseEnter.getHandlerid());
					arrivalOrder.setPaytype(purchaseEnter.getPaytype());
					arrivalOrder.setSettletype(purchaseEnter.getSettletype());
					arrivalOrder.setStorageid(purchaseEnter.getStorageid());
					arrivalOrder.setSupplierid(purchaseEnter.getSupplierid());

					arrivalOrder.setAdddeptid(purchaseEnter.getAdddeptid());
					arrivalOrder.setAdddeptname(purchaseEnter.getAdddeptname());
					arrivalOrder.setAdduserid(purchaseEnter.getAdduserid());
					arrivalOrder.setAddusername(purchaseEnter.getAddusername());
					BuySupplier buySupplier = getSupplierInfoById(purchaseEnter.getSupplierid());
					if(null!=buySupplier){
						arrivalOrder.setSuppliername(buySupplier.getName());
					}
					arrivalOrder.setField01(purchaseEnter.getField01());
					arrivalOrder.setField02(purchaseEnter.getField02());
					arrivalOrder.setField03(purchaseEnter.getField03());
					arrivalOrder.setField04(purchaseEnter.getField04());
					arrivalOrder.setField05(purchaseEnter.getField05());
					arrivalOrder.setField06(purchaseEnter.getField06());
					arrivalOrder.setField07(purchaseEnter.getField07());
					arrivalOrder.setField08(purchaseEnter.getField08());
				}
			}
			if(map.containsKey("detailList")){
				pedList=(List<PurchaseEnterDetail>) map.get("detailList");
				if(null!=pedList){
					for(PurchaseEnterDetail item : pedList){
						arrivalOrderDetail=new ArrivalOrderDetail();
						arrivalOrderDetail.setGoodsid(item.getGoodsid());
						arrivalOrderDetail.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
						arrivalOrderDetail.setArrivedate(item.getArrivedate());
						arrivalOrderDetail.setAuxnum(item.getAuxnum());
						arrivalOrderDetail.setAuxnumdetail(item.getAuxnumdetail());
						arrivalOrderDetail.setAuxunitid(item.getAuxunitid());
						arrivalOrderDetail.setAuxunitname(item.getAuxunitname());
						arrivalOrderDetail.setAuxremainder(item.getAuxremainder());
						arrivalOrderDetail.setBatchno(item.getBatchno());
						arrivalOrderDetail.setBilldetailno(item.getId().toString());
						arrivalOrderDetail.setBillno(item.getPurchaseenterid());
						arrivalOrderDetail.setDeadline(item.getDeadline());
						arrivalOrderDetail.setField01(item.getField01());
						arrivalOrderDetail.setField02(item.getField02());
						arrivalOrderDetail.setField03(item.getField03());
						arrivalOrderDetail.setField04(item.getField04());
						arrivalOrderDetail.setField05(item.getField05());
						arrivalOrderDetail.setField06(item.getField06());
						arrivalOrderDetail.setField07(item.getField07());
						arrivalOrderDetail.setField08(item.getField08());
						arrivalOrderDetail.setProduceddate(item.getProduceddate());
						arrivalOrderDetail.setRemark(item.getRemark());
						arrivalOrderDetail.setTax(item.getTax());
						arrivalOrderDetail.setTaxamount(item.getTaxamount());
						arrivalOrderDetail.setTaxprice(item.getTaxprice());
						arrivalOrderDetail.setTaxtype(item.getTaxtype());
						if(StringUtils.isNotEmpty(item.getTaxtype())){
							taxType=getTaxType(item.getTaxtype());
							if(null!=taxType){
								arrivalOrderDetail.setTaxtypename(taxType.getName());
							}
						}
						arrivalOrderDetail.setUnitid(item.getUnitid());
						arrivalOrderDetail.setUnitname(item.getUnitname());
						arrivalOrderDetail.setUnitnum(item.getUnitnum());
						arrivalOrderDetail.setNotaxprice(item.getNotaxprice());
						arrivalOrderDetail.setNotaxamount(item.getNotaxamount());
						list.add(arrivalOrderDetail);
					}
				}
			}
			arrivalOrder.setArrivalOrderDetailList(list);
		}
		return arrivalOrder;
	}
	
	@Override
	public boolean updateArrivalOrderRefer(String id,String isrefer) throws Exception{
		return updateBaseArrivalOrderRefer(id,isrefer);
	}
	@Override
	public boolean updateArrivalOrderStatus(ArrivalOrder arrivalOrder) throws Exception{
		return arrivalOrderMapper.updateArrivalOrderStatus(arrivalOrder)>0;
	}
	@Override
	public boolean deleteArrivalOrder(String id) throws Exception{
		return arrivalOrderMapper.deleteArrivalOrder(id)>0;
	}

	//------------------------------------------------------------------------//
	//采购进货单详细
	//-----------------------------------------------------------------------//

	@Override
	public boolean insertArrivalOrderDetail(ArrivalOrderDetail arrivalOrderDetail) throws Exception{
		return arrivalOrderMapper.insertArrivalOrderDetail(arrivalOrderDetail)>0;
	}

	@Override
	public List showArrivalOrderDetailListByOrderId(String orderid) throws Exception{
		if(null==orderid || "".equals(orderid)){
			return new ArrayList<ArrivalOrderDetail>();
		}
		orderid=orderid.trim();
		Map map=new HashMap();
		map.put("orderid", orderid);
		String cols = getAccessColumnList("t_purchase_arrivalorder_detail",null);
		map.put("cols", cols);
		String sql = getDataAccessRule("t_purchase_arrivalorder_detail",null);
		map.put("authDataSql", sql);
		List<ArrivalOrderDetail> list= arrivalOrderMapper.getArrivalOrderDetailListBy(map);
		if(null!=list && list.size()>0){
			TaxType taxType=null;
			MeteringUnit meteringUnit=null;
			for(ArrivalOrderDetail item :list){
				if(null!=item ){
					if(StringUtils.isNotEmpty(item.getGoodsid())){
						item.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
					}
					if(StringUtils.isNotEmpty(item.getTaxtype())){
						taxType=getTaxType(item.getTaxtype());
						if(null!=taxType){
							item.setTaxtypename(taxType.getName());
						}
					}
					if(StringUtils.isNotEmpty(item.getAuxunitid())){
						meteringUnit=getMeteringUnitById(item.getAuxunitid());
						if(null!=meteringUnit){
							item.setAuxunitname(meteringUnit.getName());
						}
					}
				}
			}
		}
		return list;
	}
	/**
	 * 根据采购进货单编号，获取采购进货单明细分页列表，不判断权限
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	@Override
	public List showPureArrivalOrderDetailListByOrderId(String orderid) throws Exception{
		orderid=orderid.trim();
		Map map=new HashMap();
		map.put("orderid", orderid);
		return arrivalOrderMapper.getArrivalOrderDetailListBy(map);
	}

	@Override
	public boolean deleteArrivalOrderDetailByOrderid(String orderid) throws Exception{
		return arrivalOrderMapper.deleteArrivalOrderDetailByOrderid(orderid)>0;
	}
	/**
	 * 更新采购明细
	 * @param arrivalOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	@Override
	public int updateArrivalOrderDetail(ArrivalOrderDetail arrivalOrderDetail) throws Exception{
		return arrivalOrderMapper.updateArrivalOrderDetail(arrivalOrderDetail);
	}
	@Override
	public ArrivalOrderDetail showPureArrivalOrderDetail(String id) throws Exception{
		return arrivalOrderMapper.getArrivalOrderDetail(id);
	}
	
	@Override
	public List showArrivalOrderDetailUpReferList(String billno) throws Exception{
		ArrivalOrder arrivalOrder=new ArrivalOrder();
		Map map=storageForPurchaseService.getPurchaseEnterByID(billno);
		List<PurchaseEnterDetail> pedList=null;
		ArrivalOrderDetail arrivalOrderDetail=null;
		List<ArrivalOrderDetail> list=new ArrayList<ArrivalOrderDetail>();
		TaxType taxType=null;
		if(map.containsKey("detailList")){
			pedList=(List<PurchaseEnterDetail>) map.get("detailList");
			if(null!=pedList){
				for(PurchaseEnterDetail item : pedList){
					arrivalOrderDetail=new ArrivalOrderDetail();
					arrivalOrderDetail.setGoodsid(item.getGoodsid());
					arrivalOrderDetail.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
					arrivalOrderDetail.setArrivedate(item.getArrivedate());
					arrivalOrderDetail.setAuxnum(item.getAuxnum());
					arrivalOrderDetail.setAuxnumdetail(item.getAuxnumdetail());
					arrivalOrderDetail.setAuxunitid(item.getAuxunitid());
					arrivalOrderDetail.setAuxunitname(item.getAuxunitname());
					arrivalOrderDetail.setBatchno(item.getBatchno());
					arrivalOrderDetail.setBilldetailno(item.getId().toString());
					arrivalOrderDetail.setBillno(item.getPurchaseenterid());
					arrivalOrderDetail.setDeadline(item.getDeadline());
					arrivalOrderDetail.setField01(item.getField01());
					arrivalOrderDetail.setField02(item.getField02());
					arrivalOrderDetail.setField03(item.getField03());
					arrivalOrderDetail.setField04(item.getField04());
					arrivalOrderDetail.setField05(item.getField05());
					arrivalOrderDetail.setField06(item.getField06());
					arrivalOrderDetail.setField07(item.getField07());
					arrivalOrderDetail.setField08(item.getField08());
					arrivalOrderDetail.setProduceddate(item.getProduceddate());
					arrivalOrderDetail.setRemark(item.getRemark());
					arrivalOrderDetail.setTax(item.getTax());
					arrivalOrderDetail.setTaxamount(item.getTaxamount());
					arrivalOrderDetail.setTaxprice(item.getTaxprice());
					arrivalOrderDetail.setTaxtype(item.getTaxtype());
					if(StringUtils.isNotEmpty(item.getTaxtype())){
						taxType=getTaxType(item.getTaxtype());
						if(null!=taxType){
							arrivalOrderDetail.setTaxtypename(taxType.getName());
						}
					}
					arrivalOrderDetail.setUnitid(item.getUnitid());
					arrivalOrderDetail.setUnitname(item.getUnitname());
					arrivalOrderDetail.setUnitnum(item.getUnitnum());
					arrivalOrderDetail.setNotaxprice(item.getNotaxprice());
					arrivalOrderDetail.setNotaxamount(item.getNotaxamount());
					list.add(arrivalOrderDetail);
				}
			}
		}
		return list;
	}

	@Override
	public List addArrivalOrderDiscount(Map map) throws Exception {
		if(!map.isEmpty()){
			String arrivalorderid = (String)map.get("arrivalorderid");
			String repartitiontype = (String)map.get("repartitiontype");
			BigDecimal totalDiscountAmount = null != map.get("taxamount") ? new BigDecimal((String)map.get("taxamount")) : BigDecimal.ZERO;

			List<ArrivalOrderDetail> detailList = arrivalOrderMapper.getArrivalOrderDetailListByOrderid(arrivalorderid);
			BigDecimal totalamount = BigDecimal.ZERO;
			BigDecimal totalunitnum = BigDecimal.ZERO;
			BigDecimal totalboxSum = BigDecimal.ZERO;
			for(ArrivalOrderDetail detail : detailList){
				totalamount = totalamount.add(detail.getTaxamount());
				totalunitnum = totalunitnum.add(detail.getUnitnum());
				totalboxSum = totalboxSum.add(detail.getTotalbox());
			}
			for(ArrivalOrderDetail detail : detailList){
				//计算平摊到商品中的 各折扣金额
				BigDecimal discountamount = BigDecimal.ZERO;
				if("0".equals(repartitiontype)){//金额
					discountamount = detail.getTaxamount().divide(totalamount, 6, BigDecimal.ROUND_HALF_UP).multiply(totalDiscountAmount);
				}else if("1".equals(repartitiontype)){//数量
					discountamount = detail.getUnitnum().divide(totalunitnum, 6, BigDecimal.ROUND_HALF_UP).multiply(totalDiscountAmount);
				}else if("2".equals(repartitiontype)){//箱数
					discountamount = detail.getTotalbox().divide(totalboxSum, 6, BigDecimal.ROUND_HALF_UP).multiply(totalDiscountAmount);
				}
				BigDecimal amountByDiscount = detail.getTaxamount().add(discountamount);
				detail.setTaxamount(amountByDiscount);
				BigDecimal taxprice = amountByDiscount.divide(detail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
				detail.setTaxprice(taxprice);
				BigDecimal notaxprice = new BigDecimal(0);
				TaxType taxType=getTaxType(detail.getTaxtype());
				if(null != taxType){
					BigDecimal rate = taxType.getRate().divide(new BigDecimal(100));
					BigDecimal taxRate = new BigDecimal(1).add(rate);
					notaxprice = taxprice.divide(taxRate, 6, BigDecimal.ROUND_HALF_UP);
				}else{
					notaxprice = taxprice.divide(new BigDecimal(1.17));
				}
				detail.setNotaxprice(notaxprice);

				//含税金额 = 含税单价*数量
				detail.setTaxamount(detail.getTaxprice().multiply(detail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				//未税金额 = 无税单价*数量
				detail.setNotaxamount(detail.getNotaxprice().multiply(detail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				//税额 = 含税金额-未税金额
				detail.setTax(detail.getTaxamount().subtract(detail.getNotaxamount()));

				GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
				if (null != goodsInfo){
					detail.setGoodsInfo(goodsInfo);
					detail.setNoboxprice(notaxprice.multiply(goodsInfo.getBoxnum()));
					detail.setBoxprice(goodsInfo.getBoxnum().multiply(detail.getTaxprice() == null ? BigDecimal.ZERO : detail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				}
			}
			return detailList;
		}
		return null;
	}

	@Override
	public List showArrivalOrderDetailDownReferList(String orderidarrs) throws Exception{
		Map map=new HashMap();
		map.put("orderidarrs", orderidarrs);
		map.put("notinvoicerefer", "1");
		List<ArrivalOrderDetail> list=arrivalOrderMapper.getArrivalOrderDetailListBy(map);
		TaxType taxType=null;
		if(null!=list && list.size()>0){
			for(ArrivalOrderDetail item : list){
				item.setOrdertype("1");
				if(StringUtils.isNotEmpty(item.getGoodsid())){
					item.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
				}
				if(StringUtils.isNotEmpty(item.getTaxtype())){
					taxType=getTaxType(item.getTaxtype());
					if(null!=taxType){
						item.setTaxtypename(taxType.getName());
					}
				}
				BigDecimal bNum = (null != item.getUninvoicenum())?item.getUninvoicenum() : new BigDecimal(0);//未开票数量
				
				//if((bNum.compareTo(new BigDecimal(0)) == 1)?true : false && item.getUnitnum().compareTo(bNum) != 0){
				if(BigDecimal.ZERO.compareTo(bNum) == -1 && item.getUnitnum().compareTo(bNum) != 0){	
					//主数量
					item.setUnitnum(item.getUninvoicenum());
					
					Map countMap=countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), bNum);
					if(countMap.containsKey("auxnumdetail")){
						item.setAuxnumdetail((String)countMap.get("auxnumdetail"));
					}
					
					
					//含税金额 = 含税单价*数量
					item.setTaxamount(item.getTaxprice().multiply(item.getUninvoicenum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//未税金额 = 无税单价*数量
					item.setNotaxamount(item.getNotaxprice().multiply(item.getUninvoicenum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//税额 = 含税金额-未税金额
					item.setTax(item.getTaxamount().subtract(item.getNotaxamount()));
				}
			}
		}
		return list;
	}
	@Override
	public boolean submitArrivalOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception{
		return false;
	}

	@Override
	public ArrivalOrderDetail getArrivalOrderDetailByOrderidAndGoodsId(String orderid,
			String goodsid) throws Exception {
		ArrivalOrderDetail arrivalOrderDetail = arrivalOrderMapper.getArrivalOrderDetailByOrderidAndGoodsId(orderid,goodsid);
		return arrivalOrderDetail;
	}
	
	@Override
	public ArrivalOrder getArrivalOrderByPrint(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String cols = getAccessColumnList("t_purchase_arrivalorder",null);
		String datasql = getDataAccessRule("t_purchase_arrivalorder",null);
		map.put("cols", cols);
		map.put("authDataSql", datasql);
		ArrivalOrder arrivalOrder= arrivalOrderMapper.getArrivalOrderBy(map);
		if(null!=arrivalOrder){
			BuySupplier buySupplier = getSupplierInfoById(arrivalOrder.getSupplierid());
			if(null != buySupplier){
				arrivalOrder.setSuppliername(buySupplier.getName());
			}
		}
		List<ArrivalOrderDetail> list=showPureArrivalOrderDetailListByOrderId(id);
		if(null!=list && list.size()>0){
			for(ArrivalOrderDetail item :list){
				if(null!=item ){
					if(StringUtils.isNotEmpty(item.getGoodsid())){
						item.setGoodsInfo(getGoodsInfoByID(item.getGoodsid()));
					}
					if(StringUtils.isNotEmpty(item.getTaxtype())){
						TaxType	taxType=getTaxType(item.getTaxtype());
						if(null!=taxType){
							item.setTaxtypename(taxType.getName());
						}
					}
					if(StringUtils.isNotEmpty(item.getAuxunitid())){
						MeteringUnit meteringUnit=getMeteringUnitById(item.getAuxunitid());
						if(null!=meteringUnit){
							item.setAuxunitname(meteringUnit.getName());
						}
					}
				}
			}
			arrivalOrder.setArrivalOrderDetailList(list);
		}else{
			arrivalOrder.setArrivalOrderDetailList(new ArrayList<ArrivalOrderDetail>());
		}
		return arrivalOrder;
	}
	

	@Override
	public boolean updateOrderPrinttimes(ArrivalOrder arrivalOrder) throws Exception{
		return arrivalOrderMapper.updateOrderPrinttimes(arrivalOrder)>0;
	}
	@Override
	public void updateOrderPrinttimes(List<ArrivalOrder> list) throws Exception{
		if(null!=list){
			for(ArrivalOrder item : list){
				arrivalOrderMapper.updateOrderPrinttimes(item);
			}
		}
	}
	@Override
	public List showArrivalOrderListBy(Map map) throws Exception{
		String datasql = getDataAccessRule("t_purchase_arrivalorder",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<ArrivalOrder> list=arrivalOrderMapper.showArrivalOrderListBy(map);
		for(ArrivalOrder item : list){
			if(StringUtils.isNotEmpty(item.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier){
					item.setBuySupplierInfo(buySupplier);
					item.setSuppliername(buySupplier.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getStorageid())){
				StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo){
					item.setStoragename(storageInfo.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getBuydeptid())){
				DepartMent departMent=getDepartmentByDeptid(item.getBuydeptid());
				if(null!=departMent){
					item.setBuydeptname(departMent.getName());
				}
			}
			if(showdetail){
				List<ArrivalOrderDetail> detailList=showPureArrivalOrderDetailListByOrderId(item.getId());
				if(null!=list && list.size()>0){
					for(ArrivalOrderDetail detail :detailList){
						if(null!=detail ){
							if(StringUtils.isNotEmpty(detail.getGoodsid())){
								detail.setGoodsInfo(getGoodsInfoByID(detail.getGoodsid()));
							}
							if(StringUtils.isNotEmpty(detail.getTaxtype())){
								TaxType	taxType=getTaxType(detail.getTaxtype());
								if(null!=taxType){
									detail.setTaxtypename(taxType.getName());
								}
							}
							if(StringUtils.isNotEmpty(detail.getAuxunitid())){
								MeteringUnit meteringUnit=getMeteringUnitById(detail.getAuxunitid());
								if(null!=meteringUnit){
									detail.setAuxunitname(meteringUnit.getName());
								}
							}
						}
					}
					item.setArrivalOrderDetailList(detailList);
				}
			}
		}
		return list;
	}
	@Override
	public boolean updateArrivalOrderDetailRemark(ArrivalOrderDetail arrivalOrderDetail) throws Exception{
		return arrivalOrderMapper.updateArrivalOrderDetailRemark(arrivalOrderDetail)>0;
	}

    @Override
    public boolean updateArrivalAndPurchaseEnterUncancel(List<String> sourceidList, PurchaseInvoice purchaseInvoice) throws Exception {
        //更新采购进货单明细的核销状态
        arrivalOrderMapper.updateArrivalOrderDetailUncancel(purchaseInvoice.getId());
        //更新采购入库单明细的核销状态
        storageForPurchaseService.updatePurchaseEnterDetailUncancel(purchaseInvoice.getId());

        //更新采购退货通知单明细的核销状态
        returnOrderMapper.updateReturnOrderDetailUncancel(purchaseInvoice.getId());
        //更新采购退货出库单明细的核销状态
        storageForPurchaseService.updatePurchaseRejectDetailUncancel(purchaseInvoice.getId());

        //更新单据核销状态
        for(String billid :sourceidList){
            ArrivalOrder arrivalOrder = arrivalOrderMapper.getArrivalOrder(billid);
            if(null != arrivalOrder){//采购进货单
                int invoiceCount=0; //开票
                int mvoiceCount=0; //开票中
                int uninvoiceCount=0; //未开票
                List<ArrivalOrderDetail> detailList = arrivalOrderMapper.getArrivalOrderDetailListByOrderid(billid);
                if(detailList.size() != 0){
                    for(ArrivalOrderDetail arrivalOrderDetail : detailList){
                        if(arrivalOrderDetail.getUnitnum().compareTo(arrivalOrderDetail.getInvoicenum()) == 0){
                            invoiceCount=invoiceCount+1;
                            storageForPurchaseService.updatePurchaseEnterDetailIsinvoice(arrivalOrderDetail.getBillno(), arrivalOrderDetail.getBilldetailno(), "1");
                        }else if(arrivalOrderDetail.getUnitnum().compareTo(arrivalOrderDetail.getUninvoicenum()) == 0){
                            uninvoiceCount=uninvoiceCount+1;
                            storageForPurchaseService.updatePurchaseEnterDetailIsinvoice(arrivalOrderDetail.getBillno(), arrivalOrderDetail.getBilldetailno(), "0");
                        }else{
                            mvoiceCount=mvoiceCount+1;
                        }
                    }
                }
                //1已开票，0未票,3开票中
                if(invoiceCount>0 && uninvoiceCount==0 && mvoiceCount==0){
                    arrivalOrderMapper.updateArrivalOrderInvoice("1", null, billid);//已开票
                }else if(invoiceCount>0 && uninvoiceCount>0 || mvoiceCount>0){
                    arrivalOrderMapper.updateArrivalOrderInvoice("3", null, billid);//开票中
                }else {
                    arrivalOrderMapper.updateArrivalOrderInvoice("0", null, billid);//未开票
                }
                ArrivalOrder arrivalOrder1 = new ArrivalOrder();
                arrivalOrder1.setId(arrivalOrder.getId());
                arrivalOrder1.setStatus("3");
                arrivalOrderMapper.updateArrivalOrderStatus(arrivalOrder1);
            }else{//采购退货通知单
                ReturnOrder returnOrder = getReturnOrderMapper().getReturnOrder(billid);
                if(null != returnOrder){
                    int invoiceCount=0; //开票
                    int mvoiceCount=0; //开票中
                    int uninvoiceCount=0; //未开票
                    List<ReturnOrderDetail> detailList = returnOrderMapper.getReturnOrderDetailListByOrderid(billid);
                    if(detailList.size() != 0){
                        for(ReturnOrderDetail returnOrderDetail : detailList){
                            if(returnOrderDetail.getUnitnum().compareTo(returnOrderDetail.getInvoicenum()) == 0){
                                invoiceCount=invoiceCount+1;
                                storageForPurchaseService.updatePurchaseEnterDetailIsinvoice(returnOrderDetail.getBillno(), returnOrderDetail.getBilldetailno(), "1");
                            }else if(returnOrderDetail.getUnitnum().compareTo(returnOrderDetail.getUninvoicenum()) == 0){
                                uninvoiceCount=uninvoiceCount+1;
                                storageForPurchaseService.updatePurchaseEnterDetailIsinvoice(returnOrderDetail.getBillno(), returnOrderDetail.getBilldetailno(), "0");
                            }else{
                                mvoiceCount=mvoiceCount+1;
                            }
                        }
                    }
                    if(invoiceCount>0 && uninvoiceCount==0 && mvoiceCount==0){
                        returnOrderMapper.updateReturnOrderInvoice("1", null, billid);//已开票
                    }else if(invoiceCount>0 && uninvoiceCount>0 || mvoiceCount>0){
                        returnOrderMapper.updateReturnOrderInvoice("3", null, billid);//开票中
                    }else{
                        returnOrderMapper.updateReturnOrderInvoice("4", null, billid);//未开票
                    }
                    ReturnOrder returnOrder1 = new ReturnOrder();
                    returnOrder1.setId(returnOrder.getId());
                    returnOrder1.setStatus("3");
                    returnOrderMapper.updateReturnOrder(returnOrder1);
                }else{
					BeginDue beginDue = beginDueMapper.getBeginDueByID(billid);
					if(null != beginDue){
						beginDue.setStatus("3");
						beginDue.setIswriteoff("0");
						beginDue.setWriteoffamount(BigDecimal.ZERO);
						beginDue.setWriteoffnotaxamount(BigDecimal.ZERO);
						beginDue.setWriteoffdate("");

						beginDue.setWriteoffuserid("");
						beginDue.setWriteoffusername("");
						beginDueMapper.updateBeginDueWriteBack(beginDue);
					}
				}
            }
        }
        return true;
    }

    /**
     * 根据采购进货单编号 获取供应商的汇总金额
     *
     * @param idarr
     * @return
     */
    @Override
    public List<Map> getSupplierArrivalSumData(List idarr) {
        if(null!=idarr && idarr.size()>0){
            List<Map> list = arrivalOrderMapper.getSupplierArrivalSumData(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }

	/**
	 * 根据采购进货单编号 获取供应商的汇总金额
	 *
	 * @param idarr
	 * @return
	 */
	@Override
	public List<Map> getSupplierArrivalSumDataForThird(List idarr) {
		if(null!=idarr && idarr.size()>0){
			List<Map> list = arrivalOrderMapper.getSupplierArrivalSumDataForThird(idarr);
			return list;
		}
		return new ArrayList<Map>();
	}
	/**
	 * 采购进货单添加分摊记录
	 * @param map
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Jan 05, 2018
	 */
	@Override
	public Map addPurchaseShareAmount(Map map) throws Exception{
		Map resMap=new HashMap();
		//采购进货单编号
		String id=(String)map.get("id");
		//分摊金额
		BigDecimal amount=new BigDecimal(map.get("amount").toString());
		//分摊类型
		String type=(String)map.get("type");
		//备注
		String remark=(String)map.get("remark");

		//分摊金额
		BigDecimal shareTotalAmount=BigDecimal.ZERO;

		List<ArrivalOrderDetail> arrivalOrderDetailList=arrivalOrderMapper.getArrivalOrderDetailListByOrderid(id);

		SysUser sysUser=getSysUser();

		for(ArrivalOrderDetail arrivalOrderDetail:arrivalOrderDetailList){
			//1按数量分摊 2按金额分摊 3按重量分摊 4按体积分摊 5按条数分摊
			if("1".equals(type)){
				shareTotalAmount=shareTotalAmount.add(arrivalOrderDetail.getUnitnum());
			}else if("2".equals(type)){
				shareTotalAmount=shareTotalAmount.add(arrivalOrderDetail.getTaxamount());
			}else if("3".equals(type)){
				GoodsInfo goodsInfo=getGoodsInfoByID(arrivalOrderDetail.getGoodsid());
				if(goodsInfo!=null){
					shareTotalAmount=shareTotalAmount.add(arrivalOrderDetail.getUnitnum().multiply(goodsInfo.getGrossweight()));
				}
			}else if("4".equals(type)){
				GoodsInfo goodsInfo=getGoodsInfoByID(arrivalOrderDetail.getGoodsid());
				if(goodsInfo!=null){
					shareTotalAmount=shareTotalAmount.add(arrivalOrderDetail.getUnitnum().multiply(goodsInfo.getSinglevolume()));
				}
			}else if("5".equals(type)){
				shareTotalAmount=shareTotalAmount.add(new BigDecimal(1));
			}
		}

		if(shareTotalAmount.compareTo(BigDecimal.ZERO)==0){
			resMap.put("flag",false);
			resMap.put("msg","分摊基数为0,不能分摊");
			return resMap;
		}
		if(remark==null){
			remark="";
		}

		if("1".equals(type)){
			remark = "按数量分配 "+remark;
		}else if("2".equals(type)){
			remark = "按金额分配 "+remark;
		}else if("3".equals(type)){
			remark = "按重量分配 "+remark;
		}else if("4".equals(type)){
			remark = "按体积分配 "+remark;
		}else if("5".equals(type)){
			remark = "按单品分配 "+remark;
		}

		//分摊金额/分摊基数=平均分摊金额
		BigDecimal shareRateAmount=amount.divide(shareTotalAmount, 6, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalShareAMount=BigDecimal.ZERO;
		for(int i=0;i<arrivalOrderDetailList.size();i++){
			ArrivalOrderDetail arrivalOrderDetail=arrivalOrderDetailList.get(i);
			BigDecimal dispense =BigDecimal.ZERO;
			if("1".equals(type)){
				dispense = arrivalOrderDetail.getUnitnum().multiply(shareRateAmount).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			}else if("2".equals(type)){
				dispense = arrivalOrderDetail.getTaxamount().multiply(shareRateAmount).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			}else if("3".equals(type)){
				GoodsInfo goodsInfo=getGoodsInfoByID(arrivalOrderDetail.getGoodsid());
				if(goodsInfo!=null){
                    dispense = arrivalOrderDetail.getUnitnum().multiply(goodsInfo.getGrossweight()).multiply(shareRateAmount).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
                }
			}else if("4".equals(type)){
				GoodsInfo goodsInfo=getGoodsInfoByID(arrivalOrderDetail.getGoodsid());
				if(goodsInfo!=null){
                    dispense = arrivalOrderDetail.getUnitnum().multiply(goodsInfo.getSinglevolume()).multiply(shareRateAmount).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
                }
			}else if("5".equals(type)){
				dispense = shareRateAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			}
			//最后一个分摊的时候，用总分摊金额减去前面的累计分摊，防止出现舍入误差
			if(i<arrivalOrderDetailList.size()-1){
				totalShareAMount=totalShareAMount.add(dispense);
			}else{
				dispense=amount.subtract(totalShareAMount);
			}

			//分摊记录数据
			ArrivalOrderChange arrivalOrderChange=new ArrivalOrderChange();
			arrivalOrderChange.setBillid(arrivalOrderDetail.getOrderid());
			arrivalOrderChange.setBillno(arrivalOrderDetail.getBillno());
			arrivalOrderChange.setTaxtype(arrivalOrderDetail.getTaxtype());
			arrivalOrderChange.setBilldetailno(arrivalOrderDetail.getBilldetailno());
			arrivalOrderChange.setGoodsid(arrivalOrderDetail.getGoodsid());
			arrivalOrderChange.setTaxamount(dispense.setScale(2,BigDecimal.ROUND_HALF_DOWN));
			BigDecimal notaxamount = getNotaxAmountByTaxAmount(dispense,arrivalOrderChange.getTaxtype()).setScale(decimalLen,BigDecimal.ROUND_HALF_DOWN);
			arrivalOrderChange.setNotaxamount(notaxamount);
			arrivalOrderChange.setTax(arrivalOrderChange.getTaxamount().subtract(arrivalOrderChange.getNotaxamount()));
			arrivalOrderChange.setRemark(remark);
			arrivalOrderChange.setAdduserid(sysUser.getUserid());
			arrivalOrderChange.setAddusername(sysUser.getUsername());
			//添加分摊记录
			int t=arrivalOrderChangeMapper.insertArrivalOrderChange(arrivalOrderChange);

			if(t>0){
				BigDecimal newtaxamount=arrivalOrderDetail.getTaxamount().add(arrivalOrderChange.getTaxamount());
				BigDecimal newnotaxamount=arrivalOrderDetail.getNotaxamount().add(arrivalOrderChange.getNotaxamount());
				BigDecimal newtax=arrivalOrderDetail.getTax().add(arrivalOrderChange.getTax());
				BigDecimal newtaxprice=newtaxamount.divide(arrivalOrderDetail.getUnitnum(),6,BigDecimal.ROUND_HALF_DOWN);
				BigDecimal newnotaxprice=newnotaxamount.divide(arrivalOrderDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_DOWN);
				arrivalOrderDetail.setTaxamount(newtaxamount);
				arrivalOrderDetail.setNotaxamount(newnotaxamount);
				arrivalOrderDetail.setTax(newtax);
				arrivalOrderDetail.setTaxprice(newtaxprice);
				arrivalOrderDetail.setNotaxprice(newnotaxprice);
				//修改采购进货单金额
				int updatenum=arrivalOrderMapper.updateArrivalOrderChange(arrivalOrderDetail);
				//分摊失败，回滚全部分摊操作
				if(updatenum==0){
					throw new Exception("采购进货单分摊金额失败");
				}
			}
		}

		resMap.put("flag",true);

		return resMap;
	}

	/**
	 * 获取采购费用分摊记录
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	@Override
	public PageData getPurchaseShareLogDate(PageMap pageMap) throws Exception{
		List<Map> list=arrivalOrderChangeMapper.getPurchaseShareLogList(pageMap);
		for(Map map:list){
			String goodsid=(String)map.get("goodsid");
			GoodsInfo goodsInfo=getGoodsInfoByID(goodsid);
			if(goodsInfo!=null){
				map.put("goodsname",goodsInfo.getName());
			}
		}
		int count=arrivalOrderChangeMapper.getPurchaseShareLogCount(pageMap);
		PageData pageData=new PageData(count,list,pageMap);
		return pageData;
	}

	/**
	 * 采购运费取消分摊
	 * @param id
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	@Override
	public Map cancelArrivalPurchaseShare(String id){
		Map resMap=new HashMap();
		//清除费用分摊日志
		arrivalOrderChangeMapper.clearArrivalOrderChangeLog(id);
		int t=arrivalOrderMapper.updateArrivalOrderChangeById(id);
		resMap.put("flag",t>0);
		return resMap;
	}
	/**
	 * 判断采购进货单是否分摊过
	 * @param id
	 * @return java.lang.Boolean
	 * @throws
	 * @author luoqiang
	 * @date Jan 08, 2018
	 */
	public Boolean isArrivalOrderShare(String id){
		int t=arrivalOrderChangeMapper.getArrivalOrderNum(id);
		return t>0;
	}
}

