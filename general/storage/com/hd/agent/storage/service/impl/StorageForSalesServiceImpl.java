/**
 * @(#)StorageForSalesServiceImpl.java
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
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.sales.model.*;
import com.hd.agent.storage.dao.SaleRejectEnterMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IStorageForSalesService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 库存管理提供销售模块接口 实现类
 * @author chenwei
 */
public class StorageForSalesServiceImpl extends StorageSaleOutServiceImpl
		implements IStorageForSalesService {
	
	
	private SaleRejectEnterMapper saleRejectEnterMapper;
	
	private ReceiptMapper receiptMapper;
	
	public ReceiptMapper getReceiptMapper() {
		return receiptMapper;
	}

	public void setReceiptMapper(ReceiptMapper receiptMapper) {
		this.receiptMapper = receiptMapper;
	}

	public SaleRejectEnterMapper getSaleRejectEnterMapper() {
		return saleRejectEnterMapper;
	}

	public void setSaleRejectEnterMapper(SaleRejectEnterMapper saleRejectEnterMapper) {
		this.saleRejectEnterMapper = saleRejectEnterMapper;
	}

	@Override
	public boolean updateSaleoutRefer(String isrefer, String saleorderid)
			throws Exception {
		int i = saleoutMapper.updateSaleoutRefer(saleorderid, isrefer);
		return i>0;
	}

	@Override
	public boolean updateSaleOutClose(String id) throws Exception {
		int i = saleoutMapper.updateSaleOutClose(id);
		return i>0;
	}
	@Override
	public boolean updateSaleOutOpen(String id) throws Exception {
		int i = saleoutMapper.updateSaleOutOpen(id);
		return i>0;
	}

	@Override
	public void updateSaleOutBack(List<ReceiptDetail> detailList)
			throws Exception {
		Map saleoutMap = new HashMap();
		String receiptid = "";
		for(ReceiptDetail receiptDetail : detailList){
			receiptid = receiptDetail.getBillid();
			SaleoutDetail saleoutDetail = saleoutMapper.getSaleoutDetailById(receiptDetail.getBilldetailno());
			if(null!=saleoutDetail){
				saleoutMap.put(saleoutDetail.getSaleoutid(), saleoutDetail.getSaleoutid());
				
				//实际交货数量 = 回单数量
				saleoutDetail.setRealsendnum(receiptDetail.getReceiptnum());
				//退货数量 = 出库单明显数量 - 回单数量
				saleoutDetail.setReturnnum(saleoutDetail.getUnitnum().subtract(receiptDetail.getReceiptnum()));
				Map auxrealMap  = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getRealsendnum());
				saleoutDetail.setAuxrealsendnum((BigDecimal) auxrealMap.get("auxnum"));
				saleoutDetail.setAuxrealsendnumdetail((String) auxrealMap.get("auxnumdetail"));
				
				Map axureturnMap = countGoodsInfoNumber(saleoutDetail.getGoodsid(), saleoutDetail.getAuxunitid(), saleoutDetail.getReturnnum());
				saleoutDetail.setAuxreturnnum((BigDecimal) auxrealMap.get("auxnum"));
				saleoutDetail.setAuxreturnnumdetail((String) auxrealMap.get("auxnumdetail"));
				
				saleoutDetail.setRealtaxamount(receiptDetail.getReceipttaxamount());
				saleoutDetail.setRealnotaxamount(receiptDetail.getReceiptnotaxamount());
				//退货含税金额 = 总含税金额-实际交货含税金额
				saleoutDetail.setReturntaxamount(saleoutDetail.getTaxamount().subtract(saleoutDetail.getRealtaxamount()));
				//退货无税金额 = 总无税金额 - 实际交货无税金额
				saleoutDetail.setReturnnotaxamount(saleoutDetail.getNotaxamount().subtract(saleoutDetail.getRealnotaxamount()));
				
				saleoutMapper.updateSaleoutDetailBack(saleoutDetail);
			}
		}
		//更新发货单 验收金额
		Set set = saleoutMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String saleoutid = entry.getKey();
			SaleoutDetail saleoutDetail = saleoutMapper.getSaleOutSumAmountBySaleoutid(saleoutid);
			if(null!=saleoutDetail){
				Saleout saleout = new Saleout();
				saleout.setId(saleoutid);
				saleout.setCheckdate(CommonUtils.getTodayDataStr());
				saleout.setCheckamount(saleoutDetail.getRealtaxamount());
				saleout.setChecknotaxamount(saleoutDetail.getRealnotaxamount());
				Saleout saleout1 = saleoutMapper.getSaleOutInfo(saleoutid);
				if(null != saleout1 && ("1".equals(saleout1.getIsinvoicebill()) || "4".equals(saleout1.getIsinvoicebill()))){
					saleout.setInvoicebillamount(saleoutDetail.getRealtaxamount());
					saleout.setInvoicebillnotaxamount(saleoutDetail.getRealnotaxamount());
				}
//				saleout.setCheckamount(saleoutDetail.getTaxamount());
//				saleout.setChecknotaxamount(saleoutDetail.getNotaxamount());
				saleout.setReceiptid(receiptid);
				saleoutMapper.editSaleOut(saleout);
			}
		}
	}
	@Override
	public boolean receiptCanAuditBySaleorderid(String saleorderid)
			throws Exception {
		int count = saleoutMapper.getUnauditSaleOutCountBySaleorderid(saleorderid);
		return count==0;
	}
	@Override
	public void updateClearSaleOutBack(List<ReceiptDetail> detailList)
			throws Exception {
		Map saleoutMap = new HashMap();
		for(ReceiptDetail receiptDetail : detailList){
			SaleoutDetail saleoutDetail = saleoutMapper.getSaleoutDetailById(receiptDetail.getBilldetailno());
			if(null!=saleoutDetail){
				saleoutMap.put(saleoutDetail.getSaleoutid(), saleoutDetail.getSaleoutid());
			}
			//回单含税单价修改后 ，更新发货单 含税单价 含税金额 未税单价 未税金额 税额
			if(saleoutDetail.getInittaxprice().compareTo(receiptDetail.getTaxprice())!=0){
				saleoutDetail.setTaxprice(saleoutDetail.getInittaxprice());
				BigDecimal taxamount=saleoutDetail.getInittaxprice().multiply(saleoutDetail.getUnitnum());
				saleoutDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
				if(null!=goodsInfo){
					BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, goodsInfo.getDefaulttaxtype());
					if(null!=saleoutDetail.getUnitnum() && saleoutDetail.getUnitnum().compareTo(BigDecimal.ZERO)!=0){
						saleoutDetail.setNotaxprice(notaxamount.divide(saleoutDetail.getUnitnum(),6,BigDecimal.ROUND_HALF_UP));
					}
					saleoutDetail.setNotaxamount(notaxamount);
					saleoutDetail.setTax(saleoutDetail.getTaxamount().subtract(saleoutDetail.getNotaxamount()));
				}
			}
			//实际交货数量 = 回单数量
			saleoutDetail.setRealsendnum(BigDecimal.ZERO);
			//退货数量 = 出库单明显数量 - 回单数量
			saleoutDetail.setReturnnum(BigDecimal.ZERO);
			saleoutDetail.setAuxrealsendnum(BigDecimal.ZERO);
			saleoutDetail.setAuxrealsendnumdetail("");
			
			saleoutDetail.setAuxreturnnum(BigDecimal.ZERO);
			saleoutDetail.setAuxreturnnumdetail("");
			
			saleoutDetail.setRealtaxamount(BigDecimal.ZERO);
			saleoutDetail.setRealnotaxamount(BigDecimal.ZERO);
			//退货含税金额 = 总含税金额-实际交货含税金额
			saleoutDetail.setReturntaxamount(BigDecimal.ZERO);
			//退货无税金额 = 总无税金额 - 实际交货无税金额
			saleoutDetail.setReturnnotaxamount(BigDecimal.ZERO);
			saleoutMapper.updateSaleoutDetailBack(saleoutDetail);
		}
		//更新发货单 验收金额
		Set set = saleoutMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String saleoutid = entry.getKey();
			Saleout saleout = new Saleout();
			saleout.setId(saleoutid);
			saleout.setCheckdate("");
			saleout.setCheckamount(BigDecimal.ZERO);
			saleout.setChecknotaxamount(BigDecimal.ZERO);
