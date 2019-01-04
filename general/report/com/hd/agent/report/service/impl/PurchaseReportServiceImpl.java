/**
 * @(#)PurchaseReportServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 16, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.WaresClass;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.purchase.model.BuyOrderDetail;
import com.hd.agent.report.dao.PurchaseReportMapper;
import com.hd.agent.report.model.ArrivalOrderCostAccountReport;
import com.hd.agent.report.model.BuyPaymentBalanceReport;
import com.hd.agent.report.model.PlannedOrderAnalysis;
import com.hd.agent.report.model.PurchaseDetailReport;
import com.hd.agent.report.model.PurchaseQuantityReport;
import com.hd.agent.report.service.IPurchaseReportService;
import com.hd.agent.report.service.IStorageReportService;

/**
 * 
 * 采购报表service实现类
 * @author chenwei
 */
public class PurchaseReportServiceImpl extends BaseFilesServiceImpl implements
		IPurchaseReportService {

	private PurchaseReportMapper purchaseReportMapper;
	
	public PurchaseReportMapper getPurchaseReportMapper() {
		return purchaseReportMapper;
	}

	public void setPurchaseReportMapper(PurchaseReportMapper purchaseReportMapper) {
		this.purchaseReportMapper = purchaseReportMapper;
	}
	
	private IStorageReportService storageReportService;

	public IStorageReportService getStorageReportService() {
		return storageReportService;
	}

	public void setStorageReportService(IStorageReportService storageReportService) {
		this.storageReportService = storageReportService;
	}

	@Override
	public PageData showBuyGoodsReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuyGoodsReportDataList(pageMap);
		int count = purchaseReportMapper.getBuyGoodsReportDataCount(pageMap);
		
		for(PurchaseDetailReport purchaseDetailReport : list){

			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
				purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
			}
			//商品名称
			GoodsInfo goodsInfo = getAllGoodsInfoByID(purchaseDetailReport.getGoodsid());
			if(null != goodsInfo){
				purchaseDetailReport.setGoodsname(goodsInfo.getName());
				purchaseDetailReport.setBrandname(goodsInfo.getBrandName());
				purchaseDetailReport.setBarcode(goodsInfo.getBarcode());
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReport.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
				purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//获取按商品销售情况合计数据
		PurchaseDetailReport purchaseDetailReportSum = purchaseReportMapper.getBuyGoodsReportSumData(pageMap);
		if(null != purchaseDetailReportSum){
            purchaseDetailReportSum.setGoodsname("合计");
			
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReportSum.getEntertaxamount() && null != purchaseDetailReportSum.getEnternotaxamount()){
				purchaseDetailReportSum.setEntertax(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReportSum.getEnternum() && purchaseDetailReportSum.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReportSum.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReportSum.getOutnum().divide(purchaseDetailReportSum.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReportSum.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReportSum.getOuttaxamount() && null != purchaseDetailReportSum.getEntertaxamount()){
				purchaseDetailReportSum.setTotalamount(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getOuttaxamount()));
			}
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			footer.add(purchaseDetailReportSum);
			pageData.setFooter(footer);
		}
		else{
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public PageData showBuyDeptReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuyDeptReportDataList(pageMap);
		int count = purchaseReportMapper.getBuyDeptReportDataCount(pageMap);
		String businessdate1 = (String)pageMap.getCondition().get("businessdate1");
		String businessdate2 = (String)pageMap.getCondition().get("businessdate2");
		if(StringUtils.isEmpty(businessdate1)){
			businessdate1 = "null";
		}
		if(StringUtils.isEmpty(businessdate2)){
			businessdate2 = "null";
		}
		//合计总进货税额
		BigDecimal totalEntertax = new BigDecimal(0);
		for(PurchaseDetailReport purchaseDetailReport : list){
			purchaseDetailReport.setTreeid(purchaseDetailReport.getBuydeptid()+ "_" + businessdate1 + "_" + businessdate2);
			purchaseDetailReport.setState("closed");
			purchaseDetailReport.setParentid("");
			
			pageMap.getCondition().put("buydept", purchaseDetailReport.getBuydeptid());
			//部门名称
			DepartMent departMent = getDepartMentById(purchaseDetailReport.getBuydeptid());
			if(null != departMent){
				purchaseDetailReport.setBuydeptname(departMent.getName());
			}else{
				purchaseDetailReport.setBuydeptname("其他");
			}
//			//计算总的辅单位数量
//			getTotalAuxByPurchaseDetailReport(purchaseDetailReport);
			
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
				purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReport.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
				purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//获取按商品销售情况合计数据
		PurchaseDetailReport purchaseDetailReportSum = purchaseReportMapper.getBuyDeptReportSumData(pageMap);
		if(null != purchaseDetailReportSum){
			purchaseDetailReportSum.setBuydeptname("合计");

			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReportSum.getEntertaxamount() && null != purchaseDetailReportSum.getEnternotaxamount()){
				purchaseDetailReportSum.setEntertax(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReportSum.getEnternum() && purchaseDetailReportSum.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReportSum.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReportSum.getOutnum().divide(purchaseDetailReportSum.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReportSum.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReportSum.getOuttaxamount() && null != purchaseDetailReportSum.getEntertaxamount()){
				purchaseDetailReportSum.setTotalamount(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getOuttaxamount()));
			}
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			footer.add(purchaseDetailReportSum);
			pageData.setFooter(footer);
		}
		else{
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public List showBuyDeptReportDetailList(Map map) throws Exception {
		PageMap pageMap = new PageMap();
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		pageMap.setCondition(map);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuyDeptReportDetailList(pageMap);
		String businessdate1 = (String) map.get("businessdate1");
		String businessdate2 = (String) map.get("businessdate2");
		if(StringUtils.isEmpty(businessdate1)){
			businessdate1="null";
		}
		if(StringUtils.isEmpty(businessdate2)){
			businessdate2="null";
		}
		if(list.size() != 0){
			int num = 0;
			for(PurchaseDetailReport purchaseDetailReport : list){
				num++;
				pageMap.getCondition().put("buydeptid", purchaseDetailReport.getBuydeptid());
				purchaseDetailReport.setTreeid(purchaseDetailReport.getBuydeptid()+"_"+num);
				purchaseDetailReport.setState("open");
				purchaseDetailReport.setParentid(purchaseDetailReport.getBuydeptid()+ "_" + businessdate1 + "_" + businessdate2);
				
				//部门名称
				DepartMent departMent = getDepartMentById(purchaseDetailReport.getBuydeptid());
				if(null != departMent){
					purchaseDetailReport.setBuydeptname(departMent.getName());
				}
				else{
					purchaseDetailReport.setBuydeptname("其他");
				}
				//进货税额 = 进货金额-进货无税金额
				if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
					purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
				}
				//商品名称
				GoodsInfo goodsInfo = super.getBaseFilesGoodsMapper().getGoodsInfo(purchaseDetailReport.getGoodsid());
				if(null != goodsInfo){
					purchaseDetailReport.setGoodsname(goodsInfo.getName());
					purchaseDetailReport.setBarcode(goodsInfo.getBarcode());
				}
				//验收退货率 = 退货数量/进货主数量
				if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
					BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
					purchaseDetailReport.setCheckoutrate(checkoutrate);
				}
				//进货金额总计 = 进货金额-退货金额
				if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
					purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
				}
			}
		}
		return list;
	}

	@Override
	public PageData showBuyBrandReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuyBrandReportDataList(pageMap);
		int count = purchaseReportMapper.getBuyBrandReportDataCount(pageMap);
		String businessdate1 = (String)pageMap.getCondition().get("businessdate1");
		String businessdate2 = (String)pageMap.getCondition().get("businessdate2");
		if(StringUtils.isEmpty(businessdate1)){
			businessdate1 = "null";
		}
		if(StringUtils.isEmpty(businessdate2)){
			businessdate2 = "null";
		}
		//合计总进货税额
		for(PurchaseDetailReport purchaseDetailReport : list){
			purchaseDetailReport.setTreeid(purchaseDetailReport.getBrandid()+ "_" + businessdate1 + "_" + businessdate2);
			purchaseDetailReport.setState("closed");
			purchaseDetailReport.setParentid("");
			
			pageMap.getCondition().put("brand", purchaseDetailReport.getBrandid());
			//品牌名称
			Brand brand = super.getBaseFilesGoodsMapper().getBrandInfo(purchaseDetailReport.getBrandid());
			if(null != brand){
				purchaseDetailReport.setBrandname(brand.getName());
			}
			else{
				purchaseDetailReport.setBrandname("其他");
			}
//			//计算总的辅单位数量
//			getTotalAuxByPurchaseDetailReport(purchaseDetailReport);
			//进货总数量描述
			if(pageMap.getCondition().containsKey("isflag")){
				if(null != purchaseDetailReport.getEnternum()){
					purchaseDetailReport.setEnternumdetail(purchaseDetailReport.getEnternum().intValue()+purchaseDetailReport.getUnitname());
				}
			}else{
				if(null != purchaseDetailReport.getEnternum()){
					purchaseDetailReport.setEnternumdetail(purchaseDetailReport.getEnternum()+purchaseDetailReport.getUnitname());
				}
			}
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
				purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReport.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
				purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//获取按商品销售情况合计数据
		PurchaseDetailReport purchaseDetailReportSum = purchaseReportMapper.getBuyBrandReportSumData(pageMap);
		if(null != purchaseDetailReportSum){
			purchaseDetailReportSum.setBrandname("合计");
			
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReportSum.getEntertaxamount() && null != purchaseDetailReportSum.getEnternotaxamount()){
				purchaseDetailReportSum.setEntertax(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReportSum.getEnternum() && purchaseDetailReportSum.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReportSum.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReportSum.getOutnum().divide(purchaseDetailReportSum.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReportSum.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReportSum.getOuttaxamount() && null != purchaseDetailReportSum.getEntertaxamount()){
				purchaseDetailReportSum.setTotalamount(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getOuttaxamount()));
			}
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			footer.add(purchaseDetailReportSum);
			pageData.setFooter(footer);
		}
		else{
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public List showBuyBrandReportDetailList(Map map) throws Exception {
		PageMap pageMap = new PageMap();
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		pageMap.setCondition(map);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuyBrandReportDetailList(pageMap);
		String businessdate1 = (String) map.get("businessdate1");
		String businessdate2 = (String) map.get("businessdate2");
		if(StringUtils.isEmpty(businessdate1)){
			businessdate1="null";
		}
		if(StringUtils.isEmpty(businessdate2)){
			businessdate2="null";
		}
		if(list.size() != 0){
			int num = 0;
			for(PurchaseDetailReport purchaseDetailReport : list){
				num++;
				pageMap.getCondition().put("brandid", purchaseDetailReport.getBrandid());
				purchaseDetailReport.setTreeid(purchaseDetailReport.getBrandid()+"_"+num);
				purchaseDetailReport.setState("open");
				purchaseDetailReport.setParentid(purchaseDetailReport.getBrandid()+ "_" + businessdate1 + "_" + businessdate2);
				
				//品牌名称
				Brand brand = super.getBaseFilesGoodsMapper().getBrandInfo(purchaseDetailReport.getBrandid());
				if(null != brand){
					purchaseDetailReport.setBrandname(brand.getName());
				}
				else{
					purchaseDetailReport.setBrandname("其他");
				}
				//进货税额 = 进货金额-进货无税金额
				if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
					purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
				}
				//商品名称
				GoodsInfo goodsInfo = super.getBaseFilesGoodsMapper().getGoodsInfo(purchaseDetailReport.getGoodsid());
				if(null != goodsInfo){
					purchaseDetailReport.setGoodsname(goodsInfo.getName());
					purchaseDetailReport.setBarcode(goodsInfo.getBarcode());
				}
				//验收退货率 = 退货数量/进货主数量
				if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
					BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
					purchaseDetailReport.setCheckoutrate(checkoutrate);
				}
				//进货金额总计 = 进货金额-退货金额
				if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
					purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
				}
			}
		}
		return list;
	}

	@Override
	public PageData showBuySupplierReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuySupplierReportDataList(pageMap);
		int count = purchaseReportMapper.getBuySupplierReportDataCount(pageMap);
		String businessdate1 = (String)pageMap.getCondition().get("businessdate1");
		String businessdate2 = (String)pageMap.getCondition().get("businessdate2");
		if(StringUtils.isEmpty(businessdate1)){
			businessdate1 = "null";
		}
		if(StringUtils.isEmpty(businessdate2)){
			businessdate2 = "null";
		}
		//合计总进货税额
		for(PurchaseDetailReport purchaseDetailReport : list){
			purchaseDetailReport.setTreeid(purchaseDetailReport.getSupplierid()+ "_" + businessdate1 + "_" + businessdate2);
			purchaseDetailReport.setState("closed");
			purchaseDetailReport.setParentid("");
			
			pageMap.getCondition().put("supplier", purchaseDetailReport.getSupplierid());
			//供应商名称
			BuySupplier buySupplier = getSupplierInfoById(purchaseDetailReport.getSupplierid());
			if(null != buySupplier){
				purchaseDetailReport.setSuppliername(buySupplier.getName());
			}
			else{
				purchaseDetailReport.setSuppliername("其他");
			}
			//条形码
			if(StringUtils.isNotEmpty(purchaseDetailReport.getGoodsid())){
				GoodsInfo goodsInfo = getGoodsInfoByID(purchaseDetailReport.getGoodsid());
				if(null != goodsInfo){
					purchaseDetailReport.setBarcode(goodsInfo.getBarcode());
				}
			}
//			//计算总的辅单位数量
//			getTotalAuxByPurchaseDetailReport(purchaseDetailReport);
			
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
				purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReport.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
				purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//获取按商品销售情况合计数据
		PurchaseDetailReport purchaseDetailReportSum = purchaseReportMapper.getBuySupplierReportSumData(pageMap);
		if(null != purchaseDetailReportSum){
			purchaseDetailReportSum.setSuppliername("合计");
			
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReportSum.getEntertaxamount() && null != purchaseDetailReportSum.getEnternotaxamount()){
				purchaseDetailReportSum.setEntertax(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReportSum.getEnternum() && purchaseDetailReportSum.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReportSum.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReportSum.getOutnum().divide(purchaseDetailReportSum.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReportSum.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReportSum.getOuttaxamount() && null != purchaseDetailReportSum.getEntertaxamount()){
				purchaseDetailReportSum.setTotalamount(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getOuttaxamount()));
			}
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			footer.add(purchaseDetailReportSum);
			pageData.setFooter(footer);
		}
		else{
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public List showBuySupplierReportDetailList(Map map) throws Exception {
		PageMap pageMap = new PageMap();
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		pageMap.setCondition(map);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuySupplierReportDetailList(pageMap);
		String businessdate1 = (String) map.get("businessdate1");
		String businessdate2 = (String) map.get("businessdate2");
		if(StringUtils.isEmpty(businessdate1)){
			businessdate1="null";
		}
		if(StringUtils.isEmpty(businessdate2)){
			businessdate2="null";
		}
		if(list.size() != 0){
			int num = 0;
			for(PurchaseDetailReport purchaseDetailReport : list){
				num++;
				pageMap.getCondition().put("supplierid", purchaseDetailReport.getSupplierid());
				purchaseDetailReport.setTreeid(purchaseDetailReport.getSupplierid()+"_"+num);
				purchaseDetailReport.setState("open");
				purchaseDetailReport.setParentid(purchaseDetailReport.getSupplierid()+ "_" + businessdate1 + "_" + businessdate2);
				
				//供应商名称
				BuySupplier buySupplier = getSupplierInfoById(purchaseDetailReport.getSupplierid());
				if(null != buySupplier){
					purchaseDetailReport.setSuppliername(buySupplier.getName());
				}
				else{
					purchaseDetailReport.setSuppliername("其他");
				}
				//进货税额 = 进货金额-进货无税金额
				if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
					purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
				}
				//商品名称
				GoodsInfo goodsInfo = super.getBaseFilesGoodsMapper().getGoodsInfo(purchaseDetailReport.getGoodsid());
				if(null != goodsInfo){
					purchaseDetailReport.setGoodsname(goodsInfo.getName());
					purchaseDetailReport.setBarcode(goodsInfo.getBarcode());
				}
				//验收退货率 = 退货数量/进货主数量
				if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
					BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
					purchaseDetailReport.setCheckoutrate(checkoutrate);
				}
				//进货金额总计 = 进货金额-退货金额
				if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
					purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
				}
			}
		}
		return list;
	}
	
	@Override
	public PageData getBuySupplierReportDetailData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuySupplierReportDetailList(pageMap);
		int count = purchaseReportMapper.getBuySupplierReportDetailCount(pageMap);
		//合计
		BigDecimal enterAuxSum = BigDecimal.ZERO;
		BigDecimal enterAuxremainderSum = BigDecimal.ZERO;
		BigDecimal outAuxnumSum = BigDecimal.ZERO;
		BigDecimal outAuxremainderSum = BigDecimal.ZERO;
		if(list.size() != 0){
			for(PurchaseDetailReport purchaseDetailReport : list){
//				Map auxenternumMap = countGoodsInfoNumber(purchaseDetailReport.getGoodsid(), purchaseDetailReport.getAuxunitid(), purchaseDetailReport.getEnternum());
//				purchaseDetailReport.setAuxenternumdetail((String) auxenternumMap.get("auxnumdetail"));
//				Map auxoutnumMap = countGoodsInfoNumber(purchaseDetailReport.getGoodsid(), purchaseDetailReport.getAuxunitid(), purchaseDetailReport.getOutnum());
//				purchaseDetailReport.setAuxoutnumdetail((String) auxoutnumMap.get("auxnumdetail"));
//
//				BigDecimal auxenternum = (null != auxenternumMap.get("auxInteger")) ? new BigDecimal(auxenternumMap.get("auxInteger").toString()) : BigDecimal.ZERO;
//				BigDecimal auxenterremainder = (null != auxenternumMap.get("auxremainder")) ? new BigDecimal(auxenternumMap.get("auxremainder").toString()) : BigDecimal.ZERO;
//				enterAuxSum = enterAuxSum.add(auxenternum);
//				enterAuxremainderSum = enterAuxremainderSum.add(auxenterremainder);
//				purchaseDetailReport.setAuxenternum(auxenternum);
//				purchaseDetailReport.setAuxenterremainder(auxenterremainder);
//				BigDecimal auxoutnum = (null != auxoutnumMap.get("auxInteger")) ? new BigDecimal(auxoutnumMap.get("auxInteger").toString()) : BigDecimal.ZERO;
//				BigDecimal auxoutremainder = (null != auxoutnumMap.get("auxremainder")) ? new BigDecimal(auxoutnumMap.get("auxremainder").toString()) : BigDecimal.ZERO;
//				outAuxnumSum = outAuxnumSum.add(auxoutnum);
//				outAuxremainderSum = outAuxremainderSum.add(auxoutremainder);
//				purchaseDetailReport.setAuxoutnum(auxoutnum);
//				purchaseDetailReport.setAuxoutremainder(auxoutremainder);
				
				//供应商名称
				BuySupplier buySupplier = getSupplierInfoById(purchaseDetailReport.getSupplierid());
				if(null != buySupplier){
					purchaseDetailReport.setSuppliername(buySupplier.getName());
				}
				else{
					purchaseDetailReport.setSuppliername("其他");
				}
				//进货税额 = 进货金额-进货无税金额
				if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
					purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
				}
				//商品名称
				GoodsInfo goodsInfo = super.getBaseFilesGoodsMapper().getGoodsInfo(purchaseDetailReport.getGoodsid());
				if(null != goodsInfo){
					purchaseDetailReport.setGoodsname(goodsInfo.getName());
					purchaseDetailReport.setBarcode(goodsInfo.getBarcode());
				}
				//验收退货率 = 退货数量/进货主数量
				if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
					BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
					purchaseDetailReport.setCheckoutrate(checkoutrate);
				}
				//进货金额总计 = 进货金额-退货金额
				if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
					purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
				}
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//合计
		List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
		PurchaseDetailReport purchaseDetailReportSum = purchaseReportMapper.getBuySupplierReportSumData(pageMap);
		if(null != purchaseDetailReportSum){
			purchaseDetailReportSum.setGoodsname("合计");
			
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReportSum.getEntertaxamount() && null != purchaseDetailReportSum.getEnternotaxamount()){
				purchaseDetailReportSum.setEntertax(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReportSum.getEnternum() && purchaseDetailReportSum.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReportSum.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReportSum.getOutnum().divide(purchaseDetailReportSum.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReportSum.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReportSum.getOuttaxamount() && null != purchaseDetailReportSum.getEntertaxamount()){
				purchaseDetailReportSum.setTotalamount(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getOuttaxamount()));
			}
			footer.add(purchaseDetailReportSum);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showBuyUserReportData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuyUserReportDataList(pageMap);
		int count = purchaseReportMapper.getBuyUserReportDataCount(pageMap);
		String businessdate1 = (String)pageMap.getCondition().get("businessdate1");
		String businessdate2 = (String)pageMap.getCondition().get("businessdate2");
		if(StringUtils.isEmpty(businessdate1)){
			businessdate1 = "null";
		}
		if(StringUtils.isEmpty(businessdate2)){
			businessdate2 = "null";
		}
		for(PurchaseDetailReport purchaseDetailReport : list){
			purchaseDetailReport.setTreeid(purchaseDetailReport.getBuyuserid()+ "_" + businessdate1 + "_" + businessdate2);
			purchaseDetailReport.setState("closed");
			purchaseDetailReport.setParentid("");
			
			pageMap.getCondition().put("buyuser", purchaseDetailReport.getBuyuserid());
			//采购员
			Personnel personnel = getPersonnelById(purchaseDetailReport.getBuyuserid());
			if(null != personnel){
				purchaseDetailReport.setBuyusername(personnel.getName());
			}
			else{
				purchaseDetailReport.setBuyusername("其他");
			}
//			//计算总的辅单位数量
//			getTotalAuxByPurchaseDetailReport(purchaseDetailReport);
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
				purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReport.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
				purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
			}
		}
		PageData pageData = new PageData(count,list,pageMap);
		//获取按商品销售情况合计数据
		PurchaseDetailReport purchaseDetailReportSum = purchaseReportMapper.getBuyUserReportSumData(pageMap);
		if(null != purchaseDetailReportSum){
			purchaseDetailReportSum.setBuyusername("合计");
			
			//进货税额 = 进货金额-进货无税金额
			if(null != purchaseDetailReportSum.getEntertaxamount() && null != purchaseDetailReportSum.getEnternotaxamount()){
				purchaseDetailReportSum.setEntertax(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getEnternotaxamount()));
			}
			//验收退货率 = 退货数量/进货主数量
			if(null != purchaseDetailReportSum.getEnternum() && purchaseDetailReportSum.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReportSum.getOutnum()){
				BigDecimal checkoutrate = purchaseDetailReportSum.getOutnum().divide(purchaseDetailReportSum.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
				purchaseDetailReportSum.setCheckoutrate(checkoutrate);
			}
			//进货金额总计 = 进货金额-退货金额
			if(null != purchaseDetailReportSum.getOuttaxamount() && null != purchaseDetailReportSum.getEntertaxamount()){
				purchaseDetailReportSum.setTotalamount(purchaseDetailReportSum.getEntertaxamount().subtract(purchaseDetailReportSum.getOuttaxamount()));
			}
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			footer.add(purchaseDetailReportSum);
			pageData.setFooter(footer);
		}
		else{
			List<PurchaseDetailReport> footer = new ArrayList<PurchaseDetailReport>();
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public List showBuyUserReportDetailList(Map map) throws Exception {
		PageMap pageMap = new PageMap();
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		pageMap.setCondition(map);
		List<PurchaseDetailReport> list = purchaseReportMapper.getBuyUserReportDetailList(pageMap);
		String businessdate1 = (String) map.get("businessdate1");
		String businessdate2 = (String) map.get("businessdate2");
		if(StringUtils.isEmpty(businessdate1)){
			businessdate1="null";
		}
		if(StringUtils.isEmpty(businessdate2)){
			businessdate2="null";
		}
		if(list.size() != 0){
			int num = 0;
			for(PurchaseDetailReport purchaseDetailReport : list){
				num++;
				pageMap.getCondition().put("buyuserid", purchaseDetailReport.getBuyuserid());
				purchaseDetailReport.setTreeid(purchaseDetailReport.getBuyuserid()+"_"+num);
				purchaseDetailReport.setState("open");
				purchaseDetailReport.setParentid(purchaseDetailReport.getBuyuserid()+ "_" + businessdate1 + "_" + businessdate2);
				
				//采购员
				Personnel personnel = getPersonnelById(purchaseDetailReport.getBuyuserid());
				if(null != personnel){
					purchaseDetailReport.setBuyusername(personnel.getName());
				}
				else{
					purchaseDetailReport.setBuyusername("其他");
				}
				//进货税额 = 进货金额-进货无税金额
				if(null != purchaseDetailReport.getEntertaxamount() && null != purchaseDetailReport.getEnternotaxamount()){
					purchaseDetailReport.setEntertax(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getEnternotaxamount()));
				}
				GoodsInfo goodsInfo = super.getBaseFilesGoodsMapper().getGoodsInfo(purchaseDetailReport.getGoodsid());
				if(null != goodsInfo){
					purchaseDetailReport.setGoodsname(goodsInfo.getName());
					purchaseDetailReport.setBarcode(goodsInfo.getBarcode());
				}
				//验收退货率 = 退货数量/进货主数量
				if(null != purchaseDetailReport.getEnternum() && purchaseDetailReport.getEnternum().compareTo(new BigDecimal(0))==1 && null != purchaseDetailReport.getOutnum()){
					BigDecimal checkoutrate = purchaseDetailReport.getOutnum().divide(purchaseDetailReport.getEnternum(), 6, BigDecimal.ROUND_HALF_UP);
					purchaseDetailReport.setCheckoutrate(checkoutrate);
				}
				//进货金额总计 = 进货金额-退货金额
				if(null != purchaseDetailReport.getOuttaxamount() && null != purchaseDetailReport.getEntertaxamount()){
					purchaseDetailReport.setTotalamount(purchaseDetailReport.getEntertaxamount().subtract(purchaseDetailReport.getOuttaxamount()));
				}
			}
		}
		return list;
	}
	
	/**
	 * 计算总的辅单位数量 进货数量与退货数量
	 * @param purchaseDetailReport
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public void getTotalAuxByPurchaseDetailReport(PurchaseDetailReport purchaseDetailReport){
		String auxenternumdetail = "";
		String auxoutnumdetail = "";
		if(purchaseDetailReport.getAuxenternum().compareTo(BigDecimal.ZERO)==1){
			auxenternumdetail += purchaseDetailReport.getAuxenternum().setScale(0,BigDecimal.ROUND_DOWN).toString()+purchaseDetailReport.getAuxunitname() ;
		}
		if(purchaseDetailReport.getAuxenterremainder().compareTo(BigDecimal.ZERO)==1){
			auxenternumdetail += purchaseDetailReport.getAuxenterremainder().setScale(0,BigDecimal.ROUND_DOWN).toString()+purchaseDetailReport.getUnitname();
		}
		if(purchaseDetailReport.getAuxoutnum().compareTo(BigDecimal.ZERO)==1){
			auxoutnumdetail += purchaseDetailReport.getAuxoutnum().setScale(0,BigDecimal.ROUND_DOWN).toString()+purchaseDetailReport.getAuxunitname() ;
		}
		if(purchaseDetailReport.getAuxoutremainder().compareTo(BigDecimal.ZERO)==1){
			auxoutnumdetail += purchaseDetailReport.getAuxoutremainder().setScale(0,BigDecimal.ROUND_DOWN).toString()+purchaseDetailReport.getUnitname();
		}
		purchaseDetailReport.setAuxenternumdetail(auxenternumdetail);
		purchaseDetailReport.setAuxoutnumdetail(auxoutnumdetail);
	}
	@Override
	public PageData showPlannedOrderAnalysisPageData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		Map<String, Object> condition=pageMap.getCondition();
		List<PlannedOrderAnalysis> list=purchaseReportMapper.showPlannedOrderAnalysisPageList(pageMap);
		String sdateStr="";
		String edateStr="";
		String referDateBy="";
		if(null!=condition){
			if(null!=condition.get("referDateBy")){
				referDateBy=(String)condition.get("referDateBy");
			}

			if(null!=referDateBy && "1".equals(referDateBy.trim())){
				referDateBy="1";
			}
			if("1".equals(referDateBy)){
				if(null!=condition.get("tqstartdate")){
					sdateStr=(String)condition.get("tqstartdate");
				}
				if(null!=condition.get("tqenddate")){
					edateStr=(String)condition.get("tqenddate");
				}				
			}else{
				if(null!=condition.get("qqstartdate")){
					sdateStr=(String)condition.get("qqstartdate");
				}
				if(null!=condition.get("qqenddate")){
					edateStr=(String)condition.get("qqenddate");
				}				
			}
		}
		int saleday=getSalesDays(sdateStr,edateStr);
		if(null!=list && list.size()>0){
			for(PlannedOrderAnalysis item : list){
				item.setSaleday(saleday);
				item.setBoxnum(BigDecimal.ZERO);
				item.setBoxamount(BigDecimal.ZERO);
				item.setOrderamount(BigDecimal.ZERO);
				item.setOrdernum(BigDecimal.ZERO);
				item.setOrderunitnum(BigDecimal.ZERO);
				item.setOrderauxremainder(BigDecimal.ZERO);
				
				if(null==item.getExistingunitnum()){
					item.setExistingunitnum(BigDecimal.ZERO);
				}
				if(null==item.getTransitunitnum()){
					item.setTransitunitnum(BigDecimal.ZERO);
				}
				if(null==item.getCurstorageunitnum()){
					item.setCurstorageunitnum(BigDecimal.ZERO);
				}
				if(null==item.getTqsaleunitnum()){
					item.setTqsaleunitnum(BigDecimal.ZERO);
				}
				if(null==item.getQqsaleunitnum()){
					item.setQqsaleunitnum(BigDecimal.ZERO);
				}
				if(null==item.getCanorderunitnum()){
					item.setCanorderunitnum(BigDecimal.ZERO);
				}
				if(null==item.getCanorderamount()){
					item.setCanorderamount(BigDecimal.ZERO);
				}
				
				if(StringUtils.isNotEmpty(item.getGoodsid())){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(item.getGoodsid());
					if(null!=goodsInfo){
						//条形码
						if(StringUtils.isNotEmpty(goodsInfo.getBarcode())){
							item.setBarcode(goodsInfo.getBarcode());
						}
						if( null!=goodsInfo.getNewbuyprice()){
							item.setBuyprice(goodsInfo.getNewbuyprice());
							item.setBuyprice(item.getBuyprice().setScale(6,BigDecimal.ROUND_HALF_UP));
						}else{
							item.setBuyprice(BigDecimal.ZERO);
						}
						if(null!=goodsInfo.getBoxnum()){
							item.setBoxnum(goodsInfo.getBoxnum());
							item.setBoxamount(goodsInfo.getBoxnum().multiply(item.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
						}
						item.setGoodsname(goodsInfo.getName());
						//品牌
						if(StringUtils.isNotEmpty(goodsInfo.getBrand())){
							item.setBrandid(goodsInfo.getBrand());
						}
						if(StringUtils.isNotEmpty(goodsInfo.getBrandName())){
							item.setBrandname(goodsInfo.getBrandName());
						}
						item.setGoodsInfo(goodsInfo);
						//主单位编码
						if(StringUtils.isNotEmpty(goodsInfo.getMainunit())){
							item.setUnitid(goodsInfo.getMainunit());
						}
						//主单位
						if(StringUtils.isNotEmpty(goodsInfo.getMainunitName())){
							item.setUnitname(goodsInfo.getMainunitName());
						}
						//辅单位编码
						if(StringUtils.isNotEmpty(goodsInfo.getAuxunitid())){
							item.setAuxunitid(goodsInfo.getAuxunitid());
						}
						//辅单位
						if(StringUtils.isNotEmpty(goodsInfo.getAuxunitname())){
							item.setAuxunitname(goodsInfo.getAuxunitname());
						}
						if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) { // 默认分类（来源商品分类）
							WaresClass waresClass =   getWaresClassByID(goodsInfo.getDefaultsort());
							if (waresClass != null) {
								item.setGoodssortname(waresClass.getThisname());
							}
							item.setGoodssort(goodsInfo.getDefaultsort());
						}
						
					}
				}
				if(item.getBoxnum().compareTo(BigDecimal.ZERO)>0){
					item.setExistingnum(item.getExistingunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					item.setTransitnum(item.getTransitunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					item.setCurstoragenum(item.getCurstorageunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					item.setCanordernum(item.getCanorderunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					//前期销售数量
					item.setTqsalenum(item.getTqsaleunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					//前期销售金额
					item.setTqsaleamount(item.getTqsaleunitnum().multiply(item.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//同期销售数量
					item.setQqsalenum(item.getQqsaleunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					//同期销售金额
					item.setQqsaleamount(item.getQqsaleunitnum().multiply(item.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//本次采购箱数量
					if(item.getCanordernum().compareTo(BigDecimal.ZERO)>0){
						item.setCanordernum(item.getCanordernum().setScale(0, BigDecimal.ROUND_HALF_UP));	//本次采购量，保持整数位
						item.setOrdernum(item.getCanordernum());
						//本次采购主单位数量
						item.setOrderunitnum(item.getOrdernum().multiply(item.getBoxnum()).setScale(3,BigDecimal.ROUND_HALF_UP));
					}
					item.setTotalstoragenum(item.getCurstoragenum().add(item.getOrdernum()));
					item.setTotalstorageunitnum(item.getCurstorageunitnum().add(item.getOrderunitnum()));
					
					BigDecimal saledate=BigDecimal.ZERO;
					if("1".equals(referDateBy)){
						if(item.getTqsalenum().compareTo(BigDecimal.ZERO)>0){
							saledate=item.getTotalstoragenum().multiply(new BigDecimal(item.getSaleday())).divide(item.getTqsalenum(), 0, BigDecimal.ROUND_HALF_UP);
						}
					}else{
						if(item.getQqsalenum().compareTo(BigDecimal.ZERO)>0){
							saledate=item.getTotalstoragenum().multiply(new BigDecimal(item.getSaleday())).divide(item.getQqsalenum(), 0, BigDecimal.ROUND_HALF_UP);
						}
					}
					item.setCansaleday(saledate.intValue());
				}
				if(item.getBoxamount().compareTo(BigDecimal.ZERO)>0){
					item.setExistingamount(item.getExistingnum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					item.setTransitamount(item.getTransitnum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					item.setCurstorageamount(item.getCurstoragenum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					item.setTotalstorageamount(item.getTotalstoragenum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					
					item.setCanorderamount(item.getCanordernum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					
					if(item.getOrdernum().compareTo(BigDecimal.ZERO)>=0){
						item.setOrderamount(item.getOrdernum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));						
					}					
				}				
			}
		}
		PageData pageData=new PageData(purchaseReportMapper.showPlannedOrderAnalysisPageCount(pageMap), 
						list, pageMap);
		return pageData;
	}
	
	@Override
	public PageData showStorageSummaryByBrand(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		Map<String, Object> condition=pageMap.getCondition();
		condition.put("isflag", "true");
		List<PlannedOrderAnalysis> list=purchaseReportMapper.showPlannedOrderAnalysisPageList(pageMap);
		String sdateStr="";
		String edateStr="";
		String referDateBy="";
		if(null!=condition){
			if(null!=condition.get("referDateBy")){
				referDateBy=(String)condition.get("referDateBy");
			}

			if(null!=referDateBy && "1".equals(referDateBy.trim())){
				referDateBy="1";
			}
			if("1".equals(referDateBy)){
				if(null!=condition.get("tqstartdate")){
					sdateStr=(String)condition.get("tqstartdate");
				}
				if(null!=condition.get("tqenddate")){
					edateStr=(String)condition.get("tqenddate");
				}				
			}else{
				if(null!=condition.get("qqstartdate")){
					sdateStr=(String)condition.get("qqstartdate");
				}
				if(null!=condition.get("qqenddate")){
					edateStr=(String)condition.get("qqenddate");
				}				
			}
		}
		int saleday=getSalesDays(sdateStr,edateStr);
		
		Map<String, List<PlannedOrderAnalysis>> brandMap=new HashMap<String, List<PlannedOrderAnalysis>>();
		if(null!=list && list.size()>0){
			for(PlannedOrderAnalysis item : list){
				item.setSaleday(saleday);
				item.setBoxnum(BigDecimal.ZERO);
				item.setBoxamount(BigDecimal.ZERO);
				item.setOrderamount(BigDecimal.ZERO);
				item.setOrdernum(BigDecimal.ZERO);
				item.setOrderunitnum(BigDecimal.ZERO);
				item.setOrderauxremainder(BigDecimal.ZERO);

				if(null==item.getExistingunitnum()){
					item.setExistingunitnum(BigDecimal.ZERO);
				}
				if(null==item.getTransitunitnum()){
					item.setTransitunitnum(BigDecimal.ZERO);
				}
				if(null==item.getCurstorageunitnum()){
					item.setCurstorageunitnum(BigDecimal.ZERO);
				}
				if(null==item.getTqsaleunitnum()){
					item.setTqsaleunitnum(BigDecimal.ZERO);
				}
				if(null==item.getQqsaleunitnum()){
					item.setQqsaleunitnum(BigDecimal.ZERO);
				}
				if(null==item.getCanorderunitnum()){
					item.setCanorderunitnum(BigDecimal.ZERO);
				}
				if(null==item.getCanorderamount()){
					item.setCanorderamount(BigDecimal.ZERO);
				}
				
				if(StringUtils.isNotEmpty(item.getGoodsid())){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(item.getGoodsid());
					if(null!=goodsInfo){
						//条形码
						if(StringUtils.isNotEmpty(goodsInfo.getBarcode())){
							item.setBarcode(goodsInfo.getBarcode());
						}
						if( null!=goodsInfo.getNewbuyprice()){
							item.setBuyprice(goodsInfo.getNewbuyprice());
							item.setBuyprice(item.getBuyprice().divide(new BigDecimal(1),6, BigDecimal.ROUND_HALF_UP));
						}else{
							item.setBuyprice(BigDecimal.ZERO);
						}
						if(null!=goodsInfo.getBoxnum()){
							item.setBoxnum(goodsInfo.getBoxnum());
							item.setBoxamount(goodsInfo.getBoxnum().multiply(item.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
						}
						item.setGoodsname(goodsInfo.getName());
						//品牌
						if(StringUtils.isNotEmpty(goodsInfo.getBrand())){
							item.setBrandid(goodsInfo.getBrand());
						}
						if(StringUtils.isNotEmpty(goodsInfo.getBrandName())){
							item.setBrandname(goodsInfo.getBrandName());
						}
						//item.setGoodsInfo(goodsInfo);
						
						//主单位编码
						if(StringUtils.isNotEmpty(goodsInfo.getMainunit())){
							item.setUnitid(goodsInfo.getMainunit());
						}
						//主单位
						if(StringUtils.isNotEmpty(goodsInfo.getMainunitName())){
							item.setUnitname(goodsInfo.getMainunitName());
						}
						//辅单位编码
						if(StringUtils.isNotEmpty(goodsInfo.getAuxunitid())){
							item.setAuxunitid(goodsInfo.getAuxunitid());
						}
						//辅单位
						if(StringUtils.isNotEmpty(goodsInfo.getAuxunitname())){
							item.setAuxunitname(goodsInfo.getAuxunitname());
						}
					}
				}
				if(item.getBoxnum().compareTo(BigDecimal.ZERO)>0){
					item.setExistingnum(item.getExistingunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					item.setTransitnum(item.getTransitunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					item.setCurstoragenum(item.getCurstorageunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					item.setCanordernum(item.getCanorderunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					//前期销售数量
					item.setTqsalenum(item.getTqsaleunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					//前期销售金额
					item.setTqsaleamount(item.getTqsaleunitnum().multiply(item.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					item.setQqsalenum(item.getQqsaleunitnum().divide(item.getBoxnum(), 3, BigDecimal.ROUND_HALF_UP));
					//同期销售金额
					item.setQqsaleamount(item.getQqsaleunitnum().multiply(item.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					//本次采购箱数量
					if(item.getCanordernum().compareTo(BigDecimal.ZERO)>0){
						item.setCanordernum(item.getCanordernum().setScale(0, BigDecimal.ROUND_HALF_UP));	//本次采购量，保持整数位
						item.setOrdernum(item.getCanordernum());
						//本次采购主单位数量
						item.setOrderunitnum(item.getOrdernum().multiply(item.getBoxnum()).setScale(3,BigDecimal.ROUND_HALF_UP));
					}
					item.setTotalstoragenum(item.getCurstoragenum().add(item.getOrdernum()));
					item.setTotalstorageunitnum(item.getCurstorageunitnum().add(item.getOrderunitnum()));
					BigDecimal saledate=BigDecimal.ZERO;
					if("1".equals(referDateBy)){
						if(item.getTqsalenum().compareTo(BigDecimal.ZERO)>0){
							saledate=item.getTotalstoragenum().multiply(new BigDecimal(item.getSaleday())).divide(item.getTqsalenum(), 0, BigDecimal.ROUND_HALF_UP);
						}
					}else{
						if(item.getQqsalenum().compareTo(BigDecimal.ZERO)>0){
							saledate=item.getTotalstoragenum().multiply(new BigDecimal(item.getSaleday())).divide(item.getQqsalenum(), 0, BigDecimal.ROUND_HALF_UP);
						}
					}
					item.setCansaleday(saledate.intValue());
				}
				if(item.getBoxamount().compareTo(BigDecimal.ZERO)>0){
					item.setExistingamount(item.getExistingnum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					item.setTransitamount(item.getTransitnum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					item.setCurstorageamount(item.getCurstoragenum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					item.setTotalstorageamount(item.getTotalstoragenum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					
					item.setCanorderamount(item.getCanordernum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					
					
					
					if(item.getOrdernum().compareTo(BigDecimal.ZERO)>=0){
						item.setOrderamount(item.getOrdernum().multiply(item.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));						
					}					
				}
				
				//按品牌合并数据
				if(brandMap.isEmpty() || !brandMap.containsKey(item.getBrandid())){
					List<PlannedOrderAnalysis> list2 = new ArrayList<PlannedOrderAnalysis>();
					list2.add(item);
					brandMap.put(item.getBrandid(), list2);
				}else{
					List<PlannedOrderAnalysis> list2 = (List<PlannedOrderAnalysis>)brandMap.get(item.getBrandid());
					list2.add(item);
					brandMap.put(item.getBrandid(), list2);
				}
			}
		}
		
		List<PlannedOrderAnalysis> brandSumList = new ArrayList<PlannedOrderAnalysis>();
		List<PlannedOrderAnalysis> footList = new ArrayList<PlannedOrderAnalysis>();
		if(!brandMap.isEmpty()){
			String auxunitname = "";
			BigDecimal existingunitnumSum = BigDecimal.ZERO;
			BigDecimal existingnumSum = BigDecimal.ZERO;
			BigDecimal existingamountSum = BigDecimal.ZERO;
			BigDecimal transitnumSum = BigDecimal.ZERO;
			BigDecimal transitamountSum = BigDecimal.ZERO;
			BigDecimal ordernumSum = BigDecimal.ZERO;
			BigDecimal orderamountSum = BigDecimal.ZERO;
			BigDecimal curstoragenumSum = BigDecimal.ZERO;
			BigDecimal curstorageamountSum = BigDecimal.ZERO;
			BigDecimal totalstoragenumSum = BigDecimal.ZERO;
			BigDecimal totalstorageamountSum = BigDecimal.ZERO;
			PlannedOrderAnalysis pAnalysisTotalSum = new PlannedOrderAnalysis();
			pAnalysisTotalSum.setBrandname("合计");
			for (Map.Entry<String, List<PlannedOrderAnalysis>> entry : brandMap.entrySet()) {
				String brandid = entry.getKey();
				List<PlannedOrderAnalysis> brandList = entry.getValue();
				PlannedOrderAnalysis pAnalysisSum = new PlannedOrderAnalysis();
				BigDecimal existingunitnum = BigDecimal.ZERO;
				BigDecimal existingnum = BigDecimal.ZERO;
				BigDecimal existingamount = BigDecimal.ZERO;
				BigDecimal transitnum = BigDecimal.ZERO;
				BigDecimal transitamount = BigDecimal.ZERO;
				BigDecimal ordernum = BigDecimal.ZERO;
				BigDecimal orderamount = BigDecimal.ZERO;
				BigDecimal curstoragenum = BigDecimal.ZERO;
				BigDecimal curstorageamount = BigDecimal.ZERO;
				BigDecimal totalstoragenum = BigDecimal.ZERO;
				BigDecimal totalstorageamount = BigDecimal.ZERO;
				pAnalysisSum.setBrandid(brandid);
				Brand brand = getGoodsBrandByID(brandid);
				if(null != brand){
					pAnalysisSum.setBrandname(brand.getName());
				}else{
					pAnalysisSum.setBrandname("未指定品牌");
				}
				auxunitname = (null != brandList.get(0).getAuxunitname()) ? brandList.get(0).getAuxunitname() : "";
				for(PlannedOrderAnalysis bAnalysis : brandList){
					existingunitnum = existingunitnum.add((null != bAnalysis.getExistingunitnum()) ? bAnalysis.getExistingunitnum() : BigDecimal.ZERO);
					existingnum = existingnum.add((null != bAnalysis.getExistingnum()) ? bAnalysis.getExistingnum() : BigDecimal.ZERO);
					existingamount = existingamount.add((null != bAnalysis.getExistingamount()) ? bAnalysis.getExistingamount() : BigDecimal.ZERO);
					transitnum = transitnum.add((null != bAnalysis.getTransitnum()) ? bAnalysis.getTransitnum() : BigDecimal.ZERO);
					transitamount = transitamount.add((null != bAnalysis.getTransitamount()) ? bAnalysis.getTransitamount() : BigDecimal.ZERO);
					ordernum = ordernum.add((null != bAnalysis.getOrdernum()) ? bAnalysis.getOrdernum() : BigDecimal.ZERO);
					orderamount = orderamount.add((null != bAnalysis.getOrderamount()) ? bAnalysis.getOrderamount() : BigDecimal.ZERO);
					curstoragenum = curstoragenum.add((null != bAnalysis.getCurstoragenum()) ? bAnalysis.getCurstoragenum() : BigDecimal.ZERO);
					curstorageamount = curstorageamount.add((null != bAnalysis.getCurstorageamount()) ? bAnalysis.getCurstorageamount() : BigDecimal.ZERO);
					totalstoragenum = totalstoragenum.add((null != bAnalysis.getTotalstoragenum()) ? bAnalysis.getTotalstoragenum() : BigDecimal.ZERO);
					totalstorageamount = totalstorageamount.add((null != bAnalysis.getTotalstorageamount()) ? bAnalysis.getTotalstorageamount() : BigDecimal.ZERO);
				
					//合计
					existingunitnumSum = existingunitnumSum.add((null != bAnalysis.getExistingunitnum()) ? bAnalysis.getExistingunitnum() : BigDecimal.ZERO);
					existingnumSum = existingnumSum.add((null != bAnalysis.getExistingnum()) ? bAnalysis.getExistingnum() : BigDecimal.ZERO);
					existingamountSum = existingamountSum.add((null != bAnalysis.getExistingamount()) ? bAnalysis.getExistingamount() : BigDecimal.ZERO);
					transitnumSum = transitnumSum.add((null != bAnalysis.getTransitnum()) ? bAnalysis.getTransitnum() : BigDecimal.ZERO);
					transitamountSum = transitamountSum.add((null != bAnalysis.getTransitamount()) ? bAnalysis.getTransitamount() : BigDecimal.ZERO);
					ordernumSum = ordernumSum.add((null != bAnalysis.getOrdernum()) ? bAnalysis.getOrdernum() : BigDecimal.ZERO);
					orderamountSum = orderamountSum.add((null != bAnalysis.getOrderamount()) ? bAnalysis.getOrderamount() : BigDecimal.ZERO);
					curstoragenumSum = curstoragenumSum.add((null != bAnalysis.getCurstoragenum()) ? bAnalysis.getCurstoragenum() : BigDecimal.ZERO);
					curstorageamountSum = curstorageamountSum.add((null != bAnalysis.getCurstorageamount()) ? bAnalysis.getCurstorageamount() : BigDecimal.ZERO);
					totalstoragenumSum = totalstoragenumSum.add((null != bAnalysis.getTotalstoragenum()) ? bAnalysis.getTotalstoragenum() : BigDecimal.ZERO);
					totalstorageamountSum = totalstorageamountSum.add((null != bAnalysis.getTotalstorageamount()) ? bAnalysis.getTotalstorageamount() : BigDecimal.ZERO);
				}
				pAnalysisSum.setAuxunitname(auxunitname);
				pAnalysisSum.setExistingunitnum(existingunitnum);
				pAnalysisSum.setExistingnum(existingnum);
				pAnalysisSum.setExistingamount(existingamount);
				pAnalysisSum.setTransitnum(transitnum);
				pAnalysisSum.setTransitamount(transitamount);
				pAnalysisSum.setOrdernum(ordernum);
				pAnalysisSum.setOrderamount(orderamount);
				pAnalysisSum.setCurstoragenum(curstoragenum);
				pAnalysisSum.setCurstorageamount(curstorageamount);
				pAnalysisSum.setTotalstoragenum(totalstoragenum);
				pAnalysisSum.setTotalstorageamount(totalstorageamount);
				if(null != pageMap.getCondition().get("isflag")){
					pAnalysisSum.setExistingnumdetail(existingnum+auxunitname);
					pAnalysisSum.setTransitnumdetail(transitnum+auxunitname);
					pAnalysisSum.setOrdernumdetail(ordernum+auxunitname);
					pAnalysisSum.setCurstoragenumdetail(curstoragenum+auxunitname);
					pAnalysisSum.setTotalstoragenumdetail(totalstoragenum+auxunitname);
				}
				brandSumList.add(pAnalysisSum);
			}
			pAnalysisTotalSum.setAuxunitname(auxunitname);
			pAnalysisTotalSum.setExistingunitnum(existingunitnumSum);
			pAnalysisTotalSum.setExistingnum(existingnumSum);
			pAnalysisTotalSum.setExistingamount(existingamountSum);
			pAnalysisTotalSum.setTransitnum(transitnumSum);
			pAnalysisTotalSum.setTransitamount(transitamountSum);
			pAnalysisTotalSum.setOrdernum(ordernumSum);
			pAnalysisTotalSum.setOrderamount(orderamountSum);
			pAnalysisTotalSum.setCurstoragenum(curstoragenumSum);
			pAnalysisTotalSum.setCurstorageamount(curstorageamountSum);
			pAnalysisTotalSum.setTotalstoragenum(totalstoragenumSum);
			pAnalysisTotalSum.setTotalstorageamount(totalstorageamountSum);
			if(null != pageMap.getCondition().get("isflag")){
				pAnalysisTotalSum.setExistingnumdetail(existingnumSum+auxunitname);
				pAnalysisTotalSum.setTransitnumdetail(transitnumSum+auxunitname);
				pAnalysisTotalSum.setOrdernumdetail(ordernumSum+auxunitname);
				pAnalysisTotalSum.setCurstoragenumdetail(curstoragenumSum+auxunitname);
				pAnalysisTotalSum.setTotalstoragenumdetail(totalstoragenumSum+auxunitname);
			}
			footList.add(pAnalysisTotalSum);
		}
		PageData pageData = new PageData(1,brandSumList,pageMap);
		if(footList.size() != 0){
			pageData.setFooter(footList);
		}
		return pageData;
	}
	

	@Override
	public PageData showPlannedOrderAnalysisPageDataInBuyOrder(PageMap pageMap) throws Exception{
		Map<String,Object> condition=pageMap.getCondition();
		String orderDetail=(String)condition.get("orderDetailParam");
		String orderStatus = (String)condition.get("orderstatus");
		String showInAuthRule=(String)condition.get("showInAuthRule");
		String doexportdata="";
		if(condition.containsKey("doexportdata")){
			doexportdata=(String)condition.get("doexportdata");
		}
		List<BuyOrderDetail> orderDetailList=null;
		if(null!=orderDetail && !"".equals(orderDetail.trim())){
			orderDetailList=JSONUtils.jsonStrToList(orderDetail.trim(),new BuyOrderDetail());
		}
		if(null==orderDetailList){
			orderDetailList=new ArrayList<BuyOrderDetail>();
		}
		//对来源单据进行排序，下面合计的时候，可以轻松计算
		Collections.sort(orderDetailList,ListSortLikeSQLComparator.createComparator("goodsid asc"));
				
		PlannedOrderAnalysis summaryAnalysis=new PlannedOrderAnalysis();

		String referDateBy="";

		if("true".equals(showInAuthRule)){
			String dataSql = getDataAccessRule("t_report_purchase_base", "t");
			pageMap.setDataSql(dataSql);
		}
		condition.put("isflag", "true");
		
		String sort=(String)condition.get("sort");
		if(null==sort){
			sort="";
		}
		String order=(String)condition.get("order");
		if(null==order){
			order="";
		}
		String ordersort="";
		if( ("".equals(sort.trim()) ||
		    "".equals(order.trim())) && StringUtils.isEmpty(pageMap.getOrderSql()) ){
			pageMap.setOrderSql(" goodsid asc ");
			ordersort="isgoodsinbill desc,goodsid asc";
		}else{
			if(!"".equals(sort.trim()) && !"".equals(order.trim())){
				ordersort=sort.trim()+" "+order.trim();
			}else if(StringUtils.isNotEmpty(pageMap.getOrderSql())){
				ordersort=pageMap.getOrderSql();
				
				pageMap.setOrderSql(null);
			}
		}
		if(condition.containsKey("sort")){
			condition.remove("sort");
		}
		if(condition.containsKey("order")){
			condition.remove("order");
		}
		
		//查询一段时间内的数据
		List<PlannedOrderAnalysis> analysislist=purchaseReportMapper.showPlannedOrderAnalysisPageList(pageMap);
		if(null==analysislist){
			analysislist=new ArrayList<PlannedOrderAnalysis>();
		}
		
		String sdateStr="";
		String edateStr="";
		if(null!=condition){
			if(null!=condition.get("referDateBy")){
				referDateBy=(String)condition.get("referDateBy");
			}

			if(null!=referDateBy && "1".equals(referDateBy.trim())){
				referDateBy="1";
			}
			if("1".equals(referDateBy)){
				if(null!=condition.get("tqstartdate")){
					sdateStr=(String)condition.get("tqstartdate");
				}
				if(null!=condition.get("tqenddate")){
					edateStr=(String)condition.get("tqenddate");
				}				
			}else{
				if(null!=condition.get("qqstartdate")){
					sdateStr=(String)condition.get("qqstartdate");
				}
				if(null!=condition.get("qqenddate")){
					edateStr=(String)condition.get("qqenddate");
				}				
			}
		}
		int saleday=getSalesDays(sdateStr,edateStr);
		
		//转换采购计划分析数据
		for(PlannedOrderAnalysis plannedOrderAnalysis : analysislist){

			plannedOrderAnalysis.setSaleday(saleday);
			plannedOrderAnalysis.setBoxnum(BigDecimal.ZERO);
			plannedOrderAnalysis.setBoxamount(BigDecimal.ZERO);
			plannedOrderAnalysis.setOrderamount(BigDecimal.ZERO);
			plannedOrderAnalysis.setOrdernum(BigDecimal.ZERO);
			plannedOrderAnalysis.setOrderunitnum(BigDecimal.ZERO);

			if(null==plannedOrderAnalysis.getExistingunitnum()){
				plannedOrderAnalysis.setExistingunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getTransitunitnum()){
				plannedOrderAnalysis.setTransitunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getCurstorageunitnum()){
				plannedOrderAnalysis.setCurstorageunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getTqsaleunitnum()){
				plannedOrderAnalysis.setTqsaleunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getQqsaleunitnum()){
				plannedOrderAnalysis.setQqsaleunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getCanorderunitnum()){
				plannedOrderAnalysis.setCanorderunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getCanorderamount()){
				plannedOrderAnalysis.setCanorderamount(BigDecimal.ZERO);
			}
			
			if(StringUtils.isEmpty(plannedOrderAnalysis.getGoodsid())){
				continue;
			}
			GoodsInfo goodsInfo = getAllGoodsInfoByID(plannedOrderAnalysis.getGoodsid());
			if(null==goodsInfo){
				continue;
			}
			
			plannedOrderAnalysis.setGoodsname(goodsInfo.getName());
			//品牌
			if(StringUtils.isNotEmpty(goodsInfo.getBrand())){
				plannedOrderAnalysis.setBrandid(goodsInfo.getBrand());
			}
			if(StringUtils.isNotEmpty(goodsInfo.getBrandName())){
				plannedOrderAnalysis.setBrandname(goodsInfo.getBrandName());
			}
			plannedOrderAnalysis.setGoodsInfo(goodsInfo);
			//主单位编码
			if(StringUtils.isNotEmpty(goodsInfo.getMainunit())){
				plannedOrderAnalysis.setUnitid(goodsInfo.getMainunit());
			}
			//主单位
			if(StringUtils.isNotEmpty(goodsInfo.getMainunitName())){
				plannedOrderAnalysis.setUnitname(goodsInfo.getMainunitName());
			}
			//辅单位编码
			if(StringUtils.isNotEmpty(goodsInfo.getAuxunitid())){
				plannedOrderAnalysis.setAuxunitid(goodsInfo.getAuxunitid());
			}
			//辅单位
			if(StringUtils.isNotEmpty(goodsInfo.getAuxunitname())){
				plannedOrderAnalysis.setAuxunitname(goodsInfo.getAuxunitname());
			}
			if( null!=goodsInfo.getNewbuyprice()){
				plannedOrderAnalysis.setBuyprice(goodsInfo.getNewbuyprice());						
			} else{
				plannedOrderAnalysis.setBuyprice(BigDecimal.ZERO);
			}
			if(null!=goodsInfo.getBoxnum()){
				plannedOrderAnalysis.setBoxnum(goodsInfo.getBoxnum());
				plannedOrderAnalysis.setBoxamount(goodsInfo.getBoxnum().multiply(plannedOrderAnalysis.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			
			summaryAnalysis.setAuxunitname(plannedOrderAnalysis.getAuxunitname());
			

			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) { // 默认分类（来源商品分类）
				WaresClass waresClass =   getWaresClassByID(goodsInfo.getDefaultsort());
				if (waresClass != null) {
					plannedOrderAnalysis.setGoodssortname(waresClass.getThisname());
				}
				plannedOrderAnalysis.setGoodssort(goodsInfo.getDefaultsort());
			}
			
			
			BigDecimal curOrderunitnum=BigDecimal.ZERO;//当前商品编号采购主数量
			BigDecimal curOrdernum=BigDecimal.ZERO;//当前商品编号采购辅数量
			
			if(null==plannedOrderAnalysis.getOrderunitnum()){
				plannedOrderAnalysis.setOrderunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getOrderamount()){
				plannedOrderAnalysis.setOrderamount(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getOrdernum()){
				plannedOrderAnalysis.setOrdernum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getOrderauxremainder()){
				plannedOrderAnalysis.setOrderauxremainder(BigDecimal.ZERO);
			}
			
			//商品编码是否在订单内
			boolean isInBill=false;
			// 按商品编码从小到大排序的orderDetailList， 当订单下一条记录不在匹配时，当时再次匹配，尝试3次
			int pointOutTimes=-3;
			for(BuyOrderDetail buyOrderDetail:orderDetailList){
				plannedOrderAnalysis.setIsgoodsinbill(0);				
				if(plannedOrderAnalysis.getGoodsid().equals(buyOrderDetail.getGoodsid())){
					isInBill=true;
					
					plannedOrderAnalysis.setIsgoodsinbill(1);
					
					if(null!=buyOrderDetail.getTaxprice()){
						plannedOrderAnalysis.setBuyprice(buyOrderDetail.getTaxprice());						
					}
					
					plannedOrderAnalysis.setBuyprice(plannedOrderAnalysis.getBuyprice().setScale(6,BigDecimal.ROUND_HALF_UP));
					
	
					if(null!=buyOrderDetail.getUnitnum()){
						plannedOrderAnalysis.setOrderunitnum(plannedOrderAnalysis.getOrderunitnum().add(buyOrderDetail.getUnitnum()));
						
						//本次采购主数量
						curOrderunitnum=curOrderunitnum.add(buyOrderDetail.getUnitnum());
					}
					if(null!=buyOrderDetail.getTaxamount()){
						plannedOrderAnalysis.setOrderamount(plannedOrderAnalysis.getOrderamount().add(buyOrderDetail.getTaxamount()));
					}

					if(null!=buyOrderDetail.getAuxremainder()){
						plannedOrderAnalysis.setOrderauxremainder(plannedOrderAnalysis.getOrderauxremainder().add(buyOrderDetail.getAuxremainder())); //当作可订量余数来算
					}
					
					if(null!=plannedOrderAnalysis.getBoxnum()){
						plannedOrderAnalysis.setBoxamount(plannedOrderAnalysis.getBoxnum().multiply(plannedOrderAnalysis.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					}					
				}else{
					if(isInBill){
						if(pointOutTimes==0){
							break;
						}else{
							pointOutTimes=pointOutTimes+1;
						}
					}
				}
			}
			
			if(plannedOrderAnalysis.getBoxnum().compareTo(BigDecimal.ZERO)>0){
				plannedOrderAnalysis.setExistingnum(plannedOrderAnalysis.getExistingunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setTransitnum(plannedOrderAnalysis.getTransitunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setCurstoragenum(plannedOrderAnalysis.getCurstorageunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				
				plannedOrderAnalysis.setCanordernum(plannedOrderAnalysis.getCanorderunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				//可订量辅数量=当前采购主数量/箱装量
				curOrdernum=curOrderunitnum.divide(plannedOrderAnalysis.getBoxnum(), 6, BigDecimal.ROUND_HALF_UP);
				plannedOrderAnalysis.setOrdernum(plannedOrderAnalysis.getOrdernum().add(curOrdernum));
				//前期销售数量
				plannedOrderAnalysis.setTqsalenum(plannedOrderAnalysis.getTqsaleunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				//前期销售金额
				plannedOrderAnalysis.setTqsaleamount(plannedOrderAnalysis.getTqsaleunitnum().multiply(plannedOrderAnalysis.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				//同期销售数量
				plannedOrderAnalysis.setQqsalenum(plannedOrderAnalysis.getQqsaleunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				//同期销售金额
				plannedOrderAnalysis.setQqsaleamount(plannedOrderAnalysis.getQqsaleunitnum().multiply(plannedOrderAnalysis.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				if(plannedOrderAnalysis.getCanordernum().compareTo(BigDecimal.ZERO)>0){
					plannedOrderAnalysis.setCanordernum(plannedOrderAnalysis.getCanordernum().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));	//可订量，保持整数位					
				}
				plannedOrderAnalysis.setTotalstoragenum(plannedOrderAnalysis.getCurstoragenum());
				plannedOrderAnalysis.setTotalstorageunitnum(plannedOrderAnalysis.getCurstorageunitnum());
				if(!"4".equals(orderStatus) && !"3".equals(orderStatus)){
					plannedOrderAnalysis.setTotalstoragenum(plannedOrderAnalysis.getTotalstoragenum().add(curOrdernum));
					plannedOrderAnalysis.setTotalstorageunitnum(plannedOrderAnalysis.getTotalstorageunitnum().add(curOrderunitnum));
				}
			}
			if(plannedOrderAnalysis.getBoxamount().compareTo(BigDecimal.ZERO)>0){
				plannedOrderAnalysis.setExistingamount(plannedOrderAnalysis.getExistingnum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setTransitamount(plannedOrderAnalysis.getTransitnum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setCurstorageamount(plannedOrderAnalysis.getCurstoragenum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setTotalstorageamount(plannedOrderAnalysis.getTotalstoragenum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));					
				plannedOrderAnalysis.setCanorderamount(plannedOrderAnalysis.getCanordernum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));					
									
			}else{
				plannedOrderAnalysis.setExistingamount(BigDecimal.ZERO);
				plannedOrderAnalysis.setTransitamount(BigDecimal.ZERO);
				plannedOrderAnalysis.setCurstorageamount(BigDecimal.ZERO);
				plannedOrderAnalysis.setTotalstorageamount(BigDecimal.ZERO);
				plannedOrderAnalysis.setCanorderamount(BigDecimal.ZERO);
			}
			//plannedOrderAnalysis.setOrderamount(plannedOrderAnalysis.getOrderamount().add(curOrdernum.multiply(plannedOrderAnalysis.getBoxamount())).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			
			
			
			//合计
			if(null!=plannedOrderAnalysis.getCanordernum() 
					&& plannedOrderAnalysis.getCanordernum().compareTo(BigDecimal.ZERO)>0){
				if(null!=summaryAnalysis.getCanordernum()){
					summaryAnalysis.setCanordernum(summaryAnalysis.getCanordernum().add(plannedOrderAnalysis.getCanordernum()));
				}else{
					summaryAnalysis.setCanordernum(plannedOrderAnalysis.getCanordernum());				
				}
			}
			if(null!=summaryAnalysis.getOrdernum()){
				summaryAnalysis.setOrdernum(summaryAnalysis.getOrdernum().add(plannedOrderAnalysis.getOrdernum()));
			}else{
				summaryAnalysis.setOrdernum(plannedOrderAnalysis.getOrdernum());
			}
			if(null!=summaryAnalysis.getOrderamount()){
				summaryAnalysis.setOrderamount(summaryAnalysis.getOrderamount().add(plannedOrderAnalysis.getOrderamount()));
			}else{
				summaryAnalysis.setOrderamount(plannedOrderAnalysis.getOrderamount());
			}
			if(null!=summaryAnalysis.getExistingnum()){
				summaryAnalysis.setExistingnum(summaryAnalysis.getExistingnum().add(plannedOrderAnalysis.getExistingnum()));
			}else{
				summaryAnalysis.setExistingnum(plannedOrderAnalysis.getExistingnum());
			}
			if(null!=summaryAnalysis.getExistingamount()){
				summaryAnalysis.setExistingamount(summaryAnalysis.getExistingamount().add(plannedOrderAnalysis.getExistingamount()));
			}else{
				summaryAnalysis.setExistingamount(plannedOrderAnalysis.getExistingamount());
			}
			if(null!=summaryAnalysis.getTransitnum()){
				summaryAnalysis.setTransitnum(summaryAnalysis.getTransitnum().add(plannedOrderAnalysis.getTransitnum()));
			}else{
				summaryAnalysis.setTransitnum(plannedOrderAnalysis.getTransitnum());
			}
			if(null!=summaryAnalysis.getTransitamount()){
				summaryAnalysis.setTransitamount(summaryAnalysis.getTransitamount().add(plannedOrderAnalysis.getTransitamount()));
			}else{
				summaryAnalysis.setTransitamount(plannedOrderAnalysis.getTransitamount());
			}
			if(null!=summaryAnalysis.getCurstorageamount()){
				summaryAnalysis.setCurstorageamount(summaryAnalysis.getCurstorageamount().add(plannedOrderAnalysis.getCurstorageamount()));
			}else{
				summaryAnalysis.setCurstorageamount(plannedOrderAnalysis.getCurstorageamount());
			}
			if(null!=summaryAnalysis.getCurstoragenum()){
				summaryAnalysis.setCurstoragenum(summaryAnalysis.getCurstoragenum().add(plannedOrderAnalysis.getCurstoragenum()));
			}else{
				summaryAnalysis.setCurstoragenum(plannedOrderAnalysis.getCurstoragenum());
			}
			if(null!=summaryAnalysis.getTotalstoragenum()){
				summaryAnalysis.setTotalstoragenum(summaryAnalysis.getTotalstoragenum().add(plannedOrderAnalysis.getTotalstoragenum()));
			}else{
				summaryAnalysis.setTotalstoragenum(plannedOrderAnalysis.getTotalstoragenum());
			}
			if(null!=summaryAnalysis.getTotalstorageamount()){
				summaryAnalysis.setTotalstorageamount(summaryAnalysis.getTotalstorageamount().add(plannedOrderAnalysis.getTotalstorageamount()));
			}else{
				summaryAnalysis.setTotalstorageamount(plannedOrderAnalysis.getTotalstorageamount());
			}
			if(null!=summaryAnalysis.getTqsalenum()){
				summaryAnalysis.setTqsalenum(summaryAnalysis.getTqsalenum().add(plannedOrderAnalysis.getTqsalenum()));
			}else{
				summaryAnalysis.setTqsalenum(plannedOrderAnalysis.getTqsalenum());
			}
			if(null!=summaryAnalysis.getTqsaleamount()){
				summaryAnalysis.setTqsaleamount(summaryAnalysis.getTqsaleamount().add(plannedOrderAnalysis.getTqsaleamount()));
			}else{
				summaryAnalysis.setTqsaleamount(plannedOrderAnalysis.getTqsaleamount());
			}
			if(null!=summaryAnalysis.getQqsalenum()){
				summaryAnalysis.setQqsalenum(summaryAnalysis.getQqsalenum().add(plannedOrderAnalysis.getQqsalenum()));
			}else{
				summaryAnalysis.setQqsalenum(plannedOrderAnalysis.getQqsalenum());
			}
			if(null!=summaryAnalysis.getQqsaleamount()){
				summaryAnalysis.setQqsaleamount(summaryAnalysis.getQqsaleamount().add(plannedOrderAnalysis.getQqsaleamount()));
			}else{
				summaryAnalysis.setQqsaleamount(plannedOrderAnalysis.getQqsaleamount());
			}

			if(null!=summaryAnalysis.getExistingunitnum()){
				summaryAnalysis.setExistingunitnum(summaryAnalysis.getExistingunitnum().add(plannedOrderAnalysis.getExistingunitnum()));
			}else{
				summaryAnalysis.setExistingunitnum(plannedOrderAnalysis.getExistingunitnum());
			}
			BigDecimal saledate=BigDecimal.ZERO;
			if("1".equals(referDateBy)){
				if(plannedOrderAnalysis.getTqsalenum().compareTo(BigDecimal.ZERO)>0){					
					saledate=plannedOrderAnalysis.getTotalstoragenum().multiply(new BigDecimal(plannedOrderAnalysis.getSaleday())).divide(plannedOrderAnalysis.getTqsalenum(), 0, BigDecimal.ROUND_HALF_UP);
				}
			}else{
				if(plannedOrderAnalysis.getQqsalenum().compareTo(BigDecimal.ZERO)>0){	
					saledate=plannedOrderAnalysis.getTotalstoragenum().multiply(new BigDecimal(plannedOrderAnalysis.getSaleday())).divide(plannedOrderAnalysis.getQqsalenum(), 0, BigDecimal.ROUND_HALF_UP);
				}
			}
			plannedOrderAnalysis.setCansaleday(saledate.intValue());
			
			summaryAnalysis.setCansaleday(null);
			
			if("true".equals(doexportdata)){
				if(null==plannedOrderAnalysis.getCanordernum() || BigDecimal.ZERO.compareTo(plannedOrderAnalysis.getCanordernum())>0){
					plannedOrderAnalysis.setCanordernum(BigDecimal.ZERO);
				}
				if(null==summaryAnalysis.getCanordernum() || BigDecimal.ZERO.compareTo(summaryAnalysis.getCanordernum())>0){
					summaryAnalysis.setCanordernum(BigDecimal.ZERO);
				}
			}
			
		}
		
		Collections.sort(analysislist,ListSortLikeSQLComparator.createComparator(ordersort));
		
		PageData pageData=new PageData(1, analysislist, pageMap);
		
		List<PlannedOrderAnalysis> brandFooterList=new ArrayList<PlannedOrderAnalysis>();
		summaryAnalysis.setGoodsid("合计");
		brandFooterList.add(summaryAnalysis);
		pageData.setFooter(brandFooterList);
		return pageData;
	}
	@Override
	public PageData showStorageSummaryByBrandInBuyOrder(PageMap pageMap) throws Exception{
		Map<String,Object> condition=pageMap.getCondition();
		String orderDetail=(String)condition.get("orderDetailParam");
		String orderStatus = (String)condition.get("orderstatus");
		String showInAuthRule=(String)condition.get("showInAuthRule");
		String doexportdata="";
		if(condition.containsKey("doexportdata")){
			doexportdata=(String)condition.get("doexportdata");
		}
		List<BuyOrderDetail> orderDetailList=null;
		if(null!=orderDetail && !"".equals(orderDetail.trim())){
			orderDetailList=JSONUtils.jsonStrToList(orderDetail.trim(),new BuyOrderDetail());
		}
		if(null==orderDetailList){
			orderDetailList=new ArrayList<BuyOrderDetail>();
		}
		//对来源单据进行排序，下面合计的时候，可以轻松计算
		Collections.sort(orderDetailList,ListSortLikeSQLComparator.createComparator("goodsid asc"));
		

		Map<String, String> brandIdUniqueMap=new HashMap<String, String>();
		List<String> brandIdList=new ArrayList<String>();
		Map<String, Object> brandMap=new HashMap<String, Object>();
		
		if("true".equals(showInAuthRule)){
			String dataSql = getDataAccessRule("t_report_purchase_base", "t");
			pageMap.setDataSql(dataSql);
		}
		condition.put("isflag", "true");
		//查询一段时间内的数据
		List<PlannedOrderAnalysis> analysislist=purchaseReportMapper.showPlannedOrderAnalysisPageList(pageMap);
		if(null==analysislist){
			analysislist=new ArrayList<PlannedOrderAnalysis>();
		}
		String referDateBy="";
		String sdateStr="";
		String edateStr="";
		if(null!=condition){
			if(null!=condition.get("referDateBy")){
				referDateBy=(String)condition.get("referDateBy");
			}

			if(null!=referDateBy && "1".equals(referDateBy.trim())){
				referDateBy="1";
			}
			if("1".equals(referDateBy)){
				if(null!=condition.get("tqstartdate")){
					sdateStr=(String)condition.get("tqstartdate");
				}
				if(null!=condition.get("tqenddate")){
					edateStr=(String)condition.get("tqenddate");
				}				
			}else{
				if(null!=condition.get("qqstartdate")){
					sdateStr=(String)condition.get("qqstartdate");
				}
				if(null!=condition.get("qqenddate")){
					edateStr=(String)condition.get("qqenddate");
				}				
			}
		}
		int saleday=getSalesDays(sdateStr,edateStr);
		

	//转换采购计划分析数据
		for(PlannedOrderAnalysis plannedOrderAnalysis : analysislist){
			plannedOrderAnalysis.setSaleday(saleday);
			plannedOrderAnalysis.setBoxnum(BigDecimal.ZERO);
			plannedOrderAnalysis.setBoxamount(BigDecimal.ZERO);
			plannedOrderAnalysis.setOrderamount(BigDecimal.ZERO);
			plannedOrderAnalysis.setOrdernum(BigDecimal.ZERO);
			plannedOrderAnalysis.setOrderunitnum(BigDecimal.ZERO);
			plannedOrderAnalysis.setOrderauxremainder(BigDecimal.ZERO);

			if(null==plannedOrderAnalysis.getExistingunitnum()){
				plannedOrderAnalysis.setExistingunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getTransitunitnum()){
				plannedOrderAnalysis.setTransitunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getCurstorageunitnum()){
				plannedOrderAnalysis.setCurstorageunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getTqsaleunitnum()){
				plannedOrderAnalysis.setTqsaleunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getQqsaleunitnum()){
				plannedOrderAnalysis.setQqsaleunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getCanorderunitnum()){
				plannedOrderAnalysis.setCanorderunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getCanorderamount()){
				plannedOrderAnalysis.setCanorderamount(BigDecimal.ZERO);
			}
			if(StringUtils.isEmpty(plannedOrderAnalysis.getGoodsid())){
				continue;
			}
			GoodsInfo goodsInfo = getAllGoodsInfoByID(plannedOrderAnalysis.getGoodsid());
			if(null==goodsInfo){
				continue;
			}
			
			plannedOrderAnalysis.setGoodsname(goodsInfo.getName());
			//品牌
			if(StringUtils.isNotEmpty(goodsInfo.getBrand())){
				plannedOrderAnalysis.setBrandid(goodsInfo.getBrand());
			}
			if(StringUtils.isNotEmpty(goodsInfo.getBrandName())){
				plannedOrderAnalysis.setBrandname(goodsInfo.getBrandName());
			}
			plannedOrderAnalysis.setGoodsInfo(goodsInfo);
			//主单位编码
			if(StringUtils.isNotEmpty(goodsInfo.getMainunit())){
				plannedOrderAnalysis.setUnitid(goodsInfo.getMainunit());
			}
			//主单位
			if(StringUtils.isNotEmpty(goodsInfo.getMainunitName())){
				plannedOrderAnalysis.setUnitname(goodsInfo.getMainunitName());
			}
			//辅单位编码
			if(StringUtils.isNotEmpty(goodsInfo.getAuxunitid())){
				plannedOrderAnalysis.setAuxunitid(goodsInfo.getAuxunitid());
			}
			//辅单位
			if(StringUtils.isNotEmpty(goodsInfo.getAuxunitname())){
				plannedOrderAnalysis.setAuxunitname(goodsInfo.getAuxunitname());
			}			

			if( null!=goodsInfo.getNewbuyprice()){
				plannedOrderAnalysis.setBuyprice(goodsInfo.getNewbuyprice());						
			} else{
				plannedOrderAnalysis.setBuyprice(BigDecimal.ZERO);
			}
			if(null!=goodsInfo.getBoxnum()){
				plannedOrderAnalysis.setBoxnum(goodsInfo.getBoxnum());
				plannedOrderAnalysis.setBoxamount(goodsInfo.getBoxnum().multiply(plannedOrderAnalysis.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			}
			
			BigDecimal curOrderunitnum=BigDecimal.ZERO;//当前商品编号采购主数量
			BigDecimal curOrdernum=BigDecimal.ZERO;//当前商品编号采购辅数量
			
			if(null==plannedOrderAnalysis.getOrderunitnum()){
				plannedOrderAnalysis.setOrderunitnum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getOrderamount()){
				plannedOrderAnalysis.setOrderamount(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getOrdernum()){
				plannedOrderAnalysis.setOrdernum(BigDecimal.ZERO);
			}
			if(null==plannedOrderAnalysis.getOrderauxremainder()){
				plannedOrderAnalysis.setOrderauxremainder(BigDecimal.ZERO);
			}
			//商品编码是否在订单内
			boolean isInBill=false;
			// 按商品编码从小到大排序的orderDetailList， 当订单下一条记录不在匹配时，当时再次匹配，尝试3次
			int pointOutTimes=-3;
			for(BuyOrderDetail buyOrderDetail:orderDetailList){
				plannedOrderAnalysis.setIsgoodsinbill(0);				
				if(plannedOrderAnalysis.getGoodsid().equals(buyOrderDetail.getGoodsid())){
					isInBill=true;
					
					plannedOrderAnalysis.setIsgoodsinbill(1);
					
					if(null!=buyOrderDetail.getTaxprice()){
						plannedOrderAnalysis.setBuyprice(buyOrderDetail.getTaxprice());						
					}
					plannedOrderAnalysis.setBuyprice(plannedOrderAnalysis.getBuyprice().setScale(6,BigDecimal.ROUND_HALF_UP));
					
	
					if(null!=buyOrderDetail.getUnitnum()){
						plannedOrderAnalysis.setOrderunitnum(plannedOrderAnalysis.getOrderunitnum().add(buyOrderDetail.getUnitnum()));
						
						//本次采购主数量
						curOrderunitnum=curOrderunitnum.add(buyOrderDetail.getUnitnum());
					}
					if(null!=buyOrderDetail.getTaxamount()){
						plannedOrderAnalysis.setOrderamount(plannedOrderAnalysis.getOrderamount().add(buyOrderDetail.getTaxamount()));
					}
					if(null!=buyOrderDetail.getAuxremainder()){
						plannedOrderAnalysis.setOrderauxremainder(plannedOrderAnalysis.getOrderauxremainder().add(buyOrderDetail.getAuxremainder())); //当作可订量余数来算
					}
					if(null!=plannedOrderAnalysis.getBoxnum()){
						plannedOrderAnalysis.setBoxamount(plannedOrderAnalysis.getBoxnum().multiply(plannedOrderAnalysis.getBuyprice()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
					}	
				}else{
					if(isInBill){
						if(pointOutTimes==0){
							break;
						}else{
							pointOutTimes=pointOutTimes+1;
						}
					}
				}
			}
			
			if(plannedOrderAnalysis.getBoxnum().compareTo(BigDecimal.ZERO)>0){
				plannedOrderAnalysis.setExistingnum(plannedOrderAnalysis.getExistingunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setTransitnum(plannedOrderAnalysis.getTransitunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setCurstoragenum(plannedOrderAnalysis.getCurstorageunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				
				plannedOrderAnalysis.setCanordernum(plannedOrderAnalysis.getCanorderunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				//可订量辅数量=当前采购主数量/箱装量
				curOrdernum=curOrderunitnum.divide(plannedOrderAnalysis.getBoxnum(), 6, BigDecimal.ROUND_HALF_UP);
				plannedOrderAnalysis.setOrdernum(plannedOrderAnalysis.getOrdernum().add(curOrdernum));
				
				plannedOrderAnalysis.setTqsalenum(plannedOrderAnalysis.getTqsaleunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setQqsalenum(plannedOrderAnalysis.getQqsaleunitnum().divide(plannedOrderAnalysis.getBoxnum(), decimalLen, BigDecimal.ROUND_HALF_UP));
				if(plannedOrderAnalysis.getCanordernum().compareTo(BigDecimal.ZERO)>0){
					plannedOrderAnalysis.setCanordernum(plannedOrderAnalysis.getCanordernum().setScale(decimalLen, BigDecimal.ROUND_HALF_UP));	//可订量，保持整数位					
				}
				plannedOrderAnalysis.setTotalstoragenum(plannedOrderAnalysis.getCurstoragenum());
				plannedOrderAnalysis.setTotalstorageunitnum(plannedOrderAnalysis.getCurstorageunitnum());
				if(!"4".equals(orderStatus) && !"3".equals(orderStatus)){
					plannedOrderAnalysis.setTotalstoragenum(plannedOrderAnalysis.getTotalstoragenum().add(curOrdernum));
					plannedOrderAnalysis.setTotalstorageunitnum(plannedOrderAnalysis.getTotalstorageunitnum().add(curOrderunitnum));
				}
			}
			if(plannedOrderAnalysis.getBoxamount().compareTo(BigDecimal.ZERO)>0){
				plannedOrderAnalysis.setExistingamount(plannedOrderAnalysis.getExistingnum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setTransitamount(plannedOrderAnalysis.getTransitnum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setCurstorageamount(plannedOrderAnalysis.getCurstoragenum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
				plannedOrderAnalysis.setTotalstorageamount(plannedOrderAnalysis.getTotalstoragenum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));					
				plannedOrderAnalysis.setCanorderamount(plannedOrderAnalysis.getCanordernum().multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));					
									
			}else{
				plannedOrderAnalysis.setExistingamount(BigDecimal.ZERO);
				plannedOrderAnalysis.setTransitamount(BigDecimal.ZERO);
				plannedOrderAnalysis.setCurstorageamount(BigDecimal.ZERO);
				plannedOrderAnalysis.setTotalstorageamount(BigDecimal.ZERO);
				plannedOrderAnalysis.setCanorderamount(BigDecimal.ZERO);
			}
			//plannedOrderAnalysis.setOrderamount(plannedOrderAnalysis.getOrderamount().add(curOrdernum.multiply(plannedOrderAnalysis.getBoxamount())).setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
			

			//按商品计算
			if(plannedOrderAnalysis.getBoxnum().compareTo(BigDecimal.ZERO)>0){
				curOrdernum=curOrderunitnum.divide(plannedOrderAnalysis.getBoxnum(), 6, BigDecimal.ROUND_HALF_UP);					
			}
			//plannedOrderAnalysis.setOrderamount(plannedOrderAnalysis.getOrderamount().add(curOrdernum.multiply(plannedOrderAnalysis.getBoxamount()).setScale(decimalLen,BigDecimal.ROUND_HALF_UP)));

			//合并到品牌，用于统计本次品牌采购
			if(StringUtils.isNotEmpty(goodsInfo.getBrand()) ){
				if( !brandIdUniqueMap.containsKey(goodsInfo.getBrand())){
					brandIdList.add(goodsInfo.getBrand());
					brandIdUniqueMap.put(goodsInfo.getBrand(), goodsInfo.getBrand());
				}
				
				if(brandMap.containsKey(goodsInfo.getBrand())){
					PlannedOrderAnalysis brandAnalysis=(PlannedOrderAnalysis)brandMap.get(goodsInfo.getBrand());
					brandAnalysis.setOrdernum(brandAnalysis.getOrdernum().add(plannedOrderAnalysis.getOrdernum()));
					brandAnalysis.setOrderunitnum(brandAnalysis.getOrderunitnum().add(plannedOrderAnalysis.getOrderunitnum()));
					brandAnalysis.setCanorderunitnum(brandAnalysis.getOrderunitnum().add(plannedOrderAnalysis.getOrderunitnum()));	//余数
					brandAnalysis.setOrderamount(brandAnalysis.getOrderamount().add(plannedOrderAnalysis.getOrderamount()));
				}else{
					PlannedOrderAnalysis brandAnalysis=new PlannedOrderAnalysis();
					brandAnalysis.setOrdernum(plannedOrderAnalysis.getOrdernum());
					brandAnalysis.setOrderunitnum(plannedOrderAnalysis.getOrderunitnum());
					brandAnalysis.setOrderauxremainder(plannedOrderAnalysis.getOrderauxremainder());
					brandAnalysis.setOrderamount(plannedOrderAnalysis.getOrderamount());
					brandMap.put(goodsInfo.getBrand(), brandAnalysis);
				}
			}
		}
		
		
		List<Map> brandFooterList=new ArrayList<Map>();
		String brandarrs="";
		if(brandIdList.size()>0){
			String[] brandArr= (String[])brandIdList.toArray(new String[brandIdList.size()]);
			brandarrs=StringUtils.join(brandArr, ",");
		}else{
			brandarrs="thereIsNoBrandSelect";	//	无品牌数据，变空
		}
		condition.put("isShowAuth", "false");
		condition.put("brandid", brandarrs);
		condition.put("groupcols", "brandid");
		condition.put("isflag", "showPageOrder");
		brandFooterList=storageReportService.showStorageAmountReportList(pageMap);
		BigDecimal totalstoragenum=BigDecimal.ZERO;
		BigDecimal totalstorageamount=BigDecimal.ZERO;
		BigDecimal totalstorageauxint=BigDecimal.ZERO;
		BigDecimal totalstorageauxnum=BigDecimal.ZERO;
		String brandid="";
		for(Map itemMap : brandFooterList){
			brandid=(String)itemMap.get("brandid");
			totalstoragenum=(BigDecimal)itemMap.get("totalstoragenum");
			totalstorageamount=(BigDecimal) itemMap.get("totalstorageamount");
			totalstorageauxint=(BigDecimal)itemMap.get("totalstorageauxint");
			totalstorageauxnum=(BigDecimal)itemMap.get("totalstorageauxnum");
			itemMap.put("curstoragenum", totalstoragenum);
			itemMap.put("curstorageamount", totalstorageamount);
			itemMap.put("curstorageauxint", totalstorageauxint);
			itemMap.put("curstorageauxnum", totalstorageauxnum);
			if(null!=brandid && !"".equals(brandid.trim()) && 
					brandMap.containsKey(brandid.trim())){
				PlannedOrderAnalysis brandAnalysis=(PlannedOrderAnalysis)brandMap.get(brandid.trim());
				if(null!=brandAnalysis){
					//如果当前订单里的商品，则需要进行合计
					if(!"3".equals(orderStatus) && !"4".equals(orderStatus) && brandAnalysis.getIsgoodsinbill()==1){
						totalstoragenum=totalstoragenum.add(brandAnalysis.getOrderunitnum());	//主单位数量
						totalstorageamount=totalstorageamount.add(brandAnalysis.getOrderamount());	//合计库存
						totalstorageauxint=totalstorageauxint.add(brandAnalysis.getOrdernum());//辅单位数量
						totalstorageauxnum=totalstorageauxnum.add(brandAnalysis.getOrderauxremainder());
						
					}

					itemMap.put("ordernum", brandAnalysis.getOrdernum());
					itemMap.put("orderauxremainder", brandAnalysis.getOrderauxremainder());
					itemMap.put("orderamount", brandAnalysis.getOrderamount());
					
				}
			}

			itemMap.put("totalstoragenum", totalstoragenum);
			itemMap.put("totalstorageamount", totalstorageamount);
			itemMap.put("totalstorageauxint", totalstorageauxint);
			itemMap.put("totalstorageauxnum", totalstorageauxnum);
		}
		PageData pageData=new PageData(1, brandFooterList, pageMap);
		pageData.setFooter(brandFooterList);
		return pageData;
	}
	
	@Override
	public List<Map<String,Object>> getExportStorageSummaryByBrandInBuyOrder(PageMap pageMap)
			throws Exception {
		List<Map<String,Object>> brandFooterResultList = new ArrayList<Map<String,Object>>();
		Map<String, String> brandIdUniqueMap=new HashMap<String, String>();
		List<String> brandIdList=new ArrayList<String>();
		Map<String, Object> brandMap=new HashMap<String, Object>();
		Map<String,Object> condition=pageMap.getCondition();
		String orderStatus = (String)condition.get("orderstatus");
		List<PlannedOrderAnalysis> analysislist=(List<PlannedOrderAnalysis>)condition.get("pOrderAnalysisList");
		if(analysislist==null){
			return brandFooterResultList;
		}

	//转换采购计划分析数据
		for(PlannedOrderAnalysis plannedOrderAnalysis : analysislist){

			GoodsInfo goodsInfo = getAllGoodsInfoByID(plannedOrderAnalysis.getGoodsid());
			if(null==goodsInfo){
				continue;
			}
			//合并到品牌，用于统计本次品牌采购
			if(StringUtils.isNotEmpty(goodsInfo.getBrand()) ){
				if( !brandIdUniqueMap.containsKey(goodsInfo.getBrand())){
					brandIdList.add(goodsInfo.getBrand());
					brandIdUniqueMap.put(goodsInfo.getBrand(), goodsInfo.getBrand());
				}
				
				if(brandMap.containsKey(goodsInfo.getBrand())){
					PlannedOrderAnalysis brandAnalysis=(PlannedOrderAnalysis)brandMap.get(goodsInfo.getBrand());
					brandAnalysis.setOrdernum(brandAnalysis.getOrdernum().add(plannedOrderAnalysis.getOrdernum()));
					brandAnalysis.setOrderunitnum(brandAnalysis.getOrderunitnum().add(plannedOrderAnalysis.getOrderunitnum()));
					brandAnalysis.setCanorderunitnum(brandAnalysis.getOrderunitnum().add(plannedOrderAnalysis.getOrderunitnum()));	//余数
					brandAnalysis.setOrderamount(brandAnalysis.getOrderamount().add(plannedOrderAnalysis.getOrderamount()));
				}else{
					PlannedOrderAnalysis brandAnalysis=new PlannedOrderAnalysis();
					brandAnalysis.setOrdernum(plannedOrderAnalysis.getOrdernum());
					brandAnalysis.setOrderunitnum(plannedOrderAnalysis.getOrderunitnum());
					brandAnalysis.setOrderauxremainder(plannedOrderAnalysis.getOrderauxremainder());
					brandAnalysis.setOrderamount(plannedOrderAnalysis.getOrderamount());
					brandMap.put(goodsInfo.getBrand(), brandAnalysis);
				}
			}
		}
		
		
		
		List<Map> brandFooterList=new ArrayList<Map>();
		String brandarrs="";
		if(brandIdList.size()>0){
			String[] brandArr= (String[])brandIdList.toArray(new String[brandIdList.size()]);
			brandarrs=StringUtils.join(brandArr, ",");
		}else{
			brandarrs="thereIsNoBrandSelect";	//	无品牌数据，变空
		}
		condition.put("isShowAuth", "false");
		condition.put("brandid", brandarrs);
		condition.put("groupcols", "brandid");
		condition.put("isflag", "showPageOrder");
		brandFooterList=storageReportService.showStorageAmountReportList(pageMap);
		BigDecimal totalstoragenum=BigDecimal.ZERO;
		BigDecimal totalstorageamount=BigDecimal.ZERO;
		BigDecimal totalstorageauxint=BigDecimal.ZERO;
		BigDecimal totalstorageauxnum=BigDecimal.ZERO;
		String brandid="";
		for(Map itemMap : brandFooterList){
			brandid=(String)itemMap.get("brandid");
			totalstoragenum=(BigDecimal)itemMap.get("totalstoragenum");
			totalstorageamount=(BigDecimal) itemMap.get("totalstorageamount");
			totalstorageauxint=(BigDecimal)itemMap.get("totalstorageauxint");
			totalstorageauxnum=(BigDecimal)itemMap.get("totalstorageauxnum");
			itemMap.put("curstoragenum", totalstoragenum);
			itemMap.put("curstorageamount", totalstorageamount);
			itemMap.put("curstorageauxint", totalstorageauxint);
			itemMap.put("curstorageauxnum", totalstorageauxnum);
			if(null!=brandid && !"".equals(brandid.trim()) && 
					brandMap.containsKey(brandid.trim())){
				PlannedOrderAnalysis brandAnalysis=(PlannedOrderAnalysis)brandMap.get(brandid.trim());
				if(null!=brandAnalysis){
					//如果当前订单里的商品，则需要进行合计
					if(!"3".equals(orderStatus) && !"4".equals(orderStatus) && brandAnalysis.getIsgoodsinbill()==1){
						totalstoragenum=totalstoragenum.add(brandAnalysis.getOrderunitnum());	//主单位数量
						totalstorageamount=totalstorageamount.add(brandAnalysis.getOrderamount());	//合计库存
						totalstorageauxint=totalstorageauxint.add(brandAnalysis.getOrdernum());//辅单位数量
						totalstorageauxnum=totalstorageauxnum.add(brandAnalysis.getOrderauxremainder());
						
					}

					itemMap.put("ordernum", brandAnalysis.getOrdernum());
					itemMap.put("orderauxremainder", brandAnalysis.getOrderauxremainder());
					itemMap.put("orderamount", brandAnalysis.getOrderamount());
					
				}
			}

			itemMap.put("totalstoragenum", totalstoragenum);
			itemMap.put("totalstorageamount", totalstorageamount);
			itemMap.put("totalstorageauxint", totalstorageauxint);
			itemMap.put("totalstorageauxnum", totalstorageauxnum);
		}
		for(Map<String,Object> itemMap : brandFooterList){
			String retordernum = "",retcurstoragenum = "",rettotalstoragenum = "";
			//本次采购数
			if(null != itemMap.get("ordernum")){
				BigDecimal ordernum3 = (BigDecimal)itemMap.get("ordernum");
				if(ordernum3.compareTo(BigDecimal.ZERO) != 0){
					retordernum = String.valueOf(ordernum3.intValue());
				}else{
					retordernum = "0";
				}
				retordernum = retordernum + itemMap.get("auxunitname");
			}
			if(null != itemMap.get("orderauxremainder")){
				BigDecimal orderauxremainder3 = (BigDecimal)itemMap.get("orderauxremainder");
				if(orderauxremainder3.compareTo(BigDecimal.ZERO) != 0){
					retordernum = retordernum + String.valueOf(orderauxremainder3.intValue());
				}else{
					retordernum = retordernum + "0";
				}
				retordernum = retordernum + itemMap.get("unitname");
			}
			//当前库存数
			if(null != itemMap.get("curstorageauxint")){
				BigDecimal curstorageauxint3 = (BigDecimal)itemMap.get("curstorageauxint");
				if(curstorageauxint3.compareTo(BigDecimal.ZERO) != 0){
					retcurstoragenum = String.valueOf(curstorageauxint3.intValue());
				}else{
					retcurstoragenum = "0";
				}
				retcurstoragenum = retcurstoragenum + itemMap.get("auxunitname");
			}
			if(null != itemMap.get("curstorageauxnum")){
				BigDecimal curstorageauxnum3 = (BigDecimal)itemMap.get("curstorageauxnum");
				if(curstorageauxnum3.compareTo(BigDecimal.ZERO) != 0){
					retcurstoragenum = retcurstoragenum + String.valueOf(curstorageauxnum3.intValue());
				}else{
					retcurstoragenum = retcurstoragenum + "0";
				}
				retcurstoragenum = retcurstoragenum + itemMap.get("unitname");
			}
			//合计库存数
			if(null != itemMap.get("totalstorageauxint")){
				BigDecimal totalstorageauxint3 = (BigDecimal)itemMap.get("totalstorageauxint");
				if(totalstorageauxint3.compareTo(BigDecimal.ZERO) != 0){
					rettotalstoragenum = String.valueOf(totalstorageauxint3.intValue());
				}else{
					rettotalstoragenum = "0";
				}
				rettotalstoragenum = rettotalstoragenum + itemMap.get("auxunitname");
			}
			if(null != itemMap.get("totalstorageauxnum")){
				BigDecimal totalstorageauxnum3 = (BigDecimal)itemMap.get("totalstorageauxnum");
				if(totalstorageauxnum3.compareTo(BigDecimal.ZERO) != 0){
					rettotalstoragenum = rettotalstoragenum + String.valueOf(totalstorageauxnum3.intValue());
				}else{
					rettotalstoragenum = rettotalstoragenum + "0";
				}
				rettotalstoragenum = rettotalstoragenum + itemMap.get("unitname");
			}
			itemMap.put("ordernum", retordernum);
			itemMap.put("curstoragenum", retcurstoragenum);
			itemMap.put("totalstoragenum", rettotalstoragenum);
			brandFooterResultList.add(itemMap);
		}
		return brandFooterResultList;
	}

	/**
	 * 初始化分析数据
	 * 
	 * @author zhanghonghui 
	 * @date 2014-7-15
	 */
	private void formPlannedOrderAnalysisWithInitData(PlannedOrderAnalysis plannedOrderAnalysis,List<PlannedOrderAnalysis> initList){
		
		plannedOrderAnalysis.setCanorderunitnum(BigDecimal.ZERO);
		plannedOrderAnalysis.setCurstorageamount(BigDecimal.ZERO);
		plannedOrderAnalysis.setCurstoragenum(BigDecimal.ZERO);
		plannedOrderAnalysis.setExistingunitnum(BigDecimal.ZERO);
		plannedOrderAnalysis.setTransitunitnum(BigDecimal.ZERO);
		plannedOrderAnalysis.setCurstorageunitnum(BigDecimal.ZERO);
		plannedOrderAnalysis.setQqsalenum(BigDecimal.ZERO);
		plannedOrderAnalysis.setQqsaleunitnum(BigDecimal.ZERO);
		plannedOrderAnalysis.setTotalstorageamount(BigDecimal.ZERO);
		plannedOrderAnalysis.setTotalstoragenum(BigDecimal.ZERO);
		plannedOrderAnalysis.setTotalstorageunitnum(BigDecimal.ZERO);
		plannedOrderAnalysis.setTqsalenum(BigDecimal.ZERO);
		plannedOrderAnalysis.setTqsaleunitnum(BigDecimal.ZERO);
		
		if(null==initList || initList.size()==0){
			return;
		}
		for(PlannedOrderAnalysis item : initList){
			plannedOrderAnalysis.setIsgoodsinbill(0);
			if(plannedOrderAnalysis.getGoodsid().equals(item.getGoodsid())){
				plannedOrderAnalysis.setIsgoodsinbill(1);
				plannedOrderAnalysis.setCanorderunitnum(item.getCanorderunitnum());
				plannedOrderAnalysis.setCurstorageamount(item.getCurstorageamount());
				plannedOrderAnalysis.setCurstoragenum(item.getCurstoragenum());
				plannedOrderAnalysis.setExistingunitnum(item.getExistingunitnum());
				plannedOrderAnalysis.setTransitunitnum(item.getTransitunitnum());
				plannedOrderAnalysis.setCurstorageunitnum(item.getCurstorageunitnum());
				plannedOrderAnalysis.setQqsalenum(item.getQqsalenum());
				plannedOrderAnalysis.setQqsaleunitnum(item.getQqsaleunitnum());
				plannedOrderAnalysis.setRemark(item.getRemark());
				plannedOrderAnalysis.setTotalstorageamount(item.getTotalstorageamount());
				plannedOrderAnalysis.setTotalstoragenum(item.getTotalstoragenum());
				plannedOrderAnalysis.setTotalstorageunitnum(item.getTotalstorageunitnum());
				plannedOrderAnalysis.setTqsalenum(item.getTqsalenum());
				plannedOrderAnalysis.setTqsaleunitnum(item.getTqsaleunitnum());
				break;
			}
		}
	}
	
	@Override
	public PageData showArrivalOrderCostAccountReportData(PageMap pageMap) throws Exception{

		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "goodsid");
			groupcols = "goodsid";
		}
		if(pageMap.getCondition().containsKey("brandid")){
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbrandarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("branduserid")){
			String str = (String) pageMap.getCondition().get("branduserid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbranduserarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("buydeptid")){
			String str = (String) pageMap.getCondition().get("buydeptid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbuydeptarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("goodsid")){
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isgoodsidarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("supplierid")){
			String str = (String) pageMap.getCondition().get("supplierid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("issupplieridarr", "1");
			}
		}
		List<ArrivalOrderCostAccountReport> list=purchaseReportMapper.getArrivalOrderCostAccountReportData(pageMap);
		GoodsInfo goodsInfo =null;
		for(ArrivalOrderCostAccountReport item : list){
			if(null!=item){	
				item.setBuynum(BigDecimal.ZERO);
				if(null==item.getCostaccountprice()){
					item.setCostaccountprice(BigDecimal.ZERO);
				}
				if(null==item.getCostaccountamount()){
					item.setCostaccountamount(BigDecimal.ZERO);
				}
				if(BigDecimal.ZERO.compareTo(item.getCostaccountamount()) == 0){
					item.setBlanceamount(BigDecimal.ZERO);
				}
				if(groupcols.indexOf("goodsid")!=-1){
					if(StringUtils.isNotEmpty(item.getGoodsid())){
						goodsInfo= getAllGoodsInfoByID(item.getGoodsid());
						if(null!=goodsInfo){
							//条形码
							if(StringUtils.isNotEmpty(goodsInfo.getBarcode())){
								item.setBarcode(goodsInfo.getBarcode());
							}
							if(null!=goodsInfo.getBoxnum()){
								item.setBoxnum(goodsInfo.getBoxnum());
								if(item.getBoxnum().compareTo(BigDecimal.ZERO)!=0){
									item.setBuynum(item.getBuyunitnum().divide(item.getBoxnum(), BillGoodsNumDecimalLenUtils.decimalLen, BigDecimal.ROUND_HALF_UP));
								}
							}
							item.setGoodsname(goodsInfo.getName());
							item.setBrandname(goodsInfo.getBrandName());
						}
					}
					if(groupcols.indexOf("brandid")==-1){
						Brand brand = getGoodsBrandByID(item.getBrandid());
						if(null!=brand){
							item.setBrandname(brand.getName());
						}
					}
					if(groupcols.indexOf("buydeptid")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
						if(null!=departMent){
							item.setBuydeptname(departMent.getName());
						}else{
							item.setBuydeptname("其他");
						}
					}
					if(groupcols.indexOf("buyuserid")==-1){
						if(StringUtils.isNotEmpty(item.getBuyuserid())){
							Personnel personnel = getPersonnelById(item.getBuyuserid());
							if(null!=personnel){
								item.setBuyusername(personnel.getName());
							}else{
								item.setBuyusername("其他");
							}
						}else{
							item.setBuyusername("其他");
						}
					}
					if(groupcols.indexOf("supplierid")==-1){
						BuySupplier buySupplier=getSupplierInfoById(item.getSupplierid());
						if(null!=buySupplier){
							item.setSuppliername(buySupplier.getName());
						}else{
							item.setSuppliername("其他");
						}
					}
				}else{
					item.setGoodsid("");
				}
				if(groupcols.indexOf("brandid")!=-1){
					Brand brand = getGoodsBrandByID(item.getBrandid());
					if(null!=brand){
						item.setBrandname(brand.getName());
					}else{
						item.setBrandname("其他");
					}
					if(groupcols.indexOf("buydeptid")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
						if(null!=departMent){
							item.setBuydeptname(departMent.getName());
						}else{
							item.setBuydeptname("其他");
						}
					}
				}
				if(groupcols.indexOf("buyuserid")!=-1){
					Personnel personnel = getPersonnelById(item.getBuyuserid());
					if(null!=personnel){
						item.setBuyusername(personnel.getName());
					}else{
						item.setBuyusername("其他");
					}
					if(groupcols.indexOf("brandid")==-1){
						item.setBrandname("");
					}
					if(groupcols.indexOf("buydeptid")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
						if(null!=departMent){
							item.setBuydeptname(departMent.getName());
						}else{
							item.setBuydeptname("其他");
						}
					}
				}
				if(groupcols.indexOf("buydeptid")!=-1){
					DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
					if(null!=departMent){
						item.setBuydeptname(departMent.getName());
					}else{
						item.setBuydeptname("其他");
					}
				}
				if(groupcols.indexOf("supplierid")!=-1){
					BuySupplier buySupplier=getSupplierInfoById(item.getSupplierid());
					if(null!=buySupplier){
						item.setSuppliername(buySupplier.getName());
					}else{
						item.setSuppliername("其他");
					}
				}
			}
		}
		int total=purchaseReportMapper.getArrivalOrderCostAccountReportCount(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<ArrivalOrderCostAccountReport> footer=purchaseReportMapper.getArrivalOrderCostAccountReportData(pageMap);
		if(null!=footer && footer.size()>0 && !(footer.size()==1 && null ==footer.get(0))){
			for(ArrivalOrderCostAccountReport item : footer){
				if(null!=item){
					if(groupcols.indexOf("goodsid")!=-1){
					
					}else if(groupcols.indexOf("brandid")!=-1){
						
					}else if(groupcols.indexOf("buyuserid")!=-1){
						
					}else if(groupcols.indexOf("buydeptid")!=-1){
							
					}
					item.setGoodsid("");
					item.setBuyusername("合计");
					item.setBuydeptid("");
					item.setBuyunitname("");
					item.setCostaccountprice(null);
					item.setBuynum(null);
					item.setBuyunitnum(null);
				}
			}
		}else{
			footer=new ArrayList<ArrivalOrderCostAccountReport>();
		}
		pageData.setFooter(footer);
		return pageData;		
	}
	@Override
	public PageData showBuyPaymentBalanceReportData(PageMap pageMap) throws Exception{
		String dataSql = getDataAccessRule("t_report_purchase_base", "t");
		pageMap.setDataSql(dataSql);
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "brandid");
			groupcols = "brandid";
		}
		if(pageMap.getCondition().containsKey("brand")){
			String str = (String) pageMap.getCondition().get("brand");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbrandarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("buyuser")){
			String str = (String) pageMap.getCondition().get("buyuser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbuyuserarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("buydept")){
			String str = (String) pageMap.getCondition().get("buydept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("isbuydeptarr", "1");
			}
		}
		if(pageMap.getCondition().containsKey("supplier")){
			String str = (String) pageMap.getCondition().get("supplier");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") >0){
				pageMap.getCondition().put("issupplierarr", "1");
			}
		}

		List<BuyPaymentBalanceReport> list=purchaseReportMapper.getBuyPaymentBalanceReportData(pageMap);
		BuySupplier buySupplier=null;
		for(BuyPaymentBalanceReport item : list){
			if(null!=item){
				if(groupcols.indexOf("brandid")!=-1){
					if(StringUtils.isNotEmpty(item.getBrandid())){
						Brand brand = getGoodsBrandByID(item.getBrandid());
						if(null!=brand){
							item.setBrandname(brand.getName());
						}else{
							item.setBrandname("其他");
						}					
					}else{
						item.setBrandname("其他");
					}
	
					if(groupcols.indexOf("buydeptid")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
						if(null!=departMent){
							item.setBuydeptname(departMent.getName());
						}else{
							item.setBuydeptname("其他");
						}
					}
					if(groupcols.indexOf("buyuserid")==-1){
						if(StringUtils.isNotEmpty(item.getBuyuserid())){
							Personnel personnel = getPersonnelById(item.getBuyuserid());
							if(null!=personnel){
								item.setBuyusername(personnel.getName());
							}else{
								item.setBuyusername("其他");
							}
						}
					}
				}
				if(groupcols.indexOf("buyuserid")!=-1){
					Personnel personnel = getPersonnelById(item.getBuyuserid());
					if(null!=personnel){
						item.setBuyusername(personnel.getName());
					}else{
						item.setBuyusername("其他");
					}
					if(groupcols.indexOf("brandid")==-1){
						item.setBrandname("");
					}
					if(groupcols.indexOf("buydeptid")==-1){
						DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
						if(null!=departMent){
							item.setBuydeptname(departMent.getName());
						}else{
							item.setBuydeptname("其他");
						}
					}
				}
				if(groupcols.indexOf("buydeptid")!=-1){
					DepartMent departMent = getDepartmentByDeptid(item.getBuydeptid());
					if(null!=departMent){
						item.setBuydeptname(departMent.getName());
					}else{
						item.setBuydeptname("其他");
					}
				}
				if(StringUtils.isNotEmpty(item.getSupplierid())){
					buySupplier=getSupplierInfoById(item.getSupplierid());
					if(null!=buySupplier){
						item.setSuppliername(buySupplier.getName());
					}
				}
			}
		}
		int total=purchaseReportMapper.getBuyPaymentBalanceReportCount(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BuyPaymentBalanceReport> footer=purchaseReportMapper.getBuyPaymentBalanceReportData(pageMap);
		if(null!=footer && footer.size()>0 && !(footer.size()==1 && null ==footer.get(0))){
			for(BuyPaymentBalanceReport item : footer){
				if(null!=item){
					item.setSupplierid("");
					item.setBuyuserid("");
					item.setSuppliername("合计");
				}
			}
		}else{
			footer=new ArrayList<BuyPaymentBalanceReport>();
		}
		pageData.setFooter(footer);
		return pageData;
	}
	/**
	 * 采购进货数量报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public PageData showPurchaseQuantityReportData(PageMap pageMap) throws Exception{
		//String dataSql = getDataAccessRule("t_report_arrivalordercostaccount", null);
		//pageMap.setDataSql(dataSql);
		List<PurchaseQuantityReport> list= purchaseReportMapper.getPurchaseQuantityReport(pageMap);
		boolean isRemainder=false;
		for(PurchaseQuantityReport item : list){
			if(StringUtils.isNotEmpty(item.getBrandid())){
				Brand brand = getGoodsBrandByID(item.getBrandid());
				if(null!=brand){
					item.setBrandname(brand.getName());
				}			
			}
			if(BigDecimal.ZERO.compareTo(item.getEnterremainder())!=0){
				isRemainder=true;
			}else{
				isRemainder=false;
			}
			if(null!= item.getEnternum()){
				if(isRemainder){
					item.setEnternum(item.getEnternum().setScale(2, BigDecimal.ROUND_HALF_UP));
				}else{
					item.setEnternum(item.getEnternum().setScale(0, BigDecimal.ROUND_HALF_UP));
				}
			}else{
				item.setEnternum(BigDecimal.ZERO);
			}
			if(null!=item.getTotalweight()){
				item.setTotalweight(item.getTotalweight().setScale(2, BigDecimal.ROUND_HALF_UP));
			}else{
				item.setTotalweight(BigDecimal.ZERO);
			}
		}
		int total =purchaseReportMapper.getPurchaseQuantityReportCount(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		Map condition=pageMap.getCondition();
		if(null==condition){
			condition=new HashMap();
		}
		condition.put("isShowSumTotal", "true");
		condition.put("ispageflag", "true");
		List<PurchaseQuantityReport> footer= purchaseReportMapper.getPurchaseQuantityReport(pageMap);
		if(null!=footer && footer.size()>0 && !(footer.size()==1 && null ==footer.get(0))){
			for(PurchaseQuantityReport item : footer){
				if(null!=item){
					item.setBrandid("");
					item.setBrandname("合计");
					item.setEnterunitname(null);
					item.setEnterremainder(null);
					if(null!= item.getEnternum()){
						item.setEnternum(item.getEnternum().setScale(2, BigDecimal.ROUND_HALF_UP));						
					}else{
						item.setEnternum(BigDecimal.ZERO);
					}
					if(null!=item.getTotalweight()){
						item.setTotalweight(item.getTotalweight().setScale(2, BigDecimal.ROUND_HALF_UP));
					}else{
						item.setTotalweight(BigDecimal.ZERO);
					}
				}
			}
		}else{
			footer=new ArrayList<PurchaseQuantityReport>();
		}
		pageData.setFooter(footer);
		return pageData;
	}
	/**
	 * 计算销售时段
	 * @param sdateStr
	 * @param edateStr
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-10-14
	 */
	private int getSalesDays(String sdateStr,String edateStr) throws Exception{
		int saleday=0;
		if((null!=sdateStr && !"".equals(sdateStr.trim())) && (null!=edateStr && !"".equals(edateStr.trim())) ){
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			 Date sDate=sdf.parse(sdateStr.trim());
			 Date eDate=sdf.parse(edateStr.trim());
			 if(null!=sDate && null!=eDate){
				 	Calendar cal = Calendar.getInstance();    
			        cal.setTime(sDate);    
			        long time1 = cal.getTimeInMillis();                 
			        cal.setTime(eDate);    
			        long time2 = cal.getTimeInMillis();         
			        if(time2>time1){
				        long between_days=(time2-time1)/(1000*3600*24);
				        saleday=Integer.parseInt(String.valueOf(between_days))+1;
			        }
			 }
		}
		return saleday;
	}
	
	/**
	 * 获取采购订单追踪明细数据
	 * @param pageMap
	 * @return
	  * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	@Override
	public PageData showBuyOrderTrackReportData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_purchase_buyorder", "t");
		pageMap.setDataSql(dataSql);
		List<Map> list = purchaseReportMapper.showBuyOrderTrackReportData(pageMap);
		BigDecimal indexOrdernum=new BigDecimal("0");
		BigDecimal indexOrderamount=new BigDecimal("0");
		BigDecimal indexOrdertotalbox=new BigDecimal("0");
		for(Map map:list){
			String goodsid1 = (String) map.get("goodsid1");
			String goodsid2 = (String) map.get("goodsid2");
			String goodsid="";
			if(goodsid2!=null)
				goodsid=goodsid2;
			else
				goodsid=goodsid1;
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				map.put("barcode", goodsInfo.getBarcode());
				map.put("goodsid", goodsid);
				map.put("brandid", goodsInfo.getBrand());
			}
			//start
			if(goodsid2!=null&&!goodsid2.equals(goodsid1)){
				BigDecimal ordernum=(BigDecimal)map.get("ordernum");
				BigDecimal ordertotalbox=(BigDecimal)map.get("ordertotalbox");
				BigDecimal orderamount=(BigDecimal)map.get("orderamount");
				indexOrdernum=indexOrdernum.add(ordernum);
				indexOrderamount=indexOrderamount.add(orderamount);
				indexOrdertotalbox=indexOrdertotalbox.add(ordertotalbox);
				map.put("ordernum", 0);
				map.put("ordertotalbox", 0);
				map.put("orderamount", 0);
				map.put("taxprice", 0);
			}
			//end
			String brandid = (String) map.get("brandid");
			Brand brand = getGoodsBrandByID(brandid);
			if(null!=brand){
				map.put("brandname", brand.getName());
			}
			String supplierid = (String) map.get("supplierid");
			BuySupplier supplier = getSupplierInfoById(supplierid);
			if(null!=supplier){
				map.put("suppliername", supplier.getName());
			}
		}
		PageData pageData = new PageData(purchaseReportMapper.showBuyOrderTrackReportCount(pageMap),list,pageMap);
		pageMap.getCondition().put("groupcols", "all");
		List<Map> footer = purchaseReportMapper.showBuyOrderTrackReportDataSum(pageMap);
		for(Map map : footer){
			if(null!=map){
				map.put("price", null);
				map.put("orderid", null);
				map.put("businessdate", "合计");
				map.put("customerid", null);
				map.put("goodsid", null);
				map.put("unitname", null);
				
				BigDecimal ordernum=(BigDecimal)map.get("ordernum");
				BigDecimal ordertotalbox=(BigDecimal)map.get("ordertotalbox");
				BigDecimal orderamount=(BigDecimal)map.get("orderamount");
				map.put("ordernum",ordernum.subtract(indexOrdernum));
				map.put("ordertotalbox", ordertotalbox.subtract(indexOrdertotalbox));
				map.put("orderamount", orderamount.subtract(indexOrderamount));
				
				BigDecimal salesamount = (BigDecimal) map.get("salesamount");
				BigDecimal salemarginamount = (BigDecimal) map.get("salemarginamount");
				if(null==salemarginamount){
					salemarginamount = BigDecimal.ZERO;
				}
				if(null!=salesamount ){
					if(salesamount.compareTo(BigDecimal.ZERO)!=0){
						//销售毛利率：(本期销售-成本金额)/本期销售 或 本期毛利金额/本期销售
						BigDecimal rate = salemarginamount.divide(salesamount, 6, BigDecimal.ROUND_HALF_UP);
						map.put("rate", rate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}else{
						map.put("rate", new BigDecimal(100).negate());
					}
				}
			}else{
				footer = new ArrayList<Map>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}
}

