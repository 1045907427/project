/**
 * @(#)PurchaseEnterServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 8, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.purchase.model.BuyOrder;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.purchase.service.ext.IPurchaseForStorageService;
import com.hd.agent.storage.dao.PurchaseEnterMapper;
import com.hd.agent.storage.model.PurchaseEnter;
import com.hd.agent.storage.model.PurchaseEnterDetail;
import com.hd.agent.storage.model.StorageDeliveryEnterForJob;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IPurchaseEnterService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 采购入库单service实现类
 * @author chenwei
 */
public class PurchaseEnterServiceImpl extends BaseStorageServiceImpl implements
		IPurchaseEnterService {
	
	protected PurchaseEnterMapper purchaseEnterMapper;
	/**
	 * 采购模块接口
	 */
	private IPurchaseForStorageService purchaseForStorageService;

	public PurchaseEnterMapper getPurchaseEnterMapper() {
		return purchaseEnterMapper;
	}

	public void setPurchaseEnterMapper(PurchaseEnterMapper purchaseEnterMapper) {
		this.purchaseEnterMapper = purchaseEnterMapper;
	}
	
	public IPurchaseForStorageService getPurchaseForStorageService() {
		return purchaseForStorageService;
	}

	public void setPurchaseForStorageService(
			IPurchaseForStorageService purchaseForStorageService) {
		this.purchaseForStorageService = purchaseForStorageService;
	}

    @Override
	public String addPurchaseEnterByBuyOrder(BuyOrder buyOrder,List<BuyOrderDetail> detailList) throws Exception {
		if(null!=buyOrder){
			PurchaseEnter purchaseEnter = new PurchaseEnter();
			if (isAutoCreate("t_storage_purchase_enter")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(purchaseEnter, "t_storage_purchase_enter");
				purchaseEnter.setId(id);
			}else{
				purchaseEnter.setId("CGRUD"+CommonUtils.getDataNumberSendsWithRand());
			} 
			purchaseEnter.setBusinessdate(buyOrder.getBusinessdate());
			purchaseEnter.setStorageid(buyOrder.getStorageid());
			purchaseEnter.setSupplierid(buyOrder.getSupplierid());
			purchaseEnter.setHandlerid(buyOrder.getHandlerid());
			purchaseEnter.setBuydeptid(buyOrder.getBuydeptid());
			purchaseEnter.setBuyuserid(buyOrder.getBuyuserid());
			purchaseEnter.setSettletype(buyOrder.getSettletype());
			purchaseEnter.setPaytype(buyOrder.getPaytype());
			purchaseEnter.setSourcetype("1");
			purchaseEnter.setSourceid(buyOrder.getId());
			purchaseEnter.setIsrefer("0");
			purchaseEnter.setStatus("2");
			
			purchaseEnter.setField01(buyOrder.getField01());
			purchaseEnter.setField02(buyOrder.getField02());
			purchaseEnter.setField03(buyOrder.getField03());
			purchaseEnter.setField04(buyOrder.getField04());
			purchaseEnter.setField05(buyOrder.getField05());
			purchaseEnter.setField06(buyOrder.getField06());
			purchaseEnter.setField07(buyOrder.getField07());
			purchaseEnter.setField08(buyOrder.getField08());
			
			purchaseEnter.setAdddeptid(buyOrder.getAdddeptid());
			purchaseEnter.setAdddeptname(buyOrder.getAdddeptname());
			purchaseEnter.setAdduserid(buyOrder.getAdduserid());
			purchaseEnter.setAddusername(buyOrder.getAddusername());
			purchaseEnter.setRemark(buyOrder.getRemark());
			
			for(BuyOrderDetail buyOrderDetail : detailList){
				//添加采购入库明细
				addPurchaseEnterDetail(buyOrderDetail, purchaseEnter);
			}
			
			int i = purchaseEnterMapper.addPurchaseEnter(purchaseEnter);
			if(i>0){
				if("1".equals(purchaseEnter.getSourcetype())){
					//调用采购订单回写接口
					List purchaseEnterSumList = purchaseEnterMapper.getPurchaseEnterDetailSumGoodsidList(purchaseEnter.getSourceid());
					purchaseForStorageService.updateBuyOrderDetailWriteBack(purchaseEnter.getSourceid(), purchaseEnterSumList);
				}
				return purchaseEnter.getId();
			}
		}
		return null;
	}
	/**
	 * 添加采购入库单明细，并且更新商品仓库现存量的在途量
	 * @param buyOrderDetail
	 * @param purchaseEnter
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 10, 2013
	 */
	public boolean addPurchaseEnterDetail(BuyOrderDetail buyOrderDetail,PurchaseEnter purchaseEnter) throws Exception{
		PurchaseEnterDetail purchaseEnterDetail = new PurchaseEnterDetail();
		purchaseEnterDetail.setStorageid(purchaseEnter.getStorageid());
		purchaseEnterDetail.setBuyorderdetailid(buyOrderDetail.getId()+"");
		purchaseEnterDetail.setBuyorderid(purchaseEnter.getSourceid());
		purchaseEnterDetail.setPurchaseenterid(purchaseEnter.getId());
		purchaseEnterDetail.setGoodsid(buyOrderDetail.getGoodsid());
		GoodsInfo goodsInfo = getAllGoodsInfoByID(buyOrderDetail.getGoodsid());
		if(null!=goodsInfo){
			purchaseEnterDetail.setBrandid(goodsInfo.getBrand());
			purchaseEnterDetail.setBarcode(goodsInfo.getBarcode());
		}
		purchaseEnterDetail.setUnitid(buyOrderDetail.getUnitid());
		purchaseEnterDetail.setUnitname(buyOrderDetail.getUnitname());
		//未入库数量
		purchaseEnterDetail.setUnitnum(buyOrderDetail.getUnstockunitnum());
		purchaseEnterDetail.setAuxunitid(buyOrderDetail.getAuxunitid());
		purchaseEnterDetail.setAuxunitname(buyOrderDetail.getAuxunitname());
		Map auxMap = countGoodsInfoNumber(buyOrderDetail.getGoodsid(), buyOrderDetail.getAuxunitid(), buyOrderDetail.getUnstockunitnum());
		String auxnumdetail = (String) auxMap.get("auxnumdetail");
		purchaseEnterDetail.setAuxnumdetail(auxnumdetail);
		BigDecimal auxnum = new BigDecimal((String)auxMap.get("auxInteger"));
		BigDecimal auxremainder = new BigDecimal((String)auxMap.get("auxremainder"));
		purchaseEnterDetail.setTotalbox((BigDecimal) auxMap.get("auxnum"));
        //订单初始数量
        purchaseEnterDetail.setInitnum(buyOrderDetail.getUnitnum());
		purchaseEnterDetail.setAuxnum(auxnum);
		purchaseEnterDetail.setAuxremainder(auxremainder);
		
		purchaseEnterDetail.setTaxprice(buyOrderDetail.getTaxprice());
		purchaseEnterDetail.setTaxamount(buyOrderDetail.getUnstocktaxamount().setScale(decimalLen, BigDecimal.ROUND_HALF_UP) );
		purchaseEnterDetail.setInitprice(buyOrderDetail.getTaxprice());
		
		BigDecimal notaxamount = getNotaxAmountByTaxAmount(buyOrderDetail.getUnstocktaxamount(), goodsInfo.getDefaulttaxtype());
		purchaseEnterDetail.setNotaxprice(buyOrderDetail.getNotaxprice());
		purchaseEnterDetail.setNotaxamount(notaxamount);
		purchaseEnterDetail.setTax(purchaseEnterDetail.getTaxamount().subtract(purchaseEnterDetail.getNotaxamount()));
		purchaseEnterDetail.setTaxtype(buyOrderDetail.getTaxtype());
		
		purchaseEnterDetail.setRemark(buyOrderDetail.getRemark());
		if(null!=buyOrderDetail.getUnstockunitnum()){
			purchaseEnterDetail.setField01(buyOrderDetail.getUnstockunitnum().intValue()+"");
			Map unsmap = countGoodsInfoNumber(buyOrderDetail.getGoodsid(), buyOrderDetail.getAuxunitid(), buyOrderDetail.getUnstockunitnum());
			String unsnumdetail = (String) unsmap.get("auxnumdetail");
			purchaseEnterDetail.setField02(unsnumdetail);
		}
		purchaseEnterDetail.setField03(buyOrderDetail.getField03());
		purchaseEnterDetail.setField04(buyOrderDetail.getField04());
		purchaseEnterDetail.setField05(buyOrderDetail.getField05());
		purchaseEnterDetail.setField06(buyOrderDetail.getField06());
		purchaseEnterDetail.setField07(buyOrderDetail.getField07());
		purchaseEnterDetail.setField08(buyOrderDetail.getField08());
		
		if(null!=purchaseEnter.getStorageid() && !"".equals(purchaseEnter.getStorageid())){
			purchaseEnterDetail.setStorageid(purchaseEnter.getStorageid());
		}else{
			if(null!=goodsInfo){
				purchaseEnterDetail.setStorageid(goodsInfo.getStorageid());
			}
		}
		//获取商品在仓库中的现存量信息
		updateStorageSummaryTransitnum(purchaseEnterDetail.getStorageid(), buyOrderDetail.getGoodsid(), buyOrderDetail.getUnstockunitnum());
		
		int i  = purchaseEnterMapper.addPurchaseEnterDetail(purchaseEnterDetail);
		return i>0;
	}

	@Override
	public GoodsInfo getGoodsInfo(String goodsid) throws Exception {
		GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
		return goodsInfo;
	}

	@Override
	public boolean addPurchaseEnter(PurchaseEnter purchaseEnter, List<PurchaseEnterDetail> detailList)
			throws Exception {
		if (isAutoCreate("t_storage_purchase_enter")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(purchaseEnter, "t_storage_purchase_enter");
			purchaseEnter.setId(id);
		}else{
			purchaseEnter.setId("CGRUD"+CommonUtils.getDataNumberSendsWithRand());
		} 
		//添加采购入库单明细
		for(PurchaseEnterDetail purchaseEnterDetail:detailList){
			//计算辅单位数量
			Map auxmap = countGoodsInfoNumber(purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getAuxunitid(), purchaseEnterDetail.getUnitnum());
			if(auxmap.containsKey("auxnum")){
				purchaseEnterDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
			}
			
			purchaseEnterDetail.setPurchaseenterid(purchaseEnter.getId());
			purchaseEnterDetail.setInitprice(purchaseEnterDetail.getTaxprice());
			GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseEnterDetail.getGoodsid());
			if(null!=goodsInfo){
				purchaseEnterDetail.setBrandid(goodsInfo.getBrand());
			}
			purchaseEnterDetail.setStorageid(purchaseEnter.getStorageid());
			//保存状态时，更新在途量
			if("2".equals(purchaseEnter.getStatus())){
				//获取商品在仓库中的现存量信息
				updateStorageSummaryTransitnum(purchaseEnterDetail.getStorageid(), purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getUnitnum());
			}
			purchaseEnterMapper.addPurchaseEnterDetail(purchaseEnterDetail);
		}
		//添加采购入库单
		SysUser sysUser = getSysUser();
		purchaseEnter.setAdddeptid(sysUser.getDepartmentid());
		purchaseEnter.setAdddeptname(sysUser.getDepartmentname());
		purchaseEnter.setAdduserid(sysUser.getUserid());
		purchaseEnter.setAddusername(sysUser.getName());
		int i = purchaseEnterMapper.addPurchaseEnter(purchaseEnter);
		if("1".equals(purchaseEnter.getSourcetype())){
			//调用采购订单回写接口
			List purchaseEnterSumList = purchaseEnterMapper.getPurchaseEnterDetailSumGoodsidList(purchaseEnter.getSourceid());
			purchaseForStorageService.updateBuyOrderDetailWriteBack(purchaseEnter.getSourceid(), purchaseEnterSumList);
		}
		return i>0;
	}

	@Override
	public Map getPurchaseEnterInfo(String id) throws Exception {
		PurchaseEnter purchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(id);
		BuySupplier buySupplier = getSupplierInfoById(purchaseEnter.getSupplierid());
		if(null!=buySupplier){
			purchaseEnter.setSuppliername(buySupplier.getName());
		}
		DepartMent departMent = getDepartmentByDeptid(purchaseEnter.getBuydeptid());
		if(null!=departMent){
			purchaseEnter.setBuydeptname(departMent.getName());
		}
		Personnel personnel = getPersonnelById(purchaseEnter.getBuyuserid());
		if(null!=personnel){
			purchaseEnter.setBuyusername(personnel.getName());
		}
		StorageInfo billstorageInfo = getStorageInfoByID(purchaseEnter.getStorageid());
		if(null!=billstorageInfo){
			purchaseEnter.setStoragename(billstorageInfo.getName());
		}
		List<PurchaseEnterDetail> list = purchaseEnterMapper.getPurchaseEnterDetailList(id);
		for(PurchaseEnterDetail purchaseEnterDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(purchaseEnterDetail.getGoodsid());
			if(goodsInfo!=null){
				goodsInfo.setItemno(getItemnoByGoodsAndStorage(purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getStorageid()));
			}
			purchaseEnterDetail.setGoodsInfo(goodsInfo);
			StorageInfo storageInfo = getStorageInfoByID(purchaseEnterDetail.getStorageid());
			if(null!=storageInfo){
				purchaseEnterDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(purchaseEnterDetail.getStoragelocationid());
			if(null!=storageLocation){
				purchaseEnterDetail.setStoragelocationname(storageLocation.getName());
			}
			TaxType taxType = getTaxType(purchaseEnterDetail.getTaxtype());
			if(null!=taxType){
				purchaseEnterDetail.setTaxtypename(taxType.getName());
			}
			if(null!=goodsInfo){
				purchaseEnterDetail.setBoxprice(goodsInfo.getBoxnum().multiply(purchaseEnterDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				purchaseEnterDetail.setNoboxprice(goodsInfo.getBoxnum().multiply(purchaseEnterDetail.getNotaxprice()).setScale(6, BigDecimal.ROUND_HALF_UP));
			}
		}
		Map map = new HashMap();
		map.put("purchaseEnter", purchaseEnter);
		map.put("detailList", list);
		return map;
	}

	@Override
	public PageData showPurchaseEnterList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_purchase_enter", "t");
		pageMap.setDataSql(dataSql);
        BigDecimal totalMoney  = BigDecimal.ZERO;
		List<PurchaseEnter> list = purchaseEnterMapper.showPurchaseEnterList(pageMap);
		//编号转名称
		for(PurchaseEnter purchaseEnter : list){
            BigDecimal money = BigDecimal.ZERO;
            List<PurchaseEnterDetail> details = purchaseEnterMapper.getPurchaseEnterDetailList(purchaseEnter.getId());
            for(PurchaseEnterDetail detail : details){
                if(null != detail.getTaxamount() && detail.getTaxamount().compareTo(BigDecimal.ZERO) > 0 ){
                    money = money.add(detail.getTaxamount());
                }
            }
            purchaseEnter.setMoney(money);
            totalMoney = totalMoney.add(money);
			StorageInfo storageInfo = getStorageInfoByID(purchaseEnter.getStorageid());
			if(null!=storageInfo){
				purchaseEnter.setStoragename(storageInfo.getName());
			}
			BuySupplier buySupplier = getSupplierInfoById(purchaseEnter.getSupplierid());
			if(null!=buySupplier){
				purchaseEnter.setSuppliername(buySupplier.getName());
			}
			DepartMent departMent = getDepartMentById(purchaseEnter.getBuydeptid());
			if(null!=departMent){
				purchaseEnter.setBuydeptname(departMent.getName());
			}
			Personnel personnel = getPersonnelById(purchaseEnter.getBuyuserid());
			if(null!=personnel){
				purchaseEnter.setBuyusername(personnel.getName());
			}
			Contacter contacter = getContacterById(purchaseEnter.getHandlerid());
			if(null!=contacter){
				purchaseEnter.setHandlername(contacter.getName());
			}
		}
		PageData pageData = new PageData(purchaseEnterMapper.showPurchaseEnterCount(pageMap),list,pageMap);
        //合计采购入库单金额
        List<PurchaseEnter> footer = new ArrayList();
        PurchaseEnter purchaseEnter = new PurchaseEnter();
        purchaseEnter.setId("合计");
        purchaseEnter.setMoney(totalMoney);
        footer.add(purchaseEnter);
        pageData.setFooter(footer);

		return pageData;
	}

	@Override
	public boolean editPurchaseEnter(PurchaseEnter purchaseEnter,List<PurchaseEnterDetail> detailList) throws Exception {
		//获取修改前采购入库单信息
		PurchaseEnter oldPurchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(purchaseEnter.getId());
		if(null==oldPurchaseEnter ||"3".equals(oldPurchaseEnter.getStatus()) || "4".equals(oldPurchaseEnter.getStatus())){
			return false;
		}
		//修改前 采购入库单为暂存状态时
		if("1".equals(oldPurchaseEnter.getStatus())){
			//删除采购入库单明细
			purchaseEnterMapper.deletePurchaseEnterDetail(purchaseEnter.getId());
		}else if("2".equals(oldPurchaseEnter.getStatus()) || "6".equals(oldPurchaseEnter.getStatus())){
			//修改前 采购入库单为保存状态时 回滚在途量
			List<PurchaseEnterDetail> oldList = purchaseEnterMapper.getPurchaseEnterDetailList(purchaseEnter.getId());
			for(PurchaseEnterDetail purchaseEnterDetail : oldList){
				//回滚在途量
				rollBackStorageSummaryTransitnum(oldPurchaseEnter.getStorageid(), purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getUnitnum());
			}
			//删除采购入库单明细
			purchaseEnterMapper.deletePurchaseEnterDetail(purchaseEnter.getId());
		}
		
		//添加采购入库单明细
		for(PurchaseEnterDetail purchaseEnterDetail:detailList){
			//计算辅单位数量
			Map auxmap = countGoodsInfoNumber(purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getAuxunitid(), purchaseEnterDetail.getUnitnum());
			if(auxmap.containsKey("auxnum")){
				purchaseEnterDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
			}
			
			purchaseEnterDetail.setPurchaseenterid(purchaseEnter.getId());
			purchaseEnterDetail.setStorageid(purchaseEnter.getStorageid());
			GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseEnterDetail.getGoodsid());
			if(null!=goodsInfo){
				purchaseEnterDetail.setBrandid(goodsInfo.getBrand());
			}
			if("2".equals(purchaseEnter.getStatus())){
				//获取商品在仓库中的现存量信息
				//更新在途量
				updateStorageSummaryTransitnum(purchaseEnter.getStorageid(), purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getUnitnum());
			}
			purchaseEnterMapper.addPurchaseEnterDetail(purchaseEnterDetail);
		}
		//添加采购入库单
		SysUser sysUser = getSysUser();
		purchaseEnter.setModifyuserid(sysUser.getUserid());
		purchaseEnter.setModifyusername(sysUser.getName());
		int i = purchaseEnterMapper.updatePurchaseEnter(purchaseEnter);
		if("1".equals(purchaseEnter.getSourcetype())){
			//调用采购订单回写接口
			List purchaseEnterSumList = purchaseEnterMapper.getPurchaseEnterDetailSumGoodsidList(purchaseEnter.getSourceid());
			purchaseForStorageService.updateBuyOrderDetailWriteBack(purchaseEnter.getSourceid(), purchaseEnterSumList);
		}
		return i>0;
	}

	@Override
	public boolean deletePurchaseEnter(String id) throws Exception {
		boolean flag = false;
		PurchaseEnter purchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(id);
		//只有暂存与保存状态才能删除
		if("1".equals(purchaseEnter.getStatus())){
			purchaseEnterMapper.deletePurchaseEnterDetail(id);
			int i = purchaseEnterMapper.deletePurchaseEnter(id);
			flag = i>0;
		}else if("2".equals(purchaseEnter.getStatus())){
			//保存状态的采购入库单 删除前需要回滚在途量
			//修改前 采购入库单为保存状态时 回滚在途量
			List<PurchaseEnterDetail> purchaseEnterDetailList = purchaseEnterMapper.getPurchaseEnterDetailList(id);
			for(PurchaseEnterDetail purchaseEnterDetail : purchaseEnterDetailList){
				//回滚在途量
				rollBackStorageSummaryTransitnum(purchaseEnter.getStorageid(), purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getUnitnum());
			}
			//有上游单据时
			if("1".equals(purchaseEnter.getSourcetype())){
				purchaseForStorageService.updateBuyOrderDetailDeleteWriteBack(purchaseEnter.getSourceid(), purchaseEnterDetailList);
			}
			purchaseEnterMapper.deletePurchaseEnterDetail(id);
			int i = purchaseEnterMapper.deletePurchaseEnter(id);
			flag = i>0;
		}
		return flag;
	}

	@Override
	public Map auditPurchaseEnter(String id) throws Exception {
		PurchaseEnter purchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(id);
		Map map = new HashMap();
		boolean flag = false;
		String msg = "";
		String downid = "";
		String today = CommonUtils.getTodayDataStr();
		if("2".equals(purchaseEnter.getStatus()) || "6".equals(purchaseEnter.getStatus())){  //保存状态才能审核
			List<PurchaseEnterDetail> detailList = purchaseEnterMapper.getPurchaseEnterDetailList(id);
			boolean auditFlag = true;
			//判断采购入库单是否可以审核
			for(PurchaseEnterDetail purchaseEnterDetail:detailList){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseEnterDetail.getGoodsid());
				if("1".equals(goodsInfo.getIsbatch())){
					if(null==purchaseEnterDetail.getBatchno() || "".equals(purchaseEnterDetail.getBatchno())){
						auditFlag = false;
						msg += "商品:"+goodsInfo.getId()+goodsInfo.getName()+"未完善批次号\n";
					}
				}else{
					if(null==purchaseEnterDetail.getStorageid() || "".equals(purchaseEnterDetail.getStorageid())){
						auditFlag = false;
						msg += "商品:"+goodsInfo.getId()+goodsInfo.getName()+"未指定入库仓库\n";
					}
				}
			}
			if(auditFlag){
				for(PurchaseEnterDetail purchaseEnterDetail:detailList){
					StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
					storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
					storageSummaryBatch.setGoodsid(purchaseEnterDetail.getGoodsid());
					storageSummaryBatch.setStorageid(purchaseEnterDetail.getStorageid());
					storageSummaryBatch.setStoragelocationid(purchaseEnterDetail.getStoragelocationid());
					storageSummaryBatch.setBarcode(purchaseEnterDetail.getBarcode());
					storageSummaryBatch.setBatchno(purchaseEnterDetail.getBatchno());
					storageSummaryBatch.setProduceddate(purchaseEnterDetail.getProduceddate());
					storageSummaryBatch.setDeadline(purchaseEnterDetail.getDeadline());
					
					storageSummaryBatch.setUnitid(purchaseEnterDetail.getUnitid());
					storageSummaryBatch.setUnitname(purchaseEnterDetail.getUnitname());
					storageSummaryBatch.setAuxunitid(purchaseEnterDetail.getAuxunitid());
					storageSummaryBatch.setAuxunitname(purchaseEnterDetail.getAuxunitname());
					storageSummaryBatch.setPrice(purchaseEnterDetail.getTaxprice());
					storageSummaryBatch.setAmount(purchaseEnterDetail.getTaxamount());
					
					storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
					
					enterStorageSummaryTransitnum(storageSummaryBatch,purchaseEnterDetail.getUnitnum(), "purchaseEnter", id, "采购入库单审核通过，更新现存量");

                    GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseEnterDetail.getGoodsid());
                    if(null!=goodsInfo){
                        //当前商品总的成本价
                        BigDecimal nowCostprice = goodsInfo.getNewstorageprice();
                        //上下限比较价格 取本次采购的价格 如果本次价格为0取最新采购价
                        BigDecimal newBuyprice = purchaseEnterDetail.getTaxprice();
                        //采购入库时，商品按什么价格入库
                        //1：按订单采购价先入库
                        //2：按当前成本价先入库
                        String purchaseEnterCostpriceType = getSysParamValue("PurchaseEnterCostpriceType");
                        if(StringUtils.isEmpty(purchaseEnterCostpriceType)){
                            purchaseEnterCostpriceType = "1";
                        }
                        BigDecimal addCostprice = newBuyprice;
                        if("1".equals(purchaseEnterCostpriceType)){
                            addCostprice = purchaseEnterDetail.getTaxprice();
                        }else if("2".equals(purchaseEnterCostpriceType)){
                            addCostprice = nowCostprice;
                        }
                        boolean isCountCostPrice = false;
						//入库后成本价
						BigDecimal preCostprice =  getGoodsCostpriceByPreMarginAdd(purchaseEnterDetail.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),addCostprice,true);
						isCountCostPrice = isPurcahseUpdateCostpriceInLimit(purchaseEnterDetail.getStorageid(),goodsInfo,preCostprice,purchaseEnterDetail.getTaxprice());
                        if(isCountCostPrice){
                            //更新最新档案库存价
                            boolean updateFlag = updateGoodsPriceByAdd(purchaseEnterDetail.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),addCostprice, true, false,true,purchaseEnter.getId(),purchaseEnterDetail.getId()+"");
                            if(updateFlag){
                                nowCostprice =  addCostprice;
                            }
                        }else{
                            //更新仓库商品成本价
                            updateStorageGoodsPriceByPurchaseEnter(purchaseEnterDetail.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),nowCostprice);
                        }
                        purchaseEnterDetail.setAddcostprice(nowCostprice);
                        //采购入库审核后 入库按成本价入库
                        purchaseEnterMapper.updatePurchaseEnterDetailAddcostprice(purchaseEnterDetail.getId().toString(),purchaseEnterDetail.getGoodsid(),nowCostprice);
                    }else{
                        purchaseEnterDetail.setAddcostprice(purchaseEnterDetail.getTaxprice());
                    }
				}
				SysUser sysUser = getSysUser();
				String billdate=getAuditBusinessdate(purchaseEnter.getBusinessdate());
				int i = purchaseEnterMapper.auditPurchaseEnter(id, sysUser.getUserid(), sysUser.getName(),billdate);
				purchaseEnter.setBusinessdate(billdate);

				flag = i>0;
				//生成下游单据
				//if(flag && "1".equals(purchaseEnter.getSourcetype())){
				if(flag){
					if("1".equals(purchaseEnter.getSourcetype())){
						//调用采购订单回写接口
						List purchaseEnterSumList = purchaseEnterMapper.getPurchaseEnterDetailSumAuditList(purchaseEnter.getSourceid());
						purchaseForStorageService.updateBuyOrderDetailAuditWriteBack(purchaseEnter.getSourceid(), purchaseEnterSumList);
					}
					if(detailList.size()>0){
                        //注该方法自生成进货单
						downid = purchaseForStorageService.addArrivalOrder(purchaseEnter, detailList);
						if(StringUtils.isNotEmpty(downid)){
                            //判断进货单是否自动审核问题
							String arrivalOrderAutoAudit= getSysParamValue("ArrivalOrderAutoAuditByPurchaseEnter");
							if(StringUtils.isEmpty(arrivalOrderAutoAudit)){
								arrivalOrderAutoAudit="1";
							}
							arrivalOrderAutoAudit=arrivalOrderAutoAudit.trim();
							if("2".equals(arrivalOrderAutoAudit)){
                                purchaseForStorageService.auditArrivalOrder(downid);
							}
							purchaseEnterMapper.updatePurchaseEnterRefer("1", id);
						}
					}
					//有来源单据的 需要判断是否追加
					if("1".equals(purchaseEnter.getSourcetype())){
						//判断采购订单是否可以继续追加生成采购入库单
						//能继续追加的话 则生成采购入库单
						boolean goAddFLag = purchaseForStorageService.getCanBuyOrderCreateNextAfterEnterAudit(purchaseEnter.getSourceid());
						if(goAddFLag){
							Map returnMap= addPurchaseEnterByRefer(purchaseEnter.getSourceid());
							map.put("purchaseEnterId", returnMap.get("id"));
						}
					}
				}else{
					throw new Exception("采购入库单审核出错 单据编号:"+id);
				}
			}
		}else{
			msg = "保存状态才能进行审核操作!";
		}
		map.put("flag", flag);
		map.put("msg", msg);
		map.put("downid", downid);
		return map;
	}

	@Override
	public Map oppauditPurchaseEnter(String id) throws Exception {
		PurchaseEnter purchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(id);
		Map map = new HashMap();
		boolean flag = false;
		if("3".equals(purchaseEnter.getStatus())){
			//判断是否可以反审
			boolean oppauditFlag = purchaseForStorageService.deleteArrivalOrderAndDetailByBillno(id);
			if(oppauditFlag){
				List<PurchaseEnterDetail> detailList = purchaseEnterMapper.getPurchaseEnterDetailList(id);
				boolean rollBackFlag = true;
				for(PurchaseEnterDetail purchaseEnterDetail:detailList){
					StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
					storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
					storageSummaryBatch.setGoodsid(purchaseEnterDetail.getGoodsid());
					storageSummaryBatch.setStorageid(purchaseEnterDetail.getStorageid());
					storageSummaryBatch.setStoragelocationid(purchaseEnterDetail.getStoragelocationid());
					storageSummaryBatch.setBatchno(purchaseEnterDetail.getBatchno());
					storageSummaryBatch.setProduceddate(purchaseEnterDetail.getProduceddate());
					storageSummaryBatch.setDeadline(purchaseEnterDetail.getDeadline());
					
					storageSummaryBatch.setUnitid(purchaseEnterDetail.getUnitid());
					storageSummaryBatch.setUnitname(purchaseEnterDetail.getUnitname());
					storageSummaryBatch.setAuxunitid(purchaseEnterDetail.getAuxunitid());
					storageSummaryBatch.setAuxunitname(purchaseEnterDetail.getAuxunitname());
					storageSummaryBatch.setPrice(purchaseEnterDetail.getTaxprice());
					storageSummaryBatch.setAmount(purchaseEnterDetail.getTaxamount());
					
					storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
					
					boolean rollFlag = rollBackEnterStorageSummaryTransitnum(storageSummaryBatch,purchaseEnterDetail.getUnitnum(), "purchaseEnter", id, "采购入库单反审通过，更新现存量");
					if(rollFlag==false){
						rollBackFlag = false;
						break;
					}
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseEnterDetail.getGoodsid());
                    boolean isUpdateCostprice = true;
                    if (null != goodsInfo) {
                        //获取计算后的成本价
                        BigDecimal countCostprice = getGoodsPirceByDiffPriceSubtract(purchaseEnterDetail.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),purchaseEnterDetail.getAddcostprice(),goodsInfo.getNewstorageprice());
                        //上下限比较价格 取本次采购的价格 如果本次价格为0取最新采购价
                        BigDecimal newBuyprice = purchaseEnterDetail.getTaxprice();
                        if(newBuyprice.compareTo(BigDecimal.ZERO)!=1){
                            newBuyprice = goodsInfo.getNewbuyprice();
                        }
                        isUpdateCostprice = isPurcahseUpdateCostpriceInLimit(purchaseEnterDetail.getStorageid(),goodsInfo,countCostprice,newBuyprice);
                    }
                    if(isUpdateCostprice){
                        //更新最新档案库存价
                        updateGoodsPriceBySubtract(purchaseEnter.getId(),purchaseEnterDetail.getId()+"",purchaseEnterDetail.getStorageid(),
                                purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),
                                purchaseEnterDetail.getAddcostprice(),true);
                    }else{
                        updateStorageGoodsPriceByPurchaseOut(purchaseEnterDetail.getStorageid(),purchaseEnterDetail.getGoodsid(),purchaseEnterDetail.getUnitnum(),purchaseEnterDetail.getAddcostprice());
                        //未计算到成本中的采购入库差额
                        BigDecimal costDiffAmount = purchaseEnterDetail.getUnitnum().multiply(purchaseEnterDetail.getAddcostprice().subtract(goodsInfo.getNewstorageprice())).negate();
                        addCostDiffAmountShare("3",purchaseEnter.getId(),purchaseEnterDetail.getId().toString(),purchaseEnter.getStorageid(),purchaseEnterDetail.getGoodsid(),costDiffAmount,"反审采购入库单造成差额");
                    }


				}
				if(rollBackFlag){
					SysUser sysUser = getSysUser();
					int i = purchaseEnterMapper.oppauditPurchaseEnter(id, sysUser.getUserid(), sysUser.getName());
					flag = i>0;
					if(flag){
						if("1".equals(purchaseEnter.getSourcetype())){
							//调用采购订单回写接口
							List purchaseEnterSumList = purchaseEnterMapper.getPurchaseEnterDetailSumAuditList(purchaseEnter.getSourceid());
							boolean writeBackflag = purchaseForStorageService.updateBuyOrderDetailAuditWriteBack(purchaseEnter.getSourceid(), purchaseEnterSumList);
							if(writeBackflag){
								purchaseForStorageService.updateBuyOrderOpen(purchaseEnter.getSourceid());
								purchaseEnterMapper.updatePurchaseEnterRefer("0", id);
							}
						}else{
							purchaseEnterMapper.updatePurchaseEnterRefer("0", id);
						}
					}
				}else{
					throw new RuntimeException("采购入库回滚失败，不能进行反审操作");
				}
			}
		}
		map.put("flag", flag);
		return map;
	}
	@Override
	public Map addPurchaseEnterByRefer(String dispatchbillid) throws Exception {
		BuyOrder buyOrder = purchaseForStorageService.showBuyOrderAndDetail(dispatchbillid);
		String id = addPurchaseEnterByBuyOrder(buyOrder, buyOrder.getBuyOrderDetailList());
		Map map = new HashMap();
		map.put("id", id);
		if(id!=null){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		return map;
	}

	@Override
	public Map submitPurchaseEnterProcess(String id) throws Exception {
		Map map = new HashMap();
		return map;
	}
	@Override
	public List showPurchaseEnterIdListBySourceid(String sourceid) throws Exception{
		return purchaseEnterMapper.showPurchaseEnterIdListBySourceid(sourceid);
	}
	@Override
	public List showPurchaseEnterListBy(Map map) throws Exception{
		String datasql = getDataAccessRule("t_storage_purchase_enter",null);
		map.put("dataSql", datasql);
		boolean showdetail=false;
		if(null!=map.get("showdetail") && StringUtils.isNotEmpty(map.get("showdetail").toString()) &&"1".equals(map.get("showdetail").toString()) ){
			showdetail=true;
		}
		List<PurchaseEnter> list=purchaseEnterMapper.showPurchaseEnterListBy(map);
		for(PurchaseEnter item : list){
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
			if(StringUtils.isNotEmpty(item.getBuyuserid())){
				Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(item.getBuyuserid());
				if(null!=personnel){
					item.setBuyusername(personnel.getName());
				}
			}
			if(StringUtils.isNotEmpty(item.getBuydeptid())){
				DepartMent departMent=getDepartmentByDeptid(item.getBuydeptid());
				if(null!=departMent){
					item.setBuydeptname(departMent.getName());
				}
			}
			if(showdetail){
				List<PurchaseEnterDetail> detailList=purchaseEnterMapper.getPurchaseEnterDetailList(item.getId());
				if(null!=list && list.size()>0){
					for(PurchaseEnterDetail detail :detailList){
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
					item.setPurchaseEnterDetailList(detailList);
				}
			}
		}
		return list;
	}
	


	@Override
	public boolean updateOrderPrinttimes(PurchaseEnter purchaseEnter) throws Exception{
		return purchaseEnterMapper.updateOrderPrinttimes(purchaseEnter)>0;
	}
	@Override
	public void updateOrderPrinttimes(List<PurchaseEnter> list) throws Exception{
		if(null!=list){
			for(PurchaseEnter item : list){
				purchaseEnterMapper.updateOrderPrinttimes(item);
			}
		}
	}
	@Override
	public PurchaseEnter showPurchaseEnter(String id) throws Exception{
		return purchaseEnterMapper.getPurchaseEnterInfo(id);
	}
	
	
	
	
	
	
	@Override
	public boolean addPurchaseEnterByDeliveryOrder(StorageDeliveryEnterForJob enterJob,List<StorageDeliveryEnterForJob> enterJobDetails) throws Exception {
		boolean flag=false;
		if(null!=enterJob){
			PurchaseEnter purchaseEnter = new PurchaseEnter();
			if (isAutoCreate("t_storage_purchase_enter")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(purchaseEnter, "t_storage_purchase_enter");
				purchaseEnter.setId(id);
			}else{
				purchaseEnter.setId("CGRUD"+CommonUtils.getDataNumberSendsWithRand());
			}
			//业务日期取验收日期
			purchaseEnter.setBusinessdate(CommonUtils.getTodayDataStr());
			//11.4修改状态为审核的	
			purchaseEnter.setStatus("3");
			String remark = enterJob.getPremark()==null?"":enterJob.getPremark();
            purchaseEnter.setSourceid(enterJob.getId());
			purchaseEnter.setRemark(remark);
			purchaseEnter.setAdduserid(enterJob.getAdduserid());
			purchaseEnter.setAddusername(enterJob.getAddusername());
			//新增人信息
			purchaseEnter.setAdddeptid(enterJob.getAdddeptid());
			purchaseEnter.setAdddeptname(enterJob.getAdddeptname());
			purchaseEnter.setAddtime(new Date());
			//审核人信息
			purchaseEnter.setAudittime(new Date());
			purchaseEnter.setAudituserid(enterJob.getAudituserid());
			purchaseEnter.setAuditusername(enterJob.getAuditusername());
			//修改人信息
			purchaseEnter.setModifytime(new Date());
			purchaseEnter.setModifyuserid(enterJob.getModifyuserid());
			purchaseEnter.setModifyusername(enterJob.getModifyusername());
			//终止人
//			purchaseEnter.setStoptime(new Date());
//			purchaseEnter.setStopuserid(enterJob.getStopuserid());
//			purchaseEnter.setStopusername(enterJob.getStopusername());
			//关闭时间
			purchaseEnter.setClosetime(enterJob.getClosetime());
			//仓库置空
			purchaseEnter.setStorageid(enterJob.getStorageid());
			
			//采购部门,采购名称
			BuySupplier sup=getSupplierInfoById(enterJob.getSupplierid());
			if(sup!=null){
				purchaseEnter.setBuyuserid(sup.getBuyuserid()==null?"":sup.getBuyuserid());
				purchaseEnter.setBuydeptid(sup.getBuydeptid()==null?"":sup.getBuydeptid());
			}
			
			purchaseEnter.setSupplierid(enterJob.getSupplierid());
			purchaseEnter.setSourcetype("2");
			purchaseEnter.setIsrefer("1");
			//添加采购入库明细.
			for(StorageDeliveryEnterForJob detail:enterJobDetails){
				addPurchaseEnterDetailByDeliveryOrder(detail, purchaseEnter);
			}
			flag = purchaseEnterMapper.addPurchaseEnterForDeliveyJob(purchaseEnter)>0;
			if(flag){
				List<PurchaseEnterDetail> purchaseEnterDetailList=purchaseEnterMapper.getPurchaseEnterDetailList(purchaseEnter.getId());
				//新增采购进货单,保存状态的
				String rsId=purchaseForStorageService.addCreateArrivalOrderByDeliveryEnter(purchaseEnter, purchaseEnterDetailList);
				if(StringUtils.isEmpty(rsId)){
					flag=false;
				}
				
			}
		}
		return flag;
	}

    /**
     * 为扫描枪提供采购入库单列表
     *
     * @return
     * @throws Exception
     */
    @Override
    public List getPurchaseEnterForScanList(Map map) throws Exception {
        String dataSql = getDataAccessRule("t_storage_purchase_enter", "t");
        map.put("dataSql",dataSql);
        List list = purchaseEnterMapper.getPurchaseEnterForScanList(map);
        return list;
    }

    /**
     * 获取采购入库单明细
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List getPurchaseEnterDetail(String id) throws Exception {
        List list = purchaseEnterMapper.getPurchaseEnterDetailList(id);
        return list;
    }

    /**
     * 扫描枪采购入库单上传并且审核
     *
     * @param json
     * @return
     * @throws Exception
     */
    @Override
    public Map uploadPurchaseEnterAndAudit(String json) throws Exception {
        Map map = new HashMap();
        boolean flag = true;
        boolean editFlag = true;
        String msg = "";
        JSONObject jsonObject = JSONObject.fromObject(json);
        if(null!=jsonObject) {
            String billid = jsonObject.getString("billid");
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            if(null==jsonArray || jsonArray.size()==0){
                map.put("flag",false);
                map.put("msg","采购入库单明细不存在。");
                return map;
            }
            //获取修改前采购入库单信息
            PurchaseEnter purchaseEnter = purchaseEnterMapper.getPurchaseEnterInfo(billid);
            if(null==purchaseEnter ||"3".equals(purchaseEnter.getStatus()) || "4".equals(purchaseEnter.getStatus())){
                map.put("flag",false);
                map.put("msg","采购入库单不存在或者已审核。");
                return map;
            }
            //修改前 采购入库单为暂存状态时
            if("1".equals(purchaseEnter.getStatus())){
                //删除采购入库单明细
                purchaseEnterMapper.deletePurchaseEnterDetail(purchaseEnter.getId());
            }else if("2".equals(purchaseEnter.getStatus()) || "6".equals(purchaseEnter.getStatus())){
                //修改前 采购入库单为保存状态时 回滚在途量
                List<PurchaseEnterDetail> oldList = purchaseEnterMapper.getPurchaseEnterDetailList(purchaseEnter.getId());
                for(PurchaseEnterDetail purchaseEnterDetail : oldList){
                    //回滚在途量
                    rollBackStorageSummaryTransitnum(purchaseEnter.getStorageid(), purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getUnitnum());
                }

            }
            List<PurchaseEnterDetail> detailList = new ArrayList<PurchaseEnterDetail>();
            boolean editflag = true;
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jdetail = jsonArray.getJSONObject(i);
                String cid = jdetail.getString("cid");
                String goodsId = jdetail.getString("goodsid");
				String goodstype = jdetail.getString("goodstype");
                PurchaseEnterDetail oldDetail = purchaseEnterMapper.getPurchaseEnterDetail(cid);
                if(null!=oldDetail && oldDetail.getGoodsid().equals(goodsId)){
                    PurchaseEnterDetail newDetail = (PurchaseEnterDetail) CommonUtils.deepCopy(oldDetail);
                    String barcode = jdetail.getString("barcode");
                    String producedate = jdetail.getString("producedate");
                    String num = jdetail.getString("num");
                    String auxnum = jdetail.getString("auxnum");
                    String remark = "";
                    if(jdetail.has("remark")){
                        jdetail.getString("remark");
                    }
                    //根据商品编号 获取商品箱装量 计算商品数量
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(newDetail.getGoodsid());
                    if(null!=goodsInfo){
                        GoodsInfo_MteringUnitInfo unitInfo = getDefaultGoodsAuxMeterUnitInfo(newDetail.getGoodsid());
                        BigDecimal bNum = new BigDecimal(auxnum).multiply(unitInfo.getRate()).add(new BigDecimal(num));
                        newDetail.setUnitnum(bNum);
                        //计算辅单位数量
                        Map auxmap = countGoodsInfoNumber(newDetail.getGoodsid(), newDetail.getAuxunitid(), newDetail.getUnitnum());
                        if (auxmap.containsKey("auxnum")) {
                            newDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                            String auxremainderStr = (String) auxmap.get("auxremainder");
                            String auxIntegerStr = (String) auxmap.get("auxInteger");
                            String auxnumdetail = (String) auxmap.get("auxnumdetail");
                            BigDecimal auxnumB = BigDecimal.ZERO;
                            BigDecimal auxremainderB = BigDecimal.ZERO;
                            if (StringUtils.isNotEmpty(auxIntegerStr)) {
                                auxnumB = new BigDecimal(auxIntegerStr);
                            }
                            if (StringUtils.isNotEmpty(auxremainderStr)) {
                                auxremainderB = new BigDecimal(auxremainderStr);
                            }
                            newDetail.setAuxnum(auxnumB);
                            newDetail.setAuxremainder(auxremainderB);
                            newDetail.setAuxnumdetail(auxnumdetail);
                            BigDecimal taxamount = newDetail.getTaxprice().multiply(newDetail.getUnitnum());
                            BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, goodsInfo.getDefaulttaxtype());
							newDetail.setTaxamount(taxamount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
							newDetail.setNotaxamount(notaxamount);
                            BigDecimal tax = newDetail.getTaxamount().subtract(newDetail.getNotaxamount());
							newDetail.setTax(tax);
                            if(StringUtils.isNotEmpty(producedate) && !"null".equals(producedate)){
                                Map batchMap = getBatchno(newDetail.getGoodsid(),producedate);
                                if(null!=batchMap){
                                    String batchno = (String) batchMap.get("batchno");
                                    String deadline = (String) batchMap.get("deadline");
                                    newDetail.setBatchno(batchno);
                                    newDetail.setDeadline(deadline);
                                    newDetail.setProduceddate(producedate);
                                }
                            }
                            if(StringUtils.isNotEmpty(barcode)){
                                newDetail.setBarcode(barcode);
                            }else{
                                newDetail.setBarcode(goodsInfo.getBarcode());
                            }
                            newDetail.setRemark(remark);
                            //过滤数量小于等于0的明细
                            if(newDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
                                detailList.add(newDetail);
                            }
                        }
                    }else{
                        flag = false;
                        msg = "扫描枪上传的商品在系统中不存在。";
                        editflag = false;
                    }
                }else if(("1").equals(goodstype)){
					PurchaseEnterDetail newDetail = new PurchaseEnterDetail();

					String barcode = jdetail.getString("barcode");
					String producedate = jdetail.getString("producedate");
					String num = jdetail.getString("num");
					String auxnum = jdetail.getString("auxnum");
					String remark = "";
					if(jdetail.has("remark")){
						jdetail.getString("remark");
					}
					//根据商品编号 获取商品箱装量 计算商品数量
					newDetail.setGoodsid(goodsId);
					newDetail.setGoodstype(goodstype);
					GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsId);
					if(null!=goodsInfo){
						GoodsInfo_MteringUnitInfo unitInfo = getDefaultGoodsAuxMeterUnitInfo(newDetail.getGoodsid());
						BigDecimal bNum = new BigDecimal(auxnum).multiply(unitInfo.getRate()).add(new BigDecimal(num));
						newDetail.setUnitnum(bNum);
						//计算辅单位数量
						Map auxmap = countGoodsInfoNumber(newDetail.getGoodsid(), newDetail.getAuxunitid(), newDetail.getUnitnum());
						if (auxmap.containsKey("auxnum")) {
							newDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
							String auxremainderStr = (String) auxmap.get("auxremainder");
							String auxIntegerStr = (String) auxmap.get("auxInteger");
							String auxnumdetail = (String) auxmap.get("auxnumdetail");
							BigDecimal auxnumB = BigDecimal.ZERO;
							BigDecimal auxremainderB = BigDecimal.ZERO;
							if (StringUtils.isNotEmpty(auxIntegerStr)) {
								auxnumB = new BigDecimal(auxIntegerStr);
							}
							if (StringUtils.isNotEmpty(auxremainderStr)) {
								auxremainderB = new BigDecimal(auxremainderStr);
							}
							newDetail.setUnitid(goodsInfo.getMainunit());
							newDetail.setUnitname(goodsInfo.getMainunitName());
							newDetail.setAuxunitname(goodsInfo.getAuxunitname());
							newDetail.setAuxunitid(goodsInfo.getAuxunitid());
							newDetail.setAuxnum(auxnumB);
							newDetail.setAuxremainder(auxremainderB);
							newDetail.setAuxnumdetail(auxnumdetail);
							newDetail.setTaxamount(new BigDecimal("0"));
							newDetail.setNotaxamount(new BigDecimal("0"));
							newDetail.setTax(new BigDecimal("0"));
							if(StringUtils.isNotEmpty(producedate) && !"null".equals(producedate)){
								Map batchMap = getBatchno(newDetail.getGoodsid(),producedate);
								if(null!=batchMap){
									String batchno = (String) batchMap.get("batchno");
									String deadline = (String) batchMap.get("deadline");
									newDetail.setBatchno(batchno);
									newDetail.setDeadline(deadline);
									newDetail.setProduceddate(producedate);
								}
							}
							if(StringUtils.isNotEmpty(barcode)){
								newDetail.setBarcode(barcode);
							}else{
								newDetail.setBarcode(goodsInfo.getBarcode());
							}
							newDetail.setRemark(remark);
							//过滤数量小于等于0的明细
							if(newDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
								detailList.add(newDetail);
							}
						}
					}else{
						flag = false;
						msg = "扫描枪上传的商品在系统中不存在。";
						editflag = false;
					}
				}else{
                    flag = false;
                    msg = "扫描枪上传采购入库明细与系统中不一致。";
                    editflag = false;
                }

            }
            if(editflag){
                //删除采购入库单明细
                purchaseEnterMapper.deletePurchaseEnterDetail(purchaseEnter.getId());
                if(detailList.size()>0){
                    //添加采购入库单明细
                    for(PurchaseEnterDetail purchaseEnterDetail:detailList){
                        //计算辅单位数量
                        Map auxmap = countGoodsInfoNumber(purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getAuxunitid(), purchaseEnterDetail.getUnitnum());
                        if(auxmap.containsKey("auxnum")){
                            purchaseEnterDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                        }

                        purchaseEnterDetail.setPurchaseenterid(purchaseEnter.getId());
                        purchaseEnterDetail.setStorageid(purchaseEnter.getStorageid());
                        GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseEnterDetail.getGoodsid());
                        if(null!=goodsInfo){
                            purchaseEnterDetail.setBrandid(goodsInfo.getBrand());
                        }
                        if("2".equals(purchaseEnter.getStatus())){
                            //获取商品在仓库中的现存量信息
                            //更新在途量
                            updateStorageSummaryTransitnum(purchaseEnter.getStorageid(), purchaseEnterDetail.getGoodsid(), purchaseEnterDetail.getUnitnum());
                        }
                        purchaseEnterDetail.setId(null);
                        purchaseEnterMapper.addPurchaseEnterDetail(purchaseEnterDetail);
                    }
                }
                //添加采购入库单
                SysUser sysUser = getSysUser();
                purchaseEnter.setModifyuserid(sysUser.getUserid());
                purchaseEnter.setModifyusername(sysUser.getName());
                int i = purchaseEnterMapper.updatePurchaseEnter(purchaseEnter);
                if("1".equals(purchaseEnter.getSourcetype())){
                    //调用采购订单回写接口
                    List purchaseEnterSumList = purchaseEnterMapper.getPurchaseEnterDetailSumGoodsidList(purchaseEnter.getSourceid());
                    purchaseForStorageService.updateBuyOrderDetailWriteBack(purchaseEnter.getSourceid(), purchaseEnterSumList);
                }
                //审核采购入库单
                Map returnMap = auditPurchaseEnter(purchaseEnter.getId());
                if(returnMap.containsKey("flag")){
                    flag  = (Boolean)returnMap.get("flag");
                    msg = (String) returnMap.get("msg");
                    if(!flag){
                        throw new Exception("扫描枪上传 采购入库单："+purchaseEnter.getId()+"审核失败，回滚");
                    }
                }
            }else{
                throw new Exception("扫描枪上传 采购入库单："+purchaseEnter.getId()+"修改失败，回滚");
            }
        }
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

    @Override
    public Map auditMultiPurchaseEnter(String ids) throws Exception {
        int failure = 0;
        int success = 0;
        boolean sFlag = true;
        String msg = "";
        String enterStorageids= "";
        String errorids = "";
        String failuerMsg = "";
        if (StringUtils.isNotEmpty(ids)) {
            if (ids.endsWith(",")) {
                ids = ids.substring(0, ids.length() - 1);
            }
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                Map map = auditPurchaseEnter(id);
                Boolean auditflag = (Boolean) map.get("flag");
                if (auditflag) {
                    success++;
                    enterStorageids +=(String) map.get("downid") + ",";
                } else {
                    failure++;
                    errorids += id+"("+(String) map.get("msg")+"),";
                }
            }
            if(success > 0){
                msg = "批量审核采购入库单成功并生成采购进货单："+enterStorageids.substring(0,enterStorageids.length()-1);
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
        msg += failuerMsg;
        map.put("msg", msg);
        return map;
    }


    private  boolean addPurchaseEnterDetailByDeliveryOrder(StorageDeliveryEnterForJob detail,PurchaseEnter enter) throws Exception{
		
		PurchaseEnterDetail purchaseEnterDetail = new PurchaseEnterDetail();
		
		purchaseEnterDetail.setPurchaseenterid(enter.getId()==null?"":enter.getId());
//		purchaseEnterDetail.setBuyorderid(enter.getSourceid()==null?"":enter.getSourceid());
//		purchaseEnterDetail.setBuyorderdetailid(detail.getId()==null?"":detail.getId()+"");
		purchaseEnterDetail.setGoodsid(detail.getGoodsid()==null?"":detail.getGoodsid());
		purchaseEnterDetail.setBrandid(detail.getBrandid()==null?"":detail.getBrandid());
		purchaseEnterDetail.setStorageid(enter.getStorageid()==null?"":enter.getStorageid());
		
		purchaseEnterDetail.setUnitid(detail.getUnitid()==null?"":detail.getUnitid());
		purchaseEnterDetail.setUnitname(detail.getUnitname()==null?"":detail.getUnitname()); //主计量单位名称
		purchaseEnterDetail.setUnitnum(detail.getUnitnum()); //数量
		purchaseEnterDetail.setAuxunitid(detail.getAuxunitid()==null?"":detail.getAuxunitid());
		purchaseEnterDetail.setAuxunitname(detail.getAuxunitname()==null?"":detail.getAuxunitname()); //辅计量单位名称
		purchaseEnterDetail.setTotalbox(detail.getTotalbox());
        purchaseEnterDetail.setAddcostprice(detail.getAddcostprice());
		if(StringUtils.isNotEmpty(detail.getGoodsid())){
			GoodsInfo goodsInfo=getGoodsInfo(detail.getGoodsid());
			if(goodsInfo!=null){
				//箱装量
				BigDecimal boxNum=goodsInfo.getBoxnum();
				//辅计量  XX箱
				BigDecimal auxnum=detail.getUnitnum().divide(boxNum,0,BigDecimal.ROUND_DOWN);
				purchaseEnterDetail.setAuxnum(auxnum);
				//主单位余数
				BigDecimal left=detail.getUnitnum().subtract(boxNum.multiply(auxnum));
				purchaseEnterDetail.setAuxremainder(left);
				//XX箱XX个
				BigDecimal left2=left.setScale(0, BigDecimal.ROUND_HALF_DOWN);
				purchaseEnterDetail.setAuxnumdetail(auxnum+detail.getAuxunitname()+left2+detail.getUnitname());
				
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
				purchaseEnterDetail.setTaxprice(taxPrice);
				//含税金额
				BigDecimal taxAmount=taxPrice.multiply(detail.getUnitnum());
				purchaseEnterDetail.setTaxamount(taxAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				//税种
				purchaseEnterDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
				
				TaxType taxTypeInfo=getTaxType(goodsInfo.getDefaulttaxtype());
				if(taxTypeInfo!=null){
					BigDecimal taxrate = taxTypeInfo.getRate().divide(new BigDecimal(100),6, BigDecimal.ROUND_HALF_UP);
					//无税单价 = 含税单价/（1+税率） 保存2位小数
					BigDecimal notaxprice = taxPrice.divide(taxrate.add(new BigDecimal(1)),6,BigDecimal.ROUND_HALF_UP);
					purchaseEnterDetail.setNotaxprice(notaxprice);
					//无税金额 = 无税单价*数量
					BigDecimal notaxamount = notaxprice.multiply(detail.getUnitnum());
					purchaseEnterDetail.setNotaxamount(notaxamount );
					//税额
					purchaseEnterDetail.setTax(purchaseEnterDetail.getTaxamount().subtract(purchaseEnterDetail.getNotaxamount()));
				}
			}
		}
		purchaseEnterDetail.setRemark(detail.getRemark()==null?"":detail.getRemark());
		purchaseEnterDetail.setSeq(detail.getSeq());
		int i  = purchaseEnterMapper.addPurchaseEnterDetail(purchaseEnterDetail);
		return i>0;
	}

	@Override
	public List getPurchaseEnterDetailSumGoodsidList(String buyorderid) {
		List purchaseEnterSumList = purchaseEnterMapper.getPurchaseEnterDetailSumGoodsidList(buyorderid);
		return  purchaseEnterSumList;
	}
	
}

