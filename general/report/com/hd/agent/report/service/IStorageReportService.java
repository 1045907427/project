/**
 * @(#)IStorageReportService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 31, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 库存报表service
 * @author chenwei
 */
public interface IStorageReportService {
	/**
	 * 根据日期生成仓库进销存汇总报表
	 * @param date
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jul 31, 2013
	 */
	public boolean addStorageInoutReportByDay(String date) throws Exception;
	/**
	 * 获取仓库出入库统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 14, 2013
	 */
	public PageData showInOutReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取进销存汇总统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 21, 2013
	 */
	public PageData showBuySaleReportData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取进销存汇总统计数据(分月)
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 19, 2014
	 */
	public PageData showBuySaleReportMonthData(PageMap pageMap)throws Exception;
	/**
	 * 获取出入库流水账数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 9, 2013
	 */
	public PageData showInOutFlowListData(PageMap pageMap) throws Exception;
	/**
	 * 获取代配送出入库流水账数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date july 19, 2016
	 */
	public PageData showDeliveryInOutFlowListData(PageMap pageMap) throws Exception;
	/**
	 * 获取盘点报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 17, 2013
	 */
	public PageData showCheckReportData(PageMap pageMap) throws Exception;
	/**
	 * 每日进销存记录
	 * @param yesdate
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 14, 2013
	 */
	public boolean  addStorageNumEveryday(String yesdate) throws Exception;

	/**
	 * 每日进销存数据插入（鸿都）
	 * 成本价：realcostprice
	 * @param yesdate
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-19
	 */
	public boolean addStorageNumEverydayHD(String yesdate)throws Exception;
	
	/**
	 * 每月进销存
	 * @param date
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 16, 2014
	 */
	public boolean addStorageNumEveryMonth(String date)throws Exception;

	/**
	 * 每月进销存数据插入（鸿都）
	 * 成本价：realcostprice
	 * @param date
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-02-19
	 */
	public boolean addStorageNumEveryMonthHD(String date)throws Exception;

	/**
	 * 生成上月份的库存周转天数报表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 27, 2013
	 */
	public boolean addStorageRevolutionDaysReport() throws Exception;
	/**
	 * 获取库存周转天数报表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 28, 2013
	 */
	public PageData showStorageRevolutionDaysData(PageMap pageMap) throws Exception;

    /**
     * 获取库存周转天数按具体日期查询统计报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 14, 2016
     */
    public PageData showStorageRevolutionDaysBySection(PageMap pageMap) throws Exception;

    /**
     * 获取库存周转天数报表销售金额流水
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 14, 2016
     */
    public PageData showStorageRevolutionDetails(PageMap pageMap) throws Exception;
	/**
	 * 获取库存平均金额统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Nov 29, 2013
	 */
	public PageData showStorageAvgAmountData(PageMap pageMap) throws Exception;
	/**
	 * 生成资金占用报表数据
	 * @param date
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public boolean addCapitalOccupyReport(String date) throws Exception;
	/**
	 * 获取资金占用金额数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public List showCapitalOccupyListData(PageMap pageMap) throws Exception;
	/**
	 * 获取资金占用平均金额数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 3, 2013
	 */
	public PageData showCapitalOccupyAvgListData(PageMap pageMap) throws Exception;
	/**
	 * 获取库存统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 6, 2013
	 */
	public PageData showStorageAmountReportListData(PageMap pageMap) throws Exception;

	/**
	 * 获取盈亏调账单报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 21, 2014
	 */
	public PageData getAdjustmentReportData(PageMap pageMap) throws Exception;
	/**
	 * 获取库存统计数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Dec 6, 2013
	 */
	public List<Map> showStorageAmountReportList(PageMap pageMap) throws Exception;

    /**
     * 获取客户装车次数统计报表数据
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-03-20
     */
    public PageData getCustomerCarNumReportData(PageMap pageMap)throws Exception;
    /**
     * 获取仓库收发存报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2015年8月18日
     */
    public PageData showStorageRecSendList(PageMap pageMap) throws Exception;
	/**
	 * 获取指定仓库的商品收发存明细
	 * @return
	 * @throws Exception
	 * @author wanghongteng	 
	 * @date 11 9, 2015
	 */
    public PageData showStorageRecSendReportDetailList(PageMap pageMap) throws Exception;
    /**
     * 获取仓库收发存报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2015年8月18日
     */
    public List showReceivenumDetailList(String goodsid,String storageid,String businessdate1,String businessdate2) throws Exception;
    /**
     * 获取仓库收发存报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei 
     * @date 2015年8月18日
     */
    public List showSendnumDetailPageList(String goodsid,String storageid,String businessdate1,String businessdate2) throws Exception;
    
    
    
    /**
     * 获取实际进销存汇总统计数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-11-17
     */
    public PageData getRealBuySaleReportDayData(PageMap pageMap)throws Exception;

    /**
     * 获取每月实际进销存汇总统计数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-11-17
     */
    public PageData getRealBuySaleReportMonthData(PageMap pageMap)throws Exception;

    /**
     * 每日进销存数据截断重新生成
     * @param date
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-22
     */
    public boolean clearResetBuySaleReportData(String date)throws Exception;

    /**
     * 每月进销存数据截断重新生成
     * @param date
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-25
     */
    public boolean clearResetBuySaleReportMonthData(String date)throws Exception;

    /**
     * 实际每日进销存数据截断重新生成
     * @param date
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-28
     */
    public boolean clearResetRealBuySaleReportData(String date)throws Exception;

    /**
     * 实际每月进销存数据截断重新生成
     * @param date
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-28
     */
    public boolean clearResetRealBuySaleReportMonthData(String date)throws Exception;

	/**
	 * 添加商品库存每天记录表
	 * @param yesdate
	 * @return
	 * @throws Exception
	 */
	public boolean addReportStorageBakEveryday(String yesdate)throws Exception;
	
	/**
	 * 获取发货人列表
	 * @return
	 * @throws Exception
	 * @author wanghongteng 
	 * @date 2016-4-7
	 */
	public PageData showDispatchUserListData(PageMap pageMap) throws Exception;

    /**
     * 查询配送情况报表
     * @return
     * @throws Exception
     * @author LIN_XX
     * @date 2016-6-21
     */
    public PageData getDriverDeliveryReportData(PageMap pageMap) throws Exception;

    /**
     * 查询业务员发货报表
     * @return
     * @throws Exception
     * @author LIN_XX
     * @date 2016-7-8
     */
    public PageData showSalesmanDeliveryData(PageMap pageMap) throws Exception;

    /**
     * 查询司机退货原始明细表
     * @return
     * @throws Exception
     * @author LIN_XX
     * @date 2016-8-8
     */
    public PageData showDriverRejectEnterData(PageMap pageMap) throws Exception;
    /**
     * 发货单商品及折扣记录添加到临时表
     * @return
     * @throws Exception
     * @author LIN_XX
     * @date 2016-8-10
     */
    public boolean addSaleoutDetailWithBrandDiscount() throws Exception;


    /**
     * 获取仓库库存商品盘点记录数据
     * @param pageMap
     * @return
     * @throws Exception
     */
    public PageData getStorageGoodsCheckLogData(PageMap pageMap) throws Exception;

	/**
	 * 获取库存树状报表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2018-2-5
	 */
	public PageData showStorageTreeReportListData(PageMap pageMap) throws Exception;


}

