package com.hd.agent.salestarget.service.impl;

import com.hd.agent.account.dao.SalesBillCheckMapper;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.CustomerSort;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.salestarget.dao.SalesTargetInputMapper;
import com.hd.agent.salestarget.model.SalesTargetGrossTraceData;
import com.hd.agent.salestarget.model.SalesTargetMonthAnalyzeData;
import com.hd.agent.salestarget.model.SalesTargetTraceData;
import com.hd.agent.salestarget.service.ISalesTargetReportService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by master on 2016/8/7.
 */
public class SalesTargetReportServiceImpl extends BaseFilesServiceImpl implements ISalesTargetReportService{

    public SalesTargetInputMapper salesTargetInputMapper;

    public SalesTargetInputMapper getSalesTargetInputMapper() {
        return salesTargetInputMapper;
    }

    public void setSalesTargetInputMapper(SalesTargetInputMapper salesTargetInputMapper) {
        this.salesTargetInputMapper = salesTargetInputMapper;
    }
    private SalesBillCheckMapper salesBillCheckMapper;

    public SalesBillCheckMapper getSalesBillCheckMapper() {
        return salesBillCheckMapper;
    }

    public void setSalesBillCheckMapper(SalesBillCheckMapper salesBillCheckMapper) {
        this.salesBillCheckMapper = salesBillCheckMapper;
    }

    @Override
    public PageData showSalesTargetTraceReportData(PageMap pageMap) throws Exception{
        Map condition=pageMap.getCondition();

        PageData pageData=null;
        //要返回的结果数据
        List<Map> reportDataList=new ArrayList<Map>();
        //对比年份
        String year=(String)condition.get("year");
        //月
        String querymonth=(String) condition.get("month");
        //业务日期
        String businessdate=(String) condition.get("businessdate");
        String salesuserid=(String)condition.get("salesuserid");
        String customersort=(String)condition.get("customersort");
        String brandid=(String)condition.get("brandid");


        Integer iQueryMonth=null;
        if(null!=querymonth && !"".equals(querymonth.trim()) && StringUtils.isNumeric(querymonth.trim())){
            iQueryMonth=Integer.parseInt(querymonth.trim());
        }else{
            querymonth="";
        }
        querymonth=querymonth.trim();

        if(null==year ||"".equals(year.trim()) || !StringUtils.isNumeric(year.trim())){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }
        int iYear=Integer.parseInt(year.trim());

        String[] businessdateArr=businessdate.split("-");
        String businessYear=businessdateArr[0];
        String businessMonth=businessdateArr[1];
        String businessDay=businessdateArr[2];
        int iBusinessYear=Integer.parseInt(businessYear.trim());
        //int iBusinessMonth=Integer.parseInt(businessYear.trim());
        //传入年比当对比年大于，返回空
        if(iYear-iBusinessYear>1){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }

        Map timeScheduleMap = getCalcYearAndMonthTimeSchedule(businessdate);
        BigDecimal monthTimeSchedule=null;
        BigDecimal yearTimeSchedule=null;
        if(timeScheduleMap!=null){
            monthTimeSchedule=(BigDecimal)timeScheduleMap.get("monthTimeSchedule");
            if(monthTimeSchedule==null){
                monthTimeSchedule=BigDecimal.ZERO;
            }
            yearTimeSchedule=(BigDecimal)timeScheduleMap.get("yearTimeSchedule");
            if(yearTimeSchedule==null){
                yearTimeSchedule=BigDecimal.ZERO;
            }
        }

        //初始化返回结果数据
        getInitSalesTargetTraceReportData(reportDataList,iBusinessYear,iYear);
        SalesTargetTraceData salesTargetTraceDataSum =new SalesTargetTraceData();

        salesTargetTraceDataSum.setBusinessdate(businessdate);
        salesTargetTraceDataSum.setYear(businessYear);
        salesTargetTraceDataSum.setLastyear(year);
        salesTargetTraceDataSum.setQuerymonth(querymonth);
        salesTargetTraceDataSum.setMonthtimeschedule(monthTimeSchedule);
        salesTargetTraceDataSum.setYeartimeschedule(yearTimeSchedule);

        String yearmonth=businessYear ;
        String lastYearmonth=iYear+"";
        String sMonth="";
        //当前月
        //String currentMonthString= CommonUtils.getCurrentMonthStr();

        for(int iMonth=1;iMonth<13;iMonth++){
            if(iMonth<10){
                sMonth="0"+iMonth;
            }else{
                sMonth=""+iMonth;
            }
            yearmonth=businessYear+"-"+sMonth;
            lastYearmonth=iYear+"-"+sMonth;

            SalesTargetTraceData salesTargetTraceData =new SalesTargetTraceData();
            //更新日期
            salesTargetTraceData.setBusinessdate(businessdate);
            salesTargetTraceData.setBusinessyear(businessYear);
            salesTargetTraceData.setBusinessmonth(businessMonth);
            salesTargetTraceData.setBusinessday(businessDay);

            salesTargetTraceData.setSalesuerid(salesuserid);
            salesTargetTraceData.setCustomersort(customersort);
            salesTargetTraceData.setBrandid(brandid);
            salesTargetTraceData.setYearmonth(yearmonth);
            salesTargetTraceData.setYear(businessYear);
            salesTargetTraceData.setIyear(iBusinessYear);
            salesTargetTraceData.setLastyearmonth(lastYearmonth);
            salesTargetTraceData.setMonth(sMonth);
            salesTargetTraceData.setImonth(iMonth);
            salesTargetTraceData.setQuerymonth(querymonth);
            salesTargetTraceData.setIquerymonth(iQueryMonth);

            salesTargetTraceData.setQnxs(BigDecimal.ZERO);
            salesTargetTraceData.setJndyxsmb(BigDecimal.ZERO);
            salesTargetTraceData.setJndexsmb(BigDecimal.ZERO);
            salesTargetTraceData.setJnxs(BigDecimal.ZERO);
            salesTargetTraceData.setQnxslj(BigDecimal.ZERO);
            salesTargetTraceData.setJndyxsmblj(BigDecimal.ZERO);
            salesTargetTraceData.setJndexsmblj(BigDecimal.ZERO);
            salesTargetTraceData.setJnxslj(BigDecimal.ZERO);
            //月达成率-第一目标
            salesTargetTraceData.setYdcl_dyxsmb(BigDecimal.ZERO);
            //月达成率-第二目标
            salesTargetTraceData.setYdcl_dexsmb(BigDecimal.ZERO);
            //累计达成率-第一目标
            salesTargetTraceData.setLjdcl_dyxsmb(BigDecimal.ZERO);
            //累计达成率-第二目标
            salesTargetTraceData.setLjdcl_dexsmb(BigDecimal.ZERO);
            //月差异额-第一目标
            salesTargetTraceData.setYcye_dyxsmb(BigDecimal.ZERO);
            //月差异额-第二目标
            salesTargetTraceData.setYcye_dexsmb(BigDecimal.ZERO);
            //累计差异额-第一目标
            salesTargetTraceData.setLjcye_dyxsmb(BigDecimal.ZERO);
            //累计差异额-第二目标
            salesTargetTraceData.setLjcye_dexsmb(BigDecimal.ZERO);
            //较15年同期-月
            salesTargetTraceData.setJqntq_yxsb(BigDecimal.ZERO);
            //较15年同期-累计
            salesTargetTraceData.setJqntq_ljxsb(BigDecimal.ZERO);
            //计算
            updateCalcSalesTargetTraceBasicData(salesTargetTraceData,salesTargetTraceDataSum);
            /*
            if(iQueryMonth!=null){
                if( iMonth<=iQueryMonth){
                    //计算
                    updateCalcSalesTargetTraceBasicData(salesTargetTraceData,salesTargetTraceDataSum);
                }else{
                }
            }else{
                //计算
                updateCalcSalesTargetTraceBasicData(salesTargetTraceData,salesTargetTraceDataSum);
            }
            */

            setIntoSalesTargetTraceReportData(reportDataList, salesTargetTraceData);
        }

        ///计算按月合计时金额
        updateCalcSalesTargetTraceReportCommonData(salesTargetTraceDataSum);
        //设置合计数据
        updateSumSalesTargetTraceReportTotal(reportDataList, salesTargetTraceDataSum);

        int total=0;

        pageData=new PageData(total, reportDataList, pageMap);

        return pageData;
    }

    /**
     * 初始化销售目标跟踪报表 数据汇总
     * @param list
     * @param iCurrentYear
     * @param iLastYear
     * @author zhanghonghui
     * @date 2014-11-29
     */
    private void getInitSalesTargetTraceReportData(List<Map> list,int iCurrentYear,int iLastYear) throws Exception{
        Map map=new LinkedHashMap();
        //如果有代码存在，则使用代码表数据
        List<SysCode> sysCodeList=getBaseSysCodeMapper().getSysCodeListForeign("SalesTargetTraceReport");
        if(null !=sysCodeList && sysCodeList.size()>0){
            for(SysCode item : sysCodeList){
                map=new LinkedHashMap();
                map.put("subject",item.getCode());
                if(item.getCode().startsWith("QN") || item.getCode().startsWith("JQN") ){
                    map.put("subjectname", item.getCodename().replace("{XX}",iLastYear+""));
                }else if(item.getCode().startsWith("JN")){
                    map.put("subjectname", item.getCodename().replace("{XX}",iCurrentYear+""));
                }else{
                    map.put("subjectname", item.getCodename());
                }

                for(int i=1;i<13;i++){
                    map.put("month_"+i, 0.00);
                }
                list.add(map);
            }
            map.put("summarycolumn",0.00);
        }
    }
    /**
     * 更新、计算并组装来自各种接口处的数据
     * @param salesTargetTraceData 数据
     * @param salesTargetTraceData 合计数据
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-8-2
     */
    private void updateCalcSalesTargetTraceBasicData(SalesTargetTraceData salesTargetTraceData,
                                                     SalesTargetTraceData salesTargetTraceDataSum) throws Exception{


        int iBusinessMonth=Integer.parseInt(salesTargetTraceData.getBusinessmonth());

        /*=========去年的销售金额 开始===========*/
        Map queryMap=new HashMap();
        queryMap.put("yearmonth",salesTargetTraceData.getLastyearmonth());
        queryMap.put("salesuserid",salesTargetTraceData.getSalesuerid());
        queryMap.put("customersort",salesTargetTraceData.getCustomersort());
        queryMap.put("brandid",salesTargetTraceData.getBrandid());

        Map queryResultMap=new HashMap();
        queryResultMap=getSalesamountMap(queryMap); //去年销售金额

        if(null!=queryResultMap){
            salesTargetTraceData.setQnxs((BigDecimal)queryResultMap.get("salesamount"));
        }
        if(salesTargetTraceData.getQnxs()==null){
            salesTargetTraceData.setQnxs(BigDecimal.ZERO);
        }
        //汇总数据去年销售合计
        if(null==salesTargetTraceDataSum.getQnxs()){
            salesTargetTraceDataSum.setQnxs(BigDecimal.ZERO);
        }
        salesTargetTraceDataSum.setQnxs(salesTargetTraceDataSum.getQnxs().add(salesTargetTraceData.getQnxs()));
        //汇总数据去年销售累计
        if(null==salesTargetTraceDataSum.getQnxslj()){
            salesTargetTraceDataSum.setQnxslj(BigDecimal.ZERO);
        }
        salesTargetTraceDataSum.setQnxslj(
                salesTargetTraceDataSum.getQnxslj().add(salesTargetTraceData.getQnxs()));

        /*=========去年的销售金额 结束===========*/

        /*=========销售目标 开始===========*/
        queryMap.clear();
        //更新日期年月
        queryMap.put("yearmonth", salesTargetTraceData.getYearmonth());
        //更新日期当月
        //queryMap.put("businessend", salesTargetTraceData.getBusinessyear() + "-12");
        queryMap.put("salesuserid",salesTargetTraceData.getSalesuerid());
        queryMap.put("customersort",salesTargetTraceData.getCustomersort());
        queryMap.put("brandid",salesTargetTraceData.getBrandid());

        queryResultMap=getSalesTargetInputSumMap(queryMap); //销售目标金额

        //当前年第一目标
        if(null!=queryResultMap){
            salesTargetTraceData.setJndyxsmb((BigDecimal) queryResultMap.get("firstsalestarget"));
            salesTargetTraceData.setJndexsmb((BigDecimal) queryResultMap.get("secondsalestarget"));
        }
        if(salesTargetTraceData.getJndyxsmb()==null){
            salesTargetTraceData.setJndyxsmb(BigDecimal.ZERO);
        }

        //当前年第二目标
        if(salesTargetTraceData.getJndexsmb()==null){
            salesTargetTraceData.setJndexsmb(BigDecimal.ZERO);
        }

        //汇总数据今年第一销售目标合计
        if(null==salesTargetTraceDataSum.getJndyxsmb()){
            salesTargetTraceDataSum.setJndyxsmb(BigDecimal.ZERO);
        }
        salesTargetTraceDataSum.setJndyxsmb(
                salesTargetTraceDataSum.getJndyxsmb().add(salesTargetTraceData.getJndyxsmb()));

        //汇总数据今年销售目标累计
        if(null==salesTargetTraceDataSum.getJndyxsmblj()){
            salesTargetTraceDataSum.setJndyxsmblj(BigDecimal.ZERO);
        }
        salesTargetTraceDataSum.setJndyxsmblj(
                salesTargetTraceDataSum.getJndyxsmblj().add(salesTargetTraceData.getJndyxsmb()));


        //汇总数据今年第二销售目标合计
        if(null==salesTargetTraceDataSum.getJndexsmb()){
            salesTargetTraceDataSum.setJndexsmb(BigDecimal.ZERO);
        }
        salesTargetTraceDataSum.setJndexsmb(salesTargetTraceDataSum.getJndexsmb().add(salesTargetTraceData.getJndexsmb()));
        //汇总数据今年第二销售目标累计
        if(null==salesTargetTraceDataSum.getJndexsmblj()){
            salesTargetTraceDataSum.setJndexsmblj(BigDecimal.ZERO);
        }
        salesTargetTraceDataSum.setJndexsmblj(
                salesTargetTraceDataSum.getJndexsmblj().add(salesTargetTraceData.getJndexsmb()));

        /*=========销售目标 结束===========*/

        /*=========今年销售 开始===========*/
        if(salesTargetTraceData.getIquerymonth()==null
                || ( salesTargetTraceData.getImonth()<=salesTargetTraceData.getIquerymonth()
                && salesTargetTraceData.getImonth()<=iBusinessMonth)) {
            queryMap = new HashMap();
            //更新日期年月
            queryMap.put("yearmonth", salesTargetTraceData.getYearmonth());
            //更新日期当月
            queryMap.put("businessend", salesTargetTraceData.getBusinessdate());
            queryMap.put("salesuserid", salesTargetTraceData.getSalesuerid());
            queryMap.put("customersort", salesTargetTraceData.getCustomersort());
            queryMap.put("brandid", salesTargetTraceData.getBrandid());

            queryResultMap = new HashMap();
            queryResultMap = getSalesamountMap(queryMap); //去年销售金额

            if (null != queryResultMap) {
                salesTargetTraceData.setJnxs((BigDecimal) queryResultMap.get("salesamount"));
            }
        }
        if(salesTargetTraceData.getJnxs()==null){
            salesTargetTraceData.setJnxs(BigDecimal.ZERO);
        }
        //汇总数据今年销售合计
        if(null==salesTargetTraceDataSum.getJnxs()){
            salesTargetTraceDataSum.setJnxs(BigDecimal.ZERO);
        }
        salesTargetTraceDataSum.setJnxs(salesTargetTraceDataSum.getJnxs().add(salesTargetTraceData.getJnxs()));

        ///汇总数据今年销售累计
        if(null==salesTargetTraceDataSum.getJnxslj()){
            salesTargetTraceDataSum.setJnxslj(BigDecimal.ZERO);
        }
        salesTargetTraceDataSum.setJnxslj(salesTargetTraceDataSum.getJnxslj().add(salesTargetTraceData.getJnxs()));
        /*=========今年销售 结束===========*/

        /*=========去年销售累计 开始===========*/
        salesTargetTraceData.setQnxslj(salesTargetTraceDataSum.getQnxslj());
        /*=========去年销售累计 结束===========*/

        /*=========今年第一销售目标累计 开始===========*/
        salesTargetTraceData.setJndyxsmblj(salesTargetTraceDataSum.getJndyxsmblj());
        /*=========今年第一销售目标累计 结束===========*/

        /*=========今年第二销售目标累计 开始===========*/
        salesTargetTraceData.setJndexsmblj(salesTargetTraceDataSum.getJndexsmblj());
        /*=========今年第二销售目标累计 结束===========*/

        /*=========今年第销售累计 开始===========*/
        salesTargetTraceData.setJnxslj(salesTargetTraceDataSum.getJnxslj());
        /*=========今年第销售累计 结束===========*/


        //计算其他项数据
        updateCalcSalesTargetTraceReportCommonData(salesTargetTraceData);
    }

