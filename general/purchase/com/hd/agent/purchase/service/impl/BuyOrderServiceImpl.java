/**
 * @(#)BuyOrderServiceImpl.java
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
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.purchase.model.PlannedOrderDetail;
import com.hd.agent.purchase.service.IBuyOrderService;
import com.hd.agent.purchase.service.ext.IPurchaseSelfExtService;
import com.hd.agent.report.model.StorageBuySaleReport;
import com.hd.agent.storage.service.IStorageForPurchaseService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 采购订单服务实现
 * 
 * @author zhanghonghui
 */
public class BuyOrderServiceImpl extends BasePurchaseServiceImpl implements IBuyOrderService {


	private IPurchaseSelfExtService purchaseSelfExtService;
	
	private IStorageForPurchaseService storageForPurchaseService;

	public IPurchaseSelfExtService getPurchaseSelfExtService() {
		return purchaseSelfExtService;
	}

	public void setPurchaseSelfExtService(
			IPurchaseSelfExtService purchaseSelfExtService) {
		this.purchaseSelfExtService = purchaseSelfExtService;
	}

	public IStorageForPurchaseService getStorageForPurchaseService() {
		return storageForPurchaseService;
	}

	public void setStorageForPurchaseService(
			IStorageForPurchaseService storageForPurchaseService) {
		this.storageForPurchaseService = storageForPurchaseService;
	}

	/**
	 * 添加单采购订单
	 */
	public boolean insertBuyOrder(BuyOrder buyOrder) throws Exception{
		return buyOrderMapper.insertBuyOrder(buyOrder)>0;
	}

