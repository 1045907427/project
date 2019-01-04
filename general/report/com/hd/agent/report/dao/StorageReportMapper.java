/**
 * @(#)StorageReportMapper.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.report.model.SaleoutDetailAndDiscount;
import com.hd.agent.report.model.*;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;

/**
 * 
 * 库存报表dao
 * @author chenwei
 */
public interface StorageReportMapper {
	/**
	 * 获取仓库出入库统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public List showInOutReportData(PageMap pageMap);
	/**
	 * 根据日期获取该天的仓库出入库数据
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public List showInOutReportDataByDate(@Param("date")String date);
	/**
	 * 根据日期和商品编码获取该天的商品仓库出入库数据
	 * @param date
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public StorageInOutReport getInOutReportDataByDateAndGoodsid(@Param("date")String date,@Param("goodsid")String goodsid);
	/**
	 * 根据日期和商品编码
	 * 获取早于该日期且离该日期最近的商品仓库出入库数据（包含该日期）
	 * @param date
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public StorageInOutReport getInOutReportDataInLastByDateAndGoodsid(@Param("date")String date,@Param("goodsid")String goodsid,@Param("storageid")String storageid);
	/**
	 * 根据日期和商品编码
	 * 获取晚于该日期且离该日期最近的商品仓库出入库数据（包含该日期）
	 * @param date
	 * @param goodsid
	 * @param storageid
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public StorageInOutReport getInOutReportDataInNearByDateAndGoodsidStorageid(@Param("date")String date,@Param("goodsid")String goodsid,@Param("storageid")String storageid);
	/**
	 * 根据日期和商品编码
	 * 获取晚于该日期且离该日期最近的商品仓库出入库数据（包含该日期）
	 * @param date
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public StorageInOutReport getInOutReportDataInNearByDateAndGoodsid(@Param("date")String date,@Param("goodsid")String goodsid);
	/**
	 * 批量插入数据
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public int addStorageInOutReportBatch(List list);
	/**
	 * 根据日期删除仓库出入库数据
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public int deleteStorageInOutReportByDate(String date);
	/**
	 * 获取仓库出入库数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public List getStorageInOutReportSumDataList(PageMap pageMap);
	/**
	 * 获取仓库出入库数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public int getStorageInOutReportSumDataCount(PageMap pageMap);
	/**
	 * 获取仓库出入库合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public StorageInOutReport getStorageInOutReportSumData(PageMap pageMap);
	/**
	 * 获取进销存初始化合计数据（只取相同仓库相同商品 日期最早的数据）
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public StorageInOutReport getStorageInOutReportInitSumData(PageMap pageMap);
	/**
	 * 获取仓库出入库初始化合计数据（只取相同仓库相同商品 日期最晚的数据）
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public StorageInOutReport getStorageInOutReportEndSumData(PageMap pageMap);
	/**
	 * 获取进销存汇总数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public List showBuySaleReportData(PageMap pageMap);
	/**
	 * 获取进销存汇总数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public int showBuySaleReportDataCount(PageMap pageMap);

    /**
     * 获取进销存汇总数据合计
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-04-08
     */
    public List<StorageBuySaleReport> showBuySaleReportDataSum(PageMap pageMap);

	/**
	 * 获取进销存总数据（分月）
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 18, 2014
	 */
	public List showBuySaleReportMonthData(PageMap pageMap);
	
	/**
	 * 获取进销存总数居数量（分月）
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 18, 2014
	 */
	public int showBuySaleReportDataMonthCount(PageMap pageMap);
	
	/**
	 * 进销存总数据合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 19, 2014
	 */
	public List showBuySaleReportDataMonthSum(PageMap pageMap);
	/**
	 * 获取出入库流水账数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 9, 2013
	 */
	public List showInOutFlowListData(PageMap pageMap);
	/**
	 * 获取出入库流水数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 9, 2013
	 */
	public int showInOutFlowListCount(PageMap pageMap);
	/**
	 * 获取出入库的流水合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 9, 2013
	 */
	public StorageInOutFlow showInOutFlowListSum(PageMap pageMap);
	
	/**
	 * 获取代配送出入库流水账数据
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date july 19, 2016
	 */
	public List showDeliveryInOutFlowListData(PageMap pageMap);
	/**
	 * 获取代配送出入库流水数量
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date july 19, 2016
	 */
	public int showDeliveryInOutFlowListCount(PageMap pageMap);
	/**
	 * 获取代配送出入库的流水合计数据
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date july 19, 2016
	 */
	public StorageInOutFlow showDeliveryInOutFlowListSum(PageMap pageMap);
	/**
	 * 获取盘点报表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public List showCheckReportDataList(PageMap pageMap);
	/**
	 * 获取盘点报表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public int showCheckReportDataCount(PageMap pageMap);

	/**
	 * 保存库存记录
	 * @param date
     * @param isStorageAccount
	 * @return
	 * @author chenwei 
	 * @date Mar 11, 2014
	 */
	public int addStorageBakEverday(@Param("date")String date,@Param("isStorageAccount")String isStorageAccount);
	
