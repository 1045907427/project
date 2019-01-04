package com.hd.agent.sales.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.OrderCar;
import com.hd.agent.sales.model.OrderCarDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 车售订单Mapper
 * @author zhengziyong
 *
 */
public interface OrderCarMapper {
    
	/**
	 * 添加车售订单
	 */
	public int addOrderCar(OrderCar order);
	
	/**
	 * 获取车售订单详细信息
	 */
	public OrderCar getOrderCar(Map map);
	
	/**
	 * 获取车售订单列表
	 */
	public List getOrderCarList(PageMap pageMap);
	
	/**
	 * 获取车售订单数量
	 */
	public int getOrderCarCount(PageMap pageMap);
	
	public int updateOrderCar(OrderCar order);
	
	/**
	 * 更新订单状态
	 */
	public int updateOrderCarStatus(OrderCar order);
	
	/**
	 * 删除订单信息
	 */
	public int deleteOrderCar(String id);
	
	public int addOrderCarDetail(OrderCarDetail detail);
	
	/**通过订单编码获取商品详细列表*/
	public List getOrderCarDetailByOrder(String id);
	
	/**通过订单编码删除商品明细*/
	public int deleteOrderCarDetailByOrderId(String id);
	
	/**通过订单编码获取订单明细合计*/
	public Map getOrderCarDetailTotal(String id);
	
	public int updateOrderCarDetail(OrderCarDetail detail);
	/**
	 * 通过单据编号获取直营销售单商品明细合计
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Apr 11, 2014
	 */
	public List getOrderCarDetailSumListByGoodsid(@Param("id")String id);
	/**
	 * 获取车销打印列表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-9
	 */
	public List getOrderCarPrintListData(PageMap pageMap);
	/**
	 * 获取车销打印列表数据条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-9
	 */
	public int getOrderCarPrintListDataCount(PageMap pageMap);
	/**
	 * 获取车销单据详细列表数据
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-9
	 */
	public List getOrderCarPrintDetailListData(PageMap pageMap);
	/**
	 * 给车销售打印使用的发货单明细
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2014-4-10
	 */
	public List getOrderCarPrintDetailListData(Map map);
	/**
	 * 判断直营销售单是否重复 
	 * @param id
	 * @param customerid
	 * @param days
	 * @return
	 * @author chenwei 
	 * @date Apr 25, 2014
	 */
	public Map checkOrderCarRepeat(@Param("id")String id,@Param("customerid")String customerid,@Param("days")String days);
	/**
	 * 根据keyid 获取车销单
	 * @param keyid
	 * @return
	 * @author chenwei 
	 * @date 2014年12月18日
	 */
	public OrderCar getOrderCarByKeyid(@Param("keyid")String keyid);
}