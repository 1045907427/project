/**
 * @(#)DistributeRejectServiceImpl.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年8月4日 huangzhiqian 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.delivery.dao.DeliveryAogorderMapper;
import com.hd.agent.delivery.dao.DeliveryRejectbillMapper;
import com.hd.agent.storage.dao.StorageDeliveryEnterMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IDistributeRejectService;
import com.hd.agent.storage.service.IPurchaseEnterService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class DistributeRejectServiceImpl extends BaseStorageServiceImpl implements
		IDistributeRejectService {
	private IPurchaseEnterService purchaseEnterService;
	
	public IPurchaseEnterService getPurchaseEnterService() {
		return purchaseEnterService;
	}
	public void setPurchaseEnterService(IPurchaseEnterService purchaseEnterService) {
		this.purchaseEnterService = purchaseEnterService;
	}
	private StorageDeliveryEnterMapper storageDeliveryEnterMapper;
	private DeliveryAogorderMapper deliveryAogorderMapper;
	private DeliveryRejectbillMapper deliveryRejectbillMapper;
	public void setDeliveryRejectbillMapper(
			DeliveryRejectbillMapper deliveryRejectbillMapper) {
		this.deliveryRejectbillMapper = deliveryRejectbillMapper;
	}
	public void setDeliveryAogorderMapper(
			DeliveryAogorderMapper deliveryAogorderMapper) {
		this.deliveryAogorderMapper = deliveryAogorderMapper;
	}
	public StorageDeliveryEnterMapper getStorageDeliveryEnterMapper() {
		return storageDeliveryEnterMapper;
	}

	public void setStorageDeliveryEnterMapper(
			StorageDeliveryEnterMapper storageDeliveryEnterMapper) {
		this.storageDeliveryEnterMapper = storageDeliveryEnterMapper;
	}


	@Override
	public PageData getStorageDeliveryEnterList(PageMap pageMap) throws Exception {
		String cols = getAccessColumnList("t_storage_delivery_enter", null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_storage_delivery_enter", null);
		pageMap.setDataSql(sql);
		List<StorageDeliveryEnter> rsList = storageDeliveryEnterMapper
				.getStorageDeliveryEnterList(pageMap);
		for(StorageDeliveryEnter enter:rsList){
			String supplierid=enter.getSupplierid();
			if(supplierid!=null&&!"".equals(supplierid)){
				BuySupplier supplier=getSupplierInfoById(supplierid);
				if(supplier!=null){
					enter.setSuppliername(supplier.getName());
				}	
			}	
			String customerid=enter.getCustomerid();
			if(customerid!=null&&!"".equals(customerid)){
				Customer customer=getCustomerByID(customerid);
				if(customer!=null){
					enter.setCustomername(customer.getName());
				}
			}
			
			
			String pcustomerid=enter.getPcustomerid();
			if(StringUtils.isNotEmpty(pcustomerid)){
				Customer pcustomer=getCustomerByID(pcustomerid);
				if(pcustomer!=null&&pcustomer.getName()!=null){
					enter.setPcustomername(pcustomer.getName());
				}
			}
			
			
			
			
			
			if("2".equals(enter.getBilltype())){
				if("2".equals(enter.getSourcetype())){
					String customerName=getUpCustomerName(enter.getSourceid());
					enter.setCustomername(customerName);
				}
			}

			
			String storageid=enter.getStorageid();
			if(storageid!=null&&!"".equals(storageid)){
				StorageInfo storageinfo=getStorageInfoByID(storageid);
				if(storageinfo!=null){
					enter.setStoragename(storageinfo.getName());
				}
			}
		}
		int count = storageDeliveryEnterMapper.getStorageDeliveryEnterCount(pageMap);
		
		return new PageData(count, rsList, pageMap);
	}

	@Override
	public Map getGoodsInfo(String goodsId) {
		
		return storageDeliveryEnterMapper.getGoodsInfo(goodsId);
	}

	@Override
	public boolean insertDatas(StorageDeliveryEnter storageDeliveryEnter,
			List list) throws Exception {
		String id="";
		boolean flag=true;
		if (isAutoCreate("t_storage_delivery_enter")) {
			// 获取自动编号
			id = getAutoCreateSysNumbderForeign(storageDeliveryEnter, "t_storage_delivery_enter");
			storageDeliveryEnter.setId(id);
		}else{
			storageDeliveryEnter.setId("CGRU"+CommonUtils.getDataNumberSendsWithRand());
			return false;
		}  
		flag=storageDeliveryEnterMapper.insertStorageDeliveryEnter(storageDeliveryEnter)>0;
		
		for(Object obj :list){
			StorageDeliveryEnterDetail detail=(StorageDeliveryEnterDetail)obj;
			detail.setBillid(id);
		}	
		for(Object obj :list){
			StorageDeliveryEnterDetail enterdetail=(StorageDeliveryEnterDetail)obj;
			//保存状态时，更新在途量
			if("2".equals(storageDeliveryEnter.getStatus())){
				updateStorageSummaryTransitnum(storageDeliveryEnter.getStorageid(), enterdetail.getGoodsid(), enterdetail.getUnitnum());
			}
            //记录添加时的成本价
            GoodsInfo goodsInfo = getAllGoodsInfoByID(enterdetail.getGoodsid());
            enterdetail.setAddcostprice(getGoodsCostprice(storageDeliveryEnter.getStorageid(),goodsInfo));
			int rs=storageDeliveryEnterMapper.insertStorageDeliveryEnterDetail(enterdetail);
			if(rs<0){
				flag=false;
			}
		}	
		
		return flag;
	}
	
	
	

	@Override
	public List getDistributeRejectInfoByID(String id) throws Exception {
		List<Object> rslist=new ArrayList<Object>();
		StorageDeliveryEnter  storageDeliveryEnter =storageDeliveryEnterMapper.getDeliveryEnterByID(id);
		List<StorageDeliveryEnterDetail> list = storageDeliveryEnterMapper.getDeliveryEnterDetailByID(id);
		if(storageDeliveryEnter!=null){
			rslist.add(storageDeliveryEnter);
			if(list!=null&&list.size()>0){
				for(StorageDeliveryEnterDetail detail:list){
					
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
//							AllWeight=(boxes.multiply(info.getTotalweight())).add(leftNum.multiply(info.getGrossweight()));//总重量
//					}else{
						if(info.getGrossweight()!=null){
							AllWeight=unitNum.multiply(info.getGrossweight());
						}
//					}
					
					detail.setBasesaleprice(detail.getPrice());//基准单价(上游单据传入的单价)
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
	public StorageDeliveryEnter getDistributeRejectEnterById(String id) {
		return storageDeliveryEnterMapper.getDeliveryEnterByID(id);
	}

	@Override
	public boolean updataEnterAndDetail(StorageDeliveryEnter storageDeliveryEnter,List<StorageDeliveryEnterDetail> list) throws Exception {
		StorageDeliveryEnter oldEnter=getDistributeRejectEnterById(storageDeliveryEnter.getId());
		if(null==oldEnter ||"3".equals(oldEnter.getStatus()) || "4".equals(oldEnter.getStatus())){
			return false;
		}else if("2".equals(oldEnter.getStatus()) || "6".equals(oldEnter.getStatus())){
			//修改前 采购入库单为保存状态时 回滚在途量
			List<StorageDeliveryEnterDetail> oldList = storageDeliveryEnterMapper.getDeliveryEnterDetailByID(oldEnter.getId());
			for(StorageDeliveryEnterDetail oldDetail : oldList){
				//删除回滚在途量
				rollBackStorageSummaryTransitnum(oldEnter.getStorageid(), oldDetail.getGoodsid(), oldDetail.getUnitnum());
			}
		}
		boolean flag=storageDeliveryEnterMapper.updateDeliveryEnter(storageDeliveryEnter)>0;
		if(flag && list!=null && list.size()>0){
			deleteDetailsById(storageDeliveryEnter.getId());
			for(Object obj:list){
				StorageDeliveryEnterDetail detail=(StorageDeliveryEnterDetail)obj;
				detail.setBillid(storageDeliveryEnter.getId());
                //记录添加时的成本价
                GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                detail.setAddcostprice(getGoodsCostprice(storageDeliveryEnter.getStorageid(),goodsInfo));
				int rs=storageDeliveryEnterMapper.insertStorageDeliveryEnterDetail(detail);
				//插入更新在途量
				updateStorageSummaryTransitnum(storageDeliveryEnter.getStorageid(), detail.getGoodsid(), detail.getUnitnum());
				if(rs<0){
					flag=false;
				}
			}
			
		}
		return flag;
	}
	
	public int deleteDetailsById(String id) {
		return storageDeliveryEnterMapper.deleteDetailsById(id);		
	}



	@Override
	public boolean deleteEnterAndDetailById(String id) throws Exception{
		boolean flag;
		StorageDeliveryEnter storageDeliveryEnter=getDistributeRejectEnterById(id);
		if(storageDeliveryEnter==null) return false;
		if((!"2".equals(storageDeliveryEnter.getStatus()))&&(!"1".equals(storageDeliveryEnter.getStatus()))){
        	return false;
		}
		flag=storageDeliveryEnterMapper.deleteEnterById(id)>0;
		if(flag){
			List<StorageDeliveryEnterDetail> details=storageDeliveryEnterMapper.getDeliveryEnterDetailByID(id);
			for(StorageDeliveryEnterDetail detail:details){
				//删除回滚在途量
				rollBackStorageSummaryTransitnum(storageDeliveryEnter.getStorageid(), detail.getGoodsid(), detail.getUnitnum());
			}
			flag=deleteDetailsById(id)>0;
		}
		return flag;
	}

	@Override
	public boolean updateStorageDeliveryEnter(StorageDeliveryEnter storageDeliveryEnter) {
		return storageDeliveryEnterMapper.updateDeliveryEnter(storageDeliveryEnter)>0;
		
	}

	@Override
	public boolean audit(StorageDeliveryEnter saveEnter,
			StorageDeliveryEnter enter,boolean type) throws Exception {
		boolean flag=false;
		flag=storageDeliveryEnterMapper.updateDeliveryEnter(saveEnter)>0;
		if(flag){
			StorageDeliveryEnter dataEnter=storageDeliveryEnterMapper.getDeliveryEnterByID(saveEnter.getId());
			List<StorageDeliveryEnterDetail> detailList=storageDeliveryEnterMapper.getDeliveryEnterDetailByID(saveEnter.getId());
				//审核时,在途量入库
				for(StorageDeliveryEnterDetail enterDetail:detailList){
					StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
					storageSummaryBatch.setId(enterDetail.getSummarybatchid());
					storageSummaryBatch.setGoodsid(enterDetail.getGoodsid());
					storageSummaryBatch.setStorageid(dataEnter.getStorageid());
					storageSummaryBatch.setStoragelocationid(enterDetail.getSummarybatchid()==null?enterDetail.getSummarybatchid():"");
					storageSummaryBatch.setBatchno(enterDetail.getBatchno());
					storageSummaryBatch.setProduceddate(enterDetail.getProduceddate());
					storageSummaryBatch.setDeadline(enterDetail.getDeadline());
				
//					storageSummaryBatch.setExistingnum(enterDetail.getUnitnum());
//					storageSummaryBatch.setUsablenum(enterDetail.getUnitnum());
//					storageSummaryBatch.setIntinum(enterDetail.getUnitnum());
				
					storageSummaryBatch.setUnitid(enterDetail.getUnitid());
					storageSummaryBatch.setUnitname(enterDetail.getUnitname());
					storageSummaryBatch.setAuxunitid(enterDetail.getAuxunitid());
					storageSummaryBatch.setAuxunitname(enterDetail.getAuxunitname());
					storageSummaryBatch.setPrice(enterDetail.getBasesaleprice());
					storageSummaryBatch.setAmount(enterDetail.getTaxamount());
				
					storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
					String billtype=dataEnter.getBilltype();
					String sourceType="";
					if("1".equals(billtype)){
						sourceType="供应商代配送到货入库,更新现存量";
					}else if("2".equals(billtype)){
						sourceType="客户配送退货入库,更新现存量";
					}
					if(type){
						//在途量入库
						enterStorageSummaryTransitnum(storageSummaryBatch,enterDetail.getUnitnum(), "storageDeliveryEnter", saveEnter.getId(),sourceType);
					}else{
						//直接入库(保存并审核时)
						addStorageSummaryNum(storageSummaryBatch,enterDetail.getUnitnum(), "storageDeliveryEnter", saveEnter.getId(),sourceType);
					}

                    GoodsInfo goodsInfo = getAllGoodsInfoByID(enterDetail.getGoodsid());
                    if(null != goodsInfo){
                        BigDecimal costprice = getGoodsCostprice(dataEnter.getStorageid(),goodsInfo);
                        if(costprice.compareTo(BigDecimal.ZERO)==0){
                            costprice = goodsInfo.getNewstorageprice();
                        }
                        if("1".equals(billtype)) {
                            //更新仓库商品成本价
                            updateStorageGoodsPriceByPurchaseEnter(dataEnter.getStorageid(), enterDetail.getGoodsid(), enterDetail.getUnitnum(), costprice);
                        }
                        //采购入库审核后 入库按成本价入库
                        storageDeliveryEnterMapper.updateDeliverEnterDetailAddcostprice(enterDetail.getId().toString(), enterDetail.getGoodsid(), costprice);
                    }
				}
																			
			//有来源时关闭来源单据
			if(enter.getSourceid()!=null&&!"0".equals(enter.getSourceid())){
				//供应商代配送退货出库通知单
				if(enter.getSourcetype()!=null&&"1".equals(enter.getSourcetype())){
					//供应商代配送到货订单
					flag =deliveryAogorderMapper.closeDeliveryAogorder(enter.getSourceid(),new Date())>0;
				}
				if(enter.getSourcetype()!=null&&"2".equals(enter.getSourcetype())){
					//供应商代配送客户退货通知单
					flag=deliveryRejectbillMapper.closeDeliveryRejectbill(enter.getSourceid(),new Date())>0;
				}
			}
		}
		return flag;
	}
	@Override
	public String getUpCustomerName(String sourceid) {
		String customerName=deliveryRejectbillMapper.getDeliveryRejectbillByID(sourceid).getCustomername();
		if(StringUtils.isNotEmpty(customerName)){
			return customerName;
		}
		return "";
	}
	@Override
	public Map oppauditDeliveryEnter(String id) throws Exception {
		Map resultMap=new HashMap();
		StorageDeliveryEnter enter = storageDeliveryEnterMapper.getDeliveryEnterByID(id);
		boolean flag = false;
		boolean rollBackFlag = true;
		String msg = "";
		if(null==enter){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要反审的数据");
			return resultMap;
		}
		if("1".equals(enter.getIscheck())){
			resultMap.put("flag", false);
			resultMap.put("msg", "代配送出库单验收后不能反审");
			return resultMap;
		}
		if("3".equals(enter.getStatus())){
			List<StorageDeliveryEnterDetail> list = storageDeliveryEnterMapper.getDeliveryEnterDetailByID(id);
			boolean auditflag = true;
            StorageInfo storageInfo = getStorageInfoByID(enter.getStorageid());
            for(StorageDeliveryEnterDetail storageDeliveryEnterDetail : list){
                GoodsInfo goodsInfo = getGoodsInfoByID(storageDeliveryEnterDetail.getGoodsid());
                if(null ==goodsInfo){
                    auditflag = false;
                    msg +="商品："+storageDeliveryEnterDetail.getGoodsid()+"不存在;";
                }
                StorageSummaryBatch storageSummaryBatch = null;
                if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
                    if(StringUtils.isNotEmpty(storageDeliveryEnterDetail.getSummarybatchid())){
                        storageSummaryBatch = getStorageSummaryBatchByID(storageDeliveryEnterDetail.getSummarybatchid());
                    }else{
                        storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(enter.getStorageid(),storageDeliveryEnterDetail.getGoodsid(),storageDeliveryEnterDetail.getProduceddate());
                    }
                }else{
                    if(StringUtils.isNotEmpty(storageDeliveryEnterDetail.getSummarybatchid())){
                        storageSummaryBatch = getStorageSummaryBatchByID(storageDeliveryEnterDetail.getSummarybatchid());
                    }else{
                        storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(enter.getStorageid(), storageDeliveryEnterDetail.getGoodsid());
                    }

                }
                //库存批次现存量
                if(null!=storageSummaryBatch){
                    BigDecimal newusenum = storageSummaryBatch.getUsablenum().subtract(storageDeliveryEnterDetail.getUnitnum());
                    if(newusenum.compareTo(BigDecimal.ZERO)==-1){
                        auditflag = false;
                        if(null!=goodsInfo){
                            msg +="商品："+storageDeliveryEnterDetail.getGoodsid()+goodsInfo.getName()+"在该仓库可用量不足;";
                        }
                    }
                }else{
                    auditflag = false;
                    msg +="商品："+storageDeliveryEnterDetail.getGoodsid()+goodsInfo.getName()+"在该仓库数量不足或者指定批次商品数量不足;";
                }
            }
            if(auditflag) {
                for (StorageDeliveryEnterDetail enterDetail : list) {
                    StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
                    storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
                    storageSummaryBatch.setGoodsid(enterDetail.getGoodsid());
                    storageSummaryBatch.setStorageid(enter.getStorageid());
                    storageSummaryBatch.setStoragelocationid(enterDetail.getSummarybatchid() == null ? enterDetail.getSummarybatchid() : "");
                    storageSummaryBatch.setBatchno(enterDetail.getBatchno());
                    storageSummaryBatch.setProduceddate(enterDetail.getProduceddate());
                    storageSummaryBatch.setDeadline(enterDetail.getDeadline());

                    //				storageSummaryBatch.setExistingnum(enterDetail.getUnitnum());
                    //				storageSummaryBatch.setUsablenum(enterDetail.getUnitnum());
                    //				storageSummaryBatch.setIntinum(enterDetail.getUnitnum());

                    storageSummaryBatch.setUnitid(enterDetail.getUnitid());
                    storageSummaryBatch.setUnitname(enterDetail.getUnitname());
                    storageSummaryBatch.setAuxunitid(enterDetail.getAuxunitid());
                    storageSummaryBatch.setAuxunitname(enterDetail.getAuxunitname());
                    storageSummaryBatch.setPrice(enterDetail.getBasesaleprice());
                    storageSummaryBatch.setAmount(enterDetail.getTaxamount());
                    storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
                    String billtype = enter.getBilltype();
                    String remark = "";
                    if ("1".equals(billtype)) {
                        remark = "供应商代配送到货入库反审,更新现存量";
                    } else if ("2".equals(billtype)) {
                        remark = "客户配送退货入库反审,更新现存量";
                    }

                    rollBackFlag = rollBackEnterStorageSummaryTransitnum(storageSummaryBatch, enterDetail.getUnitnum(), "storageDeliveryEnter", id, remark);
                    //更新仓库成本价
                    if ("1".equals(billtype)) {
                        updateStorageGoodsPriceByPurchaseOut(enter.getStorageid(), enterDetail.getGoodsid(), enterDetail.getUnitnum(), enterDetail.getAddcostprice());
                    }
                }
                if (rollBackFlag) {
                    SysUser sysUser = getSysUser();
                    StorageDeliveryEnter updateEnter = new StorageDeliveryEnter();
                    updateEnter.setStatus("2");
                    updateEnter.setId(id);
                    updateEnter.setAudittime(new Date());
                    updateEnter.setAuditusername(sysUser.getUsername());
                    updateEnter.setAudituserid(sysUser.getUserid());
                    int i = storageDeliveryEnterMapper.updateDeliveryEnter(updateEnter);
                    flag = i > 0;
                    //来源类型为采购退货单
                    if (!flag) {
                        resultMap.put("msg", "代配送入库单反审失败");
                    } else {
                        if ("1".equals(enter.getSourcetype())) {
                            deliveryAogorderMapper.oppDeliveryEnter(enter.getSourceid());
                        } else if ("2".equals(enter.getSourcetype())) {
                            deliveryRejectbillMapper.oppDeliveryEnter(enter.getSourceid());
                        }
                    }
                } else {
                    flag = false;
                    resultMap.put("msg", "代配送入库单回滚失败，不能进行反审操作");
                }
            }else{
                resultMap.put("msg", msg);
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
			StorageDeliveryEnter updateEnter=new StorageDeliveryEnter();
			updateEnter.setIscheck("1"); //检查
			updateEnter.setCheckuserid(sysUser.getUserid());
			updateEnter.setCheckname(sysUser.getName());
			updateEnter.setChecktime(new Date());
			updateEnter.setStatus("4");
//			updateEnter.setBusinessdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			updateEnter.setId(id);
			boolean flag=storageDeliveryEnterMapper.updateDeliveryEnter(updateEnter)>0;
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
	public boolean doEntersForJob() throws Exception {
		boolean flag=true;
		List<StorageDeliveryEnterForJob> enterList=storageDeliveryEnterMapper.getEntersForJob();
		//如果当天没有记录 返回true
		if(enterList==null||enterList.size()==0){
			return true;
		}
		List<StorageDeliveryEnterForJob> enterJobDetails = null;
		for(StorageDeliveryEnterForJob enterJob:enterList){
			if(StringUtils.isEmpty(enterJob.getSupplierid())||StringUtils.isEmpty(enterJob.getStorageid())){
				continue;
			}
            enterJobDetails=storageDeliveryEnterMapper.getEnterDetailForJobWithOutGroup(enterJob.getId());
			boolean tmp=purchaseEnterService.addPurchaseEnterByDeliveryOrder(enterJob,enterJobDetails);
			if(tmp){
                storageDeliveryEnterMapper.updateJobtimes(enterJob.getId());
			}else{
				flag=false;
			}
		}
		return flag;
	}
	
	
	@Override
	public List<StorageDeliveryEnter> printDeliveryEnter(Map map) throws Exception {
		String datasql = getDataAccessRule("t_storage_delivery_enter",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<StorageDeliveryEnter> list=storageDeliveryEnterMapper.showDeliveryEnterByIds(map);
		
		
		for(StorageDeliveryEnter delivery : list){
			
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
				List<StorageDeliveryEnterDetail> detailList=storageDeliveryEnterMapper.getDeliveryEnterDetailByID(delivery.getId());
				if(null!=detailList && detailList.size()>0){
					int seq=1;
					for(StorageDeliveryEnterDetail detail :detailList){
						detail.setUnitnum(detail.getUnitnum().setScale(0));
						if(null!=detail ){
							detail.setSeq(seq++);
							String goodsId=detail.getGoodsid();
							if(StringUtils.isNotEmpty(goodsId)){
								GoodsInfo info=getGoodsInfoByID(goodsId);
								if(info!=null){
									detail.setGoodsname(info.getName());
									detail.setBarcode(info.getBarcode());
									
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
		
		storageDeliveryEnterMapper.updateOrderPrinttimes(id);
	}
	
	
	


}
