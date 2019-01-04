package com.hd.agent.report.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.report.model.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 按客户销售情况统计表
 * @author chenwei
 */
public interface SalesCustomerReportMapper {
	/**
	 * 获取按客户销售情况统计报表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 17, 2013
	 */
	public List showSalesCustomerReportDataList(PageMap pageMap);
	/**
	 * 获取按客户销售情况统计报表数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 17, 2013
	 */
	public int showSalesCustomerReportDataCount(PageMap pageMap);
	/**
	 * 获取按客户销售情况合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jul 18, 2013
	 */
	public SalesCustomerReport getSalesCustomerReportSumData(PageMap pageMap);
	
	
	
	/**
	 * 获取按部门销售情况报表数据(根据销售部门)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 20, 2013
	 */
	public List getSalesDeptReportDataBySalesDeptList(PageMap pageMap);
	
	/**
	 * 获取按部门销售情况报表数量(根据销售部门)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 20, 2013
	 */
	public int getSalesDeptReportDataBySalesDeptCount(PageMap pageMap);
	
	/**
	 * 获取按部门销售情况合计数据(根据销售部门)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 20, 2013
	 */
	public SalesDepartmentReport getSalesDeptReportBySalesDeptSumData(PageMap pageMap);
	
	/**
	 * 获取按客户业务员销售情况统计报表数据(客户业务员)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 20, 2013
	 */
	public List getSalesuserReportDataListBySalesUser(PageMap pageMap);
	
	/**
	 * 获取按客户业务员销售情况统计报表数量(客户业务员)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 20, 2013
	 */
	public int getSalesuserReportDataCountBySalesUser(PageMap pageMap);
	
	/**
	 * 获取按客户业务员销售情况合计数据(客户业务员)
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 21, 2013
	 */
	public SalesUserReport getSalesuserReportSumDataBySalesUser(PageMap pageMap);
	
	/**
	 * 获取按品牌业务员销售情况统计报表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public List getSalesBranduserReportDataList(PageMap pageMap);
	
	/**
	 * 获取按品牌业务员销售情况统计报表数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public int getSalesBranduserReportDataCount(PageMap pageMap);
	
	/**
	 * 获取按品牌业务员销售情况合计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 14, 2013
	 */
	public SalesBranduserReport getSalesBranduserReportSumData(PageMap pageMap);
	
	/**
	 * 根据品牌业务员获取数据列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 15, 2013
	 */
	public List getBranduserReportDetailListByBranduser(PageMap pageMap);
	
	/**
	 * 获取按客户业务员分商品统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public List showSalesUserGoodsReportDataList(PageMap pageMap);
	/**
	 * 获取按客户业务员分商品统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public int showSalesUserGoodsReportDataCount(PageMap pageMap);
	/**
	 * 获取按客户业务员分客户分商品统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public List showSalesUserCustomerGoodsReportDataList(PageMap pageMap);
	/**
	 * 获取按客户业务员分客户分商品统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public int showSalesUserCustomerGoodsReportDataCount(PageMap pageMap);
	/**
	 * 获取按部门分品牌统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public List showSalesDeptBrandReportDataList(PageMap pageMap);
	/**
	 * 获取按部门分品牌统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public int showSalesDeptBrandReportDataCount(PageMap pageMap);
	/**
	 * 获取按品牌业务员分品牌统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public List showSalesBranduserDetialListByBrand(PageMap pageMap);
	/**
	 * 获取按品牌业务员分品牌统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public int showSalesBranduserDetialCountByBrand(PageMap pageMap);
	/**
	 * 获取销售情况基础统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public List<BaseSalesReport> showBaseSalesReportData(PageMap pageMap);
    /**
     * 获取人员部门销售统计数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date April 29, 2016
     */
    public List<BaseSalesReport> showSalesDeptReportData(PageMap pageMap);

