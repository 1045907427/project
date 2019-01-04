package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.model.OrderGoods;
import com.hd.agent.sales.model.OrderGoodsDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2017/10/16.
 */
public interface OrderGoodsMapper {
    /**
     * 销售订货单查询
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public List getOrderGoodsList(PageMap pageMap);

    /**
     * 销售订货单查询
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public int getOrderGoodsCount(PageMap pageMap);

    /**
     * 添加销售订货单明细数据
     * @param list
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public int addOrderGoodsDetailList(List list);

    /**
     * 添加销售订货单明细
     * @param
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public int addOrderGoodsDetail(OrderGoodsDetail orderGoodsDetail);

    /**
     * 添加销售订货单
     * @param orderGoods
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public int addOrderGoods(OrderGoods orderGoods);

    /**
     * 修改单据状态
     * @param orderGoods
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public int auditOrderGoods(OrderGoods orderGoods);

    /**
     * 根据编号获取销售订货单
     * @param id
     * @return com.hd.agent.sales.model.OrderGoods
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public OrderGoods getOrderGoods(String id);

    /**
     * 获取销售订货单明细数据
     * @param id
     * @param orderstr
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 16, 2017
     */
    public List getOrderGoodsDetailList(@Param("id")String id,@Param("orderstr")String orderstr);

    /**
     * 根据订货单编号和品牌编号 获取明细列表
     * @param orderid
     * @param brandid
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public List getOrderGoodsDetailListByOrderidAndBrandid(@Param("orderid")String orderid,@Param("brandid")String brandid);

    /**
     * 修改销售订货单
     * @param orderGoods
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public int updateOrderGoods(OrderGoods orderGoods);

    /**
     * 获取订货单明细的合计
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public Map getOrderGoodsDetailTotal(@Param("id")String id);

    /**
     * 验证销售订货单是否重复
     * @param id
     * @param customerid
     * @param days
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public Map checkOrderGoodsRepeat(@Param("id")String id,@Param("customerid")String customerid,@Param("days")String days);

    /**通过订货单编码删除商品明细*/
    public int deleteOrderGoodsDetailByOrderId(String id);

    /**
     * 删除销售订货单
     * @param id
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public int deleteOrderGoods(String id);

    /**
     * 生成销售订单后回写订货单数量
     * @param orderid
     * @param type 1生成订单回写2删除订单回写
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 17, 2017
     */
    public int updateOrderGoodsDetailNum(@Param("orderid")String orderid,@Param("type")String type,@Param("ordergoodsid")String ordergoodsid);

//    /**
//     * 获取生成了订单的订货单
//     * @param id
//     * @param orderid
//     * @return java.util.List
//     * @throws
//     * @author luoqiang
//     * @date Oct 17, 2017
//     */
//    public List getOrderGoodsDetailInOrder(@Param("id")String id,@Param("orderid")String orderid);

    /**
     * 获取可以生成订单的订货单数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public List getOrderGoodsListForAddOrderList(PageMap pageMap);

    /**
     * 获取可以生成订单的订货单数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public int getOrderGoodsListForAddOrderCount(PageMap pageMap);

    /**
     * 获取订货单的总金额，已生成订单金额，未生成订单金额
     * @param orderid
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public Map getOrderGoodsAmount(@Param("orderid")String orderid);

    /**
     * 获取销售订货单可以生成订单的数据
     * @param orderid
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public List showGoodsToOrderData(@Param("orderid")String orderid);

    /**
     * 判断销售订货单是否生成过销售订单
     * @param orderid
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public String getOrderGoodsInOrder(@Param("orderid")String orderid);

    /**
     * 获取符合要货单的订货单
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public List getDemandAddOrderGoodsList(PageMap pageMap);

    /**
     * 获取符合要货单的订货单
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Oct 18, 2017
     */
    public int getDemandAddOrderGoodsCount(PageMap pageMap);

    /**
     * 获取销售订单列表<br/>
     * map中参数<br/>
     * idarrs :编号字符串组<br/>
     * status : 状态<br/>
     * statusarr : 状态字符串组<br/>
     * notphprint : 配货未打印<br/>
     * notprint : 发货未打印<br/>
     * @param map
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-8-9
     */
    public List getSalesOrderGoodsListBy(Map map);

    /**
     * 获取打印的订单明细列表<br/>
     * queryMap的参数<br/>
     * orderid:订单号<br/>
     * orderseq:排序分类<br/>
     * @param queryMap
     * @return
     * @author zhanghonghui
     * @date 2016年4月11日
     */
    public List<OrderGoodsDetail> getOrderGoodsDetailPrintBy(Map queryMap);

    /**
     * 更新打印次数
     * @param order
     * @return
     * @author zhanghonghui
     * @date 2014-8-11
     */
    public int updateOrderGoodsPrinttimes(OrderGoods order);