//				saleout.setCheckamount(saleoutDetail.getTaxamount());
//				saleout.setChecknotaxamount(saleoutDetail.getNotaxamount());
			saleoutMapper.editSaleOut(saleout);
		}
	}
	
	@Override
	public Map addSaleOutByDispatchbill(DispatchBill dispatchBill,
			List<DispatchBillDetail> detailList) throws Exception {
		Map map = new HashMap();
		//判断发货单是否指定了仓库
		if(StringUtils.isEmpty(dispatchBill.getStorageid())){
			map = super.addSaleOutByDispatchbill(dispatchBill, detailList);
		}else{
			map = super.addSaleOutByDispatchbillWithStorageid(dispatchBill.getStorageid(), dispatchBill, detailList);
		}
		return map;
	}
	@Override
	public Map addSaleOutByOrdercar(DispatchBill dispatchBill,
			List<DispatchBillDetail> detailList) throws Exception {
		Map map = new HashMap();
		if(null!=dispatchBill){
			//根据销售发货通知单生成发货单
			Map addmap = super.addSaleOutByDispatchbillWithStorageid(dispatchBill.getStorageid(), dispatchBill, detailList);
			boolean flag = (Boolean) addmap.get("flag");
			String saleoutid = (String) addmap.get("saleoutid");
			if(flag){
				//自动审核发货单
				Map auditmap = super.auditSaleOut(saleoutid);
				flag = (Boolean) auditmap.get("flag");
				String receiptid = (String) auditmap.get("receiptid");
				map.put("receiptid", receiptid);
			}
			map.put("flag", flag);
		}else{
			map.put("flag", false);
		}
		return map;
	}
	@Override
	public boolean deleteSaleOutBySourceid(String sourceid) throws Exception {
		List<Saleout> saleoutList = saleoutMapper.getSaleOutInfoBySourceid(sourceid);
		boolean flag = true;
		for(Saleout saleout : saleoutList){
			if(!"1".equals(saleout.getStatus()) && !"2".equals(saleout.getStatus())){
				flag = false;
				break;
			}
		}
		if(flag){
			for(Saleout saleout : saleoutList){
				List<SaleoutDetail> detailList = saleoutMapper.getSaleOutDetailList(saleout.getId());
				int i = 0;
                boolean rollBackFlag = true;
				//删除明细 回滚待发量
				for(SaleoutDetail saleoutDetail : detailList){
					//保存状态时，回滚待发量
					if("2".equals(saleout.getStatus())){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleoutDetail.getSummarybatchid());
						if(null!=storageSummaryBatch && rollBackFlag){
							//回滚待发量
							rollBackStorageSummaryWaitnum(storageSummaryBatch.getId(), saleoutDetail.getUnitnum());
						}
					}
				}
                if(rollBackFlag){
                    //删除销售出库单
                    int j = saleoutMapper.deleteSaleOut(saleout.getId());
                    saleoutMapper.deleteSaleOutDetailBySaleoutid(saleout.getId());
                }else{
                    throw new Exception("发货单待发量回滚失败");
                }

			}
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String addSaleRejectEnterByRejectBill(RejectBill rejectBill,
			List<RejectBillDetail> detailList) throws Exception {
		//根据退货通知单 是否残次
		//生成不同仓库的退货入库单
		Map storageMap = new HashMap();
		if(null!=rejectBill && ("1".equals(rejectBill.getIsincomplete()) || (null!=rejectBill.getStorageid() && !"".equals(rejectBill.getStorageid())))){
			storageMap.put(rejectBill.getStorageid(), detailList);
		}else{
			for(RejectBillDetail rejectBillDetail : detailList){
				//商品指定库存后 添加到指定仓库的销售退货入库单
				if(StringUtils.isNotEmpty(rejectBillDetail.getStorageid())){
					String storageid = rejectBillDetail.getStorageid();
					if(storageMap.containsKey(storageid)){
						List<RejectBillDetail> list = (List<RejectBillDetail>) storageMap.get(storageid);
						list.add(rejectBillDetail);
						storageMap.put(storageid, list);
					}else{
						List<RejectBillDetail> list = new ArrayList<RejectBillDetail>();
						list.add(rejectBillDetail);
						storageMap.put(storageid, list);
					}
				}else{
					GoodsInfo goodsInfo = getGoodsInfoByID(rejectBillDetail.getGoodsid());
					if(null!=goodsInfo){
						String storageid = goodsInfo.getStorageid();
						if(storageMap.containsKey(storageid)){
							List<RejectBillDetail> list = (List<RejectBillDetail>) storageMap.get(storageid);
							list.add(rejectBillDetail);
							storageMap.put(storageid, list);
						}else{
							List<RejectBillDetail> list = new ArrayList<RejectBillDetail>();
							list.add(rejectBillDetail);
							storageMap.put(storageid, list);
						}
					}
				}
			}
		}
		String ids = "";
		Set set = storageMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String storageid = entry.getKey();
			List<RejectBillDetail> dataList = (List<RejectBillDetail>) storageMap.get(storageid);
			
			SaleRejectEnter saleRejectEnter = new SaleRejectEnter();
			if (isAutoCreate("t_storage_salereject_enter")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(saleRejectEnter, "t_storage_salereject_enter");
				saleRejectEnter.setId(id);
			}else{
				saleRejectEnter.setId("XSTH-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			saleRejectEnter.setBusinessdate(getAuditBusinessdate(rejectBill.getBusinessdate()));
			saleRejectEnter.setCustomerid(rejectBill.getCustomerid());
			saleRejectEnter.setPcustomerid(rejectBill.getPcustomerid());
			saleRejectEnter.setCustomersort(rejectBill.getCustomersort());
			saleRejectEnter.setSalesarea(rejectBill.getSalesarea());
			saleRejectEnter.setSalesdept(rejectBill.getSalesdept());
			saleRejectEnter.setSalesuser(rejectBill.getSalesuser());
			saleRejectEnter.setStorageid(storageid);
			//退货入库单 1售后退货 2直退
			if("1".equals(rejectBill.getBilltype())){
				saleRejectEnter.setSourcetype("2");
			}else if("2".equals(rejectBill.getBilltype())){
				saleRejectEnter.setSourcetype("1");
			}
			saleRejectEnter.setSourceid(rejectBill.getId());
			saleRejectEnter.setPaytype(rejectBill.getPaytype());
			saleRejectEnter.setSettletype(rejectBill.getSettletype());
			saleRejectEnter.setHandlerid(rejectBill.getHandlerid());
			saleRejectEnter.setDriverid(rejectBill.getDriverid());
			saleRejectEnter.setStatus("2");
			saleRejectEnter.setAdddeptid(rejectBill.getAdddeptid());
			saleRejectEnter.setAdddeptname(rejectBill.getAdddeptname());
			saleRejectEnter.setAdduserid(rejectBill.getAdduserid());
			saleRejectEnter.setAddusername(rejectBill.getAddusername());
			saleRejectEnter.setIndooruserid(rejectBill.getIndooruserid());
			saleRejectEnter.setStorager(rejectBill.getStorager());
			saleRejectEnter.setRemark(rejectBill.getRemark());
			//应收日期
			saleRejectEnter.setDuefromdate(rejectBill.getDuefromdate());
			BigDecimal initsendamount = BigDecimal.ZERO;
			BigDecimal initsendcostamount = BigDecimal.ZERO;
			BigDecimal sendamount = BigDecimal.ZERO;
			BigDecimal sendnotaxamount = BigDecimal.ZERO;
			BigDecimal sendcostamount = BigDecimal.ZERO;
			
			for(RejectBillDetail rejectBillDetail : dataList){
				SaleRejectEnterDetail saleRejectEnterDetail = new SaleRejectEnterDetail();
				saleRejectEnterDetail.setSalerejectid(saleRejectEnter.getId());
				saleRejectEnterDetail.setRejectid(rejectBill.getId());
				saleRejectEnterDetail.setRejectdetailid(rejectBillDetail.getId());
				if(StringUtils.isEmpty(rejectBillDetail.getBillno())){
					saleRejectEnterDetail.setBillid(rejectBill.getId());
					saleRejectEnterDetail.setBilldetailid(rejectBillDetail.getId());
				}else{
					saleRejectEnterDetail.setBillid(rejectBillDetail.getBillno());
					saleRejectEnterDetail.setBilldetailid(rejectBillDetail.getBilldetailno());
				}
				
				saleRejectEnterDetail.setGoodsid(rejectBillDetail.getGoodsid());
				saleRejectEnterDetail.setBrandid(rejectBillDetail.getBrandid());
				saleRejectEnterDetail.setBranduser(rejectBillDetail.getBranduser());
				//厂家业务员
				saleRejectEnterDetail.setSupplieruser(rejectBillDetail.getSupplieruser());
				saleRejectEnterDetail.setBranddept(rejectBillDetail.getBranddept());
				
				GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
				if(null!=goodsInfo){
					//成本价
					saleRejectEnterDetail.setCostprice(getGoodsCostprice(saleRejectEnter.getStorageid(),goodsInfo));
					saleRejectEnterDetail.setGoodssort(goodsInfo.getDefaultsort());
                    //实际成本价 商品总成本价
					saleRejectEnterDetail.setRealcostprice(goodsInfo.getNewstorageprice());
					saleRejectEnterDetail.setSupplierid(goodsInfo.getDefaultsupplier());
				}
				saleRejectEnterDetail.setStorageid(saleRejectEnter.getStorageid());
				saleRejectEnterDetail.setUnitid(rejectBillDetail.getUnitid());
				saleRejectEnterDetail.setUnitname(rejectBillDetail.getUnitname());
				saleRejectEnterDetail.setUnitnum(rejectBillDetail.getUnitnum());
				saleRejectEnterDetail.setAuxunitid(rejectBillDetail.getAuxunitid());
				saleRejectEnterDetail.setAuxunitname(rejectBillDetail.getAuxunitname());
				saleRejectEnterDetail.setAuxnum(rejectBillDetail.getAuxnum());
				saleRejectEnterDetail.setAuxremainder(rejectBillDetail.getAuxremainder());
				saleRejectEnterDetail.setAuxnumdetail(rejectBillDetail.getAuxnumdetail());
				saleRejectEnterDetail.setTotalbox(rejectBillDetail.getTotalbox());
				
				saleRejectEnterDetail.setTaxprice(rejectBillDetail.getTaxprice());
				saleRejectEnterDetail.setTaxamount(rejectBillDetail.getTaxamount());
				saleRejectEnterDetail.setNotaxprice(rejectBillDetail.getNotaxprice());
				saleRejectEnterDetail.setNotaxamount(rejectBillDetail.getNotaxamount());
				saleRejectEnterDetail.setTax(rejectBillDetail.getTax());
				saleRejectEnterDetail.setTaxtype(rejectBillDetail.getTaxtype());
				saleRejectEnterDetail.setTaxamount(rejectBillDetail.getTaxamount());
				saleRejectEnterDetail.setRemark(rejectBillDetail.getRemark());
				
				saleRejectEnterDetail.setBatchno(rejectBillDetail.getBatchno());
				saleRejectEnterDetail.setStorageid(rejectBillDetail.getStorageid());
				saleRejectEnterDetail.setSummarybatchid(rejectBillDetail.getSummarybatchid());
				saleRejectEnterDetail.setStoragelocationid(rejectBillDetail.getStoragelocationid());
				saleRejectEnterDetail.setProduceddate(rejectBillDetail.getProduceddate());
				saleRejectEnterDetail.setDeadline(rejectBillDetail.getDeadline());
                saleRejectEnterDetail.setDuefromdate(rejectBillDetail.getDuefromdate());
				saleRejectEnterDetail.setRejectcategory(rejectBillDetail.getRejectcategory());
				saleRejectEnterMapper.addSaleRejectEnterDetail(saleRejectEnterDetail);

				initsendamount = initsendamount.add(saleRejectEnterDetail.getTaxamount());
				initsendcostamount = initsendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				sendamount = sendamount.add(saleRejectEnterDetail.getTaxamount());
				sendnotaxamount = sendnotaxamount.add(saleRejectEnterDetail.getNotaxamount());
				sendcostamount = sendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			saleRejectEnter.setInitsendamount(initsendamount);
			saleRejectEnter.setInitsendcostamount(initsendcostamount);
			saleRejectEnter.setSendamount(sendamount);
			saleRejectEnter.setSendnotaxamount(sendnotaxamount);
			saleRejectEnter.setSendcostamount(sendcostamount);
			int i = saleRejectEnterMapper.addSaleRejectEnter(saleRejectEnter);
			if(i>0){
				if("".equals(ids)){
					ids = saleRejectEnter.getId();
				}else{
					ids += ","+saleRejectEnter.getId();
				}
				//自动审核销售退货入库单
				auditSaleRejectEnter(saleRejectEnter.getId());
			}
			
		}
		return ids;
	}
	@Override
	public String addSaleRejectEnterByRejectSplit(RejectBill rejectBill,
			List<RejectBillDetail> detailList) throws Exception {
		//根据退货通知单 是否残次
		//生成不同仓库的退货入库单
		Map storageMap = new HashMap();
		if(null!=rejectBill && ("1".equals(rejectBill.getIsincomplete()) || (null!=rejectBill.getStorageid() && !"".equals(rejectBill.getStorageid())))){
			storageMap.put(rejectBill.getStorageid(), detailList);
		}else{
			for(RejectBillDetail rejectBillDetail : detailList){
				GoodsInfo goodsInfo = getGoodsInfoByID(rejectBillDetail.getGoodsid());
				if(null!=goodsInfo){
					String storageid = goodsInfo.getStorageid();
					if(storageMap.containsKey(storageid)){
						List<RejectBillDetail> list = (List<RejectBillDetail>) storageMap.get(storageid);
						list.add(rejectBillDetail);
						storageMap.put(storageid, list);
					}else{
						List<RejectBillDetail> list = new ArrayList<RejectBillDetail>();
						list.add(rejectBillDetail);
						storageMap.put(storageid, list);
					}
				}
			}
		}
		//多少天之内或多少月之内的最低销售价；
		String rejectCustomerGoodsPriceInMonth = getSysParamValue("RejectCustomerGoodsPriceInMonth");
		int month = 3;
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(rejectCustomerGoodsPriceInMonth)){
			month = Integer.parseInt(rejectCustomerGoodsPriceInMonth);
		}
		String lowestdate = CommonUtils.getBeforeDateInMonth(month);

		String ids = "";
		Set set = storageMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String storageid = entry.getKey();
			List<RejectBillDetail> dataList = (List<RejectBillDetail>) storageMap.get(storageid);
			
			SaleRejectEnter saleRejectEnter = new SaleRejectEnter();
			if (isAutoCreate("t_storage_salereject_enter")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(saleRejectEnter, "t_storage_salereject_enter");
				saleRejectEnter.setId(id);
			}else{
				saleRejectEnter.setId("XSTH-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			saleRejectEnter.setBusinessdate(rejectBill.getBusinessdate());
			saleRejectEnter.setCustomerid(rejectBill.getCustomerid());
			saleRejectEnter.setPcustomerid(rejectBill.getPcustomerid());
			saleRejectEnter.setCustomersort(rejectBill.getCustomersort());
			saleRejectEnter.setSalesarea(rejectBill.getSalesarea());
			saleRejectEnter.setSalesdept(rejectBill.getSalesdept());
			saleRejectEnter.setSalesuser(rejectBill.getSalesuser());
			saleRejectEnter.setStorageid(storageid);
			//退货入库单 1售后退货 2直退
			if("1".equals(rejectBill.getBilltype())){
				saleRejectEnter.setSourcetype("2");
			}else if("2".equals(rejectBill.getBilltype())){
				saleRejectEnter.setSourcetype("1");
			}
			saleRejectEnter.setSourceid(rejectBill.getId());
			saleRejectEnter.setPaytype(rejectBill.getPaytype());
			saleRejectEnter.setSettletype(rejectBill.getSettletype());
			saleRejectEnter.setHandlerid(rejectBill.getHandlerid());
			saleRejectEnter.setDriverid(rejectBill.getDriverid());
			saleRejectEnter.setStatus("2");
			saleRejectEnter.setAdddeptid(rejectBill.getAdddeptid());
			saleRejectEnter.setAdddeptname(rejectBill.getAdddeptname());
			saleRejectEnter.setAdduserid(rejectBill.getAdduserid());
			saleRejectEnter.setAddusername(rejectBill.getAddusername());
			saleRejectEnter.setIndooruserid(rejectBill.getIndooruserid());
			//退货应收日期
			saleRejectEnter.setDuefromdate(rejectBill.getDuefromdate());
			
			BigDecimal initsendamount = BigDecimal.ZERO;
			BigDecimal initsendcostamount = BigDecimal.ZERO;
			BigDecimal sendamount = BigDecimal.ZERO;
			BigDecimal sendnotaxamount = BigDecimal.ZERO;
			BigDecimal sendcostamount = BigDecimal.ZERO;
			
			for(RejectBillDetail rejectBillDetail : dataList){
				SaleRejectEnterDetail saleRejectEnterDetail = new SaleRejectEnterDetail();
				saleRejectEnterDetail.setSalerejectid(saleRejectEnter.getId());
				saleRejectEnterDetail.setBillid(rejectBill.getId());
				saleRejectEnterDetail.setBilldetailid(rejectBillDetail.getId());
				saleRejectEnterDetail.setRejectid(rejectBill.getId());
				saleRejectEnterDetail.setRejectdetailid(rejectBillDetail.getId());
				
				saleRejectEnterDetail.setGoodsid(rejectBillDetail.getGoodsid());
				saleRejectEnterDetail.setBrandid(rejectBillDetail.getBrandid());
				saleRejectEnterDetail.setBranduser(rejectBillDetail.getBranduser());
				//厂家业务员
				saleRejectEnterDetail.setSupplieruser(rejectBillDetail.getSupplieruser());
				saleRejectEnterDetail.setBranddept(rejectBillDetail.getBranddept());
				//成本价
				saleRejectEnterDetail.setCostprice(rejectBillDetail.getCostprice());
				GoodsInfo goodsInfo = getAllGoodsInfoByID(rejectBillDetail.getGoodsid());
				if(null!=goodsInfo){
					saleRejectEnterDetail.setGoodssort(goodsInfo.getDefaultsort());
                    //实际成本价 商品总成本价
					saleRejectEnterDetail.setRealcostprice(goodsInfo.getNewstorageprice());
					saleRejectEnterDetail.setSupplierid(goodsInfo.getDefaultsupplier());
				}
				
				saleRejectEnterDetail.setStorageid(rejectBill.getStorageid());
				saleRejectEnterDetail.setUnitid(rejectBillDetail.getUnitid());
				saleRejectEnterDetail.setUnitname(rejectBillDetail.getUnitname());
				saleRejectEnterDetail.setUnitnum(rejectBillDetail.getUnitnum());
				saleRejectEnterDetail.setAuxunitid(rejectBillDetail.getAuxunitid());
				saleRejectEnterDetail.setAuxunitname(rejectBillDetail.getAuxunitname());
				saleRejectEnterDetail.setAuxnum(rejectBillDetail.getAuxnum());
				saleRejectEnterDetail.setAuxremainder(rejectBillDetail.getAuxremainder());
				saleRejectEnterDetail.setAuxnumdetail(rejectBillDetail.getAuxnumdetail());
				saleRejectEnterDetail.setTotalbox(rejectBillDetail.getTotalbox());
				
				saleRejectEnterDetail.setTaxprice(rejectBillDetail.getTaxprice());
				saleRejectEnterDetail.setTaxamount(rejectBillDetail.getTaxamount());
				saleRejectEnterDetail.setNotaxprice(rejectBillDetail.getNotaxprice());
				saleRejectEnterDetail.setNotaxamount(rejectBillDetail.getNotaxamount());
				saleRejectEnterDetail.setTax(rejectBillDetail.getTax());
				saleRejectEnterDetail.setTaxtype(rejectBillDetail.getTaxtype());
				saleRejectEnterDetail.setTaxamount(rejectBillDetail.getTaxamount());
				saleRejectEnterDetail.setRemark(rejectBillDetail.getRemark());
				
				saleRejectEnterDetail.setBatchno(rejectBillDetail.getBatchno());
                saleRejectEnterDetail.setDuefromdate(rejectBillDetail.getDuefromdate());

				//取默认销售价（当时）（价格套价格或合同价）
				BigDecimal defaultprice = getDefaultSalesPrice(saleRejectEnter.getCustomerid(),saleRejectEnterDetail.getGoodsid());
				saleRejectEnterDetail.setDefaultprice(defaultprice);
				//多少天之内或多少月之内的最低销售价；t_storage_saleout
				BigDecimal lowestprice = saleoutMapper.getSaleOutLowestPrice(saleRejectEnter.getCustomerid(),saleRejectEnterDetail.getGoodsid(),lowestdate);
				if(null == lowestprice){
					lowestprice = defaultprice;
				}
				saleRejectEnterDetail.setLowestprice(lowestprice);
				//最近一次销售价（交易价）
				BigDecimal lastprice = saleoutMapper.getSaleOutLastPrice(saleRejectEnter.getCustomerid(),saleRejectEnterDetail.getGoodsid());
				if(null == lastprice){
					lastprice = defaultprice;
				}
				saleRejectEnterDetail.setLastprice(lastprice);
				saleRejectEnterMapper.addSaleRejectEnterDetail(saleRejectEnterDetail);

				initsendamount = initsendamount.add(saleRejectEnterDetail.getTaxamount());
				initsendcostamount = initsendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				sendamount = sendamount.add(saleRejectEnterDetail.getTaxamount());
				sendnotaxamount = sendnotaxamount.add(saleRejectEnterDetail.getNotaxamount());
				sendcostamount = sendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			saleRejectEnter.setInitsendamount(initsendamount);
			saleRejectEnter.setInitsendcostamount(initsendcostamount);
			saleRejectEnter.setSendamount(sendamount);
			saleRejectEnter.setSendnotaxamount(sendnotaxamount);
			saleRejectEnter.setSendcostamount(sendcostamount);
			int i = saleRejectEnterMapper.addSaleRejectEnter(saleRejectEnter);
			if(i>0){
				if("".equals(ids)){
					ids = saleRejectEnter.getId();
				}else{
					ids += ","+saleRejectEnter.getId();
				}
				SysUser sysUser = getSysUser();
				saleRejectEnterMapper.auditSaleRejectEnter(saleRejectEnter.getId(),"3","0",sysUser.getUserid(), sysUser.getName(),getAuditBusinessdate(saleRejectEnter.getBusinessdate()));
				List saleRejectEnterList = saleRejectEnterMapper.getSaleRejectEnterDetailList(saleRejectEnter.getId());
				salesOutService.updateRejectBillDetailBack(saleRejectEnterList);
			}
			
		}
		return ids;
	}
	/**
	 * 审核销售退货入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	public Map auditSaleRejectEnter(String id) throws Exception {
        //多少天之内或多少月之内的最低销售价；
        String rejectCustomerGoodsPriceInMonth = getSysParamValue("RejectCustomerGoodsPriceInMonth");
        int month = 3;
        if(StringUtils.isNotEmpty(rejectCustomerGoodsPriceInMonth)){
            month = Integer.parseInt(rejectCustomerGoodsPriceInMonth);
        }
        String lowestdate = CommonUtils.getBeforeDateInMonth(month);

		SaleRejectEnter saleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(id);
		boolean flag = false;
		String msg = "";
		if(null==saleRejectEnter){
			msg = "单据不存在！";
		}else if("2".equals(saleRejectEnter.getStatus()) || "6".equals(saleRejectEnter.getStatus())){
			boolean auditFlag = true;
			if(StringUtils.isEmpty(saleRejectEnter.getStorageid())){
				auditFlag = false;
				msg += "未指定入库仓库\n";
			}
			if(auditFlag){
				//仓库是否独立核算 0否1是 默认否
				String isStorageAccount = getStorageIsAccount(saleRejectEnter.getStorageid());
				//更新发货单出库的成本价
				saleRejectEnterMapper.updateSaleRejectEnterDetailCostprice(saleRejectEnter.getId(),isStorageAccount);
				List<SaleRejectEnterDetail> list = saleRejectEnterMapper.getSaleRejectEnterDetailList(id);
				for(SaleRejectEnterDetail saleRejectEnterDetail : list){
                    //取默认销售价（当时）（价格套价格或合同价）
                    BigDecimal defaultprice = getDefaultSalesPrice(saleRejectEnter.getCustomerid(),saleRejectEnterDetail.getGoodsid());
                    saleRejectEnterDetail.setDefaultprice(defaultprice);
                    //多少天之内或多少月之内的最低销售价；t_storage_saleout
                    BigDecimal lowestprice = saleoutMapper.getSaleOutLowestPrice(saleRejectEnter.getCustomerid(),saleRejectEnterDetail.getGoodsid(),lowestdate);
                    if(null == lowestprice){
                        lowestprice = defaultprice;
                    }
                    saleRejectEnterDetail.setLowestprice(lowestprice);
                    //最近一次销售价（交易价）
                    BigDecimal lastprice = saleoutMapper.getSaleOutLastPrice(saleRejectEnter.getCustomerid(),saleRejectEnterDetail.getGoodsid());
                    if(null == lastprice){
                        lastprice = defaultprice;
                    }
                    saleRejectEnterDetail.setLastprice(lastprice);
                    saleRejectEnterMapper.updateSaleRejectEnterDetailPrice(saleRejectEnterDetail);

					StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
                    if(StringUtils.isNotEmpty(saleRejectEnterDetail.getSummarybatchid())){
                        storageSummaryBatch.setId(saleRejectEnterDetail.getSummarybatchid());
                    }else{
                        storageSummaryBatch.setId(CommonUtils.getDataNumberSendsWithRand());
                    }
					storageSummaryBatch.setGoodsid(saleRejectEnterDetail.getGoodsid());
					storageSummaryBatch.setId(saleRejectEnterDetail.getSummarybatchid());
					//入库仓库
					storageSummaryBatch.setStorageid(saleRejectEnter.getStorageid());
					storageSummaryBatch.setStoragelocationid(saleRejectEnterDetail.getStoragelocationid());
					storageSummaryBatch.setBatchno(saleRejectEnterDetail.getBatchno());
					storageSummaryBatch.setProduceddate(saleRejectEnterDetail.getProduceddate());
					storageSummaryBatch.setDeadline(saleRejectEnterDetail.getDeadline());
					
//					storageSummaryBatch.setExistingnum(saleRejectEnterDetail.getUnitnum());
//					storageSummaryBatch.setUsablenum(saleRejectEnterDetail.getUnitnum());
//					storageSummaryBatch.setIntinum(saleRejectEnterDetail.getUnitnum());

					storageSummaryBatch.setUnitid(saleRejectEnterDetail.getUnitid());
					storageSummaryBatch.setUnitname(saleRejectEnterDetail.getUnitname());
					storageSummaryBatch.setAuxunitid(saleRejectEnterDetail.getAuxunitid());
					storageSummaryBatch.setAuxunitname(saleRejectEnterDetail.getAuxunitname());
					storageSummaryBatch.setPrice(saleRejectEnterDetail.getTaxprice());
					storageSummaryBatch.setAmount(saleRejectEnterDetail.getTaxamount());
					
					storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
					
					//销售退货入库 更新库存
					Map map = addStorageSummaryNum(storageSummaryBatch,saleRejectEnterDetail.getUnitnum(), "saleRejectEnter", id, "销售退货入库");
					//销售退货入库更新库存后 绑定销售退货入库单明细相关的库存批次编号
					if(null!=map && map.containsKey("summarybatchid")){
						String summarybatchid = (String) map.get("summarybatchid"); 
						if(StringUtils.isNotEmpty(summarybatchid)){
                            StorageSummaryBatch summaryBatch = getStorageSummaryBatchById(summarybatchid);
                            if(null!=summaryBatch){
                                saleRejectEnterMapper.updateSaleRejectEnterDetailSummarybatchid(saleRejectEnterDetail.getId(),summarybatchid,summaryBatch.getBatchno(),summaryBatch.getProduceddate(),summaryBatch.getDeadline(),summaryBatch.getStoragelocationid());
                            }else{
                                saleRejectEnterMapper.updateSaleRejectEnterDetailSummarybatchid(saleRejectEnterDetail.getId(),summarybatchid,null,null,null,null);
                            }
						}
						boolean addflag = (Boolean)map.get("flag");
						if(addflag){
							//更新成本价
							updateStoragePriceByAdd(saleRejectEnter.getStorageid(),saleRejectEnterDetail.getGoodsid(),saleRejectEnterDetail.getUnitnum(),saleRejectEnterDetail.getCostprice(), true, false,false,saleRejectEnter.getId(),saleRejectEnterDetail.getId()+"");
						}
					}
				}
				
				SysUser sysUser = getSysUser();
				if(null==sysUser){
					sysUser = getSysUserById(saleRejectEnter.getAdduserid());
				}
				int i = saleRejectEnterMapper.auditSaleRejectEnter(id,"3","0",sysUser.getUserid(), sysUser.getName(),getAuditBusinessdate(saleRejectEnter.getBusinessdate()));
				flag = i>0;
				if(flag){

					salesOutService.updateRejectBillDetailBack(list);
				}
			}
		}else{
			msg = "只有保存状态才能审核！";
		}
		
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

    @Override
	public boolean deleteSaleRejectEnterBySourceid(String sourceid)
			throws Exception {
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterBySourceid(sourceid);
		boolean flag = true;
		if(null==list || list.size()==0){
			return true;
		}
		for(SaleRejectEnter saleRejectEnter : list){
			if("1".equals(saleRejectEnter.getStatus())||"2".equals(saleRejectEnter.getStatus())){
			}else if("3".equals(saleRejectEnter.getStatus())||"4".equals(saleRejectEnter.getStatus())){
				boolean oppflag = oppauditSaleRejectEnter(saleRejectEnter.getId());
				if(!oppflag){
					flag = false;
					throw new Exception("销售退货入库单反审失败");
				}
			}
		}
		if(flag){
			for(SaleRejectEnter saleRejectEnter : list){
				int i = saleRejectEnterMapper.deleteSaleRejectEnterByID(saleRejectEnter.getId());
				saleRejectEnterMapper.deleteSaleRejectEnterDetailByBillid(saleRejectEnter.getId());
				flag = i>0;
			}
		}
		return flag;
	}
	@Override
	public boolean deleteSaleRejectEnterByRejectidAndReceiptid(String rejectid,String receiptid) throws Exception{
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterBySourceid(rejectid);
		boolean flag = true;
		if(null==list || list.size()==0){
			return true;
		}
		for(SaleRejectEnter saleRejectEnter : list){
			if("1".equals(saleRejectEnter.getStatus())||"2".equals(saleRejectEnter.getStatus())){
			}else if("3".equals(saleRejectEnter.getStatus())||"4".equals(saleRejectEnter.getStatus())){
				boolean oppflag = oppauditSaleRejectEnter(saleRejectEnter.getId(),receiptid);
				if(!oppflag){
					flag = false;
					throw new Exception("销售退货入库单反审失败");
				}
			}
		}
		if(flag){
			for(SaleRejectEnter saleRejectEnter : list){
				int i = saleRejectEnterMapper.deleteSaleRejectEnterByID(saleRejectEnter.getId());
				saleRejectEnterMapper.deleteSaleRejectEnterDetailByBillid(saleRejectEnter.getId());
				flag = i>0;
			}
		}
		return flag;
	}

    /**
     * 反审 更新销售退货入库单 以及库存信息
     * @param id
     * @return
     * @throws Exception
     */
    public boolean updateOppauditSaleRejectEnter(String id) throws Exception {
        boolean flag = false;
        boolean auditFlag = true;
        SaleRejectEnter saleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(id);
        List<SaleRejectEnterDetail> list = saleRejectEnterMapper.getSaleRejectEnterDetailList(id);
        for(SaleRejectEnterDetail saleRejectEnterDetail : list){
            GoodsInfo goodsInfo = getAllGoodsInfoByID(saleRejectEnterDetail.getGoodsid());
            //判断指定批次中商品库存是否充足
            if(StringUtils.isNotEmpty(saleRejectEnterDetail.getSummarybatchid())){
                StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleRejectEnterDetail.getSummarybatchid());
                if(null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(saleRejectEnterDetail.getUnitnum())!=-1){
                }else{
                    auditFlag = false;
                }
            }else{
                //判断指定批次中商品库存是否充足
                if("1".equals(goodsInfo.getIsbatch())){
                    StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(saleRejectEnter.getStorageid(), saleRejectEnterDetail.getGoodsid(), saleRejectEnterDetail.getProduceddate());
                    if(null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(saleRejectEnterDetail.getUnitnum())!=-1){
                    }else{
                        auditFlag = false;
                    }
                }else{
                    //判断商品在仓库中库存数量是否充足
                    StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(saleRejectEnter.getStorageid(), saleRejectEnterDetail.getGoodsid());
                    if(null!=storageSummary && storageSummary.getUsablenum().compareTo(saleRejectEnterDetail.getUnitnum())!=-1){
                    }else{
                        auditFlag = false;
                    }
                }
            }
        }
        if(auditFlag){
            for(SaleRejectEnterDetail saleRejectEnterDetail : list){
                StorageSummaryBatch storageSummaryBatch = new StorageSummaryBatch();
                storageSummaryBatch.setId(saleRejectEnterDetail.getSummarybatchid());
                storageSummaryBatch.setGoodsid(saleRejectEnterDetail.getGoodsid());
                //入库仓库
                storageSummaryBatch.setStorageid(saleRejectEnter.getStorageid());
                storageSummaryBatch.setStoragelocationid(saleRejectEnterDetail.getStoragelocationid());
                storageSummaryBatch.setBatchno(saleRejectEnterDetail.getBatchno());
                storageSummaryBatch.setProduceddate(saleRejectEnterDetail.getProduceddate());
                storageSummaryBatch.setDeadline(saleRejectEnterDetail.getDeadline());

//                storageSummaryBatch.setExistingnum(saleRejectEnterDetail.getUnitnum());
//                storageSummaryBatch.setUsablenum(saleRejectEnterDetail.getUnitnum());
//                storageSummaryBatch.setIntinum(saleRejectEnterDetail.getUnitnum());

                storageSummaryBatch.setUnitid(saleRejectEnterDetail.getUnitid());
                storageSummaryBatch.setUnitname(saleRejectEnterDetail.getUnitname());
                storageSummaryBatch.setAuxunitid(saleRejectEnterDetail.getAuxunitid());
                storageSummaryBatch.setAuxunitname(saleRejectEnterDetail.getAuxunitname());
                storageSummaryBatch.setPrice(saleRejectEnterDetail.getTaxprice());
                storageSummaryBatch.setAmount(saleRejectEnterDetail.getTaxamount());

//                storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));

                //销售退货入库 回滚 更新库存
                boolean rflag = rollbackStorageSummaryNum(storageSummaryBatch,saleRejectEnterDetail.getUnitnum(), "saleRejectEnter", id, "销售退货入库回滚");
                if(rflag){
                    //销售退货入库单反审时 可能反审时成本价与入库时的成本价不一致 需要重新调整
                    // 更新商品成本价
                    updateGoodsPriceBySubtract(saleRejectEnter.getId(),saleRejectEnterDetail.getId()+"",saleRejectEnter.getStorageid(),
                            saleRejectEnterDetail.getGoodsid(),
                            saleRejectEnterDetail.getUnitnum(),
                            saleRejectEnterDetail.getCostprice(), true);
                }
            }

            SysUser sysUser = getSysUser();
            int i = saleRejectEnterMapper.auditSaleRejectEnter(id,"2","0",sysUser.getUserid(), sysUser.getName(),getAuditBusinessdate(saleRejectEnter.getBusinessdate()));
            flag = i>0;
            if(flag){
                salesOutService.updateRejectBillDetailBack(list);
                salesOutService.updateRejectBillInvoice("0", null, saleRejectEnter.getSourceid());
                salesOutService.updateRejectBillInvoicebill("0", null, saleRejectEnter.getSourceid());
            }
        }
        return flag;
    }
	/**
	 * 反审销售退货入库单
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年2月2日
	 */
	public boolean oppauditSaleRejectEnter(String id) throws Exception {
		SaleRejectEnter saleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(id);
		boolean flag = false;
		if(null==saleRejectEnter){
		}else if("3".equals(saleRejectEnter.getStatus()) || "4".equals(saleRejectEnter.getStatus())){
			boolean auditFlag = true;
			RejectBill rejectBill = salesOutService.getRejectBill(saleRejectEnter.getSourceid());
			
			if(null!=rejectBill && ((!"3".equals(rejectBill.getIsinvoice()) && !"0".equals(rejectBill.getIsinvoice()))
                    ||(!"3".equals(rejectBill.getIsinvoicebill()) && !"0".equals(rejectBill.getIsinvoicebill()))) ){
				auditFlag = false;
			}else if(null!=rejectBill && !"".equals(rejectBill.getReceiptid())){
				auditFlag = false;
			}
			if(auditFlag){
                flag = updateOppauditSaleRejectEnter(id);
			}
		}else{
		}
		return flag;
	}
	/**
	 * 根据销售退货入库单编号和回单编号 反审销售退货入库单
	 * @param id
	 * @param receiptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年2月6日
	 */
	public boolean oppauditSaleRejectEnter(String id,String receiptid) throws Exception {
		SaleRejectEnter saleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(id);
		boolean flag = false;
		if(null!=saleRejectEnter && ("3".equals(saleRejectEnter.getStatus()) || "4".equals(saleRejectEnter.getStatus()))){
			boolean auditFlag = true;
			RejectBill rejectBill = salesOutService.getRejectBill(saleRejectEnter.getSourceid());
			if(null!=rejectBill && !"3".equals(rejectBill.getIsinvoice()) && !"0".equals(rejectBill.getIsinvoice())){
				auditFlag = false;
			}else if(null!=rejectBill && !"".equals(rejectBill.getReceiptid())){
				//判断销售退货入库单关联的回单编号是否一致
				if(!receiptid.equals(rejectBill.getReceiptid())){
					auditFlag = false;
				}
			}
			if(auditFlag){
                flag = updateOppauditSaleRejectEnter(id);
			}
		}
		return flag;
	}
	/**
	 * 根据回单列表 获取需要退货入库的数据
	 * 并且根据是否残次 生成不同仓库的数据
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 16, 2013
	 */
	public Map getEnterListByReceiptList(List<ReceiptDetail> list) throws Exception{
		//直退的商品 根据是否残次 分发到不同的仓库
		//残次 的退货到残次仓库 正常的退到默认仓库
		Map storageMap = new HashMap();
		for(ReceiptDetail receiptDetail : list){
			//发货数量>大于客户接收数量
			if(receiptDetail.getUnitnum().compareTo(receiptDetail.getReceiptnum())==1){
				String rejectDetailid = receiptDetail.getRejectdetailid();
				RejectBill rejectBill = getSalesOutService().getRejectBillByDetailid(rejectDetailid);
				String storageid = "";
				//判断是否残次
				if(null!=rejectBill && ("1".equals(rejectBill.getIsincomplete()) || (null!=rejectBill.getStorageid() && !"".equals(rejectBill.getStorageid())))){
					storageid = rejectBill.getStorageid();
				}else{
					GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail.getGoodsid());
					if(null!=goodsInfo){
						storageid = goodsInfo.getStorageid();
					}
				}
				//回单有退货的商品 生成退货入库单前 分配仓库
				if(storageMap.containsKey(storageid)){
					List<ReceiptDetail> storagelist = (List<ReceiptDetail>) storageMap.get(storageid);
					storagelist.add(receiptDetail);
					storageMap.put(storageid, storagelist);
				}else{
					List<ReceiptDetail> storagelist = new ArrayList<ReceiptDetail>();
					storagelist.add(receiptDetail);
					storageMap.put(storageid, storagelist);
				}
			}
		}
		
		return storageMap;
	}
	
	@Override
	public String addSaleRejectEnterByReceipt(Receipt receipt,
			List<ReceiptDetail> list) throws Exception {
		String ids = "";
		//获取回单中有直退的商品列表 并且根据是否残次 区别出不同仓库的商品
		Map storageMap = getEnterListByReceiptList(list);
		Set set = storageMap.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			String storageid = entry.getKey();
			List<ReceiptDetail> storageList = (List<ReceiptDetail>) storageMap.get(storageid);
			SaleRejectEnter saleRejectEnter = new SaleRejectEnter();
			if (isAutoCreate("t_storage_salereject_enter")) {
				// 获取自动编号
				String id = getAutoCreateSysNumbderForeign(saleRejectEnter, "t_storage_salereject_enter");
				saleRejectEnter.setId(id);
			}else{
				saleRejectEnter.setId("XSTH-"+CommonUtils.getDataNumberSendsWithRand());
			} 
			saleRejectEnter.setOrderid(receipt.getSaleorderid());
			saleRejectEnter.setStorageid(storageid);
			saleRejectEnter.setBusinessdate(CommonUtils.getTodayDataStr());
			saleRejectEnter.setCustomerid(receipt.getCustomerid());
			saleRejectEnter.setPcustomerid(receipt.getPcustomerid());
			saleRejectEnter.setSalesarea(receipt.getSalesarea());
			saleRejectEnter.setSalesdept(receipt.getSalesdept());
			saleRejectEnter.setSalesuser(receipt.getSalesuser());
			
			//来源类型为发货回单
			saleRejectEnter.setSourcetype("2");
			saleRejectEnter.setSourceid(receipt.getId());
			saleRejectEnter.setPaytype(receipt.getPaytype());
			saleRejectEnter.setSettletype(receipt.getSettletype());
			saleRejectEnter.setHandlerid(receipt.getHandlerid());
			saleRejectEnter.setStatus("2");
			
			saleRejectEnter.setAdddeptid(receipt.getAdddeptid());
			saleRejectEnter.setAdddeptname(receipt.getAdddeptname());
			saleRejectEnter.setAdduserid(receipt.getAdduserid());
			saleRejectEnter.setAddusername(receipt.getAddusername());
			saleRejectEnter.setIndooruserid(receipt.getIndooruserid());
			int addSize = 0;
			BigDecimal initsendamount = BigDecimal.ZERO;
			BigDecimal initsendcostamount = BigDecimal.ZERO;
			BigDecimal sendamount = BigDecimal.ZERO;
			BigDecimal sendnotaxamount = BigDecimal.ZERO;
			BigDecimal sendcostamount = BigDecimal.ZERO;
			for(ReceiptDetail receiptDetail : storageList){
				//发货数量>大于客户接收数量
				if(receiptDetail.getUnitnum().compareTo(receiptDetail.getReceiptnum())==1){
					SaleRejectEnterDetail saleRejectEnterDetail = new SaleRejectEnterDetail();
					saleRejectEnterDetail.setSalerejectid(saleRejectEnter.getId());
					saleRejectEnterDetail.setBillid(receipt.getId());
					saleRejectEnterDetail.setBilldetailid(receiptDetail.getId());
					saleRejectEnterDetail.setGoodsid(receiptDetail.getGoodsid());
					saleRejectEnterDetail.setCostprice(receiptDetail.getCostprice());
					saleRejectEnterDetail.setBrandid(receiptDetail.getBrandid());
					saleRejectEnterDetail.setBranduser(receiptDetail.getBranduser());
					//厂家业务员
					saleRejectEnterDetail.setSupplieruser(receiptDetail.getSupplieruser());
					saleRejectEnterDetail.setBranddept(receiptDetail.getBranddept());
					
					GoodsInfo goodsInfo = getAllGoodsInfoByID(receiptDetail.getGoodsid());
					if(null!=goodsInfo){
						saleRejectEnterDetail.setGoodssort(goodsInfo.getDefaultsort());
						saleRejectEnterDetail.setSupplierid(goodsInfo.getDefaultsupplier());
					}
					
					saleRejectEnterDetail.setStorageid(receipt.getStorageid());
					saleRejectEnterDetail.setUnitid(receiptDetail.getUnitid());
					saleRejectEnterDetail.setUnitname(receiptDetail.getUnitname());
					//退货数量 = 发货数量-客户接收数量
					saleRejectEnterDetail.setUnitnum(receiptDetail.getUnitnum().subtract(receiptDetail.getReceiptnum()));
					saleRejectEnterDetail.setAuxunitid(receiptDetail.getAuxunitid());
					saleRejectEnterDetail.setAuxunitname(receiptDetail.getAuxunitname());
					
					Map auxmap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), saleRejectEnterDetail.getUnitnum());
					saleRejectEnterDetail.setAuxnum(new BigDecimal( (String)auxmap.get("auxInteger")));
					saleRejectEnterDetail.setAuxremainder(new BigDecimal( (String)auxmap.get("auxremainder")));
					saleRejectEnterDetail.setAuxnumdetail((String) auxmap.get("auxnumdetail"));
					
					saleRejectEnterDetail.setTaxprice(receiptDetail.getTaxprice());
					//含税金额 = 单价*（发货数量-接收数量）
					BigDecimal taxamount=receiptDetail.getTaxprice().multiply(receiptDetail.getUnitnum().subtract(receiptDetail.getReceiptnum()));
					saleRejectEnterDetail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
					saleRejectEnterDetail.setNotaxprice(receiptDetail.getNotaxprice());
					saleRejectEnterDetail.setTaxtype(receiptDetail.getTaxtype());
					BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, saleRejectEnterDetail.getTaxtype());
					saleRejectEnterDetail.setNotaxamount(notaxamount);
					
					//税额 = 含税金额 - 无税金额
					saleRejectEnterDetail.setTax(saleRejectEnterDetail.getTaxamount().subtract(saleRejectEnterDetail.getNotaxamount()));
					saleRejectEnterDetail.setRemark(receiptDetail.getRemark());
					
					saleRejectEnterDetail.setBatchno(receiptDetail.getBatchno());
					
					int j = saleRejectEnterMapper.addSaleRejectEnterDetail(saleRejectEnterDetail);
					
					initsendamount = initsendamount.add(saleRejectEnterDetail.getTaxamount());
					initsendcostamount = initsendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					sendamount = sendamount.add(saleRejectEnterDetail.getTaxamount());
					sendnotaxamount = sendnotaxamount.add(saleRejectEnterDetail.getNotaxamount());
					sendcostamount = sendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					if(j>0){
						addSize ++;
					}
				}
			}
			//判断是否有退货商品
			if(addSize>0){
				SysUser sysUser = getSysUser();
//				saleRejectEnter.setInitsendamount(initsendamount);
//				saleRejectEnter.setInitsendcostamount(initsendcostamount);
				saleRejectEnter.setSendamount(sendamount);
				saleRejectEnter.setSendnotaxamount(sendnotaxamount);
				saleRejectEnter.setSendcostamount(sendcostamount);
//				saleRejectEnter.setCheckamount(sendamount);
//				saleRejectEnter.setChecknotaxamount(sendnotaxamount);
				int i = saleRejectEnterMapper.addSaleRejectEnter(saleRejectEnter);
				if(i>0){
					if("".equals(ids)){
						ids = saleRejectEnter.getId();
					}else{
						ids += ","+saleRejectEnter.getId();
					}
				}
			}
		}
		//更新销售发货回单参照状态
		salesOutService.updateReceiptRefer("1", receipt.getId());
		return ids;
	}
	@Override
	public Map isGoodsEnoughByOrderDetailLit(List<OrderDetail> list) throws Exception{
		List<OrderDetail> detailList = (List) CommonUtils.deepCopy(list);
		boolean flag = true;
		String msg = "";
		//循环合计存在相同商品的数据
		List<OrderDetail> distinctList = new ArrayList<OrderDetail>();
		Map map2 = new HashMap();
		Map<String, OrderDetail> map3 = new HashMap<String, OrderDetail>();
		for(OrderDetail orderDetail : detailList){
			if(map2.containsKey(orderDetail.getGoodsid())){
				BigDecimal unitnum = (BigDecimal)map2.get("unitnum_"+orderDetail.getGoodsid());
				if(null==unitnum){
					unitnum = BigDecimal.ZERO;
				}
				if(null!=orderDetail.getUnitnum()){
					map2.put("unitnum_"+orderDetail.getGoodsid(), unitnum.add(orderDetail.getUnitnum()));
					orderDetail.setUnitnum(unitnum.add(orderDetail.getUnitnum()));
				}else{
					map2.put("unitnum_"+orderDetail.getGoodsid(), unitnum);
					orderDetail.setUnitnum(unitnum);
				}
			}else{
				map2.put(orderDetail.getGoodsid(), orderDetail.getGoodsid());
				map2.put("unitnum_"+orderDetail.getGoodsid(), orderDetail.getUnitnum());
			}
			map3.put(orderDetail.getGoodsid(), orderDetail);
		}
		Iterator<Entry<String, OrderDetail>> iterator = map3.entrySet().iterator();
        while(iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                Object key = entry.getKey();
                OrderDetail orderBillDetail = (OrderDetail)map3.get(key);
                distinctList.add(orderBillDetail);
        }
		for(OrderDetail orderDetail : distinctList){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetail.getGoodsid());
			//判断商品指定库存批次 是否足够
			if(StringUtils.isNotEmpty(orderDetail.getSummarybatchid())){
				//判断指定仓库的商品是否库存足够
				Map returnMap = isSendGoodsBySummarybatchid(orderDetail.getGoodsid(),orderDetail.getSummarybatchid(),orderDetail.getBatchno(), orderDetail.getUnitnum());
				boolean sendflag = (Boolean) returnMap.get("flag");
				if(!sendflag){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(StringUtils.isNotEmpty(orderDetail.getStorageid())){
				//判断指定仓库的商品是否库存足够
				Map returnMap = isSendGoodsByStorageidAndGoodsid(orderDetail.getStorageid(),orderDetail.getGoodsid(), orderDetail.getUnitnum());
				boolean sendflag = (Boolean) returnMap.get("flag");
				if(!sendflag){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(null!=goodsInfo && orderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
				Map returnMap = isSendGoodsByGoodsid(orderDetail.getGoodsid(), orderDetail.getUnitnum());
				boolean sendFlag = (Boolean) returnMap.get("flag");
				if(sendFlag==false){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(null==orderDetail.getUnitnum() || orderDetail.getUnitnum().compareTo(BigDecimal.ZERO)==0){
				
			}else{
				flag = false;
				msg += "商品信息出错,未找到商品："+orderDetail.getGoodsid();
				break;
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	
	@Override
	public Map isGoodsEnoughByOrderDetailInStorage(String storageid,
			List<OrderDetail> detailList) throws Exception {
		boolean flag = true;
		String msg = "";
		for(OrderDetail orderDetailDetail : detailList){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(orderDetailDetail.getGoodsid());
			//判断商品指定库存批次 是否足够
			if(StringUtils.isNotEmpty(orderDetailDetail.getSummarybatchid())){
				//判断指定仓库的商品是否库存足够
				Map returnMap = isSendGoodsBySummarybatchid(orderDetailDetail.getGoodsid(),orderDetailDetail.getSummarybatchid(),orderDetailDetail.getBatchno(), orderDetailDetail.getUnitnum());
				boolean sendflag = (Boolean) returnMap.get("flag");
				if(!sendflag){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(null!=storageid && !"".equals(storageid)){
				//判断指定仓库的商品是否库存足够
				Map returnMap = isSendGoodsByStorageidAndGoodsid(storageid,orderDetailDetail.getGoodsid(), orderDetailDetail.getUnitnum());
				boolean sendflag = (Boolean) returnMap.get("flag");
				if(!sendflag){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(orderDetailDetail.getUnitnum().compareTo(BigDecimal.ZERO)==0){
				
			}else{
				flag = false;
				msg += "商品信息出错,未找到商品："+orderDetailDetail.getGoodsid();
				break;
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	@Override
	public Map isGoodsEnoughByDispatchBillDetail(
			List<DispatchBillDetail> list) throws Exception {
		List<DispatchBillDetail> detailList = (List) CommonUtils.deepCopy(list);
		boolean flag = true;
		String msg = "";
		//循环合计存在相同商品的数据
		List<DispatchBillDetail> distinctList = new ArrayList<DispatchBillDetail>();
		Map map2 = new HashMap();
		Map<String, DispatchBillDetail> map3 = new HashMap<String, DispatchBillDetail>();
		for(DispatchBillDetail dispatchBillDetail : detailList){
			String key = dispatchBillDetail.getGoodsid()+"_"+dispatchBillDetail.getStorageid();
			if(map2.containsKey(key)){
				BigDecimal unitnum = (BigDecimal)map2.get("unitnum_"+key);
				map2.put("unitnum_"+key, unitnum.add(dispatchBillDetail.getUnitnum()));
				dispatchBillDetail.setUnitnum(unitnum.add(dispatchBillDetail.getUnitnum()));
			}else{
				map2.put(key, dispatchBillDetail.getGoodsid());
				map2.put("unitnum_"+key, dispatchBillDetail.getUnitnum());
			}
			map3.put(key, dispatchBillDetail);
		}
		Iterator<Entry<String, DispatchBillDetail>> iterator = map3.entrySet().iterator();
        while(iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                Object key = entry.getKey();
                DispatchBillDetail dispatchBillDetail = (DispatchBillDetail)map3.get(key);
                distinctList.add(dispatchBillDetail);
        }
		for(DispatchBillDetail dispatchBillDetail : distinctList){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
			//判断商品指定库存批次 是否足够
			if(StringUtils.isNotEmpty(dispatchBillDetail.getSummarybatchid())){
				//判断指定仓库的商品是否库存足够
				Map returnMap = isSendGoodsBySummarybatchid(dispatchBillDetail.getGoodsid(),dispatchBillDetail.getSummarybatchid(),dispatchBillDetail.getBatchno(), dispatchBillDetail.getUnitnum());
				boolean sendflag = (Boolean) returnMap.get("flag");
				if(!sendflag){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
				//判断指定仓库的商品是否库存足够
				Map returnMap = isSendGoodsByStorageidAndGoodsid(dispatchBillDetail.getStorageid(),dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
				boolean sendflag = (Boolean) returnMap.get("flag");
				if(!sendflag){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(null!=goodsInfo && dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)==1){
				Map returnMap = isSendGoodsByGoodsid(dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
				boolean sendFlag = (Boolean) returnMap.get("flag");
				if(sendFlag==false){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)==0){
				
			}else{
				flag = false;
				msg += "商品信息出错,未找到商品："+dispatchBillDetail.getGoodsid();
				break;
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	@Override
	public Map isGoodsEnoughByDispatchBillDetailInStorage(String storageid,
			List<DispatchBillDetail> detailList) throws Exception {
		boolean flag = true;
		String msg = "";
		for(DispatchBillDetail dispatchBillDetail : detailList){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(dispatchBillDetail.getGoodsid());
			//判断商品指定库存批次 是否足够
			if(StringUtils.isNotEmpty(dispatchBillDetail.getSummarybatchid())){
				//判断指定仓库的商品是否库存足够
				Map returnMap = isSendGoodsBySummarybatchid(dispatchBillDetail.getGoodsid(),dispatchBillDetail.getSummarybatchid(),dispatchBillDetail.getBatchno(), dispatchBillDetail.getUnitnum());
				boolean sendflag = (Boolean) returnMap.get("flag");
				if(!sendflag){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(null!=storageid && !"".equals(storageid)){
				//判断指定仓库的商品是否库存足够
				Map returnMap = isSendGoodsByStorageidAndGoodsid(storageid,dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
				boolean sendflag = (Boolean) returnMap.get("flag");
				if(!sendflag){
					flag = false;
					msg += (String) returnMap.get("msg")+"</br>";
				}
			}else if(dispatchBillDetail.getUnitnum().compareTo(BigDecimal.ZERO)==0){
				
			}else{
				flag = false;
				msg += "商品信息出错,未找到商品："+dispatchBillDetail.getGoodsid();
				break;
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	@Override
	public boolean isGoodsEnoughByOrderDetail(
			OrderDetail orderDetail) throws Exception{
		boolean sendFlag = false;
		if(StringUtils.isNotEmpty(orderDetail.getSummarybatchid())){
			Map returnMap = isSendGoodsBySummarybatchid(orderDetail.getGoodsid(), orderDetail.getSummarybatchid(), orderDetail.getBatchno(), orderDetail.getUnitnum());
			sendFlag = (Boolean) returnMap.get("flag");
		}else if(StringUtils.isNotEmpty(orderDetail.getStorageid())){
			Map returnMap = isSendGoodsByStorageidAndGoodsid(orderDetail.getStorageid(),orderDetail.getGoodsid(), orderDetail.getUnitnum());
			sendFlag = (Boolean) returnMap.get("flag");
		}else{
			Map returnMap = isSendGoodsByGoodsid(orderDetail.getGoodsid(), orderDetail.getUnitnum());
			sendFlag = (Boolean) returnMap.get("flag");
		}
		return sendFlag;
	}
	@Override
	public boolean isGoodsEnoughByDispatchBillDetail(
			DispatchBillDetail dispatchBillDetail) throws Exception{
		boolean sendFlag = false;
		if(StringUtils.isNotEmpty(dispatchBillDetail.getSummarybatchid())){
			Map returnMap = isSendGoodsBySummarybatchid(dispatchBillDetail.getGoodsid(), dispatchBillDetail.getSummarybatchid(), dispatchBillDetail.getBatchno(), dispatchBillDetail.getUnitnum());
			sendFlag = (Boolean) returnMap.get("flag");
		}else if(StringUtils.isNotEmpty(dispatchBillDetail.getStorageid())){
			Map returnMap = isSendGoodsByStorageidAndGoodsid(dispatchBillDetail.getStorageid(),dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
			sendFlag = (Boolean) returnMap.get("flag");
		}else{
			Map returnMap = isSendGoodsByGoodsid(dispatchBillDetail.getGoodsid(), dispatchBillDetail.getUnitnum());
			sendFlag = (Boolean) returnMap.get("flag");
		}
		return sendFlag;
	}

	@Override
	public StorageSummary getStorageSummarySumByGoodsid(String goodsid)
			throws Exception {
		StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsid(goodsid);
		if(null!=storageSummary){
			StorageSummary outuseableStorageSummary = getStorageSummaryMapper().getStorageSummaryTransitnumSumByOutusable(goodsid);
			BigDecimal transitnum = BigDecimal.ZERO;
			if(null!=outuseableStorageSummary && null!=outuseableStorageSummary.getTransitnum()){
				transitnum = outuseableStorageSummary.getTransitnum();
			}
			storageSummary.setOutuseablenum(storageSummary.getUsablenum().add(transitnum));
		}
		return storageSummary;
	}
	@Override
	public StorageSummary getStorageSummarySumByGoodsidAndStorageid(
			String goodsid, String storageid) throws Exception {
		StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummaryInfoByGoodsidAndStorageid(goodsid, storageid);
		StorageInfo storageInfo = getStorageInfoByID(storageid);
		if(null!=storageSummary){
			if(null!=storageInfo && "1".equals(storageInfo.getIssendusable())){
				storageSummary.setOutuseablenum(storageSummary.getUsablenum().add(storageSummary.getTransitnum()));
			}else{
				storageSummary.setOutuseablenum(storageSummary.getUsablenum());
			}
		}
		return storageSummary;
	}
	@Override
	public List getStorageSummarySumByBarcode(String barcode, String goodsid)
			throws Exception {
		List list = getStorageSummaryMapper().getStorageSummarySumByBarcode(barcode, goodsid);
		return list;
	}

	@Override
	public Map isGoodsEnoughByOrdercar(OrderCar orderCar,
			List<OrderCarDetail> detailList) throws Exception {
		boolean flag = true;
		String msg = "";
		for(OrderCarDetail orderCarDetail : detailList){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(orderCarDetail.getGoodsid());
			if(null!=goodsInfo){
				Map returnMap = isSendGoodsByStorageidAndGoodsid(orderCar.getStorageid(),orderCarDetail.getGoodsid(), orderCarDetail.getUnitnum());
				boolean sendFlag = (Boolean) returnMap.get("flag");
				if(sendFlag==false){
					flag = false;
					msg += "单据："+orderCar.getId()+(String) returnMap.get("msg")+"</br>";
				}
			}else{
				flag = false;
				msg += "商品信息出错,未找到商品："+orderCarDetail.getGoodsid();
				break;
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}

	@Override
	public boolean updateSaleRejectEnterCheckByReceipt(Receipt receipt,List<ReceiptDetail> receiptDetailList) throws Exception {
		List<RejectBill> rejectBillList = salesOutService.getRejectBillByReceiptid(receipt.getId());
		boolean flag = false;
		for(RejectBill rejectBill : rejectBillList){
			if(null!=rejectBill){
				salesOutService.updateRejectBillClose(rejectBill.getId());
                salesOutService.updateRejectBillDuefromdate(rejectBill.getId(),receipt.getDuefromdate());
				int i = saleRejectEnterMapper.updateSaleRejectEnterCheckByRejectbillid(rejectBill.getId(),receipt.getDuefromdate());
				if(null!=receipt){
					saleRejectEnterMapper.updateSaleRejectEnterReceiptid(receipt.getId());
				}
				flag = i>0;
				for(ReceiptDetail receiptDetail : receiptDetailList){
					//发货数量>大于客户接收数量
					if(receiptDetail.getUnitnum().compareTo(receiptDetail.getReceiptnum())==1){
						if(StringUtils.isNotEmpty(receiptDetail.getRejectbillid()) && StringUtils.isNotEmpty(receiptDetail.getRejectdetailid())){
							String[] rejectDetailArr = receiptDetail.getRejectdetailid().split(",");
							String[] rejectidArr = receiptDetail.getRejectbillid().split(",");
							for(int j=0;j<rejectDetailArr.length; j++){
								String rejectdetailid = rejectDetailArr[j];
								String rejectid = rejectidArr[j];
								//更新销售退货通知单 直退关联数量
								RejectBillDetail detail = salesOutService.getRejectBillDetail(rejectdetailid);
								detail.setTaxprice(receiptDetail.getTaxprice());
								BigDecimal taxamount=detail.getTaxprice().multiply(detail.getUnitnum());
								detail.setTaxamount(taxamount.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
								detail.setNotaxprice(receiptDetail.getNotaxprice());
								BigDecimal notaxamount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
								detail.setNotaxamount(notaxamount);
                                detail.setDuefromdate(receiptDetail.getDuefromdate());
								//更新销售退货通知单明细
								salesOutService.updateRejectBillDetailBack(detail);
								//更新相关的回单编号与回单明细编号
								detail.setReceiptid(receiptDetail.getBillid());
								detail.setReceiptdetailid(receiptDetail.getId());
								//更新销售退货入库单明细
								saleRejectEnterMapper.updateSaleRejectEnterDetailByRejectDetail(detail);
							}
						}
					}
				}
				//更新直退单据 单据金额
				updateSaleRejectEnterAmount(rejectBill.getId());
			}
		}
		return flag;
	}

	@Override
	public boolean updateSaleRejectEnter(SaleRejectEnter saleRejectEnter) throws Exception {
		int i = saleRejectEnterMapper.updateSaleRejectEnterCustomeridBySourceid(saleRejectEnter);
		return i>0;
	}

	@Override
	public boolean updateSaleRejectEnterDetailIDs(String oldbillid,
			String oldbilldetailid, String newbillid, String newbilldetailid)
			throws Exception {
		int i = saleRejectEnterMapper.updateSaleRejectEnterDetailIDs(oldbillid, oldbilldetailid, newbillid, newbilldetailid);
		return i>0;
	}

	@Override
	public boolean updateSaleRejectEnterCheckByReceiptOpen(Receipt receipt)
			throws Exception {
		List<RejectBill> rejectBillList = salesOutService.getRejectBillByReceiptid(receipt.getId());
		for(RejectBill rejectBill : rejectBillList){
			if(null!=rejectBill){
				salesOutService.updateRejectBillOpen(rejectBill.getId());
				//取消退货入库单验收
				int i = saleRejectEnterMapper.updateSaleRejectEnterCheckCancelByRejectbillid(rejectBill.getId());
			}
		}
		return true;
	}

	@Override
	public boolean updateSaleRejectEnterDetailByRejectDetail(
			RejectBillDetail rejectBillDetail) throws Exception{
		int i = saleRejectEnterMapper.updateSaleRejectEnterDetailByRejectDetail(rejectBillDetail);
		return i>0;
	}

	@Override
	public boolean updateSaleRejectEnterAmount(String rejectid)
			throws Exception {
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(rejectid);
		if(null!=list && list.size()>0){
			for(SaleRejectEnter saleRejectEnter : list){
				List<SaleRejectEnterDetail> detailList = saleRejectEnterMapper.getSaleRejectEnterDetailList(saleRejectEnter.getId());
				if(null!=detailList && detailList.size()>0){
					BigDecimal initsendamount = BigDecimal.ZERO;
					BigDecimal initsendcostamount = BigDecimal.ZERO;
					BigDecimal sendamount = BigDecimal.ZERO;
					BigDecimal sendnotaxamount = BigDecimal.ZERO;
					BigDecimal sendcostamount = BigDecimal.ZERO;
					for(SaleRejectEnterDetail saleRejectEnterDetail : detailList){
						initsendamount = initsendamount.add(saleRejectEnterDetail.getTaxamount());
						initsendcostamount = initsendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
						sendamount = sendamount.add(saleRejectEnterDetail.getTaxamount());
						sendnotaxamount = sendnotaxamount.add(saleRejectEnterDetail.getNotaxamount());
						sendcostamount = sendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					}
					saleRejectEnter.setInitsendamount(initsendamount);
					saleRejectEnter.setInitsendcostamount(initsendcostamount);
					saleRejectEnter.setSendamount(sendamount);
					saleRejectEnter.setSendnotaxamount(sendnotaxamount);
					saleRejectEnter.setSendcostamount(sendcostamount);
					saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
				}else{
					//明细被拆分完后 删除销售退货入库单
					saleRejectEnterMapper.deleteSaleRejectEnterByID(saleRejectEnter.getId());
				}
			}
		}
		return true;
	}

	@Override
	public boolean deleteSaleRejectEnterDetailByRejectDetail(
			RejectBillDetail rejectBillDetail) throws Exception {
		int i = saleRejectEnterMapper.deleteSaleRejectEnterDetailByRejectDetail(rejectBillDetail.getBillid(),rejectBillDetail.getId());
		return i>0;
	}

	@Override
	public List getSaleRejectEnterListByRejectid(String rejectid)
			throws Exception {
		List list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(rejectid);
		return list;
	}

	@Override
	public boolean updateSaleRejectEnterCheckByReject(RejectBill rejectBill)
			throws Exception {
		int i = saleRejectEnterMapper.updateSaleRejectEnterCheckByReject(rejectBill);
		SysUser sysUser = getSysUser();
		if(null==sysUser){
			sysUser = getSysUserById(rejectBill.getAdduserid());
		}
		if(null!=rejectBill){
			List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(rejectBill.getId());
			if(null!=list && list.size()>0){
				for(SaleRejectEnter saleRejectEnter : list){
					List<SaleRejectEnterDetail> detailList = saleRejectEnterMapper.getSaleRejectEnterDetailList(saleRejectEnter.getId());
					if(null!=detailList && detailList.size()>0){
						BigDecimal initsendamount = BigDecimal.ZERO;
						BigDecimal initsendcostamount = BigDecimal.ZERO;
						BigDecimal sendamount = BigDecimal.ZERO;
						BigDecimal sendnotaxamount = BigDecimal.ZERO;
						BigDecimal sendcostamount = BigDecimal.ZERO;
						BigDecimal checkamount = BigDecimal.ZERO;
						BigDecimal checknotaxamount = BigDecimal.ZERO;
						for(SaleRejectEnterDetail saleRejectEnterDetail : detailList){
							initsendamount = initsendamount.add(saleRejectEnterDetail.getTaxamount());
							initsendcostamount = initsendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
							sendamount = sendamount.add(saleRejectEnterDetail.getTaxamount());
							sendnotaxamount = sendnotaxamount.add(saleRejectEnterDetail.getNotaxamount());
							sendcostamount = sendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
							checkamount = checkamount.add(saleRejectEnterDetail.getTaxamount());
							checknotaxamount =  checknotaxamount.add(saleRejectEnterDetail.getNotaxamount());
						}
						saleRejectEnter.setInitsendamount(initsendamount);
						saleRejectEnter.setInitsendcostamount(initsendcostamount);
						saleRejectEnter.setSendamount(sendamount);
						saleRejectEnter.setSendnotaxamount(sendnotaxamount);
						saleRejectEnter.setSendcostamount(sendcostamount);
						if("2".equals(rejectBill.getBilltype())){
							saleRejectEnter.setCheckamount(checkamount);
							saleRejectEnter.setChecknotaxamount(checknotaxamount);
							saleRejectEnter.setCheckdate(getAuditBusinessdate(rejectBill.getBusinessdate()));
							saleRejectEnter.setCheckuserid(sysUser.getUserid());
							saleRejectEnter.setCheckusername(sysUser.getName());
						}
                        //销售区域
                        saleRejectEnter.setSalesdept(rejectBill.getSalesdept());
                        saleRejectEnter.setSalesuser(rejectBill.getSalesuser());
                        Customer customer = getCustomerByID(rejectBill.getCustomerid());
                        if (null != customer) {
                            saleRejectEnter.setSalesarea(customer.getSalesarea());
                            saleRejectEnter.setPcustomerid(customer.getPid());
                            saleRejectEnter.setCustomersort(customer.getCustomersort());
                            saleRejectEnter.setIndooruserid(customer.getIndoorstaff());
                        }
                        saleRejectEnter.setDuefromdate(rejectBill.getDuefromdate());
                        saleRejectEnter.setSalestype(rejectBill.getSalestype());
						saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
					}else{
						//明细被拆分完后 删除销售退货入库单
						saleRejectEnterMapper.deleteSaleRejectEnterByID(saleRejectEnter.getId());
					}
				}
			}
		}
		return i>0;
	}
	@Override
	public boolean updateSaleRejectEnterCheckCancelByReject(String rejectid)
			throws Exception {
		List<SaleRejectEnter> list = saleRejectEnterMapper.getSaleRejectEnterListBySalerejectid(rejectid);
		if(null!=list && list.size()>0){
			for(SaleRejectEnter saleRejectEnter : list){
				saleRejectEnter.setCheckamount(BigDecimal.ZERO);
				saleRejectEnter.setChecknotaxamount(BigDecimal.ZERO);
				saleRejectEnter.setCheckdate("");
				saleRejectEnter.setCheckuserid("");
				saleRejectEnter.setCheckusername("");
				saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
			}
		}
		return saleRejectEnterMapper.updateSaleRejectEnterChecCancelkByRejectid(rejectid)>0;
	}
	@Override
	public boolean updateSaleOutDueformdateByReceipt(Receipt receipt)
			throws Exception {
		int i = getSaleoutMapper().updateSaleOutDuefromdateByOrderid(receipt.getSaleorderid(), null,receipt.getDuefromdate());
		return i>0;
	}

	@Override
	public boolean deleteSaleOutByOrderid(String orderid) throws Exception {
		List<Saleout> list = getSaleoutMapper().getSaleOutInfoBySaleorderid(orderid);
		for(Saleout saleout : list){
			if(null==saleout.getInvoiceid() || "".equals(saleout.getInvoiceid())){
				updateSaleOutOpen(saleout.getId());
				//反审发货单 更新库存
				Map map = oppauditSaleOut(saleout.getId());
				boolean flag = (Boolean) map.get("flag");
				if(!flag){
					throw new Exception("直营销售单反审失败");
				}else{
					boolean delflag = deleteSaleOut(saleout.getId());
					if(!delflag){
						throw new Exception("直营销售单反审失败");
					}
				}
			}else{
				throw new Exception("直营销售单反审失败,回单已开票");
			}
		}
		return true;
	}

	@Override
	public List getStorageSummarySumByBarcodeInStorageid(String barcode,
			String goodsid, String storageid) throws Exception {
		List list = getStorageSummaryMapper().getStorageSummarySumByBarcodeInStorageid(barcode, goodsid,storageid);
		return list;
	}

	@Override
	public boolean doCheckSaleoutIsbigsaleout(String dispatchbillid)throws Exception{
		boolean flag = saleoutMapper.doCheckSaleoutIsbigsaleout(dispatchbillid) > 0;
		return flag;
	}

	@Override
	public boolean doCheckSaleoutIsbigsaleoutByOrderid(String orderid) throws Exception {
		boolean flag = saleoutMapper.doCheckSaleoutIsbigsaleoutByOrderid(orderid) > 0;
		return flag;
	}
}