	/**
	 * 生成库存周转天数月报表
	 * @param year
	 * @param mon
	 * @param begindate
	 * @param enddate
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public int addStorageRevolutionDaysReport(@Param("year")int year,@Param("mon")int mon,@Param("begindate")String begindate,@Param("enddate")String enddate,@Param("mondays")int mondays);
	/**
	 * 删除库存周转天数月报表
	 * @param year
	 * @param mon
	 * @return
	 * @author chenwei 
	 * @date Mar 10, 2014
	 */
	public int deleteStorageRevolutionDaysReport(@Param("year")int year,@Param("mon")int mon);
	/**
	 * 获取库存周转天数数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public List showStorageRevolutionDaysData(PageMap pageMap);

	/**
	 * 获取库存周转天数数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public int showStorageRevolutionDaysCount(PageMap pageMap);

    public List<Map> showStorageRevolutionSum(PageMap pageMap);

    /**
     * 获取库存周转天数业务日期查询数据列表
     * @param pageMap
     * @return
     * @author lin_xx
     * @date June 14, 2016
     */
    public List showStorageRevolutionDaysBySection(PageMap pageMap);

    /**
     * 获取库存周转天数业务日期查询数据数量
     * @param pageMap
     * @return
     * @author lin_xx
     * @date June 14, 2016
     */
    public int showStorageRevolutionDaysBySectionCount(PageMap pageMap);

    /**
     * 获取库存周转天数报表销售金额流水数据
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 14, 2016
     */
    public List showStorageRevolutionDetails(@Param("begindate")String begindate,@Param("enddate")String enddate,@Param("goodsid")String goodsid);

    /**
     * 根据销售金额获取库存周转天数数据明细
     * @param pageMap
     * @return
     * @author lin_xx
     * @date June 14, 2016
     */
    public List showStorageRevolutionDetails(PageMap pageMap);

    /**
     * 根据销售金额获取库存周转天数数据明细数量
     * @param pageMap
     * @return
     * @author lin_xx
     * @date June 14, 2016
     */
    public int showStorageRevolutionDetailsCount(PageMap pageMap);

	/**
	 * 获取库存平均金额统计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 29, 2013
	 */
	public List showStorageAvgAmountData(PageMap pageMap);
	/**
	 * 获取库存平均金额统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Nov 29, 2013
	 */
	public int showStorageAvgAmountCount(PageMap pageMap);
	/**
	 * 生成资金占用数据
	 * @param date
	 * @return
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public int addCapitalOccupyReport(@Param("date")String date);
	/**
	 * 获取资金占用金额数据
	 * @return
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public List showCapitalOccupyListData(PageMap pageMap);
	/**
	 * 获取资金占用金额数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public int showCapitalOccupyListCount(PageMap pageMap);
	/**
	 * 获取资金占用平均金额数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public List showCapitalOccupyAvgListData(PageMap pageMap);
	/**
	 * 获取资金占用平均金额数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public int showCapitalOccupyAvgListCount(PageMap pageMap);
	/**
	 * 获取资金占用平均金额 合计数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 4, 2013
	 */
	public CapitalOccupyReport showCapitalOccupyAvgSumData(PageMap pageMap);
	/**
	 * 获取库存统计数据列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 6, 2013
	 */
	public List showStorageAmountReportListData(PageMap pageMap);
	/**
	 * 获取库存统计数据数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Dec 6, 2013
	 */
	public int showStorageAmountReportListCount(PageMap pageMap);
	/**
	 * 获取调账单报表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 21, 2014
	 */
	public List getAdjustmentReportData(PageMap pageMap);
	/**
	 * 获取调账单报表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Apr 21, 2014
	 */
	public int getAdjustmentReportDataCount(PageMap pageMap);

    /**
     * 获取客户装车次数列表数据
     * @param pageMap
     * @return
     * @author panxiaoxiao
     * @date 2015-03-20
     */
    public List<Map<String,Object>> getCustomerCarNumReportList(PageMap pageMap);

    /**
     * 获取客户装车次数数量
     * @param pageMap
     * @return
     */
    public int getCustomerCarNumReportCount(PageMap pageMap);
    /**
     * 获取库存初始化日期
     * @return
     * @author chenwei 
     * @date 2015年8月19日
     */
    public String getStorageInitDate();
    /**
     * 获取仓库收发存报表数据
     * @param pageMap
     * @return
     * @author chenwei 
     * @date 2015年8月18日
     */
    public List showStorageRecSendList(PageMap pageMap);
    /**
     * 获取仓库指定商品收发存报表明细数据
     * @param pageMap
     * @return
     * @author chenwei 
     * @date 2015年8月18日
     */
    public List showStorageRecSendDetailList(PageMap pageMap);
    /**
     * 获取仓库收发存报表数量
     * @param pageMap
     * @return
     * @author chenwei 
     * @date 2015年8月18日
     */
    public int showStorageRecSendCount(PageMap pageMap);
    /**
     * 获取仓库收发存报表明细数量
     * @param pageMap
     * @return
     * @author chenwei 
     * @date 2015年8月18日
     */
    public int showStorageRecSendDetailCount(PageMap pageMap);
    /**
     * 获取仓库收发存报表合计数据
     * @param pageMap
     * @return
     * @author chenwei 
     * @date 2015年8月19日
     */
    public List showStorageRecSendSum(PageMap pageMap);

