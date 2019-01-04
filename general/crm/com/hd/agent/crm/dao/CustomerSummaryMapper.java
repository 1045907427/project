package com.hd.agent.crm.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CrmSalesOrder;
import com.hd.agent.crm.model.CustomerStorageOrder;
import com.hd.agent.crm.model.CustomerSummary;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface CustomerSummaryMapper {

    /**
     *查询客户库存明细报表数据
     * @author lin_xx
     * @date 2016/10/19
     */
    List<Map> getCustomerSummaryReportData(PageMap pageMap);
    /**
     * 查询客户库存报表数据合计
     * @author lin_xx
     * @date 2016/10/19
     */
    int getCustomerSummaryReportDataCount(PageMap pageMap);
    /**
     * 获取客户库存报表数据 页脚
     * @author lin_xx
     * @date 2016/10/19
     */
    Map getCustomerSummaryReportDataSum(PageMap pageMap);
     /**
      * 获取指定客户最新的库存日期
      * @author lin_xx
      * @date 2016/10/25
      */
    String getLastSummaryDayByCustomerid(String id);
    /**
     * 获取指定客户最新的历史库存日期
     * @author lin_xx
     * @date 2016/10/25
     */
    String getLastSummaryDayFromHistory(String id);
     /**
      * 根据条件获取销售情况统计表中对应客户的商品销售情况
      * @author lin_xx
      * @date 2016/10/27
      */
    List<Map> getCustomerSummaryFromSalesReport(PageMap pageMap);
     /**
      * 验证在客户库存中是否存在对应客户的对应商品
      * @author lin_xx
      * @date 2016/10/27
      */
    CustomerSummary validateGoodsForCustomer(@Param("goodsid")String goodsid ,@Param("customerid")String customerid);
    /**
     * 验证在客户历史库存中是否存在同个日期对应客户的对应商品
     * @author lin_xx
     * @date 2016/10/27
     */
    int validateGoodsForCustomer_h(@Param("goodsid")String goodsid ,@Param("customerid")String customerid,@Param("businessdate")String businessdate);
     /**
      * 获取客户库存
      * @author lin_xx
      * @date 2016/11/1
      */
     List<CustomerSummary> getCustomerSummaryByCustomerid(@Param("customerid")String customerid);
     /**
      * 添加客户库存历史记录
      * @author lin_xx
      * @date 2016/11/1
      */
    int addCustomerSummary_h(CustomerSummary customerSummary);
     /**
      * 查询客户对应商品的客户库存详细信息
      * @author lin_xx
      * @date 2016/11/17
      */
    List<Map> getCustomerSummaryForGoodsHistoryList(Map map);
     /**
      * 查询有库存的所有客户
      * @author lin_xx
      * @date 2016/11/28
      */
    List<Map> getCustomerFromStorage();
     /**
      * 删除指定编号的客户库存
      * @author lin_xx
      * @date 2016/11/30
      */
     int deleteSummaryListByCustomer(@Param("customerid")String customerid);
    /**
     * 删除指定编号的客户历史库存
     * @author lin_xx
     * @date 2016/11/30
     */
    int deleteSummaryHistoryListByCustomer(@Param("customerid")String customerid);
    /**
     * 根据编号删除库存信息
     */
    int deleteCustomerSummary(Integer id);
    /**
     * 根据客户和商品删除库存信息
     */
    int deleteSummaryByCustomerAndGoods(@Param("customerid")String customerid,@Param("goodsid")String goodsid);
    /**
     * 根据客户和商品删除库存历史记录
     */
    int deleteSummaryHistoryByCustomerAndGoods(@Param("customerid")String customerid,@Param("goodsid")String goodsid);
    /**
     * 保存库存信息
     */
    int addCustomerSummary(CustomerSummary customerSummary);
    /**
     * 更新库存信息
     */
    int updateCustomerSummary(CustomerSummary customerSummary);
    /**
     *查询客户库存报表小计列数据
     * @author lin_xx
     * @date 2018/02/06
     */
    List<Map> getCustomerSummaryReportGroupData(PageMap pageMap);
    /**
     *查询客户库存报表小计列数据条数
     * @author lin_xx
     * @date 2018/02/06
     */
    int getCustomerSummaryReportGroupCount(PageMap pageMap);
    /**
     *
     * @throws
     * @author lin_xx
     * @date 2018-02-06
     */
    Map getCustomerSummaryReportGroupSum(PageMap pageMap);

}