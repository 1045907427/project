package com.hd.agent.crm.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CustomerStorageOrder;
import com.hd.agent.sales.model.ModelOrder;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by lin_xx on 2016/10/12.
 */
public interface ICustomerStorageOrderService {

     /**
      *获取客户库存列表数据
      * @author lin_xx
      * @date 2016/10/12
      */
    public PageData getCustomerStorageOrderListPageData(PageMap pageMap) throws Exception;
     /**
      *根据单据编号获取单据明细
      * @author lin_xx
      * @date 2016/10/12
      */
    public CustomerStorageOrder getCustomerStorageOrderById(String id) throws Exception ;

     /**
      *增加客户库存单据
      * @author lin_xx
      * @date 2016/10/12
      */
    public Map addCustomerStorageOrder(CustomerStorageOrder customerStorageOrder) throws Exception;
     /**
      *删除客户库存单据
      * @author lin_xx
      * @date 2016/10/14
      */
    public boolean deleteCustomerStorageSalesOrder(String id) throws Exception ;
     /**
      *保存客户库存单据
      * @author lin_xx
      * @date 2016/10/13
      */
    public boolean editCustomerStorage(CustomerStorageOrder customerStorageOrder) throws Exception;
     /**
      *审核客户库存单据 并生成 客户库存
      * @author lin_xx
      * @date 2016/10/14
      */
    public Map auditCustomerStorage(String id) throws Exception;
    /**
     * 反审客户库存单据 并修改有客户库存的商品库存信息
     * @author lin_xx
     * @date 2016/10/14
     */
    public Map oppAuditCustomerStorage(String id) throws Exception;
    /**
     * 根据导入的模板参数 获取客户库存订单明细
     * @param wareList
     * @return
     * @throws Exception
     */
    public Map changeModelForDetail(List<ModelOrder> wareList, String gtype) throws Exception;
    /**
     * 手机客户库存订单上传
     * @author lin_xx
     * @date 2016/11/7
     */
    public Map addPhoneStorageOrder(JSONObject jsonObject) throws Exception ;

}
