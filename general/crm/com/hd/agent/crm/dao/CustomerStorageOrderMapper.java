package com.hd.agent.crm.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CustomerStorageOrder;
import com.hd.agent.crm.model.CustomerStorageOrderDetail;

import java.util.List;
import java.util.Map;

public interface CustomerStorageOrderMapper {

    /**
     * 根据条件获取客户销量数据
     * @author lin_xx
     * @date 2016/11/8
     * @param pageMap
     * @return
     */
    List<CustomerStorageOrder> getCustomerStorageOrderData(PageMap pageMap);
    /**
     * 根据条件 获取客户销量数据 条数
     * @author lin_xx
     * @date 2016/11/8
     * @param pageMap
     * @return
     */
    int getCustomerStorageOrderDataCount(PageMap pageMap);
     /**
      * 根据编号 删除客户库存单据
      * @author lin_xx
      * @date 2016/11/10
      */
    int deleteByCustomerStorageOrderId(String id);
     /**
      * 保存客户库存单据
      * @author lin_xx
      * @date 2016/11/10
      */
    int addCustomerStorageOrder(CustomerStorageOrder record);
     /**
      * 根据编号查找客户库存单据
      * @author lin_xx
      * @date 2016/11/10
      */
    CustomerStorageOrder getCustomerStorageOrderById(String id);
    /**
     * 更新 客户库存单据
     */
    int updateCustomerStorageOrder(CustomerStorageOrder customerStorageOrder);
    /**
     * 查询 客户库存单据总金额
     * @param id
     * @return
     */
    Map getCustomerStorageOrderDetailTotal(String id);
    /**
     * 根据编号删除客户库存单据及其明细
     */
    int deleteByOrderid(String id);
    /**
     * 添加客户库存单据及其明细
     * @author lin_xx
     * @date 2016/11/10
     */
    int addCustomerStorageOrderDetail(CustomerStorageOrderDetail customerStorageOrderDetail);
     /**
      * 查询客户库存单据及其明细
      * @author lin_xx
      * @date 2016/11/10
      */
    List<CustomerStorageOrderDetail> getCustomerStorageOrderDetailByOrderid(String id);
    /**
     * 根据keyid获取订单
     * @author lin_xx
     * @date 2016/11/8
     */
    CustomerStorageOrder getCustomerStorageOrderByKeyid(String keyid);
     /**
      * 获取指定客户的审核通过状态的客户库存单据
      * @author lin_xx
      * @date 2016/11/30
      */
     List<CustomerStorageOrder> getStorageOrderListByCustomerid(String cutomerid);

}