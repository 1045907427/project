package com.hd.agent.report.dao;

import java.util.List;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.PurchaseDetailReport;

/**
 * 采购情况报表dao
 * @author chenwei
 */
public interface PurchaseReportMapper {
	/**
	 * 获取按商品采购情况统计列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 27, 2013
	 */
	public List getBuyGoodsReportDataList(PageMap pageMap);
	
	/**
	 * 获取按商品采购情况统计数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 27, 2013
	 */
	public int getBuyGoodsReportDataCount(PageMap pageMap);
	
	/**
	 * 获取按商品采购情况合计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 28, 2013
	 */
	public PurchaseDetailReport getBuyGoodsReportSumData(PageMap pageMap);
	
	/*---------------------------------------*/
	//        分部门采购情况统计报表               //
	/*---------------------------------------*/
	
	
	/**
	 * 获取分部门采购情况统计列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 29, 2013
	 */
	public List getBuyDeptReportDataList(PageMap pageMap);
	
	/**
	 * 获取分部门采购情况统计列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 29, 2013
	 */
	public int getBuyDeptReportDataCount(PageMap pageMap);
	
	/**
	 * 获取分部门采购情况合计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 29, 2013
	 */
	public PurchaseDetailReport getBuyDeptReportSumData(PageMap pageMap);
	
	/**
	 * 根据部门获取分部门统计情况表详情列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 29, 2013
	 */
	public List getBuyDeptReportDetailList(PageMap pageMap);
	
	/*---------------------------------------*/
	//        分品牌采购情况统计报表               //
	/*---------------------------------------*/
	
	/**
	 * 获取分品牌采购情况统计列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List getBuyBrandReportDataList(PageMap pageMap);
	
	/**
	 * 获取分品牌采购情况统计列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public int getBuyBrandReportDataCount(PageMap pageMap);
	
	/**
	 * 获取分品牌采购情况合计数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public PurchaseDetailReport getBuyBrandReportSumData(PageMap pageMap);
	
	/**
	 * 根据品牌获取分部门统计情况表详情列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List getBuyBrandReportDetailList(PageMap pageMap);
	
	/*---------------------------------------*/
	//        分供应商采购情况统计报表             //
	/*---------------------------------------*/
	
	/**
	 * 获取分供应商采购情况统计列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List getBuySupplierReportDataList(PageMap pageMap);
	
	/**
	 * 获取分供应商采购情况统计列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public int getBuySupplierReportDataCount(PageMap pageMap);
	
	/**
	 * 获取分供应商采购情况合计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public PurchaseDetailReport getBuySupplierReportSumData(PageMap pageMap);
	
	/**
	 * 根据供应商获取分部门统计情况表详情列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List getBuySupplierReportDetailList(PageMap pageMap);
	
	/**
	 * 根据供应商获取分部门统计情况表详情数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	public int getBuySupplierReportDetailCount(PageMap pageMap);
	
	/*---------------------------------------*/
	//        分采购员采购情况统计报表             //
	/*---------------------------------------*/
	
	/**
	 * 获取分采购员采购情况统计列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List getBuyUserReportDataList(PageMap pageMap);
	
	/**
	 * 获取分采购员采购情况统计列表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public int getBuyUserReportDataCount(PageMap pageMap);
	
	/**
	 * 获取分采购员采购情况合计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public PurchaseDetailReport getBuyUserReportSumData(PageMap pageMap);
	
	/**
	 * 根据采购员获取分部门统计情况表详情列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List getBuyUserReportDetailList(PageMap pageMap);
	
	/**
	 * 根据采购员获取分部门统计情况表详情数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 22, 2014
	 */
	public int getBuyUserReportDetailListCount(PageMap pageMap);
	/**
	 * 采购计划分析分页统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-19
	 */
	public List showPlannedOrderAnalysisPageList(PageMap pageMap);
	/**
	 * 采购计划分析分页统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-19
	 */
	public int showPlannedOrderAnalysisPageCount(PageMap pageMap);	
	
	/**
	 * 采购进货差额报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-5
	 */
	public List getArrivalOrderCostAccountReportData(PageMap pageMap);
	/**
	 * 采购进货差额报表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-5
	 */
	public int getArrivalOrderCostAccountReportCount(PageMap pageMap);
	/**
	 * 采购付款差额报表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-7
	 */
	public List getBuyPaymentBalanceReportData(PageMap pageMap);
	/**
	 * 采购付款差额报表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-7
	 */
	public int getBuyPaymentBalanceReportCount(PageMap pageMap);
	/**
	 * 采购进货数量报表列表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public List getPurchaseQuantityReport(PageMap pageMap);

	/**
	 * 采购进货数量报表列表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public int getPurchaseQuantityReportCount(PageMap pageMap);
	
	/**
	 * 获取采购订单追踪明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	public List showBuyOrderTrackReportData(PageMap pageMap);
	/**
	 * 获取采购订单追踪明细数量
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	public int showBuyOrderTrackReportCount(PageMap pageMap);
	/**
	 * 获取采购订单追踪明细合计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	public List showBuyOrderTrackReportDataSum(PageMap pageMap);
}