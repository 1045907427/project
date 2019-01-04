/**
 * @(#)PurchaseRejectOutServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 19, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.ReturnOrder;
import com.hd.agent.purchase.service.ext.IPurchaseForStorageService;
import com.hd.agent.storage.dao.PurchaseRejectOutMapper;
import com.hd.agent.storage.model.PurchaseRejectOut;
import com.hd.agent.storage.model.PurchaseRejectOutDetail;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IPurchaseRejectOutService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 采购退货出库单service实现类
 * @author chenwei
 */
public class PurchaseRejectOutServiceImpl extends BaseStorageServiceImpl
		implements IPurchaseRejectOutService {
	
	private PurchaseRejectOutMapper purchaseRejectOutMapper;
	
	private IPurchaseForStorageService purchaseForStorageService;
	
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
	public Map addPurchaseRejectOut(PurchaseRejectOut purchaseRejectOut,
			List<PurchaseRejectOutDetail> detailList) throws Exception {
		if (isAutoCreate("t_storage_purchasereject_out")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(purchaseRejectOut, "t_storage_purchasereject_out");
			purchaseRejectOut.setId(id);
		}else{
			purchaseRejectOut.setId("CGCK"+CommonUtils.getDataNumberSendsWithRand());
		} 
		boolean flag = true;
		String msg = "";
		Map map = new HashMap();
		//状态为保存状态时 判断可用量是否足够
		if("2".equals(purchaseRejectOut.getStatus())){
			for(PurchaseRejectOutDetail purchaseRejectOutDetail : detailList){
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
				if(storageSummaryBatch.getUsablenum().compareTo(purchaseRejectOutDetail.getUnitnum())==-1){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
					if("1".equals(goodsInfo.getIsbatch())){
						msg = "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
					}else if("1".equals(goodsInfo.getIsstoragelocation())){
						StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
						msg = "商品:"+goodsInfo.getName()+",在库位:"+storageLocation.getName()+"中,数量不足，该库位只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
					}else{
						StorageInfo storageInfo = getStorageInfoByID(storageSummaryBatch.getStorageid());
						msg = "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量";
					}
					flag = false;
					break;
				}
			}
		}
		if(flag){
			for(PurchaseRejectOutDetail purchaseRejectOutDetail : detailList){
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(purchaseRejectOutDetail.getGoodsid(), purchaseRejectOutDetail.getAuxunitid(), purchaseRejectOutDetail.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					purchaseRejectOutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
				if(null!=storageSummaryBatch){
					//保存状态时，更新待发量
					if("2".equals(purchaseRejectOut.getStatus())){
						//更新待发量
						updateStorageSummaryWaitnum(storageSummaryBatch.getId(), purchaseRejectOutDetail.getUnitnum());
					}
					purchaseRejectOutDetail.setOrderid(purchaseRejectOut.getId());
					GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
					if(null!=goodsInfo){
						purchaseRejectOutDetail.setBrandid(goodsInfo.getBrand());
					}
					purchaseRejectOutMapper.addPurchaseRejectOutDetail(purchaseRejectOutDetail);
				}
			}
			SysUser sysUser = getSysUser();
			purchaseRejectOut.setAdddeptid(sysUser.getDepartmentid());
			purchaseRejectOut.setAdddeptname(sysUser.getDepartmentname());
			purchaseRejectOut.setAdduserid(sysUser.getUserid());
			purchaseRejectOut.setAddusername(sysUser.getName());
			int i = purchaseRejectOutMapper.addPurchaseRejectOut(purchaseRejectOut);
			map.put("flag", i>0);
			map.put("msg", "保存成功");
			map.put("id", purchaseRejectOut.getId());
			return map;
		}else{
			map.put("flag", flag);
			map.put("msg", msg);
			return map;
		}
	}

	@Override
	public Map getPurchaseRejectOut(String id) throws Exception {
		PurchaseRejectOut purchaseRejectOut = purchaseRejectOutMapper.getPurchaseRejectOutByID(id);
		
		BuySupplier buySupplier = getSupplierInfoById(purchaseRejectOut.getSupplierid());
		if(null!=buySupplier){
			purchaseRejectOut.setSuppliername(buySupplier.getName());
		}
		DepartMent departMent = getDepartmentByDeptid(purchaseRejectOut.getBuydeptid());
		if(null!=departMent){
			purchaseRejectOut.setBuydeptname(departMent.getName());
		}
		Personnel personnel = getPersonnelById(purchaseRejectOut.getBuyuserid());
		if(null!=personnel){
			purchaseRejectOut.setBuyusername(personnel.getName());
		}
		Contacter contacter = getContacterById(purchaseRejectOut.getHandlerid());
		if(null!=contacter){
			purchaseRejectOut.setHandlername(contacter.getName());
		}
		StorageInfo billstorageInfo = getStorageInfoByID(purchaseRejectOut.getStorageid());
		if(null!=billstorageInfo){
			purchaseRejectOut.setStoragename(billstorageInfo.getName());
		}
		List<PurchaseRejectOutDetail> list = purchaseRejectOutMapper.getPurchaseRejectOutByOrderid(id);
		for(PurchaseRejectOutDetail purchaseRejectOutDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
			if(goodsInfo!=null){
				goodsInfo.setItemno(getItemnoByGoodsAndStorage(purchaseRejectOutDetail.getGoodsid(),purchaseRejectOutDetail.getStorageid()));
			}
			purchaseRejectOutDetail.setGoodsInfo(goodsInfo);
			StorageInfo storageInfo = getStorageInfoByID(purchaseRejectOutDetail.getStorageid());
			if(null!=storageInfo){
				purchaseRejectOutDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocationByID(purchaseRejectOutDetail.getStoragelocationid());
			if(null!=storageLocation){
				purchaseRejectOutDetail.setStoragelocationname(storageLocation.getName());
			}
			TaxType taxType = getTaxType(purchaseRejectOutDetail.getTaxtype());
			if(null!=taxType){
				purchaseRejectOutDetail.setTaxtypename(taxType.getName());
			}
			//箱价
			if(null!=goodsInfo){
				BigDecimal boxprice = goodsInfo.getBoxnum().multiply(purchaseRejectOutDetail.getTaxprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				BigDecimal noboxprice = goodsInfo.getBoxnum().multiply(purchaseRejectOutDetail.getNotaxprice()).setScale(6,BigDecimal.ROUND_HALF_UP);
				purchaseRejectOutDetail.setBoxprice(boxprice);
				purchaseRejectOutDetail.setNoboxprice(noboxprice);
			}
		}
		Map map = new HashMap();
		map.put("purchaseRejectOut", purchaseRejectOut);
		map.put("detailList", list);
		return map;
	}

	@Override
	public PageData showPurchaseRejectOutList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_purchasereject_out", null);
		pageMap.setDataSql(dataSql);
		List<PurchaseRejectOut> list = purchaseRejectOutMapper.showPurchaseRejectOutList(pageMap);
		for(PurchaseRejectOut purchaseRejectOut : list){
			BuySupplier buySupplier = getSupplierInfoById(purchaseRejectOut.getSupplierid());
			if(null!=buySupplier){
				purchaseRejectOut.setSuppliername(buySupplier.getName());
			}
			Contacter contacter = getContacterById(purchaseRejectOut.getHandlerid());
			if(null!=contacter){
				purchaseRejectOut.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartMentById(purchaseRejectOut.getBuydeptid());
			if(null!=departMent){
				purchaseRejectOut.setBuydeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(purchaseRejectOut.getBuyuserid());
			if(null!=personnel){
				purchaseRejectOut.setBuyusername(personnel.getName());
			}
			StorageInfo storageInfo = getStorageInfoByID(purchaseRejectOut.getStorageid());
			if(null!=storageInfo){
				purchaseRejectOut.setStoragename(storageInfo.getName());
			}
			
			Map amountMap = purchaseRejectOutMapper.getPurchaseRejectOutAmount(purchaseRejectOut.getId());
			if(null!=amountMap){
				BigDecimal taxamount = (BigDecimal) amountMap.get("taxamount");
				BigDecimal notaxamount = (BigDecimal) amountMap.get("notaxamount");
				purchaseRejectOut.setTaxamount(taxamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				purchaseRejectOut.setNotaxamount(notaxamount);
			}
			//来源类型
			SysCode sysCode1 = getBaseSysCodeMapper().getSysCodeInfo(purchaseRejectOut.getSourcetype(), "purchaseRejectOut-sourcetype");
			if(null != sysCode1){
				purchaseRejectOut.setSourcetypename(sysCode1.getCodename());
			}
			//状态
			SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(purchaseRejectOut.getStatus(), "status");
			if(null != sysCode){
				purchaseRejectOut.setStatusname(sysCode.getCodename());
			}
			//打印提示
			if("3".equals(purchaseRejectOut.getStatus()) || "4".equals(purchaseRejectOut.getStatus())){
				purchaseRejectOut.setPrinttip("可打印");
			}
		}
		PageData pageData = new PageData(purchaseRejectOutMapper.showPurchaseRejectOutCount(pageMap),list,pageMap);
		
		//合计采购退货出库单
		List<PurchaseRejectOut> footer = new ArrayList<PurchaseRejectOut>();
		Map map = purchaseRejectOutMapper.getPurchaseRejectOutTotalAmount(pageMap);
		PurchaseRejectOut purchaseRejectOutSum = new PurchaseRejectOut();
		purchaseRejectOutSum.setId("合计");
		if(null!=map){
			BigDecimal taxamount = (BigDecimal) map.get("taxamount");
			BigDecimal notaxamount = (BigDecimal) map.get("notaxamount");
			purchaseRejectOutSum.setTaxamount(taxamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			purchaseRejectOutSum.setNotaxamount(notaxamount);
		}
		else{
			purchaseRejectOutSum.setTaxamount(BigDecimal.ZERO);
			purchaseRejectOutSum.setNotaxamount(BigDecimal.ZERO);
		}
		footer.add(purchaseRejectOutSum);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public Map editPurchaseRejectOut(PurchaseRejectOut purchaseRejectOut,
			List<PurchaseRejectOutDetail> detailList) throws Exception {
		boolean flag = true;
		String msg = "";
		Map map = new HashMap();
		PurchaseRejectOut oldPurchaseRejectOut = purchaseRejectOutMapper.getPurchaseRejectOutByID(purchaseRejectOut.getId());
		if(null==oldPurchaseRejectOut || "3".equals(oldPurchaseRejectOut.getStatus()) || "4".equals(oldPurchaseRejectOut.getStatus())){
			map.put("flag", false);
			map.put("msg", "单据已审核，请刷新后再操作。");
			return map;
		}
		for(PurchaseRejectOutDetail purchaseRejectOutDetail : detailList){
			if(null!=purchaseRejectOutDetail.getId()){
				PurchaseRejectOutDetail oldpurchaseRejectOutDetail = purchaseRejectOutMapper.getPurchaseRejectOutDetailByID(purchaseRejectOutDetail.getId()+"");
				//修改后的数量大于修改前的数量 判断可用量是否足够
				//if(purchaseRejectOutDetail.getUnitnum().compareTo(oldpurchaseRejectOutDetail.getUnitnum())==1){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
					//批次现存量中的可用量是否大于 修改后数量-修改前数量
					if(storageSummaryBatch.getUsablenum().compareTo(purchaseRejectOutDetail.getUnitnum().subtract(oldpurchaseRejectOutDetail.getUnitnum()))==-1){
						GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
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
				//}
			}else{
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
				if(storageSummaryBatch.getUsablenum().compareTo(purchaseRejectOutDetail.getUnitnum())==-1){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
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
			//修改前出库单信息
			if("2".equals(oldPurchaseRejectOut.getStatus())){
				List<PurchaseRejectOutDetail> oldPurchaseRejectOutDetail = purchaseRejectOutMapper.getPurchaseRejectOutByOrderid(purchaseRejectOut.getId());
				for(PurchaseRejectOutDetail purchaseRejectOutDetail : oldPurchaseRejectOutDetail){
					StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
					//回滚修改前的待发量
					rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), purchaseRejectOutDetail.getUnitnum());
				}
			}
			//删除明细
			purchaseRejectOutMapper.deletePurchaseRejectOutDetailByOrderid(purchaseRejectOut.getId());
			//添加明细
			for(PurchaseRejectOutDetail purchaseRejectOutDetail : detailList){
				//计算辅单位数量
				Map auxmap = countGoodsInfoNumber(purchaseRejectOutDetail.getGoodsid(), purchaseRejectOutDetail.getAuxunitid(), purchaseRejectOutDetail.getUnitnum());
				if(auxmap.containsKey("auxnum")){
					purchaseRejectOutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
				}
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
				if(null!=storageSummaryBatch){
					//保存状态时，更新待发量
					if("2".equals(purchaseRejectOut.getStatus()) || "6".equals(purchaseRejectOut.getStatus())){
						//更新待发量
						updateStorageSummaryWaitnum(storageSummaryBatch.getId(), purchaseRejectOutDetail.getUnitnum());
					}
					purchaseRejectOutDetail.setOrderid(purchaseRejectOut.getId());
					GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
					if(null!=goodsInfo){
						purchaseRejectOutDetail.setBrandid(goodsInfo.getBrand());
					}
					purchaseRejectOutMapper.addPurchaseRejectOutDetail(purchaseRejectOutDetail);
				}
			}
			
			SysUser sysUser = getSysUser();
			purchaseRejectOut.setModifyuserid(sysUser.getUserid());
			purchaseRejectOut.setModifyusername(sysUser.getName());
			int i = purchaseRejectOutMapper.editPurchaseRejectOut(purchaseRejectOut);
			map.put("flag", i>0);
			map.put("msg", "保存成功");
			map.put("id", purchaseRejectOut.getId());
			return map;
		}else{
			map.put("flag", flag);
			map.put("msg", msg);
			return map;
		}
	}

	@Override
	public PurchaseRejectOutDetail getPurchaseRejectOutDetailInfo(String id)
			throws Exception {
		PurchaseRejectOutDetail purchaseRejectOutDetail = purchaseRejectOutMapper.getPurchaseRejectOutDetailByID(id);
		return purchaseRejectOutDetail;
	}

	@Override
	public boolean deletePurchaseRejectOut(String id) throws Exception {
		PurchaseRejectOut purchaseRejectOut = purchaseRejectOutMapper.getPurchaseRejectOutByID(id);
		boolean flag = false;
		if(null==purchaseRejectOut){
			return true;
		}else{
			//只有暂存和保存状态才能删除
			if("1".equals(purchaseRejectOut.getStatus()) || "2".equals(purchaseRejectOut.getStatus())){
				//当状态为保存时
				if("2".equals(purchaseRejectOut.getStatus())){
					List<PurchaseRejectOutDetail> detailList = purchaseRejectOutMapper.getPurchaseRejectOutByOrderid(id);
					//删除明细 回滚待发量
					for(PurchaseRejectOutDetail purchaseRejectOutDetail : detailList){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
						if(null!=storageSummaryBatch){
							//回滚待发量
							rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), purchaseRejectOutDetail.getUnitnum());
						}
					}
				}
				purchaseRejectOutMapper.deletePurchaseRejectOutDetailByOrderid(id);
				int i = purchaseRejectOutMapper.deletePurchaseRejectOut(id);
				flag = i>0;
				if(flag && "1".equals(purchaseRejectOut.getSourcetype())){
					purchaseForStorageService.updateReturnOrderUnReferFlag(purchaseRejectOut.getSourceid());
				}
			}
		}
		return flag;
	}

	@Override
	public Map auditPurchaseRejectOut(String id) throws Exception {
		PurchaseRejectOut purchaseRejectOut = purchaseRejectOutMapper.getPurchaseRejectOutByID(id);
		boolean flag = false;
		String isAutoPurchaseReturnCheck = getSysParamValue("IsAutoPurchaseReturnCheck");
		if(null==isAutoPurchaseReturnCheck){
			isAutoPurchaseReturnCheck="1";
		}
		if(null==purchaseRejectOut || !"2".equals(purchaseRejectOut.getStatus())){
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "单据已审核或者不存在。");
			return map;
		}
        StorageInfo storageInfo = getStorageInfoByID(purchaseRejectOut.getStorageid());
        List<PurchaseRejectOutDetail> list = purchaseRejectOutMapper.getPurchaseRejectOutByOrderid(id);
        String msg = "";
        for(PurchaseRejectOutDetail detail : list) {
            PurchaseRejectOutDetail oldpurchaseRejectOutDetail = purchaseRejectOutMapper.getPurchaseRejectOutDetailByID(String.valueOf(detail.getId()));
            //修改后的数量大于修改前的数量 判断可用量是否足够
            StorageSummaryBatch storageSummaryBatch = null;
            if(StringUtils.isNotEmpty(detail.getSummarybatchid())){
                storageSummaryBatch = getStorageSummaryBatchById(detail.getSummarybatchid());
            }else{
                GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
                    storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(purchaseRejectOut.getStorageid(),detail.getGoodsid(),detail.getProduceddate());
                }else{
                    storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(purchaseRejectOut.getStorageid(),detail.getGoodsid());
                }
            }
            //批次现存量中的可用量是否大于 修改后数量-修改前数量
            if(null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(detail.getUnitnum().subtract(oldpurchaseRejectOutDetail.getUnitnum()))==-1){
                GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                //仓库与商品是批次管理
                if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())){
                    msg += "商品:"+goodsInfo.getName()+",在批次:"+storageSummaryBatch.getBatchno()+"中,数量不足，该批次只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
                }else{
                    msg += "商品:"+goodsInfo.getName()+",在仓库:"+storageInfo.getName()+"中,数量不足，该仓库只有"+storageSummaryBatch.getUsablenum().setScale(0)+storageSummaryBatch.getUnitname()+"可用量</br>";
                }
                flag = false;
            }
        }
        if(msg.length() > 0) {
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", msg);
            return map;
        }

        if("2".equals(purchaseRejectOut.getStatus()) || "6".equals(purchaseRejectOut.getStatus())){
			for(PurchaseRejectOutDetail purchaseRejectOutDetail : list){

				StorageSummaryBatch storageSummaryBatch = null;
                if(StringUtils.isNotEmpty(purchaseRejectOutDetail.getSummarybatchid())){
                    storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
                }
				if(null!=storageSummaryBatch){
					//更新现存量信息
					sendStorageSummaryWaitnum(storageSummaryBatch.getId(), purchaseRejectOutDetail.getUnitnum(),"purchaseRejectOut",id);
                    //记录出库时的商品成本价
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseRejectOutDetail.getGoodsid());
                    if(null!=goodsInfo){
                        BigDecimal addcostprice = getGoodsCostprice(purchaseRejectOut.getStorageid(),goodsInfo);
                        purchaseRejectOutDetail.setAddcostprice(addcostprice);
                        purchaseRejectOutMapper.updatePurchaseRejectOutDetailAddcostprice(purchaseRejectOutDetail.getId().toString(),purchaseRejectOutDetail.getGoodsid(),addcostprice);
//                        //调整仓库商品成本价
                        updateStorageGoodsPriceByPurchaseOut(purchaseRejectOut.getStorageid(), purchaseRejectOutDetail.getGoodsid(), purchaseRejectOutDetail.getUnitnum(),addcostprice);
                    }
					//更新商品成本价 最新采购价，验收的时候更改
					if(null!=isAutoPurchaseReturnCheck && "1".equals(isAutoPurchaseReturnCheck.trim())){
                        //代配送不重新计算成本价
                        if(!"2".equals(purchaseRejectOut.getSourcetype())){
                            //计算成本价 判断计算后成本价是否符合上下限条件
                            //符合条件则直接计算 不符合条件则把该部分成本金额差异摊分到下次入库计算
                            boolean isUpdateCostprice = false;
                            //获取计算后的成本价
                            BigDecimal countCostprice = getGoodsPirceByDiffPriceSubtract(purchaseRejectOut.getStorageid(), purchaseRejectOutDetail.getGoodsid(), purchaseRejectOutDetail.getUnitnum(), purchaseRejectOutDetail.getTaxprice(), purchaseRejectOutDetail.getAddcostprice());
                            if (null != goodsInfo) {
                                //上下限比较价格 判断成本价是否在范围内
                                isUpdateCostprice = isPurcahseUpdateCostpriceInLimit(purchaseRejectOut.getStorageid(),goodsInfo,countCostprice,purchaseRejectOutDetail.getTaxprice());
                            }
                            if(isUpdateCostprice){
                                updateGoodsPirceByDiffPriceSubtract(purchaseRejectOut.getId(),purchaseRejectOutDetail.getId().toString(),purchaseRejectOut.getStorageid(), purchaseRejectOutDetail.getGoodsid(), purchaseRejectOutDetail.getUnitnum(), purchaseRejectOutDetail.getTaxprice(), purchaseRejectOutDetail.getAddcostprice(),false,true);
                            }else{
                                updateStoragePriceByDiffPriceSubtract(purchaseRejectOut.getStorageid(), purchaseRejectOutDetail.getGoodsid(), purchaseRejectOutDetail.getUnitnum(), purchaseRejectOutDetail.getTaxprice(), purchaseRejectOutDetail.getAddcostprice());
                                //未计算到成本中的采购入库差额
                                BigDecimal costDiffAmount = purchaseRejectOutDetail.getUnitnum().multiply(purchaseRejectOutDetail.getTaxprice().subtract(purchaseRejectOutDetail.getAddcostprice())).negate();
                                addCostDiffAmountShare("2",purchaseRejectOut.getId(),purchaseRejectOutDetail.getId().toString(),purchaseRejectOut.getStorageid(),purchaseRejectOutDetail.getGoodsid(),costDiffAmount,"审核退货单，产生差额");
                            }
                        }
                    }
				}
			}
			SysUser sysUser = getSysUser();
			String billdate=getAuditBusinessdate(purchaseRejectOut.getBusinessdate());
			int i = purchaseRejectOutMapper.auditPurchaseRejectOut(id, sysUser.getUserid(), sysUser.getName(),billdate);
			flag = i>0;
			//来源类型为采购退货单
			if(flag){
				if("1".equals(purchaseRejectOut.getSourcetype())){
					purchaseForStorageService.updateReturnOrderWriteBack(purchaseRejectOut.getSourceid(), list);
				}
				purchaseForStorageService.updateReturnOrderCkstatusBy(purchaseRejectOut.getSourceid(),"1");
				
				if(null!=isAutoPurchaseReturnCheck && "1".equals(isAutoPurchaseReturnCheck.trim())){
					updateReturnOrderCheck(purchaseRejectOut,sysUser);
				}
			}
		}
        Map map = new HashMap();
        map.put("flag", flag);
        return map;
	}
	@Override
	public Map oppauditPurchaseRejectOut(String id) throws Exception {
		Map resultMap=new HashMap();
		PurchaseRejectOut purchaseRejectOut = purchaseRejectOutMapper.getPurchaseRejectOutByID(id);
		boolean flag = false;
		boolean rollBackFlag = true;
		if(null==purchaseRejectOut){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到要反审的数据");
			return resultMap;
		}
		if("2".equals(purchaseRejectOut.getSourcetype())){
			resultMap.put("flag", false);
			resultMap.put("msg", "来源类型代配送的不能反审");
			return resultMap;
		}
		
		
		if("1".equals(purchaseRejectOut.getIscheck())){
			resultMap.put("flag", false);
			resultMap.put("msg", "采购退货出库单验收后不能反审");
			return resultMap;
		}
		if("3".equals(purchaseRejectOut.getStatus())){
			List<PurchaseRejectOutDetail> list = purchaseRejectOutMapper.getPurchaseRejectOutByOrderid(id);
			for(PurchaseRejectOutDetail purchaseRejectOutDetail : list){
				StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(purchaseRejectOutDetail.getSummarybatchid());
				if(null!=storageSummaryBatch){
					//更新现存量信息
					rollBackFlag= rollBackSendStorageSummaryWaitnum(storageSummaryBatch.getId(), purchaseRejectOutDetail.getUnitnum(),"purchaseRejectOut",id);
					if(rollBackFlag==false){
						break;
					}
//                    //反审 采购入库操作 调整仓库成本价
                    updateStorageGoodsPriceByPurchaseEnter(purchaseRejectOut.getStorageid(),purchaseRejectOutDetail.getGoodsid(),purchaseRejectOutDetail.getUnitnum(),purchaseRejectOutDetail.getAddcostprice());
				}
			}
			if(rollBackFlag){
				SysUser sysUser = getSysUser();
				String billdate=getAuditBusinessdate(purchaseRejectOut.getBusinessdate());
				int i = purchaseRejectOutMapper.oppauditPurchaseRejectOut(id, sysUser.getUserid(), sysUser.getName(),billdate);
				flag = i>0;
				//来源类型为采购退货单
				if(flag){
					if("1".equals(purchaseRejectOut.getSourcetype())){
						purchaseForStorageService.updateReturnOrderOppauditWriteBack(purchaseRejectOut.getSourceid(), list);
					}
					purchaseForStorageService.updateReturnOrderCkstatusBy(purchaseRejectOut.getSourceid(),"0");				
				}else{
                    throw new Exception("采购退货反审失败");
				}
			}else{
				 flag=false;
				 resultMap.put("msg", "采购退货出库回滚失败，不能进行反审操作");
			}
		}
		resultMap.put("flag", flag);
		return resultMap;
	}
	
	private void updateReturnOrderCheck(PurchaseRejectOut purchaseRejectOut,SysUser sysUser) throws Exception{
		PurchaseRejectOut uPurchaseRejectOut=new PurchaseRejectOut();
		String todayString=CommonUtils.getTodayDataStr();
		//审核后单据中业务日期获取方式0不变更日期1是当前系统日期2取操作日期
		String IsAuditBusinessdate = getSysParamValue("IsAuditBusinessdate");
		if("0".equals(IsAuditBusinessdate)){
			uPurchaseRejectOut.setCheckdate(purchaseRejectOut.getBusinessdate());
		}else if("1".equals(IsAuditBusinessdate)){
			uPurchaseRejectOut.setCheckdate(todayString);
			uPurchaseRejectOut.setBusinessdate(todayString);
		}else if("2".equals(IsAuditBusinessdate)){
			uPurchaseRejectOut.setCheckdate(getCurrentDate());
			uPurchaseRejectOut.setBusinessdate(getCurrentDate());
		}
		if(null==sysUser){
			sysUser=getSysUser();
		}
		uPurchaseRejectOut.setIscheck("1"); //检查
		uPurchaseRejectOut.setCheckuserid(sysUser.getUserid());
		uPurchaseRejectOut.setCheckusername(sysUser.getName());
		uPurchaseRejectOut.setId(purchaseRejectOut.getId());
		uPurchaseRejectOut.setStatus("4"); //验收后关闭
		boolean flag=purchaseRejectOutMapper.editPurchaseRejectOut(uPurchaseRejectOut)>0;
		if(flag && StringUtils.isNotEmpty(purchaseRejectOut.getSourceid())){
			ReturnOrder upReturnOrder=new ReturnOrder();
			upReturnOrder.setId(purchaseRejectOut.getSourceid());
			upReturnOrder.setIscheck("1"); //检查
			upReturnOrder.setCheckuserid(sysUser.getUserid());
			upReturnOrder.setCheckusername(sysUser.getName());
			upReturnOrder.setCheckdate(uPurchaseRejectOut.getCheckdate());
			upReturnOrder.setIsinvoice("4");
			purchaseForStorageService.updateReturnOrderCheck(upReturnOrder);
		}
		
	}

	@Override
	public boolean submitPurchaseRejectOutProcess(String id) throws Exception {
		return false;
	}
	@Override
	public List getPurchaseRejectOutListBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_storage_purchasereject_out",null); //数据权限
		map.put("dataSql", sql);
		List<PurchaseRejectOut> list=purchaseRejectOutMapper.getPurchaseRejectOutListBy(map);
		boolean showdetail=false;
		if(null!=map.get("showdetail") &&StringUtils.isNotEmpty(map.get("showdetail").toString()) && "1".equals(map.get("showdetail"))  ){
			showdetail=true;
		}
		for(PurchaseRejectOut item : list){
			BuySupplier buySupplier = getSupplierInfoById(item.getSupplierid());
			if(null!=buySupplier){
				item.setBuySupplierInfo(buySupplier);
				item.setSuppliername(buySupplier.getName());
			}
			Contacter contacter = getContacterById(item.getHandlerid());
			if(null!=contacter){
				item.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartMentById(item.getBuydeptid());
			if(null!=departMent){
				item.setBuydeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(item.getBuyuserid());
			if(null!=personnel){
				item.setBuyusername(personnel.getName());
			}
			StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
			if(null!=storageInfo){
				item.setStoragename(storageInfo.getName());
			}
			
			if(showdetail){
				List<PurchaseRejectOutDetail> billDetailList = purchaseRejectOutMapper.getPurchaseRejectOutByOrderid(item.getId());
				for(PurchaseRejectOutDetail billDetail : billDetailList){
					GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
					billDetail.setGoodsInfo(goodsInfo);
					TaxType taxType = getTaxType(billDetail.getTaxtype());
					if(taxType != null){
						billDetail.setTaxtypename(taxType.getName());
					}
				}
				item.setBillDetailList(billDetailList);
			}
		}
		return list;
	}

	@Override
	public boolean updateVouchertimes(PurchaseRejectOut purchaseRejectOut) throws Exception {
		Map map = new HashMap();
		map.put("rejectOut",purchaseRejectOut);
		map.put("id",purchaseRejectOut.getId());
		int i = purchaseRejectOutMapper.updatePurchaseRejectOutBy(map);
		return i > 0;
	}

	@Override
	public boolean updateOrderPrinttimes(PurchaseRejectOut purchaseRejectOut) throws Exception{
		return purchaseRejectOutMapper.updateOrderPrinttimes(purchaseRejectOut)>0;
	}
	@Override
	public void updateOrderPrinttimes(List<PurchaseRejectOut> list) throws Exception{
		if(null!=list){
			for(PurchaseRejectOut item : list){
				purchaseRejectOutMapper.updateOrderPrinttimes(item);
			}
		}
	}
	
	@Override
	public PurchaseRejectOut getPurchaseRejectOutPureInfo(String id) throws Exception{
		return purchaseRejectOutMapper.getPurchaseRejectOutByID(id);
	}
	@Override
	public List showPurchaseRejectOutIdListBySourceid(String sourceid) throws Exception{
		return purchaseRejectOutMapper.showPurchaseRejectOutIdListBySourceid(sourceid);
	}

    public List getSupplierRejectSumData(List idarr) throws Exception{
        if(null!=idarr && idarr.size()>0){
            List<Map> list = purchaseRejectOutMapper.getSupplierRejectSumData(idarr);
            return list;
        }
        return new ArrayList<Map>();
    }

	public List getSupplierRejectSumDataForThird(List idarr) throws Exception{
		if(null!=idarr && idarr.size()>0){
			List<Map> list = purchaseRejectOutMapper.getSupplierRejectSumDataForThird(idarr);
			return list;
		}
		return new ArrayList<Map>();
	}
}

