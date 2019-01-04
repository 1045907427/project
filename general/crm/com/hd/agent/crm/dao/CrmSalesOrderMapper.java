package com.hd.agent.crm.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CrmSalesOrder;
import com.hd.agent.crm.model.CrmSalesOrderDetail;

import java.util.List;
import java.util.Map;

public interface CrmSalesOrderMapper {

    /**
     * 根据主键删除记录
     */
    int deleteCrmSalesOrderById(String id);

    /**
     * 根据条件获取客户销量数据
     * @param pageMap
     * @return
     */
    List<CrmSalesOrder> getTerminalOrderData(PageMap pageMap);
    /**
     * 根据条件 获取客户销量数据 条数
     * @param pageMap
     * @return
     */
    int getTerminalOrderDataCount(PageMap pageMap);
    /**
     * 保存客户销量数据 条数
     */
    int addCrmSalesOrder(CrmSalesOrder crmSalesOrder);

    /**
     * 根据主键查询记录
     */
    CrmSalesOrder getCrmSalesOrderById(String id);
    /**
     * 更新单据
     */
    int updateCrmSalseOrder(CrmSalesOrder crmSalesOrder);
     /**
      *获取客户销量（客户销量）报表数据
      * @author lin_xx
      * @date 2016/10/20
      */
    List<Map> getTerminalSalesOrderReportData(PageMap pageMap);
    /**
     *获取客户销量（客户销量）报表数据 合计
     * @author lin_xx
     * @date 2016/10/20
     */
    int getTerminalSalesOrderReportDataCount(PageMap pageMap);
    /**
     * 判断手机订单是否存在
     */
    CrmSalesOrder getCrmSalesOrderByKeyid(String keyid);
    /**
     * 根据条件获取商品库存的数量
     * @author lin_xx
     * @date 2016/11/2
     */
    Map getCrmSalesGoodsNumByDate(PageMap pageMap);
    /**
     * 保存记录
     */
    int addCrmSalesOrderDetail(CrmSalesOrderDetail crmSalesOrderDetail);
    /**
     * 根据主键查询记录
     */
    List<CrmSalesOrderDetail> getCrmOrderDetailByOrderid(String orderid);
    /**
     * 根据单据编号删除明细
     */
    int deleteByorderid(String id);
    /**
     * 获取订单总金额
     */
    Map getCrmOrderDetailTotal(String id);
    /**
     * 根据条件查找不同时间段客户门店销量情况
     * @throws
     * @author lin_xx
     * @date 2017/10/20
     */
    List<Map> getCustomerTerminalSalesData(PageMap pageMap) throws Exception;
    /**
     * 根据条件查找不同时间段客户门店销量情况数量
     * @throws
     * @author lin_xx
     * @date 2017/10/20
     */
    int getCustomerTerminalSalesDataCount(PageMap pageMap) throws Exception;

}