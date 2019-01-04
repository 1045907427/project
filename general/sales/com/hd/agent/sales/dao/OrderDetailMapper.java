package com.hd.agent.sales.dao;

import com.hd.agent.sales.model.Order;
import com.hd.agent.sales.model.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderDetailMapper {
    
	public int addOrderDetail(OrderDetail detail);
	/**
	 * 根据列表添加明细
	 * @param list
	 * @return
	 * @author chenwei 
	 * @date Jan 2, 2014
	 */
	public int addOrderDetailList(@Param("list")List list);
	
	/**通过订单编码获取商品详细列表*/
	public List getOrderDetailByOrder(@Param("id")String id,@Param("order")String order);

    /**
     * 根据订单编号获取该订单正常的订单明细
     * @param id
     * @param order
     * @return
     */
    public List getOrderDetailIsViewByOrder(@Param("id")String id,@Param("order")String order);
	/**
	 * 根据订单编号 获取商品的合计数量列表
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年12月29日
	 */
	public List getOrderDetailSumListByID(@Param("id")String id);
	/**
	 * 通过订单编码获取商品详细列表 合计折扣数据
	 * @param id
	 * @param order
	 * @return
	 * @author chenwei 
	 * @date 2014年6月21日
	 */
	public List getOrderDetailByOrderWithDiscount(@Param("id")String id,@Param("order")String order);

    /**
     * 通过订单编码获取不为折扣的商品详细列表（除折扣外）
     * @param id
     * @param order
     * @return
     */
    public List getOrderDetailByOrderWithoutDiscount(@Param("id")String id,@Param("order")String order);
	/**
	 * 获取销售订单不显示的列表明细（配置库存后，没有商品的明细）
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2015年2月4日
	 */
	public List getOrderDetailLackList(@Param("id")String id);
	
	/**获取商品明细详细信息*/
	public OrderDetail getOrderDetail(String id);
	
	/**通过订单编码删除商品明细*/
	public int deleteOrderDetailByOrderId(String id);
	
	/**通过订单编码获取订单明细合计*/
	public Map getOrderDetailTotal(String id);
	
	public int updateOrderDetail(OrderDetail detail);
	
	/**
	 * 回写订单数据
	 * @param detail
	 * @return
	 * @author zhengziyong 
	 * @date May 21, 2013
	 */
	public int updateOrderDetailBack(OrderDetail detail);
	/**
	 * 根据订单编号和品牌编号 获取明细列表
	 * @param orderid
	 * @param brandid
	 * @return
	 * @author chenwei 
	 * @date 2014年6月21日
	 */
	public List getOrderDetailListByOrderidAndBrandid(@Param("orderid")String orderid,@Param("brandid")String brandid);
	/**
	 * 根据订单获取该订单的折扣数量
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date 2014年7月21日
	 */
	public int getOrderDetailDiscount(@Param("orderid")String orderid);
	/**
	 * 获取订单中，0金额的商品明细数量（数量不为0，金额为0）
	 * @param orderid
	 * @return
	 * @author chenwei 
	 * @date 2014年10月7日
	 */
	public int getOrderDetailZeroCount(@Param("orderid")String orderid);
	/**
	 * 更新订单明细补货状态
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date 2014年12月26日
	 */
	public int updateOrderDetailIssupply(String id);

    /**
     * 获取订单的最近版本号
     * @param id
     * @return
     */
    public int getOrderVersionByID(@Param("id")String id);

    /**
     * 获取订单的版本序号
     * @param id
     * @param version
     * @return
     */
    public int getOrderVersionVid(@Param("id")String id,@Param("version")int version);
    /**
     * 添加销售订单版本
     * @param id
     * @param version
     * @return
     */
    public int addOrderWithVersion(@Param("id")String id,@Param("version")int version);

    /**
     * 添加销售订单明细版本
     * @param id
     * @param version
     * @return
     */
    public int addOrderDetailWithVersion(@Param("id")String id,@Param("version")int version);

    /**
     * 获取销售订单的版本记录列表
     * @param id
     * @return
     */
    public List showOrderVersionList(@Param("id")String id);

    /**
     * 通过订单编号和版本号获取订单明细合计
     * @param id
     * @param version
     * @return
     */
    public Map getOrderVersionDetailTotal(@Param("id")String id,@Param("version")int version);

    /**
     * 根据订单编号和指定版本 获取订单信息
     * @param id
     * @param version
     * @return
     */
    public Order getOrderVersion(@Param("id")String id,@Param("version")String version);

    /**
     * 根据订单编号和版本号  获取订单明细信息
     * @param id
     * @param version
     * @param order
     * @return
     */
    public List getOrderVersionDetailByOrderWithDiscount(@Param("id")String id,@Param("version")String version,@Param("order")String order);
    /**
     * 根据订单编号获取已赠送的满赠信息
     * @param orderid
     * @return
     * @author chenwei 
     * @date 2015年9月17日
     */
    public List getOrderPromotionFullFreeList(@Param("orderid")String orderid);

    /**
     * 获取订单的 促销信息列表
     * @param orderid
     * @return
     */
    public List getOrderPromotionGroupList(@Param("orderid")String orderid);

    /**
     * 根据订单编号和促销编号 获取促销信息
     * @param orderid
     * @param groupid
     * @return
     */
    public Map getOrderPromotionGroupByGroupid(@Param("orderid")String orderid,@Param("groupid")String groupid);
    /**
     * 根据开始日期与截止日期 获取客户商品的销售信息（排除赠品）\
     * @param orderid           订单编号
     * @param groupid           促销分组编号
     * @param customerid        客户编号
     * @param goodsid           商品编号
     * @param beginDate         开始日期
     * @param endDate           结束日期
     * @return
     * @author chenwei 
     * @date 2015年9月16日
     */
    public Map getGoodsSalesInfoByCustomeridAndGoodsid(@Param("orderid")String orderid,@Param("groupid")String groupid,@Param("customerid")String customerid,@Param("goodsid")String goodsid,@Param("beginDate")String beginDate,@Param("endDate")String endDate);
    /**
     * 添加客户满赠已达成条件的赠送记录
     * @param customerid		客户编号
     * @param orderid			订单编号
     * @param goodsid			商品编号
     * @param groupid			促销编号
     * @param unitnum			已参加促销数量
     * @param amount			已参加促销金额
     * @return
     * @author chenwei 
     * @date 2015年9月18日
     */
    public int addCustomerFullFreeLog(@Param("customerid")String customerid,@Param("orderid")String orderid,@Param("goodsid")String goodsid,@Param("groupid")String groupid,@Param("unitnum")BigDecimal unitnum,@Param("amount")BigDecimal amount);
    /**
     * 删除客户满赠达成条件记录
     * @param orderid
     * @return
     * @author chenwei 
     * @date 2015年9月18日
     */
    public int deleteCustomerFullFreeLog(@Param("orderid")String orderid);
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
    public List<OrderDetail> getOrderDetailPrintBy(Map queryMap);
}