    /**
     * 转储到报表数据汇总
     * @param reportDataList
     * @param salesTargetTraceData
     * @throws Exception
     */
    private void setIntoSalesTargetTraceReportData(List<Map> reportDataList,
                                                   SalesTargetTraceData salesTargetTraceData) throws Exception{
        if(null==salesTargetTraceData){
            return;
        }
        if(salesTargetTraceData.getImonth()==null || salesTargetTraceData.getImonth()==0){
            return;
        }
        boolean isShowByMonth=false;
        if(salesTargetTraceData.getIquerymonth()==null
                || salesTargetTraceData.getImonth()<=salesTargetTraceData.getIquerymonth()) {
            isShowByMonth=true;
        }
        BigDecimal tmpd=null;
        for(Map map : reportDataList) {
            String subject = (String) map.get("subject");
            if("QNXS".equals(subject)){
                //去年销售
                map.put("month_"+salesTargetTraceData.getImonth(),
                        salesTargetTraceData.getQnxs().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNDYXSMB".equals(subject)){
                //今年第一目标
                map.put("month_"+salesTargetTraceData.getImonth(),
                        salesTargetTraceData.getJndyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNDEXSMB".equals(subject)){
                //今年第二目标
                map.put("month_"+salesTargetTraceData.getImonth(),
                        salesTargetTraceData.getJndexsmb().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNXS".equals(subject)){
                //今年实际完成
                if(isShowByMonth) {
                    map.put("month_" + salesTargetTraceData.getImonth(),
                            salesTargetTraceData.getJnxs().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }
            } else if("QNXSLJ".equals(subject)){
                //去年销售累计
                map.put("month_"+salesTargetTraceData.getImonth(),
                        salesTargetTraceData.getQnxslj().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNDYXSMBLJ".equals(subject)){
                //今年第一目标累计
                map.put("month_"+salesTargetTraceData.getImonth(),
                        salesTargetTraceData.getJndyxsmblj().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNDEXSMBLJ".equals(subject)){
                //今年第二目标累计
                map.put("month_"+salesTargetTraceData.getImonth(),
                        salesTargetTraceData.getJndexsmblj().setScale(2, BigDecimal.ROUND_HALF_UP));


            } else if("JNXSLJ".equals(subject)){
                //今年销售累计
                if(isShowByMonth) {
                    map.put("month_" + salesTargetTraceData.getImonth(),
                            salesTargetTraceData.getJnxslj().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("YDCL_DYXSMB".equals(subject)){
                //月达成率-第一目标
                if(isShowByMonth) {
                    tmpd = salesTargetTraceData.getYdcl_dyxsmb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("YDCL_DEXSMB".equals(subject)){
                //月达成率-第二目标
                if(isShowByMonth) {
                    tmpd = salesTargetTraceData.getYdcl_dexsmb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("LJDCL_DYXSMB".equals(subject)){
                //累计达成率-第一目标
                if(isShowByMonth) {
                    tmpd = salesTargetTraceData.getLjdcl_dyxsmb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("LJDCL_DEXSMB".equals(subject)){
                //累计达成率-第二目标
                if(isShowByMonth) {
                    tmpd = salesTargetTraceData.getLjdcl_dexsmb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("YCYE_DYXSMB".equals(subject)){
                //月差异额-第一目标
                if(isShowByMonth) {
                    map.put("month_" + salesTargetTraceData.getImonth(),
                            salesTargetTraceData.getYcye_dyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("YCYE_DEXSMB".equals(subject)){
                //月差异额-第二目标
                if(isShowByMonth) {
                    map.put("month_" + salesTargetTraceData.getImonth(),
                            salesTargetTraceData.getYcye_dexsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("LJCYE_DYXSMB".equals(subject)){
                //累计差异额-第一目标
                if(isShowByMonth) {
                    map.put("month_" + salesTargetTraceData.getImonth(),
                            salesTargetTraceData.getLjcye_dyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("LJCYE_DEXSMB".equals(subject)){
                //累计差异额-第二目标
                if(isShowByMonth) {
                    map.put("month_" + salesTargetTraceData.getImonth(),
                            salesTargetTraceData.getLjcye_dexsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("JQNTQ_YXSB".equals(subject)){
                //较去年同期-月
                if(isShowByMonth) {
                    tmpd = salesTargetTraceData.getJqntq_yxsb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }

            } else if("JQNTQ_LJXSB".equals(subject)){
                //较去年同期累计
                if(isShowByMonth) {
                    tmpd = salesTargetTraceData.getJqntq_ljxsb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetTraceData.getImonth(),"");
                }
            }
        }
    }
    /**
     * 计算其他共用列的数据
     * @param salesTargetTraceData
     * @throws Exception
     */
    private void updateCalcSalesTargetTraceReportCommonData(SalesTargetTraceData salesTargetTraceData) throws Exception{
        //月达成率-第一目标
        salesTargetTraceData.setYdcl_dyxsmb(BigDecimal.ZERO);
        //月达成率-第二目标
        salesTargetTraceData.setYdcl_dexsmb(BigDecimal.ZERO);
        //累计达成率-第一目标
        salesTargetTraceData.setLjdcl_dyxsmb(BigDecimal.ZERO);
        //累计达成率-第二目标
        salesTargetTraceData.setLjdcl_dexsmb(BigDecimal.ZERO);
        //月差异额-第一目标
        salesTargetTraceData.setYcye_dyxsmb(BigDecimal.ZERO);
        //月差异额-第二目标
        salesTargetTraceData.setYcye_dexsmb(BigDecimal.ZERO);
        //累计差异额-第一目标
        salesTargetTraceData.setLjcye_dyxsmb(BigDecimal.ZERO);
        //累计差异额-第二目标
        salesTargetTraceData.setLjcye_dexsmb(BigDecimal.ZERO);
        //较15年同期-月
        salesTargetTraceData.setJqntq_yxsb(BigDecimal.ZERO);
        //较15年同期-累计
        salesTargetTraceData.setJqntq_ljxsb(BigDecimal.ZERO);
        int scaleLen=6;
        BigDecimal tmpd=BigDecimal.ZERO;
        if(salesTargetTraceData.getJndyxsmb().compareTo(BigDecimal.ZERO)!=0){
            //月达成率-第一目标=今年实际完成/今年第一目标
            tmpd=salesTargetTraceData.getJnxs().divide(salesTargetTraceData.getJndyxsmb(), scaleLen, BigDecimal.ROUND_HALF_UP);
            salesTargetTraceData.setYdcl_dyxsmb(tmpd);
        }
        if(salesTargetTraceData.getJndexsmb().compareTo(BigDecimal.ZERO)!=0){
            //月达成率-第二目标=今年实际完成/今年第二目标
            tmpd=salesTargetTraceData.getJnxs().divide(salesTargetTraceData.getJndexsmb(), scaleLen,BigDecimal.ROUND_HALF_UP);
            salesTargetTraceData.setYdcl_dexsmb(tmpd);
        }
        if(salesTargetTraceData.getJndyxsmblj().compareTo(BigDecimal.ZERO)!=0){
            //累计达成率-第一目标=今年实际完成累计/今年第一目标累计
            tmpd=salesTargetTraceData.getJnxslj().divide(salesTargetTraceData.getJndyxsmblj(), scaleLen, BigDecimal.ROUND_HALF_UP);
            salesTargetTraceData.setLjdcl_dyxsmb(tmpd);
        }
        if(salesTargetTraceData.getJndexsmblj().compareTo(BigDecimal.ZERO)!=0){
            //累计达成率-第二目标=今年实际完成累计/今年第二目标累计
            tmpd=salesTargetTraceData.getJnxslj().divide(salesTargetTraceData.getJndexsmblj(), scaleLen,BigDecimal.ROUND_HALF_UP);
            salesTargetTraceData.setLjdcl_dexsmb(tmpd);
        }
        //月差异额-第一目标=今年实际完成-今年第一目标
        tmpd=salesTargetTraceData.getJnxs().subtract(salesTargetTraceData.getJndyxsmb()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
        salesTargetTraceData.setYcye_dyxsmb(tmpd);

        //月差异额-第二目标=今年实际完成-今年第二目标
        tmpd=salesTargetTraceData.getJnxs().subtract(salesTargetTraceData.getJndexsmb()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
        salesTargetTraceData.setYcye_dexsmb(tmpd);

        //累计差异额-第一目标=今年实际完成累计-今年第一目标累计
        tmpd=salesTargetTraceData.getJnxslj().subtract(salesTargetTraceData.getJndyxsmblj()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
        salesTargetTraceData.setLjcye_dyxsmb(tmpd);

        //累计差异额-第二目标=今年实际完成累计-今年第二目标累计
        tmpd=salesTargetTraceData.getJnxslj().subtract(salesTargetTraceData.getJndexsmblj()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
        salesTargetTraceData.setLjcye_dexsmb(tmpd);


        if(salesTargetTraceData.getQnxs().compareTo(BigDecimal.ZERO)!=0){
            //较去年同期-月=今年第一销售今年实际完成/15年销售
            tmpd=salesTargetTraceData.getJnxs().divide(salesTargetTraceData.getQnxs(),scaleLen,BigDecimal.ROUND_HALF_UP);
            salesTargetTraceData.setJqntq_yxsb(tmpd);
        }
        if(salesTargetTraceData.getQnxslj().compareTo(BigDecimal.ZERO)!=0){
            //较去年同期-累计=今年实际完成累计/15年销售累计
            tmpd=salesTargetTraceData.getJnxslj().divide(salesTargetTraceData.getQnxslj(), scaleLen, BigDecimal.ROUND_HALF_UP);
            salesTargetTraceData.setJqntq_ljxsb(tmpd);
        }
    }

    /**
     * 计算 合计列表
     * @param reportDataList
     * @param salesTargetTraceDataSum
     * @throws Exception
     */
    private void updateSumSalesTargetTraceReportTotal(List<Map> reportDataList,
                                                      SalesTargetTraceData salesTargetTraceDataSum) throws Exception{
        if(null==salesTargetTraceDataSum){
            return;
        }
        BigDecimal tmpd=null;
        BigDecimal monthTimeSchedule=null;
        BigDecimal yearTimeSchedule=null;

        if(salesTargetTraceDataSum.getMonthtimeschedule()!=null){
            monthTimeSchedule=salesTargetTraceDataSum.getMonthtimeschedule().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }else{
            monthTimeSchedule=BigDecimal.ZERO;
        }
        if(salesTargetTraceDataSum.getYeartimeschedule()!=null){
            yearTimeSchedule=salesTargetTraceDataSum.getYeartimeschedule().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }else{
            yearTimeSchedule=BigDecimal.ZERO;
        }
        for(Map map : reportDataList) {
            String subject = (String) map.get("subject");
            map.put("month",salesTargetTraceDataSum.getQuerymonth());
            map.put("year",salesTargetTraceDataSum.getLastyear());
            map.put("businessdate",salesTargetTraceDataSum.getBusinessdate());
            map.put("monthtimeschedule",monthTimeSchedule+"%");
            map.put("yeartimeschedule",yearTimeSchedule+"%");
            if("QNXS".equals(subject)){
                //去年销售
                map.put("summarycolumn", salesTargetTraceDataSum.getQnxs());
            } else if("JNDYXSMB".equals(subject)){
                //今年第一目标
                map.put("summarycolumn", salesTargetTraceDataSum.getJndyxsmb());
            } else if("JNDEXSMB".equals(subject)){
                //今年第二目标
                map.put("summarycolumn", salesTargetTraceDataSum.getJndexsmb());
            } else if("JNXS".equals(subject)){
                //今年实际完成
                map.put("summarycolumn", salesTargetTraceDataSum.getJnxs());
            } else if("QNXSLJ".equals(subject)){
                //去年销售累计
                map.put("summarycolumn", salesTargetTraceDataSum.getQnxslj());
            } else if("JNDYXSMBLJ".equals(subject)){
                //今年第一目标累计
                map.put("summarycolumn", salesTargetTraceDataSum.getJndyxsmblj());
            } else if("JNDEXSMBLJ".equals(subject)){
                //今年第二目标累计
                map.put("summarycolumn", salesTargetTraceDataSum.getJndexsmblj());
            } else if("JNXSLJ".equals(subject)){
                //今年销售累计
                map.put("summarycolumn", salesTargetTraceDataSum.getJnxslj());
            } else if("YDCL_DYXSMB".equals(subject)){
                //月达成率-第一目标
                tmpd=salesTargetTraceDataSum.getYdcl_dyxsmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("YDCL_DEXSMB".equals(subject)){
                //月达成率-第二目标
                tmpd=salesTargetTraceDataSum.getYdcl_dexsmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("LJDCL_DYXSMB".equals(subject)){
                //累计达成率-第一目标
                tmpd=salesTargetTraceDataSum.getLjdcl_dyxsmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("LJDCL_DEXSMB".equals(subject)){
                //累计达成率-第二目标
                tmpd=salesTargetTraceDataSum.getLjdcl_dexsmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("YCYE_DYXSMB".equals(subject)){
                //月差异额-第一目标
                map.put("summarycolumn", salesTargetTraceDataSum.getYcye_dyxsmb());
            } else if("YCYE_DEXSMB".equals(subject)){
                //月差异额-第二目标
                map.put("summarycolumn", salesTargetTraceDataSum.getYcye_dexsmb());
            } else if("LJCYE_DYXSMB".equals(subject)){
                //累计差异额-第一目标
                map.put("summarycolumn", salesTargetTraceDataSum.getLjcye_dyxsmb());
            } else if("LJCYE_DEXSMB".equals(subject)){
                //累计差异额-第二目标
                map.put("summarycolumn", salesTargetTraceDataSum.getLjcye_dexsmb());
            } else if("JQNTQ_YXSB".equals(subject)){
                //较去年同期-月
                tmpd=salesTargetTraceDataSum.getJqntq_yxsb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("JQNTQ_LJXSB".equals(subject)){
                //今年第一目标累计
                tmpd=salesTargetTraceDataSum.getJqntq_ljxsb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            }
        }
    }

    @Override
    public PageData showSalesTargetGrossTraceReportData(PageMap pageMap) throws Exception{

        Map condition=pageMap.getCondition();

        PageData pageData=null;
        //要返回的结果数据
        List<Map> reportDataList=new ArrayList<Map>();

        //对比年份
        String year=(String)condition.get("year");
        //月
        String querymonth=(String) condition.get("month");
        //业务日期
        String businessdate=(String) condition.get("businessdate");
        String salesuserid=(String)condition.get("salesuserid");
        String customersort=(String)condition.get("customersort");
        String brandid=(String)condition.get("brandid");


        Integer iQueryMonth=null;
        if(null!=querymonth && !"".equals(querymonth.trim()) && StringUtils.isNumeric(querymonth.trim())){
            iQueryMonth=Integer.parseInt(querymonth.trim());
        }else{
            querymonth="";
        }
        querymonth=querymonth.trim();

        if(null==year ||"".equals(year.trim()) || !StringUtils.isNumeric(year.trim())){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }
        int iYear=Integer.parseInt(year.trim());

        String[] businessdateArr=businessdate.split("-");
        String businessYear=businessdateArr[0];
        String businessMonth=businessdateArr[1];
        String businessDay=businessdateArr[2];
        int iBusinessYear=Integer.parseInt(businessYear.trim());
        //int iBusinessMonth=Integer.parseInt(businessYear.trim());
        //传入年比当对比年大于，返回空
        if(iYear-iBusinessYear>1){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }
        Map timeScheduleMap = getCalcYearAndMonthTimeSchedule(businessdate);
        BigDecimal monthTimeSchedule=null;
        BigDecimal yearTimeSchedule=null;
        if(timeScheduleMap!=null){
            monthTimeSchedule=(BigDecimal)timeScheduleMap.get("monthTimeSchedule");
            if(monthTimeSchedule==null){
                monthTimeSchedule=BigDecimal.ZERO;
            }
            yearTimeSchedule=(BigDecimal)timeScheduleMap.get("yearTimeSchedule");
            if(yearTimeSchedule==null){
                yearTimeSchedule=BigDecimal.ZERO;
            }
        }


        //初始化返回结果数据
        getInitSalesTargetGrossTraceReportData(reportDataList,iBusinessYear,iYear);
        SalesTargetGrossTraceData salesTargetGrossTraceDataSum =new SalesTargetGrossTraceData();

        salesTargetGrossTraceDataSum.setBusinessdate(businessdate);
        salesTargetGrossTraceDataSum.setYear(businessYear);
        salesTargetGrossTraceDataSum.setLastyear(year);
        salesTargetGrossTraceDataSum.setQuerymonth(querymonth);
        salesTargetGrossTraceDataSum.setMonthtimeschedule(monthTimeSchedule);
        salesTargetGrossTraceDataSum.setYeartimeschedule(yearTimeSchedule);

        String yearmonth=businessYear ;
        String lastYearmonth=iYear+"";
        String sMonth="";
        //当前月
        //String currentMonthString= CommonUtils.getCurrentMonthStr();

        for(int iMonth=1;iMonth<13;iMonth++){
            if(iMonth<10){
                sMonth="0"+iMonth;
            }else{
                sMonth=""+iMonth;
            }
            yearmonth=businessYear+"-"+sMonth;
            lastYearmonth=iYear+"-"+sMonth;
            SalesTargetGrossTraceData salesTargetGrossTraceData =new SalesTargetGrossTraceData();
            //更新日期
            salesTargetGrossTraceData.setBusinessdate(businessdate);
            salesTargetGrossTraceData.setBusinessyear(businessYear);
            salesTargetGrossTraceData.setBusinessmonth(businessMonth);
            salesTargetGrossTraceData.setBusinessday(businessDay);

            salesTargetGrossTraceData.setSalesuerid(salesuserid);
            salesTargetGrossTraceData.setCustomersort(customersort);
            salesTargetGrossTraceData.setBrandid(brandid);
            salesTargetGrossTraceData.setYearmonth(yearmonth);
            salesTargetGrossTraceData.setYear(businessYear);
            salesTargetGrossTraceData.setIyear(iBusinessYear);
            salesTargetGrossTraceData.setLastyearmonth(lastYearmonth);
            salesTargetGrossTraceData.setMonth(sMonth);
            salesTargetGrossTraceData.setImonth(iMonth);

            salesTargetGrossTraceData.setQuerymonth(querymonth);
            salesTargetGrossTraceData.setIquerymonth(iQueryMonth);


            salesTargetGrossTraceData.setQnml(BigDecimal.ZERO);
            salesTargetGrossTraceData.setJndymlmb(BigDecimal.ZERO);
            salesTargetGrossTraceData.setJndemlmb(BigDecimal.ZERO);
            salesTargetGrossTraceData.setJnml(BigDecimal.ZERO);
            salesTargetGrossTraceData.setQnmllj(BigDecimal.ZERO);
            salesTargetGrossTraceData.setJndymlmblj(BigDecimal.ZERO);
            salesTargetGrossTraceData.setJndemlmblj(BigDecimal.ZERO);
            salesTargetGrossTraceData.setJnmllj(BigDecimal.ZERO);
            salesTargetGrossTraceData.setJnxs(BigDecimal.ZERO);
            salesTargetGrossTraceData.setJnmll(BigDecimal.ZERO);
            //月达成率-第一目标
            salesTargetGrossTraceData.setYdcl_dymlmb(BigDecimal.ZERO);
            //月达成率-第二目标
            salesTargetGrossTraceData.setYdcl_demlmb(BigDecimal.ZERO);
            //累计达成率-第一目标
            salesTargetGrossTraceData.setLjdcl_dymlmb(BigDecimal.ZERO);
            //累计达成率-第二目标
            salesTargetGrossTraceData.setLjdcl_demlmb(BigDecimal.ZERO);
            //月差异额-第一目标
            salesTargetGrossTraceData.setYcye_dymlmb(BigDecimal.ZERO);
            //月差异额-第二目标
            salesTargetGrossTraceData.setYcye_demlmb(BigDecimal.ZERO);
            //累计差异额-第一目标
            salesTargetGrossTraceData.setLjcye_dymlmb(BigDecimal.ZERO);
            //累计差异额-第二目标
            salesTargetGrossTraceData.setLjcye_demlmb(BigDecimal.ZERO);
            //较15年同期-月
            salesTargetGrossTraceData.setJqntq_ymlb(BigDecimal.ZERO);
            //较15年同期-累计
            salesTargetGrossTraceData.setJqntq_ljmlb(BigDecimal.ZERO);
            //今年毛利率
            salesTargetGrossTraceData.setJnmll(BigDecimal.ZERO);

            //计算
            updateCalcSalesTargetGrossTraceBasicData(salesTargetGrossTraceData,salesTargetGrossTraceDataSum);
            /*
            if(iQueryMonth!=null){
                if( iMonth<iQueryMonth){
                    //计算
                    updateCalcSalesTargetGrossTraceBasicData(salesTargetGrossTraceData,salesTargetGrossTraceDataSum);
                }
            }else{
                //计算
                updateCalcSalesTargetGrossTraceBasicData(salesTargetGrossTraceData,salesTargetGrossTraceDataSum);
            }
            */

            setIntoSalesTargetGrossTraceReportData(reportDataList, salesTargetGrossTraceData);
        }

        ///计算按月合计时金额
        updateCalcSalesTargetGrossTraceReportCommonData(salesTargetGrossTraceDataSum);
        //设置合计数据
        updateSumSalesTargetGrossTraceReportTotal(reportDataList, salesTargetGrossTraceDataSum);

        int total=0;

        pageData=new PageData(total, reportDataList, pageMap);

        return pageData;
    }

    /**
     * 初始化毛利目标跟踪报表
     * @param list
     * @author zhanghonghui
     * @date 2014-11-29
     */
    private void getInitSalesTargetGrossTraceReportData(List<Map> list,int iCurrentYear,int iLastYear) throws Exception{
        Map map=new LinkedHashMap();
        //如果有代码存在，则使用代码表数据
        List<SysCode> sysCodeList=getBaseSysCodeMapper().getSysCodeListForeign("SalesTargetGrossTraceReport");
        if(null !=sysCodeList && sysCodeList.size()>0){
            for(SysCode item : sysCodeList){
                map=new LinkedHashMap();
                map.put("subject",item.getCode());
                if(item.getCode().startsWith("QN") || item.getCode().startsWith("JQN") ){
                    map.put("subjectname", item.getCodename().replace("{XX}",iLastYear+""));
                }else if(item.getCode().startsWith("JN")){
                    map.put("subjectname", item.getCodename().replace("{XX}",iCurrentYear+""));
                }else{
                    map.put("subjectname", item.getCodename());
                }

                for(int i=1;i<13;i++){
                    map.put("month_"+i, 0.00);
                }
                list.add(map);
            }
            map.put("summarycolumn",0.00);
        }
    }
    /**
     * 更新、计算并组装来自各种接口处的数据
     * @param salesTargetGrossTraceData 数据
     * @param salesTargetGrossTraceData 合计数据
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-8-2
     */
    private void updateCalcSalesTargetGrossTraceBasicData(SalesTargetGrossTraceData salesTargetGrossTraceData,SalesTargetGrossTraceData salesTargetGrossTraceDataSum) throws Exception{

        int iBusinessMonth=Integer.parseInt(salesTargetGrossTraceData.getBusinessmonth());

        /*=========去年的销售毛利金额 开始===========*/
        Map queryMap=new HashMap();
        queryMap.put("yearmonth",salesTargetGrossTraceData.getLastyearmonth());
        queryMap.put("salesuserid",salesTargetGrossTraceData.getSalesuerid());
        queryMap.put("customersort",salesTargetGrossTraceData.getCustomersort());
        queryMap.put("brandid",salesTargetGrossTraceData.getBrandid());

        Map queryResultMap=new HashMap();
        queryResultMap=getSalesamountMap(queryMap); //去年销售毛利金额

        if(null!=queryResultMap){
            salesTargetGrossTraceData.setQnml((BigDecimal) queryResultMap.get("grossamount"));
        }
        if(salesTargetGrossTraceData.getQnml()==null){
            salesTargetGrossTraceData.setQnml(BigDecimal.ZERO);
        }
        //汇总数据去年销售毛利合计
        if(null==salesTargetGrossTraceDataSum.getQnml()){
            salesTargetGrossTraceDataSum.setQnml(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setQnml(salesTargetGrossTraceDataSum.getQnml().add(salesTargetGrossTraceData.getQnml()));
        //汇总数据去年销售毛利累计
        if(null==salesTargetGrossTraceDataSum.getQnmllj()){
            salesTargetGrossTraceDataSum.setQnmllj(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setQnmllj(
                salesTargetGrossTraceDataSum.getQnmllj().add(salesTargetGrossTraceData.getQnml()));

        /*=========去年的销售毛利金额 结束===========*/

        /*=========销售毛利目标 开始===========*/
        queryMap.clear();
        //更新日期年月
        queryMap.put("yearmonth", salesTargetGrossTraceData.getYearmonth());
        //更新日期当月
        //queryMap.put("businessend", salesTargetGrossTraceData.getBusinessyear() + "-12");
        queryMap.put("salesuserid",salesTargetGrossTraceData.getSalesuerid());
        queryMap.put("customersort",salesTargetGrossTraceData.getCustomersort());
        queryMap.put("brandid",salesTargetGrossTraceData.getBrandid());

        queryResultMap=getSalesTargetInputSumMap(queryMap); //销售毛利目标金额

        //当前年第一目标
        if(null!=queryResultMap){
            salesTargetGrossTraceData.setJndymlmb((BigDecimal) queryResultMap.get("firstgrossprofit"));
            salesTargetGrossTraceData.setJndemlmb((BigDecimal) queryResultMap.get("secondgrossprofit"));
        }
        if(salesTargetGrossTraceData.getJndymlmb()==null){
            salesTargetGrossTraceData.setJndymlmb(BigDecimal.ZERO);
        }

        //当前年第二目标
        if(salesTargetGrossTraceData.getJndemlmb()==null){
            salesTargetGrossTraceData.setJndemlmb(BigDecimal.ZERO);
        }

        //汇总数据今年第一销售毛利目标合计
        if(null==salesTargetGrossTraceDataSum.getJndymlmb()){
            salesTargetGrossTraceDataSum.setJndymlmb(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setJndymlmb(
                salesTargetGrossTraceDataSum.getJndymlmb().add(salesTargetGrossTraceData.getJndymlmb()));

        //汇总数据今年销售毛利目标累计
        if(null==salesTargetGrossTraceDataSum.getJndymlmblj()){
            salesTargetGrossTraceDataSum.setJndymlmblj(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setJndymlmblj(
                salesTargetGrossTraceDataSum.getJndymlmblj().add(salesTargetGrossTraceData.getJndymlmb()));


        //汇总数据今年第二销售毛利目标合计
        if(null==salesTargetGrossTraceDataSum.getJndemlmb()){
            salesTargetGrossTraceDataSum.setJndemlmb(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setJndemlmb(salesTargetGrossTraceDataSum.getJndemlmb().add(salesTargetGrossTraceData.getJndemlmb()));
        //汇总数据今年第二销售毛利目标累计
        if(null==salesTargetGrossTraceDataSum.getJndemlmblj()){
            salesTargetGrossTraceDataSum.setJndemlmblj(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setJndemlmblj(
                salesTargetGrossTraceDataSum.getJndemlmblj().add(salesTargetGrossTraceData.getJndemlmb()));

        /*=========销售毛利目标 结束===========*/

        /*=========今年销售毛利 开始===========*/
        if(salesTargetGrossTraceData.getIquerymonth()==null
                || ( salesTargetGrossTraceData.getImonth()<=salesTargetGrossTraceData.getIquerymonth()
                    && salesTargetGrossTraceData.getImonth()<=iBusinessMonth)) {
            queryMap = new HashMap();

            //更新日期年月
            queryMap.put("yearmonth", salesTargetGrossTraceData.getYearmonth());
            //更新日期当月
            queryMap.put("businessend", salesTargetGrossTraceData.getBusinessdate());
            queryMap.put("salesuserid", salesTargetGrossTraceData.getSalesuerid());
            queryMap.put("customersort", salesTargetGrossTraceData.getCustomersort());
            queryMap.put("brandid", salesTargetGrossTraceData.getBrandid());

            queryResultMap = new HashMap();
            queryResultMap = getSalesamountMap(queryMap); //去年销售毛利金额
        }
        if(null!=queryResultMap){
            salesTargetGrossTraceData.setJnml((BigDecimal) queryResultMap.get("grossamount"));
            salesTargetGrossTraceData.setJnxs((BigDecimal) queryResultMap.get("salesamount"));
        }
        if(salesTargetGrossTraceData.getJnml()==null){
            salesTargetGrossTraceData.setJnml(BigDecimal.ZERO);
        }
        if(salesTargetGrossTraceData.getJnxs()==null){
            salesTargetGrossTraceData.setJnxs(BigDecimal.ZERO);
        }
        //汇总数据今年销售毛利合计
        if(null==salesTargetGrossTraceDataSum.getJnml()){
            salesTargetGrossTraceDataSum.setJnml(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setJnml(salesTargetGrossTraceDataSum.getJnml().add(salesTargetGrossTraceData.getJnml()));


        ///汇总数据今年销售毛利累计
        if(null==salesTargetGrossTraceDataSum.getJnmllj()){
            salesTargetGrossTraceDataSum.setJnmllj(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setJnmllj(salesTargetGrossTraceDataSum.getJnmllj().add(salesTargetGrossTraceData.getJnml()));


        //汇总数据今年销售合计
        if(null==salesTargetGrossTraceDataSum.getJnxs()){
            salesTargetGrossTraceDataSum.setJnxs(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setJnxs(salesTargetGrossTraceDataSum.getJnxs().add(salesTargetGrossTraceData.getJnxs()));
        ///汇总数据今年销售累计
        if(null==salesTargetGrossTraceDataSum.getJnxslj()){
            salesTargetGrossTraceDataSum.setJnxslj(BigDecimal.ZERO);
        }
        salesTargetGrossTraceDataSum.setJnxslj(salesTargetGrossTraceDataSum.getJnxslj().add(salesTargetGrossTraceData.getJnxs()));
        /*=========今年销售毛利 结束===========*/

        /*=========去年销售毛利累计 开始===========*/
        salesTargetGrossTraceData.setQnmllj(salesTargetGrossTraceDataSum.getQnmllj());
        /*=========去年销售毛利累计 结束===========*/

        /*=========今年第一销售毛利目标累计 开始===========*/
        salesTargetGrossTraceData.setJndymlmblj(salesTargetGrossTraceDataSum.getJndymlmblj());
        /*=========今年第一销售毛利目标累计 结束===========*/

        /*=========今年第二销售毛利目标累计 开始===========*/
        salesTargetGrossTraceData.setJndemlmblj(salesTargetGrossTraceDataSum.getJndemlmblj());
        /*=========今年第二销售毛利目标累计 结束===========*/

        /*=========今年第销售毛利累计 开始===========*/
        salesTargetGrossTraceData.setJnmllj(salesTargetGrossTraceDataSum.getJnmllj());
        /*=========今年第销售毛利累计 结束===========*/

        /*=========今年第销售累计 开始===========*/
        salesTargetGrossTraceData.setJnxslj(salesTargetGrossTraceDataSum.getJnxslj());
        /*=========今年第销售累计 结束===========*/

        //计算其他项数据
        updateCalcSalesTargetGrossTraceReportCommonData(salesTargetGrossTraceData);

    }

    /**
     * 转储到报表数据汇总
     * @param reportDataList
     * @param salesTargetGrossTraceData
     * @throws Exception
     */
    private void setIntoSalesTargetGrossTraceReportData(List<Map> reportDataList,
                                                        SalesTargetGrossTraceData salesTargetGrossTraceData)
            throws Exception{
        if(null==salesTargetGrossTraceData){
            return;
        }
        if(org.apache.commons.lang.StringUtils.isEmpty(salesTargetGrossTraceData.getMonth())){
            return;
        }
        boolean isShowByMonth=false;
        if(salesTargetGrossTraceData.getIquerymonth()==null
                || salesTargetGrossTraceData.getImonth()<=salesTargetGrossTraceData.getIquerymonth()) {
            isShowByMonth=true;
        }
        BigDecimal tmpd=null;
        for(Map map : reportDataList) {
            String subject = (String) map.get("subject");
            if("QNML".equals(subject)){
                //去年销售毛利
                map.put("month_"+salesTargetGrossTraceData.getImonth(),
                        salesTargetGrossTraceData.getQnml().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNDYMLMB".equals(subject)){
                //今年第一目标
                map.put("month_"+salesTargetGrossTraceData.getImonth(),
                        salesTargetGrossTraceData.getJndymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNDEMLMB".equals(subject)){
                //今年第二目标
                map.put("month_"+salesTargetGrossTraceData.getImonth(),
                        salesTargetGrossTraceData.getJndemlmb().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNML".equals(subject)){
                //今年实际完成
                if(isShowByMonth) {
                    map.put("month_" + salesTargetGrossTraceData.getImonth(),
                            salesTargetGrossTraceData.getJnml().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(),"");
                }
            } else if("QNMLLJ".equals(subject)){
                //去年销售毛利累计
                map.put("month_"+salesTargetGrossTraceData.getImonth(),
                        salesTargetGrossTraceData.getQnmllj().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNDYMLMBLJ".equals(subject)){
                //今年第一目标累计
                map.put("month_"+salesTargetGrossTraceData.getImonth(),
                        salesTargetGrossTraceData.getJndymlmblj().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if("JNDEMLMBLJ".equals(subject)){
                //今年第二目标累计
                map.put("month_"+salesTargetGrossTraceData.getImonth(),
                        salesTargetGrossTraceData.getJndemlmblj().setScale(2, BigDecimal.ROUND_HALF_UP));


            } else if("JNMLLJ".equals(subject)){
                //今年第一目标累计
                map.put("month_"+salesTargetGrossTraceData.getImonth(),
                        salesTargetGrossTraceData.getJnmllj().setScale(2, BigDecimal.ROUND_HALF_UP));


            } else if("YDCL_DYMLMB".equals(subject)){
                //16年实际完成累计
                if(isShowByMonth) {
                    tmpd = salesTargetGrossTraceData.getYdcl_dymlmb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            } else if("YDCL_DEMLMB".equals(subject)){
                //月达成率-第二目标
                if(isShowByMonth) {
                    tmpd = salesTargetGrossTraceData.getYdcl_demlmb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            } else if("LJDCL_DYMLMB".equals(subject)){
                //累计达成率-第一目标
                if(isShowByMonth) {
                    tmpd = salesTargetGrossTraceData.getLjdcl_dymlmb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            } else if("LJDCL_DEMLMB".equals(subject)){
                //累计达成率-第二目标
                if(isShowByMonth) {
                    tmpd=salesTargetGrossTraceData.getLjdcl_demlmb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_"+salesTargetGrossTraceData.getImonth(),tmpd+ "%");
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            } else if("YCYE_DYMLMB".equals(subject)){
                //累计达成率-第一目标
                if(isShowByMonth) {
                    map.put("month_"+salesTargetGrossTraceData.getImonth(),
                            salesTargetGrossTraceData.getYcye_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }

            } else if("YCYE_DEMLMB".equals(subject)){
                //月差异额-第二目标
                if(isShowByMonth) {
                    map.put("month_" + salesTargetGrossTraceData.getImonth(),
                            salesTargetGrossTraceData.getYcye_demlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }

            } else if("LJCYE_DYMLMB".equals(subject)){
                //累计差异额-第二目标
                if(isShowByMonth) {
                    map.put("month_" + salesTargetGrossTraceData.getImonth(),
                            salesTargetGrossTraceData.getLjcye_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }

            } else if("LJCYE_DEMLMB".equals(subject)){
                //累计差异额-第二目标
                if(isShowByMonth) {
                    map.put("month_" + salesTargetGrossTraceData.getImonth(),
                            salesTargetGrossTraceData.getLjcye_demlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }

            } else if("JQNTQ_YMLB".equals(subject)){
                //较15年同期-月
                if(isShowByMonth) {
                    tmpd = salesTargetGrossTraceData.getJqntq_ymlb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            } else if("JQNTQ_LJMLB".equals(subject)){
                //较15年同期-月
                if(isShowByMonth) {
                    tmpd = salesTargetGrossTraceData.getJqntq_ljmlb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            } else if("JNXS".equals(subject)){
                //16年实际销售
                if(isShowByMonth) {
                    map.put("month_" + salesTargetGrossTraceData.getImonth(),
                            salesTargetGrossTraceData.getJnxs().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            }  else if("JNXSLJ".equals(subject)){
                //16年实际销售
                if(isShowByMonth) {
                    map.put("month_" + salesTargetGrossTraceData.getImonth(),
                            salesTargetGrossTraceData.getJnxslj().setScale(2, BigDecimal.ROUND_HALF_UP));
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            } else if("JNMLL".equals(subject)){
                //毛利率
                if(isShowByMonth) {
                    tmpd = salesTargetGrossTraceData.getJnmll().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            } else if("JNMLLLJB".equals(subject)){
                //毛利率
                if(isShowByMonth) {
                    tmpd = salesTargetGrossTraceData.getJnmllljb().multiply(
                            new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), tmpd + "%");
                }else{
                    map.put("month_" + salesTargetGrossTraceData.getImonth(), "");
                }
            }
        }
    }
    /**
     * 计算其他共用列的数据
     * @param salesTargetGrossTraceData
     * @throws Exception
     */
    private void updateCalcSalesTargetGrossTraceReportCommonData(SalesTargetGrossTraceData salesTargetGrossTraceData) throws Exception{
        //月达成率-第一目标
        salesTargetGrossTraceData.setYdcl_dymlmb(BigDecimal.ZERO);
        //月达成率-第二目标
        salesTargetGrossTraceData.setYdcl_demlmb(BigDecimal.ZERO);
        //累计达成率-第一目标
        salesTargetGrossTraceData.setLjdcl_dymlmb(BigDecimal.ZERO);
        //累计达成率-第二目标
        salesTargetGrossTraceData.setLjdcl_demlmb(BigDecimal.ZERO);
        //月差异额-第一目标
        salesTargetGrossTraceData.setYcye_dymlmb(BigDecimal.ZERO);
        //月差异额-第二目标
        salesTargetGrossTraceData.setYcye_demlmb(BigDecimal.ZERO);
        //累计差异额-第一目标
        salesTargetGrossTraceData.setLjcye_dymlmb(BigDecimal.ZERO);
        //累计差异额-第二目标
        salesTargetGrossTraceData.setLjcye_demlmb(BigDecimal.ZERO);
        //较15年同期-月
        salesTargetGrossTraceData.setJqntq_ymlb(BigDecimal.ZERO);
        //较15年同期-累计
        salesTargetGrossTraceData.setJqntq_ljmlb(BigDecimal.ZERO);
        //今年毛利率
        salesTargetGrossTraceData.setJnmll(BigDecimal.ZERO);
        //今年累计毛利率
        salesTargetGrossTraceData.setJnmllljb(BigDecimal.ZERO);

        int scaleLen=6;
        BigDecimal tmpd=BigDecimal.ZERO;
        if(salesTargetGrossTraceData.getJndymlmb().compareTo(BigDecimal.ZERO)!=0){
            //月达成率-第一目标=今年实际完成/今年第一目标
            tmpd=salesTargetGrossTraceData.getJnml().divide(salesTargetGrossTraceData.getJndymlmb(), scaleLen, BigDecimal.ROUND_HALF_UP);
            salesTargetGrossTraceData.setYdcl_dymlmb(tmpd);
        }
        if(salesTargetGrossTraceData.getJndemlmb().compareTo(BigDecimal.ZERO)!=0){
            //月达成率-第二目标=今年实际完成/今年第二目标
            tmpd=salesTargetGrossTraceData.getJnml().divide(salesTargetGrossTraceData.getJndemlmb(), scaleLen,BigDecimal.ROUND_HALF_UP);
            salesTargetGrossTraceData.setYdcl_demlmb(tmpd);
        }
        if(salesTargetGrossTraceData.getJndymlmblj().compareTo(BigDecimal.ZERO)!=0){
            //累计达成率-第一目标=今年实际完成累计/今年第一目标累计
            tmpd=salesTargetGrossTraceData.getJnmllj().divide(salesTargetGrossTraceData.getJndymlmblj(), scaleLen, BigDecimal.ROUND_HALF_UP);
            salesTargetGrossTraceData.setLjdcl_dymlmb(tmpd);
        }
        if(salesTargetGrossTraceData.getJndemlmblj().compareTo(BigDecimal.ZERO)!=0){
            //累计达成率-第二目标=今年实际完成累计/今年第二目标累计
            tmpd=salesTargetGrossTraceData.getJnmllj().divide(salesTargetGrossTraceData.getJndemlmblj(), scaleLen,BigDecimal.ROUND_HALF_UP);
            salesTargetGrossTraceData.setLjdcl_demlmb(tmpd);
        }
        //月差异额-第一目标=今年实际完成-今年第一目标
        tmpd=salesTargetGrossTraceData.getJnml().subtract(salesTargetGrossTraceData.getJndymlmb()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
        salesTargetGrossTraceData.setYcye_dymlmb(tmpd);

        //月差异额-第二目标=今年实际完成-今年第二目标
        tmpd=salesTargetGrossTraceData.getJnml().subtract(salesTargetGrossTraceData.getJndemlmb()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
        salesTargetGrossTraceData.setYcye_demlmb(tmpd);

        //累计差异额-第一目标=今年实际完成累计-今年第一目标累计
        tmpd=salesTargetGrossTraceData.getJnmllj().subtract(salesTargetGrossTraceData.getJndymlmblj()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
        salesTargetGrossTraceData.setLjcye_dymlmb(tmpd);

        //累计差异额-第二目标=今年实际完成累计-今年第二目标累计
        tmpd=salesTargetGrossTraceData.getJnmllj().subtract(salesTargetGrossTraceData.getJndemlmblj()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
        salesTargetGrossTraceData.setLjcye_demlmb(tmpd);


        if(salesTargetGrossTraceData.getQnml().compareTo(BigDecimal.ZERO)!=0){
            //较去年同期-月=今年第一销售毛利今年实际完成/15年销售毛利
            tmpd=salesTargetGrossTraceData.getJnml().divide(salesTargetGrossTraceData.getQnml(),scaleLen,BigDecimal.ROUND_HALF_UP);
            salesTargetGrossTraceData.setJqntq_ymlb(tmpd);
        }
        if(salesTargetGrossTraceData.getQnmllj().compareTo(BigDecimal.ZERO)!=0){
            //较去年同期-累计=今年实际完成累计/15年销售毛利累计
            tmpd=salesTargetGrossTraceData.getJnmllj().divide(salesTargetGrossTraceData.getQnmllj(), scaleLen, BigDecimal.ROUND_HALF_UP);
            salesTargetGrossTraceData.setJqntq_ljmlb(tmpd);
        }
        if(salesTargetGrossTraceData.getJnxs().compareTo(BigDecimal.ZERO)!=0){
            //今年毛利率=今年实际毛利/今年实际销售
            tmpd=salesTargetGrossTraceData.getJnml().divide(salesTargetGrossTraceData.getJnxs(), scaleLen, BigDecimal.ROUND_HALF_UP);
            salesTargetGrossTraceData.setJnmll(tmpd);
        }
        if(salesTargetGrossTraceData.getJnxslj().compareTo(BigDecimal.ZERO)!=0){
            //今年累计毛利率=今年累计毛利/今年实际销售
            tmpd=salesTargetGrossTraceData.getJnmllj().divide(salesTargetGrossTraceData.getJnxslj(), scaleLen, BigDecimal.ROUND_HALF_UP);
            salesTargetGrossTraceData.setJnmllljb(tmpd);
        }

    }

    /**
     * 计算 合计列表
     * @param reportDataList
     * @param salesTargetGrossTraceDataSum
     * @throws Exception
     */
    private void updateSumSalesTargetGrossTraceReportTotal(List<Map> reportDataList,
                                                           SalesTargetGrossTraceData salesTargetGrossTraceDataSum) throws Exception{
        if(null==salesTargetGrossTraceDataSum){
            return;
        }
        BigDecimal tmpd=null;
        BigDecimal monthTimeSchedule=null;
        BigDecimal yearTimeSchedule=null;

        if(salesTargetGrossTraceDataSum.getMonthtimeschedule()!=null){
            monthTimeSchedule=salesTargetGrossTraceDataSum.getMonthtimeschedule().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }else{
            monthTimeSchedule=BigDecimal.ZERO;
        }
        if(salesTargetGrossTraceDataSum.getYeartimeschedule()!=null){
            yearTimeSchedule=salesTargetGrossTraceDataSum.getYeartimeschedule().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }else{
            yearTimeSchedule=BigDecimal.ZERO;
        }
        for(Map map : reportDataList) {
            String subject = (String) map.get("subject");
            map.put("month",salesTargetGrossTraceDataSum.getQuerymonth());
            map.put("year",salesTargetGrossTraceDataSum.getLastyear());
            map.put("businessdate",salesTargetGrossTraceDataSum.getBusinessdate());
            map.put("monthtimeschedule",monthTimeSchedule+"%");
            map.put("yeartimeschedule",yearTimeSchedule+"%");
            if("QNML".equals(subject)){
                //去年销售毛利
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getQnml().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("JNDYMLMB".equals(subject)){
                //今年第一目标
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getJndymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("JNDEMLMB".equals(subject)){
                //今年第二目标
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getJndemlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("JNML".equals(subject)){
                //今年实际完成
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getJnml().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("QNMLLJ".equals(subject)){
                //去年销售毛利累计
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getQnmllj().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("JNDYMLMBLJ".equals(subject)){
                //今年第一目标累计
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getJndymlmblj().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("JNDEMLMBLJ".equals(subject)){
                //今年第一目标累计
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getJndemlmblj().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("JNMLLJ".equals(subject)){
                //今年销售毛利累计
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getJnmllj().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("YDCL_DYMLMB".equals(subject)){
                //月达成率-第一目标
                tmpd=salesTargetGrossTraceDataSum.getYdcl_dymlmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("YDCL_DEMLMB".equals(subject)){
                //月达成率-第二目标
                tmpd=salesTargetGrossTraceDataSum.getYdcl_demlmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("LJDCL_DYMLMB".equals(subject)){
                //累计达成率-第一目标
                tmpd=salesTargetGrossTraceDataSum.getLjdcl_dymlmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("LJDCL_DEMLMB".equals(subject)){
                //累计达成率-第二目标
                tmpd=salesTargetGrossTraceDataSum.getLjdcl_demlmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("YCYE_DYMLMB".equals(subject)){
                //月差异额-第一目标
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getYcye_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("YCYE_DEMLMB".equals(subject)){
                //月差异额-第二目标
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getYcye_demlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("LJCYE_DYMLMB".equals(subject)){
                //累计差异额-第一目标
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getLjcye_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("LJCYE_DEMLMB".equals(subject)){
                //累计差异额-第二目标
                map.put("summarycolumn",
                        salesTargetGrossTraceDataSum.getLjcye_demlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if("JQNTQ_YMLB".equals(subject)){
                //较去年同期-月
                tmpd=salesTargetGrossTraceDataSum.getJqntq_ymlb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("JQNTQ_LJMLB".equals(subject)){
                //今年第一目标累计
                tmpd=salesTargetGrossTraceDataSum.getJqntq_ljmlb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("JNXS".equals(subject)){
                //今年销售
                map.put("summarycolumn", salesTargetGrossTraceDataSum.getJnxs());
            } else if("JNXSLJ".equals(subject)){
                //今年销售累计
                map.put("summarycolumn", salesTargetGrossTraceDataSum.getJnxslj());
            } else if("JNMLL".equals(subject)){
                //今年毛利率
                tmpd=salesTargetGrossTraceDataSum.getJnmll().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            } else if("JNMLLLJB".equals(subject)){
                //今年毛利率
                tmpd=salesTargetGrossTraceDataSum.getJnmllljb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd+"%");
            }
        }
    }

    /**
     * 按品牌月度目标分析
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public PageData showSalesTargetBrandMonthAnalyzeReportData(PageMap pageMap) throws  Exception{
        Map condition=pageMap.getCondition();

        PageData pageData=null;
        //要返回的结果数据
        List<Map> reportDataList=new ArrayList<Map>();
        //年
        String year=(String)condition.get("year");
        //月
        String querymonth=(String) condition.get("month");
        //业务日期
        String businessdate=(String) condition.get("businessdate");

        if(null==businessdate ||"".equals(businessdate.trim())){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }
        businessdate=businessdate.trim();
        String salesuserid=(String)condition.get("salesuserid");
        String customersort=(String)condition.get("customersort");
        String brandid=(String)condition.get("brandid");

        if(null==querymonth || "".equals(querymonth.trim())){
            querymonth="";
        }
        querymonth=querymonth.trim();
        if(null==year ||"".equals(year.trim()) || !StringUtils.isNumeric(year.trim())){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }
        int iYear=Integer.parseInt(year.trim());

        String[] businessdateArr=businessdate.split("-");
        String businessYear=businessdateArr[0];
        String businessMonth=businessdateArr[1];
        String businessDay=businessdateArr[2];
        int iBusinessYear=Integer.parseInt(businessYear.trim());
        int iBusinessMonth=Integer.parseInt(businessYear.trim());
        //传入年比当前年大于1，返回空
        if(iYear-iBusinessYear>1){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }

        List<Brand> brandList=null;
        if(null!=brandid&&!"".equals(brandid.trim())) {
            Map paramMap = new HashMap();
            paramMap.put("idarrs", brandid.trim());
            brandList = getBaseGoodsMapper().getBrandListByMap(paramMap);
        }

        SalesTargetMonthAnalyzeData monthAnalyzeDataSum =new SalesTargetMonthAnalyzeData();

        monthAnalyzeDataSum.setBusinessdate(businessdate);
        monthAnalyzeDataSum.setYear(businessYear);
        monthAnalyzeDataSum.setLastyear(year);
        monthAnalyzeDataSum.setMonth(querymonth);

        SalesTargetMonthAnalyzeData monthAnalyzeDataRealDiffSum =new SalesTargetMonthAnalyzeData();

        String yearmonth=businessYear ;
        String lastYearmonth=iYear+"";
        Integer iMonth=null;
        if(!"".equals(querymonth.trim()) && StringUtils.isNumeric(querymonth.trim())){
            iMonth=Integer.parseInt(querymonth.trim());
            if(iMonth<10){
                yearmonth=businessYear+"-0"+iMonth;
                lastYearmonth=iYear+"-0"+iMonth;
            }else{
                yearmonth=businessYear+"-"+iMonth;
                lastYearmonth=iYear+"-"+iMonth;
            }
        }
        getInitSalesTargetMonthAnalyzeData(reportDataList,year.trim(),businessYear.trim());
        //当前月
        String currentMonthString= CommonUtils.getCurrentMonthStr();
        BigDecimal timeSchedule=getCalcMonthAnalyzeTimeSchedule(iMonth,businessdate);
        //保存到合计，存入
        monthAnalyzeDataSum.setTimeschedule(timeSchedule);

        Map<String,SalesTargetMonthAnalyzeData> monthAnalyzeDataMap=new HashMap<String, SalesTargetMonthAnalyzeData>();
        if(brandList!=null && brandList.size()>0){
            for(Brand brandInfo : brandList){
                if(StringUtils.isEmpty(brandInfo.getId())){
                    continue;
                }

                SalesTargetMonthAnalyzeData monthAnalyzeData =new SalesTargetMonthAnalyzeData();

                monthAnalyzeDataMap.put(brandInfo.getId(),monthAnalyzeData);
                //更新日期
                monthAnalyzeData.setBusinessdate(businessdate);
                monthAnalyzeData.setBusinessyear(businessYear);
                monthAnalyzeData.setBusinessmonth(businessMonth);
                monthAnalyzeData.setBusinessday(businessDay);

                monthAnalyzeData.setSalesuerid(salesuserid);
                monthAnalyzeData.setCustomersort(customersort);
                monthAnalyzeData.setBrandid(brandInfo.getId());
                monthAnalyzeData.setYearmonth(yearmonth);
                monthAnalyzeData.setYear(businessYear);
                monthAnalyzeData.setLastyearmonth(lastYearmonth);
                monthAnalyzeData.setLastyear(iYear+"");
                monthAnalyzeData.setMonth(querymonth);
                monthAnalyzeData.setTimeschedule(timeSchedule);

                //计算
                updateCalcSalesTargetMonthBasicData(monthAnalyzeData, monthAnalyzeDataSum, monthAnalyzeDataRealDiffSum);
                //转存到报表列表
                setIntoSalesTargetMonthAnalyzeData("brand", reportDataList, monthAnalyzeData);
            }
        }else{
            //计算

            SalesTargetMonthAnalyzeData monthAnalyzeData =new SalesTargetMonthAnalyzeData();

            //更新日期
            monthAnalyzeData.setBusinessdate(businessdate);
            monthAnalyzeData.setBusinessyear(businessYear);
            monthAnalyzeData.setBusinessmonth(businessMonth);
            monthAnalyzeData.setBusinessday(businessDay);

            monthAnalyzeData.setSalesuerid(salesuserid);
            monthAnalyzeData.setCustomersort(customersort);
            monthAnalyzeData.setBrandid(null);
            monthAnalyzeData.setYearmonth(yearmonth);
            monthAnalyzeData.setYear(businessYear);
            monthAnalyzeData.setLastyearmonth(lastYearmonth);
            monthAnalyzeData.setLastyear(iYear+"");
            monthAnalyzeData.setMonth(querymonth);
            monthAnalyzeData.setTimeschedule(timeSchedule);

            updateCalcSalesTargetMonthBasicData(monthAnalyzeData, monthAnalyzeDataSum, monthAnalyzeDataRealDiffSum);

        }

        ///计算按月合计时金额
        updateCalcSalesTargetMonthAnalyzeReportCommonData(monthAnalyzeDataSum);
        //设置合计数据
        updateSumSalesTargetMonthAnalyzeDataTotal(reportDataList, monthAnalyzeDataSum, monthAnalyzeDataRealDiffSum);

        int total=0;

        pageData=new PageData(total, reportDataList, pageMap);

        return pageData;
    }

    /**
     * 按渠道月度目标分解
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public PageData showSalesTargetCustomerSortMonthAnalyzeReportData(PageMap pageMap) throws  Exception{
        Map condition=pageMap.getCondition();

        PageData pageData=null;
        //要返回的结果数据
        List<Map> reportDataList=new ArrayList<Map>();
        //年
        String year=(String)condition.get("year");
        //月
        String querymonth=(String) condition.get("month");
        //业务日期
        String businessdate=(String) condition.get("businessdate");

        if(null==businessdate ||"".equals(businessdate.trim())){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }
        businessdate=businessdate.trim();
        String salesuserid=(String)condition.get("salesuserid");
        String customersort=(String)condition.get("customersort");
        String brandid=(String)condition.get("brandid");

        if(null==querymonth || "".equals(querymonth.trim())){
            querymonth="";
        }
        querymonth=querymonth.trim();
        if(null==year ||"".equals(year.trim()) || !StringUtils.isNumeric(year.trim())){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }
        int iYear=Integer.parseInt(year.trim());

        String[] businessdateArr=businessdate.split("-");
        String businessYear=businessdateArr[0];
        String businessMonth=businessdateArr[1];
        String businessDay=businessdateArr[2];
        int iBusinessYear=Integer.parseInt(businessYear.trim());
        int iBusinessMonth=Integer.parseInt(businessYear.trim());
        //传入年比当前年大于1，返回空
        if(iYear-iBusinessYear>1){
            pageData=new PageData(0, reportDataList, pageMap);
            return pageData;
        }

        List<CustomerSort> customerSortList=null;
        if(null!=customersort&&!"".equals(customersort.trim())) {
            Map paramMap = new HashMap();
            paramMap.put("idarrs", customersort.trim());
            customerSortList = getBaseCustomerSortMapper().getCustomerSortListByMap(paramMap);
        }

        SalesTargetMonthAnalyzeData monthAnalyzeDataSum =new SalesTargetMonthAnalyzeData();

        monthAnalyzeDataSum.setBusinessdate(businessdate);
        monthAnalyzeDataSum.setYear(businessYear);
        monthAnalyzeDataSum.setLastyear(year);
        monthAnalyzeDataSum.setMonth(querymonth);

        SalesTargetMonthAnalyzeData monthAnalyzeDataRealDiffSum =new SalesTargetMonthAnalyzeData();

        String yearmonth=businessYear ;
        String lastYearmonth=iYear+"";
        Integer iMonth=null;
        if(!"".equals(querymonth.trim()) && StringUtils.isNumeric(querymonth.trim())){
            iMonth=Integer.parseInt(querymonth.trim());
            if(iMonth<10){
                yearmonth=businessYear + "-0" + iMonth;
                lastYearmonth = iYear + "-0" +iMonth;
            }else{
                yearmonth=businessYear + "-" + iMonth;
                lastYearmonth = iYear + "-" +iMonth;
            }
        }
        getInitSalesTargetMonthAnalyzeData(reportDataList,year.trim(),businessYear.trim());
        //当前月
        String currentMonthString= CommonUtils.getCurrentMonthStr();
        BigDecimal timeSchedule=getCalcMonthAnalyzeTimeSchedule(iMonth,businessdate);
        monthAnalyzeDataSum.setTimeschedule(timeSchedule);
        Map<String,SalesTargetMonthAnalyzeData> monthAnalyzeDataMap=new HashMap<String, SalesTargetMonthAnalyzeData>();
        if(customerSortList!=null && customerSortList.size()>0){
            for(CustomerSort customerSortInfo : customerSortList){
                if(StringUtils.isEmpty(customerSortInfo.getId())){
                    continue;
                }

                SalesTargetMonthAnalyzeData monthAnalyzeData =new SalesTargetMonthAnalyzeData();

                monthAnalyzeDataMap.put(customerSortInfo.getId(),monthAnalyzeData);
                //更新日期
                monthAnalyzeData.setBusinessdate(businessdate);
                monthAnalyzeData.setBusinessyear(businessYear);
                monthAnalyzeData.setBusinessmonth(businessMonth);
                monthAnalyzeData.setBusinessday(businessDay);

                monthAnalyzeData.setSalesuerid(salesuserid);
                monthAnalyzeData.setCustomersort(customerSortInfo.getId());
                monthAnalyzeData.setBrandid(brandid);
                monthAnalyzeData.setYearmonth(yearmonth);
                monthAnalyzeData.setYear(businessYear);
                monthAnalyzeData.setLastyearmonth(lastYearmonth);
                monthAnalyzeData.setLastyear(iYear+"");
                monthAnalyzeData.setMonth(querymonth);
                monthAnalyzeData.setTimeschedule(timeSchedule);

                //计算
                updateCalcSalesTargetMonthBasicData(monthAnalyzeData, monthAnalyzeDataSum, monthAnalyzeDataRealDiffSum);
                //转存到报表列表
                setIntoSalesTargetMonthAnalyzeData("customersort", reportDataList, monthAnalyzeData);
            }
        }else{
            //计算

            SalesTargetMonthAnalyzeData monthAnalyzeData =new SalesTargetMonthAnalyzeData();

            //更新日期
            monthAnalyzeData.setBusinessdate(businessdate);
            monthAnalyzeData.setBusinessyear(businessYear);
            monthAnalyzeData.setBusinessmonth(businessMonth);
            monthAnalyzeData.setBusinessday(businessDay);

            monthAnalyzeData.setSalesuerid(salesuserid);
            monthAnalyzeData.setCustomersort(null);
            monthAnalyzeData.setBrandid(brandid);
            monthAnalyzeData.setYearmonth(yearmonth);
            monthAnalyzeData.setYear(businessYear);
            monthAnalyzeData.setLastyearmonth(lastYearmonth);
            monthAnalyzeData.setLastyear(iYear+"");
            monthAnalyzeData.setMonth(querymonth);
            monthAnalyzeData.setTimeschedule(timeSchedule);

            updateCalcSalesTargetMonthBasicData(monthAnalyzeData, monthAnalyzeDataSum, monthAnalyzeDataRealDiffSum);

        }

        ///计算按月合计时金额
        updateCalcSalesTargetMonthAnalyzeReportCommonData(monthAnalyzeDataSum);
        //设置合计数据
        updateSumSalesTargetMonthAnalyzeDataTotal(reportDataList, monthAnalyzeDataSum, monthAnalyzeDataRealDiffSum);

        int total=0;

        pageData=new PageData(total, reportDataList, pageMap);

        return pageData;
    }

    /**
     * 更新月度目标分类基本数据
     * @param monthAnalyzeData
     * @param monthAnalyzeDataSum
     * @param monthAnalyzeDataRealDiffSum
     * @throws Exception
     */
    private void updateCalcSalesTargetMonthBasicData(SalesTargetMonthAnalyzeData monthAnalyzeData,
                                                         SalesTargetMonthAnalyzeData monthAnalyzeDataSum,
                                                         SalesTargetMonthAnalyzeData monthAnalyzeDataRealDiffSum) throws Exception{
        /*=========去年的销售金额 开始===========*/
        Map queryMap=new HashMap();
        queryMap.put("yearmonth",monthAnalyzeData.getLastyearmonth());
        queryMap.put("salesuserid",monthAnalyzeData.getSalesuerid());
        queryMap.put("customersort",monthAnalyzeData.getCustomersort());
        queryMap.put("brandid",monthAnalyzeData.getBrandid());

        Map queryResultMap=new HashMap();
        queryResultMap=getSalesamountMap(queryMap); //去年销售金额

        if(null!=queryResultMap){
            monthAnalyzeData.setQnxs((BigDecimal)queryResultMap.get("salesamount"));
            monthAnalyzeData.setQnml((BigDecimal) queryResultMap.get("grossamount"));
        }
        if(monthAnalyzeData.getQnxs()==null){
            monthAnalyzeData.setQnxs(BigDecimal.ZERO);
        }
        if(monthAnalyzeData.getQnml()==null){
            monthAnalyzeData.setQnml(BigDecimal.ZERO);
        }
        //汇总数据去年销售合计
        if(null==monthAnalyzeDataSum.getQnxs()){
            monthAnalyzeDataSum.setQnxs(BigDecimal.ZERO);
        }
        monthAnalyzeDataSum.setQnxs(monthAnalyzeDataSum.getQnxs().add(monthAnalyzeData.getQnxs()));
        //汇总数据去年销售合计
        if(null==monthAnalyzeDataSum.getQnml()){
            monthAnalyzeDataSum.setQnml(BigDecimal.ZERO);
        }
        monthAnalyzeDataSum.setQnml(monthAnalyzeDataSum.getQnml().add(monthAnalyzeData.getQnml()));

        /*=========去年的销售金额 结束===========*/

        /*=========销售目标 开始===========*/
        queryMap.clear();

        if(StringUtils.isNotEmpty(monthAnalyzeData.getMonth())) {
            //更新日期年月
            queryMap.put("yearmonth", monthAnalyzeData.getYearmonth());
        }else {
            //更新日期当年第一个月
            queryMap.put("businessstart", monthAnalyzeData.getBusinessyear() + "-01");
        }
        //更新日期当月
        queryMap.put("businessend", monthAnalyzeData.getBusinessyear() + "-" + monthAnalyzeData.getBusinessmonth());
        queryMap.put("salesuserid",monthAnalyzeData.getSalesuerid());
        queryMap.put("customersort",monthAnalyzeData.getCustomersort());
        queryMap.put("brandid",monthAnalyzeData.getBrandid());

        queryResultMap=getSalesTargetInputSumMap(queryMap); //销售目标金额


        if(null!=queryResultMap){
            monthAnalyzeData.setJndyxsmb((BigDecimal) queryResultMap.get("firstsalestarget"));
            monthAnalyzeData.setJndexsmb((BigDecimal) queryResultMap.get("secondsalestarget"));
            monthAnalyzeData.setJndymlmb((BigDecimal) queryResultMap.get("firstgrossprofit"));
            monthAnalyzeData.setJndemlmb((BigDecimal) queryResultMap.get("secondgrossprofit"));
        }
        //当前年第一目标
        if(monthAnalyzeData.getJndyxsmb()==null){
            monthAnalyzeData.setJndyxsmb(BigDecimal.ZERO);
        }

        //当前年第二销售目标
        if(monthAnalyzeData.getJndexsmb()==null){
            monthAnalyzeData.setJndexsmb(BigDecimal.ZERO);
        }
        //当前年第一毛利目标
        if(monthAnalyzeData.getJndymlmb()==null){
            monthAnalyzeData.setJndymlmb(BigDecimal.ZERO);
        }

        //当前年第二毛利目标
        if(monthAnalyzeData.getJndemlmb()==null){
            monthAnalyzeData.setJndemlmb(BigDecimal.ZERO);
        }

        //汇总数据今年第一销售目标合计
        if(null==monthAnalyzeDataSum.getJndyxsmb()){
            monthAnalyzeDataSum.setJndyxsmb(BigDecimal.ZERO);
        }
        monthAnalyzeDataSum.setJndyxsmb(
                monthAnalyzeDataSum.getJndyxsmb().add(monthAnalyzeData.getJndyxsmb()));


        //汇总数据今年第二销售目标合计
        if(null==monthAnalyzeDataSum.getJndexsmb()){
            monthAnalyzeDataSum.setJndexsmb(BigDecimal.ZERO);
        }
        monthAnalyzeDataSum.setJndexsmb(monthAnalyzeDataSum.getJndexsmb().add(monthAnalyzeData.getJndexsmb()));


        //汇总数据今年第一销售毛利目标合计
        if(null==monthAnalyzeDataSum.getJndymlmb()){
            monthAnalyzeDataSum.setJndymlmb(BigDecimal.ZERO);
        }
        monthAnalyzeDataSum.setJndymlmb(
                monthAnalyzeDataSum.getJndymlmb().add(monthAnalyzeData.getJndymlmb()));


        //汇总数据今年第二销售毛利目标合计
        if(null==monthAnalyzeDataSum.getJndemlmb()){
            monthAnalyzeDataSum.setJndemlmb(BigDecimal.ZERO);
        }
        monthAnalyzeDataSum.setJndemlmb(monthAnalyzeDataSum.getJndemlmb().add(monthAnalyzeData.getJndemlmb()));
        /*=========销售目标 结束===========*/

        /*=========今年销售 开始===========*/
        queryMap=new HashMap();
        if(StringUtils.isNotEmpty(monthAnalyzeData.getMonth())) {
            //更新日期年月
            queryMap.put("yearmonth", monthAnalyzeData.getYearmonth());
        }else {
            //更新日期当年第一个月
            queryMap.put("businessstart", monthAnalyzeData.getBusinessyear() + "-01-01");
        }
        //更新日期当月
        queryMap.put("businessend", monthAnalyzeData.getBusinessdate());
        queryMap.put("salesuserid",monthAnalyzeData.getSalesuerid());
        queryMap.put("customersort",monthAnalyzeData.getCustomersort());
        queryMap.put("brandid",monthAnalyzeData.getBrandid());

        queryResultMap=new HashMap();
        queryResultMap=getSalesamountMap(queryMap); //去年销售金额

        if(null!=queryResultMap){
            monthAnalyzeData.setJnml((BigDecimal) queryResultMap.get("grossamount"));
            monthAnalyzeData.setJnxs((BigDecimal) queryResultMap.get("salesamount"));
        }
        if(monthAnalyzeData.getJnml()==null){
            monthAnalyzeData.setJnml(BigDecimal.ZERO);
        }
        if(monthAnalyzeData.getJnxs()==null){
            monthAnalyzeData.setJnxs(BigDecimal.ZERO);
        }
        //汇总数据今年销售毛利合计
        if(null==monthAnalyzeDataSum.getJnml()){
            monthAnalyzeDataSum.setJnml(BigDecimal.ZERO);
        }
        monthAnalyzeDataSum.setJnml(monthAnalyzeDataSum.getJnml().add(monthAnalyzeData.getJnml()));

        //汇总数据今年销售合计
        if(null==monthAnalyzeDataSum.getJnxs()){
            monthAnalyzeDataSum.setJnxs(BigDecimal.ZERO);
        }
        monthAnalyzeDataSum.setJnxs(monthAnalyzeDataSum.getJnxs().add(monthAnalyzeData.getJnxs()));

        /*=========今年销售 结束===========*/

        //计算其他项数据
        updateCalcSalesTargetMonthAnalyzeReportCommonData(monthAnalyzeData);

        //真实差异 第一目标对应进度差额
        if(monthAnalyzeDataRealDiffSum.getJdce_dyxsmb()==null){
            monthAnalyzeDataRealDiffSum.setJdce_dyxsmb(BigDecimal.ZERO);
        }
        //真实差异 第一目标 差额
        if(monthAnalyzeDataRealDiffSum.getCye_dyxsmb()==null){
            monthAnalyzeDataRealDiffSum.setCye_dyxsmb(BigDecimal.ZERO);
        }
        //真实差异 第一目标 差额
        if(monthAnalyzeDataRealDiffSum.getCye_dexsmb()==null){
            monthAnalyzeDataRealDiffSum.setCye_dexsmb(BigDecimal.ZERO);
        }
        //真实差异 毛利 第一目标对应进度差额
        if(monthAnalyzeDataRealDiffSum.getJdce_dymlmb()==null){
            monthAnalyzeDataRealDiffSum.setJdce_dymlmb(BigDecimal.ZERO);
        }
        //真实差异 毛利 第一目标 差额
        if(monthAnalyzeDataRealDiffSum.getCye_dymlmb()==null){
            monthAnalyzeDataRealDiffSum.setCye_dymlmb(BigDecimal.ZERO);
        }
        //真实差异 毛利 第一目标 差额
        if(monthAnalyzeDataRealDiffSum.getCye_demlmb()==null){
            monthAnalyzeDataRealDiffSum.setCye_demlmb(BigDecimal.ZERO);
        }

        //真实差异 第一目标对应进度差额
        if(monthAnalyzeData.getJdce_dyxsmb().compareTo(BigDecimal.ZERO)>0){
            monthAnalyzeDataRealDiffSum.setJdce_dyxsmb(
                    monthAnalyzeDataRealDiffSum.getJdce_dyxsmb().add(monthAnalyzeData.getJdce_dyxsmb()));
        }

        //真实差异  第一目标 差额
        if(monthAnalyzeData.getCye_dyxsmb().compareTo(BigDecimal.ZERO)>0){
            monthAnalyzeDataRealDiffSum.setCye_dyxsmb(
                    monthAnalyzeDataRealDiffSum.getCye_dyxsmb().add(monthAnalyzeData.getCye_dyxsmb()));
        }

        //真实差异  第二目标 差额
        if(monthAnalyzeData.getCye_dexsmb().compareTo(BigDecimal.ZERO)>0){
            monthAnalyzeDataRealDiffSum.setCye_dexsmb(
                    monthAnalyzeDataRealDiffSum.getCye_dexsmb().add(monthAnalyzeData.getCye_dexsmb()));
        }



        //真实差异 毛利第一目标对应进度差额
        if(monthAnalyzeData.getJdce_dymlmb().compareTo(BigDecimal.ZERO)>0){
            monthAnalyzeDataRealDiffSum.setJdce_dymlmb(
                    monthAnalyzeDataRealDiffSum.getJdce_dymlmb().add(monthAnalyzeData.getJdce_dymlmb()));
        }

        //真实差异 毛利第一目标 差额
        if(monthAnalyzeData.getCye_dymlmb().compareTo(BigDecimal.ZERO)>0){
            monthAnalyzeDataRealDiffSum.setCye_dymlmb(
                    monthAnalyzeDataRealDiffSum.getCye_dymlmb().add(monthAnalyzeData.getCye_dymlmb()));
        }

        //真实差异 毛利 第二目标 差额
        if(monthAnalyzeData.getCye_demlmb().compareTo(BigDecimal.ZERO)>0){
            monthAnalyzeDataRealDiffSum.setCye_demlmb(
                    monthAnalyzeDataRealDiffSum.getCye_demlmb().add(monthAnalyzeData.getCye_demlmb()));
        }
    }

    /**
     * 更新月度目标通用数据
     * @param monthAnalyzeData
     * @throws Exception
     */
    public void updateCalcSalesTargetMonthAnalyzeReportCommonData(SalesTargetMonthAnalyzeData monthAnalyzeData) throws Exception{
        //去年销售同期对比
        monthAnalyzeData.setJqnxstqb(BigDecimal.ZERO);
        //销售第一目标达成率
        monthAnalyzeData.setDcl_dyxsmb(BigDecimal.ZERO);
        //销售第二目标达率
        monthAnalyzeData.setDcl_dexsmb(BigDecimal.ZERO);
        //销售第一目标对应进度差额
        monthAnalyzeData.setJdce_dyxsmb(BigDecimal.ZERO);
        //销售第一目标差额
        monthAnalyzeData.setCye_dyxsmb(BigDecimal.ZERO);
        //销售第二目标差额
        monthAnalyzeData.setCye_dexsmb(BigDecimal.ZERO);

        //今年毛利率
        monthAnalyzeData.setJnmll(BigDecimal.ZERO);
        //去年销售毛利同期对比
        monthAnalyzeData.setJqnmltqb(BigDecimal.ZERO);
        //销售第一毛利目标达成率
        monthAnalyzeData.setDcl_dymlmb(BigDecimal.ZERO);
        //销售第二毛利目标达率
        monthAnalyzeData.setDcl_demlmb(BigDecimal.ZERO);
        //销售第一毛利目标对应进度差额
        monthAnalyzeData.setJdce_dymlmb(BigDecimal.ZERO);
        //销售第一毛利目标差额
        monthAnalyzeData.setCye_dymlmb(BigDecimal.ZERO);
        //销售第二毛利目标差额
        monthAnalyzeData.setCye_demlmb(BigDecimal.ZERO);

        BigDecimal tmpd=null;
        int scaleLen=6;
        if(monthAnalyzeData.getTimeschedule()!=null
                && monthAnalyzeData.getTimeschedule().compareTo(BigDecimal.ZERO)!=0){

            //去年同期对比
            if(monthAnalyzeData.getQnxs()!=null
                    && BigDecimal.ZERO.compareTo(monthAnalyzeData.getQnxs())!=0) {
                tmpd = monthAnalyzeData.getJnxs().divide(monthAnalyzeData.getQnxs(), scaleLen, BigDecimal.ROUND_HALF_UP);
                tmpd = tmpd.divide(monthAnalyzeData.getTimeschedule(), scaleLen, BigDecimal.ROUND_HALF_UP);
                monthAnalyzeData.setJqnxstqb(tmpd);
            }

            //第一目标 达成率
            if(monthAnalyzeData.getJndyxsmb()!=null
                    && BigDecimal.ZERO.compareTo(monthAnalyzeData.getJndyxsmb())!=0) {
                tmpd = monthAnalyzeData.getJnxs().divide(monthAnalyzeData.getJndyxsmb(), scaleLen, BigDecimal.ROUND_HALF_UP);
                tmpd = tmpd.divide(monthAnalyzeData.getTimeschedule(), scaleLen, BigDecimal.ROUND_HALF_UP);
                monthAnalyzeData.setDcl_dyxsmb(tmpd);
            }
            //第二目标 达成率
            if(monthAnalyzeData.getJndexsmb()!=null
                    && BigDecimal.ZERO.compareTo(monthAnalyzeData.getJndexsmb())!=0) {
                tmpd = monthAnalyzeData.getJnxs().divide(monthAnalyzeData.getJndexsmb(), scaleLen, BigDecimal.ROUND_HALF_UP);
                tmpd = tmpd.divide(monthAnalyzeData.getTimeschedule(), scaleLen, BigDecimal.ROUND_HALF_UP);
                monthAnalyzeData.setDcl_dexsmb(tmpd);
            }

            //第一目标对应进度差额
            tmpd=monthAnalyzeData.getJndyxsmb().multiply(monthAnalyzeData.getTimeschedule()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
            tmpd=tmpd.subtract(monthAnalyzeData.getJnxs()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
            monthAnalyzeData.setJdce_dyxsmb(tmpd);
            //第一目标 差额
            tmpd=monthAnalyzeData.getJndyxsmb().subtract(monthAnalyzeData.getJnxs()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
            monthAnalyzeData.setCye_dyxsmb(tmpd);
            //第二目标 差额
            tmpd=monthAnalyzeData.getJndexsmb().subtract(monthAnalyzeData.getJnxs()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
            monthAnalyzeData.setCye_dexsmb(tmpd);

            if(monthAnalyzeData.getJnxs().compareTo(BigDecimal.ZERO)!=0){
                tmpd=monthAnalyzeData.getJnml().divide(monthAnalyzeData.getJnxs(),scaleLen,BigDecimal.ROUND_HALF_UP);
                monthAnalyzeData.setJnmll(tmpd);
            }

            //去年同期毛利对比
            if(monthAnalyzeData.getQnml()!=null
                    && BigDecimal.ZERO.compareTo(monthAnalyzeData.getQnml())!=0) {
                tmpd = monthAnalyzeData.getJnml().divide(monthAnalyzeData.getQnml(), scaleLen, BigDecimal.ROUND_HALF_UP);
                tmpd = tmpd.divide(monthAnalyzeData.getTimeschedule(), scaleLen, BigDecimal.ROUND_HALF_UP);
                monthAnalyzeData.setJqnmltqb(tmpd);
            }

            //第一毛利目标 达成率
            if(monthAnalyzeData.getJndymlmb()!=null
                    && BigDecimal.ZERO.compareTo(monthAnalyzeData.getJndymlmb())!=0) {
                tmpd = monthAnalyzeData.getJnml().divide(monthAnalyzeData.getJndymlmb(), scaleLen, BigDecimal.ROUND_HALF_UP);
                tmpd = tmpd.divide(monthAnalyzeData.getTimeschedule(), scaleLen, BigDecimal.ROUND_HALF_UP);
                monthAnalyzeData.setDcl_dymlmb(tmpd);
            }
            //第二毛利目标 达成率
            if(monthAnalyzeData.getJndemlmb()!=null
                    && BigDecimal.ZERO.compareTo(monthAnalyzeData.getJndemlmb())!=0) {
                tmpd = monthAnalyzeData.getJnml().divide(monthAnalyzeData.getJndemlmb(), scaleLen, BigDecimal.ROUND_HALF_UP);
                tmpd = tmpd.divide(monthAnalyzeData.getTimeschedule(), scaleLen, BigDecimal.ROUND_HALF_UP);
                monthAnalyzeData.setDcl_demlmb(tmpd);
            }

            //第一毛利目标对应进度差额
            tmpd=monthAnalyzeData.getJndymlmb().multiply(monthAnalyzeData.getTimeschedule()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
            tmpd=tmpd.subtract(monthAnalyzeData.getJnml()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
            monthAnalyzeData.setJdce_dymlmb(tmpd);
            //第一毛利目标 差额
            tmpd=monthAnalyzeData.getJndymlmb().subtract(monthAnalyzeData.getJnml()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
            monthAnalyzeData.setCye_dymlmb(tmpd);
            //第二毛利目标 差额
            tmpd=monthAnalyzeData.getJndemlmb().subtract(monthAnalyzeData.getJnml()).setScale(scaleLen,BigDecimal.ROUND_HALF_UP);
            monthAnalyzeData.setCye_demlmb(tmpd);
        }
    }

    /**
     * 初始化毛利目标跟踪报表
     * @param list
     * @param lastYear
     * @param curYear
     * @author zhanghonghui
     * @date 2014-11-29
     */
    private void getInitSalesTargetMonthAnalyzeData(List<Map> list,String lastYear,String curYear) throws Exception{
        Map map=new LinkedHashMap();

        //销售
        List<SysCode> salesCodeList=getBaseSysCodeMapper().getSysCodeListForeign("SalesTargetMonthAnalyzeSalesReport");
        //毛利
        List<SysCode> grossCodeList=getBaseSysCodeMapper().getSysCodeListForeign("SalesTargetMonthAnalyzeGrossReport");
        Map<String,Object> codeMap=new LinkedHashMap<String,Object>();
        codeMap.put("销售",salesCodeList);
        codeMap.put("毛利",grossCodeList);
        for (Map.Entry entry : codeMap.entrySet()) {
            String key=(String)entry.getKey();
            List<SysCode> sysCodeList=(List<SysCode>)entry.getValue();
            if (null != sysCodeList && sysCodeList.size() > 0) {
                for (SysCode item : sysCodeList) {
                    map = new LinkedHashMap();
                    map.put("subjectsort",key);
                    map.put("subject", item.getCode());
                    if (item.getCode().startsWith("QN") || item.getCode().startsWith("JQN")) {
                        map.put("subjectname", item.getCodename().replace("{XX}", lastYear));
                    } else if (item.getCode().startsWith("JN")) {
                        map.put("subjectname", item.getCodename().replace("{XX}", curYear));
                    } else {
                        map.put("subjectname", item.getCodename());
                    }
                    list.add(map);
                }
                map.put("summarycolumn", 0.00);
            }
        }
    }

    /**
     * 转储到报表数据汇总
     * @param keySort brand 或 customersort
     * @param reportDataList
     * @param monthAnalyzeData
     * @throws Exception
     */
    private void setIntoSalesTargetMonthAnalyzeData(String keySort,List<Map> reportDataList,
                                    SalesTargetMonthAnalyzeData monthAnalyzeData) throws Exception {
        if (null == monthAnalyzeData) {
            return;
        }
        String key="";
        if("brand".equals(keySort)) {
            if (StringUtils.isEmpty(monthAnalyzeData.getBrandid())) {
                return;
            }
            key=monthAnalyzeData.getBrandid();
        }else if("customersort".equals(keySort)){
            if (StringUtils.isEmpty(monthAnalyzeData.getCustomersort())) {
                return;
            }
            key=monthAnalyzeData.getCustomersort();
        }else{
            return;
        }

        BigDecimal tmpd=null;
        for(Map map : reportDataList) {
            String subject = (String) map.get("subject");
            if("QNXS".equals(subject)){
                //去年销售
                map.put("target_"+ key,monthAnalyzeData.getQnxs().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("JNDYXSMB".equals(subject)){
                //今年第一销售目标
                map.put("target_" + key,monthAnalyzeData.getJndyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("JNDEXSMB".equals(subject)){
                //今年第二销售目标
                map.put("target_" + key,monthAnalyzeData.getJndexsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("JNXS".equals(subject)){
                //今年销售
                map.put("target_"+ key,monthAnalyzeData.getJnxs().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("JQNXSTQB".equals(subject)){
                //去年同期对比
                tmpd=monthAnalyzeData.getJqnxstqb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("target_"+ key,tmpd+ "%");
            } else if("DCL_DYXSMB".equals(subject)){
                //第一目标 达成率
                tmpd=monthAnalyzeData.getDcl_dyxsmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("target_"+ key,tmpd+ "%");
            } else if("DCL_DEXSMB".equals(subject)){
                //第二目标 达成率
                tmpd=monthAnalyzeData.getDcl_dexsmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("target_"+ key,tmpd+ "%");
            } else if("JDCE_DYXSMB".equals(subject)){
                //第一目标对应进度差额
                map.put("target_" + key,monthAnalyzeData.getJdce_dyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("CYE_DYXSMB".equals(subject)){
                //第一目标 差额
                map.put("target_" + key,monthAnalyzeData.getCye_dyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("CYE_DEXSMB".equals(subject)){
                //第二目标 差额
                map.put("target_"+ key,monthAnalyzeData.getCye_dexsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("QNML".equals(subject)){
                //去年销售毛利
                map.put("target_"+ key,monthAnalyzeData.getQnml().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("JNDYMLMB".equals(subject)){
                //今年第一销售毛利目标
                map.put("target_" + key,monthAnalyzeData.getJndymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("JNDEMLMB".equals(subject)){
                //今年第二销售毛利目标
                map.put("target_" + key,monthAnalyzeData.getJndemlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("JNML".equals(subject)){
                //今年销售毛利
                map.put("target_"+ key,monthAnalyzeData.getJnml().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("JNMLL".equals(subject)){
                //毛利率
                tmpd=monthAnalyzeData.getJnmll().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("target_"+ key,tmpd+ "%");
            }else if("JQNMLTQB".equals(subject)){
                //去年销售毛利同期对比
                tmpd=monthAnalyzeData.getJqnmltqb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("target_"+ key,tmpd+ "%");
            } else if("DCL_DYMLMB".equals(subject)){
                //第一销售毛利目标 达成率
                tmpd=monthAnalyzeData.getDcl_dymlmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("target_"+ key,tmpd+ "%");
            } else if("DCL_DEMLMB".equals(subject)){
                //第二销售毛利目标 达成率
                tmpd=monthAnalyzeData.getDcl_demlmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("target_"+ key,tmpd+ "%");
            } else if("JDCE_DYMLMB".equals(subject)){
                //第一销售毛利目标对应进度差额
                map.put("target_" + key,monthAnalyzeData.getJdce_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("CYE_DYMLMB".equals(subject)){
                //第一销售毛利目标 差额
                map.put("target_" + key,monthAnalyzeData.getCye_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if("CYE_DEMLMB".equals(subject)){
                //第二销售毛利目标 差额
                map.put("target_"+key,monthAnalyzeData.getCye_demlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
    }

    /**
     * 存储合计列表到报表列表
     * @param reportDataList
     * @param monthAnalyzeDataSum
     * @param monthAnalyzeDataDiff
     * @throws Exception
     */
    private void updateSumSalesTargetMonthAnalyzeDataTotal(List<Map> reportDataList,
                                  SalesTargetMonthAnalyzeData monthAnalyzeDataSum,
                                  SalesTargetMonthAnalyzeData monthAnalyzeDataDiff) throws Exception {
        BigDecimal tmpd=null;
        BigDecimal timeSchedule=null;

        if(monthAnalyzeDataSum.getTimeschedule()!=null){
            timeSchedule=monthAnalyzeDataSum.getTimeschedule().multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }else{
            timeSchedule=BigDecimal.ZERO;
        }
        for(Map map : reportDataList) {
            String subject = (String) map.get("subject");
            map.put("month",monthAnalyzeDataSum.getMonth());
            map.put("year",monthAnalyzeDataSum.getLastyear());
            map.put("businessdate",monthAnalyzeDataSum.getBusinessdate());
            map.put("timeschedule",timeSchedule+"%");
            if ("QNXS".equals(subject)) {
                //去年销售
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getQnxs().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("JNDYXSMB".equals(subject)) {
                //今年第一销售目标
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getJndyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("JNDEXSMB".equals(subject)) {
                //今年第二销售目标
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getJndexsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("JNXS".equals(subject)) {
                //今年销售
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getJnxs().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("JQNXSTQB".equals(subject)) {
                //去年同期对比
                tmpd = monthAnalyzeDataSum.getJqnxstqb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd + "%");
            } else if ("DCL_DYXSMB".equals(subject)) {
                //第一目标 达成率
                tmpd = monthAnalyzeDataSum.getDcl_dyxsmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd + "%");
            } else if ("DCL_DEXSMB".equals(subject)) {
                //第二目标 达成率
                tmpd = monthAnalyzeDataSum.getDcl_dexsmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd + "%");
            } else if ("JDCE_DYXSMB".equals(subject)) {
                //第一目标对应进度差额
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getJdce_dyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));

                map.put("realdiffcolumn",
                        monthAnalyzeDataDiff.getJdce_dyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));

            } else if ("CYE_DYXSMB".equals(subject)) {
                //第一目标 差额
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getCye_dyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));

                map.put("realdiffcolumn",
                        monthAnalyzeDataDiff.getCye_dyxsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("CYE_DEXSMB".equals(subject)) {
                //第二目标 差额
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getCye_dexsmb().setScale(2, BigDecimal.ROUND_HALF_UP));

                map.put("realdiffcolumn",
                        monthAnalyzeDataDiff.getCye_dexsmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }else if ("QNML".equals(subject)) {
                //去年销售毛利
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getQnml().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("JNDYMLMB".equals(subject)) {
                //今年第一销售毛利目标
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getJndymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("JNDEMLMB".equals(subject)) {
                //今年第二销售毛利目标
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getJndemlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("JNML".equals(subject)) {
                //今年销售毛利
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getJnml().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("JNMLL".equals(subject)) {
                //毛利率
                tmpd = monthAnalyzeDataSum.getJnmll().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd + "%");
            } else if ("JQNMLTQB".equals(subject)) {
                //去年销售毛利同期对比
                tmpd = monthAnalyzeDataSum.getJqnmltqb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd + "%");
            } else if ("DCL_DYMLMB".equals(subject)) {
                //第一销售毛利目标 达成率
                tmpd = monthAnalyzeDataSum.getDcl_dymlmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd + "%");
            } else if ("DCL_DEMLMB".equals(subject)) {
                //第二销售毛利目标 达成率
                tmpd = monthAnalyzeDataSum.getDcl_demlmb().multiply(
                        new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                map.put("summarycolumn", tmpd + "%");
            } else if ("JDCE_DYMLMB".equals(subject)) {
                //第一销售毛利目标对应进度差额
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getJdce_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));

                map.put("realdiffcolumn",
                        monthAnalyzeDataDiff.getJdce_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("CYE_DYMLMB".equals(subject)) {
                //第一销售毛利目标 差额
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getCye_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));

                map.put("realdiffcolumn",
                        monthAnalyzeDataDiff.getCye_dymlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if ("CYE_DEMLMB".equals(subject)) {
                //第二销售毛利目标 差额
                map.put("summarycolumn",
                        monthAnalyzeDataSum.getCye_demlmb().setScale(2, BigDecimal.ROUND_HALF_UP));

                map.put("realdiffcolumn",
                        monthAnalyzeDataDiff.getCye_demlmb().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
    }

    //===========================================================================
    //其他数据接口
    //===========================================================================

    /**
     * 销售金额
     * @param queryMap
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-08
     */
    private Map getSalesamountMap(Map queryMap) throws Exception{
        String yearmonth=(String)queryMap.get("yearmonth");
        String businessstart=(String)queryMap.get("businessstart");
        String businessend=(String)queryMap.get("businessend");
        String customersort=(String)queryMap.get("customersort");
        String brandid=(String)queryMap.get("brandid");
        String salesuser=(String)queryMap.get("salesuserid");

        Map requestMap=new HashMap();

        if(null!=yearmonth && !"".equals(yearmonth.trim())) {
            requestMap.put("businessyearmonth", yearmonth.trim());
        }
        if(null!=businessstart && !"".equals(businessstart.trim())) {
            requestMap.put("businessdate1", businessstart.trim());
        }
        if(null!=businessend && !"".equals(businessend.trim())) {
            requestMap.put("businessdate2", businessend.trim());
        }
        if(null!=brandid && !"".equals(brandid.trim())){
            requestMap.put("brand", brandid.trim());
        }
        if(null!=salesuser && !"".equals(salesuser.trim())){
            requestMap.put("salesuser", salesuser.trim());
        }
        if(null!=customersort && !"".equals(customersort.trim())){
            requestMap.put("customersort", customersort.trim());
        }
        PageMap pageMap=new PageMap();
        pageMap.setCondition(requestMap);
        Map saleAmountMap=salesBillCheckMapper.getSalesBillSalesAmountSum(pageMap);
        return saleAmountMap;
    }

    /**
     * 销售目标合计金额
     * @param queryMap
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2016-08-08
     */
    private Map getSalesTargetInputSumMap(Map queryMap) throws Exception{
        String yearmonth=(String)queryMap.get("yearmonth");
        String businessstart=(String)queryMap.get("businessstart");
        String businessend=(String)queryMap.get("businessend");
        String customersort=(String)queryMap.get("customersort");
        String brandid=(String)queryMap.get("brandid");
        String salesuserid=(String)queryMap.get("salesuserid");

        Map requestMap=new HashMap();

        if(null!=yearmonth && !"".equals(yearmonth.trim())) {
            requestMap.put("yearmonth", yearmonth.trim());
        }

        if(null!=businessstart && !"".equals(businessstart.trim())) {
            requestMap.put("yearmonthstart", businessstart.trim());
        }

        if(null!=businessend && !"".equals(businessend.trim())) {
            requestMap.put("yearmonthend", businessend.trim());
        }

        if(null!=brandid && !"".equals(brandid.trim())){
            requestMap.put("brandid", brandid.trim());
        }
        if(null!=salesuserid && !"".equals(salesuserid.trim())){
            requestMap.put("salesuserid", salesuserid.trim());
        }
        if(null!=customersort && !"".equals(customersort.trim())){
            requestMap.put("customersort", customersort.trim());
        }
        requestMap.put("statusarr","3,4");
        Map saleAmountMap=salesTargetInputMapper.getSalesTargetInputSum(requestMap);
        return saleAmountMap;
    }

    /**
     * 计算月度分解时间进度
     * @param iMonth
     * @param businessdate
     * @return
     * @throws Exception
     */
    public BigDecimal getCalcMonthAnalyzeTimeSchedule(Integer iMonth,String businessdate) throws Exception{
        BigDecimal resultSchedule=BigDecimal.ZERO;
        int dayIndexInMonth=0;
        int dayCountInMonth=0;

        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = smf.parse(businessdate);
        // 获得Calendar实例
        Calendar calendar = Calendar.getInstance();
        // 根据date赋值
        calendar.setTime(date);
        // 计算是当月的第几天
        dayIndexInMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // 计算当月的第一天
        calendar.add(Calendar.DATE, 1 - dayIndexInMonth);
        // 计算下月的第一天
        calendar.add(Calendar.MONTH, 1);
        // 计算当月的最后一天
        calendar.add(Calendar.DATE, -1);
        // 计算是当月一共几天
        dayCountInMonth = calendar.get(Calendar.DAY_OF_MONTH);

        String[] businessdateArr=businessdate.split("-");
        String businessYear=businessdateArr[0];
        String businessMonth=businessdateArr[1];
        String businessDay=businessdateArr[2];
        int iBusinessYear=Integer.parseInt(businessYear.trim());
        int iBusinessMonth=Integer.parseInt(businessMonth.trim());

        String businessYearFirstDay=businessYear+"-01-01";

        if(null!=iMonth){
            if(iMonth==iBusinessMonth){
                BigDecimal dayIndexDec=new BigDecimal(dayIndexInMonth);
                BigDecimal dayCountDec=new BigDecimal(dayCountInMonth);
                resultSchedule=dayIndexDec.divide(dayCountDec,4,BigDecimal.ROUND_HALF_UP);
            }else if(iMonth<iBusinessMonth){
                resultSchedule=BigDecimal.ONE;
            }
        }else{

            int daysOfYear=365;
            if(iBusinessYear % 4 == 0 && iBusinessYear % 100 != 0 || iBusinessYear % 400 == 0){
                daysOfYear=366;
            }
            int betweenDayCount=CommonUtils.daysBetween(businessYearFirstDay,businessdate);
            if(betweenDayCount>0){
                BigDecimal dayCountDec=new BigDecimal(betweenDayCount+1);
                BigDecimal yearDayCountDec=new BigDecimal(daysOfYear);
                resultSchedule=dayCountDec.divide(yearDayCountDec, 4, BigDecimal.ROUND_HALF_UP);
            }
        }

        return resultSchedule;
    }
    /**
     * 计算月时间进度，年进度
     * @param businessdate
     * @return
     * @throws Exception
     */
    public Map getCalcYearAndMonthTimeSchedule(String businessdate) throws Exception{
        Map resultMap=new HashMap();
        int dayIndexInMonth=0;
        int dayCountInMonth=0;

        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = smf.parse(businessdate);
        // 获得Calendar实例
        Calendar calendar = Calendar.getInstance();
        // 根据date赋值
        calendar.setTime(date);
        // 计算是当月的第几天
        dayIndexInMonth = calendar.get(Calendar.DAY_OF_MONTH);
        // 计算当月的第一天
        calendar.add(Calendar.DATE, 1 - dayIndexInMonth);
        // 计算下月的第一天
        calendar.add(Calendar.MONTH, 1);
        // 计算当月的最后一天
        calendar.add(Calendar.DATE, -1);
        // 计算是当月一共几天
        dayCountInMonth = calendar.get(Calendar.DAY_OF_MONTH);

        String[] businessdateArr=businessdate.split("-");
        String businessYear=businessdateArr[0];
        String businessYearFirstDay=businessYear+"-01-01";
        Integer iYear=Integer.parseInt(businessYear);

        int dayCountBusiness=CommonUtils.daysBetween(businessYearFirstDay,businessdate);
        int daysOfYear=365;
        if(iYear % 4 == 0 && iYear % 100 != 0 || iYear % 400 == 0){
            daysOfYear=366;
        }
        BigDecimal dayIndexDec=new BigDecimal(dayIndexInMonth);
        BigDecimal dayCountDec=new BigDecimal(dayCountInMonth);
        BigDecimal monthScheduleDec=dayIndexDec.divide(dayCountDec,4,BigDecimal.ROUND_HALF_UP);
        resultMap.put("monthTimeSchedule",monthScheduleDec);

        BigDecimal daysInMonthDec=new BigDecimal(dayCountBusiness+1);
        BigDecimal daysOfYearDec=new BigDecimal(daysOfYear);
        BigDecimal yearScheduleDec=daysInMonthDec.divide(daysOfYearDec,4,BigDecimal.ROUND_HALF_UP);
        resultMap.put("yearTimeSchedule",yearScheduleDec);

        return resultMap;
    }
}
