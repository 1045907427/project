/**
 * @(#)IPurchaseReportService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 16, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 采购报表service
 * @author chenwei
 */
public interface IPurchaseReportService {
	/**
	 * 获取按商品采购情况报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 27, 2013
	 */
	public PageData showBuyGoodsReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取分部门采购情况报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 29, 2013
	 */
	public PageData showBuyDeptReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 根据部门编码获取采购统计情况报表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 29, 2013
	 */
	public List showBuyDeptReportDetailList(Map map)throws Exception;
	
	/**
	 * 获取分品牌采购情况报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public PageData showBuyBrandReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 根据品牌编码获取采购统计情况报表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List showBuyBrandReportDetailList(Map map)throws Exception;
	
	/**
	 * 获取分供应商采购情况报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public PageData showBuySupplierReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 根据供应商编码获取采购统计情况报表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List showBuySupplierReportDetailList(Map map)throws Exception;
	
	/**
	 * 根据供应商编码获取分商品采购统计情况数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 7, 2014
	 */
	public PageData getBuySupplierReportDetailData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取分采购员采购情况报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public PageData showBuyUserReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 根据采购员编码获取采购统计情况报表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 30, 2013
	 */
	public List showBuyUserReportDetailList(Map map)throws Exception;
	
	/**
	 * 采购计划单分析表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-19
	 */
	public PageData showPlannedOrderAnalysisPageData(PageMap pageMap) throws Exception;
	
	/**
	 * 按品牌合计库存
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 13, 2014
	 */
	public PageData showStorageSummaryByBrand(PageMap pageMap)throws Exception;
	/**
	 * 采购计划单分析表，采购订单查看
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-19
	 */
	public PageData showPlannedOrderAnalysisPageDataInBuyOrder(PageMap pageMap) throws Exception;
	/**
	 * 按品牌合计库存
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-7-19
	 */
	public PageData showStorageSummaryByBrandInBuyOrder(PageMap pageMap) throws Exception;
	
	/**
	 * 获取按品牌合计库存导出数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 12, 2014
	 */
	public List<Map<String,Object>> getExportStorageSummaryByBrandInBuyOrder(PageMap pageMap)throws Exception;
	/**
	 * 采购进货差额报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-12-5
	 */
	public PageData showArrivalOrderCostAccountReportData(PageMap pageMap) throws Exception;
	/**
	 * 采购付款差额报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-12-7
	 */
	public PageData showBuyPaymentBalanceReportData(PageMap pageMap) throws Exception;
	/**
	 * 采购进货数量报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public PageData showPurchaseQuantityReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取采购订单追踪明细数据
	 * @param pageMap
	 * @return
	  * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	public PageData showBuyOrderTrackReportData(PageMap pageMap) throws Exception;
}