    /**
     * 获取人员部门销售统计数据数量
     * @return
     * @throws Exception
     * @author lin_xx
     * @date April 29, 2016
     */
    public int showSalesDeptReportDataCount(PageMap pageMap);
	/**
	 * 获取销售情况基础统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 12, 2013
	 */
	public int showBaseSalesReportDataCount(PageMap pageMap);
	
	/*----------------t_sales_all_report-----------------*/
	/**
	 * 获取销售同期比统计数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-23
	 */
	public List showSalesCorrespondPeriodReportData(PageMap pageMap);
	/**
	 * 获取销售同期比统计数量
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-23
	 */
	public int showSalesCorrespondPeriodReportCount(PageMap pageMap);

	/**
	 * 获取部门采购销售汇总比统计数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-23
	 */
	public List showBuySalesBillCountReportData(PageMap pageMap);
	/**
	 * 获取部门采购销售汇总比统计数量
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-11-23
	 */
	public int showBuySalesBillCountReportCount(PageMap pageMap);
	/**
	 * 获取销售订单追踪明细数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	public List showSalesOrderTrackReportData(PageMap pageMap);
	/**
	 * 获取销售订单追踪明细数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	public int showSalesOrderTrackReportCount(PageMap pageMap);
	/**
	 * 获取销售订单追踪明细合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 31, 2013
	 */
	public List showSalesOrderTrackReportDataSum(PageMap pageMap);
	/**
	 * 退货追踪明细列表
	 * @param pageMap
	 * @return
	 * @author zhengziyong 
	 * @date Jan 8, 2014
	 */
	public List getSalesRejectTrackReportList(PageMap pageMap);
	
	public int getSalesRejectTrackReportCount(PageMap pageMap);
	
	/**
	 * 获取尤妮佳销售情况统计表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 31, 2013
	 */
	public List getUNJIASalesReportData(PageMap pageMap);
	
	/**
	 * 获取客户合同商品报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public List getSalesGoodsSandPriceReportData(PageMap pageMap);
	
	/**
	 * 获取客户合同商品报表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public int getSalesGoodsSandPriceReportDataCount(PageMap pageMap);

	
	/**
	 * 获取客户交易商品报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public List getSalesGoodsTradeReportData(PageMap pageMap);
	
	/**
	 * 获取客户交易商品报表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public int getSalesGoodsTradeReportDataCount(PageMap pageMap);

	
	/**
	 * 获取客户未交易商品报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public List getSalesGoodsNotTradeReportData(PageMap pageMap);
	
	/**
	 * 获取客户未交易商品报表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public int getSalesGoodsNotTradeReportDataCount(PageMap pageMap);
	
	/**
	 * 获取财务销售情况统计数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 24, 2014
	 */
	public List getFinanceSalesReportList(PageMap pageMap);
	
	/**
	 * 获取财务销售情况统计数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 24, 2014
	 */
	public int getFinanceSalesReportListCount(PageMap pageMap);
	
	/**
	 * 要货金额报表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 6, 2014
	 */
	public List getSalesDemandReportList(PageMap pageMap);
	
	/**
	 * 要货金额报表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 6, 2014
	 */
	public int getSalesDemandReportCount(PageMap pageMap);
	
	/**
	 * 直营销售报表列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public List getSalesCarReportList(PageMap pageMap);
	
	/**
	 * 直营销售报表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 21, 2014
	 */
	public int getSalesCarReportCount(PageMap pageMap);
	
	/**
	 * 获取客户商品报价单报表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public List getSalesGoodsQuotationReportData(PageMap pageMap);
	
	/**
	 * 获取客户商品报价单报表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-1-22
	 */
	public int getSalesGoodsQuotationReportDataCount(PageMap pageMap);

    /**
     * 获取 客户业务员 客户目标考核报表 数据
     * @param pageMap
     * @return
     * @author lin_xx
     * @date 2016-7-25
     */
    public List getSalesTargetReportSalesUserCustomerData(PageMap pageMap);

    /**
     * 获取 客户业务员 客户目标考核报表 数据数量
     * @param pageMap
     * @return
     */
    public int getSalesTargetReportSalesUserCustomerCount(PageMap pageMap);

