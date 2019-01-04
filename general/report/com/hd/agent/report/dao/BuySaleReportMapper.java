package com.hd.agent.report.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: PXX
 * Date: 2015/12/25
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public interface BuySaleReportMapper {

    /**
     * 截断每日进销存表数据
     * @return
     */
    public void clearBuySaleReportData();

    /**
     * 根据业务日期获取该日进销存是否有数据
     * @param yesdate
     * @return
     */
    public int getBuySaleReportDayCountByDate(@Param("yesdate")String yesdate);

    /**
     * 添加每日进销存数据
     * @param map
     * @return
     */
    public int addBuySaleReportDay(Map map);

    /**
     * 更新进销存中成本价 取库存商品成本价
     * @param date
     * @return
     */
    public int updateBuySaleReprotDayPrice(@Param("date")String date);
    /**
     * 添加每日进销存数据（鸿都）
     * 成本价：realcostprice
     * @param map
     * @return
     */
    public int addBuySaleReportDayHD(Map map);

    /**
     * 截断每月进销存表数据
     * @return
     */
    public void clearBuySaleReportMonthData();

    /**
     * 根据业务日期获取该月进销存是否有数据
     * @param yesmonth
     * @return
     */
    public int getBuySaleReportMonthCountByDate(@Param("yesmonth")String yesmonth);

    /**
     * 添加每月进销存数据
     * @param map
     * @return
     */
    public int addBuySaleReportMonth(Map map);

    /**
     * 更新每月进销存中的价格 取库存中的商品成本价
     * @return
     */
    public int updateBuySaleReprotMonthPrice(@Param("daymonth")String daymonth);
    /**
     * 添加每月进销存数据(鸿都)
     * 成本价：realcostprice
     * @param map
     * @return
     */
    public int addBuySaleReportMonthHD(Map map);

    /**
     * 截断实际每日进销存表数据
     * @return
     */
    public void clearRealBuySaleReportData();

    /**
     * 根据业务日期获取该日实际进销存是否有数据
     * @param yesdate
     * @return
     */
    public int getRealBuySaleReportDayCountByDate(@Param("yesdate")String yesdate);

    /**
     * 添加实际每日进销存数据
     * @param map
     * @return
     */
    public int addRealBuySaleReportDay(Map map);

    /**
     * 更新实际每日进销存中成本价 取库存中的商品成本价
     * @param date
     * @return
     */
    public int updateRealBuySaleReprotDayPrice(@Param("date")String date);
    /**
     * 添加实际每日进销存数据（鸿都）
     * 成本价：realcostprice
     * @param map
     * @return
     */
    public int addRealBuySaleReportDayHD(Map map);

    /**
     * 截断实际每月进销存表数据
     * @return
     */
    public void clearRealBuySaleReportMonthData();

    /**
     * 根据业务日期获取该月实际进销存是否有数据
     * @param yesmonth
     * @return
     */
    public int getRealBuySaleReportMonthCountByDate(@Param("yesmonth")String yesmonth);

    /**
     * 添加实际每日进销存数据
     * @param map
     * @return
     */
    public int addRealBuySaleReportMonth(Map map);

    /**
     * 更新实际每月进销存中成本价 取库存中的商品成本价
     * @param daymonth
     * @return
     */
    public int updateRealBuySaleReprotMonthPrice(@Param("daymonth")String daymonth);
    /**
     * 添加实际每日进销存数据（鸿都）
     * 成本价：realcostprice
     * @param map
     * @return
     */
    public int addRealBuySaleReportMonthHD(Map map);
}
