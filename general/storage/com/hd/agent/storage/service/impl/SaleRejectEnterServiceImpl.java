/**
 * @(#)SaleRejectEnterServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 15, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.dao.RejectBillMapper;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.sales.model.RejectBill;
import com.hd.agent.sales.service.ISalesOutService;
import com.hd.agent.storage.dao.SaleRejectEnterMapper;
import com.hd.agent.storage.dao.SaleoutMapper;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.storage.model.SaleRejectEnterDetail;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.ISaleRejectEnterService;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 销售退货入库单service实现类
 * @author chenwei
 */
public class SaleRejectEnterServiceImpl extends BaseStorageServiceImpl
		implements ISaleRejectEnterService {
	
	private SaleRejectEnterMapper saleRejectEnterMapper;

    private SaleoutMapper saleoutMapper;

	private RejectBillMapper salesRejectBillMapper;
	/**
	 * 销售模块接口
	 */
	private ISalesOutService salesOutService;
	
	public SaleRejectEnterMapper getSaleRejectEnterMapper() {
		return saleRejectEnterMapper;
	}

	public void setSaleRejectEnterMapper(SaleRejectEnterMapper saleRejectEnterMapper) {
		this.saleRejectEnterMapper = saleRejectEnterMapper;
	}
	
	public ISalesOutService getSalesOutService() {
		return salesOutService;
	}

	public void setSalesOutService(ISalesOutService salesOutService) {
		this.salesOutService = salesOutService;
	}

    public SaleoutMapper getSaleoutMapper() {
        return saleoutMapper;
    }

    public void setSaleoutMapper(SaleoutMapper saleoutMapper) {
        this.saleoutMapper = saleoutMapper;
    }

	public RejectBillMapper getSalesRejectBillMapper() {
		return salesRejectBillMapper;
	}

	public void setSalesRejectBillMapper(RejectBillMapper salesRejectBillMapper) {
		this.salesRejectBillMapper = salesRejectBillMapper;
	}

	@Override
	public boolean addSaleRejectEnter(SaleRejectEnter saleRejectEnter,
			List<SaleRejectEnterDetail> detailList) throws Exception{
		if (isAutoCreate("t_storage_salereject_enter")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(saleRejectEnter, "t_storage_salereject_enter");
			saleRejectEnter.setId(id);
		}else{
			saleRejectEnter.setId("XSTH-"+CommonUtils.getDataNumberSendsWithRand());
		} 
		//获取销售区域
		Customer customer = getCustomerByID(saleRejectEnter.getCustomerid());
		if(null!=customer){
			saleRejectEnter.setSalesarea(customer.getSalesarea());
			saleRejectEnter.setPcustomerid(customer.getPid());
		}
		//添加销售退货出库单明细
		for(SaleRejectEnterDetail saleRejectEnterDetail:detailList){
			saleRejectEnterDetail.setSalerejectid(saleRejectEnter.getId());
			saleRejectEnterDetail.setStorageid(saleRejectEnter.getStorageid());
			GoodsInfo goodsInfo = getAllGoodsInfoByID(saleRejectEnterDetail.getGoodsid());
			if(null!=goodsInfo){
				saleRejectEnterDetail.setBrandid(goodsInfo.getBrand());
				//成本价
				saleRejectEnterDetail.setCostprice(getGoodsCostprice(saleRejectEnter.getStorageid(),goodsInfo));
				saleRejectEnterDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), saleRejectEnter.getCustomerid()));
				//厂家业务员
				saleRejectEnterDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), saleRejectEnter.getCustomerid()));
				//获取品牌部门
				Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
				if(null!=brand){
					saleRejectEnterDetail.setBranddept(brand.getDeptid());
				}
				//成本价
				saleRejectEnterDetail.setCostprice(getGoodsCostprice(saleRejectEnter.getStorageid(),goodsInfo));
				//实际成本价 不包括核算成本价
				saleRejectEnterDetail.setRealcostprice(goodsInfo.getNewstorageprice());
			}
			saleRejectEnterMapper.addSaleRejectEnterDetail(saleRejectEnterDetail);
		}
		//添加采购入库单
		SysUser sysUser = getSysUser();
		saleRejectEnter.setAdddeptid(sysUser.getDepartmentid());
		saleRejectEnter.setAdddeptname(sysUser.getDepartmentname());
		saleRejectEnter.setAdduserid(sysUser.getUserid());
		saleRejectEnter.setAddusername(sysUser.getName());
		int i = saleRejectEnterMapper.addSaleRejectEnter(saleRejectEnter);
		return i>0;
	}

	@Override
	public Map getSaleRejectEnter(String id) throws Exception {
		SaleRejectEnter saleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(id);
		if(null!=saleRejectEnter){
			Customer customer = getCustomerByID(saleRejectEnter.getCustomerid());
			if(null!=customer){
				saleRejectEnter.setCustomername(customer.getName());
			}
			Contacter contacter = getContacterById(saleRejectEnter.getHandlerid());
			if(null!=contacter){
				saleRejectEnter.setHandlername(contacter.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(saleRejectEnter.getSalesdept());
			if(null!=departMent){
				saleRejectEnter.setSalesdeptname(departMent.getName());
			}
			Personnel driver = getPersonnelById(saleRejectEnter.getDriverid());
			if(null!=driver){
				saleRejectEnter.setDrivername(driver.getName());
			}
			Personnel personnel = getPersonnelById(saleRejectEnter.getSalesuser());
			if(null!=personnel){
				saleRejectEnter.setSaleusername(personnel.getName());
			}
			StorageInfo billstorageInfo = getStorageInfoByID(saleRejectEnter.getStorageid());
			if(null!=billstorageInfo){
				saleRejectEnter.setStoragename(billstorageInfo.getName());
			}
		}
		List<SaleRejectEnterDetail> detailList = saleRejectEnterMapper.getSaleRejectEnterDetailList(id);
		for(SaleRejectEnterDetail saleRejectEnterDetail : detailList){
			GoodsInfo goodsInfo = getGoodsInfoByID(saleRejectEnterDetail.getGoodsid());
			saleRejectEnterDetail.setGoodsInfo(goodsInfo);
			StorageInfo storageInfo = getStorageInfoByID(saleRejectEnterDetail.getStorageid());
			if(null!=storageInfo){
				saleRejectEnterDetail.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocation(saleRejectEnterDetail.getStoragelocationid());
			if(null!=storageLocation){
				saleRejectEnterDetail.setStoragelocationname(storageLocation.getName());
			}
			TaxType taxType = getTaxType(saleRejectEnterDetail.getTaxtype());
			if(null!=taxType){
				saleRejectEnterDetail.setTaxtypename(taxType.getName());
			}
		}
		Map map = new HashMap();
		map.put("saleRejectEnter", saleRejectEnter);
		map.put("detailList", detailList);
		return map;
	}

	@Override
	public PageData showSaleRejectEnterList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_salereject_enter", "t");
		pageMap.setDataSql(dataSql);
		
		List<SaleRejectEnter> list = saleRejectEnterMapper.showSaleRejectEnterList(pageMap);
		for(SaleRejectEnter saleRejectEnter : list){
			Personnel indoorPerson = getPersonnelById(saleRejectEnter.getIndooruserid());
			if(null!=indoorPerson){
				saleRejectEnter.setIndoorusername(indoorPerson.getName());
			}
			StorageInfo storageInfo = getStorageInfoByID(saleRejectEnter.getStorageid());
			if(null!=storageInfo){
				saleRejectEnter.setStoragename(storageInfo.getName());
			}
			DepartMent departMent = getDepartMentById(saleRejectEnter.getSalesdept());
			if(null!=departMent){
				saleRejectEnter.setSalesdeptname(departMent.getName());
			}
			Personnel driver = getPersonnelById(saleRejectEnter.getDriverid());
			if(null!=driver){
				saleRejectEnter.setDrivername(driver.getName());
			}
			Personnel personnel = getPersonnelById(saleRejectEnter.getSalesuser());
			if(null!=personnel){
				saleRejectEnter.setSaleusername(personnel.getName());
			}
			Customer customer = getCustomerByID(saleRejectEnter.getCustomerid());
			if(null!=customer){
				saleRejectEnter.setCustomername(customer.getName());
			}
			Contacter contacter = getContacterById(saleRejectEnter.getHandlerid());
			if(null!=contacter){
				saleRejectEnter.setHandlerid(contacter.getName());
			}
			BigDecimal taxamount = saleRejectEnterMapper.showSaleRejectEnterSum(saleRejectEnter.getId());
			saleRejectEnter.setTotalamount(taxamount);

			Personnel storager=getBaseFilesPersonnelMapper().getPersonnelInfo(saleRejectEnter.getStorager());
			if(storager!=null){
				saleRejectEnter.setStoragername(storager.getName());
			}
		}
		PageData pageData = new PageData(saleRejectEnterMapper.showSaleRejectEnterCount(pageMap),list,pageMap);
		BigDecimal totalamount = saleRejectEnterMapper.showSaleRejectEnterSumByQuery(pageMap);
		List footer = new ArrayList();
		Map map = new HashMap();
		map.put("id", "合计");
		map.put("totalamount", totalamount);
		footer.add(map);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public boolean deleteSaleRejectEnter(String id) throws Exception {
		SaleRejectEnter saleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(id);
		if(null!=saleRejectEnter && "2".equals(saleRejectEnter.getStatus())){
			//来源类型为销售退货通知单时
			if("1".equals(saleRejectEnter.getSourcetype()) ){
				salesOutService.updateRejectBillRefer("0", saleRejectEnter.getSourceid());
				int i = saleRejectEnterMapper.deleteSaleRejectEnterByID(id);
				saleRejectEnterMapper.deleteSaleRejectEnterDetailByBillid(id);
				return i>0;
			}else if("2".equals(saleRejectEnter.getSourcetype()) ){
				//来源类型为销售发货回单时
				salesOutService.updateReceiptRefer("0", saleRejectEnter.getSourceid());
				int i = saleRejectEnterMapper.deleteSaleRejectEnterByID(id);
				saleRejectEnterMapper.deleteSaleRejectEnterDetailByBillid(id);
				return i>0;
			}else{
				//来源类型无时
				int i = saleRejectEnterMapper.deleteSaleRejectEnterByID(id);
				saleRejectEnterMapper.deleteSaleRejectEnterDetailByBillid(id);
				return i>0;
			}
		}else if(null!=saleRejectEnter && "1".equals(saleRejectEnter.getStatus())){
			int i = saleRejectEnterMapper.deleteSaleRejectEnterByID(id);
			saleRejectEnterMapper.deleteSaleRejectEnterDetailByBillid(id);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public Map auditSaleRejectEnter(String id) throws Exception {
        //多少天之内或多少月之内的最低销售价；
        String rejectCustomerGoodsPriceInMonth = getSysParamValue("RejectCustomerGoodsPriceInMonth");
        int month = 3;
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(rejectCustomerGoodsPriceInMonth)){
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
					storageSummaryBatch.setId(saleRejectEnterDetail.getSummarybatchid());
					storageSummaryBatch.setGoodsid(saleRejectEnterDetail.getGoodsid());
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
							//更新仓库商品成本价
							updateStoragePriceByAdd(saleRejectEnter.getStorageid(),saleRejectEnterDetail.getGoodsid(),saleRejectEnterDetail.getUnitnum(),saleRejectEnterDetail.getCostprice(), true, false,false,saleRejectEnter.getId(),saleRejectEnterDetail.getId()+"");
						}
					}
				}
				
				SysUser sysUser = getSysUser();
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
	public Map oppauditSaleRejectEnter(String id) throws Exception {
		SaleRejectEnter saleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(id);
		boolean flag = false;
		String msg = "";
		if(null==saleRejectEnter){
			msg = "单据不存在！";
		}else if("3".equals(saleRejectEnter.getStatus()) || "4".equals(saleRejectEnter.getStatus())){
			boolean auditFlag = true;
			RejectBill rejectBill = salesOutService.getRejectBill(saleRejectEnter.getSourceid());
			
			if(null!=rejectBill && ((!"3".equals(rejectBill.getIsinvoice()) && !"0".equals(rejectBill.getIsinvoice())) ||
                    (!"3".equals(rejectBill.getIsinvoicebill()) && !"0".equals(rejectBill.getIsinvoicebill())))){
				auditFlag = false;
				msg += "来源单据已开票。不能反审";
			}else if(null!=rejectBill && !"".equals(rejectBill.getReceiptid())){
				auditFlag = false;
				msg += "退货通知单已关联回单，不能反审。";
			}
			if(auditFlag){
				List<SaleRejectEnterDetail> list = saleRejectEnterMapper.getSaleRejectEnterDetailList(id);
				for(SaleRejectEnterDetail saleRejectEnterDetail : list){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(saleRejectEnterDetail.getGoodsid());
					if(StringUtils.isNotEmpty(saleRejectEnterDetail.getSummarybatchid())){
						StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchById(saleRejectEnterDetail.getSummarybatchid());
						if(null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(saleRejectEnterDetail.getUnitnum())!=-1){
						}else{
							auditFlag = false;
							msg += "商品:"+goodsInfo.getId()+goodsInfo.getName()+"在指定批次中数量不足\n";
						}
					}else{
						if("1".equals(goodsInfo.getIsbatch())){
							StorageSummaryBatch storageSummaryBatch = getStorageSummaryBatchByStorageidAndProduceddate(saleRejectEnter.getStorageid(), saleRejectEnterDetail.getGoodsid(), saleRejectEnterDetail.getProduceddate());
							if(null!=storageSummaryBatch && storageSummaryBatch.getUsablenum().compareTo(saleRejectEnterDetail.getUnitnum())!=-1){
							}else{
								auditFlag = false;
								msg += "商品:"+goodsInfo.getId()+goodsInfo.getName()+"在指定批次中数量不足\n";
							}
						}else{
							StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(saleRejectEnter.getStorageid(), saleRejectEnterDetail.getGoodsid());
							if(null!=storageSummary && storageSummary.getUsablenum().compareTo(saleRejectEnterDetail.getUnitnum())!=-1){
							}else{
								auditFlag = false;
								msg += "商品:"+goodsInfo.getId()+goodsInfo.getName()+"在指定仓库中数量不足\n";
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
						
//						storageSummaryBatch.setExistingnum(saleRejectEnterDetail.getUnitnum());
//						storageSummaryBatch.setUsablenum(saleRejectEnterDetail.getUnitnum());
//						storageSummaryBatch.setIntinum(saleRejectEnterDetail.getUnitnum());
						
						storageSummaryBatch.setUnitid(saleRejectEnterDetail.getUnitid());
						storageSummaryBatch.setUnitname(saleRejectEnterDetail.getUnitname());
						storageSummaryBatch.setAuxunitid(saleRejectEnterDetail.getAuxunitid());
						storageSummaryBatch.setAuxunitname(saleRejectEnterDetail.getAuxunitname());
						storageSummaryBatch.setPrice(saleRejectEnterDetail.getTaxprice());
						storageSummaryBatch.setAmount(saleRejectEnterDetail.getTaxamount());
						
//						storageSummaryBatch.setEnterdate(CommonUtils.dataToStr(new Date(), "yyyy-MM-dd"));
						
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
						int noauditcount = saleRejectEnterMapper.getSaleRejectEnterNoAuditCount(saleRejectEnter.getSourceid());
						salesOutService.updateRejectBillInvoice("0", null, saleRejectEnter.getSourceid());
						salesOutService.updateRejectBillInvoicebill("0", null, saleRejectEnter.getSourceid());
					}
				}
			}
		}else{
			msg = "只有审核通过或者关闭的单据才能反审！";
		}
		
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("msg", msg);
		return map;
	}
	@Override
	public boolean editSaleRejectEnter(SaleRejectEnter saleRejectEnter,
			List<SaleRejectEnterDetail> detailList) throws Exception {
		SaleRejectEnter oldSaleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(saleRejectEnter.getId());
		if(null==oldSaleRejectEnter || "3".equals(oldSaleRejectEnter.getStatus()) || "4".equals(oldSaleRejectEnter.getStatus())){
			return false;
		}
		SysUser sysUser = getSysUser();
		saleRejectEnter.setModifyuserid(sysUser.getUserid());
		saleRejectEnter.setModifyusername(sysUser.getName());
		Customer customer = getCustomerByID(saleRejectEnter.getCustomerid());
		if(null!=customer){
			saleRejectEnter.setSalesarea(customer.getSalesarea());
			saleRejectEnter.setSalesdept(customer.getSalesdeptid());
			saleRejectEnter.setPcustomerid(customer.getPid());
			saleRejectEnter.setCustomersort(customer.getCustomersort());
		}
		saleRejectEnterMapper.deleteSaleRejectEnterDetailByBillid(saleRejectEnter.getId());
		BigDecimal initsendamount = BigDecimal.ZERO;
		BigDecimal initsendcostamount = BigDecimal.ZERO;
		BigDecimal sendamount = BigDecimal.ZERO;
		BigDecimal sendnotaxamount = BigDecimal.ZERO;
		BigDecimal sendcostamount = BigDecimal.ZERO;
		for(SaleRejectEnterDetail saleRejectEnterDetail : detailList){
			saleRejectEnterDetail.setSalerejectid(saleRejectEnter.getId());
			saleRejectEnterDetail.setStorageid(saleRejectEnter.getStorageid());
			GoodsInfo goodsInfo = getAllGoodsInfoByID(saleRejectEnterDetail.getGoodsid());
			if(null!=goodsInfo){
				saleRejectEnterDetail.setBrandid(goodsInfo.getBrand());
				//成本价
				saleRejectEnterDetail.setCostprice(getGoodsCostprice(saleRejectEnter.getStorageid(),goodsInfo));
				saleRejectEnterDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), saleRejectEnter.getCustomerid()));
				//厂家业务员
				saleRejectEnterDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), saleRejectEnter.getCustomerid()));
				//获取品牌部门
				Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
				if(null!=brand){
					saleRejectEnterDetail.setBranddept(brand.getDeptid());
				}
				//成本价
				saleRejectEnterDetail.setCostprice(getGoodsCostprice(saleRejectEnter.getStorageid(),goodsInfo));
				//实际成本价 商品总成本价
				saleRejectEnterDetail.setRealcostprice(goodsInfo.getNewstorageprice());
			}
			saleRejectEnterMapper.addSaleRejectEnterDetail(saleRejectEnterDetail);
			initsendamount = initsendamount.add(saleRejectEnterDetail.getTaxamount());
			initsendcostamount = initsendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			sendamount = sendamount.add(saleRejectEnterDetail.getTaxamount());
			sendnotaxamount = sendnotaxamount.add(saleRejectEnterDetail.getNotaxamount());
			sendcostamount = sendcostamount.add(saleRejectEnterDetail.getCostprice().multiply(saleRejectEnterDetail.getUnitnum()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		}
		if(!"2".equals(saleRejectEnter.getSourcetype())){
			saleRejectEnter.setInitsendamount(initsendamount);
			saleRejectEnter.setInitsendcostamount(initsendcostamount);
		}
		saleRejectEnter.setSendamount(sendamount);
		saleRejectEnter.setSendnotaxamount(sendnotaxamount);
		saleRejectEnter.setSendcostamount(sendcostamount);
		int i = saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
		return i>0;
	}

	@Override
	public RejectBill showRejectBillDetailList(String id) throws Exception {
		RejectBill rejectBill = salesOutService.getRejectBill(id);
		return rejectBill;
	}

	@Override
	public Receipt showReceiptDetailList(String id) throws Exception {
		Receipt receipt = salesOutService.getReceipt(id);
		return receipt;
	}

	@Override
	public Map submitSaleRejectEnterProcess(String id) throws Exception {
		Map map = new HashMap();
		return map;
	}

	@Override
	public boolean saveCheckSaleRejectEnter(SaleRejectEnter saleRejectEnter,
			List<SaleRejectEnterDetail> detailList) throws Exception {
		if(null==saleRejectEnter || "2".equals(saleRejectEnter.getSourcetype())){
			return false;
		}
		SysUser sysUser = getSysUser();
		saleRejectEnter.setCheckuserid(sysUser.getUserid());
		saleRejectEnter.setCheckusername(sysUser.getName());
		saleRejectEnter.setStatus("4");
		saleRejectEnter.setIscheck("1");
		saleRejectEnterMapper.deleteSaleRejectEnterDetailByBillid(saleRejectEnter.getId());
		BigDecimal initsendamount = BigDecimal.ZERO;
		BigDecimal initsendcostamount = BigDecimal.ZERO;
		BigDecimal sendamount = BigDecimal.ZERO;
		BigDecimal sendnotaxamount = BigDecimal.ZERO;
		BigDecimal sendcostamount = BigDecimal.ZERO;
		for(SaleRejectEnterDetail saleRejectEnterDetail : detailList){
			saleRejectEnterDetail.setSalerejectid(saleRejectEnter.getId());
			saleRejectEnterDetail.setStorageid(saleRejectEnter.getStorageid());
			GoodsInfo goodsInfo = getAllGoodsInfoByID(saleRejectEnterDetail.getGoodsid());
			if(null!=goodsInfo){
				saleRejectEnterDetail.setBrandid(goodsInfo.getBrand());
				//成本价
				saleRejectEnterDetail.setCostprice(getGoodsCostprice(saleRejectEnter.getStorageid(),goodsInfo));
				saleRejectEnterDetail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), saleRejectEnter.getCustomerid()));
				//厂家业务员
				saleRejectEnterDetail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), saleRejectEnter.getCustomerid()));
				//获取品牌部门
				Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
				if(null!=brand){
					saleRejectEnterDetail.setBranddept(brand.getDeptid());
				}
				//成本价
				saleRejectEnterDetail.setCostprice(getGoodsCostprice(saleRejectEnter.getStorageid(),goodsInfo));
				//实际成本价 不包括核算成本价
				saleRejectEnterDetail.setRealcostprice(goodsInfo.getNewstorageprice());
			}
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
		saleRejectEnter.setCheckdate(CommonUtils.getTodayDataStr());
		saleRejectEnter.setCheckamount(sendamount);
		saleRejectEnter.setChecknotaxamount(sendnotaxamount);
		//验收后 业务日期为当天日期
		saleRejectEnter.setBusinessdate(CommonUtils.getTodayDataStr());
		int i = saleRejectEnterMapper.editSaleRejectEnter(saleRejectEnter);
		if(i>0){
			salesOutService.updateRejectBillDetailBack(detailList);
			int noauditcount = saleRejectEnterMapper.getSaleRejectEnterNoAuditCount(saleRejectEnter.getSourceid());
			//获取退货通知单信息 判断客户是否更改
			RejectBill rejectBill = salesOutService.getRejectBill(saleRejectEnter.getSourceid());
			//客户更改后 更新退货通知单 和 退货入库单客户
			if(null!=rejectBill && !rejectBill.getCustomerid().equals(saleRejectEnter.getCustomerid())){
				saleRejectEnterMapper.updateSaleRejectEnterCustomeridBySourceid(saleRejectEnter);
				saleRejectEnterMapper.updateRejectBillCustomeridBySourceid(saleRejectEnter);
			}
			if(noauditcount==0){
				salesOutService.updateRejectBillInvoice("3", null, saleRejectEnter.getSourceid());
				salesOutService.updateRejectBillInvoicebill("3", null, saleRejectEnter.getSourceid());
			}
		}
		return i>0;
	}
	@Override
	public List getSaleRejectEnterListBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_storage_salereject_enter",null); //数据权限
		map.put("dataSql", sql);
		List<SaleRejectEnter> list=saleRejectEnterMapper.getSaleRejectEnterListBy(map);
		boolean showdetail=false;
		if(null!=map.get("showdetail") &&StringUtils.isNotEmpty(map.get("showdetail").toString()) && "1".equals(map.get("showdetail"))  ){
			showdetail=true;
		}

		
		String showPCustomerName=(String)map.get("showPCustomerName");

		for(SaleRejectEnter item : list){
			if(null!=item){
				Customer customer = getCustomerByID(item.getCustomerid());
				if(null!=customer){
					item.setCustomerInfo(customer);
					item.setCustomername(customer.getName());
					/*通用版，电话存在客户里
					if(StringUtils.isNotEmpty(customer.getContact())){
						Contacter contacter=getContacterById(customer.getContact());
						if(null!=contacter){
							customer.setContactmobile(contacter.getTel());
							customer.setContactname(contacter.getName());							
						}
					}
					*/

					if("true".equals(showPCustomerName)){
						if(StringUtils.isNotEmpty(customer.getPid())){
							Customer pCustomer = getBaseFilesCustomerMapper().getCustomerInfo(customer.getPid());
							if(null!=pCustomer){
								customer.setPname(pCustomer.getName());
							}
						}
					}
				}
				Contacter handler = getContacterById(item.getHandlerid());
				if(null!=handler){
					item.setHandlername(handler.getName());
				}
				DepartMent departMent = getDepartmentByDeptid(item.getSalesdept());
				if(null!=departMent){
					item.setSalesdeptname(departMent.getName());
				}
				Personnel personnel = getPersonnelById(item.getSalesuser());
				if(null!=personnel){
					item.setSaleusername(personnel.getName());
				}
				Settlement settlement = getSettlementByID(item.getSettletype());
				if(null!=settlement){
					item.setSettletypename(settlement.getName());
				}
				Payment payment = getPaymentByID(item.getPaytype());
				if(null!=payment){
					item.setPaytypename(payment.getName());
				}
				StorageInfo storageInfo = getStorageInfoByID(item.getStorageid());
				if(null!=storageInfo){
					item.setStoragename(storageInfo.getName());
				}
				if(showdetail){
					List<SaleRejectEnterDetail> billDetailList = saleRejectEnterMapper.getSaleRejectEnterDetailList(item.getId());
					for(SaleRejectEnterDetail billDetail : billDetailList){
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
		}
		return list;
	}
	@Override
	public boolean updateOrderPrinttimes(SaleRejectEnter saleRejectEnter) throws Exception{
		return saleRejectEnterMapper.updateOrderPrinttimes(saleRejectEnter)>0;
	}
	@Override
	public void updateOrderPrinttimes(List<SaleRejectEnter> list) throws Exception{
		if(null!=list){
			for(SaleRejectEnter item : list){
				saleRejectEnterMapper.updateOrderPrinttimes(item);
			}
		}
	}
	@Override
	public SaleRejectEnter getSaleRejectEnterPureInfo(String id) throws Exception{
		return saleRejectEnterMapper.getSaleRejectEnterByID(id);
	}

	/**
	 * 批量修改收货人
	 * @param id
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2016-12-21
	 */
	@Override
	public Map editSalesRejectEnterStorager(String id, String storager) throws Exception {
		boolean flag=false;
		SaleRejectEnter saleRejectEnter = saleRejectEnterMapper.getSaleRejectEnterByID(id);
		if(null!=saleRejectEnter){
			flag=saleRejectEnterMapper.editSalesRejectEnterStorager(id,storager)>0;
			RejectBill rejectBill=salesRejectBillMapper.getRejectBillById(saleRejectEnter.getSourceid());
			if(null!=rejectBill){
				salesRejectBillMapper.editSalesRejectBillStorager(saleRejectEnter.getSourceid(),storager);
			}
		}
		Map map=new HashMap();
		map.put("flag",flag);
		return map;
	}
}