    /**
     * 获取 客户业务员 客户目标考核报表 总计
     * @param pageMap
     * @return
     */
    public Map sumSalesTargetReportSalesUser(PageMap pageMap);

    /**
	 * 销售目标统计报表中的业务与客户列数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-7
	 */
	public List getSalesTargetReportBranduserCustomerData(PageMap pageMap);
	/**
	 * 销售目标统计报表中的业务与客户列数据条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-7
	 */
	public int getSalesTargetReportBranduserCustomerCount(PageMap pageMap);
	/**
	 * 销售目标统计报表中的本期与前期销售数据
	 * @param pageMap
	 * @return
	 */
	public Map getSalesCustomerTargetReportSalesData(PageMap pageMap);

	/**
	 * 销售目标统计报表中的销售目标数据
	 * @param pageMap
	 * @return
	 */
	public Map getSalesCustomerTargetReportTargetsData(PageMap pageMap);
	/**
	 * 计算销售客户目标考核条数
	 * @param salesCustomerTargetReport
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-18
	 */
	public int getSalesCustomerTargetReportTargetsCount(SalesCustomerTargetReport salesCustomerTargetReport);
	/**
	 * 添加销售客户目标考核
	 * @param salesCustomerTargetReport
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-18
	 */
	public int addSalesCustomerTargetReport(SalesCustomerTargetReport salesCustomerTargetReport);
	/**
	 * 更新销售客户目标考核
	 * @param salesCustomerTargetReport
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-18
	 */
	public int updateSalesCustomerTargetReport(SalesCustomerTargetReport salesCustomerTargetReport);
	
	/**
	 * 根据总店 获取各门店的销售情况
	 * @param pcustomerid
	 * @param begindate
	 * @param enddate
	 * @return
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public List getSalesReportListByPcustomer(@Param("pcustomerid")String pcustomerid,@Param("begindate")String begindate,@Param("enddate")String enddate);
		/**
	 * 根据部门编号 获取供应商的销售金额
	 * @param deptid
	 * @param begindate
	 * @param enddate
	 * @return
	 * @author chenwei 
	 * @date 2014年11月22日
	 */
	public List getSupplierSalesAmountByDetpid(@Param("deptid")String deptid,@Param("begindate")String begindate,@Param("enddate")String enddate);

    /**
     * 根据日期获取客户的销售金额
     * @param date
     * @param salesDataType
     * @return
     */
    public List getCustomerSalesAmountByDate(@Param("date")String date,@Param("salesDataType")String salesDataType,@Param("salesdept")String salesdept);
	/**
	 * 获取各品牌的销售情况
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年12月5日
	 */
	public List getBrandSalesReportList(PageMap pageMap);
	/**
	 * 获取客户新品销售情况数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public List showCustomerNewGoodsReportList(PageMap pageMap);
	/**
	 * 获取客户新品销售情况数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public int showCustomerNewGoodsReportCount(PageMap pageMap);
	/**
	 * 获取客户新品销售情况合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public Map showCustomerNewGoodsReportSum(PageMap pageMap);
	/**
	 * 获取客户老品销售情况数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public List showCustomerOldGoodsReportList(PageMap pageMap);
	/**
	 * 获取客户老品销售情况数据列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public int showCustomerOldGoodsReportCount(PageMap pageMap);
	/**
	 * 获取客户老品销售情况数据合计
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2014年12月23日
	 */
	public Map showCustomerOldGoodsReportSum(PageMap pageMap);
	
	/**
	 * 获取分司机销售退货情况统计报表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 31, 2014
	 */
	public List getSalesRejectEnterReportList(PageMap pageMap);
	
	/**
	 * 获取分司机销售退货情况统计报表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 31, 2014
	 */
	public int getSalesRejectEnterReportCount(PageMap pageMap);
	
	/**
	 * 获取分司机销售退货情况统计报表合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 31, 2014
	 */
	public BaseSalesReport getSalesRejectEnterReportSum(PageMap pageMap);
	
