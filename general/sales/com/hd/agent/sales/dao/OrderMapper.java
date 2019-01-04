package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Order;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 销售订单Mapper
 * @author zhengziyong
 *
 */
public interface OrderMapper {
    
	/**
	 * 添加销售订单
	 */
	public int addOrder(Order order);
	
	/**
	 * 更新销售订单
	 */
	public int updateOrder(Order order);

    /**
     * 更新销售订单客户单号
     */
    public int updateOrderSourceid(@Param("sourceid")String sourceid ,@Param("id") String id);
	
	/**
	 * 获取销售订单详细信息
	 */
	public Order getOrderDetail(Map map);
	
	/**
	 * 获取销售订单列表
	 */
	public List getOrderList(PageMap pageMap);
	
	/**
	 * 获取销售订单数量
	 */
	public int getOrderCount(PageMap pageMap);
	
	/**
	 * 更新订单状态
	 */
	public int updateOrderStatus(Order order);
	
	/**
	 * 删除订单信息
	 */
	public int deleteOrder(String id);
	
	/**
	 * 更新是否被参照状态
	 */
	public int updateOrderRefer(String isrefer, String id);
	/**
	 * 获取客户商品的最近一次交易价格
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @author chenwei 
	 * @date Mar 21, 2014
	 */
	public Map getCustomerGoodsInfoByLast(@Param("customerid")String customerid,@Param("goodsid")String goodsid);

    /**
     * 取客户商品的最低一次交易价格
     * @param customerid
     * @param goodsid
     * @param date
     * @return
     */
    public Map getCustomerGoodsInfoByLowest(@Param("customerid")String customerid,@Param("goodsid")String goodsid,@Param("date")String date);
    /**
	 * 根据来源单据类型与来源单据编号 获取销售订单
	 * @param sourceid
	 * @param sourcetype
	 * @return
	 * @author chenwei 
	 * @date Apr 17, 2014
	 */
	public Order getOrderBySourceid(@Param("sourceid")String sourceid,@Param("sourcetype")String sourcetype);
	/**
	 * 验证销售订单是否重复
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 26, 2014
	 */
	public Map checkOrderRepeat(@Param("id")String id,@Param("customerid")String customerid,@Param("days")String days);
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
	public List getSalesOrderListBy(Map map);
	/**
	 * 更新打印次数
	 * @param order
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-8-11
	 */
	public int updateOrderPrinttimes(Order order);
	/**
	 * 
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-8-11
	 */
	public Order getOrderById(@Param("id")String id);
	/**
	 * 获取客户未出库订单金额
	 * @param customerid
	 * @return
	 * @author chenwei 
	 * @date 2014年11月26日
	 */
	public BigDecimal getCustomerOrderAmount(@Param("customerid")String customerid);

    /**
     * 获取客户未出库订单金额
     * @param customerid
     * @return
     * @author chenwei
     * @date 2014年11月26日
     */
    public BigDecimal getCustomerOrderAndDispatchAmount(@Param("customerid")String customerid);
    /**
     * 获取总店客户未出库订单金额
     * @param pcustomerid
     * @return
     */
    public BigDecimal getCustomerOrderAmountByPcustomerid(@Param("pcustomerid")String pcustomerid);

    /**
     * 获取总店客户未出库订单金额
     * @param pcustomerid
     * @return
     */
    public BigDecimal getCustomerOrderAndDispatchAmountByPcustomerid(@Param("pcustomerid")String pcustomerid);
	/**
	 * 根据订单编号获取订单金额
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年11月26日
	 */
	public BigDecimal getOrderAmount(@Param("id")String id);
	/**
	 * 获取订单客户单据号
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年12月28日
	 */
	public String getCustomerBillId(@Param("id")String id);

    /**
     * 获取促销单的引用量
     * @param groupid
     * @return
     */
    public int countPromotionGroupid(String groupid);

    /**
     * 验证客户单号是否在订单中存在
     * @param sourceid
     * @return
     */
    public int validateSourceId(@Param("sourceid")String sourceid,@Param("businessdate")String businessdate);

    /**
     * 获取订单中的品牌列表
     * @param id        订单编号
     * @return
     */
    public List<String> getBrandListInOrder(@Param("id") String id);
	 /**
	  * 根据客户及商品编号获取商品最近三次销售价（销售订单内审核通过的）
	  * @author lin_xx
	  * @date 2017/3/15
	  */
	 public List<Map> getOrderAuditGoodsPrice(@Param("customerid")String customerid,@Param("goodsid") String goodsid,@Param("count")String count);

	/**
	 * 根据客户及商品编号获取商品最近三次销售价（销售订单内审核通过的）
	 * @author wanghongteng
	 * @date 2017-6-6
	 */
	public List<Map> getNullOrderList();


	/**
	 * 修改订单的打印状态和配送打印状态
	 * @author wanghongteng
	 * @date 2017-6-6
	 */
	public int doUpdateOrderStatus(String id);

	/**
	 * 取客户最近一次商品销售价格
	 *
	 * @param customerid
	 * @param goodsid
	 * @param type
	 * @return
     * @author limin
     * @date Jan 4, 2018
	 */
	public BigDecimal getLastestPriceByCustomerGoods(@Param("customerid") String customerid, @Param("goodsid") String goodsid, @Param("type") String type);

}