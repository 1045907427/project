/**
 * @(#)StorageSummaryServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 15, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.RuleJSONUtils;
import com.hd.agent.report.dao.StorageReportMapper;
import com.hd.agent.storage.model.InventoryEnterDetail;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.model.StorageSummaryLog;
import com.hd.agent.storage.service.IStorageSummaryService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 库存现存量service实现类
 * @author chenwei
 */
public class StorageSummaryServiceImpl extends BaseStorageServiceImpl implements
		IStorageSummaryService {
	private StorageReportMapper storageReportMapper;
	
	public StorageReportMapper getStorageReportMapper() {
		return storageReportMapper;
	}
	public void setStorageReportMapper(StorageReportMapper storageReportMapper) {
		this.storageReportMapper = storageReportMapper;
	}
	@Override
	public PageData showStorageSummarySumList(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_storage_summary", "t");
        pageMap.setDataSql(dataSql);
        pageMap.setQueryAlias("g");
        String groupcolall = "z.storageid,z.goodsid";
        if( pageMap.getCondition().containsKey("groupcols")){
            groupcolall = (String) pageMap.getCondition().get("groupcols");
            if(StringUtils.isEmpty(groupcolall)){
                groupcolall = "z.storageid,z.goodsid";
            }
            pageMap.getCondition().put("groupcols",groupcolall);
        }else{
            pageMap.getCondition().put("groupcols",groupcolall);
        }
        //仓库是否独立核算 0否1是 默认否
        String isStorageAccount = getSysParamValue("IsStorageAccount");
        if(StringUtils.isEmpty(isStorageAccount)){
            isStorageAccount = "0";
        }
        pageMap.getCondition().put("isStorageAccount",isStorageAccount);
        List<Map> dataList = getStorageSummaryMapper().getStorageSummarySumList(pageMap);
        int count = 0 ;
        for(Map map : dataList){
            String brandid = (String) map.get("brandid");
            Brand brand = getGoodsBrandByID(brandid);
            if(null!=brand){
                map.put("brandname", brand.getName());
            }
            String storageid = (String) map.get("storageid");
            StorageInfo storageInfo = getStorageInfoByID(storageid);
            if(null!=storageInfo){
                map.put("storagename", storageInfo.getName());
            }
            String goodsid = (String) map.get("goodsid");
            GoodsInfo goodsInfo= getAllGoodsInfoByID(goodsid);
            if(null!=goodsInfo){
                map.put("model", goodsInfo.getModel());
                map.put("basesaleprice",goodsInfo.getBasesaleprice());
            }
            if("id".equals(pageMap.getCols())){
                map.put("id",count);
                ++count ;
            }
            String supplierid = (String) map.get("supplierid");
            BuySupplier supplier=getSupplierInfoById(supplierid);
            if(null!=supplier){
                map.put("suppliername", supplier.getName());
            }

            //辅数量处理
			String auxexistingdetail=CommonUtils.strDigitNumDeal((String)map.get("auxexistingdetail"));
            map.put("auxexistingdetail",auxexistingdetail);
			map.put("exitingauxnumdetaildefault",CommonUtils.strDigitNumDeal((String)map.get("auxexistingdetail")));
            map.put("auxusabledetail",CommonUtils.strDigitNumDeal((String)map.get("auxusabledetail")));
            map.put("auxwaitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxwaitdetail")));
            map.put("auxtransitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxtransitdetail")));
            map.put("auxallotwaitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxallotwaitdetail")));
            map.put("auxallotenterdetail",CommonUtils.strDigitNumDeal((String)map.get("auxallotenterdetail")));
            map.put("auxprojectedusabledetail",CommonUtils.strDigitNumDeal((String)map.get("auxprojectedusabledetail")));
            map.put("auxsafedetail",CommonUtils.strDigitNumDeal((String)map.get("auxsafedetail")));
        }
        PageData pageData = new PageData(getStorageSummaryMapper().getStorageSummarySumCount(pageMap),dataList,pageMap);
        pageMap.getCondition().put("groupcolall", "all");
        List<Map> footer = getStorageSummaryMapper().getStorageSummarySumList(pageMap);
        boolean addfooterflag = false;
        for(Map map : footer){
            if(null!=map){
                map.put("goodsid", "");
                map.put("barcode", "");
                map.put("unitname", "");
                map.put("goodsname", "合计");
                map.put("spell", "");
                map.put("boxnum", "");
                map.put("supplierid", "");
                map.put("waresclassname", "");

                //辅数量处理
                map.put("auxexistingdetail",CommonUtils.strDigitNumDeal((String)map.get("auxexistingdetail")));
                map.put("auxusabledetail",CommonUtils.strDigitNumDeal((String)map.get("auxusabledetail")));
                map.put("auxwaitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxwaitdetail")));
                map.put("auxtransitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxtransitdetail")));
                map.put("auxallotwaitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxallotwaitdetail")));
                map.put("auxallotenterdetail",CommonUtils.strDigitNumDeal((String)map.get("auxallotenterdetail")));
                map.put("auxprojectedusabledetail",CommonUtils.strDigitNumDeal((String)map.get("auxprojectedusabledetail")));
                map.put("auxsafedetail",CommonUtils.strDigitNumDeal((String)map.get("auxsafedetail")));
                addfooterflag = true;
            }
        }
        if(addfooterflag){
            pageData.setFooter(footer);
        }

        return pageData;
	}
	@Override
	public PageData showStorageSummaryByStorageList(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_storage_summary", "s");
		pageMap.setDataSql(dataSql);
		PageData pageData = new PageData(getStorageSummaryMapper().getStorageSummaryByStorageCount(pageMap),getStorageSummaryMapper().getStorageSummaryByStorageList(pageMap),pageMap);
		List<StorageSummary> list = pageData.getList();
		List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
		for(StorageSummary storageSummary : list){
			Map<String,Object> map = PropertyUtils.describe(storageSummary);
			map.remove("class");
			GoodsInfo goodsInfo = getGoodsInfoByID(storageSummary.getGoodsid());
			map.put("goodsInfo", goodsInfo);
			if(null != goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				map.put("goodsbrand", goodsInfo.getBrandName());
				map.put("model", goodsInfo.getModel());
			}
			map.put("state", "closed");
			StorageInfo storageInfo = getStorageInfoByID(storageSummary.getStorageid());
			if(null!=storageInfo){
				map.put("storagename", storageInfo.getName());
			}
			//商品各辅单位换算
			Map<String,Object> exitingAuxnumMap = getAuxnumsByGoods(storageSummary.getExistingnum(), storageSummary.getGoodsid(),"exiting");
			Map<String,Object> usableAuxnumMap = getAuxnumsByGoods(storageSummary.getUsablenum(), storageSummary.getGoodsid(),"usable");
			Map<String,Object> waitAuxnumMap = getAuxnumsByGoods(storageSummary.getWaitnum(), storageSummary.getGoodsid(),"wait");
			Map<String,Object> transitAuxnumMap = getAuxnumsByGoods(storageSummary.getTransitnum(), storageSummary.getGoodsid(),"transit");
			Map<String,Object> allotwaitAuxnumMap = getAuxnumsByGoods(storageSummary.getAllotwaitnum(), storageSummary.getGoodsid(),"allotwait");
			Map<String,Object> allotenterAuxnumMap = getAuxnumsByGoods(storageSummary.getAllotenternum(), storageSummary.getGoodsid(),"allotenter");
			Map<String,Object> projectedusableAuxnumMap = getAuxnumsByGoods(storageSummary.getProjectedusablenum(), storageSummary.getGoodsid(),"projectedusable");
			Map<String,Object> safeAuxnumMap = getAuxnumsByGoods(storageSummary.getSafenum(), storageSummary.getGoodsid(),"safe");
			map.putAll(exitingAuxnumMap);
			map.putAll(usableAuxnumMap);
			map.putAll(waitAuxnumMap);
			map.putAll(transitAuxnumMap);
			map.putAll(allotwaitAuxnumMap);
			map.putAll(allotenterAuxnumMap);
			map.putAll(projectedusableAuxnumMap);
			map.putAll(safeAuxnumMap);
			
			newList.add(map);
		}
		pageData.setList(newList);
		return pageData;
	}

	@Override
	public List showStorageSummaryBatchList(String storageid,String goodsid,String showZero) throws Exception {
        String dataSql = getDataAccessRule("t_storage_summary", "t1");
		PageMap pageMap = new PageMap();
        pageMap.setDataSql(dataSql);
		Map condition = new HashMap();
		condition.put("storageid", storageid);
		condition.put("goodsid", goodsid);
		condition.put("showZero",showZero);
		pageMap.setCondition(condition);
        //仓库是否独立核算 0否1是 默认否
        String isStorageAccount = getStorageIsAccount(storageid);
        pageMap.getCondition().put("isStorageAccount",isStorageAccount);

		List<Map> list = getStorageSummaryMapper().showStorageSummaryBatchListByStorageidAndGoodsid(pageMap);
		for(Map map : list){
			if(map.containsKey("goodsid")){
				String thisgoodsid = (String) map.get("goodsid");
				GoodsInfo goodsInfo = getAllGoodsInfoByID(thisgoodsid);
				if(null!=goodsInfo){
					map.put("goodsname", goodsInfo.getName());
                    map.put("basesaleprice",goodsInfo.getBasesaleprice());
				}
			}
			if(map.containsKey("storageid")){
				String thisstorageid = (String) map.get("storageid");
				StorageInfo storageInfo = getStorageInfoByID(thisstorageid);
				if(null!=storageInfo){
					map.put("storagename", storageInfo.getName());
				}
			}
			if(map.containsKey("storagelocationid")){
				String storagelocationid = (String) map.get("storagelocationid");
				StorageLocation storageLocation = getStorageLocationByID(storagelocationid);
				if(null!=storageLocation){
					map.put("storagelocationname", storageLocation.getName());
				}
			}

			//辅数量处理
			map.put("auxexistingdetail",CommonUtils.strDigitNumDeal((String)map.get("auxexistingdetail")));
			map.put("auxusabledetail",CommonUtils.strDigitNumDeal((String)map.get("auxusabledetail")));
			map.put("auxwaitnumdetail",CommonUtils.strDigitNumDeal((String)map.get("auxwaitnumdetail")));
			map.put("auxallotwaitnumdetail",CommonUtils.strDigitNumDeal((String)map.get("auxallotwaitnumdetail")));
			map.put("auxallotenternumdetail",CommonUtils.strDigitNumDeal((String)map.get("auxallotenternumdetail")));
		}
		return list;
	}

	@Override
	public List showStorageSummaryList(String goodsid,String istotalcontrol) throws Exception {
		String dataSql = getDataAccessRule("t_storage_summary", null);
		PageMap pageMap = new PageMap();
		Map condition = new HashMap();
		condition.put("istotalcontrol", istotalcontrol);
		condition.put("goodsid", goodsid);
		pageMap.setCondition(condition);
		pageMap.setDataSql(dataSql);
		List<StorageSummary> list = getStorageSummaryMapper().showStorageSummaryList(pageMap);
		List newList = new ArrayList();
		for(StorageSummary storageSummary : list){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			Map map = BeanUtils.describe(storageSummary);
			map.remove("class");
			map.put("parentId", goodsid);
			map.put("id", "storageid-"+storageSummary.getStorageid()+"goodsid-"+storageSummary.getGoodsid());
			map.put("goodsInfo", getGoodsInfoByID(storageSummary.getGoodsid()));
			if("1".equals(goodsInfo.getIsstoragelocation()) || "1".equals(goodsInfo.getIsbatch())){
				map.put("state", "closed");
			}else{
				map.put("state", "open");
			}
			//商品各辅单位换算
			Map<String,Object> exitingAuxnumMap = getAuxnumsByGoods(storageSummary.getExistingnum(), storageSummary.getGoodsid(),"exiting");
			Map<String,Object> usableAuxnumMap = getAuxnumsByGoods(storageSummary.getUsablenum(), storageSummary.getGoodsid(),"usable");
			Map<String,Object> waitAuxnumMap = getAuxnumsByGoods(storageSummary.getWaitnum(), storageSummary.getGoodsid(),"wait");
			Map<String,Object> transitAuxnumMap = getAuxnumsByGoods(storageSummary.getTransitnum(), storageSummary.getGoodsid(),"transit");
			Map<String,Object> allotwaitAuxnumMap = getAuxnumsByGoods(storageSummary.getAllotwaitnum(), storageSummary.getGoodsid(),"allotwait");
			Map<String,Object> allotenterAuxnumMap = getAuxnumsByGoods(storageSummary.getAllotenternum(), storageSummary.getGoodsid(),"allotenter");
			Map<String,Object> projectedusableAuxnumMap = getAuxnumsByGoods(storageSummary.getProjectedusablenum(), storageSummary.getGoodsid(),"projectedusable");
			Map<String,Object> safeAuxnumMap = getAuxnumsByGoods(storageSummary.getSafenum(), storageSummary.getGoodsid(),"safe");
			map.putAll(exitingAuxnumMap);
			map.putAll(usableAuxnumMap);
			map.putAll(waitAuxnumMap);
			map.putAll(transitAuxnumMap);
			map.putAll(allotwaitAuxnumMap);
			map.putAll(allotenterAuxnumMap);
			map.putAll(projectedusableAuxnumMap);
			map.putAll(safeAuxnumMap);
			
			map.put("storageInfo", getStorageInfoByID(storageSummary.getStorageid()));
			if(null!=goodsInfo){
				BigDecimal costprice = getGoodsCostprice(storageSummary.getStorageid(),goodsInfo);
				BigDecimal costamount = costprice.multiply(storageSummary.getExistingnum().setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				map.put("costamount", costamount);
				map.put("costprice", costprice);
			}
			newList.add(map);
		}
		return newList;
	}

	@Override
	public PageData showStorageSummaryLogList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_summary_log", null);
		pageMap.setDataSql(dataSql);
		List<StorageSummaryLog> list = getStorageSummaryMapper().showStorageSummaryLogList(pageMap);
		for(StorageSummaryLog storageSummaryLog : list){
			storageSummaryLog.setGoodsInfo(getGoodsInfoByID(storageSummaryLog.getGoodsid()));
			StorageInfo storageInfo = getStorageInfoByID(storageSummaryLog.getStorageid());
			if(null!=storageInfo){
				storageSummaryLog.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocationByID(storageSummaryLog.getStoragelocationid());
			if(null!=storageLocation){
				storageSummaryLog.setStoragelocationname(storageLocation.getName());
			}
			if("stockInit".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("库存初始化");
			}
			else if("purchaseEnter".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("采购入库单");
			}
			else if("saleout".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("销售发货单");
			}
			else if("storageOtherOut".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("其他出库单");
			}
			else if("saleRejectEnter".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("销售退货入库单");
			}
			else if("storageOtherEnter".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("其他入库单");
			}
			else if("allocateOut".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("调拨出库单");
			}
			else if("purchaseRejectOut".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("采购退货出库单");
			}
			else if("adjustments".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("调账单");
			}
			else if("storageDeliveryEnter".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("代配送入库单");
			}
			else if("StorageDeliveryOut".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("代配送出库单");
			}
			if("9".equals(storageSummaryLog.getBillmodel())){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				String businessdate=sdf.format(storageSummaryLog.getAddtime());  
				int  boxnum=storageSummaryLog.getGoodsInfo().getBoxnum().intValue();
				int receivenum=((null != storageSummaryLog.getReceivenum()) ? storageSummaryLog.getReceivenum() : BigDecimal.ZERO).intValue();
				int sendnum=((null != storageSummaryLog.getSendnum()) ? storageSummaryLog.getSendnum() : BigDecimal.ZERO).intValue();
				storageSummaryLog.setGoodsid("");
				storageSummaryLog.setGoodsInfo(null);
				storageSummaryLog.setStorageid("");
				storageSummaryLog.setStoragename("");
				storageSummaryLog.setStoragelocationid("");
				storageSummaryLog.setStoragelocationname("");
				storageSummaryLog.setBillid(businessdate+" 小计");
				storageSummaryLog.setBillmodel("");
				storageSummaryLog.setBillmodelname("");
				if(receivenum!=0){
				    storageSummaryLog.setAuxreceivenumdetail(String.valueOf(receivenum/boxnum)+storageSummaryLog.getAuxunitname()+String.valueOf(receivenum-receivenum/boxnum*boxnum)+storageSummaryLog.getUnitname());
				}else {
					storageSummaryLog.setAuxreceivenumdetail("");
				}
				if(sendnum!=0){
				    storageSummaryLog.setAuxsendnumdetail(String.valueOf(sendnum/boxnum)+storageSummaryLog.getAuxunitname()+String.valueOf(sendnum-sendnum/boxnum*boxnum)+storageSummaryLog.getUnitname());
				}else{
					storageSummaryLog.setAuxsendnumdetail("");
				}
				storageSummaryLog.setAddtime(null);
			}
		}
		int count = getStorageSummaryMapper().showStorageSummaryLogCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);
		
		List<StorageSummaryLog> footerList = new ArrayList<StorageSummaryLog>();
		StorageSummaryLog storageSummaryLog = new StorageSummaryLog();
		storageSummaryLog.setBillid("合计");
		Map totalmap = getStorageSummaryMapper().showStorageSummaryLogSum(pageMap);
		if(null!=totalmap){
			String goodsid =(String) totalmap.get("goodsid");
			BigDecimal boxnum = getGoodsInfoByID(goodsid).getBoxnum();
			BigDecimal receivenumBig=(BigDecimal) totalmap.get("receivenum");
			BigDecimal sendnumBig=(BigDecimal) totalmap.get("sendnum");
			String auxunitname=(String) totalmap.get("auxunitname");
			String unitname=(String) totalmap.get("unitname");
			storageSummaryLog.setReceivenum(receivenumBig);
			storageSummaryLog.setSendnum(sendnumBig);
			if(receivenumBig.compareTo(BigDecimal.ZERO)!=0){
			    storageSummaryLog.setAuxreceivenumdetail(CommonUtils.strDigitNumDeal(String.valueOf(receivenumBig.divideAndRemainder(boxnum)[0])+auxunitname+String.valueOf(receivenumBig.divideAndRemainder(boxnum)[1])+unitname));
			}else {
				storageSummaryLog.setAuxreceivenumdetail("");
			}
			if(sendnumBig.compareTo(BigDecimal.ZERO)!=0){
			    storageSummaryLog.setAuxsendnumdetail(CommonUtils.strDigitNumDeal(String.valueOf(sendnumBig.divideAndRemainder(boxnum)[0])+auxunitname+String.valueOf(sendnumBig.divideAndRemainder(boxnum)[1])+unitname));
			}else{
				storageSummaryLog.setAuxsendnumdetail("");
			}
			footerList.add(storageSummaryLog);
			pageData.setFooter(footerList);
		}
		return pageData;
	}

	@Override
	public PageData showStorageSummaryLogListByStorage(PageMap pageMap) throws Exception {
		boolean haveInitdata=false;
		boolean haveCount=false;
		String dataSql = getDataAccessRule("t_storage_summary_log", null);
		String businessdate1 = null;
		pageMap.setDataSql(dataSql);
		String queryinitdate1="";
		String queryinitdate2="";
		if(pageMap.getCondition().containsKey("businessdate1")){
			businessdate1 = (String) pageMap.getCondition().get("businessdate1");
		}
		String initdate = storageReportMapper.getStorageInitDate();
		if(StringUtils.isNotEmpty(initdate)){
			if(null!=businessdate1){
				Date busdate1 = CommonUtils.stringToDate(businessdate1);
				Date initD = CommonUtils.stringToDate(initdate);
				if(busdate1.after(initD)){
			        queryinitdate1= CommonUtils.getBeforeDateInDays(busdate1, 3);
					queryinitdate2= CommonUtils.getBeforeDateInDays(busdate1, 1);
					haveInitdata=true;
				}
			}
		}
		List<StorageSummaryLog> list = getStorageSummaryMapper().showStorageSummaryLogList(pageMap);
		for(int i=0;i<list.size();i++){
			StorageSummaryLog storageSummaryLog=list.get(i);
			StorageSummaryLog indexStorageSummaryLog=null;
			storageSummaryLog.setGoodsInfo(getGoodsInfoByID(storageSummaryLog.getGoodsid()));
			StorageInfo storageInfo = getStorageInfoByID(storageSummaryLog.getStorageid());
			if(null!=storageInfo){
				storageSummaryLog.setStoragename(storageInfo.getName());
			}
			StorageLocation storageLocation = getStorageLocationByID(storageSummaryLog.getStoragelocationid());
			if(null!=storageLocation){
				storageSummaryLog.setStoragelocationname(storageLocation.getName());
			}
			if("stockInit".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("库存初始化");
			}
			else if("purchaseEnter".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("采购入库单");
			}
			else if("saleout".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("销售发货单");
			}
			else if("storageOtherOut".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("其他出库单");
			}
			else if("saleRejectEnter".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("销售退货入库单");
			}
			else if("storageOtherEnter".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("其他入库单");
			}
			else if("allocateOut".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("调拨出库单");
			}
			else if("purchaseRejectOut".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("采购退货出库单");
			}
			else if("adjustments".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("调账单");
			}
			else if("storageDeliveryEnter".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("代配送入库单");
			}
			else if("StorageDeliveryOut".equals(storageSummaryLog.getBillmodel())){
				storageSummaryLog.setBillmodelname("代配送出库单");
			}
			
			if("9".equals(storageSummaryLog.getBillmodel())){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				String businessdate=sdf.format(storageSummaryLog.getAddtime());  
				int  boxnum=storageSummaryLog.getGoodsInfo().getBoxnum().intValue();
				int receivenum=((null != storageSummaryLog.getReceivenum()) ? storageSummaryLog.getReceivenum() : BigDecimal.ZERO).intValue();
				int sendnum=((null != storageSummaryLog.getSendnum()) ? storageSummaryLog.getSendnum() : BigDecimal.ZERO).intValue();
				storageSummaryLog.setGoodsid("");
				storageSummaryLog.setGoodsInfo(null);
				storageSummaryLog.setStorageid("");
				storageSummaryLog.setStoragename("");
				storageSummaryLog.setStoragelocationid("");
				storageSummaryLog.setStoragelocationname("");
				storageSummaryLog.setBillid(businessdate+" 小计");
				storageSummaryLog.setBillmodel("");
				storageSummaryLog.setBillmodelname("");
				storageSummaryLog.setBeginnum(null);
				storageSummaryLog.setEndnum(null);
				storageSummaryLog.setAuxbeginnumdetail("");
				storageSummaryLog.setAuxendnumdetail("");
				if(receivenum!=0){
				    storageSummaryLog.setAuxreceivenumdetail(String.valueOf(receivenum/boxnum)+storageSummaryLog.getAuxunitname()+String.valueOf(receivenum-receivenum/boxnum*boxnum)+storageSummaryLog.getUnitname());
				}else {
					storageSummaryLog.setAuxreceivenumdetail("");
				}
				if(sendnum!=0){
				    storageSummaryLog.setAuxsendnumdetail(String.valueOf(sendnum/boxnum)+storageSummaryLog.getAuxunitname()+String.valueOf(sendnum-sendnum/boxnum*boxnum)+storageSummaryLog.getUnitname());
				}else{
					storageSummaryLog.setAuxsendnumdetail("");
				}
				storageSummaryLog.setAddtime(null);
				haveCount=true;
			}
			
		
			
		}
		int count = getStorageSummaryMapper().showStorageSummaryLogCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);
		
		List<StorageSummaryLog> footerList = new ArrayList<StorageSummaryLog>();
		StorageSummaryLog storageSummaryLog = new StorageSummaryLog();
		storageSummaryLog.setBillid("合计");
		Map totalmap = getStorageSummaryMapper().showStorageSummaryLogSum(pageMap);
		if(null!=totalmap){
			String goodsid =(String) totalmap.get("goodsid");
			int  boxnum=getGoodsInfoByID(goodsid).getBoxnum().intValue();
			BigDecimal receivenumBig=(BigDecimal) totalmap.get("receivenum");
			BigDecimal sendnumBig=(BigDecimal) totalmap.get("sendnum");
			int receivenum=receivenumBig.intValue();
			int sendnum=sendnumBig.intValue();
			String auxunitname=(String) totalmap.get("auxunitname");
			String unitname=(String) totalmap.get("unitname");
			storageSummaryLog.setReceivenum(receivenumBig);
			storageSummaryLog.setSendnum(sendnumBig);
			if(receivenum!=0){
			    storageSummaryLog.setAuxreceivenumdetail(String.valueOf(receivenum/boxnum)+auxunitname+String.valueOf(receivenum-receivenum/boxnum*boxnum)+unitname);
			}else {
				storageSummaryLog.setAuxreceivenumdetail("");
			}
			if(sendnum!=0){
			    storageSummaryLog.setAuxsendnumdetail(String.valueOf(sendnum/boxnum)+auxunitname+String.valueOf(sendnum-sendnum/boxnum*boxnum)+unitname);
			}else{
				storageSummaryLog.setAuxsendnumdetail("");
			}
			footerList.add(storageSummaryLog);
			pageData.setFooter(footerList);
		}
		return pageData;
	}

	
	

	@Override
	public List getStorageBatchListByStorageidAndGoodsid(String storageid,
			String goodsid) throws Exception {
		List list = getStorageSummaryMapper().getStorageSummaryBatchListByStorageidAndGoodsid(storageid, goodsid);
		return list;
	}

	@Override
	public StorageSummaryBatch getStorageSummaryBatchInfo(String id)
			throws Exception {
		StorageSummaryBatch storageSummaryBatch = getStorageSummaryMapper().getStorageSummaryBatchInfoById(id);
		if(null!=storageSummaryBatch){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
			if(null!=goodsInfo){
				StorageInfo storageInfo = getStorageInfoByID(goodsInfo.getStorageid());
				if(null!=storageInfo){
					storageSummaryBatch.setDefaultstoragename(storageInfo.getName());	
				}
			}
		}
		return storageSummaryBatch;
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
		}else{
			storageSummary = new StorageSummary();
			storageSummary.setExistingnum(BigDecimal.ZERO);
			storageSummary.setUsablenum(BigDecimal.ZERO);
			storageSummary.setUnitname("");
			storageSummary.setOutuseablenum(BigDecimal.ZERO);
		}
		return storageSummary;
	}

    /**
     * 根据商品编号获取库存中商品的总量
     *
     * @param goodsid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 24, 2013
     */
    @Override
    public StorageSummary getStorageSummarySumByGoodsidWithDatarule(String goodsid) throws Exception {
        //是否使用数据权限 查询库存
        String IsUseStorageDatarule = getSysParamValue("IsUseStorageDatarule");
        if("1".equals(IsUseStorageDatarule)){
            String dataSqlStorage = getDataAccessRule("t_storage_summary", "t");
            StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummarySumByGoodsidWithDatarule(goodsid,dataSqlStorage);
            if(null==storageSummary){
                storageSummary = new StorageSummary();
                storageSummary.setExistingnum(BigDecimal.ZERO);
                storageSummary.setUsablenum(BigDecimal.ZERO);
                storageSummary.setUnitname("");
                storageSummary.setOutuseablenum(BigDecimal.ZERO);
            }
            return storageSummary;
        }else{
            return getStorageSummarySumByGoodsid(goodsid);
        }
    }

    @Override
	public StorageSummary getStorageSummaryByStorageAndGoods(String storageid,
			String goodsid) throws Exception {
//		StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummaryInfoByGoodsidAndStorageid(goodsid, storageid);
//		return storageSummary;
		StorageSummary storageSummary = getStorageSummaryMapper().getStorageSummaryInfoByGoodsidAndStorageid(goodsid, storageid);
		if(null!=storageSummary){
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			if(null!=storageInfo && "1".equals(storageInfo.getIssendusable())){
				storageSummary.setOutuseablenum(storageSummary.getUsablenum().add(storageSummary.getTransitnum()));
			}else{
				storageSummary.setOutuseablenum(storageSummary.getUsablenum());
			}
		}else{
			storageSummary = new StorageSummary();
			storageSummary.setExistingnum(BigDecimal.ZERO);
			storageSummary.setUsablenum(BigDecimal.ZERO);
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null != goodsInfo){
				storageSummary.setCostprice(goodsInfo.getNewstorageprice());
			}
			storageSummary.setUnitname("");
			storageSummary.setOutuseablenum(BigDecimal.ZERO);
		}
		return storageSummary;
	}

    @Override
	public PageData getStorageGoodsSelectListData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_storage_summary_batch", "t");
		pageMap.setDataSql(dataSql);
		//控件传过来的参数 生成sql语句
		String paramRule = (String) pageMap.getCondition().get("paramRule");
		String paramRuleSql = RuleJSONUtils.widgetParamToSql(paramRule,"z");
		pageMap.getCondition().put("paramRuleSql", paramRuleSql);
		List<Map> list = getStorageSummaryMapper().getStorageGoodsSelectListData(pageMap);
		//获取商品可用量字段权限
		Map colMap = getAccessColumn("t_storage_summary");
		for(Map map : list){
			if(null==colMap || colMap.size()==0 ||colMap.containsKey("usablenum")){
			}else{
				map.remove("usablenum");
			}
			if(null==colMap || colMap.size()==0 ||colMap.containsKey("existingnum")){
			}else{
				map.remove("existingnum");
			}
			//品牌名称
			String brandid = (String) map.get("brand");
			Brand brand = getBaseFilesGoodsMapper().getBrandInfo(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}
			String storageid = (String) map.get("storageid");
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			if(null!=storageInfo){
				map.put("storagename", storageInfo.getName());
			}
			//库位名称
			String storagelocationid = (String) map.get("storagelocationid");
			StorageLocation storageLocation = getStorageLocation(storagelocationid);
			if(null!=storageLocation){
				map.put("storagelocationname", storageLocation.getName());
			}
			String goodsid = (String) map.get("goodsid");
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("unitid", goodsInfo.getMainunit());
                map.put("spell", goodsInfo.getSpell());
				map.put("newstorageprice", getGoodsCostprice(storageid,goodsInfo));
				map.put("taxtype", goodsInfo.getDefaulttaxtype());
				map.put("taxtypename", goodsInfo.getDefaulttaxtypeName());
				map.put("boxnum", goodsInfo.getBoxnum());
				//商品默认仓库
				StorageInfo defaultstorageInfo = getStorageInfoByID(goodsInfo.getStorageid());
				if(null!=defaultstorageInfo){
					map.put("defaultstoragename", defaultstorageInfo.getName());
				}
			}
			
		}
		PageData pageData = new PageData(getStorageSummaryMapper().getStorageGoodsSelectListDataCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public List getStorageBrandList(String storageid) throws Exception {
		List list = getStorageSummaryMapper().getStorageBrandList(storageid);
		return list;
	}

    /**
     * 根据仓库编码 获取该仓库下的有可用量的商品批次列表
     *
     * @param storageid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 20, 2013
     */
    @Override
    public List getStorageBatchListHasUsenumByStorageid(String storageid) throws Exception {
        List list = getStorageSummaryMapper().getStorageBatchListHasUsenumByStorageid(storageid);
        return list;
    }

    /**
     * 清理库龄报表(超过60天的库龄报表)
     *
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteInventoryAgeIndays() throws Exception {
        return getStorageSummaryMapper().deleteInventoryAgeIndays(60)>0;
    }

    /**
     * 生成库龄报表
     *
     * @return
     * @throws Exception
     */
    @Override
    public boolean addInventoryAge(String date) throws Exception {

        if(StringUtils.isEmpty(date)){
            date = CommonUtils.getTodayDataStr();
        }
        getStorageSummaryMapper().deleteInventoryAge(date);

        List<StorageSummary> storageList = null;
        if(CommonUtils.getTodayDataStr().equals(date)){
            storageList = getStorageSummaryMapper().getAllStorageSummaryList();
        }else{
            storageList = getStorageSummaryMapper().getAllStorageSummaryListByDate(date);
        }
        //查询明细的日期
        String queryDate = CommonUtils.getNextDayByDate(date);
        for(StorageSummary storageSummary : storageList){
            //库龄明细倒推 剩余数量
            BigDecimal remainNum = storageSummary.getExistingnum();
            String begindate = null;
            BigDecimal age = BigDecimal.ZERO;
            List<InventoryEnterDetail> detailList = getStorageSummaryMapper().getStorageEnterDetailList(storageSummary.getStorageid(),storageSummary.getGoodsid(),queryDate);
            for(InventoryEnterDetail inventoryEnterDetail : detailList){
                if(remainNum.compareTo(BigDecimal.ZERO)==0){
                    break;
                }
                if(remainNum.compareTo(inventoryEnterDetail.getUnitnum())>=0){
                    remainNum = remainNum.subtract(inventoryEnterDetail.getUnitnum());
                    inventoryEnterDetail.setInum(inventoryEnterDetail.getUnitnum());
                }else{
                    inventoryEnterDetail.setInum(remainNum);
                    remainNum = BigDecimal.ZERO;
                }
                int days = CommonUtils.daysBetween(inventoryEnterDetail.getBustime(),CommonUtils.stringToDate(date,"yyyy-MM-dd"));
                //库龄 = 入库数量/库存数量*相隔天数
                BigDecimal thisAge = inventoryEnterDetail.getInum().divide(storageSummary.getExistingnum(),6,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(days)).setScale(2,BigDecimal.ROUND_HALF_UP);
                age = age.add(thisAge);
                begindate = CommonUtils.dataToStr(inventoryEnterDetail.getBustime(),"yyyy-MM-dd");
                inventoryEnterDetail.setBusinessdate(date);
                inventoryEnterDetail.setAge(thisAge);
                getStorageSummaryMapper().addInventoryAgeDetail(inventoryEnterDetail);
            }
            getStorageSummaryMapper().addInventoryAge(date,storageSummary.getStorageid(),storageSummary.getGoodsid(),age,storageSummary.getExistingnum(),begindate);
        }
        return true;
    }

    /**
     * 获取库龄报表数据
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public PageData showInventoryAgeDataList(PageMap pageMap) throws Exception {
    	Map condition=pageMap.getCondition();
		String oldgroupcols=(String)condition.get("groupcols");
		if(!condition.containsKey("groupcols")){
			condition.put("groupcols","z.storageid,z.goodsid");
		}else{
			String groupcols="";
			if(oldgroupcols.indexOf("storageid")!=-1){
				groupcols="z.storageid,";
			}
			if(oldgroupcols.indexOf("brandid")!=-1){
				groupcols+="b.id,";
			}
			if(oldgroupcols.indexOf("supplierid")!=-1){
				groupcols+="g.defaultsupplier,";
			}
			if(groupcols.endsWith(",")){
				groupcols=groupcols.substring(0,groupcols.length()-1);
				condition.put("groupcols",groupcols);
			}
		}
        String column_sum="";
        String column_main="";
        String column_detail="";
        String column_sum_total = "";
        SysUser sysUser = getSysUser();
        List<Map> RSlist = getStorageSummaryMapper().getInventoryAgeListDataRS(sysUser.getUserid());
        int RSCount = RSlist.size();
        int vseq = 0;
        Date businessdate = new Date();
        String businessdateStr = (String) pageMap.getCondition().get("businessdate");
        if(StringUtils.isNotEmpty(businessdateStr)){
            businessdate = CommonUtils.stringToDate(businessdateStr);
        }
        for(Map<String,Object> rsMap :RSlist){
            vseq ++;
            int beginday =(Integer)rsMap.get("beginday");
            int endday =(Integer)rsMap.get("endday");
            int seq =(Integer)rsMap.get("seq");
            column_sum=column_sum+",sum(z.inum"+seq+") as inum"+seq+",sum(z.inum"+seq+"*g.newbuyprice) as iamount"+seq+",(sum(z.inum"+seq+"*g.newbuyprice)/(1+ft.rate/100)) as inotaxamount"+seq
                    + ",floor(sum(z.inum"+seq+") / m.rate) as iauxnum"+seq+",ABS(MOD (sum(z.inum"+seq+"), m.rate)) as iauxint"+seq
                    +",CONCAT(floor(sum(z.inum"+seq+") / m.rate),gm1.name,ABS(MOD (sum(z.inum"+seq+"), m.rate)),gm.name) as inumdetail"+seq;
            column_sum_total = column_sum_total+",sum(x.inum"+seq+") as inum"+seq+",sum(x.iamount"+seq+") as iamount"+seq+",sum(x.inotaxamount"+seq+") as inotaxamount"+seq
                    + ",sum(x.iauxnum"+seq+") as iauxnum"+seq+",sum(x.iauxint"+seq+") as iauxint"+seq ;
            column_main=column_main+",0 as inum"+seq;
            //按库龄计算库存集中度
//            if(vseq<RSCount){
//                if(vseq==1){
//                    column_detail = column_detail+",(CASE when t.age>="+(beginday-1)+" and t.age<="+endday+" then t.inum ELSE 0 end) as inum"+seq;
//                }else{
//                    column_detail = column_detail+",(CASE when t.age>"+(beginday-1)+" and t.age<="+endday+" then t.inum ELSE 0 end) as inum"+seq;
//                }
//            }else{
//                column_detail = column_detail+",(CASE when t.age>"+(beginday-1)+" then t.inum ELSE 0 end) as inum"+seq;
//            }
            //按入库日期计算库存集中度
            Date beginDate = CommonUtils.getBeforeTheDateInDays(businessdate,endday-1);
            Date endDate = CommonUtils.getBeforeTheDateInDays(businessdate,beginday-2);
            String beginStr = CommonUtils.dataToStr(beginDate,"yyyy-MM-dd");
            String endStr = CommonUtils.dataToStr(endDate,"yyyy-MM-dd");
            if(vseq<RSCount){
                column_detail = column_detail+",(CASE when t.bustime>'"+beginStr+"' and t.bustime<='"+endStr+"' then t.inum ELSE 0 end) as inum"+seq;
            }else{
                column_detail = column_detail+",(CASE when t.bustime<='"+endStr+"' then t.inum ELSE 0 end) as inum"+seq;
            }
        }
        pageMap.getCondition().put("column_sum", column_sum);
        pageMap.getCondition().put("column_main", column_main);
        pageMap.getCondition().put("column_detail", column_detail);
        pageMap.getCondition().put("column_sum_total", column_sum_total);
        List<Map> dataList =getStorageSummaryMapper().getInventoryAgeDataList(pageMap);
        for(Map map : dataList){
            map.put("auxnumdetail",CommonUtils.strDigitNumDeal((String)map.get("auxnumdetail")));
            for(Map<String,Object> rsMap :RSlist){
                int seq =(Integer)rsMap.get("seq");
                map.put("inumdetail"+seq,CommonUtils.strDigitNumDeal((String)map.get("inumdetail"+seq)));
            }
        }

        PageData pageData = new PageData(getStorageSummaryMapper().getInventoryAgeDataListCount(pageMap),dataList,pageMap);
        pageMap.getCondition().put("groupcols","all");
        List<Map> footerList = getStorageSummaryMapper().getInventoryAgeDataSumList(pageMap);
        boolean isAddFoot = false;
        for(Map map : footerList){
            if(null!=map){
                isAddFoot = true;
                if(StringUtils.isEmpty(oldgroupcols)){
					map.put("storageid","");
					map.put("storagename","合计");
				} else if(oldgroupcols.indexOf("storageid")!=-1){
					map.put("storageid","");
					map.put("storagename","合计");
				}else if(oldgroupcols.indexOf("brandid")!=-1){
					map.put("brandid","");
					map.put("brandname","合计");
				}else if(oldgroupcols.indexOf("supplierid")!=-1){
					map.put("suppliername","合计");
				}
                map.put("goodsid","");
                map.put("goodsname","");
                map.put("price","");
                BigDecimal auxnum = (BigDecimal) map.get("auxnum");
                BigDecimal auxint = (BigDecimal) map.get("auxint");
                map.put("auxnumdetail",CommonUtils.strDigitNumDeal(auxnum.toString()+"箱"+auxint.toString()));
                for(Map<String,Object> rsMap :RSlist){
                    int seq =(Integer)rsMap.get("seq");
                    BigDecimal iauxnum = (BigDecimal) map.get("iauxnum"+seq);
                    BigDecimal iauxint = (BigDecimal) map.get("iauxint"+seq);
                    map.put("inumdetail"+seq,CommonUtils.strDigitNumDeal(iauxnum.toString()+"箱"+iauxint.toString()));
                }
            }
        }
        if(isAddFoot){
            pageData.setFooter(footerList);
        }
        return pageData;
    }

    /**
     * 获取库龄入库明细记录
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public PageData showInventoryAgeDetailLogData(PageMap pageMap) throws Exception {
        PageData pageData = new PageData(getStorageSummaryMapper().showInventoryAgeDetailLogDataListCount(pageMap),getStorageSummaryMapper().showInventoryAgeDetailLogDataList(pageMap),pageMap);
        List<Map> footer = getStorageSummaryMapper().showInventoryAgeDetailLogDataSum(pageMap);
        if(null!=footer){
            boolean addFooter = false;
            for(Map map : footer){
                if(null!=map){
                    addFooter = true;
                    map.put("billid","合计");
                }
            }
            if(addFooter){
                pageData.setFooter(footer);
            }
        }
        return pageData;
    }
}

