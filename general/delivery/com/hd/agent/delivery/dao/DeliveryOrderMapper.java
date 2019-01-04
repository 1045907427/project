package com.hd.agent.delivery.dao;


import com.hd.agent.common.util.PageMap;
import com.hd.agent.delivery.model.DeliveryAogorder;
import com.hd.agent.delivery.model.DeliveryOrder;
import com.hd.agent.delivery.model.DeliveryOrderDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DeliveryOrderMapper {
	/**
	 * 获取客户订单数量
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	int getDeliveryOrderListCount(PageMap pageMap);
	/**
	 * 获取客户订单列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	List getDeliveryOrderList(PageMap pageMap);
	/**
	 * 添加客户订单明细
	 * @param deliveryOrderDetail
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addDeliveryOrderDetail(DeliveryOrderDetail deliveryOrderDetail);
	/**
	 * 添加客户订单
	 * @param deliveryOrder
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addDeliveryOrder(DeliveryOrder deliveryOrder);
	/**
	 * 通过ID获取客户订单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public DeliveryOrder getDeliveryOrderByID(@Param("id")String id);
	/**
	 * 通过ID获取客户订单详情
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List<DeliveryOrderDetail> getDeliveryOrderDetailList(@Param("billid")String billid);
	/**
	 * 删除客户订单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteDeliveryOrder(@Param("id")String id);
	/**
	 * 删除客户订单详情
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteDeliveryOrderDetail(@Param("billid")String billid);
	/**
	 * 保存修改的客户订单
	 * @param deliveryOrder
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int editDeliveryOrder(DeliveryOrder deliveryOrder);
	/**
	 * 审核客户订单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int auditDeliveryOrder(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date,@Param("businessdate")String businessdate);
	/**
	 * 反审客户订单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppauditDeliveryOrder(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date);
	/**
	 * 客户出库单审核通过关闭客户订单
	 * @param id date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int closeDeliveryOrder(@Param("id")String id,@Param("date")Date date);
	/**
	 * 获取客户订单导出列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List getOrderList(PageMap pageMap);
	/**
	 * 获取客户订单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showOrderListBy(Map map);
	/**
	 * 更新打印次数
	 * @param deliveryOrder
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int updateOrderPrinttimes(DeliveryOrder deliveryOrder);
	/**
	 * 反审出库单后改变客户订单状态为审核
	 * @param id date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppDeliveryOut(@Param("id")String id);
}