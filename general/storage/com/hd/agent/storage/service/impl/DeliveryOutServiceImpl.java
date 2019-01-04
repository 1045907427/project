/**
 * @(#)DeliveryOutServiceImpl.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月13日 huangzhiqian 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.delivery.dao.DeliveryAogreturnMapper;
import com.hd.agent.delivery.dao.DeliveryOrderMapper;
import com.hd.agent.delivery.service.IDistributeService;
import com.hd.agent.purchase.service.IReturnOrderService;
import com.hd.agent.storage.dao.StorageDeliveryOutMapper;
import com.hd.agent.storage.model.StorageDeliveryOut;
import com.hd.agent.storage.model.StorageDeliveryOutDetail;
import com.hd.agent.storage.model.StorageDeliveryOutForJob;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.DeliveryOutService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class DeliveryOutServiceImpl extends BaseStorageServiceImpl implements DeliveryOutService {
	private StorageDeliveryOutMapper storageDeliveryOutMapper;
	private IDistributeService distributeService;
	private DeliveryOrderMapper deliveryOrderMapper;
	private DeliveryAogreturnMapper deliveryAogreturnMapper;
	private IReturnOrderService returnOrderService;
	
	public IReturnOrderService getReturnOrderService() {
		return returnOrderService;
	}
	public void setReturnOrderService(IReturnOrderService returnOrderService) {
		this.returnOrderService = returnOrderService;
	}
	public DeliveryOrderMapper getDeliveryOrderMapper() {
		return deliveryOrderMapper;
	}
	public void setDeliveryOrderMapper(DeliveryOrderMapper deliveryOrderMapper) {
		this.deliveryOrderMapper = deliveryOrderMapper;
	}
	public DeliveryAogreturnMapper getDeliveryAogreturnMapper() {
		return deliveryAogreturnMapper;
	}
	public void setDeliveryAogreturnMapper(DeliveryAogreturnMapper deliveryAogreturnMapper) {
		this.deliveryAogreturnMapper = deliveryAogreturnMapper;
	}
	public IDistributeService getDistributeService() {
		return distributeService;
	}
	public void setDistributeService(IDistributeService distributeService) {
		this.distributeService = distributeService;
	}
	public StorageDeliveryOutMapper getStorageDeliveryOutMapper() {
		return storageDeliveryOutMapper;
	}

	public void setStorageDeliveryOutMapper(
			StorageDeliveryOutMapper storageDeliveryOutMapper) {
		this.storageDeliveryOutMapper = storageDeliveryOutMapper;
	}

	@Override
	public Map<String,Object> insertDatas(StorageDeliveryOut storageDeliveryOut, List list) throws Exception {
				String id="";
				if (isAutoCreate("t_storage_delivery_out")) {
					// 获取自动编号
					id = getAutoCreateSysNumbderForeign(storageDeliveryOut, "t_storage_delivery_out");
					storageDeliveryOut.setId(id);
				}else{
					id="CGRU"+CommonUtils.getDataNumberSendsWithRand();
					storageDeliveryOut.setId(id);
				}  
				
				for(Object obj :list){
					StorageDeliveryOutDetail detail=(StorageDeliveryOutDetail)obj;
					detail.setBillid(id);
				}	
				boolean flag=true;
				String msg = "";
				for(Object obj :list){
					StorageDeliveryOutDetail outdetail=(StorageDeliveryOutDetail)obj;
					StorageSummaryBatch storageSummaryBatch = null;
					//如果有batchid直接根据id查
					if(StringUtils.isNotEmpty(outdetail.getSummarybatchid())){
						storageSummaryBatch = getStorageSummaryBatchById(outdetail.getSummarybatchid());
					}
					if(storageSummaryBatch == null){
						//根据仓库,商品编号,批次获取 库存分支
						storageSummaryBatch = getStorageSummaryBatchByStorageidAndBatchno(storageDeliveryOut.getStorageid(), outdetail.getBatchno(), outdetail.getGoodsid());
						if(storageSummaryBatch ==null){
							//未找到 根据仓库,商品编号 获取(1 商品无批次管理,2 商品开始无批次,后来有批次时)
							storageSummaryBatch = getStorageSummaryBatchByStorageidAndGoodsid(storageDeliveryOut.getStorageid(), outdetail.getGoodsid());
						}
						if(storageSummaryBatch==null){
							throw new RuntimeException("找不到库存分支");
						}
						outdetail.setSummarybatchid(storageSummaryBatch.getId());
					}else{
						//有id时,其他数据可能未传入
						outdetail.setBatchno(storageSummaryBatch.getBatchno());
						outdetail.setProduceddate(storageSummaryBatch.getProduceddate());
						outdetail.setDeadline(storageSummaryBatch.getDeadline());
					}
					
					
					if(storageSummaryBatch!=null&&storageSummaryBatch.getUsablenum().compareTo(outdetail.getUnitnum())==-1){
						GoodsInfo goodsInfo = getAllGoodsInfoByID(outdetail.getGoodsid());
						if("1".equals(goodsInfo.getIsbatch())){
							msg = "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum()+storageSummaryBatch.getUnitname()+"可用量";
						}else if("1".equals(goodsInfo.getIsstoragelocation())){
							StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
							if(null!=storageLocation){
								msg = "商品:"+goodsInfo.getName()+",在库位:"+storageLocation.getName()+"中,数量不足，该库位只有"+storageSummaryBatch.getUsablenum()+storageSummaryBatch.getUnitname()+"可用量";
							}
						}else{
							StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
							msg = "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum()+storageSummaryBatch.getUnitname()+"可用量";
						}
						flag = false;
						break;
					}
				}
				
				if(flag){
					for(Object obj :list){
						StorageDeliveryOutDetail outdetail=(StorageDeliveryOutDetail)obj;
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(outdetail.getSummarybatchid());
						if(null!=storageSummaryBatch){
							//保存状态时，更新待发量
							if("2".equals(storageDeliveryOut.getStatus())){
								//更新待发量
								updateStorageSummaryWaitnum(storageSummaryBatch.getId(), outdetail.getUnitnum());
							}
						}
                        //记录添加时的成本价
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(outdetail.getGoodsid());
                        outdetail.setAddcostprice(getGoodsCostprice(storageDeliveryOut.getStorageid(),goodsInfo));
						storageDeliveryOutMapper.insertStorageDeliveryOutDetail(outdetail);
					}
					storageDeliveryOutMapper.insertStorageDeliveryOut(storageDeliveryOut);
					
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("flag", flag);
				map.put("msg", CommonUtils.strDigitNumDeal(msg));
				return map;
	}

	@Override
	public PageData getStorageDeliveryOutList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_storage_delivery_out", null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_storage_delivery_out", null);
		pageMap.setDataSql(sql);
		List<StorageDeliveryOut> rsList = storageDeliveryOutMapper.getStorageDeliveryOutList(pageMap);
		for(StorageDeliveryOut out:rsList){
			String supplierid=out.getSupplierid();
			if(supplierid!=null&&!"".equals(supplierid)){
				BuySupplier supplier=getSupplierInfoById(supplierid);
				if(supplier!=null){
					out.setSuppliername(supplier.getName());
				}	
			}	
			String customerid=out.getCustomerid();
			
			if(customerid!=null&&!"".equals(customerid)){
				Customer customer=getCustomerByID(customerid);
				if(customer!=null){
					out.setCustomername(customer.getName());
				}
			}
			
			String pcustomerid=out.getPcustomerid();
			if(StringUtils.isNotEmpty(pcustomerid)){
				Customer pcustomer=getCustomerByID(pcustomerid);
				if(pcustomer!=null&&pcustomer.getName()!=null){
					out.setPcustomername(pcustomer.getName());
				}
			}
			
			/*String cussort=out.getCustomersort();
			if(StringUtils.isNotEmpty(cussort)){
				CustomerSort sort=getCustomerSortByID(cussort);
				if(sort!=null&&sort.getName()!=null){
					out.setCustomersort(sort.getName());
				}
			}*/
			
			
			
			
			if("2".equals(out.getBilltype())){
				if("2".equals(out.getSourcetype())){
					String customerName=getUpCustomerName(out.getSourceid());
					out.setCustomername(customerName);
				}
			}
			
			String storageid=out.getStorageid();
			if(storageid!=null&&!"".equals(storageid)){
				StorageInfo storageinfo=getStorageInfoByID(storageid);
				if(storageinfo!=null){
					out.setStoragename(storageinfo.getName());
				}
			}
		}
		
		int count = storageDeliveryOutMapper.getStorageDeliveryOutCount(pageMap);
		
		return new PageData(count, rsList, pageMap);
		
	}
	
	@Override
	public int deleteDetailsByBillId(String billid) {
		return storageDeliveryOutMapper.deleteDetailsByBillId(billid);		
	}
	
	@Override
	public StorageDeliveryOut getStorageDeliveryOutById(String id) {
		
		return storageDeliveryOutMapper.getStorageDeliveryOutById(id);
	}
	
	
	@Override
	public boolean deleteOutAndDetailById(String id) throws Exception {
			boolean flag=false;
			StorageDeliveryOut out=getStorageDeliveryOutById(id);
			if(out==null) return false;
			//不是暂存保存状态的 不能删除
		    if((!"1".equals(out.getStatus()))&&(!"2".equals(out.getStatus()))){
		        	return false;
		    }
		    
		    if("2".equals(out.getStatus())){
				List<StorageDeliveryOutDetail> detailList = storageDeliveryOutMapper.getDetailsByBillId(id);
				//删除明细 回滚待发量
				for(StorageDeliveryOutDetail detail : detailList){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(detail.getSummarybatchid());
					if(null!=storageSummaryBatch){
						//回滚待发量
						rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), detail.getUnitnum());
					}
				}
			}
		    flag=storageDeliveryOutMapper.deleteStorageDeliveryOutById(id)>0;
			return flag;	
	}

	@Override
	public List<Object> getEnterAndDetailById(String id) throws Exception {
		List<Object> rslist=new ArrayList<Object>();
		StorageDeliveryOut  storageDeliveryOut =getStorageDeliveryOutById(id);
		List<StorageDeliveryOutDetail> list = storageDeliveryOutMapper.getDetailsByBillId(id);
		if(storageDeliveryOut!=null){
			rslist.add(storageDeliveryOut);
			if(list!=null&&list.size()>0){
				for(StorageDeliveryOutDetail detail:list){
					
					GoodsInfo info=getGoodsInfoByID(detail.getGoodsid());
					BigDecimal unitNum=detail.getUnitnum();//总数量
					BigDecimal boxNum=info.getBoxnum();//箱装量
					BigDecimal boxes=unitNum.divide(boxNum,0);//箱数
					BigDecimal leftNum=unitNum.subtract(boxes.multiply(boxNum));//剩余散装量
					BigDecimal AllboxVolum=new BigDecimal(0);
//					if(info.getTotalvolume()!=null){
//							AllboxVolum=(boxes.multiply(info.getTotalvolume())).add(leftNum.multiply(info.getSinglevolume()));//总体积
//					}else{
						if(info.getSinglevolume()!=null){
							AllboxVolum=unitNum.multiply(info.getSinglevolume());
						}
//					}
					BigDecimal AllWeight=new BigDecimal(0);
//					if(info.getTotalweight()!=null){
					//总重量
//							AllWeight=(boxes.multiply(info.getTotalweight())).add(leftNum.multiply(info.getGrossweight()));  
//					}else{
						if(info.getGrossweight()!=null){
							AllWeight=unitNum.multiply(info.getGrossweight());
						}
//					}
					detail.setBasesaleprice(detail.getPrice());//单价回写
					detail.setBoxprice(info.getBoxnum().multiply(detail.getPrice()));//箱价回写
					detail.setWeight(AllWeight);//重量回写
					detail.setVolumn(AllboxVolum);//体积回写
					detail.setGoodsInfo(info);
					detail.setGoodsname(info.getName());
				}
				rslist.add(list);
			}
			return rslist;
		}
		return null;
	}

	@Override
	public Map updataEnterAndDetail(StorageDeliveryOut storageDeliveryOut,
			List<StorageDeliveryOutDetail> bodList) throws Exception {
		boolean flag = true;
		String msg = "";
		Map map = new HashMap();
		
		StorageDeliveryOut oldOuter=getStorageDeliveryOutById(storageDeliveryOut.getId());
		if(null==oldOuter ||"3".equals(oldOuter.getStatus()) || "4".equals(oldOuter.getStatus())){
			map.put("flag", false);
			map.put("msg", "单据已审核，请刷新后再操作。");
			return map;
		}
		
		for(StorageDeliveryOutDetail outdetail : bodList){
			if(null!=outdetail.getId()){
				StorageDeliveryOutDetail oldDetail=storageDeliveryOutMapper.getDetailById(outdetail.getId()+"");
				//修改后的数量大于修改前的数量 判断可用量是否足够
				if(outdetail.getUnitnum().compareTo(oldDetail.getUnitnum())==1){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(outdetail.getSummarybatchid());
					//批次现存量中的可用量是否大于 修改后数量-修改前数量
					if(storageSummaryBatch.getUsablenum().compareTo(outdetail.getUnitnum().subtract(oldDetail.getUnitnum()))==-1){
						GoodsInfo goodsInfo = getAllGoodsInfoByID(outdetail.getGoodsid());
						if("1".equals(goodsInfo.getIsbatch())){
							msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
						}else if("1".equals(goodsInfo.getIsstoragelocation())){
							StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
							msg += "商品:"+goodsInfo.getName()+",在库位:"+storageLocation.getName()+"中,数量不足，该库位只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
						}else{
							StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
							msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
						}
						flag = false;
					}
				}
			}else{

					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(outdetail.getSummarybatchid());
					
					if(storageSummaryBatch == null){
						storageSummaryBatch = getStorageSummaryBatchByStorageidAndBatchno(storageDeliveryOut.getStorageid(), outdetail.getBatchno(), outdetail.getGoodsid());
						if(storageSummaryBatch == null ){
							//未找到 根据仓库,商品编号 获取(1 商品无批次管理,2 商品开始无批次,后来有批次时)
							storageSummaryBatch = getStorageSummaryBatchByStorageidAndGoodsid(storageDeliveryOut.getStorageid(), outdetail.getGoodsid());
						}
						if(storageSummaryBatch==null){
							throw new RuntimeException("找不到库存分支");
						}
						outdetail.setSummarybatchid(storageSummaryBatch.getId());
					}else{
						outdetail.setBatchno(storageSummaryBatch.getBatchno());
					}
					if(storageSummaryBatch.getUsablenum().compareTo(outdetail.getUnitnum())==-1){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(outdetail.getGoodsid());
					if("1".equals(goodsInfo.getIsbatch())){
						msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
					}else if("1".equals(goodsInfo.getIsstoragelocation())){
						StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
						msg += "商品:"+goodsInfo.getName()+",在库位:"+storageLocation.getName()+"中,数量不足，该库位只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
					}else{
						StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
						msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
					}
					flag = false;
				}
			}
		}	
		if(flag){
			//修改前其他出库单信息 回滚待发量
			if("2".equals(oldOuter.getStatus())){
				List<StorageDeliveryOutDetail> oldDetails = storageDeliveryOutMapper.getDetailsByBillId(storageDeliveryOut.getId());
				for(StorageDeliveryOutDetail oldDetail : oldDetails){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(oldDetail.getSummarybatchid());
					//回滚修改前的待发量
					rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), oldDetail.getUnitnum());
				}
			}
			
			storageDeliveryOutMapper.deleteDetailsByBillId(storageDeliveryOut.getId());
			
			
			for(StorageDeliveryOutDetail outDetail : bodList){
				outDetail.setBillid(storageDeliveryOut.getId());
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(outDetail.getSummarybatchid());
				if(null!=storageSummaryBatch){
					//保存状态时，更新待发量
					if("2".equals(storageDeliveryOut.getStatus())){
						//更新待发量
						updateStorageSummaryWaitnum(storageSummaryBatch.getId(), outDetail.getUnitnum());
					}
				}
                //记录添加时的成本价
                GoodsInfo goodsInfo = getAllGoodsInfoByID(outDetail.getGoodsid());
                outDetail.setAddcostprice(getGoodsCostprice(storageDeliveryOut.getStorageid(),goodsInfo));
				storageDeliveryOutMapper.insertStorageDeliveryOutDetail(outDetail);
			}
			flag=storageDeliveryOutMapper.updateDeliveryOut(storageDeliveryOut)>0;
		}
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public boolean updateDeliveryOut(StorageDeliveryOut saveout) {
		
		return storageDeliveryOutMapper.updateDeliveryOut(saveout)>0;
	}

	@Override
	public boolean audit(String id,StorageDeliveryOut updateOut) throws Exception{
		StorageDeliveryOut oldOut=storageDeliveryOutMapper.getStorageDeliveryOutById(id);
		boolean flag=false;
		if(oldOut!=null){
			if("2".equals(oldOut.getStatus()) || "6".equals(oldOut.getStatus())){
				List<StorageDeliveryOutDetail> list = storageDeliveryOutMapper.getDetailsByBillId(id);
				//更新仓库数量
				for(StorageDeliveryOutDetail outdetail : list){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(outdetail.getSummarybatchid());
					if(null!=storageSummaryBatch){
						//更新现存量信息
						sendStorageSummaryWaitnum(storageSummaryBatch.getId(), outdetail.getUnitnum(),"StorageDeliveryOut",id);
					}
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(outdetail.getGoodsid());
                    if(null != goodsInfo){
                        BigDecimal costprice = getGoodsCostprice(oldOut.getStorageid(),goodsInfo);
                        if(costprice.compareTo(BigDecimal.ZERO)==0){
                            costprice = goodsInfo.getNewstorageprice();
                        }
                        if("1".equals(oldOut.getBilltype())){
                            //调整仓库商品成本价
                            updateStorageGoodsPriceByPurchaseOut(oldOut.getStorageid(), outdetail.getGoodsid(), outdetail.getUnitnum(),costprice);
                        }
                        //采购入库审核后 入库按成本价入库
                        storageDeliveryOutMapper.updateDeliverOutDetailAddcostprice(outdetail.getId().toString(), outdetail.getGoodsid(), costprice);
                    }
				}
				flag= storageDeliveryOutMapper.updateDeliveryOut(updateOut)>0;
				if(flag){
					//有来源时 关闭来源订单
					if(oldOut.getSourceid()!=null&&!"0".equals(oldOut.getSourceid())){
						if(oldOut.getSourcetype()!=null&&"1".equals(oldOut.getSourcetype())){
						//关闭来源
							flag=distributeService.closeDeliveryAogreturn(oldOut.getSourceid());
						}
						if(oldOut.getSourcetype()!=null&&"2".equals(oldOut.getSourcetype())){
							//关闭来源
							flag=distributeService.closeDeliveryOrder(oldOut.getSourceid());
						}
					}
				}
				
			}	
		}
		return flag;
	}
	@Override
	public StorageDeliveryOutDetail getDeliveryOutDetailById(String id) {
		return storageDeliveryOutMapper.getDetailById(id);
	}
	@Override
	public String getUpCustomerName(String sourceid) {
		String customerName=deliveryOrderMapper.getDeliveryOrderByID(sourceid).getCustomername();
		if(StringUtils.isNotEmpty(customerName)){
			return customerName;
		}
		return "";
	}
	@Override
	public Map oppauditDeliveryOut(String id) throws Exception {
		Map resultMap=new HashMap();
		StorageDeliveryOut out = storageDeliveryOutMapper.getStorageDeliveryOutById(id);
		boolean flag = false;
		boolean rollBackFlag = true;
		if(null==out){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要反审的数据");
			return resultMap;
		}
		if("1".equals(out.getIscheck())){
			resultMap.put("flag", false);
			resultMap.put("msg", "代配送出库单验收后不能反审");
			return resultMap;
		}
		
		if("3".equals(out.getStatus())){
			List<StorageDeliveryOutDetail> list = storageDeliveryOutMapper.getDetailsByBillId(id);
			for(StorageDeliveryOutDetail detail : list){
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(detail.getSummarybatchid());
				if(null!=storageSummaryBatch){
					//更新现存量信息
					rollBackFlag=rollBackSendStorageSummaryWaitnum(storageSummaryBatch.getId(), detail.getUnitnum(),"StorageDeliveryOut",id);
					if(rollBackFlag==false){
						break;
					}
                    //单据类型1供应商配送退货出库单2客户配送出库单
                    if("1".equals(out.getBilltype())){
                        //反审 采购入库操作 调整仓库成本价
                        updateStorageGoodsPriceByPurchaseEnter(out.getStorageid(),detail.getGoodsid(),detail.getUnitnum(),detail.getAddcostprice());
                    }
				}
			}
			if(rollBackFlag){
				SysUser sysUser = getSysUser();
				StorageDeliveryOut updateOut=new StorageDeliveryOut();
				updateOut.setStatus("2");
				updateOut.setId(id);
				updateOut.setAudittime(new Date());
				updateOut.setAuditusername(sysUser.getUsername());
				updateOut.setAudituserid(sysUser.getUserid());
				int i = storageDeliveryOutMapper.updateDeliveryOut(updateOut);
				flag = i>0;
				//来源类型为采购退货单
				if(!flag){
					 resultMap.put("msg", "代配送出库单反审失败");
				}
				else{
					 if("1".equals(out.getSourcetype())){
						 deliveryAogreturnMapper.oppDeliveryOut(out.getSourceid());
					 }
					 else if("2".equals(out.getSourcetype())){
						 deliveryOrderMapper.oppDeliveryOut(out.getSourceid());
					 }
				}
			}else{
				 flag=false;
				 resultMap.put("msg", "代配送出库单回滚失败，不能进行反审操作");
			}
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	@Override
	public Map batchCheck(String idarrs) throws Exception {
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
		SysUser sysUser=getSysUser();
		int isuccess=0;
		int ifail=0;
		for(String id : idArr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			id=id.trim();
			StorageDeliveryOut updateOut=new StorageDeliveryOut();
			updateOut.setIscheck("1"); //检查
			updateOut.setCheckuserid(sysUser.getUserid());
			updateOut.setCheckname(sysUser.getName());
			updateOut.setChecktime(new Date());
			updateOut.setStatus("4");
//			updateOut.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			updateOut.setId(id);
			boolean flag=storageDeliveryOutMapper.updateDeliveryOut(updateOut)>0;
			if(flag){
				successidSBuffer.append(id+",");
				isuccess=isuccess+1;
				continue;
			}else{
				ifail=ifail+1;
				failidStringBuffer.append(id+",");
			}
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
		return resultMap;
	}
	@Override
	public boolean doEntersForJob() throws Exception{
		
		boolean flag=true;
		List<StorageDeliveryOutForJob> outList=storageDeliveryOutMapper.getOutsForJob();
		//如果当天没有记录 返回true
		if(outList==null||outList.size()==0){
			return true;
		}
		List<StorageDeliveryOutForJob> outJobDetails=null;
		for(StorageDeliveryOutForJob outJob:outList){
			if(StringUtils.isEmpty(outJob.getSupplierid())||StringUtils.isEmpty(outJob.getStorageid())){
				continue;
			}
			outJobDetails=storageDeliveryOutMapper.getOutDetailForJobWithOutGroup(outJob.getId());
			boolean tmp=returnOrderService.addReturnOrderByDelivery(outJob,outJobDetails);
			if(tmp){
                storageDeliveryOutMapper.updateJobExeCuteTimes(outJob.getId());
			}else{
				flag=false;
			}
		}
			
		
		
		
		return flag;
	}
	@Override
	public List<StorageDeliveryOut> printDeliveryOut(Map map) throws Exception {
		String datasql = getDataAccessRule("t_storage_delivery_out",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<StorageDeliveryOut> list=storageDeliveryOutMapper.showDeliveryOutByIds(map);
		
		
		for(StorageDeliveryOut delivery : list){
			if("1".equals(delivery.getBilltype())){
				//供应商
				if(StringUtils.isNotEmpty(delivery.getSupplierid())){
					BuySupplier buySupplier=getSupplierInfoById(delivery.getSupplierid());
					if(null!=buySupplier){
						delivery.setSuppliername(buySupplier.getName());
					}
				}
				
			}else if("2".equals(delivery.getBilltype())){
				//客户
				if(StringUtils.isNotEmpty(delivery.getCustomerid())){
					//有来源类型时,取上一级的客户名称
					if("2".equals(delivery.getSourcetype())){
						String customerName=getUpCustomerName(delivery.getSourceid());
						delivery.setCustomername(customerName);
					}else{
						Customer cus=getCustomerByID(delivery.getCustomerid());
						if(null!=cus){
							delivery.setCustomername(cus.getName());
						}
					}
					
					
				}
			}
			if(StringUtils.isNotEmpty(delivery.getStorageid())){
				StorageInfo storageInfo = getStorageInfoByID(delivery.getStorageid());
				if(null!=storageInfo){
					delivery.setStoragename(storageInfo.getName());
				}
			}
			
			if(showdetail){
				List<StorageDeliveryOutDetail> detailList=storageDeliveryOutMapper.getDetailsByBillId(delivery.getId());
				if(null!=detailList && detailList.size()>0){
					int seq=1;
					for(StorageDeliveryOutDetail detail :detailList){
						detail.setUnitnum(detail.getUnitnum().setScale(0));
						if(null!=detail ){
							detail.setSeq(seq++);
							String goodsId=detail.getGoodsid();
							if(StringUtils.isNotEmpty(goodsId)){
								GoodsInfo info=getGoodsInfoByID(goodsId);
								if(info!=null){
									detail.setGoodsname(info.getName());
									detail.setBarcode(info.getBarcode());
									detail.setGoodsInfo(info);
								}
							}
						   String billtype=delivery.getBilltype();
						   //打印时的价格,针对供应商取buyprice,针对客户取price
						   if("1".equals(billtype)){
							   detail.setPrintprice(detail.getBuyprice().setScale(2,BigDecimal.ROUND_HALF_DOWN));
							   detail.setPrintAllprice(detail.getBuyprice().multiply(detail.getUnitnum()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
						   }else if("2".equals(billtype)){
							   detail.setPrintprice(detail.getPrice().setScale(2,BigDecimal.ROUND_HALF_DOWN));
							   detail.setPrintAllprice(detail.getPrice().multiply(detail.getUnitnum()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
						   }
							
						}
					}
					delivery.setDetailList(detailList);
				}
			}
		}
		return list;
	}
	@Override
	public void updateOrderPrinttimes(String id) throws Exception {
		storageDeliveryOutMapper.updateOrderPrinttimes(id);
		
	}
	@Override
	public StorageSummaryBatch getStorageSummaryBatchByStorageidAndGoodsidAndBatchNo(String goodsId, String storageid, String batchno) throws Exception {
		StorageSummaryBatch  batch =  getStorageSummaryBatchByStorageidAndBatchno(storageid, batchno, goodsId);
		if(batch == null ){
			batch = getStorageSummaryBatchByStorageidAndGoodsid(storageid, goodsId);
		}
		return batch;
	}
	
	

	
	
}

