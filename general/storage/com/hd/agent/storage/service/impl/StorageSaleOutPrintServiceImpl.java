/**
 * @(#)StorageSaleOutPrintServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-8-11 zhanghonghui 创建版本
 */
package com.hd.agent.storage.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.sales.dao.OrderDetailMapper;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.model.OrderGoodsDetail;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.storage.dao.BigSaleOutMapper;
import com.hd.agent.storage.dao.SaleoutMapper;
import com.hd.agent.storage.model.BigSaleOut;
import com.hd.agent.storage.model.BigSaleOutDetail;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IStorageSaleOutPrintService;
import com.hd.agent.system.model.SysParam;

/**
 * 
 * 与销售单相关打印服务层
 * @author zhanghonghui
 */
public class StorageSaleOutPrintServiceImpl extends BaseStorageServiceImpl implements
	IStorageSaleOutPrintService {
	
	protected SaleoutMapper saleoutMapper;
	
	private BigSaleOutMapper bigSaleOutMapper;

	private OrderDetailMapper salesOrderDetailMapper;

	public OrderDetailMapper getSalesOrderDetailMapper() {
		return salesOrderDetailMapper;
	}

	public void setSalesOrderDetailMapper(OrderDetailMapper salesOrderDetailMapper) {
		this.salesOrderDetailMapper = salesOrderDetailMapper;
	}

	public BigSaleOutMapper getBigSaleOutMapper() {
		return bigSaleOutMapper;
	}

	public void setBigSaleOutMapper(BigSaleOutMapper bigSaleOutMapper) {
		this.bigSaleOutMapper = bigSaleOutMapper;
	}

	public SaleoutMapper getSaleoutMapper() {
		return saleoutMapper;
	}

	public void setSaleoutMapper(SaleoutMapper saleoutMapper) {
		this.saleoutMapper = saleoutMapper;
	}


	/**
	 * 获取打印次数同步上游单据类型
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-8-12
	 */
	private String getSyncOrderTypeParam(){
		SysParam syncOrderTypeParam = getBaseSysParamMapper().getSysParam("FHDPrintTimesSyncUpOrder");
		String syncOrderType="1";
		if(null==syncOrderTypeParam){
			syncOrderType=(String)syncOrderTypeParam.getPvalue();
		}
		if(null!=syncOrderType){
			syncOrderType=syncOrderType.trim();
		}
		if(!"1".equals(syncOrderType) || !"2".equals(syncOrderType) || !"999".equals(syncOrderType)){
			syncOrderType="1";
		}
		return syncOrderType;
	}
	
	@Override
	public List getSaleOutDetailPrintListBy(Map map) throws Exception {
		String customerid=(String)map.get("customerid");
        String saleorderid = (String)map.get("saleorderid");
		String orderStr = "goodsid";
		List<OrderDetail> list = getSalesOrderDetailMapper().getOrderDetailByOrderWithDiscount(saleorderid, orderStr);
	//	List<OrderGoodsDetail> list = getOrderGoodsMapper().getOrderGoodsDetailList(saleorderid, orderStr);
	//	for(SaleoutDetail saleoutDetail : list){
		for (OrderDetail goodsDetail : list) {
			if(null!=customerid && !"".equals(customerid.trim())){
				Customer customer = getCustomerByID(customerid.trim());
				if(null!=customer){
					//获取客户店内码
				//	CustomerPrice customerPrice=getCustomerPriceByCustomerAndGoodsid(customerid, saleoutDetail.getGoodsid());
					CustomerPrice customerPrice=getCustomerPriceByCustomerAndGoodsid(customerid, goodsDetail.getGoodsid());
				    if(StringUtils.isNotEmpty(customer.getPid())){
						//customerPrice=getCustomerPriceByCustomerAndGoodsid(customer.getPid(), saleoutDetail.getGoodsid());
						customerPrice=getCustomerPriceByCustomerAndGoodsid(customer.getPid(), goodsDetail.getGoodsid());
					}
				}
			}
			//GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(saleoutDetail.getGoodsid()));
			GoodsInfo goodsInfo = (GoodsInfo) CommonUtils.deepCopy(getGoodsInfoByID(goodsDetail.getGoodsid()));
			goodsDetail.setGoodsInfo(goodsInfo);
		}
		return list;
	}
	@Override
	public void updatePrinttimesSyncUpOrder(String id) throws Exception{
		Saleout saleout=saleoutMapper.getSaleOutInfo(id);
		if(null==saleout){
			return;
		}
		saleout.setPrinttimes(1);
		saleout.setPhprinttimes(1);	//数据库自动加1
		if(saleoutMapper.updateSaleoutPrinttimes(saleout)>0){
			if(StringUtils.isEmpty(saleout.getSaleorderid())){
				return;
			}
			String syncOrderType=getSyncOrderTypeParam();
			Map queryMap=new HashMap();
			Map resultMap=new HashMap();
			queryMap.put("saleorderid", saleout.getSaleorderid());
			queryMap.put("printtype", "1");
			resultMap=saleoutMapper.getSaleOutPrintCountInfoBy(queryMap);
			//如果下游发货单打印配货打印次数已经都打印完成，更新上游单据打印次数
			if(null!=resultMap){
				Long unprint=(Long) resultMap.get("unprint");
				if(null!=unprint && unprint == 0){
					if("1".equals(syncOrderType) || "3".equals(syncOrderType)){
						queryMap.clear();
						queryMap.put("sourceid", saleout.getSaleorderid());
						queryMap.put("printtype", "1");
						saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
					}else if("2".equals(syncOrderType) || "3".equals(syncOrderType)){
						queryMap.clear();
						queryMap.put("id", saleout.getSaleorderid());
						queryMap.put("printtype", "1");
						saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);							
					}
				}
			}
			queryMap.clear();
			resultMap.clear();
			queryMap.put("saleorderid", saleout.getSaleorderid());
			queryMap.put("printtype", "2");
			resultMap=saleoutMapper.getSaleOutPrintCountInfoBy(queryMap);
			//如果下游发货单打印发货打印次数已经都打印完成，更新上游单据打印次数
			if(null!=resultMap){
				Long unprint=(Long) resultMap.get("unprint");
				if(null!=unprint && unprint == 0){
					if("1".equals(syncOrderType) || "3".equals(syncOrderType)){
						queryMap.clear();
						queryMap.put("sourceid", saleout.getSaleorderid());
						queryMap.put("printtype", "2");
						saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
					}else if("2".equals(syncOrderType) || "3".equals(syncOrderType)){
						queryMap.clear();
						queryMap.put("id", saleout.getSaleorderid());
						queryMap.put("printtype", "2");
						saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);	
					}
				}
			}
		}
	}
	/**
	 * 下游打印次数，更新上游打印次数
	 */
	@Override
	public void updatePrinttimesSyncUpOrderIds(String ids) throws Exception{
		if(null == ids || "".equals(ids.trim())){
			return ;
		}
		Map queryMap=new HashMap();
		queryMap.put("idarrs", ids);
		List<Saleout> list=saleoutMapper.showSaleOutListBy(queryMap);
		String syncOrderType=getSyncOrderTypeParam();
		for(Saleout saleout : list){
			saleout.setPrinttimes(1);
			saleout.setPhprinttimes(1);	//数据库自动加1
			if(saleoutMapper.updateSaleoutPrinttimes(saleout)>0){
				if(StringUtils.isEmpty(saleout.getSaleorderid())){
					continue;
				}
				queryMap.clear();
				Map resultMap=new HashMap();
				queryMap.put("saleorderid", saleout.getSaleorderid());
				queryMap.put("printtype", "1");
				resultMap=saleoutMapper.getSaleOutPrintCountInfoBy(queryMap);
				//如果下游发货单打印配货打印次数已经都打印完成，更新上游单据打印次数
				if(null!=resultMap){
					Long unprint=(Long) resultMap.get("unprint");
					if(null!=unprint && unprint == 0){
						if("1".equals(syncOrderType) || "3".equals(syncOrderType)){
							queryMap.clear();
							queryMap.put("sourceid", saleout.getSaleorderid());
							queryMap.put("printtype", "1");
							saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
						}else if("2".equals(syncOrderType) || "3".equals(syncOrderType)){
							queryMap.clear();
							queryMap.put("id", saleout.getSaleorderid());
							queryMap.put("printtype", "1");
							saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);							
						}
					}
				}
				queryMap.clear();
				resultMap.clear();
				queryMap.put("saleorderid", saleout.getSaleorderid());
				queryMap.put("printtype", "2");
				resultMap=saleoutMapper.getSaleOutPrintCountInfoBy(queryMap);
				//如果下游发货单打印发货打印次数已经都打印完成，更新上游单据打印次数
				if(null!=resultMap){
					Long unprint=(Long) resultMap.get("unprint");
					if(null!=unprint && unprint == 0){
						if("1".equals(syncOrderType) || "3".equals(syncOrderType)){
							queryMap.clear();
							queryMap.put("sourceid", saleout.getSaleorderid());
							queryMap.put("printtype", "2");
							saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
						}else if("2".equals(syncOrderType) || "3".equals(syncOrderType)){
							queryMap.clear();
							queryMap.put("id", saleout.getSaleorderid());
							queryMap.put("printtype", "2");
							saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);	
						}
					}
				}
			}
		}
	}
	

	
	@Override
	public void updatePhPrinttimesSyncUpOrder(String saleoutId) throws Exception{
		if(null==saleoutId || "".equals(saleoutId)){
			return;
		}
		Saleout saleout=saleoutMapper.getSaleOutInfo(saleoutId);
		if(null==saleout){
			return;
		}
		saleout.setId(saleoutId);
		saleout.setPrinttimes(null);
		saleout.setPhprinttimes(1);	//数据库自动加1
		if(saleoutMapper.updateSaleoutPrinttimes(saleout)>0){
			if(StringUtils.isEmpty(saleout.getSaleorderid())){
				return;
			}
			String syncOrderType=getSyncOrderTypeParam();
			Map queryMap=new HashMap();
			Map resultMap=new HashMap();
			queryMap.put("saleorderid", saleout.getSaleorderid());
			queryMap.put("printtype", "1");
			resultMap=saleoutMapper.getSaleOutPrintCountInfoBy(queryMap);
			//如果下游发货单打印配货打印次数已经都打印完成，更新上游单据打印次数
			if(null!=resultMap){
				Long unprint=(Long) resultMap.get("unprint");
				if(null!=unprint && unprint == 0){
					if("1".equals(syncOrderType) || "3".equals(syncOrderType)){
						queryMap.clear();
						queryMap.put("sourceid", saleout.getSaleorderid());
						queryMap.put("printtype", "1");
						saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
					}else if("2".equals(syncOrderType) || "3".equals(syncOrderType)){
						queryMap.clear();
						queryMap.put("id", saleout.getSaleorderid());
						queryMap.put("printtype", "1");
						saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);							
					}
				}
			}
		}
	}
	
	@Override
	public void updatePrinttimesSyncUpBigSalout(String bigsaleoutid)
			throws Exception {
		if(null==bigsaleoutid || "".equals(bigsaleoutid)){
			return;
		}
		BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(bigsaleoutid);
		if(null==bigSaleOut){
			return;
		}
		Integer printtimes = bigSaleOut.getPrinttimes() + 1;
		if(bigSaleOutMapper.updateBigSaleoutPrinttimes(bigsaleoutid, printtimes) > 0){
			List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(bigsaleoutid);
			if(sourceList.size() == 0){
				return;
			}
			String syncOrderType=getSyncOrderTypeParam();
			for(BigSaleOutDetail bigSaleOutDetail : sourceList){
				Map resultMap=new HashMap();
				Map queryMap=new HashMap();
				Saleout saleout = saleoutMapper.getSaleOutInfo(bigSaleOutDetail.getSaleoutid());
				if(null != saleout){
					queryMap.put("saleorderid", saleout.getSaleorderid());
					queryMap.put("printtype", "1");
					resultMap=saleoutMapper.getSaleOutPrintCountInfoBy(queryMap);
					//如果下游发货单打印配货打印次数已经都打印完成，更新上游单据打印次数
					if(null!=resultMap){
						Long unprint=(Long) resultMap.get("unprint");
						if(null!=unprint && unprint == 0){
							if("1".equals(syncOrderType) || "3".equals(syncOrderType)){
								queryMap.clear();
								queryMap.put("sourceid", saleout.getSaleorderid());
								queryMap.put("printtype", "1");
								saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
							}else if("2".equals(syncOrderType) || "3".equals(syncOrderType)){
								queryMap.clear();
								queryMap.put("id", saleout.getSaleorderid());
								queryMap.put("printtype", "1");
								saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);							
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void updateFhPrinttimesSyncUpOrder(String saleoutId) throws Exception{
		if(null==saleoutId || "".equals(saleoutId)){
			return;
		}
		Saleout saleout=saleoutMapper.getSaleOutInfo(saleoutId);
		if(null==saleout){
			return;
		}
		saleout.setId(saleoutId);
		saleout.setPrinttimes(1);
		saleout.setPhprinttimes(null);	//数据库自动加1
		if(saleoutMapper.updateSaleoutPrinttimes(saleout)>0){
			if(StringUtils.isEmpty(saleout.getSaleorderid())){
				return;
			}
			String syncOrderType=getSyncOrderTypeParam();
			
			Map queryMap=new HashMap();
			Map resultMap=new HashMap();
			queryMap.put("saleorderid", saleout.getSaleorderid());
			queryMap.put("printtype", "2");
			resultMap=saleoutMapper.getSaleOutPrintCountInfoBy(queryMap);
			//如果下游发货单打印发货打印次数已经都打印完成，更新上游单据打印次数
			if(null!=resultMap){
				Long unprint=(Long) resultMap.get("unprint");
				if(null!=unprint && unprint == 0){
					if("1".equals(syncOrderType) || "3".equals(syncOrderType)){
						queryMap.clear();
						queryMap.put("sourceid", saleout.getSaleorderid());
						queryMap.put("printtype", "2");
						saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
					}else if("2".equals(syncOrderType) || "3".equals(syncOrderType)){
						queryMap.clear();
						queryMap.put("id", saleout.getSaleorderid());
						queryMap.put("printtype", "2");
						saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);	
					}
				}
			}
		}
	}
	
	@Override
	public void updateSalesDispatchBillPrinttimes(String dispatchid,String idarrs) throws Exception{

		Map map=new HashMap();		
		if(null != dispatchid && !"".equals(dispatchid.trim())){
			map.put("id", dispatchid);
			map.put("printtype", "5"); //发货单加1
			saleoutMapper.updateSyncDispatchBillPrinttimes(map);
		}
		
		map.clear();
		map.put("sourceid", dispatchid);
		if(null !=idarrs  && !"".equals(idarrs.trim())){
			map.put("idarrs", idarrs.trim() );
		}
		map.put("printtimes", "1");
		saleoutMapper.updateSaleoutPrinttimesBy(map);
	}

	@Override
	public void updateSalesDispatchBillPrinttimesForXSD(String salesid,String idarrs) throws Exception{
		Map map=new HashMap();		
		if(null != salesid && !"".equals(salesid.trim())){
			map.put("id", salesid);
			map.put("printtype", "5"); //发货单加1
			saleoutMapper.updateSyncSalesOrderPrinttimes(map);
		}
		map.clear();
		map.put("saleorderid", salesid);
		if(null !=idarrs  && !"".equals(idarrs.trim())){
			map.put("idarrs", idarrs.trim() );
		}
		map.put("printtimes", "1");
		saleoutMapper.updateSaleoutPrinttimesBy(map);
	}
	
	@Override
	public void updatePrinttimesSyncOrder(String id,String dphprintimes,String dfprintimes) throws Exception{
		Saleout saleout=saleoutMapper.getSaleOutInfo(id);
		if(null==saleout){
			return;
		}
		if(!"999999".equals(dphprintimes) && !"900000".equals(dphprintimes)){
			saleout.setPhprinttimes(1);	//数据库自动加1			
		}else{
			saleout.setPhprinttimes(null);
		}
		if(!"999999".equals(dfprintimes) && !"900000".equals(dfprintimes)){
			saleout.setPrinttimes(1);
		}else{
			saleout.setPrinttimes(null);
		}
		if(saleoutMapper.updateSaleoutPrinttimes(saleout)>0){
			long iDphprintimes=0;
			if(null==dphprintimes || !StringUtils.isNumeric(dphprintimes)){
				dphprintimes="0";
			}
			iDphprintimes=Long.parseLong(dphprintimes);
			long iDfhprintimes=0;
			if(null==dfprintimes || !StringUtils.isNumeric(dfprintimes)){
				dfprintimes="0";
			}
			iDfhprintimes=Long.parseLong(dfprintimes);
			
			Map queryMap=new HashMap();
			Map resultMap=new HashMap();
			queryMap.put("sourceid", saleout.getSaleorderid());
			resultMap=saleoutMapper.getDispatchBillPrintCountInfoBy(queryMap);
			if(null!=resultMap){
				Long phprinttimes=(Long) resultMap.get("phprinttimes");
				Long fhprinttimes=fhprinttimes=(Long) resultMap.get("printtimes");

				if(null!=phprinttimes && !"999999".equals(dphprintimes)){
					//配货单为零时加1
					if("900000".equals(dphprintimes) && phprinttimes==0){
						queryMap.clear();
						queryMap.put("sourceid", saleout.getSaleorderid());
						queryMap.put("printtype", "4"); //配货单加1
						saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
					}else{
						//传入的打印次数=未打印前的次数，加1
						if(iDphprintimes==phprinttimes){
							queryMap.clear();
							queryMap.put("sourceid", saleout.getSaleorderid());
							queryMap.put("printtype", "4"); //配货单加1
							saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
						}						
					}
				}
				
				if(null!=fhprinttimes && !"999999".equals(dfprintimes)){
					//配货单为零时加1
					if("900000".equals(dfprintimes) && fhprinttimes==0){
						queryMap.clear();
						queryMap.put("sourceid", saleout.getSaleorderid());
						queryMap.put("printtype", "5"); //发货单加1
						saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
					}else{	
						//传入的打印次数=未打印前的次数，加1
						if(iDfhprintimes==fhprinttimes){
							queryMap.clear();
							queryMap.put("sourceid", saleout.getSaleorderid());
							queryMap.put("printtype", "5"); //发货单加1
							saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
						}						
					}
				}
			}
		}
	}
	
	@Override
	public void updatePrinttimesSyncSalesOrder(String id,String sphprintimes,String sfprintimes) throws Exception{
		Saleout saleout=saleoutMapper.getSaleOutInfo(id);
		if(null==saleout){
			return;
		}
		if(!"999999".equals(sphprintimes) && !"900000".equals(sphprintimes)){
			saleout.setPhprinttimes(1);	//数据库自动加1			
		}else{
			saleout.setPhprinttimes(null);
		}
		if(!"999999".equals(sfprintimes) && !"900000".equals(sfprintimes)){
			saleout.setPrinttimes(1);
		}else{
			saleout.setPrinttimes(null);
		}
		if(saleoutMapper.updateSaleoutPrinttimes(saleout)>0){
			long iDphprintimes=0;
			if(null==sphprintimes || !StringUtils.isNumeric(sphprintimes)){
				sphprintimes="0";
			}
			iDphprintimes=Long.parseLong(sphprintimes);
			long iDfhprintimes=0;
			if(null==sfprintimes || !StringUtils.isNumeric(sfprintimes)){
				sfprintimes="0";
			}
			iDfhprintimes=Long.parseLong(sfprintimes);
			
			Map queryMap=new HashMap();
			Map resultMap=new HashMap();
			queryMap.put("id", saleout.getSaleorderid());
			resultMap=saleoutMapper.getSalesOrderPrintCountInfoBy(queryMap);
			if(null!=resultMap){
				Long phprinttimes=(Long) resultMap.get("phprinttimes");
				Long fhprinttimes=fhprinttimes=(Long) resultMap.get("printtimes");

				if(null!=phprinttimes && !"999999".equals(sphprintimes)){
					//配货单为零时加1
					if("900000".equals(sphprintimes) && phprinttimes==0){
						queryMap.clear();
						queryMap.put("id", saleout.getSaleorderid());
						queryMap.put("printtype", "4"); //配货单加1
						saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);
					}else{
						//传入的打印次数=未打印前的次数，加1
						if(iDphprintimes==phprinttimes){
							queryMap.clear();
							queryMap.put("id", saleout.getSaleorderid());
							queryMap.put("printtype", "4"); //配货单加1
							saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);
						}						
					}
				}
				
				if(null!=fhprinttimes && !"999999".equals(sfprintimes)){
					//配货单为零时加1
					if("900000".equals(sfprintimes) && fhprinttimes==0){
						queryMap.clear();
						queryMap.put("id", saleout.getSaleorderid());
						queryMap.put("printtype", "5"); //发货单加1
						saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);
					}else{	
						//传入的打印次数=未打印前的次数，加1
						if(iDfhprintimes==fhprinttimes){
							queryMap.clear();
							queryMap.put("id", saleout.getSaleorderid());
							queryMap.put("printtype", "5"); //发货单加1
							saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);
						}						
					}
				}
			}
		}
	}
	
	@Override
	public void updatePhPrinttimesSyncOrder(String saleoutId,String dispatchPrintCount){
		if(null==saleoutId || "".equals(saleoutId)){
			return;
		}
		Saleout saleout=saleoutMapper.getSaleOutInfo(saleoutId);
		if(null==saleout){
			return;
		}
		saleout.setId(saleoutId);
		saleout.setPrinttimes(null);
		saleout.setPhprinttimes(1);	//数据库自动加1
		if(saleoutMapper.updateSaleoutPrinttimes(saleout)>0){
			long iDispatchPrintCount=0;
			if(null==dispatchPrintCount || !StringUtils.isNumeric(dispatchPrintCount)){
				dispatchPrintCount="0";
			}
			iDispatchPrintCount=Long.parseLong(dispatchPrintCount);
			
			Map queryMap=new HashMap();
			Map resultMap=new HashMap();
			queryMap.put("sourceid", saleout.getSaleorderid());
			resultMap=saleoutMapper.getDispatchBillPrintCountInfoBy(queryMap);
			if(null!=resultMap){
				Long phprinttimes=(Long) resultMap.get("phprinttimes");
				if(null!=phprinttimes){
					if(iDispatchPrintCount==phprinttimes){
						queryMap.clear();
						queryMap.put("sourceid", saleout.getSaleorderid());
						queryMap.put("printtype", "4"); //配货单加1
						saleoutMapper.updateSyncDispatchBillPrinttimes(queryMap);
					}
				}
			}
		}
	}
	
	@Override
	public void updatePhPrinttimesSyncSalesOrder(String saleoutId,String salesPrintCount){
		if(null==saleoutId || "".equals(saleoutId)){
			return;
		}
		Saleout saleout=saleoutMapper.getSaleOutInfo(saleoutId);
		if(null==saleout){
			return;
		}
		saleout.setId(saleoutId);
		saleout.setPrinttimes(null);
		saleout.setPhprinttimes(1);	//数据库自动加1
		if(saleoutMapper.updateSaleoutPrinttimes(saleout)>0){
			long iDispatchPrintCount=0;
			if(null==salesPrintCount || !StringUtils.isNumeric(salesPrintCount)){
				salesPrintCount="0";
			}
			iDispatchPrintCount=Long.parseLong(salesPrintCount);
			
			Map queryMap=new HashMap();
			Map resultMap=new HashMap();
			queryMap.put("id", saleout.getSaleorderid());
			resultMap=saleoutMapper.getSalesOrderPrintCountInfoBy(queryMap);
			if(null!=resultMap){
				Long phprinttimes=(Long) resultMap.get("phprinttimes");
				if(null!=phprinttimes){
					if(iDispatchPrintCount==phprinttimes){
						queryMap.clear();
						queryMap.put("id", saleout.getSaleorderid());
						queryMap.put("printtype", "4"); //配货单加1
						saleoutMapper.updateSyncSalesOrderPrinttimes(queryMap);
					}
				}
			}
		}
	}	
	
	//======================================================================<<
	public  Map  getBigSaleOutInfoForPrint(String id) throws Exception {
		BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(id);
		Map resultMap=new HashMap();
		if(null!=bigSaleOut){
			resultMap.put("bigSaleOut",bigSaleOut);
			if(StringUtils.isNotEmpty(bigSaleOut.getStorageid())){
				StorageInfo storageInfo = null;
				if(!resultMap.containsKey("storageInfo_"+bigSaleOut.getStorageid())){
					storageInfo=getStorageInfoByID(bigSaleOut.getStorageid());
					resultMap.put("storageInfo_"+bigSaleOut.getStorageid(),storageInfo);
				}else{
					storageInfo=(StorageInfo)resultMap.get("storageInfo_"+bigSaleOut.getStorageid());
				}

				if (null != storageInfo) {
					Personnel personnel =null;
					if(!resultMap.containsKey("storage_managerInfo_"+storageInfo.getManageruserid())){
						personnel= getPersonnelById(storageInfo.getManageruserid());
					}
					if(null!=personnel){
						resultMap.put("storage_managerInfo_" + storageInfo.getManageruserid(), personnel);
					}
				}
			}
		}
		return resultMap;
	}
	//region 整件分拣
	@Override
	public  List<Map>  getBigSaleOutGoodsListForPrint(Map map) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		List<Map> dataList=new ArrayList<Map>();
		String idarrs=(String)map.get("idarrs");
		if(null==idarrs || "".equals(idarrs.trim())){
			return dataList;
		}
		String[] idarr=StringUtils.split(idarrs,",");
		for(String id : idarr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			Map retMap = new HashMap();
			retMap.put("id",id.trim());
			List<SaleoutDetail> goodsList = new ArrayList<SaleoutDetail>();
			Map<String, List<SaleoutDetail>> billdetailmap = new LinkedHashMap<String, List<SaleoutDetail>>();
			Map<String,Saleout> billordermap=new HashMap<String, Saleout>();

			int billnum=0;
			Map bigSaleOutMap=getBigSaleOutInfoForPrint(id);
			BigSaleOut bigSaleOut = null;
			if(bigSaleOutMap.containsKey("bigSaleOut")){
				bigSaleOut=(BigSaleOut)bigSaleOutMap.get("bigSaleOut");
			}
			if(null != bigSaleOut){
				if(StringUtils.isNotEmpty(bigSaleOut.getStorageid())) {
					StorageInfo storageInfo = getStorageInfoByID(bigSaleOut.getStorageid());
					if (null != storageInfo) {
						bigSaleOut.setStoragename(storageInfo.getName());
					}
				}
				retMap.putAll(bigSaleOutMap);
				//整单分拣
				BigDecimal auxnumSum = BigDecimal.ZERO;
				BigDecimal auxremainderSum = BigDecimal.ZERO;
				goodsList = bigSaleOutMapper.getBigSaleOutGoodsListForPrint(id);
				if(goodsList.size() != 0){
					for(SaleoutDetail saleoutDetail : goodsList){
						StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
						if(null!=storageInfo){
							saleoutDetail.setStoragename(storageInfo.getName());
						}
						StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
						if(null!=storageLocation){
							saleoutDetail.setStoragelocationname(storageLocation.getName());
						}
						TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
						if(null!=taxType){
							saleoutDetail.setTaxtypename(taxType.getName());
						}

						GoodsInfo goodsInfo = getGoodsInfoByID(saleoutDetail.getGoodsid());
						if(null != goodsInfo){
							saleoutDetail.setGoodsname(goodsInfo.getName());
							saleoutDetail.setGoodsInfo(goodsInfo);
						}
						saleoutDetail.setGoodsInfo(goodsInfo);

						BigDecimal unitnum = null != saleoutDetail.getUnitnum() ? saleoutDetail.getUnitnum() : BigDecimal.ZERO;
						Map map1 = countGoodsInfoNumber(saleoutDetail.getGoodsid(),null,unitnum);
						BigDecimal auxnum = null != map1.get("auxInteger") ? new BigDecimal((String)map1.get("auxInteger")) : BigDecimal.ZERO;
						BigDecimal auxremainder = null != map1.get("auxremainder") ? new BigDecimal((String)map1.get("auxremainder")) : BigDecimal.ZERO;
						String auxnumdetail = null != map1.get("auxnumdetail") ? (String)map1.get("auxnumdetail") : "";
						saleoutDetail.setTotalauxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnum.intValue()) + saleoutDetail.getAuxunitname()));
						saleoutDetail.setAuxremainderdetail(CommonUtils.strDigitNumDeal(auxremainder.setScale(decimalScale, BigDecimal.ROUND_HALF_UP)+saleoutDetail.getUnitname()));
						saleoutDetail.setAuxnumdetail(auxnumdetail);
						saleoutDetail.setAuxremainder(auxremainder);
						saleoutDetail.setAuxnum(auxnum);

						auxnumSum = auxnumSum.add(auxnum);
						auxremainderSum = auxremainderSum.add(auxremainder);
					}
					/*
					SaleoutDetail saleoutDetailSum = new SaleoutDetail();
					GoodsInfo goodsInfoSum = new GoodsInfo();
					goodsInfouSm.setName("合计");
					saleoutDetailSum.setGoodsInfo(goodsInfoSum);
					saleoutDetailSum.setGoodsid("sum");
					saleoutDetailSum.setAuxnum(auxnumSum);
					saleoutDetailSum.setAuxremainder(auxremainderSum);
					saleoutDetailSum.setTotalauxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumSum.intValue()) + "箱"));
					saleoutDetailSum.setAuxremainderdetail(CommonUtils.strDigitNumDeal(auxremainderSum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP).toString()));
					saleoutDetailSum.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumSum.intValue()) + "箱" + auxremainderSum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP).toString()));
					goodsList.add(new SaleoutDetail());
					goodsList.add(saleoutDetailSum);
					*/
					retMap.put("goodsList", goodsList);
				}

				//分拣单
				List<SaleoutDetail> saleoutDetailList = bigSaleOutMapper.getBigSaleOutSaleoutDetailListForPrint(id);
				if(null != saleoutDetailList && saleoutDetailList.size() != 0){
					Map<String,List<SaleoutDetail>> map1 = new HashMap();
					for(SaleoutDetail saleoutDetail : saleoutDetailList){
						GoodsInfo goodsInfo = getAllGoodsInfoByIDNoCache(saleoutDetail.getGoodsid());
						if(null != goodsInfo){
							saleoutDetail.setGoodsname(goodsInfo.getName());
							saleoutDetail.setGoodsInfo(goodsInfo);
						}
						Map auxmap = countGoodsInfoNumber(saleoutDetail.getGoodsid(),saleoutDetail.getAuxunitid(),saleoutDetail.getUnitnum());
						if(!auxmap.isEmpty()){
							String auxnumdetail = null != auxmap.get("auxnumdetail") ? (String)auxmap.get("auxnumdetail") : "";
							saleoutDetail.setAuxnumdetail(auxnumdetail);
						}
						if(map1.containsKey(saleoutDetail.getSaleoutid())){
							List detailList = (List) map1.get(saleoutDetail.getSaleoutid());
							detailList.add(saleoutDetail);
							map1.put(saleoutDetail.getSaleoutid(), detailList);
						}else{
							List<SaleoutDetail> detailList = new ArrayList<SaleoutDetail>();
							detailList.add(saleoutDetail);
							map1.put(saleoutDetail.getSaleoutid(), detailList);
						}
					}

					Set set = map1.entrySet();
					Iterator it = set.iterator();
					while (it.hasNext()) {
						Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
						String key = entry.getKey();
						List<SaleoutDetail> detailList = (List<SaleoutDetail>) map1.get(key);

						Saleout saleout = getSaleoutMapper().getSaleOutInfo(key);
						if(null != saleout){
							if(StringUtils.isNotEmpty(saleout.getStorageid())) {
								StorageInfo storageInfo = getStorageInfoByID(saleout.getStorageid());
								if (null != storageInfo) {
									saleout.setStoragename(storageInfo.getName());
								}
							}

							//总店客户
							Customer pCustomer =null;
							if(StringUtils.isNotEmpty(saleout.getCustomerid())) {
								Map queryMap = new HashMap();
								queryMap.put("id", saleout.getCustomerid());
								Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
								if (null != customer) {
									if ( StringUtils.isNotEmpty(customer.getPid())) {
										if (null != pCustomer && customer.getPid().equals(pCustomer.getId())) {
											customer.setPname(pCustomer.getName());
										} else {
											queryMap.put("id", customer.getPid());
											pCustomer = getBaseFilesCustomerMapper().getCustomerDetail(queryMap);
											if (null != pCustomer) {
												customer.setPname(pCustomer.getName());
											}
										}
									}
									saleout.setCustomerInfo(customer);
									saleout.setCustomername(customer.getName());
									if (StringUtils.isNotEmpty(customer.getSalesarea())) {
										queryMap.clear();
										queryMap.put("id", customer.getSalesarea());
										SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(queryMap);
										if (null != salesArea) {
											customer.setSalesareaname(salesArea.getName());
										}
									}
									/*
									 * 通用版本不用，客户联系人电话，直接存储在客户档案
									if(StringUtils.isNotEmpty(customer.getContact())){
										queryMap.clear();
										queryMap.put("id", customer.getContact());
										Contacter contacter = getBaseFilesContacterMapper().getContacterDetail(queryMap);
										if(contacter != null){
											customer.setContactname(contacter.getName());
										}
									}
									*/

									if(StringUtils.isNotEmpty(customer.getSettletype())) {
										Settlement settlement = getSettlementByID(customer.getSettletype());
										if (null != settlement) {
											saleout.setSettletypename(settlement.getName());
										}
									}
								}
							}else{
								saleout.setCustomername("不存在该客户");
							}
							if(StringUtils.isNotEmpty(saleout.getSalesdept())) {
								DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(saleout.getSalesdept());
								if (null != departMent) {
									saleout.setSalesdeptname(departMent.getName());
								}
							}
							if(StringUtils.isNotEmpty(saleout.getSalesuser())) {
								Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(saleout.getSalesuser());
								if (null != personnel) {
									saleout.setSalesusername(personnel.getName());
								}
							}
							if(StringUtils.isNotEmpty(saleout.getStorager())) {
								Personnel storager = getBaseFilesPersonnelMapper().getPersonnelInfo(saleout.getStorager());
								if (null != storager) {
									saleout.setStoragername(storager.getName());
								}
							}
						}

						billdetailmap.put(key,detailList);
						billordermap.put(key,saleout);
					}
					retMap.put("billdetailmap", billdetailmap);
					retMap.put("billordermap", billordermap);
					retMap.put("billnum",billordermap.size());
				}
			}
			dataList.add(retMap);
		}
		return dataList;
	}
	//endregion


	@Override
	public Map getBigOutGoodsCustomerDivMapForPrint(Map map) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		Map<SaleoutDetail, List<SaleoutDetail>> customerDivMap = new LinkedHashMap<SaleoutDetail, List<SaleoutDetail>>();
		if(map.containsKey("id") && null != map.get("id")){
			String id = (String)map.get("id");
			BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(id);
			if(null != bigSaleOut){
				//按商品分客户区块
				//分商品合计
				List<SaleoutDetail> goodssumlist = bigSaleOutMapper.getBigSaleOutGoodsSum(id);
				if(goodssumlist.size() != 0){
					for(SaleoutDetail goodssod : goodssumlist){
						GoodsInfo goodsInfo = getAllGoodsInfoByIDNoCache(goodssod.getGoodsid());
						if(null != goodsInfo){
							if(goodsInfo.getName().length() > 14){
								goodssod.setGoodsname(goodsInfo.getName().toString().substring(0, 14));
							}else{
								goodssod.setGoodsname(goodsInfo.getName());
							}
						}else{
							goodssod.setGoodsname("其他未指定");
						}
						BigDecimal auxnumsum = BigDecimal.ZERO;
						BigDecimal auxremaindersum = BigDecimal.ZERO;

						List<SaleoutDetail> customerlist = bigSaleOutMapper.getBigSaleOutGoodsCustomerList(id, goodssod.getGoodsid());
						for(SaleoutDetail saleoutDetail : customerlist){
							Customer customer = getCustomerDetailNoCache(saleoutDetail.getCustomerid());
							if(null != customer){
//								if(StringUtils.isNotEmpty(customer.getName()) && customer.getName().length() > 7){
//									saleoutDetail.setCustomername(customer.getName().substring(0, 7));
//								}else{
								saleoutDetail.setCustomername(customer.getName());
//								}
							}else{
								saleoutDetail.setCustomername("其他未指定");
							}

							GoodsInfo goodsInfo2 = getAllGoodsInfoByIDNoCache(saleoutDetail.getGoodsid());
							if(null != goodsInfo2){
								goodsInfo2.setName(goodsInfo2.getName());

								if(StringUtils.isNotEmpty(goodsInfo2.getModel()) && goodsInfo2.getModel().length() > 6){
									if(goodsInfo2.getModel().getBytes().length == goodsInfo2.getModel().length()){
										goodsInfo2.setModel(goodsInfo2.getModel().substring(0,6));
									}else{
										goodsInfo2.setModel(goodsInfo2.getModel().substring(0,4));
									}
								}
								saleoutDetail.setGoodsInfo(goodsInfo2);
							}

							BigDecimal unitnum = null != saleoutDetail.getUnitnum() ? saleoutDetail.getUnitnum() : BigDecimal.ZERO;
							Map map1 = countGoodsInfoNumber(saleoutDetail.getGoodsid(),null,unitnum);
							BigDecimal auxnum = null != map1.get("auxInteger") ? new BigDecimal((String)map1.get("auxInteger")) : BigDecimal.ZERO;
							BigDecimal auxremainder = null != map1.get("auxremainder") ? new BigDecimal((String)map1.get("auxremainder")) : BigDecimal.ZERO;
							String auxnumdetail = null != map1.get("auxnumdetail") ? (String)map1.get("auxnumdetail") : "";
							saleoutDetail.setTotalauxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnum.intValue()) + saleoutDetail.getAuxunitname()));
							saleoutDetail.setAuxremainderdetail(CommonUtils.strDigitNumDeal(auxremainder.setScale(decimalScale, BigDecimal.ROUND_HALF_UP)+saleoutDetail.getUnitname()));
							saleoutDetail.setAuxnumdetail(auxnumdetail);
							saleoutDetail.setAuxremainder(auxremainder);
							saleoutDetail.setAuxnum(auxnum);

							auxnumsum = auxnumsum.add(auxnum);
							auxremaindersum = auxremaindersum.add(auxremainder);
						}

						goodssod.setTotalauxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumsum.intValue()) + goodssod.getAuxunitname()));
						goodssod.setAuxremainderdetail(CommonUtils.strDigitNumDeal(auxremaindersum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP)+goodssod.getUnitname()));
						goodssod.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumsum.intValue()) + goodssod.getAuxunitname() + auxremaindersum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP) + goodssod.getUnitname()));

						customerDivMap.put(goodssod,customerlist);
					}
				}
			}
		}
		return customerDivMap;
	}

	//region 按品牌分商品
	@Override
	public List<Map> getBigOutBrandGoodsDivMapForPrint(Map map)throws Exception{
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		List<Map> dataList=new ArrayList<Map>();
		String idarrs=(String)map.get("idarrs");
		if(null==idarrs || "".equals(idarrs.trim())){
			return dataList;
		}
		String[] idarr=StringUtils.split(idarrs,",");
		for(String id : idarr) {
			if (null == id || "".equals(id.trim())) {
				continue;
			}
			Map<String, List<SaleoutDetail>> billdetailmap = new LinkedHashMap<String, List<SaleoutDetail>>();
			Map<String,SaleoutDetail> brandsumMap=new HashMap<String, SaleoutDetail>();
			Map retMap = new HashMap();
			retMap.put("id",id.trim());

			BigSaleOut bigSaleOut = null;
			Map bigSaleOutMap=getBigSaleOutInfoForPrint(id);
			if(bigSaleOutMap.containsKey("bigSaleOut")){
				bigSaleOut=(BigSaleOut)bigSaleOutMap.get("bigSaleOut");
			}
			if(null != bigSaleOut){
				if(StringUtils.isNotEmpty(bigSaleOut.getStorageid())) {
					StorageInfo storageInfo = getStorageInfoByID(bigSaleOut.getStorageid());
					if (null != storageInfo) {
						bigSaleOut.setStoragename(storageInfo.getName());
					}
				}
				retMap.putAll(bigSaleOutMap);
				//分品牌合计
				List<SaleoutDetail> brandsumlist = bigSaleOutMapper.getBigSaleOutBrandSumList(id);
				if(brandsumlist.size() != 0){
					for(SaleoutDetail brandsumDetail : brandsumlist){
						Brand brand = getGoodsBrandByID(brandsumDetail.getBrandid());
						if(null != brand){
							brandsumDetail.setBrandname(brand.getName());
						}else{
							brandsumDetail.setBrandname("其他未指定");
						}
						BigDecimal auxnumsum = BigDecimal.ZERO;
						BigDecimal auxremaindersum = BigDecimal.ZERO;

						List<SaleoutDetail> brandlist = bigSaleOutMapper.getBigSaleOutBrandGoodsList(id,brandsumDetail.getBrandid());
						for(SaleoutDetail saleoutDetail : brandlist){
							Brand brand2 = getGoodsBrandByID(saleoutDetail.getBrandid());
							if(null != brand2){
								saleoutDetail.setBrandname(brand2.getName());
							}else{
								saleoutDetail.setBrandname("其他未指定");
							}
							GoodsInfo goodsInfo = getGoodsInfoByID(saleoutDetail.getGoodsid());
							if(null != goodsInfo){
								saleoutDetail.setGoodsname(goodsInfo.getName());
								saleoutDetail.setGoodsInfo(goodsInfo);
							}
							BigDecimal unitnum = null != saleoutDetail.getUnitnum() ? saleoutDetail.getUnitnum() : BigDecimal.ZERO;
							Map map1 = countGoodsInfoNumber(saleoutDetail.getGoodsid(),null,unitnum);
							BigDecimal auxnum = null != map1.get("auxInteger") ? new BigDecimal((String)map1.get("auxInteger")) : BigDecimal.ZERO;
							BigDecimal auxremainder = null != map1.get("auxremainder") ? new BigDecimal((String)map1.get("auxremainder")) : BigDecimal.ZERO;
							String auxnumdetail = null != map1.get("auxnumdetail") ? (String)map1.get("auxnumdetail") : "";
							saleoutDetail.setTotalauxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnum.intValue()) + saleoutDetail.getAuxunitname()));
							saleoutDetail.setAuxremainderdetail(CommonUtils.strDigitNumDeal(auxremainder.setScale(decimalScale, BigDecimal.ROUND_HALF_UP)+saleoutDetail.getUnitname()));
							saleoutDetail.setAuxnumdetail(auxnumdetail);
							saleoutDetail.setAuxremainder(auxremainder);
							saleoutDetail.setAuxnum(auxnum);

							auxnumsum = auxnumsum.add(auxnum);
							auxremaindersum = auxremaindersum.add(auxremainder);
						}

						brandsumDetail.setTotalauxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumsum.intValue()) + brandsumDetail.getAuxunitname()));
						brandsumDetail.setAuxremainderdetail(CommonUtils.strDigitNumDeal(auxremaindersum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP)+brandsumDetail.getUnitname()));
						brandsumDetail.setAuxremainder(auxremaindersum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP));
						brandsumDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumsum.intValue()) + brandsumDetail.getAuxunitname() + auxremaindersum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP) + brandsumDetail.getUnitname()));


						brandsumMap.put(brandsumDetail.getBrandid(),brandsumDetail);
						billdetailmap.put(brandsumDetail.getBrandid(),brandlist);
					}
				}

				retMap.put("billdetailmap", billdetailmap);
				retMap.put("billsummap", brandsumMap);
				retMap.put("billnum",brandsumMap.size());
			}
			dataList.add(retMap);
		}
		return dataList;
	}
	//endregion

	//region 按客户分商品
	@Override
	public List getBigSaleOutGoodsCustomerListForPrint(Map map) throws Exception {
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		List<Map> dataList=new ArrayList<Map>();
		String idarrs=(String)map.get("idarrs");
		if(null==idarrs || "".equals(idarrs.trim())){
			return dataList;
		}
		String[] idarr=StringUtils.split(idarrs,",");
		for(String id : idarr) {
			if (null == id || "".equals(id.trim())) {
				continue;
			}
			Map retMap = new HashMap();
			retMap.put("id",id.trim());
			List<SaleoutDetail> goodsCustomerList = new ArrayList<SaleoutDetail>();

			Map bigSaleOutMap=getBigSaleOutInfoForPrint(id);
			BigSaleOut bigSaleOut = null;
			if(bigSaleOutMap.containsKey("bigSaleOut")){
				bigSaleOut=(BigSaleOut)bigSaleOutMap.get("bigSaleOut");
			}
			Map customerMap=new HashMap();
			if (null != bigSaleOut) {
				if(StringUtils.isNotEmpty(bigSaleOut.getStorageid())) {
					StorageInfo storageInfo = getStorageInfoByID(bigSaleOut.getStorageid());
					if (null != storageInfo) {
						bigSaleOut.setStoragename(storageInfo.getName());
					}
				}
				retMap.putAll(bigSaleOutMap);
				//按商品分客户
				BigDecimal auxnumSum1 = BigDecimal.ZERO;
				BigDecimal auxremainderSum1 = BigDecimal.ZERO;
				goodsCustomerList = bigSaleOutMapper.getBigSaleOutGoodsCustomerListForPrint(id);
				if (goodsCustomerList.size() != 0) {
					for (SaleoutDetail saleoutDetail : goodsCustomerList) {
						GoodsInfo goodsInfo = getGoodsInfoByID(saleoutDetail.getGoodsid());
						if (null != goodsInfo) {
							saleoutDetail.setGoodsname(goodsInfo.getName());
							saleoutDetail.setGoodsInfo(goodsInfo);
						}

						Customer customer = null;
						if(retMap.containsKey("customerInfo_"+saleoutDetail.getCustomerid())){
							customer=(Customer)retMap.get("customerInfo_"+saleoutDetail.getCustomerid());
						}
						if(null == customer){
							customer = getCustomerByID(saleoutDetail.getCustomerid());
							retMap.put("customerInfo_"+saleoutDetail.getCustomerid(),customer);
						}
						if(null!=customer){
							customerMap.put(customer.getId(),customer.getId());
							saleoutDetail.setCustomername(customer.getName());
						}
						BigDecimal unitnum = null != saleoutDetail.getUnitnum() ? saleoutDetail.getUnitnum() : BigDecimal.ZERO;
						Map map1 = countGoodsInfoNumber(saleoutDetail.getGoodsid(), null, unitnum);
						BigDecimal auxnum = null != map1.get("auxInteger") ? new BigDecimal((String) map1.get("auxInteger")) : BigDecimal.ZERO;
						BigDecimal auxremainder = null != map1.get("auxremainder") ? new BigDecimal((String) map1.get("auxremainder")) : BigDecimal.ZERO;
						String auxnumdetail = null != map1.get("auxnumdetail") ? (String) map1.get("auxnumdetail") : "";
						saleoutDetail.setTotalauxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnum.intValue()) + saleoutDetail.getAuxunitname()));
						saleoutDetail.setAuxremainderdetail(CommonUtils.strDigitNumDeal(auxremainder.setScale(decimalScale, BigDecimal.ROUND_HALF_UP) + saleoutDetail.getUnitname()));
						saleoutDetail.setAuxnumdetail(auxnumdetail);
						saleoutDetail.setAuxremainder(auxremainder);
						saleoutDetail.setAuxnum(auxnum);

						auxnumSum1 = auxnumSum1.add(auxnum);
						auxremainderSum1 = auxremainderSum1.add(auxremainder);
					}
					/*
					SaleoutDetail saleoutDetailSum = new SaleoutDetail();
					GoodsInfo goodsInfoSum = new GoodsInfo();
					goodsInfoSum.setName("合计");
					saleoutDetailSum.setGoodsInfo(goodsInfoSum);
					saleoutDetailSum.setGoodsid("sum");
					saleoutDetailSum.setAuxnum(auxnumSum1);
					saleoutDetailSum.setAuxremainder(auxremainderSum1);
					saleoutDetailSum.setTotalauxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumSum1.intValue()) + "箱"));
					saleoutDetailSum.setAuxremainderdetail(CommonUtils.strDigitNumDeal(auxremainderSum1.setScale(decimalScale, BigDecimal.ROUND_HALF_UP).toString()));
					saleoutDetailSum.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumSum1.intValue()) + "箱" + auxremainderSum1.setScale(decimalScale, BigDecimal.ROUND_HALF_UP).toString()));
					goodsCustomerList.add(saleoutDetailSum);
					*/
				}
				retMap.put("customernums",customerMap.size());
				retMap.put("orderDetailList",goodsCustomerList);
			}
			dataList.add(retMap);
		}
		return dataList;
	}
	//endregion

	@Override
	public void updateDispatchBillPrinttimesBy(Map map) throws Exception{
		//发货通知单编号
		String id=(String)map.get("id");
		if(null == id || "".equals(id.trim())){
			return;
		}
		//打印时的配货打印次数
		String phprintimes=(String)map.get("phprinttimes");
		//打印时的发货打印次数
		String fhprinttimes=(String)map.get("fhprinttimes");
		//是否需要更新发货单打印次数
		String upSaleoutPrinttimes =(String)map.get("upSaleoutPrinttimes");
		
		upSaleoutPrinttimes=null==upSaleoutPrinttimes?"":upSaleoutPrinttimes.trim();
		//1 或 true 表可要是多新发货单打印次数
		if("1".equals(upSaleoutPrinttimes) || "true".equals(upSaleoutPrinttimes)){
			upSaleoutPrinttimes="1";
		}else{
			upSaleoutPrinttimes="0";
		}
		
		//配货打印次数，更新条件
		Long outPhprinttimes=(long)0;
		if(null==phprintimes || "".equals(phprintimes.trim())){
			phprintimes="false";
		}else if(StringUtils.isNumeric(phprintimes)){
			outPhprinttimes=Long.parseLong(phprintimes);
			phprintimes="compareOrg";//需要比较原打印次数后才能更新
		}else if("true".equals(phprintimes.trim())){
			//强制更新次数
			phprintimes="true";
		}else{
			phprintimes="false";
		}
		

		//发货打印次数更新条件
		Long outFhprinttimes=(long)0;
		if(null==fhprinttimes || "".equals(fhprinttimes.trim())){
			fhprinttimes="false";
		}else if(StringUtils.isNumeric(fhprinttimes)){
			outFhprinttimes=Long.parseLong(fhprinttimes);
			fhprinttimes="compareOrg";
		}else if("true".equals(fhprinttimes.trim())){
			fhprinttimes="true";
		}else{
			fhprinttimes="false";
		}
		
		//发货打印次数 与 配货打印次数都没有设置时，则都要更新
		if("false".equals(phprintimes) && "false".equals(fhprinttimes)){
			phprintimes="true";
			fhprinttimes="true";
		}
		//查询结果
		Map resultMap =new HashMap ();
		//查询条件参数
		Map<String,String> paramMap=new HashMap<String,String>();
		//查询当前打印次数
		paramMap.put("id",id.trim());
		resultMap=saleoutMapper.getDispatchBillPrintCountInfoBy(paramMap);
		
		Long orgPhprintimes=(long)0;
		Long orgFhprintimes=(long)0;
		if(null!=resultMap){
			orgPhprintimes=(Long) resultMap.get("phprinttimes");
			orgFhprintimes=(Long) resultMap.get("printtimes");
		}
		
		if("true".equals(phprintimes)
				|| ("compareOrg".equals(phprintimes) && outPhprinttimes.equals(orgPhprintimes))){
			paramMap.clear();
			paramMap.put("id", id.trim()); //发货通知单编号
			paramMap.put("printtype", "4");//更新配货打印次数
			saleoutMapper.updateSyncDispatchBillPrinttimes(paramMap);
			//是否要更新发货单配货打印次数
			if("1".equals(upSaleoutPrinttimes)){
				//发货状态
				paramMap.clear();
				paramMap.put("sourceid", id.trim());
				paramMap.put("phprinttimes", "true");//更新配货打印次数

				String phsaleoutStatusarrs = (String)map.get("ph_saleout_statusarrs");
				if(null!=phsaleoutStatusarrs && !"".equals(phsaleoutStatusarrs.trim())){
					paramMap.put("statusarr", phsaleoutStatusarrs);//更新发货打印次数
				}
				String saleoutidarr = (String)map.get("saleoutidarr");
				if(null!=saleoutidarr && !"".equals(saleoutidarr.trim())){
					paramMap.put("saleoutidarr", saleoutidarr);//更新发货单编号
				}
				saleoutMapper.updateSaleoutPrinttimesBy(paramMap);
			}
		}
		
		if("true".equals(fhprinttimes)
				|| ("compareOrg".equals(fhprinttimes) && outFhprinttimes.equals(orgFhprintimes))){
			paramMap.clear();
			paramMap.put("id", id.trim()); //发货通知单编号
			paramMap.put("printtype", "5");//更新发货打印次数
			saleoutMapper.updateSyncDispatchBillPrinttimes(paramMap);
			//是否要更新发货单配货打印次数
			if("1".equals(upSaleoutPrinttimes)){

				paramMap.clear();
				paramMap.put("sourceid", id.trim());
				paramMap.put("printtimes", "true");//更新发货打印次数

				//发货状态
				String saleoutStatusarrs = (String)map.get("saleout_statusarrs");
				if(null!=saleoutStatusarrs && !"".equals(saleoutStatusarrs.trim())){
					paramMap.put("statusarr", saleoutStatusarrs);//更新发货打印次数
				}
				String saleoutidarr = (String)map.get("saleoutidarr");
				if(null!=saleoutidarr && !"".equals(saleoutidarr.trim())){
					paramMap.put("saleoutidarr", saleoutidarr);//更新发货单编号
				}
				saleoutMapper.updateSaleoutPrinttimesBy(paramMap);
			}
		}		
	}
	@Override
	public void updateSaleOrderPrinttimesBy(Map map) throws Exception{
		//销售订单编号
		String id=(String)map.get("id");
		if(null == id || "".equals(id.trim())){
			return;
		}
		//打印时的配货打印次数
		String phprintimes=(String)map.get("phprinttimes");
		//打印时的发货打印次数
		String fhprinttimes=(String)map.get("fhprinttimes");
		//是否需要更新发货单打印次数
		String upSaleoutPrinttimes =(String)map.get("upSaleoutPrinttimes");
		//1 或 true 表可要是多新发货单打印次数
		if("1".equals(upSaleoutPrinttimes) || "true".equals(upSaleoutPrinttimes)){
			upSaleoutPrinttimes="1";
		}else{
			upSaleoutPrinttimes="0";
		}
		//是否需要更新发货通知单打印次数
		String upDispatchPrinttimes =(String)map.get("upDispatchPrinttimes");
		//1 或 true 表可要是多新发货通知单打印次数
		if("1".equals(upDispatchPrinttimes) || "true".equals(upDispatchPrinttimes)){
			upDispatchPrinttimes="1";
		}else{
			upDispatchPrinttimes="0";
		}
		
		//配货打印次数，更新条件
		Long outPhprinttimes=(long)0;
		if(null==phprintimes || "".equals(phprintimes.trim())){
			phprintimes="false";
		}else if(StringUtils.isNumeric(phprintimes)){
			outPhprinttimes=Long.parseLong(phprintimes);
			phprintimes="compareOrg";//需要比较原打印次数后才能更新
		}else if("true".equals(phprintimes.trim())){
			//强制更新次数
			phprintimes="true";
		}else{
			phprintimes="false";
		}
		

		//发货打印次数更新条件
		Long outFhprinttimes=(long)0;
		if(null==fhprinttimes || "".equals(fhprinttimes.trim())){
			fhprinttimes="false";
		}else if(StringUtils.isNumeric(fhprinttimes)){
			outFhprinttimes=Long.parseLong(fhprinttimes);
			fhprinttimes="compareOrg";
		}else if("true".equals(fhprinttimes.trim())){
			fhprinttimes="true";
		}else{
			fhprinttimes="false";
		}
		
		//发货打印次数 与 配货打印次数都没有设置时，则都要更新
		if("false".equals(phprintimes) && "false".equals(fhprinttimes)){
			phprintimes="true";
			fhprinttimes="true";
		}
		
		//查询结果
		Map resultMap =new HashMap ();
		//查询条件参数
		Map<String,String> paramMap=new HashMap<String,String>();
		//查询当前打印次数		
		paramMap.put("id",id.trim());
		resultMap=saleoutMapper.getSalesOrderPrintCountInfoBy(paramMap);
		Long orgPhprintimes=(long)0;
		Long orgFhprintimes=(long)0;
		if(null!=resultMap){
			orgPhprintimes=(Long) resultMap.get("phprinttimes");
			orgFhprintimes=(Long) resultMap.get("printtimes");
		}
		
		if("true".equals(phprintimes)
				|| ("compareOrg".equals(phprintimes) && outPhprinttimes==orgPhprintimes)){
			//更新销售单配货打印次数
			paramMap.clear();
			paramMap.put("id", id.trim()); //发货通知单编号
			paramMap.put("printtype", "4");//更新配货打印次数
			saleoutMapper.updateSyncSalesOrderPrinttimes(paramMap);
			//是否要更新发货配货打印次数
			if("1".equals(upSaleoutPrinttimes)){


				
				paramMap.clear();
				paramMap.put("saleorderid", id.trim());
				paramMap.put("phprinttimes", "true");//更新配货打印次数
				//发货状态
				String phsaleoutStatusarrs = (String)map.get("ph_saleout_statusarrs");
				if(null!=phsaleoutStatusarrs && !"".equals(phsaleoutStatusarrs.trim())){
					paramMap.put("statusarr", phsaleoutStatusarrs);
				}
				String saleoutidarr = (String)map.get("saleoutidarr");
				if(null!=saleoutidarr && !"".equals(saleoutidarr.trim())){
					paramMap.put("saleoutidarr", saleoutidarr);//更新发货单编号
				}
				saleoutMapper.updateSaleoutPrinttimesBy(paramMap);
			}
			//是否要更新发货通知单配货打印次数
			if("1".equals(upDispatchPrinttimes)){

				paramMap.clear();
				paramMap.put("sourceid", id.trim());
				paramMap.put("printtype", "4");//更新配货打印次数
				//销售订单打印时状态
				String phsalesStatusarrs =(String)map.get("ph_sales_statusarrs");
				if(null!=phsalesStatusarrs && !"".equals(phsalesStatusarrs.trim())){
					paramMap.put("statusarr", phsalesStatusarrs);
				}
				saleoutMapper.updateSyncDispatchBillPrinttimes(paramMap);
			}
		}
		
		if("true".equals(fhprinttimes)
				|| ("compareOrg".equals(fhprinttimes) && outFhprinttimes==orgFhprintimes)){
			
			//更新销售订单发货打印次数
			paramMap.clear();
			paramMap.put("id", id.trim()); //发货通知单编号
			paramMap.put("printtype", "5");//更新发货打印次数
			saleoutMapper.updateSyncSalesOrderPrinttimes(paramMap);
			
			//是否要更新发货单发货打印次数
			if("1".equals(upSaleoutPrinttimes)){

				paramMap.clear();
				paramMap.put("saleorderid", id.trim());
				paramMap.put("printtimes", "true");//更新发货打印次数

				//发货单打印时状态
				String saleoutStatusarrs = (String)map.get("saleout_statusarrs");
				if(null!=saleoutStatusarrs && !"".equals(saleoutStatusarrs.trim())){
					paramMap.put("statusarr", saleoutStatusarrs);//更新发货打印次数
				}

				String saleoutidarr = (String)map.get("saleoutidarr");
				if(null!=saleoutidarr && !"".equals(saleoutidarr.trim())){
					paramMap.put("saleoutidarr", saleoutidarr);//更新发货单编号
				}
				saleoutMapper.updateSaleoutPrinttimesBy(paramMap);
			}
			//是否要更新发货通知单配货打印次数
			if("1".equals(upDispatchPrinttimes)){

				//销售订单打印时状态
				String salesStatusarrs =(String)map.get("sales_statusarrs");
				
				paramMap.clear();
				paramMap.put("sourceid", id.trim());
				paramMap.put("printtype", "5");//更新发货打印次数
				if(null!=salesStatusarrs && !"".equals(salesStatusarrs.trim())){
					paramMap.put("statusarr", salesStatusarrs);//更新发货打印次数
				}
				saleoutMapper.updateSyncDispatchBillPrinttimes(paramMap);
			}
		}		
	}
    @Override
	public void updateSaleoutPrinttimesBy(Map map) throws Exception{
		//打印时的配货打印次数
		String phprintimes=(String)map.get("phprinttimes");
		if(!(phprintimes=="true" || phprintimes=="1")){
			map.remove("phprinttimes");
		}
		//打印时的发货打印次数
		String printtimes=(String)map.get("printtimes");
		if(!(printtimes=="true" || printtimes=="1")){
			map.remove("printtimes");
		}

		saleoutMapper.updateSaleoutPrinttimesBy(map);
	}
}