	/**
	 * 获取分品牌业务员销售回笼考核数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 22, 2015
	 */
	public List getSalesWithdrawnAssessList(PageMap pageMap);
	
	/**
	 * 获取分品牌业务员销售回笼考核数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 22, 2015
	 */
	public int getSalesWithdrawnAssessCount(PageMap pageMap);
	
	/**
	 * 获取分品牌业务员销售回笼考核合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 22, 2015
	 */
	public Map getSalesWithdrawnAssessSum(PageMap pageMap);

    /**
     * 获取客户汇总统计报表数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-10-13
     */
    public List<Map> showCollectReportData(PageMap pageMap);

    /**
     * 获取客户汇总统计报表数量
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-10-13
     */
    public int showCollectReportDataCount(PageMap pageMap);

	/**
	 * 获取汇总统计报表中对应客户的应收金额
	 * @author lin_xx
	 * @date 2017-2-14
	 */
	public BigDecimal getAllunwithdrawnamount(@Param("customerid")String customerid);

    /**
     * 获取客户未销售查询报表数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-12-11
     */
    public List<Map> getCustomerUnsaleQueryReportList(PageMap pageMap);

    /**
     * 获取客户未销售查询报表数量
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-12-11
     */
    public int getCustomerUnsaleQueryReportCount(PageMap pageMap);

    /**
     * 获取客户未核销查询报表合计
     * @param pageMap
     * @return
     */
    public List getCustomerUnsaleQueryReportSum(PageMap pageMap);
    /**
     * 分月汇总销售报表数据
     * @param pageMap
     * @return
     * @author huangzhiqian
     * @date 2016年1月12日
     */
	public List<SaleMonthReport> showMonthSalesReportData(PageMap pageMap);
	/**
	 * 查数量
	 * @param pageMap
	 * @return
	 * @author huangzhiqian
	 * @date 2016年1月13日
	 */
	public int getMonthSalesReportCount(PageMap pageMap);

    /**
     * 获取销售情况基础统计数据
     * @param pageMap
     * @return
     * @author lin_xx
     * @date Aug 17, 2016
     */
    public List getSalesPresentReportData(PageMap pageMap);

    /**
     * 获取人员部门销售统计数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date Aug 17, 2016
     */
    public int getSalesPresentReportDataCount(PageMap pageMap);

	/**
	 * 获取促销活动统计报表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-09-06
	 */
	public List<Map> getSalesPromotionReportData(PageMap pageMap);

	/**
	 * 获取促销活动统计报表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-09-07
	 */
	public int getSalesPromotionReportDataCount(PageMap pageMap);

	/**
	 * 获取促销活动统计报表合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-09-07
	 */
	public Map getSalesPromotionReportDataSum(PageMap pageMap);

	/**
	 * 档期活动折让统计表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-09
	 */
	public List<Map> getSalesScheduleActivityDiscountReportData(PageMap pageMap);

	/**
	 * 档期活动折让统计表数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-09
	 */
	public int getSalesScheduleActivityDiscountReportDataCount(PageMap pageMap);

	/**
	 * 档期活动折让统计表合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date 2016-11-09
	 */
	public Map getSalesScheduleActivityDiscountReportDataSum(PageMap pageMap);

	/**
	 * 查询客户对账单分页数据
	 *
	 * @param pageMap
	 * @return
	 * @author limin
	 * @date Apr 28
	 */
	public List<Map> selectSalesBillStatementList(PageMap pageMap);

	/**
	 * 查询客户对账单分页数据
	 *
	 * @param pageMap
	 * @return
	 * @author limin
	 * @date Apr 28
	 */
	public int selectSalesBillStatementTotalCount(PageMap pageMap);

	/**
	 * 查询客户对账单分页数据
	 *
	 * @param pageMap
	 * @return
	 * @author limin
	 * @date Apr 28
	 */
	public List<Map> selectSalesBillStatementSumData(PageMap pageMap);

