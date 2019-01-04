/**
 * @(#)ISalesReportService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 15, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.activiti.engine.impl.Page;

import java.util.List;
import java.util.Map;

/**
 * 
 * 销售报表相关service
 * @author chenwei
 */
public interface ISalesReportService {
	
	/**
	 * 获取按品牌部门销售情况数据列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 21, 2013
	 */
	public Map showSalesCompanyReportList(PageMap pageMap)throws Exception;
	/**
	 * 获取基础销售情况统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public PageData showBaseSalesReportData(PageMap pageMap) throws Exception;
    /**
     * 获取人员部门销售统计数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date April 29, 2016
     */
    public PageData showSalesDeptReportData(PageMap pageMap) throws Exception;

	/**
	 * 获取销售同期比数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-25
	 */
	public PageData showSalesCorrespondPeriodReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取部门采购销售汇总报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-11-28
	 */
	public PageData showBuySalesBillCountReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取销售订单追踪明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	public PageData showSalesOrderTrackReportData(PageMap pageMap) throws Exception;
	
	/**
	 * 退货追踪明细数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jan 8, 2014
	 */
	public PageData getSalesRejectTrackReportData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取尤妮佳销售情况统计表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public List getUNJIASalesReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 客户商品报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public PageData showSalesGoodsSandPriceReportData(PageMap pageMap) throws Exception;
	/**
	 * 客户交易商品报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public PageData showSalesGoodsTradeReportData(PageMap pageMap) throws Exception;
	/**
	 * 客户未交易商品报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public PageData showSalesGoodsNotTradeReportData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取财务销售情况统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Feb 24, 2014
	 */
	public PageData getFinanceSalesReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 要货金额报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 6, 2014
	 */
	public PageData getSalesDemandReportList(PageMap pageMap)throws Exception;
	
	/**
	 * 直营销售报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public PageData getSalesCarReportList(PageMap pageMap)throws Exception;

	/**
	 * 客户商品报价报表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public PageData showSalesGoodsQuotationReportData(PageMap pageMap) throws Exception;	

	/**
	 * 销售数量报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-2-21
	 */
	public PageData showSalesQuantityReportData(PageMap pageMap) throws Exception;

    /**
     * 显示客户业务员 客户目标考核报表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-7-22
     */
    public PageData showSalesUserTargetReportData(PageMap pageMap) throws Exception;
	
	/**
	 * 显示销售目标报表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-7
	 */
	public PageData showSalesCustomerTargetReportData(PageMap pageMap) throws Exception;
	/**
	 * 保存销售客户目标考核报表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-4-18
	 */
	public boolean saveSalesCustomerTargetReport(Map map) throws Exception;
	/**
	 *  销售毛利分析报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-5-10
	 */
	public PageData showSalesBrandGrossReportData(PageMap pageMap) throws Exception;
	/**
	 * 根据总店 获取各门店的销售金额
	 * @param pcustomerid
	 * @param begindate
	 * @param enddate
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public List getSalesReportListByPcustomer(String pcustomerid,String begindate,String enddate) throws Exception;
	/**
	 * 根据部门编号 获取供应商的销售金额
	 * @param begindate
	 * @param enddate
	 * @param detpid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public List getSupplierSalesAmountByDetpid(String begindate,String enddate,String detpid) throws Exception;

    /**
     * 根据日期 获取客户的销售金额
     * @param date
     * @param salesDataType
     * @return
     * @throws Exception
     */
    public List getCustomerSalesAmountByDate(String date,String salesDataType,String salesdept) throws  Exception;

	/**
	 * 根据日期 获取客户的销售金额
	 * @param date
	 * @param salesDataType
	 * @return
	 * @throws Exception
	 */
	public List getCustomerSalesAmountByDateForThird(String date,String salesDataType,String salesdept) throws  Exception;
	/**
	 * 获取品牌销售考核报表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public PageData showBrandAssessReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取客户新品销售情况数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public PageData showCustomerNewGoodsReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取客户老品销售情况数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public PageData showCustomerOldGoodsReportData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取分司机销售退货情况统计报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 31, 2014
	 */
	public PageData getSalesRejectEnterReportData(PageMap pageMap)throws Exception;
	
	/**
	 * 获取分品牌业务员销售回笼考核数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 22, 2015
	 */
	public PageData getSalesWithdrawnAssessData(PageMap pageMap)throws Exception;

    /**
     * 获取客户汇总统计报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-13
     */
    public PageData showCollectReportData(PageMap pageMap)throws Exception;

    /**
     * 获取客户未销售查询报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-11
     */
    public PageData getCustomerUnsaleQueryReportList(PageMap pageMap)throws Exception;
    /**
     * 分月汇总销售报表数据
     * @return
     * @throws Exception
     * @author huangzhiqian
     * @date 2016年1月12日
     */
	public PageData showMonthSaleData(PageMap pageMap)throws Exception;

	/**
	 * 查询销售退货统计报表数据
	 *
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Aug 15, 2015
	 */
	public PageData getSalesRejectReportListData(PageMap pageMap) throws Exception;
    /**
     * 获取赠品报表统计数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Aug 12, 2019
     */
    public PageData getSalesPresentReportData(PageMap pageMap) throws Exception;

	/**
	 * 获取促销活动统计报表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-06
	 */
	public PageData getSalesPromotionReportData(PageMap pageMap)throws Exception;

	/**
	 * 获取档期活动折让统计表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-09
	 */
	public PageData getSalesScheduleActivityDiscountReportData(PageMap pageMap)throws Exception;

	/**
	 * 查询对账单分页数据
	 *
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 28, 2017
	 */
	public PageData getSalesBillStatementPageData(PageMap pageMap)throws Exception;

	/**
	 * 查询对账单明细分页数据
	 *
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date May 2, 2016
	 */
	public PageData getSalesBillStatementDetailPageData(PageMap pageMap) throws Exception ;

	/**
	 * 获取厂商毛利率统计表数据
	 *
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-5-11
	 */
	public PageData showSalesSuppliserGrossReportData(PageMap pageMap) throws Exception ;

	/**
	 * 获取销售订货单报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Jan 10, 2018
	 */
	public PageData getOrderGoodsReportData(PageMap pageMap) throws Exception;

	/**
	 * 获取基础销售情况统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-12-26
	 */
	public PageData showBaseSalesAnalysisReportData(PageMap pageMap) throws Exception;

	/**
	 * 获取费用科目明细
	 * @param pushtype
	 * @param groupcolList
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2017-12-26
	 */
	public List<Map> showSalesAnalysisReportSubjectDetailPage(String pushtype,List<Map> groupcolList,String businessdate1,String businessdate2) throws Exception;

	/**
	 * 德阳销售情况统计报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Feb 05, 2018
	 */
	public PageData getDyBaseSalesReportData(PageMap pageMap) throws Exception;

	/**
	 * 获取吉马销售日报表数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public PageData showJmSalesTargetDayReportData(PageMap pageMap) throws Exception;

	/**
	 * 导入吉马销售日报表目标
	 * @param list
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public List<Map<String, Object>> importJmSalesTarget(List list) throws Exception;

	/**
	 * 保存销售日报表目标（吉马）
	 * @param targetdate
	 * @param list
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Mar 20, 2018
	 */
	public Boolean saveJmSalesTargetDay(String targetdate,List<Map> list);
}