    /**
     * 获取实际进销存汇总统计数据
     * @param pageMap
     * @return
     */
    public List<StorageBuySaleReport> getRealBuySaleReportDayData(PageMap pageMap);

    /**
     * 获取实际进销存汇总统计数量
     * @param pageMap
     * @return
     */
    public int getRealBuySaleReportDayDataCount(PageMap pageMap);

    /**
     * 获取实际进销存汇总统计合计
     * @param pageMap
     * @return
     */
    public List<StorageBuySaleReport> getRealBuySaleReportDayDataSum(PageMap pageMap);

    /**
     * 获取每月实际进销存汇总统计数据
     * @param pageMap
     * @return
     */
    public List<StorageBuySaleReport> getRealBuySaleReportMonthData(PageMap pageMap);

    /**
     * 获取每月实际进销存汇总统计数量
     * @param pageMap
     * @return
     */
    public int getRealBuySaleReportMonthDataCount(PageMap pageMap);

    /**
     * 获取每月实际进销存汇总统计合计
     * @param pageMap
     * @return
     */
    public List<StorageBuySaleReport> getRealBuySaleReportMonthDataSum(PageMap pageMap);
    
    /**
	 * 获取收发存中入库明细
	 * @param 
	 * @return
	 * @author wanghongteng
	 * @date 2015,1 26
	 */
	public List showReceivenumDetailList(@Param("goodsid")String goodsid,@Param("storageid")String storageid,@Param("businessdate1")String businessdate1,@Param("businessdate2")String businessdate2);
	
	/**
	 * 获取收发存中出库明细
	 * @param 
	 * @return
	 * @author wanghongteng 
	  * @date 2015,1 26
	 */
	public List showSendnumDetailPageList(@Param("goodsid")String goodsid,@Param("storageid")String storageid,@Param("businessdate1")String businessdate1,@Param("businessdate2")String businessdate2);
    
	/**
	 * 获取发货人列表
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2016-4-7
	 */
	public List showDispatchUserListData(PageMap pageMap);

	/**
	 * 获取发货人列表数量
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2016-4-7
	 */
	public int showDispatchUserListDataCount(PageMap pageMap);
	
	/**
	 * 获取发货人列表合计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author 王洪腾 
	 * @date 3 22, 2016
	 */
	public List showDispatchUserListDataSum(PageMap pageMap);

    /**
     * 查询配送情况报表
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-6-21
     */
    public List getDriverDeliveryReportData(PageMap pageMap);

    public int getDriverDeliveryReportDataCount(PageMap pageMap);

    /**
     * 获取配送情况报表合计
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-6-21
     */
    public List getDriverDeliveryReportDataSum(PageMap pageMap);
    /**
     * 查询业务员发货报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-6-21
     */
    public List getSalesmanDeliveryReportData(PageMap pageMap);

    /**
     * 根据条件查询发货单中的折扣
     * @param map
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-6-21
     */
    public Map getSaleoutDiscountInfo(Map map);

    /**
     * 业务员发货报表数据数量
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-6-21
     */
    public int getSalesmanDeliveryReportDataCount(PageMap pageMap);
    /**
     * 业务员发货报表数据合计
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-6-21
     */
    public List<Map> getSalesmanDeliveryReportDataSum(PageMap pageMap);
    /**
     * 业务员发货报表 折扣合计
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-6-21
     */
    public Map getSaleoutDiscountSum(PageMap pageMap);
    /**
     * 查询司机退货原始明细表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-8-8
     */
    public List getDriverRejectEnterReportData(PageMap pageMap);
    /**
     * 查询司机退货原始明细表数据合计
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-8-8
     */
    public int getDriverRejectEnterReportDataCount(PageMap pageMap);
    /**
     * 查询司机退货原始明细表数据 总和
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-8-8
     */
    public List<Map> getDriverRejectEnterReportDataSum(PageMap pageMap);
    /**
     * 根据销售订单获取状态是审核通过或者关闭的发货单(吉马旧数据整理)
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016-8-11
     */
    public List<Map> getSaleoutGoodsByClosedOrder();

    /**
     * 组装含有商品折扣的发货单明细（吉马）
     * @param saleoutDetailAndDiscount
     * @author lin_xx
     * @date 2016-8-11
     * @return
     */
    public int addSaleoutAndRejectEnterForJM(SaleoutDetailAndDiscount saleoutDetailAndDiscount);

	/**
	 * 获取库存树状报表数据
	 * @param pageMap
	 * @return
	 * @author wanghongteng
	 * @date 2016-4-15
	 */
	public List getStorageTreeReportListData(PageMap pageMap);

}

