/**
 * @(#)IDispatchBillExtService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 7, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.service.ext;

import com.hd.agent.sales.model.Order;
import com.hd.agent.sales.model.OrderCar;
import com.hd.agent.sales.model.OrderCarDetail;

import java.util.List;
import java.util.Map;

/**
 * 
 * 销售发货通知单对外接口
 * 
 * @author zhengziyong
 */
public interface IDispatchBillExtService {

	/**
	 * 订单审核自动生成发货通知单
	 * @param order
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public String addDispatchBillAuto(Order order) throws Exception;
	
	/**
	 * 反审订单时删除发货通知单
	 * @param id 订单编号
	 * @return 
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public boolean deleteDispatchBillOppauditOrder(String id) throws Exception;
	/**
	 * 直营销售订单（车销单）审核通过后，自动生成下游单据
	 * 下游单据 销售发货通知单 发货单 销售发货回单 自动审核通过
	 * @param orderCar
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Sep 3, 2013
	 */
	public Map addNextBillByOrdercar(OrderCar orderCar) throws Exception;
	/**
	 * 判断车销订单上传的商品中 车销仓库是否足够
	 * @param orderCar
	 * @param detailList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 18, 2014
	 */
	public boolean isGoodsEnoughByOrdercar(OrderCar orderCar,List<OrderCarDetail> detailList) throws Exception;
	/**
	 * 添加并且自动审核销售发货通知单
	 * @param order
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月26日
	 */
	public Map addAndAuditDispatchBill(Order order) throws Exception;
}

