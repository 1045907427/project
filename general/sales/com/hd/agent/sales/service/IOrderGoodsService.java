package com.hd.agent.sales.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.model.OrderGoods;
import com.hd.agent.sales.model.OrderGoodsDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2017/10/16.
 */
public interface IOrderGoodsService {
    /**
     * 销售订货单列表
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public PageData getOrderGoodsList(PageMap pageMap) throws Exception;

    /**
     * 添加销售订货单
     * @param orderGoods
     * @param saveaudit
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public Map addOrderGoods(OrderGoods orderGoods,String saveaudit) throws Exception;

    /**
     * 修改销售订货单单据状态
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public Map auditOrderGoods(String type, String id, String model) throws Exception;

    /**
     * 根据编号获取销售订货单
     * @param id
     * @return com.hd.agent.sales.model.OrderGoods
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public OrderGoods getOrderGoods(String id) throws Exception;

    /**
     * 修改销售订货单
     * @param orderGoods
     * @param saveaudit
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public Map updateOrderGoods(OrderGoods orderGoods,String saveaudit) throws Exception;

    /**
     * 判断订货单能否审核
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public Map checkOrderGoodsAudit(String id) throws Exception;

    /**
     * 删除销售订货单
     * @param id
     * @return java.lang.Boolean
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public Boolean deleteOrderGoods(String id);

    /**
     * 销售订货单批量审核
     * @param ids
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public Map auditMultiOrderGoods(String ids) throws Exception;

    /**
     * 根据销售订货单生成销售订单
     * @param id
     * @param orderDetailList
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public Map addOrderByOrderGoodsBill(String id,List<OrderDetail> orderDetailList) throws Exception;

    /**
     * 获取可以生成订单的订货单数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public PageData getOrderGoodsListForAddOrder(PageMap pageMap) throws Exception;

    /**
     * 获取销售订货单里未生成订单的数据
     * @param id
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public List showGoodsToOrderData(String id) throws Exception;


    /**
     * 获取符合要货单的订货单
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public PageData getDemandAddOrderGoodsData(PageMap pageMap) throws Exception;

    public List getSalesOrderGoodsListBy(Map map) throws Exception;

    /**
     * 更新打印次数
     * @param order
     * @author zhanghonghui
     * @date 2013-9-10
     */
    public boolean updateOrderGoodsPrinttimes(OrderGoods order) throws Exception;

    /**
     * 作废或取消作废订单
     * @param id
     * @param type 1作废2取消作废
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 26, 2013
     */
    public Map doInvalidOrderGoods(String id, String type) throws Exception;

    /**
     * 获取销售订货单导出数据
     * @param
     * @throws
     * @author luoqiang
     * @date Jan 05, 2018
     */
    public List getOrderGoodsExportData(PageMap pageMap) throws Exception;

    /**
     * 判断订货单是否关联过销售订单或要货单
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 14, 2018
     */
    public Map checkInvalidOrderGoods(String id);

    /**
     * 根据主键编码获取订货单明细数据
     * @param id
     * @return com.hd.agent.sales.model.OrderGoodsDetail
     * @throws
     * @author luoqiang
     * @date Mar 30, 2018
     */
    public OrderGoodsDetail getOrderGoodsDetail(int id);
}