	@Override
	public boolean addBuyOrderAddDetail(BuyOrder buyOrder) throws Exception{
		buyOrder.setId(getBillNumber(buyOrder, "t_purchase_buyorder"));
		if(StringUtils.isEmpty(buyOrder.getId())){
			return false;
		}
		if(StringUtils.isNotEmpty(buyOrder.getBillno())){
			if(!"1".equals(buyOrder.getSource())){
				buyOrder.setSource("1");
			}
		}
		boolean flag=insertBuyOrder(buyOrder);
		List<BuyOrderDetail> list=buyOrder.getBuyOrderDetailList();
		if(flag && list!=null && list.size()>0){
			int iseq=1;
			for(BuyOrderDetail item : list){	
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
				item.setOrderid(buyOrder.getId());
				item.setSeq(iseq);
				insertBuyOrderDetail(item);
				iseq=iseq+1;
                if(null == item.getBoxprice() && null == item.getNoboxprice() && item.getUnitnum() != null){
                    if(null == item.getTaxprice()){
                        item.setBoxprice(new BigDecimal(0));
                    }else{
                        item.setBoxprice(item.getTaxprice().multiply(item.getUnitnum().divide(item.getTotalbox(),6, BigDecimal.ROUND_HALF_UP)));
                    }
                    if(null == item.getNotaxprice()){
                        item.setNoboxprice(new BigDecimal(0));
                    }else{
                        item.setNoboxprice(item.getNotaxprice().multiply(item.getUnitnum().divide(item.getTotalbox(),6, BigDecimal.ROUND_HALF_UP)));
                    }
                }
			}
		}
		if(StringUtils.isNotEmpty(buyOrder.getBillno())){
			updateBasePlannedOrderRefer(buyOrder.getBillno(), "1");
		}
		return flag;		
	}
	@Override
	public BuyOrder showBuyOrderAndDetail(String id) throws Exception{
		BuyOrder pOrder= buyOrderMapper.getBuyOrder(id);
		if(null!=pOrder){
			DepartMent departMent =null;
			TaxType taxType=null;
			MeteringUnit meteringUnit=null;
			
			List<BuyOrderDetail> list=showPureBuyOrderDetailListByOrderId(pOrder.getId());
			if(null!=list && list.size()>0){
				for(BuyOrderDetail item :list){
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
						BigDecimal boxprice = goodsInfo.getBoxnum().multiply(item.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
						BigDecimal noboxprice = goodsInfo.getBoxnum().multiply(item.getNotaxprice()).setScale(6, BigDecimal.ROUND_HALF_UP);
						item.setBoxprice(boxprice);
						item.setNoboxprice(noboxprice);
                        if(null != goodsInfo.getGrossweight() && null != item.getUnitnum()){
                            BigDecimal goodsBoxweight = item.getUnitnum().multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP);
                            item.setTotalboxweight(goodsBoxweight);
                        }
                        if(null != goodsInfo.getSinglevolume() && null != item.getUnitnum()){
                            BigDecimal goodsboxvolume = item.getUnitnum().multiply(goodsInfo.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP);
                            item.setTotalboxvolume(goodsboxvolume);
                        }

					}
				}
				pOrder.setBuyOrderDetailList(list);
			}else{
				pOrder.setBuyOrderDetailList(new ArrayList<BuyOrderDetail>());
			}
		}
		return pOrder;
	}

	@Override
	public boolean updateBuyOrderAddDetail(BuyOrder buyOrder) throws Exception{
		BuyOrder oldBuyOrder=showBuyOrderByDataAuth(buyOrder.getId());
		if(null==oldBuyOrder || "3".equals(oldBuyOrder.getStatus()) || "4".equals(oldBuyOrder.getStatus())){
			return false;
		}
		boolean flag=buyOrderMapper.updateBuyOrder(buyOrder)>0;
		if(flag){			
			deleteBuyOrderDetailByOrderid(buyOrder.getId());
			List<BuyOrderDetail> list=buyOrder.getBuyOrderDetailList();
			if(flag && list!=null && list.size()>0){
				int iseq=1;
				for(BuyOrderDetail item : list){		
					//计算辅单位数量
					Map auxmap = countGoodsInfoNumber(item.getGoodsid(), item.getAuxunitid(), item.getUnitnum());
					if(auxmap.containsKey("auxnum")){
						item.setTotalbox((BigDecimal) auxmap.get("auxnum"));
					}
					item.setOrderid(buyOrder.getId());
					item.setSeq(iseq);
					insertBuyOrderDetail(item);
					iseq=iseq+1;
				}
			}
		}
		return flag;
	}

	@Override
	public boolean deleteBuyOrderAndDetail(String id) throws Exception{
		BuyOrder buyOrder=showBuyOrderByDataAuth(id);
		if(null==buyOrder){
			return false;
		}
		boolean flag=false;
		if(StringUtils.isEmpty(buyOrder.getStatus()) || "1".equals(buyOrder.getStatus()) || "2".equals(buyOrder.getStatus())){
			flag=buyOrderMapper.deleteBuyOrder(id)>0;
			if(flag){
				buyOrderMapper.deleteBuyOrderDetailByOrderid(id);
				if("1".equals(buyOrder.getSource()) && StringUtils.isNotEmpty(buyOrder.getBillno())){
					purchaseSelfExtService.updatePlannedOrderAuditStatus(buyOrder.getBillno());
					updateBasePlannedOrderRefer(buyOrder.getBillno(), "0");
				}
			}
		}
		if(StringUtils.isNotEmpty(buyOrder.getBillno())){
			updateBasePlannedOrderRefer(buyOrder.getBillno(), "0");
		}
		return flag;
	}
	@Override
	public BuyOrder showPureBuyOrder(String id) throws Exception{
		return buyOrderMapper.getBuyOrder(id);
	}
	@Override
	public BuyOrder showBuyOrder(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String cols = getAccessColumnList("t_purchase_buyorder",null);
		String datasql = getDataAccessRule("t_purchase_buyorder",null);
		map.put("cols", cols);
		map.put("authDataSql", datasql);		
		return buyOrderMapper.getBuyOrderBy(map);
	}
	@Override
	public BuyOrder showBuyOrderByDataAuth(String id) throws Exception{
		Map map=new HashMap();
		map.put("id", id.trim());
		String datasql = getDataAccessRule("t_purchase_buyorder",null);
		map.put("authDataSql", datasql);		
		return buyOrderMapper.getBuyOrderBy(map);		
	}
	
	@Override
	public PageData showBuyOrderPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_purchase_buyorder",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_purchase_buyorder",null);
		pageMap.setDataSql(sql);
		List<BuyOrder> porderList=buyOrderMapper.getBuyOrderPageList(pageMap);
		if(porderList!=null && porderList.size()>0){
			DepartMent departMent =null;
			Personnel personnel = null;
			BuySupplier buySupplier=null;
			Contacter contacter =null;
			Map map=null;
			for(BuyOrder item : porderList){
                BigDecimal totalboxweight = BigDecimal.ZERO;
                BigDecimal totalboxvolume = BigDecimal.ZERO;
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
				if(pageMap.getCondition().containsKey("showdetailcount") && null!=pageMap.getCondition().get("showdetailcount") && "1".equals(pageMap.getCondition().get("showdetailcount").toString())){
					map=new HashMap();
					map=buyOrderMapper.getBuyOrderDetailTotal(item.getId());
					if(null!=map){
						if(map.containsKey("taxamount") && null!=map.get("taxamount")){
							item.setField01(map.get("taxamount").toString());
						}
						if(map.containsKey("notaxamount") && null!=map.get("notaxamount")){
							item.setField02(map.get("notaxamount").toString());
						}
						if(map.containsKey("tax") && null!=map.get("tax")){
							item.setField03(map.get("tax").toString());
						}
					}
				}
				List<BuyOrderDetail> detailList = buyOrderMapper.getBuyOrderDetailListByOrderid(item.getId());
				for(BuyOrderDetail detail : detailList){
					if(StringUtils.isNotEmpty(detail.getGoodsid())){
						GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
						if(null != goodsInfo){
							if(null != goodsInfo.getGrossweight()){
								BigDecimal goodsBoxweight = detail.getUnitnum().multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP);
								detail.setTotalboxweight(goodsBoxweight);
								totalboxweight = totalboxweight.add(goodsBoxweight);
							}
							if(null != goodsInfo.getSinglevolume()){
								BigDecimal goodsboxvolume = detail.getUnitnum().multiply(goodsInfo.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP);
								detail.setTotalboxvolume(goodsboxvolume);
								totalboxvolume = totalboxvolume.add(goodsboxvolume);
							}
						}
					}
				}
				//每张订单总重量 总体积 合计
				item.setTotalboxweight(totalboxweight);
				item.setTotalboxvolume(totalboxvolume);
			}
		}
		PageData pageData=new PageData(buyOrderMapper.getBuyOrderPageCount(pageMap), 
						porderList, pageMap);
		return pageData;
	}
	/**
	 * 根据参数更新采购订单<br/>
	 * map中的参数：<br/>
	 * 更新参数条件：<br/>
	 * buyOrder :采购订单对象<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * @param
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	@Override
	public boolean updateBuyOrderBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_purchase_buyorder",null);
		map.put("authDataSql", sql);
		return buyOrderMapper.updateBuyOrderBy(map)>0;
	}
	/**
	 * 根据上游单据编号显示采购订单及其明细
	 * @param billno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-31
	 */
	@Override
	public BuyOrder showBuyOrderAndDetailByBillno(String billno) throws Exception{
		return purchaseSelfExtService.showBuyOrderAndDetailReferByBillno(billno);
	}
	/**
	 * 审核
	 * @param id 编号
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	@Override
	public Map auditBuyOrder(String id) throws Exception{
		Map map=new HashMap();
		Map queryMap=new HashMap();
		map.put("flag", false);
		BuyOrder buyOrder=showBuyOrderByDataAuth(id);
		if(null==buyOrder){
			map.put("flag", false);
			return map;
		}else if(null==buyOrder.getStorageid() || "".equals(buyOrder.getStorageid())){
			map.put("flag", false);
			map.put("msg", "未指定入库仓库！");
			return map;
		}
		
		boolean flag = false;
		String nextbillno="";
		SysUser sysUser = getSysUser();
		if("2".equals(buyOrder.getStatus())){

			BuyOrder bOrder=new BuyOrder();
			bOrder.setId(id);
			bOrder.setStatus("3");
			bOrder.setAudituserid(sysUser.getUserid());
			bOrder.setAuditusername(sysUser.getName());
			bOrder.setAudittime(new Date());
			bOrder.setBusinessdate(getAuditBusinessdate(buyOrder.getBusinessdate()));
			queryMap.clear();
			queryMap.put("buyOrder", bOrder);
			queryMap.put("id", id);
			flag=updateBuyOrderBy(queryMap);
			if(flag){
				List<BuyOrderDetail> list=showPureBuyOrderDetailListByOrderId(id);
				if(null != list){
					//初始化未入库状态
					for(BuyOrderDetail item : list){
						item.setUnstockunitnum(item.getUnitnum());
						item.setUnstockauxnum(item.getAuxnum());
						item.setUnstocktaxamount(item.getTaxamount());
						item.setUnstocknotaxamount(item.getNotaxamount());
					}
					///Field08用于采购分析表条件,太长了。
					//buyOrder.setField08(null);
					nextbillno=storageForPurchaseService.addPurchaseEnterByBuyOrder(buyOrder, list);
					if(!"1".equals(buyOrder.getOrderappend())){
						updateBuyOrderRefer(buyOrder.getId(),"1");
					}
					if("1".equals(buyOrder.getSource()) && StringUtils.isNotEmpty(buyOrder.getBillno())){
						purchaseSelfExtService.updatePlannedOrderDetailWriteBack(buyOrder.getBillno(),list);
					}
				}
			}
		}
		map.put("flag", flag);
		map.put("billid",nextbillno);
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
	public Map oppauditBuyOrder(String id) throws Exception{
		Map map=new HashMap();
		Map queryMap=new HashMap();
		BuyOrder buyOrder=showBuyOrderByDataAuth(id);
		if(null==buyOrder){
			map.put("msg", "未能找到相关采购订单信息");
			map.put("flag", false);
			return map;
		}
		boolean flag = false;
		SysUser sysUser = getSysUser();
		if("3".equals(buyOrder.getStatus())){

			flag=storageForPurchaseService.deletePurchaseEnterBySourceid(buyOrder.getId());
			if(flag){
				BuyOrder bOrder=new BuyOrder();
				bOrder.setId(id);
				bOrder.setStatus("2");
				bOrder.setAudituserid(sysUser.getUserid());
				bOrder.setAuditusername(sysUser.getName());
				bOrder.setAudittime(new Date());
				queryMap.put("buyOrder", bOrder);
				queryMap.put("id", id);
				flag=updateBuyOrderBy(queryMap);
				if(flag){
					if(StringUtils.isNotEmpty(buyOrder.getBillno())){
						List<BuyOrderDetail> list=showPureBuyOrderDetailListByOrderId(id);
						purchaseSelfExtService.updatePlannedOrderDetailReWriteBack(buyOrder.getBillno(),list);
						if(!"1".equals(buyOrder.getOrderappend())){
							updateBuyOrderRefer(buyOrder.getId(),"0");
						}
					}
				}
			}
		}
		map.put("flag", flag);
		return map;
	}
	
	@Override
	public boolean auditWorkflowBuyOrder(String id) throws Exception{
		BuyOrder buyOrder=showPureBuyOrder(id);
		boolean flag=false;
		SysUser sysUser = getSysUser();
		if("2".equals(buyOrder.getStatus())){
			BuyOrder bOrder=new BuyOrder();
			bOrder.setId(id);
			bOrder.setStatus("3");
			bOrder.setAudituserid(sysUser.getUserid());
			bOrder.setAuditusername(sysUser.getName());
			bOrder.setAudittime(new Date());
			
			flag=updateBuyOrderStatus(buyOrder);
			if(flag){
				List<BuyOrderDetail> list=showPureBuyOrderDetailListByOrderId(id);
				if(null != list){
					//初始化未入库状态
					for(BuyOrderDetail item : list){
						item.setUnstockunitnum(item.getUnitnum());
						item.setUnstockauxnum(item.getAuxnum());
						item.setUnstocktaxamount(item.getTaxamount());
						item.setUnstocknotaxamount(item.getNotaxamount());
					}				
					storageForPurchaseService.addPurchaseEnterByBuyOrder(buyOrder, list);
					if(!"1".equals(buyOrder.getOrderappend())){
						updateBuyOrderRefer(buyOrder.getId(),"1");
					}
					if("1".equals(buyOrder.getSource()) && StringUtils.isNotEmpty(buyOrder.getBillno())){
						purchaseSelfExtService.updatePlannedOrderDetailWriteBack(buyOrder.getBillno(),list);
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 设置采购订单引用 标志
	 * @param id
	 * @param isrefer 1表示引用，0表示未引用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-29
	 */
	@Override
	public boolean updateBuyOrderRefer(String id,String isrefer) throws Exception{
		if(null==id || "".equals(id.trim())){
			return false;
		}
		boolean flag=false;
		BuyOrder bOrder=new BuyOrder();
		bOrder.setIsrefer(isrefer);
		bOrder.setId(id);
		flag = buyOrderMapper.updateBuyOrderStatus(bOrder)>0;
		return flag;
	}

	@Override
	public boolean updateBuyOrderStatus(BuyOrder buyOrder) throws Exception{
		return buyOrderMapper.updateBuyOrderStatus(buyOrder)>0;
	}
	@Override
	public boolean deleteBuyOrder(String id) throws Exception{
		return buyOrderMapper.deleteBuyOrder(id)>0;
	}
	
	@Override
	public BuyOrder showBuyOrderByBillno(String billno){
		return buyOrderMapper.getBuyOrderByBillno(billno);
	}

	//------------------------------------------------------------------------//
	//采购订单详细
	//-----------------------------------------------------------------------//

	@Override
	public boolean insertBuyOrderDetail(BuyOrderDetail buyOrderDetail) throws Exception{
		return buyOrderMapper.insertBuyOrderDetail(buyOrderDetail)>0;
	}

	@Override
	public List showBuyOrderDetailListByOrderId(String orderid) throws Exception{
		if(null==orderid || "".equals(orderid)){
			return new ArrayList<BuyOrderDetail>();
		}
		orderid=orderid.trim();
		Map map=new HashMap();
		map.put("orderid", orderid);
		String cols = getAccessColumnList("t_purchase_buyorder_detail",null);
		map.put("cols", cols);
		String sql = getDataAccessRule("t_purchase_buyorder_detail",null);
		map.put("authDataSql", sql);
		List<BuyOrderDetail> list= buyOrderMapper.getBuyOrderDetailListBy(map);
		if(null!=list && list.size()>0){
			TaxType taxType=null;
			MeteringUnit meteringUnit=null;
			for(BuyOrderDetail item :list){
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
	 * 采购订单明细分页列表，不判断权限
	 * @param orderid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-14
	 */
	@Override
	public List showPureBuyOrderDetailListByOrderId(String orderid) throws Exception{
		return showBasePureBuyOrderDetailListByOrderId(orderid);
	}
	@Override
	public List<BuyOrderDetail> showBuyOrderDetailListBy(Map<String, Object> map) throws Exception{
		if(!(map.containsKey("ischeckauth") && 
			null!=map.get("ischeckauth") && 
			"0".equals(map.get("ischeckauth").toString()))){
			String sql = getDataAccessRule("t_purchase_plannedorder",null);
			map.put("authDataSql", sql);
		}
		List<BuyOrderDetail> list= buyOrderMapper.getBuyOrderDetailListBy(map);
		if(null!=list && list.size()>0){
			TaxType taxType=null;
			MeteringUnit meteringUnit=null;
			for(BuyOrderDetail item :list){
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

	@Override
	public boolean deleteBuyOrderDetailByOrderid(String orderid) throws Exception{
		return buyOrderMapper.deleteBuyOrderDetailByOrderid(orderid)>0;
	}
	/**
	 * 更新采购明细
	 * @param
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-5-18
	 */
	@Override
	public int updateBuyOrderDetail(BuyOrderDetail buyOrderDetail) throws Exception{
		return buyOrderMapper.updateBuyOrderDetail(buyOrderDetail);
	}
	/**
	 * 检查如果有上游单据，采购订单数量不能大于采购计划单数量
	 * @param billdetailno
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-6
	 */
	@Override
	public BigDecimal getMaxBuyUnitnum(String billdetailno) throws Exception{		
		if(null==billdetailno || "".equals(billdetailno.trim())){
			return null;
		}
		PlannedOrderDetail poDetail=showBasePurePlannedOrderDetail(billdetailno.trim());
		if(null==poDetail){
			return null;
		}
		return poDetail.getUnitnum();
	}
	
	@Override
	public BuyOrderDetail showPureBuyOrderDetail(String id) throws Exception{
		return buyOrderMapper.getBuyOrderDetail(id);
	}
	
	@Override
	public boolean updateBuyOrderDetailWriteBack(BuyOrderDetail buyOrderDetail) throws Exception{
		return buyOrderMapper.updateBuyOrderDetailWriteBack(buyOrderDetail)>0;
	}
	@Override
	public boolean submitBuyOrderProcess(String title, String userId, String processDefinitionKey, String businessKey, Map<String, Object> variables) throws Exception{
		return false;
	}
	@Override
	public List getBuyOrderDetailExport(PageMap pageMap) throws Exception{
		List<Map> list=buyOrderMapper.getBuyOrderDetailExport(pageMap);
		for(Map itemMap : list){
			String tmp="";
			tmp=(String) itemMap.get("buydeptid");
			if(null!=tmp && !"".equals(tmp.trim())){
				DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(tmp.trim());
				if(departMent != null){
					itemMap.put("buydeptname",departMent.getName());
				}					
			}
			tmp=(String) itemMap.get("buyuserid");
			if(null!=tmp && !"".equals(tmp.trim())){
				Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(tmp.trim());
				if(personnel != null){
					itemMap.put("buyusername",personnel.getName());
				}
			}
			tmp=(String) itemMap.get("supplierid");
			if(null!=tmp && !"".equals(tmp.trim())){
				BuySupplier buySupplier=getSupplierInfoById(tmp.trim());
				if(null!=buySupplier){
					itemMap.put("suppliername",buySupplier.getName());
				}
			}
			tmp=(String) itemMap.get("goodsid");
			if(null!=tmp && !"".equals(tmp.trim())){
				GoodsInfo goodsInfo =getGoodsInfoByID(tmp.trim());
				if(null!=goodsInfo){
					//itemMap.put("goodsInfo", goodsInfo);
					itemMap.put("goodsname", goodsInfo.getName());
					itemMap.put("model", goodsInfo.getModel());
					itemMap.put("barcode", goodsInfo.getBarcode());
					itemMap.put("brandname", goodsInfo.getBrandName());
				}
			}
			tmp=(String) itemMap.get("taxtype");
			if(null!=tmp && !"".equals(tmp.trim())){
				TaxType taxType=getTaxType(tmp.trim());
				if(null!=taxType){
					itemMap.put("taxtypename",taxType.getName());
				}
			}
			tmp=(String) itemMap.get("auxunitid");
			if(null!=tmp && !"".equals(tmp.trim())){
				MeteringUnit meteringUnit=getMeteringUnitById(tmp.trim());
				if(null!=meteringUnit){
					itemMap.put("auxunitname",meteringUnit.getName());
				}
			}
		}
		return list;
	}
	@Override
	public List showBuyOrderListBy(Map map) throws Exception{
		String datasql = getDataAccessRule("t_purchase_buyorder",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<BuyOrder> list=buyOrderMapper.showBuyOrderListBy(map);
		for(BuyOrder item : list){
			if(StringUtils.isNotEmpty(item.getSupplierid())){
				BuySupplier buySupplier=getSupplierInfoById(item.getSupplierid());
				if(null!=buySupplier){
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
				List<BuyOrderDetail> detailList=showPureBuyOrderDetailListByOrderId(item.getId());
				if(null!=list && list.size()>0){
					for(BuyOrderDetail detail :detailList){
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
					item.setBuyOrderDetailList(detailList);
				}
			}
		}
		return list;
	}
	@Override
	public boolean updateOrderPrinttimes(BuyOrder buyOrder) throws Exception{
		return buyOrderMapper.updateOrderPrinttimes(buyOrder)>0;
	}
	
	@Override
	public Map addBuyOrderByReport(List<StorageBuySaleReport> list,String remark,String queryForm)
			throws Exception {
		Map returnMap = new HashMap();
		Map supplierMap = new HashMap();
        String storageid = "";
		for(StorageBuySaleReport storageBuySaleReport : list){
			String supplierid="";
            storageid = storageBuySaleReport.getStorageid();
			//有第二供应商项目，先判断传入的供应商是否有
			if(StringUtils.isNotEmpty(storageBuySaleReport.getSupplierid())){
				supplierid=storageBuySaleReport.getSupplierid();
			}else{
				//没有第二供应商项目的，代码
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
				supplierid = goodsInfo.getDefaultsupplier();
				if(null==supplierid || "".equals(supplierid)){
					supplierid = "";
				}
				//没有第二供应商项目的，代码
			}
			if(supplierMap.containsKey(supplierid)){
				List detailList = (List) supplierMap.get(supplierid);
				detailList.add(storageBuySaleReport);
				supplierMap.put(supplierid, detailList);
			}else{
				List detailList = new ArrayList();
				detailList.add(storageBuySaleReport);
				supplierMap.put(supplierid, detailList);
			}
		}
		Set set = supplierMap.entrySet();
		Iterator it = set.iterator();
		boolean flag = false;
		int successNum = 0;
		String repeatVal = "";
		String orderid ="";
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String supplierid = entry.getKey();
			List<StorageBuySaleReport> detailList = (List<StorageBuySaleReport>) supplierMap.get(supplierid);
			Map planorderMap = changeBuyOrderBySupplierid(supplierid, detailList);
			if(null!=planorderMap && planorderMap.containsKey("buyOrder")){
                BuyOrder buyOrder = (BuyOrder) planorderMap.get("buyOrder");
                //当前用户有默认仓库则选择用户对应的默认仓库
                String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
                if("1".equals(OpenDeptStorage)){
                    if(null != storageid && storageid != ""){
                        buyOrder.setStorageid(storageid);
                    }
                }
				buyOrder.setRemark(remark);
				buyOrder.setField08(queryForm);
				boolean addflag = false;
				if(buyOrder.getBuyOrderDetailList().size()>0){
					addflag = addBuyOrderAddDetail(buyOrder);
				}
				if(addflag){
					successNum += buyOrder.getBuyOrderDetailList().size();
					if("".equals(orderid)){
						orderid = buyOrder.getId();
					}else{
						orderid += "," + buyOrder.getId();
					}
				}
			}
			
			String goodsid  = (String) planorderMap.get("goodsid");
			if(null!=goodsid && !"".equals(goodsid)){
				if("".equals(repeatVal)){
					repeatVal = "商品："+goodsid;
				}else{
					repeatVal += goodsid;
				}
			}
		}
		if(!"".equals(orderid)){
			flag = true;
		}else{
			flag = false;
		}
		if(!"".equals(repeatVal)){
			repeatVal +="生成失败，商品采购数量为0或者该商品未指定供应商";
		}
		int failureNum = list.size()-successNum;
		returnMap.put("flag", flag);
		returnMap.put("successNum", successNum);
		returnMap.put("failureNum", failureNum);
		returnMap.put("errormsg", repeatVal);
		returnMap.put("orderid", orderid);
		returnMap.put("msg", "生成采购订单："+orderid);
		return returnMap;
	}
	
	/**
	 * 进销存报表数据转采购订单
	 * @param supplierid
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public Map changeBuyOrderBySupplierid(String supplierid,List<StorageBuySaleReport> detailList) throws Exception{
		SysUser sysUser = getSysUser();
		Map returnMap = new HashMap();
		BuySupplier buySupplier = getSupplierInfoById(supplierid);
		String goodsids = "";
		if(null!=buySupplier){
			BuyOrder buyOrder = new BuyOrder();
			/*
			if (isAutoCreate("t_purchase_buyorder")) {
				// 获取自动编号
				buyOrder.setId( getAutoCreateSysNumbderForeign(buyOrder, "t_purchase_buyorder"));
			}else{
		    	buyOrder.setId("ST"+CommonUtils.getDataNumberSendsWithRand());
			}
			*/
			//临时编号
			buyOrder.setId("ST"+CommonUtils.getDataNumberSendsWithRand());
			buyOrder.setBusinessdate(CommonUtils.getTodayDataStr());
			buyOrder.setStatus("2");
			buyOrder.setSupplierid(supplierid);
			buyOrder.setStorageid(buySupplier.getStorageid());
			buyOrder.setHandlerid(buySupplier.getContact());
			buyOrder.setBuydeptid(buySupplier.getBuydeptid());
			buyOrder.setBuyuserid(buySupplier.getBuyuserid());
			
			buyOrder.setPaytype(buySupplier.getPaytype());
			buyOrder.setSettletype(buySupplier.getSettletype());
			buyOrder.setOrderappend(buySupplier.getOrderappend());
			
			buyOrder.setAdduserid(sysUser.getUserid());
			buyOrder.setAddusername(sysUser.getName());
			buyOrder.setAdddeptid(sysUser.getDepartmentid());
			buyOrder.setAdddeptname(sysUser.getDepartmentname());
			buyOrder.setAddtime(new Date());
			
			//取系统参数中的要求到货日期
			String arrivedateDays = getSysParamValue("ARRIVEDAYS");
			int days = 2;
			if(null!=arrivedateDays && !"".equals(arrivedateDays)){
				days = new Integer(arrivedateDays);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.add(calendar.DATE, days);
			String arrivedate=CommonUtils.dataToStr(calendar.getTime(), "yyyy-MM-dd");
			buyOrder.setArrivedate(arrivedate);
						
			List buyDetailList = new ArrayList();
			
			/**
			 * 有采购价：采购价取最高采购价还是最新采购价
			 */
			
			String purchasePriceType= getSysParamValue("PurchasePriceType");
			if(null==purchasePriceType || "".equals(purchasePriceType.trim())){
				purchasePriceType="1";
			}
			purchasePriceType=purchasePriceType.trim();
			
			/**
			 * 有采购价：采购价取最高采购价还是最新采购价
			 */
			
			for(StorageBuySaleReport storageBuySaleReport : detailList){
				BuyOrderDetail buyOrderDetail = new BuyOrderDetail();
				buyOrderDetail.setOrderid(buyOrder.getId());
				buyOrderDetail.setGoodsid(storageBuySaleReport.getGoodsid());
				GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
				if(null!=goodsInfo 
						&& null!=goodsInfo.getBoxnum()
						&& BigDecimal.ZERO.compareTo(goodsInfo.getBoxnum())!=0
						&& null!=storageBuySaleReport.getBuyinnum() 
						&& BigDecimal.ZERO.compareTo(storageBuySaleReport.getBuyinnum())==-1){
					
					buyOrderDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
					buyOrderDetail.setUnitid(goodsInfo.getMainunit());
					buyOrderDetail.setUnitname(goodsInfo.getMainunitName());
					
					buyOrderDetail.setArrivedate(arrivedate);
					
					BigDecimal unitnum=storageBuySaleReport.getBuyinnum();
					buyOrderDetail.setUnitnum(unitnum);
					//辅单位转主单位
					Map changeResultMap = countGoodsInfoNumber(goodsInfo.getId(),storageBuySaleReport.getAuxunitid(),unitnum);
					BigDecimal auxunitnum = BigDecimal.ZERO;
					if(changeResultMap.containsKey("auxInteger")){
						String auxIntegerStr=(String)changeResultMap.get("auxInteger");
						if(null!=auxIntegerStr && StringUtils.isNumeric(auxIntegerStr.trim())){
							auxunitnum=new BigDecimal(auxIntegerStr.trim());
						}
					}
					buyOrderDetail.setAuxnum(auxunitnum);
					buyOrderDetail.setAuxunitid(storageBuySaleReport.getAuxunitid());
					String auxunitname=(String)changeResultMap.get("auxunitname");
					if(null!=auxunitname && !"".equals(auxunitname.trim())){
						buyOrderDetail.setAuxunitname(auxunitname);						
					}else{
						buyOrderDetail.setAuxunitname(storageBuySaleReport.getAuxunitname());						
					}

					BigDecimal auxremainder=BigDecimal.ZERO;
					if(changeResultMap.containsKey("auxremainder")){
						String auxremainderStr=(String)changeResultMap.get("auxremainder");
						if(StringUtils.isNotEmpty(auxremainderStr)){
							auxremainder=new BigDecimal(auxremainderStr.trim());
						}
					}
					buyOrderDetail.setAuxremainder(auxremainder);
					if(changeResultMap.containsKey("auxnum")){
						buyOrderDetail.setTotalbox((BigDecimal) changeResultMap.get("auxnum"));
					}
					String auxnumdetail=(String)changeResultMap.get("auxnumdetail");
					buyOrderDetail.setAuxnumdetail(auxnumdetail);
					
					
					/**
					 * 有采购价：采购价取最高采购价还是最新采购价
					 */
					if("1".equals(purchasePriceType)){
						buyOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());						
					}else if("2".equals(purchasePriceType)){
						buyOrderDetail.setTaxprice(goodsInfo.getNewbuyprice());						
					}else{
						buyOrderDetail.setTaxprice(goodsInfo.getHighestbuyprice());						
					}
					/**
					 * 有采购价：采购价取最高采购价还是最新采购价
					 */
					
					buyOrderDetail.setTaxamount(buyOrderDetail.getUnitnum().multiply(buyOrderDetail.getTaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					BigDecimal notaxamount = getNotaxAmountByTaxAmount(buyOrderDetail.getTaxamount(),buyOrderDetail.getTaxtype());
					if(buyOrderDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
						BigDecimal notaxprice = notaxamount.divide(buyOrderDetail.getUnitnum(),6,BigDecimal.ROUND_HALF_UP);
						buyOrderDetail.setNotaxprice(notaxprice);
						buyOrderDetail.setNotaxamount(notaxamount);
						buyOrderDetail.setTax(buyOrderDetail.getTaxamount().subtract(notaxamount));
					}
					buyDetailList.add(buyOrderDetail);
				}else{
					goodsids += storageBuySaleReport.getGoodsid() +",";
				}
			}
			buyOrder.setBuyOrderDetailList(buyDetailList);
			returnMap.put("buyOrder", buyOrder);
		}else{
			for(StorageBuySaleReport storageBuySaleReport : detailList){
				goodsids += storageBuySaleReport.getGoodsid() +",";
			}
		}
		returnMap.put("goodsid", goodsids);
		return returnMap;
	}
	@Override
	public Map closeBuyOrder(String id) throws Exception{
		Map resultMap=new HashMap();
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关采购订单信息");
			return resultMap;
		}
		BuyOrder buyOrder=buyOrderMapper.getBuyOrder(id.trim());
		if(null==buyOrder){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关采购订单信息");
			return resultMap;
		}
		if(!"3".equals(buyOrder.getStatus())){
			resultMap.put("flag", false);
			resultMap.put("msg", "只有审核通过状态下采购订单才能关闭");
			return resultMap;			
		}
		List<String> enterIdList=storageForPurchaseService.getPuchaseEnterUnAuditIDList(id.trim());
		if(null!=enterIdList && enterIdList.size()!=0){
			String enterIdArray[]=(String[])enterIdList.toArray(new String[0]);
			String enterId="";
	    	if(enterIdArray.length>0){
	    		enterId=StringUtils.join(enterIdArray, ",");
	    	}

			resultMap.put("flag", false);
			resultMap.put("msg", "抱歉，采购订单对应的下游采购入库单处于保存状态，不能关闭。对应的采购入库单编号："+enterId);
			return resultMap;	
		}
		BuyOrder upbuyOrder=new BuyOrder();
		upbuyOrder.setStatus("4");
		upbuyOrder.setId(id.trim());
		boolean flag= buyOrderMapper.updateBuyOrderStatus(upbuyOrder)>0;
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	  @Override
	    public List<Map> getPurchaseHistoryGoodsPriceList(Map map) throws Exception {
	        String goodsid = (String)map.get("goodsid");
	        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
	        List<Map> list = getArrivalOrderMapper().getPurchaseHistoryGoodsPriceList(map);
	        for(Map map1 : list){
	            if(null != goodsInfo && null != goodsInfo.getBoxnum()){
	                BigDecimal taxprice = (BigDecimal)map1.get("taxprice");
	                BigDecimal boxprice = taxprice.multiply(goodsInfo.getBoxnum());
	                map1.put("boxprice",boxprice);
	            }
	            String supplierid = (String)map1.get("supplierid");
				if(null!=supplierid && !"".equals(supplierid.trim())){
					BuySupplier buySupplier=getSupplierInfoById(supplierid);
					if(null!=buySupplier){
						map1.put("suppliername",buySupplier.getName());
					}
				}
	        }
	        return list;
	    }

    @Override
    public Map auditMultiBuyOrder(String ids) throws Exception {
        int failure = 0;
        int success = 0;
        boolean sFlag = true;
        String msg = "";
        String enterStorageids= "";
        String errorids = "";
        if (StringUtils.isNotEmpty(ids)) {
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                Map map = auditBuyOrder(id);
                Boolean auditflag = (Boolean) map.get("flag");
                if (auditflag) {
                    success++;
                    enterStorageids +=(String) map.get("billid") + ",";
                } else {
                    failure++;
                    errorids += id+",";
                }
            }
            if(success > 0){
                msg = "批量审核采购订单成功并生成入库单："+enterStorageids.substring(0,enterStorageids.length()-1);
            }else{
                msg="批量审核失败";
            }
        }
        Map map = new HashMap();
        map.put("flag", sFlag);
        map.put("failure", failure);
        map.put("success", success);
        if(errorids.endsWith(",")){
            msg = msg +",审核失败单据编号："+errorids+" ";
        }
        map.put("msg", msg);
        return map;
    }
}

