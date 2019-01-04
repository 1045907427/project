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
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.model.ReturnOrderDetail;
import com.hd.agent.purchase.service.IReturnOrderService;
import com.hd.agent.sales.model.ModelOrder;
import com.hd.agent.storage.model.PurchaseRejectOut;
import com.hd.agent.storage.model.StorageDeliveryOutForJob;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IStorageForPurchaseService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

/**
 * 采购退货通知单服务实现
 * 
 * @author zhanghonghui
 */
public class ReturnOrderServiceImpl extends BasePurchaseServiceImpl implements IReturnOrderService {

	
	private IStorageForPurchaseService storageForPurchaseService;


	public IStorageForPurchaseService getStorageForPurchaseService() {
		return storageForPurchaseService;
	}

	public void setStorageForPurchaseService(
			IStorageForPurchaseService storageForPurchaseService) {
		this.storageForPurchaseService = storageForPurchaseService;
	}
	/**
	 * 添加单采购退货通知单
	 */
	private boolean insertReturnOrder(ReturnOrder returnOrder) throws Exception{
		return returnOrderMapper.insertReturnOrder(returnOrder)>0;
	}
	
	public Map addReturnOrderAddDetail(ReturnOrder returnOrder) throws Exception{
		Map map = new HashMap();
		returnOrder.setId(getBillNumber(returnOrder, "t_purchase_returnorder"));
		if(StringUtils.isEmpty(returnOrder.getId())){
			map.put("flag",false);
			return map;
		}
		boolean flag=insertReturnOrder(returnOrder);
		List<ReturnOrderDetail> list=returnOrder.getReturnOrderDetailList();
		if(flag && list!=null && list.size()>0){
			int iseq=1;
			for(ReturnOrderDetail item : list){	
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
				item.setOrderid(returnOrder.getId());
				item.setStorageid(returnOrder.getStorageid());
				item.setSeq(iseq);
				item.setUninvoicenum(item.getUnitnum());
				item.setInvoicenum(BigDecimal.ZERO);
				item.setUninvoicetaxamount(item.getTaxamount());
				insertReturnOrderDetail(item);
				iseq=iseq+1;
			}
		}
		map.put("flag",flag);
		map.put("id",returnOrder.getId());
		return map;
		
	}
	public ReturnOrder showReturnOrderAndDetail(String id) throws Exception{
		ReturnOrder pOrder= returnOrderMapper.getReturnOrder(id);
		if(null!=pOrder){
			BuySupplier buySupplier = getSupplierInfoById(pOrder.getSupplierid());
			if(null!=buySupplier){
				pOrder.setSuppliername(buySupplier.getName());
			}
			List<ReturnOrderDetail> list=showPureReturnOrderDetailListByOrderId(pOrder.getId());
			if(null!=list && list.size()>0){
				TaxType taxType=null;
				MeteringUnit meteringUnit=null;
				for(ReturnOrderDetail item :list){
					GoodsInfo goodsInfo = getGoodsInfoByID(item.getGoodsid());
					item.setGoodsInfo(goodsInfo);
					StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
					if(null!=storageInfo){
						item.setStoragename(storageInfo.getName());
					}
					StorageLocation storageLocation = getStorageLocationByID(item.getStoragelocationid());
					if(null!=storageLocation){
						item.setStoragelocationname(storageLocation.getName());
					}
					taxType = getTaxType(item.getTaxtype());
					if(null!=taxType){
						item.setTaxtypename(taxType.getName());
					}
					if(StringUtils.isNotEmpty(item.getAuxunitid())){
						meteringUnit=getMeteringUnitById(item.getAuxunitid());
						if(null!=meteringUnit){
							item.setAuxunitname(meteringUnit.getName());
						}
					}
					if(null!=goodsInfo){
						BigDecimal boxprice = goodsInfo.getBoxnum().multiply(item.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
						BigDecimal noboxprice = goodsInfo.getBoxnum().multiply(item.getNotaxprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
						item.setBoxprice(boxprice);
						item.setNoboxprice(noboxprice);
					}
				}
				pOrder.setReturnOrderDetailList(list);
			}else{
				pOrder.setReturnOrderDetailList(new ArrayList<ReturnOrderDetail>());
			}
		}
		return pOrder;
	}

	public boolean updateReturnOrderAddDetail(ReturnOrder returnOrder) throws Exception{
		Map map = new HashMap();
		map.put("id", returnOrder.getId());
		ReturnOrder oldReturnOrder = returnOrderMapper.getReturnOrderBy(map);
		if(null==oldReturnOrder || "3".equals(oldReturnOrder.getStatus()) ||"4".equals(oldReturnOrder.getStatus())){
			return false;
		}
		
		boolean flag=returnOrderMapper.updateReturnOrder(returnOrder)>0;
		if(flag){
			deleteReturnOrderDetailByOrderid(returnOrder.getId());
			List<ReturnOrderDetail> list=returnOrder.getReturnOrderDetailList();
			if(flag && list!=null && list.size()>0){
				int iseq=1;
				for(ReturnOrderDetail item : list){		
					//计算辅单位数量
					Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
					if(auxmap.containsKey("auxnum")){
						item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
					}
					item.setOrderid(returnOrder.getId());
					item.setStorageid(returnOrder.getStorageid());
					item.setSeq(iseq);
					item.setUninvoicenum(item.getUnitnum());
					item.setInvoicenum(BigDecimal.ZERO);
					item.setUninvoicetaxamount(item.getTaxamount());
					insertReturnOrderDetail(item);
					iseq=iseq+1;
				}
			}
		}
		return flag;
	}
	
	public boolean deleteReturnOrderAndDetail(String id) throws Exception{
		boolean flag=returnOrderMapper.deleteReturnOrder(id)>0;
		if(flag){
			returnOrderMapper.deleteReturnOrderDetailByOrderid(id);
		}
		return flag;
	}
	@Override
	public ReturnOrder showPureReturnOrder(String id) throws Exception{
		return returnOrderMapper.getReturnOrder(id);
	}
	@Override
	public ReturnOrder showReturnOrder(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String cols = getAccessColumnList("t_purchase_returnorder",null);
		String datasql = getDataAccessRule("t_purchase_returnorder",null);
		map.put("cols", cols);
		map.put("authDataSql", datasql);
		ReturnOrder returnOrder = returnOrderMapper.getReturnOrderBy(map);
		BuySupplier buySupplier = getSupplierInfoById(returnOrder.getSupplierid());
		if(null != buySupplier){
			returnOrder.setSuppliername(buySupplier.getName());
		}
		Contacter contacter = getContacterById(returnOrder.getHandlerid());
		if(null != contacter){
			returnOrder.setHandlername(contacter.getName());
		}
		DepartMent departMent = getDepartMentById(returnOrder.getBuydeptid());
		if(null != departMent){
			returnOrder.setBuydeptname(departMent.getName());
		}
		Personnel personnel = getPersonnelById(returnOrder.getBuyuserid());
		if(null != personnel){
			returnOrder.setBuyusername(personnel.getName());
		}
		return returnOrder;
	}
	
	@Override
	public ReturnOrder getReturnOrder(String id) throws Exception {
		Map map=new HashMap();
		map.put("id", id.trim());
		String cols = getAccessColumnList("t_purchase_returnorder",null);
		String datasql = getDataAccessRule("t_purchase_returnorder",null);
		map.put("cols", cols);
		map.put("authDataSql", datasql);
		ReturnOrder returnOrder = returnOrderMapper.getReturnOrderBy(map);
		if(null != returnOrder){
			StorageInfo storageInfo = getStorageInfoByID(returnOrder.getStorageid());
			if(null!=storageInfo){
				returnOrder.setStoragename(storageInfo.getName());
			}
			BuySupplier buySupplier = getSupplierInfoById(returnOrder.getSupplierid());
			if(null != buySupplier){
				returnOrder.setSuppliername(buySupplier.getName());
			}
			Contacter contacter = getContacterById(returnOrder.getHandlerid());
			if(null != contacter){
				returnOrder.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartMentById(returnOrder.getBuydeptid());
			if(null != departMent){
				returnOrder.setBuydeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(returnOrder.getBuyuserid());
			if(null != personnel){
				returnOrder.setBuyusername(personnel.getName());
			}
			
			Map total = getReturnOrderMapper().getReturnOrderDetailTotal(id);
			if(total != null){
				if(total.containsKey("taxamount")){
					returnOrder.setField01(total.get("taxamount").toString());
				}
				if(total.containsKey("notaxamount")){
					returnOrder.setField02(total.get("notaxamount").toString());
				}
				if(total.containsKey("tax")){
					returnOrder.setField03(total.get("tax").toString());
				}
			}
			
			List<ReturnOrderDetail> list=showPureReturnOrderDetailListByOrderId(id);
			if(null!=list && list.size()>0){
				GoodsInfo  goodsInfo=null;
				StorageLocation storageLocation =null;
				for(ReturnOrderDetail item :list){
					if(null!=item ){
						goodsInfo=getGoodsInfoByID(item.getGoodsid());
						item.setGoodsInfo(goodsInfo);
						storageLocation = getStorageLocationByID(item.getStoragelocationid());
						if(null!=storageLocation){
							item.setStoragelocationname(storageLocation.getName());
						}
						TaxType taxType = getTaxType(item.getTaxtype());
						if(null!=taxType){
							item.setTaxtypename(taxType.getName());
						}
						if(StringUtils.isNotEmpty(item.getAuxunitid())){
							MeteringUnit meteringUnit=getMeteringUnitById(item.getAuxunitid());
							if(null!=meteringUnit){
								item.setAuxunitname(meteringUnit.getName());
							}
						}
					}
				}
				returnOrder.setReturnOrderDetailList(list);
			}
		}
		return returnOrder;
	}

	@Override
	public ReturnOrder showReturnOrderByDataAuth(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String datasql = getDataAccessRule("t_purchase_returnorder",null);
		map.put("authDataSql", datasql);		
		return returnOrderMapper.getReturnOrderBy(map);		
	}
	
	@Override
	public PageData showReturnOrderPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_purchase_returnorder",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_purchase_returnorder",null);
		pageMap.setDataSql(sql);
		List<ReturnOrder> porderList=returnOrderMapper.getReturnOrderPageList(pageMap);
		BigDecimal tmp=new BigDecimal(0);
		if(porderList!=null && porderList.size()>0){
			DepartMent departMent =null;
			Personnel personnel = null;
			BuySupplier buySupplier=null;
			Contacter contacter =null;
			StorageInfo storageInfo = null;
			Map map=null;
			for(ReturnOrder item : porderList){
				item.setOrdertypename("采购退货通知单");
				if(StringUtils.isNotEmpty(item.getStorageid())){
					storageInfo = getStorageInfoByID(item.getStorageid());
					if(null != storageInfo){
						item.setStoragename(storageInfo.getName());
					}
				}
				if(StringUtils.isNotEmpty(item.getHandlerid())){
					personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getHandlerid());
					if(personnel != null){
						item.setHandlername(personnel.getName());
					}
				}
				if(StringUtils.isNotEmpty(item.getBuydeptid())){
					departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(item.getBuydeptid());
					if(departMent != null){
						item.setBuydeptname(departMent.getName());
					}					
				}
				if(StringUtils.isNotEmpty(item.getBuyuserid())){
					personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getBuyuserid());
					if(personnel != null){
						item.setBuyusername(personnel.getName());
					}
				}
				if(StringUtils.isNotEmpty(item.getSupplierid())){
					buySupplier=getSupplierInfoById(item.getSupplierid());
					if(null!=buySupplier){
						item.setSuppliername(buySupplier.getName());
					}
				}
				if(StringUtils.isNotEmpty(item.getHandlerid())){
					map=new HashMap();
					map.put("id", item.getHandlerid());
					contacter = getBaseFilesContacterMapper().getContacterDetail(map);
					if(contacter != null){
						item.setHandlername(contacter.getName());
					}
				}

				Map total = returnOrderMapper.getReturnOrderDetailTotal(item.getId());
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
				}
				//状态
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(item.getStatus(), "status");
				if(null != sysCode){
					item.setStatusname(sysCode.getCodename());
				}
				//发票状态
				if(StringUtils.isEmpty(item.getIsinvoice()) || "0".equals(item.getIsinvoice()) || "4".equals(item.getIsinvoice())){
					item.setIsinvoicename("未开票");
				}else if("1".equals(item.getIsinvoice())){
					item.setIsinvoicename("已开票");
				}else if("2".equals(item.getIsinvoice())){
					item.setIsinvoicename("核销");
				}else if("3".equals(item.getIsinvoice())){
					item.setIsinvoicename("开票中");
				}
				//出库状态
				if("1".equals(item.getCkstatus())){
					item.setCkstatusname("出库");
				}else if("0".equals(item.getCkstatus())){
					item.setCkstatusname("未出库");
				}
			}
		}
		PageData pageData=new PageData(returnOrderMapper.getReturnOrderPageCount(pageMap), 
						porderList, pageMap);
		