	/**
	 * 查询对账单明细分页数据
	 *
	 * @param pageMap
	 * @return
	 * @author limin
	 * @date Apr 28
	 */
	public List<Map> selectSalesBillStatementDetailList(PageMap pageMap);

	/**
	 * 查询对账单明细分页数据
	 *
	 * @param pageMap
	 * @return
	 * @author limin
	 * @date Apr 28
	 */
	public int selectSalesBillStatementDetailTotalCount(PageMap pageMap);

	/**
	 * 查询对账单明细分页数据
	 *
	 * @param pageMap
	 * @return
	 * @author limin
	 * @date Apr 28
	 */
	public List<Map> selectSalesBillStatementDetailSumData(PageMap pageMap);


	/**
	 * 获取厂商毛利率统计表数据
	 *
	 * @param pageMap
	 * @return
	 * @author wanghongteng
	 * @date 2017-5-11
	 */
	public List<Map> showSalesSuppliserGrossReportDataList(PageMap pageMap);

	/**
	 * 获取厂商毛利率统计表数据条数
	 *
	 * @param pageMap
	 * @return
	 * @author wanghongteng
	 * @date 2017-5-11
	 */
	public int showSalesSuppliserGrossReportDataCount(PageMap pageMap);

	/**
	 * 获取厂商毛利率统计表数据合计
	 *
	 * @param pageMap
	 * @return
	 * @author wanghongteng
	 * @date 2017-5-11
	 */
	public List<Map> showSalesSuppliserGrossReportDataSum(PageMap pageMap);

	/**
	 * 根据日期获取客户的销售金额,按单据汇总的数据
	 * @param date
	 * @param salesDataType
	 * @param salesdept
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List<Map> getCustomerSalesAmountByDateForThird(@Param("date")String date,@Param("salesDataType")String salesDataType,@Param("salesdept")String salesdept);

	/**
	 * 获取销售情况基础分析数据
	 * @param pageMap
	 * @return
	 * @author chenwei
	 * @date Nov 12, 2013
	 */
	public List showBaseSalesAnalysisReportData(PageMap pageMap);

	/**
	 * 获取销售情况基础统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei
	 * @date Nov 12, 2013
	 */
	public int showBaseSalesAnalysisReportDataCount(PageMap pageMap);
	/**
	 * 获取销售情况基础分析数据
	 * @param map
	 * @return
	 * @author chenwei
	 * @date Nov 12, 2013
	 */
	public List showSalesAnalysisReportSubjectDetailPage(@Param("con")Map map);

	/**
	 * 德阳获取销售情况统计报表数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Feb 05, 2018
	 */
	public List showDyBaseSalesReportData(PageMap pageMap);

	/**
	 * 德阳获取销售情况统计报表数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Feb 05, 2018
	 */
	public int showDyBaseSalesReportCount(PageMap pageMap);

	/**
	 * 查询吉马销售日报表数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public List showJmSalesTargetDayReportList(PageMap pageMap);

	/**
	 * 查询吉马销售日报表数据
	 * @param pageMap
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public int showJmSalesTargetDayReportCount(PageMap pageMap);

	/**
	 * 吉马销售日报表合计
	 * @param pageMap
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Mar 21, 2018
	 */
	public Map showJmSalesTargetDayReportSum(PageMap pageMap);

	/**
	 * 添加销售目标
	 * @param jmSalesTarget
	 * @return int
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public int insertJmSalesTarget(JmSalesTarget jmSalesTarget);

	/**
	 * 获取用户的销售目标(吉马报表)
	 * @param personnelid
	 * @param targetdate
	 * @return com.hd.agent.report.model.JmSalesTarget
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public JmSalesTarget getJmSalesTarget(@Param("personnelid") String personnelid,@Param("targetdate") String targetdate);

	/**
	 * 修改用户的销售目标(吉马报表)
	 * @param jmSalesTarget
	 * @return com.hd.agent.report.model.JmSalesTarget
	 * @throws
	 * @author luoqiang
	 * @date Mar 19, 2018
	 */
	public int updateJmSalesTarget(JmSalesTarget jmSalesTarget);


}