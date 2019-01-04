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
import com.hd.agent.report.dao.LendReportMapper;
import com.hd.agent.report.model.*;
import com.hd.agent.report.service.ILendReportService;
import com.hd.agent.storage.dao.StockInitMapper;
import com.hd.agent.storage.dao.StorageSummaryMapper;
import com.hd.agent.storage.model.Lend;
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
 * 借货还货报表service实现类
 * @author chenwei
 */
public class LendReportServiceImpl extends BaseFilesServiceImpl implements
		ILendReportService {

	private LendReportMapper lendReportMapper;

	/**
	 * 库存初始化dao
	 */
	private StockInitMapper stockInitMapper;
	/**
	 * 库存dao
	 */
	private StorageSummaryMapper storageSummaryMapper;


	private BuySaleReportMapper buySaleReportMapper;

	public BuySaleReportMapper getBuySaleReportMapper() {
		return buySaleReportMapper;
	}

	public void setBuySaleReportMapper(BuySaleReportMapper buySaleReportMapper) {
		this.buySaleReportMapper = buySaleReportMapper;
	}

	public LendReportMapper getLendReportMapper() {
		return lendReportMapper;
	}

	public void setLendReportMapper(LendReportMapper lendReportMapper) {
		this.lendReportMapper = lendReportMapper;
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

	@Override
	public PageData showInOutReportData(PageMap pageMap) throws Exception {
		//数据权限类型
		String datasqltype = null != pageMap.getCondition().get("datasqltype") ? (String) pageMap.getCondition().get("datasqltype") : "";
		//小计列
		String query_sqlall = " 1=1 ";
		String query_sql = " 1=1 ";
		if (!pageMap.getCondition().containsKey("groupcols")) {
			pageMap.getCondition().put("groupcols", "storageid");
		} else {
			String groupcols = (String)pageMap.getCondition().get("groupcols");
			if (groupcols.contains("customerid") && groupcols.contains("supplierid")) {
				groupcols = groupcols.replace("customerid","lendtype");
				groupcols = groupcols.replace("supplierid","lendid");
			} else if (groupcols.contains("customerid")){
				groupcols =groupcols.replace("customerid","lendtype,lendid");
				query_sql +="AND t.lendtype='2'" ;
				query_sqlall +="AND t.lendtype='2'" ;
			} else if (groupcols.contains("supplierid")){
				groupcols =groupcols.replace("supplierid","lendtype,lendid");
				query_sql +="AND t.lendtype='1'" ;
				query_sqlall +="AND t.lendtype='1' ";
			}
			pageMap.getCondition().put("groupcols",groupcols);
		}

		String query_sql_push = "";
		if (pageMap.getCondition().containsKey("goodsid")) {
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				query_sql += " and t1.goodsid = '" + str + "' ";
				query_sqlall += " and t1.goodsid = '" + str + "' ";
			} else {
				query_sql += " and FIND_IN_SET(t1.goodsid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.goodsid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("brandid")) {
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.brandid = '" + str + "' ";
					query_sqlall += " and t1.brandid = '" + str + "' ";
				} else {
					query_sql += " and (t1.brandid is null or t1.brandid = '')";
					query_sqlall += " and (t1.brandid is null or t1.brandid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.brandid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.brandid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("goodssort")) {
			String str = (String) pageMap.getCondition().get("goodssort");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t1.goodssort = '" + str + "' ";
					query_sqlall += " and t1.goodssort = '" + str + "' ";
				} else {
					query_sql += " and (t1.goodssort is null or t1.goodssort = '')";
					query_sqlall += " and (t1.goodssort is null or t1.goodssort = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t1.goodssort,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t1.goodssort,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("storageid")) {
			String str = (String) pageMap.getCondition().get("storageid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.storageid = '" + str + "' ";
					query_sqlall += " and t.storageid = '" + str + "' ";
				} else {
					query_sql += " and (t.storageid is null or t.storageid = '')";
					query_sqlall += " and (t.storageid is null or t.storageid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.storageid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.storageid,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("lendtype")) {
			String str = (String) pageMap.getCondition().get("lendtype");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.lendtype = '" + str + "' ";
					query_sqlall += " and t.lendtype = '" + str + "' ";
				} else {
					query_sql += " and (t.lendtype is null or t.lendtype = '')";
					query_sqlall += " and (t.lendtype is null or t.lendtype = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.lendtype,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.lendtype,'" + str + "')";
			}
		}
		if (pageMap.getCondition().containsKey("lendid")) {
			String str = (String) pageMap.getCondition().get("lendid");
			str = StringEscapeUtils.escapeSql(str);
			if (str.indexOf(",") == -1) {
				if (!"nodata".equals(str)) {
					query_sql += " and t.lendid = '" + str + "' ";
					query_sqlall += " and t.lendid = '" + str + "' ";
				} else {
					query_sql += " and (t.lendid is null or t.lendid = '')";
					query_sqlall += " and (t.lendid is null or t.lendid = '')";
				}
			} else {
				query_sql += " and FIND_IN_SET(t.lendid,'" + str + "')";
				query_sqlall += " and FIND_IN_SET(t.lendid,'" + str + "')";
			}
		}
		/*if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '"+str+"'";
			query_sqlall += " and t.businessdate >= '"+str+"'";
		}*/
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '"+str+"'";
			query_sqlall += " and t.businessdate <= '"+str+"'";
		}
		//query_sql += " and FIND_IN_SET(t.status,'3,4')";
		//query_sqlall += " and FIND_IN_SET(t.status,'3,4')";

		if (StringUtils.isNotEmpty(query_sqlall)) {
			query_sql_push = query_sqlall.replaceAll("t1.", "t.");
		//	query_sql_push = query_sql_push.replaceAll("t.goodsid", "t.brandid");
		}
		pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sqlall", query_sqlall);
		pageMap.getCondition().put("query_sql_push", query_sql_push);



		List<LendInOutReport> list = lendReportMapper.getLendInOutReportSumDataList(pageMap);
		for(LendInOutReport lendInOutReport:list){
			GoodsInfo goodsInfo=getGoodsInfoByID(lendInOutReport.getGoodsid());
			if(goodsInfo!=null){
				Map beginDetailMap=countGoodsInfoNumber(lendInOutReport.getGoodsid(),goodsInfo.getMainunit(),lendInOutReport.getInitnum());
				lendInOutReport.setInitauxnumdetail((String)beginDetailMap.get("auxnumdetail"));
				Map enterDetailMap=countGoodsInfoNumber(lendInOutReport.getGoodsid(),goodsInfo.getMainunit(),lendInOutReport.getEnternum());
				lendInOutReport.setEnterauxnumdetail((String)enterDetailMap.get("auxnumdetail"));
				Map outDetailMap=countGoodsInfoNumber(lendInOutReport.getGoodsid(),goodsInfo.getMainunit(),lendInOutReport.getOutnum());
				lendInOutReport.setOutauxnumdetail((String)outDetailMap.get("auxnumdetail"));
				Map endDetailMap=countGoodsInfoNumber(lendInOutReport.getGoodsid(),goodsInfo.getMainunit(),lendInOutReport.getEndnum());
				lendInOutReport.setEndauxnumdetail((String)endDetailMap.get("auxnumdetail"));
			}

		}
		/*for(LendInOutReport lendInOutReport : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(lendInOutReport.getGoodsid());
			StorageInfo storageInfo = getStorageInfoByID(lendInOutReport.getStorageid());
			if(null!=storageInfo){
				lendInOutReport.setStoragename(storageInfo.getName());
			}
			if(null!=goodsInfo){
				lendInOutReport.setBarcode(goodsInfo.getBarcode());
				lendInOutReport.setGoodsname(goodsInfo.getName());
				lendInOutReport.setBrandname(goodsInfo.getBrandName());
				lendInOutReport.setBoxnum(goodsInfo.getBoxnum());
			}
			WaresClass waresClass = getWaresClassByID(lendInOutReport.getGoodssort());
			if(null!=waresClass){
				lendInOutReport.setGoodssortname(waresClass.getName());
			}

			lendInOutReport.setInitauxnumdetail(CommonUtils.strDigitNumDeal(lendInOutReport.getInitauxnumdetail()));
			lendInOutReport.setEnterauxnumdetail(CommonUtils.strDigitNumDeal(lendInOutReport.getEnterauxnumdetail()));
			lendInOutReport.setOutauxnumdetail(CommonUtils.strDigitNumDeal(lendInOutReport.getOutauxnumdetail()));
			lendInOutReport.setEndauxnumdetail(CommonUtils.strDigitNumDeal(lendInOutReport.getEndauxnumdetail()));
		}*/
		PageData pageData = new PageData(lendReportMapper.getLendInOutReportSumDataCount(pageMap),list,pageMap);
		LendInOutReport lendInOutReportSum = lendReportMapper.getLendInOutReportSumData(pageMap);
		if(null!=lendInOutReportSum){
			lendInOutReportSum.setStoragename("合计");
			lendInOutReportSum.setGoodsname("");
			lendInOutReportSum.setUnitname("");
			lendInOutReportSum.setAuxunitname("");
			lendInOutReportSum.setBarcode("");
			lendInOutReportSum.setBrandname("");
			lendInOutReportSum.setGoodssortname("");
			lendInOutReportSum.setBoxnum(null);
			lendInOutReportSum.setGoodsid("合计");
			lendInOutReportSum.setLendname("合计");
			List<LendInOutReport> footerList = new ArrayList<LendInOutReport>();
			footerList.add(lendInOutReportSum);
			pageData.setFooter(footerList);
		}
		return pageData;
	}
}