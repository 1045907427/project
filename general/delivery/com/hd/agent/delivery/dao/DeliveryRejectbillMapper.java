package com.hd.agent.delivery.dao;


import com.hd.agent.common.util.PageMap;
import com.hd.agent.delivery.model.DeliveryAogorder;
import com.hd.agent.delivery.model.DeliveryRejectbill;
import com.hd.agent.delivery.model.DeliveryRejectbillDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DeliveryRejectbillMapper {
	/**
	 * 获取客户退货订单数量
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	int getDeliveryRejectbillListCount(PageMap pageMap);
	/**
	 * 获取客户退货订单列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	List getDeliveryRejectbillList(PageMap pageMap);
	/**
	 * 添加客户退货订单明细
	 * @param deliveryRejectbillDetail
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addDeliveryRejectbillDetail(DeliveryRejectbillDetail deliveryRejectbillDetail);
	/**
	 * 添加客户退货订单
	 * @param deliveryRejectbill
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int addDeliveryRejectbill(DeliveryRejectbill deliveryRejectbill);
	/**
	 * 通过ID获取客户退货订单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public DeliveryRejectbill getDeliveryRejectbillByID(@Param("id")String id);
	/**
	 * 通过ID获取客户退货订单详情
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List<DeliveryRejectbillDetail> getDeliveryRejectbillDetailList(@Param("billid")String billid);
	/**
	 * 删除客户退货订单
	 * @param id
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteDeliveryRejectbill(@Param("id")String id);
	/**
	 * 删除客户退货订单详情
	 * @param billid
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int deleteDeliveryRejectbillDetail(@Param("billid")String billid);
	/**
	 * 保存修改的客户退货订单
	 * @param deliveryRejectbill
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int editDeliveryRejectbill(DeliveryRejectbill deliveryRejectbill);
	/**
	 * 审核客户退货订单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int auditDeliveryRejectbill(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date,@Param("businessdate")String businessdate);
	/**
	 * 反审客户退货订单
	 * @param id userid username date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppauditDeliveryRejectbill(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("date")Date date);
	/**
	 * 客户退货入库单审核通过关闭客户退货订单
	 * @param id date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int closeDeliveryRejectbill(@Param("id")String id,@Param("date")Date date);
	/**
	 * 获取客户退货订单导出列表
	 * @param pageMap
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public List getRejectbillList(PageMap pageMap);
	/**
	 * 获取客户退货订单列表<br/>
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
	public List showRejectbillListBy(Map map);
	/**
	 * 更新打印次数
	 * @param buyOrder
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int updateRejectbillPrinttimes(DeliveryRejectbill deliveryRejectbill);
	/**
	 * 反审入库单后改变客户退货订单状态为审核
	 * @param id date
	 * @return
	 * @author wanghongteng 
	 * @date Aug 7,2015
	 */
	public int oppDeliveryEnter(@Param("id")String id);
}