		Map footMap =returnOrderMapper.getReturnOrderDetailTotalSum(pageMap);
		List<ReturnOrder> footList=new ArrayList<ReturnOrder>();
		ReturnOrder foot =new ReturnOrder();
		foot.setId("合计金额");
		foot.setSource("-999");
		foot.setIsinvoice("-999");
		if(null!=footMap){
//			ReturnOrder selectFoot=new ReturnOrder();
//			selectFoot.setId("选中金额");
//			selectFoot.setSource("-999");
//			selectFoot.setIsinvoice("-999");
//			footList.add(selectFoot);
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
		}
		else{
			foot.setField01("0");
			foot.setField02("0");
			foot.setField03("0");
		}
		footList.add(foot);
		pageData.setFooter(footList);
		return pageData;
	}
	/**
	 * 根据参数更新采购退货通知单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * returnOrder :采购退货通知单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public boolean updateReturnOrderBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_purchase_returnorder",null);
		map.put("authDataSql", sql);
		return returnOrderMapper.updateReturnOrderBy(map)>0;
	}
	
	/**
	 * 审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map auditReturnOrder(String id) throws Exception{
		Map map=new HashMap();
		map.put("flag", false);
		ReturnOrder returnOrder=showReturnOrderByDataAuth(id);
		if(null==returnOrder){
			map.put("msg", "未能找到相关采购退货通知单信息");
			map.put("billid", "");
			map.put("flag", false);
			return map;
		}		
		boolean flag = false;
		String nextbillno="";
		SysUser sysUser = getSysUser();
		if("2".equals(returnOrder.getStatus())){
			ReturnOrder upOrder=new ReturnOrder();
			upOrder.setId(id);
			upOrder.setStatus("3");
			upOrder.setAudituserid(sysUser.getUserid());
			upOrder.setAuditusername(sysUser.getName());
			upOrder.setAudittime(new Date());
			upOrder.setBusinessdate(getAuditBusinessdate(returnOrder.getBusinessdate()));
			List<ReturnOrderDetail> detailList=showPureReturnOrderDetailListByOrderId(id);
			Map resultMap=checkStorageSummaryBatchById(detailList);
			Boolean checkOk=(Boolean)resultMap.get("flag");
			if(true==checkOk){
				flag=returnOrderMapper.updateReturnOrderStatus(upOrder)>0;
				if(flag){
					if(null!=detailList && detailList.size()>0){		
						Map map1 =storageForPurchaseService.addPurchaseRejectOut(returnOrder, detailList);
                        nextbillno = (String) map1.get("id");
                        updateReturnOrderRefer(returnOrder.getId(),"1");
						
						for(ReturnOrderDetail item : detailList){
							ReturnOrderDetail rodupDetail=new ReturnOrderDetail();
							rodupDetail.setId(item.getId());
							rodupDetail.setUninvoicenum(item.getUnitnum());
							rodupDetail.setInvoicenum(BigDecimal.ZERO);
							rodupDetail.setUninvoicetaxamount(item.getTaxamount());
							rodupDetail.setInvoicetaxamount(BigDecimal.ZERO);
							returnOrderMapper.updateReturnOrderDetailWriteBack(rodupDetail);
						}
					}
				}
			}else{				
				map.put("msg", (String)resultMap.get("msg"));
			}
		}
		map.put("flag", flag);
		map.put("billid",nextbillno);
		return map;
	}
	private Map checkStorageSummaryBatchById(List<ReturnOrderDetail> list) throws Exception{
		Map result=new HashMap();
		boolean flag=true;
		//验证库存量是否足够
		StringBuilder sBuilder=new StringBuilder();
		for(ReturnOrderDetail returnOrderDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(returnOrderDetail.getGoodsid());
			StorageSummaryBatch storageSummaryBatch = null;
			if((null!=goodsInfo && "1".equals(goodsInfo.getIsbatch()))
					|| StringUtils.isNotEmpty(returnOrderDetail.getSummarybatchid())){
				storageSummaryBatch = storageForPurchaseService.getStorageSummaryBatchBySummarybatchid(returnOrderDetail.getSummarybatchid());
			}
            if(null==storageSummaryBatch){
				storageSummaryBatch = storageForPurchaseService.getStorageSummaryBatchNoBatch(returnOrderDetail.getStorageid(), returnOrderDetail.getGoodsid());
			}
			if(null==storageSummaryBatch || ( null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(returnOrderDetail.getUnitnum())==-1)){
				flag = false;
				if(sBuilder.length()==0){
					sBuilder.append("当前单据中商品编号:");
					sBuilder.append(returnOrderDetail.getGoodsid());
				}else{
					sBuilder.append(",");
					sBuilder.append(returnOrderDetail.getGoodsid());
				}
			}
		}
		result.put("flag", flag);
		if(!flag){
			sBuilder.append("库存量不足");
			result.put("msg", sBuilder.toString());
		}
		return result;
	}
	/**
	 * 反审核
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	public Map oppauditReturnOrder(String id) throws Exception{

		Map map=new HashMap();
		Map queryMap=new HashMap();
		ReturnOrder returnOrder=showReturnOrderByDataAuth(id);
		if(null==returnOrder){
			map.put("msg", "未能找到相关采购退货通知单信息");
			map.put("flag", false);
			return map;
		}
		boolean flag = false;
		SysUser sysUser = getSysUser();
		if("3".equals(returnOrder.getStatus()) && (StringUtils.isEmpty(returnOrder.getIsinvoice()) || "0".equals(returnOrder.getIsinvoice()) || "4".equals(returnOrder.getIsinvoice()))){

			flag=storageForPurchaseService.deletePurchaseRejectOutBySourceid(returnOrder.getId());
			if(flag){
				ReturnOrder upOrder=new ReturnOrder();
				upOrder.setId(id);
				upOrder.setStatus("2");
				upOrder.setAudituserid(sysUser.getUserid());
				upOrder.setAuditusername(sysUser.getName());
				upOrder.setAudittime(new Date());
				upOrder.setIsinvoice("0");

				if(returnOrderMapper.updateReturnOrderStatus(upOrder)>0){
					updateReturnOrderRefer(returnOrder.getId(),"0");
				}
			}
		}
		map.put("flag", flag);
		return map;
	}
	@Override
	public boolean auditWorkflowReturnOrder(String id) throws Exception{
		ReturnOrder returnOrder=showPureReturnOrder(id);	
		boolean flag = false;
		//String nextbillno="";
		SysUser sysUser = getSysUser();
		if("2".equals(returnOrder.getStatus())){
			ReturnOrder upOrder=new ReturnOrder();
			upOrder.setId(id);
			upOrder.setStatus("3");
			upOrder.setAudituserid(sysUser.getUserid());
			upOrder.setAuditusername(sysUser.getName());
			upOrder.setAudittime(new Date());
			flag=returnOrderMapper.updateReturnOrderStatus(upOrder)>0;
			if(flag){
				List<ReturnOrderDetail> list=showPureReturnOrderDetailListByOrderId(id);
				if(null != list){
					//nextbillno=
                            storageForPurchaseService.addPurchaseRejectOut(returnOrder, list);
					updateReturnOrderRefer(returnOrder.getId(),"1");
				}
			}
		}
		return flag;
	}
	@Override
	public boolean updateReturnOrderRefer(String id,String isrefer) throws Exception{
		if(null==id || "".equals(id.trim())){
			return false;
		}
		boolean flag=false;
		ReturnOrder upOrder=new ReturnOrder();
		upOrder.setIsrefer(isrefer);
		upOrder.setId(id);
		flag = returnOrderMapper.updateReturnOrderStatus(upOrder)>0;
		return flag;
	}

	@Override
	public boolean updateReturnOrderStatus(ReturnOrder returnOrder) throws Exception{
		return returnOrderMapper.updateReturnOrderStatus(returnOrder)>0;
	}
	//------------------------------------------------------------------------//
	//采购退货通知单详细
	//-----------------------------------------------------------------------//
	
	public boolean insertReturnOrderDetail(ReturnOrderDetail returnOrderDetail) throws Exception{
		return returnOrderMapper.insertReturnOrderDetail(returnOrderDetail)>0;
	}
	
	public List showReturnOrderDetailListByOrderId(String orderid) throws Exception{
		if(null==orderid || "".equals(orderid)){
			return new ArrayList<ReturnOrderDetail>();
		}
		orderid=orderid.trim();
		Map map=new HashMap();
		map.put("orderid", orderid);
		String cols = getAccessColumnList("t_purchase_returnorder_detail",null);
		map.put("cols", cols);
		String sql = getDataAccessRule("t_purchase_returnorder_detail",null);
		map.put("authDataSql", sql);
		List<ReturnOrderDetail> list= returnOrderMapper.getReturnOrderDetailListBy(map);
		if(null!=list && list.size()>0){
			TaxType taxType=null;
			MeteringUnit meteringUnit=null;
			StorageInfo storageInfo =null;
			GoodsInfo  goodsInfo=null;
			StorageLocation storageLocation =null;
			for(ReturnOrderDetail item :list){
				if(null!=item ){
					goodsInfo=getGoodsInfoByID(item.getGoodsid());
					item.setGoodsInfo(goodsInfo);
					storageInfo = getStorageInfoByID(item.getStorageid());
					if(null!=storageInfo){
						item.setStoragename(storageInfo.getName());
					}
					storageLocation = getStorageLocationByID(item.getStoragelocationid());
					if(null!=storageLocation){
						item.setStoragelocationname(storageLocation.getName());
					}
					taxType = getTaxType(item.getTaxtype());
					if(null!=taxType){
						item.setTaxtypename(taxType.getName());
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
	 * 采购退货通知单明细分页列表，不判断权限
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	public List showPureReturnOrderDetailListByOrderId(String orderid) throws Exception{
		return showBasePureReturnOrderDetailListByOrderId(orderid);
	}
	
	public boolean deleteReturnOrderDetailByOrderid(String orderid) throws Exception{
		return returnOrderMapper.deleteReturnOrderDetailByOrderid(orderid)>0;
	}
	/**
	 * 更新采购明细
	 * @param returnOrderDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	public int updateReturnOrderDetail(ReturnOrderDetail returnOrderDetail) throws Exception{
		return returnOrderMapper.updateReturnOrderDetail(returnOrderDetail);
	}

	@Override
	public List<ReturnOrderDetail> showReturnOrderDetailListBy(Map<String, Object> map) throws Exception{
		if(!(map.containsKey("ischeckauth") && 
			null!=map.get("ischeckauth") && 
			"0".equals(map.get("ischeckauth").toString()))){
			String sql = getDataAccessRule("t_purchase_plannedorder",null);
			map.put("authDataSql", sql);
		}
		List<ReturnOrderDetail> list= returnOrderMapper.getReturnOrderDetailListBy(map);
		if(null!=list && list.size()>0){
			TaxType taxType=null;
			MeteringUnit meteringUnit=null;
			StorageInfo storageInfo =null;
			GoodsInfo  goodsInfo=null;
			StorageLocation storageLocation =null;
			for(ReturnOrderDetail item :list){
				if(null!=item ){
					item.setOrdertype("2");
					goodsInfo=getGoodsInfoByID(item.getGoodsid());
					item.setGoodsInfo(goodsInfo);
					storageInfo = getStorageInfoByID(item.getStorageid());
					if(null!=storageInfo){
						item.setStoragename(storageInfo.getName());
					}
					storageLocation = getStorageLocationByID(item.getStoragelocationid());
					if(null!=storageLocation){
						item.setStoragelocationname(storageLocation.getName());
					}
					taxType = getTaxType(item.getTaxtype());
					if(null!=taxType){
						item.setTaxtypename(taxType.getName());
					}
					if(StringUtils.isNotEmpty(item.getAuxunitid())){
						meteringUnit=getMeteringUnitById(item.getAuxunitid());
						if(null!=meteringUnit){
							item.setAuxunitname(meteringUnit.getName());
						}
					}
					BigDecimal bNum = (null != item.getUninvoicenum())?item.getUninvoicenum() : new BigDecimal(0);//未开票数量
					BigDecimal bAuxNum = new BigDecimal(0);
					String auxStr = "",numStr = "",auxunitname = "",unitname = "";
					if((bNum.compareTo(new BigDecimal(0)) == 1)?true : false && item.getUnitnum().compareTo(bNum) != 0){
						//主数量
						item.setUnitnum(item.getUninvoicenum().negate());
						//辅数量
						List<GoodsInfo_MteringUnitInfo> muList = getGoodsMapper().getMUListByGoodsId(item.getGoodsid());
						if(muList.size() != 0){
							for(GoodsInfo_MteringUnitInfo goodsMUInfo : muList){
								if("1".equals(goodsMUInfo.getIsdefault())){
									//换算方式：固定
									if("1".equals(goodsMUInfo.getType())){
										BigDecimal rate = (null != goodsMUInfo.getRate()) ? goodsMUInfo.getRate() : new BigDecimal(0);
										//主比辅 辅数量 = 主数量/换算比率
										if("1".equals(goodsMUInfo.getMode())){
											if(bNum.remainder(rate).compareTo(new BigDecimal(0)) == 0 ){//已除尽
												bAuxNum = bNum.divide(rate, 8, BigDecimal.ROUND_HALF_UP);
												auxStr = bAuxNum.toString().substring(0, bAuxNum.toString().lastIndexOf("."));//辅数量
											}
											else{
												bAuxNum = bNum.divide(rate, 8, BigDecimal.ROUND_HALF_UP);
												BigDecimal num = bNum.remainder(rate);
												auxStr = bAuxNum.toString().substring(0, bAuxNum.toString().lastIndexOf("."));//辅数量
												if(num.toString().indexOf(".") != -1){
													numStr = num.toString().substring(0, num.toString().lastIndexOf("."));//主数量
												}
												else{
													numStr = num.toString();
												}
											}
										}
										else{//辅比主 辅单位 = 主数量*换算比率
											bAuxNum = bNum.multiply(rate);
										}
									}
								}
							}
						}
						auxunitname = (null != item.getAuxunitname() && StringUtils.isNotEmpty(auxStr)) ? item.getAuxunitname() : "";
						unitname = (null != item.getUnitname() && StringUtils.isNotEmpty(numStr)) ? item.getUnitname() : "";
						item.setAuxnumdetail(auxStr+auxunitname+numStr+unitname);
						//含税金额 = 含税单价*数量
						item.setTaxamount(item.getTaxprice().multiply(item.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
						//未税金额 = 无税单价*数量
						item.setNotaxamount(getNotaxAmountByTaxAmount(item.getTaxamount(),item.getTaxtype()));
						//税额 = 含税金额-未税金额
						item.setTax(item.getTaxamount().subtract(item.getNotaxamount()));
					}
				}
			}
		}
		return list;
	}
	@Override
	public ReturnOrder showPureReturnOrderAndDetail(String id) throws Exception{
		return showBasePureReturnOrderAndDetail(id);
	}
	@Override
	public boolean submitReturnOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception{
		return false;
	}

	@Override
	public ReturnOrderDetail getReturnOrderDetailByOrderidAndGoodsId(
			String orderid, String goodsid) throws Exception {
		ReturnOrderDetail returnOrderDetail = returnOrderMapper.getReturnOrderDetailByOrderidAndGoodsId(orderid,goodsid);
		return returnOrderDetail;
	}

	@Override
	public ReturnOrderDetail showPureReturnOrderDetail(String id) throws Exception{
		return returnOrderMapper.getReturnOrderDetail(id);
	}
	
	@Override
	public List showReturnOrderListBy(Map map) throws Exception{
		String datasql = getDataAccessRule("t_purchase_returnorder",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<ReturnOrder> list=returnOrderMapper.showReturnOrderListBy(map);
		for(ReturnOrder item : list){
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
				List<ReturnOrderDetail> detailList=showPureReturnOrderDetailListByOrderId(item.getId());
				if(null!=list && list.size()>0){
					for(ReturnOrderDetail detail :detailList){
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
					item.setReturnOrderDetailList(detailList);
				}
			}
		}
		return list;
	}
	@Override
	public boolean updateOrderPrinttimes(ReturnOrder returnOrder) throws Exception{
		return returnOrderMapper.updateOrderPrinttimes(returnOrder)>0;		
	}
	
	/**
	 * 保存验收
	 * @param returnOrder
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public Map updateReturnOrderCheck(ReturnOrder returnOrder) throws Exception{
		Map resultMap=new HashMap();
		
		ReturnOrder oldReturnOrder=returnOrderMapper.getReturnOrder(returnOrder.getId());
		if(null==oldReturnOrder){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要验收的信息");
			return resultMap;
		}
		if("1".equals(oldReturnOrder.getIscheck())){
			resultMap.put("flag", false);
			resultMap.put("msg", "此退货通知单已经被验收");
			return resultMap;
		}
		if(!"1".equals(oldReturnOrder.getCkstatus())){
			resultMap.put("flag", false);
			resultMap.put("msg", "此退货单未库，不能验收");
			return resultMap;
		}
		returnOrder.setStatus(oldReturnOrder.getStatus());
		
		SysUser sysUser=getSysUser();
		String todayString=CommonUtils.getTodayDataStr();


		ReturnOrder upReturnOrder=new ReturnOrder();
		upReturnOrder.setIscheck("1"); //检查
		upReturnOrder.setCheckuserid(sysUser.getUserid());
		upReturnOrder.setCheckusername(sysUser.getName());
		upReturnOrder.setCheckdate(todayString);
		upReturnOrder.setIsinvoice("4");

		returnOrder.setIscheck("1"); //检查
		returnOrder.setCheckuserid(sysUser.getUserid());
		returnOrder.setCheckusername(sysUser.getName());
		returnOrder.setCheckdate(todayString);
		returnOrder.setIsinvoice("4");

		Map paramMap=new HashMap();
		paramMap.put("returnOrder", upReturnOrder);
		paramMap.put("id", returnOrder.getId());
		paramMap.put("isnotcheck", "true");
		boolean flag=returnOrderMapper.updateReturnOrderBy(paramMap)>0;
		if(flag){
			List<ReturnOrderDetail> list=returnOrder.getReturnOrderDetailList();
			if(flag && list!=null && list.size()>0){
				int iseq=1;
				for(ReturnOrderDetail item : list){		
					//计算辅单位数量
					Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
					if(auxmap.containsKey("auxnum")){
						item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
					}
					item.setOrderid(returnOrder.getId());

					BigDecimal taxamount=item.getUnitnum().multiply(item.getTaxprice());
					item.setTaxamount(taxamount);
					
	                BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, item.getTaxtype());
	                item.setNotaxamount(notaxamount);
	                if (null != notaxamount && notaxamount.compareTo(BigDecimal.ZERO) == 1) {
	                    BigDecimal notaxprice = getNotaxAmountByTaxAmount(taxamount, item.getTaxtype()).divide(item.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
	                    item.setNotaxprice(notaxprice);
	                }
	                item.setTax(item.getTaxamount().subtract(item.getNotaxamount()));
					

					item.setUninvoicenum(item.getUnitnum());
					item.setInvoicenum(BigDecimal.ZERO);
					item.setUninvoicetaxamount(item.getTaxamount());
					item.setUninvoicenotaxamount(item.getNotaxamount());
					item.setReturnleftaxamount(item.getTaxamount());
					item.setReturnlefnotaxamount(item.getNotaxamount());
					
					if(returnOrderMapper.updateReturnOrderDetailByCheck(item)==0){
		 				 throw new Exception("明细验收失败。");
					}
                    //代配送不重新计算成本价
                    boolean isUpdateCostprice = true;
                    if("2".equals(oldReturnOrder.getSource())){
                        isUpdateCostprice = false;
                    }
					if(!storageForPurchaseService.updatePurchaseRejectOutDetailByReturnCheck(item,isUpdateCostprice)){
                        throw new Exception("明细验收失败。");
					}
				}
			}
			//采购退货入库单验收
			PurchaseRejectOut uPurchaseRejectOut=new PurchaseRejectOut();
			uPurchaseRejectOut.setIscheck("1"); //检查
			uPurchaseRejectOut.setCheckuserid(sysUser.getUserid());
			uPurchaseRejectOut.setCheckusername(sysUser.getName());
			uPurchaseRejectOut.setCheckdate(todayString);
			uPurchaseRejectOut.setBusinessdate(todayString);
			uPurchaseRejectOut.setStatus("4");
			paramMap.clear();
			paramMap.put("rejectOut", uPurchaseRejectOut); //要更新的消息
			paramMap.put("sourceid", returnOrder.getId()); //条件
			paramMap.put("ischeck", "0"); //条件
			storageForPurchaseService.updatePurchaseRejectOutBy(paramMap);
		}
		resultMap.put("flag", flag);
		
		return resultMap;
	}
	/**
	 * 取消验收
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public Map updateReturnOrderCheckCancel(String id) throws Exception{
		Map resultMap=new HashMap();
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要验收的信息");
			return resultMap;
		}
		ReturnOrder oldReturnOrder=returnOrderMapper.getReturnOrder(id);
		if(null==oldReturnOrder){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要验收的信息");
			return resultMap;
		}
		//来源类型代配送
		if("2".equals(oldReturnOrder.getSource())){
			resultMap.put("flag", false);
			resultMap.put("msg", "单据来源代配送");
			return resultMap;
		}
		
		if(!"1".equals(oldReturnOrder.getCkstatus())){
			resultMap.put("flag", false);
			resultMap.put("msg", "采购退货出库单未出库（已反审）");
			return resultMap;			
		}
		if(!"1".equals(oldReturnOrder.getIscheck())){
			resultMap.put("flag", false);
			resultMap.put("msg", "该单据未被验收");
			return resultMap;			
		}

		String todayString=CommonUtils.getTodayDataStr();
		if(StringUtils.isEmpty(oldReturnOrder.getCheckdate()) || !oldReturnOrder.getCheckdate().equals(todayString)){
			resultMap.put("flag", false);
			resultMap.put("msg", "当天验收的单据才能取消验收");
			return resultMap;			
		}
		if(!("0".equals(oldReturnOrder.getIsinvoice()) || "4".equals(oldReturnOrder.getIsinvoice()))){
			resultMap.put("flag", false);
			resultMap.put("msg", "未开票的退货单才能取消验收");
			return resultMap;						
		}
		ReturnOrder returnOrder=new ReturnOrder();
		returnOrder.setIscheck("0"); //未验收
		returnOrder.setCheckuserid("");
		returnOrder.setCheckusername("");
		returnOrder.setCheckdate("");
		returnOrder.setIsinvoice("0");
		Map paramMap=new HashMap();
		paramMap.put("returnOrder", returnOrder);
		paramMap.put("id", oldReturnOrder.getId());
		paramMap.put("ischeckcancel", "true");
		boolean flag=returnOrderMapper.updateReturnOrderBy(paramMap)>0;
		
		if(flag){
			PurchaseRejectOut uPurchaseRejectOut=new PurchaseRejectOut();
			uPurchaseRejectOut.setIscheck("0"); //未验收
			uPurchaseRejectOut.setCheckuserid("");
			uPurchaseRejectOut.setCheckusername("");
			uPurchaseRejectOut.setCheckdate("");
			uPurchaseRejectOut.setStatus("3");
			paramMap.clear();
			paramMap.put("rejectOut", uPurchaseRejectOut);
			paramMap.put("sourceid", oldReturnOrder.getId());
			paramMap.put("ischeck", "1"); //对已经验收的作取消验收
			if(!storageForPurchaseService.updatePurchaseRejectOutBy(paramMap)){
                throw new Exception("明细验收失败。");
			}
			storageForPurchaseService.updatePurchaseRejectOutDetailByReturnCheckCancel(oldReturnOrder.getId());
		}
		resultMap.put("flag", flag);
		
		return resultMap;
	}
	/**
	 * 批量验收
	 * @param idarrs
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年6月18日
	 */
	public Map updateReturnOrderMutiCheck(String idarrs) throws Exception{
		Map resultMap=new HashMap();
		if(null==idarrs || "".equals(idarrs.trim())){

			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关单据号");
			return resultMap;		
		}
		String[] idArr = idarrs.trim().split(",");
		if(idArr.length==0){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关单据号");
			return resultMap;	
		}
		StringBuffer successidSBuffer = new StringBuffer();
		StringBuffer failidStringBuffer =  new StringBuffer();
		StringBuilder msgBuilder=new StringBuilder();
		String todayString=CommonUtils.getTodayDataStr();
		SysUser sysUser=getSysUser();
		int isuccess=0;
		int ifail=0;
		for(String id : idArr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			id=id.trim();
			ReturnOrder oldReturnOrder = returnOrderMapper.getReturnOrder(id);
			if(null==oldReturnOrder){
				failidStringBuffer.append(id);
				msgBuilder.append("编号：");
				msgBuilder.append(id);
				msgBuilder.append("未能找到要验收的信息");
				msgBuilder.append("<br/>");
				continue;
			}
			if("1".equals(oldReturnOrder.getIscheck())){
				failidStringBuffer.append(id);
				msgBuilder.append("编号：");
				msgBuilder.append(id);
				msgBuilder.append("此退货通知单已经被验收");
				msgBuilder.append("<br/>");
				continue;
			}
			if(!"1".equals(oldReturnOrder.getCkstatus())){
				failidStringBuffer.append(id);
				msgBuilder.append("编号：");
				msgBuilder.append(id);
				msgBuilder.append("此退货单未出库，不能验收");
				msgBuilder.append("<br/>");
				continue;
			}
			List<ReturnOrderDetail> detailList= returnOrderMapper.getReturnOrderDetailListByOrderid(id);

			if(null==detailList || detailList.size()==0){
				failidStringBuffer.append(id);
				msgBuilder.append("编号：");
				msgBuilder.append(id);
				msgBuilder.append("未找到单据明细信息");
				msgBuilder.append("<br/>");
				continue;
			}
			Object transactionBegin= TransactionAspectSupport.currentTransactionStatus().createSavepoint();

			ReturnOrder upReturnOrder=new ReturnOrder();
			upReturnOrder.setIscheck("1"); //检查
			upReturnOrder.setCheckuserid(sysUser.getUserid());
			upReturnOrder.setCheckusername(sysUser.getName());
			upReturnOrder.setCheckdate(todayString);
			upReturnOrder.setIsinvoice("4");

			oldReturnOrder.setIscheck("1"); //检查
			oldReturnOrder.setCheckuserid(sysUser.getUserid());
			oldReturnOrder.setCheckusername(sysUser.getName());
			oldReturnOrder.setCheckdate(todayString);
			oldReturnOrder.setIsinvoice("4");

			Map paramMap=new HashMap();
			paramMap.put("returnOrder", upReturnOrder);
			paramMap.put("id", id);
			paramMap.put("isnotcheck", "true");
			boolean flag=returnOrderMapper.updateReturnOrderBy(paramMap)>0;
			if(flag){
                if(detailList!=null && detailList.size()>0){
					int iseq=1;
					boolean isDetailOk=true;
					for(ReturnOrderDetail item : detailList){
						//计算辅单位数量
						Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
						if(auxmap.containsKey("auxnum")){
							item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
						}
						item.setOrderid(oldReturnOrder.getId());

						BigDecimal taxamount=item.getUnitnum().multiply(item.getTaxprice());
						BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, item.getTaxtype());
						item.setTaxamount(taxamount);
						item.setNotaxamount(notaxamount);
						if (null != notaxamount && notaxamount.compareTo(BigDecimal.ZERO) == 1) {
							BigDecimal notaxprice =  getNotaxAmountByTaxAmount(taxamount, item.getTaxtype()).divide(item.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
							item.setNotaxprice(notaxprice);
						}
						item.setTax(item.getTaxamount().subtract(item.getNotaxamount()));


						item.setUninvoicenum(item.getUnitnum());
						item.setInvoicenum(BigDecimal.ZERO);
						item.setUninvoicetaxamount(item.getTaxamount());
						item.setUninvoicenotaxamount(item.getNotaxamount());
						item.setReturnleftaxamount(item.getTaxamount());
						item.setReturnlefnotaxamount(item.getNotaxamount());
						if(returnOrderMapper.updateReturnOrderDetailByCheck(item)==0){
							isDetailOk=false;
							failidStringBuffer.append(id);
							msgBuilder.append("编号：");
							msgBuilder.append(id);
							msgBuilder.append("明细验收失败");
							msgBuilder.append("<br/>");
							continue;
						}
						//代配送不重新计算成本价
						boolean isUpdateCostprice = true;
						if("2".equals(oldReturnOrder.getSource())){
							isUpdateCostprice = false;
						}
						if(!storageForPurchaseService.updatePurchaseRejectOutDetailByReturnCheck(item,isUpdateCostprice)){
							isDetailOk=false;
							failidStringBuffer.append(id);
							msgBuilder.append("编号：");
							msgBuilder.append(id);
							msgBuilder.append("明细验收失败");
							msgBuilder.append("<br/>");
							continue;
						}
					}
					if(!isDetailOk){
						TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(transactionBegin);	//如何保存失败后回滚
						continue;
					}
                }

				//采购退货入库单验收
				PurchaseRejectOut uPurchaseRejectOut=new PurchaseRejectOut();
				uPurchaseRejectOut.setIscheck("1"); //检查
				uPurchaseRejectOut.setCheckuserid(sysUser.getUserid());
				uPurchaseRejectOut.setCheckusername(sysUser.getName());
				uPurchaseRejectOut.setCheckdate(todayString);
				uPurchaseRejectOut.setBusinessdate(todayString);
				uPurchaseRejectOut.setStatus("4");
				paramMap.clear();
				paramMap.put("rejectOut", uPurchaseRejectOut);
				paramMap.put("sourceid", id);
				paramMap.put("ischeck", "0");
				if(!storageForPurchaseService.updatePurchaseRejectOutBy(paramMap)){
					failidStringBuffer.append(id+",");
					 ifail=ifail+1;
					 continue;
					 
				}
				successidSBuffer.append(id+",");
				isuccess=isuccess+1;
			}else{
				failidStringBuffer.append(id+",");
			}
			TransactionAspectSupport.currentTransactionStatus().releaseSavepoint(transactionBegin);
		}
		boolean flag=false;
		if(isuccess>0){
			flag=true;
		}
		resultMap.put("flag", flag);
		resultMap.put("successid", successidSBuffer.toString());
		resultMap.put("failid", failidStringBuffer.toString());
		resultMap.put("isucess", isuccess);
		resultMap.put("ifail", ifail);
		resultMap.put("msg",msgBuilder.toString());
		return resultMap;
	}

	@Override
	public boolean addReturnOrderByDelivery(StorageDeliveryOutForJob Outjob, List<StorageDeliveryOutForJob> OutjobDetails) throws Exception {
		
		boolean flag=false;
		if(null!=Outjob){
			ReturnOrder returnOrder = new ReturnOrder();
			returnOrder.setId(getBillNumber(returnOrder, "t_purchase_returnorder"));
			
			
			returnOrder.setBusinessdate(CommonUtils.getTodayDataStr());
			//状态为审核
			returnOrder.setStatus("3");
			
			String remark=Outjob.getPremark()==null?"":Outjob.getPremark();
			returnOrder.setRemark(remark);
			returnOrder.setAdduserid(Outjob.getAdduserid());
			returnOrder.setAddusername(Outjob.getAddusername());
			//新增人信息
			returnOrder.setAdddeptid(Outjob.getAdddeptid());
			returnOrder.setAdddeptname(Outjob.getAdddeptname());
			returnOrder.setAddtime(new Date());
			//审核人信息
			returnOrder.setAudittime(new Date());
			returnOrder.setAudituserid(Outjob.getAudituserid());
			returnOrder.setAuditusername(Outjob.getAuditusername());
			//修改人信息
			returnOrder.setModifytime(new Date());
			returnOrder.setModifyuserid(Outjob.getModifyuserid());
			returnOrder.setModifyusername(Outjob.getModifyusername());
			//终止人
			returnOrder.setStoptime(new Date());
			returnOrder.setStopuserid(Outjob.getStopuserid());
			returnOrder.setStopusername(Outjob.getStopusername());
			//验收人,验收时间 (11.5生成单据是未验收的,这几个去掉)
//			returnOrder.setCheckuserid(Outjob.getAudituserid());
//			returnOrder.setCheckusername(Outjob.getAuditusername());
//			returnOrder.setCheckdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			//关闭时间
			returnOrder.setClosetime(Outjob.getClosetime());
			//仓库置空 取消,
			returnOrder.setStorageid(Outjob.getStorageid());
			
			//采购部门,采购名称
			BuySupplier sup=getSupplierInfoById(Outjob.getSupplierid());
			if(sup!=null){
				returnOrder.setBuyuserid(sup.getBuyuserid()==null?"":sup.getBuyuserid());
				returnOrder.setBuydeptid(sup.getBuydeptid()==null?"":sup.getBuydeptid());
			}
			
			returnOrder.setSupplierid(Outjob.getSupplierid());
			returnOrder.setSource("2");
			//来源单据编号
			returnOrder.setBillno(Outjob.getId());
			returnOrder.setIsrefer("1");
			//出库状态,已出库
			returnOrder.setCkstatus("1");
			
			//4可以生成发票(出库验收后可以生成发票)
			returnOrder.setIsinvoice("4");
			//采购退货通知单生成的是未验收的
			returnOrder.setIscheck("0");
			//添加采购入库明细.
			for(StorageDeliveryOutForJob detail:OutjobDetails){
				addReturnOrderDetailByDeliveryOrderJob(detail, returnOrder);
			}
			
			flag = returnOrderMapper.addreturnOrderForDeliveryJob(returnOrder)>0;
			if(flag){
				List<ReturnOrderDetail> ReturnOrderDetailList=returnOrderMapper.getReturnOrderDetailListByOrderid(returnOrder.getId());				
				boolean rs=storageForPurchaseService.addPurchaseRejectOutForJob(returnOrder, ReturnOrderDetailList);
				flag=rs;
			}
		}
		return flag;
		
		
	}

	@Override
	public Map importReturnOrderList(Map<String, List> supplierMap) throws Exception {

		String msg = "",dismsg = "";
        int count = 0 ;
		boolean flag = false ;
		for (Map.Entry<String, List> entry : supplierMap.entrySet()) {
			List<String> disableInfo = new ArrayList<String>();
			String Str = entry.getKey();
			String[] str = Str.split(",");
			String supplierid = str[0];
			String storageid = str[1];
			BuySupplier buySupplier = getSupplierInfoById(supplierid);
			if(null != buySupplier){
				ReturnOrder returnOrder = new ReturnOrder();
				String orderid = getBillNumber(returnOrder, "t_purchase_returnorder");
				returnOrder.setId(orderid);
				returnOrder.setBusinessdate(CommonUtils.getTodayDataStr());
				returnOrder.setStatus("2");
				returnOrder.setSupplierid(supplierid);
				returnOrder.setSource("2");//类型为导入
				returnOrder.setBuydeptid(buySupplier.getBuydeptid());
				returnOrder.setBuyuserid(buySupplier.getBuyuserid());

				List<ModelOrder> wareList = entry.getValue();
				for(ModelOrder modelOrder : wareList){
					GoodsInfo goodsInfo = new GoodsInfo();
					Map goodsMap = new HashMap();
					if( StringUtils.isNotEmpty(modelOrder.getGoodsid())){//根据商品编码查商品
						goodsInfo = getAllGoodsInfoByID(modelOrder.getGoodsid());
						if(null == goodsInfo || "0".equals(goodsInfo.getState())){
							goodsMap.put("id",modelOrder.getGoodsid());
						}
					}else if( StringUtils.isNotEmpty(modelOrder.getBarcode())){
						String barcode = modelOrder.getBarcode();
						goodsInfo = getGoodsInfoByBarcode(barcode);//根据条形码查商品
                        goodsInfo = getAllGoodsInfoByID(goodsInfo.getId());
						if(null == goodsInfo || "0".equals(goodsInfo.getState())){
							goodsMap.put("barcode",barcode);
						}
					}
					if(goodsMap.size() > 0){//按条形码或者助记符导入的商品导入不成功，判断不存在还是有相同条形码或助记符
						List<GoodsInfo> goodsInfoList = getBaseGoodsMapper().getGoodsInfoListByMap(goodsMap);
						for(GoodsInfo gInfo : goodsInfoList){
							if(null != gInfo && "1".equals(gInfo.getState())){
								goodsInfo = getAllGoodsInfoByID(gInfo.getId());
								break;
							}
						}
					}
					//禁用商品不导入
					if(null != goodsInfo && "0".equals(goodsInfo.getState())){
						if(StringUtils.isNotEmpty(modelOrder.getGoodsid())){
							disableInfo.add(modelOrder.getGoodsid());
						}else if(StringUtils.isNotEmpty(modelOrder.getBarcode())){
							disableInfo.add(modelOrder.getBarcode());
						}
						continue;
					}
					if(null != goodsInfo){
						ReturnOrderDetail returnOrderDetail = new ReturnOrderDetail();
						returnOrderDetail.setOrderid(orderid);
						returnOrderDetail.setGoodsid(goodsInfo.getId());
						returnOrderDetail.setStorageid(storageid);
						returnOrderDetail.setGoodsInfo(goodsInfo);

						returnOrderDetail.setUnitid(goodsInfo.getMainunit());
						returnOrderDetail.setUnitname(goodsInfo.getMainunitName());

						BigDecimal boxnum = goodsInfo.getBoxnum();
						String num = modelOrder.getUnitnum();
						if(StringUtils.isEmpty(num)){
							continue;
						}
						BigDecimal unitnum = new BigDecimal(num);

						BigDecimal auxnum = new BigDecimal(0);
						BigDecimal overnum = new BigDecimal(0);

						if(null != boxnum && boxnum.compareTo(BigDecimal.ZERO) > 0){
							auxnum = unitnum.divide(boxnum,0,BigDecimal.ROUND_DOWN);
							overnum = unitnum.subtract(auxnum.multiply(boxnum)).setScale(0,BigDecimal.ROUND_HALF_UP);
						}else{
							overnum = unitnum;
						}
						returnOrderDetail.setUnitnum(unitnum);
						returnOrderDetail.setAuxunitid(goodsInfo.getAuxunitid());
						returnOrderDetail.setAuxunitname(goodsInfo.getAuxunitname());
						returnOrderDetail.setAuxnum(auxnum);
						returnOrderDetail.setAuxnumdetail(auxnum+goodsInfo.getAuxunitname()+overnum+goodsInfo.getMainunitName());
						returnOrderDetail.setAuxremainder(overnum);

						//0取基准销售价,1取合同价
						String priceType = getSysParamValue("DELIVERYORDERPRICE");
						BigDecimal price;
						if(StringUtils.isNotEmpty(modelOrder.getTaxprice())){
							if("null".equals(modelOrder.getTaxprice()) || StringUtils.isEmpty(modelOrder.getTaxprice())){
								price = getGoodsPriceByCustomer(goodsInfo.getId(),modelOrder.getBusid());
							}else{
								price = new BigDecimal(modelOrder.getTaxprice());
							}
						}else if("1".equals(priceType) &&!"".equals(modelOrder.getBusid())){
							//系统参数取价
							price = getGoodsPriceByCustomer(goodsInfo.getId(),modelOrder.getBusid());
						}else {
							price = goodsInfo.getBasesaleprice();
						}
						returnOrderDetail.setTaxprice(price);
						BigDecimal taxamount = price.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
						returnOrderDetail.setTaxamount(taxamount);

						BigDecimal rate = BigDecimal.ZERO;
						TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype()); //获取默认税种
						if (taxType != null) {
							returnOrderDetail.setTaxtype(taxType.getId()); //税种档案中的编码
							returnOrderDetail.setTaxtypename(taxType.getName()); //税种名称
							rate = taxType.getRate().divide(new BigDecimal(100));
						}
						returnOrderDetail.setNotaxprice(price.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
						BigDecimal notaxamount = getNotaxAmountByTaxAmount(returnOrderDetail.getTaxamount(),returnOrderDetail.getTaxtype());
						returnOrderDetail.setNotaxamount(notaxamount);
						BigDecimal tax = returnOrderDetail.getTaxamount().subtract(returnOrderDetail.getNotaxamount());
						returnOrderDetail.setTax(tax);
                        returnOrderDetail.setBoxprice(price.multiply(boxnum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
						flag = returnOrderMapper.insertReturnOrderDetail(returnOrderDetail) > 0;
					}
				}
				if(flag){
					returnOrder.setStorageid(storageid);
                    SysUser sysUser = getSysUser();
                    returnOrder.setAdduserid(sysUser.getUserid());
                    returnOrder.setAddusername(sysUser.getUsername());
                    returnOrder.setAddtime(new Date());
					flag = returnOrderMapper.insertReturnOrder(returnOrder) > 0;
					if(flag){
                        ++ count ;
						if(disableInfo.size() > 0){
                            dismsg += ",供应商："+supplierid+",商品编码："+disableInfo.toString()+"是禁用商品，不允许导入";
						}
					}
				}
			}else{
				continue;
			}
		}
		if(count > 0){
            flag = true ;
            msg = "导入成功"+count+"条单据"+dismsg;
        }else{
            flag = false ;
            msg = dismsg;
        }
		Map map = new HashMap();
		map.put("msg",msg);
		map.put("flag",flag);
		return map;
	}

	private void addReturnOrderDetailByDeliveryOrderJob(StorageDeliveryOutForJob detail, ReturnOrder returnOrder) throws Exception {
		
		
		ReturnOrderDetail orderDetail =new ReturnOrderDetail();
		
		orderDetail.setOrderid(returnOrder.getId()==null?"":returnOrder.getId());
		orderDetail.setGoodsid(detail.getGoodsid()==null?"":detail.getGoodsid());
		orderDetail.setStorageid(returnOrder.getStorageid()==null?"":returnOrder.getStorageid());
		orderDetail.setUnitid(detail.getUnitid()==null?"":detail.getUnitid());
		orderDetail.setUnitname(detail.getUnitname()==null?"":detail.getUnitname()); //主计量单位名称
		orderDetail.setUnitnum(detail.getUnitnum()); //数量
		
		orderDetail.setAuxunitid(detail.getAuxunitid()==null?"":detail.getAuxunitid());
		orderDetail.setAuxunitname(detail.getAuxunitname()==null?"":detail.getAuxunitname()); //辅计量单位名称
		orderDetail.setTotalbox(detail.getTotalbox());
        orderDetail.setAddcostprice(detail.getAddcostprice());
		if(StringUtils.isNotEmpty(detail.getGoodsid())){
			GoodsInfo goodsInfo=getGoodsInfoByID(detail.getGoodsid());
			if(goodsInfo!=null){
				//箱装量
				BigDecimal boxNum=goodsInfo.getBoxnum();
				//辅计量  XX箱
				BigDecimal auxnum=detail.getUnitnum().divide(boxNum,0,BigDecimal.ROUND_DOWN);
				orderDetail.setAuxnum(auxnum);
				//主单位余数
				BigDecimal left=detail.getUnitnum().subtract(boxNum.multiply(auxnum));
				orderDetail.setAuxremainder(left);
				//XX箱XX个
				BigDecimal left2=left.setScale(0, BigDecimal.ROUND_HALF_DOWN);
				orderDetail.setAuxnumdetail(auxnum+detail.getAuxunitname()+left2+detail.getUnitname());
				
				
				String PurchasePriceType=getSysParamValue("PurchasePriceType");
				BigDecimal taxPrice=new BigDecimal(0);
				if("1".equals(PurchasePriceType)){
					//最高采购价
					taxPrice=goodsInfo.getHighestbuyprice();
				}else if("2".equals(PurchasePriceType)){
					//最新采购价
					taxPrice=goodsInfo.getNewbuyprice();
				}
				//含税单价
				orderDetail.setTaxprice(taxPrice);
				//含税金额
				BigDecimal taxAmount=taxPrice.multiply(detail.getUnitnum());
				orderDetail.setTaxamount(taxAmount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				//税种
				orderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
				//无税金额 = 无税单价*数量
				BigDecimal notaxamount = getNotaxAmountByTaxAmount(orderDetail.getTaxamount(),orderDetail.getTaxtype());
				orderDetail.setNotaxamount(notaxamount);
				//税额
				orderDetail.setTax(orderDetail.getTaxamount().subtract(orderDetail.getNotaxamount()));
				orderDetail.setRemark(detail.getRemark());
			}
		}
		returnOrderMapper.addReturnOrderDetailByDeliveryOrderJob(orderDetail);
		
	}

	@Override
	public Map importReturnOrder(List<Map<String, Object>> list) throws Exception {
		String emptyindexs = "",sucbillid = "",msg = "",nulldata = "";
		int index = 2;
		Map billMap = new HashMap();
		for(Map<String,Object> map : list){
			String supplierid = (null != map.get("supplierid")) ? (String)map.get("supplierid") : "";
			String storagename = (null != map.get("storagename")) ? (String)map.get("storagename") : "";
			String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
			String businessdate = (null != map.get("businessdate")) ? (String)map.get("businessdate") : "null";
			GoodsInfo goodsInfoQ = getAllGoodsInfoByID(goodsid);

			//判断供应商编码、退货仓库、商品编码是否全部已输入
			if(StringUtils.isNotEmpty(supplierid) && StringUtils.isNotEmpty(storagename) && StringUtils.isNotEmpty(goodsid) && null != goodsInfoQ && "1".equals(goodsInfoQ.getState())){
				//将供应商、仓库相同的为同一张单据进行封装
				String key = supplierid+"_"+storagename+"_"+businessdate;
				if (billMap.containsKey(key)) {
					List detailList = (List) billMap.get(key);
					detailList.add(map);
					billMap.put(key, detailList);
				} else {
					List detailList = new ArrayList();
					detailList.add(map);
					billMap.put(key, detailList);
				}
			}else{
				if(StringUtils.isEmpty(emptyindexs)){
					emptyindexs = String.valueOf(index);
				}else{
					emptyindexs += "," + String.valueOf(index);
				}
			}
			index++;
		}
		Set set = billMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String key = entry.getKey();
			List<Map> detailList = (List<Map>) billMap.get(key);
			Map changemap = changeReturnOrderByKey(key, detailList);
			boolean nodataflag = changemap.get("nodataflag").equals(true);
			if(nodataflag){
				ReturnOrder returnOrder = (ReturnOrder) changemap.get("returnOrder");
				if (null != returnOrder) {
					Map map  = addReturnOrderAddDetail(returnOrder);
					if (map.get("flag").equals(true)) {
						String returnorderid = null != map.get("id") ? (String)map.get("id") : "";
						if (StringUtils.isEmpty(sucbillid)) {
							sucbillid = returnorderid;
						} else {
							sucbillid += "," + returnorderid;
						}
					}
				}
			}else{
				if(StringUtils.isEmpty(nulldata)){
					nulldata = key.split("_")[0];
				}else{
					nulldata += "," + key.split("_")[0];
				}
			}
		}
		if(StringUtils.isNotEmpty(emptyindexs)){
			msg = "第"+emptyindexs+"行中的数据，供应商编码、退货仓库、商品编码需必填或商品档案中要存在填写的启用商品，否则不允许导入数据。";
		}
		if(StringUtils.isNotEmpty(nulldata)){
			if(StringUtils.isNotEmpty(msg)){
				msg += "<br>" + "编号："+nulldata+"供应商或退货仓库不存在,不允许导入数据。";
			}else{
				msg = "编号："+nulldata+"供应商或退货仓库不存在,不允许导入数据。";
			}
		}
		if(StringUtils.isNotEmpty(sucbillid)){
			if(StringUtils.isNotEmpty(msg)){
				msg += "<br>" + "导入成功："+sucbillid.split(",").length+"条单据，编号："+sucbillid+"导入成功";
			}else{
				msg = "导入成功："+sucbillid.split(",").length+"条单据，编号："+sucbillid+"导入成功";
			}
		}
		Map map = new HashMap();
		map.put("msg",msg);
		map.put("sucbillid",sucbillid);
		return map;
	}

	/**
	 * 根据供应商编码、退货仓库、商品编码和导入的明细数据 转换成退货通知单
	 * @param key
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-21
	 */
	private Map changeReturnOrderByKey(String key,List<Map> list)throws  Exception{
		//判断是否允许输入小数位
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		Map returnMap = new HashMap();
		//判断供应商、仓库、商品是否全部存在，其中一个不存在不允许导入数据
		boolean nodataflag = true;
		String[] keyArr = key.split("_");
		String supplierid = keyArr[0];
		String storagename = keyArr[1];
		String businessdate = keyArr[2];
		if("null".equals(businessdate)){
			businessdate = CommonUtils.getTodayDataStr();
		}
		BuySupplier buySupplier = getSupplierInfoById(supplierid);
		StorageInfo storageInfo = getStorageInfoByName(storagename);
		if(null == buySupplier || null == storageInfo){
			nodataflag = false;
			returnMap.put("nodataflag",nodataflag);
			return returnMap;
		}
		ReturnOrder returnOrder = new ReturnOrder();
		returnOrder.setBusinessdate(businessdate);
		returnOrder.setStatus("2");
		returnOrder.setRemark("excel导入");
		returnOrder.setSupplierid(supplierid);
		returnOrder.setStorageid(storageInfo.getId());
		returnOrder.setBuydeptid(buySupplier.getBuydeptid());
		returnOrder.setBuyuserid(buySupplier.getBuyuserid());
		SysUser sysUser = getSysUser();
		returnOrder.setAdduserid(sysUser.getUserid());
		returnOrder.setAddusername(sysUser.getName());
		returnOrder.setAdddeptid(sysUser.getDepartmentid());
		returnOrder.setAdddeptname(sysUser.getDepartmentname());
		returnOrder.setAddtime(new Date());
		//生成单据明细信息
		List<ReturnOrderDetail> detailList = new ArrayList<ReturnOrderDetail>();
		//采购价取最高采购价还是最新采购价做系统参数,1最高采购价 2最新采购价
		String purchasePriceType= getSysParamValue("PurchasePriceType");
		if(null==purchasePriceType || "".equals(purchasePriceType.trim())){
			purchasePriceType="1";
		}
		purchasePriceType=purchasePriceType.trim();
		for(Map map : list){
			//获取商品详情
			String goodsid = (null != map.get("goodsid")) ? (String)map.get("goodsid") : "";
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null != goodsInfo){
				BigDecimal unitnum = (null != map.get("unitnum")) ? new BigDecimal((String) map.get("unitnum")) : BigDecimal.ZERO;// 数量
				String remark = (null != map.get("remark")) ? (String) map.get("remark") : "";
				BigDecimal taxprice = (null != map.get("taxprice")) ? new BigDecimal((String) map.get("taxprice")) : new BigDecimal(-1);// 单价
				unitnum = unitnum.abs().setScale(decimalScale, BigDecimal.ROUND_HALF_UP);

				ReturnOrderDetail returnOrderDetail = new ReturnOrderDetail();
				returnOrderDetail.setGoodsid(goodsInfo.getId());
				returnOrderDetail.setGoodsInfo(goodsInfo);
				returnOrderDetail.setStorageid(storageInfo.getId());
				returnOrderDetail.setUnitid(goodsInfo.getMainunit());
				returnOrderDetail.setUnitname(goodsInfo.getMainunitName());
				returnOrderDetail.setRemark(remark);
				returnOrderDetail.setAuxunitid(goodsInfo.getAuxunitid());
				returnOrderDetail.setAuxunitname(goodsInfo.getAuxunitname());
				returnOrderDetail.setUnitnum(unitnum);

				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(returnOrderDetail.getGoodsid(), returnOrderDetail.getAuxunitid(), returnOrderDetail.getUnitnum());
				if(auxmap.containsKey("auxInteger")){
					returnOrderDetail.setAuxnum(new BigDecimal((String) auxmap.get("auxInteger")));
				}
				if(auxmap.containsKey("auxremainder")){
					returnOrderDetail.setAuxremainder(new BigDecimal((String) auxmap.get("auxremainder")));
				}
				if(auxmap.containsKey("auxnumdetail")){
					returnOrderDetail.setAuxnumdetail((String) auxmap.get("auxnumdetail"));
				}
				if(taxprice.compareTo(new BigDecimal(-1) )== 0){
					//1最高采购价 2最新采购价
					if("1".equals(purchasePriceType)){
						returnOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());
					}else if("2".equals(purchasePriceType)){
						returnOrderDetail.setTaxprice(goodsInfo.getNewbuyprice());
					}
				}else{
					returnOrderDetail.setTaxprice(taxprice);
				}

				BigDecimal taxamount=returnOrderDetail.getTaxprice().multiply(returnOrderDetail.getUnitnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
				returnOrderDetail.setTaxamount(taxamount);

				returnOrderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
				BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, returnOrderDetail.getTaxtype());
				returnOrderDetail.setTaxamount(taxamount);
				returnOrderDetail.setNotaxamount(notaxamount);
				if (null != notaxamount && notaxamount.compareTo(BigDecimal.ZERO) != 0) {
					BigDecimal notaxprice = notaxamount.divide(returnOrderDetail.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP);
					returnOrderDetail.setNotaxprice(notaxprice);
				}

				returnOrderDetail.setTax(returnOrderDetail.getTaxamount().subtract(returnOrderDetail.getNotaxamount()));
				detailList.add(returnOrderDetail);
			}
		}
		returnOrder.setReturnOrderDetailList(detailList);
		returnMap.put("nodataflag",nodataflag);
		returnMap.put("returnOrder",returnOrder);
		return returnMap;
	}
}

