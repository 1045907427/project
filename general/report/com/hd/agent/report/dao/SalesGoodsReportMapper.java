package com.hd.agent.report.dao;

import java.util.List;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.SalesBrandDeptReport;
import com.hd.agent.report.model.SalesBrandReport;
import com.hd.agent.report.model.SalesGoodsReport;

/**
 * 按商品销售情况统计表
 * @author chenwei
 */
public interface SalesGoodsReportMapper {
	/**
	 * 获取按商品销售情况统计报表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 30, 2013
	 */
	public List showSalesGoodsReportDataList(PageMap pageMap);
	
	public List getSalesGoodsReportData(PageMap pageMap);
	
	public int getSalesGoodsReportDataCount(PageMap pageMap);
	
	/**
	 * 获取按商品销售情况统计报表数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 30, 2013
	 */
	public int showSalesGoodsReportDataCount(PageMap pageMap);
	
	/**
	 * 获取按商品商品销售情况合计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 12, 2013
	 */
	public SalesGoodsReport getSalesGoodsReportSumData(PageMap pageMap);
	/**
	 * 获取按商品分客户统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 13, 2013
	 */
	public List showSalesGoodsCustomerDetailList(PageMap pageMap);
	/**
	 * 获取按商品分客户统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 13, 2013
	 */
	public int showSalesGoodsCustomerDetailCount(PageMap pageMap);
	/**
	 * 获取按品牌部门销售统计情况报表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 21, 2013
	 */
	public List getshowBrandDeptReportDataList(PageMap pageMap);
	
	/**
	 * 获取按品牌部门销售统计情况报表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 21, 2013
	 */
	public int getshowBrandDeptReportDataCount(PageMap pageMap);
	
	/**
	 * 获取根据品牌部门合计销售统计情况数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 21, 2013
	 */
	public SalesBrandDeptReport getBrandDeptReportByBrandDeptSumData(PageMap pageMap);
	
	/**
	 * 获取按品牌销售情况统计报表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 12, 2013
	 */
	public List showSalesBrandReportDataList(PageMap pageMap);
	
	/**
	 * 获取所有按品牌销售情况统计报表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 13, 2013
	 */
	public List showSalesBrandReportDataListSum(PageMap pageMap);
	
	/**
	 * 获取按品牌销售情况统计报表数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 12, 2013
	 */
	public int showSalesBrandReportDataCount(PageMap pageMap);
	/**
	 * 获取按品牌分商品统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 13, 2013
	 */
	public List showSalesBrandGoodsDetailList(PageMap pageMap);
	/**
	 * 获取按品牌分商品统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 13, 2013
	 */
	public int showSalesBrandGoodsDetailcount(PageMap pageMap);
	/**
	 * 获取按商品品牌销售情况合计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 12, 2013
	 */
	public SalesBrandReport getSalesBrandReportSumData(PageMap pageMap);
	/**
	 * 销售数量报表列表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public List getSalesQuantityReport(PageMap pageMap);

	/**
	 * 销售数量报表列表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public int getSalesQuantityReportCount(PageMap pageMap);
	/**
	 * 销售毛利分析报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-5-10
	 */
	public List getSalesBrandGrossReportData(PageMap pageMap);
	/**
	 * 销售毛利分析报表 条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-5-10
	 */
	public int getSalesBrandGrossReportDataCount(PageMap pageMap);
	
}