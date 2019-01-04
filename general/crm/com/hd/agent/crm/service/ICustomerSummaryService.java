package com.hd.agent.crm.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.List;
import java.util.Map;

/**
 * Created by lin_xx on 2016/10/25.
 */
public interface ICustomerSummaryService {

    /**
     *获取客户库存明细 报表
     * @author lin_xx
     * @date 2016/10/19
     */
    public PageData getCustomerSummaryReportData(PageMap pageMap) throws Exception;
     /**
      * 获取指定客户 最新的客户库存时间
      * @author lin_xx
      * @date 2016/10/25
      */
    public String getLastSummaryDay(String customerid) throws Exception;
     /**
      * 更新客户库存
      * @author lin_xx
      * @date 2016/10/27
      */
    public Map updateSummaryByCustomer(PageMap pageMap) throws Exception ;
     /**
      * 客户销量更新
      * @author lin_xx
      * @date 2016/10/27
      */
    public Map crmSalesSync(PageMap pageMap) throws Exception ;
     /**
      * 清除指定客户的库存
      * @author lin_xx
      * @date 2016/11/1
      */
    public Map clearCustomerSummary(String customerid) throws Exception;
     /**
      * 获取库存周转天数报表数据
      * @author lin_xx
      * @date 2016/11/8
      */
    public PageData getCustomerSummaryRevolutionReportData(PageMap pageMap) throws Exception;
    /**
     * 获取客户商品库存历史数据
     * @author lin_xx
     * @date 2016/11/17
     */
    public List<Map> getCustomerSummaryForGoodsHistoryList(Map map) throws Exception;
     /**
      * 获取客户库存表里所有的客户
      * @author lin_xx
      * @date 2016/11/28
      */
     public List<Map> getAllSummaryCustomer() throws Exception;

     /**
      * 获取客户库存小计列报表数据
      * @throws
      * @author lin_xx
      * @date 2018-02-06
      */
     public PageData getCustomerSummaryReportGroupData(PageMap pageMap) throws Exception;

}
