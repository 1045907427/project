/**
 * @(#)StorageReportServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service.impl;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.journalsheet.dao.JournalSheetMapper;
import com.hd.agent.report.dao.BuySaleReportMapper;
import com.hd.agent.report.dao.StorageReportMapper;
import com.hd.agent.report.model.*;
import com.hd.agent.report.service.IStorageReportService;
import com.hd.agent.storage.dao.AllocateOutMapper;
import com.hd.agent.storage.dao.CheckListMapper;
import com.hd.agent.storage.dao.StockInitMapper;
import com.hd.agent.storage.dao.StorageSummaryMapper;
import com.hd.agent.storage.model.CheckListDetail;
import com.hd.agent.storage.model.StockInit;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 库存报表service实现类
 * @author chenwei
 */
public class StorageReportServiceImpl extends BaseFilesServiceImpl implements
		IStorageReportService {
	
	private StorageReportMapper storageReportMapper;
	
	/**
	 * 库存初始化dao
	 */
	private StockInitMapper stockInitMapper;
	/**
	 * 库存dao
	 */
	private StorageSummaryMapper storageSummaryMapper;
	/**
	 * 资金报表
	 */
	private JournalSheetMapper journalSheetMapper;

    private BuySaleReportMapper buySaleReportMapper;

    private CheckListMapper checkListMapper;

	private AllocateOutMapper allocateOutMapper;

    public BuySaleReportMapper getBuySaleReportMapper() {
        return buySaleReportMapper;
    }

    public void setBuySaleReportMapper(BuySaleReportMapper buySaleReportMapper) {
        this.buySaleReportMapper = buySaleReportMapper;
    }

    public JournalSheetMapper getJournalSheetMapper() {
		return journalSheetMapper;
	}

	public void setJournalSheetMapper(JournalSheetMapper journalSheetMapper) {
		this.journalSheetMapper = journalSheetMapper;
	}

	public StorageReportMapper getStorageReportMapper() {
		return storageReportMapper;
	}

	public void setStorageReportMapper(StorageReportMapper storageReportMapper) {
		this.storageReportMapper = storageReportMapper;
	}

	public CheckListMapper getCheckListMapper() {
		return checkListMapper;
	}

	public void setCheckListMapper(CheckListMapper checkListMapper) {
		this.checkListMapper = checkListMapper;
	}

	public StockInitMapper getStockInitMapper() {
		return stockInitMapper;
	}

	public void setStockInitMapper(StockInitMapper stockInitMapper) {
		this.stockInitMapper = stockInitMapper;
	}
	
	public StorageSummaryMapper getStorageSummaryMapper() {
		return storageSummaryMapper;
	}

	public void setStorageSummaryMapper(StorageSummaryMapper storageSummaryMapper) {
		this.storageSummaryMapper = storageSummaryMapper;
	}

	public AllocateOutMapper getAllocateOutMapper() {
		return allocateOutMapper;
	}

	public void setAllocateOutMapper(AllocateOutMapper allocateOutMapper) {
		this.allocateOutMapper = allocateOutMapper;
	}

	@Override
	public boolean addStorageInoutReportByDay(String date) throws Exception {
		List<StorageInOutReport> list = storageReportMapper.showInOutReportDataByDate(date);
		String yesday = CommonUtils.getYestodayByDate(date);
		for(StorageInOutReport storageInOutReport : list){
			//获取离日期最近的数据
			StorageInOutReport yesStorageInOutReport = storageReportMapper.getInOutReportDataInLastByDateAndGoodsid(yesday, storageInOutReport.getGoodsid(),storageInOutReport.getStorageid());
			if(null!=yesStorageInOutReport){
				//初始数量金额=前一天的期末数量金额
				storageInOutReport.setInitnum(yesStorageInOutReport.getEndnum());
				storageInOutReport.setInitamount(yesStorageInOutReport.getEndamount());
				//期末数量金额 = 初始数量金额 - 出库数量金额 + 入库数量金额
				storageInOutReport.setEndnum(storageInOutReport.getInitnum().subtract(storageInOutReport.getOutnum()).add(storageInOutReport.getEnternum()));
                BigDecimal costprice = getGoodsCostprice(storageInOutReport.getStorageid(),storageInOutReport.getGoodsid());
                //期末金额 = 数量*成本价
                storageInOutReport.setEndamount(storageInOutReport.getEndnum().multiply(costprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}else{
				//没有前一天的进销存汇总数据 则获取库存初始化的数据
				StockInit stockInit = stockInitMapper.getStockInitSumByGoodsidAndStorageid(storageInOutReport.getGoodsid(), storageInOutReport.getStorageid());
				if(null!=stockInit){
					//初始数量金额=库存初始化数量金额
					storageInOutReport.setInitnum(stockInit.getUnitnum());
					//期末数量金额 = 初始数量金额 - 出库数量金额 + 入库数量金额
					storageInOutReport.setEndnum(storageInOutReport.getInitnum().subtract(storageInOutReport.getOutnum()).add(storageInOutReport.getEnternum()));
                    //成本价
                    BigDecimal costprice = getGoodsCostprice(storageInOutReport.getStorageid(),storageInOutReport.getGoodsid());
                    //期初金额 = 数量*成本价
                    storageInOutReport.setInitamount(stockInit.getUnitnum().multiply(costprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
                    //期末金额 = 数量*成本价
                    storageInOutReport.setEndamount(storageInOutReport.getEndnum().multiply(costprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				}else{
					//初始数量金额=0
					storageInOutReport.setInitnum(BigDecimal.ZERO);
					storageInOutReport.setInitamount(BigDecimal.ZERO);
					//期末数量金额 = 初始数量金额 - 出库数量金额 + 入库数量金额
					storageInOutReport.setEndnum(storageInOutReport.getInitnum().subtract(storageInOutReport.getOutnum()).add(storageInOutReport.getEnternum()));
                    //成本价
                    BigDecimal costprice = getGoodsCostprice(storageInOutReport.getStorageid(),storageInOutReport.getGoodsid());
                    //期末金额 = 数量*成本价
                    storageInOutReport.setEndamount(storageInOutReport.getEndnum().multiply(costprice).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
				}
			}
			storageInOutReport.setBusinessdate(date);
			GoodsInfo goodsInfo = getGoodsInfoByID(storageInOutReport.getGoodsid());
			if(null!=goodsInfo){
				storageInOutReport.setUnitid(goodsInfo.getMainunit());
				storageInOutReport.setUnitname(goodsInfo.getMainunitName());
				storageInOutReport.setBrandid(goodsInfo.getBrand());
				MeteringUnit meteringUnit = getGoodsAuxUnitInfoByGoodsid(goodsInfo.getId());
				if(null!=meteringUnit){
					storageInOutReport.setAuxunitid(meteringUnit.getId());
					storageInOutReport.setAuxunitname(meteringUnit.getName());
				}
			}
		}
		if(null!=list && list.size()>0){
			//删除该日期的数据
			storageReportMapper.deleteStorageInOutReportByDate(date);
			int i = storageReportMapper.addStorageInOutReportBatch(list);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public PageData showInOutReportData(PageMap pageMap) throws Exception {
		String businessdate1 = (String) pageMap.getCondition().get("businessdate1");
		String businessdate2 = (String) pageMap.getCondition().get("businessdate2");
		
		String beforedate = "2000-01-01";
		if(StringUtils.isNotEmpty(businessdate1)){
			beforedate = CommonUtils.getBeforeDateInDays(CommonUtils.stringToDate(businessdate1), 1);
		}
		if(StringUtils.isNotEmpty(businessdate2)){
			businessdate2 = CommonUtils.getBeforeDateInDays(CommonUtils.stringToDate(businessdate2), -1);
			pageMap.getCondition().put("businessdate2", businessdate2);
		}
		
		pageMap.getCondition().put("beforedate", beforedate);
		List<StorageInOutReport> list = storageReportMapper.getStorageInOutReportSumDataList(pageMap);
		for(StorageInOutReport storageInOutReport : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(storageInOutReport.getGoodsid());
			StorageInfo storageInfo = getStorageInfoByID(storageInOutReport.getStorageid());
			if(null!=storageInfo){
				storageInOutReport.setStoragename(storageInfo.getName());
			}
			if(null!=goodsInfo){
				storageInOutReport.setBarcode(goodsInfo.getBarcode());
				storageInOutReport.setGoodsname(goodsInfo.getName());
				storageInOutReport.setBrandname(goodsInfo.getBrandName());
				storageInOutReport.setBoxnum(goodsInfo.getBoxnum());
			}
			WaresClass waresClass = getWaresClassByID(storageInOutReport.getGoodssort());
			if(null!=waresClass){
				storageInOutReport.setGoodssortname(waresClass.getName());
			}

			storageInOutReport.setInitauxnumdetail(CommonUtils.strDigitNumDeal(storageInOutReport.getInitauxnumdetail()));
			storageInOutReport.setEnterauxnumdetail(CommonUtils.strDigitNumDeal(storageInOutReport.getEnterauxnumdetail()));
			storageInOutReport.setOutauxnumdetail(CommonUtils.strDigitNumDeal(storageInOutReport.getOutauxnumdetail()));
			storageInOutReport.setEndauxnumdetail(CommonUtils.strDigitNumDeal(storageInOutReport.getEndauxnumdetail()));
		}
		PageData pageData = new PageData(storageReportMapper.getStorageInOutReportSumDataCount(pageMap),list,pageMap);
		StorageInOutReport storageInOutReportSum = storageReportMapper.getStorageInOutReportSumData(pageMap);
		if(null!=storageInOutReportSum){
			storageInOutReportSum.setInitauxnumdetail(CommonUtils.strDigitNumDeal(storageInOutReportSum.getInitauxnum().setScale(3, BigDecimal.ROUND_HALF_UP).toString()+"箱"));
			storageInOutReportSum.setEnterauxnumdetail(CommonUtils.strDigitNumDeal(storageInOutReportSum.getEnterauxnum().setScale(3, BigDecimal.ROUND_HALF_UP).toString()+"箱"));
			storageInOutReportSum.setOutauxnumdetail(CommonUtils.strDigitNumDeal(storageInOutReportSum.getOutauxnum().setScale(3, BigDecimal.ROUND_HALF_UP).toString()+"箱"));
			storageInOutReportSum.setEndauxnumdetail(CommonUtils.strDigitNumDeal(storageInOutReportSum.getEndauxnum().setScale(3, BigDecimal.ROUND_HALF_UP).toString()+"箱"));
			storageInOutReportSum.setStoragename("合计");
			storageInOutReportSum.setUnitname("");
			storageInOutReportSum.setGoodsid("");
			List<StorageInOutReport> footerList = new ArrayList<StorageInOutReport>();
			footerList.add(storageInOutReportSum);
			pageData.setFooter(footerList);
		}
		return pageData; 
	}

    @Override
    public PageData showBuySaleReportData(PageMap pageMap) throws Exception {
        String datasql = getDataAccessRule("t_report_storage_month", "d");
        pageMap.setDataSql(datasql);
        //小计列
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if(!pageMap.getCondition().containsKey("groupcols")){
            groupcols = "goodsid";
        }
        if(StringUtils.isNotEmpty(groupcols)){
            pageMap.getCondition().put("groupcols", groupcols);
        }
        if(pageMap.getCondition().containsKey("deptid")){
            String branddept_sql = "";
            String str = (String) pageMap.getCondition().get("deptid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                if(pageMap.getCondition().containsKey("deptid") && "null".equals(pageMap.getCondition().get("deptid"))){
                    branddept_sql += " and d.deptid = '' ";
                }else{
                    branddept_sql += " and d.deptid like '"+str+"%' ";
                }
            }else{
                String retStr = "";
                String[] branddeptArr = str.split(",");
                for(String branddept : branddeptArr){
                    Map map = new HashMap();
                    map.put("deptid", branddept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if(dList.size() != 0){
                        for(DepartMent departMent : dList){
                            if(org.apache.commons.lang.StringUtils.isNotEmpty(retStr)){
                                retStr += "," + departMent.getId();
                            }else{
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                branddept_sql += " and FIND_IN_SET(d.deptid,'"+retStr+"')";
            }
            pageMap.getCondition().put("branddeptsql",branddept_sql);
        }
        List<StorageBuySaleReport> list = storageReportMapper.showBuySaleReportData(pageMap);
        for(StorageBuySaleReport storageBuySaleReport : list){
            GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
            if(null!=goodsInfo ){
                storageBuySaleReport.setBarcode(goodsInfo.getBarcode());
                storageBuySaleReport.setGoodsname(goodsInfo.getName());
                storageBuySaleReport.setUnitname(goodsInfo.getMainunitName());
                Map auxsafeMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSafenum());
                storageBuySaleReport.setAuxsafenumdetail((String) auxsafeMap.get("auxnumdetail"));
            }else{
                Brand brand = getGoodsBrandByID(storageBuySaleReport.getGoodsid());
                if(null!=brand){
                    storageBuySaleReport.setGoodsname("品牌折扣："+brand.getName());
                }else{
                    storageBuySaleReport.setGoodsname("其他折扣");
                }
            }
			//品牌
			if(StringUtils.isNotEmpty(storageBuySaleReport.getBrandid())){
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getBrandid());
				if(null != brand){
					storageBuySaleReport.setBrandname(brand.getName());
				}else{
					storageBuySaleReport.setBrandname("其他未定义");
				}
			}else{
				storageBuySaleReport.setBrandname("其他未指定");
			}
			//仓库
			if(StringUtils.isNotEmpty(storageBuySaleReport.getStorageid())) {
				StorageInfo storageInfo = getStorageInfoByID(storageBuySaleReport.getStorageid());
				if(null != storageInfo) {
					storageBuySaleReport.setStoragename(storageInfo.getName());
				}
			}
			//品牌部门
			DepartMent departMent = getDepartMentById(storageBuySaleReport.getDeptid());
			if(null != departMent){
				storageBuySaleReport.setDeptname(departMent.getName());
			}
            //供应商
            BuySupplier supplier = getSupplierInfoById(storageBuySaleReport.getSupplierid());
            if(null != supplier){
                storageBuySaleReport.setSuppliername(supplier.getName());
            }else{
                storageBuySaleReport.setSuppliername("其他未定义");
            }

            Map auxInitnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getInitnum());
            storageBuySaleReport.setAuxinitnumdetail((String) auxInitnumMap.get("auxnumdetail"));
            Map buyinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getBuyinnum());
            storageBuySaleReport.setAuxbuyinnumdetail((String) buyinnumMap.get("auxnumdetail"));
            Map buyoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getBuyoutnum());
            storageBuySaleReport.setAuxbuyoutnumdetail((String) buyoutnumMap.get("auxnumdetail"));
            Map auxenternumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getEnternum());
            storageBuySaleReport.setAuxenternumdetail((String) auxenternumMap.get("auxnumdetail"));
            Map saleoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSaleoutnum());
            storageBuySaleReport.setAuxsaleoutnumdetail((String) saleoutnumMap.get("auxnumdetail"));
            Map saleinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSaleinnum());
            storageBuySaleReport.setAuxsaleinnumdetail((String) saleinnumMap.get("auxnumdetail"));
            Map auxoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getOutnum());
            storageBuySaleReport.setAuxoutnumdetail((String) auxoutnumMap.get("auxnumdetail"));
            Map allocateinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getAllocateinnum());
            storageBuySaleReport.setAuxallocateinnumdetail((String) allocateinnumMap.get("auxnumdetail"));
            Map allocateoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getAllocateoutnum());
            storageBuySaleReport.setAuxallocateoutnumdetail((String) allocateoutnumMap.get("auxnumdetail"));
            Map auxlossnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getLossnum());
            storageBuySaleReport.setAuxlossnumdetail((String) auxlossnumMap.get("auxnumdetail"));
            Map auxendnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getEndnum());
            storageBuySaleReport.setAuxendnumdetail((String) auxendnumMap.get("auxnumdetail"));
        }
        PageData pageData = new PageData(storageReportMapper.showBuySaleReportDataCount(pageMap),list,pageMap);
        List<StorageBuySaleReport> footer = storageReportMapper.showBuySaleReportDataSum(pageMap);
        for(StorageBuySaleReport storageBuySaleReport : footer){
            if(null != storageBuySaleReport){
                if(groupcols.indexOf("goodsid") != -1){
                    storageBuySaleReport.setGoodsid("");
                    storageBuySaleReport.setGoodsname("合计");
                }else if(groupcols.indexOf("deptid") != -1){
                    storageBuySaleReport.setDeptid("");
                    storageBuySaleReport.setDeptname("合计");
                }else if(groupcols.indexOf("storageid") != -1){
                    storageBuySaleReport.setStorageid("");
                    storageBuySaleReport.setStoragename("合计");
                }else if(groupcols.indexOf("brandid") != -1){
                    storageBuySaleReport.setBrandid("");
                    storageBuySaleReport.setBrandname("合计");
                }else if(groupcols.indexOf("supplierid") != -1){
                    storageBuySaleReport.setSupplierid("");
                    storageBuySaleReport.setSuppliername("合计");
                }
                storageBuySaleReport.setPrice(null);
                pageData.setFooter(footer);
            }
        }
        return pageData;
    }

	@Override
	public PageData showBuySaleReportMonthData(PageMap pageMap) throws Exception {
		String datasql = getDataAccessRule("t_report_storage_month", "m");
		pageMap.setDataSql(datasql);
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
            groupcols = "goodsid";
		}
        pageMap.getCondition().put("groupcols", groupcols);
        if(pageMap.getCondition().containsKey("deptid")){
            String branddept_sql = "";
            String str = (String) pageMap.getCondition().get("deptid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                if(pageMap.getCondition().containsKey("deptid") && "null".equals(pageMap.getCondition().get("deptid"))){
                    branddept_sql += " and m.deptid = '' ";
                }else{
                    branddept_sql += " and m.deptid like '"+str+"%' ";
                }
            }else{
                String retStr = "";
                String[] branddeptArr = str.split(",");
                for(String branddept : branddeptArr){
                    Map map = new HashMap();
                    map.put("deptid", branddept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if(dList.size() != 0){
                        for(DepartMent departMent : dList){
                            if(org.apache.commons.lang.StringUtils.isNotEmpty(retStr)){
                                retStr += "," + departMent.getId();
                            }else{
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                branddept_sql += " and FIND_IN_SET(m.deptid,'"+retStr+"')";
            }
            pageMap.getCondition().put("branddeptsql",branddept_sql);
        }
		List<StorageBuySaleReport> list = storageReportMapper.showBuySaleReportMonthData(pageMap);
		for(StorageBuySaleReport storageBuySaleReport : list){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
			if(null!=goodsInfo ){
				storageBuySaleReport.setBarcode(goodsInfo.getBarcode());
				storageBuySaleReport.setGoodsname(goodsInfo.getName());
				storageBuySaleReport.setUnitname(goodsInfo.getMainunitName());
				Map auxsafeMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSafenum());
				storageBuySaleReport.setAuxsafenumdetail((String) auxsafeMap.get("auxnumdetail"));
			}else{
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getGoodsid());
				if(null!=brand){
					storageBuySaleReport.setGoodsname("品牌折扣："+brand.getName());
				}else{
					storageBuySaleReport.setGoodsname("其他折扣");
				}
			}
			//品牌
			if(StringUtils.isNotEmpty(storageBuySaleReport.getBrandid())){
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getBrandid());
				if(null != brand){
					storageBuySaleReport.setBrandname(brand.getName());
				}else{
					storageBuySaleReport.setBrandname("其他未定义");
				}
			}else{
				storageBuySaleReport.setBrandname("其他未指定");
			}
			//仓库
			if(StringUtils.isNotEmpty(storageBuySaleReport.getStorageid())) {
				StorageInfo storageInfo = getStorageInfoByID(storageBuySaleReport.getStorageid());
				if(null != storageInfo) {
					storageBuySaleReport.setStoragename(storageInfo.getName());
				}
			}
			//品牌部门
			DepartMent departMent = getDepartMentById(storageBuySaleReport.getDeptid());
			if(null != departMent){
				storageBuySaleReport.setDeptname(departMent.getName());
			}
			//供应商
			BuySupplier supplier = getSupplierInfoById(storageBuySaleReport.getSupplierid());
			if(null != supplier){
				storageBuySaleReport.setSuppliername(supplier.getName());
			}else{
				storageBuySaleReport.setSuppliername("其他未定义");
			}

			Map auxInitnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getInitnum());
			storageBuySaleReport.setAuxinitnumdetail((String) auxInitnumMap.get("auxnumdetail"));
			Map buyinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getBuyinnum());
			storageBuySaleReport.setAuxbuyinnumdetail((String) buyinnumMap.get("auxnumdetail"));
			Map buyoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getBuyoutnum());
			storageBuySaleReport.setAuxbuyoutnumdetail((String) buyoutnumMap.get("auxnumdetail"));
			Map auxenternumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getEnternum());
			storageBuySaleReport.setAuxenternumdetail((String) auxenternumMap.get("auxnumdetail"));
			Map saleoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSaleoutnum());
			storageBuySaleReport.setAuxsaleoutnumdetail((String) saleoutnumMap.get("auxnumdetail"));
			Map saleinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSaleinnum());
			storageBuySaleReport.setAuxsaleinnumdetail((String) saleinnumMap.get("auxnumdetail"));
			Map auxoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getOutnum());
			storageBuySaleReport.setAuxoutnumdetail((String) auxoutnumMap.get("auxnumdetail"));
			Map allocateinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getAllocateinnum());
			storageBuySaleReport.setAuxallocateinnumdetail((String) allocateinnumMap.get("auxnumdetail"));
			Map allocateoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getAllocateoutnum());
			storageBuySaleReport.setAuxallocateoutnumdetail((String) allocateoutnumMap.get("auxnumdetail"));
			Map auxlossnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getLossnum());
			storageBuySaleReport.setAuxlossnumdetail((String) auxlossnumMap.get("auxnumdetail"));
			Map auxendnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getEndnum());
			storageBuySaleReport.setAuxendnumdetail((String) auxendnumMap.get("auxnumdetail"));
		}
		PageData pageData = new PageData(storageReportMapper.showBuySaleReportDataMonthCount(pageMap),list,pageMap);
		List<StorageBuySaleReport> footer = storageReportMapper.showBuySaleReportDataMonthSum(pageMap);
		for(StorageBuySaleReport storageBuySaleReport : footer){
			if(null != storageBuySaleReport){
                if(groupcols.indexOf("goodsid") != -1){
                    storageBuySaleReport.setGoodsid("");
                    storageBuySaleReport.setGoodsname("合计");
                }else if(groupcols.indexOf("deptid") != -1){
					storageBuySaleReport.setDeptid("");
					storageBuySaleReport.setDeptname("合计");
				}else if(groupcols.indexOf("storageid") != -1){
					storageBuySaleReport.setStorageid("");
					storageBuySaleReport.setStoragename("合计");
				}else if(groupcols.indexOf("brandid") != -1){
					storageBuySaleReport.setBrandid("");
					storageBuySaleReport.setBrandname("合计");
				}else if(groupcols.indexOf("supplierid") != -1){
					storageBuySaleReport.setSupplierid("");
					storageBuySaleReport.setSuppliername("合计");
				}
				storageBuySaleReport.setPrice(null);
				pageData.setFooter(footer);
			}
		}
		return pageData;
	}

    @Override
    public PageData getRealBuySaleReportDayData(PageMap pageMap) throws Exception {
        String datasql = getDataAccessRule("t_report_storage_month_real", "d");
        pageMap.setDataSql(datasql);
        //小计列
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if(!pageMap.getCondition().containsKey("groupcols")){
            groupcols = "goodsid";
        }
        if(StringUtils.isNotEmpty(groupcols)){
            pageMap.getCondition().put("groupcols", groupcols);
        }
        if(pageMap.getCondition().containsKey("deptid")){
            String branddept_sql = "";
            String str = (String) pageMap.getCondition().get("deptid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                if(pageMap.getCondition().containsKey("deptid") && "null".equals(pageMap.getCondition().get("deptid"))){
                    branddept_sql += " and d.deptid = '' ";
                }else{
                    branddept_sql += " and d.deptid like '"+str+"%' ";
                }
            }else{
                String retStr = "";
                String[] branddeptArr = str.split(",");
                for(String branddept : branddeptArr){
                    Map map = new HashMap();
                    map.put("deptid", branddept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if(dList.size() != 0){
                        for(DepartMent departMent : dList){
                            if(org.apache.commons.lang.StringUtils.isNotEmpty(retStr)){
                                retStr += "," + departMent.getId();
                            }else{
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                branddept_sql += " and FIND_IN_SET(d.deptid,'"+retStr+"')";
            }
            pageMap.getCondition().put("branddeptsql",branddept_sql);
        }
        List<StorageBuySaleReport> list = storageReportMapper.getRealBuySaleReportDayData(pageMap);
        for(StorageBuySaleReport storageBuySaleReport : list){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
			if(null!=goodsInfo ){
				storageBuySaleReport.setBarcode(goodsInfo.getBarcode());
				storageBuySaleReport.setGoodsname(goodsInfo.getName());
				storageBuySaleReport.setUnitname(goodsInfo.getMainunitName());
				Map auxsafeMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSafenum());
				storageBuySaleReport.setAuxsafenumdetail((String) auxsafeMap.get("auxnumdetail"));
			}else{
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getGoodsid());
				if(null!=brand){
					storageBuySaleReport.setGoodsname("品牌折扣："+brand.getName());
				}else{
					storageBuySaleReport.setGoodsname("其他折扣");
				}
			}
			//品牌
			if(StringUtils.isNotEmpty(storageBuySaleReport.getBrandid())){
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getBrandid());
				if(null != brand){
					storageBuySaleReport.setBrandname(brand.getName());
				}else{
					storageBuySaleReport.setBrandname("其他未定义");
				}
			}else{
				storageBuySaleReport.setBrandname("其他未指定");
			}
			//仓库
			if(StringUtils.isNotEmpty(storageBuySaleReport.getStorageid())) {
				StorageInfo storageInfo = getStorageInfoByID(storageBuySaleReport.getStorageid());
				if(null != storageInfo) {
					storageBuySaleReport.setStoragename(storageInfo.getName());
				}
			}
			//品牌部门
			DepartMent departMent = getDepartMentById(storageBuySaleReport.getDeptid());
			if(null != departMent){
				storageBuySaleReport.setDeptname(departMent.getName());
			}
			//供应商
			BuySupplier supplier = getSupplierInfoById(storageBuySaleReport.getSupplierid());
			if(null != supplier){
				storageBuySaleReport.setSuppliername(supplier.getName());
			}else{
				storageBuySaleReport.setSuppliername("其他未定义");
			}

            Map auxInitnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getInitnum());
            storageBuySaleReport.setAuxinitnumdetail((String) auxInitnumMap.get("auxnumdetail"));
            Map buyinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getBuyinnum());
            storageBuySaleReport.setAuxbuyinnumdetail((String) buyinnumMap.get("auxnumdetail"));
            Map buyoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getBuyoutnum());
            storageBuySaleReport.setAuxbuyoutnumdetail((String) buyoutnumMap.get("auxnumdetail"));
            Map auxenternumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getEnternum());
            storageBuySaleReport.setAuxenternumdetail((String) auxenternumMap.get("auxnumdetail"));
            Map saleoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSaleoutnum());
            storageBuySaleReport.setAuxsaleoutnumdetail((String) saleoutnumMap.get("auxnumdetail"));
            Map saleinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSaleinnum());
            storageBuySaleReport.setAuxsaleinnumdetail((String) saleinnumMap.get("auxnumdetail"));
            Map auxoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getOutnum());
            storageBuySaleReport.setAuxoutnumdetail((String) auxoutnumMap.get("auxnumdetail"));
            Map allocateinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getAllocateinnum());
            storageBuySaleReport.setAuxallocateinnumdetail((String) allocateinnumMap.get("auxnumdetail"));
            Map allocateoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getAllocateoutnum());
            storageBuySaleReport.setAuxallocateoutnumdetail((String) allocateoutnumMap.get("auxnumdetail"));
            Map auxlossnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getLossnum());
            storageBuySaleReport.setAuxlossnumdetail((String) auxlossnumMap.get("auxnumdetail"));
            Map auxendnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getEndnum());
            storageBuySaleReport.setAuxendnumdetail((String) auxendnumMap.get("auxnumdetail"));
        }
        PageData pageData = new PageData(storageReportMapper.getRealBuySaleReportDayDataCount(pageMap),list,pageMap);
        List<StorageBuySaleReport> footer = storageReportMapper.getRealBuySaleReportDayDataSum(pageMap);
        for(StorageBuySaleReport storageBuySaleReport : footer){
            if(null != storageBuySaleReport){
                if(groupcols.indexOf("goodsid") != -1){
                    storageBuySaleReport.setGoodsid("");
                    storageBuySaleReport.setGoodsname("合计");
                }else if(groupcols.indexOf("deptid") != -1){
					storageBuySaleReport.setDeptid("");
					storageBuySaleReport.setDeptname("合计");
				}else if(groupcols.indexOf("storageid") != -1){
					storageBuySaleReport.setStorageid("");
					storageBuySaleReport.setStoragename("合计");
				}else if(groupcols.indexOf("brandid") != -1){
					storageBuySaleReport.setBrandid("");
					storageBuySaleReport.setBrandname("合计");
				}else if(groupcols.indexOf("supplierid") != -1){
					storageBuySaleReport.setSupplierid("");
					storageBuySaleReport.setSuppliername("合计");
				}
                storageBuySaleReport.setPrice(null);
                pageData.setFooter(footer);
            }
        }
        return pageData;
    }

    @Override
    public PageData getRealBuySaleReportMonthData(PageMap pageMap) throws Exception {
        String datasql = getDataAccessRule("t_report_storage_month_real", "m");
        pageMap.setDataSql(datasql);
        //小计列
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if(!pageMap.getCondition().containsKey("groupcols")){
            groupcols = "goodsid";
        }
        pageMap.getCondition().put("groupcols", groupcols);
        if(pageMap.getCondition().containsKey("deptid")){
            String branddept_sql = "";
            String str = (String) pageMap.getCondition().get("deptid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                if(pageMap.getCondition().containsKey("deptid") && "null".equals(pageMap.getCondition().get("deptid"))){
                    branddept_sql += " and m.deptid = '' ";
                }else{
                    branddept_sql += " and m.deptid like '"+str+"%' ";
                }
            }else{
                String retStr = "";
                String[] branddeptArr = str.split(",");
                for(String branddept : branddeptArr){
                    Map map = new HashMap();
                    map.put("deptid", branddept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if(dList.size() != 0){
                        for(DepartMent departMent : dList){
                            if(org.apache.commons.lang.StringUtils.isNotEmpty(retStr)){
                                retStr += "," + departMent.getId();
                            }else{
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                branddept_sql += " and FIND_IN_SET(m.deptid,'"+retStr+"')";
            }
            pageMap.getCondition().put("branddeptsql",branddept_sql);
        }
        List<StorageBuySaleReport> list = storageReportMapper.getRealBuySaleReportMonthData(pageMap);
        for(StorageBuySaleReport storageBuySaleReport : list){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(storageBuySaleReport.getGoodsid());
			if(null!=goodsInfo ){
				storageBuySaleReport.setBarcode(goodsInfo.getBarcode());
				storageBuySaleReport.setGoodsname(goodsInfo.getName());
				storageBuySaleReport.setUnitname(goodsInfo.getMainunitName());
				Map auxsafeMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSafenum());
				storageBuySaleReport.setAuxsafenumdetail((String) auxsafeMap.get("auxnumdetail"));
			}else{
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getGoodsid());
				if(null!=brand){
					storageBuySaleReport.setGoodsname("品牌折扣："+brand.getName());
				}else{
					storageBuySaleReport.setGoodsname("其他折扣");
				}
			}
			//品牌
			if(StringUtils.isNotEmpty(storageBuySaleReport.getBrandid())){
				Brand brand = getGoodsBrandByID(storageBuySaleReport.getBrandid());
				if(null != brand){
					storageBuySaleReport.setBrandname(brand.getName());
				}else{
					storageBuySaleReport.setBrandname("其他未定义");
				}
			}else{
				storageBuySaleReport.setBrandname("其他未指定");
			}
			//仓库
			if(StringUtils.isNotEmpty(storageBuySaleReport.getStorageid())) {
				StorageInfo storageInfo = getStorageInfoByID(storageBuySaleReport.getStorageid());
				if(null != storageInfo) {
					storageBuySaleReport.setStoragename(storageInfo.getName());
				}
			}
			//品牌部门
			DepartMent departMent = getDepartMentById(storageBuySaleReport.getDeptid());
			if(null != departMent){
				storageBuySaleReport.setDeptname(departMent.getName());
			}
			//供应商
			BuySupplier supplier = getSupplierInfoById(storageBuySaleReport.getSupplierid());
			if(null != supplier){
				storageBuySaleReport.setSuppliername(supplier.getName());
			}else{
				storageBuySaleReport.setSuppliername("其他未定义");
			}

            Map auxInitnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getInitnum());
            storageBuySaleReport.setAuxinitnumdetail((String) auxInitnumMap.get("auxnumdetail"));
            Map buyinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getBuyinnum());
            storageBuySaleReport.setAuxbuyinnumdetail((String) buyinnumMap.get("auxnumdetail"));
            Map buyoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getBuyoutnum());
            storageBuySaleReport.setAuxbuyoutnumdetail((String) buyoutnumMap.get("auxnumdetail"));
            Map auxenternumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getEnternum());
            storageBuySaleReport.setAuxenternumdetail((String) auxenternumMap.get("auxnumdetail"));
            Map saleoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSaleoutnum());
            storageBuySaleReport.setAuxsaleoutnumdetail((String) saleoutnumMap.get("auxnumdetail"));
            Map saleinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getSaleinnum());
            storageBuySaleReport.setAuxsaleinnumdetail((String) saleinnumMap.get("auxnumdetail"));
            Map auxoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getOutnum());
            storageBuySaleReport.setAuxoutnumdetail((String) auxoutnumMap.get("auxnumdetail"));
            Map allocateinnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getAllocateinnum());
            storageBuySaleReport.setAuxallocateinnumdetail((String) allocateinnumMap.get("auxnumdetail"));
            Map allocateoutnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getAllocateoutnum());
            storageBuySaleReport.setAuxallocateoutnumdetail((String) allocateoutnumMap.get("auxnumdetail"));
            Map auxlossnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getLossnum());
            storageBuySaleReport.setAuxlossnumdetail((String) auxlossnumMap.get("auxnumdetail"));
            Map auxendnumMap = countGoodsInfoNumber(storageBuySaleReport.getGoodsid(), storageBuySaleReport.getAuxunitid(), storageBuySaleReport.getEndnum());
            storageBuySaleReport.setAuxendnumdetail((String) auxendnumMap.get("auxnumdetail"));
        }
        PageData pageData = new PageData(storageReportMapper.getRealBuySaleReportMonthDataCount(pageMap),list,pageMap);
        List<StorageBuySaleReport> footer = storageReportMapper.getRealBuySaleReportMonthDataSum(pageMap);
        for(StorageBuySaleReport storageBuySaleReport : footer){
            if(null != storageBuySaleReport){
                if(groupcols.indexOf("goodsid") != -1){
                    storageBuySaleReport.setGoodsid("");
                    storageBuySaleReport.setGoodsname("合计");
                }else if(groupcols.indexOf("deptid") != -1){
					storageBuySaleReport.setDeptid("");
					storageBuySaleReport.setDeptname("合计");
				}else if(groupcols.indexOf("storageid") != -1){
					storageBuySaleReport.setStorageid("");
					storageBuySaleReport.setStoragename("合计");
				}else if(groupcols.indexOf("brandid") != -1){
					storageBuySaleReport.setBrandid("");
					storageBuySaleReport.setBrandname("合计");
				}else if(groupcols.indexOf("supplierid") != -1){
					storageBuySaleReport.setSupplierid("");
					storageBuySaleReport.setSuppliername("合计");
				}
                storageBuySaleReport.setPrice(null);
                pageData.setFooter(footer);
            }
        }
        return pageData;
    }

    /**
     * 添加每日进销存数据（鸿都）
     * 成本价：realcostprice
     * @param date
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-02-19
     */
    private boolean addBuySaleReportDayHD(String date)throws Exception{
        String yesdate = CommonUtils.getYestodayByDate(date);
        //进销存中前一天是否有数据，若有，取其期末数据做期初数据，若无，0做期初数据
        int _storageday_count = buySaleReportMapper.getBuySaleReportDayCountByDate(yesdate);
        //添加数据
        Map map = new HashMap();
        map.put("businessdate",date);
        map.put("yesdate",yesdate);
        map.put("_storageday_count",_storageday_count);
        buySaleReportMapper.addBuySaleReportDayHD(map);
        //仓库是否独立核算 0否1是 默认否
//        String isStorageAccount = getSysParamValue("IsStorageAccount");
//        if("1".equals(isStorageAccount)){
//            buySaleReportMapper.updateBuySaleReprotDayPrice(date);
//        }
        return true;
    }

	/**
	 * 添加每日进销存数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2015-12-14
	 */
	private boolean addBuySaleReportDay(String date)throws Exception{
		String yesdate = CommonUtils.getYestodayByDate(date);
		//进销存中前一天是否有数据，若有，取其期末数据做期初数据，若无，0做期初数据
		int _storageday_count = buySaleReportMapper.getBuySaleReportDayCountByDate(yesdate);
		//添加数据
		Map map = new HashMap();
		map.put("businessdate",date);
		map.put("yesdate",yesdate);
		map.put("_storageday_count",_storageday_count);
		buySaleReportMapper.addBuySaleReportDay(map);
        //仓库是否独立核算 0否1是 默认否
//        String isStorageAccount = getSysParamValue("IsStorageAccount");
//        if("1".equals(isStorageAccount)){
//            buySaleReportMapper.updateBuySaleReprotDayPrice(date);
//        }
		return true;
	}

    /**
     * 添加每月进销存数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-28
     */
    private boolean addBuySaleReportMonth(String datestr)throws Exception{
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM");
        String daymonth = sf2.format(sf.parse(datestr));
        String yesmonthstr = CommonUtils.getPrevMonthDay(sf.parse(datestr));
        String yesmonth = sf2.format(sf.parse(yesmonthstr));
        //进销存中上月是否有数据，若有，取其期末数据做期初数据，若无，0做期初数据
        int _storageday_count = buySaleReportMapper.getBuySaleReportMonthCountByDate(yesmonth);
        //添加数据
        Map map = new HashMap();
        map.put("daymonth",daymonth);
        map.put("yesmonth",yesmonth);
        map.put("_storageday_count",_storageday_count);
        buySaleReportMapper.addBuySaleReportMonth(map);
        //仓库是否独立核算 0否1是 默认否
//        String isStorageAccount = getSysParamValue("IsStorageAccount");
//        if("1".equals(isStorageAccount)){
//            buySaleReportMapper.updateBuySaleReprotMonthPrice(daymonth);
//        }
        return true;
    }

    /**
     * 添加每月进销存数据（鸿都）
     * 成本价：realcostprice
     * @param datestr
     * @return
     * @throws Exception
     */
    private boolean addBuySaleReportMonthHD(String datestr)throws Exception{
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM");
        String daymonth = sf2.format(sf.parse(datestr));
        String yesmonthstr = CommonUtils.getPrevMonthDay(sf.parse(datestr));
        String yesmonth = sf2.format(sf.parse(yesmonthstr));
        //进销存中上月是否有数据，若有，取其期末数据做期初数据，若无，0做期初数据
        int _storageday_count = buySaleReportMapper.getBuySaleReportMonthCountByDate(yesmonth);
        //添加数据
        Map map = new HashMap();
        map.put("daymonth",daymonth);
        map.put("yesmonth",yesmonth);
        map.put("_storageday_count",_storageday_count);
        buySaleReportMapper.addBuySaleReportMonthHD(map);
        //仓库是否独立核算 0否1是 默认否
//        String isStorageAccount = getSysParamValue("IsStorageAccount");
//        if("1".equals(isStorageAccount)){
//            buySaleReportMapper.updateBuySaleReprotMonthPrice(daymonth);
//        }
        return true;
    }

    /**
     * 添加实际每日进销存数据
     * @param date
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-18
     */
    private boolean addRealBuySaleReportDay(String date)throws Exception{
        String yesdate = CommonUtils.getYestodayByDate(date);
        //进销存中前一天是否有数据，若有，取其期末数据做期初数据，若无，0做期初数据
        int _storageday_count = buySaleReportMapper.getRealBuySaleReportDayCountByDate(yesdate);
        //添加数据
        Map map = new HashMap();
        map.put("businessdate",date);
        map.put("yesdate",yesdate);
        map.put("_storageday_count",_storageday_count);
        buySaleReportMapper.addRealBuySaleReportDay(map);
        //仓库是否独立核算 0否1是 默认否
//        String isStorageAccount = getSysParamValue("IsStorageAccount");
//        if("1".equals(isStorageAccount)){
//            buySaleReportMapper.updateRealBuySaleReprotDayPrice(date);
//        }
        return true;
    }

    /**
     * 添加实际每日进销存数据（鸿都）
     * 成本价：realcostprice
     * @param date
     * @return
     * @throws Exception
     */
    private boolean addRealBuySaleReportDayHD(String date)throws Exception{
        String yesdate = CommonUtils.getYestodayByDate(date);
        //进销存中前一天是否有数据，若有，取其期末数据做期初数据，若无，0做期初数据
        int _storageday_count = buySaleReportMapper.getRealBuySaleReportDayCountByDate(yesdate);
        //添加数据
        Map map = new HashMap();
        map.put("businessdate",date);
        map.put("yesdate",yesdate);
        map.put("_storageday_count",_storageday_count);
        buySaleReportMapper.addRealBuySaleReportDayHD(map);
        //仓库是否独立核算 0否1是 默认否
//        String isStorageAccount = getSysParamValue("IsStorageAccount");
//        if("1".equals(isStorageAccount)){
//            buySaleReportMapper.updateRealBuySaleReprotDayPrice(date);
//        }
        return true;
    }

    /**
     * 添加实际每月进销存数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-28
     */
    private boolean addRealBuySaleReportMonth(String datestr)throws Exception{
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM");
        String daymonth = sf2.format(sf.parse(datestr));
        String yesmonthstr = CommonUtils.getPrevMonthDay(sf.parse(datestr));
        String yesmonth = sf2.format(sf.parse(yesmonthstr));
        //进销存中上月是否有数据，若有，取其期末数据做期初数据，若无，0做期初数据
        int _storageday_count = buySaleReportMapper.getRealBuySaleReportMonthCountByDate(yesmonth);
        //添加数据
        Map map = new HashMap();
        map.put("daymonth",daymonth);
        map.put("yesmonth",yesmonth);
        map.put("_storageday_count",_storageday_count);
        buySaleReportMapper.addRealBuySaleReportMonth(map);
        //仓库是否独立核算 0否1是 默认否
//        String isStorageAccount = getSysParamValue("IsStorageAccount");
//        if("1".equals(isStorageAccount)){
//            buySaleReportMapper.updateRealBuySaleReprotMonthPrice(daymonth);
//        }
        return true;
    }

    /**
     * 添加实际每月进销存数据（鸿都）
     * 成本价：realcostprice
     * @param datestr
     * @return
     * @throws Exception
     */
    private boolean addRealBuySaleReportMonthHD(String datestr)throws Exception{
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM");
        String daymonth = sf2.format(sf.parse(datestr));
        String yesmonthstr = CommonUtils.getPrevMonthDay(sf.parse(datestr));
        String yesmonth = sf2.format(sf.parse(yesmonthstr));
        //进销存中上月是否有数据，若有，取其期末数据做期初数据，若无，0做期初数据
        int _storageday_count = buySaleReportMapper.getRealBuySaleReportMonthCountByDate(yesmonth);
        //添加数据
        Map map = new HashMap();
        map.put("daymonth",daymonth);
        map.put("yesmonth",yesmonth);
        map.put("_storageday_count",_storageday_count);
        buySaleReportMapper.addRealBuySaleReportMonthHD(map);
        //仓库是否独立核算 0否1是 默认否
//        String isStorageAccount = getSysParamValue("IsStorageAccount");
//        if("1".equals(isStorageAccount)){
//            buySaleReportMapper.updateRealBuySaleReprotMonthPrice(daymonth);
//        }
        return true;
    }

    @Override
    public boolean clearResetBuySaleReportData(String date) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = sf.parse("2013-12-30");
        //截断每日进销存表数据
        buySaleReportMapper.clearBuySaleReportData();

        //获取当前版本（鸿都1 或 通用版0）默认通用版
        String version = "0";
        SysParam versionParam = getBaseSysParamMapper().getSysParam("buySaleReportVersion");
        if(null != versionParam){
            version = versionParam.getPvalue();
        }

        //添加历史每日进销存数据，截止到昨天
        Date today = sf.parse(date);
        if("0".equals(version)){
            while (date2.before(today)){
                String date2str = sf.format(date2);
                addBuySaleReportDay(date2str);

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date2);
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar.getTime();
            }
        }else if("1".equals(version)){
            while (date2.before(today)){
                String date2str = sf.format(date2);
                addBuySaleReportDayHD(date2str);

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date2);
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar.getTime();
            }
        }
        return true;
    }

    @Override
    public boolean clearResetBuySaleReportMonthData(String date) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM");
        Date month2 = sf2.parse("2013-12-01");
        //截断每月进销存表数据
        buySaleReportMapper.clearBuySaleReportMonthData();

        //获取当前版本（鸿都1 或 通用版0）默认通用版
        String version = "0";
        SysParam versionParam = getBaseSysParamMapper().getSysParam("buySaleReportVersion");
        if(null != versionParam){
            version = versionParam.getPvalue();
        }

        //添加历史每月进销存数据，截止到上个月
        Date daymonth = sf2.parse(date);
        if("0".equals(version)){
            while (month2.before(daymonth) || month2.equals(daymonth)){
                String month2str = sf.format(month2);
                addBuySaleReportMonth(month2str);

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(month2);
                calendar.add(calendar.MONTH,1);//把日期往后增加一月.整数往后推,负数往前移动
                month2=calendar.getTime();
            }
        }else if("1".equals(version)){
            while (month2.before(daymonth) || month2.equals(daymonth)){
                String month2str = sf.format(month2);
                addBuySaleReportMonthHD(month2str);

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(month2);
                calendar.add(calendar.MONTH,1);//把日期往后增加一月.整数往后推,负数往前移动
                month2=calendar.getTime();
            }
        }
        return true;
    }

    @Override
    public boolean clearResetRealBuySaleReportData(String date) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = sf.parse("2013-12-30");
        //截断实际每日进销存表数据
        buySaleReportMapper.clearRealBuySaleReportData();

        //获取当前版本（鸿都1 或 通用版0）默认通用版
        String version = "0";
        SysParam versionParam = getBaseSysParamMapper().getSysParam("buySaleReportVersion");
        if(null != versionParam){
            version = versionParam.getPvalue();
        }
        //添加历史实际每日进销存数据，截止到昨天
        Date today = sf.parse(date);
        if("0".equals(version)){
            while (date2.before(today)){
                String date2str = sf.format(date2);
                addRealBuySaleReportDay(date2str);


                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date2);
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar.getTime();
            }
        }else if("1".equals(version)){
            while (date2.before(today)){
                String date2str = sf.format(date2);
                addRealBuySaleReportDayHD(date2str);


                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date2);
                calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar.getTime();
            }
        }
        return true;
    }

    @Override
    public boolean clearResetRealBuySaleReportMonthData(String date) throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM");
        Date month2 = sf2.parse("2013-12-01");
        //截断每月进销存表数据
        buySaleReportMapper.clearRealBuySaleReportMonthData();

        //获取当前版本（鸿都1 或 通用版0）默认通用版
        String version = "0";
        SysParam versionParam = getBaseSysParamMapper().getSysParam("buySaleReportVersion");
        if(null != versionParam){
            version = versionParam.getPvalue();
        }
        //添加历史每月进销存数据，截止到上个月
        Date daymonth = sf2.parse(date);
        if("0".equals(version)){
            while (month2.before(daymonth) || month2.equals(daymonth)){
                String month2str = sf.format(month2);
                addRealBuySaleReportMonth(month2str);

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(month2);
                calendar.add(calendar.MONTH,1);//把日期往后增加一月.整数往后推,负数往前移动
                month2=calendar.getTime();
            }
        }else if("1".equals(version)){
            while (month2.before(daymonth) || month2.equals(daymonth)){
                String month2str = sf.format(month2);
                addRealBuySaleReportMonthHD(month2str);

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(month2);
                calendar.add(calendar.MONTH,1);//把日期往后增加一月.整数往后推,负数往前移动
                month2=calendar.getTime();
            }
        }
        return true;
    }

    @Override
	public PageData showInOutFlowListData(PageMap pageMap) throws Exception {
		String datasql = getDataAccessRule("t_report_storageinout_flow", "z");
		pageMap.setDataSql(datasql);
        BigDecimal storagePriceAmountSum = new BigDecimal(0) ;
		List<StorageInOutFlow> list = storageReportMapper.showInOutFlowListData(pageMap);
		for(StorageInOutFlow storageInOutFlow : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(storageInOutFlow.getGoodsid());
			if(null!=goodsInfo){
				storageInOutFlow.setGoodsname(goodsInfo.getName());
				storageInOutFlow.setBrandid(goodsInfo.getBrand());
				storageInOutFlow.setBrandname(goodsInfo.getBrandName());
				storageInOutFlow.setBarcode(goodsInfo.getBarcode());
				storageInOutFlow.setBoxnum(goodsInfo.getBoxnum());
				storageInOutFlow.setSpell(goodsInfo.getSpell());
			}
			//客户
			if(StringUtils.isNotEmpty(storageInOutFlow.getCustomerid())){
				Customer customer = getCustomerByID(storageInOutFlow.getCustomerid());
				if(null != customer){
					storageInOutFlow.setCustomername(customer.getName());
					storageInOutFlow.setCustomersort(customer.getCustomersort());
					CustomerSort customerSort = getCustomerSortByID(customer.getCustomersort());
					if(null != customerSort){
						storageInOutFlow.setCustomersortname(customerSort.getThisname());
					}
				}
			}
			StorageInfo storageInfo = getStorageInfoByID(storageInOutFlow.getStorageid());
			if(null!=storageInfo){
				storageInOutFlow.setStoragename(storageInfo.getName());
			}
			if(null != storageInOutFlow.getBilltype()){
				if("0".equals(storageInOutFlow.getBilltype())){
					storageInOutFlow.setBilltypename("采购入库单");
        		}else if("1".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("销售退货入库单");
        		}else if("2".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("其他入库单");
        		}else if("3".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("调拨单");
        		}else if("4".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("报溢调账单");
        		}else if("41".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("报损调账单");
        		}else if("5".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("发货单");
        		}else if("6".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("采购退货出库单");
        		}else if("7".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("其他出库单");
        		}
			}
			BigDecimal storagePriceAmount=new BigDecimal("0");

			BigDecimal enternum=storageInOutFlow.getEnternum();
			BigDecimal outnum=storageInOutFlow.getOutnum();
			BigDecimal storagePrice=getGoodsCostprice(storageInOutFlow.getStorageid(),storageInOutFlow.getGoodsid());
			if(enternum.compareTo(BigDecimal.ZERO)!=0){
				storagePriceAmount=enternum.multiply(storagePrice);
			}else if(outnum.compareTo(BigDecimal.ZERO)!=0){
				storagePriceAmount=outnum.multiply(storagePrice);
			}
            storagePriceAmountSum = storagePriceAmountSum.add(storagePriceAmount);
			storageInOutFlow.setStoragePrice(storagePrice);
			storageInOutFlow.setStoragePriceAmount(storagePriceAmount);

		}
		PageData pageData = new PageData(storageReportMapper.showInOutFlowListCount(pageMap),list,pageMap);
		StorageInOutFlow storageInOutFlowSum = storageReportMapper.showInOutFlowListSum(pageMap);
		if(null!=storageInOutFlowSum){
			storageInOutFlowSum.setStoragePriceAmount(storagePriceAmountSum);//成本金额合计
			storageInOutFlowSum.setStoragename("合计");
			if(null!=storageInOutFlowSum.getAuxenternum() && null!=storageInOutFlowSum.getAuxenterremainder()){
				storageInOutFlowSum.setAuxenternumdetail(storageInOutFlowSum.getAuxenternum().setScale(0,BigDecimal.ROUND_HALF_UP).toString()+","+storageInOutFlowSum.getAuxenterremainder().setScale(0,BigDecimal.ROUND_HALF_UP).toString());
			}
			if(null!=storageInOutFlowSum.getAuxoutnum() && null!=storageInOutFlowSum.getAuxoutremainder()){
				storageInOutFlowSum.setAuxoutnumdetail(storageInOutFlowSum.getAuxoutnum().setScale(0,BigDecimal.ROUND_HALF_UP).toString()+","+storageInOutFlowSum.getAuxoutremainder().setScale(0,BigDecimal.ROUND_HALF_UP).toString());
			}
			List<StorageInOutFlow> footer = new ArrayList<StorageInOutFlow>();
			footer.add(storageInOutFlowSum);
			pageData.setFooter(footer);
		}
		
		return pageData;
	}

    @Override
	public PageData showDeliveryInOutFlowListData(PageMap pageMap) throws Exception {
		String datasql = getDataAccessRule("t_report_storageinout_flow", "z");
		pageMap.setDataSql(datasql);
        BigDecimal storagePriceAmountSum = new BigDecimal(0) ;
		List<StorageInOutFlow> list = storageReportMapper.showDeliveryInOutFlowListData(pageMap);
		for(StorageInOutFlow storageInOutFlow : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(storageInOutFlow.getGoodsid());
			if(null!=goodsInfo){
				storageInOutFlow.setGoodsname(goodsInfo.getName());
				storageInOutFlow.setBrandid(goodsInfo.getBrand());
				storageInOutFlow.setBrandname(goodsInfo.getBrandName());
				storageInOutFlow.setBarcode(goodsInfo.getBarcode());
			}
			//客户
			if(StringUtils.isNotEmpty(storageInOutFlow.getCustomerid())){
				Customer customer = getCustomerByID(storageInOutFlow.getCustomerid());
				if(null != customer){
					storageInOutFlow.setCustomername(customer.getName());
				}
			}
			StorageInfo storageInfo = getStorageInfoByID(storageInOutFlow.getStorageid());
			if(null!=storageInfo){
				storageInOutFlow.setStoragename(storageInfo.getName());
			}
			if(null != storageInOutFlow.getBilltype()){
				if("1".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("代配送采购入库单");
        		}else if("2".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("代配送采购退货出库单");
        		}else if("3".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("代配送销售出库单");
        		}else if("4".equals(storageInOutFlow.getBilltype())){
        			storageInOutFlow.setBilltypename("代配送销售退货入库单");
        		}
			}
			BigDecimal storagePriceAmount=new BigDecimal("0");

			BigDecimal enternum=storageInOutFlow.getEnternum();
			BigDecimal outnum=storageInOutFlow.getOutnum();
			BigDecimal storagePrice=getGoodsCostprice(storageInOutFlow.getStorageid(),storageInOutFlow.getGoodsid());
			if(enternum.compareTo(BigDecimal.ZERO)!=0){
				storagePriceAmount=enternum.multiply(storagePrice);
			}else if(outnum.compareTo(BigDecimal.ZERO)!=0){
				storagePriceAmount=outnum.multiply(storagePrice);
			}
            storagePriceAmountSum = storagePriceAmountSum.add(storagePriceAmount);
			storageInOutFlow.setStoragePrice(storagePrice);
			storageInOutFlow.setStoragePriceAmount(storagePriceAmount);

		}
		PageData pageData = new PageData(storageReportMapper.showDeliveryInOutFlowListCount(pageMap),list,pageMap);
		StorageInOutFlow storageInOutFlowSum = storageReportMapper.showDeliveryInOutFlowListSum(pageMap);
		if(null!=storageInOutFlowSum){
			storageInOutFlowSum.setStoragePriceAmount(storagePriceAmountSum);//成本金额合计
			storageInOutFlowSum.setStoragename("合计");
			if(null!=storageInOutFlowSum.getAuxenternum() && null!=storageInOutFlowSum.getAuxenterremainder()){
				storageInOutFlowSum.setAuxenternumdetail(storageInOutFlowSum.getAuxenternum().setScale(0,BigDecimal.ROUND_HALF_UP).toString()+","+storageInOutFlowSum.getAuxenterremainder().setScale(0,BigDecimal.ROUND_HALF_UP).toString());
			}
			if(null!=storageInOutFlowSum.getAuxoutnum() && null!=storageInOutFlowSum.getAuxoutremainder()){
				storageInOutFlowSum.setAuxoutnumdetail(storageInOutFlowSum.getAuxoutnum().setScale(0,BigDecimal.ROUND_HALF_UP).toString()+","+storageInOutFlowSum.getAuxoutremainder().setScale(0,BigDecimal.ROUND_HALF_UP).toString());
			}
			List<StorageInOutFlow> footer = new ArrayList<StorageInOutFlow>();
			footer.add(storageInOutFlowSum);
			pageData.setFooter(footer);
		}
		
		return pageData;
	}
    
    
	@Override
	public PageData showCheckReportData(PageMap pageMap) throws Exception {
		List<Map<String,Object>> list = storageReportMapper.showCheckReportDataList(pageMap);
		int count = storageReportMapper.showCheckReportDataCount(pageMap);
		for(Map<String,Object> map : list){
			String storageid = (String) map.get("storageid");
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			if(null!=storageInfo){
				map.put("storagename", storageInfo.getName());
			}
			String userid = (String) map.get("checkuserid");
			Personnel personnel = getPersonnelById(userid);
			if(null!=personnel){
				map.put("checkusername", personnel.getName());
			}
			BigDecimal checknum = (BigDecimal) map.get("checknum");
			BigDecimal truenum3 = (BigDecimal) map.get("truenum3");
			BigDecimal truenum2 = (BigDecimal) map.get("truenum2");
			BigDecimal truenum1 = (BigDecimal) map.get("truenum1");
			
			BigDecimal truecount = BigDecimal.ZERO;
			BigDecimal truerate = BigDecimal.ZERO;
			if(truenum1.compareTo(BigDecimal.ZERO)==1 && checknum.compareTo(BigDecimal.ZERO)==1){
				BigDecimal truerate1 = truenum1.divide(checknum,6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
				truerate = truerate.add(truerate1);
				if(truerate1.compareTo(BigDecimal.ZERO)==1){
					map.put("truerate1", truerate1.toString()+"%");
					truecount = truecount.add(BigDecimal.ONE);
				}
			}
			if(truenum2.compareTo(BigDecimal.ZERO)==1 && checknum.compareTo(BigDecimal.ZERO)==1){
				BigDecimal truerate2 = truenum2.divide(checknum,6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
//				truerate = truerate.add(truerate2);
				if(truerate2.compareTo(BigDecimal.ZERO)==1){
					map.put("truerate2", truerate2.toString()+"%");
//					truecount = truecount.add(BigDecimal.ONE);
				}
			}
			if(truenum2.compareTo(truenum1)!=-1){
				if(truenum3.compareTo(BigDecimal.ZERO)==1 && checknum.compareTo(BigDecimal.ZERO)==1){
					BigDecimal truerate3 = truenum3.divide(checknum,6,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
					if(truenum3.compareTo(truenum1)!=-1){
						truerate = truerate.add(truerate3);
						truecount = truecount.add(BigDecimal.ONE);
					}
					if(truerate3.compareTo(BigDecimal.ZERO)==1){
						map.put("truerate3", truerate3.toString()+"%");
					}
				}
			}else{
				map.put("truenum3", BigDecimal.ZERO);
			}
			
			if(truecount.compareTo(BigDecimal.ZERO)==1){
				truerate  = truerate.divide(truecount, 2, BigDecimal.ROUND_HALF_UP);
				map.put("truerate", truerate.toString()+"%");
			}else{
				map.put("truerate", "0.00%");
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		return pageData;
	}

	@Override
	public boolean addStorageNumEveryday(String yesdate) throws Exception {
        boolean retflag = true;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(yesdate);
        ISysParamService sysParamService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
        SysParam sysParam = sysParamService.getSysParam("unauditDays");
        if(null != sysParam){
            int days = Integer.parseInt(sysParam.getPvalue());
            Date date =sdf.parse(yesdate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-days);

            String langdate = sdf.format(calendar.getTime());
            for(int i=0;i<=days;i++){
                Date date2 =sdf.parse(langdate);
                if(!date2.after(date1)){

                    boolean flag = addBuySaleReportDay(langdate);
                    retflag = retflag && flag;
                    //实际每日进销存
                    boolean realflag = addRealBuySaleReportDay(langdate);
                    retflag = retflag && realflag;

                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(date2);
                    calendar2.set(Calendar.DATE,calendar2.get(Calendar.DATE)+1);
                    langdate = sdf.format(calendar2.getTime());
                }
            }
        }else{
            boolean flag = addBuySaleReportDay(yesdate);
            retflag = retflag && flag;
            //实际每日进销存
            boolean realflag = addRealBuySaleReportDay(yesdate);
            retflag = retflag && realflag;
        }
		return retflag;
	}

    @Override
    public boolean addStorageNumEverydayHD(String yesdate) throws Exception {
        boolean retflag = true;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(yesdate);
        ISysParamService sysParamService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
        SysParam sysParam = sysParamService.getSysParam("unauditDays");
        if(null != sysParam){
            int days = Integer.parseInt(sysParam.getPvalue());
            Date date =sdf.parse(yesdate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-days);

            String langdate = sdf.format(calendar.getTime());
            for(int i=0;i<=days;i++){
                Date date2 =sdf.parse(langdate);
                if(!date2.after(date1)){

                    boolean flag = addBuySaleReportDayHD(langdate);
                    retflag = retflag && flag;
                    //实际每日进销存
                    boolean realflag = addRealBuySaleReportDayHD(langdate);
                    retflag = retflag && realflag;

                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(date2);
                    calendar2.set(Calendar.DATE,calendar2.get(Calendar.DATE)+1);
                    langdate = sdf.format(calendar2.getTime());
                }
            }
        }else{
            boolean flag = addBuySaleReportDayHD(yesdate);
            retflag = retflag && flag;
            //实际每日进销存
            boolean realflag = addRealBuySaleReportDayHD(yesdate);
            retflag = retflag && realflag;
        }
        return retflag;
    }

    @Override
    public boolean addReportStorageBakEveryday(String yesdate) throws Exception {
        //仓库是否独立核算 0否1是 默认否
        String isStorageAccount = getSysParamValue("IsStorageAccount");
        if(StringUtils.isEmpty(isStorageAccount)){
            isStorageAccount = "0";
        }
        //仓库独立核算时 库存金额按该库存的成本价计算
        //仓库不独立核算时 库存金额按商品的总成本价计算
        return storageReportMapper.addStorageBakEverday(yesdate,isStorageAccount) > 0;
    }

	@Override
	public boolean addStorageNumEveryMonth(String datestr) throws Exception {
		boolean retflag = true;
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(datestr);
		ISysParamService sysParamService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
		SysParam sysParam = sysParamService.getSysParam("unauditDays");
		if(null != sysParam){
			int days = Integer.parseInt(sysParam.getPvalue());
			Date date =sdf.parse(datestr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-days);

			String langdate = sdf.format(calendar.getTime());
			for(int i=0;i<=days;i++){
				Date date2 =sdf.parse(langdate);
				if(!date2.after(date1)){

					boolean flag = addBuySaleReportMonth(langdate);
					retflag = retflag && flag;
					//实际每月进销存
					boolean realflag = addRealBuySaleReportMonth(langdate);
					retflag = retflag && realflag;

					Calendar calendar2 = Calendar.getInstance();
					calendar2.setTime(date2);
					calendar2.set(Calendar.DATE,calendar2.get(Calendar.DATE)+1);
					langdate = sdf.format(calendar2.getTime());
				}
			}
		}else{
			boolean flag = addBuySaleReportMonth(datestr);
			retflag = retflag && flag;
			//实际每日进销存
			boolean realflag = addRealBuySaleReportMonth(datestr);
			retflag = retflag && realflag;
		}
		return retflag;
	}

    @Override
    public boolean addStorageNumEveryMonthHD(String datestr) throws Exception {
        boolean retflag = true;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(datestr);
        ISysParamService sysParamService = (ISysParamService) SpringContextUtils.getBean("sysParamService");
        SysParam sysParam = sysParamService.getSysParam("unauditDays");
        if(null != sysParam){
            int days = Integer.parseInt(sysParam.getPvalue());
            Date date =sdf.parse(datestr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-days);

            String langdate = sdf.format(calendar.getTime());
            for(int i=0;i<=days;i++){
                Date date2 =sdf.parse(langdate);
                if(!date2.after(date1)){

                    boolean flag = addBuySaleReportMonthHD(langdate);
                    retflag = retflag && flag;
                    //实际每月进销存
                    boolean realflag = addRealBuySaleReportMonthHD(langdate);
                    retflag = retflag && realflag;

                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(date2);
                    calendar2.set(Calendar.DATE,calendar2.get(Calendar.DATE)+1);
                    langdate = sdf.format(calendar2.getTime());
                }
            }
        }else{
            boolean flag = addBuySaleReportMonthHD(datestr);
            retflag = retflag && flag;
            //实际每日进销存
            boolean realflag = addRealBuySaleReportMonthHD(datestr);
            retflag = retflag && realflag;
        }
        return retflag;
    }

    @Override
	public boolean addStorageRevolutionDaysReport() throws Exception {
		String begindate = CommonUtils.getPreMonthFirstDay();
		String enddate = CommonUtils.getPreMonthLastDay();
		int year = CommonUtils.getPreMonthYear();
		int mon  =CommonUtils.getPreMonth();
		storageReportMapper.deleteStorageRevolutionDaysReport(year, mon);
		int mondays = CommonUtils.getDayOfMonth(mon);
		int i = storageReportMapper.addStorageRevolutionDaysReport(year, mon, begindate, enddate,mondays);
		return i>0;
	}

	@Override
	public PageData showStorageRevolutionDaysData(PageMap pageMap) throws Exception {
        String datasql = getDataAccessRule("t_report_storage_revolutionday","z");
        pageMap.setDataSql(datasql);
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "goodsid");
		}
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		List<Map> list = storageReportMapper.showStorageRevolutionDaysData(pageMap);
		for(Map map : list){
			String goodsid = (String) map.get("goodsid");
			String branddept = (String) map.get("brandept");
			String supplierid = (String) map.get("supplierid");
			int year = (Integer) map.get("year");
			int mon  = (Integer) map.get("mon");
			//周转天数=库存金额/销售金额*当月天数
			BigDecimal days = (BigDecimal) map.get("days");
			if(null!=days){
				days = days.multiply(new BigDecimal(CommonUtils.getDayOfMonth(mon)));
			}
			map.put("days", days);
			map.put("year", year+"");
			map.put("mon", mon+"");
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("barcode", goodsInfo.getBarcode());
				map.put("goodsname", goodsInfo.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(branddept);
			if(null!=departMent){
				map.put("branddeptname", departMent.getName());
			}else{
				map.put("branddeptname", "未指定部门");
			}
			BuySupplier buySupplier = getSupplierInfoById(supplierid);
			if(null!=buySupplier){
				map.put("supplierid", buySupplier.getId());
				map.put("suppliername", buySupplier.getName());
			}else{
				map.put("suppliername", "未指定供应商");
			}
			if(!map.containsKey("brandname")){
				map.put("brandname", "未指定品牌");
			}
		}
		PageData pageData = new PageData(storageReportMapper.showStorageRevolutionDaysCount(pageMap),list,pageMap);
		pageMap.getCondition().put("groupcolsAll", "all");
		List<Map> footer =  storageReportMapper.showStorageRevolutionSum(pageMap);
		for(Map map : footer){
			if(null!= map){
                map.put("brandname","");
                map.put("supplierid","");
				if(map.containsKey("mon")){
					int mon  = (Integer) map.get("mon");
					map.put("goodsid", "");
					map.put("year", "");
					map.put("mon", "");
					BigDecimal days = (BigDecimal) map.get("days");
					if(null!=days){
						days = days.multiply(new BigDecimal(CommonUtils.getDayOfMonth(mon)));
					}
					map.put("days", days);
				}
				if(groupcols.indexOf("goodsid")!=-1){
					map.put("goodsname", "合计");
				}else if(groupcols.indexOf("brandid")!=-1){
					map.put("brandname", "合计");
				}else if(groupcols.indexOf("brandept")!=-1){
					map.put("brandept", "合计");
				}else if(groupcols.indexOf("supplierid")!=-1){
					map.put("suppliername", "合计");
				}
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}

    @Override
    public PageData showStorageRevolutionDaysBySection(PageMap pageMap) throws Exception {
        String datasql = getDataAccessRule("t_report_storage_revolutionday","z");
        pageMap.setDataSql(datasql);
        if(!pageMap.getCondition().containsKey("groupcols")){
            pageMap.getCondition().put("groupcols", "z.goodsid");
        }
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        //日期天数计算
        String begindate = (String) pageMap.getCondition().get("begindate");
        String enddate = (String) pageMap.getCondition().get("enddate");
       int mondays  =CommonUtils.daysBetween(begindate,enddate) + 1;
        Map m = pageMap.getCondition();
        m.put("mondays",mondays);
        pageMap.setCondition(m);
        List<Map> list = storageReportMapper.showStorageRevolutionDaysBySection(pageMap);
        for(Map map : list){
            String goodsid = (String) map.get("goodsid");
            String branddept = (String) map.get("brandept");
            String supplierid = (String) map.get("supplierid");
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if(null!=goodsInfo){
                map.put("barcode", goodsInfo.getBarcode());
                map.put("goodsname", goodsInfo.getName());
            }else{
                Brand brand = getGoodsBrandByID(goodsid);
                map.put("goodsname", brand.getName());
            }
            DepartMent departMent = getDepartmentByDeptid(branddept);
            if(null!=departMent){
                map.put("branddeptname", departMent.getName());
            }else{
                map.put("branddeptname", "未指定部门");
            }
            BuySupplier buySupplier = getSupplierInfoById(supplierid);
            if(null!=buySupplier){
                map.put("suppliername", buySupplier.getName());
            }else{
                map.put("suppliername", "未指定供应商");
            }
            if(!map.containsKey("brandname")){
                map.put("brandname", "未指定品牌");
            }

        }
        int count = storageReportMapper.showStorageRevolutionDaysBySectionCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        pageMap.getCondition().put("groupcolsAll", "all");
        List<Map> footer =  storageReportMapper.showStorageRevolutionDaysBySection(pageMap);
        for(Map map : footer){
            if(null!= map){
                map.put("businessdate","");
                map.put("goodsid","");
                map.put("brandname","");
                map.put("supplierid","");
                if(groupcols.indexOf("goodsid")!=-1){
                    map.put("goodsname", "合计");
                }else if(groupcols.indexOf("brandid")!=-1){
                    map.put("brandname", "合计");
                }else if(groupcols.indexOf("brandept")!=-1){
                    map.put("branddeptname", "合计");
                }else if(groupcols.indexOf("supplierid")!=-1){
                    map.put("suppliername", "合计");
                }
           }
        }
        pageData.setFooter(footer);
        return pageData;
    }

    public PageData showStorageRevolutionDetails(PageMap pageMap) throws Exception{

        List<Map> list = storageReportMapper.showStorageRevolutionDetails(pageMap);
        for(Map map : list){
            String goodsid = (String) map.get("goodsid");
            String branddept = (String) map.get("branddept");
            String supplierid = (String) map.get("supplierid");
            String customerid = (String) map.get("customerid");
            String salesuser = (String) map.get("salesuser");
            String storageid = (String) map.get("storageid");
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if(null!=goodsInfo){
                map.put("barcode", goodsInfo.getBarcode());
                map.put("goodsname", goodsInfo.getName());
                map.put("spell",goodsInfo.getSpell());
                map.put("unitname",goodsInfo.getMainunitName());
            }
            DepartMent departMent = getDepartmentByDeptid(branddept);
            if(null!=departMent){
                map.put("branddeptname", departMent.getName());
            }else{
                map.put("branddeptname", "未指定部门");
            }
            BuySupplier buySupplier = getSupplierInfoById(supplierid);
            if(null!=buySupplier){
                map.put("suppliername", buySupplier.getName());
            }else{
                map.put("suppliername", "未指定供应商");
            }
            Customer customer = getCustomerByID(customerid);
            if(null!= customer){
                map.put("customername", customer.getName());
                map.put("salesareaname",customer.getSalesareaname());
            }else{
                map.put("customername", "未指定客户");
            }
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
            if(null!= personnel){
                map.put("salesusername", personnel.getName());
            }else{
                map.put("salesusername", "未指定客户业务员");
            }
            StorageInfo storageInfo = getStorageInfoByID(storageid);
            if(null!= storageInfo){
                map.put("storagename", storageInfo.getName());
            }else{
                map.put("storagename", "未指定库存仓库");
            }
            if(!map.containsKey("brandname")){
                map.put("brandname", "未指定品牌");
            }
        }
        int count = storageReportMapper.showStorageRevolutionDetailsCount(pageMap);
        return new PageData(count ,list,pageMap);
    }


	@Override
	public PageData showStorageAvgAmountData(PageMap pageMap) throws Exception {
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "goodsid");
		}
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		List<Map> list = storageReportMapper.showStorageAvgAmountData(pageMap);
		for(Map map : list){
			String goodsid = (String) map.get("goodsid");
			String branddept = (String) map.get("branddept");
			String supplierid = (String) map.get("supplierid");
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            BigDecimal num = (BigDecimal)map.get("existingnum");
            String auxunitname = "";
            //String unitname = (String)map.get("unitname");
			if(null!=goodsInfo){
                auxunitname = goodsInfo.getAuxunitname();
				map.put("barcode", goodsInfo.getBarcode());
				map.put("goodsname", goodsInfo.getName());
				Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
				if(null!=brand){
					map.put("brandname", brand.getName());
				}
			}

            if(null!=goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO)==1){
                if(null!=num && num.compareTo(BigDecimal.ZERO)!=0){
                    BigDecimal auxnumdetail = num.divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
                    if(StringUtils.isEmpty(auxunitname)){
                        auxunitname = getGoodsAuxUnitInfoByGoodsid(goodsid).getName();
                    }

                    DecimalFormat    df   = new DecimalFormat("######0.00");
                    map.put("auxnumdetail",df.format(auxnumdetail)+auxunitname);
                }
            }

			DepartMent departMent = getDepartmentByDeptid(branddept);
			if(null!=departMent){
				map.put("branddeptname", departMent.getName());
			}else{
				map.put("branddeptname", "未指定部门");
			}
			BuySupplier buySupplier = getSupplierInfoById(supplierid);
			if(null!=buySupplier){
				map.put("suppliername", buySupplier.getName());
			}else{
				map.put("suppliername", "未指定供应商");
			}
			if(!map.containsKey("brandname")){
				map.put("brandname", "未指定品牌");
			}
			
		}
		PageData pageData = new PageData(storageReportMapper.showStorageAvgAmountCount(pageMap),list,pageMap);
		pageMap.getCondition().put("groupcolsAll", "all");
		List<Map> footer =  storageReportMapper.showStorageAvgAmountData(pageMap);
		boolean addfooterFlag = true;
		for(Map map : footer){
			if(null!=map){
				map.put("goodsid", "");
				map.put("brandname", "");
				map.put("supplierid", "");
				if(groupcols.indexOf("goodsid")!=-1){
					map.put("goodsname", "合计");
				}else if(groupcols.indexOf("brandid")!=-1){
					map.put("brandname", "合计");
				}else if(groupcols.indexOf("branddept")!=-1){
					map.put("branddeptname", "合计");
				}else if(groupcols.indexOf("supplierid")!=-1){
					map.put("suppliername", "合计");
				}
			}else{
				addfooterFlag = false;
			}
		}
		if(addfooterFlag){
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public boolean addCapitalOccupyReport(String date) throws Exception {
		int i = storageReportMapper.addCapitalOccupyReport(date);
		return i>0;
	}

	@Override
	public List showCapitalOccupyListData(PageMap pageMap) throws Exception {
		String datasql = getDataAccessRule("t_report_capital_occupy", "x");
		pageMap.setDataSql(datasql);
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "supplierid");
		}
		BigDecimal actingmatamountSum = BigDecimal.ZERO;
		List<CapitalOccupyReport> list = storageReportMapper.showCapitalOccupyListData(pageMap);
		for(CapitalOccupyReport capitalOccupyReport : list){
			BuySupplier buySupplier = getSupplierInfoById(capitalOccupyReport.getSupplierid());
			if(null!=buySupplier){
				capitalOccupyReport.setSuppliername(buySupplier.getName());
				DepartMent departMent = getDepartmentByDeptid(buySupplier.getBuydeptid());
				if(null!=departMent){
					capitalOccupyReport.setBranddeptname(departMent.getName());
				}
//				//代垫金额
//				BigDecimal actingmatamount = journalSheetMapper.getMatcostsInputActingmatamountSum(capitalOccupyReport.getSupplierid());
//				if(null == actingmatamount){
//					actingmatamount = BigDecimal.ZERO;
//				}
				actingmatamountSum = actingmatamountSum.add(capitalOccupyReport.getAdvanceamount());
//				capitalOccupyReport.setAdvanceamount(actingmatamount);
				//合计金额
				BigDecimal totalAmount = (null != capitalOccupyReport.getTotalamount()) ? capitalOccupyReport.getTotalamount(): BigDecimal.ZERO;
//				capitalOccupyReport.setTotalamount(totalAmount.add(actingmatamount));
			}else{
				capitalOccupyReport.setSuppliername("其他");
			}
		}
		//PageData pageData = new PageData(storageReportMapper.showCapitalOccupyListCount(pageMap),list,pageMap);
		pageMap.getCondition().put("groupcolsAll", "all");
		List<CapitalOccupyReport> footer =  storageReportMapper.showCapitalOccupyListData(pageMap);
		for(CapitalOccupyReport capitalOccupyReport : footer){
			capitalOccupyReport.setSupplierid("");
			capitalOccupyReport.setSuppliername("合计");
			capitalOccupyReport.setBranddept("");
			capitalOccupyReport.setBranddeptname("");
//			capitalOccupyReport.setAdvanceamount(actingmatamountSum);
//			//合计金额
//			BigDecimal totalAmountSum = (null != capitalOccupyReport.getTotalamount()) ? capitalOccupyReport.getTotalamount(): BigDecimal.ZERO;
//			capitalOccupyReport.setTotalamount(totalAmountSum.add(actingmatamountSum));
		}
		list.addAll(footer);
		return list;
	}

	@Override
	public PageData showCapitalOccupyAvgListData(PageMap pageMap)
			throws Exception {
		String datasql = getDataAccessRule("t_report_capital_occupy", "x");
		pageMap.setDataSql(datasql);
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "supplierid");
		}
		List<CapitalOccupyReport> list = storageReportMapper.showCapitalOccupyAvgListData(pageMap);
		for(CapitalOccupyReport capitalOccupyReport : list){
			BuySupplier buySupplier = getSupplierInfoById(capitalOccupyReport.getSupplierid());
			if(null!=buySupplier){
				capitalOccupyReport.setSuppliername(buySupplier.getName());
				DepartMent departMent = getDepartmentByDeptid(buySupplier.getBuydeptid());
				if(null!=departMent){
					capitalOccupyReport.setBranddeptname(departMent.getName());
				}
			}else{
				capitalOccupyReport.setSuppliername("其他");
			}
		}
		PageData pageData = new PageData(storageReportMapper.showCapitalOccupyAvgListCount(pageMap),list,pageMap);
		pageMap.getCondition().put("groupcolsAll", "all");
		CapitalOccupyReport capitalOccupyReport =  storageReportMapper.showCapitalOccupyAvgSumData(pageMap);
		if(null!=capitalOccupyReport){
			capitalOccupyReport.setSupplierid("");
			capitalOccupyReport.setSuppliername("合计");
			capitalOccupyReport.setBranddept("");
			capitalOccupyReport.setBranddeptname("");
			List footer = new ArrayList();
			footer.add(capitalOccupyReport);
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public PageData showStorageAmountReportListData(PageMap pageMap)
			throws Exception {
		//备注：showStorageAmountReportList 方法只计算list
//		String datasql = getDataAccessRule("t_report_capital_occupy", "x");
//		pageMap.setDataSql(datasql);
		String dataSql = getDataAccessRule("t_storage_summary", "t");
		pageMap.setDataSql(dataSql);
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "storageid,goodsid");
		}
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		List<Map> list = storageReportMapper.showStorageAmountReportListData(pageMap);
		for(Map map : list){
			String storageid = (String) map.get("storageid");
			String goodsid = (String) map.get("goodsid");
			String brandid = (String) map.get("brandid");
			String supplierid = (String) map.get("supplierid");
			String branddept = (String) map.get("branddept");
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			if(null!=storageInfo){
				map.put("storagename", storageInfo.getName());
			}
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}else{
				map.put("brandname", "其他品牌");
			}
			BuySupplier buySupplier = getSupplierInfoById(supplierid);
			if(null!=buySupplier){
				map.put("suppliername", buySupplier.getName());
			}else{
				map.put("suppliername", "其他");
			}
			DepartMent departMent = getDepartmentByDeptid(branddept);
			if(null!=departMent){
				map.put("branddeptname", departMent.getName());
			}else{
				map.put("branddeptname", "其他");
			}
			//商品
			GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
			if(null != goodsInfo){
				map.put("barcode", goodsInfo.getBarcode());
				map.put("goodsname", goodsInfo.getName());
				map.put("spell", goodsInfo.getSpell());
			}
		}
		PageData pageData = new PageData(storageReportMapper.showStorageAmountReportListCount(pageMap),list,pageMap);
		pageMap.getCondition().put("groupcolsAll", "all");
		List<Map> footer = storageReportMapper.showStorageAmountReportListData(pageMap);
		for(Map map : footer){
			if(null!=map){
				map.put("storageid", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("spell", "");
				map.put("supplierid", "");
				map.put("price", "");
				if(groupcols.indexOf("storageid")!=-1){
					map.put("storagename", "合计");
				}else if(groupcols.indexOf("goodsid")!=-1){
					map.put("goodsname", "合计");
				}else if(groupcols.indexOf("brandid")!=-1){
					map.put("brandname", "合计");
				}else if(groupcols.indexOf("branddept")!=-1){
					map.put("branddeptname", "合计");
				}else if(groupcols.indexOf("supplierid")!=-1){
					map.put("suppliername", "合计");
				}
			}else{
				footer = new ArrayList<Map>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}
	@Override
	public List<Map> showStorageAmountReportList(PageMap pageMap) throws Exception{
		Map<String,Object> condition=pageMap.getCondition();
		String isShowAuth=(String)condition.get("isShowAuth");
		if(!"false".equals(isShowAuth)){
			String datasql = getDataAccessRule("t_report_capital_occupy", "x");
			//pageMap.setDataSql(datasql);
			String dataSql = getDataAccessRule("t_storage_summary", "t");
			pageMap.setDataSql(dataSql);
		}
		if(!condition.containsKey("groupcols")){
			condition.put("groupcols", "storageid,goodsid");
		}
		String groupcols = (String) condition.get("groupcols");
		List<Map> list = storageReportMapper.showStorageAmountReportListData(pageMap);
		for(Map map : list){
			String storageid = (String) map.get("storageid");
			String goodsid = (String) map.get("goodsid");
			String brandid = (String) map.get("brandid");
			String supplierid = (String) map.get("supplierid");
			String branddept = (String) map.get("branddept");
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			if(null!=storageInfo){
				map.put("storagename", storageInfo.getName());
			}
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}else{
				map.put("brandname", "其他品牌");
			}
			BuySupplier buySupplier = getSupplierInfoById(supplierid);
			if(null!=buySupplier){
				map.put("suppliername", buySupplier.getName());
			}else{
				map.put("suppliername", "其他");
			}
			DepartMent departMent = getDepartmentByDeptid(branddept);
			if(null!=departMent){
				map.put("branddeptname", departMent.getName());
			}else{
				map.put("branddeptname", "其他");
			}
			//商品
			GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
			if(null != goodsInfo){
				map.put("barcode", goodsInfo.getBarcode());
				map.put("goodsname", goodsInfo.getName());
				map.put("spell", goodsInfo.getSpell());
			}
		}
		
		return list;
	}

    @Override
    public PageData getCustomerCarNumReportData(PageMap pageMap)throws Exception{
        List<Map<String,Object>> list = storageReportMapper.getCustomerCarNumReportList(pageMap);
            for(Map<String,Object> map : list){
                if(null != map.get("carnum")){
                    Integer carnum = Integer.parseInt(map.get("carnum").toString());
                    map.put("carnum",carnum);
                }
                String customerid = (String)map.get("customerid");
                String lineid = (String)map.get("lineid");
                LogisticsLine logisticsLine = getBaseLogisticsMapper().getLineInfo(lineid);
                if(null != logisticsLine){
                    map.put("linename",logisticsLine.getName());
                }
                Customer customer = getCustomerByID(customerid);
                if(null != customer){
                    map.put("customername",customer.getName());
                    Customer pCustomer = getCustomerByID(customer.getPid());
                    if(null != pCustomer){
                        map.put("pcustomername",pCustomer.getName());
                    }
                    SalesArea salesArea = getSalesareaByID(customer.getSalesarea());
                    if(null != salesArea){
                        map.put("salesareaname",salesArea.getThisname());
                    }
                    DepartMent departMent = getDepartMentById(customer.getSalesdeptid());
                    if(null != departMent){
                        map.put("salesdeptname",departMent.getName());
                    }
                }
        }
        int count = storageReportMapper.getCustomerCarNumReportCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        return pageData;
    }

    @Override
	public PageData getAdjustmentReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_adjustments", "t");
		pageMap.setDataSql(dataSql);
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "storageid,goodsid");
		}
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		List<Map> list = storageReportMapper.getAdjustmentReportData(pageMap);
		for(Map map : list){
			String storageid = (String) map.get("storageid");
			String goodsid = (String) map.get("goodsid");
			String brandid = (String) map.get("brandid");
			String supplierid = (String) map.get("supplierid");
			String branddept = (String) map.get("branddept");
			StorageInfo storageInfo = getStorageInfoByID(storageid);
			if(null!=storageInfo){
				map.put("storagename", storageInfo.getName());
			}
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}else{
				map.put("brandname", "其他品牌");
			}
			//商品
			GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
			if(null != goodsInfo){
				map.put("barcode", goodsInfo.getBarcode());
				map.put("goodsname", goodsInfo.getName());
				map.put("spell", goodsInfo.getSpell());
			}
			
			BigDecimal auxadjustnum = (BigDecimal) map.get("auxadjustnum");
			BigDecimal auxadjustremainder = (BigDecimal) map.get("auxadjustremainder");
			String adjustnumdetail = "";
			if(null!=auxadjustnum && auxadjustnum.compareTo(BigDecimal.ZERO)!=0){
				String unitname = "箱";
				if(null!=goodsInfo){
					MeteringUnit meteringUnit = getGoodsDefaulAuxunit(goodsInfo.getId());
					if(null!=meteringUnit){
						unitname = meteringUnit.getName();
					}
				}
				adjustnumdetail += auxadjustnum.setScale(0, BigDecimal.ROUND_HALF_UP).toString()+unitname;
			}
			if(null!=auxadjustremainder && auxadjustremainder.compareTo(BigDecimal.ZERO)!=0){
				String unitname = "个";
				if(null!=goodsInfo){
					MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
					if(null!=meteringUnit){
						unitname = meteringUnit.getName();
					}
				}
				adjustnumdetail += auxadjustremainder + unitname;
			}
			map.put("adjustnumdetail", CommonUtils.strDigitNumDeal(adjustnumdetail));
		}
		PageData pageData = new PageData(storageReportMapper.getAdjustmentReportDataCount(pageMap),list,pageMap);
		pageMap.getCondition().put("groupcolsAll", "all");
		List<Map> footer = storageReportMapper.getAdjustmentReportData(pageMap);
		for(Map map : footer){
			if(null!=map){
				map.put("storageid", "");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("spell", "");
				map.put("barcode", "");
				map.put("price", "");
				if(groupcols.indexOf("storageid")!=-1){
					map.put("storagename", "合计");
				}else if(groupcols.indexOf("goodsid")!=-1){
					map.put("goodsname", "合计");
				}else if(groupcols.indexOf("brandid")!=-1){
					map.put("brandname", "合计");
				}else if(groupcols.indexOf("branddept")!=-1){
					map.put("branddeptname", "合计");
				}else if(groupcols.indexOf("supplierid")!=-1){
					map.put("suppliername", "合计");
				}
				BigDecimal auxadjustnum = (BigDecimal) map.get("auxadjustnum");
				BigDecimal auxadjustremainder = (BigDecimal) map.get("auxadjustremainder");
				String adjustnumdetail = "";
				if(null!=auxadjustnum && auxadjustnum.compareTo(BigDecimal.ZERO)!=0){
					adjustnumdetail += auxadjustnum.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
				}
				if(null!=auxadjustremainder && auxadjustremainder.compareTo(BigDecimal.ZERO)!=0){
					if(!"".equals(adjustnumdetail)){
						adjustnumdetail+=",";
					}
					adjustnumdetail += auxadjustremainder;
				}
				map.put("adjustnumdetail", CommonUtils.strDigitNumDeal(adjustnumdetail));
			}else{
				footer = new ArrayList<Map>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showStorageRecSendList(PageMap pageMap) throws Exception {
		String businessdate1 = null;
		if(pageMap.getCondition().containsKey("businessdate1")){
			businessdate1 = (String) pageMap.getCondition().get("businessdate1");
		}
		String initdate = storageReportMapper.getStorageInitDate();
		if(StringUtils.isNotEmpty(initdate)){
			if(null!=businessdate1){
				Date busdate1 = CommonUtils.stringToDate(businessdate1);
				Date initD = CommonUtils.stringToDate(initdate);
				if(busdate1.after(initD)){
			        pageMap.getCondition().put("queryinitdate1", CommonUtils.getBeforeDateInDays(busdate1, 2));
					pageMap.getCondition().put("queryinitdate2", CommonUtils.getBeforeDateInDays(busdate1, 0));
				}else{
					pageMap.getCondition().put("initdate", initdate);
				}
			}else{
				pageMap.getCondition().put("initdate", initdate);
			}
		}
		String dataSql = getDataAccessRule("t_report_recsend", "z");
		List<Map> list = storageReportMapper.showStorageRecSendList(pageMap);
		for(Map map : list){
            String storageid = null;
			if(map.containsKey("storageid")){
                storageid = (String) map.get("storageid");
				StorageInfo storageInfo = getStorageInfoByID(storageid);
				if(null!=storageInfo){
					map.put("storagename", storageInfo.getName());
				}
			}
			String beginnumdetail = null != map.get("beginnumdetail") ? CommonUtils.strDigitNumDeal((String) map.get("beginnumdetail")) : "";
			String receivenumdetail = null != map.get("receivenumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("receivenumdetail")) : "";
			String sendnumdetail = null != map.get("sendnumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("sendnumdetail")) : "";
			String endnumdetail = null != map.get("endnumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("endnumdetail")) : "";
			if(map.containsKey("goodsid")){
				String goodsid = (String) map.get("goodsid");
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					map.put("goodsname", goodsInfo.getName());
					map.put("brandname", goodsInfo.getBrandName());
					map.put("barcode", goodsInfo.getBarcode());
					map.put("unitname", goodsInfo.getMainunitName());
                    WaresClass waresClass = getWaresClassByID(goodsInfo.getDefaultsort());
                    if(null!=waresClass){
                        map.put("goodssortname", waresClass.getName());
                    }
                    BigDecimal beginnum=(BigDecimal)map.get("beginnum");
                    BigDecimal endnum=(BigDecimal)map.get("endnum");
                    BigDecimal receivenum=(BigDecimal)map.get("receivenum");
                    BigDecimal sendnum=(BigDecimal)map.get("sendnum");
                    BigDecimal price= getGoodsCostprice(storageid,goodsid);

                    if(beginnum!=null){
                        BigDecimal beginamount=beginnum.multiply(price);
//                        map.put("beginamount", beginamount);
                    }
                    if(receivenum!=null){
                        BigDecimal receiveamount=receivenum.multiply(price);
//                        map.put("receiveamount", receiveamount);
                    }
                    if(sendnum!=null){
                        BigDecimal sendamount=sendnum.multiply(price);
//                        map.put("sendamount", sendamount);
                    }
                    if(endnum!=null){
                        BigDecimal endamount=endnum.multiply(price);
//                        map.put("endamount", endamount);
                    }
					Map beginDetailMap=countGoodsInfoNumber(goodsid,goodsInfo.getMainunit(),beginnum);
					beginnumdetail=(String)beginDetailMap.get("auxnumdetail");
					Map receiveDetailMap=countGoodsInfoNumber(goodsid,goodsInfo.getMainunit(),receivenum);
					receivenumdetail=(String)receiveDetailMap.get("auxnumdetail");
					Map  sendDetailMap=countGoodsInfoNumber(goodsid,goodsInfo.getMainunit(),sendnum);
					sendnumdetail=(String)sendDetailMap.get("auxnumdetail");
					Map  endDetailMap=countGoodsInfoNumber(goodsid,goodsInfo.getMainunit(),endnum);
					endnumdetail=(String)endDetailMap.get("auxnumdetail");
				}
			}

			map.put("beginnumdetail",beginnumdetail);
			map.put("receivenumdetail",receivenumdetail);
			map.put("sendnumdetail",sendnumdetail);
			map.put("endnumdetail",endnumdetail);
		}
		PageData pageData = new PageData(storageReportMapper.showStorageRecSendCount(pageMap), list, pageMap);
		List<Map> footer = storageReportMapper.showStorageRecSendSum(pageMap);
		for(Map map : footer){
			if(null!=map){
				map.put("storageid", "");
				map.put("storagename","合计");
				map.put("goodsid", "");
				map.put("goodsname", "");
				map.put("barcode", "");
				map.put("price", "");
				map.put("auxunitname", "");
				map.put("beginnumdetail", "");
				map.put("receivenumdetail", "");
				map.put("sendnumdetail", "");
				map.put("endnumdetail", "");
				
				BigDecimal auxbeginnum = ((BigDecimal) map.get("auxbeginnum")).setScale(3,BigDecimal.ROUND_HALF_UP);
				BigDecimal auxreceivenum = ((BigDecimal) map.get("auxreceivenum")).setScale(3,BigDecimal.ROUND_HALF_UP);
				BigDecimal auxsendnum = ((BigDecimal) map.get("auxsendnum")).setScale(3,BigDecimal.ROUND_HALF_UP);
				BigDecimal auxendnum = ((BigDecimal) map.get("auxendnum")).setScale(3,BigDecimal.ROUND_HALF_UP);
				map.put("auxbeginnum", auxbeginnum);
				map.put("auxreceivenum", auxreceivenum);
				map.put("auxsendnum", auxsendnum);
				map.put("auxendnum", auxendnum);
			}else{
				footer = new ArrayList<Map>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}
	
	@Override
	public PageData showStorageRecSendReportDetailList(PageMap pageMap) throws Exception {
		String businessdate1 = null;
		if(pageMap.getCondition().containsKey("businessdate1")){
			businessdate1 = (String) pageMap.getCondition().get("businessdate1");
		}
		String initdate = storageReportMapper.getStorageInitDate();
		if(StringUtils.isNotEmpty(initdate)){
			if(null!=businessdate1){
				Date busdate1 = CommonUtils.stringToDate(businessdate1);
				Date initD = CommonUtils.stringToDate(initdate);
				if(busdate1.after(initD)){
			        pageMap.getCondition().put("queryinitdate1", CommonUtils.getBeforeDateInDays(busdate1, 2));
					pageMap.getCondition().put("queryinitdate2", CommonUtils.getBeforeDateInDays(busdate1, 0));
				}else{
					pageMap.getCondition().put("initdate", initdate);
				}
			}else{
				pageMap.getCondition().put("initdate", initdate);
			}
		}
		String dataSql = getDataAccessRule("t_report_recsend", "z");
		List<Map> list = storageReportMapper.showStorageRecSendDetailList(pageMap);
		for(int i=0;i<list.size();i++){
			Map map=list.get(i);

			String beginnumdetail1 = null != map.get("beginnumdetail") ? CommonUtils.strDigitNumDeal((String) map.get("beginnumdetail")) : "";
			String receivenumdetail = null != map.get("receivenumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("receivenumdetail")) : "";
			String sendnumdetail = null != map.get("sendnumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("sendnumdetail")) : "";
			String endnumdetail1 = null != map.get("endnumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("endnumdetail")) : "";
			map.put("beginnumdetail",beginnumdetail1);
			map.put("receivenumdetail",receivenumdetail);
			map.put("sendnumdetail",sendnumdetail);
			map.put("endnumdetail",endnumdetail1);

			if(i!=0){
				Map indexMap=list.get(i-1);
				BigDecimal boxnum=(BigDecimal)indexMap.get("boxnum");
				String unitname=(String)indexMap.get("unitname");
				String auxunitname=(String)indexMap.get("auxunitname");
				BigDecimal indexbeginnum=(null != (BigDecimal)indexMap.get("endnum")) ? (BigDecimal)indexMap.get("endnum") : BigDecimal.ZERO;
				BigDecimal receivenum;
				if("stockinit".equals((String)map.get("billmodel"))){
					receivenum=(null != (BigDecimal)map.get("beginnum")) ? (BigDecimal)map.get("beginnum") : BigDecimal.ZERO;
				}
				else{
					receivenum=(null != (BigDecimal)map.get("receivenum")) ? (BigDecimal)map.get("receivenum") : BigDecimal.ZERO;
				}
				BigDecimal sendnum=(null != (BigDecimal)map.get("sendnum")) ? (BigDecimal)map.get("sendnum") : BigDecimal.ZERO;
				BigDecimal intBeginAuxunitxnum=indexbeginnum.divideAndRemainder(boxnum)[0];
				BigDecimal intBeginUnitnum=indexbeginnum.divideAndRemainder(boxnum)[1];
				BigDecimal auxbeginnum=indexbeginnum.divide(boxnum, 3, BigDecimal.ROUND_HALF_UP);
				String beginnumdetail=CommonUtils.strDigitNumDeal(intBeginAuxunitxnum+auxunitname+intBeginUnitnum+unitname);
				map.put("beginnum", indexbeginnum);
				map.put("auxbeginnum", auxbeginnum);
				map.put("beginnumdetail", beginnumdetail);
				BigDecimal endnum=(indexbeginnum.add(receivenum)).subtract(sendnum);
				BigDecimal intEndAuxunitxnum=endnum.divideAndRemainder(boxnum)[0];
				BigDecimal intEndUnitnum=endnum.divideAndRemainder(boxnum)[1];
				BigDecimal auxendnum=endnum.divide(boxnum, 3, BigDecimal.ROUND_HALF_UP);
				String endnumdetail=CommonUtils.strDigitNumDeal(intEndAuxunitxnum+auxunitname+intEndUnitnum+unitname);
				map.put("endnum", endnum);
				map.put("auxendnum", auxendnum);
				map.put("endnumdetail", endnumdetail);
			}else if(i==0&&("0".equals((String)map.get("billmodel"))||"stockInit".equals((String)map.get("billmodel")))){
				map.put("beginnum",(null != (BigDecimal)map.get("beginnum")) ? (BigDecimal)map.get("beginnum") : BigDecimal.ZERO );
			}else if(i==0&&!"0".equals((String)map.get("billmodel"))){
				BigDecimal receivenum=(null != (BigDecimal)map.get("receivenum")) ? (BigDecimal)map.get("receivenum") : BigDecimal.ZERO;
				BigDecimal sendnum=(null != (BigDecimal)map.get("sendnum")) ? (BigDecimal)map.get("sendnum") : BigDecimal.ZERO;
				BigDecimal endnum=receivenum.subtract(sendnum);
				map.put("endnum",endnum);
			}
            String storageid = null;
			if(map.containsKey("storageid")){
				storageid = (String) map.get("storageid");
				StorageInfo storageInfo = getStorageInfoByID(storageid);
				if(null!=storageInfo){
					map.put("storagename", storageInfo.getName());
				}
			}
			if(map.containsKey("goodsid")){
				String goodsid = (String) map.get("goodsid");
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					map.put("goodsname", goodsInfo.getName());
					map.put("brandname", goodsInfo.getBrandName());
					map.put("barcode", goodsInfo.getBarcode());
				}
				WaresClass waresClass = getWaresClassByID(goodsInfo.getDefaultsort());
				if(null!=waresClass){
					map.put("goodssortname", waresClass.getName());
				}
				BigDecimal beginnum=(BigDecimal)map.get("beginnum");
				BigDecimal endnum=(BigDecimal)map.get("endnum");
				BigDecimal receivenum=(BigDecimal)map.get("receivenum");
				BigDecimal sendnum=(BigDecimal)map.get("sendnum");
				BigDecimal price=goodsInfo.getNewstorageprice();
				
				if(beginnum!=null){
					BigDecimal beginamount=beginnum.multiply(price);
					if(i!=0){
						Map indexMap=list.get(i-1);
						BigDecimal endamount=(BigDecimal)indexMap.get("endamount");
						beginamount=endamount;
					}
					map.put("beginamount", beginamount);
				}
				if(receivenum!=null){
//					BigDecimal receiveamount=receivenum.multiply(price);
//					map.put("receiveamount", receiveamount);
				}
				if(sendnum!=null){
//					BigDecimal sendamount=sendnum.multiply(price);
//					map.put("sendamount", sendamount);
				}
				if(endnum!=null){
					BigDecimal beginamount=(BigDecimal)map.get("beginamount");
					BigDecimal endamount=(BigDecimal)map.get("endamount");
					endamount=beginamount.add(endamount);
					map.put("endamount", endamount);
				}
			}
			if(map.containsKey("billmodel")){
				String billmodel = (String) map.get("billmodel");
				if("stockInit".equals(billmodel)){
					map.put("billmodelname", "库存初始化");
				}
				else if("purchaseEnter".equals(billmodel)){
					map.put("billmodelname", "采购入库单");
				}
				else if("saleout".equals(billmodel)){
					map.put("billmodelname", "销售发货单");
				}
				else if("storageOtherOut".equals(billmodel)){
					map.put("billmodelname", "其他出库单");
				}
				else if("saleRejectEnter".equals(billmodel)){
					map.put("billmodelname", "销售退货入库单");
				}
				else if("storageOtherEnter".equals(billmodel)){
					map.put("billmodelname", "其他入库单");
				}
				else if("allocateOut".equals(billmodel)){
					map.put("billmodelname", "调拨出库单");
				}
				else if("purchaseRejectOut".equals(billmodel)){
					map.put("billmodelname", "采购退货出库单");
				}
				else if("adjustments".equals(billmodel)){
					map.put("billmodelname", "调账单");
				}
				else if("storageDeliveryEnter".equals(billmodel)){
					map.put("billmodelname", "代配送入库单");
				}
				else if("StorageDeliveryOut".equals(billmodel)){
					map.put("billmodelname", "代配送出库单");
				}
			}
			
		}
		if("0".equals((String)list.get(0).get("billmodel"))){
			list.remove(0);
		}
		PageData pageData = new PageData(storageReportMapper.showStorageRecSendDetailCount(pageMap), list, pageMap);
		return pageData;
	}

	@Override
	public List showReceivenumDetailList(String goodsid, String storageid, String businessdate1, String businessdate2) throws Exception {
		List<Map> list = storageReportMapper.showReceivenumDetailList(goodsid,storageid,businessdate1,businessdate2);
		if(list.size() != 0){
			for(Map map : list){
				if(map.containsKey("billmodel")){
					String billmodel = (String) map.get("billmodel");
					if("stockInit".equals(billmodel)){
						map.put("billmodelname", "库存初始化");
					}
					else if("purchaseEnter".equals(billmodel)){
						map.put("billmodelname", "采购入库单");
					}
					else if("saleout".equals(billmodel)){
						map.put("billmodelname", "销售发货单");
					}
					else if("storageOtherOut".equals(billmodel)){
						map.put("billmodelname", "其他出库单");
					}
					else if("saleRejectEnter".equals(billmodel)){
						map.put("billmodelname", "销售退货入库单");
					}
					else if("storageOtherEnter".equals(billmodel)){
						map.put("billmodelname", "其他入库单");
					}
					else if("allocateOut".equals(billmodel)){
						map.put("billmodelname", "调拨出库单");
					}
					else if("purchaseRejectOut".equals(billmodel)){
						map.put("billmodelname", "采购退货出库单");
					}
					else if("adjustments".equals(billmodel)){
						map.put("billmodelname", "调账单");
					}
					else if("storageDeliveryEnter".equals(billmodel)){
						map.put("billmodelname", "代配送入库单");
					}
					else if("StorageDeliveryOut".equals(billmodel)){
						map.put("billmodelname", "代配送出库单");
					}
				}

				String beginnumdetail = null != map.get("beginnumdetail") ? CommonUtils.strDigitNumDeal((String) map.get("beginnumdetail")) : "";
				String receivenumdetail = null != map.get("receivenumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("receivenumdetail")) : "";
				String sendnumdetail = null != map.get("sendnumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("sendnumdetail")) : "";
				String endnumdetail = null != map.get("endnumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("endnumdetail")) : "";
				map.put("beginnumdetail",beginnumdetail);
				map.put("receivenumdetail",receivenumdetail);
				map.put("sendnumdetail",sendnumdetail);
				map.put("endnumdetail",endnumdetail);
			}
			
		}
		return list;
	}

	@Override
	public List showSendnumDetailPageList(String goodsid, String storageid, String businessdate1, String businessdate2) throws Exception {
		List<Map> list = storageReportMapper.showSendnumDetailPageList(goodsid,storageid,businessdate1,businessdate2);
		if(list.size() != 0){
			for(Map map : list){
				if(map.containsKey("billmodel")){
					String billmodel = (String) map.get("billmodel");
					if("stockInit".equals(billmodel)){
						map.put("billmodelname", "库存初始化");
					}
					else if("purchaseEnter".equals(billmodel)){
						map.put("billmodelname", "采购入库单");
					}
					else if("saleout".equals(billmodel)){
						map.put("billmodelname", "销售发货单");
					}
					else if("storageOtherOut".equals(billmodel)){
						map.put("billmodelname", "其他出库单");
					}
					else if("saleRejectEnter".equals(billmodel)){
						map.put("billmodelname", "销售退货入库单");
					}
					else if("storageOtherEnter".equals(billmodel)){
						map.put("billmodelname", "其他入库单");
					}
					else if("allocateOut".equals(billmodel)){
						map.put("billmodelname", "调拨出库单");
					}
					else if("purchaseRejectOut".equals(billmodel)){
						map.put("billmodelname", "采购退货出库单");
					}
					else if("adjustments".equals(billmodel)){
						map.put("billmodelname", "调账单");
					}
					else if("storageDeliveryEnter".equals(billmodel)){
						map.put("billmodelname", "代配送入库单");
					}
					else if("StorageDeliveryOut".equals(billmodel)){
						map.put("billmodelname", "代配送出库单");
					}
				}

				String beginnumdetail = null != map.get("beginnumdetail") ? CommonUtils.strDigitNumDeal((String) map.get("beginnumdetail")) : "";
				String receivenumdetail = null != map.get("receivenumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("receivenumdetail")) : "";
				String sendnumdetail = null != map.get("sendnumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("sendnumdetail")) : "";
				String endnumdetail = null != map.get("endnumdetail") ? CommonUtils.strDigitNumDeal((String)map.get("endnumdetail")) : "";
				map.put("beginnumdetail",beginnumdetail);
				map.put("receivenumdetail",receivenumdetail);
				map.put("sendnumdetail",sendnumdetail);
				map.put("endnumdetail",endnumdetail);
			}
		}
		return list;
	}

	@Override
	public PageData showDispatchUserListData(PageMap pageMap) throws Exception {
		BigDecimal pn=new BigDecimal(getSysParamValue("printnum"));
		Map map1=pageMap.getCondition();
		map1.put("pn", pn);
		pageMap.setCondition(map1);
		List<DispatchUserReport> list = storageReportMapper.showDispatchUserListData(pageMap);
		for(DispatchUserReport dispatchUserReport : list){
			String storagerid =dispatchUserReport.getStoragerid();
			Personnel personnel=getPersonnelById(storagerid);
			if(null!=personnel){
				dispatchUserReport.setStoragername(personnel.getName());
			}
			String storageid =dispatchUserReport.getStorageid();
			StorageInfo storageInfo=getStorageInfoByID(storageid);
			if(null!=storageInfo){
				dispatchUserReport.setStoragename(storageInfo.getName());
			}
		}
		PageData pageData = new PageData(storageReportMapper.showDispatchUserListDataCount(pageMap),list,pageMap);
		List<Map> footer = storageReportMapper.showDispatchUserListDataSum(pageMap);
		for(Map map : footer){
			if(null!=map){
				map.put("storagerid", null);
				map.put("storageid", null);
				map.put("storagername", "合计");
			}else{
				footer = new ArrayList<Map>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}

    @Override
    public PageData getDriverDeliveryReportData(PageMap pageMap) throws Exception {

        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if(StringUtils.isNotEmpty(groupcols)){
            pageMap.getCondition().put("groupcols", groupcols);
        }else{
            pageMap.getCondition().put("groupcols","goodsid");
        }
        String datasql = getDataAccessRule("t_report_storage_DriverDelivery","z");
        if(StringUtils.isEmpty(datasql)){
            datasql = "1 = 1";
        }
        pageMap.setDataSql(datasql);
        List<Map> list = storageReportMapper.getDriverDeliveryReportData(pageMap);
        for(Map map : list){
            String customerid = (String) map.get("customerid");
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            String salesuser = (String) map.get("salesuser");
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
            if (personnel != null) {
                map.put("salesusername",personnel.getName());
            }
            String salesdept = (String) map.get("salesdept");
            DepartMent departMent = getDepartmentByDeptid(salesdept);
            if (departMent != null) {
                map.put("salesdeptname",departMent.getName());
            }
            String driverid = (String) map.get("driverid");
            personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(driverid);
            if (personnel != null) {
                map.put("drivername",personnel.getName());
            }
            String goodsid = (String) map.get("goodsid");
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if(null != goodsInfo){
                map.put("goodsname",goodsInfo.getName());
                map.put("barcode",goodsInfo.getBarcode());
                map.put("brandname",goodsInfo.getBrandName());
                map.put("model",goodsInfo.getModel());
            }
        }
        int count = storageReportMapper.getDriverDeliveryReportDataCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        List<Map> footer = storageReportMapper.getDriverDeliveryReportDataSum(pageMap);
        String cols = "customername";
        if(null != groupcols){
            cols = groupcols.split(",")[0];
        }
        for(Map map : footer){
            if(null!=map){
                map.put(cols , "合计");
            }else{
                footer = new ArrayList<Map>();
            }
        }
        pageData.setFooter(footer);
        return pageData;
    }

    @Override
    public PageData showSalesmanDeliveryData(PageMap pageMap) throws Exception {
        List<Map> list = storageReportMapper.getSalesmanDeliveryReportData(pageMap);
        for(Map map : list){
            String goodsid = (String) map.get("goodsid");
            String orderid = (String) map.get("orderid");

            Map map1 = new HashMap();
            //根据条件获取发货单中该商品的折扣金额总计
            map1.put("orderid",orderid);
            map1.put("goodsid",goodsid);
            Map goodsDiscountMap = storageReportMapper.getSaleoutDiscountInfo(map1);//获取订单总的折扣金额 总件数
            if(null != goodsDiscountMap){
                //根据数量进行折扣分摊
                BigDecimal disamountSum = (BigDecimal) goodsDiscountMap.get("disamountSum");
                BigDecimal totalboxSum = (BigDecimal) goodsDiscountMap.get("totalboxSum");
                BigDecimal totalbox = (BigDecimal) map.get("totalbox");
                if(totalboxSum.compareTo(BigDecimal.ZERO) > 0 ){
                    BigDecimal percent = totalbox.divide(totalboxSum,6,BigDecimal.ROUND_HALF_EVEN);
                    BigDecimal discountamount = disamountSum.multiply(percent);
                    map.put("discountamount",discountamount);;//商品折扣
                    BigDecimal taxamount = (BigDecimal) map.get("taxamount");
                    taxamount = taxamount.subtract(discountamount);
                    map.put("taxamount",taxamount);
                }

            }

            if(!map.containsKey("discountamount")){
                map.put("discountamount",0);
            }
            String customerid = (String) map.get("customerid");
            Customer customer = getCustomerByID(customerid);
            if(null != customer){
                map.put("customername",customer.getName());
            }
            String salesuser = (String) map.get("branduser");
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
            if (personnel != null) {
                map.put("brandusername",personnel.getName());
            }
            String salesdept = (String) map.get("persondept");
            DepartMent departMent = getDepartmentByDeptid(salesdept);
            if (departMent != null) {
                map.put("persondeptname",departMent.getName());
            }
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if(null != goodsInfo){
                map.put("goodsname",goodsInfo.getName());
                map.put("barcode",goodsInfo.getBarcode());
                map.put("brandname",goodsInfo.getBrandName());
                map.put("model",goodsInfo.getModel());
            }
        }
        int count = storageReportMapper.getSalesmanDeliveryReportDataCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        List<Map> footer = storageReportMapper.getSalesmanDeliveryReportDataSum(pageMap);
        for(Map map : footer){
            if(null!=map){
                Map countMap = storageReportMapper.getSaleoutDiscountSum(pageMap);
                if(null != countMap){
                    BigDecimal discountamount = (BigDecimal)countMap.get("discountamount");
                    BigDecimal taxamount = (BigDecimal)countMap.get("taxamount");
                    taxamount = taxamount.subtract(discountamount);
                    map.put("taxamount",taxamount);
                    map.put("discountamount",discountamount);
                }else {
                    map.put("discountamount",0);
                }

                map.put("customername" , "合计");
            }else{
                footer = new ArrayList<Map>();
            }
        }
        pageData.setFooter(footer);
        return pageData;
    }

    @Override
    public PageData showDriverRejectEnterData(PageMap pageMap) throws Exception {

        String groupcols = (String) pageMap.getCondition().get("groupcols");
        String col = "";
        if(StringUtils.isNotEmpty(groupcols)){
            if(groupcols.contains(",")){
                String[] args = groupcols.split(",");
                col = args[0];
                for(String a : args){
                    if(groupcols.contains("z")){
                        groupcols += "z."+a +",";
                    }else{
                        groupcols = "z."+a +",";
                    }
                }
                groupcols = groupcols.substring(0,groupcols.length()-1);
            }else{
                col = groupcols;
            }
        }else{
            col = "customerid";
            groupcols = "z.id";
        }
        pageMap.getCondition().put("groupcols", groupcols);
        String datasql = getDataAccessRule("t_report_storage_DriverDelivery","z");
        if(StringUtils.isEmpty(datasql)){
            datasql = "1 = 1";
        }
        pageMap.setDataSql(datasql);
        List<Map> list = storageReportMapper.getDriverRejectEnterReportData(pageMap);
        for(Map map : list){
            if(!("z.id".equals(groupcols))){
                map.put("sourceid","");
            }
            if("z.id".equals(groupcols) || groupcols.contains("customerid")){
                String customerid = (String) map.get("customerid");
                Customer customer = getCustomerByID(customerid);
                if(null != customer){
                    map.put("customername",customer.getName());
                }
            }else{
                map.put("customerid","");
            }
            if("z.id".equals(groupcols) || groupcols.contains("salesuser")){
                String salesuser = (String) map.get("salesuser");
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
                if (null != personnel) {
                    map.put("salesusername",personnel.getName());
                }
            }else {
                map.put("salesuser","");
            }
            if("z.id".equals(groupcols) || groupcols.contains("salesdept")){
                String salesdept = (String) map.get("salesdept");
                DepartMent departMent = getDepartmentByDeptid(salesdept);
                if (null != departMent) {
                    map.put("salesdeptname",departMent.getName());
                }
            }else{
                map.put("salesdept","");
            }
            if("z.id".equals(groupcols) || groupcols.contains("driverid")){
                String driverid = (String) map.get("driverid");
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(driverid);
                if (null != personnel) {
                    map.put("drivername",personnel.getName());
                }
            }else{
                map.put("driverid","");
            }
            if("z.id".equals(groupcols) || groupcols.contains("brandid")){
                String brandid = (String) map.get("brandid");
                Brand brand = getGoodsBrandByID(brandid);
                if (null != brand) {
                    map.put("brandname",brand.getName());
                }
            }else{
                map.put("brandid","");
            }
            if("z.id".equals(groupcols) || groupcols.contains("goodsid")){
                String goodsid = (String) map.get("goodsid");
                GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                if(null != goodsInfo){
                    if(groupcols.contains("goodsid") || groupcols.contains("z.id") ){
                        map.put("goodsname",goodsInfo.getName());
                        map.put("barcode",goodsInfo.getBarcode());
                        map.put("model",goodsInfo.getModel());
                        map.put("brandname",goodsInfo.getBrandName());
                    }
                }
            }else{
                map.put("goodsid","");
            }

        }
        int count = storageReportMapper.getDriverRejectEnterReportDataCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        List<Map> footer = storageReportMapper.getDriverRejectEnterReportDataSum(pageMap);
        for(Map map : footer){
            if(null!=map){
                if("salesuser".equals(col)){
                    map.put("salesusername" , "合计");
                }else if("salesdept".equals(col)){
                    map.put("salesdeptname" , "合计");
                }else if("driverid".equals(col)){
                    map.put("drivername" , "合计");
                }else if("brandid".equals(col)){
                    map.put("brandname" , "合计");
                }else{
                    map.put(col,"合计");
                }
            }else{
                footer = new ArrayList<Map>();
            }
        }
        pageData.setFooter(footer);
        return pageData;
    }

    public boolean addSaleoutDetailWithBrandDiscount() throws Exception{

        List<Map> detailList = storageReportMapper.getSaleoutGoodsByClosedOrder();
        int i = 0;
        if(detailList.size() > 0){
            for(Map map : detailList){
                SaleoutDetailAndDiscount detail = new SaleoutDetailAndDiscount();
                detail.setBrandid((String) map.get("brandid"));
                detail.setTaxamount((BigDecimal) map.get("taxamount"));
                detail.setTotalbox((BigDecimal) map.get("totalbox"));
                detail.setUnitnum((BigDecimal) map.get("unitnum"));

                String goodsid = (String) map.get("goodsid");
                String orderid = (String)map.get("saleorderid");
                detail.setGoodsid(goodsid);
                detail.setSaleorderid(orderid);
                detail.setSourceid((String) map.get("sourceid"));

                String isdiscount = (String)map.get("isdiscount");
                if(StringUtils.isNotEmpty(isdiscount)){//对发货单中的商品明细进行折扣分摊
                    Map map1 = new HashMap();
                    map1.put("orderid",orderid);
                    Map goodsDiscountMap = storageReportMapper.getSaleoutDiscountInfo(map1);//获取该商品的商品折扣金额 总件数
                    if(null != goodsDiscountMap){
                        //根据件数进行商品折扣分摊
                        BigDecimal disamountSum = (BigDecimal) goodsDiscountMap.get("disamountSum");
                        BigDecimal totalboxSum = (BigDecimal) goodsDiscountMap.get("totalboxSum");
                        if(totalboxSum.compareTo(BigDecimal.ZERO) > 0){
                            BigDecimal totalbox = (BigDecimal) map.get("totalbox");
                            BigDecimal percent = totalbox.divide(totalboxSum,6,BigDecimal.ROUND_HALF_EVEN);
                            BigDecimal discountamount = disamountSum.multiply(percent);
                            //商品折扣
                            map.put("discountamount",discountamount);
                            BigDecimal taxamount = (BigDecimal) map.get("taxamount");
                            taxamount = taxamount.subtract(discountamount);
                            map.put("taxamount",taxamount);
                            detail.setTaxamount((BigDecimal) map.get("taxamount"));//发货金额重新赋值
                        }

                    }

                    if(!map.containsKey("discountamount")){
                        detail.setDiscountamount(new BigDecimal(0));
                    }else{
                        detail.setDiscountamount((BigDecimal) map.get("discountamount"));
                    }
                }

                String customerid = (String) map.get("customerid");
                detail.setCustomerid(customerid);
                Customer customer = getCustomerByID(customerid);
                if(null != customer){
                    detail.setCustomername(customer.getName());
                }
                String salesuser = (String) map.get("branduser");
                detail.setBranduser(salesuser);
                Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
                if (personnel != null) {
                    detail.setBrandusername(personnel.getName());
                }
                String salesdept = (String) map.get("persondept");
                detail.setPersondept(salesdept);
                DepartMent departMent = getDepartmentByDeptid(salesdept);
                if (departMent != null) {
                    detail.setPersondeptname(departMent.getName());
                }
                GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                if(null != goodsInfo){
                    detail.setGoodsname(goodsInfo.getName());
                    detail.setBarcode(goodsInfo.getBarcode());
                    detail.setBrandname(goodsInfo.getBrandName());
                    detail.setModel(goodsInfo.getModel());

                }
                i = storageReportMapper.addSaleoutAndRejectEnterForJM(detail);
            }

        }
        return  i > 0;
    }

    /**
     * 获取仓库库存商品盘点记录数据
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public PageData getStorageGoodsCheckLogData(PageMap pageMap) throws Exception {
        String nums = (String) pageMap.getCondition().get("nums");
        if(StringUtils.isEmpty(nums)){
            nums = "3";
        }
        int numsInt = Integer.parseInt(nums);
        List<Map> list = checkListMapper.getStorageGoodsCheckLogData(pageMap);
        for(Map map : list){
            String storageid = (String) map.get("storageid");
            String goodsid = (String) map.get("goodsid");
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if(map.containsKey("brandid")) {
                String brandid = (String) map.get("brandid");
                Brand brand = getGoodsBrandByID(brandid);
                if (null != brand) {
                    map.put("brandname", brand.getName());
                }
            }
            String goodssort = (String) map.get("goodssort");
            WaresClass waresClass  = getWaresClassByID(goodssort);
            if(null!=waresClass){
                map.put("goodssortname",waresClass.getName());
            }
            BigDecimal existingnum = (BigDecimal) map.get("existingnum");
            if(existingnum.compareTo(BigDecimal.ZERO)!=0){
                Map eMap = countGoodsInfoNumber(goodsid,goodsInfo.getAuxunitid(),existingnum);
                map.put("auxexistingdetail",eMap.get("auxnumdetail"));
            }
            BigDecimal usablenum = (BigDecimal) map.get("usablenum");
            if(usablenum.compareTo(BigDecimal.ZERO)!=0){
                Map uMap = countGoodsInfoNumber(goodsid,goodsInfo.getAuxunitid(),usablenum);
                map.put("auxusabledetail",uMap.get("auxnumdetail"));
            }
            List<CheckListDetail> checkList = checkListMapper.getStroageGoodsCheckList(storageid,goodsid,nums+"");
            for(int i=1;i<=checkList.size();i++){
                CheckListDetail checkListDetail = checkList.get(i-1);
                map.put("billid"+i,checkListDetail.getChecklistid());
                map.put("istrue"+i,checkListDetail.getIstrue());
                map.put("audittime"+i,CommonUtils.dataToStr(checkListDetail.getAudittime(),"yyyy-MM-dd HH:mm:ss"));
                map.put("checkuserid"+i,checkListDetail.getCheckuserid());
                map.put("checkusername"+i,checkListDetail.getCheckusername());
				map.put("booknum"+i,checkListDetail.getBooknum());
				map.put("realnum"+i,checkListDetail.getRealnum());
				map.put("profitlossnum"+i,checkListDetail.getProfitlossnum());
                map.put("auxbooknumdetail"+i,checkListDetail.getAuxbooknumdetail());
                map.put("auxrealnumdetail"+i,checkListDetail.getAuxrealnumdetail());
                map.put("auxprofitlossnumdetail"+i,checkListDetail.getAuxprofitlossnumdetail());
            }
        }
        PageData pageData = new PageData(checkListMapper.getStorageGoodsCheckLogCount(pageMap),list,pageMap);
        return pageData;
    }

	@Override
	public PageData showStorageTreeReportListData(PageMap pageMap) throws Exception {
    	boolean isallstorage = false;
		String dataSql = getDataAccessRule("t_storage_summary", "t");
		Map queryMap = pageMap.getCondition();
		String expandid = (String)queryMap.get("expandid");
		String expandpid = (String)queryMap.get("expandpid");
		String isexpand = (String)queryMap.get("isexpand");
		pageMap.setDataSql(dataSql);
		pageMap.setQueryAlias("g");
		//仓库是否独立核算 0否1是 默认否
		String isStorageAccount = getSysParamValue("IsStorageAccount");
		if(StringUtils.isEmpty(isStorageAccount)){
			isStorageAccount = "0";
		}
		pageMap.getCondition().put("isStorageAccount",isStorageAccount);
		List<Map> list = storageReportMapper.getStorageTreeReportListData(pageMap);
		for(Map map : list){
			String id = (String)map.get("data");

			String datatype = (String)map.get("datatype");
			if("brandid".equals(datatype)){
				Brand brand = getGoodsBrandByID(id);
				if(null!=brand){
					map.put("name", brand.getName());
				}
			}else if("supplierid".equals(datatype)){
				if("empty".equals(id)){
					map.put("name", "默认供应商");
				}else{
					BuySupplier supplier = getSupplierInfoById(id);
					if(null!=supplier){
						map.put("name", supplier.getName());
					}
				}

			}else if("storageid".equals(datatype)){
				if("allstorage".equals(id)){
					map.put("name", "总仓库");
					isallstorage= true;
				}else{
					StorageInfo storageInfo = getStorageInfoByID(id);
					if(null!=storageInfo){
						map.put("name", storageInfo.getName());
					}
				}
			}else if("goodsid".equals(datatype)){
				GoodsInfo goodsInfo = getGoodsInfoByID(id);
				if(null!=goodsInfo){
					map.put("name", goodsInfo.getName());
				}
			}

			String brandid = (String) map.get("brandid");
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}
			String goodsid = (String) map.get("goodsid");
			GoodsInfo goodsInfo= getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("model", goodsInfo.getModel());
				map.put("basesaleprice",goodsInfo.getBasesaleprice());
			}


			//辅数量处理
			map.put("auxexistingnum",((BigDecimal)map.get("auxexistingnum")).setScale(3,BigDecimal.ROUND_HALF_UP));
			String auxexistingdetail=CommonUtils.strDigitNumDeal((String)map.get("auxexistingdetail"));
			map.put("auxexistingdetail",auxexistingdetail);
//			map.put("auxexistingnum",CommonUtils.strDigitNumDeal((String)map.get("auxexistingnum")));
			map.put("exitingauxnumdetaildefault",CommonUtils.strDigitNumDeal((String)map.get("auxexistingdetail")));
			map.put("auxusabledetail",CommonUtils.strDigitNumDeal((String)map.get("auxusabledetail")));
			map.put("auxwaitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxwaitdetail")));
			map.put("auxtransitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxtransitdetail")));
			map.put("auxallotwaitdetail",CommonUtils.strDigitNumDeal((String)map.get("auxallotwaitdetail")));
			map.put("auxallotenterdetail",CommonUtils.strDigitNumDeal((String)map.get("auxallotenterdetail")));
			map.put("auxprojectedusabledetail",CommonUtils.strDigitNumDeal((String)map.get("auxprojectedusabledetail")));
			map.put("auxsafedetail",CommonUtils.strDigitNumDeal((String)map.get("auxsafedetail")));
		}
		if(isallstorage&&list.size()==1){
			list.clear();
		}
		List dataList = new ArrayList();
		if(StringUtils.isEmpty(isexpand)){
			int index=0;
			int length=list.size();
			List indexList= new ArrayList();
			if(0==dataList.size()){
				for(Map map : list){
					if("".equals((String)map.get("pid"))){
						indexList.add(map);
					}
				}
			}
			dataList =getChildMap(indexList,list);
		}else{
			dataList = list;
		}
		PageData pageData = new PageData(dataList.size(),dataList,pageMap);
		return pageData;
	}

	private List<Map> getChildMap(List<Map> indexList,List<Map> list) throws Exception{
		List datalist =new ArrayList();
		for(Map indexmap : indexList){
			List<Map> childlist = new ArrayList<Map>();
			for(Map mainmap : list){
				if(mainmap.get("pid").equals(indexmap.get("id"))){
					childlist.add(mainmap);
				}

			}
			if(0==childlist.size()){
				//if(!"".equals((String)indexmap.get("pid"))){
//				indexmap.put("iconCls","icon-annex");
				//}
				indexmap.put("state","closed");
				indexmap.put("rid", CommonUtils.getRandomWithTime());
			}
			else{
				List a =new ArrayList();
				a= getChildMap(childlist,list);
				indexmap.put("children",a);
				indexmap.put("state","closed");
				indexmap.put("rid", CommonUtils.getRandomWithTime());
			}
			datalist.add(indexmap);

		}
		return datalist;
	}


}

