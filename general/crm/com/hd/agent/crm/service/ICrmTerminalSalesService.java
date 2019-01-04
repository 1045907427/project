package com.hd.agent.crm.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CrmSalesOrder;
import com.hd.agent.sales.model.ModelOrder;
import net.sf.json.JSONObject;
import org.activiti.engine.impl.Page;

import java.util.*;

/**
 * Created by lin_xx on 2016/9/23.
 */
public interface ICrmTerminalSalesService {
     /**
      *获取销售终端数据列表
      * @author lin_xx
      * @date 2016/9/23
      */
    public PageData getTerminalOrderData(PageMap pageMap) throws Exception ;
     /**
      *保存新增的销售终端数据
      * @author lin_xx
      * @date 2016/9/27
      */
    public Map addTerminalOrder(CrmSalesOrder crmSalesOrder) throws Exception;
     /**
      *根据单据编号 获取单据详情
      * @author lin_xx
      * @date 2016/9/29
      */
    public CrmSalesOrder getTerminalOrderById(String id) throws Exception;
     /**
      * 保存修改
      * @author lin_xx
      * @date 2016/9/29
      */
    public boolean editTerminalOrder(CrmSalesOrder crmSalesOrder) throws Exception;
    /**
     * 删除明细同时根据记录条数删除单据
     * @param id
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2016/9/29
     */
    public boolean deleteTerminalSalesOrder(String id) throws Exception ;
    /**
     * 根据导入的模板参数 获取终端订单明细
     * @param wareList
     * @return
     * @throws Exception
     */
    public Map changeModelForDetail(List<ModelOrder> wareList, String gtype) throws Exception;
     /**
      * 查询客户销量报表数据
      * @author lin_xx
      * @date 2016/10/20
      */
    public PageData getTerminalSalesOrderReportData(PageMap pageMap) throws Exception;
     /**
      * 手机客户销量订单上传
      * @author lin_xx
      * @date 2016/11/7
      */
     public Map addPhoneTerminalOrder(JSONObject jsonObject) throws Exception ;
     /**
      * 根据条件查找不同时间段客户门店销量情况
      * @throws
      * @author lin_xx
      * @date 2017/10/20
      */
     public PageData getCustomerTerminalSalesData(PageMap pageMap) throws Exception;


}