    /**
     * 获取订货单明细符合要货单条件的数量
     * @param demandid
     * @param ordergoodsid
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 23, 2017
     */
    public int getDemandGoodsInOrderGoodsNum(@Param("demandid") String demandid,@Param("ordergoodsid") String ordergoodsid);

    /**
     * 修改订货单明细数据
     * @param orderGoodsDetailList
     * @return int
     * @throws
     * @author luoqiang
     * @date Oct 23, 2017
     */
    public int updateOrderGoodsDetailList(@Param("list")List<OrderGoodsDetail> orderGoodsDetailList,@Param("ordergoodsid") String ordergoodsid);

    /**
     * 获取销售订货单导出的数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 05, 2018
     */
    public List getExportOrderGoodsData(PageMap pageMap);

    /**
     * 获取销售订货单报表数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 10, 2018
     */
    public List getOrderGoodsReportList(PageMap pageMap);

    /**
     * 获取销售订货单报表数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 10, 2018
     */
    public int getOrderGoodsReportCount(PageMap pageMap);

    /**
     * 获取销售订货单报表合计数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Jan 10, 2018
     */
    public Map getOrderGoodsReportSum(PageMap pageMap);

    /**
     * 获取手机端订货单报表查询数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Mar 09, 2018
     */
    public List getPhoneOrderGoodsReportList(PageMap pageMap);

    /**
     * 获取手机端订货单报表查询数据
     * @param pageMap
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Mar 09, 2018
     */
    public int getPhoneOrderGoodsReportCount(PageMap pageMap);

    /**
     * 订货单获取数据用来关联要货单
     * @param ids
     * @return java.util.List
     * @throws
     * @author luoqiang
     * @date Mar 09, 2018
     */
    public List getOrderGoodsMapList(@Param("ids") String ids);

    /**
     * 批量添加订货单关联信息数据
     * @param list
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 09, 2018
     */
    public int insertOrderGoodsRelationList(List list);

    /**
     * 删除订货单关联订单的关系
     * @param orderid
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 12, 2018
     */
    public int deleteRelationOrderGoods(@Param("orderid") String orderid);

    /**
     * 根据单据编号对订货单进行回写
     * @param orderid
     * @param type 1表示还原订货单已生成未生成数量，金额，2表示关联订货单已生成未生成数量，金额
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    public int updateOrderGoodsByOrder(@Param("orderid") String orderid,@Param("type") String type,@Param("ordergoodsid") String ordergoodsid);

    /**
     * 删除订单里面本次保存删除掉的商品
     * @param orderid
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    public int deleteRalationNotInOrder(@Param("orderid") String orderid);

    /**
     * 回写关系表数量，把关系表数量改为当前明细的商品数量
     * @param orderid
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    public int updateRelationGoodsNum(@Param("orderid") String orderid);

    /**
     * 关联订货单之后更新订单明细里面的relationordergoodsid
     * @param orderid
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    public int updateOrderRelationOrdergoodsid(@Param("orderid") String orderid);

    /**
     * 修改要货单关联订货单字段
     * @param demandid
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    public int updateDemandRelationId(@Param("demandid") String demandid,@Param("type") String type);

    /**
     * 删除不存在订单明细的订货单关系（要货单类型生成的订单使用）
     * @param orderid
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    public int deleteRalationNotInDemandOrder(@Param("orderid") String orderid,@Param("demandid") String demandid);

    /**
     * 获取订货单关系明细
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    public Map getOrderGoodsRelationDetail(@Param("id") String id);

    /**
     * 修改订货单关系的数量
     * @param id
     * @param unitnum
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 15, 2018
     */
    public int updateOrderGoodsRelationNum(@Param("id") String id,@Param("unitnum") BigDecimal unitnum);

    /**
     * 回写关系表数量，把关系表数量改为当前明细的商品数量
     * @param orderid
     * @param demandid
     * @return int
     * @throws
     * @author luoqiang
     * @date Mar 16, 2018
     */
    public int updateDemadRelationGoodsNum(@Param("orderid") String orderid,@Param("demandid") String demandid);

    /**
     * 获取订货单单据列表
     * @param ids
     * @return java.util.List<com.hd.agent.sales.model.OrderGoods>
     * @throws
     * @author luoqiang
     * @date Mar 26, 2018
     */
    public List<OrderGoods> getOrderGoodsListByIds(@Param("ids") String ids);

    /**
     * 根据主键获取订货单明细数据
     * @param id
     * @return com.hd.agent.sales.model.OrderGoodsDetail
     * @throws
     * @author luoqiang
     * @date Mar 30, 2018
     */
    public OrderGoodsDetail getOrderGoodsDetail(int id);
}
